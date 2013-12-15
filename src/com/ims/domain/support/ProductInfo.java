/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ims.domain.support;

import com.ims.domain.PersistenceDomain;

import javax.persistence.*;

/**
 * @author Administrator
 */
@Entity
@Table(name = "ims_product_info")
public class ProductInfo extends PersistenceDomain {
    public static final String PRODUCT_CODE = "productCode";
    public static final String CATEGORY_CODE = "categoryCode";
    public static final String CATEGORY_EARRING = "Earring";
    @Column(name = "PRODUCT_NAME")
    private String productName;

    @Column(name = "PRODUCT_CODE")
    private String productCode;

    @Column(name = "CATEGORY_CODE")
    private String categoryCode;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "CATEGORY_ID")
    private ProductCategory category;

    @Column(name = "PRODUCT_SIZE")
    private int productSize;

    @Column(name = "PRODUCT_PRICE")
    private double price;

    @Column(name = "PRODUCT_PICTURE")
    private String pictureName;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    public int getProductSize() {
        return productSize;
    }

    public void setProductSize(int productSize) {
        this.productSize = productSize;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPictureName() {
        return pictureName;
    }

    public void setPictureName(String pictureName) {
        this.pictureName = pictureName;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

}
