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
			//int persizeRatio = 10;
			List<User> users = new ArrayList<User>(length);
			//Set<Integer> set = new HashSet<Integer>(length);
			int listSize = list.size();
			//double realDataRadio = length*1d/listSize;
			//int persize = listSize%persizeRatio==0?listSize/persizeRatio:listSize/persizeRatio+1;
			Set<Integer> set1 = getRandom1(listSize,length);
			if(set1!=null){
				Iterator<Integer> it = set1.iterator();
				while(it.hasNext()){
					int index = it.next();
					users.add(list.get(index-1));
				}
				return users;
			}
			return users;
		}
		return null;
//		if(list!=null&&list.size()>0&&length>0){
//			int persizeRatio = 10;
//			double dataRatio = 0.1;
//			List<User> users = new ArrayList<User>(length);
//			Set<Integer> set = new HashSet<Integer>(length);
//			int listSize = list.size();
//			double realDataRadio = length*1d/listSize;
//			if(realDataRadio<dataRatio){
//				int persize = listSize%persizeRatio==0?listSize/persizeRatio:listSize/persizeRatio+1;
//				for(int j=0;j<length;j++){
//					Set<Integer> set1 = getRandom1(listSize,persize);
//					Set<Integer> set2 = getRandom2(listSize,persize);
//					Set<Integer> set3 = getRandom3(listSize,2,persize);
//					if(set1!=null&&set2!=null&&set3!=null){
//						Iterator<Integer> it = set1.iterator();
//						boolean flag = true;
//						while(it.hasNext()){
//							int index = it.next();
//							if(set2.contains(index)&&set3.contains(index)&&!set.contains(index)){
//								set.add(index);
//								//index是1到list的长度之间的任意一个数，可以当作是长度，因为作为下标是要減1
//								users.add(list.get(index-1));
//								flag=false;
//								break;
//							}
//						}
//						if(flag){
//							j--;
//						}
//					}
//				}
//				return users;
//			}else{
//				Set<Integer> set1 = getRandom1(listSize,length);
//				if(set1!=null){
//					Iterator<Integer> it = set1.iterator();
//					while(it.hasNext()){
//						int index = it.next();
//						users.add(list.get(index-1));
//					}
//					return users;
//				}
//			}
//		}
//		return null;
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
	public  Set<Integer> getRandom3(int maxnum,int perlength,int length){
		if(maxnum>0&&length>0&&maxnum>=length){
			Set<Integer> set = new HashSet<Integer>(length);
			String s = "";
			Random random = new Random(); 
			String num = maxnum+"";
			String tnum = num;
			int arrlength = (num.length()/perlength)+(num.length()%perlength==0?0:1);
			String[]  arr = new String[arrlength];
			int arrindex =0;
			while(num.length()>=perlength){
				arr[arrindex++]=num.substring(0, perlength);
				num = num.substring(perlength);
			}
			if(num.length()>0){
				arr[arrlength-1]=num;
			}
			for(int j=0;j<length;j++){
				boolean loopFlag = true;
				while(loopFlag){
					loopFlag=false;
					boolean zeroFlag = true;
					for(int i=0;i<arr.length;i++){
						if(i==0){
							//如果是第一位，则取0到第一位+1之间的随机数
							int n = random.nextInt(Integer.parseInt(arr[i])+1);
							s=n+"";
							//如果数字只是个位数，并且不为0，那么就不用重新计算
							if(n!=0){
								zeroFlag=false;
							}
						}else{
							//如果不是第一位，取当前arr[i]的数值位数相同的9，比如arr[1]=44,那么此随机数就取0到99之间的数
							int numlength = arr[i].length();
							int maxrandomnum = 1;
							for(int k=1;k<=numlength;k++){
								maxrandomnum=maxrandomnum*10;
							}
							int n = random.nextInt(maxrandomnum);
							s=s+n;
							//如果非第一个数字之后有任何一个数据不为0，说明随机数不是0，不用重新计算
							if(n!=0){
								zeroFlag=false;
							}
						}
						//如果随机数是每一位都是0，或者随机数大于最大值，则重新计算。
						if(i==(arr.length-1)&&(zeroFlag||s.compareTo(tnum)>0)){
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
		RandomNumUtil util = new RandomNumUtil();
		Set<Integer> set = util.getRandom3(4300,1,20);
		if(set!=null&&set.size()>0){
			Iterator<Integer> it = set.iterator();
			while(it.hasNext()){
				System.out.println(it.next());
			}
		}
	}
}
