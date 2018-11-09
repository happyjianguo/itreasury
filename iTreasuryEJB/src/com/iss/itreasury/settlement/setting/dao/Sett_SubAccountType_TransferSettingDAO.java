package com.iss.itreasury.settlement.setting.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collection;

import com.iss.itreasury.settlement.base.SettlementDAO;
import com.iss.itreasury.settlement.base.SettlementException;
import com.iss.itreasury.settlement.setting.dataentity.TransferSettingInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;

public class Sett_SubAccountType_TransferSettingDAO extends SettlementDAO
{
	public Sett_SubAccountType_TransferSettingDAO()
	{
		super("Sett_SubAccountType_Transfer", true);
		this.setUseMaxID();
	}
	
	public Collection findByAccountTypeID(long lOfficeID, long lCurrencyID) throws SettlementException,Exception
	{
		StringBuffer strSQLBuff = null;
		try
		{
			initDAO();
			strSQLBuff = new StringBuffer();
			strSQLBuff.append(" select * from Sett_SubAccountType_Transfer \n");
			strSQLBuff.append(" where officeID = " + lOfficeID + " and currencyID = " + lCurrencyID + " \n");
			strSQLBuff.append(" and statusID = " + Constant.RecordStatus.VALID + " \n");
			strSQLBuff.append(" order by id desc ");
			System.out.println(strSQLBuff.toString());
			prepareStatement(strSQLBuff.toString());
			executeQuery();
			TransferSettingInfo subTransferInfo = new TransferSettingInfo();
			Collection c = getDataEntitiesFromResultSet(subTransferInfo.getClass());	
			finalizeDAO();
			return c;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new SettlementException();
		}
	}
	
//	public boolean isTransferSettingExist(TransferSettingInfo transferSettingInfo)throws Exception
//	{
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		ResultSet rset = null;
//		boolean bResult = false;
//		try
//		{
//			StringBuffer bufferSQL = new StringBuffer();
//			bufferSQL.append(" select * from Sett_SubAccountType_Transfer \n");
//			bufferSQL.append(" where officeID = " + transferSettingInfo.getOfficeId() + " \n");
//			bufferSQL.append(" and currencyID = " + transferSettingInfo.getCurrencyId() + " \n");
//			bufferSQL.append(" and statusID = " + transferSettingInfo.getStatusId() +" \n");
//			bufferSQL.append(" and CONTRACTTYPEID = " + transferSettingInfo.getContractTypeId() + " \n");
//			bufferSQL.append(" and CONTRACTID = " + transferSettingInfo.getContractId() + " \n");
//			bufferSQL.append(" and id != " + transferSettingInfo.getId());
//			log.debug(bufferSQL.toString());
//			conn = Database.getConnection();
//			pstmt = conn.prepareStatement(bufferSQL.toString());
//			rset = pstmt.executeQuery();
//			if(rset.next())
//			{
//				bResult = true;
//			}
//		}
//		catch (Exception e)
//		{
//			e.printStackTrace();
//			throw e;
//		}
//		finally
//		{
//			if (rset != null)
//			{
//				rset.close();
//			}
//			if (pstmt != null)
//			{
//				pstmt.close();
//			}
//			if (conn != null)
//			{
//				conn.close();
//			}
//		}
//		return bResult;
//	}

}
