package com.multiagent.hawklithm.leon.module.SimpleMessageTransportBufferModule;

import java.util.Date;

public class SimpleMessage {
	private Date time;
	private String message;

	public SimpleMessage() {

	}

	public SimpleMessage(Date time, String message) {
		this.time = time;
		this.message = message;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
