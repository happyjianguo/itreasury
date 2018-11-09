/**
 * 
 */
package com.iss.itreasury.encrypt.impl;

import java.sql.Connection;

import com.iss.itreasury.util.IException;

/**
 * @author tanxin
 *
 */
public interface Encrypt {
	
	/**
	 * 系统校验密码正确性
	 * @param strLoginNo 用户登录名
	 * @param strCheck 登录输入的密码
	 * @return 如果密码匹配成功返回 true,否则返回 false
	 */
	public boolean checkPassword(String strLoginNo,String strCheck) throws Exception;
	
	/**
	 * 系统校验密码正确性
	 * @param strLoginNo 用户登录名
	 * @param strCheck 登录输入的用户名
	 * @return 如果密码匹配成功返回 true,否则返回 false
	 */	
	public boolean checkOBPassword(String strLoginNo,String strCheck)throws Exception;
	
	/**
	 * 更新密码，把加密得到的二进制数据写入数据库，操作数据库表userinfo
	 * @param lUserID 用户id
	 * @param newPass 密码
	 */
	public void updatePassword(long lUserID,byte[] newPass) throws IException;
	
	/**
	 * 
	 * 更新密码，把加密得到的二进制数据写入数据库，操作数据库表ob_user
	 * @param lUserID 网银用户id
	 * @param newPass 密码
	 */
	public void updateOBPassword(long lUserID,byte[] newPass) throws IException;
	
	/**
	 * 更新密码，把加密得到的二进制数据写入数据库，操作数据库表userinfo
	 * @param lUserID 用户id
	 * @param newPass 密码
	 */
	public void updatePassword(long lUserID,String newPass) throws IException;
	
	/**
	 * 
	 * 更新密码，把加密得到的二进制数据写入数据库，操作数据库表ob_user
	 * @param lUserID 网银用户id
	 * @param newPass 密码
	 */
	public void updateOBPassword(long lUserID,String newPass) throws IException;
	
	/**
	 * 根据登录名取得用户密码，操作数据库表 userinfo
	 * @param strLoginNo 用户登录名
	 * @return 密码
	 */
	public byte[] getPasswordByLoginNo(String strLoginNo);
	
	/**
	 * 根据登录名取得用户密码，操作数据库表 userinfo
	 * @param strLoginNo 用户登录名
	 * @return 密码
	 */
	public String getPasswordByLogin(String strLoginNo);
	
	/**
	 * 根据网银登录名取得用户密码，操作数据库表 ob_user
	 * @param strLoginNo 网银用户登录名
	 * @return 密码
	 */
	public byte[] getOBPasswordByLoginNo(String strLoginNo);
	
	/**
	 * 根据网银登录名取得用户密码，操作数据库表 ob_user
	 * @param strLoginNo 网银用户登录名
	 * @return 密码
	 */
	public String getOBPasswordByLogin(String strLoginNo);
	
	/**
	 * 修改系统用户的密码
	 * @param lUserID 用户id
	 * @param strPass 密码
	 * @throws IException
	 */
	public void changeUserPassword(long lUserID,String strPass) throws Exception;
	
	/**
	 * 修改系统用户的密码 新增Connection 连接
	 * @param lUserID 用户id
	 * @param strPass 密码
	 * @throws IException
	 */
	public void changeUserPassword(long lUserID,String strPass,Connection conn) throws Exception;	
	
	/**
	 * 修改网银用户的密码
	 * @param lUserID 用户id
	 * @param strPass 密码
	 * @throws IException
	 */
	public void changeOBUserPassword(long lUserID,String strPass) throws Exception;
	/**
	 * 修改网银用户的密码 新增connection参数
	 * @param lUserID
	 * @param strPass
	 * @param conn
	 * @throws Exception
	 */
	public void changeOBUserPassword(long lUserID,String strPass,Connection conn) throws Exception;
	/**
	 * 密码初始化，将所有系统用户的密码初始化为 strPass
	 * @param strPass
	 */
	public void initPassword(String strPass);
	
	/**
	 * 密码初始化，将所有网银用户的密码初始化为 strPass
	 * @param strPass
	 */
	public void initOBPassword(String strPass);

}
