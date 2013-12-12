package com.lottery.domain;

public class Percentage implements Comparable<Percentage>{

	private String name;
	private String percentOfAll="0%";
	private String percentOfPrize="0%";
	private String percentOfDiff="0%";
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPercentOfAll() {
		return percentOfAll;
	}
	public void setPercentOfAll(String percentOfAll) {
		this.percentOfAll = percentOfAll;
	}
	public String getPercentOfPrize() {
		return percentOfPrize;
	}
	public void setPercentOfPrize(String percentOfPrize) {
		this.percentOfPrize = percentOfPrize;
	}
	public String getPercentOfDiff() {
		return percentOfDiff;
	}
	public void setPercentOfDiff(String percentOfDiff) {
		this.percentOfDiff = percentOfDiff;
	}
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		int result = 17;
		//result = 37*result+(int) (id ^ (id>>>32));
		result = 37*result+(name==null?0:name.hashCode());
		//result = 37*result+displayOrder;
		//result = 37*result+(this.url==null?0:url.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if(!(obj instanceof Percentage)){
			return false;
		}
		Percentage obj2 = (Percentage)obj;
		if(this.name!=null&&!"".equals(name)){
			return this.name==obj2.getName();
		}else{
			return false;
		}
	}
	@Override
	public int compareTo(Percentage o) {
		// TODO Auto-generated method stub
		String thatname = o.getName();
		if(name.length()==thatname.length()){
			return this.name.compareTo(o.getName());
		}
		return name.length()-thatname.length();
	}
	
}
