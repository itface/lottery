package com.lottery.service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.lottery.domain.Percentage;
import com.lottery.domain.User;

public interface UserService {

	public void impUser(MultipartFile mf)throws IOException, SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException;
	public List<User> findAll();
	public List<User> findAllActiveUser();
	public void updateUserStatus(List<User> users);
	public void updateAllUserStatus();
	public long findActiveUserNum();
	public long countTotalUser();
	public void getUserPercentage(Map<String,Percentage> dymap,Map<String,Percentage> ywdymap,Map<String,Percentage> usernumbermap);
}
