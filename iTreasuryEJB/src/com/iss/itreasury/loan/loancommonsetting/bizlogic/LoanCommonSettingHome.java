package com.iss.itreasury.loan.loancommonsetting.bizlogic;


import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;

/**
 * Created 2003-8-15 14:50:59
 * Code generated by the Forte for Java EJB Module
 * @author yfan
 */

public interface LoanCommonSettingHome extends EJBHome {
    public LoanCommonSetting create() throws RemoteException,CreateException;
}
