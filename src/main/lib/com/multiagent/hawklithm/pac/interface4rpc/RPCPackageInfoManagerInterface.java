
package com.multiagent.hawklithm.pac.interface4rpc;
import java.util.HashMap;
import java.util.Map;

/**
 * RPC订单接口，服务器执行
 * 
 * @author hawklithm
 * 
 */
public interface RPCPackageInfoManagerInterface {
	public static Map<Integer, String> PACKAGE_MAPPING = new HashMap<Integer, String>() {
		private static final long serialVersionUID = -7478175425827764450L;
		{
			PACKAGE_MAPPING.put(1, "初级手术包");
			PACKAGE_MAPPING.put(2, "中级手术包");
			PACKAGE_MAPPING.put(3, "高级手术包");
		}
	};
}
