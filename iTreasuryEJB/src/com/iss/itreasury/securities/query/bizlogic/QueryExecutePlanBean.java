/*
 * Created on 2004-4-13
 * 
 * To change the template for this generated file go to Window - Preferences -
 * Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.securities.query.bizlogic;
import java.sql.Timestamp;

import com.iss.itreasury.securities.exception.SecuritiesException;
import com.iss.itreasury.securities.query.dataentity.QueryExecutePlanParam;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Log4j;
import com.iss.system.dao.PageLoader;
/**
 * @author 王怡
 * 
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */

//点击“执行计划信息”按钮，链接到特定录入人在特定状态下的执行计划界面，列表信息包括：
//[执行计划版本号]、[生成日期]、[状态]，点击[执行计划版本号]，链接显示具体该版本下的执行计划列表信息。
public class QueryExecutePlanBean
{
	protected Log4j log = new Log4j(Constant.ModuleType.SECURITIES, this);
	private StringBuffer sbSelect = null;
	private StringBuffer sbFrom = null;
	private StringBuffer sbWhere = null;
	private StringBuffer sbOrderBy = null;
	public final static int OrderBy_PlanVersion = 1; //业务通知单编号
	public final static int OrderBy_InputTime = 2; //业务单位名称
	public final static int OrderBy_StatusID = 3; //业务类型

	/**
	 * 返回证券查询的SQLString语句
	 * 
	 * @param queryParam
	 * @return
	 */
	private void getSQL(QueryExecutePlanParam queryParam)
	{
		sbSelect = new StringBuffer();
		sbSelect.append("   *  \n");
		sbFrom = new StringBuffer();
		
		//列表信息包括：[执行计划版本号]、[生成日期]、[状态]
		
		sbFrom.append("(     \n");
		//合同ID
		long contractID = queryParam.getContractID();
		//申请书ID
		long applyID = queryParam.getApplyID();
		//执行计划版本ID
		long planVersion = queryParam.getPlanVersion();

		sbFrom.append("  select   \n");
		
		sbFrom.append("   SEC_ContractPlan.planVersion,--执行计划版本号  \n");
		sbFrom.append("   SEC_ContractPlan.InputTime, --生成日期  \n");
		sbFrom.append("   SEC_ContractPlan.StatusID --状态  \n");

		sbFrom.append("  from \n");
//		sbFrom.append("   SEC_ContractPlan,sec_applyform,SEC_ApplyContract \n");
		sbFrom.append("   SEC_ContractPlan,SEC_ApplyContract \n");
		
		sbFrom.append("   where 1=1 \n");
//		sbFrom.append("   and SEC_ContractPlan.ApplyID = sec_applyform.id \n");
		sbFrom.append("   and SEC_ContractPlan.ContractID = SEC_ApplyContract.id \n");
		

		//		===========以下是根据页面传递的参数决定的条件======start====
		//条件1：合同ID
		if (contractID != -1)
		{
			sbFrom.append(" and SEC_ApplyContract.id = " + contractID + " \n");
		}
//		//条件2：申请书ID
//		if (applyID != -1)
//		{
//			sbFrom.append(" and sec_applyform.id = " + applyID + " \n");
//		}
		//条件3：执行计划版本ID
		if (planVersion != -1)
		{
			sbFrom.append(" and SEC_ContractPlan.planVersion = " + planVersion + " \n");
		}

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
			case OrderBy_PlanVersion :
				sbOrderBy.append(" \n order by planVersion" + strDesc);
				break;
			case OrderBy_InputTime :
				sbOrderBy.append(" \n order by InputTime" + strDesc);
				break;
			case OrderBy_StatusID :
				sbOrderBy.append(" \n order by StatusID" + strDesc);
				break;
		}
		log.debug("////////////////////////////////////////-");
	}



	public PageLoader queryExecutePlanInfo(QueryExecutePlanParam queryParam) throws SecuritiesException
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
			"com.iss.itreasury.securities.query.dataentity.QueryExecutePlanInfo",
			null);
		pageLoader.setOrderBy(" " + sbOrderBy.toString() + " ");
		return pageLoader;
	}
}
