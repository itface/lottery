package com.lottery.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.lottery.domain.NumberPool;
import com.lottery.domain.SuffixNum;
import com.lottery.domain.User;

public class RandomNumUtil {

	/**
	 * 
	 * @param set还未被抽过的尾号集，如果set为空说明都被抽过或尾号集为空
	 * @param min
	 * @param max
	 * @return
	 */
	public long getRandomOfSuffixnum(Set<SuffixNum> set,int min,int max){
		if(set!=null&&set.size()>0){
			long num = Math.round(Math.random()*(max-min)+min);
			while(true){
				num = Math.round(Math.random()*(max-min)+min);
				SuffixNum s = new SuffixNum();
				s.setSuffixnum((int)num);
				if(set.contains(s)){
					return num;
				}
			}
		}
		return -1;
	}
	public  List<NumberPool> getRandomOfNumber(List<NumberPool> list,int length){
		if(list!=null&&list.size()>0&&length>0){
			List<NumberPool> numberPoolas = new ArrayList<NumberPool>(length);
			Set<Integer> set = getRandom3(0,list.size(),length,null);
			Iterator<Integer> it = set.iterator();
			while(it.hasNext()){
				int n = it.next();
				numberPoolas.add(list.get(n));
			}
			return numberPoolas;
		}
		return null;
	}
	public  List<User> getRandomOfUser(List<User> list,int length){
		if(list!=null&&list.size()>0&&length>0){
			int persizeRatio = 10;
			double dataRatio = 0.3;
			List<User> users = new ArrayList<User>(length);
			Set<Integer> set = new HashSet<Integer>(length);
			int listSize = list.size();
			double realDataRadio = length*1d/listSize;
			if(realDataRadio<=dataRatio){
				int persize = listSize%persizeRatio==0?listSize/persizeRatio:listSize/persizeRatio+1;
				for(int j=0;j<length;j++){
					Set<Integer> set1 = getRandom1(listSize,persize);
					Set<Integer> set2 = getRandom2(listSize,persize);
					//Set<Integer> set3 = getRandom3(listSize,2,persize);
					if(set1!=null&&set2!=null){
						Iterator<Integer> it = set1.iterator();
						boolean flag = true;
						while(it.hasNext()){
							int index = it.next();
							if(set2.contains(index)&&!set.contains(index)){
								set.add(index);
								//index是1到list的长度之间的任意一个数，可以当作是长度，因为作为下标是要減1
								User u = list.get(index-1);
								users.add(u);
								//把u的状态设置为已抽过
								u.setStatus(-1);
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
			}else{
				Set<Integer> set1 = getRandom1(listSize,length);
				if(set1!=null){
					Iterator<Integer> it = set1.iterator();
					while(it.hasNext()){
						int index = it.next();
						User u = list.get(index-1);
						users.add(u);
						//把u的状态设置为已抽过
						u.setStatus(-1);
					}
					return users;
				}
			}
		}
		return null;
	}
	/**
	 * 最大值每一位随机生成最终随机数
	 * @param list
	 * @param length
	 * @return
	 */
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
	/**
	 * 按每一位随机取出从minnum到maxnum之间的length个随机数
	 * @param minnum
	 * @param maxnum
	 * @param perlength
	 * @param length
	 * @return
	 */
	public  Set<Integer> getRandom3(int minnum,int maxnum,int length,Set<Integer> exclude){
		if(maxnum>0&&length>0&&maxnum>=length){
			Set<Integer> set = new HashSet<Integer>(length);
			String s = "";
			Random random = new Random(); 
			String num = maxnum+"";
			String min = minnum+"";
			//下限位数跟上限位数保持一致
			if(min.length()!=num.length()){
				while(num.length()>min.length()){
					min="0"+min;
				}
			}
			String tnum = num;
			String tmin = min;
			int arrlength = num.length();
			String[]  arr = new String[arrlength];
			String[]  minarr = new String[arrlength];
			int arrindex =0;
			//把最大值以每perlength位，放进arr中
			while(num.length()>0){
				arr[arrindex]=num.substring(0, 1);
				num = num.substring(1);
				
				minarr[arrindex]=min.substring(0, 1);
				min = min.substring(1);
				arrindex++;
			}
			//循环取length个随机数
			for(int j=0;j<length;j++){
				boolean loopFlag = true;
				while(loopFlag){
					loopFlag=false;
					//循环arr，分别随机
					for(int i=0;i<arr.length;i++){
						if(i==0){
							//如果是第一位，则取0到第一位+1之间的随机数
							int maxn = Integer.parseInt(arr[i]);
							int minn = Integer.parseInt(minarr[i]);
							long n = Math.round(Math.random()*(maxn-minn)+minn);
							s=n+"";
						}else{
							//如果不是第一位，随机数取从0到9之间的数
							int n = random.nextInt(10);
							s=s+n;
						}
						//如果随机数大于最大值,或者小于最小值，则重新计算。
						if(i==(arr.length-1)&&(s.compareTo(tnum)>0||s.compareTo(tmin)<0)){
							loopFlag=true;
						}
					}
				}
				//去点以0开头的字符
				while(s.indexOf("0")==0){
					s=s.substring(1);
				}
				int n = Integer.parseInt(s.equals("")?"0":s);
				if(set.contains(n)||(exclude==null?false:exclude.contains(n))){
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
	public static void main(String[] args){
		RandomNumUtil util = new RandomNumUtil();
		Set<Integer> set = util.getRandom3(1, 2116, 10,null);
		Iterator<Integer> it = set.iterator();
		while(it.hasNext()){
			System.out.println(it.next());
		}
	}
}
