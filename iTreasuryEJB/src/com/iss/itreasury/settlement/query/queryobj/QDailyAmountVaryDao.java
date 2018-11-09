/*
 * Created on 2004-04-15
 *
 * To change the template for this generated type comment go to 
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.settlement.query.queryobj;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.sql.SQLException;
import java.sql.PreparedStatement;

import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.settlement.query.resultinfo.QueryDailyCapitalInfo;
import com.iss.itreasury.settlement.query.resultinfo.QueryInnerAccountInfo;
import com.iss.itreasury.settlement.query.resultinfo.QueryOuterAccountInfo;
import com.iss.itreasury.settlement.query.resultinfo.QCurrencyDepositInfo;
import com.iss.itreasury.settlement.query.paraminfo.QueryDailyCapitalWhereInfo;

/**
 * @author kewen hu 2004-04-19 
 * 
 * To change the template for this generated type comment go to 
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class QDailyAmountVaryDao extends BaseQueryObject {
	/** 数据库连接 */
	private Connection connection = null;		//DB连接

	/** 数据库表[sett_ClientType] */
	private long SETTCLIENTTYPEID10 = 10;		//华能国际电力开发公司(股份代管)
	private long SETTCLIENTTYPEID11 = 11;		//中国华能集团公司(股份代管)
	private long SETTCLIENTTYPEID14 = 14;		//中国华能集团公司电厂
	private long SETTCLIENTTYPEID13 = 13;		//华能国际电力股份有限公司电厂
	private long SETTCLIENTTYPEID12 = 12;		//华能国际电力开发公司电厂

	/** 判断类型(上交、支款) */
	private static final long CHECKPAY = 1;		//上交
	private static final long CHECKRECEIVE = 2;	//支款

	/** 序号值 */
	private long lIndex = 0;					//序号值

	/**
	 * 构造函数
	 * @param  nothing
	 * @return nothing(创建一个新的数据库连接)
	 * @exception nothing
	 */
	public QDailyAmountVaryDao() {
		super();
		this.connection = this.getConnection();
		try {
			//华能国际电力开发公司(股份代管)
			SETTCLIENTTYPEID10 = this.queryIDByClientTypeName("华能国际电力开发公司(股份代管)", connection);
			//中国华能集团公司(股份代管)
			SETTCLIENTTYPEID11 = this.queryIDByClientTypeName("中国华能集团公司(股份代管)", connection);
			//中国华能集团公司电厂
			SETTCLIENTTYPEID14 = this.queryIDByClientTypeName("中国华能集团公司电厂", connection);
			//华能国际电力股份有限公司电厂
			SETTCLIENTTYPEID13 = this.queryIDByClientTypeName("华能国际电力股份有限公司电厂", connection);
			//华能国际电力开发公司电厂
			SETTCLIENTTYPEID12 = this.queryIDByClientTypeName("华能国际电力开发公司电厂", connection);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 构造函数
	 * @param  Connection conn
	 * @return nothing(保存参数中的数据库连接)
	 * @exception nothing
	 */
	public QDailyAmountVaryDao(Connection conn) {
		super();
		this.connection = conn;
		try {
			//华能国际电力开发公司(股份代管)
			SETTCLIENTTYPEID10 = this.queryIDByClientTypeName("华能国际电力开发公司(股份代管)", connection);
			//中国华能集团公司(股份代管)
			SETTCLIENTTYPEID11 = this.queryIDByClientTypeName("中国华能集团公司(股份代管)", connection);
			//中国华能集团公司电厂
			SETTCLIENTTYPEID14 = this.queryIDByClientTypeName("中国华能集团公司电厂", connection);
			//华能国际电力股份有限公司电厂
			SETTCLIENTTYPEID13 = this.queryIDByClientTypeName("华能国际电力股份有限公司电厂", connection);
			//华能国际电力开发公司电厂
			SETTCLIENTTYPEID12 = this.queryIDByClientTypeName("华能国际电力开发公司电厂", connection);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 析构函数
	 * @param  Connection conn
	 * @return nothing(关闭参数中的数据库连接)
	 * @exception SQLException
	 */
	public void CloseDailyAmountVaryDao(Connection conn) throws SQLException {
		this.cleanup(conn);
	}

	/**
	 * 析构函数
	 * @param  nothing
	 * @return nothing(关闭创建的数据库连接)
	 * @exception SQLException
	 */
	public void CloseDailyAmountVaryDao() throws SQLException {
		this.cleanup(this.connection);
	}

	/**
	 * 将金额为负的值-Value转化为(Value),为正的值Value转化为Value
	 *  -123456.00-->(123,456.00),123456.00-->123,456.00
	 * @param nothing(当dAmount值为0.00时输出"&nbsp;")
	 * @return long[]
	 */
	public static String amountTrim(double dAmount) {
		return amountTrim(dAmount, 3);
	}

	/**
	 * 将金额为负的值-Value转化为(Value),为正的值Value转化为Value
	 *  -123456.00-->(123,456.00),123456.00-->123,456.00
	 * @param nothing(当dAmount值为0.00时输出"&nbsp;")
	 * @return long[]
	 */
	public static String amountTrimForExcel(double dAmount) {
		String sAmount = amountTrim(dAmount, 3);
		if ("&nbsp;&nbsp;".equals(sAmount)) {
			sAmount="";
		}
		return sAmount;
	}

	/**
	 * 四舍五入
	 *  -123456.55-->(123,457),123456.55-->123,457
	 * @param double dAmount(当dAmount值为0.00时输出"&nbsp;&nbsp;")
	 * @return String
	 */
	public static String roundAmount(double dAmount) {
		String sAmount = "";
		if(dAmount < 0.0) {
			dAmount=dAmount*(-1);
			sAmount=DataFormat.roundAmount(dAmount);
			if (!"&nbsp;&nbsp;".equals(sAmount)) {
				sAmount="("+DataFormat.roundAmount(dAmount)+")";
			}
		} else if (dAmount > 0.0) {
			sAmount=DataFormat.roundAmount(dAmount);
		} else {
			sAmount="&nbsp;&nbsp;";
		}
		return sAmount;
	}

	/**
	 * 四舍五入
	 *  -123456.55-->(123,457),123456.55-->123,457
	 * @param double dAmount(当dAmount值为0.00时输出"&nbsp;&nbsp;")
	 * @return String
	 */
	public static String roundAmountForExcel(double dAmount) {
		String sAmount = "";
		if(dAmount < 0.0) {
			dAmount=dAmount*(-1);
			sAmount=DataFormat.roundAmount(dAmount);
			if (!"&nbsp;&nbsp;".equals(sAmount)) {
				sAmount="("+DataFormat.roundAmount(dAmount)+")";
			}
		} else if (dAmount > 0.0) {
			sAmount=DataFormat.roundAmount(dAmount);
		} else {
			sAmount="&nbsp;&nbsp;";
		}
		if ("&nbsp;&nbsp;".equals(sAmount)) {
			sAmount="";
		}
		return sAmount;
	}

	/**
	 * 将金额为负的值-Value转化为(Value),为正的值Value转化为Value
	 *  -123456.00-->(123,456.00),123456.00-->123,456.00
	 * @param nothing(当dAmount值为0.00时输出"0.00")
	 * @return long[]
	 */
	public static String formatAmount(double dAmount) {
		String sAmount = "";
		if(dAmount<0.0) {
			dAmount=dAmount*(-1);
			sAmount="("+DataFormat.formatDisabledAmount(dAmount)+")";
		} else if (dAmount>0.0) {
			sAmount=DataFormat.formatDisabledAmount(dAmount);
		} else {
			sAmount="&nbsp;&nbsp;";
		}
		return sAmount;
	}

	/**
	 * 将金额为负的值-Value转化为(Value),为正的值Value转化为Value
	 *  -123456.00-->(123,456.00),123456.00-->123,456.00
	 * @param nothing(当dAmount值为0.00时输出"0.00")
	 * @return long[]
	 */
	public static String formatAmountForExcel(double dAmount) {
		String sAmount = "";
		if(dAmount<0.0) {
			dAmount=dAmount*(-1);
			sAmount="("+DataFormat.formatDisabledAmount(dAmount)+")";
		} else if (dAmount>0.0) {
			sAmount=DataFormat.formatDisabledAmount(dAmount);
		} else {
			sAmount="";
		}
		return sAmount;
	}

	/**
	 * 取SQL串为ClientID
	 * @param  QueryDailyCapitalWhereInfo qdcwi
	 * @return StringBuffer
	 * @exception nothing
	 */
	private String getSqlForClientID(QueryDailyCapitalWhereInfo qdcwi) {
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("SELECT nClientID \n");
		strSQL.append("FROM sett_Account \n");
		strSQL.append("WHERE 1 = 1 \n");
		strSQL.append("	AND nOfficeID = "+qdcwi.getOfficeID()+" \n");
		strSQL.append("	AND nCurrencyID = "+qdcwi.getCurrencyID()+" \n");
		strSQL.append("	AND nStatusID = "+SETTConstant.AccountStatus.NORMAL+" \n");
		strSQL.append("	AND nCheckStatusID = "+SETTConstant.AccountCheckStatus.CHECK+" \n");
		strSQL.append("	AND nAccountTypeID IN ("+DataFormat.getStringWithTagByLongArray(this.getAccountTypeCode())+") \n");

		return strSQL.toString();
	}

	/**
	 * 查询客户分类ID通过客户类型名称
	 * @param  String sName, Connection conn
	 * @return long
	 * @exception throws SQLException
	 */
	private long queryIDByClientTypeName(String sName, Connection conn) throws SQLException {
		long clientTypeID = 0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("SELECT ID \n");
		strSQL.append("FROM sett_ClientType \n");
		strSQL.append("WHERE 1 = 1 \n");
		strSQL.append("	AND sName = '"+sName+"' \n");
		strSQL.append("	AND nStatusID = 1 \n");
		log.info("SQL="+strSQL.toString());
		try {
			ps = conn.prepareStatement(strSQL.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				clientTypeID = rs.getLong("ID");
			}
		} catch (SQLException se) {
			throw new SQLException("取客户分类ID通过客户类型名称出错[queryIDClientTypeName]:" + se.getMessage());
		} finally {
			this.cleanup(rs);
			this.cleanup(ps);
		}

		return clientTypeID;
	}

	/**
	 * 查询客户分类ID通过客户类型名称
	 * @param  String sName
	 * @return long
	 * @exception throws SQLException
	 */
	private long queryIDByClientTypeName(String sName) throws SQLException {
		return this.queryIDByClientTypeName(sName, connection);
	}

	/**
	 * 取全资电厂小计的客户数量
	 * @param  QueryDailyCapitalWhereInfo qdcwi, Connection conn
	 * @return int
	 * @exception SQLException
	 */
	public int getCapitalElectricClientCount(QueryDailyCapitalWhereInfo qdcwi, Connection conn) throws SQLException {
		int iCount = 0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("SELECT COUNT(*) clientCount \n");
		strSQL.append("FROM Client \n");
		strSQL.append("WHERE  1 = 1 \n");
		strSQL.append("	AND nOfficeID = "+qdcwi.getOfficeID()+" \n");
		strSQL.append("	AND nParentCorpID1 = NVL((SELECT ID FROM Client WHERE sCode = '"+SETTConstant.PartClientCode.CODE0003+"'),0) \n");
		strSQL.append("	AND nParentCorpID2 IN (NVL((SELECT ID FROM Client WHERE sCode = '"+SETTConstant.PartClientCode.CODE0000+"'),0),-1) \n");
		strSQL.append("	AND nSettClientTypeID = "+SETTCLIENTTYPEID13+" \n");
		strSQL.append("	AND ID NOT IN ("+this.getSqlForClientID(qdcwi)+") \n");
		strSQL.append("ORDER BY ID \n");
		log.info("SQL="+strSQL.toString());
		try {
			ps = conn.prepareStatement(strSQL.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				iCount = rs.getInt("clientCount");
			}
		} catch (SQLException se) {
			throw new SQLException("取全资电厂小计的客户数量出错[getCapitalElectricClientCount]: "+se.getMessage());
		} finally {
			this.cleanup(rs);
			this.cleanup(ps);
		}

		return iCount;
	}

	/**
	 * 取全资电厂小计的客户数量
	 * @param  QueryDailyCapitalWhereInfo qdcwi
	 * @return int
	 * @exception SQLException
	 */
	public int getCapitalElectricClientCount(QueryDailyCapitalWhereInfo qdcwi) throws SQLException {
		return this.getCapitalElectricClientCount(qdcwi, connection);
	}

	/**
	 * 判断固定客户是否账户正常
	 * @param  QueryDailyCapitalWhereInfo qdcwi, String sCode, Connection conn
	 * @return String[]
	 * @exception SQLException
	 */
	public String[] CheckNormalForClientID(QueryDailyCapitalWhereInfo qdcwi, String sCode, Connection conn) throws SQLException {
		String[] string = new String[3];
		string[0] = "0";
		string[1] = "";
		string[2] = "";
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("SELECT ID,sName,sCode \n");
		strSQL.append("FROM Client \n");
		strSQL.append("WHERE  1 = 1 \n");
		strSQL.append("	AND nOfficeID = "+qdcwi.getOfficeID()+" \n");
		strSQL.append("	AND sCode = '"+sCode+"' \n");
		strSQL.append("	AND ID IN ("+this.getSqlForClientID(qdcwi)+") \n");
		strSQL.append("ORDER BY ID \n");
		log.info("SQL="+strSQL.toString());
		try {
			ps = conn.prepareStatement(strSQL.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				string[0] = rs.getString("ID");
				string[1] = rs.getString("sName");
				string[2] = rs.getString("sCode");
			}
		} catch (SQLException se) {
			throw new SQLException("判断固定客户是否账户正常出错[CheckNormalForClientID]: "+se.getMessage());
		} finally {
			this.cleanup(rs);
			this.cleanup(ps);
		}

		return string;
	}

	/**
	 * 判断固定客户是否账户正常
	 * @param  QueryDailyCapitalWhereInfo qdcwi, String sCode
	 * @return String[]
	 * @exception SQLException
	 */
	public String[] CheckNormalForClientID(QueryDailyCapitalWhereInfo qdcwi, String sCode) throws SQLException {
		return this.CheckNormalForClientID(qdcwi, sCode, connection);
	}

	/**
	 * 取全资电厂小计的客户集
	 * @param  QueryDailyCapitalWhereInfo qdcwi, Connection conn
	 * @return Collection
	 * @exception SQLException
	 */
	public Collection getCapitalElectricClientID(QueryDailyCapitalWhereInfo qdcwi, Connection conn) throws SQLException {
		ArrayList list = new ArrayList();
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("SELECT ID,sName,sSimpleName,sCode \n");
		strSQL.append("FROM Client \n");
		strSQL.append("WHERE  1 = 1 \n");
		strSQL.append("	AND nOfficeID = "+qdcwi.getOfficeID()+" \n");
		strSQL.append("	AND nParentCorpID1 = NVL((SELECT ID FROM Client WHERE sCode = '"+SETTConstant.PartClientCode.CODE0003+"'),0) \n");
		strSQL.append("	AND nParentCorpID2 IN (NVL((SELECT ID FROM Client WHERE sCode = '"+SETTConstant.PartClientCode.CODE0000+"'),0),-1) \n");
		strSQL.append("	AND nSettClientTypeID = "+SETTCLIENTTYPEID13+" \n");
		strSQL.append("	AND ID NOT IN ("+this.getSqlForClientID(qdcwi)+") \n");
		strSQL.append("ORDER BY ID \n");
		log.info("SQL="+strSQL.toString());
		try {
			ps = conn.prepareStatement(strSQL.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				String[] string = new String[4];
				string[0] = rs.getString("ID");
				string[1] = rs.getString("sName");
				string[2] = rs.getString("sCode");
				string[3] = rs.getString("sSimpleName");
				list.add(string);
			}
		} catch (SQLException se) {
			throw new SQLException("取全资电厂小计的客户集出错[getCapitalElectricClientID]: "+se.getMessage());
		} finally {
			this.cleanup(rs);
			this.cleanup(ps);
		}

		return list != null && list.size() > 0?list:null;
	}

	/**
	 * 取全资电厂小计的客户集
	 * @param  QueryDailyCapitalWhereInfo qdcwi
	 * @return Collection
	 * @exception SQLException
	 */
	public Collection getCapitalElectricClientID(QueryDailyCapitalWhereInfo qdcwi) throws SQLException {
		return this.getCapitalElectricClientID(qdcwi, connection);
	}

	/**
	 * 取控股电厂小计的客户集
	 * @param  QueryDailyCapitalWhereInfo qdcwi, Connection conn
	 * @return Collection
	 * @exception SQLException
	 */
	public Collection getHoldingElectricClientID(QueryDailyCapitalWhereInfo qdcwi, Connection conn) throws SQLException {
		ArrayList list = new ArrayList();
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("SELECT ID,sName,sCode \n");
		strSQL.append("FROM Client \n");
		strSQL.append("WHERE  1 = 1 \n");
		strSQL.append("	AND nOfficeID = "+qdcwi.getOfficeID()+" \n");
		strSQL.append("	AND nParentCorpID1 = NVL((SELECT ID FROM Client WHERE sCode = '"+SETTConstant.PartClientCode.CODE0003+"'),0) \n");
		strSQL.append("	AND nParentCorpID2 IN (NVL((SELECT ID FROM Client WHERE sCode = '"+SETTConstant.PartClientCode.CODE0000+"'),0),-1) \n");
		strSQL.append("	AND nSettClientTypeID = "+SETTCLIENTTYPEID13+" \n");
		strSQL.append("	AND ID IN ("+this.getSqlForClientID(qdcwi)+") \n");
		strSQL.append("ORDER BY ID \n");
		log.info("SQL="+strSQL.toString());
		try {
			ps = conn.prepareStatement(strSQL.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				String[] string = new String[3];
				string[0] = rs.getString("ID");
				string[1] = rs.getString("sName");
				string[2] = rs.getString("sCode");
				list.add(string);
			}
		} catch (SQLException se) {
			throw new SQLException("取控股电厂小计的客户集出错[getHoldingElectricClientID]: "+se.getMessage());
		} finally {
			this.cleanup(rs);
			this.cleanup(ps);
		}

		return list != null && list.size() > 0?list:null;
	}

	/**
	 * 取控股电厂小计的客户集
	 * @param  QueryDailyCapitalWhereInfo qdcwi
	 * @return Collection
	 * @exception SQLException
	 */
	public Collection getHoldingElectricClientID(QueryDailyCapitalWhereInfo qdcwi) throws SQLException {
		return this.getHoldingElectricClientID(qdcwi, connection);
	}

	/**
	 * 取集团1合计的客户集
	 * @param  QueryDailyCapitalWhereInfo qdcwi, Connection conn
	 * @return Collection
	 * @exception SQLException
	 */
	public Collection getGroupTotalClientID(QueryDailyCapitalWhereInfo qdcwi, Connection conn) throws SQLException {
		ArrayList list = new ArrayList();
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("SELECT ID,sName,sCode \n");
		strSQL.append("FROM Client \n");
		strSQL.append("WHERE  1 = 1 \n");
		strSQL.append("	AND nOfficeID = "+qdcwi.getOfficeID()+" \n");
		strSQL.append("	AND nParentCorpID1 = NVL((SELECT ID FROM Client WHERE sCode = '"+SETTConstant.PartClientCode.CODE0001+"'),0) \n");
		strSQL.append("	AND nParentCorpID2 = NVL((SELECT ID FROM Client WHERE sCode = '"+SETTConstant.PartClientCode.CODE0003+"'),0) \n");
		strSQL.append("	AND nSettClientTypeID = "+SETTCLIENTTYPEID11+" \n");
		strSQL.append("	AND ID NOT IN ("+this.getSqlForClientID(qdcwi)+") \n");
		strSQL.append("ORDER BY ID \n");
		log.info("SQL="+strSQL.toString());
		try {
			ps = conn.prepareStatement(strSQL.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				String[] string = new String[3];
				string[0] = rs.getString("ID");
				string[1] = rs.getString("sName");
				string[2] = rs.getString("sCode");
				list.add(string);
			}
		} catch (SQLException se) {
			throw new SQLException("取集团1合计的客户集出错[getGroupTotalClientID]: "+se.getMessage());
		} finally {
			this.cleanup(rs);
			this.cleanup(ps);
		}

		return list != null && list.size() > 0?list:null;
	}

	/**
	 * 取集团1合计的客户集
	 * @param  QueryDailyCapitalWhereInfo qdcwi
	 * @return Collection
	 * @exception SQLException
	 */
	public Collection getGroupTotalClientID(QueryDailyCapitalWhereInfo qdcwi) throws SQLException {
		return this.getGroupTotalClientID(qdcwi, connection);
	}

	/**
	 * 取开发1合计的客户集
	 * @param  QueryDailyCapitalWhereInfo qdcwi, Connection conn
	 * @return Collection
	 * @exception SQLException
	 */
	public Collection getEmpolderTotalClientID(QueryDailyCapitalWhereInfo qdcwi, Connection conn) throws SQLException {
		ArrayList list = new ArrayList();
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("SELECT ID,sName,sCode \n");
		strSQL.append("FROM Client \n");
		strSQL.append("WHERE  1 = 1 \n");
		strSQL.append("	AND nOfficeID = "+qdcwi.getOfficeID()+" \n");
		strSQL.append("	AND nParentCorpID1 = NVL((SELECT ID FROM Client WHERE sCode = '"+SETTConstant.PartClientCode.CODE0002+"'),0) \n");
		strSQL.append("	AND nParentCorpID2 = NVL((SELECT ID FROM Client WHERE sCode = '"+SETTConstant.PartClientCode.CODE0003+"'),0) \n");
		strSQL.append("	AND nSettClientTypeID = "+SETTCLIENTTYPEID10+" \n");
		strSQL.append("	AND ID NOT IN ("+this.getSqlForClientID(qdcwi)+") \n");
		strSQL.append("ORDER BY ID \n");
		log.info("SQL="+strSQL.toString());
		try {
			ps = conn.prepareStatement(strSQL.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				String[] string = new String[3];
				string[0] = rs.getString("ID");
				string[1] = rs.getString("sName");
				string[2] = rs.getString("sCode");
				list.add(string);
			}
		} catch (SQLException se) {
			throw new SQLException("取开发1合计的客户集出错[getEmpolderTotalClientID]: "+se.getMessage());
		} finally {
			this.cleanup(rs);
			this.cleanup(ps);
		}

		return list != null && list.size() > 0?list:null;
	}

	/**
	 * 取开发1合计的客户集
	 * @param  QueryDailyCapitalWhereInfo qdcwi
	 * @return Collection
	 * @exception SQLException
	 */
	public Collection getEmpolderTotalClientID(QueryDailyCapitalWhereInfo qdcwi) throws SQLException {
		return this.getEmpolderTotalClientID(qdcwi, connection);
	}

	/**
	 * 取集团电厂合计的客户集
	 * @param  QueryDailyCapitalWhereInfo qdcwi, Connection conn
	 * @return Collection
	 * @exception SQLException
	 */
	public Collection getGroupElectricClientID(QueryDailyCapitalWhereInfo qdcwi, Connection conn) throws SQLException {
		ArrayList list = new ArrayList();
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("SELECT ID,sName,sCode \n");
		strSQL.append("FROM Client \n");
		strSQL.append("WHERE  1 = 1 \n");
		strSQL.append("	AND nOfficeID = "+qdcwi.getOfficeID()+" \n");
		strSQL.append("	AND nParentCorpID1 = NVL((SELECT ID FROM Client WHERE sCode = '"+SETTConstant.PartClientCode.CODE0001+"'),0) \n");
		strSQL.append("	AND nParentCorpID2 = NVL((SELECT ID FROM Client WHERE sCode = '"+SETTConstant.PartClientCode.CODE0003+"'),0) \n");
		strSQL.append("	AND nSettClientTypeID = "+SETTCLIENTTYPEID11+" \n");
		strSQL.append("	AND ID IN ("+this.getSqlForClientID(qdcwi)+") \n");
		strSQL.append("ORDER BY ID \n");
		log.info("SQL="+strSQL.toString());
		try {
			ps = conn.prepareStatement(strSQL.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				String[] string = new String[3];
				string[0] = rs.getString("ID");
				string[1] = rs.getString("sName");
				string[2] = rs.getString("sCode");
				list.add(string);
			}
		} catch (SQLException se) {
			throw new SQLException("取集团电厂合计的客户集出错[getGroupElectricClientID]: "+se.getMessage());
		} finally {
			this.cleanup(rs);
			this.cleanup(ps);
		}

		return list != null && list.size() > 0?list:null;
	}

	/**
	 * 取集团电厂合计的客户集
	 * @param  QueryDailyCapitalWhereInfo qdcwi
	 * @return Collection
	 * @exception SQLException
	 */
	public Collection getGroupElectricClientID(QueryDailyCapitalWhereInfo qdcwi) throws SQLException {
		return this.getGroupElectricClientID(qdcwi, connection);
	}

	/**
	 * 取开发电厂合计的客户集
	 * @param  QueryDailyCapitalWhereInfo qdcwi, Connection conn
	 * @return Collection
	 * @exception SQLException
	 */
	public Collection getEmpolderElectricClientID(QueryDailyCapitalWhereInfo qdcwi, Connection conn) throws SQLException {
		ArrayList list = new ArrayList();
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("SELECT ID,sName,sCode \n");
		strSQL.append("FROM Client \n");
		strSQL.append("WHERE  1 = 1 \n");
		strSQL.append("	AND nOfficeID = "+qdcwi.getOfficeID()+" \n");
		strSQL.append("	AND nParentCorpID1 = NVL((SELECT ID FROM Client WHERE sCode = '"+SETTConstant.PartClientCode.CODE0002+"'),0) \n");
		strSQL.append("	AND nParentCorpID2 = NVL((SELECT ID FROM Client WHERE sCode = '"+SETTConstant.PartClientCode.CODE0003+"'),0) \n");
		strSQL.append("	AND nSettClientTypeID = "+SETTCLIENTTYPEID10+" \n");
		strSQL.append("	AND ID IN ("+this.getSqlForClientID(qdcwi)+") \n");
		strSQL.append("ORDER BY ID \n");
		log.info("SQL="+strSQL.toString());
		try {
			ps = conn.prepareStatement(strSQL.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				String[] string = new String[3];
				string[0] = rs.getString("ID");
				string[1] = rs.getString("sName");
				string[2] = rs.getString("sCode");
				list.add(string);
			}
		} catch (SQLException se) {
			throw new SQLException("取开发电厂合计的客户集出错[getEmpolderElectricClientID]: "+se.getMessage());
		} finally {
			this.cleanup(rs);
			this.cleanup(ps);
		}

		return list != null && list.size() > 0?list:null;
	}

	/**
	 * 取开发电厂合计的客户集
	 * @param  QueryDailyCapitalWhereInfo qdcwi
	 * @return Collection
	 * @exception SQLException
	 */
	public Collection getEmpolderElectricClientID(QueryDailyCapitalWhereInfo qdcwi) throws SQLException {
		return this.getEmpolderElectricClientID(qdcwi, connection);
	}

	/**
	 * 取集团全资电厂的客户集
	 * @param  QueryDailyCapitalWhereInfo qdcwi, Connection conn
	 * @return Collection
	 * @exception SQLException
	 */
	public Collection getGroupCapitalClientID(QueryDailyCapitalWhereInfo qdcwi, Connection conn) throws SQLException {
		ArrayList list = new ArrayList();
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("SELECT ID,sName,sCode \n");
		strSQL.append("FROM Client \n");
		strSQL.append("WHERE  1 = 1 \n");
		strSQL.append("	AND nOfficeID = "+qdcwi.getOfficeID()+" \n");
		strSQL.append("	AND nParentCorpID1 = NVL((SELECT ID FROM Client WHERE sCode = '"+SETTConstant.PartClientCode.CODE0001+"'),0) \n");
		strSQL.append("	AND nParentCorpID2 IN (NVL((SELECT ID FROM Client WHERE sCode = '"+SETTConstant.PartClientCode.CODE0000+"'),0),-1) \n");
		strSQL.append("	AND nSettClientTypeID = "+SETTCLIENTTYPEID14+" \n");
		strSQL.append("	AND ID NOT IN ("+this.getSqlForClientID(qdcwi)+") \n");
		strSQL.append("ORDER BY ID \n");
		log.info("SQL="+strSQL.toString());
		try {
			ps = conn.prepareStatement(strSQL.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				String[] string = new String[3];
				string[0] = rs.getString("ID");
				string[1] = rs.getString("sName");
				string[2] = rs.getString("sCode");
				list.add(string);
			}
		} catch (SQLException se) {
			throw new SQLException("取集团全资电厂的客户集出错[getGroupCapitalClientID]: "+se.getMessage());
		} finally {
			this.cleanup(rs);
			this.cleanup(ps);
		}

		return list != null && list.size() > 0?list:null;
	}

	/**
	 * 取集团全资电厂的客户集
	 * @param  QueryDailyCapitalWhereInfo qdcwi
	 * @return Collection
	 * @exception SQLException
	 */
	public Collection getGroupCapitalClientID(QueryDailyCapitalWhereInfo qdcwi) throws SQLException {
		return this.getGroupCapitalClientID(qdcwi, connection);
	}

	/**
	 * 取开发全资电厂的客户集
	 * @param  QueryDailyCapitalWhereInfo qdcwi, Connection conn
	 * @return Collection
	 * @exception SQLException
	 */
	public Collection getEmpolderCapitalClientID(QueryDailyCapitalWhereInfo qdcwi, Connection conn) throws SQLException {
		ArrayList list = new ArrayList();
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("SELECT ID,sName,sCode \n");
		strSQL.append("FROM Client \n");
		strSQL.append("WHERE  1 = 1 \n");
		strSQL.append("	AND nOfficeID = "+qdcwi.getOfficeID()+" \n");
		strSQL.append("	AND nParentCorpID1 = NVL((SELECT ID FROM Client WHERE sCode = '"+SETTConstant.PartClientCode.CODE0002+"'),0) \n");
		strSQL.append("	AND nParentCorpID2 IN (NVL((SELECT ID FROM Client WHERE sCode = '"+SETTConstant.PartClientCode.CODE0000+"'),0),-1) \n");
		strSQL.append("	AND nSettClientTypeID = "+SETTCLIENTTYPEID12+" \n");
		strSQL.append("	AND ID NOT IN ("+this.getSqlForClientID(qdcwi)+") \n");
		strSQL.append("ORDER BY ID \n");
		log.info("SQL="+strSQL.toString());
		try {
			ps = conn.prepareStatement(strSQL.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				String[] string = new String[3];
				string[0] = rs.getString("ID");
				string[1] = rs.getString("sName");
				string[2] = rs.getString("sCode");
				list.add(string);
			}
		} catch (SQLException se) {
			throw new SQLException("取开发全资电厂的客户集出错[getEmpolderCapitalClientID]: "+se.getMessage());
		} finally {
			this.cleanup(rs);
			this.cleanup(ps);
		}

		return list != null && list.size() > 0?list:null;
	}

	/**
	 * 取开发全资电厂的客户集
	 * @param  QueryDailyCapitalWhereInfo qdcwi
	 * @return Collection
	 * @exception SQLException
	 */
	public Collection getEmpolderCapitalClientID(QueryDailyCapitalWhereInfo qdcwi) throws SQLException {
		return this.getEmpolderCapitalClientID(qdcwi, connection);
	}

	/**
	 * 取集团控股电厂的客户集
	 * @param  QueryDailyCapitalWhereInfo qdcwi, Connection conn
	 * @return Collection
	 * @exception SQLException
	 */
	public Collection getGroupHoldingClientID(QueryDailyCapitalWhereInfo qdcwi, Connection conn) throws SQLException {
		ArrayList list = new ArrayList();
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("SELECT ID,sName,sCode \n");
		strSQL.append("FROM Client \n");
		strSQL.append("WHERE  1 = 1 \n");
		strSQL.append("	AND nOfficeID = "+qdcwi.getOfficeID()+" \n");
		strSQL.append("	AND nParentCorpID1 = NVL((SELECT ID FROM Client WHERE sCode = '"+SETTConstant.PartClientCode.CODE0001+"'),0) \n");
		strSQL.append("	AND nParentCorpID2 IN (NVL((SELECT ID FROM Client WHERE sCode = '"+SETTConstant.PartClientCode.CODE0000+"'),0),-1) \n");
		strSQL.append("	AND nSettClientTypeID = "+SETTCLIENTTYPEID14+" \n");
		strSQL.append("	AND ID IN ("+this.getSqlForClientID(qdcwi)+") \n");
		strSQL.append("ORDER BY ID \n");
		log.info("SQL="+strSQL.toString());
		try {
			ps = conn.prepareStatement(strSQL.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				String[] string = new String[3];
				string[0] = rs.getString("ID");
				string[1] = rs.getString("sName");
				string[2] = rs.getString("sCode");
				list.add(string);
			}
		} catch (SQLException se) {
			throw new SQLException("取集团控股电厂的客户集出错[getGroupHoldingClientID]: "+se.getMessage());
		} finally {
			this.cleanup(rs);
			this.cleanup(ps);
		}

		return list != null && list.size() > 0?list:null;
	}

	/**
	 * 取集团控股电厂的客户集
	 * @param  QueryDailyCapitalWhereInfo qdcwi
	 * @return Collection
	 * @exception SQLException
	 */
	public Collection getGroupHoldingClientID(QueryDailyCapitalWhereInfo qdcwi) throws SQLException {
		return this.getGroupHoldingClientID(qdcwi, connection);
	}

	/**
	 * 取开发控股电厂的客户集
	 * @param  QueryDailyCapitalWhereInfo qdcwi, Connection conn
	 * @return Collection
	 * @exception SQLException
	 */
	public Collection getEmpolderHoldingClientID(QueryDailyCapitalWhereInfo qdcwi, Connection conn) throws SQLException {
		ArrayList list = new ArrayList();
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("SELECT ID,sName,sCode \n");
		strSQL.append("FROM Client \n");
		strSQL.append("WHERE  1 = 1 \n");
		strSQL.append("	AND nOfficeID = "+qdcwi.getOfficeID()+" \n");
		strSQL.append("	AND nParentCorpID1 = NVL((SELECT ID FROM Client WHERE sCode = '"+SETTConstant.PartClientCode.CODE0002+"'),0) \n");
		strSQL.append("	AND nParentCorpID2 IN (NVL((SELECT ID FROM Client WHERE sCode = '"+SETTConstant.PartClientCode.CODE0000+"'),0),-1) \n");
		strSQL.append("	AND nSettClientTypeID = "+SETTCLIENTTYPEID12+" \n");
		strSQL.append("	AND ID IN ("+this.getSqlForClientID(qdcwi)+") \n");
		strSQL.append("ORDER BY ID \n");
		log.info("SQL="+strSQL.toString());
		try {
			ps = conn.prepareStatement(strSQL.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				String[] string = new String[3];
				string[0] = rs.getString("ID");
				string[1] = rs.getString("sName");
				string[2] = rs.getString("sCode");
				list.add(string);
			}
		} catch (SQLException se) {
			throw new SQLException("取开发控股电厂的客户集出错[getEmpolderHoldingClientID]: "+se.getMessage());
		} finally {
			this.cleanup(rs);
			this.cleanup(ps);
		}

		return list != null && list.size() > 0?list:null;
	}

	/**
	 * 取开发控股电厂的客户集
	 * @param  QueryDailyCapitalWhereInfo qdcwi
	 * @return Collection
	 * @exception SQLException
	 */
	public Collection getEmpolderHoldingClientID(QueryDailyCapitalWhereInfo qdcwi) throws SQLException {
		return this.getEmpolderHoldingClientID(qdcwi, connection);
	}

	/** 取内部数据 */
	/**
	 * 查询内部客户上月余额数据
	 * @param  QueryDailyCapitalWhereInfo qdcwi, long lClientID, Connection conn
	 * @return double
	 * @exception throws SQLException
	 */
	private double queryPreviousMonthBalance(QueryDailyCapitalWhereInfo qdcwi, long lClientID, Connection conn) throws SQLException {
		double mBalance = 0.0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("SELECT SUM(NVL(a.mBalance,0.0)) mBalance \n");
		strSQL.append("FROM sett_DailyAccountBalance a,sett_Account b \n");
		strSQL.append("WHERE a.nAccountID = b.ID \n");
		strSQL.append("	AND b.nOfficeID = "+qdcwi.getOfficeID()+" \n");
		strSQL.append("	AND b.nCurrencyID = "+qdcwi.getCurrencyID()+" \n");
		strSQL.append("	AND b.nStatusID = "+SETTConstant.AccountStatus.NORMAL+" \n");
		strSQL.append("	AND b.nCheckStatusID = "+SETTConstant.AccountCheckStatus.CHECK+" \n");
		strSQL.append("	AND b.nAccountTypeID IN ("+DataFormat.getStringWithTagByLongArray(this.getAccountTypeCode())+") \n");
		strSQL.append("	AND b.nClientID = "+lClientID+" \n");
		if (qdcwi.getQueryDate() != null) {
			strSQL.append("	AND a.dtDate = TO_DATE('"+DataFormat.getLastDateString(
				DataFormat.getPreviousMonth(qdcwi.getQueryDate(), 1))+
				"','YYYY/MM/DD HH24:MI:SS') \n");
		}
		//strSQL.append("SELECT SUM(DECODE(nTransDirection, \n");
		//strSQL.append("	"+SETTConstant.DebitOrCredit.CREDIT+",NVL(mAmount,0.00), \n");
		//strSQL.append("	"+SETTConstant.DebitOrCredit.DEBIT+",-NVL(mAmount,0.00))) mAmount \n");
		log.info("SQL="+strSQL.toString());
		try {
			ps = conn.prepareStatement(strSQL.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				mBalance = rs.getDouble("mBalance");
			}
		} catch (SQLException se) {
			throw new SQLException("取内部客户上月余额数据出错[queryPreviousMonthBalance]:" + se.getMessage());
		} finally {
			this.cleanup(rs);
			this.cleanup(ps);
		}

		return mBalance;
	}

	/**
	 * 查询内部客户昨日余额数据
	 * @param  QueryDailyCapitalWhereInfo qdcwi, long lClientID, Connection conn
	 * @return double
	 * @exception throws SQLException
	 */
	private double queryYesterdayBalance(QueryDailyCapitalWhereInfo qdcwi, long lClientID, Connection conn) throws SQLException {
		double mBalance = 0.0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("SELECT SUM(NVL(a.mBalance,0.0)) mBalance \n");
		strSQL.append("FROM sett_DailyAccountBalance a,sett_Account b \n");
		strSQL.append("WHERE a.nAccountID = b.ID \n");
		strSQL.append("	AND b.nOfficeID = "+qdcwi.getOfficeID()+" \n");
		strSQL.append("	AND b.nCurrencyID = "+qdcwi.getCurrencyID()+" \n");
		strSQL.append("	AND b.nStatusID = "+SETTConstant.AccountStatus.NORMAL+" \n");
		strSQL.append("	AND b.nCheckStatusID = "+SETTConstant.AccountCheckStatus.CHECK+" \n");
		strSQL.append("	AND b.nAccountTypeID IN ("+DataFormat.getStringWithTagByLongArray(this.getAccountTypeCode())+") \n");
		strSQL.append("	AND b.nClientID = "+lClientID+" \n");
		if (qdcwi.getQueryDate() != null) {
			strSQL.append("	AND a.dtDate = TO_DATE('"+DataFormat.getDateString(
			DataFormat.getPreviousDate(qdcwi.getQueryDate()))+
			"','YYYY/MM/DD HH24:MI:SS') \n");
		}
		log.info("SQL="+strSQL.toString());
		try {
			ps = conn.prepareStatement(strSQL.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				mBalance = rs.getDouble("mBalance");
			}
		} catch (SQLException se) {
			throw new SQLException("取内部客户昨日余额数据出错[queryYesterdayBalance]:" + se.getMessage());
		} finally {
			this.cleanup(rs);
			this.cleanup(ps);
		}

		return mBalance;
	}

	/**
	 * 查询内部客户非当日的今日余额数据
	 * @param  QueryDailyCapitalWhereInfo qdcwi, long lClientID, Connection conn
	 * @return double
	 * @exception throws SQLException
	 */
	private double queryDailyAccountBalance(QueryDailyCapitalWhereInfo qdcwi, long lClientID, Connection conn) throws SQLException {
		double mBalance = 0.0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("SELECT SUM(NVL(a.mBalance,0.0)) mBalance \n");
		strSQL.append("FROM sett_DailyAccountBalance a,sett_Account b \n");
		strSQL.append("WHERE a.nAccountID = b.ID \n");
		strSQL.append("	AND b.nOfficeID = "+qdcwi.getOfficeID()+" \n");
		strSQL.append("	AND b.nCurrencyID = "+qdcwi.getCurrencyID()+" \n");
		strSQL.append("	AND b.nStatusID = "+SETTConstant.AccountStatus.NORMAL+" \n");
		strSQL.append("	AND b.nCheckStatusID = "+SETTConstant.AccountCheckStatus.CHECK+" \n");
		strSQL.append("	AND b.nAccountTypeID IN ("+DataFormat.getStringWithTagByLongArray(this.getAccountTypeCode())+") \n");
		strSQL.append("	AND b.nClientID = "+lClientID+" \n");
		if (qdcwi.getQueryDate() != null) {
			strSQL.append("	AND a.dtDate = TO_DATE('"+DataFormat.getDateString(qdcwi.getQueryDate())+
			"','YYYY/MM/DD HH24:MI:SS') \n");
		}
		log.info("SQL="+strSQL.toString());
		try {
			ps = conn.prepareStatement(strSQL.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				mBalance = rs.getDouble("mBalance");
			}
		} catch (SQLException se) {
			throw new SQLException("取内部客户非当日的今日余额数据出错[queryDailyAccountBalance]:" + se.getMessage());
		} finally {
			this.cleanup(rs);
			this.cleanup(ps);
		}

		return mBalance;
	}

	/**
	 * 查询内部客户当日的今日余额数据
	 * @param  QueryDailyCapitalWhereInfo qdcwi, long lClientID, Connection conn
	 * @return double
	 * @exception throws SQLException
	 */
	private double querySubAccountBalance(QueryDailyCapitalWhereInfo qdcwi, long lClientID, Connection conn) throws SQLException {
		double mBalance = 0.0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("SELECT SUM(NVL(a.mBalance,0.0)) mBalance \n");
		strSQL.append("FROM sett_SubAccount a,sett_Account b \n");
		strSQL.append("WHERE a.nAccountID = b.ID \n");
		strSQL.append("	AND b.nOfficeID = "+qdcwi.getOfficeID()+" \n");
		strSQL.append("	AND b.nCurrencyID = "+qdcwi.getCurrencyID()+" \n");
		strSQL.append("	AND b.nStatusID = "+SETTConstant.AccountStatus.NORMAL+" \n");
		strSQL.append("	AND b.nCheckStatusID = "+SETTConstant.AccountCheckStatus.CHECK+" \n");
		strSQL.append("	AND b.nAccountTypeID IN ("+DataFormat.getStringWithTagByLongArray(this.getAccountTypeCode())+") \n");
		strSQL.append("	AND b.nClientID = "+lClientID+" \n");
		strSQL.append("	AND a.nStatusID = "+SETTConstant.SubAccountStatus.NORMAL);
		//strSQL.append("	AND a.dtDate = TO_DATE('"+DataFormat.getDateString(qdcwi.getDateTo())+"','YYYY/MM/DD HH24:MI:SS') \n");

		log.info("SQL="+strSQL.toString());
		try {
			ps = conn.prepareStatement(strSQL.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				mBalance = rs.getDouble("mBalance");
			}
		} catch (SQLException se) {
			throw new SQLException("取内部客户当日的今日余额数据出错[querySubAccountBalance]:" + se.getMessage());
		} finally {
			this.cleanup(rs);
			this.cleanup(ps);
		}

		return mBalance;
	}

	/**
	 * 查询内部客户的今日余额数据
	 * @param  QueryDailyCapitalWhereInfo qdcwi, long lClientID, Connection conn
	 * @return double
	 * @exception throws SQLException
	 */
	private double queryTodayBalance(QueryDailyCapitalWhereInfo qdcwi, long lClientID, Connection conn) throws SQLException {
		if (this.isToday(qdcwi.getOfficeID(), qdcwi.getCurrencyID(), qdcwi.getQueryDate())) {
			return this.querySubAccountBalance(qdcwi, lClientID, conn);
		} else {
			return this.queryDailyAccountBalance(qdcwi, lClientID, conn);
		}
	}

	/**
	 * 查询内部客户当日变动数据
	 * @param  QueryDailyCapitalWhereInfo qdcwi, long nDirection, long lClientID, Connection conn
	 * @return double
	 * @exception throws SQLException
	 */
	private double queryInsideDailyAmount(QueryDailyCapitalWhereInfo qdcwi, long nDirection, long lClientID, Connection conn) throws SQLException {
		double mDailyAmount = 0.0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("SELECT NVL(SUM(a.mAmount),0.0) mAmount \n");
		strSQL.append("FROM sett_TransAccountDetail a,sett_Account b \n");
		strSQL.append("WHERE a.nTransAccountID = b.ID \n");
		strSQL.append("	AND a.nOfficeID = "+qdcwi.getOfficeID()+" \n");
		strSQL.append("	AND a.nCurrencyID = "+qdcwi.getCurrencyID()+" \n");
		strSQL.append("	AND a.nStatusID = "+SETTConstant.TransactionStatus.CHECK+" \n");
		strSQL.append("	AND b.nOfficeID = "+qdcwi.getOfficeID()+" \n");
		strSQL.append("	AND b.nCurrencyID = "+qdcwi.getCurrencyID()+" \n");
		strSQL.append("	AND b.nStatusID = "+SETTConstant.AccountStatus.NORMAL+" \n");
		strSQL.append("	AND b.nCheckStatusID = "+SETTConstant.AccountCheckStatus.CHECK+" \n");
		strSQL.append("	AND b.nAccountTypeID IN ("+DataFormat.getStringWithTagByLongArray(this.getAccountTypeCode())+") \n");
		strSQL.append("	AND b.nClientID = "+lClientID+" \n");
		if (qdcwi.getQueryDate() != null) {
			strSQL.append("	AND a.dtExecute = TO_DATE('"+
				DataFormat.getDateString(qdcwi.getQueryDate())+"','YYYY/MM/DD HH24:MI:SS') \n");
		}
		strSQL.append("	and a.nTransDirection = "+nDirection+" \n");
		log.info("SQL="+strSQL.toString());
		try {
			ps = conn.prepareStatement(strSQL.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				mDailyAmount = rs.getDouble("mAmount");
			}
		} catch (SQLException se) {
			throw new SQLException("取内部数据当日变动出错[queryInsideDailyAmount]:" + se.getMessage());
		} finally {
			this.cleanup(rs);
			this.cleanup(ps);
		}

		return mDailyAmount;
	}

	/**
	 * 查询内部客户本月累计数据
	 * @param  QueryDailyCapitalWhereInfo qdcwi, long nDirection, long lClientID, Connection conn
	 * @return double
	 * @exception throws SQLException
	 */
	private double queryInsideMonthAmount(QueryDailyCapitalWhereInfo qdcwi, long nDirection, long lClientID, Connection conn) throws SQLException {
		double mMonthAmount = 0.0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("SELECT NVL(SUM(a.mAmount),0.0) mAmount \n");
		strSQL.append("FROM sett_TransAccountDetail a,sett_Account b \n");
		strSQL.append("WHERE a.nTransAccountID = b.ID \n");
		strSQL.append("	AND a.nOfficeID = "+qdcwi.getOfficeID()+" \n");
		strSQL.append("	AND a.nCurrencyID = "+qdcwi.getCurrencyID()+" \n");
		strSQL.append("	AND a.nStatusID = "+SETTConstant.TransactionStatus.CHECK+" \n");
		strSQL.append("	AND b.nOfficeID = "+qdcwi.getOfficeID()+" \n");
		strSQL.append("	AND b.nCurrencyID = "+qdcwi.getCurrencyID()+" \n");
		strSQL.append("	AND b.nStatusID = "+SETTConstant.AccountStatus.NORMAL+" \n");
		strSQL.append("	AND b.nCheckStatusID = "+SETTConstant.AccountCheckStatus.CHECK+" \n");
		strSQL.append("	AND b.nAccountTypeID IN ("+DataFormat.getStringWithTagByLongArray(this.getAccountTypeCode())+") \n");
		strSQL.append("	AND b.nClientID = "+lClientID+" \n");
		//取得本月的最初一天和当日的前一天
		if (qdcwi.getQueryDate() != null) {
			String sFirstDay = DataFormat.getDateString(qdcwi.getQueryDate());
			sFirstDay = sFirstDay.substring(0, sFirstDay.length()-2)+"01";
			String sLastDay = DataFormat.getDateString(DataFormat.getPreviousDate(qdcwi.getQueryDate()));
			strSQL.append("	AND a.dtExecute >= TO_DATE('"+sFirstDay+"','YYYY/MM/DD HH24:MI:SS') \n");
			strSQL.append("	AND a.dtExecute <= TO_DATE('"+sLastDay+"','YYYY/MM/DD HH24:MI:SS') \n");
		}
		strSQL.append("	and a.nTransDirection = "+nDirection+" \n");
		log.info("SQL="+strSQL.toString());
		try {
			ps = conn.prepareStatement(strSQL.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				mMonthAmount = rs.getDouble("mAmount");
			}
		} catch (SQLException se) {
			throw new SQLException("取内部数据当月累计出错[queryInsideMonthAmount]:" + se.getMessage());
		} finally {
			this.cleanup(rs);
			this.cleanup(ps);
		}

		return mMonthAmount;
	}

	/**
	 * 查询内部客户本年累计数据
	 * @param  QueryDailyCapitalWhereInfo qdcwi, long nDirection, long lClientID, Connection conn
	 * @return double
	 * @exception throws SQLException
	 */
	private double queryInsideYearAmount(QueryDailyCapitalWhereInfo qdcwi, long nDirection, long lClientID, Connection conn) throws SQLException {
		double mMonthAmount = 0.0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("SELECT NVL(SUM(a.mAmount),0.0) mAmount \n");
		strSQL.append("FROM sett_TransAccountDetail a,sett_Account b \n");
		strSQL.append("WHERE a.nTransAccountID = b.ID \n");
		strSQL.append("	AND a.nOfficeID = "+qdcwi.getOfficeID()+" \n");
		strSQL.append("	AND a.nCurrencyID = "+qdcwi.getCurrencyID()+" \n");
		strSQL.append("	AND a.nStatusID = "+SETTConstant.TransactionStatus.CHECK+" \n");
		strSQL.append("	AND b.nOfficeID = "+qdcwi.getOfficeID()+" \n");
		strSQL.append("	AND b.nCurrencyID = "+qdcwi.getCurrencyID()+" \n");
		strSQL.append("	AND b.nStatusID = "+SETTConstant.AccountStatus.NORMAL+" \n");
		strSQL.append("	AND b.nCheckStatusID = "+SETTConstant.AccountCheckStatus.CHECK+" \n");
		strSQL.append("	AND b.nAccountTypeID IN ("+DataFormat.getStringWithTagByLongArray(this.getAccountTypeCode())+") \n");
		strSQL.append("	AND b.nClientID = "+lClientID+" \n");
		//取得本年的最初一天和当日的前一天
		if (qdcwi.getQueryDate() != null) {
			String sFirstDay = DataFormat.getDateString(qdcwi.getQueryDate());
			sFirstDay = sFirstDay.substring(0, 5) + "01-01";
			String sLastDay = DataFormat.getDateString(DataFormat.getPreviousDate(qdcwi.getQueryDate()));
			strSQL.append("	AND a.dtExecute >= TO_DATE('"+sFirstDay+"','YYYY/MM/DD HH24:MI:SS') \n");
			strSQL.append("	AND a.dtExecute <= TO_DATE('"+sLastDay+"','YYYY/MM/DD HH24:MI:SS') \n");
		}
		strSQL.append("	and a.nTransDirection = "+nDirection+" \n");
		log.info("SQL="+strSQL.toString());
		try {
			ps = conn.prepareStatement(strSQL.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				mMonthAmount = rs.getDouble("mAmount");
			}
		} catch (SQLException se) {
			throw new SQLException("取内部数据本年累计出错[queryInsideYearAmount]:" + se.getMessage());
		} finally {
			this.cleanup(rs);
			this.cleanup(ps);
		}

		return mMonthAmount;
	}

	/**
	 * 取当日上交
	 * @param  QueryDailyCapitalWhereInfo qdcwi, long lClientID, Connection conn
	 * @return double
	 * @exception SQLException
	 */	
	private double seekDailyHandIn(QueryDailyCapitalWhereInfo qdcwi, long lClientID, Connection conn) throws SQLException {
		return this.queryInsideDailyAmount(qdcwi, SETTConstant.DebitOrCredit.CREDIT, lClientID, conn);
	}

	/**
	 * 取当日支款
	 * @param  QueryDailyCapitalWhereInfo qdcwi, long lClientID, Connection conn
	 * @return double
	 * @exception SQLException
	 */
	private double seekDailyCost(QueryDailyCapitalWhereInfo qdcwi, long lClientID, Connection conn) throws SQLException {
		return this.queryInsideDailyAmount(qdcwi, SETTConstant.DebitOrCredit.DEBIT, lClientID, conn);
	}

	/**
	 * 取本月上交
	 * @param  QueryDailyCapitalWhereInfo qdcwi, long lClientID, Connection conn
	 * @return double
	 * @exception SQLException
	 */
	private double seekMonthHandIn(QueryDailyCapitalWhereInfo qdcwi, long lClientID, Connection conn) throws SQLException {
		return this.queryInsideMonthAmount(qdcwi, SETTConstant.DebitOrCredit.CREDIT, lClientID, conn);
	}

	/**
	 * 取本月支款
	 * @param  QueryDailyCapitalWhereInfo qdcwi, long lClientID, Connection conn
	 * @return double
	 * @exception SQLException
	 */
	private double seekMonthCost(QueryDailyCapitalWhereInfo qdcwi, long lClientID, Connection conn) throws SQLException {
		return this.queryInsideMonthAmount(qdcwi, SETTConstant.DebitOrCredit.DEBIT, lClientID, conn);
	}

	/**
	 * 取本年上交
	 * @param  QueryDailyCapitalWhereInfo qdcwi, long lClientID, Connection conn
	 * @return double
	 * @exception SQLException
	 */
	private double seekYearHandIn(QueryDailyCapitalWhereInfo qdcwi, long lClientID, Connection conn) throws SQLException {
		return this.queryInsideYearAmount(qdcwi, SETTConstant.DebitOrCredit.CREDIT, lClientID, conn);
	}

	/**
	 * 取本年支款
	 * @param  QueryDailyCapitalWhereInfo qdcwi, long lClientID, Connection conn
	 * @return double
	 * @exception SQLException
	 */
	private double seekYearCost(QueryDailyCapitalWhereInfo qdcwi, long lClientID, Connection conn) throws SQLException {
		return this.queryInsideYearAmount(qdcwi, SETTConstant.DebitOrCredit.DEBIT, lClientID, conn);
	}

	/** 取外部数据 */
	/**
	 * 查询外部数据表当日变动数据
	 * @param  QueryDailyCapitalWhereInfo qdcwi, long lReceivePayClientID, String sAccountNo, long nDirectionID, Connection conn
	 * @return double
	 * @exception SQLException
	 */
	private double queryExtDailyAmount(QueryDailyCapitalWhereInfo qdcwi, long lReceivePayClientID, long lClientID, long nDirectionID, Connection conn) throws SQLException {
		double dAmount = 0.0;
		PreparedStatement ps = null;
		ResultSet rs = null;

		StringBuffer strSQL = new StringBuffer();
		strSQL.append("SELECT SUM(mAmount) mAmount \n");
		strSQL.append("FROM sett_TransCurrentDeposit \n");
		strSQL.append("WHERE 1 = 1 \n");
		strSQL.append("	AND nOfficeID = "+qdcwi.getOfficeID()+" \n");
		strSQL.append("	AND nCurrencyID = "+qdcwi.getCurrencyID()+" \n");
		if (nDirectionID == CHECKRECEIVE) {
			strSQL.append("	AND nReceiveClientID = "+lReceivePayClientID+" \n");
			strSQL.append("	AND nTransActionTypeID = "+SETTConstant.TransactionType.SUBCLIENT_BANKRECEIVE+" \n");
		} else if (nDirectionID == CHECKPAY) {
			strSQL.append("	AND nPayClientID = "+lReceivePayClientID+" \n");
			strSQL.append("	AND nTransActionTypeID = "+SETTConstant.TransactionType.SUBCLIENT_BANKPAY+" \n");
		}
		strSQL.append("	AND nStatusID = "+SETTConstant.TransactionStatus.CHECK+" \n");
		//如果lClientID为-1时，没有下属单位这种说法
		if (lClientID != -1) {
			strSQL.append("	AND nSubClientID = "+lClientID+" \n");
		}
		if (qdcwi.getQueryDate() != null) {
			strSQL.append("	AND dtExecute = TO_DATE('"+
				DataFormat.getDateString(qdcwi.getQueryDate())+"','YYYY/MM/DD HH24:MI:SS') \n");
		}
		log.info("SQL="+strSQL.toString());
		try {
			ps = conn.prepareStatement(strSQL.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				dAmount = rs.getDouble("mAmount");
			}
		} catch (SQLException se) {
			throw new SQLException("取外部数据当日变动出错[queryExtDailyAmount]: "+se.getMessage());
		} finally {
			this.cleanup(rs);
			this.cleanup(ps);
		}

		return dAmount;
	}

	/**
	 * 查询外部数据表当月累计数据
	 * @param  QueryDailyCapitalWhereInfo qdcwi, long lReceivePayClientID, String sAccountNo, long nDirectionID, Connection conn
	 * @return double
	 * @exception throws SQLException
	 */ 
	private double queryExtMonthAmount(QueryDailyCapitalWhereInfo qdcwi, long lReceivePayClientID, long lClientID, long nDirectionID, Connection conn) throws SQLException {
		double dAmount = 0.0;
		PreparedStatement ps = null;
		ResultSet rs = null;

		StringBuffer strSQL = new StringBuffer();
		strSQL.append("SELECT SUM(mAmount) mAmount \n");
		strSQL.append("FROM sett_TransCurrentDeposit \n");
		strSQL.append("WHERE 1 = 1 \n");
		strSQL.append("	AND nOfficeID = "+qdcwi.getOfficeID()+" \n");
		strSQL.append("	AND nCurrencyID = "+qdcwi.getCurrencyID()+" \n");
		if (nDirectionID == CHECKRECEIVE) {
			strSQL.append("	AND nReceiveClientID = "+lReceivePayClientID+" \n");
			strSQL.append("	AND nTransActionTypeID = "+SETTConstant.TransactionType.SUBCLIENT_BANKRECEIVE+" \n");
		} else if (nDirectionID == CHECKPAY) {
			strSQL.append("	AND nPayClientID = "+lReceivePayClientID+" \n");
			strSQL.append("	AND nTransActionTypeID = "+SETTConstant.TransactionType.SUBCLIENT_BANKPAY+" \n");
		}
		strSQL.append("	AND nStatusID = "+SETTConstant.TransactionStatus.CHECK+" \n");
		//如果lClientID为-1时，没有下属单位这种说法
		if (lClientID != -1) {
			strSQL.append("	AND nSubClientID = "+lClientID+" \n");
		}
		//取得本月的最初一天和当日的前一天
		if (qdcwi.getQueryDate() != null) {
			String sFirstDay = DataFormat.getDateString(qdcwi.getQueryDate());
			sFirstDay = sFirstDay.substring(0, sFirstDay.length()-2)+"01";
			String sLastDay = DataFormat.getDateString(DataFormat.getPreviousDate(qdcwi.getQueryDate()));
			strSQL.append("	AND dtExecute >= TO_DATE('"+sFirstDay+"','YYYY/MM/DD HH24:MI:SS') \n");
			strSQL.append("	AND dtExecute <= TO_DATE('"+sLastDay+"','YYYY/MM/DD HH24:MI:SS') \n");
		}
		log.info("SQL="+strSQL.toString());
		try {
			ps = conn.prepareStatement(strSQL.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				dAmount = rs.getDouble("mAmount");
			}
		} catch (SQLException se) {
			throw new SQLException("取外部数据当月累计出错[queryExtMonthAmount]: " + se.getMessage());
		} finally {
			this.cleanup(rs);
			this.cleanup(ps);
		}

		return dAmount;
	}

	/**
	 * 查询外部数据表本年累计数据
	 * @param  QueryDailyCapitalWhereInfo qdcwi, long lReceivePayClientID, String sAccountNo, long nDirectionID, Connection conn
	 * @return double
	 * @exception throws SQLException
	 */ 
	private double queryExtYearAmount(QueryDailyCapitalWhereInfo qdcwi, long lReceivePayClientID, long lClientID, long nDirectionID, Connection conn) throws SQLException {
		double dAmount = 0.0;
		PreparedStatement ps = null;
		ResultSet rs = null;

		StringBuffer strSQL = new StringBuffer();
		strSQL.append("SELECT SUM(mAmount) mAmount \n");
		strSQL.append("FROM sett_TransCurrentDeposit \n");
		strSQL.append("WHERE 1 = 1 \n");
		strSQL.append("	AND nOfficeID = "+qdcwi.getOfficeID()+" \n");
		strSQL.append("	AND nCurrencyID = "+qdcwi.getCurrencyID()+" \n");
		if (nDirectionID == CHECKRECEIVE) {
			strSQL.append("	AND nReceiveClientID = "+lReceivePayClientID+" \n");
			strSQL.append("	AND nTransActionTypeID = "+SETTConstant.TransactionType.SUBCLIENT_BANKRECEIVE+" \n");
		} else if (nDirectionID == CHECKPAY) {
			strSQL.append("	AND nPayClientID = "+lReceivePayClientID+" \n");
			strSQL.append("	AND nTransActionTypeID = "+SETTConstant.TransactionType.SUBCLIENT_BANKPAY+" \n");
		}
		strSQL.append("	AND nStatusID = "+SETTConstant.TransactionStatus.CHECK+" \n");
		//如果lClientID为-1时，没有下属单位这种说法
		if (lClientID != -1) {
			strSQL.append("	AND nSubClientID = "+lClientID+" \n");
		}
		//取得本年的最初一天和当日的前一天
		if (qdcwi.getQueryDate() != null) {
			String sFirstDay = DataFormat.getDateString(qdcwi.getQueryDate());
			sFirstDay = sFirstDay.substring(0, 5) + "01-01";
			String sLastDay = DataFormat.getDateString(DataFormat.getPreviousDate(qdcwi.getQueryDate()));
			strSQL.append("	AND dtExecute >= TO_DATE('"+sFirstDay+"','YYYY/MM/DD HH24:MI:SS') \n");
			strSQL.append("	AND dtExecute <= TO_DATE('"+sLastDay+"','YYYY/MM/DD HH24:MI:SS') \n");
		}
		log.info("SQL="+strSQL.toString());
		try {
			ps = conn.prepareStatement(strSQL.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				dAmount = rs.getDouble("mAmount");
			}
		} catch (SQLException se) {
			throw new SQLException("取外部数据本年累计出错[queryExtYearAmount]: " + se.getMessage());
		} finally {
			this.cleanup(rs);
			this.cleanup(ps);
		}

		return dAmount;
	}

	/**
	 * 取当日上交
	 * @param  QueryDailyCapitalWhereInfo qdcwi, long lReceivePayClientID, long lClientID, Connection conn
	 * @return double
	 * @exception SQLException
	 */	
	private double seekExtDailyHandIn(QueryDailyCapitalWhereInfo qdcwi, long lReceivePayClientID, long lClientID, Connection conn) throws SQLException {
		return this.queryExtDailyAmount(qdcwi, lReceivePayClientID, lClientID, CHECKRECEIVE, conn);
	}

	/**
	 * 取当日支款
	 * @param  QueryDailyCapitalWhereInfo qdcwi, long lReceivePayClientID, long lClientID, Connection conn
	 * @return double
	 * @exception SQLException
	 */
	private double seekExtDailyCost(QueryDailyCapitalWhereInfo qdcwi, long lReceivePayClientID, long lClientID, Connection conn) throws SQLException {
		return this.queryExtDailyAmount(qdcwi, lReceivePayClientID, lClientID, CHECKPAY, conn);
	}

	/**
	 * 取本月上交
	 * @param  QueryDailyCapitalWhereInfo qdcwi, long lReceivePayClientID, long lClientID, Connection conn
	 * @return double
	 * @exception nothing
	 */
	private double seekExtMonthHandIn(QueryDailyCapitalWhereInfo qdcwi, long lReceivePayClientID, long lClientID, Connection conn) throws SQLException {
		return this.queryExtMonthAmount(qdcwi, lReceivePayClientID, lClientID, CHECKRECEIVE, conn);
	}

	/**
	 * 取本月支款
	 * @param  QueryDailyCapitalWhereInfo qdcwi, long lReceivePayClientID, long lClientID, Connection conn
	 * @return double
	 * @exception SQLException
	 */
	private double seekExtMonthCost(QueryDailyCapitalWhereInfo qdcwi, long lReceivePayClientID, long lClientID, Connection conn) throws SQLException {
		return this.queryExtMonthAmount(qdcwi, lReceivePayClientID, lClientID, CHECKPAY, conn);
	}

	/**
	 * 取本年上交
	 * @param  QueryDailyCapitalWhereInfo qdcwi, long lReceivePayClientID, long lClientID, Connection conn
	 * @return double
	 * @exception nothing
	 */
	private double seekExtYearHandIn(QueryDailyCapitalWhereInfo qdcwi, long lReceivePayClientID, long lClientID, Connection conn) throws SQLException {
		return this.queryExtYearAmount(qdcwi, lReceivePayClientID, lClientID, CHECKRECEIVE, conn);
	}

	/**
	 * 取本年支款
	 * @param  QueryDailyCapitalWhereInfo qdcwi, long lReceivePayClientID, long lClientID, Connection conn
	 * @return double
	 * @exception SQLException
	 */
	private double seekExtYearCost(QueryDailyCapitalWhereInfo qdcwi, long lReceivePayClientID, long lClientID, Connection conn) throws SQLException {
		return this.queryExtYearAmount(qdcwi, lReceivePayClientID, lClientID, CHECKPAY, conn);
	}

	/** 取本月预算 */
	/**
	 * 查询本月预算数据
	 * @param  QueryDailyCapitalWhereInfo qdcwi, QueryDailyCapitalWhereInfo qdcwi, String sAccountNo, Connection conn
	 * @return double[]
	 * @exception SQLException
	 */
	private double[] queryMonthBugetAmount(QueryDailyCapitalWhereInfo qdcwi, long lClientID, Connection conn) throws SQLException {
		double[] Double = new double[2];
		Double[0] = 0.00;
		Double[1] = 0.00;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("SELECT mDepositAmount,mWithdrawAmount \n");
		strSQL.append("FROM sett_MonthBudget \n");
		strSQL.append("WHERE 1 = 1 \n");
		strSQL.append("	AND nOfficeID = "+qdcwi.getOfficeID()+" \n");
		strSQL.append("	AND nCurrencyID = "+qdcwi.getCurrencyID()+" \n");
		strSQL.append("	AND nClientID = "+lClientID+" \n");
		if (qdcwi.getQueryDate() != null) {
			String queryDate = DataFormat.getDateString(qdcwi.getQueryDate());
			strSQL.append("	AND sYearMonth LIKE '"+queryDate.substring(0,4).concat(queryDate.substring(5,7))+"' \n");
		}
		strSQL.append("	AND nStatusID = "+SETTConstant.RecordStatus.VALID+" \n");
		log.info("SQL="+strSQL.toString());
		try {
			ps = conn.prepareStatement(strSQL.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				Double[0] = rs.getDouble("mDepositAmount");
				Double[1] = rs.getDouble("mWithdrawAmount");
			}
		} catch (SQLException se) {
			throw new SQLException("取本月预算数据出错[queryMonthBugetAmount]: "+se.getMessage());
		} finally {
			this.cleanup(rs);
			this.cleanup(ps);
		}

		return Double;
	}
//===========================================================================================================================

//===========================================================================================================================
	//***************************************************
	// 股份公司每日资金变动情况表
	//***************************************************
	/**
	 * 取全资电厂小计的结果集
	 * @param  QueryDailyCapitalWhereInfo qdcwi, Connection conn
	 * @return Collection
	 * @exception SQLException
	 */
	public Collection getCapitalElectricInfo(QueryDailyCapitalWhereInfo qdcwi, Connection conn) throws SQLException {
		ArrayList list = new ArrayList();
		Collection collection = this.getCapitalElectricClientID(qdcwi, conn);
		String[] sPartClientID = this.CheckNormalForClientID(qdcwi, SETTConstant.PartClientCode.CODE0003, conn);
		Iterator iterator = null;
		if (collection != null) {
			iterator = collection.iterator();
		}
		while (iterator != null && iterator.hasNext()) {
			String[] string = (String[]) iterator.next();
			QueryDailyCapitalInfo info = new QueryDailyCapitalInfo();
			//单位名称
			info.setName(string[1]);
			info.setSimpleName(string[3]);
			//当日变动-上交 1
			double dmDailyHandIn = this.seekExtDailyHandIn(qdcwi, Long.parseLong(sPartClientID[0]), Long.parseLong(string[0]), conn);
			info.setDailyHandIn(dmDailyHandIn);
			//当日变动-支款 2
			double dmDailyCost = this.seekExtDailyCost(qdcwi, Long.parseLong(sPartClientID[0]), Long.parseLong(string[0]), conn);
			info.setDailyCost(dmDailyCost);
			//本月累计-上交 3
			double dmMonthHandIn = this.seekExtMonthHandIn(qdcwi, Long.parseLong(sPartClientID[0]), Long.parseLong(string[0]), conn);
			dmMonthHandIn = dmMonthHandIn + dmDailyHandIn;
			info.setMonthHandIn(dmMonthHandIn);
			//本月累计-支款 4
			double dmMonthCost = this.seekExtMonthCost(qdcwi, Long.parseLong(sPartClientID[0]), Long.parseLong(string[0]), conn);
			dmMonthCost = dmMonthCost + dmDailyCost;
			info.setMonthCost(dmMonthCost);

			//取本月预算
			double[] dmMonthBuget = this.queryMonthBugetAmount(qdcwi, Long.parseLong(string[0]), conn);
			double dmMonthBudgetHandIn = dmMonthBuget[0];
			double dmMonthBudgetCost = dmMonthBuget[1];
			//本月预算-上交 5
			info.setMonthBudgetHandIn(dmMonthBudgetHandIn);
			//本月预算-支款 6
			info.setMonthBudgetCost(dmMonthBudgetCost);

			//与预算差额-上交 7
			double dmMarginHandIn = dmMonthHandIn - dmMonthBudgetHandIn;
			info.setMarginHandIn(dmMarginHandIn);
			//与预算差额-支款 8
			double dmMarginCost = dmMonthCost - dmMonthBudgetCost;
			info.setMarginCost(dmMarginCost);
			//月初余额 9
			double dmMonthBalance = 0.0;
			info.setMonthBalance(dmMonthBalance);
			//今日余额 10=9+3-4
			double dmTodayBalance = dmMonthBalance + dmMonthHandIn - dmMonthCost;
			info.setTodayBalance(dmTodayBalance);
			//备注
			info.setAbstract("");

			list.add(info);
		}

		return list != null && list.size() > 0?list:null;
	}

	/**
	 * 取全资电厂小计的结果集
	 * @param  QueryDailyCapitalWhereInfo qdcwi
	 * @return Collection
	 * @exception SQLException
	 */
	public Collection getCapitalElectricInfo(QueryDailyCapitalWhereInfo qdcwi) throws SQLException {
		return this.getCapitalElectricInfo(qdcwi, connection);
	}

	/**
	 * 取控股电厂小计的结果集
	 * @param  QueryDailyCapitalWhereInfo qdcwi, Connection conn
	 * @return Collection
	 * @exception SQLException
	 */
	public Collection getHoldingElectricInfo(QueryDailyCapitalWhereInfo qdcwi, Connection conn) throws SQLException {
		ArrayList list = new ArrayList();
		Collection collection = this.getHoldingElectricClientID(qdcwi, conn);
		Iterator iterator = null;
		if (collection != null) {
			iterator = collection.iterator();
		}
		while (iterator != null && iterator.hasNext()) {
			String[] string = (String[]) iterator.next();
			QueryDailyCapitalInfo info = new QueryDailyCapitalInfo();
			//单位名称
			info.setName(string[1]);
			//当日变动-上交 1
			double dmDailyHandIn = this.seekDailyHandIn(qdcwi, Long.parseLong(string[0]), conn);
			info.setDailyHandIn(dmDailyHandIn);
			//当日变动-支款 2
			double dmDailyCost = this.seekDailyCost(qdcwi, Long.parseLong(string[0]), conn);
			info.setDailyCost(dmDailyCost);
			//本月累计-上交 3
			double dmMonthHandIn = this.seekMonthHandIn(qdcwi, Long.parseLong(string[0]), conn);
			dmMonthHandIn = dmMonthHandIn + dmDailyHandIn;
			info.setMonthHandIn(dmMonthHandIn);
			//本月累计-支款 4
			double dmMonthCost = this.seekMonthCost(qdcwi, Long.parseLong(string[0]), conn);
			dmMonthCost = dmMonthCost + dmDailyCost;
			info.setMonthCost(dmMonthCost);

			//取本月预算
			double[] dmMonthBuget = this.queryMonthBugetAmount(qdcwi, Long.parseLong(string[0]), conn);
			double dmMonthBudgetHandIn = dmMonthBuget[0];
			double dmMonthBudgetCost = dmMonthBuget[1];
			//本月预算-上交 5
			info.setMonthBudgetHandIn(dmMonthBudgetHandIn);
			//本月预算-支款 6
			info.setMonthBudgetCost(dmMonthBudgetCost);

			//与预算差额-上交 7
			double dmMarginHandIn = dmMonthHandIn - dmMonthBudgetHandIn;
			info.setMarginHandIn(dmMarginHandIn);
			//与预算差额-支款 8
			double dmMarginCost = dmMonthCost - dmMonthBudgetCost;
			info.setMarginCost(dmMarginCost);
			//月初余额 9
			double dmMonthBalance = this.queryPreviousMonthBalance(qdcwi, Long.parseLong(string[0]), conn);
			info.setMonthBalance(dmMonthBalance);
			//今日余额 10=9+3-4
			double dmTodayBalance = dmMonthBalance + dmMonthHandIn - dmMonthCost;
			info.setTodayBalance(dmTodayBalance);
			//备注
			info.setAbstract("");

			list.add(info);
		}

		return list != null && list.size() > 0?list:null;
	}

	/**
	 * 取控股电厂小计的结果集
	 * @param  QueryDailyCapitalWhereInfo qdcwi
	 * @return Collection
	 * @exception SQLException
	 */
	public Collection getHoldingElectricInfo(QueryDailyCapitalWhereInfo qdcwi) throws SQLException {
		return this.getHoldingElectricInfo(qdcwi, connection);
	}

	/**
	 * 取股份公司的结果集
	 * @param  QueryDailyCapitalWhereInfo qdcwi, Connection conn
	 * @return Collection
	 * @exception SQLException
	 */
	public QueryDailyCapitalInfo getHoldingCompanyInfo(QueryDailyCapitalWhereInfo qdcwi, Connection conn) throws SQLException {
		String[] sPartClientID = this.CheckNormalForClientID(qdcwi, SETTConstant.PartClientCode.CODE0003, conn);
		QueryDailyCapitalInfo info = new QueryDailyCapitalInfo();
		//单位名称
		info.setName("本部支款");
		//当日变动-上交 1
		double dmDailyHandIn = 
			this.seekDailyHandIn(qdcwi, Long.parseLong(sPartClientID[0]), conn)-
			this.seekExtDailyHandIn(qdcwi, Long.parseLong(sPartClientID[0]), -1, conn);
		info.setDailyHandIn(dmDailyHandIn);
		//当日变动-支款 2
		double dmDailyCost = 
			this.seekDailyCost(qdcwi, Long.parseLong(sPartClientID[0]), conn)-
			this.seekExtDailyCost(qdcwi, Long.parseLong(sPartClientID[0]), -1, conn);
		info.setDailyCost(dmDailyCost);
		//本月累计-上交 3
		double dmMonthHandIn = 
			this.seekMonthHandIn(qdcwi, Long.parseLong(sPartClientID[0]), conn)-
			this.seekExtMonthHandIn(qdcwi, Long.parseLong(sPartClientID[0]), -1, conn);
		dmMonthHandIn = dmMonthHandIn + dmDailyHandIn;
		info.setMonthHandIn(dmMonthHandIn);
		//本月累计-支款 4
		double dmMonthCost = 
			this.seekMonthCost(qdcwi, Long.parseLong(sPartClientID[0]), conn)-
			this.seekExtMonthCost(qdcwi, Long.parseLong(sPartClientID[0]), -1, conn);
		dmMonthCost = dmMonthCost + dmDailyCost;
		info.setMonthCost(dmMonthCost);

		//取本月预算
		double[] dmMonthBuget = this.queryMonthBugetAmount(qdcwi, Long.parseLong(sPartClientID[0]), conn);
		double dmMonthBudgetHandIn = dmMonthBuget[0];
		double dmMonthBudgetCost = dmMonthBuget[1];
		//本月预算-上交 5
		info.setMonthBudgetHandIn(dmMonthBudgetHandIn);
		//本月预算-支款 6
		info.setMonthBudgetCost(dmMonthBudgetCost);

		//与预算差额-上交 7
		double dmMarginHandIn = dmMonthHandIn - dmMonthBudgetHandIn;
		info.setMarginHandIn(dmMarginHandIn);
		//与预算差额-支款 8
		double dmMarginCost = dmMonthCost - dmMonthBudgetCost;
		info.setMarginCost(dmMarginCost);
		//月初余额 9
		double dmMonthBalance = this.queryPreviousMonthBalance(qdcwi, Long.parseLong(sPartClientID[0]), conn);
		info.setMonthBalance(dmMonthBalance);
		//今日余额 10=9+3-4
		double dmTodayBalance = dmMonthBalance + dmMonthHandIn - dmMonthCost;
		info.setTodayBalance(dmTodayBalance);
		//备注
		info.setAbstract("");

		return info;
	}

	/**
	 * 取股份公司的结果集
	 * @param  QueryDailyCapitalWhereInfo qdcwi
	 * @return Collection
	 * @exception SQLException
	 */
	public QueryDailyCapitalInfo getHoldingCompanyInfo(QueryDailyCapitalWhereInfo qdcwi) throws SQLException {
		return this.getHoldingCompanyInfo(qdcwi, connection);
	}
//===========================================================================================================================

//===========================================================================================================================
	//***************************************************
	// 股份公司代管电厂每日资金变动表
	//***************************************************
	/**
	 * 取集团1记录的结果集
	 * @param  QueryDailyCapitalWhereInfo qdcwi, Connection conn
	 * @return Collection
	 * @exception SQLException
	 */
	public Collection getGroupOneInfo(QueryDailyCapitalWhereInfo qdcwi, Connection conn) throws SQLException {
		ArrayList list = new ArrayList();
		Collection collection = this.getGroupTotalClientID(qdcwi, conn);
		String[] sPartClientID = this.CheckNormalForClientID(qdcwi, SETTConstant.PartClientCode.CODE0021, conn);
		Iterator iterator = null;
		if (collection != null) {
			iterator = collection.iterator();
		}
		while (iterator != null && iterator.hasNext()) {
			String[] string = (String[]) iterator.next();
			QueryOuterAccountInfo info = new QueryOuterAccountInfo();
			// 序号
			info.setNo(++lIndex);
			// 存款单位
			info.setDepositCorp(string[1]);
			// 本月累计-存入
			double dmMonthHandIn = this.seekExtMonthHandIn(qdcwi, Long.parseLong(sPartClientID[0]), Long.parseLong(string[0]), conn);
			info.setMonthHandIn(dmMonthHandIn);
			// 本月累计-支取
			double dmMonthCost = this.seekExtMonthCost(qdcwi, Long.parseLong(sPartClientID[0]), Long.parseLong(string[0]), conn);
			info.setMonthCost(dmMonthCost);
			// 当日变动-存入
			double dmDailyHandIn = this.seekExtDailyHandIn(qdcwi, Long.parseLong(sPartClientID[0]), Long.parseLong(string[0]), conn);
			info.setDailyHandIn(dmDailyHandIn);
			// 当日变动-支取
			double dmDailyCost = this.seekExtDailyCost(qdcwi, Long.parseLong(sPartClientID[0]), Long.parseLong(string[0]), conn);
			info.setDailyCost(dmDailyCost);
			// 本月合计-存入
			double dmMonthSumHandIn = dmMonthHandIn + dmDailyHandIn;
			info.setMonthSumHandIn(dmMonthSumHandIn);
			// 本月合计-支取
			double dmMonthSumCost = dmMonthCost + dmDailyCost;
			info.setMonthSumCost(dmMonthSumCost);
			// 当前存款
			double dmNowDeposit = dmMonthSumHandIn - dmMonthSumCost;
			info.setNowDeposit(dmNowDeposit);
			// 本年累计-上交
			double dmYearHandIn = this.seekExtYearHandIn(qdcwi, Long.parseLong(sPartClientID[0]), Long.parseLong(string[0]), conn);
			info.setYearHandIn(dmYearHandIn);
			// 本年累计-拨款
			double dmYearCost = this.seekExtYearCost(qdcwi, Long.parseLong(sPartClientID[0]), Long.parseLong(string[0]), conn);;
			info.setYearCost(dmYearCost);
			// 本年合计-上交
			double dmYearSumHandIn = dmDailyHandIn + dmYearHandIn;
			info.setYearSumHandIn(dmYearSumHandIn);
			// 本年合计-拨款
			double dmYearSumCost = dmDailyCost + dmYearCost;
			info.setYearSumCost(dmYearSumCost);
			// 本年合计-差额
			double dmYearSumMargin = dmYearSumHandIn - dmYearSumCost;
			info.setYearSumMargin(dmYearSumMargin);
			// 备注
			info.setAbstract("");

			list.add(info);
		}

		return list != null && list.size() > 0?list:null;
	}

	/**
	 * 取集团1记录的结果集
	 * @param  QueryDailyCapitalWhereInfo qdcwi
	 * @return Collection
	 * @exception SQLException
	 */
	public Collection getGroupOneInfo(QueryDailyCapitalWhereInfo qdcwi) throws SQLException {
		return this.getGroupOneInfo(qdcwi, connection);
	}

	/**
	 * 取集团1合计的结果集
	 * @param  QueryDailyCapitalWhereInfo qdcwi, Connection conn
	 * @return QueryOuterAccountInfo
	 * @exception SQLException
	 */
	public QueryOuterAccountInfo getGroupOneCompanyInfo(QueryDailyCapitalWhereInfo qdcwi, Connection conn) throws SQLException {
		String[] sPartClientID = this.CheckNormalForClientID(qdcwi, SETTConstant.PartClientCode.CODE0021, conn);
		QueryOuterAccountInfo info = new QueryOuterAccountInfo();
		// 序号
		//info.setNo(++lIndex);
		// 存款单位
		info.setDepositCorp("集团1合计");
		//昨日余额
		double dmYesterdayBalance = this.queryYesterdayBalance(qdcwi, Long.parseLong(sPartClientID[0]), conn);
		info.setYesterdayBalance(dmYesterdayBalance);
		//今日余额
		double dmTodayBalance = this.queryTodayBalance(qdcwi, Long.parseLong(sPartClientID[0]), conn);
		info.setTodayBalance(dmTodayBalance);
		//集团1支款
		double dmDailyCost = 
			this.seekDailyCost(qdcwi, Long.parseLong(sPartClientID[0]), conn)-
			this.seekExtDailyCost(qdcwi, Long.parseLong(sPartClientID[0]), -1, conn);
		info.setDailyCost(dmDailyCost);
		//集团1收款
		double dmDailyHandIn = 
			this.seekDailyHandIn(qdcwi, Long.parseLong(sPartClientID[0]), conn)-
			this.seekExtDailyHandIn(qdcwi, Long.parseLong(sPartClientID[0]), -1, conn);
		info.setDailyHandIn(dmDailyHandIn);

		return info;
	}

	/**
	 * 取集团1合计的结果集
	 * @param  QueryDailyCapitalWhereInfo qdcwi
	 * @return QueryOuterAccountInfo
	 * @exception SQLException
	 */
	public QueryOuterAccountInfo getGroupOneCompanyInfo(QueryDailyCapitalWhereInfo qdcwi) throws SQLException {
		return this.getGroupOneCompanyInfo(qdcwi, connection);
	}

	/**
	 * 取开发1记录的结果集
	 * @param  QueryDailyCapitalWhereInfo qdcwi, Connection conn
	 * @return Collection
	 * @exception SQLException
	 */
	public Collection getEmpolderOneInfo(QueryDailyCapitalWhereInfo qdcwi, Connection conn) throws SQLException {
		ArrayList list = new ArrayList();
		Collection collection = this.getEmpolderTotalClientID(qdcwi, conn);
		String[] sPartClientID = this.CheckNormalForClientID(qdcwi, SETTConstant.PartClientCode.CODE0020, conn);
		Iterator iterator = null;
		if (collection != null) {
			iterator = collection.iterator();
		}
		while (iterator != null && iterator.hasNext()) {
			String[] string = (String[]) iterator.next();
			QueryOuterAccountInfo info = new QueryOuterAccountInfo();
			// 序号
			info.setNo(++lIndex);
			// 存款单位
			info.setDepositCorp(string[1]);
			// 本月累计-存入
			double dmMonthHandIn = this.seekExtMonthHandIn(qdcwi, Long.parseLong(sPartClientID[0]), Long.parseLong(string[0]), conn);
			info.setMonthHandIn(dmMonthHandIn);
			// 本月累计-支取
			double dmMonthCost = this.seekExtMonthCost(qdcwi, Long.parseLong(sPartClientID[0]), Long.parseLong(string[0]), conn);
			info.setMonthCost(dmMonthCost);
			// 当日变动-存入
			double dmDailyHandIn = this.seekExtDailyHandIn(qdcwi, Long.parseLong(sPartClientID[0]), Long.parseLong(string[0]), conn);
			info.setDailyHandIn(dmDailyHandIn);
			// 当日变动-支取
			double dmDailyCost = this.seekExtDailyCost(qdcwi, Long.parseLong(sPartClientID[0]), Long.parseLong(string[0]), conn);
			info.setDailyCost(dmDailyCost);
			// 本月合计-存入
			double dmMonthSumHandIn = dmMonthHandIn + dmDailyHandIn;
			info.setMonthSumHandIn(dmMonthSumHandIn);
			// 本月合计-支取
			double dmMonthSumCost = dmMonthCost + dmDailyCost;
			info.setMonthSumCost(dmMonthSumCost);
			// 当前存款
			double dmNowDeposit = dmMonthSumHandIn - dmMonthSumCost;
			info.setNowDeposit(dmNowDeposit);
			// 本年累计-上交
			double dmYearHandIn = this.seekExtYearHandIn(qdcwi, Long.parseLong(sPartClientID[0]), Long.parseLong(string[0]), conn);
			info.setYearHandIn(dmYearHandIn);
			// 本年累计-拨款
			double dmYearCost = this.seekExtYearCost(qdcwi, Long.parseLong(sPartClientID[0]), Long.parseLong(string[0]), conn);;
			info.setYearCost(dmYearCost);
			// 本年合计-上交
			double dmYearSumHandIn = dmDailyHandIn + dmYearHandIn;
			info.setYearSumHandIn(dmYearSumHandIn);
			// 本年合计-拨款
			double dmYearSumCost = dmDailyCost + dmYearCost;
			info.setYearSumCost(dmYearSumCost);
			// 本年合计-差额
			double dmYearSumMargin = dmYearSumHandIn - dmYearSumCost;
			info.setYearSumMargin(dmYearSumMargin);
			// 备注
			info.setAbstract("");

			list.add(info);
		}

		return list != null && list.size() > 0?list:null;
	}

	/**
	 * 取开发1记录的结果集
	 * @param  QueryDailyCapitalWhereInfo qdcwi
	 * @return Collection
	 * @exception SQLException
	 */
	public Collection getEmpolderOneInfo(QueryDailyCapitalWhereInfo qdcwi) throws SQLException {
		return this.getEmpolderOneInfo(qdcwi, connection);
	}

	/**
	 * 取开发1合计的结果集
	 * @param  QueryDailyCapitalWhereInfo qdcwi, Connection conn
	 * @return QueryOuterAccountInfo
	 * @exception SQLException
	 */
	public QueryOuterAccountInfo getEmpolderOneCompanyInfo(QueryDailyCapitalWhereInfo qdcwi, Connection conn) throws SQLException {
		String[] sPartClientID = this.CheckNormalForClientID(qdcwi, SETTConstant.PartClientCode.CODE0020, conn);
		QueryOuterAccountInfo info = new QueryOuterAccountInfo();
		// 序号
		//info.setNo(++lIndex);
		// 存款单位
		info.setDepositCorp("开发1合计");
		//昨日余额
		double dmYesterdayBalance = this.queryYesterdayBalance(qdcwi, Long.parseLong(sPartClientID[0]), conn);
		info.setYesterdayBalance(dmYesterdayBalance);
		//今日余额
		double dmTodayBalance = this.queryTodayBalance(qdcwi, Long.parseLong(sPartClientID[0]), conn);
		info.setTodayBalance(dmTodayBalance);
		//集团1支款
		double dmDailyCost = 
			this.seekDailyCost(qdcwi, Long.parseLong(sPartClientID[0]), conn)-
			this.seekExtDailyCost(qdcwi, Long.parseLong(sPartClientID[0]), -1, conn);
		info.setDailyCost(dmDailyCost);
		//集团1收款
		double dmDailyHandIn = 
			this.seekDailyHandIn(qdcwi, Long.parseLong(sPartClientID[0]), conn)-
			this.seekExtDailyHandIn(qdcwi, Long.parseLong(sPartClientID[0]), -1, conn);
		info.setDailyHandIn(dmDailyHandIn);

		return info;
	}

	/**
	 * 取开发1合计的结果集
	 * @param  QueryDailyCapitalWhereInfo qdcwi
	 * @return QueryOuterAccountInfo
	 * @exception SQLException
	 */
	public QueryOuterAccountInfo getEmpolderOneCompanyInfo(QueryDailyCapitalWhereInfo qdcwi) throws SQLException {
		return this.getEmpolderOneCompanyInfo(qdcwi, connection);
	}

	/**
	 * 取集团电厂合计记录的结果集
	 * @param  QueryDailyCapitalWhereInfo qdcwi, Connection conn
	 * @return Collection
	 * @exception SQLException
	 */
	public Collection getGroupElectricInfo(QueryDailyCapitalWhereInfo qdcwi, Connection conn) throws SQLException {
		ArrayList list = new ArrayList();
		Collection collection = this.getGroupElectricClientID(qdcwi, conn);
		Iterator iterator = null;
		if (collection != null) {
			iterator = collection.iterator();
		}
		while (iterator != null && iterator.hasNext()) {
			String[] string = (String[]) iterator.next();
			QueryInnerAccountInfo info = new QueryInnerAccountInfo();
			// 序号
			info.setNo(++lIndex);
			// 存款单位
			info.setDepositCorp(string[1]);
			// 本月累计-存入1
			double dmMonthHandIn = this.seekMonthHandIn(qdcwi, Long.parseLong(string[0]), conn);
			info.setMonthHandIn(dmMonthHandIn);
			// 本月累计-支取2
			double dmMonthCost = this.seekMonthCost(qdcwi, Long.parseLong(string[0]), conn);
			info.setMonthCost(dmMonthCost);
			// 当日变动-存入3
			double dmDailyHandIn = this.seekDailyHandIn(qdcwi, Long.parseLong(string[0]), conn);
			info.setDailyHandIn(dmDailyHandIn);
			// 当日变动-支取4
			double dmDailyCost = this.seekDailyCost(qdcwi, Long.parseLong(string[0]), conn);
			info.setDailyCost(dmDailyCost);
			// 本月合计-存入5=1+3
			double dmMonthSumHandIn = dmMonthHandIn + dmDailyHandIn;
			info.setMonthSumHandIn(dmMonthSumHandIn);
			// 本月合计-支取6=2+4
			double dmMonthSumCost = dmMonthCost + dmDailyCost;
			info.setMonthSumCost(dmMonthSumCost);
			// 当前存款7=5-6
			double dmNowDeposit = dmMonthSumHandIn - dmMonthSumCost;
			info.setNowDeposit(dmNowDeposit);
			// 上月余额8
			double dmPreMonthBalance = this.queryPreviousMonthBalance(qdcwi, Long.parseLong(string[0]), conn);
			info.setPreMonthBalance(dmPreMonthBalance);
			// 当日余额9=7+8
			double dmNowDayBalance = dmNowDeposit + dmPreMonthBalance;
			info.setNowDayBalance(dmNowDayBalance);

			//取本月预算
			double[] dmMonthBuget = this.queryMonthBugetAmount(qdcwi, Long.parseLong(string[0]), conn);
			double dmMonthBudgetHandIn = dmMonthBuget[0];
			double dmMonthBudgetCost = dmMonthBuget[1];
			//本月预算-上交 10
			info.setNowMonthHandIn(dmMonthBudgetHandIn);
			//本月预算-支款 11
			info.setNowMonthCost(dmMonthBudgetCost);

			//与预算差额-上交 12=5-10
			double dmMarginHandIn = dmMonthSumHandIn - dmMonthBudgetHandIn;
			info.setMarginHandIn(dmMarginHandIn);
			//与预算差额-支款 13=6-11
			double dmMarginCost = dmMonthSumCost - dmMonthBudgetCost;
			info.setMarginCost(dmMarginCost);

			list.add(info);
		}

		return list != null && list.size() > 0?list:null;
	}

	/**
	 * 取集团电厂合计记录的结果集
	 * @param  QueryDailyCapitalWhereInfo qdcwi
	 * @return Collection
	 * @exception SQLException
	 */
	public Collection getGroupElectricInfo(QueryDailyCapitalWhereInfo qdcwi) throws SQLException {
		return this.getGroupElectricInfo(qdcwi, connection);
	}

	/**
	 * 取开发电厂合计记录的结果集
	 * @param  QueryDailyCapitalWhereInfo qdcwi, Connection conn
	 * @return Collection
	 * @exception SQLException
	 */
	public Collection getEmpolderElectricInfo(QueryDailyCapitalWhereInfo qdcwi, Connection conn) throws SQLException {
		ArrayList list = new ArrayList();
		Collection collection = this.getEmpolderElectricClientID(qdcwi, conn);
		Iterator iterator = null;
		if (collection != null) {
			iterator = collection.iterator();
		}
		while (iterator != null && iterator.hasNext()) {
			String[] string = (String[]) iterator.next();
			QueryInnerAccountInfo info = new QueryInnerAccountInfo();
			// 序号
			info.setNo(++lIndex);
			// 存款单位
			info.setDepositCorp(string[1]);
			// 本月累计-存入1
			double dmMonthHandIn = this.seekMonthHandIn(qdcwi, Long.parseLong(string[0]), conn);
			info.setMonthHandIn(dmMonthHandIn);
			// 本月累计-支取2
			double dmMonthCost = this.seekMonthCost(qdcwi, Long.parseLong(string[0]), conn);
			info.setMonthCost(dmMonthCost);
			// 当日变动-存入3
			double dmDailyHandIn = this.seekDailyHandIn(qdcwi, Long.parseLong(string[0]), conn);
			info.setDailyHandIn(dmDailyHandIn);
			// 当日变动-支取4
			double dmDailyCost = this.seekDailyCost(qdcwi, Long.parseLong(string[0]), conn);
			info.setDailyCost(dmDailyCost);
			// 本月合计-存入5=1+3
			double dmMonthSumHandIn = dmMonthHandIn + dmDailyHandIn;
			info.setMonthSumHandIn(dmMonthSumHandIn);
			// 本月合计-支取6=2+4
			double dmMonthSumCost = dmMonthCost + dmDailyCost;
			info.setMonthSumCost(dmMonthSumCost);
			// 当前存款7=5-6
			double dmNowDeposit = dmMonthSumHandIn - dmMonthSumCost;
			info.setNowDeposit(dmNowDeposit);
			// 上月余额8
			double dmPreMonthBalance = this.queryPreviousMonthBalance(qdcwi, Long.parseLong(string[0]), conn);
			info.setPreMonthBalance(dmPreMonthBalance);
			// 当日余额9=7+8
			double dmNowDayBalance = dmNowDeposit + dmPreMonthBalance;
			info.setNowDayBalance(dmNowDayBalance);

			//取本月预算
			double[] dmMonthBuget = this.queryMonthBugetAmount(qdcwi, Long.parseLong(string[0]), conn);
			double dmMonthBudgetHandIn = dmMonthBuget[0];
			double dmMonthBudgetCost = dmMonthBuget[1];
			//本月预算-上交 10
			info.setNowMonthHandIn(dmMonthBudgetHandIn);
			//本月预算-支款 11
			info.setNowMonthCost(dmMonthBudgetCost);

			//与预算差额-上交 12=5-10
			double dmMarginHandIn = dmMonthSumHandIn - dmMonthBudgetHandIn;
			info.setMarginHandIn(dmMarginHandIn);
			//与预算差额-支款 13=6-11
			double dmMarginCost = dmMonthSumCost - dmMonthBudgetCost;
			info.setMarginCost(dmMarginCost);

			list.add(info);
		}

		return list != null && list.size() > 0?list:null;
	}

	/**
	 * 取开发电厂合计记录的结果集
	 * @param  QueryDailyCapitalWhereInfo qdcwi
	 * @return Collection
	 * @exception SQLException
	 */
	public Collection getEmpolderElectricInfo(QueryDailyCapitalWhereInfo qdcwi) throws SQLException {
		return this.getEmpolderElectricInfo(qdcwi, connection);
	}
//===========================================================================================================================

//===========================================================================================================================
	//***************************************************
	// 华能集团公司及下属电厂存款每日变动情况表
	//***************************************************
	/**
	 * 取华能集团公司的结果集
	 * @param  QueryDailyCapitalWhereInfo qdcwi, Connection conn
	 * @return QueryInnerAccountInfo
	 * @exception SQLException
	 */
	public QueryInnerAccountInfo getGroupCompanyInfo(QueryDailyCapitalWhereInfo qdcwi, Connection conn) throws SQLException {
		String[] sPartClientID = this.CheckNormalForClientID(qdcwi, SETTConstant.PartClientCode.CODE0001, conn);
		QueryInnerAccountInfo info = new QueryInnerAccountInfo();
		// 序号
		info.setNo(++lIndex);
		// 存款单位
		info.setDepositCorp(sPartClientID[1]);
		// 本月累计-存入1
		double dmMonthHandIn = 
			this.seekMonthHandIn(qdcwi, Long.parseLong(sPartClientID[0]), conn)-
			this.seekExtMonthHandIn(qdcwi, Long.parseLong(sPartClientID[0]), -1, conn);
		info.setMonthHandIn(dmMonthHandIn);
		// 本月累计-支取2
		double dmMonthCost = 
			this.seekMonthCost(qdcwi, Long.parseLong(sPartClientID[0]), conn)-
			this.seekExtMonthCost(qdcwi, Long.parseLong(sPartClientID[0]), -1, conn);
		info.setMonthCost(dmMonthCost);
		// 当日变动-存入3
		double dmDailyHandIn = 
			this.seekDailyHandIn(qdcwi, Long.parseLong(sPartClientID[0]), conn)-
			this.seekExtDailyHandIn(qdcwi, Long.parseLong(sPartClientID[0]), -1, conn);;
		info.setDailyHandIn(dmDailyHandIn);
		// 当日变动-支取4
		double dmDailyCost = 
			this.seekDailyCost(qdcwi, Long.parseLong(sPartClientID[0]), conn)-
			this.seekExtDailyCost(qdcwi, Long.parseLong(sPartClientID[0]), -1, conn);
		info.setDailyCost(dmDailyCost);
		// 本月合计-存入5=1+3
		double dmMonthSumHandIn = dmMonthHandIn + dmDailyHandIn;
		info.setMonthSumHandIn(dmMonthSumHandIn);
		// 本月合计-支取6=2+4
		double dmMonthSumCost = dmMonthCost + dmDailyCost;
		info.setMonthSumCost(dmMonthSumCost);
		// 当前存款7=5-6
		double dmNowDeposit = dmMonthSumHandIn - dmMonthSumCost;
		info.setNowDeposit(dmNowDeposit);
		// 上月余额8
		double dmPreMonthBalance = this.queryPreviousMonthBalance(qdcwi, Long.parseLong(sPartClientID[0]), conn);
		info.setPreMonthBalance(dmPreMonthBalance);
		// 当日余额9=7+8
		double dmNowDayBalance = dmNowDeposit + dmPreMonthBalance;
		info.setNowDayBalance(dmNowDayBalance);

		//取本月预算
		double[] dmMonthBuget = this.queryMonthBugetAmount(qdcwi, Long.parseLong(sPartClientID[0]), conn);
		double dmMonthBudgetHandIn = dmMonthBuget[0];
		double dmMonthBudgetCost = dmMonthBuget[1];
		//本月预算-上交 10
		info.setNowMonthHandIn(dmMonthBudgetHandIn);
		//本月预算-支款 11
		info.setNowMonthCost(dmMonthBudgetCost);

		//与预算差额-上交 12=5-10
		double dmMarginHandIn = dmMonthSumHandIn - dmMonthBudgetHandIn;
		info.setMarginHandIn(dmMarginHandIn);
		//与预算差额-支款 13=6-11
		double dmMarginCost = dmMonthSumCost - dmMonthBudgetCost;
		info.setMarginCost(dmMarginCost);

		return info;
	}

	/**
	 * 取华能集团公司的结果集
	 * @param  QueryDailyCapitalWhereInfo qdcwi
	 * @return QueryInnerAccountInfo
	 * @exception SQLException
	 */
	public QueryInnerAccountInfo getGroupCompanyInfo(QueryDailyCapitalWhereInfo qdcwi) throws SQLException {
		return this.getGroupCompanyInfo(qdcwi, connection);
	}

	/**
	 * 取集团全资电厂记录的结果集
	 * @param  QueryDailyCapitalWhereInfo qdcwi, Connection conn
	 * @return Collection
	 * @exception SQLException
	 */
	public Collection getGroupCapitalElectricInfo(QueryDailyCapitalWhereInfo qdcwi, Connection conn) throws SQLException {
		ArrayList list = new ArrayList();
		Collection collection = this.getGroupCapitalClientID(qdcwi, conn);
		String[] sPartClientID = this.CheckNormalForClientID(qdcwi, SETTConstant.PartClientCode.CODE0001, conn);
		Iterator iterator = null;
		if (collection != null) {
			iterator = collection.iterator();
		}
		while (iterator != null && iterator.hasNext()) {
			String[] string = (String[]) iterator.next();
			QueryInnerAccountInfo info = new QueryInnerAccountInfo();
			// 序号
			//info.setNo(++lIndex);
			// 存款单位
			info.setDepositCorp(string[1]);
			// 本月累计-存入1
			double dmMonthHandIn = this.seekExtMonthHandIn(qdcwi, Long.parseLong(sPartClientID[0]), Long.parseLong(string[0]), conn);
			info.setMonthHandIn(dmMonthHandIn);
			// 本月累计-支取2
			double dmMonthCost = this.seekExtMonthCost(qdcwi, Long.parseLong(sPartClientID[0]), Long.parseLong(string[0]), conn);
			info.setMonthCost(dmMonthCost);
			// 当日变动-存入3
			double dmDailyHandIn = this.seekExtDailyHandIn(qdcwi, Long.parseLong(sPartClientID[0]), Long.parseLong(string[0]), conn);
			info.setDailyHandIn(dmDailyHandIn);
			// 当日变动-支取4
			double dmDailyCost = this.seekExtDailyCost(qdcwi, Long.parseLong(sPartClientID[0]), Long.parseLong(string[0]), conn);
			info.setDailyCost(dmDailyCost);
			// 本月合计-存入5=1+3
			double dmMonthSumHandIn = dmMonthHandIn + dmDailyHandIn;
			info.setMonthSumHandIn(dmMonthSumHandIn);
			// 本月合计-支取6=2+4
			double dmMonthSumCost = dmMonthCost + dmDailyCost;
			info.setMonthSumCost(dmMonthSumCost);
			// 当前存款7=5-6
			double dmNowDeposit = dmMonthSumHandIn - dmMonthSumCost;
			info.setNowDeposit(dmNowDeposit);
			// 上月余额8
			double dmPreMonthBalance = 0.0;
			info.setPreMonthBalance(dmPreMonthBalance);
			// 当日余额9=7+8
			double dmNowDayBalance = dmNowDeposit + dmPreMonthBalance;
			info.setNowDayBalance(dmNowDayBalance);

			//取本月预算
			double[] dmMonthBuget = this.queryMonthBugetAmount(qdcwi, Long.parseLong(string[0]), conn);
			double dmMonthBudgetHandIn = dmMonthBuget[0];
			double dmMonthBudgetCost = dmMonthBuget[1];
			//本月预算-上交 10
			info.setNowMonthHandIn(dmMonthBudgetHandIn);
			//本月预算-支款 11
			info.setNowMonthCost(dmMonthBudgetCost);

			//与预算差额-上交 12=5-10
			double dmMarginHandIn = dmMonthSumHandIn - dmMonthBudgetHandIn;
			info.setMarginHandIn(dmMarginHandIn);
			//与预算差额-支款 13=6-11
			double dmMarginCost = dmMonthSumCost - dmMonthBudgetCost;
			info.setMarginCost(dmMarginCost);

			list.add(info);
		}

		return list != null && list.size() > 0?list:null;
	}

	/**
	 * 取集团全资电厂记录的结果集
	 * @param  QueryDailyCapitalWhereInfo qdcwi
	 * @return Collection
	 * @exception SQLException
	 */
	public Collection getGroupCapitalElectricInfo(QueryDailyCapitalWhereInfo qdcwi) throws SQLException {
		return this.getGroupCapitalElectricInfo(qdcwi, connection);
	}

	/**
	 * 取华能国际电力开发公司的结果集
	 * @param  QueryDailyCapitalWhereInfo qdcwi, Connection conn
	 * @return QueryInnerAccountInfo
	 * @exception SQLException
	 */
	public QueryInnerAccountInfo getEmpolderCompanyInfo(QueryDailyCapitalWhereInfo qdcwi, Connection conn) throws SQLException {
		String[] sPartClientID = this.CheckNormalForClientID(qdcwi, SETTConstant.PartClientCode.CODE0002, conn);
		QueryInnerAccountInfo info = new QueryInnerAccountInfo();
		// 序号
		info.setNo(++lIndex);
		// 存款单位
		info.setDepositCorp(sPartClientID[1]);
		// 本月累计-存入1
		double dmMonthHandIn = 
			this.seekMonthHandIn(qdcwi, Long.parseLong(sPartClientID[0]), conn)-
			this.seekExtMonthHandIn(qdcwi, Long.parseLong(sPartClientID[0]), -1, conn);
		info.setMonthHandIn(dmMonthHandIn);
		// 本月累计-支取2
		double dmMonthCost = 
			this.seekMonthCost(qdcwi, Long.parseLong(sPartClientID[0]), conn)-
			this.seekExtMonthCost(qdcwi, Long.parseLong(sPartClientID[0]), -1, conn);
		info.setMonthCost(dmMonthCost);
		// 当日变动-存入3
		double dmDailyHandIn = 
			this.seekDailyHandIn(qdcwi, Long.parseLong(sPartClientID[0]), conn)-
			this.seekExtDailyHandIn(qdcwi, Long.parseLong(sPartClientID[0]), -1, conn);
		info.setDailyHandIn(dmDailyHandIn);
		// 当日变动-支取4
		double dmDailyCost = 
			this.seekDailyCost(qdcwi, Long.parseLong(sPartClientID[0]), conn)-
			this.seekExtDailyCost(qdcwi, Long.parseLong(sPartClientID[0]), -1, conn);
		info.setDailyCost(dmDailyCost);
		// 本月合计-存入5=1+3
		double dmMonthSumHandIn = dmMonthHandIn + dmDailyHandIn;
		info.setMonthSumHandIn(dmMonthSumHandIn);
		// 本月合计-支取6=2+4
		double dmMonthSumCost = dmMonthCost + dmDailyCost;
		info.setMonthSumCost(dmMonthSumCost);
		// 当前存款7=5-6
		double dmNowDeposit = dmMonthSumHandIn - dmMonthSumCost;
		info.setNowDeposit(dmNowDeposit);
		// 上月余额8
		double dmPreMonthBalance = this.queryPreviousMonthBalance(qdcwi, Long.parseLong(sPartClientID[0]), conn);
		info.setPreMonthBalance(dmPreMonthBalance);
		// 当日余额9=7+8
		double dmNowDayBalance = dmNowDeposit + dmPreMonthBalance;
		info.setNowDayBalance(dmNowDayBalance);

		//取本月预算
		double[] dmMonthBuget = this.queryMonthBugetAmount(qdcwi, Long.parseLong(sPartClientID[0]), conn);
		double dmMonthBudgetHandIn = dmMonthBuget[0];
		double dmMonthBudgetCost = dmMonthBuget[1];
		//本月预算-上交 10
		info.setNowMonthHandIn(dmMonthBudgetHandIn);
		//本月预算-支款 11
		info.setNowMonthCost(dmMonthBudgetCost);

		//与预算差额-上交 12=5-10
		double dmMarginHandIn = dmMonthSumHandIn - dmMonthBudgetHandIn;
		info.setMarginHandIn(dmMarginHandIn);
		//与预算差额-支款 13=6-11
		double dmMarginCost = dmMonthSumCost - dmMonthBudgetCost;
		info.setMarginCost(dmMarginCost);

		return info;
	}

	/**
	 * 取华能国际电力开发公司的结果集
	 * @param  QueryDailyCapitalWhereInfo qdcwi
	 * @return QueryInnerAccountInfo
	 * @exception SQLException
	 */
	public QueryInnerAccountInfo getEmpolderCompanyInfo(QueryDailyCapitalWhereInfo qdcwi) throws SQLException {
		return this.getEmpolderCompanyInfo(qdcwi, connection);
	}

	/**
	 * 取开发全资电厂记录的结果集
	 * @param  QueryDailyCapitalWhereInfo qdcwi, Connection conn
	 * @return Collection
	 * @exception SQLException
	 */
	public Collection getEmpolderCapitalElectricInfo(QueryDailyCapitalWhereInfo qdcwi, Connection conn) throws SQLException {
		ArrayList list = new ArrayList();
		Collection collection = this.getEmpolderCapitalClientID(qdcwi, conn);
		String[] sPartClientID = this.CheckNormalForClientID(qdcwi, SETTConstant.PartClientCode.CODE0002, conn);
		Iterator iterator = null;
		if (collection != null) {
			iterator = collection.iterator();
		}
		while (iterator != null && iterator.hasNext()) {
			String[] string = (String[]) iterator.next();
			QueryInnerAccountInfo info = new QueryInnerAccountInfo();
			// 序号
			//info.setNo(++lIndex);
			// 存款单位
			info.setDepositCorp(string[1]);
			// 本月累计-存入1
			double dmMonthHandIn = this.seekExtMonthHandIn(qdcwi, Long.parseLong(sPartClientID[0]), Long.parseLong(string[0]), conn);
			info.setMonthHandIn(dmMonthHandIn);
			// 本月累计-支取2
			double dmMonthCost = this.seekExtMonthCost(qdcwi, Long.parseLong(sPartClientID[0]), Long.parseLong(string[0]), conn);
			info.setMonthCost(dmMonthCost);
			// 当日变动-存入3
			double dmDailyHandIn = this.seekExtDailyHandIn(qdcwi, Long.parseLong(sPartClientID[0]), Long.parseLong(string[0]), conn);
			info.setDailyHandIn(dmDailyHandIn);
			// 当日变动-支取4
			double dmDailyCost = this.seekExtDailyCost(qdcwi, Long.parseLong(sPartClientID[0]), Long.parseLong(string[0]), conn);
			info.setDailyCost(dmDailyCost);
			// 本月合计-存入5=1+3
			double dmMonthSumHandIn = dmMonthHandIn + dmDailyHandIn;
			info.setMonthSumHandIn(dmMonthSumHandIn);
			// 本月合计-支取6=2+4
			double dmMonthSumCost = dmMonthCost + dmDailyCost;
			info.setMonthSumCost(dmMonthSumCost);
			// 当前存款7=5-6
			double dmNowDeposit = dmMonthSumHandIn - dmMonthSumCost;
			info.setNowDeposit(dmNowDeposit);
			// 上月余额8
			double dmPreMonthBalance = 0.0;
			info.setPreMonthBalance(dmPreMonthBalance);
			// 当日余额9=7+8
			double dmNowDayBalance = dmNowDeposit + dmPreMonthBalance;
			info.setNowDayBalance(dmNowDayBalance);

			//取本月预算
			double[] dmMonthBuget = this.queryMonthBugetAmount(qdcwi, Long.parseLong(string[0]), conn);
			double dmMonthBudgetHandIn = dmMonthBuget[0];
			double dmMonthBudgetCost = dmMonthBuget[1];
			//本月预算-上交 10
			info.setNowMonthHandIn(dmMonthBudgetHandIn);
			//本月预算-支款 11
			info.setNowMonthCost(dmMonthBudgetCost);

			//与预算差额-上交 12=5-10
			double dmMarginHandIn = dmMonthSumHandIn - dmMonthBudgetHandIn;
			info.setMarginHandIn(dmMarginHandIn);
			//与预算差额-支款 13=6-11
			double dmMarginCost = dmMonthSumCost - dmMonthBudgetCost;
			info.setMarginCost(dmMarginCost);

			list.add(info);
		}

		return list != null && list.size() > 0?list:null;
	}

	/**
	 * 取开发全资电厂记录的结果集
	 * @param  QueryDailyCapitalWhereInfo qdcwi
	 * @return Collection
	 * @exception SQLException
	 */
	public Collection getEmpolderCapitalElectricInfo(QueryDailyCapitalWhereInfo qdcwi) throws SQLException {
		return this.getEmpolderCapitalElectricInfo(qdcwi, connection);
	}

	/**
	 * 取集团控股电厂记录的结果集
	 * @param  QueryDailyCapitalWhereInfo qdcwi, Connection conn
	 * @return Collection
	 * @exception SQLException
	 */
	public Collection getGroupHoldingElectricInfo(QueryDailyCapitalWhereInfo qdcwi, Connection conn) throws SQLException {
		ArrayList list = new ArrayList();
		Collection collection = this.getGroupHoldingClientID(qdcwi, conn);
		Iterator iterator = null;
		if (collection != null) {
			iterator = collection.iterator();
		}
		while (iterator != null && iterator.hasNext()) {
			String[] string = (String[]) iterator.next();
			QueryInnerAccountInfo info = new QueryInnerAccountInfo();
			// 序号
			info.setNo(++lIndex);
			// 存款单位
			info.setDepositCorp(string[1]);
			// 本月累计-存入1
			double dmMonthHandIn = this.seekMonthHandIn(qdcwi, Long.parseLong(string[0]), conn);
			info.setMonthHandIn(dmMonthHandIn);
			// 本月累计-支取2
			double dmMonthCost = this.seekMonthCost(qdcwi, Long.parseLong(string[0]), conn);
			info.setMonthCost(dmMonthCost);
			// 当日变动-存入3
			double dmDailyHandIn = this.seekDailyHandIn(qdcwi, Long.parseLong(string[0]), conn);
			info.setDailyHandIn(dmDailyHandIn);
			// 当日变动-支取4
			double dmDailyCost = this.seekDailyCost(qdcwi, Long.parseLong(string[0]), conn);
			info.setDailyCost(dmDailyCost);
			// 本月合计-存入5=1+3
			double dmMonthSumHandIn = dmMonthHandIn + dmDailyHandIn;
			info.setMonthSumHandIn(dmMonthSumHandIn);
			// 本月合计-支取6=2+4
			double dmMonthSumCost = dmMonthCost + dmDailyCost;
			info.setMonthSumCost(dmMonthSumCost);
			// 当前存款7=5-6
			double dmNowDeposit = dmMonthSumHandIn - dmMonthSumCost;
			info.setNowDeposit(dmNowDeposit);
			// 上月余额8
			double dmPreMonthBalance = this.queryPreviousMonthBalance(qdcwi, Long.parseLong(string[0]), conn);
			info.setPreMonthBalance(dmPreMonthBalance);
			// 当日余额9=7+8
			double dmNowDayBalance = dmNowDeposit + dmPreMonthBalance;
			info.setNowDayBalance(dmNowDayBalance);

			//取本月预算
			double[] dmMonthBuget = this.queryMonthBugetAmount(qdcwi, Long.parseLong(string[0]), conn);
			double dmMonthBudgetHandIn = dmMonthBuget[0];
			double dmMonthBudgetCost = dmMonthBuget[1];
			//本月预算-上交 10
			info.setNowMonthHandIn(dmMonthBudgetHandIn);
			//本月预算-支款 11
			info.setNowMonthCost(dmMonthBudgetCost);

			//与预算差额-上交 12=5-10
			double dmMarginHandIn = dmMonthSumHandIn - dmMonthBudgetHandIn;
			info.setMarginHandIn(dmMarginHandIn);
			//与预算差额-支款 13=6-11
			double dmMarginCost = dmMonthSumCost - dmMonthBudgetCost;
			info.setMarginCost(dmMarginCost);

			list.add(info);
		}

		return list != null && list.size() > 0?list:null;
	}

	/**
	 * 取集团控股电厂记录的结果集
	 * @param  QueryDailyCapitalWhereInfo qdcwi
	 * @return Collection
	 * @exception SQLException
	 */
	public Collection getGroupHoldingElectricInfo(QueryDailyCapitalWhereInfo qdcwi) throws SQLException {
		return this.getGroupHoldingElectricInfo(qdcwi, connection);
	}

	/**
	 * 取开发控股电厂记录的结果集
	 * @param  QueryDailyCapitalWhereInfo qdcwi, Connection conn
	 * @return Collection
	 * @exception SQLException
	 */
	public Collection getEmpolderHoldingElectricInfo(QueryDailyCapitalWhereInfo qdcwi, Connection conn) throws SQLException {
		ArrayList list = new ArrayList();
		Collection collection = this.getEmpolderHoldingClientID(qdcwi, conn);
		Iterator iterator = null;
		if (collection != null) {
			iterator = collection.iterator();
		}
		while (iterator != null && iterator.hasNext()) {
			String[] string = (String[]) iterator.next();
			QueryInnerAccountInfo info = new QueryInnerAccountInfo();
			// 序号
			info.setNo(++lIndex);
			// 存款单位
			info.setDepositCorp(string[1]);
			// 本月累计-存入1
			double dmMonthHandIn = this.seekMonthHandIn(qdcwi, Long.parseLong(string[0]), conn);
			info.setMonthHandIn(dmMonthHandIn);
			// 本月累计-支取2
			double dmMonthCost = this.seekMonthCost(qdcwi, Long.parseLong(string[0]), conn);
			info.setMonthCost(dmMonthCost);
			// 当日变动-存入3
			double dmDailyHandIn = this.seekDailyHandIn(qdcwi, Long.parseLong(string[0]), conn);
			info.setDailyHandIn(dmDailyHandIn);
			// 当日变动-支取4
			double dmDailyCost = this.seekDailyCost(qdcwi, Long.parseLong(string[0]), conn);
			info.setDailyCost(dmDailyCost);
			// 本月合计-存入5=1+3
			double dmMonthSumHandIn = dmMonthHandIn + dmDailyHandIn;
			info.setMonthSumHandIn(dmMonthSumHandIn);
			// 本月合计-支取6=2+4
			double dmMonthSumCost = dmMonthCost + dmDailyCost;
			info.setMonthSumCost(dmMonthSumCost);
			// 当前存款7=5-6
			double dmNowDeposit = dmMonthSumHandIn - dmMonthSumCost;
			info.setNowDeposit(dmNowDeposit);
			// 上月余额8
			double dmPreMonthBalance = this.queryPreviousMonthBalance(qdcwi, Long.parseLong(string[0]), conn);
			info.setPreMonthBalance(dmPreMonthBalance);
			// 当日余额9=7+8
			double dmNowDayBalance = dmNowDeposit + dmPreMonthBalance;
			info.setNowDayBalance(dmNowDayBalance);

			//取本月预算
			double[] dmMonthBuget = this.queryMonthBugetAmount(qdcwi, Long.parseLong(string[0]), conn);
			double dmMonthBudgetHandIn = dmMonthBuget[0];
			double dmMonthBudgetCost = dmMonthBuget[1];
			//本月预算-上交 10
			info.setNowMonthHandIn(dmMonthBudgetHandIn);
			//本月预算-支款 11
			info.setNowMonthCost(dmMonthBudgetCost);

			//与预算差额-上交 12=5-10
			double dmMarginHandIn = dmMonthSumHandIn - dmMonthBudgetHandIn;
			info.setMarginHandIn(dmMarginHandIn);
			//与预算差额-支款 13=6-11
			double dmMarginCost = dmMonthSumCost - dmMonthBudgetCost;
			info.setMarginCost(dmMarginCost);

			list.add(info);
		}

		return list != null && list.size() > 0?list:null;
	}

	/**
	 * 取开发控股电厂记录的结果集
	 * @param  QueryDailyCapitalWhereInfo qdcwi
	 * @return Collection
	 * @exception SQLException
	 */
	public Collection getEmpolderHoldingElectricInfo(QueryDailyCapitalWhereInfo qdcwi) throws SQLException {
		return this.getEmpolderHoldingElectricInfo(qdcwi, connection);
	}
//===========================================================================================================================

//===========================================================================================================================
	//***************************************************
	// 人民币存款周报
	//***************************************************
	/**
	 * 查询内部客户上周存款余额数据
	 * @param  QueryDailyCapitalWhereInfo qdcwi, long lClientID, Connection conn
	 * @return double
	 * @exception throws SQLException
	 */
	private double queryPreviousWeeklyBalance(QueryDailyCapitalWhereInfo qdcwi, long lClientID, Connection conn) throws SQLException {
		double mBalance = 0.0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("SELECT ROUND(SUM(NVL(a.mBalance,0.0))/10000,0) mBalance \n");
		strSQL.append("FROM sett_DailyAccountBalance a,sett_Account b \n");
		strSQL.append("WHERE a.nAccountID = b.ID \n");
		strSQL.append("	AND b.nOfficeID = "+qdcwi.getOfficeID()+" \n");
		strSQL.append("	AND b.nCurrencyID = "+qdcwi.getCurrencyID()+" \n");
		strSQL.append("	AND b.nStatusID = "+SETTConstant.AccountStatus.NORMAL+" \n");
		strSQL.append("	AND b.nCheckStatusID = "+SETTConstant.AccountCheckStatus.CHECK+" \n");
		strSQL.append("	AND b.nAccountTypeID IN ("+DataFormat.getStringWithTagByLongArray(this.getAccountTypeCode())+") \n");
		strSQL.append("	AND b.nClientID = "+lClientID+" \n");
		strSQL.append("	AND a.dtDate = TO_DATE('"+DataFormat.getDateString(DataFormat.getPreviousDate(qdcwi.getDateFrom()))+"','YYYY/MM/DD HH24:MI:SS') \n");
		log.info("SQL="+strSQL.toString());
		try {
			ps = conn.prepareStatement(strSQL.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				mBalance = rs.getDouble("mBalance");
			}
		} catch (SQLException se) {
			throw new SQLException("取内部数据上周存款余额出错[queryPreviousWeeklyBalance]:" + se.getMessage());
		} finally {
			this.cleanup(rs);
			this.cleanup(ps);
		}

		return mBalance;
	}

	/**
	 * 查询内部客户本周存款余额数据通过账户类型
	 * @param  QueryDailyCapitalWhereInfo qdcwi, long lClientID, long lAccountTypeID, Connection conn
	 * @return double
	 * @exception throws SQLException
	 */
	private double queryDailyAccountBalance(QueryDailyCapitalWhereInfo qdcwi, long lClientID, long lAccountTypeID, Connection conn) throws SQLException {
		double mBalance = 0.0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("SELECT ROUND(SUM(NVL(a.mBalance,0.0))/10000,0) mBalance \n");
		strSQL.append("FROM sett_DailyAccountBalance a,sett_Account b \n");
		strSQL.append("WHERE a.nAccountID = b.ID \n");
		strSQL.append("	AND b.nOfficeID = "+qdcwi.getOfficeID()+" \n");
		strSQL.append("	AND b.nCurrencyID = "+qdcwi.getCurrencyID()+" \n");
		strSQL.append("	AND b.nStatusID = "+SETTConstant.AccountStatus.NORMAL+" \n");
		strSQL.append("	AND b.nCheckStatusID = "+SETTConstant.AccountCheckStatus.CHECK+" \n");
		strSQL.append("	AND b.nAccountTypeID IN ("+lAccountTypeID+") \n");
		strSQL.append("	AND b.nClientID = "+lClientID+" \n");
		strSQL.append("	AND a.dtDate = TO_DATE('"+DataFormat.getDateString(qdcwi.getDateTo())+"','YYYY/MM/DD HH24:MI:SS') \n");

		log.info("SQL="+strSQL.toString());
		try {
			ps = conn.prepareStatement(strSQL.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				mBalance = rs.getDouble("mBalance");
			}
		} catch (SQLException se) {
			throw new SQLException("取内部客户本周存款余额数据通过账户类型出错[queryCurrentWeeklyBalance]:" + se.getMessage());
		} finally {
			this.cleanup(rs);
			this.cleanup(ps);
		}

		return mBalance;
	}

	/**
	 * 查询内部客户本周存款余额数据通过账户类型
	 * @param  QueryDailyCapitalWhereInfo qdcwi, long lClientID, long lAccountTypeID, Connection conn
	 * @return double
	 * @exception throws SQLException
	 */
	private double querySubAccountBalance(QueryDailyCapitalWhereInfo qdcwi, long lClientID, long lAccountGroupID, Connection conn) throws SQLException {
		double mBalance = 0.0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("SELECT ROUND(SUM(NVL(a.mBalance,0.0))/10000,0) mBalance \n");
		strSQL.append("FROM sett_SubAccount a,sett_Account b,sett_accountType c \n");
		strSQL.append("WHERE a.nAccountID = b.ID \n");
		strSQL.append("	AND b.nOfficeID = "+qdcwi.getOfficeID()+" \n");
		strSQL.append("	AND b.nCurrencyID = "+qdcwi.getCurrencyID()+" \n");
		strSQL.append("	AND b.nStatusID = "+SETTConstant.AccountStatus.NORMAL+" \n");
		strSQL.append("	AND b.nCheckStatusID = "+SETTConstant.AccountCheckStatus.CHECK+" \n");
		strSQL.append("	AND b.nAccountTypeID=c.id and c.nAccountGroupID = "+lAccountGroupID+" \n");
		strSQL.append("	AND b.nClientID = "+lClientID+" \n");
		strSQL.append("	AND a.nStatusID = "+SETTConstant.SubAccountStatus.NORMAL);
		//strSQL.append("	AND a.dtDate = TO_DATE('"+DataFormat.getDateString(qdcwi.getDateTo())+"','YYYY/MM/DD HH24:MI:SS') \n");

		log.info("SQL="+strSQL.toString());
		try {
			ps = conn.prepareStatement(strSQL.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				mBalance = rs.getDouble("mBalance");
			}
		} catch (SQLException se) {
			throw new SQLException("取内部客户本周存款余额数据通过账户类型出错[queryCurrentWeeklyBalance]:" + se.getMessage());
		} finally {
			this.cleanup(rs);
			this.cleanup(ps);
		}

		return mBalance;
	}

	/**
	 * 查询内部客户本周存款余额数据通过账户类型
	 * @param  QueryDailyCapitalWhereInfo qdcwi, long lClientID, long lAccountTypeID, Connection conn
	 * @return double
	 * @exception throws SQLException
	 */
	private double queryCurrentWeeklyBalance(QueryDailyCapitalWhereInfo qdcwi, long lClientID, long lAccountTypeID, Connection conn) throws SQLException {
		if (this.isToday(qdcwi.getOfficeID(), qdcwi.getCurrencyID(), qdcwi.getDateTo())) {
			return this.querySubAccountBalance(qdcwi, lClientID, lAccountTypeID, conn);
		} else {
			return this.queryDailyAccountBalance(qdcwi, lClientID, lAccountTypeID, conn);
		}
	}

	/**
	 * 查询内部客户本周的交易数据通过交易类型
	 * @param  QueryDailyCapitalWhereInfo qdcwi, Connection conn, long lClientID, long lAccountTypeID
	 * @return double
	 * @exception throws SQLException
	 */
	private double queryInsideWeeklyAmount(QueryDailyCapitalWhereInfo qdcwi, Connection conn, long lClientID, long lAccountTypeID) throws SQLException {
		double mMonthAmount = 0.0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer strSQL = new StringBuffer();
		//strSQL.append("SELECT NVL(SUM(a.mAmount),0.0) mAmount \n");
		strSQL.append("SELECT ROUND(SUM(DECODE(a.nTransDirection, \n");
		strSQL.append("	"+SETTConstant.DebitOrCredit.CREDIT+",NVL(a.mAmount,0.00), \n");
		strSQL.append("	"+SETTConstant.DebitOrCredit.DEBIT+",-NVL(a.mAmount,0.00)))/10000,0) mAmount \n");
		strSQL.append("FROM sett_TransAccountDetail a,sett_Account b \n");
		strSQL.append("WHERE a.nTransAccountID = b.ID \n");
		strSQL.append("	AND a.nOfficeID = "+qdcwi.getOfficeID()+" \n");
		strSQL.append("	AND a.nCurrencyID = "+qdcwi.getCurrencyID()+" \n");
		strSQL.append("	AND a.nStatusID = "+SETTConstant.TransactionStatus.CHECK+" \n");
		strSQL.append("	AND b.nOfficeID = "+qdcwi.getOfficeID()+" \n");
		strSQL.append("	AND b.nCurrencyID = "+qdcwi.getCurrencyID()+" \n");
		strSQL.append("	AND b.nStatusID = "+SETTConstant.AccountStatus.NORMAL+" \n");
		strSQL.append("	AND b.nCheckStatusID = "+SETTConstant.AccountCheckStatus.CHECK+" \n");
		strSQL.append("	AND b.nAccountTypeID IN ("+lAccountTypeID+") \n");
		strSQL.append("	AND b.nClientID = "+lClientID+" \n");
		//取得本周的最初一天和最后一天
		if (qdcwi.getDateFrom() != null && qdcwi.getDateTo() != null) {
			strSQL.append("	AND a.dtExecute >= TO_DATE('"+DataFormat.getDateString(qdcwi.getDateFrom())+"','YYYY/MM/DD HH24:MI:SS') \n");
			strSQL.append("	AND a.dtExecute <= TO_DATE('"+DataFormat.getDateString(qdcwi.getDateTo())+"','YYYY/MM/DD HH24:MI:SS') \n");
		}
		log.info("SQL="+strSQL.toString());
		try {
			ps = conn.prepareStatement(strSQL.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				mMonthAmount = rs.getDouble("mAmount");
			}
		} catch (SQLException se) {
			throw new SQLException("取内部客户结束日当天的交易数据出错[queryDateToAmount]:" + se.getMessage());
		} finally {
			this.cleanup(rs);
			this.cleanup(ps);
		}

		return mMonthAmount;
	}

	/**
	 * 查询内部客户本周累计数据
	 * @param  QueryDailyCapitalWhereInfo qdcwi, long nDirection, long lClientID, Connection conn
	 * @return double
	 * @exception throws SQLException
	 */
	private double queryInsideWeeklyAmount(QueryDailyCapitalWhereInfo qdcwi, long nDirection, long lClientID, Connection conn) throws SQLException {
		double mMonthAmount = 0.0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("SELECT ROUND(SUM(NVL(a.mAmount,0.0))/10000,0) mAmount \n");
		strSQL.append("FROM sett_TransAccountDetail a,sett_Account b \n");
		strSQL.append("WHERE a.nTransAccountID = b.ID \n");
		strSQL.append("	AND a.nOfficeID = "+qdcwi.getOfficeID()+" \n");
		strSQL.append("	AND a.nCurrencyID = "+qdcwi.getCurrencyID()+" \n");
		strSQL.append("	AND a.nStatusID = "+SETTConstant.TransactionStatus.CHECK+" \n");
		strSQL.append("	AND b.nOfficeID = "+qdcwi.getOfficeID()+" \n");
		strSQL.append("	AND b.nCurrencyID = "+qdcwi.getCurrencyID()+" \n");
		strSQL.append("	AND b.nStatusID = "+SETTConstant.AccountStatus.NORMAL+" \n");
		strSQL.append("	AND b.nCheckStatusID = "+SETTConstant.AccountCheckStatus.CHECK+" \n");
		strSQL.append("	AND b.nAccountTypeID IN ("+DataFormat.getStringWithTagByLongArray(this.getAccountTypeCode())+") \n");
		strSQL.append("	AND b.nClientID = "+lClientID+" \n");
		//取得本周的最初一天和最后一天
		if (qdcwi.getDateFrom() != null && qdcwi.getDateTo() != null) {
			strSQL.append("	AND a.dtExecute >= TO_DATE('"+DataFormat.getDateString(qdcwi.getDateFrom())+"','YYYY/MM/DD HH24:MI:SS') \n");
			strSQL.append("	AND a.dtExecute <= TO_DATE('"+DataFormat.getDateString(qdcwi.getDateTo())+"','YYYY/MM/DD HH24:MI:SS') \n");
		}
		strSQL.append("	and a.nTransDirection = "+nDirection+" \n");
		log.info("SQL="+strSQL.toString());
		try {
			ps = conn.prepareStatement(strSQL.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				mMonthAmount = rs.getDouble("mAmount");
			}
		} catch (SQLException se) {
			throw new SQLException("取内部数据本周累计出错[queryInsideMonthAmount]:" + se.getMessage());
		} finally {
			this.cleanup(rs);
			this.cleanup(ps);
		}

		return mMonthAmount;
	}

	/**
	 * 取本周上交
	 * @param  QueryDailyCapitalWhereInfo qdcwi, long lClientID, Connection conn
	 * @return double
	 * @exception SQLException
	 */
	private double seekWeeklyHandIn(QueryDailyCapitalWhereInfo qdcwi, long lClientID, Connection conn) throws SQLException {
		return this.queryInsideWeeklyAmount(qdcwi, SETTConstant.DebitOrCredit.CREDIT, lClientID, conn);
	}

	/**
	 * 取本周支款
	 * @param  QueryDailyCapitalWhereInfo qdcwi, long lClientID, Connection conn
	 * @return double
	 * @exception SQLException
	 */
	private double seekWeeklyCost(QueryDailyCapitalWhereInfo qdcwi, long lClientID, Connection conn) throws SQLException {
		return this.queryInsideWeeklyAmount(qdcwi, SETTConstant.DebitOrCredit.DEBIT, lClientID, conn);
	}

	/**
	 * 取内部客户集本周交易金额数据
	 * @param  QueryDailyCapitalWhereInfo qdcwi, Connection conn, long lDirectionID
	 * @return double
	 * @exception throws SQLException
	 */
	private double queryInsideClientAllWeeklyAmount(QueryDailyCapitalWhereInfo qdcwi, Connection conn, long lDirectionID) throws SQLException {
		double dAmount = 0.0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("SELECT ROUND(SUM(NVL(mAmount,0.0))/10000,0) mAmount \n");
		strSQL.append("FROM sett_GlEntry \n");
		strSQL.append("WHERE 1 = 1 \n");
		strSQL.append("	AND (SUBSTR(sSubjectCode,0,18) = '001.000.2120010000' \n");
		strSQL.append("	OR SUBSTR(sSubjectCode,0,18) = '001.000.2120020000' \n");
		strSQL.append("	OR SUBSTR(sSubjectCode,0,18) = '001.000.2130000000') \n");
		strSQL.append("	AND nTransDirection = "+lDirectionID+" \n");
		strSQL.append("	AND nStatusID = 3 \n");
		strSQL.append("	AND nOfficeID = "+qdcwi.getOfficeID()+" \n");
		//strSQL.append("	AND nCurrencyID = "+qdcwi.getCurrencyID()+" \n");
		strSQL.append("	AND dtExecute >= TO_DATE('"+DataFormat.getDateString(qdcwi.getDateFrom())+"','YYYY/MM/DD HH24:MI:SS') \n");
		strSQL.append("	AND dtExecute <= TO_DATE('"+DataFormat.getDateString(qdcwi.getDateTo())+"','YYYY/MM/DD HH24:MI:SS') \n");
		log.info("SQL="+strSQL.toString());
		try {
			ps = conn.prepareStatement(strSQL.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				dAmount = rs.getDouble("mAmount");
			}
		} catch (SQLException se) {
			throw new SQLException("取内部客户集本周交易金额出错[queryInsideClientAllWeeklyAmount]:" + se.getMessage());
		} finally {
			this.cleanup(rs);
			this.cleanup(ps);
		}

		return dAmount;
	}

	/**
	 * 取内部客户集上周余额数据
	 * @param  QueryDailyCapitalWhereInfo qdcwi, Connection conn
	 * @return double
	 * @exception throws SQLException
	 */
	private double queryInsideClientUpWeeklyBalance(QueryDailyCapitalWhereInfo qdcwi, Connection conn) throws SQLException {
		double dBalance = 0.0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("SELECT ROUND(SUM(DECODE(nBalanceDirection, \n");
		strSQL.append("	"+SETTConstant.DebitOrCredit.CREDIT+",-(NVL(mDebitBalance,0.0)+NVL(mCreditBalance,0.0))/10000, \n");
		strSQL.append("	"+SETTConstant.DebitOrCredit.DEBIT+",(NVL(mDebitBalance,0.0)+NVL(mCreditBalance,0.0))/10000)),0) mBalance \n");
		strSQL.append("FROM  sett_GlBalance \n");
		strSQL.append("WHERE 1 = 1 \n");
		strSQL.append("	AND (SUBSTR(sGlSubjectCode,0,18) = '001.000.2120010000' \n");
		strSQL.append("	OR SUBSTR(sGlSubjectCode,0,18) = '001.000.2120020000' \n");
		strSQL.append("	OR SUBSTR(sGlSubjectCode,0,18) = '001.000.2130000000') \n");
		strSQL.append("	AND nOfficeID = "+qdcwi.getOfficeID()+" \n");
		//strSQL.append("	AND nCurrencyID = "+qdcwi.getCurrencyID()+" \n");
		strSQL.append("	AND dtGlDate = TO_DATE('"+DataFormat.getDateString(
			DataFormat.getPreviousDate(qdcwi.getDateFrom()))+"','YYYY/MM/DD HH24:MI:SS') \n");
		log.info("SQL="+strSQL.toString());
		try {
			ps = conn.prepareStatement(strSQL.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				dBalance = rs.getDouble("mBalance");
			}
		} catch (SQLException se) {
			throw new SQLException("取内部客户集上周余额出错[queryInsideClientUpWeeklyBalance]:" + se.getMessage());
		} finally {
			this.cleanup(rs);
			this.cleanup(ps);
		}

		return dBalance;
	}

	/**
	 * 取内部客户集本周余额数据
	 * @param  QueryDailyCapitalWhereInfo qdcwi, Connection conn, long lAccountTypeID
	 * @return double
	 * @exception throws SQLException
	 */
	private double queryInsideClientThisWeeklyBalance(QueryDailyCapitalWhereInfo qdcwi, Connection conn, long lAccountTypeID) throws SQLException {
		double dBalance = 0.0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("SELECT ROUND(SUM(DECODE(nBalanceDirection, \n");
		strSQL.append("	"+SETTConstant.DebitOrCredit.CREDIT+",-(NVL(mDebitBalance,0.0)+NVL(mCreditBalance,0.0))/10000, \n");
		strSQL.append("	"+SETTConstant.DebitOrCredit.DEBIT+",(NVL(mDebitBalance,0.0)+NVL(mCreditBalance,0.0))/10000)),0) mBalance \n");
		strSQL.append("FROM  sett_GlBalance \n");
		strSQL.append("WHERE 1 = 1 \n");
		if (SETTConstant.AccountType.isCurrentAccountType(lAccountTypeID )
		        ||SETTConstant.AccountType.isOtherDepositAccountType(lAccountTypeID)) {
			strSQL.append("	AND SUBSTR(sGlSubjectCode,0,18) = '001.000.2120010000' \n");
		} else if (SETTConstant.AccountType.isFixAccountType(lAccountTypeID )) {
			strSQL.append("	AND SUBSTR(sGlSubjectCode,0,18) = '001.000.2130000000' \n");
		} else if (SETTConstant.AccountType.isNotifyAccountType(lAccountTypeID )) {
			strSQL.append("	AND SUBSTR(sGlSubjectCode,0,18) = '001.000.2120020000' \n");
		}
		strSQL.append("	AND nOfficeID = "+qdcwi.getOfficeID()+" \n");
		//strSQL.append("	AND nCurrencyID = "+qdcwi.getCurrencyID()+" \n");
		strSQL.append("	AND dtGlDate = TO_DATE('"+DataFormat.getDateString(qdcwi.getDateTo())+"','YYYY/MM/DD HH24:MI:SS') \n");
		log.info("SQL="+strSQL.toString());
		try {
			ps = conn.prepareStatement(strSQL.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				dBalance = rs.getDouble("mBalance");
			}
		} catch (SQLException se) {
			throw new SQLException("取内部客户集本周余额出错[queryInsideClientThisWeeklyBalance]:" + se.getMessage());
		} finally {
			this.cleanup(rs);
			this.cleanup(ps);
		}

		return dBalance;
	}

	/**
	 * 取全部指定公司数据-成员单位合计
	 * @param  QueryDailyCapitalWhereInfo qdcwi, Connection conn
	 * @return Collection
	 * @exception throws SQLException
	 */
	public Collection getCurrencyDepositWeeklyInfo(QueryDailyCapitalWhereInfo qdcwi, Connection conn) throws SQLException {
		//TODO TODELETE
		/*
		ArrayList list = new ArrayList();
		String[] sPartClientCode = SETTConstant.PartClientCode.getAllCode();
		for (int i = 0; i < sPartClientCode.length; i++) {
			String[] string = this.CheckNormalForClientID(qdcwi, sPartClientCode[i], conn);
			if ("0".equals(string[0])) {
				continue;
			}
			QCurrencyDepositInfo info = new QCurrencyDepositInfo();
			// 序号
			if (!string[2].equalsIgnoreCase(SETTConstant.PartClientCode.CODE0020) && 
				!string[2].equalsIgnoreCase(SETTConstant.PartClientCode.CODE0021)) {
				info.setNo(++lIndex);
			}
			// 存款单位
			info.setDepositCorp(string[1]);
			// 上周存款余额
			info.setPreWeeklyBalance(this.queryPreviousWeeklyBalance(qdcwi, Long.parseLong(string[0]), conn));
			// 本周-存入
			info.setWeeklyHandIn(this.seekWeeklyHandIn(qdcwi, Long.parseLong(string[0]), conn));
			// 本周-支取
			info.setWeeklyCost(this.seekWeeklyCost(qdcwi, Long.parseLong(string[0]), conn));
			// 本周存款余额-活期存款
			double dmCurrencyBalance = this.queryCurrentWeeklyBalance(qdcwi, Long.parseLong(string[0]), SETTConstant.AccountType.CURRENTDEPOSIT, conn);
			info.setCurrencyBalance(dmCurrencyBalance);
			// 本周存款余额-定期存款
			double dmFixedBalance = this.queryCurrentWeeklyBalance(qdcwi, Long.parseLong(string[0]), SETTConstant.AccountType.FIXEDDEPOSIT, conn);
			info.setFixedBalance(dmFixedBalance);
			// 本周存款余额-通知存款
			double dmNotifyBalance = this.queryCurrentWeeklyBalance(qdcwi, Long.parseLong(string[0]), SETTConstant.AccountType.NOTIFY, conn);
			info.setNotifyBalance(dmNotifyBalance);
			// 合计
			double dmSumBalance = dmCurrencyBalance+dmFixedBalance+dmNotifyBalance;
			info.setSumBalance(dmSumBalance);

			list.add(info);
		}

		return list != null && list.size() > 0?list:null;
		*/
		return null;
	}

	/**
	 * 取全部指定公司数据-成员单位合计
	 * @param  QueryDailyCapitalWhereInfo qdcwi
	 * @return Collection
	 * @exception throws SQLException
	 */
	public Collection getCurrencyDepositWeeklyInfo(QueryDailyCapitalWhereInfo qdcwi) throws SQLException {
		return this.getCurrencyDepositWeeklyInfo(qdcwi, connection);
	}

	/**
	 * 取集团控股电厂成员数据
	 * @param  QueryDailyCapitalWhereInfo qdcwi, Connection conn
	 * @return Collection
	 * @exception throws SQLException
	 */
	public Collection getGroupHoldingWeeklyInfo(QueryDailyCapitalWhereInfo qdcwi, Connection conn) throws SQLException {
		ArrayList list = new ArrayList();
		Collection collection = this.getGroupHoldingClientID(qdcwi, conn);
		Iterator iterator = null;
		if (collection != null) {
			iterator = collection.iterator();
		}
		while (iterator != null && iterator.hasNext()) {
			String[] string = (String[]) iterator.next();
			QCurrencyDepositInfo info = new QCurrencyDepositInfo();
			// 序号
			info.setNo(++lIndex);
			// 存款单位
			info.setDepositCorp(string[1]);
			// 上周存款余额
			info.setPreWeeklyBalance(this.queryPreviousWeeklyBalance(qdcwi, Long.parseLong(string[0]), conn));
			// 本周-存入
			info.setWeeklyHandIn(this.seekWeeklyHandIn(qdcwi, Long.parseLong(string[0]), conn));
			// 本周-支取
			info.setWeeklyCost(this.seekWeeklyCost(qdcwi, Long.parseLong(string[0]), conn));
			// 本周存款余额-活期存款
			double dmCurrencyBalance = this.queryCurrentWeeklyBalance(qdcwi, Long.parseLong(string[0]), SETTConstant.AccountGroupType.CURRENT, conn);
			info.setCurrencyBalance(dmCurrencyBalance);
			// 本周存款余额-定期存款
			double dmFixedBalance = this.queryCurrentWeeklyBalance(qdcwi, Long.parseLong(string[0]), SETTConstant.AccountGroupType.FIXED, conn);
			info.setFixedBalance(dmFixedBalance);
			// 本周存款余额-通知存款
			double dmNotifyBalance = this.queryCurrentWeeklyBalance(qdcwi, Long.parseLong(string[0]), SETTConstant.AccountGroupType.NOTIFY, conn);
			info.setNotifyBalance(dmNotifyBalance);
			// 合计
			double dmSumBalance = dmCurrencyBalance+dmFixedBalance+dmNotifyBalance;
			info.setSumBalance(dmSumBalance);

			list.add(info);
		}

		return list != null && list.size() > 0?list:null;
	}

	/**
	 * 取集团控股电厂成员数据
	 * @param  QueryDailyCapitalWhereInfo qdcwi
	 * @return Collection
	 * @exception throws SQLException
	 */
	public Collection getGroupHoldingWeeklyInfo(QueryDailyCapitalWhereInfo qdcwi) throws SQLException {
		return this.getGroupHoldingWeeklyInfo(qdcwi, connection);
	}

	/**
	 * 取开发控股电厂成员数据
	 * @param  QueryDailyCapitalWhereInfo qdcwi, Connection conn
	 * @return Collection
	 * @exception throws SQLException
	 */
	public Collection getEmpolderHoldingWeeklyInfo(QueryDailyCapitalWhereInfo qdcwi, Connection conn) throws SQLException {
		ArrayList list = new ArrayList();
		Collection collection = this.getEmpolderHoldingClientID(qdcwi, conn);
		Iterator iterator = null;
		if (collection != null) {
			iterator = collection.iterator();
		}
		while (iterator != null && iterator.hasNext()) {
			String[] string = (String[]) iterator.next();
			QCurrencyDepositInfo info = new QCurrencyDepositInfo();
			// 序号
			info.setNo(++lIndex);
			// 存款单位
			info.setDepositCorp(string[1]);
			// 上周存款余额
			info.setPreWeeklyBalance(this.queryPreviousWeeklyBalance(qdcwi, Long.parseLong(string[0]), conn));
			// 本周-存入
			info.setWeeklyHandIn(this.seekWeeklyHandIn(qdcwi, Long.parseLong(string[0]), conn));
			// 本周-支取
			info.setWeeklyCost(this.seekWeeklyCost(qdcwi, Long.parseLong(string[0]), conn));
			// 本周存款余额-活期存款
			double dmCurrencyBalance = this.queryCurrentWeeklyBalance(qdcwi, Long.parseLong(string[0]), SETTConstant.AccountGroupType.CURRENT, conn);
			info.setCurrencyBalance(dmCurrencyBalance);
			// 本周存款余额-定期存款
			double dmFixedBalance = this.queryCurrentWeeklyBalance(qdcwi, Long.parseLong(string[0]), SETTConstant.AccountGroupType.FIXED, conn);
			info.setFixedBalance(dmFixedBalance);
			// 本周存款余额-通知存款
			double dmNotifyBalance = this.queryCurrentWeeklyBalance(qdcwi, Long.parseLong(string[0]), SETTConstant.AccountGroupType.NOTIFY, conn);
			info.setNotifyBalance(dmNotifyBalance);
			// 合计
			double dmSumBalance = dmCurrencyBalance+dmFixedBalance+dmNotifyBalance;
			info.setSumBalance(dmSumBalance);

			list.add(info);
		}

		return list != null && list.size() > 0?list:null;
	}

	/**
	 * 取开发控股电厂成员数据
	 * @param  QueryDailyCapitalWhereInfo qdcwi
	 * @return Collection
	 * @exception throws SQLException
	 */
	public Collection getEmpolderHoldingWeeklyInfo(QueryDailyCapitalWhereInfo qdcwi) throws SQLException {
		return this.getEmpolderHoldingWeeklyInfo(qdcwi, connection);
	}

	/**
	 * 取集团电厂成员数据
	 * @param  QueryDailyCapitalWhereInfo qdcwi, Connection conn
	 * @return Collection
	 * @exception throws SQLException
	 */
	public Collection getGroupElectricWeeklyInfo(QueryDailyCapitalWhereInfo qdcwi, Connection conn) throws SQLException {
		ArrayList list = new ArrayList();
		Collection collection = this.getGroupElectricClientID(qdcwi, conn);
		Iterator iterator = null;
		if (collection != null) {
			iterator = collection.iterator();
		}
		while (iterator != null && iterator.hasNext()) {
			String[] string = (String[]) iterator.next();
			QCurrencyDepositInfo info = new QCurrencyDepositInfo();
			// 序号
			info.setNo(++lIndex);
			// 存款单位
			info.setDepositCorp(string[1]);
			// 上周存款余额
			info.setPreWeeklyBalance(this.queryPreviousWeeklyBalance(qdcwi, Long.parseLong(string[0]), conn));
			// 本周-存入
			info.setWeeklyHandIn(this.seekWeeklyHandIn(qdcwi, Long.parseLong(string[0]), conn));
			// 本周-支取
			info.setWeeklyCost(this.seekWeeklyCost(qdcwi, Long.parseLong(string[0]), conn));
			// 本周存款余额-活期存款
			double dmCurrencyBalance = this.queryCurrentWeeklyBalance(qdcwi, Long.parseLong(string[0]), SETTConstant.AccountGroupType.CURRENT, conn);
			info.setCurrencyBalance(dmCurrencyBalance);
			// 本周存款余额-定期存款
			double dmFixedBalance = this.queryCurrentWeeklyBalance(qdcwi, Long.parseLong(string[0]), SETTConstant.AccountGroupType.FIXED, conn);
			info.setFixedBalance(dmFixedBalance);
			// 本周存款余额-通知存款
			double dmNotifyBalance = this.queryCurrentWeeklyBalance(qdcwi, Long.parseLong(string[0]), SETTConstant.AccountGroupType.NOTIFY, conn);
			info.setNotifyBalance(dmNotifyBalance);
			// 合计
			double dmSumBalance = dmCurrencyBalance+dmFixedBalance+dmNotifyBalance;
			info.setSumBalance(dmSumBalance);

			list.add(info);
		}

		return list != null && list.size() > 0?list:null;
	}

	/**
	 * 取集团电厂成员数据
	 * @param  QueryDailyCapitalWhereInfo qdcwi
	 * @return Collection
	 * @exception throws SQLException
	 */
	public Collection getGroupElectricWeeklyInfo(QueryDailyCapitalWhereInfo qdcwi) throws SQLException {
		return this.getGroupElectricWeeklyInfo(qdcwi, connection);
	}

	/**
	 * 取开发电厂成员数据
	 * @param  QueryDailyCapitalWhereInfo qdcwi, Connection conn
	 * @return Collection
	 * @exception throws SQLException
	 */
	public Collection getEmpolderElectricWeeklyInfo(QueryDailyCapitalWhereInfo qdcwi, Connection conn) throws SQLException {
		ArrayList list = new ArrayList();
		Collection collection = this.getEmpolderElectricClientID(qdcwi, conn);
		Iterator iterator = null;
		if (collection != null) {
			iterator = collection.iterator();
		}
		while (iterator != null && iterator.hasNext()) {
			String[] string = (String[]) iterator.next();
			QCurrencyDepositInfo info = new QCurrencyDepositInfo();
			// 序号
			info.setNo(++lIndex);
			// 存款单位
			info.setDepositCorp(string[1]);
			// 上周存款余额
			info.setPreWeeklyBalance(this.queryPreviousWeeklyBalance(qdcwi, Long.parseLong(string[0]), conn));
			// 本周-存入
			info.setWeeklyHandIn(this.seekWeeklyHandIn(qdcwi, Long.parseLong(string[0]), conn));
			// 本周-支取
			info.setWeeklyCost(this.seekWeeklyCost(qdcwi, Long.parseLong(string[0]), conn));
			// 本周存款余额-活期存款
			double dmCurrencyBalance = this.queryCurrentWeeklyBalance(qdcwi, Long.parseLong(string[0]), SETTConstant.AccountGroupType.CURRENT, conn);
			info.setCurrencyBalance(dmCurrencyBalance);
			// 本周存款余额-定期存款
			double dmFixedBalance = this.queryCurrentWeeklyBalance(qdcwi, Long.parseLong(string[0]), SETTConstant.AccountGroupType.FIXED, conn);
			info.setFixedBalance(dmFixedBalance);
			// 本周存款余额-通知存款
			double dmNotifyBalance = this.queryCurrentWeeklyBalance(qdcwi, Long.parseLong(string[0]), SETTConstant.AccountGroupType.NOTIFY, conn);
			info.setNotifyBalance(dmNotifyBalance);
			// 合计
			double dmSumBalance = dmCurrencyBalance+dmFixedBalance+dmNotifyBalance;
			info.setSumBalance(dmSumBalance);

			list.add(info);
		}

		return list != null && list.size() > 0?list:null;
	}

	/**
	 * 取开发电厂成员数据
	 * @param  QueryDailyCapitalWhereInfo qdcwi
	 * @return Collection
	 * @exception throws SQLException
	 */
	public Collection getEmpolderElectricWeeklyInfo(QueryDailyCapitalWhereInfo qdcwi) throws SQLException {
		return this.getEmpolderElectricWeeklyInfo(qdcwi, connection);
	}

	/**
	 * 取股份控股电厂成员数据
	 * @param  QueryDailyCapitalWhereInfo qdcwi, Connection conn
	 * @return Collection
	 * @exception throws SQLException
	 */
	public Collection getHoldingElectricWeeklyInfo(QueryDailyCapitalWhereInfo qdcwi, Connection conn) throws SQLException {
		ArrayList list = new ArrayList();
		Collection collection = this.getHoldingElectricClientID(qdcwi, conn);
		Iterator iterator = null;
		if (collection != null) {
			iterator = collection.iterator();
		}
		while (iterator != null && iterator.hasNext()) {
			String[] string = (String[]) iterator.next();
			QCurrencyDepositInfo info = new QCurrencyDepositInfo();
			// 序号
			info.setNo(++lIndex);
			// 存款单位
			info.setDepositCorp(string[1]);
			// 上周存款余额
			info.setPreWeeklyBalance(this.queryPreviousWeeklyBalance(qdcwi, Long.parseLong(string[0]), conn));
			// 本周-存入
			info.setWeeklyHandIn(this.seekWeeklyHandIn(qdcwi, Long.parseLong(string[0]), conn));
			// 本周-支取
			info.setWeeklyCost(this.seekWeeklyCost(qdcwi, Long.parseLong(string[0]), conn));
			// 本周存款余额-活期存款
			double dmCurrencyBalance = this.queryCurrentWeeklyBalance(qdcwi, Long.parseLong(string[0]), SETTConstant.AccountGroupType.CURRENT, conn);
			info.setCurrencyBalance(dmCurrencyBalance);
			// 本周存款余额-定期存款
			double dmFixedBalance = this.queryCurrentWeeklyBalance(qdcwi, Long.parseLong(string[0]), SETTConstant.AccountGroupType.FIXED, conn);
			info.setFixedBalance(dmFixedBalance);
			// 本周存款余额-通知存款
			double dmNotifyBalance = this.queryCurrentWeeklyBalance(qdcwi, Long.parseLong(string[0]), SETTConstant.AccountGroupType.NOTIFY, conn);
			info.setNotifyBalance(dmNotifyBalance);
			// 合计
			double dmSumBalance = dmCurrencyBalance+dmFixedBalance+dmNotifyBalance;
			info.setSumBalance(dmSumBalance);

			list.add(info);
		}

		return list != null && list.size() > 0?list:null;
	}

	/**
	 * 取股份控股电厂成员数据
	 * @param  QueryDailyCapitalWhereInfo qdcwi
	 * @return Collection
	 * @exception throws SQLException
	 */
	public Collection getHoldingElectricWeeklyInfo(QueryDailyCapitalWhereInfo qdcwi) throws SQLException {
		return this.getHoldingElectricWeeklyInfo(qdcwi, connection);
	}

	/**
	 * 取子公司合计数据通过科目表
	 * @param  QueryDailyCapitalWhereInfo qdcwi, Connection conn
	 * @return QCurrencyDepositInfo
	 * @exception nothing
	 */
	public QCurrencyDepositInfo getAllSubClientWeeklyInfo(QueryDailyCapitalWhereInfo qdcwi, Connection conn) throws SQLException {
		QCurrencyDepositInfo info = new QCurrencyDepositInfo();

		// 存款单位
		info.setDepositCorp("合计");
		// 上周存款余额
		info.setPreWeeklyBalance(this.queryInsideClientUpWeeklyBalance(qdcwi, conn));
		// 本周-存入
		double dmAmountCredit = this.queryInsideClientAllWeeklyAmount(qdcwi, conn, SETTConstant.DebitOrCredit.CREDIT);
		info.setWeeklyHandIn(dmAmountCredit);
		// 本周-支取
		double dmAmountDebit = this.queryInsideClientAllWeeklyAmount(qdcwi, conn, SETTConstant.DebitOrCredit.DEBIT);
		info.setWeeklyCost(dmAmountDebit);
		// 本周存款余额-活期存款
		double dmCurrencyBalance = this.queryInsideClientThisWeeklyBalance(qdcwi, conn, SETTConstant.AccountGroupType.CURRENT);
		info.setCurrencyBalance(dmCurrencyBalance);
		// 本周存款余额-定期存款
		double dmFixedBalance = this.queryInsideClientThisWeeklyBalance(qdcwi, conn, SETTConstant.AccountGroupType.FIXED);
		info.setFixedBalance(dmFixedBalance);
		// 本周存款余额-通知存款
		double dmNotifyBalance = this.queryInsideClientThisWeeklyBalance(qdcwi, conn, SETTConstant.AccountGroupType.NOTIFY);
		info.setNotifyBalance(dmNotifyBalance);
		// 合计
		double dmSumBalance = dmCurrencyBalance+dmFixedBalance+dmNotifyBalance;
		info.setSumBalance(dmSumBalance);

		return info;
	}

	/**
	 * 取子公司合计数据通过科目表
	 * @param  QueryDailyCapitalWhereInfo qdcwi
	 * @return QCurrencyDepositInfo
	 * @exception nothing
	 */
	public QCurrencyDepositInfo getAllSubClientWeeklyInfo(QueryDailyCapitalWhereInfo qdcwi) throws SQLException {
		return this.getAllSubClientWeeklyInfo(qdcwi, connection);
	}
}
//===========================================================================================================================