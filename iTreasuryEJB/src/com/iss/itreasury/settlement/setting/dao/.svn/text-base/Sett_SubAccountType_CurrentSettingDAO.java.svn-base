/*
 * Created on 2004-9-3
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.settlement.setting.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collection;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.settlement.account.dataentity.AccountTypeInfo;
import com.iss.itreasury.settlement.base.SettlementDAO;
import com.iss.itreasury.settlement.base.SettlementException;
import com.iss.itreasury.settlement.setting.dataentity.SubAccountTypeCurrentSettingInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;
/**
 * @author ruixie
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class Sett_SubAccountType_CurrentSettingDAO extends SettlementDAO
{
	public Sett_SubAccountType_CurrentSettingDAO()
	{
		super("Sett_SubAccountType_Current", true);
		this.setUseMaxID();
		// TODO Auto-generated constructor stub
	}
	private final static int SUBJECT_ACCOUNT = 0;
	private final static int SUBJECT_INTEREST = 1;
	private final static int SUBJECT_PREDRAWINTEREST = 2;
	private final static int SUBJECT_TAXINTEREST = 3;
	private final static int SUBJECT_COMMISSION = 4;
	private final static int SUBJECT_NEGOTIATEINTEREST = 5;
	public Collection findByAccountTypeID(long lAccountTypeID, long lOfficeID, long lCurrencyID) throws SettlementException
	{
		StringBuffer strSQLBuff = new StringBuffer();
		try
		{
			initDAO();
			//返回需求的结果集
			strSQLBuff = new StringBuffer();
			strSQLBuff.append(" select * from Sett_SubAccountType_Current \n");
			strSQLBuff.append(" where nOfficeID = " + lOfficeID + " and nCurrencyID = " + lCurrencyID + " and nStatusID = " + Constant.RecordStatus.VALID + " \n");
			if (lAccountTypeID > 0)
			{
				strSQLBuff.append(" and nAccountTypeID = " + lAccountTypeID);
			}
			strSQLBuff.append(" order by id ");
			System.out.println(strSQLBuff.toString());
			prepareStatement(strSQLBuff.toString());
			executeQuery();
			SubAccountTypeCurrentSettingInfo subAccountCurrentInfo = new SubAccountTypeCurrentSettingInfo();
			Collection c = getDataEntitiesFromResultSet(subAccountCurrentInfo.getClass());
			finalizeDAO();
			return c;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new SettlementException();
		}
	}
	public boolean isCurrentSettingExist(SubAccountTypeCurrentSettingInfo currentSettingInfo)throws Exception
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		boolean bResult = false;
		try
		{
			StringBuffer bufferSQL = new StringBuffer();
			bufferSQL.append(" select * from Sett_SubAccountType_Current \n");
			bufferSQL.append(" where nOfficeID = " + currentSettingInfo.getOfficeID() + " and nCurrencyID = " + currentSettingInfo.getCurrencyID() + " and nStatusID = " + Constant.RecordStatus.VALID + " \n");
			bufferSQL.append(" and NACCOUNTTYPEID="+currentSettingInfo.getAccountTypeID());
			bufferSQL.append(" and nvl(NCLIENTID,-1)="+currentSettingInfo.getClientID());
			bufferSQL.append(" and nvl(NACCOUNTID,-1)="+currentSettingInfo.getAccountId());
			bufferSQL.append(" and id!="+currentSettingInfo.getId());
			log.debug(bufferSQL.toString());
			conn = Database.getConnection();
			pstmt = conn.prepareStatement(bufferSQL.toString());
			rset = pstmt.executeQuery();
			if(rset.next())
			{
				bResult = true;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			if (rset != null)
			{
				rset.close();
			}
			if (pstmt != null)
			{
				pstmt.close();
			}
			if (conn != null)
			{
				conn.close();
			}
		}
		return bResult;
	}
	public String findAccountSubjectCodeByAccountTypeIDAndClientID(AccountTypeInfo accountType, long officeID, long currencyID, long clientID) throws Exception
	{
		return getSubjectCode(accountType, SUBJECT_ACCOUNT, officeID, currencyID, clientID);
	}
	public String findPayInterestSubjectCodeByAccountTypeIDAndClientID(AccountTypeInfo accountType, long officeID, long currencyID, long clientID) throws Exception
	{
		return getSubjectCode(accountType, SUBJECT_INTEREST, officeID, currencyID, clientID);
	}
	public String findPredrawInterestSubjectCodeByAccountTypeIDAndClientID(AccountTypeInfo accountType, long officeID, long currencyID, long clientID) throws Exception
	{
		return getSubjectCode(accountType, SUBJECT_PREDRAWINTEREST, officeID, currencyID, clientID);
	}
	public String findCommissionSubjectCodeByAccountTypeIDAndClientID(AccountTypeInfo accountType, long officeID, long currencyID, long clientID) throws Exception
	{
		return getSubjectCode(accountType, SUBJECT_COMMISSION, officeID, currencyID, clientID);
	}
	public String findNegotiateInterestSubjectCodeByAccountTypeIDAndClientID(AccountTypeInfo accountType, long officeID, long currencyID, long clientID) throws Exception
	{
		return getSubjectCode(accountType, SUBJECT_NEGOTIATEINTEREST, officeID, currencyID, clientID);
	}
	private String getSubjectCode(AccountTypeInfo accountType, int subjectType, long officeID, long currencyID, long clientID) throws Exception
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
				case SUBJECT_NEGOTIATEINTEREST :
					{
						selectColumnName = "SNEGOTIATEINTERESTSUBJECT";
					}
					break;
				case SUBJECT_COMMISSION :
				{
					selectColumnName = "sCommissionSubject";
				}
					break;
			}
			StringBuffer bufferSQL = new StringBuffer();
			bufferSQL.append("SELECT " + selectColumnName + " FROM " + strTableName + " WHERE NACCOUNTTYPEID = " + accountType.getId());
			bufferSQL.append(" AND nstatusid = 1");
			if (accountType.getIsClientID() == 1)
			{
				bufferSQL.append(" AND NCLIENTID = " + clientID);
			}
			
			//added by mzh_fu 2008/04/29 国电需求，对于活期增加按账户下级分类；保证金按账户、存单下级分类
			if (accountType.getIsAccount() == 1)
			{
				long lTempAccountId = -1;
				
				if(accountType.getSubAccountTypeCurrentSettingInfo() != null){
					lTempAccountId = accountType.getSubAccountTypeCurrentSettingInfo().getAccountId();
				}else{
					lTempAccountId = accountType.getSubAccountTypeFixedDepositInfo().getAccountId();
				}
				bufferSQL.append(" AND nAccountID = " + lTempAccountId);
			}
			
			if(accountType.getIsDeposit() == 1 && accountType.getSubAccountTypeFixedDepositInfo() != null){
				bufferSQL.append(" AND sDepositNo = '" + accountType.getSubAccountTypeFixedDepositInfo().getDepositNo() + "' ");
			}
			
			bufferSQL.append(" AND NOFFICEID = " + officeID);
			bufferSQL.append(" AND NCURRENCYID = " + currencyID);
			String strSQL = bufferSQL.toString();
			log.debug(strSQL);
			conn = Database.getConnection();
			pstmt = conn.prepareStatement(strSQL);
			rset = pstmt.executeQuery();
			if (rset.next())
			{
				res = rset.getString(selectColumnName);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			if (rset != null)
			{
				rset.close();
			}
			if (pstmt != null)
			{
				pstmt.close();
			}
			if (conn != null)
			{
				conn.close();
			}
		}
		return res;
	}
	
	public void updateSubType(SubAccountTypeCurrentSettingInfo subCurrentInfo) throws Exception
	{
	    Connection conn = null;
		PreparedStatement pstmt = null;
		StringBuffer sb = new StringBuffer();
	    try{
			sb.append(" UPDATE Sett_SubAccountType_Current ");
			sb.append(" set NSTATUSID = " + subCurrentInfo.getStatusID());
			sb.append(" where naccounttypeid = " + subCurrentInfo.getAccountTypeID());
			
			conn = Database.getConnection();
			pstmt = conn.prepareStatement(sb.toString());
			System.out.println("sql:"+sb.toString());
			pstmt.executeUpdate();
	    }
	    catch(ITreasuryDAOException e)
	    {
	        e.printStackTrace();
	        throw e;
	    }
	    finally {
		finalizeDAO();
	    }
	}
	
	public static void main(String[] args)
	{
	    Sett_SubAccountType_CurrentSettingDAO subCurrentDAO = new Sett_SubAccountType_CurrentSettingDAO();
	    SubAccountTypeCurrentSettingInfo subCurrentInfo = new SubAccountTypeCurrentSettingInfo();
		subCurrentInfo.setAccountTypeID(15);
		subCurrentInfo.setStatusID(Constant.RecordStatus.INVALID);
		try {
            subCurrentDAO.updateSubType(subCurrentInfo);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}
}
