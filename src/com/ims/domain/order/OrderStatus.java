/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ims.domain.order;

/**
 * @author Administrator
 */
public enum OrderStatus {

    PO_DRAFT("PO_Draft", "草稿状态-PO刚被创建",true),
    PO_CONFIRMED("PO_Confirmed", "确认状态-客户已经确认",true),
    PO_CANCELLED("PO_Cancelled", "删除状态-PO已被删除",true),
    PI_GENERATED("PI_Generated", "草稿状态-PI刚从PO创建出来",false),
    PI_CONFIRMED("PI_Confirmed", "确认状态-PI已经被确认",false),
    PI_CANCELLED("PI_Confirmed", "确认状态-PI已经被确认",false);

    private String code;
    private String description;
    private boolean poStatus;

    private OrderStatus(String code, String description, boolean poStatus) {
        this.code = code;
        this.description = description;
        this.poStatus = poStatus;
    }

    public String getCode() {
        return code;
    }

    public boolean isPoStatus() {
        return poStatus;
    }

    public String getDescription() {
        return description;
    }
}
