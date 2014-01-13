package com.lottery.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lottery.dao.BaseDao;
import com.lottery.domain.NumberPool;
import com.lottery.domain.PrizeSerial;
import com.lottery.domain.SuffixNum;
import com.lottery.service.PrizeSerialService;
import com.lottery.service.SuffixNumService;
@Service
public class SuffixNumServiceServiceImpl implements SuffixNumService{

	@Autowired
	private BaseDao<SuffixNum> dao;
	@Autowired
	private PrizeSerialService prizeSerialService;
	
	@Override
	@Transactional
	public void deleteAll() {
		// TODO Auto-generated method stub
		PrizeSerial prizeSerial = prizeSerialService.getActivePrizeSerial();
		if(prizeSerial!=null){
			prizeSerial.getSuffixnums().clear();
		}
	}

	@Override
	@Transactional
	public void saveList(int suffixnumfrom,int suffixnumto,String exclude) {
		// TODO Auto-generated method stub
		PrizeSerial prizeSerial = prizeSerialService.getActivePrizeSerial();
		if(prizeSerial!=null){
			boolean emptyflag = false;
			String tempexclude="";
			if(exclude!=null&&!"".equals(exclude)){
				emptyflag=true;
				tempexclude=";"+exclude+";";
			}
			for(int i=suffixnumfrom;i<=suffixnumto;i++){
				if(emptyflag){
					if(exclude.equals(i+"")||tempexclude.indexOf(";"+i+";")>=0){
						continue;
					}
				}
				SuffixNum suffixNum = new SuffixNum();
				suffixNum.setSuffixnum(i);
				suffixNum.setPrizeSerial(prizeSerial);
				prizeSerial.getSuffixnums().add(suffixNum);
			}
		}
	}
}
