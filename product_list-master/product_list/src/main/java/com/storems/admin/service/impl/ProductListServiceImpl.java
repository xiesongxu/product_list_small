package com.storems.admin.service.impl;

import com.storems.admin.dao.ProductListDao;
import com.storems.admin.entity.ProductList;
import com.storems.admin.service.ProductListService;
import com.storems.admin.utils.ProductUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 商品信息服务层实现类
 */
@Service
public class ProductListServiceImpl implements ProductListService {

    @Autowired
    ProductListDao productListDao;

    /**
     * 调用dao层进行批量保存
     * @param products
     * @return
     */
    @Transactional
    public String batchSave(List<ProductList> products) {
        try {
            productListDao.batchSave(products);
        } catch (Exception e) {
            return "批量保存信息失败";
        }
        return "批量保存信息成功";
    }

    /**
     * 调用dao层进行批量修改
     * @param products
     * @return
     */
    @Transactional
    public String batchUpdate(List<ProductList> products) {
        String curDate = ProductUtil.getCurDate(); //当前时间
        double productSum = 0; //出货商品数量
        double productValue = 0; //出货商品总价值
        double meanValue = 0; //每个商品单价
        String productID = ""; //出货商品id
        List<ProductList> productLists = null; //保质期内的入库商品
        //遍历导入的每项出货商品信息
        for (ProductList product : products) {
            //获取出货商品id
            productID = product.getProductID();
            //获取出货商品数量
            productSum = Double.valueOf(product.getProductSum());
            //获取出货商品总价值
            productValue = Double.valueOf(product.getProductValue());
            //得到每个商品单价
            meanValue = productValue/productSum;
            //根据商品id获取保质期内的入库商品
            productLists = productListDao.findByProductId(productID, curDate);
            //遍历存在的入库商品
            for (ProductList pd : productLists) {
                //获取入库商品的数量
                double sum = Double.valueOf(pd.getProductSum());
                //减去入库商品，得到还存在的出库商品
                productSum = productSum-sum;
                //出库商品已经被全部计算
                if (productSum<0) {
                    //重新计算入库商品数
                    pd.setProductSum(String.valueOf(-productSum));
                    //重新计算入库商品价值
                    pd.setProductValue(String.valueOf(meanValue*(-productSum)));
                    //设置修改时间
                    pd.setUpdateDate(curDate);
                    pd.setRemark(product.getRemark());
                    //新增一条出货数据
                    ProductList productList = new ProductList();
                    productList.setSerialno(ProductUtil.getSerialNo());
                    productList.setProductID(productID);
                    productList.setProductName(product.getProductName());
                    productList.setProductValue(String.valueOf(meanValue*productSum));
                    productList.setProductSum(String.valueOf(productSum));
                    productList.setRemark(product.getRemark());
                    productList.setFlag("2");
                    productList.setExpireDate(product.getExpireDate());
                    productList.setInputDate(curDate);
                    productList.setUpdateDate(curDate);
                    productList.setUserid("system");
                    productListDao.save(productList);
                    break;
                } else { //出库商品还有部分未计算，或刚好全部入库商品全部出库
                    //把标签设为出库
                    pd.setFlag("2");
                    //设置修改时间
                    pd.setUpdateDate(curDate);
                    pd.setRemark(product.getRemark());
                }
                //商品刚好出货完毕退出
                if (productSum==0) {
                    break;
                }
            }
            productListDao.batchUpdate(productLists);
        }
        return "批量出库成功";
    }

    public List<ProductList> findProductSafeListPage(Map map) {
        return null;
    }

    /**
     * 分页查询在保质期内商品列表
     * @param map
     * @return
     */
    public List<Map> findProductList(Map map) {
        return productListDao.findProductList(map);
    }

    /**
     * 查询在保质期内商品的总数
     * @param curDate
     * @return
     */
    public int findSumProductList(String curDate) {
        return productListDao.findSumProductList(curDate);
    }

    /**
     * 通过流水号查找对应商品
     * @param serialno
     * @return
     */
    public ProductList findProductBySerialno(String serialno) {
        return productListDao.findProductBySerialno(serialno);
    }

    /**
     * 新增一条商品数据
     * @param productList
     */
    public void save(ProductList productList) {
        productListDao.save(productList);
    }

    /**
     * 修改数据
     * @param product
     */
    public void update(ProductList product) {
        productListDao.update(product);
    }
}
