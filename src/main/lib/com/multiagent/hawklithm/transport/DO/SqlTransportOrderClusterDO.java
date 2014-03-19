package com.multiagent.hawklithm.transport.DO;

import java.util.ArrayList;
import java.util.List;
//
//public class SqlTransportOrderClusterDO {
//	private SqlTransportMainOrderDO mainOrder;
//	private List<SqlTransportSubOrderCombineWithGPSDO> subOrder;
//
//	public SqlTransportMainOrderDO getMainOrder() {
//		return mainOrder;
//	}
//
//	public void setMainOrder(SqlTransportMainOrderDO mainOrder) {
//		this.mainOrder = mainOrder;
//	}
//
//	public List<SqlTransportSubOrderCombineWithGPSDO> getSubOrder() {
//		return subOrder;
//	}
//
//	public void setSubOrder(List<SqlTransportSubOrderCombineWithGPSDO> subOrder) {
//		this.subOrder = subOrder;
//	}
//}

public class SqlTransportOrderClusterDO {
	private SqlTransportMainOrderDO mainOrder;
	private List<SqlTransportSubOrderDO> subOrder = new ArrayList<SqlTransportSubOrderDO>();

	public SqlTransportMainOrderDO getMainOrder() {
		return mainOrder;
	}

	public void setMainOrder(SqlTransportMainOrderDO mainOrder) {
		this.mainOrder = mainOrder;
	}

	public List<SqlTransportSubOrderDO> getSubOrder() {
		return subOrder;
	}

	public void setSubOrder(List<SqlTransportSubOrderDO> subOrder) {
		this.subOrder = subOrder;
	}
}
