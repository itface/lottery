package com.lottery.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="prizeusers")
public class PrizeUsers implements Serializable{

	private static final long serialVersionUID = 5757795679672816084L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
	private long prizeid;
	private String uid;
	private String serialnumber;
	@Temporal(TemporalType.TIMESTAMP)
	private Date prizetime;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getPrizeid() {
		return prizeid;
	}
	public void setPrizeid(long prizeid) {
		this.prizeid = prizeid;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getSerialnumber() {
		return serialnumber;
	}
	public void setSerialnumber(String serialnumber) {
		this.serialnumber = serialnumber;
	}
	public Date getPrizetime() {
		return prizetime;
	}
	public void setPrizetime(Date prizetime) {
		this.prizetime = prizetime;
	}

	
}
