package com.storems.admin.entity;

import java.io.Serializable;

/**
 * 商品库存
 */
public class ProductAllList implements Serializable {
    //流水号
    private String serialno;

    //商品id
    private String productID;

    //商品总数
    private String productSum;

    public String getSerialno() {
        return serialno;
    }

    public void setSerialno(String serialno) {
        this.serialno = serialno;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductSum() {
        return productSum;
    }

    public void setProductSum(String productSum) {
        this.productSum = productSum;
    }
}
