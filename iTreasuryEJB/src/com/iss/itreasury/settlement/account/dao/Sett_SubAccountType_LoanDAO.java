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
public class Sett_SubAccountType_LoanDAO extends SettlementDAO
{

	private Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);

	public Sett_SubAccountType_LoanDAO()
	{
		strTableName = "sett_subaccounttype_loan";
	}
	
	public Sett_SubAccountType_LoanDAO(Connection conn)
	{
		super(conn);
		strTableName = "sett_subaccounttype_loan";
	}	

	public String findAccountSubjectCode(AccountTypeInfo accoutType, long loanTypeID, long consignClientID, long intervalNum, long draftType, long loanYearID,long clientID,long discountTypeID,long assureTypeID, long officeID, long currencyID) throws SQLException
	{
		return getSubjectCode(accoutType, loanTypeID, consignClientID,intervalNum, loanYearID,draftType, clientID, discountTypeID,assureTypeID, SUBJECT_ACCOUNT, officeID, currencyID);
	}

	public String findInterestSubjectCode(AccountTypeInfo accountType, long loanTypeID, long consignClientID, long intervalNum,long draftType,  long loanYearID,long clientID,long discountTypeID, long assureTypeID, long officeID, long currencyID) throws SQLException
	{
		return getSubjectCode(accountType, loanTypeID, consignClientID,intervalNum, loanYearID,draftType, clientID, discountTypeID, assureTypeID, SUBJECT_INTEREST, officeID, currencyID);
	}

	public String findPredrawInterestSubjectCode(AccountTypeInfo accountType, long loanTypeID, long consignClientID, long intervalNum,long draftType,  long loanYearID,long clientID,long discountTypeID,long assureTypeID,  long officeID, long currencyID) throws SQLException
	{
		return getSubjectCode(accountType, loanTypeID, consignClientID, intervalNum, loanYearID,draftType, clientID, discountTypeID, assureTypeID, SUBJECT_PREDRAWINTEREST, officeID, currencyID);
	}
	
	public String findInterestTaxSubjectCode(AccountTypeInfo accountType, long loanTypeID, long consignClientID, long intervalNum,long draftType,  long loanYearID,long clientID,long discountTypeID,long assureTypeID,  long officeID, long currencyID) throws SQLException
	{
		return getSubjectCode(accountType, loanTypeID, consignClientID, intervalNum, loanYearID,draftType, clientID, discountTypeID, assureTypeID, SUBJECT_TAXINTEREST, officeID, currencyID);
	}
	
	public String findCommissionSubjectCode(AccountTypeInfo accountType, long loanTypeID, long consignClientID, long intervalNum,long draftType,  long loanYearID,long clientID,long discountTypeID,long assureTypeID,  long officeID, long currencyID) throws SQLException
	{
		return getSubjectCode(accountType, loanTypeID, consignClientID, intervalNum, loanYearID,draftType, clientID, discountTypeID, assureTypeID, SUBJECT_COMMISSION, officeID, currencyID);
	}	

	private final static int SUBJECT_ACCOUNT = 0;
	private final static int SUBJECT_INTEREST = 1;
	private final static int SUBJECT_PREDRAWINTEREST = 2;
	private final static int SUBJECT_TAXINTEREST = 3;
	private final static int SUBJECT_COMMISSION = 4;
	private final static int SUBJECT_NEGOTIATEINTEREST = 5;
	private String getSubjectCode(AccountTypeInfo accountType, long loanTypeID, long consignClientID, long intervalNum, long loanYearID,long draftType,long clientID,long discountTypeID, long assureTypeID,int subjectType, long officeID, long currencyID) throws SQLException
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
						selectColumnName = "SINTERESTSUBJECT";
					}
					break;
				case SUBJECT_PREDRAWINTEREST :
					{
						selectColumnName = "SBOOKEDINTERESTSUBJECT";
					}
					break;
				case SUBJECT_TAXINTEREST :
					{
						selectColumnName = "SINTERESTTAXSUBJECT";
					}
					break;		
				case SUBJECT_COMMISSION :
					{
						selectColumnName = "SCOMMISSIONSUBJECT";
					}
					break;
					
							
			}

			StringBuffer bufferSQL = new StringBuffer();
			bufferSQL.append("SELECT " + selectColumnName + " FROM " + strTableName + " WHERE NACCOUNTTYPEID = " + accountType.getId());
			bufferSQL.append(" AND nstatusid = 1");
			
			if (accountType.getIsLoanType() == 1)
			{
				bufferSQL.append(" AND NLOANTYPEID = " + loanTypeID);

			}

			if (accountType.getIsLoanMonth() == 1)
			{
				bufferSQL.append(" AND nloanmonthstart <= " + intervalNum + " AND nloanmonthend >= " + intervalNum);
			}

			if (accountType.getIsLoanYear() == 1)
			{
				bufferSQL.append(" AND nloanYearID = " + loanYearID);
			}
			
			if (accountType.getIsCosign() == 1)
			{
				bufferSQL.append(" AND NCONSIGNERCLIENTID = " + consignClientID);
			}
			
			if (accountType.getIsDraftType() == 1)
			{
				bufferSQL.append(" AND NDRAFTTYPEID = " + draftType);
			}	
			if(accountType.getIsClientID() == 1)
			{
			
				bufferSQL.append(" AND NCLIENTID = " + clientID);
			
			}
			if(accountType.getIsReDiscountType() == 1)
			{
				bufferSQL.append(" AND NTRANSDISCOUNTTYPEID = " + discountTypeID);
			}
			
			if(accountType.getIsAssure() == 1)
			{
			
				bufferSQL.append(" AND NASSURETYPEID = " + assureTypeID);
			}	
			
			//added by mzh_fu 2008/04/29 国电需求，对于自营贷款、委托贷款、银团贷款 增加按合同和放款通知单下级分类，其它贷款业务不做处理
			if(accountType.getSubAccountTypeLoanSettingInfo() != null){
				if(accountType.getIsContract() == 1)
				{			
					bufferSQL.append(" AND nContractID = " + accountType.getSubAccountTypeLoanSettingInfo().getContractId());
				}	
				
				if(accountType.getIsLoanNote() == 1)
				{			
					bufferSQL.append(" AND nLoanNoteId = " + accountType.getSubAccountTypeLoanSettingInfo().getLoanNoteId());
				}
			}			
			
			bufferSQL.append(" AND NOFFICEID = " + officeID);			
			bufferSQL.append(" AND NCURRENCYID = " + currencyID);			
			String strSQL = bufferSQL.toString();
			log.debug(strSQL);
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
