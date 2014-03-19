package com.hawklithm.cerberus.protocol;

import java.util.Date;

public class FlowRecordDO {
	private String infoType;
	private Integer recordId;
	private Date startTime;
	private Date endTime;
	private Integer equipmentId;
	private Integer objectId;
	private String objectType;
	private Integer readerId;
	private Integer cameraId;
	private Integer staffId;
	private String processName;
	private Date realTime;
	private String objectStatus;
	private final static String TYPE_EQUIPMENT_HISTORY="type_equipment_history",TYPE_ITEM_HISTORY="type_item_history",TYPE_PACKAGE_HISTORY="type_package_history",ITEM="item",PACKAGE="package";
	public static boolean isItem(String type){
		return type.equals(ITEM);
	}
	public static boolean isPackage(String type){
		return type.equals(PACKAGE);
	}
	public static boolean isTypeEquipmentHistory(String type){
		return type.equals(TYPE_EQUIPMENT_HISTORY);
	}
	public static boolean isTypeItemHistory(String type){
		return type.equals(TYPE_ITEM_HISTORY);
	}
	public static boolean isTypePackageHistory(String type){
		return type.equals(TYPE_PACKAGE_HISTORY);
	}
	public String getInfoType() {
		return infoType;
	}
	public void setInfoType(String infoType) {
		this.infoType = infoType;
	}
	public Integer getRecordId() {
		return recordId;
	}
	public void setRecordId(Integer recordId) {
		this.recordId = recordId;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Integer getEquipmentId() {
		return equipmentId;
	}
	public void setEquipmentId(Integer equipmentId) {
		this.equipmentId = equipmentId;
	}
	public Integer getObjectId() {
		return objectId;
	}
	public void setObjectId(Integer objectId) {
		this.objectId = objectId;
	}
	public String getObjectType() {
		return objectType;
	}
	public void setObjectType(String objectType) {
		this.objectType = objectType;
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
	public Integer getStaffId() {
		return staffId;
	}
	public void setStaffId(Integer staffId) {
		this.staffId = staffId;
	}
	public String getProcessName() {
		return processName;
	}
	public void setProcessName(String processName) {
		this.processName = processName;
	}
	public Date getRealTime() {
		return realTime;
	}
	public void setRealTime(Date realTime) {
		this.realTime = realTime;
	}
	public String getObjectStatus() {
		return objectStatus;
	}
	public void setObjectStatus(String objectStatus) {
		this.objectStatus = objectStatus;
	}
}
