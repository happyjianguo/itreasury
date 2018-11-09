/*
 * Created on 2004-10-27
 * 
 * TODO To change the template for this generated file go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.settlement.repaymentloancorresponding.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Vector;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.settlement.base.*;
import com.iss.itreasury.settlement.transcurrentdeposit.dataentity.TransCurrentDepositInfo;
import com.iss.itreasury.settlement.util.NameRef;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.settlement.repaymentloancorresponding.dataentity.*;
import com.iss.itreasury.util.DataFormat;

/**
 * @author yychen
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ReportDao extends SettlementDAO
{
    public ReportDao()
    {
        super("Sett_transcurrentdeposit", false);
    }
    
    
    public Collection getReceiveResult(ReportQueryInfo queryInfo) throws SettlementDAOException
    {
    	Vector result = new Vector();
    	try
		{
	    	initDAO();
	    	String sql = " select b.id loandetailid,a.stransno,nreceiveclientid clientid,a.nreceiveaccountid accountid,a.mamount amount,a.dtintereststart,a.dtexecute "
	    				+" from sett_transcurrentdeposit a,sett_loandetail b "
						+" where b.transid = a.id "
						+" and a.nreceiveclientid > 0 "
						+" and b.status = " + SETTConstant.RecordStatus.VALID
	    				+" and a.nstatusid = " + SETTConstant.TransactionStatus.CHECK;//只查找已复核的
			if (queryInfo.getBeginDtExecute() != null)
			{
				String time = queryInfo.getBeginDtExecute().toString();
				time = time.substring(0, 10);
				sql += " and a.dtexecute >= to_date('" + time + "','yyyy-mm-dd') \n";
			}
			if (queryInfo.getEndDtExecute() != null)
			{
				String time = queryInfo.getEndDtExecute().toString();
				time = time.substring(0, 10);
				sql += " and a.dtexecute <= to_date('" + time + "','yyyy-mm-dd') \n";
			}
			if (queryInfo.getBeginClientId() > 0)
			{
				sql += " and a.nreceiveclientid >= " + queryInfo.getBeginClientId();
			}
			if (queryInfo.getEndClientId() > 0)
			{
				sql += " and a.nreceiveclientid <= " + queryInfo.getEndClientId();
			}
			sql += " order by b.id ";
			
			this.prepareStatement(sql);
			this.executeQuery();
			
			while(transRS.next())
			{
				ReportInfo info = new ReportInfo();
				info.setTypeName("银行收款");
				info.setLoanDetailId(transRS.getLong("loanDetailId"));
				info.setSTransNo(transRS.getString("stransno"));
				info.setClientId(transRS.getLong("clientId"));
				info.setClientName(DataFormat.formatString(NameRef.getClientNameByID(info.getClientId())));
				info.setAccountId(transRS.getLong("accountId"));
				info.setAccountNo(DataFormat.formatString(NameRef.getAccountNoByID(info.getAccountId())));
				info.setAmount(transRS.getDouble("amount"));
				info.setDtInterestStart(transRS.getTimestamp("dtInterestStart"));
				info.setDtExecute(transRS.getTimestamp("dtExecute"));
				result.add(info);
			}
		}
		catch (ITreasuryDAOException e)
		{
			throw new SettlementDAOException("查找收款明细信息产生错误", e);
		}
		catch (Exception e)
		{
			throw new SettlementDAOException("查找收款明细信息产生错误", e);
		}
		finally 
		{
			try
			{
				finalizeDAO();
			}
			catch (ITreasuryDAOException e)
			{
				throw new SettlementDAOException(e);
			}
		}
		
		return result;
    }
    
    public Collection getPayResult(ReportQueryInfo queryInfo) throws SettlementDAOException
    {
    	Vector result = new Vector();
    	try
		{
	    	initDAO();
	    	String sql = " select b.loandetailid loandetailid,a.stransno,npayclientid clientid,a.npayaccountid accountid,b.amount amount,a.dtintereststart,a.dtexecute "
	    				+" from sett_transcurrentdeposit a,sett_repaymentdetail b "
						+" where b.transid = a.id "
						+" and a.npayclientid > 0 "
						+" and b.status = " + SETTConstant.RecordStatus.VALID
	    				+" and a.nstatusid = " + SETTConstant.TransactionStatus.CHECK;//只查找已复核的
			if (queryInfo.getBeginDtExecute() != null)
			{
				String time = queryInfo.getBeginDtExecute().toString();
				time = time.substring(0, 10);
				sql += " and a.dtexecute >= to_date('" + time + "','yyyy-mm-dd') \n";
			}
			if (queryInfo.getEndDtExecute() != null)
			{
				String time = queryInfo.getEndDtExecute().toString();
				time = time.substring(0, 10);
				sql += " and a.dtexecute <= to_date('" + time + "','yyyy-mm-dd') \n";
			}
			if (queryInfo.getBeginClientId() > 0)
			{
				sql += " and a.npayclientid >= " + queryInfo.getBeginClientId();
			}
			if (queryInfo.getEndClientId() > 0)
			{
				sql += " and a.npayclientid <= " + queryInfo.getEndClientId();
			}
			sql += " order by b.loandetailid,b.id ";
			
			this.prepareStatement(sql);
			this.executeQuery();
			
			while(transRS.next())
			{
				ReportInfo info = new ReportInfo();
				info.setTypeName("银行付款");
				info.setLoanDetailId(transRS.getLong("loanDetailId"));
				info.setSTransNo(transRS.getString("stransno"));
				info.setClientId(transRS.getLong("clientId"));
				info.setClientName(DataFormat.formatString(NameRef.getClientNameByID(info.getClientId())));
				info.setAccountId(transRS.getLong("accountId"));
				info.setAccountNo(DataFormat.formatString(NameRef.getAccountNoByID(info.getAccountId())));
				info.setAmount(transRS.getDouble("amount"));
				info.setDtInterestStart(transRS.getTimestamp("dtInterestStart"));
				info.setDtExecute(transRS.getTimestamp("dtExecute"));
				result.add(info);
			}
		}
		catch (ITreasuryDAOException e)
		{
			throw new SettlementDAOException("查找收款明细信息产生错误", e);
		}
		catch (Exception e)
		{
			throw new SettlementDAOException("查找收款明细信息产生错误", e);
		}
		finally 
		{
			try
			{
				finalizeDAO();
			}
			catch (ITreasuryDAOException e)
			{
				throw new SettlementDAOException(e);
			}
		}
		
		return result;
    }
}

