package com.storems.admin.service.impl;


import com.storems.admin.dao.AtstoreDao;
import com.storems.admin.dao.GoodsDao;
import com.storems.admin.entity.Atstore;
import com.storems.admin.entity.Goods;
import com.storems.admin.service.AtstoreService;
import com.storems.admin.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AtstoreServiceImpl implements AtstoreService {
   // @Autowired
    private AtstoreDao atstoreDao;

    
    public void batchDelete(String id) {
        atstoreDao.batchDelete(id);
    }
    
    public Atstore findById(String id) {
        return atstoreDao.findById(id);
    }

    
    public void save(Atstore atstore) {
        atstoreDao.save(atstore);
    }

    
    public void update(Atstore atstore) {
        atstoreDao.update(atstore);
    }

    
    public List<Map> findAtstorePage(Map map) {
        return atstoreDao.findAtstorePage(map);
    }

    
    public int findAtstorePageCount(Map map) {
        return atstoreDao.findAtstorePageCount(map);
    }

    
    public Atstore getByRelationId(String relationId) {
        return atstoreDao.getByRelationId(relationId);
    }

    
    public Atstore getByGoodsId(String goodsId) {
        return atstoreDao.getByGoodsId(goodsId);
    }
}
