package com.multiagent.hawklithm.rpc.manager;

public class RpcSystemProxy extends AbstractRpcInterceptor{
	private String interfaceName;
	private String version;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getInterfaceName() {
		return interfaceName;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}
}