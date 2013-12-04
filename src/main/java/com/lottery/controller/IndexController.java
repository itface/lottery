package com.lottery.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lottery.domain.Prize;
import com.lottery.service.ActionService;
import com.lottery.service.PrizeSettingService;
import com.lottery.service.UserService;

@Controller
@RequestMapping(value="/index")
public class IndexController {
	@Autowired
	private PrizeSettingService prizeSettingService;
	@Autowired
	private ActionService actionService;
	@Autowired
	private UserService userService;
	@RequestMapping
	public ModelAndView index(){
		List<Prize> list = prizeSettingService.findAll();
		Map map = new HashMap();
		map.put("prizelist", list);
		return new ModelAndView("/main",map);
	}
	@RequestMapping(value="/action/{prizeid}")
	public @ResponseBody Object action(@PathVariable long prizeid){
		return actionService.action(prizeid);
	}
	@RequestMapping(value="/initall")
	public @ResponseBody void initall(){
	}
	@RequestMapping(value="/inituserstatus")
	public @ResponseBody void inituserstatus(){
		userService.updateAllUserStatus();
	}
}
