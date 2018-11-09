package com.iss.itreasury.craftbrother.apply.dao;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import com.iss.itreasury.craftbrother.util.CRANameRef;
import com.iss.itreasury.craftbrother.util.CRAconstant;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.loan.base.LoanDAOException;
import com.iss.itreasury.loan.base.LoanException;
import com.iss.itreasury.loan.transdiscountapply.dataentity.TransDiscountApplyInfo;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.securities.apply.dao.SEC_BondTypeDAO;
import com.iss.itreasury.securities.apply.dataentity.ApplyInfo;
import com.iss.itreasury.securities.apply.dataentity.ApplyQueryInfo;
import com.iss.itreasury.securities.apply.dataentity.BondTypeInfo;
import com.iss.itreasury.securities.deliveryorderservice.bizlogic.DeliveryOrderServiceBiz;
import com.iss.itreasury.securities.deliveryorderservice.dataentity.UsableCreditLineOfSecuritiesInfo;
import com.iss.itreasury.securities.exception.SecuritiesDAOException;
import com.iss.itreasury.securities.securitiescontract.dao.ContractBondTypeDao;
import com.iss.itreasury.securities.securitiescontract.dao.SecuritiesContractDao;
import com.iss.itreasury.securities.securitiescontract.dataentity.ContractBondTypeInfo;
import com.iss.itreasury.securities.securitiescontract.dataentity.SecuritiesContractInfo;
import com.iss.itreasury.securities.securitiescontractplan.dao.SecuritiesContractPlanDao;
import com.iss.itreasury.securities.securitiescontractplan.dataentity.SecuritiesContractPlanVersionInfo;
import com.iss.itreasury.securities.util.NameRef;
import com.iss.itreasury.securities.util.SECConstant;
import com.iss.itreasury.securities.util.SecuritiesDAO;
import com.iss.itreasury.system.approval.dao.ApprovalDao;
import com.iss.itreasury.system.approval.dataentity.ApprovalSettingInfo;
import com.iss.itreasury.system.approval.dataentity.ApprovalTracingInfo;
import com.iss.itreasury.system.approval.dataentity.ApprovalUserInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.util.Log4j;
public class SEC_ApplyDAO extends SecuritiesDAO
{
	protected Log4j log4j = new Log4j(Constant.ModuleType.CRAFTBROTHER, this);
	public SEC_ApplyDAO()
	{
		super("SEC_ApplyForm");
	}
	/*
	 * 
	 */
	public Collection findByMultiOption(ApplyQueryInfo qInfo) throws SecuritiesDAOException
	{
		String strSelect = "";
		String strSQL = "";
		String strSQL1 = "";
		Vector v = new Vector();
		long transactionTypeID = qInfo.getTransactionTypeId();
		long clientID = qInfo.getClientId();
		long counterpartID = qInfo.getCounterpartId();
		double startAmount = qInfo.getStartAmount();
		double endAmount = qInfo.getEndAmount();
		double startPledgeSecuritiesAmount = qInfo.getStartPledgeSecuritiesAmount();
		double endPledgeSecuritiesAmount = qInfo.getEndPledgeSecuritiesAmount();
		Timestamp startDate = qInfo.getStartDate();
		Timestamp endDate = qInfo.getEndDate();
		long statusID = qInfo.getStatusId();
		long userID = qInfo.getUserId();
		String strUser = qInfo.getStrUser();
		long accountID = qInfo.getAccountId(); //资金帐号
		long securitiesID = qInfo.getSecuritiesId(); //证券代码
		double startPrice = qInfo.getStartPrice(); //成交价格
		double endPrice = qInfo.getEndPrice();
		double startQuantity = qInfo.getStartQuantity(); //成交数量
		double endQuantity = qInfo.getEndQuantity();
		double startStockQuantity = qInfo.getStartStockQuantity(); //转换份额
		double endStockQuantity = qInfo.getEndStockQuantity();
		long queryPurpose = qInfo.getQueryPurpose();
		String startCode = qInfo.getStartCode();
		String endCode = qInfo.getEndCode();
		Timestamp startTransactionStartDate = qInfo.getStartTransactionStartDate();
		Timestamp endTransactionStartDate = qInfo.getEndTransactionStartDate();
		Timestamp startTransactionEndDate = qInfo.getStartTransactionEndDate();
		Timestamp endTransactionEndDate = qInfo.getEndTransactionEndDate();
		long pageLineCount = qInfo.getPageLineCount();
		long pageNo = qInfo.getPageNo();
		long orderParam = qInfo.getOrderParam();
		long desc = qInfo.getDesc();
		String orderParamString = qInfo.getOrderParamString();
		long lRecordCount = -1;
		long lPageCount = -1;
		long lRowNumStart = -1;
		long lRowNumEnd = -1;
		long pageInfo[] = new long[2];
		long term = qInfo.getTerm();
		long settlementTypeID = qInfo.getSettlementTypeID();
		long interestTypeID = qInfo.getInterestTypeID();
		long currencyID = qInfo.getCurrencyId();
		
		/*boxu add 2006-09-19*/
		double startcostAmount = 0;  //回购成本开始
		double endcostAmount = 0;  //回购成本结束
		long startterm = -1;  //开始期限
		long endterm = -1;  //结束期限
		double startincomeRate = 0;  //开始回购收益率
		double endincomeRate = 0;  //结束回购收益率
		startcostAmount = qInfo.getStartcostAmount();  //回购成本开始
		endcostAmount = qInfo.getEndcostAmount();  //回购成本结束
		startincomeRate = qInfo.getStartincomeRate();  //开始回购收益率
		endincomeRate = qInfo.getEndincomeRate();  //结束回购收益率
		startterm = qInfo.getStartterm();  //回购期限开始
		endterm = qInfo.getEndterm();  //回购期限结束
		
		try
		{
			initDAO();
		}
		catch (ITreasuryDAOException e)
		{
			throw new SecuritiesDAOException(e);
		}
		//计算记录总数				
		if (queryPurpose == 1) //for modify
		{
			strSQL = "";
			strSelect = " select count(*) ";
			strSQL =
				" from SEC_ApplyForm aa "
					+ " where 1=1 "
					+ " and StatusID >= "
					+ SECConstant.ApplyFormStatus.SUBMITED
					+ " and StatusID <= "
					+ SECConstant.ApplyFormStatus.CHECKED
					+ " and TransactionTypeID = "
					+ transactionTypeID
					+ " and InputUserID = "
					+ userID;
			//////////////////////查询条件////////////////////////////////////////////////////
			if (transactionTypeID > 0 && transactionTypeID < 1000)
			{
				strSQL1 = " select ID from SEC_TransactionType t where StatusID = 3 and IsNeedApplyForm = 1 and BusinessTypeID = " + transactionTypeID;
				strSQL += " and transactionTypeID in (" + NameRef.getNamesByID(strSQL1, "ID") + ") ";
			}
			else
				if (transactionTypeID > 1000)
				{
					strSQL += " and transactionTypeID = " + transactionTypeID;
				}
			if (statusID == SECConstant.ApplyFormStatus.SUBMITED)
			{
				strSQL += " and nextCheckLevel = 1 ";
				strSQL += " and StatusID = " + SECConstant.ApplyFormStatus.SUBMITED;
			}
			else if ( statusID == SECConstant.ApplyFormStatus.CHECKED )
			{
				strSQL += " and StatusID = " + SECConstant.ApplyFormStatus.CHECKED;
			}
			else
			{
				strSQL += " and StatusID in ( "+SECConstant.ApplyFormStatus.SUBMITED+", "+SECConstant.ApplyFormStatus.CHECKED+" ) ";
			}
		}
		else
			if (queryPurpose == 2) //for examine
			{
				strSelect = " select count(*) ";
				strSQL = " from SEC_ApplyForm aa " + " where 1=1 " + " and NextCheckUserID in " + strUser + " and TransactionTypeID = " + transactionTypeID;
				if (statusID == SECConstant.ApplyFormStatus.SUBMITED)
				{
					strSQL += " and StatusID = " + SECConstant.ApplyFormStatus.SUBMITED;
				}
				else
				{
					strSQL += " and StatusID = " + SECConstant.ApplyFormStatus.SUBMITED;
				}
			}
			else
				if (queryPurpose == 3) //for attornment query
				{
					strSelect = " select count(*) ";
					strSQL = " from SEC_ApplyForm aa " + " where 1=1 " + " and TransactionTypeID = " + transactionTypeID + " and StatusID = " + SECConstant.ApplyFormStatus.CHECKED;
				}
			else if (queryPurpose == 4) //申请书权限转移-申请书查找
			{
				strSelect = " select count(*) ";
				strSQL = " from SEC_ApplyForm aa " + " where 1=1 " ;
				
				if (qInfo.getStatusId()>0)
				{
					strSQL += " and StatusID =" + qInfo.getStatusId();
				}
				if (qInfo.getTransactionTypeId() > 0)
				{
					strSQL += " and transactionTypeID=" + qInfo.getTransactionTypeId();
				}
				if (qInfo.getInputUserId() > 0)
				{
					strSQL += " and inputUserID=" + qInfo.getInputUserId(); 
				}
								
			}
            else if ( queryPurpose == 5 )
			{
				strSelect = " select count(*) ";
				strSQL = " from SEC_ApplyForm aa " + " where 1=1 ";
			}
		if (clientID != -1)
		{
			strSQL += " and ClientID = " + clientID;
		}
		if (counterpartID != -1)
		{
			strSQL += " and CounterpartID = " + counterpartID;
		}
		if (startAmount != 0)
		{
			strSQL += " and Amount >= " + startAmount;
		}
		if (endAmount != 0)
		{
			strSQL += " and Amount <= " + endAmount;
		}
		if (startPledgeSecuritiesAmount != 0)
		{
			strSQL += " and PledgeSecuritiesAmount >= " + startPledgeSecuritiesAmount;
		}
		if (endPledgeSecuritiesAmount != 0)
		{
			strSQL += " and PledgeSecuritiesAmount <= " + endPledgeSecuritiesAmount;
		}
		if (currencyID > 0)
		{
			strSQL += " and currencyID = " + currencyID;
		}
		if (startDate != null)
		{
			strSQL += " and to_char(InputDate,'yyyy-mm-dd') >= '" + DataFormat.getDateString(startDate) + "'";
		}
		if (endDate != null)
		{
			strSQL += " and to_char(InputDate,'yyyy-mm-dd') <= '" + DataFormat.getDateString(endDate) + "'";
		}
		if (counterpartID != -1)
		{
			strSQL += " and CounterpartID = " + counterpartID;
		}
		if (accountID != -1)
		{
			strSQL += " and AccountID = " + accountID;
		}
		if (securitiesID != -1)
		{
			strSQL += " and SecuritiesID = " + securitiesID;
		}
		if (startPrice != 0)
		{
			strSQL += " and Price >= " + startPrice;
		}
		if (endPrice != 0)
		{
			strSQL += " and Price <= " + endPrice;
		}
		if (startQuantity != 0)
		{
			strSQL += " and Quantity >= " + startQuantity;
		}
		if (endQuantity != 0)
		{
			strSQL += " and Quantity <= " + endQuantity;
		}
		if (startStockQuantity != 0)
		{
			strSQL += " and StockQuantity >= " + startStockQuantity;
		}
		if (endStockQuantity != 0)
		{
			strSQL += " and StockQuantity <= " + endStockQuantity;
		}
		if (startCode != "" && startCode.length() > 0)
		{
			strSQL += " and Code >= '" + startCode + "'";
		}
		if (endCode != "" && endCode.length() > 0)
		{
			strSQL += " and Code <= '" + endCode + "'";
		}
		if (startTransactionStartDate != null)
		{
			strSQL += " and to_char(TransactionStartDate,'yyyy-mm-dd') >= '" + DataFormat.getDateString(startTransactionStartDate) + "'";
		}
		if (endTransactionStartDate != null)
		{
			strSQL += " and to_char(TransactionStartDate,'yyyy-mm-dd') <= '" + DataFormat.getDateString(endTransactionStartDate) + "'";
		}
		if (startTransactionEndDate != null)
		{
			strSQL += " and to_char(TransactionEndDate,'yyyy-mm-dd') >= '" + DataFormat.getDateString(startTransactionEndDate) + "'";
		}
		if (endTransactionEndDate != null)
		{
			strSQL += " and to_char(TransactionEndDate,'yyyy-mm-dd') <= '" + DataFormat.getDateString(endTransactionEndDate) + "'";
		}
		if (term > 0)
		{
			strSQL += " and term = " + term;
		}
		if (settlementTypeID > 0)
		{
			strSQL += " and settlementTypeID=" + settlementTypeID;
		}
		if (interestTypeID > 0)
		{
			strSQL += " and interestTypeID=" + interestTypeID;
		}
		
		/*
		boxu add 2006-09-19
		double startcostAmount = 0;  //回购成本开始
		double endcostAmount = 0;  //回购成本结束
		long startterm = -1;  //开始期限
		long endterm = -1;  //结束期限
		double startincomeRate = 0;  //开始回购收益率
		double endincomeRate = 0;  //结束回购收益率
		*/
		if (startcostAmount != 0)
		{
			strSQL += " and costAmount >= " + startcostAmount;
		}
		if (endcostAmount != 0)
		{
			strSQL += " and costAmount <= " + endcostAmount;
		}
		if (startincomeRate > 0)
		{
			strSQL += " and incomeRate >= " + startincomeRate;
		}
		if (endincomeRate > 0)
		{
			strSQL += " and incomeRate <= " + endincomeRate;
		}
		if (startterm > 0)
		{
			strSQL += " and term >= " + startterm;
		}
		if (endterm > 0)
		{
			strSQL += " and term <= " + endterm;
		}
		////////////////////////////排序处理//////////////////////////////////////////////////////////////////////
		int nIndex = 0;
		nIndex = orderParamString.indexOf(".");
		if (nIndex > 0)
		{
			if (orderParamString.substring(0, nIndex).toLowerCase().equals("sec_applyform"))
			{
				strSQL += " order by aa." + orderParamString.substring(nIndex + 1);
			}
		}
		else
		{
			strSQL += " order by aa.ID";
		}
		if (desc == Constant.PageControl.CODE_ASCORDESC_DESC)
		{
			strSQL += " desc";
		}
		log4j.debug(strSelect + strSQL);
		try
		{
			prepareStatement(strSelect + strSQL);
			ResultSet rs = executeQuery();
			if (rs != null && rs.next())
			{
				lRecordCount = rs.getLong(1);
			}
		}
		catch (ITreasuryDAOException e)
		{
			throw new SecuritiesDAOException("批量查询申请书笔数产生错误", e);
		}
		catch (SQLException e)
		{
			throw new SecuritiesDAOException("批量查询申请书笔数产生错误", e);
		}
		lPageCount = lRecordCount / pageLineCount;
		if ((lRecordCount % pageLineCount) != 0)
		{
			lPageCount++;
		}
		pageInfo[0] = lRecordCount;
		pageInfo[1] = lPageCount;
		//v.add(pageInfo);
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		//返回需求的结果集
		lRowNumStart = (pageNo - 1) * pageLineCount + 1;
		lRowNumEnd = lRowNumStart + pageLineCount - 1;
		strSQL = " select * from ( select aa.*,rownum r from ( select * " + strSQL;
		strSQL += " ) aa ) where r between " + lRowNumStart + " and " + lRowNumEnd;
		log4j.debug("xxxxxxxxxxxxxxxxbbbbbbbbbbbbbbbbbbbbb"+strSQL);
		try
		{
			prepareStatement(strSQL);
			ResultSet rs1 = executeQuery();
			long rep = -1;
			while (rs1 != null && rs1.next())
			{
				rep = rs1.getLong("id");
				if (queryPurpose == 3) //for attornment query
				{
					boolean check = checkAttornment(rep);
					log.print("_________" + check);
					if (!check)
					{
						continue;
					}
				}
				ApplyInfo applyInfo = new ApplyInfo();
				//applyInfo = (ApplyInfo) getDataEntityFromResultSet(applyInfo.getClass());
				applyInfo.setId(rep);
				applyInfo.setOfficeId(rs1.getLong("officeId")); //办事处
				applyInfo.setCurrencyId(rs1.getLong("currencyId")); //币种
				applyInfo.setCode(rs1.getString("code")); //申请书编号
				applyInfo.setTransactionTypeId(rs1.getLong("transactionTypeId")); //交易类型
				applyInfo.setTransactionStartDate(rs1.getTimestamp("transactionStartDate")); //成交日期开始日
				applyInfo.setTransactionEndDate(rs1.getTimestamp("transactionEndDate")); //成交日期截止日
				applyInfo.setClientId(rs1.getLong("clientId")); //业务单位
				applyInfo.setCounterpartId(rs1.getLong("counterpartId")); //交易对手[债券分销商/基金管理公司]
				applyInfo.setAccountId(rs1.getLong("accountId")); //交易帐号[资金帐号]
				applyInfo.setSecuritiesId(rs1.getLong("securitiesId")); //证券代码ID[抵押债券代码ID/可转债代码ID]
				applyInfo.setAmount(rs1.getDouble("amount")); //拆借金额[全价/认购金额/申购金额]
				applyInfo.setPledgeSecuritiesAmount(rs1.getDouble("pledgeSecuritiesAmount")); //券面总额
				applyInfo.setPrice(rs1.getDouble("price")); //成交价格[转股价格]
				applyInfo.setQuantity(rs1.getDouble("quantity")); //申购数量[成交数量/赎回份额/转股的债券数量]
				applyInfo.setConvertRate(rs1.getDouble("convertRate")); //折算比例[预付定金比例]
				applyInfo.setStockQuantity(rs1.getDouble("stockQuantity")); //转成股票数量
				applyInfo.setCommissionCharge(rs1.getDouble("commissionCharge")); //预计手续费返还
				applyInfo.setLiquidateRate(rs1.getLong("liquidateRate")); //清算速度
				applyInfo.setBidTypeId(rs1.getLong("bidTypeId")); //投标方式
				applyInfo.setTerm(rs1.getLong("term")); //拆借期限[回购期限]
				applyInfo.setTermTypeId(rs1.getLong("termTypeId")); //期限类型
				applyInfo.setRate(rs1.getDouble("rate")); //拆借利率[回购利率]
				applyInfo.setStockId(rs1.getLong("stockId")); //转成股票代码ID
				applyInfo.setRemark(rs1.getString("remark")); //备注
				applyInfo.setNextCheckUserId(rs1.getLong("nextCheckUserId")); //下一级审核人
				applyInfo.setNextCheckLevel(rs1.getLong("nextCheckLevel")); //下一级审核级别
				applyInfo.setInputUserId(rs1.getLong("inputUserId")); //录入人
				applyInfo.setInputDate(rs1.getTimestamp("inputDate")); //录入时间
				applyInfo.setUpdateUserId(rs1.getLong("updateUserId")); //修改人
				applyInfo.setUpdateDate(rs1.getTimestamp("updateDate")); //修改时间
				applyInfo.setStatusId(rs1.getLong("statusId")); //状态
				applyInfo.setTimestamp(rs1.getTimestamp("timestamp")); //实践戳
				applyInfo.setCommissionChargeRate(rs1.getDouble("commissionChargeRate")); //手续费率
				applyInfo.setSettlementTypeID(rs1.getLong("settlementTypeID")); //结息方式ID
				applyInfo.setInterestTypeID(rs1.getLong("interestTypeID")); //计息方式ID
				applyInfo.setMaturitySource(rs1.getString("maturitySource")); //还款资金来源
				applyInfo.setStartRate(rs1.getDouble("startRate")); //常用记息方式：期初
				applyInfo.setChangeRate(rs1.getDouble("changeRate")); //常用记息方式：变动数
				applyInfo.setIsLowLevel(rs1.getLong("IsLowLevel")); //是否最低审核级别
				//SEC_ApplyForm表中没有的字段
				applyInfo.setClientName(NameRef.getClientNameByID(rs1.getLong("clientId"))); //业务单位
				applyInfo.setCounterpartName(NameRef.getCounterpartNameByID(rs1.getLong("counterpartId"))); //交易对手
				applyInfo.setSecuritiesName(NameRef.getSecuritiesNameByID(rs1.getLong("securitiesId"))); //证券名称
				applyInfo.setAccountNo(NameRef.getAccountNobyAccountID(rs1.getLong("accountId"))); //资金账户代码
				applyInfo.setAccountName(NameRef.getAccountNameById(rs1.getLong("accountId"))); //资金账户名称
				applyInfo.setStockHolderAccountCode(NameRef.getStockHolderAccountCodeByAccountId(rs1.getLong("accountId"))); //股东账户代码
				applyInfo.setStockName(NameRef.getSecuritiesNameByID(rs1.getLong("stockId"))); //转成股票名称
				/*qqgd add this			*/
				applyInfo.setBondName(rs1.getString("bondName"));
				applyInfo.setBondScale(this.getBondScale(applyInfo.getId()));
				/* end of qqgd's adding */
				applyInfo.setRecordCount(lRecordCount); //记录数
				applyInfo.setPageCount(lPageCount); //页数
				
				//boxu add 2006-09-19
				applyInfo.setCostAmount(rs1.getDouble("costAmount"));  //回购成本
				applyInfo.setIncomeRate(rs1.getDouble("incomeRate"));  //回购收益率
				
				v.add(applyInfo);
			}
			//设置审核级别是否相同
			//说明：只有从开始出现与上一记录审核级别不一致的记录开始设置审核级别是否相同，默认为相同
			//added by gqzhang
			boolean IsSameCheckLevel = true;
			long lTag = -1;
			Vector vctTemp = new Vector();
			if (v != null && v.size() > 0)
			{
				Log.print("===========开始设置审核级别是否相同===============");
				Log.print("===========V size():" + v.size());
				for (int i = 0; i < v.size(); i++)
				{
					ApplyInfo applyInfo = (ApplyInfo) v.elementAt(i);
					if (applyInfo != null)
					{
						if (i == 0)
						{
							lTag = applyInfo.getIsLowLevel();
							Log.print("===========" + i + ":" + lTag);
							Log.print("===========标记" + i + ":" + IsSameCheckLevel);
						}
						else if (IsSameCheckLevel == true)
						{
							if (applyInfo.getIsLowLevel() != lTag)
							{
								Log.print("===========" + i + ":" + applyInfo.getIsLowLevel());
								IsSameCheckLevel = false;
								Log.print("===========标记" + i + ":" + IsSameCheckLevel);
							}
						}
						if (IsSameCheckLevel == false)
						{
							applyInfo.setIsSameCheckLevel(Constant.YesOrNo.NO);
						}
						Log.print("===========最后标记" + i + ":" + IsSameCheckLevel);
						vctTemp.add(applyInfo);
					}
				}
				Log.print("===========vctTemp size():" + vctTemp.size());
				v = vctTemp;
			}
		}
		catch (ITreasuryDAOException e)
		{
			throw new SecuritiesDAOException("批量查询申请书产生错误", e);
		}
		catch (SQLException e)
		{
			throw new SecuritiesDAOException("批量查询申请书产生错误", e);
		}
		try
		{
			finalizeDAO();
		}
		catch (ITreasuryDAOException e)
		{
			throw new SecuritiesDAOException(e);
		}
		return (v.size() > 0 ? v : null);
	}
	
	/*
	 * boxu add 2006-11-30
	 * 加入金额审批效验
	 */
	public Collection findByMultiOption1(ApplyQueryInfo qInfo,long ActionID) throws SecuritiesDAOException
	{
		String sql="";
		
		/*SUBLOANTYPEID----ACTIONID*/
		long lSubLoanTypeID = -1;
		long lActionID = -1;
		
		Vector v = new Vector();
		long transactionTypeID = qInfo.getTransactionTypeId();
		
		/*证卷操作*/
		if ( ActionID == 1 )
		{
			long[] result=NameRef.getLoantypidAndActionidByTransactionid(ActionID,transactionTypeID);  //申请
			lSubLoanTypeID=result[0];
			lActionID=result[1];
		}
		else if ( ActionID == 2 )
		{
			long[] result=NameRef.getLoantypidAndActionidByTransactionid(ActionID,transactionTypeID);  //业务
			lSubLoanTypeID=result[0];
			lActionID=result[1];
		}
		
		System.out.println("lSubLoanTypeID=="+lSubLoanTypeID+"==lActionID=="+lActionID);
		
		long clientID = qInfo.getClientId();
		long counterpartID = qInfo.getCounterpartId();
		double startAmount = qInfo.getStartAmount();
		double endAmount = qInfo.getEndAmount();
		double startPledgeSecuritiesAmount = qInfo.getStartPledgeSecuritiesAmount();
		double endPledgeSecuritiesAmount = qInfo.getEndPledgeSecuritiesAmount();
		Timestamp startDate = qInfo.getStartDate();
		Timestamp endDate = qInfo.getEndDate();
		long statusID = qInfo.getStatusId();
		long userID = qInfo.getUserId();
		String strUser = qInfo.getStrUser();
		long accountID = qInfo.getAccountId(); //资金帐号
		long securitiesID = qInfo.getSecuritiesId(); //证券代码
		double startPrice = qInfo.getStartPrice(); //成交价格
		double endPrice = qInfo.getEndPrice();
		double startQuantity = qInfo.getStartQuantity(); //成交数量
		double endQuantity = qInfo.getEndQuantity();
		double startStockQuantity = qInfo.getStartStockQuantity(); //转换份额
		double endStockQuantity = qInfo.getEndStockQuantity();
		long queryPurpose = qInfo.getQueryPurpose();
		String startCode = qInfo.getStartCode();
		String endCode = qInfo.getEndCode();
		Timestamp startTransactionStartDate = qInfo.getStartTransactionStartDate();
		Timestamp endTransactionStartDate = qInfo.getEndTransactionStartDate();
		Timestamp startTransactionEndDate = qInfo.getStartTransactionEndDate();
		Timestamp endTransactionEndDate = qInfo.getEndTransactionEndDate();
		long pageLineCount = qInfo.getPageLineCount();
		long pageNo = qInfo.getPageNo();
		long orderParam = qInfo.getOrderParam();
		long desc = qInfo.getDesc();
		String orderParamString = qInfo.getOrderParamString();
		long lRecordCount = -1;
		long lPageCount = -1;
		long lRowNumStart = -1;
		long lRowNumEnd = -1;
		long pageInfo[] = new long[2];
		long term = qInfo.getTerm();
		long settlementTypeID = qInfo.getSettlementTypeID();
		long interestTypeID = qInfo.getInterestTypeID();
		long currencyID = qInfo.getCurrencyId();
		
		/*boxu add 2006-09-19*/
		double startcostAmount = 0;  //回购成本开始
		double endcostAmount = 0;  //回购成本结束
		long startterm = -1;  //开始期限
		long endterm = -1;  //结束期限
		double startincomeRate = 0;  //开始回购收益率
		double endincomeRate = 0;  //结束回购收益率
		startcostAmount = qInfo.getStartcostAmount();  //回购成本开始
		endcostAmount = qInfo.getEndcostAmount();  //回购成本结束
		startincomeRate = qInfo.getStartincomeRate();  //开始回购收益率
		endincomeRate = qInfo.getEndincomeRate();  //结束回购收益率
		startterm = qInfo.getStartterm();  //回购期限开始
		endterm = qInfo.getEndterm();  //回购期限结束
		
		try
		{
			initDAO();
		}
		catch (ITreasuryDAOException e)
		{
			throw new SecuritiesDAOException(e);
		}
		try
		{
			sql="(SELECT c.*,-1 moneysegment,-1 approvalid from SEC_ApplyForm c";
			sql+=" ,(select a.NAPPROVALCONTENTID from loan_approvaltracing a,(select NAPPROVALCONTENTID,max(ID) as ID from loan_approvaltracing group by NAPPROVALCONTENTID) b";
			sql+=" where (a.NNEXTUSERID="+userID+" ";
			
			//修改
			sql+=" and a.nactionid="+lActionID+" and a.nloantypeid = "+lSubLoanTypeID+" ";
			
			sql+="  and a.NMODULEID="+Constant.ModuleType.CRAFTBROTHER+" and nstatusid="+Constant.RecordStatus.VALID+""; 
			sql+=" and a.ID(+)= b.ID and a.NAPPROVALCONTENTID(+) = b.NAPPROVALCONTENTID )) d";
			sql+=" where c.id =d.NAPPROVALCONTENTID and c.statusid="+SECConstant.ApplyFormStatus.APPROVALING+"";
			
			if (transactionTypeID != -1)
			{
				sql += " and c.transactiontypeid = " + transactionTypeID;
			}
			if (clientID != -1)
			{
				sql += " and c.ClientID = " + clientID;
			}
			if (counterpartID != -1)
			{
				sql += " and c.CounterpartID = " + counterpartID;
			}
			if (startAmount != 0)
			{
				sql += " and c.Amount >= " + startAmount;
			}
			if (endAmount != 0)
			{
				sql += " and c.Amount <= " + endAmount;
			}
			if (startPledgeSecuritiesAmount != 0)
			{
				sql += " and c.PledgeSecuritiesAmount >= " + startPledgeSecuritiesAmount;
			}
			if (endPledgeSecuritiesAmount != 0)
			{
				sql += " and c.PledgeSecuritiesAmount <= " + endPledgeSecuritiesAmount;
			}
			if (currencyID > 0)
			{
				sql += " and c.currencyID = " + currencyID;
			}
			if (startDate != null)
			{
				sql += " and to_char(c.InputDate,'yyyy-mm-dd') >= '" + DataFormat.getDateString(startDate) + "'";
			}
			if (endDate != null)
			{
				sql += " and to_char(c.InputDate,'yyyy-mm-dd') <= '" + DataFormat.getDateString(endDate) + "'";
			}
			if (counterpartID != -1)
			{
				sql += " and c.CounterpartID = " + counterpartID;
			}
			if (accountID != -1)
			{
				sql += " and c.AccountID = " + accountID;
			}
			if (securitiesID != -1)
			{
				sql += " and c.SecuritiesID = " + securitiesID;
			}
			if (startPrice != 0)
			{
				sql += " and c.Price >= " + startPrice;
			}
			if (endPrice != 0)
			{
				sql += " and c.Price <= " + endPrice;
			}
			if (startQuantity != 0)
			{
				sql += " and c.Quantity >= " + startQuantity;
			}
			if (endQuantity != 0)
			{
				sql += " and c.Quantity <= " + endQuantity;
			}
			if (startStockQuantity != 0)
			{
				sql += " and c.StockQuantity >= " + startStockQuantity;
			}
			if (endStockQuantity != 0)
			{
				sql += " and c.StockQuantity <= " + endStockQuantity;
			}
			if (startCode != "" && startCode.length() > 0)
			{
				sql += " and c.Code >= '" + startCode + "'";
			}
			if (endCode != "" && endCode.length() > 0)
			{
				sql += " and c.Code <= '" + endCode + "'";
			}
			if (startTransactionStartDate != null)
			{
				sql += " and to_char(c.TransactionStartDate,'yyyy-mm-dd') >= '" + DataFormat.getDateString(startTransactionStartDate) + "'";
			}
			if (endTransactionStartDate != null)
			{
				sql += " and to_char(c.TransactionStartDate,'yyyy-mm-dd') <= '" + DataFormat.getDateString(endTransactionStartDate) + "'";
			}
			if (startTransactionEndDate != null)
			{
				sql += " and to_char(c.TransactionEndDate,'yyyy-mm-dd') >= '" + DataFormat.getDateString(startTransactionEndDate) + "'";
			}
			if (endTransactionEndDate != null)
			{
				sql += " and to_char(c.TransactionEndDate,'yyyy-mm-dd') <= '" + DataFormat.getDateString(endTransactionEndDate) + "'";
			}
			if (term > 0)
			{
				sql += " and c.term = " + term;
			}
			if (settlementTypeID > 0)
			{
				sql += " and c.settlementTypeID=" + settlementTypeID;
			}
			if (interestTypeID > 0)
			{
				sql += " and c.interestTypeID=" + interestTypeID;
			}
			/*
			boxu add 2006-09-19
			double startcostAmount = 0;  //回购成本开始
			double endcostAmount = 0;  //回购成本结束
			long startterm = -1;  //开始期限
			long endterm = -1;  //结束期限
			double startincomeRate = 0;  //开始回购收益率
			double endincomeRate = 0;  //结束回购收益率
			*/
			if (startcostAmount != 0)
			{
				sql += " and c.costAmount >= " + startcostAmount;
			}
			if (endcostAmount != 0)
			{
				sql += " and c.costAmount <= " + endcostAmount;
			}
			if (startincomeRate > 0)
			{
				sql += " and c.incomeRate >= " + startincomeRate;
			}
			if (endincomeRate > 0)
			{
				sql += " and c.incomeRate <= " + endincomeRate;
			}
			if (startterm > 0)
			{
				sql += " and c.term >= " + startterm;
			}
			if (endterm > 0)
			{
				sql += " and c.term <= " + endterm;
			}
		    
		    sql+=") union ( ";
		    
			sql += " select d.* from (";
			sql += " select aaa.* from (";
			sql += " select aa.*,rr.moneysegment,rr.approvalid from SEC_ApplyForm aa,loan_approvalrelation rr";
			//增加关于币种的判断-mhjin-东方电气
			sql += " where rr.moduleid="+Constant.ModuleType.CRAFTBROTHER+" and aa.amount>rr.moneysegment and rr.currencyid =" +currencyID+ " and aa.statusid="+SECConstant.ApplyFormStatus.SUBMITED;
			
			//lSubLoanTypeID 和 lActionID
			sql += " and rr.actionid = " + lActionID ;
			sql += " and rr.subloantypeid = " + lSubLoanTypeID ;
			
			if (transactionTypeID != -1)
			{
				sql += " and aa.transactiontypeid = " + transactionTypeID;
			}
			if (clientID != -1)
			{
				sql += " and aa.ClientID = " + clientID;
			}
			if (counterpartID != -1)
			{
				sql += " and aa.CounterpartID = " + counterpartID;
			}
			if (startAmount != 0)
			{
				sql += " and aa.Amount >= " + startAmount;
			}
			if (endAmount != 0)
			{
				sql += " and aa.Amount <= " + endAmount;
			}
			if (startPledgeSecuritiesAmount != 0)
			{
				sql += " and aa.PledgeSecuritiesAmount >= " + startPledgeSecuritiesAmount;
			}
			if (endPledgeSecuritiesAmount != 0)
			{
				sql += " and aa.PledgeSecuritiesAmount <= " + endPledgeSecuritiesAmount;
			}
			if (currencyID > 0)
			{
				sql += " and aa.currencyID = " + currencyID;
			}
			if (startDate != null)
			{
				sql += " and to_char(aa.InputDate,'yyyy-mm-dd') >= '" + DataFormat.getDateString(startDate) + "'";
			}
			if (endDate != null)
			{
				sql += " and to_char(aa.InputDate,'yyyy-mm-dd') <= '" + DataFormat.getDateString(endDate) + "'";
			}
			if (counterpartID != -1)
			{
				sql += " and aa.CounterpartID = " + counterpartID;
			}
			if (accountID != -1)
			{
				sql += " and aa.AccountID = " + accountID;
			}
			if (securitiesID != -1)
			{
				sql += " and aa.SecuritiesID = " + securitiesID;
			}
			if (startPrice != 0)
			{
				sql += " and aa.Price >= " + startPrice;
			}
			if (endPrice != 0)
			{
				sql += " and aa.Price <= " + endPrice;
			}
			if (startQuantity != 0)
			{
				sql += " and aa.Quantity >= " + startQuantity;
			}
			if (endQuantity != 0)
			{
				sql += " and aa.Quantity <= " + endQuantity;
			}
			if (startStockQuantity != 0)
			{
				sql += " and aa.StockQuantity >= " + startStockQuantity;
			}
			if (endStockQuantity != 0)
			{
				sql += " and aa.StockQuantity <= " + endStockQuantity;
			}
			if (startCode != "" && startCode.length() > 0)
			{
				sql += " and aa.Code >= '" + startCode + "'";
			}
			if (endCode != "" && endCode.length() > 0)
			{
				sql += " and aa.Code <= '" + endCode + "'";
			}
			if (startTransactionStartDate != null)
			{
				sql += " and to_char(aa.TransactionStartDate,'yyyy-mm-dd') >= '" + DataFormat.getDateString(startTransactionStartDate) + "'";
			}
			if (endTransactionStartDate != null)
			{
				sql += " and to_char(aa.TransactionStartDate,'yyyy-mm-dd') <= '" + DataFormat.getDateString(endTransactionStartDate) + "'";
			}
			if (startTransactionEndDate != null)
			{
				sql += " and to_char(aa.TransactionEndDate,'yyyy-mm-dd') >= '" + DataFormat.getDateString(startTransactionEndDate) + "'";
			}
			if (endTransactionEndDate != null)
			{
				sql += " and to_char(aa.TransactionEndDate,'yyyy-mm-dd') <= '" + DataFormat.getDateString(endTransactionEndDate) + "'";
			}
			if (term > 0)
			{
				sql += " and aa.term = " + term;
			}
			if (settlementTypeID > 0)
			{
				sql += " and aa.settlementTypeID=" + settlementTypeID;
			}
			if (interestTypeID > 0)
			{
				sql += " and aa.interestTypeID=" + interestTypeID;
			}
			/*
			boxu add 2006-09-19
			double startcostAmount = 0;  //回购成本开始
			double endcostAmount = 0;  //回购成本结束
			long startterm = -1;  //开始期限
			long endterm = -1;  //结束期限
			double startincomeRate = 0;  //开始回购收益率
			double endincomeRate = 0;  //结束回购收益率
			*/
			if (startcostAmount != 0)
			{
				sql += " and aa.costAmount >= " + startcostAmount;
			}
			if (endcostAmount != 0)
			{
				sql += " and aa.costAmount <= " + endcostAmount;
			}
			if (startincomeRate > 0)
			{
				sql += " and aa.incomeRate >= " + startincomeRate;
			}
			if (endincomeRate > 0)
			{
				sql += " and aa.incomeRate <= " + endincomeRate;
			}
			if (startterm > 0)
			{
				sql += " and aa.term >= " + startterm;
			}
			if (endterm > 0)
			{
				sql += " and aa.term <= " + endterm;
			}
		    
			sql += " ) aaa,(";
			sql += " select aa.id,max(rr.moneysegment) maxamount from SEC_ApplyForm aa,loan_approvalrelation rr";
			//增加关于币种的判断-mhjin-东方电气
			sql += " where rr.moduleid="+Constant.ModuleType.CRAFTBROTHER+" and aa.amount>rr.moneysegment and rr.currencyid =" +currencyID+ " and aa.statusid="+SECConstant.ApplyFormStatus.SUBMITED;
			
			//lSubLoanTypeID 和 lActionID
			sql += " and rr.actionid = " + lActionID ;
			sql += " and rr.subloantypeid = " + lSubLoanTypeID ;
			
			if (transactionTypeID != -1)
			{
				sql += " and aa.transactiontypeid = " + transactionTypeID;
			}
			if (clientID != -1)
			{
				sql += " and aa.ClientID = " + clientID;
			}
			if (counterpartID != -1)
			{
				sql += " and aa.CounterpartID = " + counterpartID;
			}
			if (startAmount != 0)
			{
				sql += " and aa.Amount >= " + startAmount;
			}
			if (endAmount != 0)
			{
				sql += " and aa.Amount <= " + endAmount;
			}
			if (startPledgeSecuritiesAmount != 0)
			{
				sql += " and aa.PledgeSecuritiesAmount >= " + startPledgeSecuritiesAmount;
			}
			if (endPledgeSecuritiesAmount != 0)
			{
				sql += " and aa.PledgeSecuritiesAmount <= " + endPledgeSecuritiesAmount;
			}
			if (currencyID > 0)
			{
				sql += " and aa.currencyID = " + currencyID;
			}
			if (startDate != null)
			{
				sql += " and to_char(aa.InputDate,'yyyy-mm-dd') >= '" + DataFormat.getDateString(startDate) + "'";
			}
			if (endDate != null)
			{
				sql += " and to_char(aa.InputDate,'yyyy-mm-dd') <= '" + DataFormat.getDateString(endDate) + "'";
			}
			if (counterpartID != -1)
			{
				sql += " and aa.CounterpartID = " + counterpartID;
			}
			if (accountID != -1)
			{
				sql += " and aa.AccountID = " + accountID;
			}
			if (securitiesID != -1)
			{
				sql += " and aa.SecuritiesID = " + securitiesID;
			}
			if (startPrice != 0)
			{
				sql += " and aa.Price >= " + startPrice;
			}
			if (endPrice != 0)
			{
				sql += " and aa.Price <= " + endPrice;
			}
			if (startQuantity != 0)
			{
				sql += " and aa.Quantity >= " + startQuantity;
			}
			if (endQuantity != 0)
			{
				sql += " and aa.Quantity <= " + endQuantity;
			}
			if (startStockQuantity != 0)
			{
				sql += " and aa.StockQuantity >= " + startStockQuantity;
			}
			if (endStockQuantity != 0)
			{
				sql += " and aa.StockQuantity <= " + endStockQuantity;
			}
			if (startCode != "" && startCode.length() > 0)
			{
				sql += " and aa.Code >= '" + startCode + "'";
			}
			if (endCode != "" && endCode.length() > 0)
			{
				sql += " and aa.Code <= '" + endCode + "'";
			}
			if (startTransactionStartDate != null)
			{
				sql += " and to_char(aa.TransactionStartDate,'yyyy-mm-dd') >= '" + DataFormat.getDateString(startTransactionStartDate) + "'";
			}
			if (endTransactionStartDate != null)
			{
				sql += " and to_char(aa.TransactionStartDate,'yyyy-mm-dd') <= '" + DataFormat.getDateString(endTransactionStartDate) + "'";
			}
			if (startTransactionEndDate != null)
			{
				sql += " and to_char(aa.TransactionEndDate,'yyyy-mm-dd') >= '" + DataFormat.getDateString(startTransactionEndDate) + "'";
			}
			if (endTransactionEndDate != null)
			{
				sql += " and to_char(aa.TransactionEndDate,'yyyy-mm-dd') <= '" + DataFormat.getDateString(endTransactionEndDate) + "'";
			}
			if (term > 0)
			{
				sql += " and aa.term = " + term;
			}
			if (settlementTypeID > 0)
			{
				sql += " and aa.settlementTypeID=" + settlementTypeID;
			}
			if (interestTypeID > 0)
			{
				sql += " and aa.interestTypeID=" + interestTypeID;
			}
			/*
			boxu add 2006-09-19
			double startcostAmount = 0;  //回购成本开始
			double endcostAmount = 0;  //回购成本结束
			long startterm = -1;  //开始期限
			long endterm = -1;  //结束期限
			double startincomeRate = 0;  //开始回购收益率
			double endincomeRate = 0;  //结束回购收益率
			*/
			if (startcostAmount != 0)
			{
				sql += " and aa.costAmount >= " + startcostAmount;
			}
			if (endcostAmount != 0)
			{
				sql += " and aa.costAmount <= " + endcostAmount;
			}
			if (startincomeRate > 0)
			{
				sql += " and aa.incomeRate >= " + startincomeRate;
			}
			if (endincomeRate > 0)
			{
				sql += " and aa.incomeRate <= " + endincomeRate;
			}
			if (startterm > 0)
			{
				sql += " and aa.term >= " + startterm;
			}
			if (endterm > 0)
			{
				sql += " and aa.term <= " + endterm;
			}
		    
			sql += " group by aa.id ) bbb";
			sql += " where aaa.id = bbb.id and aaa.moneysegment = bbb.maxamount) d,";	
			sql += " loan_approvalsetting e,loan_approvalitem f";
			sql += " where d.approvalid = e.id and f.napprovalid=e.id and f.nlevel=1 and f.nuserid="+userID;
			sql +=")";
			System.out.println("查询语句SQL^^^^^^^^^^^===="+sql);
			
			prepareStatement(sql);
			ResultSet rs1 = executeQuery();
			long rep = -1;
			while (rs1 != null && rs1.next())
			{
				rep = rs1.getLong("id");
				
				ApplyInfo applyInfo = new ApplyInfo();
				
				applyInfo.setId(rep);
				applyInfo.setOfficeId(rs1.getLong("officeId")); //办事处
				applyInfo.setCurrencyId(rs1.getLong("currencyId")); //币种
				applyInfo.setCode(rs1.getString("code")); //申请书编号
				applyInfo.setTransactionTypeId(rs1.getLong("transactionTypeId")); //交易类型
				applyInfo.setTransactionStartDate(rs1.getTimestamp("transactionStartDate")); //成交日期开始日
				applyInfo.setTransactionEndDate(rs1.getTimestamp("transactionEndDate")); //成交日期截止日
				applyInfo.setClientId(rs1.getLong("clientId")); //业务单位
				applyInfo.setCounterpartId(rs1.getLong("counterpartId")); //交易对手[债券分销商/基金管理公司]
				applyInfo.setAccountId(rs1.getLong("accountId")); //交易帐号[资金帐号]
				applyInfo.setSecuritiesId(rs1.getLong("securitiesId")); //证券代码ID[抵押债券代码ID/可转债代码ID]
				applyInfo.setAmount(rs1.getDouble("amount")); //拆借金额[全价/认购金额/申购金额]
				applyInfo.setPledgeSecuritiesAmount(rs1.getDouble("pledgeSecuritiesAmount")); //券面总额
				applyInfo.setPrice(rs1.getDouble("price")); //成交价格[转股价格]
				applyInfo.setQuantity(rs1.getDouble("quantity")); //申购数量[成交数量/赎回份额/转股的债券数量]
				applyInfo.setConvertRate(rs1.getDouble("convertRate")); //折算比例[预付定金比例]
				applyInfo.setStockQuantity(rs1.getDouble("stockQuantity")); //转成股票数量
				applyInfo.setCommissionCharge(rs1.getDouble("commissionCharge")); //预计手续费返还
				applyInfo.setLiquidateRate(rs1.getLong("liquidateRate")); //清算速度
				applyInfo.setBidTypeId(rs1.getLong("bidTypeId")); //投标方式
				applyInfo.setTerm(rs1.getLong("term")); //拆借期限[回购期限]
				applyInfo.setTermTypeId(rs1.getLong("termTypeId")); //期限类型
				applyInfo.setRate(rs1.getDouble("rate")); //拆借利率[回购利率]
				applyInfo.setStockId(rs1.getLong("stockId")); //转成股票代码ID
				applyInfo.setRemark(rs1.getString("remark")); //备注
				applyInfo.setNextCheckUserId(rs1.getLong("nextCheckUserId")); //下一级审核人
				applyInfo.setNextCheckLevel(rs1.getLong("nextCheckLevel")); //下一级审核级别
				applyInfo.setInputUserId(rs1.getLong("inputUserId")); //录入人
				applyInfo.setInputDate(rs1.getTimestamp("inputDate")); //录入时间
				applyInfo.setUpdateUserId(rs1.getLong("updateUserId")); //修改人
				applyInfo.setUpdateDate(rs1.getTimestamp("updateDate")); //修改时间
				applyInfo.setStatusId(rs1.getLong("statusId")); //状态
				applyInfo.setTimestamp(rs1.getTimestamp("timestamp")); //实践戳
				applyInfo.setCommissionChargeRate(rs1.getDouble("commissionChargeRate")); //手续费率
				applyInfo.setSettlementTypeID(rs1.getLong("settlementTypeID")); //结息方式ID
				applyInfo.setInterestTypeID(rs1.getLong("interestTypeID")); //计息方式ID
				applyInfo.setMaturitySource(rs1.getString("maturitySource")); //还款资金来源
				applyInfo.setStartRate(rs1.getDouble("startRate")); //常用记息方式：期初
				applyInfo.setChangeRate(rs1.getDouble("changeRate")); //常用记息方式：变动数
				applyInfo.setIsLowLevel(rs1.getLong("IsLowLevel")); //是否最低审核级别
				//SEC_ApplyForm表中没有的字段
				applyInfo.setClientName(NameRef.getClientNameByID(rs1.getLong("clientId"))); //业务单位
				applyInfo.setCounterpartName(NameRef.getCounterpartNameByID(rs1.getLong("counterpartId"))); //交易对手
				applyInfo.setSecuritiesName(NameRef.getSecuritiesNameByID(rs1.getLong("securitiesId"))); //证券名称
				applyInfo.setAccountNo(NameRef.getAccountNobyAccountID(rs1.getLong("accountId"))); //资金账户代码
				applyInfo.setAccountName(NameRef.getAccountNameById(rs1.getLong("accountId"))); //资金账户名称
				applyInfo.setStockHolderAccountCode(NameRef.getStockHolderAccountCodeByAccountId(rs1.getLong("accountId"))); //股东账户代码
				applyInfo.setStockName(NameRef.getSecuritiesNameByID(rs1.getLong("stockId"))); //转成股票名称
				/*qqgd add this			*/
				applyInfo.setBondName(rs1.getString("bondName"));
				applyInfo.setBondScale(this.getBondScale(applyInfo.getId()));
				/* end of qqgd's adding */
				applyInfo.setRecordCount(lRecordCount); //记录数
				applyInfo.setPageCount(lPageCount); //页数
				
				//boxu add 2006-09-19
				applyInfo.setCostAmount(rs1.getDouble("costAmount"));  //回购成本
				applyInfo.setIncomeRate(rs1.getDouble("incomeRate"));  //回购收益率
				
				v.add(applyInfo);
			}
		}
		catch (ITreasuryDAOException e)
		{
			throw new SecuritiesDAOException("批量查询申请书产生错误", e);
		}
		catch (SQLException e)
		{
			throw new SecuritiesDAOException("批量查询申请书产生错误", e);
		}
		try
		{
			finalizeDAO();
		}
		catch (ITreasuryDAOException e)
		{
			throw new SecuritiesDAOException(e);
		}
		return (v.size() > 0 ? v : null);
	}
	
	private double getBondScale(long lID)
	{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		double totalBond = 0;
		String strSQL = "";
		try
		{
			conn = Database.getConnection();
			strSQL = "select sum(amount) as bondScale \n" + "from SEC_ApplyFormBondType \n" + "where applyFormID=" + lID + " and statusID = 1";
			ps = conn.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs.next())
			{
				totalBond = rs.getDouble("bondScale");
			}
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
			if (conn != null)
			{
				conn.close();
				conn = null;
			}
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return totalBond;
	}
	public long check(ApprovalTracingInfo ATInfo) throws SecuritiesDAOException
	{
		long lMaxID = -1;
		long lSerialID = -1;
		long lStatusID = -1;
		long lResultID = -1;
		String strSQL = "";
		//定义相应操作常量
		//模块类型
		long lModuleID = ATInfo.getModuleID();
		//业务类型
		long lLoanTypeID = ATInfo.getLoanTypeID();
		//操作类型
		long lActionID = ATInfo.getActionID();
		long lApprovalContentID = ATInfo.getApprovalContentID();
		long lNextUserID = ATInfo.getNextUserID();
		//long lNextLevel = ATInfo.getNextLevel();
		long lApprovalID = ATInfo.getApprovalID();
		long lUserID = ATInfo.getInputUserID();
		ApplyInfo aInfo = new ApplyInfo();
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		if (ATInfo.getCheckActionID() == SECConstant.Actions.REJECT) //拒绝
		{
			aInfo.setId(lApprovalContentID);
			aInfo.setStatusId(SECConstant.ApplyFormStatus.REJECTED);
			try
			{
				update(aInfo);
			}
			catch (ITreasuryDAOException e)
			{
				throw new SecuritiesDAOException(e);
			}
		}
		if (ATInfo.getCheckActionID() == SECConstant.Actions.CHECK) //审批
		{
			aInfo.setId(lApprovalContentID);
			aInfo.setStatusId(SECConstant.ApplyFormStatus.SUBMITED);
			aInfo.setNextCheckUserId(lNextUserID);
			//aInfo.setNextCheckLevel(lNextLevel+1);
			//System.out.println("eeeeeeeeeeeeeee  "+aInfo.getApprovalID()+ "   "+ATInfo.getNextLevel());
			aInfo.setNextCheckLevel(getNextCheckLevelByApplyID(ATInfo.getApprovalID(),ATInfo.getNextUserID()));
			//System.out.println("rrrrrrrrrrrrrrr "+aInfo.getNextCheckLevel());
			try
			{
				update(aInfo);
			}
			catch (ITreasuryDAOException e)
			{
				throw new SecuritiesDAOException(e);
			}
		}
		if (ATInfo.getCheckActionID() == SECConstant.Actions.CHECKOVER) //审批&&最后
		{
			aInfo.setId(lApprovalContentID);
			aInfo.setStatusId(SECConstant.ApplyFormStatus.CHECKED);
			aInfo.setNextCheckUserId(lNextUserID);
			try
			{
				update(aInfo);
			}
			catch (ITreasuryDAOException e)
			{
				throw new SecuritiesDAOException(e);
			}
			
			//审批完成后需要做的操作
			//生成合同
			doAfterCheckOver(lApprovalContentID);
		}
		if (ATInfo.getCheckActionID() == SECConstant.Actions.RETURN) //修改
		{
			aInfo.setId(lApprovalContentID);
			aInfo.setStatusId(SECConstant.ApplyFormStatus.SUBMITED);
			
			
			//zpli 2005-12-07 审批退回时下一级审批人应为第一级审批人，不应为当前录入人
			aInfo.setNextCheckUserId(ATInfo.getInputUserID());
			
			try{
				
				Connection con = Database.getConnection();
				ApprovalDao dao = new ApprovalDao(con);		
				ApprovalSettingInfo settingInfo=dao.findApprovalItem(lApprovalID,1,-1,ATInfo.getCurrencyID(),ATInfo.getOfficeID());
				Vector v=settingInfo.getNextUser();
				if (v.size()>0){
					ApprovalUserInfo userInfo=(ApprovalUserInfo)v.firstElement();
					if(userInfo!=null)
						aInfo.setNextCheckUserId(userInfo.getUserID());
				}		
					
			}
			catch(Exception e){
				e.printStackTrace();
			}
			//////////////////////////////////////////////////////////
			
			//置下一级审核为1
			aInfo.setNextCheckLevel(1);
			try
			{
				update(aInfo);
			}
			catch (ITreasuryDAOException e)
			{
				throw new SecuritiesDAOException(e);
			}
		}
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		log4j.debug("check end");
		return lApprovalContentID;
	}
	/*
	 *  (non-Javadoc)
	 * 
	 */
	public String getApplyCode(long lTransactionTypeID) throws SecuritiesDAOException
	{
		String strSQL = "";
		String strCode = "000";
		long lCode = 0;
		Timestamp tsToday = Env.getSystemDateTime();
		String strYear = DataFormat.getDateString(tsToday).substring(2, 4);
		try
		{
			initDAO();
		}
		catch (ITreasuryDAOException e)
		{
			throw new SecuritiesDAOException(e);
		}
		strSQL = " select max(nvl(Code,0)) Code from SEC_ApplyForm where Code like 'SQ" + strYear + lTransactionTypeID + "%'";
		log4j.debug(strSQL);
		try
		{
			prepareStatement(strSQL);
			ResultSet rs = executeQuery();
			if (rs != null && rs.next())
			{
				strCode = rs.getString(1);
				log4j.debug(strCode);
				if (strCode != null && strCode.length() == 11)
				{
					lCode = Long.parseLong(strCode.substring(8)) + 1;
				}
				else
				{
					lCode = 1;
				}
				strCode = "SQ" + strYear + lTransactionTypeID + DataFormat.formatInt(lCode, 3, true);
			}
		}
		catch (ITreasuryDAOException e)
		{
			throw new SecuritiesDAOException("生成申请书编号产生错误", e);
		}
		catch (SQLException e)
		{
			throw new SecuritiesDAOException("生成申请书编号产生错误", e);
		}
		try
		{
			finalizeDAO();
		}
		catch (ITreasuryDAOException e)
		{
			throw new SecuritiesDAOException(e);
		}
		log4j.debug(":::::::::: ::::strcode::::::" + strCode);
		return strCode;
	}
	/*
	 *  (non-Javadoc)
	 * 
	 */
	private void doAfterCheckOver(long lApplyID) throws SecuritiesDAOException
	{
		ApplyInfo aInfo = new ApplyInfo();
		try
		{
			aInfo = (ApplyInfo) findByID(lApplyID, aInfo.getClass());
			if (aInfo != null)
			{
				if (aInfo.getTransactionTypeId() == SECConstant.BusinessType.CAPITAL_REPURCHASE.CAPITAL_REPURCHASE
					|| aInfo.getTransactionTypeId() == SECConstant.BusinessType.ENTRUST_FINANCING.ENTRUST_FINANCING
					|| aInfo.getTransactionTypeId() == SECConstant.BusinessType.ENTRUSTED_FINANCING.ENTRUSTED_FINANCING
					|| aInfo.getTransactionTypeId() == SECConstant.BusinessType.FOREIGN_CURRENCY_INVESTMENT.FOREIGN_CURRENCY_INVESTMENT
					|| aInfo.getTransactionTypeId() == SECConstant.BusinessType.BOND_UNDERWRITING.BOND_UNDERWRITING)
				{
					//生成合同
					long contractID = doCreateContract(aInfo);
					
					if (aInfo.getTransactionTypeId() == SECConstant.BusinessType.BOND_UNDERWRITING.BOND_UNDERWRITING)
						doCopyBondType(lApplyID, contractID);
				}
				else
				{
					aInfo = doFinishApply(aInfo);
				}
			}
		}
		catch (ITreasuryDAOException e)
		{
			throw new SecuritiesDAOException(e);
		}
	}
	/*
	 *  (non-Javadoc)
	 * 
	 */
	
	public void doCheckOver(long lApplyID) throws SecuritiesDAOException
	{
		ApplyInfo aInfo = new ApplyInfo();
		try
		{
			aInfo = (ApplyInfo) findByID(lApplyID, aInfo.getClass());
			if (aInfo != null)
			{
				if (aInfo.getTransactionTypeId() == SECConstant.BusinessType.CAPITAL_REPURCHASE.CAPITAL_REPURCHASE
					|| aInfo.getTransactionTypeId() == SECConstant.BusinessType.ENTRUST_FINANCING.ENTRUST_FINANCING
					|| aInfo.getTransactionTypeId() == SECConstant.BusinessType.ENTRUSTED_FINANCING.ENTRUSTED_FINANCING
					|| aInfo.getTransactionTypeId() == SECConstant.BusinessType.FOREIGN_CURRENCY_INVESTMENT.FOREIGN_CURRENCY_INVESTMENT
					|| aInfo.getTransactionTypeId() == SECConstant.BusinessType.BOND_UNDERWRITING.BOND_UNDERWRITING)
				{
					//生成合同
					long contractID = doCreateContract(aInfo);
					
					if (aInfo.getTransactionTypeId() == SECConstant.BusinessType.BOND_UNDERWRITING.BOND_UNDERWRITING)
						doCopyBondType(lApplyID, contractID);
				}
				else
				{
					aInfo = doFinishApply(aInfo);
				}
			}
		}
		catch (ITreasuryDAOException e)
		{
			throw new SecuritiesDAOException(e);
		}
	}
	/*
	 *  (non-Javadoc)
	 * 
	 */
	private ApplyInfo doFinishApply(ApplyInfo aInfo) throws SecuritiesDAOException
	{
		Timestamp tsToday = Env.getSystemDate();
		Timestamp tsTransactionStartDate = null;
		Timestamp tsTransactionEndDate = null;
		long lApplyID = -1;
		try
		{
			tsTransactionStartDate = aInfo.getTransactionStartDate();
			tsTransactionEndDate = aInfo.getTransactionEndDate();
			lApplyID = aInfo.getId();
			aInfo.clearUsedFields();
			aInfo.setId(lApplyID);
			//若申请审批完成之日晚于成交日期截止日，
			//则在审批完成时，该申请的状态自动变为“已结束”
			if (tsToday.after(tsTransactionEndDate))
			{
				aInfo.setStatusId(SECConstant.ApplyFormStatus.COMPLETED);
				try
				{
					update(aInfo);
				}
				catch (ITreasuryDAOException e)
				{
					throw new SecuritiesDAOException(e);
				}
			}
			//若申请审批完成之日晚于成交日期开始日，
			//则在审批完成时，系统自动将“审批完成日”定为“成交日期开始日”
			/** 
			 * modified by qhzhou 2010-05-20 华联以录入成交开始日为准 
			else
				if (tsToday.after(tsTransactionStartDate))
				{
					aInfo.setTransactionStartDate(tsToday);
					try
					{
						update(aInfo);
					}
					catch (ITreasuryDAOException e)
					{
						throw new SecuritiesDAOException(e);
					}
				}
			*/
		}
		catch (SecuritiesDAOException e)
		{
			throw new SecuritiesDAOException("申请书审核完成后的操作产生错误", e);
		}
		return aInfo;
	}
	/*
	 *  (non-Javadoc)
	 * 
	 */
	private void doCopyBondType(long lID, long contractID) throws SecuritiesDAOException
	{
		SEC_BondTypeDAO applyDao = new SEC_BondTypeDAO();
		ContractBondTypeDao conDao = new ContractBondTypeDao();
		Vector aList = null;
		BondTypeInfo aInfo = null;
		ContractBondTypeInfo cInfo = null;
		try
		{
			aList = (Vector) applyDao.findByApplyID(lID);
			//System.out.println("______________________" + aList.size());
			if (aList == null || aList.size() <= 0)
				return;
			int count = aList.size();
			for (int i = 0; i < count; i++)
			{
				aInfo = (BondTypeInfo) aList.get(i);
				cInfo = new ContractBondTypeInfo();
				cInfo.setContractFormId(contractID);
				cInfo.setAmount(aInfo.getAmount());
				cInfo.setName(aInfo.getName());
				cInfo.setRate(aInfo.getRate());
				cInfo.setStatusId(1);
				cInfo.setTerm(aInfo.getTerm());
				conDao.setUseMaxID();
				conDao.add(cInfo);
			}
		}
		catch (ITreasuryDAOException e)
		{
			throw new SecuritiesDAOException(e);
		}
	}
	/*
	 *  (non-Javadoc)
	 * 
	 */
	private long doCreateContract(ApplyInfo aInfo) throws SecuritiesDAOException
	{
		//super("SEC_APPLYCONTRACT");
		SecuritiesContractDao dao = new SecuritiesContractDao();
		SecuritiesContractInfo cInfo = new SecuritiesContractInfo();
		SecuritiesContractPlanVersionInfo pInfo = new SecuritiesContractPlanVersionInfo();
		
		//super("SEC_ContractPlan");
		SecuritiesContractPlanDao pdao = new SecuritiesContractPlanDao();
		pdao.setUseMaxID();
		long lContractID = -1;
		try
		{
			cInfo.setCode(dao.getContractCode(aInfo.getTransactionTypeId())); //合同编号
			cInfo.setApplyId(aInfo.getId()); //对应申请书ID
			cInfo.setClientId(aInfo.getClientId()); //业务单位ID
			cInfo.setCounterpartId(aInfo.getCounterpartId()); //交易对手，券商，委托方，存款行，发行人
			cInfo.setAccountId(aInfo.getAccountId()); //交易账号，资金账号
			cInfo.setAmount(aInfo.getAmount()); //回购金额，期初余额，存款金额，承销金额，委托金额
			cInfo.setTerm(aInfo.getTerm()); //拆借期限，回购期限，委托期限，存款期限，受托期限
			cInfo.setTermTypeId(aInfo.getTermTypeId()); //期限类型
			cInfo.setRate(aInfo.getRate()); //拆借利率，回购利率，预计收益率，年利率
			cInfo.setRemark(aInfo.getRemark()); //备注，其他委托内容，特殊记息方式
			cInfo.setCommissionChargeRate(aInfo.getCommissionChargeRate()); //手续费率
			cInfo.setSettlementTypeId(aInfo.getSettlementTypeID()); //结息方式ID
			cInfo.setInterestTypeId(aInfo.getInterestTypeID()); //计息方式ID，委托资产形式ID，承销方式ID
			cInfo.setMaturitySource(aInfo.getMaturitySource()); //还款资金来源
			cInfo.setStartRate(aInfo.getStartRate()); //常用计息方式：期初
			cInfo.setChangeRate(aInfo.getChangeRate()); //常用计息方式：变动数
			cInfo.setTransactionTypeId(aInfo.getTransactionTypeId()); //交易类型
			cInfo.setTransactionStartDate(aInfo.getTransactionStartDate()); //成交日期开始日，回购，合同开始日期，起息日
			cInfo.setTransactionEndDate(aInfo.getTransactionEndDate()); //成交日期截至日，回购，合同结束日期，到期日
			cInfo.setCurrencyId(aInfo.getCurrencyId()); //币种
			cInfo.setOfficeId(aInfo.getOfficeId()); //财务公司
			cInfo.setNextCheckUserId(aInfo.getNextCheckUserId()); //下一级审核人
			cInfo.setInputUserId(aInfo.getInputUserId()); //录入人
			cInfo.setInputDate(aInfo.getInputDate()); //录入时间
			cInfo.setUpdateUserId(aInfo.getUpdateUserId()); //修改人
			cInfo.setUpdateDate(aInfo.getUpdateDate()); //修改时间
			cInfo.setStatusId(SECConstant.ContractStatus.SAVE); //合同状态
			cInfo.setBondName(aInfo.getBondName());
			cInfo.setContractInterest(0);
			cInfo.setContractInterestDate(aInfo.getTransactionStartDate());
			
			//boxu add 2006-09-20
			cInfo.setCostAmount(aInfo.getCostAmount());  //回购成本
			cInfo.setIncomeRate(aInfo.getIncomeRate());  //回购收益率
			
			lContractID = dao.add(cInfo);
			pInfo.setApplyID(aInfo.getId());
			pInfo.setContractID(lContractID);
			pInfo.setInputTime(aInfo.getInputDate());
			pInfo.setInputUserID(aInfo.getInputUserId());
			pInfo.setIsUsed(0);
			pInfo.setPlanVersion(1);
			pInfo.setStatusID(1);
			
			//合同表
			pdao.add(pInfo);
		}
		catch (ITreasuryDAOException e)
		{
			throw new SecuritiesDAOException(e);
		}
		return lContractID;
	}
	/*
	 *  查询交易对手授信额度的相关信息
	 * 	只针对资金拆借业务（拆入，拆出）
	 */
	public ApplyInfo findCounterpartCreditInfo(ApplyInfo info) throws SecuritiesDAOException
	{
		StringBuffer strSQL = new StringBuffer();
		long lCreditTransactionTypeID = -1;
		if (info != null && info.getCounterpartId() > 0 && info.getTransactionTypeId() > 0)
		{
			try
			{
				initDAO();
			}
			catch (ITreasuryDAOException e)
			{
				throw new SecuritiesDAOException(e);
			}
			if (info.getTransactionTypeId() == SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_IN)
			{
				lCreditTransactionTypeID = SECConstant.BusinessType.CAPITAL_IN_CREDIT_EXTENSION.CAPITAL_IN_CREDIT_EXTENSION;
			}
			else
				if (info.getTransactionTypeId() == SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_OUT)
				{
					lCreditTransactionTypeID = SECConstant.BusinessType.CAPITAL_OUT_CREDIT_EXTENSION.CAPITAL_OUT_CREDIT_EXTENSION;
				}
			strSQL.append(" select t2.ID,t2.Code,t2.Name, \n");
			strSQL.append(" t3.sumpledgesecuritiesamount SumPledgeSecuritiesAmount1, \n");
			strSQL.append(" t4.sumpledgesecuritiesamount SumPledgeSecuritiesAmount2, \n");
			strSQL.append(" t1.TransactionStartDate, \n");
			strSQL.append(" t1.TransactionEndDate,t1.PledgeSecuritiesAmount,t1.Term \n");
			strSQL.append(" from SEC_ApplyForm t1, \n");
			strSQL.append(" SEC_CounterPart t2, \n");
			strSQL.append(" (select counterpartid,transactiontypeid,sum(pledgesecuritiesamount) sumpledgesecuritiesamount \n");
			strSQL.append(" from ( \n");
			strSQL.append(" select counterpartid,transactiontypeid,pledgesecuritiesamount \n");
			strSQL.append(" from sec_applyform \n");
			strSQL.append(" where statusid in (1,2) \n");
			strSQL.append(" union all \n");
			strSQL.append(" select counterpartid,transactiontypeid,pledgesecuritiesamount \n");
			strSQL.append(" from sec_deliveryorder \n");
			strSQL.append(" where statusid > 0 \n");
			strSQL.append(" ) \n");
			strSQL.append(" group by counterpartid,transactiontypeid) t3, \n");
			strSQL.append(" (select transactiontypeid,sum(pledgesecuritiesamount) sumpledgesecuritiesamount \n");
			strSQL.append(" from ( \n");
			strSQL.append(" select transactiontypeid,pledgesecuritiesamount \n");
			strSQL.append(" from sec_applyform \n");
			strSQL.append(" where statusid in (1,2) \n");
			strSQL.append(" union all \n");
			strSQL.append(" select transactiontypeid,pledgesecuritiesamount \n");
			strSQL.append(" from sec_deliveryorder \n");
			strSQL.append(" where statusid > 0 \n");
			strSQL.append(" ) \n");
			strSQL.append(" group by transactiontypeid ");
			strSQL.append(" ) t4 \n");
			strSQL.append(" where t1.CounterPartID = t2.ID \n");
			strSQL.append(" and t3.CounterpartId(+) = t2.ID \n");
			strSQL.append(" and t3.TransactionTypeID = t4.TransactionTypeID \n");
			strSQL.append(" and t1.StatusID = " + SECConstant.ApplyFormStatus.CHECKED);
			strSQL.append(" and t1.CounterPartID = " + info.getCounterpartId());
			strSQL.append(" and t1.TransactionTypeID = " + lCreditTransactionTypeID);
			strSQL.append(" and t3.TransactionTypeId = " + info.getTransactionTypeId());
			strSQL.append(" and t2.StatusID = " + SECConstant.CounterpartStatus.CHECKED);
			log4j.debug(strSQL.toString());
			try
			{
				prepareStatement(strSQL.toString());
				ResultSet rs = executeQuery();
				if (rs != null && rs.next())
				{
					info.setCreditAmount(rs.getDouble("pledgeSecuritiesAmount"));
					info.setCreditStartDate(rs.getTimestamp("TransactionStartDate"));
					info.setCreditEndDate(rs.getTimestamp("TransactionEndDate"));
					info.setCreditTerm(rs.getLong("Term"));
					info.setSumCreditAmount1(rs.getDouble("SumPledgeSecuritiesAmount1"));
					info.setSumCreditAmount2(rs.getDouble("SumPledgeSecuritiesAmount2"));
				}
			}
			catch (ITreasuryDAOException e)
			{
				throw new SecuritiesDAOException("查询交易对手授信额度的操作产生错误", e);
			}
			catch (SQLException e)
			{
				throw new SecuritiesDAOException("查询交易对手授信额度的操作产生错误", e);
			}
			try
			{
				finalizeDAO();
			}
			catch (ITreasuryDAOException e)
			{
				throw new SecuritiesDAOException(e);
			}
		}
		return info;
	}
	public static void main(String[] args)
	{
		SEC_ApplyDAO dao = new SEC_ApplyDAO();
		ApplyInfo info = new ApplyInfo();
		/*  
		info.setId(0);
		try {
		    info.setCode(dao.getApplyCode(1));
		} catch (SecuritiesDAOException e) {
		    e.printStackTrace();
		}
		Timestamp tsDate = Env.getSystemDateTime();
		info.setTransactionTypeId(1101);
		info.setInputDate(tsDate);
		info.setInputUserId(1);
		info.setStatusId(1);
		try {
		    dao.add(info);
		} catch (SecuritiesDAOException e1) {
		    // TODO Auto-generated catch block
		    e1.printStackTrace();
		}
		
		
		info.setId(42);
		try {
		    info.setCode(dao.getApplyCode(1));
		} catch (SecuritiesDAOException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
		Timestamp tsDate = Env.getSystemDateTime();
		info.setTransactionTypeId(1101);
		info.setInputDate(tsDate);
		info.setInputUserId(2);
		info.setStatusId(2);
		try {
		    dao.update(info);
		} catch (SecuritiesDAOException e1) {
		    // TODO Auto-generated catch block
		    e1.printStackTrace();
		}
		
		
		
		try {
		    info = (ApplyInfo)dao.findByID(1,info.getClass());
		} catch (SecuritiesDAOException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
		Log.print(info.toString());
		
		*/
		ApplyQueryInfo qInfo = new ApplyQueryInfo();
		Collection c = null;
		qInfo.setQueryPurpose(2);
		qInfo.setUserId(1);
		qInfo.setTransactionTypeId(1301);
		qInfo.setPageLineCount(100);
		qInfo.setPageNo(1);
		try
		{
			c = dao.findByMultiOption(qInfo);
			Iterator it = c.iterator();
			while (it.hasNext())
			{
				ApplyInfo aInfo = (ApplyInfo) it.next();
				Log.print(aInfo.toString());
			}
		}
		catch (SecuritiesDAOException e)
		{
			e.printStackTrace();
		}
	}
	/**
	 * 根据证券ID或者证券类别ID获取已审批未使用的申请书金额
	 * */
	public double getAmountOfApprovedAndUsing(long securitiesID, long securitiesTypeID) throws SecuritiesDAOException
	{
		return getAmountByCondition(securitiesID, securitiesTypeID, USINGCONDITION_USING);
	}
	/**
	 * 根据证券ID或者证券类别ID获取申请书总金额
	 * */
	public double getAmountOfUsed(long securitiesID, long securitiesTypeID) throws SecuritiesDAOException
	{
		return getAmountByCondition(securitiesID, securitiesTypeID, USINGCONDITION_USED);
	}
	private boolean checkAttornment(long lID) throws SecuritiesDAOException
	{
		String strSQL = "";
		Connection con = null;
		PreparedStatement ps = null;
		strSQL = " select count(*) from SEC_AttornmentApplyForm where statusId in (1,2,3,5) and repurchaseApplyId=" + lID;
		log4j.debug(strSQL);
		try
		{
			con = Database.getConnection();
			ps = con.prepareStatement(strSQL);
			ResultSet rs = ps.executeQuery();
			if (rs != null && rs.next())
			{
				long i = rs.getLong(1);
				log.print("+++++++++++" + i);
				if (i > 0)
					return false;
			}
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
		catch (SQLException ex)
		{
			ex.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return true;
	}
	private final static long USINGCONDITION_USING = 1;
	private final static long USINGCONDITION_USED = 2;
	private double getAmountByCondition(long securitiesID, long securitiesTypeID, long usingConditon) throws SecuritiesDAOException
	{
		double amount = 0.0;
		try
		{
			String strSQL = "select sum(Amount) from " + strTableName + " WHERE securitiesid ";
			//+ " WHERE TransactionTypeID in (select id from SEC_TransactionType where StockDirection=1) and securitiesid ";
			initDAO();
			if (securitiesID > 0)
			{
				strSQL += " = " + securitiesID;
			}
			else
			{
				strSQL += " in (select id from SEC_Securities where TypeID= " + securitiesTypeID + ")";
			}
			if (usingConditon == USINGCONDITION_USING)
			{
				strSQL += " and StatusID in (1,2)";
			}
			else
			{
				strSQL += " and StatusID = 3";
			}
			prepareStatement(strSQL);
			ResultSet localRS = executeQuery();
			try
			{
				if (localRS.next())
					amount = localRS.getDouble(1);
			}
			catch (SQLException e1)
			{
				throw new ITreasuryDAOException("", e1);
			}
			finalizeDAO();
		}
		catch (ITreasuryDAOException e)
		{
			throw new SecuritiesDAOException(e);
		}
		return amount;
	}
	/**
	 * 获得该申请书的当前审批级别
	 * @param applyId
	 * @return
	 * @throws SecuritiesDAOException
	 */
	
	
	private long getNextCheckLevelByApplyID(long applyId,long nextUserId) throws SecuritiesDAOException
	{
		long nextCheckLevel = -1;
		String strSQL = "";
		strSQL = " select nlevel from loan_approvalitem where 1 = 1 ";
		strSQL += " and napprovalid = " + applyId +" and nuserid="+nextUserId;
		try
		{
			initDAO();
			prepareStatement(strSQL);
			System.out.println("fewfew");
			System.out.println("dddddddddddddd  "+strSQL);
			ResultSet rs = executeQuery();
			try
			{
				while (rs != null && rs.next())
				{
					nextCheckLevel = rs.getLong("nlevel");
				}
			}
			catch (SQLException e1)
			{
				e1.printStackTrace();
			}
		}
		catch (ITreasuryDAOException e)
		{
			throw new SecuritiesDAOException(e);
		}
		try
		{
			finalizeDAO();
		}
		catch (ITreasuryDAOException e)
		{
			throw new SecuritiesDAOException(e);
		}
		return nextCheckLevel;
	}
	
	
	private long getNextCheckLevelByApplyID(long applyId) throws SecuritiesDAOException
	{
		long nextCheckLevel = -1;
		String strSQL = "";
		strSQL = " select nextCheckLevel from sec_applyform where 1 = 1 ";
		strSQL += " and id = " + applyId;
		try
		{
			initDAO();
			prepareStatement(strSQL);
			ResultSet rs = executeQuery();
			try
			{
				while (rs != null && rs.next())
				{
					nextCheckLevel = rs.getLong("nextCheckLevel");
				}
			}
			catch (SQLException e1)
			{
				e1.printStackTrace();
			}
		}
		catch (ITreasuryDAOException e)
		{
			throw new SecuritiesDAOException(e);
		}
		try
		{
			finalizeDAO();
		}
		catch (ITreasuryDAOException e)
		{
			throw new SecuritiesDAOException(e);
		}
		return nextCheckLevel;
	}
	/**
	 * 检查资金拆借授信额度
	 * @param counterpartID	交易对手
	 * @param transactionTypeID 交易类型
	 * @param newPledgeSecuritiesAmount 拆借金额
	 * @param lApplyID 申请书标示
	 * @return
	 * @throws SecuritiesDAOException
	 */
	public String checkCapitalLandingCreditExtension(long clientID, long counterpartID, long transactionTypeID, double newPledgeSecuritiesAmount, long lApplyID,Timestamp startDate,Timestamp endDate) throws SecuritiesDAOException
	{
		StringBuffer strSQL = new StringBuffer();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String strResult = "";
		String counterpartCode = "";
		String counterpartName = "";
		Timestamp creditStartDate = null;
		Timestamp creditEndDate = null;
		long term = 0;
		double creditAmount = 0;
		double applyAmount1 = 0; //申请书金额（已提交，已审核）
		double applyAmount2 = 0; //申请书金额（已使用）
		double deliverOrderAmount = 0; //已使用申请书对应的交割单金额
		double sumCreditAmount1 = 0; //申请书按交易对手、交易类型区分的累计金额
		double sumCreditAmount2 = 0; //申请书按交易类型区分的累计金额
		double pledgeSecuritiesAmount1 = 0; //交割单资金拆入\出累计金额
		double pledgeSecuritiesAmount2 = 0; //交割单资金拆入\出返款累计金额
		double registeredCapital = 0;
		double oldPledgeSecuritiesAmount = 0;
		long financeID =-1;//财务公司ID 
		try
		{
			log4j.print("检查资金拆借授信额度开始");
			con = Database.getConnection();
			if (lApplyID > 0)
			{
				strSQL.append(" select * ");
				strSQL.append(" from SEC_ApplyForm ");
				strSQL.append(" where ID = " + lApplyID);
				log4j.debug(strSQL.toString());
				ps = con.prepareStatement(strSQL.toString());
				rs = ps.executeQuery();
				if (rs != null && rs.next())
				{
					//资金拆入\出
					oldPledgeSecuritiesAmount = rs.getDouble("PledgeSecuritiesAmount");
				}
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
				strSQL.setLength(0);
			}
			
			/***************/
			/*拆出/拆入授信*/
			/***************/
			financeID = CRANameRef.getPartenerInfo(1).getClientID();
			strSQL.append("select aa.creditamount pledgeSecuritiesAmount,aa.startdate TransactionStartDate,aa.enddate TransactionEndDate,bb.code,bb.name");
			strSQL.append(" from ");
			strSQL.append(" cra_creditlimit aa,sec_counterpart bb ");
			strSQL.append(" where aa.statusid= " + CRAconstant.TransactionStatus.APPROVALED );
			strSQL.append(" and aa.STARTDATE<= To_Date('" + DataFormat.getDateString(startDate) + "','yyyy-mm-dd')");
			strSQL.append(" and aa.ENDDATE>=To_Date('" + DataFormat.getDateString(endDate) + "','yyyy-mm-dd')");
			if(transactionTypeID == SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_IN)
			{
				strSQL.append(" and aa.transactiontype ="+ CRAconstant.TransactionType.CAPITAL_IN );
				strSQL.append(" and aa.creditclientid = " + counterpartID);
				strSQL.append(" and aa.creditedclientid = " + financeID);
				strSQL.append(" and aa.creditclientid=bb.id");
			}
			
			if(transactionTypeID == SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_OUT)
			{
				strSQL.append(" and aa.transactiontype  =" +CRAconstant.TransactionType.CAPITAL_OUT);
				strSQL.append(" and aa.creditclientid = " + financeID);
				strSQL.append(" and aa.creditedclientid = " + counterpartID);
				strSQL.append(" and aa.creditedclientid=bb.id");
			}
			
			log4j.debug(strSQL.toString());
			System.out.println("资金拆借授信11111111111" + strSQL.toString());
			ps = con.prepareStatement(strSQL.toString());
			rs = ps.executeQuery();
			if (rs != null && rs.next())
			{
				counterpartCode = rs.getString("Code");
				counterpartName = rs.getString("Name");
				creditStartDate = rs.getTimestamp("TransactionStartDate");
				creditEndDate = rs.getTimestamp("TransactionEndDate");
				creditAmount = rs.getDouble("pledgeSecuritiesAmount");
				//term = rs.getLong("Term");
			}
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
			/************************/
			/*申请书：已提交，已审核*/
			/************************/
			strSQL.setLength(0);
			strSQL.append(" select sum(nvl(pledgesecuritiesamount,0)) ");
			strSQL.append(" from sec_applyform ");
			strSQL.append(" where statusid in (" + SECConstant.ApplyFormStatus.SUBMITED + "," + SECConstant.ApplyFormStatus.CHECKED + ") ");
			strSQL.append(" and transactiontypeid = " + transactionTypeID);
			strSQL.append(" and counterpartid = " + counterpartID);
			strSQL.append(" and TRANSACTIONSTARTDATE>=To_Date('" + DataFormat.getDateString(creditStartDate)+ "','yyyy-mm-dd')");
			strSQL.append(" and TRANSACTIONENDDATE<=To_Date('" + DataFormat.getDateString(creditEndDate)+ "','yyyy-mm-dd')");
			log4j.debug(strSQL.toString());
			ps = con.prepareStatement(strSQL.toString());
			rs = ps.executeQuery();
			if (rs != null && rs.next())
			{
				applyAmount1 = rs.getDouble(1);
			}
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
			/************************/
			/*申请书：已使用		*/
			/************************/
			strSQL.setLength(0);
			strSQL.append(" select sum(nvl(pledgesecuritiesamount,0)) ");
			strSQL.append(" from sec_applyform ");
			strSQL.append(" where statusid in (" + SECConstant.ApplyFormStatus.USED + ") ");
			strSQL.append(" and transactiontypeid = " + transactionTypeID);
			strSQL.append(" and counterpartid = " + counterpartID);
			strSQL.append(" and TRANSACTIONSTARTDATE>=To_Date('" + DataFormat.getDateString(creditStartDate)+ "','yyyy-mm-dd')");
			strSQL.append(" and TRANSACTIONENDDATE<=To_Date('" + DataFormat.getDateString(creditEndDate)+ "','yyyy-mm-dd')");
			log4j.debug(strSQL.toString());
			ps = con.prepareStatement(strSQL.toString());
			rs = ps.executeQuery();
			if (rs != null && rs.next())
			{
				applyAmount2 = rs.getDouble(1);
			}
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
			/******************************/
			/*已使用申请书对应的交割单金额*/
			/******************************/
			strSQL.setLength(0);
			strSQL.append(" select sum(nvl(b.pledgesecuritiesamount,0)) ");
			strSQL.append(" from sec_applyform a, sec_deliveryorder b ");
			strSQL.append(" where b.applyformid = a.id and b.statusid > 0 and a.statusid = " + SECConstant.ApplyFormStatus.USED);
			strSQL.append(" and b.transactiontypeid = " + transactionTypeID);
			strSQL.append(" and b.counterpartid = " + counterpartID);
			strSQL.append(" and a.TRANSACTIONSTARTDATE>=To_Date('" + DataFormat.getDateString(creditStartDate)+ "','yyyy-mm-dd')");
			strSQL.append(" and a.TRANSACTIONENDDATE<=To_Date('" + DataFormat.getDateString(creditEndDate)+ "','yyyy-mm-dd')");
			log4j.debug(strSQL.toString());
			System.out.println("资金拆借授信11111111111" + strSQL.toString());
			ps = con.prepareStatement(strSQL.toString());
			rs = ps.executeQuery();
			if (rs != null && rs.next())
			{
				deliverOrderAmount = rs.getDouble(1);
			}
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
			/***************/
			/*拆出/拆入余额*/
			/***************/
			strSQL.setLength(0);
			strSQL.append(" select sum(nvl(PledgeSecuritiesAmount,0)) ");
			strSQL.append(" from SEC_DeliveryOrder ");
			strSQL.append(" where StatusID > 0 ");
			if (transactionTypeID == SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_IN)
			{
				strSQL.append(" and TransactionTypeID in (");
				strSQL.append(SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_IN);
				strSQL.append(") ");
			}
			else
				if (transactionTypeID == SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_OUT)
				{
					strSQL.append(" and TransactionTypeID in (");
					strSQL.append(SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_OUT);
					strSQL.append(") ");
				}
			strSQL.append(" and TransactionDate>=To_Date('" + DataFormat.getDateString(creditStartDate)+ "','yyyy-mm-dd')");
			strSQL.append(" and MaturityDate<=To_Date('" + DataFormat.getDateString(creditEndDate)+ "','yyyy-mm-dd')");
			strSQL.append(" group by CounterpartID,TransactionTypeID ");
			strSQL.append(" having CounterpartID = " + counterpartID);
			log4j.debug(strSQL.toString());
			ps = con.prepareStatement(strSQL.toString());
			rs = ps.executeQuery();
			if (rs != null && rs.next())
			{
				//资金拆入/出
				pledgeSecuritiesAmount1 = rs.getDouble(1);
			}
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
			strSQL.setLength(0);
			strSQL.append(" select sum(nvl(PledgeSecuritiesAmount,0)) ");
			strSQL.append(" from SEC_DeliveryOrder ");
			strSQL.append(" where StatusID > 0 ");
			if (transactionTypeID == SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_IN)
			{
				strSQL.append(" and TransactionTypeID in (");
				strSQL.append(SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_IN_REPAY);
				strSQL.append(") ");
			}
			else
				if (transactionTypeID == SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_OUT)
				{
					strSQL.append(" and TransactionTypeID in (");
					strSQL.append(SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_OUT_REPAY);
					strSQL.append(") ");
				}
			strSQL.append(" and TransactionDate>=To_Date('" + DataFormat.getDateString(creditStartDate)+ "','yyyy-mm-dd')");
			strSQL.append(" and MaturityDate<=To_Date('" + DataFormat.getDateString(creditEndDate)+ "','yyyy-mm-dd')");
			strSQL.append(" group by CounterpartID,TransactionTypeID ");
			strSQL.append(" having CounterpartID = " + counterpartID);
			log4j.debug(strSQL.toString());
			ps = con.prepareStatement(strSQL.toString());
			rs = ps.executeQuery();
			if (rs != null && rs.next())
			{
				//资金拆入/出返款
				pledgeSecuritiesAmount2 = rs.getDouble(1);
			}
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
			sumCreditAmount1 = applyAmount1 + applyAmount2 - deliverOrderAmount;
			if (newPledgeSecuritiesAmount > creditAmount - sumCreditAmount1 - (pledgeSecuritiesAmount1 - pledgeSecuritiesAmount2) + oldPledgeSecuritiesAmount)				//if (newPledgeSecuritiesAmount > creditAmount - sumCreditAmount1 + pledgeSecuritiesAmount2 + oldPledgeSecuritiesAmount)
			{
				strResult =
					"本次申请金额"+DataFormat.formatDisabledAmount(newPledgeSecuritiesAmount,2)+
					     "万元已超出"
						+ DataFormat.formatString(counterpartName)
						+ "交易对手"
						+ DataFormat.formatDisabledAmount(creditAmount, 2)
						+ "万元的授信额度，超出金额为"
						+ DataFormat.formatDisabledAmount(
							newPledgeSecuritiesAmount + sumCreditAmount1 + (pledgeSecuritiesAmount1 - pledgeSecuritiesAmount2) - creditAmount - oldPledgeSecuritiesAmount,
							2)
						+ "万元；目前已用授信额度为"
						+ DataFormat.formatDisabledAmount(sumCreditAmount1 + (pledgeSecuritiesAmount1 - pledgeSecuritiesAmount2) - oldPledgeSecuritiesAmount, 2)
						+ "万元。";
						//，已申请金额为"
						//+ DataFormat.formatDisabledAmount(sumCreditAmount1, 2)
						//+ "万元。";
			}
			else
			{
				strResult = "";
			}
			log4j.print(strResult);
			log4j.print("检查资金拆借授信额度结束");
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return strResult;
	}
	
	/**
	 * 2009-04-24 去掉无关参数，申请起始日期所在的授信区间，取结束日期最远的作为当前“授信区间”
	 * @param clientID
	 * @param counterpartID
	 * @param transactionTypeID
	 * @param newPledgeSecuritiesAmount
	 * @param lApplyID
	 * @param startDate
	 * @param endDate
	 * @param officeID
	 * @return
	 * @throws SecuritiesDAOException
	 */
	public long findByCreditID(long clientID, long counterpartID, long transactionTypeID, double newPledgeSecuritiesAmount, long lApplyID,Timestamp startDate,Timestamp endDate,long officeID) throws SecuritiesDAOException
	{
		StringBuffer strSQL = new StringBuffer();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		long creditId = -1;
		long financeID =-1;//财务公司ID 
		try
		{
			log4j.print("检查资金拆借授信额度开始");
			con = Database.getConnection();
			
			/***************/
			/*拆出/拆入授信*/
			/***************/
			financeID = CRANameRef.getPartenerInfo(officeID).getClientID();
			strSQL.append("select aa.id,aa.creditamount pledgeSecuritiesAmount,aa.startdate TransactionStartDate,aa.enddate TransactionEndDate,bb.code,bb.name");
			strSQL.append(" from ");
			strSQL.append(" cra_creditlimit aa,sec_counterpart bb ");
			strSQL.append(" where aa.statusid in(" + CRAconstant.TransactionStatus.APPROVALED+","+CRAconstant.TransactionStatus.USED+")");
			strSQL.append(" and aa.STARTDATE<= To_Date('" + DataFormat.getDateString(startDate) + "','yyyy-mm-dd')");
			strSQL.append(" and aa.ENDDATE>=To_Date('" + DataFormat.getDateString(endDate) + "','yyyy-mm-dd')");
			if(transactionTypeID == SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_IN)
			{
				strSQL.append(" and aa.transactiontype ="+ CRAconstant.TransactionType.CAPITAL_IN );
				strSQL.append(" and aa.creditclientid = " + counterpartID);
				strSQL.append(" and aa.creditedclientid = " + financeID);
				strSQL.append(" and aa.creditclientid=bb.id");
			}
			
			if(transactionTypeID == SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_OUT)
			{
				strSQL.append(" and aa.transactiontype  =" +CRAconstant.TransactionType.CAPITAL_OUT);
				strSQL.append(" and aa.creditclientid = " + financeID);
				strSQL.append(" and aa.creditedclientid = " + counterpartID);
				strSQL.append(" and aa.creditedclientid=bb.id");
			}
			//取授信结束日期最远的区间作为当前授信
			strSQL.append(" ORDER BY aa.ENDDATE DESC ");
			
			log4j.debug(strSQL.toString());
			System.out.println("资金拆借授信11111111111" + strSQL.toString());
			ps = con.prepareStatement(strSQL.toString());
			rs = ps.executeQuery();
			if (rs != null && rs.next())
			{
				creditId = rs.getLong("id");
			}
			if (rs != null)
			{
				rs.close();
				rs = null;
			}
			if (ps != null)
			{
				ps.close();
				ps = null;
			}if(con != null){
				con.close();
				con=null;
			}
		   }
			catch (SQLException ex)
			{
				ex.printStackTrace();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			return creditId;
		}

	
	/**
	 * 检查资金拆借授信额度
	 * @param counterpartID	交易对手
	 * @param transactionTypeID 交易类型
	 * @param newPledgeSecuritiesAmount 拆借金额
	 * @param lApplyID 申请书标示
	 * @param officeid 办事处标示
	 * @return
	 * @throws SecuritiesDAOException
	 */
	public String checkCapitalLandingCreditExtension(long clientID, long counterpartID, long transactionTypeID, double newPledgeSecuritiesAmount, long lApplyID,Timestamp startDate,Timestamp endDate,long officeID) throws SecuritiesDAOException
	{
		StringBuffer strSQL = new StringBuffer();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String strResult = "";
		String counterpartCode = "";
		String counterpartName = "";
		Timestamp creditStartDate = null;
		Timestamp creditEndDate = null;
		long term = 0;
		double creditAmount = 0;
		double applyAmount1 = 0; //申请书金额（已提交，已审核）
		double applyAmount2 = 0; //申请书金额（已使用）
		double deliverOrderAmount = 0; //已使用申请书对应的交割单金额
		double sumCreditAmount1 = 0; //申请书按交易对手、交易类型区分的累计金额
		double sumCreditAmount2 = 0; //申请书按交易类型区分的累计金额
		double pledgeSecuritiesAmount1 = 0; //交割单资金拆入\出累计金额
		double pledgeSecuritiesAmount2 = 0; //交割单资金拆入\出返款累计金额
		double registeredCapital = 0;
		double oldPledgeSecuritiesAmount = 0;
		long financeID =-1;//财务公司ID 
		try
		{
			log4j.print("检查资金拆借授信额度开始");
			con = Database.getConnection();
			if (lApplyID > 0)
			{
				strSQL.append(" select * ");
				strSQL.append(" from SEC_ApplyForm ");
				strSQL.append(" where ID = " + lApplyID);
				log4j.debug(strSQL.toString());
				ps = con.prepareStatement(strSQL.toString());
				rs = ps.executeQuery();
				if (rs != null && rs.next())
				{
					//资金拆入\出
					oldPledgeSecuritiesAmount = rs.getDouble("PledgeSecuritiesAmount");
				}
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
				strSQL.setLength(0);
			}
			
			/***************/
			/*拆出/拆入授信*/
			/***************/
			financeID = CRANameRef.getPartenerInfo(officeID).getClientID();
			strSQL.append("select aa.creditamount pledgeSecuritiesAmount,aa.startdate TransactionStartDate,aa.enddate TransactionEndDate,bb.code,bb.name");
			strSQL.append(" from ");
			strSQL.append(" cra_creditlimit aa,sec_counterpart bb ");
			strSQL.append(" where aa.statusid in(" + CRAconstant.TransactionStatus.APPROVALED+","+CRAconstant.TransactionStatus.USED+")");
			strSQL.append(" and aa.STARTDATE<= To_Date('" + DataFormat.getDateString(startDate) + "','yyyy-mm-dd')");
			strSQL.append(" and aa.ENDDATE>=To_Date('" + DataFormat.getDateString(startDate) + "','yyyy-mm-dd')");
			if(transactionTypeID == SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_IN)
			{
				strSQL.append(" and aa.transactiontype ="+ CRAconstant.TransactionType.CAPITAL_IN );
				strSQL.append(" and aa.creditclientid = " + counterpartID);
				strSQL.append(" and aa.creditedclientid = " + financeID);
				strSQL.append(" and aa.creditclientid=bb.id");
			}
			
			if(transactionTypeID == SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_OUT)
			{
				strSQL.append(" and aa.transactiontype  =" +CRAconstant.TransactionType.CAPITAL_OUT);
				strSQL.append(" and aa.creditclientid = " + financeID);
				strSQL.append(" and aa.creditedclientid = " + counterpartID);
				strSQL.append(" and aa.creditedclientid=bb.id");
			}
			strSQL.append(" order by aa.ENDDATE desc ");
			
			log4j.debug(strSQL.toString());
			System.out.println("资金拆借授信11111111111" + strSQL.toString());
			ps = con.prepareStatement(strSQL.toString());
			rs = ps.executeQuery();
			if (rs != null && rs.next())
			{
				counterpartCode = rs.getString("Code");
				counterpartName = rs.getString("Name");
				creditStartDate = rs.getTimestamp("TransactionStartDate");
				creditEndDate = rs.getTimestamp("TransactionEndDate");
				creditAmount = rs.getDouble("pledgeSecuritiesAmount");
				//term = rs.getLong("Term");
			}
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
			/************************/
			/*申请书：已提交，审批中，已审核*/
			/************************/
			strSQL.setLength(0);
			strSQL.append(" select sum(nvl(pledgesecuritiesamount,0)) ");
			strSQL.append(" from sec_applyform ");
			strSQL.append(" where statusid in (" + SECConstant.ApplyFormStatus.SUBMITED + "," + SECConstant.ApplyFormStatus.CHECKED + "," + SECConstant.ApplyFormStatus.APPROVALING + ") ");
			strSQL.append(" and transactiontypeid = " + transactionTypeID);
			strSQL.append(" and counterpartid = " + counterpartID);
			strSQL.append(" and TRANSACTIONSTARTDATE >= To_Date('" + DataFormat.getDateString(creditStartDate)+ "','yyyy-mm-dd')");
			//只判断业务开始日期在授信区间 modified by qhzhou 2009-04-24
			//strSQL.append(" and TRANSACTIONENDDATE<=To_Date('" + DataFormat.getDateString(creditEndDate)+ "','yyyy-mm-dd')");
			strSQL.append(" and TRANSACTIONSTARTDATE <= To_Date('" + DataFormat.getDateString(creditEndDate)+ "','yyyy-mm-dd')");
			log4j.debug(strSQL.toString());
			ps = con.prepareStatement(strSQL.toString());
			rs = ps.executeQuery();
			if (rs != null && rs.next())
			{
				applyAmount1 = rs.getDouble(1);
			}
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
			/************************/
			/*申请书：已使用		*/
			/************************/
			strSQL.setLength(0);
			strSQL.append(" select sum(nvl(pledgesecuritiesamount,0)) ");
			strSQL.append(" from sec_applyform ");
			strSQL.append(" where statusid in (" + SECConstant.ApplyFormStatus.USED + ") ");
			strSQL.append(" and transactiontypeid = " + transactionTypeID);
			strSQL.append(" and counterpartid = " + counterpartID);
			
			strSQL.append(" and TRANSACTIONSTARTDATE >= To_Date('" + DataFormat.getDateString(creditStartDate)+ "','yyyy-mm-dd')");
			//只判断业务开始日期在授信区间 modified by qhzhou 2009-04-24
			//strSQL.append(" and TRANSACTIONENDDATE<=To_Date('" + DataFormat.getDateString(creditEndDate)+ "','yyyy-mm-dd')");
			strSQL.append(" and TRANSACTIONSTARTDATE <=To_Date('" + DataFormat.getDateString(creditEndDate)+ "','yyyy-mm-dd')");
			log4j.debug(strSQL.toString());
			ps = con.prepareStatement(strSQL.toString());
			rs = ps.executeQuery();
			if (rs != null && rs.next())
			{
				applyAmount2 = rs.getDouble(1);
			}
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
			/******************************/
			/*已使用申请书对应的交割单金额*/
			/******************************/
			strSQL.setLength(0);
			strSQL.append(" select sum(nvl(b.pledgesecuritiesamount,0)) ");
			strSQL.append(" from sec_applyform a, sec_deliveryorder b ");
			strSQL.append(" where b.applyformid = a.id and b.statusid > 0 and a.statusid = " + SECConstant.ApplyFormStatus.USED);
			strSQL.append(" and b.transactiontypeid = " + transactionTypeID);
			strSQL.append(" and b.counterpartid = " + counterpartID);
			strSQL.append(" and a.TRANSACTIONSTARTDATE>=To_Date('" + DataFormat.getDateString(creditStartDate)+ "','yyyy-mm-dd')");
			//只判断业务开始日期在授信区间 modified by qhzhou 2009-04-24
			//strSQL.append(" and a.TRANSACTIONENDDATE<=To_Date('" + DataFormat.getDateString(creditEndDate)+ "','yyyy-mm-dd')");
			strSQL.append(" and a.TRANSACTIONSTARTDATE<=To_Date('" + DataFormat.getDateString(creditEndDate)+ "','yyyy-mm-dd')");
			log4j.debug(strSQL.toString());
			System.out.println("资金拆借授信11111111111" + strSQL.toString());
			ps = con.prepareStatement(strSQL.toString());
			rs = ps.executeQuery();
			if (rs != null && rs.next())
			{
				deliverOrderAmount = rs.getDouble(1);
			}
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
			/***************/
			/*拆出/拆入余额*/
			/***************/
			strSQL.setLength(0);
			strSQL.append(" select sum(nvl(PledgeSecuritiesAmount,0)) ");
			strSQL.append(" from SEC_DeliveryOrder ");
			strSQL.append(" where StatusID > 0 ");
			if (transactionTypeID == SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_IN)
			{
				strSQL.append(" and TransactionTypeID in (");
				strSQL.append(SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_IN);
				strSQL.append(") ");
			}
			else
				if (transactionTypeID == SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_OUT)
				{
					strSQL.append(" and TransactionTypeID in (");
					strSQL.append(SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_OUT);
					strSQL.append(") ");
				}
			strSQL.append(" and TransactionDate>=To_Date('" + DataFormat.getDateString(creditStartDate)+ "','yyyy-mm-dd')");
			strSQL.append(" and MaturityDate<=To_Date('" + DataFormat.getDateString(creditEndDate)+ "','yyyy-mm-dd')");
			strSQL.append(" group by CounterpartID,TransactionTypeID ");
			strSQL.append(" having CounterpartID = " + counterpartID);
			log4j.debug(strSQL.toString());
			ps = con.prepareStatement(strSQL.toString());
			rs = ps.executeQuery();
			if (rs != null && rs.next())
			{
				//资金拆入/出
				pledgeSecuritiesAmount1 = rs.getDouble(1);
			}
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
			strSQL.setLength(0);
			strSQL.append(" select sum(nvl(PledgeSecuritiesAmount,0)) ");
			strSQL.append(" from SEC_DeliveryOrder ");
			strSQL.append(" where StatusID > 0 ");
			if (transactionTypeID == SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_IN)
			{
				strSQL.append(" and TransactionTypeID in (");
				strSQL.append(SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_IN_REPAY);
				strSQL.append(") ");
			}
			else
				if (transactionTypeID == SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_OUT)
				{
					strSQL.append(" and TransactionTypeID in (");
					strSQL.append(SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_OUT_REPAY);
					strSQL.append(") ");
				}
			strSQL.append(" and TransactionDate>=To_Date('" + DataFormat.getDateString(creditStartDate)+ "','yyyy-mm-dd')");
			strSQL.append(" and MaturityDate<=To_Date('" + DataFormat.getDateString(creditEndDate)+ "','yyyy-mm-dd')");
			strSQL.append(" group by CounterpartID,TransactionTypeID ");
			strSQL.append(" having CounterpartID = " + counterpartID);
			log4j.debug(strSQL.toString());
			ps = con.prepareStatement(strSQL.toString());
			rs = ps.executeQuery();
			if (rs != null && rs.next())
			{
				//资金拆入/出返款
				pledgeSecuritiesAmount2 = rs.getDouble(1);
			}
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
			sumCreditAmount1 = applyAmount1 + applyAmount2 - deliverOrderAmount;
			if (newPledgeSecuritiesAmount > creditAmount - sumCreditAmount1 - (pledgeSecuritiesAmount1 - pledgeSecuritiesAmount2) + oldPledgeSecuritiesAmount)				//if (newPledgeSecuritiesAmount > creditAmount - sumCreditAmount1 + pledgeSecuritiesAmount2 + oldPledgeSecuritiesAmount)
			{
				strResult =
					"本次申请金额"+DataFormat.formatDisabledAmount(newPledgeSecuritiesAmount,2)+
					     "万元已超出"
						+ DataFormat.formatString(counterpartName)
						+ "交易对手"
						+ DataFormat.formatDisabledAmount(creditAmount, 2)
						+ "万元的授信额度，超出金额为"
						+ DataFormat.formatDisabledAmount(
							newPledgeSecuritiesAmount + sumCreditAmount1 + (pledgeSecuritiesAmount1 - pledgeSecuritiesAmount2) - creditAmount - oldPledgeSecuritiesAmount,
							2)
						+ "万元；目前已用授信额度为"
						+ DataFormat.formatDisabledAmount(sumCreditAmount1 + (pledgeSecuritiesAmount1 - pledgeSecuritiesAmount2) - oldPledgeSecuritiesAmount, 2)
						+ "万元。";
						//，已申请金额为"
						//+ DataFormat.formatDisabledAmount(sumCreditAmount1, 2)
						//+ "万元。";
			}
			else
			{
				strResult = "";
			}
			log4j.print(strResult);
			log4j.print("检查资金拆借授信额度结束");
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return strResult;
	}
	
	public boolean checkTransactionTypeID(long lTransactionTypeID)
	{
		boolean bResult = false;
		switch ((int) lTransactionTypeID)
		{
			//1)	股票一级网上申购：股票首发网上申购、股票增发网上申购；
			case (int) SECConstant.BusinessType.STOCK_BID_ONLINE.INITIAL_OFFERING_BID_ONLINE :
				bResult = true;
				break;
			case (int) SECConstant.BusinessType.STOCK_BID_ONLINE.PROMOTION_BID_ONLINE :
				bResult = true;
				break;
				//2)	股票一级网下申购：股票首发网下申购、股票增发网下申购；
			case (int) SECConstant.BusinessType.STOCK_BID.INITIAL_OFFERING_BID :
				bResult = true;
				break;
			case (int) SECConstant.BusinessType.STOCK_BID.PROMOTION_BID :
				bResult = true;
				break;
				//3)	股票二级：股票买入、网上配股、网下配股；
			case (int) SECConstant.BusinessType.STOCK_TRANSACTION.STOCK_BUYIN :
				bResult = true;
				break;
			case (int) SECConstant.BusinessType.STOCK_TRANSACTION.STOCK_RATION_ONLINE :
				bResult = true;
				break;
			case (int) SECConstant.BusinessType.STOCK_TRANSACTION.STOCK_RATION :
				bResult = true;
				break;
				//4)	央行票据一级：央行票据申购；
			case (int) SECConstant.BusinessType.CENTRAL_BANK_NOTE_BID.NOTE_BID :
				bResult = true;
				break;
				//5)	央行票据二级：央行票据买入；
			case (int) SECConstant.BusinessType.CENTRAL_BANK_NOTE_TRANSACTION.NOTE_BUYIN :
				bResult = true;
				break;
				//6)	银行间国债一级：银行间国债申购；
			case (int) SECConstant.BusinessType.BANK_NATIONAL_BOND_BID.BOND_BID :
				bResult = true;
				break;
				//7)	银行间国债二级：银行间国债买入；
			case (int) SECConstant.BusinessType.BANK_NATIONAL_BOND_TRANSACTION.BOND_BUYIN :
				bResult = true;
				break;
				//8)	交易所国债一级：交易所国债申购；
			case (int) SECConstant.BusinessType.EXCHANGECENTER_NATIONAL_BOND_BID.BOND_BID :
				bResult = true;
				break;
				//9)	交易所国债二级：交易所国债买入；
			case (int) SECConstant.BusinessType.EXCHANGECENTER_NATIONAL_BOND_TRANSACTION.BOND_BUYIN :
				bResult = true;
				break;
				//10)	金融债一级：金融债申购；
			case (int) SECConstant.BusinessType.FINANCIAL_BOND_BID.BOND_BID :
				bResult = true;
				break;
				//11)	金融债二级：金融债买入；
			case (int) SECConstant.BusinessType.FINANCIAL_BOND_TRANSACTION.BOND_BUYIN :
				bResult = true;
				break;
				//12)	政策性金融债一级：政策性金融债申购；
			case (int) SECConstant.BusinessType.POLICY_RELATED_FINANCIAL_BOND_BID.BOND_BID :
				bResult = true;
				break;
				//13)	政策性金融债二级：政策性金融债买入；
			case (int) SECConstant.BusinessType.POLICY_RELATED_FINANCIAL_BOND_TRANSACTION.BOND_BUYIN :
				bResult = true;
				break;
				//14)	企业债一级：企业债申购；
			case (int) SECConstant.BusinessType.ENTERPRISE_BOND_BID.BOND_BID :
				bResult = true;
				break;
				//15)	企业债二级：企业债买入；
			case (int) SECConstant.BusinessType.ENTERPRISE_BOND_TRANSACTION.BOND_BUYIN :
				bResult = true;
				break;
				//16)	可转债一级网上申购：可转债网上申购；
			case (int) SECConstant.BusinessType.TRANSFORMABLE_BOND_BID_ONLINE.BOND_BID_ONLINE :
				bResult = true;
				break;
				//17)	可转债一级网下申购：可转债网下申购；
			case (int) SECConstant.BusinessType.TRANSFORMABLE_BOND_BID.BOND_BID :
				bResult = true;
				break;
				//18)	可转债二级：可转债买入；
			case (int) SECConstant.BusinessType.TRANSFORMABLE_BOND_TRANSACTION.BOND_BUYIN :
				bResult = true;
				break;
				//19)	债转股：债转股；
			case (int) SECConstant.BusinessType.DEBT_TO_EQUITY.DEBT_TO_EQUITY :
				bResult = true;
				break;
				//20)	封闭式基金一级网上申购：封闭式基金网上申购；
			case (int) SECConstant.BusinessType.ENCLOSED_FUND_BID_ONLINE.FUND_BID_ONLINE :
				bResult = true;
				break;
				//21)	封闭式基金一级网下申购：封闭式基金网下申购；
			case (int) SECConstant.BusinessType.ENCLOSED_FUND_BID.FUND_BID :
				bResult = true;
				break;
				//22)	封闭式基金二级：封闭式基金买入；
			case (int) SECConstant.BusinessType.ENCLOSED_FUND_TRANSACTION.FUND_BUYIN :
				bResult = true;
				break;
				//23)	开放式基金一级认购：开放式基金一级认购；
			case (int) SECConstant.BusinessType.MUTUAL_FUND_SUBSCRIBE.FUND_SUBSCRIBE :
				bResult = true;
				break;
				//24)	开放式基金二级申购：开放式基金二级申购。
			case (int) SECConstant.BusinessType.MUTUAL_FUND_BID.FUND_BID :
				bResult = true;
				break;
			default :
				bResult = false;
				break;
		}
		return bResult;
	}
	/**
	 * 检查申请书授信额度
	 * @param counterpartID	交易对手
	 * @param transactionTypeID 交易类型
	 * @param newPledgeSecuritiesAmount 拆借金额
	 * @param lApplyID 申请书标示
	 * @return
	 * @throws SecuritiesDAOException
	 */
	public String checkApplyCreditExtension(long securitiesID, long securitiesTypeID, double applyAmount) throws SecuritiesDAOException, RemoteException
	{
		StringBuffer strSQL = new StringBuffer();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String strResult = "";
		DeliveryOrderServiceBiz dso = new DeliveryOrderServiceBiz();
		UsableCreditLineOfSecuritiesInfo creditInfo1 = new UsableCreditLineOfSecuritiesInfo();
		UsableCreditLineOfSecuritiesInfo creditInfo2 = new UsableCreditLineOfSecuritiesInfo();
		try
		{
			Log.print("SEC_ApplyDAO检查申请书授信额度开始");
			con = Database.getConnection();
			//申请额度检查
			Log.print("申请额度检查");
			if (securitiesID > 0)
			{
				creditInfo1 = dso.getUsableCreditLineOfSingleSecurities(securitiesID, -1);
			}
			if (securitiesTypeID > 0)
			{
				creditInfo2 = dso.getUsableCreditLineOfSingleSecurities(-1, securitiesTypeID);
			}
			if (creditInfo1 != null && applyAmount > (creditInfo1.getAmount() + applyAmount))
			{
				strResult =
					"<font color=red>"
						+ "本申请已超出"
						+ NameRef.getSecuritiesCodeByID(securitiesID)
						+ "证券代码"
						+ DataFormat.formatDisabledAmount(creditInfo1.getCreditLine(), 2)
						+ "元的总授信额度，超出金额为"
						+ DataFormat.formatDisabledAmount(applyAmount - (creditInfo1.getAmount() + applyAmount), 2)
						+ "元；目前库存为"
						+ DataFormat.formatDisabledAmount(creditInfo1.getCost(), 2)
						+ "元，申请金额为"
						+ DataFormat.formatDisabledAmount(creditInfo1.getAmountOfApprovedAndUsing() + creditInfo1.getAmountOfUsed() - applyAmount, 2)
						+ "元。"
						+ "</font>";
			}
			else
				if (creditInfo2 != null && applyAmount > (creditInfo2.getAmount() + applyAmount))
				{
					strResult =
						"<font color=red>"
							+ "本申请已超出"
							+ NameRef.getSecuritiesTypeNameByID(securitiesTypeID)
							+ "证券类别"
							+ DataFormat.formatDisabledAmount(creditInfo2.getCreditLine(), 2)
							+ "元的总授信额度，超出金额为"
							+ DataFormat.formatDisabledAmount(applyAmount - (creditInfo2.getAmount() + applyAmount), 2)
							+ "元；目前库存为"
							+ DataFormat.formatDisabledAmount(creditInfo2.getCost(), 2)
							+ "元，申请金额为"
							+ DataFormat.formatDisabledAmount(creditInfo2.getAmountOfApprovedAndUsing() + creditInfo2.getAmountOfUsed() - applyAmount, 2)
							+ "元。"
							+ "</font>";
				}
				else
				{
					strResult =
						NameRef.getSecuritiesCodeByID(securitiesID)
							+ "证券代码总授信额度为"
							+ DataFormat.formatDisabledAmount(creditInfo1.getCreditLine(), 2)
							+ "元；目前库存为"
							+ DataFormat.formatDisabledAmount(creditInfo1.getCost(), 2)
							+ "元，申请金额为"
							+ DataFormat.formatDisabledAmount(creditInfo1.getAmountOfApprovedAndUsing() + creditInfo1.getAmountOfUsed() - applyAmount, 2)
							+ "元。　"
							+ NameRef.getSecuritiesTypeNameByID(securitiesTypeID)
							+ "证券类别总授信额度为"
							+ DataFormat.formatDisabledAmount(creditInfo2.getCreditLine(), 2)
							+ "元；目前库存为"
							+ DataFormat.formatDisabledAmount(creditInfo2.getCost(), 2)
							+ "元，申请金额为"
							+ DataFormat.formatDisabledAmount(creditInfo2.getAmountOfApprovedAndUsing() + creditInfo2.getAmountOfUsed() - applyAmount, 2)
							+ "元。";
				}
			Log.print(strResult);
			Log.print("SEC_ApplyDAO检查申请书授信额度结束");
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return strResult;
	}
	
	/**
	 * 方法说明：修改状态
	 * @param StatusID
	 * @return long  
	 * @throws IException
	 */
	public long updateStatusID(long lID, long StatusID) throws Exception
	{
		long lReturn = -1;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			con = Database.getConnection();
			StringBuffer strSQLBuffer = new StringBuffer();
			strSQLBuffer.append("UPDATE SEC_ApplyForm ");
			strSQLBuffer.append(" SET StatusID = "+StatusID+", updatedate = sysdate WHERE ID = "+lID+" ");
			//String strSQL = strSQLBuffer.toString();
			//log.info(strSQLBuffer.toString());
			System.out.println("sql========"+strSQLBuffer.toString());
			ps = con.prepareStatement(strSQLBuffer.toString());
			//ps.setLong(1, StatusID);
			//ps.setLong(2, lID);
			ps.executeUpdate();
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
		finally
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
		return lID;
	}
	/**
	 * Added by xwhe, 2007/10/17
	 * 检查合同是否已被使用
	 * @param applyId 申请单id
	 * @return
	 */
	public boolean checkStatuID(long applyId) throws LoanDAOException{
		boolean flag=false;
		long[] checkStatusId={
				//SECConstant.ContractStatus.SAVE,
				//SECConstant.DeliveryOrderStatus.SAVED,
				SECConstant.DeliveryOrderStatus.CHECKED,
				SECConstant.DeliveryOrderStatus.USED,
				SECConstant.DeliveryOrderStatus.POSTED,
				};
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append( "select count(*) from sec_deliveryorder s");
		sqlBuf.append(" where s.statusid in (");
		for(int i=0;i<checkStatusId.length;i++){
			sqlBuf.append(checkStatusId[i]);
			if(i!=(checkStatusId.length-1)){
				sqlBuf.append(",");
			}
		}
		sqlBuf.append(")");
		sqlBuf.append(" and s.applyformid = " + applyId);
		try
		{
			initDAO();
			prepareStatement(sqlBuf.toString());
			ResultSet rs = executeQuery();
			try
			{
				if(rs != null && rs.next())
				{
					long temp=rs.getLong(1);
					if(temp>0){
						flag=true;
					}
				}
			}
			catch (SQLException e1)
			{
				e1.printStackTrace();
			}
		}
		catch (ITreasuryDAOException e)
		{
			throw new LoanDAOException(e);
		}finally{
			try
			{
				finalizeDAO();
			}
			catch (ITreasuryDAOException e)
			{
				throw new LoanDAOException(e);
			}
		}
		return flag;		
	}
	/**
	 * Added by xwhe, 2007/10/17
	 * 检查合同是否已被使用
	 * @param applyId 申请单id
	 * @return
	 */
	public boolean canclediliver(long applyId) throws LoanDAOException{
		boolean flag=false;
		long[] checkStatusId={
				SECConstant.DeliveryOrderStatus.SAVED,
				};
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append( "update  sec_deliveryorder s");
		sqlBuf.append(" set s.statusid = "+SECConstant.DeliveryOrderStatus.DELETED);
		sqlBuf.append(" where s.statusid in (");
		for(int i=0;i<checkStatusId.length;i++){
			sqlBuf.append(checkStatusId[i]);
			if(i!=(checkStatusId.length-1)){
				sqlBuf.append(",");
			}
		}
		sqlBuf.append(")");
		sqlBuf.append(" and s.applyformid = " + applyId);
		try
		{
			initDAO();
			prepareStatement(sqlBuf.toString());
			ResultSet rs = executeQuery();
			try
			{
				if(rs != null && rs.next())
				{
					long temp=rs.getLong(1);
					if(temp>0){
						flag=true;
					}
				}
			}
			catch (SQLException e1)
			{
				e1.printStackTrace();
			}
		}
		catch (ITreasuryDAOException e)
		{
			throw new LoanDAOException(e);
		}finally{
			try
			{
				finalizeDAO();
			}
			catch (ITreasuryDAOException e)
			{
				throw new LoanDAOException(e);
			}
		}
		return flag;		
	}
	/**
     *申请书的单笔查询操作
    */
    public ApplyInfo findByCreditID(long lID) throws LoanDAOException
    {
    	ApplyInfo info = new ApplyInfo();
        ResultSet rs = null;
        String strSQL = "";

        try
        {
            initDAO();           
            strSQL = " select * from sec_applyform " 
                + " where creditID=" + lID+"and statusid!= "+SECConstant.ApplyFormStatus.CANCELED;
            System.out.println(strSQL);
            Log.print(strSQL);
            prepareStatement(strSQL);
            rs = executeQuery();
            
            if (rs != null && rs.next())
            {
                
                info.setId(lID); //申请标识
              
                info.setCreditId(rs.getLong("CREDITID"));
            }
            if (rs != null)
            {
                rs.close();
                rs = null;
            }
            
            finalizeDAO();
            
        }
        catch (ITreasuryDAOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (SQLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally
        {
            try
            {
                finalizeDAO();
            } 
            catch (ITreasuryDAOException e1)
            {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
        return info;
    }
}
