package com.iss.itreasury.settlement.interest.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.settlement.interest.dataentity.CommissionInfo;

public class Sett_CommissionDAO extends SettlementDAO{
	public Sett_CommissionDAO()
	{
		super();
	}
	public Sett_CommissionDAO(String tableName)
	{
		super(tableName);
	}	
	
	public CommissionInfo findById(long id) throws Exception
	{
		CommissionInfo info = new CommissionInfo();
		Connection con = getConnection();
		PreparedStatement ps = null;		
		ResultSet rs = null;	
		StringBuffer sql = new StringBuffer();
		try {
			sql.append(" select contract.nborrowclientid clientId,s.dtexecute,s.stransno,p.nofficeid  officeid,s.npayinterestaccountid payaccountid, \n");
			sql.append(" s.minterest,s.sabstract,s.ninputuserid \n");
			sql.append(" from SETT_TRANSINTERESTSETTLEMENT s,loan_PayForm p,sett_SubAccount sub,loan_ContractForm contract \n");
			sql.append(" where s.nsubaccountid = sub.id \n");
			sql.append(" and sub.al_nloannoteid = p.id(+) \n");
			sql.append(" and p.ncontractid = contract.id(+) \n");
			sql.append(" and s.id ="+id);
			System.out.println("sql="+sql.toString());
			ps = con.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			if (rs.next()) {
				info.setBorrowClientId(rs.getLong("clientId"));
				info.setExecuteDate(rs.getTimestamp("dtexecute"));///////////////
				info.setTransNo(rs.getString("stransno"));/////////
				info.setOfficeId(rs.getLong("officeid"));    ///////////
				info.setAccountId(rs.getLong("payaccountid"));
				info.setCommissionAmount(rs.getDouble("minterest"));/////////////
				info.setAbstract(rs.getString("sabstract"));////////
				info.setInputUserId(rs.getLong("ninputuserid"));////////////
	
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		return info;
	}
	
	
}
