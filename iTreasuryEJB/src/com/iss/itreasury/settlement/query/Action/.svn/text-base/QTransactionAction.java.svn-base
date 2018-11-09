package com.iss.itreasury.settlement.query.Action;

import java.sql.Timestamp;
import java.util.Map;

import com.iss.itreasury.settlement.query.bizlogic.QTransactionBiz;
import com.iss.itreasury.settlement.query.paraminfo.QueryTransactionConditionInfo;
import com.iss.itreasury.util.DataFormat;
import com.iss.sysframe.pager.dataentity.PagerInfo;

/**
 * 账户查询操作类
 * 
 * @author xiang
 * 
 */
public class QTransactionAction {

	QTransactionBiz biz = new QTransactionBiz();

	public PagerInfo queryTransaction(Map map) throws Exception {
		PagerInfo pagerInfo = null;

		// 定义变量
		long lOfficeID = Long.valueOf((String) map.get("lofficeid"))
				.longValue();// 办事处
		long lCurrencyID = Long.valueOf((String) map.get("lcurrencyid"))
				.longValue();// 币种

		long lTransactionTypeID = -1;// 业务类型
		long lAccountTransTypeID = -1;// 账户交易类型

		long lPayClientIDStart = -1;// 付款方客户ID（由）
		String strPayClientNoStart = "";// 付款方客户编号（由）
		long lPayClientIDEnd = -1;// 付款方客户ID（至）
		String strPayClientNoEnd = "";// 付款方客户编号（至）
		long lPayAccountIDStart = -1;// 付款方账户（由）
		String strPayAccountNoStart = "";// 付款方账户号（由）
		long lPayAccountIDEnd = -1;// 付款方账户（至）
		String strPayAccountNoEnd = "";// 付款方账户号（至）
		double dPayAmountStart = 0.0;// 付款方金额（由）
		double dPayAmountEnd = 0.0;// 付款方金额（至）

		long lReceiveClientIDStart = -1;// 收款方客户ID（由）
		String strReceiveClientNoStart = "";// 收款方客户编号（由）
		long lReceiveClientIDEnd = -1;// 收款方客户ID（至）
		String strReceiveClientNoEnd = "";// 收款方客户编号（至）
		long lReceiveAccountIDStart = -1;// 收款方账户（由）
		String strReceiveAccountNoStart = "";// 收款方账户号（由）
		long lReceiveAccountIDEnd = -1;// 收款方账户（至）
		String strReceiveAccountNoEnd = "";// 收款方账户号（至）
		double dReceiveAmountStart = 0.0;// 收款方金额（由）
		double dReceiveAmountEnd = 0.0;// 收款方金额（至）

		long lBankID = -1;// 开户行
		String strTransNoStart = "";// 交易号（由）
		String strTransNoEnd = "";// 交易号（至）
		String strBankChequeNO = "";// 支票号
		String strDeclarationNO = "";// 报单号
		Timestamp tsInterestStartStart = null;// 起息日（由）
		Timestamp tsInterestStartEnd = null;// 起息日（至）
		Timestamp tsExecuteStart = null;// 执行日（由）
		Timestamp tsExecuteEnd = null;// 执行日（至）
		long lStatusID = -1;// 交易纪录状态
		long lInputuserID = -1;// 录入人
		long lCheckuserID = -1;// 复核人

		long lQueryType = -1;// 查询类型

		long lOrderID = -1;// 排序类型ID
		long lDESC = -1;// 升降序ID
		long lPageCount = -1;// 每页纪录条数

		String str200 = ""; // 定期，通知开立
		String strTransactionType = "";// 业务类型字符串
		String strlAbstract = "";// 摘要

		long signer = -1;// 过滤已授权电子签章客户的交易

		long difoffice = -1;// 是否显示通存通兑交易

		long lSource = -1;// 交易-数据来源（1：柜台 2：网银 3：银行 4以上：SAP等外部系统）
		String strApplyCode = "";// 交易-申请指令编号

		// add by 2012-05-17 添加付款方指定编号
		String strPayAccountNos = "";
		// add by 2012-05-17 添加收款方指定编号
		String strReceiveAccountNos = "";
		// 判断是否输入金额
		String payMoneyStartBlank = "";
		String payMoneyEndBlank = "";
		String receiveMoneyStartBlank = "";
		String receiveMoneyEndBlank = "";
		String strPageLoaderKey = "";
		String lUnit = "";
		// 读取数据
		String strTemp = null;
		
		strTemp = (String) map.get("strpageloaderkey");
		if (strTemp != null && strTemp.trim().length() > 0) {
			strPageLoaderKey = strTemp;
		}
		
		strTemp = (String) map.get("laccounttranstypeid");
		if (strTemp != null && strTemp.trim().length() > 0) {
			lAccountTransTypeID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String) map.get("lbankid");
		if (strTemp != null && strTemp.trim().length() > 0) {
			lBankID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String) map.get("lcheckuserid");
		if (strTemp != null && strTemp.trim().length() > 0) {
			lCheckuserID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String) map.get("tsexecuteend");
		if (strTemp != null && strTemp.trim().length() > 0) {
			tsExecuteEnd = DataFormat.getDateTime(strTemp);
		}
		strTemp = (String) map.get("tsexecutestart");
		if (strTemp != null && strTemp.trim().length() > 0) {
			tsExecuteStart = DataFormat.getDateTime(strTemp);
		}
		strTemp = (String) map.get("linputuserid");
		if (strTemp != null && strTemp.trim().length() > 0) {
			lInputuserID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String) map.get("tsintereststartend");
		if (strTemp != null && strTemp.trim().length() > 0) {
			tsInterestStartEnd = DataFormat.getDateTime(strTemp);
		}
		strTemp = (String) map.get("tsintereststartstart");
		if (strTemp != null && strTemp.trim().length() > 0) {
			tsInterestStartStart = DataFormat.getDateTime(strTemp);
		}
		strTemp = (String) map.get("lpayaccountidend");
		if (strTemp != null && strTemp.trim().length() > 0) {
			lPayAccountIDEnd = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String) map.get("lpayaccountidstart");
		if (strTemp != null && strTemp.trim().length() > 0) {
			lPayAccountIDStart = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String) map.get("strpayaccountnoend");
		if (strTemp != null && strTemp.trim().length() > 0) {
			if (strTemp.trim().length() > 8) {
				strPayAccountNoEnd = strTemp;
			}
		}
		strTemp = (String) map.get("strpayaccountnostart");
		if (strTemp != null && strTemp.trim().length() > 0) {
			if (strTemp.trim().length() > 8) {
				strPayAccountNoStart = strTemp;
			}
		}
		strTemp = (String) map.get("dpayamountend");
		if (strTemp != null && strTemp.trim().length() > 0) {
			dPayAmountEnd = Double.valueOf(
					DataFormat.reverseFormatAmount(strTemp)).doubleValue();
		} 
		if (strTemp != null && strTemp.trim().length() > 0) {
			payMoneyEndBlank = "blank";
		}
		strTemp = (String) map.get("dpayamountstart");
		if (strTemp != null && strTemp.trim().length() > 0) {
			dPayAmountStart = Double.valueOf(
					DataFormat.reverseFormatAmount(strTemp)).doubleValue();
		} 
		strTemp = (String) map.get("dpayamountstart");
		if (strTemp != null && strTemp.trim().length() > 0) {
			payMoneyStartBlank = "blank";

		}
		strTemp = (String) map.get("strpayclientnoend");
		if (strTemp != null && strTemp.trim().length() > 0) {
			strPayClientNoEnd = strTemp;
		}
		strTemp = (String) map.get("lpayclientidend");
		if (strTemp != null && strTemp.trim().length() > 0) {
			lPayClientIDEnd = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String) map.get("lpayclientidstart");
		if (strTemp != null && strTemp.trim().length() > 0) {
			lPayClientIDStart = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String) map.get("strpayclientnostart");
		if (strTemp != null && strTemp.trim().length() > 0) {
			strPayClientNoStart = strTemp;
		}
		strTemp = (String) map.get("lreceiveaccountidend");
		if (strTemp != null && strTemp.trim().length() > 0) {
			lReceiveAccountIDEnd = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String) map.get("lreceiveaccountidstart");
		if (strTemp != null && strTemp.trim().length() > 0) {
			lReceiveAccountIDStart = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String) map.get("strreceiveaccountnoend");
		if (strTemp != null && strTemp.trim().length() > 0) {
			if (strTemp.trim().length() > 8) {
				strReceiveAccountNoEnd = strTemp;
			}
		}
		strTemp = (String) map.get("strreceiveaccountnostart");
		if (strTemp != null && strTemp.trim().length() > 0) {
			if (strTemp.trim().length() > 8) {
				strReceiveAccountNoStart = strTemp;
			}
		}
		strTemp = (String) map.get("dreceiveamountend");
		if (strTemp != null && strTemp.trim().length() > 0) {
			dReceiveAmountEnd = Double.valueOf(
					DataFormat.reverseFormatAmount(strTemp)).doubleValue();
		} 
		strTemp = (String) map.get("receivemoneyendblank");
		if (strTemp != null && strTemp.trim().length() > 0) {
			receiveMoneyEndBlank = "blank";
		}
		strTemp = (String) map.get("dreceiveamountstart");
		if (strTemp != null && strTemp.trim().length() > 0) {
			dReceiveAmountStart = Double.valueOf(
					DataFormat.reverseFormatAmount(strTemp)).doubleValue();
		} 
		strTemp = (String) map.get("receivemoneystartblank");
		if (strTemp != null && strTemp.trim().length() > 0) {
			receiveMoneyStartBlank = "blank";
		}
		strTemp = (String) map.get("lreceiveclientidend");
		if (strTemp != null && strTemp.trim().length() > 0) {
			lReceiveClientIDEnd = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String) map.get("lreceiveclientidstart");
		if (strTemp != null && strTemp.trim().length() > 0) {
			lReceiveClientIDStart = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String) map.get("strreceiveclientnoend");
		if (strTemp != null && strTemp.trim().length() > 0) {
			strReceiveClientNoEnd = strTemp;
		}
		strTemp = (String) map.get("strreceiveclientnostart");
		if (strTemp != null && strTemp.trim().length() > 0) {
			strReceiveClientNoStart = strTemp;
		}
		strTemp = (String) map.get("lstatusid");
		if (strTemp != null && strTemp.trim().length() > 0) {
			lStatusID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String) map.get("ltransactiontypeid");
		if (strTemp != null && strTemp.trim().length() > 0) {
			lTransactionTypeID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String) map.get("strtransnoend");
		if (strTemp != null && strTemp.trim().length() > 0) {
			strTransNoEnd = strTemp;
		}
		strTemp = (String) map.get("strtransnostart");
		if (strTemp != null && strTemp.trim().length() > 0) {
			strTransNoStart = strTemp;
		}

		strTemp = (String) map.get("strbankchequeno");
		if (strTemp != null && strTemp.trim().length() > 0) {
			strBankChequeNO = strTemp;
		}

		strTemp = (String) map.get("strdeclarationno");
		if (strTemp != null && strTemp.trim().length() > 0) {
			strDeclarationNO = strTemp;
		}

		strTemp = (String) map.get("lquerytype");
		if (strTemp != null && strTemp.trim().length() > 0) {
			lQueryType = Long.valueOf(strTemp).longValue();
		}

		strTemp = (String) map.get("strtransactiontype");
		if (strTemp != null && strTemp.trim().length() > 0) {
			strTransactionType = strTemp;
		}
		// add by zyyao 2007-6-7 增加摘要作为查询条件
		strTemp = (String) map.get("strlabstract");
		if (strTemp != null && strTemp.trim().length() > 0) {
			strlAbstract = strTemp;
		}

		// 过滤授权电子签章客户的交易
		strTemp = (String) map.get("signer");
		if (strTemp != null && strTemp.trim().length() > 0) {
			signer = Long.valueOf(strTemp).longValue();
		}

		strTemp = (String) map.get("lsource");
		if (strTemp != null && strTemp.trim().length() > 0) {
			lSource = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String) map.get("strapplycode");
		if (strTemp != null && strTemp.trim().length() > 0) {
			strApplyCode = strTemp;
		}

		// 是否显示通存通兑交易
		strTemp = (String) map.get("difoffice");
		if (strTemp != null && strTemp.trim().length() > 0) {
			difoffice = Long.valueOf(strTemp).longValue();
		}

		// add by 2012-05-17 添加付款方指定编号
		strTemp = (String) map.get("strpayaccountnos");
		if (strTemp != null && strTemp.trim().length() > 0) {
			strPayAccountNos = strTemp;
		}
		// add by 2012-05-17 添加收款方指定编号
		strTemp = (String) map.get("strreceiveaccountnos");
		if (strTemp != null && strTemp.trim().length() > 0) {
			strReceiveAccountNos = strTemp;
		}
		strTemp = (String) map.get("lunit");
		if (strTemp != null && strTemp.trim().length() > 0) {
			lUnit = strTemp;
		}
		
		QueryTransactionConditionInfo info = new QueryTransactionConditionInfo();

		// 组装查询条件Info
		info.setQueryType(Long.valueOf(lQueryType).longValue());
		info.setAccountTransTypeID(lAccountTransTypeID);
		info.setBankID(lBankID);
		info.setCheckuserID(lCheckuserID);
		info.setCurrencyID(lCurrencyID);
		info.setExecuteEnd(tsExecuteEnd);
		info.setExecuteStart(tsExecuteStart);
		info.setInputuserID(lInputuserID);
		info.setInterestStartEnd(tsInterestStartEnd);
		info.setInterestStartStart(tsInterestStartStart);
		info.setOfficeID(lOfficeID);
		info.setPayAccountIDEnd(lPayAccountIDEnd);
		info.setPayAccountIDStart(lPayAccountIDStart);
		info.setPayAccountNoEnd(strPayAccountNoEnd);
		info.setPayAccountNoStart(strPayAccountNoStart);
		info.setPayAmountEnd(dPayAmountEnd);
		info.setPayAmountStart(dPayAmountStart);
		info.setPayClientNoEnd(strPayClientNoEnd);
		info.setPayClientIDEnd(lPayClientIDEnd);
		info.setPayClientIDStart(lPayClientIDStart);
		info.setPayClientNoStart(strPayClientNoStart);
		info.setReceiveAccountIDEnd(lReceiveAccountIDEnd);
		info.setReceiveAccountIDStart(lReceiveAccountIDStart);
		info.setReceiveAccountNoEnd(strReceiveAccountNoEnd);
		info.setReceiveAccountNoStart(strReceiveAccountNoStart);
		info.setReceiveAmountEnd(dReceiveAmountEnd);
		info.setReceiveAmountStart(dReceiveAmountStart);
		info.setReceiveClientIDEnd(lReceiveClientIDEnd);
		info.setReceiveClientIDStart(lReceiveClientIDStart);
		info.setReceiveClientNoEnd(strReceiveClientNoEnd);
		info.setReceiveClientNoStart(strReceiveClientNoStart);
		info.setStatusID(lStatusID);
		info.setTransactionTypeID(lTransactionTypeID);
		info.setTransNoEnd(strTransNoEnd);
		info.setTransNoStart(strTransNoStart);
		info.setDeclarationNO(strDeclarationNO);
		info.setBankChequeNO(strBankChequeNO);
		info.setDESC(lDESC);
		info.setOrderID(lOrderID);
		info.setPageCount(lPageCount);
		info.setTransactionTypeIDs(strTransactionType);
		// add by zyyao 2007-6-7 增加摘要作为查询条件
		info.setAbstract(strlAbstract);
		info.setSigner(signer);
		info.setSource(lSource);
		info.setApplyCode(strApplyCode);
		info.setDifoffice(difoffice);
		// add by 2012-05-17 添加付款方指定编号
		info.setPayAppointAccountNo(strPayAccountNos);
		// add by 2012-05-17 添加收款方指定编号
		info.setReceiveAppointAccountNo(strReceiveAccountNos);

		info.setPayMoneyStartBlank(payMoneyStartBlank);
		info.setPayMoneyEndBlank(payMoneyEndBlank);
		info.setReceiveMoneyStartBlank(receiveMoneyStartBlank);
		info.setReceiveMoneyEndBlank(receiveMoneyEndBlank);

		pagerInfo = biz.queryTransaction(info,strPageLoaderKey,lUnit);
		
		return pagerInfo;
	}
	
	public PagerInfo queryTransactionForSubAccount(Map map) throws Exception {
		PagerInfo pagerInfo = null;
		long lOfficeID = Long.parseLong(map.get("lofficeid")+"");//办事处
		long lCurrencyID = Long.parseLong(map.get("lcurrencyid")+"");//币种
		
		String strPayAccountNoStart = "";//付款方账户号（由）
		String strPayAccountNoEnd = "";//付款方账户号（至）
		String strReceiveAccountNoStart = "";//收款方账户号（由）
		String strReceiveAccountNoEnd = "";//收款方账户号（至）
		Timestamp tsExecuteEnd = null;//
		Timestamp tsExecuteStart = null;//
		
		String strDepositNo = "";//存单号
		long lContractID = -1;//合同ID
		long lPayFormID = -1;//放款通知单（贴现凭证）
		
		long lStatusID = -1;//交易纪录状态
		

		
		//读取数据
		String strTemp = null;
		strTemp = (String)map.get("tsexecuteenddate");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    tsExecuteEnd = DataFormat.getDateTime(strTemp);
		}
		strTemp = (String)map.get("tsexecutestartdate");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    tsExecuteStart = DataFormat.getDateTime(strTemp);
		}
		strTemp = (String)map.get("lpayaccountnoendctrl");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    strPayAccountNoEnd = strTemp;
		}
		strTemp = (String)map.get("lpayaccountnostartctrl");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    strPayAccountNoStart = strTemp;
		}
		strTemp = (String)map.get("lreceiveaccountidendctrl");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    strReceiveAccountNoEnd = strTemp;
		}
		strTemp = (String)map.get("lreceiveaccountidstartctrl");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    strReceiveAccountNoStart = strTemp;
		}
		strTemp = (String)map.get("ltransactionstatusid");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    lStatusID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)map.get("lcontractid");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    lContractID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)map.get("lpayformid");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    lPayFormID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)map.get("strdepositno");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    strDepositNo = strTemp;
		}

		QueryTransactionConditionInfo conditionInfo = new QueryTransactionConditionInfo();
		conditionInfo.setCurrencyID(lCurrencyID);
		conditionInfo.setExecuteEnd(tsExecuteEnd);
		conditionInfo.setExecuteStart(tsExecuteStart);
		conditionInfo.setOfficeID(lOfficeID);
		conditionInfo.setPayAccountNoEnd(strPayAccountNoEnd);
		conditionInfo.setPayAccountNoStart(strPayAccountNoStart);
		conditionInfo.setReceiveAccountNoEnd(strReceiveAccountNoEnd);
		conditionInfo.setReceiveAccountNoStart(strReceiveAccountNoStart);
		conditionInfo.setDepositNo(strDepositNo);
		conditionInfo.setContractID(lContractID);
		conditionInfo.setPayFormID(lPayFormID);
		conditionInfo.setStatusID(lStatusID);
		pagerInfo = biz.queryTransactionInfo(conditionInfo);
		return pagerInfo;
	}
	
	public PagerInfo queryTransactionInfo(Map map) throws Exception {
		
		long lCurrencyID =Long.valueOf( map.get("lcurrencyid").toString()) ;
		Timestamp tsExecuteEnd = DataFormat.getDateTime(map.get("tsexecuteend").toString()) ;
		Timestamp tsExecuteStart =DataFormat.getDateTime(map.get("tsexecutestart").toString()) ;
		long lOfficeID =Long.valueOf( map.get("lofficeid").toString()) ;
		String strPayAccountNoEnd = map.get("strpayaccountnoend").toString() ;
		String strPayAccountNoStart = map.get("strpayaccountnostart").toString() ;
		String strReceiveAccountNoEnd = map.get("strreceiveaccountnoend").toString() ;
		String strReceiveAccountNoStart = map.get("strreceiveaccountnostart").toString() ;
		String strDepositNo =map.get("strdepositno").toString() ;
		long lContractID = Long.valueOf(map.get("lcontractid").toString()) ;
		long lPayFormID =Long.valueOf( map.get("lpayformid").toString()) ;
		long lStatusID =Long.valueOf( map.get("lstatusid").toString()) ;
		
		QueryTransactionConditionInfo conditionInfo = new QueryTransactionConditionInfo();
		conditionInfo.setCurrencyID(lCurrencyID);
		conditionInfo.setExecuteEnd(tsExecuteEnd);
		conditionInfo.setExecuteStart(tsExecuteStart);
		conditionInfo.setOfficeID(lOfficeID);
		conditionInfo.setPayAccountNoEnd(strPayAccountNoEnd);
		conditionInfo.setPayAccountNoStart(strPayAccountNoStart);
		conditionInfo.setReceiveAccountNoEnd(strReceiveAccountNoEnd);
		conditionInfo.setReceiveAccountNoStart(strReceiveAccountNoStart);
		conditionInfo.setDepositNo(strDepositNo);
		conditionInfo.setContractID(lContractID);
		conditionInfo.setPayFormID(lPayFormID);
		conditionInfo.setStatusID(lStatusID); 
		return biz.queryAccountTransactionInfo(conditionInfo);
	}
	
	public PagerInfo queryTransactionInfoForPrint(Map map) throws Exception {
		
		long lStatusID = Long.valueOf( map.get("ltransactionstatusid").toString()) ;
		long lOfficeID = Long.valueOf( map.get("officelist").toString()) ;
		String strTransactionType = map.get("strtransactiontype").toString() ;
		String tsExecuteStartDate = map.get("tsexecutestartdate").toString() ;
		Timestamp tsExecuteStart = DataFormat.getDateTime(tsExecuteStartDate);
		String tsExecuteEndDate = map.get("tsexecuteenddate").toString() ;
		Timestamp tsExecuteEnd = DataFormat.getDateTime(tsExecuteEndDate);
		long lClientID = Long.valueOf( map.get("lclientid").toString()) ;
		long lUserID = Long.valueOf( map.get("luserid").toString()) ;
		long lCurrencyID = Long.valueOf( map.get("lcurrencyid").toString()) ;
		QueryTransactionConditionInfo conditionInfo = new QueryTransactionConditionInfo();
		conditionInfo.setStatusID(lStatusID); 
		conditionInfo.setOfficeID(lOfficeID);
		conditionInfo.setTransactionTypeIDs(strTransactionType);
		conditionInfo.setExecuteStart(tsExecuteStart);
		conditionInfo.setExecuteEnd(tsExecuteEnd);
		conditionInfo.setPayClientIDStart(lClientID);  //付款方客户ID（由）
		conditionInfo.setReceiveClientIDStart(lClientID);  //收款方客户ID（由）
		conditionInfo.setClientId(lClientID);
		conditionInfo.setCurrencyID(lCurrencyID);
		
		return biz.queryTransactionInfoForPrint(conditionInfo,lUserID);
	}
}
