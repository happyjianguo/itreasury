package com.iss.itreasury.ebank.obsystem.bizlogic;


import java.rmi.RemoteException;

import javax.ejb.EJBObject;
import com.iss.itreasury.ebank.obsystem.dataentity.*;
import com.iss.itreasury.loan.loancommonsetting.dataentity.*;
import java.util.*;
import com.iss.itreasury.util.*;

/**
 * Created 2002-12-7 16:48:11
 * Code generated by the Forte for Java EJB Module
 * @author dong
 */

public interface OBSystem extends EJBObject 
{
	public long addSignAmount (SignAmountInfo info) throws RemoteException,IException;  
	public long addSignAmountForCurr (SignAmountInfo info) throws RemoteException,IException;  
	public long addSignAmountForFix (SignAmountInfo info) throws RemoteException,IException;  
	public long addAccountPrvg(Collection c) throws RemoteException,IException;  
	public long addPayee(ClientCapInfo info) throws RemoteException,IException;
	public long updatePayee(ClientCapInfo info) throws RemoteException,IException;
	public long deletePayee(long lID) throws RemoteException,Exception;  
	public long saveClientInfo(ClientInfo clientinfo) throws RemoteException,IException; 
	public ClientInfo findClientByID(long lClientID) throws RemoteException,IException;
	public ClientCapInfo findAccount(long lClientID, long lCurrencyID, String strAccountno,long officeid) throws RemoteException,IException;
	
}
