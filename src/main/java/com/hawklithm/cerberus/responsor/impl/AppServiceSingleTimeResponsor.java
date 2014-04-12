package com.hawklithm.cerberus.responsor.impl;

import com.hawklithm.cerberus.responsor.AppServiceResponsor;

public class AppServiceSingleTimeResponsor implements AppServiceResponsor{
	private StringBuilder messageBuffer=new StringBuilder();
	@Override
	public void write(String message){
		messageBuffer.append(message);
	}
	@Override
	public String response(){
		String tmp=messageBuffer.toString();
		messageBuffer.setLength(0);
		return tmp;
	}
}