package com.hawklithm.cerberus.executor;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;

import com.hawklithm.cerberus.protocol.FrontEndingCommunicationProtocol;

public interface FrontEndingCommunicationExecutor {
	FrontEndingCommunicationProtocol<Map<String,Object>> execute(FrontEndingCommunicationProtocol<Map<String,Object>> message )
			throws ServletException, IOException;
}
