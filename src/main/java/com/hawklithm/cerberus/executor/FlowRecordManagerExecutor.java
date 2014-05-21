package com.hawklithm.cerberus.executor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;

import com.google.gson.Gson;
import com.hawklithm.cerberus.protocol.FlowRecordDO;
import com.hawklithm.cerberus.protocol.FrontEndingCommunicationProtocol;
import com.hawklithm.cerberus.protocol.FrontEndingRequestCondition;
import com.hawklithm.cerberus.protocol.HistoryAllAskDO;
import com.hawklithm.cerberus.protocol.HistoryAskConstant;
import com.hawklithm.cerberus.protocol.HistoryVideoAskDO;
import com.hawklithm.cerberus.protocol.OperateTypeConstant;
import com.hawklithm.cerberus.protocol.ProtocolUtils;
import com.hawklithm.cerberus.video.dataobject.VideoInfoDO;
import com.hawklithm.cerberus.video.manager.VideoManager;
import com.multiagent.hawklithm.history.dataobject.ExItemHistoryDO;
import com.multiagent.hawklithm.history.dataobject.ItemAllHistoryInfoDO;
import com.multiagent.hawklithm.history.dataobject.PackageAllHistoryInfoDO;
import com.multiagent.hawklithm.history.dataobject.PackageHistoryDO;
import com.multiagent.hawklithm.history.interface4rpc.RPCHistoryInfoGetterInterface;
import com.multiagent.hawklithm.leon.interface4rpc.RPCMachineFlowRecordManagerInterface;

public class FlowRecordManagerExecutor implements FrontEndingCommunicationExecutor{
	private RPCMachineFlowRecordManagerInterface flowRecordManager;
	private RPCHistoryInfoGetterInterface historyInfoGetter;
	private VideoManager videoManager;
	private Gson gson=new Gson();

	public RPCMachineFlowRecordManagerInterface getFlowRecordManager() {
		return flowRecordManager;
	}

	public void setFlowRecordManager(RPCMachineFlowRecordManagerInterface flowRecordManager) {
		this.flowRecordManager = flowRecordManager;
	}

	@Override
	public  FrontEndingCommunicationProtocol<Map<String,Object>> execute(FrontEndingCommunicationProtocol<Map<String,Object>> message )
			throws ServletException, IOException {
		FrontEndingCommunicationProtocol<Map<String,Object>> result = new FrontEndingCommunicationProtocol<Map<String,Object>>();
		try {
			if (message.getOperateType().equals(HistoryAskConstant.OPERATE_QUERY)) {
				query(message, result);
				result.setStatusOk();
			}else if (message.getOperateType().equals(HistoryAskConstant.OPERATE_ASKFORHISTORY_BYITEMANDMACHINE)){
				askforHistoryByItemAndMachine(message, result);
				result.setStatusOk();
			}else if (message.getOperateType().equals(HistoryAskConstant.OPERATE_ASKFORHISTORY_BYPACKAGEANDMACHINE)){
				askforHistoryByPackageAndMachine(message,result);
				result.setStatusOk();
			}else if (message.getOperateType().equals(HistoryAskConstant.OPERATE_ASKFORVIDEO_BYTIME)){
				askforVideoByTime(message, result);
				result.setStatusOk();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * ������еid���豸id��ѯ��ʷ��Ϣ
	 * @param message
	 * @param result
	 * @throws Exception
	 */
	protected void askforHistoryByItemAndMachine(FrontEndingCommunicationProtocol<Map<String,Object>> message,
			FrontEndingCommunicationProtocol<Map<String,Object>> result) throws Exception{
		HistoryAllAskDO msg = getHistoryAllAskedInfo(message).get(0);
		ItemAllHistoryInfoDO itemAll=historyInfoGetter.getItemAllHistoryOnSpecifiedMachine(msg.getId(), msg.getMachineId(), msg.getLength(), msg.getOffset());
		System.out.println("��ѯ��е���ݣ�"+gson.toJson(itemAll));
		result.getRows().add(getFrontEndingRequest(itemAll).toMapping());
	}
	/**
	 * ����������id���豸id��ѯ��ʷ��Ϣ
	 * @param message
	 * @param result
	 * @throws Exception
	 */
	protected void askforHistoryByPackageAndMachine(FrontEndingCommunicationProtocol<Map<String,Object>> message,
			FrontEndingCommunicationProtocol<Map<String,Object>> result) throws Exception{
		HistoryAllAskDO msg = getHistoryAllAskedInfo(message).get(0);
		PackageAllHistoryInfoDO packAll=historyInfoGetter.getPackageAllHistoryOnSpecifiedMachine(msg.getId(), msg.getMachineId(), msg.getLength(), msg.getOffset());
		System.out.println("��ѯ���������ݣ�"+gson.toJson(packAll));
		result.getRows().add(getFrontEndingRequest(packAll).toMapping());
	}
	
	/**
	 * ������ֹʱ����豸id��ѯ¼����Ϣ
	 * @param message
	 * @param result
	 * @throws Exception
	 */
	protected void askforVideoByTime(FrontEndingCommunicationProtocol<Map<String,Object>> message,
			FrontEndingCommunicationProtocol<Map<String,Object>> result)  throws Exception{
		HistoryVideoAskDO msg=getVideoAskCondition(message).get(0);
		VideoInfoDO[] infos=videoManager.getVideoByTimeAndMachineId(msg.getStartTime(), msg.getEndTime(), msg.getMachineId());
		for (VideoInfoDO info:infos){
			result.getRows().add(getFrontEndingRequest(info).toMapping());
		}
	}
	
	protected List<HistoryVideoAskDO> getVideoAskCondition(FrontEndingCommunicationProtocol<Map<String,Object>> message) throws Exception{
		//TODO ��Ҫ��������
		return null;
	}
	
	protected List<HistoryAllAskDO> getHistoryAllAskedInfo(FrontEndingCommunicationProtocol<Map<String,Object>> message)  throws Exception{
		//TODO ��Ҫ��������
		return null;
	}

	protected void query(FrontEndingCommunicationProtocol<Map<String,Object>> message,
			FrontEndingCommunicationProtocol<Map<String,Object>> result) throws Exception {
		if (message.getRows().size() > 1) {
			throw new Exception("��ѯ����ֻ�ܰ���һ��������Ϣ");
		}
		
		FlowRecordDO msg = getOrderCluster(message).get(0);
		
		if (FlowRecordDO.isItem(message.getTableName())){
//			SqlReaderAtEquipmentDO[] infos = flowRecordManager.getEquipmentHistoryInfo(msg.getRecordId(),
//					msg.getStartTime(), msg.getEndTime(), msg.getEquipmentId(),msg.getObjectId(),
//					msg.getItemId(), msg.getReaderId(), msg.getCameraId(), msg.getStaffId());
//			for (SqlReaderAtEquipmentDO index : infos) {
//				result.getRows().add(getFrontEndingRequest(index).toMapping());
//			}
			ExItemHistoryDO[] itemInfos=flowRecordManager.queryItemHistory(msg.getRecordId(), msg.getObjectId(), msg.getReaderId(), msg.getEquipmentId(), msg.getStartTime(), msg.getEndTime(),message.getOffset(),message.getLength());
			System.out.println("��ѯ����е���ݣ�"+gson.toJson(itemInfos));
			for (ExItemHistoryDO index:itemInfos){
				result.getRows().add(getFrontEndingRequest(index).toMapping());
			}
		}else if (FlowRecordDO.isPackage(message.getTableName())){
			PackageHistoryDO[] packageInfos=flowRecordManager.queryPackageHistory(msg.getRecordId(), msg.getObjectId(), msg.getReaderId(), msg.getEquipmentId(), msg.getStartTime(), msg.getEndTime(),message.getOffset(),message.getLength());
			for (PackageHistoryDO index:packageInfos){
				result.getRows().add(getFrontEndingRequest(index).toMapping());
			}
		}
		
		
	}
	
	/**
	 * 	private Integer id;
	private Date time;
	private Date gmtCreate;
	private Date gmtModified;
	private Integer itemId;
	private Integer readerId;
	private Integer cameraId;
	private String itemStatus;
	private Integer equipmentId;
	
	private Integer packageId;
	private String packageStatus;
	
	private ExPackageHistoryDO[] packs;
	private ExItemHistoryDO[] items;
	private Date startTime;
	private Date endTime;
	 * @param info
	 * @return
	 */

	private FrontEndingRequestCondition getFrontEndingRequest(Object info) {
		FrontEndingRequestCondition condition = new FrontEndingRequestCondition();
		try {
			ProtocolUtils.notNullSet(condition.getCondition(), "id", ProtocolUtils.GetProperty(info, "id"));
			ProtocolUtils.notNullSet(condition.getCondition(), "time", ProtocolUtils.GetProperty(info, "time"));
			ProtocolUtils.notNullSet(condition.getCondition(), "gmtCreate", ProtocolUtils.GetProperty(info, "gmtCreate"));
			ProtocolUtils.notNullSet(condition.getCondition(), "gmtModified", ProtocolUtils.GetProperty(info, "gmtModified"));
			ProtocolUtils.notNullSet(condition.getCondition(), "itemId", ProtocolUtils.GetProperty(info, "itemId"));
			ProtocolUtils.notNullSet(condition.getCondition(), "packageId", ProtocolUtils.GetProperty(info, "packageId"));
			ProtocolUtils.notNullSet(condition.getCondition(), "readerId", ProtocolUtils.GetProperty(info, "readerId"));
			ProtocolUtils.notNullSet(condition.getCondition(), "cameraId", ProtocolUtils.GetProperty(info, "cameraId"));
			ProtocolUtils.notNullSet(condition.getCondition(), "itemStatus", ProtocolUtils.GetProperty(info, "itemStatus"));
			ProtocolUtils.notNullSet(condition.getCondition(), "packageStatus", ProtocolUtils.GetProperty(info, "packageStatus"));
			ProtocolUtils.notNullSet(condition.getCondition(), "equipmentId", ProtocolUtils.GetProperty(info, "equipmentId"));
			ProtocolUtils.notNullSet(condition.getCondition(), "processName", ProtocolUtils.GetProperty(info, "processName"));
			ProtocolUtils.notNullSet(condition.getCondition(), "staffInfo", ProtocolUtils.GetProperty(info, "staffInfo"));
			ProtocolUtils.notNullSet(condition.getCondition(), "items", ProtocolUtils.GetProperty(info, "items"));
			ProtocolUtils.notNullSet(condition.getCondition(), "packs", ProtocolUtils.GetProperty(info, "packs"));
			ProtocolUtils.notNullSet(condition.getCondition(), "startTime", ProtocolUtils.GetProperty(info, "startTime"));
			ProtocolUtils.notNullSet(condition.getCondition(), "endTime", ProtocolUtils.GetProperty(info, "endTime"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return condition;
	}
	
/*	
	private byte[] data;
	private String name;
	private Date startTime;
	private Date endTime;
	private Integer machineId;
	*/
	private FrontEndingRequestCondition getFrontEndingRequest(VideoInfoDO info){
		FrontEndingRequestCondition condition = new FrontEndingRequestCondition();
		try {
			ProtocolUtils.notNullSet(condition.getCondition(), "data", ProtocolUtils.GetProperty(info, "data"));
			ProtocolUtils.notNullSet(condition.getCondition(), "name", ProtocolUtils.GetProperty(info, "name"));
			ProtocolUtils.notNullSet(condition.getCondition(), "startTime", ProtocolUtils.GetProperty(info, "startTime"));
			ProtocolUtils.notNullSet(condition.getCondition(), "endTime", ProtocolUtils.GetProperty(info, "endTime"));
			ProtocolUtils.notNullSet(condition.getCondition(), "machineId", ProtocolUtils.GetProperty(info, "machineId"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return condition;
	}

	protected List<FlowRecordDO> getOrderCluster(
			FrontEndingCommunicationProtocol<Map<String,Object>> message) throws Exception {
		if (message.getRows().size() == 0) {
			throw new Exception("������û����Ϣ");
		}
		if (message.getRows().size() != 1
				&& message.getOperateType().equals(OperateTypeConstant.OPERATE_QUERY)) {
			throw new Exception("һ�β�ѯֻ�ܰ���һ����������");
		}
		List<FlowRecordDO> ret = new ArrayList<FlowRecordDO>();
		for (Map<String,Object> index : message.getRows()) {
			FrontEndingRequestCondition msg=new FrontEndingRequestCondition(index);
//			ExSqlReaderAtEquipmentDO info = new ExSqlReaderAtEquipmentDO();
//			info.setId((Integer) ProtocolUtils.notNullGet(msg.getCondition(), "id"));
//			info.setStartTime((Date) ProtocolUtils.notNullGet(msg.getCondition(), "startTime"));
//			info.setEndTime((Date) ProtocolUtils.notNullGet(msg.getCondition(), "endTime"));
//			info.setId((Integer) ProtocolUtils.notNullGet(msg.getCondition(), "equipmentId"));
//			info.setId((Integer) ProtocolUtils.notNullGet(msg.getCondition(), "itemId"));
//			info.setId((Integer) ProtocolUtils.notNullGet(msg.getCondition(), "readerId"));
//			info.setId((Integer) ProtocolUtils.notNullGet(msg.getCondition(), "cameraId"));
//			info.setId((Integer) ProtocolUtils.notNullGet(msg.getCondition(), "staffId"));
//			ret.add(info);
			FlowRecordDO record=new FlowRecordDO();
			record.setInfoType((String) ProtocolUtils.notNullGet(msg.getCondition(), "infoType"));
			record.setRecordId((Integer) ProtocolUtils.notNullGet(msg.getCondition(), "recordId"));
			record.setStartTime((Date)ProtocolUtils.notNullGet(msg.getCondition(),"startTime"));
			record.setEndTime((Date)ProtocolUtils.notNullGet(msg.getCondition(), "endTime"));
			record.setEquipmentId((Integer) ProtocolUtils.notNullGet(msg.getCondition(), "equipmentId"));
			record.setObjectId((Integer) ProtocolUtils.notNullGet(msg.getCondition(), "objectId"));
			record.setObjectType((String) ProtocolUtils.notNullGet(msg.getCondition(), "objectType"));
			record.setReaderId((Integer) ProtocolUtils.notNullGet(msg.getCondition(), "readerId"));
			record.setCameraId((Integer) ProtocolUtils.notNullGet(msg.getCondition(), "cameraId"));
			record.setStaffId((Integer) ProtocolUtils.notNullGet(msg.getCondition(), "staffId"));
			record.setProcessName((String) ProtocolUtils.notNullGet(msg.getCondition(), "processName"));
			record.setRealTime((Date) ProtocolUtils.notNullGet(msg.getCondition(), "realTime"));
			record.setObjectStatus((String) ProtocolUtils.notNullGet(msg.getCondition(), "objectStatus"));
			ret.add(record);
		}
		return ret;
	}

	public RPCHistoryInfoGetterInterface getHistoryInfoGetter() {
		return historyInfoGetter;
	}

	public void setHistoryInfoGetter(RPCHistoryInfoGetterInterface historyInfoGetter) {
		this.historyInfoGetter = historyInfoGetter;
	}

	public VideoManager getVideoManager() {
		return videoManager;
	}

	public void setVideoManager(VideoManager videoManager) {
		this.videoManager = videoManager;
	}

}
