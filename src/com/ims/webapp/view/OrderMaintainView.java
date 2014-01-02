package com.ims.webapp.view;

import com.ims.domain.order.OrderStatus;
import com.ims.domain.order.ProformaInvoice;
import com.ims.domain.order.PurchaseOrder;
import com.ims.domain.support.ProductInfo;
import com.ims.webapp.view.criteria.OrderSearchCriteria;
import com.ims.webapp.view.criteria.ProdSearchCriteria;
import com.ims.webapp.view.dto.OrderStatusDTO;
import com.ims.webapp.view.dto.ProductInfoDataModel;
import org.primefaces.event.RowEditEvent;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.ArrayList;
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

    public void loadData() {
        prodCategoryList = this.supportingDataService.loadProdCategoryList(true);
        this.productList = this.supportingDataService.getProductInfoList(new ProdSearchCriteria());
        productInfoDataModel = new ProductInfoDataModel(this.productList);
    }

    public void loadPO(){
        this.purchaseOrder = this.orderService.findPurchaseOrderByID(purchaseOrderID);
        System.out.println("id="+this.purchaseOrderID);
    }

    public String gotoModifyPO(){

        return "po_maintain";
    }

    public void updateOrderItem(RowEditEvent event) {
        System.out.println(event);
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

    public String searchPurchaseOrder(){
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

    public List<OrderStatusDTO> getPurchaseOrderStatus(){
        List<OrderStatusDTO> orderStatusDTOs = new ArrayList<OrderStatusDTO>();
        for(OrderStatus status:OrderStatus.values()){
            if(status.isPoStatus()){
                OrderStatusDTO statusDTO = new OrderStatusDTO(status.getCode(),status.getDescription());
                orderStatusDTOs.add(statusDTO);
            }
        }
        return orderStatusDTOs;
    }

    public List<OrderStatusDTO> getProformaInvoiceStatus(){
        List<OrderStatusDTO> orderStatusDTOs = new ArrayList<OrderStatusDTO>();
        for(OrderStatus status:OrderStatus.values()){
            if(!status.isPoStatus()){
                OrderStatusDTO statusDTO = new OrderStatusDTO(status.getCode(),status.getDescription());
                orderStatusDTOs.add(statusDTO);
            }
        }
        return orderStatusDTOs;
    }

    public List<PurchaseOrder> getPurchaseOrderList() {
        return purchaseOrderList;
    }

    public void setPurchaseOrderList(List<PurchaseOrder> purchaseOrderList) {
        this.purchaseOrderList = purchaseOrderList;
    }
}
