package com.iss.itreasury.settlement.interest.bizlogic;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.Iterator;

import com.iss.itreasury.loan.contract.dataentity.ContractInfo;
import com.iss.itreasury.loan.loanpaynotice.dataentity.LoanPayNoticeInfo;
import com.iss.itreasury.loan.overdueapply.dataentity.OverDueInfo;
import com.iss.itreasury.settlement.account.dao.Sett_AccountDAO;
import com.iss.itreasury.settlement.account.dao.Sett_AccountTypeDAO;
import com.iss.itreasury.settlement.account.dao.Sett_SubAccountDAO;
import com.iss.itreasury.settlement.account.dao.sett_TransAccountDetailDAO;
import com.iss.itreasury.settlement.account.dataentity.AccountInfo;
import com.iss.itreasury.settlement.account.dataentity.AccountTypeInfo;
import com.iss.itreasury.settlement.account.dataentity.SubAccountAssemblerInfo;
import com.iss.itreasury.settlement.account.dataentity.SubAccountCurrentInfo;
import com.iss.itreasury.settlement.account.dataentity.SubAccountFixedInfo;
import com.iss.itreasury.settlement.account.dataentity.SubAccountLoanInfo;
import com.iss.itreasury.settlement.account.dataentity.TransAccountDetailInfo;
import com.iss.itreasury.settlement.interest.dao.Sett_CompoundInterestSettingDAO;
import com.iss.itreasury.settlement.interest.dao.Sett_DailyAccountBalanceDAO;
import com.iss.itreasury.settlement.interest.dataentity.BankInterestAdjustInfo;
import com.iss.itreasury.settlement.interest.dataentity.ClearLoanAccountInterestConditionInfo;
import com.iss.itreasury.settlement.interest.dataentity.CurrentDepositAccountInterestInfo;
import com.iss.itreasury.settlement.interest.dataentity.DailyAccountBalanceInfo;
import com.iss.itreasury.settlement.interest.dataentity.InterestAccountIDInfo;
import com.iss.itreasury.settlement.interest.dataentity.InterestCalculationParameterInfo;
import com.iss.itreasury.settlement.interest.dataentity.InterestRate;
import com.iss.itreasury.settlement.interest.dataentity.InterestsInfo;
import com.iss.itreasury.settlement.interest.dataentity.LiborInterestAdjustInfo;
import com.iss.itreasury.settlement.interest.dataentity.LoanAccountInterestInfo;
import com.iss.itreasury.settlement.transloan.dataentity.TransRepaymentLoanInfo;
import com.iss.itreasury.settlement.transmargindeposit.dao.Sett_TransOpenMarginDepositDAO;
import com.iss.itreasury.settlement.transmargindeposit.dataentity.QueryByStatusConditionInfo;
import com.iss.itreasury.settlement.transmargindeposit.dataentity.TransMarginOpenInfo;
import com.iss.itreasury.settlement.util.NameRef;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.settlement.util.UtilOperation;
import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log4j;
import com.iss.itreasury.util.RecordAccountInfo;
/**
 * @author Liu huijun
 * Date of Creation    2003-11-06
 * **********结息处理业务接口，主要实现的功能包括****************************
 * 01:查询账户的收付息账户ID：查询账户的收付息账户ID，结息记账时用。
 * 02:活期/协定账户结息：活期单户结息时，更新子账户表中当前累计利息、上一结息日等信息。
 * 03:活期/协定账户结息反交易：活期和贷款账户单户结息记录删除、取消复核时，更新子账户表。
 * 04:贷款账户结息/费用。
 * 05:贷款账户结息/费用反交易。
 * 06:单户计息倒填处理。
 * 07:单户结息倒填处理。
 * 
 * **********批交易业务接口，主要实现的功能包括××××××××××××××××××××××××××××××
 * 08:贷款复利计算日期处理
 * 09:账户余额日结
 * 10:每日算息
 * 11:起息日倒填处理
 * 12:结息日倒填处理
 * 13:存款银行利率倒填处理
 * 14:贷款银行利率倒填处理         
 */

public class InterestBatch
{

	public final static long LOAN_ACCOUNT_HASOVERDUED = 9;
	//日志
	private Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);
	private InterestCalculation interestCalculation = null;
	private Sett_DailyAccountBalanceDAO sett_DailyAccountBalanceDAO = null;
	private Connection conn_Out = null;
	/**
	 * 构造方法一，自己创建一个Connection.
	 * 
	 */
	public InterestBatch()
	{
		try
		{
			conn_Out = Database.getConnection();
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		interestCalculation = new InterestCalculation(conn_Out);
		sett_DailyAccountBalanceDAO = new Sett_DailyAccountBalanceDAO(conn_Out);
	}

	/**
	 * 构造方法二，需要传递一个连接Connection
	 * 
	 * @param conn
	 */

	public InterestBatch(Connection connection)
	{
		this.conn_Out = connection;
		interestCalculation = new InterestCalculation(connection);
		sett_DailyAccountBalanceDAO = new Sett_DailyAccountBalanceDAO(connection);
	}

	/**
	 * added by mzh_fu 为解决内存泄露问题所加（慎用）
	 * 
	 * 关闭数据库连接
	 * @throws IException
	 */
	public void closeConnection() throws IException {
		try {
			if (conn_Out != null && conn_Out.isClosed() == false)
				conn_Out.close();
		} catch (SQLException e) {
			throw new IException(e.getMessage());
		}
	}
	public static void main(String[] args)
	{
		/**		
		Connection con = null;
				try
				{
					con = Database.getConnection();
				}
				catch (Exception sqle)
				{
					sqle.printStackTrace();
				}		

		//	//Timestamp date1 = new Timestamp(101, 1, 27, 0, 0, 0, 0);
		//	//	Timestamp date2 = new Timestamp(101, 5, 45, 0, 0, 0, 0);
		//
		//		System.out.println("日期date1是: " + date1.toString());
		//		System.out.println("日期date2是：" + date2.toString());
		
		//System.out.println("start。。。。。。。。");
		
		//InterestBatch interestBatch = new InterestBatch();
		//
		//Timestamp tsCloseDate = Env.getSystemDate(1,1);
		//System.out.println("tsCloseDate = "+tsCloseDate);
		//tsCloseDate = DataFormat.getPreviousDate(tsCloseDate);
		//
		//System.out.println("tsCloseDate = "+tsCloseDate);
				InterestBatch interestBatch = new InterestBatch();
				Timestamp tsCloseDate = Env.getSystemDate(1,1);
				tsCloseDate = DataFormat.getPreviousDate(tsCloseDate);
				System.out.println("日期tsCloseDate是：" + tsCloseDate);
		try {
			interestBatch.dailyCalculateInterest(con,1,1,tsCloseDate);
		}
		catch (IException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
	}
	/**
		* 接口名称：查询账户收付息账户ID
		* 功能描述：查询账户的收付息账户ID，结息记账时用。
		* @param accountID       主账户ID
		* @param SubAccountID    子账户ID    
		* @param InterestType    费用类型 1：利息，2：担保费，3：手续费，4：复利，5：罚息
		* @return InterestAccountID 该对象含有两个属性：付息（借方）账户ID,收息（贷方）账户ID.
		* @throws Exception 
		*/
	public InterestAccountIDInfo getInterestAccountID(long AccountID, long SubAccountID, long InterestType) throws IException
	{
		log.info("lhj debug info == 主账户AccountID" + AccountID);
		log.info("lhj debug info == 子账户SubAccountID" + SubAccountID);
		log.info("lhj debug info == 利息类型InterestType" + InterestType);
		InterestAccountIDInfo interestAccountID = null;
		long sett_AccountTypeID = -1; //主账户sett_Account表中的账户类型ID
		long ac_interestAccountID = -1; //活期子账户中的收利息账户ID
		long PayInterestAccountID = -1; //付息（借方）账户ID
		long ReceiveInterestAccountID = -1; //收息（贷方）账户ID
		Connection conn = this.conn_Out;
		//根据AccountID查找主账户记录。
		Sett_AccountDAO sett_AccountDAO = new Sett_AccountDAO(conn);
		AccountInfo accountInfo = null;
		try
		{
			accountInfo = sett_AccountDAO.findByID(AccountID);
			if (accountInfo == null)
			{
				throw new IException(true, "主账户表中没有对应记录，交易失败", null);
			}

		}
		catch (IException ie)
		{
			ie.printStackTrace();
			throw ie;
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException(true, "无法在主账户表中找到对应的信息，交易失败", null);
		}
		if (accountInfo != null)
		{
			sett_AccountTypeID = accountInfo.getAccountTypeID();
		}
		//根据SubAccountID查找对应的子账户记录
		Sett_SubAccountDAO sett_SubAccountDAO = new Sett_SubAccountDAO(conn);
		SubAccountAssemblerInfo subAccountAssemblerInfo = null;
		try
		{
			subAccountAssemblerInfo = sett_SubAccountDAO.findByID(SubAccountID);
			if (subAccountAssemblerInfo == null)
			{
				throw new IException(true, "子账户表中没有对应记录，交易失败", null);
			}

		}
		catch (IException ie)
		{
			ie.printStackTrace();
			throw ie;
		}
		catch (SQLException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
			throw new IException(true, "无法在子账户表中找到对应的信息，交易失败", null);
		}
		//账户类型由主账户中取出
        log.debug("---------判断账户类型------------");
        Sett_AccountTypeDAO sett_AccountTypeDAO = new Sett_AccountTypeDAO();
        AccountTypeInfo accountTypeInfo = null;
        try {
			accountTypeInfo = sett_AccountTypeDAO.findByID(sett_AccountTypeID);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (accountTypeInfo != null) {
			//根据账户类型ID进行不同的处理	
			log.info("lhj -------------sett_AccountTypeID ==" + sett_AccountTypeID);
			//--------定期账户和贴现账户不进行记息---------------------------------------------------------
			if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.FIXED ||
				accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.NOTIFY || 
				accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.DISCOUNT)
			{
				PayInterestAccountID = -1;
				ReceiveInterestAccountID = -1;
			}
			//---------对于活期账户--------------------------------------------------------------------     
			else if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CURRENT || 
				accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.OTHERDEPOSIT || 
				accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.BAK ||
				accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.RESERVE ||
				accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.MARGIN)
			{
				if (subAccountAssemblerInfo != null)
				{
					ac_interestAccountID = subAccountAssemblerInfo.getSubAccountCurrenctInfo().getInterestAccountID();
					log.debug("子账户是：["+SubAccountID + "] 的ac_interestAccountID = "+ac_interestAccountID);
					if (ac_interestAccountID < 1)
					{
						PayInterestAccountID = -1;
						//收息账户ID即为主账户ID
						ReceiveInterestAccountID = AccountID;
					}
					else
					{
						PayInterestAccountID = -1;
						//收息账户ID是子账户中的收利息账户ID
						ReceiveInterestAccountID = ac_interestAccountID;
					}
				}
			}
			//---------对于拆借账户--------------------------------------------------------------------     
			else if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.LENDING)
			{
				if (subAccountAssemblerInfo != null)
				{
					ac_interestAccountID = subAccountAssemblerInfo.getSubAccountCurrenctInfo().getInterestAccountID();
					log.debug("子账户是：["+SubAccountID + "] 的ac_interestAccountID = "+ac_interestAccountID);
					//拆借账户的付息账户是共用的活期账户收息账户字段
					PayInterestAccountID = ac_interestAccountID;
				}
			}
			//---------对于贷款账户，根据不同的费用类型，查询账户的收复息账户ID------------------------
			else if  (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.TRUST || 
				accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CONSIGN || 
				accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.OTHERLOAN ||
				accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.YT)
			{
				if (InterestType == SETTConstant.InterestFeeType.INTEREST)
				{
					//费用类型是1：利息
					if (subAccountAssemblerInfo != null)
					{
						//付息账户ID是子账户中贷款方付息账户ID			
						PayInterestAccountID = subAccountAssemblerInfo.getSubAccountLoanInfo().getPayInterestAccountID();
						//收息账户ID是子账户中委托方收息账户ID
						ReceiveInterestAccountID = subAccountAssemblerInfo.getSubAccountLoanInfo().getReceiveInterestAccountID();
					}
				}
				if (InterestType == SETTConstant.InterestFeeType.ASSURE)
				{
					//费用类型是2：担保费
					if (subAccountAssemblerInfo != null)
					{
						//付息账户ID是子账户中担保费账户ID
						PayInterestAccountID = subAccountAssemblerInfo.getSubAccountLoanInfo().getPaySuretyAccountID();
						//收息账户ID,2003-12-11   
						ReceiveInterestAccountID = subAccountAssemblerInfo.getSubAccountLoanInfo().getReceiveSuretyAccountID();
	
					}
				}
				if (InterestType == SETTConstant.InterestFeeType.COMMISION)
				{
					//费用类型是3：手续费
					if (subAccountAssemblerInfo != null)
					{
						//付息账户ID是子账户中手续费账户ID						
						PayInterestAccountID = subAccountAssemblerInfo.getSubAccountLoanInfo().getCommissionAccountID();
						ReceiveInterestAccountID = -1;
					}
				}
				if (InterestType == SETTConstant.InterestFeeType.COMPOUNDINTEREST)
				{
					//费用类型是4：复利
					if (subAccountAssemblerInfo != null)
					{
						//付息账户ID是子账户中贷款方付息账户ID			
						PayInterestAccountID = subAccountAssemblerInfo.getSubAccountLoanInfo().getPayInterestAccountID();
						//收息账户ID是子账户中委托方收息账户ID
						ReceiveInterestAccountID = subAccountAssemblerInfo.getSubAccountLoanInfo().getReceiveInterestAccountID();
					}
				}
				if (InterestType == SETTConstant.InterestFeeType.FORFEITINTEREST)
				{
					//费用类型是5：罚息
					if (subAccountAssemblerInfo != null)
					{
						//付息账户ID是子账户中贷款方付息账户ID			
						PayInterestAccountID = subAccountAssemblerInfo.getSubAccountLoanInfo().getPayInterestAccountID();
						//收息账户ID是子账户中委托方收息账户ID
						ReceiveInterestAccountID = subAccountAssemblerInfo.getSubAccountLoanInfo().getReceiveInterestAccountID();
					}
				}
			}
		}
		
		//生成返回对象
		//注意：第一个属性是付息（借方）账户ID,第二个属性是收息（贷方）账户ID       
		interestAccountID = new InterestAccountIDInfo(PayInterestAccountID, ReceiveInterestAccountID);
		log.debug(" getInterestAccountID() == 付息（借方）账户ID" + interestAccountID.getPayInterestAccountID());
		log.debug(" getInterestAccountID() == 收息（贷方）账户ID" + interestAccountID.getReceiveInterestAccountID());
		return interestAccountID;
	}

	/**
	 * 接口名称：活期账户/协定账户结息。
	 * 功能描述： 活期账户单户结息时候，更新子账户表中当前累计利息，上一结息日等信息。
	 * 注   意1：定期、通知、贴现账户不准调用此接口！
	 * 注   意2：活期结息不允许部分结息和豁免利息。
	 * @param AccountID        主账户ID
	 * @param SubAccountID     子账户ID
	 * @param interestDate     结息日
	 * @param PecisionInterest 应收/付利息
	 * @param NogotiatePecisionInterest 应收/付协定利息
	 * @return long -1:不成功，1：成功
	 * @throws IException
	 * 
	 * 修改历史：加入保证金账户结息，jason,2006-04-10
	 */

	/*lhj 2003-11-26 需要增加Connection !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!11*/
	public long clearCurrentDepositAccountInterest(long AccountID, long SubAccountID, Timestamp interestDate, double PrecisionInterest, double NegotiatePrecisionInterest) throws IException
	{
		log.info("interestBatch(322)lhj debug info == 进入活期账户/协定账户/保证金账户 结息......");

		Connection conn = this.conn_Out;

		long isSuccess = -1;
		//根据主账户AccountID在sett_Account表中定位主账户记录
		Sett_AccountDAO sett_AccountDAO = new Sett_AccountDAO(conn);
		AccountInfo accountInfo = null;
		try
		{
			accountInfo = sett_AccountDAO.findByID(AccountID);
			log.info("---0---");
			if (accountInfo == null)
			{
				throw new IException(true, "主账户表中没有对应记录，交易失败", null);
			}
		}
		catch (IException ie)
		{
			ie.printStackTrace();
			throw ie;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IException(true, "无法在主账户表中找到对应的信息，交易失败", null);
		}
		log.info("interestBatch(288)lhj debug info == AccountID是："+ AccountID);
		log.info("interestBatch(288)lhj debug info == SubAccountID是："+ SubAccountID); 
		log.info("interestBatch(288)lhj debug info == PecisionInterest是："+ PrecisionInterest);
		log.info("interestBatch(288)lhj debug info == NegotiatePecisionInterest是："+ NegotiatePrecisionInterest);   

		if (accountInfo != null)
		{
			//账户类型由主账户中取出
	        log.debug("---------判断账户类型------------");
			long accountTypeID = accountInfo.getAccountTypeID();
	        Sett_AccountTypeDAO sett_AccountTypeDAO = new Sett_AccountTypeDAO();
	        AccountTypeInfo accountTypeInfo = null;
	        
	        try 
	        {
				accountTypeInfo = sett_AccountTypeDAO.findByID(accountTypeID);
			} 
	        catch (SQLException e) 
	        {
				e.printStackTrace();
			}
	        
			if (accountTypeInfo != null) 
			{
				//本接口只针对于活期账户，即只负责下列账户类型：	
				//备付金、准备金、拆借账户的处理方式同活期 modify by bingliu 20110721
				if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CURRENT ||
					accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.OTHERDEPOSIT ||
					accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.BAK ||
					accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.RESERVE ||
					accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.LENDING ||
					accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.MARGIN)
				{
					//根据子账户SubAccountID在sett_SubAccount表中定位子账户记录。
					Sett_SubAccountDAO sett_SubAccountDAO = new Sett_SubAccountDAO(conn);
					SubAccountAssemblerInfo subAccountAssemblerInfo = null;
					try
					{
						subAccountAssemblerInfo = sett_SubAccountDAO.findByID(SubAccountID);
						if (subAccountAssemblerInfo == null)
						{
							throw new IException(true, "子账户表中没有对应记录，交易失败", null);
						}
					}
					catch (IException ie)
					{
						ie.printStackTrace();
						throw ie;
					}
					catch (SQLException e1)
					{
						e1.printStackTrace();
						throw new IException(true, "无法在子账户表中找到对应的信息，交易失败", null);
					}
					if (subAccountAssemblerInfo != null)
					{
						//boxu add 增加临时存放计提金额和计提日期的方法
						SubAccountFixedInfo subAccountFixedInfo = subAccountAssemblerInfo.getSubAccountFixedInfo();
						
						//活期账户
						SubAccountCurrentInfo subAccountCurrentInfo = subAccountAssemblerInfo.getSubAccountCurrenctInfo();
						
						subAccountFixedInfo.setPreDrawInterest(subAccountCurrentInfo.getDrawInterest());  //计提利息
						subAccountFixedInfo.setPreDrawDate(subAccountCurrentInfo.getPreDrawDate());  //计提日期
						
						double interest = 0.0;  //结息后利息
						double negotiateInterest = 0.0;  //结息后协定利息
						//结息前的正常利息
						double oldInterest = subAccountCurrentInfo.getInterest();
						//结息前的协定利息
						double oldNegotiateInterest = subAccountCurrentInfo.getNegotiateInterest();
						long isNegotiate = -1;  //是否是协定存款
						
						log.info("活期结息前的正常利息是:" + oldInterest);
						log.info("活期结息前的的协定利息是:" + oldNegotiateInterest);
						log.info("活期结息前的结息日期是:" + subAccountCurrentInfo.getClearInterestDate());
	
						//正常利息的结息
						interest = UtilOperation.Arith.sub(oldInterest, PrecisionInterest);
						
						//是否协定存款
						isNegotiate = subAccountCurrentInfo.getIsNegotiate();
	
						if (isNegotiate == SETTConstant.BooleanValue.ISTRUE)
						{
							negotiateInterest = UtilOperation.Arith.sub(oldNegotiateInterest, NegotiatePrecisionInterest);
						}
	
						log.info("interestBatch(368)lhj debug info == 结息后的正常利息是:" + interest);
						log.info("interestBatch(368)lhj debug info == 结息后的协定利息是:" + negotiateInterest);
						log.info("interestBatch(366)lhj debug info == 结息日期是:" + interestDate);
						//将结息后的当前利息和当前协定利息回写入子账户对象中
						subAccountCurrentInfo.setInterest(interest);
						subAccountCurrentInfo.setNegotiateInterest(negotiateInterest);
						//更新结息日期
						subAccountCurrentInfo.setClearInterestDate(interestDate);
						
						//以前活期没有计提操作,现在增加了活期的计提操作,所以结息后清空计提利息和计提日期(在会计分录中增加"计提利息")
						subAccountCurrentInfo.setDrawInterest(0.0);
						//2008年1月28日 计提日期修改保存为结息日
						subAccountCurrentInfo.setPreDrawDate(interestDate);

						try
						{
							sett_SubAccountDAO.updateSubAccountFix(subAccountFixedInfo);
							if (sett_SubAccountDAO.updateSubAccountCurrent(subAccountCurrentInfo) > -1)
							{
								//Modify by chuanliu,for margin subaccount's status(finish)
								//结息后将保证金账户状态改为"已结清"
								if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.MARGIN)
								{
									if (subAccountCurrentInfo.getBalance() < 0.01 && interest < 0.01)
									{
										sett_SubAccountDAO.updateFinishDateAndStatus(subAccountCurrentInfo.getID(), SETTConstant.SubAccountStatus.FINISH, interestDate);
									}
								}
								
								log.info("活期/保证金结息完成，更新子账户成功！");
								isSuccess = 1;
							}
						}
						catch (SQLException e2)
						{
							e2.printStackTrace();
							throw new IException("Gen_E001");
						}catch(Exception e3)
					    {
						    e3.printStackTrace();
							throw new IException("Gen_E001");
						}
					}
				}
			}
		}

		return isSuccess;
	}

	/**
	 * 接口名称：活期账户/协定账户结息反交易。
	 * 功能描述：活期账户单户结息记录删除、取消复核时，调用此接口更新子账户表。
	 * 注   意1：不含贴现户。
	 * @param AccountID     主账户ID
	 * @param SubAccountID  子账户ID
	 * @param LastInterestDate 上一结息日
	 * @param PrecisionInterest 应收/付利息
	 * @param NegotiatePrecisionInterest 应收/付协定利息
	 * @return long -1:不成功，1：成功
	 * @throws IException
	 */

	public long clearCurrentDepositAccountInterestReverse(long AccountID, long SubAccountID, Timestamp LastInterestDate, double PrecisionInterest, double NegotiatePrecisionInterest) throws IException
	{
		log.info("==============活期账户/协定账户结息反交易==================");
		Connection conn = this.conn_Out;
		long isSuccess = -1;
		//根据主账户AccountID在sett_Account表中定位主账户记录
		Sett_AccountDAO sett_AccountDAO = new Sett_AccountDAO(conn);
		AccountInfo accountInfo = null;
		try
		{
			accountInfo = sett_AccountDAO.findByID(AccountID);
			if (accountInfo == null)
			{
				throw new IException(true, "主账户表中没有对应记录，交易失败", null);
			}
		}
		catch (IException ie)
		{
			ie.printStackTrace();
			throw ie;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IException(true, "无法在主账户表中找到对应的信息，交易失败", null);
		}
		if (accountInfo != null)
		{
	        log.debug("---------判断账户类型------------");
			long accountTypeID = accountInfo.getAccountTypeID();
	        Sett_AccountTypeDAO sett_AccountTypeDAO = new Sett_AccountTypeDAO();
	        AccountTypeInfo accountTypeInfo = null;
	        
	        try 
	        {
				accountTypeInfo = sett_AccountTypeDAO.findByID(accountTypeID);
			}
	        catch (SQLException e) 
	        {
				e.printStackTrace();
			}
			
			if (accountTypeInfo != null) 
			{
				//本接口只针对于活期账户，即只负责下列账户类型：			加一个保证金类型 add by wjliu--2007-4-9
				if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CURRENT ||
					accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.OTHERDEPOSIT ||
					accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.BAK ||
					accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.RESERVE ||
					accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.LENDING ||
					accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.MARGIN) 
				{
					//根据子账户SubAccountID在sett_SubAccount表中定位子账户记录。
					Sett_SubAccountDAO sett_SubAccountDAO = new Sett_SubAccountDAO(conn);
					SubAccountAssemblerInfo subAccountAssemblerInfo = null;
					try
					{
						subAccountAssemblerInfo = sett_SubAccountDAO.findByID(SubAccountID);
						if (subAccountAssemblerInfo == null)
						{
							throw new IException(true, "子账户表中没有对应记录，交易失败", null);
						}
	
					}
					catch (IException ie)
					{
						ie.printStackTrace();
						throw ie;
					}
					catch (SQLException e1)
					{
						e1.printStackTrace();
						throw new IException(true, "无法在子账户表中找到对应的信息，交易失败", null);
					}
					if (subAccountAssemblerInfo != null)
					{
						//活期取回以前的计提利息和计提日期
						SubAccountFixedInfo subAccountFixedInfo = subAccountAssemblerInfo.getSubAccountFixedInfo();
						
						//活期账户
						SubAccountCurrentInfo subAccountCurrentInfo = subAccountAssemblerInfo.getSubAccountCurrenctInfo();
						//反结息前的相关信息
						double interest = 0.0; //反结息后的正常利息
						double negotiateInterest = 0.0; //反结息后的协定利息
						double oldInterest = 0.0; //反结息前的正常利息
						double oldNegotiateInterest = 0.0; //反结息前的协定利息
						Timestamp oldClearInterestDate = null; //反结息前的结息日期
						long isNegotiate = -1; //是否是协定存款

						//反结息前的正常利息
						oldInterest = subAccountCurrentInfo.getInterest();
						//反结息前的协定利息
						oldNegotiateInterest = subAccountCurrentInfo.getNegotiateInterest();
						//反结息前的结息日期
						oldClearInterestDate = subAccountCurrentInfo.getClearInterestDate();
	
						//进行反结息....................................................
	
						//正常利息的反结息
						interest = UtilOperation.Arith.add(oldInterest, PrecisionInterest);
						//协定利息的结息
						isNegotiate = subAccountCurrentInfo.getIsNegotiate();
						if (isNegotiate == SETTConstant.BooleanValue.ISTRUE)
						{
							//协定利息的反结息
							negotiateInterest = UtilOperation.Arith.add(oldNegotiateInterest, NegotiatePrecisionInterest);
						}

						log.info("================================================================================");
						log.info("活期反结息前的正常利息是:" + oldInterest);
						log.info("活期反结息前的协定利息是:" + oldNegotiateInterest);
						log.info("活期反结息前的结息日期是:" + oldClearInterestDate);
						log.info("================================================================================");
						log.info("活期反结息后的结息日期是:" + LastInterestDate);
						log.info("活期反结息后的正常利息是:" + interest);
						log.info("活期反结息后的协定利息是:" + negotiateInterest);
						log.info("================================================================================");
	
						//将恢复上一次结息时的当前利息和当前协定利息回写入子账户对象中
						subAccountCurrentInfo.setInterest(interest);
						subAccountCurrentInfo.setNegotiateInterest(negotiateInterest);
						//结息日期更新为上一次结息日
						subAccountCurrentInfo.setClearInterestDate(LastInterestDate);
						
						//计提利息
						subAccountCurrentInfo.setDrawInterest(subAccountFixedInfo.getPreDrawInterest());
						//计提日期
						subAccountCurrentInfo.setPreDrawDate(subAccountFixedInfo.getPreDrawDate());
						
						try
						{
							//清楚临时存放的计提利息和计提日期
							subAccountFixedInfo.setPreDrawInterest(0.0);
							subAccountFixedInfo.setPreDrawDate(null);
							sett_SubAccountDAO.updateSubAccountFix(subAccountFixedInfo);
							
							if (sett_SubAccountDAO.updateSubAccountCurrent(subAccountCurrentInfo) > -1)
							{
								//Modify by chuanliu,for margin subaccount's status(NORMAL)
								//结息后将保证金账户状态改为"未结清"
								if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.MARGIN)
								{
									if (subAccountCurrentInfo.getBalance() != 0 || interest > 0.01)
									{
										sett_SubAccountDAO.updateFinishDateAndStatus(subAccountCurrentInfo.getID(), SETTConstant.SubAccountStatus.NORMAL, null);
									}
								}
								
								isSuccess = 1;
								log.info("lhj debug info == 反结息的更新子账户成功！！");
							}
						}
						catch (SQLException e2)
						{
							e2.printStackTrace();
							throw new IException("Gen_E001");
						}catch(Exception e3)
					    {
						    e3.printStackTrace();
							throw new IException("Gen_E001");
						}
						
					}
				}
			}
		}
		
		return isSuccess;
	}

	/**
	  * 接口名称：贷款账户结息/费用
		* 功能描述：贷款账户单户结息时，将子账户当前累计利息清零，对部分结息，当前累计利息减去已结利息，子账户
		*           一结息日修改为本次结息日。
		* 注   意1：只针对贷款账户，不含贴现户。
		* 注   意2：定期，通知、贴现账户不允许调用本接口。
		* 注   意3: lhj 2004-06-03 根据董春莉修改文档，对该方法进行修改
		*           以下在♂♂和♀♀之间的为过去的代码						 
		* @param  ClearLoanAccountInterestConditionInfo 贷款账户结息/费用条件对象
		* @return long -1:不成功，1：成功
		* @throws IException
		*/

	public long clearLoanAccountInterest(ClearLoanAccountInterestConditionInfo clearLoanAccountInterestConditionInfo) throws IException
	{
		log.print("lhj debug info 开始进行贷款结息-------------------------------------------------------------");
		log.debug(UtilOperation.dataentityToString(clearLoanAccountInterestConditionInfo));
		Connection conn = this.conn_Out;
		long AccountID = clearLoanAccountInterestConditionInfo.getAccountID();
		//主账户ID
		long SubAccountID = clearLoanAccountInterestConditionInfo.getSubAccountID();
		//子账户ID
		Timestamp InterestDate = clearLoanAccountInterestConditionInfo.getInterestDate();
		//结息日
		double Interest = clearLoanAccountInterestConditionInfo.getInterest();
		//应付利息
		double InterestReceivable = clearLoanAccountInterestConditionInfo.getInterestReceivable();
		//应付计提利息
		double SuretyFee = clearLoanAccountInterestConditionInfo.getSuretyFee();
		//应付担保费
		double Commision = clearLoanAccountInterestConditionInfo.getCommision();
		//应付手续费
		double CompoundInterest = clearLoanAccountInterestConditionInfo.getCompoundInterest();
		//应付复利
		double OverDueInterest = clearLoanAccountInterestConditionInfo.getOverDueInterest();
		//应付逾期罚息
		double RealInterest = clearLoanAccountInterestConditionInfo.getRealInterest();
		//实付利息
		double RealInterestReceivable = clearLoanAccountInterestConditionInfo.getRealInterestReceivable();
		//实付计提利息
		double RealSuretyFee = clearLoanAccountInterestConditionInfo.getRealSuretyFee();
		//实付担保费
		double RealCommision = clearLoanAccountInterestConditionInfo.getRealCommision();
		//实付手续费
		double RealCompoundInterest = clearLoanAccountInterestConditionInfo.getRealCompoundInterest();
		//实付复利
		double RealOverDueInterest = clearLoanAccountInterestConditionInfo.getRealOverDueInterest();
		//实付逾期罚息
		long isRemitInterest = clearLoanAccountInterestConditionInfo.getIsRemitInterest();
		//是否免还剩余利息
		long isRemitSuretyFee = clearLoanAccountInterestConditionInfo.getIsRemitSuretyFee();
		//是否免还剩余担保费
		long isRemitCommision = clearLoanAccountInterestConditionInfo.getIsRemitCommision();
		// 是否免还剩余手续费
		long isRemitCompoundInterest = clearLoanAccountInterestConditionInfo.getIsRemitCompoundInterest();
		//是否免还剩余复利
		long isRemitOverDueInterest = clearLoanAccountInterestConditionInfo.getIsRemitOverDueInterest();
		//是否免还剩余逾期罚息

		long isSuccess = -1;
		//根据AccountID在sett_account表中定位主账户记录
		Sett_AccountDAO sett_AccountDAO = new Sett_AccountDAO(conn);
		AccountInfo accountInfo = null;
		if (Interest > 0 && CompoundInterest > 0 && OverDueInterest > 0)
		{
			log.print("lhj debug info 贷款收回，或者利息支付，[利息＝复利＝罚息]全部>0!!!");

		}
		else
		{
			if (Interest > 0)
			{
				log.print("lhj debug info 利息＝结息");
			}
			if (CompoundInterest > 0)
			{
				log.print("lhj debug info 复利＝结息");
			}
			if (OverDueInterest > 0)
			{
				log.print("lhj debug info 罚息＝结息");
			}
		}

		try
		{
			accountInfo = sett_AccountDAO.findByID(AccountID);
			if (accountInfo == null)
			{
				throw new IException(true, "主账户表中没有对应记录，交易失败", null);
			}
		}
		catch (IException ie)
		{
			ie.printStackTrace();
			throw ie;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IException(true, "无法在主账户表中找到对应的信息，交易失败", null);
		}
		if (accountInfo != null)
		{
			//办事处
			long officeID = accountInfo.getOfficeID();
			//币种
			long currencyID = accountInfo.getCurrencyID();
			//当前开关机日期
			Timestamp closeDate = null;
			closeDate = Env.getSystemDate(conn, officeID, currencyID);
	        log.debug("---------判断账户类型------------");
			long accountTypeID = accountInfo.getAccountTypeID();
	        Sett_AccountTypeDAO sett_AccountTypeDAO = new Sett_AccountTypeDAO();
	        AccountTypeInfo accountTypeInfo = null;
	        
	        try 
	        {
				accountTypeInfo = sett_AccountTypeDAO.findByID(accountTypeID);
			} 
	        catch (SQLException e) 
	        {
				e.printStackTrace();
			}
	        
			if (accountTypeInfo != null) 
			{
				//本接口只应用于贷款账户：
				if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.TRUST || 
					accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CONSIGN || 
					accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.DISCOUNT || 
					accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.OTHERLOAN || 
					accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.YT) //短期贷款
				{
					//根据SubAccountID在sett_SubAccount表中定位一条相应记录
					Sett_SubAccountDAO sett_SubAccountDAO = new Sett_SubAccountDAO(conn);
					SubAccountAssemblerInfo subAccountAssemblerInfo = null;
	
					try
					{
						subAccountAssemblerInfo = sett_SubAccountDAO.findByID(SubAccountID);
						if (subAccountAssemblerInfo == null)
						{
							throw new IException(true, "子账户表中没有对应记录，交易失败", null);
						}
					}
					catch (IException ie)
					{
						ie.printStackTrace();
						throw ie;
					}
					catch (SQLException e1)
					{
						e1.printStackTrace();
						throw new IException(true, "无法在子账户表中找到对应的信息，交易失败", null);
					}
	
					if (subAccountAssemblerInfo != null)
					{
						log.print("lhj debug infp 贷款结息前各个属性--------------------------------------------------------");
						log.print("lhj debug info 贷款要结息的应付利息是  :" + Interest);
						log.print("lhj debug info 贷款要结息的实付利息是  :" + RealInterest);
						if (isRemitInterest == SETTConstant.BooleanValue.ISTRUE){
							log.print("lhj debug info =====================   :免收剩余利息！！ ");
						}
						log.print("lhj debug info 贷款要结息的应付复利是  :" + CompoundInterest);
						log.print("lhj debug info 贷款要结息的实付复利是  :" + RealCompoundInterest);
						if (isRemitCompoundInterest == SETTConstant.BooleanValue.ISTRUE){
							log.print("lhj debug info =====================   :免收剩余复利！！ ");
						}
						log.print("lhj debug info 贷款要结息的应付手续费是:" + Commision);
						log.print("lhj debug info 贷款要结息的实付手续费是:" + RealCommision);
						if (isRemitCommision == SETTConstant.BooleanValue.ISTRUE){
							log.print("lhj debug info =====================   :免收剩余手续费！！ ");
						}
						log.print("lhj debug info 贷款要结息的应付罚息是  :" + OverDueInterest);
						log.print("lhj debug info 贷款要结息的实付罚息是  :" + RealOverDueInterest);
						if (isRemitOverDueInterest == SETTConstant.BooleanValue.ISTRUE){
							log.print("lhj debug info =====================   :免收剩余逾期罚息！！ ");
						}
						log.print("lhj debug info 贷款要结息的应付担保费是:" + SuretyFee);
						log.print("lhj debug info 贷款要结息的实付担保费是:" + RealSuretyFee);
						if (isRemitSuretyFee == SETTConstant.BooleanValue.ISTRUE){
							log.print("lhj debug info =====================   :免收剩余担保费！！ ");
						}
						log.print("lhj debug info 贷款要结息的应付计提利息:" + InterestReceivable);
						log.print("lhj debug info 贷款要结息的实付计提利息:" + RealInterestReceivable);
						log.print("lhj debug info 贷款要结息的结息日期是  :" + InterestDate);
						log.print("lhj debug infp 贷款结息前各个属性--------------------------------------------------------");
						//贷款账户
						SubAccountLoanInfo subAccountLoanInfo = subAccountAssemblerInfo.getSubAccountLoanInfo();
						//活期账户（2003-12-12 贮备费用利息。）
						SubAccountCurrentInfo subAccountCurrentInfo = subAccountAssemblerInfo.getSubAccountCurrenctInfo();
						//定期账户（2004-03-03 缓存逾期欠息）
						SubAccountFixedInfo subAccountFixedInfo = subAccountAssemblerInfo.getSubAccountFixedInfo();
						//根据参数值，更新相应的结息日期
						
                        //上次计提日期 modify by xwhe 2009-03-23
						Timestamp al_dtpredraw = subAccountLoanInfo.getPreDrawDate();
						/**
						if (Interest > 0 ){
							subAccountLoanInfo.setClearInterestDate(InterestDate);
						}
						if (SuretyFee > 0){
							subAccountLoanInfo.setClearSureFeeDate(InterestDate);
						}
						if (Commision > 0){
							subAccountLoanInfo.setClearCommissionDate(InterestDate);
						}
						if (CompoundInterest > 0){
							subAccountLoanInfo.setClearCompoundDate(InterestDate);
						}
						if (OverDueInterest > 0 ){	
							subAccountLoanInfo.setClearOverDueDate(InterestDate);
						}
						*/
						
						//Boxu Update 2009年02月16日 针对于单独结算手续费不改变结息日修改
						//利息
						if(Interest > 0)  //应付利息大于零
						{
							if (isRemitInterest == SETTConstant.BooleanValue.ISTRUE)  //免收剩余利息
							{
								subAccountLoanInfo.setClearInterestDate(InterestDate);
								subAccountLoanInfo.setPreDrawDate(InterestDate);
							}
							if (UtilOperation.Arith.round(Interest,2) == UtilOperation.Arith.round(RealInterest,2))  //实付利息等于应付利息
							{
								subAccountLoanInfo.setClearInterestDate(InterestDate);
								subAccountLoanInfo.setPreDrawDate(InterestDate);
							}
						}
						
						//担保费
						if (SuretyFee > 0)
						{
							if (isRemitSuretyFee == SETTConstant.BooleanValue.ISTRUE)
							{
								subAccountLoanInfo.setClearSureFeeDate(InterestDate);
							}
							if (UtilOperation.Arith.round(SuretyFee,2) == UtilOperation.Arith.round(RealSuretyFee,2))
							{
								subAccountLoanInfo.setClearSureFeeDate(InterestDate);
							}
						}
						
						//手续费
						if (Commision > 0)
						{
							if (isRemitCommision == SETTConstant.BooleanValue.ISTRUE)
							{
								subAccountLoanInfo.setClearCommissionDate(InterestDate);
							}
							if (UtilOperation.Arith.round(Commision,2) == UtilOperation.Arith.round(RealCommision,2))
							{
								subAccountLoanInfo.setClearCommissionDate(InterestDate);
							}
						}
						
						//复利
						if (CompoundInterest > 0)
						{
							if (isRemitCompoundInterest == SETTConstant.BooleanValue.ISTRUE)
							{
								subAccountLoanInfo.setClearCompoundDate(InterestDate);
							}
							if (UtilOperation.Arith.round(CompoundInterest,2) == UtilOperation.Arith.round(RealCompoundInterest,2))
							{
								subAccountLoanInfo.setClearCompoundDate(InterestDate);
							}
						}
						
						//罚息
						if (OverDueInterest > 0 )
						{
							if (isRemitOverDueInterest == SETTConstant.BooleanValue.ISTRUE)
							{
								subAccountLoanInfo.setClearOverDueDate(InterestDate);
							}
							if (UtilOperation.Arith.round(OverDueInterest,2) == UtilOperation.Arith.round(RealOverDueInterest,2))
							{
								subAccountLoanInfo.setClearOverDueDate(InterestDate);
							}
						}
	
						//定义临时变量
						double tmpInterest                 = subAccountLoanInfo.getInterest(); //利息
						double tmpPreDrawInterest          = subAccountLoanInfo.getPreDrawInterest(); //计提利息
						double tmpSuretyFee                = subAccountLoanInfo.getSuretyFee(); //担保费
						double tmpCommission               = subAccountLoanInfo.getCommission(); //手续费
						double tmpCompoundInterest         = subAccountLoanInfo.getCompoundInterest(); //复利
						double tmpOverDueInterest          = subAccountLoanInfo.getOverDueInterest(); //罚息
						double tmpArrearageInterest        = subAccountLoanInfo.getArrearageInterest(); //累计欠息					
						double tmpOverDueArrearageInterest = subAccountLoanInfo.getOverDueArrearageInterest(); //逾期欠息
	
						/*-----暂时将结息前的这liu个利息值保存在AC_属性中------*/
						if (tmpArrearageInterest > 0)
						{
							subAccountCurrentInfo.setCapitalLimitAmount(tmpArrearageInterest); //累计欠息对应于资金限制金额
						}
						if (SuretyFee > 0){
							subAccountCurrentInfo.setFirstLimitAmount(tmpSuretyFee); //担保费对应第一透支金额
						}
						if (Commision > 0){
							subAccountCurrentInfo.setSecondLimitAmount(tmpCommission); //手续费对应第二透支金额
						}
						if (CompoundInterest > 0){
							subAccountCurrentInfo.setThirdLimitAmount(tmpCompoundInterest); //复利对应第三透支金额
						}
						if (OverDueInterest > 0){
							subAccountCurrentInfo.setMonthLimitAmount(tmpOverDueInterest); //罚息对应月度透支金额
						}
						subAccountCurrentInfo.setNegotiateInterest(tmpPreDrawInterest); //计提利息对应协定利息
//						if (tmpOverDueArrearageInterest > 0)
//						{
//							if (Interest > 0)
//							{
//								subAccountCurrentInfo.setNegotiateAmount(tmpOverDueArrearageInterest); //逾期欠息对应于协定余额 2004-01-013	
//								subAccountCurrentInfo.setNegotiateUnit(tmpOverDueArrearageInterest);
//								subAccountFixedInfo.setPreDrawInterest(tmpOverDueArrearageInterest);
//							}
//							if (CompoundInterest > 0)
//							{
//								subAccountCurrentInfo.setNegotiateUnit(tmpOverDueArrearageInterest);
//								subAccountFixedInfo.setPreDrawInterest(tmpOverDueArrearageInterest);
//							}
//							if (OverDueInterest > 0)
//							{
//								subAccountFixedInfo.setPreDrawInterest(tmpOverDueArrearageInterest);
//							}
//						}
						/*-----暂时将结息前的这liu个利息值保存在AC_属性中------*/
						log.print("===========================================================================");
						log.print("lhj debug infp 子账户中各个属性--------------------------------------------------------");
						log.print("lhj debug info 子账户中的正常利息是 :" + tmpInterest);
						log.print("lhj debug info 子账户中的计提利息是 :" + tmpPreDrawInterest);
						log.print("lhj debug info 子账户中的复利是     :" + tmpCompoundInterest);
						log.print("lhj debug info 子账户中的手续费是   :" + tmpCommission);
						log.print("lhj debug info 子账户中的担保费是   :" + tmpSuretyFee);
						log.print("lhj debug info 子账户中的罚息是     :" + tmpOverDueInterest);
						log.print("lhj debug info 子账户中的累计欠息是 :" + tmpArrearageInterest);
						log.print("lhj debug info 子账户中的逾期欠息是 :" + tmpOverDueArrearageInterest);
						log.print("lhj debug info 贷款的结息日期是     :" + InterestDate);
						log.print("===========================================================================");
						//放款通知单ID
						long loanNoteID = subAccountLoanInfo.getLoanNoteID();
						UtilOperation utilOperation = new UtilOperation(conn);
						//判断是否已经逾期
						//OverDueInfo overDueInfo = utilOperation.getOverDueInfoByLoanNoteID(loanNoteID);
						//是否计收复利！
						//long isCompoundInterest = -1;
						//是否已经逾期
						//long isOverDue = -1;
						
						log.print("判断是否逾期，是否计收复利..................................");
						/*if (overDueInfo != null && overDueInfo.getLoanPayID() > 0)
						{
							isOverDue = 1; //已经逾期！！
							isCompoundInterest = overDueInfo.getIsCompoundInterest();
							if (isCompoundInterest == Constant.YesOrNo.YES)
							{
								log.print("【【【【【【【【【【【逾期－－－计收复利－－－】】】】】】】】】】");
							}
							else
							{
								log.print("〖〖〖〖〖〖〖〖〖〖〖逾期－－－不计收复利－－－〗〗〗〗〗〗〗〗〗");
							}
						}*/
						
						//结息，包含结清利息和部分结清利息
						log.print("开始正式进行结息或者利息费用支♂♂♂♂♂♂♂♂♂♂♂♂♂♂♂♂♂♂♂♂♂♂♂♂♂♂♂♂♂♂♂♂♂♂♂♂♂♂♂♂♂♂♂♂");
						if (Interest > 0)
						{
							double oddInterest         = 0.0;
							double oddCompoundInterest = 0.0;  //结利息后的剩余复利
							double oddForFeitInterest  = 0.0;  //结利息后的剩余罚息
							
							//Boxu Update 2008年4月23日 修改免还的逻辑
							if ((UtilOperation.Arith.round(Interest, 2) > UtilOperation.Arith.round(RealInterest, 2)) && isRemitInterest == SETTConstant.BooleanValue.ISTRUE)
							{
								tmpInterest = UtilOperation.Arith.sub(tmpInterest, Interest);
							}
							//else if (UtilOperation.Arith.round(Interest, 2) == UtilOperation.Arith.round(RealInterest, 2))
							//{
							//	tmpInterest = UtilOperation.Arith.sub(tmpInterest, Interest);
						    //}
							else
							{
								tmpInterest = UtilOperation.Arith.sub(tmpInterest, RealInterest);
						    }
							
							/**
							 * Boxu Update 2009年02月16日
							 * 子账户利息 - 实付利息（有进位和没有进位两种情况） = 剩余利息
							 * 进位，剩余利息为负数，现系统做了处理
							 * 不进位，剩余利息会有一个小于0.01的正数
							 * 所以做下列处理
							 */
							//if(tmpInterest < 0)
							if(tmpInterest < 0.01)
							{
								tmpInterest = 0.0;
							}
						
							//Boxu Update 2008年4月23日 计提利息
							if (UtilOperation.Arith.round(InterestReceivable, 2) > UtilOperation.Arith.round(RealInterestReceivable, 2) && isRemitInterest == SETTConstant.BooleanValue.ISTRUE)
							{
								tmpPreDrawInterest = UtilOperation.Arith.sub(tmpPreDrawInterest, InterestReceivable);
							}
							else
							{
								tmpPreDrawInterest = UtilOperation.Arith.sub(tmpPreDrawInterest, RealInterestReceivable);
							}
							
							if(tmpPreDrawInterest < 0)
							{
								tmpPreDrawInterest = 0.0;
							}
							
							//临时保存计提日期
							if (subAccountLoanInfo.getPreDrawDate() != null)
							{
							//	subAccountFixedInfo.setPreDrawDate(subAccountLoanInfo.getPreDrawDate());
								subAccountFixedInfo.setPreDrawDate(al_dtpredraw);
								
							}
						}

						//计提利息（不豁免，未结清部分保留在数据库中以备日后冲销）RealInterestReceivable
						/*if (InterestReceivable > 0)
						{
							if (isRemitInterest == SETTConstant.BooleanValue.ISTRUE)
							{
								//tmpPreDrawInterest = UtilOperation.Arith.sub(tmpPreDrawInterest, InterestReceivable);
								tmpPreDrawInterest = 0.0;
							}
							else
							{
								tmpPreDrawInterest = UtilOperation.Arith.sub(tmpPreDrawInterest, RealInterestReceivable);
							}
						}*/
							
						//担保费累计未结余额
						if (SuretyFee > 0)
						{
							log.print("开始结担保费..............................start.....");
							if ((UtilOperation.Arith.round(SuretyFee, 2) == UtilOperation.Arith.round(RealSuretyFee, 2)) || isRemitSuretyFee == SETTConstant.BooleanValue.ISTRUE)
								tmpSuretyFee = 0;
							else
								tmpSuretyFee = UtilOperation.Arith.sub(SuretyFee, RealSuretyFee);
							log.print("开始结担保费..............................end.....");
						}
						
						//手续费累计未结余额
						if (Commision > 0)
						{
							log.print("开始结手续费..............................start.....");
							if ((UtilOperation.Arith.round(Commision, 2) == UtilOperation.Arith.round(RealCommision, 2)) || isRemitCommision == SETTConstant.BooleanValue.ISTRUE)
								tmpCommission = 0;
							else
								tmpCommission = UtilOperation.Arith.sub(Commision, RealCommision);
							log.print("开始结手续费..............................end.....");
						}
						
						//复利累计未结余额
						if (CompoundInterest > 0 )
						{
							log.print("开始结复利..............................start.....");
							if ((UtilOperation.Arith.round(CompoundInterest, 2) == UtilOperation.Arith.round(RealCompoundInterest, 2)) || isRemitCompoundInterest == SETTConstant.BooleanValue.ISTRUE){
								tmpCompoundInterest = 0;
							}else{
								tmpCompoundInterest = UtilOperation.Arith.sub(tmpCompoundInterest, RealCompoundInterest);
							}
						}
	
						//逾期罚息累计未结余额
						if (OverDueInterest > 0 )
						{
							if (isRemitOverDueInterest == SETTConstant.BooleanValue.ISTRUE)
							{
								//tmpOverDueInterest = 0.0;
								tmpOverDueInterest = UtilOperation.Arith.sub(subAccountLoanInfo.getOverDueInterest(), OverDueInterest);
							}
							else
							{
								log.print("开始结逾期罚息..............................start.....");
								/*if ((UtilOperation.Arith.round(OverDueInterest, 2) == UtilOperation.Arith.round(RealOverDueInterest, 2))) {
									tmpOverDueInterest = 0.0;
								}
								else {
									tmpOverDueInterest = UtilOperation.Arith.sub(OverDueInterest, RealOverDueInterest);
									
								}*/
								tmpOverDueInterest = UtilOperation.Arith.sub(subAccountLoanInfo.getOverDueInterest(), RealOverDueInterest);
								
								log.print("开始结逾期罚息..............................end.....");
							}

						}
						
						
						
						LoanPayNoticeInfo loanPayNoticeInfo = utilOperation.findLoanPayNoticeByID(loanNoteID);
						if(loanPayNoticeInfo.getIsRemitCompoundInterest()==Constant.TRUE)
						{
							if(!InterestDate.before(closeDate))
							{
								//利息费用支付及结息还息情况冲减累计欠息
								//正常交易还息是需要判断是否需要冲减累计欠息
								InterestsInfo preinterestsInfo = null;
								InterestsInfo todayinterestsInfo = null;
								double payInterest = 0;
								double payCompoundInterest = 0;
								double payOverDueInterest = 0;
								try
								{
									preinterestsInfo = utilOperation.findPayInterestAmount(subAccountAssemblerInfo.getSubAccountLoanInfo().getID(),closeDate);
									todayinterestsInfo = utilOperation.findAllClearCompoundInterset(subAccountAssemblerInfo.getSubAccountLoanInfo().getID(),closeDate);
									payInterest = preinterestsInfo.getPayminterest()+todayinterestsInfo.getPayminterest();
									payCompoundInterest = preinterestsInfo.getPaymcompoundinterest()+todayinterestsInfo.getPaymcompoundinterest();
									payOverDueInterest = preinterestsInfo.getPaymforfeitinterest()+todayinterestsInfo.getPaymforfeitinterest();
								}
								catch(Exception e)
								{
									throw new IException(e.getMessage());
								}
								if(Interest>0)
								{
									if(preinterestsInfo.getMinterest()-payInterest>RealInterest)
									{
										tmpArrearageInterest = UtilOperation.Arith.sub(tmpArrearageInterest ,RealInterest);
									}
									else if(preinterestsInfo.getMinterest()-payInterest<RealInterest&&preinterestsInfo.getMinterest()>payInterest)
									{
										tmpArrearageInterest = UtilOperation.Arith.sub(tmpArrearageInterest ,UtilOperation.Arith.sub(preinterestsInfo.getMinterest(),payInterest));
									}
									else
									{
										tmpArrearageInterest = 0;
									}
								}
								if(CompoundInterest>0)
								{
									if(preinterestsInfo.getMcompoundinterest()-payCompoundInterest>RealCompoundInterest)
									{
										tmpArrearageInterest =UtilOperation.Arith.sub( tmpArrearageInterest ,RealCompoundInterest);
									}
									else if(preinterestsInfo.getMcompoundinterest()-payCompoundInterest<RealCompoundInterest&&preinterestsInfo.getMcompoundinterest()>payCompoundInterest)
									{
										tmpArrearageInterest = UtilOperation.Arith.sub(tmpArrearageInterest ,UtilOperation.Arith.sub(preinterestsInfo.getMcompoundinterest(),payCompoundInterest));
									}
									else
									{
										tmpArrearageInterest = 0;
									}
								
								}
								if(OverDueInterest>0)
								{
									if(preinterestsInfo.getMforfeitinterest()-payOverDueInterest>RealOverDueInterest)
									{
										tmpArrearageInterest =UtilOperation.Arith.sub( tmpArrearageInterest ,RealOverDueInterest);
									}
									else if(preinterestsInfo.getMforfeitinterest()-payOverDueInterest<RealOverDueInterest&&preinterestsInfo.getMforfeitinterest()>payOverDueInterest)
									{
										tmpArrearageInterest = UtilOperation.Arith.sub(tmpArrearageInterest ,UtilOperation.Arith.sub(preinterestsInfo.getMforfeitinterest(),payOverDueInterest));
									}
									else
									{
										tmpArrearageInterest = 0;
									}
								}
							}
							else
							{
								//查询当日还款情况
								InterestsInfo returnInfo  = interestCalculation.calculationLoanAccountCompoundInterest(conn, officeID, currencyID, AccountID, SubAccountID, InterestDate, closeDate, RealCompoundInterest, SETTConstant.InterestFeeType.COMPOUNDINTEREST);
								//根据当日还款交易重新计算当日复利累计
								tmpCompoundInterest = returnInfo.getMcompoundinterest() ;
								tmpArrearageInterest =returnInfo.getArrearageInterest() ;
							}
							if(tmpArrearageInterest<0.01)
							{
								tmpArrearageInterest = 0;
							}
						}
						//为贷款账户对象赋值
						subAccountLoanInfo.setInterest(tmpInterest);
						subAccountLoanInfo.setSuretyFee(tmpSuretyFee);
						subAccountLoanInfo.setPreDrawInterest(tmpPreDrawInterest);
						subAccountLoanInfo.setCommission(tmpCommission);
						subAccountLoanInfo.setCompoundInterest(tmpCompoundInterest);
						subAccountLoanInfo.setOverDueInterest(tmpOverDueInterest);
						subAccountLoanInfo.setArrearageInterest(tmpArrearageInterest);
						//2004-1-29
						subAccountLoanInfo.setOverDueArrearageInterest(tmpOverDueArrearageInterest);
						
						//Boxu add 2007-6-27 修改子账户计提日期
						//Boxu update 2009-2-13 移动到判断是否变更结息日之后
						//subAccountLoanInfo.setPreDrawDate(InterestDate);
						
						log.print(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
						log.print("lhj debug infp 贷款结息后各个属性--------------------------------------------------------");
						log.print("lhj debug info 贷款结息后的正常利息是:" + tmpInterest);
						log.print("lhj debug info 贷款结息后的计提利息是:" + tmpPreDrawInterest);
						log.print("lhj debug info 贷款结息后的复利是    :" + tmpCompoundInterest);
						log.print("lhj debug info 贷款结息后的手续费是  :" + tmpCommission);
						log.print("lhj debug info 贷款结息后的担保费是  :" + tmpSuretyFee);
						log.print("lhj debug info 贷款结息后的罚息是    :" + tmpOverDueInterest);
						log.print("lhj debug info 贷款结息后的累计欠息是:" + tmpArrearageInterest);
						log.print("lhj debug info 贷款结息后的逾期欠息是:" + tmpOverDueArrearageInterest);
						log.print("lhj debug info 贷款的结息日期是      :" + InterestDate);
						log.print("lhj debug infp 贷款结息后各个属性--------------------------------------------------------");
	
						//update子账户表sett_SubAccount
						try
						{
							//2003-12-12 在活期账户中保存四种费用
							subAccountCurrentInfo.setInterest(tmpInterest);
							sett_SubAccountDAO.updateSubAccountCurrent(subAccountCurrentInfo);
							sett_SubAccountDAO.updateSubAccountFix(subAccountFixedInfo);
							if (sett_SubAccountDAO.updateSubAccountLoan(subAccountLoanInfo) > -1)
							{
								isSuccess = 1;
							}
						}
						catch (SQLException e2)
						{
							e2.printStackTrace();
							throw new IException("Gen_E001");
						}catch(Exception e3)
					    {
						    e3.printStackTrace();
							throw new IException("Gen_E001");
						}
					}
				}
			}
		}
		return isSuccess;

	}

	/**
	 * 接口名称：贷款账户结息/费用反交易
	 * 功能描述：贷款账户单户结息/费用反交易时，恢复子账户当前累计利息，上一结息日等信息为结息前的信息。
	 * 注   意1：只针对贷款账户，不含贴现账户。
	 * 注   意2：定期，通知、贴现账户不允许调用此接口。 
	 * @param  ClearLoanAccountInterestConditionInfo 贷款账户结息/费用条件对象
	 * @return long -1:不成功，1：成功
	 * @throws IException
	 */
	/*lhj 2003-11-26 需要增加Connection !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!11*/
	public long clearLoanAccountInterestReverse(ClearLoanAccountInterestConditionInfo clearLoanAccountInterestConditionInfo) throws IException
	{
		//log.info("lhj debug infp 开始进行贷款反结息-------------------------------------------------------------");
		log.debug(UtilOperation.dataentityToString(clearLoanAccountInterestConditionInfo));
		Connection conn = this.conn_Out;
		long AccountID = clearLoanAccountInterestConditionInfo.getAccountID(); //主账户ID
		long SubAccountID = clearLoanAccountInterestConditionInfo.getSubAccountID(); //子账户ID
		Timestamp LastInterestDate = clearLoanAccountInterestConditionInfo.getInterestDate(); //上一结息日
		log.info("lhj debug info 上一结息日 = " + LastInterestDate + " !!!!!!!!!!!!!!!!");
		double Interest = clearLoanAccountInterestConditionInfo.getInterest(); //应付利息
		double InterestReceivable = clearLoanAccountInterestConditionInfo.getInterestReceivable(); //应付计提利息
		double SuretyFee = clearLoanAccountInterestConditionInfo.getSuretyFee(); //应付担保费
		double Commision = clearLoanAccountInterestConditionInfo.getCommision(); //应付手续费
		double CompoundInterest = clearLoanAccountInterestConditionInfo.getCompoundInterest(); //应付复利
		double OverDueInterest = clearLoanAccountInterestConditionInfo.getOverDueInterest(); //应付逾期罚息
		double RealInterest = clearLoanAccountInterestConditionInfo.getRealInterest(); //实付利息
		double RealInterestReceivable = clearLoanAccountInterestConditionInfo.getRealInterestReceivable(); //实付计提利息
		double RealSuretyFee = clearLoanAccountInterestConditionInfo.getRealSuretyFee(); //实付担保费
		double RealCommision = clearLoanAccountInterestConditionInfo.getRealCommision(); //实付手续费
		double RealCompoundInterest = clearLoanAccountInterestConditionInfo.getRealCompoundInterest(); //实付复利
		double RealOverDueInterest = clearLoanAccountInterestConditionInfo.getRealOverDueInterest(); //实付逾期罚息
		long isRemitInterest = clearLoanAccountInterestConditionInfo.getIsRemitInterest(); //是否免还剩余利息
		long isRemitSuretyFee = clearLoanAccountInterestConditionInfo.getIsRemitSuretyFee(); //是否免还剩余担保费
		long isRemitCommision = clearLoanAccountInterestConditionInfo.getIsRemitCommision(); // 是否免还剩余手续费
		long isRemitCompoundInterest = clearLoanAccountInterestConditionInfo.getIsRemitCompoundInterest(); //是否免还剩余复利
		long isRemitOverDueInterest = clearLoanAccountInterestConditionInfo.getIsRemitOverDueInterest(); //是否免还剩余逾期罚息

		long isSuccess = -1;

		//根据AccountID在sett_account表中定位主账户记录
		Sett_AccountDAO sett_AccountDAO = new Sett_AccountDAO(conn);
		AccountInfo accountInfo = null;
		try
		{
			accountInfo = sett_AccountDAO.findByID(AccountID);
			if (accountInfo == null)
			{
				throw new IException(true, "主账户表中没有对应记录，交易失败", null);
			}
		}
		catch (IException ie)
		{
			ie.printStackTrace();
			throw ie;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IException(true, "无法在主账户表中找到对应的信息，交易失败", null);
		}
		if (accountInfo != null)
		{
	        log.debug("---------判断账户类型------------");
			long accountTypeID = accountInfo.getAccountTypeID();
	        Sett_AccountTypeDAO sett_AccountTypeDAO = new Sett_AccountTypeDAO();
	        AccountTypeInfo accountTypeInfo = null;
	        
	        try 
	        {
				accountTypeInfo = sett_AccountTypeDAO.findByID(accountTypeID);
			}
	        catch (SQLException e) 
			{
				e.printStackTrace();
			}
			
			if (accountTypeInfo != null) 
			{
				//本接口只应用于贷款账户：
				if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.TRUST || 
					accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CONSIGN || 
					accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.DISCOUNT || 
					accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.OTHERLOAN || 
					accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.YT) //短期贷款
				{
					//根据SubAccountID在sett_SubAccount表中定位一条相应记录
					Sett_SubAccountDAO sett_SubAccountDAO = new Sett_SubAccountDAO(conn);
					SubAccountAssemblerInfo subAccountAssemblerInfo;
					try
					{
						subAccountAssemblerInfo = sett_SubAccountDAO.findByID(SubAccountID);
						if (subAccountAssemblerInfo == null)
						{
							throw new IException(true, "子账户表中没有对应记录，交易失败", null);
						}
					}
					catch (IException ie)
					{
						ie.printStackTrace();
						throw ie;
					}
					catch (SQLException e1)
					{
						// TODO Auto-generated catch block
						e1.printStackTrace();
						throw new IException(true, "无法在子账户表中找到对应的信息，交易失败", null);
					}
					if (subAccountAssemblerInfo != null)
					{
						log.info("lhj debug info 贷款反结息前各个属性--------------------------------------------------------");
						log.info("lhj debug info == 上一结息日是：" + LastInterestDate);
	
						log.info("lhj debug info 贷款反结息前的应付利息是  :" + Interest);
						log.info("lhj debug info 贷款反结息前的实付利息是  :" + RealInterest);
						log.info("lhj debug info 贷款反结息前的应付复利是  :" + CompoundInterest);
						log.info("lhj debug info 贷款反结息前的实付复利是  :" + RealCompoundInterest);
						log.info("lhj debug info 贷款反结息前的应付手续费是:" + Commision);
						log.info("lhj debug info 贷款反结息前的实付手续费是:" + RealCommision);
						log.info("lhj debug info 贷款反结息前的应付逾期罚息:" + OverDueInterest);
						log.info("lhj debug info 贷款反结息前的实付逾期罚息:" + RealOverDueInterest);
						log.info("lhj debug info 贷款反结息前的应付担保费是:" + SuretyFee);
						log.info("lhj debug info 贷款反结息前的实付担保费是:" + RealSuretyFee);
						log.info("lhj debug info 贷款反结息前的应付计提利息:" + InterestReceivable);
						log.info("lhj debug info 贷款反结息前的实付计提利息:" + RealInterestReceivable);
	
						log.info("lhj debug infp 贷款反结息前各个属性--------------------------------------------------------");
	
						//贷款账户
						SubAccountLoanInfo subAccountLoanInfo = subAccountAssemblerInfo.getSubAccountLoanInfo();
						//活期账户（2004-01-14 保存着结息前的六种费用利息值）
						SubAccountCurrentInfo subAccountCurrentInfo = subAccountAssemblerInfo.getSubAccountCurrenctInfo();
						//定期账户（2004-03-03 保存着逾期欠息）
						SubAccountFixedInfo subAccountFixedInfo = subAccountAssemblerInfo.getSubAccountFixedInfo();
	
						//根据参数值，更新相应的结息日期
						if (Interest > 0&&UtilOperation.Arith.round(Interest,2)==UtilOperation.Arith.round(RealInterest,2))
							subAccountLoanInfo.setClearInterestDate(LastInterestDate);
						if (SuretyFee > 0)
							subAccountLoanInfo.setClearSureFeeDate(LastInterestDate);
						if (Commision > 0)
							subAccountLoanInfo.setClearCommissionDate(LastInterestDate);
						if (OverDueInterest > 0&&UtilOperation.Arith.round(OverDueInterest,2)==UtilOperation.Arith.round(RealOverDueInterest,2))
							subAccountLoanInfo.setClearOverDueDate(LastInterestDate);
						if (CompoundInterest > 0&&UtilOperation.Arith.round(CompoundInterest,2)==UtilOperation.Arith.round(RealCompoundInterest,2))
							subAccountLoanInfo.setClearCompoundDate(LastInterestDate);
						//定义临时变量
						double tmpInterest = subAccountLoanInfo.getInterest();
						double tmpPreDrawInterest = subAccountLoanInfo.getPreDrawInterest();
						double tmpSuretyFee = subAccountLoanInfo.getSuretyFee();
						double tmpCommission = subAccountLoanInfo.getCommission();
						double tmpCompoundInterest = subAccountLoanInfo.getCompoundInterest();
						double tmpOverDueInterest = subAccountLoanInfo.getOverDueInterest();
						double tmpArrearageInterest = subAccountLoanInfo.getArrearageInterest();
						//逾期欠息
						double tmpOverDueArrearageInterest = subAccountLoanInfo.getOverDueArrearageInterest();
	
						//从活期账户中取出结息前的费用值
						double trueSuretyFee = subAccountCurrentInfo.getFirstLimitAmount(); //第一透支余额对应：担保费
						double trueCommission = subAccountCurrentInfo.getSecondLimitAmount(); //第二透支余额对应：手续费
						double trueCompoundInterest = subAccountCurrentInfo.getThirdLimitAmount(); //第三透支余额对应：复利 
						double trueOverDueInterest = subAccountCurrentInfo.getMonthLimitAmount(); //月度累计余额对应：罚息
						double trueArrearageInterest = subAccountCurrentInfo.getCapitalLimitAmount(); //资金限制余额对应：累计欠息
						double truePredrawInterest = subAccountCurrentInfo.getNegotiateInterest(); //对应于贷款计提利息
						//逾期欠息
						double trueOverDueArrearageInterest = 0;
						if (Interest > 0)
							trueOverDueArrearageInterest = subAccountCurrentInfo.getNegotiateAmount(); //对应于贷款逾期欠息 2004-01-13
						if (CompoundInterest > 0)
							trueOverDueArrearageInterest = subAccountCurrentInfo.getNegotiateUnit();
						if (OverDueInterest > 0)
							trueOverDueArrearageInterest = subAccountFixedInfo.getPreDrawInterest();
	
						log.info("===========================================================================");
						log.info("lhj debug inf  目前子账户中各个属性--------------------------------------------------------");
						log.info("lhj debug info 子账户中的正常利息是:" + tmpInterest);
						log.info("lhj debug info 子账户中的计提利息是:" + tmpPreDrawInterest);
						log.info("lhj debug info 子账户中的复利是    :" + tmpCompoundInterest);
						log.info("lhj debug info 子账户中的手续费是  :" + tmpCommission);
						log.info("lhj debug info 子账户中的担保费是  :" + tmpSuretyFee);
						log.info("lhj debug info 子账户中的罚息是    :" + tmpOverDueInterest);
						log.info("lhj debug info 子账户中的累计欠息是    :" + tmpArrearageInterest);
						log.info("lhj debug info 子账户中的逾期欠息是    :" + tmpOverDueArrearageInterest);
						log.info("===========================================================================");
	
						//结息，包含结清利息和部分结清利息
						if (Interest > 0)
						{
							log.info("lhj debug info 贷款反结息 UtilOperation.Arith.round(Interest,2)== " + UtilOperation.Arith.round(Interest, 2));
							log.info("lhj debug info 贷款反结息 UtilOperation.Arith.round(RealInterest,2)== " + UtilOperation.Arith.round(RealInterest, 2));
							log.info("isRemitInterest = " + isRemitInterest);
							if ((UtilOperation.Arith.round(Interest, 2) == UtilOperation.Arith.round(RealInterest, 2)) || isRemitInterest == SETTConstant.BooleanValue.ISTRUE)
							{
								log.info("lhj debug info == 贷款反结息 == 相等！");
								tmpInterest = UtilOperation.Arith.add(tmpInterest, Interest);
							}
							else
							{
								log.info("lhj debug info == 贷款反结息 == 不相等!!!!");
								tmpInterest = UtilOperation.Arith.add(tmpInterest, RealInterest);
							}
	
						}
	
						//计提利息（不豁免，未结清部分保留在数据库中以备日后冲销）
						tmpPreDrawInterest = truePredrawInterest;
						//担保费累计未结余额
						if (SuretyFee > 0)
							tmpSuretyFee = trueSuretyFee;
						//手续费累计未结余额
						if (Commision > 0)
							tmpCommission = trueCommission;
						//复利累计未结余额
						if (CompoundInterest > 0)
						{
							//						trueOverDueArrearageInterest = subAccountCurrentInfo.getNegotiateUnit();
							//						if (OverDueInterest > 0)
							//							trueOverDueArrearageInterest = subAccountFixedInfo.getPreDrawInterest();
							tmpCompoundInterest = trueCompoundInterest;
						}
						//逾期罚息累计未结余额
						if (OverDueInterest > 0)
							tmpOverDueInterest = trueOverDueInterest;
						//trueOverDueArrearageInterest = subAccountFixedInfo.getPreDrawInterest();
	
						log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
	
						log.info("lhj debug info 贷款反结息后各个属性--------------------------------------------------------");
	
						log.info("lhj debug info 贷款反结息后的正常利息是:" + tmpInterest);
						log.info("lhj debug info 贷款反结息后的计提利息是:" + tmpPreDrawInterest);
						log.info("lhj debug info 贷款反结息后的复利是    :" + tmpCompoundInterest);
						log.info("lhj debug info 贷款反结息后的手续费是  :" + tmpCommission);
						log.info("lhj debug info 贷款反结息后的担保费是  :" + tmpSuretyFee);
						log.info("lhj debug info 贷款反结息后的罚息是    :" + tmpOverDueInterest);
						log.info("lhj debug info 贷款反结息后的累计欠息余额是    :" + trueArrearageInterest);
						log.info("lhj debug info 贷款反结息后的逾期欠息余额是    :" + trueOverDueArrearageInterest);
						log.info("lhj debug info 贷款反结息后各个属性--------------------------------------------------------");
	
						//为贷款账户对象赋值
						subAccountLoanInfo.setInterest(tmpInterest);
						subAccountLoanInfo.setSuretyFee(tmpSuretyFee);
						
						//Boxu Add 2008年3月4日 为了修改"复利","罚息"等删除时回写计提利息和计提日期的问题
						if(Interest>0)
						{
							subAccountLoanInfo.setPreDrawInterest(tmpPreDrawInterest);
						}
						Timestamp closeDate = Env.getSystemDate(accountInfo.getOfficeID(), accountInfo.getCurrencyID());
						UtilOperation utilOperation = new UtilOperation(conn);
						LoanPayNoticeInfo loanPayNoticeInfo = utilOperation.findLoanPayNoticeByID(subAccountLoanInfo.getLoanNoteID());
						if(loanPayNoticeInfo.getIsRemitCompoundInterest()==Constant.TRUE)
						{
							DailyAccountBalanceInfo predaily =null;
							InterestsInfo preinterestsInfo = null;
							InterestsInfo todayinterestsInfo = null;
							double payInterest = 0;
							double payCompoundInterest = 0;
							double payOverDueInterest = 0.0;
							try
							{
								predaily  = sett_DailyAccountBalanceDAO.findAllBySubAccountIDAndDate(SubAccountID, accountInfo.getOfficeID(), accountInfo.getCurrencyID(), getNextNDay(closeDate, -1));
								preinterestsInfo = utilOperation.findPayInterestAmount(subAccountAssemblerInfo.getSubAccountLoanInfo().getID(),closeDate);
								todayinterestsInfo = utilOperation.findAllClearCompoundInterset(subAccountAssemblerInfo.getSubAccountLoanInfo().getID(),closeDate);
								if(clearLoanAccountInterestConditionInfo.getClearInterestDate().equals(closeDate))
								{
									payInterest = todayinterestsInfo.getPayminterest()-RealInterest;
									payCompoundInterest = todayinterestsInfo.getPaymcompoundinterest()-RealCompoundInterest;
									payOverDueInterest = todayinterestsInfo.getPaymforfeitinterest()-RealOverDueInterest;
								}
								else
								{
									payInterest = todayinterestsInfo.getPayminterest();
									payCompoundInterest = todayinterestsInfo.getPaymcompoundinterest();
									payOverDueInterest = todayinterestsInfo.getPaymforfeitinterest();
								}
								tmpArrearageInterest = predaily.getAl_mArrearageInterest();
								tmpCompoundInterest = predaily.getMcompoundinterest();
							}
							catch(Exception e)
							{
								throw new IException(e.getMessage());
							}
								if(preinterestsInfo.getMinterest()-preinterestsInfo.getPayminterest()>payInterest)
								{
									tmpArrearageInterest = UtilOperation.Arith.sub(tmpArrearageInterest ,payInterest);
								}
								else if(preinterestsInfo.getMinterest()-preinterestsInfo.getPayminterest()<payInterest&&preinterestsInfo.getMinterest()>preinterestsInfo.getPayminterest())
								{
									tmpArrearageInterest = UtilOperation.Arith.sub(tmpArrearageInterest ,UtilOperation.Arith.sub(preinterestsInfo.getMinterest(),preinterestsInfo.getPayminterest()));
								}
								if(preinterestsInfo.getMcompoundinterest()-preinterestsInfo.getPaymcompoundinterest()>payCompoundInterest)
								{
									tmpArrearageInterest =UtilOperation.Arith.sub( tmpArrearageInterest ,payCompoundInterest);
								}
								else if(preinterestsInfo.getMcompoundinterest()-preinterestsInfo.getPaymcompoundinterest()<payCompoundInterest&&preinterestsInfo.getMcompoundinterest()>preinterestsInfo.getPaymcompoundinterest())
								{
									tmpArrearageInterest = UtilOperation.Arith.sub(tmpArrearageInterest ,UtilOperation.Arith.sub(preinterestsInfo.getMcompoundinterest(),preinterestsInfo.getPaymcompoundinterest()));
								}
								if(preinterestsInfo.getMforfeitinterest()-preinterestsInfo.getPaymforfeitinterest()>payOverDueInterest)
								{
									tmpArrearageInterest =UtilOperation.Arith.sub( tmpArrearageInterest ,payOverDueInterest);
								}
								else if(preinterestsInfo.getMforfeitinterest()-preinterestsInfo.getPaymforfeitinterest()<payOverDueInterest&&preinterestsInfo.getMforfeitinterest()>preinterestsInfo.getPaymforfeitinterest())
								{
									tmpArrearageInterest = UtilOperation.Arith.sub(tmpArrearageInterest ,UtilOperation.Arith.sub(preinterestsInfo.getMforfeitinterest(),preinterestsInfo.getPaymforfeitinterest()));
								}
							tmpCompoundInterest = UtilOperation.Arith.sub(tmpCompoundInterest,payCompoundInterest);
						}
						if(tmpArrearageInterest<0.01)
						{
							tmpArrearageInterest = 0;
						}
						subAccountLoanInfo.setCommission(tmpCommission);
						subAccountLoanInfo.setCompoundInterest(tmpCompoundInterest);
						subAccountLoanInfo.setOverDueInterest(tmpOverDueInterest);
						subAccountLoanInfo.setArrearageInterest(tmpArrearageInterest);
						subAccountLoanInfo.setOverDueArrearageInterest(trueOverDueArrearageInterest);
	
						//update子账户表sett_SubAccount
						try
						{
							subAccountCurrentInfo.setFirstLimitAmount(0);
							subAccountCurrentInfo.setSecondLimitAmount(0);
							subAccountCurrentInfo.setThirdLimitAmount(0);
							subAccountCurrentInfo.setMonthLimitAmount(0);
							subAccountCurrentInfo.setCapitalLimitAmount(0);
	
							if (Interest > 0)
								subAccountCurrentInfo.setNegotiateAmount(0);
							if (CompoundInterest > 0)
								subAccountCurrentInfo.setNegotiateUnit(0);
							if (OverDueInterest > 0)
								subAccountFixedInfo.setPreDrawInterest(0);
							
							//还原计提日期
							//Boxu Add 2008年3月4日 为了修改"复利","罚息"等删除时回写计提利息和计提日期的问题
							if(Interest>0)
							{
								if ( subAccountFixedInfo.getPreDrawDate() != null&&UtilOperation.Arith.round(Interest,2)==UtilOperation.Arith.round(RealInterest,2) )
								{
									subAccountLoanInfo.setPreDrawDate(subAccountFixedInfo.getPreDrawDate());
									subAccountFixedInfo.setPreDrawDate(null);
								}
							}
	
							sett_SubAccountDAO.updateSubAccountCurrent(subAccountCurrentInfo);
	
							sett_SubAccountDAO.updateSubAccountFix(subAccountFixedInfo);
							if (sett_SubAccountDAO.updateSubAccountLoan(subAccountLoanInfo) > -1)
							{
								isSuccess = 1;
							}
						}
						catch (SQLException e2)
						{
							e2.printStackTrace();
							throw new IException("Gen_E001");
						}catch(Exception e3)
					    {
						    e3.printStackTrace();
							throw new IException("Gen_E001");
						}
					}
				}
			}
		}
		return isSuccess;

	}

	/**
		* 接口名称：单户计息倒填
		* 功能描述：1：单户计息信息倒填，更新账户余额日结表中该账户的每一日的余额、利息、利率信息、
		*              累计利息信息，以及子账户表中的当前累计利息。
		*           2：“利息费用结算”、“利息费用支付”、“贷款归还”等交易进行账户结息时以及 “关机时
		*              起息日/利率倒填”处理线程均需调用本接口。实现处理账户当天倒填余额当天结息或
		*              者当天倒填银行利率当天结息的功能。
		*           3：计息倒填处理包含余额倒填和利率倒填。利率倒填时，余额不变，利率改变。
		*           4：对倒填开户交易和一般倒填交易分开实现。
		*           5：对账户交易明细表中设置账户倒填标志,以防止操作重复的操作放在本算法的外面！。
		* @param AccountID    主账户ID
		* @param SubAccountID 子账户ID
		* @param BackDate     倒填交易起息日
		* @param BackAmount   倒填交易金额
		* @param ExecuteDate  执行日/关机日期
		* @param isCloseSystem 是否关机调用
		* @return long -1:不成功，1：成功
		* @throws IException
		*/

	public long accountInterestCalculationBackward(
		long AccountID,
		long SubAccountID,
		Timestamp BackDate,
		double BackAmount,
		long lOfficeID,
		long lCurrencyID,
		Timestamp ExecuteDate,
		long isCloseSystem)
		throws IException
	{
		long isSuccess = -1; //成功标志
		Connection conn = this.conn_Out;
		double accumulateNormalInterestAdjust = 0.0; //累计正常利息调整额
		double accumulateNegotiateInterestAdjust = 0.0; //累计协定利息调整额
		double accumulateCompoundInterestAdjust = 0.0; //累计复利利息调整额
		double accumulateOverDueInterestAdjust = 0.0; //累计罚息利息调整额
		double newAccumulateNormalInterest = 0.0; //新的累计正常利息调整额
		double newAccumulateNegotiateInterest = 0.0; //新的累计协定利息调整额
		double newAccumulateCompoundInterest = 0.0; //新的累计复利利息调整额
		double newAccumulateOverDueInterest = 0.0; //新的累计罚息利息调整额
		double executeDateNormalInteres = 0.0; //活期子账户的当前利息（即在关机算息后的正常利息）
		double executeDateNegotiateInterest = 0.0; //活期子账户的当前利息（即在关机算息后的协定利息）
		double executeDateLoanInterest = 0.0; //贷款子账户的当前利息（即在关机算息后的贷款利息）
		double executeDateMarginInterest = 0.0; //保证金子账户的当前利息（即在关机算息后的保证金利息）
		double executeDateCompoundInterest = 0.0; //贷款子账户的当前复利（即在关机算息后的贷款复利）
		double executeDateOverDueInterest = 0.0; //贷款子账户的当前罚息（即在关机算息后的贷款罚息）
		//double todayCompoundIntersetAdjust = 0.0; //当日复利用于在最后一天
		double arrearageInterest = 0.0; //欠息金额

		
		//根据AccountID在sett_account表中定位主账户记录
		Sett_AccountDAO sett_AccountDAO = new Sett_AccountDAO(conn);
		UtilOperation utilOperation = new UtilOperation(conn);
		AccountInfo accountInfo = null;
		try
		{
			accountInfo = sett_AccountDAO.findByID(AccountID);
			if (accountInfo == null)
			{
				throw new IException(true, "主账户表中没有对应记录，交易失败", null);
			}
		}
		catch (IException ie)
		{
			throw ie;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IException(true, "无法在主账户表中找到对应的信息，交易失败", null);
		}
		log.info("单户计息倒填中取主账户成功！");
		//根据SubAccountID在sett_SubAccount表中定位子账户记录
		Sett_SubAccountDAO sett_SubAccountDAO = new Sett_SubAccountDAO(conn);
		SubAccountAssemblerInfo subAccountAssemblerInfo = null;
		try
		{
			log.info("单户计息倒填中取子账户....");
			subAccountAssemblerInfo = sett_SubAccountDAO.findByID(SubAccountID);
		}
		catch (SQLException e1)
		{
			e1.printStackTrace();
			throw new IException(true, "无法在字子账户表中找到对应的信息，交易失败", null);
		}
		log.info("单户计息倒填中取子账户成功！");
		//子账户中的活期存款信息
		SubAccountCurrentInfo subAccountCurrentInfo = null;
		//子账户中的贷款信息
		SubAccountLoanInfo subAccountLoanInfo = null;
		//子账户中保证金信息
		SubAccountFixedInfo subAccountFixedInfo = null;
		//账户当前余额
		double currentBalance = -1;
		if (subAccountAssemblerInfo != null)
		{
			//取出在关机倒算前的当前子账户的正常利息和协定利息（活期子账户）、当前利息（贷款子账户）
			//目的：在结束关机倒算后，更新每日算息后的子账户的账户余额日结表记录
			//      更新累计利息字段：
			//      新的累计利息 = 关机倒算前的利息 + 累计利息调整额
			//      此步骤是在结束循环并且更新完子账户后，在修改倒填标志之前进行

			//活期子账户------------------------------------------------------------------	
			subAccountCurrentInfo = subAccountAssemblerInfo.getSubAccountCurrenctInfo();
			//活期子账户的当前利息（即在关机算息后的利息）
			executeDateNormalInteres = subAccountCurrentInfo.getInterest();
			//活期子账户的当前利息（即在关机算息后的利息）
			executeDateNegotiateInterest = subAccountCurrentInfo.getNegotiateInterest();
			//活期子账户------------------------------------------------------------------

			//贷款子账户------------------------------------------------------------------
			subAccountLoanInfo = subAccountAssemblerInfo.getSubAccountLoanInfo();
			//贷款子账户的当前利息（即在关机算息后的贷款利息）
			executeDateLoanInterest = subAccountLoanInfo.getInterest();
			//贷款子账户的当前复利（即在关机算息后的贷款复利）
			executeDateCompoundInterest = subAccountLoanInfo.getCompoundInterest();
			//贷款子账户的当前罚息（即在关机算息后的贷款罚息）
			executeDateOverDueInterest = subAccountLoanInfo.getOverDueInterest();
			//贷款子账户------------------------------------------------------------------
			
			//保证金子账户----------------------------------------------------------------
			subAccountFixedInfo = subAccountAssemblerInfo.getSubAccountFixedInfo();
			executeDateMarginInterest = subAccountFixedInfo.getInterest();
			//保证金子账户----------------------------------------------------------------
		}
		else
		{
			throw new IException(true, "无法在子账户表中找到对应的信息，交易失败", null);
		}
		
		CurrentDepositAccountInterestInfo currentDepositAccountInterestInfo = null;

		if (accountInfo != null)
		{

			long currencyID = accountInfo.getCurrencyID(); //主账户中币种
			long accountTypeID = accountInfo.getAccountTypeID();
	        Sett_AccountTypeDAO sett_AccountTypeDAO = new Sett_AccountTypeDAO();
	        AccountTypeInfo accountTypeInfo = null;
	        try {
				accountTypeInfo = sett_AccountTypeDAO.findByID(accountTypeID);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if (accountTypeInfo != null) {
				//本接口对定期存款和贴现账户不进行计息
				if (accountTypeInfo.getAccountGroupID() != SETTConstant.AccountGroupType.FIXED && 
					accountTypeInfo.getAccountGroupID() != SETTConstant.AccountGroupType.NOTIFY && 
					accountTypeInfo.getAccountGroupID() != SETTConstant.AccountGroupType.DISCOUNT)
				{
					//倒算前的每日账户金额
					DailyAccountBalanceInfo dailyAccountBalanceInfo = null;
					if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.TRUST || 
							accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CONSIGN || 
							accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.OTHERLOAN || 
							accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.YT)
					{
						try
						{
							dailyAccountBalanceInfo = sett_DailyAccountBalanceDAO.findAllBySubAccountIDAndDate(SubAccountID, lOfficeID, lCurrencyID, getNextNDay(BackDate, -1));
						}
						catch (SQLException e2)
						{
							e2.printStackTrace();
							throw new IException("Gen_E001");
						}
						
						//现用于贷款循环前
						if (dailyAccountBalanceInfo != null && dailyAccountBalanceInfo.getAccountID() > 0)
						{
							accumulateNormalInterestAdjust = dailyAccountBalanceInfo.getInterest();
							newAccumulateNormalInterest = accumulateNormalInterestAdjust;
							
							//累计复利利息调整额
							accumulateCompoundInterestAdjust = dailyAccountBalanceInfo.getMcompoundinterest();
							newAccumulateCompoundInterest = accumulateCompoundInterestAdjust;
							
							//累计罚息利息调整额
							accumulateOverDueInterestAdjust = dailyAccountBalanceInfo.getMforfeitinterest();
							newAccumulateOverDueInterest = accumulateOverDueInterestAdjust;
							//开始倒算 -- 欠息金额
							arrearageInterest = dailyAccountBalanceInfo.getAl_mArrearageInterest();
						}
					}
					
					//计算循环次数
					InterestCalculation interestCalculation = new InterestCalculation(conn);
					int loopDays = (int) interestCalculation.getIntervalDays(BackDate, ExecuteDate, 1);
					//				log.info("循环次数是 " + loopDays);
	
					Sett_DailyAccountBalanceDAO sett_DailyAccountBalanceDAO = new Sett_DailyAccountBalanceDAO(conn);
					//				log.info("lhj debug info =========单户计息倒填循环开始......");
					for (int i = 0; i < loopDays; i++)
					{ //－－－－循环开始－－－－－－不含ExecuteDate当天－－－－－－－－－－－－－－－－－－－－－－－－
						//定义中间变量-------------------------------------------------------start-------
						//新的计息余额
						double newInterestBalance = 0.0;
						//本日正常利息调整额
						double todayNormalInterestAdjust = 0.0;
						//本日协定利息调整额
						double todayNegotiateInterestAdjust = 0.0;
						//本日复利利息
						double todayCompoundIntersetAdjust = 0.0;
						//本日罚息利息
						double todayOverDueIntersetAdjust = 0.0;
						//当前算息日期
						Timestamp tmpBackDate = getNextNDay(BackDate, i);
						//当前算息日期+1
						Timestamp tmpBackDateAddOne = getNextNDay(tmpBackDate, 1);
						//					log.info("lhj debug info ======当前日期是tmpBackDate" + tmpBackDate);
	
						//根据主账户AccountID,子账户SubAccountID,算息日期tmpBackDate在账户余额日结表-中查询相关记录。
	
						//DailyAccountBalanceInfo dailyAccountBalanceInfo = null;
						try
						{
							//						log.info("lhj debug info =====在账户余额日结表中查询相关记录以区分倒填开户还是一般倒填交易......");
	
							dailyAccountBalanceInfo = sett_DailyAccountBalanceDAO.findAllBySubAccountIDAndDate(SubAccountID, lOfficeID, lCurrencyID, tmpBackDate);
						}
						catch (SQLException e2)
						{
							e2.printStackTrace();
							throw new IException("Gen_E001");
						}
						long dabi_AccountID = -1;
						if (dailyAccountBalanceInfo != null)
						{
							dabi_AccountID = dailyAccountBalanceInfo.getAccountID();
							//						log.info("lhj debug info ===========dabi_AccountID是： " + dabi_AccountID);
						}
						if (dailyAccountBalanceInfo == null || dabi_AccountID < 0)
						{
							//---------------------------------------倒填开户交易-------start-------------------------------------------
							//						log.info("在账户余额日结表中没有记录，开始进行〔倒填开户交易〕......");
							dailyAccountBalanceInfo = new DailyAccountBalanceInfo();
							if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CURRENT ||
								accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.BAK ||
								accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.RESERVE ||
								accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.LENDING ||
								accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.OTHERDEPOSIT)
							{
								//--------倒填开户交易--------活期账户计息-----------start-----------------------------------------------
								//新的计息余额
								//							log.info("〔倒填开户交易〕中开始进行活期账户计息......");
								newInterestBalance = BackAmount;
								
								if(accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.LENDING)
								{//如果是拆借账户，借贷方向与本if中的其他账户组相反
									newInterestBalance = -BackAmount;
								}
								//							利率计划ID						   
								long interestRatePlanID = subAccountCurrentInfo.getInterestRatePlanID();
								//							利率计划类型
								long lInterestRatePlanTypeID = NameRef.getInterestRatePlanTypeIDByID(interestRatePlanID);
								//							协定存款金额
								double negotiateAmount = subAccountCurrentInfo.getNegotiateAmount();
								//							协定存款单位（元）
								double negotiateUnit = subAccountCurrentInfo.getNegotiateUnit();
								//long negotiateRatePlanID = -1;  //2003-11-10该字段从数据库中删除！！！！！！！dataentity中无此方法！？？？？？？
								//修改为协定利率 2003-11-23 lhj*********************
								//							协定利率
								double negotiateRate = subAccountCurrentInfo.getNegotiateRate();
								//
								//Modify by leiyang 2008-06-30 
								long IsNegotiate = subAccountCurrentInfo.getIsNegotiate(); //是否协定存款
	
								//negotiateRatePlanID = subAccountCurrentInfo.getNegotiateRatePlanID();//协定存款利率计划ID
								//							计算活期存款的本日正常余额，正常利率，正常利息，协定余额，协定利率，协定利息		
								
								if(lInterestRatePlanTypeID == SETTConstant.InterestRatePlanType.BALANCE_AVERAGE)
								{//log.info("利率计划类型为日均余额结息，调用日均余额算息方法 modify by yanliu");
									
									Timestamp tsDateStart = BackDate;
									Timestamp tsDateEnd = tmpBackDateAddOne;
									double averageBalance = newInterestBalance;		
	
									//Modify by leiyang 更改校验协定存款的方式
									//增加 IsNegotiate 参数，是否为协定存款
									currentDepositAccountInterestInfo =
										interestCalculation.calculateCurrentDepositAccountInterestForAverageBalance(
											lOfficeID,
											lCurrencyID,
											interestRatePlanID,
											averageBalance,			//日均余额
											newInterestBalance,			//当日计息金额
											tsDateStart,	//起息日
											tsDateEnd,		//关机日下一天
											negotiateAmount,
											negotiateUnit,
											negotiateRate,
											IsNegotiate);										
								}
								else
								{//log.info("利率计划类型不是日均余额结息，调用一般算息方法 modify by yanliu");		
									//Modify by leiyang 更改校验协定存款的方式
									//增加 IsNegotiate 参数，是否为协定存款
									currentDepositAccountInterestInfo =
										interestCalculation.calculateCurrentDepositAccountInterest(
											lOfficeID,
											lCurrencyID,
											interestRatePlanID,
											newInterestBalance,
											tmpBackDate,
											tmpBackDateAddOne,
											negotiateAmount,
											negotiateUnit,
											negotiateRate,
											IsNegotiate);
								}
								
								//---取计算后的结构------------------------------------------------------------------------
								//本日正常余额
								double todayNormalBalance = currentDepositAccountInterestInfo.getNormalBalance();
								//本日协定余额
								double todayNegotiateBalance = currentDepositAccountInterestInfo.getNegotiationBalance();
								//本日正常利率
								double todayNormalInterestRate = currentDepositAccountInterestInfo.getNormalInterestRate();
								//本日协定利率
								double todayNegotiateInterestRate = negotiateRate;
								//本日正常利息			
								double todayNormalInterest = currentDepositAccountInterestInfo.getNormalInterest();
								//本日协定利息
								double todayNegotiateInterest = currentDepositAccountInterestInfo.getNegotiationInterest();
								//---取计算后的结构结束------------------------------------------------------------------------
	
								//本日正常利息增加额
								todayNormalInterestAdjust = todayNormalInterest;
								//本日协定利息增加额
								todayNegotiateInterestAdjust = todayNegotiateInterest;
	
								//为每日账户余额对象赋值
								//							主账户
								dailyAccountBalanceInfo.setAccountID(AccountID);
								//							子账户
								dailyAccountBalanceInfo.setSubAccountID(SubAccountID);
								//							日期
								dailyAccountBalanceInfo.setDate(tmpBackDate);
								//主账户状态
								dailyAccountBalanceInfo.setAccountStatusID(accountInfo.getStatusID());
								//余额
								dailyAccountBalanceInfo.setBalance(0.0); //余额是零
								//计息余额（正常部分
								dailyAccountBalanceInfo.setInterestBalance(todayNormalBalance);
								//协定余额
								dailyAccountBalanceInfo.setAc_mNegotiateBalance(todayNegotiateBalance);
								//正常利率
								dailyAccountBalanceInfo.setInterestRate(todayNormalInterestRate);
								//正常利息
								dailyAccountBalanceInfo.setDailyInterest(todayNormalInterest);
								//协定利率
								dailyAccountBalanceInfo.setAc_mNegotiateRate(todayNegotiateInterestRate);
								//协定利息
								dailyAccountBalanceInfo.setAc_mDailyNegotiateInterest(todayNegotiateInterest);
								//累计的正常利息的调整值
								if(lInterestRatePlanTypeID == SETTConstant.InterestRatePlanType.BALANCE_AVERAGE)
								{
									accumulateNormalInterestAdjust = todayNormalInterestAdjust;
								}
								else
								{
									accumulateNormalInterestAdjust = UtilOperation.Arith.add(accumulateNormalInterestAdjust, todayNormalInterestAdjust);
								}
								//累计的协定利息的调整值
								accumulateNegotiateInterestAdjust = UtilOperation.Arith.add(accumulateNegotiateInterestAdjust, todayNegotiateInterestAdjust);
								//新的累计正常利息
								newAccumulateNormalInterest = accumulateNormalInterestAdjust;
								//							新的累计协定利息
								newAccumulateNegotiateInterest = accumulateNegotiateInterestAdjust;
								dailyAccountBalanceInfo.setInterest(newAccumulateNormalInterest);
								dailyAccountBalanceInfo.setAc_mNegotiateInterest(newAccumulateNegotiateInterest);
	
								//							log.info("lhj debug info ==累计调整正常利息是:" + newAccumulateNormalInterest);
								//							log.info("lhj debug info ==累计调整协定利息是:" + newAccumulateNegotiateInterest);
	
								//	//--------倒填开户交易--------活期账户计息----------end------------------------------------------------
								//							log.info("〔倒填开户交易〕中开始进行活期账户计息结束");
							}
							else if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.TRUST || 
								accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CONSIGN || 
								accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.OTHERLOAN || 
								accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.YT)
							{
								//----倒填开户交易--------贷款账户计息--------------start--------------------------------------------
								log.info("〔倒填开户交易〕中开始进行贷款账户计息......");
								//新的计息余额
								newInterestBalance = -BackAmount;
								//放款单号
								long loanNoteID = subAccountLoanInfo.getLoanNoteID();
								
								//根据放款通知单取出利率
								InterestRate interestRate = interestCalculation.getInterestRateByPayForm(loanNoteID, tmpBackDate);
								//贷款利率
								double AL_InterestRate = interestRate.getRate();
								//贷款利率类型					
								long AL_InterestType = interestRate.getType();
	
								/*计算本日贷款利息*/
								double todayLoanInterest = interestCalculation.calculateLoanAccountInterest(currencyID, AL_InterestRate, AL_InterestType, newInterestBalance, tmpBackDate, tmpBackDateAddOne);
	
								//本日正常利息调整额
								todayNormalInterestAdjust = todayLoanInterest;
								//为每日账户余额对象赋值=================================================
								//主账户
								dailyAccountBalanceInfo.setAccountID(AccountID);
								//子账户
								dailyAccountBalanceInfo.setSubAccountID(SubAccountID);
								//日期
								dailyAccountBalanceInfo.setDate(tmpBackDate);
								//主账户状态
								dailyAccountBalanceInfo.setAccountStatusID(accountInfo.getStatusID());
								//账户余额是0！！！！！
								dailyAccountBalanceInfo.setBalance(0);
								//计息余额
								dailyAccountBalanceInfo.setInterestBalance(newInterestBalance);
								//当日贷款利率	     
								dailyAccountBalanceInfo.setInterestRate(AL_InterestRate);
								//当日新增贷款利率
								dailyAccountBalanceInfo.setDailyInterest(todayNormalInterestAdjust);
								//累计的贷款利息的调整额							
								accumulateNormalInterestAdjust = UtilOperation.Arith.add(accumulateNormalInterestAdjust, todayNormalInterestAdjust);
								//新的累计贷款利息
								newAccumulateNormalInterest = accumulateNormalInterestAdjust;
								//
								dailyAccountBalanceInfo.setInterest(newAccumulateNormalInterest);
	
								//log.info("lhj debug info ---累计贷款利息是:" + newAccumulateNormalInterest);
								//----倒填开户交易--------贷款账户计息----------------end------------------------------------------  
	
								//log.info("〔倒填开户交易〕中开始进行贷款账户计息结束");
								
								//modify by leiyang3
								//2010/11/28
								//用于计算自营贷款到期后的罚息
								if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.TRUST)
								{
									LoanPayNoticeInfo loanPayNoticeInfo = utilOperation.findLoanPayNoticeByID(loanNoteID);
									
									if(loanPayNoticeInfo == null){
										throw new IException("没有找到有效的放款单信息");
									}
									
									Timestamp loanStartDate = loanPayNoticeInfo.getStart();
									Timestamp loanEndDate = loanPayNoticeInfo.getEnd();
									
									//-- 复利计算开始 --
									if(loanPayNoticeInfo.getIsRemitCompoundInterest()==Constant.TRUE) //通过合同是否时行复利属性，判断是否进行复利计算
									{
										boolean boolClearDay = utilOperation.isClearInterestDay(SubAccountID, tmpBackDate);
										double compoundAccumulateBalance = 0.0;
				
										//每日复利基数（有结息日后，当天为结息日情况）：上一日累积未还利息  + 上一日累积未还复利  + 上一日累积未还罚息
										if(boolClearDay == true)
										{
											compoundAccumulateBalance = UtilOperation.Arith.sub(newAccumulateNormalInterest, todayNormalInterestAdjust);
											compoundAccumulateBalance = UtilOperation.Arith.add(compoundAccumulateBalance, newAccumulateCompoundInterest);
											compoundAccumulateBalance = UtilOperation.Arith.add(compoundAccumulateBalance, newAccumulateOverDueInterest);
											arrearageInterest = compoundAccumulateBalance;
										}
										//每日复利基数（有结息日后，当天不为结息日情况）：子账户未还利息
										else
										{
											if(arrearageInterest>0)//上一日有欠息，当日非结息日计算本日前夕
											{
												InterestsInfo preinterestsInfo = null;
												InterestsInfo interestsInfo = null;
												try
												{
													preinterestsInfo = utilOperation.findPayInterestAmount(subAccountAssemblerInfo.getSubAccountLoanInfo().getID(),tmpBackDate);
													interestsInfo = utilOperation.findAllClearCompoundInterset(SubAccountID, tmpBackDate);
												}
												catch(Exception e)
												{
													throw new IException(e.getMessage());
												}
												if(preinterestsInfo.getMinterest()-preinterestsInfo.getPayminterest()>interestsInfo.getPayminterest())
												{
													arrearageInterest = UtilOperation.Arith.sub(arrearageInterest ,interestsInfo.getPayminterest());
												}
												else if(preinterestsInfo.getMinterest()-preinterestsInfo.getPayminterest()<interestsInfo.getPayminterest()&&preinterestsInfo.getMinterest()>preinterestsInfo.getPayminterest())
												{
													arrearageInterest = UtilOperation.Arith.sub(arrearageInterest ,UtilOperation.Arith.sub(preinterestsInfo.getMinterest(),preinterestsInfo.getPayminterest()));
												}
												if(preinterestsInfo.getMcompoundinterest()-preinterestsInfo.getPaymcompoundinterest()>interestsInfo.getPaymcompoundinterest())
												{
													arrearageInterest =UtilOperation.Arith.sub( arrearageInterest ,interestsInfo.getPaymcompoundinterest());
												}
												else if(preinterestsInfo.getMcompoundinterest()-preinterestsInfo.getPaymcompoundinterest()<interestsInfo.getPaymcompoundinterest()&&preinterestsInfo.getMcompoundinterest()>preinterestsInfo.getPaymcompoundinterest())
												{
													arrearageInterest = UtilOperation.Arith.sub(arrearageInterest ,UtilOperation.Arith.sub(preinterestsInfo.getMcompoundinterest(),preinterestsInfo.getPaymcompoundinterest()));
												}
												if(preinterestsInfo.getMforfeitinterest()-preinterestsInfo.getPaymforfeitinterest()>interestsInfo.getPaymforfeitinterest())
												{
													arrearageInterest =UtilOperation.Arith.sub( arrearageInterest ,interestsInfo.getPaymforfeitinterest());
												}
												else if(preinterestsInfo.getMforfeitinterest()-preinterestsInfo.getPaymforfeitinterest()<interestsInfo.getPaymforfeitinterest()&&preinterestsInfo.getMforfeitinterest()>preinterestsInfo.getPaymforfeitinterest())
												{
													arrearageInterest = UtilOperation.Arith.sub(arrearageInterest ,UtilOperation.Arith.sub(preinterestsInfo.getMforfeitinterest(),preinterestsInfo.getPaymforfeitinterest()));
												}
											}
											}
										
										//复利利率
										InterestCalculationParameterInfo paramInfo = new InterestCalculationParameterInfo();
										paramInfo.setLoanNoteID(loanNoteID);
										paramInfo.setValidDate(tmpBackDate);
										InterestRate compoundRate = interestCalculation.getCompoundInterestRate(paramInfo);
										
										//倒算后的每日复利利息
										todayCompoundIntersetAdjust = interestCalculation.calculateLoanAccountInterest(lCurrencyID, compoundRate.getRate(), compoundRate.getType(), arrearageInterest, tmpBackDate, tmpBackDateAddOne);
										//倒算后的累计复利利息
										accumulateCompoundInterestAdjust = UtilOperation.Arith.add(newAccumulateCompoundInterest, todayCompoundIntersetAdjust);
										newAccumulateCompoundInterest = accumulateCompoundInterestAdjust;
										
										//保存每日复利利息
										dailyAccountBalanceInfo.setMcompounddailyinterset(todayCompoundIntersetAdjust);
										//累计复利利息
										dailyAccountBalanceInfo.setMcompoundinterest(newAccumulateCompoundInterest);
										//欠息
										dailyAccountBalanceInfo.setAl_mArrearageInterest(arrearageInterest);
									}
									//-- 复利计算结束 --
									
									//-- 罚息计算开始  --
									//是否进行罚息
									if(loanPayNoticeInfo.getIsRemitOverDueInterest()==Constant.TRUE) //通过合同是否时行罚息属性，判断是否进行罚息
									{
										if(!loanEndDate.after(tmpBackDate))
										{
											InterestCalculationParameterInfo paramInfo = new InterestCalculationParameterInfo();
											paramInfo.setLoanNoteID(loanNoteID);
											paramInfo.setValidDate(tmpBackDate);
											
											InterestRate overDueRate = interestCalculation.getOverDueInterestRate(paramInfo);
											//每日罚息总金额：贷款未还金额
											double overDueAccumulateBalance = newInterestBalance;
											//倒算后的每日罚息利息
											todayOverDueIntersetAdjust = interestCalculation.calculateLoanAccountInterest(lCurrencyID, overDueRate.getRate(), overDueRate.getType(), overDueAccumulateBalance, tmpBackDate, tmpBackDateAddOne);
											//倒算后的累计罚息利息
											accumulateOverDueInterestAdjust = UtilOperation.Arith.add(newAccumulateOverDueInterest, todayOverDueIntersetAdjust);
											newAccumulateOverDueInterest = accumulateOverDueInterestAdjust;
											
											//保存每日罚息利息
											dailyAccountBalanceInfo.setMforfeitdailyinterset(todayOverDueIntersetAdjust);
											//累计罚息利息
											dailyAccountBalanceInfo.setMforfeitinterest(newAccumulateOverDueInterest);
											//当日罚息利率	     
											dailyAccountBalanceInfo.setMForfeitInterestRate(overDueRate.getRate());
											//重新设置当日新增贷款利率
											dailyAccountBalanceInfo.setDailyInterest(0.0);
											//重新设置累计贷款利息
											accumulateNormalInterestAdjust = UtilOperation.Arith.sub(newAccumulateNormalInterest, todayNormalInterestAdjust);
											newAccumulateNormalInterest = accumulateNormalInterestAdjust;
											dailyAccountBalanceInfo.setInterest(newAccumulateNormalInterest);
										}
									}
									//-- 罚息计算结束 --
								}
								
							}
							else if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.MARGIN)
							{
								System.out.println("--------倒填开户交易--------保证金账户计息（计算方式和活期账户类似）-----------start-----------------------------------------------");
								//新的计息余额
								//							log.info("〔倒填开户交易〕中开始进行保证金账户账户计息......");
								newInterestBalance = BackAmount;
	
								//正常利率
								double normalInterestRate = subAccountFixedInfo.getRate();
								
								System.out.println("--------正常利率: "+normalInterestRate);
								
	                            //计算利息 
								double commonInterest = interestCalculation.caculateInterest(newInterestBalance, tmpBackDate, tmpBackDateAddOne, SETTConstant.InterestRateDaysFlag.DAYS_360 , normalInterestRate, SETTConstant.InterestRateTypeFlag.YEAR, SETTConstant.InterestRateDaysFlag.DAYS_360);

								//---取计算后的结构------------------------------------------------------------------------
								//本日正常余额
								double todayNormalBalance = newInterestBalance;
								
								//本日正常利率
								double todayNormalInterestRate = normalInterestRate;
								
								//本日正常利息			
								double todayNormalInterest = commonInterest;
								
								//---取计算后的结构结束------------------------------------------------------------------------
	
								//本日正常利息增加额
								todayNormalInterestAdjust = todayNormalInterest;
	
								//为每日账户余额对象赋值
								//							主账户
								dailyAccountBalanceInfo.setAccountID(AccountID);
								//							子账户
								dailyAccountBalanceInfo.setSubAccountID(SubAccountID);
								//							日期
								dailyAccountBalanceInfo.setDate(tmpBackDate);
								//主账户状态
								dailyAccountBalanceInfo.setAccountStatusID(accountInfo.getStatusID());
								//余额
								dailyAccountBalanceInfo.setBalance(0.0); //余额是零
								//计息余额（正常部分
								dailyAccountBalanceInfo.setInterestBalance(todayNormalBalance);
								
								//正常利率
								dailyAccountBalanceInfo.setInterestRate(todayNormalInterestRate);
								//正常利息
								dailyAccountBalanceInfo.setDailyInterest(todayNormalInterest);
								
								accumulateNormalInterestAdjust = UtilOperation.Arith.add(accumulateNormalInterestAdjust, todayNormalInterestAdjust);
								
								//新的累计正常利息
								newAccumulateNormalInterest = accumulateNormalInterestAdjust;
								
								dailyAccountBalanceInfo.setInterest(newAccumulateNormalInterest);
							}

							//为账户余额日结表中增加一条新记录
							try
							{
								//							log.info("〔倒填开户交易〕向开始账户日结表中新增日期是" + tmpBackDate.toString() + "的数据.....");
								sett_DailyAccountBalanceDAO.add(dailyAccountBalanceInfo);
							}
							catch (Exception e3)
							{
								e3.printStackTrace();
								throw new IException("保存失败，请重试！");
							}
						//						log.info("〔倒填开户交易〕向开始账户日结表中新增日期是" + tmpBackDate.toString() + "的数据成功！");
						} //-------------------------------倒填开户交易----------------------end-----------------------------------------
						else if (dailyAccountBalanceInfo != null && dabi_AccountID > 0)
						{
							//-------------------------------一般倒填交易----------------------start---------------------------------------
							//						log.info("在账户余额日结表中存在记录，开始进行〔一般倒填交易〕......");
							if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CURRENT||
									accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.BAK ||
									accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.RESERVE ||
									accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.LENDING)
							{
								//--------一般倒填交易--------活期账户计息-------------start--------------------------------------------
								double oldBalance = 0.0; //关机前的本账户的余额							
								double oldInterestBalance = 0.0; //关机前的老的计息余额							
								double oldDailyInterest = 0.0; //关机前本账户的每日正常利息
								double oldInterest = 0.0; //关机前的本账户的正常利息
	
								double oldNegotiateBalance = 0.0; //关机前的本账户的协定余额
								double oldDailyNegotiateInterest = 0.0; //关机前的本账户的每日协定利息							
								double oldNegotiateInterest = 0.0; //关机前的本账户的协定利息
	
								oldBalance = dailyAccountBalanceInfo.getBalance();
								oldInterestBalance = dailyAccountBalanceInfo.getInterestBalance();
								oldDailyInterest = dailyAccountBalanceInfo.getDailyInterest();
								oldInterest = dailyAccountBalanceInfo.getInterest();
	
								oldNegotiateBalance = dailyAccountBalanceInfo.getAc_mNegotiateBalance();
								oldDailyNegotiateInterest = dailyAccountBalanceInfo.getAc_mDailyNegotiateInterest();
								oldNegotiateInterest = dailyAccountBalanceInfo.getAc_mNegotiateInterest();
								log.info("---------------------------------------------------------------");
								log.info("lhj debug info == 倒算前-正常余额是:" + oldBalance);
								log.info("lhj debug info == 倒算前-正常计息余额是:" + oldInterestBalance);
								log.info("lhj debug info == 倒算前-每日正常利息是:" + oldDailyInterest);
								log.info("lhj debug info == 倒算前-正常利息值是:" + oldInterest);
								log.info("---------------------------------------------------------------");
								log.info("lhj debug info == 倒算前-协定余额是:" + oldNegotiateBalance);
								log.info("lhj debug info == 倒算前-每日协定利息是:" + oldDailyInterest);
								log.info("lhj debug info == 倒算前-协定利息值是:" + oldNegotiateInterest);
								log.info("---------------------------------------------------------------");
								log.info("〔一般倒填交易〕中开始进行活期账户计息......");
	
								//本日计息余额
								newInterestBalance = UtilOperation.Arith.add(UtilOperation.Arith.add(oldInterestBalance, oldNegotiateBalance), BackAmount);
								
								if(accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.LENDING)
								{//如果是拆借账户，借贷方向与本if中的其他账户组相反
									newInterestBalance = UtilOperation.Arith.sub(UtilOperation.Arith.add(oldInterestBalance, oldNegotiateBalance), BackAmount);
								}
								
								long interestRatePlanID = subAccountCurrentInfo.getInterestRatePlanID();
								//本日利率计划ID
								double negotiateAmount = subAccountCurrentInfo.getNegotiateAmount();
								//本日协定存款金额
								
								//Modify by leiyang 2008-10-15
								//修改关于协定存款生效日的问题
								//本日协定存款单位（元），历史上没有记录只能取子帐户的协定存款单位
								double negotiateUnit = subAccountCurrentInfo.getNegotiateUnit();
								//long negotiateRatePlanID = -1;
								//2003-11-10该字段从数据库中删除！！！！！！！dataentity中无此方法！？？？？？？
								//negotiateRatePlanID = subAccountCurrentInfo.getNegotiateRatePlanID();//协定存款利率计划ID
								//本日协定存款利率，只能取历史上的协定存款利率
								double negotiateRate = dailyAccountBalanceInfo.getAc_mNegotiateRate();

								
								//Modify by leiyang 2008-07-03 
								long IsNegotiate = subAccountCurrentInfo.getIsNegotiate(); //是否协定存款
	
								//计算活期存款的本日正常余额，正常利率，正常利息，协定余额，协定利率，协定利息		
								//Modify by leiyang 更改校验协定存款的方式
								//增加 IsNegotiate 参数，是否为协定存款
								currentDepositAccountInterestInfo =
									interestCalculation.calculateCurrentDepositAccountInterest(
										lOfficeID,
										lCurrencyID,
										interestRatePlanID,
										newInterestBalance,
										tmpBackDate,
										tmpBackDateAddOne,
										negotiateAmount,
										negotiateUnit,
										negotiateRate,
										IsNegotiate);
	
								//added by mzh_fu 2008/01/03 如果从利率计划表中没有表取到利率,那么取日结表中的利率
								if(currentDepositAccountInterestInfo.getNormalInterestRate() <= 0){	
									//Modify by leiyang 更改校验协定存款的方式
									//增加 IsNegotiate 参数，是否为协定存款
									currentDepositAccountInterestInfo =
										interestCalculation.calculateCurrentDepositAccountInterest(
											dailyAccountBalanceInfo.getInterestRate(),
											newInterestBalance,
											tmpBackDate,
											tmpBackDateAddOne,
											negotiateAmount,
											negotiateUnit,
											negotiateRate,
											IsNegotiate);
								}
								
								//倒算后正常余额
								double todayNormalBalance = currentDepositAccountInterestInfo.getNormalBalance();
								//倒算后协定余额
								double todayNegotiateBalance = currentDepositAccountInterestInfo.getNegotiationBalance();
								//倒算后正常利息
								double todayNormalInterest = currentDepositAccountInterestInfo.getNormalInterest();
								//倒算后协定利息
								double todayNegotiateInterest = currentDepositAccountInterestInfo.getNegotiationInterest();
								//倒算后正常利息增加额		
								todayNormalInterestAdjust = UtilOperation.Arith.sub(todayNormalInterest, oldDailyInterest);
								//倒算后协定利息增加额
								todayNegotiateInterestAdjust = UtilOperation.Arith.sub(todayNegotiateInterest, oldDailyNegotiateInterest);
	
								log.info("---------------------------------------------------------------");
								log.info("lhj debug info == 倒算后#正常余额是:" + todayNormalBalance);
								//log.info("lhj debug info == 倒算后-正常计息余额是:" + oldInterestBalance);
								log.info("lhj debug info == 倒算后#每日正常利息是:" + oldDailyInterest);
								//log.info("lhj debug info == 倒算后#正常利息值是:" + todayNormalInterest);
								log.info("---------------------------------------------------------------");
								log.info("lhj debug info == 倒算后#协定余额是:" + todayNegotiateBalance);
								//log.info("lhj debug info == 倒算后#每日协定利息是:" + oldDailyInterest);
								//log.info("lhj debug info == 倒算后#协定利息值是:" + oldNegotiateInterest);
	
								//为每日账户余额对象赋值
								//正常计息余额
								dailyAccountBalanceInfo.setInterestBalance(todayNormalBalance);
								//协定计息余额
								dailyAccountBalanceInfo.setAc_mNegotiateBalance(todayNegotiateBalance);
								//每日正常利息
								dailyAccountBalanceInfo.setDailyInterest(todayNormalInterest);
								//每日协定利息
								dailyAccountBalanceInfo.setAc_mDailyNegotiateInterest(todayNegotiateInterest);
								//正常利率
								dailyAccountBalanceInfo.setInterestRate(currentDepositAccountInterestInfo.getNormalInterestRate());
								//协定利率
								dailyAccountBalanceInfo.setAc_mNegotiateRate(negotiateRate);
								//协定利息
								dailyAccountBalanceInfo.setAc_mDailyNegotiateInterest(todayNegotiateInterest);
								//--------一般倒填交易-----------活期账户计息----------end-----------------------------------------------
								//正常利息的累计调整额
								accumulateNormalInterestAdjust = UtilOperation.Arith.add(accumulateNormalInterestAdjust, todayNormalInterestAdjust);
								//协定利息的累计调整额
								accumulateNegotiateInterestAdjust = UtilOperation.Arith.add(accumulateNegotiateInterestAdjust, todayNegotiateInterestAdjust);
								//新的累计正常利息							
								newAccumulateNormalInterest = UtilOperation.Arith.add(oldInterest, accumulateNormalInterestAdjust);
								//新的累计协定利息
								newAccumulateNegotiateInterest = UtilOperation.Arith.add(oldNegotiateInterest, accumulateNegotiateInterestAdjust);
								//							log.info("lhj debug info == 倒算后#累计正常利息是:" + newAccumulateNormalInterest);
								//							log.info("lhj debug info == 倒算后#累计协定利息是:" + newAccumulateNegotiateInterest);
								//							log.info("lhj debug info ==协定利率是:" + negotiateRate);
								//
								//							log.info("---------------------------------------------------------------");
								dailyAccountBalanceInfo.setInterest(newAccumulateNormalInterest);
								dailyAccountBalanceInfo.setAc_mNegotiateInterest(newAccumulateNegotiateInterest);
								//							log.info("〔一般倒填交易〕中开始进行活期账户计息结束");
							}
							else if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.TRUST || 
									accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CONSIGN || 
									accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.OTHERLOAN || 
									accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.YT)
							{
								//--------一般倒填交易------------贷款账户计息---------start----------------------------------------------
								log.info("〔一般倒填交易〕中开始进行贷款账户计息......");

								//新的计息余额
								newInterestBalance = UtilOperation.Arith.sub(dailyAccountBalanceInfo.getInterestBalance(), BackAmount);
								//倒算前的贷款利息
								//double oldLoanInterest = dailyAccountBalanceInfo.getInterest();
								//放款单号
								long loanNoteID = -1;
								loanNoteID = subAccountLoanInfo.getLoanNoteID();
								/*计算本日贷款利息*/
								InterestRate interestRate = interestCalculation.getInterestRateByPayForm(loanNoteID, tmpBackDate);
								//还没有实现2003-11-10//2003-11-20日在UtilOperation中增加一个方法,调用了张颜com.iss.itreasury.loan.loanpaynotice.dao中的getRateValue方法
								double AL_InterestRate = interestRate.getRate();
								//根据放款通知单取出利率
								long AL_InterestType = interestRate.getType();

								//根据放款通知单取出利率类型
								log.debug("********************************************************************");
								log.debug("lhj debug info ==贷款倒填前-新的计息余额：" + newInterestBalance);
								log.debug("lhj debug info ==放款通知单号码：" + loanNoteID);
								log.debug("lhj debug info ==倒填日期是： " + tmpBackDate);
								log.debug("lhj debug info ==贷款利率是： " + AL_InterestRate);
								log.debug("********************************************************************");
								//本日正常利息
								double todayNormalInterest = interestCalculation.calculateLoanAccountInterest(currencyID, AL_InterestRate, AL_InterestType, newInterestBalance, tmpBackDate, tmpBackDateAddOne);
								//本日利息增加额
								//todayNormalInterestAdjust = UtilOperation.Arith.sub(todayNormalInterest, dailyAccountBalanceInfo.getDailyInterest());
								todayNormalInterestAdjust = todayNormalInterest;
								//为每日账户余额对象赋值
								//							计息余额
								dailyAccountBalanceInfo.setInterestBalance(newInterestBalance);
								//当日贷款利率
								dailyAccountBalanceInfo.setInterestRate(AL_InterestRate);
								//本日正常利息
								dailyAccountBalanceInfo.setDailyInterest(todayNormalInterestAdjust);
	
								//--------一般倒填交易-------------贷款账户计息----------end------------------------------------------------
								accumulateNormalInterestAdjust = UtilOperation.Arith.add(accumulateNormalInterestAdjust, todayNormalInterestAdjust);
								//accumulateNegotiateInterestAdjust = UtilOperation.Arith.add(accumulateNegotiateInterestAdjust, todayNegotiateInterestAdjust);
								InterestsInfo interestsInfo = null;
								//减去当日还款金额
								try {
									interestsInfo = utilOperation.findAllClearCompoundInterset(SubAccountID, tmpBackDate);
								} catch (Exception e) {
									// TODO Auto-generated catch block
									throw new IException(e.getMessage());
								}
								if(interestsInfo!=null)
								{
									accumulateNormalInterestAdjust = UtilOperation.Arith.sub(accumulateNormalInterestAdjust,interestsInfo.getPayminterest());
								}
								//倒算后的累计正常利息
								newAccumulateNormalInterest = accumulateNormalInterestAdjust;
								dailyAccountBalanceInfo.setInterest(newAccumulateNormalInterest);
								
								//modify by leiyang3
								//2010/11/28
								//用于计算自营贷款到期后的罚息
								if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.TRUST)
								{
									LoanPayNoticeInfo loanPayNoticeInfo = utilOperation.findLoanPayNoticeByID(loanNoteID);
									if(loanPayNoticeInfo == null){
										throw new IException("没有找到有效的放款单信息");
									}
									
									Timestamp loanStartDate = loanPayNoticeInfo.getStart();
									Timestamp loanEndDate = loanPayNoticeInfo.getEnd();
									
									//-- 复利计算开始 --
									if(loanPayNoticeInfo.getIsRemitCompoundInterest()==Constant.TRUE) //通过合同是否时行复利属性，判断是否进行复利计算
									{
										boolean boolClearDay = utilOperation.isClearInterestDay(SubAccountID, tmpBackDate);
										double compoundAccumulateBalance = 0.0;
				
										//每日复利基数（有结息日后，当天为结息日情况）：上一日累积未还利息  + 上一日累积未还复利  + 上一日累积未还罚息
										if(boolClearDay == true)
										{
											compoundAccumulateBalance = UtilOperation.Arith.sub(newAccumulateNormalInterest, todayNormalInterestAdjust);
											compoundAccumulateBalance = UtilOperation.Arith.add(compoundAccumulateBalance, newAccumulateCompoundInterest);
											compoundAccumulateBalance = UtilOperation.Arith.add(compoundAccumulateBalance, newAccumulateOverDueInterest);
											arrearageInterest = compoundAccumulateBalance;
										}
										//每日复利基数（有结息日后，当天不为结息日情况）：子账户未还利息
										else
										{
											if(arrearageInterest>0)//上一日有欠息，当日非结息日计算本日前夕
											{
												InterestsInfo preinterestsInfo = null;
												try
												{
													preinterestsInfo = utilOperation.findPayInterestAmount(subAccountAssemblerInfo.getSubAccountLoanInfo().getID(),tmpBackDate);
												}
												catch(Exception e)
												{
													throw new IException(e.getMessage());
												}
												if(preinterestsInfo.getMinterest()-preinterestsInfo.getPayminterest()>interestsInfo.getPayminterest())
												{
													arrearageInterest = UtilOperation.Arith.sub(arrearageInterest ,interestsInfo.getPayminterest());
												}
												else if(preinterestsInfo.getMinterest()-preinterestsInfo.getPayminterest()<interestsInfo.getPayminterest()&&preinterestsInfo.getMinterest()>preinterestsInfo.getPayminterest())
												{
													arrearageInterest = UtilOperation.Arith.sub(arrearageInterest ,UtilOperation.Arith.sub(preinterestsInfo.getMinterest(),preinterestsInfo.getPayminterest()));
												}
												if(preinterestsInfo.getMcompoundinterest()-preinterestsInfo.getPaymcompoundinterest()>interestsInfo.getPaymcompoundinterest())
												{
													arrearageInterest =UtilOperation.Arith.sub( arrearageInterest ,interestsInfo.getPaymcompoundinterest());
												}
												else if(preinterestsInfo.getMcompoundinterest()-preinterestsInfo.getPaymcompoundinterest()<interestsInfo.getPaymcompoundinterest()&&preinterestsInfo.getMcompoundinterest()>preinterestsInfo.getPaymcompoundinterest())
												{
													arrearageInterest = UtilOperation.Arith.sub(arrearageInterest ,UtilOperation.Arith.sub(preinterestsInfo.getMcompoundinterest(),preinterestsInfo.getPaymcompoundinterest()));
												}
												if(preinterestsInfo.getMforfeitinterest()-preinterestsInfo.getPaymforfeitinterest()>interestsInfo.getPaymforfeitinterest())
												{
													arrearageInterest =UtilOperation.Arith.sub( arrearageInterest ,interestsInfo.getPaymforfeitinterest());
												}
												else if(preinterestsInfo.getMforfeitinterest()-preinterestsInfo.getPaymforfeitinterest()<interestsInfo.getPaymforfeitinterest()&&preinterestsInfo.getMforfeitinterest()>preinterestsInfo.getPaymforfeitinterest())
												{
													arrearageInterest = UtilOperation.Arith.sub(arrearageInterest ,UtilOperation.Arith.sub(preinterestsInfo.getMforfeitinterest(),preinterestsInfo.getPaymforfeitinterest()));
												}
											}
											}
										
										//复利利率
										InterestCalculationParameterInfo paramInfo = new InterestCalculationParameterInfo();
										paramInfo.setLoanNoteID(loanNoteID);
										paramInfo.setValidDate(tmpBackDate);
										InterestRate compoundRate = interestCalculation.getCompoundInterestRate(paramInfo);
										
										//倒算后的每日复利利息
										todayCompoundIntersetAdjust = interestCalculation.calculateLoanAccountInterest(lCurrencyID, compoundRate.getRate(), compoundRate.getType(), arrearageInterest, tmpBackDate, tmpBackDateAddOne);
										//倒算后的累计复利利息
										accumulateCompoundInterestAdjust = UtilOperation.Arith.add(newAccumulateCompoundInterest, todayCompoundIntersetAdjust);
										newAccumulateCompoundInterest = accumulateCompoundInterestAdjust;
										
										//保存每日复利利息
										dailyAccountBalanceInfo.setMcompounddailyinterset(todayCompoundIntersetAdjust);
										//累计复利利息
										dailyAccountBalanceInfo.setMcompoundinterest(newAccumulateCompoundInterest);
										//欠息
										dailyAccountBalanceInfo.setAl_mArrearageInterest(arrearageInterest);
									}
									//-- 复利计算结束 --
									
									//-- 罚息计算开始  --
									//是否进行罚息
									if(loanPayNoticeInfo.getIsRemitOverDueInterest()==Constant.TRUE) //通过合同是否时行罚息属性，判断是否进行罚息
									{
										if(!loanEndDate.after(tmpBackDate))
										{
											InterestCalculationParameterInfo paramInfo = new InterestCalculationParameterInfo();
											paramInfo.setLoanNoteID(loanNoteID);
											paramInfo.setValidDate(tmpBackDate);
											
											InterestRate overDueRate = interestCalculation.getOverDueInterestRate(paramInfo);
											//每日罚息总金额：贷款未还金额
											double overDueAccumulateBalance = newInterestBalance;
											//倒算后的每日罚息利息
											todayOverDueIntersetAdjust = interestCalculation.calculateLoanAccountInterest(lCurrencyID, overDueRate.getRate(), overDueRate.getType(), overDueAccumulateBalance, tmpBackDate, tmpBackDateAddOne);
											//倒算后的累计罚息利息
											accumulateOverDueInterestAdjust = UtilOperation.Arith.add(newAccumulateOverDueInterest, todayOverDueIntersetAdjust);
											newAccumulateOverDueInterest = accumulateOverDueInterestAdjust;
											
											//保存每日罚息利息
											dailyAccountBalanceInfo.setMforfeitdailyinterset(todayOverDueIntersetAdjust);
											//累计罚息利息
											dailyAccountBalanceInfo.setMforfeitinterest(newAccumulateOverDueInterest);
											//当日罚息利率	     
											dailyAccountBalanceInfo.setMForfeitInterestRate(overDueRate.getRate());
											//重新设置当日新增贷款利率
											dailyAccountBalanceInfo.setDailyInterest(0.0);
											//重新设置累计贷款利息
											accumulateNormalInterestAdjust = UtilOperation.Arith.sub(newAccumulateNormalInterest, todayNormalInterestAdjust);
											newAccumulateNormalInterest = accumulateNormalInterestAdjust;
											dailyAccountBalanceInfo.setInterest(newAccumulateNormalInterest);
										}
									}
									//-- 罚息计算结束 --
								}
	
								//log.info("lhj debug info ==[" + SubAccountID + "]贷款累计正常利息是:" + newAccumulateNormalInterest);
								//log.info("〔一般倒填交易〕中开始进行贷款账户计息结束");
							}
	
							//                         为账户余额日结表中更新一条新记录
	
							try
							{
								//log.info("〔一般倒填交易〕中开始更新账户余额日结表中日期是" + tmpBackDate.toString() + "的数据.....");
								sett_DailyAccountBalanceDAO.update(dailyAccountBalanceInfo);
							}
							catch (Exception e4)
							{
								e4.printStackTrace();
								throw new IException("保存失败，请重试！");
							}
							//log.info("〔一般倒填交易〕中开始更新账户余额日结表中日期是" + tmpBackDate.toString() + "的数据成功！");
							//--------------------------------一般倒填交易----------------------end---------------------------------------
						}
					} //－－－－循环结束－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
					//log.info("循环结束");
					//更新子账户表
					try
					{
						//log.info("开始更新子账户表........");
						if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CURRENT||
								accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.BAK ||
								accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.RESERVE ||
								accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.LENDING)
						{
							//log.info("开始更新子账户表中活期账户信息........");
							//---------------------------------------------------------------------------------  
							double newSubNormalInterest = UtilOperation.Arith.add(executeDateNormalInteres, accumulateNormalInterestAdjust);
							double newSubNegotiateInterest = UtilOperation.Arith.add(executeDateNegotiateInterest, accumulateNegotiateInterestAdjust);
							subAccountCurrentInfo.setInterest(newSubNormalInterest);
							subAccountCurrentInfo.setNegotiateInterest(newSubNegotiateInterest);
							//---------------------------------------------------------------------------------
							//更新子账户中的活期账户,更新成功，单户计息倒填返回值设置为1 
							sett_SubAccountDAO.updateSubAccountCurrent(subAccountCurrentInfo);
							//在关机处理时候，更新最后一日的账户余额日结表记录，只修改利息mInterest
							if (isCloseSystem == SETTConstant.BooleanValue.ISTRUE)
							{
								//							DailyAccountBalanceInfo lastCurrentDailyAccountBalanceInfo = sett_DailyAccountBalanceDAO.findAllBySubAccountIDAndDate(SubAccountID, lOfficeID, lCurrencyID, ExecuteDate);
								//							if (lastCurrentDailyAccountBalanceInfo == null)
								//							{
								//								throw new IException(true, "无法在账户余额表中找到对应的信息，交易失败", null);
								//							}
								//							lastCurrentDailyAccountBalanceInfo.setInterest(newSubNormalInterest);
								//							lastCurrentDailyAccountBalanceInfo.setAc_mNegotiateInterest(newSubNegotiateInterest);
								//							sett_DailyAccountBalanceDAO.update(lastCurrentDailyAccountBalanceInfo);
	
							}
	
						}
						//Boxu Add 2008年3月21日 保证金 
						else if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.MARGIN)
						{
							log.info("开始更新子账户表中保证金账户信息........");
							//---------------------------------------------------------------------------------  
							double newSubNormalInterest = UtilOperation.Arith.add(executeDateMarginInterest, accumulateNormalInterestAdjust);
							subAccountFixedInfo.setInterest(newSubNormalInterest);
							//---------------------------------------------------------------------------------
							//更新子账户中的保证金账户,更新成功，单户计息倒填返回值设置为1 
							sett_SubAccountDAO.updateSubAccountFix(subAccountFixedInfo);
							//在关机处理时候，更新最后一日的账户余额日结表记录，只修改利息mInterest
							if (isCloseSystem == SETTConstant.BooleanValue.ISTRUE)
							{
								//							DailyAccountBalanceInfo lastCurrentDailyAccountBalanceInfo = sett_DailyAccountBalanceDAO.findAllBySubAccountIDAndDate(SubAccountID, lOfficeID, lCurrencyID, ExecuteDate);
								//							if (lastCurrentDailyAccountBalanceInfo == null)
								//							{
								//								throw new IException(true, "无法在账户余额表中找到对应的信息，交易失败", null);
								//							}
								//							lastCurrentDailyAccountBalanceInfo.setInterest(newSubNormalInterest);
								//							lastCurrentDailyAccountBalanceInfo.setAc_mNegotiateInterest(newSubNegotiateInterest);
								//							sett_DailyAccountBalanceDAO.update(lastCurrentDailyAccountBalanceInfo);
	
							}
	
						}
						else if(accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.TRUST)
						{
							//保存子账户正常利息
							double newSubLoanInterest = accumulateNormalInterestAdjust;
							subAccountLoanInfo.setInterest(newSubLoanInterest);
							//保存子账户复利利息
							double newSubLoanCompoundInterest =  accumulateCompoundInterestAdjust;
							subAccountLoanInfo.setCompoundInterest(newSubLoanCompoundInterest);
							
							//保存子账户罚息利息
							double newSubLoanOverDueInterest =   accumulateOverDueInterestAdjust;
							subAccountLoanInfo.setOverDueInterest(newSubLoanOverDueInterest);
							
							//保存子账户未还利息
							subAccountLoanInfo.setArrearageInterest(arrearageInterest);
							
							sett_SubAccountDAO.updateSubAccountLoan(subAccountLoanInfo);
						
						}
						else if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CONSIGN || 
							accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.OTHERLOAN || 
							accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.YT)
						{
							//log.info("开始更新子账户表中贷款信息........");
							//**********************************************************************************
							double newSubLoanInterest =  accumulateNormalInterestAdjust;
							subAccountLoanInfo.setInterest(newSubLoanInterest);

							//更新子账户中的贷款账户 ,更新成功，单户计息倒填返回值设置为1 
							sett_SubAccountDAO.updateSubAccountLoan(subAccountLoanInfo);
	
							if (isCloseSystem == SETTConstant.BooleanValue.ISTRUE)
							{
								//							//log.info("更新子账户表中贷款信息成功！");
								//							//EXECUTE当天的倒填在循环外面进行！//2003-12-05
								//							//在关机处理时候，更新最后一日的账户余额日结表记录，只修改利息mInterest
								//							DailyAccountBalanceInfo lastLoanDailyAccountBalanceInfo = sett_DailyAccountBalanceDAO.findAllBySubAccountIDAndDate(SubAccountID, lOfficeID, lCurrencyID, ExecuteDate);
								//							if (lastLoanDailyAccountBalanceInfo == null)
								//							{
								//								throw new IException(true, "无法在账户余额表中找到对应的信息，交易失败", null);
								//							}
								//							lastLoanDailyAccountBalanceInfo.setInterest(newSubLoanInterest);
								//							sett_DailyAccountBalanceDAO.update(lastLoanDailyAccountBalanceInfo);
							}
							//*************************************************************************************
						}
						//将倒填交易的状态修改！2003-11-25 lhj 
						sett_TransAccountDetailDAO sett_TransAccountDetailDAO = new sett_TransAccountDetailDAO(conn);
						//修改账户交易倒填标志！！！
						sett_TransAccountDetailDAO.updateInterestBackFlag(AccountID, SubAccountID, lCurrencyID, BackDate, ExecuteDate);
						//倒填成功
						isSuccess = 1;
					}
					catch (IException e)
					{
						e.printStackTrace();
						throw e;
					}
					catch (Exception e2)
					{
						e2.printStackTrace();
						throw new IException("Gen_E001");
					}
				} //定期存款和贴现账户不进行计息
			}
		}
		return isSuccess;
	}

	/**
	 * 接口名称：单户结息倒填处理
	 * 功能描述：1：单户结息倒填时，更新账户余额日结表中该账户每一日的累计利息和累计欠息等信息。
	 *           2：“利息费用结算”，“利息费用支付”，“贷款归还”等交易进行账户结息时，结息日小于
	 *              当前日时，须先执行本接口。
	 *           3：删除倒填结息记录时也要调用本接口。
	 * @param AccountID    主账户ID
	 * @param SubAccountID 子账户ID
	 * @param BackDate     倒填交易起息日
	 * @param BackAmount   倒填交易金额
	 * @param ExecuteDate  执行日/关机日期
	 * @return long -1:不成功，1：成功
	 * @throws IException
	 */
	public long accountInterestSettlelmentBackward(long AccountID, long SubAccountID, Timestamp BackDate, double BackAmount, long lofficeID, long lCurrencyID, Timestamp ExecuteDate,long amountType) throws IException
	{
		log.info("单户结息倒填处理 AccountID == " + AccountID);
		log.info("单户结息倒填处理 SubAccountID == " + SubAccountID);
		log.info("单户结息倒填处理 BackDate == " + BackDate);
		log.info("单户结息倒填处理 BackAmount == " + BackAmount);
		log.info("单户结息倒填处理 ExecuteDate == " + ExecuteDate);

		//返回值
		long isSuccess = -1;
		Connection conn = this.conn_Out;
		double accumulateNormalInterestAdjust = 0.0; //累计正常利息调整额
		double accumulateNegotiateInterestAdjust = 0.0; //累计协定利息调整额
		double accumulateCompoundInterestAdjust = 0.0;//累计复利整额
		double accumulateOverDueInterestAdjust = 0.0; //累计逾期利息调整额
		double tmpArrearageInterest = 0;

		//根据AccountID在sett_account表中定位主账户记录
		Sett_AccountDAO sett_AccountDAO = new Sett_AccountDAO(conn);
		AccountInfo accountInfo = null;
		try
		{
			accountInfo = sett_AccountDAO.findByID(AccountID);
			if (accountInfo == null)
			{
				throw new IException(true, "主账户表没有对应记录，交易失败", null);
			}
		}
		catch (IException ie)
		{
			ie.printStackTrace();
			throw ie;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IException(true, "无法在主账户表中找到对应的信息，交易失败", null);
		}

		//根据SubAccountID在sett_SubAccount表中定位子账户记录
		Sett_SubAccountDAO sett_SubAccountDAO = new Sett_SubAccountDAO(conn);
		SubAccountAssemblerInfo subAccountAssemblerInfo = null;
		//子账户中的活期存款信息
		SubAccountCurrentInfo subAccountCurrentInfo = null;
		//账户当前余额
		double currentBalance = 0.0;
		try
		{
			subAccountAssemblerInfo = sett_SubAccountDAO.findByID(SubAccountID);
			if (subAccountAssemblerInfo == null)
			{
				throw new IException(true, "子账户表中没有对应记录，交易失败", null);
			}
			//子账户中的活期账户对象	
			subAccountCurrentInfo = subAccountAssemblerInfo.getSubAccountCurrenctInfo();

		}
		catch (IException ie)
		{
			ie.printStackTrace();
			throw ie;
		}
		catch (SQLException SQLe1)
		{
			// TODO Auto-generated catch block
			SQLe1.printStackTrace();
			throw new IException(true, "无法在子账户表中找到对应的信息，交易失败", null);
		}

		//	CurrentDepositAccountInterestInfo currentDepositAccountInterestInfo = null;

		if (accountInfo != null && accountInfo.getAccountID() > 0)
		{

			long currencyID = accountInfo.getCurrencyID(); //主账户中币种
			long accountTypeID = accountInfo.getAccountTypeID(); //主账户类型
			log.info("lhj debug info == 主账户账户类型是：" + accountTypeID);
	        Sett_AccountTypeDAO sett_AccountTypeDAO = new Sett_AccountTypeDAO();
	        AccountTypeInfo accountTypeInfo = null;
	        try {
				accountTypeInfo = sett_AccountTypeDAO.findByID(accountTypeID);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if (accountTypeInfo != null) {
				//定期存款和贴现账户不进行计息
				if (accountTypeInfo.getAccountGroupID() != SETTConstant.AccountGroupType.FIXED && 
					accountTypeInfo.getAccountGroupID() != SETTConstant.AccountGroupType.NOTIFY && 
					accountTypeInfo.getAccountGroupID() != SETTConstant.AccountGroupType.DISCOUNT)
				{
					//是否协定存款
					long isNegotiate = subAccountCurrentInfo.getIsNegotiate();
					if (isNegotiate != 1)
					{
						//一般活期或者贷款
						//log.info("一般活期或贷款计算累计正常利息调整额和累计协定利息调整额");
						accumulateNormalInterestAdjust = BackAmount;
						accumulateNegotiateInterestAdjust = 0.0;
						
						if(amountType == SETTConstant.InterestFeeType.FORFEITINTEREST){
							accumulateOverDueInterestAdjust =  BackAmount;
						}
					}
					else
					{
						//协定存款
						//log.info("协定存款计算累计正常利息调整额和累计协定利息调整额");
						//倒填结息日的昨天
						Timestamp backDateDecreaseOne = getNextNDay(BackDate, -1);
	
						DailyAccountBalanceInfo dailyAccountBalanceInfo = null;
						try
						{
							dailyAccountBalanceInfo = sett_DailyAccountBalanceDAO.findAllBySubAccountIDAndDate(SubAccountID, lofficeID, lCurrencyID, backDateDecreaseOne);
							if (dailyAccountBalanceInfo == null)
							{
								throw new IException(true, "账户余额表中没有对应记录，交易失败", null);
							}
						}
						catch (IException ie)
						{
							ie.printStackTrace();
							throw ie;
						}
						catch (SQLException SQLe1)
						{
							SQLe1.printStackTrace();
							throw new IException("Gen_E001");
						}
						if (dailyAccountBalanceInfo != null && dailyAccountBalanceInfo.getAccountID() > 0)
						{
							//如果BackAmount是负数，则表示是删除结息，所计算出的累计利息调整额和累计协定利息调整额取反
							long isNegative = -1;
							if (BackAmount < 0){
								isNegative = -1;
							}else{
								isNegative = 1;
							}
							//累计正常利息调整额	
							accumulateNormalInterestAdjust = UtilOperation.Arith.mul(isNegative, dailyAccountBalanceInfo.getInterest());
							//累计协定利息调整额
							accumulateNegotiateInterestAdjust = UtilOperation.Arith.mul(isNegative, dailyAccountBalanceInfo.getAc_mNegotiateInterest());
							log.info("lhj debug info ==单户结息倒填处理==累计正常利息调整额== " + accumulateNormalInterestAdjust);
							log.info("lhj debug info ==单户结息倒填处理==累计协定利息调整额== " + accumulateNegotiateInterestAdjust);
						}
					}

					//循环计算新的累计利息调整额和新的累计协定利息调整额
					//循环次数，即为BackDate和ExecuteDate之间的 天数
					int loopDays = (int) interestCalculation.getIntervalDays(BackDate, ExecuteDate, 1);
					log.info("－－－－循环开始－－－－－－－－－－－－－不含ExecuteDate当天－－－－－－－－－－－－－－－－－－－");
					for (int i = 0; i < loopDays; i++)
					{
						//－－－－循环开始－－－－－－－－－－－－－不含ExecuteDate当天－－－－－－－－－－－－－－－－－－－
						Timestamp tmpBackDate = getNextNDay(BackDate, i);
	
						DailyAccountBalanceInfo tmpDailyAccountBalanceInfo = null;
	
						try
						{
							tmpDailyAccountBalanceInfo = sett_DailyAccountBalanceDAO.findAllBySubAccountIDAndDate(SubAccountID, lofficeID, lCurrencyID, tmpBackDate);
						}
						catch (SQLException SQLe2)
						{
							SQLe2.printStackTrace();
							throw new IException("Gen_E001");
						}
						//新的累计正常利息
						double newAccumulateNormalInterest = 0.0;
						//新的累计协定利息
						double newAccumulateNegotiateInterest = 0.0;
						//新的累计欠息
						double newAccumulateArrearageInterest = 0.0;
						//新的逾期欠息
						double newAccumulateOverDueAmount = 0.0;
	
						if (tmpDailyAccountBalanceInfo != null && tmpDailyAccountBalanceInfo.getAccountID() > 0)
						{
							//新的累计正常利息
							newAccumulateNormalInterest = UtilOperation.Arith.sub(tmpDailyAccountBalanceInfo.getInterest(), accumulateNormalInterestAdjust);
							//新的累计协定利息
							newAccumulateNegotiateInterest = UtilOperation.Arith.sub(tmpDailyAccountBalanceInfo.getAc_mNegotiateInterest(), accumulateNegotiateInterestAdjust);
							log.debug( "测试数据0610＝：日期是"+ tmpBackDate +"累计利息值："+tmpDailyAccountBalanceInfo.getInterest());
	                        log.debug( "测试数据0610＝：日期是"+ tmpBackDate +"累计欠息值："+tmpDailyAccountBalanceInfo.getAl_mArrearageInterest());
							if (tmpDailyAccountBalanceInfo.getAl_mArrearageInterest() > 0)
							{
								log.debug( "测试数据0610＝：日期是"+ tmpBackDate +"累计欠息值进行倒填......！");
	                            log.debug( "测试数据0610＝：日期是"+ tmpBackDate +"累计正常利息调整额:"+accumulateNormalInterestAdjust);
								newAccumulateArrearageInterest = UtilOperation.Arith.sub(tmpDailyAccountBalanceInfo.getAl_mArrearageInterest(), accumulateNormalInterestAdjust);
								log.debug( "测试数据0610＝：日期是"+ tmpBackDate +"累计欠息值倒填后的值："+newAccumulateArrearageInterest);
	                            if (newAccumulateArrearageInterest < 0)
								{
									newAccumulateArrearageInterest = 0;
								}
							}
							//---------------------------------------------------------------------------------------
							//if (tmpDailyAccountBalanceInfo.getAl_mOverDueAmount() > 0)
							//{
							//	newOverDueAmount = UtilOperation.Arith.sub(tmpDailyAccountBalanceInfo.getAl_mOverDueAmount(), accumulateNormalInterestAdjust);
							//	if (newOverDueAmount < 0)
							//	{
							//		newOverDueAmount = 0;
							//	}
							//}
							//---------------------------------------------------------------------------------------
							log.info("lhj debug info &&单户结息倒填处理&&新的累计正常利息&& " + newAccumulateNormalInterest);
							log.info("lhj debug info &&单户结息倒填处理&&新的累计协定利息&& " + newAccumulateNegotiateInterest);
							log.info("lhj debug info &&单户结息倒填处理&&&&新的累计欠息&&&& " + newAccumulateArrearageInterest);
							//log.info("lhj debug info &&单户结息倒填处理&&&&新的逾期欠息&&&& " + newOverDueAmount);
							//将新的累计正常利息，新的累计协定利息，新的累计欠息更新到每日账户余额表（sett_DailyAccountBalance）
							tmpDailyAccountBalanceInfo.setInterest(newAccumulateNormalInterest);
							tmpDailyAccountBalanceInfo.setAc_mNegotiateInterest(newAccumulateNegotiateInterest);
							tmpDailyAccountBalanceInfo.setAl_mArrearageInterest(newAccumulateArrearageInterest);
							//tmpDailyAccountBalanceInfo.setAl_mOverDueAmount(newOverDueAmount);
							
							//新增对自营贷款结复利的特殊处理
							UtilOperation utilOperation = new UtilOperation(conn);
							if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.TRUST)
							{
								long loanNoteID = subAccountAssemblerInfo.getSubAccountLoanInfo().getLoanNoteID();
								LoanPayNoticeInfo loanPayNoticeInfo = utilOperation.findLoanPayNoticeByID(loanNoteID);
								if(loanPayNoticeInfo == null){
									throw new IException("没有找到有效的放款单信息");
								}
								
								Timestamp loanStartDate = loanPayNoticeInfo.getStart();
								Timestamp loanEndDate = loanPayNoticeInfo.getEnd();
								
								//-- 复利计算开始 --
								if(loanPayNoticeInfo.getIsRemitCompoundInterest()==Constant.TRUE) //通过合同是否时行复利属性，判断是否进行复利计算
								{
									//利息费用支付及结息还息情况冲减累计欠息
									//正常交易还息是需要判断是否需要冲减累计欠息
									InterestsInfo preinterestsInfo = null;
									InterestsInfo todayinterestsInfo = null;
									DailyAccountBalanceInfo preDailyAccountBalanceInfo = null;
									try
									{
										preinterestsInfo = utilOperation.findPayInterestAmount(subAccountAssemblerInfo.getSubAccountLoanInfo().getID(),tmpBackDate);
										todayinterestsInfo = utilOperation.findAllClearCompoundInterset(subAccountAssemblerInfo.getSubAccountLoanInfo().getID(),tmpBackDate);
										preDailyAccountBalanceInfo = sett_DailyAccountBalanceDAO.findAllBySubAccountIDAndDate(SubAccountID, lofficeID, lCurrencyID,getNextNDay(tmpBackDate, -1));
										tmpArrearageInterest = preDailyAccountBalanceInfo.getAl_mArrearageInterest();
										accumulateCompoundInterestAdjust = preDailyAccountBalanceInfo.getMcompoundinterest();
									}
									catch(Exception e)
									{
										throw new IException(e.getMessage());
									}
									double Payminterest = 0;
									double Paymcompoundinterest = 0;
									double Paymforfeitinterest = 0;
									if(amountType==SETTConstant.InterestFeeType.INTEREST&&i==0)
									{
										Payminterest = UtilOperation.Arith.add(BackAmount,todayinterestsInfo.getPayminterest());
									}
									else
									{
										Payminterest = todayinterestsInfo.getPayminterest();
									}
									if(amountType==SETTConstant.InterestFeeType.COMPOUNDINTEREST&&i==0)
									{
										Paymcompoundinterest = UtilOperation.Arith.add(BackAmount,todayinterestsInfo.getPaymcompoundinterest());
									}
									else
									{
										Paymcompoundinterest = todayinterestsInfo.getPaymcompoundinterest();
									}
									if(amountType==SETTConstant.InterestFeeType.FORFEITINTEREST&&i==0)
									{
										Paymforfeitinterest = UtilOperation.Arith.add(BackAmount,todayinterestsInfo.getPaymforfeitinterest());
									}
									else
									{
										Paymforfeitinterest = todayinterestsInfo.getPaymforfeitinterest();
									}
									if(!utilOperation.isClearInterestDay(SubAccountID, tmpBackDate))
									{
									
										if(preinterestsInfo.getMinterest()-preinterestsInfo.getPayminterest()>Payminterest)
										{
											tmpArrearageInterest = UtilOperation.Arith.sub(tmpArrearageInterest ,Payminterest);
										}
										else if(preinterestsInfo.getMinterest()-preinterestsInfo.getPayminterest()<Payminterest&&preinterestsInfo.getMinterest()>preinterestsInfo.getPayminterest())
										{
											tmpArrearageInterest = UtilOperation.Arith.sub(tmpArrearageInterest ,UtilOperation.Arith.sub(preinterestsInfo.getMinterest(),preinterestsInfo.getPayminterest()));
										}
										if(preinterestsInfo.getMcompoundinterest()-preinterestsInfo.getPaymcompoundinterest()>Paymcompoundinterest)
										{
											tmpArrearageInterest =UtilOperation.Arith.sub( tmpArrearageInterest ,Paymcompoundinterest);
										}
										else if(preinterestsInfo.getMcompoundinterest()-preinterestsInfo.getPaymcompoundinterest()<Paymcompoundinterest&&preinterestsInfo.getMcompoundinterest()>preinterestsInfo.getPaymcompoundinterest())
										{
											tmpArrearageInterest = UtilOperation.Arith.sub(tmpArrearageInterest ,UtilOperation.Arith.sub(preinterestsInfo.getMcompoundinterest(),preinterestsInfo.getPaymcompoundinterest()));
										}
									
										if(preinterestsInfo.getMforfeitinterest()-preinterestsInfo.getPaymforfeitinterest()>Paymforfeitinterest)
										{
											tmpArrearageInterest =UtilOperation.Arith.sub( tmpArrearageInterest ,Paymforfeitinterest);
										}
										else if(preinterestsInfo.getMforfeitinterest()-preinterestsInfo.getPaymforfeitinterest()<Paymforfeitinterest&&preinterestsInfo.getMforfeitinterest()>-preinterestsInfo.getPaymforfeitinterest())
										{
											tmpArrearageInterest = UtilOperation.Arith.sub(tmpArrearageInterest ,UtilOperation.Arith.sub(preinterestsInfo.getMforfeitinterest(),preinterestsInfo.getPaymforfeitinterest()));
										}
									}
									else
									{
										tmpArrearageInterest = UtilOperation.Arith.add(UtilOperation.Arith.add(UtilOperation.Arith.sub(preDailyAccountBalanceInfo.getInterest(),Payminterest),
										UtilOperation.Arith.sub(preDailyAccountBalanceInfo.getMcompoundinterest(),Paymcompoundinterest)),
										UtilOperation.Arith.sub(preDailyAccountBalanceInfo.getMforfeitinterest(),Paymforfeitinterest));
									}
									if(amountType==SETTConstant.InterestFeeType.COMPOUNDINTEREST&&i==0)
									{
										todayinterestsInfo.setPaymcompoundinterest(UtilOperation.Arith.add(BackAmount,todayinterestsInfo.getPaymcompoundinterest()));
									}
									accumulateCompoundInterestAdjust = UtilOperation.Arith.sub(accumulateCompoundInterestAdjust ,todayinterestsInfo.getPaymcompoundinterest());
									
									if(accumulateCompoundInterestAdjust<0.01)
									{
										accumulateCompoundInterestAdjust = 0;
									}
									if(tmpArrearageInterest<0.01)
									{
										tmpArrearageInterest = 0;
									}
										
									
									//复利利率
									InterestCalculationParameterInfo paramInfo = new InterestCalculationParameterInfo();
									paramInfo.setLoanNoteID(subAccountAssemblerInfo.getSubAccountLoanInfo().getLoanNoteID());
									paramInfo.setValidDate(tmpBackDate);
									InterestRate compoundRate = interestCalculation.getCompoundInterestRate(paramInfo);
									double todayCompoundIntersetAdjust=0;
									//倒算后的每日复利利息
									todayCompoundIntersetAdjust = interestCalculation.calculateLoanAccountInterest(lCurrencyID, compoundRate.getRate(), compoundRate.getType(), tmpArrearageInterest, tmpBackDate, getNextNDay(tmpBackDate, 1));
									//倒算后的累计复利利息
									accumulateCompoundInterestAdjust = UtilOperation.Arith.add(accumulateCompoundInterestAdjust, todayCompoundIntersetAdjust);
									tmpDailyAccountBalanceInfo.setAl_mArrearageInterest(tmpArrearageInterest);
									
									//保存每日复利利息
									tmpDailyAccountBalanceInfo.setMcompounddailyinterset(todayCompoundIntersetAdjust);
									//累计复利利息
									tmpDailyAccountBalanceInfo.setMcompoundinterest(accumulateCompoundInterestAdjust);
								}
								//-- 复利计算结束 --
								
								//-- 罚息计算开始  --
								//是否进行罚息
								if(loanPayNoticeInfo.getIsRemitOverDueInterest()==Constant.TRUE) //通过合同是否时行罚息属性，判断是否进行罚息
								{
									if(!loanEndDate.after(tmpBackDate))
									{
										if(amountType == SETTConstant.InterestFeeType.FORFEITINTEREST)
										{
											//新的累计罚息利息
											newAccumulateOverDueAmount = UtilOperation.Arith.sub(tmpDailyAccountBalanceInfo.getMforfeitinterest(), accumulateOverDueInterestAdjust);

											//每日罚息总金额：贷款未还金额
											//double overDueAccumulateBalance = newInterestBalance;
											//倒算后的每日罚息利息
											//todayOverDueIntersetAdjust = interestCalculation.calculateLoanAccountInterest(lCurrencyID, overDueRate.getRate(), overDueRate.getType(), overDueAccumulateBalance, tmpBackDate, tmpBackDateAddOne);
											//倒算后的累计罚息利息
											//accumulateOverDueInterestAdjust = UtilOperation.Arith.add(newAccumulateOverDueInterest, todayOverDueIntersetAdjust);
											//newAccumulateOverDueInterest = accumulateOverDueInterestAdjust;
											
											//保存每日罚息利息
											//dailyAccountBalanceInfo.setMforfeitdailyinterset(todayOverDueIntersetAdjust);
											//累计罚息利息
											tmpDailyAccountBalanceInfo.setMforfeitinterest(newAccumulateOverDueAmount);
											//重新设置当日新增贷款利率
											//dailyAccountBalanceInfo.setDailyInterest(0.0);
											//重新设置累计贷款利息
											//accumulateNormalInterestAdjust = UtilOperation.Arith.sub(newAccumulateNormalInterest, todayNormalInterestAdjust);
											//newAccumulateNormalInterest = accumulateNormalInterestAdjust;
											//dailyAccountBalanceInfo.setInterest(newAccumulateNormalInterest);
										}
									}
								}
								//-- 罚息计算结束 --
							}
							//自营贷款结复利结束
							//update the sett_DailyAccountBalance
							try
							{
								//log.info("开始更新" + tmpBackDate.toString() + "的账户余额日结表中的一条记录...");
								sett_DailyAccountBalanceDAO.update(tmpDailyAccountBalanceInfo);
								//log.info("更新" + tmpBackDate.toString() + "成功！");
							}
							catch (Exception e2)
							{
								e2.printStackTrace();
								throw new IException("Gen_E001");
							}
						}
					} //－－－－循环结束－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
					//log.info("//－－－－循环结束－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－");
					isSuccess = 1;
				} //定期存款和贴现账户不计息	
				else
				{
					//log.info("lhj debug info == 定期存款和贴现账户不计息");
					isSuccess = 1; //hjliu
				}
			}
		}
		return isSuccess;
	}
	
	/**
	 * 接口名称：单户结息倒填处理
	 * 功能描述：1：单户结息倒填时，更新账户余额日结表中该账户每一日的累计利息和累计欠息等信息。
	 *           2：“利息费用结算”，“利息费用支付”，“贷款归还”等交易进行账户结息时，结息日小于
	 *              当前日时，须先执行本接口。
	 *           3：删除倒填结息记录时也要调用本接口。
	 * @param AccountID    主账户ID
	 * @param SubAccountID 子账户ID
	 * @param BackDate     倒填交易起息日
	 * @param BackAmount   倒填交易金额
	 * @param ExecuteDate  执行日/关机日期
	 * @return long -1:不成功，1：成功
	 * @throws IException
	 */
	public long accountInterestSettlelmentBackward(TransRepaymentLoanInfo transInfo) throws IException
	{
		log.info("单户结息倒填处理 AccountID == " + transInfo.getLoanAccountID());
		log.info("单户结息倒填处理 LoanNoteID == " + transInfo.getLoanNoteID());
		//log.info("单户结息倒填处理 SubAccountID == " + SubAccountID);
		log.info("单户结息倒填处理 BackDate == " + transInfo.getInterestClear());
		//log.info("单户结息倒填处理 BackAmount == " + BackAmount);
		log.info("单户结息倒填处理 ExecuteDate == " + transInfo.getExecute());
		
		Timestamp backDate = transInfo.getInterestClear();
		Timestamp executeDate = transInfo.getExecute();

		long isSuccess = -1; //成功标志
		Connection conn = this.conn_Out;
		double accumulateNormalInterestAdjust = 0.0; //累计正常利息调整额
		double accumulateCompoundInterestAdjust = 0.0; //累计复利利息调整额
		double accumulateOverDueInterestAdjust = 0.0; //累计罚息利息调整额
		double newAccumulateNormalInterest = 0.0; //新的累计正常利息调整额
		double newAccumulateCompoundInterest = 0.0; //新的累计复利利息调整额
		double newAccumulateOverDueInterest = 0.0; //新的累计罚息利息调整额
		double executeDateLoanInterest = 0.0; //贷款子账户的当前利息（即在关机算息后的贷款利息）
		double executeDateCompoundInterest = 0.0; //贷款子账户的当前复利（即在关机算息后的贷款复利）
		double executeDateOverDueInterest = 0.0; //贷款子账户的当前罚息（即在关机算息后的贷款罚息）
		double arrearageInterest = 0.0; //欠息金额
		//计息余额
		double interestBalance = 0.0;

		
		//根据AccountID在sett_account表中定位主账户记录

		Sett_AccountDAO sett_AccountDAO = new Sett_AccountDAO(conn);
		UtilOperation utilOperation = new UtilOperation(conn);
		AccountInfo accountInfo = null;
		try
		{
			accountInfo = sett_AccountDAO.findByID(transInfo.getLoanAccountID());
			if (accountInfo == null)
			{
				throw new IException(true, "主账户表中没有对应记录，交易失败", null);
			}
		}
		catch (IException ie)
		{
			throw ie;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IException(true, "无法在主账户表中找到对应的信息，交易失败", null);
		}
		log.info("单户计息倒填中取主账户成功！");
		//根据SubAccountID在sett_SubAccount表中定位子账户记录
		Sett_SubAccountDAO sett_SubAccountDAO = new Sett_SubAccountDAO(conn);
		SubAccountAssemblerInfo subAccountAssemblerInfo = null;
		try
		{
			log.info("单户计息倒填中取子账户....");
			subAccountAssemblerInfo = sett_SubAccountDAO.findByLoanNoteID(transInfo.getLoanAccountID(), transInfo.getLoanNoteID());
		}
		catch (SQLException e1)
		{
			e1.printStackTrace();
			throw new IException(true, "无法在字子账户表中找到对应的信息，交易失败", null);
		}
		log.info("单户计息倒填中取子账户成功！");
		//子账户中的贷款信息
		SubAccountLoanInfo subAccountLoanInfo = null;

		if (subAccountAssemblerInfo != null)
		{
			//取出在关机倒算前的当前子账户的正常利息和协定利息（活期子账户）、当前利息（贷款子账户）
			//目的：在结束关机倒算后，更新每日算息后的子账户的账户余额日结表记录
			//      更新累计利息字段：
			//      新的累计利息 = 关机倒算前的利息 + 累计利息调整额
			//      此步骤是在结束循环并且更新完子账户后，在修改倒填标志之前进行

			//贷款子账户------------------------------------------------------------------
			subAccountLoanInfo = subAccountAssemblerInfo.getSubAccountLoanInfo();
			//贷款子账户的当前利息（即在关机算息后的贷款利息）
			executeDateLoanInterest = subAccountLoanInfo.getInterest();
			//贷款子账户的当前复利（即在关机算息后的贷款复利）
			executeDateCompoundInterest = subAccountLoanInfo.getCompoundInterest();
			//贷款子账户的当前罚息（即在关机算息后的贷款罚息）
			executeDateOverDueInterest = subAccountLoanInfo.getOverDueInterest();
			//贷款子账户------------------------------------------------------------------

		}
		else
		{
			throw new IException(true, "无法在子账户表中找到对应的信息，交易失败", null);
		}

		if (accountInfo != null)
		{

			long currencyID = accountInfo.getCurrencyID(); //主账户中币种
			long accountTypeID = accountInfo.getAccountTypeID();
	        Sett_AccountTypeDAO sett_AccountTypeDAO = new Sett_AccountTypeDAO();
	        AccountTypeInfo accountTypeInfo = null;
	        try {
				accountTypeInfo = sett_AccountTypeDAO.findByID(accountTypeID);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if (accountTypeInfo != null) {
				//本接口对定期存款和贴现账户不进行计息
				if (accountTypeInfo.getAccountGroupID() != SETTConstant.AccountGroupType.FIXED && 
					accountTypeInfo.getAccountGroupID() != SETTConstant.AccountGroupType.NOTIFY && 
					accountTypeInfo.getAccountGroupID() != SETTConstant.AccountGroupType.DISCOUNT)
				{
					DailyAccountBalanceInfo dailyAccountBalanceInfo = null;
					try
					{
						dailyAccountBalanceInfo = sett_DailyAccountBalanceDAO.findAllBySubAccountIDAndDate(subAccountLoanInfo.getID(), transInfo.getOfficeID(), transInfo.getCurrencyID(), getNextNDay(backDate, -1));
					}
					catch (SQLException e2)
					{
						e2.printStackTrace();
						throw new IException("Gen_E001");
					}
					//开始倒算 -- 累积利息
					//累计正常利息调整额
					if(transInfo.getIsRemitInterest() == SETTConstant.BooleanValue.ISTRUE) {
						accumulateNormalInterestAdjust = 0.0; 
						newAccumulateNormalInterest = 0.0;
					}
					else {
						accumulateNormalInterestAdjust =  dailyAccountBalanceInfo.getInterest();
						if(transInfo.getAmount()>=0)
						{
							accumulateNormalInterestAdjust = UtilOperation.Arith.sub(accumulateNormalInterestAdjust, transInfo.getRealInterest());
						}
						newAccumulateNormalInterest = accumulateNormalInterestAdjust;
					}
					
					//累计复利利息调整额
					if(transInfo.getIsRemitCompoundInterest() == SETTConstant.BooleanValue.ISTRUE) {
						accumulateCompoundInterestAdjust = 0.0; 
						newAccumulateCompoundInterest = 0.0;
					}
					else{
						accumulateCompoundInterestAdjust =  dailyAccountBalanceInfo.getMcompoundinterest();
						accumulateCompoundInterestAdjust = UtilOperation.Arith.sub(accumulateCompoundInterestAdjust, transInfo.getRealCompoundInterest());
						newAccumulateCompoundInterest = accumulateCompoundInterestAdjust;
					}
					
					//累计罚息利息调整额
					if(transInfo.getIsRemitOverDueInterest() == SETTConstant.BooleanValue.ISTRUE) {
						accumulateOverDueInterestAdjust = 0.0; 
						newAccumulateOverDueInterest = 0.0;
					}
					else{
						accumulateOverDueInterestAdjust =  dailyAccountBalanceInfo.getMforfeitinterest();
						accumulateOverDueInterestAdjust = UtilOperation.Arith.sub(accumulateOverDueInterestAdjust, transInfo.getRealOverDueInterest());
						newAccumulateOverDueInterest = accumulateOverDueInterestAdjust;
					}
					
					//开始倒算 -- 欠息金额
					arrearageInterest = dailyAccountBalanceInfo.getAl_mArrearageInterest();
					//开始倒算 -- 计息余额
					interestBalance = dailyAccountBalanceInfo.getInterestBalance();
					
	
					//计算循环次数
					InterestCalculation interestCalculation = new InterestCalculation(conn);
					int loopDays = (int) interestCalculation.getIntervalDays(backDate, executeDate, 1);
					//				log.info("循环次数是 " + loopDays);
	
					//				log.info("lhj debug info =========单户计息倒填循环开始......");
					for (int i = 0; i < loopDays; i++)
					{ //－－－－循环开始－－－－－－不含ExecuteDate当天－－－－－－－－－－－－－－－－－－－－－－－－
						//定义中间变量-------------------------------------------------------start-------
						//本日正常利息调整额
						double todayNormalInterestAdjust = 0.0;
						//本日复利利息
						double todayCompoundIntersetAdjust = 0.0;
						//本日罚息利息
						double todayOverDueIntersetAdjust = 0.0;
						//当前算息日期
						Timestamp tmpBackDate = getNextNDay(backDate, i);
						//当前算息日期+1
						Timestamp tmpBackDateAddOne = getNextNDay(tmpBackDate, 1);
						try
						{
							dailyAccountBalanceInfo = sett_DailyAccountBalanceDAO.findAllBySubAccountIDAndDate(subAccountLoanInfo.getID(), transInfo.getOfficeID(), transInfo.getCurrencyID(), tmpBackDate);
						}
						catch (SQLException e2)
						{
							e2.printStackTrace();
							throw new IException("Gen_E001");
						}
						long dabi_AccountID = -1;
						if (dailyAccountBalanceInfo != null)
						{
							dabi_AccountID = dailyAccountBalanceInfo.getAccountID();
							//						log.info("lhj debug info ===========dabi_AccountID是： " + dabi_AccountID);
						}

						if (dailyAccountBalanceInfo != null && dabi_AccountID > 0)
						{
							//-------------------------------一般倒填交易----------------------start---------------------------------------
							//						log.info("在账户余额日结表中存在记录，开始进行〔一般倒填交易〕......");
							if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.TRUST || 
									accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CONSIGN || 
									accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.OTHERLOAN || 
									accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.YT)
							{
								//--------一般倒填交易------------贷款账户计息---------start----------------------------------------------
								log.info("〔一般倒填交易〕中开始进行贷款账户计息......");
								todayNormalInterestAdjust = dailyAccountBalanceInfo.getDailyInterest();
								
								//倒算后的累计正常利息
								accumulateNormalInterestAdjust = UtilOperation.Arith.add(accumulateNormalInterestAdjust, todayNormalInterestAdjust);
								newAccumulateNormalInterest = accumulateNormalInterestAdjust;
								dailyAccountBalanceInfo.setInterest(newAccumulateNormalInterest);
								
								//modify by leiyang3
								//2010/11/28
								//用于计算自营贷款到期后的罚息
								if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.TRUST)
								{
									LoanPayNoticeInfo loanPayNoticeInfo = utilOperation.findLoanPayNoticeByID(transInfo.getLoanNoteID());
									if(loanPayNoticeInfo == null){
										throw new IException("没有找到有效的放款单信息");
									}
									
									Timestamp loanStartDate = loanPayNoticeInfo.getStart();
									Timestamp loanEndDate = loanPayNoticeInfo.getEnd();
									
									//-- 复利计算开始 --
									if(loanPayNoticeInfo.getIsRemitCompoundInterest()==Constant.TRUE) //通过合同是否时行复利属性，判断是否进行复利计算
									{
											//利息费用支付及结息还息情况冲减累计欠息
											//正常交易还息是需要判断是否需要冲减累计欠息
											InterestsInfo preinterestsInfo = null;
											InterestsInfo todayinterestsInfo = null;
											DailyAccountBalanceInfo predailyAccountBalanceInfo = null;
											double payInterest = 0;
											double payCompoundInterest = 0;
											double payOverDueInterest = 0;
											double tmpArrearageInterest = 0;
											try
											{
												preinterestsInfo = utilOperation.findPayInterestAmount(subAccountAssemblerInfo.getSubAccountLoanInfo().getID(),tmpBackDate);
												todayinterestsInfo = utilOperation.findAllClearCompoundInterset(subAccountAssemblerInfo.getSubAccountLoanInfo().getID(),tmpBackDate);
												predailyAccountBalanceInfo = sett_DailyAccountBalanceDAO.findAllBySubAccountIDAndDate(subAccountLoanInfo.getID(), transInfo.getOfficeID(), transInfo.getCurrencyID(), getNextNDay(tmpBackDate,-1));
												tmpArrearageInterest = predailyAccountBalanceInfo.getAl_mArrearageInterest();
												accumulateCompoundInterestAdjust = predailyAccountBalanceInfo.getMcompoundinterest();
											}
											catch(Exception e)
											{
												throw new IException(e.getMessage());
											}
											if(transInfo.getAmount()>=0)
											{
												if(i==0)
												{
													payInterest = transInfo.getRealInterest()+todayinterestsInfo.getPayminterest();
													payCompoundInterest = transInfo.getRealCompoundInterest()+todayinterestsInfo.getPaymcompoundinterest();
													payOverDueInterest = transInfo.getRealOverDueInterest()+todayinterestsInfo.getPaymforfeitinterest();
												}
												else
												{
													payInterest = todayinterestsInfo.getPayminterest();
													payCompoundInterest = todayinterestsInfo.getPaymcompoundinterest();
													payOverDueInterest = todayinterestsInfo.getPaymforfeitinterest();
												}
												//结息日判断
												if(utilOperation.isClearInterestDay(dailyAccountBalanceInfo.getSubAccountID(), tmpBackDate))
												{
													double temp1 = UtilOperation.Arith.sub(predailyAccountBalanceInfo.getInterest() ,payInterest);
													double temp2  = UtilOperation.Arith.sub(predailyAccountBalanceInfo.getMcompoundinterest() , payCompoundInterest);
													double temp3  = UtilOperation.Arith.sub(predailyAccountBalanceInfo.getMforfeitinterest() , payOverDueInterest);
													
													tmpArrearageInterest = UtilOperation.Arith.add(UtilOperation.Arith.add(temp1,temp2),temp3);
												}
												else
												{
														if(preinterestsInfo.getMinterest()-preinterestsInfo.getPayminterest()>payInterest)
														{
															tmpArrearageInterest = UtilOperation.Arith.sub(tmpArrearageInterest ,payInterest);
														}
														else if(preinterestsInfo.getMinterest()-preinterestsInfo.getPayminterest()<payInterest&&preinterestsInfo.getMinterest()>preinterestsInfo.getPayminterest())
														{
															tmpArrearageInterest = UtilOperation.Arith.sub(tmpArrearageInterest ,UtilOperation.Arith.sub(preinterestsInfo.getMinterest(),preinterestsInfo.getPayminterest()));
														}
														if(preinterestsInfo.getMcompoundinterest()-preinterestsInfo.getPaymcompoundinterest()>payCompoundInterest)
														{
															tmpArrearageInterest =UtilOperation.Arith.sub( tmpArrearageInterest ,payCompoundInterest);
														}
														else if(preinterestsInfo.getMcompoundinterest()-preinterestsInfo.getPaymcompoundinterest()<payCompoundInterest&&preinterestsInfo.getMcompoundinterest()>preinterestsInfo.getPaymcompoundinterest())
														{
															tmpArrearageInterest = UtilOperation.Arith.sub(tmpArrearageInterest ,UtilOperation.Arith.sub(preinterestsInfo.getMcompoundinterest(),preinterestsInfo.getPaymcompoundinterest()));
														}
														if(preinterestsInfo.getMforfeitinterest()-preinterestsInfo.getPaymforfeitinterest()>payOverDueInterest)
														{
															tmpArrearageInterest =UtilOperation.Arith.sub( tmpArrearageInterest ,payOverDueInterest);
														}
														else if(preinterestsInfo.getMforfeitinterest()-preinterestsInfo.getPaymforfeitinterest()<payOverDueInterest&&preinterestsInfo.getMforfeitinterest()>preinterestsInfo.getPaymforfeitinterest())
														{
															tmpArrearageInterest = UtilOperation.Arith.sub(tmpArrearageInterest ,UtilOperation.Arith.sub(preinterestsInfo.getMforfeitinterest(),preinterestsInfo.getPaymforfeitinterest()));
														}
												}
											}
											else
											{//取消复核反冲
												if(i==0)
												{
													payInterest = -transInfo.getRealInterest()+todayinterestsInfo.getPayminterest();
													payCompoundInterest = -transInfo.getRealCompoundInterest()+todayinterestsInfo.getPaymcompoundinterest();
													payOverDueInterest = -transInfo.getRealOverDueInterest()+todayinterestsInfo.getPaymforfeitinterest();
												}
												else
												{
													payInterest = todayinterestsInfo.getPayminterest();
													payCompoundInterest = todayinterestsInfo.getPaymcompoundinterest();
													payOverDueInterest = todayinterestsInfo.getPaymforfeitinterest();
												}
												//结息日判断
												if(utilOperation.isClearInterestDay(dailyAccountBalanceInfo.getSubAccountID(), tmpBackDate))
												{
													double temp1 = UtilOperation.Arith.sub(predailyAccountBalanceInfo.getInterest() , payInterest);
													double temp2  = UtilOperation.Arith.sub(predailyAccountBalanceInfo.getMcompoundinterest() , payCompoundInterest);
													double temp3  = UtilOperation.Arith.sub(predailyAccountBalanceInfo.getMforfeitinterest() , payOverDueInterest);
													tmpArrearageInterest = UtilOperation.Arith.add(UtilOperation.Arith.add(temp1,temp2),temp3);
												}
												else
												{
													if(preinterestsInfo.getMinterest()-preinterestsInfo.getPayminterest()>payInterest)
													{
														tmpArrearageInterest = UtilOperation.Arith.sub(tmpArrearageInterest ,payInterest);
													}
													else if(preinterestsInfo.getMinterest()-preinterestsInfo.getPayminterest()<payInterest&&preinterestsInfo.getMinterest()>preinterestsInfo.getPayminterest())
													{
														tmpArrearageInterest = UtilOperation.Arith.sub(tmpArrearageInterest ,UtilOperation.Arith.sub(preinterestsInfo.getMinterest(),preinterestsInfo.getPayminterest()));
													}
													if(preinterestsInfo.getMcompoundinterest()-preinterestsInfo.getPaymcompoundinterest()>payCompoundInterest)
													{
														tmpArrearageInterest =UtilOperation.Arith.sub( tmpArrearageInterest ,payCompoundInterest);
													}
													else if(preinterestsInfo.getMcompoundinterest()-preinterestsInfo.getPaymcompoundinterest()<payCompoundInterest&&preinterestsInfo.getMcompoundinterest()>preinterestsInfo.getPaymcompoundinterest())
													{
														tmpArrearageInterest = UtilOperation.Arith.sub(tmpArrearageInterest ,UtilOperation.Arith.sub(preinterestsInfo.getMcompoundinterest(),preinterestsInfo.getPaymcompoundinterest()));
													}
													if(preinterestsInfo.getMforfeitinterest()-preinterestsInfo.getPaymforfeitinterest()>payOverDueInterest)
													{
														tmpArrearageInterest =UtilOperation.Arith.sub( tmpArrearageInterest ,payOverDueInterest);
													}
													else if(preinterestsInfo.getMforfeitinterest()-preinterestsInfo.getPaymforfeitinterest()<payOverDueInterest&&preinterestsInfo.getMforfeitinterest()>preinterestsInfo.getPaymforfeitinterest())
													{
														tmpArrearageInterest = UtilOperation.Arith.sub(tmpArrearageInterest ,UtilOperation.Arith.sub(preinterestsInfo.getMforfeitinterest(),preinterestsInfo.getPaymforfeitinterest()));
													}
												}
											}
											if(tmpArrearageInterest<0.01)
											{
												tmpArrearageInterest = 0;
											}
											
										
										//复利利率
										InterestCalculationParameterInfo paramInfo = new InterestCalculationParameterInfo();
										paramInfo.setLoanNoteID(transInfo.getLoanNoteID());
										paramInfo.setValidDate(tmpBackDate);
										InterestRate compoundRate = interestCalculation.getCompoundInterestRate(paramInfo);
										
										//倒算后的每日复利利息
										todayCompoundIntersetAdjust = interestCalculation.calculateLoanAccountInterest(transInfo.getCurrencyID(), compoundRate.getRate(), compoundRate.getType(), tmpArrearageInterest, tmpBackDate, tmpBackDateAddOne);
										//倒算后的累计复利利息
										accumulateCompoundInterestAdjust = UtilOperation.Arith.add(UtilOperation.Arith.add(newAccumulateCompoundInterest, todayCompoundIntersetAdjust),todayinterestsInfo.getPaymcompoundinterest());
										newAccumulateCompoundInterest = accumulateCompoundInterestAdjust;
										if(newAccumulateCompoundInterest<0.01)
										{
											newAccumulateCompoundInterest = 0;
										}
										dailyAccountBalanceInfo.setAl_mArrearageInterest(tmpArrearageInterest);
										
										//保存每日复利利息
										dailyAccountBalanceInfo.setMcompounddailyinterset(todayCompoundIntersetAdjust);
										//累计复利利息
										dailyAccountBalanceInfo.setMcompoundinterest(newAccumulateCompoundInterest);
									}
									//-- 复利计算结束 --
									
									//-- 罚息计算开始  --
									//是否进行罚息
									if(loanPayNoticeInfo.getIsRemitOverDueInterest()==Constant.TRUE) //通过合同是否时行罚息属性，判断是否进行罚息
									{
										if(!loanEndDate.after(tmpBackDate))
										{
											InterestCalculationParameterInfo paramInfo = new InterestCalculationParameterInfo();
											paramInfo.setLoanNoteID(transInfo.getLoanNoteID());
											paramInfo.setValidDate(tmpBackDate);
											
											InterestRate overDueRate = interestCalculation.getOverDueInterestRate(paramInfo);
											//每日罚息总金额：贷款未还金额
											double overDueAccumulateBalance = interestBalance;
											//倒算后的每日罚息利息
											todayOverDueIntersetAdjust = interestCalculation.calculateLoanAccountInterest(transInfo.getCurrencyID(), overDueRate.getRate(), overDueRate.getType(), overDueAccumulateBalance, tmpBackDate, tmpBackDateAddOne);
											//倒算后的累计罚息利息
											accumulateOverDueInterestAdjust = UtilOperation.Arith.add(newAccumulateOverDueInterest, todayOverDueIntersetAdjust);
											newAccumulateOverDueInterest = accumulateOverDueInterestAdjust;
											
											//保存每日罚息利息
											dailyAccountBalanceInfo.setMforfeitdailyinterset(todayOverDueIntersetAdjust);
											//累计罚息利息
											dailyAccountBalanceInfo.setMforfeitinterest(newAccumulateOverDueInterest);
											//当日罚息利率	     
											dailyAccountBalanceInfo.setMForfeitInterestRate(overDueRate.getRate());
											//重新设置当日新增贷款利率
											dailyAccountBalanceInfo.setDailyInterest(0.0);
											//重新设置累计贷款利息
											accumulateNormalInterestAdjust = UtilOperation.Arith.sub(newAccumulateNormalInterest, todayNormalInterestAdjust);
											newAccumulateNormalInterest = accumulateNormalInterestAdjust;
											dailyAccountBalanceInfo.setInterest(newAccumulateNormalInterest);
										}
									}
									//-- 罚息计算结束 --
								}
	
								//log.info("lhj debug info ==[" + SubAccountID + "]贷款累计正常利息是:" + newAccumulateNormalInterest);
								//log.info("〔一般倒填交易〕中开始进行贷款账户计息结束");
							}
	
							//                         为账户余额日结表中更新一条新记录
	
							try
							{
								//log.info("〔一般倒填交易〕中开始更新账户余额日结表中日期是" + tmpBackDate.toString() + "的数据.....");
								sett_DailyAccountBalanceDAO.update(dailyAccountBalanceInfo);
							}
							catch (Exception e4)
							{
								e4.printStackTrace();
								throw new IException("保存失败，请重试！");
							}
							//log.info("〔一般倒填交易〕中开始更新账户余额日结表中日期是" + tmpBackDate.toString() + "的数据成功！");
							//--------------------------------一般倒填交易----------------------end---------------------------------------
						}
					} //－－－－循环结束－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
					//log.info("循环结束");
						isSuccess = 1;
				} //定期存款和贴现账户不进行计息
				else
				{
					isSuccess = 1;
				}
			}
		}
		return isSuccess;
	}
	

	/**
	 * 接口名称：贷款复利计算日期处理
	 * 功能描述：1:在复利计算日期当天关机时，将账户上的累计利息（mInterest）加入到本日账户的累计欠息（AL_mArrearageInterest）。
	 *           2:本接口只处理贷款账户。
	 *           3:2003-12-29华能确认：对复利也要征收利息，即“复利的复利”，所以，在复利计算日期当天，要对贷款的复利进行一次
	 *             结息，所结的复利和正常利息合计为该账户的欠息，用于计算下一期的复利。
	 * @param conn    　　数据库连接，由方法调用者提供传入，不可为空！
	 * @param officeID　　办事处ID　　
	 * @param currencyID　币种
	 * @param closeDate　 关机日期　
	 * @return long -1 不成功，1：成功
	 * @throws IException
	 */
	public long loanCompoundCalculateDateDeal(Connection conn, long officeID, long currencyID, Timestamp closeDate) throws IException
	{
		long lReturn = -1;
		Collection loanAccountCollection = null;
		try
		{
			//判断当前关机日期是否等于[复利计算日期设置表]中的一个日期			
			Sett_CompoundInterestSettingDAO sett_compoundInterestSettingDAO = new Sett_CompoundInterestSettingDAO(conn);
			long returnValue = sett_compoundInterestSettingDAO.findByCompoundInterestDate(officeID, currencyID, closeDate);
			if (returnValue == 1)
			{

				Sett_SubAccountDAO sett_SubAccountDAO = new Sett_SubAccountDAO(conn);
				InterestCalculation interestCalculation = new InterestCalculation(conn);
				UtilOperation utilOperation = new UtilOperation(conn);
				//				
				//查询所有的贷款账户
				loanAccountCollection = sett_SubAccountDAO.findAllLoanAccount(officeID, currencyID);
				if (loanAccountCollection != null && loanAccountCollection.size() > 0)
				{
					Iterator loanAccountIterator = loanAccountCollection.iterator();
					log.info("%%%%%%%%%%%%%%贷款复利日期处理%%%%%%%%%%%%%%%%%%%%%%%");
					while (loanAccountIterator.hasNext())
					{
						SubAccountAssemblerInfo subAccountAssemblerInfo = (SubAccountAssemblerInfo) loanAccountIterator.next();
						SubAccountLoanInfo subAccountLoanInfo = subAccountAssemblerInfo.getSubAccountLoanInfo();
						long accountID = subAccountLoanInfo.getAccountID();
						long subAccountID = subAccountLoanInfo.getID();
						long loanNoteID = subAccountLoanInfo.getLoanNoteID();
						//余额
						double loanBalance = subAccountLoanInfo.getBalance();
						//复利
						double compoundInterest = 0.0;
						//罚息
						double forfeitInterest = 0.0;
						//逾期欠息
						double overDueArrearageInterest = 0.0;
						overDueArrearageInterest = subAccountLoanInfo.getOverDueArrearageInterest();
						//判断该户是否已逾期!!!!!!!!!!!!!!!!!!!!!!!1
						OverDueInfo overDueInfo = utilOperation.getOverDueInfoByLoanNoteID(loanNoteID);

						//2004-01-16 修改了需求：
						//如果该户已逾期且计算复利Al_nloannoteid in (select npayformid from loan_overdueform where nstatusid=2 and NISCOMPOUNDINTEREST=是)

						if (overDueInfo != null && overDueInfo.getLoanPayID() > 0)
						{ //如果该户已逾期
							long isCompoundInterest = -1;
							isCompoundInterest = overDueInfo.getIsCompoundInterest();
							log.info("逾期账户的是否收取复利：" + isCompoundInterest);
							//钩选了计收复利！！！！！！！！！！！！！！！！！！
							if (isCompoundInterest == Constant.YesOrNo.YES)
							{
								//修改 2004-1-29 -----------------------------------------------------------------

								// 计算贷款账户的当前利息：注意在逾期时已做处理，当前利息从逾期日起就已停算。
								LoanAccountInterestInfo loanAccountInterestInfo = interestCalculation.getLoanAccountInterest(conn, officeID, currencyID, accountID, subAccountID, closeDate, closeDate);
								double loanInterest = loanAccountInterestInfo.getInterest();
								// 计算贷款账户的当前复利：注意在逾期时已做处理，当前复利从逾期日起就已停算
								LoanAccountInterestInfo loanAccountCompoundInterestInfo =
									interestCalculation.getLoanAccountFee(conn, officeID, currencyID, accountID, subAccountID, closeDate, closeDate, SETTConstant.InterestFeeType.COMPOUNDINTEREST);
								compoundInterest = loanAccountCompoundInterestInfo.getInterest();
								//计算贷款账户的当前罚息  
								LoanAccountInterestInfo loanForfeitInterestInfo =
									interestCalculation.getLoanAccountFee(conn, officeID, currencyID, accountID, subAccountID, closeDate, closeDate, SETTConstant.InterestFeeType.FORFEITINTEREST);
								//当前罚息
								forfeitInterest = loanForfeitInterestInfo.getInterest();
								log.info("－－－贷款已经逾期－－计收复利－－－－");
								log.info("贷款当前子账户  ： " + subAccountID);
								log.info("贷款当前余额    ： " + loanBalance);
								log.info("贷款当前利息    ： " + loanInterest);
								log.info("贷款当前复利    ： " + compoundInterest);
								log.info("贷款当前罚息    ： " + forfeitInterest);
								log.info("贷款当前逾期欠息： " + overDueArrearageInterest);
								overDueArrearageInterest = UtilOperation.Arith.add(UtilOperation.Arith.add(loanInterest, compoundInterest), forfeitInterest);
								// 修改 2004-1-29 -----------------------------------------------------------------

								log.info("计收复利后的逾期欠息： " + overDueArrearageInterest);
								log.info("－－－贷款已经逾期－－计收复利－－－－");
								//更新子账户的逾期欠息
								sett_SubAccountDAO.updateArrearageInterest(subAccountID, overDueArrearageInterest, SETTConstant.InterestFeeType.FORFEITINTEREST);

							}
						}
						else
						{ //如果该户没有逾期

							//计算当前贷款账户的当前复利
							LoanAccountInterestInfo loanAccountInterestInfo =
								interestCalculation.getLoanAccountFee(conn, officeID, currencyID, accountID, subAccountID, closeDate, closeDate, SETTConstant.InterestFeeType.COMPOUNDINTEREST);
							//当前复利
							compoundInterest = loanAccountInterestInfo.getInterest();
							log.info("－－－贷款没有逾期－－－－");
							log.info("贷款当前子账户  ： " + subAccountID);
							log.info("贷款当前复利    ： " + compoundInterest);
							log.info("贷款当前利息    ： " + subAccountLoanInfo.getInterest());
							log.info("贷款当前累计欠息： " + compoundInterest + subAccountLoanInfo.getInterest());
							log.info("－－－贷款没有逾期－－－－");
							//更新子账户的累计欠息
							sett_SubAccountDAO.updateArrearageInterest(subAccountID, compoundInterest, SETTConstant.InterestFeeType.COMPOUNDINTEREST);

						}
					}
					log.info("%%%%%%%%%%%%%%贷款复利日期处理%%%%%%%%%%%%%%%%%%%%%%%");
					lReturn = 1;

				}

			}

		}
		catch (IException ie)
		{
			ie.printStackTrace();
			throw ie;
		}
		catch (SQLException sqlE)
		{
			sqlE.printStackTrace();
			throw new IException("Gen_E001");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return lReturn;
	}

	/**
	 * 接口名称：账户余额日结。
	 * 功能描述：关机时调用本接口，读子账户表将子账户表中的 相关信息读入到账户余额日结表中。
	 * @param conn 数据库连接，由方法的调用者提供，never null.
	 * @param officeID 主账户中的办事处ID。
	 * @param currencyID 主账户中的币种ID。
	 * @param closeDate  本次关机时间。
	 * @return long -1：不成功，1：成功
	 * @throws IException
	 */

	public long dailyAccountBalanceSettlement(Connection conn, long officeID, long currencyID, Timestamp closeDate) throws IException
	{

		long lReturn = -1;

		Collection subAccountInfoCollection = null;
		Sett_SubAccountDAO sett_SubAccountDAO = new Sett_SubAccountDAO(conn);

		//在子账户表中查找所有账户状态是未清户的子账户集合
		try
		{
			//log.info("开始在子账户表中查询所有非清户状态的子账户集合......");
			subAccountInfoCollection = sett_SubAccountDAO.findByStatus(officeID, currencyID);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException("Gen_E001");
		}

		if (subAccountInfoCollection != null && subAccountInfoCollection.size() > 0)
		{
			//临时对象保存累计未复核金额不等于零的账户
			ArrayList _alTemp = new ArrayList(16);

			//log.info("子账户表中共查询到" + subAccountInfoCollection.size() + "非清户状态的子账户记录");
			Iterator subAccountAssemblerInfoIterator = subAccountInfoCollection.iterator();
			while (subAccountAssemblerInfoIterator.hasNext())
			{
				SubAccountAssemblerInfo subAccountAssemblerInfo = (SubAccountAssemblerInfo) subAccountAssemblerInfoIterator.next();
				/////////////////////////////////////////////////////////////////////////////////////
				//subAccountAssemblerInfo中含有三种不同的实体对象：
				//subAccountCurrentInfo,subAccountLoanInfo,subAccountFixedInfo
				//因为余额属于公有的属性，故任取一个实体对象：活期对象
				/////////////////////////////////////////////////////////////////////////////////////
				SubAccountCurrentInfo subAccountCurrentInfo = subAccountAssemblerInfo.getSubAccountCurrenctInfo();
				//子账户的nAccountID
				long accountID = subAccountCurrentInfo.getAccountID();
				Sett_AccountDAO sett_AccountDAO = new Sett_AccountDAO(conn);
				AccountInfo accountInfo = null;

				try
				{
					//log.info("根据子账户对应的主账户ID查询主账户......");
					accountInfo = sett_AccountDAO.findByID(accountID);
					if (accountInfo == null)
					{
						throw new IException(true, "主账户表中没有对应记录，交易失败", null);
					}
					//新建一个账户余额日结表对象
					DailyAccountBalanceInfo dailyAccountBalanceInfo = new DailyAccountBalanceInfo();

					dailyAccountBalanceInfo.setDate(closeDate); //日期
					//
					if (accountInfo != null)
					{
						//log.info("开始为新建的账户余额日结表对象赋值.........");
						dailyAccountBalanceInfo.setAccountID(accountID);
						//主账户ID
						dailyAccountBalanceInfo.setSubAccountID(subAccountCurrentInfo.getID());
						//子账户ID
						dailyAccountBalanceInfo.setAccountStatusID(accountInfo.getStatusID());
						//主账户状态ＩＤ
						dailyAccountBalanceInfo.setBalance(subAccountCurrentInfo.getBalance());
						//账户余额
						if (subAccountCurrentInfo.getIsInterest() == SETTConstant.BooleanValue.ISTRUE)
						{ //计息
							dailyAccountBalanceInfo.setInterestBalance(subAccountCurrentInfo.getBalance());
							//计息余额
						}
						if (subAccountCurrentInfo.getIsInterest() == SETTConstant.BooleanValue.ISFALSE)
						{ //不计息
							double interestBalance = 0.0;
							dailyAccountBalanceInfo.setInterestBalance(interestBalance); //计息余额为零！

						}
						dailyAccountBalanceInfo.setSubAccountStatusid(subAccountCurrentInfo.getStatusID()) ;
						dailyAccountBalanceInfo.setFreezeAmount(subAccountCurrentInfo.getCapitalLimitAmount());
						log.print("dailyAccountBalanceInfo.setSubAccountID="+dailyAccountBalanceInfo.getSubAccountID());
						log.print("dailyAccountBalanceInfo.setFreezeAmount="+dailyAccountBalanceInfo.getFreezeAmount());
						//log.info("赋值结束！开始向sett_DailyAccountBalance表中增加一条新的记录......");
						Sett_DailyAccountBalanceDAO sett_DailyAccountBalance = new Sett_DailyAccountBalanceDAO(conn);
						DailyAccountBalanceInfo newDailyAccountBalanceInfo = null;
						newDailyAccountBalanceInfo = sett_DailyAccountBalance.findAllBySubAccountIDAndDate(subAccountCurrentInfo.getID(), officeID, currencyID, closeDate);
						if (newDailyAccountBalanceInfo != null){
							System.out.println("日期：" + closeDate + ", 子账户：" + subAccountCurrentInfo.getID() + "的账户余额表记录已经存在！，交易失败");
							throw new IException(true, "日期：" + closeDate + ", 子账户：" + subAccountCurrentInfo.getID() + "的账户余额表记录已经存在！，交易失败", null);
						}
						if (sett_DailyAccountBalance.add(dailyAccountBalanceInfo) > 0)
						{
							System.out.println("sett_DailyAccountBalance表中增加一条新的记录成功！");
						}
					}
				}
				catch (IException ie)
				{
					ie.printStackTrace();
					throw ie;
				}
				catch (Exception e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
					throw new IException("Gen_E001");
				}

				if (subAccountCurrentInfo.getDailyUncheckAmount() != 0.0)
				{
					_alTemp.add(subAccountCurrentInfo);
				}

			} //循环结束，设置返回值为1

			RecordAccountInfo _record = new RecordAccountInfo();
			_record.recordAccountInfo(_alTemp);

			//log.info("账户余额日结结束！");
			lReturn = 1;
		}

		return lReturn;
	}

	/**
	 * 接口名称：每日算息
	 * 功能说明：1：读账户余额日结表，并在表上计算利息。
	 *           2：不对贷款中的手续费率和担保费率进行每日计息。
	 *           3：将计算后的数据更新到账户余额日结表和子账户表中。
	 * @param conn       数据库连接，由方法调用者传入，不可为空！
	 * @param officeID   办事处ID。
	 * @param currencyID 主账户币种。
	 * @param closeDate  关机日期
	 * @return long -1:不成功   1:成功
	 * @throws IException
	 */

	public long dailyCalculateInterest(Connection conn, long officeID, long currencyID, Timestamp closeDate) throws IException
	{
		long lReturn = -1;
		
		//
		log.info(closeDate.toString() + "的关机算息开始......");
		Collection dailyAccountBalanceInfoVector = null;
		DailyAccountBalanceInfo dailyAccountBalanceInfo = null;
		//
		String closeDateString = closeDate.toString();
		int closeDateDay = new Integer(closeDateString.substring(8, 10)).intValue();
		Timestamp closeDateAddOne = getNextNDay(closeDate, 1);

		Sett_DailyAccountBalanceDAO dailyAccountBalanceDAO = new Sett_DailyAccountBalanceDAO(conn);
		try
		{
			//查询账户余额日结表中日期为closeDate的所有集合
			//System.out.println("开始查询账户余额日结表中日期为当前关机日期的所有记录....");
			dailyAccountBalanceInfoVector = dailyAccountBalanceDAO.findByDate(officeID, currencyID, closeDate);

			if (dailyAccountBalanceInfoVector != null && dailyAccountBalanceInfoVector.size() > 0)
			{
				log.info("查询结束，共查询到" + dailyAccountBalanceInfoVector.size() + "条账户余额日结记录");
				//需要调用计算接口的方法
				InterestCalculation interestCalculation = new InterestCalculation(conn);
				//需要调用账户余额日结表Sett_DailyAccountBalanceDAO的方法，更新账户余额日结表

				//需要调用子账户Sett_SubAccountDAO的方法，更新子账户
				Sett_SubAccountDAO sett_SubAccountDAO = new Sett_SubAccountDAO(conn);
				Iterator vectorIterator = dailyAccountBalanceInfoVector.iterator();
				while (vectorIterator.hasNext())
				{
					dailyAccountBalanceInfo = (DailyAccountBalanceInfo) vectorIterator.next();
					long accountID = dailyAccountBalanceInfo.getAccountID();
					// 主账户ID
					long subAccountID = dailyAccountBalanceInfo.getSubAccountID();
					// 子账户ID
					//根据主账户ID定位一条主账户记录。
					AccountInfo accountInfo = null;
					SubAccountAssemblerInfo subAccountAssemblerInfo = null;
					Sett_AccountDAO sett_AccountDAO = new Sett_AccountDAO(conn);
					UtilOperation utilOperation = new UtilOperation(conn);
					accountInfo = sett_AccountDAO.findByID(accountID);
					if (accountInfo == null)
					{
						throw new IException(true, "主账户表中没有对应记录，交易失败", null);
					}
					if (accountInfo != null)
					{
						//账户类型由主账户中取出
						long accountTypeID = accountInfo.getAccountTypeID();
				        log.debug("---------判断账户类型------------");
				        Sett_AccountTypeDAO sett_AccountTypeDAO = new Sett_AccountTypeDAO();
				        AccountTypeInfo accountTypeInfo = null;
				        try {
							accountTypeInfo = sett_AccountTypeDAO.findByID(accountTypeID);
						} catch (SQLException e) {
							e.printStackTrace();
						}
						if (accountTypeInfo != null) {
							//定期、通知存款账户和贴现账户不进行每日计息                
							if (accountTypeInfo.getAccountGroupID() != SETTConstant.AccountGroupType.FIXED && 
								accountTypeInfo.getAccountGroupID() != SETTConstant.AccountGroupType.NOTIFY && 
								accountTypeInfo.getAccountGroupID() != SETTConstant.AccountGroupType.DISCOUNT)
							{
								//根据子账户ID定位一条子账户记录。
								subAccountAssemblerInfo = sett_SubAccountDAO.findByID(subAccountID);
								//
								SubAccountCurrentInfo subAccountCurrentInfo = null;
								SubAccountLoanInfo subAccountLoanInfo = null;
								SubAccountFixedInfo subAccountFixedInfo = null;
								
								if (subAccountAssemblerInfo == null)
								{
									throw new IException(true, "子账户表中没有对应记录，交易失败", null);
								}
								if (subAccountAssemblerInfo != null)
								{
									//子账户中活期实体对象
									subAccountCurrentInfo = subAccountAssemblerInfo.getSubAccountCurrenctInfo();
									
									//子账户中贷款实体对象
									subAccountLoanInfo = subAccountAssemblerInfo.getSubAccountLoanInfo();
									
									//子账户中定期（保证金）实体对象
									subAccountFixedInfo = subAccountAssemblerInfo.getSubAccountFixedInfo();

									//计算活期存款的本日正常余额、正常利率、正常利息、协定余额、协定利率、协定利息
									if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CURRENT ||
										accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.BAK ||
										accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.RESERVE ||
										accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.LENDING ||
										accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.OTHERDEPOSIT)
									{
										long interestRatePlanID = subAccountCurrentInfo.getInterestRatePlanID();
										//利率计划ID
										//只处理利率计划大于-1的子账户========2003-11-14=======
										if (interestRatePlanID > 0)
										{	
											long lInterestRatePlanTypeID = NameRef.getInterestRatePlanTypeIDByID(interestRatePlanID);
											double balance = subAccountCurrentInfo.getBalance();//子账户当前余额
											double negotiateAmount = subAccountCurrentInfo.getNegotiateAmount();// 协定存款金额
											double negotiateUnit = subAccountCurrentInfo.getNegotiateUnit();//协定存款单位（元）
											//修改为直接取出协定利率nNegotiateRate 2003-11-19 刘惠军
											double negotiateRate = subAccountCurrentInfo.getNegotiateRate();
											double subInterest = subAccountCurrentInfo.getInterest();//子账户当前利息
											double subNegotiateInterest = subAccountCurrentInfo.getNegotiateInterest();//子账户当前协定利息
											//Modify by leiyang 2008-06-30 
											long IsNegotiate = subAccountCurrentInfo.getIsNegotiate(); //是否协定存款
											
											//计算
											CurrentDepositAccountInterestInfo currentDepositAccountInterestInfo = null;
											if(lInterestRatePlanTypeID == SETTConstant.InterestRatePlanType.BALANCE_AVERAGE)
											{
												log.info("---------------利率计划类型为日均余额结息，调用日均余额算息方法 modify by yanliu");
												log.info("---------------利率计划 = "+interestRatePlanID+"--- 利率计划类型 = "+lInterestRatePlanTypeID);
												log.info("---------------子账户ID = "+subAccountCurrentInfo.getID());
												Timestamp tsDateStart = subAccountCurrentInfo.getClearInterestDate();
												Timestamp tsDateEnd = closeDateAddOne;
												double sumBalance = dailyAccountBalanceDAO.sumAccountBalanceBySubAccountIDAndDate(subAccountCurrentInfo.getID(),tsDateStart,tsDateEnd);
												double averageBalance = UtilOperation.Arith.div(sumBalance,interestCalculation.getIntervalDays(tsDateStart,tsDateEnd,1));
												
												//Modify by leiyang 更改校验协定存款的方式
												//增加 IsNegotiate 参数，是否为协定存款
												currentDepositAccountInterestInfo =
													interestCalculation.calculateCurrentDepositAccountInterestForAverageBalance(
														officeID,
														currencyID,
														interestRatePlanID,
														averageBalance,		//日均余额
														balance,			//当日计息金额
														subAccountCurrentInfo.getClearInterestDate(),	//上次结息日期
														closeDateAddOne,								//关机日下一天
														negotiateAmount,
														negotiateUnit,
														negotiateRate,
														IsNegotiate);										
											}
											else
											{
												System.out.println("-----------利率计划类型不是日均余额结息，调用一般算息方法 modify by yanliu");										
												System.out.println("---------------利率计划 = "+interestRatePlanID+"--- 利率计划类型 = "+lInterestRatePlanTypeID);
												//Modify by leiyang 更改校验协定存款的方式
												//增加 IsNegotiate 参数，是否为协定存款
												currentDepositAccountInterestInfo =
													interestCalculation.calculateCurrentDepositAccountInterest(
														officeID,
														currencyID,
														interestRatePlanID,
														balance,
														closeDate,
														closeDateAddOne,
														negotiateAmount,
														negotiateUnit,
														negotiateRate,
														IsNegotiate);
											}
	
											//
											double normalInterestRate = currentDepositAccountInterestInfo.getNormalInterestRate();
											double negotiateInterestRate = currentDepositAccountInterestInfo.getNegotiationInterestRate();
											//本日正常利息
											double todayNormalInterest = currentDepositAccountInterestInfo.getNormalInterest();
											double todayNegotiateInterest = currentDepositAccountInterestInfo.getNegotiationInterest();
											//更新账户余额日结表
											double newInterest = 0.0;
											//经过本日算息后的账户余额日结表中的新的累计正常利息
											double newNegotiateInterest = 0.0;
											//经过本日算息后的账户余额日结表中的新的累计协定利息
											if(lInterestRatePlanTypeID == SETTConstant.InterestRatePlanType.BALANCE_AVERAGE)
											{
												newInterest = todayNormalInterest;
											}
											else
											{
												newInterest = UtilOperation.Arith.add(subInterest, todayNormalInterest);											
											}
											newNegotiateInterest = UtilOperation.Arith.add(subNegotiateInterest, todayNegotiateInterest);
	
											log.debug(	" 子账户ID是：" + subAccountID + "的新的协定利息值是:" + newNegotiateInterest + " == " + "子账户协定利息值：" + subNegotiateInterest + "　＋　今日正常协定利息值： " + todayNegotiateInterest);
											dailyAccountBalanceInfo.setInterest(newInterest);
											dailyAccountBalanceInfo.setAc_mNegotiateInterest(newNegotiateInterest);
											//add yanliu 2005-11-24 
											if(lInterestRatePlanTypeID == SETTConstant.InterestRatePlanType.BALANCE_AVERAGE)
											{
												//日均余额结息法算出来的todayNormalInterest是截止到今日的累计应付利息，所以今日利息为todayNormalInterest-subInterest
												dailyAccountBalanceInfo.setDailyInterest(UtilOperation.Arith.sub(todayNormalInterest,subInterest));
											}
											else
											{
												//非日均余额结息算出的todayNormalInterest是今日的利息
												dailyAccountBalanceInfo.setDailyInterest(todayNormalInterest);
											}
											dailyAccountBalanceInfo.setInterestRate(normalInterestRate);
											dailyAccountBalanceInfo.setAc_mNegotiateRate(negotiateInterestRate);
											dailyAccountBalanceInfo.setAc_mNegotiateBalance(currentDepositAccountInterestInfo.getNegotiationBalance());//协定余额
											dailyAccountBalanceInfo.setInterestBalance(currentDepositAccountInterestInfo.getNormalBalance());//计息余额
											dailyAccountBalanceInfo.setAc_mDailyNegotiateInterest(currentDepositAccountInterestInfo.getNegotiationInterest());//本日协定利息
											//
											dailyAccountBalanceDAO.update(dailyAccountBalanceInfo);

											//更新子账户
											if(lInterestRatePlanTypeID == SETTConstant.InterestRatePlanType.BALANCE_AVERAGE)
											{
												subAccountCurrentInfo.setInterest( todayNormalInterest);
											}
											else
											{
												subAccountCurrentInfo.setInterest(UtilOperation.Arith.add(subInterest, todayNormalInterest));											
											}

											double tmpNegotiateInterest = UtilOperation.Arith.add(subNegotiateInterest, todayNegotiateInterest);
											subAccountCurrentInfo.setNegotiateInterest(tmpNegotiateInterest);
											//
											sett_SubAccountDAO.updateSubAccountCurrent(subAccountCurrentInfo);
										}
									}
									else if(accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.MARGIN)
									{
										/**
										 * 保证金存款利息计算方式与活期账户类似，每天都计息。
										 * 利率的取值：如果关机日在保证金存款的结束日期之前（不含结束日期），则取保证金开立交易中所录入的利率；
										 *           如果关机日在保证金存款的结束日期之后（含结束日期），则取账户设置的利率计划的利率。
										 */
										log.info("开始保证金存款每日算息..........");
										log.info("**************** 账户 ："+accountID);
										log.info("**************** 子账户 ："+subAccountID);
										log.info("**************** 算息日期 ："+closeDate);
										log.info("**************** 保证金存单到期日 ："+subAccountFixedInfo.getEndDate());
										subAccountFixedInfo.setAccountID(accountID);
										subAccountFixedInfo.setID(subAccountID);

										log.info("保证金存款尚未到期，利率取保证金存单中所录入的利率.....");
										
										//从保证金存单中得到利率值:
										double marginInterestRate = 0.00;
										
										double balance = 0.00;              //子账户当前余额
										double negotiateAmount = 0.00;      //协定存款金额
										double negotiateUnit = 0.00;        //协定存款单位元）
										double negotiateRate = 0.00;        //协定利率
										double subInterest = 0.00;          //子账户当前利息
										double subNegotiateInterest = 0.00; //子账户当前协定利息

										//天数标志
										long daysFlag = 1;
										//利率标志
										long interestRateDaysFlag = SETTConstant.InterestRateDaysFlag.DAYS_360;
										//正常利息
										double commonInterest = 0.0;
										
										//正常余额
										double commonBalance = 0.0;
										//正常利率
										double normalInterestRate = 0.0;

										normalInterestRate = 0.0;
										
										//本日正常利息
										double todayNormalInterest = 0.0;
										//更新账户余额日结表
										double newInterest = 0.0;
										//经过本日算息后的账户余额日结表中的新的累计正常利息
										double newNegotiateInterest = 0.0;
										//经过本日算息后的账户余额日结表中的新的累计协定利息
										
										//一个保证金账户可能有多个存单，每个存单分别计息

										//开始算息
										balance = subAccountFixedInfo.getBalance();
										
										subInterest = subAccountFixedInfo.getInterest();
										
										CurrentDepositAccountInterestInfo currentDepositAccountInterestInfo = new CurrentDepositAccountInterestInfo();
										
										//正常利率
										normalInterestRate = subAccountFixedInfo.getRate();
										
										//计算利息 
										commonInterest = interestCalculation.caculateInterest(balance, closeDate, closeDateAddOne, SETTConstant.InterestRateDaysFlag.DAYS_360 , normalInterestRate, SETTConstant.InterestRateTypeFlag.YEAR, SETTConstant.InterestRateDaysFlag.DAYS_360);
										
										//正常余额
										commonBalance = balance;
										
										long intervalDays = interestCalculation.getIntervalDays(closeDate, closeDate, SETTConstant.InterestRateTypeFlag.YEAR);
										
										currentDepositAccountInterestInfo.setIntervalDays(intervalDays); 
										currentDepositAccountInterestInfo.setNegotiationBalance(0.00);
										currentDepositAccountInterestInfo.setNegotiationInterest(0.00);
										currentDepositAccountInterestInfo.setNormalBalance(commonBalance);
										currentDepositAccountInterestInfo.setNormalInterest(commonInterest);
										currentDepositAccountInterestInfo.setNormalInterestRate(normalInterestRate);
										currentDepositAccountInterestInfo.setNegotiationInterestRate(0.00);

										normalInterestRate = currentDepositAccountInterestInfo.getNormalInterestRate();
										
										//本日正常利息
										todayNormalInterest = currentDepositAccountInterestInfo.getNormalInterest();

										newInterest = UtilOperation.Arith.add(subInterest, todayNormalInterest);
										
										dailyAccountBalanceInfo.setInterest(newInterest);
										dailyAccountBalanceInfo.setAc_mNegotiateInterest(newNegotiateInterest);
										
										dailyAccountBalanceInfo.setDailyInterest(todayNormalInterest);
										
										dailyAccountBalanceInfo.setInterestRate(normalInterestRate);
										dailyAccountBalanceInfo.setInterestBalance(currentDepositAccountInterestInfo.getNormalBalance());//计息余额
										
										dailyAccountBalanceDAO.update(dailyAccountBalanceInfo);

										//更新子账户
										subAccountFixedInfo.setInterest(UtilOperation.Arith.add(subInterest, todayNormalInterest));											
										
										sett_SubAccountDAO.updateSubAccountFix(subAccountFixedInfo);
										log.info("结束保证金存款每日算息.....");
									}
									else if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.TRUST || 
										accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CONSIGN || 
										accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.OTHERLOAN || 
										accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.YT)
									{
										long loanNoteID = subAccountLoanInfo.getLoanNoteID(); //放款单号
										double loanBalance = subAccountLoanInfo.getBalance(); //贷款余额
										double loanInterest = subAccountLoanInfo.getInterest(); //贷款当前利息
										double loanCompoundInterest = subAccountLoanInfo.getCompoundInterest(); //贷款当前复利
										double loanOverDueInterest = subAccountLoanInfo.getOverDueInterest(); //贷款当前罚息
										double loanArrearageInterest = subAccountLoanInfo.getArrearageInterest();//贷款当前欠息
										double newInterest = 0.0;
										double newCompoundInterest = 0.0;
										double newOverDueInterest = 0.0;
										InterestRate interestRateInfo = null;

										
										interestRateInfo = interestCalculation.getInterestRateByPayForm(loanNoteID, closeDate);
										double tmpRate = interestRateInfo.getRate();
										long tmpType = interestRateInfo.getType();
										//本日贷款利息
										double todayLoanInterest = 0.0;
										todayLoanInterest = interestCalculation.calculateLoanAccountInterest(currencyID, tmpRate, tmpType, loanBalance, closeDate, closeDateAddOne);
										//本日欠息余额
										double todayArrearageInterest = 0.0;
										todayArrearageInterest = subAccountLoanInfo.getArrearageInterest();
										//更新账户余额日结表

										newInterest = UtilOperation.Arith.add(loanInterest, todayLoanInterest);
										//System.out.println("lhj debug info ==子账户[" + subAccountID + "]==的新的贷款利息是：" + newInterest);
										//System.out.println("lhj debug info ==由原有贷款利息 " + newInterest + "　＋　今日新增贷款利息 " + todayLoanInterest + "而得！");
										//System.out.println("贷款算息结束，开始更新账户余额日结表....");
										//贷款新利息
										dailyAccountBalanceInfo.setInterest(newInterest);
										//今日贷款利息
										dailyAccountBalanceInfo.setDailyInterest(todayLoanInterest);
										//累计欠息金额
										dailyAccountBalanceInfo.setAl_mArrearageInterest(todayArrearageInterest);
										//贷款利率
										dailyAccountBalanceInfo.setInterestRate(tmpRate);
										
										//modify by leiyang3
										//2010/11/28
										//用于计算自营贷款到期后的罚息
										if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.TRUST)
										{
											LoanPayNoticeInfo loanPayNoticeInfo = utilOperation.findLoanPayNoticeByID(loanNoteID);
											
											if(loanPayNoticeInfo == null){
												throw new IException("没有找到有效的放款单信息");
											}
											
											Timestamp loanStartDate = loanPayNoticeInfo.getStart();
											Timestamp loanEndDate = loanPayNoticeInfo.getEnd();
											
											//-- 复利计算开始 --
											if(loanPayNoticeInfo.getIsRemitCompoundInterest()==Constant.TRUE) //通过合同是否时行复利属性，判断是否进行复利计算
											{
												//关机时需要判断当天是否为结息日，如果当天是结息日但是不存在结息交易时，需要重新计算复利基数既认为结息日当天没有归还任何利息
												boolean boolClearDay = utilOperation.isClearInterestDay(dailyAccountBalanceInfo.getSubAccountID(), closeDate);
												if(boolClearDay)
												{
													loanArrearageInterest =UtilOperation.Arith.add( UtilOperation.Arith.add(subAccountLoanInfo.getInterest(),subAccountLoanInfo.getCompoundInterest()),subAccountLoanInfo.getOverDueInterest());
												}
												//复利利率
												InterestCalculationParameterInfo paramInfo = new InterestCalculationParameterInfo();
												paramInfo.setLoanNoteID(loanNoteID);
												paramInfo.setValidDate(closeDate);
												InterestRate compoundRate = interestCalculation.getCompoundInterestRate(paramInfo);
												
												//每日复利利息
												double todayCompoundInterset = interestCalculation.calculateLoanAccountInterest(currencyID, compoundRate.getRate(), compoundRate.getType(), loanArrearageInterest, closeDate, closeDateAddOne);
												//倒算后的累计复利利息
												newCompoundInterest = UtilOperation.Arith.add(loanCompoundInterest, todayCompoundInterset);
												
												//保存每日复利利息
												dailyAccountBalanceInfo.setMcompounddailyinterset(todayCompoundInterset);
												//累计复利利息
												dailyAccountBalanceInfo.setMcompoundinterest(newCompoundInterest);
												//当日欠息
												dailyAccountBalanceInfo.setAl_mArrearageInterest(loanArrearageInterest);
												
											}
											//-- 复利计算结束 --

											//-- 罚息计算开始  --
											//是否进行罚息
											if(loanPayNoticeInfo.getIsRemitOverDueInterest()==Constant.TRUE) //通过合同是否时行罚息属性，判断是否进行罚息
											{
												if(!loanEndDate.after(closeDate))
												{
													InterestCalculationParameterInfo paramInfo = new InterestCalculationParameterInfo();
													paramInfo.setLoanNoteID(loanNoteID);
													paramInfo.setValidDate(closeDate);
													
													InterestRate overDueRate = interestCalculation.getOverDueInterestRate(paramInfo);
													
													//每日罚息总金额：贷款未还金额
													double overDueAccumulateBalance = loanBalance;
													//每日罚息利息
													double todayOverDueInterset = interestCalculation.calculateLoanAccountInterest(currencyID, overDueRate.getRate(), overDueRate.getType(), overDueAccumulateBalance, closeDate, closeDateAddOne);
													//累计罚息利息
													newOverDueInterest = UtilOperation.Arith.add(loanOverDueInterest, todayOverDueInterset);
													
													//保存每日罚息利息
													dailyAccountBalanceInfo.setMforfeitdailyinterset(todayOverDueInterset);
													//累计罚息利息
													dailyAccountBalanceInfo.setMforfeitinterest(newOverDueInterest);
													//当日罚息利率	     
													dailyAccountBalanceInfo.setMForfeitInterestRate(overDueRate.getRate());
													//重新设置当日新增贷款利率
													dailyAccountBalanceInfo.setDailyInterest(0.0);
													//重新设置累计贷款利息
													newInterest = UtilOperation.Arith.sub(newInterest, todayLoanInterest);
													dailyAccountBalanceInfo.setInterest(newInterest);
												}
											}
													
											//-- 罚息计算结束  --
										}
												
										
//										//判断该贷款账户是否已经逾期！！！！！！！！！！
//										OverDueInfo overDueInfo = utilOperation.getOverDueInfoByLoanNoteID(loanNoteID);
//										if (overDueInfo != null && overDueInfo.getLoanPayID() > 0)
//										{
//											/*
//											 *************************************************************
//											 * 如果该户已逾期,停止计算贷款利息、复利，转而计算罚息。
//											 *************************************************************
//											 */
//											//计息余额设置为零
//											dailyAccountBalanceInfo.setInterestBalance(0.0);
//											//本日利息为零
//											dailyAccountBalanceInfo.setDailyInterest(0.0);
//											//累计利息
//											dailyAccountBalanceInfo.setInterest(loanInterest);
//											newInterest = loanInterest;
//											//累计欠息为零，不再计算复利
//											dailyAccountBalanceInfo.setAl_mArrearageInterest(0.0);
//											//子账户逾期欠息
//											double overDueArrearageInterest = subAccountLoanInfo.getOverDueArrearageInterest();
//											double overDueAmount = 0.0;
//											overDueAmount = UtilOperation.Arith.add(loanBalance, overDueArrearageInterest);
//											//
//											log.info("$$$$$$$$$$$$逾期贷款每日算息，贷款子账户：" + subAccountID);
//											log.info("$$$$$$$$$$$$逾期贷款每日算息，贷款本金  ：" + loanBalance);
//											log.info("$$$$$$$$$$$$逾期贷款每日算息，逾期欠息  ：" + overDueArrearageInterest);
//											log.info("$$$$$$$$$$$$逾期贷款每日算息，逾期金额  ：" + overDueAmount);
//											log.info("---------------------------------------------------------");
//											//逾期金额
//											dailyAccountBalanceInfo.setAl_mOverDueAmount(overDueAmount);											
//												System.out.println("中国国电贷款倒算罚息开始----");
//												
//												DailyAccountBalanceInfo accountBalanceInfo = null;
//												try
//												{
//													accountBalanceInfo = sett_DailyAccountBalanceDAO.findAllBySubAccountIDAndDate(subAccountID, officeID, currencyID, getNextNDay(closeDate, -1));
//												}
//												catch (SQLException e)
//												{
//													e.printStackTrace();
//													throw new IException("Gen_E001");
//												}
//												
//												//贷款罚息总和
//												double sumOverdueInterest = 0.0;
//												sumOverdueInterest = accountBalanceInfo.getMforfeitinterest();
//												
//												//每日贷款罚息
//												double overdueInterest = 0.0;
//												overdueInterest = 
//													UtilOperation.Arith.div(
//														UtilOperation.Arith.mul(loanBalance, overDueInfo.getFineInterestRate()), 36000);
//												
//												sumOverdueInterest = UtilOperation.Arith.add(sumOverdueInterest, overdueInterest);
//												
//												dailyAccountBalanceInfo.setMforfeitdailyinterset(overdueInterest);
//												dailyAccountBalanceInfo.setMforfeitinterest(sumOverdueInterest);
//												
//												System.out.println("中国国电贷款倒算罚息结束----");
//												
//												//是否计算复利
//												if(overDueInfo.getIsCompoundInterest() == SETTConstant.BooleanValue.ISTRUE)
//												{
//													System.out.println("中国国电贷款倒算复利开始----");
//													//贷款复利总和
//													double sumCompoundinterest = 0.0;
//													sumCompoundinterest = accountBalanceInfo.getMcompoundinterest();
//													
//													//每日复利
//													double compoundinterest = 0.0;
//													compoundinterest = 
//														UtilOperation.Arith.div(
//															UtilOperation.Arith.mul(loanInterest, overDueInfo.getFineInterestRate()), 36000);
//													
//													sumCompoundinterest = UtilOperation.Arith.add(sumCompoundinterest, compoundinterest);
//												
//													dailyAccountBalanceInfo.setMcompounddailyinterset(compoundinterest);
//													dailyAccountBalanceInfo.setMcompoundinterest(sumCompoundinterest);
//													System.out.println("中国国电贷款倒算复利结束----");
//												}
//											//}
//										}
		
										dailyAccountBalanceDAO.update(dailyAccountBalanceInfo);

										//更新子账户表
										subAccountLoanInfo.setInterest(newInterest);
										subAccountLoanInfo.setCompoundInterest(newCompoundInterest);
										subAccountLoanInfo.setOverDueInterest(newOverDueInterest);
										subAccountLoanInfo.setArrearageInterest(loanArrearageInterest);
										sett_SubAccountDAO.updateSubAccountLoan(subAccountLoanInfo);
									}	
								} //子账户不为空	
							} //定期存款账户和贴现账户不进行每日计息
						}//账户类型不为空	
					} //主账户不为空
				} //循环结束
				System.out.println(closeDate.toString() + "日的算息全部结束！设置返回值为1");
				lReturn = 1;
			} //账户余额日结表集合不为空？
		}
		catch (IException ie)
		{
			ie.printStackTrace();
			throw ie;
		}
		catch (SQLException sqle)
		{
			sqle.printStackTrace();
			throw new IException(true, "无法在主账户或子账户表中找到对应的信息，交易失败", null);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return lReturn;

	}

	/**
	 * Copy from  Huangye‘s InterestCalculation
	 * 2003-11-14
	 * 去当前日期的后面（前面）N天的日期
	 * @param CurrentDay 当前日期
	 * @param n>0 means get furture day, else means get past day
	 * @return nextDay
	*/
	private Timestamp getNextNDay(Timestamp currentDay, int n)
	{
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(currentDay);
		calendar.add(Calendar.DATE, n);
		java.util.Date resDate = calendar.getTime();
		return new Timestamp(resDate.getTime());
	}
	
	/**
	 * 判断保证金存款是否到期
	 * 根据传入的保证金存款账户和子账户，
	 * 判断该子账户对应的保证金开立交易是否已经到期
	 * 
	 * @param SubAccountCurrentInfo
	 * @return boolean
	 * @throws Exception
	 */
	private boolean isMarginDepositAtTerm(SubAccountFixedInfo info) throws Exception
	{
		boolean flag = false;
		
		
		return flag;
	}

	/**
	 * 接口名称:起息日倒填处理
	 * 功能说明:读账户交易明细表,将执行日是今天而起息日早于今天的交易记录对应的账户,从计息日到执行日的前一天,重新计算其每一日的计算余额和利息.
	 *          更新账户余额日结表.
	 * @param conn 数据库连接,由外部调用者传入的连接,不可为空!
	 * @param lOfficeID  办事处
	 * @param lCurrencyID 币种 
	 * @param closeDate 关机日期
	 * @return long -1:不成功  1:成功
	 * @throws IException
	 * 
	 */

	public long interestStartDateBackward(Connection conn, long lOfficeID, long lCurrencyID, Timestamp closeDate) throws IException
	{
		long lReturn = -1;
		//取出需要倒填处理的交易账户以及借贷差额汇总transAccountDetailCollection
		this.conn_Out = conn;
		Collection transAccountDetailCollection = null;
		sett_TransAccountDetailDAO sett_TransAccountDetailDAO = new sett_TransAccountDetailDAO(conn);
		try
		{
			transAccountDetailCollection = sett_TransAccountDetailDAO.findByCloseDate(lOfficeID, lCurrencyID, closeDate);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		if (transAccountDetailCollection != null && transAccountDetailCollection.size() > 0)
		{
			int sum = transAccountDetailCollection.size();
			//log.print("共查询出需要倒填的交易账户数量是: " + transAccountDetailCollection.size());
			Iterator transAccountDetailIterator = transAccountDetailCollection.iterator();
			TransAccountDetailInfo transAccountDetailInfo = null;
			int i = 0; //调试用,无意义.hjliu 2003-11-20
			while (transAccountDetailIterator.hasNext())
			{
				transAccountDetailInfo = (TransAccountDetailInfo) transAccountDetailIterator.next();

				long tmpTransAccountID = -1; //交易账户
				long tmpSubAccountID = -1; //交易子账户          
				Timestamp interestStartDate = null; //起息日

				double backAmount = 0.0;
				//倒填金额,在数据库的算法是:sum(decode(nTransDirection,1,-mAmount,2,mAmount))

				tmpTransAccountID = transAccountDetailInfo.getTransAccountID();
				tmpSubAccountID = transAccountDetailInfo.getTransSubAccountID();
				interestStartDate = transAccountDetailInfo.getDtInterestStart();
				backAmount = transAccountDetailInfo.getAmount();
				long isCloseSystem = SETTConstant.BooleanValue.ISTRUE;
				//				log.info("lhj debug info =======tmpTransAccountID是：" + tmpTransAccountID);
				//				log.info("lhj debug info =======tmpSubAccountID是：" + tmpSubAccountID);
				//				log.info("lhj debug info =======interestStartDate是：" + interestStartDate);
				//				log.info("lhj debug info =======backAmount：" + backAmount);
				//调用单户计息倒填处理				
				if (accountInterestCalculationBackward(tmpTransAccountID, tmpSubAccountID, interestStartDate, backAmount, lOfficeID, lCurrencyID, closeDate, isCloseSystem) == 1)
				{
					i++;

				}
			}
			//			log.print("循环结束----------------------------------------");
			//			log.print("总计倒填处理成功的账户数量是: " + i);
			lReturn = 1;
		}
		return lReturn;
	}

	/**
	 * 接口名称:存款银行利率倒填处理
	 * 功能说明:当银行利率改变，并且生效日早于当前系统日期时候调用。适用于所有的存款。
	 * @param officeID
	 * @param currencyID
	 * @param closeDate
	 * @return
	 */
	public long depositBankInterestBackward(Connection conn, long officeID, long currencyID, Timestamp closeDate) throws IException
	{
		long lReturn = -1;
		Collection depositBankInterestCollection = null;

		Sett_SubAccountDAO sett_SubAccountDAO = new Sett_SubAccountDAO(conn);
		try
		{
			depositBankInterestCollection = sett_SubAccountDAO.findAllDepositBankInterestAdjust(officeID, currencyID, closeDate);

			if (depositBankInterestCollection != null && depositBankInterestCollection.size() > 0)
			{
				Iterator depositBankInterestIterator = depositBankInterestCollection.iterator();
				while (depositBankInterestIterator.hasNext())
				{
					BankInterestAdjustInfo depositBankInterestAdjustInfo = (BankInterestAdjustInfo) depositBankInterestIterator.next();
					long accountID = depositBankInterestAdjustInfo.getAccountID();
					long subAccountID = depositBankInterestAdjustInfo.getSubAccountID();
					Timestamp backDate = depositBankInterestAdjustInfo.getBackDate();
					//					log.info("存款银行利率倒填==当前子账户ID：" + subAccountID);
					//					log.info("存款银行利率倒填==当前主账户ID：" + accountID);
					//					log.info("存款银行利率倒填==倒填日期：" + backDate);

					//调用单户计息倒填
					long interestReturnValue = this.accountInterestCalculationBackward(accountID, subAccountID, backDate, 0, officeID, currencyID, closeDate, SETTConstant.BooleanValue.ISTRUE);
					if (interestReturnValue != 1)
					{
						log.info("存款利率调整引起的单户计息倒填不成功，子账户ID：" + subAccountID);
						throw new IException(true, "存款利率调整－单户计息倒填不成功，交易失败", null);
					}

				}
				lReturn = 1;

			}

		}
		catch (IException ie)
		{
			ie.printStackTrace();
			throw ie;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");

		}

		return lReturn;
	}
    
	/**
		 * 接口名称:贷款银行利率倒填处理
		 * 功能说明:当贷款银行利率调整时候，并且生效日早于当前系统日期时候调用。适用于所有的贷款。
		 * @param officeID
		 * @param currencyID
		 * @param closeDate
		 * @return
		 */
		public long loanBankInterestBackward(Connection conn, long officeID, long currencyID, Timestamp closeDate) throws IException
		{
			long lReturn = -1;
			Collection loanBankInterestCollection = null;

			Sett_SubAccountDAO sett_SubAccountDAO = new Sett_SubAccountDAO(conn);
			try
			{
				loanBankInterestCollection = sett_SubAccountDAO.findAllLoanBankInterestAdjust(officeID, currencyID, closeDate);

				if (loanBankInterestCollection != null && loanBankInterestCollection.size() > 0)
				{
					Iterator loanBankInterestIterator = loanBankInterestCollection.iterator();
					while (loanBankInterestIterator.hasNext())
					{
						BankInterestAdjustInfo loanBankInterestAdjustInfo = (BankInterestAdjustInfo) loanBankInterestIterator.next();
						long accountID = loanBankInterestAdjustInfo.getAccountID();
						long subAccountID = loanBankInterestAdjustInfo.getSubAccountID();
						Timestamp backDate = loanBankInterestAdjustInfo.getBackDate();
						long loanRateAdjustID = loanBankInterestAdjustInfo.getLoanRateAdjustPayDetailID();
						
						log.debug("贷款银行利率倒填==当前子账户ID：" + subAccountID);
						log.debug("贷款银行利率倒填==当前主账户ID：" + accountID);
						log.debug("贷款银行利率倒填==贷款利率调整详细信息表ID：" + loanRateAdjustID);
						log.debug("贷款银行利率倒填==倒填日期：" + backDate);

						//调用单户计息倒填
						long interestReturnValue = this.accountInterestCalculationBackward(accountID, subAccountID, backDate, 0, officeID, currencyID, closeDate, SETTConstant.BooleanValue.ISTRUE);
						if (interestReturnValue != 1)
						{
							log.info("贷款利率调整引起的单户计息倒填不成功，子账户ID：" + subAccountID);
							throw new IException(true, "贷款利率调整－单户计息倒填不成功，交易失败", null);
						}else{
						   //将对象loanBankInterestAdjustInfo对应的LOAN_RATEADJUSTPAYDETAIL的NISCOUNTINTEREST修改为0
						   //以免进行重复倒算
						   long lUpdateReturn = sett_SubAccountDAO.updateLoanRateAdjustPayDetail(loanRateAdjustID);
						   log.debug("贷款利率调整详细信息更改标志lUpdateReturn = "+lUpdateReturn);
						}

					}
					lReturn = 1;

				}

			}
			catch (IException ie)
			{
				ie.printStackTrace();
				throw ie;
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new IException("Gen_E001");

			}

			return lReturn;
		}
		
		/**
		 * 接口名称:贷款Libor利率倒填处理
		 * 功能说明:当贷款Libor利率调整时候，并且生效日早于当前系统日期时候调用。适用于所有的贷款。
		 * @param officeID
		 * @param currencyID
		 * @param closeDate
		 * @return
		 */
		public long loanLiborInterestBackward(Connection conn, long officeID, long currencyID, Timestamp closeDate) throws IException
		{
			long lReturn = -1;
			Collection loanLiborInterestCollection = null;

			Sett_SubAccountDAO sett_SubAccountDAO = new Sett_SubAccountDAO(conn);
			try
			{
				loanLiborInterestCollection = sett_SubAccountDAO.findAllLoanLiborInterestAdjust(officeID, currencyID, closeDate);

				if (loanLiborInterestCollection != null && loanLiborInterestCollection.size() > 0)
				{
					Iterator loanLiborInterestIterator = loanLiborInterestCollection.iterator();
					while (loanLiborInterestIterator.hasNext())
					{
						LiborInterestAdjustInfo loanLiborInterestAdjustInfo = (LiborInterestAdjustInfo) loanLiborInterestIterator.next();
						long accountID = loanLiborInterestAdjustInfo.getAccountID();
						long subAccountID = loanLiborInterestAdjustInfo.getSubAccountID();
						Timestamp backDate = loanLiborInterestAdjustInfo.getBackDate();
						long loanRateAdjustID = loanLiborInterestAdjustInfo.getLoanLiborInformID();
						
						log.debug("贷款LIBOR利率倒填==当前子账户ID：" + subAccountID);
						log.debug("贷款LIBOR利率倒填==当前主账户ID：" + accountID);
						log.debug("贷款LIBOR利率倒填==LIBOR利率调整详细信息表ID：" + loanRateAdjustID);
						log.debug("贷款LIBOR利率倒填==倒填日期：" + backDate);

						//调用单户计息倒填
						long interestReturnValue = this.accountInterestCalculationBackward(accountID, subAccountID, backDate, 0, officeID, currencyID, closeDate, SETTConstant.BooleanValue.ISTRUE);
						if (interestReturnValue != 1)
						{
							log.info("贷款LIBOR利率调整引起的单户计息倒填不成功，子账户ID：" + subAccountID);
							throw new IException(true, "贷款LIBOR利率调整－单户计息倒填不成功，交易失败", null);
						}else{
						   //将对象LiborInterestAdjustInfo对应的LOAN_LIBORINFORM的ISCOUNTINTEREST修改为0
						   //以免进行重复倒算
						   long lUpdateReturn = sett_SubAccountDAO.updateLoanLiborInform(loanRateAdjustID);
						   log.debug("贷款LIBOR利率调整详细信息更改标志lUpdateReturn = "+lUpdateReturn);
						}

					}
					lReturn = 1;

				}

			}
			catch (IException ie)
			{
				ie.printStackTrace();
				throw ie;
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new IException("Gen_E001");

			}

			return lReturn;
		}
	/**
	 * 功能：贷款放款通知单逾期时，将未结清的本金、正常利息、复利等，都作为计算罚息的基数，按罚息利率收取罚息。
	 * @param conn    数据库连接，外部调用者传入。
	 * @param lOfficeID 办事处
	 * @param lCurrencyID 币种
	 * @param closeDate  当前关机日期
	 * @return lReturn －1：不成功，1:成功
	 * @throws IException:
	 */
	public long loanAccountOverDue(Connection conn, long lOfficeID, long lCurrencyID, Timestamp closeDate) throws IException
	{
		long lReturn = -1;
		Collection overDueLoanCollection = null;

		UtilOperation utilOperation = new UtilOperation(conn);
		Sett_SubAccountDAO sett_SubAccountDAO = new Sett_SubAccountDAO(conn);
		InterestCalculation interestCalculation = new InterestCalculation(conn);
		Sett_DailyAccountBalanceDAO sett_DailyAccountBalanceDAO = new Sett_DailyAccountBalanceDAO(conn);
		try
		{
			overDueLoanCollection = utilOperation.findAllOverDueLoanAccount(lOfficeID, lCurrencyID, closeDate);

			//
			if (overDueLoanCollection != null && overDueLoanCollection.size() > 0)
			{
				Iterator overDueLoanIterator = overDueLoanCollection.iterator();
				while (overDueLoanIterator.hasNext())
				{
					OverDueInfo overDueLoan = (OverDueInfo) overDueLoanIterator.next();

					Timestamp fineDate = overDueLoan.getFineDate();
					long loanNoteID = overDueLoan.getLoanPayID();
					SubAccountAssemblerInfo subAccount = sett_SubAccountDAO.findByLoanNoteID(0, loanNoteID);
					SubAccountLoanInfo subLoanAccount = subAccount.getSubAccountLoanInfo();
					//用于存放是否协定存款标志，＝＝＝＝》暂时用于是否做了逾期处理！
					SubAccountCurrentInfo subCurrentAccount = subAccount.getSubAccountCurrenctInfo();
					//主账户ID,和子账户ID
					long accountID = subLoanAccount.getAccountID();
					long subAccountID = subLoanAccount.getID();
					//余额
					double loanBalance = 0.0;
					loanBalance = subLoanAccount.getBalance();
					//计算贷款账户的逾期未还利息
					LoanAccountInterestInfo loanInterestInfo = interestCalculation.getLoanAccountInterest(conn, lOfficeID, lCurrencyID, accountID, subAccountID, fineDate, closeDate);
					double loanInterest = loanInterestInfo.getInterest();
					//子账户的正常利息修改为逾期日的正常利息，不再改变！2004-02-23 刘惠军
					subLoanAccount.setInterest(loanInterest);

					//计算贷款账户的逾期未还复利
					LoanAccountInterestInfo LoanCompoundInterestInfo =
						interestCalculation.getLoanAccountFee(conn, lOfficeID, lCurrencyID, accountID, subAccountID, fineDate, closeDate, SETTConstant.InterestFeeType.COMPOUNDINTEREST);
					double loanCompoundInterest = LoanCompoundInterestInfo.getInterest();

					//停止计算复利
					subLoanAccount.setArrearageInterest(0);
					//初始化罚息结息日，作了这个处理后，计算罚息时就不再需要区分是否第一次罚。
					subLoanAccount.setClearOverDueDate(fineDate);
					//更新账户逾期欠息
					double overDueArrearageInterest = 0.0;
					overDueArrearageInterest = UtilOperation.Arith.add(UtilOperation.Arith.round(loanInterest, 2), UtilOperation.Arith.round(loanCompoundInterest, 2));
					subLoanAccount.setOverDueArrearageInterest(overDueArrearageInterest);

					log.info("$$$$$$$$$$$$逾期贷款处理，主账户ID：" + accountID);
					log.info("$$$$$$$$$$$$逾期贷款处理，子账户ID：" + subAccountID);
					log.info("$$$$$$$$$$$$逾期贷款处理，贷款利息：" + loanInterest);
					log.info("$$$$$$$$$$$$逾期贷款处理，贷款复利：" + loanCompoundInterest);
					log.info("$$$$$$$$$$$$逾期贷款处理，逾期欠息：" + overDueArrearageInterest);
					log.info("$$$$$$$$$$$$逾期贷款处理，贷款本金：" + loanBalance);

					//更新子账户
					/*
					 * 将子账户中的是否协定存款AC_nIsNegotiate用于判断是否已经做了逾期处理，
					 * 如果已经做了逾期，则将该字段设置为：9
					 * 防止重复逾期处理！
					 * 2004-02-04
					 */
					subCurrentAccount.setIsNegotiate(LOAN_ACCOUNT_HASOVERDUED);
					sett_SubAccountDAO.updateSubAccountCurrent(subCurrentAccount);
					//更新子账户
					sett_SubAccountDAO.updateSubAccountLoan(subLoanAccount);
					double overDueAmount = 0.0;

					//更新该子账户的账户余额表记录
					if (fineDate.before(closeDate))
						overDueAmount = UtilOperation.Arith.add(overDueArrearageInterest, loanBalance);
					log.info("$$$$$$$$$$$$逾期贷款处理，逾期金额：" + overDueAmount);
					sett_DailyAccountBalanceDAO.updateOverDueArrearageAmount(subAccountID, overDueAmount, loanInterest, fineDate);

				}
				lReturn = 1;

			}

		}
		catch (IException ie)
		{
			ie.printStackTrace();
			throw ie;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}

		return lReturn;
	}
	
	/**
	 * 接口名称:复利罚息倒填处理
	 * 功能说明:中国国电贷款逾期处理接口 Boxu 2008-10-06
	 * @param conn 数据库连接,由外部调用者传入的连接,不可为空
	 * @param lOfficeID  办事处
	 * @param lCurrencyID 币种 
	 * @param closeDate 关机日期
	 * @return long -1:不成功  1:成功
	 * @throws IException
	 */
	public long interestGuoDianStartDateBackward(Connection conn, long lOfficeID, long lCurrencyID, Timestamp closeDate) throws IException
	{
		long lReturn = -1;
		//取出需要倒填处理的交易账户以及借贷差额汇总transAccountDetailCollection
		this.conn_Out = conn;
		Collection transAccountDetailCollection = null;
		sett_TransAccountDetailDAO sett_TransAccountDetailDAO = new sett_TransAccountDetailDAO(conn);
		
		try
		{
			transAccountDetailCollection = sett_TransAccountDetailDAO.findGuoDianByCloseDate(lOfficeID, lCurrencyID, closeDate);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		
		if (transAccountDetailCollection != null && transAccountDetailCollection.size() > 0)
		{
			Iterator transAccountDetailIterator = transAccountDetailCollection.iterator();
			TransAccountDetailInfo transAccountDetailInfo = null;
			
			int i = 0;  //调试用,无意义.hjliu 2003-11-20
			
			while (transAccountDetailIterator.hasNext())
			{
				transAccountDetailInfo = (TransAccountDetailInfo) transAccountDetailIterator.next();

				long tmpTransAccountID = -1;  			//交易账户
				long tmpSubAccountID = -1;  			//交易子账户          
				Timestamp interestStartDate = null;  	//起息日
				double backAmount = 0.0;  				//倒填金额,从每日算息余额表中获得账户余额
				long iscompound = -1;					//是否计算复利
				double dRate = 0.0;						//罚息利率
				
				tmpTransAccountID = transAccountDetailInfo.getTransAccountID();
				tmpSubAccountID = transAccountDetailInfo.getTransSubAccountID();
				interestStartDate = transAccountDetailInfo.getDtInterestStart();
				iscompound = transAccountDetailInfo.getTransDirection();
				dRate = transAccountDetailInfo.getRate();
				//backAmount = transAccountDetailInfo.getAmount();
				long isCloseSystem = SETTConstant.BooleanValue.ISTRUE;
				
				//调用单户计息倒填处理
				if (accountGuoDianInterestCalculationBackward(tmpTransAccountID, tmpSubAccountID, interestStartDate, backAmount, lOfficeID, lCurrencyID, closeDate, isCloseSystem, iscompound, dRate) == 1)
				{
					i++;
				}
			}
			
			lReturn = 1;
		}
		
		return lReturn;
	}
	
	/**
	* 接口名称:复利罚息倒填处理
	* 功能说明:中国国电贷款逾期处理接口 Boxu 2008-10-06
	* @param AccountID    主账户ID
	* @param SubAccountID 子账户ID
	* @param BackDate     倒填交易起息日
	* @param BackAmount   倒填交易金额
	* @param ExecuteDate  执行日/关机日期
	* @param isCloseSystem 是否关机调用
	* @return long -1:不成功，1：成功
	* @throws IException
	*/
	public long accountGuoDianInterestCalculationBackward(
		long AccountID,
		long SubAccountID,
		Timestamp BackDate,
		double BackAmount,
		long lOfficeID,
		long lCurrencyID,
		Timestamp ExecuteDate,
		long isCloseSystem,
		long iscompound,
		double dRate)
		throws IException
	{
		long isSuccess = -1;  //成功标志
		Connection conn = this.conn_Out;
		double executeDateLoanInterest = 0.0;  //贷款子账户的当前利息（即在关机算息后的贷款利息）
		
		//根据AccountID在sett_account表中定位主账户记录
		Sett_AccountDAO sett_AccountDAO = new Sett_AccountDAO(conn);
		AccountInfo accountInfo = null;
		
		try
		{
			accountInfo = sett_AccountDAO.findByID(AccountID);
			if (accountInfo == null)
			{
				throw new IException(true, "主账户表中没有对应记录，交易失败", null);
			}
		}
		catch (IException ie)
		{
			throw ie;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IException(true, "无法在主账户表中找到对应的信息，交易失败", null);
		}
		log.info("单户计息倒填中取主账户成功！");
		
		//根据SubAccountID在sett_SubAccount表中定位子账户记录
		Sett_SubAccountDAO sett_SubAccountDAO = new Sett_SubAccountDAO(conn);
		SubAccountAssemblerInfo subAccountAssemblerInfo = null;
		
		try
		{
			log.info("单户计息倒填中取子账户....");
			subAccountAssemblerInfo = sett_SubAccountDAO.findByID(SubAccountID);
		}
		catch (SQLException e1)
		{
			e1.printStackTrace();
			throw new IException(true, "无法在字子账户表中找到对应的信息，交易失败", null);
		}
		
		log.info("单户计息倒填中取子账户成功！");
		//子账户中的贷款信息
		SubAccountLoanInfo subAccountLoanInfo = null;
		SubAccountCurrentInfo subAccountCurrentInfo = null;
		
		if (subAccountAssemblerInfo != null)
		{
			//贷款子账户------------------------------------------------------------------
			subAccountLoanInfo = subAccountAssemblerInfo.getSubAccountLoanInfo();
			//贷款子账户的当前利息（即在关机算息后的贷款利息）
			executeDateLoanInterest = subAccountLoanInfo.getInterest();
			//贷款子账户------------------------------------------------------------------
			
			//活期子账户------------------------------------------------------------------	
			subAccountCurrentInfo = subAccountAssemblerInfo.getSubAccountCurrenctInfo();
			//活期子账户------------------------------------------------------------------
		}
		else
		{
			throw new IException(true, "无法在子账户表中找到对应的信息，交易失败", null);
		}

		if (accountInfo != null)
		{
			long accountTypeID = accountInfo.getAccountTypeID();
	        Sett_AccountTypeDAO sett_AccountTypeDAO = new Sett_AccountTypeDAO();
	        AccountTypeInfo accountTypeInfo = null;
	        
	        try {
				accountTypeInfo = sett_AccountTypeDAO.findByID(accountTypeID);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			if (accountTypeInfo != null) {
				//本接口对定期存款和贴现账户不进行计息
				if (accountTypeInfo.getAccountGroupID() != SETTConstant.AccountGroupType.FIXED && 
					accountTypeInfo.getAccountGroupID() != SETTConstant.AccountGroupType.NOTIFY && 
					accountTypeInfo.getAccountGroupID() != SETTConstant.AccountGroupType.DISCOUNT)
				{
					//计算循环次数
					InterestCalculation interestCalculation = new InterestCalculation(conn);
					int loopDays = (int) interestCalculation.getIntervalDays(BackDate, ExecuteDate, 1);
					//log.info("循环次数是 " + loopDays);
					
					Sett_DailyAccountBalanceDAO sett_DailyAccountBalanceDAO = new Sett_DailyAccountBalanceDAO(conn);
					for (int i = 0; i < loopDays; i++)
					{
						//计息余额
						double newInterestBalance = 0.0;
						//当前算息日期
						Timestamp tmpBackDate = getNextNDay(BackDate, i);
						//当前算息日期+1
						//Timestamp tmpBackDateAddOne = getNextNDay(tmpBackDate, 1);
	
						//根据主账户AccountID,子账户SubAccountID,算息日期tmpBackDate在账户余额日结表-中查询相关记录。
						DailyAccountBalanceInfo dailyAccountBalanceInfo = null;
						
						try
						{
							dailyAccountBalanceInfo = sett_DailyAccountBalanceDAO.findAllBySubAccountIDAndDate(SubAccountID, lOfficeID, lCurrencyID, tmpBackDate);
						}
						catch (SQLException e2)
						{
							e2.printStackTrace();
							throw new IException("Gen_E001");
						}
						
						long dabi_AccountID = -1;
						if (dailyAccountBalanceInfo != null)
						{
							dabi_AccountID = dailyAccountBalanceInfo.getAccountID();
						}
						
						if (dailyAccountBalanceInfo != null && dabi_AccountID > 0)
						{
							if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.TRUST || 
								accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CONSIGN)
							{
								System.out.println("中国国电贷款倒算罚息开始----");
								//计息余额
								if(dailyAccountBalanceInfo.getInterestBalance() > 0) {
									newInterestBalance = dailyAccountBalanceInfo.getInterestBalance();
								} else {
									//系统做了逾期处理,不在计算正常利息					
									newInterestBalance = dailyAccountBalanceInfo.getBalance();
									
								}
								System.out.println("中国国电贷款倒算罚息开始----00000000000…………………………"+ getNextNDay(tmpBackDate, -1));
								//贷款罚息总和
								DailyAccountBalanceInfo accountBalanceInfo = new DailyAccountBalanceInfo();
								try
								{
									accountBalanceInfo = sett_DailyAccountBalanceDAO.findAllBySubAccountIDAndDate(SubAccountID, lOfficeID, lCurrencyID, getNextNDay(tmpBackDate, -1));
									System.out.println("中国国电贷款倒算罚息开始----00000000000…………………………"+ accountBalanceInfo);
								}
								catch (SQLException e)
								{
									e.printStackTrace();
									throw new IException("Gen_E001");
								}
								if(accountBalanceInfo!=null){
									double sumOverdueInterest = 0.0;
									sumOverdueInterest = accountBalanceInfo.getMforfeitinterest();
									
									//每日贷款罚息
									double overdueInterest = 0.0;
									overdueInterest = 
										UtilOperation.Arith.div(
											UtilOperation.Arith.mul(newInterestBalance, dRate), 36000);
									
									sumOverdueInterest = UtilOperation.Arith.add(sumOverdueInterest, overdueInterest);
									
									dailyAccountBalanceInfo.setMforfeitdailyinterset(overdueInterest);
									dailyAccountBalanceInfo.setMforfeitinterest(sumOverdueInterest);
									
									System.out.println("中国国电贷款倒算罚息结束----");	
									
									//是否计算复利
									if(iscompound == SETTConstant.BooleanValue.ISTRUE)
									{
										System.out.println("中国国电贷款倒算复利开始----");
										//贷款复利总和
										double sumCompoundinterest = 0.0;
										sumCompoundinterest = accountBalanceInfo.getMcompoundinterest();
										
										//每日复利
										double compoundinterest = 0.0;
										compoundinterest = 
											UtilOperation.Arith.div(
												UtilOperation.Arith.mul(accountBalanceInfo.getInterest(), dRate), 36000);
										sumCompoundinterest = UtilOperation.Arith.add(sumCompoundinterest, compoundinterest);
									
										dailyAccountBalanceInfo.setMcompounddailyinterset(compoundinterest);
										dailyAccountBalanceInfo.setMcompoundinterest(sumCompoundinterest);
										System.out.println("中国国电贷款倒算复利结束----");	
									}
								}
								
								
							}
							//为账户余额日结表中更新一条新记录
							try
							{
								sett_DailyAccountBalanceDAO.update(dailyAccountBalanceInfo);
							}
							catch (Exception e4)
							{
								e4.printStackTrace();
								throw new IException("保存失败，请重试！");
							}
						}
					}

					//更新子账户表
					try
					{
						System.out.println("开始更新子账户表........");
						if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.TRUST || 
							accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CONSIGN)
						{
							//log.info("开始更新子账户表中贷款信息........");
							//更新子账户
							/*
							 * 将子账户中的是否协定存款AC_nIsNegotiate用于判断是否已经做了逾期处理，
							 * 如果已经做了逾期，则将该字段设置为：9
							 * 防止重复逾期处理
							 */
							subAccountCurrentInfo.setIsNegotiate(LOAN_ACCOUNT_HASOVERDUED);
							sett_SubAccountDAO.updateSubAccountCurrent(subAccountCurrentInfo);
							
							subAccountLoanInfo.setClearOverDueDate(BackDate);		//更新罚息日期
							
							if(iscompound == SETTConstant.BooleanValue.ISTRUE)
							{
								subAccountLoanInfo.setClearCompoundDate(BackDate);	//更新复利日期
							}

							//更新子账户中的贷款
							sett_SubAccountDAO.updateSubAccountLoan(subAccountLoanInfo);
						}
						System.out.println("结束更新子账户表........");
						//倒填成功
						isSuccess = 1;
					}
					catch (Exception e)
					{
						e.printStackTrace();
						throw new IException("Gen_E001");
					}
				}
			}
		}
		
		return isSuccess;
	}
	
}