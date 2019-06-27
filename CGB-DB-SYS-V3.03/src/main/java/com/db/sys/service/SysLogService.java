package com.db.sys.service;
/**
 * 日志模块业务接口，负责日志业务的规范定义
 * @author 全日制
 *
 */

import com.db.common.vo.PageObject;
import com.db.sys.entity.SysLog;

public interface SysLogService {
	/**
	 * 通过此方法实现分页查询操作
	 * 
	 * @param name
	 *            基于条件查询时的参数名
	 * @param pageCurrent
	 *            当前的页码值
	 * @return 当前页记录+分页信息
	 */

	PageObject<SysLog> findPageObjects(Integer pageCurrent, String username);
	
	
	
	int deleteObjects(Integer... ids); 

}
