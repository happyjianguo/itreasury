/*
 * Created on 2004-2-17
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.ebank.obquery.bizlogic;
import java.sql.*;
import com.iss.itreasury.util.*;
import java.util.*;
import com.iss.itreasury.loan.util.*;
import com.iss.itreasury.ebank.util.*;
import com.iss.itreasury.ebank.obdao.OBBaseDao;
import com.iss.itreasury.ebank.obdataentity.*;
import com.iss.itreasury.ebank.obquery.dataentity.*;
import com.iss.itreasury.ebank.obquery.dao.*;
import com.iss.itreasury.loan.query.dataentity.*;
import com.iss.itreasury.loan.extendapply.dataentity.* ;
import com.iss.itreasury.loan.repayplan.dataentity.RepayPlanInfo ;
import com.iss.itreasury.loan.contract.dataentity.*;
import com.iss.itreasury.loan.contract.dao.*;
import com.iss.itreasury.loan.loanpaynotice.dao.* ;
import com.iss.itreasury.loan.loanpaynotice.dataentity.* ;
/**
 * @author gdzhao
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class OBContractQuery {
	/**
	 * 查询合同信息，操作loan_Contractform表和它的附属表
	 * @param long lID 合同标示
	 * @return ContractInfo
	 * @writen by  2003-10-6
	 */
	public ContractInfo findByID(long lID) throws IException
	{
		ContractInfo info = null;
		try
		{
			ContractDao contractDao = new ContractDao();
			info = contractDao.findByID(lID);
		}
		catch (Exception e)
		{
			throw new IException("Gen_E001");
		}
		return info;
	}
	public Collection queryContractPlan(OBQueryTermInfo qInfo) throws Exception
	{
		Collection info = null;
		try
		{
			OBQueryDao dao = new OBQueryDao();
			info = dao.queryContractPlan( qInfo );
		}
		catch (Exception e)
		{
			throw new IException("Gen_E001");
		}
		return info;
	}
	
	public Collection findPlanByContract(long lContractID, long lPageLineCount, long lPageNo, long lOrderParam, long lDesc) throws Exception
	{
		Collection info=null;
		try
		{
			OBQueryDao dao=new OBQueryDao();
			info=dao.findPlanByContract( lContractID,lPageLineCount,lPageNo,lOrderParam,lDesc);
		}
		catch (Exception e)
		{
			throw new IException("Gen_E001");
		}
		return info;				
		
	}
	public Collection findPlanByVer(
			long ContractPayPlanVersionID,
			long lPageLineCount,
			long lPageNo,
			long lOrderParam,
			long lDesc,
			long lUserID,
			long lOfficeID)
			throws Exception
	{
		Collection info =null;
		try
		{
			OBQueryDao dao=new OBQueryDao();
			info=dao.findPlanByVer( ContractPayPlanVersionID,lPageLineCount,lPageNo,lOrderParam,lDesc,lUserID,lOfficeID);
		}
		catch (Exception e)
		{
			throw new IException("Gen_E001");
		}
		return info;				
	}
	public LoanPayNoticeInfo findLoanPayNoticeByID( long lLoanPayNoticeID ) throws Exception
	{
		LoanPayNoticeInfo info = new LoanPayNoticeInfo() ;
		try {
		LoanPayNoticeDao loanPayNoticeDao = new LoanPayNoticeDao();
		info = loanPayNoticeDao.findLoanPayNoticeByID(lLoanPayNoticeID);
		} catch (Exception e) {
			throw new IException("Gen_E001");
		}
		return info ;
	}
	public Collection queryContract(OBQueryTermInfo qInfo) throws Exception
	{
		Collection c=null;
		OBQueryDao dao=new OBQueryDao();
		try
		{
			c=dao.queryContract( qInfo );
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return c;
	}
	public QuerySumInfo queryContractSum(OBQueryTermInfo qInfo) throws Exception
	{
		QuerySumInfo sumInfo = null;
		OBQueryDao dao=new OBQueryDao();
		try
		{
			sumInfo=dao.queryContractSum( qInfo );
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return sumInfo;
	}
    
    /*
     * 由合同ID找到其所有的 用款、还款计划
     * @author haoning
     * @time 2004-3-9
     * @param lContractID
     * function
     */
    
    public Collection findPlanDetailByContractID(long lContractID) throws Exception
    {
        Collection info=null;
        try
        {
            OBQueryDao dao=new OBQueryDao();
            info=dao.findPlanDetailByContractID(lContractID);
        }
        catch (Exception e)
        {
            throw new IException("Gen_E001");
        }
        return info;                
        
    }


}
