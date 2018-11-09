package com.iss.itreasury.settlement.transnoteacceptance.bizdelegation;

import java.rmi.RemoteException;
import com.iss.itreasury.settlement.transnoteacceptance.bizlogic.TransNoteAcceptance;
import com.iss.itreasury.settlement.transnoteacceptance.bizlogic.TransNoteAcceptanceHome;
import com.iss.itreasury.settlement.transnoteacceptance.dataentity.TransAcceptanceNoteAcceptanceInfo;
import com.iss.itreasury.util.EJBHomeFactory;
import com.iss.itreasury.util.IRollbackException;

public class TransNoteAcceptanceDelegation {
	
	private TransNoteAcceptance transNoteAcceptanceFacade;
	
	public TransNoteAcceptanceDelegation()throws RemoteException
	{
		try
		{
			TransNoteAcceptanceHome home = (TransNoteAcceptanceHome) EJBHomeFactory.getFactory().lookUpHome(TransNoteAcceptanceHome.class);
			transNoteAcceptanceFacade = home.create();
		}
		catch (Exception e)
		{
			throw new RemoteException(e.getMessage());
		}
	}
	
	public TransAcceptanceNoteAcceptanceInfo acceptanceNext(TransAcceptanceNoteAcceptanceInfo info) throws RemoteException, IRollbackException
	{
		return this.transNoteAcceptanceFacade.acceptanceNext(info);
	}
	
	public long acceptanceTempSave(TransAcceptanceNoteAcceptanceInfo info) throws RemoteException, IRollbackException
	{
		return this.transNoteAcceptanceFacade.acceptanceTempSave(info);
	}
	
	
	public long acceptanceSave(TransAcceptanceNoteAcceptanceInfo info) throws RemoteException, IRollbackException
	{
		return this.transNoteAcceptanceFacade.acceptanceSave(info);
	}

}
