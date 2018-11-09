/*
 * Created on 2004-6-25
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.securities.query.bizlogic;

import java.sql.Timestamp;

import com.iss.itreasury.securities.exception.SecuritiesException;
import com.iss.itreasury.securities.query.dataentity.*;
import com.iss.itreasury.securities.util.SECConstant;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Log4j;
import com.iss.itreasury.util.Constant;
import com.iss.system.dao.PageLoader;
import com.iss.itreasury.util.Env;
/**
 * @author gdzhao
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class QuerySecuritiesContractBean
{
	protected Log4j log = new Log4j(Constant.ModuleType.SECURITIES, this);
	
	private StringBuffer sbSelect   = null;
	private StringBuffer sbFrom     = null;
	private StringBuffer sbWhere    = null;
	private StringBuffer sbOrderBy  = null;
	
	public final static int OrderBy_ContractCode       = 1; //合同编号
	public final static int OrderBy_ApplyFormCode      = 2; //对应申请书编号
	public final static int OrderBy_BusinessTypeName   = 3; //业务类型
	public final static int OrderBy_CounterpartName    = 4; //交易对手名称
	public final static int OrderBy_ContractInputDate  = 5; //合同录入日期
	public final static int OrderBy_ContractStatusID   = 6; //合同状态
	public final static int OrderBy_NextCheckUserID    = 7; //最后审核人
	public final static int OrderBy_InputUserID        = 8; //录入人
	public final static int OrderBy_Amount             = 9; //合同金额
	public final static int OrderBy_TransactionDate    = 10; //成交日期
	public final static int OrderBy_Term               = 11; //期限

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
		//需要查出来的在v002显示的信息
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
		
		//是否查询需要展期的合同
		long isQueryExtend = queryParam.getIsQeuryExtend();


		sbFrom.append("  select \n");
		sbFrom.append("   sec_applyContract.id as contractId,--合同id  \n");
		sbFrom.append("   sec_applyContract.code as code,--合同编号  \n");
		sbFrom.append("   sec_applyContract.ApplyID as applyID ,--对应申请书编号  \n");
		sbFrom.append("   SEC_BUSINESSTYPE.id as businessTypeId ,--业务类型id  \n");
		sbFrom.append("   sec_applyContract.TRANSACTIONTYPEID as transactionTypeId,--交易类型id  \n");
		sbFrom.append("   sec_applyContract.CounterpartID as counterpartID ,--交易对手id  \n");
		sbFrom.append("   sec_applyContract.InputDate as inputDate ,--合同录入日期  \n");
		sbFrom.append("   sec_applyContract.StatusID as statusID ,--合同状态  \n");
		sbFrom.append("   sec_applyContract.NextCheckUserID as nextCheckUserID ,--审核人，也作为参数查出审核状态  \n");
		sbFrom.append("   sec_applyContract.InputUserID as inputUserID ,--录入人  \n");
		sbFrom.append("   sec_applyContract.Amount as amount ,--合同金额  \n");
		sbFrom.append("   sec_applyContract.TransactionDate as transactionDate ,--成交日期(委托日期)  \n");
		sbFrom.append("   sec_applyContract.Term as term --期限  \n");
		
		

		sbFrom.append("  from \n");
		sbFrom.append("   sec_applyContract ,SEC_BUSINESSTYPE \n");
		
		sbFrom.append("   where 1=1 \n");
		sbFrom.append("   and substr(sec_applyContract.TRANSACTIONTYPEID,0,2) = SEC_BUSINESSTYPE.id \n");
		sbFrom.append("   and SEC_BUSINESSTYPE.Statusid <> 0 \n");
		
		//		===========以下是根据页面传递的参数决定的条件======start====
		//条件1：业务类型ID
		if (businessTypeId != -1)
		{
			sbFrom.append(" and sec_applyContract.TRANSACTIONTYPEID = " + businessTypeId + " \n");
		}
		
		//条件2：开始合同编号
		if (startContractId != -1)
		{
			sbFrom.append(" and sec_applyContract.id >= " + startContractId + " \n");
		}
		
		//条件3：结束合同编号
		if (endContractId != -1)
		{
			sbFrom.append(" and sec_applyContract.id <= " + endContractId + " \n");
		}
		
		//条件4：交易对手
		if (counterpartId != -1)
		{
			sbFrom.append(" and sec_applyContract.CounterpartID = " + counterpartId + " \n");
		}		
		
		//条件5：合同录入日期开始日
		if (!"".equals(strStartDate))
		{
			sbFrom.append(" and to_char(sec_applyContract.InputDate , 'yyyy-mm-dd') >= " + "'" + strStartDate + "'\n");
		}
		//条件6：合同录入日期结束日
		if (!"".equals(strEndDate))
		{
			sbFrom.append(" and to_char(sec_applyContract.InputDate , 'yyyy-mm-dd') <= " + "'" + strEndDate + "'\n");
		}
		//条件7：合同状态
		if (statusID != -1)
		{
			sbFrom.append(" and sec_applyContract.StatusID = " + statusID + " \n");
		}
		//条件8：录入人
		if (inputUserId != -1)
		{
			sbFrom.append(" and sec_applyContract.InputUserID = " + inputUserId + " \n");
		}
		
		//-----------委托理财展期业务需求变更  start
		if(isQueryExtend != -1)
		{//状态为执行中或者已展期的合同允许展期
			sbFrom.append(" and sec_applyContract.StatusID in ("+SECConstant.ContractStatus.ACTIVE +","+SECConstant.ContractStatus.EXTEND+")");
		}
        //-----------委托理财展期业务需求变更  end
		
		//sbFrom拼写结束！！！！！！！！！！！
		sbFrom.append("   )  \n");
		//////////////from////////////////end////////////////////////-
		sbWhere = new StringBuffer();
		//////////////where//////////////-end////////////////////////-
		sbWhere.append(" ");
		//////////////where//////////////-end////////////////////////-
		sbOrderBy = new StringBuffer();
		String strDesc = queryParam.getDesc() == 1 ? " desc " : "";
		//log.debug( "////queryParam.getDesc() //////////-" +
		// queryParam.getDesc());
		
		switch ((int) queryParam.getOrderField())
		{
			case OrderBy_ContractCode :
				sbOrderBy.append(" \n order by code" + strDesc);
				break;
			case OrderBy_ApplyFormCode :
				sbOrderBy.append(" \n order by applyID" + strDesc);
				break;
			case OrderBy_BusinessTypeName :
				sbOrderBy.append(" \n order by businessTypeId" + strDesc);
				break;
			case OrderBy_CounterpartName :
				sbOrderBy.append(" \n order by CounterpartID" + strDesc);
				break;
			case OrderBy_ContractInputDate :
				sbOrderBy.append(" \n order by InputDate" + strDesc);
				break;
			case OrderBy_ContractStatusID :
				sbOrderBy.append(" \n order by StatusID" + strDesc);
				break;
			case OrderBy_NextCheckUserID :
				sbOrderBy.append(" \n order by NextCheckUserID" + strDesc);
				break;
			case OrderBy_InputUserID :
				sbOrderBy.append(" \n order by InputUserID" + strDesc);
				break;
			case OrderBy_Amount :
				sbOrderBy.append(" \n order by Amount" + strDesc);
				break;
			case OrderBy_TransactionDate :
				sbOrderBy.append(" \n order by TransactionDate" + strDesc);
				break;
			case OrderBy_Term :
				sbOrderBy.append(" \n order by Term" + strDesc);
				break;
		}
		log.debug("////////////////////////////////////////-");
	}

	/**
	 * 生成PageLoader
	 * 
	 * @param queryParam
	 * @return @throws
	 *         SecuritiesException
	 */
	public PageLoader queryContractInfo(QuerySecuritiesContractParam queryParam)
		throws SecuritiesException {
			/*if (Env.getProjectName().equalsIgnoreCase(Constant.ProjectName.CNMEF))
			{
				System.out.println("项目名称：国机;模块：合同查询");
				//getCNMEFSQL(queryParam);
			}
			else
			{*/
				System.out.println("项目名称：--;模块：合同查询");
				getSQL(queryParam);
			//}
			//
			PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
			log.debug("queryNoticeForm ==sbOrderBy :" + sbOrderBy.toString());
			pageLoader.initPageLoader(
				new AppContext(),
				sbFrom.toString(),
				sbSelect.toString(),
				sbWhere.toString(),
				(int) Constant.PageControl.CODE_PAGELINECOUNT,
				"com.iss.itreasury.securities.query.dataentity.QuerySecuritiesContractInfo",
				null);
			pageLoader.setOrderBy(" " + sbOrderBy.toString() + " ");
			return pageLoader;
	}


}
