package com.lottery.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.lottery.domain.User;

public class RandomNumUtil {

	public static List<User> randomUser(List<User> list,int num){
		if(list!=null&&list.size()>0&&num>0){
			
		}
		return null;
	}
	 public static List<User> getRandomList(List<User> list){ 
		if(list!=null&&list.size()>0){
			int length = list.size();
			Random random = new Random(); 
	        List<User> all = new ArrayList<User>(length);
	        Set<Integer> indexs = new HashSet<Integer>(length);
	        for(int i=0;i<length;i++){  
	        	int index = random.nextInt(length);
	        	if(indexs.contains(index)){
	        		i--;
	        	}else{
	        		all.add(list.get(index));
	        		indexs.add(index);
	        	}
	        }  
	        return all;  
		}
		return list;
	}  
	 public static List<User> getRandomNumber(List<User> list,int length){ 
		Random random = new Random(); 
        Set<User> users = new HashSet<User>(length);
        List<User> all = new ArrayList<User>(length);
        int range = list.size();
        for(int i=0;i<length;i++){  
        	User user = list.get(random.nextInt(range));
        	if(users.contains(user)){
        		i--;
        	}else{
        		users.add(user);
        		all.add(user);
        	}
        }  
        return all;  
	}  
	public static void main(String[] args){
		/*
		List<User> list = new ArrayList<User>();
		for(int i=0;i<100;i++){  
			User user = new User();
			user.setId(i);
			list.add(user);
		}
		Set<User> users = getRandomNumber(list,80,15);
		for(User user : users){
			System.out.println(user.getId());
		}
		*/
	}
}
