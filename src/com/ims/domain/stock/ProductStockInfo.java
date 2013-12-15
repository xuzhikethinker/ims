/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ims.domain.stock;

import com.ims.domain.PersistenceDomain;
import com.ims.domain.support.ProductInfo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Administrator
 */
@Entity
@Table(name = "ims_product_stock")
public class ProductStockInfo extends PersistenceDomain {

    @Column(name = "CATEGORY_CODE")
    private String categoryCode;

    @Column(name = "PRODUCT_CODE")
    private String productCode;

    @Column(name = "PRODUCT_PICTURE")
    private String productPicture;

    @Column(name = "SIZE_4_AMOUNT")
    private int size4Amount;

    @Column(name = "SIZE_5_AMOUNT")
    private int size5Amount;

    @Column(name = "SIZE_6_AMOUNT")
    private int size6Amount;

    @Column(name = "SIZE_7_AMOUNT")
    private int size7Amount;

    @Column(name = "SIZE_8_AMOUNT")
    private int size8Amount;

    @Column(name = "SIZE_9_AMOUNT")
    private int size9Amount;

    @Column(name = "SIZE_10_AMOUNT")
    private int size10Amount;

    @Column(name = "STOCK_TYPE")
    private int stockType;//成品还是半成品

    @Column(name = "STOCK_AMOUNT")
    private int stockAmount;

    @Column(name = "STOCK_ALERT_AMOUNT")
    private int alertStockAmount;

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
        return isSupportSize()? size4Amount+size5Amount+size6Amount+size7Amount+size8Amount+size9Amount+size10Amount:stockAmount;
    }

    public void setStockAmount(int stockAmount) {
        this.stockAmount = stockAmount;
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

    public int getSize4Amount() {
        return size4Amount;
    }

    public void setSize4Amount(int size4Amount) {
        this.size4Amount = size4Amount;
    }

    public int getSize5Amount() {
        return size5Amount;
    }

    public void setSize5Amount(int size5Amount) {
        this.size5Amount = size5Amount;
    }

    public int getSize6Amount() {
        return size6Amount;
    }

    public void setSize6Amount(int size6Amount) {
        this.size6Amount = size6Amount;
    }

    public int getSize7Amount() {
        return size7Amount;
    }

    public void setSize7Amount(int size7Amount) {
        this.size7Amount = size7Amount;
    }

    public int getSize8Amount() {
        return size8Amount;
    }

    public void setSize8Amount(int size8Amount) {
        this.size8Amount = size8Amount;
    }

    public int getSize9Amount() {
        return size9Amount;
    }

    public void setSize9Amount(int size9Amount) {
        this.size9Amount = size9Amount;
    }

    public int getSize10Amount() {
        return size10Amount;
    }

    public void setSize10Amount(int size10Amount) {
        this.size10Amount = size10Amount;
    }

    public String getProductPicture() {
        return productPicture;
    }

    public void setProductPicture(String productPicture) {
        this.productPicture = productPicture;
    }

    public boolean isSupportSize(){
        return ProductInfo.CATEGORY_EARRING.equalsIgnoreCase(this.categoryCode);
    }
}
