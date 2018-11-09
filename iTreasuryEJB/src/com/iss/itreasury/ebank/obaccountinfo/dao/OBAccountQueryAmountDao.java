package com.iss.itreasury.ebank.obaccountinfo.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.jsp.JspWriter;

import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.ConfigConstant;
import com.iss.itreasury.util.Log4j;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.settlement.util.NameRef;
import com.iss.itreasury.ebank.obaccountinfo.bizlogic.OBAccountQueryWhere;
import com.iss.itreasury.ebank.obaccountinfo.dataentity.OBAccountQueryAmountInfo;
import com.iss.itreasury.ebank.obaccountinfo.dataentity.OBBankAccountInfo;
import com.iss.itreasury.settlement.account.dataentity.AccountTypeInfo;
import com.iss.itreasury.settlement.setting.dao.Sett_FilialeAccountSetDAO ;
import com.iss.itreasury.settlement.setting.dataentity.FilialeAccountInfo;

/**
 * Title:        		iTreasury
 * Description:                   
 * Copyright:           Copyright (c) 2004
 * Company:             iSoftStone
 * @author             kewen hu 
 * @version
 * Date of Creation     2004-01-12
 */
public class OBAccountQueryAmountDao extends OBConnectionDao {
	/** Logger */
	protected Log4j log = new Log4j(Constant.ModuleType.EBANK, this);
	/** 客户信息 */
	private Collection clientList = null;
	/**
	 * 构造函数
	 * @param  Connection conn
	 * @return nothing
	 * @exception nothing
	 */
	public OBAccountQueryAmountDao(Connection conn) {
		super(conn);
		super.setConnection(super.getConnection());
	}

	/**
	 * 断开连接
	 * @param  nothing
	 * @return nothing
	 * @exception throws SQLException
	 */
	public void closeConn() throws SQLException {
		try {
			cleanup(super.getConnection());
		} catch (SQLException se) {
			throw new SQLException("断开连接失败："+se.toString());
		}
	}

	/**
	 * 格式化金额
	 * @param double
	 * @return string
	 */
	public String formatAmount(double dAmount) {
		String sAmount = "";
		if(dAmount == 0.0) {
			sAmount="0.00";
		} else {
			sAmount=DataFormat.formatDisabledAmount(dAmount);
		}
		return sAmount;
	}

	/**
	 * 类型编码转换
	 * @param long[] lTypeID
	 * @return: such as 17,18,19,20,21,22,23
	 */
	protected String getTypeIdOfChange(long[] lTypeID) {
		String strTypeID = "";
		for (int i = 0; i < lTypeID.length; i++)
			strTypeID = strTypeID + lTypeID[i] + ",";
		//modify by zcwang 2007-3-26 如果帐户类型id 不存在，则返回-1
		if(!strTypeID.equals(""))
		{
		return strTypeID.substring(0, strTypeID.length() - 1);
		}
		else 
		{
			return "-1";
		}
	}

	/**
	 * 取得账户类型的SQL
	 * @param  OBAccountQueryWhere obaqw, long lAccountGroupTypeID
	 * @return StringBuffer
	 */
	private StringBuffer getStringForAccountGroupTypeID(OBAccountQueryWhere obaqw, long lAccountGroupTypeID) {
		StringBuffer bufSQL = new StringBuffer();
		if (lAccountGroupTypeID == SETTConstant.AccountGroupType.CURRENT) {
			bufSQL.append("	AND b.nAccountTypeID IN ("+this.getTypeIdOfChange(
				SETTConstant.AccountType.getCurrentAccountTypeCode(
				obaqw.getCurrencyID(),obaqw.getOfficeID()))+") \n");		
		} else if (lAccountGroupTypeID == SETTConstant.AccountGroupType.FIXED) {
			bufSQL.append("	AND b.nAccountTypeID IN ("+this.getTypeIdOfChange(
				SETTConstant.AccountType.getFixAccountTypeCode(
				obaqw.getCurrencyID(),obaqw.getOfficeID()))+") \n");
		} else if (lAccountGroupTypeID == SETTConstant.AccountGroupType.NOTIFY) {
			bufSQL.append("	AND b.nAccountTypeID IN ("+this.getTypeIdOfChange(
				SETTConstant.AccountType.getNotifyAccountTypeCode(
				obaqw.getCurrencyID(),obaqw.getOfficeID()))+") \n");
			//add by zcwang 2007-3-22
		} else if (lAccountGroupTypeID == SETTConstant.AccountGroupType.OTHERDEPOSIT) {
			bufSQL.append("	AND b.nAccountTypeID IN ("+this.getTypeIdOfChange(
				SETTConstant.AccountType.getOtherDepositAccountTypeCode(
				obaqw.getCurrencyID(),obaqw.getOfficeID()))+") \n");
		} else if (lAccountGroupTypeID == SETTConstant.AccountGroupType.TRUST) {
			bufSQL.append("	AND b.nAccountTypeID IN ("+this.getTypeIdOfChange(
				SETTConstant.AccountType.getTrustAccountTypeCode(
				obaqw.getCurrencyID(),obaqw.getOfficeID()))+") \n");
		} else if (lAccountGroupTypeID == SETTConstant.AccountGroupType.CONSIGN) {
			bufSQL.append("	AND b.nAccountTypeID IN ("+this.getTypeIdOfChange(
				SETTConstant.AccountType.getConsignAccountTypeCode(
				obaqw.getCurrencyID(),obaqw.getOfficeID()))+") \n");
		} else if (lAccountGroupTypeID == SETTConstant.AccountGroupType.DISCOUNT) {
			bufSQL.append("	AND b.nAccountTypeID IN ("+this.getTypeIdOfChange(
				SETTConstant.AccountType.getDiscountAccountTypeCode(
				obaqw.getCurrencyID(),obaqw.getOfficeID()))+") \n");
		} else if (lAccountGroupTypeID == SETTConstant.AccountGroupType.OTHERLOAN) {
			bufSQL.append("	AND b.nAccountTypeID IN ("+this.getTypeIdOfChange(
				SETTConstant.AccountType.getOtherDepositAccountTypeCode(
				obaqw.getCurrencyID(),obaqw.getOfficeID()))+") \n");
		} else if (lAccountGroupTypeID == SETTConstant.AccountGroupType.OFFBALANCE) {
			bufSQL.append("	AND b.nAccountTypeID IN ("+this.getTypeIdOfChange(
				SETTConstant.AccountType.getOffBalanceAccountTypeCode(
				obaqw.getCurrencyID(),obaqw.getOfficeID()))+") \n");
		} else if (lAccountGroupTypeID == SETTConstant.AccountGroupType.BANK) {
			bufSQL.append("	AND b.nAccountTypeID IN ("+this.getTypeIdOfChange(
				SETTConstant.AccountType.getBankAccountTypeCode(
				obaqw.getCurrencyID(),obaqw.getOfficeID()))+") \n");
		} else if (lAccountGroupTypeID == SETTConstant.AccountGroupType.MARGIN) {
			bufSQL.append("	AND b.nAccountTypeID IN ("+this.getTypeIdOfChange(
				SETTConstant.AccountType.getMarginAccountTypeCode(
				obaqw.getCurrencyID(),obaqw.getOfficeID()))+") \n");
		}else if (lAccountGroupTypeID == SETTConstant.AccountGroupType.YT) {
			bufSQL.append("	AND b.nAccountTypeID IN ("+this.getTypeIdOfChange(
					SETTConstant.AccountType.getYTAccountTypeCode(
					obaqw.getCurrencyID(),obaqw.getOfficeID()))+") \n");
			}
			//
		/*				
		} else if (lAccountGroupTypeID == SETTConstant.AccountGroupType.TRUST
				|| lAccountGroupTypeID == SETTConstant.AccountGroupType.CONSIGN
				|| lAccountGroupTypeID == SETTConstant.AccountGroupType.DISCOUNT
				|| lAccountGroupTypeID == SETTConstant.AccountGroupType.OTHERLOAN) {
			bufSQL.append("	AND b.nAccountTypeID IN ("+this.getTypeIdOfChange(
				SETTConstant.AccountType.getLoanAccountTypeCode(
				obaqw.getCurrencyID()))+") \n");
		} else {
			bufSQL.append("	AND b.nAccountTypeID IN ("+this.getTypeIdOfChange(
				SETTConstant.AccountType.getAllCode(
				obaqw.getCurrencyID()))+") \n");
				
		}*/
		else
		{
			;
		}
		
		return bufSQL;
	}

	/**
	 * 取得账户余额
	 * @param  OBAccountQueryWhere obaqw, long lAccountID, 
	 * @param  long lAccountGroupTypeID
	 * @return double
	 * @exception SQLException
	 */
	public double getBalanceByAccountID(OBAccountQueryWhere obaqw, long lAccountID, long lAccountGroupTypeID) throws SQLException {
		double mBalance = 0.0;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer bufSQL = new StringBuffer();
		try {
			bufSQL.append("	SELECT b.ID,SUM(NVL(mBalance,0.0)) mBalance \n");
			bufSQL.append("	FROM sett_DailyAccountBalance a,sett_Account b \n");
			bufSQL.append("	WHERE a.nAccountID = b.ID \n");
			//bufSQL.append("		AND b.nOfficeID = "+obaqw.getOfficeID()+" \n");
			bufSQL.append("		AND b.nCurrencyID = "+obaqw.getCurrencyID()+" \n");
			bufSQL.append("		AND b.nCheckStatusID = "+SETTConstant.AccountCheckStatus.CHECK+" \n");
			bufSQL.append("		AND b.ID = "+lAccountID+" \n");
			bufSQL.append(this.getStringForAccountGroupTypeID(obaqw, lAccountGroupTypeID).toString());
			bufSQL.append("		AND a.dtDate = TO_DATE('"+DataFormat.getDateString(
				DataFormat.getPreviousDate(obaqw.getDateTo()))+
				"','YYYY/MM/DD HH24:MI:SS') \n");
			bufSQL.append("	GROUP BY b.ID");
			log.info(bufSQL.toString());
			conn = super.getConnection();
			ps = conn.prepareStatement(bufSQL.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				mBalance = rs.getDouble("mBalance");
			}
		} catch (SQLException se) {
			se.printStackTrace();
			throw se;
		} finally {
			this.cleanup(rs);
			this.cleanup(ps);
		}
		return mBalance;
	}
	/**
	 * 取得账户交易额
	 * @param  OBAccountQueryWhere obaqw, long lAccountID, 
	 * @param  long lAccountGroupTypeID
	 * @return double
	 * @exception SQLException
	 */
	public double getAmountByAccountID(OBAccountQueryWhere obaqw, long lAccountID, long lAccountGroupTypeID) throws SQLException {
		double mAmount = 0.0;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer bufSQL = new StringBuffer();
		bufSQL.append("	SELECT ID,SUM(NVL(mAmount,0.0)) mAmount FROM \n");
		bufSQL.append("	    (SELECT b.ID, \n");
		bufSQL.append("			DECODE(a.nTransDirection,"+
			SETTConstant.DebitOrCredit.DEBIT+",-a.mAmount,"+
			SETTConstant.DebitOrCredit.CREDIT+",a.mAmount) mAmount \n");
		bufSQL.append("		FROM sett_TransAccountDetail a,sett_Account b \n");
		bufSQL.append("		WHERE a.nStatusID = "+
			SETTConstant.TransactionStatus.CHECK+" \n");
		bufSQL.append("			AND dtExecute = TO_DATE('"+
			DataFormat.getDateString(obaqw.getDateTo())+"','YYYY/MM/DD HH24:MI:SS') \n");
		bufSQL.append("		    AND a.nTransAccountID = b.ID \n");
		bufSQL.append("			AND b.ID = "+lAccountID+" \n");
		bufSQL.append(this.getStringForAccountGroupTypeID(obaqw, lAccountGroupTypeID).toString());
		bufSQL.append("			AND b.nOfficeID = "+obaqw.getOfficeID()+" \n");
		bufSQL.append("			AND b.nCurrencyID = "+obaqw.getCurrencyID()+" \n");
		bufSQL.append("			AND b.nCheckStatusID = "+SETTConstant.AccountCheckStatus.CHECK+") \n");
		bufSQL.append("		GROUP BY ID \n");
		
		log.info(bufSQL.toString());
		try {
			conn = super.getConnection();
			ps = conn.prepareStatement(bufSQL.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				mAmount = rs.getDouble("mAmount");
			}
		} catch (SQLException se) {
			se.printStackTrace();
			throw se;
		} finally {
			this.cleanup(rs);
			this.cleanup(ps);
		}
		return mAmount;
	}

	/**
	 *  通过母公司ID查询客户ID
	 * @param  OBAccountQueryWhere obaqw, long lParentCorpID, 
	 * @param  long lAccountGroupTypeID
	 * @return Collection
	 * @exception throws SQLException
	 */
	private Collection getClientIDByNparentCorpID(OBAccountQueryWhere obaqw, long lParentCorpID, long lAccountGroupTypeID) throws SQLException {
		ArrayList list = new ArrayList();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer bufSQL = new StringBuffer();
		//bufSQL.append("SELECT DISTINCT ID,sCode,sName,nAccountTypeID,nLoanClientTypeID FROM \n");
		bufSQL.append("SELECT DISTINCT ID,sCode,sName FROM \n");
		//bufSQL.append("(SELECT a.ID,a.sCode,a.sName,b.nAccountTypeID,a.nSettClientTypeID \n");
		bufSQL.append("(SELECT a.ID,a.sCode,a.sName,b.nAccountTypeID \n");
		bufSQL.append("FROM Client a,sett_Account b \n");
		bufSQL.append("WHERE a.ID = b.nClientID \n");
		bufSQL.append("	AND b.nCurrencyID = "+obaqw.getCurrencyID()+" \n");
		bufSQL.append("	AND b.nOfficeID = "+obaqw.getOfficeID()+" \n");
		bufSQL.append("	AND b.nCheckStatusID = "+SETTConstant.AccountCheckStatus.CHECK+" \n");
		bufSQL.append("	AND a.nParentCorpID1 = "+lParentCorpID+" \n");
		bufSQL.append(this.getStringForAccountGroupTypeID(obaqw, lAccountGroupTypeID).toString());
		bufSQL.append("	AND a.ID NOT IN (SELECT nParentCorpID1 FROM Client \n");
		bufSQL.append("		WHERE nParentCorpID1 NOT IN (0,-1)) \n");
		//bufSQL.append("ORDER BY b.nAccountTypeID,a.nSettClientTypeID) \n");
		bufSQL.append("ORDER BY b.nAccountTypeID) \n");
		log.info(bufSQL.toString());
		try {
			conn = super.getConnection();
			ps = conn.prepareStatement(bufSQL.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				OBAccountQueryAmountInfo info = new OBAccountQueryAmountInfo();
				info.setClientID(rs.getLong("ID"));
				info.setClientCode(rs.getString("sCode"));
				info.setClientName(rs.getString("sName"));
				//info.setAccountTypeID(rs.getLong("nAccountTypeID"));
				//info.setLoanClientTypeID(rs.getLong("nLoanClientTypeID"));
				list.add(info);
			}
		} catch (SQLException se) {
			se.printStackTrace();
			throw se;
		} finally {
			this.cleanup(rs);
			this.cleanup(ps);
		}
		return list.size() > 0?list:null;
	}
	
	
	/**
	 *  获得子公司
	 * @param  OBAccountQueryWhere obaqw, long lParentCorpID, 
	 * @param  long lAccountGroupTypeID
	 * @return Collection
	 * @exception throws SQLException
	 */
	public Collection getChildClientID(OBAccountQueryWhere obaqw, long lParentCorpID, long lAccountGroupTypeID) throws SQLException {
		ArrayList list = new ArrayList();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null; 
		StringBuffer bufSQL = new StringBuffer(); 
		//bufSQL.append("SELECT DISTINCT ID,sCode,sName,nAccountTypeID,nLoanClientTypeID FROM \n");
		bufSQL.append("SELECT DISTINCT ID,sCode,sName FROM \n");
		bufSQL.append("(SELECT a.ID,a.sCode,a.sName,b.nAccountTypeID \n");
 		bufSQL.append("FROM Client a,sett_Account b\n");
		//bufSQL.append("FROM Client a,sett_Account b,childClientPrivilege c \n");
		bufSQL.append("WHERE a.ID = b.nClientID \n");
		//bufSQL.append("	AND c.ChildClientID = a.id \n");
		bufSQL.append("	AND b.nCurrencyID = "+obaqw.getCurrencyID()+" \n");
		bufSQL.append("	AND b.nOfficeID = "+obaqw.getOfficeID()+" \n");
		bufSQL.append("	AND b.nCheckStatusID = "+SETTConstant.AccountCheckStatus.CHECK+" \n");
		//bufSQL.append("	AND c.clientID = "+lParentCorpID+" \n");
		//bufSQL.append("	AND decode(c.privilegeType,1,c.privilegeValue,-1)=1  \n");
		
		/**中交   王锐修改试验！*/
		 bufSQL.append("	AND (a.nParentCorpID1 = (select nParentCorpID1 from Client where id = "
				+ lParentCorpID
				+ ") OR a.nParentCorpID2 = (select nParentCorpID2 from Client where id = "
				+ lParentCorpID+")) \n");
		//bufSQL.append("	AND (a.nParentCorpID1 = (select nParentCorpID1 from Client where id ="+lParentCorpID+" \n");
		bufSQL.append(this.getStringForAccountGroupTypeID(obaqw, lAccountGroupTypeID).toString());
//		bufSQL.append("	AND a.ID NOT IN (SELECT nParentCorpID1 FROM Client \n");
//		bufSQL.append("		WHERE nParentCorpID1 NOT IN (0,-1)) \n");
//		bufSQL.append("	AND a.ID NOT IN (SELECT nParentCorpID2 FROM Client \n");
//		bufSQL.append("		WHERE nParentCorpID2 NOT IN (0,-1)) \n");
		bufSQL.append("ORDER BY b.nAccountTypeID) \n");
		
		//bufSQL.append("select * from client where nlevelCode like '10117%'");
		log.info("getChildClientID sql="+bufSQL.toString());
		try {
			conn = super.getConnection();
			ps = conn.prepareStatement(bufSQL.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				OBAccountQueryAmountInfo info = new OBAccountQueryAmountInfo();
				info.setClientID(rs.getLong("ID"));
				info.setClientCode(rs.getString("sCode"));
				info.setClientName(rs.getString("sName"));
				//info.setAccountTypeID(rs.getLong("nAccountTypeID"));
				//info.setLoanClientTypeID(rs.getLong("nLoanClientTypeID"));
				list.add(info);
			}
		} catch (SQLException se) {
			se.printStackTrace();
			throw se;
		} finally {
			this.cleanup(rs);
			this.cleanup(ps);
		}
		return list.size() > 0?list:null;
	}
	//modify by zcwang 2007-3-20
	//中交修改新增
	public Collection getChildClientID(OBAccountQueryWhere obaqw,long lParentCorpID,long lOfficeid,boolean child) throws SQLException {
		ArrayList list = new ArrayList();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null; 
		StringBuffer bufSQL = new StringBuffer(); 
		
		bufSQL.append("select distinct ID,sCode,sName from client where nlevelCode like (select t.nlevelcode||'_%' from  client t where id="+lParentCorpID+") ");
		if(Config.getBoolean(ConfigConstant.EBANK_ISQUERYCLIENT,false))
		{
			bufSQL.append("or id="+lParentCorpID);
		}
		
		log.info("getChildClientID sql="+bufSQL.toString());
		try {
			conn = super.getConnection();
			ps = conn.prepareStatement(bufSQL.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				OBAccountQueryAmountInfo info = new OBAccountQueryAmountInfo();
				info.setClientID(rs.getLong("ID"));
				info.setClientCode(rs.getString("sCode"));
				info.setClientName(rs.getString("sName"));
				//info.setAccountTypeID(rs.getLong("nAccountTypeID"));
				//info.setLoanClientTypeID(rs.getLong("nLoanClientTypeID"));
				list.add(info);
			}
		} catch (SQLException se) {
			se.printStackTrace();
			throw se;
		} finally {
			this.cleanup(rs);
			this.cleanup(ps);
		}
		return list.size() > 0?list:null;
	}
	//
	/**
	 *  通过母公司ID查询客户ID、判断客户ID是否是母公司ID
	 * @param  OBAccountQueryWhere obaqw, long lParentCorpID, 
	 * @param  long lAccountGroupTypeID
	 * @return Collection
	 * @exception throws SQLException
	 */
	private Collection getClientIDIsNparentCorpID(OBAccountQueryWhere obaqw, long lParentCorpID, long lAccountGroupTypeID) throws SQLException {
		ArrayList list = new ArrayList();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer bufSQL = new StringBuffer();
		//bufSQL.append("SELECT DISTINCT ID,sCode,sName,nAccountTypeID,nLoanClientTypeID FROM \n");
		bufSQL.append("SELECT DISTINCT ID,sCode,sName FROM \n");
		//bufSQL.append("(SELECT a.ID,a.sCode,a.sName,b.nAccountTypeID,a.nSettClientTypeID \n");
		bufSQL.append("(SELECT a.ID,a.sCode,a.sName,b.nAccountTypeID \n");
		bufSQL.append("FROM Client a,sett_Account b \n");
		bufSQL.append("WHERE a.ID = b.nClientID \n");
		bufSQL.append("	AND b.nCurrencyID = "+obaqw.getCurrencyID()+" \n");
		bufSQL.append("	AND b.nOfficeID = "+obaqw.getOfficeID()+" \n");
		bufSQL.append("	AND b.nCheckStatusID = "+SETTConstant.AccountCheckStatus.CHECK+" \n");
		bufSQL.append("	AND a.nParentCorpID1 = "+lParentCorpID+" \n");
		bufSQL.append(this.getStringForAccountGroupTypeID(obaqw, lAccountGroupTypeID).toString());
		bufSQL.append("	AND a.ID IN (SELECT nParentCorpID1 FROM Client \n");
		bufSQL.append("		WHERE nParentCorpID1 NOT IN (0,-1)) \n");
		//bufSQL.append("ORDER BY b.nAccountTypeID,a.nSettClientTypeID) \n");
		bufSQL.append("ORDER BY b.nAccountTypeID) \n");
		log.info(bufSQL.toString());
		try {
			conn = super.getConnection();
			ps = conn.prepareStatement(bufSQL.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				OBAccountQueryAmountInfo info = new OBAccountQueryAmountInfo();
				info.setClientID(rs.getLong("ID"));
				info.setClientCode(rs.getString("sCode"));
				info.setClientName(rs.getString("sName"));
				//info.setAccountTypeID(rs.getLong("nAccountTypeID"));
				//info.setLoanClientTypeID(rs.getLong("nLoanClientTypeID"));
				list.add(info);
			}
		} catch (SQLException se) {
			se.printStackTrace();
			throw se;
		} finally {
			this.cleanup(rs);
			this.cleanup(ps);
		}
		return list.size() > 0?list:null;
	}

	/**
	 *  通过母公司ID查询客户ID、判断客户ID是否是母公司ID
	 * @param  OBAccountQueryWhere obaqw, long lParentCorpID, 
	 * @param  Collection list, long lAccountGroupTypeID
	 * @return Collection
	 * @exception throws SQLException
	 */
	private Collection getSubClientID(Collection list, OBAccountQueryWhere obaqw, long lParentCorpID, long lAccountGroupTypeID) throws Exception {
		Collection subList = this.getClientIDByNparentCorpID(obaqw, lParentCorpID, lAccountGroupTypeID);
		
		Iterator iterSubList = null;
		if (subList != null) {
			iterSubList = subList.iterator();
			while (iterSubList != null && iterSubList.hasNext()) {
				OBAccountQueryAmountInfo info = (OBAccountQueryAmountInfo) iterSubList.next();
				list.add(info);
			}
		}
		Collection collection = this.getClientIDIsNparentCorpID(obaqw, lParentCorpID, lAccountGroupTypeID);
		Iterator iterList = null;
		if (collection != null) {
			iterList = collection.iterator();
		}
		while (iterList != null && iterList.hasNext()) {
			OBAccountQueryAmountInfo info = (OBAccountQueryAmountInfo) iterList.next();
			list.add(info);
			list = this.getSubClientID(list, obaqw, info.getClientID(), lAccountGroupTypeID);
		}
		
		//return list.size() > 0?list:null;
		return list;
	}

	/**
	 *  通过客户ID查询账户信息
	 * @param  OBAccountQueryWhere obaqw, long lAccountGroupTypeID, 
	 * @param  long lClientID
	 * @return Collection
	 * @exception throws SQLException
	 */
	public Collection getAccountInfoByClientID(OBAccountQueryWhere obaqw, long lAccountGroupTypeID, long lClientID) throws Exception {
		ArrayList list = new ArrayList();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer bufSQL = new StringBuffer();
		
		try {
			
			bufSQL.append("SELECT DISTINCT b.ID,");
			//a.nSettClientTypeID,c.sName sSettName,
			bufSQL.append("b.sAccountNo,b.sName,b.nAccountTypeID \n");
			bufSQL.append("FROM Client a,sett_Account b \n");
			bufSQL.append("WHERE a.ID = b.nClientID \n");
			//bufSQL.append("	AND a.nSettClientTypeID = c.ID \n");
			bufSQL.append("	AND b.nCurrencyID = "+obaqw.getCurrencyID()+" \n");
			bufSQL.append("	AND b.nOfficeID = "+obaqw.getOfficeID()+" \n");
			bufSQL.append("	AND b.nCheckStatusID = "+SETTConstant.AccountCheckStatus.CHECK+" \n");
			bufSQL.append("	AND b.nClientID = "+lClientID+" \n");
			//中交增加 账户授权功能！
			//bufSQL.append("AND b.id IN(select accountid from Ob_acctprvgbyclient where clientID="+lParentCorpID+")");
			bufSQL.append(this.getStringForAccountGroupTypeID(obaqw, lAccountGroupTypeID).toString());
			
			//后增加
			bufSQL.append("	order by b.nAccountTypeID \n");
			log.info(bufSQL.toString());
			
			conn = super.getConnection();
			ps = conn.prepareStatement(bufSQL.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				OBAccountQueryAmountInfo info = new OBAccountQueryAmountInfo();
				info.setOpenAccountID(rs.getLong("ID"));
				info.setAccountTypeID(rs.getLong("nAccountTypeID"));
				info.setOpenAccountNo(rs.getString("sAccountNo"));
				info.setOpenAccountName(rs.getString("sName"));
				//info.setLoanClientTypeID(rs.getLong("nSettClientTypeID"));
				//info.setLoanClientTypeName(rs.getString("sSettName"));
				info.setClientID(lClientID);
				
				
				list.add(info);
			}
		} catch (SQLException se) {
			se.printStackTrace();
			throw se;
		}
		catch(Exception e)
		{
			System.out.println("*************e="+e.toString());
		}
		finally {
			this.cleanup(rs);
			this.cleanup(ps);
		}
		return list.size() > 0?list:null;
	}
	
	//------------------------中交增加,账户查询授权--------------------------------
	public Collection getAccountInfoByClientID(OBAccountQueryWhere obaqw, long lAccountGroupTypeID, long lClientID,long lParentCorpID,boolean self) throws Exception {
		ArrayList list = new ArrayList();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer bufSQL = new StringBuffer();
		try {
			
			bufSQL.append("SELECT DISTINCT b.ID,");
			//a.nSettClientTypeID,c.sName sSettName,
			bufSQL.append("b.sAccountNo,b.sName,b.nAccountTypeID,b.nClientID \n");
			bufSQL.append("FROM Client a,sett_Account b \n");
			bufSQL.append("WHERE a.ID = b.nClientID \n");
			//bufSQL.append("	AND a.nSettClientTypeID = c.ID \n");
			bufSQL.append("	AND b.nCurrencyID = "+obaqw.getCurrencyID()+" \n");
			//bufSQL.append("	AND b.nOfficeID = "+obaqw.getOfficeID()+" \n");
			bufSQL.append("	AND b.nCheckStatusID = "+SETTConstant.AccountCheckStatus.CHECK+" \n");
			//bufSQL.append("	AND b.nClientID = "+lClientID+" \n");
			//中交增加 账户授权功能！
			//modify by zcwang 2007-3-20 判断是否是本单位的帐户，如果是就把所有本单位的帐户全部查处，否则只查找授权的下属单位帐户
//			if(lParentCorpID != lClientID)
//			{
//				bufSQL.append(" and ");
//			}else{
//				bufSQL.append(" or ");
//			}
			bufSQL.append("and b.id IN(select accountid from Ob_acctprvgbyclient where clientID="+lParentCorpID+") ");
			bufSQL.append(this.getStringForAccountGroupTypeID(obaqw, lAccountGroupTypeID).toString());
			
			//后增加
			bufSQL.append("	order by b.nClientID,b.nAccountTypeID \n");
			log.info(bufSQL.toString());
			
			conn = super.getConnection();
			ps = conn.prepareStatement(bufSQL.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				OBAccountQueryAmountInfo info = new OBAccountQueryAmountInfo();
				info.setOpenAccountID(rs.getLong("ID"));
				info.setAccountTypeID(rs.getLong("nAccountTypeID"));
				info.setOpenAccountNo(rs.getString("sAccountNo"));
				info.setOpenAccountName(rs.getString("sName"));
				//info.setLoanClientTypeID(rs.getLong("nSettClientTypeID"));
				//info.setLoanClientTypeName(rs.getString("sSettName"));
				info.setClientID(lClientID);
				list.add(info);
			}
		} catch (SQLException se) {
			se.printStackTrace();
			throw se;
		}
		catch(Exception e)
		{
			System.out.println("*************e="+e.toString());
		}
		finally {
			this.cleanup(rs);
			this.cleanup(ps);
		}
		return list.size() > 0?list:null;
	}
	/**
	 *  通过账户ID查询账户信息
	 * @param  OBAccountQueryWhere obaqw, long lAccountGroupTypeID, 
	 * @param  long lClientID
	 * @return Collection
	 * @exception throws SQLException
	 */
	public Collection getAccountInfoByAccountID(OBAccountQueryWhere obaqw, long lAccountGroupTypeID, long lAccountID) throws Exception {
		ArrayList list = new ArrayList();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer bufSQL = new StringBuffer();
		bufSQL.append("SELECT DISTINCT b.ID, "); 
				//a.nSettClientTypeID,c.sName sSettName,
		bufSQL.append("b.sAccountNo,b.sName,b.nAccountTypeID \n");
		//bufSQL.append("FROM Client a,sett_Account b,sett_ClientType c \n");
		bufSQL.append("FROM Client a,sett_Account b \n");
		bufSQL.append("WHERE a.ID = b.nClientID \n");
		//bufSQL.append("	AND a.nSettClientTypeID = c.ID \n");
		bufSQL.append("	AND b.nCurrencyID = "+obaqw.getCurrencyID()+" \n");
		//bufSQL.append("	AND b.nOfficeID = "+obaqw.getOfficeID()+" \n");
		bufSQL.append("	AND b.nCheckStatusID = "+SETTConstant.AccountCheckStatus.CHECK+" \n");
		bufSQL.append("	AND b.ID = "+lAccountID+" \n");
		bufSQL.append(this.getStringForAccountGroupTypeID(obaqw, lAccountGroupTypeID).toString());
		//后增加
		bufSQL.append("	order by b.nAccountTypeID \n");
		log.info(bufSQL.toString());
		try {
			conn = super.getConnection();
			ps = conn.prepareStatement(bufSQL.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				OBAccountQueryAmountInfo info = new OBAccountQueryAmountInfo();
				info.setOpenAccountID(rs.getLong("ID"));
				info.setAccountTypeID(rs.getLong("nAccountTypeID"));
				info.setOpenAccountNo(rs.getString("sAccountNo"));
				info.setOpenAccountName(rs.getString("sName"));
				//info.setLoanClientTypeID(rs.getLong("nSettClientTypeID"));
				//info.setLoanClientTypeName(rs.getString("sSettName"));
				list.add(info);
			}
		} catch (SQLException se) {
			se.printStackTrace();
			throw se;
		} finally {
			this.cleanup(rs);
			this.cleanup(ps);
		}
		return list.size() > 0?list:null;
	}

	/**
	 *  通过自营贷款客户分类排列账户ID
	 * @param  Collection list
	 * @return Collection
	 * @exception nothing
	 */
	public Collection compositByLoanClientTypeID(Collection collection) {
		ArrayList list = new ArrayList();
		ArrayList arrayList = (ArrayList) collection;
		Iterator iterList = null;
		List listTemp = arrayList.subList(0, arrayList.size());
		if (listTemp != null) {
			iterList = listTemp.iterator();
		}
		while (iterList != null && iterList.hasNext()) {
			int iIndex = -1;
			OBAccountQueryAmountInfo info1 = (OBAccountQueryAmountInfo)iterList.next();
			list.add(info1);
			long lAccountTypeID1 = info1.getAccountTypeID();
			long lLoanClientTypeID1 = info1.getLoanClientTypeID();
			while (iterList != null && iterList.hasNext()) {
				OBAccountQueryAmountInfo info2 = (OBAccountQueryAmountInfo)iterList.next();
				long lAccountTypeID2 = info2.getAccountTypeID();
				long lLoanClientTypeID2 = info2.getLoanClientTypeID();
				if (lAccountTypeID1 == lAccountTypeID2) {
					if (lLoanClientTypeID1 == lLoanClientTypeID2) {
						list.add(info2);
					} else {
						listTemp.set(++iIndex, info2);
					}
				} else {
					listTemp.set(++iIndex, info2);
				}
			}
			if (iIndex >= 0) {
				listTemp = listTemp.subList(0, iIndex+1);
				iterList = listTemp.iterator();
			}
		}

		return list.size() > 0?list:null;
	}
   
	/**
	 *  查询账户类型值最小的账户ID
	 * @param  Collection list
	 * @return OBAccountQueryAmountInfo
	 * @exception nothing
	 */
	private OBAccountQueryAmountInfo getMinAccountTypeInfo(Collection collection) {
		OBAccountQueryAmountInfo infoRtn = new OBAccountQueryAmountInfo();
		List arrayList = (List) collection;
		Iterator iterList = null;
		List listTemp = arrayList.subList(0, arrayList.size());
		if (listTemp != null) {
			iterList = listTemp.iterator();	
			int iIndex = -1;
			int indexRtn = 0;		
			OBAccountQueryAmountInfo info1 = (OBAccountQueryAmountInfo)listTemp.get(0);				
			long lAccountTypeID1 = info1.getAccountTypeID();
			while (iterList != null && iterList.hasNext()) {
				iIndex = iIndex+1;
				OBAccountQueryAmountInfo info2 = (OBAccountQueryAmountInfo)iterList.next();
				long lAccountTypeID2 = info2.getAccountTypeID();
				if (lAccountTypeID1 > lAccountTypeID2) {
					indexRtn = iIndex;
					lAccountTypeID1 = lAccountTypeID2;
				}
			}
			infoRtn=(OBAccountQueryAmountInfo)listTemp.get(indexRtn);
		}
		return infoRtn;				
	}

	/**
	 *  通过账户类型排列账户ID
	 * @param  Collection list
	 * @return Collection
	 * @exception nothing
	 */
	public Collection compositByAccountTypeID(Collection collection) {
		ArrayList list = new ArrayList();
		ArrayList arrayList = (ArrayList) collection;		
		Iterator iterList = null;
		List listTemp = arrayList.subList(0, arrayList.size());
		if (listTemp != null) {
			iterList = listTemp.iterator();
		}
		while (iterList != null && iterList.hasNext()) {
			int iIndex = -1;
			//OBAccountQueryAmountInfo info1 = (OBAccountQueryAmountInfo)iterList.next();
			OBAccountQueryAmountInfo info1 = this.getMinAccountTypeInfo(listTemp);			
			//list.add(info1);
			long lAccountTypeID1 = info1.getAccountTypeID();
			while (iterList != null && iterList.hasNext()) {
				OBAccountQueryAmountInfo info2 = (OBAccountQueryAmountInfo)iterList.next();
				long lAccountTypeID2 = info2.getAccountTypeID();
				if (lAccountTypeID1 == lAccountTypeID2) {
					list.add(info2);
				} else {
					listTemp.set(++iIndex, info2);
				}
			}
			if (iIndex >= 0) {
				listTemp = listTemp.subList(0, iIndex+1);
				iterList = listTemp.iterator();
			}
		}

		//return list.size() > 0?list:null;
		return list;
	}

	/**
	 *  通过母公司ID查询客户ID、判断客户ID是否是母公司ID
	 * @param  OBAccountQueryWhere obaqw, long lParentCorpID, 
	 * @param  long lAccountGroupTypeID
	 * @return Collection
	 * @exception throws SQLException
	 */
	public Collection getClientID(OBAccountQueryWhere obaqw, long lParentCorpID, long lAccountGroupTypeID) throws Exception {
		Collection list = new ArrayList();
		list = this.getSubClientID(list, obaqw, lParentCorpID, lAccountGroupTypeID);

		return list.size() > 0?list:null;
	}

	/**
	 * 按客户编号排序
	 * @param out
	 * @param strFileName
	 * @param obaqw
	 * @param lParentCorpID
	 * @param lAccountGroupTypeID
	 * @param lClientID
	 * @throws Exception
	 */
	private Collection doSortClientCode(Collection coll) {
		if (coll == null)
		{
			return null;
		}
		ArrayList list = new ArrayList();
		ArrayList arrayList = (ArrayList) coll;		
		Iterator iterList = null;
		List listTemp = arrayList.subList(0, arrayList.size());		
		if (listTemp != null) {
			iterList = listTemp.iterator();
		}
		while (iterList != null && iterList.hasNext()) {
			int iIndex = 0;
			int indexTemp=0;
			OBAccountQueryAmountInfo info1 = (OBAccountQueryAmountInfo)iterList.next();			
			String strClientNo1 = info1.getClientCode();
			while (iterList != null && iterList.hasNext()) {
				iIndex=iIndex+1;
				OBAccountQueryAmountInfo info2 = (OBAccountQueryAmountInfo)iterList.next();
				String strClientNo2 = info2.getClientCode();
				if (strClientNo1.compareTo(strClientNo2) > 0) {
					strClientNo1 = strClientNo2;
					indexTemp = iIndex;
				}
			}
			list.add(listTemp.get(indexTemp));
			listTemp.remove(indexTemp);
			if (iIndex >= 0) {
				iterList = listTemp.iterator();
			}
		}
		return list.size() > 0?list:null;
	}

	/**
	 * 按客户编号排序
	 * @param out
	 * @param strFileName
	 * @param obaqw
	 * @param lParentCorpID
	 * @param lAccountGroupTypeID
	 * @param lClientID
	 * @throws Exception
	 */
	private Collection doSortAccountCode(Collection coll) {
		ArrayList list = new ArrayList();
		ArrayList arrayList = (ArrayList) coll;		
		Iterator iterList = null;
		List listTemp = arrayList.subList(0, arrayList.size());		
		if (listTemp != null) {
			iterList = listTemp.iterator();
		}
		while (iterList != null && iterList.hasNext()) {
			int iIndex = 0;
			int indexTemp=0;
			OBAccountQueryAmountInfo info1 = (OBAccountQueryAmountInfo)iterList.next();			
			String strAccountNo1 = info1.getOpenAccountNo();
			while (iterList != null && iterList.hasNext()) {
				iIndex=iIndex+1;
				OBAccountQueryAmountInfo info2 = (OBAccountQueryAmountInfo)iterList.next();
				String strAccountNo2 = info2.getOpenAccountNo();
				if (strAccountNo1.compareTo(strAccountNo2)>0) {
					strAccountNo1 = strAccountNo2;
					indexTemp = iIndex;
				}
			}
			list.add(listTemp.get(indexTemp));
			listTemp.remove(indexTemp);
			if (iIndex >= 0) {
				iterList = listTemp.iterator();
			}
		}
		return list.size() > 0?list:null;
	}

	/**
	 *  下拉框控件(选择下属单位)
	 * @param  JspWriter out, OBAccountQueryWhere obaqw, long lParentCorpID, 
	 * @param  long lAccountGroupTypeID, long lClientID
	 * @return Collection
	 * @exception throws Exception
	 */
	public void showClientListControl(
		JspWriter out, 
		String strFileName, 
		OBAccountQueryWhere obaqw, 
		long lParentCorpID, 
		long lAccountGroupTypeID, 
		long lClientID,
		long lOfficeid,
		boolean child) 
		throws Exception {
		//clientList = this.getChildClientID(obaqw, lParentCorpID, lAccountGroupTypeID);
		//中交修改
		clientList = this.getChildClientID(obaqw,lParentCorpID,lOfficeid,child);
		//后增加排序
		clientList = this.doSortClientCode(clientList); 
		Iterator iterList = null;
		if (clientList != null) {
			iterList = clientList.iterator(); 
		}
		out.println("<select class='select' name=\"" + strFileName + "\"   onChange='slctChange();' >");
		out.println("<option value=\"-1\">全部</option>");
		while (iterList != null && iterList.hasNext()) {
			OBAccountQueryAmountInfo obaqai = (OBAccountQueryAmountInfo)iterList.next();

				long lSupplierID = obaqai.getClientID();
				String strSelected = "";
				if (lSupplierID == lClientID) {
					strSelected = " selected";
				}
				out.println(
					"<option value=\""
						+ obaqai.getClientID()
						+ "\""
						+ strSelected
						+ ">"
						+ obaqai.getClientCode()
						+ "("
						+ obaqai.getClientName()
						+ ")"
						+ "</option>");
				strSelected = "";
			
		}
		out.println("</select>");
	}

	/**
	 *  下拉框控件(选择下属单位账号)
	 * @param  JspWriter out, String strFileName, 
	 * @param  Collection list, long lAccountID
	 * @return Collection
	 * @exception throws Exception
	 */
	public void showAccountListControl(
		JspWriter out, 
		String strFileName, 
		Collection list,
		long lAccountID) 
		throws Exception {
		Iterator iterList = null;
		
		if (list != null)
		{
			iterList = list.iterator();
		}
		
		Sett_FilialeAccountSetDAO settDao = new Sett_FilialeAccountSetDAO ();
		
		String sTemp = "";
		while (iterList != null && iterList.hasNext()) {
			OBAccountQueryAmountInfo obaqai = (OBAccountQueryAmountInfo)iterList.next();
			long lSupplierID = obaqai.getOpenAccountID();
			String strSelected = "";
			if (lSupplierID == lAccountID) {
				strSelected = " selected";
			}
			
			FilialeAccountInfo[] info = settDao.findRefFilialeAccountInfoBySettAccountId(obaqai.getOpenAccountID());
			sTemp = "";
			if (info != null)
			{
				if (info[0] != null)
				{
					if (info[0].getBankAccountNo()!=null && info[0].getBankAccountNo().length()>0)
					{
						if (SETTConstant.AccountType.isCurrentAccountType(info[0].getAccountType())
						        ||SETTConstant.AccountType.isOtherDepositAccountType(info[0].getAccountType()))
						{
							sTemp = " -- "+info[0].getBankAccountNo();
						}
					}
				}
			}
				String StrTempBankNo = "";
				StrTempBankNo = NameRef.getBankAccountNOByCurrenctAccountID(obaqai.getOpenAccountNo());
				if(StrTempBankNo!=null && StrTempBankNo.length()>0)
				{
					StrTempBankNo = obaqai.getOpenAccountNo() + " -- " + StrTempBankNo;
					obaqai.setOpenAccountNo(StrTempBankNo);
				}								

			out.println(
				"<option value=\""
					+ obaqai.getOpenAccountID()
					+ "\""
					+ strSelected
					+ ">"
					+ obaqai.getOpenAccountNo()
					+sTemp
					+ "("
					+ obaqai.getOpenAccountName()
					+ ")"
					+ "</option>");
			strSelected = "";
		}
	}

	/**
	 *  下拉框控件(选择下属单位账号)
	 * @param  JspWriter out, String strFileName, OBAccountQueryWhere obaqw,
	 * @param  long lAccountGroupID,
	 * @param  Collection list, long lClientID, long lAccountID
	 * @return Collection
	 * @exception throws Exception
	 */
	public void showAccountListControl(
		JspWriter out, 
		String strFileName, 
		OBAccountQueryWhere obaqw,
		long lAccountGroupTypeID, 
		long lClientID, 
		long lAccountID) 
		throws Exception {
		ArrayList listAccount = new ArrayList();
		Iterator iterList = null;
		Iterator iterAccount = null;
		out.println("<select class='select' name=\"" + strFileName + "\" >");
		out.println("<option value=\"-1\">全部</option>");
		if (lClientID == -1) {
			if (clientList != null) {
				iterList = clientList.iterator();
			}
			while (iterList != null && iterList.hasNext()) {
				OBAccountQueryAmountInfo obaqai = (OBAccountQueryAmountInfo)iterList.next();
				
				Collection accountList = this.getAccountInfoByClientID(obaqw, lAccountGroupTypeID, obaqai.getClientID());
				if (accountList != null) {
					//this.showAccountListControl(out, strFileName, accountList, lAccountID);
					
					listAccount.addAll(accountList);
				}
			}
		} else {
			Collection accountList = this.getAccountInfoByClientID(obaqw, lAccountGroupTypeID, lClientID);
			if (accountList != null) {
				//this.showAccountListControl(out, strFileName, accountList, lAccountID);
				listAccount.addAll(accountList);
			}
		}
		listAccount = (ArrayList) this.doSortAccountCode(listAccount);
		this.showAccountListControl(out, strFileName, listAccount, lAccountID);
		out.println("</select>");
	}
	
//---------------------------- 中交修改该方法，增加账户授权-----------------------------
	public void showAccountListControl(
			JspWriter out, 
			String strFileName, 
			OBAccountQueryWhere obaqw,
			long lAccountGroupTypeID, 
			long lClientID, 
			long lAccountID,
			long lParentCorpID,
			long sessionClientid) 
			throws Exception {
			ArrayList listAccount = new ArrayList();
			Iterator iterList = null;
			Iterator iterAccount = null;
			out.println("<select class='select' name=\"" + strFileName + "\" >");
			out.println("<option value=\"-1\">全部</option>");
			if (lClientID == -1) {
				if (clientList != null) {
					iterList = clientList.iterator();
				}
				boolean self=false;  //modify by zcwang 2007-3-20判断是否是本单位的帐户，如果是就把所有本单位的帐户全部查处，否则只查找授权的下属单位帐户
				while (iterList != null && iterList.hasNext()) {
					OBAccountQueryAmountInfo obaqai = (OBAccountQueryAmountInfo)iterList.next();
					if(sessionClientid==obaqai.getClientID()) self=true;
					Collection accountList = this.getAccountInfoByClientID(obaqw, lAccountGroupTypeID, sessionClientid,lParentCorpID,self);
					if (accountList != null) {
						//this.showAccountListControl(out, strFileName, accountList, lAccountID);
						
						listAccount.addAll(accountList);
					}
					self=false;
				}
			} else {
				boolean self=false; //modify by zcwang 2007-3-20判断是否是本单位的帐户，如果是就把所有本单位的帐户全部查处，否则只查找授权的下属单位帐户
				if(sessionClientid==lClientID) self=true;
				Collection accountList = this.getAccountInfoByClientID(obaqw, lAccountGroupTypeID, lClientID,lParentCorpID,self);
				if (accountList != null) {
					//this.showAccountListControl(out, strFileName, accountList, lAccountID);
					listAccount.addAll(accountList);
				}
				self=false;
			}
			listAccount = (ArrayList) this.doSortAccountCode(listAccount);
			this.showAccountListControl(out, strFileName, listAccount, lAccountID);
			out.println("</select>");
		}
	
//===============================================================
	/**
	 *  下拉框控件(选择下属单位账号)
	 * @param  JspWriter out, String strFileName, 
	 * @param  Collection list, long lAccountID
	 * @return Collection
	 * @exception throws Exception
	 */
	public void showHisAccountListControl(
		JspWriter out, 
		String strFileName, 
		Collection list,
		long lAccountID) 
		throws Exception {
		Iterator iterList = null;
		
		if (list != null)
		{
			iterList = list.iterator();
		}
		
		Sett_FilialeAccountSetDAO settDao = new Sett_FilialeAccountSetDAO ();
		
		String sTemp = "";
		while (iterList != null && iterList.hasNext()) {
			OBAccountQueryAmountInfo obaqai = (OBAccountQueryAmountInfo)iterList.next();
			long lSupplierID = obaqai.getOpenAccountID();
			String strSelected = "";
			log.print("<<<<<<<<<<>>>>>>>>>>>>>>>>lSupplierID" + lSupplierID);
			log.print("<<<<<<<<<<s>>>>>>>>>>>>>>>>lAccountID" + lAccountID);
			if (lSupplierID == lAccountID) {
				log.print("<<<<<<<<<<>>>>>>>>>>>>>>>>lAccountID" + lAccountID);
				strSelected = " selected";
			}
			
			FilialeAccountInfo[] info = settDao.findRefFilialeAccountInfoBySettAccountId(obaqai.getOpenAccountID());
			sTemp = "";
			if (info != null)
			{
				if (info[0] != null)
				{
					if (info[0].getBankAccountNo()!=null && info[0].getBankAccountNo().length()>0)
					{
						if (SETTConstant.AccountType.isCurrentAccountType(info[0].getAccountType())
						        ||SETTConstant.AccountType.isOtherDepositAccountType(info[0].getAccountType()))
						{
							sTemp = " -- "+info[0].getBankAccountNo();
						}
					}
				}
			}
				String StrTempBankNo = "";
				StrTempBankNo = NameRef.getBankAccountNOByCurrenctAccountID(obaqai.getOpenAccountNo());
				if(StrTempBankNo!=null && StrTempBankNo.length()>0)
				{
					StrTempBankNo = obaqai.getOpenAccountNo() + " -- " + StrTempBankNo;
					obaqai.setOpenAccountNo(StrTempBankNo);
				}								
		 
			AccountTypeInfo accountTypeInfo = new AccountTypeInfo();
			accountTypeInfo = SETTConstant.AccountType.getAccountTypeInfoByAccountTypeID(obaqai.getAccountTypeID());
			out.println(
					"<option value=\""
					+ obaqai.getOpenAccountID()
					+ ";"
 					+ accountTypeInfo.getAccountGroupID() 
					+ "@"
					+ obaqai.getAccountTypeID()
					+ "$"
					+ obaqai.getClientID()
					+ "\""
					+ strSelected
					+ ">"
					+ accountTypeInfo.getAccountType() 
					+ " -- "
					+ obaqai.getOpenAccountNo()
					+sTemp
					+ "</option>");
			strSelected = "";
		}
	}
	/**
	 *  下拉框控件(选择下属单位账号)
	 * @param  JspWriter out, String strFileName, OBAccountQueryWhere obaqw,
	 * @param  long lAccountGroupID,
	 * @param  Collection list, long lClientID, long lAccountID
	 * @return Collection
	 * @exception throws Exception
	 */
	public void showAccountListControl(
		JspWriter out, 
		String strFileName, 
		OBAccountQueryWhere obaqw,
		long lAccountGroupTypeID, 
		long lParentClentID,
		long lClientID, 
		long lAccountID,
		String strJS) 
		throws Exception {
		ArrayList listAccount = new ArrayList();
		Iterator iterList = null; 
		out.println("<select class='select' name=\"" + strFileName + "\" " + strJS + ">");
		out.println("<option value=\"-1\">请选择</option>");
		if (lClientID == -1) {
			clientList = this.getChildClientID(obaqw, lParentClentID, lAccountGroupTypeID);
			
			//后增加排序
			clientList = this.doSortClientCode(clientList); 
			if (clientList != null) {
				iterList = clientList.iterator();
			}
			while (iterList != null && iterList.hasNext()) {
				OBAccountQueryAmountInfo obaqai = (OBAccountQueryAmountInfo)iterList.next();
				
				Collection accountList = this.getAccountInfoByClientID(obaqw, lAccountGroupTypeID, obaqai.getClientID());
				if (accountList != null) {
					//this.showAccountListControl(out, strFileName, accountList, lAccountID);
					
					listAccount.addAll(accountList);
				}
			}
		} else {
			Collection accountList = this.getAccountInfoByClientID(obaqw, lAccountGroupTypeID, lClientID);
			if (accountList != null) {
				//this.showAccountListControl(out, strFileName, accountList, lAccountID);
				listAccount.addAll(accountList);
			}
		}
		listAccount = (ArrayList) this.doSortAccountCode(listAccount);
		this.showHisAccountListControl(out, strFileName, listAccount, lAccountID);
		out.println("</select>");
	}
//===============================================
	/**
	 *  取得账户组
	 * @param  long[] lTypeID
	 * @return Collection
	 * @exception nothing
	 */
	public Collection getCodeInfo(long[] lTypeID) {
		ArrayList list = new ArrayList();
		if(lTypeID!=null){
			for (int i = 0; i < lTypeID.length; i++) {
				OBAccountQueryAmountInfo obaqai = new OBAccountQueryAmountInfo();
				obaqai.setAccountGroupID(lTypeID[i]);
				obaqai.setAccountGroupName(SETTConstant.AccountGroupType.getName(lTypeID[i]));
				list.add(obaqai);
			}
		}

		return list.size() > 0?list:null;
	}
	
	/**
	 * 得到下属单位及下属单位账号
	 * @throws Exception 
	 */
	public void showClientList(
			JspWriter out, 
			String strFileName, 
			OBAccountQueryWhere obaqw, 
			long lParentCorpID,  
			long[] lClientIDArray) throws Exception {
		out.println("<select name=\""
				+ strFileName
				+ "\" size=\"6\" multiple=\"multiple\">");
		out.println(this.getOption(obaqw, lParentCorpID, lClientIDArray).toString());
		out.println("</select>");
	}
	public static void main(String[] args) throws SQLException, IOException{
		Connection conn= null;
		OBAccountQueryAmountDao dao = new OBAccountQueryAmountDao(conn);
		OBAccountQueryWhere obaqw = new OBAccountQueryWhere();
		obaqw.setCurrencyID(1L);
		obaqw.setOfficeID(1L);
		long[] l = {};
		System.out.println(dao.getOption(obaqw, 2,  l));
	}
	/**
	 * 得到下属单位的下拉菜单
	 * @param out
	 * @param obaqw
	 * @param lParentCorpID
	 * @param lAccountGroupTypeID
	 * @param i
	 * @throws SQLException
	 * @throws IOException
	 */
	public StringBuffer getOption( 
			OBAccountQueryWhere obaqw, 
			long lParentCorpID,  
			long[] lClientIDArray) throws SQLException, IOException
	{
		StringBuffer sb = new StringBuffer();
		ArrayList listClient = new ArrayList();
		listClient.addAll(this.getChildClientList(obaqw,lParentCorpID));//得到下属单位
		if(listClient != null)
		{
			for(int n = 0;n<listClient.size();n++) 
			{
				OBAccountQueryAmountInfo obaqai = (OBAccountQueryAmountInfo)listClient.get(n);
				String strSelected = "";
				if(lClientIDArray != null)
				{
					for(int s = 0;s<lClientIDArray.length;s++)
					{
						if(obaqai.getClientID() == lClientIDArray[s])
							strSelected = "selected";
					}
				}
				sb.append("<option value=\""
						+obaqai.getClientID()
						+"\" "
						+ strSelected
						+">"
						+ obaqai.getClientCode()
						+ "("
						+ obaqai.getClientName()
						+ ")"
						+"</option>\n");	
			}
		}
		return sb;		
	}
	/**
	 * 得到下属单位列表
	 * @param oproperty 
	 * @return 
	 * @return 
	 *
	 */
	public Collection getChildClientList(OBAccountQueryWhere obaqw, long lAccountGroupTypeID)
	{
		String blank = "&nbsp;&nbsp;";
		String _code = "";
		OBAccountQueryAmountInfo info = new OBAccountQueryAmountInfo();
		OBAccountQueryAmountInfo _info = new OBAccountQueryAmountInfo();
		ArrayList listClient = new ArrayList();
		ArrayList rlistClient = new ArrayList();
		listClient = (ArrayList)this.getChildClientList(obaqw,this.getLevelcode(lAccountGroupTypeID),lAccountGroupTypeID);
		for(int i = 0;i<listClient.size();i++)
		{
			info = (OBAccountQueryAmountInfo) listClient.get(i);
			if(info.getLevelid()>0)
			{
				_info = (OBAccountQueryAmountInfo) listClient.get(0);
				for(long j = _info.getLevelid();j<info.getLevelid();j++)
					_code = _code + blank;
				info.setClientCode(_code + info.getClientCode());
				_code = new String();
			}
			rlistClient.add(info);
		}
		
		return rlistClient;
	}
	

	private Collection getChildClientList(OBAccountQueryWhere obaqw, String levelcode, long lAccountGroupTypeID)
	{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null; 
		StringBuffer strSQL = new StringBuffer();
		ArrayList al = new ArrayList();
		
		OBAccountQueryAmountInfo info = null;
		
		try
		{
			strSQL.append("select DISTINCT t.id,t.scode,t.sname,t.nlevelid,t.nlevelcode \n");
			strSQL.append("from Client t,sett_Account b  \n");
			strSQL.append(" where t.id=b.nclientid \n");
			strSQL.append(" and t.nlevelcode like '" + levelcode + "%' \n");
			strSQL.append(" and b.ncurrencyid=" + obaqw.getCurrencyID() + " \n");
			strSQL.append(" and b.nofficeid=" + obaqw.getOfficeID() + " \n");
			strSQL.append(" and b.ncheckstatusid= " + SETTConstant.AccountCheckStatus.CHECK + " \n");
			strSQL.append(this.getStringForAccountGroupTypeID(obaqw, lAccountGroupTypeID).toString());
			strSQL.append(" order by t.nlevelcode \n");
			System.out.println("得到下属单位列表strSQL=\n" + strSQL.toString());
			conn =  super.getConnection();
			ps = conn.prepareStatement(strSQL.toString());
			rs = ps.executeQuery();
			while(rs.next())
			{
				info = new OBAccountQueryAmountInfo();
				info.setClientID(rs.getLong("id"));
				info.setClientCode(rs.getString("scode"));
				info.setClientName(rs.getString("sname"));
				info.setLevelid(rs.getLong("nlevelid"));
				info.setLevelCode(rs.getString("nlevelcode"));
				al.add(info);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return al;
	}
	public Collection getBankAcctByClientID(long clientID)
	{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null; 
		StringBuffer strSQL = new StringBuffer();
		ArrayList al = new ArrayList();
		
		OBBankAccountInfo info = null;
		
		try
		{
			strSQL.append("select a.id,a.saccountno,b.s_accountno,b.s_accountname ,b.s_branchname,c.scode,c.sbranchcity \n");
			strSQL.append("from sett_Account a ,bs_bankaccountinfo b,sett_branch c  \n");
			strSQL.append(" where a.nclientid="+clientID+" and a.nCheckStatusID=" + SETTConstant.AccountCheckStatus.CHECK + " \n" );
			strSQL.append(" and a.id=b.n_subjectid(+) and b.n_bankid=c.id(+) \n");
			//add by zhangfuxing 2006-11-24
			strSQL.append(" and b.n_rdstatus>0 and b.n_ischeck>0 \n");
			strSQL.append(" order by a.id \n");
			System.out.println("得到银行账户内部账户对应关系strSQL=\n" + strSQL.toString());
			conn =  super.getConnection();
			ps = conn.prepareStatement(strSQL.toString());
			rs = ps.executeQuery();
			while(rs.next())
			{
				info = new OBBankAccountInfo();
				info.setNbAcctNO(rs.getString("saccountno"));
				info.setBankAcctNO(rs.getString("s_accountno"));
				
				info.setBankAcctName(rs.getString("s_accountname"));
				info.setBankName(rs.getString("s_branchname"));
				info.setBankCode(rs.getString("scode"));
				info.setBankCity(rs.getString("sbranchcity"));
				al.add(info);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return al;
	}

	private String getLevelcode(long clientID)
	{
		String levelcode = "";
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null; 
		StringBuffer strSQL = new StringBuffer();
		try
		{
			strSQL.append("select nlevelcode from Client where id=" + clientID);
			conn =  super.getConnection();
			ps = conn.prepareStatement(strSQL.toString());
			rs = ps.executeQuery();
			while(rs.next())
			{
				levelcode = rs.getString("nlevelcode");
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		System.out.println("nlevelcode的值是：" + levelcode);
		return levelcode;
	}
}