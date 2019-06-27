package com.db.common.vo;

import java.io.Serializable;
//封装和checkbox相关的信息
//使用map封装  不好，  因为值类型不可控，可读性不好
public class CheckBox implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 282985653226440750L;
	private Integer id;
	private String name;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "CheckBox [id=" + id + ", name=" + name + "]";
	}

}
