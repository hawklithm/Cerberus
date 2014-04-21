package com.multiagent.hawklithm.leon.module.property.DO;

import java.util.Date;

import com.multiagent.hawklithm.item.dataobject.ExItemInfoDO;

public class ChangerAnnouncerPropertyWithStatus {
	private ExItemInfoDO[] itemInfo;
	private Integer[] packageInfo;
	protected Integer machineRFID;
	protected Integer staffRFID;
	protected Date timeStamp;
	protected String sourceType;
	public void setOldVersion(ChangerAnnouncerPropertyArrayVersion z){
		setMachineRFID(z.getMachineRFID());
		setStaffRFID(z.getStaffRFID());
		setTimeStamp(z.getTimeStamp());
		setSourceType(z.getSourceType());
	}
	
	public Integer[] getPackageInfo() {
		return packageInfo;
	}
	public void setPackageInfo(Integer[] packageInfo) {
		this.packageInfo = packageInfo;
	}
	public Integer getMachineRFID() {
		return machineRFID;
	}
	public void setMachineRFID(Integer machineRFID) {
		this.machineRFID = machineRFID;
	}
	public Integer getStaffRFID() {
		return staffRFID;
	}
	public void setStaffRFID(Integer staffRFID) {
		this.staffRFID = staffRFID;
	}
	public Date getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}
	public String getSourceType() {
		return sourceType;
	}
	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	public ExItemInfoDO[] getItemInfo() {
		return itemInfo;
	}

	public void setItemInfo(ExItemInfoDO[] itemInfo) {
		this.itemInfo = itemInfo;
	}
}
