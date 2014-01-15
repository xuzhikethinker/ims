/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ims.domain.order;

import com.ims.webapp.view.util.NumberUtil;

import javax.persistence.*;
import java.util.*;

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

    @Transient
    private List<PurchaseOrderItem> orderItemList = new ArrayList<PurchaseOrderItem>();

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

    public List<PurchaseOrderItem> getOrderItemList() {
        if (this.orderItemList.isEmpty()) {
            this.orderItemList.addAll(this.orderItems);
            Collections.sort(this.orderItemList, new Comparator<PurchaseOrderItem>() {
                @Override
                public int compare(PurchaseOrderItem o1, PurchaseOrderItem o2) {
                    return o1.getDisplaySeq() - o2.getDisplaySeq();
                }
            });
        }
        return orderItemList;
    }

    public void caculateTotalAmount() {
        double total = 0l;
        for (PurchaseOrderItem item : this.getOrderItemList()) {
            total += item.getTotalPrice();
        }
        this.setTotalPrice(NumberUtil.formatDoubleWith2Decimal(total));
    }

    public int getNextDisplaySeq() {
        int seq = 0;
        for (PurchaseOrderItem item : this.getOrderItemList()) {
            seq = seq < item.getDisplaySeq() ? item.getDisplaySeq() : seq;
        }
        return seq + 1;
    }
}
