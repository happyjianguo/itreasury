package com.iss.itreasury.util;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.toft.core2.transfer.design.ITransfer;
import com.toft.core2.transfer.impl.hessian.HessianTransferClient;

/**
 * 新奥ldap查询
 * @author wmzheng
 */
public class LdapUtil {

	/**
	 * 财司、网银新增用户时查询Ldap，查询该用户是否存在，存在true，不存在false
	 * @param loginName
	 * @return
	 */
	public static boolean isUserExist(HttpServletRequest request,String loginName) throws ITreasuryDAOException
	{
		boolean flag = false;
		String mgHessianURL = null;
		try {
			mgHessianURL = (String)request.getSession().getAttribute("MGHessianURL");
			HessianTransferClient client = new HessianTransferClient();
			ITransfer res = client.accessTransferObject(mgHessianURL+"?serviceName=queryLdapServlet", loginName);
			Map map = (Map)res;
			flag = ((Boolean)map.get("result")).booleanValue();
			
		} catch (Exception e) {
            e.printStackTrace();
            throw new ITreasuryDAOException("查询Ldap异常发生", e);
		}
		
		return flag;
	}
}
