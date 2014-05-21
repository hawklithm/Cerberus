package com.multiagent.hawklithm.net.manager;

import java.util.LinkedHashMap;
import java.util.Map;

import com.hawklithm.cerberus.nettyservice.AppServiceHandler;
/*
 * 将名称和相应的处理类联系起来
 */
public class ProcessorRegister {
	Map<String,AppServiceHandler> router = new LinkedHashMap<String, AppServiceHandler>();

	public void regist(AppServiceHandler handler, String url) {
		router.put(url, handler);
	}
	public AppServiceHandler getAppService(String url){
		return router.get(url);
	}
}
