package com.iss.itreasury.settlement.bizdelegation;

import java.rmi.RemoteException;
import java.util.Collection;

import javax.ejb.CreateException;

import com.iss.itreasury.settlement.transcurrentdeposit.bizlogic.TransCurrentDeposit;
import com.iss.itreasury.settlement.transcurrentdeposit.bizlogic.TransCurrentDepositHome;
import com.iss.itreasury.settlement.transcurrentdeposit.dataentity.TransCurrentDepositAssembler;
import com.iss.itreasury.settlement.transferloancontract.bizlogic.TransferLoanContractDeposit;
import com.iss.itreasury.settlement.transferloancontract.bizlogic.TransferLoanContractDepositHome;
import com.iss.itreasury.settlement.transferloancontract.dataentity.NoticeAndAgentDetailConditionInfo;
import com.iss.itreasury.settlement.transferloancontract.dataentity.TransferLoanContractInfo;
import com.iss.itreasury.util.EJBHomeFactory;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IRollbackException;

public class TransferLoanContractDelegation {
	
	private TransferLoanContractDeposit transferLoanContractFacade;
	
	public TransferLoanContractDelegation() throws RemoteException, IRollbackException
	{
		try
		{

			TransferLoanContractDepositHome home =
				(TransferLoanContractDepositHome) EJBHomeFactory.getFactory().lookUpHome(TransferLoanContractDepositHome.class);
			transferLoanContractFacade = (TransferLoanContractDeposit) home.create();
		}
		catch (RemoteException e)
		{
			throw e;
		}
		catch (IException e)
		{
			e.printStackTrace();
			throw new RemoteException();
		}
		catch (CreateException e)
		{
			e.printStackTrace();
			throw new RemoteException();
		}

	}
	public long tempSave(TransferLoanContractInfo info) throws RemoteException, IRollbackException
	{
		return transferLoanContractFacade.tempSave(info);

	}
	public long preSave(TransferLoanContractInfo info) throws RemoteException, IRollbackException
	{
		return transferLoanContractFacade.preSave(info);

	}
	public long delete(TransferLoanContractInfo info) throws RemoteException, IRollbackException
	{
		return transferLoanContractFacade.delete(info);

	}
	
	public Collection findByConditions(TransferLoanContractInfo info)
	throws RemoteException, IRollbackException
    {
	   return transferLoanContractFacade.findByConditions(info);

    }
	public TransferLoanContractInfo findByID(TransferLoanContractInfo info)
	throws RemoteException, IRollbackException
    {
	   return transferLoanContractFacade.findByID(info);

    }
	public TransferLoanContractInfo transferMatch(TransferLoanContractInfo info)
	throws RemoteException, IRollbackException
    {
	   return transferLoanContractFacade.transferMatch(info);

    }
	public long transferCheck(boolean checkOrCancel,TransferLoanContractInfo info)
	throws RemoteException, IRollbackException
    {
	   return transferLoanContractFacade.transferCheck(checkOrCancel,info);

    }
	public long repaytransferCheck(boolean checkOrCancel,TransferLoanContractInfo info)
	throws RemoteException, IRollbackException
    {
	   return transferLoanContractFacade.repaytransferCheck(checkOrCancel,info);

    }
	public Collection findNoticeAndAgentDetial(NoticeAndAgentDetailConditionInfo info)
	throws RemoteException, IRollbackException
    {
	   return transferLoanContractFacade.findNoticeAndAgentDetial(info);

    }
	public long tempClientSave(TransferLoanContractInfo info) throws RemoteException, IRollbackException
	{
		return transferLoanContractFacade.tempClientSave(info);

	}
	public long preClientSave(TransferLoanContractInfo info) throws RemoteException, IRollbackException
	{
		return transferLoanContractFacade.preClientSave(info);

	}
	public long clientDelete(TransferLoanContractInfo info) throws RemoteException, IRollbackException
	{
		return transferLoanContractFacade.clientDelete(info);

	}
	public Collection findNoticeAndAgentDetialForFalse(NoticeAndAgentDetailConditionInfo info,Collection coll)
	throws RemoteException, IRollbackException
    {
	   return transferLoanContractFacade.findNoticeAndAgentDetialForFalse(info,coll);

    }

	public Collection findTransferloandetailByTransferId(TransferLoanContractInfo info)
	throws RemoteException, IRollbackException
    {
	   return transferLoanContractFacade.findTransferloandetailByTransferId(info);

    }

	public long transferClientCheck(boolean checkOrCancel,TransferLoanContractInfo info)
	throws RemoteException, IRollbackException
    {
	   return transferLoanContractFacade.transferClientCheck(checkOrCancel,info);

    }
	public long preSaveNoProxy(TransferLoanContractInfo info) throws RemoteException, IRollbackException
	{
		return transferLoanContractFacade.preSaveNoProxy(info);

	}
	public long deleteNoProxy(TransferLoanContractInfo info) throws RemoteException, IRollbackException
	{
		return transferLoanContractFacade.deleteNoProxy(info);

	}
	public TransferLoanContractInfo findInfoByTransNo(TransferLoanContractInfo info) throws RemoteException, IRollbackException
	{
		return transferLoanContractFacade.findInfoByTransNo(info);

	}

}
