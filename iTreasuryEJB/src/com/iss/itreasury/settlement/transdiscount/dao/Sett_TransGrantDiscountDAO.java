/*
 * Created on 2003-10-10
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.settlement.transdiscount.dao;
import java.sql.Timestamp;
import java.util.Collection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.settlement.offbalanceregister.dataentity.OffBalanceRegisterInfo;
import com.iss.itreasury.settlement.transdiscount.dataentity.TransDiscountDetailInfo;
import com.iss.itreasury.settlement.transdiscount.dataentity.TransDiscountSubjectInfo;
import com.iss.itreasury.settlement.transdiscount.dataentity.TransGrantDiscountInfo;
import com.iss.itreasury.settlement.transdiscount.dataentity.QueryConditionInfo;
import com.iss.itreasury.settlement.transgeneralledger.dataentity.TransGeneralLedgerInfo;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Log4j;
import com.iss.itreasury.settlement.util.SETTConstant;
import java.util.ArrayList;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.loan.util.*;
import com.iss.itreasury.loan.discount.dataentity.DiscountCredenceInfo;
import com.iss.itreasury.util.Log;
import java.util.Collection;
import java.rmi.RemoteException;
import com.iss.itreasury.loan.contract.dataentity.*;
import com.iss.itreasury.loan.discount.dataentity.*;
import java.util.Vector;
import com.iss.itreasury.util.IException;
import com.iss.system.dao.PageLoader;

/**
 * @author yiwang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class Sett_TransGrantDiscountDAO extends SettlementDAO
{
	public final static int ORDERBY_STRANSNO = 0;
	public final static int ORDERBY_NTRANSACTIONTYPEID = 1;
	public final static int ORDERBY_NDISCOUNTACCOUNTID = 2;
	public final static int ORDERBY_NDISCOUNTCONTRACTID = 3;
	public final static int ORDERBY_NDISCOUNTNOTEID = 4;
	public final static int ORDERBY_NDEPOSITACCOUNTID = 5;
	public final static int ORDERBY_MDISCOUNTBILLAMOUNT = 6;
	public final static int ORDERBY_MINTEREST = 7;
	public final static int ORDERBY_MDISCOUNTAMOUNT = 8;
	public final static int ORDERBY_SABSTRACT = 9;
	public final static int ORDERBY_NSTATUSID = 10;

	private final static double INVALID_VALUE = -9999999999999999.0;

	private StringBuffer strSelectSQLBuffer = null;

	//

	/**
	 * constructor
	 */
	public Sett_TransGrantDiscountDAO()
	{
		super.strTableName = "Sett_TransGrantDiscount";
	}
	public static void main(String[] args) throws SQLException
	{
		Sett_TransGrantDiscountDAO dao=new Sett_TransGrantDiscountDAO();
	   dao.findDiscountBill(64);
	}
	/**
	 * 方法说明：新增贴现发放交易记录
	 * @param info:Sett_TransCurrentDepositInfo
	 * @return : long - 返回新增贴现发放交易记录ID
	 * @throws IException
	 */
	private Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);

	/**
	 * 方法说明：新增贴现发放交易记录
	 * @param info:Sett_TransCurrentDepositInfo
	 * @return : long - 返回新增贴现发放交易记录ID
	 * @throws IException
	 */
	public long add(TransGrantDiscountInfo info) throws Exception
	{
		long lReturn = -1;
		log.info("insert into  " + this.strTableName);
		Connection conn = null;
		PreparedStatement ps = null;
		try
		{
			if (info.getID() == -1)
			{
				long id = this.getSett_TransGrantDiscountID();
				info.setID(id);
			}
			Log.print("\n\n getSett_TransGrantDiscountID id=" + info.getID() + "\n\n");

			conn = this.getConnection();
			StringBuffer buffer = new StringBuffer();
			buffer.append("insert into " + this.strTableName + " ");
			buffer.append(" (" + this.getItemNames(DAO_OPERATION_ADD) + ")values");
			if(info.isPayForm()){
				buffer.append(" ,ISPAYFORM )");
			}
			buffer.append("(");
			buffer.append("?,?,?,?,?,?,?,?,?,?,");
			buffer.append("?,?,?,?,?,?,?,?,?,?,");
			buffer.append("?,?,sysdate,?,?,?,?,?,?,?,?,?,?,?,?");
			if(info.isPayForm()){
				buffer.append(",?");
			}
			buffer.append(")");
			String sql = buffer.toString();

			ps = conn.prepareStatement(sql);
			Log.print("\n\n sql:  " + sql + "\n\n");

			int nRs = this.addDaoPrepareStatement(info, ps, DAO_OPERATION_ADD);

			if (nRs > 0)
			{
				lReturn = info.getID();
			}
		}
		finally
		{
			this.cleanup(ps);
			this.cleanup(conn);
		}
		return lReturn;
	}
	/**
	 * 方法说明：物理修改贴现发放交易记录
	 * @param info :Sett_TransCurrentDepositInfo
	 * @return : long - 返回贴现发放交易记录ID
	 * @throws IException
	 */
	public long update(TransGrantDiscountInfo info) throws Exception
	{
		long lReturn = -1;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			conn = getConnection();

			StringBuffer buffer = new StringBuffer("");
			buffer.append("UPDATE \n");
			buffer.append(this.strTableName + "\n");
			buffer.append(" SET dtModify=sysdate, \n");
			buffer.append(this.getItemNames(DAO_OPERATION_UPDATE) + " \n");
			if(info.isPayForm())
				buffer.append(" ,ISPAYFORM=? ");
			buffer.append(" WHERE ID = ?");

			String sql = buffer.toString();

			log.info(sql);

			ps = conn.prepareStatement(sql);

			int nRs = this.addDaoPrepareStatement(info, ps, DAO_OPERATION_UPDATE);

			if (nRs > 0)
			{
				lReturn = info.getID();
			}

		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		return lReturn;
	}
	/**
	 * 方法说明：删除贴现发放交易记录
	 * @param info :Sett_TransCurrentDepositInfo
	 * @return : long - 返回被删除贴现发放交易记录ID
	 * @throws IException
	 */
	public long delete(long id, long nUserID, String sAbstract, String sCheackAbstratc) throws Exception
	{
		this.updateStatus(id, SETTConstant.TransactionStatus.DELETED, nUserID, sAbstract, sCheackAbstratc);

		return id;
	}

	/**
	 * 方法说明：根据交易ID，得到交易详细信息
	 * @param nID （数据库记录的主键）
	 * @return Sett_TransCurrentDepositInfo
	 * @throws IException
	 */
	public TransGrantDiscountInfo findByID(long nID) throws Exception
	{
		TransGrantDiscountInfo info = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			conn = this.getConnection();
			String sql = "select * from " + this.strTableName + " where id=?";
			ps = conn.prepareStatement(sql);
			ps.setLong(1, nID);
			rs = ps.executeQuery();
			info = this.transferResultsetIntoInfo(rs);
			
			//查询其它信息
			TransGrantDiscountInfo loanInfo = this.findInfoByDiscountNoteID(info.getDiscountNoteID());
			info.setSignBillClientID(loanInfo.getSignBillClientID());
			info.setSignBillClientName(loanInfo.getSignBillClientName());
			info.setSignBillAccountName(loanInfo.getSignBillAccountName());
			info.setSignBillBankName(loanInfo.getSignBillBankName());
		}
		finally
		{
			this.cleanup(rs);
			this.cleanup(ps);
			this.cleanup(conn);
		}

		return info;
	}
	/**
	 * 方法说明：修改状态
	 * @param StatusID  0已删除1暂存2保存（未复核）3已复核4未签认5已签认6已确认7已勾账
	 * @return long  
	 * @throws IException
	 */
	public long updateStatus(long id, long StatusID, long lUserID, String strAbstract, String strCheckAbstract) throws Exception
	{
		long lReturn = -1;
		Connection conn = null;
		PreparedStatement ps = null;
		try
		{
			conn = this.getConnection();
			String sql = " update " + this.strTableName + " set nStatusID=?,nCheckUserID=?,sAbstract=?,sCheckAbstract=?,dtModify=sysdate where id=? ";
			ps = conn.prepareStatement(sql);
			ps.setLong(1, StatusID);
			ps.setLong(2, lUserID);
			ps.setString(3, strAbstract);
			ps.setString(4, strCheckAbstract);
			ps.setLong(5, id);

			int n = ps.executeUpdate();
			if (n > 0)
				lReturn = id;
		}
		finally
		{
			this.cleanup(ps);
			this.cleanup(conn);
		}

		return lReturn;
	}

	/**
	 *方法说明：根据查询条件组合，查询出符合的记录
	 *  Method findByConditions.
	 * @param nOfficeID   //办事处ID
	 * @param nCurrencyID //币种ID
	 * @param nInputUserID     //用户ID
	 * @param nQueryTypeID     // 查询类型：0，（处理的查找）；1，（复核的查找） 2.
	 * @param nStatusID   //交易状态
	 * @param dtExecute        //查询日期 即执行日期
	 * @param nTransactionTypeID  //  业务类型
	 * @param orderByType//排序字段
	 * @param isDesc     //是否为降序 
	 * @return Collection
	 */
	public Collection findByCondition(QueryConditionInfo info) throws Exception
	{
		long nOfficeID = info.getOfficeID();
		long nCurrencyID = info.getCurrencyID();
		long nUserID = info.getUserID();
		long nTypeID = info.getTypeID();
		long nTransactionTypeID = info.getTransactionTypeID();
		long nStatusID = info.getStatusID();
		int orderByType = (int) info.getOrderByType();
		boolean isDesc = info.getDesc();
		Timestamp date = info.getDate();
		String dt = DataFormat.getDateString(date);
		boolean isPayForm = info.isPayForm();

		Collection cln = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			conn = this.getConnection();
			StringBuffer buffer = new StringBuffer("");
			/*
			buffer.append(" select sTransNo,nTransactionTypeID,");
			buffer.append("nDiscountAccountID,nDiscountContractID,");
			buffer.append("nDiscountNoteID,nDepositAccountID,");
			buffer.append("mDiscountBillAmount,mInterest,");
			buffer.append("mDiscountAmount,sAbstract,nStatusID");
			*/
			buffer.append(" select * ");

			buffer.append(" from " + this.strTableName + " \n");
			buffer.append(" where ID is not null ");

			//日期特别处理
			if (nStatusID != SETTConstant.TransactionStatus.TEMPSAVE)
			{
				buffer.append(" and dtExecute=to_date('" + DataFormat.getDateString(date) + "','yyyy-mm-dd')");
			}

			buffer.append(" and nOfficeID=" + nOfficeID);
			buffer.append(" and nCurrencyID=" + nCurrencyID);

			//根据查询类型处理用户ID
			if (nTypeID == 0)
			{
				buffer.append(" and nInputUserID=" + nUserID);
				buffer.append(" and nCheckUserID!=" + nUserID);
			}
			else if (nTypeID == 1)
			{
				buffer.append(" and nCheckUserID=" + nUserID);
				buffer.append(" and nInputUserID!=" + nUserID);
			}

			buffer.append(" and nTransactionTypeID=" + nTransactionTypeID);
			
			if (nTypeID == 0)
			{//处理的查找
				if (nStatusID > 0)
					buffer.append(" and nStatusID=" + nStatusID);
				else
					buffer.append(" and nStatusID in(" + SETTConstant.TransactionStatus.TEMPSAVE + "," + SETTConstant.TransactionStatus.SAVE + ") ");
			}
			else if(nTypeID == 1)
			{//复核的查找
				buffer.append(" and nStatusID=" + nStatusID);
			}

			//处理查询
			if(isPayForm){
				buffer.append(" and ispayform=1 ");

			}
			else//add by dwj 20130117
			{
				buffer.append(" and (ispayform=2 or ispayform is null) ");
			}
			switch (orderByType)
			{
				case ORDERBY_STRANSNO :
					buffer.append(" order by sTransNo ");
					break;
				case ORDERBY_NTRANSACTIONTYPEID :
					buffer.append(" order by nTransactionTypeID ");
					break;
				case ORDERBY_NDISCOUNTACCOUNTID :
					buffer.append(" order by nDiscountAccountID ");
					break;
				case ORDERBY_NDISCOUNTCONTRACTID :
					buffer.append(" order by nDiscountContractID ");
					break;
				case ORDERBY_NDISCOUNTNOTEID :
					buffer.append(" order by nDiscountNoteID ");
					break;
				case ORDERBY_NDEPOSITACCOUNTID :
					buffer.append(" order by nDepositAccountID ");
					break;
				case ORDERBY_MDISCOUNTBILLAMOUNT :
					buffer.append(" order by mDiscountBillAmount ");
					break;
				case ORDERBY_MINTEREST :
					buffer.append(" order by mInterest ");
					break;
				case ORDERBY_MDISCOUNTAMOUNT :
					buffer.append(" order by mDiscountAmount ");
					break;
				case ORDERBY_SABSTRACT :
					buffer.append(" order by sAbstract ");
					break;
				case ORDERBY_NSTATUSID :
					buffer.append(" order by nStatusID ");
					break;
				default :
					buffer.append(" order by sTransNo ");
					break;
			}
			if (isDesc)
				buffer.append(" desc ");
			else
				buffer.append(" asc ");

			String sql = buffer.toString();

			Log.print("\n\n" + sql + "\n\n");

			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			cln = this.transferResultsetIntoCollection(rs);

		}
		finally
		{
			this.cleanup(rs);
			this.cleanup(ps);
			this.cleanup(conn);
		}

		return cln;
	}

	/**
	 * 方法说明：根据查询条件匹配
	 *  Method  match.
	 * @param Sett_TransCurrentDepositInfo info
	 * @return Sett_TransCurrentDepositInfo
	 */
	public TransGrantDiscountInfo match(TransGrantDiscountInfo info) throws Exception
	{
		TransGrantDiscountInfo infoRtn = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			/* 需要匹配如下这些字段
			nDiscountAccountID	贴现账户
			nDiscountContractID	贴现合同号
			nDiscountNoteID	贴现凭证
			nDepositAccountID	贴现转至活期账户ID
			nPayTypeID	贴现付款方式      //删除
			nBankID	汇出行ID		
			mDiscountBillAmount	汇票金额
			mDiscountAmount	贴现金额
			mInterest	利息	
			加一个条件  录入人不能是自己			
			*/
			long nDiscountAccountID = info.getDiscountAccountID();
			long nDiscountContractID = info.getDiscountContractID();
			long nDiscountNoteID = info.getDiscountNoteID();
			long nDepositAccountID = info.getDepositAccountID();
			long nPayTypeID = info.getPayTypeID();
			long nBankID = info.getBankID();
			double mDiscountBillAmount = info.getDiscountBillAmount();
			double mDiscountAmount = info.getDiscountAmount();
			double mInterest = info.getInterest();
			long userFromJsp = info.getInputUserID();

			StringBuffer buffer = new StringBuffer("");
			buffer.append(" select * from " + this.strTableName + " where id is not null ");
			buffer.append(" and nDiscountAccountID=" + nDiscountAccountID);
			buffer.append(" and nDiscountContractID=" + nDiscountContractID);
			buffer.append(" and nDiscountNoteID=" + nDiscountNoteID);
			buffer.append(" and nDepositAccountID=" + nDepositAccountID);
			//buffer.append(" and nPayTypeID=" + nPayTypeID);
			buffer.append(" and nBankID=" + nBankID);
			
//			if (mDiscountBillAmount != INVALID_VALUE)
//				buffer.append(" and mDiscountBillAmount=" + mDiscountBillAmount);
//			buffer.append(" and mDiscountAmount=" + mDiscountAmount);
//			buffer.append(" and mInterest=" + mInterest);

			//必须交易状态必须为保存
			//Modified by zwsun,  2007/7/5
			buffer.append(" and nStatusID=" + info.getStatusID());
			//buffer.append(" and nStatusID=2 ");
			//交易号不能为空
			buffer.append(" and sTransNo is not null ");
			//录入人不能是自己
			buffer.append(" and nInputUserID!=" + userFromJsp + "  ");
			if(info.isPayForm())
				buffer.append(" and ISPAYFORM=" + SETTConstant.DiscountBillType.ISPAYFORM+ "  ");
			String sql = buffer.toString();

			Log.print("\n\n" + sql + "\n\n"); 

			conn = this.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			infoRtn = this.transferResultsetIntoInfo(rs);
		}
		finally
		{
			this.cleanup(rs);
			this.cleanup(ps);
			this.cleanup(conn);
		}

		return infoRtn;
	}

	/**
	 * 方法说明：根据查询条件匹配
	 * @param nOperation
	 * @return 已经拼好的字符串
	 */
	private String getItemNames(int nOperation)
	{
		String rs = "";
		StringBuffer strBf = new StringBuffer("");

		if (nOperation == DAO_OPERATION_FIND)
		{
			strBf.append(" ID=?,");
			strBf.append(" nOfficeID=?,");
			strBf.append(" nCurrencyID=?,");
			strBf.append(" sTransNo=?,");
			strBf.append(" nTransactionTypeID=?,");
			strBf.append(" nDiscountAccountID=?,");
			strBf.append(" nDiscountContractID=?,");
			strBf.append(" nDiscountNoteID=?,");
			strBf.append(" mDiscountBillAmount=?,");
			strBf.append(" mDiscountAmount=?,");
			strBf.append(" nDepositAccountID=?,");
			strBf.append(" nPayTypeID=?,");
			strBf.append(" nBankID=?,");
			strBf.append(" sExtAcctNo=?,");
			strBf.append(" sExtAcctName=?,");
			strBf.append(" sBankName=?,");
			strBf.append(" sProvince=?,");
			strBf.append(" sCity=?,");
			strBf.append(" nCashFlowID=?,");
			strBf.append(" mInterest=?,");
			strBf.append(" dtInterestStart=?,");
			strBf.append(" dtExecute=?,");
			strBf.append(" dtModify=sysdate,");
			strBf.append(" dtInput=?,");
			strBf.append(" nInputUserID=?,");
			strBf.append(" nCheckUserID=?,");
			strBf.append(" sAbstract=?,");
			strBf.append(" sCheckAbstract=?,");
			strBf.append(" nStatusID=?,");
			strBf.append(" mBankAcceptanceAmount=?,");
			strBf.append(" mTradeAcceptanceAmount=?, ");
			strBf.append(" nAbstractID=?,");
			strBf.append(" nSignBillAccountID=?,");
			strBf.append(" mInterestOfSign=?,");
			strBf.append(" mInterestOfDiscount=?");
		}
		else if (nOperation == DAO_OPERATION_UPDATE)
		{
			strBf.append(" ID=?,");
			strBf.append(" nOfficeID=?,");
			strBf.append(" nCurrencyID=?,");
			strBf.append(" sTransNo=?,");
			strBf.append(" nTransactionTypeID=?,");
			strBf.append(" nDiscountAccountID=?,");
			strBf.append(" nDiscountContractID=?,");
			strBf.append(" nDiscountNoteID=?,");
			strBf.append(" mDiscountBillAmount=?,");
			strBf.append(" mDiscountAmount=?,");
			strBf.append(" nDepositAccountID=?,");
			strBf.append(" nPayTypeID=?,");
			strBf.append(" nBankID=?,");
			strBf.append(" sExtAcctNo=?,");
			strBf.append(" sExtAcctName=?,");
			strBf.append(" sBankName=?,");
			strBf.append(" sProvince=?,");
			strBf.append(" sCity=?,");
			strBf.append(" nCashFlowID=?,");
			strBf.append(" mInterest=?,");
			strBf.append(" dtInterestStart=?,");
			strBf.append(" dtExecute=?,");
			//strBf.append(" dtModify=sysdate,");
			strBf.append(" dtInput=?,");
			strBf.append(" nInputUserID=?,");
			strBf.append(" nCheckUserID=?,");
			strBf.append(" sAbstract=?,");
			strBf.append(" sCheckAbstract=?,");
			strBf.append(" nStatusID=?,");
			strBf.append(" mBankAcceptanceAmount=?,");
			strBf.append(" mTradeAcceptanceAmount=?, ");
			strBf.append(" nAbstractID=?,");
			strBf.append(" nSignBillAccountID=?,");
			strBf.append(" mInterestOfSign=?,");
			strBf.append(" mInterestOfDiscount=?");
		}
		else
		{
			strBf.append(" ID,");
			strBf.append(" nOfficeID,");
			strBf.append(" nCurrencyID,");
			strBf.append(" sTransNo,");
			strBf.append(" nTransactionTypeID,");
			strBf.append(" nDiscountAccountID,");
			strBf.append(" nDiscountContractID,");
			strBf.append(" nDiscountNoteID,");
			strBf.append(" mDiscountBillAmount,");
			strBf.append(" mDiscountAmount,");
			strBf.append(" nDepositAccountID,");
			strBf.append(" nPayTypeID,");
			strBf.append(" nBankID,");
			strBf.append(" sExtAcctNo,");
			strBf.append(" sExtAcctName,");
			strBf.append(" sBankName,");
			strBf.append(" sProvince,");
			strBf.append(" sCity,");
			strBf.append(" nCashFlowID,");
			strBf.append(" mInterest,");
			strBf.append(" dtInterestStart,");
			strBf.append(" dtExecute,");
			strBf.append(" dtModify,");
			strBf.append(" dtInput,");
			strBf.append(" nInputUserID,");
			strBf.append(" nCheckUserID,");
			strBf.append(" sAbstract,");
			strBf.append(" sCheckAbstract,");
			strBf.append(" nStatusID,");
			strBf.append(" mBankAcceptanceAmount,");
			strBf.append(" mTradeAcceptanceAmount,");
			strBf.append(" nAbstractID,");
			strBf.append(" nSignBillAccountID,");
			strBf.append(" mInterestOfSign,");
			strBf.append(" mInterestOfDiscount");
		}

		rs = strBf.toString();
		return rs;
	}
	private int addDaoPrepareStatement(TransGrantDiscountInfo info, PreparedStatement psmt, int nOperation) throws Exception
	{
		int index = 1;
		PreparedStatement ps = psmt;
		ps.setLong(index++, info.getID());
		ps.setLong(index++, info.getOfficeID());
		ps.setLong(index++, info.getCurrencyID());
		ps.setString(index++, info.getTransNo());
		ps.setLong(index++, info.getTransactionTypeID());
		ps.setLong(index++, info.getDiscountAccountID());
		ps.setLong(index++, info.getDiscountContractID());
		ps.setLong(index++, info.getDiscountNoteID());
		ps.setDouble(index++, info.getDiscountBillAmount());
		ps.setDouble(index++, info.getDiscountAmount());
		ps.setLong(index++, info.getDepositAccountID());
		ps.setLong(index++, info.getPayTypeID());
		ps.setLong(index++, info.getBankID());
		ps.setString(index++, info.getExtAcctNo());
		ps.setString(index++, info.getExtAcctName());
		ps.setString(index++, info.getBankName());
		ps.setString(index++, info.getProvince());
		ps.setString(index++, info.getCity());
		ps.setLong(index++, info.getCashFlowID());
		ps.setDouble(index++, info.getInterest());
		ps.setTimestamp(index++, info.getInterestStartDate());
		ps.setTimestamp(index++, info.getExecuteDate());
		//修改的时间  直接用sysdate
		//ps.setTimestamp(index++, info.getModifyDate());
		ps.setTimestamp(index++, info.getInputDate());
		ps.setLong(index++, info.getInputUserID());
		ps.setLong(index++, info.getCheckUserID());
		ps.setString(index++, info.getAbstract());
		ps.setString(index++, info.getCheckAbstract());
		ps.setLong(index++, info.getStatusID());
		ps.setDouble(index++, info.getMBankAcceptanceAmount());
		ps.setDouble(index++, info.getMTradeAcceptanceAmount());
		ps.setLong(index++, info.getNAbstract());
		ps.setLong(index++, info.getSignBillAccountID());
		ps.setDouble(index++, info.getInterestOfSign());
		ps.setDouble(index++, info.getInterestOfDiscount());
		if(info.isPayForm())
			ps.setLong(index++,SETTConstant.DiscountBillType.ISPAYFORM);

		if (nOperation == DAO_OPERATION_UPDATE)
			ps.setLong(index++, info.getID());

		int nResult = ps.executeUpdate();
		return nResult;
	}

	private TransGrantDiscountInfo transferResultsetIntoInfo(ResultSet rs) throws Exception
	{
		TransGrantDiscountInfo info = null;
		while (rs.next())
		{
			info = new TransGrantDiscountInfo();
			info.setID(rs.getLong("ID"));
			info.setOfficeID(rs.getLong("nOfficeID"));
			info.setCurrencyID(rs.getLong("nCurrencyID"));
			info.setTransNo(rs.getString("sTransNo"));
			info.setTransactionTypeID(rs.getLong("nTransactionTypeID"));
			info.setDiscountAccountID(rs.getLong("nDiscountAccountID"));
			info.setDiscountContractID(rs.getLong("nDiscountContractID"));
			info.setDiscountNoteID(rs.getLong("nDiscountNoteID"));
			info.setDiscountBillAmount(rs.getDouble("mDiscountBillAmount"));
			info.setDiscountAmount(rs.getDouble("mDiscountAmount"));
			info.setDepositAccountID(rs.getLong("nDepositAccountID"));
			info.setPayTypeID(rs.getLong("nPayTypeID"));
			info.setBankID(rs.getLong("nBankID"));
			info.setExtAcctNo(rs.getString("sExtAcctNo"));
			info.setExtAcctName(rs.getString("sExtAcctName"));
			info.setBankName(rs.getString("sBankName"));
			info.setProvince(rs.getString("sProvince"));
			info.setCity(rs.getString("sCity"));
			info.setCashFlowID(rs.getLong("nCashFlowID"));
			info.setInterest(rs.getDouble("mInterest"));
			info.setInterestStartDate(rs.getTimestamp("dtInterestStart"));
			info.setExecuteDate(rs.getTimestamp("dtExecute"));
			info.setModifyDate(rs.getTimestamp("dtModify"));
			info.setInputDate(rs.getTimestamp("dtInput"));
			info.setInputUserID(rs.getLong("nInputUserID"));
			info.setCheckUserID(rs.getLong("nCheckUserID"));
			info.setAbstract(rs.getString("sAbstract"));
			info.setCheckAbstract(rs.getString("sCheckAbstract"));
			info.setStatusID(rs.getLong("nStatusID"));
			info.setMBankAcceptanceAmount(rs.getDouble("mBankAcceptanceAmount"));
			info.setMTradeAcceptanceAmount(rs.getDouble("mTradeAcceptanceAmount"));
			info.setNAbstract(rs.getLong("nAbstractID"));
			info.setSignBillAccountID(rs.getLong("nSignBillAccountID"));
			info.setInterestOfSign(rs.getDouble("mInterestOfSign"));
			info.setInterestOfDiscount(rs.getDouble("mInterestOfDiscount"));
			//add by dwj 20130116
			long ispayform = rs.getLong("ispayform");
			if(SETTConstant.DiscountBillType.ISPAYFORM==ispayform)
			{
				info.setPayForm(true);
			}
			//end add by dwj 20130116
		}

		return info;
	}

	private TransGrantDiscountInfo transferLoanResultsetIntoInfo(ResultSet rs) throws Exception
	{
		TransGrantDiscountInfo info = null;
		while (rs.next())
		{
			info = new TransGrantDiscountInfo();
			info.setID(-1);

			info.setDiscountBillAmount(rs.getDouble("mDiscountBillAmount"));
			info.setDiscountAmount(rs.getDouble("mDiscountAmount"));
			info.setInterest(rs.getDouble("mInterest")+rs.getDouble("PurchaserInterest"));
			info.setInterestStartDate(rs.getTimestamp("dtInterestStart"));
			info.setPayTypeID(rs.getLong("nPayTypeID"));
			info.setExtAcctNo(rs.getString("sExtAcctNo"));
			info.setExtAcctName(rs.getString("sExtAcctName")) ;
			info.setBankName(rs.getString("sBankName")); 
			info.setProvince(rs.getString("sProvince"));
		    info.setCity(rs.getString("sCity"));
			info.setDepositAccountID(rs.getLong("nDepositAccountID"));
			info.setBankID(rs.getLong("nBankID"));
			info.setSignBillClientID(rs.getLong("SignBillClientID"));
			
			info.setSignBillClientName(rs.getString("SignBillClientName"));
			info.setSignBillAccountName(rs.getString("SignBillAccountName"));
			info.setSignBillBankName(rs.getString("SignBillBankName"));
			
			info.setInterestOfSign(rs.getDouble("PurchaserInterest"));
			info.setInterestOfDiscount(rs.getDouble("mInterest"));
			info.setPurchaseInterestRate(rs.getDouble("PurchaserInterestRate"));
		}

		return info;
	}
	private TransDiscountDetailInfo transferLoanResultsetInfo(ResultSet rs) throws Exception
	{
		TransDiscountDetailInfo info = null;
		while (rs.next())
		{
			info = new TransDiscountDetailInfo();
			info.setID(-1);

			info.setMDiscountBillAmount(rs.getDouble("mDiscountBillAmount"));
			info.setDiscountCredenceAmount(rs.getDouble("DiscountCredenceAmount"));
			//info.setInterest(rs.getDouble("mInterest")+rs.getDouble("PurchaserInterest"));
			info.setDtInterestStart(rs.getTimestamp("dtInterestStart"));
			//info.setPayTypeID(rs.getLong("nPayTypeID"));
			info.setSExtAccountNo(rs.getString("sExtAccountNo"));
			info.setSExtClientName(rs.getString("sExtClientName")) ;
			info.setSRemitinBank(rs.getString("sRemitinBank")); 
			info.setSRemitinProvince(rs.getString("sRemitinProvince"));
		    info.setSRemitinCity(rs.getString("sRemitinCity"));
			info.setNDepositAccountID(rs.getLong("nDepositAccountID"));
			info.setNBankID(rs.getLong("nBankID"));
			//info.setSignBillClientID(rs.getLong("SignBillClientID"));
			info.setNInOrOut(rs.getLong("InorOut"));
			info.setNDiscountTypeId(rs.getLong("nDiscountTypeId"));
			
			info.setSignBillClientName(rs.getString("SignBillClientName"));
			//info.setSignBillAccountName(rs.getString("SignBillAccountName"));
			//info.setSignBillBankName(rs.getString("SignBillBankName"));
			
			info.setInterestOfSign(rs.getDouble("PurchaserInterest"));
			info.setInterestOfDiscount(rs.getDouble("mInterest"));
			info.setPurchaseInterestRate(rs.getDouble("PurchaserInterestRate"));
		}

		return info;
	}

	private Collection transferResultsetIntoCollection(ResultSet rs) throws Exception
	{
		ArrayList list = new ArrayList();
		TransGrantDiscountInfo info = null;
		while (rs.next())
		{
			info = new TransGrantDiscountInfo();
			info.setID(rs.getLong("ID"));
			info.setOfficeID(rs.getLong("nOfficeID"));
			info.setCurrencyID(rs.getLong("nCurrencyID"));
			info.setTransNo(rs.getString("sTransNo"));
			info.setTransactionTypeID(rs.getLong("nTransactionTypeID"));
			info.setDiscountAccountID(rs.getLong("nDiscountAccountID"));
			info.setDiscountContractID(rs.getLong("nDiscountContractID"));
			info.setDiscountNoteID(rs.getLong("nDiscountNoteID"));
			info.setDiscountBillAmount(rs.getDouble("mDiscountBillAmount"));
			info.setDiscountAmount(rs.getDouble("mDiscountAmount"));
			info.setDepositAccountID(rs.getLong("nDepositAccountID"));
			info.setPayTypeID(rs.getLong("nPayTypeID"));
			info.setBankID(rs.getLong("nBankID"));
			info.setExtAcctNo(rs.getString("sExtAcctNo"));
			info.setExtAcctName(rs.getString("sExtAcctName"));
			info.setBankName(rs.getString("sBankName"));
			info.setProvince(rs.getString("sProvince"));
			info.setCity(rs.getString("sCity"));
			info.setCashFlowID(rs.getLong("nCashFlowID"));
			info.setInterest(rs.getDouble("mInterest"));
			info.setInterestStartDate(rs.getTimestamp("dtInterestStart"));
			info.setExecuteDate(rs.getTimestamp("dtExecute"));
			info.setModifyDate(rs.getTimestamp("dtModify"));
			info.setInputDate(rs.getTimestamp("dtInput"));
			info.setInputUserID(rs.getLong("nInputUserID"));
			info.setCheckUserID(rs.getLong("nCheckUserID"));
			info.setAbstract(rs.getString("sAbstract"));
			info.setCheckAbstract(rs.getString("sCheckAbstract"));
			info.setStatusID(rs.getLong("nStatusID"));
			info.setMBankAcceptanceAmount(rs.getDouble("mBankAcceptanceAmount"));
			info.setMTradeAcceptanceAmount(rs.getDouble("mTradeAcceptanceAmount"));
			info.setNAbstract(rs.getLong("nAbstractID"));
			info.setSignBillAccountID(rs.getLong("nSignBillAccountID"));
			info.setInterestOfSign(rs.getDouble("mInterestOfSign"));
			info.setInterestOfDiscount(rs.getDouble("mInterestOfDiscount"));
			list.add(info);
			Log.print("\n\n id=" + info.getID() + "\n\n");
		}

		return list;
	}

	//根据贴现凭证ID，得到放款的信息
	public TransGrantDiscountInfo findInfoByDiscountNoteID(long nDiscountNoteID) throws Exception
	{
		TransGrantDiscountInfo info = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{			
			conn = this.getConnection();
			String sql =
					" select "
					+ " a.dtFillDate dtInterestStart, "
					+ " a.mAmount mDiscountBillAmount, "
					+ " a.mAmount-a.mInterest mDiscountAmount, "
					+ " a.mInterest mInterest, "
					+ " a.nGrantTypeID nPayTypeID, "
					+ " a.sReceiveAccount sExtAcctNo, "
					+ " a.sReceiveClientName sExtAcctName, "
					+ " a.sRemitBank sBankName, "
					+ " a.nAccountBankID nBankID, "
					+ " a.sRemitInProvince sProvince, "
					+ " a.sRemitInCity sCity, "
					+ " a.nGrantCurrentAccountID nDepositAccountID, "
					+ " b.DiscountClientID SignBillClientID, "
					
					//Boxu update 贴现单位的"名称""账号""开户银行" 全部从LOAN_DISCOUNTCREDENCE取
					+ " a.SAPPLYCLIENT SignBillClientName, "  //单位
					+ " a.SAPPLYACCOUNT SignBillAccountName, "  //账号
					+ " a.SAPPLYBANK SignBillBankName, "  //开户银行
					
					//+ " b.DiscountClientName SignBillClientName, "
					
					+ " b.PurchaserInterestRate PurchaserInterestRate, "
					+ " a.PurchaserInterest PurchaserInterest "
					+ " from  LOAN_DISCOUNTCREDENCE a,loan_ContractForm b "
					+ " where a.Ncontractid=b.ID "					
					+ " and a.id=? ";
					System.out.println("\n\n sql:="+sql+"\n\n");

			ps = conn.prepareStatement(sql);

			ps.setLong(1, nDiscountNoteID);
			rs = ps.executeQuery();
			info = this.transferLoanResultsetIntoInfo(rs);
			
			if(info==null){
				 sql =
					" select "
					+ " a.dtFillDate dtInterestStart, "
					+ " a.mAmount mDiscountBillAmount, "
					+ " a.mAmount-a.mInterest mDiscountAmount, "
					+ " a.mInterest mInterest, "
					+ " a.nGrantTypeID nPayTypeID, "
					+ " a.sReceiveAccount sExtAcctNo, "
					+ " a.sReceiveClientName sExtAcctName, "
					+ " a.sRemitBank sBankName, "
					+ " a.nAccountBankID nBankID, "
					+ " a.sRemitInProvince sProvince, "
					+ " a.sRemitInCity sCity, "
					+ " a.nGrantCurrentAccountID nDepositAccountID, "
					+ " b.DiscountClientID SignBillClientID, "
					
					//Boxu update 贴现单位的"名称""账号""开户银行" 全部从LOAN_DISCOUNTCREDENCE取
					+ " a.SAPPLYCLIENT SignBillClientName, "  //单位
					+ " a.SAPPLYACCOUNT SignBillAccountName, "  //账号
					+ " a.SAPPLYBANK SignBillBankName, "  //开户银行
					
					//+ " b.DiscountClientName SignBillClientName, "
					
					+ " b.PurchaserInterestRate PurchaserInterestRate, "
					+ " a.PurchaserInterest PurchaserInterest "
					+ " from  loan_discountpayform a,loan_ContractForm b "
					+ " where a.Ncontractid=b.ID "					
					+ " and a.id=? ";
					System.out.println("\n\n sql:="+sql+"\n\n");

				ps = conn.prepareStatement(sql);
	
				ps.setLong(1, nDiscountNoteID);
				rs = ps.executeQuery();
				info = this.transferLoanResultsetIntoInfo(rs);
			}

		}
		finally
		{
			this.cleanup(rs);
			this.cleanup(ps);
			this.cleanup(conn);
		}

		return info;
	}
//	根据转贴现凭证ID，得到放款的信息
	public TransDiscountDetailInfo findInfoByTransDiscountNoteID(long transDiscountNoteID) throws Exception
	{
		TransDiscountDetailInfo info = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{			
			conn = this.getConnection();
			String sql =
					" select "
					+ " a.dtFillDate dtInterestStart, "
					+ " a.mAmount mDiscountBillAmount, "
					+ " a.mAmount-a.mInterest DiscountCredenceAmount, "
					+ " a.mInterest mInterest, "
					//+ " a.nGrantTypeID nPayTypeID, "
					+ " a.sReceiveAccount sExtAccountNo, "
					+ " a.sReceiveClientName sExtClientName, "
					+ " a.sRemitBank sRemitinBank, "
					+ " a.nAccountBankID nBankID, "
					+ " a.sRemitInProvince sRemitinProvince, "
					+ " a.sRemitInCity sRemitinCity, "
					+ " a.nGrantCurrentAccountID nDepositAccountID, "
					+ " b.DiscountClientID SignBillClientID, "
					+ " b.ninorout InorOut, "
					+ " b.Ndiscounttypeid NdiscounttypeID, "
					
					//Boxu update 贴现单位的"名称""账号""开户银行" 全部从LOAN_DISCOUNTCREDENCE取
					+ " a.SAPPLYCLIENT SignBillClientName, "  //单位
					+ " a.SAPPLYACCOUNT SignBillAccountName, "  //账号
					+ " a.SAPPLYBANK SignBillBankName, "  //开户银行
					
					//+ " b.DiscountClientName SignBillClientName, "
					
					+ " b.PurchaserInterestRate PurchaserInterestRate, "
					+ " a.PurchaserInterest PurchaserInterest "
					+ " from  LOAN_DISCOUNTCREDENCE a,loan_ContractForm b "
					+ " where a.Ncontractid=b.ID "					
					+ " and a.id=? ";
					System.out.println("\n\n sql:="+sql+"\n\n");

			ps = conn.prepareStatement(sql);

			ps.setLong(1, transDiscountNoteID);
			rs = ps.executeQuery();
			info = this.transferLoanResultsetInfo(rs);
		}
		finally
		{
			this.cleanup(rs);
			this.cleanup(ps);
			this.cleanup(conn);
		}

		return info;
	}

	//	根据贴现凭证ID，得到保存过的贴现发放表信息
	public TransGrantDiscountInfo findDiscountInfoByDiscountNoteID(long nDiscountNoteID) throws Exception
	{
		TransGrantDiscountInfo info = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			conn = this.getConnection();
			String sql = " select * from sett_transgrantdiscount " + " where id=?";

			ps = conn.prepareStatement(sql);

			ps.setLong(1, nDiscountNoteID);
			rs = ps.executeQuery();
			info = this.transferResultsetIntoInfo(rs);
		}
		finally
		{
			this.cleanup(rs);
			this.cleanup(ps);
			this.cleanup(conn);
		}

		return info;
	}

	/**
	 * 检查该记录能否保存：
	 */

	public boolean chechSaveOptions(TransGrantDiscountInfo info) throws Exception
	{
		boolean flg = false;
		//
		return flg;
	}

	public TransGrantDiscountInfo findSubQueryInfoByDiscountNoteID(long nDiscountNoteID) throws Exception
	{
		TransGrantDiscountInfo info = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			conn = this.getConnection();
				String sql = " select " + " sCode sTransNo, " + //贴现汇票号码  用 TransNo 记
				
				" nContractID nDiscountContractID, " + //合同号  DiscountContractID
				" mAmount mDiscountBillAmount,  " + //汇票金额    DiscountBillAmount	
				" mAmount-mInterest mDiscountAmount, "+ //核定金额		
				" minterest minterest"+ //利息		
				" from  LOAN_DISCOUNTCREDENCE " + " where id=?";

			ps = conn.prepareStatement(sql);

			ps.setLong(1, nDiscountNoteID);
			rs = ps.executeQuery();

			while (rs.next())
			{
				info = new TransGrantDiscountInfo();
				info.setTransNo(rs.getString("sTransNo"));
				info.setDiscountContractID(rs.getLong("nDiscountContractID"));
				info.setDiscountBillAmount(rs.getDouble("mDiscountBillAmount"));
				info.setDiscountAmount(rs.getDouble("mDiscountAmount"));
				info.setInterest(rs.getDouble("minterest"));
			}

		}
		finally
		{
			this.cleanup(rs);
			this.cleanup(ps);
			this.cleanup(conn);
		}

		return info;
	}
	
	public TransGrantDiscountInfo findDateInfoByDiscountNoteID(long nDiscountNoteID) throws Exception
		{
			TransGrantDiscountInfo info = null;
			Connection conn = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			try
			{
				conn = this.getConnection();
					String sql = " select dtDiscountDate dtInput from loan_ContractForm where id=?";

				ps = conn.prepareStatement(sql);

				ps.setLong(1, nDiscountNoteID);
				rs = ps.executeQuery();
System.out.println("sql: "+sql);
System.out.println("nDiscountNoteID: "+nDiscountNoteID);

				while (rs.next())
				{
					info = new TransGrantDiscountInfo();
					info.setInputDate(rs.getTimestamp("dtInput"));
				}

			}
			finally
			{
				this.cleanup(rs);
				this.cleanup(ps);
				this.cleanup(conn);
			}

			return info;
		}

	//修改loan_DiscountCredence 中的记录状态，参数为贴现凭证编号 nOperation 1,保存 2,删除
	public void updateLoanCredenceDiscountStatus(long nDIscountNoteID, long nOperation) throws Exception
	{
		boolean flag = false;
		try
		{
			//根据贴现凭证编号取得状态
			long nOldStatusID = this.findNstatusByID(nDIscountNoteID);
			Log.print("\n\n nOldStatusID=" + nOldStatusID + " \n\n");
			
			//判断状态
			if (nOperation == 1) //保存
			{
				if (nOldStatusID != LOANConstant.DiscountCredenceStatus.CHECK)
				{
					throw new Exception("贴现凭证状态不正确,请检查!");
				}
				if (!this.updateLoanDiscountStatusByID(nDIscountNoteID, LOANConstant.DiscountCredenceStatus.USED))
				{
					throw new Exception("修改贴现凭证状态失败,请检查!");
				}
			}
			else if (nOperation == 2) //删除
			{
				if (nOldStatusID != LOANConstant.DiscountCredenceStatus.USED)
				{
					throw new Exception("贴现凭证状态不正确,请检查!");
				}
				if (!this.updateLoanDiscountStatusByID(nDIscountNoteID, LOANConstant.DiscountCredenceStatus.CHECK))
				{
					throw new Exception("修改贴现凭证状态失败,请检查!");
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new Exception("更改贴现凭证状态出错: " + e.getMessage());
		}
	}
	
	//修改loan_DiscountCredence 中的记录状态，参数为贴现凭证编号 nOperation 1,保存 2,删除
	public void updateLoanPayFormDiscountStatus(long nDIscountNoteID, long nOperation) throws Exception
	{
		boolean flag = false;
		try
		{
			//根据贴现凭证编号取得状态
			long nOldStatusID = this.findPayFormNstatusByID(nDIscountNoteID);
			Log.print("\n\n nOldStatusID=" + nOldStatusID + " \n\n");
			
			//判断状态
			if (nOperation == 1) //保存
			{
				if (nOldStatusID != LOANConstant.DiscountCredenceStatus.CHECK)
				{
					throw new Exception("贴现凭证状态不正确,请检查!");
				}
				if (!this.updateLoanDiscountPayFormStatusByID(nDIscountNoteID, LOANConstant.DiscountCredenceStatus.USED))
				{
					throw new Exception("修改贴现凭证状态失败,请检查!");
				}
			}
			else if (nOperation == 2) //删除
			{
				if (nOldStatusID != LOANConstant.DiscountCredenceStatus.USED)
				{
					throw new Exception("贴现凭证状态不正确,请检查!");
				}
				if (!this.updateLoanDiscountPayFormStatusByID(nDIscountNoteID, LOANConstant.DiscountCredenceStatus.CHECK))
				{
					throw new Exception("修改贴现凭证状态失败,请检查!");
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new Exception("更改贴现凭证状态出错: " + e.getMessage());
		}
	}

	/**
	 * 通过交易号查找交易ID
	 */
	public long getIDByTransNo(String strTransNo) throws Exception
	{
		long lID = -1;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			conn = this.getConnection();
			String sql = " select ID from sett_transgrantdiscount " + " where sTransNo=? ";
			ps = conn.prepareStatement(sql);
			ps.setString(1, strTransNo);
			rs = ps.executeQuery();

			while (rs.next())
			{
				lID = rs.getLong("ID");
			}
		}
		finally
		{
			this.cleanup(rs);
			this.cleanup(ps);
			this.cleanup(conn);
		}
		return lID;
	}

	//通过ID查找loan_DiscountCredence中记录状态
	private long findNstatusByID(long nDiscountNoteID) throws Exception
	{
		long lRtn = -1;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			conn = this.getConnection();
			String sql = " select nStatusID from loan_discountcredence " + " where id=? ";
			ps = conn.prepareStatement(sql);
			ps.setLong(1, nDiscountNoteID);
			rs = ps.executeQuery();

			while (rs.next())
			{
				lRtn = rs.getLong("nStatusID");
			}
		}
		finally
		{
			this.cleanup(rs);
			this.cleanup(ps);
			this.cleanup(conn);
		}
		return lRtn;
	}
	
	//通过ID查找loan_DiscountCredence中记录状态
	private long findPayFormNstatusByID(long nDiscountNoteID) throws Exception
	{
		long lRtn = -1;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			conn = this.getConnection();
			String sql = " select nStatusID from loan_discountpayform " + " where id=? ";
			ps = conn.prepareStatement(sql);
			ps.setLong(1, nDiscountNoteID);
			rs = ps.executeQuery();

			while (rs.next())
			{
				lRtn = rs.getLong("nStatusID");
			}
		}
		finally
		{
			this.cleanup(rs);
			this.cleanup(ps);
			this.cleanup(conn);
		}
		return lRtn;
	}

	
//	通过ID更改loan_DiscountCredence中记录状态
	private boolean updateDiscountBillSave(long discountID) throws Exception
	{
		Log.print("\n\n updateLoanDiscountStatusByID 传入参数 nDIscountNoteID=" + discountID + " \n\n");
		//Log.print("\n\n updateLoanDiscountStatusByID 传入参数 nNewStatusID=" + nNewStatusID + " \n\n");
		boolean flag = false;

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			conn = this.getConnection();
			String sql = " update  loan_discountcontractbill " + " set NBILLSTATUSID=3 " + " where id=? ";
			ps = conn.prepareStatement(sql);
			ps.setLong(1, discountID);
		//	ps.setLong(2, nDIscountNoteID);
			int nNum = -1;
			nNum = ps.executeUpdate();
			if (nNum > 0)
			{
				flag = true;
			}

		}
		finally
		{
			this.cleanup(rs);
			this.cleanup(ps);
			this.cleanup(conn);
		}

		return flag;
	}
	//通过ID更改loan_DiscountCredence中记录状态
	private boolean updateLoanDiscountStatusByID(long nDIscountNoteID, long nNewStatusID) throws Exception
	{
		Log.print("\n\n updateLoanDiscountStatusByID 传入参数 nDIscountNoteID=" + nDIscountNoteID + " \n\n");
		Log.print("\n\n updateLoanDiscountStatusByID 传入参数 nNewStatusID=" + nNewStatusID + " \n\n");
		boolean flag = false;

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			conn = this.getConnection();
			String sql = " update loan_discountcredence " + " set nStatusID=? " + " where id=? ";

			ps = conn.prepareStatement(sql);
			ps.setLong(1, nNewStatusID);
			ps.setLong(2, nDIscountNoteID);
			int nNum = -1;
			nNum = ps.executeUpdate();
			if (nNum > 0)
			{
				flag = true;
			}

		}
		finally
		{
			this.cleanup(rs);
			this.cleanup(ps);
			this.cleanup(conn);
		}

		return flag;
	}
	
	//通过ID更改loan_DiscountCredence中记录状态
	private boolean updateLoanDiscountPayFormStatusByID(long nDIscountNoteID, long nNewStatusID) throws Exception
	{
		Log.print("\n\n updateLoanDiscountStatusByID 传入参数 nDIscountNoteID=" + nDIscountNoteID + " \n\n");
		Log.print("\n\n updateLoanDiscountStatusByID 传入参数 nNewStatusID=" + nNewStatusID + " \n\n");
		boolean flag = false;

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			conn = this.getConnection();
			String sql = " update loan_discountpayform " + " set nStatusID=? " + " where id=? ";

			ps = conn.prepareStatement(sql);
			ps.setLong(1, nNewStatusID);
			ps.setLong(2, nDIscountNoteID);
			int nNum = -1;
			nNum = ps.executeUpdate();
			if (nNum > 0)
			{
				flag = true;
			}

		}
		finally
		{
			this.cleanup(rs);
			this.cleanup(ps);
			this.cleanup(conn);
		}

		return flag;
	}


	public DiscountCredenceInfo findDiscountCredenceByID(long lDiscountCredenceID) throws Exception
	{
		Log.print("\n\n 进入DAO \n\n");
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String strSQL = null;
		int nBillCount = 0;
		DiscountCredenceInfo lai = new DiscountCredenceInfo();

		try
		{
			con = this.getConnection();
			strSQL = " select a.*, ";
			strSQL += " b.ID nContractID,b.sContractCode,b.nBorrowClientID,b.mExamineAmount,b.mCheckAmount, ";
			strSQL += " b.mDiscountRate,c.sName sClientName,d.sName sInputUserName";
			strSQL += " from Loan_DiscountCredence a, Loan_ContractForm b, Client c, UserInfo d ";
			strSQL += " where a.nContractID=b.ID and b.nBorrowClientID=c.ID(+) and a.nInputUserID=d.ID and b.nTypeID=? and a.ID=? ";

			Log.print(strSQL);
			ps = con.prepareStatement(strSQL);
			ps.setLong(1, LOANConstant.LoanType.TX);
			ps.setLong(2, lDiscountCredenceID);
			rs = ps.executeQuery();
			if (rs.next())
			{
				//贴现申请
				lai.setDiscountContractID(rs.getLong("nContractID")); //贴现ID标识
				lai.setDiscountContractCode(rs.getString("sContractCode")); //贴现编号

				System.out.println("====getDiscountContractCode===="+lai.getDiscountContractCode());
				//申请单位
				lai.setApplyClientID(rs.getLong("nBorrowClientID")); //申请单位编号
				lai.setApplyClientName(rs.getString("sClientName")); //申请单位名称
				lai.setApplyAccount(rs.getString("sApplyAccount")); //申请单位账户号
				lai.setApplyBank(rs.getString("sApplyBank")); //申请单位开户银行

				lai.setExamineAmount(rs.getDouble("mExamineAmount")); //批准金额
				lai.setCheckAmount(rs.getDouble("mCheckAmount")); //核定金额
				//lai.setInterest(rs.getDouble("mExamineAmount") - rs.getDouble("mCheckAmount")); //贴现利息
				lai.setDiscountRate(rs.getDouble("mDiscountRate")); //贴现利率 modify by rxie 因为3。0中信贷没有在loan_discountCredence表中填入利率值，所有贴现利率从合同中取得。

				//贴现凭证
				lai.setID(lDiscountCredenceID); //贴现凭证标识
				lai.setCode(rs.getString("sCode")); //贴现凭证编号
				lai.setFillDate(rs.getTimestamp("dtFillDate"));
				lai.setDraftTypeID(rs.getLong("nDraftTypeID")); //贴现汇票种类标示
				lai.setDraftCode(rs.getString("sDraftCode")); //贴现汇票号码
				lai.setPublicDate(rs.getTimestamp("dtPublicDate")); //发票日
				lai.setAtTerm(rs.getTimestamp("dtAtTerm")); //到期日
				lai.setAcceptClientName(rs.getString("sAcceptClient")); //承兑单位名称
				lai.setAcceptAccount(rs.getString("sAcceptAccount")); //承兑单位账户号
				lai.setAcceptBank(rs.getString("sAcceptBank")); //承兑单位银行
				lai.setStatusID(rs.getLong("nStatusID")); //贴现凭证是否删除

				lai.setBillAmount(rs.getDouble("mAmount")); //贴现凭证金额
				lai.setBillInterest(rs.getDouble("mInterest")); //贴现凭证利息
				lai.setBillCheckAmount(rs.getDouble("mAmount") - rs.getDouble("mInterest")); //贴现凭证核定金额

				lai.setInputUserID(rs.getLong("nInputUserID"));
				lai.setInputUserName(rs.getString("sInputUserName"));
				lai.setInputDate(rs.getTimestamp("dtInputDate"));
				lai.setNextCheckUserID(rs.getLong("nNextCheckUserID")); //下一个审核人标示
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;

			con.close();
			con = null;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw new Exception(ex.getMessage());
		}

		finally
		{
			try
			{
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (con != null)
					con.close();
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
				throw new Exception(ex.getMessage());
			}
		}

		return lai;

	}
	
	//根据贴现凭证编号（放款单号）查询贴现汇票种类
	public long findPaytypeIDByID(long lDiscountCredenceID) throws Exception
	{
		long lRtn=-1;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			conn = this.getConnection();
			String sql = " select nAcceptpotypeID "+
                         " from loan_discountcontractbill "+
                         " where nDiscountCredenceID=?";

			ps = conn.prepareStatement(sql);

			ps.setLong(1, lDiscountCredenceID);
			rs = ps.executeQuery();

			while (rs.next())
			{
				lRtn=rs.getLong("nAcceptpotypeID");		
				if(lRtn!=-1)
				break;
			}

		}
		finally
		{
			this.cleanup(rs);
			this.cleanup(ps);
			this.cleanup(conn);
		}
		
		return lRtn;
	}
	
	//计息明细  从贷款考过来
	/**
		 * 查询一个贴现申请下的贴现票据并计息，操作DiscountBill表
		 * @param lContractID 贴现合同标识
		 * @param lDiscountCredenceID 贴现凭证标识
		 * @return 返回贴现票据的列表
		 */
		public Collection findBillInterestByID(long lContractID, long lDiscountCredenceID, long lPageLineCount, long lPageNo, long lOrderParam, long lDesc)
			throws RemoteException,IException
		{
			PreparedStatement ps = null;
			ResultSet rs = null;
			Connection con = null;
			String strSelect = null;
			String strSQL = null;
			String strOrder = null;

			Vector v = new Vector();
			long lRecordCount = -1;
			long lPageCount = -1;
			long lRowNumStart = -1;
			long lRowNumEnd = -1;

			double dDiscountRate = 0; //利率
			Timestamp tsDiscountDate = null; //计息日
			double dExamineAmount = 0; //批准金额
			double dDiscountAccrual = 0; //利息
			double dCheckAmount = 0; //实付金额

			Timestamp tsEnd = null; //贴现日期
			String strEnd = ""; //贴现日期
			int nDays = 0; //实际贴现天数
			double dAmount = 0; //票据金额
			double dAccrual = 0; //贴现利息
			double dRealAmount = 0; //实付贴现金额
			double dTotalAmount = 0; //总票据金额
			double dTotalAccrual = 0; //总票据利息
			double dTotalRealAmount = 0; //总票据实付金额

			try
			{
				con = this.getConnection();

				Log.print("======进入贴现计息(findBillInterestByID)方法======");

				Log.print("合同标示：" + lContractID);
				Log.print("凭证标示：" + lDiscountCredenceID);

				if (lContractID > 0)
				{
					strSQL = " select a.* from Loan_ContractForm a where a.ID=? ";

					ps = con.prepareStatement(strSQL);
					ps.setLong(1, lContractID);
					rs = ps.executeQuery();
					if (rs.next())
					{
						dExamineAmount = rs.getDouble("mExamineAmount"); 	//批准金额
						dRealAmount = rs.getDouble("mCheckAmount"); 		//核定金额
						dAccrual = dExamineAmount - dRealAmount; 			//贴现利息
						dDiscountRate = rs.getDouble("mDiscountRate"); 		//贴现利率
						tsDiscountDate = rs.getTimestamp("dtDiscountDate"); //贴现计息日
					}
					rs.close();
					rs = null;
					ps.close();
					ps = null;

					strSQL = " from Loan_DiscountContractBill where nStatusID=" + Constant.RecordStatus.VALID + " and nContractID=" + lContractID;

				}
				else if (lDiscountCredenceID > 0)
				{
					strSQL = " select a.* from Loan_ContractForm a, Loan_DiscountCredence b where a.ID=b.nContractID and b.ID=? ";

					ps = con.prepareStatement(strSQL);
					ps.setLong(1, lDiscountCredenceID);
					rs = ps.executeQuery();
					if (rs.next())
					{
						dExamineAmount = rs.getDouble("mExamineAmount"); 	//批准金额
						dRealAmount = rs.getDouble("mCheckAmount");			//核定金额
						dAccrual = dExamineAmount - dRealAmount; 			//贴现利息
						dDiscountRate = rs.getDouble("mDiscountRate"); 		//贴现利率
						tsDiscountDate = rs.getTimestamp("dtDiscountDate"); //贴现计息日
					}
					rs.close();
					rs = null;
					ps.close();
					ps = null;

					strSQL = " from Loan_DiscountContractBill where nStatusID=" + Constant.RecordStatus.VALID + " and nDiscountCredenceID=" + lDiscountCredenceID;

				}

				Log.print("======开始查询票据总数和总金额======");

				//计算记录总数
				strSelect = " select count(*),sum(nvl(mAmount,0)),sum(nvl(mCheckAmount,0)) ";
				//strSQL = " from DiscountBill where nStatusID=" + Constant.RecordStatus.VALID + " and nDiscountApplyID=" + lDiscountApplyID;

				Log.print(strSelect + strSQL);
				ps = con.prepareStatement(strSelect + strSQL);
				rs = ps.executeQuery();

				if (rs != null && rs.next())
				{
					lRecordCount = rs.getLong(1);
					dTotalAmount = rs.getDouble(2);
					dTotalRealAmount = rs.getDouble(3);
					dTotalAccrual = dTotalAmount - dTotalRealAmount;
				}
				Log.print("==============");
				Log.print("票据总张数=" + lRecordCount);
				Log.print("票据总金额=" + dTotalAmount);
				Log.print("票据总利息=" + dTotalAccrual);
				Log.print("总实付金额=" + dTotalRealAmount);
				Log.print("==============");
				rs.close();
				rs = null;
				ps.close();
				ps = null;

				Log.print("======结束查询票据总数和总金额======");

				lPageCount = lRecordCount / lPageLineCount;

				if ((lRecordCount % lPageLineCount) != 0)
				{
					lPageCount++;
				}
				////////////////////////////排序处理//////////////////////////////////////////////////////////////////////
				switch ((int) lOrderParam)
				{
					case 1 :
						strSQL += " order by nSerialNo ";
						break;
					case 2 :
						strSQL += " order by sUserName ";
						break;
					case 3 :
						strSQL += " order by sBank ";
						break;
					case 4 :
						strSQL += " order by nIsLocal ";
						break;
					case 5 :
						strSQL += " order by dtCreate ";
						break;
					case 6 :
						strSQL += " order by dtEnd ";
						break;
					case 7 :
						strSQL += " order by nAddDays ";
						break;
					case 8 :
						strSQL += " order by sCode ";
						break;
					case 9 :
						strSQL += " order by mAmount ";
						break;
					case 10 :
						strSQL += " order by nAcceptPOTypeID";
						break;
					case 11 :
						strSQL += " order by sFormerOwner";
						break;
					default :
						strSQL += " order by nSerialNo ";
				}

				if (lDesc == Constant.PageControl.CODE_ASCORDESC_DESC)
				{
					strSQL += " desc";
				}

				/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
				//返回需求的结果集
				lRowNumStart = (lPageNo - 1) * lPageLineCount + 1;
				lRowNumEnd = lRowNumStart + lPageLineCount - 1;

				strSQL = "select * " + strSQL;
				strSQL = " select a.*, rownum r from " + " ( " + strSQL + " ) a ";
				strSQL = " select * from ( " + strSQL + " ) b  where b.r between " + lRowNumStart + " and " + lRowNumEnd;

				Log.print("翻页查询开始");
				Log.print(strSQL);
				ps = con.prepareStatement(strSQL);
				rs = ps.executeQuery();

				while (rs != null && rs.next())
				{
					DiscountBillInfo dbill = new DiscountBillInfo();

					dbill.setDiscountContractID(lContractID); //贴现合同标示
					dbill.setDiscountDate(tsDiscountDate); //计息日
					dbill.setDiscountRate(dDiscountRate); //计息利率

					dbill.setDiscountCredenceID(rs.getLong("nDiscountCredenceID")); //凭证标示
					//dbill.OB_lDiscountCredenceID = rs.getLong("ob_nDiscountCredenceID");
					dbill.setID(rs.getLong("ID")); //票据标示
					dbill.setSerialNo(rs.getLong("nSerialNo")); //序列号
					dbill.setUserName(rs.getString("sUserName")); //原始出票人
					dbill.setBank(rs.getString("sBank")); //承兑银行
					dbill.setIsLocal(rs.getLong("nIsLocal")); //承兑银行所在地（是否在本地）
					dbill.setCreate(rs.getTimestamp("dtCreate")); //出票日
					dbill.setEnd(rs.getTimestamp("dtEnd")); //到期日
					dbill.setCode(rs.getString("sCode")); //汇票号码
					dbill.setAmount(rs.getDouble("mAmount")); //汇票金额
					dbill.setAddDays(rs.getLong("nAddDays")); //节假日增加计息天数
					dbill.setAcceptPOTypeID(rs.getLong("nAcceptPOTypeID")); //汇票类型
					dbill.setFormerOwner(rs.getString("sFormerOwner")); //贴现单位直接前手
					//////////////////////////////////////////////////////////

					//dAmount = rs.getDouble("mAmount"); //汇票金额

					tsEnd = rs.getTimestamp("dtEnd");
					nDays = 0;
					if (tsEnd != null && tsDiscountDate != null)
					{
						strEnd = tsEnd.toString();
						tsEnd =
							new java.sql.Timestamp(
								new Integer(strEnd.substring(0, 4)).intValue() - 1900,
								new Integer(strEnd.substring(5, 7)).intValue() - 1,
								new Integer(strEnd.substring(8, 10)).intValue(),
								0,
								0,
								0,
								0);
						nDays = (int) java.lang.Math.ceil((tsEnd.getTime() - tsDiscountDate.getTime()) / 86400000) + rs.getInt("nAddDays"); //加上节假日增加计息天数
					}
					if (nDays >= 0)
					{
						if (rs.getLong("nIsLocal") == LOANConstant.YesOrNo.NO)
						{
							nDays = nDays + 3;
						}
					
					}
				
					dbill.setDays(nDays); //实际贴现天数
					dbill.setRealAmount(rs.getDouble("mCheckAmount"));//实付贴现金额
					dbill.setAccrual(rs.getDouble("mAmount")-rs.getDouble("mCheckAmount"));//贴现利息			

					dbill.setCount(lRecordCount);
					dbill.setTotalAmount(DataFormat.formatDouble(dTotalAmount, 2));
					dbill.setTotalAccrual(DataFormat.formatDouble(dTotalAccrual, 2));
					dbill.setTotalRealAmount(DataFormat.formatDouble(dTotalRealAmount,2));				
					v.add(dbill);
				}
				Log.print("翻页查询结束");
				rs.close();
				rs = null;
				ps.close();
				ps = null;
				con.close();
				con = null;

			}
			catch (Exception e)
			{	
				Log.print(e.getMessage());
				throw new IException("Gen_E001");
			}
			finally
			{
				try
				{
					if (rs != null)
					{
						rs.close();
						rs = null;
					}
					if (ps != null)
					{
						ps.close();
						ps = null;
					}
					if (con != null)
					{
						con.close();
						con = null;
					}
				}
				catch (Exception e)
				{
					Log.print(e.getMessage());
					throw new IException("Gen_E001:"+e.getMessage());
				}
			}

			Log.print("======退出贴现计息(findBillInterestByID)方法======");

			return (v.size() > 0 ? v : null);

		}
		public Collection findDiscountBill(long contractid) throws SQLException{
			ArrayList al=new ArrayList();
			PreparedStatement ps = null;
			ResultSet rs = null;
			Connection con = null;
			String strSelect = "";
			Timestamp tsDiscountDate = null; //计息日
			Timestamp tsEnd = null; //贴现日期
			String strEnd = ""; //贴现日期
			int nDays = 0; //实际贴现天数
			long lRecordCount=-1;
			double dTotalAmount = 0; //总票据金额
			double dTotalAccrual = 0; //总票据利息
			double dTotalRealAmount = 0; //总票据实付金额
			try {
				con=Database.getConnection();
				Log.print("======开始查询票据总数和总金额======");

				//计算记录总数
				strSelect = " select count(*),sum(nvl(mAmount,0)),sum(nvl(mCheckAmount,0)) "+
				" from Loan_DiscountContractBill where nStatusID=" + Constant.RecordStatus.VALID + " and ncontractid=" + contractid;

				System.out.println(strSelect);
				ps = con.prepareStatement(strSelect);
				rs = ps.executeQuery();

				if (rs != null && rs.next())
				{   
					lRecordCount = rs.getLong(1);
					dTotalAmount = rs.getDouble(2);
					dTotalRealAmount = rs.getDouble(3);
					dTotalAccrual = dTotalAmount - dTotalRealAmount;
				}
				System.out.println("==============");
				System.out.println("票据总张数=" + lRecordCount);
				System.out.println("票据总金额=" + dTotalAmount);
				System.out.println("票据总利息=" + dTotalAccrual);
				System.out.println("总实付金额=" + dTotalRealAmount);
				System.out.println("==============");
				rs.close();
				rs = null;
				ps.close();
				ps = null;

				System.out.println("======结束查询票据总数和总金额======");
				strSelect=" select a.*,c.dtdiscountdate ,c.mdiscountrate MRATE from Loan_DiscountContractBill a,loan_discountcredence b,loan_contractform c where a.nStatusID=1 " +
						" and a.ndiscountcredenceid=b.id and c.id = b.ncontractid"
                       +" and a.ncontractid="+contractid;
				System.out.println("查票据明细："+strSelect);
				ps=con.prepareStatement(strSelect);
				rs=ps.executeQuery();
				
				while (rs != null && rs.next())
				{
					DiscountBillInfo dbill = new DiscountBillInfo();

					dbill.setDiscountContractID(rs.getLong("NCONTRACTID")); //贴现合同标示
					
					dbill.setDiscountDate( rs.getTimestamp("dtdiscountdate")); //计息日
					
					dbill.setDiscountRate(rs.getDouble("MRATE")); //计息利率

					dbill.setDiscountCredenceID(rs.getLong("nDiscountCredenceID")); //凭证标示
					//dbill.OB_lDiscountCredenceID = rs.getLong("ob_nDiscountCredenceID");
					dbill.setID(rs.getLong("ID")); //票据标示
					dbill.setSerialNo(rs.getLong("nSerialNo")); //序列号
					dbill.setUserName(rs.getString("sUserName")); //原始出票人
					dbill.setBank(rs.getString("sBank")); //承兑银行
					dbill.setIsLocal(rs.getLong("nIsLocal")); //承兑银行所在地（是否在本地）
					dbill.setCreate(rs.getTimestamp("dtCreate")); //出票日
					dbill.setEnd(rs.getTimestamp("dtEnd")); //到期日
					dbill.setCode(rs.getString("sCode")); //汇票号码
					dbill.setAmount(rs.getDouble("mAmount")); //汇票金额
					dbill.setAddDays(rs.getLong("nAddDays")); //节假日增加计息天数
					dbill.setAcceptPOTypeID(rs.getLong("nAcceptPOTypeID")); //汇票类型
					dbill.setFormerOwner(rs.getString("sFormerOwner")); //贴现单位直接前手
					//////////////////////////////////////////////////////////

					//dAmount = rs.getDouble("mAmount"); //汇票金额

					tsEnd = rs.getTimestamp("dtEnd");
					tsDiscountDate=rs.getTimestamp("dtdiscountdate");
					nDays = 0;
					if (tsEnd != null && tsDiscountDate != null)
					{
						strEnd = tsEnd.toString();
						tsEnd =
							new java.sql.Timestamp(
								new Integer(strEnd.substring(0, 4)).intValue() - 1900,
								new Integer(strEnd.substring(5, 7)).intValue() - 1,
								new Integer(strEnd.substring(8, 10)).intValue(),
								0,
								0,
								0,
								0);
						nDays = (int) java.lang.Math.ceil((tsEnd.getTime() - tsDiscountDate.getTime()) / 86400000) + rs.getInt("nAddDays"); //加上节假日增加计息天数
					}
					if (nDays >= 0)
					{
						if (rs.getLong("nIsLocal") == LOANConstant.YesOrNo.NO)
						{
							nDays = nDays + 3;
						}
					
					}
				
					dbill.setDays(nDays); //实际贴现天数
					dbill.setRealAmount(rs.getDouble("mCheckAmount"));//实付贴现金额
					dbill.setAccrual(rs.getDouble("mAmount")-rs.getDouble("mCheckAmount"));//贴现利息			

					dbill.setCount(lRecordCount);
					dbill.setTotalAmount(DataFormat.formatDouble(dTotalAmount, 2));
					dbill.setTotalAccrual(DataFormat.formatDouble(dTotalAccrual, 2));
					dbill.setTotalRealAmount(DataFormat.formatDouble(dTotalRealAmount,2));				
					al.add(dbill);
				}
				if(rs!=null)
				{	rs.close();
				rs = null;}
				if(ps!=null)
				{	ps.close();
				    ps = null;}
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				if(rs!=null)
				{	rs.close();
				rs = null;}
				if(ps!=null)
				{	ps.close();
				    ps = null;}
				if(con!=null){
					con.close();
					con=null;
				}
			}
			return al;
		}
		public Collection findDiscountBillByContractIdAndPayFormId(long contractid,long payFormId) throws SQLException{
			ArrayList al=new ArrayList();
			PreparedStatement ps = null;
			ResultSet rs = null;
			Connection con = null;
			String strSelect = "";
			Timestamp tsDiscountDate = null; //计息日
			Timestamp tsEnd = null; //贴现日期
			String strEnd = ""; //贴现日期
			int nDays = 0; //实际贴现天数
			long lRecordCount=-1;
			double dTotalAmount = 0; //总票据金额
			double dTotalAccrual = 0; //总票据利息
			double dTotalRealAmount = 0; //总票据实付金额
			try {
				con=Database.getConnection();
				Log.print("======开始查询票据总数和总金额======");

				//计算记录总数
				strSelect = " select count(*),sum(nvl(mAmount,0)),sum(nvl(mCheckAmount,0)) "+
				" from Loan_DiscountContractBill where nStatusID=" + Constant.RecordStatus.VALID + " and ncontractid=" + contractid + " and ndiscountcredenceid in (select id from loan_discountcredence where DISCOUNTPAYFORM ="+payFormId+")";

				System.out.println(strSelect);
				ps = con.prepareStatement(strSelect);
				rs = ps.executeQuery();

				if (rs != null && rs.next())
				{   
					lRecordCount = rs.getLong(1);
					dTotalAmount = rs.getDouble(2);
					dTotalRealAmount = rs.getDouble(3);
					dTotalAccrual = dTotalAmount - dTotalRealAmount;
				}
				System.out.println("==============");
				System.out.println("票据总张数=" + lRecordCount);
				System.out.println("票据总金额=" + dTotalAmount);
				System.out.println("票据总利息=" + dTotalAccrual);
				System.out.println("总实付金额=" + dTotalRealAmount);
				System.out.println("==============");
				rs.close();
				rs = null;
				ps.close();
				ps = null;

				System.out.println("======结束查询票据总数和总金额======");//lidi修改贴现日为凭证的放款日20101209
				strSelect=" select a.*,c.dtdiscountdate ,c.mdiscountrate MRATE from Loan_DiscountContractBill a,loan_discountcredence b,loan_contractform c where a.nStatusID=1 " +
						" and a.ndiscountcredenceid=b.id and c.id = b.ncontractid"
                       +" and a.ncontractid="+contractid+ " and a.ndiscountcredenceid  in (select id from loan_discountcredence where DISCOUNTPAYFORM =" + payFormId+")";
				System.out.println("findDiscountBillByContractIdAndCredenceId查票据明细："+strSelect);
				ps=con.prepareStatement(strSelect);
				rs=ps.executeQuery();
				
				while (rs != null && rs.next())
				{
					DiscountBillInfo dbill = new DiscountBillInfo();

					dbill.setDiscountContractID(rs.getLong("NCONTRACTID")); //贴现合同标示
					dbill.setDiscountDate( rs.getTimestamp("dtdiscountdate")); //计息日
					dbill.setDiscountRate(rs.getDouble("MRATE")); //计息利率
					dbill.setDiscountCredenceID(rs.getLong("nDiscountCredenceID")); //凭证标示
					dbill.setID(rs.getLong("ID")); //票据标示
					dbill.setSerialNo(rs.getLong("nSerialNo")); //序列号
					dbill.setUserName(rs.getString("sUserName")); //原始出票人
					dbill.setBank(rs.getString("sBank")); //承兑银行
					dbill.setIsLocal(rs.getLong("nIsLocal")); //承兑银行所在地（是否在本地）
					dbill.setCreate(rs.getTimestamp("dtCreate")); //出票日
					dbill.setEnd(rs.getTimestamp("dtEnd")); //到期日
					dbill.setCode(rs.getString("sCode")); //汇票号码
					dbill.setAmount(rs.getDouble("mAmount")); //汇票金额
					dbill.setAddDays(rs.getLong("nAddDays")); //节假日增加计息天数
					dbill.setAcceptPOTypeID(rs.getLong("nAcceptPOTypeID")); //汇票类型
					dbill.setFormerOwner(rs.getString("sFormerOwner")); //贴现单位直接前手
					//dAmount = rs.getDouble("mAmount"); //汇票金额

					tsEnd = rs.getTimestamp("dtEnd");
					tsDiscountDate=rs.getTimestamp("DTDISCOUNTDATE");	//	贴现日dtdiscountdate
					nDays = 0;
					if (tsEnd != null && tsDiscountDate != null)
					{
						strEnd = tsEnd.toString();
						tsEnd =
							new java.sql.Timestamp(
								new Integer(strEnd.substring(0, 4)).intValue() - 1900,
								new Integer(strEnd.substring(5, 7)).intValue() - 1,
								new Integer(strEnd.substring(8, 10)).intValue(),
								0,
								0,
								0,
								0);
						nDays = (int) java.lang.Math.ceil((tsEnd.getTime() - tsDiscountDate.getTime()) / 86400000) + rs.getInt("nAddDays"); //加上节假日增加计息天数
					}
					if (nDays >= 0)
					{
						if (rs.getLong("nIsLocal") == LOANConstant.YesOrNo.NO)
						{
							nDays = nDays + 3;
						}
					
					}
				
					dbill.setDays(nDays); //实际贴现天数
					dbill.setRealAmount(rs.getDouble("mCheckAmount"));//实付贴现金额
					dbill.setAccrual(rs.getDouble("mAmount")-rs.getDouble("mCheckAmount"));//贴现利息			

					dbill.setCount(lRecordCount);
					dbill.setTotalAmount(DataFormat.formatDouble(dTotalAmount, 2));
					dbill.setTotalAccrual(DataFormat.formatDouble(dTotalAccrual, 2));
					dbill.setTotalRealAmount(DataFormat.formatDouble(dTotalRealAmount,2));				
					al.add(dbill);
				}
				if(rs!=null)
				{	rs.close();
				rs = null;}
				if(ps!=null)
				{	ps.close();
				    ps = null;}
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				if(rs!=null)
				{	rs.close();
				rs = null;}
				if(ps!=null)
				{	ps.close();
				    ps = null;}
				if(con!=null){
					con.close();
					con=null;
				}
			}
			return al;
		}
		
		

		public Collection findDiscountBillByContractIdAndCredenceId(long contractid,long credenceId) throws SQLException{
			ArrayList al=new ArrayList();
			PreparedStatement ps = null;
			ResultSet rs = null;
			Connection con = null;
			String strSelect = "";
			Timestamp tsDiscountDate = null; //计息日
			Timestamp tsEnd = null; //贴现日期
			String strEnd = ""; //贴现日期
			int nDays = 0; //实际贴现天数
			long lRecordCount=-1;
			double dTotalAmount = 0; //总票据金额
			double dTotalAccrual = 0; //总票据利息
			double dTotalRealAmount = 0; //总票据实付金额
			try {
				con=Database.getConnection();
				Log.print("======开始查询票据总数和总金额======");

				//计算记录总数
				strSelect = " select count(*),sum(nvl(mAmount,0)),sum(nvl(mCheckAmount,0)) "+
				" from Loan_DiscountContractBill where nStatusID=" + Constant.RecordStatus.VALID + " and ncontractid=" + contractid + " and ndiscountcredenceid = " + credenceId;

				System.out.println(strSelect);
				ps = con.prepareStatement(strSelect);
				rs = ps.executeQuery();

				if (rs != null && rs.next())
				{   
					lRecordCount = rs.getLong(1);
					dTotalAmount = rs.getDouble(2);
					dTotalRealAmount = rs.getDouble(3);
					dTotalAccrual = dTotalAmount - dTotalRealAmount;
				}
				System.out.println("==============");
				System.out.println("票据总张数=" + lRecordCount);
				System.out.println("票据总金额=" + dTotalAmount);
				System.out.println("票据总利息=" + dTotalAccrual);
				System.out.println("总实付金额=" + dTotalRealAmount);
				System.out.println("==============");
				rs.close();
				rs = null;
				ps.close();
				ps = null;

				System.out.println("======结束查询票据总数和总金额======");//lidi修改贴现日为凭证的放款日20101209
				strSelect=" select a.*,c.dtdiscountdate ,c.mdiscountrate MRATE from Loan_DiscountContractBill a,loan_discountcredence b,loan_contractform c where a.nStatusID=1 " +
						" and a.ndiscountcredenceid=b.id and c.id = b.ncontractid"
                       +" and a.ncontractid="+contractid+ " and a.ndiscountcredenceid = " + credenceId;
				System.out.println("findDiscountBillByContractIdAndCredenceId查票据明细："+strSelect);
				ps=con.prepareStatement(strSelect);
				rs=ps.executeQuery();
				
				while (rs != null && rs.next())
				{
					DiscountBillInfo dbill = new DiscountBillInfo();

					dbill.setDiscountContractID(rs.getLong("NCONTRACTID")); //贴现合同标示
					dbill.setDiscountDate( rs.getTimestamp("dtdiscountdate")); //计息日
					dbill.setDiscountRate(rs.getDouble("MRATE")); //计息利率
					dbill.setDiscountCredenceID(rs.getLong("nDiscountCredenceID")); //凭证标示
					dbill.setID(rs.getLong("ID")); //票据标示
					dbill.setSerialNo(rs.getLong("nSerialNo")); //序列号
					dbill.setUserName(rs.getString("sUserName")); //原始出票人
					dbill.setBank(rs.getString("sBank")); //承兑银行
					dbill.setIsLocal(rs.getLong("nIsLocal")); //承兑银行所在地（是否在本地）
					dbill.setCreate(rs.getTimestamp("dtCreate")); //出票日
					dbill.setEnd(rs.getTimestamp("dtEnd")); //到期日
					dbill.setCode(rs.getString("sCode")); //汇票号码
					dbill.setAmount(rs.getDouble("mAmount")); //汇票金额
					dbill.setAddDays(rs.getLong("nAddDays")); //节假日增加计息天数
					dbill.setAcceptPOTypeID(rs.getLong("nAcceptPOTypeID")); //汇票类型
					dbill.setFormerOwner(rs.getString("sFormerOwner")); //贴现单位直接前手
					//dAmount = rs.getDouble("mAmount"); //汇票金额

					tsEnd = rs.getTimestamp("dtEnd");
					tsDiscountDate=rs.getTimestamp("DTDISCOUNTDATE");	//	贴现日dtdiscountdate
					nDays = 0;
					if (tsEnd != null && tsDiscountDate != null)
					{
						strEnd = tsEnd.toString();
						tsEnd =
							new java.sql.Timestamp(
								new Integer(strEnd.substring(0, 4)).intValue() - 1900,
								new Integer(strEnd.substring(5, 7)).intValue() - 1,
								new Integer(strEnd.substring(8, 10)).intValue(),
								0,
								0,
								0,
								0);
						nDays = (int) java.lang.Math.ceil((tsEnd.getTime() - tsDiscountDate.getTime()) / 86400000) + rs.getInt("nAddDays"); //加上节假日增加计息天数
					}
					if (nDays >= 0)
					{
						if (rs.getLong("nIsLocal") == LOANConstant.YesOrNo.NO)
						{
							nDays = nDays + 3;
						}
					
					}
				
					dbill.setDays(nDays); //实际贴现天数
					dbill.setRealAmount(rs.getDouble("mCheckAmount"));//实付贴现金额
					dbill.setAccrual(rs.getDouble("mAmount")-rs.getDouble("mCheckAmount"));//贴现利息			

					dbill.setCount(lRecordCount);
					dbill.setTotalAmount(DataFormat.formatDouble(dTotalAmount, 2));
					dbill.setTotalAccrual(DataFormat.formatDouble(dTotalAccrual, 2));
					dbill.setTotalRealAmount(DataFormat.formatDouble(dTotalRealAmount,2));				
					al.add(dbill);
				}
				if(rs!=null)
				{	rs.close();
				rs = null;}
				if(ps!=null)
				{	ps.close();
				    ps = null;}
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				if(rs!=null)
				{	rs.close();
				rs = null;}
				if(ps!=null)
				{	ps.close();
				    ps = null;}
				if(con!=null){
					con.close();
					con=null;
				}
			}
			return al;
		}
		
		
		
		
	    //贴现发放--贴现汇票明细表查询
	    public PageLoader findByDiscountNoteID(long discountNoteID, String strOrderBy) throws Exception
	    {
	        StringBuffer m_sbFrom = new StringBuffer();
	        StringBuffer m_sbSelect = new StringBuffer();
	        StringBuffer m_sbWhere = new StringBuffer();
	        StringBuffer m_sbOrderBy = new StringBuffer();

	        m_sbSelect.append(" * ");
	        m_sbFrom.append("  loan_discountContractBill  \n");
	        m_sbWhere.append("  nStatusID = " + Constant.RecordStatus.VALID);  
	        m_sbWhere.append("  and NDISCOUNTCREDENCEID = " + discountNoteID);  
	        	       
	        m_sbOrderBy.append(strOrderBy);

	        PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory
	                .getBaseObject("com.iss.system.dao.PageLoader");

	        pageLoader
	                .initPageLoader(
	                        new AppContext(),
	                        m_sbFrom.toString(),
	                        m_sbSelect.toString(),
	                        m_sbWhere.toString(),
	                        (int) Constant.PageControl.CODE_PAGELINECOUNT,
	                        "com.iss.itreasury.settlement.transdiscount.dataentity.DiscountBillInfo",
	                        null);
	        pageLoader.setOrderBy(m_sbOrderBy.toString());
	       
	        return pageLoader;
	    }
	    /**
		 * 方法说明：新增转贴现发放交易记录
		 * @param info:Sett_TransCurrentDepositInfo
		 * @return : long - 返回新增贴现发放交易记录ID
		 * @throws IException
		 */
		public long add(TransDiscountDetailInfo info) throws Exception
		{
			long lReturn = -1;
			log.info("insert into  sett_transcredence" );
			Connection conn = null;
			PreparedStatement ps = null;
			try
			{
				if (info.getID() == -1)
				{
					long id = this.getSett_TransCredenceID();
					info.setID(id);
				}
				System.out.println("\n\n getsett_transcredenceID id=" + info.getID() + "\n\n");

				conn = this.getConnection();
				StringBuffer buffer = new StringBuffer();
				buffer.append("insert into sett_transcredence" );
				buffer.append(" (" + this.getDiscountItemNames(DAO_OPERATION_ADD) + ")values");
				buffer.append("(");
				buffer.append("?,?,?,?,?,?,?,?,?,?,");
				buffer.append("?,?,?,?,?,?,?,?,?,?,");
				buffer.append("?,?,?,?,?,?,?,?,?,?,?,?,?");
				buffer.append(")");
				String sql = buffer.toString();
                
				ps = conn.prepareStatement(sql);
				Log.print("\n\n sql:  " + sql + "\n\n");
				System.out.println("我要看的SQL"+sql);

				int nRs = this.addDiscountPrepareStatement(info, ps, DAO_OPERATION_ADD);

				if (nRs > 0)
				{
					lReturn = info.getID();
				}
			}
			finally
			{
				this.cleanup(ps);
				this.cleanup(conn);
			}
			return lReturn;
		}
		/**
		 * 方法说明：根据查询条件匹配
		 * @param nOperation
		 * @return 已经拼好的字符串
		 */
		private String getDiscountItemNames(int nOperation)
		{
			String rs = "";
			StringBuffer strBf = new StringBuffer("");

			if (nOperation == DAO_OPERATION_FIND)
			{
				strBf.append(" ID=?,");
				strBf.append(" nOfficeID=?,");
				strBf.append(" nCurrencyID=?,");
				strBf.append(" sTransNo=?,");
				strBf.append(" nTransactionTypeID=?,");
				strBf.append(" nDiscountContractID=?,");
				strBf.append(" nDiscountCredence=?,");
				strBf.append(" mDiscountBillAmount=?,");
				strBf.append(" DiscountCredenceAmount=?,");
				strBf.append(" nDepositAccountID=?,");
				strBf.append(" nReceiveClientID=?,");
				strBf.append(" nReceiveAccountID=?,");
				strBf.append(" nPayClientID=?,");
				strBf.append(" nPayAccountID=?,");
				strBf.append(" sExtAccountNo=?,");
				strBf.append(" sExtClientName=?,");
				strBf.append(" sRemitinBank=?,");
				strBf.append(" sRemitinProvince=?,");
				strBf.append(" sRemitinCity=?,");
			    strBf.append(" nBankID=?,");
			    strBf.append(" nCashFlowID=?,");
			    strBf.append(" mInterest=?,");
				strBf.append(" dtInterestStart=?,");
				strBf.append(" dtExecute=?,");
			//	strBf.append(" dtModify=sysdate,");
				strBf.append(" dtInput=?,");
				strBf.append(" nInputUserID=?,");
				strBf.append(" nCheckUserID=?,");
				strBf.append(" sAbstract=?,");
				strBf.append(" nAbstractID=?,");
				strBf.append(" sCancleAbstract=?,");
				strBf.append(" sConfirmAbstract=?,");
				strBf.append(" nStatusID=?,");
				strBf.append(" nDiscountCounterPartID=?");
			}
			else if (nOperation == DAO_OPERATION_UPDATE)
			{
			//	strBf.append(" ID=?,");
				strBf.append(" nOfficeID=?,");
				strBf.append(" nCurrencyID=?,");
				strBf.append(" sTransNo=?,");
			//	strBf.append(" nTransactionTypeID=?,");
			//	strBf.append(" nDiscountContractID=?,");
			//	strBf.append(" nDiscountCredence=?,");
			//	strBf.append(" mDiscountBillAmount=?,");
			//	strBf.append(" DiscountCredenceAmount=?,");
				strBf.append(" nDepositAccountID=?,");
				strBf.append(" nReceiveClientID=?,");
				strBf.append(" nReceiveAccountID=?,");
				strBf.append(" nPayClientID=?,");
				strBf.append(" nPayAccountID=?,");
				strBf.append(" sExtAccountNo=?,");
				strBf.append(" sExtClientName=?,");
				strBf.append(" sRemitinBank=?,");
				strBf.append(" sRemitinProvince=?,");
				strBf.append(" sRemitinCity=?,");
			    strBf.append(" nBankID=?,");
			    strBf.append(" nCashFlowID=?,");
			    strBf.append(" mInterest=?,");
				strBf.append(" dtInterestStart=?,");
				strBf.append(" dtExecute=?,");
				//strBf.append(" dtModify=sysdate,");
				strBf.append(" dtInput=?,");
				strBf.append(" nInputUserID=?,");
				strBf.append(" nCheckUserID=?,");
				strBf.append(" sAbstract=?,");
				strBf.append(" nAbstractID=?,");
				strBf.append(" sCancleAbstract=?,");
				strBf.append(" sConfirmAbstract=?,");
				strBf.append(" nStatusID=? ");
		//		strBf.append(" nDiscountCounterPartID=?");
			}
			else
			{
				strBf.append(" ID,");
				strBf.append(" nOfficeID,");
				strBf.append(" nCurrencyID,");
				strBf.append(" sTransNo,");
				strBf.append(" nTransactionTypeID,");
				strBf.append(" nDiscountContractID,");
				strBf.append(" nDiscountCredence,");
				strBf.append(" mDiscountBillAmount,");
				strBf.append(" DiscountCredenceAmount,");
				strBf.append(" nDepositAccountID,");
				strBf.append(" nReceiveClientID,");
				strBf.append(" nReceiveAccountID,");
				strBf.append(" nPayClientID,");
				strBf.append(" nPayAccountID,");
				strBf.append(" sExtAccountNo,");
				strBf.append(" sExtClientName,");
				strBf.append(" sRemitinBank,");
				strBf.append(" sRemitinProvince,");
				strBf.append(" sRemitinCity,");
			    strBf.append(" nBankID,");
			    strBf.append(" nCashFlowID,");
			    strBf.append(" mInterest,");
				strBf.append(" dtInterestStart,");
				strBf.append(" dtExecute,");
				//strBf.append(" dtModify,");
				strBf.append(" dtInput,");
				strBf.append(" nInputUserID,");
				strBf.append(" nCheckUserID,");
				strBf.append(" sAbstract,");
				strBf.append(" nAbstractID,");
				strBf.append(" sCancleAbstract,");
				strBf.append(" sConfirmAbstract,");
				strBf.append(" nStatusID,");
				strBf.append(" nDiscountCounterPartID");
			}

			rs = strBf.toString();
			return rs;
		}
		private int addDiscountPrepareStatement(TransDiscountDetailInfo info, PreparedStatement psmt, int nOperation) throws Exception
		{
			int index = 1;
			PreparedStatement ps = psmt;
			if (nOperation == DAO_OPERATION_UPDATE){
				ps.setLong(index++, info.getNOfficeID());
				ps.setLong(index++, info.getNCurrencyID());
				ps.setString(index++, info.getSTransNo());
				ps.setLong(index++, info.getNDepositAccountID());
				ps.setLong(index++, info.getNReceiveClientID());
				ps.setLong(index++, info.getNReceiveAccountID());
				ps.setLong(index++, info.getNPayClientID());
				ps.setLong(index++, info.getNPayAccountID());
				ps.setString(index++,info.getSExtAccountNo());
				ps.setString(index++,info.getSExtClientName());
				ps.setString(index++,info.getSRemitinBank());
				ps.setString(index++, info.getSRemitinProvince());
				ps.setString(index++,info.getSRemitinCity());
				ps.setLong(index++,info.getNBankID());
				ps.setLong(index++,info.getNCashFlowID());
				ps.setDouble(index++,info.getMInterest());
				ps.setTimestamp(index++,info.getDtInterestStart());
				ps.setTimestamp(index++,info.getDtExecute());
//				修改的时间  直接用sysdate
				//ps.setTimestamp(index++, info.getDtModify());
				ps.setTimestamp(index++,info.getDtInput());
				ps.setLong(index++,info.getNInputUserID());
				ps.setLong(index++,info.getNCheckUserID());
				ps.setString(index++,info.getSAbstract());
				ps.setLong(index++,info.getNAbstractID());
				ps.setString(index++,info.getSCancleAbstract());
				ps.setString(index++,info.getSConfirmAbstract());
				ps.setLong(index++,info.getNStatusID());
				ps.setLong(index++, info.getID());
			}
			else
			{
			ps.setLong(index++, info.getID());
			ps.setLong(index++, info.getNOfficeID());
			ps.setLong(index++, info.getNCurrencyID());
			ps.setString(index++, info.getSTransNo());
			ps.setLong(index++, info.getNTransactionTypeID());
		//	ps.setLong(index++, info.getDiscountAccountID());
			ps.setLong(index++, info.getNDiscountContractID());
			ps.setLong(index++, info.getNDiscountCredence());
			ps.setDouble(index++, info.getMDiscountBillAmount());
			ps.setDouble(index++, info.getDiscountCredenceAmount());
			ps.setLong(index++, info.getNDepositAccountID());
			//ps.setLong(index++, info.getPayTypeID());
			ps.setLong(index++, info.getNReceiveClientID());
			ps.setLong(index++, info.getNReceiveAccountID());
			ps.setLong(index++, info.getNPayClientID());
			ps.setLong(index++, info.getNPayAccountID());
			ps.setString(index++,info.getSExtAccountNo());
			ps.setString(index++,info.getSExtClientName());
			ps.setString(index++,info.getSRemitinBank());
			ps.setString(index++, info.getSRemitinProvince());
			ps.setString(index++,info.getSRemitinCity());
			ps.setLong(index++,info.getNBankID());
			ps.setLong(index++,info.getNCashFlowID());
			ps.setDouble(index++,info.getMInterest());
			ps.setTimestamp(index++,info.getDtInterestStart());
			ps.setTimestamp(index++,info.getDtExecute());
//			修改的时间  直接用sysdate
			//ps.setTimestamp(index++, info.getDtModify());
			ps.setTimestamp(index++,info.getDtInput());
			ps.setLong(index++,info.getNInputUserID());
			ps.setLong(index++,info.getNCheckUserID());
			ps.setString(index++,info.getSAbstract());
			ps.setLong(index++,info.getNAbstractID());
			ps.setString(index++,info.getSCancleAbstract());
			ps.setString(index++,info.getSConfirmAbstract());
			ps.setLong(index++,info.getNStatusID());
			ps.setLong(index++,info.getNDiscountCounterPartid());		
			}

			int nResult = ps.executeUpdate();
			return nResult;
		}
		 /**
		 * 方法说明：新增转贴现发放交易记录科目号
		 * @param info:
		 * @return : long - 返回新增贴现发放交易记录ID
		 * @throws IException
		 */
		public long addSubject(TransDiscountSubjectInfo info) throws Exception
		{
			long lReturn = -1;
			log.info("insert into  sett_discountsubject" );
			Connection conn = null;
			PreparedStatement ps = null;
			try
			{
				if (info.getID() == -1)
				{
					long id = this.getSett_TransDiscountSubjectID();
					info.setID(id);
				}
				System.out.println("\n\n SEQ_TRANSDISCOUNTSUBJECT id=" + info.getID() + "\n\n");

				conn = this.getConnection();
				StringBuffer buffer = new StringBuffer();
				buffer.append("insert into sett_discountsubject" );
				buffer.append(" (" + this.getDiscountSubjectItemNames(DAO_OPERATION_ADD) + ")values");
				buffer.append("(");
				buffer.append("?,?,?,?,?,?");
				buffer.append(")");
				String sql = buffer.toString();
                
				ps = conn.prepareStatement(sql);
				Log.print("\n\n sql:  " + sql + "\n\n");
				System.out.println("我要看的SQL"+sql);

				int nRs = this.addDiscountSubjectPreStatement(info, ps, DAO_OPERATION_ADD);

				if (nRs > 0)
				{
					lReturn = info.getID();
				}
			}
			finally
			{
				this.cleanup(ps);
				this.cleanup(conn);
			}
			return lReturn;
		}
		/**
		 * 方法说明：根据查询条件匹配
		 * @param nOperation
		 * @return 已经拼好的字符串
		 */
		private String getDiscountSubjectItemNames(int nOperation)
		{
			String rs = "";
			StringBuffer strBf = new StringBuffer("");

			if (nOperation == DAO_OPERATION_FIND)
			{
				strBf.append(" ID=?,");
				strBf.append(" transDiscountID=?,");
				strBf.append(" mDirection=?,");
				strBf.append(" mAmount=?,");
				strBf.append(" transTypeID=?,");
				strBf.append(" GENERALLEDGER=?");
			}
			else if (nOperation == DAO_OPERATION_UPDATE)
			{
				strBf.append(" ID=?,");
				strBf.append(" transDiscountID=?,");
				strBf.append(" mDirection=?,");
				strBf.append(" mAmount=?,");
				strBf.append(" transTypeID=?,");
				strBf.append(" GENERALLEDGER=?");
			}
			else
			{
				strBf.append(" ID,");
				strBf.append(" transDiscountID,");
				strBf.append(" mDirection,");
				strBf.append(" mAmount,");
				strBf.append(" transTypeID,");
				strBf.append(" GENERALLEDGER ");
			}

			rs = strBf.toString();
			return rs;
		}
		private int addDiscountSubjectPreStatement(TransDiscountSubjectInfo info, PreparedStatement psmt, int nOperation) throws Exception
		{
			int index = 1;
			PreparedStatement ps = psmt;
			ps.setLong(index++, info.getID());
			ps.setLong(index++, info.getTransDiscountID());
			ps.setLong(index++, info.getMdirection());
			ps.setDouble(index++, info.getMamount());
			ps.setLong(index++, info.getTransTypeID());
			ps.setLong(index++, info.getGeneralLedger());	
			if (nOperation == DAO_OPERATION_UPDATE)
				ps.setLong(index++, info.getID());

			int nResult = ps.executeUpdate();
			return nResult;
		}
		/**
		 * 方法说明：根据交易ID，得到交易详细信息
		 * @param nID （数据库记录的主键）
		 * @return Sett_TransCurrentDepositInfo
		 * @throws IException
		 */
		public TransDiscountDetailInfo findInfoByID(long nID) throws Exception
		{
			TransDiscountDetailInfo info = null;
			Connection conn = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			try
			{
				conn = this.getConnection();
				String sql = "select * from sett_transcredence where id=?";
				ps = conn.prepareStatement(sql);
				ps.setLong(1, nID);
				rs = ps.executeQuery();
				info = this.transferResultIntoInfo(rs);
				
				//查询其它信息
				TransDiscountDetailInfo loanInfo = this.findInfoByTransDiscountNoteID(info.getNDiscountCredence());
				info.setSignBillClientID(loanInfo.getSignBillClientID());
				info.setSignBillClientName(loanInfo.getSignBillClientName());
				info.setSignBillAccountName(loanInfo.getSignBillAccountName());
				info.setSignBillBankName(loanInfo.getSignBillBankName());
			}
			finally
			{
				this.cleanup(rs);
				this.cleanup(ps);
				this.cleanup(conn);
			}

			return info;
		}
		private TransDiscountDetailInfo transferResultIntoInfo(ResultSet rs) throws Exception
		{
			TransDiscountDetailInfo info = null;
			while (rs.next())
			{
				info = new TransDiscountDetailInfo();
				info.setID(rs.getLong("ID"));
				info.setNOfficeID(rs.getLong("nOfficeID"));
				info.setNCurrencyID(rs.getLong("nCurrencyID"));
				info.setSTransNo(rs.getString("sTransNo"));
				info.setNTransactionTypeID(rs.getLong("nTransactionTypeID"));
				info.setNDiscountContractID(rs.getLong("nDiscountContractID"));
				//info.setDiscountAccountID(rs.getLong("nDiscountAccountID"));
				info.setNDiscountCredence(rs.getLong("nDiscountCredence"));
				info.setMDiscountBillAmount(rs.getDouble("mDiscountBillAmount"));
				info.setDiscountCredenceAmount(rs.getDouble("DISCOUNTCREDENCEAMOUNT"));
				info.setNDepositAccountID(rs.getLong("nDepositAccountID"));
				info.setNReceiveClientID(rs.getLong("NRECEIVECLIENTID"));
				info.setNReceiveAccountID(rs.getLong("NRECEIVEACCOUNTID"));
				info.setNPayClientID(rs.getLong("NPAYCLIENTID"));
				info.setNPayAccountID(rs.getLong("NPAYACCOUNTID"));			
				//info.setPayTypeID(rs.getLong("nPayTypeID"));
				info.setNBankID(rs.getLong("nBankID"));
				info.setSExtAccountNo(rs.getString("SEXTACCOUNTNO"));
				info.setSExtClientName(rs.getString("SEXTCLIENTNAME"));
				info.setSRemitinBank(rs.getString("SREMITINBANK"));
				info.setSRemitinProvince(rs.getString("SREMITINPROVINCE"));
				info.setSRemitinCity(rs.getString("SREMITINCITY"));
				info.setNCashFlowID(rs.getLong("nCashFlowID"));
				info.setMInterest(rs.getDouble("mInterest"));
				info.setDtInterestStart(rs.getTimestamp("dtInterestStart"));
				info.setDtExecute(rs.getTimestamp("dtExecute"));
				info.setDtModify(rs.getTimestamp("dtModify"));
				info.setDtInput(rs.getTimestamp("dtInput"));
				info.setNInputUserID(rs.getLong("nInputUserID"));
				info.setNCheckUserID(rs.getLong("nCheckUserID"));
				info.setSAbstract(rs.getString("sAbstract"));
				info.setSConfirmAbstract(rs.getString("SCONFIRMABSTRACT"));
				info.setNStatusID(rs.getLong("nStatusID"));			
				info.setNAbstractID(rs.getLong("nAbstractID"));
				info.setNDiscountCounterPartid(rs.getLong("NDISCOUNTCOUNTERPARTID"));
			}

			return info;
		}
		/**
		 * 方法说明：物理修改转贴现发放交易记录
		 * @param info :Sett_TransCurrentDepositInfo
		 * @return : long - 返回贴现发放交易记录ID
		 * @throws IException
		 */
		public long update(TransDiscountDetailInfo info) throws Exception
		{
			long lReturn = -1;
			Connection conn = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			try
			{
				conn = getConnection();

				StringBuffer buffer = new StringBuffer("");
				buffer.append("UPDATE \n");
				buffer.append("sett_transcredence\n");
				buffer.append(" SET dtModify=sysdate, \n");
				buffer.append(this.getDiscountItemNames(DAO_OPERATION_UPDATE) + " \n");
				buffer.append(" WHERE ID = ?");

				String sql = buffer.toString();

				log.info(sql);

				ps = conn.prepareStatement(sql);

				int nRs = this.addDiscountPrepareStatement(info, ps, DAO_OPERATION_UPDATE);

				if (nRs > 0)
				{
					lReturn = info.getID();
				}

			}
			finally
			{
				cleanup(rs);
				cleanup(ps);
				cleanup(conn);
			}
			return lReturn;
		}
		
		/**
		 * 方法说明：根据查询条件匹配转贴现
		 *  Method  match.
		 * @param Sett_TransCurrentDepositInfo info
		 * @return Sett_TransCurrentDepositInfo
		 */
		public TransDiscountDetailInfo match(TransDiscountDetailInfo info) throws Exception
		{
			TransDiscountDetailInfo infoRtn = null;
			Connection conn = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			try
			{
				/* 需要匹配如下这些字段		
				nDiscountContractID	转贴现合同号
				nDiscountCredence	转贴现凭证
				nDiscountCounterPartid 交易对手 
				mDiscountBillAmount	汇票金额
				DiscountCredenceAmount	转贴现金额
				mInterest	利息	
				加一个条件  录入人不能是自己			
				*/
				long nDiscountContractID = info.getNDiscountContractID();
				long nDiscountCredence = info.getNDiscountCredence();
				long nDiscountCounterPartid = info.getNDiscountCounterPartid();
				double mDiscountBillAmount = info.getMDiscountBillAmount();
				double DiscountCredenceAmount = info.getDiscountCredenceAmount();
				double mInterest = info.getMInterest();
				long userFromJsp = info.getNInputUserID();

				StringBuffer buffer = new StringBuffer("");
				buffer.append(" select * from sett_transcredence where id is not null ");
				buffer.append(" and ndiscountcounterpartid=" + nDiscountCounterPartid);
				buffer.append(" and ndiscountcontractid=" + nDiscountContractID);
				buffer.append(" and ndiscountcredence=" + nDiscountCredence);
				//必须交易状态必须为保存
				buffer.append(" and nstatusid=" + info.getNStatusID());
				//交易号不能为空
				buffer.append(" and stransno is not null ");
				//录入人不能是自己
				buffer.append(" and ninputuserid !=" + userFromJsp + "  ");
				String sql = buffer.toString();
				System.out.println("\n\n" + sql + "\n\n");
				conn = this.getConnection();
				ps = conn.prepareStatement(sql);
				rs = ps.executeQuery();
				infoRtn = this.transferResultIntoInfo(rs);
			}
			finally
			{
				this.cleanup(rs);
				this.cleanup(ps);
				this.cleanup(conn);
			}

			return infoRtn;
		}
		/**
		 * 方法说明：根据查询条件组合，查询出符合的记录
		 *  Method findByConditions.
		 * @param sett_TransCurrentDepositInfo
		 * @param orderBySequence 
		 * @return Collection
		 */
		public Collection findByConditions(TransDiscountSubjectInfo conditionInfo)
			throws Exception
		{

			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			Collection result = null;
			try
			{
				con = getConnection();
				StringBuffer strSQLBuffer = new StringBuffer(256);

				strSQLBuffer.append("SELECT * FROM \n");
				strSQLBuffer.append("sett_discountsubject \n");

				boolean isNeedWhere = false;

				StringBuffer strWhereSQLBuffer = new StringBuffer(128);

				if (conditionInfo.getTransDiscountID() != -1)
				{
					strSQLBuffer.append(" where transdiscountid = " + conditionInfo.getTransDiscountID() + " \n");
					isNeedWhere = true;
				}
				System.out.println(strSQLBuffer.toString());
				ps = con.prepareStatement(strSQLBuffer.toString());
				rs = ps.executeQuery();

				result = this.getInfoFromResultSet(rs);
			}

			finally
			{
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			}
			return result;
		}
		private Collection getInfoFromResultSet(ResultSet rs) throws Exception
		{

			ArrayList list = new ArrayList();
			while (rs.next())
			{
				TransDiscountSubjectInfo info = new TransDiscountSubjectInfo();

				info.setID(rs.getLong("ID"));
				info.setTransDiscountID(rs.getLong("TRANSDISCOUNTID"));
				info.setMamount(rs.getDouble("MAMOUNT"));
				info.setMdirection(rs.getLong("MDIRECTION"));
				info.setTransTypeID(rs.getLong("TRANSTYPEID"));
				info.setGeneralLedger(rs.getLong("GENERALLEDGER"));
				list.add(info);
			}
			return list;
		}
		/**
		 * 方法说明：修改状态
		 * @param StatusID  0已删除1暂存2保存（未复核）3已复核4未签认5已签认6已确认7已勾账
		 * @return long  
		 * @throws IException
		 */
		public long updateTransStatus(long id, long StatusID, long lUserID, String strAbstract, String strCheckAbstract) throws Exception
		{
			long lReturn = -1;
			Connection conn = null;
			PreparedStatement ps = null;
			try
			{
				conn = this.getConnection();
				String sql = " update sett_transcredence set nStatusID=?,nCheckUserID=?,sAbstract=?,sConfirmAbstract=?,dtModify=sysdate where id=? ";
				ps = conn.prepareStatement(sql);
				ps.setLong(1, StatusID);
				ps.setLong(2, lUserID);
				ps.setString(3, strAbstract);
				ps.setString(4, strCheckAbstract);
				ps.setLong(5, id);

				int n = ps.executeUpdate();
				if (n > 0)
					lReturn = id;
			}
			finally
			{
				this.cleanup(ps);
				this.cleanup(conn);
			}

			return lReturn;
		}
		/**
		 *方法说明：根据查询条件组合，查询出符合的记录
		 *  Method findByConditions.
		 * @param nOfficeID   //办事处ID
		 * @param nCurrencyID //币种ID
		 * @param nInputUserID     //用户ID
		 * @param nQueryTypeID     // 查询类型：0，（处理的查找）；1，（复核的查找） 2.
		 * @param nStatusID   //交易状态
		 * @param dtExecute        //查询日期 即执行日期
		 * @param nTransactionTypeID  //  业务类型
		 * @param orderByType//排序字段
		 * @param isDesc     //是否为降序 
		 * @return Collection
		 */
		public Collection findByConditions(QueryConditionInfo info) throws Exception
		{
			long nOfficeID = info.getOfficeID();
			long nCurrencyID = info.getCurrencyID();
			long nUserID = info.getUserID();
			long nTypeID = info.getTypeID();
			long nTransactionTypeID = info.getTransactionTypeID();
			long nStatusID = info.getStatusID();
			int orderByType = (int) info.getOrderByType();
			boolean isDesc = info.getDesc();
			Timestamp date = info.getDate();
			String dt = DataFormat.getDateString(date);

			Collection cln = null;
			Connection conn = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			try
			{
				conn = this.getConnection();
				StringBuffer buffer = new StringBuffer("");
				
				buffer.append(" select * ");

				buffer.append(" from sett_transcredence  \n");
				buffer.append(" where ID is not null ");

				//日期特别处理
				if (nStatusID != SETTConstant.TransactionStatus.TEMPSAVE)
				{
					buffer.append(" and dtExecute=to_date('" + DataFormat.getDateString(date) + "','yyyy-mm-dd')");
				}

				buffer.append(" and nOfficeID=" + nOfficeID);
				buffer.append(" and nCurrencyID=" + nCurrencyID);

				//根据查询类型处理用户ID
				if (nTypeID == 0)
				{
					buffer.append(" and nInputUserID=" + nUserID);
					buffer.append(" and nCheckUserID!=" + nUserID);
				}
				else if (nTypeID == 1)
				{
					buffer.append(" and nCheckUserID=" + nUserID);
					buffer.append(" and nInputUserID!=" + nUserID);
				}

				buffer.append(" and nTransactionTypeID=" + nTransactionTypeID);
				
				if (nTypeID == 0)
				{//处理的查找
					if (nStatusID > 0)
						buffer.append(" and nStatusID=" + nStatusID);
					else
						buffer.append(" and nStatusID in(" + SETTConstant.TransactionStatus.TEMPSAVE + "," + SETTConstant.TransactionStatus.SAVE + ") ");
				}
				else if(nTypeID == 1)
				{//复核的查找
					buffer.append(" and nStatusID=" + nStatusID);
				}

				//处理查询

				switch (orderByType)
				{
					case ORDERBY_STRANSNO :
						buffer.append(" order by sTransNo ");
						break;
					case ORDERBY_NTRANSACTIONTYPEID :
						buffer.append(" order by nTransactionTypeID ");
						break;
				///	case ORDERBY_NDISCOUNTACCOUNTID :
				//		buffer.append(" order by nDiscountAccountID ");
				//		break;
					case ORDERBY_NDISCOUNTCONTRACTID :
						buffer.append(" order by NDISCOUNTCONTRACTID ");
						break;
					case ORDERBY_NDISCOUNTNOTEID :
						buffer.append(" order by NDISCOUNTCREDENCE ");
						break;
					case ORDERBY_NDEPOSITACCOUNTID :
						buffer.append(" order by nDepositAccountID ");
						break;
					case ORDERBY_MDISCOUNTBILLAMOUNT :
						buffer.append(" order by MDISCOUNTBILLAMOUNT ");
						break;
					case ORDERBY_MINTEREST :
						buffer.append(" order by MINTEREST ");
						break;
					case ORDERBY_MDISCOUNTAMOUNT :
						buffer.append(" order by DISCOUNTCREDENCEAMOUNT ");
						break;
					case ORDERBY_SABSTRACT :
						buffer.append(" order by sAbstract ");
						break;
					case ORDERBY_NSTATUSID :
						buffer.append(" order by nStatusID ");
						break;
					default :
						buffer.append(" order by sTransNo ");
						break;
				}
				if (isDesc)
					buffer.append(" desc ");
				else
					buffer.append(" asc ");

				String sql = buffer.toString();

				System.out.println("\n\n" + sql + "\n\n");

				ps = conn.prepareStatement(sql);
				rs = ps.executeQuery();
				cln = this.transferResultIntoCollection(rs);

			}
			finally
			{
				this.cleanup(rs);
				this.cleanup(ps);
				this.cleanup(conn);
			}

			return cln;
		}
		private Collection transferResultIntoCollection(ResultSet rs) throws Exception
		{
			ArrayList list = new ArrayList();
			TransDiscountDetailInfo info = null;
			while (rs.next())
			{
				info = new TransDiscountDetailInfo();
				info.setID(rs.getLong("ID"));
				info.setNOfficeID(rs.getLong("nOfficeID"));
				info.setNCurrencyID(rs.getLong("nCurrencyID"));
				info.setSTransNo(rs.getString("sTransNo"));
				info.setNTransactionTypeID(rs.getLong("nTransactionTypeID"));
				info.setNDiscountContractID(rs.getLong("nDiscountContractID"));
				info.setNDiscountCredence(rs.getLong("nDiscountCredence"));
				info.setMDiscountBillAmount(rs.getDouble("mDiscountBillAmount"));
				info.setDiscountCredenceAmount(rs.getDouble("DISCOUNTCREDENCEAMOUNT"));
				info.setNDepositAccountID(rs.getLong("nDepositAccountID"));
				info.setNReceiveClientID(rs.getLong("NRECEIVECLIENTID"));
				info.setNReceiveAccountID(rs.getLong("NRECEIVEACCOUNTID"));
				info.setNPayClientID(rs.getLong("NPAYCLIENTID"));
				info.setNPayAccountID(rs.getLong("NPAYACCOUNTID"));			
				info.setNBankID(rs.getLong("nBankID"));
				info.setSExtAccountNo(rs.getString("SEXTACCOUNTNO"));
				info.setSExtClientName(rs.getString("SEXTCLIENTNAME"));
				info.setSRemitinBank(rs.getString("SREMITINBANK"));
				info.setSRemitinProvince(rs.getString("SREMITINPROVINCE"));
				info.setSRemitinCity(rs.getString("SREMITINCITY"));
				info.setNCashFlowID(rs.getLong("nCashFlowID"));
				info.setMInterest(rs.getDouble("mInterest"));
				info.setDtInterestStart(rs.getTimestamp("dtInterestStart"));
				info.setDtExecute(rs.getTimestamp("dtExecute"));
				info.setDtModify(rs.getTimestamp("dtModify"));
				info.setDtInput(rs.getTimestamp("dtInput"));
				info.setNInputUserID(rs.getLong("nInputUserID"));
				info.setNCheckUserID(rs.getLong("nCheckUserID"));
				info.setSAbstract(rs.getString("sAbstract"));
				info.setSConfirmAbstract(rs.getString("SCONFIRMABSTRACT"));
				info.setNStatusID(rs.getLong("nStatusID"));			
				info.setNAbstractID(rs.getLong("nAbstractID"));
				info.setNDiscountCounterPartid(rs.getLong("NDISCOUNTCOUNTERPARTID"));
				list.add(info);
				Log.print("\n\n id=" + info.getID() + "\n\n");
			}

			return list;
		}
		/**
		 * 方法说明：删除转贴现发放交易记录
		 * @param info :Sett_TransCurrentDepositInfo
		 * @return : long - 返回被删除转贴现发放交易记录ID
		 * @throws IException
		 */
		public long delDiscountInfo(TransDiscountDetailInfo info) throws Exception
		{
			Sett_TransGrantDiscountDAO settDAO = new Sett_TransGrantDiscountDAO();
			long id = settDAO.updateTransStatus(info);

			return id;
		}
		public long updateTransStatus(TransDiscountDetailInfo info) throws Exception
		{
			long lReturn = -1;
			Connection conn = null;
			PreparedStatement ps = null;
			try
			{
				conn = this.getConnection();
				String sql = " update sett_transcredence set nStatusID=?,ncheckuserid=?,sabstract=?,sconfirmabstract=?,dtModify=sysdate where id=? ";
				ps = conn.prepareStatement(sql);
				ps.setLong(1, info.getNStatusID());
				ps.setLong(2, info.getNCheckUserID());
				ps.setString(3, info.getSAbstract());
				ps.setString(4, info.getSConfirmAbstract());
				ps.setLong(5, info.getID());

				int n = ps.executeUpdate();
				if (n > 0)
					lReturn = info.getID();
			}
			finally
			{
				this.cleanup(ps);
				this.cleanup(conn);
			}

			return lReturn;
		}
//		转贴现发放时，根据贴现票据ID，得到保存过的贴现发放表信息
		public TransGrantDiscountInfo findDiscountInfoByDiscountBillID(long nDiscountBillID) throws Exception
		{
			TransGrantDiscountInfo info = null;
			Connection conn = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			try
			{
				conn = this.getConnection();
				String sql = " select a.* from sett_transgrantdiscount a, loan_discountcontractbill b " 
					+ " where a.ndiscountnoteid = b.ndiscountcredenceid " 
					+ " and a.nstatusid = " + SETTConstant.TransactionStatus.CHECK
					+ " and b.id=?";
				ps = conn.prepareStatement(sql);
				ps.setLong(1, nDiscountBillID);
				rs = ps.executeQuery();
				info = this.transferResultsetIntoInfo(rs);
			}
			finally
			{
				this.cleanup(rs);
				this.cleanup(ps);
				this.cleanup(conn);
			}

			return info;
		}	
		
		public ArrayList getGrantDiscountInfo(TransGrantDiscountInfo findInfo)throws Exception
		{
			ArrayList arrayList = new ArrayList();

			TransGrantDiscountInfo info = null;
			Connection conn = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			StringBuffer sbSql = new StringBuffer();
			try
			{
				conn = this.getConnection();
				sbSql.append("select dc.* from loan_discountpayform dpf,loan_discountcredence dc where dpf.id = dc.discountpayform \n");
				sbSql.append(" and dpf.id = ?" );
				System.out.println(" dpf.id = " + findInfo.getDiscountNoteID());
				ps = conn.prepareStatement(sbSql.toString());
				ps.setLong(1, findInfo.getDiscountNoteID());
				rs = ps.executeQuery();
				
				while(rs.next())
				{
					TransGrantDiscountInfo transInfo = new TransGrantDiscountInfo();
					transInfo.setDiscountBillAmount(rs.getDouble("MAMOUNT"));//票面金额
					transInfo.setDiscountAmount(rs.getDouble("MAMOUNT")-rs.getDouble("MINTEREST"));
					transInfo.setInterest(rs.getDouble("PURCHASERINTEREST")+rs.getDouble("MINTEREST"));
					transInfo.setInterestOfSign(rs.getDouble("PURCHASERINTEREST"));//出票人承担
					transInfo.setInterestOfDiscount(rs.getDouble("MINTEREST"));//贴现人承担
					transInfo.setDiscountNoteID(rs.getLong("id"));
					arrayList.add(transInfo);
				}
				
			}
			finally
			{
				this.cleanup(rs);
				this.cleanup(ps);
				this.cleanup(conn);
			}
			return arrayList;
		}

}
