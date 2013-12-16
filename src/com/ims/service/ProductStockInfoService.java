/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ims.service;

import com.ims.domain.stock.ProductStockInfo;
import com.ims.domain.stock.StockType;
import com.ims.repository.ProductStockInfoRepository;
import com.ims.webapp.view.criteria.CompareCode;
import com.ims.webapp.view.criteria.ProdStockSearchCriteria;
import com.ims.webapp.view.dto.ProductStockAmountDTO;
import com.ims.webapp.view.dto.ProductStockInfoDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

@Service("productStockInfoService")
public class ProductStockInfoService {
    public static final String SEMI_PROD_STOCK = "semiStock";
    public static final String END_PROD_STOCK = "endStock";
    @Autowired
    private ProductStockInfoRepository productStockInfoRepository;

    public void saveProductStockInfo(ProductStockInfo productStockInfo) {
        this.productStockInfoRepository.saveAndFlush(productStockInfo);
    }

    public void updateProdcutStockInfo(ProductStockInfoDTO stockInfoDTO) {
        ProductStockInfo targetStock = stockInfoDTO.getTargetProductStock();
        if (stockInfoDTO.getTargetStockType() == StockType.Finished.getCode()) {
            ProductStockInfo relatedStock = stockInfoDTO.getRelatedProductStock();
            updateStockAmount(targetStock, stockInfoDTO.getNewStockAmount(), true);
            updateStockAmount(relatedStock, stockInfoDTO.getNewStockAmount(), false);
            this.saveProductStockInfo(relatedStock);
        } else {
            updateStockAmount(targetStock, stockInfoDTO.getNewStockAmount(), true);
        }
        this.saveProductStockInfo(targetStock);
    }

    private void updateStockAmount(ProductStockInfo stockInfo, ProductStockAmountDTO newStockAmount, boolean addStock) {
        stockInfo.setStockAmount(addStock ? stockInfo.getStockAmount() + newStockAmount.getStockAmount() : stockInfo.getStockAmount() - newStockAmount.getStockAmount());
        stockInfo.setSize10Amount(addStock ? stockInfo.getSize10Amount() + newStockAmount.getSize10Amount() : stockInfo.getSize10Amount() - newStockAmount.getSize10Amount());
        stockInfo.setSize9Amount(addStock ? stockInfo.getSize9Amount() + newStockAmount.getSize9Amount() : stockInfo.getSize9Amount() - newStockAmount.getSize9Amount());
        stockInfo.setSize8Amount(addStock ? stockInfo.getSize8Amount() + newStockAmount.getSize8Amount() : stockInfo.getSize8Amount() - newStockAmount.getSize8Amount());
        stockInfo.setSize7Amount(addStock ? stockInfo.getSize7Amount() + newStockAmount.getSize7Amount() : stockInfo.getSize7Amount() - newStockAmount.getSize7Amount());
        stockInfo.setSize6Amount(addStock ? stockInfo.getSize6Amount() + newStockAmount.getSize6Amount() : stockInfo.getSize6Amount() - newStockAmount.getSize6Amount());
        stockInfo.setSize5Amount(addStock ? stockInfo.getSize5Amount() + newStockAmount.getSize5Amount() : stockInfo.getSize5Amount() - newStockAmount.getSize5Amount());
        stockInfo.setSize4Amount(addStock ? stockInfo.getSize4Amount() + newStockAmount.getSize4Amount() : stockInfo.getSize4Amount() - newStockAmount.getSize4Amount());

        if (stockInfo.isSupportSize()) {
            int stockAmount = stockInfo.getSize4Amount() + stockInfo.getSize5Amount() + stockInfo.getSize6Amount() + stockInfo.getSize7Amount() + stockInfo.getSize8Amount() + stockInfo.getSize9Amount() + stockInfo.getSize10Amount();
            stockInfo.setStockAmount(stockAmount);
        }
    }

    public List<ProductStockInfo> findProdStockInfoListFrom(final ProdStockSearchCriteria stockSearchCriteria) {
        Specification<ProductStockInfo> speci = new Specification<ProductStockInfo>() {
            @Override
            public Predicate toPredicate(Root<ProductStockInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                predicates.add(cb.equal(root.get("stockType"), stockSearchCriteria.getStockType()));
                if (StringUtils.isNotEmpty(stockSearchCriteria.getProdCategoryCode())) {
                    predicates.add(cb.equal(root.get("categoryCode"), stockSearchCriteria.getProdCategoryCode()));
                }
                if (StringUtils.isNotEmpty(stockSearchCriteria.getProdCode())) {
                    predicates.add(cb.like(root.<String>get("productCode"), "%" + stockSearchCriteria.getProdCode() + "%"));
                }
                String compareCode = stockSearchCriteria.getCompareCode();
                if (StringUtils.isNotEmpty(compareCode) && stockSearchCriteria.isIncludeComparedValue()) {
                    if (CompareCode.isEqual(compareCode)) {
                        predicates.add(cb.equal(root.get("stockAmount"), stockSearchCriteria.getStockAmount()));
                    } else if (CompareCode.isGreater(compareCode)) {
                        predicates.add(cb.greaterThan(root.<Integer>get("stockAmount"), stockSearchCriteria.getStockAmount()));
                    } else if (CompareCode.isLess(compareCode)) {
                        predicates.add(cb.lessThan(root.<Integer>get("stockAmount"), stockSearchCriteria.getStockAmount()));
                    } else if (CompareCode.isGreaterOrEqual(compareCode)) {
                        predicates.add(cb.greaterThanOrEqualTo(root.<Integer>get("stockAmount"), stockSearchCriteria.getStockAmount()));
                    } else if (CompareCode.isLessOrEqual(compareCode)) {
                        predicates.add(cb.lessThanOrEqualTo(root.<Integer>get("stockAmount"), stockSearchCriteria.getStockAmount()));
                    }

                    if (stockSearchCriteria.isTransformAction() && ((CompareCode.isLess(compareCode) || CompareCode.isLessOrEqual(compareCode)))) {
                        predicates.add(cb.greaterThan(root.<Integer>get("stockAmount"), 0));
                    }
                }

                query.where(cb.and(predicates.toArray(new Predicate[predicates.size()]))).orderBy(cb.desc(root.get("categoryCode")));
                return null;
            }
        };

        return productStockInfoRepository.findAll(speci);
    }

    public Map<String, ProductStockInfo> getProductStockMapWithProdCodeKey(ProdStockSearchCriteria stockSearchCriteria) {
        Map<String, ProductStockInfo> productStockInfoMap = new LinkedHashMap<String, ProductStockInfo>();
        List<ProductStockInfo> productStockInfos = this.findProdStockInfoListFrom(stockSearchCriteria);
        for (ProductStockInfo productStockInfo : productStockInfos) {
            productStockInfoMap.put(productStockInfo.getProductCode(), productStockInfo);
        }
        return productStockInfoMap;
    }

    public void saveProductStockInfo(List<ProductStockInfo> productStockInfos) {
        for (ProductStockInfo productStockInfo : productStockInfos) {
            this.productStockInfoRepository.saveAndFlush(productStockInfo);
        }
    }

    public int transformStock(List<ProductStockInfoDTO> stockInfoDTOList) {
        for (ProductStockInfoDTO stock : stockInfoDTOList) {
            ProductStockInfo semiStock = stock.getTargetProductStock();
            ProductStockInfo endStock = stock.getRelatedProductStock();

            endStock.setStockAmount(endStock.getStockAmount() + semiStock.getStockAmount());
            endStock.setSize4Amount(endStock.getSize4Amount() + semiStock.getSize4Amount());
            endStock.setSize5Amount(endStock.getSize5Amount() + semiStock.getSize5Amount());
            endStock.setSize6Amount(endStock.getSize6Amount() + semiStock.getSize6Amount());
            endStock.setSize7Amount(endStock.getSize7Amount() + semiStock.getSize7Amount());
            endStock.setSize8Amount(endStock.getSize8Amount() + semiStock.getSize8Amount());
            endStock.setSize9Amount(endStock.getSize9Amount() + semiStock.getSize9Amount());
            endStock.setSize10Amount(endStock.getSize10Amount() + semiStock.getSize10Amount());

            semiStock.setStockAmount(0);
            semiStock.setSize4Amount(0);
            semiStock.setSize5Amount(0);
            semiStock.setSize6Amount(0);
            semiStock.setSize7Amount(0);
            semiStock.setSize8Amount(0);
            semiStock.setSize9Amount(0);
            semiStock.setSize10Amount(0);
            this.saveProductStockInfo(semiStock);
            this.saveProductStockInfo(endStock);
        }
        return 0;
    }
}
