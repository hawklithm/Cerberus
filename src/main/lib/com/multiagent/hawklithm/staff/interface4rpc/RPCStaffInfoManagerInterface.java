package com.multiagent.hawklithm.staff.interface4rpc;

import org.springframework.dao.DataAccessException;

import com.multiagent.hawklithm.staff.DO.StaffInfoDO;

public interface RPCStaffInfoManagerInterface {

	boolean delete(int staffId) throws DataAccessException;

	boolean modify(Integer staffId, String staffName, String staffPhoneNumber, String staffGender,
			Integer staffAge, String staffDepartmentId) throws DataAccessException;

	StaffInfoDO[] queryById(int staffId) throws DataAccessException;

	StaffInfoDO[] queryByAge(Integer ageStart, Integer ageEnd, Integer offset, Integer length)
			throws DataAccessException;

	StaffInfoDO[] queryByGender(String gender, Integer offset, Integer length)
			throws DataAccessException;

	StaffInfoDO[] queryByName(String name, Integer offset, Integer length)
			throws DataAccessException;

	StaffInfoDO[] queryByAllInfo(Integer staffId, String staffName, String staffPhoneNumber,
			String staffGender, Integer staffAgeStart, Integer staffAgeEnd,
			String staffDepartmentId, Integer offset, Integer length) throws DataAccessException;

	Integer submit(String staffName, String staffPhoneNumber, String staffGender, Integer staffAge,
			String staffDepartmentId) throws DataAccessException;
}
