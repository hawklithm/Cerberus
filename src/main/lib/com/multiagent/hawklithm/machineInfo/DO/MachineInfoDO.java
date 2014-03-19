package com.multiagent.hawklithm.machineInfo.DO;

import java.util.Date;

/**
 * tb_machine_manage设备信息管理表
id							int	
gmt_create			date				表项创建日期
gmt_modified		date				表项修改日期
gmt_buy				date				购买日期
gmt_last_repair		date				上次维修时间
machine_number	char				机器编号
equipment_id			int					机器RFID
manufacturer			varchar			生产厂商
detail					varchar 			设备详细参数
 * @author hawklithm
 * 2013-12-25下午1:07:17
 */
public class MachineInfoDO {
	private Integer id;
	private Date gmtCreate;
	private Date gmtModified;
	private Date gmtBuy;
	private Date gmtLastRepair;
	private String machineNumber;
	private Integer equipmentId;
	private String manufacturer;
	private String detail;
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
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
	public Date getGmtBuy() {
		return gmtBuy;
	}
	public void setGmtBuy(Date gmtBuy) {
		this.gmtBuy = gmtBuy;
	}
	public Date getGmtLastRepair() {
		return gmtLastRepair;
	}
	public void setGmtLastRepair(Date gmtLastRepair) {
		this.gmtLastRepair = gmtLastRepair;
	}
	public String getMachineNumber() {
		return machineNumber;
	}
	public void setMachineNumber(String machineNumber) {
		this.machineNumber = machineNumber;
	}
	public Integer getEquipmentId() {
		return equipmentId;
	}
	public void setEquipmentId(Integer equipmentId) {
		this.equipmentId = equipmentId;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
}
