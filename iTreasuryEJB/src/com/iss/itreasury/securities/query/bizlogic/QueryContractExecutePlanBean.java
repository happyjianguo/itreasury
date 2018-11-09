/*
 * Created on 2004-7-27
 * 
 * To change the template for this generated file go to Window - Preferences -
 * Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.securities.query.bizlogic;
import com.iss.itreasury.securities.exception.SecuritiesException;
import com.iss.itreasury.securities.query.dataentity.QueryContractExecutePlanParam;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Log4j;
import com.iss.system.dao.PageLoader;
/**
 * @author 王怡
 * 
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */

//点击“合同执行情况信息”按钮，不同的业务进入不同的界面。合同执行情况统计的是结算模块复核完毕的金额。
//（可参考贷款模块中的“贷款实际执行情况查询”）。
public class QueryContractExecutePlanBean
{
	protected Log4j log = new Log4j(Constant.ModuleType.SECURITIES, this);
	private StringBuffer sbSelect = null;
	private StringBuffer sbFrom = null;
	private StringBuffer sbWhere = null;
	private StringBuffer sbOrderBy = null;
//	public final static int OrderBy_BussinessTypeId = 2; 
//	public final static int OrderBy_NoticeFormID = 1; 
//	public final static int OrderBy_InputUserID = 3; 
	


	/**
	 * 返回证券查询的SQLString语句
	 * 
	 * @param queryParam
	 * @return
	 */
	private void getSQL(QueryContractExecutePlanParam queryParam)
	{
		sbSelect = new StringBuffer();
		sbSelect.append("   *  \n");
		sbFrom = new StringBuffer();
		
		sbFrom.append("(     \n");
		//合同ID
		long contractID = queryParam.getContractID();

		sbFrom.append("  select   \n");
		
		sbFrom.append("   SEC_ApplyContract.id as contractId,--合同id  \n");
		sbFrom.append("   decode(TranType.capitaldirection,4,Sett_Transsecurities.dtDate,7,Sett_Transsecurities.dtDate,null)  \n");
		sbFrom.append("   		as ReceivedDate,--收款日期  \n");
		sbFrom.append("   decode(TranType.capitaldirection,5,Sett_Transsecurities.dtDate,6,Sett_Transsecurities.dtDate,null)  \n");
		sbFrom.append("   		as PaiedDate,--付款日期  \n");
		sbFrom.append("   decode(TranType.capitaldirection,4,Sett_Transsecurities.mAmount,7,Sett_Transsecurities.mAmount,0.0)  \n");
		sbFrom.append("   		as ReceivedAmount1,--收款1  \n");
		sbFrom.append("   decode(TranType.capitaldirection,4,Sec_Noticeform.NOTICEINTEREST,7,Sec_Noticeform.NOTICEINTEREST,0.0)  \n");
		sbFrom.append("   		as ReceivedAmount2,--收款2  \n");
		sbFrom.append("   decode(TranType.capitaldirection,5,Sett_Transsecurities.mAmount,6,Sett_Transsecurities.mAmount,0.0)  \n");
		sbFrom.append("   		as PaiedAmount1,--付款1  \n");
		sbFrom.append("   decode(TranType.capitaldirection,5,Sec_Noticeform.NOTICEINTEREST,6,Sec_Noticeform.NOTICEINTEREST,0.0)  \n");
		sbFrom.append("   		as PaiedAmount2,--付款2  \n");
		sbFrom.append("   Sett_Transsecurities.mAmount as Amount,--金额,交易金额,承销金额  \n");
		sbFrom.append("   SEC_ApplyContract.CommissionChargeRate as CommissionChargeRate,--手续费率  \n");
		sbFrom.append("   Sett_Transsecurities.nCurrencyID as CurrencyID,--币种  \n");
		sbFrom.append("   sec_noticeForm.noticeInterest as RealInterest,--已付利息,实际收益  \n");
		sbFrom.append("   SEC_ApplyContract.Rate as ExecuteRate,--执行利率,收益率  \n");
		sbFrom.append("   SEC_ApplyContract.INTERESTBALANCE as interestBalance,--利息合计  \n");
		sbFrom.append("   sett_TransSecurities.dtModify as modifyDate,--修改日期  \n");
		sbFrom.append("   SEC_ApplyContract.TRANSACTIONTYPEID as TransactionTypeID --交易类型id  \n");
		
		sbFrom.append("  from \n");
		
		sbFrom.append("   sec_noticeForm ,SEC_ApplyContract,sett_TransSecurities,Sec_Transactiontype TranType \n");
		
		sbFrom.append("   where 1=1 \n");
		sbFrom.append("   and \n");
		sbFrom.append("   SEC_ApplyContract.ID(+) = sec_noticeForm.ContractID \n");
		sbFrom.append("   and \n");
		sbFrom.append("   sec_noticeform.id = sett_TransSecurities.nSecuritiesNoticeID \n");
		sbFrom.append("   and \n");
		sbFrom.append("   TranType.id(+) = Sec_Noticeform.transactiontypeid \n");
		sbFrom.append("   and \n");
		sbFrom.append("   sett_TransSecurities.NSTATUSID >= 3 \n");
		

		//		===========以下是根据页面传递的参数决定的条件======start====
		//条件1：合同ID
		if (contractID != -1)
		{
			sbFrom.append(" and SEC_ApplyContract.id = " + contractID + " \n");
		}

		//sbFrom拼写结束！！！！！！！！！！！
		sbFrom.append("   )  \n");
		//////////////from////////////////end////////////////////////-
		sbWhere = new StringBuffer();
		//////////////where//////////////-end////////////////////////-
		sbWhere.append(" ");
		//////////////where//////////////-end////////////////////////-
		sbOrderBy = new StringBuffer();

		log.debug("////////////////////////////////////////-");
	}



	public PageLoader queryExecutePlanInfo(QueryContractExecutePlanParam queryParam) throws SecuritiesException
	{


		getSQL(queryParam);
		//
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		log.debug("queryNoticeForm ==sbOrderBy :" + sbOrderBy.toString());
		pageLoader.initPageLoader(
			new AppContext(),
			sbFrom.toString(),
			sbSelect.toString(),
			sbWhere.toString(),
			(int) Constant.PageControl.CODE_PAGELINECOUNT,
			"com.iss.itreasury.securities.query.dataentity.QueryContractExecutePlanInfo",
			null);
		pageLoader.setOrderBy(" " + sbOrderBy.toString() + " ");
		return pageLoader;
	}
}
