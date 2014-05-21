package com.multiagent.hawklithm.history.dataobject;

import java.util.Date;

public class PackageAllHistoryInfoDO {
	private ExPackageHistoryDO[] packs;
	private Date startTime;
	private Date endTime;
	public ExPackageHistoryDO[] getPacks() {
		return packs;
	}
	public void setPacks(ExPackageHistoryDO[] packs) {
		this.packs = packs;
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
