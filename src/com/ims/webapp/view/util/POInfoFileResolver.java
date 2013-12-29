package com.ims.webapp.view.util;

import com.ims.domain.order.PurchaseOrder;
import com.ims.domain.order.PurchaseOrderItem;
import com.ims.domain.support.ProductAmount;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;

public class POInfoFileResolver extends ExcelResolver {
    public static PurchaseOrder processPOInfoFile(InputStream is, String fileName) {
        return resolveExcel2010(is);
    }

    private static PurchaseOrder resolveExcel2010(InputStream is) {
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        try {
            Workbook wb = new XSSFWorkbook(is);
            Sheet sheet = wb.getSheetAt(0);
            if(sheet!=null){
                int rowNums = sheet.getPhysicalNumberOfRows();
                if(rowNums>0){
                    Row row = sheet.getRow(0);
                    purchaseOrder.setPurchaseOrderNumber(getCellString(row.getCell(1)));

                    row = sheet.getRow(1);
                    purchaseOrder.setPiNumber(getCellString(row.getCell(1)));

                    row = sheet.getRow(2);
                    purchaseOrder.setOrderSubmitDate(getCellString(row.getCell(1)));

                    row = sheet.getRow(3);
                    purchaseOrder.setDeliveryDate(getCellString(row.getCell(1)));

                    if(rowNums>5){
                        int itemSeq = 0;
                        for(int index=5;index<rowNums;index++){
                            row = sheet.getRow(index);
                            if(row!=null){
                                String prodCode = getCellString(row.getCell(0));
                                if(StringUtils.isNotEmpty(prodCode)){
                                    PurchaseOrderItem poItem = new PurchaseOrderItem();
                                    poItem.setCompanyProductCode(prodCode);
                                    itemSeq++;
                                    poItem.setPoItemCode(purchaseOrder.getPurchaseOrderNumber()+"_"+(itemSeq));
                                    poItem.setDisplaySeq(itemSeq);
                                    poItem.setPoNumber(purchaseOrder.getPurchaseOrderNumber());
                                    ProductAmount productAmount = poItem.getProductAmount();
                                    productAmount.setSize4Amount(getCellInt(row.getCell(1)));
                                    productAmount.setSize5Amount(getCellInt(row.getCell(2)));
                                    productAmount.setSize6Amount(getCellInt(row.getCell(3)));
                                    productAmount.setSize7Amount(getCellInt(row.getCell(4)));
                                    productAmount.setSize8Amount(getCellInt(row.getCell(5)));
                                    productAmount.setSize9Amount(getCellInt(row.getCell(6)));
                                    productAmount.setSize10Amount(getCellInt(row.getCell(7)));
                                    productAmount.updateTotalAmount(getCellInt(row.getCell(8)));
                                    poItem.setUnitPrice(getCellNumeric(row.getCell(9)));
                                    purchaseOrder.addOrderItemToList(poItem);
                                }
                            }
                        }
                    }

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            purchaseOrder = null;
        }
        return purchaseOrder;
    }
}
