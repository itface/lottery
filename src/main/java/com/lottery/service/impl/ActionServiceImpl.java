package com.lottery.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
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

	private int getRandomOfSuffixnum(Set<SuffixNum> set,int min,int max,PrizeSerial prizeSerial){
		RandomNumUtil util = new RandomNumUtil();
		Set<Integer> ts = new HashSet<Integer>();
		while(ts.size()<=set.size()){
			long n = util.getRandomOfSuffixnum(set, min, max,prizeSerial);
			if(n!=-1){
				int number = (int)n;
				ts.add(number);
				boolean flag =prizeSerial.checkSuffixNumRepeat(number);
				if(!flag){
					long count = numberPoolService.countNumberPoolBySuffixnum(prizeSerial.getNum(), number);
					if(count>0){
						return number;
					}
				}
			}else{
				return -1;
			}
		}
		return -1;
	}
	private  Set<NumberPool> getRandomOfNumber(Set<NumberPool> set,int length,Prize prize){
		RandomNumUtil util = new RandomNumUtil();
		while(true){
			Set<NumberPool> set2 = util.getRandomOfNumber(set,prize.getPrizenum());
			if(set2!=null&&set2.size()>0){
				PrizeSerial prizeSerial = prize.getPrizeSerial();
				boolean flag = prizeSerial.checkNumberPoolRepeat(set2);
				if(!flag){
					return set2;
				}
			}else{
				return null;
			}
		}
	}
	public  Set<User> getRandomOfUser(List<User> list,Set<User> excludeusers,int length,int distinctlength){
		RandomNumUtil util = new RandomNumUtil();
		while(true){
			Set<User> set = util.getRandomOfUser(list,excludeusers, length, distinctlength);
			if(set!=null&&set.size()>0){
				boolean flag = userService.checkRepeat(set);
				if(!flag){
					return set;
				}
			}else{
				return null;
			}
		}
	}
	@Override
	@Transactional
	public List<PrizeUser> action(long prizeId) {
		// TODO Auto-generated method stub
		Prize prize = prizeService.findById(prizeId);
		//如果没有设置奖项的总中奖次数，或者还没有超过总中奖次数，则可以抽
		if(prize!=null&&checkActionFlag(prize)){
			PrizeSerial prizeSerial = prize.getPrizeSerial();
			int prizecount = prizeSerial.getPrizecount();
			int pn = prize.getPrizenum();
			List<PrizeUser> prizeUsers = new ArrayList<PrizeUser>();
			int sameprizecount = (int)prizeUserService.countPrizeUserByType(prize.getId(), prizeSerial.getNum());
			if(Prize.PRIZETYPE_SUFFIXNUM.equals(prize.getPrizetype())){
				long num = suffixNumService.getYcNum(prize.getId());
				if(prize.getTotalprizenum()==0||num<prize.getTotalprizenum()){
					Set<SuffixNum> set = prizeSerial.getActiveSuffixNum();
					if(set!=null&&set.size()>0){
						int n = getRandomOfSuffixnum(set, prizeSerial.getSuffixnumfrom(), prizeSerial.getSuffixnumto(),prizeSerial);
						if(n!=-1){
							List<NumberPool> list = numberPoolService.updateNumberPoolStatus(prizeSerial.getNum(),n);
							prizeSerial.setPrizecount(prizecount+1);
							prizeSerial.updateSuffixNum((int)n,prize.getId());
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
			}else if(Prize.PRIZETYPE_NUMBER.equals(prize.getPrizetype())){
				Set<NumberPool> set = prizeSerial.getActiveNumberPool();
				//List<NumberPool> list = numberPoolService.findAllActiveNumberPool(prizeSerial.getNum());
				if(set!=null&&set.size()>=pn){
					Set<NumberPool> set2 = getRandomOfNumber(set,pn,prize);
					if(set2!=null&&set2.size()>0){
						prizeSerial.setPrizecount(prizecount+1);
						Iterator<NumberPool> it = set2.iterator();
						while(it.hasNext()){
							NumberPool numberPool = it.next();
							PrizeUser prizeUser = new PrizeUser(prize,numberPool,prizecount+1,sameprizecount+1);
							prizeUserService.addPrizeUser(prizeUser);
							prizeUsers.add(prizeUser);
						}
						numberPoolService.updateNumberPoolStatus(set2,prizeSerial.getId());
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
					Set<User> excludeusers = new HashSet<User>();
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
								Set<User> us = new HashSet<User>();
								us.addAll(list2);
								//如果抽奖人数小于奖项的中奖人数，则直接抽均衡设置的最低人数,并且符合条件的人数必须大于等于最低人数
								if(count+prizenum<=pn&&us.size()>=prizenum){
									//getRandomOfUser方法中已经把抽中人的奖态改为已抽过
									Set<User> users = this.getRandomOfUser(list2,excludeusers, prizenum, us.size());
									if(users!=null&&users.size()>0){
										balanceRule.setYcxx(balanceRule.getYcxx()+prizenum);
										list.addAll(users);
										excludeusers.addAll(users);
										count+=prizenum;
									}
								}else if(pn>count&&us.size()>=(pn-count)){
									//如果抽的人数多于奖项的中奖人数，但奖项的中奖人数还没抽满时，则只抽剩余的人数,并且符合条件的人数必须大于等于最低人数
									prizenum = pn-count;
									Set<User> users = this.getRandomOfUser(list2,excludeusers, prizenum, us.size());
									//List<User> users = util.getRandomOfUser(list2,prizenum);
									if(users!=null&&users.size()>0){
										balanceRule.setYcxx(balanceRule.getYcxx()+prizenum);
										list.addAll(users);
										excludeusers.addAll(users);
										count+=prizenum;
									}
								}else if(pn>count&&us.size()<(pn-count)){
									list.addAll(us);
									excludeusers.addAll(us);
									prizenum = us.size();
									balanceRule.setYcxx(balanceRule.getYcxx()+prizenum);
									count+=prizenum;
								}
							}
						}
					}
					//如果奖项的中奖人数还没抽满时，则再抽剩余的奖
					if(count<pn){
						List<User> list2 = userService.findAllActiveUser();
						if(list2!=null&&list2.size()>0){
							Set<User> us = new HashSet<User>();
							us.addAll(list2);
							if(us.size()>=pn-count){
								Set<User> users = this.getRandomOfUser(list2,excludeusers, pn-count, us.size());
								list.addAll(users);
								excludeusers.addAll(users);
							}
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
					if(list!=null&&list.size()>0){
						Set<User> us = new HashSet<User>();
						us.addAll(list);
						if(us.size()>=pn){
							Set<User> users = this.getRandomOfUser(list,null, pn, us.size());
							if(users!=null&&users.size()>0){
								prizeSerial.setPrizecount(prizecount+1);
								for(User user:users){
									PrizeUser prizeUser = new PrizeUser(prize,user,prizecount+1,sameprizecount+1);
									prizeUserService.addPrizeUser(prizeUser);
									prizeUsers.add(prizeUser);
								}
								List<User> list2 = new ArrayList<User>();
								list2.addAll(users);
								userService.updateUserStatus(list2);
								//Collections.sort(prizeUsers);
								return prizeUsers;
							}
						}
					}
				}
			}
		}
		return null;
	}


	@Override
	public boolean checkActionFlag(Prize prize) {
		// TODO Auto-generated method stub
		if(prize!=null){
			int pn = prize.getPrizenum();
			PrizeSerial prizeSerial = prize.getPrizeSerial();
			if(Prize.PRIZETYPE_SUFFIXNUM.equals(prize.getPrizetype())){
				long ycry = suffixNumService.getYcNum(prize.getId());
				if(prize.getTotalprizenum()==0||(prize.getTotalprizenum()>0&&ycry<prize.getTotalprizenum())){
					Set<SuffixNum> set = prizeSerial.getActiveSuffixNum();
					if(set!=null&&set.size()>0){
						return true;
					}
				}
			}else if(Prize.PRIZETYPE_NUMBER.equals(prize.getPrizetype())){
				List<PrizeUser> list = prizeUserService.findPrizeUserById(prize.getId());
				if(prize.getTotalprizenum()==0||(prize.getTotalprizenum()>0&&list.size()<prize.getTotalprizenum())){
					Set<NumberPool> set = prizeSerial.getActiveNumberPool();
					int num=set.size();
					//long num = numberPoolService.findAllActiveNumberPoolNum(prizeSerial.getNum());
					if(num>0&&num>=pn){
						return true;
					}
				}
			}else if(Prize.PRIZETYPE_USER.equals(prize.getPrizetype())){
				List<PrizeUser> list = prizeUserService.findPrizeUserById(prize.getId());
				if(prize.getTotalprizenum()==0||(prize.getTotalprizenum()>0&&list.size()<prize.getTotalprizenum())){
					long num = userService.findDistinctActiveUserNum();
					if(num>0&&num>=pn){
						return true;
					}
				}
			}
		}
		return false;
	}

}
