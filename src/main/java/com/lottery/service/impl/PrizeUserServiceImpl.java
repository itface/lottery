package com.lottery.service.impl;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lottery.dao.BaseDao;
import com.lottery.domain.Percentage;
import com.lottery.domain.Prize;
import com.lottery.domain.PrizeSerial;
import com.lottery.domain.PrizeUser;
import com.lottery.domain.SuffixNum;
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

//	@Override
//	public List<PrizeUser> findPrizeUser2(String serialname) {
//		// TODO Auto-generated method stub
//		String sql = null;
//		if(serialname!=null&&!serialname.equals("")){
//			sql = "from PrizeUser t where t.prizeserialname = '"+serialname+"' order by t.prizetime desc";
//			return dao.find(sql, null);
//		}
//		return null;
//	}

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

	@Override
	public void getUserPercentage(Map<String,Percentage> dymap,Map<String,Percentage> ywdymap,Map<String,Percentage> usernumbermap) {
		// TODO Auto-generated method stub
		List<PrizeUser> list = findCurrentPrizeUser();
		if(list!=null&&list.size()>0){
			int total = list.size();
			int persize =1000;
			DecimalFormat df = new DecimalFormat();
			String pattern = "#%";
			df.applyPattern(pattern);
			Map<String,Integer> tdymap = new HashMap<String,Integer>();
			Map<String,Integer> tywdymap = new HashMap<String,Integer>();
			Map<String,Integer> tusernumbermap = new HashMap<String,Integer>();
			for(PrizeUser user : list){
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
				String dy = dyit.next();
				int num = tdymap.get(dy);
				double n = num*1d/total;
				if(dymap.containsKey(dy)){
					Percentage percentage = dymap.get(dy);
					percentage.setPercentOfPrize(df.format(n));
				}else{
					Percentage percentage = new Percentage();
					percentage.setName(dy);
					percentage.setPercentOfPrize(df.format(n));
					dymap.put(dy, percentage);
				}
			}
			Iterator<String> ywdyit = tywdymap.keySet().iterator();
			while(ywdyit.hasNext()){
				String ywdy = ywdyit.next();
				int num = tywdymap.get(ywdy);
				double n = num*1d/total;
				if(ywdymap.containsKey(ywdy)){
					Percentage percentage = ywdymap.get(ywdy);
					percentage.setPercentOfPrize(df.format(n));
				}else{
					Percentage percentage = new Percentage();
					percentage.setName(ywdy);
					percentage.setPercentOfPrize(df.format(n));
					ywdymap.put(ywdy, percentage);
				}
			}
			Iterator<String> usernumberit = tusernumbermap.keySet().iterator();
			while(usernumberit.hasNext()){
				String usernumber = usernumberit.next();
				int num = tusernumbermap.get(usernumber);
				double n = num*1d/total;
				if(usernumbermap.containsKey(usernumber)){
					Percentage percentage = usernumbermap.get(usernumber);
					percentage.setPercentOfPrize(df.format(n));
				}else{
					Percentage percentage = new Percentage();
					percentage.setName(usernumber);
					percentage.setPercentOfPrize(df.format(n));
					usernumbermap.put(usernumber, percentage);
				}
			}
		}
	}
//
//	public List<PrizeUser> getPrizeUserList(){
//		PrizeSerial prizeSerial = prizeSerialService.getActivePrizeSerial();
//		if(prizeSerial!=null){
//			String serialnum = prizeSerial.getNum();
//			return dao.find("from PrizeUser t where t.prizeserialnum=?1 order by t.indexorder desc,t.usernumber asc", new Object[]{serialnum});
//		}
//		return null;
//	}

	@Override
	public boolean ifHavePrizeUserOfUser(String serialnum) {
		// TODO Auto-generated method stub
		long num =  dao.findTotalCount("select count(*) as num from PrizeUser t where t.prizeserialnum=?1 and t.prizetype='"+Prize.PRIZETYPE_USER+"'", new Object[]{serialnum});
		return num>0?true:false;
	}

	@Override
	public boolean ifHavePrizeUserOfNum(String serialnum) {
		// TODO Auto-generated method stub
		long num =  dao.findTotalCount("select count(*) as num from PrizeUser t where t.prizeserialnum=?1 and (t.prizetype='"+Prize.PRIZETYPE_NUMBER+"' or t.prizetype='"+Prize.PRIZETYPE_SUFFIXNUM+"')", new Object[]{serialnum});
		return num>0?true:false;
	}

	@Override
	public List<PrizeUser> findCurrentPrizeUserByType(long prizeid) {
		// TODO Auto-generated method stub
		return dao.find("from PrizeUser t where t.prizeid=?1", new Object[]{prizeid});
	}

	@Override
	public List<PrizeUser> getPrizeUserList(String type) {
		// TODO Auto-generated method stub
		PrizeSerial prizeSerial = prizeSerialService.getActivePrizeSerial();
		if(prizeSerial!=null){
			String serialnum = prizeSerial.getNum();
			if(Prize.PRIZETYPE_URL_USER.equals(type)){
				return dao.find("from PrizeUser t where t.prizeserialnum=?1 and t.prizetype=?2 order by t.indexorder desc,t.usernumber asc", new Object[]{serialnum,Prize.PRIZETYPE_USER});
			}else if(Prize.PRIZETYPE_URL_NOT_USER.equals(type)){
				return dao.find("from PrizeUser t where t.prizeserialnum=?1 and (t.prizetype=?2 or t.prizetype=?3) order by t.indexorder desc,t.usernumber asc", new Object[]{serialnum,Prize.PRIZETYPE_SUFFIXNUM,Prize.PRIZETYPE_NUMBER});
			}else{
				return dao.find("from PrizeUser t where t.prizeserialnum=?1 order by t.indexorder desc,t.usernumber asc", new Object[]{serialnum});
			}
		}
		return null;
	}

	@Override
	public long countPrizeUserByType(long prizeid,String serialnum) {
		// TODO Auto-generated method stub
		return dao.findTotalCount("select count(distinct t.indexorder) as num from PrizeUser t where t.prizeid=?1 and t.prizeserialnum=?2", new Object[]{prizeid,serialnum});
	}
}
