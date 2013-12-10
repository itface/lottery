package com.lottery.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lottery.dao.BaseDao;
import com.lottery.domain.PrizeSerial;
import com.lottery.domain.PrizeUser;
import com.lottery.service.PrizeSerialService;
import com.lottery.service.PrizeUserService;
@Service
public class PrizeUserServiceImpl implements PrizeUserService{

	@Autowired
	private BaseDao<PrizeUser> dao;
	@Autowired
	private PrizeSerialService prizeSerialService;
	@Override
	@Transactional
	public void addPrizeUser(PrizeUser prizeUser) {
		// TODO Auto-generated method stub
		dao.persist(prizeUser);
	}

	@Override
	public List<PrizeUser> findPrizeUser(String serialname) {
		// TODO Auto-generated method stub
		String sql = null;
		if(serialname!=null&&!serialname.equals("")){
			sql = "from PrizeUser t where t.prizeserialname = '"+serialname+"' order by t.prizetime desc";
			return dao.find(sql, null);
		}
		return null;
	}

	@Override
	public List<PrizeUser> findCurrentPrizeUser() {
		// TODO Auto-generated method stub
		PrizeSerial prizeSerial = prizeSerialService.getActivePrizeSerial();
		if(prizeSerial!=null){
			String serialnum = prizeSerial.getNum();
			return dao.find("from PrizeUser t where t.prizeserialnum=?1 order by t.indexorder asc", new Object[]{serialnum});
		}
		return null;
	}

	@Override
	public List<PrizeUser> findPrizeUserBySerialnum(String serialnum) {
		// TODO Auto-generated method stub
		return dao.find("from PrizeUser t where t.prizeserialnum=?1 order by t.indexorder asc", new Object[]{serialnum});

	}

}
