package com.lottery.service;

import java.util.List;
import java.util.Set;

import com.lottery.domain.NumberPool;


public interface NumberPoolService {

	public void deleteAll();
	public void saveList(int numberpoolfrom,int numberpoolto,String exclude);
	public List<NumberPool> findAllActiveNumberPool(String serialnum);
	//public boolean checkRepeat(List<NumberPool> list,String serialnum);
	public long findAllActiveNumberPoolNum(String serialnum);
	public void updateNumberPoolStatus(Set<NumberPool> set,long serialid);
	public List<NumberPool>  updateNumberPoolStatus(String serialnum,int suffixnum);
	public long countNumberPoolBySuffixnum(String serialnum,int suffixnum);
}
