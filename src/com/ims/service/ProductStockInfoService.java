/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ims.service;

import com.ims.domain.stock.ProductStockInfo;
import com.ims.domain.stock.StockType;
import com.ims.domain.support.ProductAmount;
import com.ims.repository.ProductStockInfoRepository;
import com.ims.webapp.view.criteria.CompareCode;
import com.ims.webapp.view.criteria.ProdStockSearchCriteria;
import com.ims.webapp.view.dto.ProductStockAmountDTO;
import com.ims.webapp.view.dto.ProductStockInfoDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service("productStockInfoService")
public class ProductStockInfoService {
    public static final String SEMI_PROD_STOCK = "semiStock";
    public static final String END_PROD_STOCK = "endStock";
    @Autowired
    private ProductStockInfoRepository productStockInfoRepository;

    public void saveProductStockInfo(ProductStockInfo productStockInfo) {
        this.productStockInfoRepository.saveAndFlush(productStockInfo);
    }

    public void updateProdcutStockInfo(ProductStockInfoDTO stockInfoDTO, boolean convertToFinishedProd) {
        ProductStockInfo targetStock = stockInfoDTO.getTargetProductStock();
        ProductStockInfo relatedStock = stockInfoDTO.getRelatedProductStock();
        if (stockInfoDTO.getTargetStockType() == StockType.Finished.getCode()) {
            updateStockAmount(targetStock, stockInfoDTO.getNewStockAmount(), true);
            updateStockAmount(relatedStock, stockInfoDTO.getNewStockAmount(), false);
            this.saveProductStockInfo(relatedStock);
        } else {
            if(convertToFinishedProd){
                updateStockAmount(targetStock, stockInfoDTO.getNewStockAmount(), false);
                updateStockAmount(relatedStock, stockInfoDTO.getNewStockAmount(), true);
                this.saveProductStockInfo(relatedStock);
            }else{
            updateStockAmount(targetStock, stockInfoDTO.getNewStockAmount(), true);
            }
        }
        this.saveProductStockInfo(targetStock);
    }

    private void updateStockAmount(ProductStockInfo stockInfo, ProductStockAmountDTO newStockAmount, boolean addStock) {
        ProductAmount productAmount = stockInfo.getProductAmount();
        productAmount.setTotalAmount(addStock ? productAmount.getTotalAmount() + newStockAmount.getStockAmount() : productAmount.getTotalAmount() - newStockAmount.getStockAmount());
        productAmount.setSize10Amount(addStock ? productAmount.getSize10Amount() + newStockAmount.getSize10Amount() : productAmount.getSize10Amount() - newStockAmount.getSize10Amount());
        productAmount.setSize9Amount(addStock ? productAmount.getSize9Amount() + newStockAmount.getSize9Amount() : productAmount.getSize9Amount() - newStockAmount.getSize9Amount());
        productAmount.setSize8Amount(addStock ? productAmount.getSize8Amount() + newStockAmount.getSize8Amount() : productAmount.getSize8Amount() - newStockAmount.getSize8Amount());
        productAmount.setSize7Amount(addStock ? productAmount.getSize7Amount() + newStockAmount.getSize7Amount() : productAmount.getSize7Amount() - newStockAmount.getSize7Amount());
        productAmount.setSize6Amount(addStock ? productAmount.getSize6Amount() + newStockAmount.getSize6Amount() : productAmount.getSize6Amount() - newStockAmount.getSize6Amount());
        productAmount.setSize5Amount(addStock ? productAmount.getSize5Amount() + newStockAmount.getSize5Amount() : productAmount.getSize5Amount() - newStockAmount.getSize5Amount());
        productAmount.setSize4Amount(addStock ? productAmount.getSize4Amount() + newStockAmount.getSize4Amount() : productAmount.getSize4Amount() - newStockAmount.getSize4Amount());

        if (stockInfo.isSupportSize()) {
            int stockAmount = productAmount.getSize4Amount() + productAmount.getSize5Amount() + productAmount.getSize6Amount() + productAmount.getSize7Amount() + productAmount.getSize8Amount() + productAmount.getSize9Amount() + productAmount.getSize10Amount();
            productAmount.setTotalAmount(stockAmount);
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
                    Path<ProductAmount> productAmount = root.<ProductAmount>get("productAmount");
                    if (CompareCode.isEqual(compareCode)) {
                        predicates.add(cb.equal(productAmount.get("totalAmount"), stockSearchCriteria.getStockAmount()));
                    } else if (CompareCode.isGreater(compareCode)) {
                        predicates.add(cb.greaterThan(productAmount.<Integer>get("totalAmount"), stockSearchCriteria.getStockAmount()));
                    } else if (CompareCode.isLess(compareCode)) {
                        predicates.add(cb.lessThan(productAmount.<Integer>get("totalAmount"), stockSearchCriteria.getStockAmount()));
                    } else if (CompareCode.isGreaterOrEqual(compareCode)) {
                        predicates.add(cb.greaterThanOrEqualTo(productAmount.<Integer>get("totalAmount"), stockSearchCriteria.getStockAmount()));
                    } else if (CompareCode.isLessOrEqual(compareCode)) {
                        predicates.add(cb.lessThanOrEqualTo(productAmount.<Integer>get("totalAmount"), stockSearchCriteria.getStockAmount()));
                    } else if(CompareCode.isEqualAlertAmount(compareCode)){
                        predicates.add(cb.equal(productAmount.get("totalAmount"), root.get("alertStockAmount")));
                    } else if(CompareCode.isGreaterThanAlertAmount(compareCode)){
                        predicates.add(cb.greaterThan(productAmount.<String>get("totalAmount"), root.<String>get("alertStockAmount")));
                    } else if(CompareCode.isLessThanAlertAmount(compareCode)){
                        predicates.add(cb.lessThan(productAmount.<String>get("totalAmount"), root.<String>get("alertStockAmount")));
                    }

                    if (stockSearchCriteria.isTransformAction() && ((CompareCode.isLess(compareCode) || CompareCode.isLessOrEqual(compareCode)))) {
                        predicates.add(cb.greaterThan(productAmount.<Integer>get("totalAmount"), 0));
                    }
                }

                query.where(cb.and(predicates.toArray(new Predicate[predicates.size()]))).orderBy(cb.desc(root.get("categoryCode")),cb.asc(root.get("productCode")));
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
            ProductAmount endStockAmount = endStock.getProductAmount();
            ProductAmount semiStockAmount = semiStock.getProductAmount();

            endStockAmount.setTotalAmount(endStockAmount.getTotalAmount() + semiStock.getStockAmount());
            endStockAmount.setSize4Amount(endStockAmount.getSize4Amount() + semiStockAmount.getSize4Amount());
            endStockAmount.setSize5Amount(endStockAmount.getSize5Amount() + semiStockAmount.getSize5Amount());
            endStockAmount.setSize6Amount(endStockAmount.getSize6Amount() + semiStockAmount.getSize6Amount());
            endStockAmount.setSize7Amount(endStockAmount.getSize7Amount() + semiStockAmount.getSize7Amount());
            endStockAmount.setSize8Amount(endStockAmount.getSize8Amount() + semiStockAmount.getSize8Amount());
            endStockAmount.setSize9Amount(endStockAmount.getSize9Amount() + semiStockAmount.getSize9Amount());
            endStockAmount.setSize10Amount(endStockAmount.getSize10Amount() + semiStockAmount.getSize10Amount());

            semiStockAmount.setTotalAmount(0);
            semiStockAmount.setSize4Amount(0);
            semiStockAmount.setSize5Amount(0);
            semiStockAmount.setSize6Amount(0);
            semiStockAmount.setSize7Amount(0);
            semiStockAmount.setSize8Amount(0);
            semiStockAmount.setSize9Amount(0);
            semiStockAmount.setSize10Amount(0);
            this.saveProductStockInfo(semiStock);
            this.saveProductStockInfo(endStock);
        }
        return 0;
    }

    public int updateStockAlertAmount(ProdStockSearchCriteria stockSearchCriteria){
        int updatedRecords = 0;
        if(StringUtils.isEmpty(stockSearchCriteria.getProdCategoryCode())){
            if(stockSearchCriteria.getStockType()==ProdStockSearchCriteria.NO_STOCK_TYPE){
                updatedRecords = productStockInfoRepository.updateStockAlertAmount(stockSearchCriteria.getAlertStockAmount());
            }else{
                updatedRecords = productStockInfoRepository.updateStockAlertAmount(stockSearchCriteria.getStockType(),stockSearchCriteria.getAlertStockAmount());
            }
        }else{
            if(stockSearchCriteria.getStockType()==ProdStockSearchCriteria.NO_STOCK_TYPE){
                updatedRecords = productStockInfoRepository.updateStockAlertAmount(stockSearchCriteria.getProdCategoryCode(),stockSearchCriteria.getAlertStockAmount());
            }else{
                updatedRecords = productStockInfoRepository.updateStockAlertAmount(stockSearchCriteria.getProdCategoryCode(),stockSearchCriteria.getStockType(),stockSearchCriteria.getAlertStockAmount());
            }
        }
        return updatedRecords;
    }

    public List<ProductStockInfo> getAlertedStockInfoList(int stockType){
        return this.productStockInfoRepository.getLessThanAlertStockAmount(stockType);
    }

}
