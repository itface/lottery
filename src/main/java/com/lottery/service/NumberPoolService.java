package com.lottery.service;

import java.util.List;

import com.lottery.domain.NumberPool;


public interface NumberPoolService {

	public void deleteAll();
	public void saveList(int numberpoolfrom,int numberpoolto,String exclude);
	public List<NumberPool> findAllActiveNumberPool();
	public void updateNumberPoolStatus(List<NumberPool> list);
	public List<NumberPool>  updateNumberPoolStatus(int suffixnum);
}
