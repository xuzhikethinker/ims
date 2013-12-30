package com.ims.webapp.view.dto;

/**
 * Created by Administrator on 13-12-29.
 */
public class OrderStatusDTO {
    private String code;
    private String description;

    public OrderStatusDTO() {
    }

    public OrderStatusDTO(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
