package com.ims.webapp.view.util;

import org.apache.poi.ss.usermodel.Cell;

/**
 * Created by Administrator on 13-12-22.
 */
public abstract class ExcelResolver {
    protected static final String EXCEL_2010 = ".xlsx";
    protected static String getCellString(Cell cell){
        return cell!=null? cell.getStringCellValue():"";
    }

    protected static double getCellNumeric(Cell cell){
        return cell!=null?cell.getNumericCellValue():0d;
    }

    protected static int getCellInt(Cell cell){
        try{
            double value = cell.getNumericCellValue();
            return (int)value;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }
}
