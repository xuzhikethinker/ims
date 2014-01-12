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

/**
 * @author Administrator
 */
@MappedSuperclass
public class OrderGeneralInfo extends PersistenceDomain {

    @Column(name = "CUSTOMER_NAME")
    protected String customerName;

    @Column(name = "SHIP_TO")
    protected String shipTo;

    @Column(name = "CUSTOMER_CODE")
    protected String customerCode;

    @Column(name = "ISSUER")
    protected String issuer;
    @Column(name = "TOTAL_PRICE")
    protected double totalPrice;

    @Embedded
    protected ContactInfo contact;

    @Column(name = "PO_REQUIREMENT_REMARKS")
    protected String orderRequirementRemarks;

    @Column(name = "ORDER_SUBMIT_DATE")
    protected String orderSubmitDate;

    @Column(name = "ORDER_DELIVERY_DATE")
    protected String deliveryDate;

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

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getShipTo() {
        return shipTo;
    }

    public void setShipTo(String shipTo) {
        this.shipTo = shipTo;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }
}
