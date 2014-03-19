package com.multiagent.hawklithm.rpc.net;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.handler.codec.frame.LengthFieldBasedFrameDecoder;
import org.jboss.netty.handler.codec.frame.LengthFieldPrepender;

import com.google.gson.Gson;
import com.multiagent.hawklithm.davinci.rpc.DO.RPCSystemProtocol;
import com.multiagent.hawklithm.exception.MessageTransportException;
import com.multiagent.hawklithm.rpc.DAO.Announcer;
import com.multiagent.hawklithm.rpc.manager.RPCLockManager;

public class RPCClient {
	private int port;
	private String address;
	private Channel channel;
	private NettyHandler handler;
	private Gson gson = new Gson();
	private Announcer announcer;
	private RPCLockManager lockManager;

	public RPCClient(int port) {
		this.port = port;
		this.address = "127.0.0.1";
		initRPCClient();
	}

	public RPCClient(int port, String address) {
		this.port = port;
		this.address = address;
		initRPCClient();
	}

	public void initRPCClient() {
		ClientBootstrap bootstrap = new ClientBootstrap(new NioClientSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool()));
		handler = new NettyHandler() {

			private Gson gson = new Gson();

			@Override
			public void onMessageReceived(String msg, Channel cha) throws MessageTransportException {
				RPCSystemProtocol rpcMessage = gson.fromJson(msg, RPCSystemProtocol.class);
				if (!rpcMessage.selfCheck()) {
					throw new MessageTransportException("RPC包错误");
				}
				// announcer.insert(rpcMessage);
				lockManager.AnswerReceived(rpcMessage.uuid, rpcMessage);//接收到服务端回复后将结果返回并将rpc调用解锁

			}

			@Override
			public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
				channel = e.getChannel();
			}
		};
		bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
			public ChannelPipeline getPipeline() throws Exception {
				ChannelPipeline pipeline = Channels.pipeline();
				pipeline.addLast("UP_FRAME_HANDLER", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
				pipeline.addLast("DOWN_FRAME_HANDLER", new LengthFieldPrepender(4, false));
				// return Channels.pipeline(handler);
				pipeline.addLast("myHandler", handler);
				return pipeline;
			}
		});
		bootstrap.connect(new InetSocketAddress(address, port));
	}

	public void sendMessage(String msg) throws MessageTransportException {
		handler.sendMessage(msg, channel);
	}

	public void sendRPCProtocol(RPCSystemProtocol msg) throws MessageTransportException {
		String message = gson.toJson(msg);
		System.out.println(message);
		handler.sendMessage(message, channel);
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public Announcer getAnnouncer() {
		return announcer;
	}

	public void setAnnouncer(Announcer announcer) {
		this.announcer = announcer;
	}

	public RPCLockManager getLockManager() {
		return lockManager;
	}

	public void setLockManager(RPCLockManager lockManager) {
		this.lockManager = lockManager;
	}

}
