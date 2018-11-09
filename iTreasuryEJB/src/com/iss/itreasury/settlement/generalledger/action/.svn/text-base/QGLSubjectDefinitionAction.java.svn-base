package com.iss.itreasury.settlement.generalledger.action;

import java.util.Map;

import com.iss.itreasury.settlement.generalledger.bizlogic.QGLSubjectDefinitionBiz;
import com.iss.itreasury.settlement.generalledger.dataentity.GLSubjectDefinitionInfo;
import com.iss.sysframe.pager.dataentity.PagerInfo;

public class QGLSubjectDefinitionAction {

	QGLSubjectDefinitionBiz qglsubjectdefinitionbiz = new QGLSubjectDefinitionBiz();

	public PagerInfo queryAccount(Map map) throws Exception {
		
		PagerInfo pagerInfo = null;
		String strTemp;
		// 如果没有设定排序的条件,缺省按照交易号
		GLSubjectDefinitionInfo condition = new GLSubjectDefinitionInfo();

		condition = new GLSubjectDefinitionInfo();

		//办事处ID			
		condition.setOfficeID(Long.valueOf((String)map.get("lofficeid")).longValue());

		//币种号		
		condition.setCurrencyID(Long.valueOf((String)map.get("lcurrencyid")).longValue());

		//科目代码
		strTemp = (String)map.get("subjectNo");
		if(strTemp != null && !strTemp.equals(""))
		{
			condition.setSubcode(strTemp);
		}

		//科目名称
		strTemp = (String)map.get("subjectName");
		if(strTemp != null && !strTemp.equals(""))
		{
			condition.setSegmentName2(strTemp);
		}
		//科目类型
		strTemp = (String)map.get("subjectType");
		if(strTemp != null && !strTemp.equals(""))
		{
			condition.setSubjectType(Long.parseLong(strTemp));
		}

		//余额方向
		strTemp = (String)map.get("balanceDirection");
		if(strTemp != null && !strTemp.equals(""))
		{
			condition.setBalanceDirection(Long.parseLong(strTemp));
		}

		//发生额方向
		strTemp = (String)map.get("amountDirection");
		if(strTemp != null && !strTemp.equals(""))
		{
			condition.setAmountDirection(Long.parseLong(strTemp));
		}

		pagerInfo = qglsubjectdefinitionbiz.queryGLSubjectDefinition(condition);
		return pagerInfo;
	}
}
