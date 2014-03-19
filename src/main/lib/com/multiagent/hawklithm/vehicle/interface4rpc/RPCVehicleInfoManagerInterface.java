package com.multiagent.hawklithm.vehicle.interface4rpc;

import org.springframework.dao.DataAccessException;

import com.multiagent.hawklithm.vehicle.DO.VehicleInfoDO;

public interface RPCVehicleInfoManagerInterface {

	boolean addVehicleInformation(String vehicleType, Integer vehicleLevel, Double vehicleCapacity)
			throws DataAccessException;

	boolean deleteVehicleInformation(int vehicleId) throws DataAccessException;

	boolean modifyVehicleInfomation(Integer vehicleId, String vehicleType, Integer vehicleLevel,
			Double vehicleCapacity) throws DataAccessException;

	VehicleInfoDO[] queryById(int vehicleId) throws DataAccessException;

	VehicleInfoDO[] queryByAllInfo(Integer vehicleId, String vehicleType, Integer vehicleLevel,
			Double vehicleCapacity, Integer offset, Integer length) throws DataAccessException;

}
