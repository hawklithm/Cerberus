package com.hawklithm.cerberus.appService;

import org.jboss.netty.channel.Channel;

public class AppServiceRequest {
	private String content;
	private String authenticate;
	private Channel channel;
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getAuthenticate() {
		return authenticate;
	}
	public void setAuthenticate(String authenticate) {
		this.authenticate = authenticate;
	}
	public Channel getChannel() {
		return channel;
	}
	public void setChannel(Channel channel) {
		this.channel = channel;
	}
}
