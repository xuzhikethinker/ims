/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ims.service;

import com.ims.domain.customer.CustomerInfo;
import com.ims.domain.customer.CustomerProductCodeMap;
import com.ims.repository.CustomerInfoRepository;
import com.ims.repository.CustomerProductCodeMapRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author Administrator
 */
@Service("customerService")
public class CustomerService {
    @Autowired
    private CustomerInfoRepository customerInfoRepository;

    @Autowired
    private CustomerProductCodeMapRepository customerProductCodeMapRepository;

    public Map<String, CustomerProductCodeMap> getCustomerProdCodeMap(){
        Map<String, CustomerProductCodeMap> customerProductCodeMapping = new LinkedHashMap<String, CustomerProductCodeMap>();
        List<CustomerProductCodeMap> codeMaps = this.getCustomerProdCodeMapList();
        for(CustomerProductCodeMap codeMap: codeMaps){
            customerProductCodeMapping.put(codeMap.getCustomerProductCode(),codeMap);
        }
        return customerProductCodeMapping;
    }

    public List<CustomerProductCodeMap> getCustomerProdCodeMapList(){
        List<CustomerProductCodeMap> customerProductCodeMaps = new ArrayList<CustomerProductCodeMap>();
        this.customerProductCodeMapRepository.findAll();
        return customerProductCodeMaps;
    }

    public CustomerInfo getCustomerInfo(){
        List<CustomerInfo> customerInfoList = this.customerInfoRepository.findAll();
        if(customerInfoList!=null &&!customerInfoList.isEmpty()){
            return customerInfoList.get(0);
        }
        return new CustomerInfo();
    }

    public void saveCustomer(CustomerInfo customerInfo){
        this.customerInfoRepository.saveAndFlush(customerInfo);
    }
}
