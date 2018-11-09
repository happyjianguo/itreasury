package com.iss.itreasury.settlement.clientmanage.bizlogic;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;
import javax.ejb.SessionBean;
import com.iss.itreasury.ebank.privilege.bizlogic.UserBean;
import com.iss.itreasury.ebank.privilege.dataentity.OB_UserInfo;
import com.iss.itreasury.settlement.account.dao.Sett_AccountDAO;
import com.iss.itreasury.settlement.account.dataentity.QueryAccountConditionInfo;
import com.iss.itreasury.settlement.clientmanage.dataentity.ClientInfo;
import com.iss.itreasury.settlement.clientmanage.dao.Sett_ClientDAO;
import com.iss.itreasury.settlement.clientmanage.dataentity.QueryClientConditionInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.system.dao.AdminOfUserDao;
import com.iss.itreasury.system.dataentity.AdminOfUserInfo;
import com.iss.itreasury.system.util.SYSConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IRollbackException;
import com.iss.itreasury.util.Log4j;

public class ClientManageEJB implements SessionBean{
	private javax.ejb.SessionContext ctx = null;
	final static long serialVersionUID = 3206093459760846163L;
	private Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);	
	/**
	 * ejbActivate method comment
	 * 
	 * @exception java.rmi.RemoteException
	 *                �쳣˵����
	 */
	public void ejbActivate() throws java.rmi.RemoteException
	{

	}
	/**
	 * ejbCreate method comment
	 * 
	 * @exception javax.ejb.CreateException
	 *                �쳣˵����
	 * @exception java.rmi.RemoteException
	 *                �쳣˵����
	 */
	public void ejbCreate() throws javax.ejb.CreateException, RemoteException
	{		
	}
	/**
	 * ejbPassivate method comment
	 * 
	 * @exception java.rmi.RemoteException
	 *                �쳣˵����
	 */
	public void ejbPassivate() throws java.rmi.RemoteException
	{
	}
	/**
	 * ejbRemove method comment
	 * 
	 * @exception java.rmi.RemoteException
	 *                �쳣˵����
	 */
	public void ejbRemove() throws java.rmi.RemoteException
	{
	}
	/**
	 * getSessionContext method comment
	 * 
	 * @return javax.ejb.SessionContext
	 */
	public javax.ejb.SessionContext getSessionContext()
	{
		return ctx;
	}
	/**
	 * setSessionContext method comment
	 * 
	 * @param ctx
	 *            javax.ejb.SessionContext
	 * @exception java.rmi.RemoteException
	 *                �쳣˵����
	 */
	public void setSessionContext(javax.ejb.SessionContext ctx) throws RemoteException
	{
		this.ctx = ctx;
	}
	/**
	 * ����˵�������ݲ�ѯ������ϣ���ѯ�����������Ŀͻ�
	 * 
	 * @param qcci:
	 *            QueryClientConditionInfo @return: Collection
	 * @throws Exception
	 */
	public Collection findClientByCondition(QueryClientConditionInfo qcci) throws RemoteException, IRollbackException
	{
		Vector v = new Vector();
		Sett_ClientDAO objClientDAO = new Sett_ClientDAO();
		try
		{
			v = (Vector) objClientDAO.findByCondition(qcci);
		}
		catch (Exception e)
		{
			throw new IRollbackException(ctx, e.getMessage());
		}
		return v.size() > 0 ? v : null;
	}
	/**
	 * ����˵�������ݿͻ�ID����ѯ�ͻ���Ϣ
	 * 
	 * @param lClientID :
	 *            long @return: ClientInfo
	 * @throws Exception
	 */
	public ClientInfo findClientByID(long lClientID) throws RemoteException, IRollbackException
	{
		ClientInfo ci = new ClientInfo();
		Sett_ClientDAO objClientDAO = new Sett_ClientDAO();
		try
		{
			ci = objClientDAO.findByID(lClientID);
		}
		catch (Exception e)
		{
			throw new IRollbackException(ctx, e.getMessage());
		}
		return ci;
	}
	/**
	 * ����˵���� ɾ���ͻ�
	 * 
	 * @param ci:
	 *            ClientInfo
	 * @return : long - ����ɾ���Ŀͻ�ID
	 * @throws Exception
	 *  
	 */
	public long deleteClient(long lClientID) throws RemoteException, IRollbackException
	{
		long lReturn = -1;
		Sett_ClientDAO objClientDAO = new Sett_ClientDAO();
		Sett_AccountDAO objAccountDAO = new Sett_AccountDAO();
		ClientInfo ci = new ClientInfo();
		Collection coll = null;
		QueryAccountConditionInfo qaci = new QueryAccountConditionInfo();
		UserBean userBean = new UserBean();
		OB_UserInfo userinfo = new OB_UserInfo();
		AdminOfUserInfo queryInfo = null;
		AdminOfUserInfo adminInfo = null;
		ArrayList list = new ArrayList();
		AdminOfUserDao userDao = new AdminOfUserDao();
		Connection conn = null;
		try
		{
			
			ci = objClientDAO.findByID(lClientID);
			if (ci != null)
			{
				//modify by Forest,20040518,�����жϣ�����ÿͻ��ѱ�����ģ��ʹ�ã����ܱ�ɾ����
				//ԭ�Ѵ����жϣ�����ÿͻ��Ѿ����ʻ��ˣ�����ɾ����
				qaci.setStartClientCode(ci.getClientCode());
				qaci.setEndClientCode(ci.getClientCode());
				coll = objAccountDAO.findByConditions(qaci);
				if ((coll != null && coll.size() > 0) || objClientDAO.checkIsUsedByLoan(lClientID) == true)
				{
					lReturn = -1;
				}
				else
				{
					conn = Database.getConnection();
					//ɾ���ÿͻ��������û�
					userinfo.setNClientId(lClientID);
					userinfo.setNStatus(SETTConstant.RecordStatus.INVALID);
					userBean.deleteBankUserByClient(userinfo);
					//����ÿͻ�Ϊ����Ա���ڿͻ�����ɾ���������ͻ��Ĺ�������
					queryInfo = new AdminOfUserInfo();
					queryInfo.setClientID(lClientID);
					queryInfo.setIsBelongToClient(SYSConstant.belongToClient.ISBELONG);
					list = userDao.findUserByClient(queryInfo,conn);
					if(list!=null)
					{
						Iterator it = list.iterator();
						while(it.hasNext())
						{
							adminInfo = (AdminOfUserInfo)it.next();
							userDao.deleteAdminOfUser(adminInfo, conn);
						}
						
					}
					//ɾ���ͻ�
					ci.setStatusID(SETTConstant.RecordStatus.INVALID);
					lReturn = objClientDAO.update(ci);
				}
			}
		}
		catch (IRollbackException ex)
		{
			throw ex;
		}
		catch (Exception e)
		{
			throw new IRollbackException(ctx, e.getMessage());
		}
		finally
		{
			try
			{
				if(conn!=null)
				{
					conn.close();
					conn = null;
				}
			}catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return lReturn;
	}
	/**
	 * ����˵����ȡ�ÿͻ����
	 * 
	 * @param lOfficeID :
	 *            long @return: String - �����Ŀͻ����
	 * @throws Exception
	 */
	public String getNewClientCode(long lOfficeID) throws RemoteException, IRollbackException
	{
		String strReturn = "";
		Sett_ClientDAO objClientDAO = new Sett_ClientDAO();
		try
		{
			strReturn = objClientDAO.getNewClientCode(lOfficeID);
		}
		catch (Exception e)
		{
			throw new IRollbackException(ctx, e.getMessage());
		}
		return strReturn;
	}
	/**
	 * ����˵���� �����ͻ�
	 * 
	 * @param ci:
	 *            ClientInfo
	 * @return : long - ���������Ŀͻ�ID
	 * @throws Exception
	 *  
	 */
	public long addClient(ClientInfo ci) throws RemoteException, IRollbackException
	{
		long lReturn = -1;
		boolean bDublicate = false;
		Sett_ClientDAO objClientDAO = new Sett_ClientDAO();
		try
		{
			bDublicate = isDuplicateClientCode(-1, ci.getClientCode());
			if (!bDublicate)
			{
				lReturn = objClientDAO.add(ci);
			}
		}
		catch (Exception e)
		{
			throw new IRollbackException(ctx, e.getMessage());
		}
		return lReturn;
	}
	/**
	 * ����˵�����ж������Ŀͻ�����Ƿ��Ѿ����ڡ�
	 */
	private boolean isDuplicateClientCode(long lOfficeID, String strClientCode) throws Exception
	{
		boolean bReturn = false;
		Sett_ClientDAO objClientDAO = new Sett_ClientDAO();
		QueryClientConditionInfo qcci = new QueryClientConditionInfo();
		Collection coll = null;
		try
		{
			qcci.setOfficeID(lOfficeID);
			qcci.setStartClientCode(strClientCode);
			qcci.setEndClientCode(strClientCode);
			coll = objClientDAO.findByCondition(qcci);
			if (coll != null && coll.size() > 0)
			{
				bReturn = true;
			}
		}
		catch (Exception exp)
		{
			throw exp;
		}
		return bReturn;
	}
	/**
	 * ����˵�����޸Ŀͻ���Ϣ
	 * 
	 * @param ci:
	 *            ClientInfo @return: long - �ͻ�ID
	 * @throws Exception
	 */
	public long updateClient(ClientInfo ci) throws RemoteException, IRollbackException
	{
		long lReturn = -1;
		Sett_ClientDAO objClientDAO = new Sett_ClientDAO();
		try
		{
			lReturn = objClientDAO.update(ci);
		}
		catch (Exception e)
		{
			throw new IRollbackException(ctx, e.getMessage());
		}
		return lReturn;
	}

}
