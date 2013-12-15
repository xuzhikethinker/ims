/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ims.domain.order;

/**
 * @author Administrator
 */
public enum POStatus {

    PO_SUBMITTED("PO_Submitted", "PO is submitted"),
    PI_GENERATED("PI_Generated", "PI is generated for customer confirm"),
    PO_CANCELLED("PO_Cancelled", "PO is cancelled by user"),
    PI_CONFIRMED("PI_Confirmed", "PI is confirmed by customer with final price");

    private String code;
    private String description;

    private POStatus(String code, String description) {
        this.code = code;
        this.description = description;
    }
}
