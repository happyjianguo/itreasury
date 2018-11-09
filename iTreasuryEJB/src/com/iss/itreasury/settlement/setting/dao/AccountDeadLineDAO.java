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
import com.iss.itreasury.settlement.setting.dataentity.AccountDeadLineInfo;
import com.iss.itreasury.settlement.setting.dataentity.AccountGuardSettingInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Log;

/**
 * @author zyzhu
 * 
 */
public class AccountDeadLineDAO extends SettlementDAO {

	

	// 构造函数
	public AccountDeadLineDAO()
    {
        super("Sett_AccountDeadLine",false);
        super.setUseMaxID();
    }
	

	//关联查找
	
	
	public Collection findByAll(AccountDeadLineInfo form) throws SQLException,Exception  {
		
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
			strSQLQuery.append(" from Sett_AccountDeadLine a, sett_account b");
			strSQLQuery.append(" where 1=1 ");
			strSQLQuery.append(" and a.accountid = b.id");
			if (form.getOfficeId()> 0) {
				strSQLQuery.append(" and b.NOFFICEID=" + form.getOfficeId());
			}
			if (form.getStatusId() > 0) {
				strSQLQuery.append(" and b.nstatusid=" + form.getStatusId());
				strSQLQuery.append(" and a.statusid=" + form.getStatusId());
			}
			initDAO();
			log.info(strSQLQuery.toString());
			prepareStatement(strSQLQuery.toString());
			executeQuery();
			try{
				while (transRS.next()) {
					AccountDeadLineInfo form1 = new AccountDeadLineInfo();
					form1.setId(transRS.getLong(1));
					form1.setBalanceLimited(transRS.getLong(3));
					form1.setOfficeId(transRS.getLong(10));
					form1.setAccountId(transRS.getLong(2));
					form1.setDebitLimited(transRS.getLong(4));
					form1.setCreditLimited(transRS.getLong(5));
					form1.setTranspayLimited(transRS.getLong(6));
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
			// 通过账号ID查询账户限额设置记录
			strSQLQuery.append("SELECT ");
			strSQLQuery.append("	* ");
			strSQLQuery.append("FROM ");
			strSQLQuery.append("	SETT_ACCOUNTDEADLINE ");
			strSQLQuery.append("WHERE ");
			strSQLQuery.append("	ACCOUNTID = " + lAccountId + " AND ");
			strSQLQuery.append("	STATUSID = " + Constant.RecordStatus.VALID);

			Log.print("通过账号Id查询账户限额底线记录的SQL:" + strSQLQuery.toString());

			// 查询数据库
			ps = conn.prepareStatement(strSQLQuery.toString());
			rs = ps.executeQuery();

			// 提取查询结果
			while (rs.next()) {
				// 新建Bean对象
				AccountDeadLineInfo adlInfo = new AccountDeadLineInfo();
				// 转换数据
				adlInfo.setId(rs.getLong("ID"));
				adlInfo.setAccountId(rs.getLong("ACCOUNTID"));
				adlInfo.setBalanceLimited(rs.getDouble("BALANCELIMITED"));
				adlInfo.setCreditLimited(rs.getDouble("CREDITLIMITED"));
				adlInfo.setDebitLimited(rs.getDouble("DEBITLIMITED"));
				adlInfo.setStatDays(rs.getLong("STATDAYS"));
				adlInfo.setTranspayLimited(rs.getDouble("TRANSPAYLIMITED"));
				adlInfo.setStatusId(rs.getLong("STATUSID"));
				
				// 加入结果集合
				aryResult.add(adlInfo);
			}

		} finally {
			// 清除数据库连接
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}

		//返回函数值
		return aryResult;

	}

	
}
