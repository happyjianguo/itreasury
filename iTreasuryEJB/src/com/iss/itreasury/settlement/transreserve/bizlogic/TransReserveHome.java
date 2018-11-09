package com.iss.itreasury.settlement.transreserve.bizlogic;
import com.iss.itreasury.settlement.transreserve.bizlogic.TransReserve;

public interface TransReserveHome  extends javax.ejb.EJBHome{

	TransReserve create() throws javax.ejb.CreateException, java.rmi.RemoteException;
}
