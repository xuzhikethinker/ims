package com.ims.webapp.view.dto;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-12-1
 * Time: 下午9:40
 * To change this template use File | Settings | File Templates.
 */
public class ProductInfoDTO {
    private String productInfoCategory;
    private String productInfoCode;
    private String productInfoImageName;

    public String getProductInfoCategory() {
        return productInfoCategory;
    }

    public void setProductInfoCategory(String productInfoCategory) {
        this.productInfoCategory = productInfoCategory;
    }

    public String getProductInfoCode() {
        return productInfoCode;
    }

    public void setProductInfoCode(String productInfoCode) {
        this.productInfoCode = productInfoCode;
    }

    public String getProductInfoImageName() {
        return productInfoImageName;
    }

    public void setProductInfoImageName(String productInfoImageName) {
        this.productInfoImageName = productInfoImageName;
    }
}
