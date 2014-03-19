package com.multiagent.hawklithm.rpc.DAO;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.multiagent.hawklithm.davinci.rpc.DO.RPCSystemProtocol;

public class Announcer {
	private Map<UUID, RPCSystemProtocol> map = new HashMap<UUID, RPCSystemProtocol>();

	synchronized public void clear() {
		map.clear();
	}

	synchronized public RPCSystemProtocol get(UUID uuid) {
		if (map.containsKey(uuid)) {
			return map.get(uuid);
		} else {
			return null;
		}
	}
	
	synchronized public void insert(RPCSystemProtocol object){
		map.put(object.uuid, object);
	}
}
