package com.multiagent.hawklithm.transport.interface4rpc;

import java.util.Date;

import com.multiagent.hawklithm.transport.DO.SqlTransportOrderClusterDO;
import com.multiagent.hawklithm.transport.DO.TempTransportOrderClusterDO;

public interface RPCTransportOrderManagerInterface {

	void submitOrder(TempTransportOrderClusterDO clusterDO) throws Exception;

	void deleteOrder(int orderId) throws Exception;

	SqlTransportOrderClusterDO queryOrderByOrderId(int id) throws Exception;

	SqlTransportOrderClusterDO queryOrderByTransportOrderId(int id) throws Exception;

	SqlTransportOrderClusterDO[] queryOrderByInfo(Date startTime, Date finishTime,
			String startAddress, String endAddress, Integer vehicleId) throws Exception;
}
