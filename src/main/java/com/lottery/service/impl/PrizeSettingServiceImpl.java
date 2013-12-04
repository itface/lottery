package com.lottery.service.impl;

import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lottery.dao.BaseDao;
import com.lottery.domain.Jqgrid_DataJson;
import com.lottery.domain.Prize;
import com.lottery.service.PrizeSettingService;
import com.lottery.util.JsonUtils;
@Service
public class PrizeSettingServiceImpl implements PrizeSettingService{

	@Autowired
	private BaseDao<Prize> dao;
	
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
		int order  = prize.getIndexorder();
		dao.executeUpdate("update Prize t set t.indexorder=t.indexorder+1 where t.indexorder>=?1",new Object[]{order});
		dao.persist(prize);
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
			dao.update(prize);
		}
	}

	@Override
	@Transactional
	public void deletePrize(long id) {
		// TODO Auto-generated method stub
		Prize prize = this.findById(id);
		int order = prize.getIndexorder();
		dao.deleteById(Prize.class, id);
		dao.executeUpdate("update Prize t set t.indexorder=t.indexorder-1 where t.indexorder>?1",new Object[]{order});
	}

	@Override
	public Prize findById(long id) {
		// TODO Auto-generated method stub
		return dao.findById(Prize.class, id);
	}

	@Override
	public List<Prize> findAll() {
		// TODO Auto-generated method stub
		return dao.find("from Prize t", null);
	}

	@Override
	public JSONObject findAllJson() {
		// TODO Auto-generated method stub
		List<Prize> list  = this.findAll();
		if(list!=null&&list.size()>0){
			Jqgrid_DataJson jsonData = new Jqgrid_DataJson(0,0,0,list);
			JSONObject jsonObject = JsonUtils.objectToJSONObject(jsonData,null);
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
		return dao.findTotalCount("select count(t.id) as num from Prize t", null);
	}

}
