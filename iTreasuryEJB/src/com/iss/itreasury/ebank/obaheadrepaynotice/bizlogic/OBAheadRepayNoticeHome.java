package com.iss.itreasury.ebank.obaheadrepaynotice.bizlogic;

import java.rmi.*;
import javax.ejb.*;

public interface OBAheadRepayNoticeHome extends EJBHome {
  public OBAheadRepayNotice create() throws RemoteException, CreateException;
}