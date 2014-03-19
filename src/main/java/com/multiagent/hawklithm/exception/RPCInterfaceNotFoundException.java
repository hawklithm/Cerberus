package com.multiagent.hawklithm.exception;

public class RPCInterfaceNotFoundException extends Exception {

	private static final long serialVersionUID = 2510856824635126456L;
	private static String defaultErrorMessage="the rpc interface you request is not found";

	public RPCInterfaceNotFoundException() {
		super(defaultErrorMessage);
	}

	public RPCInterfaceNotFoundException(String msg) {
		super(defaultErrorMessage+"["+msg+"]");
	}

	public RPCInterfaceNotFoundException(Exception e) {
		super(e.getMessage());
	}
}
