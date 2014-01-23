package com.lottery.service;



public interface SuffixNumService {

	public void deleteAll();
	public void saveList(int suffixnumfrom,int suffixnumto,String exclude);
	public long getYcNum(long prizeid); 
	//public boolean chenckYcNum(long serialid,int num);
}
