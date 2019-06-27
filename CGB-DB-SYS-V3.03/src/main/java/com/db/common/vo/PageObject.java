package com.db.common.vo;

import java.io.Serializable;
import java.util.List;
/**
 * 业务层用于封装分页数据 的一个值对象(value Object)
 * 通用的，一页显示几行，一共的行数，一页的数据list,当前页数，
 * @author 全日制
 *
 * @param <T>   泛型
 */
public class PageObject<T> implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -973103533350138669L;
	//当前页页码
	private Integer pageCurrent;
    /**页面大小*/
    private Integer pageSize;
    /**总行数(通过查询获得)*/
    private Integer rowCount=0;
    /**总页数(通过计算获得)*/
    private Integer pageCount=0;
    /**当前页要呈现的记录*/
    private List<T> records;
    
    
    
	public Integer getPageCurrent() {
		return pageCurrent;
	}
	public void setPageCurrent(Integer pageCurrent) {
		this.pageCurrent = pageCurrent;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getRowCount() {
		return rowCount;
	}
	public void setRowCount(Integer rowCount) {
		this.rowCount = rowCount;
	}
	public Integer getPageCount() {
		return pageCount;
	}
	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}
	public List<T> getRecords() {
		return records;
	}
	public void setRecords(List<T> records) {
		this.records = records;
	}
	@Override
	public String toString() {
		return "PageObject [pageCurrent=" + pageCurrent + ", pageSize=" + pageSize + ", rowCount=" + rowCount
				+ ", pageCount=" + pageCount + ", records=" + records + "]";
	}


}
