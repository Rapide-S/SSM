package com.db.sys.service;

import java.util.List;
import java.util.Map;

import com.db.common.vo.Node;
import com.db.sys.entity.SysMenu;
/*
 *  定义菜单业务接口,负责处理菜单模块业务
 */
public interface SysMenuService {
	//查询所有菜单以及上一级菜单信息(只取id,名字)
	List<Map<String,Object>> findObjects();
	
	
	//基于菜单ID删除菜单以及对应的关系数据
	int deleteObject(Integer id);

	
	List<Node> findZtreeMenuNodes();
	
	//将菜单信息传给数据层对象
	/**
	 * 将菜单信息传给数据层对象
	 * @param entity
	 * @return
	 */
	int saveObject(SysMenu entity);
	
	//将菜单信息更新到数据库
	int updateObject(SysMenu entity);
	
}
