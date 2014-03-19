package com.multiagent.hawklithm.exception;
/**
 * 
 * @author hawklithm
 *
 */
public class MessageTransportException extends Exception {


	private static final long serialVersionUID = 3952700842430951420L;

	public MessageTransportException(String msg){
		super(msg);
	}
	
	public MessageTransportException(Exception e){
		super(e.getMessage());
	}
}
