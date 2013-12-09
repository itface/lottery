package com.lottery.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lottery.dao.BaseDao;
import com.lottery.domain.PrizeUser;
import com.lottery.service.PrizeUserService;
@Service
public class PrizeUserServiceImpl implements PrizeUserService{

	@Autowired
	private BaseDao<PrizeUser> dao;
	
	@Override
	@Transactional
	public void addPrizeUser(PrizeUser prizeUser) {
		// TODO Auto-generated method stub
		dao.persist(prizeUser);
	}

	@Override
	public List<PrizeUser> findPrizeUser(String serialnum) {
		// TODO Auto-generated method stub
		String sql = null;
		if(serialnum!=null&&!serialnum.equals("")){
			sql = "from PrizeUser t where t.sm="+serialnum+" order by t.prizetime desc";
		}else{
			sql = "from PrizeUser t order by t.prizetime desc";
		}
		return dao.find(sql, null);
	}

}
