package com.lottery.service.impl;

import java.util.ArrayList;
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
import com.lottery.service.SuffixNumService;
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
	@Autowired
	private SuffixNumService suffixNumService;


	@Override
	@Transactional
	public List<PrizeUser> action(long prizeId) {
		// TODO Auto-generated method stub
		Prize prize = prizeService.findById(prizeId);
		//如果没有设置奖项的总中奖次数，或者还没有超过总中奖次数，则可以抽
		if(prize!=null&&!prizeService.checkfinish(prize)){
			PrizeSerial prizeSerial = prize.getPrizeSerial();
			int prizecount = prizeSerial.getPrizecount();
			int pn = prize.getPrizenum();
			List<PrizeUser> prizeUsers = new ArrayList<PrizeUser>();
			int sameprizecount = (int)prizeUserService.countPrizeUserByType(prizeId, prizeSerial.getNum());
			RandomNumUtil util = new RandomNumUtil();
			if(Prize.PRIZETYPE_SUFFIXNUM.equals(prize.getPrizetype())){
				long num = suffixNumService.getYcNum(prizeSerial.getNum());
				if(prize.getTotalprizenum()==0||num<prize.getTotalprizenum()){
					Set<SuffixNum> set = prizeSerial.getActiveSuffixNum();
					if(set!=null&&set.size()>0){
						long n = util.getRandomOfSuffixnum(set, prizeSerial.getSuffixnumfrom(), prizeSerial.getSuffixnumto());
						if(n!=-1){
							prizeSerial.setPrizecount(prizecount+1);
							prizeSerial.updateSuffixNum((int)n);
							List<NumberPool> list = numberPoolService.updateNumberPoolStatus(prizeSerial.getNum(),(int)n);
							if(list!=null&&list.size()>0){
								boolean tempflag = true;
								for(NumberPool numberPool : list){
									PrizeUser prizeUser = new PrizeUser(prize,numberPool,prizecount+1,sameprizecount+1);
									prizeUserService.addPrizeUser(prizeUser);
									if(tempflag){
										//抽尾号时，只返回第一个中奖对象
										prizeUsers.add(prizeUser);
										tempflag=false;
									}
								}
								return prizeUsers;
							}
						}
					}
				}
			}else if(Prize.PRIZETYPE_NUMBER.equals(prize.getPrizetype())){
				List<NumberPool> list = numberPoolService.findAllActiveNumberPool(prizeSerial.getNum());
				if(list!=null&&list.size()>=pn){
					List<NumberPool> list2 = util.getRandomOfNumber(list,pn);
					if(list2!=null&&list2.size()>0){
						prizeSerial.setPrizecount(prizecount+1);
						for(NumberPool numberPool : list2){
							PrizeUser prizeUser = new PrizeUser(prize,numberPool,prizecount+1,sameprizecount+1);
							prizeUserService.addPrizeUser(prizeUser);
							prizeUsers.add(prizeUser);
						}
						numberPoolService.updateNumberPoolStatus(list2);
						//Collections.sort(prizeUsers);
						return prizeUsers;
					}
				}
			}else if(Prize.PRIZETYPE_USER.equals(prize.getPrizetype())){
				List<BalanceRule> balanceruleList = prizeSerial.getBalanceruleList();
				//设置了分组均衡
				if(balanceruleList!=null&&balanceruleList.size()>0){
					int count=0;
					List<User> list = new ArrayList<User>();
					//先把所有设置了分组均衡的奖先抽出来
					for(BalanceRule balanceRule : balanceruleList){
						//当抽的奖多于奖项的中奖人数则抽奖完成
						if(count>=pn){
							break;
						}
						int prizenum = (balanceRule.getMin()-balanceRule.getYcxx());
						if(prizenum>0){
							List<User> list2 = userService.findActiveUserByBalanceRule(balanceRule);
							if(list2!=null&&list2.size()>0){
								//如果抽奖人数小于奖项的中奖人数，则直接抽均衡设置的最低人数,并且符合条件的人数必须大于等于最低人数
								if(count+prizenum<=pn&&list2.size()>=prizenum){
									//getRandomOfUser方法中已经把抽中人的奖态改为已抽过
									List<User> users = util.getRandomOfUser(list2,prizenum);
									balanceRule.setYcxx(balanceRule.getYcxx()+prizenum);
									if(users!=null&&users.size()>0){
										list.addAll(users);
									}
									count+=prizenum;
								}else if(pn>count&&list2.size()>=(pn-count)){
									//如果抽的人数多于奖项的中奖人数，但奖项的中奖人数还没抽满时，则只抽剩余的人数,并且符合条件的人数必须大于等于最低人数
									prizenum = pn-count;
									List<User> users = util.getRandomOfUser(list2,prizenum);
									balanceRule.setYcxx(balanceRule.getYcxx()+prizenum);
									if(users!=null&&users.size()>0){
										list.addAll(users);
									}
									count+=prizenum;
								}
							}
						}
					}
					//如果奖项的中奖人数还没抽满时，则再抽剩余的奖
					if(count<pn){
						List<User> list2 = userService.findAllActiveUser();
						if(list2.size()>=pn-count){
							List<User> users = util.getRandomOfUser(list2,pn-count);
							list.addAll(users);
						}
					}
					if(list!=null&&list.size()>0){
						prizeSerial.setPrizecount(prizecount+1);
						for(User user:list){
							PrizeUser prizeUser = new PrizeUser(prize,user,prizecount+1,sameprizecount+1);
							prizeUserService.addPrizeUser(prizeUser);
							prizeUsers.add(prizeUser);
						}
						userService.updateUserStatus(list);
						//Collections.sort(prizeUsers);
						return prizeUsers;
					}
				}else{
					//没有设置分组均衡
					List<User> list = userService.findAllActiveUser();
					if(list!=null&&list.size()>=pn){
						List<User> users = util.getRandomOfUser(list,pn);
						if(users!=null&&users.size()>0){
							prizeSerial.setPrizecount(prizecount+1);
							for(User user:users){
								PrizeUser prizeUser = new PrizeUser(prize,user,prizecount+1,sameprizecount+1);
								prizeUserService.addPrizeUser(prizeUser);
								prizeUsers.add(prizeUser);
							}
							userService.updateUserStatus(users);
							//Collections.sort(prizeUsers);
							return prizeUsers;
						}
					}
				}
			}
		}
		return null;
	}


	@Override
	public boolean checkActionFlag(long prizeId) {
		// TODO Auto-generated method stub
		Prize prize = prizeService.findById(prizeId);
		if(prize!=null&&!prizeService.checkfinish(prize)){
			int pn = prize.getPrizenum();
			PrizeSerial prizeSerial = prize.getPrizeSerial();
			if(Prize.PRIZETYPE_SUFFIXNUM.equals(prize.getPrizetype())){
				long num = suffixNumService.getYcNum(prizeSerial.getNum());
				if(prize.getTotalprizenum()==0||num<prize.getTotalprizenum()){
					Set<SuffixNum> set = prizeSerial.getActiveSuffixNum();
					if(set!=null&&set.size()>0){
						return true;
					}
				}
			}else if(Prize.PRIZETYPE_NUMBER.equals(prize.getPrizetype())){
				long num = numberPoolService.findAllActiveNumberPoolNum(prizeSerial.getNum());
				if(num>0&&num>=pn){
					return true;
				}
			}else if(Prize.PRIZETYPE_USER.equals(prize.getPrizetype())){
				long num = userService.findActiveUserNum();
				if(num>0&&num>=pn){
					return true;
				}
			}
		}
		return false;
	}

}
