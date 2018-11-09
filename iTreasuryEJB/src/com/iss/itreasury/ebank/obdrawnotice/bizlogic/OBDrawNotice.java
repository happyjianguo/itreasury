package com.iss.itreasury.ebank.obdrawnotice.bizlogic;

//import java.rmi.*;
import javax.ejb.*;
import java.rmi.*;
import com.iss.itreasury.ebank.obdrawnotice.dataentity.*;

public interface OBDrawNotice extends EJBObject {
	public DrawNoticeInfo findDrawNoticeByID(long lDrawNoticeID,long lContractID) throws RemoteException,Exception;
	public long saveDrawNotice(DrawNoticeInfo info) throws RemoteException,Exception;
	public long updateStatus(long lDrawNoticeID,long lStatus) throws RemoteException,Exception;
	public long updateDrawNotice(DrawNoticeInfo drawNoticeInfo) throws RemoteException,Exception;
}