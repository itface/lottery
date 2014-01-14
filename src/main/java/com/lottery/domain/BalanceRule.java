package com.lottery.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;

@Entity
@Table(name="balancerule")
public class BalanceRule implements Serializable,Comparable<BalanceRule>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5163831575314769546L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String rule;
	private int min;
	private int ycxx;
	private int indexorder;
	private String sm;
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "prizeserial_id", referencedColumnName = "id")
	private PrizeSerial prizeSerial=new PrizeSerial();
	
	
	public void update(BalanceRule balanceRule){
		if(balanceRule!=null){
			this.id=balanceRule.getId();
			this.rule=balanceRule.getRule();
			this.min=balanceRule.getMin();
			this.ycxx=balanceRule.getYcxx();
			this.indexorder=balanceRule.getIndexorder();
			this.sm=balanceRule.getSm();
		}
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public String getRule() {
		return rule;
	}
	public void setRule(String rule) {
		this.rule = rule;
	}
	public int getIndexorder() {
		return indexorder;
	}
	public void setIndexorder(int indexorder) {
		this.indexorder = indexorder;
	}
	public String getSm() {
		return sm;
	}
	public void setSm(String sm) {
		this.sm = sm;
	}
	public int getMin() {
		return min;
	}
	public void setMin(int min) {
		this.min = min;
	}
	public int getYcxx() {
		return ycxx;
	}
	public void setYcxx(int ycxx) {
		this.ycxx = ycxx;
	}
	public PrizeSerial getPrizeSerial() {
		return prizeSerial;
	}
	public void setPrizeSerial(PrizeSerial prizeSerial) {
		this.prizeSerial = prizeSerial;
	}
	@Override
	public int compareTo(BalanceRule o) {
		// TODO Auto-generated method stub
		return this.indexorder-o.getIndexorder();
	}
}
