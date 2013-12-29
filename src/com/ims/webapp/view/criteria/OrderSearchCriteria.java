package com.ims.webapp.view.criteria;

import java.io.Serializable;

/**
 * Created by Administrator on 13-12-29.
 */
public class OrderSearchCriteria implements Serializable {
    private Long purchaseOrderID;
    private String purchaseOrderNum;
    private String purchaseOrderStatus;
    private Long proformaInvoiceID;
    private String proformaInvoiceNum;

    public Long getPurchaseOrderID() {
        return purchaseOrderID;
    }

    public void setPurchaseOrderID(Long purchaseOrderID) {
        this.purchaseOrderID = purchaseOrderID;
    }

    public String getPurchaseOrderNum() {
        return purchaseOrderNum;
    }

    public void setPurchaseOrderNum(String purchaseOrderNum) {
        this.purchaseOrderNum = purchaseOrderNum;
    }

    public String getPurchaseOrderStatus() {
        return purchaseOrderStatus;
    }

    public void setPurchaseOrderStatus(String purchaseOrderStatus) {
        this.purchaseOrderStatus = purchaseOrderStatus;
    }

    public Long getProformaInvoiceID() {
        return proformaInvoiceID;
    }

    public void setProformaInvoiceID(Long proformaInvoiceID) {
        this.proformaInvoiceID = proformaInvoiceID;
    }

    public String getProformaInvoiceNum() {
        return proformaInvoiceNum;
    }

    public void setProformaInvoiceNum(String proformaInvoiceNum) {
        this.proformaInvoiceNum = proformaInvoiceNum;
    }
}
