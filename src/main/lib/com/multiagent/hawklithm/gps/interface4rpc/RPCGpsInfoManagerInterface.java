package com.multiagent.hawklithm.gps.interface4rpc;

import java.util.Date;

import com.multiagent.hawklithm.gps.DO.GpsInfoDO;

public interface RPCGpsInfoManagerInterface {

	boolean delete(Integer gpsId);

	boolean submit(Integer vehicleId, Double longitude, Double latitude,
			Date time, String stationName, Integer subTransId);

	boolean submit(Integer vehicleId, Double longitude, Double latitude,
			Date time, Integer subTransId);

	GpsInfoDO[] queryBySubTransId(Integer subTransId);

}
