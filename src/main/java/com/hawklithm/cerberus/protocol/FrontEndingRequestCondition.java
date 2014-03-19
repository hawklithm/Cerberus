package com.hawklithm.cerberus.protocol;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class FrontEndingRequestCondition {
	private Map<String, Object> condition;
	static private  String CONDITION_OBJECT="condition";
	
	public FrontEndingRequestCondition(){
		 condition= new HashMap<String, Object>();
	}
	public FrontEndingRequestCondition(Map<String,Object> map){
		condition=(Map<String, Object>) map.get(CONDITION_OBJECT);
	}
	public Map<String,Object> toMapping(){
		Map<String,Object> ret=new LinkedHashMap<String,Object>();
		ret.put(CONDITION_OBJECT, condition);
		return ret;
	}

	public Map<String, Object> getCondition() {
		return condition;
	}

	public void setCondition(Map<String, Object> condition) {
		this.condition = condition;
	}
}
