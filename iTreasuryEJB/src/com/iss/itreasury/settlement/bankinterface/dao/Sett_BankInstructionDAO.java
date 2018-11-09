package com.iss.itreasury.settlement.bankinterface.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.settlement.bankinterface.dataentity.BankAccountTransInfo;
import com.iss.itreasury.settlement.bankinterface.dataentity.BankInstructionInfo;
import com.iss.itreasury.settlement.bankinterface.dataentity.QueryBankInstructionConditionInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.Log4j;

/**
 * Title: iTreasury Description: Copyright: Copyright (c) 2003 Company:
 * iSoftStone
 * 
 * @author yehuang
 * @version Date of Creation 2004-1-14
 */
public class Sett_BankInstructionDAO extends SettlementDAO
{

	Log4j log4j = null;

	final static public int BankInstruction_StatusID = 10;
	final static public int BankInstruction_ID = 20;
	final static public int BankInstruction_PayAccountNo = 30;
	final static public int BankInstruction_BankType = 40;
	final static public int BankInstruction_RecAccountNo = 50;
	final static public int BankInstruction_Amount = 70;
	final static public int BankInstruction_TransNo = 80;
	final static public int BankInstruction_TransType = 81;
	final static public int BankInstruction_SendDate = 90;
	final static public int BankInstruction_CancelDate = 100;
	final static public int BankInstruction_BankExecuteDate = 110;

	/**
	 * Constructor for Sett_BankInstructionDAO.
	 * 
	 * @param conn
	 */
	public Sett_BankInstructionDAO(Connection conn)
	{
		super(conn);

		log4j = new Log4j(Constant.ModuleType.SETTLEMENT, this);
		strTableName = "Sett_BankInstruction";
	}

	public Sett_BankInstructionDAO()
	{
		log4j = new Log4j(Constant.ModuleType.SETTLEMENT, this);
		strTableName = "Sett_BankInstruction";
	}

	public long add(BankInstructionInfo info) throws Exception
	{
		boolean isSelfManagedCon = false;
		if (transConn != null)
		{
			isSelfManagedCon = true;
		}
		long lReturn = -1;
		Connection conn = null;
		PreparedStatement pstmt = null;
		StringBuffer buffer = new StringBuffer();
		buffer.append(" insert into \n");
		buffer.append(strTableName);
		buffer.append(" \n (ID, \n");
		buffer.append("SPAYACCOUNTNO,\n");
		buffer.append("SPAYACCOUNTNAME,\n");
		buffer.append("SPAYDEPARTMENTNAME,\n");
		buffer.append("SPAYBRANCHNAME,\n");
		buffer.append("MAMOUNT,\n");
		buffer.append("SRECEIVEACCOUNTNO,\n");
		buffer.append("SRECEIVEACCOUNTNAME,\n");
		buffer.append("SRECEIVEDEPARTMENTNAME,\n");
		buffer.append("SRECEIVEBRANCHNAME,\n");
		buffer.append("SRECAREANAMEOFPROVINCE,\n");
		buffer.append("SRECAREANAMEOFCITY,\n");
		buffer.append("SCOMMENT,\n");
		buffer.append("SABSTRACT,\n");
		buffer.append("SSENDERNO,\n");
		buffer.append("SCANCELLERNO,\n");
		buffer.append("DTCREATETIME,\n");
		buffer.append("DTSENDTIME,\n");
		buffer.append("DTCANCELTIME,\n");
		buffer.append("DTMODIFYTIME,\n");
		buffer.append("NSTATUSID,\n");
		buffer.append("STRANSACTIONNO,\n");
		buffer.append("NTRANSTYPE,\n");
		buffer.append("NRECEIVEBANK,\n");
		buffer.append("SIDOFBANKSEG1,\n");
		buffer.append("SIDOFBANKSEG2,\n");
		buffer.append("SIDOFBANKSEG3,\n");
		buffer.append("SIDOFBANKSEG4,\n");
		buffer.append("DTSIGN,\n");
		buffer.append("DTTRANSTIMEOFBANK,\n");
		buffer.append("SSTATUSIDOFBANK,\n");
		buffer.append("SSTATUSDESCOFBANK,\n");
		buffer.append("SREMINDOFBANK,\n");

		//新增数据库字段
		buffer.append("SPAYAREANAMEOFPROVINCE, \n");
		buffer.append("SPAYAREANAMEOFCITY,\n");
		buffer.append("NCURRENCYID,\n");
		buffer.append("NISSAMEBANK,\n");
		buffer.append("NREMITPRIORITY,\n");
		
		//新增中行所需的联行号，机构号，手续费账号
		buffer.append("SBANKEXCHANGECODEOFPAYER, \n");
		buffer.append("SBRANCHCODEOFPAYER,\n");
		buffer.append("SBANKEXCHANGECODEOFREC,\n");
		buffer.append("SBRANCHCODEOFREC,\n");
		buffer.append("SPAYCHARGEACCOUNTNO,\n"); 
		buffer.append("SBANKEXCHANGECODEOFPC,\n");
		buffer.append("SBRANCHCODEOFPC) \n");

		try
		{
			conn = this.getConnection();
			String strQueryDate = Env.getSystemDateString(1, 1);
			strQueryDate.substring(0, 10);
			Timestamp dtQueryDate = DataFormat.getDateTime(strQueryDate);

			buffer.append(" values(?,?,?,?,?,?,?,?,?,?,");
			buffer.append("?,?,?,?,?,?,?,?,?,?,?,");
			buffer.append("?,?,?,?,?,?,?,?,?,?,");
			buffer.append("?,?,?,?,?,?,?,");
			buffer.append("?,?,?,?,?,?,?)\n");

			log.info(buffer.toString());

			pstmt = conn.prepareStatement(buffer.toString());
			long id = getSett_BankInstructionID();
			info.setID(id);
			int nIndex = 1;
			pstmt.setLong(nIndex++, info.getID());

			pstmt.setString(nIndex++, info.getPayAccountNo());
			pstmt.setString(nIndex++, info.getPayAccountName());
			pstmt.setString(nIndex++, info.getPayDepartmentName());
			pstmt.setString(nIndex++, info.getPayBranchName());
			pstmt.setDouble(nIndex++, info.getAmount());
			pstmt.setString(nIndex++, info.getReceiveAccountNo());
			pstmt.setString(nIndex++, info.getReceiveAccountName());
			pstmt.setString(nIndex++, info.getReceiveDepartmentName());
			pstmt.setString(nIndex++, info.getReceiveBranchName());
			pstmt
					.setString(nIndex++, info
							.getReceiveBranchAreaNameOfProvince());
			pstmt.setString(nIndex++, info.getReceiveBranchAreaNameOfCity());
			pstmt.setString(nIndex++, info.getComment());
			pstmt.setString(nIndex++, info.getAbstract());
			pstmt.setString(nIndex++, info.getSenderNo());
			pstmt.setString(nIndex++, info.getCancellerNo());
			pstmt.setTimestamp(nIndex++, info.getCreateTime());
			pstmt.setTimestamp(nIndex++, info.getSendTime());
			pstmt.setTimestamp(nIndex++, info.getCancelTime());
			
			info.setModifyTime(Env.getSystemDateTime());
			pstmt.setTimestamp(nIndex++, info.getModifyTime());
			pstmt.setLong(nIndex++, info.getStatusID());
			pstmt.setString(nIndex++, info.getTransactionNo());
			pstmt.setLong(nIndex++, info.getTransType());
			pstmt.setLong(nIndex++, info.getReceiveBank());
			pstmt.setString(nIndex++, info.getIDOfBankSeg1());
			pstmt.setString(nIndex++, info.getIDOfBankSeg2());
			pstmt.setString(nIndex++, info.getIDOfBankSeg3());
			pstmt.setString(nIndex++, info.getIDOfBankSeg4());
			pstmt.setTimestamp(nIndex++, info.getSignTime());
			pstmt.setTimestamp(nIndex++, info.getTransTimeOfBank());
			pstmt.setString(nIndex++, info.getStatusIDOfBank());
			pstmt.setString(nIndex++, info.getStatusDescOfBank());
			pstmt.setString(nIndex++, info.getRemindOfBank());
			//添加新增字段
			pstmt.setString(nIndex++, info.getPayAreaNameOfProvince());
			pstmt.setString(nIndex++, info.getPayAreaNameOfCity());
			pstmt.setLong(nIndex++, info.getCurrencyID());
			pstmt.setLong(nIndex++, info.getIsSameBank());
			pstmt.setLong(nIndex++, info.getRemitPriority());
			
			//添加中行所需字段
			pstmt.setString(nIndex++, info.getPayBankExchangeCode());//付方联行号
			pstmt.setString(nIndex++, info.getPayBranchCodeOfBank());//付方机构号
			pstmt.setString(nIndex++, info.getReceiveBankExchangeCode());//收方联行号
			pstmt.setString(nIndex++, info.getReceiveBranchCodeOfBank());//收方机构号
			pstmt.setString(nIndex++, info.getPayChargeAccountNo());//手续费账号
			pstmt.setString(nIndex++, info.getPayChargeBankExchangeCode());//手续费账户联行号
			pstmt.setString(nIndex++, info.getPayChargeBranchCodeOfBank());//手续费账户机构号

			pstmt.execute();
			lReturn = id;
		}
		finally
		{
			if (!isSelfManagedCon)
			{
				this.cleanup(pstmt);
				this.cleanup(conn);
			}
		}
		return lReturn;
	}

	/**
	 * 更新方法，部分从柜台业务带来的属性不能修改，只能新增加入。 包括：收款方账号、付款方账号、金额、摘要、对应交易类型、对应交易编号
	 * 
	 * @param info
	 * @throws SQLException
	 */
	public void update(BankInstructionInfo info) throws Exception
	{
		Connection conn = null;
		PreparedStatement pstmt = null;

		StringBuffer buffer = new StringBuffer();
		buffer.append("update " + strTableName + " set \n");

		//buffer.append("SPAYACCOUNTNO = ?,\n");
		buffer.append("SPAYACCOUNTNAME = ?,\n");
		buffer.append("SPAYDEPARTMENTNAME = ?,\n");
		buffer.append("SPAYBRANCHNAME = ?,\n");
		//buffer.append("MAMOUNT = ?,\n");
		//buffer.append("SRECEIVEACCOUNTNO = ?,\n");
		buffer.append("SRECEIVEACCOUNTNAME = ?,\n");
		buffer.append("SRECEIVEDEPARTMENTNAME = ?,\n");
		buffer.append("SRECEIVEBRANCHNAME = ?,\n");
		buffer.append("SRECAREANAMEOFPROVINCE = ?,\n");
		buffer.append("SRECAREANAMEOFCITY = ?,\n");
		buffer.append("SCOMMENT = ?,\n");
		//buffer.append("SABSTRACT = ?,\n");
		buffer.append("SSENDERNO = ?,\n");
		buffer.append("SCANCELLERNO = ?,\n");
		//buffer.append("DTCREATETIME = ?,\n");
		buffer.append("DTSENDTIME = ?,\n");
		buffer.append("DTCANCELTIME = ?,\n");
		buffer.append("DTMODIFYTIME = sysdate,\n");
		buffer.append("NSTATUSID = ?,\n");
		//buffer.append("STRANSACTIONNO = ?,\n");
		//buffer.append("NTRANSTYPE = ?,\n");
		buffer.append("NRECEIVEBANK = ?,\n");
		buffer.append("SIDOFBANKSEG1 = ?,\n");
		buffer.append("SIDOFBANKSEG2 = ?,\n");
		buffer.append("SIDOFBANKSEG3 = ?,\n");
		buffer.append("SIDOFBANKSEG4 = ?,\n");
		buffer.append("DTSIGN = ?,\n");
		buffer.append("DTTRANSTIMEOFBANK = ?,\n");
		buffer.append("SSTATUSIDOFBANK = ?,\n");
		buffer.append("SSTATUSDESCOFBANK = ?,\n");
		buffer.append("SREMINDOFBANK = ?,\n");

		//新增数据库字段
		buffer.append("SPAYAREANAMEOFPROVINCE = ?,\n");
		buffer.append("SPAYAREANAMEOFCITY = ?,\n");
		buffer.append("NCURRENCYID = ?,\n");
		buffer.append("NISSAMEBANK = ?,\n");
		buffer.append("NREMITPRIORITY = ?,\n");
		
		//新增中行所需的联行号，机构号，手续费账号
		//buffer.append("SBANKEXCHANGECODEOFPAYER = ?, \n");
		//buffer.append("SBRANCHCODEOFPAYER = ?,\n");
		buffer.append("SBANKEXCHANGECODEOFREC = ?,\n");
		buffer.append("SBRANCHCODEOFREC = ? \n");
		//buffer.append("SPAYCHARGEACCOUNTNO = ?  \n");
		//buffer.append("SBANKEXCHANGECODEOFPC = ? \n");
		//buffer.append("SBRANCHCODEOFPC = ? \n");

		buffer.append("where ID=?\n");

		log.debug(buffer.toString());
		try
		{
			conn = this.getConnection();
			pstmt = conn.prepareStatement(buffer.toString());

			int nIndex = 1;

			//pstmt.setString(nIndex++, info.getPayAccountNo());
			pstmt.setString(nIndex++, info.getPayAccountName());
			pstmt.setString(nIndex++, info.getPayDepartmentName());
			pstmt.setString(nIndex++, info.getPayBranchName());
			//pstmt.setDouble(nIndex++, info.getAmount());
			//pstmt.setString(nIndex++, info.getReceiveAccountNo());
			pstmt.setString(nIndex++, info.getReceiveAccountName());
			pstmt.setString(nIndex++, info.getReceiveDepartmentName());
			pstmt.setString(nIndex++, info.getReceiveBranchName());
			pstmt
					.setString(nIndex++, info
							.getReceiveBranchAreaNameOfProvince());
			pstmt.setString(nIndex++, info.getReceiveBranchAreaNameOfCity());
			pstmt.setString(nIndex++, info.getComment());
			//pstmt.setString(nIndex++, info.getAbstract());
			pstmt.setString(nIndex++, info.getSenderNo());
			pstmt.setString(nIndex++, info.getCancellerNo());
			pstmt.setTimestamp(nIndex++, info.getSendTime());
			pstmt.setTimestamp(nIndex++, info.getCancelTime());
			//pstmt.setTimestamp(nIndex++, info.getModifyTime());
			pstmt.setLong(nIndex++, info.getStatusID());
			//pstmt.setString(nIndex++, info.getTransactionNo());
			//pstmt.setLong(nIndex++, info.getTransType());
			pstmt.setLong(nIndex++, info.getReceiveBank());
			pstmt.setString(nIndex++, info.getIDOfBankSeg1());
			pstmt.setString(nIndex++, info.getIDOfBankSeg2());
			pstmt.setString(nIndex++, info.getIDOfBankSeg3());
			pstmt.setString(nIndex++, info.getIDOfBankSeg4());
			pstmt.setTimestamp(nIndex++, info.getSignTime());
			pstmt.setTimestamp(nIndex++, info.getTransTimeOfBank());
			pstmt.setString(nIndex++, info.getStatusIDOfBank());
			pstmt.setString(nIndex++, info.getStatusDescOfBank());
			pstmt.setString(nIndex++, info.getRemindOfBank());

			//添加新增字段
			pstmt.setString(nIndex++, info.getPayAreaNameOfProvince());
			pstmt.setString(nIndex++, info.getPayAreaNameOfCity());
			pstmt.setLong(nIndex++, info.getCurrencyID());
			pstmt.setLong(nIndex++, info.getIsSameBank());
			pstmt.setLong(nIndex++, info.getRemitPriority());
			
			//添加中行所需字段
			//pstmt.setString(nIndex++, info.getPayBankExchangeCode());//付方联行号
			//pstmt.setString(nIndex++, info.getPayBranchCodeOfBank());//付方机构号
			pstmt.setString(nIndex++, info.getReceiveBankExchangeCode());//收方联行号
			pstmt.setString(nIndex++, info.getReceiveBranchCodeOfBank());//收方机构号
			//pstmt.setString(nIndex++, info.getPayChargeAccountNo());//手续费账号
			//pstmt.setString(nIndex++, info.getPayChargeBankExchangeCode());//手续费账户联行号
			//pstmt.setString(nIndex++, info.getPayChargeBranchCodeOfBank());//手续费账户机构号

			pstmt.setLong(nIndex++, info.getID());

			pstmt.execute();

		}
		finally
		{
			this.cleanup(pstmt);
			this.cleanup(conn);

		}
	}

	/**
	 * 更新银行相关信息 当前包括： DTMODIFYTIME NSTATUSID SIDOFBANKSEG1 SIDOFBANKSEG2
	 * SIDOFBANKSEG3 SIDOFBANKSEG4 DTSIGN DTTRANSTIMEOFBANK SSTATUSIDOFBANK
	 * SSTATUSDESCOFBANK SREMINDOFBANK
	 * 
	 * @param info
	 * @throws SQLException
	 */
	public void updateBankInfo(BankInstructionInfo info) throws Exception
	{
		Connection conn = null;
		PreparedStatement pstmt = null;

		StringBuffer buffer = new StringBuffer();
		buffer.append("update " + strTableName + " set \n");

		//buffer.append("SPAYACCOUNTNO = ?,\n");
		//buffer.append("SPAYACCOUNTNAME = ?,\n");
		//buffer.append("SPAYDEPARTMENTNAME = ?,\n");
		//buffer.append("SPAYBRANCHNAME = ?,\n");
		//buffer.append("MAMOUNT = ?,\n");
		//buffer.append("SRECEIVEACCOUNTNO = ?,\n");
		//buffer.append("SRECEIVEACCOUNTNAME = ?,\n");
		//buffer.append("SRECEIVEDEPARTMENTNAME = ?,\n");
		//buffer.append("SRECEIVEBRANCHNAME = ?,\n");
		//buffer.append("SRECAREANAMEOFPROVINCE = ?,\n");
		//buffer.append("SRECAREANAMEOFCITY = ?,\n");
		//buffer.append("SCOMMENT = ?,\n");
		//buffer.append("SABSTRACT = ?,\n");
		//buffer.append("SSENDERNO = ?,\n");
		//buffer.append("SCANCELLERNO = ?,\n");
		//buffer.append("DTCREATETIME = ?,\n");
		//buffer.append("DTSENDTIME = ?,\n");
		//buffer.append("DTCANCELTIME = ?,\n");
		buffer.append("DTMODIFYTIME = sysdate,\n");
		buffer.append("NSTATUSID = ?,\n");
		//buffer.append("STRANSACTIONNO = ?,\n");
		//buffer.append("NTRANSTYPE = ?,\n");
		//buffer.append("NRECEIVEBANK = ?,\n");
		buffer.append("SIDOFBANKSEG1 = ?,\n");
		buffer.append("SIDOFBANKSEG2 = ?,\n");
		buffer.append("SIDOFBANKSEG3 = ?,\n");
		buffer.append("SIDOFBANKSEG4 = ?,\n");
		buffer.append("DTSIGN = ?,\n");
		buffer.append("DTTRANSTIMEOFBANK = ?,\n");
		buffer.append("SSTATUSIDOFBANK = ?,\n");
		buffer.append("SSTATUSDESCOFBANK = ?,\n");
		buffer.append("SREMINDOFBANK = ? \n");

		buffer.append("where id=?\n");

		log.debug(buffer.toString());
		try
		{
			conn = this.getConnection();
			pstmt = conn.prepareStatement(buffer.toString());

			int nIndex = 1;

			//pstmt.setString(nIndex++, info.getPayAccountNo());
			//pstmt.setString(nIndex++, info.getPayAccountName());
			//pstmt.setString(nIndex++, info.getPayDepartmentName());
			//pstmt.setString(nIndex++, info.getPayBranchName());
			//pstmt.setDouble(nIndex++, info.getAmount());
			//pstmt.setString(nIndex++, info.getReceiveAccountNo());
			//pstmt.setString(nIndex++, info.getReceiveAccountName());
			//pstmt.setString(nIndex++, info.getReceiveDepartmentName());
			//pstmt.setString(nIndex++, info.getReceiveBranchName());
			//pstmt.setString(nIndex++,
			// info.getReceiveBranchAreaNameOfProvince());
			//pstmt.setString(nIndex++, info.getReceiveBranchAreaNameOfCity());
			//pstmt.setString(nIndex++, info.getComment());
			//pstmt.setString(nIndex++, info.getAbstract());
			//pstmt.setString(nIndex++, info.getSenderNo());
			//pstmt.setString(nIndex++, info.getCancellerNo());
			//pstmt.setTimestamp(nIndex++, info.getSendTime());
			//pstmt.setTimestamp(nIndex++, info.getCancelTime());
			//pstmt.setTimestamp(nIndex++, info.getModifyTime());
			pstmt.setLong(nIndex++, info.getStatusID());
			//pstmt.setString(nIndex++, info.getTransactionNo());
			//pstmt.setLong(nIndex++, info.getTransType());
			//pstmt.setLong(nIndex++, info.getReceiveBank());
			pstmt.setString(nIndex++, info.getIDOfBankSeg1());
			pstmt.setString(nIndex++, info.getIDOfBankSeg2());
			pstmt.setString(nIndex++, info.getIDOfBankSeg3());
			pstmt.setString(nIndex++, info.getIDOfBankSeg4());
			pstmt.setTimestamp(nIndex++, info.getSignTime());
			pstmt.setTimestamp(nIndex++, info.getTransTimeOfBank());
			pstmt.setString(nIndex++, info.getStatusIDOfBank());
			pstmt.setString(nIndex++, info.getStatusDescOfBank());
			pstmt.setString(nIndex++, info.getRemindOfBank());

			pstmt.setLong(nIndex++, info.getID());

			pstmt.execute();

		}
		finally
		{
			this.cleanup(pstmt);
			this.cleanup(conn);

		}
	}

	/**
	 * 更新银行相关信息 当前包括： DTMODIFYTIME NSTATUSID DTSIGN DTTRANSTIMEOFBANK
	 * SSTATUSIDOFBANK SSTATUSDESCOFBANK SREMINDOFBANK
	 * 
	 * @param info
	 * @throws SQLException
	 */
	public void updateBankInfoByEvent(BankInstructionInfo info)
			throws Exception
	{
		Connection conn = null;
		PreparedStatement pstmt = null;

		StringBuffer buffer = new StringBuffer();
		buffer.append("update " + strTableName + " set \n");

		//buffer.append("SPAYACCOUNTNO = ?,\n");
		//buffer.append("SPAYACCOUNTNAME = ?,\n");
		//buffer.append("SPAYDEPARTMENTNAME = ?,\n");
		//buffer.append("SPAYBRANCHNAME = ?,\n");
		//buffer.append("MAMOUNT = ?,\n");
		//buffer.append("SRECEIVEACCOUNTNO = ?,\n");
		//buffer.append("SRECEIVEACCOUNTNAME = ?,\n");
		//buffer.append("SRECEIVEDEPARTMENTNAME = ?,\n");
		//buffer.append("SRECEIVEBRANCHNAME = ?,\n");
		//buffer.append("SRECAREANAMEOFPROVINCE = ?,\n");
		//buffer.append("SRECAREANAMEOFCITY = ?,\n");
		//buffer.append("SCOMMENT = ?,\n");
		//buffer.append("SABSTRACT = ?,\n");
		//buffer.append("SSENDERNO = ?,\n");
		//buffer.append("SCANCELLERNO = ?,\n");
		//buffer.append("DTCREATETIME = ?,\n");
		//buffer.append("DTSENDTIME = ?,\n");
		//buffer.append("DTCANCELTIME = ?,\n");
		buffer.append("DTMODIFYTIME = sysdate,\n");
		buffer.append("NSTATUSID = ?,\n");
		//buffer.append("STRANSACTIONNO = ?,\n");
		//buffer.append("NTRANSTYPE = ?,\n");
		//buffer.append("NRECEIVEBANK = ?,\n");
		//buffer.append("SIDOFBANKSEG1 = ?,\n");
		//buffer.append("SIDOFBANKSEG2 = ?,\n");
		//buffer.append("SIDOFBANKSEG3 = ?,\n");
		//buffer.append("SIDOFBANKSEG4 = ?,\n");
		buffer.append("DTSIGN = ?,\n");
		buffer.append("DTTRANSTIMEOFBANK = ?,\n");
		buffer.append("SSTATUSIDOFBANK = ?,\n");
		buffer.append("SSTATUSDESCOFBANK = ?,\n");
		buffer.append("SREMINDOFBANK = ? \n");

		buffer.append("where SIDOFBANKSEG1 = ?\n");
		buffer.append(" and SIDOFBANKSEG2 = ?\n");
		buffer.append(" and SIDOFBANKSEG3 = ?\n");
		buffer.append(" and SIDOFBANKSEG4 = ?\n");

		log.debug(buffer.toString());
		try
		{
			conn = this.getConnection();
			pstmt = conn.prepareStatement(buffer.toString());

			int nIndex = 1;

			//pstmt.setString(nIndex++, info.getPayAccountNo());
			//pstmt.setString(nIndex++, info.getPayAccountName());
			//pstmt.setString(nIndex++, info.getPayDepartmentName());
			//pstmt.setString(nIndex++, info.getPayBranchName());
			//pstmt.setDouble(nIndex++, info.getAmount());
			//pstmt.setString(nIndex++, info.getReceiveAccountNo());
			//pstmt.setString(nIndex++, info.getReceiveAccountName());
			//pstmt.setString(nIndex++, info.getReceiveDepartmentName());
			//pstmt.setString(nIndex++, info.getReceiveBranchName());
			//pstmt.setString(nIndex++,
			// info.getReceiveBranchAreaNameOfProvince());
			//pstmt.setString(nIndex++, info.getReceiveBranchAreaNameOfCity());
			//pstmt.setString(nIndex++, info.getComment());
			//pstmt.setString(nIndex++, info.getAbstract());
			//pstmt.setString(nIndex++, info.getSenderNo());
			//pstmt.setString(nIndex++, info.getCancellerNo());
			//pstmt.setTimestamp(nIndex++, info.getSendTime());
			//pstmt.setTimestamp(nIndex++, info.getCancelTime());
			//pstmt.setTimestamp(nIndex++, info.getModifyTime());
			pstmt.setLong(nIndex++, info.getStatusID());
			//pstmt.setString(nIndex++, info.getTransactionNo());
			//pstmt.setLong(nIndex++, info.getTransType());
			//pstmt.setLong(nIndex++, info.getReceiveBank());
			pstmt.setTimestamp(nIndex++, info.getSignTime());
			pstmt.setTimestamp(nIndex++, info.getTransTimeOfBank());
			pstmt.setString(nIndex++, info.getStatusIDOfBank());
			pstmt.setString(nIndex++, info.getStatusDescOfBank());
			pstmt.setString(nIndex++, info.getRemindOfBank());

			pstmt.setString(nIndex++, info.getIDOfBankSeg1());
			pstmt.setString(nIndex++, info.getIDOfBankSeg2());
			pstmt.setString(nIndex++, info.getIDOfBankSeg3());
			pstmt.setString(nIndex++, info.getIDOfBankSeg4());

			pstmt.execute();

		}
		finally
		{
			this.cleanup(pstmt);
			this.cleanup(conn);

		}
	}

	public Collection findByCondition(
			QueryBankInstructionConditionInfo condition) throws Exception
	{
		long lReturn = -1;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		ArrayList list = new ArrayList();

		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT * FROM \n");
		buffer.append(strTableName);
		buffer.append("\n WHERE 1=1 ");
		if (condition.getStatus() != null && condition.getStatus().length > 0)
		{
			buffer.append("\nAND (NSTATUSID = " + condition.getStatus()[0]);

			for (int i = 1; i < condition.getStatus().length; i++)
			{
				buffer.append("\nOR NSTATUSID = " + condition.getStatus()[i]);
			}
			buffer.append("\n)");
		}
		if (condition.getCreateBeginDate() != null)
		{
			buffer.append("\n AND  DTCREATETIME >= to_date('"
					+ condition.getCreateBeginDate() + "','yyyy-mm-dd')");
		}
		if (condition.getCreateEndDate() != null)
		{
			buffer.append("\n AND  DTCREATETIME <= to_date('"
					+ condition.getCreateEndDate()
					+ " 23:59','yyyy-mm-dd HH24:MI')");
		}
		if (condition.getSendBeginDate() != null)
		{
			buffer.append("\n AND  DTSENDTIME >= to_date('"
					+ condition.getSendBeginDate() + "','yyyy-mm-dd')");
		}
		if (condition.getSendEndDate() != null)
		{
			buffer.append("\n AND  DTSENDTIME <= to_date('"
					+ condition.getSendEndDate()
					+ " 23:59','yyyy-mm-dd HH24:MI')");
		}
		if (condition.getTransactionType() > 0)
		{
			buffer.append("\n AND  NTRANSTYPE = "
					+ condition.getTransactionType() + "");
		}
		if (condition.getReceiveBankType() > 0)
		{
			buffer.append("\n AND  NRECEIVEBANK = "
					+ condition.getReceiveBankType() + "");
		}

		//新增查询条件
		if (condition.getPayAccountNo() != null)
		{
			buffer.append("\n AND  SPAYACCOUNTNO = '"
					+ condition.getPayAccountNo() + "'");
		}

		buffer.append(" ORDER BY \n");

		switch (condition.getOrderByType())
		{
			case BankInstruction_StatusID : {
				buffer.append(" NSTATUSID \n");
			}
				break;
			case BankInstruction_ID : {
				buffer.append(" ID \n");
			}
				break;
			case BankInstruction_PayAccountNo : {
				buffer.append(" SPAYACCOUNTNO \n");
			}
				break;
			case BankInstruction_BankType : {
				buffer.append(" NRECEIVEBANK \n");
			}
				break;
			case BankInstruction_RecAccountNo : {
				buffer.append(" SRECEIVEACCOUNTNO \n");
			}
				break;
			case BankInstruction_Amount : {
				buffer.append(" MAMOUNT \n");
			}
				break;
			case BankInstruction_TransNo : {
				buffer.append(" STRANSACTIONNO \n");
			}
				break;
			case BankInstruction_TransType : {
				buffer.append(" NTRANSTYPE \n");
			}
				break;
			case BankInstruction_SendDate : {
				buffer.append(" DTSENDTIME \n");
			}
				break;

			case BankInstruction_CancelDate : {
				buffer.append(" DTCANCELTIME \n");
			}
				break;

			case BankInstruction_BankExecuteDate : {
				buffer.append(" DTTRANSTIMEOFBANK \n");
			}
				break;
			default : {
				buffer.append(" STRANSACTIONNO \n");
			}
				break;
		}

		if (condition.isDesc())
		{
			buffer.append(" DESC \n");
		}
		else
		{
			buffer.append(" ASC \n");
		}

		try
		{
			conn = this.getConnection();
			log.info(buffer.toString());
			pstmt = conn.prepareStatement(buffer.toString());

			rset = pstmt.executeQuery();

			while (rset.next())
			{
				BankInstructionInfo tmp = getInfoFromRS(rset);
				list.add(tmp);
			}

		}
		finally
		{
			this.cleanup(rset);
			this.cleanup(pstmt);
			this.cleanup(conn);

		}

		return list;
	}

	public BankInstructionInfo findByID(long id) throws Exception
	{
		long lReturn = -1;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT * FROM \n");
		buffer.append(strTableName);
		buffer.append("\n WHERE id = " + id);
		BankInstructionInfo res = null;

		try
		{
			conn = this.getConnection();
			log.info(buffer.toString());
			pstmt = conn.prepareStatement(buffer.toString());
			//pstmt.setTimestamp(1,info.getGLDate());

			rset = pstmt.executeQuery();

			if (rset.next())
			{
				res = getInfoFromRS(rset);

			}

		}
		finally
		{
			this.cleanup(rset);
			this.cleanup(pstmt);
			this.cleanup(conn);

		}

		return res;
	}

	public BankInstructionInfo findByTransactionKeyOfBank(
			BankInstructionInfo condition) throws Exception
	{
		long lReturn = -1;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT * FROM \n");
		buffer.append(strTableName);
		buffer.append("\n WHERE 1=1 ");

		if (condition.getReceiveBank() != -1)
		{
			buffer.append("\n AND  NRECEIVEBANK = "
					+ condition.getReceiveBank() + " ");
		}

		if (condition.getIDOfBankSeg1() != null)
		{
			buffer.append("\n AND  sidofbankseg1 = '"
					+ condition.getIDOfBankSeg1() + "'");
		}
		else
		{
			buffer.append("\n AND  sidofbankseg1 is null ");
		}

		if (condition.getIDOfBankSeg2() != null)
		{
			buffer.append("\n AND  sidofbankseg2 = '"
					+ condition.getIDOfBankSeg2() + "'");
		}
		else
		{
			buffer.append("\n AND  sidofbankseg2 is null ");
		}

		if (condition.getIDOfBankSeg3() != null)
		{
			buffer.append("\n AND  sidofbankseg3 = '"
					+ condition.getIDOfBankSeg3() + "'");
		}
		else
		{
			buffer.append("\n AND  sidofbankseg3 is null ");
		}

		if (condition.getIDOfBankSeg4() != null)
		{
			buffer.append("\n AND  sidofbankseg4 = '"
					+ condition.getIDOfBankSeg4() + "'");
		}
		else
		{
			buffer.append("\n AND  sidofbankseg4 is null ");
		}

		BankInstructionInfo res = null;

		try
		{
			conn = this.getConnection();
			log.info(buffer.toString());
			pstmt = conn.prepareStatement(buffer.toString());
			//pstmt.setTimestamp(1,info.getGLDate());

			rset = pstmt.executeQuery();

			if (rset.next())
			{
				res = getInfoFromRS(rset);
			}

		}
		finally
		{
			this.cleanup(rset);
			this.cleanup(pstmt);
			this.cleanup(conn);

		}

		return res;
	}

	/**
	 * 方法说明：根据ID查找修改时间
	 * 
	 * @param transCurrentDepositID
	 * @return Timestamp
	 * @throws IException
	 */
	public Timestamp findTouchDate(long instructionID) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Timestamp res = null;
		try
		{
			con = getConnection();
			StringBuffer strSQLBuffer = new StringBuffer();

			strSQLBuffer.append("SELECT dtModifytime FROM \n");
			strSQLBuffer.append(strTableName + " \n");
			strSQLBuffer.append("WHERE ID = ? \n");
			String strSQL = strSQLBuffer.toString();
			log.info(strSQL);
			ps = con.prepareStatement(strSQL);
			ps.setLong(1, instructionID);
			rs = ps.executeQuery();
			if (rs.next())
			{
				res = rs.getTimestamp(1);
			}

		}

		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		return res;
	}

	private BankInstructionInfo getInfoFromRS(ResultSet rset) throws Exception
	{
		BankInstructionInfo temp = new BankInstructionInfo();

		temp.setID(rset.getLong("ID"));
		temp.setPayAccountNo(rset.getString("SPAYACCOUNTNO"));
		temp.setPayAccountName(rset.getString("SPAYACCOUNTNAME"));
		temp.setPayDepartmentName(rset.getString("SPAYDEPARTMENTNAME"));
		temp.setPayBranchName(rset.getString("SPAYBRANCHNAME"));
		temp.setAmount(rset.getDouble("MAMOUNT"));
		temp.setReceiveAccountNo(rset.getString("SRECEIVEACCOUNTNO"));
		temp.setReceiveAccountName(rset.getString("SRECEIVEACCOUNTNAME"));
		temp.setReceiveDepartmentName(rset.getString("SRECEIVEDEPARTMENTNAME"));
		temp.setReceiveBranchName(rset.getString("SRECEIVEBRANCHNAME"));
		temp.setReceiveBranchAreaNameOfProvince(rset
				.getString("SRECAREANAMEOFPROVINCE"));
		temp.setReceiveBranchAreaNameOfCity(rset
				.getString("SRECAREANAMEOFCITY"));
		temp.setComment(rset.getString("SCOMMENT"));
		temp.setAbstract(rset.getString("SABSTRACT"));
		temp.setSenderNo(rset.getString("SSENDERNO"));
		temp.setCancellerNo(rset.getString("SCANCELLERNO"));
		temp.setCreateTime(rset.getTimestamp("DTCREATETIME"));
		temp.setSendTime(rset.getTimestamp("DTSENDTIME"));
		temp.setCancelTime(rset.getTimestamp("DTCANCELTIME"));
		temp.setModifyTime(rset.getTimestamp("DTMODIFYTIME"));
		temp.setStatusID(rset.getLong("NSTATUSID"));
		temp.setTransactionNo(rset.getString("STRANSACTIONNO"));
		temp.setTransType(rset.getLong("NTRANSTYPE"));
		temp.setReceiveBank(rset.getLong("NRECEIVEBANK"));
		temp.setIDOfBankSeg1(rset.getString("SIDOFBANKSEG1"));
		temp.setIDOfBankSeg2(rset.getString("SIDOFBANKSEG2"));
		temp.setIDOfBankSeg3(rset.getString("SIDOFBANKSEG3"));
		temp.setIDOfBankSeg4(rset.getString("SIDOFBANKSEG4"));
		temp.setSignTime(rset.getTimestamp("DTSIGN"));
		temp.setTransTimeOfBank(rset.getTimestamp("DTTRANSTIMEOFBANK"));
		temp.setStatusIDOfBank(rset.getString("SSTATUSIDOFBANK"));
		temp.setStatusDescOfBank(rset.getString("SSTATUSDESCOFBANK"));
		temp.setRemindOfBank(rset.getString("SREMINDOFBANK"));	
		
		
		//添加新增字段
		
		temp.setPayAreaNameOfCity(rset.getString("SPAYAREANAMEOFCITY"));
		temp.setPayAreaNameOfProvince(rset.getString("SPAYAREANAMEOFPROVINCE"));
		temp.setCurrencyID(rset.getLong("NCURRENCYID"));
		temp.setIsSameBank(rset.getLong("NISSAMEBANK"));
		temp.setRemitPriority(rset.getLong("NREMITPRIORITY"));
		
		//新增中行所需字段
		temp.setPayBankExchangeCode(rset.getString("SBANKEXCHANGECODEOFPAYER"));//付方联行号
		temp.setPayBranchCodeOfBank(rset.getString("SBRANCHCODEOFPAYER"));//付方机构号
		temp.setReceiveBankExchangeCode(rset.getString("SBANKEXCHANGECODEOFREC"));//收方联行号
		temp.setReceiveBranchCodeOfBank(rset.getString("SBRANCHCODEOFREC"));//收方机构号
		temp.setPayChargeAccountNo(rset.getString("SPAYCHARGEACCOUNTNO"));//手续费账号
		temp.setPayChargeBankExchangeCode(rset.getString("SBANKEXCHANGECODEOFPC"));//手续费账户联行号
		temp.setPayChargeBranchCodeOfBank(rset.getString("SBRANCHCODEOFPC"));//手续费账户机构号

		return temp;
	}

	//=========================================================================//
	/**
	 * 重载基类的方法
	 */
	public long updateStatus(long id, long StatusID) throws Exception
	{
		long lReturn = -1;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			con = getConnection();
			/**
			 * Important: If any field in database changed, please correct them
			 * at here and fucntion:addDatatoPrepareStatement
			 */
			StringBuffer strSQLBuffer = new StringBuffer();
			strSQLBuffer.append("UPDATE \n");
			strSQLBuffer.append(strTableName + " \n");
			strSQLBuffer.append(" SET \n");
			strSQLBuffer.append(" nStatusID = ?, \n");
			strSQLBuffer.append(" DTMODIFYTIME = sysdate \n");
			strSQLBuffer.append(" WHERE ID = ? \n");

			String strSQL = strSQLBuffer.toString();
			ps = con.prepareStatement(strSQL);
			log.info(strSQL);
			ps.setLong(1, StatusID);
			ps.setLong(2, id);

			ps.executeUpdate();
		}
		finally
		{
			cleanup(ps);
			cleanup(con);
		}
		return id;
	}

	//	public static void main(String[] args)
	//	{
	//		try
	//		{
	//
	//			Sett_BankInstructionDAO dao = new Sett_BankInstructionDAO();
	//
	//			BankInstructionInfo bai = dao.findByID(443);
	//			bai.setComment("change");
	//			long l = dao.add(bai);
	//			bai = dao.findByID(l);
	//			if (bai != null)
	//				System.out.println(" AccountName:" + bai.getPayAccountName());
	//			System.out.println("========= *success* =========");
	//		}
	//		catch (Exception e)
	//		{
	//			e.printStackTrace();
	//		}
	//	}

	/**
	 * @param transactionNo
	 * @return @throws
	 *         Exception
	 */
	public BankInstructionInfo[] findByTransactionNoOfSett(String transactionNo)
			throws Exception
	{
		long lReturn = -1;

		BankInstructionInfo[] bankInstructionInfos = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		ArrayList lis = new ArrayList(32);

		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT * FROM \n");
		buffer.append(strTableName);
		buffer.append("\n WHERE 1=1 ");

		buffer.append("\n AND  STRANSACTIONNO  ='" + transactionNo + "' ");
		buffer.append("\n AND  NSTATUSID <> "
				+ SETTConstant.BankInstructionStatus.DELETED + "");

		BankInstructionInfo res = null;

		try
		{
			conn = this.getConnection();
			log.info(buffer.toString());
			pstmt = conn.prepareStatement(buffer.toString());
			//pstmt.setTimestamp(1,info.getGLDate());

			rset = pstmt.executeQuery();

			while (rset.next())
			{
				res = getInfoFromRS(rset);
				lis.add(res);
			}

		}
		finally
		{
			this.cleanup(rset);
			this.cleanup(pstmt);
			this.cleanup(conn);

		}
		if (lis == null || lis.size() == 0)
		{
			return null;
		}
		else
		{
			int size = lis.size();
			bankInstructionInfos = new BankInstructionInfo[size];
			bankInstructionInfos = (BankInstructionInfo[]) lis.toArray(bankInstructionInfos);
			
			return bankInstructionInfos;
		}
	}

	public static void main(String arg[])
	{
		Sett_BankInstructionDAO dao = new Sett_BankInstructionDAO();
		try
		{
			dao.findByTransactionNoOfSett("20041022010101128");
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}