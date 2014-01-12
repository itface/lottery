package com.lottery.service;

import java.util.List;

import com.lottery.domain.SuffixNum;


public interface SuffixNumService {

	public void deleteAll();
	public void saveList(int suffixnumfrom,int suffixnumto,String exclude);
	public List<SuffixNum> findAllActiveSuffinxNum();
	public void updateSuffinxNumStatus(List<SuffixNum> list);
}
