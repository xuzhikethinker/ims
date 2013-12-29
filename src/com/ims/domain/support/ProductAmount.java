package com.ims.domain.support;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ProductAmount {
    @Column(name = "SIZE_4_AMOUNT")
    private int size4Amount;

    @Column(name = "SIZE_5_AMOUNT")
    private int size5Amount;

    @Column(name = "SIZE_6_AMOUNT")
    private int size6Amount;

    @Column(name = "SIZE_7_AMOUNT")
    private int size7Amount;

    @Column(name = "SIZE_8_AMOUNT")
    private int size8Amount;

    @Column(name = "SIZE_9_AMOUNT")
    private int size9Amount;

    @Column(name = "SIZE_10_AMOUNT")
    private int size10Amount;

    @Column(name = "TOTAL_AMOUNT")
    private int totalAmount;

    public int getSize4Amount() {
        return size4Amount;
    }

    public void setSize4Amount(int size4Amount) {
        this.size4Amount = size4Amount;
    }

    public int getSize5Amount() {
        return size5Amount;
    }

    public void setSize5Amount(int size5Amount) {
        this.size5Amount = size5Amount;
    }

    public int getSize6Amount() {
        return size6Amount;
    }

    public void setSize6Amount(int size6Amount) {
        this.size6Amount = size6Amount;
    }

    public int getSize7Amount() {
        return size7Amount;
    }

    public void setSize7Amount(int size7Amount) {
        this.size7Amount = size7Amount;
    }

    public int getSize8Amount() {
        return size8Amount;
    }

    public void setSize8Amount(int size8Amount) {
        this.size8Amount = size8Amount;
    }

    public int getSize9Amount() {
        return size9Amount;
    }

    public void setSize9Amount(int size9Amount) {
        this.size9Amount = size9Amount;
    }

    public int getSize10Amount() {
        return size10Amount;
    }

    public void setSize10Amount(int size10Amount) {
        this.size10Amount = size10Amount;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void updateTotalAmount(int uploadedAmount){
        totalAmount = size4Amount+size5Amount+size6Amount+size7Amount+size8Amount+size9Amount+size10Amount;
        if(totalAmount==0){
            totalAmount = uploadedAmount;
        }
    }
}
