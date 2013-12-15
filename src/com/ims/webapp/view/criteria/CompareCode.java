package com.ims.webapp.view.criteria;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by Administrator on 13-12-14.
 */
public enum CompareCode {
    EMPTY("","选择比较符","not compare"),
    ET("=","等于(=)","equal"),
    GREATER(">","大于(>)", "greater than"),
    LESS("<","小于(<)","less than"),
    GREATER_ET(">=","大于等于(>=)","greater than or equal to"),
    LESS_ET("<=","小于等于(<=)","less than or equal to");

    private String code;
    private String displayName;
    private String description;
    private CompareCode(String code, String displayName, String description) {
        this.code = code;
        this.displayName = displayName;
        this.description = description;
    }

    public static boolean isEmpty(String code){
        return StringUtils.isEmpty(code);
    }

    public static boolean isEqual(String code){
        return StringUtils.isNotEmpty(code) && ET.code.equalsIgnoreCase(code);
    }

    public static boolean isGreater(String code){
        return StringUtils.isNotEmpty(code) && GREATER.code.equalsIgnoreCase(code);
    }

    public static boolean isLess(String code){
        return StringUtils.isNotEmpty(code) && LESS.code.equalsIgnoreCase(code);
    }

    public static boolean isGreaterOrEqual(String code){
        return StringUtils.isNotEmpty(code) && GREATER_ET.code.equalsIgnoreCase(code);
    }

    public static boolean isLessOrEqual(String code){
        return StringUtils.isNotEmpty(code) && LESS_ET.code.equalsIgnoreCase(code);
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public String getDisplayName() {
        return displayName;
    }
}
