package com.iss.itreasury.ebank.obbudget.bizlogic;

public interface OBBudgetHome extends javax.ejb.EJBHome{
	OBBudget create() throws javax.ejb.CreateException, java.rmi.RemoteException;
}
