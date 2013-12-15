/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ims.domain.order;

import com.ims.domain.PersistenceDomain;
import com.ims.domain.support.ProductInfo;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

/**
 * @author Administrator
 */
@MappedSuperclass
public abstract class OrderItem extends PersistenceDomain {

    @Column(name = "CUST_PROD_CODE")
    private String customerProductCode;

    @Column(name = "COMP_PROD_CODE")
    private String companyProductCode;

    @Transient
    private ProductInfo productInfo;

    @Column(name = "COMP_PROD_SIZE")
    private int productSize;

    @Column(name = "QUANTITY")
    private int quantity;

    @Column(name = "UNIT_PRICE")
    private double unitPrice;

    @Column(name = "TOTAL_AMOUNT")
    private double totalAmount;

    @Column(name = "DISPLAY_SEQ")
    private int displaySeq;

    public String getCustomerProductCode() {
        return customerProductCode;
    }

    public void setCustomerProductCode(String customerProductCode) {
        this.customerProductCode = customerProductCode;
    }

    public String getCompanyProductCode() {
        return companyProductCode;
    }

    public void setCompanyProductCode(String companyProductCode) {
        this.companyProductCode = companyProductCode;
    }

    public ProductInfo getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(ProductInfo productInfo) {
        this.productInfo = productInfo;
    }

    public int getProductSize() {
        return productSize;
    }

    public void setProductSize(int productSize) {
        this.productSize = productSize;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getDisplaySeq() {
        return displaySeq;
    }

    public void setDisplaySeq(int displaySeq) {
        this.displaySeq = displaySeq;
    }

}
