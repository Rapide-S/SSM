package com.db.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.db.sys.vo.SysRoleMenuVo;
import com.db.common.vo.CheckBox;
import com.db.sys.entity.SysRole;

public interface SysRoleDao {
	 /**
     * 分页查询角色信息
     * @param startIndex 上一页的结束位置
     * @param pageSize 每页要查询的记录数
     * @return
     */
	List<SysRole> findPageObjects(
             @Param("name")String name,
			@Param("startIndex")Integer startIndex,
			@Param("pageSize")Integer pageSize);
	/**
	 * 查询记录总数
	 * @return
	 */
	int getRowCount(@Param("name") String name);

	// 根据角色id删除角色自身信息
	int deleteObject(Integer id);

	// 将角色信息写入数据库
	int insertObject(SysRole sysRole);

	// 基于基于角色id查询角色信息,以及对应菜单的方法
	SysRoleMenuVo findObjectById(Integer id);

	int updateObject(SysRole entity);

	// 一个查询角色ID,角色名的方法
	List<CheckBox> findObjects();

	/**
	 * 按照名字去统计相关记录
	 * @param name
	 * @return
	 */
	int getRowCountByName(String name);

}
