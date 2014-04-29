package com.multiagent.hawklithm.davinci.exceptioin;

/**
 * 
 * @author hawklithm 2014-1-5ÏÂÎç7:44:14
 */
public class ProcessModuleNotFoundException extends ModuleNotFoundException {
	private static final long serialVersionUID = 5377301587941344722L;
	private static String defaultErrorMessage = "the ProcessModule you request is not found,please check your ProcessModule Name";

	public ProcessModuleNotFoundException() {
		super(defaultErrorMessage);
	}

	public ProcessModuleNotFoundException(String msg) {
		super(msg);
	}

	public ProcessModuleNotFoundException(String msg, String moduleName) {
		super(defaultErrorMessage + " [" + moduleName + " not found ] " + msg);
	}

	public ProcessModuleNotFoundException(Exception e) {
		super(e.getMessage());
	}

}
