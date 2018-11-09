/*
 * Created on 2003-10-23
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.settlement.interest.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import oracle.net.ano.SupervisorService;

import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.settlement.interest.dataentity.InterestRatePlanItemInfo;
import com.iss.itreasury.util.Log4j;
import com.iss.itreasury.util.*;
import com.iss.itreasury.settlement.util.SETTConstant;

/**
 * @author yiwang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class sett_InterestRatePlanItemDAO extends InterestDAO
{
	private Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);	
	
	public sett_InterestRatePlanItemDAO(Connection transConn){
		super(transConn);
		super.strTableName = "sett_InterestRatePlanItem";
	}
	
    /**
     *  
     * 取银行利率ID和浮动利率，对表sett_InterestRatePlanItem进行查询，条件是利率计划ID,当前余额，
     * @param interestRatePlanID
     * @param balance
     * @return InterestRatePlanItemInfo sett_interestRatePlanItem对应的实体类
     * @throws SQLException
     */

	public InterestRatePlanItemInfo getInfoByInterestPlanIDAndBalance(long interestRatePlanID,
																		double balance) throws SQLException{
		InterestRatePlanItemInfo info = new InterestRatePlanItemInfo();
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;				
		
		StringBuffer bufferSQL = new StringBuffer();
		
		conn = getConnection();
				
		bufferSQL.append("SELECT * FROM ");
		bufferSQL.append(strTableName);
		bufferSQL.append(" WHERE nInterestRatePlanID = " + interestRatePlanID); 
		bufferSQL.append(" AND ((mBanlance >= " + balance);
		bufferSQL.append(" AND NBalanceType = " + SETTConstant.BalanceType.LOWER);
		bufferSQL.append(") OR (mBanlance < " + balance);
		bufferSQL.append(" AND NBalanceType = " + SETTConstant.BalanceType.UPPER );
		bufferSQL.append(")) ORDER BY mBanlance DESC");
		
		log.info(bufferSQL.toString());
		ps = conn.prepareStatement(bufferSQL.toString());
		rs = ps.executeQuery();
		info = getInfoFromResultSet(rs);
		
		cleanup(rs);
		cleanup(ps);
		cleanup(conn);
		 
		return info; 
	}

	private InterestRatePlanItemInfo getInfoFromResultSet(ResultSet rs) throws SQLException{
		InterestRatePlanItemInfo info = new InterestRatePlanItemInfo();
		if(rs.next()){
			info.setBalance(rs.getDouble("MBANLANCE"));
			info.setBalanceType(rs.getLong("NBALANCETYPE"));
			info.setDateEnd(rs.getTimestamp("DTDATESTART"));
			info.setDateStart(rs.getTimestamp("DTDATEEND"));
			info.setDayCount(rs.getLong("NDAYCOUNT"));
			info.setDayType(rs.getLong("NDAYTYPE"));
			info.setID(rs.getLong("ID"));
			info.setInterestRate(rs.getFloat("FINTERESTRATE"));
			info.setInterestRateID(rs.getLong("NINTERESTRATEID"));
			info.setInterestRatePlanID(rs.getLong("NINTERESTRATEPLANID"));
			info.setSerialNo(rs.getLong("NSERIALNO"));
		}		
		return info;				
	}
	
}
