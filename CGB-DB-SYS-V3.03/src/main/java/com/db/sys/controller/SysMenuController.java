package com.db.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.db.common.vo.JsonResult;
import com.db.sys.entity.SysMenu;
import com.db.sys.service.SysMenuService;

@Controller
@RequestMapping("/menu/")
public class SysMenuController {
	@Autowired
	private SysMenuService sysMenuService;
	//返回菜单列表页面
	@RequestMapping("doMenuListUI")
	public String doMenuListUI() {
		return "sys/menu_list";
	}
	
	
	//查询所有菜单以及上一级菜单信息(
	@RequestMapping("doFindObjects")
	@ResponseBody
	public JsonResult  doFindObjects() {
		 return new JsonResult(sysMenuService.findObjects());
	}
	
	
	 
	 @RequestMapping("doDeleteObject")
	 @ResponseBody
	 public JsonResult doDeleteObject(Integer id){
		 sysMenuService.deleteObject(id);//如果失败的话，这里就抛出异常了，成功才会向下执行
		 return new JsonResult("delete OK");
	 }
	 //返回菜单编辑页面
	 @RequestMapping("doMenuEditUI")
	 public String doMenuEditUI(){
		 return "sys/menu_edit";
	 }
	 //基于客户端请求,访问业务层对象方法,获取菜单节点对象,并封装返回
	 @RequestMapping("doFindZtreeMenuNodes")
	 @ResponseBody
	 public JsonResult doFindZtreeMenuNodes(){
		 return new JsonResult(sysMenuService.findZtreeMenuNodes());
	 }
	 /*
	  * 1）	获取客户端请求数据
		2）	调用业务层方法将数据写入数据库
		3）	封装结果并返回

	  */
	@RequestMapping("doSaveObject")
	@ResponseBody
	public JsonResult doSaveObject(SysMenu entity) {
		sysMenuService.saveObject(entity);
		return new JsonResult("save ok");
	}
	//更新菜单数据
	@RequestMapping("doUpdateObject")
	@ResponseBody
	public JsonResult doUpdateObject(SysMenu entity){
	    sysMenuService.updateObject(entity);
	    return new JsonResult("update ok");
	}

	

}
