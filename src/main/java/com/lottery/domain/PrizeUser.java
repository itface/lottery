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
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="prizeusers")
public class PrizeUser implements Serializable,Comparable<PrizeUser>{

	private static final long serialVersionUID = 5757795679672816084L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date prizetime;
	private int indexorder;
	private int sameprizeindexorder;
	@Transient
	private String indexordername;
	
	
	private String prizeserialnum;
	private String prizeserialname;
	
	private long prizeid;
	private String prizename;
	private String prizetype;
	private int totalprizenum;
	private int prizenum;
	private String sm;
	private String jp;
	
	private String usernumber;
    private String username;
    private String dept;
    private String region;
    private String ywdy;
    private String ywdy2;
    
    //private int number;
//	@JsonIgnore
//	@ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id", referencedColumnName = "id")
//	User user = new User();
//	
//	@JsonIgnore
//	@ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "prize_id", referencedColumnName = "id")
//	Prize prize = new Prize();
	
	public PrizeUser(){
		
	}
	public PrizeUser(Prize prize,User user,int indexorder,int sameprizeindexorder){
		if(prize!=null){
			this.prizeid=prize.getId();
			this.jp=prize.getJp();
			this.prizenum=prize.getPrizenum();
			this.prizeserialnum=prize.getPrizeSerial().getNum();
			this.prizeserialname=prize.getPrizeSerial().getName();
			this.prizetype=prize.getPrizetype();
			this.totalprizenum=prize.getTotalprizenum();
			this.prizename=prize.getPrizename();
			this.sm=prize.getSm();
		}
		if(user!=null){
			this.region=user.getRegion();
			this.dept=user.getDept();
			this.username=user.getUsername();
			this.usernumber=user.getUsernumber();
			this.ywdy=user.getYwdy();
			this.ywdy2=user.getYwdy2();
		}
		this.prizetime=new Date();
		this.indexorder=indexorder;
		this.sameprizeindexorder=sameprizeindexorder;
	}
	public PrizeUser(Prize prize,NumberPool numberPool,int indexorder,int sameprizeindexorder){
		if(prize!=null){
			this.prizeid=prize.getId();
			this.jp=prize.getJp();
			this.prizenum=prize.getPrizenum();
			this.prizeserialnum=prize.getPrizeSerial().getNum();
			this.prizeserialname=prize.getPrizeSerial().getName();
			this.prizetype=prize.getPrizetype();
			this.totalprizenum=prize.getTotalprizenum();
			this.prizename=prize.getPrizename();
			this.sm=prize.getSm();
		}
		if(numberPool!=null){
			this.region=numberPool.getNumber()+"";
			this.dept=numberPool.getNumber()+"";
			this.username=numberPool.getNumber()+"";
			this.usernumber=numberPool.getNumber()+"";
			this.ywdy=numberPool.getNumber()+"";
			this.ywdy2=numberPool.getNumber()+"";
		}
		this.prizetime=new Date();
		this.indexorder=indexorder;
		this.sameprizeindexorder=sameprizeindexorder;
	}
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
	public String getPrizeserialnum() {
		return prizeserialnum;
	}
	public void setPrizeserialnum(String prizeserialnum) {
		this.prizeserialnum = prizeserialnum;
	}
	public String getPrizeserialname() {
		return prizeserialname;
	}
	public void setPrizeserialname(String prizeserialname) {
		this.prizeserialname = prizeserialname;
	}
	public String getPrizename() {
		return prizename;
	}
	public void setPrizename(String prizename) {
		this.prizename = prizename;
	}
	public int getPrizenum() {
		return prizenum;
	}
	public void setPrizenum(int prizenum) {
		this.prizenum = prizenum;
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
	public int getIndexorder() {
		return indexorder;
	}
	public void setIndexorder(int indexorder) {
		this.indexorder = indexorder;
	}
	
	public long getPrizeid() {
		return prizeid;
	}
	public void setPrizeid(long prizeid) {
		this.prizeid = prizeid;
	}
	public String getIndexordername() {
		return "第"+sameprizeindexorder+"次";
	}
	public void setIndexordername(String indexordername) {
		this.indexordername = indexordername;
	}
	
	public String getPrizetype() {
		return prizetype;
	}
	public void setPrizetype(String prizetype) {
		this.prizetype = prizetype;
	}
	public int getTotalprizenum() {
		return totalprizenum;
	}
	public void setTotalprizenum(int totalprizenum) {
		this.totalprizenum = totalprizenum;
	}
	
	public int getSameprizeindexorder() {
		return sameprizeindexorder;
	}
	public void setSameprizeindexorder(int sameprizeindexorder) {
		this.sameprizeindexorder = sameprizeindexorder;
	}
	
	@Override
	public int compareTo(PrizeUser o) {
		// TODO Auto-generated method stub
		//return this.usernumber.compareTo(o.getUsernumber());
		return this.indexorder-o.indexorder;
	}
}
