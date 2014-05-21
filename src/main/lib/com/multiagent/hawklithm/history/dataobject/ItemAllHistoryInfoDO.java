package com.multiagent.hawklithm.history.dataobject;

import java.util.Date;

public class ItemAllHistoryInfoDO {
	private ExItemHistoryDO[] items;
	private Date startTime;
	private Date endTime;
	public ExItemHistoryDO[] getItems() {
		return items;
	}
	public void setItems(ExItemHistoryDO[] items) {
		this.items = items;
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
}
