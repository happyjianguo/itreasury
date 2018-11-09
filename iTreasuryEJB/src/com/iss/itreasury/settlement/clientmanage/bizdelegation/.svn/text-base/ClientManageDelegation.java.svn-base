package com.iss.itreasury.settlement.clientmanage.bizdelegation;

import java.rmi.RemoteException;
import java.util.Collection;

import javax.ejb.CreateException;

import com.iss.itreasury.settlement.clientmanage.dao.Sett_ClientSealPicDAO;
import com.iss.itreasury.settlement.clientmanage.dataentity.ClientInfo;
import com.iss.itreasury.settlement.clientmanage.bizlogic.ClientManage;
import com.iss.itreasury.settlement.clientmanage.bizlogic.ClientManageHome;
import com.iss.itreasury.settlement.clientmanage.dataentity.QueryClientConditionInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.EJBHomeFactory;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IRollbackException;
import com.iss.itreasury.util.Log4j;

public class ClientManageDelegation {
	private Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);
	private ClientManage clientManage;
	public ClientManageDelegation() throws RemoteException
	{
		try
		{
			ClientManageHome home = (ClientManageHome) EJBHomeFactory.getFactory().lookUpHome(ClientManageHome.class);
			clientManage = (ClientManage) home.create();
		}
		catch (RemoteException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (CreateException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.debug("ClientManageDelegation::Constructor 11!!!~");
	}
	public Collection findClientByCondition(QueryClientConditionInfo qcci) throws RemoteException, IRollbackException
	{
		Collection coll = null;
		coll = clientManage.findClientByCondition(qcci);
		return coll;
	}
	public ClientInfo findClientByID(long lClientID) throws RemoteException, IRollbackException
	{
		ClientInfo ci = new ClientInfo();
		ci = clientManage.findClientByID(lClientID);
		return ci;
	}
	public long deleteClient(long lClientID) throws RemoteException, IRollbackException
	{
		long lReturn = -1;
		lReturn = clientManage.deleteClient(lClientID);
		return lReturn;
	}
	public String getNewClientCode(long lOfficeID) throws RemoteException, IRollbackException
	{
		String strReturn = "";
		strReturn = clientManage.getNewClientCode(lOfficeID);
		return strReturn;
	}
	public long addClient(ClientInfo ci) throws RemoteException, IRollbackException
	{
		long lReturn = -1;
		lReturn = clientManage.addClient(ci);
		return lReturn;
	}
	public long updateClient(ClientInfo ci) throws RemoteException, IRollbackException
	{
		long lReturn = -1;
		lReturn = clientManage.updateClient(ci);
		return lReturn;
	}
	public Collection findClientSealPicByClientID(long lClientID)
	{
		Collection c = null;
		try
		{
			Sett_ClientSealPicDAO dao = new Sett_ClientSealPicDAO();
			c = dao.findByClientID(lClientID);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return c;
	}


}
