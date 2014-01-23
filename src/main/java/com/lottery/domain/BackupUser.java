package com.lottery.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="backupuser")
public class BackupUser implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1192669140279142331L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
 

	private String usernumber;
	
    private String username;
    private String dept;
    private String region;
    private String ywdy;
    private String ywdy2;
    private int status;
    private int indexorder;

    private String prizeserialnum;

    public BackupUser(){
		
	}
	public BackupUser(User user,String prizeserialnum){
		this.dept=user.getDept();
		this.indexorder=user.getIndexorder();
		this.region=user.getRegion();
		this.status=user.getStatus();
		this.username=user.getUsername();
		this.usernumber=user.getUsernumber();
		this.ywdy=user.getYwdy();
		this.ywdy2=user.getYwdy2();
		this.prizeserialnum=prizeserialnum;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
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
	public String getUsernumber() {
		return usernumber;
	}
	public void setUsernumber(String usernumber) {
		this.usernumber = usernumber;
	}
	public String getYwdy2() {
		return ywdy2;
	}
	public void setYwdy2(String ywdy2) {
		this.ywdy2 = ywdy2;
	}
	public int getIndexorder() {
		return indexorder;
	}
	public void setIndexorder(int indexorder) {
		this.indexorder = indexorder;
	}
	
	public String getPrizeserialnum() {
		return prizeserialnum;
	}
	public void setPrizeserialnum(String prizeserialnum) {
		this.prizeserialnum = prizeserialnum;
	}

}