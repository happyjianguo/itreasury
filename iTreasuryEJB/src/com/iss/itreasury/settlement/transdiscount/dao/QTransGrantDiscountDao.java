package com.iss.itreasury.settlement.transdiscount.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Map;


import com.iss.itreasury.loan.discount.dataentity.DiscountBillInfo;
import com.iss.itreasury.settlement.transdiscount.dataentity.QueryConditionInfo;
import com.iss.itreasury.settlement.transloan.dataentity.TransGrantLoanInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Log;

/**
 * 账户查询数据层
 * 
 * @author xiang
 * 
 */
public class QTransGrantDiscountDao {

	public String queryLoanSQL(QueryConditionInfo info) {
		long nOfficeID = info.getOfficeID();
		long nCurrencyID = info.getCurrencyID();
		long nUserID = info.getUserID();
		long nTypeID = info.getTypeID();
		long nTransactionTypeID = info.getTransactionTypeID();
		long nStatusID = info.getStatusID();
		Timestamp date = info.getDate();
		String dt = DataFormat.getDateString(date);

		StringBuffer buffer = new StringBuffer("");
		buffer.append(" select * ");

		buffer.append(" from Sett_TransGrantDiscount \n");
		buffer.append(" where ID is not null ");

		// 日期特别处理
		if (nStatusID != SETTConstant.TransactionStatus.TEMPSAVE) {
			buffer.append(" and dtExecute=to_date('"
					+ DataFormat.getDateString(date) + "','yyyy-mm-dd')");
		}

		buffer.append(" and nOfficeID=" + nOfficeID);
		buffer.append(" and nCurrencyID=" + nCurrencyID);

		// 根据查询类型处理用户ID
		if (nTypeID == 0) {
			buffer.append(" and nInputUserID=" + nUserID);
			buffer.append(" and nCheckUserID!=" + nUserID);
		} else if (nTypeID == 1) {
			buffer.append(" and nCheckUserID=" + nUserID);
			buffer.append(" and nInputUserID!=" + nUserID);
		}

		buffer.append(" and nTransactionTypeID=" + nTransactionTypeID);

		if (nTypeID == 0) {// 处理的查找
			if (nStatusID > 0)
				buffer.append(" and nStatusID=" + nStatusID);
			else
				buffer.append(" and nStatusID in("
						+ SETTConstant.TransactionStatus.TEMPSAVE + ","
						+ SETTConstant.TransactionStatus.SAVE + ") ");
		} else if (nTypeID == 1) {// 复核的查找
			buffer.append(" and nStatusID=" + nStatusID);
		}
		Log.print(buffer.toString());
		return buffer.toString();
	}
	
	public String getSQLByDiscountBillByContractIdAndCredenceId(Map<String,String> map) throws SQLException{
		String sql = "";
		String contractid = map.get("lcontractid");
		String credenceId = map.get("ldiscountnoteid");
		sql=" select a.*,c.dtdiscountdate ,c.mdiscountrate MRATE from Loan_DiscountContractBill a,loan_discountcredence b,loan_contractform c where a.nStatusID=1 " +
		" and a.ndiscountcredenceid=b.id and c.id = b.ncontractid"
         +" and a.ncontractid="+contractid+ " and a.ndiscountcredenceid = " + credenceId;
		System.out.println("sql="+sql);	
		return sql;
	}
	
	public DiscountBillInfo queryDiscountBillSumAmount(Map<String,String> map) throws Exception
	{
		Connection conn = null;
		DiscountBillInfo info = new DiscountBillInfo();
		PreparedStatement ps = null;
		ResultSet rs = null;
		long lRecordCount=-1;
		double dTotalAmount = 0; //总票据金额
		double dTotalAccrual = 0; //总票据利息
		double dTotalRealAmount = 0; //总票据实付金额		
		try
		{
			String sql = "";
			String contractid = map.get("lcontractid");
			String credenceId = map.get("ldiscountnoteid");		
			conn=Database.getConnection();
			sql = " select count(*),sum(nvl(mAmount,0)),sum(nvl(mCheckAmount,0)) "+
			" from Loan_DiscountContractBill where nStatusID=" + Constant.RecordStatus.VALID + " and ncontractid=" + contractid + " and ndiscountcredenceid = " + credenceId;
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();		
			while(rs.next())
			{
				lRecordCount = rs.getLong(1);
				dTotalAmount = rs.getDouble(2);
				dTotalRealAmount = rs.getDouble(3);
				dTotalAccrual = dTotalAmount - dTotalRealAmount;	
				break;
			}
			info.setCount(lRecordCount);
			info.setTotalAmount(DataFormat.formatDouble(dTotalAmount, 2));
			info.setTotalAccrual(DataFormat.formatDouble(dTotalAccrual, 2));
			info.setTotalRealAmount(DataFormat.formatDouble(dTotalRealAmount,2));					
		}catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("汇总查询报错!",e);
		}
		finally
		{
			if(rs!=null)
			{	rs.close();
			rs = null;}
			if(ps!=null)
			{	ps.close();
			    ps = null;}
			if(conn!=null){
				conn.close();
				conn=null;
			}			
		}
		return info;
		
	}

}
