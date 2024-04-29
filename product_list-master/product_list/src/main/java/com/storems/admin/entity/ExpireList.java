package com.storems.admin.entity;

import java.io.Serializable;

/**
 * 过期商品或在保质期商品信息
 */
public class ExpireList  implements Serializable {
    //流水号
    private String serialno;
    //product_list关联流水号
    private String relativeSerialno;
    //商品ID
    private String productID;
    //商品数量
    private String productSum;
    //输入时间
    private String inputDate;
    //过期时间（几天）
    private String expireDate;

    public String getSerialno() {
        return serialno;
    }

    public void setSerialno(String serialno) {
        this.serialno = serialno;
    }

    public String getRelativeSerialno() {
        return relativeSerialno;
    }

    public void setRelativeSerialno(String relativeSerialno) {
        this.relativeSerialno = relativeSerialno;
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

    public String getInputDate() {
        return inputDate;
    }

    public void setInputDate(String inputDate) {
        this.inputDate = inputDate;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }
}
