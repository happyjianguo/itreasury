/*
 * Created on 2004-11-18
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.settlement.bankinterface.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;

import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.settlement.bankinterface.dataentity.BankInstructionLogInfo;
import com.iss.itreasury.util.Env;

/**
 * @author mxzhou
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class Sett_BankInstructionLogDAO extends SettlementDAO
{
	/**
	 * 构造方法，由DAO自己维护连接
	 */
	public Sett_BankInstructionLogDAO()
	{
		super();
		this.strTableName = "sett_BankInstructionLog";
	}

	/**
	 * 带参数的构造方法，连接由外部调用者维护
	 * @param conn
	 */
	public Sett_BankInstructionLogDAO(Connection conn)
	{
		super(conn);
		this.strTableName = "sett_BankInstructionLog";
	}
	
	/**
	 * 添加一条记录
	 * @param info
	 * @return long
	 * @throws Exception
	 */
	public void add(BankInstructionLogInfo info) throws Exception
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		StringBuffer buffer = new StringBuffer();
		buffer.append(" insert into \n");
		buffer.append(strTableName);
		buffer.append(" \n (DTRECORD, \n");
		buffer.append("NINSTRID,\n");
		buffer.append("NOPERATORID,\n");
		buffer.append("NOPERATETYPE,\n");
		buffer.append("SPAYACCOUNTNO,\n");
		buffer.append("SRECACCOUNTNO,\n");
		buffer.append("MAMOUNT,\n");
		buffer.append("NSTATUSID,\n");
		buffer.append("NRECEIVEBANK,\n");
		buffer.append("SIDOFBANKSEG1,\n");
		buffer.append("SIDOFBANKSEG2,\n");
		buffer.append("SIDOFBANKSEG3,\n");
		buffer.append("SIDOFBANKSEG4,\n");
		buffer.append("SSTATUSIDOFBANK,\n");
		buffer.append("SSTATUSDESCOFBANK,\n");
		buffer.append("SREMINDOFBANK)\n");

		try
		{
			conn = this.getConnection();

			buffer.append(" values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)\n");

			log.info(buffer.toString());

			pstmt = conn.prepareStatement(buffer.toString());

			int nIndex = 1;
			pstmt.setTimestamp(nIndex++, info.getRecordTime());
			pstmt.setLong(nIndex++, info.getInstrID());
			pstmt.setLong(nIndex++, info.getOperatorID());
			pstmt.setLong(nIndex++, info.getOperateType());
			pstmt.setString(nIndex++, info.getPayAccountNO());
			pstmt.setString(nIndex++, info.getRecAccountNO());
			pstmt.setDouble(nIndex++, info.getAmount());
			pstmt.setLong(nIndex++, info.getStatusID());
			pstmt.setLong(nIndex++, info.getReceiveBank());
			pstmt.setString(nIndex++, info.getIdOfBankSeg1());
			pstmt.setString(nIndex++, info.getIdOfBankSeg2());
			pstmt.setString(nIndex++, info.getIdOfBankSeg3());
			pstmt.setString(nIndex++, info.getIdOfBankSeg4());
			pstmt.setString(nIndex++, info.getStatusIDofBank());
			pstmt.setString(nIndex++, info.getStatusDescOfBank());
			pstmt.setString(nIndex++, info.getRemindOfBank());

			pstmt.execute();
		}
		finally
		{
			this.cleanup(pstmt);
			this.cleanup(conn);
		}
	}
	
	/**
	 * 测试方法
	 * @param args
	 */
	public static void main(String[] args)
	{
		Sett_BankInstructionLogDAO logDAO = new Sett_BankInstructionLogDAO();
		
		BankInstructionLogInfo logInfo = new BankInstructionLogInfo();
		logInfo.setRecordTime(Env.getSystemDateTime());
		
		try
		{
			logDAO.add(logInfo);
			System.out.print("test ok!");
		}
		catch (Exception e)
		{
			System.out.print("test failed!");
			e.printStackTrace();
		}
	}
}
