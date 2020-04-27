package com.yq.shopping.po;

import java.io.Serializable;
/**
 * 响应回前端的信息的封装类
 * @author HaiLin
 *
 */
public class Result implements Serializable{

	private boolean success;
	private String message;
	private Integer num;
	
	
	public Result() {
		super();
	}
	public Result(boolean success, String message) {
		super();
		this.success = success;
		this.message = message;
	}
	
	public Result(boolean success, String message, Integer num) {
		super();
		this.success = success;
		this.message = message;
		this.num = num;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public boolean isSuccess() {
		return success;
	}
	public String getMessage() {
		return message;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
