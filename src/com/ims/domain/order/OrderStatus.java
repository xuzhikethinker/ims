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

    PO_DRAFT("PO_Draft", "草稿状态-PO刚被创建", true, false),
    PO_CONFIRMED("PO_Confirmed", "确认状态-客户已经确认，可以创建出货PI", true, true),
    PO_CREATING_PI("PIs_Creating", "关联部分出货PI-PO创建了部分出货PI", true, true),
    PO_COPMLETED("PO_Completed", "结束状态-PO创建了所有出货PI", true, false),
    PO_CANCELLED("PO_Cancelled", "删除状态-PO已被删除", true, false),
    PI_GENERATED("PI_Generated", "草稿状态-PI刚从PO创建出来", false, false),
    PI_CONFIRMED("PI_Confirmed", "确认状态-PI已经被确认", false, false),
    PI_CANCELLED("PI_Cancelled", "删除状态-PI已经被删除", false, false);

    private String code;
    private String description;
    private boolean poStatus;
    private boolean allowCreatePI;

    private OrderStatus(String code, String description, boolean poStatus, boolean allowCreatePI) {
        this.code = code;
        this.description = description;
        this.poStatus = poStatus;
        this.allowCreatePI = allowCreatePI;
    }

    public static String getDescription(String code) {
        for (OrderStatus status : OrderStatus.values()) {
            if (status.getCode().equalsIgnoreCase(code)) {
                return status.getDescription();
            }
        }
        return "";
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

    public boolean isAllowCreatePI() {
        return allowCreatePI;
    }

    public void setAllowCreatePI(boolean allowCreatePI) {
        this.allowCreatePI = allowCreatePI;
    }
}
