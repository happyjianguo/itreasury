/*
 * Created on 2004-10-25
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

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.settlement.base.*;
import com.iss.itreasury.settlement.transcurrentdeposit.dataentity.TransCurrentDepositInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.settlement.repaymentloancorresponding.dataentity.*;

/**
 * @author yychen
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class GrantLoanDao extends SettlementDAO
{
    public GrantLoanDao()
    {
        super("Sett_LoanDetail", false);
    }
    
	public GrantLoanDao(Connection conn)
	{
		super("Sett_LoanDetail",conn);					//调用父类构建器
	}
    
    
    public void reduceBalance(LoanDetailInfo info,double balance) throws SettlementDAOException
    {
    	GrantLoanDao dao = new GrantLoanDao();
    	try
		{
    		info.setBalance(info.getBalance()-balance);
    		dao.update(info);
		}
		catch (Exception e)
		{
			throw new SettlementDAOException("减少余额产生错误", e);
		}
    }
    
    public void addBalance(LoanDetailInfo info,double balance) throws SettlementDAOException
    {
    	GrantLoanDao dao = new GrantLoanDao();
    	try
		{
    		info.setBalance(info.getBalance()+balance);
    		dao.update(info);
		}
		catch (Exception e)
		{
			throw new SettlementDAOException("回复(增加)余额产生错误", e);
		}
    }
    
    public Collection findLoanDetail() throws SettlementDAOException
    {
    	Collection loanDetailInfos = null;
    	try
		{
	    	initDAO();
			//查询所有有效的银行账户
			String sql = "select * from Sett_LoanDetail where status = " + SETTConstant.RecordStatus.VALID + " order by loandate asc";
			
			this.prepareStatement(sql);
			this.executeQuery();
			
			loanDetailInfos = this.getDataEntitiesFromResultSet(LoanDetailInfo.class);
		}
		catch (ITreasuryDAOException e)
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
		
		return loanDetailInfos;
    }
    
    //通过交易id获得收款明细id
    public long findIdByTransId(long transId) throws SettlementDAOException
	{
    	long id = -1;
    	String sql = "";
		PreparedStatement ps = null;
		ResultSet rs = null;	
    	try
		{
    		initDAO();
    		sql = "select id from sett_loandetail where transid = " + transId + " and status = " + SETTConstant.RecordStatus.VALID;
			ps = transConn.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs.next())
			{
				id = rs.getLong(1);
			}
		}
    	catch (Exception e)
		{
    		throw new SettlementDAOException("通过交易id获得收款明细id失败",e);
		}
    	finally
		{
			try 
			{
	    		if(rs!=null) rs.close();
	    		if(ps!=null) ps.close();
			} 
    		catch (SQLException e)
			{
    			throw new SettlementDAOException("通过交易id获得收款明细id失败",e);
			}
			try
			{
				finalizeDAO();
			}
			catch (ITreasuryDAOException e)
			{
				throw new SettlementDAOException(e);
			}
		}
    	return id;
	}
    
    public LoanDetailInfo findLoanDetailById(long id) throws SettlementDAOException
    {
    	LoanDetailInfo loanDetailInfo = null;
    	try
		{
	    	initDAO();
			//查询所有有效的银行账户
			String sql = "select * from Sett_LoanDetail where id = " + id  ;
			
			this.prepareStatement(sql);
			this.executeQuery();
			
			loanDetailInfo = (LoanDetailInfo)this.findByID(id, LoanDetailInfo.class);
		}
		catch (ITreasuryDAOException e)
		{
			throw new SettlementDAOException("通过id查找收款明细信息产生错误", e);
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
		
		return loanDetailInfo;
    }
}

