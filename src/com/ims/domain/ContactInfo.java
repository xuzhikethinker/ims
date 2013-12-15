/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ims.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * @author Administrator
 */
@Embeddable
public class ContactInfo implements Serializable {

    @Column(name = "CONTACT_NAME")
    private String contactName;

    @Column(name = "PHONE_NUMBER")
    private String phoneNum;

    @Column(name = "FAX_NUMBER")
    private String faxNum;

    @Column(name = "CONTACT_ADDRESS")
    private String address;

    @Column(name = "EMAIL_ADDR")
    private String email;

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getFaxNum() {
        return faxNum;
    }

    public void setFaxNum(String faxNum) {
        this.faxNum = faxNum;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "ContactInfo{" + "contactName=" + contactName + ", phoneNum=" + phoneNum + ", faxNum=" + faxNum + ", address=" + address + ", email=" + email + '}';
    }

}
