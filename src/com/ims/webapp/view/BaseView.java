/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ims.webapp.view;

import com.ims.domain.stock.ProductStockInfo;
import com.ims.domain.support.ProductCategory;
import com.ims.domain.support.ProductInfo;
import com.ims.domain.support.ProductUnit;
import com.ims.service.CustomerService;
import com.ims.service.OrderService;
import com.ims.service.ProductStockInfoService;
import com.ims.service.SupportingDataService;

import javax.faces.bean.ManagedProperty;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author develop
 */
public abstract class BaseView {
    private String menuCode;
    protected static final String ACTION_ADD = "add";
    protected static final String ACTION_UPDATE = "update";
    protected static final String ACTION_DELETE = "delete";
    protected static final String ACTION_LIST = "list";
    protected static final String ACTION_CONVERT_STOCK = "convertStock";
    private String action;

    @ManagedProperty(value = "#{supportingDataService}")
    protected SupportingDataService supportingDataService;

    @ManagedProperty(value = "#{customerService}")
    protected CustomerService customerService;

    @ManagedProperty(value = "#{orderService}")
    protected OrderService orderService;

    @ManagedProperty(value = "#{productStockInfoService}")
    protected ProductStockInfoService productStockInfoService;

    protected List<ProductCategory> prodCategoryList = new ArrayList<ProductCategory>();
    protected Map<String, ProductCategory> productCategoryMap = new HashMap<String, ProductCategory>();
    protected List<ProductUnit> productUnitList = new ArrayList<ProductUnit>();
    protected List<ProductInfo> productList = new ArrayList<ProductInfo>();
    protected List<ProductStockInfo> semiProdStockList = new ArrayList<ProductStockInfo>();
    protected List<ProductStockInfo> endProdStockList = new ArrayList<ProductStockInfo>();

    public SupportingDataService getSupportingDataService() {
        return supportingDataService;
    }

    public void setSupportingDataService(SupportingDataService supportingDataService) {
        this.supportingDataService = supportingDataService;
    }

    public CustomerService getCustomerService() {
        return customerService;
    }

    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    public OrderService getOrderService() {
        return orderService;
    }

    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    public ProductStockInfoService getProductStockInfoService() {
        return productStockInfoService;
    }

    public void setProductStockInfoService(ProductStockInfoService productStockInfoService) {
        this.productStockInfoService = productStockInfoService;
    }

    public Map<String, ProductCategory> getProductCategoryMap() {
        return productCategoryMap;
    }

    public void setProductCategoryMap(Map<String, ProductCategory> productCategoryMap) {
        this.productCategoryMap = productCategoryMap;
    }

    protected FacesContext getCurrentFacesContext() {
        return FacesContext.getCurrentInstance();
    }

    /**
     * 可以通过ExternalContext获取requestMap等，getRequestParameterMap()
     *
     * @return
     */
    protected ExternalContext getCurrentExternalContext() {
        return this.getCurrentFacesContext().getExternalContext();
    }

    protected ServletContext getServletContext() {
        return (ServletContext) this.getCurrentExternalContext().getContext();
    }

    protected String getInitParamValueByKey(String name) {
        return this.getCurrentExternalContext().getInitParameter(name);
    }

    public List<ProductCategory> getProdCategoryList() {
        return prodCategoryList;
    }

    public void setProdCategoryList(List<ProductCategory> prodCategoryList) {
        this.prodCategoryList = prodCategoryList;
    }

    public List<ProductUnit> getProductUnitList() {
        return productUnitList;
    }

    public void setProductUnitList(List<ProductUnit> productUnitList) {
        this.productUnitList = productUnitList;
    }

    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        System.out.println("setAction = " + action);
        this.action = action;
    }

    public List<ProductInfo> getProductList() {
        return productList;
    }

    public void setProductList(List<ProductInfo> productList) {
        this.productList = productList;
    }

    protected Map<String, ProductCategory> buildProductCategoryMap(List<ProductCategory> prodCategoryList) {
        Map<String, ProductCategory> productCategoryMap = new HashMap<String, ProductCategory>();
        for (ProductCategory category : prodCategoryList) {
            productCategoryMap.put(category.getCategoryName(), category);
        }
        return productCategoryMap;
    }

    public List<ProductStockInfo> getEndProdStockList() {
        return endProdStockList;
    }

    public void setEndProdStockList(List<ProductStockInfo> endProdStockList) {
        endProdStockList = endProdStockList;
    }

    public List<ProductStockInfo> getSemiProdStockList() {
        return semiProdStockList;
    }

    public void setSemiProdStockList(List<ProductStockInfo> semiProdStockList) {
        semiProdStockList = semiProdStockList;
    }
}
