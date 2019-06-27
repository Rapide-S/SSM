package com.db.common.vo;

import java.io.Serializable;

public class Node implements Serializable{
	
	/**
	 * 基于此对象封装树节点信息
	 */
	private static final long serialVersionUID = -4794314574141335353L;
	private Integer id;
	private String name;
	private Integer parentId;
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
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}


}
