package com.multiagent.hawklithm.transport.DO;

import java.util.Date;
/**
id						int	
gmt_create		date				表项创建日期
gmt_modified	date				表项修改日期
parent_id			int					父运输单ID
Vehicle_id			int					运输工具ID
Sub_order_id		int					对应订单子订单，或返件子订单
 * @author hawklithm
 * 2013-12-18下午4:51:47
 */
public class SqlTransportSubOrderDO {
	private Integer id;
	private Date gmtCreate;
	private 	Date gmtModified;
	private Integer parentId;
	private Integer vehicleId;
	private Integer subOrderId;
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
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public Integer getVehicleId() {
		return vehicleId;
	}
	public void setVehicleId(Integer vehicleId) {
		this.vehicleId = vehicleId;
	}
	public Integer getSubOrderId() {
		return subOrderId;
	}
	public void setSubOrderId(Integer subOrderId) {
		this.subOrderId = subOrderId;
	}
}
