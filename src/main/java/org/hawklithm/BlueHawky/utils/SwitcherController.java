
package org.hawklithm.BlueHawky.utils;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.jboss.netty.channel.Channel;

import com.hawklithm.cerberus.appService.AppServiceRequest;
import com.hawklithm.cerberus.appService.AppServiceResponse;
import com.hawklithm.utils.Pair;

public abstract class SwitcherController{
	protected Switcher switcher;
	protected Channel channel;
	protected AppServiceRequest keepAliveRequest;
	protected AppServiceResponse keepAliveResponse;
//	protected Queue<AppServiceRequest> request=new LinkedList<AppServiceRequest>();
//	protected Queue<AppServiceResponse>response=new LinkedList<AppServiceResponse>();
	protected Queue<Pair<AppServiceRequest, AppServiceResponse>> request=new ConcurrentLinkedQueue<Pair<AppServiceRequest, AppServiceResponse>>();
	protected String name;
	
//	public class ServicePair{
//		public  AppServiceRequest keepAliveRequest;
//		public AppServiceResponse keepAliveResponse;
//		public ServicePair(AppServiceRequest keepAliveRequest, AppServiceResponse keepAliveResponse) {
//			this.keepAliveRequest=keepAliveRequest;
//			this.keepAliveResponse=keepAliveResponse;
//		}
//	}
	
	public SwitcherController(/*Switchable target,*/String name){
//		switcher=new Switcher(target);
		this.name=name;
	}
	
	abstract public void run();
	
	public void addOneTimeRequest(Pair<AppServiceRequest, AppServiceResponse> request){
		this.request.offer(request);
	}
//	public void addOneTimeResponse(AppServiceResponse response){
//		this.response.offer(response);
//	}
	public boolean isOneTimeRequestEmpty(){
		return request.isEmpty();
	}
	public Pair<AppServiceRequest, AppServiceResponse> getOneTimeRequest(){
		return request.poll();
	}
//	public AppServiceResponse getOneTimeResponse(){
//		return response.poll();
//	}
	
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


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public AppServiceRequest getKeepAliveRequest() {
		return keepAliveRequest;
	}

	public void setKeepAliveRequest(AppServiceRequest keepAliveRequest) {
		this.keepAliveRequest = keepAliveRequest;
//		setChannel(keepAliveRequest.getChannel());
	}

	public AppServiceResponse getKeepAliveResponse() {
		return keepAliveResponse;
	}

	public void setKeepAliveResponse(AppServiceResponse keepAliveResponse) {
		this.keepAliveResponse = keepAliveResponse;
	}
	
}
