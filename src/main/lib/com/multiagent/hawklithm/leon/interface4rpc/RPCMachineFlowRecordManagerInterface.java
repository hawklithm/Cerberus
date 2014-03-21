package com.multiagent.hawklithm.leon.interface4rpc;

import java.util.Date;

import com.multiagent.hawklithm.history.dataobject.ExItemHistoryDO;
import com.multiagent.hawklithm.history.dataobject.PackageHistoryDO;
import com.multiagent.hawklithm.leon.DO.SqlEquipCamReaderMappingDO;
import com.multiagent.hawklithm.leon.DO.SqlReaderAtEquipmentDO;

public interface RPCMachineFlowRecordManagerInterface {

	SqlReaderAtEquipmentDO[] getEquipmentHistoryInfoByItemId(Integer itemId, Date startTime,
			Date endTime);

	SqlReaderAtEquipmentDO[] getEquipmentHistoryInfoByPackageId(Integer packageId, Date startTime,
			Date endTime);

	SqlReaderAtEquipmentDO[] getEquipmentHistoryInfo(Integer id, Date startTime, Date endTime,
			Integer equipmentId, Integer packageId, Integer itemId, Integer readerId,
			Integer cameraId, Integer staffId);

	SqlEquipCamReaderMappingDO getMappingDataByReaderId(int readerId);

	SqlEquipCamReaderMappingDO getMappingDataByCameraId(int cameraId);

	SqlEquipCamReaderMappingDO getMappingDataByEquipmentId(int equipmentId);

	ExItemHistoryDO[] queryItemHistory(Integer id, Integer itemId, Integer readerId,
			Integer equipmentId, Date startTime, Date endTime, Integer offset, Integer length);

	PackageHistoryDO[] queryPackageHistory(Integer id, Integer packageId, Integer readerId,
			Integer equipmentId, Date startTime, Date endTime, Integer offset, Integer length);



}
