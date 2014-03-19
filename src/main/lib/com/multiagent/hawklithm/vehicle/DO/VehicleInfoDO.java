package com.multiagent.hawklithm.vehicle.DO;

import java.util.Date;

/**
vehicle_id					int
gmt_create				date				表项创建时间
gmt_modified			date				表项修改时间
vehicle_type				char				运输工具类型，对应配置文件参数
vehicle_level				int					车辆优先等级，用于资源调度
vehicle_capacity			float				车辆容量
 * @author hawklithm
 * 2013-12-25下午3:59:38
 */
public class VehicleInfoDO {
	private Integer vehicleId;
	private Date gmtCreate;
	private Date gmtModified;
	private String vehicleType;
	private Integer vehicleLevel;
	private double vehicleCapacity;

	public Integer getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(Integer vehicleId) {
		this.vehicleId = vehicleId;
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

	public String getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	public Integer getVehicleLevel() {
		return vehicleLevel;
	}

	public void setVehicleLevel(Integer vehicleLevel) {
		this.vehicleLevel = vehicleLevel;
	}

	public double getVehicleCapacity() {
		return vehicleCapacity;
	}

	public void setVehicleCapacity(double vehicleCapacity) {
		this.vehicleCapacity = vehicleCapacity;
	}
}
