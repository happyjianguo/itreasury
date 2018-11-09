/*
 * Created on 2003-10-29
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.iss.itreasury.settlement.transloan.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;
import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.loan.base.LoanException;
import com.iss.itreasury.loan.loaninterestsetting.dataentity.AdjustInterestConditionInfo;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.settlement.transloan.bizlogic.BankLoanQuery;
import com.iss.itreasury.settlement.transloan.dataentity.SubLoanAccountDetailInfo;
import com.iss.itreasury.settlement.transloan.dataentity.TransRepaymentLoanInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.settlement.util.UtilOperation;
import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.settlement.account.dao.Sett_SubAccountDAO;
import com.iss.itreasury.settlement.account.dataentity.SubAccountAssemblerInfo;
import com.iss.itreasury.settlement.account.dataentity.SubAccountLoanInfo;
import com.iss.itreasury.settlement.interest.bizlogic.InterestCalculation;
/**
 * @author yychen
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Sett_TransRepaymentLoanDAO extends SettlementDAO
{
	private final static StringBuffer sbRepaymentSQL = new StringBuffer(128);
	private static Logger logger = Logger.getLogger(Sett_TransGrantLoanDAO.class);
	/**
	 * 交易号
	 */
	public final static int ORDER_TRANS_NO = 1;
	private final static String ORDER_TRANS_NO_NAME = "sTransNo";
	/**
	 * 交易类型
	 */
	public final static int ORDER_TRANSACTION_TYPE_ID = 2;
	private final static String ORDER_TRANSACTION_TYPE_ID_NAME = "nTransactionTypeID";
	/**
	 * 贷款账户
	 */
	public final static int ORDER_LOAN_ACCOUNT_ID = 3;
	private final static String ORDER_LOAN_ACCOUNT_ID_NAME = "nLoanAccountID";
	/**
	 * 贷款合同号
	 */
	public final static int ORDER_LOAN_CONTRACT_ID = 4;
	private final static String ORDER_LOAN_CONTRACT_ID_NAME = "nLoanContractID";
	/**
	 * 放款通知单
	 */
	public final static int ORDER_LOAN_NOTE_ID = 5;
	private final static String ORDER_LOAN_NOTE_ID_NAME = "nLoanNoteID";
	/**
	 * 活期存款账户ID
	 */
	public final static int ORDER_DEPOSIT_ACCOUNT_ID = 6;
	private final static String ORDER_DEPOSIT_ACCOUNT_ID_NAME = "nDepositAccountID";
	/**
	 * 金额
	 */
	public final static int ORDER_AMOUNT = 7;
	private final static String ORDER_AMOUNT_NAME = "mAmount";
	/**
	 * 起息日
	 */
	public final static int ORDER_INTEREST_START = 8;
	private final static String ORDER_INTEREST_START_NAME = "dtInterestStart";
	/**
	 * 摘要
	 */
	public final static int ORDER_ABSTRACT = 9;
	private final static String ORDER_ABSTRACT_NAME = "sAbstract";
	/**
	 * 交易状态
	 */
	public final static int ORDER_STATUS = 10;
	private final static String ORDER_STATUS_NAME = "nStatusID";
	/**
	 * 修改时间
	 */
	public final static int ORDER_MODIFY = 11;
	/**
	 * 录入日期
	 */
	public final static int ORDER_INPUT_DATE = 12;

	/**
	 * 付息活期账户
	 */
	public final static int ORDER_PAYINTEREST_ACCOUNT = 13;
	private final static String ORDER_PAYINTEREST_ACCOUNT_NAME = "nPayInterestAccountID";

	/**
	 * 交易号
	 */
	public final static int ORDER_TEMPTRANS_NO = 14;
	private final static String ORDER_TEMPTRANS_NO_NAME = "sTempTransNo";
	
	static {
		sbRepaymentSQL.append("ID, NOFFICEID, NCURRENCYID, STRANSNO, NTRANSACTIONTYPEID, \n");
		sbRepaymentSQL.append("NFREEFORMID, NISCANCLELOAN, NCLIENTID, NDEPOSITACCOUNTID, SCONSIGNVOUCHERNO, \n");
		sbRepaymentSQL.append("SCONSIGNPASSWORD, SBILLNO, NBILLTYPEID, NBILLBANKID, NBANKID, \n");
		sbRepaymentSQL.append("SEXTBANKNO, NCASHFLOWID, NLOANACCOUNTID, NLOANCONTRACTID, NLOANNOTEID, \n");
		sbRepaymentSQL.append("NPREFORMID, MAMOUNT, DTINTERESTSTART, NABSTRACTID, SABSTRACT, \n");
		sbRepaymentSQL.append("NCONSIGNACCOUNTID, NCONSIGNDEPOSITACCOUNTID, NPAYINTERESTACCOUNTID, NINTERESTBANKID, NINTERESTCASHFLOWID, \n");
		sbRepaymentSQL.append("NRECEIVEINTERESTACCOUNTID, NPAYSUERTYFEEACCOUNTID, NSUERTYFEEBANKID, NSUERTYFEECASHFLOWID, NRECEIVESUERTYFEEACCOUNTID, \n");
		sbRepaymentSQL.append("NCOMMISSIONACCOUNTID, NCOMMISSIONBANKID, NCOMMISSIONCASHFLOWID, MINTEREST, MINTERESTRECEIVABLE, \n");
		sbRepaymentSQL.append("MINTERESTINCOME, MINTERESTTAX, MCOMPOUNDINTEREST, MOVERDUEINTEREST, MSURETYFEE, \n");
		sbRepaymentSQL.append("MCOMMISSION, SADJUSTINTERESTREASON, MADJUSTINTEREST, MAHEADRECEIVEINTEREST, NISREMITINTEREST, \n");
		sbRepaymentSQL.append("NISREMITCOMPOUNDINTEREST, NISREMITOVERDUEINTEREST, NISREMITSURETYFEE, NISREMITCOMMISSION, NCAPITALANDINTERESTDEALWAY, \n");
		sbRepaymentSQL.append("MREALINTEREST, MREALINTERESTRECEIVABLE, MREALINTERESTINCOME, MREALINTERESTTAX, MREALCOMPOUNDINTEREST, \n");
		sbRepaymentSQL.append("MREALOVERDUEINTEREST, MREALSURETYFEE, MREALCOMMISSION, DTINPUT, NINPUTUSERID, \n");
		sbRepaymentSQL.append("NCHECKUSERID, SCHECKABSTRACT, NSTATUSID, DTMODIFY,DTEXECUTE,DTINTERESTCLEAR,NSUBACCOUNTID,mCurrentBalance, \n");
		sbRepaymentSQL.append("dtLatestInterestClear,nTransDirectionID,sDeclarationNo,sTempTransNO, \n");
		sbRepaymentSQL.append("dtCompoundInterestStart,mCompoundAmount,mCompoundRate, \n");	//复利信息
		sbRepaymentSQL.append("dtOverDueStart,mOverDueAmount,mOverDueRate, \n");			//逾期罚息信息
		sbRepaymentSQL.append("mLoanRepaymentRate,dtSuretyFeeStart,mSuretyFeeRate, \n");	//为打印添加第二组信息
		sbRepaymentSQL.append("dtCommissionStart,mCommissionRate,nSerialNo,SINSTRUCTIONNO "); //为网银添加
	}
	/**
	 * 增加贷款收回记录
	 * @param info TransRepaymentLoanInfo
	 * @return TransRepaymentLoanInfo ID
	 * @throws SQLException
	 */
	public long add(TransRepaymentLoanInfo info) throws SQLException, NullPointerException,IException
	{
		Log.print("开始进入add............");
		long id = -1;
		Connection conn = null;
		StringBuffer buffer = new StringBuffer(128);
		buffer.append("insert into SETT_TRANSREPAYMENTLOAN \n");
		buffer.append("(  \n");
		buffer.append(sbRepaymentSQL.toString());
		buffer.append(" ) \n");
		buffer.append("\n");
		buffer.append("values \n");
		buffer.append("( ");
		buffer.append("?,?,?,?,?,?,?,?,?,?, ");
		buffer.append("?,?,?,?,?,?,?,?,?,?, ");
		buffer.append("?,?,?,?,?,?,?,?,?,?, ");
		buffer.append("?,?,?,?,?,?,?,?,?,?, ");
		buffer.append("?,?,?,?,?,?,?,?,?,?, ");
		buffer.append("?,?,?,?,?,?,?,?,?,?, ");
		buffer.append("?,?,?,?,?,?,?,?,?,?, ");
		buffer.append("?,?,?,?,?,?,?,?,?,?, ");
		buffer.append("?,?,?,?,?,?,?,?,?,? ) ");
		
		Log.print(buffer.toString());

		PreparedStatement pstmt = null;
		try
		{
			conn = this.getConnection();
			
			id = getNextID(conn);
			info.setID(id);
			int index = 1;
			pstmt = conn.prepareStatement(buffer.toString());
			////1-----10
			pstmt.setLong(index++, info.getID());						//~1
			pstmt.setLong(index++, info.getOfficeID());					//~2
			pstmt.setLong(index++, info.getCurrencyID());				//~3
			pstmt.setString(index++, info.getTransNo());				//~4
			pstmt.setLong(index++, info.getTransactionTypeID());		//~5
			pstmt.setLong(index++, info.getFreeFormID());				//~6
			pstmt.setLong(index++, info.getIsCancelLoan());				//~7
			pstmt.setLong(index++, info.getClientID());					//~8
			pstmt.setLong(index++, info.getDepositAccountID());			//~9
			pstmt.setString(index++, info.getConsignPayVoucherNo());	//~10
			///11-----20
			pstmt.setString(index++, info.getConsignPayPWD());			//~11
			pstmt.setString(index++, info.getBillNo());					//~12
			pstmt.setLong(index++, info.getBillTypeID());				//~13
			pstmt.setLong(index++, info.getBillBankID());				//~14
			pstmt.setLong(index++, info.getBankID());					//~15
			pstmt.setString(index++, info.getExtBankNo());				//~16
			pstmt.setLong(index++, info.getCashFlowID());				//~17
			pstmt.setLong(index++, info.getLoanAccountID());			//~18
			pstmt.setLong(index++, info.getLoanContractID());			//~19
			pstmt.setLong(index++, info.getLoanNoteID());				//~20
			////21-----30
			pstmt.setLong(index++, info.getPreFormID());				//~21
			pstmt.setDouble(index++, info.getAmount());					//~22
			pstmt.setTimestamp(index++, info.getInterestStart());		//~23
			pstmt.setLong(index++, info.getAbstractID());				//~24
			pstmt.setString(index++, info.getAbstract());				//~25
			pstmt.setLong(index++, info.getConsignAccountID());			//~26
			pstmt.setLong(index++, info.getConsignDepositAccountID());	//~27
			pstmt.setLong(index++, info.getPayInterestAccountID());		//~28
			pstmt.setLong(index++, info.getInterestBankID());			//~29
			pstmt.setLong(index++, info.getInterestCashFlowID());		//~30
			////31-----40
			pstmt.setLong(index++, info.getReceiveInterestAccountID());	//~31
			pstmt.setLong(index++, info.getPaySuretyAccountID());		//~32
			pstmt.setLong(index++, info.getSuretyBankID());				//~33
			pstmt.setLong(index++, info.getSuretyCashFlowID());			//~34
			pstmt.setLong(index++, info.getReceiveSuretyAccountID());	//~35
			pstmt.setLong(index++, info.getCommissionAccountID());		//~36
			pstmt.setLong(index++, info.getCommissionBankID());			//~37
			pstmt.setLong(index++, info.getCommissionCashFlowID());		//~38
			pstmt.setDouble(index++, info.getInterest());				//~39
			pstmt.setDouble(index++, info.getInterestReceiveAble());	//~40
			////41-----50
			pstmt.setDouble(index++, info.getInterestIncome());			//~41
			pstmt.setDouble(index++, info.getInterestTax());			//~42
			pstmt.setDouble(index++, info.getCompoundInterest());		//~43
			pstmt.setDouble(index++, info.getOverDueInterest());		//~44
			pstmt.setDouble(index++, info.getSuretyFee());				//~45
			pstmt.setDouble(index++, info.getCommission());				//~46
			pstmt.setString(index++, info.getAdjustInterestReason());	//~47
			pstmt.setDouble(index++, info.getAdjustInterest());			//~48
			pstmt.setDouble(index++, info.getAheadRepayInterest());		//~49
			pstmt.setDouble(index++, info.getIsRemitInterest());		//~50
			////51-----60
			pstmt.setLong(index++, info.getIsRemitCompoundInterest());	//~51
			pstmt.setLong(index++, info.getIsRemitOverDueInterest());	//~52
			pstmt.setLong(index++, info.getIsRemitSuretyFee());			//~53
			pstmt.setLong(index++, info.getIsRemitCommission());		//~54
			pstmt.setLong(index++, info.getCapitalAndInterstDealway());	//~55
			pstmt.setDouble(index++, info.getRealInterest());			//~56
			pstmt.setDouble(index++, info.getRealInterestReceiveAble());//~57
			pstmt.setDouble(index++, info.getRealInterestIncome());		//~58
			pstmt.setDouble(index++, info.getRealInterestTax());		//~59
			pstmt.setDouble(index++, info.getRealCompoundInterest());	//~60
			////61-----70
			pstmt.setDouble(index++, info.getRealOverDueInterest());	//~61
			pstmt.setDouble(index++, info.getRealSuretyFee());			//~62
			pstmt.setDouble(index++, info.getRealCommission());			//~63
			pstmt.setTimestamp(index++, info.getInput());				//~64
			pstmt.setLong(index++, info.getInputUserID());				//~65
			pstmt.setLong(index++, info.getCheckUserID());				//~66
			pstmt.setString(index++, info.getCheckAbstract());			//~67
			pstmt.setLong(index++, info.getStatusID());					//~68
			pstmt.setTimestamp(index++, info.getModify());				//~69
			pstmt.setTimestamp(index++, info.getExecute());				//~70
			////71-----80
			pstmt.setTimestamp(index++, info.getInterestClear());		//~71
			pstmt.setLong(index++, info.getSubAccountID());				//子账户 72
			pstmt.setDouble(index++,info.getCurrentBalance());			//累计还款金额 73
			pstmt.setTimestamp(index++,info.getLatestInterestClear());	//上次结息日期 74
			
			/**
			 * 多笔贷款收回 特有参数
			 */
			pstmt.setLong(index++,info.getTransDirectionID());			//交易方向 75
			pstmt.setString(index++,info.getDeclarationNo());			//报单号 76
			pstmt.setString(index++,info.getTempTransNO());				//临时交易号 77
			
			/**
			 * 为打印添加参数
			 */
			pstmt.setTimestamp(index++,info.getCompoundInterestStart());//复利起息日 78
			pstmt.setDouble(index++,info.getCompoundAmount());			//复利本金 79
			pstmt.setDouble(index++,info.getCompoundRate());			//复利利率 80
			////81-----90
			pstmt.setTimestamp(index++,info.getOverDueStart());			//逾期罚息起息日 81
			pstmt.setDouble(index++,info.getOverDueAmount());			//逾期罚息本金 82
			pstmt.setDouble(index++,info.getOverDueRate());				//逾期罚息利率 83
			/**
			 * 为打印添加的第二组参数,另外添加多笔贷款收回序列号
			 * mLoanRepaymentRate,dtSuretyFeeStart,mSuretyFeeRate
			 * dtCommissionStart,mCommissionRate,nSerialNo
			 */
			pstmt.setDouble(index++,info.getLoanRepaymentRate());		//还款利率 84
			pstmt.setTimestamp(index++,info.getSuretyFeeStart());		//担保费起息日 85
			pstmt.setDouble(index++,info.getSuretyFeeRate());			//担保费费率 86
			pstmt.setTimestamp(index++,info.getCommissionStart());		//手续费起息日 87
			pstmt.setDouble(index++,info.getCommissionRate());			//手续费率 88
			pstmt.setLong(index++,info.getSerialNo());					//多笔贷款收回序列号 89
			pstmt.setString(index++,info.getInstructionNo());			//为网银添加的参数 90
			
			int intExe = pstmt.executeUpdate();

			this.cleanup(pstmt);
		}
		finally
		{
		    this.cleanup(pstmt);
			this.cleanup(conn);
		}
		return id;
	}
	/**
	 * 修改贷款收回记录的状态
	 * @param id long
	 * @param statusId long
	 * @return id
	 * @throws SQLException
	 */
	public long updateStatus(long id, long statusId) throws SQLException
	{
		long returnValue = -1;
		Connection conn = null;
		PreparedStatement pstmt = null;
		try
		{
			conn = this.getConnection();
			pstmt = conn.prepareStatement("update SETT_TRANSREPAYMENTLOAN set NSTATUSID=?,dtModify=sysdate where ID=? ");
			pstmt.setLong(1, statusId);
			pstmt.setLong(2, id);
			pstmt.executeUpdate();
			returnValue = id;
			this.cleanup(pstmt);
			this.cleanup(conn);
		}
		finally
		{
		    this.cleanup(pstmt);
			this.cleanup(conn);
		}
		return returnValue;
	}
	/**
	 * 改变免还通知单的状态
	 * @param id
	 * @param statusID
	 * @throws SQLException
	 */
	public void updateFreeFormStatus(long id, long statusID) throws SQLException
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		try
		{
			conn = this.getConnection();
			pstmt = conn.prepareStatement("update " + "loan_FreeForm set nStatusId=? where id=?");
			pstmt.setLong(1, statusID);
			pstmt.setLong(2, id);
			pstmt.executeUpdate();
			this.cleanup(pstmt);
			this.cleanup(conn);
		}
		finally
		{
			this.cleanup(pstmt);
			this.cleanup(conn);
		}
	}
	public boolean isFreeFormStatusCorrect(long id,long lStatusID)throws IException{
		boolean blnIsExeOk 	= false;
		Connection conn 	= null;
		PreparedStatement ps= null;
		ResultSet rs		= null;
		try{
			conn = this.getConnection();
			ps = conn.prepareStatement("select id from loan_FreeForm where " +
					"id="+id+" and nStatusID="+lStatusID);
			rs = ps.executeQuery();
			if (rs.next())
			{
			    blnIsExeOk = true;
			}
			this.cleanup(rs);
			this.cleanup(ps);
			this.cleanup(conn);
		}catch(SQLException e){
			throw new IException("查询免还通知单错误",e);
		}finally{
			try{
				this.cleanup(rs);
				this.cleanup(ps);
				this.cleanup(conn);
			}catch(SQLException e){
				throw new IException("查询免还通知单错误",e);
			}
		}
		return blnIsExeOk;
	}
	
	/**
	 * 改变提前还款通知单的状态
	 * @param id
	 * @param statusID
	 * @throws SQLException
	 */
	public void updatePreFormStatus(long id, long statusID) throws SQLException
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		try
		{
			conn = this.getConnection();
			pstmt = conn.prepareStatement("update " + "LOAN_AHEADREPAYFORM set nStatusId=? where id=?");
			pstmt.setLong(1, statusID);
			pstmt.setLong(2, id);
			pstmt.executeUpdate();
			this.cleanup(pstmt);
			this.cleanup(conn);
		}
		finally
		{
			this.cleanup(pstmt);
			this.cleanup(conn);
		}
	}
	public boolean isPreFormStatusCorrect(long id,long lStatusID)throws IException{
		boolean blnIsExeOk 	= false;
		Connection conn 	= null;
		PreparedStatement ps= null;
		ResultSet rs		= null;
		try{
			conn = this.getConnection();
			ps = conn.prepareStatement("select id from LOAN_AHEADREPAYFORM where " +
					"id="+id+" and nStatusID="+lStatusID);
			rs = ps.executeQuery();
			if (rs.next())
			{
			    blnIsExeOk = true;
			}
			this.cleanup(rs);
			this.cleanup(ps);
			this.cleanup(conn);
		}catch(SQLException e){
			throw new IException("查询提前还款通知单错误",e);
		}finally{
			try{
				this.cleanup(rs);
				this.cleanup(ps);
				this.cleanup(conn);
			}catch(SQLException e){
				throw new IException("查询提前还款通知单错误",e);
			}
		}
		return blnIsExeOk;
	}
	/**
		 * 修改贷款收回记录的状态
		 * @param id long
		 * @param statusId long
		 * @param strAbstract String
		 * @param lUserID long
		 * @return id
		 * @throws SQLException
		 */
	public long updateStatus(long id, long statusId, String strAbstract, long lUserID) throws SQLException
	{
		long returnValue = -1;
		Connection conn = null;
		PreparedStatement pstmt = null;
		try
		{
			conn = this.getConnection();
			pstmt = conn.prepareStatement("update SETT_TRANSREPAYMENTLOAN set NSTATUSID=?,SCHECKABSTRACT=?,NCHECKUSERID=?,dtModify=sysdate where ID=? ");
			pstmt.setLong(1, statusId);
			pstmt.setString(2, strAbstract);
			pstmt.setLong(3, lUserID);
			pstmt.setLong(4, id);
			pstmt.executeUpdate();
			this.cleanup(pstmt);
			this.cleanup(conn);
			returnValue = id;
		}
		finally
		{
		    this.cleanup(pstmt);
			this.cleanup(conn);
		}
		return returnValue;
	}
	
	/**
	 * 把交易表中的交易号修改为指定值,用于多笔贷款收回勾账和取消勾账中更改交易号和序列号
	 * @param id
	 * @param strTransNo
	 */
	public void updateTransNo(long id,String strTransNo,long lSerialNo)throws SQLException{
		Connection conn = null;
		Log.print("===开始改变贷款回收交易表中的第"+id+"条的交易号为:"+strTransNo);
		PreparedStatement ps = null;
		try{
			StringBuffer sbSQL = new StringBuffer(128);
			sbSQL.append("update Sett_TransRepaymentLoan set sTransNo=" + strTransNo + "\n");
			if (lSerialNo>0){
				sbSQL.append(",nSerialNo = " + lSerialNo + "\n");
			}
			sbSQL.append("where id = " + id);
			conn = this.getConnection();
			ps = conn.prepareStatement(sbSQL.toString());
			ps.executeUpdate();
			this.cleanup(ps);
			this.cleanup(conn);
		}finally{
		    this.cleanup(ps);
			this.cleanup(conn);
		}
	}
	/**
	 * 多笔贷款收回勾账查询
	 * @param condiInfo
	 * @return
	 * @throws IException
	 */
	public Collection findSquareUpRecordsByCondition(TransRepaymentLoanInfo condiInfo)throws IException{
		LinkedList lList = new LinkedList();
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		Log.print("====开始查询待勾账的记录====");
		try{
			conn = this.getConnection();
			StringBuffer sbSql = new StringBuffer(128);
			sbSql.append("select "+sbRepaymentSQL.toString()+" from Sett_TransRepaymentLoan where 1=1 \n");
			
			/**
			 * 拼SQL语句
			 */
			if (condiInfo.getClientID()>0){
				sbSql.append("and nClientID=? \n");
			}
			if (condiInfo.getDeclarationNo()!=null && condiInfo.getDeclarationNo().length()>0){
				sbSql.append("and sDeclarationNo=? \n");
			}
			Log.print("方法内的执行日："+condiInfo.getExecute());
			if(condiInfo.getExecute()!=null){
				Log.print("方法内的执行日："+condiInfo.getExecute().toString());
				sbSql.append("and dtExecute=to_date('"+DataFormat.getDateString(condiInfo.getExecute())+"','yyyy-mm-dd') \n");
			}
			if (condiInfo.getTransNo()!=null && condiInfo.getTransNo().length()>0){
				sbSql.append("and sTransNo=? \n");
			}
			sbSql.append("and nStatusID=? \n");
			sbSql.append("and nTransactionTypeID=? \n");
			//拼排序字段
			sbSql.append("order by \n");
			if (condiInfo.getOrderByType()>0){
				sbSql.append(getOrderColumnName((int)condiInfo.getOrderByType()) + ", \n");
			}
			sbSql.append("nTransDirectionID \n");
			
			if (condiInfo.getAscOrDesc() == Constant.PageControl.CODE_ASCORDESC_ASC){
				
				sbSql.append("asc");
			}
			else {
				sbSql.append("desc");
			}
			
			Log.print(sbSql.toString());
			Log.print("客户ID:"+condiInfo.getClientID());
			Log.print("报单号:"+condiInfo.getDeclarationNo());
			
			ps = conn.prepareStatement(sbSql.toString());
			int index = 0;
			if (condiInfo.getClientID()>0){
				ps.setLong(++index,condiInfo.getClientID());
			}
			if (condiInfo.getDeclarationNo()!=null && condiInfo.getDeclarationNo().length()>0){
				ps.setString(++index,condiInfo.getDeclarationNo());
			}
			if (condiInfo.getTransNo()!=null && condiInfo.getTransNo().length()>0){
				ps.setString(++index,condiInfo.getTransNo());
			}
			ps.setLong(++index,condiInfo.getStatusID());
			ps.setLong(++index,SETTConstant.TransactionType.MULTILOANRECEIVE);
			
			rs = ps.executeQuery();
			
			while(rs.next()){
				TransRepaymentLoanInfo info = resultToInfo(rs);
				lList.add(info);
			}
			this.cleanup(rs);
			this.cleanup(ps);
			this.cleanup(conn);
		}catch(SQLException e){throw new IException("查询勾账记录条件错误,无法查询对应记录",e);}
		 finally{
		 	try{
		 	    this.cleanup(rs);
				this.cleanup(ps);
				this.cleanup(conn);
		 	}catch (SQLException e){throw new IException("查询勾账记录时出现错误",e);}
		 }
		return lList;
	}
	
	/**
	 * 再贷款收回复核时核对子账户取得当前交易余额,并写入余额字段
	 * @param id
	 * @throws Exception
	 */
	public void updateCurrentBalance(long id,long subAccountId)throws Exception{
		Connection conn = null;
		PreparedStatement ps = null;
		try{
			conn = this.getConnection();
			double dBalance = 0.0;
			double dUncheckAmount = 0.0;
			double dCurrentBalance = 0.0;
			String strSQL ="update Sett_TransRepaymentLoan set MCURRENTBALANCE = " +
					"(select (MBALANCE-MUNCHECKPAYMENTAMOUNT) from " +
					"(select MBALANCE,MUNCHECKPAYMENTAMOUNT from Sett_SubAccount " +
					"where id="+subAccountId+")) where id="+id;
			Log.print(strSQL);
			ps = conn.prepareStatement(strSQL);
			ps.executeUpdate();
			this.cleanup(ps);
			this.cleanup(conn);
		}finally{
		    this.cleanup(ps);
			this.cleanup(conn);
		}
	}
	/**
	 * 修改贷款收回记录
	 * @param info TransRepaymentLoanInfo
	 * @return id
	 * @throws SQLException
	 */
	public long update(TransRepaymentLoanInfo info) throws SQLException
	{
		long returnValue = -1;
		Connection conn = null;
		int index = 1;
		PreparedStatement pstmt = null;
		try
		{
			conn = this.getConnection();
			StringBuffer sbSQL = new StringBuffer(128);
			sbSQL.append("update SETT_TRANSREPAYMENTLOAN set \n");
			sbSQL.append("NOFFICEID=?, NCURRENCYID=?, STRANSNO=?, NTRANSACTIONTYPEID=?, \n");
			sbSQL.append("NFREEFORMID=?, NISCANCLELOAN=?, NCLIENTID=?, NDEPOSITACCOUNTID=?, SCONSIGNVOUCHERNO=?, \n");
			sbSQL.append("SCONSIGNPASSWORD=?, SBILLNO=?, NBILLTYPEID=?, NBILLBANKID=?, NBANKID=?, \n");
			sbSQL.append("SEXTBANKNO=?, NCASHFLOWID=?, NLOANACCOUNTID=?, NLOANCONTRACTID=?, NLOANNOTEID=?, \n");
			sbSQL.append("NPREFORMID=?, MAMOUNT=?, DTINTERESTSTART=?, NABSTRACTID=?, SABSTRACT=?, \n");
			sbSQL.append("NCONSIGNACCOUNTID=?, NCONSIGNDEPOSITACCOUNTID=?, NPAYINTERESTACCOUNTID=?, NINTERESTBANKID=?, NINTERESTCASHFLOWID=?, \n");
			sbSQL.append("NRECEIVEINTERESTACCOUNTID=?, NPAYSUERTYFEEACCOUNTID=?, NSUERTYFEEBANKID=?, NSUERTYFEECASHFLOWID=?, NRECEIVESUERTYFEEACCOUNTID=?, \n");
			sbSQL.append("NCOMMISSIONACCOUNTID=?, NCOMMISSIONBANKID=?, NCOMMISSIONCASHFLOWID=?, MINTEREST=?, MINTERESTRECEIVABLE=?, \n");
			sbSQL.append("MINTERESTINCOME=?, MINTERESTTAX=?, MCOMPOUNDINTEREST=?, MOVERDUEINTEREST=?, MSURETYFEE=?, \n");
			sbSQL.append("MCOMMISSION=?, SADJUSTINTERESTREASON=?, MADJUSTINTEREST=?, MAHEADRECEIVEINTEREST=?, NISREMITINTEREST=?, \n");
			sbSQL.append("NISREMITCOMPOUNDINTEREST=?, NISREMITOVERDUEINTEREST=?, NISREMITSURETYFEE=?, NISREMITCOMMISSION=?, NCAPITALANDINTERESTDEALWAY=?, \n");
			sbSQL.append("MREALINTEREST=?, MREALINTERESTRECEIVABLE=?, MREALINTERESTINCOME=?, MREALINTERESTTAX=?, MREALCOMPOUNDINTEREST=?, \n");
			sbSQL.append("MREALOVERDUEINTEREST=?, MREALSURETYFEE=?, MREALCOMMISSION=?, DTINPUT=?, NINPUTUSERID=?, \n");
			sbSQL.append("NCHECKUSERID=?, SCHECKABSTRACT=?, NSTATUSID=?, DTMODIFY=?,DTEXECUTE=?,DTINTERESTCLEAR=?,NSUBACCOUNTID=?, \n");
			sbSQL.append("MCURRENTBALANCE=?,dtLatestInterestClear=?,nTransDirectionID=?,sDeclarationNo=?,sTempTransNO=?, \n");
			sbSQL.append("dtCompoundInterestStart=?,mCompoundAmount=?,mCompoundRate=?,dtOverDueStart=?,mOverDueAmount=?,mOverDueRate=?, \n");
			sbSQL.append("mLoanRepaymentRate=?,dtSuretyFeeStart=?,mSuretyFeeRate=?,dtCommissionStart=?,mCommissionRate=?,nSerialNo=?,SINSTRUCTIONNO=? \n");
			sbSQL.append("where ID=?\n");
			pstmt = conn.prepareStatement(sbSQL.toString());
			/**
			 * 为打印添加的第二组参数,另外添加多笔贷款收回序列号
			 * mLoanRepaymentRate,dtSuretyFeeStart,mSuretyFeeRate
			 * dtCommissionStart,mCommissionRate,nSerialNo
			 */
			////1-----10
			pstmt.setLong(index++, info.getOfficeID());
			pstmt.setLong(index++, info.getCurrencyID());
			pstmt.setString(index++, info.getTransNo());
			pstmt.setLong(index++, info.getTransactionTypeID());
			pstmt.setLong(index++, info.getFreeFormID());
			pstmt.setLong(index++, info.getIsCancelLoan());
			pstmt.setLong(index++, info.getClientID());
			pstmt.setLong(index++, info.getDepositAccountID());
			pstmt.setString(index++, info.getConsignPayVoucherNo());
			///11-----20
			pstmt.setString(index++, info.getConsignPayPWD());
			pstmt.setString(index++, info.getBillNo());
			pstmt.setLong(index++, info.getBillTypeID());
			pstmt.setLong(index++, info.getBillBankID());
			pstmt.setLong(index++, info.getBankID());
			pstmt.setString(index++, info.getExtBankNo());
			pstmt.setLong(index++, info.getCashFlowID());
			pstmt.setLong(index++, info.getLoanAccountID());
			pstmt.setLong(index++, info.getLoanContractID());
			pstmt.setLong(index++, info.getLoanNoteID());
			////21-----30
			pstmt.setLong(index++, info.getPreFormID());
			pstmt.setDouble(index++, info.getAmount());
			pstmt.setTimestamp(index++, info.getInterestStart());
			pstmt.setLong(index++, info.getAbstractID());
			pstmt.setString(index++, info.getAbstract());
			pstmt.setLong(index++, info.getConsignAccountID());
			pstmt.setLong(index++, info.getConsignDepositAccountID());
			pstmt.setLong(index++, info.getPayInterestAccountID());
			pstmt.setLong(index++, info.getInterestBankID());
			pstmt.setLong(index++, info.getInterestCashFlowID());
			////31-----40
			pstmt.setLong(index++, info.getReceiveInterestAccountID());
			pstmt.setLong(index++, info.getPaySuretyAccountID());
			pstmt.setLong(index++, info.getSuretyBankID());
			pstmt.setLong(index++, info.getSuretyCashFlowID());
			pstmt.setLong(index++, info.getReceiveSuretyAccountID());
			pstmt.setLong(index++, info.getCommissionAccountID());
			pstmt.setLong(index++, info.getCommissionBankID());
			pstmt.setLong(index++, info.getCommissionCashFlowID());
			pstmt.setDouble(index++, info.getInterest());
			pstmt.setDouble(index++, info.getInterestReceiveAble());
			////41-----50
			pstmt.setDouble(index++, info.getInterestIncome());
			pstmt.setDouble(index++, info.getInterestTax());
			pstmt.setDouble(index++, info.getCompoundInterest());
			pstmt.setDouble(index++, info.getOverDueInterest());
			pstmt.setDouble(index++, info.getSuretyFee());
			pstmt.setDouble(index++, info.getCommission());
			pstmt.setString(index++, info.getAdjustInterestReason());
			pstmt.setDouble(index++, info.getAdjustInterest());
			pstmt.setDouble(index++, info.getAheadRepayInterest());
			pstmt.setDouble(index++, info.getIsRemitInterest());
			////51-----60
			pstmt.setLong(index++, info.getIsRemitCompoundInterest());
			pstmt.setLong(index++, info.getIsRemitOverDueInterest());
			pstmt.setLong(index++, info.getIsRemitSuretyFee());
			pstmt.setLong(index++, info.getIsRemitCommission());
			pstmt.setLong(index++, info.getCapitalAndInterstDealway());
			pstmt.setDouble(index++, info.getRealInterest());
			pstmt.setDouble(index++, info.getRealInterestReceiveAble());
			pstmt.setDouble(index++, info.getRealInterestIncome());
			pstmt.setDouble(index++, info.getRealInterestTax());
			pstmt.setDouble(index++, info.getRealCompoundInterest());
			////61-----70
			pstmt.setDouble(index++, info.getRealOverDueInterest());
			pstmt.setDouble(index++, info.getRealSuretyFee());
			pstmt.setDouble(index++, info.getRealCommission());
			pstmt.setTimestamp(index++, info.getInput());
			pstmt.setLong(index++, info.getInputUserID());
			pstmt.setLong(index++, info.getCheckUserID());
			pstmt.setString(index++, info.getCheckAbstract());
			pstmt.setLong(index++, info.getStatusID());
			pstmt.setTimestamp(index++, info.getModify());
			pstmt.setTimestamp(index++, info.getExecute());
			////71-----80
			pstmt.setTimestamp(index++, info.getInterestClear());
			pstmt.setLong(index++, info.getSubAccountID()); 			//子账户ID
			pstmt.setDouble(index++,info.getCurrentBalance());			//余额
			pstmt.setTimestamp(index++,info.getLatestInterestClear());  //上次结息日
			pstmt.setLong(index++,info.getTransDirectionID());			//交易方向
			pstmt.setString(index++,info.getDeclarationNo());			//报单号
			pstmt.setString(index++,info.getTempTransNO());				//临时交易号
			pstmt.setTimestamp(index++,info.getCompoundInterestStart());
			pstmt.setDouble(index++,info.getCompoundAmount());
			pstmt.setDouble(index++,info.getCompoundRate());
			////81-----90
			pstmt.setTimestamp(index++,info.getOverDueStart());
			pstmt.setDouble(index++,info.getOverDueAmount());
			pstmt.setDouble(index++,info.getOverDueRate());
			pstmt.setDouble(index++,info.getLoanRepaymentRate());		//还款利率
			pstmt.setTimestamp(index++,info.getSuretyFeeStart());		//担保费起息日
			pstmt.setDouble(index++,info.getSuretyFeeRate());			//手续费率
			pstmt.setTimestamp(index++,info.getCommissionStart());		//手续费起息日
			pstmt.setDouble(index++,info.getCommissionRate());			//手续费率
			pstmt.setLong(index++,info.getSerialNo());					//多笔贷款收回序列号
			pstmt.setString(index++,info.getInstructionNo());			//为网银添加
			/**
			 * 为打印添加的第二组参数,另外添加多笔贷款收回序列号
			 * mLoanRepaymentRate,dtSuretyFeeStart,mSuretyFeeRate
			 * dtCommissionStart,mCommissionRate,nSerialNo
			 */
			////where id = ? 
			pstmt.setLong(index++, info.getID());
			log.debug(sbSQL.toString());
			pstmt.execute();
			this.cleanup(pstmt);
			this.cleanup(conn);
		}
		finally
		{
		    this.cleanup(pstmt);
			this.cleanup(conn);
		}
		return returnValue;
	}

	/**
	 * 通过交易号查找交易ID 
	 * @param id ID
	 * @return 贷款发放记录
	 * @throws SQLException
	 */
	public long getIDByTransNo(String strTransNo) throws SQLException
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		long lID = -1;
		try
		{
			conn = this.getConnection();
			StringBuffer sbSQL = new StringBuffer(128);
			sbSQL.append("SELECT ID from SETT_TRANSREPAYMENTLOAN where sTransNo=? ");
			pstmt = conn.prepareStatement(sbSQL.toString());
			pstmt.setString(1, strTransNo);
			rset = pstmt.executeQuery();
			if (rset != null && rset.next())
			{
				lID = rset.getLong("ID");
			}
			this.cleanup(rset);
			this.cleanup(pstmt);
			this.cleanup(conn);
		}
		finally
		{
			this.cleanup(rset);
			this.cleanup(pstmt);
			this.cleanup(conn);
		}
		return lID;
	}	
	
	/**
	 * 通过交易号查找交易ID 
	 * @param id ID
	 * @return 贷款发放记录
	 * @throws SQLException
	 */
	public long getIDByTransNoAndSerialNo(String strTransNo,long lSerialNo) throws SQLException
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		long lID = -1;
		try
		{
			conn = this.getConnection();
			StringBuffer sbSQL = new StringBuffer(128);
			sbSQL.append("SELECT ID from SETT_TRANSREPAYMENTLOAN where (sTransNo=? \n");
			sbSQL.append("and nSerialNo=?) or sTempTransNo=? ");
			pstmt = conn.prepareStatement(sbSQL.toString());
			pstmt.setString(1, strTransNo);
			pstmt.setLong(2,lSerialNo);
			pstmt.setString(3, strTransNo);
			rset = pstmt.executeQuery();
			if (rset != null && rset.next())
			{
				lID = rset.getLong("ID");
			}
			this.cleanup(rset);
			this.cleanup(pstmt);
			this.cleanup(conn);
		}
		finally
		{
			this.cleanup(rset);
			this.cleanup(pstmt);
			this.cleanup(conn);
		}
		return lID;
	}	
	
	public Collection getRepaymentCollectionByTransNo(String strTransNo)throws IException{
		LinkedList lList 		= new LinkedList();
		Connection conn 		= null;
		PreparedStatement ps 	= null;
		ResultSet rs 			= null;
		try{
			conn = this.getConnection();
			StringBuffer sbSql = new StringBuffer(128);
			sbSql.append("select " + sbRepaymentSQL.toString() + "from Sett_TransRepaymentLoan \n");
			sbSql.append("where sTransNo='" + strTransNo + "'");
			
			Log.print(sbSql.toString());
			ps = conn.prepareStatement(sbSql.toString());
			rs = ps.executeQuery();
			
			while(rs.next()){
				TransRepaymentLoanInfo info = new TransRepaymentLoanInfo();
				info = resultToInfo(rs);
				lList.add(info);
			}
			this.cleanup(rs);
			this.cleanup(ps);
			this.cleanup(conn);
			
		}catch(SQLException e){throw new IException("查询多笔贷款收回结果集出错"+e.getMessage());}
		finally{
			try{
				this.cleanup(rs);
				this.cleanup(ps);
				this.cleanup(conn);
			}catch(SQLException e){
				throw new IException("查询多笔贷款收回结果集出错"+e.getMessage());
			}
		}
		return lList;
	}
	
	/**
		 * 通过id查找贷款收回记录 
		 * @param id ID
		 * @return 贷款收回记录
		 * @throws SQLException
		 */
	public TransRepaymentLoanInfo findByID(long id) throws SQLException
	{
		TransRepaymentLoanInfo info = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try
		{
			conn = this.getConnection();
			StringBuffer sbSQL = new StringBuffer(128);
			sbSQL.append("SELECT \n");
			sbSQL.append(sbRepaymentSQL.toString());
			sbSQL.append("FROM SETT_TRANSREPAYMENTLOAN ");
			sbSQL.append(" where id=? ");
			pstmt = conn.prepareStatement(sbSQL.toString());
			pstmt.setLong(1, id);
			rset = pstmt.executeQuery();
			if (rset != null && rset.next())
			{
				info = resultToInfo(rset);
				Log.print("还款金额:" + info.getAmount());
				Log.print("还款客户:" + info.getClientID());
				Log.print("贷款账户号:" + info.getLoanAccountID());
				Log.print("放款通知单:" + info.getLoanNoteID());
			}
			this.cleanup(rset);
			this.cleanup(pstmt);
			this.cleanup(conn);
		}
		finally
		{
		    this.cleanup(rset);
			this.cleanup(pstmt);
			this.cleanup(conn);
		}
		return info;
	}
	/**
			 * 根据条件查找贷款收回记录
			 * @param info TransRepaymentLoanInfo
			 * @param orderType long
			 * @param isDesc boolean
			 * @return Collection
			 * @throws Exception
			 */
	public Collection findByCondition(TransRepaymentLoanInfo info) throws Exception
	{
		List result = new ArrayList();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try
		{
			Log.print("进入findByCondition............");
			conn = this.getConnection();
			StringBuffer sbSQL = new StringBuffer(128); //基本查询语句

			sbSQL.append("SELECT \n");
			sbSQL.append(sbRepaymentSQL.toString());
			sbSQL.append("FROM SETT_TRANSREPAYMENTLOAN ");
			sbSQL.append(" where nOfficeID = " + info.getOfficeID());
			sbSQL.append(" and nCurrencyID = " + info.getCurrencyID());
			sbSQL.append(" and nTransactionTypeID = " + info.getTransactionTypeID() + " \n");
			if (info.getInputUserID() > 0) //录入人
			{
				sbSQL.append(" and nInputUserID =" + info.getInputUserID() + " \n");
			}
			if (info.getCheckUserID() > -1) //复合人
			{
				sbSQL.append(" and nCheckUserID =" + info.getCheckUserID() + " \n");
			}
			
			if (info.getTransDirectionID() > 0){ //交易方向
				sbSQL.append(" and nTransDirectionID =" + info.getTransDirectionID() + " \n");
			}
			
			long[] statusIDs = info.getStatusIDs();

			Log.print("状态数组:" + statusIDs);
			for (int i = 0; i < statusIDs.length; i++)
			{
				Log.print("statusID" + i + ":" + statusIDs[i]);
			}

			if (statusIDs != null)
			{
				boolean isStart = true;
				for (int i = 0; i < statusIDs.length; i++)
				{
					Log.print("status:" + statusIDs[i]);
					if (statusIDs[i] == SETTConstant.TransactionStatus.TEMPSAVE)
					{ //暂存没有时间限制
						if (isStart)
						{
							sbSQL.append("and (");
							isStart = !isStart;
						}
						else
						{
							sbSQL.append("or ");
						}
						sbSQL.append("(nStatusID = " + SETTConstant.TransactionStatus.TEMPSAVE + ") ");
					}
					else
						if (statusIDs[i] == SETTConstant.TransactionStatus.SAVE)
						{ //保存要查当天的
							if (isStart)
							{
								sbSQL.append("and (");
								isStart = !isStart;
							}
							else
							{
								sbSQL.append("or ");
							}
							sbSQL.append("(nStatusID = " + SETTConstant.TransactionStatus.SAVE);
							sbSQL.append(" and dtExecute=?) ");
						}
						else
							if (statusIDs[i] == SETTConstant.TransactionStatus.CHECK)
							{ //已经复核的要查当天
								if (isStart)
								{
									sbSQL.append("and (");
									isStart = !isStart;
								}
								else
								{
									sbSQL.append("or ");
								}
								sbSQL.append("(nStatusID = " + SETTConstant.TransactionStatus.CHECK);
								sbSQL.append(" and dtExecute=?) ");
							}
							else
							{
								sbSQL.append("and ");
								sbSQL.append("nStatusID = " + statusIDs[i]); //空白的时候
							}
				}
				if (!isStart)
					sbSQL.append(") ");
			}
			else
			{ //如果没有statusID，默认查全部有效记录
				sbSQL.append(" and (nStatusID = " + SETTConstant.TransactionStatus.TEMPSAVE + ") ");
				sbSQL.append(" or (nStatusID = " + SETTConstant.TransactionStatus.SAVE);
				sbSQL.append(" and dtExecute=?) ");
			}
			if (info.getOrderByType() > 0)
			{
				sbSQL.append(" order by " + getOrderColumnName((int) info.getOrderByType()));
				if (info.getAscOrDesc() == Constant.PageControl.CODE_ASCORDESC_ASC)
				{
					sbSQL.append(" asc ");
				}
				else
				{
					sbSQL.append(" desc ");
				}
			}
			Log.print(sbSQL.toString());
			int intIndex = 0;
			pstmt = conn.prepareStatement(sbSQL.toString());
			if (statusIDs != null)
			{ //添加执行时间
				for (int i = 0; i < statusIDs.length; i++)
				{ //如果有存储的
					if (statusIDs[i] == SETTConstant.TransactionStatus.SAVE)
					{
						pstmt.setTimestamp(++intIndex, info.getExecute());
					}
					else
						if (statusIDs[i] == SETTConstant.TransactionStatus.CHECK)
						{
							pstmt.setTimestamp(++intIndex, info.getExecute());
						}
				}
			}
			else
			{ //查全部
				pstmt.setTimestamp(++intIndex, info.getExecute());
			}
			//暂时屏蔽执行时间
			//pstmt.setTimestamp(1, info.getExecute());
			Log.print("execute date:" + info.getExecute().toString());
			rset = pstmt.executeQuery();
			while (rset.next())
			{
				result.add(resultToInfo(rset));
			}
			this.cleanup(rset);
			this.cleanup(pstmt);
			this.cleanup(conn);
		}
		finally
		{
		    this.cleanup(rset);
			this.cleanup(pstmt);
			this.cleanup(conn);
		}
		return result;
	}
	/**
	 * 得到贷款条件信息
	 * @param info
	 * @return LoanConditionInfo
	 * @throws Exception
	 */
	public SubLoanAccountDetailInfo findSubLoanAccountDetailByCondition(SubLoanAccountDetailInfo info) throws Exception
	{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			Log.print("进入SubLoanAccountDetailInfo................");
			conn = this.getConnection();
			StringBuffer sbSql = new StringBuffer(128);
			long lSubAccount = -1; //子账户ID
			//根据放款通知单得到子账户ID; 
			//AL_nLoanNoteID 放款通知单 sett_SubAccount子账户表
			sbSql.append("select ID from sett_SubAccount ");
			sbSql.append("where AL_nLoanNoteID=");
			sbSql.append(info.getPayFormID() + " \n");
			
			if (info.isTempSave() == false)
			{
				sbSql.append("and nStatusId=1");
			}

			Log.print(sbSql.toString());
			ps = conn.prepareStatement(sbSql.toString());
			rs = ps.executeQuery();

			if (rs.next())
			{
				lSubAccount = rs.getLong(1);
				Log.print("sub account id:" + lSubAccount);
			}
			else
			{
				throw new IException("未找到放款通知单对应的子账户");
			}
			cleanup(rs);
			cleanup(ps);
			
			StringBuffer sbLoanTypeSql = new StringBuffer(128);
			long lLoanType = -1; //贷款类型
			
			sbLoanTypeSql.append("select loan_contractForm.nTypeID from loan_payForm,loan_contractForm \n" );
			sbLoanTypeSql.append(" where loan_contractForm.id=loan_payForm.nContractID \n");
			sbLoanTypeSql.append(" and loan_payForm.id="+ info.getPayFormID() + " \n");

			Log.print(sbLoanTypeSql.toString());
			ps = conn.prepareStatement(sbLoanTypeSql.toString());
			rs = ps.executeQuery();

			if (rs.next())
			{
				lLoanType = rs.getLong(1);
				Log.print("loanType:" + lLoanType);
			}			
			cleanup(rs);
			cleanup(ps);		
			
			if (lSubAccount > 0)
			{ //查到合法的子账户
				StringBuffer sbSubAccountSql = new StringBuffer(128);

				sbSubAccountSql.append("select MINTEREST,dtClearInterest,(mBalance-MUNCHECKPAYMENTAMOUNT) as mBalance, AL_NLOANNOTEID,Al_nConsignAccountID, \n");
				sbSubAccountSql.append("AL_NISCYCLOAN, AL_DTCALCULATEINTEREST, \n");
				sbSubAccountSql.append("AL_NPAYINTERESTACCOUNTID, AL_NRECEIVEINTERESTACCOUNTID, \n");
				sbSubAccountSql.append("AL_MPREDRAWINTEREST, AL_DTPREDRAW, \n");
				sbSubAccountSql.append("AL_nPaySuretyAccountID , AL_nReceiveSuretyAccountID , AL_NCOMMISSIONACCOUNTID, \n");
				sbSubAccountSql.append("AL_MSURETYFEE, AL_DTCLEARSUREFEE, AL_MCOMMISSION, \n");
				sbSubAccountSql.append("AL_DTCLEARCOMMISSION, AL_NINTERESTTAXACCOUNTID, \n");
				sbSubAccountSql.append("AL_MINTERESTTAX, AL_MINTERESTTAXRATE, AL_DTCLEARINTERESTTAX, \n");
				sbSubAccountSql.append("AL_DTEFFECTIVETAX, AL_NOVERDUEACCOUNTID, \n");
				sbSubAccountSql.append("AL_MOVERDUEINTEREST, AL_DTCLEAROVERDUE, AL_NCOMPOUNDACCOUNTID, \n");
				sbSubAccountSql.append("AL_MCOMPOUNDINTEREST, AL_DTCLEARCOMPOUND, \n");
				sbSubAccountSql.append("AL_MARREARAGEINTEREST, AC_MNEGOTIATERATE, AC_DTNEGOTIATERATE ,AL_nInterestTaxRatePlanID \n");
				sbSubAccountSql.append("from sett_SubAccount \n");
				sbSubAccountSql.append("where ID=" + lSubAccount);

				ps = conn.prepareStatement(sbSubAccountSql.toString());
				rs = ps.executeQuery();
				Log.print(" 查询子账户SQL:" + sbSubAccountSql.toString());
				if (rs.next())
				{
					//---------取得需要的信息
					Log.print("info赋值");


					info.setSubAccountID(lSubAccount); //子账户ID
						
					/**
					 * 此处为判断子账户中所还金额是否还清
					 * 判断方式为 : 实际余额 = 子账户中余额 - 子账户未复核还款 + 原来保存的还款 - 当前需要保存的还款
					 * 其中原来保存的还款是用于修改保存,如果是新增保存此值应为0.0,不影响计算
					 * 如果余额大于零,那么交易可以正常进行,等于零,带出利息信息如果小于零,交易中止. 
					 */
					//余额
					info.setBalance(rs.getDouble("mBalance"));
					
					if(lLoanType==LOANConstant.LoanType.YT)
					{
						BankLoanQuery bankLoanQuery =new BankLoanQuery();
						//承贷比例
						
						double dRate = 0.0;
						dRate=bankLoanQuery.findRateByFormID(info.getPayFormID());					
						double dBanlance  = 0.0;
						dBanlance = bankLoanQuery.findBalanceByFormID(info.getPayFormID());	
						if(dRate>0)
						{
							info.setBalance(dBanlance);
						}
					}
					
					Log.print("---开始计算实际余额---");
							
					double dRealBalance = UtilOperation.Arith.sub(UtilOperation.Arith.add(info.getBalance(),info.getSavedAmount()),info.getAmount());
					Log.print("库里的余额:"+info.getBalance());
					Log.print("保存过的还款:"+info.getSavedAmount());
					Log.print("新还款:"+info.getAmount());
					Log.print("子账户：" + lSubAccount+"当前实际余额为:"+dRealBalance);
					Log.print("----计算结束----");
					
					//利息税费
					info.setInterestTaxRate(rs.getDouble("AL_MINTERESTTAXRATE"));
					
					//利息税费率计划
					info.setInterestTaxRatePlan(rs.getLong("AL_nInterestTaxRatePlanID"));
					
					info.setClearInterest(rs.getTimestamp("dtClearInterest"));
					Log.print("结息日期"+info.getClearInterest());
	
					//委托方存款账户号
					info.setConsignAccountID(rs.getLong("Al_nConsignAccountID"));
					Log.print("委托方存款账户号:" + info.getConsignAccountID());
					if (info.getOfficeID()>0 && (info.getTransactionType() == SETTConstant.TransactionType.INTERESTFEEPAYMENT || dRealBalance == 0.0))
					{
						info.setAccountClear(true);			//账户结清，开始算息
						//付息活期账号
						info.setPayInterestAccountID(rs.getLong("AL_nPayInterestAccountID"));
						
						//利息收入账户
						info.setReceiveInterestAccountID(rs.getLong("AL_nReceiveInterestAccountID"));
						
						//付担保费账号
						info.setPaySuretyFeeAccountID(rs.getLong("AL_nPaySuretyAccountID"));
						
						//担保费收入账户
						info.setReceiveSuretyFeeAccountID(rs.getLong("AL_nReceiveSuretyAccountID"));
						
						//手续费付出账户
						info.setCommissionAccountID(rs.getLong("AL_nCommissionAccountID"));
					}
					else {
						if (info.isTempSave() == false)
						{
							if (dRealBalance<0)
							{
								throw new IException("当前该子账户中未还款金额为:"+info.getBalance()+"," + "小于您输入的金额,请重新输入");
							}
						}
					}
				}
				else
				{
					throw new IException("未找到对应子账户");
				}

				StringBuffer sbSqlContractType = new StringBuffer();
				sbSqlContractType.append("select nTypeID from loan_contractform ");
				sbSqlContractType.append("where id=(select nContractID ");
				sbSqlContractType.append("from loan_payform where id=?)");

				this.cleanup(rs);
				this.cleanup(ps);
				ps = conn.prepareStatement(sbSqlContractType.toString());
				ps.setLong(1, info.getPayFormID());
				rs = ps.executeQuery();
				if (rs.next())
				{
					info.setContractType(rs.getLong(1));
				}
				this.cleanup(rs);
				this.cleanup(ps);
				//---------取得需要的信息*/
			}
			else
			{
				//throw new IException("subAccount is invalid");
			} 
			this.cleanup(conn);
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
		 * 根据条件查找贷款收回记录
		 * @param info TransRepaymentLoanInfo
		 * @param orderType long
		 * @param isDesc boolean
		 * @return Collection
		 * @throws Exception
		 */
	public Collection match(DAOHelper condition) throws Exception
	{
		List result = new ArrayList();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try
		{
			conn = this.getConnection();
			StringBuffer sbSQL = new StringBuffer(128);
			sbSQL.append("SELECT \n");
			sbSQL.append(sbRepaymentSQL.toString());
			sbSQL.append("FROM SETT_TRANSREPAYMENTLOAN");
			condition.setSqlString(sbSQL.toString());
			condition.setOrderColumnName(getOrderColumnName(condition.getOrderType()));
			pstmt = DAOHelper.buildPreparedStatemnet(conn, condition);
			rset = pstmt.executeQuery();
			while (rset.next())
			{
				result.add(resultToInfo(rset));
			}
			this.cleanup(rset);
			this.cleanup(pstmt);
			this.cleanup(conn);
		}
		finally
		{
		    this.cleanup(rset);
			this.cleanup(pstmt);
			this.cleanup(conn);
		}
		return result;
	}
	/**
		 * 将ResultSet转换为TransRepaymentLoanInfo 
		 * @param rset ResultSet
		 * @return TransRepaymentLoanInfo
		 * @throws SQLException
		 */
	private static TransRepaymentLoanInfo resultToInfo(ResultSet rset) throws SQLException
	{
		
		TransRepaymentLoanInfo info = new TransRepaymentLoanInfo();
		//		//1-----10
		info.setID(rset.getLong(1));
		info.setOfficeID(rset.getLong(2));
		info.setCurrencyID(rset.getLong(3));
		info.setTransNo(rset.getString(4));
		info.setTransactionTypeID(rset.getLong(5));
		info.setFreeFormID(rset.getLong(6));
		info.setIsCancelLoan(rset.getLong(6));
		info.setClientID(rset.getLong(8));
		info.setDepositAccountID(rset.getLong(9));
		info.setConsignPayVoucherNo(rset.getString(10));
		///11-----20
		info.setConsignPayPWD(rset.getString(11));
		info.setBillNo(rset.getString(12));
		info.setBillTypeID(rset.getLong(13));
		info.setBillBankID(rset.getLong(14));
		info.setBankID(rset.getLong(15));
		info.setExtBankNo(rset.getString(16));
		info.setCashFlowID(rset.getLong(17));
		info.setLoanAccountID(rset.getLong(18));
		info.setLoanContractID(rset.getLong(19));
		info.setLoanNoteID(rset.getLong(20));
		////21-----30
		info.setPreFormID(rset.getLong(21));
		info.setAmount(rset.getDouble(22));
		info.setInterestStart(rset.getTimestamp(23));
		info.setAbstractID(rset.getLong(24));
		info.setAbstract(rset.getString(25));
		info.setConsignAccountID(rset.getLong(26));
		info.setConsignDepositAccountID(rset.getLong(27));
		info.setPayInterestAccountID(rset.getLong(28));
		info.setInterestBankID(rset.getLong(29));
		info.setInterestCashFlowID(rset.getLong(30));
		////31-----40
		info.setReceiveInterestAccountID(rset.getLong(31));
		info.setPaySuretyAccountID(rset.getLong(32));
		info.setSuretyBankID(rset.getLong(33));
		info.setSuretyCashFlowID(34);
		info.setReceiveSuretyAccountID(rset.getLong(35));
		info.setCommissionAccountID(rset.getLong(36));
		info.setCommissionBankID(rset.getLong(37));
		info.setCommissionCashFlowID(rset.getLong(38));
		info.setInterest(rset.getDouble(39));
		info.setInterestReceiveAble(rset.getDouble(40));
		////41-----50
		info.setInterestIncome(rset.getDouble(41));
		info.setInterestTax(rset.getDouble(42));
		info.setCompoundInterest(rset.getDouble(43));
		info.setOverDueInterest(rset.getDouble(44));
		info.setSuretyFee(rset.getDouble(45));
		info.setCommission(rset.getDouble(46));
		info.setAdjustInterestReason(rset.getString(47));
		info.setAdjustInterest(rset.getDouble(48));
		info.setAheadRepayInterest(rset.getDouble(49));
		info.setIsRemitInterest(rset.getLong(50));
		////51-----60
		info.setIsRemitCompoundInterest(rset.getLong(51));
		info.setIsRemitOverDueInterest(rset.getLong(52));
		info.setIsRemitSuretyFee(rset.getLong(53));
		info.setIsRemitCommission(rset.getLong(54));
		info.setCapitalAndInterstDealway(rset.getLong(55));
		info.setRealInterest(rset.getDouble(56));
		info.setRealInterestReceiveAble(rset.getDouble(57));
		info.setRealInterestIncome(rset.getDouble(58));
		info.setRealInterestTax(rset.getDouble(59));
		info.setRealCompoundInterest(rset.getDouble(60));
		////61-----70
		info.setRealOverDueInterest(rset.getDouble(61));
		info.setRealSuretyFee(rset.getDouble(62));
		info.setRealCommission(rset.getDouble(63));
		info.setInput(rset.getTimestamp(64));
		info.setInputUserID(rset.getLong(65));
		info.setCheckUserID(rset.getLong(66));
		info.setCheckAbstract(rset.getString(67));
		info.setStatusID(rset.getLong(68));
		info.setModify(rset.getTimestamp(69));
		info.setExecute(rset.getTimestamp(70));
		////71-----80
		info.setInterestClear(rset.getTimestamp(71));
		info.setSubAccountID(rset.getLong(72));
		info.setCurrentBalance(rset.getDouble(73));
		info.setLatestInterestClear(rset.getTimestamp(74));		//上次结息日
		/**
		 * 多笔贷款收回
		 */
		info.setTransDirectionID(rset.getLong(75));				//交易方向
		info.setDeclarationNo(rset.getString(76));				//报单号
		info.setTempTransNO(rset.getString(77));				//临时交易号
		
		/**
		 * 为打印添加的参数
		 */
		info.setCompoundInterestStart(rset.getTimestamp(78));
		info.setCompoundAmount(rset.getDouble(79));
		info.setCompoundRate(rset.getDouble(80));
		////81-----90
		info.setOverDueStart(rset.getTimestamp(81));
		info.setOverDueAmount(rset.getDouble(82));
		info.setOverDueRate(rset.getDouble(83));
		/**
		 * 为打印添加的第二组参数,另外添加多笔贷款收回序列号
		 * mLoanRepaymentRate,dtSuretyFeeStart,mSuretyFeeRate
		 * dtCommissionStart,mCommissionRate,nSerialNo
		 */
		info.setLoanRepaymentRate(rset.getDouble(84));
		info.setSuretyFeeStart(rset.getTimestamp(85));
		info.setSuretyFeeRate(rset.getDouble(86));
		info.setCommissionStart(rset.getTimestamp(87));
		info.setCommissionRate(rset.getDouble(88));
		info.setSerialNo(rset.getLong(89));
		info.setInstructionNo(rset.getString(90));
		
		return info;
	}
	/**
	 * 得到下一个ID
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	private static long getNextID(Connection conn) throws SQLException
	{
		long id = -1;
		String sqlString = "select SEQ_TRANSREPAYMENTLOANID.nextval from dual";
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try
		{
			pstmt = conn.prepareStatement(sqlString);
			rset = pstmt.executeQuery();
			if (rset.next())
			{
				id = rset.getLong(1);
			}
			if (rset!=null) rset.close();
			if (pstmt!=null) pstmt.close();
		}
		catch(Exception e)
		{
		    e.printStackTrace();
		    if (rset!=null) 
		        {
		        	rset.close();
		        	rset = null;
		        }
			if (pstmt!=null) 
			    {
			    	pstmt.close();
			    	pstmt = null;
			    }
		    
		}
		finally
		{
		    if (rset!=null) 
	        {
	        	rset.close();
	        	rset = null;
	        }
		    if (pstmt!=null) 
		    {
		    	pstmt.close();
		    	pstmt = null;
		    }
		}
		return id;
	}
	/**
			 * 将次序类型转换为列名
			 * @param orderType
			 * @return
			 */
	private static String getOrderColumnName(int orderType)
	{
		String returnValue = "";
		switch (orderType)
		{
			case ORDER_TRANS_NO :
				returnValue = ORDER_TRANS_NO_NAME;
				break;
			case ORDER_TRANSACTION_TYPE_ID :
				returnValue = ORDER_TRANSACTION_TYPE_ID_NAME;
				break;
			case ORDER_LOAN_ACCOUNT_ID :
				returnValue = ORDER_LOAN_ACCOUNT_ID_NAME;
				break;
			case ORDER_LOAN_CONTRACT_ID :
				returnValue = ORDER_LOAN_CONTRACT_ID_NAME;
				break;
			case ORDER_LOAN_NOTE_ID :
				returnValue = ORDER_LOAN_NOTE_ID_NAME;
				break;
			case ORDER_DEPOSIT_ACCOUNT_ID :
				returnValue = ORDER_DEPOSIT_ACCOUNT_ID_NAME;
				break;
			case ORDER_AMOUNT :
				returnValue = ORDER_AMOUNT_NAME;
				break;
			case ORDER_INTEREST_START :
				returnValue = ORDER_INTEREST_START_NAME;
				break;
			case ORDER_ABSTRACT :
				returnValue = ORDER_ABSTRACT_NAME;
				break;
			case ORDER_STATUS :
				returnValue = ORDER_STATUS_NAME;
				break;
			case ORDER_PAYINTEREST_ACCOUNT :
				returnValue = ORDER_PAYINTEREST_ACCOUNT_NAME;
				break;
			case ORDER_TEMPTRANS_NO :
				returnValue = ORDER_TEMPTRANS_NO_NAME;
				break;
		}
		return returnValue;
	}
	/**
	 * modify by zcwang 2007-6-1 借用大唐修改（有一笔贷款在本金已经还清，做还款业务时对利息做了免还处理，但季度结息时仍然能够结算出当时做了免还的利息金额）
	 * @param subAccountId
	 * @throws Exception
	 */
	public void updateCurrentBalance2(long subAccountId)throws Exception{
		Connection conn = null;
		PreparedStatement ps = null;
		try{
			conn = this.getConnection();
			String strSQL =" update sett_subaccount set minterest = 0.0 where id = "+subAccountId;
			Log.print(strSQL);
			ps = conn.prepareStatement(strSQL);
			ps.executeUpdate();
			this.cleanup(ps);
			this.cleanup(conn);
		}finally{
		    this.cleanup(ps);
			this.cleanup(conn);
		}
	}
	
	/**
	 * Modify by leiyang 2007-6-14 用于修改贷款帐户的子帐户
	 * 
	 * @param subAccountLoanInfo
	 * @throws IException 
	 * @throws SQLException 
	 */
	public void updateSubAccountLoanInfo(SubAccountLoanInfo subAccountLoanInfo) throws IException, SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = this.getConnection();
			StringBuffer strSQL = new StringBuffer();
			strSQL.append("update sett_subaccount ");
			strSQL.append("set MInterest = ?,");
			strSQL.append("AL_MCompoundInterest = ?,");
			strSQL.append("AL_MOverDueInterest = ?,");
			strSQL.append("AL_MSuretyFee = ?,");
			strSQL.append("AL_MCommission = ?,");
			strSQL.append("AL_MInterestTax = ?, ");
			strSQL.append("AL_MPredrawInterest = ? ");  //计提利息
			strSQL.append("where ID = ? ");
			
			ps = conn.prepareStatement(strSQL.toString());
			ps.setDouble(1, subAccountLoanInfo.getInterest());
			ps.setDouble(2, subAccountLoanInfo.getCompoundInterest());
			ps.setDouble(3, subAccountLoanInfo.getOverDueInterest());
			ps.setDouble(4, subAccountLoanInfo.getSuretyFee());
			ps.setDouble(5, subAccountLoanInfo.getCommission());
			ps.setDouble(6, subAccountLoanInfo.getInterestTax());
			ps.setDouble(7, subAccountLoanInfo.getPreDrawInterest());  //计提利息
			ps.setLong(8, subAccountLoanInfo.getID());

			ps.executeUpdate();
			
		    this.cleanup(ps);
			this.cleanup(conn);
		}
		catch(SQLException e) {
			throw new IException("修改贷款帐户的子帐户出错  " + e.getMessage());
		}
		finally{
		    this.cleanup(ps);
			this.cleanup(conn);
		}
	}
	
	/**
	 * Modify by leiyang 2007-6-14 用于取得贷款帐户的子帐户Bean
	 * @param subAccountId
	 * @return
	 * @throws SQLException
	 * @throws IException 
	 */
	public SubAccountLoanInfo getSubAccountLoanInfoByID(long subAccountId) throws SQLException, IException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		SubAccountLoanInfo subAccountLoanInfo = new SubAccountLoanInfo();
		try{
			conn = this.getConnection();
			String strSQL ="select * from sett_subaccount where id = " + subAccountId;
			Log.print(strSQL);
			ps = conn.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if(rs.next()) {
				subAccountLoanInfo.setID(rs.getLong("ID"));
				subAccountLoanInfo.setAccountID(rs.getLong("NAccountID"));
				subAccountLoanInfo.setInterest(rs.getDouble("MInterest"));
				subAccountLoanInfo.setBalance(rs.getDouble("MBalance"));
				subAccountLoanInfo.setOpenAmount(rs.getDouble("MOpenAmount"));
				subAccountLoanInfo.setOpenDate(rs.getTimestamp("DTOpen"));
				subAccountLoanInfo.setFinishDate(rs.getTimestamp("DTFinish"));
				subAccountLoanInfo.setIsInterest(rs.getLong("NIsInterest"));
				subAccountLoanInfo.setClearInterestDate(rs.getTimestamp("DTClearInterest"));
				subAccountLoanInfo.setStatusID(rs.getLong("NStatusID"));
				subAccountLoanInfo.setDailyUncheckAmount(rs.getDouble("MUNCHECKPAYMENTAMOUNT")); // 当天累计未复核付款金额
				subAccountLoanInfo.setLoanNoteID(rs.getLong("AL_NLoanNoteID"));
				subAccountLoanInfo.setIsCycLoan(rs.getLong("AL_NIsCycLoan"));
				subAccountLoanInfo.setCalculateInterestDate(rs.getTimestamp("AL_DTCalculateInterest"));
				subAccountLoanInfo.setPayInterestAccountID(rs.getLong("AL_NPayInterestAccountID"));
				subAccountLoanInfo.setReceiveInterestAccountID(rs.getLong("AL_NReceiveInterestAccountID"));
				subAccountLoanInfo.setPreDrawInterest(rs.getDouble("AL_MPreDrawInterest"));
				subAccountLoanInfo.setPreDrawDate(rs.getTimestamp("AL_DTPreDraw"));
				subAccountLoanInfo.setPaySuretyAccountID(rs.getLong("AL_NPaySuretyAccountID"));
				subAccountLoanInfo.setReceiveSuretyAccountID(rs.getLong("AL_NReceiveSuretyAccountID"));
				subAccountLoanInfo.setCommissionAccountID(rs.getLong("AL_NCommissionAccountID"));
				subAccountLoanInfo.setSuretyFee(rs.getDouble("AL_MSuretyFee"));
				subAccountLoanInfo.setClearSureFeeDate(rs.getTimestamp("AL_DTClearSureFee"));
				subAccountLoanInfo.setCommission(rs.getDouble("AL_MCommission"));
				subAccountLoanInfo.setClearCommissionDate(rs.getTimestamp("AL_DTClearCommission"));
				subAccountLoanInfo.setInterestTaxAccountID(rs.getLong("AL_NInterestTaxAccountID"));
				subAccountLoanInfo.setInterestTax(rs.getDouble("AL_MInterestTax"));
				subAccountLoanInfo.setInterestTaxRate(rs.getDouble("AL_MInterestTaxRate"));
				subAccountLoanInfo.setClearInterestTaxDate(rs.getTimestamp("AL_DTClearInterestTax"));
				subAccountLoanInfo.setEffectiveTaxDate(rs.getTimestamp("AL_DTEffectiveTax"));
				subAccountLoanInfo.setOverDueAccountID(rs.getLong("AL_NOverDueAccountID"));
				subAccountLoanInfo.setOverDueInterest(rs.getDouble("AL_MOverDueInterest"));
				subAccountLoanInfo.setClearOverDueDate(rs.getTimestamp("AL_DTClearOverDue"));
				subAccountLoanInfo.setCompoundAccountID(rs.getLong("AL_NCompoundAccountID"));
				subAccountLoanInfo.setCompoundInterest(rs.getDouble("AL_MCompoundInterest"));
				subAccountLoanInfo.setClearCompoundDate(rs.getTimestamp("AL_DTClearCompound"));
				subAccountLoanInfo.setArrearageInterest(rs.getDouble("AL_MArrearageInterest"));
				subAccountLoanInfo.setConsignAccountID(rs.getLong("AL_NConsignAccountID"));
				subAccountLoanInfo.setOverDueArrearageInterest(rs.getDouble("AL_MOverDueArrearageInterest"));
				subAccountLoanInfo.setInterestTaxRatePlanID(rs.getLong("AL_NInterestTaxRatePlanID"));
			}
			this.cleanup(rs);
			this.cleanup(ps);
			this.cleanup(conn);
		}catch(SQLException e) {
			throw new IException("取得贷款帐户的子帐户Bean出错  " + e.getMessage());
		}
		finally{
			this.cleanup(rs);
		    this.cleanup(ps);
			this.cleanup(conn);
		}
		return subAccountLoanInfo;
	}
	
	public static void main(String[] args) throws Exception
	{
		Sett_TransRepaymentLoanDAO dao = new Sett_TransRepaymentLoanDAO();
		SubLoanAccountDetailInfo sub = new SubLoanAccountDetailInfo();
		sub.setPayFormID(3);
		sub = dao.findSubLoanAccountDetailByCondition(sub);
	}
	/**
	 * 通过还款通知单的ID查询获得该通知单的所有还款金额  //ADD  BY-------------------------------华联
	 * @param PReformid
	 * @return 贷款归还记录
	 * @throws SQLException
	 */
	public double getTotalMountByPReformid(long PReformid,long lID) throws SQLException
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		double Amout = 0;
		try
		{
			conn = this.getConnection();
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append("select sum(t.mamount) mamount from sett_transrepaymentloan t where  t.nstatusid in (2,3) \n");
			sbSQL.append("and t.npreformid="+PReformid);
			if(lID>0){
				sbSQL.append(" and t.id !="+lID);	
			}		
			System.out.println("==============SQL=============="+sbSQL.toString());
			pstmt = conn.prepareStatement(sbSQL.toString());			
			rset = pstmt.executeQuery();
			if (rset != null && rset.next())
			{
				Amout = rset.getDouble("mamount");
			}
			this.cleanup(rset);
			this.cleanup(pstmt);
			this.cleanup(conn);
		}
		finally
		{
			this.cleanup(rset);
			this.cleanup(pstmt);
			this.cleanup(conn);
		}
		return Amout;
	}		
	/**
	 * 通过还款通知单的ID查询获得该通知单的金额 
	 * @param PReformid    //ADD  BY-------------------------------华联
	 * @return 查询还款通知单金额
	 * @throws SQLException
	 */
	public double getAMountByPReformid(long PReformid) throws SQLException
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		double Amout = 0;
		try
		{
			conn = this.getConnection();
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append("select mamount from loan_aheadrepayform t where  t.nstatusid in(3,4)\n");
			sbSQL.append("and t.id="+ PReformid);
			System.out.println("==============SQL=============="+sbSQL.toString());
			pstmt = conn.prepareStatement(sbSQL.toString());			
			rset = pstmt.executeQuery();
			if (rset != null && rset.next())
			{
				Amout = rset.getDouble("mamount");
			}
			this.cleanup(rset);
			this.cleanup(pstmt);
			this.cleanup(conn);
		}
		finally
		{
			this.cleanup(rset);
			this.cleanup(pstmt);
			this.cleanup(conn);
		}
		return Amout;
	}
	/**
	 * 通过放款单ID查找贷款收回记录 
	 * @param id ID
	 * @return 贷款收回记录
	 * @throws SQLException
	 * @throws IException 
	 */
   public Collection findInterestByNoteID(long lLoanNoteID ,long nOfficeID,long nCurrencyID, Timestamp nClearInterestDate) throws SQLException, IException
    {
	TransRepaymentLoanInfo info = null;
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rset = null;
	Vector v = new Vector();
	Timestamp tsLastInterestDate = null;
	String tsDate = Env.getSystemDateString(nOfficeID, nCurrencyID);
	AdjustInterestConditionInfo adjustInfo =  new AdjustInterestConditionInfo();
	try
	{
		conn = this.getConnection();
		StringBuffer sbSQL = new StringBuffer(128);
		sbSQL.append(" SELECT a.nloanaccountid,a.nsubaccountid,a.nclientid, a.nloancontractid,a.npreformid,a.mamount, b.dtoutdate,\n");
		sbSQL.append(" nvl(b.minterestrate,0) interestrate,nvl(b.mAdjustRate,0) mAdjustRate,nvl(b.mStaidAdjustRate,0) mStaidAdjustRate,a.dtintereststart \n");
		sbSQL.append("FROM SETT_TRANSREPAYMENTLOAN a,loan_payform b \n");
		sbSQL.append(" where a.nloannoteid = b.id ");
		sbSQL.append(" and a.nofficeid = ? ");
		sbSQL.append(" and a.ncurrencyid = ? ");
		sbSQL.append(" and a.nstatusid = "+SETTConstant.TransactionStatus.CHECK);
		sbSQL.append(" and b.nstatusid = "+LOANConstant.LoanPayNoticeStatus.USED);
		sbSQL.append(" and a.nloannoteid = ? ");
		sbSQL.append(" and a.dtexecute =to_date('" + tsDate +"','yyyy-mm-dd')");	//有干扰找XWHE,此条件加入原因：当一笔放款单，有多次还款时，系统无法区分在上次结息日以前的还款也会查出来，这是错误的，现在业务流程是，利随本清
		sbSQL.append(" and a.ntransactiontypeid in("+SETTConstant.TransactionType.TRUSTLOANRECEIVE+","+SETTConstant.TransactionType.CONSIGNLOANRECEIVE+")");
		pstmt = conn.prepareStatement(sbSQL.toString());
		int index = 1;
		pstmt.setLong(index++, nOfficeID);
		pstmt.setLong(index++, nCurrencyID);
		pstmt.setLong(index++, lLoanNoteID);
		rset = pstmt.executeQuery();
		while (rset != null && rset.next())
		{
			info = resultInterestToInfo(rset);
			if(info.getSubAccountID()>0)
			{
			   try
				{
				Sett_SubAccountDAO sett_SubAccountDAO = new Sett_SubAccountDAO();
				SubAccountAssemblerInfo subAccountAssemblerInfo = null;
				SubAccountLoanInfo resultInfo = new SubAccountLoanInfo();
								
				subAccountAssemblerInfo = sett_SubAccountDAO.findByID(info.getSubAccountID());
				resultInfo.setID(info.getSubAccountID());
				resultInfo = sett_SubAccountDAO.querySubAccountInfo(resultInfo);
			    tsLastInterestDate = resultInfo.getClearInterestDate();//通过子账户获取上一个结息日
				}
			    catch (Exception ie)
				{
					throw new IException(true, "子账户表中没有对应记录，查询失败", null);
				}
			}
			    Timestamp tsRepayStartDate  =  info.getInterestStart();//还款开始日期
			    Timestamp tsRepayEndDate  =  info.getExecute();//还款结束日期
			    InterestCalculation interestCalculation = new InterestCalculation(conn);
			    int repayDays = (int) interestCalculation.getIntervalDays(tsRepayStartDate, tsRepayEndDate, 1);
			    int interestDays = (int) interestCalculation.getIntervalDays(tsLastInterestDate, tsRepayEndDate, 1);
			if(repayDays<interestDays)
			{
				String strSQL = null;
				ResultSet rs = null;
				long lRecordCount = -1;
				Timestamp startInterestDate = null;
				
				StringBuffer sSQL = new StringBuffer(128);
				sSQL.append(" SELECT count(*) FROM   loan_rateadjustpaydetail  bb ");
				sSQL.append(" WHERE   bb.nContractID= "+info.getLoanContractID());
				sSQL.append(" and bb.nloanpaynoticeid ="+lLoanNoteID);
				sSQL.append(" and bb.STATUS ="+Constant.RecordStatus.VALID);
				sSQL.append(" and bb.dtstartdate>= to_date('" + DataFormat.getDateString(tsRepayStartDate)+"','yyyy-mm-dd')");
				sSQL.append(" and bb.dtstartdate< to_date('"+ DataFormat.getDateString(tsRepayEndDate)+"','yyyy-mm-dd')");	//当天还款不取当天生效日的利率			
				pstmt = conn.prepareStatement(sSQL.toString());
				rs = pstmt.executeQuery();
				//prepareStatement(strSQL);
               // rs = executeQuery();
				if (rs != null && rs.next())
                {
                    lRecordCount = rs.getLong(1);
                }
				if (lRecordCount <= 0)
                {
					int Day = (int) interestCalculation.getIntervalDays(tsRepayStartDate, tsRepayEndDate, 1);
					double interest = UtilOperation.Arith.div(UtilOperation.Arith.mul(UtilOperation.Arith.mul(info.getAmount(), info.getInterestIncome()), Day), 36000);
					info.setInterest(interest);
                }
				else
				{				
				strSQL = " select nvl(cc.mRate,0) mRate , nvl(bb.mAdjustRate,0) mAdjustRate,nvl(bb.mStaidAdjustRate,0) mStaidAdjustRate,bb.dtstartdate ";
				strSQL+= " FROM   loan_rateadjustpaydetail bb, loan_InterestRate cc ";
				strSQL+= " where bb.nContractID = "+info.getLoanContractID();
				strSQL+= " and bb.nloanpaynoticeid = "+lLoanNoteID;
				strSQL+= " and bb.STATUS ="+Constant.RecordStatus.VALID;
				strSQL+= " and bb.dtstartdate>= to_date('" + DataFormat.getDateString(tsRepayStartDate)+"','yyyy-mm-dd')";
				strSQL+= " and bb.dtstartdate< to_date('"+ DataFormat.getDateString(tsRepayEndDate)+"','yyyy-mm-dd')";
				strSQL+= " and bb.nBankInterestID = cc.id(+) ";
				strSQL+= " order by bb.dtstartdate ";
				
				pstmt = conn.prepareStatement(strSQL.toString());
				rs = pstmt.executeQuery();
				//rs = executeQuery();
				double interest1 = 0.0;
				int compareDay = 0;
				double interestRate = info.getInterestIncome();
				while (rs != null && rs.next())
	            {				
					adjustInfo.setDAdjustRate(rs.getDouble("mrate") * (1 + rs.getDouble("mAdjustRate") / 100)+ rs.getDouble("mStaidAdjustRate"));
					adjustInfo.setTsRateValidate(rs.getTimestamp("dtstartdate"));
					compareDay = (int) interestCalculation.getIntervalDays(tsRepayStartDate, adjustInfo.getTsRateValidate(), 1);
					interest1 = interest1 + UtilOperation.Arith.div(UtilOperation.Arith.mul(UtilOperation.Arith.mul(info.getAmount(), interestRate), compareDay), 36000);
					tsRepayStartDate = adjustInfo.getTsRateValidate();
					interestRate = adjustInfo.getDAdjustRate();
				}
				 compareDay =(int) interestCalculation.getIntervalDays(tsRepayStartDate, tsRepayEndDate, 1);
				 interest1 = interest1+UtilOperation.Arith.div(UtilOperation.Arith.mul(UtilOperation.Arith.mul(info.getAmount(), adjustInfo.getDAdjustRate()), compareDay), 36000);
				 System.out.println("还款利息:" + interest1);
				 info.setInterest(interest1);
				}
			}
			
		
				/*
				 * 国电桑旭提出
				 * 利息费用支付：利息页面，点击“还款单利息计算”按钮打开的页面，利息计算有误，显示的是从发放到开机日的所有利息，
				 * 但是此放款单之前做过结息，此次利息应该是从上次结息日到开机日的利息
				*/
				if(repayDays >= interestDays)
				{
					info.setInterestStart(tsLastInterestDate);
					String sSQL = "";
					double	interestRate =  info.getInterestIncome();
					//取最新的利率
					sSQL = " select nvl(cc.mRate,0) mRate , nvl(bb.mAdjustRate,0) mAdjustRate,nvl(bb.mStaidAdjustRate,0) mStaidAdjustRate,bb.dtstartdate ";
					sSQL+= " FROM   loan_rateadjustpaydetail bb ,loan_InterestRate cc  ";
					sSQL+= " where bb.nContractID = "+info.getLoanContractID();
					sSQL+= " and bb.nloanpaynoticeid = "+lLoanNoteID;
					sSQL+= " and bb.STATUS ="+Constant.RecordStatus.VALID;
					sSQL+= " and bb.dtstartdate< to_date('" + DataFormat.getDateString(tsLastInterestDate)+"','yyyy-mm-dd')";
					sSQL+= " and bb.nBankInterestID = cc.id(+) ";
					sSQL+= " order by bb.dtstartdate desc";
					pstmt = conn.prepareStatement(sSQL.toString());
					ResultSet rsRate = null;
					rsRate = pstmt.executeQuery();
					if (rsRate != null && rsRate.next())
	                {
						interestRate = rsRate.getDouble("mrate") * (1 + rsRate.getDouble("mAdjustRate") / 100)+ rsRate.getDouble("mStaidAdjustRate");
	                }
					//
					ResultSet rs = null;
					long lRecordCount = -1;
					
					ResultSet rs1 = null;
								
					String sSQL1 = "";
					sSQL1 =" SELECT count(*) FROM   loan_rateadjustpaydetail  bb ";
					sSQL1+=" WHERE   bb.nContractID= "+info.getLoanContractID();
					sSQL1+=" and bb.nloanpaynoticeid ="+lLoanNoteID;
					sSQL1+=" and bb.STATUS ="+Constant.RecordStatus.VALID;
					sSQL1+=" and bb.dtstartdate>= to_date('" + DataFormat.getDateString(tsLastInterestDate)+"','yyyy-mm-dd')";
					sSQL1+=" and bb.dtstartdate< to_date('"+ DataFormat.getDateString(tsRepayEndDate)+"','yyyy-mm-dd')";
					pstmt = conn.prepareStatement(sSQL1.toString());
					rs = pstmt.executeQuery();
					if (rs != null && rs.next())
	                {
	                    lRecordCount = rs.getLong(1);
	                }
					
					if (lRecordCount <= 0)
	                {
						int Day = (int) interestCalculation.getIntervalDays(tsLastInterestDate, tsRepayEndDate, 1);
						double interest = UtilOperation.Arith.div(UtilOperation.Arith.mul(UtilOperation.Arith.mul(info.getAmount(), interestRate), Day), 36000);
						info.setInterest(interest);
	                }
					else
					{
						sSQL = " select nvl(cc.mRate,0) mRate , nvl(bb.mAdjustRate,0) mAdjustRate,nvl(bb.mStaidAdjustRate,0) mStaidAdjustRate,bb.dtstartdate ";
						sSQL+= " FROM   loan_rateadjustpaydetail bb ,loan_InterestRate cc ";
						sSQL+= " where bb.nContractID = "+info.getLoanContractID();
						sSQL+= " and bb.nloanpaynoticeid = "+lLoanNoteID;
						sSQL+= " and bb.STATUS ="+Constant.RecordStatus.VALID;
						sSQL+= " and bb.dtstartdate>= to_date('" + DataFormat.getDateString(tsLastInterestDate)+"','yyyy-mm-dd')";
						sSQL+= " and bb.dtstartdate< to_date('"+ DataFormat.getDateString(tsRepayEndDate)+"','yyyy-mm-dd')";
						sSQL+= " and bb.nBankInterestID = cc.id(+) ";
						sSQL+= " order by bb.dtstartdate ";
						//prepareStatement(sSQL);
						pstmt = conn.prepareStatement(sSQL.toString());
						rs1 = pstmt.executeQuery();
						//rs1 = executeQuery();
						double interest2 = 0.0;
						int compareDay2 = 0;
						//double interestRate = info.getInterestIncome();
						while (rs1 != null && rs1.next())
						{
							adjustInfo.setDAdjustRate(rs1.getDouble("mrate") * (1 + rs1.getDouble("mAdjustRate") / 100)+ rs1.getDouble("mStaidAdjustRate"));
							adjustInfo.setTsRateValidate(rs1.getTimestamp("dtstartdate"));
						    compareDay2 = (int) interestCalculation.getIntervalDays(tsLastInterestDate, adjustInfo.getTsRateValidate(), 1);
						    System.out.println("还款天数:" + compareDay2);
						    System.out.println("还款利率:" + interestRate);
						    interest2 = interest2+ UtilOperation.Arith.div(UtilOperation.Arith.mul(UtilOperation.Arith.mul(info.getAmount(), interestRate), compareDay2), 36000);
						    System.out.println("还款利息:" + interest2);
						    tsLastInterestDate = adjustInfo.getTsRateValidate();
						    interestRate = adjustInfo.getDAdjustRate();
						}
						compareDay2 = (int) interestCalculation.getIntervalDays(tsLastInterestDate, tsRepayEndDate, 1);
						System.out.println("还款天数:" + compareDay2);
						System.out.println("还款利率:" + adjustInfo.getDAdjustRate());
						interest2 = interest2+UtilOperation.Arith.div(UtilOperation.Arith.mul(UtilOperation.Arith.mul(info.getAmount(), adjustInfo.getDAdjustRate()), compareDay2), 36000);	
						System.out.println("还款利息:" + interest2);
						info.setInterest(interest2);
				   }	
				}
			/*
			}
			else
			{
				if(repayDays >= interestDays)
				{
					ResultSet rs4 = null ;
					String sbSQL4 = "";
					double	interestRate =  info.getInterestIncome();			
					ResultSet rs = null;
					long lRecordCount = -1;
					String sSQL = "";
					ResultSet rs1 = null;
					
					sbSQL4 = " SELECT * FROM   loan_rateadjustpaydetail  bb ";
					sbSQL4+=" WHERE   bb.nContractID= "+info.getLoanContractID();
					sbSQL4+=" and bb.nloanpaynoticeid ="+lLoanNoteID;
					sbSQL4+=" and bb.dtstartdate>= to_date('" + DataFormat.getDateString(tsRepayStartDate)+"','yyyy-mm-dd')";
					sbSQL4+=" and bb.dtstartdate<= to_date('"+ DataFormat.getDateString(tsLastInterestDate)+"','yyyy-mm-dd')";
					pstmt = conn.prepareStatement(sbSQL4.toString());
					rs4 = pstmt.executeQuery();
					while (rs4 != null && rs4.next())
					{					
						interestRate = rs4.getDouble("mrate") * (1 + rs4.getDouble("mAdjustRate") / 100)+ rs4.getDouble("mStaidAdjustRate");
					}
								
					String sSQL1 = "";
					sSQL1 =" SELECT count(*) FROM   loan_rateadjustpaydetail  bb ";
					sSQL1+=" WHERE   bb.nContractID= "+info.getLoanContractID();
					sSQL1+=" and bb.nloanpaynoticeid ="+lLoanNoteID;
					sSQL1+=" and bb.dtstartdate>= to_date('" + DataFormat.getDateString(tsRepayStartDate)+"','yyyy-mm-dd')";
					sSQL1+=" and bb.dtstartdate<= to_date('"+ DataFormat.getDateString(tsRepayEndDate)+"','yyyy-mm-dd')";
					pstmt = conn.prepareStatement(sSQL1.toString());
					rs = pstmt.executeQuery();
					if (rs != null && rs.next())
	                {
	                    lRecordCount = rs.getLong(1);
	                }
					if (lRecordCount <= 0)
	                {
						int Day = (int) interestCalculation.getIntervalDays(tsRepayStartDate, tsRepayEndDate, 1);
						double interest = UtilOperation.Arith.div(UtilOperation.Arith.mul(UtilOperation.Arith.mul(info.getAmount(), interestRate), Day), 36000);
						info.setInterest(interest);
	                }
					else
					{							
					sSQL = " select bb.* ";
					sSQL+= " FROM   loan_rateadjustpaydetail bb ";
					sSQL+= " where bb.nContractID = "+info.getLoanContractID();
					sSQL+= " and bb.nloanpaynoticeid = "+lLoanNoteID;
					sSQL+= " and bb.dtstartdate>= to_date('" + DataFormat.getDateString(tsLastInterestDate)+"','yyyy-mm-dd')";
					sSQL+= " and bb.dtstartdate<= to_date('"+ DataFormat.getDateString(tsRepayEndDate)+"','yyyy-mm-dd')";
					sSQL+= " order by bb.dtstartdate ";
					//prepareStatement(sSQL);
					pstmt = conn.prepareStatement(sSQL.toString());
					rs1 = pstmt.executeQuery();
					//rs1 = executeQuery();
					double interest2 = 0.0;
					int compareDay2 = 0;
					//double interestRate = info.getInterestIncome();
					while (rs1 != null && rs1.next())
					{
						adjustInfo.setDAdjustRate(rs1.getDouble("mrate") * (1 + rs1.getDouble("mAdjustRate") / 100)+ rs1.getDouble("mStaidAdjustRate"));
						adjustInfo.setTsRateValidate(rs1.getTimestamp("dtstartdate"));
					    compareDay2 = (int) interestCalculation.getIntervalDays(tsLastInterestDate, adjustInfo.getTsRateValidate(), 1);
					    System.out.println("还款天数:" + compareDay2);
					    System.out.println("还款利率:" + interestRate);
					    interest2 = interest2+ UtilOperation.Arith.div(UtilOperation.Arith.mul(UtilOperation.Arith.mul(info.getAmount(), interestRate), compareDay2), 36000);
					    System.out.println("还款利息:" + interest2);
					    tsLastInterestDate = adjustInfo.getTsRateValidate();
					    interestRate = adjustInfo.getDAdjustRate();
					}
					compareDay2 = (int) interestCalculation.getIntervalDays(tsLastInterestDate, tsRepayEndDate, 1);
					System.out.println("还款天数:" + compareDay2);
					System.out.println("还款利率:" + adjustInfo.getDAdjustRate());
					interest2 = interest2+UtilOperation.Arith.div(UtilOperation.Arith.mul(UtilOperation.Arith.mul(info.getAmount(), adjustInfo.getDAdjustRate()), compareDay2), 36000);	
					System.out.println("还款利息:" + interest2);
					info.setInterest(interest2);
				   }	
				}
			}
			*/
			System.out.println("还款金额:" + info.getAmount());
			System.out.println("贷款账户号:" + info.getLoanAccountID());
			System.out.println("放款通知单:" + lLoanNoteID);
			v.add(info);
		}
		this.cleanup(rset);
		this.cleanup(pstmt);
		this.cleanup(conn);
	}
	finally
	{
	    this.cleanup(rset);
		this.cleanup(pstmt);
		this.cleanup(conn);
	}
	return v;
}
/**
	 * 将ResultSet转换为TransRepaymentLoanInfo 
	 * @param rset ResultSet
	 * @return TransRepaymentLoanInfo
	 * @throws SQLException
* */
private static TransRepaymentLoanInfo resultInterestToInfo(ResultSet rset) throws SQLException
{
	TransRepaymentLoanInfo info = new TransRepaymentLoanInfo();
	info.setLoanAccountID(rset.getLong("nloanaccountid"));
	info.setLoanContractID(rset.getLong("nloancontractid"));
	info.setClientID(rset.getLong("nclientid"));
	info.setInterestStart(rset.getTimestamp("dtoutdate"));
	info.setExecute(rset.getTimestamp("dtintereststart"));
	info.setInterestIncome(rset.getDouble("interestrate")* (1 + rset.getDouble("mAdjustRate") / 100)+ rset.getDouble("mStaidAdjustRate") );
	info.setAmount(rset.getDouble("mamount"));
	info.setPreFormID(rset.getLong("npreformid"));
	info.setSubAccountID(rset.getLong("nsubaccountid"));
	
	return info;
  }
	/**
	 * 通过条件查询同一合同和放宽通知单ID下 实际支付的利息、手续费等金额合计
	 * @param id ID
	 * @return 贷款收回记录
	 * @throws SQLException
	 */
	public TransRepaymentLoanInfo grantFindInterestByCondition(SubLoanAccountDetailInfo info1) throws SQLException
	{
		TransRepaymentLoanInfo info = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try
		{
			conn = this.getConnection();
			StringBuffer sbSQL = new StringBuffer(128);
			sbSQL.append(" select sum(MREALINTEREST) ,sum(MREALINTERESTRECEIVABLE),sum(MREALINTERESTINCOME),sum(MREALINTERESTTAX), \n");
			sbSQL.append(" sum(MREALCOMPOUNDINTEREST),sum(MREALOVERDUEINTEREST),sum(MREALSURETYFEE),sum(MREALCOMMISSION) \n");
			sbSQL.append(" from (select sum(MREALINTEREST) MREALINTEREST,sum(MREALINTERESTRECEIVABLE) MREALINTERESTRECEIVABLE,sum(MREALINTERESTINCOME) MREALINTERESTINCOME,sum(MREALINTERESTTAX) MREALINTERESTTAX, \n");
			sbSQL.append(" sum(MREALCOMPOUNDINTEREST) MREALCOMPOUNDINTEREST,sum(MREALOVERDUEINTEREST) MREALOVERDUEINTEREST,sum(MREALSURETYFEE) MREALSURETYFEE,sum(MREALCOMMISSION) MREALCOMMISSION \n");
			sbSQL.append(" from SETT_TRANSREPAYMENTLOAN where 1=1 and (NSTATUSID=2 or NSTATUSID=3) \n");
			sbSQL.append(" and dtinterestclear = to_date('"+DataFormat.formatDate( info1.getInterestStart())+"','yyyy-mm-dd') \n");
			if(info1.getContractID()>0){
				sbSQL.append(" and NLOANCONTRACTID="+info1.getContractID()+" \n");				
			}
			if(info1.getPayFormID()>0){
				sbSQL.append(" and NLOANNOTEID="+info1.getPayFormID()+" \n");				
			}
			
			sbSQL.append("  union   (select decode(b.ninteresttype, 1, sum(b.minterest), 0) mrealinterest, 0,0,0,        \n");
			sbSQL.append("                decode(b.ninteresttype, 4, sum(b.minterest), 0) mrealcompoundinterest, \n");
			sbSQL.append("                decode(b.ninteresttype, 5, sum(b.minterest), 0) mrealoverdueinterest,0,0   \n");
			sbSQL.append("           from sett_TransInterestSettlement b                                         \n");
			sbSQL.append("          where b.ntransactiontypeid = 104                                             \n");
			sbSQL.append("            and b.nsubaccountid = "+info1.getSubAccountID()+"                                     \n");
			sbSQL.append("            and b.dtinterestsettlement = to_date('"+DataFormat.formatDate(info1.getInterestStart())+"', 'yyyy-mm-dd')           \n");
			sbSQL.append("            and b.nstatusid <> 0                                                       \n");
			sbSQL.append("          group by b.ninteresttype) )                                                   \n");
			
			
			
			pstmt = conn.prepareStatement(sbSQL.toString());
			rset = pstmt.executeQuery();
			if (rset != null && rset.next())
			{
				info=new TransRepaymentLoanInfo();
				info.setRealInterest(rset.getDouble(1));
				info.setRealInterestReceiveAble(rset.getDouble(2));
				info.setRealInterestIncome(rset.getDouble(3));
				info.setRealInterestTax(rset.getDouble(4));
				info.setRealCompoundInterest(rset.getDouble(5));
				info.setRealOverDueInterest(rset.getDouble(6));
				info.setRealSuretyFee(rset.getDouble(7));
				info.setRealCommission(rset.getDouble(8));
			}
			this.cleanup(rset);
			this.cleanup(pstmt);
			this.cleanup(conn);
		}
		finally
		{
		    this.cleanup(rset);
			this.cleanup(pstmt);
			this.cleanup(conn);
		}
		return info;
	}
	
}
