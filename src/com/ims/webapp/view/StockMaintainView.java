package com.ims.webapp.view;

import com.ims.domain.stock.ProductStockInfo;
import com.ims.domain.stock.StockType;
import com.ims.service.ProductStockInfoService;
import com.ims.webapp.view.criteria.CompareCode;
import com.ims.webapp.view.criteria.ProdStockSearchCriteria;
import com.ims.webapp.view.dto.ProductStockAmountDTO;
import com.ims.webapp.view.dto.ProductStockInfoDTO;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.event.RowEditEvent;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@ManagedBean(name = "stockMaintainView")
@ViewScoped
public class StockMaintainView extends BaseView {
    private List<ProductStockInfoDTO> stockInfoDTOList = new ArrayList<ProductStockInfoDTO>();
    private ProdStockSearchCriteria stockSearchCriteria = new ProdStockSearchCriteria();
    private ProductStockAmountDTO newStockAmount = new ProductStockAmountDTO();

    private ProductStockInfo selectedProductStock;

    public ProdStockSearchCriteria getStockSearchCriteria() {
        return stockSearchCriteria;
    }

    public void setStockSearchCriteria(ProdStockSearchCriteria stockSearchCriteria) {
        this.stockSearchCriteria = stockSearchCriteria;
    }

    public void loadData() {
//        stockSearchCriteria.setStockType(StockType.Semifinished.getCode());
//        semiProdStockList = this.productStockInfoService.findProdStockInfoListFrom(stockSearchCriteria);
//        stockSearchCriteria.setStockType(StockType.Finished.getCode());
//        this.endProdStockList = this.productStockInfoService.findProdStockInfoListFrom(stockSearchCriteria);
        prodCategoryList = this.supportingDataService.loadProdCategoryList(false);
        Map<String, ProductStockInfo> semiStockMap = this.productStockInfoService.getProductStockMapWithProdCodeKey( new ProdStockSearchCriteria(StockType.Semifinished.getCode()));
        Map<String, ProductStockInfo> endStockMap = this.productStockInfoService.getProductStockMapWithProdCodeKey( new ProdStockSearchCriteria(StockType.Finished.getCode()));
        buildStockInfoDTO(semiStockMap,endStockMap);
    }

    private void buildStockInfoDTO(Map<String, ProductStockInfo> semiStockMap, Map<String, ProductStockInfo> endStockMap) {
               boolean semiProdStock = ProductStockInfoService.SEMI_PROD_STOCK.equalsIgnoreCase(getMenuCode());
        for (String prodCode : endStockMap.keySet()) {
            ProductStockInfoDTO stockInfoDTO = new ProductStockInfoDTO(semiProdStock ? StockType.Semifinished.getCode() : StockType.Finished.getCode());
            ProductStockInfo targetStock = semiProdStock ? semiStockMap.get(prodCode) : endStockMap.get(prodCode);
            ProductStockInfo relatedStock = semiProdStock ? endStockMap.get(prodCode) : semiStockMap.get(prodCode);
            if (targetStock != null) {
                stockInfoDTO.setTargetProductStock(targetStock);
                stockInfoDTO.setRelatedProductStock(relatedStock);
                stockInfoDTOList.add(stockInfoDTO);
            }
        }
    }

    public void filterProductStockList(){
        stockInfoDTOList.clear();
        stockSearchCriteria.setStockType(StockType.Semifinished.getCode());
        stockSearchCriteria.setIncludeComparedValue(ProductStockInfoService.SEMI_PROD_STOCK.equalsIgnoreCase(getMenuCode()));
        Map<String, ProductStockInfo> semiStockMap = this.productStockInfoService.getProductStockMapWithProdCodeKey(stockSearchCriteria);

        stockSearchCriteria.setStockType(StockType.Finished.getCode());
        stockSearchCriteria.setIncludeComparedValue(ProductStockInfoService.END_PROD_STOCK.equalsIgnoreCase(getMenuCode()));
        Map<String, ProductStockInfo> endStockMap = this.productStockInfoService.getProductStockMapWithProdCodeKey(stockSearchCriteria);
        buildStockInfoDTO(semiStockMap,endStockMap);
    }

    public void enableCompareValueInput(){
        stockSearchCriteria.setRequireCompareValue(StringUtils.isNotEmpty(stockSearchCriteria.getCompareCode()));
    }

    public void editProdStock(RowEditEvent event) {
        ProductStockInfoDTO stockInfoDTO = (ProductStockInfoDTO) event.getObject();
        FacesMessage msg = new FacesMessage(stockInfoDTO.getTargetProductStock().getProductCode() + " 库存数量成功更新", stockInfoDTO.getTargetProductStock().getProductCode() + " 库存数量成功更新");
        this.productStockInfoService.updateProdcutStockInfo(stockInfoDTO);
        System.out.println(stockInfoDTO.getTargetProductStock());
        stockInfoDTO.getTargetProductStock().setVersion(stockInfoDTO.getTargetProductStock().getVersion() + 1);
        stockInfoDTO.getRelatedProductStock().setVersion(stockInfoDTO.getRelatedProductStock().getVersion() + 1);
        stockInfoDTO.resetNewStockAmount();
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("库存更新", "库存更新已被取消");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public List<ProductStockInfoDTO> getStockInfoDTOList() {
        return stockInfoDTOList;
    }

    public void setStockInfoDTOList(List<ProductStockInfoDTO> stockInfoDTOList) {
        this.stockInfoDTOList = stockInfoDTOList;
    }

    public ProductStockInfo getSelectedProductStock() {
        return selectedProductStock;
    }

    public void setSelectedProductStock(ProductStockInfo selectedProductStock) {
        if(selectedProductStock!=null && StringUtils.isNotEmpty(this.getAction()) && ACTION_CONVERT_STOCK.equalsIgnoreCase(this.getAction())){
            newStockAmount.setStockAmount(selectedProductStock.getStockAmount());
            newStockAmount.setSize10Amount(selectedProductStock.getSize10Amount());
            newStockAmount.setSize9Amount(selectedProductStock.getSize9Amount());
            newStockAmount.setSize8Amount(selectedProductStock.getSize8Amount());
            newStockAmount.setSize7Amount(selectedProductStock.getSize7Amount());
            newStockAmount.setSize4Amount(selectedProductStock.getSize4Amount());
            newStockAmount.setSize5Amount(selectedProductStock.getSize5Amount());
            newStockAmount.setSize6Amount(selectedProductStock.getSize6Amount());
        }
        this.selectedProductStock = selectedProductStock;
    }

    public String convertToFinishedProd(){
        System.out.println("selected stock=" + selectedProductStock);

        return null;
    }

    private void updateStockAmount(ProductStockInfo selectedProductStock,ProductStockAmountDTO newStockAmount){

    }

    public ProductStockAmountDTO getNewStockAmount() {
        return newStockAmount;
    }

    public void setNewStockAmount(ProductStockAmountDTO newStockAmount) {
        this.newStockAmount = newStockAmount;
    }

    public String transformStock(){
        if(StringUtils.isEmpty(stockSearchCriteria.getCompareCode())){
            stockSearchCriteria.setCompareCode(CompareCode.GREATER.getCode());
            stockSearchCriteria.setStockAmount(0);
        }
        stockSearchCriteria.setTransformAction(true);
        stockSearchCriteria.setIncludeComparedValue(true);
        filterProductStockList();

        productStockInfoService.transformStock(stockInfoDTOList);
        System.out.println("stockInfoDTOList size=" + stockInfoDTOList.size());
        FacesMessage msg = new FacesMessage("批量转换半成品", "一共有 "+stockInfoDTOList.size() +" 种半成品库存被成功转换为成品库存。");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        return null;
    }
}
