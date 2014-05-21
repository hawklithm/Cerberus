package com.multiagent.hawklithm.history.dataobject;


public class ExPackageHistoryDO extends PackageHistoryDO {
	private String processName;
	private String staffInfo;
	private String type;
	public ExPackageHistoryDO(){
		
	}
	public ExPackageHistoryDO(PackageHistoryDO oldInfo){
		setId(oldInfo.getId());
		setTime(oldInfo.getTime());
		setGmtCreate(oldInfo.getGmtCreate());
		setGmtModified(oldInfo.getGmtModified());
		setPackageId(oldInfo.getPackageId());
		setReaderId(oldInfo.getReaderId());
		setCameraId(oldInfo.getCameraId());
		setEquipmentId(oldInfo.getEquipmentId());
		setPackageStatus(oldInfo.getPackageStatus());
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
