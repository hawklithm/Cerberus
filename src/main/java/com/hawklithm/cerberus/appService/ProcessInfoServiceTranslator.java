package com.hawklithm.cerberus.appService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;

import org.hawklithm.BlueHawky.utils.SwitcherController;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.util.internal.ConcurrentHashMap;
import org.springframework.util.Assert;

import com.hawklithm.cerberus.protocol.FrontEndingCommunicationProtocol;
import com.multiagent.hawklithm.net.handler.AppServiceNettyHandler;

public class ProcessInfoServiceTranslator extends AppCommonServiceTranslator /*implements Switchable*/ {

	private AppServiceRequest request;
	private AppServiceResponse response;
//	private boolean flag = false;
	private Map<String, SwitcherController> controllerMap = new ConcurrentHashMap<String, SwitcherController>();
	private Thread thread=new Thread (new Runnable(){

		@Override
		public void run() {
			while (true) {
				for (Map.Entry<String, SwitcherController> index : controllerMap.entrySet()) {
					index.getValue().run();
				}
				System.out.print(".");
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
	});

	// private SwitcherController controller = new SwitcherController(this) {
	//
	// @Override
	// public void run() {
	// Assert.notNull(channel);
	// System.out.print(".");
	// System.out.println("loop address: "+channel.getRemoteAddress().toString());
	// flag=true;
	// FrontEndingCommunicationProtocol<Map<String, Object>> message =
	// gson.fromJson(
	// request.getContent(), FrontEndingCommunicationProtocol.class);
	// FrontEndingCommunicationProtocol<Map<String, Object>> result = null ;
	// while (channel.isConnected()) {
	// try {
	// System.out.println("获取机器buffer数据");
	// System.out.println("查询条件: "+gson.toJson(message));
	// result = executor.execute(message);
	// } catch (ServletException | IOException e1) {
	// e1.printStackTrace();
	// }
	// sendMessage(channel, result);
	// try {
	// Thread.sleep(3000);
	// } catch (InterruptedException e) {
	// e.printStackTrace();
	// }
	// }
	// }
	// public void sendMessage(Channel channel,
	// FrontEndingCommunicationProtocol<Map<String, Object>> protocol) {
	// if (protocol==null) return;
	// Map<String,String> retMap=new HashMap<String,String>();
	// AppServiceNettyHandler.setStatus(retMap,"connected"/*response.getStatus()*/);
	// AppServiceNettyHandler.setAuthenticate(retMap,response.getAuthenticate());
	// // AppServiceNettyHandler.setContent(retMap, gson.toJson(protocol));
	// AppServiceNettyHandler.setResponse(retMap, gson.toJson(protocol));
	// String message=gson.toJson(retMap);
	// System.out.println("send message to controll center: "+message);
	// byte[] messageBytes = message.getBytes();
	// ChannelBuffer buffer = ChannelBuffers.buffer(messageBytes.length);
	// buffer.writeBytes(messageBytes);
	// channel.write(buffer);
	// }
	//
	// };

	public void init(){
		System.out.println("线程开启");
		thread.start();
	}
	
	@Override
	public void execute(AppServiceRequest request, AppServiceResponse response) {
		System.out.println("收到请求");
		this.request = request;
		this.response = response;
		String addressAndPort = response.getChannel().getRemoteAddress().toString();
		SwitcherController controller;
		if (controllerMap.containsKey(addressAndPort)) {
			controller = controllerMap.get(addressAndPort);
			System.out.println("连接已存在: "+addressAndPort);
		} else {
			// 初始化switcherContoller
			controller = new SwitcherController(addressAndPort) {

				@Override
				public void run() {
					Assert.notNull(channel);
//					System.out.print(".");
					System.out.println("loop address: " + channel.getRemoteAddress().toString());
//					flag = true;
					FrontEndingCommunicationProtocol<Map<String, Object>> message = gson.fromJson(
							request.getContent(), FrontEndingCommunicationProtocol.class);
					FrontEndingCommunicationProtocol<Map<String, Object>> result = null;
					
//					while (channel.isConnected()) {
						try {
							System.out.println("获取机器buffer数据");
							System.out.println("查询条件: " + gson.toJson(message));
							result = executor.execute(message);
						} catch (ServletException | IOException e1) {
							e1.printStackTrace();
						}
						sendMessage(channel, result);
						
//					}
				}

				public void sendMessage(Channel channel,
						FrontEndingCommunicationProtocol<Map<String, Object>> protocol) {
					if (protocol == null)
						return;
					Map<String, String> retMap = new HashMap<String, String>();
					AppServiceNettyHandler.setStatus(retMap, "connected"/*response.getStatus()*/);
					AppServiceNettyHandler.setAuthenticate(retMap, response.getAuthenticate());
					AppServiceNettyHandler.setResponse(retMap, gson.toJson(protocol));
					String message = gson.toJson(retMap);
					System.out.println("send message to controll center: " + message);
					byte[] messageBytes = message.getBytes();
					ChannelBuffer buffer = ChannelBuffers.buffer(messageBytes.length);
					buffer.writeBytes(messageBytes);
					channel.write(buffer);
				}

			};
			
			controllerMap.put(addressAndPort, controller);
			System.out.println("添加新连接: "+addressAndPort);
		}
		controller.setRequest(request);
		controller.setResponse(response);
		
		FrontEndingCommunicationProtocol<Map<String, Object>> result = new FrontEndingCommunicationProtocol<Map<String, Object>>();
		// result.setStatusOk();
		response.write(gson.toJson(result));
		response.setStatus("ok");
	}

//	@Override
//	public void enable(Object... object) {
//		if (thread == null) {
//			thread = new Thread(runnable);
//			thread.start();
//			System.out.println("线程开启");
//		} else if (!flag) {
//			thread.resume();
//			flag = true;
//		}
//	}
//
//	@Override
//	public void disable(Object... object) {
//		if (thread != null) {
//			if (flag) {
//				thread.suspend();
//				flag = false;
//			}
//		}
//	}

	public AppServiceRequest getRequest() {
		return request;
	}

	public void setRequest(AppServiceRequest request) {
		this.request = request;
	}

	public AppServiceResponse getResponse() {
		return response;
	}

	public void setResponse(AppServiceResponse response) {
		this.response = response;
	}

}
