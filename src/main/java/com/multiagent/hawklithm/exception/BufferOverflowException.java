package com.multiagent.hawklithm.exception;

public class BufferOverflowException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8740634152684365049L;
	private static String defaultErrorMessage="Buffer Overflow";
	
	public BufferOverflowException() {
		super(defaultErrorMessage);
	}

	public BufferOverflowException(String msg){
		super(msg);
	}
	
	public BufferOverflowException(Exception e){
		super(e.getMessage());
	}
}
