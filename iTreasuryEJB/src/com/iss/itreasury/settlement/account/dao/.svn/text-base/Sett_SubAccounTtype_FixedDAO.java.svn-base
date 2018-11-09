package com.iss.itreasury.settlement.account.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.settlement.account.dataentity.AccountTypeInfo;
import com.iss.itreasury.util.*;

/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2003-10-17
 */
public class Sett_SubAccounTtype_FixedDAO extends SettlementDAO
{
	private Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);

	public Sett_SubAccounTtype_FixedDAO()
	{
		strTableName = "sett_subaccounttype_fixed";
	}
	
	public Sett_SubAccounTtype_FixedDAO(Connection conn)
	{
		super(conn);
		strTableName = "sett_subaccounttype_fixed";
	}	
	/**
	 * 新增条件：客户ID added By Huang Ye @2004-11-26
	 * */
	public String findAccountSubjectCodeByAccountTypeIDAndFixDepositMonthID(AccountTypeInfo accoutType, long fixDepositMonthID, long officeID, long currencyID,long clientID) throws SQLException
	{
		return getSubjectByAccountTypeIDAndFixDepositMonthID(accoutType, fixDepositMonthID, SUBJECT_ACCOUNT, officeID, currencyID,clientID);
	}
	/**
	 * 新增条件：客户ID added By Huang Ye @2004-11-26
	 * */
	public String findInterestSubjectCodeByAccountTypeIDAndFixDepositMonthID(AccountTypeInfo accoutType, long fixDepositMonthID, long officeID, long currencyID,long clientID) throws SQLException
	{
		return getSubjectByAccountTypeIDAndFixDepositMonthID(accoutType, fixDepositMonthID, SUBJECT_INTEREST, officeID, currencyID,clientID);
	}
	/**
	 * 新增条件：客户ID added By Huang Ye @2004-11-26
	 * */
	public String findPredrawInterestSubjectCodeByAccountTypeIDAndFixDepositMonthID(AccountTypeInfo accoutType, long fixDepositMonthID, long officeID, long currencyID,long clientID) throws SQLException
	{
		return getSubjectByAccountTypeIDAndFixDepositMonthID(accoutType, fixDepositMonthID, SUBJECT_PREDRAWINTEREST, officeID, currencyID,clientID);
	}

	private final static int SUBJECT_ACCOUNT = 0;
	private final static int SUBJECT_INTEREST = 1;
	private final static int SUBJECT_PREDRAWINTEREST = 2;
	private final static int SUBJECT_TAXINTEREST = 3;
	private final static int SUBJECT_COMMISSION = 4;
	private final static int SUBJECT_NEGOTIATEINTEREST = 5;
	
	/**
	 * 新增条件：客户ID added By Huang Ye @2004-11-26
	 * */
	private String getSubjectByAccountTypeIDAndFixDepositMonthID(AccountTypeInfo accoutType, long fixDepositMonthID, int subjectType, long officeID, long currencyID,long clientID) throws SQLException
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String res = "";
		String selectColumnName = "";
		try
		{
			switch (subjectType)
			{
				case SUBJECT_ACCOUNT :
					{
						selectColumnName = "SSUBJECTCODE";
					}
					break;
				case SUBJECT_INTEREST :
					{
						selectColumnName = "SPAYINTERESTSUBJECT";
					}
					break;
				case SUBJECT_PREDRAWINTEREST :
					{
						selectColumnName = "SBOOKEDINTERESTSUBJECT";
					}
					break;
			}
			String strSQL =
				"SELECT " + selectColumnName + " FROM " + strTableName + " WHERE NACCOUNTTYPEID = " + accoutType.getId() + " AND NFIXEDDEPOSITMONTHID = " + fixDepositMonthID
				 + " AND NSTATUSID = " + 1
				 + " AND NOFFICEID = " + officeID
				 + " AND NCURRENCYID = " + currencyID;
			if(accoutType.getIsClientID() == 1){
				strSQL +=  " AND NClientID = " + clientID;
			}
			
			//added by mzh_fu 2008/04/29 国电需求，对于定期、通知增加按账户和存单下级分类
			if(accoutType.getIsAccount() == 1){
				strSQL +=  " AND nAccountID = " + accoutType.getSubAccountTypeFixedDepositInfo().getAccountId();
			}
			if(accoutType.getIsDeposit() == 1){
				strSQL +=  " AND sDepositNo = '" + accoutType.getSubAccountTypeFixedDepositInfo().getDepositNo() + "' ";
			}
			
			log.info(strSQL);
			conn = this.getConnection();
			pstmt = conn.prepareStatement(strSQL);
			rset = pstmt.executeQuery();

			if (rset.next())
			{
				res = rset.getString(selectColumnName);
			}
		}
		finally
		{
			cleanup(rset);
			cleanup(pstmt);
			cleanup(conn);
		}
		return res;
	}

}
