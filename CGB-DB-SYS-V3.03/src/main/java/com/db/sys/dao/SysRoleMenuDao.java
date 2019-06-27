package com.db.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

/*
 * 基于此接口访问角色，菜单关系表(sys_role_menu)数据
 */
public interface SysRoleMenuDao {
	// 基于菜单ID删除关系表数据
	// 参数：菜单ID
	// 返回值：删除的行数
	int deleteObjectsByMenuId(Integer menuId);

	// 3） 根据角色id删除角色与菜单的关系数据
	int deleteObjectsByRoleId(Integer roleId);

	// 将角色和菜单数据写入数据库
	int insertObjects(@Param("roleId") Integer roleId, @Param("menuIds") Integer[] menuIds);

	int deleteObjectByRoleId(Integer roleId);

	
	/**
	 * 基于角色id查找菜单id信息)
	 * @param roleIds
	 * @return
	 */
	List<Integer> findMenuIdsByRoleIds(@Param("roleIds") Integer[] roleIds);

}
