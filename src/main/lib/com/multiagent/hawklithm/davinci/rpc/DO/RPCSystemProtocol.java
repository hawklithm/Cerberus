package com.multiagent.hawklithm.davinci.rpc.DO;

import java.util.UUID;

/**
 * 
 * @author hawklithm
 * 
 */
public class RPCSystemProtocol {
	private String[] parameters;
	private String version;
	public UUID uuid=UUID.randomUUID();
	private String returnObject; 
//	private String[] paramsType;
	private String methodName;
	private String className;
	private ParameterTypeDatas paramsType;
	private String userName;
	private String exception;
	private String exceptionType;

	public boolean selfCheck() {
		return true;
	}


	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}



	public String getReturnObject() {
		return returnObject;
	}


	public void setReturnObject(String returnObject) {
		this.returnObject = returnObject;
	}




	public String getMethodName() {
		return methodName;
	}


	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}


	public String getClassName() {
		return className;
	}


	public void setClassName(String className) {
		this.className = className;
	}


	public String[] getParameters() {
		return parameters;
	}


	public void setParameters(String[] parameters) {
		this.parameters = parameters;
	}


	public ParameterTypeDatas getParamsType() {
		return paramsType;
	}


	public void setParamsType(ParameterTypeDatas paramsType) {
		this.paramsType = paramsType;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getException() {
		return exception;
	}


	public void setException(String exception) {
		this.exception = exception;
	}


	public String getExceptionType() {
		return exceptionType;
	}


	public void setExceptionType(String exceptionType) {
		this.exceptionType = exceptionType;
	}



}
