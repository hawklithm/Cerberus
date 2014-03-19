package com.multiagent.hawklithm.davinci.rpc.DO;

public class ParameterTypeDatas {
	private String[] parameterTypes;
	private Boolean[] isPrimitive;
	private int length;

	public ParameterTypeDatas(int len) {
		parameterTypes = new String[len];
		isPrimitive = new Boolean[len];
		length = len;
	}

	public int getLength() {
		return length;
	}

	public boolean isPrimitive(int index) {
		return isPrimitive[index] == null ? false : isPrimitive[index];
	}

	public void setPrimitive(int index) {
		isPrimitive[index] = new Boolean(true);
	}

	public String getParamType(int index) {
		return parameterTypes[index];
	}

	public void setParamType(int index, String str) {
		parameterTypes[index] = str;
	}
}
