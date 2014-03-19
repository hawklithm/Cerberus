package com.multiagent.hawklithm.sephiroth.interface4rpc;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.multiagent.hawklithm.sephiroth.DO.SqlPackageOrderClusterDO;
import com.multiagent.hawklithm.sephiroth.DO.TempPackageOrderClusterDO;

/**
 * RPC订单接口，服务器执行
 * 
 * @author hawklithm
 * 
 */
public interface RPCOrderManagerInterface {
	public static String ORDER_STATUS_RECLAIM = "order_state_reclaim",
			ORDER_STATUS_STORAGED = "order_state_storaged",
			ORDER_STATUS_ASSORTING = "order_state_assorting",
			ORDER_STATUS_SIGNED = "order_state_signed",
			ORDER_STATUS_SUBMITEDS = "order_state_submited";

	public static Map<String, String> ORDER_STATUS_MAPPING = new HashMap<String, String>() {
		private static final long serialVersionUID = -7478175425827764450L;
		{
			put(ORDER_STATUS_SUBMITEDS,"已下单未回收");
			put(ORDER_STATUS_RECLAIM, "已回收未配货");
			put(ORDER_STATUS_STORAGED, "已出库未签收");
			put(ORDER_STATUS_ASSORTING, "已配货未出库");
			put(ORDER_STATUS_SIGNED, "已签收");
		}
	};

	public void submitOrder(TempPackageOrderClusterDO clusterDO);

	public double calculateOrderClusterPrice(TempPackageOrderClusterDO clusterDO);

	public void deleteOrder(int orderId);

	SqlPackageOrderClusterDO[] selectOrdersByUserId(Integer userId, Integer offset, Integer length);

	SqlPackageOrderClusterDO selectOrdersByOrderId(Integer orderId);

	SqlPackageOrderClusterDO[] selectOrdersByInfo(Integer userId, Date startDay, Date endDay,
			String orderStatus, Integer offset, Integer length);
}
