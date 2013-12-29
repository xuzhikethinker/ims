/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ims.domain.order;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 出货PI，一个PO可以对应多个出货PI.出货PI和PO的样子差不多。
 *
 * @author Administrator
 */
@Entity
@Table(name = "ims_proforma_invoice")
public class ProformaInvoice extends OrderGeneralInfo {

    @Column(name = "PI_NUMBER")
    private String proformaInvoiceNumber;

    @Column(name = "PI_STATUS")
    private String status;

    @Column(name = "PO_NUMBER")
    private String purchaseOrderNumber;

    @Column(name = "PO_ID")
    private Long purchaseOrderID;

//    @ManyToOne
//    // 可选属性optional=false,表示company不能为空
//    @JoinColumn(name = "PO_ID")
//    private PurchaseOrder purchaseOrder;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<ProformaInvoiceItem> orderItems = new HashSet<ProformaInvoiceItem>();

    public String getProformaInvoiceNumber() {
        return proformaInvoiceNumber;
    }

    public void setProformaInvoiceNumber(String proformaInvoiceNumber) {
        this.proformaInvoiceNumber = proformaInvoiceNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

//    public PurchaseOrder getPurchaseOrder() {
//        return purchaseOrder;
//    }
//
//    public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
//        this.purchaseOrder = purchaseOrder;
//    }

    public Set<ProformaInvoiceItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Set<ProformaInvoiceItem> orderItems) {
        this.orderItems = orderItems;
    }

    public String getPurchaseOrderNumber() {
        return purchaseOrderNumber;
    }

    public void setPurchaseOrderNumber(String purchaseOrderNumber) {
        this.purchaseOrderNumber = purchaseOrderNumber;
    }

    public Long getPurchaseOrderID() {
        return purchaseOrderID;
    }

    public void setPurchaseOrderID(Long purchaseOrderID) {
        this.purchaseOrderID = purchaseOrderID;
    }

    public void addOrderItem(ProformaInvoiceItem newItem) {
        newItem.setOwner(this);
        this.orderItems.add(newItem);
        this.getChangeLog().setLastModifiedDate(new Date());
    }

}
