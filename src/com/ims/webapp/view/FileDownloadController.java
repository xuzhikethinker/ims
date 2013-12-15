package com.ims.webapp.view;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.InputStream;

/**
 * Created by Administrator on 13-12-6.
 */
@ManagedBean(name = "fileDownloadController")
@ViewScoped
public class FileDownloadController extends BaseView {
    private StreamedContent file;

    public FileDownloadController() {
        InputStream stream = this.getServletContext().getResourceAsStream("/resources/download/productInfo.xlsx");
        file = new DefaultStreamedContent(stream, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "productInfo.xlsx");

//        InputStream stream = this.getServletContext().getResourceAsStream("/resources/download/BMW.jpg");
//        file = new DefaultStreamedContent(stream, "image/jpg", "BMW.jpg");
    }

    public StreamedContent getFile() {
        return file;
    }
}
