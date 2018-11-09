package com.iss.itreasury.fcinterface.bankportal.privilegemgt.bizlogic;

import java.sql.Connection;
import java.sql.SQLException;

import com.iss.itreasury.fcinterface.bankportal.privilegemgt.dao.DataPrivilegeDAO;
import com.iss.itreasury.fcinterface.bankportal.privilegemgt.dataentity.DataPrivilegeInfo;
import com.iss.itreasury.fcinterface.bankportal.sysframe.exp.business.BusinessException;
import com.iss.itreasury.fcinterface.bankportal.util.DAOFactory;
import com.iss.itreasury.fcinterface.bankportal.util.Database;

public class DataPrivilegeBiz
{
	private static Boolean lock = new Boolean(true);
	public void addPrivilege(DataPrivilegeInfo privilegeInfo) throws BusinessException
	{
		synchronized(lock)
		{
			Connection conn = null;	
			try
			{		
				conn = Database.getConnection();
				conn.setAutoCommit(false);
				DataPrivilegeDAO dataPrivilegeDAO = (DataPrivilegeDAO)DAOFactory.getDAOImpl(DataPrivilegeDAO.class, conn);
				dataPrivilegeDAO.addPrivilege(privilegeInfo);
				conn.commit();			
			}catch(Exception e)
			{
				try
				{
					conn.rollback();
				} catch (SQLException e1)
				{
				}
				e.printStackTrace();
				throw new BusinessException("保存权限信息时出现异常："+e.getMessage(), e);
			}
			finally
			{
				try
				{
					if(conn != null)
					{
					    conn.close();
					}
				} catch (SQLException e1)
				{
				}
			}
		}
	}
	
	public long autoAuthorize(long userID, long officeID) throws BusinessException
	{	
		synchronized(lock)
		{
			long userGroupID = -1;
			Connection conn = null;	
			try
			{		
				conn = Database.getConnection();
				conn.setAutoCommit(false);
				DataPrivilegeDAO dataPrivilegeDAO = (DataPrivilegeDAO)DAOFactory.getDAOImpl(DataPrivilegeDAO.class, conn);
				userGroupID = dataPrivilegeDAO.autoAuthorize(userID,officeID);
				conn.commit();	
				return userGroupID;
			}catch(Exception e)
			{
				try
				{
					conn.rollback();
				} catch (SQLException e1)
				{
				}
				e.printStackTrace();
				throw new BusinessException("自动用户授权出现异常："+e.getMessage(), e);
			}
			finally
			{
				try
				{
					if(conn != null)
					{
					    conn.close();
					}
				} catch (SQLException e1)
				{
				}
			}	
		}
	}
	
	public static void main(String[] args)
	{
		try
		{			
			DataPrivilegeBiz biz= new DataPrivilegeBiz();
//			DataPrivilegeInfo paramInfo = new DataPrivilegeInfo();
//			paramInfo.setOfficeID(3);
//			paramInfo.setUserGroupID(2);
//			paramInfo.setOperationType(1);
//			paramInfo.setIsContainSub(BooleanValue.FALSE);
//			paramInfo.setAcctConditionType(MonitorAcctConditionType.ALL);
			biz.autoAuthorize(1,1);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
