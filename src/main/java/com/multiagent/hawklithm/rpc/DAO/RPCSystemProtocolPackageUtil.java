package com.multiagent.hawklithm.rpc.DAO;

import java.lang.reflect.Method;

import org.apache.commons.lang3.ClassUtils;

import com.google.gson.Gson;
import com.multiagent.hawklithm.davinci.rpc.DO.ParameterTypeDatas;
import com.multiagent.hawklithm.davinci.rpc.DO.RPCSystemProtocol;

public class RPCSystemProtocolPackageUtil {
	private final static Gson gson = new Gson();

	public static RPCSystemProtocol getRPCProtocolPackage(Method method, Object[] params,
			String version) {
		// System.out.println("getName():"+method.getName());
		// System.out.println("getClass():"+method.getDeclaringClass().toString());
		RPCSystemProtocol ret = new RPCSystemProtocol();
		ret.setMethodName(method.getName());
		int len = method.getParameterTypes().length;
		Class<?>[] parameterTypes = method.getParameterTypes();
		ParameterTypeDatas paramTypes = new ParameterTypeDatas(len);
		String[] paramStrs = new String[len];
		for (int i = 0; i < len; i++) {
			if (parameterTypes[i].isPrimitive()) {
				paramTypes.setPrimitive(i);
				paramTypes.setParamType(i, ClassUtils.primitiveToWrapper(parameterTypes[i])
						.getName());
			} else {
				paramTypes.setParamType(i, parameterTypes[i].getName());
			}

			paramStrs[i] = gson.toJson(params[i]);
		}
		ret.setParamsType(paramTypes);
		ret.setClassName(method.getDeclaringClass().getName());
		ret.setParameters(paramStrs);
		ret.setVersion(version);
		return ret;
	}
}
