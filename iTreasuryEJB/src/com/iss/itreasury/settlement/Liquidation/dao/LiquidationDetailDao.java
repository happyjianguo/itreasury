package com.iss.itreasury.settlement.Liquidation.dao;

import java.sql.Connection;

import com.iss.itreasury.settlement.base.SettlementDAO;

public class LiquidationDetailDao extends SettlementDAO{
	
	public LiquidationDetailDao()
	{
		super("Sett_LiquidationDetail");
		setUseMaxID();
	}
	
	public LiquidationDetailDao(Connection conn)
	{
		super("Sett_LiquidationDetail",conn);
		setUseMaxID();
	}
	
	/**
	 * 根据清算交易主表ID删除清算明细表相关明细
	 * @param liquidationid
	 * @return
	 * @throws Exception
	 */
	public void deleteByliquidationid(long liquidationid) throws Exception
	{
		try
		{
			initDAO();
			StringBuffer strSQLBuffer = new StringBuffer();
			strSQLBuffer.append("update Sett_Liquidationdetail set nStatusID = 0 where nliquidationid = ? \n");
			String strSQL = strSQLBuffer.toString();
			prepareStatement(strSQL);
			transPS.setLong(1, liquidationid);
			transRS = executeQuery();
			System.out.print(strSQL);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			finalizeDAO();
		}
	}

}
