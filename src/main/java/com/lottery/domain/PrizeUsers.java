package com.lottery.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Pattern;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="prizeusers")
public class PrizeUsers implements Serializable{

	private static final long serialVersionUID = 5757795679672816084L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date prizetime;
	
	private String prizeSerial;
	private String prizetype;
	private int num;
	private String sm;
	private String jp;
	
	private String uid;
	private String usernumber;
    private String username;
    private String dept;
    private String region;
    private String ywdy;
    private String ywdy2;
    
    
//	@JsonIgnore
//	@ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id", referencedColumnName = "id")
//	User user = new User();
//	
//	@JsonIgnore
//	@ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "prize_id", referencedColumnName = "id")
//	Prize prize = new Prize();
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Date getPrizetime() {
		return prizetime;
	}
	public void setPrizetime(Date prizetime) {
		this.prizetime = prizetime;
	}
	public String getPrizeSerial() {
		return prizeSerial;
	}
	public void setPrizeSerial(String prizeSerial) {
		this.prizeSerial = prizeSerial;
	}
	public String getPrizetype() {
		return prizetype;
	}
	public void setPrizetype(String prizetype) {
		this.prizetype = prizetype;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getSm() {
		return sm;
	}
	public void setSm(String sm) {
		this.sm = sm;
	}
	public String getJp() {
		return jp;
	}
	public void setJp(String jp) {
		this.jp = jp;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getUsernumber() {
		return usernumber;
	}
	public void setUsernumber(String usernumber) {
		this.usernumber = usernumber;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getYwdy() {
		return ywdy;
	}
	public void setYwdy(String ywdy) {
		this.ywdy = ywdy;
	}
	public String getYwdy2() {
		return ywdy2;
	}
	public void setYwdy2(String ywdy2) {
		this.ywdy2 = ywdy2;
	}

	
}
