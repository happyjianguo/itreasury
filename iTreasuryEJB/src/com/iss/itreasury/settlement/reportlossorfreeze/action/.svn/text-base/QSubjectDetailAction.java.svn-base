package com.iss.itreasury.settlement.reportlossorfreeze.action;

import java.util.Map;

import com.iss.itreasury.settlement.reportlossorfreeze.bizlogic.QSubjectDetailBiz;
import com.iss.itreasury.settlement.reportlossorfreeze.dataentity.QuerySubjectInfo;
import com.iss.itreasury.util.DataFormat;
import com.iss.sysframe.pager.dataentity.PagerInfo;

public class QSubjectDetailAction {
	QSubjectDetailBiz biz= new 	QSubjectDetailBiz();
	
	/**
	 * 科目明细查询
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public PagerInfo findSubjectDetail(Map map) throws Exception{
		PagerInfo pagerInfo = null;
		try 
		{ 
			QuerySubjectInfo qInfo =new QuerySubjectInfo();
			String strTemp = "";
			String strExecuteStart = ""; //执行日期--开始
			String strExecuteEnd = ""; //执行日期--结束
			long lTransActionType = -1;
			long officeid=-1;
			String strInsterestStartDate = "";  //起息日 从
			String strInsterestEndDate = "";   //起息日 到
			String strTransNoFrom = "";  //交易号 从
			String strTransNoTo = "";  //交易号 至
			String strTransactionType = "";  //交易类型
			/**
			 * 获得提交变量
			 */
			
			strTemp = (String) map.get("lOfficeID".toLowerCase());
			if( strTemp != null && strTemp.length() > 0 )
			{
				officeid = Long.parseLong(strTemp.trim());
			}
			strTemp = (String) map.get("hdnTransActionType".toLowerCase());
			if( strTemp != null && strTemp.length() > 0 )
			{
				lTransActionType = Long.parseLong(strTemp.trim());
			}
			strTemp = (String) map.get("startdate".toLowerCase());
			if( strTemp != null && strTemp.length() > 0 )
			{
				strExecuteStart = strTemp.trim();
			
			}
			strTemp = (String) map.get("enddate".toLowerCase());
			if( strTemp != null && strTemp.length() > 0 )
			{
				strExecuteEnd = strTemp.trim();
				
			}

			strTemp = (String) map.get("interestStartDate".toLowerCase());
			if( strTemp != null && strTemp.length() > 0 )
			{
				strInsterestStartDate = strTemp.trim();
				
			}
			
			strTemp = (String) map.get("interestEndDate".toLowerCase());
			if( strTemp != null && strTemp.length() > 0 )
			{
				strInsterestEndDate = strTemp.trim();
				
			}		
			
			strTemp = (String) map.get("TransNoFrom".toLowerCase());
			if( strTemp != null && strTemp.length() > 0 )
			{
				strTransNoFrom = strTemp.trim();
				
			}		 
			 
			strTemp = (String) map.get("TransNoTo".toLowerCase());
			if( strTemp != null && strTemp.length() > 0 )
			{
				strTransNoTo = strTemp.trim();
				
			}		

			strTemp = (String) map.get("strTransactionType".toLowerCase());
			if( strTemp != null && strTemp.length() > 0 )
			{
				strTransactionType = strTemp.trim();
				
			}	
		
			qInfo.setNtransactiontypeid(lTransActionType);
		
			
			if( strExecuteStart != null && strExecuteStart.length() > 0 )
			{
				qInfo.setStartDate(DataFormat.getDateTime((strExecuteStart)));
			}
			if( strExecuteEnd != null && strExecuteEnd.length() > 0 )
			{
				qInfo.setEndDate(DataFormat.getDateTime((strExecuteEnd)));
			}
			
			if( strInsterestStartDate != null && strInsterestStartDate.length() > 0 )
			{
				qInfo.setDtintereststartFrom(DataFormat.getDateTime((strInsterestStartDate)));
			}
			
			if( strInsterestEndDate != null && strInsterestEndDate.length() > 0 )
			{
				qInfo.setDtintereststartTo(DataFormat.getDateTime((strInsterestEndDate)));
			}			
			
			qInfo.setStrTransNoFrom(strTransNoFrom);
			qInfo.setStrTransNoTo(strTransNoTo);		
			qInfo.setOfficeId(officeid);
			qInfo.setStrTransactionType(strTransactionType);
			
			pagerInfo = biz.findSubjectDetail(qInfo);
			
		} 
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		
		return pagerInfo;
	}

}
	
