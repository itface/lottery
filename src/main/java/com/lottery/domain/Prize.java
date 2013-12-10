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

	
	private static final long serialVersionUID = -1108604706063040536L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
	
	private String prizename;
	private int prizenum;
	private int indexorder;
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
	
	public String getPrizelistlabel() {
		return prizename+"_"+prizenum;
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
