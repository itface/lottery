package com.lottery.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
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
	public String findPrizeUserHtmlOfCurrent() {
		// TODO Auto-generated method stub
		PrizeSerial prizeSerial = prizeSerialService.getActivePrizeSerial();
		if(prizeSerial!=null){
			return this.getPrizeUserHtml(prizeSerial);
		}
		return "";
	}

	@Override
	public String findPrizeUserHtmlOfHistory(String serialnum) {
		// TODO Auto-generated method stub
		PrizeSerial prizeSerial = prizeSerialService.getPrizeSerialByNum(serialnum);
		if(prizeSerial!=null){
			return this.getPrizeUserHtml(prizeSerial);
		}
		return "";
	}
	private String getPrizeUserHtml(PrizeSerial prizeSerial){
		StringBuffer sb = new StringBuffer();
		if(prizeSerial!=null){
			Set<Prize> prizes = prizeSerial.getPrizes();
			if(prizes!=null&&prizes.size()>0){
				sb.append("<table class='userList' border='0' cellspacing='0' cellpadding='0' width='100%'>");
				sb.append("<tr class='wr-table-hd-inner'><td colspan='6' style='text-align:left;padding-left:20px'>抽奖活动：").append(prizeSerial.getName()).append("（").append(prizeSerial.getNum()).append("）</td></tr>");
				List<Prize> prizelist = new ArrayList<Prize>();
				prizelist.addAll(prizes);
				Collections.sort(prizelist);
				for(Prize prize : prizelist){
					List<PrizeUser> templist = this.findPrizeUserById(prize.getId());
					if(templist!=null&&templist.size()>0){
						Collections.sort(templist);
						int count = 0;
						Set<Integer> indexorders = new HashSet<Integer>();
						boolean numberflag = false;
						for(PrizeUser pu : templist){
							if(Prize.PRIZETYPE_SUFFIXNUM.equals(pu.getPrizetype())){
								String suffixnum = pu.getUid().substring(pu.getUid().length()-1);
								sb.append("<tr>");
								sb.append("<td colspan='6'>&nbsp;</td>");
								sb.append("</tr>");
								sb.append("<tr class='wr-table-hd-inner'>");
								sb.append("<td colspan='6' style='text-align:left;padding-left:20px'>");
								sb.append(pu.getPrizename()).append(" 第").append(pu.getSameprizeindexorder()).append("次 <span style='color:red;'>尾号：").append(suffixnum).append("</span>");
								sb.append("</td>");
								sb.append("</tr>");
							}else if(Prize.PRIZETYPE_NUMBER.equals(pu.getPrizetype())){
								numberflag=true;
								if(!indexorders.contains(pu.getIndexorder())){
									sb.append("<tr>");
									sb.append("<td colspan='6'>&nbsp;</td>");
									sb.append("</tr>");
									sb.append("<tr class='wr-table-hd-inner'>");
									sb.append("<td colspan='6' style='text-align:left;padding-left:20px'>").append(pu.getPrizename()).append(" 第").append(pu.getSameprizeindexorder()).append("次 ");
									sb.append("</td>");
									sb.append("</tr>");
									sb.append("<tr class='wr-table-td-inner wr-table-tr-row' style='line-height:10px'>");
									sb.append("<td>").append(pu.getUid()).append("</td>");
									indexorders.add(pu.getIndexorder());
								}else if(count%5!=0){
									sb.append("<td>").append(pu.getUid()).append("</td>");
								}else{
									sb.append("</tr>");
									sb.append("<tr  class='wr-table-td-inner wr-table-tr-row'>");
									sb.append("<td>").append(pu.getUid()).append("</td>");
								}
								count++;
							}else if(Prize.PRIZETYPE_USER.equals(pu.getPrizetype())){
								if(!indexorders.contains(pu.getIndexorder())){
									indexorders.add(pu.getIndexorder());
									count=0;
									sb.append("<tr>");
									sb.append("<td colspan='6'>&nbsp;</td>");
									sb.append("</tr>");
									sb.append("<tr class='wr-table-hd-inner' style='height:50px; line-height:50px; '>");
									sb.append("<td colspan='6' style='text-align:left;padding-left:20px;'>").append(pu.getPrizename()).append(" 第").append(pu.getSameprizeindexorder()).append("次 ");
									sb.append("</tr>");
									sb.append("<tr class='wr-table-hd-inner' style='height:30px; line-height:30px'>");
									sb.append("<td width='100px'>序号</td>");
									sb.append("<td width='130px'>姓名</td>");
									sb.append("<td width='130px'>员工编号</td>");
									sb.append("<td width='130px'>地域</td>");
									sb.append("<td width='130px'>业务单元</td>");
									sb.append("</tr>");
								}
								sb.append("<tr class='wr-table-td-inner wr-table-tr-row'>");
								sb.append("<td>").append(++count).append("</td>");
								sb.append("<td>").append(pu.getUsername()).append("</td>");
								sb.append("<td>").append(pu.getUsernumber()).append("</td>");
								sb.append("<td>").append(pu.getRegion()).append("</td>");
								sb.append("<td>").append(pu.getYwdy()).append("</td>");
								sb.append("</tr>");
							}
						}
						if(numberflag&&count%5!=0){
							sb.append("</tr>");
						}
					}
				}
				sb.append("</table>");
			}
		}
		return sb.toString();
	}
	@Override
	public List<PrizeUser> findPrizeUserOfCurrentByType(String prizetype) {
		// TODO Auto-generated method stub
		PrizeSerial prizeSerial = prizeSerialService.getActivePrizeSerial();
		if(prizeSerial!=null){
			String serialnum = prizeSerial.getNum();
			return this.findPrizeUserOfHistoryByType(serialnum, prizetype);
		}
		return null;
	}

	@Override
	public List<PrizeUser> findPrizeUserOfHistoryByType(String serialnum,String prizetype) {
		// TODO Auto-generated method stub
		if(prizetype!=null&&!"".equals(prizetype)){
			return dao.find("from PrizeUser t where t.prizeserialnum=?1 and t.prizetype=?2 order by t.indexorder asc", new Object[]{serialnum,prizetype});
		}else{
			return dao.find("from PrizeUser t where t.prizeserialnum=?1 order by t.indexorder asc", new Object[]{serialnum});
		}
	}

	@Override
	public void getUserPercentage(Map<String,Percentage> dymap,Map<String,Percentage> ywdymap,Map<String,Percentage> usernumbermap) {
		// TODO Auto-generated method stub
		List<PrizeUser> list = findPrizeUserOfCurrentByType(Prize.PRIZETYPE_USER);
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

	/**
	 * 根 据奖项id取获奖名单
	 */
	@Override
	public List<PrizeUser> findPrizeUserById(long prizeid) {
		// TODO Auto-generated method stub
		List<PrizeUser> list = dao.find("from PrizeUser t where t.prizeid=?1 order by t.indexorder desc", new Object[]{prizeid});
		if(list!=null&&list.size()>0){
			PrizeUser prizeuser = list.get(0);
			if(Prize.PRIZETYPE_SUFFIXNUM.equals(prizeuser.getPrizetype())){
				List<PrizeUser> list2 = new ArrayList<PrizeUser>();
				Map<String,PrizeUser> map = new HashMap<String,PrizeUser>();
				for(PrizeUser pu : list){
					String key = pu.getPrizeid()+":"+pu.getIndexorder();
					if(!map.containsKey(key)){
						map.put(key, pu);
						list2.add(pu);
					}
				}
				return list2;
			}
		}
		return list;
	}

//	@Override
//	public List<PrizeUser> getPrizeUserList(long prizeid) {
//		// TODO Auto-generated method stub
//		PrizeSerial prizeSerial = prizeSerialService.getActivePrizeSerial();
//		if(prizeSerial!=null){
//			
//			String serialnum = prizeSerial.getNum();
//			if(type!=null&&!"".equals(type)){
//				return dao.find("from PrizeUser t where t.prizeserialnum=?1 and t.prizetype=?2 order by t.indexorder desc,t.usernumber asc", new Object[]{serialnum,type});
//			}else{
//				return dao.find("from PrizeUser t where t.prizeserialnum=?1 order by t.indexorder desc,t.usernumber asc", new Object[]{serialnum});
//			}
//		}
//		return this.findPrizeUserById(prizeid);
//	}

	@Override
	public long countPrizeUserByType(long prizeid,String serialnum) {
		// TODO Auto-generated method stub
		return dao.findTotalCount("select count(distinct t.indexorder) as num from PrizeUser t where t.prizeid=?1 and t.prizeserialnum=?2", new Object[]{prizeid,serialnum});
	}

	@Override
	public String getYcjxId(String serialnum) {
		// TODO Auto-generated method stub
		List list = dao.find("select distinct(t.prizeid) from PrizeUser t where t.prizeserialnum=?1", new Object[]{serialnum});
		StringBuffer sb = new StringBuffer("{0:0");
		if(list!=null&&list.size()>0){
			for(int i=0;i<list.size();i++){
				String id = (Long)list.get(i)+"";
				sb.append(",").append(id).append(":").append(id);
			}
		}
		sb.append("}");
		return sb.toString();
	}


	
}
