package com.hawklithm.cerberus.executor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;

import com.hawklithm.cerberus.protocol.FrontEndingCommunicationProtocol;
import com.hawklithm.cerberus.protocol.FrontEndingRequestCondition;
import com.hawklithm.cerberus.protocol.OperateTypeConstant;
import com.multiagent.hawklithm.user.DO.SqlUserInfoDO;
import com.multiagent.hawklithm.user.interface4rpc.RPCUserInfoManagerInterface;

public class UserManagerExecutor implements FrontEndingCommunicationExecutor{


	private RPCUserInfoManagerInterface userInfoManager;

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
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	protected void query(FrontEndingCommunicationProtocol<Map<String,Object>> message,
			FrontEndingCommunicationProtocol<Map<String,Object>> result) throws Exception {
		if (message.getRows().size() > 1) {
			throw new Exception("查询条件只能包含一个订单信息");
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
		condition.getCondition().put("userId", userInfo.getUserId());
		condition.getCondition().put("userName", userInfo.getUserName());
		condition.getCondition().put("password", userInfo.getPassword());
		condition.getCondition().put("level", userInfo.getLevel());
		condition.getCondition().put("isEmployee", userInfo.getIsEmployee());
		condition.getCondition().put("hospitalId", userInfo.getHospitalId());
		condition.getCondition().put("enable", userInfo.getEnable());
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
			throw new Exception("数据中没有信息");
		}
		if (message.getRows().size() != 1
				&& message.getOperateType().equals(OperateTypeConstant.OPERATE_QUERY)) {
			throw new Exception("一次查询只能包含一个订单条件");
		}
		List<SqlUserInfoDO> ret = new ArrayList<SqlUserInfoDO>();
		for (Map<String,Object> index : message.getRows()) {
			FrontEndingRequestCondition msg=new FrontEndingRequestCondition(index);
			SqlUserInfoDO userInfo = new SqlUserInfoDO();
			if (msg.getCondition().containsKey("userId")) {
				userInfo.setUserId((Integer) msg.getCondition().get("userId"));
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

}
