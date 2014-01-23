package com.lottery.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lottery.dao.BaseDao;
import com.lottery.domain.BalanceRule;
import com.lottery.domain.Jqgrid_DataJson;
import com.lottery.domain.Prize;
import com.lottery.domain.PrizeSerial;
import com.lottery.service.BalanceRuleService;
import com.lottery.service.PrizeSerialService;
import com.lottery.service.UserService;
import com.lottery.util.JsonUtils;
@Service
public class BalanceRuleServiceImpl implements BalanceRuleService{

	@Autowired
	private BaseDao<BalanceRule> dao;
	@Autowired
	private PrizeSerialService prizeSerialService;
	@Autowired
	private UserService userService;
	
	@Override
	@Transactional
	public void add(BalanceRule balanceRule) {
		// TODO Auto-generated method stub
		PrizeSerial prizeSerial = prizeSerialService.getActivePrizeSerial();
		if(prizeSerial!=null){
			int order  = balanceRule.getIndexorder();
			dao.executeUpdate("update BalanceRule t set t.indexorder=t.indexorder+1 where t.indexorder>=?1",new Object[]{order});
			balanceRule.setPrizeSerial(prizeSerial);
			prizeSerial.getBalancerules().add(balanceRule);
		}
	}

	@Override
	public List<BalanceRule> findAll() {
		// TODO Auto-generated method stub
		PrizeSerial prizeSerial = prizeSerialService.getActivePrizeSerial();
		if(prizeSerial!=null){
			List<BalanceRule> list = new ArrayList<BalanceRule>();
			list.addAll(prizeSerial.getBalancerules());
			Collections.sort(list);
			return list;
		}
		return null;
	}

	@Override
	public JSONObject findAllJson() {
		// TODO Auto-generated method stub
		List<BalanceRule> list  = this.findAll();
		if(list!=null&&list.size()>0){
			Jqgrid_DataJson jsonData = new Jqgrid_DataJson(0,0,0,list);
			JSONObject jsonObject = JsonUtils.objectToJSONObject(jsonData,new String[]{"prizeSerial","prizeUsers"});
			return jsonObject;
		}
		return null;
	}

	@Override
	@Transactional
	public void delete(String ids) {
		// TODO Auto-generated method stub
		if(ids!=null&&!"".equals(ids)){
			String[] arr = ids.split(",");
			for(int i=0;i<arr.length;i++){
				long id = Long.parseLong(arr[i]);
				BalanceRule balanceRule = this.findById(id);
				int order = balanceRule.getIndexorder();
				dao.deleteById(BalanceRule.class, id);
				dao.executeUpdate("update BalanceRule t set t.indexorder=t.indexorder-1 where t.indexorder>?1",new Object[]{order});
			}
		}
	}

	@Override
	@Transactional
	public void update(BalanceRule balanceRule) {
		// TODO Auto-generated method stub
		BalanceRule b = this.findById(balanceRule.getId());
		if(b!=null){
			BalanceRule old = this.findById(balanceRule.getId());
			int oldOrder = old.getIndexorder();
			int newOrder = balanceRule.getIndexorder();
			if(oldOrder>newOrder){
				dao.executeUpdate("update BalanceRule t set t.indexorder=t.indexorder+1 where t.indexorder>=?1 and t.indexorder<?3 and t.id!=?2",new Object[]{newOrder,balanceRule.getId(),oldOrder});
			}else if(oldOrder<newOrder){
				dao.executeUpdate("update BalanceRule t set t.indexorder=t.indexorder-1 where t.indexorder<=?1 and t.indexorder>?3 and t.id!=?2",new Object[]{newOrder,balanceRule.getId(),oldOrder});
			}
			b.update(balanceRule);
		}
	}

	@Override
	public BalanceRule findById(long id) {
		// TODO Auto-generated method stub
		return dao.findById(BalanceRule.class, id);
	}

	@Override
	public String getOrderSelection(String indexorder) {
		// TODO Auto-generated method stub
		long num = this.getBalanceRuleNum();
		StringBuffer sb = new StringBuffer("");
		if(num>0){
			if("".equals(indexorder)){
				num=num+1;
				indexorder=num+"";
			}
			for(int i=1;i<=num;i++){
				String selected = (i+"").equals(indexorder)?"selected":"";
				sb.append("<option value='"+i+"' "+selected+">"+i+"</option>");
			}
		}else{
			sb.append("<option value='1'>1</option>");
		}
		sb.append("");
		return sb.toString();
	}

	@Override
	public int getBalanceRuleNum() {
		// TODO Auto-generated method stub
		PrizeSerial prizeSerial = prizeSerialService.getActivePrizeSerial();
		if(prizeSerial!=null){
			return prizeSerial.getBalancerules().size();
		}
		return 0;
	}

	@Override
	public String testBalanceRule(long id) {
		// TODO Auto-generated method stub
		return userService.testFindActiveUserByBalanceRule(this.findById(id));
	}

	@Override
	public String getYcjxId(String serialnum) {
		// TODO Auto-generated method stub
		List list = dao.find("select id from BalanceRule t where t.prizeSerial.num=?1 and t.ycxx>0", new Object[]{serialnum});
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
