package com.hawklithm.cerberus.appService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.servlet.ServletException;

import org.hawklithm.BlueHawky.utils.SwitcherController;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.util.internal.ConcurrentHashMap;
import org.springframework.util.Assert;

import com.hawklithm.cerberus.protocol.FrontEndingCommunicationProtocol;
import com.hawklithm.utils.Pair;
import com.multiagent.hawklithm.exception.NothingChangeAndDoNotNeedToExecuteException;
import com.multiagent.hawklithm.net.handler.AppServiceNettyHandler;

public class WarningInfoServiceTranslator extends AppCommonServiceTranslator{
	private Map<String, SwitcherController> controllerMap = new ConcurrentHashMap<String, SwitcherController>();
	private Queue<SwitcherController> oneTimeController=new ConcurrentLinkedQueue<SwitcherController>();
	private Thread thread=new Thread (new Runnable(){

		@Override
		public void run() {
			while (true) {
				while(!oneTimeController.isEmpty()){
					 oneTimeController.poll().run();
				}
				Iterator<Map.Entry<String, SwitcherController> > it=controllerMap.entrySet().iterator();
				while(it.hasNext()){
					Map.Entry<String, SwitcherController> entry=it.next();
					SwitcherController switcher=entry.getValue();
					if (!switcher.isAlive()){
						it.remove();
					}else {
						switcher.run();
					}
				}
//				
//				for (Map.Entry<String, SwitcherController> index : controllerMap.entrySet()) {
//					index.getValue().run();
//				}
				System.out.print(".");
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
	});


	public void init(){
		System.out.println("线程开启");
		thread.start();
	}
	
	@Override
	public void execute(AppServiceRequest request, AppServiceResponse response) {
		System.out.println("收到请求");
		String addressAndPort = response.getChannel().getRemoteAddress().toString();
		SwitcherController controller;
		if (controllerMap.containsKey(addressAndPort)) {
			controller = controllerMap.get(addressAndPort);
			if (request.isKeepAlive()){
				controller.setKeepAliveRequest(request);
				controller.setKeepAliveResponse(response);
			}else {
				controller.addOneTimeRequest(new Pair<AppServiceRequest, AppServiceResponse>(request, response));
			}
			System.out.println("连接已存在: "+addressAndPort);
		} else {
			// 初始化switcherContoller
			controller = new SwitcherController(addressAndPort) {

				@Override
				public void run() {
					Assert.notNull(channel);
					if (!channel.isConnected()){
						this.setAlive(false);
						return;
					}
					System.out.println("loop address: " + channel.getRemoteAddress().toString());
					/**
					 * 执行长连接
					 */
					if (keepAliveRequest!=null){
						/**
						 * 执行长连接的请求任务
						 */
						invoke(keepAliveRequest,keepAliveResponse);
					}
					
					/**
					 * 执行一次性请求任务
					 */
					while(!isOneTimeRequestEmpty()){
						Pair<AppServiceRequest, AppServiceResponse> pair=getOneTimeRequest();
						invoke(pair.getFirst(), pair.getLast());
					}
				}
				
				private void invoke(AppServiceRequest request, AppServiceResponse response){
					FrontEndingCommunicationProtocol<Map<String, Object>> message = gson.fromJson(
							request.getContent(), FrontEndingCommunicationProtocol.class);
					FrontEndingCommunicationProtocol<Map<String, Object>> result = null;
					
						try {
							System.out.println("获取机器buffer数据");
							System.out.println("查询条件: " + gson.toJson(message));
							result = executor.execute(message);
							sendMessage(channel, result,response);
						} catch (Exception e){
							if (e instanceof ServletException || e instanceof IOException){
								e.printStackTrace();
							}else if (e instanceof NothingChangeAndDoNotNeedToExecuteException){
								
							}
						}
				}


				public void sendMessage(Channel channel,
						FrontEndingCommunicationProtocol<Map<String, Object>> protocol,AppServiceResponse  response) {
					if (protocol == null)
						return;
					Map<String, String> retMap = new HashMap<String, String>();
					AppServiceNettyHandler.setStatus(retMap, "connected"/*response.getStatus()*/);
					AppServiceNettyHandler.setAuthenticate(retMap, response.getAuthenticate());
					AppServiceNettyHandler.setResponse(retMap, gson.toJson(protocol));
					AppServiceNettyHandler.setTargetUrl(retMap, "/Announcement");
					String message = gson.toJson(retMap);
					System.out.println("send announcement message to controll center: " + message);
					byte[] messageBytes = message.getBytes();
					ChannelBuffer buffer = ChannelBuffers.buffer(messageBytes.length);
					buffer.writeBytes(messageBytes);
					channel.write(buffer);
				}

			};
			controller.setChannel(request.getChannel());
			if (request.isKeepAlive()){
				controller.setKeepAliveRequest(request);
				controller.setKeepAliveResponse(response);
				controllerMap.put(addressAndPort, controller);
				System.out.println("添加新连接: "+addressAndPort);
			}else {
				controller.addOneTimeRequest(new Pair<AppServiceRequest, AppServiceResponse>(request, response));
				oneTimeController.add(controller);
				System.out.println("添加一次性任务");
			}
		}
		
		FrontEndingCommunicationProtocol<Map<String, Object>> result = new FrontEndingCommunicationProtocol<Map<String, Object>>();
		response.write(gson.toJson(result));
		response.setStatus("ok");
	}


}
