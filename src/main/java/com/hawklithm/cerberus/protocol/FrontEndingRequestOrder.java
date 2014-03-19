package com.hawklithm.cerberus.protocol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class FrontEndingRequestOrder {
	private static String MAIN_OBJECT="main",SUB_OBJECT="subs",REFLECT_OBJECT="reflects";
	private HashMap<String, Object> main;
	private ArrayList<HashMap<String, Object>> subs;
	private ArrayList<HashMap<String, Object>> reflects;
	
	public FrontEndingRequestOrder(){
		main = new HashMap<String, Object>();
		subs = new ArrayList<HashMap<String, Object>>();
		reflects = new ArrayList<HashMap<String, Object>>();
	}
	public FrontEndingRequestOrder(Map<String,Object> map){
		main=(HashMap<String, Object>) map.get(MAIN_OBJECT);
		subs=(ArrayList<HashMap<String, Object>>) map.get(SUB_OBJECT);
		reflects=(ArrayList<HashMap<String, Object>>) map.get(REFLECT_OBJECT);
	}
	public Map<String,Object> toMapping(){
		Map<String,Object> ret=new LinkedHashMap<String,Object>();
		ret.put(MAIN_OBJECT, main);
		ret.put(SUB_OBJECT, subs);
		ret.put(REFLECT_OBJECT, reflects);
		return ret;
	}

	public void addSubOrder(HashMap<String, Object> order) {
		subs.add(order);
	}

	public void addReflectOrder(HashMap<String, Object> order) {
		reflects.add(order);
	}

	public HashMap<String, Object> getMain() {
		return main;
	}

	public void setMain(HashMap<String, Object> main) {
		this.main = main;
	}

	public ArrayList<HashMap<String, Object>> getSubs() {
		return subs;
	}

	public void setSubs(ArrayList<HashMap<String, Object>> subs) {
		this.subs = subs;
	}

	public ArrayList<HashMap<String, Object>> getReflects() {
		return reflects;
	}

	public void setReflects(ArrayList<HashMap<String, Object>> reflects) {
		this.reflects = reflects;
	}
}
