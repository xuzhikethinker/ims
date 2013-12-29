package com.ims.webapp.view.criteria;

/**
 * Created by Administrator on 13-12-8.
 */
public class ProdSearchCriteria {
    private String prodCategoryCode;
    private String prodCode;
    private String custProdCode;

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

    public String getCustProdCode() {
        return custProdCode;
    }

    public void setCustProdCode(String custProdCode) {
        this.custProdCode = custProdCode;
    }

    @Override
    public String toString() {
        return "ProdSearchCriteria{" +
                "prodCategoryCode='" + prodCategoryCode + '\'' +
                ", prodCode='" + prodCode + '\'' +
                '}';
    }
}
