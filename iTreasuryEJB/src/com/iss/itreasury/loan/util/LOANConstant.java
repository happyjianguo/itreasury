/*
 * Copyright (c) 1999-2001 AsiaEC.com. All Rights Reserved.
 *
 * ���幦��˵�������������Ŀ���õ��ĳ���
 *
 * ʹ��ע�����
 * 1
 * 2

 * 3
 *
 * ���ߣ�yfan
 *
 * ������Ա��
 *
 */
package com.iss.itreasury.loan.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import javax.servlet.jsp.JspWriter;

import com.iss.itreasury.loan.credit.dao.CreditLimitDAO;
import com.iss.itreasury.loan.setting.bizlogic.LoanContractAssortSettingBiz;
import com.iss.itreasury.loan.setting.dataentity.LoanAssortSettingInfo;
import com.iss.itreasury.loan.bizdelegation.LoanAssortSettingDelegation;
import com.iss.itreasury.loan.bizdelegation.LoanTypeRelationDelegation;
import com.iss.itreasury.util.Constant;

public class LOANConstant extends com.iss.itreasury.util.Constant {
	// ��������
	//
	// ��ҵ����
	public static class ClientCorpNature {
		// ��ҵ����
		public static final long MARKET = 1; // ����

		public static final long OUTOFMARKET = 2; // ������

		public static final long OTHER = 3; // ����

		public static final String getName(long lCode) {
			String strReturn = ""; // ��ʼ������ֵ
			switch ((int) lCode) {
			case (int) MARKET:
				strReturn = "����";
				break;
			case (int) OUTOFMARKET:
				strReturn = "������";
				break;
			case (int) OTHER:
				strReturn = "����";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { MARKET, OUTOFMARKET, OTHER };
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant
					.getAllCode(
							"com.iss.itreasury.loan.util.LOANConstant$ClientCorpNature",
							officeID, currencyID);
		}

		/**
		 * ��ʾ�����б�
		 * 
		 * @param out
		 * @param strControlName,
		 *            �ؼ�����
		 * @param nType���ؼ����ͣ�0����ʾȫ������
		 * @param lSelectValue
		 * @param isNeedAll���Ƿ���Ҫ��ȫ���
		 * @param isNeedBlank
		 *            �Ƿ���Ҫ�հ���
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode();
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				System.out.println(ex.toString());
			}
		}

		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty, long lOfficeID,
				long lCurrencyID) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode(lOfficeID, lCurrencyID);
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				System.out.println(ex.toString());
			}
		}

	}

//	�жһ�Ʊ
	public static class AcceptBillStatus {
		// �жһ�Ʊ״̬
		public static final long OPEN = 1;// ����

		public static final long CLOSE = 2;// �ѳж�

		public static final String getName(long lCode) {
			String strReturn = ""; // ��ʼ������ֵ
			switch ((int) lCode) {
			case (int) OPEN:
				strReturn = "����";
				break;
			case (int) CLOSE:
				strReturn = "�ѳж�";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = null;
			lTemp = new long[2];
			lTemp[0] = OPEN;
			lTemp[1] = CLOSE;
			return lTemp;
		}

	}
	
	// �Ŵ�֤��
	public static class CreditProveStatus {
		// �Ŵ�֤��״̬
		public static final long OPEN = 1;// ����

		public static final long CLOSE = 2;// ����

		public static final String getName(long lCode) {
			String strReturn = ""; // ��ʼ������ֵ
			switch ((int) lCode) {
			case (int) OPEN:
				strReturn = "����";
				break;
			case (int) CLOSE:
				strReturn = "����";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = null;
			lTemp = new long[2];
			lTemp[0] = OPEN;
			lTemp[1] = CLOSE;
			return lTemp;
		}

	}
	
	// ����
	public static class LetterGuaranteeStatus {
		// �Ŵ�֤��״̬
		public static final long OPEN = 1;// ִ����

		public static final long CLOSE = 2;// ʧЧ

		public static final String getName(long lCode) {
			String strReturn = ""; // ��ʼ������ֵ
			switch ((int) lCode) {
			case (int) OPEN:
				strReturn = "ִ����";
				break;
			case (int) CLOSE:
				strReturn = "ʧЧ";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = null;
			lTemp = new long[2];
			lTemp[0] = OPEN;
			lTemp[1] = CLOSE;
			return lTemp;
		}

	}

	//����֤
	public static class LetterCreditStatus{
		//����֤״̬
		public static final long OPEN = 1;//����
		
		public static final long DDTZ = 2;//����֪ͨ
		
		public static final long YHZ = 3;//ѹ����
		
		public static final long YHJS = 4;//ѹ�����
		
		public static final long YSD = 5;//���굥
		
		public static final String getName(long lCode){
			String strReturn = "";
			switch((int)lCode){
			case (int)OPEN:
				strReturn = "����";
				break;
			case (int)DDTZ:
				strReturn = "����֪ͨ";
				break;
			case (int)YHZ:
				strReturn = "ѹ����";
				break;
			case (int)YHJS:
				strReturn = "ѹ�����";
				break;
			case (int)YSD:
				strReturn = "���굥";
				break;
			}
			return strReturn;
		}
		public static final long[] getAllCode() {
			long[] lTemp = null;
			lTemp = new long[5];
			lTemp[0] = OPEN;
			lTemp[1] = DDTZ;
			lTemp[2] = YHZ;
			lTemp[3] = YHJS;
			lTemp[4] = YSD;
			return lTemp;
		}
		
	}


	// ��������
	public static class CurrencyType {
		public static final long CNY = 1; // �����

		public static final long USD = 2; // ��Ԫ

		public static final long EUR = 3; // ŷԪ

		public static final long JPY = 4; // ��Ԫ

		public static final String getName(long lCode) {
			String strReturn = ""; // ��ʼ������ֵ
			switch ((int) lCode) {
			case (int) CNY:
				strReturn = "��";
				break;
			case (int) USD:
				strReturn = "$";
				break;
			case (int) EUR:
				strReturn = "&euro;";
				break;
			case (int) JPY:
				strReturn = "&yen;";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = null;
			lTemp = new long[4];
			lTemp[0] = CNY;
			lTemp[1] = USD;
			lTemp[2] = EUR;
			lTemp[3] = JPY;
			return lTemp;
		}
	
		/**
		 * ��ʾ�����б�
		 * 
		 * @param out
		 * @param strControlName     �ؼ�����
		 * @param nType���ؼ����ͣ�0����ʾȫ������
		 * @param lSelectValue
		 * @param isNeedAll���Ƿ���Ҫ��ȫ���
		 * @param isNeedBlank �Ƿ���Ҫ�հ���
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
		{
			long[] lArrayID = null;
			String[] strArrayName = null;
			
			try
			{
				lArrayID = getAllCode();				
				strArrayName = new String[lArrayID.length];
				
				for (int i = 0; i < lArrayID.length; i++)
				{
					strArrayName[i] = getName(lArrayID[i]);
				}
				
				showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
			}
			catch (Exception ex)
			{
				System.out.println(ex.toString());
			}
		}
	}

	// ��ҵ����
	public static class ClientCorpIndustry {
		// ��ҵ����
		public static final long YXZRGS = 1; // �������ι�˾

		public static final long GYDZGS = 2; // ���ж�����ҵ

		public static final long WSDZGS = 3; // ���̶�����ҵ

		public static final long GFYXGSSS = 4; // �ɷ����ޣ����У�

		public static final long GFYXGSWSS = 5; // �ɷ����ޣ�δ���У�

		public static final long QT = 6; // ����

		public static final String getName(long lCode) {
			String strReturn = ""; // ��ʼ������ֵ
			switch ((int) lCode) {
			case (int) YXZRGS:
				strReturn = "�������ι�˾";
				break;
			case (int) GYDZGS:
				strReturn = "���ж�����ҵ";
				break;
			case (int) WSDZGS:
				strReturn = "���̶�����ҵ";
				break;
			case (int) GFYXGSSS:
				strReturn = "�ɷ����޹�˾�����У�";
				break;
			case (int) GFYXGSWSS:
				strReturn = "�ɷ����޹�˾��δ���У�";
				break;
			case (int) QT:
				strReturn = "����";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = null;
			lTemp = new long[6];
			lTemp[0] = YXZRGS;
			lTemp[1] = GYDZGS;
			lTemp[2] = WSDZGS;
			lTemp[3] = GFYXGSSS;
			lTemp[4] = GFYXGSWSS;
			lTemp[5] = QT;

			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant
					.getAllCode(
							"com.iss.itreasury.loan.util.LOANConstant$ClientCorpIndustry",
							officeID, currencyID);
		}

		/**
		 * ��ʾ�����б�
		 * 
		 * @param out
		 * @param strControlName,
		 *            �ؼ�����
		 * @param nType���ؼ����ͣ�0����ʾȫ������
		 * @param lSelectValue
		 * @param isNeedAll���Ƿ���Ҫ��ȫ���
		 * @param isNeedBlank
		 *            �Ƿ���Ҫ�հ���
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode();
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				System.out.println(ex.toString());
			}
		}

		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty, long lOfficeID,
				long lCurrencyID) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode(lOfficeID, lCurrencyID);
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				System.out.println(ex.toString());
			}
		}

	}

	// Ʊ��״̬
	public static class BillStatus {
		// Ʊ��״̬
		public static final long KC = 1; // ���

		public static final long ZT = 2; // ��;

		public static final long YHG = 3; // �ѻع�

	}

	// ��������
	public static class LoanType {
		// ��������
		public static final long ZY = 1; // ��Ӫ����

		public static final long WT = 2; // ί�д���

		public static final long TX = 3; // ����

		public static final long ZGXE = 4; // ����޶�

		public static final long YT = 5; // ���Ŵ���

		public static final long ZTX = 6; // ת����

		public static final long MFXD = 7; // ���Ŵ�

		public static final long DB = 8; // ����

		public static final long RZZL = 10;// ��������

		public static final long OTHER = 9; // ����
		
		public static final long CREDIT = 11;//����
		
		public static final long XDZR = 12; //�Ŵ��ʲ�ת��
		
		public static final long SHYCHDHP = 80;//��ҵ�жһ�Ʊ

		public static final String getName(long lCode) {
			String strReturn = ""; // ��ʼ������ֵ
			switch ((int) lCode) {
			case (int) ZY:
				strReturn = "��Ӫ����";
				break;
			case (int) WT:
				strReturn = "ί�д���";
				break;
			case (int) TX:
				strReturn = "����";
				break;
			case (int) ZGXE:
				strReturn = "����޶����";
				break;
			case (int) YT:
				strReturn = "���Ŵ���";
				break;
			//case (int) ZTX:
			//	strReturn = "ת����";
			//	break;
			case (int) MFXD:
				strReturn = "���Ŵ�";
				break;
			case (int) DB:
				strReturn = "����";
				break;
			case (int) RZZL:
				strReturn = "��������";
				break;
			case (int) OTHER:
				strReturn = "����";
				break;
			case (int) CREDIT:
				strReturn = "��������";
			    break;
			case (int) XDZR:
				strReturn = "�Ŵ��ʲ�ת��";
			    break;
			case (int) SHYCHDHP :
				strReturn = "��ҵ�жһ�Ʊ";
				break;        
			}
			return strReturn;
		}

		// ԭ���Ѿ��д˷���
		public static final long[] getAllCode() {
			long[] loantype = { ZY, WT, TX, ZGXE, YT,MFXD, DB, RZZL,
					OTHER,CREDIT,XDZR };
			return loantype;
		}
		//����ҵ��
		
		// �������� by liuguang ���Ӵ���������ConfigTool�������ã�����Ϣ���ý��㴦��Ӧ�ã� 
		public static final long[] getAllCode1(long officeID,long currencyID)
        {
        	return Constant.getAllCode("com.iss.itreasury.loan.util.LOANConstant$LoanType",officeID,currencyID);
        }
		// �н����룬�ʽ���㣭��ѯ�������ѯ������������ֻ��ʾ�ڲ��ʽ�ռ�ú�����
		public static final long[] getAllCodeForZj()
		{
			long[] loantype = 
			{ ZY, OTHER };
			return loantype;
		}

		// ȡ�������͹�ϵ������ѡ��Ĵ���
		public static final long[] getAllCode(long lOfficeID, long lCurrencyID) {
			LoanTypeRelationDelegation delegation = new LoanTypeRelationDelegation();
			return delegation.getAllSetLoanTypeID(lOfficeID, lCurrencyID);
		}
		
		// ȡ�������͹�ϵ������ѡ��Ĵ���(ע��ȥ���������� by liaiyi 2013-01-15)
		public static final long[] getAllCode2(long lOfficeID, long lCurrencyID) {
			LoanTypeRelationDelegation delegation = new LoanTypeRelationDelegation();
			long[] getAllCode = delegation.getAllSetLoanTypeID(lOfficeID, lCurrencyID);
			
			int count = 0;
			long[] _getAllCode = null;
			
			for(int i=0;i<getAllCode.length;i++){
				long tmp = getAllCode[i];
				if(tmp!=CREDIT){
					count++;
				}
			}
			_getAllCode = new long[count];
			int j = 0;
			for(int i=0;i<getAllCode.length;i++){
				long tmp = getAllCode[i];
				if(tmp!=CREDIT){
					_getAllCode[j] = getAllCode[i];
					j++;
				}
			}
			
			return _getAllCode;
		}

		public static final void showList(JspWriter out, String strControlName,
				long lSelectValue, boolean isNeedAll, boolean isNeedBlank,
				String strProperty, long[] lArrayID) {
			// long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				// lArrayID = getAllCode(lOfficeID,lCurrencyID);
				// lArrayID = getAllSetLoanTypeID(lOfficeID,lCurrencyID);
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				System.out.println(ex.toString());
			}
		}
	}

	// ���õĴ�������
	public static class SubLoanType {
		public static final String getName(long lCode) {
			return LOANNameRef.getSubLoanTypeNameByID(lCode);
		}

		public static final String getCode(long lCode) {
			return LOANNameRef.getSubLoanTypeCodeByID(lCode);
		}

		// ȡ�������͹�ϵ������ѡ�������
		public static final long[] getAllCode(long lOfficeID, long lCurrencyID) {
			LoanTypeRelationDelegation delegation = new LoanTypeRelationDelegation();
			return delegation.getAllSetSubLoanTypeID(lOfficeID, lCurrencyID);
		}

		public static final void showList(JspWriter out, String strControlName,
				long lSelectValue, boolean isNeedAll, boolean isNeedBlank,
				String strProperty, long lOfficeID, long lCurrencyID,
				long[] lLoanTypeID, long[] lSubLoanTypeID) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				// lLoanTypeID,long[] lSubLoanTypeID
				// lArrayID = getAllCode(lOfficeID,lCurrencyID);
				if (lLoanTypeID == null) {
					lArrayID = lSubLoanTypeID;
				} else {
					LoanTypeRelationDelegation delegation = new LoanTypeRelationDelegation();
					lArrayID = delegation.getAllSetSubLoanTypeIDByLoanTypeID(
							lOfficeID, lCurrencyID, lLoanTypeID);
				}

				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				System.out.println(ex.toString());
			}
		}

		public static final void showListByType(JspWriter out,
				String strControlName, long type, long lSelectValue,
				boolean isNeedAll, boolean isNeedBlank, String strProperty,
				long lOfficeID, long lCurrencyID) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				lArrayID = getAllCode(lOfficeID, lCurrencyID);

				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				System.out.println(ex.toString());
			}
		}
	}

	// ����״̬
	public static class LoanStatus {
		// ����״̬
		public static final long SAVE = 1; // �ѱ���

		public static final long SUBMIT = 2; // ���ύ

		public static final long CHECK = 3; // ������

		public static final long REFUSE = -2; // �Ѿܾ�

		public static final long OTHER = 4; // ���� TODO ֵ����

		public static final long CANCEL = 5; // �Ѿ�ȡ��
		
		public static final long APPROVALING = 20; //������		

		public static final String getName(long lCode) {
			String strReturn = ""; // ��ʼ������ֵ
			switch ((int) lCode) {
			case (int) SAVE:
				//modified by mzh_fu 2007/06/11,����δ�ύ����ǰֻ��һ��״̬"�ѱ���"
//				strReturn = "׫д";
				strReturn = "�ѱ���";
				break;
			case (int) SUBMIT:
				strReturn = "���ύ";
				break;
			case (int) CHECK:
				strReturn = "������";
				break;
			case (int) REFUSE:
				strReturn = "�Ѿܾ�";
				break;
			case (int) OTHER:
				strReturn = "����";
				break;
			case (int) CANCEL:
				strReturn = "��ȡ��";
				break;
			case (int) APPROVALING:
				strReturn = "������";
				break;				
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { SAVE, SUBMIT, CHECK, REFUSE, CANCEL };

			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant.getAllCode(
					"com.iss.itreasury.loan.util.LOANConstant$LoanStatus",
					officeID, currencyID);
		}
	}
    
	
	/* �������� ��֤���� �� ��֤��Ĵ���ʽ
     * 1 Ϊ ����/��ǰ����
     * 2 Ϊ �ع�
     * quanshao ��� 2010-5-31
     */
	public static class DecognizanceDeal{
		public static final long MATURITY = 1;  // ����/��ǰ����
		
		public static final long BUY_BAKE = 2;  // �ع�
		
		public static final String getName(long lCode) {
			String strReturn = ""; // ��ʼ������ֵ
			switch ((int) lCode) {
			case (int) MATURITY:
				strReturn = "����/��ǰ����";
				break;
			case (int) BUY_BAKE:
				strReturn = "�ع�";
				break;
			}
			return strReturn;
		}
		
		public static final long[] getAllCode() {
			long[] lTemp = { MATURITY, BUY_BAKE };
			return lTemp;
		}
		/**
		 * ��ʾ�����б�
		 * 
		 * @param out
		 * @param strControlName  �ؼ�����
		 * @param lSelectValue
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName, long lSelectValue, String strProperty) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				   lArrayID     = getAllCode();
				   strArrayName = new String[lArrayID.length];
				   for (int i = 0; i < lArrayID.length; i++) {
					  strArrayName[i] = getName(lArrayID[i]);
				   }
				   showCommonList(out, strControlName, lArrayID, strArrayName,lSelectValue, false, strProperty, false);
			} catch (Exception ex) {
				System.out.println(ex.toString());
			}
		}
		
	}
	
	
	// ��Ʊ����
	public static class DraftType {
		// ��Ʊ����
		public static final long BANK = 1; // ���гжһ�Ʊ

		public static final long BIZ = 2; // ��ҵ�жһ�Ʊ

		public static final String getName(long lCode) {
			String strReturn = ""; // ��ʼ������ֵ
			switch ((int) lCode) {
			case (int) BANK:
				strReturn = "���гжһ�Ʊ";
				break;
			case (int) BIZ:
				strReturn = "��ҵ�жһ�Ʊ";
				break;
			default:
				strReturn = "";
				break;			
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { BANK, BIZ };
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant.getAllCode(
					"com.iss.itreasury.loan.util.LOANConstant$DraftType",
					officeID, currencyID);
		}

		/**
		 * ��ʾ�����б�
		 * 
		 * @param out
		 * @param strControlName,
		 *            �ؼ�����
		 * @param nType���ؼ����ͣ�0����ʾȫ������
		 * @param lSelectValue
		 * @param isNeedAll���Ƿ���Ҫ��ȫ���
		 * @param isNeedBlank
		 *            �Ƿ���Ҫ�հ���
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode();
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				System.out.println(ex.toString());
			}
		}

		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty, long lOfficeID,
				long lCurrencyID) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode(lOfficeID, lCurrencyID);
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				System.out.println(ex.toString());
			}
		}

	}

	// չ��״̬
	public static class ExtendStatus {
		// չ��״̬
		public static final long SAVE = 1; //�ѱ���

		public static final long SUBMIT = 2; // ���ύ

		public static final long CHECK = 3; // ������

		public static final long REFUSE = 4; // �Ѿܾ�

		public static final long USED = 5; // ��ʹ��
		
		public static final long APPROVALING = 20; //������
		
		public static final long CANCEL = 0; //��ȡ��
		
		
		public static final String getName(long lCode) {
			String strReturn = ""; // ��ʼ������ֵ
			switch ((int) lCode) {
			case (int) SAVE:
				//modified by mzh_fu 2007/07/05
//				strReturn = "׫д";
				strReturn = "�ѱ���";
				break;
			case (int) SUBMIT:
				strReturn = "���ύ";
				break;
			case (int) CHECK:
				strReturn = "������";
				break;
			case (int) REFUSE:
				strReturn = "�Ѿܾ�";
				break;
			case (int) USED:
				strReturn = "��ʹ��";
				break;
			case (int) CANCEL:
				strReturn = "��ȡ��";
				break;
			case (int) APPROVALING:
				strReturn = "������";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = null;
			lTemp = new long[7];
			lTemp[0] = SAVE;
			lTemp[1] = SUBMIT;
			lTemp[2] = CHECK;
			lTemp[3] = REFUSE;
			lTemp[4] = USED;
			lTemp[5] = APPROVALING;
			lTemp[6] =CANCEL;
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant.getAllCode(
					"com.iss.itreasury.loan.util.LOANConstant$ExtendStatus",
					officeID, currencyID);
		}
	}

	// ��ͬ״̬
	public static class ContractStatus 
	{
		// ��ͬ״̬
		public static final long SAVE = 1; // �ѱ���

		public static final long SUBMIT = 2; // ���ύ

		public static final long CHECK = 3; // ������

		public static final long NOTACTIVE = 4; // δִ��

		public static final long ACTIVE = 5; // ִ����

		public static final long EXTEND = 6; // ��չ��

		public static final long OVERDUE = 7; // ������

		public static final long DELAYDEBT = 8; // ����

		public static final long BADDEBT = 9; // ����

		public static final long FINISH = 10; // �ѽ���

		public static final long CANCEL = 11; // ��ȡ��

		public static final long REFUSE = 12; // �Ѿܾ�
		
		public static final long APPROVALING = 20; //������
		
		
		public static final String getName(long lCode) {
			String strReturn = ""; // ��ʼ������ֵ
			switch ((int) lCode) {
			case (int) SAVE:
				//modified by mzh_fu 2007/07/05
//				strReturn = "׫д";
				strReturn = "�ѱ���";
				break;
			case (int) SUBMIT:
				strReturn = "���ύ";
				break;
			case (int) CHECK:
				strReturn = "������";
				break;
			case (int) NOTACTIVE:
				strReturn = "δִ��";
				break;
			case (int) ACTIVE:
				strReturn = "ִ����";
				break;
			case (int) EXTEND:
				strReturn = "��չ��";
				break;
			case (int) OVERDUE:
				strReturn = "������";
				break;
			case (int) DELAYDEBT:
				strReturn = "����";
				break;
			case (int) BADDEBT:
				strReturn = "����";
				break;
			case (int) FINISH:
				strReturn = "�ѽ���";
				break;
			case (int) CANCEL:
				strReturn = "��ȡ��";
				break;
			case (int) REFUSE:
				strReturn = "�Ѿܾ�";
				break;
			case (int) APPROVALING:
				strReturn = "������";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = null;

			lTemp = new long[12];
			lTemp[0] = SAVE;
			lTemp[1] = SUBMIT;
			lTemp[2] = CHECK;
			lTemp[3] = NOTACTIVE;
			lTemp[4] = ACTIVE;
			lTemp[5] = EXTEND;
			lTemp[6] = OVERDUE;
			lTemp[7] = DELAYDEBT;
			lTemp[8] = BADDEBT;
			lTemp[9] = FINISH;
			lTemp[10] = CANCEL;
			lTemp[11] = REFUSE;

			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant.getAllCode(
					"com.iss.itreasury.loan.util.LOANConstant$ContractStatus",
					officeID, currencyID);
		}

		// (ע��ȥ�����ύ���ѽ�������ȡ��  by liaiyi 2013-01-15)
		public static final long[] getAllCode3(long officeID, long currencyID) {
			long[] getAllCode = getAllCode(officeID, currencyID);
			
			int count = 0;
			long[] _getAllCode = null;
			
			for(int i=0;i<getAllCode.length;i++){
				long tmp = getAllCode[i];
				if(tmp!=SUBMIT && tmp!=FINISH && tmp!=CANCEL){
				
					count++;
				}
			}
			_getAllCode = new long[count];
			int j = 0;
			for(int i=0;i<getAllCode.length;i++){
				long tmp = getAllCode[i];
				if(tmp!=SUBMIT && tmp!=FINISH && tmp!=CANCEL){
					_getAllCode[j] = getAllCode[i];
					j++;
				}
			}
			
			return _getAllCode;
		}

		/**
		 * ��ʾ�����б�
		 * 
		 * @param out
		 * @param strControlName,
		 *            �ؼ�����
		 * @param nType���ؼ����ͣ�0����ʾȫ����1,����ʹ�ã�����ʾ׫д�����ύ������ˣ���ȡ�����Ѿܾ���2,�Ŵ��ʲ�ת��ʹ�ã�
		 * @param lSelectValue
		 * @param isNeedAll���Ƿ���Ҫ��ȫ���
		 * @param isNeedBlank
		 *            �Ƿ���Ҫ�հ���
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode();
					break;
				case 1:
					lArrayID = new long[7];
					lArrayID[0] = NOTACTIVE;
					lArrayID[1] = ACTIVE;
					lArrayID[2] = EXTEND;
					lArrayID[3] = OVERDUE;
					lArrayID[4] = DELAYDEBT;
					lArrayID[5] = BADDEBT;
					lArrayID[6] = FINISH;
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				System.out.println(ex.toString());
			}
		}

		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty, long lOfficeID,
				long lCurrencyID) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode(lOfficeID, lCurrencyID);
					break;
				case 1:
					lArrayID = new long[7];
					lArrayID[0] = NOTACTIVE;
					lArrayID[1] = ACTIVE;
					lArrayID[2] = EXTEND;
					lArrayID[3] = OVERDUE;
					lArrayID[4] = DELAYDEBT;
					lArrayID[5] = BADDEBT;
					lArrayID[6] = FINISH;
					break;
				case 2:
					lArrayID = new long[2];
					lArrayID[0] = ACTIVE;
					lArrayID[1] = EXTEND;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				System.out.println(ex.toString());
			}
		}

	}
	
	
//	 ���ֻ�Ʊ���� ��������״
	public static class TsDiscountType 
	{
		// ���ֻ�Ʊ���� 
		public static final long BUSINESSPO = 2; // ��ҵ��Ʊ 

		public static final long BANKPO = 1; // ���л�Ʊ

		
		
		public static final String getName(long lCode) {
			String strReturn = ""; // ��ʼ������ֵ
			switch ((int) lCode) {
			case (int) BUSINESSPO:
				//modified by mzh_fu 2007/07/05
//				strReturn = "׫д";
				strReturn = "��ҵ�жһ�Ʊ";
				break;
			case (int) BANKPO:
				strReturn = "���гжһ�Ʊ";
				break;
			}
			return strReturn;
		}
		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant.getAllCode(
					"com.iss.itreasury.loan.util.LOANConstant$TsDiscountType",
					officeID, currencyID);
		}

	}

	/*public static class CreditType 
	{
		// �������� 
		public static final long ZH = 1; // �ۺ�����
		public static final long ZY = 2; // ��Ӫ���� 
		public static final long BUSPO= 3; // ��ҵ�жһ�Ʊ 
			
		public static final String getName(long lCode) {
			String strReturn = ""; // ��ʼ������ֵ
			switch ((int) lCode) {
			case (int) ZH:
				//modified by mzh_fu 2007/07/05
//				strReturn = "׫д";
				strReturn = "�ۺ�����";
				break;
			case (int) ZY:
				strReturn = "��Ӫ����";
				break;
			case (int) BUSPO:
				strReturn = "��ҵ�жһ�Ʊ";
				break;
			}
			return strReturn;
		}
		
		public static final long getRefID(String lName)
		{
			long longReturn =-1; // ��ʼ������ֵ
			if(lName.equals("�ۺ�����"))
			{
				longReturn=1;
			}
			else if (lName.equals("��Ӫ����"))
			{
				longReturn=2;
			}
			else 
			{
				longReturn=3;
			}
			return longReturn;
		}
		
		
		
		
		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant.getAllCode(
					"com.iss.itreasury.loan.util.LOANConstant$CreditType",
					officeID, currencyID);
		}	
	}*/
	
//	 ����Ʒ�� 
	public static class changeType 
	{
		// �������� 
		public static final long XINZENG = 3 ; //�ύ
		public static final long JIA = 1; // �ӷ�
		public static final long JIAN= 2; // ����
		public static final long BIANGENG = 4; //���

		public static final String getOperator(long lCode) {
			String strReturn = ""; // ��ʼ������ֵ
			switch ((int) lCode) {
			case (int) JIA:
				strReturn = "+";
				break;
			case (int) JIAN:
				strReturn = "-";
				break;

			}
			return strReturn;
		}
		
		public static final String getQuery(long lCode) {
			String strReturn = ""; // ��ʼ������ֵ
			switch ((int) lCode) {
			case (int) XINZENG:
				strReturn = "��������";
				break;
			case (int) BIANGENG:
				strReturn = "���ű��";
				break;

			}
			return strReturn;
		}
		
		public static final String getName(long lCode) {
			String strReturn = ""; // ��ʼ������ֵ
			switch ((int) lCode) {
				case (int) JIA:
					strReturn = "���ű��";
					break;
				case (int) JIAN:
					strReturn = "���ű��";
					break;
				case (int) XINZENG:
					strReturn = "��������";
					break;
			}
			return strReturn;
		}
		
		public static final long getRefID(String lName)
		{
			long longReturn =-1; // ��ʼ������ֵ
			if(lName.equals("+"))
			{
				longReturn=1;
			}
			 if (lName.equals("-"))
			{
				longReturn=2;
			}
			return longReturn;
		}
		
		
		
		
		public static final long[] getAllCode()
		{
			long[] lTemp = null;
			lTemp = new long[3];
			lTemp[0] = JIA;
			lTemp[1] = JIAN;
			lTemp[2] = XINZENG;
			return lTemp;
		}
		
		/**
		 * ��ʾ�����б�
		 * 
		 * @param out
		 * @param strControlName     �ؼ�����
		 * @param nType���ؼ����ͣ�0����ʾȫ������
		 * @param lSelectValue �Ƿ�ѡ�пհ�ѡ�ֵΪ��-1������ѡ�У�
		 * @param isNeedAll���Ƿ���Ҫ��ȫ���
		 * @param isNeedBlank �Ƿ���Ҫ�հ���
		 * @param strProperty ������������������ԣ���Ϊ���ַ�����
		 * @param lArrayID �Զ�����������ʵ������
		 */
		public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, 
				boolean isNeedBlank, String strProperty)
		{
			long[] lArrayID = null;
			String[] strArrayName = null;
			
			try
			{
				switch (nType) {
				case 0:
					lArrayID = new long[3];
					lArrayID[0] = JIA;
					lArrayID[1] = JIAN;
					lArrayID[2] = XINZENG;
					break;
				}			
				strArrayName = new String[lArrayID.length];
				
				for (int i = 0; i < lArrayID.length; i++)
				{
					strArrayName[i] = getName(lArrayID[i]);
				}
				
				showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
			}
			catch (Exception ex)
			{
				System.out.println(ex.toString());
			}
		}
		
		
		/**
		 * ��ʾ�����б�
		 * 
		 * @param out
		 * @param strControlName     �ؼ�����
		 * @param nType���ؼ����ͣ�0����ʾȫ������
		 * @param lSelectValue �Ƿ�ѡ�пհ�ѡ�ֵΪ��-1������ѡ�У�
		 * @param isNeedAll���Ƿ���Ҫ��ȫ���
		 * @param isNeedBlank �Ƿ���Ҫ�հ���
		 * @param strProperty ������������������ԣ���Ϊ���ַ�����
		 * @param lArrayID �Զ�����������ʵ������
		 */
		public static final void showOperatorList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, 
				boolean isNeedBlank, String strProperty)
		{
			long[] lArrayID = null;
			String[] strArrayName = null;
			
			try
			{
				switch (nType) {
				case 0:
					lArrayID = new long[2];
					lArrayID[0] = JIA;
					lArrayID[1] = JIAN;
					break;
				}			
				strArrayName = new String[lArrayID.length];
				
				for (int i = 0; i < lArrayID.length; i++)
				{
					strArrayName[i] = getOperator(lArrayID[i]);
				}
				
				showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
			}
			catch (Exception ex)
			{
				System.out.println(ex.toString());
			}
		}
	
	
	/**
	 * ��ʾ�����б�
	 * 
	 * @param out
	 * @param strControlName     �ؼ�����
	 * @param nType���ؼ����ͣ�0����ʾȫ������
	 * @param lSelectValue �Ƿ�ѡ�пհ�ѡ�ֵΪ��-1������ѡ�У�
	 * @param isNeedAll���Ƿ���Ҫ��ȫ���
	 * @param isNeedBlank �Ƿ���Ҫ�հ���
	 * @param strProperty ������������������ԣ���Ϊ���ַ�����
	 * @param lArrayID �Զ�����������ʵ������
	 */
	public static final void showQueryList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, 
			boolean isNeedBlank, String strProperty)
	{
		long[] lArrayID = null;
		String[] strArrayName = null;
		
		try
		{
			switch (nType) {
			case 0:
				lArrayID = new long[2];
				lArrayID[0] = XINZENG;
				lArrayID[1] = BIANGENG;
				break;
			}			
			strArrayName = new String[lArrayID.length];
			
			for (int i = 0; i < lArrayID.length; i++)
			{
				strArrayName[i] = getQuery(lArrayID[i]);
			}
			
			showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
		}
		catch (Exception ex)
		{
			System.out.println(ex.toString());
		}
	}
}
	
	
	
	
	
	
	

	// ��������״̬
	public static class BankCreditStatus
	{
		public static final long ACTIVE = 1;	//ִ����
		public static final long FINISHED = 2;	//�ѽ���
		public static final long CANCELED = 3;	//��ȡ��
		
		// ͨ������ƥ������
		public static final String getName(long lCode)
		{
			String strReturn = ""; //��ʼ������ֵ
			
			switch ((int)lCode)
			{
				case (int)ACTIVE :
					strReturn = "ִ����";
					break;
				case (int)FINISHED :
					strReturn = "�ѽ���";
					break;
				case (int)CANCELED :
					strReturn = "��ȡ��";
					break;
			}
			
			return strReturn;
		}
		
		// �õ����еĴ���
		public static final long[] getAllCode()
		{
			long[] lTemp = null;
			lTemp = new long[3];
			lTemp[0] = ACTIVE;
			lTemp[1] = FINISHED;
			lTemp[2] = CANCELED;
			
			return lTemp;
		}
		
		/**
		 * ��ʾ�����б�
		 * 
		 * @param out
		 * @param strControlName     �ؼ�����
		 * @param nType���ؼ����ͣ�0����ʾȫ������
		 * @param lSelectValue
		 * @param isNeedAll���Ƿ���Ҫ��ȫ���
		 * @param isNeedBlank �Ƿ���Ҫ�հ���
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
		{
			long[] lArrayID = null;
			String[] strArrayName = null;
			try
			{
				lArrayID = getAllCode();				
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++)
				{
					strArrayName[i] = getName(lArrayID[i]);
				}
				
				showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
			}
			catch (Exception ex)
			{
				System.out.println(ex.toString());
			}
		}
	}

	//��������Ʒ��
	public static class BankCreditVariety 
	{
		public static final long MIXEDBANKCREDIT = 0;		//���ö��
		public static final long SHORTTERMLOAN = 1;			//���ڴ���
		public static final long LONGTERMLOAN = 2;			//�г��ڴ���
		public static final long LETTEROFCREDIT = 3;		//����֤
		public static final long LETTEROFIGUARANTEE = 4;	//����
		public static final long PROVEOFCREDIT = 5;			//�Ŵ�֤��
		public static final long ACCEPTBILL = 6;			//�жһ�Ʊ
		
		//ͨ������ƥ������
		public static final String getName(long lCode)
		{
			String strReturn = ""; //��ʼ������ֵ
			
			switch ((int)lCode)
			{
				case (int)MIXEDBANKCREDIT :
					strReturn = "���ö��";
					break;
				case (int)SHORTTERMLOAN :
					strReturn = "���ڴ���";
					break;
				case (int)LONGTERMLOAN :
					strReturn = "�г��ڴ���";
					break;
				case (int)LETTEROFCREDIT :
					strReturn = "����֤";
					break;
				case (int)LETTEROFIGUARANTEE :
					strReturn = "����";
					break;
				case (int)PROVEOFCREDIT :
					strReturn = "�Ŵ�֤��";
					break;
				case (int)ACCEPTBILL :
					strReturn = "�жһ�Ʊ";
					break;
			}
			
			return strReturn;
		}
		
		// �õ����еĴ���
		public static final long[] getAllCode()
		{
			long[] lTemp = null;
			lTemp = new long[7];
			lTemp[0] = MIXEDBANKCREDIT;
			lTemp[1] = SHORTTERMLOAN;
			lTemp[2] = LONGTERMLOAN;
			lTemp[3] = LETTEROFCREDIT;
			lTemp[4] = LETTEROFIGUARANTEE;
			lTemp[5] = PROVEOFCREDIT;
			lTemp[6] = ACCEPTBILL;
			
			return lTemp;
		}
		
		// �õ��ۺ�����Ʒ��
		public static final long[] getBankCreditList() {
			long[] lTemp = null;
			lTemp = new long[6];
			lTemp[0] = SHORTTERMLOAN;
			lTemp[1] = LONGTERMLOAN;
			lTemp[2] = LETTEROFCREDIT;
			lTemp[3] = LETTEROFIGUARANTEE;
			lTemp[4] = PROVEOFCREDIT;
			lTemp[5] = ACCEPTBILL;
			
			return lTemp;
		}
		
		//�õ������ʽ������Ʒ��
		public static final long[] getMonetaryFundCode() {
			long[] lTemp = null;
			lTemp = new long[2];
			lTemp[0] = SHORTTERMLOAN;
			lTemp[1] = LONGTERMLOAN;
			
			return lTemp;
		}
		
		//�õ��м�ҵ�������Ʒ��
		public static final long[] getMiddleOperCode()
		{
			long[] lTemp = null;
			lTemp = new long[4];
			lTemp[1] = LETTEROFCREDIT;
			lTemp[2] = LETTEROFIGUARANTEE;
			lTemp[3] = PROVEOFCREDIT;
			lTemp[4] = ACCEPTBILL;
			
			return lTemp;
		}
		
		/**
		 * ��ʾ�����б�
		 * 
		 * @param out
		 * @param strControlName     �ؼ�����
		 * @param nType���ؼ����ͣ�0����ʾȫ����1����ʾ�ۺ�����Ʒ�֣�2����ʾ�����ʽ�����Ʒ�֣�3����ʾ�м�ҵ������Ʒ�֣�
		 * @param lSelectValue
		 * @param isNeedAll���Ƿ���Ҫ��ȫ���
		 * @param isNeedBlank �Ƿ���Ҫ�հ���
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
		{
			long[] lArrayID = null;
			String[] strArrayName = null;
			try
			{
				switch (nType)
				{
					case 0 :
						lArrayID = getAllCode();
						break;
					case 1:
						lArrayID = getBankCreditList();
						break;
					case 2 :
						lArrayID = getMonetaryFundCode();
						break;
					case 3 :
						lArrayID = getMiddleOperCode();
						break;
				}
				
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++)
				{
					strArrayName[i] = getName(lArrayID[i]);
				}
				
				showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
			}
			catch (Exception ex)
			{
				System.out.println(ex.toString());
			}
		}
	}

	//	���������Ƿ��ѭ��ʹ��
	public static class BankCreditExtReuse
	{
		public static final long YES = 0;	// ��
		public static final long NO  = 1;	// ��
		
		//ͨ������ƥ������
		public static final String getName(long lCode)
		{
			String strReturn = ""; //��ʼ������ֵ
			
			switch ((int)lCode)
			{
				case (int)YES :
					strReturn = "��";
					break;
				case (int)NO :
					strReturn = "��";
					break;
			}
			
			return strReturn;
		}
		
		// �õ����еĴ���
		public static final long[] getAllCode()
		{
			long[] lTemp = null;
			lTemp = new long[2];
			lTemp[0] = YES;
			lTemp[1] = NO;
			
			return lTemp;
		}
		
		/**
		 * ��ʾ�����б�
		 * 
		 * @param out
		 * @param strControlName     �ؼ�����
		 * @param nType���ؼ����ͣ�0����ʾȫ������
		 * @param lSelectValue
		 * @param isNeedAll���Ƿ���Ҫ��ȫ���
		 * @param isNeedBlank �Ƿ���Ҫ�հ���
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
		{
			long[] lArrayID = null;
			String[] strArrayName = null;
			try
			{
				lArrayID = getAllCode();				
				strArrayName = new String[lArrayID.length];
				
				for (int i = 0; i < lArrayID.length; i++)
				{
					strArrayName[i] = getName(lArrayID[i]);
				}
				
				showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
			}
			catch (Exception ex)
			{
				System.out.println(ex.toString());
			}
		}
	}

	// ������������
	public static class BankCreditExtTerm 
	{
		public static final long TERMINABLE = 0;	// ������
		public static final long DATELESS = 1;		// ������
		
		//ͨ������ƥ������
		public static final String getName(long lCode)
		{
			String strReturn = ""; //��ʼ������ֵ
			
			switch ((int) lCode)
			{
				case (int)TERMINABLE :
					strReturn = "������";
					break;
				case (int)DATELESS :
					strReturn = "������";
					break;
			}
			
			return strReturn;
		}
		
		// �õ����еĴ���
		public static final long[] getAllCode()
		{
			long[] lTemp = null;
			lTemp = new long[2];
			lTemp[0] = TERMINABLE;
			lTemp[1] = DATELESS;
			
			return lTemp;
		}
		
		/**
		 * ��ʾ�����б�
		 * 
		 * @param out
		 * @param strControlName     �ؼ�����
		 * @param nType���ؼ����ͣ�0����ʾȫ������
		 * @param lSelectValue
		 * @param isNeedAll���Ƿ���Ҫ��ȫ���
		 * @param isNeedBlank �Ƿ���Ҫ�հ���
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
		{
			long[] lArrayID = null;
			String[] strArrayName = null;
			try
			{
				lArrayID = getAllCode();				
				strArrayName = new String[lArrayID.length];
				
				for (int i = 0; i < lArrayID.length; i++)
				{
					strArrayName[i] = getName(lArrayID[i]);
				}
				
				showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
			}
			catch (Exception ex)
			{
				System.out.println(ex.toString());
			}
		}
	}

	// ��Ѻ����
	public static class ImpawnType {
		// ��������1
		public static final long ENTITY = 1; // ʵ����Ѻ

		public static final long DROIT = 2; // Ȩ����Ѻ

		public static final String getName(long lCode) {
			String strReturn = ""; // ��ʼ������ֵ
			switch ((int) lCode) {
			case (int) ENTITY:
				strReturn = "ʵ����Ѻ";
				break;
			case (int) DROIT:
				strReturn = "Ȩ����Ѻ";
				break;
			}
			return strReturn;
		}
	}

	// ��������
	public static class AssureKind {
		public static final long INTERNAL = 1; // ���ڵ���

		public static final long EXTERNAL = 2; // ���ⵣ��

		public static final String getName(long lCode) {
			String strReturn = ""; // ��ʼ������ֵ
			switch ((int) lCode) {
			case (int) INTERNAL:
				strReturn = "���ڵ���";
				break;
			case (int) EXTERNAL:
				strReturn = "���ⵣ��";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] longTemp = { INTERNAL, EXTERNAL };
			return longTemp;
		}
	}

	// ������ʽ
	public static class AssureType {
		// ������ʽassureMode
		public static final long CREDIT = 1; // ����

		public static final long ASSURE = 2; // ��֤  ��ԭ���ġ���������Ϊ����֤��

		public static final long PLEDGE = 3; // ��Ѻ

		public static final long IMPAWN = 4; // ��Ѻ

		public static final long RECOGNIZANCE = 5; // ��֤��
		//added by xiongfei 2010/05/17
		public static final long REPURCHASE = 6;//�ع�

		public static final String getName(long lCode) {
			String strReturn = ""; // ��ʼ������ֵ
			switch ((int) lCode) {
			case (int) CREDIT:
				strReturn = "����";
				break;
			case (int) ASSURE:
				strReturn = "��֤";
				break;
			case (int) PLEDGE:
				strReturn = "��Ѻ";
				break;
			case (int) IMPAWN:
				strReturn = "��Ѻ";
				break;
			case (int) RECOGNIZANCE:
				strReturn = "��֤��";
				break;
			//added by xiongfei 2010/05/17
			case (int) REPURCHASE:
				strReturn = "�ع�";
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] longTemp = { CREDIT, ASSURE, PLEDGE, IMPAWN, RECOGNIZANCE ,REPURCHASE };
			return longTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant.getAllCode(
					"com.iss.itreasury.loan.util.LOANConstant$AssureType",
					officeID, currencyID);
		}
	}

	// ��������1
	public static class AssureType1 {
		// ��������1
		public static final long FINANCING = 1; // ���ʵ���

		public static final long NONFINANCING = 2; // �����ʵ���

		public static final long OTHER = 3; // ����

		public static final String getName(long lCode) {
			String strReturn = ""; // ��ʼ������ֵ
			switch ((int) lCode) {
			case (int) FINANCING:
				strReturn = "���ʵ���";
				break;
			case (int) NONFINANCING:
				strReturn = "�����ʵ���";
				break;
			case (int) OTHER:
				strReturn = "����";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] longTemp = { FINANCING, NONFINANCING, OTHER };
			return longTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant.getAllCode(
					"com.iss.itreasury.loan.util.LOANConstant$AssureType1",
					officeID, currencyID);
		}

		/**
		 * ��ʾ�����б�
		 * 
		 * @param out
		 * @param strControlName,
		 *            �ؼ�����
		 * @param nType���ؼ����ͣ�0����ʾȫ������
		 * @param lSelectValue
		 * @param isNeedAll���Ƿ���Ҫ��ȫ���
		 * @param isNeedBlank
		 *            �Ƿ���Ҫ�հ���
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode();
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				System.out.println(ex.toString());
			}
		}

		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty, long lOfficeID,
				long lCurrencyID) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode(lOfficeID, lCurrencyID);
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				System.out.println(ex.toString());
			}
		}

	}

	// ��������2
	public static class AssureType2 {
		// ��������2
		public static final long LOAN = 1; // �����

		public static final long HOMELAND = 2; // ó�����µĹ��ڵ���

		public static final long OVERSEAS = 3; // ó�����µĹ��ⵣ��

		public static final long TENDER = 4; // ��Ͷ�굣��

		public static final long PERFORM = 5; // ��Լ����

		public static final long IMPAWN = 6; // �ʱ�

		public static final long PREPAYMENT = 7; // Ԥ�����

		public static final long BORROW = 8; //����
		
		public static final long CREDIT = 9; //���Ŷ�ȵ���
		
		public static final long SECURITIES = 10; //�м�֤ȯ����
		
		public static final long FINANCING = 11; //�������޵���
		
		public static final long DEFERRED = 12; //���ڸ����
		
		public static final long INSURANCE = 13; //Ͷ������
		
		public static final long TARIFF = 14; //��˰����
		
		public static final long SIGHTPAYMENT = 15; //���ڸ����
		
		public static final long EXPERIENCE = 16; //�������޵���
		
		public static final long OTHER = 17; // ����		
		

		public static final String getName(long lCode) {
			String strReturn = ""; // ��ʼ������ֵ
			switch ((int) lCode) {
			case (int) LOAN:
				strReturn = "�����";
				break;
			case (int) HOMELAND:
				strReturn = "ó�����µĹ��ڵ���";
				break;
			case (int) OVERSEAS:
				strReturn = "ó�����µĹ��ⵣ��";
				break;
			case (int) TENDER:
				strReturn = "��Ͷ�굣��";
				break;
			case (int) PERFORM:
				strReturn = "��Լ����";
				break;
			case (int) IMPAWN:
				strReturn = "�ʱ�";
				break;
			case (int) PREPAYMENT:
				strReturn = "Ԥ�����";
				break;
			case (int) OTHER:
				strReturn = "����";
				break;
			case (int) BORROW:
				strReturn = "����";
			break;
			case (int) CREDIT:
				strReturn = "���Ŷ�ȵ���";
			break;
			case (int) SECURITIES:
				strReturn = "�м�֤ȯ����";
			break;
			case (int) FINANCING:
				strReturn = "�������޵���";
			break;
			case (int) DEFERRED:
				strReturn = "���ڸ����";
			break;
			case (int) INSURANCE:
				strReturn = "Ͷ������";
			break;
			case (int) TARIFF:
				strReturn = "��˰����";
			break;
			case (int) SIGHTPAYMENT:
				strReturn = "���ڸ����";
			break;
			case (int) EXPERIENCE:
				strReturn = "�������޵���";
			break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] longTemp = { LOAN, HOMELAND, OVERSEAS, TENDER, PERFORM,
					IMPAWN, PREPAYMENT, OTHER, BORROW, CREDIT, SECURITIES,
					FINANCING , DEFERRED, INSURANCE, TARIFF, SIGHTPAYMENT, EXPERIENCE };
			return longTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant.getAllCode(
					"com.iss.itreasury.loan.util.LOANConstant$AssureType2",
					officeID, currencyID);
		}

		/**
		 * ��ʾ�����б�
		 * 
		 * @param out
		 * @param strControlName,
		 *            �ؼ�����
		 * @param nType���ؼ����ͣ�0����ʾȫ������
		 * @param lSelectValue
		 * @param isNeedAll���Ƿ���Ҫ��ȫ���
		 * @param isNeedBlank
		 *            �Ƿ���Ҫ�հ���
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode();
					break;
				case 1:
					lArrayID = new long[] { BORROW, CREDIT, SECURITIES,
							FINANCING, DEFERRED };
					break;
				case 2:
					lArrayID = new long[] { INSURANCE, PREPAYMENT, PERFORM, TARIFF,
							SIGHTPAYMENT, EXPERIENCE, OTHER };
					break;
				case 3:
					lArrayID = new long[] { OTHER };
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				System.out.println(ex.toString());
			}
		}

		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty, long lOfficeID,
				long lCurrencyID) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode(lOfficeID, lCurrencyID);
					break;
				case 1:
					lArrayID = new long[] { BORROW, CREDIT, SECURITIES,
							FINANCING, DEFERRED };
					break;
				case 2:
					lArrayID = new long[] { INSURANCE, PREPAYMENT, PERFORM, TARIFF,
							SIGHTPAYMENT, EXPERIENCE, OTHER };
					break;
				case 3:
					lArrayID = new long[] { OTHER };
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				System.out.println(ex.toString());
			}
		}

	}

	// ����������
	public static class BuyOrSaleType {
		// ����������
		public static final long BUY = 1; // ��

		public static final long SALE = 2; // ��

		public static final long NOTUSE = 3; // ������

		public static final String getName(long lCode) {
			String strReturn = ""; // ��ʼ������ֵ
			switch ((int) lCode) {
			case (int) BUY:
				strReturn = "��";
				break;
			case (int) SALE:
				strReturn = "��";
				break;
			case (int) NOTUSE:
				strReturn = "������";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { BUY, SALE, NOTUSE };
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant.getAllCode(
					"com.iss.itreasury.loan.util.LOANConstant$BuyOrSaleType",
					officeID, currencyID);
		}

		/**
		 * ��ʾ�����б�
		 * 
		 * @param out
		 * @param strControlName,
		 *            �ؼ�����
		 * @param nType���ؼ����ͣ�0����ʾȫ������
		 * @param lSelectValue
		 * @param isNeedAll���Ƿ���Ҫ��ȫ���
		 * @param isNeedBlank
		 *            �Ƿ���Ҫ�հ���
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode();
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				System.out.println(ex.toString());
			}
		}

		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty, long lOfficeID,
				long lCurrencyID) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode(lOfficeID, lCurrencyID);
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				System.out.println(ex.toString());
			}
		}

	}

	// �ļ�����
	public static class FileType {
		// �ļ�����
		public static final long TXT = 1; // �ı��ļ�

		public static final long DOC = 2; // WORD�ĵ�

		public static final long XSL = 3; // EXCEL�ĵ�

		public static final String getName(long lCode) {
			String strReturn = ""; // ��ʼ������ֵ
			switch ((int) lCode) {
			case (int) TXT:
				strReturn = "��";
				break;
			case (int) DOC:
				strReturn = "��";
				break;
			case (int) XSL:
				strReturn = "������";
				break;
			}
			return strReturn;
		}
	}

	// �ϴ���������ͬ��ʽ�ı�������������������
	public static class AttachParentType {
		// �ĵ�������
		public static final long CONTRACT = 1; // ��ͬ

		public static final long CLIENT = 2; // �ͻ�

		public static final long LOAN = 3; // ����

		public static final long CONSIGNCONTRACT = 4; // ί�к�ͬ

		public static final long EXTENDAPPLY = 5; // չ������

		public static final long CONSIGNUPSAVESETTING = 6; // �ϴ��ʽ��˻�����

		public static final long YTAPPROVALRESOLUTION = 7; // �μ�����������(���Ŵ���)
	}

	// ���Ե����ĺ�ͬ����
	public static class ContractType {
		// ���Ե����ĺ�ͬ����
		public static final long LOAN = 1; // ����ͬ

		public static final long ASSURE = 2; // ������ͬ

		public static final long PLEDGE = 3; // ��Ѻ��ͬ

		public static final long IMPAWN = 4; // ��Ѻ��ͬ

		public static final long EXTEND = 5; // չ�ں�ͬ

		public static final long DKDCB = 6; // ��������

		public static final long ASSUREDCB = 7; // ���������

		public static final long FINANCETJB = 8; // ����ͳ�Ʊ�

		public static final long TX = 9; // ���ֺ�ͬ

		public static final long ZTX = 10; // ת���ֺ�ͬ

		public static final long ZCFZB = 11;// �ʲ���ծ��

		public static final long SYB = 12;// �����

		public static final long XJLLB = 13;// �ֽ�������

		// ���е��ӵ�������Ѻ��ͬ��ί�к�ͬ�� modified by zntan 2004-11-8
		public static final long GQIMPAWN = 14;// ��Ȩ��Ѻ��ͬ

		public static final long GFIMPAWN = 15;// �ɷ���Ѻ��ͬ

		public static final long CDIMPAWN = 16;// �浥��Ѻ��ͬ

		public static final long CONSIGN = 17;// ί�к�ͬ��ί�д���ί�к�ͬ��

		// �Ϻ��������� modified by zntan 2004-11-25
		public static final long ZGEASSURE = 18;// ��߶֤��ͬ

		public static final long ZGEPLEDGE = 19;// ��߶��Ѻ��ͬ

		public static final long DYWQD = 20;// ��Ѻ���嵥

		public static final long DBXY = 21;// ����Э��

		// �Ϻ��ַ����� modified by liwang 2006-03-21
		public static final long SHPF_KLGNBHXY = 22;// �������ڱ���Э��

		public static final long SHPF_RZTenancy = 23;// �������޺�ͬ

		public static final long SHPF_ZGXE = 24;// ����޶����ͬ

		public static final String getName(long lCode) {
			String strReturn = ""; // ��ʼ������ֵ
			switch ((int) lCode) {
			case (int) LOAN:
				strReturn = "����ͬ";
				break;
			case (int) ASSURE:
				strReturn = "������ͬ";
				break;
			case (int) PLEDGE:
				strReturn = "��Ѻ��ͬ";
				break;
			case (int) IMPAWN:
				strReturn = "��Ѻ��ͬ";
				break;
			case (int) EXTEND:
				strReturn = "չ�ں�ͬ";
				break;
			case (int) DKDCB:
				strReturn = "��������";
				break;
			case (int) ASSUREDCB:
				strReturn = "���������";
				break;
			case (int) FINANCETJB:
				strReturn = "����ͳ�Ʊ�";
				break;
			case (int) TX:
				strReturn = "���ֺ�ͬ";
				break;
			//case (int) ZTX:
		//		strReturn = "ת���ֺ�ͬ";
		//		break;
			case (int) ZCFZB:
				strReturn = "�ʲ���ծ��";
				break;
			case (int) SYB:
				strReturn = "�����";
				break;
			case (int) XJLLB:
				strReturn = "�ֽ�������";
				break;
			case (int) GQIMPAWN:
				strReturn = "��Ȩ��Ѻ��ͬ";
				break;
			case (int) GFIMPAWN:
				strReturn = "�ɷ���Ѻ��ͬ";
				break;
			case (int) CDIMPAWN:
				strReturn = "�浥��Ѻ��ͬ";
				break;
			case (int) CONSIGN:
				strReturn = "ί�к�ͬ";
				break;
			case (int) ZGEASSURE:
				strReturn = "��߶֤��ͬ";
				break;
			case (int) ZGEPLEDGE:
				strReturn = "��߶��Ѻ��ͬ";
				break;
			case (int) DYWQD:
				strReturn = "��Ѻ���嵥";
				break;
			case (int) DBXY:
				strReturn = "����Э��";
				break;
			case (int) SHPF_KLGNBHXY:
				strReturn = "�������ڱ���Э��";
				break;
			case (int) SHPF_RZTenancy:
				strReturn = "�������޺�ͬ";
				break;
			case (int) SHPF_ZGXE:
				strReturn = "����޶����ͬ";
				break;
			}
			return strReturn;
		}
	}

	// ���ʵ���״̬
	public static class InterestRateSettingStatus {
		// ���ʵ���״̬
		public static final long SUBMIT = 1; // �ѱ���

		//modified by mzh_fu 2007/07/05
		
		public static final long CHECK = 3; // ������

		public static final long REFUSE = -2; // �Ѿܾ�
        
		public static final long ADJUST = 4;//�ѵ���
		
		public static final long CANCELAPPLY = 0; //ȡ������
		
		public static final long APPROVALING = 20; //������
		
		public static final String getName(long lCode) {
			String strReturn = ""; // ��ʼ������ֵ
			switch ((int) lCode) {
			case (int) SUBMIT:
				strReturn = "�ѱ���";
				break;
			case (int) CHECK:
				strReturn = "������";
				break;
			case (int) REFUSE:
				strReturn = "�Ѿܾ�";
				break;
			case (int) ADJUST:
				strReturn = "�ѵ���";
				break;
			case (int) CANCELAPPLY:
				strReturn = "��ȡ��";
				break;
			
			case (int) APPROVALING:
			strReturn = "������";
			break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = new long[6];
			lTemp[0]= SUBMIT;
			lTemp[1]= CHECK;
			lTemp[2]= REFUSE;
			lTemp[3]=ADJUST;
			lTemp[4]=CANCELAPPLY;
			lTemp[5]=APPROVALING;
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant
					.getAllCode(
							"com.iss.itreasury.loan.util.LOANConstant$InterestRateSettingStatus",
							officeID, currencyID);
		}

	}

	// ���õȼ�
	public static class CreditLevel {
		// ���õȼ�
		public static final long AAA = 1; // AAA��

		public static final long AA = 2; // AA��

		public static final long A = 3; // A��

		public static final long BBB = 4; // BBB��

		public static final long BB = 5; // BB��

		public static final long B = 6; // B��

		public static final long CCC = 7; // CCC��

		public static final long CC = 8; // CC��

		public static final long C = 9; // C��

		public static final long Aa = 10; // Aa��

		public static final long Ab = 11; // Ab��

		public static final long Ac = 12; // Ac��

		public static final long Ba = 13; // Ba��

		public static final long Bb = 14; // Bb��

		public static final long Bc = 15; // Bc��

		public static final long Ca = 16; // Ca��

		public static final long Cb = 17; // Cb��

		public static final long Cc = 18; // Cc��

		public static final String getName(long lCode) {
			String strReturn = ""; // ��ʼ������ֵ
			switch ((int) lCode) {
			case (int) AAA:
				strReturn = "AAA";
				break;
			case (int) AA:
				strReturn = "AA";
				break;
			case (int) A:
				strReturn = "A";
				break;
			case (int) BBB:
				strReturn = "BBB";
				break;
			case (int) BB:
				strReturn = "BB";
				break;
			case (int) B:
				strReturn = "B";
				break;
			case (int) CCC:
				strReturn = "CCC";
				break;
			case (int) CC:
				strReturn = "CC";
				break;
			case (int) C:
				strReturn = "C";
				break;
			case (int) Aa:
				strReturn = "Aa";
				break;
			case (int) Ab:
				strReturn = "Ab";
				break;
			case (int) Ac:
				strReturn = "Ac";
				break;
			case (int) Ba:
				strReturn = "Ba";
				break;
			case (int) Bb:
				strReturn = "Bb";
				break;
			case (int) Bc:
				strReturn = "Bc";
				break;
			case (int) Ca:
				strReturn = "Ca";
				break;
			case (int) Cb:
				strReturn = "Cb";
				break;
			case (int) Cc:
				strReturn = "Cc";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = null;
			lTemp = new long[5];
			lTemp[0] = AAA;
			lTemp[1] = AA;
			lTemp[2] = A;
			lTemp[3] = BBB;
			lTemp[4] = BB;
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant.getAllCode(
					"com.iss.itreasury.loan.util.LOANConstant$CreditLevel",
					officeID, currencyID);
		}

		/**
		 * ��ʾ�����б�
		 * 
		 * @param out
		 * @param strControlName,
		 *            �ؼ�����
		 * @param nType���ؼ����ͣ�0����ʾȫ������
		 * @param lSelectValue
		 * @param isNeedAll���Ƿ���Ҫ��ȫ���
		 * @param isNeedBlank
		 *            �Ƿ���Ҫ�հ���
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode();
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				System.out.println(ex.toString());
			}
		}

		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty, long lOfficeID,
				long lCurrencyID) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode(lOfficeID, lCurrencyID);
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				System.out.println(ex.toString());
			}
		}

	}

	// �ͻ�����
	public static class ClientType {
		// �ͻ�����
		public static final long GFGS = 1; // �ɷݹ�˾

		public static final long JTGS = 2; // ���Ź�˾

		public static final long QT = 3; // ����

		public static final long HCQY = 4; // ������ҵ

		public static final long MFXD = 5; // �����Ŵ�

		public static final String getName(long lCode) {
			String strReturn = ""; // ��ʼ������ֵ
			switch ((int) lCode) {
			case (int) GFGS:
				strReturn = "�ɷݹ�˾";
				break;
			case (int) JTGS:
				strReturn = "���Ź�˾";
				break;
			case (int) QT:
				strReturn = "����";
				break;
			case (int) HCQY:
				strReturn = "������ҵ";
				break;
			case (int) MFXD:
				strReturn = "�����Ŵ�";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { GFGS, JTGS, QT, HCQY, MFXD };
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant.getAllCode(
					"com.iss.itreasury.loan.util.LOANConstant$ClientType",
					officeID, currencyID);
		}

		/**
		 * ��ʾ�����б�
		 * 
		 * @param out
		 * @param strControlName,
		 *            �ؼ�����
		 * @param nType���ؼ����ͣ�0����ʾȫ������
		 * @param lSelectValue
		 * @param isNeedAll���Ƿ���Ҫ��ȫ���
		 * @param isNeedBlank
		 *            �Ƿ���Ҫ�հ���
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode();
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				System.out.println(ex.toString());
			}
		}

		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty, long lOfficeID,
				long lCurrencyID) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode(lOfficeID, lCurrencyID);
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				System.out.println(ex.toString());
			}
		}
	}

	// ��������
	public static class InterestRateType {
		// ��������
		public static final long BANK = 1; // ��������

		public static final long LIBOR = 2; // LIBOR����

		public static final String getName(long lCode) {
			String strReturn = ""; // ��ʼ������ֵ
			switch ((int) lCode) {
			case (int) BANK:
				strReturn = "��������";
				break;
			case (int) LIBOR:
				strReturn = "LIBOR����";
				break;
			}
			return strReturn;
		}
	}

	// �����б�����
	public static class ListType {
		public static final long LOANCLIENTTYPE = 1; // ��Ӫ����ͻ�����

		public static final long SETTCLIENTTYPE = 2; // ����ͻ�����
	}

	// �⻹����״̬
	public static class FreeApplyStatus {
		// �⻹����״̬
		public static final long SUBMIT = 1; // �ѱ���

		public static final long CHECK = 3; // ������

		public static final long USED = 4; // ��ʹ��

		public static final long REFUSE = -2; // �Ѿܾ�
		
		public static final long APPROVALING = 20; //������
		
		//added by ryping on 07-08-08
		public static final long CANCEL = 0;//��ȡ��
		//end
		
		public static final String getName(long lCode) {
			String strReturn = ""; // ��ʼ������ֵ
			switch ((int) lCode) {
			//added by ryping on 07-08-08
			case (int) CANCEL:
				strReturn = "��ȡ��";
				break;
			//end
			case (int) SUBMIT:
				strReturn = "�ѱ���";
				break;
			case (int) CHECK:
				strReturn = "������";
				break;
			case (int) USED:
				strReturn = "��ʹ��";
				break;
			case (int) REFUSE:
				strReturn = "�Ѿܾ�";
				break;
			case (int) APPROVALING:
				strReturn = "������";
				break;
			}
			return strReturn;
		}
		
		//added by ryping on 2007/06/25
		public static final long[] getAllCode() {
			long[] lTemp = { SUBMIT, CHECK, USED, CANCEL ,APPROVALING};
			return lTemp;
		}

		//added by ryping on 2007/06/25
		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode();
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				System.out.println(ex.toString());
			}
		}
	}

	// ��ִͬ�мƻ����״̬
	public static class PlanModifyStatus {
		// �⻹����״̬
		public static final long SUBMIT = 1; // �ѱ���
		
		//modified by mzh_fu 2007/07/05
		//public static final long CHECK = 2; // �����
		public static final long CHECK = 3; // ������

		public static final long REFUSE = -2; // �Ѿܾ�
		
		public static final long APPROVALING = 20; //������

		public static final String getName(long lCode) {
			String strReturn = ""; // ��ʼ������ֵ
			switch ((int) lCode) {
			case (int) SUBMIT:
				// modified by mzh_fu 2007/07/05
				//strReturn = "���ύ";
				strReturn = "�ѱ���";
				break;
			case (int) CHECK:
				strReturn = "������";
				break;
			case (int) REFUSE:
				strReturn = "�Ѿܾ�";
				break;
			case (int) APPROVALING:
				strReturn = "������";
				break;	
			}
			return strReturn;
		}
	}

	// ����״̬
	public static class OverDueStatus {
		// ����״̬
		public static final long SUBMIT = 1; // "�ѱ���";

		public static final long CHECK = 3; // "������";

		public static final long NOTYET = 4; // "û������";

		public static final long YES = 5; // "������";
		
		public static final long APPROVALING = 20; //������

		public static final String getName(long lCode) {
			String strReturn = ""; // ��ʼ������ֵ
			switch ((int) lCode) {
			case (int) SUBMIT:
				// modified by mzh_fu 2007/07/05
				//strReturn = "���ύ";
				strReturn = "�ѱ���";
				break;
			case (int) CHECK:
				strReturn = "������";
				break;
			case (int) NOTYET:
				strReturn = "û������";
				break;
			case (int) YES:
				strReturn = "������";
				break;
			case (int) APPROVALING:
				strReturn = "������";
				break;	
			}
			return strReturn;
		}
	}

	// �ſ�֪ͨ��״̬
	public static class LoanPayNoticeStatus {
		// �ſ�֪ͨ��״̬
		public static final long SUBMIT = 1; // �ѱ���

		public static final long CHECK = 3; // ������

		public static final long USED = 4; // ��ʹ��

		public static final long REFUSE = -2; // �Ѿܾ�
		
		public static final long APPROVALING = 20; //������	
		//modify by fulihe ��Ϊ���ݿ����Ķ���0 �����Ը�Ϊ0,��״̬��ǰΪ5
		public static final long CANCEL = 0; // ��ȡ��

		public static final long[] getAllCode() {
			long[] lTemp = { SUBMIT, CHECK, USED, REFUSE,APPROVALING,CANCEL };
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant
					.getAllCode(
							"com.iss.itreasury.loan.util.LOANConstant$LoanPayNoticeStatus",
							officeID, currencyID);
		}

		public static final String getName(long lCode) {
			String strReturn = ""; // ��ʼ������ֵ
			switch ((int) lCode) {
			case (int) SUBMIT:
				strReturn = "�ѱ���";
				break;
			case (int) CHECK:
				strReturn = "������";
				break;
			case (int) USED:
				strReturn = "��ʹ��";
				break;
			case (int) REFUSE:
				strReturn = "�Ѿܾ�";
				break;
			case (int) APPROVALING:
				strReturn = "������";
				break;
			case (int) CANCEL:
				strReturn = "��ȡ��";
				break;
			}
			return strReturn;
		}
	}

	// ��ǰ����֪ͨ��״̬
	public static class AheadRepayStatus {
		// ��ǰ����֪ͨ��״̬
		public static final long SUBMIT = 1; // �ѱ���

		public static final long CHECK = 3; // �����

		public static final long USED = 4; // ��ʹ��

		public static final long REFUSE = 5; // "�Ѿܾ�";

		public static final long CANCEL = 6; // ��ȡ��
		
		public static final long APPROVALING = 20; //������	
		
		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant.getAllCode("com.iss.itreasury.loan.util.LOANConstant$AheadRepayStatus",
							officeID, currencyID);
		}

		public static final String getName(long lCode) {
			String strReturn = ""; // ��ʼ������ֵ
			switch ((int) lCode) {
			case (int) SUBMIT:
				strReturn = "�ѱ���";
				break;
			case (int) CHECK:
				strReturn = "������";
				break;
			case (int) USED:
				strReturn = "��ʹ��";
				break;
			case (int) REFUSE:
				strReturn = "�Ѿܾ�";
				break;
			case (int) CANCEL:
				strReturn = "��ȡ��";
				break;
			case (int) APPROVALING:
				strReturn = "������";
				break;
			}
			return strReturn;
		}
		
		//Modify by leiyang date 2007/06/25
		public static final long[] getAllCode() {
			long[] lTemp = { SUBMIT, CHECK, USED, CANCEL ,APPROVALING};
			return lTemp;
		}

		//Modify by leiyang date 2007/06/25
		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode();
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				System.out.println(ex.toString());
			}
		}
	}

	// ����ƾ֤״̬
	public static class DiscountCredenceStatus {
		// ����ƾ֤״̬
		public static final long SAVE = 1; // "�ѱ���";

		public static final long SUBMIT = 2; // "���ύ";

		public static final long CHECK = 3; // "������";

		public static final long USED = 4; // "��ʹ��";

		public static final long FINISH = 5; // "�ѽ���";
		
		public static final long APPROVALING = 20; //������	

		public static final long REFUSE = -2; // "�Ѿܾ�";
		
		//added by mzh_fu 2007/03/26 Ϊ�������ƾ֤ȡ����,������ƾ֤��ѯ�в�ѯ������ƾ֤״̬����ʾ������
		public static final long CANCEL = 0; // "��ȡ��";
		
		public static final String getName(long lCode) {
			String strReturn = ""; // ��ʼ������ֵ
			switch ((int) lCode) {
			case (int) SAVE:
				strReturn = "�ѱ���";
				break;
			case (int) SUBMIT:
				strReturn = "���ύ";
				break;
			case (int) APPROVALING:
				strReturn = "������";
				break;
			case (int) CHECK:
				strReturn = "������";
				break;
			case (int) USED:
				strReturn = "��ʹ��";
				break;
			case (int) REFUSE:
				strReturn = "�Ѿܾ�";
				break;
			case (int) FINISH:
				strReturn = "�ѽ���";
				break;
				
			//added by mzh_fu 2007/03/26 Ϊ�������ƾ֤ȡ����,������ƾ֤��ѯ�в�ѯ������ƾ֤״̬����ʾ������
			case (int) CANCEL:
				strReturn = "��ȡ��";
				break;			
			}
			return strReturn;
		}
		public static final long[] getAllCode() {
			long[] lTemp = null;

			lTemp = new long[8];
			lTemp[0] = SAVE;
			lTemp[1] = SUBMIT;
			lTemp[2] = APPROVALING;
			lTemp[3] = CHECK;
			lTemp[4] = USED;
			lTemp[5] = FINISH;
			lTemp[6] = CANCEL;
			lTemp[7] = REFUSE;
			
			return lTemp;
		}
		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant.getAllCode(
					"com.iss.itreasury.loan.util.LOANConstant$DiscountCredenceStatus",
					officeID, currencyID);
		}
	}
	
	//��ҵ�жһ�Ʊ֪ͨ wjdu
	public static class Inform
	{
		public static final long SHQSHXF = 1;//��ȡ������
		public static final long DQCHD = 2;//���ڳж�
		public static final long DFBXSHH = 3;//�渶��Ϣ�ջ�
		
		public static final String getName(long lCode)
		{
			String strReturn = ""; //��ʼ������ֵ
			switch ((int) lCode)
			{
				case (int)   SHQSHXF :strReturn = "��ȡ������";break;
				case (int)   DQCHD  :strReturn = "���ڳж�";break;
				case (int)   DFBXSHH  :strReturn = "�渶��Ϣ�ջ�";break;
			}
			return strReturn;
		}
	}
	

	// Libor����֪ͨ״̬
	public static class LiborInformStatus {
		// Libor����֪ͨ״̬
		public static final long SAVE = 1; // "�ѱ���";

		public static final long SUBMIT = 2; // "���ύ";

		public static final long CHECK = 3; // "������";

		public static final long REFUSE = -2; // "�Ѿܾ�";

		public static final String getName(long lCode) {
			String strReturn = ""; // ��ʼ������ֵ
			switch ((int) lCode) {
			case (int) SAVE:
				//strReturn = "׫д";
				strReturn = "�ѱ���";
				break;
			case (int) SUBMIT:
				strReturn = "���ύ";
				break;
			case (int) CHECK:
				strReturn = "������";
				break;
			case (int) REFUSE:
				strReturn = "�Ѿܾ�";
				break;
			}
			return strReturn;
		}
	}

	// ���֪ͨ��״̬
	public static class LoanDrawNoticeStatus {
		// ���֪ͨ��״̬
		//modified by mzh_fu 2007/07/05
		//public static final long SUBMIT = 2; // ���ύ
		public static final long SUBMIT = 1; // �ѱ���
		
		public static final long CHECK = 3; // ������

		public static final long USED = 4; // ��ʹ��

		public static final long REFUSE = -2; // �Ѿܾ�
		
		public static final long APPLOVING=20;//������
		
		//added by ryping on 07-08-09
		public static final long CANCEL = 0;//��ȡ��
		//end

		public static final long[] getAllCode() {
			long[] lTemp = { SUBMIT, CHECK, USED, REFUSE,APPLOVING };
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant
					.getAllCode(
							"com.iss.itreasury.loan.util.LOANConstant$LoanDrawNoticeStatus",
							officeID, currencyID);
		}

		public static final String getName(long lCode) {
			String strReturn = ""; // ��ʼ������ֵ
			switch ((int) lCode) {
			case (int) CANCEL:
				strReturn = "��ȡ��";
				break;
			case (int) SUBMIT:
				// modified by mzh_fu 2007/07/05
				//strReturn = "���ύ";
				strReturn = "�ѱ���";
				break;
			case (int) CHECK:
				strReturn = "������";
				break;
			case (int) USED:
				strReturn = "��ʹ��";
				break;
			case (int) REFUSE:
				strReturn = "�Ѿܾ�";
				break;
			case (int) APPLOVING:
				strReturn = "������";
				break;
			}
			return strReturn;
		}
	}

	// �ƻ��޸���Դ����
	public static class PlanModifyType {
		// �ƻ��޸���Դ����
		public static final long LOAN = 1; // ��������

		public static final long MENU = 2; // �ƻ��޸�

		public static final long EXTEND = 3; // չ������

		public static final long OVERDUE = 4; // ��������

		public static final String getName(long lCode) {
			String strReturn = ""; // ��ʼ������ֵ
			switch ((int) lCode) {
			case (int) LOAN:
				strReturn = "��������";
				break;
			case (int) MENU:
				strReturn = "�ƻ��޸�";
				break;
			case (int) EXTEND:
				strReturn = "չ������";
				break;
			case (int) OVERDUE:
				strReturn = "��������";
				break;
			}
			return strReturn;
		}
	}

	// �ſ�֪ͨ���޸���Դ
	public static class LoanPayNoticeModifySourceType {
		// �ſ�֪ͨ���޸���Դ
		public static final long JS = 1; // ����

		public static final long XD = 2; // �Ŵ�

		public static final String getName(long lCode) {
			String strReturn = ""; // ��ʼ������ֵ
			switch ((int) lCode) {
			case (int) JS:
				strReturn = "����";
				break;
			case (int) XD:
				strReturn = "�Ŵ�";
				break;
			}
			return strReturn;
		}
	}

	// ��ͬ(����)״̬���״̬
	public static class RiskModifyStatus {
		// ״̬���״̬
		public static final long SUBMIT = 1; // �ѱ���
		
		//modified by mzh_fu 2007/07/05
//		public static final long CHECK = 2; // �����
		public static final long CHECK = 3; // ������

		public static final long REFUSE = -2; // �Ѿܾ�
		
		public static final long APPROVALING = 20; //������
		public static final long cancelApproval = -1;//ȡ������ ������鱨��ȡ������ʱ����loan_risklevel����status��
		public static final String getName(long lCode) {
			String strReturn = ""; // ��ʼ������ֵ
			switch ((int) lCode) {
			case (int) SUBMIT:
				//modified by mzh_fu 2007/07/05
				//strReturn = "���ύ";
				strReturn = "�ѱ���";
				break;
			case (int) CHECK:
				strReturn = "������";
				break;
			case (int) REFUSE:
				strReturn = "�Ѿܾ�";
				break;
			case (int) APPROVALING:
				strReturn = "������";
				break;
			}
			return strReturn;
		}
	}

	// ��������
	public static class VentureLevel {
		// ��������
		public static final long A = 1; // "����";

		public static final long B = 2; // "��ע";

		public static final long C = 3; // "�μ�";

		public static final long D = 4; // "����";

		public static final long E = 5; // "��ʧ";

		public static final String getName(long lCode) {
			String strReturn = ""; // ��ʼ������ֵ
			switch ((int) lCode) {
			case (int) A:
				strReturn = "����";
				break;
			case (int) B:
				strReturn = "��ע";
				break;
			case (int) C:
				strReturn = "�μ�";
				break;
			case (int) D:
				strReturn = "����";
				break;
			case (int) E:
				strReturn = "��ʧ";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { A, B, C, D, E };
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant.getAllCode(
					"com.iss.itreasury.loan.util.LOANConstant$VentureLevel",
					officeID, currencyID);
		}
		public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
		{
			long[] lArrayID = null;
			String[] strArrayName = null;
			try
			{
				switch (nType)
				{
					case 0 :
						lArrayID = getAllCode();
						break;
				}
				strArrayName = new String[lArrayID.length];
				
				for (int i = 0; i < lArrayID.length; i++)
				{
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank, false);
			}
			catch (Exception ex)
			{
				System.out.println(ex.toString());
			}
		}
	}
	
	
	/*
	 * ���󱨸�����������
	 */
	public static class AfterCheckType {
		// �����������
		public static final long JIDU = 1; // "������";

		public static final long MONTH = 2; // "����";

		public static final long TEMP = 3; // "��ʱ";

		
		public static final String getName(long lCode) {
			String strReturn = ""; // ��ʼ������ֵ
			switch ((int) lCode) {
			case (int) JIDU:
				strReturn = "������";
				break;
			case (int) MONTH:
				strReturn = "����";
				break;
			case (int) TEMP:
				strReturn = "��ʱ";
				break;
			
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { JIDU, MONTH, TEMP };
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant.getAllCode(
					"com.iss.itreasury.loan.util.LOANConstant$AfterCheckType",
					officeID, currencyID);
		}
		public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
		{
			long[] lArrayID = null;
			String[] strArrayName = null;
			try
			{
				switch (nType)
				{
					case 0 :
						lArrayID = getAllCode();
						break;
				}
				strArrayName = new String[lArrayID.length];
				
				for (int i = 0; i < lArrayID.length; i++)
				{
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank, false);
			}
			catch (Exception ex)
			{
				System.out.println(ex.toString());
			}
		}
	}
	
	
	
	
	
	
	

	// ���Ե����ĺ�ͬ�ĺ�ͬģ���ļ���
	public static class Template {
		// ģ���ļ���
		public static final long HN_ZYDK = 1; // ��Ӫ����

		public static final long HN_WTDK = 2; // ί�д���

		public static final long HN_WTTJTH = 3; // ͳ��ͳ��

		public static final long HN_ZGXE = 4; // ����޶�

		public static final long HN_YTDK = 5; // ���Ŵ���

		public static final long HN_TX = 6; // ����-���л�Ʊ

		public static final long HN_ASSURE = 7; // ����

		public static final long HN_PLEDGE = 8; // ��Ѻ

		public static final long HN_IMPAWN = 9; // ��Ѻ

		public static final long HN_EXTENDZY = 10; // ��Ӫչ��

		public static final long HN_DKDCB = 11; // ��������

		public static final long HN_EXTENDWT = 12; // ί��չ��

		public static final long HN_TXBIZ = 13; // ����-��ҵ��Ʊ

		// ���е��ӵ�������Ѻ��ͬ�� by zntan 2004-11-8
		public static final long GQIMPAWN = 14;// ��Ȩ��Ѻ

		public static final long GFIMPAWN = 15;// �ɷ���Ѻ

		public static final long CDIMPAWN = 16;// �浥��Ѻ

		public static final long WTWT = 17;// ί�к�ͬ��ί�д���ί�к�ͬ��

		// �Ϻ��������� modified by zntan 2004-11-25
		public static final long ZGEASSURE = 18;// ��߶֤��ͬ

		public static final long ZGEPLEDGE = 19;// ��߶��Ѻ��ͬ

		public static final long DYWQD = 20;// ��Ѻ���嵥

		public static final long DBXY = 21;// ����Э��

		// �Ϻ��ַ����� modified by liwang 2006-03-21
		public static final long SHPF_KLGNBHXY = 22;// �������ڱ���Э��

		public static final long SHPF_RZTenancy = 23;// �������޺�ͬ

		public static final long SHPF_ASSURE = 24;// ��֤��ͬ

		public static final long SHPF_IMPAWN = 25;// ��Ѻ��ͬ

		public static final long SHPF_ZYDK = 26;// ��Ӫ�����ͬ

		public static final long SHPF_WTDK = 27;// ί�д����ͬ

		public static final long SHPF_ZGEASSURE = 28;// ��߶֤��ͬ

		public static final long SHPF_ZGEPLEDGE = 29;// ��߶��Ѻ��ͬ

		public static final String getName(long lCode) {
			String strReturn = ""; // ��ʼ������ֵ

			/* TOCONFIG��TODELETE */
			/*
			 * ��Ʒ������������Ŀ,���е���Ϊ�ο�; ninh 2005-03-24
			 */

			/*
			 * //if (Env.getProjectName().equals(Constant.ProjectName.HN))
			 * //����Ŀ�жϸ�Ϊ�����ļ����� haoning 2004-11-03
			 * if(Config.GLOBAL.getProjectType() == Constant.ProjectType.HN) {
			 * switch ((int) lCode) { case (int) HN_ZYDK : strReturn =
			 * "rmbjkht.txt;c010-v.jsp"; break; case (int) HN_WTDK : strReturn =
			 * "wtdkht.txt;c070-v.jsp"; break; case (int) HN_WTTJTH : strReturn =
			 * "tjthht.txt;c050-v.jsp"; break; case (int) HN_ZGXE : strReturn =
			 * "zgxeht.txt;c080-v.jsp"; break; case (int) HN_YTDK : strReturn =
			 * ""; break; case (int) HN_TX : strReturn =
			 * "yhchdhptxxy.txt;c110-v.jsp"; break; case (int) HN_ASSURE :
			 * strReturn = "bzht.txt;c030-v.jsp"; break; case (int) HN_PLEDGE :
			 * strReturn = "dyht.txt;c040-v.jsp"; break; case (int) HN_IMPAWN :
			 * strReturn = ""; break; case (int) HN_EXTENDZY : strReturn =
			 * "jkzqxy.txt;c310-v.jsp"; break; case (int) HN_EXTENDWT :
			 * strReturn = "wtdkzqxy.txt;c320-v.jsp"; break; case (int) HN_DKDCB :
			 * strReturn = "dkdcb.txt;c101-v.jsp"; break; case (int) HN_TXBIZ :
			 * strReturn = "shychdhptxxy.txt;c125-v.jsp"; break; } } //else if
			 * (Env.getProjectName().equals(Constant.ProjectName.MBEC))
			 * //����Ŀ�жϸ�Ϊ�����ļ����� haoning 2004-11-03 else
			 * if(Config.GLOBAL.getProjectType() == Constant.ProjectType.MBEC) {
			 * strReturn = "dqxtdkht.txt;c120-v.jsp"; } //else if
			 * (Env.getProjectName().equals(Constant.ProjectName.CNMEF))
			 * //����Ŀ�жϸ�Ϊ�����ļ����� haoning 2004-11-03 else
			 * if(Config.GLOBAL.getProjectType() == Constant.ProjectType.CNMEF) {
			 * switch ((int) lCode) { case (int) HN_ZYDK : strReturn =
			 * "rmbjkht.txt;c010-v.jsp"; break; case (int) HN_WTDK : strReturn =
			 * "wtdkht.txt;c070-v.jsp"; break; case (int) HN_WTTJTH : strReturn =
			 * "tjthht.txt;c050-v.jsp"; break; case (int) HN_ZGXE : strReturn =
			 * "zgxeht.txt;c080-v.jsp"; break; case (int) HN_YTDK : strReturn =
			 * ""; break; case (int) HN_TX : strReturn =
			 * "yhchdhptxxy.txt;c110-v.jsp"; break; case (int) HN_ASSURE :
			 * strReturn = "bzht.txt;c030-v.jsp"; break; case (int) HN_PLEDGE :
			 * strReturn = "dyht.txt;c040-v.jsp"; break; case (int) HN_IMPAWN :
			 * strReturn = ""; break; case (int) HN_EXTENDZY : strReturn =
			 * "jkzqxy.txt;c310-v.jsp"; break; case (int) HN_EXTENDWT :
			 * strReturn = "wtdkzqxy.txt;c320-v.jsp"; break; case (int) HN_DKDCB :
			 * strReturn = "dkdcb.txt;c101-v.jsp"; break; case (int) HN_TXBIZ :
			 * strReturn = "shychdhptxxy.txt;c125-v.jsp"; break; } } //else if
			 * (Env.getProjectName().equals(Constant.ProjectName.HAIER))
			 * //����Ŀ�жϸ�Ϊ�����ļ����� haoning 2004-11-03 else
			 * if(Config.GLOBAL.getProjectType() == Constant.ProjectType.HAIER) {
			 * switch ((int) lCode) { case (int) HN_DKDCB : strReturn =
			 * "haierdkdcb.txt;c401-v.jsp"; break; case (int) HN_ZYDK :
			 * strReturn = "haierrmbjkht.txt;c410-v.jsp"; break; case (int)
			 * HN_TX : strReturn = "haieryhchdhptxxy.txt;c460-v.jsp"; break;
			 * case (int) HN_TXBIZ : strReturn =
			 * "haiershychdhptxxy.txt;c470-v.jsp"; break; case (int) HN_ASSURE :
			 * strReturn = "haierbzht.txt;c430-v.jsp"; break; case (int)
			 * HN_PLEDGE : strReturn = "haierdyht.txt;c440-v.jsp"; break; case
			 * (int) HN_IMPAWN : strReturn = "haierzyht.txt;c450-v.jsp"; break; } }
			 * //����Ŀ�жϸ�Ϊ�����ļ����� haoning 2004-11-03 else
			 * if(Config.GLOBAL.getProjectType() == Constant.ProjectType.CEC) {
			 * switch ((int) lCode) { case (int) HN_ZYDK ://��Ӫ�������ͬ strReturn =
			 * "cecjkht.txt;c500-v.jsp"; break; case (int) HN_TX ://��ҵ��Ʊ���ֺ�ͬ
			 * strReturn = "cecshyhptxht.txt;c537-v.jsp"; break; case (int)
			 * HN_ASSURE ://��֤��ͬ strReturn = "cecbzht.txt;c506-v.jsp"; break;
			 * case (int) HN_PLEDGE ://��Ѻ��ͬ strReturn =
			 * "cecdyht.txt;c525-v.jsp"; break; case (int) GQIMPAWN ://��Ȩ��Ѻ��ͬ
			 * strReturn = "cecgqzhyht.txt;c510-v.jsp"; break; case (int)
			 * GFIMPAWN ://�ɷ���Ѻ��ͬ strReturn = "cecgfzhyht.txt;c515-v.jsp";
			 * break; case (int) CDIMPAWN ://�浥��Ѻ��ͬ strReturn =
			 * "ceccdzhyht.txt;c520-v.jsp"; break; case (int) HN_WTDK
			 * ://ί�д������ͬ strReturn = "cecwtjkht.txt;c534-v.jsp"; break; case
			 * (int) WTWT ://ί�д���ί�к�ͬ strReturn = "cecwtdkwtht.txt;c531-v.jsp";
			 * break; case (int) HN_ZGXE ://����޶����ͬ������Ӫ����Ľ���ͬ strReturn =
			 * "cecjkht.txt;c500-v.jsp"; break; case (int) HN_DKDCB ://��������
			 * strReturn = "cecdkdcb.txt;c101-v.jsp"; break; case (int)
			 * HN_EXTENDZY ://��Ӫչ�ں�ͬ���û��� strReturn = "cecjkzqxy.txt;c310-v.jsp";
			 * break; case (int) HN_EXTENDWT ://ί��չ�ں�ͬ���û��� strReturn =
			 * "cecwtdkzqxy.txt;c320-v.jsp"; break; case (int) HN_WTTJTH
			 * ://ί�д���ͳ��ͳ������ͬͬί�д������ strReturn = "cecwtjkht.txt;c534-v.jsp";
			 * break; } } else if(Config.GLOBAL.getProjectType() ==
			 * Constant.ProjectType.SEFC) { switch ((int) lCode) { case (int)
			 * HN_ZYDK ://��Ӫ�������ͬ strReturn = "SEFCjkht.txt;c550-v.jsp"; break;
			 * case (int) HN_ASSURE ://��֤��ͬ strReturn =
			 * "SEFCbzht.txt;c555-v.jsp"; break; case (int) HN_PLEDGE ://��Ѻ��ͬ
			 * strReturn = "SEFCdyht.txt;c566-v.jsp"; break; case (int)
			 * HN_IMPAWN : strReturn = ""; break; case (int) HN_WTDK ://ί�д������ͬ
			 * strReturn = "SEFCwtdkxy.txt;c579-v.jsp"; break; case (int)
			 * HN_WTTJTH ://ί�д���ͳ��ͳ������ͬͬί�д������ strReturn =
			 * "SEFCwtdkxy.txt;c579-v.jsp"; break; case (int) ZGEASSURE
			 * ://��߶֤��ͬ strReturn = "SEFCzgebzht.txt;c560-v.jsp"; break; case
			 * (int) ZGEPLEDGE ://��߶��Ѻ��ͬ strReturn =
			 * "SEFCzgedyht.txt;c572-v.jsp"; break; case (int) DYWQD ://��Ѻ���嵥
			 * strReturn = "SEFCdywqd.txt;c581-v.jsp"; break; case (int)
			 * HN_DKDCB ://�����������û��� strReturn = "SEFCdkdcb.txt;c101-v.jsp";
			 * break; case (int) HN_EXTENDZY ://��Ӫչ�ں�ͬ���û��� strReturn =
			 * "SEFCjkzqxy.txt;c310-v.jsp"; break; case (int) HN_EXTENDWT
			 * ://ί��չ�ں�ͬ���û��� strReturn = "SEFCwtdkzqxy.txt;c320-v.jsp"; break;
			 * case (int) HN_ZGXE ://����޶����ͬ��Ӫ���� strReturn =
			 * "SEFCjkht.txt;c550-v.jsp"; break; case (int) DBXY://����Э��
			 * strReturn = "SEFCdbxy.txt;c582-v.jsp"; break; } } //
			 */

			switch ((int) lCode) {
			case (int) HN_ZYDK:
				strReturn = "casicrmbjkht.txt;c600-v.jsp";
				break;
			case (int) HN_WTDK:
				strReturn = "casicwtjkht.txt;c534-v.jsp";
				break;
			case (int) HN_WTTJTH:
				strReturn = "tjthht.txt;c050-v.jsp";
				break;
			case (int) HN_ZGXE:
				strReturn = "casicrmbjkht.txt;c600-v.jsp";
				break;
			case (int) HN_YTDK:
				strReturn = "";
				break;
			case (int) HN_TX:
				strReturn = "casicyhchdhptxxy.txt;c460-v.jsp";// ��Haier��ģ��һ��
				break;
			case (int) HN_ASSURE:
				strReturn = "casicbzht.txt;c615-v.jsp";
				break;
			case (int) HN_PLEDGE:
				strReturn = "casicdyht.txt;c605-v.jsp";
				break;
			case (int) HN_IMPAWN:
				strReturn = "casiczyht.txt;c610-v.jsp";
				break;
			case (int) HN_EXTENDZY:
				strReturn = "casicjkzqxy.txt;c310-v.jsp";
				break;
			case (int) HN_EXTENDWT:
				strReturn = "wtdkzqxy.txt;c320-v.jsp";
				break;
			case (int) HN_DKDCB:
				strReturn = "dkdcb.txt;c101-v.jsp";
				break;
			case (int) HN_TXBIZ:
				strReturn = "casicshychdhptxxy.txt;c470-v.jsp";// ��Haier��ģ��һ��
				break;
			case (int) GQIMPAWN:// ��Ȩ��Ѻ��ͬ
				strReturn = "cecgqzhyht.txt;c510-v.jsp";
				break;
			case (int) GFIMPAWN:// �ɷ���Ѻ��ͬ
				strReturn = "cecgfzhyht.txt;c515-v.jsp";
				break;
			case (int) CDIMPAWN:// �浥��Ѻ��ͬ
				strReturn = "ceccdzhyht.txt;c520-v.jsp";
				break;
			case (int) WTWT:// ί�д���ί�к�ͬ
				strReturn = "casicwtdkwtht.txt;c531-v.jsp";
				break;
			case (int) ZGEASSURE:// ��߶֤��ͬ
				strReturn = "SEFCzgebzht.txt;c560-v.jsp";
				break;
			case (int) ZGEPLEDGE:// ��߶��Ѻ��ͬ
				strReturn = "SEFCzgedyht.txt;c572-v.jsp";
				break;
			case (int) DYWQD:// ��Ѻ���嵥
				strReturn = "SEFCdywqd.txt;c581-v.jsp";
				break;
			case (int) DBXY:// ����Э��
				strReturn = "casicdbxy.txt;c582-v.jsp";
				break;
			// �Ϻ��ַ�
			case (int) SHPF_ZYDK:// ��Ӫ�����ͬ
				strReturn = "shpfrmbjkht.txt;c620-v.jsp";
				break;
			case (int) SHPF_WTDK:// ί�д����ͬ
				strReturn = "shpfwtdkwt.txt;c626-v.jsp";
				break;
			case (int) SHPF_ASSURE:// ��֤��ͬ
				strReturn = "shpfbzht.txt;c630-v.jsp";
				break;
			case (int) SHPF_IMPAWN:// ��Ѻ��ͬ
				strReturn = "shpfdyht.txt;c634-v.jsp";
				break;
			case (int) SHPF_ZGEASSURE:// ��߶֤��ͬ
				strReturn = "shpfzgebzht.txt;c638-v.jsp";
				break;
			case (int) SHPF_ZGEPLEDGE:// ��߶��Ѻ��ͬ
				strReturn = "shpfzgedyht.txt;c642-v.jsp";
				break;
			case (int) SHPF_KLGNBHXY:// �������ڱ���Э��
				strReturn = "shpfklgnbhxy.txt;c646-v.jsp";
				break;
			case (int) SHPF_RZTenancy:// �������޺�ͬ
				strReturn = "shpfrzzlht.txt;c650-v.jsp";
				break;
			}

			/* TOCONFIG��END */

			return strReturn;
		}

	}

	// ����ҵ����������
	public static class AtTermAwakeType {
		// ����ҵ����������
		public static final long ZYDQ = 1; // ��Ӫ���ڴ���

		public static final long ZYZCQ = 2; // ��Ӫ�г��ڴ���

		public static final long WT = 3; // ί�д���

		public static final long WTTJTH = 4; // ί�д��ͳ��ͳ��

		// public static final long TX = 5; //����
		public static final long ZGXEDQ = 6; // ����޶����

		public static final long ZGXEZCQ = 7; // ����޶��г���

		public static final long YTDQ = 8; // ���Ŷ��ڴ���

		public static final long YTZCQ = 10; // �����г��ڴ���

		// public static final long ZTX = 11; //ת����
		public static final long DKLVTZ = 20; // �������ʵ���

		public static final long EXCEED = 21; // ��������
		
		public static final long WTCOMMISSION = 30; //ί�д�����������ȡ����

		public static final String getName(long lCode) {
			String strReturn = ""; // ��ʼ������ֵ
			switch ((int) lCode) {
			case (int) ZYDQ:
				strReturn = "��Ӫ���ڴ���";
				break;
			case (int) ZYZCQ:
				strReturn = "��Ӫ�г��ڴ���";
				break;
			case (int) WT:
				strReturn = "ί�д���";
				break;
			case (int) WTTJTH:
				strReturn = "ί�д���-ͳ��ͳ��";
				break;
			case (int) ZGXEDQ:
				strReturn = "����޶����";
				break;
			case (int) ZGXEZCQ:
				strReturn = "����޶��г���";
				break;
			case (int) YTDQ:
				strReturn = "���Ŷ��ڴ���";
			case (int) YTZCQ:
				strReturn = "�����г��ڴ���";
				break;
			case (int) DKLVTZ:
				strReturn = "�������ʵ���";
				break;
			case (int) EXCEED:
				strReturn = "��������";
				break;
			case (int) WTCOMMISSION:
				strReturn = "ί�д�����������ȡ����";
			}
			return strReturn;
		}

		public static final long getCount() {
			return 10;
		}
	}

	// �޸ļƻ��ļ���ԭ��
	public static class WhoChangePlan {
		public static final long LOANAPPLY = 1; // ��������

		public static final long PLANMODIFY = 2; // ִ�мƻ��޸�

		public static final long EXTENDAPPLY = 3; // չ������

		public static final long OVERDUEAPPLY = 4; // ��������
	}

	// ����ſʽ
	public static class PayType {
		public static final long DAY = 1; // ���շſ�

		public static final long WEEK = 2; // ���ܷſ�

		public static final long MONTH = 3; // ���·ſ�

		public static final long QUARTOR = 4; // �����ſ�

		public static final long HALFYEAR = 5; // ������ſ�

		public static final long YEAR = 6; // ����ſ�

		public static final long ONETIME = 7; // һ�ηſ�

		public static final long NOTUSE = 8; // ������

		public static final String getName(long lCode) {
			String strReturn = ""; // ��ʼ������ֵ
			switch ((int) lCode) {
			case (int) DAY:
				strReturn = "���շſ�";
				break;
			case (int) WEEK:
				strReturn = "���ܷſ�";
				break;
			case (int) MONTH:
				strReturn = "���·ſ�";
				break;
			case (int) QUARTOR:
				strReturn = "�����ſ�";
				break;
			case (int) HALFYEAR:
				strReturn = "������ſ�";
				break;
			case (int) YEAR:
				strReturn = "����ſ�";
				break;
			case (int) ONETIME:
				strReturn = "һ�ηſ�";
				break;
			case (int) NOTUSE:
				strReturn = "������";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { DAY, WEEK, MONTH, QUARTOR, HALFYEAR, YEAR,
					ONETIME, NOTUSE };
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant.getAllCode(
					"com.iss.itreasury.loan.util.LOANConstant$PayType",
					officeID, currencyID);
		}

		public static final long getCount() {
			return 8;
		}

		/**
		 * ��ʾ�����б�
		 * 
		 * @param out
		 * @param strControlName,
		 *            �ؼ�����
		 * @param nType���ؼ����ͣ�0����ʾȫ������
		 * @param lSelectValue
		 * @param isNeedAll���Ƿ���Ҫ��ȫ���
		 * @param isNeedBlank
		 *            �Ƿ���Ҫ�հ���
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode();
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				System.out.println(ex.toString());
			}
		}

		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty, long lOfficeID,
				long lCurrencyID) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode(lOfficeID, lCurrencyID);
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				System.out.println(ex.toString());
			}
		}

	}

	// ����ʽ
	public static class RepayType {
		public static final long DAY = 1; // ���ջ���

		public static final long WEEK = 2; // ���ܻ���

		public static final long MONTH = 3; // ���»���

		public static final long QUARTOR = 4; // ��������

		public static final long HALFYEAR = 5; // �����껹��

		public static final long YEAR = 6; // ���껹��

		public static final long ONETIME = 7; // һ�λ���

		public static final long NOTUSE = 8; // ������

		public static final String getName(long lCode) {
			String strReturn = ""; // ��ʼ������ֵ
			switch ((int) lCode) {
			case (int) DAY:
				strReturn = "���ջ���";
				break;
			case (int) WEEK:
				strReturn = "���ܻ���";
				break;
			case (int) MONTH:
				strReturn = "���»���";
				break;
			case (int) QUARTOR:
				strReturn = "��������";
				break;
			case (int) HALFYEAR:
				strReturn = "�����껹��";
				break;
			case (int) YEAR:
				strReturn = "���껹��";
				break;
			case (int) ONETIME:
				strReturn = "һ���廹";
				break;
			case (int) NOTUSE:
				strReturn = "������";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { DAY, WEEK, MONTH, QUARTOR, HALFYEAR, YEAR,
					ONETIME, NOTUSE };
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant.getAllCode(
					"com.iss.itreasury.loan.util.LOANConstant$RepayType",
					officeID, currencyID);
		}

		public static final long getCount() {
			return 8;
		}

		/**
		 * ��ʾ�����б�
		 * 
		 * @param out
		 * @param strControlName,
		 *            �ؼ�����
		 * @param nType���ؼ����ͣ�0����ʾȫ������
		 * @param lSelectValue
		 * @param isNeedAll���Ƿ���Ҫ��ȫ���
		 * @param isNeedBlank
		 *            �Ƿ���Ҫ�հ���
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode();
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				System.out.println(ex.toString());
			}
		}

		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty, long lOfficeID,
				long lCurrencyID) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode(lOfficeID, lCurrencyID);
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				System.out.println(ex.toString());
			}
		}
	}

	// �ƻ�����
	public static class PlanType {
		// �ƻ�����
		public static final long PAY = 1; // �ſ�

		public static final long REPAY = 2; // ����

		public static final String getName(long lCode) {
			String strReturn = ""; // ��ʼ������ֵ
			switch ((int) lCode) {
			case (int) PAY:
				strReturn = "�ſ�";
				break;
			case (int) REPAY:
				strReturn = "����";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { PAY, REPAY };
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant.getAllCode(
					"com.iss.itreasury.loan.util.LOANConstant$PlanType",
					officeID, currencyID);
		}

		/**
		 * ��ʾ�����б�
		 * 
		 * @param out
		 * @param strControlName,
		 *            �ؼ�����
		 * @param nType���ؼ����ͣ�0����ʾȫ������
		 * @param lSelectValue
		 * @param isNeedAll���Ƿ���Ҫ��ȫ���
		 * @param isNeedBlank
		 *            �Ƿ���Ҫ�հ���
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode();
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				System.out.println(ex.toString());
			}
		}

		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty, long lOfficeID,
				long lCurrencyID) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode(lOfficeID, lCurrencyID);
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				System.out.println(ex.toString());
			}
		}

	}

	// ����������
	public static class AreaType {
//		public static final long CLOSE = 1; // ��մ���
//
//		public static final long POLICY = 2; // �����Դ���
//
//		public static final long SPECIAL = 3; // ר�����
//
//		public static final long GENERAL = 4; // һ����ҵ����
//
//		public static final long CONSIGN = 5;// ί�д���
//
//		public static final long DISCOUNT = 6;// ����
//
//		public static final long LIMITLOAN = 7;// ����޶����
//
//		public static final long ASSURE = 8;// ����
//
//		public static final long BANK_ZXSY_NJFH = 9;// ����ʵҵ�����Ͼ�����
//
//		public static final long BANK_ZGJS_HZFH = 10;// �й��������к�����֧������
//
//		public static final long BANK_ZSGF = 11;// �������йɷ����޹�˾
//
//		public static final long BANK_SZFZ_HZQT = 12;// ���ڷ�չ���к�����̩֧��
//
//		public static final long[] getAllCode() {
//
//			long[] lTemp = { CLOSE, POLICY, SPECIAL, GENERAL, CONSIGN,
//					DISCOUNT, LIMITLOAN, ASSURE, BANK_ZXSY_NJFH,
//					BANK_ZGJS_HZFH, BANK_ZSGF, BANK_SZFZ_HZQT };
//			return lTemp;
//		}

		public static final long[] getAllCode() {
			Collection coll = new ArrayList();
			long assortId = LOANConstant.AssortSetType.AREA;
			LoanAssortSettingDelegation delegation = new LoanAssortSettingDelegation();
			coll = delegation.getAssortNameAndIDByAssortId(assortId);
			Iterator iterator = coll.iterator();
			long[] lTemp = new long[coll.size()];
			int i = 0;
			while(iterator.hasNext()){
				LoanAssortSettingInfo info = (LoanAssortSettingInfo)iterator.next();
				lTemp[i] = info.getId();
				i++;
			}
			return lTemp;
		}
		
		public static final long[] getOfficeIdAndCurrencyIdAllCode(long officeId, long currencyId, long typeId) {
			Collection coll = new ArrayList();
			//long assortId = LOANConstant.AssortSetType.AREA;
			LoanAssortSettingDelegation delegation = new LoanAssortSettingDelegation();
			coll = delegation.getAssortNameAndIDByAssort(officeId, currencyId, typeId);
			Iterator iterator = coll.iterator();
			long[] lTemp = new long[coll.size()];
			int i = 0;
			while(iterator.hasNext()){
				LoanAssortSettingInfo info = (LoanAssortSettingInfo)iterator.next();
				lTemp[i] = info.getId();
				i++;
			}
			return lTemp;
		}
		
		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant.getAllCode(
					"com.iss.itreasury.loan.util.LOANConstant$AreaType",
					officeID, currencyID);
		}
		
		public static final String getName(long lCode) {
			String strReturn = ""; // ��ʼ������ֵ
			LoanAssortSettingDelegation delegation = new LoanAssortSettingDelegation();
			strReturn = delegation.getAssortNameByID(lCode);
			return strReturn;
		}
		
//		public static final String getName(long lCode) {
//			String strReturn = ""; // ��ʼ������ֵ
//			switch ((int) lCode) {
//			case (int) CLOSE:
//				strReturn = "��մ���";
//				break;
//			case (int) POLICY:
//				strReturn = "�����Դ���";
//				break;
//			case (int) SPECIAL:
//				strReturn = "ר�����";
//				break;
//			case (int) GENERAL:
//				strReturn = "һ����ҵ����";
//				break;
//			case (int) CONSIGN:
//				strReturn = "ί�д���";
//				break;
//			case (int) DISCOUNT:
//				strReturn = "����";
//				break;
//			case (int) LIMITLOAN:
//				strReturn = "����޶����";
//				break;
//			case (int) ASSURE:
//				strReturn = "����";
//				break;
//			case (int) BANK_ZXSY_NJFH:
//				strReturn = "����ʵҵ�����Ͼ�����";
//				break;
//			case (int) BANK_ZGJS_HZFH:
//				strReturn = "�й��������к�����֧������";
//				break;
//			case (int) BANK_ZSGF:
//				strReturn = "�������йɷ����޹�˾";
//				break;
//			case (int) BANK_SZFZ_HZQT:
//				strReturn = "���ڷ�չ���к�����̩֧��";
//				break;
//			}
//			return strReturn;
//		}
	}

	// ����ҵ����1
	public static class IndustryType1 {
		// ��ƷMILITARY/���ز�ESTATE/���ֳ�SPECIALCAR/���������CARSPARE
		// ͨ��COMMUNICATION/���ֲ���SPECIALMATERIAL/����OTHER
//		public static final long MILITARY = 1; // ��Ʒ
//
//		public static final long ESTATE = 2; // ���ز�
//
//		public static final long SPECIALCAR = 3; // ���ֳ�
//
//		public static final long CARSPARE = 4; // ���������
//
//		public static final long COMMUNICATION = 5; // ͨ��
//
//		public static final long SPECIALMATERIAL = 6; // ���ֲ���
//
//		public static final long OTHER = 7; // ����
//
//		// nhcw(2005-11-21) add by kenny
//		public static final long CONTENTFLOW = 8;// ����
//
//		public static final long PLANESERVICE = 9;// �ɻ�ά��
//
//		public static final long NAVIGATETRANSPORT = 10;// �������䡢���ջ���
//
//		public static final long JUNKETING = 11;// ����

		// public static final long ESTATE = 2; //���ز�
		// public static final long OTHER = 7; //����
		public static final String getName(long lCode) {
			String strReturn = ""; // ��ʼ������ֵ
			LoanAssortSettingDelegation delegation = new LoanAssortSettingDelegation();
			strReturn = delegation.getAssortNameByID(lCode);
			return strReturn;
		}
//		public static final String getName(long lCode) {
//			String strReturn = ""; // ��ʼ������ֵ
//			switch ((int) lCode) {
//			case (int) MILITARY:
//				strReturn = "��Ʒ";
//				break;
//			case (int) ESTATE:
//				strReturn = "���ز�";
//				break;
//			case (int) SPECIALCAR:
//				strReturn = "���ֳ�";
//				break;
//			case (int) CARSPARE:
//				strReturn = "���������";
//				break;
//			case (int) COMMUNICATION:
//				strReturn = "ͨ��";
//				break;
//			case (int) SPECIALMATERIAL:
//				strReturn = "���ֲ���";
//				break;
//			case (int) OTHER:
//				strReturn = "����";
//				break;
//			case (int) CONTENTFLOW:
//				strReturn = "����";
//				break;
//			case (int) PLANESERVICE:
//				strReturn = "�ɻ�ά��";
//				break;
//			case (int) NAVIGATETRANSPORT:
//				strReturn = "��������";
//				break;
//			case (int) JUNKETING:
//				strReturn = "����";
//				break;
//			}
//			return strReturn;
//		}

		public static final long[] getAllCode() {
			Collection coll = new ArrayList();
			long assortId = LOANConstant.AssortSetType.INDUSTRY1;
			LoanAssortSettingDelegation delegation = new LoanAssortSettingDelegation();
			coll = delegation.getAssortNameAndIDByAssortId(assortId);
			Iterator iterator = coll.iterator();
			long[] lTemp = new long[coll.size()];
			int i = 0;
			while(iterator.hasNext()){
				LoanAssortSettingInfo info = (LoanAssortSettingInfo)iterator.next();
				lTemp[i] = info.getId();
				i++;
			}
			return lTemp;
		}
		
//		public static final long[] getAllCode() {
//			long[] lReturn = null;
//			long[] lTemp = { MILITARY, ESTATE, SPECIALCAR, CARSPARE,
//					COMMUNICATION, SPECIALMATERIAL, OTHER, CONTENTFLOW,
//					PLANESERVICE, NAVIGATETRANSPORT, JUNKETING };
//			lReturn = lTemp;
//			return lReturn;
//		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant.getAllCode(
					"com.iss.itreasury.loan.util.LOANConstant$IndustryType1",
					officeID, currencyID);
		}

		/**
		 * ��ʾ�����б�
		 * 
		 * @param out
		 * @param strControlName,
		 *            �ؼ�����
		 * @param nType���ؼ����ͣ�0����ʾȫ������
		 * @param lSelectValue
		 * @param isNeedAll���Ƿ���Ҫ��ȫ���
		 * @param isNeedBlank
		 *            �Ƿ���Ҫ�հ���
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode();
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				System.out.println(ex.toString());
			}
		}

		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty, long lOfficeID,
				long lCurrencyID) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode(lOfficeID, lCurrencyID);
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				System.out.println(ex.toString());
			}
		}
	}

	// ����ҵ����2
	public static class IndustryType2 {
		// ƽ̨PLATFORM/����LOCAL
		// ���ߵ�λONLINE/�����ߵ�λUNONLINE
//		public static final long PLATFORM = 1; // ƽ̨
//
//		public static final long LOCAL = 2; // ����
//
//		//public static final long ONLINE = 3; //���ߵ�λ
//		//public static final long UNONLINE = 4; //�����ߵ�λ
//
//		// nhcw(2005-11-21) add by kenny
//		public static final long STOCKCOMPANY = 5; // �ɷݿعɹ�˾
//
//		public static final long GROUPCOMPANY = 6; // ���ſعɹ�˾
//
//		public static final long HOLDINGCOMPANY = 7; // �عɹ�˾
//
//		public static final long ASSISTANTTRADE = 8; // ��ҵ���
//
//		public static final long SUBSIDIARY = 9; // �ӹ�˾

		public static final String getName(long lCode) {
			String strReturn = ""; // ��ʼ������ֵ
			LoanAssortSettingDelegation delegation = new LoanAssortSettingDelegation();
			strReturn = delegation.getAssortNameByID(lCode);
			return strReturn;
		}

		public static final long[] getAllCode() {
			Collection coll = new ArrayList();
			long assortId = LOANConstant.AssortSetType.INDUSTRY2;
			LoanAssortSettingDelegation delegation = new LoanAssortSettingDelegation();
			coll = delegation.getAssortNameAndIDByAssortId(assortId);
			Iterator iterator = coll.iterator();
			long[] lTemp = new long[coll.size()];
			int i = 0;
			while(iterator.hasNext()){
				LoanAssortSettingInfo info = (LoanAssortSettingInfo)iterator.next();
				lTemp[i] = info.getId();
				i++;
			}
			return lTemp;
		}
		
//		public static final long[] getAllCode() {
//			long[] lReturn = null;
//			long[] lTemp = { PLATFORM, LOCAL, STOCKCOMPANY, GROUPCOMPANY,
//					HOLDINGCOMPANY, ASSISTANTTRADE, SUBSIDIARY };
//			lReturn = lTemp;
//
//			return lReturn;
//		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant.getAllCode(
					"com.iss.itreasury.loan.util.LOANConstant$IndustryType2",
					officeID, currencyID);
		}

//		public static final String getName(long lCode) {
//			String strReturn = ""; // ��ʼ������ֵ
//			switch ((int) lCode) {
//			case (int) PLATFORM:
//				strReturn = "ƽ̨";
//				break;
//			case (int) LOCAL:
//				strReturn = "����";
//				break;
//			case (int) STOCKCOMPANY:
//				strReturn = "�ɷݿعɹ�˾";
//				break;
//			case (int) GROUPCOMPANY:
//				strReturn = "���ſعɹ�˾";
//				break;
//			case (int) HOLDINGCOMPANY:
//				strReturn = "�عɹ�˾";
//				break;
//			case (int) ASSISTANTTRADE:
//				strReturn = "��ҵ���";
//				break;
//			case (int) SUBSIDIARY:
//				strReturn = "�ӹ�˾";
//				break;
//			}
//			return strReturn;
//		}

		/**
		 * ��ʾ�����б�
		 * 
		 * @param out
		 * @param strControlName,
		 *            �ؼ�����
		 * @param nType���ؼ����ͣ�0����ʾȫ������
		 * @param lSelectValue
		 * @param isNeedAll���Ƿ���Ҫ��ȫ���
		 * @param isNeedBlank
		 *            �Ƿ���Ҫ�հ���
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode();
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				System.out.println(ex.toString());
			}
		}

		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty, long lOfficeID,
				long lCurrencyID) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode(lOfficeID, lCurrencyID);
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				System.out.println(ex.toString());
			}
		}

	}

	// ����ҵ����3
	public static class IndustryType3 {
//		public static final long CONTROLSTOCK = 1; // �ع�
//
//		public static final long SHARESTOCK = 2; // �ι�
		
		public static final String getName(long lCode) {
			String strReturn = ""; // ��ʼ������ֵ
			LoanAssortSettingDelegation delegation = new LoanAssortSettingDelegation();
			strReturn = delegation.getAssortNameByID(lCode);
			return strReturn;
		}

		public static final long[] getAllCode() {
			Collection coll = new ArrayList();
			long assortId = LOANConstant.AssortSetType.INDUSTRY3;
			LoanAssortSettingDelegation delegation = new LoanAssortSettingDelegation();
			coll = delegation.getAssortNameAndIDByAssortId(assortId);
			Iterator iterator = coll.iterator();
			long[] lTemp = new long[coll.size()];
			int i = 0;
			while(iterator.hasNext()){
				LoanAssortSettingInfo info = (LoanAssortSettingInfo)iterator.next();
				lTemp[i] = info.getId();
				i++;
			}
			return lTemp;
		}
//		public static final long[] getAllCode() {
//			long[] lTemp = { CONTROLSTOCK, SHARESTOCK };
//			return lTemp;
//		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant.getAllCode(
					"com.iss.itreasury.loan.util.LOANConstant$IndustryType3",
					officeID, currencyID);
		}

//		public static final String getName(long lCode) {
//			String strReturn = ""; // ��ʼ������ֵ
//			switch ((int) lCode) {
//			case (int) CONTROLSTOCK:
//				strReturn = "�ع�";
//				break;
//			case (int) SHARESTOCK:
//				strReturn = "�ι�";
//				break;
//			}
//			return strReturn;
//		}

		/**
		 * ��ʾ�����б�
		 * 
		 * @param out
		 * @param strControlName,
		 *            �ؼ�����
		 * @param nType���ؼ����ͣ�0����ʾȫ������
		 * @param lSelectValue
		 * @param isNeedAll���Ƿ���Ҫ��ȫ���
		 * @param isNeedBlank
		 *            �Ƿ���Ҫ�հ���
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode();
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				System.out.println(ex.toString());
			}
		}

		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty, long lOfficeID,
				long lCurrencyID) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode(lOfficeID, lCurrencyID);
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				System.out.println(ex.toString());
			}
		}
	}

	// �Ƿ�ǣͷ��
	public static class IsHead {
		// �Ƿ�ǣͷ��
		public static final long YES = 1; // ��

		public static final long NO = 0; // ����
	}

	/* ��������ȡ����ʽ */
	public static class ChargeRatePayType {
		public static final long ONETIME = 1;

		public static final long YEAR = 2;

		public static final long QUARTER = 3;

		public static final long HALFYEAR = 4;

		public static final long MONTH = 5;

		public static final String getName(long lCode) {
			String strReturn = ""; // ��ʼ������ֵ
			switch ((int) lCode) {
			case (int) ONETIME:
				strReturn = "һ����";
				break;
			case (int) YEAR:
				strReturn = "����";
				break;
			case (int) HALFYEAR:
				strReturn = "������";
				break;
			case (int) QUARTER:
				strReturn = "����";
				break;
			case (int) MONTH:
				strReturn = "����";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { ONETIME, YEAR, HALFYEAR, QUARTER, MONTH };
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant
					.getAllCode(
							"com.iss.itreasury.loan.util.LOANConstant$ChargeRatePayType",
							officeID, currencyID);
		}

		/**
		 * ��ʾ�����б�
		 * 
		 * @param out
		 * @param strControlName,
		 *            �ؼ�����
		 * @param nType���ؼ����ͣ�0����ʾȫ������
		 * @param lSelectValue
		 * @param isNeedAll���Ƿ���Ҫ��ȫ���
		 * @param isNeedBlank
		 *            �Ƿ���Ҫ�հ���
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode();
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				System.out.println(ex.toString());
			}
		}

		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty, long lOfficeID,
				long lCurrencyID) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode(lOfficeID, lCurrencyID);
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				System.out.println(ex.toString());
			}
		}
	}

	// �ſ�֪ͨ���ſʽ
	public static class TransType {
		// �ſ�֪ͨ���ſʽ
		public static final long Bank = 1; // ���и���

		public static final long CurrentAccount = 2; // �����˻�
	}

	public static class AttornmentStatus {
		// ����ת������״̬
		public static final long SAVE = 1; // �ѱ���

		public static final long SUBMIT = 2; // ���ύ

		public static final long CHECK = 3; // �����

		public static final long REFUSE = 4; // �Ѿܾ�

		public static final long USED = 5; // ��ʹ��

		public static final long CANCEL = 6; // �Ѿ�ȡ��
		
		public static final long REJECTED = 7; //�Ѿܾ�
		
		public static final long APPROVALING = 8;//�����		

		public static final String getName(long lCode) {
			String strReturn = ""; // ��ʼ������ֵ
			switch ((int) lCode) {
			case (int) SAVE:
				//modified by mzh_fu 2007/07/05
				//strReturn = "׫д";
				strReturn = "�ѱ���";
				break;
			case (int) SUBMIT:
				strReturn = "�ѱ���";
				break;
			case (int) CHECK:
				strReturn = "������";
				break;
			case (int) REFUSE:
				strReturn = "�Ѿܾ�";
				break;
			case (int) USED:
				strReturn = "��ʹ��";
				break;
			case (int) CANCEL:
				strReturn = "��ȡ��";
				break;
			case (int) REJECTED :
				strReturn = "�Ѿܾ�";
				break;
			case (int) APPROVALING :	
				strReturn = "������";
			    break;				
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { SAVE, SUBMIT, CHECK, REFUSE, USED, CANCEL, REJECTED, APPROVALING };

			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant
					.getAllCode(
							"com.iss.itreasury.loan.util.LOANConstant$AttornmentStatus",
							officeID, currencyID);
		}

		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode();
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				System.out.println(ex.toString());
			}
		}

		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty, long lOfficeID,
				long lCurrencyID) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode(lOfficeID, lCurrencyID);
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				System.out.println(ex.toString());
			}
		}
	}

	public static class CheckDiscountBillStatus {
		// ��ҵ����
		public static final long WSC = 1; // δ���

		public static final long ZZSC = 2; // �������

		public static final long SCYX = 3; // �����Ч

		public static final long SCWX = 4; // �����Ч

	}

	/**
	 * 
	 * ҳ���������
	 * 
	 * @author rongyang
	 */
	public static class Actions {
		public static final int CREATETEMPSAVE = 1; // ������ʱ����

		public static final int MODIFYTEMPSAVE = 2; // �޸���ʱ����

		public static final int CREATESAVE = 3; // ��������

		public static final int MODIFYSAVE = 4; // �޸ı���

		public static final int DELETE = 5; // ɾ��

		public static final int LINKSEARCH = 6; // ���Ӳ���

		public static final int MATCHSEARCH = 7; // ƥ�����

		public static final int CHECK = 8; // ����/���

		public static final int CANCELCHECK = 9; // ȡ������

		public static final int NEXTSTEP = 10; // ��һ��

		public static final int TODETAIL = 11; // �㽻�׺ŵ���ϸҳ��

		public static final int MODIFYNEXTSTEP = 12; // ������һ��

		public static final int REJECT = 13; // �ܾ�

		public static final int RETURN = 14; // �����޸�

		public static final int CHECKOVER = 15; // ������

		public static final int MASSCHECK = 16; // ��������

		public static final int MASSCANCELCHECK = 17; // ����ȡ������

		public static final int UPDATESEARCH = 18; // �޸Ĳ���

		public static final int CHECKSEARCH = 19; // ��˲���

		public static final int COMMIT = 20; // �ύ
		public static final int QUERY = 24;

		public static final String getName(long lCode) {
			String strReturn = "";
			switch ((int) lCode) {
			case CREATETEMPSAVE:
				strReturn = "��ʱ����";
				break;
			case MODIFYTEMPSAVE:
				strReturn = "��ʱ����";
				break;
			case CREATESAVE:
				strReturn = "��������";
				break;
			case MODIFYSAVE:
				strReturn = "�޸ı���";
				break;
			case DELETE:
				strReturn = "ɾ��";
				break;
			case LINKSEARCH:
				strReturn = "���Ӳ���";
				break;
			case MATCHSEARCH:
				strReturn = "ƥ�����";
				break;
			case CHECK:
				strReturn = "����";
				break;
			case CANCELCHECK:
				strReturn = "ȡ������";
				break;
			case NEXTSTEP:
				strReturn = "��һ��";
				break;
			case TODETAIL:
				strReturn = "�쿴��ϸ";
				break;
			case REJECT:
				strReturn = "�ܾ�";
				break;
			case RETURN:
				strReturn = "�����޸�";
				break;
			case CHECKOVER:
				strReturn = "������";
				break;
			case MASSCHECK:
				strReturn = "��������";
				break;
			case MASSCANCELCHECK:
				strReturn = "����ȡ������";
				break;
			case UPDATESEARCH:
				strReturn = "�޸Ĳ���";
				break;
			case CHECKSEARCH:
				strReturn = "��˲���";
				break;
			case COMMIT:
				strReturn = "�ύ";
			case QUERY:
				strReturn = "��ѯ";

			}
			return strReturn;
		}
	}

	/**
	 * ת�������� ���롢����
	 * 
	 * @author haoning
	 */
	public static class TransDiscountInOrOut {
		public static final long IN = 1; // ����

		public static final long OUT = 2; // ����

		public static final String getName(long lCode) {
			String strReturn = ""; // ��ʼ������ֵ
			switch ((int) lCode) {
			case (int) IN:
				strReturn = "����";
				break;
			case (int) OUT:
				strReturn = "����";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { IN, OUT };
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant
					.getAllCode(
							"com.iss.itreasury.loan.util.LOANConstant$TransDiscountInOrOut",
							officeID, currencyID);
		}

		/**
		 * ��ʾ�����б�
		 * 
		 * @param out
		 * @param strControlName,
		 *            �ؼ�����
		 * @param nType���ؼ����ͣ�0����ʾȫ������
		 * @param lSelectValue
		 * @param isNeedAll���Ƿ���Ҫ��ȫ���
		 * @param isNeedBlank
		 *            �Ƿ���Ҫ�հ���
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode();
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				System.out.println(ex.toString());
			}
		}

		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty, long lOfficeID,
				long lCurrencyID) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode(lOfficeID, lCurrencyID);
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				System.out.println(ex.toString());
			}
		}
	}

	/**
	 * ת�������� ���ʽ���ع�ʽ
	 * 
	 * @author haoning
	 */
	public static class TransDiscountType {
		public static final long BUYBREAK = 1; // ���ʽ

		public static final long REPURCHASE = 2; // �ع�ʽ

		public static final String getName(long lCode) {
			String strReturn = ""; // ��ʼ������ֵ
			switch ((int) lCode) {
			case (int) BUYBREAK:
				strReturn = "���ʽ";
				break;
			case (int) REPURCHASE:
				strReturn = "�ع�ʽ";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { BUYBREAK, REPURCHASE };
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant
					.getAllCode(
							"com.iss.itreasury.loan.util.LOANConstant$TransDiscountType",
							officeID, currencyID);
		}

		/**
		 * ��ʾ�����б�
		 * 
		 * @param out
		 * @param strControlName,
		 *            �ؼ�����
		 * @param nType���ؼ����ͣ�0����ʾȫ������
		 * @param lSelectValue
		 * @param isNeedAll���Ƿ���Ҫ��ȫ���
		 * @param isNeedBlank
		 *            �Ƿ���Ҫ�հ���
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode();
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				System.out.println(ex.toString());
			}
		}

		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty, long lOfficeID,
				long lCurrencyID) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode(lOfficeID, lCurrencyID);
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				System.out.println(ex.toString());
			}
		}
	}
	
	/**
	 * �������
	 * 
	 * @author haoning
	 */
	public static class ChangeOper {
		public static final long AAMOUNT = 1; // ��������Ϣ
		public static final long ACHANGE = 2; // ��������Ϣ

		public static final String getName(long lCode) {
			String strReturn = ""; // ��ʼ������ֵ
			switch ((int) lCode) {
			case (int) AAMOUNT:
				strReturn = "���Ŷ����Ϣ";
				break;
			case (int) ACHANGE:
				strReturn = "���ű����Ϣ";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { AAMOUNT, ACHANGE };
			return lTemp;
		}
	}

	/**
	 * �ع���ʽ һ���Իع����ִλع�
	 * 
	 * @author haoning
	 */
	public static class TransRepurchaseType {
		public static final long ONCE = 1; // һ���Իع�

		public static final long MULTI_TIME = 2; // �ִλع�

		public static final String getName(long lCode) {
			String strReturn = ""; // ��ʼ������ֵ
			switch ((int) lCode) {
			case (int) ONCE:
				strReturn = "һ���Իع�";
				break;
			case (int) MULTI_TIME:
				strReturn = "�ִλع�";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { -1, ONCE, MULTI_TIME };
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant
					.getAllCode(
							"com.iss.itreasury.loan.util.LOANConstant$TransRepurchaseType",
							officeID, currencyID);
		}

		/**
		 * ��ʾ�����б�
		 * 
		 * @param out
		 * @param strControlName,
		 *            �ؼ�����
		 * @param nType���ؼ����ͣ�0����ʾȫ������
		 * @param lSelectValue
		 * @param isNeedAll���Ƿ���Ҫ��ȫ���
		 * @param isNeedBlank
		 *            �Ƿ���Ҫ�հ���
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode();
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				System.out.println(ex.toString());
			}
		}

		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty, long lOfficeID,
				long lCurrencyID) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode(lOfficeID, lCurrencyID);
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				System.out.println(ex.toString());
			}
		}
	}

	// ƾ֤����
	public static class CredenceType {
		// ƾ֤����
		public static final long DISCOUNTCREDENCE = 1; // "����ƾ֤";

		public static final long TRANSDISCOUNTCREDENCE = 2; // "ת����ƾ֤";

		public static final long REPURCHASECREDENCE = 3; // "�ع�ƾ֤";

		public static final String getName(long lCode) {
			String strReturn = ""; // ��ʼ������ֵ
			switch ((int) lCode) {
			case (int) DISCOUNTCREDENCE:
				strReturn = "����ƾ֤";
				break;
			case (int) TRANSDISCOUNTCREDENCE:
				strReturn = "ת����ƾ֤";
				break;
			case (int) REPURCHASECREDENCE:
				strReturn = "�ع�ƾ֤";
				break;
			}
			return strReturn;
		}
	}

	// Ʊ����Դ
	public static class BillSourceType {
		// ƾ֤����
		public static final long DISCOUN = 1; // "����";

		public static final long PACHASETRANSDISCOUNT = 2; // "ת��������";

		public static final String getName(long lCode) {
			String strReturn = ""; // ��ʼ������ֵ
			switch ((int) lCode) {
			case (int) DISCOUN:
				strReturn = "����";
				break;
			case (int) PACHASETRANSDISCOUNT:
				strReturn = "ת��������";
				break;
			}
			return strReturn;
		}
	}
	
	public static class CreditRelationType {
		
		public static final long SIMPLE = 1;  //�������Ź�ϵ
		
		public static final long MULTILEVEL = 2; //�༶���Ź�ϵ
		
	}

	
	/**
	 * ���Ŷ��״̬
	 * @author Administrator
	 *
	 */
	public static class CreditStatus {
		
		public static final long SAVE = 1;     //�ѱ���
		
 		public static final long CHECK = 3;    //������
		
		public static final long APPROVALING = 20; //������
		
		
		
		public static final long DUFAULT = -1;  //Ĭ��ֵ
		
		public static final long ACTIVE = 6;   //�Ѽ���

		public static final long FREEZE = 7;   //�Ѷ���
		
		public static final long DELETE = 0;  //��ɾ��
		
		
		
		public static final long OVERTIME = -20;   //�ѹ��ڣ����ã�
		
		public static final long SUBMIT = -10; //���ύ�����ã�
		
		//public static final long REFUSE = 4;   //�Ѿܾ�
		
		//public static final long CANCEL = 5;   //��ȡ��
		
		public static final String getName(long lCode) {
			String strReturn = ""; // ��ʼ������ֵ
			switch ((int) lCode) {
			case (int) SAVE:
			    strReturn = "�ѱ���";
			    break;
			case (int) CHECK:
				strReturn = "������";
				break;
			case (int) ACTIVE:
				strReturn = "�Ѽ���";
				break;
			case (int) FREEZE:
				strReturn = "�Ѷ���";
				break;
			//case (int) CANCEL:
			//	strReturn = "��ȡ��";
			//	break;
			case (int) DELETE:
				strReturn = "��ɾ��";
				break;
			//case (int) OVERTIME:
			//	strReturn = "�ѹ���";
			//	break;
			//case (int) SUBMIT:
			//    strReturn = "���ύ";
			//    break;
			//case (int) REFUSE:
			//    strReturn = "�Ѿܾ�";
			//    break;
			case (int) APPROVALING:
			    strReturn = "������";
			    break;
			}
			return strReturn;
		}
		
		/**
		 * ��ʾ�����б�
		 * 
		 * @param out
		 * @param strControlName     �ؼ�����
		 * @param nType���ؼ����ͣ�0����ʾȫ������
		 * @param lSelectValue �Ƿ�ѡ�пհ�ѡ�ֵΪ��-1������ѡ�У�
		 * @param isNeedAll���Ƿ���Ҫ��ȫ���
		 * @param isNeedBlank �Ƿ���Ҫ�հ���
		 * @param strProperty ������������������ԣ���Ϊ���ַ�����
		 * @param lArrayID �Զ�����������ʵ������
		 */
		public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, 
				boolean isNeedBlank, String strProperty)
		{
			long[] lArrayID = null;
			String[] strArrayName = null;
			
			try
			{
				switch (nType) {
				case 0:
					lArrayID = new long[2];
					lArrayID[0] = CHECK;
					lArrayID[1] = ACTIVE;
					break;
				case 1:
					lArrayID = new long[4];
					lArrayID[0] = SAVE;
					lArrayID[1] = APPROVALING;
					lArrayID[2] = CHECK;
					lArrayID[3] = ACTIVE;
				}
				strArrayName = new String[lArrayID.length];
				
				for (int i = 0; i < lArrayID.length; i++)
				{
					strArrayName[i] = getName(lArrayID[i]);
				}
				
				showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
			}
			catch (Exception ex)
			{
				System.out.println(ex.toString());
			}
		}
	}
	

	
	//���������ѯ��ʽ
	public static class queryCreditProduct
	{
		public static final long SELF_AND_ZHSX = 1; // �Լ����ۺ�����
		public static final long ALL = 2; // ȫ������
		public static final long SELF= 3; // �Լ�
		
		public static final String getQueryString(long lCode, long lCreditProduct){
			String strReturn = ""; // ��ʼ������ֵ
			switch ((int) lCode) {
			case (int) SELF_AND_ZHSX:
				if(lCreditProduct == CreditProduct.ZHSX){
					strReturn = CreditProduct.ZY + ", " + CreditProduct.SP + ", " + CreditProduct.BH + ", " + CreditProduct.ZHSX;
				}
				else {
					strReturn = lCreditProduct + ", " + CreditProduct.ZHSX;
				}
				break;
			case (int) ALL:
				strReturn = CreditProduct.ZY + ", " + CreditProduct.SP + ", " + CreditProduct.BH + ", " + CreditProduct.ZHSX;
				break;
			case (int) SELF:
				strReturn = String.valueOf(lCreditProduct);
				break;
			}
			return strReturn;
		}
	}
	/**
	 * ����Ʒ��
	 * @author Administrator
	 *
	 */
	public static class CreditProduct {
		
		public static final long ZY = 1;  //��Ӫ��������

		public static final long SP = 2;  //��ҵ�жһ�Ʊ����

		public static final long BH = 3;  //����
		
		public static final long ZHSX = 4;  //�ۺ�����

		public static final String getName(long lCode) {
			String strReturn = ""; // ��ʼ������ֵ
			switch ((int) lCode) {
			case (int) ZY:
				strReturn = "��Ӫ����";
				break;
			case (int) SP:
				strReturn = "�жһ�Ʊ";
				break;
			case (int) BH:
				strReturn = "����";
				break;
			case (int) ZHSX:
				strReturn = "�ۺ�����";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { ZY, SP, BH, ZHSX };
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant.getAllCode(
					"com.iss.itreasury.loan.util.LOANConstant$CreditProduct",
					officeID, currencyID);
		}

		/**
		 * ��ʾ�����б�
		 * 
		 * @param out
		 * @param strControlName,
		 *            �ؼ�����
		 * @param nType���ؼ����ͣ�0����ʾȫ������
		 * @param lSelectValue
		 * @param isNeedAll���Ƿ���Ҫ��ȫ���
		 * @param isNeedBlank
		 *            �Ƿ���Ҫ�հ���
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode();
					break;
				
				case 1:
					lArrayID = new long[3];
					lArrayID[0] = ZY;
					lArrayID[1] = SP;
					lArrayID[2] = BH;
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				System.out.println(ex.toString());
			}
		}

		//���ְ��ºͱ��ֵ�������
		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty, long lOfficeID,
				long lCurrencyID) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
					case 0:
						lArrayID = getAllCode(lOfficeID, lCurrencyID);
						break;
						
					case 1:
						lArrayID = new long[3];
						lArrayID[0] = ZY;
						lArrayID[1] = SP;
						lArrayID[2] = BH;
						break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				System.out.println(ex.toString());
			}
		}

		// ֻ��ʾ�ж�����ƵĲ�Ʒ
		public static final void showListValid(JspWriter out,
				String strControlName, int nType, long lSelectValue,
				boolean isNeedAll, boolean isNeedBlank, String strProperty,
				long lOfficeID, long lCurrencyID) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				CreditLimitDAO dao = new CreditLimitDAO();
				switch (nType) {
				case 0:
					lArrayID = dao.findIsControlProduct();
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				System.out.println(ex.toString());
			}
		}

	}
	
	
	/**
	 * ����ģʽ
	 * @author leiyang
	 *
	 */
	public static class CreditMode {
		
		public static final long SINGLECORP  = 1;  //��һ��������

		public static final long GROUP  = 2;  ///��������

		public static final String getName(long lCode) {
			String strReturn = ""; // ��ʼ������ֵ
			switch ((int) lCode) {
			case (int) SINGLECORP:
				strReturn = "��һ��������";
				break;
			case (int) GROUP:
				strReturn = "��������";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = {SINGLECORP, GROUP};
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant.getAllCode(
					"com.iss.itreasury.loan.util.LOANConstant$CreditMode",
					officeID, currencyID);
		}

		/**
		 * ��ʾ�����б�
		 * 
		 * @param out
		 * @param strControlName,
		 *            �ؼ�����
		 * @param nType���ؼ����ͣ�0����ʾȫ������
		 * @param lSelectValue
		 * @param isNeedAll���Ƿ���Ҫ��ȫ���
		 * @param isNeedBlank
		 *            �Ƿ���Ҫ�հ���
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode();
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				System.out.println(ex.toString());
			}
		}

		//���ְ��ºͱ��ֵ�������
		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty, long lOfficeID,
				long lCurrencyID) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode(lOfficeID, lCurrencyID);
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				System.out.println(ex.toString());
			}
		}
	}

	public static class ActionTrace {
		public static class CreditAction {
			public static final long ADDNEW = 1;

			public static final long ACTIVE = 2;

			public static final long CANCELACTIVE = 3;

			public static final long FREEZE = 4;

			public static final long CANCELFREEZE = 5;

			public static final long MODIFY = 6;

			public static final long DELETE = 7;

			public static final long OVERTIME = 8;

			public static final String getName(long lCode) {
				String strReturn = ""; // ��ʼ������ֵ
				switch ((int) lCode) {
				case (int) ADDNEW:
					strReturn = "����";
					break;
				case (int) ACTIVE:
					strReturn = "����";
					break;
				case (int) CANCELACTIVE:
					strReturn = "ȡ������";
					break;
				case (int) FREEZE:
					strReturn = "����";
					break;
				case (int) MODIFY:
					strReturn = "�޸�";
					break;
				case (int) DELETE:
					strReturn = "ɾ��";
					break;
				case (int) CANCELFREEZE:
					strReturn = "ȡ������";
					break;
				case (int) OVERTIME:
					strReturn = "����";
					break;

				}
				return strReturn;
			}

		}
	}

	public static class CreditReportType {
		public static final long ABOUTUSE = 1;

		public static final long STATISTICS = 2;

		public static final long DETAIL = 3;

		public static final String getName(long lCode) {
			String strReturn = ""; // ��ʼ������ֵ
			switch ((int) lCode) {
			case (int) ABOUTUSE:
				strReturn = "ʹ�������";
				break;
			case (int) STATISTICS:
				strReturn = "ͳ�Ʊ�";
				break;
			case (int) DETAIL:
				strReturn = "��ϸ��";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { ABOUTUSE, STATISTICS, DETAIL };
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant
					.getAllCode(
							"com.iss.itreasury.loan.util.LOANConstant$CreditReportType",
							officeID, currencyID);
		}

		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode();
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				System.out.println(ex.toString());
			}
		}

		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty, long lOfficeID,
				long lCurrencyID) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode(lOfficeID, lCurrencyID);
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				System.out.println(ex.toString());
			}
		}
	}

	/*
	 * ��Ҫ���ٵ�ҵ�����
	 */
	public static class TraceType {
		public static long CREDIT = 1;
	}

	// �������뷽ʽ
	public static class CountInterestType {
		// �������뷽ʽ
		public static final long CalAfterRounding = 1; // ��������ۼ�

		public static final long CalBeforRounding = 2; // ���ۼӺ�����

		public static final String getName(long lCode) {
			String strReturn = ""; // ��ʼ������ֵ
			switch ((int) lCode) {
			case (int) CalAfterRounding:
				strReturn = "��������ۼ�";
				break;
			case (int) CalBeforRounding:
				strReturn = "���ۼӺ�����";
				break;
			}
			return strReturn;
		}
	}

	// �����շ�֪ͨ��״̬
	public static class AssureChargeNoticeStatus {
		// �����շ�֪ͨ��״̬
		//public static final long SAVE = 1; // �ѱ���
		
		public static final long SUBMIT = 1; // �ѱ���
		
		public static final long CHECK = 3; // �����

		public static final long USED = 4; // ��ʹ��

		public static final long CANCEL = 5; // ��ȡ��

		public static final long REFUSE = 6; // �Ѿܾ�
		
		public static final long APPROVALING = 20; // ������

		public static final long[] getAllCode() {
			long[] lTemp = {SUBMIT, CHECK, USED, CANCEL, REFUSE ,APPROVALING};
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant
					.getAllCode(
							"com.iss.itreasury.loan.util.LOANConstant$AssureChargeNoticeStatus",
							officeID, currencyID);
		}

		public static final String getName(long lCode) {
			String strReturn = ""; // ��ʼ������ֵ
			switch ((int) lCode) {
			//case (int) SAVE:
			//	strReturn = "�ѱ���";
			//	break;
			case (int) SUBMIT:
				strReturn = "�ѱ���";
				break;
			case (int) CHECK:
				strReturn = "������";
				break;
			case (int) USED:
				strReturn = "��ʹ��";
				break;
			case (int) CANCEL:
				strReturn = "��ȡ��";
				break;
			case (int) REFUSE:
				strReturn = "�Ѿܾ�";
				break;
			case (int) APPROVALING:
				strReturn = "������";
				break;
			}
			return strReturn;
		}
	}
    
	//�������ޱ�֤����ȡ֪ͨ��״̬
	public static class RecognizanceNoticeStatus {
		public static final long DEL = 0; // ��ɾ��
		
		public static final long SUBMIT = 1; // �ѱ���
		
		public static final long CHECK = 3; // ������

		public static final long USED = 4; // ��ʹ��

		public static final long CANCEL = 5; // ��ȡ��

		public static final long REFUSE = 6; // �Ѿܾ�
		
		public static final long APPROVALING = 20; // ������

		public static final long[] getAllCode() {
			long[] lTemp = {DEL,SUBMIT, CHECK, USED, CANCEL, REFUSE ,APPROVALING};
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant
					.getAllCode(
							"com.iss.itreasury.loan.util.LOANConstant$RecognizanceNoticeStatus",
							officeID, currencyID);
		}

		public static final String getName(long lCode) {
			String strReturn = ""; // ��ʼ������ֵ
			switch ((int) lCode) {
			case (int) DEL:
				strReturn = "��ɾ��";
				break;
			case (int) SUBMIT:
				strReturn = "�ѱ���";
				break;
			case (int) CHECK:
				strReturn = "������";
				break;
			case (int) USED:
				strReturn = "��ʹ��";
				break;
			case (int) CANCEL:
				strReturn = "��ȡ��";
				break;
			case (int) REFUSE:
				strReturn = "�Ѿܾ�";
				break;
			case (int) APPROVALING:
				strReturn = "������";
				break;
			}
			return strReturn;
		}
	}
	// ������֪ͨ��״̬
	public static class AssureManagementNoticeStatus {
		// ������֪ͨ��״̬
		//public static final long SAVE = 1; // �ѱ���
		
		public static final long SUBMIT = 1; // �ѱ���

		public static final long CHECK = 3; // �����

		public static final long USED = 4; // ��ʹ��

		public static final long CANCEL = 5; // ��ȡ��

		public static final long REFUSE = 6; // �Ѿܾ�
		
		public static final long APPROVALING = 20; // ������

		public static final long[] getAllCode() {
			long[] lTemp = {SUBMIT, CHECK, USED, CANCEL, REFUSE, APPROVALING };
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant
					.getAllCode(
							"com.iss.itreasury.loan.util.LOANConstant$AssureManagementNoticeStatus",
							officeID, currencyID);
		}

		public static final String getName(long lCode) {
			String strReturn = ""; // ��ʼ������ֵ
			switch ((int) lCode) {
			//case (int) SAVE:
			//	strReturn = "�ѱ���";
			//	break;
			case (int) SUBMIT:
				strReturn = "�ѱ���";
				break;
			case (int) CHECK:
				strReturn = "������";
				break;
			case (int) USED:
				strReturn = "��ʹ��";
				break;
			case (int) CANCEL:
				strReturn = "��ȡ��";
				break;
			case (int) REFUSE:
				strReturn = "�Ѿܾ�";
				break;
			case (int) APPROVALING:
				strReturn = "������";
				break;
			}
			return strReturn;
		}
	}

	// ������ʽ
	public static class AssureManagementType {
		// ������ʽ
		public static final long REPEAL = 1; // ����/��ǰ����

		public static final long COUNTERCLAIM = 2; // ��������

		public static final long CANCEL = 3; // ȡ������

		public static final long[] getAllCode() {
			long[] lTemp = { REPEAL, COUNTERCLAIM, CANCEL };
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant
					.getAllCode(
							"com.iss.itreasury.loan.util.LOANConstant$AssureManagementType",
							officeID, currencyID);
		}

		public static final String getName(long lCode) {
			String strReturn = ""; // ��ʼ������ֵ
			switch ((int) lCode) {
			case (int) REPEAL:
				strReturn = "����/��ǰ����";
				break;
			case (int) COUNTERCLAIM:
				strReturn = "��������";
				break;
			case (int) CANCEL:
				strReturn = "ȡ������";
				break;
			}
			return strReturn;
		}

		/**
		 * ��ʾ�����б�
		 * 
		 * @param out
		 * @param strControlName,
		 *            �ؼ�����
		 * @param nType���ؼ����ͣ�0����ʾȫ������
		 * @param lSelectValue
		 * @param isNeedAll���Ƿ���Ҫ��ȫ���
		 * @param isNeedBlank
		 *            �Ƿ���Ҫ�հ���
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode();
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				System.out.println(ex.toString());
			}
		}

		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty, long lOfficeID,
				long lCurrencyID) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode(lOfficeID, lCurrencyID);
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				System.out.println(ex.toString());
			}
		}
	}

	// �ʲ��ع���ͬ״̬
	public static class RepurchaseStatus {
		// �ʲ��ع���ͬ״̬
		public static final long SUBMIT = 1; // �ɹ���

		public static final long FINISH = 2; // �ѹ���

		public static final long CANCEL = 3; // ��ȡ��

		public static final String getName(long lCode) {
			String strReturn = ""; // ��ʼ������ֵ
			switch ((int) lCode) {
			case (int) SUBMIT:
				strReturn = "�ɹ���";
				break;
			case (int) FINISH:
				strReturn = "�ѹ���";
				break;
			case (int) CANCEL:
				strReturn = "��ȡ��";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { SUBMIT, FINISH, CANCEL };
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant
					.getAllCode(
							"com.iss.itreasury.loan.util.LOANConstant$RepurchaseStatus",
							officeID, currencyID);
		}

		/**
		 * ��ʾ�����б�
		 * 
		 * @param out
		 * @param strControlName,
		 *            �ؼ�����
		 * @param nType���ؼ����ͣ�0����ʾȫ������
		 * @param lSelectValue
		 * @param isNeedAll���Ƿ���Ҫ��ȫ���
		 * @param isNeedBlank
		 *            �Ƿ���Ҫ�հ���
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode();
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				System.out.println(ex.toString());
			}
		}

		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty, long lOfficeID,
				long lCurrencyID) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode(lOfficeID, lCurrencyID);
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				System.out.println(ex.toString());
			}
		}

	}

	/**
	 * �������Ĵ�������ת�����Ŵ��Ĵ������͡���������ί�д���Ϊ3���Ŵ���Ϊ2
	 * 
	 * @param longTypID
	 *            ����������
	 * @return �Ŵ�������
	 * @throws Exception
	 */
	public static long ConvertLoanTypeFromOBToLoan(long longTypeID)
			throws Exception {
		long rtn = -1;
		switch ((int) longTypeID) {
		case 1:
		case 2:
			rtn = 1;
			break;
		case 3:
			rtn = 2;
			break;
		case 4:
			// rtn=1;
			break;
		case 5:
			rtn = 3;
			break;
		case 6:
		case 7:
			rtn = 4;
			break;
		case 8:
			// rtn=1;
			break;
		case 9:
			// rtn=1;
			break;
		case 10:
			// rtn=1;
			break;
		case 11:
			// rtn=1;
			break;
		case 12:
			// rtn=1;
			break;
		case 14:
			// rtn=1;
			break;
		default:
			throw new Exception("�޷�ʶ��Ĵ�������");
		}
		return rtn;

	}

	// ��Ϣ���㷽ʽ���������ޣ�
	public static class InterestCountType {
		// �ʲ��ع���ͬ״̬
		//modified by xiongfei 2010/05/17 ���ȱ��͵ȶ�ĳ��˵ȶ��͵ȶϢ
		public static final long AVERAGEPRINCIPAL = 1; // �ȶ��

		public static final long AVERAGEAMOUNT = 2; // �ȶϢ

		public static final String getName(long lCode) {
			String strReturn = ""; // ��ʼ������ֵ
			switch ((int) lCode) {
			case (int) AVERAGEPRINCIPAL:
				strReturn = "�ȶ��";
				break;
			case (int) AVERAGEAMOUNT:
				strReturn = "�ȶϢ";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { AVERAGEPRINCIPAL, AVERAGEAMOUNT };
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant
					.getAllCode(
							"com.iss.itreasury.loan.util.LOANConstant$InterestCountType",
							officeID, currencyID);
		}

		/**
		 * ��ʾ�����б�
		 * 
		 * @param out
		 * @param strControlName,
		 *            �ؼ�����
		 * @param nType���ؼ����ͣ�0����ʾȫ������
		 * @param lSelectValue
		 * @param isNeedAll���Ƿ���Ҫ��ȫ���
		 * @param isNeedBlank
		 *            �Ƿ���Ҫ�հ���
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode();
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				System.out.println(ex.toString());
			}
		}

		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty, long lOfficeID,
				long lCurrencyID) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode(lOfficeID, lCurrencyID);
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				System.out.println(ex.toString());
			}
		}
	}
	// �������� added by mzh_fu 2007/06/22
	public static class WorkType {
		
		public static final long WAITDEALWITHWORK = 1;//������ҵ��
		
		public static final long CURRENTWORK = 2; //��������

		public static final long HISTORYWORK = 3;//�Ѱ�����

		public static final long FINISHEDWORK = 4;//�������
		
		public static final long REFUSEWORK = 5;//�ܾ�ҵ��
		
		public static final long CANCELAPPROVAL = 6;//ȡ������	
		
		//Added by leiyang, 2007/09/28 �õ��ҵĹ��������ӵ�ַ
		public final static String getWorkUrl(String strContext, long workType){
			String workUrl = "";
			switch((int)workType){
				case (int)WAITDEALWITHWORK:
					workUrl = strContext + "/mywork/waitDealWithWorkList-main.jsp";
					break;
				case (int)CURRENTWORK:
					workUrl = strContext + "/mywork/currentWorkList-main.jsp";
					break;
				case (int)HISTORYWORK:
					workUrl = strContext + "/securities/mywork/historyWorkList-main.jsp";
					break;
				case (int)FINISHEDWORK:
					workUrl = strContext + "";
					break;
				case (int)REFUSEWORK:
					workUrl = strContext + "/mywork/refuseWorkList-main.jsp";
					break;
				case (int)CANCELAPPROVAL:
					workUrl = strContext + "/mywork/cancelApprovalList-main.jsp";
					break;					
			}
			return workUrl;
		}
		
		public final static String getWorkUrl(long workType){
			String strContext="/NASApp/iTreasury-loan";
			return getWorkUrl(strContext, workType);
		}
	}
	
	/**
	 * ����������� added by mzh_fu 2007/08/31
	 * 
	 */
	public static class AssortSetType {
		public static final long AREA = 1; // ��������

		public static final long INDUSTRY1 = 2;// ��ҵ����1

		public static final long INDUSTRY2 = 3;// ��ҵ����2
        //modify by xwhe 2008-06-10 
		public static final long INDUSTRY3 = 4;// �Ƿ�����˻����

		public static final String getName(long lCode) {
			String strReturn = ""; // ��ʼ������ֵ
			switch ((int) lCode) {
			case (int) AREA:
				strReturn = "��������";
				break;
			case (int) INDUSTRY1:
				strReturn = "��ҵ����1";
				break;
			case (int) INDUSTRY2:
				strReturn = "��ҵ����2";
				break;
			case (int) INDUSTRY3:
				strReturn = "�Ƿ�����˻����";
				break;
			
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { AREA, INDUSTRY1, INDUSTRY2, INDUSTRY3 };
			return lTemp;
		}
		
		/**
		 * ����Vector��ID��ȡ�����ͬ����<p>
		 * 
		 * @param FieldID
		 * @param officeID
		 * @param currencyID
		 * @return
		 * @throws Exception
		 * 
		 * @author kaishao
		 */
		public static final String getContractName(long FieldID,long officeID,
				long currencyID) throws Exception{
			String str = "";
			str = new LoanContractAssortSettingBiz().getFieldname(FieldID,officeID,currencyID);
			if(str==null||"".equals(str)){
				str=LOANConstant.AssortSetType.getName((int)FieldID);
			}
			return (str);
		}

		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode();
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				System.out.println(ex.toString());
			}
		}
	}
	
	//Modify by leiyang 2007/09/04 ͬ������MagnifierSQL��������
	//��������״̬
	public static class ObjectiveSetting
	{
		public static final long RSAVE = 1;  //�ѱ���
		public static final long RDELETE = -1; //��ɾ��
		public static final long APPROVAL = 2; //������
		public static final long CHECK = 3; //������
		public static final long REFUSE = 4; //�Ѿܾ�
		
		public static final String getName(long creditId){
			String strReturn = ""; //��ʼ������ֵ
			
			switch ((int) creditId)
			{
				case (int) RSAVE :
					strReturn = "�ѱ���";
					break;
				case (int) RDELETE :
					strReturn = "��ɾ��";
					break;
				case (int) APPROVAL :
					strReturn = "������";
					break;
				case (int) CHECK :
					strReturn = "������";
					break;
				case (int) REFUSE :
					strReturn = "�Ѿܾ�";
					break;
			}
			return strReturn;
		}
		
		public static long[] getAllCode(){
			long lTemp[] = null;
			lTemp = new long[4];
			lTemp[0] = RSAVE;
			lTemp[1] = APPROVAL;
			lTemp[2] = CHECK;
			lTemp[3] = REFUSE;
			return lTemp;
		}
		
		public static long[] getAllCodes(){
			long lTemp[] = null;
			lTemp = new long[2];
			lTemp[0] = RSAVE;
			lTemp[1] = APPROVAL;
			return lTemp;
		}
		
		public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
		{
			long[] lArrayID = null;
			String[] strArrayName = null;
			try
			{
				switch (nType)
				{
					case 0 :
						lArrayID = getAllCode();
						break;
					case 1 :
						lArrayID = getAllCodes();
						break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++)
				{
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
			}
			catch (Exception ex)
			{
				System.out.println(ex.toString());
			}
		}
	}
	
	
	 /**
     * ����ҵ���Ƿ���ж�ȿ���
     * @author Administrator
     *
     */
	public static class ISRatingControl {
		
		public static final long YES = 1; //��

		public static final long NO = 0; // ��

		public static final String getName(long lCode) {
			
			String strReturn = ""; // ��ʼ������ֵ
			switch ((int) lCode) {
			case (int) YES:
				strReturn = "��";
				break;
			case (int) NO:
				strReturn = "��";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { YES, NO };
			return lTemp;
		}
	
		/**
		 * ��ʾ�����б�(����Ʒ���Ƿ���ж�ȿ���)
		 * 
		 * @param out
		 * @param strControlName     �ؼ�����
		 * @param nType���ؼ����ͣ�0����ʾȫ������
		 * @param lSelectValue �Ƿ�ѡ�пհ�ѡ�ֵΪ��-1������ѡ�У�
		 * @param isNeedAll���Ƿ���Ҫ��ȫ���
		 * @param isNeedBlank �Ƿ���Ҫ�հ���
		 * @param strProperty ������������������ԣ���Ϊ���ַ�����
		 */
		public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
		{
			long[] lArrayID = null;
			String[] strArrayName = null;
			
			try
			{
				lArrayID = getAllCode();				
				strArrayName = new String[lArrayID.length];
				
				for (int i = 0; i < lArrayID.length; i++)
				{
					strArrayName[i] = getName(lArrayID[i]);
				}
				
				showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
			}
			catch (Exception ex)
			{
				System.out.println(ex.toString());
			}
		}
	}
	
	 /**
     * ���Ʒ�ʽ
     * @author Administrator
     *
     */
	public static class ControlMode {
		
		public static final long RIGIDITY = 1; //���Կ���

		public static final long FLEXIBLE = 2; //���Կ���

		public static final String getName(long lCode) {
			
			String strReturn = ""; // ��ʼ������ֵ
			switch ((int) lCode) {
			case (int) RIGIDITY:
				strReturn = "���Կ���";
				break;
			case (int) FLEXIBLE:
				strReturn = "���Կ���";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { RIGIDITY, FLEXIBLE };
			return lTemp;
		}
	
		/**
		 * ��ʾ�����б�(����Ʒ���Ƿ���ж�ȿ���)
		 * 
		 * @param out
		 * @param strControlName     �ؼ�����
		 * @param nType���ؼ����ͣ�0����ʾȫ������
		 * @param lSelectValue �Ƿ�ѡ�пհ�ѡ�ֵΪ��-1������ѡ�У�
		 * @param isNeedAll���Ƿ���Ҫ��ȫ���
		 * @param isNeedBlank �Ƿ���Ҫ�հ���
		 * @param strProperty ������������������ԣ���Ϊ���ַ�����
		 */
		public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
		{
			long[] lArrayID = null;
			String[] strArrayName = null;
			
			try
			{
				lArrayID = getAllCode();				
				strArrayName = new String[lArrayID.length];
				
				for (int i = 0; i < lArrayID.length; i++)
				{
					strArrayName[i] = getName(lArrayID[i]);
				}
				
				showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
			}
			catch (Exception ex)
			{
				System.out.println(ex.toString());
			}
		}
	}
	
	 /**
     * ��������
     * @author Administrator
     */
	public static class LoanTerm {
		public static final long SIXMOUTH_BELOW			= 1;  //���������ڣ�����
		public static final long SIXMOUTH_ONEYEAR		= 2;  //��������һ��
		public static final long ONEYEAR_THREEYEAR		= 3;  //һ��������
		public static final long THREEYEAR_FIVEYEAR		= 4;  //����������
		public static final long FIVEYEAR_ABOVE			= 5;  //��������
		
		public static final String getName(long lCode) {
			
			String strReturn = ""; // ��ʼ������ֵ
			switch ((int) lCode) {
				case (int) SIXMOUTH_BELOW:
					strReturn = "���������ڣ�����";
					break;
				case (int) SIXMOUTH_ONEYEAR:
					strReturn = "��������һ��";
					break;
				case (int) ONEYEAR_THREEYEAR:
					strReturn = "һ��������";
					break;
				case (int) THREEYEAR_FIVEYEAR:
					strReturn = "����������";
					break;
				case (int) FIVEYEAR_ABOVE:
					strReturn = "��������";
					break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { SIXMOUTH_BELOW, SIXMOUTH_ONEYEAR, ONEYEAR_THREEYEAR, THREEYEAR_FIVEYEAR, FIVEYEAR_ABOVE };
			return lTemp;
		}
	
		/**
		 * ��ʾ�����б�(��������)
		 * @param out
		 * @param strControlName     �ؼ�����
		 * @param nType���ؼ����ͣ�0����ʾȫ������
		 * @param lSelectValue �Ƿ�ѡ�пհ�ѡ�ֵΪ��-1������ѡ�У�
		 * @param isNeedAll���Ƿ���Ҫ��ȫ���
		 * @param isNeedBlank �Ƿ���Ҫ�հ���
		 * @param strProperty ������������������ԣ���Ϊ���ַ�����
		 */
		public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
		{
			long[] lArrayID = null;
			String[] strArrayName = null;
			
			try
			{
				lArrayID = getAllCode();
				strArrayName = new String[lArrayID.length];
				
				for (int i = 0; i < lArrayID.length; i++)
				{
					strArrayName[i] = getName(lArrayID[i]);
				}
				
				showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank, false);
			}
			catch (Exception ex)
			{
				System.out.println(ex.toString());
			}
		}
	}
	
	
	/******************************************************************************************************/
	/*
	 * ���ų�������ʱ����LOANConstant�н�����ֲ��CREDITConstant��
	 */
	
	public static class CreditType {
		
		public static final long ZY = 1; // ��Ӫ����

		//public static final long WT = 2; // ί�д���

		public static final long TX = 3; // ����

		//public static final long ZGXE = 4; // ����޶�

		//public static final long YT = 5; // ���Ŵ���

		//public static final long ZTX = 6; // ת����

		//public static final long MFXD = 7; // ���Ŵ�

		public static final long DB = 8; // ����

		public static final String getName(long lCode) {
			String strReturn = ""; // ��ʼ������ֵ
			switch ((int) lCode) {
			case (int) ZY:
				strReturn = "��Ӫ����";
				break;
			case (int) TX:
				strReturn = "����";
				break;
			case (int) DB:
				strReturn = "����";
				break;
			}
			return strReturn;
		}
		
		public static final long[] getAllCode(long officeID, long currencyID) {
			long[] creditType =Constant.getAllCode("com.iss.itreasury.loan.util.LOANConstant$CreditType", officeID, currencyID);
			return creditType != null ? creditType : (new long[0]);
		}
		public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty,long officeID, long currencyID)
		{
			long[] lArrayID = null;
			String[] strArrayName = null;
			try
			{
				switch (nType)
				{
					case 0 :
						lArrayID = getAllCode(officeID, currencyID);
						break;
				}
				strArrayName = new String[lArrayID.length];
				
				for (int i = 0; i < lArrayID.length; i++)
				{
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
			}
			catch (Exception ex)
			{
				System.out.println(ex.toString());
			}
		}
	}
	
	/*
	 * ����ģʽ
	 */
	public static class CreditModel {
	
		public static final long SIMPLE = 1; //��һ��������

		public static final long GROUP = 2; //��������

		public static final String getName(long lCode) {
			String strReturn = ""; // ��ʼ������ֵ
			switch ((int) lCode) {
			case (int) SIMPLE:
				strReturn = "��һ��������";
				break;
			case (int) GROUP:
				strReturn = "��������";
				break;	
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { SIMPLE, GROUP };
			return lTemp;
		}
		
		/**
		 * ��ʾ�����б�
		 * 
		 * @param out
		 * @param strControlName,�ؼ�����
		 * @param nType���ؼ����ͣ�0����ʾȫ������
		 * @param lSelectValue
		 * @param isNeedAll���Ƿ���Ҫ"ȫ����"
		 * @param isNeedBlank���Ƿ���Ҫ�հ���
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
					case 0:
						lArrayID = getAllCode();
						break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				System.out.println(ex.toString());
			}
		}
	}
	
	/*
	 * ���Ų�������
	 */
	public static class OperationType {
		
		public static final long NEW = 1; //��������

		public static final long CHANGE = 2; //���ű��
		
		public static final String getName(long lCode) {
			String strReturn = ""; // ��ʼ������ֵ
			switch ((int) lCode) {
			case (int) NEW:
				strReturn = "��������";
				break;
			case (int) CHANGE:
				strReturn = "���ű��";
				break;	
			}
			return strReturn;
		}
		
		public static final long[] getAllCode() {
			long[] lTemp = { NEW, CHANGE };
			return lTemp;
		}
		
		public static final void showList(JspWriter out, String strControlName,int nType, long lSelectValue, boolean isNeedAll,boolean isNeedBlank, String strProperty) 
		{
			long[] lArrayID = null;
			String[] strArrayName = null;
			try{
				switch (nType) 
				{
					case 0:lArrayID = getAllCode();
						   break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				System.out.println(ex.toString());
			}
		}
		
	}
	
	/*
	 * �����Ƿ����ʶ
	 */
	public static class CreditShare {
		
		public static final long YES = 0; //����ʹ���ۺ�����

		public static final long NO = 1; //������ռ��Ʒ������
		
	}
	
	/*
	 * ���ű��������
	 */
	public static class OperationSign {
		
		public static final long ADDITION = 1; //�ӷ�

		public static final long SUBTRACTION = 2; //����
		
		public static final String getSignName(long lCode) {
			String strReturn = ""; // ��ʼ������ֵ
			switch ((int) lCode) {
			case (int) ADDITION:
				strReturn = "+";
				break;
			case (int) SUBTRACTION:
				strReturn = "-";
				break;	
			}
			return strReturn;
		}
		
		public static final String getName(long lCode) {
			String strReturn = ""; // ��ʼ������ֵ
			switch ((int) lCode) {
			case (int) ADDITION:
				strReturn = "����";
				break;
			case (int) SUBTRACTION:
				strReturn = "����";
				break;	
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { ADDITION, SUBTRACTION };
			return lTemp;
		}
		
		/**
		 * ��ʾ�����б�
		 * 
		 * @param out
		 * @param strControlName,�ؼ�����
		 * @param nType���ؼ����ͣ�0����ʾȫ������
		 * @param lSelectValue
		 * @param isNeedAll���Ƿ���Ҫ"ȫ����"
		 * @param isNeedBlank���Ƿ���Ҫ�հ���
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
					case 0:
						lArrayID = getAllCode();
						break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getSignName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				System.out.println(ex.toString());
			}
		}
	}
	
	/*
	 * ���ſ��Ʒ�ʽ
	 */
	public static class ControlType {
		
		public static final long RIGIDITY = 1; //����

		public static final long FLEXIBILITY = 2; //����

		public static final String getName(long lCode) {
			String strReturn = ""; // ��ʼ������ֵ
			switch ((int) lCode) {
			case (int) RIGIDITY:
				strReturn = "����";
				break;
			case (int) FLEXIBILITY:
				strReturn = "����";
				break;	
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { RIGIDITY, FLEXIBILITY };
			return lTemp;
		}
		
		/**
		 * ��ʾ�����б�
		 * 
		 * @param out
		 * @param strControlName,�ؼ�����
		 * @param nType���ؼ����ͣ�0����ʾȫ������
		 * @param lSelectValue
		 * @param isNeedAll���Ƿ���Ҫ"ȫ����"
		 * @param isNeedBlank���Ƿ���Ҫ�հ���
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
					case 0:
						lArrayID = getAllCode();
						break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				System.out.println(ex.toString());
			}
		}
	}
	
	/**
	 * @author afzhu
	 * ��������������� ---���㷽ʽ
	 */
	public static class BalanceType
	{
		public static final long CURRENTACCOUNT = 1;//�����˻�
		public static final long RECOGNIZANCEACCOUNT = 2;//��֤���˻�
		public static final long FIRST_CURRENTACCOUNT_LATE_RECOGNIZANCEACCOUNT = 3;//�Ȼ��ں�֤��
		public static final long CURRENTACCOUNTRECOGNIZANCEACCOUNTSCALE = 4;//�����뱣֤�𰴱���
		public static final String getName(long lCode) {
			String strReturn = ""; // ��ʼ������ֵ
			switch ((int) lCode) {
			case (int) CURRENTACCOUNT:
			    strReturn = "�����˻�";
			    break;
			case (int) RECOGNIZANCEACCOUNT:
				strReturn = "��֤���˻�";
				break;
			case (int) FIRST_CURRENTACCOUNT_LATE_RECOGNIZANCEACCOUNT:
				strReturn = "�Ȼ��ں�֤��";
				break;
			case (int) CURRENTACCOUNTRECOGNIZANCEACCOUNTSCALE:
				strReturn = "�����뱣֤�𰴱���";
				break;
			}
			return strReturn;
		}
	}
	
	/**
	 * ���Ŷ��״̬
	 * @author Administrator
	 *
	 */
	public static class CreditFlowStatus {
		
		public static final long SAVE = 1;     //�ѱ���
		
 		public static final long CHECK = 3;    //������
		
		public static final long APPROVALING = 20; //������
		
		public static final long ACTIVE = 6;   //�Ѽ���
		
		public static final long DELETE = 0;  //��ɾ��

		
		public static final String getName(long lCode) {
			String strReturn = ""; // ��ʼ������ֵ
			switch ((int) lCode) {
			case (int) SAVE:
			    strReturn = "�ѱ���";
			    break;
			case (int) CHECK:
				strReturn = "������";
				break;
			case (int) ACTIVE:
				strReturn = "�Ѽ���";
				break;
			case (int) DELETE:
				strReturn = "��ɾ��";
				break;
			case (int) APPROVALING:
			    strReturn = "������";
			    break;
			}
			return strReturn;
		}
		
		/**
		 * ��ʾ�����б�
		 * 
		 * @param out
		 * @param strControlName,�ؼ�����
		 * @param nType���ؼ����ͣ�0����ʾȫ������
		 * @param lSelectValue
		 * @param isNeedAll���Ƿ���Ҫ"ȫ����"
		 * @param isNeedBlank���Ƿ���Ҫ�հ���
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			String[] strArrayValue = null;
			try {
				switch (nType) {
					case 0:
						lArrayID = new long[]{SAVE, APPROVALING, CHECK, ACTIVE};
						break;
				}
				strArrayName = new String[lArrayID.length];
				strArrayValue = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
					strArrayValue[i] = String.valueOf(lArrayID[i]);
				}
				showCommonList(out, strControlName, strArrayValue, strArrayName,
						String.valueOf(lSelectValue), isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				System.out.println(ex.toString());
			}
		}
	}
	
	
	// ���ʵ���״̬
	public static class LoanRateAdjustStatus {
		// ����״̬
		public static final long SAVE = 1; // �ѱ���

		public static final long SUBMIT = 2; // ���ύ

		public static final long CHECK = 3; // ������

		public static final long REFUSE = -2; // �Ѿܾ�

		

		public static final long CANCEL = 0; // �Ѿ�ȡ��
		
		public static final long APPROVALING = 20; //������		

		public static final String getName(long lCode) {
			String strReturn = ""; // ��ʼ������ֵ
			switch ((int) lCode) {
			case (int) SAVE:
				//modified by mzh_fu 2007/06/11,����δ�ύ����ǰֻ��һ��״̬"�ѱ���"
//				strReturn = "׫д";
				strReturn = "�ѱ���";
				break;
			case (int) SUBMIT:
				strReturn = "���ύ";
				break;
			case (int) CHECK:
				strReturn = "������";
				break;
			case (int) REFUSE:
				strReturn = "�Ѿܾ�";
				break;
			
			case (int) CANCEL:
				strReturn = "��ȡ��";
				break;
			case (int) APPROVALING:
				strReturn = "������";
				break;				
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { SAVE, SUBMIT, CHECK, REFUSE, CANCEL };

			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant.getAllCode(
					"com.iss.itreasury.loan.util.LOANConstant$LoanRateAdjustStatus",
					officeID, currencyID);
		}
	}

	//	 --״̬
	public static class reportState
	{
		//״̬
		public static final long SAVE = 1;//����
		public static final long SUBMIT = 2;//�ύ
		public static final long DELETE = 3;//ɾ��
		public static final long CHECKING = 4;//�����
		public static final long CHECK = 5;//���ͨ��
		public static final long REFUSE = -2;//�Ѿܾ�
		public static final long INVALIDATION = 7;//ʧЧ
		
		public static final String getName(long lCode)
		{
			String strReturn = ""; //��ʼ������ֵ
			switch ((int) lCode)
			{
				case (int) SAVE :
					strReturn = "׫д";
					break;
				case (int) SUBMIT :
					strReturn = "���ύ";
					break;
				case (int) DELETE :
					strReturn = "ɾ��";
					break;
				case (int) CHECKING :
					strReturn = "�����";
					break;
				case (int) CHECK :
					strReturn = "�����";
					break;
				case (int) REFUSE :
					strReturn = "�Ѿܾ�";
					break;
				case (int) INVALIDATION :
					strReturn = "ʧЧ";
					break;
		
			}
			return strReturn;
		}
		public static final long[] getAllCode()
		{
			long[] lTemp = { SAVE, SUBMIT ,DELETE ,CHECKING,CHECK,REFUSE,INVALIDATION };
			return lTemp;
		}
		
	}


	public static class GageType
	{
		public static final long GYTDSYQ = 1; //��������ʹ��Ȩ
		public static final long FWSYQ = 2; //��������Ȩ
		public static final long JQSB = 3; //�����豸
		public static final long QTCC = 4; //�����Ʋ�
		public static final long XYBZ = 5; //���ñ�֤
		public static final long HP = 6; //��Ʊ
		public static final long BP = 7; //��Ʊ
		public static final long ZP = 8; //֧Ʊ
		public static final long ZJ = 9; //ծ��
		public static final long CD = 10; //�浥
		public static final long TD = 11; //�ᵥ
		public static final long GPGQ = 12; //��Ʊ����Ȩ
		public static final long YSK = 13; //Ӧ�տ�
		public static final long QCHGZ = 14; //�����ϸ�֤
		public static final long QTQL = 15; //����Ȩ��
		public static final long BZJ = 16; //��֤��
		public static final String getName(long lCode)
		{
			String strReturn = ""; //��ʼ������ֵ
			switch ((int) lCode)
			{
				case (int) GYTDSYQ :strReturn = "��������ʹ��Ȩ"; break;
				case (int) FWSYQ   :strReturn = "��������Ȩ";   break;
				case (int) JQSB    :strReturn = "�����豸";   break;
				case (int) QTCC    :strReturn = "�����Ʋ�";   break;
				case (int) XYBZ    :strReturn = "��֤";  break;//ԭΪ���ñ�֤
				case (int) HP      :strReturn = "��Ʊ";   break;
				case (int) BP      :strReturn = "��Ʊ";   break;
				case (int) ZP      :strReturn = "֧Ʊ";   break;
				case (int) ZJ      :strReturn = "ծ��";  break;
				case (int) CD      :strReturn = "�浥";   break;
				case (int) TD      :strReturn = "�ᵥ";   break;
				case (int) GPGQ    :strReturn = "��Ʊ";   break;
				case (int) YSK     :strReturn = "Ӧ�տ�";  break;
				case (int) QCHGZ   :strReturn = "�����ϸ�֤";   break;
				case (int) QTQL    :strReturn = "����Ȩ��";   break;
				case (int) BZJ     :strReturn = "��֤��";   break;
			}
			return strReturn;
		}
		public static final long[] getAllCodeI()//Ȩ֤������룭��ѯ����Ʒ��Ϣ--��Ѻ
		{
			long[] longTemp = {GYTDSYQ,FWSYQ,HP,BP,ZP,CD,GPGQ,QCHGZ};
			return longTemp;
		}
		public static final long[] getDiYa()//��ѺƷ����
		{
			long[] longTemp = {GYTDSYQ,FWSYQ,JQSB,QTCC};
			return longTemp;
		}
		public static final long[] getZhiYa()//��ѺƷ����
		{
			long[] longTemp = {HP,BP,ZP,ZJ,CD,TD,GPGQ,YSK,QCHGZ,QTQL};
			return longTemp;
		}
		public static final long[] getXinYong()//���ñ�֤����
		{
			long[] longTemp = {XYBZ};
			return longTemp;
		}
		public static final long[] getBaoZhengJin()//��֤������
		{
			long[] longTemp = {BZJ};
			return longTemp;
		}
		public static final long[] getNull()
		{
			long[] longTemp = {};
			return longTemp;
		}
		public static final long[] getAllCode()
		{
			long[] longTemp = {GYTDSYQ,FWSYQ,JQSB,QTCC,XYBZ,HP,BP,ZP,ZJ,CD,TD,GPGQ,YSK,QCHGZ,QTQL,BZJ};
			return longTemp;
		}
		public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
		{
			long[] lArrayID = null;
			String[] strArrayName = null;
			try
			{
				switch (nType)
				{
					case 0 :
						lArrayID = getAllCodeI();
						break;
					case 1:
						lArrayID = getDiYa();
						break;
					case 2:
						lArrayID = getZhiYa();
						break;
					case 3:
						lArrayID = getXinYong();
						break;
					case 4:
						lArrayID = getBaoZhengJin();
						break;
					case 5:
						lArrayID = getNull();
						break;
					case 6:
						lArrayID = getAllCode();
						break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++)
				{
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
			}
			catch (Exception ex)
			{
				System.out.println(ex.toString());
			}
		}
    }


	public static class MainTenanceAction
	{
		
		public static final long MCHECKIN = 1; //��Ϣ�Ǽ�
		public static final long MAINTENANCE = 2; //��Ϣά��
		public static final long MDELETE = 3; //��Ϣɾ��

		
		public static final String getName(long lCode)
		{
			String strReturn = ""; //��ʼ������ֵ
			switch ((int) lCode)
			{
				case (int) MCHECKIN :strReturn = "��Ϣ�Ǽ�"; break;
				case (int) MAINTENANCE   :strReturn = "��Ϣά��";   break;
				case (int) MDELETE   :strReturn = "��Ϣɾ��";   break;
			}
			return strReturn;
		}
		public static final long[] getAllCode()//Ȩ֤������룭��ѯ����Ʒ��Ϣ����Ʒ״̬
		{
			long[] longTemp = {MCHECKIN,MAINTENANCE,MDELETE};
			return longTemp;
		}
		public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
		{
			long[] lArrayID = null;
			String[] strArrayName = null;
			try
			{
				switch (nType)
				{
					case 0 :
						lArrayID = getAllCode();
						break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++)
				{
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
			}
			catch (Exception ex)
			{
				System.out.println(ex.toString());
			}
		}
    }


	public static class GageStatus
	{
		
		public static final long INVALID = 0; //��Ч(ɾ��)
		public static final long SAVE = 1; //�ѵǼ�
		public static final long SUBMIT = 2; //���ύ
		public static final long CHECKING = 3; //�����
		public static final long BECOME_EFFECTIVE = 4;	//��ʹ��
		public static final long BECOME_INVALID = 5;	//��ʧЧ
		public static final long REFUSE = -2; //�Ѿܾ�
		
		public static final String getName(long lCode)
		{
			String strReturn = ""; //��ʼ������ֵ
			switch ((int) lCode)
			{
			    case (int) REFUSE :strReturn = "�Ѿܾ�"; break;
				case (int) SAVE :strReturn = "�ѵǼ�"; break;
				case (int) SUBMIT   :strReturn = "���ύ";   break;
				case (int) CHECKING   :strReturn = "�����";   break;
				case (int) BECOME_EFFECTIVE   :strReturn = "��ʹ��";   break;
				case (int) BECOME_INVALID   :strReturn = "��ʧЧ";   break;
			}
			return strReturn;
		}
		public static final long[] getAllCode()//Ȩ֤������룭��ѯ����Ʒ��Ϣ����Ʒ״̬
		{
			long[] longTemp = {SAVE,BECOME_EFFECTIVE,BECOME_INVALID};
			return longTemp;
		}
		public static final long[] getAllCodeI()//Ȩ֤������룭��ѯ����Ʒ��Ϣ����Ʒ״̬
		{
			long[] longTemp = {SAVE};
			return longTemp;
		}
		public static final long[] getAllCodeII()//����Ʒ��Ϣά������ѯ����Ʒ��Ϣ����Ʒ״̬
		{
			long[] longTemp = {SAVE,BECOME_EFFECTIVE};
			return longTemp;
		}
		public static final long[] getAllCodeIII()//����Ʒ����ѯ
		{
			long[] longTemp = {SAVE,BECOME_EFFECTIVE,REFUSE,SUBMIT,CHECKING,BECOME_INVALID};
			return longTemp;
		}
		public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
		{
			long[] lArrayID = null;
			String[] strArrayName = null;
			try
			{
				switch (nType)
				{
					case 0 :
						lArrayID = getAllCodeI();
						break;
					case 1 :
						lArrayID = getAllCodeII();
						break;	
					case 2 :
						lArrayID = getAllCode();
						break;
					case 3 :
						lArrayID = getAllCodeIII();
						break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++)
				{
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
			}
			catch (Exception ex)
			{
				System.out.println(ex.toString());
			}
		}
    }

	public static class WarrantStatus
	{
		public static final long REFUSE = -2; //�Ѿܾ�
		public static final long SAVE = 1;//�ѵǼ�
		public static final long SUBMIT = 2; //���ύ
		public static final long CHECKING = 3; //�����
		public static final long BECOME_EFFECTIVE = 4; //�����
		public static final long BECOME_INVALID = 5;   //�ѳ���
		public static final long LEND = 6;	           //�ѳ���
		public static final long RESTORE = 7;	       //�ѹ黹
		public static final long INVALID = 0; //��Ч(ɾ��)
		
		
		public static final String getName(long lCode)
		{
			String strReturn = ""; //��ʼ������ֵ
			switch ((int) lCode)
			{
			    case (int) REFUSE   :strReturn = "�Ѿܾ�";   break;
			    case (int) SAVE   :strReturn = "�ѵǼ�";   break;
			    case (int) SUBMIT   :strReturn = "���ύ";   break;
			    case (int) CHECKING   :strReturn = "�����";   break;
				case (int) BECOME_EFFECTIVE   :strReturn = "�����";   break;
				case (int) BECOME_INVALID   :strReturn = "�ѳ���";   break;
				case (int) LEND   :strReturn = "�ѳ���";   break;
				case (int) RESTORE   :strReturn = "�ѹ黹";   break;
			}
			return strReturn;
		}
		public static final long[] getAllCodeI()
		{
			long[] longTemp = {BECOME_EFFECTIVE};
			return longTemp;
		}
		public static final long[] getAllCodeII()
		{
			long[] longTemp = {LEND};
			return longTemp;
		}
		public static final long[] getAllCodeIII()
		{
			long[] longTemp = {SAVE,BECOME_EFFECTIVE,BECOME_INVALID,LEND};
			return longTemp;
		}
		public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
		{
			long[] lArrayID = null;
			String[] strArrayName = null;
			try
			{
				switch (nType)
				{
					case 0 :
						lArrayID = getAllCodeI();
						break;
					case 1 :
						lArrayID = getAllCodeII();
						break;
					case 2 :
						lArrayID = getAllCodeIII();
						break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++)
				{
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
			}
			catch (Exception ex)
			{
				System.out.println(ex.toString());
			}
		}
    }

	public static class GageProperty
	{
		public static final long CREDIT = 1; //���ñ�֤
		public static final long PLEDGE = 2; //��Ѻ
		public static final long IMPAWN = 3; //��Ѻ
		public static final long RECOGNIZANCE = 4; //��֤��
		public static final String getName(long lCode)
		{
			String strReturn = ""; //��ʼ������ֵ
			switch ((int) lCode)
			{
				case (int) CREDIT :
					strReturn = "��֤";//ԭΪ���ñ�֤
					break;
				case (int) PLEDGE :
					strReturn = "��ѺƷ";
					break;
				case (int) IMPAWN :
					strReturn = "��ѺƷ";
					break;
				case (int) RECOGNIZANCE :
					strReturn = "��֤��";
					break;
			}
			return strReturn;
		}
		
		
		
		public static final long[] getAllCodeI()
		{
			long[] longTemp = {CREDIT, PLEDGE, IMPAWN, RECOGNIZANCE };
			return longTemp;
		}
		public static final long[] getAllCodeII()
		{
			long[] longTemp = {PLEDGE, IMPAWN};
			return longTemp;
		}
		public static final long[] getAllCodeIII()
		{
			long[] longTemp = {PLEDGE, IMPAWN, RECOGNIZANCE };
			return longTemp;
		}
		//add by wwcheng 10-01-11
		public static final long[] getAllCodeIV()
		{
			long[] longTemp = {PLEDGE};
			return longTemp;
		}
		public static final long[] getAllCodeV()
		{
			long[] longTemp = {IMPAWN};
			return longTemp;
		}
		public static final long[] getAllCodeVI()
		{
			long[] longTemp = {RECOGNIZANCE };
			return longTemp;
		}
		public static final long[] getAllCodeVII()
		{
			long[] longTemp = {PLEDGE, RECOGNIZANCE };
			return longTemp;
		}
		public static final long[] getAllCodeVIII()
		{
			long[] longTemp = {IMPAWN, RECOGNIZANCE };
			return longTemp;
		}
		
		public static final long[] getAllCodeIVV()
		{
			long[] longTemp = {CREDIT};
			return longTemp;
		}
		public static final long[] getAllCodeIIVV()
		{
			long[] longTemp = {CREDIT, PLEDGE};
			return longTemp;
		}
		public static final long[] getAllCodeIIIVV()
		{
			long[] longTemp = {CREDIT, IMPAWN};
			return longTemp;
		}
		public static final long[] getAllCodeVVV()
		{
			long[] longTemp = {CREDIT, RECOGNIZANCE };
			return longTemp;
		}
		public static final long[] getAllCodeVVVI()
		{
			long[] longTemp = {CREDIT, PLEDGE, IMPAWN};
			return longTemp;
		}
		public static final long[] getAllCodeVVVII()
		{
			long[] longTemp = {CREDIT, PLEDGE,  RECOGNIZANCE };
			return longTemp;
		}
		public static final long[] getAllCodeVVVIII()
		{
			long[] longTemp = {CREDIT, IMPAWN, RECOGNIZANCE };
			return longTemp;
		}
		
		
		public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
		{
			long[] lArrayID = null;
			String[] strArrayName = null;
			try
			{
				switch (nType)
				{
					case 0 :
						lArrayID = getAllCodeI();
						break;
					case 1 :
						lArrayID = getAllCodeII();
						break;
					case 2 :
						lArrayID = getAllCodeIII();
						break;
					//add  by wwcheng 10-01-11
					case 3 :
						lArrayID = getAllCodeIV();
						break;
					case 4 :
						lArrayID = getAllCodeV();
						break;
					case 5 :
						lArrayID = getAllCodeVI();
						break;
					case 6 :
						lArrayID = getAllCodeVII();
						break;
					case 7 :
						lArrayID = getAllCodeVIII();
						break;
					case 8 :
						lArrayID = getAllCodeIVV();
						break;
					case 9 :
						lArrayID = getAllCodeIIVV();
						break;
					case 10 :
						lArrayID = getAllCodeIIIVV();
						break;
					case 11 :
						lArrayID = getAllCodeVVV();
						break;
					case 12 :
						lArrayID = getAllCodeVVVI();
						break;
					case 13 :
						lArrayID = getAllCodeVVVII();
						break;
					case 14 :
						lArrayID = getAllCodeVVVIII();
						break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++)
				{
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank, false);//��������
			}
			catch (Exception ex)
			{
				System.out.println(ex.toString());
			}
		}
		
	}

	public static class StatusType
	{
		//
		public static final long UNCHECK = 1; //δ���
		public static final long CHECKED = 2; //�����
		
		//public static final long C = 5; //�����
		//public static final long D = -2; //�Ѿܾ�
		//����ɾ��״̬
		public static final long E = 0; //��ɾ��
		
		
		public static final String getName(long lCode)
		{
			String strReturn = ""; //��ʼ������ֵ
			switch ((int) lCode)
			{
				case (int) UNCHECK :
					strReturn = "δ���";
					break;
				case (int) CHECKED :
					strReturn = "�����";
					break;
//				case (int) C :
//					strReturn = "�����";
//					break;
//				case (int) D :
//					strReturn = "�Ѿܾ�";
//					break;
		
			}
			return strReturn;
		}
		public static final long[] getAllCode()
		{
			long[] lTemp = { UNCHECK, CHECKED };
			return lTemp;
		}
		public static final long[] getAllApproveCode()
		{
			long[] lTemp = { CHECKED };
			return lTemp;
		}
		public static final long[] getAllSelectCode()
		{
			long[] lTemp = { UNCHECK };
			return lTemp;
		}
//		public static final long[] getAllSelectCode2()
//		{
//			long[] lTemp = { A, B, C ,D};
//			return lTemp;
//		}
		public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
		{
			long[] lArrayID = null;
			String[] strArrayName = null;
			try
			{
				switch (nType)
				{
					case 0 :
						lArrayID = getAllCode();
						break;
					case 1 :
						lArrayID = getAllApproveCode();
						break;
					case 2 :
						lArrayID = getAllSelectCode();
						break;
//					case 3 :
//						lArrayID = getAllSelectCode2();
//						break;
						

				}
				strArrayName = new String[lArrayID.length];
				
				for (int i = 0; i < lArrayID.length; i++)
				{
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
			}
			catch (Exception ex)
			{
				System.out.println(ex.toString());
			}
		}
	}


	public static class WarrantApplyStatus
	{
		public static final long REFUSE = -2; //�Ѿܾ�
		public static final long INVALID = 0; //��Ч(ɾ��)
		public static final long SAVE = 1; //�ѵǼ�
		public static final long SUBMIT = 2; //���ύ
		public static final long CHECKING = 3; //�����
		public static final long CHECK = 4; //�����
		public static final long BECOME_INVALID = 5;//��ʧЧ
		
		public static final String getName(long lCode)
		{
			String strReturn = ""; //��ʼ������ֵ
			switch ((int) lCode)
			{
				case (int) SAVE :strReturn = "�ѱ���"; break;
				case (int) SUBMIT   :strReturn = "���ύ";   break;
				case (int) CHECK   :strReturn = "�����";   break;
				case (int) REFUSE   :strReturn = "�Ѿܾ�";   break;
				case (int) BECOME_INVALID   :strReturn = "��ʧЧ";   break;
				case (int) CHECKING   :strReturn = "�����";   break;
			}
			return strReturn;
		}
		public static final long[] getAllCodeI()//Ȩ֤������룭��ѯ����Ʒ��Ϣ����Ʒ״̬
		{
			long[] longTemp = {SAVE};
			return longTemp;
		}
		public static final long[] getAllCodeII()//����Ʒ��Ϣά������ѯ����Ʒ��Ϣ����Ʒ״̬
		{
			long[] longTemp = {SUBMIT,CHECKING};
			return longTemp;
		}
		public static final long[] getAllCodeIII()
		{
			long[] longTemp = {SAVE,SUBMIT,CHECK,CHECKING,REFUSE};
			return longTemp;
		}
		public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
		{
			long[] lArrayID = null;
			String[] strArrayName = null;
			try
			{
				switch (nType)
				{
					case 0 :
						lArrayID = getAllCodeI();
						break;
					case 1 :
						lArrayID = getAllCodeII();
						break;	
					case 2 :
						lArrayID = getAllCodeIII();
						break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++)
				{
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
			}
			catch (Exception ex)
			{
				System.out.println(ex.toString());
			}
		}
    }


	public static class WarrantApplyType
	{
		
		public static final long  RUKU_APPLY     = Constant.ApprovalAction.DBPGL_1; //���
		public static final long  CHUKU_APPLY    = Constant.ApprovalAction.DBPGL_2;	//����
		public static final long  CHUJIE_APPLY   = Constant.ApprovalAction.DBPGL_3;	//����
		public static final long  GUIHUAN_APPLY  = Constant.ApprovalAction.DBPGL_4;	//�黹
		
		public static final String getName(long lCode)throws Exception
		{
			return Constant.ApprovalAction.getName(lCode)+"����";
		}
		public static final long[] getAllCode()
		{
			long[] longTemp = {RUKU_APPLY,CHUKU_APPLY,CHUJIE_APPLY,GUIHUAN_APPLY};
			return longTemp;
		}
		public static final long[] getRUKU_APPLYCode()
		{
			long[] longTemp = {RUKU_APPLY};
			return longTemp;
		}
		public static final long[] getCHUKU_APPLYCode()
		{
			long[] longTemp = {CHUKU_APPLY};
			return longTemp;
		}
		public static final long[] getCHUJIE_APPLYCode()
		{
			long[] longTemp = {CHUJIE_APPLY};
			return longTemp;
		}
		public static final long[] getGUIHUAN_APPLYCode()
		{
			long[] longTemp = {GUIHUAN_APPLY};
			return longTemp;
		}
		public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
		{
			long[] lArrayID = null;
			String[] strArrayName = null;
			try
			{
				switch (nType)
				{
					case 0 :
						lArrayID = getAllCode();
						break;
					case (int) RUKU_APPLY:
						lArrayID = getRUKU_APPLYCode();
						break;
					case (int) CHUKU_APPLY :
						lArrayID = getCHUKU_APPLYCode();
						break;
					case (int) CHUJIE_APPLY :
						lArrayID = getCHUJIE_APPLYCode();
						break;
					case (int) GUIHUAN_APPLY :
						lArrayID = getGUIHUAN_APPLYCode();
						break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++)
				{
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
			}
			catch (Exception ex)
			{
				System.out.println(ex.toString());
			}
		}
    }


		//���ſͻ�����״̬
	public static class LoanCreditgrade
	{

		public static final long SAVE = 1; //���Ƶ�
		public static final long YISHIYONG = 2; //�ѱ���
		public static final long YIGUOQI = 3; //��ʧЧ
		public static final long SHENPIZHONG = 4; //������
		public static final long YISHENPI = 5; //������
		public static final long YIFUHE = 7; //����Ч
		public static final String getName(long lCode)
		{
			String strReturn = ""; //��ʼ������ֵ
			switch ((int) lCode)
			{
				case (int) SAVE :
					strReturn = "�ѱ���";
					break;
				case (int) YISHIYONG :
					strReturn = "�ѱ���";
					break;
				case (int) YIGUOQI :
					strReturn = "��ʧЧ";
				    break;
				case (int) YIFUHE :
					strReturn = "����Ч";
				    break;
				case (int) SHENPIZHONG :
					strReturn = "������";
				    break;
				case (int) YISHENPI :
					strReturn = "����Ч";
					break;
			}
			return strReturn;
		}

		public static final long[] getAllCode()
		{
			long[] lTemp = { SAVE, YISHIYONG, YIGUOQI,YIFUHE,SHENPIZHONG,YISHENPI };
			return lTemp;
		}
		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant.getAllCode(
					"com.iss.itreasury.loan.util.LOANConstant$LoanCreditgrade", officeID,
					currencyID);
		}         
		/**
		 * ��ʾ�����б�
		 * 
		 * @param out
		 * @param strControlName,
		 *            �ؼ�����
		 * @param nType���ؼ����ͣ�0����ʾȫ������
		 * @param lSelectValue
		 * @param isNeedAll���Ƿ���Ҫ��ȫ���
		 * @param isNeedBlank
		 *            �Ƿ���Ҫ�հ���
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
		{
			long[] lArrayID = null;
			String[] strArrayName = null;
			try
			{
				switch (nType)
				{
					case 0 :
						lArrayID = getAllCode();
						break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++)
				{
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
			}
			catch (Exception ex)
			{
				System.out.println(ex.toString());
			}
		}
		public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty,long lOfficeID,long lCurrencyID)
		{
			long[] lArrayID = null;
			String[] strArrayName = null;
			try
			{
				switch (nType)
				{
					case 0 :
						lArrayID = getAllCode(lOfficeID,lCurrencyID);
						break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++)
				{
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
			}
			catch (Exception ex)
			{
				System.out.println(ex.toString());
			}
		}

	}
		

	//������鱨�棺��ʾ�ꡢ����
	public static class YearList
	{
		//
		public static final long A = 2009; //2009
		public static final long B = 2010; //2010
		public static final long C = 2011; //2011
		public static final long D = 2012; //2012
		public static final long E = 2013; //2013
		public static final long F = 2014; //2014
		public static final long G = 2015; //2015
		public static final long H = 2016; //2016
		
		public static final String getName(long lCode)
		{
			String strReturn = ""; //��ʼ������ֵ
			switch ((int) lCode)
			{
				case (int) A :
					strReturn = "2009";
					break;
				case (int) B :
					strReturn = "2010";
					break;
				case (int) C :
					strReturn = "2011";
					break;
				case (int) D :
					strReturn = "2012";
					break;
				case (int) E :
					strReturn = "2013";
					break;
				case (int) F :
					strReturn = "2014";
					break;
				case (int) G :
					strReturn = "2015";
					break;
				case (int) H :
					strReturn = "2016";
					break;
					
		
			}
			return strReturn;
		}
		public static final long[] getAllCode()
		{
			long[] lTemp = { A, B ,C ,D ,E,F,G,H };
			return lTemp;
		}
		
		public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
		{
			long[] lArrayID = null;
			String[] strArrayName = null;
			try
			{
				switch (nType)


				{
					case 0 :
						lArrayID = getAllCode();
						break;

				}
				strArrayName = new String[lArrayID.length];
				
				for (int i = 0; i < lArrayID.length; i++)
				{
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank, false);
			}
			catch (Exception ex)
			{
				System.out.println(ex.toString());
			}
		}
	}
	
	/**
	 * ��鷶Χ ���� ��
	 * @author lllliu
	 *
	 */
	public static class CheckSpace
	{
		public static final long JANUARY = 1; 
		public static final long FEBRUARY = 2;
		public static final long MARCH = 3; 
		public static final long APRIL = 4; 
		public static final long MAY = 5; 
		public static final long JUNE = 6; 
		public static final long JULY = 7; 
		public static final long AUGUST = 8; 
		public static final long SEPTEMBER = 9; 
		public static final long OCTOBER = 10;
		public static final long NOVEMBER = 11; 
		public static final long DECEMBER = 12; 
		
		public static final long ONEQUARTER = 14;
		public static final long TWOQUARTER = 15;
		public static final long THREEQUARTER = 16; 
		public static final long FOURQUARTER = 17;
		
		public static final String getName(long lCode)
		{
			String strReturn = ""; //��ʼ������ֵ
			switch ((int) lCode)

			{
				case (int) ONEQUARTER :
					strReturn = "��һ����";
					break;
				case (int) TWOQUARTER :
					strReturn = "�ڶ�����";
					break;
				case (int) THREEQUARTER :
					strReturn = "��������";
					break;
				case (int) FOURQUARTER :
					strReturn = "���ļ���";
					break;
					
				case (int) JANUARY :
					strReturn = "1";
					break;
				case (int) FEBRUARY :
					strReturn = "2";
					break;
				case (int) MARCH :
					strReturn = "3";
					break;
				case (int) APRIL :
					strReturn = "4";
					break;
				case (int) MAY :
					strReturn = "5";
					break;
				case (int) JUNE :
					strReturn = "6";
					break;
				case (int) JULY :
					strReturn = "7";
					break;
				case (int) AUGUST :
					strReturn = "8";
					break;
				case (int) SEPTEMBER :
					strReturn = "9";
					break;
				case (int) OCTOBER :
					strReturn = "10";
					break;
				case (int) NOVEMBER :
					strReturn = "11";
					break;
				case (int) DECEMBER :
					strReturn = "12";
					break;
				
			}
			return strReturn;
		}

		public static final long[] getAllQuArterCode()
		{
			long[] lTemp = { ONEQUARTER, TWOQUARTER, THREEQUARTER,FOURQUARTER};
			return lTemp;
		}
		public static final long[] getAllMonthCode()
		{
			long[] lTemp = {JANUARY,FEBRUARY,MARCH,APRIL,MAY,JUNE,JULY,AUGUST,SEPTEMBER,OCTOBER,NOVEMBER,DECEMBER};
			return lTemp;
		}
		public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
		{
			long[] lArrayID = null;
			String[] strArrayName = null;
			try
			{
				switch (nType)


				{
					case 1 :
						lArrayID = getAllQuArterCode();
						break;
					case 2 :
						lArrayID = getAllMonthCode();
						break;
				}
				strArrayName = new String[lArrayID.length];
				
				for (int i = 0; i < lArrayID.length; i++)
				{
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank, false);
			}
			catch (Exception ex)
			{
				System.out.println(ex.toString());
			}
		}
	  
	}

	/**
	 * ������Ƶ��
	 * @author lllliu
	 *
	 */
	public static class CheckFrequency
	{
		//�ʲ��ع���ͬ״̬
		public static final long QUARTER = 1; //ÿ����
		public static final long MONTH = 2; //ÿ��
		public static final long TEMPORARY = 3; //��ʱ
		public static final String getName(long lCode)
		{
			String strReturn = ""; //��ʼ������ֵ
			switch ((int) lCode)
			{
				case (int) QUARTER :
					strReturn = "ÿ����";
					break;
				case (int) MONTH :
					strReturn = "ÿ��";
					break;
				case (int) TEMPORARY :
					strReturn = "��ʱ";
					break;



			}
			return strReturn;
		}
		public static final long[] getAllCode()
		{
			long[] lTemp = { QUARTER, MONTH, TEMPORARY };
			return lTemp;
		}
		/**
		 * ��ʾ�����б�
		 *
		 * @param out
		 * @param strControlName,
		 *            �ؼ�����
		 * @param nType���ؼ����ͣ�0����ʾȫ������
		 * @param lSelectValue
		 * @param isNeedAll���Ƿ���Ҫ��ȫ���
		 * @param isNeedBlank
		 *            �Ƿ���Ҫ�հ���
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
		{
			long[] lArrayID = null;
			String[] strArrayName = null;
			try
			{
				switch (nType)
				{
					case 0 :
						lArrayID = getAllCode();
						break;



				}
				strArrayName = new String[lArrayID.length];
				
				for (int i = 0; i < lArrayID.length; i++)
				{
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
			}
			catch (Exception ex)
			{
				System.out.println(ex.toString());
			}
		}

	}
//	�������(��)
	public static class FinancialAnalysisYear
	{

		public static final long q = 2001; //2001
		public static final long w = 2002; //2002
		public static final long e = 2003; //2003
		public static final long r = 2004; //2004
		public static final long t = 2005; //2005
		public static final long y = 2006; //2006
		public static final long u = 2007; //2007
		public static final long i = 2008; //2008
		public static final long o = 2009; //2009
		public static final long a = 2010; //2010
		public static final long s = 2011; //2011
		public static final long d = 2012; //2012
		public static final long f = 2013; //2013
		public static final long g = 2014; //2014
		public static final long h = 2015; //2015
		public static final long j = 2016; //2016
		public static final long k = 2017; //2017
		public static final long l = 2018; //2018
		
		public static final String getName(long lCode)
		{
			String strReturn = ""; //��ʼ������ֵ
			switch ((int) lCode)
			{
				case (int) q :
					strReturn = "2001";
					break;
				case (int) w :
					strReturn = "2002";
					break;
				case (int) e :
					strReturn = "2003";
					break;
				case (int) r :
					strReturn = "2004";
					break;
				case (int) t :
					strReturn = "2005";
					break;
				case (int) y :
					strReturn = "2006";
					break;
				case (int) u :
					strReturn = "2007";
					break;
				case (int) i :
					strReturn = "2008";
					break;
				case (int) o :
					strReturn = "2009";
					break;
				case (int) a :
					strReturn = "2010";
					break;
				case (int) s :
					strReturn = "2011";
					break;
				case (int) d :
					strReturn = "2012";
					break;
				case (int) f :
					strReturn = "2013";
					break;
				case (int) g :
					strReturn = "2014";
					break;
				case (int) h :
					strReturn = "2015";
					break;
				case (int) j :
					strReturn = "2016";
					break;
				case (int) k :
					strReturn = "2017";
					break;
				case (int) l :
					strReturn = "2018";
					break;
			}
			return strReturn;
		}

		public static final long[] getAllCode()
		{
			long[] lTemp = { q, w, e,r,t,y,u,i,o,a,s,d,f,g,h,j,k,l };
			return lTemp;
		}
		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant.getAllCode(
					"com.iss.itreasury.loan.util.LOANConstant$FinancialAnalysisYear", officeID,
					currencyID);
		}         
		/**
		 * ��ʾ�����б�
		 * 
		 * @param out
		 * @param strControlName,
		 *            �ؼ�����
		 * @param nType���ؼ����ͣ�0����ʾȫ������
		 * @param lSelectValue
		 * @param isNeedAll���Ƿ���Ҫ��ȫ���
		 * @param isNeedBlank
		 *            �Ƿ���Ҫ�հ���
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
		{
			long[] lArrayID = null;
			String[] strArrayName = null;
			try
			{
				switch (nType)
				{
					case 0 :
						lArrayID = getAllCode();
						break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++)
				{
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
			}
			catch (Exception ex)
			{
				System.out.println(ex.toString());
			}
		}
		public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty,long lOfficeID,long lCurrencyID)
		{
			long[] lArrayID = null;
			String[] strArrayName = null;
			try
			{
				switch (nType)
				{
					case 0 :
						lArrayID = getAllCode(lOfficeID,lCurrencyID);
						break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++)
				{
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
			}
			catch (Exception ex)
			{
				System.out.println(ex.toString());
			}
		}

	}
	
//	�������(��)  
	public static class FinancialAnalysisYue
	{

		public static final long q = 1; //2001
		public static final long w = 2; //2002
		public static final long e = 3; //2003
		public static final long r = 4; //2004
		public static final long t = 5; //2005
		public static final long y = 6; //2006
		public static final long u = 7; //2007
		public static final long i = 8; //2008
		public static final long o = 9; //2009
		public static final long a = 10; //2010
		public static final long s = 11; //2011
		public static final long d = 12; //2012
		
		
		public static final String getName(long lCode)
		{
			String strReturn = ""; //��ʼ������ֵ
			switch ((int) lCode)
			{
				case (int) q :
					strReturn = "1";
					break;
				case (int) w :
					strReturn = "2";
					break;
				case (int) e :
					strReturn = "3";
					break;
				case (int) r :
					strReturn = "4";
					break;
				case (int) t :
					strReturn = "5";
					break;
				case (int) y :
					strReturn = "6";
					break;
				case (int) u :
					strReturn = "7";
					break;
				case (int) i :
					strReturn = "8";
					break;
				case (int) o :
					strReturn = "9";
					break;
				case (int) a :
					strReturn = "10";
					break;
				case (int) s :
					strReturn = "11";
					break;
				case (int) d :
					strReturn = "12";
					break;
				
			}
			return strReturn;
		}

		public static final long[] getAllCode()
		{
			long[] lTemp = { q, w, e,r,t,y,u,i,o,a,s,d };
			return lTemp;
		}
		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant.getAllCode(
					"com.iss.itreasury.loan.util.LOANConstant$FinancialAnalysisYue", officeID,
					currencyID);
		}         
		/**
		 * ��ʾ�����б�
		 * 
		 * @param out
		 * @param strControlName,
		 *            �ؼ�����
		 * @param nType���ؼ����ͣ�0����ʾȫ������
		 * @param lSelectValue
		 * @param isNeedAll���Ƿ���Ҫ��ȫ���
		 * @param isNeedBlank
		 *            �Ƿ���Ҫ�հ���
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
		{
			long[] lArrayID = null;
			String[] strArrayName = null;
			try
			{
				switch (nType)
				{
					case 0 :
						lArrayID = getAllCode();
						break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++)
				{
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank, false);
			}
			catch (Exception ex)
			{
				System.out.println(ex.toString());
			}
		}
		public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty,long lOfficeID,long lCurrencyID)
		{
			long[] lArrayID = null;
			String[] strArrayName = null;
			try
			{
				switch (nType)
				{
					case 0 :
						lArrayID = getAllCode(lOfficeID,lCurrencyID);
						break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++)
				{
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
			}
			catch (Exception ex)
			{
				System.out.println(ex.toString());
			}
		}

	}
	/**
	 * ������鱨������״̬
	 * @author fulihe
	 *
	 */
	public static class AfterCreditStatus {
		
		public static final long SUBMIT = 1; // �ѱ���

		
		
		public static final long CHECK = 3; // ������

		public static final long REFUSE = -2; // �Ѿܾ�
        
		public static final long INVALID = -1;//ȡ������
		public static final long CANCELAPPLY = 0; //ȡ������
		
		public static final long APPROVALING = 20; //������
		
		public static final String getName(long lCode) {
			String strReturn = ""; // ��ʼ������ֵ
			switch ((int) lCode) {
			case (int) SUBMIT:
				strReturn = "�ѱ���";
				break;
			case (int) CHECK:
				strReturn = "������";
				break;
			case (int) REFUSE:
				strReturn = "�Ѿܾ�";
				break;
			
			case (int) CANCELAPPLY:
				strReturn = "ȡ������";
				break;
			
			case (int) APPROVALING:
			strReturn = "������";
			break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = new long[5];
			lTemp[0]= SUBMIT;
			lTemp[1]= CHECK;
			lTemp[2]= REFUSE;
			lTemp[3]=CANCELAPPLY;
			lTemp[4]=APPROVALING;
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant
					.getAllCode(
							"com.iss.itreasury.loan.util.LOANConstant$AfterCreditStatus",
							officeID, currencyID);
		}

	}
	
	//���������ʻ�����
	public static class LoanClientType{
		public static final long INTERIOR = 1;  //�ڲ��ͻ�
		public static final long EXTERIOR = 2;  //�ⲿ�ͻ�
	}
	
	//��ҵ�жһ�Ʊ֪ͨ״̬ wjdu
	public static class InformStatus
	{
		public static final long DELETE = 0;//ɾ��0
		public static final long SUBMIT = 2;//�ύ1
		public static final long SAVE = 1;//����2
		public static final long CHECKING = 5;//�����(�����)3(����֪ͨ����û��)
		public static final long CHECK = 3;//�����(���ͨ��)4
		public static final long REFUSE = -2;//�ܾ�5
		public static final long USED = 4; //"��ʹ��";
		public static final long FINISH = 6; //"�ѽ���";7(����֪ͨ����û��)
		public static final long RETURN = 7;//�˻��޸�
		public static final String getName(long lCode)
		{
			String strReturn = ""; //��ʼ������ֵ
			switch ((int) lCode)
			{
				case (int)	 DELETE :strReturn = "ɾ��"; break;
				case (int)   SUBMIT :strReturn = "���ύ";   break;
				case (int)   SAVE  :strReturn = "׫д";   break;
				case (int)   CHECKING  :strReturn = "���ύ";      break;
				case (int)   CHECK :strReturn = "�����";      break;
				case (int)   REFUSE :strReturn = "�ܾ�";      break;
				//case (int)   UPDATE :strReturn = "�˻��޸�";      break;
				case (int)   USED :strReturn = "��ʹ��";      break;
				case (int)   FINISH :strReturn = "�ѽ���";      break;
			}
			return strReturn;
		}
		
	}
	
	public static class RemindType
	{
		public static final String PLAN = "plan";//���ݻ���ƻ�����
		public static final String NOTICE = "notice";//���ݻ���֪ͨ������
		
	}
	

}