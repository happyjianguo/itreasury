package com.iss.itreasury.settlement.interest.action;

import java.util.Map;
import java.util.Vector;

import com.iss.itreasury.settlement.enddayprocess.process.EndDayProcess;
import com.iss.itreasury.settlement.interest.bizlogic.InterestQueryBiz;
import com.iss.itreasury.settlement.interest.bizlogic.InterestSettlement;
import com.iss.itreasury.settlement.interest.dataentity.InterestEstimateQueryInfo;
import com.iss.itreasury.settlement.interest.dataentity.InterestQueryInfo;
import com.iss.itreasury.settlement.interest.dataentity.InterestQueryResultInfo;
import com.iss.itreasury.settlement.interest.dataentity.InterestSettmentInfo;
import com.iss.itreasury.settlement.query.queryobj.QInterestEstimate;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.SessionMng;
import com.iss.sysframe.pager.dataentity.PagerInfo;

public class InterestQueryAction{
	InterestQueryBiz interestQueryBiz = new InterestQueryBiz();
	
	PagerInfo pagerInfo = null;
	
	/**
	 * 利息费用结算处理查询方法
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public PagerInfo queryInterest(Map map) throws Exception{
		try
		{
			InterestQueryInfo qInfo = new InterestQueryInfo();
			qInfo.convertMapToDataEntity(map);//将Map转化为INFO
			pagerInfo = interestQueryBiz.queryInterest(qInfo);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
	}

	/**
	 * 打印到期贷款通知书、逾期贷款催收通知书
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public PagerInfo loanMatureNotice(Map map) throws Exception{
		try
		{ 	
			InterestEstimateQueryInfo qInfo = new InterestEstimateQueryInfo();
			qInfo.setNoticetype(SETTConstant.LoanNoticeType.LoanMatureNotice);
			qInfo.convertMapToDataEntity(map);//将Map转化为INFO
			String strAction1 = map.get("straction1")==null?"": map.get("straction1").toString();
			String ids =map.get("ids")==null?"":map.get("ids").toString();
			String cp = map.get("cp")==null?"": map.get("cp").toString();
			String rp = map.get("rp")==null?"": map.get("rp").toString();
			pagerInfo = interestQueryBiz.loanMatureNotice(qInfo ,strAction1,ids,cp,rp);

		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
	}
	
	public PagerInfo calculateInterest(Map map) throws Exception{
		try
		{
			String strAction = (String)map.get("straction");
			InterestQueryInfo qInfo = new InterestQueryInfo();
			qInfo.convertMapToDataEntity(map);//将Map转化为INFO
			String[] lSubLoanTypeValueLeft = new String[1];
			String strSubLoanTypeValueLeft = (String)map.get("strsubloantypevalueleft");
			String[] lSubLoanTypeValueRight = new String[1];
			String strSubLoanTypeValueRight = (String)map.get("strsubloantypevalueright");
			if(strSubLoanTypeValueLeft!=null && !"".equals(strSubLoanTypeValueLeft)){
				lSubLoanTypeValueLeft[0] = strSubLoanTypeValueLeft;
				qInfo.setLSubLoanTypeValueLeft(lSubLoanTypeValueLeft);
			}
			if(strSubLoanTypeValueRight!=null && !"".equals(strSubLoanTypeValueRight)){
				lSubLoanTypeValueRight[0] = strSubLoanTypeValueRight;
				qInfo.setLSubLoanTypeValueRight(lSubLoanTypeValueRight);
			}
			//查询
			if("calculate".equals(strAction)){
				pagerInfo = interestQueryBiz.calculateInterest(qInfo);
			}
			//计提
			else if("calInterest".equals(strAction))
			{
				String[] ckArr = new String[1] ;
				
				//选中的checkbox
				if(map.get("ck") instanceof String){
					ckArr[0] = (String)map.get("ck");
				}else{
				    ckArr = (String[])map.get("ck");
				}
				
				Vector doVec = new Vector();
				for(String ck : ckArr){
					InterestQueryResultInfo info = (InterestQueryResultInfo) SessionMng.sessionCache.get(ck);
					SessionMng.sessionCache.remove(ck);
					doVec.add(info);
				}
				
				
				long isSave =-1;
				long isKeepAccount = -1;
				boolean isClearPartInterest = false;
				long OfficeID = -1;
				long CurrencyID = -1;
				long UserID = -1;
				
				String strTemp = null;			
				
				strTemp = (String)map.get("officeid");
				if (strTemp != null && strTemp.trim().length() > 0)
				{
					OfficeID = Long.valueOf(strTemp);
				}
				strTemp = (String)map.get("currencyid");
				if (strTemp != null && strTemp.trim().length() > 0)
				{
					CurrencyID = Long.valueOf(strTemp);
				}
				strTemp = (String)map.get("userid");
				if (strTemp != null && strTemp.trim().length() > 0)
				{
					UserID = Long.valueOf(strTemp);
				}
				strTemp = (String)map.get("issave");
				if (strTemp != null && strTemp.trim().length() > 0)
				{
					isSave = SETTConstant.BooleanValue.ISTRUE;
				}
				strTemp = (String)map.get("iskeepaccount");
				if (strTemp != null && strTemp.trim().length() > 0)
				{
					isKeepAccount = SETTConstant.BooleanValue.ISTRUE;
				}
				strTemp = (String)map.get("isclearpartinterest");
				if (strTemp != null && strTemp.trim().length() > 0)
				{
					isClearPartInterest = true;
				}
				String strContinueCal = (String)map.get("strcontinuecal");
				
				InterestSettmentInfo settmentInfo = new InterestSettmentInfo();
				InterestSettlement interestSettlement = new InterestSettlement();
				settmentInfo.setIsSave(isSave);
				settmentInfo.setIsKeepAccount(isKeepAccount);
				settmentInfo.setOfficeID(OfficeID);
				settmentInfo.setCurrencyID(CurrencyID);
				settmentInfo.setInputUserID(UserID);
				settmentInfo.setExecuteDate(Env.getSystemDate(settmentInfo.getOfficeID(), settmentInfo.getCurrencyID()));
				settmentInfo.setClearPartInterest(isClearPartInterest);
				
				//计提
				settmentInfo.setOperationType(SETTConstant.InterestOperateType.PREDRAWINTEREST);
				
				//前端确认提交的贷款不能计提
				if(strContinueCal != null && "true".equals(strContinueCal))
				{
					//业务处理
					interestSettlement.drawingInterest(null,doVec,settmentInfo);	    
				    //session.setAttribute("resultList", result);		
				    //strActionResult = Constant.ActionResult.SUCCESS;
					//sessionMng.getActionMessages().addMessage("计提成功");
				}
				else
				{
					interestSettlement.drawingInterest(null,doVec,settmentInfo);	    
				    //session.setAttribute("resultList", result);		
				    //strActionResult = Constant.ActionResult.SUCCESS;
					//sessionMng.getActionMessages().addMessage("计提成功");
				}				
				
				//业务处理
	//			map.put("settmentInfo", settmentInfo);
				
				pagerInfo = interestQueryBiz.calculateInterest(qInfo);
				
				
			}else if("cleanupinterest".equals(strAction)){

				String[] ckArr = new String[1] ;
				
				//选中的checkbox
				if(map.get("ck") instanceof String){
					ckArr[0] = (String)map.get("ck");
				}else{
				    ckArr = (String[])map.get("ck");
				}
				
				Vector doVec = new Vector();
				for(String ck : ckArr){
					InterestQueryResultInfo info = (InterestQueryResultInfo) SessionMng.sessionCache.get(ck);
					SessionMng.sessionCache.remove(ck);
					doVec.add(info);
				}
				
				//InterestQueryInfo qInfo = new InterestQueryInfo();
				//qInfo.convertMapToDataEntity(map);//将Map转化为INFO
				
				
				long isSave =-1;
				long isKeepAccount = -1;
				boolean isClearPartInterest = false;
				long OfficeID = -1;
				long CurrencyID = -1;
				long UserID = -1;
				
				String strTemp = null;			
				
				strTemp = (String)map.get("officeid");
				if (strTemp != null && strTemp.trim().length() > 0)
				{
					OfficeID = Long.valueOf(strTemp);
				}
				strTemp = (String)map.get("currencyid");
				if (strTemp != null && strTemp.trim().length() > 0)
				{
					CurrencyID = Long.valueOf(strTemp);
				}
				strTemp = (String)map.get("userid");
				if (strTemp != null && strTemp.trim().length() > 0)
				{
					UserID = Long.valueOf(strTemp);
				}
				strTemp = (String)map.get("issave");
				if (strTemp != null && strTemp.trim().length() > 0)
				{
					isSave = SETTConstant.BooleanValue.ISTRUE;
				}
				strTemp = (String)map.get("iskeepaccount");
				if (strTemp != null && strTemp.trim().length() > 0)
				{
					isKeepAccount = SETTConstant.BooleanValue.ISTRUE;
				}
				strTemp = (String)map.get("isclearpartinterest");
				if (strTemp != null && strTemp.trim().length() > 0)
				{
					isClearPartInterest = true;
				}
				String strContinueCal = (String)map.get("strcontinuecal");
				
				InterestSettmentInfo settmentInfo = new InterestSettmentInfo();
				InterestSettlement interestSettlement = new InterestSettlement();
				settmentInfo.setIsSave(isSave);
				settmentInfo.setIsKeepAccount(isKeepAccount);
				settmentInfo.setOfficeID(OfficeID);
				settmentInfo.setCurrencyID(CurrencyID);
				settmentInfo.setInputUserID(UserID);
				settmentInfo.setExecuteDate(Env.getSystemDate(settmentInfo.getOfficeID(), settmentInfo.getCurrencyID()));
				settmentInfo.setClearPartInterest(isClearPartInterest);
				
				///冲销计提
				settmentInfo.setOperationType(SETTConstant.InterestOperateType.CLEANPREDRAWINTEREST);
				 
				//业务处理
				interestSettlement.clearDrawingInterest(null,doVec,settmentInfo);
			    //流程控制	
				
				//业务处理
				
				pagerInfo = interestQueryBiz.calculateInterest(qInfo);
				
				
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		return pagerInfo;
	}
	
	public PagerInfo balanceInterest(Map map) throws Exception{
		try
		{
			//选中的checkbox
			String ckStr = (String)map.get("ckstr");
			String[] ckArr = ckStr.split(",");
			
			Vector banlanceVec = new Vector();
			for(String ck : ckArr){
				InterestQueryResultInfo info = (InterestQueryResultInfo)SessionMng.sessionCache.get(ck);
				SessionMng.sessionCache.remove(ck);
				banlanceVec.add(info);
			}
			
			
			InterestQueryInfo qInfo = new InterestQueryInfo();
			//qInfo.convertMapToDataEntity(map);//将Map转化为INFO
			
			
			long isSave =-1;
			long isKeepAccount = -1;
			boolean isClearPartInterest = false;
			long OfficeID = -1;
			long CurrencyID = -1;
			long UserID = -1;
			
			String strTemp = null;			
			
			strTemp = (String)map.get("officeid");
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				OfficeID = Long.valueOf(strTemp);
			}
			strTemp = (String)map.get("currencyid");
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				CurrencyID = Long.valueOf(strTemp);
			}
			strTemp = (String)map.get("userid");
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				UserID = Long.valueOf(strTemp);
			}
			strTemp = (String)map.get("issave");
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				isSave = SETTConstant.BooleanValue.ISTRUE;
			}
			strTemp = (String)map.get("iskeepaccount");
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				isKeepAccount = SETTConstant.BooleanValue.ISTRUE;
			}
			strTemp = (String)map.get("isclearpartinterest");
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				isClearPartInterest = true;
			}
			

			InterestSettmentInfo settmentInfo = new InterestSettmentInfo();
			InterestSettlement interestSettlement = new InterestSettlement();
			settmentInfo.setIsSave(isSave);
			settmentInfo.setIsKeepAccount(isKeepAccount);
			settmentInfo.setOfficeID(OfficeID);
			settmentInfo.setCurrencyID(CurrencyID);
			settmentInfo.setInputUserID(UserID);
			settmentInfo.setExecuteDate(Env.getSystemDate(settmentInfo.getOfficeID(), settmentInfo.getCurrencyID()));
			settmentInfo.setClearPartInterest(isClearPartInterest);
			
			//结算
			settmentInfo.setOperationType(SETTConstant.InterestOperateType.INTERESTSETTLEMENT);
			
			Vector resultVec = null;//(Vector).getAttribute("resultList");
			 
			 
			String clearInterestString = (String)map.get("clearintereststring");
			if(clearInterestString!=null && !("").equals(clearInterestString)){
				//页面录入结息时间
				settmentInfo.setInputClearInterest(DataFormat.getDateTime(clearInterestString));
			}else{
				//页面录入结息时间
				settmentInfo.setInputClearInterest(DataFormat.getDateTime(Env.getSystemDateString(OfficeID, CurrencyID)));
			}
			
			 
			//业务处理
			Vector result = interestSettlement.balanceInterest(null,banlanceVec,settmentInfo);
			map.put("settmentInfo", settmentInfo);
			map.put("result", result);
			
			pagerInfo = interestQueryBiz.balanceInterest(qInfo, map);
			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
	}
	
	
	public PagerInfo queryInterestEstimateClientInfo(Map map) throws NumberFormatException, Exception{
		InterestEstimateQueryInfo qInfo = new InterestEstimateQueryInfo();
		QInterestEstimate qobj = new QInterestEstimate();
		qInfo.convertMapToDataEntity(map);//将Map转化为INFO
		if (EndDayProcess.getSystemStatusID(Long.valueOf(map.get("officeid").toString()),
				Long.valueOf(map.get("currencyid").toString())) != Constant.SystemStatus.OPEN) {
			qInfo.setClearInterestDate(qobj.getNextNDay(qInfo
					.getClearInterestDate(), -1));
		}
		return interestQueryBiz.queryInterestEstimateClientInfo(qInfo);
	}
	
	public PagerInfo queryInterestEstimateAccountInfo(Map map) throws NumberFormatException, Exception{
		InterestEstimateQueryInfo qInfo = new InterestEstimateQueryInfo();
		QInterestEstimate qobj = new QInterestEstimate();
		qInfo.convertMapToDataEntity(map);//将Map转化为INFO
		return interestQueryBiz.queryInterestEstimateAccountInfo(qInfo);
	}
	
}
