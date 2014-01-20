package com.ims.webapp.view.util;

import com.ims.domain.order.PurchaseOrder;
import com.ims.domain.order.PurchaseOrderItem;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.util.IOUtils;

import javax.servlet.ServletContext;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by Administrator on 14-1-12.
 */
public class POExcelGenerator {
    public static void generateExcel(Workbook wb, PurchaseOrder purchaseOrder, ServletContext servletContext) {
        Sheet sheet = wb.getSheetAt(0);
        //To
        Row row = sheet.getRow(4);
        String to = row.getCell(0).getStringCellValue() + purchaseOrder.getCustomerName();
        row.getCell(0).setCellValue(to);

        //Tel
        row = sheet.getRow(5);
        String tel = row.getCell(0).getRichStringCellValue() + purchaseOrder.getContact().getPhoneNum();
        row.getCell(0).setCellValue(tel);

        //Fax
        row = sheet.getRow(6);
        String fax = row.getCell(0).getStringCellValue() + purchaseOrder.getContact().getFaxNum();
        row.getCell(0).setCellValue(fax);

        //PI Number
        String piNum = row.getCell(5).getStringCellValue() + purchaseOrder.getPiNumber();
        row.getCell(5).setCellValue(piNum);

        //submit Date
        String submitDate = row.getCell(15).getStringCellValue() + purchaseOrder.getOrderSubmitDate();
        row.getCell(15).setCellValue(submitDate);
        //Attn
        row = sheet.getRow(7);
        String attn = row.getCell(0).getStringCellValue() + purchaseOrder.getContact().getContactName();
        row.getCell(0).setCellValue(attn);


        //Add
        row = sheet.getRow(8);
        String add = row.getCell(0).getStringCellValue() + purchaseOrder.getContact().getAddress();
        row.getCell(0).setCellValue(add);

        //PONumber
        row = sheet.getRow(8);
        String poNum = row.getCell(5).getStringCellValue() + purchaseOrder.getPurchaseOrderNumber();
        row.getCell(5).setCellValue(poNum);

        String develiveryDate = row.getCell(15).getStringCellValue() + purchaseOrder.getDeliveryDate();
        row.getCell(15).setCellValue(develiveryDate);

        //shipTo
        row = sheet.getRow(9);
        String shipTo = row.getCell(5).getStringCellValue() + purchaseOrder.getShipTo();
        row.getCell(5).setCellValue(shipTo);

        //from 15, it is order items
        BufferedImage bufferImg = null;
        ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
        int itemRow = purchaseOrder.getOrderItems().size();
        InputStream stream = servletContext.getResourceAsStream("/resources/upload/BR2882_s.jpg");
        try {
            byte[] bytes = IOUtils.toByteArray(stream);
            int pictureIdx = wb.addPicture(bytes, Workbook.PICTURE_TYPE_JPEG);
            stream.close();
            CreationHelper helper = wb.getCreationHelper();
            // Create the drawing patriarch.  This is the top level container for all shapes.
            Drawing drawing = sheet.createDrawingPatriarch();
            List<PurchaseOrderItem> items = purchaseOrder.getOrderItemList();
            PurchaseOrderItem item = null;
            CellStyle cellStyle = wb.createCellStyle();
            cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
            cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
            cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
            cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
            //add a picture shape
            for (int i = 0; i < itemRow; i++) {
                item = items.get(i);
                pictureIdx = getPictureIndex(wb,item.getProductInfo().getPictureName(),servletContext);
                row = sheet.createRow(i + 15);
                row.setHeight((short) 1750);
                row.createCell(0).setCellStyle(cellStyle);
                ClientAnchor anchor = helper.createClientAnchor();
                //set top-left corner of the picture,
                //subsequent call of Picture#resize() will operate relative to it
                anchor.setCol1(0);
                anchor.setRow1(i + 15);
                Picture pict = drawing.createPicture(anchor, pictureIdx);

                //auto-size picture relative to its top-left corner
                pict.resize();

                Cell cell = null;

                setCellStringValue(row.createCell(1), purchaseOrder.getPurchaseOrderNumber(), cellStyle);
                setCellStringValue(row.createCell(2),item.getProductInfo().getCustomerProductCode(),cellStyle);
                setCellStringValue(row.createCell(3), item.getCompanyProductCode(), cellStyle);
                setCellStringValue(row.createCell(4),item.getProductInfo().getDescription(),cellStyle);

                if (item.getProductInfo().getCategory().isSupportSize()) {
                    cell = getCell(row,6,cellStyle);
                    if (item.getProductAmount().getSize4Amount() > 0) {
                        cell.setCellValue(item.getProductAmount().getSize4Amount());
                    }

                    cell = getCell(row,7,cellStyle);
                    if (item.getProductAmount().getSize5Amount() > 0) {
                        cell.setCellValue(item.getProductAmount().getSize5Amount());
                    }

                    cell = getCell(row,8,cellStyle);
                    if (item.getProductAmount().getSize6Amount() > 0) {
                        cell.setCellValue(item.getProductAmount().getSize6Amount());
                    }

                    cell = getCell(row,9,cellStyle);
                    if (item.getProductAmount().getSize7Amount() > 0) {
                        cell.setCellValue(item.getProductAmount().getSize7Amount());
                    }

                    cell = getCell(row,10,cellStyle);
                    if (item.getProductAmount().getSize8Amount() > 0) {
                        cell.setCellValue(item.getProductAmount().getSize8Amount());
                    }

                    cell = getCell(row,11,cellStyle);
                    if (item.getProductAmount().getSize9Amount() > 0) {
                        cell.setCellValue(item.getProductAmount().getSize9Amount());
                    }

                    cell = getCell(row,12,cellStyle);
                    if (item.getProductAmount().getSize10Amount() > 0) {
                        cell.setCellValue(item.getProductAmount().getSize10Amount());
                    }
                } else {
                    getCell(row,6,cellStyle);
                    getCell(row,7,cellStyle);
                    getCell(row,8,cellStyle);
                    getCell(row,9,cellStyle);
                    getCell(row,10,cellStyle);
                    getCell(row,11,cellStyle);
                    getCell(row,12,cellStyle);
                }

                cell = getCell(row,13,cellStyle);
                cell.setCellValue("$"+item.getProductAmount().getTotalAmount());
                cell = getCell(row,14,cellStyle);
                cell.setCellValue(item.getProductInfo().getCategory().getUnit());
                cell = getCell(row,15,cellStyle);
                cell.setCellValue("$"+item.getUnitPrice());
                cell = getCell(row,16,cellStyle);
                cell.setCellValue("$"+item.getTotalPrice());

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int getPictureIndex(Workbook wb,String picName, ServletContext servletContext){
        InputStream stream = servletContext.getResourceAsStream("/resources/upload/"+picName);
        int pictureIdx = 0;
        try {
            byte[] bytes = IOUtils.toByteArray(stream);
            pictureIdx = wb.addPicture(bytes, Workbook.PICTURE_TYPE_JPEG);

            stream.close();
        } catch (IOException e) {
            return 0;
        }
        return pictureIdx;
    }
    private static Cell getCell(Row row, int col, CellStyle cellStyle){
        Cell cell = row.createCell(col);
        cell.setCellStyle(cellStyle);
        return cell;
    }
    private static void setCellStringValue(Cell cell,String value,CellStyle cellStyle){
        cell.setCellStyle(cellStyle);
        cell.setCellValue(value);
    }

    private static void setCellIntValue(Cell cell,int value,CellStyle cellStyle){
        cell.setCellStyle(cellStyle);
        cell.setCellValue(value);
    }

    private static void setCellDoubleValue(Cell cell,double value,CellStyle cellStyle){
        cell.setCellStyle(cellStyle);
        cell.setCellValue(value);
    }
}
