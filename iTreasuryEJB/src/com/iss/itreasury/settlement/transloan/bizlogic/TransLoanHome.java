/*
 * Created on 2003-8-7
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.transloan.bizlogic;
/**
 * @author yiwang
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public interface TransLoanHome extends javax.ejb.EJBHome
{
	TransLoan create() throws javax.ejb.CreateException, java.rmi.RemoteException;
}
