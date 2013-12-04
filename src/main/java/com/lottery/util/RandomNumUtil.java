package com.lottery.util;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.lottery.domain.User;

public class RandomNumUtil {

	public static List<User> randomUser(List<User> list,int num){
		if(list!=null&&list.size()>0&&num>0){
			Random random = new Random();
			int max = num;
			int min=1;
			int s = random.nextInt(max)%(max-min+1) + min;
		}
		return null;
	}
	 public static Set<User> getRandomNumber(List<User> list,int length){ 
		Random random = new Random(); 
        Set<User> users = new HashSet<User>(length);
        int range = list.size();
        for(int i=0;i<length;i++){  
        	User user = list.get(random.nextInt(range));
        	if(users.contains(user)){
        		i--;
        	}else{
        		users.add(user);
        	}
        }  
        return users;  
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
