/**
 * <p>Title:关机时对合同进行的操作 </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: isoftstone </p>
 * <p>created by ：fanyang</p>
 * <p>date ：2004-07-14</p>
 * <p>modified by :      </p>
 * <p>version 1.0</p>
 * <p>Description: </p>
 */
package com.iss.itreasury.closesystem;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.Collection;

import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.securities.util.*;

import com.iss.itreasury.util.Log4j;

public class SecContractOperation
{
	//	日志
	private Log4j log = new Log4j(Constant.ModuleType.SECURITIES, this);

	public SecContractOperation()
	{
	}
	/**
	* 扫描所有的合同,将其中状态是未执行的状态改为已执行,条件是未执行状态的合同
	* 下至少有一个通知单的状态是已审核
	* @param lOfficeID 办事处标识
	* @param lCurrencyID 币种标识  
	* @param tsDate 开/关机时间 
	* Description: 
	* ----待确定
	*/
	public boolean updateContractStatus(Connection conn, long lOfficeID, long lCurrencyID) throws Exception
	{
		boolean bIsPassed = true;
		PreparedStatement ps= null;
		try
		{
			String sql = "";
			sql = " update sec_applycontract set statusid = " + SECConstant.ContractStatus.ACTIVE
				+ " where id in ( "
				+ " select distinct a.id from sec_applycontract a,sec_noticeform b "
				+ " where a.id =  b.contractid "
				+ " and a.statusid = " + SECConstant.ContractStatus.NOTACTIVE
				+ " and a.currencyid = " + lCurrencyID
				+ " and a.officeid = " + lOfficeID
				+ " and b.statusid in (" + SECConstant.NoticeFormStatus.CHECKED + "," + SECConstant.NoticeFormStatus.USED + "," + SECConstant.NoticeFormStatus.COMPLETED + "," + SECConstant.NoticeFormStatus.POSTED + "))";
				
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
//			if (bIsPassed)
//			{
//				
//			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			bIsPassed = false;
			// 释放数据库连接等
			if (ps != null)
			{
				ps.close();
				ps = null;
			}
		}
		finally
		{
			// 释放数据库连接等
			if (ps != null)
			{
				ps.close();
				ps = null;
			}
		}
		return bIsPassed;
	}
	public static void main(String args[]) throws Exception
	{
		SecContractOperation test = new SecContractOperation();
		Connection conn = Database.getConnection();
		test.updateContractStatus(conn,1,1);
		System.out.println("yeah");
	}
}
