package com.iss.itreasury.ebank.obfinanceinstr.action;

import java.sql.Timestamp;
import java.util.Map;

import com.iss.itreasury.ebank.obfinanceinstr.bizlogic.QueryCheckInfoBiz;
import com.iss.itreasury.ebank.obfinanceinstr.dataentity.FinanceInfo;
import com.iss.itreasury.ebank.obfinanceinstr.dataentity.QueryCapForm;
import com.iss.itreasury.ebank.obfinanceinstr.dataentity.Query_FinanceInfo;
import com.iss.itreasury.ebank.util.OBConstant;
import com.iss.itreasury.util.DataFormat;
import com.iss.sysframe.pager.dataentity.PagerInfo;

public class QueryCheckInfoAction {
	
	QueryCheckInfoBiz queryCheckInfoBiz = new QueryCheckInfoBiz();
	
	public PagerInfo queryCheckInfo(Map map) throws Exception
	{
		PagerInfo pagerInfo = null;
		try
		{
			long lTransType = -1;
			if(map.get("ltranstype")!=null && map.get("ltranstype").toString().trim().length()>0){
				lTransType = Long.valueOf(map.get("ltranstype").toString().trim());
			}
			Query_FinanceInfo qInfo = new Query_FinanceInfo();
			qInfo.convertMapToDataEntity(map);//将Map转化为INFO
			qInfo.setNtranstype(lTransType);
			pagerInfo = queryCheckInfoBiz.queryCheckInfo(qInfo);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
	
	}
	
	public PagerInfo queryBatchCheckInfo(Map map) throws Exception
	{
		PagerInfo pagerInfo = null;
		try
		{
			FinanceInfo qInfo = new FinanceInfo();
			
			String strStartDate = null;
			String strEndDate = null;
			String strStatus = "";
			long userID = -1;
			long clientID = -1;
			
			if(map.get("strstartdate")!=null && map.get("strstartdate").toString().trim().length()>0){
				strStartDate = map.get("strstartdate").toString().trim();
				qInfo.setDtDepositBillCheckdate(DataFormat.getDateTime(strStartDate));
			}
			if(map.get("strenddate")!=null && map.get("strenddate").toString().trim().length()>0){
				strEndDate = map.get("strenddate").toString().trim();
				qInfo.setDtDepositBillInputdate(DataFormat.getDateTime(strEndDate));
			}
			if(map.get("sstatus")!=null && map.get("sstatus").toString().trim().length()>0){
				strStatus = map.get("sstatus").toString().trim();
				qInfo.setSStatus(strStatus);
			}
			if(map.get("userid")!=null && map.get("userid").toString().trim().length()>0){
				userID = Long.parseLong(map.get("userid").toString().trim());
				qInfo.setNUserID(userID);
			}
			if(map.get("clientid")!=null && map.get("clientid").toString().trim().length()>0){
				clientID = Long.parseLong(map.get("clientid").toString().trim());
				qInfo.setClientID(clientID);
			}
			
			//qInfo.convertMapToDataEntity(map);//将Map转化为INFO
			
			pagerInfo = queryCheckInfoBiz.queryBatchCheckInfo(qInfo);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
	
	}
	
	public PagerInfo queryBatchCheckInfoDetail(Map map) throws Exception
	{
		PagerInfo pagerInfo = null;
		try
		{
			FinanceInfo qInfo = new FinanceInfo();
			if(map.get("sbatchno")!=null && map.get("sbatchno").toString().trim().length()>0){
				String sbatchno = (String)map.get("sbatchno").toString().trim();
				qInfo.setSBatchNo(sbatchno);
			}
			//qInfo.convertMapToDataEntity(map);//将Map转化为INFO
			pagerInfo = queryCheckInfoBiz.queryBatchCheckInfoDetail(qInfo);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
	
	}
	
	public PagerInfo queryUncheckInfo(Map map) throws Exception
	{
		PagerInfo pagerInfo = null;
		try
		{
			QueryCapForm qInfo = new QueryCapForm();

			if(map.get("selecttype")!=null && map.get("selecttype").toString().trim().length()>0){
				long selecttype = Long.parseLong(map.get("selecttype").toString().trim());
				qInfo.setTransType(selecttype);
			}
			if(map.get("txtconfirma")!=null && map.get("txtconfirma").toString().trim().length()>0){
				String txtconfirma = map.get("txtconfirma").toString().trim();
				qInfo.setStartSubmit(txtconfirma);
			}
			if(map.get("txtconfirmb")!=null && map.get("txtconfirmb").toString().trim().length()>0){
				String txtconfirmb = map.get("txtconfirmb").toString().trim();
				qInfo.setEndSubmit(txtconfirmb);
			}
			if(map.get("selectstatus")!=null && map.get("selectstatus").toString().trim().length()>0){
				long selectstatus = Long.parseLong(map.get("selectstatus").toString().trim());
				qInfo.setStatus(selectstatus);
			}
			if(map.get("txtminamount")!=null && map.get("txtminamount").toString().trim().length()>0){
				String txtminamount = map.get("txtminamount").toString().trim();
				qInfo.setMinAmount ( Double.parseDouble(DataFormat.reverseFormatAmount(txtminamount)) );// 交易金额-最小值
			}
			if(map.get("txtmaxamount")!=null && map.get("txtmaxamount").toString().trim().length()>0){
				String txtmaxamount = map.get("txtmaxamount").toString().trim();
				qInfo.setMaxAmount ( Double.parseDouble(DataFormat.reverseFormatAmount(txtmaxamount)) );// 交易金额-最大值
			}
			if(map.get("txtexecutea")!=null && map.get("txtexecutea").toString().trim().length()>0){
				String txtexecutea = map.get("txtexecutea").toString().trim();
				qInfo.setStartExe(txtexecutea);
			}
			if(map.get("txtexecuteb")!=null && map.get("txtexecuteb").toString().trim().length()>0){
				String txtexecuteb = map.get("txtexecuteb").toString().trim();
				qInfo.setEndExe(txtexecuteb);
			}
			if(map.get("sign")!=null && map.get("sign").toString().trim().length()>0){
				String sign = map.get("sign").toString().trim();
				qInfo.setSign(sign);
			}
			if(map.get("lclientid")!=null && map.get("lclientid").toString().trim().length()>0){
				long lclientid = Long.parseLong(map.get("lclientid").toString().trim());
				qInfo.setClientID(lclientid);
			}
			if(map.get("lcurrencyid")!=null && map.get("lcurrencyid").toString().trim().length()>0){
				long lcurrencyid = Long.parseLong(map.get("lcurrencyid").toString().trim());
				qInfo.setCurrencyID(lcurrencyid);
			}
			if(map.get("luserid")!=null && map.get("luserid").toString().trim().length()>0){
				long luserid = Long.parseLong(map.get("luserid").toString().trim());
				qInfo.setUserID(luserid);
			}
			
			qInfo.setOperationTypeID ( OBConstant.QueryOperationType.CHECK );
			
			//qInfo.convertMapToDataEntity(map);//将Map转化为INFO
			pagerInfo = queryCheckInfoBiz.queryUncheckInfo(qInfo);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
	
	}

	public PagerInfo queryUncheckDetailInfo(Map map) throws Exception{
		PagerInfo pagerInfo = null;
		try
		{
			FinanceInfo qInfo = new FinanceInfo();
			if(map.get("lid")!=null && map.get("lid").toString().trim().length()>0){
				long lid = Long.parseLong(map.get("lid").toString().trim());
				qInfo.setID(lid);
			}
			if(map.get("lcurrencyid")!=null && map.get("lcurrencyid").toString().trim().length()>0){
				long lcurrencyid = Long.parseLong(map.get("lcurrencyid").toString().trim());
				qInfo.setCurrencyID(lcurrencyid);
			}
			if(map.get("luserid")!=null && map.get("luserid").toString().trim().length()>0){
				long luserid = Long.parseLong(map.get("luserid").toString().trim());
				qInfo.setUserID(luserid);
			}
			if(!map.get("transtype").equals("null") && map.get("transtype").toString().trim().length()>0){
				long transtype = Long.parseLong(map.get("transtype").toString().trim());
				qInfo.setTransType(transtype);
			}
			
			pagerInfo = queryCheckInfoBiz.queryUncheckDetailInfo(qInfo);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
	
	}
	
	public PagerInfo querySignInfo(Map map) throws Exception{

		PagerInfo pagerInfo = null;
		try
		{
			QueryCapForm qInfo = new QueryCapForm();

			if(map.get("selecttype")!=null && map.get("selecttype").toString().trim().length()>0){
				long selecttype = Long.parseLong(map.get("selecttype").toString().trim());
				qInfo.setTransType(selecttype);
			}
			if(map.get("txtconfirma")!=null && map.get("txtconfirma").toString().trim().length()>0){
				String txtconfirma = map.get("txtconfirma").toString().trim();
				qInfo.setStartSubmit(txtconfirma);
			}
			if(map.get("txtconfirmb")!=null && map.get("txtconfirmb").toString().trim().length()>0){
				String txtconfirmb = map.get("txtconfirmb").toString().trim();
				qInfo.setEndSubmit(txtconfirmb);
			}
			if(map.get("selectstatus")!=null && !("null").equals(map.get("selectstatus")) && map.get("selectstatus").toString().trim().length()>0){
				long selectstatus = Long.parseLong(map.get("selectstatus").toString().trim());
				qInfo.setStatus(selectstatus);
			}
			if(map.get("txtminamount")!=null && map.get("txtminamount").toString().trim().length()>0){
				String txtminamount = map.get("txtminamount").toString().trim();
				qInfo.setMinAmount ( Double.parseDouble(DataFormat.reverseFormatAmount(txtminamount)) );// 交易金额-最小值
			}
			if(map.get("txtmaxamount")!=null && map.get("txtmaxamount").toString().trim().length()>0){
				String txtmaxamount = map.get("txtmaxamount").toString().trim();
				qInfo.setMaxAmount ( Double.parseDouble(DataFormat.reverseFormatAmount(txtmaxamount)) );// 交易金额-最大值
			}
			if(map.get("txtexecutea")!=null && map.get("txtexecutea").toString().trim().length()>0){
				String txtexecutea = map.get("txtexecutea").toString().trim();
				qInfo.setStartExe(txtexecutea);
			}
			if(map.get("txtexecuteb")!=null && map.get("txtexecuteb").toString().trim().length()>0){
				String txtexecuteb = map.get("txtexecuteb").toString().trim();
				qInfo.setEndExe(txtexecuteb);
			}
			if(map.get("sign")!=null && map.get("sign").toString().trim().length()>0){
				String sign = map.get("sign").toString().trim();
				qInfo.setSign(sign);
			}
			if(map.get("lclientid")!=null && map.get("lclientid").toString().trim().length()>0){
				long lclientid = Long.parseLong(map.get("lclientid").toString().trim());
				qInfo.setClientID(lclientid);
			}
			if(map.get("lcurrencyid")!=null && map.get("lcurrencyid").toString().trim().length()>0){
				long lcurrencyid = Long.parseLong(map.get("lcurrencyid").toString().trim());
				qInfo.setCurrencyID(lcurrencyid);
			}
			if(map.get("luserid")!=null && map.get("luserid").toString().trim().length()>0){
				long luserid = Long.parseLong(map.get("luserid").toString().trim());
				qInfo.setUserID(luserid);
			}
			if(map.get("selectstatus")!=null && map.get("selectstatus").toString().trim().length()>0){
				long status = Long.parseLong(map.get("selectstatus").toString().trim());
				qInfo.setStatus(status);
			}
			
			qInfo.setOperationTypeID (OBConstant.QueryOperationType.SIGN);
			
			pagerInfo = queryCheckInfoBiz.querySignInfo(qInfo);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
	
	}
	
	public PagerInfo queryBatchSignInfo(Map map) throws Exception
	{
		PagerInfo pagerInfo = null;
		try
		{
			FinanceInfo qInfo = new FinanceInfo();
			
			String strStartDate = null;
			String strEndDate = null;
			String strStatus = "";
			long userID = -1;
			long clientID = -1;
			long currencyID = -1;
			
			if(map.get("strstartdate")!=null && map.get("strstartdate").toString().trim().length()>0){
				strStartDate = map.get("strstartdate").toString().trim();
				qInfo.setDtDepositBillCheckdate(DataFormat.getDateTime(strStartDate));
			}
			if(map.get("strenddate")!=null && map.get("strenddate").toString().trim().length()>0){
				strEndDate = map.get("strenddate").toString().trim();
				qInfo.setDtDepositBillInputdate(DataFormat.getDateTime(strEndDate));
			}
			if(map.get("selectstatus")!=null && map.get("selectstatus").toString().trim().length()>0){
				strStatus = map.get("selectstatus").toString().trim();
				qInfo.setSStatus(strStatus);
				qInfo.setStatus(Long.parseLong(strStatus));
			}
			if(map.get("userid")!=null && map.get("userid").toString().trim().length()>0){
				userID = Long.parseLong(map.get("userid").toString().trim());
				qInfo.setUserID(userID);
			}
			if(map.get("clientid")!=null && map.get("clientid").toString().trim().length()>0){
				clientID = Long.parseLong(map.get("clientid").toString().trim());
				qInfo.setClientID(clientID);
			}
			if(map.get("currencyid")!=null && map.get("currencyid").toString().trim().length()>0){
				currencyID = Long.parseLong(map.get("currencyid").toString().trim());
				qInfo.setCurrencyID(currencyID);
			}
			
			pagerInfo = queryCheckInfoBiz.queryBatchSignInfo(qInfo);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
	
	}
	
	
	public PagerInfo queryBatchSignInfoDetail(Map map) throws Exception
	{
		PagerInfo pagerInfo = null;
		try
		{
			FinanceInfo qInfo = new FinanceInfo();
			if(map.get("sbatchno")!=null && map.get("sbatchno").toString().trim().length()>0){
				String sbatchno = (String)map.get("sbatchno").toString().trim();
				qInfo.setSBatchNo(sbatchno);
			}
			if(map.get("clientid")!=null && map.get("clientid").toString().trim().length()>0){
				long clientID = Long.valueOf((String)map.get("clientid").toString().trim());
				qInfo.setClientID(clientID);
			}
			if(map.get("userid")!=null && map.get("userid").toString().trim().length()>0){
				long UserID = Long.valueOf((String)map.get("userid").toString().trim());
				qInfo.setUserID(UserID);
			}
			if(map.get("currencyid")!=null && map.get("currencyid").toString().trim().length()>0){
				long CurrencyID = Long.valueOf((String)map.get("currencyid").toString().trim());
				qInfo.setCurrencyID(CurrencyID);
			}
			if(map.get("status")!=null && map.get("status").toString().trim().length()>0){
				long status = Long.valueOf((String)map.get("status").toString().trim());
				qInfo.setStatus(status);
			}
			
			//qInfo.convertMapToDataEntity(map);//将Map转化为INFO
			pagerInfo = queryCheckInfoBiz.queryBatchSignInfoDetail(qInfo);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
	
	}
	
	/**
	 * 申请指令查询
	 */
	public PagerInfo queryCapFormInfo(Map map) throws Exception
	{
		PagerInfo pagerInfo = null;
		try
		{
			long lTransType = -1;       // 网上银行交易类型
	        long lContractID = -1;      // 合同ID
	        String strContractNo = "";  // 合同号
			long lChildClientID = -1;//下属单位
			String sChildClientNo = "";//下属单位
	        long lDepositID = -1;       // 存款单据ID
	        String strDepositNo = "";   //存款单据号
	        String strStartSubmit = ""; // 提交日期-从
	        String strEndSubmit = "";   // 提交日期-到
	        long lStatus = -1;          // 交易指令状态
	        double dMinAmount = 0.00;   // 交易金额-最小值
	        double dMaxAmount = 0.00;   // 交易金额-最大值
	        String strStartExe = "";    // 执行日期-从
	        String strEndExe = "";      // 执行日期-到
	        String sNextSuccessPage = "";
	        long nEbankStatus = -1;  
		    long lClientID = -1;
		    long lCurrencyID = -1;
		    long lUserID = -1;
	        String currencySymbol = "";
	        if(map.get("currencysymbol")!=null && map.get("currencysymbol").toString().trim().length()>0){
	        	currencySymbol = (String)map.get("currencysymbol").toString().trim();
			}
			if(map.get("nebankstatus")!=null && map.get("nebankstatus").toString().trim().length()>0){
				nEbankStatus = Long.valueOf((String)map.get("nebankstatus").toString().trim());
			}
			if(map.get("ltranstype")!=null && map.get("ltranstype").toString().trim().length()>0){
				lTransType = Long.valueOf((String)map.get("ltranstype").toString().trim());
				if (lTransType == 0) {
	                lTransType = -1;
	            }
			}
			if(map.get("lstatus")!=null && map.get("lstatus").toString().trim().length()>0){
				lStatus = Long.valueOf((String)map.get("lstatus").toString().trim());
			}
			if(map.get("sstartsubmit")!=null && map.get("sstartsubmit").toString().trim().length()>0){
				strStartSubmit = (String)map.get("sstartsubmit").toString().trim();
			}
			if(map.get("sendsubmit")!=null && map.get("sendsubmit").toString().trim().length()>0){
				strEndSubmit = (String)map.get("sendsubmit").toString().trim();
			}
			if(map.get("sstartexe")!=null && map.get("sstartexe").toString().trim().length()>0){
				strStartExe = (String)map.get("sstartexe").toString().trim();
			}
			if(map.get("sendexe")!=null && map.get("sendexe").toString().trim().length()>0){
				strEndExe = (String)map.get("sendexe").toString().trim();
			}
			if(map.get("dminamount")!=null && map.get("dminamount").toString().trim().length()>0){
				dMinAmount = Double.parseDouble(DataFormat.reverseFormatAmount((String)map.get("dminamount").toString().trim()));
			}
			if(map.get("dmaxamount")!=null && map.get("dmaxamount").toString().trim().length()>0){
				dMaxAmount = Double.parseDouble(DataFormat.reverseFormatAmount((String)map.get("dmaxamount").toString().trim()));
			}
			if(map.get("lclientid")!=null && map.get("lclientid").toString().trim().length()>0){
				lClientID = Long.valueOf((String)map.get("lclientid").toString().trim());
			}
			if(map.get("lcurrencyid")!=null && map.get("lcurrencyid").toString().trim().length()>0){
				lCurrencyID = Long.valueOf((String)map.get("lcurrencyid").toString().trim());
			}
			if(map.get("luserid")!=null && map.get("luserid").toString().trim().length()>0){
				lUserID = Long.valueOf((String)map.get("luserid").toString().trim());
			}
			switch ((int)lTransType) {
	            case (int)OBConstant.QueryInstrType.FIXEDTOCURRENTTRANSFER:
	            	if(map.get("lfixeddepositid")!=null && map.get("lfixeddepositid").toString().trim().length()>0){
	            		lDepositID = Long.valueOf((String)map.get("lfixeddepositid").toString().trim());
	    			}
		            if(map.get("lfixeddepositidctrl")!=null && map.get("lfixeddepositidctrl").toString().trim().length()>0){
		            	strDepositNo = (String)map.get("lfixeddepositidctrl").toString().trim();
	    			}
	                break;
	            case (int)OBConstant.QueryInstrType.NOTIFYDEPOSITDRAW:
	            	if(map.get("lnotifydepositid")!=null && map.get("lnotifydepositid").toString().trim().length()>0){
	            		lDepositID = Long.valueOf((String)map.get("lnotifydepositid").toString().trim());
	    			}
		            if(map.get("lnotifydepositidctrl")!=null && map.get("lnotifydepositidctrl").toString().trim().length()>0){
		            	strDepositNo = (String)map.get("lnotifydepositidctrl").toString().trim();
	    			}
	                break;
	            case (int)OBConstant.QueryInstrType.TRUSTLOANRECEIVE:
	            	if(map.get("lloancontractid")!=null && map.get("lloancontractid").toString().trim().length()>0){
	            		lContractID = Long.valueOf((String)map.get("lloancontractid").toString().trim());
	    			}
		            if(map.get("lloancontractidctrl")!=null && map.get("lloancontractidctrl").toString().trim().length()>0){
		            	strContractNo = (String)map.get("lloancontractidctrl").toString().trim();
	    			}
	                break;
	            case (int)OBConstant.QueryInstrType.CONSIGNLOANRECEIVE:
	            	if(map.get("lsettcontractid")!=null && map.get("lsettcontractid").toString().trim().length()>0){
	            		lContractID = Long.valueOf((String)map.get("lsettcontractid").toString().trim());
	    			}
		            if(map.get("lsettcontractidctrl")!=null && map.get("lsettcontractidctrl").toString().trim().length()>0){
		            	strContractNo = (String)map.get("lsettcontractidctrl").toString().trim();
	    			}
	                break;
	            case (int)OBConstant.QueryInstrType.INTERESTFEEPAYMENT:
	            	if(map.get("lratecontractid")!=null && map.get("lratecontractid").toString().trim().length()>0){
	            		lContractID = Long.valueOf((String)map.get("lratecontractid").toString().trim());
	    			}
		            if(map.get("lratecontractidctrl")!=null && map.get("lratecontractidctrl").toString().trim().length()>0){
		            	strContractNo = (String)map.get("lratecontractidctrl").toString().trim();
	    			}
	                break;
				case (int)OBConstant.QueryInstrType.CHILDCAPTRANSFER:
					if(map.get("lchildclientid")!=null && map.get("lchildclientid").toString().trim().length()>0){
						lChildClientID = Long.valueOf((String)map.get("lchildclientid").toString().trim());
	    			}
					if (lChildClientID<=0)
					{
						lChildClientID = -2;
					}
					if(map.get("txtclientcode")!=null && map.get("txtclientcode").toString().trim().length()>0){
						sChildClientNo = (String)map.get("txtclientcode").toString().trim();
	    			}
	                break;
	            default :
	                break;
	        }
			
			/* 初始化查询信息类 */		
	        QueryCapForm queryCapForm = new QueryCapForm();
			if (lTransType==OBConstant.QueryInstrType.CHILDCAPTRANSFER)
			{
				queryCapForm.setChildClientID(lChildClientID);
				queryCapForm.setChildClientNo(sChildClientNo);
				//request.setAttribute("child", "1");
			}
	        queryCapForm.setTransType(lTransType);          // 网上银行交易类型
	        queryCapForm.setStatus(lStatus);                // 交易指令状态
	        queryCapForm.setStartSubmit(strStartSubmit);    // 提交日期-从
	        queryCapForm.setEndSubmit(strEndSubmit);        // 提交日期-到
	        queryCapForm.setStartExe(strStartExe);          // 执行日期-从
	        queryCapForm.setEndExe(strEndExe);              // 执行日期-到
	        queryCapForm.setMinAmount(dMinAmount);          // 金额最小值
	        queryCapForm.setMaxAmount(dMaxAmount);          // 金额最大值
	        queryCapForm.setContractID(lContractID);        // 合同ID
	        queryCapForm.setDepositID(lDepositID);          // 存款单据ID
	        queryCapForm.setContractNo(strContractNo);      // 合同号
	        queryCapForm.setDepositNo(strDepositNo);        // 存款单据号
	        queryCapForm.setNEbankStatus(nEbankStatus);     // 银行指令状态
	        /* 从session中获取相应数据 */
	        queryCapForm.setClientID(lClientID);
	        queryCapForm.setCurrencyID(lCurrencyID);
	        queryCapForm.setUserID(lUserID);
	        /* 根据页面菜单确定查询类型 */
	        queryCapForm.setOperationTypeID(OBConstant.QueryOperationType.QUERY);
			//Log.print("===========OperationTypeID="+queryCapForm.getOperationTypeID());
	        queryCapForm.setOrderBy(true);
			
			pagerInfo = queryCheckInfoBiz.queryCapFormInfo(queryCapForm,currencySymbol);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
	
	}
}
