/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ims.service;

import com.ims.domain.order.PurchaseOrder;
import com.ims.domain.order.PurchaseOrderItem;
import com.ims.domain.support.ProductInfo;
import com.ims.repository.ProformaInvoiceItemRepository;
import com.ims.repository.ProformaInvoiceRepository;
import com.ims.repository.PurchaseOrderItemRepository;
import com.ims.repository.PurchaseOrderRepository;
import com.ims.webapp.view.criteria.OrderSearchCriteria;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("orderService")
public class OrderService {
    @Autowired
    private ProformaInvoiceItemRepository proformaInvoiceItemRepository;

    @Autowired
    private ProformaInvoiceRepository proformaInvoiceRepository;

    @Autowired
    private PurchaseOrderItemRepository purchaseOrderItemRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private SupportingDataService supportingDataService;

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    public void savePurchaseOrder(Map<String, ProductInfo> productInfoMap, PurchaseOrder purchaseOrder) {


        this.purchaseOrderRepository.saveAndFlush(purchaseOrder);
    }

    public List<PurchaseOrder> findPurchaseOrderByPONumber(String poNumber) {
        return this.purchaseOrderRepository.findPurchaseOrderByPurchaseOrderNumber(poNumber.toUpperCase());
    }

    public PurchaseOrder findPurchaseOrderByID(Long purchaseOrderID) {
        PurchaseOrder order = this.purchaseOrderRepository.findOne(purchaseOrderID);
        Map<String, ProductInfo> productInfoMap = supportingDataService.getProductInfoMap();
        for (PurchaseOrderItem item : order.getOrderItemList()) {
            ProductInfo productInfo = productInfoMap.get(item.getCompanyProductCode());
            if (productInfo != null) {
                item.setProductInfo(productInfo);
            }
        }
        return order;
    }

    public List<PurchaseOrder> searchPurchaseOrderList(final OrderSearchCriteria orderSearchCriteria) {
        List<PurchaseOrder> purchaseOrders = new ArrayList<PurchaseOrder>();
        Specification<PurchaseOrder> speci = new Specification<PurchaseOrder>() {
            @Override
            public Predicate toPredicate(Root<PurchaseOrder> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                if (StringUtils.isNotEmpty(orderSearchCriteria.getPurchaseOrderStatus())) {
                    predicates.add(cb.equal(root.get("status"), orderSearchCriteria.getPurchaseOrderStatus()));
                }

                if (StringUtils.isNotEmpty(orderSearchCriteria.getPurchaseOrderNum())) {
                    predicates.add(cb.like(root.<String>get("purchaseOrderNumber"), "%" + orderSearchCriteria.getPurchaseOrderNum() + "%"));
                }

                if (StringUtils.isNotEmpty(orderSearchCriteria.getProformaInvoiceNum())) {
                    predicates.add(cb.like(root.<String>get("piNumber"), "%" + orderSearchCriteria.getProformaInvoiceNum() + "%"));
                }

                query.where(cb.and(predicates.toArray(new Predicate[predicates.size()]))).orderBy(cb.desc(root.get("changeLog").get("createdDate")));
                return null;
            }
        };

        return purchaseOrderRepository.findAll(speci);
    }
}
