/*
 * Created on 2004-7-12
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.system.bizdelegation;

import java.util.Collection;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.system.user.bizlogic.UserBean;
import com.iss.itreasury.system.user.dataentity.UserInfo;

/**
 * @author yiwang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class UserDelegation
{
	UserBean bean ;
	/**
	 * 
	 */
	public UserDelegation ( )
	{
		super ( ) ;
		// TODO Auto-generated constructor stub
		bean = new UserBean();
	}
	public UserInfo findUserByID(long Id) throws ITreasuryDAOException
	{
		return bean.findUserByID(Id);
	}
	public Collection findAllUsers() throws ITreasuryDAOException
	{
		return bean.findAllUsers();
	}
	public Collection findUserByConditioni(UserInfo condition) throws ITreasuryDAOException
	{
		return bean.findUserByCondition(condition);
	}	
	public void updateUserInfo(UserInfo condition) throws ITreasuryDAOException
	{
		bean.updateUserInfo(condition);
	}
}
