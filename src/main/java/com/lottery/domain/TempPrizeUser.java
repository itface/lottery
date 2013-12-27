package com.lottery.domain;

public class TempPrizeUser implements Comparable<TempPrizeUser>{

	private String name;
	private int prizetypeindex;
	private PrizeUser prizeUser;
	public TempPrizeUser(){
		
	}
	public TempPrizeUser(String name,int prizetypeindex,PrizeUser prizeUser){
		this.name=name;
		this.prizetypeindex=prizetypeindex;
		this.prizeUser=prizeUser;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPrizetypeindex() {
		return prizetypeindex;
	}
	public void setPrizetypeindex(int prizetypeindex) {
		this.prizetypeindex = prizetypeindex;
	}
	
	public PrizeUser getPrizeUser() {
		return prizeUser;
	}
	public void setPrizeUser(PrizeUser prizeUser) {
		this.prizeUser = prizeUser;
	}
	@Override
	public int compareTo(TempPrizeUser o) {
		// TODO Auto-generated method stub
		if(o!=null){
			//不管是不是抽的同一个奖，都先比较总第几次抽，如果总第几次相同，则是同一批次的奖。
			return this.getPrizeUser().getIndexorder()-o.getPrizeUser().getIndexorder();
		}
		return 0;
	}
	
}
