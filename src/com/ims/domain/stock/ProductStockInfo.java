/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ims.domain.stock;

import com.ims.domain.PersistenceDomain;
import com.ims.domain.support.ProductAmount;
import com.ims.domain.support.ProductCategory;
import com.ims.domain.support.ProductInfo;

import javax.persistence.*;

@Entity
@Table(name = "ims_product_stock")
public class ProductStockInfo extends PersistenceDomain {

    @Column(name = "CATEGORY_CODE")
    private String categoryCode;

    @Column(name = "PRODUCT_CODE")
    private String productCode;

    @Column(name = "PRODUCT_PICTURE")
    private String productPicture;

    @Embedded
    private ProductAmount productAmount = new ProductAmount();

    @Column(name = "STOCK_TYPE")
    private int stockType;//成品还是半成品

    @Column(name = "STOCK_ALERT_AMOUNT")
    private int alertStockAmount;

    @Transient
    private ProductCategory category;

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public int getStockType() {
        return stockType;
    }

    public void setStockType(int stockType) {
        this.stockType = stockType;
    }

    public int getStockAmount() {
        return isSupportSize() ? productAmount.getSize4Amount() + productAmount.getSize5Amount() + productAmount.getSize6Amount() + productAmount.getSize7Amount() + productAmount.getSize8Amount() + productAmount.getSize9Amount() + productAmount.getSize10Amount() : productAmount.getTotalAmount();
}

    public int getAlertStockAmount() {
        return alertStockAmount;
    }

    public void setAlertStockAmount(int alertStockAmount) {
        this.alertStockAmount = alertStockAmount;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public static ProductStockInfo buildStockInfoFromProd(ProductInfo productInfo, StockType stockType) {
        ProductStockInfo stockInfo = new ProductStockInfo();
        stockInfo.setStockType(stockType.getCode());
        stockInfo.setAlertStockAmount(10);
        stockInfo.setCategoryCode(productInfo.getCategoryCode());
        stockInfo.setProductCode(productInfo.getProductCode());
        stockInfo.setProductPicture(productInfo.getPictureName());
        return stockInfo;
    }

    public void updateFromProd(ProductInfo productInfo) {
        this.setProductPicture(productInfo.getPictureName());
        this.setCategoryCode(productInfo.getCategoryCode());
    }

    public String getProductPicture() {
        return productPicture;
    }

    public void setProductPicture(String productPicture) {
        this.productPicture = productPicture;
    }

    public boolean isSupportSize() {
        return ProductInfo.CATEGORY_EARRING.equalsIgnoreCase(this.categoryCode);
    }

    public ProductAmount getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(ProductAmount productAmount) {
        this.productAmount = productAmount;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }
}
