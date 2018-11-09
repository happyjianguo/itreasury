package com.iss.itreasury.settlement.transcommission.bizlogic;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;

/**
 *
 * @name: TransCommissionHome
 * @description:
 * @author: gqfang
 * @create: 2005-8-25
 *
 */
public interface TransCommissionHome extends EJBHome
{
    TransCommission create() throws CreateException,java.rmi.RemoteException;
}