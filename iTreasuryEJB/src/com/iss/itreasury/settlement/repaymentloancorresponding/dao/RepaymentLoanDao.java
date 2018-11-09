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
import com.iss.itreasury.settlement.repaymentloancorresponding.dataentity.LoanDetailInfo;
import com.iss.itreasury.settlement.repaymentloancorresponding.dataentity.RepaymentDetailInfo;
import com.iss.itreasury.settlement.transcurrentdeposit.dataentity.TransCurrentDepositInfo;
import com.iss.itreasury.settlement.util.SETTConstant;

/**
 * @author yychen
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class RepaymentLoanDao extends SettlementDAO
{
    public RepaymentLoanDao()
    {
        super("Sett_RepaymentDetail", false);
    }
    
	public RepaymentLoanDao(Connection conn)
	{
		super("Sett_RepaymentDetail",conn);					//调用父类构建器
	}
    
    //通过交易id获得付款明细infos
    public Collection findRepaymentInfosByTransId(long transId) throws SettlementDAOException
	{
    	long id = -1;
    	String sql = "";
		PreparedStatement ps = null;
		ResultSet rs = null;	
		Collection repaymentDetailInfos = null;
    	try
		{
    		initDAO();
    		sql = "select * from sett_repaymentdetail where transid = " + transId + " and status = " + SETTConstant.RecordStatus.VALID;
			this.prepareStatement(sql);
			this.executeQuery();
			
			repaymentDetailInfos = this.getDataEntitiesFromResultSet(RepaymentDetailInfo.class);
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
    	return repaymentDetailInfos;
	}
}

