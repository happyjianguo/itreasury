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
 * @author 
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class Sett_OtherAccountType_OtherSettingDAO extends SettlementDAO
{
	public Sett_OtherAccountType_OtherSettingDAO()
	{
		super("Sett_SubAccountType_Transfer", true);
		this.setUseMaxID();
	}
    //信贷资产转让科目类型
	private final static int SUBJECT_TYPE_REPURCHASE_NOTIFY = 6;//卖出回购金融资产款
	private final static int SUBJECT_TYPE_EXPENSE = 7;//金融企业往来利息支出科目
	private final static int SUBJECT_TYPE_HANDLE = 8;//金融企业往来应付利息科目
	private final static int SUBJECT_TYPE_ZYAMOUNT = 9;//自营贷款本金科目
	private final static int SUBJECT_TYPE_MDAMOUNT = 10;//应付卖断本金科目
	private final static int SUBJECT_TYPE_MDINTEREST = 11;//应付卖断利息科目
	private final static int SUBJECT_TYPE_ZRCOMMISSION = 12;//手续费及佣金收入科目
	public Collection findByAccountTypeID(long lAccountTypeID, long lOfficeID, long lCurrencyID) throws SettlementException
	{
		StringBuffer strSQLBuff = new StringBuffer();
		try
		{
			initDAO();
			//返回需求的结果集
			strSQLBuff = new StringBuffer();
			strSQLBuff.append(" select * from Sett_SubAccountType_Transfer \n");
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
			bufferSQL.append(" select * from Sett_SubAccountType_Transfer \n");
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
	public String findRepurchaseSubjectCodeByAccountTypeIDAndClientID(long  contractID, long officeID, long currencyID, long clientID) throws Exception
	{
		return getSubjectCode(contractID, SUBJECT_TYPE_REPURCHASE_NOTIFY, officeID, currencyID, clientID);
	}
	public String findExpenseSubjectCodeByAccountTypeIDAndClientID(long  contractID, long officeID, long currencyID, long clientID) throws Exception
	{
		return getSubjectCode(contractID, SUBJECT_TYPE_EXPENSE, officeID, currencyID, clientID);
	}
	public String findHandleSubjectCodeByAccountTypeIDAndClientID(long  contractID, long officeID, long currencyID, long clientID) throws Exception
	{
		return getSubjectCode(contractID, SUBJECT_TYPE_HANDLE, officeID, currencyID, clientID);
	}
	public String findZyAmountSubjectCodeByAccountTypeIDAndClientID(long  contractID, long officeID, long currencyID, long clientID) throws Exception
	{
		return getSubjectCode(contractID, SUBJECT_TYPE_ZYAMOUNT, officeID, currencyID, clientID);
	}
	public String findMDAmountSubjectCodeByAccountTypeIDAndClientID(long  contractID, long officeID, long currencyID, long clientID) throws Exception
	{
		return getSubjectCode(contractID, SUBJECT_TYPE_MDAMOUNT, officeID, currencyID, clientID);
	}
	public String findMDInterestSubjectCodeByAccountTypeIDAndClientID(long  contractID, long officeID, long currencyID, long clientID) throws Exception
	{
		return getSubjectCode(contractID, SUBJECT_TYPE_MDINTEREST, officeID, currencyID, clientID);
	}
	public String findZRCommissionSubjectCodeByAccountTypeIDAndClientID(long  contractID, long officeID, long currencyID, long clientID) throws Exception
	{
		return getSubjectCode(contractID, SUBJECT_TYPE_ZRCOMMISSION, officeID, currencyID, clientID);
	}
	private String getSubjectCode(long  contractID, int subjectType, long officeID, long currencyID, long clientID) throws Exception
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
				case SUBJECT_TYPE_REPURCHASE_NOTIFY :
					{
						selectColumnName = "FINCECAPITALSUBJECT";//卖出回购金融资产款
					}
					break;
				case SUBJECT_TYPE_EXPENSE :
					{
						selectColumnName = "FINCEREPAYINTESUBJECT";//金融企业往来利息支出科目
					}
					break;
				case SUBJECT_TYPE_HANDLE :
					{
						selectColumnName = "FINCEPAYINTESUBJECT";//金融企业往来应付利息科目
					}
					break;
				case SUBJECT_TYPE_ZYAMOUNT :
					{
						selectColumnName = "ZYAMOUNTSUBJECT";////自营贷款本金科目
					}
					break;
				case SUBJECT_TYPE_MDAMOUNT :
				    {
					selectColumnName = "PAYAMOUNTSUBJECT";//应付卖断本金科目
				    }
					break;
				case SUBJECT_TYPE_MDINTEREST :
				    {
					selectColumnName = "PAYINTERESTSUBJECT";//应付卖断利息科目
				    }
					break;
				case SUBJECT_TYPE_ZRCOMMISSION :
				   {
					selectColumnName = "SCOMMISSIONSUBJECT";//手续费佣金收入科目
				   }
					break;
			}
			StringBuffer bufferSQL = new StringBuffer();
			bufferSQL.append("SELECT " + selectColumnName + " FROM " + strTableName + " WHERE CONTRACTID = " +  contractID);
			bufferSQL.append(" AND statusid = 1");		
			bufferSQL.append(" AND OFFICEID = " + officeID);
			bufferSQL.append(" AND CURRENCYID = " + currencyID);
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
	    Sett_OtherAccountType_OtherSettingDAO subCurrentDAO = new Sett_OtherAccountType_OtherSettingDAO();
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
