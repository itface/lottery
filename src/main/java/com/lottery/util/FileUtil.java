package com.lottery.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;

import com.lottery.domain.PrizeUser;

public class FileUtil {

	public void writeFile(String file,List<PrizeUser> list) throws IOException{
		File f = new File(file);
		if(!f.exists()){
			f.createNewFile();
		}
		if(list!=null&&list.size()>0){
			SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			PrintWriter writer = null;
			try {
				writer = new PrintWriter(new FileOutputStream(f));
				for(PrizeUser p : list){
					//writer.println(p.getId()+"	"+p.getPrizeid()+"	"+p.getSerialnumber()+"	"+p.getUid()+"	"+sd.format(p.getPrizetime()));
				}
			}finally{
				if(writer!=null){
					writer.close();
				}
			}
		}
	}
}
