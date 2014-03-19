package com.multiagent.hawklithm.rpc.manager;

import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import com.hawklithm.cerberus.nettyservice.AppServiceHandler;
import com.multiagent.hawklithm.net.manager.ProcessorRegister;

public class RpcAutoRegister implements BeanPostProcessor {

	private RpcRegManager rpcProxyRegManager;
	private ProcessorRegister processorRegister;

	private ProxyFactoryBean proxyRoot = null;

	public RpcRegManager getRpcProxyRegManager() {
		return rpcProxyRegManager;
	}

	public void setRpcProxyRegManager(RpcRegManager rpcProxyRegManager) {
		this.rpcProxyRegManager = rpcProxyRegManager;
	}

	public Object postProcessBeforeInitialization(Object bean, String beanName)
			throws BeansException {
		return bean;
	}

	public Object postProcessAfterInitialization(Object bean, String beanName)
			throws BeansException {
		System.out.println(bean.getClass());
		try {
			if ((bean instanceof ProxyFactoryBean) && beanName.equals("proxyRoot")) {
				proxyRoot = (ProxyFactoryBean) bean;
			} else if (bean instanceof RpcSystemProxy) {
				rpcProxyRegManager.regist((RpcSystemProxy) bean);
				// TODO 打印日志信息
				System.out.println("完成 " + ((RpcSystemProxy) bean).getInterfaceName() + "注册");
				if (proxyRoot == null) {
					throw new Exception(
							"rpc framework initialization error, please write the ProxyFactoryBean before all the RpcSystemProxy in the xml");
				}
				Class<?>[] tmpArray = new Class<?>[1];
				tmpArray[0] = Class.forName(((RpcSystemProxy) bean).getInterfaceName());
				proxyRoot.setInterfaces(tmpArray);
				// proxyRoot.addInterface(Class.forName(((RpcSystemProxy)bean).getInterfaceName()));
				// return
				// Main.origin.getProxyObject(Class.forName(((RpcSystemProxy)bean).getInterfaceName()));
				return proxyRoot.getObject();
			}else if (bean instanceof AppServiceHandler){
				AppServiceHandler handler=(AppServiceHandler) bean;
				processorRegister.regist(handler, handler.getTargetUrl());
				//TODO 打印AppService注册日志
				System.out.println("完成注册appService: "+beanName+"url: "+handler.getTargetUrl());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return bean;
	}

	public ProcessorRegister getProcessorRegister() {
		return processorRegister;
	}

	public void setProcessorRegister(ProcessorRegister processorRegister) {
		this.processorRegister = processorRegister;
	}

}
