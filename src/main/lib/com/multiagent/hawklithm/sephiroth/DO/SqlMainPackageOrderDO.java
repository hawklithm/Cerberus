package com.multiagent.hawklithm.sephiroth.DO;

import java.util.Date;
/**
 * 手术包主订单 tb_order
Order_id						int	
gmt_create					date				表项创建日期
gmt_modified				date				表项修改日期
User_id							int					下单用户ID
order_time						Date				下单时间
order_status					varchar			订单状态,对应配置文件中参数
expect_receive_time		date				期望收货时间
level								int					订单优先级
order_note					varchar			备注
 * @author hawklithm
 *
 */
public class SqlMainPackageOrderDO {
	private Integer orderId;
	private Date gmtCreate;
	private Date gmtModified;
	private Integer UserId;
	private Date orderTime;
	private String orderStatus;
	private Date expectReceiveTime;
	private Integer level;
	private String orderNote;
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
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
	public Integer getUserId() {
		return UserId;
	}
	public void setUserId(Integer userId) {
		UserId = userId;
	}
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
}
