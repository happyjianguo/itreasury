package com.iss.itreasury.settlement.bankinterface.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.util.*;
import com.iss.itreasury.settlement.bankinterface.dataentity.BankAccountMappingInfo;
//import com.iss.itreasury.settlement.bankinterface.dataentity.BankAccountTransInfo;
import com.iss.itreasury.settlement.bankinterface.dataentity.QueryAccountMappingInfo;

/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2005
 * Company:             iSoftStone
 * @author             	qijiang 
 * @version
 *  Date of Creation    2005-9-16
 */
public class Sett_BankAccountMappingDAO extends SettlementDAO {
	
	
	private Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);
		
	public Sett_BankAccountMappingDAO(){
		super();
		this.strTableName = "Sett_BankAccountsMapping";
	}
	
	public Sett_BankAccountMappingDAO(Connection conn){
		super(conn);
		this.strTableName = "Sett_BankAccountsMapping";
	}
	
	
	/**
	 * 新增银行对账记录，银行对账记录由银行历史交易表中的数据而来
	 * 
	 * @param info BankAccountsMappingInfo
	 * @return 设置的记录id
	 * @throws Exception
	 */
	public long add(BankAccountMappingInfo info) throws Exception {
		long lReturn = -1;

		Connection conn = null;
		PreparedStatement pstmt = null;

		StringBuffer buffer = new StringBuffer();
		buffer.append("insert into \n");
		buffer.append(strTableName);
		buffer.append("\n (ID, \n");
		buffer.append("sAccountNo,\n");
		buffer.append("sSubjectCode,\n");		
		buffer.append("nDirection,\n ");
		buffer.append("mAmount,\n");
		buffer.append("dtTransaction,\n ");
		buffer.append("sAbstract,\n");
		buffer.append("nStatusID,\n");
		buffer.append("nMappingID)\n");
		buffer.append("values(?,?,?,?,?,?,?,?,?)");

		try {
			log.info("BankAccountsMappingInfo[Add]_SQL:" + buffer.toString());
			conn = this.getConnection();
			pstmt = conn.prepareStatement(buffer.toString());

			int nIndex = 1;

			pstmt.setLong(nIndex++, info.getID());
			pstmt.setString(nIndex++, info.getAccountNo());
			pstmt.setString(nIndex++, info.getSubjectCode());
			pstmt.setLong(nIndex++, info.getTransDirection());
			pstmt.setDouble(nIndex++, info.getAmount());
			pstmt.setTimestamp(nIndex++, info.getTransDate());
			pstmt.setString(nIndex++, info.getAbstract());
			pstmt.setLong(nIndex++, info.getStatusID());
			pstmt.setLong(nIndex++, info.getMappingID());

			pstmt.execute();

			lReturn = info.getID();
			
			cleanup(pstmt);
			cleanup(conn);
			
		} finally {
			cleanup(pstmt);
			cleanup(conn);
		}

		return lReturn;
	}

	public BankAccountMappingInfo findByID(long ID) throws Exception
	{
		
		BankAccountMappingInfo info = null;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT * FROM \n");
		buffer.append(strTableName);
		buffer.append("\n WHERE ID = '" + ID + "' \n");
		try {
			conn = this.getConnection();
			log.info("BankAccountsMappingInfo[findByID]_SQL:" +buffer.toString());
			pstmt = conn.prepareStatement(buffer.toString());
			rset = pstmt.executeQuery();

			while (rset.next()) {
				info = new BankAccountMappingInfo();

				info.setID(rset.getLong("ID"));
				info.setAccountNo(rset.getString("sAccountNo"));
				info.setSubjectCode(rset.getString("sSubjectCode"));
				info.setTransDirection(rset.getLong("nDirection"));
				info.setAmount(rset.getDouble("mAmount"));
				info.setTransDate(rset.getTimestamp("dtTransaction"));
				info.setAbstract(rset.getString("sAbstract"));
				info.setStatusID(rset.getLong("nStatusID"));
				info.setMappingID(rset.getLong("nMappingID"));
			}
			cleanup(rset);
			cleanup(pstmt);
			cleanup(conn);

		} finally {
			cleanup(rset);
			cleanup(pstmt);
			cleanup(conn);

		}

		return info;

	}
	
	
	public BankAccountMappingInfo findByMappingID(long mappingID) throws Exception
	{
		
		BankAccountMappingInfo info = null;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT * FROM \n");
		buffer.append(strTableName);
		buffer.append("\n WHERE nMappingID = '" + mappingID + "' \n");
		try {
			conn = this.getConnection();
			log.info("BankAccountsMappingInfo[findByMappingID]_SQL:" +buffer.toString());
			pstmt = conn.prepareStatement(buffer.toString());
			rset = pstmt.executeQuery();

			while (rset.next()) {
				info = new BankAccountMappingInfo();

				info.setID(rset.getLong("ID"));
				info.setAccountNo(rset.getString("sAccountNo"));
				info.setSubjectCode(rset.getString("sSubjectCode"));
				info.setTransDirection(rset.getLong("nDirection"));
				info.setAmount(rset.getDouble("mAmount"));
				info.setTransDate(rset.getTimestamp("dtTransaction"));
				info.setAbstract(rset.getString("sAbstract"));
				info.setStatusID(rset.getLong("nStatusID"));
				info.setMappingID(rset.getLong("nMappingID"));
			}
			cleanup(rset);
			cleanup(pstmt);
			cleanup(conn);

		} finally {
			cleanup(rset);
			cleanup(pstmt);
			cleanup(conn);
		}

		return info;

	}
	
	
	public Collection findByCondition(QueryAccountMappingInfo condition) throws Exception {

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		ArrayList list = new ArrayList();

		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT * FROM \n");
		buffer.append(strTableName);
		buffer.append("\n WHERE 1 = 1 \n");
		if(condition.getBankAccountNo()!=null
				&& condition.getBankAccountNo().length()>0) {
			buffer.append(" and sAccountNo='"+condition.getBankAccountNo().trim()+"' \n");
		}
		if(condition.getSubjectCode()!=null 
				&& condition.getSubjectCode().length()>0) {
			buffer.append(" and sSubjectCode='"+condition.getSubjectCode().trim()+"' \n");
		}
		if (condition.getStartDate() != null)
		{
			buffer
					.append(" and to_date(to_char(dtTransaction,'yyyymmdd'),'yyyymmdd')>= to_date('"
							+ DataFormat.formatDate(condition.getStartDate())
							+ "','yyyy-mm-dd')");
		}
		if (condition.getEndDate() != null)
		{
			buffer
					.append(" and to_date(to_char(dtTransaction,'yyyymmdd'),'yyyymmdd')<= to_date('"
							+ DataFormat.formatDate(condition.getEndDate())
							+ "','yyyy-mm-dd')");
		}
		if(condition.getTransDirection()>0) {
			buffer.append(" and nDirection="+condition.getTransDirection()+" \n");
		}
		if(condition.getStatusID() > 0) {
			buffer.append(" and nStatusID="+condition.getStatusID()+" \n");
		}
		if(condition.getMappingID()>0) {
			buffer.append(" and nMappingID="+condition.getMappingID()+" \n");
		}
		buffer.append(" order by dtTransaction");
		try {
			conn = this.getConnection();
			log.info("BankAccountsMappingInfo[findByCondition]_SQL:"
					+ buffer.toString());
			pstmt = conn.prepareStatement(buffer.toString());
			rset = pstmt.executeQuery();

			while (rset.next()) {
				BankAccountMappingInfo info = new BankAccountMappingInfo();

				info.setID(rset.getLong("ID"));
				info.setAccountNo(rset.getString("sAccountNo"));
				info.setSubjectCode(rset.getString("sSubjectCode"));
				info.setTransDirection(rset.getLong("nDirection"));
				info.setAmount(rset.getDouble("mAmount"));
				info.setTransDate(rset.getTimestamp("dtTransaction"));
				info.setAbstract(rset.getString("sAbstract"));
				info.setStatusID(rset.getLong("nStatusID"));
				info.setMappingID(rset.getLong("nMappingID"));
				
				list.add(info);
			}
			log.debug("Start clean...");
			cleanup(rset);
			cleanup(pstmt);
			cleanup(conn);
			log.debug("End clean...");

		} finally {
			cleanup(rset);
			cleanup(pstmt);
			cleanup(conn);
		}

		if (list.size() == 0) {
			return null;
		}		

		return list;		
	}
	
	public void update(BankAccountMappingInfo info) throws Exception
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		StringBuffer buffer = new StringBuffer();
		buffer.append("update " + strTableName + " set \n");
		buffer.append("nStatusID = ?,\n");
		buffer.append("nMappingID = ? \n");
		buffer.append("where ID = ? \n");
		try
		{
			conn = this.getConnection();
			log.info("BankAccountsMappingInfo[update]_SQL:" 
					+ buffer.toString());
			pstmt = conn.prepareStatement(buffer.toString());

			int nIndex = 1;
			
			pstmt.setLong(nIndex++, info.getStatusID());
			pstmt.setLong(nIndex++, info.getMappingID());
			pstmt.setLong(nIndex++, info.getID());
			
			pstmt.execute();

			
			cleanup(pstmt);
			cleanup(conn);
		}
		finally
		{
			cleanup(pstmt);
			cleanup(conn);

		}
	}
	/*
	public Collection getTransInfoForImport(QueryAccountMappingInfo qfi) throws Exception {		
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		ArrayList list = new ArrayList();
		StringBuffer m_sbSql = new StringBuffer();
		m_sbSql.append("select * from ( ");
		m_sbSql.append("select * from sett_TransInfoOfBankAccount ");
		m_sbSql.append(" where 1=1 ");
		if(qfi.getBankAccountNo()!=null && !"".equals(qfi.getBankAccountNo())) {
			m_sbSql.append(" and sAccountNo = '" + qfi.getBankAccountNo() + "'");
		}
		if(qfi.getStartDate()!=null) {
			m_sbSql.append(" and to_date(to_char(dtTransAction,'yyyymmdd'),'yyyymmdd')>= to_date('"
					+ DataFormat.formatDate(qfi.getStartDate())
					+ "','yyyy-mm-dd')");
		}
		if (qfi.getEndDate()!=null)
		{
			m_sbSql.append(" and to_date(to_char(dtTransAction,'yyyymmdd'),'yyyymmdd')<= to_date('"
							+ DataFormat.formatDate(qfi.getEndDate())
							+ "','yyyy-mm-dd')");
		}
		m_sbSql.append(" union select * from sett_HisTransInfoOfBankAccount ");
		m_sbSql.append(" where 1=1 ");
		if(qfi.getBankAccountNo()!=null && !"".equals(qfi.getBankAccountNo())) {
			m_sbSql.append(" and sAccountNo = '" + qfi.getBankAccountNo() + "'");
		}
		if(qfi.getStartDate()!=null) {
			m_sbSql.append(" and to_date(to_char(dtTransAction,'yyyymmdd'),'yyyymmdd')>= to_date('"
					+ DataFormat.formatDate(qfi.getStartDate())
					+ "','yyyy-mm-dd')");
		}
		if (qfi.getEndDate()!=null)
		{
			m_sbSql.append(" and to_date(to_char(dtTransAction,'yyyymmdd'),'yyyymmdd')<= to_date('"
							+ DataFormat.formatDate(qfi.getEndDate())
							+ "','yyyy-mm-dd')");
		}
		m_sbSql.append(")");
		m_sbSql.append(" order by dtTransAction ");
		//System.out.println("导入银行交易数据到银行对账表：" + m_sbSql.toString());
		try
		{			
			conn = this.getConnection();		

			log.info("导入银行交易数据到银行对账表：" + m_sbSql.toString());

			pstmt = conn.prepareStatement(m_sbSql.toString());

			rs = pstmt.executeQuery();
			
			while (rs.next())
			{
				BankAccountTransInfo temp = new BankAccountTransInfo();

				temp.setID(rs.getLong("ID"));
				temp.setAccountNo(rs.getString("SACCOUNTNO"));
				temp.setAccountName(rs.getString("SACCOUNTNAME"));
				temp.setBranchName(rs.getString("SBRANCHNAME"));
				temp.setOppositeAccountNo(rs.getString("SOPPOSITEACCOUNTNO"));
				temp.setOppositeAccountName(rs.getString("SOPPOSITEACCOUNTNAME"));
				temp.setOppositeBranchName(rs.getString("SOPPOSITEBRANCHNAME"));
				temp.setAmount(rs.getDouble("MAMOUNT"));
				temp.setCurrencyID(rs.getLong("NCURRENCYID"));
				temp.setDirection(rs.getLong("NDIRECTION"));
				temp.setCheckNo(rs.getString("SCHECKNO"));
				temp.setCheckType(rs.getString("SCHECKTYPE"));
				temp.setTransAction(new Date(rs.getTimestamp("DTTRANSACTION")
								.getTime()));
				temp.setTransactionType(rs.getString("STRANSACTIONTYPE"));
				temp.setAbstract(rs.getString("SABSTRACT"));
				temp.setComment(rs.getString("SCOMMENT"));
				temp.setBankType(rs.getLong("NBANKTYPE"));

				//新增字段
				temp.setTransNoOfBank(rs.getString("STRANSNOOFBANK"));
				temp.setIsToTurnIn(rs.getLong("NISTOTURNIN"));
				temp.setTurnInResult(rs.getLong("NTURNINRESULT"));
				temp.setTurnInTime(rs.getTimestamp("DTTURNIN"));
				temp.setTurnInTransType(rs.getLong("NTURNINTRANSTYPE"));
				temp.setTransactionNO(rs.getString("STRANSACTIONNO"));
				temp.setIsPrintedVoucher(rs.getLong("NISPRINTEDVOUCHER"));
				temp.setIsDeletedByBank(rs.getLong("NISDELETEDBYBANK"));
				temp.setSTurnInRemind(rs.getString("STURNINREMIND"));
				temp.setTurnInSend(rs.getTimestamp("DTTURNINSEND"));
				
				list.add(temp);
			}
			cleanup(rs);
			cleanup(pstmt);
			cleanup(conn);
		}
		finally
		{
			cleanup(rs);
			cleanup(pstmt);
			cleanup(conn);
		}
		
		return list;
	}
	*/
	public Collection getTransForCompare(QueryAccountMappingInfo condition) throws Exception {

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		ArrayList list = new ArrayList();

		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT * FROM \n");
		buffer.append(strTableName);
		buffer.append("\n WHERE 1 = 1 \n");
		if(condition.getBankAccountNo()!=null
				&& condition.getBankAccountNo().length()>0) {
			buffer.append(" and sAccountNo='"+condition.getBankAccountNo().trim()+"' \n");
		}
		if(condition.getSubjectCode()!=null 
				&& condition.getSubjectCode().length()>0) {
			buffer.append(" and sSubjectCode='"+condition.getSubjectCode().trim()+"' \n");
		}
		if (condition.getStartDate() != null)
		{
			buffer
					.append(" and to_date(to_char(dtTransaction,'yyyymmdd'),'yyyymmdd')>= to_date('"
							+ DataFormat.formatDate(condition.getStartDate())
							+ "','yyyy-mm-dd')");
		}
		if (condition.getEndDate() != null)
		{
			buffer
					.append(" and to_date(to_char(dtTransaction,'yyyymmdd'),'yyyymmdd')<= to_date('"
							+ DataFormat.formatDate(condition.getEndDate())
							+ "','yyyy-mm-dd')");
		}
		if(condition.getTransDirection()>0) {
			buffer.append(" and nDirection="+condition.getTransDirection()+" \n");
		}
		buffer.append(" order by dtTransaction");
		try {
			conn = this.getConnection();
			log.info("BankAccountsMappingInfo[getTransForCompare]_SQL:"
					+ buffer.toString());
			pstmt = conn.prepareStatement(buffer.toString());
			rset = pstmt.executeQuery();

			while (rset.next()) {
				BankAccountMappingInfo info = new BankAccountMappingInfo();

				info.setID(rset.getLong("ID"));
				info.setAccountNo(rset.getString("sAccountNo"));
				info.setSubjectCode(rset.getString("sSubjectCode"));
				info.setTransDirection(rset.getLong("nDirection"));
				info.setAmount(rset.getDouble("mAmount"));
				info.setTransDate(rset.getTimestamp("dtTransaction"));
				info.setAbstract(rset.getString("sAbstract"));
				info.setStatusID(rset.getLong("nStatusID"));
				info.setMappingID(rset.getLong("nMappingID"));
				
				list.add(info);
			}
			log.debug("Start clean...");
			cleanup(rset);
			cleanup(pstmt);
			cleanup(conn);
			log.debug("End clean...");

		} finally {
			cleanup(rset);
			cleanup(pstmt);
			cleanup(conn);
		}

		if (list.size() == 0) {
			return null;
		}		

		return list;		
	}
	
	/*public static void main(String[] args) {
		
		Sett_BankAccountMappingDAO dao = new Sett_BankAccountMappingDAO();
		QueryAccountMappingInfo qfi = new QueryAccountMappingInfo();
		qfi.setBankAccountNo("11111111111111111");
		qfi.setStartDate(DataFormat.getDateTime("2005-12-22"));
		qfi.setStartDate(DataFormat.getDateTime("2005-12-22"));
		
		try {
			Collection coll = dao.getTransInfoForImport(qfi);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}*/
}
