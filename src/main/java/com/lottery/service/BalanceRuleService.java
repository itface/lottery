package com.lottery.service;

import java.util.List;

import net.sf.json.JSONObject;

import com.lottery.domain.BalanceRule;

public interface BalanceRuleService {

	public void add(BalanceRule balanceRule);
	public List<BalanceRule> findAll();
	public BalanceRule findById(long id);
	public JSONObject findAllJson();
	public void delete(String ids);
	public void update(BalanceRule balanceRule);
	public String getOrderSelection(String indexorder);
	public int getBalanceRuleNum();
	public String testBalanceRule(long id);
}
