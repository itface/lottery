package com.lottery.service;

import java.util.List;
import java.util.Map;

import com.lottery.domain.Percentage;
import com.lottery.domain.PrizeUser;

public interface PrizeUserService {

	public void addPrizeUser(PrizeUser prizeUser);
	public List<PrizeUser> findPrizeUser(String serialnum);
	public List<PrizeUser> findCurrentPrizeUser();
	public List<PrizeUser> findPrizeUserBySerialnum(String serialnum);
	public void getUserPercentage(Map<String,Percentage> dymap,Map<String,Percentage> ywdymap,Map<String,Percentage> usernumbermap);
}
