package com.hawklithm.cerberus.executor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;

import com.hawklithm.cerberus.protocol.FrontEndingCommunicationProtocol;
import com.hawklithm.cerberus.protocol.FrontEndingRequestCondition;
import com.hawklithm.cerberus.protocol.OperateTypeConstant;
import com.multiagent.hawklithm.vehicle.DO.VehicleInfoDO;
import com.multiagent.hawklithm.vehicle.interface4rpc.RPCVehicleInfoManagerInterface;

public class VehicleManagerExecutor implements FrontEndingCommunicationExecutor{

	private RPCVehicleInfoManagerInterface vehicleInfoManager;
	
	@Override
	public FrontEndingCommunicationProtocol<Map<String,Object>>  execute(FrontEndingCommunicationProtocol<Map<String,Object>> message)
			throws ServletException, IOException {
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
			} else if (message.getOperateType().equals(OperateTypeConstant.OPERATE_MODIFY)) {
				update(message);
				result.setStatusOk();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	protected void query(FrontEndingCommunicationProtocol<Map<String,Object>> message,
			FrontEndingCommunicationProtocol<Map<String,Object>> result) throws Exception {
		if (message.getRows().size() > 1) {
			throw new Exception("查询条件只能包含一个订单信息");
		}
		VehicleInfoDO msg = getOrderCluster(message).get(0);
		VehicleInfoDO[] infos = vehicleInfoManager.queryByAllInfo(msg.getVehicleId(),
				msg.getVehicleType(), msg.getVehicleLevel(), msg.getVehicleCapacity(),
				message.getOffset(), message.getLength());
		for (VehicleInfoDO index : infos) {
			result.getRows().add(getFrontEndingRequest(index).toMapping());
		}
	}

	private FrontEndingRequestCondition getFrontEndingRequest(VehicleInfoDO info) {
		FrontEndingRequestCondition condition = new FrontEndingRequestCondition();
		condition.getCondition().put("vehicleId", info.getVehicleId());
		condition.getCondition().put("vehicleType", info.getVehicleType());
		condition.getCondition().put("vehicleLevel", info.getVehicleLevel());
		condition.getCondition().put("vehicleCapacity", info.getVehicleCapacity());
		return condition;
	}

	protected void delete(FrontEndingCommunicationProtocol<Map<String,Object>> message)
			throws Exception {
		for (Map<String,Object> index : message.getRows()) {
			FrontEndingRequestCondition request=new FrontEndingRequestCondition(index);
			if (request.getCondition().containsKey("vehicleId")) {
				vehicleInfoManager.deleteVehicleInformation((Integer) request.getCondition().get(
						"vehicleId"));
			}
		}
	}

	protected void update(FrontEndingCommunicationProtocol<Map<String,Object>> message)
			throws Exception {
		List<VehicleInfoDO> infos = getOrderCluster(message);
		for (VehicleInfoDO info : infos) {
			vehicleInfoManager.modifyVehicleInfomation(info.getVehicleId(), info.getVehicleType(),
					info.getVehicleLevel(), info.getVehicleCapacity());
		}
	}

	protected void submit(FrontEndingCommunicationProtocol<Map<String,Object>> message)
			throws Exception {
		List<VehicleInfoDO> infos = getOrderCluster(message);
		for (VehicleInfoDO info : infos) {
			vehicleInfoManager.addVehicleInformation(info.getVehicleType(), info.getVehicleLevel(),
					info.getVehicleCapacity());
		}
	}

	protected List<VehicleInfoDO> getOrderCluster(
			FrontEndingCommunicationProtocol<Map<String,Object>> message) throws Exception {
		if (message.getRows().size() == 0) {
			throw new Exception("数据中没有信息");
		}
		if (message.getRows().size() != 1
				&& message.getOperateType().equals(OperateTypeConstant.OPERATE_QUERY)) {
			throw new Exception("一次查询只能包含一个订单条件");
		}
		List<VehicleInfoDO> ret = new ArrayList<VehicleInfoDO>();
		for (Map<String,Object> index : message.getRows()) {
			FrontEndingRequestCondition msg=new FrontEndingRequestCondition(index);
			VehicleInfoDO info = new VehicleInfoDO();
			if (msg.getCondition().containsKey("vehicleId")) {
				info.setVehicleId((Integer) msg.getCondition().get("vehicleId"));
			}
			if (msg.getCondition().containsKey("vehicleType")) {
				info.setVehicleType((String) msg.getCondition().get("vehicleType"));
			}
			if (msg.getCondition().containsKey("vehicleLevel")) {
				info.setVehicleLevel((Integer) msg.getCondition().get("vehicleLevel"));
			}
			if (msg.getCondition().containsKey("vehicleCapacity")) {
				info.setVehicleCapacity((Double) msg.getCondition().get("vehicleCapacity"));
			}
			ret.add(info);
		}
		return ret;
	}

	public RPCVehicleInfoManagerInterface getVehicleInfoManager() {
		return vehicleInfoManager;
	}

	public void setVehicleInfoManager(RPCVehicleInfoManagerInterface vehicleInfoManager) {
		this.vehicleInfoManager = vehicleInfoManager;
	}

}
