/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ims.domain.customer;

import com.ims.domain.PersistenceDomain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 客户产品编号与公司产品编号的对应
 *
 * @author Administrator
 */
@Entity
@Table(name = "ims_cust_product_map")
public class CustomerProductCodeMap extends PersistenceDomain {

    @Column(name = "CUST_PRODUCT_CODE")
    private String customerProductCode;

    @Column(name = "CUST_PRODUCT_SECOND_CODE")
    private String customerProductSecondCode;

    @Column(name = "COMP_PRODUCT_CODE")
    private String companyProductCode;

    //    @ManyToOne
//    @JoinColumn(name = "CUSTOMER_ID")
//    private CustomerInfo customer;
    public String getCustomerProductCode() {
        return customerProductCode;
    }

    public void setCustomerProductCode(String customerProductCode) {
        this.customerProductCode = customerProductCode;
    }

    public String getCustomerProductSecondCode() {
        return customerProductSecondCode;
    }

    public void setCustomerProductSecondCode(String customerProductSecondCode) {
        this.customerProductSecondCode = customerProductSecondCode;
    }

    public String getCompanyProductCode() {
        return companyProductCode;
    }

    public void setCompanyProductCode(String companyProductCode) {
        this.companyProductCode = companyProductCode;
    }

    //    public CustomerInfo getCustomer() {
//        return customer;
//    }
//
//    public void setCustomer(CustomerInfo customer) {
//        this.customer = customer;
//    }
    @Override
    public String toString() {
        return "CustomerProductCodeMap{" + "customerProductCode=" + customerProductCode + ", customerProductSecondCode=" + customerProductSecondCode + ", companyProductCode=" + companyProductCode + '}';
    }

}
