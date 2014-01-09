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
public class BalanceRule implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5163831575314769546L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String ywdy;
	private String region;
	private int min;
	private int ycxx;
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "prizeserial_id", referencedColumnName = "id")
	private PrizeSerial prizeSerial=new PrizeSerial();
	
	
	public void update(BalanceRule balanceRule){
		if(balanceRule!=null){
			this.id=balanceRule.getId();
			this.ywdy=balanceRule.getYwdy();
			this.region=balanceRule.getRegion();
			this.min=balanceRule.getMin();
			this.ycxx=balanceRule.getYcxx();
		}
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getYwdy() {
		return ywdy;
	}
	public void setYwdy(String ywdy) {
		this.ywdy = ywdy;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
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

	
}
