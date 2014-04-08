package com.hawklithm.cerberus.protocol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FrontEndingCommunicationProtocol<T> {
	// private String status;
	// private String tableType;
	// private String operateType;
	// private int index;
	// private int offset;
	private static final String OPERATE_TYPE = "operateType", TABLE_TYPE = "tableType",
			STATUS = "status", LENGTH = "lengths", OFFSET = "offset", RETURN_STATE_OK = "ok",
			RETURN_STATE_FAIL_STRING = "fail";
	private Map<String, Object> condition = new HashMap<String, Object>();
	private List<T> rows = new ArrayList<T>();


	public Map<String, Object> getCondition() {
		return condition;
	}

	public void setCondition(Map<String, Object> condition) {
		this.condition = condition;
	}

	public String getTableName() {
		return (String) condition.get(TABLE_TYPE);
	}

	public String getOperateType() {
		return (String) condition.get(OPERATE_TYPE);
	}

	public String getStatus() {
		return (String) condition.get(STATUS);
	}

	public void setStatusOk() {
		condition.put(STATUS, RETURN_STATE_OK);
	}

	public void setStatusFail() {
		condition.put(STATUS, RETURN_STATE_FAIL_STRING);
	}

	public Integer getLength() {
		if (condition.containsKey(LENGTH)){
		return (Integer) condition.get(LENGTH);
		}else {
			return 15;
		}
	}

	public Integer getOffset() {
		if (condition.containsKey(OFFSET)){
		return (Integer) condition.get(OFFSET);
		}else {
			return 0;
		}
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

}
