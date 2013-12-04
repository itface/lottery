package com.lottery.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.lottery.service.UserService;
import com.lottery.util.ExcelReader;

@Controller
@RequestMapping(value="/uploadify")
public class UploadifyController {
	@Autowired
	private UserService impUserService;
	
	@RequestMapping(value="/upload")
	public @ResponseBody String upload(HttpServletRequest request,HttpServletResponse response,@RequestParam MultipartFile[] uploadify) throws IOException, SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		if(uploadify!=null){
			ExcelReader excelReader = new ExcelReader();
			for(int i=0;i<uploadify.length;i++){
				MultipartFile mf = uploadify[i];
				impUserService.impUser(mf);
			}
		}
		return "上传成功";
	}
}
