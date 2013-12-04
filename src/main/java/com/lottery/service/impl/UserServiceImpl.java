package com.lottery.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.lottery.dao.BaseDao;
import com.lottery.domain.User;
import com.lottery.service.UserService;
import com.lottery.util.ExcelReader;
@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private BaseDao<User> dao;
	@Override
	@Transactional
	public void impUser(MultipartFile mf) throws IOException, SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		// TODO Auto-generated method stub
		InputStream is = mf.getInputStream();
		ExcelReader excelReader = new ExcelReader();
		String[] fields = excelReader.readExcelTitle(is);
		 is = mf.getInputStream();
	     Set<User> users = excelReader.readExcelContent(is,fields);
	     if(users!=null&&users.size()>0){
	    	 dao.saveList(users);
	     }
	}
	@Override
	public List<User> findAll() {
		// TODO Auto-generated method stub
		return dao.find("from User t", null);
	}
	@Override
	@Transactional
	public void updateUsetStatus(List<User> users) {
		// TODO Auto-generated method stub
		if(users!=null&&users.size()>0){
			for(User user : users){
				user.setStatus(-1);
			}
		}
	}
	@Override
	public List<User> findAllActiveUser() {
		// TODO Auto-generated method stub
		return dao.find("from User t where t.status=0", null);
	}

}
