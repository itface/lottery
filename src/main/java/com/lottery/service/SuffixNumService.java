package com.lottery.service;



public interface SuffixNumService {

	public void deleteAll();
	public void saveList(int suffixnumfrom,int suffixnumto,String exclude);
	public long getYcNum(String serialnum); 
}
