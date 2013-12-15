package com.ims.webapp.view.dto;

import com.ims.domain.stock.ProductStockInfo;

/**
 * Created by Administrator on 13-12-13.
 */
public class ProductStockInfoDTO {
    private int targetStockType = 0;
    private ProductStockInfo targetProductStock =new ProductStockInfo();
    private ProductStockInfo relatedProductStock=new ProductStockInfo();
    private ProductStockAmountDTO newStockAmount = new ProductStockAmountDTO();

    public ProductStockInfoDTO(int targetStockType) {
        this.targetStockType = targetStockType;
    }

    public void resetNewStockAmount(){
        newStockAmount = new ProductStockAmountDTO();
    }

    public int getTargetStockType() {
        return targetStockType;
    }

    public void setTargetStockType(int targetStockType) {
        this.targetStockType = targetStockType;
    }

    public ProductStockInfo getTargetProductStock() {
        return targetProductStock;
    }

    public void setTargetProductStock(ProductStockInfo targetProductStock) {
        this.targetProductStock = targetProductStock;
    }

    public ProductStockInfo getRelatedProductStock() {
        return relatedProductStock;
    }

    public void setRelatedProductStock(ProductStockInfo relatedProductStock) {
        this.relatedProductStock = relatedProductStock;
    }

    public ProductStockAmountDTO getNewStockAmount() {
        return newStockAmount;
    }

    public void setNewStockAmount(ProductStockAmountDTO newStockAmount) {
        this.newStockAmount = newStockAmount;
    }
}
