package com.lottery.domain;

import java.io.Serializable;
import java.util.List;

/**
 * jqgrid加载的数据格式
 * @author Administrator
 *
 */
public class Jqgrid_DataJson implements Serializable{

	private static final long serialVersionUID = 8961653795826721665L;

	private long total;
	private int page;
	private long records;
	private long rowTotal;
	private List rows;
	public Jqgrid_DataJson(int page,int rowNumPerPage,long totalRows,List list){
		if(page>0&&rowNumPerPage>0){
			this.total=(totalRows%rowNumPerPage==0?totalRows/rowNumPerPage:totalRows/rowNumPerPage+1);
			this.page=page;
			this.records=totalRows;
			//this.rowTotal=list.size();
			this.rows=list;
		}else{
			this.total=1;
			this.page=1;
			this.records=list.size();
			this.rows=list;
		}
	}
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public long getRecords() {
		return records;
	}
	public void setRecords(long records) {
		this.records = records;
	}
	public long getRowTotal() {
		return rowTotal;
	}
	public void setRowTotal(long rowTotal) {
		this.rowTotal = rowTotal;
	}
	public List getRows() {
		return rows;
	}
	public void setRows(List rows) {
		this.rows = rows;
	}
	
	
	
}
