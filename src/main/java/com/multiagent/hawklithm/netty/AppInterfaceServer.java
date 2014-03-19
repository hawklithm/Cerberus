package com.multiagent.hawklithm.netty;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.frame.LengthFieldBasedFrameDecoder;
import org.jboss.netty.handler.codec.frame.LengthFieldPrepender;

import com.google.gson.Gson;
import com.multiagent.hawklithm.davinci.rpc.DO.RPCSystemProtocol;
import com.multiagent.hawklithm.exception.MessageTransportException;
import com.multiagent.hawklithm.rpc.manager.RPCLockManager;
import com.multiagent.hawklithm.rpc.net.NettyHandler;

/**
 * PC应用接口服务端
 * 
 * @author hawklithm 2014-1-10下午3:20:48
 */
public class AppInterfaceServer {
	private int port;
	private Channel channel;
	private NettyHandler handler;
	private Gson gson = new Gson();
	private RPCLockManager lockManager;

	public AppInterfaceServer(int port) {
		this.port = port;
		init();
	}
	public AppInterfaceServer(){
		this.port = 48800;
		init();
	}


	public void init() {
		ServerBootstrap bootstrap = new ServerBootstrap(new NioServerSocketChannelFactory(
				Executors.newCachedThreadPool(), Executors.newCachedThreadPool()));
		bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
			public ChannelPipeline getPipeline() throws Exception {
				ChannelPipeline pipeline = Channels.pipeline();
				pipeline.addLast("UP_FRAME_HANDLER", new LengthFieldBasedFrameDecoder(
						Integer.MAX_VALUE, 0, 2, 0, 2));
				pipeline.addLast("DOWN_FRAME_HANDLER", new LengthFieldPrepender(2, false));
				// return Channels.pipeline(handler);
				pipeline.addLast("myHandler", handler);
				return pipeline;
			}
		});
		bootstrap.bind(new InetSocketAddress(port));
	}

	public void sendMessage(String msg) throws MessageTransportException {
		handler.sendMessage(msg, channel);
	}

	public void sendRPCProtocol(RPCSystemProtocol msg) throws MessageTransportException {
		String message = gson.toJson(msg);
//		System.out.println(message);
		handler.sendMessage(message, channel);
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}


	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	public NettyHandler getHandler() {
		return handler;
	}

	public void setHandler(NettyHandler handler) {
		this.handler = handler;
	}


	public RPCLockManager getLockManager() {
		return lockManager;
	}

	public void setLockManager(RPCLockManager lockManager) {
		this.lockManager = lockManager;
	}

}
