package com.lottery.controller;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

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
		return "true";
	}
	@RequestMapping(value="/uploadbgpage",method = RequestMethod.GET)
	public ModelAndView uploadbgpage(){
		return new ModelAndView("/uploadbg");
	}
	@RequestMapping(value="/uploadbg")
	public @ResponseBody String uploadbg(HttpServletRequest request,HttpServletResponse response,@RequestParam MultipartFile[] uploadify) throws IOException, SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		if(uploadify!=null){
			String basePath = request.getSession().getServletContext().getRealPath("/");
			String savePath = basePath+File.separator+"resources"+File.separator+"bg";
			String realfilename = "bg.jpg";
			File dir = new File(savePath);
			if(!dir.exists()){
				dir.mkdirs();
			}
			for(int i=0;i<uploadify.length;i++){
				MultipartFile mf = uploadify[i];
				String fileName = mf.getOriginalFilename();
				String suffix = fileName.substring(fileName.lastIndexOf("."));
				File uploadFile = new File(savePath +File.separator+ realfilename);
				while(uploadFile.exists()){
					//uploadFile = new File(savePath +File.separator+(count++)+"_"+fileName);
					uploadFile.delete();
				}
				FileCopyUtils.copy(mf.getBytes(), uploadFile);
			}
		}
		return "true";
	}
	@RequestMapping(value="/uploadmusicpage",method = RequestMethod.GET)
	public ModelAndView uploadmusicpage(){
		return new ModelAndView("/uploadmusic");
	}
	@RequestMapping(value="/uploadmusic1")
	public @ResponseBody String uploadmusic1(HttpServletRequest request,HttpServletResponse response,@RequestParam MultipartFile[] uploadify1) throws IOException, SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		if(uploadify1!=null){
			String basePath = request.getSession().getServletContext().getRealPath("/");
			String savePath = basePath+File.separator+"resources"+File.separator+"music";
			String realfilename = "bgmusic.mp3";
			File dir = new File(savePath);
			if(!dir.exists()){
				dir.mkdirs();
			}
			for(int i=0;i<uploadify1.length;i++){
				MultipartFile mf = uploadify1[i];
				String fileName = mf.getOriginalFilename();
				//String suffix = fileName.substring(fileName.lastIndexOf("."));
				File uploadFile = new File(savePath +File.separator+ realfilename);
				while(uploadFile.exists()){
					//uploadFile = new File(savePath +File.separator+(count++)+"_"+fileName);
					uploadFile.delete();
				}
				FileCopyUtils.copy(mf.getBytes(), uploadFile);
			}
		}
		return "true";
	}
	@RequestMapping(value="/uploadmusic2")
	public @ResponseBody String uploadmusic2(HttpServletRequest request,HttpServletResponse response,@RequestParam MultipartFile[] uploadify2) throws IOException, SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		if(uploadify2!=null){
			String basePath = request.getSession().getServletContext().getRealPath("/");
			String savePath = basePath+File.separator+"resources"+File.separator+"music";
			String realfilename = "startmusic.mp3";
			File dir = new File(savePath);
			if(!dir.exists()){
				dir.mkdirs();
			}
			for(int i=0;i<uploadify2.length;i++){
				MultipartFile mf = uploadify2[i];
				String fileName = mf.getOriginalFilename();
				//String suffix = fileName.substring(fileName.lastIndexOf("."));
				File uploadFile = new File(savePath +File.separator+ realfilename);
				while(uploadFile.exists()){
					//uploadFile = new File(savePath +File.separator+(count++)+"_"+fileName);
					uploadFile.delete();
				}
				FileCopyUtils.copy(mf.getBytes(), uploadFile);
			}
		}
		return "true";
	}
	@RequestMapping(value="/uploadmusic3")
	public @ResponseBody String uploadmusic3(HttpServletRequest request,HttpServletResponse response,@RequestParam MultipartFile[] uploadify3) throws IOException, SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		if(uploadify3!=null){
			String basePath = request.getSession().getServletContext().getRealPath("/");
			String savePath = basePath+File.separator+"resources"+File.separator+"music";
			String realfilename = "stopmusic.mp3";
			File dir = new File(savePath);
			if(!dir.exists()){
				dir.mkdirs();
			}
			for(int i=0;i<uploadify3.length;i++){
				MultipartFile mf = uploadify3[i];
				String fileName = mf.getOriginalFilename();
				//String suffix = fileName.substring(fileName.lastIndexOf("."));
				File uploadFile = new File(savePath +File.separator+ realfilename);
				while(uploadFile.exists()){
					//uploadFile = new File(savePath +File.separator+(count++)+"_"+fileName);
					uploadFile.delete();
				}
				FileCopyUtils.copy(mf.getBytes(), uploadFile);
			}
		}
		return "true";
	}
}
