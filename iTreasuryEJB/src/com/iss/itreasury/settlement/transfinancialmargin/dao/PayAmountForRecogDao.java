package com.iss.itreasury.settlement.transfinancialmargin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.settlement.transfinancialmargin.dataentity.PayAmountForRecogInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Log4j;

public class  PayAmountForRecogDao extends SettlementDAO{
	protected Log4j log4j = new Log4j(Constant.ModuleType.SETTLEMENT, this);
	public PayAmountForRecogDao()
	{
		super("SETT_PAYAMOUNTFORRECOG");
	}
	
	public PayAmountForRecogInfo findBySubAccountID(long subAccountID)throws Exception{
		PayAmountForRecogInfo returnInfo = new PayAmountForRecogInfo();
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			StringBuffer buffer = new StringBuffer();
			buffer.append("select * from SETT_PAYAMOUNTFORRECOG where nSubAccountID = ?  and NSTATUSID = 1 ");
			ps = con.prepareStatement(buffer.toString());
			ps.setLong(1, subAccountID);
			rs = ps.executeQuery();
			if(rs.next()){
				returnInfo.setId(rs.getLong("id"));
				returnInfo.setNAccountID(rs.getLong("NAccountID"));
				returnInfo.setNSubAccountID(rs.getLong("NSubAccountID"));
				returnInfo.setPayAmount(rs.getDouble("PayAmount"));
				returnInfo.setNContractID(rs.getLong("NContractID"));
				returnInfo.setNStatusID(rs.getLong("nStatusID"));
			}
		}catch(Exception e){
			 e.printStackTrace();
			 throw e;
		}
		finally{
			try{
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			}catch(Exception e){
				e.printStackTrace();
				throw e;
			}
		} 
		
		return returnInfo;
	}

}
