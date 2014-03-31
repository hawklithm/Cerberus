package com.multiagent.hawklithm.annoucement.dataobject;

import com.multiagent.hawklithm.leon.module.SimpleMessageTransportBufferModule.SimpleMessage;

public class AnnouncementInfoDO {
	private String processName;
	private SimpleMessage[] retValue;

	// private String[] retValue;
	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}


	public SimpleMessage[] getRetValue() {
		return retValue;
	}

	public void setRetValue(SimpleMessage[] retValue) {
		this.retValue = retValue;
	}

}
