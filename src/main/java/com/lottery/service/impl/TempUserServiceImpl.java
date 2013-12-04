package com.lottery.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lottery.dao.BaseDao;
import com.lottery.domain.TempUser;
import com.lottery.domain.User;
import com.lottery.service.TempUserService;
@Service
public class TempUserServiceImpl implements TempUserService{

	@Autowired
	private BaseDao<TempUser> dao;
	
	@Override
	@Transactional
	public void saveList(List<User> list) {
		// TODO Auto-generated method stub
		if(list!=null&&list.size()>0){
			for(User user : list){
				dao.persist(new TempUser(user));
			}
		}
		
	}

}
