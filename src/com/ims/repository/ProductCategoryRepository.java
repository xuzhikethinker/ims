/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ims.repository;

import com.ims.domain.support.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Administrator
 */
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {

}
