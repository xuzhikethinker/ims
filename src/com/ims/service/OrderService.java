/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ims.service;

import com.ims.repository.ProformaInvoiceItemRepository;
import com.ims.repository.ProformaInvoiceRepository;
import com.ims.repository.PurchaseOrderItemRepository;
import com.ims.repository.PurchaseOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("orderService")
public class OrderService {
    @Autowired
    private ProformaInvoiceItemRepository proformaInvoiceItemRepository;

    @Autowired
    private ProformaInvoiceRepository proformaInvoiceRepository;

    @Autowired
    private PurchaseOrderItemRepository purchaseOrderItemRepository;

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;
}
