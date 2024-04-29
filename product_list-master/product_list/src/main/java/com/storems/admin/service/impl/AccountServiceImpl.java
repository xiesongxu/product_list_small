package com.storems.admin.service.impl;



import com.storems.admin.utils.ResultType;
//import com.zcs.util.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.storems.admin.dao.AccountDao;
import com.storems.admin.entity.Account;
import com.storems.admin.service.AccountService;

import java.util.List;
import java.util.Map;

@Service
public class AccountServiceImpl implements AccountService {
    //@Autowired
    private AccountDao accountDao;
    
	public Account login(Account account) {
		System.out.println("执行登录");
		System.out.println(account.getUserName());
		System.out.println(account.getPassword());
		account.setUserName(account.getUserName().toLowerCase());
//		account.setPassword(MD5.getMd5(account.getPassword()));
		return accountDao.login(account);
	}
    
    public void batchDelete(String id) {
        accountDao.batchDelete(id);
    }
    
    public Account findById(String id) {
        return accountDao.findById(id);
    }

    
    public void save(Account account) {
        accountDao.save(account);
    }

    
    public void update(Account account) {
        accountDao.update(account);
    }

    
    public List<Map> findAccountPage(Map map) {
        return accountDao.findAccountPage(map);
    }

    
    public int findAccountPageCount(Map map) {
        return accountDao.findAccountPageCount(map);
    }

}
