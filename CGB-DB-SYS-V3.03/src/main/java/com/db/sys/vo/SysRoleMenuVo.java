package com.db.sys.vo;

import java.io.Serializable;
import java.util.List;

/**
 * VO：通过此对象封装 角色以及 角色对应的菜单id
 * @author ta
 */

public class SysRoleMenuVo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3037297553356382499L;
	/**角色id*/
	private Integer id;
	/**角色名称*/
	private String name;
	/**角色备注*/
	private String note;
	/**角色对应的菜单id*/
	private List<Integer> menuIds;
	
	
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
	public List<Integer> getMenuIds() {
		return menuIds;
	}
	public void setMenuIds(List<Integer> menuIds) {
		this.menuIds = menuIds;
	}
	@Override
	public String toString() {
		return "SysRoleMenuVo [id=" + id + ", name=" + name + ", note=" + note + ", menuIds=" + menuIds + "]";
	}


}
