package com.hawklithm.cerberus.responsor.impl;

import com.hawklithm.cerberus.responsor.AppServiceResponsor;
/*主要是将单次单单任务的消息添加到一起进行传输
 * 
 */
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