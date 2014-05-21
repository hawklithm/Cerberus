package com.hawklithm.cerberus.executor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;

import com.hawklithm.cerberus.protocol.FrontEndingCommunicationProtocol;
import com.hawklithm.cerberus.protocol.FrontEndingRequestCondition;
import com.hawklithm.cerberus.protocol.OperateTypeConstant;
import com.multiagent.hawklithm.staff.DO.StaffInfoDO;
import com.multiagent.hawklithm.staff.interface4rpc.RPCStaffInfoManagerInterface;
import com.multiagent.hawklithm.user.DO.SqlUserInfoDO;
import com.multiagent.hawklithm.user.interface4rpc.RPCUserInfoManagerInterface;

public class UserManagerExecutor implements FrontEndingCommunicationExecutor{


	private RPCUserInfoManagerInterface userInfoManager;
    private RPCStaffInfoManagerInterface staffInfoManager;
	@Override
	public FrontEndingCommunicationProtocol<Map<String,Object>> execute(FrontEndingCommunicationProtocol<Map<String,Object>> message)
			throws ServletException, IOException {
		FrontEndingCommunicationProtocol<Map<String,Object>> result = new FrontEndingCommunicationProtocol<Map<String,Object>>();
		try {
			if (message.getOperateType().equals(OperateTypeConstant.OPERATE_SUBMIT)) {
				submit(message);
				result.setStatusOk();
			} else if (message.getOperateType().equals(OperateTypeConstant.OPERATE_DELETE)) {
				delete(message);
				result.setStatusOk();
			} else if (message.getOperateType().equals(OperateTypeConstant.OPERATE_QUERY)) {
				query(message, result);
				result.setStatusOk();
			} else if (message.getOperateType().equals(OperateTypeConstant.OPERATE_MODIFY)) {
				update(message);
				result.setStatusOk();
			}
			else if(message.getOperateType().equals(OperateTypeConstant.OPERATE_LOGIN)){
				
				login(message,result);
				result.setStatusOk();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	/*
	 * ר�����ڿͻ��˵ĵ�½
	 */
	protected void login(FrontEndingCommunicationProtocol<Map<String,Object>> message,
			FrontEndingCommunicationProtocol<Map<String,Object>> result) throws Exception {
		if(message.getRows().size()>1){
			throw new Exception("��ѯ����ֻ�ܰ���һ��Ա����Ϣ");
		}
		SqlUserInfoDO msg=getOrderCluster(message).get(0);
		SqlUserInfoDO u=null;
		if (msg.getUserId() != null) {
		  u=userInfoManager.selectUserById(msg.getUserId());
		} else if (msg.getUserName() != null) {
			u=userInfoManager.selectUserByUserName(msg.getUserName());
		} else {
			SqlUserInfoDO[] clusters = userInfoManager.selectUser(null, null, msg.getLevel(),
					msg.getIsEmployee(), msg.getHospitalId(), message.getOffset(),
					message.getLength());
		}
		if(u.getIsEmployee()){
			StaffInfoDO[] infos = staffInfoManager.queryById(u.getStaffId());
			for (StaffInfoDO index : infos) {
				result.getRows().add(getRequesting(index).toMapping());
			}
		}
	}
	private FrontEndingRequestCondition getRequesting(StaffInfoDO info) {
		FrontEndingRequestCondition condition = new FrontEndingRequestCondition();
		if(info!=null){
			condition.getCondition().put("staffId", info.getStaffId());
			condition.getCondition().put("staffName", info.getStaffName());
			condition.getCondition().put("staffPhoneNumber", info.getStaffPhoneNumber());
			condition.getCondition().put("staffGender", info.getStaffGender());
			condition.getCondition().put("staffAge", info.getStaffAge());
			condition.getCondition().put("staffDepartmentName", info.getStaffDepartmentName());
			condition.getCondition().put("userIconPath", info.getUserIconPath());
	
		}

		return condition;
	}
	protected void query(FrontEndingCommunicationProtocol<Map<String,Object>> message,
			FrontEndingCommunicationProtocol<Map<String,Object>> result) throws Exception {
		if (message.getRows().size() > 1) {
			throw new Exception("��ѯ����ֻ�ܰ���һ��������Ϣ");
		}
		SqlUserInfoDO msg = getOrderCluster(message).get(0);
		if (msg.getUserId() != null) {
			result.getRows().add(
					getFrontEndingRequest(userInfoManager.selectUserById(msg.getUserId())).toMapping());
		} else if (msg.getUserName() != null) {
			result.getRows().add(
					getFrontEndingRequest(userInfoManager.selectUserByUserName(msg.getUserName())).toMapping());
		} else {
			SqlUserInfoDO[] clusters = userInfoManager.selectUser(null, null, msg.getLevel(),
					msg.getIsEmployee(), msg.getHospitalId(), message.getOffset(),
					message.getLength());
			for (SqlUserInfoDO index : clusters) {
				result.getRows().add(getFrontEndingRequest(index).toMapping());
			}
		}
	}

	private FrontEndingRequestCondition getFrontEndingRequest(SqlUserInfoDO userInfo) {
		FrontEndingRequestCondition condition = new FrontEndingRequestCondition();
		if(userInfo!=null){
			condition.getCondition().put("userId", userInfo.getUserId());
			condition.getCondition().put("userName", userInfo.getUserName());
			condition.getCondition().put("password", userInfo.getPassword());
			condition.getCondition().put("level", userInfo.getLevel());
			condition.getCondition().put("isEmployee", userInfo.getIsEmployee());
			condition.getCondition().put("hospitalId", userInfo.getHospitalId());
			condition.getCondition().put("enable", userInfo.getEnable());
		    condition.getCondition().put("staffId",userInfo.getStaffId());      
		}

		return condition;
	}

	protected void delete(FrontEndingCommunicationProtocol<Map<String,Object>> message)
			throws Exception {
		for (Map<String,Object> index : message.getRows()) {
			FrontEndingRequestCondition request=new FrontEndingRequestCondition(index);
			if (request.getCondition().containsKey("userId")) {
				userInfoManager.deleteUser((Integer) request.getCondition().get("userId"));
			}
		}
	}

	protected void update(FrontEndingCommunicationProtocol<Map<String,Object>> message)
			throws Exception {
		List<SqlUserInfoDO> users = getOrderCluster(message);
		for (SqlUserInfoDO user : users) {
			userInfoManager.modifyUserInfo(user.getUserId(), user.getUserName(), user.getLevel(),
					user.getIsEmployee(), user.getHospitalId(), user.getEnable());
		}
	}

	protected void submit(FrontEndingCommunicationProtocol<Map<String,Object>> message)
			throws Exception {
		List<SqlUserInfoDO> users = getOrderCluster(message);
		for (SqlUserInfoDO user : users) {
			userInfoManager.addUser(user.getUserName(), user.getPassword(), user.getLevel(),
					user.getIsEmployee(), user.getHospitalId());
		}
	}

	protected List<SqlUserInfoDO> getOrderCluster(
			FrontEndingCommunicationProtocol<Map<String,Object>> message) throws Exception {
		if (message.getRows().size() == 0) {
			throw new Exception("������û����Ϣ");
		}
		if (message.getRows().size() != 1
				&& message.getOperateType().equals(OperateTypeConstant.OPERATE_QUERY)) {
			throw new Exception("һ�β�ѯֻ�ܰ���һ����������");
		}
		List<SqlUserInfoDO> ret = new ArrayList<SqlUserInfoDO>();
		for (Map<String,Object> index : message.getRows()) {
			FrontEndingRequestCondition msg=new FrontEndingRequestCondition(index);
			SqlUserInfoDO userInfo = new SqlUserInfoDO();
			if (msg.getCondition().containsKey("userId")) {
			     userInfo.setUserId(Double.valueOf((Double)msg.getCondition().get("userId")).intValue());
				//userInfo.setUserId((Integer) msg.getCondition().get("userId"));
			     }
			if (msg.getCondition().containsKey("userName")) {
				userInfo.setUserName((String) msg.getCondition().get("userName"));
			}
			if (msg.getCondition().containsKey("password")) {
				userInfo.setPassword((String) msg.getCondition().get("password"));
			}
			if (msg.getCondition().containsKey("isEmployee")) {
				userInfo.setIsEmployee((Boolean) msg.getCondition().get("isEmployee"));
			}
			if (msg.getCondition().containsKey("hospitalId")) {
				userInfo.setHospitalId((Integer) msg.getCondition().get("hospitalId"));
			}
			if (msg.getCondition().containsKey("role")) {
				userInfo.setLevel((String) msg.getCondition().get("role"));
			}
			if (msg.getCondition().containsKey("enable")) {
				userInfo.setEnable((Boolean) msg.getCondition().get("enable"));
			}
			if (msg.getCondition().containsKey("staffId")) {
				userInfo.setEnable((Boolean) msg.getCondition().get("staffId"));
			}
			ret.add(userInfo);
		}
		return ret;
	}

	public RPCUserInfoManagerInterface getUserInfoManager() {
		return userInfoManager;
	}

	public void setUserInfoManager(RPCUserInfoManagerInterface userInfoManager) {
		this.userInfoManager = userInfoManager;
	}
	public RPCStaffInfoManagerInterface getStaffInfoManager() {
		return staffInfoManager;
	}
	public void setStaffInfoManager(RPCStaffInfoManagerInterface staffInfoManager) {
		this.staffInfoManager = staffInfoManager;
	}
}
