/*
 * Created on 2004-4-9
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.ebank.obinterest.bizlogic;

//import java.rmi.RemoteException;
import java.util.Vector;
import com.iss.itreasury.ebank.obinterest.dao.OBInterestDao;
import com.iss.itreasury.settlement.interest.dao.Sett_LoanNoticeDAO;
import com.iss.itreasury.settlement.interest.dataentity.LoanNoticeInfo;
import com.iss.itreasury.settlement.interest.dataentity.InterestEstimateQueryInfo;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IRollbackException;
//import com.iss.itreasury.util.Log4j;
import com.iss.itreasury.settlement.util.*;
import com.iss.itreasury.loan.contract.dataentity.ContractInfo;
import com.iss.itreasury.loan.contract.dao.ContractDao;
import com.iss.itreasury.settlement.query.queryobj.QLoanNotice;

/**
 * @author hyzeng
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class OBInterestOperation
{
	//private static Log4j log4j = null;

	/**
	 * Method disPatchLoanNotice.
	 * 根据通知书类型，进行分发
	 * 如果是季度结息提醒，即应付利息通知单
	 * 则先根据合同号找出该合同下的放款通知单
	 * 在调用findLoanNotice方法
	 * 否则直接调用
	 * @param info
	 * @throws IRollbackException
	 * @throws RemoteException
	 * @throws IException
	 */
	public void dispatchLoanNotice(LoanNoticeInfo info) throws IRollbackException, IException
	{
		OBInterestDao obInterestDao = new OBInterestDao();
		try
		{
			if (info.getFormTypeID() == SETTConstant.LoanNoticeType.LoanInterestNotice)
			{
				long[] tempLong = obInterestDao.findPayFormByContractID(info.getContractID());
				System.out.println("tempLong.length=" + tempLong.length);

				if (tempLong.length == 1 && tempLong[0] == -1)
				{
					throw new IException("该合同下暂无放款通知单！");
				}
				else
				{
					for (int i = 0; i < tempLong.length; i++)
					{
						info.setLoanNoteID(tempLong[i]);
						findLoanNotice(info);
					}
				}
			}
			else
			{
				findLoanNotice(info);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	/**
	 * Method findLoanNotice.
	 * 首先根据Info中传入的放款通知单id以及通知书类型，
	 * 查询表sett_LoanNotice是否已经存在指定通知书类型的通知书
	 * 如果已经存在则返回查询结果集
	 * 如果不存在则在表sett_LoanNotice中查入一条数据库记录
	 * @param Info
	 * @return Vector
	 * @throws IRollbackException
	 * @throws RemoteException
	 */
	public void findLoanNotice(LoanNoticeInfo info) throws IRollbackException, IException
	{
		Vector vctResult = null;
		OBInterestDao obInterestDao = new OBInterestDao();
		LoanNoticeInfo loanNoticeInfo = null;
		String strAccountNo = "";
		long lAccountType = -1;
		String strTemp = "";
		try
		{
			System.out.println("====findLoanNotice start====");
			vctResult = obInterestDao.queryLoanNotice(info);
			if (vctResult != null && vctResult.size() == 1)
			{
				loanNoticeInfo = (LoanNoticeInfo) vctResult.elementAt(0);
				if (loanNoticeInfo.getID() < 0)
				{
					//开始根据条件查询并计算通知书信息并进行保存
					//贷款合同号
					System.out.println("合同号：" + info.getContractID());
					info.setContractNo(NameRef.getContractNoByID(info.getContractID()));
					//放款通知单号
					info.setPayFormNo(NameRef.getPayFormNoByID(info.getLoanNoteID()));
					System.out.println("放款通知单号：" + info.getPayFormNo());
					//账户类型
					strAccountNo = NameRef.getAccountNoByID(info.getAccountID());
					lAccountType = NameRef.getAccountTypeByID(info.getAccountID());
					if (lAccountType > 0)
					{
						if (SETTConstant.AccountType.isTrustAccountType(lAccountType)
								|| SETTConstant.AccountType.isConsignAccountType(lAccountType))
						{
							info.setAccountTypeID(lAccountType);
						}
					}
					//结息日，已经在dao的返回结果里面设置
					System.out.println("====query contractinfo start====");
					ContractDao contractDao = new ContractDao();
					ContractInfo contractInfo = contractDao.findByID(info.getContractID());
					if (contractInfo != null)
					{
						//借款人名称
						info.setBorrowClientName(NameRef.getClientNameByID(contractInfo.getBorrowClientID()));
						System.out.println("借款人名称：" + info.getBorrowClientName());
						//办事处
						info.setOfficeID(contractInfo.getOfficeID());
						//币种
						info.setCurrencyID(contractInfo.getCurrencyID());
						//贷款金额
						info.setLoanAmount(contractInfo.getLoanAmount());
						//贷款起始日期
						info.setLoanStartDate(contractInfo.getLoanStart());
						//贷款结束日期
						info.setLoanEndDate(contractInfo.getLoanEnd());
						//贷款期限
						info.setLoanTerm(contractInfo.getIntervalNum());
					}
					//设置调用算息方法queryNoticeInterest所需要的初始条件
					System.out.println("====set query interest condition start====");
					InterestEstimateQueryInfo interestEstimateQueryInfo = new InterestEstimateQueryInfo();
					interestEstimateQueryInfo.setCurrencyID(info.getCurrencyID());
					interestEstimateQueryInfo.setOfficeID(info.getOfficeID());
					interestEstimateQueryInfo.setClearInterestDate(info.getClearInterestDate());
					System.out.println("===通知书类型:" + info.getFormTypeID());
					interestEstimateQueryInfo.setNoticetype(info.getFormTypeID());
					System.out.println("====结息日期:" + interestEstimateQueryInfo.getClearInterestDate());
					if (SETTConstant.AccountType.isTrustAccountType(info.getAccountTypeID()))
					{
						interestEstimateQueryInfo.setFeeType(2);
						System.out.println("账户类型：自营");
					}
					if (SETTConstant.AccountType.isConsignAccountType(info.getAccountTypeID()))
					{
						interestEstimateQueryInfo.setFeeType(3);
						System.out.println("账户类型：委托");
					}
					interestEstimateQueryInfo.setIsSelectInterest(1);
					interestEstimateQueryInfo.setIsSelectForfeitInterest(1);
					interestEstimateQueryInfo.setIsSelectCompoundInterest(1);
					//开调用算息接口
					System.out.println("====start query interest info====");
					QLoanNotice qLoanNotice = new QLoanNotice();
					Vector vctTemp = new Vector();
					LoanNoticeInfo copyInfo = new LoanNoticeInfo();
					//开始拷贝数据
					copyInfo.setOfficeID(info.getOfficeID());
					copyInfo.setCurrencyID(info.getCurrencyID());
					copyInfo.setLoanNoteID(info.getLoanNoteID());
					copyInfo.setPayFormNo(info.getPayFormNo());
					copyInfo.setSubAccountID(info.getSubAccountID());
					copyInfo.setAccountID(info.getAccountID());
					copyInfo.setAccountTypeID(info.getAccountTypeID());
					copyInfo.setContractID(info.getContractID());
					copyInfo.setBorrowClientID(info.getBorrowClientID());
					copyInfo.setClearInterestDate(info.getClearInterestDate());
					copyInfo.setLoanAmount(info.getLoanAmount());
					copyInfo.setLoanStartDate(info.getLoanStartDate());
					copyInfo.setLoanEndDate(info.getLoanEndDate());
					copyInfo.setLoanTerm(info.getLoanTerm());
					copyInfo.setFormTypeID(info.getFormTypeID());
					vctTemp.add(copyInfo);
					Vector vctQuery = null;
					vctQuery = qLoanNotice.queryNoticeInterest(null, vctTemp, interestEstimateQueryInfo);
					if (vctQuery != null && vctQuery.size() > 0)
					{
						LoanNoticeInfo tempInfo = (LoanNoticeInfo) vctQuery.elementAt(0);
						if (tempInfo != null)
						{
							//贷款余额
							info.setLoanBalance(tempInfo.getLoanBalance());
							//原合同利率
							info.setOriginalContractRate(tempInfo.getOriginalContractRate());
							//调整后合同利率
							info.setNewExecuteRate(tempInfo.getNewExecuteRate());
							//执行利率
							info.setExecuteRate(tempInfo.getExecuteRate());
							//调整后执行利率
							info.setNewExecuteRate(tempInfo.getNewExecuteRate());
							//调整日期
							info.setExecuteRateValidDate(tempInfo.getExecuteRateValidDate());
							//通知书年度
							info.setFormYear(tempInfo.getFormYear());
							//通知书编号
							info.setFormNo(tempInfo.getFormNo());
							//催收次数
							info.setFormNum(tempInfo.getFormNum());
							//正常利息
							info.setInterest(tempInfo.getInterest());
							//担保费
							info.setSuretyFee(tempInfo.getSuretyFee());
							//复利
							info.setCompoundInterest(tempInfo.getCompoundInterest());
							//手续费率
							info.setCommissionRate(tempInfo.getCommissionRate());
							//手续费
							info.setCommission(tempInfo.getCommission());
							//逾期罚息利率
							info.setOverDueInterestRate(tempInfo.getOverDueInterestRate());
							//逾期罚息
							info.setOverDueInterest(tempInfo.getOverDueInterest());
							//正常利息的起息日
							info.setInterestStartDate(tempInfo.getInterestStartDate());
							//*****以下四个字段没有存储
							//担保客户
							//担保合同号暂放
							//利息合计
							//本息合计
							//*****以上四个字段没有存储
						}
					}
					//向table sett_loannotice 中插入一条记录
					System.out.println("====start insert a loannotice record====");
					Sett_LoanNoticeDAO sett_LoanNoticeDAO = new Sett_LoanNoticeDAO();
					long lID = sett_LoanNoticeDAO.add(null, info);
					if (lID > 0)
					{
						System.out.println("lID:" + lID);
					}
					vctResult = new Vector();
					vctResult.add(info);
				}
			}
			else
			{
				System.out.println("===========the loannotices are already existed!===");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	public static void main(String[] args)
	{
		try
		{
			OBInterestEJB obInterestEJB = new OBInterestEJB();
			LoanNoticeInfo info = new LoanNoticeInfo();
			info.setContractID(390);
			info.setLoanNoteID(1546);
			info.setFormTypeID(1);
			obInterestEJB.dispatchLoanNotice(info);
		}
		catch (Exception e)
		{
			System.out.println(e.toString());
		}
	}
}
