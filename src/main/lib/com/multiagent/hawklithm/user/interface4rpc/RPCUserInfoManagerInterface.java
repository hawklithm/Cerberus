package com.multiagent.hawklithm.user.interface4rpc;

import com.multiagent.hawklithm.user.DO.SqlUserInfoDO;

public interface RPCUserInfoManagerInterface {
	/**
	 * 添加用户
	 * 
	 * @param userName
	 * @param password
	 * @param level
	 * @param isEmployee
	 * @param hospitalId
	 * @return
	 */
	public boolean addUser(String userName, String password, String level, boolean isEmployee,
			int hospitalId);

	/**
	 * 删除用户
	 * 
	 * @param userId
	 * @return
	 */
	public boolean deleteUser(int userId);

	/**
	 * 根据用户id查询用户信息
	 * 
	 * @param userId
	 * @return
	 */
	public SqlUserInfoDO selectUserById(int userId);

	/**
	 * 根据用户名查询用户信息
	 * 
	 * @param userName
	 * @return
	 */
	public SqlUserInfoDO selectUserByUserName(String userName);

	/**
	 * 根据信息查询用户信息
	 * 
	 * @param userId
	 * @param userName
	 * @param level
	 * @param isEmployee
	 * @param hospitalId
	 * @return
	 */
	public SqlUserInfoDO[] selectUser(Integer userId, String userName, String level,
			Boolean isEmployee, Integer hospitalId, Integer offset, Integer length);

	/**
	 * 修改用户密码
	 * 
	 * @param userId
	 * @param oldPassword
	 * @param newPassword
	 * @return
	 */
	boolean modifyPassword(int userId, String oldPassword, String newPassword);

	/**
	 * 修改用户信息，除密码外
	 * 
	 * @param userId
	 * @param userName
	 * @param level
	 * @param isEmployee
	 * @param hospitalId
	 * @return
	 */
	boolean modifyUserInfo(int userId, String userName, String level, boolean isEmployee,
			int hospitalId, boolean enable);

}
