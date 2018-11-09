package com.iss.itreasury.securities.query.bizlogic;

import java.sql.Timestamp;

import com.iss.itreasury.securities.exception.SecuritiesException;
import com.iss.itreasury.securities.query.dataentity.QuerySecuritiesContractParam;
import com.iss.itreasury.securities.util.SECConstant;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Log4j;
import com.iss.system.dao.PageLoader;

/**
 * @Name: QueryExtendContractBean.java @Description: @
 * 
 * @Author : gqfang 
 * @Create Date: 2005-4-26 To change the template for this
 * generated type comment go to Window - Preferences - Java - Code Generation -
 * Code and Comments
 */
public class QueryExtendContractBean
{
	protected Log4j log = new Log4j(Constant.ModuleType.SECURITIES, this);

	private StringBuffer sbSelect = null;
	private StringBuffer sbFrom = null;
	private StringBuffer sbWhere = null;
	private StringBuffer sbOrderBy = null;

	public final static int OrderBy_ContractCode = 1;       //原合同编号
	public final static int OrderBy_ExtendContractCode = 2; //展期合同编号
	public final static int OrderBy_Amount = 3;             //展期金额
	public final static int OrderBy_ExtendStartDate = 4;    //展期起始日
	public final static int OrderBy_ExtendEndDate = 5;      //展期到期日
	public final static int OrderBy_Term = 6;               //展期期限
	public final static int OrderBy_Rate = 7;               //展期利率
	public final static int OrderBy_ContractStatusID = 8;   //展期合同状态
	

	/**
	 * 返回合同查询的SQLString语句
	 * 
	 * @param queryParam
	 * @return
	 */
	private void getSQL(QuerySecuritiesContractParam queryParam)
	{
		System.out.println("Log========================================================AAA");
		sbSelect = new StringBuffer();
		sbSelect.append("   *  \n");
		sbFrom = new StringBuffer();
		
		//[合同编号]、[对应申请书编号]、[业务类型]、[交易对手名称]、[合同录入日期]、[合同状态]、[审核状态]、[最后审核人]、[录入人]
		sbFrom.append("(     \n");
		//条件1：业务类型id
		long businessTypeId = queryParam.getBusinessTypeId();
		//条件2：开始合同编号
		long startContractId = queryParam.getStartContractId();
		//条件3：结束合同编号
		long endContractId = queryParam.getEndContractId();
		//条件4：交易对手
		long counterpartId = queryParam.getCounterpartId();
		//条件5：合同录入日期开始日
		Timestamp startDate = queryParam.getStartDate();
		String strStartDate = DataFormat.getDateString(startDate);
		//条件6：合同录入日期结束日
		Timestamp endDate = queryParam.getEndDate();
		String strEndDate = DataFormat.getDateString(endDate);
		//条件7：合同状态
		long statusID = queryParam.getStatusID();
		//条件8：录入人
		long inputUserId = queryParam.getInputUserId();
		
		sbFrom.append("  select \n");
		sbFrom.append("   SEC_EXTENDFORM.id as id,--展期合同id  \n");
		sbFrom.append("   SEC_EXTENDFORM.code as code,--展期合同编号  \n");
		sbFrom.append("   SEC_EXTENDFORM.ApplyContractID as applyContractId ,--对应原合同id  \n");
		sbFrom.append("   SEC_BUSINESSTYPE.id as businessTypeId ,--业务类型id  \n");
		sbFrom.append("   SEC_EXTENDFORM.TRANSACTIONTYPEID as transactionTypeId,--交易类型id  \n");
		//sbFrom.append("   sec_applyContract.CounterpartID as counterpartID ,--交易对手id  \n");
		sbFrom.append("   SEC_EXTENDFORM.InputDate as inputDate ,--展期合同录入日期  \n");
		sbFrom.append("   SEC_EXTENDFORM.ExtendStartDate as extendStartDate ,--展期合同开始日  \n");
		sbFrom.append("   SEC_EXTENDFORM.ExtendEndDate as extendEndDate ,--展期合同到期日  \n");
		sbFrom.append("   SEC_EXTENDFORM.StatusID as statusId ,--展期合同状态  \n");
		sbFrom.append("   SEC_EXTENDFORM.NextCheckUserID as nextCheckUserId ,--审核人，也作为参数查出审核状态  \n");
		sbFrom.append("   SEC_EXTENDFORM.InputUserID as inputUserId ,--录入人  \n");
		sbFrom.append("   SEC_EXTENDFORM.Amount as amount ,--展期金额  \n");
		sbFrom.append("   SEC_EXTENDFORM.Rate as rate ,--展期利率  \n");
	    //sbFrom.append("   sec_applyContract.TransactionDate as transactionDate ,--成交日期(委托日期)  \n");
		sbFrom.append("   SEC_EXTENDFORM.Term as term --期限  \n");

		sbFrom.append("  from \n");
		sbFrom.append("   SEC_EXTENDFORM ,SEC_BUSINESSTYPE \n");

		sbFrom.append("   where 1=1 \n");
		sbFrom.append("   and substr(SEC_EXTENDFORM.TRANSACTIONTYPEID,0,2) = SEC_BUSINESSTYPE.id \n");
		//		===========以下是根据页面传递的参数决定的条件======start====
		//条件1：业务类型ID
		if (businessTypeId != -1)
		{
			sbFrom.append(" and SEC_EXTENDFORM.TRANSACTIONTYPEID = " + businessTypeId + " \n");
		}
		//条件2：开始合同(原合同)编号
		if (startContractId != -1)
		{
			sbFrom.append(" and SEC_EXTENDFORM.applyContractId >= " + startContractId + " \n");
		}
		//条件3：结束合同(原合同)编号
		if (endContractId != -1)
		{
			sbFrom.append(" and SEC_EXTENDFORM.applyContractId <= " + endContractId + " \n");
		}
		//条件4：交易对手
		//if (counterpartId != -1)
		//{
		//	sbFrom.append(" and sec_applyContract.CounterpartID = "
		//			+ counterpartId + " \n");
		//}
		//条件5：合同录入日期开始日
		if (!"".equals(strStartDate))
		{
			sbFrom.append(" and to_char(SEC_EXTENDFORM.InputDate , 'yyyy-mm-dd') >= " + "'" + strStartDate + "'\n");
		}
		//条件6：合同录入日期结束日
		if (!"".equals(strEndDate))
		{
			sbFrom.append(" and to_char(SEC_EXTENDFORM.InputDate , 'yyyy-mm-dd') <= " + "'" + strEndDate + "'\n");
		}
		//条件7：合同状态
		if (statusID != -1)
		{
			sbFrom.append(" and SEC_EXTENDFORM.StatusID = " + statusID + " \n");
		}
		//条件8：录入人
		if (inputUserId != -1)
		{
			sbFrom.append(" and SEC_EXTENDFORM.InputUserID = " + inputUserId + " \n");
		}
		
		//sbFrom拼写结束！！！！！！！！！！！
		sbFrom.append("   )  \n");
		
		sbWhere = new StringBuffer();
		
		sbWhere.append(" ");
		
		sbOrderBy = new StringBuffer();
		String strDesc = queryParam.getDesc() == 1 ? " desc " : "";
		
		switch ((int) queryParam.getOrderField())
		{
			case OrderBy_ContractCode :
				sbOrderBy.append(" \n order by applyContractId " + strDesc);
				break;
			case OrderBy_ExtendContractCode :
				sbOrderBy.append(" \n order by id " + strDesc);
				break;
			case OrderBy_Amount :
				sbOrderBy.append(" \n order by Amount" + strDesc);
				break;
			case OrderBy_ExtendStartDate :
				sbOrderBy.append(" \n order by extendStartDate " + strDesc);
				break;
			case OrderBy_ExtendEndDate :
				sbOrderBy.append(" \n order by extendEndDate " + strDesc);
				break;
			case OrderBy_Term :
				sbOrderBy.append(" \n order by Term" + strDesc);
				break;
			case OrderBy_Rate :
				sbOrderBy.append(" \n order by Rate " + strDesc);
				break;
			case OrderBy_ContractStatusID :
				sbOrderBy.append(" \n order by StatusID " + strDesc);
				break;
		}
		
	}
	/**
	 * 生成PageLoader
	 * 
	 * @param queryParam
	 * @return @throws
	 * SecuritiesException
	 */
	public PageLoader queryContractInfo(QuerySecuritiesContractParam queryParam) throws SecuritiesException
	{

		getSQL(queryParam);
		
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
	
		log.debug("queryContractInfo ==sbOrderBy :" + sbOrderBy.toString());
		
		pageLoader .initPageLoader(
						new AppContext(),
						sbFrom.toString(),
						sbSelect.toString(),
						sbWhere.toString(),
						(int) Constant.PageControl.CODE_PAGELINECOUNT,
						"com.iss.itreasury.securities.securitiescontractextend.dataentity.SecuritiesContractExtendInfo",
						null);
		
		pageLoader.setOrderBy(" " + sbOrderBy.toString() + " ");
		
		return pageLoader;
	}
}