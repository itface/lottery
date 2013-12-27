package com.lottery.service;

import java.util.List;
import java.util.Map;

import com.lottery.domain.Percentage;
import com.lottery.domain.PrizeUser;
import com.lottery.domain.TempPrizeUser;

public interface PrizeUserService {

	public List<PrizeUser> getPrizeUserList();
	public void addPrizeUser(PrizeUser prizeUser);
	public List<PrizeUser> findPrizeUser(String serialnum);
	public List<PrizeUser> findCurrentPrizeUser();
	public List<PrizeUser> findPrizeUserBySerialnum(String serialnum);
	public void getUserPercentage(Map<String,Percentage> dymap,Map<String,Percentage> ywdymap,Map<String,Percentage> usernumbermap);
}
