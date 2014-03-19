package com.multiagent.hawklithm.rpc.manager;

import java.util.ArrayList;
import java.util.List;

import com.multiagent.hawklithm.exception.RPCInterfaceNotFoundException;

public class RpcRegManager {

	private List<RpcSystemProxy> regList = new ArrayList<RpcSystemProxy>();

	private List<Class<?>> proxyInterfacesList = new ArrayList<Class<?>>();

	public List<Class<?>> getProxyInterfacesList() {
		return proxyInterfacesList;
	}

	public Class<?>[] getProxyInterfacesArray() {
		int length = proxyInterfacesList.size();
		Class<?>[] classes = new Class<?>[length];
		for (int index = 0; index < length; index++) {
			classes[index] = proxyInterfacesList.get(index);
		}
		return classes;
	}

	public String getVersion(String interfaceName)
			throws RPCInterfaceNotFoundException {
		for (RpcSystemProxy object : regList) {
			if (object.getInterfaceName().equals(interfaceName)) {
				return object.getVersion();
			}
		}
		throw new RPCInterfaceNotFoundException(interfaceName);
	}

	public void setProxyInterfacesList(List<Class<?>> proxyInterfacesList) {
		this.proxyInterfacesList = proxyInterfacesList;
	}

	public boolean regist(RpcSystemProxy proxy) {
		try {
			if (!regList.contains(proxy)) {
				try {
					proxyInterfacesList.add(Class.forName(proxy
							.getInterfaceName()));
				} catch (ClassNotFoundException e) {
					// TODO 打印日志
					throw e;
				}
				regList.add(proxy);
			}
		} catch (Exception e) {
			// TODO 添加打印日志
			return false;
		}
		return true;
	}

	public List<RpcSystemProxy> getRegList() {
		return regList;
	}

	public void setRegList(List<RpcSystemProxy> regList) {
		this.regList = regList;
	}
}