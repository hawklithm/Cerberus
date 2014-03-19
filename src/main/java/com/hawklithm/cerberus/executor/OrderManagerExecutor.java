package com.hawklithm.cerberus.executor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;

import com.hawklithm.cerberus.protocol.FrontEndingCommunicationProtocol;
import com.hawklithm.cerberus.protocol.FrontEndingRequestOrder;
import com.hawklithm.cerberus.protocol.OperateTypeConstant;
import com.hawklithm.cerberus.protocol.ProtocolUtils;
import com.hawklithm.cerberus.servlet.MessageTranslateServlet;
import com.multiagent.hawklithm.sephiroth.DO.SqlMainPackageOrderDO;
import com.multiagent.hawklithm.sephiroth.DO.SqlPackageOrderClusterDO;
import com.multiagent.hawklithm.sephiroth.DO.SqlReflectOrderDO;
import com.multiagent.hawklithm.sephiroth.DO.SqlSubPackageOrderDO;
import com.multiagent.hawklithm.sephiroth.DO.TempMainPackageOrderDO;
import com.multiagent.hawklithm.sephiroth.DO.TempPackageOrderClusterDO;
import com.multiagent.hawklithm.sephiroth.DO.TempReflectOrderDO;
import com.multiagent.hawklithm.sephiroth.DO.TempSubPackageOrderDO;
import com.multiagent.hawklithm.sephiroth.interface4rpc.RPCOrderManagerInterface;

/**
 * 
 * @author BlueHawky
 *
 */
public class OrderManagerExecutor implements FrontEndingCommunicationExecutor{


	private RPCOrderManagerInterface orderManager;

	@Override
	public FrontEndingCommunicationProtocol<Map<String,Object>/*FrontEndingRequestOrder*/> execute(FrontEndingCommunicationProtocol<Map<String,Object>/*FrontEndingRequestOrder*/> message)
			throws ServletException, IOException {
		List<TempPackageOrderClusterDO> orderClusters;
		FrontEndingCommunicationProtocol<Map<String,Object>> result = new FrontEndingCommunicationProtocol<Map<String,Object>>();
		try {
			if (message.getOperateType().equals(OperateTypeConstant.OPERATE_SUBMIT)) {
				orderClusters = getOrderCluster(message);
				for (TempPackageOrderClusterDO orderCluster : orderClusters) {
					orderManager.submitOrder(orderCluster);
				}
				result.setStatusOk();
			} else if (message.getOperateType().equals(OperateTypeConstant.OPERATE_DELETE)) {
				// orderClusters = getOrderCluster(message);
				for (Map<String,Object> index : message.getRows()) {
					FrontEndingRequestOrder request=new FrontEndingRequestOrder(index);
					orderManager.deleteOrder((int) request.getMain().get("orderId"));
				}
				// for (TempPackageOrderClusterDO orderCluster : orderClusters)
				// {
				// orderManager.deleteOrder(orderCluster.getMainOrder().getOrderId());
				// }
				result.setStatusOk();
			} else if (message.getOperateType().equals(OperateTypeConstant.OPERATE_QUERY)) {
				if (message.getRows().size() > 1) {
					throw new Exception("查询条件只能包含一个订单信息");
				}
				FrontEndingRequestOrder msg =new FrontEndingRequestOrder(message.getRows().get(0));
				Integer orderId = (Integer) msg.getMain().get("orderId");
				if (orderId != null) {
					result.getRows()
							.add(getFrontEndingRequestOrder(orderManager
									.selectOrdersByOrderId(orderId)).toMapping());
				} else {
					SqlPackageOrderClusterDO[] clusters = orderManager.selectOrdersByInfo(
							(Integer) msg.getMain().get("userId"),
							(Date) msg.getMain().get("startDay"), (Date) msg.getMain()
									.get("endDay"), (String) msg.getMain().get("orderStatus"),
							message.getOffset(), message.getLength());
					if (clusters!=null){
						for (SqlPackageOrderClusterDO index : clusters) {
							result.getRows().add(getFrontEndingRequestOrder(index).toMapping());
						}
					}
				}
				result.setStatusOk();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	protected FrontEndingRequestOrder getFrontEndingRequestOrder(SqlPackageOrderClusterDO cluster) {
		FrontEndingRequestOrder ret = new FrontEndingRequestOrder();
		for (SqlReflectOrderDO index : cluster.getReflectOrders()) {
			ret.addReflectOrder(getMapFromReflectOrder(index));
		}
		for (SqlSubPackageOrderDO index : cluster.getSubOrders()) {
			ret.addSubOrder(getMapFromSubPackageOrder(index));
		}
		ret.setMain(getMapFromMainPackageOrder(cluster.getMainOrder()));
		return ret;
	}

	protected List<TempPackageOrderClusterDO> getOrderCluster(
			FrontEndingCommunicationProtocol<Map<String,Object>> order) throws Exception {
		if (order.getRows().size() == 0) {
			throw new Exception("数据中没有订单信息");
		}
		if (order.getOperateType().equals(OperateTypeConstant.OPERATE_SUBMIT)
				&& order.getRows().size() != 1) {
			throw new Exception("拒绝一次下多个订单");
		}
		if (order.getRows().size() != 1
				&& order.getOperateType().equals(OperateTypeConstant.OPERATE_QUERY)) {
			throw new Exception("一次查询只能包含一个订单条件");
		}
		List<TempPackageOrderClusterDO> ret = new ArrayList<TempPackageOrderClusterDO>();
		TempPackageOrderClusterDO orderCluster;
		for (Map<String,Object> index : order.getRows()) {
			FrontEndingRequestOrder request=new FrontEndingRequestOrder(index);
			orderCluster = new TempPackageOrderClusterDO();
			orderCluster.setMainOrder(getMainOrderFromMap(request.getMain()));
			for (Map<String, Object> map : request.getSubs()) {
				orderCluster.addSubOrder(getSubPackageOrderFromMap(map));
			}
			for (Map<String, Object> map : request.getReflects()) {
				orderCluster.addReflectOrder(getReflectOrderFromMap(map));
			}
			ret.add(orderCluster);
		}
		return ret;
	}

	protected HashMap<String, Object> getMapFromReflectOrder(SqlReflectOrderDO order) {
		HashMap<String, Object> ret = new HashMap<String, Object>();
		ProtocolUtils.notNullSet(ret, "itemAmount", order.getItemAmount());
		ProtocolUtils.notNullSet(ret, "itemType", order.getItemType());
		ProtocolUtils.notNullSet(ret, "parentId", order.getParentId());
		return ret;
	}

	protected HashMap<String, Object> getMapFromSubPackageOrder(SqlSubPackageOrderDO order) {
		HashMap<String, Object> ret = new HashMap<String, Object>();
		ProtocolUtils.notNullSet(ret, "parentId", order.getParentId());
		ProtocolUtils.notNullSet(ret, "packageType", order.getPackageType());
		ProtocolUtils.notNullSet(ret, "packageAmount", order.getPackageAmount());
		return ret;
	}

	protected HashMap<String, Object> getMapFromMainPackageOrder(SqlMainPackageOrderDO order) {
		HashMap<String, Object> ret = new HashMap<String, Object>();
		ProtocolUtils.notNullSet(ret, "userId", order.getUserId());
		ProtocolUtils.notNullSet(ret, "orderTime", order.getOrderTime());
		ProtocolUtils.notNullSet(ret, "orderStatus", order.getOrderStatus());
		ProtocolUtils.notNullSet(ret, "expectReceiveTime", order.getExpectReceiveTime());
		ProtocolUtils.notNullSet(ret, "level", order.getLevel());
		ProtocolUtils.notNullSet(ret, "orderNote", order.getOrderNote());
		ProtocolUtils.notNullSet(ret, "orderId", order.getOrderId());
		return ret;
	}

	protected TempReflectOrderDO getReflectOrderFromMap(Map<String, Object> paramMap) {
		TempReflectOrderDO reflectOrder = new TempReflectOrderDO();
		reflectOrder.setParentId((Integer) paramMap.get("parentId"));
		reflectOrder.setItemType((Integer) paramMap.get("itemType"));
		reflectOrder.setItemAmount((Integer) paramMap.get("itemAmount"));
		return reflectOrder;
	}

	protected TempSubPackageOrderDO getSubPackageOrderFromMap(Map<String, Object> paramMap) {
		TempSubPackageOrderDO subOrder = new TempSubPackageOrderDO();
		subOrder.setParentId((Integer) paramMap.get("parentId"));
		subOrder.setPackageType((Integer) paramMap.get("packageType"));
		subOrder.setPackageAmount((Integer) paramMap.get("packageAmount"));
		return subOrder;
	}

	protected TempMainPackageOrderDO getMainOrderFromMap(Map<String, Object> paramMap) {
		TempMainPackageOrderDO mainOrder = new TempMainPackageOrderDO();
		mainOrder.setUserId((Integer) paramMap.get("userId"));
		mainOrder.setOrderTime((Date) paramMap.get("orderTime"));
		mainOrder.setOrderStatus((String) paramMap.get("orderStatus"));
		mainOrder.setExpectReceiveTime((Date) paramMap.get("expectReceiveTime"));
		mainOrder.setLevel((Integer) paramMap.get("level"));
		mainOrder.setOrderNote((String) paramMap.get("orderNote"));
		return mainOrder;
	}

	public RPCOrderManagerInterface getOrderManager() {
		return orderManager;
	}

	public void setOrderManager(RPCOrderManagerInterface orderManager) {
		this.orderManager = orderManager;
	}

}
