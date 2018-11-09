package com.iss.itreasury.settlement.Liquidation.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;

import com.iss.itreasury.settlement.base.SettlementDAO;
import com.iss.itreasury.settlement.transcurrentdeposit.dataentity.TransCurrentDepositInfo;

public class LiquidationDao extends SettlementDAO{
	
	public LiquidationDao()
	{
		super("Sett_Liquidation");
		setUseMaxID();
	}
	
	public LiquidationDao(Connection conn)
	{
		super("Sett_Liquidation",conn);
		setUseMaxID();
	}
	/**
	 * 根据交易号得到清算交易主表ID
	 * @param sTransno
	 * @return
	 * @throws Exception
	 */
	public long getIDbyTransno(String sTransno) throws Exception
	{
		long id = -1;
		try
		{
			initDAO();
			StringBuffer strSQLBuffer = new StringBuffer();
			strSQLBuffer.append("select id from Sett_Liquidation where sTransno = ? and STATUSID > 0\n");
			String strSQL = strSQLBuffer.toString();
			
			prepareStatement(strSQL);
			transPS.setString(1, sTransno);
			transRS = executeQuery();
			if(transRS!=null&&transRS.next())
			{
				id = transRS.getLong(1);
			}
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
		return id;
	}

}
