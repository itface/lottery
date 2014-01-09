package com.lottery.service;

import java.util.List;

import com.lottery.domain.PrizeSerial;

public interface PrizeSerialService {

	public PrizeSerial getActivePrizeSerial();
	public PrizeSerial getPrizeSerialByNum(String num);
	public List<PrizeSerial> getInActivePrizeSerial();
	public void stopActivePrizeSerial();
	public PrizeSerial addActivePrizeSerial(PrizeSerial prizeSerial);
	//public void updatePrizeSerialName(long id,String name);
	public void updatePrizeCount(long id);
	public void update(long id, String name,int suffixnumfrom,int suffixnumto,String suffixnumexclude,int numberpoolfrom,int numberpoolto,String numberpoolexclude);
}
