package com.multiagent.hawklithm.hospital.interface4rpc;

import org.springframework.dao.DataAccessException;

import com.multiagent.hawklithm.hospital.DO.HospitalInfoDO;

public interface RPCHospitalInfoManagerInterface {
	boolean addHospitalInformation(String hospitalName, Integer hospitalLevel,
			String hospitalAddress, String hospitalAgent, String agentPhone)
			throws DataAccessException;

	boolean deleteHopitalInformation(Integer hospitalId) throws DataAccessException;

	boolean modifyHospitalInfomation(Integer hospitalId, String hospitalName,
			Integer hospitalLevel, String hospitalAddress, String hospitalAgent, String agentPhone)
			throws DataAccessException;

	HospitalInfoDO[] query(Integer hospitalId, String hospitalName, Integer hospitalLevel,
			String hospitalAddress, String hospitalAgent, String agentPhone, Integer offset,
			Integer length) throws DataAccessException;
}
