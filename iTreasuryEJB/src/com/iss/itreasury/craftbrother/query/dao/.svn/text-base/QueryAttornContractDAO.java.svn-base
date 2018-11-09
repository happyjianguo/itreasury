package com.iss.itreasury.craftbrother.query.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.iss.itreasury.craftbrother.query.dataentity.AssetInterestCalInfo;
import com.iss.itreasury.craftbrother.query.dataentity.QueryAttornContractInfo;
import com.iss.itreasury.craftbrother.query.dataentity.QueryAttornContractResultInfo;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.securities.securitiescontract.dataentity.SecuritiesContractQueryInfo;
import com.iss.itreasury.securities.util.SECConstant;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.system.dao.PageLoader;

public class QueryAttornContractDAO {
		private StringBuffer m_sbSelect = null;
		private StringBuffer m_sbFrom = null;
		private StringBuffer m_sbWhere = null;
		private StringBuffer m_sbOrderBy = null;
		
		public QueryAttornContractDAO()
		{
			
		}
		
		public List QueryAttornContractTotalInfo(QueryAttornContractInfo info) throws Exception{
			List contractList = new ArrayList();
			Connection con = null;
			ResultSet rs = null;
			PreparedStatement ps = null;
			con = Database.getConnection();
			String sql = "select a.transactionTypeId transactionTypeId,b.name transactionTypeName,sum(a.amount) contractTotalAmount, \n";
			sql += "sum(c.noticeamount) noticeTotalAmount \n";
			sql += "from sec_applycontract a,sec_transactiontype b,(select aa.contractid contractid,aa.noticeamount noticeamount from sec_noticeform aa where aa.transactionTypeId="+SECConstant.BusinessType.CAPITAL_REPURCHASE.PAYBACK_NOTIFY+") c \n";
			sql += " where a.transactiontypeid = b.id \n";
			sql += " and c.contractid(+)= a.id \n";
			sql += "and a.inputdate >= to_date(?,'yyyy-MM-dd') and a.inputdate <= to_date(?,'yyyy-MM-dd') ";
			sql += " group by a.transactionTypeId,b.name \n";
			ps = con.prepareStatement(sql.toString());
			ps.setString(1,DataFormat.getDateString(info.getStartInputDate()));
			ps.setString(2,DataFormat.getDateString(info.getEndInputDate()));
			rs = ps.executeQuery();
			System.out.println("SQL=============:"+sql);
			while(rs.next()){
				QueryAttornContractResultInfo resultInfo = new QueryAttornContractResultInfo();
				resultInfo.setTranscationTypeId(rs.getLong("transactionTypeId"));
				resultInfo.setTranscationType(rs.getString("transactionTypeName"));
				resultInfo.setTotalSaleAmount(rs.getDouble("contractTotalAmount"));
				resultInfo.setTotalBuyAmount(rs.getDouble("noticeTotalAmount"));
				resultInfo.setStartDate(info.getStartInputDate());
				resultInfo.setEndDate(info.getEndInputDate());
				contractList.add(resultInfo);
			}
			return contractList;
		}
		
		public List QueryAttornContractParticularInfo(QueryAttornContractInfo info) throws Exception{
			List contractList = new ArrayList();
			Connection con = null;
			ResultSet rs = null;
			PreparedStatement ps = null;
			con = Database.getConnection();
			String sql = "select a.transactionTypeId transactionTypeId,b.name transactionTypeName,a.code contractCode, \n";
			sql += " a.amount contractAmount,a.rate contractRate,a.inputdate contractInputDate, c.noticeAmount noticeAmount,c.executerate noticeRate\n";
			sql += "from sec_applycontract a,sec_transactiontype b,(select aa.contractid contractid,aa.noticeamount noticeamount,aa.executerate executerate from sec_noticeform aa where aa.transactionTypeId="+SECConstant.BusinessType.CAPITAL_REPURCHASE.PAYBACK_NOTIFY+") c \n";
			sql += " where a.transactiontypeid = b.id \n";
			sql += " and c.contractid(+) = a.id \n";
			sql += " and a.inputdate >= to_date(?,'yyyy-MM-dd') and a.inputdate <= to_date(?,'yyyy-MM-dd') ";
			sql += " and a.transactionTypeId = ? \n";
			ps = con.prepareStatement(sql.toString());
			ps.setString(1,DataFormat.getDateString(info.getStartInputDate()));
			ps.setString(2,DataFormat.getDateString(info.getEndInputDate()));
			ps.setLong(3,info.getContractTypeId());
			rs = ps.executeQuery();
			System.out.println("SQL=============:"+sql);
			while(rs.next()){
				QueryAttornContractResultInfo resultInfo = new QueryAttornContractResultInfo();
				resultInfo.setTranscationTypeId(rs.getLong("transactionTypeId"));
				resultInfo.setTranscationType(rs.getString("transactionTypeName"));
				resultInfo.setContractCode(rs.getString("contractCode"));
				resultInfo.setContractRate(rs.getDouble("contractRate"));
				resultInfo.setInputDate(rs.getTimestamp("contractInputDate"));
				resultInfo.setContractAmount(rs.getDouble("contractAmount"));
				resultInfo.setSaleAmount(rs.getDouble("noticeAmount"));
				resultInfo.setBuyAmount(rs.getDouble("contractAmount"));
				resultInfo.setNoticeRate(rs.getDouble("noticeRate"));
				contractList.add(resultInfo);
			}
			return contractList;
		}
	}

