package com.ims.webapp.view;

import com.ims.webapp.view.util.ProductInfoFileResolver;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

@ManagedBean(name = "fileUploadController")
@ViewScoped
public class FileUploadController extends BaseView {

    private String fileCode;

    public void handleFileUpload(FileUploadEvent event) {
        FacesMessage msg = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
        if (!PhaseId.INVOKE_APPLICATION.equals(event.getPhaseId())) {
            event.setPhaseId(PhaseId.INVOKE_APPLICATION);
            event.queue();
        } else {
            String fileType = (String) event.getComponent().getAttributes().get("fileType");
            String param2 = this.getFileCode();
            try {
                File targetFolder = new File(this.getInitParamValueByKey("UPLOAD_IMAGE_PATH"));
                UploadedFile file = event.getFile();
                prodCategoryList = this.supportingDataService.loadProdCategoryList(true);
                if (fileType.equals("prodInfoList")) {
                    ProductInfoFileResolver.resolveProductInfoListFile(file.getInputstream(), file.getFileName(), prodCategoryList);
                    this.supportingDataService.updateCategoryList(prodCategoryList);
                    this.productList = this.supportingDataService.getProductInfoList(null);
                    return;
                }
                String newFileName = this.getServletContext().getRealPath("") + File.separator + "resources" + File.separator + "upload" + File.separator + file.getFileName();
                FileOutputStream fos = new FileOutputStream(new File(newFileName));
                InputStream is = file.getInputstream();
                int BUFFER_SIZE = 8192;
                byte[] buffer = new byte[BUFFER_SIZE];
                int a;
                while (true) {
                    a = is.read(buffer);
                    if (a < 0) break;
                    fos.write(buffer, 0, a);
                    fos.flush();
                }
                fos.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public String getFileCode() {
        return fileCode;
    }

    public void setFileCode(String fileCode) {
        this.fileCode = fileCode;
    }
}
