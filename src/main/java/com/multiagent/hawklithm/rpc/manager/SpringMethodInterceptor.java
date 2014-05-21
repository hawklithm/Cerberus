package com.multiagent.hawklithm.rpc.manager;

import java.lang.reflect.Constructor;
import java.util.UUID;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.util.StringUtils;

import com.google.gson.Gson;
import com.multiagent.hawklithm.davinci.rpc.DO.RPCSystemProtocol;
import com.multiagent.hawklithm.rpc.DAO.RPCSystemProtocolPackageUtil;
import com.multiagent.hawklithm.rpc.net.RPCClient;
/*
 * �ڵ��Կͻ����������õ�ĳ������ʱ�����netty��xinhua���д���,
 * �õ���Ӧ�ķ���ֵ
 * 
 */
public class SpringMethodInterceptor implements MethodInterceptor {

	private RpcRegManager rpcProxyRegManager;
	private RPCClient rpcClient;
	// private int count=0;
	private RPCLockManager lockManager;
	private Gson gson = new Gson();

	public Object invoke(MethodInvocation inv) throws Throwable {
		// System.out.println("���ͺ���������Ϣ");
		// System.out.println(inv.getMethod().getDeclaringClass().getName() +
		// " " + inv.getArguments());
		String version = rpcProxyRegManager.getVersion(inv.getMethod().getDeclaringClass()
				.getName());
		RPCSystemProtocol message = RPCSystemProtocolPackageUtil.getRPCProtocolPackage(
				inv.getMethod(), inv.getArguments(), version);
		rpcClient.sendRPCProtocol(message);
		UUID uuidOrigin = message.uuid;
		RPCSystemProtocol recvMessage = lockManager.waitforAnswer(uuidOrigin);
		System.out.println("Ҧ����");
		System.out.println("get return message");
		Class<?> returnType = inv.getMethod().getReturnType();
		String exceptionString = recvMessage.getException();
		String exceptionTypeString = recvMessage.getExceptionType();
		if (!StringUtils.isEmpty(exceptionTypeString)) {
			System.out.println("exception happen: " + exceptionString);
			System.out.println("type: " + recvMessage.getExceptionType());
			Class<?> clas = Class.forName(exceptionTypeString);
			Constructor<?> con = clas.getConstructor(String.class);
			Exception e = (Exception) con.newInstance(exceptionString);
			e.getStackTrace();
			// System.out.println(inv.getMethod().getName());
			// System.out.println(inv.getMethod().getDeclaringClass().getName());
			e.setStackTrace(changeStackTrace(e.getStackTrace(), inv.getMethod().getName()));
			throw e;
		}
		System.out.println("RPC client get return message: "+recvMessage.getReturnObject() +"\n type is : "+returnType.getName() );
		Object ret = gson.fromJson(recvMessage.getReturnObject(), returnType);

		return ret;
		// count++;
		// System.out.println(count);
		// rpcClient.sendRPCProtocol(message);
		// return null;
	}

	private StackTraceElement[] changeStackTrace(StackTraceElement[] source, String startMethodName) {
		int index = 0;
		for (StackTraceElement element : source) {
			if (element.getMethodName().equals(startMethodName)
					&& element.getClassName().indexOf("com.sun.proxy") >= 0) {
				break;
			}
			index++;
		}
		StackTraceElement[] ret = null;// =new
										// StackTraceElement[source.length-index];
		if (index < source.length) {
			ret = ArrayUtils.subarray(source, index, source.length);
		}
		return ret;
	}

	public RpcRegManager getRpcProxyRegManager() {
		return rpcProxyRegManager;
	}

	public void setRpcProxyRegManager(RpcRegManager rpcProxyRegManager) {
		this.rpcProxyRegManager = rpcProxyRegManager;
	}

	public RPCClient getRpcClient() {
		return rpcClient;
	}

	public void setRpcClient(RPCClient rpcClient) {
		this.rpcClient = rpcClient;
	}

	public RPCLockManager getLockManager() {
		return lockManager;
	}

	public void setLockManager(RPCLockManager lockManager) {
		this.lockManager = lockManager;
	}

}
