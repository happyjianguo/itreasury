/*
 * Created on 2003-8-7
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.transspecial.bizlogic;
/**
 *
 * <p>Title:TransSpecialHome Class </p>
 * <p>Description: Inherited from java.ejb.EJBHome </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company:isoftstone </p>
 * @author Yuqiangliao
 * @version 1.0
 */
public interface TransSpecialHome extends javax.ejb.EJBHome
{
	TransSpecial create() throws javax.ejb.CreateException, java.rmi.RemoteException;
}
