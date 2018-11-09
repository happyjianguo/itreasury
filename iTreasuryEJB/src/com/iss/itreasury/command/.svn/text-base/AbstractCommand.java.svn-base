/*
 * Created on 2005-12-7
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.command;

import java.io.Serializable;
import com.iss.itreasury.util.IException;
import java.sql.Connection;
/**
 * <p>Title:Command抽象类 </p>
 * <p>Description: Command抽象类有统一的执行接口，</p>
 * <p>Company: iSoftstone</p>
 * @version 1.0
 */
public abstract class AbstractCommand implements Serializable {
    /**
     * Command统一的抽象执行方法，需要具体子类实现
     * @exception ITreasuryException 
     */
    public abstract void execute() throws IException ;
    
    protected void clearConn(Connection con) 
    {
    	try
		{
	    	if (con!=null)
	    	{
	    		con.close();
	    		con=null;
	    	}
		}catch (Exception e){
			e.printStackTrace();
		}
    }
}
