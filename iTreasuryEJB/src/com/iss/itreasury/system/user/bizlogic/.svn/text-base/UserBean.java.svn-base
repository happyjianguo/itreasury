/*
 * Created on 2004-7-12
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.system.user.bizlogic ;
import java.sql.SQLException;
import java.util.Collection;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.system.user.dao.UserInfoDAO;
import com.iss.itreasury.system.user.dataentity.UserInfo;
import com.iss.itreasury.util.Constant;
/**
 * @author yiwang
 * 
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
public class UserBean
{
	UserInfoDAO	dao ;
	/**
	 *  
	 */
	public UserBean ( )
	{
		super ( ) ;
		// TODO Auto-generated constructor stub
		dao = new UserInfoDAO ( "userinfo" , true ) ;
	}
	
	
	
	public UserInfo findUserByID ( long lUserID )  throws ITreasuryDAOException
	{
		return (UserInfo) dao.findByID ( lUserID , (new UserInfo ( )).getClass ( ) ) ;
	}
	
	/**
	 *  add by fxzhang 2007-1-8
	 * @param lUserID
	 * @return
	 * @throws Exception
	 */
	public String getNameByID ( long lUserID ) throws Exception
	{
		return dao.getNameByID( lUserID );
	}
	
	public Collection findAllUsers ( ) throws ITreasuryDAOException
	{
		UserInfo condition = new UserInfo ( ) ;
		//
		condition.setStatusID ( Constant.RecordStatus.VALID ) ;
		return dao.findByCondition ( condition ) ;
	}
	public Collection findUserByCondition ( UserInfo condition ) throws ITreasuryDAOException
	{
		return dao.findByCondition ( condition ) ;
	}
	public void updateUserInfo(UserInfo condition) throws ITreasuryDAOException
	{
		dao.update(condition);
	}
	

}