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
import java.util.Vector;

import com.iss.itreasury.settlement.base.SettlementDAO;
import com.iss.itreasury.settlement.base.SettlementDAOException;
import com.iss.itreasury.settlement.base.SettlementException;
import com.iss.itreasury.settlement.setting.dataentity.SubAccountTypeLoanSettingInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;

/**
 * @author ruixie
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class Sett_SubAccountType_LoanSettingDAO extends SettlementDAO
{
	public Sett_SubAccountType_LoanSettingDAO ()
	{
		super ("Sett_SubAccountType_Loan",true) ;
		// TODO Auto-generated constructor stub
	}

	public Collection findByAccountTypeID(long lAccountTypeID,long lOfficeID,long lCurrencyID) throws SettlementException
	{
		StringBuffer strSQLBuff = new StringBuffer();
		try
		{
			initDAO();
			//返回需求的结果集
			strSQLBuff = new StringBuffer();
			strSQLBuff.append(" select * from SETT_SUBACCOUNTTYPE_LOAN \n");
			strSQLBuff.append(" where nOfficeID = "+lOfficeID+" and nCurrencyID = "+lCurrencyID+" and nStatusID = "+Constant.RecordStatus.VALID+" \n");
			if(lAccountTypeID > 0)
			{
				strSQLBuff.append(" and nAccountTypeID = "+lAccountTypeID );
			}
			strSQLBuff.append(" order by nSerialNo ");
			System.out.println(strSQLBuff.toString());
			
			prepareStatement(strSQLBuff.toString());
			executeQuery();
			SubAccountTypeLoanSettingInfo subAccountLoanInfo = new SubAccountTypeLoanSettingInfo(); 
			Collection c = getDataEntitiesFromResultSet(subAccountLoanInfo.getClass());
			finalizeDAO();
			return c;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			e.printStackTrace();throw new SettlementException();
		}
	}
	
		public boolean isLoanSettingExist(SubAccountTypeLoanSettingInfo subLoanInfo)throws Exception
		{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rset = null;
			boolean bResult = false;
			try
			{
				StringBuffer bufferSQL = new StringBuffer();
				bufferSQL.append(" select * from SETT_SUBACCOUNTTYPE_LOAN \n");
				bufferSQL.append(" where nOfficeID = " + subLoanInfo.getOfficeId() + " and nCurrencyID = " + subLoanInfo.getCurrencyId() + " and nStatusID = " + Constant.RecordStatus.VALID + " \n");
				bufferSQL.append(" and NACCOUNTTYPEID="+subLoanInfo.getAccountTypeId());
				bufferSQL.append(" and nvl(NLOANTYPEID,-1)="+subLoanInfo.getLoanTypeId());
				bufferSQL.append(" and nvl(NLOANMONTHID,-1)="+subLoanInfo.getLoanMonthId());
				bufferSQL.append(" and nvl(NLOANYEARID,-1)="+subLoanInfo.getLoanYearId());
				bufferSQL.append(" and nvl(NCONSIGNERCLIENTID,-1)="+subLoanInfo.getConsignerClientId());
				bufferSQL.append(" and nvl(NDRAFTTYPEID,-1)="+subLoanInfo.getDraftTypeId());
				bufferSQL.append(" and nvl(NLOANMONTHSTART,-1)<="+subLoanInfo.getLoanMonthStart());
				bufferSQL.append(" and nvl(NLOANMONTHEND,-1)>="+subLoanInfo.getLoanMonthEnd());
				bufferSQL.append(" and nvl(NCLIENTID,-1)="+subLoanInfo.getClientId());
				bufferSQL.append(" and nvl(NTRANSDISCOUNTTYPEID,-1)="+subLoanInfo.getTransDiscountTypeId());
				bufferSQL.append(" and nvl(NASSURETYPEID,-1)="+subLoanInfo.getAssureTypeID());
		        //add by xwhe 2008-5-4		
				bufferSQL.append(" and nvl(ncontractid,-1)="+subLoanInfo.getContractId());
				bufferSQL.append(" and nvl(nloannoteid,-1)="+subLoanInfo.getLoanNoteId());
				bufferSQL.append(" and id!="+subLoanInfo.getId());
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
	
	public long getMaxSerialNo(SubAccountTypeLoanSettingInfo subLoanInfo) throws SettlementException
	{
		long lResult = -1;
		StringBuffer strSQLBuff = new StringBuffer();
		try
		{
			initDAO();
			//返回需求的结果集
			strSQLBuff = new StringBuffer();
			strSQLBuff.append(" select nvl(max(nSerialNo),0)+1 serialNo from SETT_SUBACCOUNTTYPE_LOAN where nOfficeID = "+subLoanInfo.getOfficeId()+" and nCurrencyID = "+subLoanInfo.getCurrencyId()+" and nAccountTypeID = "+subLoanInfo.getAccountTypeId()+" and nStatusID = 1 ");
			System.out.println(strSQLBuff.toString());
			
			prepareStatement(strSQLBuff.toString());
			ResultSet rs = executeQuery();
			if(rs.next())
			{
				lResult = rs.getLong("serialNo");
			}
			finalizeDAO();
			return lResult;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			e.printStackTrace();throw new SettlementException();
		}
	}
	
	
	
}
