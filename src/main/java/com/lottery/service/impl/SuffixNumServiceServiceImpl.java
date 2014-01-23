package com.lottery.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lottery.dao.BaseDao;
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
			dao.executeUpdate("delete from SuffixNum t where t.prizeSerial.id=?1", new Object[]{prizeSerial.getId()});
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

	@Override
	public long getYcNum(long prizeid) {
		// TODO Auto-generated method stub
		
		return dao.findTotalCount("select count(*) as num from SuffixNum t where t.prizeid=?1", new Object[]{prizeid});
	}
}
