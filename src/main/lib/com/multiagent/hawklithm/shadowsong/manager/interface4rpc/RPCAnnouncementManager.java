package com.multiagent.hawklithm.shadowsong.manager.interface4rpc;

import com.multiagent.hawklithm.leon.module.SimpleMessageTransportBufferModule.SimpleMessage;
import com.multiagent.hawklithm.machineInfo.DO.MachineInfoDO;

public interface RPCAnnouncementManager {

	SimpleMessage[] getAnnoucement();

	void sendOutMachineInfoChangeAnnouncement(MachineInfoDO machineInfo);

}
