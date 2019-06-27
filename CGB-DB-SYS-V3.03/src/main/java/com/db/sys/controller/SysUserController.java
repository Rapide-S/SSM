package com.db.sys.controller;

import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.db.common.vo.JsonResult;
import com.db.common.vo.PageObject;
import com.db.sys.entity.SysUser;
import com.db.sys.service.SysRoleService;
import com.db.sys.service.SysUserService;
import com.db.sys.vo.SysUserDeptVo;

@Controller
@RequestMapping("/user/")
public class SysUserController {
	@Autowired
	SysUserService sysUserService;
	
	@Autowired
	SysRoleService sysRoleService;
	
	@RequestMapping("doUserListUI")
	public String doUserListUI() {
		return "sys/user_list";
	}
	
	@RequestMapping("doPwdEditUI")
	public String doPwdEditUI() {
		return "sys/pwd_edit";
	}
	
	
	@RequestMapping("doFindPageObjects")
	@ResponseBody
	public JsonResult doFindPageObjects(String username,Integer pageCurrent) {
		PageObject<SysUserDeptVo> pageObjects = sysUserService.findPageObjects(username, pageCurrent);
		return new JsonResult(pageObjects);
	}
	
	//执行禁用或者开启操作
	@RequestMapping("doValidById")
	@ResponseBody//传入用户id和当前权限值
	public JsonResult doValidById(Integer id,Integer valid) {
		//第三个参数，是谁修改了
		sysUserService.valiById(id, valid, "admin");
		return new JsonResult("update OK");
	}
	
	//用户添加
	@RequestMapping("doUserEditUI")
	public String doUserEditUI() {
		return "sys/user_edit";
	}
	
	@RequestMapping("doSaveObject")
	@ResponseBody
	public JsonResult doSaveObject(SysUser entity,Integer[] roleIds){
		sysUserService.saveObject(entity,roleIds);
		return new JsonResult("save ok");
	}

	@RequestMapping("doFindObjectById")
	@ResponseBody
	public JsonResult doFindObjectById(Integer id) {
		Map<String, Object> map = sysUserService.findObjectById(id);
		return new JsonResult(map);
	}
	
	@RequestMapping("doUpdateObject")
	@ResponseBody
	public JsonResult doUpdateObject(
	    SysUser entity,Integer[] roleIds){
		sysUserService.updateObject(entity,roleIds);
		return new JsonResult("update ok");
	}
	@RequestMapping("doLogin")
	@ResponseBody
	public JsonResult doLogin(boolean isRememberMe,String username,String password) {
		//1.获取subject对象
		Subject subject = SecurityUtils.getSubject();
		// 2.通过Subject提交用户信息,交给shiro框架进行认证操作
		// 2.1对用户进行封装
		UsernamePasswordToken token = new UsernamePasswordToken(username,password);
		if (isRememberMe) {
			token.setRememberMe(true);
		}
		 //2.2对用户信息进行身份认证
		subject.login(token);
		 //分析:
		 //1)token会传给shiro的SecurityManager
		 //2)SecurityManager将token传递给认证管理器
		 //3)认证管理器会将token传递给realm

		return new JsonResult("login ok");
	}
	
	
	//密码修改
	@RequestMapping("doUpdatePassword")
	 @ResponseBody
	 public JsonResult doUpdatePassword(
			 String pwd,//原密码
			 String newPwd,//新密码
			 String cfgPwd) {//确认密码
		 sysUserService.updatePassword(pwd, newPwd, cfgPwd);
		 return new JsonResult("update ok");
	 }

	
	
	
	
	
}
