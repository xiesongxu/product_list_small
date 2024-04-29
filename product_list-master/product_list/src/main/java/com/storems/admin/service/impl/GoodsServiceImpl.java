package com.storems.admin.service.impl;


import com.storems.admin.dao.GoodsDao;
import com.storems.admin.entity.Goods;
import com.storems.admin.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    private GoodsDao goodsDao;

    
    public void batchDelete(String id) {
        goodsDao.batchDelete(id);
    }
    
    public Goods findById(String id) {
        return goodsDao.findById(id);
    }

    
    public void save(Goods goods) {
        goodsDao.save(goods);
    }

    
    public void update(Goods goods) {
        goodsDao.update(goods);
    }

    
    public List<Map> findGoodsPage(Map map) {
        return goodsDao.findGoodsPage(map);
    }

    
    public int findGoodsPageCount(Map map) {
        return goodsDao.findGoodsPageCount(map);
    }

    
    public List<Goods> findAll() {
        return goodsDao.findAll();
    }
}
