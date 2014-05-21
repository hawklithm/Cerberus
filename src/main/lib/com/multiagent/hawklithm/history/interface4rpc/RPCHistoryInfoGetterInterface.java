package com.multiagent.hawklithm.history.interface4rpc;

import com.multiagent.hawklithm.history.dataobject.ItemAllHistoryInfoDO;
import com.multiagent.hawklithm.history.dataobject.PackageAllHistoryInfoDO;

public interface RPCHistoryInfoGetterInterface {
	PackageAllHistoryInfoDO getPackageAllHistoryOnSpecifiedMachine(Integer packId,Integer machineId,Integer length,Integer offset);
	ItemAllHistoryInfoDO getItemAllHistoryOnSpecifiedMachine(Integer itemId, Integer machineId,
			Integer length, Integer offset);
}
