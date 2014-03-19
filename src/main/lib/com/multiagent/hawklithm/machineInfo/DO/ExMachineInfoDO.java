package com.multiagent.hawklithm.machineInfo.DO;

import java.util.Date;

public class ExMachineInfoDO extends MachineInfoDO {
	private Date gmtBuyStart;
	private Date gmtBuyEnd;
	private Date gmtLastRepairStart;
	private Date gmtLastRepairEnd;

	public Date getGmtBuyStart() {
		return gmtBuyStart;
	}

	public void setGmtBuyStart(Date gmtBuyStart) {
		this.gmtBuyStart = gmtBuyStart;
	}

	public Date getGmtBuyEnd() {
		return gmtBuyEnd;
	}

	public void setGmtBuyEnd(Date gmtBuyEnd) {
		this.gmtBuyEnd = gmtBuyEnd;
	}

	public Date getGmtLastRepairStart() {
		return gmtLastRepairStart;
	}

	public void setGmtLastRepairStart(Date gmtLastRepairStart) {
		this.gmtLastRepairStart = gmtLastRepairStart;
	}

	public Date getGmtLastRepairEnd() {
		return gmtLastRepairEnd;
	}

	public void setGmtLastRepairEnd(Date gmtLastRepairEnd) {
		this.gmtLastRepairEnd = gmtLastRepairEnd;
	}
}
