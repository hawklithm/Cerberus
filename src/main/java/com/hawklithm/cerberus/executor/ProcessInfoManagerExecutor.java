package com.hawklithm.cerberus.executor;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.hawklithm.cerberus.protocol.FrontEndingCommunicationProtocol;
import com.hawklithm.cerberus.protocol.FrontEndingRequestCondition;
import com.hawklithm.cerberus.protocol.MachineInfoOperator;
import com.hawklithm.cerberus.protocol.ProtocolUtils;
import com.multiagent.hawklithm.exception.NothingChangeAndDoNotNeedToExecuteException;
import com.multiagent.hawklithm.item.dataobject.ExItemInfoDO;
import com.multiagent.hawklithm.item.dataobject.ItemInfoDO;
import com.multiagent.hawklithm.leon.DO.SqlReaderAtEquipmentDO;
import com.multiagent.hawklithm.leon.interface4rpc.RPCMachineFlowRecordManagerInterface;
import com.multiagent.hawklithm.leon.module.property.DO.ChangerAnnouncerPropertyArrayVersion;
import com.multiagent.hawklithm.leon.module.property.DO.ChangerAnnouncerPropertyWithStatus;
import com.multiagent.hawklithm.leon.process.dataobject.ProcessInfoDO;
import com.multiagent.hawklithm.leon.process.interface4rpc.RPCProcessInfoManagerInterface;

public class ProcessInfoManagerExecutor implements FrontEndingCommunicationExecutor{

	private static final String RET_VALUE="retValue",PROCESS_NAME="processName",ID="id";
	private RPCProcessInfoManagerInterface processInfoManager;
	private RPCMachineFlowRecordManagerInterface machineFlowRecordManager;
	
	private static void setRetValue(Object[] object,Map<String, Object> map){
		map.put(RET_VALUE, object);
	}
	private static void  setProcessName(String name,Map<String, Object> map){
		map.put(PROCESS_NAME, name);
	}
	private static void setId(Integer id,Map<String, Object> map){
		map.put(ID, id);
	}
	
	private class ItemStateAssembler{
		public final static int STATUS_READY=0x01,STATUS_RUNNING=0x02,STATUS_DONE=0x03;
		private Map<Integer, Integer> map=new HashMap<Integer,Integer>();
		private List<ChangerAnnouncerPropertyArrayVersion> list=new ArrayList<ChangerAnnouncerPropertyArrayVersion>();
		public void addProperty(ChangerAnnouncerPropertyArrayVersion info){
			list.add(info);
		}
		public void add(ItemInfoDO[] infos,int status){
			for (ItemInfoDO index:infos){
				if (map.containsKey(index.getItemId())){
					Integer oldStatus=map.get(index.getItemId());
					map.put(index.getItemId(),Math.max(oldStatus, status));
				}else {
					map.put(index.getItemId(), status);
				}
			}
		}
		public ChangerAnnouncerPropertyWithStatus[] get(){
			ChangerAnnouncerPropertyWithStatus[] ans=new ChangerAnnouncerPropertyWithStatus[list.size()];
			int ci=0;
			for (ChangerAnnouncerPropertyArrayVersion index:list){
				ans[ci]=new ChangerAnnouncerPropertyWithStatus();
				Map<Integer,ExItemInfoDO> tempList=new HashMap<Integer,ExItemInfoDO>();
				boolean gateTag=index.getSourceType().equals("gate_tag");
				for (ItemInfoDO oldItem:index.getItemAdd()){
					ExItemInfoDO newItem=ExItemInfoDO.getExtend(oldItem);
					newItem.setStatus(map.get(oldItem.getItemId()));
					/**
					 * 如果是工段数据，并且是待处理状态，才添加，否则不添加。这样可以减少数据传输量
					 */
					if (gateTag&&newItem.getStatus()==ItemStateAssembler.STATUS_READY){
						tempList.put(newItem.getItemId(), newItem);
					}
					else if(!gateTag){
						tempList.put(newItem.getItemId(), newItem);
					}
				}
				for (ItemInfoDO oldItem:index.getItemRemove()){
					ExItemInfoDO newItem=ExItemInfoDO.getExtend(oldItem);
					newItem.setStatus(map.get(oldItem.getItemId()));
					/**
					 * 如果是工段数据，并且是待处理状态，才添加，否则不添加。这样可以减少数据传输量
					 */
					if (gateTag&&newItem.getStatus()==ItemStateAssembler.STATUS_READY){
						tempList.put(newItem.getItemId(), newItem);
					}
					else if(!gateTag){
						tempList.put(newItem.getItemId(), newItem);
					}
				}
				ExItemInfoDO[] exItem=new ExItemInfoDO[tempList.size()];
				int cj=0;
				for (Map.Entry<Integer, ExItemInfoDO> entry : tempList.entrySet()){
					exItem[cj++]=entry.getValue();
				}
				ans[ci].setItemInfo(exItem);
				ans[ci].setOldVersion(index);
				ci++;
			}
			return ans;
		}
		
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
			}else if (message.getOperateType().equals(MachineInfoOperator.MACHINE_QUERY_TODAY_DETAIL)){
				queryToday(message, result);
				result.setStatusOk();
			}
		} catch (Exception e) {
			if (e instanceof NothingChangeAndDoNotNeedToExecuteException){
				throw e;
			}
			e.printStackTrace();
		}
		return result;
	}
	
	protected void queryToday(FrontEndingCommunicationProtocol<Map<String,Object>> message,
			FrontEndingCommunicationProtocol<Map<String,Object>> result) throws Exception {
		if (message.getRows().size() > 1) {
			throw new Exception("查询条件只能包含一个订单信息");
		}
		for (Map<String, Object> index:message.getRows()){
			FrontEndingRequestCondition front=new FrontEndingRequestCondition(index);
			ProcessInfoDO msg = getQueryCondition(front.getCondition());
			if (StringUtils.isEmpty(msg.getProcessName())) continue;
			Map<String, Object> ans=new  HashMap<String, Object>();
			Integer[] equipmentRfids=processInfoManager.getMachineId(msg.getProcessName());
			Date today=new Date(new Date().getDate());
			ChangerAnnouncerPropertyArrayVersion[] retValue=machineFlowRecordManager.getEquipmentsTodayHistoryInfo(today, equipmentRfids);
			
//			ChangerAnnouncerPropertyArrayVersion[] retValue=processInfoManager.getBufferedPropertyList(msg.getProcessName());
//			List<ChangerAnnouncerPropertyArrayVersion> checked=new ArrayList<ChangerAnnouncerPropertyArrayVersion>(retValue.length);
//			for (int i=0;i<retValue.length;i++){
//				if (!retValue[i].isDataNull())
//				checked.add(retValue[i]);
//			}
//			if (checked.size()==0){
//				throw new NothingChangeAndDoNotNeedToExecuteException("nothing change!");
//			}
//			
			/**
			 *  状态合并
			 */
			ItemStateAssembler assembler=new ItemStateAssembler();
			for (ChangerAnnouncerPropertyArrayVersion ch:retValue){
				assembler.addProperty(ch);
				if (ch.getSourceType().equals("gate_tag")){
					assembler.add(ch.getItemAdd(), ItemStateAssembler.STATUS_READY);
				}else {
					assembler.add(ch.getItemAdd(), ItemStateAssembler.STATUS_RUNNING);
					assembler.add(ch.getItemRemove(), ItemStateAssembler.STATUS_DONE);
				}
			}
			
			ProcessInfoManagerExecutor.setRetValue(assembler.get(),ans);
			ProcessInfoManagerExecutor.setProcessName(msg.getProcessName(),ans);
			result.getRows().add(getFrontEndingRequest(ans).toMapping());
		}
		if (result.getRows().size()==0){
			throw new NothingChangeAndDoNotNeedToExecuteException("nothing change!");
		}
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
			
			/**
			 *  状态合并
			 */
			ItemStateAssembler assembler=new ItemStateAssembler();
			for (ChangerAnnouncerPropertyArrayVersion ch:checked){
				assembler.addProperty(ch);
				if (ch.getSourceType().equals("gate_tag")){
					assembler.add(ch.getItemAdd(), ItemStateAssembler.STATUS_READY);
				}else {
					assembler.add(ch.getItemAdd(), ItemStateAssembler.STATUS_RUNNING);
					assembler.add(ch.getItemRemove(), ItemStateAssembler.STATUS_DONE);
				}
			}
			
			ProcessInfoManagerExecutor.setRetValue(assembler.get(),ans);
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
	public RPCMachineFlowRecordManagerInterface getMachineFlowRecordManager() {
		return machineFlowRecordManager;
	}
	public void setMachineFlowRecordManager(
			RPCMachineFlowRecordManagerInterface machineFlowRecordManager) {
		this.machineFlowRecordManager = machineFlowRecordManager;
	}

}
