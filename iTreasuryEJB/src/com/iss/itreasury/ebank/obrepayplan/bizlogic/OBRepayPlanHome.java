package com.iss.itreasury.ebank.obrepayplan.bizlogic;

import java.rmi.*;
import javax.ejb.*;

public interface OBRepayPlanHome extends EJBHome {
  public OBRepayPlan create() throws RemoteException, CreateException;
}