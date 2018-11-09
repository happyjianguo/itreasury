package com.iss.itreasury.ebank.obextendapply.bizlogic;

import java.rmi.*;
import javax.ejb.*;

public interface OBExtendApplyHome extends EJBHome {
  public OBExtendApply create() throws RemoteException, CreateException;
}