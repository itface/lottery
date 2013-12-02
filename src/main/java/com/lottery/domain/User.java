package com.lottery.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
@Entity
@Table(name="user")
public class User implements Serializable{


	private static final long serialVersionUID = -453211585815429686L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
 
	@NotEmpty(message = "帐号不可以为空")
	@Pattern(regexp = "[^'<>=\\\\]*", message = "帐号不能包含特殊字符")
	@Length(max=100,message="帐号长度不能超过100")
	@Column(name="uid",length = 100, unique = true)
	private String uid;

	
    @NotEmpty(message = "姓名不可以为空")
    @Pattern(regexp = "[^'<>=\\\\]*", message = "姓名不能包含特殊字符")
    @Length(max=100,message="姓名长度不能超过100")
    @Column(name="username",length = 100)
    private String username;
    private String dept;
    private String region;
    private int status;
    
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
	
}