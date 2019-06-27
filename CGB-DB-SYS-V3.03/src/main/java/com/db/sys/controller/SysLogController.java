package com.db.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.db.common.vo.JsonResult;
import com.db.common.vo.PageObject;
import com.db.sys.entity.SysLog;
import com.db.sys.service.SysLogService;

@Controller
@RequestMapping("/log/") // 映射路径，以后访问日志页面，前缀必须是这个log,定义对外的url
public class SysLogController {

	@Autowired // 不用new SysLogServiceimpl 提高灵活性
	private SysLogService sysLogService;

	@RequestMapping("doLogListUI")
	public String doLogListUI() {
		return "sys/log_list";
	}

	@GetMapping("doFindPageObjects")//当提交用post时，这里也用post
	@ResponseBody
	public JsonResult doFindPageObjects(Integer pageCurrent,String username) {
		
		PageObject<SysLog> findPageObjects = sysLogService.findPageObjects(pageCurrent,username);
		return new JsonResult(findPageObjects);
	}

	@RequestMapping("doDeleteObjects")
	@ResponseBody
	public JsonResult doDeleteObjects(Integer... ids){
		  sysLogService.deleteObjects(ids);
		  return new JsonResult("delete ok");
	  }
	//测试：http://localhost:8080/CGB-DB-SYS-V3.01/log/doDeleteObjects.do?ids=17
	
}
