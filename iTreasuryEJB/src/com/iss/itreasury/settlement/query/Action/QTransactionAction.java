package com.iss.itreasury.settlement.query.Action;

import java.sql.Timestamp;
import java.util.Map;

import com.iss.itreasury.settlement.query.bizlogic.QTransactionBiz;
import com.iss.itreasury.settlement.query.paraminfo.QueryTransactionConditionInfo;
import com.iss.itreasury.util.DataFormat;
import com.iss.sysframe.pager.dataentity.PagerInfo;

/**
 * �˻���ѯ������
 * 
 * @author xiang
 * 
 */
public class QTransactionAction {

	QTransactionBiz biz = new QTransactionBiz();

	public PagerInfo queryTransaction(Map map) throws Exception {
		PagerInfo pagerInfo = null;

		// �������
		long lOfficeID = Long.valueOf((String) map.get("lofficeid"))
				.longValue();// ���´�
		long lCurrencyID = Long.valueOf((String) map.get("lcurrencyid"))
				.longValue();// ����

		long lTransactionTypeID = -1;// ҵ������
		long lAccountTransTypeID = -1;// �˻���������

		long lPayClientIDStart = -1;// ����ͻ�ID���ɣ�
		String strPayClientNoStart = "";// ����ͻ���ţ��ɣ�
		long lPayClientIDEnd = -1;// ����ͻ�ID������
		String strPayClientNoEnd = "";// ����ͻ���ţ�����
		long lPayAccountIDStart = -1;// ����˻����ɣ�
		String strPayAccountNoStart = "";// ����˻��ţ��ɣ�
		long lPayAccountIDEnd = -1;// ����˻�������
		String strPayAccountNoEnd = "";// ����˻��ţ�����
		double dPayAmountStart = 0.0;// ������ɣ�
		double dPayAmountEnd = 0.0;// ���������

		long lReceiveClientIDStart = -1;// �տ�ͻ�ID���ɣ�
		String strReceiveClientNoStart = "";// �տ�ͻ���ţ��ɣ�
		long lReceiveClientIDEnd = -1;// �տ�ͻ�ID������
		String strReceiveClientNoEnd = "";// �տ�ͻ���ţ�����
		long lReceiveAccountIDStart = -1;// �տ�˻����ɣ�
		String strReceiveAccountNoStart = "";// �տ�˻��ţ��ɣ�
		long lReceiveAccountIDEnd = -1;// �տ�˻�������
		String strReceiveAccountNoEnd = "";// �տ�˻��ţ�����
		double dReceiveAmountStart = 0.0;// �տ���ɣ�
		double dReceiveAmountEnd = 0.0;// �տ������

		long lBankID = -1;// ������
		String strTransNoStart = "";// ���׺ţ��ɣ�
		String strTransNoEnd = "";// ���׺ţ�����
		String strBankChequeNO = "";// ֧Ʊ��
		String strDeclarationNO = "";// ������
		Timestamp tsInterestStartStart = null;// ��Ϣ�գ��ɣ�
		Timestamp tsInterestStartEnd = null;// ��Ϣ�գ�����
		Timestamp tsExecuteStart = null;// ִ���գ��ɣ�
		Timestamp tsExecuteEnd = null;// ִ���գ�����
		long lStatusID = -1;// ���׼�¼״̬
		long lInputuserID = -1;// ¼����
		long lCheckuserID = -1;// ������

		long lQueryType = -1;// ��ѯ����

		long lOrderID = -1;// ��������ID
		long lDESC = -1;// ������ID
		long lPageCount = -1;// ÿҳ��¼����

		String str200 = ""; // ���ڣ�֪ͨ����
		String strTransactionType = "";// ҵ�������ַ���
		String strlAbstract = "";// ժҪ

		long signer = -1;// ��������Ȩ����ǩ�¿ͻ��Ľ���

		long difoffice = -1;// �Ƿ���ʾͨ��ͨ�ҽ���

		long lSource = -1;// ����-������Դ��1����̨ 2������ 3������ 4���ϣ�SAP���ⲿϵͳ��
		String strApplyCode = "";// ����-����ָ����

		// add by 2012-05-17 ��Ӹ��ָ�����
		String strPayAccountNos = "";
		// add by 2012-05-17 ����տָ�����
		String strReceiveAccountNos = "";
		// �ж��Ƿ�������
		String payMoneyStartBlank = "";
		String payMoneyEndBlank = "";
		String receiveMoneyStartBlank = "";
		String receiveMoneyEndBlank = "";
		String strPageLoaderKey = "";
		String lUnit = "";
		// ��ȡ����
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
		// add by zyyao 2007-6-7 ����ժҪ��Ϊ��ѯ����
		strTemp = (String) map.get("strlabstract");
		if (strTemp != null && strTemp.trim().length() > 0) {
			strlAbstract = strTemp;
		}

		// ������Ȩ����ǩ�¿ͻ��Ľ���
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

		// �Ƿ���ʾͨ��ͨ�ҽ���
		strTemp = (String) map.get("difoffice");
		if (strTemp != null && strTemp.trim().length() > 0) {
			difoffice = Long.valueOf(strTemp).longValue();
		}

		// add by 2012-05-17 ��Ӹ��ָ�����
		strTemp = (String) map.get("strpayaccountnos");
		if (strTemp != null && strTemp.trim().length() > 0) {
			strPayAccountNos = strTemp;
		}
		// add by 2012-05-17 ����տָ�����
		strTemp = (String) map.get("strreceiveaccountnos");
		if (strTemp != null && strTemp.trim().length() > 0) {
			strReceiveAccountNos = strTemp;
		}
		strTemp = (String) map.get("lunit");
		if (strTemp != null && strTemp.trim().length() > 0) {
			lUnit = strTemp;
		}
		
		QueryTransactionConditionInfo info = new QueryTransactionConditionInfo();

		// ��װ��ѯ����Info
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
		// add by zyyao 2007-6-7 ����ժҪ��Ϊ��ѯ����
		info.setAbstract(strlAbstract);
		info.setSigner(signer);
		info.setSource(lSource);
		info.setApplyCode(strApplyCode);
		info.setDifoffice(difoffice);
		// add by 2012-05-17 ��Ӹ��ָ�����
		info.setPayAppointAccountNo(strPayAccountNos);
		// add by 2012-05-17 ����տָ�����
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
		long lOfficeID = Long.parseLong(map.get("lofficeid")+"");//���´�
		long lCurrencyID = Long.parseLong(map.get("lcurrencyid")+"");//����
		
		String strPayAccountNoStart = "";//����˻��ţ��ɣ�
		String strPayAccountNoEnd = "";//����˻��ţ�����
		String strReceiveAccountNoStart = "";//�տ�˻��ţ��ɣ�
		String strReceiveAccountNoEnd = "";//�տ�˻��ţ�����
		Timestamp tsExecuteEnd = null;//
		Timestamp tsExecuteStart = null;//
		
		String strDepositNo = "";//�浥��
		long lContractID = -1;//��ͬID
		long lPayFormID = -1;//�ſ�֪ͨ��������ƾ֤��
		
		long lStatusID = -1;//���׼�¼״̬
		

		
		//��ȡ����
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
		conditionInfo.setPayClientIDStart(lClientID);  //����ͻ�ID���ɣ�
		conditionInfo.setReceiveClientIDStart(lClientID);  //�տ�ͻ�ID���ɣ�
		conditionInfo.setClientId(lClientID);
		conditionInfo.setCurrencyID(lCurrencyID);
		
		return biz.queryTransactionInfoForPrint(conditionInfo,lUserID);
	}
}
