package com.lottery.service;

import java.util.List;

import net.sf.json.JSONObject;

import com.lottery.domain.Prize;

public interface PrizeSettingService {

	public int getPrizeOrder(long id);
	public Prize findById(long id);
	public List<Prize> findAll();
	public void addPrize(Prize prize);
	public void updatePrize(Prize prize);
	public void deletePrize(long id);
	public JSONObject findAllJson();
	public String getOrderSelection(String order);
	public long getPrizeNum();
}
