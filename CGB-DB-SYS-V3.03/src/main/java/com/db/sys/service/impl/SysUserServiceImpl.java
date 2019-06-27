package com.db.sys.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.db.common.annotation.RequiredLog;
import com.db.common.exception.ServiceException;
import com.db.common.util.PageUtil;
import com.db.common.vo.PageObject;
import com.db.sys.dao.SysRoleDao;
import com.db.sys.dao.SysUserDao;
import com.db.sys.dao.SysUserRoleDao;
import com.db.sys.entity.SysUser;
import com.db.sys.service.SysUserService;
import com.db.sys.vo.SysUserDeptVo;

@Service
public class SysUserServiceImpl implements SysUserService{
	@Autowired
	SysUserDao sysUserDao;
	
	@Autowired
	SysRoleDao sysRoleDao;
	
	@Autowired
	SysUserRoleDao sysUserRoleDao;
	
	@RequiredLog("查看用户")
	@RequiresPermissions("sys:user:view")
	@Override
	public PageObject<SysUserDeptVo> findPageObjects(String username, Integer pageCurrent) {
		if (pageCurrent==null||pageCurrent<=0) {
			throw new ServiceException("参数不合法");
		}
		int rows = sysUserDao.getRowCount(username);
		if (rows==0) {
			throw new ServiceException("记录不存在");
		}
		int pageSize=3;
		int startIndex = (pageCurrent-1)*pageSize;
		List<SysUserDeptVo> records = sysUserDao.findPageObjects(username, startIndex, pageSize);
		//得到当前页数的所有数据集合，然后用下面的工具把数据封装到一页的对象中。
		return PageUtil.newInstance(pageCurrent, rows, pageSize, records);
	}
	//访问这个方法 获取  注解，  获取这里面的字符串值
	@RequiredLog("禁用启用")
	@RequiresPermissions("sys:user:valid")//访问这个方法必须指定权限
	@Override
	public int valiById(Integer id, Integer valid, String modifiedUser) {
		if (id==null||id<=0) {
			throw new ServiceException("参数不合法，id=="+id);
		}
		if (valid!=1&&valid!=0) {
			throw new ServiceException("参数不合法，valie="+valid);
		}
		if (StringUtils.isEmpty(modifiedUser)) {
			throw new ServiceException("修改用户不能为空");
		}
		//执行禁用或开启操作
		int rows=0;
		try {
			rows = sysUserDao.valiById(id, valid, modifiedUser);
			
		} catch (Throwable e) {
			e.printStackTrace();
			throw new ServiceException("系统在维护");
		}
		if (rows==0) {
			throw new ServiceException("此记录可能已经不存在");
		}
		return rows;
	}

	@RequiredLog("新增用户")
	@RequiresPermissions("sys:user:add")
	@Override
	public int saveObject(SysUser entity, Integer[] roleIds) {
		if (entity==null) {
			throw new ServiceException("保存对象不能为空");
		}
		if(StringUtils.isEmpty(entity.getUsername()))
		    throw new ServiceException("用户名不能为空");
			if(StringUtils.isEmpty(entity.getPassword()))
			throw new ServiceException("密码不能为空");
	        if(roleIds==null || roleIds.length==0)
			throw new ServiceException("至少要为用户分配角色");
	        
	      //2.将数据写入数据库
			String salt=UUID.randomUUID().toString();
			entity.setSalt(salt);
			//加密(先了解,讲shiro时再说)
			SimpleHash sHash=new SimpleHash("MD5",entity.getPassword(), salt);
			entity.setPassword(sHash.toHex());

			int rows=sysUserDao.insertObject(entity);
			
			 sysUserRoleDao.insertObjects(
						entity.getId(),
						roleIds);//"1,2,3,4";

		return rows;
	}


	@Override
	public Map<String, Object> findObjectById(Integer id) {
		if (id==null||id<=0) {
			throw new IllegalArgumentException("请选择一个用户");
		}
		SysUserDeptVo user = sysUserDao.findObjectById(id);
		List<Integer> roleIds = sysUserRoleDao.findRoleIdsByUserId(id);
		HashMap<String, Object> map = new HashMap<>();
		map.put("user", user);
		map.put("roleIds",roleIds);
		
		return map;
	}
	//说明这个方法进行事务控制,加括号内设置事务隔离级别，也可以再类上加，类上定义共性，方法上定义特性
	@Transactional
	@Override
	public int updateObject(SysUser entity, Integer[] roleIds) {
		//1.参数有效性验证
				if(entity==null)
					throw new IllegalArgumentException("保存对象不能为空");
				if(StringUtils.isEmpty(entity.getUsername()))
					throw new IllegalArgumentException("用户名不能为空");
				if(roleIds==null||roleIds.length==0)
					throw new IllegalArgumentException("必须为其指定角色");
				//其它验证自己实现，例如用户名已经存在，密码长度，...
				//2.更新用户自身信息
				int rows=sysUserDao.updateObject(entity);
				//3.保存用户与角色关系数据
				sysUserRoleDao.deleteObjectsByUserId(entity.getId());
				sysUserRoleDao.insertObjects(entity.getId(),roleIds);
				//4.返回结果
				return rows;

	}
	@Override
	public int updatePassword(String password, String newPassword, String cfgPassword) {
		//1.判定新密码与密码确认是否相同
				if(StringUtils.isEmpty(newPassword))
				throw new IllegalArgumentException("新密码不能为空");
				if(StringUtils.isEmpty(cfgPassword))
				throw new IllegalArgumentException("确认密码不能为空");
				if(!newPassword.equals(cfgPassword))
				throw new IllegalArgumentException("两次输入的密码不相等");
				//2.判定原密码是否正确
				if(StringUtils.isEmpty(password))
				throw new IllegalArgumentException("原密码不能为空");
				//获取登陆用户
				SysUser user=(SysUser)SecurityUtils.getSubject().getPrincipal();
				SimpleHash sh=new SimpleHash("MD5",
				password, user.getSalt(), 1);
				if(!user.getPassword().equals(sh.toHex()))
				throw new IllegalArgumentException("原密码不正确");
				//3.对新密码进行加密
				String salt=UUID.randomUUID().toString();
				sh=new SimpleHash("MD5",newPassword,salt, 1);
				//4.将新密码加密以后的结果更新到数据库
				int rows=sysUserDao.updatePassword(sh.toHex(), salt,user.getId());
				if(rows==0)
				throw new ServiceException("修改失败");
				return rows;

	}
	




}
