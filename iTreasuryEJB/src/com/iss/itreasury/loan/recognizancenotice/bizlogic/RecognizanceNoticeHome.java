/*
 * Created on 2004-11-29
 *
 * Title:				iTreasury
 * @author             	yfan 
 * Company:             iSoftStone
 * Copyright:           Copyright (c) 2003
 * @version
 * Description:         
 */
package com.iss.itreasury.loan.recognizancenotice.bizlogic;

/**
 * @author quanshao
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public interface RecognizanceNoticeHome extends javax.ejb.EJBHome
{
    RecognizanceNotice create() throws javax.ejb.CreateException, java.rmi.RemoteException;
}


