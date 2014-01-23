package com.lottery.service;

import java.util.List;
import java.util.Map;

import com.lottery.domain.Percentage;
import com.lottery.domain.PrizeUser;

public interface PrizeUserService {

	//public List<PrizeUser> getPrizeUserList(long prizeid);
	public void addPrizeUser(PrizeUser prizeUser);
	public long countPrizeUserByType(long prizeid,String serialnum);
	//public List<PrizeUser> findCurrentPrizeUser(String prizeurltype);
	//public List<PrizeUser> findPrizeUserBySerialnum2(String serialnum,String prizeurltype);
	public void getUserPercentage(Map<String,Percentage> dymap,Map<String,Percentage> ywdymap,Map<String,Percentage> usernumbermap);
	public boolean ifHavePrizeUserOfUser(String serialnum);
	public boolean ifHavePrizeUserOfNum(String serialnum);
	public List<PrizeUser> findPrizeUserById(long prizeid);
	public String findPrizeUserHtmlOfCurrent();
	public String findPrizeUserHtmlOfHistory(String serialnum);
	public List<PrizeUser> findPrizeUserOfCurrentByType(String type);
	public List<PrizeUser> findPrizeUserOfHistoryByType(String serialnum,String type);
	public String getYcjxId(String serialnum);
}
