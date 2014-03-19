
package org.hawklithm.BlueHawky.utils;

import org.jboss.netty.channel.Channel;

import com.hawklithm.cerberus.appService.AppServiceRequest;
import com.hawklithm.cerberus.appService.AppServiceResponse;

public abstract class SwitcherController{
	protected Switcher switcher;
	protected Channel channel;
	protected AppServiceRequest request;
	protected AppServiceResponse response;
	protected String name;
	public SwitcherController(/*Switchable target,*/String name){
//		switcher=new Switcher(target);
		this.name=name;
	}
	
	abstract public void run();
	
	public Switcher getSwitcher() {
		return switcher;
	}
	public void setSwitcher(Switcher switcher) {
		this.switcher = switcher;
	}
	public Channel getChannel() {
		return channel;
	}
	public void setChannel(Channel channel) {
		this.channel = channel;
	}
	public AppServiceRequest getRequest() {
		return request;
	}
	public void setRequest(AppServiceRequest request) {
//		switcher.disable();
		this.request = request;
		setChannel(request.getChannel());
//		if (this.channel!=null){
//			channel.close();
//		}
//		this.channel=request.getChannel();
//		switcher.enable();
	}
	public AppServiceResponse getResponse() {
		return response;
	}
	public void setResponse(AppServiceResponse response) {
		this.response = response;
	}
}
