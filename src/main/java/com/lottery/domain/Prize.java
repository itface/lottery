package com.lottery.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;
@Entity
@Table(name="prize")
public class Prize implements Serializable{

	
	private static final long serialVersionUID = -1108604706063040536L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
	
	private String prizetype;
	private int num;
	private int indexorder;
	private int status;
	private String sm;
	private String jp;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "prizeserial_id", referencedColumnName = "id")
	private PrizeSerial prizeSerial=new PrizeSerial();
	
//	@JsonIgnore
//	@OneToMany(fetch=FetchType.LAZY,cascade = {CascadeType.REFRESH,CascadeType.PERSIST},mappedBy="prize")  
//    private Set<PrizeUsers> prizeUsers= new HashSet<PrizeUsers>();
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
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
	public String getJp() {
		return jp;
	}
	public void setJp(String jp) {
		this.jp = jp;
	}
	
}
