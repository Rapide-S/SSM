package com.db.sys.dao.impl;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.db.sys.dao.SysLogDao;
@Repository  //用于标注数据访问组件，即DAO组件
public abstract class DefaultSysLogDao implements SysLogDao{
	@Autowired//自动注册这个bean 
	private SqlSessionFactory sqlsessionfactory;
	@Override
	public int deleteObjects(Integer... ids) {
		//1。获取sqlsession对象   不可以共享  ，用完 必须释放
		SqlSession session = sqlsessionfactory.openSession();
		
		//2.执行删除对象
		SysLogDao mapper = session.getMapper(SysLogDao.class);
		int rows = mapper.deleteObjects(ids);
		session.commit();
		//3.释放资源
		session.close();
		
		
		return rows;
	}

}
