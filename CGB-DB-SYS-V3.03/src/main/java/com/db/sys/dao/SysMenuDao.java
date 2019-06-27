package com.db.sys.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.db.common.vo.Node;
import com.db.sys.entity.SysMenu;

/*
 * 1)	定义菜单持久层对象,处理数据访问操作
 */
public interface SysMenuDao {
	// 2) 定义菜单查询方法,查询所有菜单以及上一级菜单信息(只取id,名字)
	List<Map<String, Object>> findObjects();

	/**
	 * 根据菜单id统计子菜单的个数
	 * 
	 * @param id
	 * @return 子菜单个数
	 */

	int getChildCount(Integer id);

	/**
	 * 根据id 删除菜单
	 * 
	 * @param id
	 * @return 删除行数
	 */
	int deleteObject(Integer id);

	// 基于请求获取数据库对应的菜单表中的所有菜单信息(id,name,parentId)
	List<Node> findZtreeMenuNodes();
	//添加插入数据的方法，用于将菜单信息写入到数据库
	int insertObject(SysMenu entity);

	// 修改数据
	int updateObject(SysMenu entity);
	
	/**
	 * 基于菜单id查找权限标识信息
	 * @param menuIds
	 * @return
	 */
	List<String> findPermissions(@Param("menuIds") Integer[] menuIds);
	

}
