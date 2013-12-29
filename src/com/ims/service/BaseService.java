package com.ims.service;

/**
 * Created by Administrator on 13-12-24.
 */
public abstract class BaseService {
    public String getLikeString(String value){
        return "%"+value+"%";
    }
}
