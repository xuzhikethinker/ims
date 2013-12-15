package com.ims.webapp.view.util;

import com.ims.domain.support.ProductCategory;
import com.ims.domain.support.ProductInfo;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-12-1
 * Time: 下午9:37
 * To change this template use File | Settings | File Templates.
 */
public class ProductInfoFileResolver {
    private static final String EXCEL_2010 = ".xlsx";

    public static boolean resolveProductInfoListFile(InputStream is, String fileName, List<ProductCategory> prodCategoryList) {
        if (fileName.endsWith(EXCEL_2010)) {
            resolveExcel2010(is, prodCategoryList);
        } else {

        }

        return true;
    }

    private static boolean resolveExcel2010(InputStream is, List<ProductCategory> prodCategoryList) {
        try {
            Workbook wb = new XSSFWorkbook(is);
            for (ProductCategory category : prodCategoryList) {
                Sheet sheet = wb.getSheet(category.getCategoryName());
                int colNum = sheet.getPhysicalNumberOfRows();
                if (colNum > 1) {
                    for (int index = 1; index < colNum; index++) {
                        Row row = sheet.getRow(index);
                        ProductInfo prod = new ProductInfo();
                        prod.setCategoryCode(category.getCategoryName());
                        prod.setProductCode(row.getCell(0).getStringCellValue());
                        prod.setProductName(row.getCell(0).getStringCellValue());
                        prod.setPictureName(row.getCell(1).getStringCellValue());
                        prod.setPrice(row.getCell(2).getNumericCellValue());
                        category.addProductInfo(prod);
                    }
                }
            }

        } catch (IOException e) {

        }
        return true;
    }
}
