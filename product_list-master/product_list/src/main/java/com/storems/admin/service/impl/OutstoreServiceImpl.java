package com.storems.admin.service.impl;



import com.storems.admin.dao.InstoreDao;
import com.storems.admin.dao.OutstoreDao;
import com.storems.admin.entity.Instore;
import com.storems.admin.entity.Outstore;
import com.storems.admin.service.InstoreService;
import com.storems.admin.service.OutstoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class OutstoreServiceImpl implements OutstoreService {
   // @Autowired
    private OutstoreDao atstoreDao;

    
    public void batchDelete(String id) {
        atstoreDao.batchDelete(id);
    }
    
    public Outstore findById(String id) {
        return atstoreDao.findById(id);
    }

    
    public void save(Outstore outstore) {
        atstoreDao.save(outstore);
    }

    
    public void update(Outstore outstore) {
        atstoreDao.update(outstore);
    }

    
    public List<Map> findOutstorePage(Map map) {
        return atstoreDao.findOutstorePage(map);
    }

    
    public int findOutstorePageCount(Map map) {
        return atstoreDao.findOutstorePageCount(map);
    }


    
    public List<Outstore> statisticalByParam(Map map) {
        return atstoreDao.statisticalByParam(map);
    }
}
