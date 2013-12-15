/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ims.domain.order;

import javax.persistence.*;

/**
 * @author Administrator
 */
@Entity
@Table(name = "ims_po_item")
public class PurchaseOrderItem extends OrderItem {

    @Column(name = "PO_ITEM_CODE")
    private String poItemCode;

    @Column(name = "PO_NUMBER")
    private String poNumber;

    @ManyToOne
    @JoinColumn(name = "PO_ID")
    private PurchaseOrder owner;

    public String getPoItemCode() {
        return poItemCode;
    }

    public void setPoItemCode(String poItemCode) {
        this.poItemCode = poItemCode;
    }

    public PurchaseOrder getOwner() {
        return owner;
    }

    public void setOwner(PurchaseOrder owner) {
        this.owner = owner;
    }

    public String getPoNumber() {
        return poNumber;
    }

    public void setPoNumber(String poNumber) {
        this.poNumber = poNumber;
    }

}
