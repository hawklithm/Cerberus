package com.hawklithm.cerberus.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.exc.UnrecognizedPropertyException;

import com.google.gson.Gson;
import com.hawklithm.cerberus.executor.FrontEndingCommunicationExecutor;
import com.hawklithm.cerberus.protocol.FrontEndingCommunicationProtocol;

/**
 * ½»»¥servlet
 * 
 * @author hawklithm 2013-12-17ÏÂÎç2:27:58
 */
public class MessageTranslateServlet extends AbstractAuthenticateProcessingServlet {

	private static final long serialVersionUID = 1L;
//	public static final String OPERATE_QUERY = "operateQuery", OPERATE_SUBMIT = "operateSubmit",
//			OPERATE_DELETE = "operateDelete", OPERATE_MODIFY = "operateModify";
	private Gson gson = new Gson();
	private FrontEndingCommunicationExecutor executor;

	@Override
	protected void authenticateDoGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

	}

	@Override
	protected void authenticateDoPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
		String json = "";
		if (br != null) {
			json = br.readLine();
		}
		System.out.println(json);
		ObjectMapper mapper = new ObjectMapper();
		try{
		@SuppressWarnings("unchecked")
		FrontEndingCommunicationProtocol<Map<String,Object>> message = mapper.readValue(json,
				FrontEndingCommunicationProtocol.class);
		FrontEndingCommunicationProtocol<Map<String,Object>> result =executor.execute(message);
		response.setContentType("application/json");
		mapper.writeValue(response.getOutputStream(), gson.toJson(result));
		}catch(UnrecognizedPropertyException e){
			e.printStackTrace();
		}
	}

	@Override
	protected void accessDenied(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		System.out.println("deny");
	}

	public FrontEndingCommunicationExecutor getExecutor() {
		return executor;
	}

	public void setExecutor(FrontEndingCommunicationExecutor executor) {
		this.executor = executor;
	}


}
