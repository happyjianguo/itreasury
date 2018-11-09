package com.iss.itreasury.settlement.transadjustinterest.bizlogic;
/**
 * @author zwsun, 2007/7/9
 */
import javax.ejb.EJBHome;

public interface TransAdjustInterestHome extends EJBHome{
	TransAdjustInterest create() throws javax.ejb.CreateException, java.rmi.RemoteException;
}
