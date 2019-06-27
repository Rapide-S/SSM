package com.db.sys.service;


import java.util.Map;

import com.db.common.vo.PageObject;
import com.db.sys.entity.SysUser;
import com.db.sys.vo.SysUserDeptVo;

public interface SysUserService {

	PageObject<SysUserDeptVo> findPageObjects(String username, Integer pageCurrent);

	// 控制开启和禁用
	int valiById(Integer id, Integer valid, String modifiedUser);

	// 1) 接收客户端数据,并进行合法验证
	// 2) 将数据保存到数据库(用户信息,用户角色关系信息)

	int saveObject(SysUser entity, Integer[] roleIds);
	
	Map<String, Object> findObjectById(Integer userid);
	//更新用户信息
	 int updateObject(SysUser entity,Integer[] roleIds);
	 
	 
	 /**
		 * 修改密码
		 * @param password 原密码
		 * @param newPassword 新密码(还没加密)
		 * @param cfgPassword 确认密码
		 * @return
		 */
		int updatePassword(String password,
				           String newPassword,
				           String cfgPassword);

	 
	 
	 
}
