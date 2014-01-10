package com.lottery.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lottery.dao.BaseDao;
import com.lottery.domain.NumberPool;
import com.lottery.domain.PrizeSerial;
import com.lottery.domain.User;
import com.lottery.service.NumberPoolService;
import com.lottery.service.PrizeSerialService;
@Service
public class NumberPoolServiceImpl implements NumberPoolService{

	@Autowired
	private BaseDao<NumberPool> dao;
	@Autowired
	private PrizeSerialService prizeSerialService;
	
	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		dao.executeUpdate("delete from NumberPool t", null);
	}

	@Override
	@Transactional
	public void saveList(int numberpoolfrom,int numberpoolto,String exclude) {
		// TODO Auto-generated method stub
		PrizeSerial prizeSerial = prizeSerialService.getActivePrizeSerial();
		if(prizeSerial!=null){
			boolean emptyflag = false;
			String tempexclude="";
			if(exclude!=null&&!"".equals(exclude)){
				emptyflag=true;
				tempexclude=";"+exclude+";";
			}
			for(int i=numberpoolfrom;i<=numberpoolto&&((i+"").equals(exclude)||exclude.indexOf(i+";")==0||exclude.indexOf(";"+i+";")>0||exclude.indexOf(";"+i)==exclude.lastIndexOf(";"+i));i++){
				if(emptyflag){
					if(exclude.equals(i+"")||tempexclude.indexOf(";"+i+";")>=0){
						continue;
					}
				}
				NumberPool numberPool = new NumberPool();
				numberPool.setNumber(i);
				numberPool.setPrizeSerial(prizeSerial);
				prizeSerial.getNumberpools().add(numberPool);
			}
		}
	}

	@Override
	public List<NumberPool> findAllActiveNumberPool() {
		// TODO Auto-generated method stub
		return dao.find("from NumberPool t where t.status=0", null);
	}

	@Override
	@Transactional
	public void updateNumberPoolStatus(List<NumberPool> list) {
		// TODO Auto-generated method stub
		if(list!=null&&list.size()>0){
			for(NumberPool numberPool : list){
				numberPool.setStatus(-1);
			}
		}
	}

	@Override
	@Transactional
	public void updateNumberPoolStatus(int suffixnum) {
		// TODO Auto-generated method stub
		 dao.executeUpdate("update NumberPool t set t.status=-1 where t.number like '%_"+suffixnum+"%'",null);
	}

}
