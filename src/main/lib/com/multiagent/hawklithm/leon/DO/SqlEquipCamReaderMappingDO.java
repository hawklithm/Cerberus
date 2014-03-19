package com.multiagent.hawklithm.leon.DO;

import java.util.Date;

/**
 * 设备、摄像头、读卡器映射表 tb_mapping
 * @author hawklithm
 *列名	类型
id						int
gmt_create		date
gmt_modified	date
reader_id			int
camera_id			int
equipment_id		int
 *
 */
public class SqlEquipCamReaderMappingDO {
	private Integer id;
	private Date gmtCreate ;
	private Date gmtModified;
	private Integer readerId;
	private Integer cameraId;
	private Integer equipmentId;
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
	public Integer getEquipmentId() {
		return equipmentId;
	}
	public void setEquipmentId(Integer equipmentId) {
		this.equipmentId = equipmentId;
	}
}
