package com.multiagent.hawklithm.net.handler;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils.Null;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;

import com.google.gson.Gson;
import com.hawklithm.cerberus.appService.AppServiceRequest;
import com.hawklithm.cerberus.appService.AppServiceResponse;
import com.hawklithm.cerberus.nettyservice.AppServiceHandler;
import com.multiagent.hawklithm.exception.MessageTransportException;
import com.multiagent.hawklithm.net.manager.ProcessorRegister;
import com.multiagent.hawklithm.rpc.net.NettyHandler;

public class AppServiceNettyHandler extends NettyHandler {
	
	private Gson gson=new Gson();
	private ProcessorRegister processorRegister;
	public ProcessorRegister getProcessorRegister() {
		return processorRegister;
	}
	public void setProcessorRegister(ProcessorRegister processorRegister) {
		this.processorRegister = processorRegister;
	}
	private static String TARGET_URL="targetUrl",STATUS="status",CONTENT="content",AUTHENTICATE="authenticate",RESPONSE="response",KEEP_ALIVE="keepAlive";
	private static String KEEP_ALIVE_TRUE="keep_alive_true";
	
	public static void setKeepAlive(Map<String, String> map,String state){
		map.put(KEEP_ALIVE, state);
	}
	public static String getKeepAlive(Map<String, String> map){
		if (map.containsKey(KEEP_ALIVE)){
		return map.get(KEEP_ALIVE);
		}else {
			return "null string";
		}
	}
	
	public static void setTargetUrl(Map<String, String> map,String targetUrl){
		map.put(TARGET_URL, targetUrl);
	}
	public static String getTargetUrl(Map<String,String> map) {
		return map.get(TARGET_URL);
	}
	public static  void setStatus(Map<String,String>map,String status) {
		map.put(STATUS,status);
	}
	public static  void setAuthenticate(Map<String,String>map,String authenticate){
		map.put(AUTHENTICATE, authenticate);
	}
	public static String getAuthenticate(Map<String,String> map){
		return map.get(AUTHENTICATE);
	}
	public static String getContent(Map<String,String>map){
		return map.get(CONTENT);
	}
	public static void setContent(Map<String, String>map,String content){
		map.put(CONTENT, content);
	}
	public static void setResponse(Map<String, String>map,String content){
		map.put(RESPONSE, content);
	}
	public static String getResponse(Map<String, String>map){
		return map.get(RESPONSE);
	}
	/**
	 * 将通道和数据打包，分别将request和response发送给对应url的service,具体响应由response中的函数来处理
	 */
	@Override
	public void onMessageReceived(String message, Channel channel) throws MessageTransportException {
		Map<String,String> infoMap=gson.fromJson(message, HashMap.class);
		Map<String,String> retMap=new HashMap<String,String>();
		String targetUrl=getTargetUrl(infoMap);
		AppServiceHandler handler= processorRegister.getAppService(targetUrl);
		AppServiceRequest request=new AppServiceRequest();
		request.setAuthenticate(getAuthenticate(infoMap));
		request.setChannel(channel);
		request.setKeepAlive(getKeepAlive(infoMap).equals(KEEP_ALIVE_TRUE));
		System.out.println("address: "+channel.getRemoteAddress().toString());
		request.setContent(getContent(infoMap));
		AppServiceResponse response=new AppServiceResponse();
		response.setChannel(channel);
		if (handler!=null){
			handler.doExcute(request, response);
			setStatus(retMap,response.getStatus());
			response.response();
		}else{
			setStatus(retMap, "fail");
			System.out.println("handler is null");
		}
		setTargetUrl(retMap, targetUrl);
		setAuthenticate(retMap,response.getAuthenticate());
		setResponse(retMap, response.getResponse());
		sendMessage(gson.toJson(retMap), channel);
	}
	@Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		System.out.println("有人连接上了");
		super.channelConnected(ctx, e);
	}
}
