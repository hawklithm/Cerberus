package com.multiagent.hawklithm.machineInfo.interface4rpc;

import java.util.Date;

import org.springframework.dao.DataAccessException;

import com.multiagent.hawklithm.machineInfo.DO.EquipmentStaffMappingDO;
import com.multiagent.hawklithm.machineInfo.DO.MachineInfoDO;

public interface RPCMachineInfoManagerInterface {

	Integer addMachineInfo(Date gmtBuy, Date gmtLastRepair, Integer equimpmentId,
			String manufacturer, String detail);

	MachineInfoDO[] queryByAllInfo(Integer id, Date gmtBuyStart, Date gmtBuyEnd,
			Date gmtLastRepairStart, Date gmtLastRepairEnd, String equipmentType,
			Integer equimpmentId, String manufacturer, String detail, Integer offset, Integer length)
			throws DataAccessException;

	MachineInfoDO[] queryByBuyTime(Date startTime, Date endTime, Integer offset, Integer length)
			throws DataAccessException;

	MachineInfoDO[] queryByRepairTime(Date startTime, Date endTime, Integer offset, Integer length)
			throws DataAccessException;

	MachineInfoDO[] queryById(int id) throws DataAccessException;

	boolean equipmentDetailModify(Integer id, Date gmtLastRepair, String detail)
			throws DataAccessException;


	boolean equipmentDetailDeleteById(int id) throws DataAccessException;

	EquipmentStaffMappingDO[] queryEquipmentStaffMappingByStaffId(Integer staffId, Integer offset,
			Integer length);

	EquipmentStaffMappingDO[] queryEquipmentStaffMappingByEquipmentId(Integer equipmentId,
			Integer offset, Integer length);

	boolean updateEquipmentStaffMapping(Integer id, Integer equipmentId, Integer staffId);

	boolean deleteEquipmentStaffMapping(Integer id);

	Integer addEquipmentStaffMapping(Integer staffId, Integer equipmentId);

	EquipmentStaffMappingDO[] queryAllEquipmentStaffMapping(Integer offset, Integer length);

}
