package com.iss.itreasury.settlement.transcommission.bizlogic;

import java.rmi.RemoteException;
import java.util.Collection;

import javax.ejb.EJBObject;

import com.iss.itreasury.util.IRollbackException;

/**
 *
 * @name: TransCommission
 * @description:
 * @author: gqfang
 * @create: 2005-8-25
 *
 */
public interface TransCommission extends EJBObject
{
    public long commissionSettlement( Collection coll ) throws RemoteException,IRollbackException;
}