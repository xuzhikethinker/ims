/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ims.domain.order;

/**
 * @author Administrator
 */
public enum SPIStatus {

    SPI_CREATED("SPI_Created", "SPI is created from customer confirmed PO."),
    SPI_CANCELLED("SPI_Cancelled", "SPI is cancelled by operator."),
    SPI_CONFIRMED("SPI_Confirmed", "SPI is confirmed by operator.");

    private String code;
    private String description;

    private SPIStatus(String code, String description) {
        this.code = code;
        this.description = description;
    }
}
