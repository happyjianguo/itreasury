package com.iss.itreasury.util;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.toft.core2.UserInfo;
import com.toft.core2.transfer.design.ITransfer;
import com.toft.core2.transfer.impl.hessian.HessianTransferClient;

/**
 * 针对SDC操作的工具类
 * @author 马现福
 */
public class SDCUtil {
	
	private static String mgHessianURL;
	
	public String getMgHessianURL() {
		return mgHessianURL;
	}


	public void setMgHessianURL(String mgHessianURL) {
		SDCUtil.mgHessianURL = mgHessianURL;
	}


	/**
	*@author <a href="mailto:xfma3@isoftstone.com">马现福</a>
	*@version 创建时间：Apr 2, 2011 2:52:20 PM
	*@功能：通过Hessian获取密码策略信息Map
	*@param request
	*@param identifer
	*@return
	*@throws ITreasuryDAOException
	 */
	public static Map getPasswordPolicyMap(HttpServletRequest request) throws ITreasuryDAOException
	{
		Map passwordPolicyMap = null;
		try {
			if(mgHessianURL == null || "".equals(mgHessianURL)){
				mgHessianURL = (String)request.getSession().getAttribute("MGHessianURL");
			}
			UserInfo userInfo = (UserInfo)request.getSession().getAttribute("Toft_SessionKey_UserData");
			String identifer = (String)userInfo.getAttribute("pswdPolicyId");
			HessianTransferClient client = new HessianTransferClient();
			ITransfer res = client.accessTransferObject(mgHessianURL+"?serviceName=queryPasswordPolicyServlet", identifer);
			passwordPolicyMap = (Map)res;
			
		} catch (Exception e) {
            e.printStackTrace();
            throw new ITreasuryDAOException("获取密码策略异常发生", e);
		}
		
		return passwordPolicyMap;
	}
	
	/**
	*@author <a href="mailto:xfma3@isoftstone.com">马现福</a>
	*@version 创建时间：Apr 2, 2011 3:21:32 PM
	*@功能：判断是否需要Ldap验证
	*@param request
	*@return
	*@throws ITreasuryDAOException
	 */
	public static boolean isNeedLDAP(HttpServletRequest request) throws ITreasuryDAOException
	{
		boolean flag = false;
		try {
			Map map = getPasswordPolicyMap(request);
			if(map != null && "1".equals(map.get("NEEDLDAP"))){
				flag = true;
			}
			
		} catch (Exception e) {
            e.printStackTrace();
            throw new ITreasuryDAOException("判断是否需要Ldap验证发生异常", e);
		}
		
		return flag;
	}
	
	/**
	*@author <a href="mailto:xfma3@isoftstone.com">马现福</a>
	*@version 创建时间：Apr 2, 2011 3:21:32 PM
	*@功能：判断是否需要指纹验证
	*@param request
	*@return
	*@throws ITreasuryDAOException
	 */
	public static boolean isNeedFingerPrint(HttpServletRequest request) throws ITreasuryDAOException
	{
		boolean flag = false;
		try {
			Map map = getPasswordPolicyMap(request);
			if(map != null){
				if("1".equals(map.get("FINGEROPEN"))){//指纹认证是否开启 1代表开启
					if("1".equals(map.get("NEEDFINGERPRINT"))){//密码策略中是否勾选使用指纹 1代表勾选
						flag = true;
					}
				}
			}
			
		} catch (Exception e) {
            e.printStackTrace();
            throw new ITreasuryDAOException("判断是否需要指纹验证发生异常", e);
		}
		
		return flag;
	}
	
	
	

}
