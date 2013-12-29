package com.ims.webapp.view.util;

import com.ims.domain.customer.CustomerProductCodeMap;
import com.ims.domain.support.ProductCategory;
import com.ims.domain.support.ProductInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-12-1
 * Time: 下午9:37
 * To change this template use File | Settings | File Templates.
 */
public class ProductInfoFileResolver extends ExcelResolver{


    public static boolean resolveProductInfoListFile(InputStream is, String fileName, List<ProductCategory> prodCategoryList, Map<String, CustomerProductCodeMap> customerProductCodeMapping, List<CustomerProductCodeMap> newCustProdCodeList) {
        if (fileName.endsWith(EXCEL_2010)) {
            resolveExcel2010(is, prodCategoryList,customerProductCodeMapping,newCustProdCodeList);
        } else {

        }

        return true;
    }

    private static boolean resolveExcel2010(InputStream is, List<ProductCategory> prodCategoryList,Map<String, CustomerProductCodeMap> customerProductCodeMapping, List<CustomerProductCodeMap> newCustProdCodeList) {
        try {
            Workbook wb = new XSSFWorkbook(is);
            for (ProductCategory category : prodCategoryList) {
                Sheet sheet = wb.getSheet(category.getCategoryName());
                if (sheet != null) {
                    Map<String, ProductInfo> productInfoMap = category.getProductInfoMap();
                    int rowNums = sheet.getPhysicalNumberOfRows();
                    if (rowNums > 1) {
                        for (int index = 1; index < rowNums; index++) {
                            Row row = sheet.getRow(index);
                            String prodCode = getCellString(row.getCell(0));
                            if (StringUtils.isNotEmpty(prodCode)) {
                                ProductInfo prod = productInfoMap.get(prodCode);
                                if (prod == null) {
                                    prod = new ProductInfo();
                                    category.addProductInfo(prod);
                                    prod.setCategoryCode(category.getCategoryName());
                                    prod.setProductCode(prodCode);
                                    productInfoMap.put(prodCode, prod);
                                }
                                prod.setProductName(prodCode);
                                prod.setDescription(getCellString(row.getCell(1)));
                                prod.setCustProdCode(getCellString(row.getCell(2)));
                                prod.setCustProdSecondCode(getCellString(row.getCell(3)));
                                prod.setPictureName(getCellString(row.getCell(4)));
                                prod.setPrice(getCellNumeric(row.getCell(5)));

                                if(StringUtils.isNotEmpty(prod.getCustProdCode())){
                                    if(!customerProductCodeMapping.containsKey(prod.getCustProdCode())){
                                        CustomerProductCodeMap codeMap = new CustomerProductCodeMap();
                                        codeMap.setCustomerProductCode(prod.getCustProdCode());
                                        codeMap.setCompanyProductCode(prod.getProductCode());
                                        codeMap.setCustomerProductSecondCode(prod.getCustProdSecondCode());
                                        newCustProdCodeList.add(codeMap);
                                    }
                                }
                            }
                        }
                    }
                }
            }

        } catch (IOException e) {

        }
        return true;
    }


}
