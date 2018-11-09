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
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import java.rmi.RemoteException;
import javax.ejb.EJBException;
import javax.ejb.CreateException;

import com.iss.itreasury.command.AbstractCommand;
import com.iss.itreasury.util.IException;
/**
 * <p>Title: CommandServerBean</p>
 * <p>Description:CommandServer服务端的具体实现，一个无状态的会话Bean </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company:iSoftstone </p>
 * @version 1.0
 * @ejbHome com.iss.itreasury.ebank.util.CommandServerHome
 * @ejbRemote com.iss.itreasury.ebank.util.CommandServer
 * @ejbLocal com.iss.itreasury.ebank.bizdelegation.CommandServerLocal
 * @ejbLocalHome com.iss.itreasury.ebank.bizdelegation.CommandServerLocalHome
 * @max-beans-in-free-pool 100
 * @initial-beans-in-free-pool 10
 */
public class CimsRunnerBean implements SessionBean {
    private SessionContext ctx;

    /**
     * @stereotype include
     * @label include
     * @clientRole call*/
   

    public void setSessionContext(SessionContext context) throws RemoteException, EJBException {
        ctx = context;
    }

    public void ejbActivate() throws EJBException {
    }

    public void ejbPassivate() throws EJBException {
    }

    public void ejbRemove() throws EJBException {
    }

    public void ejbCreate() throws CreateException, EJBException {
            // Write your code here
        }

    /**
     * 执行业务逻辑类
     * @ejbTransactionAttribute Required 
     * @param command   业务逻辑类
     */
    public AbstractCommand executeCommand(AbstractCommand cmd) throws IException,  EJBException {
	    try{
			cmd.execute();
        }
		catch(IException ex)
        {
            //构造需回滚的异常
            ctx.setRollbackOnly();
            throw ex;
        }
        return cmd;
    }
}
