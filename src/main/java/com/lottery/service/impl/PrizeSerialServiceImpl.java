package com.lottery.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lottery.dao.BaseDao;
import com.lottery.domain.PrizeSerial;
import com.lottery.domain.User;
import com.lottery.service.BackupUserService;
import com.lottery.service.PrizeSerialService;
import com.lottery.service.UserService;
@Service
public  class PrizeSerialServiceImpl implements PrizeSerialService{

	@Autowired
	private BaseDao<PrizeSerial> dao;
	@Autowired
	private BackupUserService backupUserService;
	@Autowired
	private UserService userService;
	
	@Override
	public PrizeSerial getActivePrizeSerial() {
		// TODO Auto-generated method stub
		List<PrizeSerial> list = dao.find("from PrizeSerial t where t.status=0", null);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}

	@Override
	@Transactional
	public void stopActivePrizeSerial() {
		// TODO Auto-generated method stub
		PrizeSerial prizeSerial = this.getActivePrizeSerial();
		if(prizeSerial!=null){
			List<User> users = userService.findAll();
			backupUserService.saveList(users, prizeSerial.getNum());
			userService.deleteAll();
		}
		dao.executeUpdate("update PrizeSerial t set t.enddate=?1 where t.status=0", new Object[]{new Date()});
		dao.executeUpdate("update PrizeSerial t set t.status=-1", null);
		
	}

	@Override
	@Transactional
	public PrizeSerial addActivePrizeSerial(PrizeSerial prizeSerial) {
		// TODO Auto-generated method stub
//		dao.executeUpdate("update PrizeSerial t set t.enddate=getdate() where t.status=0", null);
//		dao.executeUpdate("update PrizeSerial t set t.status=-1", null);
		stopActivePrizeSerial();
		dao.persist(prizeSerial);
		return prizeSerial;
	}

	@Override
	@Transactional
	public void updatePrizeSerialName(long id, String name) {
		// TODO Auto-generated method stub
		dao.executeUpdate("update PrizeSerial t set t.name=?1 where t.id=?2", new Object[]{name,id});
	}

	@Override
	@Transactional
	public void updatePrizeCount(long id) {
		// TODO Auto-generated method stub
		dao.executeUpdate("update PrizeSerial t set t.prizecount=t.prizecount+1 where t.id=?1", new Object[]{id});
	}

	@Override
	public List<PrizeSerial> getInActivePrizeSerial() {
		// TODO Auto-generated method stub
		return dao.find("from PrizeSerial t where t.status!=0", null);
	}

	@Override
	public PrizeSerial getPrizeSerialByNum(String num) {
		// TODO Auto-generated method stub
		List<PrizeSerial> list = dao.find("from PrizeSerial t where t.num=?1", new Object[]{num});
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}

}
