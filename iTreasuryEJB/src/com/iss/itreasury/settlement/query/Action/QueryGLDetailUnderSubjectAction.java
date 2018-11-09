package com.iss.itreasury.settlement.query.Action;

import java.sql.Timestamp;
import java.util.Map;

import com.iss.itreasury.settlement.query.bizlogic.QueryGLDetailBiz;
import com.iss.itreasury.settlement.query.bizlogic.QueryGLDetailUnderSubjectBiz;
import com.iss.itreasury.util.DataFormat;
import com.iss.sysframe.pager.dataentity.PagerInfo;

/**
 * ������������&�ս��Ŀ����_��Ŀ���µĲ�ѯ
 * @author songwenxiao
 *
 */
public class QueryGLDetailUnderSubjectAction {
	
	QueryGLDetailUnderSubjectBiz biz = new QueryGLDetailUnderSubjectBiz();
	
	public PagerInfo queryGLDetailUnderSubject(Map map) throws Exception
	{
		PagerInfo pagerInfo = null;
		try
		{
			//�������
			long lCurrencyID = -1; 
		   	long lOfficeID = -1; 
		  //�õ�����
			String strTemp = "";
			String strTransNo = "";
			String lCurrencyType="";


	
		    
		    strTemp = (String) map.get("strTransNo".toLowerCase());
		    if(strTemp != null && strTemp.length() > 0)
		    {
		    	strTransNo = strTemp;
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
		    
		   
			  
			pagerInfo = biz.findGLDetailsUnderSubject(null, null, strTransNo, lOfficeID, lCurrencyID, -1, -1, null, null,lCurrencyType);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
	}

}
