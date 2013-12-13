package com.lottery.controller;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lottery.domain.Percentage;
import com.lottery.domain.Prize;
import com.lottery.domain.PrizeSerial;
import com.lottery.domain.PrizeUser;
import com.lottery.service.ActionService;
import com.lottery.service.PrizeSerialService;
import com.lottery.service.PrizeService;
import com.lottery.service.PrizeUserService;
import com.lottery.service.UserService;

@Controller
@RequestMapping(value="/index")
public class IndexController {
	@Autowired
	private PrizeService prizeSettingService;
	@Autowired
	private ActionService actionService;
	@Autowired
	private UserService userService;
	@Autowired
	private PrizeSerialService prizeSerialService;
	@Autowired
	private PrizeUserService prizeUserService;
	
	@RequestMapping
	public ModelAndView index(){
		Map<String,Object> map = new HashMap<String,Object>();
		List<Prize> list = prizeSettingService.findAll();
		if(list!=null&&list.size()>0){
			JsonConfig config = new JsonConfig();
			config.setExcludes(new String[]{"prizeSerial","prizelistlabel"});
			JSONArray jsonlist = JSONArray.fromObject(list,config);
			map.put("prizelistjson", jsonlist);
		}else{
			map.put("prizelistjson", "[]");
		}
		map.put("prizelist", list==null?new ArrayList():list);
		PrizeSerial prizeSerial = prizeSerialService.getActivePrizeSerial();
		long usernum = userService.findActiveUserNum();
		if(prizeSerial!=null&&prizeSerial.getName()!=null&&!"".equals(prizeSerial.getName())&&usernum>0){
			map.put("initflag",true);
		}else{
			map.put("initflag",false);
		}
		map.put("title", prizeSerial==null?"":prizeSerial.getName());
		return new ModelAndView("/main",map);
	}
	@RequestMapping(value="/action/{prizeid}")
	public @ResponseBody Object action(@PathVariable long prizeid){
		return actionService.action(prizeid);
		//return null;
	}
	@RequestMapping(value="/initall")
	public @ResponseBody void initall(){
	}
	@RequestMapping(value="/initpage",method = RequestMethod.GET)
	public ModelAndView initpage(){
		Map<String,Object> map = new HashMap<String,Object>();
		Date date = new Date();
		SimpleDateFormat sd = new SimpleDateFormat("yyyyMMddHHmmss");
		PrizeSerial currentPrizeSerial = prizeSerialService.getActivePrizeSerial();
		if(currentPrizeSerial!=null){
			map.put("pnum", currentPrizeSerial.getId());
			map.put("name", currentPrizeSerial.getName());
		}else{
			PrizeSerial prizeSerial = new PrizeSerial();
			prizeSerial.setNum(sd.format(date));
			prizeSerial.setBegindate(date);
			prizeSerialService.addActivePrizeSerial(prizeSerial);
			map.put("pnum", prizeSerial.getId());
			map.put("name", "");
			
		}
		List<PrizeUser> prizeUserlist = prizeUserService.findCurrentPrizeUser();
		if(prizeUserlist!=null&&prizeUserlist.size()>0){
			map.put("uploadshow", false);
		}else{
			map.put("uploadshow", true);
		}
		return new ModelAndView("/initPage",map);
	}
	@RequestMapping(value="/saveprizeserial",method = RequestMethod.POST)
	public @ResponseBody void savePrizeserial(String name,long pnum){
		prizeSerialService.updatePrizeSerialName(pnum, name);
	}
	@RequestMapping(value="/endprize")
	public @ResponseBody void endprize(){
		prizeSerialService.stopActivePrizeSerial();
	}
	@RequestMapping(value="/inituserstatus")
	public @ResponseBody void inituserstatus(){
		userService.updateAllUserStatus();
	}
	@RequestMapping(value="/currentprizepage",method = RequestMethod.GET)
	public ModelAndView currentprizepage(){
		Map<String,Object> map = new HashMap<String,Object>();
		List<PrizeUser> list = prizeUserService.findCurrentPrizeUser();
		map.put("prizelist", list);
		return new ModelAndView("/prizeCurrent",map);
	}
	@RequestMapping(value="/historyprizepage")
	public ModelAndView historyprizepage(String prizeSerials){
		Map<String,Object> map = new HashMap<String,Object>();
		List<PrizeSerial> prizeSerialsList = prizeSerialService.getInActivePrizeSerial();
		List<PrizeUser> list = prizeUserService.findPrizeUserBySerialnum(prizeSerials);
		map.put("prizeSerials", prizeSerialsList==null?new ArrayList():prizeSerialsList);
		map.put("prizelist", list);
		return new ModelAndView("/prizeHistory",map);
	}
	@RequestMapping(value="/resultreportpage",method = RequestMethod.GET)
	public ModelAndView resultreportpage(){
		long totaluser  = userService.countTotalUser();
		Map<String,Object> map = new HashMap<String,Object>();
		List<Percentage> usernumberPercentage = new ArrayList<Percentage>();
		List<Percentage> regionPercentage= new ArrayList<Percentage>();
		List<Percentage> ywdyPercentage= new ArrayList<Percentage>();
		Map<String,Percentage> dymap = new HashMap<String,Percentage>();
		Map<String,Percentage> ywdymap = new HashMap<String,Percentage>();
		Map<String,Percentage> usernumbermap = new HashMap<String,Percentage>();
		DecimalFormat df = new DecimalFormat();
		String pattern = "#%";
		df.applyPattern(pattern);
		userService.getUserPercentage(dymap, ywdymap, usernumbermap);
		prizeUserService.getUserPercentage(dymap, ywdymap, usernumbermap);
		Iterator<String> dyit = dymap.keySet().iterator();
		while(dyit.hasNext()){
			Percentage percentage = dymap.get(dyit.next());
			regionPercentage.add(percentage);
			double allp = Double.parseDouble(percentage.getPercentOfAll().replaceAll("%", ""));
			double prizep = Double.parseDouble(percentage.getPercentOfPrize()==null?"0":percentage.getPercentOfPrize().replaceAll("%", ""));
			double d = (prizep-allp)/allp;
			//new DecimalFormat("0").format(d);
			//new BigDecimal("2").setScale(0, BigDecimal.ROUND_HALF_UP)
			percentage.setPercentOfDiff(df.format(d));
		}
		Iterator<String> ywdyit = ywdymap.keySet().iterator();
		while(ywdyit.hasNext()){
			Percentage percentage = ywdymap.get(ywdyit.next());
			ywdyPercentage.add(percentage);
			double allp = Double.parseDouble(percentage.getPercentOfAll().replaceAll("%", ""));
			double prizep = Double.parseDouble(percentage.getPercentOfPrize()==null?"0":percentage.getPercentOfPrize().replaceAll("%", ""));
			double d = (prizep-allp)/allp;
			percentage.setPercentOfDiff(df.format(d));
		}
		Iterator<String> usernumberit = usernumbermap.keySet().iterator();
		while(usernumberit.hasNext()){
			Percentage percentage = usernumbermap.get(usernumberit.next());
			usernumberPercentage.add(percentage);
			double allp = Double.parseDouble(percentage.getPercentOfAll().replaceAll("%", ""));
			double prizep = Double.parseDouble(percentage.getPercentOfPrize()==null?"0":percentage.getPercentOfPrize().replaceAll("%", ""));
			double d = (prizep-allp)/allp;
			percentage.setPercentOfDiff(df.format(d));
		}
		Collections.sort(usernumberPercentage);
		map.put("usernumberlist",usernumberPercentage);
		map.put("ywdylist",ywdyPercentage);
		map.put("dylist",regionPercentage);
		map.put("usernumbersize",usernumberPercentage.size()+1);
		map.put("ywdysize",ywdyPercentage.size()+1);
		map.put("dysize",regionPercentage.size()+1);
		map.put("totaluser", totaluser);
		return new ModelAndView("/resultReport",map);
	}
	@RequestMapping(value="/resultreport",method = RequestMethod.GET)
	public @ResponseBody Object resultreport(){
		return prizeUserService.findCurrentPrizeUser();
	}
}
