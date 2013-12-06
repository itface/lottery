package com.lottery.service;

import com.lottery.domain.PrizeSerial;

public interface PrizeSerialService {

	public PrizeSerial getActivePrizeSerial();
	public void stopActivePrizeSerial();
	public PrizeSerial addActivePrizeSerial(PrizeSerial prizeSerial);
	public void updatePrizeSerialName(long id,String name);
}
