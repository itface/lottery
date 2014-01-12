package com.lottery.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lottery.domain.BalanceRule;
import com.lottery.domain.NumberPool;
import com.lottery.domain.Prize;
import com.lottery.domain.PrizeSerial;
import com.lottery.domain.PrizeUser;
import com.lottery.domain.SuffixNum;
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
			int prizecount = prizeSerial.getPrizecount();
			List<PrizeUser> prizeUsers = new ArrayList<PrizeUser>();
			RandomNumUtil util = new RandomNumUtil();
			if(Prize.PRIZETYPE_SUFFIXNUM.equals(prize.getPrizetype())){
				Set<SuffixNum> set = prizeSerial.getSuffixnums();
				long n = util.getRandomOfSuffixnum(set, prizeSerial.getSuffixnumfrom(), prizeSerial.getSuffixnumto());
				if(n!=-1){
					List<NumberPool> list = numberPoolService.updateNumberPoolStatus((int)n);
					for(NumberPool numberPool : list){
						PrizeUser prizeUser = new PrizeUser(prize,numberPool,prizecount+1);
						prizeUserService.addPrizeUser(prizeUser);
						prizeUsers.add(prizeUser);
					}
					return prizeUsers;
				}
			}else if(Prize.PRIZETYPE_NUMBER.equals(prize.getPrizetype())){
				List<NumberPool> list = numberPoolService.findAllActiveNumberPool();
				List<NumberPool> list2 = util.getRandomOfNumber(list,prize.getPrizenum());
				if(list2!=null&&list2.size()>0){
					prizeSerial.setPrizecount(prizecount+1);
					for(NumberPool numberPool : list2){
						PrizeUser prizeUser = new PrizeUser(prize,numberPool,prizecount+1);
						prizeUserService.addPrizeUser(prizeUser);
						prizeUsers.add(prizeUser);
					}
					numberPoolService.updateNumberPoolStatus(list2);
					return prizeUsers;
				}
				
			}else if(Prize.PRIZETYPE_USER.equals(prize.getPrizetype())){
				Set<BalanceRule> set = prizeSerial.getBalancerules();
				//设置了分组均衡
				if(set!=null&&set.size()>0){
					Iterator<BalanceRule> it = set.iterator();
					int count=0;
					List<User> list = new ArrayList<User>();
					//先把所有设置了分组均衡的奖先抽出来
					while(it.hasNext()){
						//当抽的奖多于奖项的中奖人数则抽奖完成
						if(count>=prizecount){
							break;
						}
						BalanceRule balanceRule = it.next();
						int prizenum = (balanceRule.getMin()-balanceRule.getYcxx());
						List<User> list2 = userService.findActiveUserByBalanceRule(balanceRule);
						if(list2!=null&&list2.size()>0){
							//如果抽奖人数小于奖项的中奖人数，则直接抽均衡设置的最低人数
							if(count+prizenum<=prizecount){
								//getRandomOfUser方法中已经把抽中人的奖态改为已抽过
								List<User> users = util.getRandomOfUser(list2,prizenum);
								if(users!=null&&users.size()>0){
									list.addAll(users);
								}
							}else if(prizecount>count){
								//如果抽的人数多于奖项的中奖人数，但奖项的中奖人数还没抽满时，则只抽剩余的人数
								prizenum = prizecount-count;
								List<User> users = util.getRandomOfUser(list2,prizenum);
								balanceRule.setYcxx(balanceRule.getYcxx()+prizenum);
								if(users!=null&&users.size()>0){
									list.addAll(users);
								}
							}
						}
						count+=prizenum;
					}
					//如果奖项的中奖人数还没抽满时，则再抽剩余的奖
					if(count<prizecount){
						List<User> list2 = userService.findAllActiveUser();
						List<User> users = util.getRandomOfUser(list2,prizecount-count);
						list.addAll(users);
					}
					if(list!=null&&list.size()>0){
						prizeSerial.setPrizecount(prizecount+1);
						for(User user:list){
							PrizeUser prizeUser = new PrizeUser(prize,user,prizecount+1);
							prizeUserService.addPrizeUser(prizeUser);
							prizeUsers.add(prizeUser);
						}
						userService.updateUserStatus(list);
						Collections.sort(prizeUsers);
						return prizeUsers;
					}
				}else{
					//没有设置分组均衡
					List<User> list = userService.findAllActiveUser();
					List<User> users = util.getRandomOfUser(list,prize.getPrizenum());
					if(users!=null&&users.size()>0){
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
		}
		return null;
	}

}
