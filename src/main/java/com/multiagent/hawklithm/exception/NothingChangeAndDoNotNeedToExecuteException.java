package com.multiagent.hawklithm.exception;

public class NothingChangeAndDoNotNeedToExecuteException extends Exception {
	private static final long serialVersionUID = -2565369097912505132L;

	public NothingChangeAndDoNotNeedToExecuteException(String msg){
		super(msg);
	}
	
	public NothingChangeAndDoNotNeedToExecuteException(Exception e){
		super(e.getMessage());
	}
}
