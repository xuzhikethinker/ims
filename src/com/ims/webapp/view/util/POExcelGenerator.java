package com.ims.webapp.view.util;

import com.ims.domain.order.PurchaseOrder;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.util.IOUtils;

import javax.servlet.ServletContext;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

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
        InputStream stream = servletContext.getResourceAsStream("/resources/upload/BR2882_s.jpg");
        try {
            byte[] bytes = IOUtils.toByteArray(stream);
            int pictureIdx = wb.addPicture(bytes, Workbook.PICTURE_TYPE_JPEG);
            stream.close();
            HSSFClientAnchor anchor1 = new HSSFClientAnchor(0,0,512,255,(short) 1,1,(short)10,20);
            CreationHelper helper = wb.getCreationHelper();
            // Create the drawing patriarch.  This is the top level container for all shapes.
            Drawing drawing = sheet.createDrawingPatriarch();
            //add a picture shape
            for (int i = 15; i < 25; i++) {
                ClientAnchor anchor = helper.createClientAnchor();
                //set top-left corner of the picture,
                //subsequent call of Picture#resize() will operate relative to it
                anchor.setCol1(0);
                anchor.setRow1(i);
                anchor.setDx1(30);
                anchor.setDy1(30);
                Picture pict = drawing.createPicture(anchor, pictureIdx);

                //auto-size picture relative to its top-left corner
                pict.resize();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
