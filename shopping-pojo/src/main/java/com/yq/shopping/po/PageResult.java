package com.yq.shopping.po;

import java.io.Serializable;

import java.util.List;

import com.github.pagehelper.PageInfo;

public class PageResult implements Serializable{

	private List rows;//返回的数据
	
	private Long total;//返回的总记录数

	public PageResult() {
		super();
	}

	public PageResult(List rows, Long total) {
		super();
		this.rows = rows;
		this.total = total;
	}

	public List getRows() {
		return rows;
	}

	public Long getTotal() {
		return total;
	}

	public void setRows(List rows) {
		this.rows = rows;
	}

	public void setTotal(Long total) {
		this.total = total;
	}


	
	
}
