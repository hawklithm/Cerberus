package com.multiagent.hawklithm.leon.process.dataobject;

import com.multiagent.hawklithm.leon.module.property.DO.ChangerAnnouncerPropertyArrayVersion;

public class ProcessInfoDO {
	private String processName;
	private Integer id;
	private ChangerAnnouncerPropertyArrayVersion[] retValue;
//	private String[] retValue;
	public String getProcessName() {
		return processName;
	}
	public void setProcessName(String processName) {
		this.processName = processName;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer  id) {
		this.id = id;
	}
	public ChangerAnnouncerPropertyArrayVersion[] getRetValue() {
		return retValue;
	}
	public void setRetValue(ChangerAnnouncerPropertyArrayVersion[] retValue) {
		this.retValue = retValue;
	}
}
