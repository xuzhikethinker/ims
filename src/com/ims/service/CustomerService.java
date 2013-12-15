/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ims.service;

import com.ims.repository.CustomerInfoRepository;
import com.ims.repository.CustomerProductCodeMapRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 */
@Service("customerService")
public class CustomerService {
    @Autowired
    private CustomerInfoRepository customerInfoRepository;

    @Autowired
    private CustomerProductCodeMapRepository customerProductCodeMapRepository;
}
