package com.lottery.service;

import java.util.List;

import com.lottery.domain.PrizeUser;

public interface ActionService {

	public List<PrizeUser> action(long prizeId);
	public boolean checkActionFlag(long prizeId);
}
