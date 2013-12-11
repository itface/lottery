package com.lottery.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
		return new ModelAndView("/resultReport");
	}
	@RequestMapping(value="/resultreport",method = RequestMethod.GET)
	public @ResponseBody Object resultreport(){
		return prizeUserService.findCurrentPrizeUser();
	}
}
