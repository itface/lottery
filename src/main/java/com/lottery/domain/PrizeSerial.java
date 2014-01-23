package com.lottery.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
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
	private int suffixnumfrom;
	private int suffixnumto;
	private String suffixnumexclude;
	private int numberpoolfrom;
	private int numberpoolto;
	private String numberpoolexclude;
	@JsonIgnore
	@OneToMany(fetch=FetchType.LAZY,cascade = {CascadeType.REMOVE,CascadeType.PERSIST},mappedBy="prizeSerial")
	private Set<Prize> prizes = new HashSet<Prize>();
	@JsonIgnore
	@OneToMany(fetch=FetchType.LAZY,cascade = {CascadeType.REMOVE,CascadeType.PERSIST},mappedBy="prizeSerial")
	private Set<SuffixNum> suffixnums = new HashSet<SuffixNum>();
	@JsonIgnore
	@OneToMany(fetch=FetchType.LAZY,cascade = {CascadeType.REMOVE,CascadeType.PERSIST},mappedBy="prizeSerial")
	private Set<NumberPool> numberpools = new HashSet<NumberPool>();
	@JsonIgnore
	@OneToMany(fetch=FetchType.LAZY,cascade = {CascadeType.REMOVE,CascadeType.PERSIST},mappedBy="prizeSerial")
	private Set<BalanceRule> balancerules = new HashSet<BalanceRule>();
	
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
	public Set<SuffixNum> getSuffixnums() {
		return suffixnums;
	}
	public void setSuffixnums(Set<SuffixNum> suffixnums) {
		this.suffixnums = suffixnums;
	}
	public Set<NumberPool> getNumberpools() {
		return numberpools;
	}
	public void setNumberpools(Set<NumberPool> numberpools) {
		this.numberpools = numberpools;
	}
	public int getSuffixnumfrom() {
		return suffixnumfrom;
	}
	public void setSuffixnumfrom(int suffixnumfrom) {
		this.suffixnumfrom = suffixnumfrom;
	}
	public int getSuffixnumto() {
		return suffixnumto;
	}
	public void setSuffixnumto(int suffixnumto) {
		this.suffixnumto = suffixnumto;
	}
	public int getNumberpoolfrom() {
		return numberpoolfrom;
	}
	public void setNumberpoolfrom(int numberpoolfrom) {
		this.numberpoolfrom = numberpoolfrom;
	}
	public int getNumberpoolto() {
		return numberpoolto;
	}
	public void setNumberpoolto(int numberpoolto) {
		this.numberpoolto = numberpoolto;
	}
	public String getSuffixnumexclude() {
		return suffixnumexclude;
	}
	public void setSuffixnumexclude(String suffixnumexclude) {
		this.suffixnumexclude = suffixnumexclude;
	}
	public String getNumberpoolexclude() {
		return numberpoolexclude;
	}
	public void setNumberpoolexclude(String numberpoolexclude) {
		this.numberpoolexclude = numberpoolexclude;
	}
	public Set<BalanceRule> getBalancerules() {
		return balancerules;
	}
	public void setBalancerules(Set<BalanceRule> balancerules) {
		this.balancerules = balancerules;
	}
	public List<BalanceRule> getBalanceruleList() {
		List<BalanceRule> list = new ArrayList<BalanceRule>();
		if(balancerules!=null&&balancerules.size()>0){
			list.addAll(balancerules);
			Collections.sort(list);
		}
		return list;
	}
	public Set<SuffixNum> getActiveSuffixNum(){
		Set<SuffixNum> set = new HashSet<SuffixNum>();
		if(suffixnums!=null&&suffixnums.size()>0){
			Iterator<SuffixNum> it = suffixnums.iterator();
			while(it.hasNext()){
				SuffixNum suffixNum = it.next();
				if(suffixNum.getPrizeid()==0){
					set.add(suffixNum);
				}
			}
		}
		return set;
	}
	public Set<NumberPool> getActiveNumberPool(){
		Set<NumberPool> set = new HashSet<NumberPool>();
		if(numberpools!=null&&numberpools.size()>0){
			Iterator<NumberPool> it = numberpools.iterator();
			while(it.hasNext()){
				NumberPool nb = it.next();
				if(nb.getStatus()==0){
					set.add(nb);
				}
			}
		}
		return set;
	}
	public boolean checkSuffixNumRepeat(int number){
		if(suffixnums!=null&&suffixnums.size()>0){
			Iterator<SuffixNum> it = suffixnums.iterator();
			while(it.hasNext()){
				SuffixNum suffixNum = it.next();
				if(suffixNum.getPrizeid()!=0&&suffixNum.getSuffixnum()==number){
					return true;
				}
			}
		}
		return false;
	}
	public boolean checkNumberPoolRepeat(Set<NumberPool> set){
		if(numberpools!=null&&numberpools.size()>0){
			Iterator<NumberPool> it = numberpools.iterator();
			while(it.hasNext()){
				NumberPool nb = it.next();
				if(nb.getStatus()==-1&&set.contains(nb)){
					return true;
				}
			}
		}
		return false;
	}
	public void updateSuffixNum(int num,long prizeid){
		if(suffixnums!=null&&suffixnums.size()>0){
			Iterator<SuffixNum> it = suffixnums.iterator();
			while(it.hasNext()){
				SuffixNum suffixNum = it.next();
				if(suffixNum.getSuffixnum()==num){
					suffixNum.setPrizeid(prizeid);
				}
			}
		}
	}
	public List<Prize> getPrizes(String type) {
		List<Prize> list = new ArrayList<Prize>();
		if(Prize.PRIZETYPE_URL_USER.equals(type)){
			if(prizes!=null&&prizes.size()>0){
				for(Prize prize : prizes){
					if(Prize.PRIZETYPE_USER.equals(prize.getPrizetype())){
						list.add(prize);
					}
				}
			}
		}else if(Prize.PRIZETYPE_URL_NOT_USER.equals(type)){
			if(prizes!=null&&prizes.size()>0){
				for(Prize prize : prizes){
					if(Prize.PRIZETYPE_NUMBER.equals(prize.getPrizetype())||Prize.PRIZETYPE_SUFFIXNUM.equals(prize.getPrizetype())){
						list.add(prize);
					}
				}
			}
		}
		return list;
	}
	
}
