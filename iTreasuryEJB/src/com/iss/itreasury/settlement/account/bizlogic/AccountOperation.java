/*
 * Created on 2003-9-6
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.settlement.account.bizlogic;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.util.Collection;

import javax.ejb.CreateException;

import com.iss.itreasury.settlement.query.paraminfo.QueryAccountWhereInfo;
import com.iss.itreasury.settlement.setting.dao.Sett_BranchDAO;
import com.iss.itreasury.settlement.account.dataentity.AccountInfo;
import com.iss.itreasury.settlement.account.dataentity.ExternalAccountInfo;
import com.iss.itreasury.settlement.account.dataentity.SubAccountAssemblerInfo;
import com.iss.itreasury.settlement.account.dataentity.SubAccountFixedInfo;
import com.iss.itreasury.settlement.account.dataentity.SubAccountLoanInfo;
import com.iss.itreasury.settlement.account.dataentity.TransAccountDetailInfo;
import com.iss.itreasury.settlement.generalledger.dao.Sett_GeneralLedgerDAO;
import com.iss.itreasury.settlement.setting.dataentity.BranchInfo;
import com.iss.itreasury.settlement.transfinancialmargin.dataentity.TransFinancialMarginInfo;
import com.iss.itreasury.settlement.util.NameRef;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.EJBHomeFactory;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IRollbackException;
import com.iss.itreasury.util.Log4j;
import com.iss.itreasury.util.Env;
/**
 * @author yiwang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class AccountOperation
{
	final public static int SUBJECT_TYPE_ACCOUNT = 0;
	final public static int SUBJECT_TYPE_INTEREST = 1;
	final public static int SUBJECT_TYPE_PREDRAWINTEREST = 2;
	final public static int SUBJECT_TYPE_INTERESTTAX = 3;
	final public static int SUBJECT_TYPE_COMMISSION = 4;
	final public static int SUBJECT_TYPE_NEGOTIATEINTEREST = 5;
	
	//信贷资产转让科目类型
	final public static int SUBJECT_TYPE_REPURCHASE_NOTIFY = 6;//卖出回购金融资产款
	final public static int SUBJECT_TYPE_EXPENSE = 7;//金融企业往来利息支出科目
	final public static int SUBJECT_TYPE_HANDLE = 8;//金融企业往来应付利息科目
	final public static int SUBJECT_TYPE_ZYAMOUNT = 9;//自营贷款本金科目
	final public static int SUBJECT_TYPE_MDAMOUNT = 10;//应付卖断本金科目
	final public static int SUBJECT_TYPE_MDINTEREST = 11;//应付卖断利息科目
	final public static int SUBJECT_TYPE_ZRCOMMISSION = 12;//手续费及佣金收入科目
	private Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);
	private Account account;
	public AccountOperation() throws RemoteException
	{
		try
		{
			AccountHome home = (AccountHome) EJBHomeFactory.getFactory().lookUpHome(AccountHome.class);
			account = (Account) home.create();
		}
		catch (RemoteException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (CreateException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.debug("AccountOperation::Constructor 11!!!~");
	}
	public boolean queryAccountBalance(long m_lOfficeID,long currecyid,String accountno,double je) throws RemoteException, IRollbackException{
		
//		Timestamp ts=;
		QueryAccountWhereInfo qawi=new QueryAccountWhereInfo();
		qawi.setQueryDate(Env.getSystemDate(m_lOfficeID,currecyid));
		qawi.setStartAccountNo(accountno);
		qawi.setEndAccountNo(accountno);
		qawi.setCurrencyID(currecyid);
		qawi.setOfficeID(m_lOfficeID);
		boolean b=true;
		try{
			AccountEJB ae=new AccountEJB();
			Double dl= ae.queryAccountBalance(qawi);
		
		if(dl!=null&&dl.doubleValue()<je){
			b=false;
		}
		}catch(Exception e){
			e.printStackTrace();
			throw new RemoteException(e.getMessage(),e);
		}
		return b;
	}
	//conn即做作非容器管理的数据库操作的数据联结，同时也做为是否为容器管理事务操作的判断标志,null表示为容器管理事务，反之则为非容器管理
	private Connection conn = null;
	/**本构造函数暂时暂时只为账簿调用的的getSubjectBySubAccountID方法初使化账户模块使用*/
	public AccountOperation(Connection conn) throws RemoteException
	{
		if (conn == null)
		{
			try
			{
				AccountHome home = (AccountHome) EJBHomeFactory.getFactory().lookUpHome(AccountHome.class);
				account = (Account) home.create();
			}
			catch (RemoteException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (CreateException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (IException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			this.conn = conn;
		}
	}

	/**
	 * 不进行EJB及数据库连接的构造函数
	 * */
	public AccountOperation(long l){
	}
	
	/**
	 * 
	 * @param lAccountID
	 * @param dPayAmount
	 * @return
	 * @throws IRollbackException
	 */
	public boolean isOverDraft(long lAccountID, long lSubAccountID, double dPayAmount)
		throws RemoteException, IRollbackException
	{
		boolean bReturn = false;
		bReturn = account.isOverDraft(lAccountID, lSubAccountID, dPayAmount);
		return bReturn;
	}

	/**
	 * 方法说明：判断 账户余额支付累计未复核交易金额后是否还有余额支付此交易金额（实际余额-累计未复核金额-交易金额<0,不考虑账户是否允许透支）
	 * 
	 * @param lAccountID
	 * @param dPayAmount
	 *            @return: 不足 返回true; 足 返回false
	 * @throws RemoteException,IRollbackException
	 * @throws IRollbackException
	 */
	public boolean isLackBalanceToDraft(long lAccountID, double dPayAmount) throws RemoteException, IRollbackException
	{
		boolean bReturn = false;
		bReturn = account.isLackBalanceToDraft(lAccountID, dPayAmount);
		return bReturn;
	}
	
	public long validateAccountStatus(long lAccountID) throws RemoteException, IRollbackException
	{
		return account.validateAccountStatus(lAccountID);
	}
	/**
	 * 注意:这个方法的调用与账户模块的其它函数的调用都有区别，他与构造函数AccountOperation(Connection conn)配套使用
	 * 暂时仅供总账模块使用
	 * @param lAccountID
	 * @return
	 * @throws IRollbackException
	 */
	public String getSubjectBySubAccountID(long lSubAccountID, int subjectType)
		throws RemoteException, IRollbackException, IException
	{
		log.debug("accountOperation::getSubjectBySubAccountID");
		if (conn == null)
		{
			return account.getSubjectBySubAccountID(lSubAccountID, subjectType);
		}
		else
		{
			AccountBean accBean = new AccountBean(conn);
			return accBean.getSubjectBySubAccountID(lSubAccountID, subjectType);
		}
	}
	public String getSubjectByOther(long lCracontractID, int subjectType)
	throws RemoteException, IRollbackException, IException
{
	log.debug("accountOperation::getSubjectByOther");
	if (conn == null)
	{
		return account.getSubjectByOther(lCracontractID, subjectType);
	}
	else
	{
		AccountBean accBean = new AccountBean(conn);
		return accBean.getSubjectByOther(lCracontractID, subjectType);
	}
}
	/**
	 * 
	 * @param lAccountID
	 * @return
	 * @throws IRollbackException
	 */
	public String getSubjectByBankID(long lBankID) throws RemoteException, IRollbackException
	{
		String strSubject = null;
		BranchInfo bi = null;
		Sett_BranchDAO objBranchDAO = new Sett_BranchDAO();
		try
		{
			bi = objBranchDAO.findByID(lBankID);
			if(bi!=null && !"".equals(bi.getSubjectCode()))
			{
				if(NameRef.getSubjectIdByCode(bi.getSubjectCode())<=0)
				{
					throw new IException("找不到该开户行"+bi.getBranchName()+"对应的科目编号,导致生成会计分录失败");
				}
			}
		}
		catch (Exception e)
		{
			throw new RemoteException(e.getMessage());
		}
		if (bi != null)
			strSubject = bi.getSubjectCode();
		return strSubject;
	}
	
	/**
	 * 
	 * @param lAccountID
	 * @return
	 * @throws IRollbackException
	 */
	public String getSubjectByGLTypeID(long lGLTypeID) throws RemoteException, IRollbackException
	{
		Sett_GeneralLedgerDAO glDAO = new Sett_GeneralLedgerDAO();
		String subjectCode;
		try
		{
			subjectCode = glDAO.findSubjectCodeByID(lGLTypeID);
		}
		catch (Exception e)
		{
			throw new RemoteException(e.getMessage());
		}
		return subjectCode;
	}

	/**
	 * 方法说明：保存外部账户
	 * @param ExternalAccountInfo
	 * @return 保存记录ID
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long saveExternalAccount(ExternalAccountInfo info) throws RemoteException, IRollbackException
	{
		return account.saveExternalAccount(info);
	}

	public long addCurrentUncheckAmount(long lAccountID, long lOppAccountID, double dPayAmount)
		throws RemoteException, IRollbackException
	{
		return account.addCurrentUncheckAmount(lAccountID, lOppAccountID, dPayAmount);
	}

	/**
	 * 方法说明：增加定期子账户累计未复核付款金额。 保存时：未复核累计付款金额增加，参数dPaymount为正数；复核时：未复核累计付款金额减少，参数
	 * dPaymount为负数
	 * 
	 * @param lAccountID
	 * @param dPayAmount
	 * @return @throws
	 *         RemoteException
	 * @throws IRollbackException
	 */

	public long addFixedUncheckAmount(long lAccountID, String strFixedDepositNo, double dPayAmount,long lTransType)
		throws RemoteException, IRollbackException
	{
		return account.addFixedUncheckAmount(lAccountID, strFixedDepositNo, dPayAmount);
	}
	
	/**
	 * 更新 子账户 中 累计未复核付款金额，更新为字段mbalance的值
	 * @param lAccountID
	 * @param contractid
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void addFixedUncheckAmount4Recog(long lAccountID, long contractid)
	    throws RemoteException, IRollbackException
	{
		account.addFixedUncheckAmount4Recog(lAccountID,contractid);
	}

	/**
	 * 方法说明：增加贷款子账户累计未复核付款金额。 保存时：未复核累计付款金额增加，参数dPaymount为正数；复核时：未复核累计付款金额减少，参数
	 * dPaymount为负数
	 * 
	 * @param lAccountID
	 * @param dPayAmount
	 * @return @throws
	 *         RemoteException
	 * @throws IRollbackException
	 */

	public long addLoanUncheckAmount(long lAccountID, long loanNoteID, double dPayAmount)
		throws RemoteException, IRollbackException
	{
		return account.addLoanUncheckAmount(lAccountID, loanNoteID, dPayAmount);
	}

	/**
	 * 方法说明：扣除活期累计未复核付款金额。 保存时：未复核累计付款金额增加，参数dPaymount为正数；复核时：未复核累计付款金额减少，参数
	 * dPaymount为负数
	 * 
	 * @param lSubAccountID
	 * @param dPayAmount
	 * @return @throws
	 *         RemoteException
	 * @throws IRollbackException
	 */
	public long subtractCurrentUncheckAmount(long lAccountID, double dPayAmount)
		throws RemoteException, IRollbackException
	{
		log.debug("AccountOperation::subtractCurrentUncheckAmount");
		return account.subtractCurrentUncheckAmount(lAccountID, dPayAmount);
	}

	/**
	 * 方法说明：扣除定期累计未复核付款金额。 保存时：未复核累计付款金额增加，参数dPaymount为正数；复核时：未复核累计付款金额减少，参数
	 * dPaymount为负数
	 * 
	 * @param lSubAccountID
	 * @param dPayAmount
	 * @return @throws
	 *         RemoteException
	 * @throws IRollbackException
	 */
	public long subtractFixedUncheckAmount(long lAccountID, String strFixedDepositNo, double dPayAmount)
		throws RemoteException, IRollbackException
	{
		return account.subtractFixedUncheckAmount(lAccountID, strFixedDepositNo, dPayAmount);
	}
    /**
     * 更新 子账户 中 累计未复核金额，更新为0.0
     * @param lAccountID
     * @param contractid
     * @throws RemoteException
     * @throws IRollbackException
     */
	public void subtractFixedUncheckAmount4Recog(long lAccountID, long contractid)
	   throws RemoteException, IRollbackException
	{
		account.subtractUncheckAmount4Recog(lAccountID, contractid);
	}

	/**
	 * 方法说明：扣除贷款累计未复核付款金额。 保存时：未复核累计付款金额增加，参数dPaymount为正数；复核时：未复核累计付款金额减少，参数
	 * dPaymount为负数
	 * 
	 * @param lSubAccountID
	 * @param dPayAmount
	 * @return @throws
	 *         RemoteException
	 * @throws IRollbackException
	 */
	public long subtractLoanUncheckAmount(long lAccountID, long loanNoteID, double dPayAmount)
		throws RemoteException, IRollbackException
	{
		return account.subtractLoanUncheckAmount(lAccountID, loanNoteID, dPayAmount);

	}

	/**
	 * 方法说明：活期支取。
	 * @param tadi
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long withdrawCurrent(TransAccountDetailInfo tadi) throws RemoteException, IRollbackException
	{
		return account.withdrawCurrent(tadi);
	}

	/**
	 * 方法说明：活期支取。
	 * @param tadi
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long withdrawCurrent(TransAccountDetailInfo tadi, Connection conn) throws IException, IRollbackException
	{
		AccountBean accBean = new AccountBean(conn);
		return accBean.withdrawCurrent(tadi);
	}

	/**
	 * 方法说明：取消活期支取。
	 * @param tadi
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void cancelWithdrawCurrent(TransAccountDetailInfo tadi) throws RemoteException, IRollbackException
	{
		account.cancelWithdrawCurrent(tadi);
	}

	/**
	 * 方法说明：取消活期支取。
	 * @param tadi
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void cancelWithdrawCurrent(TransAccountDetailInfo tadi, Connection conn) throws IException, IRollbackException
	{
		AccountBean accBean = new AccountBean(conn);
		accBean.cancelWithdrawCurrent(tadi);
	}

	/**
	 * 方法说明：活期存入。
	 * @param tadi
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long depositCurrent(TransAccountDetailInfo tadi) throws RemoteException, IRollbackException
	{
		return account.depositCurrent(tadi);
	}

	/**
	 * 方法说明：活期存入 非容器管理事务
	 * @param tadi
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long depositCurrent(TransAccountDetailInfo tadi, Connection conn) throws IException, IRollbackException
	{
		AccountBean accBean = new AccountBean(conn);
		return accBean.depositCurrent(tadi);
	}
	/**
	 * 删除账户交易明细表的交易明细
	 * @param strTransNo 交易编号
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long deleteTransAccountDetail(String strTransNo) throws RemoteException, IRollbackException
	{
		return account.deleteTransAccountDetail(strTransNo);
	}

	/**
	 * 删除账户交易明细表的交易明细
	 * @param strTransNo 交易编号
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long deleteTransAccountDetail(String strTransNo, Connection conn) throws IException
	{
		AccountBean accBean = new AccountBean(conn);
		return accBean.deleteTransAccountDetail(strTransNo);
	}

	/**
	 * 方法说明：取消活期存入。
	 * @param tadi
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void cancelDepositCurrent(TransAccountDetailInfo tadi) throws RemoteException, IRollbackException
	{
		account.cancelDepositCurrent(tadi);
	}

	/**
	 * 方法说明：取消活期存入。
	 * @param tadi
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void cancelDepositCurrent(TransAccountDetailInfo tadi, Connection conn) throws IException, IRollbackException
	{
		AccountBean accBean = new AccountBean(conn);
		accBean.cancelDepositCurrent(tadi);
	}
	/**
	 * 方法说明：根据账户ID，查询账户信息
	 * @param  lAccountID
	 * @return:AccountInfo ai
	 * @throws RemoteException,IRollbackException
	 */
	public AccountInfo findAccountByID(long lAccountID) throws RemoteException, IRollbackException
	{
		return account.findAccountByID(lAccountID);
	}
	/**
	 * 方法说明：根据子账户ID，查询子账户信息
	 * @param  lAccountID
	 * @return:AccountInfo ai
	 * @throws RemoteException,IRollbackException
	 */
	public SubAccountAssemblerInfo findSubAccountByID(long lSubAccountID) throws RemoteException, IRollbackException
	{
		return account.findSubAccountByID(lSubAccountID);
	}
	/**
	 * 定期开空户
	 * @param safi
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long openFixSubAccount(SubAccountFixedInfo safi) throws RemoteException, IRollbackException
	{
		return account.openFixSubAccount(safi);
	}
	/**
	 * 定期存入
	 * @param tadi
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long depositFix(TransAccountDetailInfo tadi) throws RemoteException, IRollbackException
	{
		return account.depositFix(tadi);
	}

	/**
	 * 定期支取
	 * @param tadi
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long withdrawFix(TransAccountDetailInfo tadi) throws RemoteException, IRollbackException
	{
		return account.withdrawFix(tadi);
	}
	
	public Collection withdrawFix4Recog(TransAccountDetailInfo tadi,long nContractID) throws RemoteException, IRollbackException
	{
		return account.withdrawFix4Recog(tadi,nContractID);
	}
	/**
	 * 融资租赁还款支取
	 * @param tadi
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long withdrawFinance(TransAccountDetailInfo tadi) throws RemoteException, IRollbackException
	{
		return account.withdrawFinance(tadi);
	}

	/**
	 * 删除定期子账户
	 * @param safi
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void deleteFixSubAccount(long subAccountID) throws RemoteException, IRollbackException
	{
		account.deleteFixSubAccount(subAccountID);
	}

	/** 方法说明：删除贷款子账户。
	* 
	* @param tadi
	* @return @throws
	*         RemoteException
	* @throws IRollbackException
	*/
	public void deleteLoanSubAccount(long subAccountID) throws RemoteException, IRollbackException
	{
		account.deleteLoanSubAccount(subAccountID);
	}

	/**
	 * 取消定期存入
	 * @param tadi
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long cancelDepositFix(TransAccountDetailInfo tadi) throws RemoteException, IRollbackException
	{
		return account.cancelDepositFix(tadi);
	}

	/**
	 * 取消融资租赁保证金还款存入
	 * @param tadi
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void cancelWithdrawFinance(TransAccountDetailInfo tadi) throws RemoteException, IRollbackException
	{
		account.cancelWithdrawFinance(tadi);

	}
	
	/**
	 * 取消定期存入
	 * @param tadi
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void cancelWithdrawFix(TransAccountDetailInfo tadi) throws RemoteException, IRollbackException
	{
		account.cancelWithdrawFix(tadi);

	}
	
	public void cancelWithdrawFix4Recog(TransAccountDetailInfo tadi,long nContractID ) throws RemoteException, IRollbackException
	{
		account.cancelWithdrawFix4Recog(tadi,nContractID);

	}

	/**
	 * 方法说明 ： 开立定期存款子账户
	 * 
	 * @param safi
	 * @return @throws
	 *         RemoteException
	 * @throws IRollbackException
	 */
	public long openLoanSubAccount(SubAccountLoanInfo sali) throws RemoteException, IRollbackException
	{
		return account.openLoanSubAccount(sali);
	}

	/**
	 * 方法说明：贷款发放
	 * 
	 * @param tadi
	 * @return 子账户ID
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long grantLoan(TransAccountDetailInfo tadi) throws RemoteException, IRollbackException
	{
		return account.grantLoan(tadi);
	}

	/**
	 * 方法说明：取消贷款发放
	 * 
	 * @param tadi
	 * 
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long cancelGrantLoan(TransAccountDetailInfo tadi) throws RemoteException, IRollbackException
	{
		return account.cancelGrantLoan(tadi);
	}

	/**
	 * 贷款归还
	 * 
	 * @param tadi
	 * 
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long repayLoan(TransAccountDetailInfo tadi) throws RemoteException, IRollbackException
	{
		return account.repayLoan(tadi);
	}
	/**
	 * 取消贷款归还
	 * 
	 * @param tadi
	 * 
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long cancelRepayLoan(TransAccountDetailInfo tadi) throws RemoteException, IRollbackException
	{
		return account.cancelRepayLoan(tadi);
	}

	/**
	 * 根据子账户ID取不同账户类型(定期及贷款)的计提利息
	*/
	public double getPredrawInterestBySubAccountIDAndAccountType(long subAccountID, long accountType)
		throws RemoteException, IRollbackException
	{
		return account.getPredrawInterestBySubAccountIDAndAccountType(subAccountID, accountType);
	}

	/**
	 * 根据活期主账户ID查询子账户ID
	*/
	public long getCurrentSubAccoutIDByAccoutID(long accoutID) throws RemoteException, IRollbackException
	{
		return account.getCurrentSubAccoutIDByAccoutID(accoutID);
	}
	/**
	 * 根据贷款主账户ID和放款通知单查询子账户ID
	*/
	public long getLoanSubAccountIDByAccountIDAndLoanNoteID(long accoutID, long loanNoteID)
		throws RemoteException, IRollbackException
	{
		return account.getLoanSubAccountIDByAccountIDAndLoanNoteID(accoutID, loanNoteID);
	}
	
	/**
	 * 根据贷款主账户ID、放款通知单和状态查询子账户ID
	*/
	public long getLoanSubAccountIDByAccountIDAndLoanNoteIDAndStatus(long accoutID, long loanNoteID,long lStatus)
		throws RemoteException, IRollbackException
	{
		return account.getLoanSubAccountIDByAccountIDAndLoanNoteIDAndStatus(accoutID, loanNoteID,lStatus);
	}

	public long getAccountTypeBySubAccountID(long subAccountID) throws RemoteException, IRollbackException
	{
		return account.getAccountTypeBySubAccountID(subAccountID);
	}

	/**根据交易费用类型ID查询科目号*/
	public String getSubjectByTransFeeTypeID(long transFeeTypeID) throws RemoteException, IRollbackException
	{
		return account.getSubjectByTransFeeTypeID(transFeeTypeID);
	}
//	/**根据 ID修改预算状态*/
//	public long  updateBudgetStatusID(long id,long budgetStatusID) throws RemoteException, IRollbackException
//	{
//		return account.updateBudgetStatusID(id,budgetStatusID);
//	}
	
	/**
	 * 到期续存处理计提利息和计提日期
	 * @param tadi
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long ContinueFixedPreDrawInterest(TransAccountDetailInfo tadi, String strCheckType) throws RemoteException, IRollbackException
	{
		return account.ContinueFixedPreDrawInterest(tadi, strCheckType);
	}
	
	//获取客户
	public String findClientCodeBySubAccountID(long lSubAccountID) throws RemoteException, IRollbackException, IException
	{
		if (conn == null)
		{
			return account.findClientCodeBySubAccountID(lSubAccountID);
		}
		else
		{
			AccountBean accBean = new AccountBean(conn);
			return accBean.findClientCodeBySubAccountID(lSubAccountID);
		}
	}
	
	//获取账户类型
	public long findAccountTypeBySubAccountID(long lSubAccountID) throws RemoteException, IRollbackException, IException
	{
		if (conn == null)
		{
			return account.findAccountTypeBySubAccountID(lSubAccountID);
		}
		else
		{
			AccountBean accBean = new AccountBean(conn);
			return accBean.findAccountTypeBySubAccountID(lSubAccountID);
		}
	}
	
	//获取账户类型
	public long updateCommission(TransAccountDetailInfo tadi) throws RemoteException, IRollbackException
	{
		if (conn == null)
		{
			return account.updateCommission(tadi);
		}
		else
		{
			AccountBean accBean = new AccountBean(conn);
			return accBean.updateCommission(tadi);
		}
	}
	//获取账户类型
	public double findAvailableBalance(long lAccountID,long lOfficeID,long lCurrencyID) throws RemoteException, IRollbackException, IException
	{
		AccountBean accBean = new AccountBean(conn);
		return accBean.findAvailableBalance(lAccountID, lOfficeID, lCurrencyID);
	}
}
