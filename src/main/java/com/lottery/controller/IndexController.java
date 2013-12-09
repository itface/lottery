package com.lottery.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lottery.domain.Prize;
import com.lottery.domain.PrizeSerial;
import com.lottery.service.ActionService;
import com.lottery.service.PrizeSerialService;
import com.lottery.service.PrizeService;
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
	@RequestMapping
	public ModelAndView index(){
		List<Prize> list = prizeSettingService.findAll();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("prizelist", list==null?new ArrayList():list);
		PrizeSerial prizeSerial = prizeSerialService.getActivePrizeSerial();
		long usernum = userService.findActiveUserNum();
		if(prizeSerial!=null&&prizeSerial.getName()!=null&&!"".equals(prizeSerial.getName())&&usernum>0){
			map.put("initflag",true);
		}else{
			map.put("initflag",false);
		}
		return new ModelAndView("/main",map);
	}
	@RequestMapping(value="/action/{prizeid}")
	public @ResponseBody Object action(@PathVariable long prizeid){
		return actionService.action(prizeid);
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
}
