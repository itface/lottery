package com.lottery.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lottery.domain.NumberPool;
import com.lottery.domain.Prize;
import com.lottery.domain.PrizeSerial;
import com.lottery.domain.PrizeUser;
import com.lottery.domain.User;
import com.lottery.service.ActionService;
import com.lottery.service.NumberPoolService;
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
	@Autowired
	private NumberPoolService numberPoolService;
	@Override
	@Transactional
	public List<PrizeUser> action(long prizeId) {
		// TODO Auto-generated method stub
		Prize prize = prizeService.findById(prizeId);
		if(prize!=null){
			PrizeSerial prizeSerial = prize.getPrizeSerial();
			RandomNumUtil util = new RandomNumUtil();
			if(Prize.PRIZETYPE_SUFFIXNUM.equals(prize.getPrizetype())){
				
			}else if(Prize.PRIZETYPE_NUMBER.equals(prize.getPrizetype())){
				List<NumberPool> list = numberPoolService.findAllActiveNumberPool();
				List<NumberPool> list2 = util.getRandomOfNumber(list,prize.getPrizenum());
				
			}else if(Prize.PRIZETYPE_USER.equals(prize.getPrizetype())){
				List<User> list = userService.findAllActiveUser();
				
				List<User> users = util.getRandomOfUser(list,prize.getPrizenum());
				List<PrizeUser> prizeUsers = new ArrayList<PrizeUser>();
				if(users!=null&&users.size()>0){
					int prizecount = prizeSerial.getPrizecount();
					prizeSerial.setPrizecount(prizecount+1);
					for(User user:users){
						PrizeUser prizeUser = new PrizeUser(prize,user,prizecount+1);
						prizeUserService.addPrizeUser(prizeUser);
						prizeUsers.add(prizeUser);
					}
					userService.updateUserStatus(users);
					Collections.sort(prizeUsers);
					return prizeUsers;
				}
			}
		}
		return null;
	}

}
