package com.db.sys.entity;


public class SysRole extends BaseEntity{
	/**
	 * 封装从数据库查询的角色数据
	 */
	private static final long serialVersionUID = 8423046850390468734L;
	private Integer id;
	private String name;
	private String note;
	
	
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
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	@Override
	public String toString() {
		return "SysRole [id=" + id + ", name=" + name + ", note=" + note + "]";
	}
	

}
