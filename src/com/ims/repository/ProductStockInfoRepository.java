/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ims.repository;

import com.ims.domain.stock.ProductStockInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Administrator
 */
@Transactional
public interface ProductStockInfoRepository extends JpaRepository<ProductStockInfo, Long>, JpaSpecificationExecutor<ProductStockInfo> {
    @Modifying
    @Query("update ProductStockInfo s set s.alertStockAmount = ?1")
    public int updateStockAlertAmount(int alertAmount);

    @Modifying
    @Query("update ProductStockInfo s set s.alertStockAmount = ?2 where s.stockType = ?1")
    public int updateStockAlertAmount(int stockType,int alertAmount);

    @Modifying
    @Query("update ProductStockInfo s set s.alertStockAmount = ?2 where s.categoryCode = ?1")
    public int updateStockAlertAmount(String categoryCode,int alertAmount);

    @Modifying
    @Query("update ProductStockInfo s set s.alertStockAmount = ?3 where s.stockType = ?2 and s.categoryCode=?1")
    public int updateStockAlertAmount(String categoryCode, int stockType,int alertAmount);

    @Query("select u from ProductStockInfo u where u.productAmount.totalAmount<u.alertStockAmount and u.stockType=?1")
    public List<ProductStockInfo> getLessThanAlertStockAmount(int stockType);

}
