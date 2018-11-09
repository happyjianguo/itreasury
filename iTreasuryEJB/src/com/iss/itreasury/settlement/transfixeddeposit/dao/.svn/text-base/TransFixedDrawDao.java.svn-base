package com.iss.itreasury.settlement.transfixeddeposit.dao;

import com.iss.itreasury.settlement.transfixeddeposit.dataentity.QueryByStatusConditionInfo;
import com.iss.itreasury.util.DataFormat;

public class TransFixedDrawDao {
	
	public String queryTransFixedDrawSQL(QueryByStatusConditionInfo qInfo){
		
		StringBuffer sbSQL= new StringBuffer();
		
		//业务处理
		if (qInfo.getTypeID() == 0) 
		{
			//处理时的查找不用加日期（因为有关机处理，暂存与未复核查全部就可以了）			
			sbSQL.append("select aa.*,'通知存款支取' as biztypename \n");
			sbSQL.append("from sett_TransFixedWithDraw aa \n");
			sbSQL.append("where \n");
			sbSQL.append("nOfficeID="+qInfo.getOfficeID()+" \n");
			sbSQL.append("and nCurrencyID="+qInfo.getCurrencyID()+" \n");
			if(qInfo.getTransactionTypeID()>=0){
				sbSQL.append("and nTransActionTypeID="+qInfo.getTransactionTypeID()+" \n");
			}
			if(qInfo.getStatus()!=null)
			{
				//状态查询条件
				String query ="";
				query = getQueryString(qInfo);
				sbSQL.append(" and ("+ query + ") \n");
			}
			sbSQL.append("and nInputUserID="+qInfo.getUserID()+" \n");
			
		}else if (qInfo.getTypeID() == 1) //业务复核
		{
			//处理时的查找不用加日期（因为有关机处理，暂存与未复核查全部就可以了）				
			sbSQL.append("select aa.*,'通知存款支取' as biztypename \n");
			sbSQL.append("from sett_TransFixedWithDraw aa \n");
			sbSQL.append("where \n");
			sbSQL.append("nOfficeID="+qInfo.getOfficeID()+" \n");
			sbSQL.append("and nCurrencyID="+qInfo.getCurrencyID()+" \n");
			if(qInfo.getTransactionTypeID()>=0){
				sbSQL.append("and nTransActionTypeID="+qInfo.getTransactionTypeID()+" \n");
			}
			if(qInfo.getStatus()!=null)
			{
				//状态查询条件
				String query ="";
				query = getQueryString(qInfo);
				sbSQL.append(" and ("+ query + ") \n");
			}	
			sbSQL.append("and nCheckUserID="+qInfo.getUserID()+" \n");
			sbSQL.append("and dtExecute=TO_DATE('" + DataFormat.getDateString(qInfo.getDate()) + "','yyyy-mm-dd') \n");
		}

		return sbSQL.toString();
	}
	
	private String getQueryString(QueryByStatusConditionInfo info) 
	{
		String query = "nStatusID=";
		for(int i=0;i<info.getStatus().length;i++)
		{									
			if(i<info.getStatus().length -1)
			{	
								
				query= query+ info.getStatus()[i] + " or nStatusID=";
			}
			else
			{
				query= query+ info.getStatus()[i];
			}
		}	
		return query;
	}
	


}
