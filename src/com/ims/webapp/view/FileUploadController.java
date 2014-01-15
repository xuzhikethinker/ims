package com.ims.webapp.view;

import com.ims.domain.customer.CustomerInfo;
import com.ims.domain.customer.CustomerProductCodeMap;
import com.ims.domain.order.OrderStatus;
import com.ims.domain.order.PurchaseOrder;
import com.ims.domain.order.PurchaseOrderItem;
import com.ims.domain.support.ProductInfo;
import com.ims.webapp.view.util.NumberUtil;
import com.ims.webapp.view.util.POInfoFileResolver;
import com.ims.webapp.view.util.ProductInfoFileResolver;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@ManagedBean(name = "fileUploadController")
@ViewScoped
public class FileUploadController extends BaseView {
    private static final String PROD_INFO_LIST = "prodInfoList";
    private static final String PO_INFO_FILE = "POInfo";
    private String errorMessage = null;
    private boolean processSuccess = true;

    private String fileCode;

    public void processFileUpload(FileUploadEvent event) {
        String fileType = (String) event.getComponent().getAttributes().get("fileType");
        UploadedFile file = event.getFile();
        prodCategoryList = this.supportingDataService.loadProdCategoryList(true);
        custProdCodeMapList = this.customerService.getCustomerProdCodeMapList();
        customerProductCodeMapping = this.customerService.getCustomerProdCodeMap();
        productInfoMap = this.supportingDataService.getProductInfoMap();
        if (StringUtils.isNotEmpty(fileType) && fileType.equals(PROD_INFO_LIST)) {
            errorMessage = this.processProdInfoUpload(file);
            FacesMessage msg = new FacesMessage("上传产品信息", "产品信息上传成功");
            if (errorMessage != null) {
                msg = new FacesMessage("上传产品信息", "产品信息上传失败，请查看错误信息");
                processSuccess = false;
            }
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else if (StringUtils.isNotEmpty(fileType) && fileType.equals(PO_INFO_FILE)) {
            errorMessage = this.processPOInfoUpload(file, productInfoMap);
            FacesMessage msg = new FacesMessage("创建草稿PO", "草稿PO创建成功");
            if (errorMessage != null) {
                msg = new FacesMessage("创建草稿PO", "草稿PO创建失败，请查看错误信息");
                processSuccess = false;
            }
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }

    }

    private String processPOInfoUpload(UploadedFile file, Map<String, ProductInfo> productInfoMap) {
        String errorMessage = null;
        StringBuffer errors = new StringBuffer();
        StringBuilder noProductList = new StringBuilder("系统中没有如下产品：\r\n");
        boolean noProduct = false;
        StringBuilder noCustomerProdCodeList = new StringBuilder("系统中如下产品没有对应的客户产品编号：\r\n");
        boolean noCustProdCode = false;
        try {
            PurchaseOrder purchaseOrder = POInfoFileResolver.processPOInfoFile(file.getInputstream(), file.getFileName());
            if (purchaseOrder != null) {
                errorMessage = checkUploadedPO(purchaseOrder);
                if (errorMessage == null) {
                    CustomerInfo customerInfo = customerService.getCustomerInfo();
                    purchaseOrder.setCustomerName(customerInfo.getCustomerName());
                    purchaseOrder.setCustomerCode(customerInfo.getCustomerCode());
                    purchaseOrder.setContact(customerInfo.getContact());
                    purchaseOrder.setStatus(OrderStatus.PO_DRAFT.getCode());
                    if (!purchaseOrder.getOrderItems().isEmpty()) {
                        for (PurchaseOrderItem item : purchaseOrder.getOrderItems()) {
                            ProductInfo productInfo = productInfoMap.get(item.getCompanyProductCode());
                            if (productInfo != null) {
                                String custProdCode = productInfo.getCustomerProductCode();
                                if (custProdCode == null) {
                                    custProdCode = "No_Customer_Product_Code";
                                    noCustProdCode = true;
                                    noCustomerProdCodeList.append("");
                                }

                                item.setCustomerProductCode(custProdCode);
                                double unitPrice = item.getUnitPrice() == 0l ? productInfo.getPrice() : item.getUnitPrice();
                                item.setUnitPrice(unitPrice);
                                item.setTotalPrice(NumberUtil.formatDoubleWith2Decimal(unitPrice * item.getProductAmount().getTotalAmount()));
                                purchaseOrder.addToTotalPrice(NumberUtil.formatDoubleWith2Decimal(item.getTotalPrice()));
                            } else {
                                noProduct = true;
                                noProductList.append("Product No. = " + item.getCompanyProductCode() + "\r\n");
                            }
                        }
                    }
                    if (noProduct) {
                        errors.append(noProductList.toString() + "\r\n");
                    }

                    if (noCustProdCode) {
                        errors.append(noCustomerProdCodeList.toString() + "\r\n");
                    }
                    if(!errors.toString().isEmpty()){
                        errorMessage = errors.toString();
                    }

                    if (errorMessage == null || errorMessage.length() == 0) {
                        this.orderService.savePurchaseOrder(productInfoMap, purchaseOrder);
                    }
                }
            }
        } catch (IOException io) {
            errorMessage = io.getMessage();
        }
        return errorMessage;
    }

    private String checkUploadedPO(PurchaseOrder purchaseOrder) {
        StringBuffer errors = new StringBuffer();

        if (StringUtils.isNotEmpty(purchaseOrder.getPurchaseOrderNumber())) {
            List<PurchaseOrder> purchaseOrders = this.orderService.findPurchaseOrderByPONumber(purchaseOrder.getPurchaseOrderNumber());
            if (purchaseOrders != null && !purchaseOrders.isEmpty()) {
                errors.append("系统中已有相同的PO No.存在：PO No. = " + purchaseOrder.getPurchaseOrderNumber() + "，修改后重新提交。\r\n");
            }
        }

        if (StringUtils.isEmpty(purchaseOrder.getPurchaseOrderNumber())) {
            errors.append("PO No.不可以为空\r\n");
        }

        if (purchaseOrder.getOrderItems().isEmpty()) {
            errors.append("至少要有一条产品记录\r\n");
        }

        return StringUtils.isEmpty(errors.toString()) ? null : errors.toString();
    }

    private String processProdInfoUpload(UploadedFile file) {
        String errorMessage = null;
        try {
            List<CustomerProductCodeMap> newCustProdCodeList = new ArrayList<CustomerProductCodeMap>();
            ProductInfoFileResolver.resolveProductInfoListFile(file.getInputstream(), file.getFileName(), prodCategoryList, customerProductCodeMapping, newCustProdCodeList);
            this.supportingDataService.updateCategoryList(prodCategoryList);
            this.productList = this.supportingDataService.getProductInfoList(null);
        } catch (IOException io) {
            errorMessage = io.getMessage();
        }
        return errorMessage;
    }


    public void handleFileUpload(FileUploadEvent event) {
        FacesMessage msg = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
        if (!PhaseId.INVOKE_APPLICATION.equals(event.getPhaseId())) {
            event.setPhaseId(PhaseId.INVOKE_APPLICATION);
            event.queue();
        } else {
            String fileType = (String) event.getComponent().getAttributes().get("fileType");
            String param2 = this.getFileCode();
            try {
                File targetFolder = new File(this.getInitParamValueByKey("UPLOAD_IMAGE_PATH"));
                UploadedFile file = event.getFile();
                prodCategoryList = this.supportingDataService.loadProdCategoryList(true);
                if (fileType.equals("prodInfoList")) {
//                    ProductInfoFileResolver.resolveProductInfoListFile(file.getInputstream(), file.getFileName(), prodCategoryList);
                    this.supportingDataService.updateCategoryList(prodCategoryList);
                    this.productList = this.supportingDataService.getProductInfoList(null);
                    return;
                }
                String newFileName = this.getServletContext().getRealPath("") + File.separator + "resources" + File.separator + "upload" + File.separator + file.getFileName();
                FileOutputStream fos = new FileOutputStream(new File(newFileName));
                InputStream is = file.getInputstream();
                int BUFFER_SIZE = 8192;
                byte[] buffer = new byte[BUFFER_SIZE];
                int a;
                while (true) {
                    a = is.read(buffer);
                    if (a < 0) break;
                    fos.write(buffer, 0, a);
                    fos.flush();
                }
                fos.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public String getFileCode() {
        return fileCode;
    }

    public void setFileCode(String fileCode) {
        this.fileCode = fileCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public boolean isProcessSuccess() {
        return processSuccess;
    }

    public void setProcessSuccess(boolean processSuccess) {
        this.processSuccess = processSuccess;
    }
}
