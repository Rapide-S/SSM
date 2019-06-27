package com.db.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.db.common.vo.CheckBox;
import com.db.sys.entity.SysUser;
import com.db.sys.vo.SysUserDeptVo;

public interface SysUserDao {
	// 获取表中用户信息.
	List<SysUserDeptVo> findPageObjects(
			@Param("username") String username, 
			@Param("startIndex") Integer startIndex,
			@Param("pageSize") Integer pageSize);

	//根据条件查詢总记录数
	int getRowCount(@Param("username") String username);

	
	//定义禁用启用方法
	int valiById(
			@Param("id")Integer is,
			@Param("valid")Integer valid,
			@Param("modifiedUser")String modifiedUser);
	//负责将用户信息写入到数据库
	int insertObject(SysUser entity);
	
	//基于用户id查询用户以及部门的方法)
	SysUserDeptVo findObjectById(Integer id);
	
	int updateObject(SysUser entity);
	
	
	/**
	 * (根据用户名查询用户对象的方法定义
	 * @param username
	 * @return
	 */
	SysUser findUserByUserName(String username);
	
	/**
	 * 基于用户id修改用户的密码
	 * @param password 新的密码
	 * @param salt 密码加密时使用的盐值
	 * @param id 用户id
	 * @return
	 */
	int updatePassword(
			@Param("password")String password,
			@Param("salt")String salt,
			@Param("id")Integer id);

	
	

}
