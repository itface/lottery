package com.lottery.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
@Entity
@Table(name="user")
public class User implements Serializable,Comparable<User>{


	private static final long serialVersionUID = -453211585815429686L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
 
	@NotEmpty(message = "帐号不可以为空")
	@Pattern(regexp = "[^'<>=\\\\]*", message = "帐号不能包含特殊字符")
	@Length(max=100,message="帐号长度不能超过100")
	@Column(name="uid",length = 100, unique = true)
	private String uid;

	private String usernumber;
	
    @NotEmpty(message = "姓名不可以为空")
    @Pattern(regexp = "[^'<>=\\\\]*", message = "姓名不能包含特殊字符")
    @Length(max=100,message="姓名长度不能超过100")
    @Column(name="username",length = 100)
    private String username;
    private String dept;
    private String region;
    private String ywdy;
    private String ywdy2;
    private int status;
    private int indexorder;
//    @OneToMany(fetch=FetchType.LAZY,cascade = {CascadeType.REFRESH,CascadeType.PERSIST},mappedBy="user")  
//	Set<PrizeUsers> prizeUsers = new HashSet<PrizeUsers>();
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		int result = 17;
		//result = 37*result+(int) (id ^ (id>>>32));
		result = 37*result+(uid==null?0:uid.hashCode());
		//result = 37*result+displayOrder;
		//result = 37*result+(this.url==null?0:url.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if(!(obj instanceof User)){
			return false;
		}
		User obj2 = (User)obj;
		if(this.uid!=null&&!"".equals(uid)){
			return this.uid==obj2.getUid();
		}else{
			return false;
		}
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
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
	@Override
	public int compareTo(User user) {
		// TODO Auto-generated method stub
		return this.usernumber.compareTo(user.getUsernumber());
	}
}