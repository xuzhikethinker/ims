package com.ims.webapp.view;

import com.ims.domain.order.OrderStatus;
import com.ims.domain.order.ProformaInvoice;
import com.ims.domain.order.PurchaseOrder;
import com.ims.domain.order.PurchaseOrderItem;
import com.ims.domain.support.ProductInfo;
import com.ims.webapp.view.criteria.OrderSearchCriteria;
import com.ims.webapp.view.criteria.ProdSearchCriteria;
import com.ims.webapp.view.dto.OrderStatusDTO;
import com.ims.webapp.view.dto.ProductInfoDataModel;
import com.ims.webapp.view.util.POExcelGenerator;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Workbook;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.Visibility;
import org.primefaces.util.Constants;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ManagedBean(name = "orderMaintainView")
@ViewScoped
public class OrderMaintainView extends StockMaintainView {
    private static final String ADD_ORDER = "add";
    private static final String MODIFY_ORDER = "modify";
    private static final String PO_INPUT_SIMPLE = "simple";
    private static final String PO_INPUT_EXCEL = "excel";
    private String orderAction = ADD_ORDER;
    private PurchaseOrder purchaseOrder;
    private ProformaInvoice proformaInvoice;
    private String poInputMethod = PO_INPUT_SIMPLE;
    private ProductInfoDataModel productInfoDataModel = new ProductInfoDataModel();
    private ProductInfo[] selectedProductInfos;
    private List<ProductInfo> poItemList = new ArrayList<ProductInfo>();
    private boolean searchProd = false;
    private Long purchaseOrderID;
    private OrderSearchCriteria orderSearchCriteria = new OrderSearchCriteria();
    private List<PurchaseOrder> purchaseOrderList = new ArrayList<PurchaseOrder>();
    private ProductInfo selectedProd;
    private boolean supportSize;
    private PurchaseOrderItem newOrderItem = new PurchaseOrderItem();
    public boolean addNewOrderItem = false;

    public OrderMaintainView() {
        System.out.println("OrderMaintainView=" + this);
    }

    public void loadData() {
        prodCategoryList = this.supportingDataService.loadProdCategoryList(true);
        this.productList = this.supportingDataService.getProductInfoList(new ProdSearchCriteria());
        productInfoDataModel = new ProductInfoDataModel(this.productList);
    }

    public void loadPO() {
        this.purchaseOrder = this.orderService.findPurchaseOrderByID(purchaseOrderID);
        this.productList = this.supportingDataService.getProductInfoList(new ProdSearchCriteria());
        System.out.println("id=" + this.purchaseOrderID);
    }

    public String gotoModifyPO() {

        return "po_maintain";
    }

    public void updateOrderItem(RowEditEvent event) {
        PurchaseOrderItem orderItem = (PurchaseOrderItem) event.getObject();
        this.purchaseOrder = this.orderService.savePurchaseOrderItem(orderItem);
        System.out.println("total=" + this.purchaseOrder.getTotalPrice());
//        selectedProductInfo = (ProductInfo) event.getObject();
//        FacesMessage msg = new FacesMessage("更新产品信息", "产品信息成功更新");
//        this.supportingDataService.updateProductInfo(selectedProductInfo);
//        reloadData(SupportingDataService.MENU_CODE_PROD_INFO);
//        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void cancelUpdateOrderItem(RowEditEvent event) {
//        selectedProductInfo = (ProductInfo) event.getObject();
//        FacesMessage msg = new FacesMessage("更新产品信息", "产品信息成功更新");
//        this.supportingDataService.updateProductInfo(selectedProductInfo);
//        reloadData(SupportingDataService.MENU_CODE_PROD_INFO);
//        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public String getOrderAction() {
        return orderAction;
    }

    public void setOrderAction(String orderAction) {
        this.orderAction = orderAction;
    }

    public PurchaseOrder getPurchaseOrder() {
        return purchaseOrder;
    }

    public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }

    public ProformaInvoice getProformaInvoice() {
        return proformaInvoice;
    }

    public void setProformaInvoice(ProformaInvoice proformaInvoice) {
        this.proformaInvoice = proformaInvoice;
    }

    public String getPoInputMethod() {
        return poInputMethod;
    }

    public void setPoInputMethod(String poInputMethod) {
        this.poInputMethod = poInputMethod;
    }

    public ProductInfoDataModel getProductInfoDataModel() {
        return productInfoDataModel;
    }

    public void setProductInfoDataModel(ProductInfoDataModel productInfoDataModel) {
        this.productInfoDataModel = productInfoDataModel;
    }

    public ProductInfo[] getSelectedProductInfos() {
        return selectedProductInfos;
    }

    public void setSelectedProductInfos(ProductInfo[] selectedProductInfos) {
        this.selectedProductInfos = selectedProductInfos;
    }

    public String populateSelectedProdItems() {
        if (selectedProductInfos != null) {
            if (!searchProd) {
                poItemList = new ArrayList<ProductInfo>();
            }
            for (int i = 0; i < selectedProductInfos.length; i++) {
                poItemList.add(selectedProductInfos[i]);
            }
        }
        if (poItemList != null) {
            selectedProductInfos = (ProductInfo[]) poItemList.toArray(new ProductInfo[poItemList.size()]);
        }
        return null;
    }

    public void filterProductInfoList() {
        System.out.println("selectedProdCategory=" + prodSearchCriteria.toString());
        this.productList = this.supportingDataService.getProductInfoList(prodSearchCriteria);
        productInfoDataModel = new ProductInfoDataModel(this.productList);
        searchProd = true;
        if (poItemList != null) {
            selectedProductInfos = (ProductInfo[]) poItemList.toArray(new ProductInfo[poItemList.size()]);
        }
    }

    public List<ProductInfo> getPoItemList() {
        return poItemList;
    }

    public String searchPurchaseOrder() {
        purchaseOrderList = this.orderService.searchPurchaseOrderList(orderSearchCriteria);
        return null;
    }

    public void setPoItemList(List<ProductInfo> poItemList) {
        this.poItemList = poItemList;
    }

    public boolean isSearchProd() {
        return searchProd;
    }

    public void setSearchProd(boolean searchProd) {
        this.searchProd = searchProd;
    }

    public Long getPurchaseOrderID() {
        return purchaseOrderID;
    }

    public void setPurchaseOrderID(Long purchaseOrderID) {
        this.purchaseOrderID = purchaseOrderID;
    }

    public OrderSearchCriteria getOrderSearchCriteria() {
        return orderSearchCriteria;
    }

    public void setOrderSearchCriteria(OrderSearchCriteria orderSearchCriteria) {
        this.orderSearchCriteria = orderSearchCriteria;
    }

    public List<OrderStatusDTO> getPurchaseOrderStatus() {
        List<OrderStatusDTO> orderStatusDTOs = new ArrayList<OrderStatusDTO>();
        for (OrderStatus status : OrderStatus.values()) {
            if (status.isPoStatus()) {
                OrderStatusDTO statusDTO = new OrderStatusDTO(status.getCode(), status.getDescription());
                orderStatusDTOs.add(statusDTO);
            }
        }
        return orderStatusDTOs;
    }

    public List<OrderStatusDTO> getProformaInvoiceStatus() {
        List<OrderStatusDTO> orderStatusDTOs = new ArrayList<OrderStatusDTO>();
        for (OrderStatus status : OrderStatus.values()) {
            if (!status.isPoStatus()) {
                OrderStatusDTO statusDTO = new OrderStatusDTO(status.getCode(), status.getDescription());
                orderStatusDTOs.add(statusDTO);
            }
        }
        return orderStatusDTOs;
    }

    public String savePurchaseOrder() {
        this.orderService.savePurchaseOrder(this.purchaseOrder);
        this.purchaseOrder = this.orderService.findPurchaseOrderByID(this.purchaseOrder.getId());
        return null;
    }

    public String exportExcel() throws IOException {
        this.orderService.savePurchaseOrder(this.purchaseOrder);
        this.purchaseOrder = this.orderService.findPurchaseOrderByID(this.purchaseOrder.getId());
        InputStream fileinputstream = this.getServletContext().getResourceAsStream("/resources/download/ConfirmPI-template.xls");
        POIFSFileSystem poifsfilesystem = new POIFSFileSystem(fileinputstream);
        Workbook wb = new HSSFWorkbook(poifsfilesystem);

        POExcelGenerator.generateExcel(wb,purchaseOrder,this.getServletContext());
        writeExcelToResponse(this.getCurrentExternalContext(), wb, "PI.xls");
        this.getCurrentFacesContext().responseComplete();
        return null;
    }

    public List<PurchaseOrder> getPurchaseOrderList() {
        return purchaseOrderList;
    }

    public void setPurchaseOrderList(List<PurchaseOrder> purchaseOrderList) {
        this.purchaseOrderList = purchaseOrderList;
    }

    public ProductInfo getSelectedProd() {
        return selectedProd;
    }

    public void setSelectedProd(ProductInfo selectedProd) {
        this.selectedProd = selectedProd;
    }

    public List<ProductInfo> completeProductInfoList(String code) {
        List<ProductInfo> result = new ArrayList<ProductInfo>();
        for (ProductInfo productInfo : this.productList) {
            if (productInfo.getProductCode().toUpperCase().contains(code.toUpperCase())) {
                result.add(productInfo);
            }
        }
        return result;
    }

    public void addNewPOItem(SelectEvent e) {
        Object item = e.getObject();
        supportSize = this.selectedProd.getCategory().isSupportSize();
        newOrderItem.setOwner(this.getPurchaseOrder());
        newOrderItem.setTotalPrice(0l);
        newOrderItem.setPoNumber(this.purchaseOrder.getPurchaseOrderNumber());
        newOrderItem.setCompanyProductCode(this.selectedProd.getProductCode());
        newOrderItem.setDisplaySeq(this.purchaseOrderList.size() + 1);
        newOrderItem.setUnitPrice(1l);
        newOrderItem.setPoItemCode("newItemCode");
        newOrderItem.setCustomerProductCode("CustomerProdCode");
        newOrderItem.setProductInfo(this.getSelectedProd());
        newOrderItem.setUnitPrice(this.getSelectedProd().getPrice());
        this.purchaseOrder.addOrderItemToList(newOrderItem);
        this.addNewOrderItem = true;
        System.out.println("new Item Prod = " + item);
    }

    public boolean isSupportSize() {
        return supportSize;
    }

    public void setSupportSize(boolean supportSize) {
        this.supportSize = supportSize;
    }

    public PurchaseOrderItem getNewOrderItem() {
        return newOrderItem;
    }

    public void setNewOrderItem(PurchaseOrderItem newOrderItem) {
        this.newOrderItem = newOrderItem;
    }

    public String addOrderItemToPurchaseOrder() {
        this.purchaseOrder.addOrderItemToList(this.newOrderItem);
        newOrderItem.setDisplaySeq(purchaseOrder.getOrderItemList().size()+1);
        newOrderItem.setPoNumber(this.purchaseOrder.getPurchaseOrderNumber());
        newOrderItem.setPoItemCode(this.purchaseOrder.getPurchaseOrderNumber() + "_" + newOrderItem.getDisplaySeq());
        newOrderItem.setCustomerProductCode(this.getSelectedProd().getCustomerProductCode());
        newOrderItem.setTotalPrice(newOrderItem.getUnitPrice() * newOrderItem.getTotalAmountBy(newOrderItem.getProductInfo().getCategory().isSupportSize()));
        this.purchaseOrder.caculateTotalAmount();
        this.orderService.savePurchaseOrder(this.purchaseOrder);
        purchaseOrderID = this.purchaseOrder.getId();
        this.purchaseOrder = this.orderService.findPurchaseOrderByID(purchaseOrderID);
        newOrderItem = new PurchaseOrderItem();
        selectedProd = new ProductInfo();
        this.addNewOrderItem = false;
        return null;
    }

    protected void writeExcelToResponse(ExternalContext externalContext, Workbook generatedExcel, String filename) throws IOException {
        externalContext.setResponseContentType("application/vnd.ms-excel");
        externalContext.setResponseHeader("Expires", "0");
        //externalContext.setResponseHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
        externalContext.setResponseHeader("Pragma", "public");
        externalContext.setResponseHeader("Content-disposition", "attachment;filename=" + filename + ".xls");
        externalContext.addResponseCookie(Constants.DOWNLOAD_COOKIE, "true", Collections.<String, Object>emptyMap());

        OutputStream out = externalContext.getResponseOutputStream();
        generatedExcel.write(out);
        externalContext.responseFlushBuffer();

    }

    public boolean isAddNewOrderItem() {
        return addNewOrderItem;
    }

    public void setAddNewOrderItem(boolean addNewOrderItem) {
        this.addNewOrderItem = addNewOrderItem;
    }

    public void handleToggle(ToggleEvent event) {
        if(event.getVisibility()==Visibility.HIDDEN){
            addNewOrderItem = false;
        }else{
            addNewOrderItem = true;
        }

    }

    public void handleClose(ToggleEvent event) {
        addNewOrderItem = false;
    }
}
