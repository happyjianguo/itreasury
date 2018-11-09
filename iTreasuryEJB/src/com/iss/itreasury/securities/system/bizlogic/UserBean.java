/*
 * Created on 2004-4-20
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.securities.system.bizlogic;

import com.iss.itreasury.securities.exception.*;
import com.iss.itreasury.securities.system.dataentity.*;
import com.iss.itreasury.securities.system.dao.*;
/**
 * @author lgwang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class UserBean
{
	
	
	public UserBean(){
		
	}
	
	public void changePassword(UserInfo info)throws SecuritiesException{
		UserDao dao = null;
		if (info.getSPassword() == null 
				|| info.getSPassword().equals("") 
				|| info.getSPassword().length()<6){
			throw new SecuritiesException("您希望更改的密码非法,请输入正确的密码",null);
		}
		try{
			dao = new UserDao();
			dao.changePassword(info);
		}catch(SecuritiesDAOException e){
			throw new SecuritiesException("更改用户密码失败",e);
		}
	}
}
