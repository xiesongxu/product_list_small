package com.storems.admin.service.impl;



import com.storems.admin.dao.InstoreDao;
import com.storems.admin.entity.Instore;
import com.storems.admin.service.InstoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class InstoreServiceImpl implements InstoreService {
   // @Autowired
    private InstoreDao atstoreDao;

    
    public void batchDelete(String id) {
        atstoreDao.batchDelete(id);
    }
    
    public Instore findById(String id) {
        return atstoreDao.findById(id);
    }

    
    public void save(Instore instore) {
        atstoreDao.save(instore);
    }

    
    public void update(Instore instore) {
        atstoreDao.update(instore);
    }

    
    public List<Map> findInstorePage(Map map) {
        return atstoreDao.findInstorePage(map);
    }

    
    public int findInstorePageCount(Map map) {
        return atstoreDao.findInstorePageCount(map);
    }


    
    public List<Instore> statisticalByParam(Map map) {
        return atstoreDao.statisticalByParam(map);
    }
}
