package com.hawklithm.cerberus.nettyservice;

import com.hawklithm.cerberus.appService.AppServiceRequest;
import com.hawklithm.cerberus.appService.AppServiceResponse;

public interface AppServiceHandler {
	public void doExcute(AppServiceRequest request,AppServiceResponse response);
	public String getTargetUrl();
}
