package com.lottery.controller;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lottery.domain.BalanceRule;
import com.lottery.domain.Percentage;
import com.lottery.domain.Prize;
import com.lottery.domain.PrizeSerial;
import com.lottery.domain.PrizeUser;
import com.lottery.service.ActionService;
import com.lottery.service.BalanceRuleService;
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
	@Autowired
	private BalanceRuleService balanceRuleService;
	
	@RequestMapping
	public ModelAndView index(String id,HttpServletRequest request){
		Map<String,Object> map = new HashMap<String,Object>();
		String basePath = request.getSession().getServletContext().getRealPath("/");
		{//检查是否初始化过
			PrizeSerial prizeSerial = prizeSerialService.getActivePrizeSerial();
			//long usernum = userService.findActiveUserNum();
			if(prizeSerial!=null&&prizeSerial.getName()!=null&&!"".equals(prizeSerial.getName())){
				map.put("initflag",true);
				map.put("title", prizeSerial==null?"":prizeSerial.getName());
			}else{
				map.put("initflag",false);
			}
		}
		{//奖项
			List<Prize> list = prizeSettingService.findAll();
			//增加空选项
			Prize prize = new Prize();
			prize.setId(0);
			prize.setPrizetype("");
			prize.setIndexorder(0);
			prize.setPrizename("屏保");
			if(list!=null&&list.size()>0){
				list.add(prize);
				Collections.sort(list);
				JsonConfig config = new JsonConfig();
				config.setExcludes(new String[]{"prizeSerial","prizelistlabel"});
				JSONArray jsonlist = JSONArray.fromObject(list,config);
				map.put("prizelistjson", jsonlist);
				map.put("prizelist",list);
			}else{
				list = new ArrayList<Prize>();
				list.add(prize);
				map.put("prizelistjson", "[]");
				map.put("prizelist",list);
			}
		}
		{//设置背景图
			String savePath = basePath+File.separator+"resources"+File.separator+"bg";
			File bg = new File(savePath +File.separator+ "bg.jpg");
			if(bg.exists()){
				map.put("bgname", "bg.jpg");
			}else{
				map.put("bgname", "defaultbg.jpg");
			}
		}
		{//设置音乐
			String savePath = basePath+File.separator+"resources"+File.separator+"music";
			File bg = new File(savePath +File.separator+ "bgmusic.mp3");
			if(bg.exists()){
				map.put("bgmusic", "bgmusic.mp3");
			}else{
				map.put("bgmusic", "defaultbgmusic.mp3");
			}
			bg = new File(savePath +File.separator+ "startmusic.mp3");
			if(bg.exists()){
				map.put("startmusic", "startmusic.mp3");
			}else{
				map.put("startmusic", "defaultstartmusic.mp3");
			}
			bg = new File(savePath +File.separator+ "stopmusic.mp3");
			if(bg.exists()){
				map.put("stopmusic", "stopmusic.mp3");
			}else{
				map.put("stopmusic", "defaultstopmusic.mp3");
			}
		}
		if(id!=null&&!"".equals(id)&&!"0".equals(id)){
			Prize prize = prizeSettingService.findById(Long.parseLong(id));
			if(prize!=null){
				boolean flag = actionService.checkActionFlag(prize.getId());
				map.put("prizeid",id);
				map.put("actionstartbtnshow",flag?"visible":"hidden");
				map.put("lotterystatus", flag?"init":"stop");
				if(Prize.PRIZETYPE_USER.equals(prize.getPrizetype())){
					map.put("userlist", userService.findAllActiveUserame());
					return new ModelAndView("/mainUser",map);
				}else if(Prize.PRIZETYPE_SUFFIXNUM.equals(prize.getPrizetype())||Prize.PRIZETYPE_NUMBER.equals(prize.getPrizetype())){
					return new ModelAndView("/mainNumber",map);
				}
			}
		}
		return new ModelAndView("/mainBlank",map);
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
			map.put("ycjxid",prizeUserService.getYcjxId(currentPrizeSerial.getNum()));
			map.put("ycbalanceid",balanceRuleService.getYcjxId(currentPrizeSerial.getNum()));
			map.put("suffixnumfrom", currentPrizeSerial.getSuffixnumfrom()==-1?"":currentPrizeSerial.getSuffixnumfrom());
			map.put("suffixnumto", currentPrizeSerial.getSuffixnumto()==-1?"":currentPrizeSerial.getSuffixnumto());
			map.put("suffixnumexclude", currentPrizeSerial.getSuffixnumexclude());
			map.put("numberpoolfrom", currentPrizeSerial.getNumberpoolfrom()==-1?"":currentPrizeSerial.getNumberpoolfrom());
			map.put("numberpoolto", currentPrizeSerial.getNumberpoolto()==-1?"":currentPrizeSerial.getNumberpoolto());
			map.put("numberpoolexclude", currentPrizeSerial.getNumberpoolexclude());
			//List<PrizeUser> prizeUserlist = prizeUserService.findCurrentPrizeUser();
			if(prizeUserService.ifHavePrizeUserOfNum(currentPrizeSerial.getNum())){
				map.put("prizeuserofnum", true);
			}else{
				map.put("prizeuserofnum", false);
			}
			if(prizeUserService.ifHavePrizeUserOfUser(currentPrizeSerial.getNum())){
				map.put("uploadshow", false);
			}else{
				map.put("uploadshow", true);
			}
		}else{
			PrizeSerial prizeSerial = new PrizeSerial();
			prizeSerial.setNum(sd.format(date));
			prizeSerial.setBegindate(date);
			prizeSerial.setSuffixnumfrom(-1);
			prizeSerial.setSuffixnumto(-1);
			prizeSerial.setNumberpoolfrom(-1);
			prizeSerial.setNumberpoolto(-1);
			prizeSerialService.addActivePrizeSerial(prizeSerial);
			map.put("pnum", prizeSerial.getId());
			map.put("name", "");
			map.put("uploadshow", true);
			map.put("prizeuserofnum", false);
			map.put("ycjxid","{}");
			map.put("ycbalanceid","{}");
		}
		return new ModelAndView("/initPage",map);
	}
	@RequestMapping(value="/saveprizeserial",method = RequestMethod.POST)
	public @ResponseBody void savePrizeserial(String name,long pnum,int suffixnumfrom,int suffixnumto,String suffixnumexclude,int numberpoolfrom,int numberpoolto,String numberpoolexclude){
		prizeSerialService.update(pnum, name,suffixnumfrom,suffixnumto,suffixnumexclude,numberpoolfrom,numberpoolto,numberpoolexclude);
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
		map.put("resulthtml",prizeUserService.findPrizeUserHtmlOfCurrent());
		return new ModelAndView("/prizeCurrent",map);
	}
	@RequestMapping(value="/historyprizepage")
	public ModelAndView historyprizepage(String prizeSerials){
		Map<String,Object> map = new HashMap<String,Object>();
		List<PrizeSerial> prizeSerialsList = prizeSerialService.getInActivePrizeSerial();
		map.put("prizeSerials", prizeSerialsList==null?new ArrayList():prizeSerialsList);
		map.put("resulthtml",prizeUserService.findPrizeUserHtmlOfHistory(prizeSerials));
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
		//map.put("usernumbersize",usernumberPercentage.size()+1);
		//map.put("ywdysize",ywdyPercentage.size()+1);
		//map.put("dysize",regionPercentage.size()+1);
		map.put("totaluser", totaluser);
		return new ModelAndView("/resultReport",map);
	}
	@RequestMapping(value="/resultreport",method = RequestMethod.GET)
	public @ResponseBody Object resultreport(){
		return prizeUserService.findPrizeUserOfCurrentByType(Prize.PRIZETYPE_USER);
	}
	@RequestMapping(value="/checkuser")
	public @ResponseBody boolean checkuser(int prizelength){
		long totaluser  = userService.findActiveUserNum();
		if(prizelength>totaluser||prizelength<1){
			return false;
		}
		return true;
	}
	@RequestMapping(value="/prizeuserlist",method = RequestMethod.GET)
	public @ResponseBody Object prizeuserlist(long prizeid){
		return prizeUserService.findPrizeUserById(prizeid);
	}
	@RequestMapping(value="/getusernum",method = RequestMethod.GET)
	public @ResponseBody long getusernum(){
		return userService.countTotalUser();
	}
	@RequestMapping(value="/getbanlanceruleSelecctString",method = RequestMethod.GET)
	public @ResponseBody String getbanlanceruleSelecctString(String indexorder) throws UnsupportedEncodingException{
		return balanceRuleService.getOrderSelection(indexorder);
	}

	
	
	@RequestMapping(value="/balancerule",method = RequestMethod.GET)
	public @ResponseBody Object getbalancerule() {
		JSONObject jsonObject = balanceRuleService.findAllJson();
		return jsonObject==null?"{}":jsonObject;
	}
	@RequestMapping(value="/balancerule",method = RequestMethod.POST)
	public @ResponseBody void newbalancerule(BalanceRule balanceRule){
		balanceRuleService.add(balanceRule);
	}
	@RequestMapping(value="/balancerule",method = RequestMethod.DELETE)
	public @ResponseBody void deletebalancerule(String param){
		balanceRuleService.delete(param);
	}
	@RequestMapping(value=("/balancerule/{prizeid}"),method = RequestMethod.PUT)
	public @ResponseBody void updatebalancerule(BalanceRule balanceRule){
		balanceRuleService.update(balanceRule);
	}
	@RequestMapping(value="/testbalancerule",method = RequestMethod.GET)
	public @ResponseBody String testbalancerule(long id) {
		return balanceRuleService.testBalanceRule(id);
	}
}
