package com.multiagent.hawklithm.transport.DO;

import java.util.Date;
/**
 * 
Id							int
gmt_create			date				表项创建日期
gmt_modified		date				表项修改日期
order_id				int					订单ID
Start_time				date				出发时间
Finish_time				date				到达时间
Start_address			varchar			发货地点
End_address			varchar			收货地点
 * @author hawklithm
 * 2013-12-18下午4:49:44
 */
public class SqlTransportMainOrderDO {
	private Integer id;
	private Date gmtCreate;
	private Date gmtModified;
	private Integer orderId;
	private Date startTime;
	private Date finishTime;
	private String startAddress;
	private String endAddress;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getGmtCreate() {
		return gmtCreate;
	}
	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}
	public Date getGmtModified() {
		return gmtModified;
	}
	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getFinishTime() {
		return finishTime;
	}
	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}
	public String getStartAddress() {
		return startAddress;
	}
	public void setStartAddress(String startAddress) {
		this.startAddress = startAddress;
	}
	public String getEndAddress() {
		return endAddress;
	}
	public void setEndAddress(String endAddress) {
		this.endAddress = endAddress;
	}
}
