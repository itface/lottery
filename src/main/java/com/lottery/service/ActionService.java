package com.lottery.service;

import java.util.List;

import com.lottery.domain.User;

public interface ActionService {

	public List<User> action(long prizeId);
}
