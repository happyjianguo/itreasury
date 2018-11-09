/*
 * Created on 2003-9-16
 * 
 * To change the template for this generated file go to Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.bizdelegation;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Vector;

import javax.ejb.CreateException;

import com.iss.itreasury.settlement.account.bizlogic.Account;
import com.iss.itreasury.settlement.account.bizlogic.AccountHome;
import com.iss.itreasury.settlement.account.dao.Sett_AccountBankDAO;
import com.iss.itreasury.settlement.account.dao.Sett_AccountDAO;
import com.iss.itreasury.settlement.account.dao.Sett_ClientSealPicDAO;
import com.iss.itreasury.settlement.account.dataentity.AccountInfo;
import com.iss.itreasury.settlement.account.dataentity.ClientInfo;
import com.iss.itreasury.settlement.account.dataentity.ClientSealPicInfo;
import com.iss.itreasury.settlement.account.dataentity.QueryAccountConditionInfo;
import com.iss.itreasury.settlement.account.dataentity.QueryClientConditionInfo;
import com.iss.itreasury.settlement.account.dataentity.SubAccountAssemblerInfo;
import com.iss.itreasury.settlement.account.dataentity.SubAccountCurrentInfo;
import com.iss.itreasury.settlement.setting.dao.Sett_BranchDAO;
import com.iss.itreasury.util.EJBHomeFactory;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IRollbackException;
/**
 * @author wlming
 * 
 * To change the template for this generated type comment go to Window>Preferences>Java>Code Generation>Code and
 * Comments
 */
public class AccountDelegation
{
	private Account accountFacade;
	public AccountDelegation() throws RemoteException
	{
		try
		{
			AccountHome home = (AccountHome) EJBHomeFactory.getFactory().lookUpHome(AccountHome.class);
			accountFacade = (Account) home.create();
		}
		catch (RemoteException re)
		{
			re.printStackTrace();
		}
		catch (CreateException ce)
		{
			ce.printStackTrace();
		}
		catch (IException ie)
		{
			ie.printStackTrace();
		}
	}
	public long addClient(ClientInfo ci) throws RemoteException, IRollbackException
	{
		long lReturn = -1;
		lReturn = accountFacade.addClient(ci);
		return lReturn;
	}
	public String getNewClientCode(long lOfficeID) throws RemoteException, IRollbackException
	{
		String strReturn = "";
		strReturn = accountFacade.getNewClientCode(lOfficeID);
		return strReturn;
	}
	public String getNewAccountNo(long lOfficeID, long lCurrencyID, long lAccountTypeID, long lClientID) throws RemoteException, IRollbackException
	{
		String strReturn = "";
		strReturn = accountFacade.getNewAccountNo(lOfficeID, lCurrencyID, lAccountTypeID, lClientID);
		return strReturn;
	}
	/**
	 * ����˵�����õ��µ��˻��ţ��������˻���׼�����˻�������˻� ���ã�
	 * 
	 * @param lOfficeID ��ǰ��������
	 * @param lOfficeID �ͻ���������
	 * @param lAccountGroupTypeID �˻�������
	 * @param lAccountTypeID �˻�����
	 * @param lCurrencyID
	 * @return �������˻����
	 * @throws RemoteException,IRollbackException
	 */
	public String getNewAccountNo(long lOfficeID, long lCurrencyID, long lAccountTypeID, long lClientID,long lAccountGroupTypeID,long belongOfficeID) throws RemoteException, IRollbackException
	{
		String strReturn = "";
		strReturn = accountFacade.getNewAccountNo(lOfficeID, lCurrencyID, lAccountTypeID, lClientID,lAccountGroupTypeID,belongOfficeID);
		return strReturn;
	}
	public ClientInfo findClientByID(long lClientID) throws RemoteException, IRollbackException
	{
		ClientInfo ci = new ClientInfo();
		ci = accountFacade.findClientByID(lClientID);
		return ci;
	}
	public Collection findClientByCondition(QueryClientConditionInfo qcci) throws RemoteException, IRollbackException
	{
		Collection coll = null;
		coll = accountFacade.findClientByCondition(qcci);
		return coll;
	}
	public Collection findAccountByCondition(QueryAccountConditionInfo qaci) throws RemoteException, IRollbackException
	{
		Collection coll = null;
		coll = accountFacade.findAccountByCondition(qaci);
		return coll;
	}
	public Collection findReserveAccountByConditions(QueryAccountConditionInfo qaci) throws RemoteException, IRollbackException
	{
		Collection coll = null;
		coll = accountFacade.findReserveAccountByCondition(qaci);
		return coll;
	}
	public long updateClient(ClientInfo ci) throws RemoteException, IRollbackException
	{
		long lReturn = -1;
		lReturn = accountFacade.updateClient(ci);
		return lReturn;
	}
	public long deleteClient(long lClientID) throws RemoteException, IRollbackException
	{
		long lReturn = -1;
		lReturn = accountFacade.deleteClient(lClientID);
		return lReturn;
	}
	public long addClientSealPic(ClientSealPicInfo cspi)
	{
		long lReturn = -1;
		try
		{
			Sett_ClientSealPicDAO dao = new Sett_ClientSealPicDAO();
			lReturn = dao.add(cspi);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
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
	/**
	 * ���������˻�
	 * 
	 * @param ai
	 * @param saci
	 * @return @throws
	 *         RemoteException
	 * @throws IRollbackException
	 */
	public long addCurrentAccount(AccountInfo ai, SubAccountCurrentInfo saci, Vector vAccountBank) throws RemoteException, IRollbackException
	{
		return accountFacade.addCurrentAccount(ai, saci, vAccountBank);
	}
	/**
	 * �޸Ķ��ںʹ����˻�
	 * 
	 * @param ai
	 * @return @throws
	 *         RemoteException
	 * @throws IRollbackException
	 */
	public long addAccount(AccountInfo ai) throws RemoteException, IRollbackException
	{
		return accountFacade.addAccount(ai);
	}
	/**
	 * ���������˻�
	 * 
	 * @param ai
	 * @param saci
	 * @return @throws
	 *         RemoteException
	 * @throws IRollbackException
	 */
	public long updateCurrentAccount(AccountInfo ai, SubAccountCurrentInfo saci, Vector vAccountBank) throws RemoteException, IRollbackException
	{
		return accountFacade.updateCurrentAccount(ai, saci, vAccountBank);
	}
	/**
	 * �޸Ķ��ںʹ����˻�
	 * 
	 * @param ai
	 * @return @throws
	 *         RemoteException
	 * @throws IRollbackException
	 */
	public long updateAccount(AccountInfo ai) throws RemoteException, IRollbackException
	{
		return accountFacade.updateAccount(ai);
	}
	/**
	 * ���һ������˻�
	 * 
	 * @param lAccountID
	 * @return @throws
	 *         RemoteException
	 * @throws IRollbackException
	 */
	public SubAccountAssemblerInfo findSubCurrentAccountByAccountID(long lAccountID) throws RemoteException, IRollbackException
	{
		return accountFacade.findSubCurrentAccountByAccountID(lAccountID);
	}
    /**
     * ����˵�����������˻�ID����ѯ���˻���Ϣ
     * 
     * @param lSubAccountID
     *            ���˻�ID @return:SubAccountAssemblerInfo ai ���˻�Assemble
     * @throws RemoteException,IRollbackException
     */
    public SubAccountAssemblerInfo findSubAccountByID(long lSubAccountID) throws RemoteException, IRollbackException
    {
        return accountFacade.findSubAccountByID(lSubAccountID);
    	
    }
	/**
	 * ��û����˻��Ŀ�������Ϣ
	 * @param lAccountID
	 * @return
	 */
	public Collection findAccountBankByAccountID(long lAccountID)
	{
		Sett_AccountBankDAO dao = new Sett_AccountBankDAO();
		Collection c = null;
		try
		{
			c = dao.findByAccountID(lAccountID);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return c;
	}
	/**
	 * ��û����˻������п�������Ϣ
	 * @param 
	 * @return Vector
	 */
	public Vector getAllBank(long lOfficeID,long lCurrencyID)
	{
		Sett_BranchDAO dao = new Sett_BranchDAO();
		Vector v = new Vector();
		try
		{
			v = dao.getAllBank(lOfficeID,lCurrencyID);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return v;
	}
    public AccountInfo findAccountByID(long lAccountID)  throws RemoteException, IRollbackException
    {
        return accountFacade.findAccountByID(lAccountID);
    }
	/**
	 * �����޸��˻�״̬�����ʼƻ�
	 * @param ai
	 * @return @throws
	 *         RemoteException
	 * @throws IRollbackException
	 */
	public long batchUpdateAccount(QueryAccountConditionInfo qaci,AccountInfo ai,SubAccountCurrentInfo saci) throws RemoteException, IRollbackException
	{
		return accountFacade.BatchUpdateAccount(qaci,ai,saci);
	}
	/**
	 * ����˵���������˻�ID�޸��˻�״̬
	 * @param qaci
	 * @return long - �����˻�ID
	 * @throws Exception
	 */
	public long updateAccountCheckStatus(long lAccountID,long lActionID,long lCheckStatusID,long lCheckUserID,Timestamp tsCheckDate) throws RemoteException, IRollbackException
	{
		return accountFacade.UpdateCheckStatus(lAccountID,lActionID,lCheckStatusID,lCheckUserID,tsCheckDate);
	}
    public double getMonthSumAmount(long lAccountID, long lOfficeID, long lCurrencyID) throws Exception
    {
        Sett_AccountDAO dao = new Sett_AccountDAO();
        return dao.getMonthSumAmount(lAccountID, lOfficeID, lCurrencyID);
    }
    public double getDaySumAmount(long lAccountID, long lOfficeID, long lCurrencyID) throws Exception
    {
        Sett_AccountDAO dao = new Sett_AccountDAO();
        return dao.getDaySumAmount(lAccountID, lOfficeID, lCurrencyID);
    }
    
    public long doApproval(AccountInfo info)throws RemoteException, IRollbackException
    {
    	return accountFacade.doApproval(info);
    }
    
    public long cancelApproval(AccountInfo info)throws RemoteException, IRollbackException
    {
    	return accountFacade.cancelApproval(info);
    }
    /**
     * @param info
     * @return
     * @throws RemoteException
     * @throws IRollbackException
     */
    public long haveTrans(AccountInfo info)throws RemoteException, IRollbackException
    {
    	return accountFacade.haveTrans(info);
    }
    
}