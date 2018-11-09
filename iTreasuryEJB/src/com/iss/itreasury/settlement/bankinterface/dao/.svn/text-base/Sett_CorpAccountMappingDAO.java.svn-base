package com.iss.itreasury.settlement.bankinterface.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.util.*;
import com.iss.itreasury.settlement.bankinterface.dataentity.CorpAccountMappingInfo;
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
public class Sett_CorpAccountMappingDAO extends SettlementDAO {
	
	
	private Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);
		
	public Sett_CorpAccountMappingDAO(){
		super();
		this.strTableName = "Sett_CorpAccountsMapping";
	}
	
	public Sett_CorpAccountMappingDAO(Connection conn){
		super(conn);
		this.strTableName = "Sett_CorpAccountsMapping";
	}
	
	
	/**
	 * 新增企业对账记录，企业对账记录由会计分录中的数据而来
	 * 
	 * @param info CorpAccountsMappingInfo
	 * @return 设置的记录id
	 * @throws Exception
	 */
	public long add(CorpAccountMappingInfo info) throws Exception {
		long lReturn = -1;

		Connection conn = null;
		PreparedStatement pstmt = null;
		Statement statement = null;
		ResultSet rs = null;
		StringBuffer buffer = new StringBuffer();
		buffer.append("insert into \n");
		buffer.append(strTableName);
		buffer.append("\n (ID, \n");
		buffer.append("nGlentryID,\n");
		buffer.append("nTransDetailID,\n");
		buffer.append("sSubjectCode,\n");
		buffer.append("nWithinAccountID,\n");
		buffer.append("sTransNo,\n");
		buffer.append("nTransactionTypeID,\n");
		buffer.append("nTransDirection,\n ");
		buffer.append("mAmount,\n");
		buffer.append("dtExecute,\n ");
		buffer.append("dtInterestStart,\n");
		buffer.append("sAbstract,\n");
		buffer.append("nStatusID,\n");
		buffer.append("nMappingID)\n");
		buffer.append("values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

		try {
			log.info("CorpAccountsMappingInfo[Add]_SQL:" + buffer.toString());
			conn = this.getConnection();
			pstmt = conn.prepareStatement(buffer.toString());
			
			long id = -1;
			rs = conn.createStatement().executeQuery( " select max(ID) ID from " + strTableName);
			if (rs.next())
			{
				id = (long) rs.getLong("ID");
				id++;
			}
			else
			{
				id = 1;
			}
			info.setID(id);

			int nIndex = 1;

			pstmt.setLong(nIndex++, info.getID());
			log.info("新增的会计分录ID: "+info.getGlentryID());
			pstmt.setLong(nIndex++, info.getGlentryID());
			pstmt.setLong(nIndex++, info.getTransDetailID());
			pstmt.setString(nIndex++, info.getSubjectCode());
			pstmt.setLong(nIndex++, info.getWithinAccountID());
			pstmt.setString(nIndex++, info.getTransNo());
			pstmt.setLong(nIndex++, info.getTransactionTypeID());
			pstmt.setLong(nIndex++, info.getTransDirection());
			pstmt.setDouble(nIndex++, info.getAmount());
			pstmt.setTimestamp(nIndex++, info.getExecute());
			pstmt.setTimestamp(nIndex++, info.getInterestStart());
			pstmt.setString(nIndex++, info.getAbstract());
			pstmt.setLong(nIndex++, info.getStatusID());
			pstmt.setLong(nIndex++, info.getID());

			pstmt.execute();

			lReturn = info.getID();
			
			
			this.cleanup(pstmt);
			this.cleanup(conn);
		} finally {
			this.cleanup(pstmt);
			this.cleanup(conn);
		}

		return lReturn;
	}

	public CorpAccountMappingInfo findByID(long ID) throws Exception
	{
		
		CorpAccountMappingInfo info = null;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT * FROM \n");
		buffer.append(strTableName);
		buffer.append("\n WHERE ID = '" + ID + "' \n");
		try {
			conn = this.getConnection();
			log.info("CorpAccountsMappingInfo[findByID]_SQL:" +buffer.toString());
			pstmt = conn.prepareStatement(buffer.toString());
			rset = pstmt.executeQuery();

			while (rset.next()) {
				info = new CorpAccountMappingInfo();

				info.setID(rset.getLong("ID"));
				info.setGlentryID(rset.getLong("nGlentryID"));
				info.setTransDetailID(rset.getLong("nTransDetailID"));
				info.setSubjectCode(rset.getString("sSubjectCode"));
				info.setWithinAccountID(rset.getLong("nWithinAccountID"));
				info.setTransNo(rset.getString("sTransNo"));
				info.setTransactionTypeID(rset.getLong("nTransactionTypeID"));
				info.setTransDirection(rset.getLong("nTransDirection"));
				info.setAmount(rset.getDouble("mAmount"));
				info.setExecute(rset.getTimestamp("dtExecute"));
				info.setInterestStart(rset.getTimestamp("dtInterestStart"));
				info.setAbstract(rset.getString("sAbstract"));
				info.setStatusID(rset.getLong("nStatusID"));
				info.setMappingID(rset.getLong("nMappingID"));
			}
			
			this.cleanup(rset);
			this.cleanup(pstmt);
			this.cleanup(conn);

		} finally {
			this.cleanup(rset);
			this.cleanup(pstmt);
			this.cleanup(conn);

		}

		return info;

	}
	
	
	public CorpAccountMappingInfo findByMappingID(long mappingID) throws Exception
	{
		
		CorpAccountMappingInfo info = null;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT * FROM \n");
		buffer.append(strTableName);
		buffer.append("\n WHERE ID = '" + mappingID + "' \n");
		try {
			conn = this.getConnection();
			log.info("CorpAccountsMappingInfo[findByMappingID]_SQL:" +buffer.toString());
			pstmt = conn.prepareStatement(buffer.toString());
			rset = pstmt.executeQuery();

			while (rset.next()) {
				info = new CorpAccountMappingInfo();

				info.setID(rset.getLong("ID"));
				info.setGlentryID(rset.getLong("nGlentryID"));
				info.setTransDetailID(rset.getLong("nTransDetailID"));
				info.setSubjectCode(rset.getString("sSubjectCode"));
				info.setWithinAccountID(rset.getLong("nWithinAccountID"));
				info.setTransNo(rset.getString("sTransNo"));
				info.setTransactionTypeID(rset.getLong("nTransactionTypeID"));
				info.setTransDirection(rset.getLong("nTransDirection"));
				info.setAmount(rset.getDouble("mAmount"));
				info.setExecute(rset.getTimestamp("dtExecute"));
				info.setInterestStart(rset.getTimestamp("dtInterestStart"));
				info.setAbstract(rset.getString("sAbstract"));
				info.setStatusID(rset.getLong("nStatusID"));
				info.setMappingID(rset.getLong("nMappingID"));
			}
			
			this.cleanup(rset);
			this.cleanup(pstmt);
			this.cleanup(conn);

		} finally {
			this.cleanup(rset);
			this.cleanup(pstmt);
			this.cleanup(conn);

		}

		return info;

	}
	
	
	public Collection findByCondition(QueryAccountMappingInfo condition) throws Exception {

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		ArrayList list = new ArrayList(4);

		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT * FROM \n");
		buffer.append(strTableName);
		buffer.append("\n WHERE 1 = 1 \n");
		if(condition.getSubjectCode() != null 
				&& condition.getSubjectCode().length() > 0) {
			buffer.append(" and sSubjectCode='"+condition.getSubjectCode().trim()+"' \n");
		}
		if(condition.getWithinAccountID() > 0) {
			buffer.append(" and nWithinAccountID="+condition.getWithinAccountID()+" \n");
		}
		if (condition.getStartDate() != null)
		{
			buffer
					.append(" and to_date(to_char(dtInterestStart,'yyyymmdd'),'yyyymmdd')>= to_date('"
							+ DataFormat.formatDate(condition.getStartDate())
							+ "','yyyy-mm-dd')");
		}
		if (condition.getEndDate() != null) 
		{
			buffer
					.append(" and to_date(to_char(dtInterestStart,'yyyymmdd'),'yyyymmdd')<= to_date('"
							+ DataFormat.formatDate(condition.getEndDate())
							+ "','yyyy-mm-dd')");
		}
		if(condition.getTransDirection()>0) {
			buffer.append(" and nTransDirection="+condition.getTransDirection()+" \n");
		}
		if(condition.getStatusID() > 0) {
			buffer.append(" and nStatusID="+condition.getStatusID()+" \n");
		}
		if(condition.getMappingID()>0) {
			buffer.append(" and nMappingID="+condition.getMappingID()+" \n");
		}
		buffer.append(" order by sTransNo");
		try {
			conn = this.getConnection();
			log.info("CorpAccountsMappingInfo[findByCondition]_SQL:"
					+ buffer.toString());
			pstmt = conn.prepareStatement(buffer.toString());
			rset = pstmt.executeQuery();

			while (rset.next()) {
				CorpAccountMappingInfo tmp = new CorpAccountMappingInfo();

				tmp.setID(rset.getLong("ID"));
				tmp.setGlentryID(rset.getLong("nGlentryID"));
				tmp.setTransDetailID(rset.getLong("nTransDetailID"));
				tmp.setSubjectCode(rset.getString("sSubjectCode"));
				tmp.setWithinAccountID(rset.getLong("nWithinAccountID"));
				tmp.setTransNo(rset.getString("sTransNo"));
				tmp.setTransactionTypeID(rset.getLong("nTransactionTypeID"));
				tmp.setTransDirection(rset.getLong("nTransDirection"));
				tmp.setAmount(rset.getDouble("mAmount"));
				tmp.setExecute(rset.getTimestamp("dtExecute"));
				tmp.setInterestStart(rset.getTimestamp("dtInterestStart"));
				tmp.setAbstract(rset.getString("sAbstract"));
				tmp.setStatusID(rset.getLong("nStatusID"));
				tmp.setMappingID(rset.getLong("nMappingID"));
				
				list.add(tmp);
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
	
	public Collection getCorpInfoForCompare(QueryAccountMappingInfo condition) throws Exception {

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		ArrayList list = new ArrayList(4);

		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT * FROM \n");
		buffer.append(strTableName);
		buffer.append("\n WHERE 1 = 1 \n");
		if(condition.getSubjectCode() != null 
				&& condition.getSubjectCode().length() > 0) {
			buffer.append(" and sSubjectCode='"+condition.getSubjectCode().trim()+"' \n");
		}
		if(condition.getWithinAccountID() > 0) {
			buffer.append(" and nWithinAccountID="+condition.getWithinAccountID()+" \n");
		}
		if (condition.getStartDate() != null)
		{
			buffer
					.append(" and to_date(to_char(dtInterestStart,'yyyymmdd'),'yyyymmdd')>= to_date('"
							+ DataFormat.formatDate(condition.getStartDate())
							+ "','yyyy-mm-dd')");
		}
		if (condition.getEndDate() != null) 
		{
			buffer
					.append(" and to_date(to_char(dtInterestStart,'yyyymmdd'),'yyyymmdd')<= to_date('"
							+ DataFormat.formatDate(condition.getEndDate())
							+ "','yyyy-mm-dd')");
		}
		if(condition.getTransDirection()>0) {
			buffer.append(" and nTransDirection="+condition.getTransDirection()+" \n");
		}
		buffer.append(" order by sTransNo");
		try {
			conn = this.getConnection();
			log.info("CorpAccountsMappingInfo[findByCondition]_SQL:"
					+ buffer.toString());
			pstmt = conn.prepareStatement(buffer.toString());
			rset = pstmt.executeQuery();

			while (rset.next()) {
				CorpAccountMappingInfo tmp = new CorpAccountMappingInfo();

				tmp.setID(rset.getLong("ID"));
				tmp.setGlentryID(rset.getLong("nGlentryID"));
				tmp.setTransDetailID(rset.getLong("nTransDetailID"));
				tmp.setSubjectCode(rset.getString("sSubjectCode"));
				tmp.setWithinAccountID(rset.getLong("nWithinAccountID"));
				tmp.setTransNo(rset.getString("sTransNo"));
				tmp.setTransactionTypeID(rset.getLong("nTransactionTypeID"));
				tmp.setTransDirection(rset.getLong("nTransDirection"));
				tmp.setAmount(rset.getDouble("mAmount"));
				tmp.setExecute(rset.getTimestamp("dtExecute"));
				tmp.setInterestStart(rset.getTimestamp("dtInterestStart"));
				tmp.setAbstract(rset.getString("sAbstract"));
				tmp.setStatusID(rset.getLong("nStatusID"));
				tmp.setMappingID(rset.getLong("nMappingID"));
				
				list.add(tmp);
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
	  
	public void update(CorpAccountMappingInfo info) throws Exception
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
			log.info("CorpAccountsMappingInfo[update]_SQL:"
					+ buffer.toString());
			pstmt = conn.prepareStatement(buffer.toString());

			int nIndex = 1;
			
			pstmt.setLong(nIndex++, info.getStatusID());
			pstmt.setLong(nIndex++, info.getMappingID());
			pstmt.setLong(nIndex++, info.getID());
			
			pstmt.execute();			
			
			this.cleanup(pstmt);
			this.cleanup(conn);

		}
		finally
		{
			this.cleanup(pstmt);
			this.cleanup(conn);

		}
	}
	
	public long getMaxMappingID() throws Exception {
		long maxID = -1;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT max(nMappingID) FROM \n");
		buffer.append(strTableName);
		
		try {
			conn = this.getConnection();
			log.info("CorpAccountsMappingInfo[findByID]_SQL:" +buffer.toString());
			pstmt = conn.prepareStatement(buffer.toString());
			rset = pstmt.executeQuery();

			while (rset.next()) {
				maxID = rset.getLong(1);
			}
			
			this.cleanup(rset);
			this.cleanup(pstmt);
			this.cleanup(conn);
		}
		finally
		{
			this.cleanup(rset);
			this.cleanup(pstmt);
			this.cleanup(conn);

		}
		
		return maxID;
	}
}
