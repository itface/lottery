package com.lottery.service;

import java.util.List;

import com.lottery.domain.PrizeUser;

public interface PrizeUserService {


	public void addPrizeUser(PrizeUser prizeUser);
	public List<PrizeUser> findPrizeUser(String serialnum);
}
