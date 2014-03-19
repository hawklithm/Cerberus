package com.multiagent.hawklithm.transport.DO;

import java.util.ArrayList;
import java.util.List;

public class TempTransportOrderClusterDO {
	private TempTransportMainOrderDO mainOrder;
	private List<TempTransportSubOrderDO> subOrder = new ArrayList<TempTransportSubOrderDO>();

	public TempTransportMainOrderDO getMainOrder() {
		return mainOrder;
	}

	public void setMainOrder(TempTransportMainOrderDO mainOrder) {
		this.mainOrder = mainOrder;
	}

	public List<TempTransportSubOrderDO> getSubOrder() {
		return subOrder;
	}

	public void setSubOrder(List<TempTransportSubOrderDO> subOrder) {
		this.subOrder = subOrder;
	}

	public void addSubOrder(TempTransportSubOrderDO order) {
		if (this.subOrder == null) {
			this.subOrder = new ArrayList<TempTransportSubOrderDO>();
		}
		this.subOrder.add(order);
	}
}
