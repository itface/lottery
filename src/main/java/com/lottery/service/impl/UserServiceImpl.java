package com.lottery.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.lottery.dao.BaseDao;
import com.lottery.domain.Percentage;
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
		this.deleteAll();
		tempUserService.deleteAll();
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
	@Override
	public long countTotalUser() {
		// TODO Auto-generated method stub
		return dao.findTotalCount("select count(t.id) as num from User t", null);
	}
	@Override
	public void getUserPercentage(Map<String,Percentage> dymap,Map<String,Percentage> ywdymap,Map<String,Percentage> usernumbermap){
		// TODO Auto-generated method stub
		List<User> list = this.findAll();
		if(list!=null&&list.size()>0){
			int total = list.size();
			int persize =1000;
			DecimalFormat df = new DecimalFormat();
			String pattern = "#%";
			df.applyPattern(pattern);
			Map<String,Integer> tdymap = new HashMap<String,Integer>();
			Map<String,Integer> tywdymap = new HashMap<String,Integer>();
			Map<String,Integer> tusernumbermap = new HashMap<String,Integer>();
			for(User user : list){
				String dy = user.getRegion();
				String ywdy = user.getYwdy2();
				String usernumber = user.getUsernumber();
				while(usernumber.indexOf('0')==0){
					usernumber=usernumber.substring(1);
				}
				int num = Integer.parseInt(usernumber);
				if(tdymap.containsKey(dy)){
					tdymap.put(dy, tdymap.get(dy)+1);
				}else{
					tdymap.put(dy, 1);
				}
				if(tywdymap.containsKey(ywdy)){
					tywdymap.put(ywdy, tywdymap.get(ywdy)+1);
				}else{
					tywdymap.put(ywdy, 1);
				}
				int rangenum = num/persize;
				String range = (rangenum*persize+1)+"-"+((rangenum+1)*persize);
				if(tusernumbermap.containsKey(range)){
					tusernumbermap.put(range, tusernumbermap.get(range)+1);
				}else{
					tusernumbermap.put(range, 1);
				}
			}
			Iterator<String> dyit = tdymap.keySet().iterator();
			while(dyit.hasNext()){
				Percentage percentage = new Percentage();
				String dy = dyit.next();
				int num = tdymap.get(dy);
				double n = num*1d/total;
				percentage.setName(dy);
				percentage.setPercentOfAll(df.format(n));
				dymap.put(dy, percentage);
			}
			Iterator<String> ywdyit = tywdymap.keySet().iterator();
			while(ywdyit.hasNext()){
				Percentage percentage = new Percentage();
				String ywdy = ywdyit.next();
				int num = tywdymap.get(ywdy);
				double n = num*1d/total;
				percentage.setName(ywdy);
				percentage.setPercentOfAll(df.format(n));
				ywdymap.put(ywdy,percentage);
			}
			Iterator<String> usernumberit = tusernumbermap.keySet().iterator();
			while(usernumberit.hasNext()){
				Percentage percentage = new Percentage();
				String usernumber = usernumberit.next();
				int num = tusernumbermap.get(usernumber);
				double n = num*1d/total;
				percentage.setName(usernumber);
				percentage.setPercentOfAll(df.format(n));
				usernumbermap.put(usernumber, percentage);
			}
		}
	}
	@Override
	@Transactional
	public void deleteAll() {
		// TODO Auto-generated method stub
		dao.executeUpdate("delete from User t", null);
	}

}
