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
@Table(name="suffixnum")
public class SuffixNum implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -79990111295950689L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private int suffixnum;
	private int status;
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "prizeserial_id", referencedColumnName = "id")
	private PrizeSerial prizeSerial=new PrizeSerial();
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getSuffixnum() {
		return suffixnum;
	}
	public void setSuffixnum(int suffixnum) {
		this.suffixnum = suffixnum;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public PrizeSerial getPrizeSerial() {
		return prizeSerial;
	}
	public void setPrizeSerial(PrizeSerial prizeSerial) {
		this.prizeSerial = prizeSerial;
	}
	
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		int result = 17;
		result = 37*result+(int) (suffixnum ^ (suffixnum>>>32));
		//result = 37*result+(uid==null?0:uid.hashCode());
		//result = 37*result+displayOrder;
		//result = 37*result+(this.url==null?0:url.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if(!(obj instanceof SuffixNum)){
			return false;
		}
		SuffixNum obj2 = (SuffixNum)obj;
		return this.suffixnum==obj2.getSuffixnum();
	}
	
}
