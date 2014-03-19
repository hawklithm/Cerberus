package com.hawklithm.cerberus.executor;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hawklithm.cerberus.protocol.FrontEndingCommunicationProtocol;
import com.hawklithm.cerberus.protocol.FrontEndingRequestTransport;
import com.hawklithm.cerberus.protocol.OperateTypeConstant;
import com.hawklithm.cerberus.protocol.ProtocolUtils;
import com.multiagent.hawklithm.gps.DO.GpsInfoDO;
import com.multiagent.hawklithm.gps.interface4rpc.RPCGpsInfoManagerInterface;
import com.multiagent.hawklithm.transport.DO.SqlTransportMainOrderDO;
import com.multiagent.hawklithm.transport.DO.SqlTransportOrderClusterDO;
import com.multiagent.hawklithm.transport.DO.SqlTransportSubOrderDO;
import com.multiagent.hawklithm.transport.DO.TempTransportMainOrderDO;
import com.multiagent.hawklithm.transport.DO.TempTransportOrderClusterDO;
import com.multiagent.hawklithm.transport.DO.TempTransportSubOrderDO;
import com.multiagent.hawklithm.transport.DO.TransportWithGPSInfoClusterDO;
import com.multiagent.hawklithm.transport.DO.TransportWithGPSInfoSubOrderDO;
import com.multiagent.hawklithm.transport.interface4rpc.RPCTransportOrderManagerInterface;

public class TransportManagerExecutor implements FrontEndingCommunicationExecutor{

	private RPCTransportOrderManagerInterface transportManager;
	private RPCGpsInfoManagerInterface gpsManager;
	public RPCGpsInfoManagerInterface getGpsManager() {
		return gpsManager;
	}

	public void setGpsManager(RPCGpsInfoManagerInterface gpsManager) {
		this.gpsManager = gpsManager;
	}

	
	public  FrontEndingCommunicationProtocol<Map<String,Object>>  execute(FrontEndingCommunicationProtocol<Map<String, Object>> message){
		FrontEndingCommunicationProtocol<Map<String,Object>> result = new FrontEndingCommunicationProtocol<Map<String,Object>>();
		try {
			if (message.getOperateType().equals(OperateTypeConstant.OPERATE_SUBMIT)) {
				submit(message);
				result.setStatusOk();
			} else if (message.getOperateType().equals(OperateTypeConstant.OPERATE_DELETE)) {
				delete(message);
				result.setStatusOk();
			} else if (message.getOperateType().equals(OperateTypeConstant.OPERATE_QUERY)) {
				query(message, result);
				result.setStatusOk();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}


	protected void query(FrontEndingCommunicationProtocol<Map<String, Object>> message,
			FrontEndingCommunicationProtocol<Map<String,Object>> result) throws Exception {
		if (message.getRows().size() > 1) {
			throw new Exception("查询条件只能包含一个订单信息");
		}
		FrontEndingRequestTransport msg = new FrontEndingRequestTransport(message.getRows().get(0));
		Integer orderId = (Integer) ProtocolUtils.notNullGet(msg.getMain(),"orderId");
		if (orderId != null) {
			SqlTransportOrderClusterDO cluster = transportManager.queryOrderByOrderId(orderId);
			result.getRows().add(getFrontEndingRequestOrder(addGPSInfoToTransportCluster(cluster)).toMapping());
		} else {
			SqlTransportOrderClusterDO[] clusters = transportManager.queryOrderByInfo(
					(Date) ProtocolUtils.notNullGet(msg.getMain(), "startTime"),
					(Date) ProtocolUtils.notNullGet(msg.getMain(), "finishTime"),
					(String) ProtocolUtils.notNullGet(msg.getMain(), "startAddress"),
					(String) ProtocolUtils.notNullGet(msg.getMain(), "endAddress"),
					(Integer) ProtocolUtils.notNullGet(msg.getMain(), "vehicleId"));
			for (SqlTransportOrderClusterDO index : clusters) {
				result.getRows().add(getFrontEndingRequestOrder(addGPSInfoToTransportCluster(index)).toMapping());
			}
		}
	}
	
	protected TransportWithGPSInfoClusterDO addGPSInfoToTransportCluster(SqlTransportOrderClusterDO cluster){
		TransportWithGPSInfoClusterDO clusterWithGPS=new TransportWithGPSInfoClusterDO(cluster);
		for (TransportWithGPSInfoSubOrderDO index:clusterWithGPS.getSubOrder()){
			GpsInfoDO[] gpss=gpsManager.queryBySubTransId(index.getId());
			index.setGps(gpss);
		}
		return clusterWithGPS;
	}

	protected void delete(FrontEndingCommunicationProtocol<Map<String, Object>> message)
			throws Exception {
		for (Map<String, Object> msg : message.getRows()) {
			FrontEndingRequestTransport index = new FrontEndingRequestTransport(msg);
			transportManager.deleteOrder((Integer) ProtocolUtils.notNullGet(index.getMain(),
					"orderId"));
		}
	}

	/**
	 * 运单提交只允许提交主订单，子订单的相关信息在货物进行装车的时候由读卡器读入
	 * 
	 * @param message
	 * @throws Exception
	 */
	protected void submit(FrontEndingCommunicationProtocol<Map<String, Object>> message)
			throws Exception {
		List<TempTransportOrderClusterDO> orderClusters = getOrderCluster(message);
		for (TempTransportOrderClusterDO orderCluster : orderClusters) {
			transportManager.submitOrder(orderCluster);
		}
	}

	protected List<TempTransportOrderClusterDO> getOrderCluster(
			FrontEndingCommunicationProtocol<Map<String, Object>> order) throws Exception {
		if (order.getRows().size() == 0) {
			throw new Exception("数据中没有订单信息");
		}
		if (order.getRows().size() != 1
				&& order.getOperateType().equals(OperateTypeConstant.OPERATE_QUERY)) {
			throw new Exception("一次查询只能包含一个订单条件");
		}
		List<TempTransportOrderClusterDO> ret = new ArrayList<TempTransportOrderClusterDO>();
		TempTransportOrderClusterDO orderCluster;
		for (Map<String, Object> index : order.getRows()) {
			FrontEndingRequestTransport request = new FrontEndingRequestTransport(index);
			orderCluster = new TempTransportOrderClusterDO();
			orderCluster.setMainOrder(getMainOrderFromMap(request.getMain()));
			for (Map<String, Object> map : request.getSubs()) {
				orderCluster.addSubOrder(getSubOrderFromMap(map));
			}
			ret.add(orderCluster);
		}
		return ret;
	}
	protected FrontEndingRequestTransport getFrontEndingRequestOrder(
			TransportWithGPSInfoClusterDO cluster) {
		FrontEndingRequestTransport ret = new FrontEndingRequestTransport();
		for (TransportWithGPSInfoSubOrderDO index : cluster.getSubOrder()) {
			ret.addSubOrder(getMapFromSubOrder(index));
		}
		ret.setMain(getMapFromMainOrder(cluster.getMainOrder()));
		return ret;
	}

	protected FrontEndingRequestTransport getFrontEndingRequestOrder(
			SqlTransportOrderClusterDO cluster) {
		FrontEndingRequestTransport ret = new FrontEndingRequestTransport();
		for (SqlTransportSubOrderDO index : cluster.getSubOrder()) {
			ret.addSubOrder(getMapFromSubOrder(index));
		}
		ret.setMain(getMapFromMainOrder(cluster.getMainOrder()));
		return ret;
	}

	protected Map<String, Object> getMapFromMainOrder(SqlTransportMainOrderDO order) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (order==null){
			return map;
		}
		ProtocolUtils.notNullSet(map, "id", order.getId());
		ProtocolUtils.notNullSet(map, "orderId", order.getOrderId());
		ProtocolUtils.notNullSet(map, "startAddress", order.getStartAddress());
		ProtocolUtils.notNullSet(map, "endAddress", order.getEndAddress());
		ProtocolUtils.notNullSet(map, "startTime", order.getStartTime());
		ProtocolUtils.notNullSet(map, "finishTime", order.getFinishTime());
		return map;
	}
protected Map<String, Object> getMapFromSubOrder(TransportWithGPSInfoSubOrderDO order) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (order==null){
			return map;
		}
		ProtocolUtils.notNullSet(map, "id", order.getId());
		ProtocolUtils.notNullSet(map, "parentId", order.getParentId());
		ProtocolUtils.notNullSet(map, "subOrderId", order.getSubOrderId());
		ProtocolUtils.notNullSet(map, "vehicleId", order.getVehicleId());
		ProtocolUtils.notNullSet(map, "gps", order.getGps());
		return map;
	}
	protected Map<String, Object> getMapFromSubOrder(SqlTransportSubOrderDO order) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (order==null){
			return map;
		}
		ProtocolUtils.notNullSet(map, "id", order.getId());
		ProtocolUtils.notNullSet(map, "parentId", order.getParentId());
		ProtocolUtils.notNullSet(map, "subOrderId", order.getSubOrderId());
		ProtocolUtils.notNullSet(map, "vehicleId", order.getVehicleId());
		return map;
	}

	protected TempTransportMainOrderDO getMainOrderFromMap(Map<String, Object> paramMap) {
		TempTransportMainOrderDO mainOrder = new TempTransportMainOrderDO();
		mainOrder.setOrderId((Integer) paramMap.get("orderId"));
		mainOrder.setStartTime((Date) paramMap.get("startTime"));
		mainOrder.setFinishTime((Date) paramMap.get("finishTime"));
		mainOrder.setStartAddress((String) paramMap.get("startAddress"));
		mainOrder.setEndAddress((String) paramMap.get("endAddress"));
		return mainOrder;
	}

	protected TempTransportSubOrderDO getSubOrderFromMap(Map<String, Object> paramMap) {
		TempTransportSubOrderDO subOrder = new TempTransportSubOrderDO();
		subOrder.setVehicleId((Integer) paramMap.get("vehicleId"));
		subOrder.setSubOrderId((Integer) paramMap.get("subOrderId"));
		return subOrder;
	}

	public RPCTransportOrderManagerInterface getTransportManager() {
		return transportManager;
	}

	public void setTransportManager(RPCTransportOrderManagerInterface transportManager) {
		this.transportManager = transportManager;
	}


}
