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
@Table(name = "ims_pi_item")
public class ProformaInvoiceItem extends OrderItem {

    @Column(name = "PO_ITEM_CODE")
    private String poItemCode;

    @Column(name = "PI_ITEM_CODE")
    private String piItemCode;

    @Column(name = "PI_NUMBER")
    private String piNumber;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "PI_ID")
    private ProformaInvoice owner;

    public String getPoItemCode() {
        return poItemCode;
    }

    public void setPoItemCode(String poItemCode) {
        this.poItemCode = poItemCode;
    }

    public String getPiItemCode() {
        return piItemCode;
    }

    public void setPiItemCode(String piItemCode) {
        this.piItemCode = piItemCode;
    }

    public String getPiNumber() {
        return piNumber;
    }

    public void setPiNumber(String piNumber) {
        this.piNumber = piNumber;
    }

    public ProformaInvoice getOwner() {
        return owner;
    }

    public void setOwner(ProformaInvoice owner) {
        this.owner = owner;
    }

}
