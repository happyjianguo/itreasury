/*
 * Created on 2004-8-13
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.loan.actiontrace.dao;

import java.sql.Connection;
import com.iss.itreasury.util.*;
import com.iss.itreasury.loan.util.*;
import java.util.*;
import com.iss.itreasury.dao.*;
import com.iss.itreasury.loan.actiontrace.dataentity.*;
import com.iss.itreasury.loan.base.LoanDAO;
/**
 * @author gdzhao
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ActionTraceDao extends LoanDAO
{
	
	protected Log4j log4j = new Log4j(Constant.ModuleType.LOAN, this);	
	public ActionTraceDao()
	{
		super("Loan_ActionTracing");
	}
	public ActionTraceDao(Connection conn)
	{
		super("Loan_ActionTracing",conn);
	}

	public Collection findTraceHistory(long moduleID,long traceTypeID,long itemID) throws Exception
	{
		Collection c=null;
		String strSQL="";
		
		try
        {
            /*-----------------init DAO --------------------*/
            try 
            {
            	initDAO();
            } 
            catch (ITreasuryDAOException e) 
            {
            	throw e;
            }
            /*----------------end of init------------------*/		

            try 
            {
            	strSQL = "select * from Loan_ActionTracing"	
            		+" where moduleID="+moduleID
            		+" and traceTypeID="+traceTypeID
            		+" and itemID="+itemID;
            		
            	log4j.print( strSQL );	
            		
            	prepareStatement(strSQL);
            	executeQuery();
            	c=getDataEntitiesFromResultSet( ActionTraceInfo.class );

            }
            catch(Exception e)
            {
            	throw new ITreasuryDAOException("查询历史跟踪信息出错",e);
            }

            /*----------------finalize Dao-----------------*/
            try 
            {
            	finalizeDAO();
            } 
            catch (ITreasuryDAOException e) 
            {
            	throw e;
            }
            /*----------------end of finalize---------------*/
        } catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally
        {
           finalizeDAO();
        }
		return (c.size () > 0 ? c : null);
	}			
}

