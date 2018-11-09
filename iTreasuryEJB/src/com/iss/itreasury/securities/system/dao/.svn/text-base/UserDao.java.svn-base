/*
 * Created on 2004-4-19
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.securities.system.dao;

import com.iss.itreasury.securities.util.SecuritiesDAO;
import com.iss.itreasury.securities.system.dataentity.UserInfo;
import com.iss.itreasury.securities.exception.SecuritiesDAOException;

/**
 * @author lgwang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class UserDao extends SecuritiesDAO
{
	
	public UserDao(){
		super("UserInfo");
	}
	
	/**
	 * change user password
	 * @param info
	 * @throws SecuritiesDAOException
	 */
	public void changePassword(UserInfo info)throws SecuritiesDAOException{
		try{
			update(info);
		}catch (Exception e){
			e.printStackTrace();
			throw new SecuritiesDAOException(e.getMessage(),e);
		}
	}
}
