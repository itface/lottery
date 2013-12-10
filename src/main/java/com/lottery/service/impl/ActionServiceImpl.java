package com.lottery.service.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lottery.domain.Prize;
import com.lottery.domain.PrizeSerial;
import com.lottery.domain.PrizeUser;
import com.lottery.domain.User;
import com.lottery.service.ActionService;
import com.lottery.service.PrizeService;
import com.lottery.service.PrizeUserService;
import com.lottery.service.UserService;
import com.lottery.util.RandomNumUtil;
@Service
public class ActionServiceImpl implements ActionService{

	@Autowired
	private PrizeService prizeService;
	@Autowired
	private UserService userService;
	@Autowired
	private PrizeUserService prizeUserService;
	@Override
	@Transactional
	public List<User> action(long prizeId) {
		// TODO Auto-generated method stub
		Prize prize = prizeService.findById(prizeId);
		if(prize!=null){
			PrizeSerial prizeSerial = prize.getPrizeSerial();
			int prizecount = prizeSerial.getPrizecount();
			prizeSerial.setPrizecount(prizecount+1);
			List<User> list = userService.findAllActiveUser();
			RandomNumUtil util = new RandomNumUtil();
			List<User> users = util.getRandom(list,prize.getPrizenum());
			if(users!=null&&users.size()>0){
				for(User user:users){
					PrizeUser prizeUser = new PrizeUser(prize,user,prizecount+1);
					prizeUserService.addPrizeUser(prizeUser);
				}
				userService.updateUserStatus(users);
				Collections.sort(users);
				return users;
			}
		}
		return null;
	}

}
