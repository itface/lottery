package com.lottery.service;

import java.util.List;
import java.util.Map;

import com.lottery.domain.Percentage;
import com.lottery.domain.PrizeUser;

public interface PrizeUserService {

	public List<PrizeUser> getPrizeUserList();
	public void addPrizeUser(PrizeUser prizeUser);
	//public List<PrizeUser> findPrizeUser2(String serialnum);
	public List<PrizeUser> findCurrentPrizeUser();
	public List<PrizeUser> findPrizeUserBySerialnum(String serialnum);
	public void getUserPercentage(Map<String,Percentage> dymap,Map<String,Percentage> ywdymap,Map<String,Percentage> usernumbermap);
	public boolean ifHavePrizeUserOfUser(String serialnum);
	public boolean ifHavePrizeUserOfNum(String serialnum);
	public List<PrizeUser> findCurrentPrizeUserByType(long prizeid);
}
