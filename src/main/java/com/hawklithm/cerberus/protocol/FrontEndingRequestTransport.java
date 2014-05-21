package com.hawklithm.cerberus.protocol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FrontEndingRequestTransport {
	/*
	 * 运单消息协议
	 * main运单
	 * subs(
	 * 
	 */
	public static String MAIN_OBJECT = "main";
	public static String SUBS_OBJECT = "subs";
	private Map<String, Object> main;// = new HashMap<String, Object>();
	private List<Map<String, Object>> subs;// = new ArrayList<Map<String,
											// Object>>();

	public FrontEndingRequestTransport() {
		main = new HashMap<String, Object>();
		subs = new ArrayList<Map<String, Object>>();
	}

	public FrontEndingRequestTransport(Map<String, Object> map) {
		main = (Map<String, Object>) map.get(MAIN_OBJECT);
		subs = (List<Map<String, Object>>) map.get(SUBS_OBJECT);
	}
	
	public Map<String,Object> toMapping(){
		Map<String,Object> ret=new LinkedHashMap<String,Object>();
		ret.put(MAIN_OBJECT, main);
		ret.put(SUBS_OBJECT, subs);
		return ret;
	}

	public void addSubOrder(Map<String, Object> order) {
		subs.add(order);
	}

	public Map<String, Object> getMain() {
		return main;
	}

	public void setMain(Map<String, Object> main) {
		this.main = main;
	}

	public List<Map<String, Object>> getSubs() {
		return subs;
	}

	public void setSubs(List<Map<String, Object>> subs) {
		this.subs = subs;
	}

}
