/*
 * Created on 2005-12-7
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.clientmanage.commandrunner.cmis;

/**
 * @author gdzhao
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
import javax.ejb.EJBObject;

import com.iss.itreasury.command.AbstractCommand;
import com.iss.itreasury.util.IException;
/**
 * <p>Title: </p> <p>Description: </p> <p>Copyright: Copyright (c) 2002</p> <p>Company: </p>
 * @version 1.0
 * @author pcliu
 */
public interface CimsRunner extends EJBObject {
    /**
     * @ejbTransactionAttribute Required 
     */
	AbstractCommand executeCommand(AbstractCommand cmd)throws javax.ejb.EJBException, java.rmi.RemoteException, IException, RuntimeException;

    /** @link dependency */
    /*# CommandServerBean lnkSession1Bean; */
}
