/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ims.domain.order;

import com.ims.domain.PersistenceDomain;
import com.ims.domain.support.ProductAmount;
import com.ims.domain.support.ProductInfo;

import javax.persistence.Column;
import javax.persistence.Embedded;
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

    @Embedded
    private ProductAmount productAmount = new ProductAmount();

    @Column(name = "UNIT_PRICE")
    private double unitPrice;

    @Column(name = "TOTAL_PRICE")
    private double totalPrice;

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

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getDisplaySeq() {
        return displaySeq;
    }

    public void setDisplaySeq(int displaySeq) {
        this.displaySeq = displaySeq;
    }

    public ProductAmount getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(ProductAmount productAmount) {
        this.productAmount = productAmount;
    }

    public int getTotalAmountBy(boolean supportSize){
        if(supportSize){
            int total = this.productAmount.getSize4Amount()+this.productAmount.getSize5Amount()+this.productAmount.getSize6Amount()+this.productAmount.getSize7Amount()+this.productAmount.getSize8Amount()+this.productAmount.getSize9Amount()+this.productAmount.getSize10Amount();
            this.productAmount.setTotalAmount(total);
            return total;
        }else{
            return this.productAmount.getTotalAmount();
        }
    }
}
