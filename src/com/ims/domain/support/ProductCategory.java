/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ims.domain.support;

import com.ims.domain.PersistenceDomain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
@Entity
@Table(name = "ims_product_category")
public class ProductCategory extends PersistenceDomain {

    @Column(name = "CATEGORY_NAME")
    private String categoryName;

    @Column(name = "SUPPORT_SIZE")
    private boolean supportSize;

    @Column(name = "CATEGORY_DESC_CH")
    private String chineseDesc;

    @Column(name = "CATEGORY_DESC_EN")
    private String englishDesc;

    @OneToMany(mappedBy = "category", cascade = {CascadeType.REFRESH, CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    private List<ProductInfo> productList = new ArrayList<ProductInfo>();

    @Column(name = "CATEGORY_UNIT")
    private String unit;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public boolean isSupportSize() {
        return supportSize;
    }

    public void setSupportSize(boolean supportSize) {
        this.supportSize = supportSize;
    }

    public String getChineseDesc() {
        return chineseDesc;
    }

    public void setChineseDesc(String chineseDesc) {
        this.chineseDesc = chineseDesc;
    }

    public String getEnglishDesc() {
        return englishDesc;
    }

    public void setEnglishDesc(String englishDesc) {
        this.englishDesc = englishDesc;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public List<ProductInfo> getProductList() {
        return productList;
    }

    public void setProductList(List<ProductInfo> productList) {
        this.productList = productList;
    }

    public void addProductInfo(ProductInfo prod) {
        prod.setCategory(this);
        this.productList.add(prod);
    }

    public Map<String, ProductInfo> getProductInfoMap(){
        Map<String, ProductInfo> productInfoMap = new HashMap<String, ProductInfo>();
        for(ProductInfo productInfo:this.productList){
            productInfoMap.put(productInfo.getProductCode(),productInfo);
        }
        return productInfoMap;
    }

}
