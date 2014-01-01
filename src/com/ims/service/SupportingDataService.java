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

import javax.persistence.criteria.*;
import java.util.*;

@Transactional
@Service("supportingDataService")
public class SupportingDataService extends BaseService {
    public static final String MENU_CODE_PROD_CATEGORY_UNIT = "prodCU";
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

    public Map<String, ProductCategory> loadProdCategoryMap() {
        List<ProductCategory> results = productCategoryRepository.findAll();
        Map<String, ProductCategory> categoryMap = new HashMap<String, ProductCategory>();
        for (ProductCategory category : results) {
            categoryMap.put(category.getCategoryName(), category);
        }
        return categoryMap;
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


    /**
     * 期望的SQL: select * from ims_product_info where CATEGORY_CODE='xxx' and PRODUCT_CODE like '%yyy%' and (CUST_PROD_CODE like '%aaa%' or CUST_PROD_SECOND_CODE like '%aaa%')
     * 有两种方式达到效果，如下
     *
     * @param prodSearchCriteria
     * @return
     */
    public List<ProductInfo> findProductInfoListFrom(final ProdSearchCriteria prodSearchCriteria) {
        Specification<ProductInfo> speci = new Specification<ProductInfo>() {
            @Override
            public Predicate toPredicate(Root<ProductInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                Path<String> custProdCode = root.<String>get(ProductInfo.CUST_PROD_CODE);
                Path<String> custProdCode2 = root.<String>get(ProductInfo.CUST_SEC_PROD_CODE);
                Predicate orClause = null;

                if (StringUtils.isNotBlank(prodSearchCriteria.getProdCategoryCode())) {
                    predicates.add(cb.equal(root.get(ProductInfo.CATEGORY_CODE), prodSearchCriteria.getProdCategoryCode()));
                }
                if (StringUtils.isNotEmpty(prodSearchCriteria.getProdCode())) {
                    predicates.add(cb.like(root.<String>get(ProductInfo.PRODUCT_CODE), getLikeString(prodSearchCriteria.getProdCode())));
                }
                if (StringUtils.isNotBlank(prodSearchCriteria.getCustProdCode())) {
                    //第一种方式，把or直接加入到predicates中，然后在where里面直接 query.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
                    //Predicate p1 = cb.or(cb.like(custProdCode, getLikeString(prodSearchCriteria.getCustProdCode())), cb.like(custProdCode2, getLikeString(prodSearchCriteria.getCustProdCode())));
                    //predicates.add(p1);

                    //第二种方式：构造一个or的Predicate，然后在where里面和前面的分开组合：query.where(cb.and(predicates.toArray(new Predicate[predicates.size()])),orClause);
                    String param = getLikeString(prodSearchCriteria.getCustProdCode());
                    orClause = cb.or(cb.like(custProdCode, param), cb.like(custProdCode2, param));
                }
                //select * from ims_product_info  where CATEGORY_CODE=? and (PRODUCT_CODE like ?) and (CUST_PROD_CODE like ? or CUST_PROD_SECOND_CODE like ?)
                //第一种方式
                //query.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));

                //第二种方式
                if (orClause != null) {
                    query.where(cb.and(predicates.toArray(new Predicate[predicates.size()])), orClause);
                } else {
                    query.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
                }
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
                    newProductStockInfos.add(ProductStockInfo.buildStockInfoFromProd(productInfo, stockType));
                }
            }
            this.productStockInfoService.saveProductStockInfo(newProductStockInfos);
            this.productStockInfoService.saveProductStockInfo(updatedProductStockInfos);
        }
    }

    public Map<String, ProductInfo> getProductInfoMap() {
        Map<String, ProductInfo> productInfoMap = new LinkedHashMap<String, ProductInfo>();
        for (ProductInfo productInfo : productInfoRepository.findAll()) {
            productInfoMap.put(productInfo.getProductCode(), productInfo);
        }
        return productInfoMap;
    }
}
