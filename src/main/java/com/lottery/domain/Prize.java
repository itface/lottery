package com.lottery.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
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
	

}
