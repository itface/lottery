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
@Table(name="tempuser")
public class TempUser implements Serializable{


	private static final long serialVersionUID = -884192681296194661L;

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
		if(!(obj instanceof TempUser)){
			return false;
		}
		TempUser obj2 = (TempUser)obj;
		if(this.uid!=null&&!"".equals(uid)){
			return this.uid==obj2.getUid();
		}else{
			return false;
		}
	}
	public TempUser(){
		
	}
	public TempUser(User user){
		this.dept=user.getDept();
		this.indexorder=user.getIndexorder();
		this.region=user.getRegion();
		this.status=user.getStatus();
		this.uid=user.getUid();
		this.username=user.getUsername();
		this.usernumber=user.getUsernumber();
		this.ywdy=user.getYwdy();
		this.ywdy2=user.getYwdy2();
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
	
}