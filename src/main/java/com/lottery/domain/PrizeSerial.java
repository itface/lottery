package com.lottery.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="prizegroup")
public class PrizeSerial implements Serializable{

	private static final long serialVersionUID = 1967737952290626461L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
	private String num;
	private String name;
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date begindate;
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date enddate;
	private int status;
	private int prizecount;
	@JsonIgnore
	@OneToMany(fetch=FetchType.LAZY,cascade = {CascadeType.REMOVE,CascadeType.PERSIST},mappedBy="prizeSerial")
	private Set<Prize> prizes = new HashSet<Prize>();
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getBegindate() {
		return begindate;
	}
	public void setBegindate(Date begindate) {
		this.begindate = begindate;
	}
	public Date getEnddate() {
		return enddate;
	}
	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Set<Prize> getPrizes() {
		return prizes;
	}
	public void setPrizes(Set<Prize> prizes) {
		this.prizes = prizes;
	}
	public int getPrizecount() {
		return prizecount;
	}
	public void setPrizecount(int prizecount) {
		this.prizecount = prizecount;
	}
}
