package com.ims.webapp.view;

import com.ims.domain.stock.ProductStockInfo;
import com.ims.domain.stock.StockType;
import com.ims.domain.support.ProductAmount;
import com.ims.domain.support.ProductCategory;
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
    private List<ProductStockInfo> alertedStockInfoList = new ArrayList<ProductStockInfo>();
    private List<ProductStockInfo> alertedSemiStockInfoList = new ArrayList<ProductStockInfo>();
    private List<ProductStockInfo> alertedEndStockInfoList = new ArrayList<ProductStockInfo>();

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
        Map<String, ProductStockInfo> semiStockMap = this.productStockInfoService.getProductStockMapWithProdCodeKey(new ProdStockSearchCriteria(StockType.Semifinished.getCode()));
        Map<String, ProductStockInfo> endStockMap = this.productStockInfoService.getProductStockMapWithProdCodeKey(new ProdStockSearchCriteria(StockType.Finished.getCode()));
        buildStockInfoDTO(semiStockMap, endStockMap);
    }

    public void loadAlertData(){
        ProdStockSearchCriteria criteria = new ProdStockSearchCriteria();
        criteria.setStockType(StockType.Semifinished.getCode());
        criteria.setIncludeComparedValue(true);
        criteria.setCompareCode(CompareCode.LESS_ALERT.getCode());
        Map<String, ProductStockInfo> semiStockMap = this.productStockInfoService.getProductStockMapWithProdCodeKey(criteria);
        criteria.setStockType(StockType.Finished.getCode());
        Map<String, ProductStockInfo> endStockMap = this.productStockInfoService.getProductStockMapWithProdCodeKey(criteria);
        Map<String, ProductCategory> categoryMap = this.supportingDataService.loadProdCategoryMap();
        for(String prodCode: semiStockMap.keySet()){
            ProductStockInfo stockInfo = semiStockMap.get(prodCode);
            ProductCategory category = categoryMap.get(stockInfo.getCategoryCode());
            stockInfo.setCategory(category);
            alertedSemiStockInfoList.add(stockInfo);
        }

        for(String prodCode: endStockMap.keySet()){
            ProductStockInfo stockInfo = endStockMap.get(prodCode);
            ProductCategory category = categoryMap.get(stockInfo.getCategoryCode());
            stockInfo.setCategory(category);
            alertedEndStockInfoList.add(stockInfo);
        }
    }



    private void buildStockInfoDTO(Map<String, ProductStockInfo> semiStockMap, Map<String, ProductStockInfo> endStockMap) {
        boolean semiProdStock = ProductStockInfoService.SEMI_PROD_STOCK.equalsIgnoreCase(getMenuCode());
        Map<String, ProductCategory> categoryMap = this.supportingDataService.loadProdCategoryMap();
        for (String prodCode : endStockMap.keySet()) {
            ProductStockInfoDTO stockInfoDTO = new ProductStockInfoDTO(semiProdStock ? StockType.Semifinished.getCode() : StockType.Finished.getCode());
            ProductStockInfo targetStock = semiProdStock ? semiStockMap.get(prodCode) : endStockMap.get(prodCode);
            ProductStockInfo relatedStock = semiProdStock ? endStockMap.get(prodCode) : semiStockMap.get(prodCode);
            if (targetStock != null) {
                ProductCategory category = categoryMap.get(targetStock.getCategoryCode());
                targetStock.setCategory(category);
                relatedStock.setCategory(category);
                stockInfoDTO.setProductCode(prodCode);
                stockInfoDTO.setTargetProductStock(targetStock);
                stockInfoDTO.setRelatedProductStock(relatedStock);
                stockInfoDTOList.add(stockInfoDTO);
            }
        }
    }

    public void filterProductStockList() {
        stockInfoDTOList.clear();
        stockSearchCriteria.setStockType(StockType.Semifinished.getCode());
        stockSearchCriteria.setIncludeComparedValue(ProductStockInfoService.SEMI_PROD_STOCK.equalsIgnoreCase(getMenuCode()));
        Map<String, ProductStockInfo> semiStockMap = this.productStockInfoService.getProductStockMapWithProdCodeKey(stockSearchCriteria);

        stockSearchCriteria.setStockType(StockType.Finished.getCode());
        stockSearchCriteria.setIncludeComparedValue(ProductStockInfoService.END_PROD_STOCK.equalsIgnoreCase(getMenuCode()));
        Map<String, ProductStockInfo> endStockMap = this.productStockInfoService.getProductStockMapWithProdCodeKey(stockSearchCriteria);
        buildStockInfoDTO(semiStockMap, endStockMap);
    }

    public void enableCompareValueInput() {
        String compareCode = stockSearchCriteria.getCompareCode();
        stockSearchCriteria.setRequireCompareValue(StringUtils.isNotEmpty(compareCode) && (CompareCode.isEqual(compareCode) || CompareCode.isGreater(compareCode) || CompareCode.isGreaterOrEqual(compareCode) || CompareCode.isLess(compareCode) || CompareCode.isLessOrEqual(compareCode)));
    }

    public void editProdStock(RowEditEvent event) {
        ProductStockInfoDTO stockInfoDTO = (ProductStockInfoDTO) event.getObject();
        FacesMessage msg = new FacesMessage(stockInfoDTO.getTargetProductStock().getProductCode() + " 库存数量成功更新", stockInfoDTO.getTargetProductStock().getProductCode() + " 库存数量成功更新");
        this.productStockInfoService.updateProdcutStockInfo(stockInfoDTO,false);
        System.out.println(stockInfoDTO.getTargetProductStock());
        if (stockInfoDTO.getTargetStockType() == StockType.Finished.getCode()){
            stockInfoDTO.getTargetProductStock().setVersion(stockInfoDTO.getTargetProductStock().getVersion() + 1);
            stockInfoDTO.getRelatedProductStock().setVersion(stockInfoDTO.getRelatedProductStock().getVersion() + 1);
        }else{
            stockInfoDTO.getTargetProductStock().setVersion(stockInfoDTO.getTargetProductStock().getVersion() + 1);
        }

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
        if (selectedProductStock != null && StringUtils.isNotEmpty(this.getAction()) && ACTION_CONVERT_STOCK.equalsIgnoreCase(this.getAction())) {
            ProductAmount selectedProdAmount = selectedProductStock.getProductAmount();
            newStockAmount.setStockAmount(selectedProdAmount.getTotalAmount());
            newStockAmount.setSize10Amount(selectedProdAmount.getSize10Amount());
            newStockAmount.setSize9Amount(selectedProdAmount.getSize9Amount());
            newStockAmount.setSize8Amount(selectedProdAmount.getSize8Amount());
            newStockAmount.setSize7Amount(selectedProdAmount.getSize7Amount());
            newStockAmount.setSize4Amount(selectedProdAmount.getSize4Amount());
            newStockAmount.setSize5Amount(selectedProdAmount.getSize5Amount());
            newStockAmount.setSize6Amount(selectedProdAmount.getSize6Amount());
        }
        this.selectedProductStock = selectedProductStock;
    }

    public String convertToFinishedProd() {
        System.out.println("selected stock=" + selectedProductStock);
        ProductStockInfoDTO targetStockDTO = null;
        for(ProductStockInfoDTO stockInfoDTO: stockInfoDTOList){
            if(stockInfoDTO.getProductCode().equalsIgnoreCase(selectedProductStock.getProductCode())){
                targetStockDTO = stockInfoDTO;
                break;
            }
        }
        targetStockDTO.setNewStockAmount(this.newStockAmount);
        this.productStockInfoService.updateProdcutStockInfo(targetStockDTO, true);
        targetStockDTO.getTargetProductStock().setVersion(targetStockDTO.getTargetProductStock().getVersion() + 1);
        targetStockDTO.getRelatedProductStock().setVersion(targetStockDTO.getRelatedProductStock().getVersion() + 1);
        targetStockDTO.resetNewStockAmount();
        FacesMessage msg = new FacesMessage("半成品转换", "半成品转换成品库存成功保存");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        return null;
    }

    private void updateStockAmount(ProductStockInfo selectedProductStock, ProductStockAmountDTO newStockAmount) {

    }

    public ProductStockAmountDTO getNewStockAmount() {
        return newStockAmount;
    }

    public void setNewStockAmount(ProductStockAmountDTO newStockAmount) {
        this.newStockAmount = newStockAmount;
    }

    public String transformStock() {
        if (StringUtils.isEmpty(stockSearchCriteria.getCompareCode())) {
            stockSearchCriteria.setCompareCode(CompareCode.GREATER.getCode());
            stockSearchCriteria.setStockAmount(0);
        }
        stockSearchCriteria.setTransformAction(true);
        stockSearchCriteria.setIncludeComparedValue(true);
        filterProductStockList();

        productStockInfoService.transformStock(stockInfoDTOList);
        System.out.println("stockInfoDTOList size=" + stockInfoDTOList.size());
        FacesMessage msg = new FacesMessage("批量转换半成品", "一共有 " + stockInfoDTOList.size() + " 种半成品库存被成功转换为成品库存。");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        return null;
    }

    public String updateStockAlertAmount() {
        int count = productStockInfoService.updateStockAlertAmount(stockSearchCriteria);
        FacesMessage msg = new FacesMessage("批量设置库存预警量", "一共有 " + count + " 种产品成功设置预警量。");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        return null;
    }

    public String retrieveStockListLessThanAlertAmount() {
        alertedStockInfoList = productStockInfoService.getAlertedStockInfoList(0);
        return null;
    }

    public List<ProductStockInfo> getAlertedStockInfoList() {
        return alertedStockInfoList;
    }

    public void setAlertedStockInfoList(List<ProductStockInfo> alertedStockInfoList) {
        this.alertedStockInfoList = alertedStockInfoList;
    }

    public List<ProductStockInfo> getAlertedSemiStockInfoList() {
        return alertedSemiStockInfoList;
    }

    public void setAlertedSemiStockInfoList(List<ProductStockInfo> alertedSemiStockInfoList) {
        this.alertedSemiStockInfoList = alertedSemiStockInfoList;
    }

    public List<ProductStockInfo> getAlertedEndStockInfoList() {
        return alertedEndStockInfoList;
    }

    public void setAlertedEndStockInfoList(List<ProductStockInfo> alertedEndStockInfoList) {
        this.alertedEndStockInfoList = alertedEndStockInfoList;
    }
}
