package com.lottery.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lottery.dao.BaseDao;
import com.lottery.domain.PrizeSerial;
import com.lottery.service.PrizeSerialService;
@Service
public class PrizeSerialServiceImpl implements PrizeSerialService{

	@Autowired
	private BaseDao<PrizeSerial> dao;
	
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
		dao.executeUpdate("update PrizeSerial t set t.status=0 , t.name=?1 where t.id=?2", new Object[]{name,id});
	}

}
