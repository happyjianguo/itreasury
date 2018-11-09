/*
 * Created on 2004-6-8
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.securities.securitiescontractplan.dao;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.securities.util.*;
import com.iss.itreasury.securities.apply.dataentity.*;
import com.iss.itreasury.securities.exception.*;
import com.iss.itreasury.util.*;
import com.iss.itreasury.system.bizlogic.*;
import com.iss.itreasury.system.dataentity.*;
import java.util.*;
import java.sql.Timestamp;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.iss.itreasury.securities.securitiescontractplan.dataentity.*;
import com.iss.itreasury.securities.securitiescontract.dataentity.*;


/**
 * @author gdzhao
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class SecuritiesContractPlanDetailDao extends SecuritiesDAO{
	protected Log4j log4j = new Log4j(Constant.ModuleType.SECURITIES, this);

	public SecuritiesContractPlanDetailDao()
	{
		super("SEC_ContractPlanDetail");
	}

	public Collection findPlanDetailByVer(long verID) throws Exception
	{
		Collection c = null;
		String strSQL="";
		
		/*-----------------init DAO --------------------*/
		try 
		{
			initDAO();
		} 
		catch (ITreasuryDAOException e) 
		{
			throw new SecuritiesDAOException(e);
		}
		/*----------------end of init------------------*/		
				
		try 
		{
			strSQL = "select * from SEC_ContractPlanDetail \n"
				+" where 1=1 \n"
				+" and planVersionID="+verID;
				
			prepareStatement(strSQL);
			executeQuery();
			c=getDataEntitiesFromResultSet( SecuritiesContractPlanDetailInfo.class );
		}
		catch(Exception e)
		{
			throw new SecuritiesDAOException("查找合同计划明细时产生错误",e);
		}

		/*----------------finalize Dao-----------------*/
		try 
		{
			finalizeDAO();
		} 
		catch (ITreasuryDAOException e) 
		{
			throw new SecuritiesDAOException(e);
		}
		/*----------------end of finalize---------------*/		
		return (c.size () > 0 ? c : null);					
	}

	public void deletePlanDetailByVer(long verID) throws Exception
	{
		String strSQL="";
		
		/*-----------------init DAO --------------------*/
		try 
		{
			initDAO();
		} 
		catch (ITreasuryDAOException e) 
		{
			throw new SecuritiesDAOException(e);
		}
		/*----------------end of init------------------*/		
				
		try 
		{
			strSQL = "delete from SEC_ContractPlanDetail \n"
				+" where 1=1 \n"
				+" and planVersionID="+verID;
				
			prepareStatement(strSQL);
			executeQuery();
		}
		catch(Exception e)
		{
			throw new SecuritiesDAOException("删除合同计划明细时产生错误",e);
		}

		/*----------------finalize Dao-----------------*/
		try 
		{
			finalizeDAO();
		} 
		catch (ITreasuryDAOException e) 
		{
			throw new SecuritiesDAOException(e);
		}
		/*----------------end of finalize---------------*/		
				
	}
	
	public void delete(long id)  throws ITreasuryDAOException{
		String strSQL="";
		
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
			strSQL = "delete from SEC_ContractPlanDetail \n"
				+" where 1=1 \n"
				+" and ID="+id;
				
			prepareStatement(strSQL);
			executeQuery();
		}
		catch(Exception e)
		{
			throw new ITreasuryDAOException("删除合同计划明细时产生错误",e);
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
	}	
}
