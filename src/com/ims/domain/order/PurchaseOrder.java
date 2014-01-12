/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ims.domain.order;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Administrator
 */
@Entity
@Table(name = "ims_purchase_order")
public class PurchaseOrder extends OrderGeneralInfo {

    private static final long serialVersionUID = 1L;
    @Column(name = "PO_NUMBER")
    private String purchaseOrderNumber;

    @Column(name = "PO_STATUS")
    private String status;


    @Column(name = "DRAFT_PI_NUM")
    private String piNumber;

    @OneToMany(fetch = FetchType.LAZY)
    private Set<ProformaInvoice> associatedProformaInvoices = new HashSet<ProformaInvoice>();

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<PurchaseOrderItem> orderItems = new HashSet<PurchaseOrderItem>();

    public String getPurchaseOrderNumber() {
        return purchaseOrderNumber;
    }

    public void setPurchaseOrderNumber(String purchaseOrderNumber) {
        this.purchaseOrderNumber = purchaseOrderNumber;
    }

    public String getStatus() {
        return status;
    }

    public String getStatusDisplayString() {
        return OrderStatus.getDescription(status);
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Set<ProformaInvoice> getAssociatedProformaInvoices() {
        return associatedProformaInvoices;
    }

    public void setAssociatedProformaInvoices(Set<ProformaInvoice> associatedProformaInvoices) {
        this.associatedProformaInvoices = associatedProformaInvoices;
    }

    public Set<PurchaseOrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Set<PurchaseOrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public String getPiNumber() {
        return piNumber;
    }

    public void setPiNumber(String piNumber) {
        this.piNumber = piNumber;
    }

    public void addOrderItemToList(PurchaseOrderItem item) {
        item.setOwner(this);
        orderItems.add(item);
    }

    public void addToTotalPrice(double itemPrice) {
        this.totalPrice += itemPrice;
    }

    public List<PurchaseOrderItem> getOrderItemList(){
        List<PurchaseOrderItem> items = new ArrayList<PurchaseOrderItem>();
        for(PurchaseOrderItem item:this.getOrderItems()){
            items.add(item);
        }
        return items;
    }

    public void caculateTotalAmount(){
        double total = 0l;
        for(PurchaseOrderItem item:this.getOrderItemList()){
            total += item.getTotalPrice();
        }
        this.setTotalPrice(total);
    }
}
