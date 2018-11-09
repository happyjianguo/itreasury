package com.iss.itreasury.ebank.obaheadrepaynotice.bizlogic;

import java.rmi.*;
import javax.ejb.*;
import com.iss.itreasury.ebank.obaheadrepaynotice.dataentity.*;

public interface OBAheadRepayNotice extends EJBObject {

	public AheadRepayNoticeInfo findAheadRepayNotice(long lContractID,long lLoanPayID,long lAheadRepayID) throws RemoteException, Exception;

	public long saveAheadRepayNotice(AheadRepayNoticeInfo info) throws RemoteException, Exception;

	public long updateAheadRepayNotice(AheadRepayNoticeInfo info) throws RemoteException, Exception;

	public long updateStatus(long lAheadPayID,long lStatusID) throws RemoteException, Exception;

}