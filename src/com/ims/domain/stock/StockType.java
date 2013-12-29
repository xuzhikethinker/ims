/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ims.domain.stock;

/**
 * @author Administrator
 */
public enum StockType {
    Semifinished(0, "Semifinished product"),
    Finished(1, "Finished product");

    private int code;
    private String description;

    private StockType(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }
}
