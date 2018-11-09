package com.iss.itreasury.craftbrother.transferloancontract.counterparty.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.util.Constant;

public class CounterparBankDao extends ITreasuryDAO 
{
	public CounterparBankDao()
	{
		super("CRA_COUNTERPARTBANK");
	}
	public CounterparBankDao(Connection  conn)
	{
		super("CRA_COUNTERPARTBANK","SEQ_CRA_COUNTERPARTBANK",conn);
	}
	/**
     * jzw 2010-05-24
     * 判断是否要加签章
     * @param clientID  客户id
     * @param transTypeID 交易类型编号
     * @param nOfficeID 办事处ID
     * @param nCurrencyID 币种ID
     * @return String
	 * @throws ITreasuryDAOException
	 */
	public String findIsSignature(long clientID,long transTypeID,long nOfficeID,long nCurrencyID,String billName) throws ITreasuryDAOException {
		ResultSet res = null;
		String isSignature = "0";
		String transSignature = "0";
		try {
			initDAO();
			StringBuffer buffer = new StringBuffer(); 
			buffer.append("SELECT NISSIGNATURE FROM \n");
			buffer.append("sett_signature");
			buffer.append("\n WHERE NOFFICEID="+nOfficeID+" and NCLIENTID="+clientID);
			System.out.println("客户是否授权允许签章语句---"+buffer.toString());  //jzw 2010-05-26
			prepareStatement(buffer.toString());
			res = executeQuery();
			try {
				while(res!=null&&res.next()){
					isSignature = res.getString("NISSIGNATURE");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			buffer = new StringBuffer();
			buffer.append("SELECT NISSIGNATURE FROM \n");
			buffer.append("print_billrelation");
			buffer.append("\n WHERE NOFFICEID="+nOfficeID+" and ncurrency="+nCurrencyID);
			buffer.append("\n and ntransactiontypeid="+transTypeID+" and upper(stempname)='"+billName.toUpperCase()+"' and ndeptid="+Constant.NETBANK);
			System.out.println("单据类型是否授权允许签章语句---"+buffer.toString());  //jzw 2010-05-26
			prepareStatement(buffer.toString());
			res = executeQuery();
			try {
				while(res!=null&&res.next()){
						transSignature = res.getString("NISSIGNATURE");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if(isSignature!=null&&isSignature.equals("1")&&transSignature!=null&&transSignature.equals("1")){
				return isSignature;
			}
		} catch (ITreasuryDAOException ide) {
			throw ide;
		} finally {// added by mzh_fu 2008/03/26 将关闭连接放到 finally 中
			finalizeDAO();
		}
		return "0";
	}
}
