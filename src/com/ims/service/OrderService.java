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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private PurchaseOrderRepository purchaseOrderRepository;

    public void savePurchaseOrder(Map<String, ProductInfo> productInfoMap, PurchaseOrder purchaseOrder) {

        if (!purchaseOrder.getOrderItems().isEmpty()) {
            for (PurchaseOrderItem item : purchaseOrder.getOrderItems()) {
                ProductInfo productInfo = productInfoMap.get(item.getCompanyProductCode());
                if (productInfo != null) {
                    item.setCustomerProductCode(productInfo.getCustomerProductCode());
                    double unitPrice = item.getUnitPrice()==0l? productInfo.getPrice():item.getUnitPrice();
                    item.setUnitPrice(unitPrice);
                    item.setTotalPrice(unitPrice*item.getProductAmount().getTotalAmount());
                }
            }
        }
        this.purchaseOrderRepository.saveAndFlush(purchaseOrder);
    }

    public List<PurchaseOrder> findPurchaseOrderByPONumber(String poNumber){
        return this.purchaseOrderRepository.findPurchaseOrderByPurchaseOrderNumber(poNumber.toUpperCase());
    }
}
