
package com.multiagent.hawklithm.pac.interface4rpc;
import java.util.HashMap;
import java.util.Map;

/**
 * RPC�����ӿڣ�������ִ��
 * 
 * @author hawklithm
 * 
 */
public interface RPCPackageInfoManagerInterface {
	public static Map<Integer, String> PACKAGE_MAPPING = new HashMap<Integer, String>() {
		private static final long serialVersionUID = -7478175425827764450L;
		{
			PACKAGE_MAPPING.put(1, "����������");
			PACKAGE_MAPPING.put(2, "�м�������");
			PACKAGE_MAPPING.put(3, "�߼�������");
		}
	};
}
