/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ims.service;

import com.ims.domain.stock.ProductStockInfo;
import com.ims.domain.stock.StockType;
import com.ims.domain.support.ProductCategory;
import com.ims.domain.support.ProductInfo;
import com.ims.domain.support.ProductUnit;
import com.ims.repository.ProductCategoryRepository;
import com.ims.repository.ProductInfoRepository;
import com.ims.repository.ProductUnitRepository;
import com.ims.webapp.view.criteria.ProdSearchCriteria;
import com.ims.webapp.view.criteria.ProdStockSearchCriteria;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Transactional
@Service("supportingDataService")
public class SupportingDataService {
    public static final String MENU_CODE_PROD_CATEGORY = "prodC";
    public static final String MENU_CODE_PROD_UNIT = "prodU";
    public static final String MENU_CODE_PROD_INFO = "prodI";
    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Autowired
    private ProductUnitRepository productUnitRepository;

    @Autowired
    private ProductStockInfoService productStockInfoService;

    public List<ProductCategory> loadProdCategoryList(boolean loadProdInfo) {
        List<ProductCategory> results = productCategoryRepository.findAll();
        if (loadProdInfo) {
            for (ProductCategory category : results) {
                category.getProductList().size();
            }
        }
        return results;

    }

    public List<ProductUnit> loadProdProdUnitList() {
        return productUnitRepository.findAll();
    }

    public void addNewProductUnit(ProductUnit unit) {
        productUnitRepository.saveAndFlush(unit);
    }

    public void deleteProductUnit(ProductUnit unit) {
        this.productUnitRepository.delete(unit);
    }

    public void deleteProductCategory(ProductCategory category) {
        this.productCategoryRepository.delete(category);
    }

    public void addNewProductCategory(ProductCategory category) {
        this.productCategoryRepository.saveAndFlush(category);
    }

    public void updateCategoryList(List<ProductCategory> categoryList) {
        for (ProductCategory category : categoryList) {
            this.productCategoryRepository.saveAndFlush(category);
        }
    }


    public void updateProductInfo(ProductInfo productInfo) {
        this.productInfoRepository.saveAndFlush(productInfo);
    }

    public void deleteProductInfo(ProductInfo productInfo) {
        this.productInfoRepository.delete(productInfo.getId());
    }

    public void addProductInfo(ProductInfo productInfo) {
        this.productInfoRepository.saveAndFlush(productInfo);
    }


    public List<ProductInfo> findProductInfoListFrom(final ProdSearchCriteria prodSearchCriteria) {
        Specification<ProductInfo> speci = new Specification<ProductInfo>() {
            @Override
            public Predicate toPredicate(Root<ProductInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                if (StringUtils.isNotBlank(prodSearchCriteria.getProdCategoryCode())) {
                    predicates.add(cb.equal(root.get("categoryCode"), prodSearchCriteria.getProdCategoryCode()));
                }
                if (StringUtils.isNotEmpty(prodSearchCriteria.getProdCode())) {
                    predicates.add(cb.equal(root.get("productCode"), prodSearchCriteria.getProdCode()));
                }

                query.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
                return null;
            }
        };

        return productInfoRepository.findAll(speci);
    }

    public List<ProductInfo> getProductInfoList(ProdSearchCriteria prodSearchCriteria) {
        String categoryName = prodSearchCriteria.getProdCategoryCode();
        String prodCode = prodSearchCriteria.getProdCode();
        List<ProductInfo> productInfoList = new ArrayList<ProductInfo>();
        for (ProductCategory category : this.loadProdCategoryList(true)) {
            if (categoryName != null && category.getCategoryName().equalsIgnoreCase(categoryName)) {
                if (prodCode == null) {
                    return category.getProductList();
                } else {
                    productInfoList = new ArrayList<ProductInfo>();
                    for (ProductInfo prod : category.getProductList()) {
                        if (prod.getProductCode().contains(prodCode)) {
                            productInfoList.add(prod);
                        }
                    }
                    return productInfoList;
                }

            } else {
                productInfoList.addAll(category.getProductList());
            }
        }
        return productInfoList;
    }

    public void syncProdInfo2ProdStock(List<ProductInfo> productInfos) {
        if (productInfos == null) {
            productInfos = this.productInfoRepository.findAll();
        }
        for (StockType stockType : StockType.values()) {
            ProdStockSearchCriteria stockSearchCriteria = new ProdStockSearchCriteria(stockType.getCode());
            Map<String, ProductStockInfo> productStockInfoMap = productStockInfoService.getProductStockMapWithProdCodeKey(stockSearchCriteria);
            List<ProductStockInfo> newProductStockInfos = new ArrayList<ProductStockInfo>();
            List<ProductStockInfo> updatedProductStockInfos = new ArrayList<ProductStockInfo>();
            for (ProductInfo productInfo : productInfos) {
                ProductStockInfo productStockInfo = productStockInfoMap.get(productInfo.getProductCode());
                if (productStockInfo != null) {
                    productStockInfo.updateFromProd(productInfo);
                    updatedProductStockInfos.add(productStockInfo);
                } else {
                    newProductStockInfos.add(ProductStockInfo.buildStockInfoFromProd(productInfo,stockType));
                }
            }
            this.productStockInfoService.saveProductStockInfo(newProductStockInfos);
            this.productStockInfoService.saveProductStockInfo(updatedProductStockInfos);
        }
    }
}
