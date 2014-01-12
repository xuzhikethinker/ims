package com.ims.webapp.view.converter;

import com.ims.domain.support.ProductInfo;
import com.ims.service.SupportingDataService;
import com.ims.webapp.view.criteria.ProdSearchCriteria;
import org.apache.commons.lang3.StringUtils;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import java.util.List;

@ManagedBean(name="productInfoConverter")
@RequestScoped
public class ProductInfoConverter implements Converter {

    @ManagedProperty(value = "#{supportingDataService}")
    protected SupportingDataService supportingDataService;

    public SupportingDataService getSupportingDataService() {
        return supportingDataService;
    }

    public void setSupportingDataService(SupportingDataService supportingDataService) {
        this.supportingDataService = supportingDataService;
    }

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String submittedValue) {
       // this.supportingDataService = (SupportingDataService) SpringUtil.getBean("supportingDataService");
        List<ProductInfo> productInfoList = supportingDataService.getProductInfoList(new ProdSearchCriteria());
        if (StringUtils.isEmpty(submittedValue)) {
            return null;
        } else {
            for (ProductInfo prod : productInfoList) {
                if (prod.getProductCode().equalsIgnoreCase(submittedValue)) {
                    return prod;
                }
            }
        }
        return null;
    }

    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
        if (value == null || value.equals("")) {
            return "";
        } else {
            return ((ProductInfo)value).getProductCode();
        }
    }
}
