package com.multiagent.hawklithm.leon.DO;

import java.util.Date;

public class ExSqlReaderAtEquipmentDO extends SqlReaderAtEquipmentDO {
	private Date startTime;
	private Date endTime;
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
}
