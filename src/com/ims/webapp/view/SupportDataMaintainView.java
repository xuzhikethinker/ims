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
import org.primefaces.event.RowEditEvent;

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


    //    @PostConstruct
//    public void init(){
//        System.out.println();
//    }
    public SupportDataMaintainView() {
        System.out.println("SupportDataMaintainView constructor");
    }

    public void loadData() {
        if (SupportingDataService.MENU_CODE_PROD_CATEGORY_UNIT.equalsIgnoreCase(getMenuCode())) {
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
        reloadData(SupportingDataService.MENU_CODE_PROD_CATEGORY_UNIT);
        return null;
    }

    public String updateProductInfo1() {
        this.supportingDataService.updateProductInfo(selectedProductInfo);
        return null;
    }

    public void updateProductInfo(RowEditEvent event) {
        selectedProductInfo = (ProductInfo) event.getObject();
        FacesMessage msg = new FacesMessage("更新产品信息", "产品信息成功更新");
        this.supportingDataService.updateProductInfo(selectedProductInfo);
        reloadData(SupportingDataService.MENU_CODE_PROD_INFO);
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void cancelUpdateProductInfo(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("更新产品信息", "产品信息更新已被取消");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void saveProductUnit(ActionEvent actionEvent) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("成功添加产品单位"));
        supportingDataService.addNewProductUnit(productUnit);
        reloadData(SupportingDataService.MENU_CODE_PROD_CATEGORY_UNIT);
    }

    public void editProdCategory(RowEditEvent event) {
        productCategory = (ProductCategory) event.getObject();
        FacesMessage msg = new FacesMessage("更新产品类别", "产品类别成功更新");
        this.supportingDataService.addNewProductCategory(productCategory);
        reloadData(SupportingDataService.MENU_CODE_PROD_CATEGORY_UNIT);
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void editProdUnit(RowEditEvent event) {
        ProductUnit unit = (ProductUnit) event.getObject();
        FacesMessage msg = new FacesMessage("更新产品单元", unit + " 产品单元成功更新");
        supportingDataService.addNewProductUnit(unit);
        reloadData(SupportingDataService.MENU_CODE_PROD_CATEGORY_UNIT);
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void cancelUpdateUnit(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("产品单位更新", "产品单位更新已被取消");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void cancelUpdateCategory(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("产品类别更新", "产品类别更新已被取消");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void saveProductCategory(ActionEvent actionEvent) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("成功新增产品目录"));
        supportingDataService.addNewProductCategory(productCategory);
        reloadData(SupportingDataService.MENU_CODE_PROD_CATEGORY_UNIT);
    }


    public ProductUnit getProductUnit() {
        return productUnit;
    }

    public void setProductUnit(ProductUnit productUnit) {
        this.productUnit = productUnit;
    }

    public String deleteProductUnit() {
        this.supportingDataService.deleteProductUnit(productUnit);
        reloadData(SupportingDataService.MENU_CODE_PROD_UNIT);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("成功删除产品单位"));
        return null;
    }

    public String deleteProductCategory() {
        this.supportingDataService.deleteProductCategory(productCategory);
        reloadData(SupportingDataService.MENU_CODE_PROD_CATEGORY_UNIT);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("成功删除产品类别"));
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


    public String searchProdInfo() {
        System.out.println("searchCriteria=" + this.prodSearchCriteria.toString());
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

}
