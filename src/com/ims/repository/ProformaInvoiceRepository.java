/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ims.repository;

import com.ims.domain.order.ShipmentProformaInvoice;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Administrator
 */
public interface ProformaInvoiceRepository extends JpaRepository<ShipmentProformaInvoice, Long> {

}
