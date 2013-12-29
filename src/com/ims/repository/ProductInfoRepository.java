/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ims.repository;

import com.ims.domain.support.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Administrator
 */
public interface ProductInfoRepository extends JpaRepository<ProductInfo, Long>, JpaSpecificationExecutor<ProductInfo> {
    @Query("select u from ProductInfo u order by u.categoryCode asc ")
    public List<ProductInfo> getAllProductInfo();
}
