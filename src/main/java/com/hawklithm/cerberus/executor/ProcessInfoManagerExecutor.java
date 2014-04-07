package com.hawklithm.cerberus.executor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.hawklithm.cerberus.protocol.FrontEndingCommunicationProtocol;
import com.hawklithm.cerberus.protocol.FrontEndingRequestCondition;
import com.hawklithm.cerberus.protocol.MachineInfoOperator;
import com.hawklithm.cerberus.protocol.ProtocolUtils;
import com.multiagent.hawklithm.exception.NothingChangeAndDoNotNeedToExecuteException;
import com.multiagent.hawklithm.leon.module.property.DO.ChangerAnnouncerPropertyArrayVersion;
import com.multiagent.hawklithm.leon.process.dataobject.ProcessInfoDO;
import com.multiagent.hawklithm.leon.process.interface4rpc.RPCProcessInfoManagerInterface;

public class ProcessInfoManagerExecutor implements FrontEndingCommunicationExecutor{

	private static final String RET_VALUE="retValue",PROCESS_NAME="processName",ID="id";
	private RPCProcessInfoManagerInterface processInfoManager;
	
	private static void setRetValue(Object[] object,Map<String, Object> map){
		map.put(RET_VALUE, object);
	}
	private static void  setProcessName(String name,Map<String, Object> map){
		map.put(PROCESS_NAME, name);
	}
	private static void setId(Integer id,Map<String, Object> map){
		map.put(ID, id);
	}

	@Override
	public FrontEndingCommunicationProtocol<Map<String,Object>> execute(FrontEndingCommunicationProtocol<Map<String,Object>> message )
			throws Exception {
		FrontEndingCommunicationProtocol<Map<String,Object>> result = new FrontEndingCommunicationProtocol<Map<String,Object>>();
		try {
			 if (message.getOperateType().equals(MachineInfoOperator.OPERATE_QUERY)) {
				query(message, result);
				result.setStatusOk();
			}else if (message.getOperateType().equals(MachineInfoOperator.MACHINE_DETAIL_QUERY)){
				machineDetailQuery(message, result);
				result.setStatusOk();
			}else if (message.getOperateType().equals(MachineInfoOperator.MACHINE_DETAIL_SUBMIT)){
				
			}
		} catch (Exception e) {
			if (e instanceof NothingChangeAndDoNotNeedToExecuteException){
				throw e;
			}
			e.printStackTrace();
		}
		return result;
	}
	
	protected void machineDetailSubmit(FrontEndingCommunicationProtocol<Map<String, Object>> message) throws Exception{
		if (message.getRows().size() > 1) {
			throw new Exception("查询条件只能包含一个订单信息");
		}
		for (Map<String, Object> index:message.getRows()){
			FrontEndingRequestCondition front=new FrontEndingRequestCondition(index);
			ProcessInfoDO msg = getQueryCondition(front.getCondition());
			if (StringUtils.isEmpty(msg.getProcessName())) continue;
			Map<String, Object> ans= new HashMap<String, Object>();
			if (msg.getId()!=null&&msg.getId().intValue()!=0){
				ProcessInfoManagerExecutor.setRetValue(new String[]{processInfoManager.getEquipmentInfoById(msg.getProcessName(), msg.getId().intValue())}, ans);
				ProcessInfoManagerExecutor.setId(msg.getId(), ans);
			}else{
				ProcessInfoManagerExecutor.setRetValue(processInfoManager.getProcessInfo(msg.getProcessName()), ans);
			}
			ProcessInfoManagerExecutor.setProcessName(msg.getProcessName(), ans);
//			result.getRows().add(getFrontEndingRequest(ans).toMapping());
		}
	}
	
	protected void machineDetailQuery(FrontEndingCommunicationProtocol<Map<String, Object>> message,
			FrontEndingCommunicationProtocol<Map<String, Object>> result) throws Exception{
		if (message.getRows().size() > 1) {
			throw new Exception("查询条件只能包含一个订单信息");
		}
		for (Map<String, Object> index:message.getRows()){
			FrontEndingRequestCondition front=new FrontEndingRequestCondition(index);
			ProcessInfoDO msg = getQueryCondition(front.getCondition());
			if (StringUtils.isEmpty(msg.getProcessName())) continue;
			Map<String, Object> ans= new HashMap<String, Object>();
			if (msg.getId()!=null&&msg.getId().intValue()!=0){
				ProcessInfoManagerExecutor.setRetValue(new String[]{processInfoManager.getEquipmentInfoById(msg.getProcessName(), msg.getId().intValue())}, ans);
				ProcessInfoManagerExecutor.setId(msg.getId(), ans);
			}else{
				ProcessInfoManagerExecutor.setRetValue(processInfoManager.getProcessInfo(msg.getProcessName()), ans);
			}
			ProcessInfoManagerExecutor.setProcessName(msg.getProcessName(), ans);
			result.getRows().add(getFrontEndingRequest(ans).toMapping());
		}
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
			Map<String, Object> ans=new  HashMap<String, Object>();
			ChangerAnnouncerPropertyArrayVersion[] retValue=processInfoManager.getBufferedPropertyList(msg.getProcessName());
			List<ChangerAnnouncerPropertyArrayVersion> checked=new ArrayList<ChangerAnnouncerPropertyArrayVersion>(retValue.length);
			for (int i=0;i<retValue.length;i++){
				if (!retValue[i].isDataNull())
				checked.add(retValue[i]);
			}
			if (checked.size()==0){
				throw new NothingChangeAndDoNotNeedToExecuteException("nothing change!");
			}
			ProcessInfoManagerExecutor.setRetValue(checked.toArray(new ChangerAnnouncerPropertyArrayVersion[checked.size()]),ans);
			ProcessInfoManagerExecutor.setProcessName(msg.getProcessName(),ans);
			result.getRows().add(getFrontEndingRequest(ans).toMapping());
		}
		if (result.getRows().size()==0){
			throw new NothingChangeAndDoNotNeedToExecuteException("nothing change!");
		}
	}
	
	
	private ProcessInfoDO getQueryCondition(Map<String, Object> map) {
		ProcessInfoDO data = new ProcessInfoDO();
		data.setProcessName((String) ProtocolUtils.notNullGet(map, "processName"));
		data.setId((Integer) ProtocolUtils.notNullGet(map, "id"));
		return data;
	}

	private FrontEndingRequestCondition getFrontEndingRequest(Map<String, Object> info) {
		FrontEndingRequestCondition condition = new FrontEndingRequestCondition();
		condition.getCondition().put("retValue", info.get("retValue"));
		condition.getCondition().put("processName", info.get("processName"));
		condition.getCondition().put("id", info.get("id"));
		return condition;
	}

	public RPCProcessInfoManagerInterface getProcessInfoManager() {
		return processInfoManager;
	}

	public void setProcessInfoManager(RPCProcessInfoManagerInterface processInfoManager) {
		this.processInfoManager = processInfoManager;
	}

}
