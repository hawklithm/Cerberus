package com.hawklithm.cerberus.executor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;

import com.hawklithm.cerberus.protocol.FrontEndingCommunicationProtocol;
import com.hawklithm.cerberus.protocol.FrontEndingRequestCondition;
import com.hawklithm.cerberus.protocol.OperateTypeConstant;
import com.multiagent.hawklithm.hospital.DO.HospitalInfoDO;
import com.multiagent.hawklithm.hospital.interface4rpc.RPCHospitalInfoManagerInterface;

public class HospitalManagerExecutor implements FrontEndingCommunicationExecutor{


	private RPCHospitalInfoManagerInterface hospitalInfoManager;


	@Override
	public FrontEndingCommunicationProtocol<Map<String,Object>> execute(FrontEndingCommunicationProtocol<Map<String,Object>> message )
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
		HospitalInfoDO msg = getOrderCluster(message).get(0);
		HospitalInfoDO[] infos = hospitalInfoManager.query(msg.getHospitalId(),
				msg.getHospitalName(), msg.getHospitalLevel(), msg.getHospitalAddress(),
				msg.getHospitalAgent(), msg.getAgentPhone(), message.getOffset(),
				message.getLength());
		for (HospitalInfoDO index : infos) {
			result.getRows().add(getFrontEndingRequest(index).toMapping());
		}
	}

	private FrontEndingRequestCondition getFrontEndingRequest(HospitalInfoDO info) {
		FrontEndingRequestCondition condition = new FrontEndingRequestCondition();
		condition.getCondition().put("hospitalId", info.getHospitalId());
		condition.getCondition().put("hospitalName", info.getHospitalName());
		condition.getCondition().put("hospitalLevel", info.getHospitalLevel());
		condition.getCondition().put("hospitalAddress", info.getHospitalAddress());
		condition.getCondition().put("hospitalAgent", info.getHospitalAgent());
		condition.getCondition().put("agentPhone", info.getAgentPhone());
		return condition;
	}

	protected void delete(FrontEndingCommunicationProtocol<Map<String,Object>> message)
			throws Exception {
		for (Map<String,Object> index : message.getRows()) {
			FrontEndingRequestCondition request=new FrontEndingRequestCondition(index);
			if (request.getCondition().containsKey("hospitalId")) {
				hospitalInfoManager.deleteHopitalInformation((Integer) request.getCondition().get(
						"hospitalId"));
			}
		}
	}

	protected void update(FrontEndingCommunicationProtocol<Map<String,Object>> message)
			throws Exception {
		List<HospitalInfoDO> infos = getOrderCluster(message);
		for (HospitalInfoDO info : infos) {
			hospitalInfoManager.modifyHospitalInfomation(info.getHospitalId(),
					info.getHospitalName(), info.getHospitalLevel(), info.getHospitalAddress(),
					info.getHospitalAgent(), info.getAgentPhone());
		}
	}

	protected void submit(FrontEndingCommunicationProtocol<Map<String,Object>> message)
			throws Exception {
		List<HospitalInfoDO> infos = getOrderCluster(message);
		for (HospitalInfoDO info : infos) {
			hospitalInfoManager.addHospitalInformation(info.getHospitalName(),
					info.getHospitalLevel(), info.getHospitalAddress(), info.getHospitalAgent(),
					info.getAgentPhone());
		}
	}

	protected List<HospitalInfoDO> getOrderCluster(
			FrontEndingCommunicationProtocol<Map<String,Object>> message) throws Exception {
		if (message.getRows().size() == 0) {
			throw new Exception("数据中没有信息");
		}
		if (message.getRows().size() != 1
				&& message.getOperateType().equals(OperateTypeConstant.OPERATE_QUERY)) {
			throw new Exception("一次查询只能包含一个订单条件");
		}
		List<HospitalInfoDO> ret = new ArrayList<HospitalInfoDO>();
		for (Map<String,Object> index : message.getRows()) {
			FrontEndingRequestCondition msg=new FrontEndingRequestCondition(index);
			HospitalInfoDO info = new HospitalInfoDO();
			if (msg.getCondition().containsKey("hospitalId")) {
				info.setHospitalId((Integer) msg.getCondition().get("hospitalId"));
			}
			if (msg.getCondition().containsKey("hospitalName")) {
				info.setHospitalName((String) msg.getCondition().get("hospitalName"));
			}
			if (msg.getCondition().containsKey("hospitalLevel")) {
				info.setHospitalLevel((Integer) msg.getCondition().get("hospitalLevel"));
			}
			if (msg.getCondition().containsKey("hospitalAddress")) {
				info.setHospitalAddress((String) msg.getCondition().get("hospitalAddress"));
			}
			if (msg.getCondition().containsKey("hospitalAgent")) {
				info.setHospitalAgent((String) msg.getCondition().get("hospitalAgent"));
			}
			if (msg.getCondition().containsKey("agentPhone")) {
				info.setAgentPhone((String) msg.getCondition().get("agentPhone"));
			}
			ret.add(info);
		}
		return ret;
	}

	public RPCHospitalInfoManagerInterface getHospitalInfoManager() {
		return hospitalInfoManager;
	}

	public void setHospitalInfoManager(RPCHospitalInfoManagerInterface hospitalInfoManager) {
		this.hospitalInfoManager = hospitalInfoManager;
	}

}
