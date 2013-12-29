package com.ims.webapp.view;

import org.apache.commons.lang3.StringUtils;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "customerView")
@ViewScoped
public class CustomerView extends BaseView {
    public void loadData() {
        this.customerInfo = this.customerService.getCustomerInfo();
    }

    public String saveCustomer() {
        if (StringUtils.isEmpty(customerInfo.getCustomerCode())) {
            customerInfo.setCustomerCode("CustomerCode-" + System.currentTimeMillis());
        }
        this.customerService.saveCustomer(customerInfo);
        FacesMessage msg = new FacesMessage("保存客户信息", "客户信息保存成功");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        this.customerInfo = this.customerService.getCustomerInfo();
        return null;
    }
}
