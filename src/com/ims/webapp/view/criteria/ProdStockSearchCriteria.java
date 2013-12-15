package com.ims.webapp.view.criteria;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 13-12-10.
 */
public class ProdStockSearchCriteria {
    private int stockType;
    private String prodCategoryCode;
    private String prodCode;
    private int alertStockAmount;
    private int stockAmount;
    private String compareCode;
    List<CompareCode> compareCodeList = new ArrayList<CompareCode>();
    private boolean requireCompareValue;
    private boolean includeComparedValue;

    public ProdStockSearchCriteria() {
    }

    public ProdStockSearchCriteria(int stockType) {
        this.stockType = stockType;
    }

    public int getStockType() {
        return stockType;
    }

    public void setStockType(int stockType) {
        this.stockType = stockType;
    }

    public String getProdCategoryCode() {
        return prodCategoryCode;
    }

    public void setProdCategoryCode(String prodCategoryCode) {
        this.prodCategoryCode = prodCategoryCode;
    }

    public String getProdCode() {
        return prodCode;
    }

    public void setProdCode(String prodCode) {
        this.prodCode = prodCode;
    }

    public int getAlertStockAmount() {
        return alertStockAmount;
    }

    public void setAlertStockAmount(int alertStockAmount) {
        this.alertStockAmount = alertStockAmount;
    }

    public int getStockAmount() {
        return stockAmount;
    }

    public void setStockAmount(int stockAmount) {
        this.stockAmount = stockAmount;
    }

    public String getCompareCode() {
        return compareCode;
    }

    public void setCompareCode(String compareCode) {
        this.compareCode = compareCode;
    }

//    public void setCompareCodeList(List<CompareCode> compareCodeList) {
//        this.compareCodeList = compareCodeList;
//    }

    public List<CompareCode> getCompareCodeList() {
        compareCodeList.clear();
        for (CompareCode compare : CompareCode.values()) {
            if (compare != CompareCode.EMPTY) {
                compareCodeList.add(compare);
            }
        }
        return compareCodeList;
    }

    public boolean isRequireCompareValue() {
        return requireCompareValue;
    }

    public void setRequireCompareValue(boolean requireCompareValue) {
        this.requireCompareValue = requireCompareValue;
    }

    public boolean isIncludeComparedValue() {
        return includeComparedValue;
    }

    public void setIncludeComparedValue(boolean includeComparedValue) {
        this.includeComparedValue = includeComparedValue;
    }
}
