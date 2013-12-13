package com.lottery.service;

import java.util.List;

import com.lottery.domain.User;

public interface TempUserService {

	public void deleteAll();
	public void saveList(List<User> list);
}
