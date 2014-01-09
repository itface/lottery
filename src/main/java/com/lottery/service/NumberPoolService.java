package com.lottery.service;


public interface NumberPoolService {

	public void deleteAll();
	public void saveList(int numberpoolfrom,int numberpoolto,String exclude);
}
