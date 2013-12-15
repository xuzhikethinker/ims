/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ims.domain.support;

import com.ims.domain.PersistenceDomain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Administrator
 */
@Entity
@Table(name = "ims_product_unit")
public class ProductUnit extends PersistenceDomain {

    @Column(name = "UNIT_NAME")
    private String unitName;

    @Column(name = "UNIT_DESC")
    private String description;

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
