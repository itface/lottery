package com.lottery.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottery.domain.Prize;
import com.lottery.domain.User;
import com.lottery.service.ActionService;
import com.lottery.service.PrizeSettingService;
import com.lottery.service.UserService;
import com.lottery.util.RandomNumUtil;
@Service
public class ActionServiceImpl implements ActionService{

	@Autowired
	private PrizeSettingService prizeSettingService;
	@Autowired
	private UserService userService;
	@Override
	public List<User> action(long prizeId) {
		// TODO Auto-generated method stub
		Prize prize = prizeSettingService.findById(prizeId);
		if(prize!=null){
			List<User> list = userService.findAllActiveUser();
			List<User> users = RandomNumUtil.getRandomNumber(list,prize.getNum());
			if(users!=null&&users.size()>0){
				List<User> u = new ArrayList<User>(prize.getNum());
//				Iterator<User> it = users.iterator();
//				while(it.hasNext()){
//					User user = it.next();
//				}
				u.addAll(users);
				userService.updateUserStatus(u);
				Collections.sort(u);
				return u;
			}
		}
		return null;
	}

}
