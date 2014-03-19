package com.multiagent.hawklithm.davinci.exceptioin;

/**
 * 
 * @author hawklithm 2014-1-5обнГ7:42:05
 */
public class ModuleNotFoundException extends Exception {
	private static final long serialVersionUID = -6256141756100821532L;
	private static String defaultErrorMessage = "the module you request is not found,please check your RFID";

	public ModuleNotFoundException() {
		super(defaultErrorMessage);
	}

	public ModuleNotFoundException(String msg) {
		super(msg);
	}

	public ModuleNotFoundException(Exception e) {
		super(e.getMessage());
	}
}
