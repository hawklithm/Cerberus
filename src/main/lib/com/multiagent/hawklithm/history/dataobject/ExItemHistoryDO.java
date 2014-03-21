package com.multiagent.hawklithm.history.dataobject;


public class ExItemHistoryDO extends ItemHistoryDO {
	private String processName;
	private String staffInfo;
	private String type;
	public ExItemHistoryDO(){}
	public ExItemHistoryDO(ItemHistoryDO object){
		this.id=object.getId();
		this.time=object.getTime();
		this.gmtCreate=object.getGmtCreate();
		this.gmtModified=object.getGmtModified();
		this.itemId=object.getItemId();
		this.readerId=object.getReaderId();
		this.cameraId=object.getCameraId();
		this.itemStatus=object.getItemStatus();
		this.equipmentId=object.getEquipmentId();
	}
	public String getProcessName() {
		return processName;
	}
	public void setProcessName(String processName) {
		this.processName = processName;
	}
	public String getStaffInfo() {
		return staffInfo;
	}
	public void setStaffInfo(String staffInfo) {
		this.staffInfo = staffInfo;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
