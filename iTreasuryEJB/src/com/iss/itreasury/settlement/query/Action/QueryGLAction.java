package com.iss.itreasury.settlement.query.Action;

import java.sql.Timestamp;
import java.util.Map;

import com.iss.itreasury.settlement.query.bizlogic.QueryGLBiz;
import com.iss.itreasury.util.DataFormat;
import com.iss.sysframe.pager.dataentity.PagerInfo;

/**
 * �����������ܲ�ѯ
 * @author songwenxiao
 *
 */
public class QueryGLAction {
	
	QueryGLBiz biz = new QueryGLBiz();
	
	public PagerInfo queryGL(Map map) throws Exception
	{
		PagerInfo pagerInfo = null;
		try
		{
			
			//�������
			long lCurrencyID = -1; 
		   	long lOfficeID = -1; 
			Timestamp tsDate = null;//��ѯ����
			String strBankCodeStart="";//��ѯ��ʼ���к�
			String strBankCodeEnd = "";//��ѯ��ֹ���к�
			String strTemp = "";	
			String lCurrencyType="";


			//��ò�ѯ����
			strTemp = (String) map.get("tsDate".toLowerCase());
		    if(strTemp != null && strTemp.length() > 0)
			{
				tsDate = DataFormat.getDateTime(strTemp);
			}
			
			strTemp = (String) map.get("strBankStartCode".toLowerCase());
			if(strTemp != null && strTemp.length() > 0)
			{ 
				strBankCodeStart = strTemp;
			}
			
			strTemp = (String) map.get("strBankEndCode".toLowerCase());
		    if(strTemp != null && strTemp.length() > 0)
			{
				strBankCodeEnd = strTemp;
			}
		    
		    strTemp = (String) map.get("lOfficeID".toLowerCase());
		    if(strTemp != null && strTemp.length() > 0)
		    {
		    	lOfficeID = Long.valueOf(strTemp);
		    }
		    
		    strTemp = (String) map.get("lCurrencyID".toLowerCase());
		    if(strTemp != null && strTemp.length() > 0)
		    {
		    	lCurrencyID = Long.valueOf(strTemp);
		    }
		    
		    strTemp = (String) map.get("lCurrencyType".toLowerCase());
		    if(strTemp != null && strTemp.length() > 0)
		    {
		    	lCurrencyType = strTemp;
		    }

			long lUnit = 1;
			String strUnit = (String) map.get("lUnit".toLowerCase());
		    if (strUnit!=null && strUnit.trim().length()>0){
		    	lUnit = Long.parseLong(strUnit);
		    }
		    
			
			pagerInfo = biz.getTodayLoanAcountBalace(lOfficeID, lCurrencyID, tsDate, strBankCodeStart, strBankCodeEnd, lUnit,lCurrencyType);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
	}

}
