package com.iss.itreasury.ebank.obinterest.bizlogic;
import java.rmi.RemoteException;
import java.util.Vector;
import javax.ejb.*;
import com.iss.itreasury.settlement.interest.dataentity.LoanNoticeInfo;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IRollbackException;
public interface OBInterest extends EJBObject
{
	public void dispatchLoanNotice(LoanNoticeInfo Info) throws IRollbackException, RemoteException, IException;
	public void findLoanNotice(LoanNoticeInfo info) throws IRollbackException, RemoteException, IException;
}