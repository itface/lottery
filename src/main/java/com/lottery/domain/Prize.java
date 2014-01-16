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
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnore;
@Entity
@Table(name="prize")
public class Prize implements Serializable,Comparable<Prize>{

	
	public static final long serialVersionUID = -1108604706063040536L;
	public final static String PRIZETYPE_SUFFIXNUM = "尾号";
	public final static String PRIZETYPE_NUMBER = "号码";
	public final static String PRIZETYPE_USER = "用户";
	public final static String PRIZETYPE_URL_USER = "user";
	public final static String PRIZETYPE_URL_NOT_USER = "num";
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
	
	private String prizename;
	private int prizenum;
	private int indexorder;
	private String prizetype;
	private int totalprizenum;
	//private int status;
	private String sm;
	private String jp;
	private String jppic;
	@JsonIgnore
	@Transient
	private String prizelistlabel;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "prizeserial_id", referencedColumnName = "id")
	private PrizeSerial prizeSerial=new PrizeSerial();
	
//	@JsonIgnore
//	@OneToMany(fetch=FetchType.LAZY,cascade = {CascadeType.REFRESH,CascadeType.PERSIST},mappedBy="prize")  
//    private Set<PrizeUsers> prizeUsers= new HashSet<PrizeUsers>();
	
	public Prize(){
		
	}
	public void update(Prize prize){
		if(prize!=null){
			this.id=prize.getId();
			this.prizename=prize.getPrizename();
			this.prizenum=prize.getPrizenum();
			this.indexorder=prize.getIndexorder();
			//this.status=prize.getStatus();
			this.sm=prize.getSm();
			this.jp=prize.getJp();
			this.jppic=prize.getJppic();
			
			this.prizetype=prize.getPrizetype();
			this.totalprizenum=prize.getTotalprizenum();
		}
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
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
//	public int getStatus() {
//		return status;
//	}
//	public void setStatus(int status) {
//		this.status = status;
//	}
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
	
	public String getJppic() {
		return jppic;
	}
	public void setJppic(String jppic) {
		this.jppic = jppic;
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
	public String getPrizelistlabel() {
		return totalprizenum+"_"+prizename+"_"+prizenum;
	}
	public void setPrizelistlabel(String prizelistlabel) {
		this.prizelistlabel = prizelistlabel;
	}
	@Override
	public int compareTo(Prize o) {
		// TODO Auto-generated method stub
		return this.indexorder-o.getIndexorder();
	}
	
}
