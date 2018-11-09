/**
 * 
 */
package com.iss.itreasury.settlement.setting.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.settlement.setting.dataentity.AccountGuardSettingInfo;
import com.iss.itreasury.settlement.setting.dataentity.BankInstructionSettingInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Log;

/**
 * @author zyzhu
 * 
 */
public class Sett_AccountGuardSettingDAO extends SettlementDAO {

	// 构造函数
	public Sett_AccountGuardSettingDAO() {
		super("Sett_AccountGuardSetting", false);
		super.setUseMaxID();
	}
	
	//关联查找
	
	
	public Collection findByAll(AccountGuardSettingInfo form) throws SQLException,Exception  {
		
//		 定义变量
		Collection aryResult = new ArrayList();

		// 定义数据库查询变量
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
   // 开始查询统计
       try{
			
//			 获取数据库连接
			conn = getConnection();
			StringBuffer strSQLQuery = new StringBuffer();
			strSQLQuery.append(" select a.*,b.nofficeid ");
			strSQLQuery.append(" from Sett_AccountGuardSetting a, sett_account b");
			strSQLQuery.append(" where 1=1 ");
			strSQLQuery.append(" and a.accountid = b.id");
			if (form.getOfficeId()> 0) {
				strSQLQuery.append(" and b.NOFFICEID=" + form.getOfficeId());
			}
			if (form.getStatusId() > 0) {
				strSQLQuery.append(" and b.nstatusid=" + form.getStatusId());
			}
			initDAO();
			log.info(strSQLQuery.toString());
			prepareStatement(strSQLQuery.toString());
			executeQuery();
			try{
				while (transRS.next()) {
					AccountGuardSettingInfo form1 = new AccountGuardSettingInfo();
					form1.setId(transRS.getLong(1));
					form1.setCurrencyId(transRS.getLong(2));
					form1.setOfficeId(transRS.getLong(3));
					form1.setAccountId(transRS.getLong(4));
					form1.setSumLimited(transRS.getLong(5));
					form1.setTransPayLimited(transRS.getLong(6));
					
					form1.setValidDate(transRS.getTimestamp(7));
					form1.setStatDays(transRS.getLong(8));
					aryResult.add(form1);			
			     }			
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
            catch (ITreasuryDAOException e) {
			throw new ITreasuryDAOException("查询账户限额设置异常", e);
		}  
            finally {
			finalizeDAO();
		}
		return aryResult;
	}


		
	  
       
       
       
       
	// 通过账号Id查询记录
	public Collection findByAccountId(long lAccountId) throws SQLException {

		// 定义变量
		ArrayList aryResult = new ArrayList();

		// 定义数据库查询变量
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;

		// 开始查询统计
		try {

			// 获取数据库连接
			conn = getConnection();

			StringBuffer strSQLQuery = new StringBuffer();
			// 通过账户ID查询账户监管设置记录
			strSQLQuery.append("SELECT ");
			strSQLQuery.append("	* ");
			strSQLQuery.append("FROM ");
			strSQLQuery.append("	SETT_ACCOUNTGUARDSETTING ");
			strSQLQuery.append("WHERE ");
			strSQLQuery.append("	ACCOUNTID = " + lAccountId + " AND ");
			strSQLQuery.append("	STATUSID = " + Constant.RecordStatus.VALID);

			Log.print("通过账号Id查询监管账户记录的SQL:" + strSQLQuery.toString());

			// 查询数据库
			ps = conn.prepareStatement(strSQLQuery.toString());
			rs = ps.executeQuery();

			// 提取查询结果
			while (rs.next()) {
				// 新建Bean对象
				AccountGuardSettingInfo agsInfo = new AccountGuardSettingInfo();
				// 转换数据
				agsInfo.setId(rs.getLong("ID"));
				agsInfo.setCurrencyId(rs.getLong("CURRENCYID"));
				agsInfo.setOfficeId(rs.getLong("OFFICEID"));
				agsInfo.setAccountId(rs.getLong("ACCOUNTID"));
				agsInfo.setSumLimited(rs.getDouble("SUMLIMITED"));
				agsInfo.setTransPayLimited(rs.getDouble("TRANSPAYLIMITED"));
				agsInfo.setValidDate(rs.getTimestamp("VALIDDATE"));
				agsInfo.setStatDays(rs.getLong("STATDAYS"));
				agsInfo.setRemark(rs.getString("REMARK"));
				agsInfo.setIsNeedGuard(rs.getLong("ISNEEDGUARD"));
				agsInfo.setInputUserId(rs.getLong("INPUTUSERID"));
				agsInfo.setInputDate(rs.getTimestamp("INPUTDATE"));
				agsInfo.setModifyUserId(rs.getLong("MODIFYUSERID"));
				agsInfo.setModifyDate(rs.getTimestamp("MODIFYDATE"));
				agsInfo.setStatusId(rs.getLong("STATUSID"));
				// 加入结果集合
				aryResult.add(agsInfo);
			}

		} finally {
			// 清除数据库连接
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}

		return aryResult;

	}
}
