package com.multiagent.hawklithm.leon.module.SimpleMessageTransportBufferModule;

import java.util.Date;

public class SimpleMessage {
	private Date timeStamp;
	private String message;

	public SimpleMessage() {

	}

	public SimpleMessage(Date time, String message) {
		this.timeStamp=time;
		this.message = message;
	}



	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}
}
