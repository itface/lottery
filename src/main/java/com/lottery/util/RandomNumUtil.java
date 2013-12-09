package com.lottery.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.lottery.domain.User;

public class RandomNumUtil {

	public  List<User> getRandom(List<User> list,int length){
		if(list!=null&&list.size()>0&&length>0){
			List<User> users = new ArrayList<User>(length);
			Set<Integer> set = new HashSet<Integer>(length);
			int listSize = list.size();
			for(int j=0;j<length;j++){
				Set<Integer> set1 = getRandom1(listSize,100);
				Set<Integer> set2 = getRandom2(listSize,100);
				if(set1!=null&&set2!=null){
					Iterator<Integer> it = set1.iterator();
					boolean flag = true;
					while(it.hasNext()){
						int index = it.next();
						if(set2.contains(index)&&!set.contains(index)){
							set.add(index);
							//index是1到list的长度之间的任意一个数，可以当作是长度，因为作为下标是要減1
							users.add(list.get(index-1));
							flag=false;
							break;
						}
					}
					if(flag){
						j--;
					}
				}
			}
			return users;
		}
		return null;
	}
	public  Set<Integer> getRandom1(int maxnum,int length){
		if(maxnum>0&&length>0&&maxnum>=length){
			Set<Integer> set = new HashSet<Integer>(length);
			String s = "";
			Random random = new Random(); 
			String num = maxnum+"";
			for(int j=0;j<length;j++){
				boolean loopFlag = true;
				while(loopFlag){
					loopFlag=false;
					boolean zeroFlag = true;
					for(int i=0;i<num.length();i++){
						if(i==0){
							//如果是第一位，则取0到第一位+1之间的随机数
							String ts = num.substring(i, i+1);
							int n = random.nextInt(Integer.parseInt(ts)+1);
							s=n+"";
							//如果数字只是个位数，并且不为0，那么就不用重新计算
							if(n!=0){
								zeroFlag=false;
							}
						}else{
							//如果不是第一位，取0到9之间的随机数
							int n = random.nextInt(10);
							s=s+n;
							//如果非第一个数字之后有任何一个数据不为0，说明随机数不是0，不用重新计算
							if(n!=0){
								zeroFlag=false;
							}
						}
						//如果随机数是每一位都是0，或者随机数大于最大值，则重新计算。
						if(i==(num.length()-1)&&(zeroFlag||s.compareTo(num)>0)){
							//i=-1;
							loopFlag=true;
						}
					}
				}
				//去点以0开头的字符
				while(s.indexOf("0")==0){
					s=s.substring(1);
				}
				int n = Integer.parseInt(s);
				if(set.contains(n)){
					j--;
				}else{
					set.add(n);
				}
			}
			return set;
		}
		return null;
	}
	public  Set<Integer> getRandom2(int maxnum,int length){
		if(maxnum>0&&length>0&&maxnum>=length){
			Set<Integer> set = new HashSet<Integer>(length);
			Random random = new Random();
			 for(int i=0;i<length;i++){  
		        int num = random.nextInt(maxnum+1);
		        if(set.contains(num)){
					i--;
				}else{
					set.add(num);
				}
			 }
			 return set;
		}
		return null;
	}
	 public  List<User> getRandomList(List<User> list){ 
		if(list!=null&&list.size()>0){
			int length = list.size();
			Random random = new Random(); 
	        List<User> all = new ArrayList<User>(length);
	        Set<Integer> indexs = new HashSet<Integer>(length);
	        for(int i=0;i<length;i++){  
	        	//随机数是大于等于0，小于list长度的数
	        	int index = random.nextInt(length);
	        	if(indexs.contains(index)){
	        		i--;
	        	}else{
	        		User user = list.get(index);
	        		user.setIndexorder(i+1);
	        		all.add(user);
	        		indexs.add(index);
	        	}
	        }  
	        return all;  
		}
		return list;
	}  
//	 public static List<User> getRandomNumber2(List<User> list,int length){ 
//		Random random = new Random(); 
//        Set<User> users = new HashSet<User>(length);
//        List<User> all = new ArrayList<User>(length);
//        int range = list.size();
//        for(int i=0;i<length;i++){  
//        	User user = list.get(random.nextInt(range));
//        	if(users.contains(user)){
//        		i--;
//        	}else{
//        		users.add(user);
//        		all.add(user);
//        	}
//        }  
//        return all;  
//	}  
	public static void main(String[] args){
		boolean flag =true;
		for(int i=0;i<10;i++){
			System.out.println(i);
			if(i==9&&flag){
				i=-1;
				flag=false;
			}
		}
		//Set<Integer> set = getRandom1(4300,100);
//		if(set!=null&&set.size()>0){
//			Iterator<Integer> it = set.iterator();
//			while(it.hasNext()){
//				System.out.println(it.next());
//			}
//		}
	}
}
