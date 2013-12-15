/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ims.webapp.view;

import com.ims.domain.support.ProductCategory;
import com.ims.domain.support.ProductInfo;
import com.ims.domain.support.ProductUnit;
import com.ims.service.SupportingDataService;
import com.ims.webapp.view.criteria.ProdSearchCriteria;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "supportDataMaintainView")
@ViewScoped
public class SupportDataMaintainView extends BaseView {
    private ProductUnit productUnit = new ProductUnit();
    private ProductCategory productCategory = new ProductCategory();
    private String selectedProdCategory;
    private ProductInfo selectedProductInfo;
    private ProductInfo newProductInfo = new ProductInfo();
    private ProdSearchCriteria prodSearchCriteria = new ProdSearchCriteria();

    //    @PostConstruct
//    public void init(){
//        System.out.println();
//    }
    public SupportDataMaintainView() {
        System.out.println("SupportDataMaintainView constructor");
    }

    public void loadData() {
        if (SupportingDataService.MENU_CODE_PROD_CATEGORY.equalsIgnoreCase(getMenuCode())) {
            prodCategoryList = this.supportingDataService.loadProdCategoryList(false);
            productCategoryMap = this.buildProductCategoryMap(prodCategoryList);
            this.productUnitList = this.supportingDataService.loadProdProdUnitList();
        } else if (SupportingDataService.MENU_CODE_PROD_UNIT.equalsIgnoreCase(getMenuCode())) {
            this.productUnitList = this.supportingDataService.loadProdProdUnitList();
        } else if (SupportingDataService.MENU_CODE_PROD_INFO.equalsIgnoreCase(getMenuCode())) {
            prodCategoryList = this.supportingDataService.loadProdCategoryList(true);
            productCategoryMap = this.buildProductCategoryMap(prodCategoryList);
            this.productList = this.supportingDataService.getProductInfoList(prodSearchCriteria);
        }
    }

    public String addNewProductUnit() {
        supportingDataService.addNewProductUnit(productUnit);
        reloadData(SupportingDataService.MENU_CODE_PROD_UNIT);
        return null;
    }

    public String addNewProductCategory() {
        this.supportingDataService.addNewProductCategory(productCategory);
        reloadData(SupportingDataService.MENU_CODE_PROD_CATEGORY);
        return null;
    }

    public String updateProductInfo() {
        this.supportingDataService.updateProductInfo(selectedProductInfo);
        return null;
    }

    public void saveProductUnit(ActionEvent actionEvent) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("已经成功添加"));
        supportingDataService.addNewProductUnit(productUnit);
        reloadData(SupportingDataService.MENU_CODE_PROD_UNIT);
    }

    public void saveProductCategory(ActionEvent actionEvent) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("成功新增产品目录"));
        supportingDataService.addNewProductCategory(productCategory);
        reloadData(SupportingDataService.MENU_CODE_PROD_CATEGORY);
    }


    public ProductUnit getProductUnit() {
        System.out.println("------ getProductUnit=" + productUnit);
        return productUnit;
    }

    public void setProductUnit(ProductUnit productUnit) {
        System.out.println("------ setProductUnit=" + productUnit);
        this.productUnit = productUnit;
    }

    public String deleteProductUnit() {
        System.out.println("------ deleteProductUnit=" + productUnit);
        this.supportingDataService.deleteProductUnit(productUnit);
        reloadData(SupportingDataService.MENU_CODE_PROD_UNIT);
        return null;
    }

    public String deleteProductCategory() {
        System.out.println("------ deleteProductCategory=" + productCategory);
        this.supportingDataService.deleteProductCategory(productCategory);
        reloadData(SupportingDataService.MENU_CODE_PROD_CATEGORY);
        return null;
    }

    public String deleteProductInfo() {
        this.supportingDataService.deleteProductInfo(selectedProductInfo);
        reloadData(SupportingDataService.MENU_CODE_PROD_INFO);
        return null;
    }

    public String addProductInfo() {
        ProductCategory category = productCategoryMap.get(newProductInfo.getCategoryCode());
        if (category != null) {
            category.addProductInfo(newProductInfo);
            List<ProductCategory> categoryList = new ArrayList<ProductCategory>();
            categoryList.add(category);
            this.supportingDataService.updateCategoryList(categoryList);
        }
//        this.supportingDataService.addProductInfo(newProductInfo);
        reloadData(SupportingDataService.MENU_CODE_PROD_INFO);
        newProductInfo = new ProductInfo();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("成功新增产品代码"));
        return null;
    }

    private void reloadData(String menuCode) {
        this.setMenuCode(menuCode);
        loadData();
        productUnit = new ProductUnit();
        productCategory = new ProductCategory();
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }

    public void filterProductInfoList() {
        System.out.println("selectedProdCategory=" + prodSearchCriteria.toString());
        this.productList = this.supportingDataService.getProductInfoList(prodSearchCriteria);
    }

    public String searchProdInfo() {
        System.out.println("searchCriteria=" + this.prodSearchCriteria.toString());
//        this.productList = this.supportingDataService.getProductInfoList(prodSearchCriteria);
        this.productList = this.supportingDataService.findProductInfoListFrom(prodSearchCriteria);
        return null;
    }

    public String syncProdInfoToProdStock() {
        this.supportingDataService.syncProdInfo2ProdStock(null);
        return null;
    }

    public String getSelectedProdCategory() {
        return selectedProdCategory;
    }

    public void setSelectedProdCategory(String selectedProdCategory) {
        this.selectedProdCategory = selectedProdCategory;
    }

    public ProductInfo getSelectedProductInfo() {
        return selectedProductInfo;
    }

    public void setSelectedProductInfo(ProductInfo selectedProductInfo) {
        this.selectedProductInfo = selectedProductInfo;
    }

    public ProductInfo getNewProductInfo() {
        return newProductInfo;
    }

    public void setNewProductInfo(ProductInfo newProductInfo) {
        this.newProductInfo = newProductInfo;
    }

    public ProdSearchCriteria getProdSearchCriteria() {
        return prodSearchCriteria;
    }

    public void setProdSearchCriteria(ProdSearchCriteria prodSearchCriteria) {
        this.prodSearchCriteria = prodSearchCriteria;
    }
}
