/*
 * Created on 2004-6-14
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
import java.sql.*;
import com.iss.itreasury.securities.securitiescontractplan.dataentity.*;
import com.iss.itreasury.securities.securitiescontract.dataentity.*;

/**
 * @author gdzhao
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class SecuritiesPlanModifyDao extends SecuritiesDAO{
	protected Log4j log4j = new Log4j(Constant.ModuleType.SECURITIES, this);

	public SecuritiesPlanModifyDao(){
		super("SEC_PlanModify");
	}

	public PlanModifyInfo findPlanModifyInfoByContract(long lID) throws Exception
	{
		String strSQL="";
		PlanModifyInfo c = null;
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		long verID = -1;
		
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
			conn=Database.getConnection() ;
			strSQL = "select ID from sec_PlanModify "
				+" where 1=1 \n"
				+" and contractID="+lID
				+" and statusID= "+SECConstant.PlanModifyStatus.SUBMIT ;
				
			ps=conn.prepareStatement(strSQL);
			rs=ps.executeQuery();
			if (rs.next()) 
			{
				verID=rs.getLong("ID");
			}
			if (rs!=null)
			{
				rs.close() ;
				rs=null;			
			}
			if (ps!=null)
			{
				ps.close() ;
				ps=null;
			}
			if (conn!=null)
			{
				conn.close();
				conn=null;
			}
			if (verID>0)
			{
				c=(PlanModifyInfo)this.findByID( verID,PlanModifyInfo.class  );
			}
		}
		catch(Exception e)
		{
			throw new SecuritiesDAOException("查找合同版本时产生错误",e);
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
		return (c);		
	}
	
}
