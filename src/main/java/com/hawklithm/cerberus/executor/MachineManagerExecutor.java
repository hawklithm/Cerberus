package com.hawklithm.cerberus.executor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;

import com.hawklithm.cerberus.protocol.EquipmentStaffMappingOperateTypeConstant;
import com.hawklithm.cerberus.protocol.FrontEndingCommunicationProtocol;
import com.hawklithm.cerberus.protocol.FrontEndingRequestCondition;
import com.hawklithm.cerberus.protocol.OperateTypeConstant;
import com.hawklithm.cerberus.protocol.ProtocolUtils;
import com.multiagent.hawklithm.machineInfo.DO.EquipmentStaffMappingDO;
import com.multiagent.hawklithm.machineInfo.DO.ExMachineInfoDO;
import com.multiagent.hawklithm.machineInfo.DO.MachineInfoDO;
import com.multiagent.hawklithm.machineInfo.interface4rpc.RPCMachineInfoManagerInterface;

public class MachineManagerExecutor implements FrontEndingCommunicationExecutor{


	private RPCMachineInfoManagerInterface machineInfoManager;


	@Override
	public FrontEndingCommunicationProtocol<Map<String,Object>>  execute(	FrontEndingCommunicationProtocol<Map<String,Object>> message)
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
			} else if (message.getOperateType().equals(EquipmentStaffMappingOperateTypeConstant.OPERATE_ADD_MAPPING)){
				addMapping(message,result);
				result.setStatusOk();
			}else if (message.getOperateType().equals(EquipmentStaffMappingOperateTypeConstant.OPERATE_DELETE_MAPPING)){
				deleteMapping(message,result);
				result.setStatusOk();
			}else if (message.getOperateType().equals(EquipmentStaffMappingOperateTypeConstant.OPERATE_UPDATE_MAPPING)){
				updateMapping(message,result);
				result.setStatusOk();
			}else if (message.getOperateType().equals(EquipmentStaffMappingOperateTypeConstant.OPERATE_QUERY_MAPPING)){
				queryMapping(message,result);
				result.setStatusOk();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	private void queryMapping(FrontEndingCommunicationProtocol<Map<String, Object>> message,
			FrontEndingCommunicationProtocol<Map<String, Object>> result) throws Exception{
		if (message.getRows().size() > 1) {
			throw new Exception("查询条件只能包含一个订单信息");
		}
		EquipmentStaffMappingDO mapping=getMappingInfo(message).get(0);
		EquipmentStaffMappingDO[] infos;
		if (mapping.getStaffId()!=null){
			infos=machineInfoManager.queryEquipmentStaffMappingByStaffId(mapping.getStaffId(), message.getOffset(), message.getLength());
		}else  if (mapping.getEquipmentId()!=null){
			infos=machineInfoManager.queryEquipmentStaffMappingByEquipmentId(mapping.getEquipmentId(), message.getOffset(), message.getLength());
		}else {
			infos=machineInfoManager.queryAllEquipmentStaffMapping(message.getOffset(), message.getLength());
		}
		for (EquipmentStaffMappingDO index:infos){
			result.getRows().add(getFrontEndingRequest(index).toMapping());
		}
	}

	private void updateMapping(FrontEndingCommunicationProtocol<Map<String, Object>> message,
			FrontEndingCommunicationProtocol<Map<String, Object>> result) throws Exception{
		List<EquipmentStaffMappingDO> infos = getMappingInfo(message);
		for (EquipmentStaffMappingDO info : infos) {
			machineInfoManager.updateEquipmentStaffMapping(info.getId(), info.getEquipmentId(), info.getStaffId());
		}
		
	}

	private void deleteMapping(FrontEndingCommunicationProtocol<Map<String, Object>> message,
			FrontEndingCommunicationProtocol<Map<String, Object>> result) throws Exception{
		for (Map<String,Object> index : message.getRows()) {
			FrontEndingRequestCondition request=new FrontEndingRequestCondition(index);
			if (request.getCondition().containsKey("id")) {
				machineInfoManager.deleteEquipmentStaffMapping((Integer) request.getCondition().get("id"));
			}
		}
		
	}

	private void addMapping(FrontEndingCommunicationProtocol<Map<String, Object>> message,
			FrontEndingCommunicationProtocol<Map<String, Object>> result) throws Exception{
		List<EquipmentStaffMappingDO> infos = getMappingInfo(message);
		for (EquipmentStaffMappingDO info : infos) {
			machineInfoManager.addEquipmentStaffMapping(info.getStaffId(), info.getEquipmentId());
		}
		
	}
	
	private FrontEndingRequestCondition getFrontEndingRequest(EquipmentStaffMappingDO info){
		FrontEndingRequestCondition condition = new FrontEndingRequestCondition();
		Map<String, Object> map=condition.getCondition();
		ProtocolUtils.notNullSet(map, "id", info.getId());
		ProtocolUtils.notNullSet(map, "gmtCreate", info.getGmtCreate());
		ProtocolUtils.notNullSet(map, "gmtModified", info.getGmtModified());
		ProtocolUtils.notNullSet(map, "equipmentId", info.getEquipmentId());
		ProtocolUtils.notNullSet(map, "staffId", info.getStaffId());
		return condition;
	}
	
	private List<EquipmentStaffMappingDO> getMappingInfo(FrontEndingCommunicationProtocol<Map<String,Object>> message) throws Exception{
		if (message.getRows().size() == 0) {
			throw new Exception("数据中没有信息");
		}
		if (message.getRows().size() != 1
				&& message.getOperateType().equals(EquipmentStaffMappingOperateTypeConstant.OPERATE_QUERY_MAPPING)) {
			throw new Exception("一次查询只能包含一个订单条件");
		}
		List<EquipmentStaffMappingDO> ret = new ArrayList<EquipmentStaffMappingDO>();
		for (Map<String,Object> index : message.getRows()) {
			FrontEndingRequestCondition msg=new FrontEndingRequestCondition(index);
			EquipmentStaffMappingDO info = new EquipmentStaffMappingDO();
			info.setId((Integer) ProtocolUtils.notNullGet(msg.getCondition(), "id"));
			info.setEquipmentId((Integer) ProtocolUtils.notNullGet(msg.getCondition(), "equipmentId"));
			info.setGmtCreate((Date)ProtocolUtils.notNullGet(msg.getCondition(), "gmtCreate"));
			info.setGmtModified((Date)ProtocolUtils.notNullGet(msg.getCondition(), "gmtModified"));
			info.setStaffId((Integer)ProtocolUtils.notNullGet(msg.getCondition(), "staffId"));
			ret.add(info);
		}
		return ret;
	}

	protected void query(FrontEndingCommunicationProtocol<Map<String,Object>> message,
			FrontEndingCommunicationProtocol<Map<String,Object>> result) throws Exception {
		if (message.getRows().size() > 1) {
			throw new Exception("查询条件只能包含一个订单信息");
		}
		ExMachineInfoDO msg = getOrderCluster(message).get(0);
		MachineInfoDO[] infos = machineInfoManager.queryByAllInfo(msg.getId(),
				msg.getGmtBuyStart(), msg.getGmtBuyEnd(), msg.getGmtLastRepairStart(),
				msg.getGmtLastRepairEnd(), msg.getMachineNumber(), msg.getEquipmentId(),
				msg.getManufacturer(), msg.getDetail(), message.getOffset(), message.getLength());
		for (MachineInfoDO index : infos) {
			result.getRows().add(getFrontEndingRequest(index).toMapping());
		}
	}

	private FrontEndingRequestCondition getFrontEndingRequest(MachineInfoDO info) {
		FrontEndingRequestCondition condition = new FrontEndingRequestCondition();
		condition.getCondition().put("id", info.getId());
		condition.getCondition().put("gmtBuy", info.getGmtBuy());
		condition.getCondition().put("gmtLastRepair", info.getGmtLastRepair());
		condition.getCondition().put("machineNumber", info.getMachineNumber());
		condition.getCondition().put("equipmentId", info.getEquipmentId());
		condition.getCondition().put("manufacturer", info.getManufacturer());
		condition.getCondition().put("detail", info.getDetail());
		return condition;
	}

	protected void delete(FrontEndingCommunicationProtocol<Map<String,Object>> message)
			throws Exception {
		for (Map<String,Object> index : message.getRows()) {
			FrontEndingRequestCondition request=new FrontEndingRequestCondition(index);
			if (request.getCondition().containsKey("id")) {
				machineInfoManager.equipmentDetailDeleteById((Integer) request.getCondition().get("id"));
			}
		}
	}

	protected void update(FrontEndingCommunicationProtocol<Map<String,Object>> message)
			throws Exception {
		List<ExMachineInfoDO> infos = getOrderCluster(message);
		for (ExMachineInfoDO info : infos) {
			machineInfoManager.equipmentDetailModify(info.getId(), info.getGmtLastRepair(), info.getDetail());
		}
	}

	protected void submit(FrontEndingCommunicationProtocol<Map<String,Object>> message)
			throws Exception {
		List<ExMachineInfoDO> infos = getOrderCluster(message);
		for (ExMachineInfoDO info : infos) {
			machineInfoManager.addMachineInfo(info.getGmtBuy(), info.getGmtLastRepair(),
					 info.getEquipmentId(), info.getManufacturer(),	info.getDetail());
		}
	}

	protected List<ExMachineInfoDO> getOrderCluster(
			FrontEndingCommunicationProtocol<Map<String,Object>> message) throws Exception {
		if (message.getRows().size() == 0) {
			throw new Exception("数据中没有信息");
		}
		if (message.getRows().size() != 1
				&& message.getOperateType().equals(OperateTypeConstant.OPERATE_QUERY)) {
			throw new Exception("一次查询只能包含一个订单条件");
		}
		List<ExMachineInfoDO> ret = new ArrayList<ExMachineInfoDO>();
		for (Map<String,Object> index : message.getRows()) {
			FrontEndingRequestCondition msg=new FrontEndingRequestCondition(index);
			ExMachineInfoDO info = new ExMachineInfoDO();
			if (msg.getCondition().containsKey("id")) {
				info.setId((Integer) msg.getCondition().get("id"));
			}
			if (msg.getCondition().containsKey("gmtBuy")) {
				info.setGmtBuy((Date) msg.getCondition().get("gmtBuy"));
			}
			if (msg.getCondition().containsKey("gmtLastRepair")) {
				info.setGmtLastRepair((Date) msg.getCondition().get("gmtLastRepair"));
			}
			if (msg.getCondition().containsKey("machineNumber")) {
				info.setMachineNumber((String) msg.getCondition().get("machineNumber"));
			}
			if (msg.getCondition().containsKey("equipmentId")) {
				info.setEquipmentId((Integer) msg.getCondition().get("equipmentId"));
			}
			if (msg.getCondition().containsKey("manufacturer")) {
				info.setManufacturer((String) msg.getCondition().get("manufacturer"));
			}
			if (msg.getCondition().containsKey("detail")) {
				info.setDetail((String) msg.getCondition().get("detail"));
			}
			if (msg.getCondition().containsKey("gmtBuyStart")) {
				info.setGmtBuyStart((Date) msg.getCondition().get("gmtBuyStart"));
			}
			if (msg.getCondition().containsKey("gmtBuyEnd")) {
				info.setGmtBuyEnd((Date) msg.getCondition().get("gmtBuyEnd"));
			}
			if (msg.getCondition().containsKey("gmtLastRepairStart")) {
				info.setGmtLastRepairStart((Date) msg.getCondition().get("gmtLastRepairStart"));
			}
			if (msg.getCondition().containsKey("gmtLastRepairEnd")) {
				info.setGmtLastRepairEnd((Date) msg.getCondition().get("gmtLastRepairEnd"));
			}
			ret.add(info);
		}
		return ret;
	}

	public RPCMachineInfoManagerInterface getMachineInfoManager() {
		return machineInfoManager;
	}

	public void setMachineInfoManager(RPCMachineInfoManagerInterface machineInfoManager) {
		this.machineInfoManager = machineInfoManager;
	}

}
