/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ims.domain.customer;

import com.ims.domain.ContactInfo;
import com.ims.domain.PersistenceDomain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 */
@Entity
@Table(name = "ims_customer_info")
public class CustomerInfo extends PersistenceDomain {

    @Column(name = "CUSTOMER_COMP_NAME")
    private String customerName;

    @Column(name = "CUSTOMER_CODE")
    private String customerCode;

    @Embedded
    private ContactInfo contact = new ContactInfo();

    //    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)//级联保存、更新、删除、刷新;延迟加载
//    @JoinColumn(name = "CUSTOMER_ID")//在book表增加一个外键列来实现一对多的单向关联
    @Transient
    private List<CustomerProductCodeMap> productCodeMap;

    public CustomerInfo() {
        this.productCodeMap = new ArrayList<CustomerProductCodeMap>();
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public ContactInfo getContact() {
        return contact;
    }

    public void setContact(ContactInfo contact) {
        this.contact = contact;
    }

    public List<CustomerProductCodeMap> getProductCodeMap() {
        return productCodeMap;
    }

    public void setProductCodeMap(List<CustomerProductCodeMap> productCodeMap) {
        this.productCodeMap = productCodeMap;
    }

    public void removeProductCodeMap(CustomerProductCodeMap codeMap) {
        this.productCodeMap.remove(codeMap);
        //codeMap.setCustomer(null);
    }

    @Override
    public String toString() {
        return "CustomerInfo{" + "customerName=" + customerName + ", customerCode=" + customerCode + ", contact=" + contact + ", productCodeMap=" + productCodeMap + '}';
    }

}
