package com.hawklithm.cerberus.video.manager;

import java.util.Date;

import com.hawklithm.cerberus.video.dataobject.VideoInfoDO;

public interface VideoManager {
	VideoInfoDO[] getVideoByTimeAndMachineId(Date start,Date end,Integer machineId);
	VideoInfoDO[] getVideoByVideoName(String name);
	VideoInfoDO[] getAllVideoDuringPeriod(Date start,Date end);
}
