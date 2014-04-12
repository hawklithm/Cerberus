package com.hawklithm.cerberus.executor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;

import com.hawklithm.cerberus.protocol.FrontEndingCommunicationProtocol;
import com.hawklithm.cerberus.protocol.FrontEndingRequestCondition;
import com.hawklithm.cerberus.protocol.OperateTypeConstant;
import com.hawklithm.cerberus.protocol.StaffEquipmentMappingOperateTypeConstant;
import com.multiagent.hawklithm.staff.DO.ExStaffInfoDO;
import com.multiagent.hawklithm.staff.DO.StaffInfoDO;
import com.multiagent.hawklithm.staff.interface4rpc.RPCStaffInfoManagerInterface;

public class StaffManagerExecutor implements FrontEndingCommunicationExecutor{


	private RPCStaffInfoManagerInterface staffInfoManager;

	@Override
	public  FrontEndingCommunicationProtocol<Map<String,Object>>  execute(FrontEndingCommunicationProtocol<Map<String,Object>> message)
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
			}else if (message.getOperateType().equals(StaffEquipmentMappingOperateTypeConstant.OPERATE_LOGIN)){
				
			}else if (message.getOperateType().equals(StaffEquipmentMappingOperateTypeConstant.OPERATE_LOGOUT)){
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	protected void query(FrontEndingCommunicationProtocol<Map<String, Object>> message,
			FrontEndingCommunicationProtocol<Map<String, Object>> result) throws Exception {
		if (message.getRows().size() > 1) {
			throw new Exception("查询条件只能包含一个订单信息");
		}
		ExStaffInfoDO msg = getOrderCluster(message).get(0);
		if (msg.getEquipmentId() != null) {
			StaffInfoDO[] infos = staffInfoManager.queryByEquipmentId(msg.getEquipmentId());
			for (StaffInfoDO index : infos) {
				result.getRows().add(getFrontEndingRequest(index).toMapping());
			}
		} else {
			StaffInfoDO[] infos = staffInfoManager.queryByAllInfo(msg.getStaffId(),
					msg.getStaffName(), msg.getStaffPhoneNumber(), msg.getStaffGender(),
					msg.getStaffAgeStart(), msg.getStaffAgeEnd(), msg.getStaffDepartmentName(),
					message.getOffset(), message.getLength());
			for (StaffInfoDO index : infos) {
				result.getRows().add(getFrontEndingRequest(index).toMapping());
			}
		}
	}

	private FrontEndingRequestCondition getFrontEndingRequest(StaffInfoDO info) {
		FrontEndingRequestCondition condition = new FrontEndingRequestCondition();
		condition.getCondition().put("staffId", info.getStaffId());
		condition.getCondition().put("staffName", info.getStaffName());
		condition.getCondition().put("staffPhoneNumber", info.getStaffPhoneNumber());
		condition.getCondition().put("staffGender", info.getStaffGender());
		condition.getCondition().put("staffAge", info.getStaffAge());
		condition.getCondition().put("staffDepartmentName", info.getStaffDepartmentName());
		condition.getCondition().put("userIconPath", info.getUserIconPath());
		return condition;
	}

	protected void delete(FrontEndingCommunicationProtocol<Map<String,Object>> message)
			throws Exception {
		for (Map<String,Object> index : message.getRows()) {
			FrontEndingRequestCondition request=new FrontEndingRequestCondition(index);
			if (request.getCondition().containsKey("staffId")) {
				staffInfoManager.delete((Integer) request.getCondition().get("staffId"));
			}
		}
	}

	protected void update(FrontEndingCommunicationProtocol<Map<String,Object>> message)
			throws Exception {
		List<ExStaffInfoDO> infos = getOrderCluster(message);
		for (StaffInfoDO info : infos) {
			staffInfoManager.modify(info.getStaffId(), info.getStaffName(),
					info.getStaffPhoneNumber(), info.getStaffGender(), info.getStaffAge(),
					info.getStaffDepartmentName());
		}
	}

	protected void submit(FrontEndingCommunicationProtocol<Map<String,Object>> message)
			throws Exception {
		List<ExStaffInfoDO> infos = getOrderCluster(message);
		for (StaffInfoDO info : infos) {
			staffInfoManager.submit(info.getStaffName(), info.getStaffPhoneNumber(),
					info.getStaffGender(), info.getStaffAge(), info.getStaffDepartmentName());
		}
	}

	protected List<ExStaffInfoDO> getOrderCluster(
			FrontEndingCommunicationProtocol<Map<String,Object>> message) throws Exception {
		if (message.getRows().size() == 0) {
			throw new Exception("数据中没有信息");
		}
		if (message.getRows().size() != 1
				&& message.getOperateType().equals(OperateTypeConstant.OPERATE_QUERY)) {
			throw new Exception("一次查询只能包含一个订单条件");
		}
		List<ExStaffInfoDO> ret = new ArrayList<ExStaffInfoDO>();
		for (Map<String,Object> index : message.getRows()) {
			FrontEndingRequestCondition msg=new FrontEndingRequestCondition(index);
			ExStaffInfoDO info = new ExStaffInfoDO();
			if (msg.getCondition().containsKey("staffAgeStart")) {
				info.setStaffAgeStart((Integer) msg.getCondition().get("staffAgeStart"));
			}
			if (msg.getCondition().containsKey("staffAgeEnd")) {
				info.setStaffAgeEnd((Integer) msg.getCondition().get("staffAgeEnd"));
			}
			if (msg.getCondition().containsKey("staffId")) {
				info.setStaffId((Integer) msg.getCondition().get("staffId"));
			}
			if (msg.getCondition().containsKey("staffName")) {
				info.setStaffName((String) msg.getCondition().get("staffName"));
			}
			if (msg.getCondition().containsKey("staffPhoneNumber")) {
				info.setStaffPhoneNumber((String) msg.getCondition().get("staffPhoneNumber"));
			}
			if (msg.getCondition().containsKey("staffGender")) {
				info.setStaffGender((String) msg.getCondition().get("staffGender"));
			}
			if (msg.getCondition().containsKey("staffAge")) {
				info.setStaffAge((Integer) msg.getCondition().get("staffAge"));
			}
			if (msg.getCondition().containsKey("staffDepartmentName")) {
				info.setStaffDepartmentName((String) msg.getCondition().get("staffDepartmentName"));
			}
			if (msg.getCondition().containsKey("equipmentId")){
				Object object=msg.getCondition().get("equipmentId");
				if (object instanceof Double){
					info.setEquipmentId(((Double)object).intValue());
				}else{
					info.setEquipmentId((Integer)object);
				}
			}
			if (msg.getCondition().containsKey("userIconPath")){
				info.setUserIconPath((String)msg.getCondition().get("userIconPath"));
			}
			ret.add(info);
		}
		return ret;
	}

	public RPCStaffInfoManagerInterface getStaffInfoManager() {
		return staffInfoManager;
	}

	public void setStaffInfoManager(RPCStaffInfoManagerInterface staffInfoManager) {
		this.staffInfoManager = staffInfoManager;
	}

}
