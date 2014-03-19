package com.multiagent.hawklithm.leon.DO;

import java.util.Date;
/**
 * 设备处理流水表
 * tb_equipment_history:
 *id					int
 *time				date
 *gmt_create		date
 *gmt_modified	date
 *package_id		int
 *Item_id			int
 *reader_id		int
 *camera_id		int
 *equipment_id	int
 *staff_id			int
 * @author hawklithm
 * 
 */
public class SqlReaderAtEquipmentDO {
	private Integer id;
	private Date time;
	private Date gmtCreate;
	private Date gmtModified;
	private Integer equipmentId;
	private Integer packageId;
	private Integer itemId;
	private Integer readerId;
	private Integer cameraId;
	private Integer staffId;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getEquipmentId() {
		return equipmentId;
	}
	public void setEquipmentId(Integer equipmentId) {
		this.equipmentId = equipmentId;
	}
	public Integer getPackageId() {
		return packageId;
	}
	public void setPackageId(Integer packageId) {
		this.packageId = packageId;
	}
	public Integer getItemId() {
		return itemId;
	}
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	public Integer getReaderId() {
		return readerId;
	}
	public void setReaderId(Integer readerId) {
		this.readerId = readerId;
	}
	public Integer getCameraId() {
		return cameraId;
	}
	public void setCameraId(Integer cameraId) {
		this.cameraId = cameraId;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
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
	public Integer getStaffId() {
		return staffId;
	}
	public void setStaffId(Integer staffId) {
		this.staffId = staffId;
	}
}
