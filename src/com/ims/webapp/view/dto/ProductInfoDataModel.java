package com.ims.webapp.view.dto;

import com.ims.domain.support.ProductInfo;
import org.primefaces.model.SelectableDataModel;

import javax.faces.model.ListDataModel;
import java.util.List;

/**
 * Created by Administrator on 13-12-19.
 */
public class ProductInfoDataModel extends ListDataModel<ProductInfo> implements SelectableDataModel<ProductInfo> {

    public ProductInfoDataModel() {
    }

    public ProductInfoDataModel(List<ProductInfo> list) {
        super(list);
    }

    @Override
    public Object getRowKey(ProductInfo productInfo) {
        return productInfo.getProductCode();
    }

    @Override
    public ProductInfo getRowData(String rowKey) {
        List<ProductInfo> productInfoList = (List<ProductInfo>)this.getWrappedData();
        for(ProductInfo productInfo:productInfoList){
            if(productInfo.getProductCode().equalsIgnoreCase(rowKey)){
                return  productInfo;
            }
        }
        return null;
    }
}
