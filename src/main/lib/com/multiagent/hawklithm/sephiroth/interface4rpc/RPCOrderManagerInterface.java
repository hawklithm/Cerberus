package com.multiagent.hawklithm.sephiroth.interface4rpc;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.multiagent.hawklithm.sephiroth.DO.SqlPackageOrderClusterDO;
import com.multiagent.hawklithm.sephiroth.DO.TempPackageOrderClusterDO;

/**
 * RPC�����ӿڣ�������ִ��
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
			put(ORDER_STATUS_SUBMITEDS,"���µ�δ����");
			put(ORDER_STATUS_RECLAIM, "�ѻ���δ���");
			put(ORDER_STATUS_STORAGED, "�ѳ���δǩ��");
			put(ORDER_STATUS_ASSORTING, "�����δ����");
			put(ORDER_STATUS_SIGNED, "��ǩ��");
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
