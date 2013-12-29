/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ims.domain.order;

import com.ims.domain.ContactInfo;
import com.ims.domain.PersistenceDomain;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;

/**
 * @author Administrator
 */
@MappedSuperclass
public class OrderGeneralInfo extends PersistenceDomain {

    @Column(name = "CUSTOMER_NAME")
    private String customerName;

    @Column(name = "CUSTOMER_CODE")
    private String customerCode;

    @Column(name = "TOTAL_AMOUNT")
    private double totalAmount;

    @Embedded
    private ContactInfo contact;

    @Column(name = "PO_REQUIREMENT_REMARKS")
    private String orderRequirementRemarks;

    @Column(name = "ORDER_SUBMIT_DATE")
    private String orderSubmitDate;

    @Column(name = "ORDER_DELIVERY_DATE")
    private String deliveryDate;

    public String getOrderRequirementRemarks() {
        return orderRequirementRemarks;
    }

    public void setOrderRequirementRemarks(String orderRequirementRemarks) {
        this.orderRequirementRemarks = orderRequirementRemarks;
    }

    public String getOrderSubmitDate() {
        return orderSubmitDate;
    }

    public void setOrderSubmitDate(String orderSubmitDate) {
        this.orderSubmitDate = orderSubmitDate;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public ContactInfo getContact() {
        return contact;
    }

    public void setContact(ContactInfo contact) {
        this.contact = contact;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

}
