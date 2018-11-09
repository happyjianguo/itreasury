package com.iss.itreasury.ebank.obfreeapply.bizlogic;

//import java.rmi.*;
import javax.ejb.*;
import com.iss.itreasury.ebank.obfreeapply.dataentity.*;
import java.rmi.*;

public interface OBFreeApply extends EJBObject {
	public FreeApplyInfo findFreeApply(FreeApplyQueryInfo queryInfo) throws RemoteException,Exception;
	public long saveFreeApply(FreeApplyInfo freeApplyInfo) throws RemoteException,Exception;
	public long updateStatus(long lFreeApplyID,long lStatus) throws RemoteException,Exception;
	public long updateFreeApply(FreeApplyInfo freeApplyInfo) throws RemoteException,Exception;
	
}