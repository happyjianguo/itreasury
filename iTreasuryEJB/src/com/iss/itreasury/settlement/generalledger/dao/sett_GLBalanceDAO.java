package com.iss.itreasury.settlement.generalledger.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.settlement.generalledger.dataentity.GLBalanceInfo;
import com.iss.itreasury.util.Log4j;
import com.iss.itreasury.util.Constant;;
/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2003-12-19
 */
public class sett_GLBalanceDAO extends SettlementDAO {


	private Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);
	/**
	 * Constructor for sett_GLBalanceDAO.
	 */
	public sett_GLBalanceDAO() {
		super();
		super.strTableName = "sett_GLBalance";
	}

	/**
	 * Constructor for sett_GLBalanceDAO.
	 * @param conn
	 */
	public sett_GLBalanceDAO(Connection conn) {
		super(conn);
		super.strTableName = "sett_GLBalance";		
	}


	public long add(GLBalanceInfo info) throws SQLException{
		
		
		long lReturn = -1;
		//modify by flh 改为id从seq中取，不取maxid
		 Connection conn = null;
		 PreparedStatement pstmt = null;

		 StringBuffer buffer = new StringBuffer();
		 buffer.append("insert into \n");
		 buffer.append(strTableName);
		 buffer.append("\n (ID, \n");
		 buffer.append("dtGlDate,\n");
		 buffer.append("nOfficeID,\n");
		 buffer.append("nCurrencyID,\n");
		 buffer.append("sGlSubjectCode,\n");
		 buffer.append("nBalanceDirection,\n");
		 //buffer.append("nTransDirection,\n ");
		 buffer.append("mDebitBalance,\n");
		 buffer.append("mCreditBalance, \n ");
		 buffer.append("NDEBITNUMBER,\n");
		 buffer.append("NCREDITNUMBER, \n ");		 
		 buffer.append("MDEBITAMOUNT,\n");
		 buffer.append("MCREDITAMOUNT) \n ");		 
		 buffer.append("values(SEQ_Sett_GlBalance.nextval,?,?,?,?,?,?,?,?,?,?,?)");

		 try
		 {
			//long id = this.getNextID();
			//info.setID(id);		 	
			log.debug(buffer.toString());
			 conn = this.getConnection();
			 pstmt = conn.prepareStatement(buffer.toString());

			 int nIndex = 1;

			 //pstmt.setLong(nIndex++, info.getID());
			 pstmt.setTimestamp(nIndex++, info.getGLDate());
			 pstmt.setLong(nIndex++, info.getOfficeID());
			 pstmt.setLong(nIndex++, info.getCurrencyID());
			 pstmt.setString(nIndex++, info.getGLSubjectCode());
			 pstmt.setLong(nIndex++, info.getBalanceDirection());
			 //pstmt.setLong(nIndex++, info.getTransDirection());
			 pstmt.setDouble(nIndex++, info.getDebitBalance());
			 pstmt.setDouble(nIndex++, (-1.0)*info.getCreditBalance());
			 pstmt.setLong(nIndex++, info.getDebitNumber());
			 pstmt.setLong(nIndex++, info.getCreditNumber());			 
			 pstmt.setDouble(nIndex++, info.getDebitAmount());
			 pstmt.setDouble(nIndex++, info.getCreditAmount());

			 pstmt.execute();

			 lReturn = 1;
		 }
		 finally
		 {
			 this.cleanup(pstmt);
			 this.cleanup(conn);
		 }

		 return lReturn;			
	
		
	}
	
	private long getNextID() throws SQLException {
	  return getSett_GLBalanceID();
	}	
	
	public Collection findByCondition(GLBalanceInfo info) throws SQLException {
	  	
		long lReturn = -1;


	
		 Connection conn = null;
		 PreparedStatement pstmt = null;
		 ResultSet rset = null;
		 ArrayList list = new ArrayList();

		 StringBuffer buffer = new StringBuffer();
		 buffer.append("SELECT * FROM \n");
		 buffer.append(strTableName);
		
		 buffer.append("\n WHERE DTGLDATE = to_date('" + info.getGLDate().toString().substring(0, 10) + "','yyyy-mm-dd') \n");
		 if(info.getOfficeID() > 0){
			buffer.append(" AND NOFFICEID = " + info.getOfficeID());
		 }
		 
		 if(info.getCurrencyID() > 0){
			buffer.append(" AND NCURRENCYID = " + info.getCurrencyID());
		 }
			
		 if(info.getGLSubjectCode()!=null && !info.getGLSubjectCode().equals("")){
			buffer.append(" AND SGLSUBJECTCODE = '" + info.getGLSubjectCode()+ "'");
		 }
		 //Please add other conditions if you need
		 
		try
		  {
			
			  conn = this.getConnection();
			  log.info(buffer.toString());
			  pstmt = conn.prepareStatement(buffer.toString());
			  //pstmt.setTimestamp(1,info.getGLDate());
			  
			 	  
			  rset = pstmt.executeQuery();

			  while(rset.next())
			  {
				GLBalanceInfo tmp = new GLBalanceInfo();

				  tmp.setID(rset.getLong("ID"));
				  tmp.setBalanceDirection(rset.getLong("NBALANCEDIRECTION"));
				  tmp.setCreditAmount(rset.getDouble("MCREDITAMOUNT"));
				  tmp.setCreditBalance((-1.0)*rset.getDouble("MCREDITBALANCE"));
				  tmp.setCreditNumber(rset.getLong("NCREDITNUMBER"));	
				  tmp.setCurrencyID(rset.getLong("NCURRENCYID"));
				  tmp.setDebitAmount(rset.getDouble("MDEBITAMOUNT"));
				  tmp.setDebitBalance(rset.getDouble("MDEBITBALANCE"));
				  tmp.setDebitNumber(rset.getLong("NDEBITNUMBER"));
				  tmp.setGLDate(rset.getTimestamp("DTGLDATE"));
				  tmp.setGLSubjectCode(rset.getString("SGLSUBJECTCODE"));
				  tmp.setOfficeID(rset.getLong("NOFFICEID"));
				  //tmp.setTransDirection(rset.getLong(""))
				  list.add(tmp);			  
			  }

		  }
		  finally
		  {
			  this.cleanup(rset);
			  this.cleanup(pstmt);
			  this.cleanup(conn);

		  }

  	
		return list;		
	}
	
	
	public void update(GLBalanceInfo info)  throws SQLException {
		long lReturn = -1;

		 Connection conn = null;
		 PreparedStatement pstmt = null;

		 StringBuffer buffer = new StringBuffer(64);
		 buffer.append("update "+ strTableName +" set \n");
		buffer.append("dtGlDate = ?,\n");
		buffer.append("nOfficeID = ?,\n");
		buffer.append("nCurrencyID = ?,\n");
		buffer.append("sGlSubjectCode = ?,\n");
		buffer.append("nBalanceDirection = ?,\n");
		//buffer.append("nTransDirection,\n ");
		buffer.append("mDebitBalance = ?,\n");
		buffer.append("mCreditBalance  = ?, \n ");
		buffer.append("NDEBITNUMBER = ?,\n");
		buffer.append("NCREDITNUMBER = ?, \n ");		 
		buffer.append("MDEBITAMOUNT = ?,\n");
		buffer.append("MCREDITAMOUNT = ? \n ");		
		 buffer.append("where id=?\n");
		
		log.debug(buffer.toString());
		 try
		 {
			 conn = this.getConnection();
			 pstmt = conn.prepareStatement(buffer.toString());

			 int nIndex = 1;

			pstmt.setTimestamp(nIndex++, info.getGLDate());
			pstmt.setLong(nIndex++, info.getOfficeID());
			pstmt.setLong(nIndex++, info.getCurrencyID());
			pstmt.setString(nIndex++, info.getGLSubjectCode());
			pstmt.setLong(nIndex++, info.getBalanceDirection());
			//pstmt.setLong(nIndex++, info.getTransDirection());
			pstmt.setDouble(nIndex++, info.getDebitBalance());
			pstmt.setDouble(nIndex++, (-1.0)*info.getCreditBalance());
			pstmt.setLong(nIndex++, info.getDebitNumber());
			pstmt.setLong(nIndex++, info.getCreditNumber());			 
			pstmt.setDouble(nIndex++, info.getDebitAmount());
			pstmt.setDouble(nIndex++, info.getCreditAmount());
			pstmt.setLong(nIndex++, info.getID());
			 pstmt.execute();


		 }
		 finally
		 {
				 this.cleanup(pstmt);
				 this.cleanup(conn);

		 }

		
	}
	
}
