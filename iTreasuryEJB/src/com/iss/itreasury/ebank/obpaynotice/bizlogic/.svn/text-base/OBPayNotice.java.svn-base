package com.iss.itreasury.ebank.obpaynotice.bizlogic;

import java.rmi.*;
import javax.ejb.*;
import javax.servlet.jsp.JspWriter;
import com.iss.itreasury.ebank.obpaynotice.dataentity.*;


public interface OBPayNotice extends EJBObject {
	public PayNoticeInfo findPayNoticeByID(long lpayID,long lContractID) throws RemoteException,Exception;
	public long savePayNotice(PayNoticeInfo info) throws RemoteException,Exception ;
	public long updatePayNotice(PayNoticeInfo info) throws RemoteException,Exception ;
	public long updateStatus(long ipayID, long nstatusID) throws RemoteException,Exception ;
}