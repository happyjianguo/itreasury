/*
 * Created on 2003-8-7
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.securities.notice.bizlogic;

import com.iss.itreasury.util.IException;

/**
 * @author yiwang
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public interface NoticeHome extends javax.ejb.EJBHome
{
	Notice create() throws javax.ejb.CreateException, java.rmi.RemoteException;
}
