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
import com.lottery.service.TempUserService;
import com.lottery.service.UserService;
import com.lottery.util.ExcelReader;
import com.lottery.util.RandomNumUtil;
@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private BaseDao<User> dao;
	@Autowired
	private TempUserService tempUserService;
	
	
	@Override
	@Transactional
	public void impUser(MultipartFile mf) throws IOException, SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		// TODO Auto-generated method stub
		dao.executeUpdate("delete from User t", null);
		dao.executeUpdate("delete from TempUser t", null);
		InputStream is = mf.getInputStream();
		ExcelReader excelReader = new ExcelReader();
		RandomNumUtil util = new RandomNumUtil();
		String[] fields = excelReader.readExcelTitle(is);
		 is = mf.getInputStream();
	     List<User> users = excelReader.readExcelContent(is,fields);
	     if(users!=null&&users.size()>0){
	    	 tempUserService.saveList(users);
	    	 List<User> randomUser = util.getRandomList(users);
	    	 dao.saveList(randomUser);
	     }
	}
	@Override
	public List<User> findAll() {
		// TODO Auto-generated method stub
		return dao.find("from User t", null);
	}
	@Override
	@Transactional
	public void updateUserStatus(List<User> users) {
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
	@Override
	@Transactional
	public void updateAllUserStatus() {
		// TODO Auto-generated method stub
		dao.executeUpdate("update User t set t.status=0", null);
	}
	@Override
	public long findActiveUserNum() {
		// TODO Auto-generated method stub
		long num = dao.findTotalCount("select count(t.id) as num from User t where t.status=0", null);
		return num;
	}

}
