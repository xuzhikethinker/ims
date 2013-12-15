/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ims.domain.order;

import javax.persistence.*;
import java.util.HashSet;
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

    @OneToMany(fetch = FetchType.LAZY)
    private Set<ShipmentProformaInvoice> associatedProformaInvoices = new HashSet<ShipmentProformaInvoice>();

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

    public void setStatus(String status) {
        this.status = status;
    }

    public Set<ShipmentProformaInvoice> getAssociatedProformaInvoices() {
        return associatedProformaInvoices;
    }

    public void setAssociatedProformaInvoices(Set<ShipmentProformaInvoice> associatedProformaInvoices) {
        this.associatedProformaInvoices = associatedProformaInvoices;
    }

    public Set<PurchaseOrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Set<PurchaseOrderItem> orderItems) {
        this.orderItems = orderItems;
    }

}
