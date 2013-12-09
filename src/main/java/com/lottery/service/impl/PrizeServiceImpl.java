package com.lottery.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lottery.dao.BaseDao;
import com.lottery.domain.Jqgrid_DataJson;
import com.lottery.domain.Prize;
import com.lottery.domain.PrizeSerial;
import com.lottery.service.PrizeSerialService;
import com.lottery.service.PrizeService;
import com.lottery.util.JsonUtils;
@Service
public class PrizeServiceImpl implements PrizeService{

	@Autowired
	private BaseDao<Prize> dao;
	@Autowired
	private PrizeSerialService prizeSerialService;
	
	@Override
	public int getPrizeOrder(long id) {
		// TODO Auto-generated method stub
		Prize prize = dao.findById(Prize.class, id);
		if(prize!=null){
			return prize.getIndexorder();
		}
		return 0;
	}

	@Override
	@Transactional
	public void addPrize(Prize prize) {
		// TODO Auto-generated method stub
		if(prize!=null){
			int order  = prize.getIndexorder();
			dao.executeUpdate("update Prize t set t.indexorder=t.indexorder+1 where t.indexorder>=?1",new Object[]{order});
			//dao.persist(prize);
			PrizeSerial prizeSerial = prizeSerialService.getActivePrizeSerial();
			if(prizeSerial!=null){
				prize.setPrizeSerial(prizeSerial);
				prizeSerial.getPrizes().add(prize);
			}
		}
	}
	@Override
	@Transactional
	public void updatePrize(Prize prize) {
		// TODO Auto-generated method stub
		if(prize!=null&&prize.getId()>0){
			Prize old = this.findById(prize.getId());
			int oldOrder = old.getIndexorder();
			int newOrder = prize.getIndexorder();
			if(oldOrder>newOrder){
				dao.executeUpdate("update Prize t set t.indexorder=t.indexorder+1 where t.indexorder>=?1 and t.id!=?2",new Object[]{newOrder,prize.getId()});
			}else if(oldOrder<newOrder){
				dao.executeUpdate("update Prize t set t.indexorder=t.indexorder-1 where t.indexorder<=?1 and t.id!=?2",new Object[]{newOrder,prize.getId()});
			}
			//dao.update(prize);
			old.update(prize);
		}
	}

	@Override
	@Transactional
	public void deletePrize(String ids) {
		// TODO Auto-generated method stub
		if(ids!=null&&!"".equals(ids)){
			String[] arr = ids.split(",");
			for(int i=0;i<arr.length;i++){
				long id = Long.parseLong(arr[i]);
				Prize prize = this.findById(id);
				int order = prize.getIndexorder();
				dao.deleteById(Prize.class, id);
				dao.executeUpdate("update Prize t set t.indexorder=t.indexorder-1 where t.indexorder>?1",new Object[]{order});
			}
		}
	}

	@Override
	public Prize findById(long id) {
		// TODO Auto-generated method stub
		return dao.findById(Prize.class, id);
	}

	@Override
	public List<Prize> findAll() {
		// TODO Auto-generated method stub
		//return dao.find("from Prize t", null);
		PrizeSerial prizeSerial = prizeSerialService.getActivePrizeSerial();
		if(prizeSerial!=null){
			List<Prize> list = new ArrayList<Prize>();
			list.addAll(prizeSerial.getPrizes());
			Collections.sort(list);
			return list;
		}
		return null;
	}

	@Override
	public JSONObject findAllJson() {
		// TODO Auto-generated method stub
		List<Prize> list  = this.findAll();
		if(list!=null&&list.size()>0){
			Jqgrid_DataJson jsonData = new Jqgrid_DataJson(0,0,0,list);
			JSONObject jsonObject = JsonUtils.objectToJSONObject(jsonData,new String[]{"prizeSerial","prizeUsers"});
			return jsonObject;
		}
		return null;
	}

	@Override
	public String getOrderSelection(String indexorder) {
		// TODO Auto-generated method stub
		long num = this.getPrizeNum();
		StringBuffer sb = new StringBuffer("");
		if(num>0){
			if("".equals(indexorder)){
				num=num+1;
				indexorder=num+"";
			}
			for(int i=1;i<=num;i++){
				String selected = (i+"").equals(indexorder)?"selected":"";
				sb.append("<option value='"+i+"' "+selected+">"+i+"</option>");
			}
		}else{
			sb.append("<option value='1'>1</option>");
		}
		sb.append("");
		return sb.toString();
	}

	@Override
	public long getPrizeNum() {
		// TODO Auto-generated method stub
		PrizeSerial prizeSerial = prizeSerialService.getActivePrizeSerial();
		if(prizeSerial!=null){
			return (prizeSerial.getPrizes()==null?0:prizeSerial.getPrizes().size());
		}
		return 0;
	}

}
