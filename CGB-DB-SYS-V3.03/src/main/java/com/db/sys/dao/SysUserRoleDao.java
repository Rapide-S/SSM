package com.db.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface SysUserRoleDao {
	
	//	根据角色id删除角色与用户的关系数据(
	
	  int deleteObjectsByRoleId(Integer roleId);
	  
	  
	  //负责将用户与角色的关系数据写入到数据库
	  int insertObjects(
				@Param("userId")Integer userId,
				@Param("roleIds")Integer[]roleIds);

	  //根据用户id查询角色ID
	  List<Integer> findRoleIdsByUserId(Integer id);
	  
	  //更新时，基于用户ID删除用户角色关系数据
	  int deleteObjectsByUserId(Integer userId);
	  
	  
}
