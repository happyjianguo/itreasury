/*
 * Created on 2004-11-26
 * 
 * TODO To change the template for this generated file go to Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.evoucher.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.JspWriter;

import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.settlement.setting.dao.Sett_SpecialOperationDAO;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.settlement.util.SETTConstant.TransactionType;
import com.iss.itreasury.util.Constant;

/**
 * @author hyzeng
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class VOUConstant extends com.iss.itreasury.util.Constant { 

	public static class privilegeType {

		public static final int privilegeType_1 = 1; // ��ѯȨ��

		public static final int privilegeType_2 = 2; // ����Ȩ��
	}

	// ��ӡ����
	public static class PrintSection {

		public static final int FINANCECOMPANY = 1; // ����˾

		public static final int EBANKCUSTOMER = 2; // ���Ͽͻ�

		// ȡ�ô�ӡ��������
		public static final String getName(long operCode) {

			String strReturn = ""; // ��ʼ������ֵ

			switch ((int) operCode) {
			case (int) FINANCECOMPANY:
				strReturn = "����˾";
				break;
			case (int) EBANKCUSTOMER:
				strReturn = "���Ͽͻ�";
				break;
			}

			return strReturn;
		}

		// ȡ�ô�ӡ��������
		public static final long[] getAllCode() {

			long[] transType = { FINANCECOMPANY, EBANKCUSTOMER };
			return transType;
		}

		public static final void showList(JspWriter out, String strControlName,
				long lSelectValue, boolean isNeedAll, boolean isNeedBlank,
				String strProperty, long[] lArrayID) {

			String[] strArrayName = null;
			strArrayName = new String[lArrayID.length];

			for (int i = 0; i < lArrayID.length; i++) {
				strArrayName[i] = getName(lArrayID[i]);
			}
			showCommonList(out, strControlName, lArrayID, strArrayName,
					lSelectValue, isNeedAll, strProperty, isNeedBlank);
		}
	}

	// ���ӵ��ݹ�ͻ���Ȩ����
	public static class EleDocsSet {
		// �Ѿ���Ȩ
		public static final long HASSETRIGHT = 1;

		// δ��Ȩ
		public static final long HASNOSETRIGHT = 0;

	}

	/**
	 * ��������������Ȩ
	 * 
	 * @author zhanglei
	 * 
	 */
	public static class EBankDocRiht {
		/*
		public static final String[][] ebankDocType = {
				{ "201001", "��Ϣ��ѯ-�˻���Ϣ-�˻����" },
				{ "201002", "��Ϣ��ѯ-�˻���Ϣ-�˻����-�˻���ϸ" },
				{ "201003", "��Ϣ��ѯ-�˻���Ϣ-�˻���ʷ��ϸ" },
				{ "201004", "��Ϣ��ѯ-�˻���Ϣ-������λ�˻���ѯ" },
				{ "201005", "��Ϣ��ѯ-�˻���Ϣ-������λ�˻���ѯ-�˻���ϸ" },
				{ "201006", "��Ϣ��ѯ-�����˻���Ϣ-�˻����˵�-�˻�������ϸ" },
				{ "201007", "��Ϣ��ѯ-�����˻���Ϣ-������λ�˻���ѯ-�˻�������ϸ" },
				{ "201008", "��Ϣ��ѯ-Ӧ����Ϣ�ͷ��ÿ���" }//,
//				{String.valueOf(TransType.BANKRECEIVE),"�����տ�"},
//				{String.valueOf(TransType.BANKPAY),"��ҵ����"},
//				{String.valueOf(TransType.INTERNALTRANSFER),"�ڲ�ת��"},
//				{String.valueOf(TransType.OPENFIXDEPOSIT),"���ڿ���"},
//				{String.valueOf(TransType.FIXEDTOCURRENTTRANSFER),"����֧ȡ"},
//				{String.valueOf(TransType.FIXEDCONTINUETRANSFER),"����ת��"},
//				{String.valueOf(TransType.OPENNOTIFYACCOUNT),"֪ͨ����"},
//				{String.valueOf(TransType.NOTIFYDEPOSITDRAW),"֪ͨ���֧ȡ"},
//				{String.valueOf(TransType.TRUSTLOANGRANT),"��Ӫ�����"},
//				{String.valueOf(TransType.TRUSTLOANRECEIVE),"��Ӫ�����ջ�"},
//				{String.valueOf(TransType.DISCOUNTGRANT),"���ַ���"},
//				{String.valueOf(TransType.DISCOUNTRECEIVE),"�����ջ�"},
//				{String.valueOf(TransType.OPENMARGIN),"��֤����"},
//				{String.valueOf(TransType.WITHDRAWMARGIN),"��֤��֧ȡ"},
//				{String.valueOf(TransType.INTERESTFEEPAYMENT),"��Ϣ����֧��"},
//				{String.valueOf(TransType.GENERALLEDGER),"������"},
//				{String.valueOf(TransType.SPECIALOPERATION),"���⽻��"}
				};*/
		public static final long ZHYE = 201001;	//��Ϣ��ѯ-�˻���Ϣ-�˻����
		public static final long ZHMX = 201002;	//��Ϣ��ѯ-�˻���Ϣ-�˻����-�˻���ϸ
		public static final long ZHLSMX = 201003;	//��Ϣ��ѯ-�˻���Ϣ-�˻���ʷ��ϸ
		public static final long XSDWZHCX = 201004;	//��Ϣ��ѯ-�˻���Ϣ-������λ�˻���ѯ
		public static final long XSZHMX = 201005;	//��Ϣ��ѯ-�˻���Ϣ-������λ�˻���ѯ-�˻���ϸ
		public static final long ZHJYMX = 201006;	//��Ϣ��ѯ-�����˻���Ϣ-�˻����˵�-�˻�������ϸ
		public static final long XSZHJYMX = 201007;	//��Ϣ��ѯ-�����˻���Ϣ-������λ�˻���ѯ-�˻�������ϸ
		public static final long YFLX = 201008;	//��Ϣ��ѯ-Ӧ����Ϣ�ͷ��ÿ���
		
		public static final String getName(long typeId){
			
			String strReturn = "";
			switch ((int) typeId) {
				case (int)ZHYE:
					strReturn = "��Ϣ��ѯ-�˻���Ϣ-�˻����";
					break;
				case (int)ZHMX:
					strReturn = "��Ϣ��ѯ-�˻���Ϣ-�˻����-�˻���ϸ";
					break;
				case (int)ZHLSMX:
					strReturn = "��Ϣ��ѯ-�˻���Ϣ-�˻���ʷ��ϸ";
					break;
				case (int)XSDWZHCX:
					strReturn = "��Ϣ��ѯ-�˻���Ϣ-������λ�˻���ѯ";
					break;
				case (int)XSZHMX:
					strReturn = "��Ϣ��ѯ-�˻���Ϣ-������λ�˻���ѯ-�˻���ϸ";
					break;
				case (int)ZHJYMX:
					strReturn = "��Ϣ��ѯ-�����˻���Ϣ-�˻����˵�-�˻�������ϸ";
					break;
				case (int)XSZHJYMX:
					strReturn = "��Ϣ��ѯ-�����˻���Ϣ-������λ�˻���ѯ-�˻�������ϸ";
					break;
				case (int)YFLX:
					strReturn = "��Ϣ��ѯ-Ӧ����Ϣ�ͷ��ÿ���";
					break;
			}
			return strReturn;
		}
		
		
		public static final String getTypeDesc(long typeId){

			String strReturn = "";
			switch ((int) typeId) {
			//------------�������֡�
			case (int)ZHYE:
				strReturn = "��Ϣ��ѯ-�˻���Ϣ-�˻����";
				break;
			case (int)ZHMX:
				strReturn = "��Ϣ��ѯ-�˻���Ϣ-�˻����-�˻���ϸ";
				break;
			case (int)ZHLSMX:
				strReturn = "��Ϣ��ѯ-�˻���Ϣ-�˻���ʷ��ϸ";
				break;
			case (int)XSDWZHCX:
				strReturn = "��Ϣ��ѯ-�˻���Ϣ-������λ�˻���ѯ";
				break;
			case (int)XSZHMX:
				strReturn = "��Ϣ��ѯ-�˻���Ϣ-������λ�˻���ѯ-�˻���ϸ";
				break;
			case (int)ZHJYMX:
				strReturn = "��Ϣ��ѯ-�����˻���Ϣ-�˻����˵�-�˻�������ϸ";
				break;
			case (int)XSZHJYMX:
				strReturn = "��Ϣ��ѯ-�����˻���Ϣ-������λ�˻���ѯ-�˻�������ϸ";
				break;
			case (int)YFLX:
				strReturn = "��Ϣ��ѯ-Ӧ����Ϣ�ͷ��ÿ���";
				break;
			//---------------------------------------------
			case (int) TransactionType.BANKRECEIVE:
				strReturn = "�����տ�";
				break;
			case (int) TransactionType.BANKRECEIVE_GATHERING:
				strReturn = "�����տ���տ���";
				break;
			case (int) TransactionType.BANKRECEIVE_SUBCLIENT:
				strReturn = "�����տ��Ա��λ�տ�";
				break;
			case (int) TransactionType.BANKRECEIVE_TOSUBCLIENT:
				strReturn = "�����տת��Ա��λ�տ�";
				break;
			case (int) TransactionType.CASHRECEIVE:
				strReturn = "�ֽ��տ�";
				break;
			case (int) TransactionType.BANKPAY:
				strReturn = "���и���";
				break;
			case (int) TransactionType.BANKPAY_DOWNTRANSFER:
				strReturn = "���и���»���Ա��λ";
				break;
			case (int) TransactionType.BANKPAY_FINCOMPANYPAY:
				strReturn = "���и���-��˾����";
				break;
			case (int) TransactionType.BANKPAY_PAYSUBACCOUNT:
				strReturn = "���и���-�����˻�";
				break;
			case (int) TransactionType.SUBCLIENT_BANKRECEIVE:
				strReturn = "������λ�����տ�";
				break;
			case (int) TransactionType.SUBCLIENT_BANKPAY:
				strReturn = "������λ���и���";
				break;
			case (int) TransactionType.CHECKPAY:
				strReturn = "֧Ʊ����";
				break;
			case (int) TransactionType.CASHPAY:
				strReturn = "�ֽ𸶿�";
				break;
			case (int) TransactionType.DRAFTPAY:
				strReturn = "Ʊ�㸶��";
				break;
			case (int) TransactionType.INTERNALVIREMENT:
				strReturn = "�ڲ�ת��";
				break;
			case (int) TransactionType.CONSIGNRECEIVE:
				strReturn = "ί���տ�";
				break;
			case (int) TransactionType.CONSIGNSAVE:
				strReturn = "ί�д��";
				break;
			case (int) TransactionType.CAUTIONMONEYSAVE:
				strReturn = "��֤����";
				break;
			case (int) TransactionType.NATIONALDEBT_BUY:
				strReturn = "��ծ����";
				break;
			case (int) TransactionType.NATIONALDEBT_SELL:
				strReturn = "��ծ����";
				break;
			case (int) TransactionType.ONETOMULTI:
				strReturn = "�����";
				break;
			case (int) TransactionType.OTHERPAY:
				strReturn = "��������";
				break;
			case (int) TransactionType.OPENFIXEDDEPOSIT:
				strReturn = "���ڿ���";
				break;
			case (int) TransactionType.FIXEDTOCURRENTTRANSFER:
				strReturn = "����֧ȡ";
				break;
			case (int) TransactionType.FIXEDCONTINUETRANSFER:
				strReturn = "��������";
				break;
			case (int) TransactionType.OPENNOTIFYACCOUNT:
				strReturn = "֪ͨ����";
				break;
			case (int) TransactionType.NOTIFYDEPOSITDRAW:
				strReturn = "֪ͨ���֧ȡ";
				break;
			case (int) TransactionType.TRUSTLOANGRANT:
				strReturn = "��Ӫ�����";
				break;
			case (int) TransactionType.TRUSTLOANRECEIVE:
				strReturn = "��Ӫ�����ջ�";
				break;
			case (int) TransactionType.CONSIGNLOANGRANT:
				strReturn = "ί�д����";
				break;
			case (int) TransactionType.CONSIGNLOANRECEIVE:
				strReturn = "ί�д����ջ�";
				break;
			case (int) TransactionType.DISCOUNTGRANT:
				strReturn = "���ַ���";
				break;
			case (int) TransactionType.DISCOUNTRECEIVE:
				strReturn = "�����ջ�";
				break;
			case (int) TransactionType.MULTILOANRECEIVE:
				strReturn = "��ʴ����ջ�";
				break;
			case (int) TransactionType.TRANSDISCOUNTGRANT:
				strReturn = "ת���ַ���";
				break;	
			case (int) TransactionType.CONSIGNUPRECEIVE:
				strReturn = "ί�������ʽ�";
				break;
			case (int) TransactionType.CONSIGNUPSAVE:
				strReturn = "�ϴ��ʽ���ؼ����Ÿ�Ϣ�ʽ�";
				break;
			case (int) TransactionType.CONSIGNUPSAVERECEIVE:
				strReturn = "�ϴ��ʽ����ռ�����";
				break;
			case (int) TransactionType.SHORTDEBTRECEIVE:
				strReturn = "���̸�";
				break;
			case (int) TransactionType.CONSIGNCAPITALOPERATION:
				strReturn = "�ͻ�ί���ʽ����";
				break;
			case (int) TransactionType.SHORTLOANGRANT:
				strReturn = "���Ŷ��ڴ���";
				break;
			case (int) TransactionType.CYCLOANGRANT:
				strReturn = "����ѭ������";
				break;
			case (int) TransactionType.GENERALLEDGER:
				strReturn = "����ҵ��";
				break;
			case (int) TransactionType.TRANSFEE:
				strReturn = "���׷���";
				break;
			case (int) TransactionType.SPECIALOPERATION:
				strReturn = "����ҵ��";
				break;
			case (int) TransactionType.COMPATIBILITY:
				strReturn = "����ҵ��";
				break;
			case (int) TransactionType.TRANSABATEMENT:
				strReturn = "ת���������Զ�����";
				break;
			case (int) TransactionType.SHORTLOANRECEIVE:
				strReturn = "���ڴ����ջ�";
				break;
			case (int) TransactionType.INTERESTGRANT:
				strReturn = "������Ϣ";
				break;
			case (int) TransactionType.SHUTDOWN:
				strReturn = "�ػ�";
				break;
			case (int) TransactionType.CYCLOANRECEIVE:
				strReturn = "ѭ�������ջ�";
				break;
			case (int) TransactionType.BANKUPSAVE:
				strReturn = "��������";
				break;
			case (int) TransactionType.BANKUPRECEIVE:
				strReturn = "���е���";
				break;
			case (int) TransactionType.BANKINTEREST:
				strReturn = "���з��Ÿ�Ϣ�ʽ�";
				break;
			case (int) TransactionType.CYCCONSIGNLOANGRANT:
				strReturn = "ѭ��ί�д����";
				break;
			case (int) TransactionType.CYCCONSIGNLOANRECEIVE:
				strReturn = "ѭ��ί�д����ջ�";
				break;
			case (int) TransactionType.INTERESTFEEPAYMENT:
				strReturn = "��Ϣ����֧��";
				break;
			case (int) TransactionType.SECURITIESRECEIVE:
				strReturn = "����˾�տ�";
				break;
			case (int) TransactionType.SECURITIESPAY:
				strReturn = "����˾����";
				break;
			case (int) TransactionType.SEC_WTLC_RECEIVE:
				strReturn = "ί������տ�";
				break;
			case (int) TransactionType.SEC_WTLC_PAY:
				strReturn = "ί����Ƹ���";
				break;
			case (int) TransactionType.SEC_ZQCX_RECEIVE:
				strReturn = "ծȯ�����տ�";
				break;
			case (int) TransactionType.SEC_ZQCX_PAY:
				strReturn = "ծȯ��������";
				break;
			case (int) TransactionType.INTEREST_FEE_PAY_CURRENT:
				strReturn = "��Ϣ����֧�����ڽ�Ϣ";
				break;
			case (int) TransactionType.INTEREST_FEE_PAY_MARGIN:
				strReturn = "��Ϣ����֧����֤���Ϣ";
				break;
			case (int) TransactionType.YT_LOAN_COMMISION_FEE:
				strReturn = "���Ŵ����������";
				break;
			case (int) TransactionType.FIXED_DEPOSIT_PREDRAW_INTEREST:
				strReturn = "���ڴ�����Ӧ����Ϣ����������";
				break;
			case (int) TransactionType.MARGIN_DEPOSIT_PREDRAW_INTEREST:
				strReturn = "��֤�������Ӧ����Ϣ����������";
				break;
			case (int) TransactionType.TRUST_LOAN_PREDRAW_INTEREST:
				strReturn = "��Ӫ�������Ӧ����Ϣ����������";
				break;
			case (int) TransactionType.YT_LOAN_PREDRAW_INTEREST:
				strReturn = "���Ŵ������Ӧ����Ϣ����������";
				break;
			case (int) TransactionType.TRUST_LOAN_INTEREST:
				strReturn = "��Ӫ�����Ϣ";
				break;
			case (int) TransactionType.YT_LOAN_INTEREST:
				strReturn = "���Ŵ����Ϣ";
				break;
			case (int) TransactionType.TRUST_LOAN_SURETY_FEE:
				strReturn = "��Ӫ����ᵣ����";
				break;
			case (int) TransactionType.CONSIGN_LOAN_INTEREST:
				strReturn = "ί�д����Ϣ";
				break;
			case (int) TransactionType.CONSIGN_LOAN_COMMISION_FEE:
				strReturn = "ί�д����������";
				break;
			case (int) TransactionType.GRANT_DEBIT_INTEREST:
				strReturn = "���Ÿ�Ϣ�ʽ�";
				break;
			case (int) TransactionType.RECEIVE_DEBIT_INTEREST:
				strReturn = "�ջظ�Ϣ�ʽ�";
				break;
			case (int) TransactionType.BANKGROUPLOANGRANT:
				strReturn = "���Ŵ����";
				break;
			case (int) TransactionType.BANKGROUPLOANRECEIVE:
				strReturn = "���Ŵ����ջ�";
				break;
			// ����ר�ã�04/10/20 add by weilu��
			case (int) TransactionType.CHECK_RECEIVE:
				strReturn = "����֧Ʊ�տ�";
				break;
			case (int) TransactionType.REMIT_RECEIVE:
				strReturn = "���е���տ�";
				break;
			case (int) TransactionType.OTHER_RECEIVE:
				strReturn = "����������ʽ�տ�";
				break;
			case (int) TransactionType.CHECK_PAY:
				strReturn = "����֧Ʊ����";
				break;
			case (int) TransactionType.REMIT_PAY:
				strReturn = "���е�㸶��";
				break;
			case (int) TransactionType.TAX_PAY:
				strReturn = "����˰������";
				break;
			case (int) TransactionType.OTHER_PAY:
				strReturn = "����������ʽ����";
				break;
			case (int) TransactionType.DELEGATION_INCOME_OFFBALANCE:
				strReturn = "�������м�ֵƷ�����ҵ������";
				break;
			case (int) TransactionType.DELEGATION_PAYOUT_OFFBALANCE:
				strReturn = "�������м�ֵƷ�����ҵ�񸶳�";
				break;
			case (int) TransactionType.CONSIGN_INCOME_OFFBALANCE:
				strReturn = "����δ����Ϣ�����ҵ������";
				break;
			case (int) TransactionType.CONSIGN_PAYOUT_OFFBALANCE:
				strReturn = "����δ����Ϣ�����ҵ�񸶳�";
				break;
			case (int) TransactionType.DISCOUNT_INCOME_OFFBALANCE:
				strReturn = "��ҵ��Ʊ���������ҵ������";
				break;
			case (int) TransactionType.DISCOUNT_PAYOUT_OFFBALANCE:
				strReturn = "��ҵ��Ʊ���������ҵ�񸶳�";
				break;
			case (int) TransactionType.ASSURE_INCOME_OFFBALANCE:
				strReturn = "��������ƾ�������ҵ������";
				break;
			case (int) TransactionType.ASSURE_PAYOUT_OFFBALANCE:
				strReturn = "��������ƾ�������ҵ�񸶳�";
				break;
			case (int) TransactionType.REPORTLOSS:
				strReturn = "��ʧ";
				break;
			case (int) TransactionType.REPORTFIND:
				strReturn = "���";
				break;
			case (int) TransactionType.CHANGECERTIFICATE:
				strReturn = "����֤��";
				break;
			case (int) TransactionType.FREEZE:
				strReturn = "����";
				break;
			case (int) TransactionType.DEFREEZE:
				strReturn = "�ⶳ";
				break;
			case (int) TransactionType.ACCOUNTOPEN:
				strReturn = "�˻�����";
				break;
			case (int) TransactionType.ACCOUNTMODIFY:
				strReturn = "�ʻ��޸�";
				break;	
			case (int) TransactionType.FUND_REQUEST:
				strReturn = "�ʽ�����";
				break;
			case (int) TransactionType.FUND_RETURN:
				strReturn = "�ʽ��ϴ�";
				break;
			case (int) TransactionType.BILL_REGISTER:
				strReturn = "�հ�ƾ֤ע��";
				break;
			case (int) TransactionType.BILL_USE:
				strReturn = "�հ�ƾ֤����";
				break;
			case (int) TransactionType.INITIATIVE_TURNIN:
				strReturn = "��������";
				break;
			case (int) TransactionType.DRAW_PRINCIPAL:
				strReturn = "֪ͨ����֧ȡ����";
				break;
			case (int) TransactionType.DRAW_INTEREST:
				strReturn = "֪ͨ����֧ȡ��Ϣ";
				break;
			case (int) TransactionType.UPGATHERING:
				strReturn = "�ʽ�����";
				break;
			case (int) TransactionType.COMMISSION:
				strReturn = "������������ȡ";
				break;
			case (int) TransactionType.INTERESTFEEPAYMENT_SURETY:
				strReturn = "��Ϣ֧������Ӫ�������";
				break;
			case (int) TransactionType.INTERESTFEEPAYMENT_COMMISION:
				strReturn = "��Ϣ֧����ί�д���������";
				break;
			case (int) TransactionType.OPENMARGIN:
				strReturn = "�����տ�";
				break;
			case (int) TransactionType.WITHDRAWMARGIN:
				strReturn = "������";
				break;
			case (int) TransactionType.RECEIVEFINANCE:
				strReturn = "���������տ�";
				break;
			case (int) TransactionType.RETURNFINANCE:
				strReturn = "�������޻���";
				break;
				
			case (int) TransactionType.CAPITALTRANSFER:
				strReturn = "�ʽ𻮲�";
				break;
			case (int) TransactionType.DISCOUNTACCRUAL:
				strReturn = "���ִ������Ӧ����Ϣ����������";
				break;
			case (int) TransactionType.DISCOUNT_LOAN_INTEREST:
				strReturn = "���ִ����Ϣ";
				break;
			case (int) TransactionType.CONSIGN_LOAN_PREDRAW_INTEREST:
				strReturn = "ί�д������Ӧ����Ϣ����������";
				break;
			case (int) TransactionType.CURRENT_DEPOSIT_PREDRAW_INTEREST:
				strReturn = "���ڴ�����Ӧ����Ϣ����������";
				break;
			case (int) TransactionType.CURRENT_NOTIFY_PREDRAW_INTEREST:
				strReturn = "֪ͨ������Ӧ����Ϣ����������";
				break;
			case (int) TransactionType.ABJUSTINTEREST:
				strReturn = "�ۼƷ��õ���";
				break;
			case (int)TransactionType.BANKPAY_NOTONLINE:
				strReturn = "���и����ش���";
				break;
			case (int) TransactionType.CAPITALUP:
				strReturn = "��ҵ�ʽ��ϻ�";
				break;
			case (int)TransactionType.CAPITALDOWN:
				strReturn = "��ҵ�ʽ��²�";
				break;
			case (int) TransactionType.TRANSFERPAY:
				strReturn = "�Ŵ��ʲ�ת�ø���";
				break;
			case (int) TransactionType.TRANSFERREPAY:
				strReturn = "�Ŵ��ʲ�ת���տ�";
				break;
			case (int) TransactionType.AGENTTRANSFERREPAY:
				strReturn = "�Ŵ��ʲ�ת�ô��տ�";
				break;
			case (int) TransactionType.REPURCHASEDRAW:
				strReturn = "�����ع�����";
				break;
			case (int) TransactionType.BREAKINTERESTNOTIFY:
				strReturn = "�������Ͻ�Ϣ";
				break;
				
				
			default:// ������������ҵ��Ľ������ͺ���Ҫ������ҵ������н������ͺϲ�������ҵ��Ľ�����������
			{
				try {
					Sett_SpecialOperationDAO specialDao = new Sett_SpecialOperationDAO();
					strReturn = specialDao.findSpecialOperationByID(typeId).m_strName;
				} catch (Exception e) {
					System.out.println("ȡ�����⽻������ʱ�쳣");
					e.printStackTrace();
				}
			}
			}
			return strReturn;
		
		}
		
		public static final long[] getAllCode()
        {
            long[] lTemp ={ ZHYE, ZHMX, ZHLSMX, XSDWZHCX, XSZHMX, ZHJYMX, XSZHJYMX, YFLX};
            return lTemp;
        }
		
		public static final long[] getAllCode(long officeID, long currencyID) {

			long[] constantArray = Constant.getAllCode(
					"com.iss.itreasury.evoucher.util.VOUConstant$EBankDocRiht",
					officeID, currencyID);
			return constantArray;
		}
		
	}

	// ҵ������
	public static class TransType {

		public static final long BANKRECEIVE = 1; // �����տ�

		public static final long BANKPAY = 2; // ��ҵ����

		public static final long INTERNALTRANSFER = 6; // �ڲ�ת��

		public static final long OPENFIXDEPOSIT = 12; // ���ڿ���

		public static final long FIXEDTOCURRENTTRANSFER = 13; // ����֧ȡ

		public static final long FIXEDCONTINUETRANSFER = 14; // ����ת��

		public static final long OPENNOTIFYACCOUNT = 15; // ֪ͨ����

		public static final long NOTIFYDEPOSITDRAW = 16; // ֪ͨ���֧ȡ

		public static final long TRUSTLOANGRANT = 17; // ��Ӫ�����

		public static final long TRUSTLOANRECEIVE = 18; // ��Ӫ�����ջ�

		public static final long DISCOUNTGRANT = 21; // ���ַ���

		public static final long DISCOUNTRECEIVE = 22; // �����ջ�

		public static final long OPENMARGIN = 301; // ��֤����

		public static final long WITHDRAWMARGIN = 302; // ��֤��֧ȡ

		public static final long INTERESTFEEPAYMENT = 45; // ��Ϣ����֧��

		public static final long GENERALLEDGER = 31; // ������

		public static final long SPECIALOPERATION = 33; // ���⽻��

		// ȡ��ҵ����������
		public static final String getName(long operCode) {

			String strReturn = ""; // ��ʼ������ֵ

			switch ((int) operCode) {
			case (int) BANKRECEIVE:
				strReturn = "�����տ�";
				break;
			case (int) BANKPAY:
				strReturn = "��ҵ����";
				break;
			case (int) INTERNALTRANSFER:
				strReturn = "�ڲ�ת��";
				break;
			case (int) OPENFIXDEPOSIT:
				strReturn = "���ڿ���";
				break;
			case (int) FIXEDTOCURRENTTRANSFER:
				strReturn = "����֧ȡ";
				break;
			case (int) FIXEDCONTINUETRANSFER:
				strReturn = "����ת��";
				break;
			case (int) OPENNOTIFYACCOUNT:
				strReturn = "֪ͨ����";
				break;
			case (int) NOTIFYDEPOSITDRAW:
				strReturn = "֪ͨ���֧ȡ";
				break;
			case (int) TRUSTLOANGRANT:
				strReturn = "��Ӫ�����";
				break;
			case (int) TRUSTLOANRECEIVE:
				strReturn = "��Ӫ�����ջ�";
				break;
			case (int) DISCOUNTGRANT:
				strReturn = "���ַ���";
				break;
			case (int) DISCOUNTRECEIVE:
				strReturn = "�����ջ�";
				break;
			case (int) OPENMARGIN:
				strReturn = "��֤����";
				break;
			case (int) WITHDRAWMARGIN:
				strReturn = "��֤��֧ȡ";
				break;
			case (int) INTERESTFEEPAYMENT:
				strReturn = "��Ϣ����֧��";
				break;
			case (int) GENERALLEDGER:
				strReturn = "������";
				break;
			case (int) SPECIALOPERATION:
				strReturn = "���⽻��";
				break;
			}

			return strReturn;
		}

		// ȡ������ҵ������
		public static final long[] getAllCode() {

			long[] transType = { BANKRECEIVE, BANKPAY, INTERNALTRANSFER,
					OPENFIXDEPOSIT, FIXEDTOCURRENTTRANSFER,
					FIXEDCONTINUETRANSFER, OPENNOTIFYACCOUNT,
					NOTIFYDEPOSITDRAW, TRUSTLOANGRANT, TRUSTLOANRECEIVE,
					DISCOUNTGRANT, DISCOUNTRECEIVE, OPENMARGIN, WITHDRAWMARGIN,
					INTERESTFEEPAYMENT, GENERALLEDGER, SPECIALOPERATION };
			return transType;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {

			long[] constantArray = Constant.getAllCode(
					"com.iss.itreasury.evoucher.util.VOUConstant$TransType",
					officeID, currencyID);

			Sett_SpecialOperationDAO specialDao = new Sett_SpecialOperationDAO();
			long[] specialArray = specialDao
					.getAllSpecialOperationForConstant();
			if (specialArray != null && specialArray.length > 0) {
				long[] arrReturn = new long[constantArray.length
						+ specialArray.length];
				System.arraycopy(constantArray, 0, arrReturn, 0,
						constantArray.length);
				System.arraycopy(specialArray, 0, arrReturn,
						constantArray.length, specialArray.length);
				return arrReturn;
			} else
			// ������ݿ���û����������ҵ�����ͣ���ֱ�ӷ���constant�ж��������
			{
				return constantArray;
			}
		}

		public static final void showList(JspWriter out, String strControlName,
				long lSelectValue, boolean isNeedAll, boolean isNeedBlank,
				String strProperty, long[] lArrayID) {

			String[] strArrayName = null;
			strArrayName = new String[lArrayID.length];

			for (int i = 0; i < lArrayID.length; i++) {
				strArrayName[i] = getName(lArrayID[i]);
			}
			showCommonList(out, strControlName, lArrayID, strArrayName,
					lSelectValue, isNeedAll, strProperty, isNeedBlank);
		}

		/**
		 * ����ģ���ID��ʾ��ģ���ҵ������
		 * 
		 * @param out
		 * @param strControlName
		 * @param lSelectValue
		 * @param isNeedAll
		 * @param isNeedBlank
		 * @param strProperty
		 * @param lArrayID
		 * @param moduleID
		 *            ģ��ID
		 */
		public static final void showList(JspWriter out, String strControlName,
				long lSelectValue, boolean isNeedAll, boolean isNeedBlank,
				String strProperty, int moduleID, long officeID, long currencyID) {

			String[] strArrayName = null;
			long[] lArrayID = null;

			switch ((int) moduleID) {
			case (int) Constant.ModuleType.SETTLEMENT:
				lArrayID = SETTConstant.TransactionType.getAllCode(officeID,
						currencyID);
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = SETTConstant.TransactionType
							.getName(lArrayID[i]);
				}
				break;
			case (int) Constant.ModuleType.LOAN:
				lArrayID = LOANConstant.LoanType.getAllCode();
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = LOANConstant.LoanType
							.getName(lArrayID[i]);
				}
				break;
			}

			showCommonList(out, strControlName, lArrayID, strArrayName,
					lSelectValue, isNeedAll, strProperty, isNeedBlank);
		}
	}

	// �Ƿ����������
	public static class IsEbankApply {

		public static final long YES = 1;

		public static final long NO = 2;
	}

	// �Ƿ�ǩ��
	public static class IsChapter {

		public static final long YES = 1;

		public static final long NO = 2;
	}

	// ����״̬
	public static class VoucherStatus {

		public static final long SAVE = 1;

		public static final long APPROVALING = 2; // ������

		public static final long APPROVED = 3; // ������

		public static final long SIGN = 4; // ��ǩ��

		public static final long REFUSE = 5; // �Ѿܾ�

		public static final long USED = 6; // ��ʹ��

		public static final long[] getAllCode() {

			long[] lTemp = { SAVE, APPROVALING, APPROVED, SIGN, REFUSE, USED };
			return lTemp;
		}

		public static final String getName(long lCode) {

			String strReturn = ""; // ��ʼ������ֵ
			switch ((int) lCode) {
			case (int) SAVE:
				strReturn = "�ѱ���";
				break;
			case (int) APPROVALING:
				strReturn = "������";
				break;
			case (int) APPROVED:
				strReturn = "������";
				break;
			case (int) SIGN:
				strReturn = "��ǩ��";
				break;
			case (int) USED:
				strReturn = "��ʹ��";
				break;
			case (int) REFUSE:
				strReturn = "�Ѿܾ�";
				break;
			}

			return strReturn;
		}
	}

	public static class ReceiptType {

		public static final long INRECONING = 1; // ���ʵ�

		public static final long DEBIETRANSFERSUBPENA = 2; // ����ת�ʽ跽��Ʊ

		public static final long LENDERTRANSFERSUBPENA = 3; // ����ת�ʴ�����Ʊ

		public static final long DEPOSITVOUCHER = 4; // ���֧ȡƾ֤

		public static final String getName(long operCode) {

			String strName = "";
			switch ((int) operCode) {
			case (int) INRECONING:
				strName = "���ʵ�";
			case (int) DEBIETRANSFERSUBPENA:
				strName = "����ת�ʽ跽��Ʊ";
			case (int) LENDERTRANSFERSUBPENA:
				strName = "����ת�ʴ�����Ʊ";
			case (int) DEPOSITVOUCHER:
				strName = "���֧ȡƾ֤";
			}
			return strName;
		}
	}

	// ��ӡģ��
	public static class PrintModule {

		public static final int SETTMENT = 1; // ����

		public static final int LOAN = 2; // ����

		// ȡ�ô�ӡģ������
		public static final String getName(long operCode) {

			String strReturn = ""; // ��ʼ������ֵ

			switch ((int) operCode) {
			case (int) SETTMENT:
				strReturn = "����  ";
				break;
			case (int) LOAN:
				strReturn = "����  ";
				break;
			}

			return strReturn;
		}

		// ȡ�ô�ӡģ������
		public static final long[] getAllCode() {

			long[] transType = { SETTMENT, LOAN };
			return transType;
		}

		public static final void showList(JspWriter out, String strControlName,
				long lSelectValue, boolean isNeedAll, boolean isNeedBlank,
				String strProperty, long[] lArrayID) {

			String[] strArrayName = null;
			strArrayName = new String[lArrayID.length];

			for (int i = 0; i < lArrayID.length; i++) {
				strArrayName[i] = getName(lArrayID[i]);
			}
			showCommonList(out, strControlName, lArrayID, strArrayName,
					lSelectValue, isNeedAll, strProperty, isNeedBlank);
		}
	}

	// added by mzh_fu 2007/07/04 ��������(������ʹ��)
	public static class ApprovalType {
		public static final int REPRINTAPPLY = 1; // ��������

		public static final String getName(long operCode) {
			String strReturn = ""; // ��ʼ������ֵ

			switch ((int) operCode) {
			case (int) REPRINTAPPLY:
				strReturn = "�������� ";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] transType = { REPRINTAPPLY };
			return transType;
		}

		public static final void showList(JspWriter out, String strControlName,
				long lSelectValue, boolean isNeedAll, boolean isNeedBlank,
				String strProperty, long[] lArrayID) {
			String[] strArrayName = null;
			strArrayName = new String[lArrayID.length];

			for (int i = 0; i < lArrayID.length; i++) {
				strArrayName[i] = getName(lArrayID[i]);
			}
			showCommonList(out, strControlName, lArrayID, strArrayName,
					lSelectValue, isNeedAll, strProperty, isNeedBlank);
		}
	}

	public static class XMLValueType {
		public static final int RETURNTABLENAME = 1;

		public static final int RETURNDESC = 2;

		public static final int RETURNVALUE = 3;
	}
}