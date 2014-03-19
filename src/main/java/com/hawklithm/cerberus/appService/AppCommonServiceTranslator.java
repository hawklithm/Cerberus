package com.hawklithm.cerberus.appService;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;

import com.google.gson.Gson;
import com.hawklithm.cerberus.executor.FrontEndingCommunicationExecutor;
import com.hawklithm.cerberus.protocol.FrontEndingCommunicationProtocol;

public class AppCommonServiceTranslator extends AbstractAppServiceTranslator{

	protected String targetUrl;
	protected Gson gson = new Gson();
	protected FrontEndingCommunicationExecutor executor;

	@Override
	public String getTargetUrl() {
		return targetUrl;
	}

	public void setTargetUrl(String targetUrl) {
		this.targetUrl = targetUrl;
	}

	public FrontEndingCommunicationExecutor getExecutor() {
		return executor;
	}

	public void setExecutor(FrontEndingCommunicationExecutor executor) {
		this.executor = executor;
	}

	@Override
	public void execute(AppServiceRequest request, AppServiceResponse response) {
		try {
			FrontEndingCommunicationProtocol<Map<String,Object>> message = gson
					.fromJson(request.getContent(),
							FrontEndingCommunicationProtocol.class);
			FrontEndingCommunicationProtocol<Map<String,Object>> result;
			result = executor.execute(message);
			response.write(gson.toJson(result));
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
