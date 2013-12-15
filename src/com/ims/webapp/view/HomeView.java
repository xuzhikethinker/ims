/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ims.webapp.view;

import com.ims.domain.support.ProductCategory;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author develop
 */
@ManagedBean(name = "homeView")
@RequestScoped
public class HomeView extends BaseView implements Serializable {

    private String userName = "";
    private String content = "";
    private int seq;
    private List<ProductCategory> prodCategoryList = new ArrayList<ProductCategory>();

    //@ManagedProperty("#{param.type}") //this works well only for RequestScoped
    private String type;

    public HomeView() {
        //this.userName = "xuzhike";
        Map initParams = this.getCurrentExternalContext().getInitParameterMap();
        System.out.println("HomeView init=" + this.getCurrentExternalContext().getInitParameterMap());
    }

    @PostConstruct
    public void init() {
        prodCategoryList = this.supportingDataService.loadProdCategoryList(false);
        System.out.println("category size = " + prodCategoryList);
    }

    public String loadData() {
        System.out.println("HomeView loadData type=" + type);
        return null;
    }

    public String getOutcome() {
        System.out.println("HomeView getOutcome seq=" + seq);
        return "sp_content";
    }


    public void reset() {
        FacesContext fc = FacesContext.getCurrentInstance();
        System.out.println("HomeView reset type=" + type);
        this.content = "这是最新测试结果，type=" + type;
    }

    //   public void retrieveType(ComponentSystemEvent event){
// 
//	FacesContext fc = FacesContext.getCurrentInstance();
//        String v = (String)fc.getExternalContext().getRequestMap().get("type");
//        System.out.println("retrieveType = "+v);
//  }	
    public String getUserName() {
        System.out.println("getUserName = " + userName);
        return userName;
    }

    public String getServiceProviderCode() {
        System.out.println("getUserName, seq = " + seq);
        return "sp_content";
    }

    public void setUserName(String userName) {
        System.out.println("setUserName = " + userName);
        this.userName = userName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        System.out.println("setContent = " + content);
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        System.out.println("setType = " + type);
        this.type = type;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public List<ProductCategory> getProdCategoryList() {
        return prodCategoryList;
    }

    public void setProdCategoryList(List<ProductCategory> prodCategoryList) {
        this.prodCategoryList = prodCategoryList;
    }

}
