package com.hawklithm.cerberus.executor;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;

import org.apache.commons.lang.StringUtils;

import com.hawklithm.cerberus.protocol.FrontEndingCommunicationProtocol;
import com.hawklithm.cerberus.protocol.FrontEndingRequestCondition;
import com.hawklithm.cerberus.protocol.OperateTypeConstant;
import com.hawklithm.cerberus.protocol.ProtocolUtils;
import com.multiagent.hawklithm.leon.process.dataobject.ProcessInfoDO;
import com.multiagent.hawklithm.leon.process.interface4rpc.RPCProcessInfoManagerInterface;

public class ProcessInfoManagerExecutor implements FrontEndingCommunicationExecutor{

	private RPCProcessInfoManagerInterface processInfoManager;

	@Override
	public FrontEndingCommunicationProtocol<Map<String,Object>> execute(FrontEndingCommunicationProtocol<Map<String,Object>> message )
			throws ServletException, IOException {
		FrontEndingCommunicationProtocol<Map<String,Object>> result = new FrontEndingCommunicationProtocol<Map<String,Object>>();
		try {
			 if (message.getOperateType().equals(OperateTypeConstant.OPERATE_QUERY)) {
				query(message, result);
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
		for (Map<String, Object> index:message.getRows()){
			FrontEndingRequestCondition front=new FrontEndingRequestCondition(index);
			ProcessInfoDO msg = getQueryCondition(front.getCondition());
			if (StringUtils.isEmpty(msg.getProcessName())) continue;
			ProcessInfoDO ans=new  ProcessInfoDO();
			ans.setRetValue(processInfoManager.getBufferedPropertyList(msg.getProcessName()));
			ans.setProcessName(msg.getProcessName());
//			if (msg.getId()!=null&&msg.getId().intValue()!=0){
//				ans.setRetValue(new String[]{processInfoManager.getEquipmentInfoById(msg.getProcessName(), msg.getId().intValue())});
//			}else{
//				ans.setRetValue(processInfoManager.getProcessInfo(msg.getProcessName()));
//			}
			result.getRows().add(getFrontEndingRequest(ans).toMapping());
		}
	}
	
	private ProcessInfoDO getQueryCondition(Map<String, Object> map) {
		ProcessInfoDO data = new ProcessInfoDO();
		data.setProcessName((String) ProtocolUtils.notNullGet(map, "processName"));
		data.setId((Integer) ProtocolUtils.notNullGet(map, "id"));
		return data;
	}

	private FrontEndingRequestCondition getFrontEndingRequest(ProcessInfoDO info) {
		FrontEndingRequestCondition condition = new FrontEndingRequestCondition();
		condition.getCondition().put("retValue", info.getRetValue());
		condition.getCondition().put("processName", info.getProcessName());
		return condition;
	}




	public RPCProcessInfoManagerInterface getProcessInfoManager() {
		return processInfoManager;
	}

	public void setProcessInfoManager(RPCProcessInfoManagerInterface processInfoManager) {
		this.processInfoManager = processInfoManager;
	}

}
