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
import com.lottery.util.JsonUtils;
@Service
public class BalanceRuleServiceImpl implements BalanceRuleService{

	@Autowired
	private BaseDao<BalanceRule> dao;
	@Autowired
	private PrizeSerialService prizeSerialService;
	
	@Override
	@Transactional
	public void add(BalanceRule balanceRule) {
		// TODO Auto-generated method stub
		PrizeSerial prizeSerial = prizeSerialService.getActivePrizeSerial();
		if(prizeSerial!=null){
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
				dao.deleteById(BalanceRule.class, id);
			}
		}
	}

	@Override
	@Transactional
	public void update(BalanceRule balanceRule) {
		// TODO Auto-generated method stub
		BalanceRule b = this.findById(balanceRule.getId());
		if(b!=null){
			b.update(balanceRule);
		}
	}

	@Override
	public BalanceRule findById(long id) {
		// TODO Auto-generated method stub
		return dao.findById(BalanceRule.class, id);
	}

}
