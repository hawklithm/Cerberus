package com.multiagent.hawklithm.rpc.event;

import org.springframework.context.ApplicationEvent;

import com.multiagent.hawklithm.davinci.rpc.DO.RPCSystemProtocol;

public class ValueReturnEvent extends ApplicationEvent{

	private static final long serialVersionUID = 2369358683505146234L;
	private RPCSystemProtocol msg;
	
	public ValueReturnEvent(Object source,RPCSystemProtocol msg) {
		super(source);
		this.msg=msg;
	}
	
	public RPCSystemProtocol getMessage(){
		return msg;
	}
	
}
