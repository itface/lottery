package com.lottery.controller;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lottery.domain.Prize;
import com.lottery.service.PrizeSettingService;

@Controller
@RequestMapping(value="/prizesetting")
public class PrizeSettingController {
	@Autowired
	private PrizeSettingService prizeSettingService;
	
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody Object getJqgridData() {
		JSONObject jsonObject = prizeSettingService.findAllJson();
		return jsonObject==null?"{}":jsonObject;
	}
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody void _new(Prize prize){
		prizeSettingService.addPrize(prize);
	}
	@RequestMapping(method = RequestMethod.DELETE)
	public @ResponseBody void delete(String param){
		prizeSettingService.deletePrize(param);
	}
	@RequestMapping(value=("/{prizeid}"),method = RequestMethod.PUT)
	public @ResponseBody void update(Prize prize){
		prizeSettingService.updatePrize(prize);
	}
	@RequestMapping(value="/getOrderSelection",method = RequestMethod.GET)
	public @ResponseBody String getOrderSelection(String indexorder) {
		return prizeSettingService.getOrderSelection(indexorder);
	}
}
