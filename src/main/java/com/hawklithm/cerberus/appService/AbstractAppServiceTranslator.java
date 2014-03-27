package com.hawklithm.cerberus.appService;

import com.hawklithm.cerberus.nettyservice.AppServiceHandler;
import com.hawklithm.cerberus.responsor.AppServiceResponsor;

public abstract class AbstractAppServiceTranslator implements AppServiceHandler {
	protected AppServiceResponsor responsor;
	@Override
	public void doExcute(AppServiceRequest request, AppServiceResponse response){
		response.setResponsor(responsor);
		execute(request, response);
	}
	public abstract void execute(AppServiceRequest request, AppServiceResponse response);
	public AppServiceResponsor getResponsor() {
		return responsor;
	}
	public void setResponsor(AppServiceResponsor responsor) {
		this.responsor = responsor;
	}


}
