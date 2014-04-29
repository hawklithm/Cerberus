package com.multiagent.hawklithm.leon.process.interface4rpc;

import com.multiagent.hawklithm.davinci.exceptioin.ModuleNotFoundException;
import com.multiagent.hawklithm.davinci.exceptioin.ProcessModuleNotFoundException;
import com.multiagent.hawklithm.leon.module.property.DO.ChangerAnnouncerPropertyArrayVersion;

public interface RPCProcessInfoManagerInterface {

	String[] getProcessInfo(String processName) throws ModuleNotFoundException;

	String getEquipmentInfoById(String processName, Integer id) throws ModuleNotFoundException;
	
	/**
	 *  获取buffered数据的接口
	 * @param processName
	 * @return
	 * @throws ModuleNotFoundException 
	 */
	ChangerAnnouncerPropertyArrayVersion[] getBufferedPropertyList(String processName) throws ModuleNotFoundException;
	
	
	Integer[] getMachineId(String processName) throws ProcessModuleNotFoundException;

}
