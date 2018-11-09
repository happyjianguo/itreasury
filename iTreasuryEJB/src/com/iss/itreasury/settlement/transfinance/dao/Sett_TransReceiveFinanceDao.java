/*
 * Created on 2006-03-20
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */

package com.iss.itreasury.settlement.transfinance.dao;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.loan.contract.dao.ContractDao;
import com.iss.itreasury.loan.contract.dataentity.ContractInfo;
import com.iss.itreasury.loan.leasehold.dao.LoanAssureChargeFormDao;
import com.iss.itreasury.loan.leasehold.dataentity.LeaseholdPayNoticeInfo;
import com.iss.itreasury.settlement.transfinance.dataentity.TransReceiveFinanceInfo;
import com.iss.itreasury.settlement.transfinance.dataentity.QueryByStatusConditionInfo;
import com.iss.itreasury.settlement.transfixeddeposit.dataentity.TransFixedOpenInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IRollbackException;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.util.Log4j;

import java.rmi.RemoteException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author feiye 融资租凭存款交易的--收款--DAO类：
 *         1、所有变量都为Private,设置只能用setXXX方法，得到只能用getXXX方法。 2、包含变量、类型、默认值、说明 To
 *         change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */

public class Sett_TransReceiveFinanceDao extends SettlementDAO
{

	public final static int	ORDERBY_TRANSNO		= 0;	//交易号	
	
	public final static int	ORDERBY_CONTRACTID			= 1;	//贷款合同号											

	public final static int	ORDERBY_RECEIVEFORMID			= 2;//通知单编号												

	public final static int	ORDERBY_PAYBAILACCOUNTID			= 3;	//付保证金账户											

	public final static int	ORDERBY_RECEVICEBAILACCOUNTID	= 4;	//收保证收款账户											

	public final static int	ORDERBY_BAILAMOUNT				= 5;//收保证金金额												

	public final static int	ORDERBY_PAYPOUNDAGEACCOUNTID	= 6;	//付手续费账户											

	public final static int	ORDERBY_POUNDAGGAMOUNT			= 7;	//手续费金额											

	public final static int	ORDERBY_INTERESTSTART				= 8;	//起息日											

	public final static int	ORDERBY_EXECUTE			= 9;		//执行日												

	public final static int	ORDERBY_ABSTRACT				= 10;	//摘要											

	public final static int	ORDERBY_STATUSID			= 11;		//状态										
	
	/**
	 * 日志添加
	 */
	private Log4j			log							= new Log4j(Constant.ModuleType.SETTLEMENT, this);

	protected Log4j log4j = new Log4j(Constant.ModuleType.LOAN, this);
	/**
	 * 计算收保证金账户的和
	 * @param lID
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 * @throws IException
	 */
		public double sumRecognizanceAmount(long lID) throws RemoteException,IRollbackException,IException {
			PreparedStatement ps = null;
			ResultSet     rs     = null;
			Connection    con    = null;
			String        strSQL = null;
			double        result = 0.0;
			try
			{
				con = Database.getConnection();
				strSQL = " select sum(MBAILAMOUNT) from Sett_TransReceiveFinance where NCONTRACTID = "+lID+" and nstatusid = "+SETTConstant.TransactionStatus.CHECK;
				ps = con.prepareStatement(strSQL);
				rs = ps.executeQuery();
				if (rs != null && rs.next())
				{
					result = rs.getDouble(1);
				}
			}
			catch (Exception ex)
			{
				log4j.error(ex.toString());
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
				catch (Exception ex)
				{
					log4j.error(ex.toString());
					throw new IException("Gen_E001");
				}
			}
			
			return result;
		}
	
	/**
	 * 新增融资租赁收款交易的方法： 逻辑说明：
	 * 
	 * @param info,
	 *            SettTransReceiveFinanceInfo, 融资租赁收款交易实体类
	 * @return long, 新生成记录的标识
	 * @throws Exception
	 *             不用try catch 不用管直接抛出即可，因为是修改结果，所以才会有此现象
	 */
	public long add(TransReceiveFinanceInfo info) throws Exception
	{

		long lReturn = -1;
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			// 利用数据库的序列号取ID;
			long id = super.getSett_TransReceiveFinanceID();
			info.setID(id);
			StringBuffer buffer = new StringBuffer();
			buffer.append("INSERT INTO Sett_TransReceiveFinance \n");
			buffer.append("(ID,nOfficeID,nCurrencyID,sTransNo, \n");
			buffer.append("nTransactionTypeID,nStatusID,dtModify, \n");
			buffer.append("dtInput,nInputUserID,nCheckUserID, \n");
			buffer.append("nAbstractID,sAbstract,sCheckAbstract,nContractID, \n");
			buffer.append("nReceiveFormID,nReceiveBailAccountID, \n");
			buffer.append("nPayBailAccountID,nPayBailBankID,mBailAmount, \n");
			buffer.append("nIsBailInterest,nPayPoundageAccountID, \n");
			buffer.append("nPayPoundageBankID,mPoundageAmount, \n");
			buffer.append("dtInterestStart, \n");
			buffer.append("dtExecute,sInstructionNo,nCashFlowID, \n");
			buffer.append("mRate,nInterestAccountID) \n");
			buffer.append("VALUES  \n");
			buffer.append("(?,?,?,?,?,?,sysdate,?,?,?, \n");
			buffer.append("?,?,?,?,?,?,?,?,?,?, \n");
			buffer.append("?,?,?,?,?,?,?,?,?) \n");

			ps = con.prepareStatement(buffer.toString());
			log.info(" 融资租赁收款交易:  \n" + buffer.toString());
			int index = 1;
			ps.setLong(index++, info.getID());
			ps.setLong(index++, info.getOfficeID());
			ps.setLong(index++, info.getCurrencyID());
			ps.setString(index++, info.getTransNo());
			ps.setLong(index++, info.getTransactionTypeID());
			ps.setLong(index++, info.getStatusID());
			ps.setTimestamp(index++, info.getInputDate());
			ps.setLong(index++, info.getInputUserID());
			ps.setLong(index++, info.getCheckUserID());
			ps.setLong(index++, info.getAbstractID());
			ps.setString(index++, info.getAbstract());
			ps.setString(index++, info.getCheckAbstract());
			ps.setLong(index++, info.getContractID());
			ps.setLong(index++, info.getReceiveFormID());
			ps.setLong(index++, info.getReceviceBailAccountID());
			ps.setLong(index++, info.getPayBailAccountID());
			ps.setLong(index++, info.getPayBailBankID());
			ps.setDouble(index++, info.getBailAmount());
			ps.setLong(index++, info.getIsBailInterest());
			ps.setLong(index++, info.getPayPoundageAccountID());
			ps.setLong(index++, info.getPayPoundageBankID());
			ps.setDouble(index++, info.getPoundageAmount());
			ps.setTimestamp(index++, info.getInterestStartDate());
			ps.setTimestamp(index++, info.getExecuteDate());
			ps.setString(index++, info.getInstructionNo());
			ps.setLong(index++, info.getCashFlowID());
			ps.setDouble(index++, info.getRate());
			ps.setLong(index++, info.getInterestAccountID());

			int i = ps.executeUpdate();
			if (i > 0) {
				lReturn = info.getID();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		finally {
			try {
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			}
			catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		return lReturn;
	}


	/**
	 * 修改融资租赁收款交易的方法： 逻辑说明：
	 * 
	 * @param info,
	 *            SettTransReceiveFinanceInfo, 融资租赁 收款交易实体类
	 * @return long, 被修改记录的标识
	 * @throws Exception
	 */
	public long update(TransReceiveFinanceInfo info) throws Exception
	{

		long lReturn = -1;
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append(" update Sett_TransReceiveFinance set \n");
			buffer.append(" nOfficeID=?,nCurrencyID=?,sTransNo=?,\n");
			buffer.append(" nTransactionTypeID=?,nStatusID=?,dtModify=sysdate,dtInput=?,\n");
			buffer.append(" nInputUserID=?,nCheckUserID=?,nAbstractID=?,sAbstract=?,\n");
			buffer.append(" sCheckAbstract=?,nContractID=?,nReceiveFormID=?,\n");
			buffer.append(" nReceiveBailAccountID=?,nPayBailAccountID=?,nPayBailBankID=?,mBailAmount=?,\n");
			buffer.append(" nIsBailInterest=?,nPayPoundageAccountID=?,nPayPoundageBankID=?,mPoundageAmount=?,\n");
			buffer.append(" dtInterestStart=?,dtExecute=?,sInstructionNo=?,nCashFlowID=?,\n");
			buffer.append(" mRate=?,nInterestAccountID=? \n");
			buffer.append(" where ID=? \n");
			
			ps = con.prepareStatement(buffer.toString());
			log.info("修改融资租赁收款交易 :  \n " + buffer.toString());

			int index = 1;

			ps.setLong(index++, info.getOfficeID());
			ps.setLong(index++, info.getCurrencyID());
			ps.setString(index++, info.getTransNo());
			ps.setLong(index++, info.getTransactionTypeID());
			ps.setLong(index++, info.getStatusID());
			ps.setTimestamp(index++, info.getInputDate());
			ps.setLong(index++, info.getInputUserID());
			ps.setLong(index++, info.getCheckUserID());
			ps.setLong(index++, info.getAbstractID());
			ps.setString(index++, info.getAbstract());
			ps.setString(index++, info.getCheckAbstract());
			ps.setLong(index++, info.getContractID());
			ps.setLong(index++, info.getReceiveFormID());
			ps.setLong(index++, info.getReceviceBailAccountID());
			ps.setLong(index++, info.getPayBailAccountID());
			ps.setLong(index++, info.getPayBailBankID());
			ps.setDouble(index++, info.getBailAmount());
			ps.setLong(index++, info.getIsBailInterest());
			ps.setLong(index++, info.getPayPoundageAccountID());
			ps.setLong(index++, info.getPayPoundageBankID());
			ps.setDouble(index++, info.getPoundageAmount());
			ps.setTimestamp(index++, info.getInterestStartDate());
			ps.setTimestamp(index++, info.getExecuteDate());
			ps.setString(index++, info.getInstructionNo());
			ps.setLong(index++, info.getCashFlowID());
			ps.setDouble(index++, info.getRate());
			ps.setLong(index++, info.getInterestAccountID());
			ps.setLong(index++, info.getID());

			int i = ps.executeUpdate();
			if (i > 0) {
				lReturn = info.getID();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		finally {
			try {
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			}
			catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		return lReturn;
	}


	/**
	 * 根据标识查询融资租赁收款交易明细的方法： 逻辑说明：
	 * 
	 * @param lID
	 *            long , 交易的ID
	 * @return TransMarginOpenInfo, 融资租赁收款交易实体类
	 * @throws Exception
	 */
	public TransReceiveFinanceInfo findByID(long lID) throws Exception
	{

		TransReceiveFinanceInfo info = new TransReceiveFinanceInfo();
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {

			String strSQL = "select * from Sett_TransReceiveFinance where id=? ";
			log.info("根据标识查询融资租赁收款交易明细 :  \n " + strSQL);
			ps = con.prepareStatement(strSQL);
			ps.setLong(1, lID);
			rs = ps.executeQuery();
			if (rs.next()) {
				info = getReceiveFinance(info, rs);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		finally {
			try {
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			}
			catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		return info;
	}


	/**
	 * 根据交易号查询融资租赁收款交易明细的方法： 逻辑说明：
	 * 
	 * @param strTransNo
	 *            String , 交易号
	 * @return TransReceiveFinanceInfo, 融资租赁收款交易实体类
	 * @throws Exception
	 */
	public TransReceiveFinanceInfo findByTransNo(String strTransNo) throws Exception
	{

		TransReceiveFinanceInfo info = new TransReceiveFinanceInfo();
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {

			String strSQL = "select * from  Sett_TransReceiveFinance  where sTransNo=? ";
			log.info("根据交易号查询融资租赁收款交易明细 :  \n " + strSQL);
			ps = con.prepareStatement(strSQL);
			ps.setString(1, strTransNo);
			rs = ps.executeQuery();
			if (rs.next()) {
				info = getReceiveFinance(info, rs);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		finally {
			try {
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			}
			catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		return info;
	}

/*
	*//**
	 * 根据条件判断融资租赁收款交易是否重复的方法： 逻辑说明：
	 * 
	 * @param FixedContinueInfo
	 *            searchInfo , 融资租赁收款交易实体类
	 * @return boolean , false 重复
	 * @throws Exception
	 *//*
	public boolean checkIsDuplicate(TransFixedContinueInfo searchInfo) throws Exception
	{

		boolean rtnFlg = true;
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {

			String strSQL = "select * from Sett_TransOpenMarginDeposit where id=? ";

			log.info("判断融资租赁收款交易是否重复 : \n" + strSQL);
			ps = con.prepareStatement(strSQL);
			// ps.setLong(1,lID);
			rs = ps.executeQuery();
			if (rs.next()) {
				rtnFlg = false;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		finally {
			try {
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			}
			catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		return rtnFlg;
	}

*/
	/**
	 * 修改融资租赁收款交易状态的方法： 逻辑说明：
	 * 
	 * @param lID,
	 *            long, 本金交易标识
	 * @param lStatusID,
	 *            long, 状态标识
	 * @return long, 被修改记录的标识
	 * @throws Exception
	 */
	public long updateStatus(long lID, long lStatusID) throws Exception
	{

		long lReturn = -1;
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append("update Sett_TransReceiveFinance set nStatusID=? where ID=?");
			log.info("修改融资租赁收款交易状态 : \n" + buffer.toString());
			ps = con.prepareStatement(buffer.toString());
			int index = 1;
			ps.setLong(index++, lStatusID);
			ps.setLong(index++, lID);
			int i = ps.executeUpdate();
			if (i > 0) {
				lReturn = lID;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		finally {
			try {
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			}
			catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		return lReturn;
	}


	/**
	 * 设置融资租赁收款交易结果集： 逻辑说明：
	 * 
	 * @throws Exception
	 */
	private TransReceiveFinanceInfo getReceiveFinance(TransReceiveFinanceInfo info, ResultSet rs) throws Exception
	{
		info = new TransReceiveFinanceInfo();
		try {
			//基本信息
			info.setID(rs.getLong("ID"));
			info.setOfficeID(rs.getLong("nOfficeID"));
			info.setCurrencyID(rs.getLong("nCurrencyID"));
			info.setTransNo(rs.getString("sTransNo"));
			info.setTransactionTypeID(rs.getLong("nTransactionTypeID"));
			info.setStatusID(rs.getLong("nStatusID"));
			info.setModifyDate(rs.getTimestamp("dtModify"));
			info.setInputDate(rs.getTimestamp("dtInput"));
			info.setInputUserID(rs.getLong("nInputUserID"));
			info.setCheckUserID(rs.getLong("nCheckUserID"));
			info.setAbstractID(rs.getLong("nAbstractID"));
			info.setAbstract(rs.getString("sAbstract"));
			info.setCheckAbstract(rs.getString("sCheckAbstract"));
			
			//融资租赁业务信息（收款）
			info.setContractID(rs.getLong("NCONTRACTID"));		//合同ID（loan_contractForm表）
			info.setReceiveFormID(rs.getLong("NReceiveFormID"));	//收款通知单ID
			info.setReceviceBailAccountID(rs.getLong("nReceiveBailAccountID"));	//收融资租赁收款账户ID
			info.setPayBailAccountID(rs.getLong("nPayBailAccountID"));	//付融资租赁收款账户ID
			info.setPayBailBankID(rs.getLong("nPayBailBankID"));	//付融资租赁收款银行ID
			info.setBailAmount(rs.getDouble("mBailAmount"));	//收取融资租赁收款金额
			info.setIsBailInterest(rs.getLong("nIsBailInterest"));	//是否计息(融资租赁收款)
			info.setPayPoundageAccountID(rs.getLong("nPayPoundageAccountID"));	//付手续费账户ID
			info.setPayPoundageBankID(rs.getLong("nPayPoundageBankID"));	//付手续费银行ID
			info.setPoundageAmount(rs.getDouble("mPoundageAmount"));	//收取手续费金额
			info.setExecuteDate(rs.getTimestamp("dtExecute"));	//业务执行日
			info.setInterestStartDate(rs.getTimestamp("dtInterestStart"));	//融资租赁收款起息日

			//扩展的信息
			info.setInstructionNo(rs.getString("sInstructionNo"));
			info.setCashFlowID(rs.getLong("nCashFlowID"));
			info.setRate(rs.getDouble("mRate"));
			info.setInterestAccountID(rs.getLong("nInterestAccountID"));
			
			//合同
			if(info.getContractID()>0){
				System.out.println("取贷款合同信息-------------(开始)");
				ContractDao contractDao=new ContractDao();
				ContractInfo contractInfo=new ContractInfo();
				contractInfo=contractDao.findByID(info.getContractID());
				info.setContractCode(contractInfo.getContractCode());//合同编号
				info.setContractFinanceClientID(contractInfo.getBorrowClientID());//承租单位客户ID
				info.setContractFinanceClientCode(contractInfo.getBorrowClientCode());//承租单位客户编号			
				info.setContractFinanceClientName(contractInfo.getBorrowClientName());//承租单位客户名称
				info.setContractFinanceStartDate(contractInfo.getLoanStart());//租赁开始日期
				info.setContractFinanceEndDate(contractInfo.getLoanEnd());//租赁开始日期
				info.setContractFinanceTerm(contractInfo.getIntervalNum());//租凭期限
				info.setContractFinanceRate(contractInfo.getRate());//租赁利率
				
				info.setContractBailAmount(contractInfo.getRecognizanceAmount());//合同融资租赁 总保证金 金额
				info.setContractBailAmountForYS(contractInfo.getReceivedRecognizanceAmount());//已收融资租赁  保证金 累计金额
				info.setContractBailAmountForWS(info.getContractBailAmount()-info.getContractBailAmountForYS());//未收融资租赁保证金金额
				System.out.println("取贷款合同信息-------------(结束)");
			}
			
			//收款单
			if(info.getReceiveFormID()>0){
				System.out.println("取贷款收款通知单信息-------------(开始)");
				LoanAssureChargeFormDao loanAssureChargeFormDao=new LoanAssureChargeFormDao();
				LeaseholdPayNoticeInfo leaseholdPayNoticeInfo=new LeaseholdPayNoticeInfo();
				leaseholdPayNoticeInfo=loanAssureChargeFormDao.findPayNoticeByID(info.getReceiveFormID());
				
				info.setReceiveFormCode(leaseholdPayNoticeInfo.getCode());//收款通知单编号	只读	收款通知单信息
				info.setReceiveFormDate(leaseholdPayNoticeInfo.getExecuteDate());//收款日期	只读	收款通知单信息
				System.out.println("取贷款收款通知单信息-------------(结束)");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return info;

	}

	/**
	 * 根据状态查询的方法： 逻辑说明：
	 * 
	 * @param QueryByStatusConditionInfo ,
	 *            按状态查询的查询条件实体类。
	 * @return Collection ,包含TransReceiveFinanceInfo查询结果实体类的记录集
	 * @throws Exception
	 */
	public Collection findByStatus(QueryByStatusConditionInfo info)	throws Exception 
	{
		ArrayList arrResult = new ArrayList();
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try 
		{
			//String strSQL ="";
			StringBuffer buffer = new StringBuffer();
			//状态查询条件
			String query ="";
			if(info.getStatus()!=null)
			{
				query = getQueryString(info);
			}
			else
			{
				return arrResult;
			}
			
			if(con==null){
				System.out.println("得到数据库连接为空!");
			}else{
				System.out.println("得到数据库连接不为空!");
			}
			System.out.println("nOfficeID:"+info.getOfficeID()+":AAA");
			System.out.println("nCurrencyID:"+info.getCurrencyID()+":AAA");
			System.out.println("nTransActionTypeID:"+info.getTransactionTypeID()+":AAA");
			System.out.println("nInputUserID:"+info.getUserID()+":AAA");
			System.out.println("getTypeID:"+info.getTypeID()+":AAA");
			System.out.println("stauts:"+info.getStatus().toString()+":AAA");
			System.out.println("stauts:"+info.getDate()+":AAA");
			
			//排序条件
			String order = getOrderString(info);			
			//业务处理
			if (info.getTypeID() == 0) 
			{
				//处理时的查找不用加日期（因为有关机处理，暂存与未复核查全部就可以了）			
											
								
				buffer.append("select * \n");
				buffer.append("from Sett_TransReceiveFinance \n");
				buffer.append("where \n");
				buffer.append("nOfficeID=? \n");
				buffer.append("and nCurrencyID=? and nTransActionTypeID=? and \n");
				buffer.append("("+ query + ") \n");
				buffer.append("and nInputUserID=? \n");
				//buffer.append("order by ID \n");
				buffer.append(""+ order + "");
				ps = con.prepareStatement(buffer.toString());				
				log.info(buffer.toString());
				int index = 1;
				ps.setLong(index++, info.getOfficeID());
				ps.setLong(index++, info.getCurrencyID());
				ps.setLong(index++, info.getTransactionTypeID());				
				ps.setLong(index++, info.getUserID());
				
				rs = ps.executeQuery();
				
				int j=0;
				while (rs.next()) 
				{
					System.out.println("  取得第几条数据:"+(++j));
					TransReceiveFinanceInfo resultInfo = new TransReceiveFinanceInfo();
					
					resultInfo = getReceiveFinance(resultInfo, rs);
					arrResult.add(resultInfo);
				}
			} 
			else if (info.getTypeID() == 1) //业务复核
				{
				//处理时的查找不用加日期（因为有关机处理，暂存与未复核查全部就可以了）				
				
				buffer.append("select * \n");
				buffer.append("from Sett_TransReceiveFinance \n");
				buffer.append("where \n");
				buffer.append("nOfficeID=? \n");
				buffer.append("and nCurrencyID=? and nTransActionTypeID=? and \n");
				buffer.append("("+ query + ") \n");				
				buffer.append("and nCheckUserID=? and dtExecute=? \n");
				//buffer.append("order by ID \n");
				buffer.append(""+ order + "");

				ps = con.prepareStatement(buffer.toString());
				log.info(buffer.toString());				
				int index = 1;
				ps.setLong(index++, info.getOfficeID());
				ps.setLong(index++, info.getCurrencyID());
				ps.setLong(index++, info.getTransactionTypeID());				
				ps.setLong(index++, info.getUserID());
				ps.setTimestamp(index++, info.getDate());								

				rs = ps.executeQuery();
				while (rs.next()) 
				{
					TransReceiveFinanceInfo resultInfo = new TransReceiveFinanceInfo();
					
					resultInfo = getReceiveFinance(resultInfo, rs);
					arrResult.add(resultInfo);

				}
			}

		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			throw e;
		} finally 
		{
			try 
			{
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			}
			 catch (Exception e)
			 {
				e.printStackTrace();
				throw e;
			}
		}
		return arrResult;
	}

	private String getQueryString(QueryByStatusConditionInfo info)
	{

		String query;
		query = "nStatusID=";
		for (int i = 0; i < info.getStatus().length; i++) {
			if (i < info.getStatus().length - 1) {

				query = query + info.getStatus()[i] + " or nStatusID=";
			}
			else {
				query = query + info.getStatus()[i];
			}
		}
		return query;
	}

	//排序条件
	private String getOrderString(QueryByStatusConditionInfo info)
	{
		String order = "";
		boolean isNeedOrderBy = true;
		switch (info.getOrderByType())
		{
			case ORDERBY_TRANSNO :
			{
				order = " ORDER BY sTransNo ";	//交易号
			}
				break;
			case ORDERBY_CONTRACTID :
			{
				order = " ORDER BY nContractID ";	//贷款合同号
			}
				break;
			case ORDERBY_RECEIVEFORMID :
			{
				order = " ORDER BY NReceiveFormID ";		//收款通知单编号
			}
				break;
			case ORDERBY_PAYBAILACCOUNTID :
			{
				order = " ORDER BY nPayBailAccountID ";	//付保证金账户
			}
				break;
			case ORDERBY_RECEVICEBAILACCOUNTID :
			{
				order = " ORDER BY nReceviceBailAccountID ";	//收保证收款账户
			}
				break;
			case ORDERBY_BAILAMOUNT :
			{
				order = " ORDER BY mBailAmount ";	//收保证金金额
			}
				break;
			case ORDERBY_PAYPOUNDAGEACCOUNTID :
			{
				order = " ORDER BY nPayPoundageAccountID ";		//付手续费账户
			}
				break;
			case ORDERBY_POUNDAGGAMOUNT :			//手续费金额
			{
				order = " ORDER BY mPoundageAmount ";
			}
				break;
			case ORDERBY_INTERESTSTART :			//起息日
			{
				order = " ORDER BY dtInterestStart ";
			}
				break;
			case ORDERBY_EXECUTE :		//执行日	
			{
				order = " ORDER BY dtExecute ";
			}
				break;
			case ORDERBY_ABSTRACT :			//摘要
			{
				order = " ORDER BY SABSTRACT ";
			}
				break;
			case ORDERBY_STATUSID :			//状态
			{
				order = " ORDER BY NSTATUSID ";
			}
				break;
			default :
			{
				isNeedOrderBy = false;
			}
				break;
		}
		if (isNeedOrderBy) {
			if (info.isDesc())
				order = order + " DESC \n";
			else
				order = order + " ASC \n";
		}
		else {
			order = "ORDER BY ID DESC \n";
		}
		return order;
	}


	/**
	 * 复核匹配的方法： 逻辑说明：
	 * 
	 * @param TransReceiveFinanceInfo,融资租赁收款存款交易复核匹配查询条件实体类
	 * @return Collection ,包含TransReceiveFinanceInfo查询结果实体类的记录集
	 * @throws Exception
	 */
	public Collection match(TransReceiveFinanceInfo info) throws Exception
	{

		ArrayList arrResult = new ArrayList();
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			StringBuffer buffer = null;

			for(int i=0;i<10;i++)
				System.out.println("从页面传过来的利率为:"+info.getRate());
			/**
			 * 	融资租赁收款			基本信息：
			 * 承租单位编号						要不要，值得考虑一下
			 * 合同号ID				NCONTRACTID
			 * 收款通知单ID			NReceiveFormID
			 * 
			 * 保存金账户编号			nReceiveBailAccountID		
			 * 保证金来源-活期账户		nPayBailAccountID
			 * 保证金来源-银行收款		nPayBailBankID
			 * 保证金是否计息			nIsBailInterest
			 * 保证金的起息日			dtInterestStart
			 * 保证金金额				mBailAmount
			 * 
			 * 手续费来源-活期账户		nPayPoundageAccountID
			 * 手续费来源-银行收款		nPayPoundageBankID
			 * 手续费金额				mPoundageAmount
			 * 
			 * 收款执行日				dtExecute
			 * 
			 * 当前交易状态(未复核),录入人不是操作者 
			 */
			
			if (info.getTransactionTypeID() == SETTConstant.TransactionType.RECEIVEFINANCE) {
				buffer = new StringBuffer();
				buffer.append(" select * from Sett_TransReceiveFinance \n");
				buffer.append(" where nOfficeID=? and nCurrencyID=? and nStatusID=? \n");
				buffer.append(" and nInputUserID <>? and NCONTRACTID=? and \n");
				buffer.append(" nReceiveFormID=? and nReceiveBailAccountID=? and nPayBailAccountID=? and \n");
				buffer.append(" nPayBailBankID=?  \n");
				buffer.append(" and nIsBailInterest=? ");
				if(info.getIsBailInterest()>0){
					System.out.println("是要计息的.....");
					buffer.append(" and dtInterestStart=? and mRate =? and nInterestAccountID=? \n");
				}
				
				buffer.append(" and mBailAmount=? and nPayPoundageAccountID=? and nPayPoundageBankID=? \n");
				buffer.append(" and mPoundageAmount =? \n");
				buffer.append(" and dtExecute =? \n");
				buffer.append(" order by ID \n");

				ps = con.prepareStatement(buffer.toString());
				System.out.println(buffer.toString());

				int index = 1;
				ps.setLong(index++, info.getOfficeID());
				System.out.println("info.getOfficeID():" + info.getOfficeID());
				ps.setLong(index++, info.getCurrencyID());
				System.out.println("info.getCurrencyID():" + info.getCurrencyID());
				ps.setLong(index++, info.getStatusID());
				System.out.println("info.getStatusID():" + info.getStatusID());
				ps.setLong(index++, info.getInputUserID());
				System.out.println("info.getInputUserID():" + info.getInputUserID());
				ps.setLong(index++, info.getContractID());
				System.out.println("info.getContractID():" + info.getContractID());
				ps.setLong(index++, info.getReceiveFormID());
				System.out.println("info.getReceiveFormID():" + info.getReceiveFormID());
				ps.setLong(index++, info.getReceviceBailAccountID());
				System.out.println("info.getReceviceBailAccountID():" + info.getReceviceBailAccountID());
				ps.setLong(index++, info.getPayBailAccountID());
				System.out.println("info.getPayBailAccountID():" + info.getPayBailAccountID());
				ps.setLong(index++, info.getPayBailBankID());
				System.out.println("info.getPayBailBankID():" + info.getPayBailBankID());
				ps.setLong(index++, info.getIsBailInterest());
				System.out.println("info.getIsBailInterest():" + info.getIsBailInterest());				
				if(info.getIsBailInterest()>0){
					System.out.println("保证金是要计息的!");

					ps.setTimestamp(index++, info.getInterestStartDate());
					System.out.println("info.getInterestStartDate():" + info.getInterestStartDate());
					ps.setDouble(index++, info.getRate());
					System.out.println("info.getRate():" + info.getRate());
					ps.setLong(index++, info.getInterestAccountID());
					System.out.println("info.getInterestAccountID():" + info.getInterestAccountID());
				}
				
				ps.setDouble(index++, info.getBailAmount());
				System.out.println("info.getBailAmount():" + info.getBailAmount());
				ps.setLong(index++, info.getPayPoundageAccountID());
				System.out.println("info.getPayPoundageAccountID():" + info.getPayPoundageAccountID());
				ps.setLong(index++, info.getPayPoundageBankID());
				System.out.println("info.getPayPoundageBankID():" + info.getPayPoundageBankID());
				ps.setDouble(index++, info.getPoundageAmount());
				System.out.println("info.getPoundageAmount():" + info.getPoundageAmount());
				ps.setTimestamp(index++, info.getExecuteDate());
				System.out.println("info.getExecuteDate():" + info.getExecuteDate());
				
				rs = ps.executeQuery();
				while (rs.next()) {
					TransReceiveFinanceInfo depositInfo = new TransReceiveFinanceInfo();
					depositInfo = getReceiveFinance(depositInfo, rs);	
					arrResult.add(depositInfo);
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		finally {
			try {
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			}
			catch (Exception e) {
				e.printStackTrace();
				throw e;
			}

		}
		return arrResult;
	}
	
	/**
	 * 融资租赁收款继续功能： 逻辑说明：
	 * 
	 * 根据合同ID和收款通知单ID查找相应的贷款合同信息及收款通知单信息
	 * 
	 * @param lID
	 *            long , 交易的ID
	 * @return TransMarginOpenInfo, 融资租赁收款交易实体类
	 * @throws Exception
	 */
	public TransReceiveFinanceInfo next(TransReceiveFinanceInfo infoTemp) throws Exception
	{
		TransReceiveFinanceInfo info = new TransReceiveFinanceInfo();
		try {
			//合同
			if(infoTemp.getContractID()>0){
				System.out.println("取贷款合同信息-------------(开始)");
				ContractDao contractDao=new ContractDao();
				ContractInfo contractInfo=new ContractInfo();
				contractInfo=contractDao.findByID(infoTemp.getContractID());
				info.setContractID(infoTemp.getContractID());
				info.setContractCode(contractInfo.getContractCode());//合同编号
				info.setContractFinanceClientID(contractInfo.getBorrowClientID());//承租单位客户ID
				info.setContractFinanceClientCode(contractInfo.getBorrowClientCode());//承租单位客户编号			
				info.setContractFinanceClientName(contractInfo.getBorrowClientName());//承租单位客户名称
				info.setContractFinanceStartDate(contractInfo.getLoanStart());//租赁开始日期
				info.setContractFinanceEndDate(contractInfo.getLoanEnd());//租赁开始日期
				info.setContractFinanceTerm(contractInfo.getIntervalNum());//租凭期限
				info.setContractFinanceRate(contractInfo.getRate());//租赁利率
				
				info.setContractBailAmount(contractInfo.getRecognizanceAmount());//合同融资租赁 总保证金 金额
				info.setContractBailAmountForYS(contractInfo.getReceivedRecognizanceAmount());//已收融资租赁  保证金 累计金额
				info.setContractBailAmountForWS(info.getContractBailAmount()-info.getContractBailAmountForYS());//未收融资租赁保证金金额
				
				System.out.println("取贷款合同信息-------------(结束)");
			}
			
			//收款单
			if(infoTemp.getReceiveFormID()>0){
				System.out.println("取贷款收款通知单信息-------------(开始)");
				LoanAssureChargeFormDao loanAssureChargeFormDao=new LoanAssureChargeFormDao();
				LeaseholdPayNoticeInfo leaseholdPayNoticeInfo=new LeaseholdPayNoticeInfo();
				leaseholdPayNoticeInfo=loanAssureChargeFormDao.findPayNoticeByID(infoTemp.getReceiveFormID());
				info.setReceiveFormID(infoTemp.getReceiveFormID());
				info.setReceiveFormCode(leaseholdPayNoticeInfo.getCode());//收款通知单编号	只读	收款通知单信息
				info.setReceiveFormDate(leaseholdPayNoticeInfo.getExecuteDate());//收款日期	只读	收款通知单信息
				
				info.setIsBailInterest(leaseholdPayNoticeInfo.getIsInteRest());		//收款单中 保证金是否计息
				info.setRate(leaseholdPayNoticeInfo.getRate());						//收款单中 保证金的利率
				info.setBailAmount(leaseholdPayNoticeInfo.getRecognizanceAmount());	//收款单中 保证金的金额
				info.setPoundageAmount(leaseholdPayNoticeInfo.getAssureChargeAmount());		//收款单中 手续费的金额
				info.setReceviceBailAccountID(leaseholdPayNoticeInfo.getRecrecognizanceAccountId());//收款单中 收保证金的账户
				System.out.println("取贷款收款通知单信息收保证金账户ID1111"+leaseholdPayNoticeInfo.getRecrecognizanceAccountId());
				System.out.println("取贷款收款通知单信息收保证金账户ID2222"+info.getReceviceBailAccountID());
				info.setPayBailAccountID(leaseholdPayNoticeInfo.getRecognizanceAccountId());//收款单中 付保证金的账户
				info.setPayPoundageAccountID(leaseholdPayNoticeInfo.getAssureChargeAccountId());//收款单中 付手续费账户
				
				System.out.println("取贷款收款通知单信息-------------(结束)");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		finally {
			
		}
		return info;
	}
	
	/**
	 * 检查收款通知单的状态是否正常
	 * @param lPayFormID
	 * @return
	 * @throws SQLException
	 */
	public boolean checkPayForm(long lReceiveFormID,long lStatusToCheck)throws SQLException{
		boolean blnIsOK=false;
		Connection conn=null;
		try{
			Log.print("校验放款通知单状态......");
			Log.print("放款通知单ID:"+lReceiveFormID);
			conn = this.getConnection();
			PreparedStatement ps = null;
			ResultSet rs = null;
			String strSql = "select statusid from LOAN_ASSURECHARGEFORM where id="+lReceiveFormID
				+" and statusid="+lStatusToCheck;
			ps = conn.prepareStatement(strSql);
			rs = ps.executeQuery();
			if (rs.next()){
				blnIsOK = true;
			}
			this.cleanup(rs);
			this.cleanup(ps);
		}catch(SQLException sqlExp){throw sqlExp;}
		finally{
			this.cleanup(conn);
		}
		return blnIsOK;
	}

	public void updateLoanReceiveFormStatus(long id, long statusID) throws SQLException
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		try
		{
			conn = this.getConnection();
			pstmt = conn.prepareStatement("update " + "LOAN_ASSURECHARGEFORM set statusid=? where id=?");
			pstmt.setLong(1, statusID);
			pstmt.setLong(2, id);
			pstmt.executeUpdate();

		}
		finally
		{
			this.cleanup(pstmt);
			this.cleanup(conn);
		}
	}
	
	public static void main(String []args){
		Sett_TransReceiveFinanceDao dao=new Sett_TransReceiveFinanceDao();

		TransReceiveFinanceInfo info=new TransReceiveFinanceInfo();
		info.setTransactionTypeID(SETTConstant.TransactionType.RECEIVEFINANCE);
		
		info.setContractID(1000092);
		
		/*
		Collection c=null;
		try {
			c = dao.match(info);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(c==null){
			System.out.println("得到的数据为空!");
			
		}else{
			System.out.println("得到的数据不为空!"+c.size());
			
		}
		*/
		
				
		/*
		ArrayList arrResult = new ArrayList();
		
		QueryByStatusConditionInfo info=new QueryByStatusConditionInfo();
		info.setCurrencyID(1);
		info.setOfficeID(1);
		info.setTransactionTypeID(401);
		long status[]={1,2};
		info.setStatus(status);
		info.setTypeID(0);
		info.setUserID(1);

				try {
			arrResult=(ArrayList)dao.findByStatus(info);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(arrResult!=null ){
			System.out.println("不为空");
		}else{
			System.out.println("为空");
		}
		*/
	}

}