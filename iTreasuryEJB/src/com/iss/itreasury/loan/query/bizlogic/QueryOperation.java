/*
 * Created on 2003-10-28
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.loan.query.bizlogic;

import com.iss.itreasury.util.*;
import com.iss.itreasury.loan.util.*;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;
import com.iss.itreasury.loan.query.dataentity.*;
import com.iss.itreasury.loan.query.dao.*;
import com.iss.itreasury.loan.recognizancenotice.dataentity.RecognizanceNoticeInfo;
import com.iss.system.dao.PageLoader;
import com.iss.itreasury.loan.attornmentapply.dataentity.*;
import com.iss.itreasury.loan.credit.dao.*;
import com.iss.itreasury.loan.credit.dataentity.*;
import com.iss.itreasury.loan.leasehold.dataentity.LeaseholdPayNoticeInfo;
import com.iss.itreasury.loan.leasehold.dataentity.LeaseholdRepayNoticeInfo;

/**
 * @author hyzeng
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class QueryOperation
{
	private static Log4j log4j = null;

	public PageLoader queryPayNotice(QuestPayNoticeInfo payNInfo) throws Exception
	{
		PageLoader c = null;
		QueryDao dao = new QueryDao();

		try
		{
			c = dao.queryPayNotice(payNInfo);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return c;
	}
	/**
	 * 查询银团贷款提款通知单
	 * @param drawInfo
	 * @return
	 * @throws Exception
	 */
	public PageLoader queryDrawNotice(QuestLoanDrawNoticeInfo drawInfo) throws Exception
	{
		PageLoader c = null;
		QueryDao dao = new QueryDao();

		try
		{
			c = dao.queryDrawNotice(drawInfo);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return c;
	}

	public String getDiscountCredenceOrder(long orderParam, long desc)
	{
		QueryDao dao = new QueryDao();
		String orderSQL = dao.getDiscountCredenceOrder(orderParam, desc);
		return orderSQL;
	}
	public PageLoader queryDiscountCredence(QuestCredenceInfo cInfo) throws Exception
	{
		PageLoader c = null;
		QueryDao dao = new QueryDao();

		try
		{
			c = dao.queryDiscountCredence(cInfo);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return c;
	}
	
	public PageLoader queryTransDiscountCredence(long lID,long nTypeID) throws Exception
	{
		PageLoader c = null;
		QueryDao dao = new QueryDao();

		try
		{
			c = dao.queryTransDiscountCredence(lID,nTypeID);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return c;
	}
	
	public String getPayNoticeOrder(long orderParam, long desc)
	{
		QueryDao dao = new QueryDao();
		String orderSQL = dao.getPayNoticeOrder(orderParam, desc);
		return orderSQL;
	}
	public String getDrawNoticeOrder(long orderParam, long desc)
	{
		QueryDao dao = new QueryDao();
		String orderSQL = dao.getDrawNoticeOrder(orderParam, desc);
		return orderSQL;
	}
	
	/**
	 * 担保收款通知单查询排序 （上海电气） added by zntan 2004-12-9
	 * @param orderParam
	 * @param desc
	 * @return
	 */
	public String getAssureChargeNoticeOrder(long orderParam, long desc)
	{
		QueryDao dao = new QueryDao();
		String orderSQL = dao.getAssureChargeNoticeOrder(orderParam, desc);
		return orderSQL;
	}
	
	/**
	 * 担保收款通知单查询 （上海电气） added by zntan 2004-12-9
	 * @param lID
	 * @return
	 * @throws Exception
	 */
	public PageLoader queryAssureChargeNotice(QueryAssureNoticeInfo qInfo) throws Exception
	{
		PageLoader c = null;
		QueryDao dao = new QueryDao();

		try
		{
			c = dao.queryAssureChargeNotice(qInfo);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return c;
	}
	
	/**
	 * 担保保后处理通知单查询排序 （上海电气） added by zntan 2004-12-9
	 * @param orderParam
	 * @param desc
	 * @return
	 */
	public String getAssureManagementNoticeOrder(long orderParam, long desc)
	{
		QueryDao dao = new QueryDao();
		String orderSQL = dao.getAssureManagementNoticeOrder(orderParam, desc);
		return orderSQL;
	}
	
	/**
	 * 担保保后处理通知单查询 （上海电气） added by zntan 2004-12-9
	 * @param lID
	 * @return
	 * @throws Exception
	 */
	public PageLoader queryAssureManagementNotice(QueryAssureNoticeInfo qInfo) throws Exception
	{
		PageLoader c = null;
		QueryDao dao = new QueryDao();

		try
		{
			c = dao.queryAssureManagementNotice(qInfo);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return c;
	}

	/**
	 * 执行合同执行情况查询
	 * @param lID 合同标示
	 * @param lParam 排序标志
	 * @param lDesc 升降序标志
	 * @return 
	 * @throws Exception
	 */
	public Collection queryPerform(long lID, long lParam, long lDesc) throws Exception
	{
		Collection c = null;
		QueryDao dao = new QueryDao();

		try
		{
			c = dao.queryPerform(lID, lParam, lDesc);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}

		return c;
	}

	/**
	 * 查询合同执行情况的汇总信息
	 * @param lID 合同ID
	 * @return
	 * @throws Exception
	 */
	public QuerySumInfo queryPerformSum(long lID) throws Exception
	{
		QuerySumInfo sumInfo = null;
		QueryDao dao = new QueryDao();

		try
		{
			sumInfo = dao.queryPerformSum(lID);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}

		return sumInfo;
	}

	public PageLoader queryAttornment(AttornmentQueryInfo qInfo) throws Exception
	{
		PageLoader loader = null;
		QueryDao dao = new QueryDao();

		try
		{
			loader = dao.queryAttornment(qInfo);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return loader;
	}

	public String getAttornmentOrderSQL(long lOrder, long lDesc)
	{
		QueryDao dao = new QueryDao();
		return dao.getAttornmentOrderSQL(lOrder, lDesc);
	}

	/**
	 * 执行贷款申请查询的汇总信息查询
	 * @param qInfo
	 * @return
	 * @throws Exception
	 */
	public QuerySumInfo queryAttornmentSum(AttornmentQueryInfo qInfo) throws Exception
	{
		QuerySumInfo sumInfo = null;
		QueryDao dao = new QueryDao();

		try
		{
			sumInfo = dao.queryAttornmentSum(qInfo);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return sumInfo;

	}

	/**
	 * 执行贷款申请查询（使用PageLoader方式）
	 * @param qInfo
	 * @return
	 * @throws Exception
	 */
	public PageLoader queryLoanApply(QueryLoanApplyInfo qInfo) throws Exception
	{
		PageLoader loader = null;
		QueryDao dao = new QueryDao();

		try
		{
			loader = dao.queryLoanApply(qInfo);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return loader;
	}

	public String getLoanOrderSQL(long lOrder, long lDesc)
	{
		QueryDao dao = new QueryDao();
		return dao.getLoanOrderSQL(lOrder, lDesc);
	}

	/**
	 * 执行贷款申请查询的汇总信息查询
	 * @param qInfo
	 * @return
	 * @throws Exception
	 */
	public QuerySumInfo queryLoanApplySum(QueryLoanApplyInfo qInfo) throws Exception
	{
		QuerySumInfo sumInfo = null;
		QueryDao dao = new QueryDao();

		try
		{
			sumInfo = dao.queryLoanApplySum(qInfo);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return sumInfo;

	}

	/**
	 * 执行合同查询，（使用PageLoader方式）
	 * @param qInfo
	 * @return
	 * @throws Exception
	 */
	public PageLoader queryContract(QuestContractInfo qInfo) throws Exception
	{
		PageLoader loader = null;
		QueryDao dao = new QueryDao();

		try
		{
			loader = dao.queryContract(qInfo);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return loader;
	}
	/**
	 * 执行合同利率查询，（使用PageLoader方式）
	 * @param cInfo
	 * @return
	 * @throws Exception
	 */
	public PageLoader queryContractRate(QueryBatchWillContractInfo cInfo) throws Exception
	{
		PageLoader loader = null;
		QueryBatchWillContractDao dao = new QueryBatchWillContractDao();

		try
		{
			loader = dao.queryContractRate(cInfo);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return loader;
	}
	
	/**
	 * 执行合同利率调整查询，（使用PageLoader方式）
	 * @author 马现福
	 * @param adjustQueryInfo
	 * @return PageLoader
	 * @throws Exception
	 */
	public PageLoader queryContractAdjustInfo(ContractInfoAdjustQuery adjustQueryInfo) throws Exception
	{
		PageLoader loader = null;
		QueryDao dao = new QueryDao();

		try
		{
			loader = dao.queryContractAdjustInfo(adjustQueryInfo);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return loader;
	}

	/**
	 * 
	 * @param lOrder
	 * @param lDesc
	 * @return
	 */
	public String getContractOrderSQL(long lOrder, long lDesc)
	{
		QueryDao dao = new QueryDao();

		return (dao.getContractOrderSQL(lOrder, lDesc));
	}
	
	/**
	 * 点击标题排序方法（放款通知单）
	 * @author 马现福
	 * @param lOrder
	 * @param lDesc
	 * @return
	 */
	public String getPayFormOrderSQL(long lOrder, long lDesc)
	{
		QueryDao dao = new QueryDao();

		return (dao.getPayFormOrderSQL(lOrder, lDesc));
	}
	
	/**
	 * 点击标题排序方法（展期申请）
	 * @param lOrder
	 * @param lDesc
	 * @return
	 */
	public String getExtendOrderSQL(long lOrder, long lDesc)
	{
		QueryDao dao = new QueryDao();

		return (dao.getExtendOrderSQL(lOrder, lDesc));
	}
	
	/**
	 * 执行合同查询的汇总信息查询
	 * @param qInfo
	 * @return
	 * @throws Exception
	 */
	public QuerySumInfo queryContractSum(QuestContractInfo qInfo) throws Exception
	{
		QuerySumInfo sumInfo = null;
		QueryDao dao = new QueryDao();

		try
		{
			sumInfo = dao.QueryContractSum(qInfo);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return sumInfo;

	}
	
	/**
	 * 执行合同凭证查询的汇总信息查询
	 * @param qInfo
	 * @return
	 * @throws Exception
	 */
	public QuerySumInfo queryDiscountCredenceSum(QuestCredenceInfo cInfo) throws Exception
	{
		QuerySumInfo sumInfo = null;
		QueryDao dao = new QueryDao();

		try
		{
			sumInfo = dao.queryDiscountCredenceSum(cInfo);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return sumInfo;

	}
	
	

	/*
	 * 
	 * @author haoning
	 * @time 2003-11-20
	 * @param QueryDao
	 * function
	 */
	public PageLoader queryContractPlan(QuestContractPlanInfo qInfo) throws Exception
	{
		PageLoader loader = null;
		QueryDao dao = new QueryDao();

		try
		{
			loader = dao.queryQuestContractPlanInfo(qInfo);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return loader;
	}

	/**
	 * 执行合同执行计划的查询，此方法只在合同详细信息查看执行计划时使用。
	 * @param qInfo
	 * @return
	 * @throws Exception
	 */
	public Collection queryContractPlanResult(QuestContractPlanInfo qInfo) throws Exception
	{
		QueryDao dao = new QueryDao();
		Collection c = null;

		try
		{
			c = dao.queryContractPlanResult(qInfo);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
		return c;
	}

	/*
	 * 
	 * @author haoning
	 * @time 2003-11-20
	 * @param QuestExtendInfo
	 * function
	 */
	public PageLoader queryExtend(QuestExtendInfo qInfo) throws Exception
	{
		PageLoader loader = null;
		QueryDao dao = new QueryDao();

		try
		{
			loader = dao.queryQuestExtendInfo(qInfo);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return loader;
	}

	
	/*
	 * 
	 * @author haoning
	 * @time 2003-11-20
	 * @param QuestExtendInfo
	 * function
	 */
	public QuerySumInfo queryExtendSum(QuestExtendInfo qInfo) throws Exception
	{
		QuerySumInfo sumInfo = null;
		QueryDao dao = new QueryDao();

		try
		{
			sumInfo = dao.QueryExtendSum(qInfo);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return sumInfo;
	}

	/*
	 * 
	 * @author haoning
	 * @time 2003-11-20
	 * @param QuestExtendInfo
	 * function
	 */
	public QueryMyJobCountInfo queryMyJobCount(long lUserID, long lOfficeID, long lCurrencyID) throws Exception
	{
		long lStatusID = -1;
		QueryMyJobCountInfo CountInfo = new QueryMyJobCountInfo();
		QueryDao dao = new QueryDao();

		try
		{
			//=====================================申请状态===========================//
			Log.print("//============================申请状态==========================//");
			//===================撰写==================//
			Log.print("//-----------------------------撰写----------------------");
			lStatusID = 0;
			{
				//贷款申请
				CountInfo.setLoanApplySaveCount(dao.QueryLoanCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
				/*
				if (Env.getProjectName().equals(Constant.ProjectName.HN) || Env.getProjectName().equals(Constant.ProjectName.CNMEF))
				{
					//贴现申请
					CountInfo.setDiscountApplySaveCount(dao.QueryDiscountApplyCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
					CountInfo.setTransDiscountApplySaveCount(dao.QueryLoanApplyCountByLoanType(lUserID, lStatusID, lOfficeID, lCurrencyID,Constant.ApprovalLoanType.ZTX,LOANConstant.LoanType.ZTX));
					//========ninh 2004-06-25======//        
					//审核贷款转让申请——撰写
					CountInfo.setAttornmentApplySaveCount(
					        dao.QueryAttornmentApplyCount(lUserID,lStatusID,lOfficeID,lCurrencyID)
					        );
				}
				//*/
				//用贷款类型配置项 代替 项目判断 ninh 2004-11-08
					//贴现申请
					CountInfo.setDiscountApplySaveCount(dao.QueryDiscountApplyCount(lUserID, lStatusID, lOfficeID, lCurrencyID));

					CountInfo.setTransDiscountApplySaveCount(dao.QueryLoanApplyCountByLoanType(lUserID, lStatusID, lOfficeID, lCurrencyID,Constant.ApprovalLoanType.ZTX,LOANConstant.LoanType.ZTX));

			}
			//===================已提交================//
			Log.print("//------------------------------已提交--------------------------//");
			lStatusID = 1;
			{
				//贷款申请——已提交
				CountInfo.setLoanApplySubmitCount(dao.QueryLoanCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
				//放款通知单——已提交
				CountInfo.setPaySubmitCount(dao.QueryPayCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
				//逾期处理——已提交
				CountInfo.setOverDueSubmitCount(dao.QueryOverDueCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
				//合同执行计划——已提交
				CountInfo.setContractPlanSubmitCount(dao.QueryContractPlanCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
				//利率调整——已提交
				CountInfo.setRateAdjustSubmitCount(dao.QueryRateAdjustCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
				//提前还款——已提交
				CountInfo.setAheadRePaySubmitCount(dao.QueryAheadRePayCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
				/*
				if (Env.getProjectName().equals(Constant.ProjectName.HN) || Env.getProjectName().equals(Constant.ProjectName.CNMEF)) //大桥没有，华能有的
				{
					//展期申请——已提交
					CountInfo.setExtendApplySubmitCount(dao.QueryExtendApplyCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
					//免还申请——已提交
					CountInfo.setFreeApplySubmitCount(dao.QueryFreeApplyCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
					//贴现申请——已提交
					CountInfo.setDiscountApplySubmitCount(dao.QueryDiscountApplyCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
					//贴现凭证——已提交
					CountInfo.setDiscountCredenceSubmitCount(dao.QueryDiscountCredenceCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
					//转贴现申请——已提交
					CountInfo.setTransDiscountApplySubmitCount(dao.QueryLoanApplyCountByLoanType(lUserID, lStatusID, lOfficeID, lCurrencyID,Constant.ApprovalLoanType.ZTX,LOANConstant.LoanType.ZTX));
					//转贴现凭证申请——已提交
					CountInfo.setTransDiscountCredenceSubmitCount(dao.QueryCredenceCountByCredenceType(lUserID, lStatusID, lOfficeID, lCurrencyID,Constant.ApprovalLoanType.ZTX,LOANConstant.CredenceType.TRANSDISCOUNTCREDENCE));					
					//回购凭证申请——已提交
					CountInfo.setRepurchaseCredenceSubmitCount(dao.QueryCredenceCountByCredenceType(lUserID, lStatusID, lOfficeID, lCurrencyID,Constant.ApprovalLoanType.ZTX,LOANConstant.CredenceType.REPURCHASECREDENCE));					
					//风险状态变更——已提交
					CountInfo.setRiskStatusAdjustSubmitCount(dao.QueryRiskStatusAdjustCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
					//合同状态变更——已提交
					CountInfo.setContractStatusAdjustSubmitCount(dao.QueryContractStatusAdjustCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
					//银团放款通知单——已提交
					CountInfo.setYTPaySubmitCount(dao.QueryYTPayCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
					//银团提款通知单——已提交
					CountInfo.setYTDrawNoticeSubmitCount(dao.QueryDrawNoticeCount(lUserID, lStatusID, lOfficeID, lCurrencyID));

					//========ninh 2004-06-25======//        
					//审核贷款转让申请——已提交
					CountInfo.setAttornmentApplySubmitCount(dao.QueryAttornmentApplyCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
				}//*/
				//用贷款类型配置项 代替 项目判断 ninh 2004-11-08
					//展期申请——已提交
					CountInfo.setExtendApplySubmitCount(dao.QueryExtendApplyCount(lUserID, lStatusID, lOfficeID, lCurrencyID));

					//免还申请——已提交
					CountInfo.setFreeApplySubmitCount(dao.QueryFreeApplyCount(lUserID, lStatusID, lOfficeID, lCurrencyID));

					//贴现申请——已提交
					CountInfo.setDiscountApplySubmitCount(dao.QueryDiscountApplyCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
					//贴现凭证——已提交
					CountInfo.setDiscountCredenceSubmitCount(dao.QueryDiscountCredenceCount(lUserID, lStatusID, lOfficeID, lCurrencyID));

					//转贴现申请——已提交
					CountInfo.setTransDiscountApplySubmitCount(dao.QueryLoanApplyCountByLoanType(lUserID, lStatusID, lOfficeID, lCurrencyID,Constant.ApprovalLoanType.ZTX,LOANConstant.LoanType.ZTX));
					//转贴现凭证申请——已提交
					CountInfo.setTransDiscountCredenceSubmitCount(dao.QueryCredenceCountByCredenceType(lUserID, lStatusID, lOfficeID, lCurrencyID,Constant.ApprovalLoanType.ZTX,LOANConstant.CredenceType.TRANSDISCOUNTCREDENCE));					
					//回购凭证申请——已提交
					CountInfo.setRepurchaseCredenceSubmitCount(dao.QueryCredenceCountByCredenceType(lUserID, lStatusID, lOfficeID, lCurrencyID,Constant.ApprovalLoanType.ZTX,LOANConstant.CredenceType.REPURCHASECREDENCE));					
					//风险状态变更——已提交
					CountInfo.setRiskStatusAdjustSubmitCount(dao.QueryRiskStatusAdjustCount(lUserID, lStatusID, lOfficeID, lCurrencyID));

					//合同状态变更——已提交
					CountInfo.setContractStatusAdjustSubmitCount(dao.QueryContractStatusAdjustCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
					//银团放款通知单——已提交
					CountInfo.setYTPaySubmitCount(dao.QueryYTPayCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
					//银团提款通知单——已提交
					CountInfo.setYTDrawNoticeSubmitCount(dao.QueryDrawNoticeCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
					//审核贷款转让申请——已提交
					CountInfo.setAttornmentApplySubmitCount(dao.QueryAttornmentApplyCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
					//担保收款通知单———已提交
					CountInfo.setAssureChargeNoticeSubmitCount(dao.queryAssureChargeNoticeCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
					//保后处理通知单--已提交
					CountInfo.setAssureManagementNoticeSubmitCount(dao.queryAssureManagementNoticeCount(lUserID, lStatusID, lOfficeID, lCurrencyID));

			}
			//=====================待审核=====================//
			Log.print("//-----------------------------待审核--------------------------");
			lStatusID = 2;
			{
				//贷款申请——待审核
				CountInfo.setLoanApplyForCheckCount(dao.QueryLoanCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
				//放款通知单——待审核
				CountInfo.setPayForCheckCount(dao.QueryPayCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
				//逾期处理——待审核
				CountInfo.setOverDueForCheckCount(dao.QueryOverDueCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
				//合同执行计划——待审核
				CountInfo.setContractPlanForCheckCount(dao.QueryContractPlanCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
				//利率调整——待审核
				CountInfo.setRateAdjustForCheckCount(dao.QueryRateAdjustCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
				//提前还款——待审核
				CountInfo.setAheadRePayForCheckCount(dao.QueryAheadRePayCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
				/*
				if (Env.getProjectName().equals(Constant.ProjectName.HN) || Env.getProjectName().equals(Constant.ProjectName.CNMEF)) //大桥没有，华能有的
				{
					//展期申请——待审核        
					CountInfo.setExtendApplyForCheckCount(dao.QueryExtendApplyCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
					//免还申请——待审核        
					CountInfo.setFreeApplyForCheckCount(dao.QueryFreeApplyCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
					//贴现申请——待审核
					CountInfo.setDiscountApplyForCheckCount(dao.QueryDiscountApplyCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
					//贴现凭证——待审核
					CountInfo.setDiscountCredenceForCheckCount(dao.QueryDiscountCredenceCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
					//转贴现申请——待审核
					CountInfo.setTransDiscountApplyForCheckCount(dao.QueryLoanApplyCountByLoanType(lUserID, lStatusID, lOfficeID, lCurrencyID,Constant.ApprovalLoanType.ZTX,LOANConstant.LoanType.ZTX));
					//转贴现凭证申请——待审核
					CountInfo.setTransDiscountCredenceForCheckCount(dao.QueryCredenceCountByCredenceType(lUserID, lStatusID, lOfficeID, lCurrencyID,Constant.ApprovalLoanType.ZTX,LOANConstant.CredenceType.TRANSDISCOUNTCREDENCE));					
					//回购凭证申请——待审核
					CountInfo.setRepurchaseCredenceForCheckCount(dao.QueryCredenceCountByCredenceType(lUserID, lStatusID, lOfficeID, lCurrencyID,Constant.ApprovalLoanType.ZTX,LOANConstant.CredenceType.REPURCHASECREDENCE));					
					//风险状态变更——待审核
					CountInfo.setRiskStatusAdjustForCheckCount(dao.QueryRiskStatusAdjustCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
					//合同状态变更——待审核
					CountInfo.setContractStatusAdjustForCheckCount(dao.QueryContractStatusAdjustCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
					//银团放款通知单——待审核
					CountInfo.setYTPayForCheckCount(dao.QueryYTPayCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
					//银团提款通知单——待审核
					CountInfo.setYTDrawNoticeForCheckCount(dao.QueryDrawNoticeCount(lUserID, lStatusID, lOfficeID, lCurrencyID));

					//=====ninh 2004-06-25======//        
					//审核贷款转让申请——待审核
					CountInfo.setAttornmentApplyForCheckCount(dao.QueryAttornmentApplyCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
				}
				//*/
				//用贷款类型配置项 代替 项目判断 ninh 2004-11-08
					//展期申请——待审核        
					CountInfo.setExtendApplyForCheckCount(dao.QueryExtendApplyCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
					//免还申请——待审核        
					CountInfo.setFreeApplyForCheckCount(dao.QueryFreeApplyCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
					//贴现申请——待审核
					CountInfo.setDiscountApplyForCheckCount(dao.QueryDiscountApplyCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
					//贴现凭证——待审核
					CountInfo.setDiscountCredenceForCheckCount(dao.QueryDiscountCredenceCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
					//转贴现申请——待审核
					CountInfo.setTransDiscountApplyForCheckCount(dao.QueryLoanApplyCountByLoanType(lUserID, lStatusID, lOfficeID, lCurrencyID,Constant.ApprovalLoanType.ZTX,LOANConstant.LoanType.ZTX));
					//转贴现凭证申请——待审核
					CountInfo.setTransDiscountCredenceForCheckCount(dao.QueryCredenceCountByCredenceType(lUserID, lStatusID, lOfficeID, lCurrencyID,Constant.ApprovalLoanType.ZTX,LOANConstant.CredenceType.TRANSDISCOUNTCREDENCE));					
					//回购凭证申请——待审核
					CountInfo.setRepurchaseCredenceForCheckCount(dao.QueryCredenceCountByCredenceType(lUserID, lStatusID, lOfficeID, lCurrencyID,Constant.ApprovalLoanType.ZTX,LOANConstant.CredenceType.REPURCHASECREDENCE));					
					//风险状态变更——待审核
					CountInfo.setRiskStatusAdjustForCheckCount(dao.QueryRiskStatusAdjustCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
					//合同状态变更——待审核
					CountInfo.setContractStatusAdjustForCheckCount(dao.QueryContractStatusAdjustCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
					//银团放款通知单——待审核
					CountInfo.setYTPayForCheckCount(dao.QueryYTPayCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
					//银团提款通知单——待审核
					CountInfo.setYTDrawNoticeForCheckCount(dao.QueryDrawNoticeCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
					//审核贷款转让申请——待审核
					CountInfo.setAttornmentApplyForCheckCount(dao.QueryAttornmentApplyCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
					//担保收款通知单--待审核
					CountInfo.setAssureChargeNoticeForCheckCount(dao.queryAssureChargeNoticeCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
					//保后处理通知单--待审核
					CountInfo.setAssureManagementNoticeForCheckCount(dao.queryAssureManagementNoticeCount(lUserID, lStatusID, lOfficeID, lCurrencyID));

			}
			//=================================合同状态===============================//
			Log.print("//==============================合同状态===========================//");
			//====================撰写===================//
			Log.print("//-------------------------撰写----------------------//");
			lStatusID = 0;
			{
				//贷款合同——撰写-总数
				CountInfo.setContractApplySaveCount(dao.QueryContractCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
				/*
				if (Env.getProjectName().equals(Constant.ProjectName.HN) || Env.getProjectName().equals(Constant.ProjectName.CNMEF)) //大桥没有，华能有的
				{
					//展期合同——撰写-总数
					CountInfo.setExtendContractSaveCount(dao.QueryExtendContractCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
					//贴现合同——撰写-总数
					CountInfo.setDiscountContractSaveCount(dao.QueryDiscountContractCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
					//转贴现合同——撰写-总数
					CountInfo.setTransDiscountContractSaveCount(dao.QueryContractCountByLoanType(lUserID, lStatusID, lOfficeID, lCurrencyID,Constant.ApprovalLoanType.ZTX,LOANConstant.LoanType.ZTX));
				}
				//*/
					//展期合同——撰写-总数
					CountInfo.setExtendContractSaveCount(dao.QueryExtendContractCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
					//贴现合同——撰写-总数
					CountInfo.setDiscountContractSaveCount(dao.QueryDiscountContractCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
					//转贴现合同——撰写-总数
					CountInfo.setTransDiscountContractSaveCount(dao.QueryContractCountByLoanType(lUserID, lStatusID, lOfficeID, lCurrencyID,Constant.ApprovalLoanType.ZTX,LOANConstant.LoanType.ZTX));
			}
			//===================已提交==================//
			Log.print("//---------------------------已提交---------------------//");
			lStatusID = 1;
			{
				//贷款合同——已提交-总数
				CountInfo.setContractApplySubmitCount(dao.QueryContractCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
				/*
				if (Env.getProjectName().equals(Constant.ProjectName.HN) || Env.getProjectName().equals(Constant.ProjectName.CNMEF)) //大桥没有，华能有的
				{
					//展期合同——已提交-总数
					CountInfo.setExtendContractSubmitCount(dao.QueryExtendContractCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
					//贴现合同——已提交-总数
					CountInfo.setDiscountContractSubmitCount(dao.QueryDiscountContractCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
					//转贴现合同——已提交-总数
					CountInfo.setTransDiscountContractSubmitCount(dao.QueryContractCountByLoanType(lUserID, lStatusID, lOfficeID, lCurrencyID,Constant.ApprovalLoanType.ZTX,LOANConstant.LoanType.ZTX));					
				}
				//*/
					//展期合同——已提交-总数
					CountInfo.setExtendContractSubmitCount(dao.QueryExtendContractCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
					//贴现合同——已提交-总数
					CountInfo.setDiscountContractSubmitCount(dao.QueryDiscountContractCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
					//转贴现合同——已提交-总数
					CountInfo.setTransDiscountContractSubmitCount(dao.QueryContractCountByLoanType(lUserID, lStatusID, lOfficeID, lCurrencyID,Constant.ApprovalLoanType.ZTX,LOANConstant.LoanType.ZTX));					
			}
			//===================待审核==================//
			Log.print("//------------------------待审核-----------------------//");
			lStatusID = 2;
			{
				//贷款合同——待审核-总数
				CountInfo.setContractApplyForCheckCount(dao.QueryContractCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
				/*
				if (Env.getProjectName().equals(Constant.ProjectName.HN) || Env.getProjectName().equals(Constant.ProjectName.CNMEF)) //大桥没有，华能有的
				{
					//展期合同——待审核-总数
					CountInfo.setExtendContractForCheckCount(dao.QueryExtendContractCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
					//贴现合同——待审核-总数
					CountInfo.setDiscountContractForCheckCount(dao.QueryDiscountContractCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
					//转贴现合同——待审核-总数
					CountInfo.setTransDiscountContractForCheckCount(dao.QueryContractCountByLoanType(lUserID, lStatusID, lOfficeID, lCurrencyID,Constant.ApprovalLoanType.ZTX,LOANConstant.LoanType.ZTX));										
				}
				//*/
					//展期合同——待审核-总数
					CountInfo.setExtendContractForCheckCount(dao.QueryExtendContractCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
					//贴现合同——待审核-总数
					CountInfo.setDiscountContractForCheckCount(dao.QueryDiscountContractCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
					//转贴现合同——待审核-总数
					CountInfo.setTransDiscountContractForCheckCount(dao.QueryContractCountByLoanType(lUserID, lStatusID, lOfficeID, lCurrencyID,Constant.ApprovalLoanType.ZTX,LOANConstant.LoanType.ZTX));										
			}
			//===================待激活==================//
			Log.print("//---------------------------待激活---------------------//");
			lStatusID = 3;
			{
				//贷款合同——待激活-总数
				CountInfo.setContractApplyForActiveCount(dao.QueryContractApplyForActiveCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return CountInfo;
	}

	public Collection queryCreditLimitInfo(CreditLimitQueryInfo qInfo) throws Exception
	{
		CreditLimitDAO dao = new CreditLimitDAO();
		return dao.findByMultiOption( qInfo );	
	} 
	
	public double getTheRealAmountByContractID(long lContractID) throws Exception
	{
		double dReturn = 0.0;
		QueryDao dao = new QueryDao();
		
		try
		{
			dReturn = dao.getTheRealAmountByContractID(lContractID);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return dReturn;
	}
	
        /**
         * 融资租赁收款通知单查询排序
         * @param orderParam
         * @param desc
         * @return
         */
        public String getLeaseholdPayNoticeOrder(long orderParam, long desc)
        {
                QueryDao dao = new QueryDao();
                String orderSQL = dao.getLeaseholdPayNoticeOrder(orderParam, desc);
                return orderSQL;
        }

        /**
         * 融资租赁收款通知单查询
         * @param lID
         * @return
         * @throws Exception
         */
        public PageLoader queryLeaseholdPayNotice(LeaseholdPayNoticeInfo qInfo) throws Exception
        {
                PageLoader c = null;
                QueryDao dao = new QueryDao();

                try
                {
                        c = dao.queryLeaseholdPayNotice(qInfo);
                }
                catch (Exception e)
                {
                        e.printStackTrace();
                        throw e;
                }
                return c;
        }
        
        /**
         * 融资租赁保证金保后处理查询
         * @param lID
         * @return
         * @throws Exception
         */
        public PageLoader queryRecognizanceNotice(RecognizanceNoticeInfo rInfo) throws Exception
        {
                PageLoader c = null;
                QueryDao dao = new QueryDao();

                try
                {
                        c = dao.queryRecognizanceNotice(rInfo);
                }
                catch (Exception e)
                {
                        e.printStackTrace();
                        throw e;
                }
                return c;
        }

        /**
         * 融资租赁收款通知单查询排序
         * @param orderParam
         * @param desc
         * @return
         */
        public String getLeaseholdRepayNoticeOrder(long orderParam, long desc)
        {
                QueryDao dao = new QueryDao();
                String orderSQL = dao.getLeaseholdRepayNoticeOrder(orderParam, desc);
                return orderSQL;
        }

        /**
         * 融资租赁收款通知单查询
         * @param lID
         * @return
         * @throws Exception
         */
        public PageLoader queryLeaseholdRepayNotice(LeaseholdRepayNoticeInfo qInfo) throws Exception
        {
                PageLoader c = null;
                QueryDao dao = new QueryDao();

                try
                {
                        c = dao.queryLeaseholdRepayNotice(qInfo);
                }
                catch (Exception e)
                {
                        e.printStackTrace();
                        throw e;
                }
                return c;
        }
        
        //Modify by leiyang date 2007/06/27
    	/**
    	* 查询还款通知单的保存状态或者全部状态
    	* @param ContractQueryInfo 查询条件
    	* @return Collection
    	 * @throws Exception 
    	 * @throws IException 
    	* @exception Exception
    	*/
    	/*public Collection queryRepayNoticeSaveOrAll(QueryRepayNoticeInfo qInfo, boolean bool) throws Exception
    	{
    		Collection c = null;
    		try
    		{
    			QueryDao dao = new QueryDao();
    			c = dao.queryRepayNoticeSaveOrAll(qInfo,bool);
    		}
    		catch (Exception e)
    		{
    			e.printStackTrace();
    			throw e;
    		}
    		return c;
    	}
    	*/
        //Modify by leiyang date 2007/06/27
    	/**
    	* 查询还款通知单的非保存状态
    	* @param ContractQueryInfo 查询条件
    	* @return Collection
    	 * @throws Exception 
    	 * @throws IException 
    	* @exception Exception
    	*/
    	/*public Collection queryRepayNoticeNotSave(QueryRepayNoticeInfo qInfo) throws Exception
    	{
    		Collection c = null;
    		try
    		{
    			QueryDao dao = new QueryDao();
    			c = dao.queryRepayNoticeNotSave(qInfo);
    		}
    		catch (Exception e)
    		{
    			e.printStackTrace();
    			throw e;
    		}
    		return c;
    	}*/
       
      
        /**
         * 查询还款通知单 
         * @param repayInfo
         * @return
         * @throws Exception
         */
        public PageLoader queryRepayNotice(QuestRepayNoticeInfo repayInfo) throws Exception{
        	PageLoader c = null;
        	
        	QueryDao dao = new QueryDao();

            try
            {
                    c = dao.queryRepayNotice(repayInfo);
            }
            catch (Exception e)
            {
                    e.printStackTrace();
                    throw e;
            }
        	
        	return c;
        }
        
        
        /**
         * 还款通知单根据表头排序
         * @param orderParam
         * @param desc
         * @return
         */
        public String getLoanRepayNoticeOrder(long orderParam, long desc)
        {
                QueryDao dao = new QueryDao();
                String orderSQL = dao.getLoanRepayNoticeOrder(orderParam, desc);
                return orderSQL;
        }
       
        
        /**
         * 查询还款通知单各项总额
         * @param 
         */
      
        public QuerySumInfo queryRepayNoticeSum(QuestRepayNoticeInfo repayInfo)throws Exception{
     	   
     	   QuerySumInfo sumInfo = null;
     		QueryDao dao = new QueryDao();

     		try
     		{
     			sumInfo = dao.queryRepayNoticeSum(repayInfo);
     		}
     		catch (Exception e)
     		{
     			e.printStackTrace();
     			throw e;
     		}
     		return sumInfo;
     	   
        }  
        
        
        
  /**
   *
   * 查询放款通知单合同总额和放款总额
   */
   public QuerySumInfo queryLoanPayNoticeSum (QuestPayNoticeInfo payNoticeInfo) throws Exception {
	   
	   
	   QuerySumInfo sumInfo = null;
		QueryDao dao = new QueryDao();

		try
		{
			sumInfo = dao.queryLoanPayNoticeSum(payNoticeInfo);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return sumInfo;
	   
   } 
   /**
    * 查询银团贷款总额
    * @param drawInfo
    * @return
    * @throws Exception
    */
   public QuerySumInfo queryYTDrawNoticeSum(QuestLoanDrawNoticeInfo drawInfo)throws Exception{
	   
	   QuerySumInfo sumInfo = null;
		QueryDao dao = new QueryDao();

		try
		{
			sumInfo = dao.queryYTDrawNoticeSum(drawInfo);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return sumInfo;
	   
   }  
   
   /**
    * 业务汇总查询 
    * @param repayInfo
    * @return
    * @throws Exception
    */
   public PageLoader queryAllBusinesses(QuestContractInfo allInfo) throws Exception{
   	PageLoader c = null;
   	
   	QueryDao dao = new QueryDao();

       try
       {
               c = dao.queryAllBusinesses(allInfo);
       }
       catch (Exception e)
       {
               e.printStackTrace();
               throw e;
       }
   	
   	return c;
   }
   
   
   /**
    * 业务汇总根据表头排序
    * @param orderParam
    * @param desc
    * @return
    */
   public String getAllBusinessesOrder(long orderParam, long desc)
   {
           QueryDao dao = new QueryDao();
           String orderSQL = dao.getAllBusinessesOrder(orderParam, desc);
           return orderSQL;
   }
  
   
  
   /**
    * 业务汇总各种总额
    * @param allInfo
    * @return
    * @throws Exception
    */
   public QuerySumInfo queryAllBusinessesSum(QuestContractInfo allInfo )throws Exception{
	   
	   QuerySumInfo sumInfo = null;
		QueryDao dao = new QueryDao();

		try
		{
			sumInfo = dao.queryAllBusinessesSum(allInfo);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return sumInfo;
	   
   }  
   
   
   
   
   
   
   
	public static void main(String[] args)
	{
		try
		{
			QueryOperation op = new QueryOperation();
			QueryMyJobCountInfo info = new QueryMyJobCountInfo();
			info = op.queryMyJobCount(76, 1, 1);
			Log.print("---------------kkk = "+info.getExtendApplySubmitCount().length);
		}
		catch (Exception e)
		{
			System.out.println(e.toString());
		}
	}
	
//	放款单逾期转表外查询
	public PageLoader queryPayform_Overdue(QuestPayNoticeInfo payNInfo) throws Exception
	{
		PageLoader c = null;
		QueryDao dao = new QueryDao();

		try
		{
			c = dao.queryPayform_Overdue(payNInfo);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return c;
}
//转表外、撤销操作	
	 public long doOffBalance(long ID,long UserID,String strAction) throws RemoteException, IRollbackException,Exception
	    {
	        long lReturn = -1;
	        try{
	        	QueryDao dao = new QueryDao();
		        if(strAction.equals("doOffbanlance"))
                { 	
		        	lReturn=dao.updateStatus(ID, Constant.offBalance.YES, UserID);
                }
                else
                {
                	lReturn=dao.updateStatus(ID,Constant.offBalance.NO, UserID);
                }
	        }
	    	catch (IException ie)
			{
				throw ie;
			}
			catch (Exception e)
			{
				throw new IException("Gen_E001");
			}
	        return lReturn;

	    }
	//坏账记录查询,将结果集保存到SETT_PAYFORM_OVERDUE表中
	 public void findOffbalanceInfo(long office,long currency,String tsInterestStart) throws Exception
		{
		    QueryDao dao = new QueryDao();
		    QueryPayformOverdue info =null;
			Collection vReturn = new ArrayList();
			try {
				vReturn=dao.queryOffBalanceInfo(office,currency,tsInterestStart);
				if (vReturn != null && vReturn.size() > 0) 
				{
					info = new QueryPayformOverdue();
					Iterator it=vReturn.iterator();
					
					while(it.hasNext())
					{
						info=(QueryPayformOverdue)it.next();
						if(dao.isRepeat(info))
						{
							dao.updateOffBalance(info);
						}
						else
						{
							dao.addOffbalance(info);
						}
					}
				}
			} catch (Exception e) {
				throw new IException("导入坏账失败,"+e.getMessage());
				
			}
			
		}

}