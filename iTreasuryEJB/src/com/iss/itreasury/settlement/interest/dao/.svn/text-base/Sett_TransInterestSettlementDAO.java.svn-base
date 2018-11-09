/*
 * Created on 2003-10-28
 *
 * InterestQueryResultInfo.java
 */
package com.iss.itreasury.settlement.interest.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;

import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.loan.discount.dataentity.DiscountBillInfo;
import com.iss.itreasury.settlement.interest.dataentity.InterestQueryInfo;
import com.iss.itreasury.settlement.interest.dataentity.InterestQueryResultInfo;
import com.iss.itreasury.settlement.interest.dataentity.InterestSettmentInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.settlement.util.UtilOperation;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.Log4j;

/**
 * @author xiren li
 *
 * 利息费用结算结息处理数据访问对象，主要实现的功能包括：
 * 1.查询结息记录。
 * 2.计提利息。
 * 3.冲销计提利息。
 * 4.结算利息。
 * 
 * 注意事项:为了使该数据访问对象对容器的事务和非容器的事务都提供支持，
 *          不在每个方法内部取得数据库的连接，而是由外界传入，由调用
 *          该对象方法的外界对象来维护事务。鉴于此，该类不必继承
 *          于SettlementDAO.
 */

public class Sett_TransInterestSettlementDAO extends SettlementDAO
{

	/**Logger instance*/
	private Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);

	/**
	 * 从结果集向数据实体赋值(全部)
	 */
	private Vector fetchDataFromRS(ResultSet rs) throws Exception
	{
		Vector rstVec = new Vector();
		//int i = 0; //lhj debug data
		while (rs.next())
		{
			InterestQueryResultInfo info = new InterestQueryResultInfo();
			info.setAccountNo(rs.getString("accountNo"));
			info.setAccountTypeID(rs.getLong("accountTypeID"));
			info.setAccountID(rs.getLong("accountID"));
			info.setSubAccountID(rs.getLong("subAccountID"));
			info.setFixedDepositNo(rs.getString("fixedDepositNo"));
			info.setContractNo(rs.getString("contractNo"));	
			info.setPayFormNo(rs.getString("payFormNo"));
			info.setPayFormID(rs.getLong("payFormID"));  //ADD BY ZCWANG 2007-6-23
			info.setLoanPreDrawInterest(rs.getDouble("loanPredrawInterest"));
			info.setInterestRate(rs.getDouble("discountRate"));
			info.setEndDate(rs.getTimestamp("enddate"));
			
			//合同ID
			info.setContractID(rs.getLong("contractID"));
			//贴现凭证ID
			info.setDiscreID(rs.getLong("discreID"));
			
			//活期计提利息
			//info.setDrawingInterest(rs.getDouble("drawingInterest"));
			
			rstVec.addElement(info);
			//
			//			i++; //lhj debug data
			//			log.info("lhj debug info ==[" + i + "]==InterestQueryResultInfo赋值开始......");
			//			log.info("lhj debug info ==[" + i + "]==accountTypeID==" + rs.getLong("accountTypeID"));
			//			log.info("lhj debug info ==[" + i + "]==accountID==" + rs.getLong("accountID"));
			//			log.info("lhj debug info ==[" + i + "]==subAccountID==" + rs.getLong("subAccountID"));
			//			log.info("lhj debug info ==[" + i + "]==定期单据号==" + rs.getString("fixedDepositNo"));
			//			log.info("lhj debug info ==[" + i + "]==合同号==" + rs.getString("contractNo"));
			//			log.info("lhj debug info ==[" + i + "]==放款通知单==" + rs.getString("payFormNo"));
			//			log.info("lhj debug info ==[" + i + "]==InterestQueryResultInfo赋值结束......");
			//
		}
		return rstVec;
	}

	/**
	 * 根据条件进行结息记录的查询(所有类型的账户包括活期、定期和贷款)。
	 * @param con 数据库连接，由外部传入，never null.
	 * @param queryInfo 包含查询条件的实体，never null.
	 * @return InterestQueryResultInfo[] 满足条件的结息记录主体信息数组，关于利息等信息
	 *         需要调用计息接口计算得出。如果没有符合条件的结息记录，那么返回 null.
	 * @throws Excetion 任何系统异常发生时。
	 */
	public Vector selectSettlementRecords(Connection con, InterestQueryInfo queryInfo,long lInterestType) throws Exception
	{		
		StringBuffer buffer = new StringBuffer();
		Connection conInternal = null;
		if (con == null)
		{
			conInternal = getConnection();
		}
		else
		{
			conInternal = con;
		}
		String strSQL = "";
		PreparedStatement ps = null;
		ResultSet rs = null;
		Vector result = null; //返回值

		try
		{
			//构造查询语句主体
			//buffer.append(" SELECT * FROM ( SELECT rs_temp.*, rownum r FROM( ");
			buffer.append(" SELECT ");
			buffer.append(" -1 contractID, -1 discreID, ");
			buffer.append(" contractform.dtenddate enddate, -1 discountRate, account.ID AS accountID, ");
			buffer.append(" account.sAccountNo AS accountNo, "); //账户号
			buffer.append(" account.nAccountTypeID AS accountTypeID,"); //账户类型
			buffer.append(" subaccount.ID AS subAccountID, "); //子账户 ID
			buffer.append(" subaccount.AF_sDepositNo AS fixedDepositNo, "); //定期单据号
			
			//活期、保证金、委贷、自营用此字段
			buffer.append(" subaccount.AL_mPreDrawInterest AS loanPreDrawInterest, ");
			
			//通知、定期
			//buffer.append(" subaccount.AF_mPreDrawInterest AS drawingInterest, "); //活期计提利息
			
			buffer.append(" contractform.sContractCode AS contractNo, "); //合同号
			buffer.append(" payform.sCode AS payFormNo, "); //放款通知单号
			
			buffer.append(" payform.ID AS payFormID "); //放款通知单号ID  MODIFY BY ZCWANG 2007-6-23
			
			buffer.append(" FROM ");
			//modify by kenny (增加担保合同与保证金账户的关联关系)
			buffer.append(" sett_Account account, sett_SubAccount subaccount, (select id,scode,nContractID from loan_PayForm union select id,code scode,ContractID nContractID from LOAN_ASSURECHARGEFORM) payform, loan_ContractForm contractform, loan_LoanForm loanform");
			
			//Boxu Add 2010-10-29 查询条件增加账户类型
			buffer.append(" , Sett_AccountType acctype ");
			
			buffer.append(" WHERE ");
			buffer.append(" account.ID = subaccount.nAccountID ");
			
			//Boxu Add 2010-10-29 查询条件增加账户类型
			buffer.append(" and account.nAccountTypeID = acctype.id ");
			if(queryInfo.getIsCheckedType() == 1)
			{
				if(queryInfo.getLAccountTypeID1() > 0)
				{
					buffer.append(" and acctype.id = "+ queryInfo.getLAccountTypeID1() +" ");
				}
			}
			else if(queryInfo.getIsCheckedType() == 2)
			{
				if(queryInfo.getLAccountTypeID2() > 0)
				{
					buffer.append(" and acctype.naccountgroupid = "+ queryInfo.getLAccountTypeID2() +" ");
				}
			}
			
			//修改 by kenny (胡志强)(2007-04-02) 增加账户状态为[只收不付冻结、不收不付冻结、部分冻结];以下账户状态均能算息、结息
			buffer.append(" AND account.NSTATUSID in (" + 
					SETTConstant.AccountStatus.NORMAL + "," + 
					SETTConstant.AccountStatus.FREEZE + "," + 
					SETTConstant.AccountStatus.ALLFREEZE + "," + 
					SETTConstant.AccountStatus.PARTFREEZE + ")");
			
			buffer.append(" AND account.NCHECKSTATUSID = " + SETTConstant.AccountCheckStatus.CHECK);
			//buffer.append(" AND subaccount.NSTATUSID = " + SETTConstant.AccountStatus.NORMAL);
			buffer.append(" AND subaccount.NSTATUSID in ( " + 
					SETTConstant.SubAccountStatus.NORMAL+ "," + 
					SETTConstant.SubAccountStatus.ONLYPAYFREEZE+","+ 
					SETTConstant.SubAccountStatus.ALLFREEZE+ ","+
					SETTConstant.SubAccountStatus.PARTFREEZE+ ")");
			buffer.append(" AND ");
			buffer.append(" subaccount.AL_nLoanNoteID = payform.ID (+) ");
			buffer.append(" AND ");
			buffer.append(" payform.nContractID = contractform.ID (+) ");
			buffer.append(" AND ");
			buffer.append(" contractform.nLoanID = loanform.ID (+) ");

			if (!queryInfo.getAccountIDFromCtrl().trim().equals(""))
			{ //开始账户号
				buffer.append(" AND ");
				buffer.append(" account.sAccountNo >= '" + queryInfo.getAccountIDFromCtrl() + "'");
			}
			if (!queryInfo.getAccountIDToCtrl().trim().equals(""))
			{ //结束账户号
				buffer.append(" AND ");
				buffer.append(" account.sAccountNo <= '" + queryInfo.getAccountIDToCtrl() + "'");
			}
			if (!queryInfo.getFixedDepositIDFromCtrl().trim().equals(""))
			{ //定期单据号开始
				buffer.append(" AND( ");
				//buffer.append(" subaccount.AF_sDepositNo is null or ");
				buffer.append(" subaccount.AF_sDepositNo >= '" + queryInfo.getFixedDepositIDFromCtrl() + "'");
				buffer.append(" ) ");
			}
			if (!queryInfo.getFixedDepositIDToCtrl().trim().equals(""))
			{ //定期单据号结束
				buffer.append(" AND( ");
				//buffer.append(" subaccount.AF_sDepositNo is null or ");
				buffer.append(" subaccount.AF_sDepositNo <= '" + queryInfo.getFixedDepositIDToCtrl() + "'");
				buffer.append(" ) ");
			}
			if (!queryInfo.getContractIDFromCtrl().trim().equals(""))
			{ //合同号开始
				buffer.append(" AND( ");
				//buffer.append(" contractform.sContractCode is null or ");
				buffer.append(" contractform.sContractCode >= '" + queryInfo.getContractIDFromCtrl() + "'");
				buffer.append(" ) ");
			}
			if (!queryInfo.getContractIDToCtrl().trim().equals(""))
			{ //合同号结束
				buffer.append(" AND( ");
				//buffer.append(" contractform.sContractCode is null or ");
				buffer.append(" contractform.sContractCode <= '" + queryInfo.getContractIDToCtrl() + "'");
				buffer.append(" ) ");
			}
			if (!queryInfo.getPayFormIDFromCtrl().trim().equals(""))
			{ //放款通知单号开始
				buffer.append(" AND( ");
				//buffer.append(" payform.sCode is null or ");
				buffer.append(" payform.sCode >= '" + queryInfo.getPayFormIDFromCtrl() + "'");
				buffer.append(" ) ");
			}
			if (!queryInfo.getPayFormIDToCtrl().trim().equals(""))
			{ //放款通知单号结束
				buffer.append(" AND( ");
				//buffer.append(" payform.sCode is null or ");
				buffer.append(" payform.sCode <= '" + queryInfo.getPayFormIDToCtrl() + "'");
				buffer.append(" ) ");
			}
			/*//将贷款类型修改为贷款子类型
			if (queryInfo.getLoanType() != -1)
			{ //贷款种类
				buffer.append(" AND( ");
				//buffer.append(" loanform.nTypeID is null or ");
				buffer.append(" contractform.nTypeID = " + queryInfo.getLoanType());
				buffer.append(" ) ");
			}
			*/
			if (!queryInfo.getLSubLoanTypeValue().trim().equals(""))
			{//如果将multiple的select值选到右边,再全选到左边,此时控件的值为-1
				if(!queryInfo.getLSubLoanTypeValue().trim().equals("-1")){
					//贷款子类型
					buffer.append(" AND( ");
					//buffer.append(" loanform.NSUBTYPEID is null or ");
					buffer.append(" contractform.NSUBTYPEID in (" + queryInfo.getLSubLoanTypeValue());
					buffer.append(" )) ");
				}
			}
			if (queryInfo.getLoanTerm() != -1)
			{ //贷款期限
				buffer.append(" AND( ");
				//buffer.append(" loanform.nIntervalNum is null or ");
				buffer.append(" contractform.nIntervalNum = " + queryInfo.getLoanTerm());
				buffer.append(" ) ");
			}
			if (queryInfo.getYearTerm() != -1)
			{ //年期
				buffer.append(" AND( ");
				//buffer.append(" contractform.nIntervalNum is null or ");
				buffer.append(" substr(contractform.sContractCode,0,4) = '" + queryInfo.getYearTerm() + "'");
				buffer.append(" ) ");
			}
			if (queryInfo.getConsignID() != -1)
			{ //委托方
				buffer.append(" AND( ");
				//buffer.append(" loanform.nConsignClientID is null or ");
				buffer.append(" contractform.nConsignClientID = " + queryInfo.getConsignID());
				buffer.append(" ) ");
			}
			if(lInterestType==SETTConstant.InterestFeeType.ASSURE)
			{
				if (queryInfo.getClearInterest() != null)
				{ //结息日
					//buffer.append(" AND( ");
					//buffer.append(" subaccount.dtClearInterest is null or ");
					buffer.append(" AND ");
					buffer.append(
						" subaccount.AL_dtClearSureFee < to_date('"
							+ DataFormat.formatDate(queryInfo.getClearInterest())
							+ "','yyyy-mm-dd')");
					//buffer.append(" ) ");		
						
				}
			}
			if(lInterestType==SETTConstant.InterestFeeType.INTEREST)
			{
				if (queryInfo.getClearInterest() != null)
				{ //结息日
					//buffer.append(" AND( ");
					//buffer.append(" subaccount.dtClearInterest is null or ");
					buffer.append(" AND ");
					buffer.append(
						" subaccount.dtClearInterest <= to_date('"
							+ DataFormat.formatDate(queryInfo.getClearInterest())
							+ "','yyyy-mm-dd')");
					//buffer.append(" ) ");		
							
				}
			}
			if(lInterestType==SETTConstant.InterestFeeType.COMMISION)
			{
				if (queryInfo.getClearInterest() != null)
				{ //结息日
					//buffer.append(" AND( ");
					//buffer.append(" subaccount.dtClearInterest is null or ");
					buffer.append(" AND ");
					buffer.append(
						" subaccount.AL_dtClearCommission < to_date('"
							+ DataFormat.formatDate(queryInfo.getClearInterest())
							+ "','yyyy-mm-dd')");
					//buffer.append(" ) ");		
						
				}
			}
			if(lInterestType==SETTConstant.InterestFeeType.COMPOUNDINTEREST)
			{
				if (queryInfo.getClearInterest() != null)
				{ //结息日
					//buffer.append(" AND( ");
					//buffer.append(" subaccount.dtClearInterest is null or ");
					buffer.append(" AND ");
					buffer.append(
						" subaccount.AL_dtClearCompound < to_date('"
							+ DataFormat.formatDate(queryInfo.getClearInterest())
							+ "','yyyy-mm-dd')");
					//buffer.append(" ) ");		
							
				}
			}
			if(lInterestType==SETTConstant.InterestFeeType.PREDRAWINTEREST)
			{
				if (queryInfo.getClearInterest() != null)
				{ //结息日
					//buffer.append(" AND( ");
					//buffer.append(" subaccount.dtClearInterest is null or ");
					buffer.append(" AND( ");
					buffer.append(
						" subaccount.AF_dtPreDraw <= to_date('"
							+ DataFormat.formatDate(queryInfo.getClearInterest())
							+ "','yyyy-mm-dd')");
					buffer.append(" or subaccount.AL_dtPreDraw <= to_date('"
											+ DataFormat.formatDate(queryInfo.getClearInterest())
											+ "','yyyy-mm-dd')");		
					buffer.append(" ) ");		
							
				}
			}
			if(lInterestType==SETTConstant.InterestFeeType.FORFEITINTEREST)
			{
				if (queryInfo.getClearInterest() != null)
				{ //结息日
					//buffer.append(" AND( ");
					//buffer.append(" subaccount.dtClearInterest is null or ");
					buffer.append(" AND ");
					buffer.append(
						" subaccount.AL_dtClearOverDue < to_date('"
							+ DataFormat.formatDate(queryInfo.getClearInterest())
							+ "','yyyy-mm-dd')");
					//buffer.append(" ) ");		
							
				}
			}
			
			if (queryInfo.getIsClearNull() == SETTConstant.BooleanValue.ISTRUE)
			{ //是否滤空
				buffer.append(" AND ");
				buffer.append(" subaccount.MBALANCE > 0");
			}
			buffer.append(" AND ");
			buffer.append(" account.nOfficeID = " + queryInfo.getOfficeID());
			buffer.append(" AND ");
			buffer.append(" account.nCurrencyID = " + queryInfo.getCurrencyID());
			buffer.append(" AND ");
			
			//通知存款
			//buffer.append(" account.nAccountTypeID not in (select id from sett_accounttype where nAccountGroupID="+ SETTConstant.AccountGroupType.NOTIFY +")");
			//buffer.append(" AND ");
			
			//贴现贷款
			buffer.append(" account.nAccountTypeID not in (select id from sett_accounttype where nAccountGroupID="+ 
				SETTConstant.AccountGroupType.DISCOUNT +" and nstatusId=1 and officeId="+queryInfo.getOfficeID()+" and currencyId="+queryInfo.getCurrencyID()+")");

			/**
			 * 修改的地方
			 */
			//加入贴现记录查询 start----------
			buffer.append(" union ");
			buffer.append(" SELECT ");
			
			//从合同表中查询出贴现利率
			buffer.append(" contractform.id contractID, discredence.id discreID, ");
			buffer.append(" contractform.dtenddate enddate, contractform.mdiscountrate AS discountRate, account.ID AS accountID, ");
			
			buffer.append(" account.sAccountNo AS accountNo, "); //帐户号
			buffer.append(" account.nAccountTypeID AS accountTypeID,"); //帐户类型
			buffer.append(" subaccount.ID AS subAccountID, "); //子帐户 ID
			buffer.append(" subaccount.AF_sDepositNo||'/'||discredence.SDRAFTCODE AS fixedDepositNo, "); //定期单据号
			buffer.append(" subaccount.AL_mPreDrawInterest AS loanPreDrawInterest, ");  //贴现计提利息
			
			//buffer.append(" 0 drawingInterest, "); //活期计提利息
			
			buffer.append(" contractform.sContractCode AS contractNo, "); //合同号
			buffer.append(" discredence.sCode AS payFormNo, "); //放款通知单号
			
			buffer.append(" -1 payFormID "); //放款通知单号ID  MODIFY BY ZCWANG 2007-6-23
			
			buffer.append(" FROM ");
			
			//loan_discountcredence 记录贴现凭证信息
			buffer.append(" sett_Account account, sett_SubAccount subaccount, loan_discountcredence discredence, loan_ContractForm contractform, loan_LoanForm loanform");
			
			//Boxu Add 2010-10-29 查询条件增加账户类型
			buffer.append(" , Sett_AccountType acctype ");
			
			buffer.append(" WHERE ");
			buffer.append(" account.ID = subaccount.nAccountID ");
			
			//Boxu Add 2010-10-29 查询条件增加账户类型
			buffer.append(" and account.nAccountTypeID = acctype.id ");
			if(queryInfo.getIsCheckedType() == 1)
			{
				if(queryInfo.getLAccountTypeID1() > 0)
				{
					buffer.append(" and acctype.id = "+ queryInfo.getLAccountTypeID1() +" ");
				}
			}
			else if(queryInfo.getIsCheckedType() == 2)
			{
				if(queryInfo.getLAccountTypeID2() > 0)
				{
					buffer.append(" and acctype.naccountgroupid = "+ queryInfo.getLAccountTypeID2() +" ");
				}
			}
			
			//修改 by kenny (胡志强)(2007-08-18) 增加账户状态为[只收不付冻结、不收不付冻结、部分冻结];以下账户状态均能算息、结息
			buffer.append(" AND account.NSTATUSID in (" + 
					SETTConstant.AccountStatus.NORMAL + "," + 
					SETTConstant.AccountStatus.FREEZE + "," + 
					SETTConstant.AccountStatus.ALLFREEZE + "," + 
					SETTConstant.AccountStatus.PARTFREEZE + ")");
			
			buffer.append(" AND account.NCHECKSTATUSID = " + SETTConstant.AccountCheckStatus.CHECK);
			//buffer.append(" AND subaccount.NSTATUSID = " + SETTConstant.AccountStatus.NORMAL);
			buffer.append(" AND subaccount.NSTATUSID in ( " + 
					SETTConstant.SubAccountStatus.NORMAL+ "," + 
					SETTConstant.SubAccountStatus.ONLYPAYFREEZE+","+ 
					SETTConstant.SubAccountStatus.ALLFREEZE+ ","+
					SETTConstant.SubAccountStatus.PARTFREEZE+ ")");
			
			buffer.append(" AND ");
			
			//al_nloannoteid 放款单号
			buffer.append(" subaccount.al_nloannoteid=discredence.id(+) ");
			buffer.append(" AND ");
			buffer.append(" discredence.ncontractid=contractform.ID (+) ");
			buffer.append(" AND ");
			buffer.append(" contractform.nLoanID = loanform.ID (+) ");
			
			if (!queryInfo.getAccountIDFromCtrl().trim().equals(""))
			{ //开始帐户号
				buffer.append(" AND ");
				buffer.append(" account.sAccountNo >= '" + queryInfo.getAccountIDFromCtrl() + "'");
			}
			if (!queryInfo.getAccountIDToCtrl().trim().equals(""))
			{ //结束帐户号
				buffer.append(" AND ");
				buffer.append(" account.sAccountNo <= '" + queryInfo.getAccountIDToCtrl() + "'");
			}
			if (!queryInfo.getFixedDepositIDFromCtrl().trim().equals(""))
			{ //定期单据号开始
				buffer.append(" AND( ");
				//buffer.append(" subaccount.AF_sDepositNo is null or ");
				buffer.append(" subaccount.AF_sDepositNo >= '" + queryInfo.getFixedDepositIDFromCtrl() + "'");
				buffer.append(" ) ");
			}
			if (!queryInfo.getFixedDepositIDToCtrl().trim().equals(""))
			{ //定期单据号结束
				buffer.append(" AND( ");
				//buffer.append(" subaccount.AF_sDepositNo is null or ");
				buffer.append(" subaccount.AF_sDepositNo <= '" + queryInfo.getFixedDepositIDToCtrl() + "'");
				buffer.append(" ) ");
			}
			if (!queryInfo.getContractIDFromCtrl().trim().equals(""))
			{ //合同号开始
				buffer.append(" AND( ");
				//buffer.append(" contractform.sContractCode is null or ");
				buffer.append(" contractform.sContractCode >= '" + queryInfo.getContractIDFromCtrl() + "'");
				buffer.append(" ) ");
			}
			if (!queryInfo.getContractIDToCtrl().trim().equals(""))
			{ //合同号结束
				buffer.append(" AND( ");
				//buffer.append(" contractform.sContractCode is null or ");
				buffer.append(" contractform.sContractCode <= '" + queryInfo.getContractIDToCtrl() + "'");
				buffer.append(" ) ");
			}
			/*//将贷款类型修改为贷款子类型
			if (queryInfo.getLoanType() != -1)
			{ //贷款种类
				buffer.append(" AND( ");
				//buffer.append(" loanform.nTypeID is null or ");
				buffer.append(" contractform.nTypeID = " + queryInfo.getLoanType());
				buffer.append(" ) ");
			}
			*/
			if (!queryInfo.getLSubLoanTypeValue().trim().equals(""))
			{//如果将multiple的select值选到右边,再全选到左边,此时控件的值为-1
				if(!queryInfo.getLSubLoanTypeValue().trim().equals("-1")){
					//贷款子类型
					buffer.append(" AND( ");
					//buffer.append(" loanform.NSUBTYPEID is null or ");
					buffer.append(" contractform.NSUBTYPEID in (" + queryInfo.getLSubLoanTypeValue());
					buffer.append(" )) ");
				}
			}
			if (queryInfo.getLoanTerm() != -1)
			{ //贷款期限
				buffer.append(" AND( ");
				//buffer.append(" loanform.nIntervalNum is null or ");
				buffer.append(" contractform.nIntervalNum = " + queryInfo.getLoanTerm());
				buffer.append(" ) ");
			}
			if (queryInfo.getYearTerm() != -1)
			{ //年期
				buffer.append(" AND( ");
				//buffer.append(" contractform.nIntervalNum is null or ");
				buffer.append(" substr(contractform.sContractCode,0,4) = '" + queryInfo.getYearTerm() + "'");
				buffer.append(" ) ");
			}
			if (queryInfo.getConsignID() != -1)
			{ //委托方
				buffer.append(" AND( ");
				//buffer.append(" loanform.nConsignClientID is null or ");
				buffer.append(" contractform.nConsignClientID = " + queryInfo.getConsignID());
				buffer.append(" ) ");
			}
			if(lInterestType==SETTConstant.InterestFeeType.ASSURE)
			{
				if (queryInfo.getClearInterest() != null)
				{ //结息日
					//buffer.append(" AND( ");
					//buffer.append(" subaccount.dtClearInterest is null or ");
					buffer.append(" AND ");
					buffer.append(
						" subaccount.AL_dtClearSureFee < to_date('"
							+ DataFormat.formatDate(queryInfo.getClearInterest())
							+ "','yyyy-mm-dd')");
					//buffer.append(" ) ");		
					buffer.append(" and acctype.naccountgroupid <> 6 ");	
				}
			}
			if(lInterestType==SETTConstant.InterestFeeType.INTEREST)
			{
				if (queryInfo.getClearInterest() != null)
				{ //结息日
					//buffer.append(" AND( ");
					//buffer.append(" subaccount.dtClearInterest is null or ");
					buffer.append(" AND ");
					buffer.append(
						" subaccount.dtClearInterest < to_date('"
							+ DataFormat.formatDate(queryInfo.getClearInterest())
							+ "','yyyy-mm-dd')");
					//buffer.append(" ) ");		
							
				}
			}
			if(lInterestType==SETTConstant.InterestFeeType.COMMISION)
			{
				if (queryInfo.getClearInterest() != null)
				{ //结息日
					//buffer.append(" AND( ");
					//buffer.append(" subaccount.dtClearInterest is null or ");
					buffer.append(" AND ");
					buffer.append(
						" subaccount.AL_dtClearCommission < to_date('"
							+ DataFormat.formatDate(queryInfo.getClearInterest())
							+ "','yyyy-mm-dd')");
					//buffer.append(" ) ");		
					buffer.append(" and acctype.naccountgroupid <> 6 ");	
				}
			}
			if(lInterestType==SETTConstant.InterestFeeType.COMPOUNDINTEREST)
			{
				if (queryInfo.getClearInterest() != null)
				{ //结息日
					//buffer.append(" AND( ");
					//buffer.append(" subaccount.dtClearInterest is null or ");
					buffer.append(" AND ");
					buffer.append(
						" subaccount.AL_dtClearCompound < to_date('"
							+ DataFormat.formatDate(queryInfo.getClearInterest())
							+ "','yyyy-mm-dd')");
					//buffer.append(" ) ");		
					buffer.append(" and acctype.naccountgroupid <> 6 ");
							
				}
			}
			if(lInterestType==SETTConstant.InterestFeeType.PREDRAWINTEREST)
			{
				if (queryInfo.getClearInterest() != null)
				{ //结息日
					//buffer.append(" AND( ");
					//buffer.append(" subaccount.dtClearInterest is null or ");
					buffer.append(" AND( ");
					buffer.append(
						" subaccount.AF_dtPreDraw <= to_date('"
							+ DataFormat.formatDate(queryInfo.getClearInterest())
							+ "','yyyy-mm-dd')");
					buffer.append(" or subaccount.AL_dtPreDraw <= to_date('"
											+ DataFormat.formatDate(queryInfo.getClearInterest())
											+ "','yyyy-mm-dd')");		
					buffer.append(" ) ");		
							
				}
			}
			if(lInterestType==SETTConstant.InterestFeeType.FORFEITINTEREST)
			{
				if (queryInfo.getClearInterest() != null)
				{ //结息日
					//buffer.append(" AND( ");
					//buffer.append(" subaccount.dtClearInterest is null or ");
					buffer.append(" AND ");
					buffer.append(
						" subaccount.AL_dtClearOverDue < to_date('"
							+ DataFormat.formatDate(queryInfo.getClearInterest())
							+ "','yyyy-mm-dd')");
					//buffer.append(" ) ");		
					buffer.append(" and acctype.naccountgroupid <> 6 ");		
				}
			}
			
			if (queryInfo.getIsClearNull() == SETTConstant.BooleanValue.ISTRUE)
			{ //是否滤空
				buffer.append(" AND ");
				buffer.append(" subaccount.MBALANCE > 0");
			}
			
			//贴现开始日期
			if(queryInfo.getDtStartDiscount() != null)
			{
				buffer.append(" and ");
				buffer.append(" to_char(contractform.dtDiscountDate,'yyyy-mm-dd') >= '"
						+ DataFormat.getDateString(queryInfo.getDtStartDiscount()) + "'");
			}
			//贴现结束日期
			if(queryInfo.getDtEndDiscount() != null)
			{
				buffer.append(" and ");
				buffer.append(" to_char(contractform.dtDiscountDate,'yyyy-mm-dd') <= '"
						+ DataFormat.getDateString(queryInfo.getDtEndDiscount()) + "'");
			}
			
			buffer.append(" AND ");
			buffer.append(" account.nOfficeID = " + queryInfo.getOfficeID());
			buffer.append(" AND ");
			buffer.append(" account.nCurrencyID = " + queryInfo.getCurrencyID());
			buffer.append(" AND ");
			buffer.append(" account.nAccountTypeID in (select id from sett_accounttype where nAccountGroupID="+ 
				SETTConstant.AccountGroupType.DISCOUNT +" and nstatusId=1 and officeId="+queryInfo.getOfficeID()+" and currencyId="+queryInfo.getCurrencyID()+")");
			//加入贴现记录查询 end  ----------
			
			buffer.append(" Order By ");
			buffer.append(" accountNo, contractNo, payFormNo");
			//buffer.append(" ) rs_temp ");
			//buffer.append(" ) WHERE r BETWEEN ? AND ? ");
			
			strSQL = buffer.toString();

			log.info(strSQL);

			ps = conInternal.prepareStatement(strSQL);

			//执行查询
			rs = ps.executeQuery();

			//取得结果
			log.info("***********设置返回结果***********");
			result = fetchDataFromRS(rs);
		}
		catch (Exception e)
		{
			log.error("检索结息记录时发生错误：" + e.getMessage());
			throw e;
		}
		finally
		{ //释放资源
			cleanup(rs);
			cleanup(ps);
			if(con==null)
			{
				cleanup(conInternal);
			}
		}

		return result;
	}
	/**
	 * 根据条件进行结息之前上次起息日的查询
	 * 
	 */
	public Timestamp findInterestStartDate(Connection con, long lSubAccountID,long lInterestType,long lOperationType) throws Exception
	{		
		StringBuffer buffer = new StringBuffer();
		Connection conInternal = null;
		if (con == null)
		{
			conInternal = getConnection();
		}
		else
		{
			conInternal = con;
		}
		String strSQL = "";
		PreparedStatement ps = null;
		ResultSet rs = null;
		Timestamp result = null; //返回值

		try
		{
			strSQL = "select max(dtInterestStart) dtInterestStart from sett_TransInterestSettlement where nSubAccountID=? and nInterestType=? and nOperationType =? order by dtExecute ";
			log.info(strSQL);
			ps = con.prepareStatement(strSQL);
			ps.setLong(1, lSubAccountID);
			ps.setLong(2, lInterestType);
			ps.setLong(3, lOperationType);
			rs = ps.executeQuery();
			if (rs.next())
			{
				result = rs.getTimestamp("dtInterestStart");
			}		
		}
		catch (Exception e)
		{
			log.error("检索结息记录时发生错误：" + e.getMessage());
			throw e;
		}
		finally
		{ //释放资源
			cleanup(rs);
			cleanup(ps);
			if(con==null)
			{
				cleanup(conInternal);
			}
		}
		return result;
	}
	/*public long getRecordCount(Connection con, InterestQueryInfo queryInfo) throws Exception{
	
	   log.info("查询结息记录总数");
	   StringBuffer buffer = new StringBuffer();
	   String strSQL = "";
	   PreparedStatement ps = null;
	   ResultSet rs = null;
	   long lRtn = 0;
	   
	   //构造查询语句主体
	   buffer.append(" SELECT count(*) AS totalcnt ");
	   buffer.append(" FROM ");
	   buffer.append(" sett_Account account, sett_SubAccount subaccount, loan_PayForm payform, loan_ContractForm contractform, loan_LoanForm loanform");
	   buffer.append(" WHERE ");
	   buffer.append(" account.ID = subaccount.nAccountID ");
	   buffer.append(" AND ");
	   buffer.append(" subaccount.AL_nLoanNoteID = payform.ID (+) ");
	   buffer.append(" AND ");
	   buffer.append(" payform.nContractID = contractform.ID (+) ");
	   buffer.append(" AND ");
	   buffer.append(" contractform.nLoanID = loanform.ID (+) ");
	   if(!queryInfo.getAccountIDFromCtrl().trim().equals("")){                    //开始账户号
	       buffer.append(" AND ");
	       buffer.append(" account.sAccountNo >= ? ");
	   }
	   if(!queryInfo.getFixedDepositIDFromCtrl().trim().equals("")){               //定期单据号开始
	       buffer.append(" AND( ");
	       buffer.append(" subaccount.AF_sDepositNo is null or ");
	       buffer.append(" subaccount.AF_sDepositNo >= ? ");
	       buffer.append(" ) ");
	   }
	   if(!queryInfo.getFixedDepositIDToCtrl().trim().equals("")){                 //定期单据号结束
	       buffer.append(" AND( ");
	       buffer.append(" subaccount.AF_sDepositNo is null or ");
	       buffer.append(" subaccount.AF_sDepositNo <= ? ");
	       buffer.append(" ) ");
	   }
	   if(!queryInfo.getContractIDFromCtrl().trim().equals("")){                   //合同号开始
	       buffer.append(" AND( ");
	       buffer.append(" contractform.sContractCode is null or ");
	       buffer.append(" contractform.sContractCode >= ? ");
	       buffer.append(" ) ");
	   }
	   if(!queryInfo.getContractIDToCtrl().trim().equals("")){                     //合同号结束
	       buffer.append(" AND( ");
	       buffer.append(" contractform.sContractCode is null or ");
	       buffer.append(" contractform.sContractCode <= ? ");
	       buffer.append(" ) ");
	   }
	   if(!queryInfo.getPayFormIDFromCtrl().trim().equals("")){                   //放款通知单号开始
	       buffer.append(" AND( ");
	       buffer.append(" payform.sCode is null or ");
	       buffer.append(" payform.sCode >= ? ");
	       buffer.append(" ) ");
	   }
	   if(!queryInfo.getPayFormIDToCtrl().trim().equals("")){                     //放款通知单号结束
	       buffer.append(" AND( ");
	       buffer.append(" payform.sCode is null or ");
	       buffer.append(" payform.sCode <= ? ");
	       buffer.append(" ) ");
	   }
	   if(queryInfo.getLoanType() != -1){                                          //贷款种类
	       buffer.append(" AND( ");
	       buffer.append(" loanform.nTypeID is null or ");
	       buffer.append(" loanform.nTypeID = ? ");
	       buffer.append(" ) ");
	   }
	   if(queryInfo.getLoanTerm() != -1){                                          //贷款期限
	       buffer.append(" AND( ");
	       buffer.append(" loanform.nIntervalNum is null or ");
	       buffer.append(" loanform.nIntervalNum = ? ");
	       buffer.append(" ) ");
	   }
	   if(queryInfo.getLoanTerm() != -1){                                          //年期
	       buffer.append(" AND( ");
	       buffer.append(" contractform.nIntervalNum is null or ");
	       buffer.append(" contractform.nIntervalNum = ? ");
	       buffer.append(" ) ");
	   }
	   if(queryInfo.getConsignID() != -1){                                          //委托方
	       buffer.append(" AND( ");
	       buffer.append(" loanform.nConsignClientID is null or ");
	       buffer.append(" loanform.nConsignClientID = ? ");
	       buffer.append(" ) ");
	   }
	   if(queryInfo.getClearInterest() != null){                                    //结息日
	       buffer.append(" AND ");
	       buffer.append(" subaccount.dtClearInterest >= ? ");
	   }
	   buffer.append(" Order By ");
	   buffer.append(" account.sAccountNo, contractform.sContractCode, payform.sCode");
	   strSQL = buffer.toString();
	//	    log.debug("strSQL is : " + strSQL);
	   System.out.println(strSQL);
	   try{
	       //设置连接参数
		    ps = con.prepareStatement(strSQL);
	       int nIndex = 1;
		    if(!queryInfo.getAccountIDFromCtrl().trim().equals("")){                    //开始账户号
		        ps.setString(nIndex++, queryInfo.getAccountIDFromCtrl().trim());
		    }
		    if(!queryInfo.getAccountIDToCtrl().trim().equals("")){                      //结束账户号
		        ps.setString(nIndex++, queryInfo.getAccountIDToCtrl().trim());
		    }
		    if(!queryInfo.getFixedDepositIDFromCtrl().trim().equals("")){               //定期单据号开始
		        ps.setString(nIndex++, queryInfo.getFixedDepositIDFromCtrl().trim());
		    }
		    if(!queryInfo.getFixedDepositIDToCtrl().trim().equals("")){                 //定期单据号结束
		        ps.setString(nIndex++, queryInfo.getFixedDepositIDToCtrl().trim());
		    }
		    if(!queryInfo.getContractIDFromCtrl().trim().equals("")){                   //合同号开始
		        ps.setString(nIndex++, queryInfo.getContractIDFromCtrl().trim());
		    }
		    if(!queryInfo.getContractIDToCtrl().trim().equals("")){                     //合同号结束
		        ps.setString(nIndex++, queryInfo.getContractIDToCtrl().trim());
		    }
		    if(!queryInfo.getPayFormIDFromCtrl().trim().equals("")){                    //放款通知单号开始
		        ps.setString(nIndex++, queryInfo.getPayFormIDFromCtrl().trim());
		    }
		    if(!queryInfo.getPayFormIDToCtrl().trim().equals("")){                      //放款通知单号结束
		        ps.setString(nIndex++, queryInfo.getPayFormIDToCtrl().trim());
		    }
		    if(queryInfo.getLoanType() != -1){                                          //贷款种类
		        ps.setLong(nIndex++, queryInfo.getLoanType());
		    }
		    if(queryInfo.getLoanTerm() != -1){                                          //贷款期限
		        ps.setLong(nIndex++, queryInfo.getLoanTerm());
		    }
		    if(queryInfo.getLoanTerm() != -1){                                          //年期
		        ps.setLong(nIndex++, queryInfo.getLoanTerm());
		    }
		    if(queryInfo.getConsignID() != -1){                                          //委托方
		       ps.setLong(nIndex++, queryInfo.getConsignID());
		    }
		    if(queryInfo.getClearInterest() != null){                                    //结息日
		        ps.setTimestamp(nIndex++, queryInfo.getClearInterest());
		    }    	
		    
		    //执行查询
		    rs = ps.executeQuery();
		    if(rs.next()){
		        lRtn = rs.getLong("totalcnt");
		    }    	    
	   }catch(Exception e){
	       log.error("检索结息记录总数时发生错误：" + e.getMessage());
	       throw e;
	   }finally{                                                                           //释放资源
	       if(rs != null){
	           try{
	               rs.close();
	           }catch(Exception eClose){
	               ;
	           }
	       }
	       if(ps != null){
	           try{
	               ps.close();
	           }catch(Exception eClose){
	               ;
	           }
	       }
	   }
	
	   return lRtn;
	}*/
	/**
	 * 新增结息表数据的方法：
	 * 逻辑说明：
	 * 
	 * @param info, InterestSettmentInfo, 结息交易实体类
	 * @return long, 新生成记录的标识
	 * @throws IException
	 */
	public long add(Connection con, InterestSettmentInfo info) throws Exception
	{
		long lReturn = -1;
		Connection conInternal = null;
		if (con == null)
		{
			conInternal = getConnection();
		}
		else
		{
			conInternal = con;
		}
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{

			//利用数据库的取ID;			
			//long id= getMaxID(con);
			long id = getID(con);
			info.setID(id);

			StringBuffer buffer = new StringBuffer();
			buffer.append(" INSERT INTO sett_TransInterestSettlement \n ");
			buffer.append(" ( ID, nOfficeID, nCurrencyID, STRANSNO, NTRANSACTIONTYPEID \n ");
			buffer.append(" , NACCOUNTID, NSUBACCOUNTID, NACCOUNTTYPEID, NINTERESTTYPE, NOPERATIONTYPE \n ");
			buffer.append(" , DTINTERESTSETTLEMENT, DTINTERESTSTART, DTINTERESTEND, NINTERESTDAYS, MBASEBALANCE \n ");
			buffer.append(" , MRATE, MPECISIONINTEREST, MINTEREST, MNEGOTIATEBALANCE, MNEGOTIATERATE \n ");
			buffer.append(" , MNEGOTIATEPECISIONINTEREST, MNEGOTIATEINTEREST, MINTERESTTAXRATE, MINTERESTTAX, NPAYINTERESTACCOUNTID \n ");
			buffer.append(" , NRECEIVEINTERESTACCOUNTID, DTEXECUTE, NINPUTUSERID, SABSTRACT, SCHECKABSTRACT \n ");
			buffer.append(" , NISSAVE, NISKEEPACCOUNT, NISSUCCESS, SFAULTREASON, NINTERESTFEESETTINGDETAILID \n ");
			buffer.append(" , dtAutoExecute, nBatchNo, NSTATUSID ) \n ");  //38
			buffer.append(" VALUES \n ");
			buffer.append("( ?,?,?,?,?,?,?,?,?,?, \n ");
			buffer.append(" ?,?,?,?,?,?,?,?,?,?, \n ");
			buffer.append(" ?,?,?,?,?,?,?,?,?,?, \n ");
			buffer.append(" ?,?,?,?,?,sysdate,?,?) \n ");

			ps = con.prepareStatement(buffer.toString());
			log.info(buffer.toString());
			
			int index = 1;
			ps.setLong(index++, info.getID());
			ps.setLong(index++, info.getOfficeID());
			ps.setLong(index++, info.getCurrencyID());
			ps.setString(index++, info.getTransNo());
			ps.setLong(index++, info.getTransactionTypeID());
			ps.setLong(index++, info.getAccountID());
			ps.setLong(index++, info.getSubAccountID());
			ps.setLong(index++, info.getAccountTypeID());
			ps.setLong(index++, info.getInterestType());
			ps.setLong(index++, info.getOperationType());
			ps.setTimestamp(index++, info.getInterestSettlementDate());
			ps.setTimestamp(index++, info.getInterestStartDate());
			ps.setTimestamp(index++, info.getInterestEndDate());
			ps.setLong(index++, info.getInterestDays());
			ps.setDouble(index++, info.getBaseBalance());
			ps.setDouble(index++, info.getRate());
			ps.setDouble(index++, info.getPecisionInterest());
			ps.setDouble(index++, info.getInterest());  //18
			ps.setDouble(index++, info.getNegotiateBalance());
			ps.setDouble(index++, info.getNegotiateRate());
			ps.setDouble(index++, info.getNegotiatePecisionInterest());
			ps.setDouble(index++, info.getNegotiateInterest());
			ps.setDouble(index++, info.getInterestTaxRate());
			ps.setDouble(index++, info.getInterestTax());
			ps.setLong(index++, info.getPayInterestAccountID());
			ps.setLong(index++, info.getReceiveInterestAccountID());
			ps.setTimestamp(index++, info.getExecuteDate());
			ps.setLong(index++, info.getInputUserID());
			ps.setString(index++, info.getAbstract());
			ps.setString(index++, info.getCheckAbstract());
			ps.setLong(index++, info.getIsSave());
			ps.setLong(index++, info.getIsKeepAccount());
			ps.setLong(index++, info.getIsSuccess());
			ps.setString(index++, info.getFaultReason());
			ps.setLong(index++, info.getTransInterestFeeID());
			ps.setLong(index++, info.getBatchNo());
			ps.setLong(index++, info.getStatusID());

			int i = ps.executeUpdate();
			if (i > 0)
			{
				lReturn = info.getID();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			try
			{
				cleanup(rs);
				cleanup(ps);
				if (con == null)
				{
					cleanup(conInternal);
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw e;
			}
		}
		return lReturn;
	}
	/**
	 * 根据标识查询定期（通知）交易明细的方法：
	 * 逻辑说明：
	 * 
	 * @param lID long , 交易的ID
	 * @return FixedOpenInfo, 定期（通知）交易实体类
	 * @throws Exception
	 */
	public InterestSettmentInfo findByID(Connection con, long lID) throws Exception
	{
		InterestSettmentInfo info = new InterestSettmentInfo();
		Connection conInternal = null;
		if (con == null)
		{
			conInternal = getConnection();
		}
		else
		{
			conInternal = con;
		}
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{

			String strSQL = "select * from sett_TransInterestSettlement where id=? ";
			log.info(strSQL);
			ps = conInternal.prepareStatement(strSQL);
			ps.setLong(1, lID);
			rs = ps.executeQuery();
			if (rs.next())
			{
				info = getInterestSettlementInfo(info, rs);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			try
			{
				cleanup(rs);
				cleanup(ps);
			   if(con == null)
			   {
				cleanup(conInternal);
			   }
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw e;
			}
		}
		return info;
	}

	/**
	 * 根据标识查询上一次操作的记录
	 * 逻辑说明：与时间进行比较,当天有该操作的上一次记录返回false,没有可以删除
	 * 
	 * @return InterestSettmentInfo, 结息记录实体类
	 * @throws Exception
	 */
	public InterestSettmentInfo findOperation(Connection con, InterestSettmentInfo info) throws Exception
	{
		InterestSettmentInfo interestInfo = new InterestSettmentInfo();
		Connection conInternal = null;
		PreparedStatement ps = null;
		PreparedStatement preps = null;
		ResultSet rs = null;
		ResultSet resrs = null;
		StringBuffer strSQL = new StringBuffer();
		String transNo = "";
		long operationType = -1;
		Timestamp minDate = null;
		
		if (con == null)
		{
			conInternal = getConnection();
		}
		else
		{
			conInternal = con;
		}
		
		try
		{
			strSQL.append(" select * from sett_TransInterestSettlement ");
			strSQL.append(" where 1=1 ");
			strSQL.append(" and naccountid = "+info.getAccountID()+" and nsubaccountid = "+info.getSubAccountID()+" ");
			strSQL.append(" and dtexecute = to_date('"+DataFormat.formatDate(info.getExecuteDate())+"','yyyy-mm-dd') ");
			strSQL.append(" and nofficeid = "+info.getOfficeID()+" and ncurrencyid = "+info.getCurrencyID()+" ");
			strSQL.append(" and nstatusid = "+SETTConstant.TransactionStatus.CHECK+" and NACCOUNTTYPEID = "+info.getAccountTypeID()+" ");
			strSQL.append(" and dtautoexecute > to_date('"+DataFormat.getDateTimeString( info.getAutoExecuteDate() )+"','yyyy-mm-dd hh24:mi:ss') ");
			
			log.info(strSQL.toString());
			ps = con.prepareStatement(strSQL.toString());
			rs = ps.executeQuery();
			preps = con.prepareStatement(strSQL.toString());
			resrs = preps.executeQuery();
			
			if(rs.next())
			{
				minDate = rs.getTimestamp("dtAutoExecute");
				transNo = rs.getString("stransno");
				operationType = rs.getLong("NOPERATIONTYPE");
			}
			
			while (resrs.next())
			{
				Timestamp tempDate = null;
				tempDate = resrs.getTimestamp("dtAutoExecute");
				
				if(tempDate.before(minDate))
				{
					minDate = resrs.getTimestamp("dtAutoExecute");
					transNo = resrs.getString("stransno");
					operationType = resrs.getLong("NOPERATIONTYPE");
				}
			}
			
			interestInfo.setTransNo(transNo);
			interestInfo.setOperationType(operationType);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			try
			{
				cleanup(rs);
				cleanup(ps);
				if (con == null)
				{
					cleanup(conInternal);
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw e;
			}
		}
		return interestInfo;
	}
	
	/**
	 * 方法说明：根据查询条件组合，查询出符合的记录
	 *  Method findByConditions.
	 * @param sett_TransCurrentDepositInfo
	 * @param orderBySequence 
	 * @return Collection
	 */
	public Collection findByConditions(
		Connection con,
		InterestSettmentInfo conditionInfo,
		int orderByType,
		boolean isDesc)
		throws Exception
	{

		Connection conInternal = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Collection result = null;
		try
		{
			if (con == null)
			{
				conInternal = getConnection();
			}
			else
			{
				conInternal = con;
			}

			StringBuffer strSQLBuffer = new StringBuffer(256);

			strSQLBuffer.append("SELECT * FROM sett_TransInterestSettlement \n");

			//flag for deciding whether there is WHERE in query string
			boolean isNeedWhere = false;

			StringBuffer strWhereSQLBuffer = new StringBuffer(128);

			if (conditionInfo.getID() != -1)
			{
				strWhereSQLBuffer.append(" AND ID = " + conditionInfo.getID() + " \n");
				isNeedWhere = true;
			}

			if (conditionInfo.getOfficeID() != -1)
			{
				strWhereSQLBuffer.append(" AND NOFFICEID = " + conditionInfo.getOfficeID() + " \n");
				isNeedWhere = true;
			}

			if (conditionInfo.getCurrencyID() != -1)
			{
				strWhereSQLBuffer.append(" AND NCURRENCYID = " + conditionInfo.getCurrencyID() + " \n");
				isNeedWhere = true;
			}

			if (conditionInfo.getTransInterestFeeID() != -1)
			{
				strWhereSQLBuffer.append(
					" AND nInterestFeeSettingDetailID  = " + conditionInfo.getTransInterestFeeID() + " \n");
				isNeedWhere = true;
			}

			if (isNeedWhere)
			{
				strSQLBuffer.append(" WHERE");
				//Cut first "AND"
				strSQLBuffer.append(strWhereSQLBuffer.substring(4));
			}

			//			boolean isNeedOrderBy = true;
			//			switch (orderByType)
			//			{
			//
			//			}
			//
			//			if (isNeedOrderBy)
			//			{
			//				if (isDesc)
			//					strSQLBuffer.append(" DESC \n");
			//				else
			//					strSQLBuffer.append(" ASC \n");
			//			}

			log.info(strSQLBuffer.toString());
			ps = conInternal.prepareStatement(strSQLBuffer.toString());
			rs = ps.executeQuery();

			result = new ArrayList(16);
			while (rs.next())
			{
				InterestSettmentInfo info = new InterestSettmentInfo();
				getInterestSettlementInfo(info, rs);

				result.add(info);
			}

			result = (result.size() > 0) ? result : null;
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			if (con == null)
			{
				cleanup(conInternal);
			}
		}
		return result;
	}
	/**
	 * 设置利息结算交易结果集： 
	 * 逻辑说明：
	 * @throws Exception
	 */
	private InterestSettmentInfo getInterestSettlementInfo(InterestSettmentInfo info, ResultSet rs) throws Exception
	{
		//info = new InterestSettmentInfo();
		try
		{
			info.setID(rs.getLong("ID"));
			info.setOfficeID(rs.getLong("nOfficeID"));
			info.setCurrencyID(rs.getLong("nCurrencyID"));
			info.setTransNo(rs.getString("sTransNo"));
			info.setTransactionTypeID(rs.getLong("nTransactionTypeID"));
			info.setAccountID(rs.getLong("nAccountID"));
			info.setSubAccountID(rs.getLong("nSubAccountID"));
			info.setAccountTypeID(rs.getLong("nAccountTypeID"));
			info.setInterestType(rs.getLong("nInterestType"));
			info.setOperationType(rs.getLong("nOperationType"));
			info.setInterestSettlementDate(rs.getTimestamp("dtInterestSettlement"));
			info.setInterestStartDate(rs.getTimestamp("dtInterestStart"));
			info.setInterestEndDate(rs.getTimestamp("dtInterestEnd"));
			info.setInterestDays(rs.getLong("nInterestDays"));
			info.setBaseBalance(rs.getDouble("mBaseBalance"));
			info.setRate(rs.getDouble("mRate"));
			info.setPecisionInterest(rs.getDouble("mPecisionInterest"));
			info.setInterest(rs.getDouble("mInterest"));
			info.setNegotiateBalance(rs.getDouble("mNegotiateBalance"));
			info.setNegotiateRate(rs.getDouble("mNegotiateRate"));
			info.setNegotiatePecisionInterest(rs.getDouble("mNegotiatePecisionInterest"));
			info.setNegotiateInterest(rs.getDouble("mNegotiateInterest"));
			info.setInterestTaxRate(rs.getDouble("mInterestTaxRate"));
			info.setInterestTax(rs.getDouble("mInterestTax"));
			info.setPayInterestAccountID(rs.getLong("nPayInterestAccountID"));
			info.setReceiveInterestAccountID(rs.getLong("nReceiveInterestAccountID"));
			info.setExecuteDate(rs.getTimestamp("dtExecute"));
			info.setInputUserID(rs.getLong("nInputUserID"));
			info.setAbstract(rs.getString("sAbstract"));
			info.setCheckAbstract(rs.getString("sCheckAbstract"));
			info.setIsSave(rs.getLong("nIsSave"));
			info.setIsKeepAccount(rs.getLong("nIsKeepAccount"));
			info.setIsSuccess(rs.getLong("nIsSuccess"));
			info.setFaultReason(rs.getString("sFaultReason"));
			info.setTransInterestFeeID(rs.getLong("NINTERESTFEESETTINGDETAILID"));
			info.setAutoExecuteDate(rs.getTimestamp("dtAutoExecute"));
			info.setBatchNo(rs.getLong("nBatchNo"));
			info.setStatusID(rs.getLong("nStatusID"));
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return info;

	}

	/**
	 * 设置利息结算交易结果集： 
	 * 逻辑说明：
	 * @throws Exception
	 */
	private InterestSettmentInfo getNeInterestSettlementInfo(InterestSettmentInfo info, ResultSet rs) throws Exception
	{
		//info = new InterestSettmentInfo();
		try
		{
			info.setID(rs.getLong("ID"));			
			info.setNegotiateBalance(rs.getDouble("mNegotiateBalance"));
			info.setNegotiateRate(rs.getDouble("mNegotiateRate"));
			info.setNegotiatePecisionInterest(rs.getDouble("mNegotiatePecisionInterest"));
			info.setNegotiateInterest(rs.getDouble("mNegotiateInterest"));			
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return info;

	}
	/**
	 * 修改交易状态的方法：
	 * 逻辑说明：
	 * 
	 * @param lID, long, 本金交易标识
	 * @param lStatusID, long, 状态标识
	 * @return long, 被修改记录的标识
	 * @throws Exception
	 */
	public long updateStatus(Connection con, long lID, long lStatusID) throws Exception
	{
		log.info("lhj debug info == ID:" + lID);
		log.info("lhj debug info == 状态标示：" + lStatusID);
		long lReturn = -1;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conInternal = null;
		if (con == null)
		{
			conInternal = getConnection();
		}
		else
		{
			conInternal = con;
		}
		try
		{
			StringBuffer buffer = new StringBuffer();
			buffer.append("update sett_TransInterestSettlement set nStatusID=?,dtAutoExecute=sysdate where ID=?");
			log.info(buffer.toString());
			ps = con.prepareStatement(buffer.toString());
			int index = 1;
			ps.setLong(index++, lStatusID);
			ps.setLong(index++, lID);
			int i = ps.executeUpdate();
			if (i > 0)
			{
				lReturn = lID;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			try
			{
				cleanup(rs);
				cleanup(ps);
				if (con == null)
				{
					cleanup(conInternal);
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw e;
			}
		}
		return lReturn;
	}
	/**
	 * 修改结息表数据的方法：
	 * 逻辑说明：
	 * 
	 * @param info, InterestSettmentInfo, 结息交易实体类
	 * @return long, 新生成记录的标识
	 * @throws IException
	 */
	public long update(Connection con, InterestSettmentInfo info) throws Exception
	{
		long lReturn = -1;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conInternal = null;
		if (con == null)
		{
			conInternal = getConnection();
		}
		else
		{
			conInternal = con;
		}
		try
		{
			//利用数据库的序列号取ID;
			//long id= super.getSett_TransFixedDrawID();
			//info.setID(id);
			StringBuffer buffer = new StringBuffer();
			buffer.append("update sett_TransInterestSettlement set \n");
			buffer.append("nOfficeID=?,nCurrencyID=?,sTransNo=?, \n");
			buffer.append("nTransactionTypeID=?,nAccountID=?, \n");
			buffer.append("nSubAccountID=?,nAccountTypeID=?,nInterestType=?, \n");
			buffer.append("nOperationType=?,dtInterestSettlement=?,dtInterestStart=?, \n");
			buffer.append("dtInterestEnd=?,nInterestDays=?,mBaseBalance=?, \n");
			buffer.append("mRate=?,mPecisionInterest=?,mInterest=?, \n");
			buffer.append("mNegotiateBalance=?,mNegotiateRate=?,mNegotiatePecisionInterest=?, \n");
			buffer.append("mNegotiateInterest=?,mInterestTaxRate=?,mInterestTax=?, \n");
			buffer.append("nPayInterestAccountID=?,nReceiveInterestAccountID=?,dtExecute=?, \n");
			buffer.append("nInputUserID=?,sAbstract=?,sCheckAbstract=?, \n");
			buffer.append("nIsSave=?,nIsKeepAccount=?,nIsSuccess=?,sFaultReason=?, \n");
			buffer.append("nInterestFeeSettingDetailID =?,dtAutoExecute=sysdate,nBatchNo=?,nStatusID=? \n");
			buffer.append("where ID=? \n");

			ps = con.prepareStatement(buffer.toString());
			log.info(buffer.toString());

			int index = 1;
			ps.setLong(index++, info.getOfficeID());
			ps.setLong(index++, info.getCurrencyID());
			ps.setString(index++, info.getTransNo());
			ps.setLong(index++, info.getTransactionTypeID());
			ps.setLong(index++, info.getAccountID());
			ps.setLong(index++, info.getSubAccountID());
			ps.setLong(index++, info.getAccountTypeID());
			ps.setLong(index++, info.getInterestType());
			ps.setLong(index++, info.getOperationType());
			ps.setTimestamp(index++, info.getInterestSettlementDate());
			ps.setTimestamp(index++, info.getInterestStartDate());
			ps.setTimestamp(index++, info.getInterestEndDate());
			ps.setLong(index++, info.getInterestDays());
			ps.setDouble(index++, info.getBaseBalance());
			ps.setDouble(index++, info.getRate());
			ps.setDouble(index++, info.getPecisionInterest());
			ps.setDouble(index++, info.getInterest());
			ps.setDouble(index++, info.getNegotiateBalance());
			ps.setDouble(index++, info.getNegotiateRate());
			ps.setDouble(index++, info.getNegotiatePecisionInterest());
			ps.setDouble(index++, info.getNegotiateInterest());
			ps.setDouble(index++, info.getInterestTaxRate());
			ps.setDouble(index++, info.getInterestTax());
			ps.setLong(index++, info.getPayInterestAccountID());
			ps.setLong(index++, info.getReceiveInterestAccountID());
			ps.setTimestamp(index++, info.getExecuteDate());
			ps.setLong(index++, info.getInputUserID());
			ps.setString(index++, info.getAbstract());
			ps.setString(index++, info.getCheckAbstract());
			ps.setLong(index++, info.getIsSave());
			ps.setLong(index++, info.getIsKeepAccount());
			ps.setLong(index++, info.getIsSuccess());
			ps.setString(index++, info.getFaultReason());
			ps.setLong(index++, info.getTransInterestFeeID());
			ps.setLong(index++, info.getBatchNo());
			ps.setLong(index++, info.getStatusID());
			ps.setLong(index++, info.getID());

			int i = ps.executeUpdate();
			if (i > 0)
			{
				lReturn = info.getID();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			try
			{
				cleanup(rs);
				cleanup(ps);
				if (con == null)
				{
					cleanup(conInternal);
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw e;
			}
		}
		return lReturn;
	}
	/**
	 * 
	 * @param info
	 * @return
	 * @throws Exception
	 */
	private long getMaxID(Connection con) throws Exception
	{
		long lMaxID = -1;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			String strSQL = "select nvl(max(id)+1,1) from sett_TransInterestSettlement ";
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs.next())
			{
				lMaxID = rs.getLong(1);
			}
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

		}
		return lMaxID;
	}
	private long getID(Connection conn) throws Exception
	{
		long id = -1;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			StringBuffer sb = new StringBuffer();
			sb.append("select SEQ_TRANSINTERESTSETTLEMENT.nextval nextid from dual");
			ps = conn.prepareStatement(sb.toString());
			rs = ps.executeQuery();
			if (rs.next())
			{
				id = rs.getLong("nextid");
			}
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
		}
		return id;
	}
	/**
	 * 计提定期账户的利息。
	 * 计提某个定期账户的利息，假设传入的账户都是定期账户。
	 * @param con 数据库连接，由外部传入，never null.
	 * @param resultInfo 包含要计提的账户相关信息的实体，never null.
	 * @return int value:
	 * @throws Excetion 任何系统异常发生时。
	  */
	public int drawingFixedInterest(Connection con, InterestQueryResultInfo resultInfo) throws Exception
	{
		return -1;
	}

	/**
	* 计提自营贷款的利息。
	* 计提某个自营贷款的利息，假设传入的账户都是自营贷款账户。如果该自营贷款账户不允许计提而操作员
	* 进行强行计提，只要设置合同放款单的是否计提成功标志；否则还要进行结息操作。
	* @param con 数据库连接，由外部传入，never null.
	* @param resultInfo 包含要计提的账户相关信息的实体，never null.
	* @return int value:
	* @throws Excetion 任何系统异常发生时。
	 */
	public int drawingSelfLoadInterest(Connection con, InterestQueryResultInfo resultInfo) throws Exception
	{
		return -1;
	}

	/**
	 * 冲销计提利息。	 
	 * @param con 数据库连接，由外部传入，never null.
	 * @param resultInfo 包含要冲销计提的账户相关信息的实体，never null.
	 * @return int value
	 * @throws Excetion 任何系统异常发生时。
	  */
	public int clearDrawingInterest(Connection con, InterestQueryResultInfo resultInfo) throws Exception
	{
		return -1;
	}

	/**
	 * 结算利息。	 
	 * @param con 数据库连接，由外部传入，never null.
	 * @param resultInfo 包含要结算的账户相关信息的实体，never null.
	 * @return int value
	 * @throws Excetion 任何系统异常发生时。
	  */
	public int settleInterest(Connection con, InterestQueryResultInfo resultInfo) throws Exception
	{
		return -1;
	}

	public long getNextBatchNo(Connection conn, long lOfficeID, long lCurrencyID)throws Exception
	{
		long lResult = -1;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			Timestamp time = Env.getSystemDate(conn, lOfficeID, lCurrencyID);
			SimpleDateFormat fmtDate = new SimpleDateFormat("yyyyMMdd");

			String strTemp = fmtDate.format(time);

			StringBuffer sb = new StringBuffer();
			sb.append("select decode(nvl(max(nBatchNo), -1),-1,");
			sb.append(strTemp);
			sb.append("0001,max(nBatchNo + 1)) AS BatchNo \n");
			sb.append("from sett_transInterestSettlement \n");
			sb.append("where dtExecute =to_date('");
			sb.append(strTemp);
			sb.append("', 'yyyymmdd')");
			
			ps = conn.prepareStatement(sb.toString());
			rs = ps.executeQuery();
			if (rs.next())
			{
				lResult = rs.getLong("BatchNo");
			}
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
		}
		return lResult;
	}

	/**
	 * 根据传入的子账户ID查询最近一次结手续费日期（包括部分结手续费操作）
	 * @param con 数据库连接，由外部传入，never null.
	 * @param subaccountID子账户ID.
	 * @return result.
	 * @throws Excetion 任何系统异常发生时。
	 */
	public Timestamp getMaxCommissionDate(Connection con, long subaccountID) throws Exception
	{		
		StringBuffer buffer = new StringBuffer();
		Connection conInternal = null;
		
		if (con == null)
		{
			conInternal = getConnection();
		}
		else
		{
			conInternal = con;
		}
		
		String strSQL = "";
		PreparedStatement ps = null;
		ResultSet rs = null;
		Timestamp result = null; //返回值

		try
		{
			//构造查询语句主体
			buffer.append(" select max(date1) mdate ");
			buffer.append(" from ");
			buffer.append(" ( ");
			buffer.append(" select  t1.ntransactiontypeid,t1.minterest comm,t1.dtinterestsettlement date1 ");
			buffer.append(" from sett_transinterestsettlement t1 ");
			buffer.append(" where t1.ntransactiontypeid = "+SETTConstant.TransactionType.CONSIGN_LOAN_COMMISION_FEE+" ");
			buffer.append(" and t1.nsubaccountid = ? and t1.NSTATUSID!="+SETTConstant.TransactionStatus.DELETED+" ");
			buffer.append(" union all");
			buffer.append(" select t2.ntransactiontypeid,t2.mrealcommission,t2.dtintereststart  date1 ");
			buffer.append(" from sett_transrepaymentloan t2 ");
			buffer.append(" where t2.ntransactiontypeid =  "+SETTConstant.TransactionType.INTERESTFEEPAYMENT+"  and t2.mrealcommission > 0 and t2.NSTATUSID!="+SETTConstant.TransactionStatus.DELETED+" ");
			buffer.append(" and t2.nsubaccountid = ? ");
			buffer.append(" ) ");
			
			strSQL = buffer.toString();

			log.info(strSQL);

			ps = conInternal.prepareStatement(strSQL);
			ps.setLong(1, subaccountID);
			ps.setLong(2, subaccountID);

			//执行查询
			rs = ps.executeQuery();

			//取得结果
			log.info("***********设置返回结果***********");
			DiscountBillInfo dbill = null;

			while (rs.next())
			{
				result = rs.getTimestamp(1);
			}
		}
		catch (Exception e)
		{
			log.error("检索结息记录时发生错误：" + e.getMessage());
			throw e;
		}
		finally  //释放资源
		{ 
			cleanup(rs);
			cleanup(ps);
			if(con==null)
			{
				cleanup(conInternal);
			}
		}

		return result;
	}
	public InterestSettmentInfo findMnegoInterestInfoByID( long lID) throws Exception
	{
		InterestSettmentInfo info = new InterestSettmentInfo();
		Connection conInternal = null;
		conInternal = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{

			String strSQL = "select * from sett_TransInterestSettlement where id=? ";
			log.info(strSQL);
			ps = conInternal.prepareStatement(strSQL);
			ps.setLong(1, lID);
			rs = ps.executeQuery();
			if (rs.next())
			{
				info = getNeInterestSettlementInfo(info, rs);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			try
			{
				cleanup(rs);
				cleanup(ps);
				cleanup(conInternal);
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw e;
			}
		}
		return info;
	}
	
	/**
	 * 根据传入的贴现合同ID和贴现凭证ID查询贴现票据明细
	 * @param con 数据库连接，由外部传入，never null.
	 * @param contractID贴现合同ID，discreID贴现凭证ID，never null.
	 * @return InterestQueryResultInfo[] 满足条件的结息记录主体信息数组，关于利息等信息
	 *         需要调用计息接口计算得出。如果没有符合条件的结息记录，那么返回 null.
	 * @throws Excetion 任何系统异常发生时。
	 */
	public Vector selectDiscountBillRecords(Connection con, long contractID, long discreID) throws Exception
	{		
		StringBuffer buffer = new StringBuffer();
		Connection conInternal = null;
		
		if (con == null)
		{
			conInternal = getConnection();
		}
		else
		{
			conInternal = con;
		}
		
		String strSQL = "";
		PreparedStatement ps = null;
		ResultSet rs = null;
		Vector result = null; //返回值

		try
		{
			//构造查询语句主体
			buffer.append(" SELECT ");
			buffer.append(" ID, NCONTRACTID, NSERIALNO, SUSERNAME, SBANK ");
			buffer.append(" , NISLOCAL, DTCREATE, DTEND, SCODE, MAMOUNT ");
			buffer.append(" , NSTATUSID, NADDDAYS, NDISCOUNTCREDENCEID, OB_NDISCOUNTCREDENCEID, NACCEPTPOTYPEID ");
			buffer.append(" , SFORMEROWNER, MCHECKAMOUNT, NBILLSOURCETYPEID, NISCANSELL, REPURCHASEDATE ");
			buffer.append(" , REPURCHASETERM, NISABATEMENT, NSELLSTATUSID, NOFFICEID, NCURRENCYID ");
			buffer.append(" , NMODULESOURCEID, NDRAFTTYPEID, STRACCEPTORNAME, STRACCEPTORBANK, STRACCEPTORACCOUNT ");
			buffer.append(" , NSTORAGESTATUSID, NSTORAGETRANSID, NQUERYSTATUSID, NCONSIGNSTATUSID, NFORMERCONSTATUSID ");
			buffer.append(" , NCONSIGNTIME, NINPUTUSERID, DTINPUTDATE, NMODIFYUSERID, DTMODIFYDATE ");
			buffer.append(" , NCHECKSTATUS, SCHECKCODE, DTCHECKDATE, NBILLSTATUSID, DTCANCELDATE ");
			buffer.append(" FROM ");
			buffer.append(" loan_discountcontractbill ");
			buffer.append(" WHERE ");
			buffer.append(" ncontractid = "+contractID+" ");
			buffer.append(" and ndiscountcredenceid = "+discreID+" ");
			buffer.append(" and nStatusID = "+Constant.RecordStatus.VALID+" ");
			buffer.append(" Order By ");
			buffer.append(" ID ");
			
			strSQL = buffer.toString();

			log.info(strSQL);

			ps = conInternal.prepareStatement(strSQL);

			//执行查询
			rs = ps.executeQuery();

			//取得结果
			log.info("***********设置返回结果***********");
			DiscountBillInfo dbill = null;
			result = new Vector();

			while (rs.next())
			{
				dbill = new DiscountBillInfo();
				
				dbill.setCreate(rs.getTimestamp("dtcreate"));
				dbill.setEnd(rs.getTimestamp("dtend"));
				dbill.setAmount(rs.getDouble("mamount"));
				dbill.setNbillStatusId(rs.getLong("NBILLSTATUSID"));
				
				result.addElement(dbill);
			}
		}
		catch (Exception e)
		{
			log.error("检索结息记录时发生错误：" + e.getMessage());
			throw e;
		}
		finally  //释放资源
		{ 
			cleanup(rs);
			cleanup(ps);
			if(con==null)
			{
				cleanup(conInternal);
			}
		}

		return result;
	}
}
