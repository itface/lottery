package com.lottery.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lottery.dao.BaseDao;
import com.lottery.domain.BackupUser;
import com.lottery.domain.User;
import com.lottery.service.BackupUserService;
@Service
public class BackupUserServiceImpl implements BackupUserService{

	@Autowired
	private BaseDao<BackupUser> dao;

	
	@Override
	@Transactional
	public void saveList(List<User> list,String prizeSerialNum) {
		// TODO Auto-generated method stub
		if(list!=null&&list.size()>0){
			for(User user : list){
				dao.persist(new BackupUser(user,prizeSerialNum));
			}
		}
		
	}

}
