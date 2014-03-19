package com.multiagent.hawklithm.exception;

public class BufferEmptyException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3172296417448008574L;
	private static String defaultErrorMessage="Buffer Empty";
	
	public BufferEmptyException() {
		super(defaultErrorMessage);
	}

	public BufferEmptyException(String msg){
		super(msg);
	}
	
	public BufferEmptyException(Exception e){
		super(e.getMessage());
	}
}
