/*
 * Created on 2003-9-26
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.bizdelegation;
import java.rmi.RemoteException;
import java.util.Vector;

import javax.ejb.CreateException;

//import com.iss.itreasury.settlement.bankinstruction.dao.BankInstructionDAO;
import com.iss.itreasury.settlement.transcurrentdeposit.dataentity.TransCurrentDepositAssembler;
import com.iss.itreasury.settlement.transfixeddeposit.dataentity.TransFixedOpenInfo;
import com.iss.itreasury.settlement.transspecial.bizlogic.TransSpecial;
import com.iss.itreasury.settlement.transspecial.bizlogic.TransSpecialHome;
import com.iss.itreasury.settlement.transspecial.dataentity.QueryByStatusConditionInfo;
import com.iss.itreasury.settlement.transspecial.dataentity.QueryBySubSpecialTypeAndStatusInfo;
import com.iss.itreasury.settlement.transspecial.dataentity.SpecialOperationInfo;
import com.iss.itreasury.settlement.transspecial.dataentity.TransSpecialOperationAssembler;
import com.iss.itreasury.settlement.transspecial.dataentity.TransSpecialOperationInfo;
import com.iss.itreasury.util.EJBHomeFactory;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IRollbackException;
import com.iss.itreasury.util.Log;

/**
 * @author wlming
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class TransSpecialOperationDelegation
{
	TransSpecial transspecialejb = null;

	public TransSpecialOperationDelegation() throws RemoteException, IException
	{
		try
		{

			TransSpecialHome home = (TransSpecialHome) EJBHomeFactory.getFactory().lookUpHome(TransSpecialHome.class);
			transspecialejb = (TransSpecial) home.create();
		}
		catch (RemoteException e)
		{
			throw new RemoteException("System Error ---Creating TransSpecialEJB failed!", e);
		}
		catch (IException e)
		{
			throw new IException("System Error:" + e.getMessage(), e);
		}
		catch (CreateException e)
		{
			e.printStackTrace();
			throw new RemoteException("System Error ---Creating TransSpecialEJB failed!", e);
		}
	}

	public long cancelCheck(TransSpecialOperationInfo tsoi) throws RemoteException, IRollbackException
	{
		return transspecialejb.cancelCheck(tsoi);
	}

	public long check(TransSpecialOperationInfo tsoi) throws RemoteException, IRollbackException
	{
		long res = transspecialejb.check(tsoi);
//		//发送银行指令
//		BankInstructionDAO bankInstructDAO = new BankInstructionDAO();
//		try
//		{
//			bankInstructDAO.sendBankInstruction(tsoi.getStransno());
//		}
//		catch (IException e)
//		{
//			Log.print("----------------------------发送银行指令出现异常----------------");
//			throw new IRollbackException(null,e.getMessage(),e);
//		}
		return res;
	}

	public boolean delete(TransSpecialOperationInfo tsoi) throws RemoteException, IRollbackException
	{
		return transspecialejb.delete(tsoi);
	}

	public TransSpecialOperationInfo findDetailByID(long itransid) throws RemoteException, IRollbackException
	{
		TransSpecialOperationInfo tsoi = transspecialejb.findDetailByID(itransid);
		System.out.println("Delegation getNtransactiontypeid : " + tsoi.getNtransactiontypeid());
		return tsoi;
	}

	public Vector findByStatus(QueryByStatusConditionInfo info, String strOrderByName, boolean isDesc) throws RemoteException, IRollbackException
	{
		//System.out.println("findListByStatus in bizdelegation");
		Vector v = new Vector();
		v = transspecialejb.findByStatus(info, strOrderByName, isDesc);
		return v;
	}
	public Vector findBySubtransSpecialIDandStatus(QueryBySubSpecialTypeAndStatusInfo info, String strOrderByName, boolean isDesc) throws RemoteException, IRollbackException
	{
		//System.out.println("findListByStatus in bizdelegation");
		Vector v = new Vector();
		v = transspecialejb.findBySubtransSpecialIDandStatus(info, strOrderByName, isDesc);
		return v;
	}
	public SpecialOperationInfo findSettingDetailByID(long isettingid) throws RemoteException, IRollbackException
	{
		return transspecialejb.findSettingDetailByID(isettingid);
	}

	public TransSpecialOperationInfo match(TransSpecialOperationInfo tsoi, long specialoperationid) throws RemoteException, IRollbackException
	{
		return transspecialejb.match(tsoi, specialoperationid);
	}

	public long presave(TransSpecialOperationInfo tsoi) throws RemoteException, IRollbackException
	{
		return transspecialejb.presave(tsoi);
	}

	public long save(TransSpecialOperationInfo tsoi) throws RemoteException, IRollbackException
	{
		return transspecialejb.save(tsoi);
	}
	
	
	/**
	 * 自动保存和复核
	 * */
	public TransSpecialOperationAssembler saveAndCheckAutomatically(TransSpecialOperationAssembler info) throws RemoteException, IRollbackException{
		TransSpecialOperationAssembler assemble = transspecialejb.saveAndCheckAutomatically(info);
		return assemble;
	}


	public long tempsave(TransSpecialOperationInfo tsoi) throws RemoteException, IRollbackException
	{
		return transspecialejb.tempsave(tsoi);
	}

	public long getIDByTransNo(String strTransNo) throws RemoteException, IRollbackException
	{
		return transspecialejb.getIDByTransNo(strTransNo);
	}
	/**
	 * 方法说明：审批方法
	 *  Method  doApproval.
	 * @param TransSpecialOperationInfo info
	 * @return long transid
	 */
	public long doApproval ( TransSpecialOperationInfo tsoi)
		throws RemoteException, IRollbackException
	{
		return transspecialejb.doApproval(tsoi);
	}
	/**
	 * 方法说明：取消审批方法
	 *  Method  doApproval.
	 * @param TransSpecialOperationInfo info
	 * @return long transid
	 */
	public long cancelApproval ( TransSpecialOperationInfo tsoi)
		throws RemoteException, IRollbackException
	{
		return transspecialejb.cancelApproval(tsoi);
	}
}
