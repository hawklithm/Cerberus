package com.multiagent.hawklithm.rpc.manager;

import org.springframework.aop.framework.ProxyFactoryBean;

public abstract class AbstractRpcInterceptor {
	private ProxyFactoryBean proxyFactory;

	public ProxyFactoryBean getProxyFactory() {
		return proxyFactory;
	}

	public void setProxyFactory(ProxyFactoryBean proxyFactory) {
		this.proxyFactory = proxyFactory;
	}
}
