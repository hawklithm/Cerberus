package com.hawklithm.cerberus.executor;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.hawklithm.cerberus.protocol.AnnouncementOperateConstant;
import com.hawklithm.cerberus.protocol.FrontEndingCommunicationProtocol;
import com.hawklithm.cerberus.protocol.FrontEndingRequestCondition;
import com.hawklithm.cerberus.protocol.OperateTypeConstant;
import com.hawklithm.cerberus.protocol.ProtocolUtils;
import com.multiagent.hawklithm.annoucement.dataobject.AnnouncementInfoDO;
import com.multiagent.hawklithm.exception.NothingChangeAndDoNotNeedToExecuteException;
import com.multiagent.hawklithm.leon.module.SimpleMessageTransportBufferModule.SimpleMessage;
import com.multiagent.hawklithm.shadowsong.manager.interface4rpc.RPCAnnouncementManager;

public class SimpleInfoManagerExecutor implements FrontEndingCommunicationExecutor{

	private RPCAnnouncementManager announcementManager;

	@Override
	public FrontEndingCommunicationProtocol<Map<String,Object>> execute(FrontEndingCommunicationProtocol<Map<String,Object>> message )
			throws Exception {
		FrontEndingCommunicationProtocol<Map<String,Object>> result = new FrontEndingCommunicationProtocol<Map<String,Object>>();
		try {
			 if (message.getOperateType().equals(OperateTypeConstant.OPERATE_QUERY)) {
				query(message, result);
				result.setStatusOk();
			}if (message.getOperateType().equals(AnnouncementOperateConstant.OPERATE_ANNOUNCE)){
				
			}
		} catch (Exception e) {
			if (e instanceof NothingChangeAndDoNotNeedToExecuteException){
				throw e;
			}
			e.printStackTrace();
		}
		return result;
	}
	protected void announce(FrontEndingCommunicationProtocol<Map<String,Object>> message,
			FrontEndingCommunicationProtocol<Map<String,Object>> result){
		for (Map<String, Object> index:message.getRows()){
			FrontEndingRequestCondition front=new FrontEndingRequestCondition(index);
			String announceMessage=(String)ProtocolUtils.notNullGet(front.getCondition(), "message");
		}
	}

	protected void query(FrontEndingCommunicationProtocol<Map<String,Object>> message,
			FrontEndingCommunicationProtocol<Map<String,Object>> result) throws Exception {
		if (message.getRows().size() > 1) {
			throw new Exception("查询条件只能包含一个订单信息");
		}
		for (Map<String, Object> index:message.getRows()){
			FrontEndingRequestCondition front=new FrontEndingRequestCondition(index);
			AnnouncementInfoDO msg = getQueryCondition(front.getCondition());
			if (StringUtils.isEmpty(msg.getProcessName())) continue;
			AnnouncementInfoDO ans=new  AnnouncementInfoDO();
			SimpleMessage[] retValue=announcementManager.getAnnoucement();
			if (retValue.length==0){
				throw new NothingChangeAndDoNotNeedToExecuteException("[rpcAnnouncementManager] nothing change!");
			}
			ans.setRetValue(retValue);
			ans.setProcessName(msg.getProcessName());
			result.getRows().add(getFrontEndingRequest(ans).toMapping());
		}
	}
	
	
	private AnnouncementInfoDO getQueryCondition(Map<String, Object> map) {
		AnnouncementInfoDO data = new AnnouncementInfoDO();
		data.setProcessName((String) ProtocolUtils.notNullGet(map, "processName"));
		return data;
	}

	private FrontEndingRequestCondition getFrontEndingRequest(AnnouncementInfoDO info) {
		FrontEndingRequestCondition condition = new FrontEndingRequestCondition();
		condition.getCondition().put("retValue", info.getRetValue());
		condition.getCondition().put("processName", info.getProcessName());
		return condition;
	}



	public RPCAnnouncementManager getAnnouncementManager() {
		return announcementManager;
	}

	public void setAnnouncementManager(RPCAnnouncementManager announcementManager) {
		this.announcementManager = announcementManager;
	}

}
