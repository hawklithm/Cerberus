package com.multiagent.hawklithm.sephiroth.DO;

import java.util.Date;

public class TempMainPackageOrderDO {
	private Integer orderId;
	private Integer userId;
	private Date orderTime;
	private String orderStatus;
	private Date expectReceiveTime;
	private Integer level;
	private String orderNote;
	public Date getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public Date getExpectReceiveTime() {
		return expectReceiveTime;
	}
	public void setExpectReceiveTime(Date expectReceiveTime) {
		this.expectReceiveTime = expectReceiveTime;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public String getOrderNote() {
		return orderNote;
	}
	public void setOrderNote(String orderNote) {
		this.orderNote = orderNote;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
}
