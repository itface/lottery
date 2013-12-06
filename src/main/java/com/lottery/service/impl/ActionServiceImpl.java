package com.lottery.service.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lottery.domain.Prize;
import com.lottery.domain.PrizeUsers;
import com.lottery.domain.User;
import com.lottery.service.ActionService;
import com.lottery.service.PrizeService;
import com.lottery.service.UserService;
import com.lottery.util.RandomNumUtil;
@Service
public class ActionServiceImpl implements ActionService{

	@Autowired
	private PrizeService prizeSettingService;
	@Autowired
	private UserService userService;
	
	@Override
	@Transactional
	public List<User> action(long prizeId) {
		// TODO Auto-generated method stub
		Prize prize = prizeSettingService.findById(prizeId);
		if(prize!=null){
			List<User> list = userService.findAllActiveUser();
			List<User> users = RandomNumUtil.getRandomNumber(list,prize.getNum());
			if(users!=null&&users.size()>0){
				Date date = new Date();
				for(User user:users){
//					PrizeUsers prizeUsers = new PrizeUsers();
//					prizeUsers.setPrizetime(date);
//					prizeUsers.setPrize(prize);
//					prizeUsers.setUser(user);
//					user.getPrizeUsers().add(prizeUsers);
//					prize.getPrizeUsers().add(prizeUsers);
				}
				userService.updateUserStatus(users);
				Collections.sort(users);
				return users;
			}
		}
		return null;
	}

}
