/*
 * Created on 2003-11-18
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.iss.itreasury.settlement.query.queryobj;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import com.iss.itreasury.util.Constant;
import com.iss.itreasury.settlement.account.dataentity.*;
import com.iss.itreasury.settlement.account.dao.*;
import com.iss.itreasury.settlement.interest.bizlogic.InterestCalculation;
import com.iss.itreasury.settlement.interest.bizlogic.InterestOperation;
import com.iss.itreasury.settlement.interest.dataentity.LoanAccountInterestInfo;

import com.iss.itreasury.util.Log4j;

import com.iss.itreasury.settlement.query.resultinfo.*;
import com.iss.itreasury.settlement.setting.dao.Sett_TaxRatePlanSettingItemDAO;
import com.iss.itreasury.settlement.transloan.bizlogic.BankLoanQuery;
import com.iss.itreasury.settlement.transloan.dataentity.LoanPayFormDetailInfo;
import com.iss.itreasury.settlement.transloan.dao.Sett_TransGrantLoanDAO;
import com.iss.itreasury.settlement.transdiscount.dao.Sett_TransGrantDiscountDAO;
import com.iss.itreasury.settlement.transdiscount.dataentity.TransGrantDiscountInfo;
import com.iss.itreasury.loan.loanpaynotice.dao.LoanPayNoticeDao;
import com.iss.itreasury.util.*;
import com.iss.itreasury.loan.loanpaynotice.dataentity.*;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.settlement.util.SETTConstant;

/**
 * @author yfsu
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class QSubAccountDao extends BaseQueryObject
{
	Log4j logger = null;
	public QSubAccountDao()
	{
		super();
		// TODO Auto-generated constructor stub
		logger = new Log4j(Constant.ModuleType.SETTLEMENT, this);
	}

	//通过子账户ID查询定期存款单信息
	public QuerySubFixedInfo findFixedInfoBySubaccountID(long nSubAccountID) throws Exception
	{
		Sett_SubAccountDAO dao = new Sett_SubAccountDAO();
		QuerySubFixedInfo fixInfo = null;
		SubAccountAssemblerInfo subInfo = null;
		try
		{
			fixInfo = new QuerySubFixedInfo();
			subInfo = dao.findByID(nSubAccountID);
			if (subInfo != null)
			{
				fixInfo.setID(subInfo.getSubAccountFixedInfo().getID());
				fixInfo.setAccountID(subInfo.getSubAccountFixedInfo().getAccountID());
				fixInfo.setAF_sDepositNo(subInfo.getSubAccountFixedInfo().getDepositNo());
				fixInfo.setAF_dtStart(subInfo.getSubAccountFixedInfo().getStartDate());
				fixInfo.setAF_nDepositTerm(subInfo.getSubAccountFixedInfo().getDepositTerm());
				fixInfo.setAF_dtEnd(subInfo.getSubAccountFixedInfo().getEndDate());
				fixInfo.setAF_mRate(subInfo.getSubAccountFixedInfo().getRate());
				fixInfo.setMOpenAmount(subInfo.getSubAccountFixedInfo().getOpenAmount());
				fixInfo.setMBalance(subInfo.getSubAccountFixedInfo().getBalance());
				fixInfo.setNStatusID(subInfo.getSubAccountFixedInfo().getStatusID());
				fixInfo.setDtOpen(subInfo.getSubAccountFixedInfo().getOpenDate());
				fixInfo.setDtFinish(subInfo.getSubAccountFixedInfo().getFinishDate());
				fixInfo.setAF_mPreDrawInterest(subInfo.getSubAccountFixedInfo().getPreDrawInterest());
				
				//保证金计提利息
				fixInfo.setAL_mPreDrawInterest(subInfo.getSubAccountCurrenctInfo().getDrawInterest());
				
				fixInfo.setAF_mInterest(subInfo.getSubAccountFixedInfo().getInterest());
				fixInfo.setLPayFormID(subInfo.getSubAccountLoanInfo().getLoanNoteID()); //融资租赁专用
				
				fixInfo.setIsAutoContinue(subInfo.getSubAccountFixedInfo().getIsAutoContinue());//是否自动续存
				fixInfo.setAutoContinueType(subInfo.getSubAccountFixedInfo().getAutoContinueType());//自动续存方式
				fixInfo.setAutoContinueAccountID(subInfo.getSubAccountFixedInfo().getInterestAccountID());//续存收息账户id（续存方式为本金续存时）
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return fixInfo;

	}

	//通过子账户ID查询通知存单信息
	public QuerySubNoteInfo findNoteInfoBySubaccountID(long nSubAccountID) throws Exception
	{
		QuerySubNoteInfo info = null;
		SubAccountAssemblerInfo subInfo = null;
		try
		{
			Sett_SubAccountDAO dao = new Sett_SubAccountDAO();
			info = new QuerySubNoteInfo();
			subInfo = dao.findByID(nSubAccountID);
			if (subInfo != null)
			{
				info.setID(subInfo.getSubAccountFixedInfo().getID());
				info.setAccountID(subInfo.getSubAccountFixedInfo().getAccountID());
				info.setAF_sDepositNo(subInfo.getSubAccountFixedInfo().getDepositNo());
				info.setAF_dtStart(subInfo.getSubAccountFixedInfo().getStartDate());
				info.setAF_nNoticeDay(subInfo.getSubAccountFixedInfo().getNoticeDay());
				info.setMOpenAmount(subInfo.getSubAccountFixedInfo().getOpenAmount());
				info.setMBalance(subInfo.getSubAccountFixedInfo().getBalance());
				info.setNStatusID(subInfo.getSubAccountFixedInfo().getStatusID());
				info.setDtOpen(subInfo.getSubAccountFixedInfo().getOpenDate());
				info.setDtFinish(subInfo.getSubAccountFixedInfo().getFinishDate());
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}

		return info;
	}
	/**
	 * 获得累计还息
	 * @param nSubAccountID
	 * @return
	 * @throws Exception
	 */
	public double getTotalRepayInterest(long nSubAccountID) throws Exception
	{
		double dInterest = 0.0;
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		String strSQL = "";		
		try
		{
			con = this.getConnection();
			strSQL = "select sum(Interest) Interest from sett_vTransInterestDetail where subaccountID="+nSubAccountID;
			strSQL += " and InterestTypeID not in ("+SETTConstant.InterestFeeType.ASSURE+","+SETTConstant.InterestFeeType.COMMISION+","+SETTConstant.InterestFeeType.INTERESTTAX+","+SETTConstant.InterestFeeType.PREDRAWINTEREST+" ";
			
			//Boxu Add 2008年3月14日 该视图查询冲销计提利息的时候显示nInterestType为"8",所以查询出来的累计还息加上了冲销计提的利息
			strSQL += " , 8 ) ";
			
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if(rs != null && rs.next())
			{
				dInterest = rs.getDouble("Interest");	
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(con);				
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(con);			
		}
		return dInterest;
	}


	//通过子账户ID查询信托贷款信息
	public QuerySubAffianceInfo findAffianceInfoBySubaccountID(long nSubAccountID) throws Exception
	{
		QuerySubAffianceInfo info = null;
		//子账户
		SubAccountAssemblerInfo subInfo = null;
		try
		{
			Sett_SubAccountDAO dao = new Sett_SubAccountDAO();
			info = new QuerySubAffianceInfo();
			subInfo = dao.findByID(nSubAccountID);
			if (subInfo != null)
			{

				info.setID(subInfo.getSubAccountLoanInfo().getID());
				info.setAccountID(subInfo.getSubAccountLoanInfo().getAccountID());
				info.setAL_nLoanNoteID(subInfo.getSubAccountLoanInfo().getLoanNoteID()); //放款通知单					
				info.setNStatusID(subInfo.getSubAccountLoanInfo().getStatusID()); //状态				

				info.setNPayInterestAccountID(subInfo.getSubAccountLoanInfo().getPayInterestAccountID()); //结息账户
								
				info.setAL_nPayInterestAccountID(subInfo.getSubAccountLoanInfo().getPaySuretyAccountID()); //贷款方账户号
				info.setAL_nSuretyAccountID(subInfo.getSubAccountLoanInfo().getReceiveSuretyAccountID()); //担保方账户号
				info.setMBalance(subInfo.getSubAccountLoanInfo().getBalance()); //当前余额				
				info.setAC_mNegotiateInterest(subInfo.getSubAccountLoanInfo().getInterest()); //累计利息
				info.setAF_mPreDrawInterest(subInfo.getSubAccountLoanInfo().getPreDrawInterest()); //已计提利息
				info.setDtOpen(subInfo.getSubAccountLoanInfo().getOpenDate()); //开户日期
				info.setDtFinish(subInfo.getSubAccountLoanInfo().getFinishDate()); //清户日期				
				// 放款状态（子账户状态）
				info.setSubAccountStatusID(subInfo.getSubAccountLoanInfo().getStatusID());

				//通过放款单账户号查询下面的信息
				Sett_TransGrantLoanDAO loanDao = new Sett_TransGrantLoanDAO();
				LoanPayFormDetailInfo tmpInfo = new LoanPayFormDetailInfo();
				tmpInfo.setLoadNoteID(subInfo.getSubAccountLoanInfo().getLoanNoteID());
				LoanPayFormDetailInfo loanInfo = loanDao.findPayFormDetailByCondition(tmpInfo);
				if (loanInfo != null)
				{

					//合同号
					info.setContractCode(loanInfo.getContractCode());
					//贷款种类
					info.setLoanType(loanInfo.getLoanType());
					//贷款期限
					info.setLoanTerm(loanInfo.getLoanTerm());
					//贷款金额  
					info.setAmount(loanInfo.getAmount());
					//起始日期
					info.setAF_dtStart(loanInfo.getStart()); //起始日期				
					//到期日期
					info.setAF_dtEnd(loanInfo.getEnd()); //到期日期
					//利率
					info.setLoanInterestRate(loanInfo.getInterestRate()); //利率	
					info.setAL_mSuretyFee(loanInfo.getAssureRate()); //担保费率
					info.setDtOut(loanInfo.getInterestStart());//放款日期
					info.setCommissionRate(loanInfo.getCommissionRate());
					info.setTypeID1(loanInfo.getTypeID1());//地区分类
					info.setTypeID2(loanInfo.getTypeID2());//行业分类1
					info.setTypeID3(loanInfo.getTypeID3());//行业分类2
				}
				//获得累计还息
				info.setTotalRepaymentInterest(this.getTotalRepayInterest(info.getID()));
				
				if(info.getLoanType()==LOANConstant.LoanType.YT)
				{
					double ytRate = 0.0;
					BankLoanQuery bankLoanQuery =new BankLoanQuery();
					//承贷比例				
					/*
					ytRate=bankLoanQuery.findRateByFormID(subInfo.getSubAccountLoanInfo().getLoanNoteID());
					if(ytRate>0)
					{
						info.setAC_mNegotiateInterest(info.getAC_mNegotiateInterest()/ytRate*100);
					}
					*/
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}

		return info;
	}

	//通过子账户ID查询委托贷款信息
	public QuerySubConsignInfo findConsignInfoBySubaccountID(long nSubAccountID) throws Exception
	{
		QuerySubConsignInfo info = null;
		//子账户
		SubAccountAssemblerInfo subInfo = null;
		try
		{
			Sett_SubAccountDAO dao = new Sett_SubAccountDAO();
			info = new QuerySubConsignInfo();
			subInfo = dao.findByID(nSubAccountID);
			Sett_TaxRatePlanSettingItemDAO iteDAO = new Sett_TaxRatePlanSettingItemDAO();
    		
    		//得到利息税费率计划对应的主利率
    		double mainRate = iteDAO.getMainRate(subInfo.getSubAccountLoanInfo().getInterestTaxRatePlanID());
			if (subInfo != null)
			{

				info.setID(subInfo.getSubAccountLoanInfo().getID()); //子账户ID
                info.setAccountID(subInfo.getSubAccountLoanInfo().getAccountID());// 主账户ID						
				info.setAL_nLoanNoteID(subInfo.getSubAccountLoanInfo().getLoanNoteID()); //放款通知单				
				info.setNStatusID(subInfo.getSubAccountLoanInfo().getStatusID()); //状态	
				info.setAF_mRate(subInfo.getSubAccountLoanInfo().getInterestTaxRate()); //利率	
				info.setAL_nPayInterestAccountID(subInfo.getSubAccountLoanInfo().getPayInterestAccountID()); //贷款方付息账户
                //// 手续费
                Sett_AccountDAO acctDao = new Sett_AccountDAO();
                AccountInfo ai = acctDao.findByID(subInfo.getSubAccountLoanInfo().getAccountID());
                //
                InterestOperation io = new InterestOperation();
                Timestamp tsQueryDate = Env.getSystemDate(ai.getOfficeID(), ai.getCurrencyID());
                
                LoanAccountInterestInfo laii = io.getLoanAccountFee(ai.getOfficeID(),ai.getCurrencyID(),ai.getAccountID(),subInfo.getSubAccountLoanInfo().getID(),tsQueryDate,tsQueryDate,SETTConstant.InterestFeeType.COMMISION);
                
				info.setAL_mCommission(laii.getInterest());//手续费
				
				info.setAL_nCommissionAccountID(subInfo.getSubAccountLoanInfo().getCommissionAccountID()); //支付手续费账户号
				
				info.setAL_mInterestTaxRate(mainRate); //利息税费率
				info.setAL_dtEffectiveTax(subInfo.getSubAccountLoanInfo().getEffectiveTaxDate()); //生效日期
				info.setAL_nInterestTaxAccountID(subInfo.getSubAccountLoanInfo().getInterestTaxAccountID()); //支付利息税费账户号
				
				info.setConsignAccountID(subInfo.getSubAccountLoanInfo().getConsignAccountID()); //委托存款账户号
				
				info.setAL_nReceiveInterestAccountID(subInfo.getSubAccountLoanInfo().getReceiveInterestAccountID()); //委托方收息账户
				
				info.setAL_nIsInterest(subInfo.getSubAccountLoanInfo().getIsInterest()); //是否计息
				info.setMBalance(subInfo.getSubAccountLoanInfo().getBalance()); //当前余额				
				info.setAC_mNegotiateInterest(subInfo.getSubAccountLoanInfo().getInterest()); //累计利息
				info.setAF_mPreDrawInterest(subInfo.getSubAccountLoanInfo().getPreDrawInterest()); //已计提利息
				info.setDtOpen(subInfo.getSubAccountLoanInfo().getOpenDate()); //开户日期
				info.setDtFinish(subInfo.getSubAccountLoanInfo().getFinishDate()); //清户日期
				

				//通过放款单账户号查询下面的信息
				Sett_TransGrantLoanDAO loanDao = new Sett_TransGrantLoanDAO();
				LoanPayFormDetailInfo tmpInfo = new LoanPayFormDetailInfo();
				tmpInfo.setLoadNoteID(subInfo.getSubAccountLoanInfo().getLoanNoteID());
				LoanPayFormDetailInfo loanInfo = loanDao.findPayFormDetailByCondition(tmpInfo);
				if (loanInfo != null)
				{
					//手续费率
					LoanPayNoticeDao loanpayDao = new LoanPayNoticeDao();
					PayNoticeRateInfo pnInfo=loanpayDao.getRateValue(Constant.RateType.CHARGE,
							subInfo.getSubAccountLoanInfo().getLoanNoteID(),
							null);
	System.out.println("\n\n 放款通知单 ="+subInfo.getSubAccountLoanInfo().getLoanNoteID()+"\n\n");
	System.out.println("\n\n pnInfo="+pnInfo+"\n\n");
					info.setAL_mCommissionRate(pnInfo.getInterestRate()); 
					//合同号
					info.setContractCode(loanInfo.getContractCode());
					//贷款种类
					info.setLoanType(loanInfo.getLoanType());
					//贷款期限
					info.setLoanTerm(loanInfo.getLoanTerm());
					//贷款金额  
					info.setAmount(loanInfo.getAmount());
					//委托方                
					info.setClientCode(loanInfo.getClientCode());
					//Added by zwsun, 2007/7/4, 委托方名称
					info.setClientName(loanInfo.getClientName());
					//起始日期	
					info.setAF_dtStart(loanInfo.getStart());
					//到期日期
					info.setAF_dtEnd(loanInfo.getEnd());
					//放款日期
					info.setDtOut(loanInfo.getInterestStart());
					//手续费收取方式  getChargeRateTypeID
					info.setChargeRateTypeID(loanInfo.getChargeRateTypeID());

					//设置年期
					if (loanInfo.getStart() != null)
					{
						info.setNYear(Long.parseLong(loanInfo.getStart().toString().substring(0, 4)));
					}
					
					info.setTypeID1(loanInfo.getTypeID1());//地区分类
					info.setTypeID2(loanInfo.getTypeID2());//行业分类1
					info.setTypeID3(loanInfo.getTypeID3());//行业分类2					
				}
				//获得累计还息
				info.setTotalRepaymentInterest(this.getTotalRepayInterest(info.getID()));				
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}

		return info;
	}

	//通过子账户ID查询贴现发放信息
	public QuerySubDiscountInfo findDiscountInfoBySubaccountID(long nSubAccountID) throws Exception
	{
		QuerySubDiscountInfo info = null;
		//子账户
		SubAccountAssemblerInfo subInfo = null;
		try
		{
			Sett_SubAccountDAO dao = new Sett_SubAccountDAO();
			info = new QuerySubDiscountInfo();
			subInfo = dao.findByID(nSubAccountID);
			if (subInfo != null)
			{
				info.setID(subInfo.getSubAccountLoanInfo().getID());
				info.setAccountID(subInfo.getSubAccountLoanInfo().getAccountID());
				info.setNStatusID(subInfo.getSubAccountLoanInfo().getStatusID()); //状态				
				info.setMBalance(subInfo.getSubAccountLoanInfo().getBalance()); //当前余额
				info.setDtOpen(subInfo.getSubAccountLoanInfo().getOpenDate()); //开户日期
				info.setDtFinish(subInfo.getSubAccountLoanInfo().getFinishDate()); //清户日期
				info.setAL_mInterestTax(subInfo.getSubAccountLoanInfo().getInterest()); //利 息
				
				//通过放款单账户号查询下面的信息
				Sett_TransGrantDiscountDAO discountDao = new Sett_TransGrantDiscountDAO();
				
				TransGrantDiscountInfo discountInfo = discountDao.findSubQueryInfoByDiscountNoteID(subInfo.getSubAccountLoanInfo().getLoanNoteID());
				System.out.println("\n\n 放款通知单 LoanNoteID=" + subInfo.getSubAccountLoanInfo().getLoanNoteID() + " \n\n");
				if (discountInfo != null)
				{
					//合同号  来自 loan_discountcredence
					info.setLoanContractID(discountInfo.getDiscountContractID());
					info.setAL_nLoanNoteID(subInfo.getSubAccountLoanInfo().getLoanNoteID());
					//贴现凭证编号	来自 loan_discountcredence				
					info.setAL_nLoanNoteNo(discountInfo.getTransNo());
					//汇票金额 来自 loan_discountcredence
					info.setAmount(discountInfo.getDiscountBillAmount());
					//核定金额
					info.setDiscountAmount(discountInfo.getDiscountAmount());
					//利息
					info.setAL_mInterestTax(discountInfo.getInterest());	
					//起始日期					
					Sett_TransGrantDiscountDAO dtDao = new Sett_TransGrantDiscountDAO();
					TransGrantDiscountInfo dtInfo = dtDao.findDateInfoByDiscountNoteID(discountInfo.getDiscountContractID());
					info.setAF_dtStart(dtInfo.getInputDate());
					//汇票种类  数据来自 loan_discountcontractbill					
					info.setNDraftTypeID(discountDao.findPaytypeIDByID(subInfo.getSubAccountLoanInfo().getLoanNoteID()));

					//info.setTypeID1(loanInfo.getTypeID1());//地区分类
					//info.setTypeID2(loanInfo.getTypeID2());//行业分类1
					//info.setTypeID3(loanInfo.getTypeID3());//行业分类2
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}

		return info;
	}

	//通过子账户ID查询账户类型
	public long findAccountTypeBySubAccountID(long nSubAccountID) throws Exception
	{
		System.out.println("\n\n 后台,传入的参数为: nSubAccountID=" + nSubAccountID + "\n\n");
		long nAccountTypeID = -1;

		Sett_SubAccountDAO dao = new Sett_SubAccountDAO();
		Sett_AccountDAO accntDao = new Sett_AccountDAO();
		SubAccountAssemblerInfo subInfo = null;
		AccountInfo accountInfo = null;

		try
		{
			subInfo = dao.findByID(nSubAccountID);
			System.out.println("\n\n subInfo=" + subInfo + "\n\n");
			if (subInfo != null)
			{
				long nAccountID = subInfo.getSubAccountFixedInfo().getAccountID();
				System.out.println("\n\n 后台,nAccountID=" + nAccountID + "\n\n");
				accountInfo = accntDao.findByID(nAccountID);
				System.out.println("\n\n 后台,accountInfo=" + accountInfo + "\n\n");
				if (accountInfo != null)
				{
					nAccountTypeID = accountInfo.getAccountTypeID();
					System.out.println("\n\n 后台,nAccountTypeID=" + nAccountTypeID + "\n\n");
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}

		return nAccountTypeID;
	}
}
