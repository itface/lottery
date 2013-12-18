package com.lottery.filter;

import java.util.HashSet;
import java.util.Set;

public class SessionCached {

	private static SessionCached sessionCached;
	private Set<String> set = new HashSet<String>();
	private SessionCached(){
		
	}
	public static SessionCached getInstance(){
		if(sessionCached==null){
			synchronized(SessionCached.class){
				if(sessionCached==null){
					sessionCached = new SessionCached();
				}
			}
		}
		return sessionCached;
	}
	public  boolean checkExist(String sessionid){
		return set.contains(sessionid);
	}
	public  void add(String sessionid){
		set.add(sessionid);
	}
	public  void remove(String sessionid){
		set.remove(sessionid);
	}
}
