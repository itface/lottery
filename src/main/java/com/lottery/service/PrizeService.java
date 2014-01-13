package com.lottery.service;

import java.util.List;

import net.sf.json.JSONObject;

import com.lottery.domain.Prize;

public interface PrizeService {

	public int getPrizeOrder(long id);
	public Prize findById(long id);
	public List<Prize> findAll(String type);
	public List<Prize> findAll();
	public void addPrize(Prize prize);
	public void updatePrize(Prize prize);
	public void deletePrize(String ids);
	public JSONObject findAllJson(String type);
	public String getOrderSelection(String order);
	public long getPrizeNum(String type);
	public boolean checkfinish(Prize prize);
}
