package com.lottery.service;

import java.util.List;

import com.lottery.domain.User;

public interface BackupUserService {

	public void saveList(List<User> list);
}
