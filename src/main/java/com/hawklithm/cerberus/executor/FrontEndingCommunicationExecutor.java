package com.hawklithm.cerberus.executor;

import java.util.Map;

import com.hawklithm.cerberus.protocol.FrontEndingCommunicationProtocol;

public interface FrontEndingCommunicationExecutor {
	FrontEndingCommunicationProtocol<Map<String,Object>> execute(FrontEndingCommunicationProtocol<Map<String,Object>> message )
			throws Exception;
}
