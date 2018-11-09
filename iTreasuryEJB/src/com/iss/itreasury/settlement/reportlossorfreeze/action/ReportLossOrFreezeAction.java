package com.iss.itreasury.settlement.reportlossorfreeze.action;

import java.sql.Timestamp;
import java.util.Map;

import com.iss.itreasury.settlement.reportlossorfreeze.bizlogic.ReportLossOrFreezeBiz;
import com.iss.itreasury.settlement.reportlossorfreeze.dataentity.ReportLossOrFreezeQueryInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.DataFormat;
import com.iss.sysframe.pager.dataentity.PagerInfo;

public class ReportLossOrFreezeAction {
	ReportLossOrFreezeBiz biz= new 	ReportLossOrFreezeBiz();
	
	public PagerInfo findByConditions(Map map) throws Exception{
		PagerInfo pagerInfo = null;
		try 
		{ 
			//
			ReportLossOrFreezeQueryInfo qInfo =new ReportLossOrFreezeQueryInfo();
			String  checkdate = (String) map.get("sysdate".toLowerCase().toString());
			Timestamp checkDate1=DataFormat.getDateTime(checkdate);
			qInfo.setCheckDate(checkDate1);
			
			long userid=Long.parseLong(map.get("lUserID".toLowerCase()).toString());
			qInfo.setCheckUserId(userid);
			
			qInfo.setStatus(SETTConstant.TransactionStatus.CHECK);
			
			qInfo.setTransActionType(SETTConstant.TransactionType.REPORTLOSS);
			
			pagerInfo = biz.findByConditionsSuspendAndHanding(qInfo);
			
		} 
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		
		return pagerInfo;
	}
	
	public PagerInfo findByConditionsHanging(Map map) throws Exception{
		PagerInfo pagerInfo = null;
		try 
		{ 
			//
			ReportLossOrFreezeQueryInfo qInfo =new ReportLossOrFreezeQueryInfo();
			String  checkdate = (String) map.get("sysdate".toLowerCase().toString());
			Timestamp checkDate1=DataFormat.getDateTime(checkdate);
			qInfo.setCheckDate(checkDate1);
			
			long userid=Long.parseLong(map.get("lUserID".toLowerCase()).toString());
			qInfo.setCheckUserId(userid);
			
			
			qInfo.setStatus(SETTConstant.TransactionStatus.CHECK);
			qInfo.setTransActionType(SETTConstant.TransactionType.REPORTFIND);
			
			pagerInfo = biz.findByConditionsSuspendAndHanding(qInfo);
			
		} 
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		
		return pagerInfo;
	}
	public PagerInfo findByConditionsIssCertificates(Map map) throws Exception{
		PagerInfo pagerInfo = null;
		try 
		{ 
			//换发证书
			ReportLossOrFreezeQueryInfo qInfo =new ReportLossOrFreezeQueryInfo();
			String  checkdate = (String) map.get("sysdate".toLowerCase().toString());
			Timestamp checkDate1=DataFormat.getDateTime(checkdate);
			qInfo.setCheckDate(checkDate1);
			
			long userid=Long.parseLong(map.get("lUserID".toLowerCase()).toString());
			qInfo.setCheckUserId(userid);
			qInfo.setStatus(SETTConstant.TransactionStatus.CHECK);
			qInfo.setTransActionType(SETTConstant.TransactionType.CHANGECERTIFICATE);
			
			pagerInfo = biz.findByConditionsIssCertificates(qInfo);
			
		} 
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		
		return pagerInfo;
	}
	
	public PagerInfo findByConditionsFrozen(Map map) throws Exception{
		PagerInfo pagerInfo = null;
		try 
		{ 
			//账户冻结——连接查找（业务处理、业务复核）
			ReportLossOrFreezeQueryInfo qInfo =new ReportLossOrFreezeQueryInfo();
			
			long lStatusId = -1;
			
			 //long strTmp=Long.parseLong(map.get("hdnStatus".toLowerCase()).toString());
			String strTmp= map.get("hdnStatus".toLowerCase()).toString();
			
			if( strTmp != null && strTmp.length() > 0 )
			{
				lStatusId = Long.parseLong(strTmp.trim());
			}
			
			String  checkdate = (String) map.get("sysdate".toLowerCase().toString());
			Timestamp checkDate1=DataFormat.getDateTime(checkdate);
			long userid=Long.parseLong(map.get("lUserID".toLowerCase()).toString());
			
			if(lStatusId == SETTConstant.TransactionStatus.SAVE)
			{
				qInfo.setInputDate(checkDate1);
				qInfo.setInputUserId(userid);
			}
			else if(lStatusId == SETTConstant.TransactionStatus.CHECK)
			{
				qInfo.setCheckDate(checkDate1);
				qInfo.setCheckUserId(userid);
			}
			
			qInfo.setStatus(lStatusId);
			System.out.println("lStatusId的状态是============================================================="+lStatusId);
			qInfo.setTransActionType(SETTConstant.TransactionType.FREEZE);
			
			pagerInfo = biz.findReportFreezeByConditions(qInfo);
			
		} 
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		
		return pagerInfo;
	}
	
	
	public PagerInfo findByConditionsAccountThaw(Map map) throws Exception{
		PagerInfo pagerInfo = null;
		try 
		{ 
			//账户解冻——连接查找
			ReportLossOrFreezeQueryInfo qInfo =new ReportLossOrFreezeQueryInfo();
			long lStatusId = -1;
			String strTmp= map.get("hdnStatus".toLowerCase()).toString();
			
			if( strTmp != null && strTmp.length() > 0 )
			{
				lStatusId = Long.parseLong(strTmp.trim());
			}
			
			String  checkdate = (String) map.get("sysdate".toLowerCase().toString());
			Timestamp checkDate1=DataFormat.getDateTime(checkdate);
			long userid=Long.parseLong(map.get("lUserID".toLowerCase()).toString());
			
			if(lStatusId == SETTConstant.TransactionStatus.SAVE)
			{
				qInfo.setInputDate(checkDate1);
				qInfo.setInputUserId(userid);
			}
			else if(lStatusId == SETTConstant.TransactionStatus.CHECK)
			{
				qInfo.setCheckDate(checkDate1);
				qInfo.setCheckUserId(userid);
			}
			
			qInfo.setStatus(lStatusId);
			qInfo.setTransActionType(SETTConstant.TransactionType.DEFREEZE);
			
			
			pagerInfo = biz.findReportFreezeByConditions(qInfo);
			
		} 
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		
		return pagerInfo;
	}

}
	
