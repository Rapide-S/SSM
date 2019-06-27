package com.db.sys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.db.common.exception.ServiceException;
import com.db.common.util.PageUtil;
import com.db.common.vo.PageObject;
import com.db.sys.dao.SysLogDao;
import com.db.sys.entity.SysLog;
import com.db.sys.service.SysLogService;
@Service
public class SysLogServiceImpl implements SysLogService{

	@Autowired   //自动注册这个bean 
	private SysLogDao sysLogDao;
	
	
	@Override
	public PageObject<SysLog> findPageObjects(Integer pageCurrent, String username) {
		//1.参数的合法性验证
		if (pageCurrent==null||pageCurrent<1) {
			throw new IllegalArgumentException("页码值无效");
		}
		//2.基于用户名统计总记录并且进行验证
		int rowCount = sysLogDao.getRowCount(username);
		if (rowCount==0) {
			throw new ServiceException("没有找到对应的记录");
		}
		
		//3.基于查询条件查询当前页的日志信息
			//3.1定义页面大小
		int pageSize =3;
			//3.2计算起始位置
		int startIndex = (pageCurrent-1)*pageSize;
			//3.3基于起始位置执行当前页数据的查询
		List<SysLog> records = sysLogDao.findPageObjects(username, startIndex, pageSize);
		
		//4.对查询结果进行封装并返回
		
		
		
//		PageObject<SysLog> pageObject = new PageObject<SysLog>();
//		
//		pageObject.setPageCurrent(pageCurrent);
//		pageObject.setPageSize(pageSize);
//		pageObject.setRowCount(rowCount);
//		pageObject.setRecords(records);
//		pageObject.setPageCount((rowCount - 1) / pageSize + 1);//总页数
		return PageUtil.newInstance(pageCurrent, rowCount, pageSize, records);
	}


	@Override
	public int deleteObjects(Integer... ids) {
		//1.判定参数合法性
		if (ids==null||ids.length==0) {
			throw new IllegalArgumentException("请先选择");
		}
		int rows =0;
		//2.执行删除操作
		try {
		 rows = sysLogDao.deleteObjects(ids);
		}catch (Throwable e) {
			e.printStackTrace();
			//发出报警信息(例如给运维人员发短信) 做监控
			throw new ServiceException("系统故障，正在恢复中...");
		}

		
		// 4.对结果进行验证
		if (rows == 0)
			throw new ServiceException("记录可能已经不存在");
		// 5.返回结果

		return rows;
	}

}
