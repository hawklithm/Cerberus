package com.hawklithm.cerberus.appService;

import org.jboss.netty.channel.Channel;
import org.springframework.util.Assert;

import com.hawklithm.cerberus.responsor.AppServiceResponsor;
import com.hawklithm.cerberus.responsor.impl.AppServiceSingleTimeResponsor;

public class AppServiceResponse {
	private Channel channel;
	private String status;
	private String response;
	private String authenticate;
	/**
	 * responsor一定要注入，在父类AbstractAppServiceTranslator中的doExecute方法中注入了
	 */
	private AppServiceResponsor responsor;
	
	public AppServiceResponse(){
		responsor=new AppServiceSingleTimeResponsor();
//		System.out.println(responsor==null);
	}
	
	public void write(String message){
		if(responsor==null){
			System.out.println("responsor is null");
		}
		Assert.notNull(responsor);
		responsor.write(message);
	}
	public void response(){
		Assert.notNull(responsor);
		response=responsor.response();
	}
	public Channel getChannel() {
		return channel;
	}
	public void setChannel(Channel channel) {
		this.channel = channel;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	public String getAuthenticate() {
		return authenticate;
	}
	public void setAuthenticate(String authenticate) {
		this.authenticate = authenticate;
	}
	public AppServiceResponsor getResponsor() {
		return responsor;
	}
	public void setResponsor(AppServiceResponsor responsor) {
		this.responsor = responsor;
	}
}
