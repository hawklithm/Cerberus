package com.multiagent.hawklithm.transport.DO;

import java.util.ArrayList;
import java.util.List;

public class TransportWithGPSInfoClusterDO {
	private SqlTransportMainOrderDO mainOrder;
	private List<TransportWithGPSInfoSubOrderDO> subOrder = new ArrayList<TransportWithGPSInfoSubOrderDO>();
	
	public TransportWithGPSInfoClusterDO(){
		
	}
	public TransportWithGPSInfoClusterDO(SqlTransportOrderClusterDO cluster){
		setMainOrder(cluster.getMainOrder());
		for (SqlTransportSubOrderDO index:cluster.getSubOrder()){
			subOrder.add(new TransportWithGPSInfoSubOrderDO(index));
		}
	}

	public List<TransportWithGPSInfoSubOrderDO> getSubOrder() {
		return subOrder;
	}

	public void setSubOrder(List<TransportWithGPSInfoSubOrderDO> subOrder) {
		this.subOrder = subOrder;
	}

	public SqlTransportMainOrderDO getMainOrder() {
		return mainOrder;
	}

	public void setMainOrder(SqlTransportMainOrderDO mainOrder) {
		this.mainOrder = mainOrder;
	}
}
