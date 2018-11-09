/*
 * Created on 2004-7-29
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.system.usermanage.bizlogic;

import java.util.Collection;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.system.usermanage.dao.PrivilegeDAO;
import com.iss.itreasury.system.usermanage.dataentity.PrivilegeInfo;
import com.iss.itreasury.util.Constant;

/**
 * @author yiwang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class PrivilegeBean
{
	PrivilegeDAO m_dao = null;
	/**
	 * 
	 */
	public PrivilegeBean ( )
	{
		super ( ) ;
		// TODO Auto-generated constructor stub
		m_dao = new PrivilegeDAO();
	}
	public PrivilegeInfo findByPrivilegeID(long lPrivilegeID)throws ITreasuryDAOException
	{
		return (PrivilegeInfo)m_dao.findByID(lPrivilegeID , (new PrivilegeInfo ( )).getClass ( ));
	}
	public Collection findPrivilegeByCondition(PrivilegeInfo condition)throws ITreasuryDAOException
	{
		condition.setStatusID( Constant.RecordStatus.VALID );
		return m_dao.findByCondition(condition);
	}
}
