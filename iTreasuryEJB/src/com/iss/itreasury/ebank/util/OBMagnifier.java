/*
* OBMagnifier.java
* Created by yanliu 
* 2002��12��8��
 */
package com.iss.itreasury.ebank.util;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.net.URLEncoder;

import com.iss.itreasury.dataentity.*;
import com.iss.itreasury.ebank.obfinanceinstr.dao.*;
import com.iss.itreasury.ebank.obfinanceinstr.dataentity.*;
import com.iss.itreasury.settlement.bizdelegation.AccountSystemDelegation;
import com.iss.itreasury.settlement.dataentity.AccountSystemInfo;
import com.iss.itreasury.settlement.util.*;
import com.iss.itreasury.settlement.util.NameRef;
import com.iss.itreasury.loan.util.*;
import com.iss.itreasury.util.*;

import javax.servlet.jsp.JspWriter;
import com.iss.itreasury.util.Constant;
public class OBMagnifier
{
	private static String ZOOMERRORMSG = "�Ŵ󾵲������ô���!";
	/**
		 * �����տ�����˺ŷŴ󾵣��㣩
		 * @author yanliu
		 * @param out
		 * @param lClientID �ͻ�ID
		 * @param lCurrencyID ����ID
		 * @param strRelativeForm ������
		 * @param sBankAccountCode �˺ţ���ʼֵ��
		 * @param strFormName ��ҳ������
		 * @param strCtrlName �Ŵ����ؼ�����
		 * @param strTitle �Ŵ�����)
		 * @param strClientNo �ͻ����(��ʶֵ)
		 * @param strFirstTD ��һ��TD������
		 * @param strSecondTD �ڶ���TD������
		 * @param sNextControlsClient ��һ������������ý���Ŀؼ�
		 * @param strRtnClientNameCtrl ����ֵ���ͻ����ƣ���Ӧ�Ŀؼ���
		 * @param isBlurQuery �Ƿ�֧���տ���Ƶ�ģ����ѯ
		 */
	public static void createPayeeAccountNOCtrl(
		JspWriter out,
		long lCurrencyID,
		long lClientID,
		String strRelativeForm1,
		String strRelativeForm2,
		String strRelativeForm3,
		String strRelativeForm4,
		String strRelativeForm5,
		String strFormName,
		String sBankAccountCode,
		String strCtrlName,
		String strTitle,
		String strFirstTD,
		String strSecondTD)
	{
		createPayeeAccountNOCtrl(
			out,
			lCurrencyID,
			lClientID,
			strRelativeForm1,
			strRelativeForm2,
			strRelativeForm3,
			strRelativeForm4,
			strRelativeForm5,
			strFormName,
			sBankAccountCode,
			strCtrlName,
			strTitle,
			strFirstTD,
			strSecondTD,
			false);
	}
	public static void createPayeeAccountNOCtrl(
		JspWriter out,
		long lCurrencyID,
		long lClientID,
		String strRelativeForm1,
		String strRelativeForm2,
		String strRelativeForm3,
		String strRelativeForm4,
		String strRelativeForm5,
		String strFormName,
		String sBankAccountCode,
		String strCtrlName,
		String strTitle,
		String strFirstTD,
		String strSecondTD,
		boolean isBlurQuery)
	{
		String strMagnifierName = URLEncoder.encode("�տ�˺�");
		String strMainProperty = " maxlength='50' value='" + sBankAccountCode + "'";
		String strPrefix = "";
		String[] strMainNames = { strCtrlName + "Ctrl", strRelativeForm1, strRelativeForm2, strRelativeForm3, strRelativeForm4, strRelativeForm5};
		String[] strMainFields = { "spayeeacctno", "ID", "sPayeeName", "SPAYEEPROV", "SPAYEECITY", "sPayeeBankName" };
		
		String[]  strReturnNames = { strCtrlName+"hiddenValue" }; //�տ
		
		String[] strReturnFields = {"spayeeacctno"
		};
		String[] strReturnValues = {""
		};
		String[] strDisplayNames = { URLEncoder.encode("�տ�˺�"), URLEncoder.encode("�տ����")};
		String[] strDisplayFields = { "spayeeacctno", "sPayeeName" };
		int nIndex = 0;
		String strSQL = "getPayeeAccountNOSQL(" + isBlurQuery + "," + lClientID + "," + lCurrencyID + "," + strCtrlName + "Ctrl.value," + strRelativeForm2 + ".value" + ")";
		String[] strNextControls = { "dAmount" };
		try
		{
			showZoomCtrl(
				out,
				strMagnifierName,
				strFormName,
				strPrefix,
				strMainNames,
				strMainFields,
				strReturnNames,
				strReturnFields,
				strReturnValues,
				strDisplayNames,
				strDisplayFields,
				nIndex,
				strMainProperty,
				strSQL,
				strNextControls,
				strTitle,
				strFirstTD,
				strSecondTD,
				
				false,
				false);
			
		}
		catch (Exception e)
		{
		}
	}
	/**
		 * �����տ�����˺ŷŴ󾵣���ת��
		 * @author yanliu
		 * @param out
		 * @param lClientID �ͻ�ID
		 * @param lCurrencyID ����ID
		 * @param strRelativeForm ������
		 * @param sBankAccountCode �˺ţ���ʼֵ��
		 * @param strFormName ��ҳ������
		 * @param strCtrlName �Ŵ����ؼ�����
		 * @param strTitle �Ŵ�����)
		 * @param strClientNo �ͻ����(��ʶֵ)
		 * @param strFirstTD ��һ��TD������
		 * @param strSecondTD �ڶ���TD������
		 * @param sNextControlsClient ��һ������������ý���Ŀؼ�
		 * @param strRtnClientNameCtrl ����ֵ���ͻ����ƣ���Ӧ�Ŀؼ���
		 * @param isBlurQuery �Ƿ�֧���տ���Ƶ�ģ����ѯ
		 */
	public static void createPayeeBankNOCtrl(
		JspWriter out,
		long lCurrencyID,
		long lClientID,
		String strRelativeForm1,
		String strRelativeForm2,
		String strFormName,
		String sBankAccountCode,
		String strCtrlName,
		String strTitle,
		String strFirstTD,
		String strSecondTD)
	{
		createPayeeBankNOCtrl(out, lCurrencyID, lClientID, strRelativeForm1, strRelativeForm2, strFormName, sBankAccountCode, strCtrlName, strTitle, strFirstTD, strSecondTD, true);
	}
	/**
	 * �����տ�����˺ŷŴ󾵣���ת��(����ר��)
	 * @author 
	 * @param out
	 * @param lClientID �ͻ�ID
	 * @param lCurrencyID ����ID
	 * @param strRelativeForm ������
	 * @param sBankAccountCode �˺ţ���ʼֵ��
	 * @param strFormName ��ҳ������
	 * @param strCtrlName �Ŵ����ؼ�����
	 * @param strTitle �Ŵ�����)
	 * @param strClientNo �ͻ����(��ʶֵ)
	 * @param strFirstTD ��һ��TD������
	 * @param strSecondTD �ڶ���TD������
	 * @param sNextControlsClient ��һ������������ý���Ŀؼ�
	 * @param strRtnClientNameCtrl ����ֵ���ͻ����ƣ���Ӧ�Ŀؼ���
	 * @param isBlurQuery �Ƿ�֧���տ���Ƶ�ģ����ѯ
	 */
public static void createInterPayeeBankNOCtrl(
	JspWriter out,
	long lCurrencyID,
	long lClientID,
	String strRelativeForm1,
	String strRelativeForm2,
	String strFormName,
	String sBankAccountCode,
	String strCtrlName,
	String strTitle,
	String strFirstTD,
	String strSecondTD)
{
	createInterPayeeBankNOCtrl(out, lCurrencyID, lClientID, strRelativeForm1, strRelativeForm2, strFormName, sBankAccountCode, strCtrlName, strTitle, strFirstTD, strSecondTD, true);
}

	public static void createPayeeBankNOCtrl(
		JspWriter out,
		long lCurrencyID,
		long lClientID,
		String strRelativeForm1,
		String strRelativeForm2,
		String strFormName,
		String sBankAccountCode,
		String strCtrlName,
		String strTitle,
		String strFirstTD,
		String strSecondTD,
		boolean isBlurQuery)
	{
		String strMagnifierName = URLEncoder.encode("�տ�˺�");
		String strMainProperty = " maxlength='40' value='" + sBankAccountCode + "'";
		String strPrefix = "";
		String[] strMainNames = { strCtrlName + "Ctrl", strRelativeForm1, strRelativeForm2 };
		String[] strMainFields = { "spayeeacctno", "ID", "sPayeeName" };
		String[] strReturnNames = { "sPayeeBankAccountNO","hid"+strCtrlName +"Ctrl","hid"+strRelativeForm2 };
		String[] strReturnFields = { "accountBankNo","spayeeacctno", "sPayeeName" };
		String[] strReturnValues = { "","","" };
		String[] strDisplayNames = { URLEncoder.encode("�տ�˺�"), URLEncoder.encode("�˻�����")};
		String[] strDisplayFields = { "displayAccountNo", "sPayeeName"};
		int nIndex = 0;
		String strSQL = "getPayeeBankNOSQL(" + isBlurQuery + "," + lClientID + "," + lCurrencyID + "," + strCtrlName + "Ctrl.value," + strRelativeForm2 + ".value" + ")";
		String[] strNextControls = { "dAmount" };
		try
		{
			showZoomCtrl(
				out,
				strMagnifierName,
				strFormName,
				strPrefix,
				strMainNames,
				strMainFields,
				strReturnNames,
				strReturnFields,
				strReturnValues,
				strDisplayNames,
				strDisplayFields,
				nIndex,
				strMainProperty,
				strSQL,
				strNextControls,
				strTitle,
				strFirstTD,
				strSecondTD,
				false,
				false);
		}
		catch (Exception e)
		{
		}
	}
	
	public static void createInterPayeeBankNOCtrl(
			JspWriter out,
			long lCurrencyID,
			long lClientID,
			String strRelativeForm1,
			String strRelativeForm2,
			String strFormName,
			String sBankAccountCode,
			String strCtrlName,
			String strTitle,
			String strFirstTD,
			String strSecondTD,
			boolean isBlurQuery)
		{
			String strMagnifierName = URLEncoder.encode("�տ�˺�");
			String strMainProperty = " maxlength='40' value='" + sBankAccountCode + "'";
			String strPrefix = "";
			String[] strMainNames = { strCtrlName + "Ctrl", strRelativeForm1, strRelativeForm2 };
			String[] strMainFields = { "spayeeacctno", "ID", "sPayeeName" };
			String[] strReturnNames = { "sPayeeBankAccountNO","hid"+strCtrlName +"Ctrl","hid"+strRelativeForm2 };
			String[] strReturnFields = { "accountBankNo","spayeeacctno", "sPayeeName" };
			String[] strReturnValues = { "","","" };
			String[] strDisplayNames = { URLEncoder.encode("�տ�˺�"), URLEncoder.encode("�˻�����")};
			String[] strDisplayFields = { "displayAccountNo", "sPayeeName"};
			int nIndex = 0;
			String strSQL = "getInterPayeeBankNOSQL(" + isBlurQuery + "," + lClientID + "," + lCurrencyID + "," + strCtrlName + "Ctrl.value," + strRelativeForm2 + ".value" + ")";
			String[] strNextControls = { "dAmount" };
			try
			{
				showZoomCtrl(
					out,
					strMagnifierName,
					strFormName,
					strPrefix,
					strMainNames,
					strMainFields,
					strReturnNames,
					strReturnFields,
					strReturnValues,
					strDisplayNames,
					strDisplayFields,
					nIndex,
					strMainProperty,
					strSQL,
					strNextControls,
					strTitle,
					strFirstTD,
					strSecondTD,
					false,
					false);
			}
			catch (Exception e)
			{
			}
		}
	
	
	/**
	 * �����տ�����˺ŷŴ󾵣���ת��
	 * @author xwhe
	 * @param out
	 * @param m_lOfficeID ���´�ID
	 * @param lCurrencyID ����ID
	 * @param strRelativeForm ������
	 * @param sBankAccountCode �˺ţ���ʼֵ��
	 * @param strFormName ��ҳ������
	 * @param strCtrlName �Ŵ����ؼ�����
	 * @param strTitle �Ŵ�����)
	 * @param strClientNo �ͻ����(��ʶֵ)
	 * @param strFirstTD ��һ��TD������
	 * @param strSecondTD �ڶ���TD������
	 * @param sNextControlsClient ��һ������������ý���Ŀؼ�
	 * @param strRtnClientNameCtrl ����ֵ���ͻ����ƣ���Ӧ�Ŀؼ���
	 * @param isBlurQuery �Ƿ�֧���տ���Ƶ�ģ����ѯ
	 */
public static void createPayeeBankNOCtrl1(
	JspWriter out,
	long lCurrencyID,
	long m_lOfficeID,
	String strRelativeForm1,
	String strRelativeForm2,
	String strFormName,
	String sBankAccountCode,
	String strCtrlName,
	String strTitle,
	String strFirstTD,
	String strSecondTD)
{
	createPayeeBankNOCtrl1(out, lCurrencyID, m_lOfficeID, strRelativeForm1, strRelativeForm2, strFormName, sBankAccountCode, strCtrlName, strTitle, strFirstTD, strSecondTD, false);
}

public static void createPayeeBankNOCtrl1(
	JspWriter out,
	long lCurrencyID,
	long m_lOfficeID,
	String strRelativeForm1,
	String strRelativeForm2,
	String strFormName,
	String sBankAccountCode,
	String strCtrlName,
	String strTitle,
	String strFirstTD,
	String strSecondTD,
	boolean isBlurQuery)
{
	String strMagnifierName = URLEncoder.encode("�տ�˺�");
	String strMainProperty = " maxlength='50' value='" + sBankAccountCode + "'";
	String strPrefix = "";
	String[] strMainNames = { strCtrlName + "Ctrl", strRelativeForm1, strRelativeForm2 };
	String[] strMainFields = { "spayeeacctno", "ID", "sPayeeName" };
	String[] strReturnNames = { "sPayeeBankAccountNO","hid"+strCtrlName +"Ctrl","hid"+strRelativeForm2 };
	String[] strReturnFields = { "accountBankNo","spayeeacctno", "sPayeeName" };
	String[] strReturnValues = { "","","" };
	String[] strDisplayNames = { URLEncoder.encode("�տ�˺�"), URLEncoder.encode("�˻�����")};
	String[] strDisplayFields = { "displayAccountNo", "sPayeeName"};
	int nIndex = 0;
	String strSQL = "getPayeeBankNOSQL1(" + isBlurQuery + "," + m_lOfficeID + "," + lCurrencyID + "," + strCtrlName + "Ctrl.value," + strRelativeForm2 + ".value" + ")";
	String[] strNextControls = { "dAmount" };
	try
	{
		showZoomCtrl(
			out,
			strMagnifierName,
			strFormName,
			strPrefix,
			strMainNames,
			strMainFields,
			strReturnNames,
			strReturnFields,
			strReturnValues,
			strDisplayNames,
			strDisplayFields,
			nIndex,
			strMainProperty,
			strSQL,
			strNextControls,
			strTitle,
			strFirstTD,
			strSecondTD,
			false,
			false);
	}
	catch (Exception e)
	{
	}
}
	/**
	 * ������ͬ�Ŵ�
	 * @param out
	 * @param lOfficeID ���´�ID
	 * @param lCurrencyID ����ID
	 * @param strFormName ��������
	 * @param strCtrlName �Ŵ����ؼ�����
	 * @param strTitle �Ŵ�����
	 * @param lClientID �ͻ�ID(��ʶֵ)
	 * @param lContractID ��ͬID(��ʶֵ)
	 * @param strContractNo ��ͬ��(��ʶֵ)
	 * @param lTransactionType ��������(��ѯ����:��SETTConstant.TransactionType���壬-1 ��ѯ����)
	 * @param lMagnifierType ��ͬ�Ŵ�����(��ѯ����: 1 ҵ����;2 ҵ�񸴺�;)
	 * @param strClientCtrl �ͻ�ID��Ӧ�Ŀؼ����ƣ���ѯʱ������
	 * @param strFirstTD ��һ��TD������
	 * @param strSecondTD �ڶ���TD������
	 * @param strNextControls ��һ������������ý���Ŀؼ�
	 */
	public static void createContractCtrl(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lClientID,
		long lContractID,
		String strContractNo,
		long lTransactionType,
		long lMagnifierType,
		String strClientCtrl,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls)
	{
		String strMagnifierName = URLEncoder.encode("��ͬ");
		String strMainProperty = " maxlength='30' value='" + strContractNo + "'";
		String strPrefix = "";
		String[] strMainNames = { strCtrlName + "Ctrl" };
		String[] strMainFields = { "ContractCode", };
		String[] strReturnNames = { strCtrlName, strCtrlName + "ClientID" };
		String[] strReturnFields = { "ContractID", "ClientID" };
		String[] strReturnValues = { String.valueOf(lContractID), String.valueOf(lClientID)};
		String[] strDisplayNames = { URLEncoder.encode("��ͬ��"), URLEncoder.encode("�ͻ�����")};
		String[] strDisplayFields = { "ContractCode", "ClientName" };
		int nIndex = 0;
		long[] lContractTypeIDs = null;
		//���� ��LOANConstant.LoanType.ZYDQ  ZYZCQ  ZGXEDQ  ZGXEZCQ  YT
		if (lTransactionType == SETTConstant.TransactionType.TRUSTLOANGRANT || lTransactionType == SETTConstant.TransactionType.TRUSTLOANRECEIVE)
		{
//			lContractTypeIDs = new long[] { LOANConstant.LoanType.ZYDQ, LOANConstant.LoanType.ZYZCQ, LOANConstant.LoanType.ZGXEDQ, LOANConstant.LoanType.ZGXEZCQ };
			lContractTypeIDs = new long[] { LOANConstant.LoanType.ZY, LOANConstant.LoanType.ZGXE };
		}
		else if (lTransactionType == SETTConstant.TransactionType.BANKGROUPLOANRECEIVE)
		{
//			lContractTypeIDs = new long[] { LOANConstant.LoanType.YTDQ, LOANConstant.LoanType.YTZCQ };
			lContractTypeIDs = new long[] { LOANConstant.LoanType.YT };
		}
		//ί�� : WT  WTTJTH
		else if (lTransactionType == SETTConstant.TransactionType.CONSIGNLOANGRANT || lTransactionType == SETTConstant.TransactionType.CONSIGNLOANRECEIVE)
		{
//			lContractTypeIDs = new long[] { LOANConstant.LoanType.WT, LOANConstant.LoanType.WTTJTH };
			lContractTypeIDs = new long[] { LOANConstant.LoanType.WT };
		}
		//���� : TX
		else if (lTransactionType == SETTConstant.TransactionType.DISCOUNTGRANT || lTransactionType == SETTConstant.TransactionType.DISCOUNTRECEIVE)
		{
			lContractTypeIDs = new long[] { LOANConstant.LoanType.TX };
		}
		else if (lTransactionType == SETTConstant.TransactionType.INTERESTFEEPAYMENT)
		{
			lContractTypeIDs =
				new long[] {
					LOANConstant.LoanType.ZY,
//					LOANConstant.LoanType.ZYZCQ,
					LOANConstant.LoanType.ZGXE,
//					LOANConstant.LoanType.ZGXEZCQ,
					LOANConstant.LoanType.YT,
//					LOANConstant.LoanType.YTZCQ,
					LOANConstant.LoanType.WT
//					LOANConstant.LoanType.WTTJTH 
					};
		}
		else if (lTransactionType == -1)
		{
			lContractTypeIDs = LOANConstant.LoanType.getAllCode(lOfficeID,lCurrencyID);
		}
		long[] lStatusIDs = null;
		if (lMagnifierType == -1)
		{
			lStatusIDs = LOANConstant.ContractStatus.getAllCode();
		}
		//ҵ����
		else if (lMagnifierType == 1)
		{
			lStatusIDs = new long[] { LOANConstant.ContractStatus.NOTACTIVE, LOANConstant.ContractStatus.ACTIVE, LOANConstant.ContractStatus.EXTEND };
		}
		else if (lMagnifierType == 2)
		{
			lStatusIDs =
				new long[] {
					LOANConstant.ContractStatus.NOTACTIVE,
					LOANConstant.ContractStatus.ACTIVE,
					LOANConstant.ContractStatus.EXTEND,
					LOANConstant.ContractStatus.OVERDUE,
					LOANConstant.ContractStatus.DELAYDEBT,
					LOANConstant.ContractStatus.BADDEBT };
		}
		StringBuffer sbSQL = new StringBuffer(64);
		sbSQL.append("getContractSQL(");
		sbSQL.append(lOfficeID);
		sbSQL.append(",");
		sbSQL.append(lCurrencyID);
		sbSQL.append(",");
		sbSQL.append("'");
		if (lContractTypeIDs != null && lContractTypeIDs.length > 0)
		{
			for (int i = 0; i < lContractTypeIDs.length; i++)
			{
				sbSQL.append(lContractTypeIDs[i]);
				if (i < lContractTypeIDs.length - 1)
				{
					sbSQL.append(",");
				}
			}
		}
		else
		{
			sbSQL.append("-1");
		}
		sbSQL.append("'");
		sbSQL.append(",");
		sbSQL.append("'");
		if (lStatusIDs != null && lStatusIDs.length > 0)
		{
			for (int i = 0; i < lStatusIDs.length; i++)
			{
				sbSQL.append(lStatusIDs[i]);
				if (i < lStatusIDs.length - 1)
				{
					sbSQL.append(",");
				}
			}
		}
		else
		{
			sbSQL.append("-1");
		}
		sbSQL.append("'");
		sbSQL.append(",");
		sbSQL.append(strCtrlName);
		sbSQL.append("Ctrl.value");
		sbSQL.append(",");
		if (strClientCtrl != null && !strClientCtrl.trim().equals(""))
		{
			sbSQL.append(strClientCtrl);
			sbSQL.append(".value)");
		}
		else
		{
			sbSQL.append("-1)");
		}
		//Log.print(sbSQL.toString());
		try
		{
			OBMagnifier.showZoomCtrl(
				out,
				strMagnifierName,
				strFormName,
				strPrefix,
				strMainNames,
				strMainFields,
				strReturnNames,
				strReturnFields,
				strReturnValues,
				strDisplayNames,
				strDisplayFields,
				nIndex,
				strMainProperty,
				sbSQL.toString(),
				strNextControls,
				strTitle,
				strFirstTD,
				strSecondTD,
				false,
				false);
		}
		catch (Exception e)
		{
			//e.printStackTrace();
			Log.print("��ͬ�Ŵ�[" + strCtrlName + "]�쳣��" + e.toString());
		}
	}

	public static void createContractCtrl1(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lClientID,
		long lContractID,
		String strContractNo,
		long lTransactionType,
		long lMagnifierType,
		String strClientCtrl,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls)
	{

		String strMagnifierName = URLEncoder.encode("��ͬ");
		String strMainProperty = " maxlength='30' value='" + strContractNo + "'";
		String strPrefix = "";
		String[] strMainNames = { strCtrlName + "Ctrl" };
		String[] strMainFields = { "ContractCode", };
		String[] strReturnNames = { strCtrlName, strCtrlName + "ClientID" };
		String[] strReturnFields = { "ContractID", "ClientID" };
		String[] strReturnValues = { String.valueOf(lContractID), String.valueOf(lClientID)};
		String[] strDisplayNames = { URLEncoder.encode("��ͬ��"), URLEncoder.encode("�ͻ�����")};
		String[] strDisplayFields = { "ContractCode", "ClientName" };
		int nIndex = 0;
		//long[] lContractTypeIDs = null;
		long[] lStatusIDs = null;
		if (lMagnifierType == -1)
		{
			lStatusIDs = LOANConstant.ContractStatus.getAllCode();
		}
		//ҵ����
		else if (lMagnifierType == 1)
		{
			lStatusIDs = new long[] { LOANConstant.ContractStatus.NOTACTIVE, LOANConstant.ContractStatus.ACTIVE, LOANConstant.ContractStatus.EXTEND };
		}
		else if (lMagnifierType == 2)
		{
			lStatusIDs =
				new long[] {
					LOANConstant.ContractStatus.NOTACTIVE,
					LOANConstant.ContractStatus.ACTIVE,
					LOANConstant.ContractStatus.EXTEND,
					LOANConstant.ContractStatus.OVERDUE,
					LOANConstant.ContractStatus.DELAYDEBT,
					LOANConstant.ContractStatus.BADDEBT };
		}
		StringBuffer sbSQL = new StringBuffer(64);
		sbSQL.append("getContractSQL(");
		sbSQL.append(lOfficeID);
		sbSQL.append(",");
		sbSQL.append(lCurrencyID);
		sbSQL.append(",");
		sbSQL.append("");
		sbSQL.append(strFormName + ".lContractTypeIDs.value");
		sbSQL.append("");
		sbSQL.append(",");
		sbSQL.append("'");
		if (lStatusIDs != null && lStatusIDs.length > 0)
		{
			for (int i = 0; i < lStatusIDs.length; i++)
			{
				sbSQL.append(lStatusIDs[i]);
				if (i < lStatusIDs.length - 1)
				{
					sbSQL.append(",");
				}
			}
		}
		else
		{
			sbSQL.append("-1");
		}
		sbSQL.append("'");
		sbSQL.append(",");
		sbSQL.append(strCtrlName);
		sbSQL.append("Ctrl.value");
		sbSQL.append(",");
		if (strClientCtrl != null && !strClientCtrl.trim().equals(""))
		{
			sbSQL.append(strClientCtrl);
			sbSQL.append(".value)");
		}
		else
		{
			sbSQL.append("-1)");
		}
		//Log.print(sbSQL.toString());
		try
		{
			OBMagnifier.showZoomCtrl(
				out,
				strMagnifierName,
				strFormName,
				strPrefix,
				strMainNames,
				strMainFields,
				strReturnNames,
				strReturnFields,
				strReturnValues,
				strDisplayNames,
				strDisplayFields,
				nIndex,
				strMainProperty,
				sbSQL.toString(),
				strNextControls,
				strTitle,
				strFirstTD,
				strSecondTD,
				false,
				false);
		}
		catch (Exception e)
		{
			//e.printStackTrace();
			Log.print("��ͬ�Ŵ�[" + strCtrlName + "]�쳣��" + e.toString());
		}
	}

	/**
	 * �����ſ����ڷŴ�
	 * @param out
	 * @param lOfficeID ���´�ID
	 * @param lCurrencyID ����ID
	 * @param strFormName ��������
	 * @param strCtrlName �Ŵ����ؼ�����
	 * @param strTitle �Ŵ�����
	 * @param lContractID ��ͬID(��ʶֵ)
	 * @param lPayFormID �ſ�֪ͨ��ID(��ʶֵ)
	 * @param lAccountID �����˻�ID(��ʼֵ)
	 * @param tsPayDate �ſ�����(��ʶֵ)
	 * @param strPayFormNo �ſ�֪ͨ����(��ʶֵ)
	 * @param lPayFormTypeID �ſ�֪ͨ������(��ѯ����:1,���У�2��ί�� 3�����к�ί��)
	 * @param lTypeID �ſ�֪ͨ��ʹ������(�ڲ�״̬��
	 * 		0����ʾȫ����
	 * 		1������/ί�з��š���ҵ����ʹ�ã�
	 * 		2������/ί�з��š���ҵ�񸴺�ʹ�ã�
	 * 		3������/ί���ջء���ҵ����ʹ�ã�
	 * 		4������/ί���ջء���ҵ�񸴺�ʹ�á���
	 * @param strContractCtrl ��ͬID��Ӧ�Ŀؼ����ƣ���ѯʱ������
	 * @param strFirstTD ��һ��TD������
	 * @param strSecondTD �ڶ���TD������
	 * @param strNextControls ��һ������������ý���Ŀؼ�
	 * @param strRtnLoanNoteIDCtrl ����ֵ���ſ�֪ͨ��ID����Ӧ�Ŀؼ���
	 * @param strRtnLoanFormCodeCtrl ����ֵ���ſ�֪ͨ����ţ���Ӧ�Ŀؼ���
	 * @param strRtnStartDateCtrl ����ֵ����ʼ���ڣ���Ӧ�Ŀؼ���
	 * @param strRtnEndDateCtrl ����ֵ���������ڣ���Ӧ�Ŀؼ���
	 * @param strRtnSubAccountIDCtrl ����ֵ�����˻�ID����Ӧ�Ŀؼ���
	 * @param strRtnAccountBalanceCtrl ����ֵ����������Ӧ�Ŀؼ���
	 */
	public static void createPayFormCtrl(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		long lClientID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lContractID,
		long lPayFormID,
		long lAccountID,
		Timestamp tsPayDate,
		String strPayFormNo,
		long lPayFormTypeID,
		long lTypeID,
		String strContractCtrl,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,
		String strRtnLoanNoteIDCtrl,
		String strRtnContractIDCtrl,
		String strRtnStartDateCtrl,
		String strRtnEndDateCtrl,
		String strRtnSubAccountIDCtrl,
		String strRtnAccountBalanceCtrl)
	{
		String strMagnifierName = URLEncoder.encode("�ſ�����");
		String strMainProperty = " maxlength='30' value='" + DataFormat.formatDate(tsPayDate) + "'";
		String strPrefix = "";
		if (strRtnContractIDCtrl == null || strRtnContractIDCtrl.equals(""))
		{
			strRtnContractIDCtrl = "ItIsNotControl";
		}
		if (strRtnStartDateCtrl == null || strRtnStartDateCtrl.equals(""))
		{
			strRtnStartDateCtrl = "ItIsNotControl";
		}
		if (strRtnEndDateCtrl == null || strRtnEndDateCtrl.equals(""))
		{
			strRtnEndDateCtrl = "ItIsNotControl";
		}
		if (strRtnSubAccountIDCtrl == null || strRtnSubAccountIDCtrl.equals(""))
		{
			strRtnSubAccountIDCtrl = "ItIsNotControl";
		}
		String[] strMainNames = { strCtrlName + "Ctrl", strRtnAccountBalanceCtrl, strCtrlName + "rate" ,strRtnContractIDCtrl};
		String[] strMainFields = { "PayDate", "LoanBalance", "rate" ,"ContractID"};
		String[] strReturnNames = { strCtrlName, strCtrlName+"ContractID", strCtrlName + "AccountID" };
		String[] strReturnFields = { "LoanNoteID", "ContractID", "LoanAccountID" };
		String[] strReturnValues = { String.valueOf(lPayFormID), String.valueOf(lContractID), String.valueOf(lAccountID)};
		String[] strDisplayNames = { URLEncoder.encode("�ſ�֪ͨ����"), URLEncoder.encode("�ſ�����"), URLEncoder.encode("�������")};
		String[] strDisplayFields = { "PayFormCode", "PayDate", "LoanBalance" };
		int nIndex = 0;
		long[] lContractTypeIDs = null;
		//���� ��LOANConstant.LoanType.ZYDQ  ZYZCQ  ZGXEDQ  ZGXEZCQ  YT
		if (lPayFormTypeID == 1)
		{
//			lContractTypeIDs =
//				new long[] {
//					LOANConstant.LoanType.ZYDQ,
//					LOANConstant.LoanType.ZYZCQ,
//					LOANConstant.LoanType.ZGXEDQ,
//					LOANConstant.LoanType.ZGXEZCQ,
//					LOANConstant.LoanType.YTDQ,
//					LOANConstant.LoanType.YTZCQ };
			lContractTypeIDs =
				new long[] {
					LOANConstant.LoanType.ZY,
					LOANConstant.LoanType.ZGXE,
					LOANConstant.LoanType.YT };
		}
		//ί�� : WT  WTTJTH
		else if (lPayFormTypeID == 2)
		{
//			lContractTypeIDs = new long[] { LOANConstant.LoanType.WT, LOANConstant.LoanType.WTTJTH };
			lContractTypeIDs = new long[] { LOANConstant.LoanType.WT};
		}
		else if (lPayFormTypeID == 3)
		{
//			lContractTypeIDs =
//				new long[] {
//					LOANConstant.LoanType.ZYDQ,
//					LOANConstant.LoanType.ZYZCQ,
//					LOANConstant.LoanType.ZGXEDQ,
//					LOANConstant.LoanType.ZGXEZCQ,
//					LOANConstant.LoanType.YTDQ,
//					LOANConstant.LoanType.YTZCQ,
//					LOANConstant.LoanType.WT,
//					LOANConstant.LoanType.WTTJTH };
			lContractTypeIDs =
				new long[] {
					LOANConstant.LoanType.ZY,
					LOANConstant.LoanType.ZGXE,
					LOANConstant.LoanType.YT,
					LOANConstant.LoanType.WT };
		}
		else if (lPayFormTypeID == 4)
		{
			lContractTypeIDs = new long[] { LOANConstant.LoanType.YT };
//			lContractTypeIDs = new long[] { LOANConstant.LoanType.YTDQ, LOANConstant.LoanType.YTZCQ, };
		}
		long[] lStatusIDArray = null;
		//���״̬����
		if (lTypeID == 0)
		{
			lStatusIDArray = new long[] { LOANConstant.LoanPayNoticeStatus.CHECK, LOANConstant.LoanPayNoticeStatus.USED };
		}
		else if (lTypeID == 1)
		{
			lStatusIDArray = new long[] { LOANConstant.LoanPayNoticeStatus.CHECK };
		}
		else if (lTypeID == 2)
		{
			lStatusIDArray = new long[] { LOANConstant.LoanPayNoticeStatus.USED };
		}
		else if (lTypeID == 3)
		{
			lStatusIDArray = new long[] { -100 };
		}
		else if (lTypeID == 4)
		{
			lStatusIDArray = new long[] { -200 };
		}
		else if (lTypeID == 5)
		{
			lStatusIDArray = new long[] { -300 };
		}
		StringBuffer sbSQL = new StringBuffer(64);
		sbSQL.append("getPayFormSQL(");
		sbSQL.append(lOfficeID);
		sbSQL.append(",");
		sbSQL.append(lCurrencyID);
		sbSQL.append(",");
		sbSQL.append(lClientID);
		sbSQL.append(",");
		if (strContractCtrl != null && !strContractCtrl.trim().equals(""))
		{
			sbSQL.append(strContractCtrl + ".value");
		}
		else
		{
			sbSQL.append("-1");
		}
		sbSQL.append(",");
		sbSQL.append("'");
		if (lContractTypeIDs != null && lContractTypeIDs.length > 0)
		{
			for (int i = 0; i < lContractTypeIDs.length; i++)
			{
				sbSQL.append(lContractTypeIDs[i]);
				if (i < lContractTypeIDs.length - 1)
				{
					sbSQL.append(",");
				}
			}
		}
		else
		{
			sbSQL.append("-1");
		}
		sbSQL.append("'");
		sbSQL.append(",");
		sbSQL.append("'");
		if (lStatusIDArray != null && lStatusIDArray.length > 0)
		{
			for (int i = 0; i < lStatusIDArray.length; i++)
			{
				sbSQL.append(lStatusIDArray[i]);
				if (i < lStatusIDArray.length - 1)
				{
					sbSQL.append(",");
				}
			}
		}
		else
		{
			sbSQL.append("-1");
		}
		sbSQL.append("'");
		sbSQL.append(",");
		sbSQL.append(strCtrlName);
		sbSQL.append("Ctrl.value)");
		Log.print("**�Ŵ�Sql**" + sbSQL.toString());
		try
		{
			OBMagnifier.showDifferZoomCtrl(
				out,
				strMagnifierName,
				strFormName,
				strPrefix,
				strMainNames,
				strMainFields,
				strReturnNames,
				strReturnFields,
				strReturnValues,
				strDisplayNames,
				strDisplayFields,
				nIndex,
				strMainProperty,
				sbSQL.toString(),
				strNextControls,
				strTitle,
				strFirstTD,
				strSecondTD,
				false,
				false,
				"ShowDifferMagnifierZoom.jsp");
		}
		catch (Exception e)
		{
			//e.printStackTrace();
			Log.print("�Ŵ�[" + strCtrlName + "]�쳣��" + e.toString());
		}
	}

	/**
		 * �����ſ�֪ͨ���Ŵ�
		 * @param out
		 * @param lOfficeID ���´�ID
		 * @param lCurrencyID ����ID
		 * @param strFormName ��������
		 * @param strCtrlName �Ŵ����ؼ�����
		 * @param strTitle �Ŵ�����
		 * @param lContractID ��ͬID(��ʶֵ)
		 * @param lPayFormID �ſ�֪ͨ��ID(��ʶֵ)
		 * @param strPayFormNo �ſ�֪ͨ����(��ʶֵ)
		 * @param lPayFormTypeID �ſ�֪ͨ������
		 * (��ѯ����:1,���У�2��ί�У�0,ȫ��)
		 * @param lTypeID �ſ�֪ͨ��ʹ������(�ڲ�״̬��
		 * 		0����ʾȫ����
		 * 		1������/ί�з��š���ҵ����ʹ�ã�
		 * 		2������/ί�з��š���ҵ�񸴺�ʹ�ã�
		 * 		3������/ί���ջء���ҵ����ʹ�ã�
		 * 		4������/ί���ջء���ҵ�񸴺�ʹ�ã�
		 * 		5�����׷��á���ҵ����ʹ�ã�
		 * 		6�����׷��á���ҵ����ʹ�ã���
		 * @param strContractCtrl ��ͬID��Ӧ�Ŀؼ����ƣ���ѯʱ������
		 * @param strFirstTD ��һ��TD������
		 * @param strSecondTD �ڶ���TD������
		 * @param strNextControls ��һ������������ý���Ŀؼ�
		 * @param strRtnStartDateCtrl ����ֵ����ʼ���ڣ���Ӧ�Ŀؼ���
		 * @param strRtnEndDateCtrl ����ֵ���������ڣ���Ӧ�Ŀؼ���
		 * @param strRtnSubAccountIDCtrl ����ֵ�����˻�ID����Ӧ�Ŀؼ���
		 * @param strRtnBalanceCtrl ����ֵ���ſ����Ӧ�Ŀؼ���
		 */
	public static void createPayFormNOCtrl(
		JspWriter out,
		long lClientID,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lContractID,
		long lPayFormID,
		String strPayFormNo,
		long lPayFormTypeID,
		long lTypeID,
		String strContractCtrl,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,
		String strRtnStartDateCtrl,
		String strRtnEndDateCtrl,
		String strRtnSubAccountIDCtrl,
		String strRtnBalanceCtrl)
	{
		String strMagnifierName = URLEncoder.encode("�ſ�֪ͨ��");
		String strMainProperty = " maxlength='30' value='" + strPayFormNo + "'";
		String strPrefix = "";

		if (strRtnStartDateCtrl == null || strRtnStartDateCtrl.equals(""))
		{
			strRtnStartDateCtrl = "ItIsNotControl";
		}
		if (strRtnEndDateCtrl == null || strRtnEndDateCtrl.equals(""))
		{
			strRtnEndDateCtrl = "ItIsNotControl";
		}
		if (strRtnSubAccountIDCtrl == null || strRtnSubAccountIDCtrl.equals(""))
		{
			strRtnSubAccountIDCtrl = "ItIsNotControl";
		}
		if (strRtnBalanceCtrl == null || strRtnBalanceCtrl.equals(""))
		{
			strRtnBalanceCtrl = "ItIsNotControl";
		}

		String[] strMainNames = { strCtrlName + "Ctrl", strRtnStartDateCtrl, strRtnEndDateCtrl, strRtnSubAccountIDCtrl, strRtnBalanceCtrl };
		String[] strMainFields = { "PayFormCode", "StartDate", "EndDate", "SubAccountID", "Balance" };

		String[] strReturnNames = { strCtrlName, strCtrlName + "ContractID" };
		String[] strReturnFields = { "PayFormID", "ContractID" };
		String[] strReturnValues = { String.valueOf(lPayFormID), String.valueOf(lContractID)};

		String[] strDisplayNames = { URLEncoder.encode("�ſ�֪ͨ����"), URLEncoder.encode("��ʼ����"), URLEncoder.encode("�ſ�����")};
		String[] strDisplayFields = { "PayFormCode", "StartDate", "PayDate" };
		int nIndex = 0;

		long[] lContractTypeIDs = null;

		//���� ��LOANConstant.LoanType.ZYDQ  ZYZCQ  ZGXEDQ  ZGXEZCQ  YT
		if (lPayFormTypeID == 1)
		{
//			lContractTypeIDs =
//				new long[] {
//					LOANConstant.LoanType.ZYDQ,
//					LOANConstant.LoanType.ZYZCQ,
//					LOANConstant.LoanType.ZGXEDQ,
//					LOANConstant.LoanType.ZGXEZCQ,
//					LOANConstant.LoanType.YTDQ,
//					LOANConstant.LoanType.YTZCQ };
			lContractTypeIDs =
				new long[] {
					LOANConstant.LoanType.ZY,
					LOANConstant.LoanType.ZGXE,
					LOANConstant.LoanType.YT };

		}
		//ί�� : WT  WTTJTH
		else if (lPayFormTypeID == 2)
		{
//			lContractTypeIDs = new long[] { LOANConstant.LoanType.WT, LOANConstant.LoanType.WTTJTH };
			lContractTypeIDs = new long[] { LOANConstant.LoanType.WT };
		}
		else if (lPayFormTypeID == 0)
		{
//			lContractTypeIDs =
//				new long[] {
//					LOANConstant.LoanType.WT,
//					LOANConstant.LoanType.WTTJTH,
//					LOANConstant.LoanType.ZYDQ,
//					LOANConstant.LoanType.ZYZCQ,
//					LOANConstant.LoanType.ZGXEDQ,
//					LOANConstant.LoanType.ZGXEZCQ,
//					LOANConstant.LoanType.YTDQ,
//					LOANConstant.LoanType.YTZCQ };
			lContractTypeIDs =
				new long[] {
					LOANConstant.LoanType.WT,
					LOANConstant.LoanType.ZY,
					LOANConstant.LoanType.ZGXE,
					LOANConstant.LoanType.YT };
		}
		long[] lStatusIDArray = null;

		//���״̬����
		if (lTypeID == 0)
		{
			lStatusIDArray = new long[] { LOANConstant.LoanPayNoticeStatus.CHECK, LOANConstant.LoanPayNoticeStatus.USED };
		}
		else if (lTypeID == 1)
		{
			lStatusIDArray = new long[] { LOANConstant.LoanPayNoticeStatus.CHECK };
		}
		else if (lTypeID == 2)
		{
			lStatusIDArray = new long[] { LOANConstant.LoanPayNoticeStatus.USED };
		}
		else if (lTypeID == 3)
		{
			lStatusIDArray = new long[] { -100 };
		}
		else if (lTypeID == 4)
		{
			lStatusIDArray = new long[] { -200 };
		}
		else if (lTypeID == 5)
		{
			lStatusIDArray = new long[] { -500 };
		}
		else if (lTypeID == 6)
		{
			lStatusIDArray = new long[] { -600 };
		}

		StringBuffer sbSQL = new StringBuffer(64);
		sbSQL.append("getPayFormNOSQL(");
		sbSQL.append(lClientID);
		sbSQL.append(",");
		sbSQL.append(lOfficeID);
		sbSQL.append(",");
		sbSQL.append(lCurrencyID);
		sbSQL.append(",");
		if (strContractCtrl != null && !strContractCtrl.trim().equals(""))
		{
			sbSQL.append(strContractCtrl + ".value");
		}
		else
		{
			sbSQL.append("-1");
		}
		sbSQL.append(",");
		sbSQL.append("'");
		if (lContractTypeIDs != null && lContractTypeIDs.length > 0)
		{
			for (int i = 0; i < lContractTypeIDs.length; i++)
			{
				sbSQL.append(lContractTypeIDs[i]);
				if (i < lContractTypeIDs.length - 1)
				{
					sbSQL.append(",");
				}
			}
		}
		else
		{
			sbSQL.append("-1");
		}
		sbSQL.append("'");
		sbSQL.append(",");

		sbSQL.append("'");

		if (lStatusIDArray != null && lStatusIDArray.length > 0)
		{
			for (int i = 0; i < lStatusIDArray.length; i++)
			{
				sbSQL.append(lStatusIDArray[i]);
				if (i < lStatusIDArray.length - 1)
				{
					sbSQL.append(",");
				}
			}
		}
		else
		{
			sbSQL.append("-1");
		}
		sbSQL.append("'");

		sbSQL.append(",");
		sbSQL.append(strCtrlName);
		sbSQL.append("Ctrl.value)");

		//Log.print(sbSQL.toString());

		try
		{
			OBMagnifier.showZoomCtrl(
				out,
				strMagnifierName,
				strFormName,
				strPrefix,
				strMainNames,
				strMainFields,
				strReturnNames,
				strReturnFields,
				strReturnValues,
				strDisplayNames,
				strDisplayFields,
				nIndex,
				strMainProperty,
				sbSQL.toString(),
				strNextControls,
				strTitle,
				strFirstTD,
				strSecondTD,
				false,
				false);
		}
		catch (Exception e)
		{
			//e.printStackTrace();
			Log.print("�Ŵ�[" + strCtrlName + "]�쳣��" + e.toString());
		}
	}

	/**
	 * ������������˺ŷŴ�,�տ�˺ţ��ڲ�ת�ˣ�
	 * ��������Ȩ��
	 * @author yanliu
	 * @param out
	 * @param lUserID �û�ID
	 * @param lClientID �ͻ�ID
	 * @param lInstructionID ��ǰ����ID
	 * @param lCurrencyID ����ID
	 * @param sBankAccountCode �˺ţ���ʼֵ��
	 * @param strFormName ��ҳ������
	 * @param strCtrlName �Ŵ����ؼ�����
	 * @param strTitle �Ŵ�����)
	 * @param strClientNo �ͻ����(��ʶֵ)
	 * @param strFirstTD ��һ��TD������
	 * @param strSecondTD �ڶ���TD������
	 * @param sNextControlsClient ��һ������������ý���Ŀؼ�
	 * @param strRtnClientNameCtrl ����ֵ���ͻ����ƣ���Ӧ�Ŀؼ���
	 */
	public static void createPayerAccountNoCtrl(
		JspWriter out,
		long lUserID,
		long lCurrencyID,
		long lInstructionID,
		long lClientID,
		String strRelativeForm1,
		String strRelativeForm2,
		String strRelativeForm3,
		String strFormName,
		String sBankAccountCode,
		String strCtrlName,
		String strTitle,
		String strFirstTD,
		String strSecondTD)
	{
		String strMagnifierName = (strRelativeForm2 != null && strRelativeForm2.length() > 0)?URLEncoder.encode("����˺�"):URLEncoder.encode("�տ�˺�");
		String strMainProperty = " maxlength='40' value='" + NameRef.getNoLineAccountNo(sBankAccountCode) + "'";
		String strPrefix = "";
		String[] strMainNames = { strCtrlName + "Ctrl", strRelativeForm1, strRelativeForm2, strRelativeForm3 };
		String[] strMainFields = { "saccountno", "nAccountID", "", "" };
		String[] strMainName = { strCtrlName + "Ctrl", strRelativeForm1,strRelativeForm3 }; //�տ
		String[] strMainField = { "saccountno", "nAccountID","sname" }; //�տ
		String[] strReturnNames = { "sName" };
		String[] strReturnFields = { "sname" };
		String[] strReturnValues = { "" };
		String[] strReturnName = { "sPayeeBankAccountNOInternal" }; //�տ
		String[] strReturnField = { "sname" }; //�տ
		String[] strReturnValue = { "" }; //�տ
		String[] strDisplayNames = { URLEncoder.encode("�˺�"), URLEncoder.encode("�˻�����")};
		String[] strDisplayFields = { "displayAccountNo", "sname" };

	    
		if(strCtrlName.equals("sInterestPayeeAccountInternal")){
		 
		strReturnName[0]="PayeeBankAccountNOInternal";//��Ϣ�տ
		 }
		int nIndex = 0;
		String strSQL = "";
		if (strRelativeForm2 != null && strRelativeForm2.length() > 0)
		{
			strSQL = "getPayerAccountNoSQL(" + strCtrlName + "Ctrl.value," + lUserID + "," + lClientID + "," + lCurrencyID + "," + lInstructionID + ")";
		}
		else
		{
			strSQL = "getInternalPayeeAccountNoSQL(" + strCtrlName + "Ctrl.value," + lUserID + "," + lClientID + "," + lCurrencyID + "," + lInstructionID + ")";
		}
		String[] strNextControls = { "nRemitType" };
		try
		{
			if (!strRelativeForm2.equals("") && !strRelativeForm3.equals(""))
			{
				showPayerAcctZoomCtrl(
					out,
					strMagnifierName,
					strFormName,
					strPrefix,
					strMainNames,
					strMainFields,
					strReturnNames,
					strReturnFields,
					strReturnValues,
					strDisplayNames,
					strDisplayFields,
					nIndex,
					strMainProperty,
					strSQL,
					strNextControls,
					strTitle,
					strFirstTD,
					strSecondTD,
					false,
					false);
			}
			else
			{
				showZoomCtrl(
					out,
					strMagnifierName,
					strFormName,
					strPrefix,
					strMainName,
					strMainField,
					strReturnName,
					strReturnField,
					strReturnValue,
					strDisplayNames,
					strDisplayFields,
					nIndex,
					strMainProperty,
					strSQL,
					strNextControls,
					strTitle,
					strFirstTD,
					strSecondTD,
					false,
					false);
			}
		}
		catch (Exception e)
		{
		}
	}
	/**
	 * ������������˺ŷŴ�,�տ�˺ţ��ڲ�ת�ˣ�
	 * ��������Ȩ��
	 * @author yanliu
	 * @param out
	 * @param lUserID �û�ID
	 * @param lClientID �ͻ�ID
	 * @param lInstructionID ��ǰ����ID
	 * @param lCurrencyID ����ID
	 * @param sBankAccountCode �˺ţ���ʼֵ��
	 * @param strFormName ��ҳ������
	 * @param strCtrlName �Ŵ����ؼ�����
	 * @param strTitle �Ŵ�����)
	 * @param strClientNo �ͻ����(��ʶֵ)
	 * @param strFirstTD ��һ��TD������
	 * @param strSecondTD �ڶ���TD������
	 * @param sNextControlsClient ��һ������������ý���Ŀؼ�
	 * @param strRtnClientNameCtrl ����ֵ���ͻ����ƣ���Ӧ�Ŀؼ���
	 */
	public static void createPayerAccountCtrlWithout(
		JspWriter out,
		long lUserID,
		long lCurrencyID,
		long lInstructionID,
		long lClientID,
		String strRelativeForm1,
		String strRelativeForm2,
		String strRelativeForm3,
		String strFormName,
		String sBankAccountCode,
		String strCtrlName,
		String strTitle,
		String strFirstTD,
		String strSecondTD)
	{
		String strMagnifierName = (strRelativeForm2 != null && strRelativeForm2.length() > 0)?URLEncoder.encode("����˺�"):URLEncoder.encode("�տ�˺�");
		String strMainProperty = " maxlength='40' value='" + NameRef.getNoLineAccountNo(sBankAccountCode) + "'";
		String strPrefix = "";
		String[] strMainNames = { strCtrlName + "Ctrl", strRelativeForm1, strRelativeForm2, strRelativeForm3 };
		String[] strMainFields = { "saccountno", "nAccountID", "", "" };
		String[] strMainName = { strCtrlName + "Ctrl", strRelativeForm1,strRelativeForm3 }; //�տ
		String[] strMainField = { "saccountno", "nAccountID","sname" }; //�տ
		String[] strReturnNames = { "sName" };
		String[] strReturnFields = { "sname" };
		String[] strReturnValues = { "" };
		String[] strReturnName = { "sPayeeBankAccountNOInternal" }; //�տ
		String[] strReturnField = { "sname" }; //�տ
		String[] strReturnValue = { "" }; //�տ
		String[] strDisplayNames = { URLEncoder.encode("�˺�"), URLEncoder.encode("�˻�����")};
		String[] strDisplayFields = { "displayAccountNo", "sname" };

	    
		if(strCtrlName.equals("sInterestPayeeAccountInternal")){
		 
		strReturnName[0]="PayeeBankAccountNOInternal";//��Ϣ�տ
		 }
		int nIndex = 0;
		String strSQL = "";
		if (strRelativeForm2 != null && strRelativeForm2.length() > 0)
		{
			strSQL = "getPayerAccountSQLWithout(" + strCtrlName + "Ctrl.value," + lUserID + "," + lClientID + "," + lCurrencyID + "," + lInstructionID + ")";
		}
		else
		{
			strSQL = "getInternalPayeeAccountNoSQL(" + strCtrlName + "Ctrl.value," + lUserID + "," + lClientID + "," + lCurrencyID + "," + lInstructionID + ")";
		}
		String[] strNextControls = { "nRemitType" };
		try
		{
			if (!strRelativeForm2.equals("") && !strRelativeForm3.equals(""))
			{
				showPayerAcctZoomCtrl(
					out,
					strMagnifierName,
					strFormName,
					strPrefix,
					strMainNames,
					strMainFields,
					strReturnNames,
					strReturnFields,
					strReturnValues,
					strDisplayNames,
					strDisplayFields,
					nIndex,
					strMainProperty,
					strSQL,
					strNextControls,
					strTitle,
					strFirstTD,
					strSecondTD,
					false,
					false);
			}
			else
			{
				showZoomCtrl(
					out,
					strMagnifierName,
					strFormName,
					strPrefix,
					strMainName,
					strMainField,
					strReturnName,
					strReturnField,
					strReturnValue,
					strDisplayNames,
					strDisplayFields,
					nIndex,
					strMainProperty,
					strSQL,
					strNextControls,
					strTitle,
					strFirstTD,
					strSecondTD,
					false,
					false);
			}
		}
		catch (Exception e)
		{
		}
	}
	/**
	 * ������������˺ŷŴ�,�տ�˺ţ��ڲ�ת�ˣ�(����ר��)
	 * @author yanliu
	 * @param out
	 * @param lUserID �û�ID
	 * @param lClientID �ͻ�ID
	 * @param lInstructionID ��ǰ����ID
	 * @param lCurrencyID ����ID
	 * @param sBankAccountCode �˺ţ���ʼֵ��
	 * @param strFormName ��ҳ������
	 * @param strCtrlName �Ŵ����ؼ�����
	 * @param strTitle �Ŵ�����)
	 * @param strClientNo �ͻ����(��ʶֵ)
	 * @param strFirstTD ��һ��TD������
	 * @param strSecondTD �ڶ���TD������
	 * @param sNextControlsClient ��һ������������ý���Ŀؼ�
	 * @param strRtnClientNameCtrl ����ֵ���ͻ����ƣ���Ӧ�Ŀؼ���
	 */
	public static void createPayerInterAccountNoCtrl(
		JspWriter out,
		long lUserID,
		long lCurrencyID,
		long lInstructionID,
		long lClientID,
		String strRelativeForm1,
		String strRelativeForm2,
		String strRelativeForm3,
		String strFormName,
		String sBankAccountCode,
		String strCtrlName,
		String strTitle,
		String strFirstTD,
		String strSecondTD)
	{
		String strMagnifierName = (strRelativeForm2 != null && strRelativeForm2.length() > 0)?URLEncoder.encode("����˺�"):URLEncoder.encode("�տ�˺�");
		String strMainProperty = " maxlength='40' value='" + NameRef.getNoLineAccountNo(sBankAccountCode) + "'";
		String strPrefix = "";
		String[] strMainNames = { strCtrlName + "Ctrl", strRelativeForm1, strRelativeForm2, strRelativeForm3 };
		String[] strMainFields = { "saccountno", "nAccountID", "", "" };
		String[] strMainName = { strCtrlName + "Ctrl", strRelativeForm1,strRelativeForm3 }; //�տ
		String[] strMainField = { "saccountno", "nAccountID","sname" }; //�տ
		String[] strReturnNames = { "sName" };
		String[] strReturnFields = { "sname" };
		String[] strReturnValues = { "" };
		String[] strReturnName = { "sPayeeBankAccountNOInternal" }; //�տ
		String[] strReturnField = { "sname" }; //�տ
		String[] strReturnValue = { "" }; //�տ
		String[] strDisplayNames = { URLEncoder.encode("�˺�"), URLEncoder.encode("�˻�����")};
		String[] strDisplayFields = { "displayAccountNo", "sname" };

	    
		if(strCtrlName.equals("sInterestPayeeAccountInternal")){
		 
		strReturnName[0]="PayeeBankAccountNOInternal";//��Ϣ�տ
		 }
		int nIndex = 0;
		String strSQL = "";
		if (strRelativeForm2 != null && strRelativeForm2.length() > 0)
		{
			strSQL = "getPayerAccountNoSQL(" + strCtrlName + "Ctrl.value," + lUserID + "," + lClientID + "," + lCurrencyID + "," + lInstructionID + ")";
		}
		else
		{
			//strSQL = "getInternalPayeeAccountNoSQL(" + strCtrlName + "Ctrl.value," + lUserID + "," + lClientID + "," + lCurrencyID + "," + lInstructionID + ")";
			strSQL = "getInterPayeeAccountNoSQL(" + strCtrlName + "Ctrl.value," + lUserID + "," + lClientID + "," + lCurrencyID + "," + lInstructionID + ")";		
		}            
		String[] strNextControls = { "nRemitType" };
		try
		{
			if (!strRelativeForm2.equals("") && !strRelativeForm3.equals(""))
			{
				showPayerAcctZoomCtrl(
					out,
					strMagnifierName,
					strFormName,
					strPrefix,
					strMainNames,
					strMainFields,
					strReturnNames,
					strReturnFields,
					strReturnValues,
					strDisplayNames,
					strDisplayFields,
					nIndex,
					strMainProperty,
					strSQL,
					strNextControls,
					strTitle,
					strFirstTD,
					strSecondTD,
					false,
					false);
			}
			else
			{
				showZoomCtrl(
					out,
					strMagnifierName,
					strFormName,
					strPrefix,
					strMainName,
					strMainField,
					strReturnName,
					strReturnField,
					strReturnValue,
					strDisplayNames,
					strDisplayFields,
					nIndex,
					strMainProperty,
					strSQL,
					strNextControls,
					strTitle,
					strFirstTD,
					strSecondTD,
					false,
					false);
			}
		}
		catch (Exception e)
		{
		}
	}
	
	
	
	/**
	 * ������������˺ŷŴ�,�н����»�����Ŵ󾵣������տ�������
	 * @author yanliu
	 * @param out
	 * @param lUserID �û�ID
	 * @param lClientID �ͻ�ID
	 * @param lInstructionID ��ǰ����ID
	 * @param lCurrencyID ����ID
	 * @param sBankAccountCode �˺ţ���ʼֵ��
	 * @param strFormName ��ҳ������
	 * @param strCtrlName �Ŵ����ؼ�����
	 * @param strTitle �Ŵ�����)
	 * @param strClientNo �ͻ����(��ʶֵ)
	 * @param strFirstTD ��һ��TD������
	 * @param strSecondTD �ڶ���TD������
	 * @param sNextControlsClient ��һ������������ý���Ŀؼ�
	 * @param strRtnClientNameCtrl ����ֵ���ͻ����ƣ���Ӧ�Ŀؼ���
	 */
	public static void createPayerAccountNoCtrl1(
		JspWriter out,
		long lUserID,
		long lCurrencyID,
		long lInstructionID,
		long lClientID,
		String strRelativeForm1,
		String strRelativeForm2,
		String strRelativeForm3,
		String strFormName,
		String sBankAccountCode,
		String strCtrlName,
		String strTitle,
		String strFirstTD,
		String strSecondTD)
	{
		String strMagnifierName = URLEncoder.encode("����˺�");
		String strMainProperty = " maxlength='40' value='" + sBankAccountCode + "'";
		String strPrefix = "";
		String[] strMainNames = { strCtrlName + "Ctrl", strRelativeForm1,strRelativeForm2, strRelativeForm3 ,"sPayeeAcctNoDownZoomCtrl","sPayeeNameZoomAcctDownCtrl","sPayeeProvDown","sPayeeCityDown","sPayeeBankNameDown"};
		String[] strMainFields = { "sactno", "nActID","","","o1","o2","p","c","o3"};
		//String[] strMainName = { strCtrlName + "Ctrl", strRelativeForm1 }; //�տ
		//String[] strMainField = { "saccountno", "nAccountID" }; //�տ
		String[] strReturnNames = { "sName" };
		String[] strReturnFields = { "sname" };
		String[] strReturnValues = { "" };
		//String[] strReturnName = { "sPayeeBankAccountNOInternal" }; //�տ
		//String[] strReturnField = { "sname" }; //�տ
		//String[] strReturnValue = { "" }; //�տ
		String[] strDisplayNames = { URLEncoder.encode("�˺�"), URLEncoder.encode("�˻�����")};
		String[] strDisplayFields = { "dANo", "sname" };
		int nIndex = 0;
		String strSQL = "";
	
		strSQL = "getPayerAccountNoSQL1(" + strCtrlName + "Ctrl.value," + lUserID + "," + lClientID + "," + lCurrencyID + "," + lInstructionID + ")";
	
		
		String[] strNextControls = { "nRemitType" };
		try
		{
			showPayerAcctZoomCtrlZj(
					out,
					strMagnifierName,
					strFormName,
					strPrefix,
					strMainNames,
					strMainFields,
					strReturnNames,
					strReturnFields,
					strReturnValues,
					strDisplayNames,
					strDisplayFields,
					nIndex,
					strMainProperty,
					strSQL,
					strNextControls,
					strTitle,
					strFirstTD,
					strSecondTD,
					false,
					false);
			
		}
		catch (Exception e)
		{
		}
	}
/*
 * �н���������ת�տ�Ŵ�
 */
	public static void createPayerAccountNoCtrlForZj(
			JspWriter out,
			long lUserID,
			long lCurrencyID,
			long lInstructionID,
			long lClientID,
			String strRelativeForm1,
			String strRelativeForm2,
			String strRelativeForm3,
			String strFormName,
			String sBankAccountCode,
			String strCtrlName,
			String strTitle,
			String strFirstTD,
			String strSecondTD)
		{
			String strMagnifierName = URLEncoder.encode("����˺�");
			String strMainProperty = " maxlength='40' value='" + sBankAccountCode + "'";
			String strPrefix = "";
			String[] strMainNames = { strCtrlName + "Ctrl", strRelativeForm1, strRelativeForm2, strRelativeForm3 };
			String[] strMainFields = { "saccountno", "nAccountID", "", "" };
			String[] strMainName = { strCtrlName + "Ctrl", strRelativeForm1 }; //�տ
			String[] strMainField = { "saccountno", "nAccountID" }; //�տ
			String[] strReturnNames = { "sName" };
			String[] strReturnFields = { "sname" };
			String[] strReturnValues = { "" };
			String[] strReturnName = { "sPayeeBankAccountNOInternal" }; //�տ
			String[] strReturnField = { "sname" }; //�տ
			String[] strReturnValue = { "" }; //�տ
			String[] strDisplayNames = { URLEncoder.encode("�ڲ��˺�"), URLEncoder.encode("�ڲ��˻�����")};
			String[] strDisplayFields = { "displayAccountNo", "sname" };
			int nIndex = 0;
			String strSQL = "";
			if (strRelativeForm2 != null && strRelativeForm2.length() > 0)
			{
				strSQL = "getPayerAccountNoSQL(" + strCtrlName + "Ctrl.value," + lUserID + "," + lClientID + "," + lCurrencyID + "," + lInstructionID + ")";
			}
			else
			{
				strSQL = "getInternalPayeeAccountNoSQL(" + strCtrlName + "Ctrl.value," + lUserID + "," + lClientID + "," + lCurrencyID + "," + lInstructionID + ")";
			}
			String[] strNextControls = { "dAmount" };
			try
			{
				if (!strRelativeForm2.equals("") && !strRelativeForm3.equals(""))
				{
					showPayerAcctZoomCtrl(
						out,
						strMagnifierName,
						strFormName,
						strPrefix,
						strMainNames,
						strMainFields,
						strReturnNames,
						strReturnFields,
						strReturnValues,
						strDisplayNames,
						strDisplayFields,
						nIndex,
						strMainProperty,
						strSQL,
						strNextControls,
						strTitle,
						strFirstTD,
						strSecondTD,
						false,
						false);
				}
				else
				{
					showZoomCtrl(
						out,
						strMagnifierName,
						strFormName,
						strPrefix,
						strMainName,
						strMainField,
						strReturnName,
						strReturnField,
						strReturnValue,
						strDisplayNames,
						strDisplayFields,
						nIndex,
						strMainProperty,
						strSQL,
						strNextControls,
						strTitle,
						strFirstTD,
						strSecondTD,
						false,
						false);
				}
			}
			catch (Exception e)
			{
			}
		}
	//�������и���˻���ѯ�Ŵ󾵣���������Ȩ�ޣ�
	public static void createPayerAccountNoCtrl(
		JspWriter out,
		long lUserID,
		long lCurrencyID,
		long lInstructionID,
		long lClientID,
		String strRelativeForm1,
		String strRelativeForm2,
		String strRelativeForm3,
		String strFormName,
		String sBankAccountCode,
		String strCtrlName,
		String strTitle,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls)
	{
		String strMagnifierName = (strRelativeForm2 != null && strRelativeForm2.length() > 0)?URLEncoder.encode("����˺�"):URLEncoder.encode("�տ�˺�");
		String strMainProperty = " maxlength='40' value='" + NameRef.getNoLineAccountNo(sBankAccountCode) + "'";
		String strPrefix = "";
		String[] strMainNames = { strCtrlName + "Ctrl", strRelativeForm1, strRelativeForm2, strRelativeForm3 };
		String[] strMainFields = { "saccountno", "nAccountID", "", "" };
		String[] strMainName = { strCtrlName + "Ctrl", strRelativeForm1, strRelativeForm3}; //�տ
		String[] strMainField = { "saccountno", "nAccountID","sname" }; //�տ
		String[] strReturnNames = { "sName" };
		String[] strReturnFields = { "sname" };
		String[] strReturnValues = { "" };
	   
	     String[]  strReturnName = { "sPayeeBankAccountNOInternal" }; //�տ
		if(strCtrlName.equals("sInterestPayeeAccountInternal")){
		 
		strReturnName[0]="PayeeBankAccountNOInternal";//��Ϣ�տ
		 }
	     
		String[] strReturnField = { "sname" }; //�տ
		String[] strReturnValue = { "" }; //�տ
		String[] strDisplayNames = { URLEncoder.encode("�˺�"), URLEncoder.encode("�˻�����")};
		String[] strDisplayFields = { "displayAccountNo", "sname" };
		int nIndex = 0;
		String strSQL = "";
		
		if (strRelativeForm2 != null && strRelativeForm2.length() > 0)
		{
			strSQL = "getPayerAccountNoSQL(" + strCtrlName + "Ctrl.value," + lUserID + "," + lClientID + "," + lCurrencyID + "," + lInstructionID + ")";
		}
		else
		{
			strSQL = "getInternalPayeeAccountNoSQL(" + strCtrlName + "Ctrl.value," + lUserID + "," + lClientID + "," + lCurrencyID + "," + lInstructionID + ")";
		}
		//String[] strNextControls = { "nRemitType" };
		try
		{
			if (!strRelativeForm2.equals("") && !strRelativeForm3.equals(""))
			{
				showPayerAcctZoomCtrl(
					out,
					strMagnifierName,
					strFormName,
					strPrefix,
					strMainNames,
					strMainFields,
					strReturnNames,
					strReturnFields,
					strReturnValues,
					strDisplayNames,
					strDisplayFields,
					nIndex,
					strMainProperty,
					strSQL,
					strNextControls,
					strTitle,
					strFirstTD,
					strSecondTD,
					false,
					false);
			}
			else
			{
				showZoomCtrl(
					out,
					strMagnifierName,
					strFormName,
					strPrefix,
					strMainName,
					strMainField,
					strReturnName,
					strReturnField,
					strReturnValue,
					strDisplayNames,
					strDisplayFields,
					nIndex,
					strMainProperty,
					strSQL,
					strNextControls,
					strTitle,
					strFirstTD,
					strSecondTD,
					false,
					false);
			}
		}
		catch (Exception e)
		{
		}
	}
	/**
	 * ��������������
	 * @param out
	 * @param lUserID
	 * @param lCurrencyID
	 * @param lInstructionID
	 * @param lClientID
	 * @param strRelativeForm1
	 * @param strRelativeForm2
	 * @param strRelativeForm3
	 * @param strFormName
	 * @param sBankAccountCode
	 * @param strCtrlName
	 * @param strTitle
	 * @param strFirstTD
	 * @param strSecondTD
	 * @param strNextControls
	 */
	
	public static void createPayerAccountNoCtrlByDate(
			JspWriter out,
			long lUserID,
			long lCurrencyID,
			long lOfficeId,
			long lInstructionID,
			long lClientID,
			String strRelativeForm1,
			String strRelativeForm2,
			String strRelativeForm3,
			String strRelativeForm4,
			String strFormName,
			String sBankAccountCode,
			String strCtrlName,
			String strTitle,
			String strFirstTD,
			String strSecondTD,
			String[] strNextControls)
		{
			//��ȡ��ǰ����ϵͳ��������
			Timestamp opendate = Env.getSystemDate(lOfficeId,lCurrencyID);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String strOpenDate = sdf.format(opendate);
			String strMagnifierName = (strRelativeForm2 != null && strRelativeForm2.length() > 0)?URLEncoder.encode("����˺�"):URLEncoder.encode("�տ�˺�");
			String strMainProperty = " maxlength='40' value='" + NameRef.getNoLineAccountNo(sBankAccountCode) + "'";
			String strPrefix = "";
			String[] strMainNames = { strCtrlName + "Ctrl", strRelativeForm1, strRelativeForm2, strRelativeForm3,strRelativeForm4 };
			String[] strMainFields = { "saccountno", "nAccountID", "", "", "dtopendate" };
			String[] strReturnNames = { "sName","hiddenOpendate" };
			String[] strReturnFields = { "sname","dtopendate"};
			String[] strReturnValues = { "",strOpenDate};
			String[] strDisplayNames = { URLEncoder.encode("�˺�"), URLEncoder.encode("�˻�����")};
			String[] strDisplayFields = { "displayAccountNo", "sname" };
			int nIndex = 0;
			String strSQL = "";
			strSQL = "getPayerAccountNoSQLByDate(" + strCtrlName + "Ctrl.value," + lUserID + "," + lClientID + "," + lCurrencyID + "," + lInstructionID + ")";

			try
			{
					showPayerAcctZoomCtrl(
						out,
						strMagnifierName,
						strFormName,
						strPrefix,
						strMainNames,
						strMainFields,
						strReturnNames,
						strReturnFields,
						strReturnValues,
						strDisplayNames,
						strDisplayFields,
						nIndex,
						strMainProperty,
						strSQL,
						strNextControls,
						strTitle,
						strFirstTD,
						strSecondTD,
						false,
						false);
			}
			catch (Exception e)
			{
			}
		}	
	//ҵ�񸴺�new ר�÷Ŵ󾵣���ʸ��֪ͨ������
	
	public static void createPayerAccountCheckCtrl(
		JspWriter out,
		long lUserID,
		long lCurrencyID,
		long lInstructionID,
		long lClientID,
		String strRelativeForm1,
		String strRelativeForm2,
		String strRelativeForm3,
		String strFormName,
		String sBankAccountCode,
		String strCtrlName,
		String strTitle,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls)
	{
		String strMagnifierName = (strRelativeForm2 != null && strRelativeForm2.length() > 0)?URLEncoder.encode("����˺�"):URLEncoder.encode("�տ�˺�");
		String strMainProperty = " maxlength='40' value='" + NameRef.getNoLineAccountNo(sBankAccountCode) + "'";
		String strPrefix = "";
		String[] strMainNames = { strCtrlName + "Ctrl", strRelativeForm1, strRelativeForm2, strRelativeForm3 };
		String[] strMainFields = { "saccountno", "nAccountID", "", "" };
		String[] strMainName = { strCtrlName + "Ctrl", strRelativeForm1, strRelativeForm3}; //�տ
		String[] strMainField = { "saccountno", "nAccountID","sname" }; //�տ
		String[] strReturnNames = { "sName" };
		String[] strReturnFields = { "sname" };
		String[] strReturnValues = { "" };
	   
	     String[]  strReturnName = { "sPayeeBankAccountNOInternal" }; //�տ
		if(strCtrlName.equals("sInterestPayeeAccountInternal")){
		 
		strReturnName[0]="PayeeBankAccountNOInternal";//��Ϣ�տ
		 }
	     
		String[] strReturnField = { "sname" }; //�տ
		String[] strReturnValue = { "" }; //�տ
		String[] strDisplayNames = { URLEncoder.encode("�˺�"), URLEncoder.encode("�˻�����")};
		String[] strDisplayFields = { "displayAccountNo", "sname" };
		int nIndex = 0;
		String strSQL = "";
		
		if (strRelativeForm2 != null && strRelativeForm2.length() > 0)
		{
			strSQL = "getPayerAccountCheckSQLWithout(" + strCtrlName + "Ctrl.value," + lUserID + "," + lClientID + "," + lCurrencyID + "," + lInstructionID + ")";
		}
		else
		{
			strSQL = "getInternalPayeeAccountNoSQL(" + strCtrlName + "Ctrl.value," + lUserID + "," + lClientID + "," + lCurrencyID + "," + lInstructionID + ")";
		}
		//String[] strNextControls = { "nRemitType" };
		try
		{
			if (!strRelativeForm2.equals("") && !strRelativeForm3.equals(""))
			{
				showPayerAcctZoomCtrl(
					out,
					strMagnifierName,
					strFormName,
					strPrefix,
					strMainNames,
					strMainFields,
					strReturnNames,
					strReturnFields,
					strReturnValues,
					strDisplayNames,
					strDisplayFields,
					nIndex,
					strMainProperty,
					strSQL,
					strNextControls,
					strTitle,
					strFirstTD,
					strSecondTD,
					false,
					false);
			}
			else
			{
				showZoomCtrl(
					out,
					strMagnifierName,
					strFormName,
					strPrefix,
					strMainName,
					strMainField,
					strReturnName,
					strReturnField,
					strReturnValue,
					strDisplayNames,
					strDisplayFields,
					nIndex,
					strMainProperty,
					strSQL,
					strNextControls,
					strTitle,
					strFirstTD,
					strSecondTD,
					false,
					false);
			}
		}
		catch (Exception e)
		{
		}
	}


	
	
	
	//�������и���˻���ѯ�Ŵ󾵣�û�й�������Ȩ�ޣ�
	public static void createPayerAccountCtrlWithout(
			JspWriter out,
			long lUserID,
			long lCurrencyID,
			long lInstructionID,
			long lClientID,
			String strRelativeForm1,
			String strRelativeForm2,
			String strRelativeForm3,
			String strFormName,
			String sBankAccountCode,
			String strCtrlName,
			String strTitle,
			String strFirstTD,
			String strSecondTD,
			String[] strNextControls)
		{
			String strMagnifierName = (strRelativeForm2 != null && strRelativeForm2.length() > 0)?URLEncoder.encode("����˺�"):URLEncoder.encode("�տ�˺�");
			String strMainProperty = " maxlength='40' value='" + NameRef.getNoLineAccountNo(sBankAccountCode) + "'";
			String strPrefix = "";
			String[] strMainNames = { strCtrlName + "Ctrl", strRelativeForm1, strRelativeForm2, strRelativeForm3 };
			String[] strMainFields = { "saccountno", "nAccountID", "", "" };
			String[] strMainName = { strCtrlName + "Ctrl", strRelativeForm1, strRelativeForm3}; //�տ
			String[] strMainField = { "saccountno", "nAccountID","sname" }; //�տ
			String[] strReturnNames = { "sName" };
			String[] strReturnFields = { "sname" };
			String[] strReturnValues = { "" };
		   
		     String[]  strReturnName = { "sPayeeBankAccountNOInternal" }; //�տ
			if(strCtrlName.equals("sInterestPayeeAccountInternal")){
			 
			strReturnName[0]="PayeeBankAccountNOInternal";//��Ϣ�տ
			 }
		     
			String[] strReturnField = { "sname" }; //�տ
			String[] strReturnValue = { "" }; //�տ
			String[] strDisplayNames = { URLEncoder.encode("�˺�"), URLEncoder.encode("�˻�����")};
			String[] strDisplayFields = { "displayAccountNo", "sname" };
			int nIndex = 0;
			String strSQL = "";
			
			if (strRelativeForm2 != null && strRelativeForm2.length() > 0)
			{
				strSQL = "getPayerAccountSQLWithout(" + strCtrlName + "Ctrl.value," + lUserID + "," + lClientID + "," + lCurrencyID + "," + lInstructionID + ")";
			}
			else
			{
				strSQL = "getInternalPayeeAccountNoSQL(" + strCtrlName + "Ctrl.value," + lUserID + "," + lClientID + "," + lCurrencyID + "," + lInstructionID + ")";
			}
			
			//String[] strNextControls = { "nRemitType" };
			try
			{
				if (!strRelativeForm2.equals("") && !strRelativeForm3.equals(""))
				{
					showPayerAcctZoomCtrl(
						out,
						strMagnifierName,
						strFormName,
						strPrefix,
						strMainNames,
						strMainFields,
						strReturnNames,
						strReturnFields,
						strReturnValues,
						strDisplayNames,
						strDisplayFields,
						nIndex,
						strMainProperty,
						strSQL,
						strNextControls,
						strTitle,
						strFirstTD,
						strSecondTD,
						false,
						false);
				}
				else
				{
					showZoomCtrl(
						out,
						strMagnifierName,
						strFormName,
						strPrefix,
						strMainName,
						strMainField,
						strReturnName,
						strReturnField,
						strReturnValue,
						strDisplayNames,
						strDisplayFields,
						nIndex,
						strMainProperty,
						strSQL,
						strNextControls,
						strTitle,
						strFirstTD,
						strSecondTD,
						false,
						false);
				}
			}
			catch (Exception e)
			{
			}
		}
	
	
	//����ר��
	public static void createPayerInterAccountNoCtrl(
			JspWriter out,
			long lUserID,
			long lCurrencyID,
			long lInstructionID,
			long lClientID,
			String strRelativeForm1,
			String strRelativeForm2,
			String strRelativeForm3,
			String strFormName,
			String sBankAccountCode,
			String strCtrlName,
			String strTitle,
			String strFirstTD,
			String strSecondTD,
			String[] strNextControls)
		{
			String strMagnifierName = (strRelativeForm2 != null && strRelativeForm2.length() > 0)?URLEncoder.encode("����˺�"):URLEncoder.encode("�տ�˺�");
			String strMainProperty = " maxlength='40' value='" + NameRef.getNoLineAccountNo(sBankAccountCode) + "'";
			String strPrefix = "";
			String[] strMainNames = { strCtrlName + "Ctrl", strRelativeForm1, strRelativeForm2, strRelativeForm3 };
			String[] strMainFields = { "saccountno", "nAccountID", "", "" };
			String[] strMainName = { strCtrlName + "Ctrl", strRelativeForm1, strRelativeForm3}; //�տ
			String[] strMainField = { "saccountno", "nAccountID","sname" }; //�տ
			String[] strReturnNames = { "sName" };
			String[] strReturnFields = { "sname" };
			String[] strReturnValues = { "" };
		   
		     String[]  strReturnName = { "sPayeeBankAccountNOInternal" }; //�տ
			if(strCtrlName.equals("sInterestPayeeAccountInternal")){
			 
			strReturnName[0]="PayeeBankAccountNOInternal";//��Ϣ�տ
			 }
		     
			String[] strReturnField = { "sname" }; //�տ
			String[] strReturnValue = { "" }; //�տ
			String[] strDisplayNames = { URLEncoder.encode("�˺�"), URLEncoder.encode("�˻�����")};
			String[] strDisplayFields = { "displayAccountNo", "sname" };
			int nIndex = 0;
			String strSQL = "";
			if (strRelativeForm2 != null && strRelativeForm2.length() > 0)
			{
				strSQL = "getPayerAccountNoSQL(" + strCtrlName + "Ctrl.value," + lUserID + "," + lClientID + "," + lCurrencyID + "," + lInstructionID + ")";
			}
			else
			{
				strSQL = "getInterPayeeAccountNoSQL(" + strCtrlName + "Ctrl.value," + lUserID + "," + lClientID + "," + lCurrencyID + "," + lInstructionID + ")";
			}
			//String[] strNextControls = { "nRemitType" };
			try
			{
				if (!strRelativeForm2.equals("") && !strRelativeForm3.equals(""))
				{
					showPayerAcctZoomCtrl(
						out,
						strMagnifierName,
						strFormName,
						strPrefix,
						strMainNames,
						strMainFields,
						strReturnNames,
						strReturnFields,
						strReturnValues,
						strDisplayNames,
						strDisplayFields,
						nIndex,
						strMainProperty,
						strSQL,
						strNextControls,
						strTitle,
						strFirstTD,
						strSecondTD,
						false,
						false);
				}
				else
				{
					showZoomCtrl(
						out,
						strMagnifierName,
						strFormName,
						strPrefix,
						strMainName,
						strMainField,
						strReturnName,
						strReturnField,
						strReturnValue,
						strDisplayNames,
						strDisplayFields,
						nIndex,
						strMainProperty,
						strSQL,
						strNextControls,
						strTitle,
						strFirstTD,
						strSecondTD,
						false,
						false);
				}
			}
			catch (Exception e)
			{
			}
		}
	
	
	public static void createPayerAccountNoCtrl1(
			JspWriter out,
			long lUserID,
			long lCurrencyID,
			long lInstructionID,
			long lClientID,
			String strRelativeForm1,
			String strRelativeForm2,
			String strRelativeForm3,
			String strRelativeForm4,
			String strFormName,
			String sBankAccountCode,
			String strCtrlName,
			String strTitle,
			String strFirstTD,
			String strSecondTD,
			String[] strNextControls)
		{
			String strMagnifierName = URLEncoder.encode("����˺�");
			String strMainProperty = " maxlength='40' value='" + NameRef.getNoLineAccountNo(sBankAccountCode) + "'";
			String strPrefix = "";
			String[] strMainNames = { strCtrlName + "Ctrl", strRelativeForm1, strRelativeForm2, strRelativeForm3,strRelativeForm4 };
			String[] strMainFields = { "saccountno", "nAccountID", "", "" ,"issoft"};
			String[] strMainName = { strCtrlName + "Ctrl", strRelativeForm1, strRelativeForm3}; //�տ
			String[] strMainField = { "saccountno", "nAccountID","sname"  }; //�տ
			String[] strReturnNames = { "sName" };
			String[] strReturnFields = { "sname" };
			String[] strReturnValues = { "" };
		   
		     String[]  strReturnName = { "sPayeeBankAccountNOInternal" }; //�տ
			if(strCtrlName.equals("sInterestPayeeAccountInternal")){
			 
			strReturnName[0]="PayeeBankAccountNOInternal";//��Ϣ�տ
			 }
		     
			String[] strReturnField = { "sname" }; //�տ
			String[] strReturnValue = { "" }; //�տ
			String[] strDisplayNames = { URLEncoder.encode("�˺�"), URLEncoder.encode("�˻�����")};
			String[] strDisplayFields = { "displayAccountNo", "sname" };
			int nIndex = 0;
			String strSQL = "";
			if (strRelativeForm2 != null && strRelativeForm2.length() > 0)
			{
				strSQL = "getPayerAccountNoSQL2(" + strCtrlName + "Ctrl.value," + lUserID + "," + lClientID + "," + lCurrencyID + "," + lInstructionID + ")";
				
			}
			else
			{
				strSQL = "getInternalPayeeAccountNoSQL(" + strCtrlName + "Ctrl.value," + lUserID + "," + lClientID + "," + lCurrencyID + "," + lInstructionID + ")";
			}
			//String[] strNextControls = { "nRemitType" };
			try
			{
				if (!strRelativeForm2.equals("") && !strRelativeForm3.equals(""))
				{
					showPayerAcctZoomCtrl(
						out,
						strMagnifierName,
						strFormName,
						strPrefix,
						strMainNames,
						strMainFields,
						strReturnNames,
						strReturnFields,
						strReturnValues,
						strDisplayNames,
						strDisplayFields,
						nIndex,
						strMainProperty,
						strSQL,
						strNextControls,
						strTitle,
						strFirstTD,
						strSecondTD,
						false,
						false);
				}
				else
				{
					showZoomCtrl(
						out,
						strMagnifierName,
						strFormName,
						strPrefix,
						strMainName,
						strMainField,
						strReturnName,
						strReturnField,
						strReturnValue,
						strDisplayNames,
						strDisplayFields,
						nIndex,
						strMainProperty,
						strSQL,
						strNextControls,
						strTitle,
						strFirstTD,
						strSecondTD,
						false,
						false);
				}
			}
			catch (Exception e)
			{
			}
		}

	/**
	 * ��������(֪ͨ)���ݺŷŴ�
	 * @param out
	 * @param lOfficeID ���´�ID
	 * @param lCurrencyID ����ID
	 * @param strFormName ��������
	 * @param strCtrlName �Ŵ����ؼ�����
	 * @param strTitle �Ŵ�����
	 * @param lUserID ��ǰ�û�ID(��ʶֵ)
	 * @param lAccountID ���˻�ID(��ʶֵ) 
	 * @param lSubAccountID ���˻�ID(��ʶֵ)
	 * @param strDepositNo ����(֪ͨ)�浥��(��ʶֵ)
	 * @param lDepositTypeID �浥���ͣ�1�����ڣ�2��֪ͨ��
	 * @param lTypeID �Ŵ����ͣ�ȡֵ���£�
	 * 	1�����ڣ�֪ͨ������--����ƥ��ʱʹ��
	 * 	21�����ڣ�֪ͨ��֧ȡ--ҵ����ʱʹ��
	 *  22�����ڣ�֪ͨ��֧ȡ--ҵ�񸴺�ʱʹ��
	 *  3����������ת��--ҵ����ʱʹ�ã�����ʾ�ѵ��ڵĴ浥��
	 * 
	 * @param strAccountIDCtrl����ѯʱ������
	 * @param strFirstTD ��һ��TD������
	 * @param strSecondTD �ڶ���TD������
	 * @param sNextControlsClient ��һ������������ý���Ŀؼ�
	 * @param strRtnEndDateCtrl ����ֵ�������գ���Ӧ�Ŀؼ���
	 */
	public static void createFixedDepositNoCtrl(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lUserID,
		long lAccountID,
		long lSubAccountID,
		String strDepositNo,
		long lDepositTypeID,
		long lTypeID,
		String strAccountIDCtrl,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,
		String strRtnEndDateCtrl)
	{
		createFixedDepositNoCtrl(
			out,
			lOfficeID,
			lCurrencyID,
			strFormName,
			strCtrlName,
			strTitle,
			lUserID,
			lAccountID,
			lSubAccountID,
			strDepositNo,
			lDepositTypeID,
			lTypeID,
			strAccountIDCtrl,
			strFirstTD,
			strSecondTD,
			strNextControls,
			strRtnEndDateCtrl,
			"",
			"",
			"",
			"",
			"",
			"");
	}
	public static void createFixedDepositNoCtrl(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lUserID,
		long lAccountID,
		long lSubAccountID,
		String strDepositNo,
		long lDepositTypeID,
		long lTypeID,
		String strAccountIDCtrl,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,
		String strRtnEndDateCtrl,
		String strRtnOpenDateCtrl,
		String strRtnCapitalCtrl,
		String strRtnBalanceCtrl,
		String strRtnRateCtrl,
		String strRtnIntervalCtrl,
		String strRtnStartDateCtrl)
	{
		createFixedDepositNoCtrl(
			out,
			lOfficeID,
			lCurrencyID,
			strFormName,
			strCtrlName,
			strTitle,
			lUserID,
			lAccountID,
			lSubAccountID,
			strDepositNo,
			lDepositTypeID,
			lTypeID,
			strAccountIDCtrl,
			strFirstTD,
			strSecondTD,
			strNextControls,
			strRtnEndDateCtrl,
			"",
			"",
			"",
			"",
			"",
			"",
			"");
	}
	/**
	 * ��������(֪ͨ)���ݺŷŴ�
	 * @param out
	 * @param lOfficeID ���´�ID
	 * @param lCurrencyID ����ID
	 * @param strFormName ��������
	 * @param strCtrlName �Ŵ����ؼ�����
	 * @param strTitle �Ŵ�����
	 * @param lUserID ��ǰ�û�ID(��ʶֵ)
	 * @param lAccountID ���˻�ID(��ʶֵ) 
	 * @param lSubAccountID ���˻�ID(��ʶֵ)
	 * @param strDepositNo ����(֪ͨ)�浥��(��ʶֵ)
	 * @param lDepositTypeID �浥���ͣ�1�����ڣ�2��֪ͨ��
	 * @param lTypeID �Ŵ����ͣ�ȡֵ���£�
	 * 	1�����ڣ�֪ͨ������--����ƥ��ʱʹ��
	 * 	21�����ڣ�֪ͨ��֧ȡ--ҵ����ʱʹ��
	 *  22�����ڣ�֪ͨ��֧ȡ--ҵ�񸴺�ʱʹ��
	 *  3����������ת��--ҵ����ʱʹ�ã�����ʾ�ѵ��ڵĴ浥��
	 * 
	 * @param strAccountIDCtrl����ѯʱ������
	 * @param strFirstTD ��һ��TD������
	 * @param strSecondTD �ڶ���TD������
	 * @param sNextControlsClient ��һ������������ý���Ŀؼ�
	 * @param strRtnEndDateCtrl ����ֵ�������գ���Ӧ�Ŀؼ���
	 * @param strRtnOpenDateCtrl ����ֵ�������գ���Ӧ�Ŀؼ���
	 * @param strRtnCapitalCtrl ����ֵ�����𣩶�Ӧ�Ŀؼ���
	 * @param strRtnBalanceCtrl ����ֵ������Ӧ�Ŀؼ���
	 * @param strRtnRateCtrl ����ֵ�����ʣ���Ӧ�Ŀؼ���
	 * @param strRtnIntervalCtrl ����ֵ�����ޣ���Ӧ�Ŀؼ���
	 * @param strRtnStartDateCtrl ����ֵ����ʼ���ڣ���Ӧ�Ŀؼ���
	 */
	public static void createFixedDepositNoCtrl(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lUserID,
		long lAccountID,
		long lSubAccountID,
		String strDepositNo,
		long lDepositTypeID,
		long lTypeID,
		String strAccountIDCtrl,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,
		String strRtnEndDateCtrl,
		String strRtnOpenDateCtrl,
		String strRtnCapitalCtrl,
		String strRtnBalanceCtrl,
		String strRtnRateCtrl,
		String strRtnIntervalCtrl,
		String strRtnStartDateCtrl,
		String strAmount)
	{
		String strMagnifierName = URLEncoder.encode("���ݺ�");
		if (lDepositTypeID == 1)
		{
			strMagnifierName = URLEncoder.encode("���ڴ��ݺ�");
		}
		else if (lDepositTypeID == 2)
		{
			strMagnifierName = URLEncoder.encode("֪ͨ���ݺ�");
		}
		String strMainProperty = " maxlength='30' value='" + strDepositNo + "'";
		if (strAmount != null && strAmount != "")
		{
			strMainProperty += " onblur=\"adjustAmount('" + strFormName + "','" + strAmount + "',1,'sChineseAmount','" + lCurrencyID + "');\" ";
			//strMainProperty += " onfocus=\"adjustAmount('" + strFormName +"','" + strRtnCapitalCtrl + "',2,'sChineseAmount','" + lCurrencyID+"');\" ";
		}
		String strPrefix = "";
		if (strRtnEndDateCtrl == null || strRtnEndDateCtrl.equals(""))
		{
			strRtnEndDateCtrl = "ItIsNotControl";
		}
		if (strRtnOpenDateCtrl == null || strRtnOpenDateCtrl.equals(""))
		{
			strRtnOpenDateCtrl = "ItIsNotControl";
		}
		if (strRtnCapitalCtrl == null || strRtnCapitalCtrl.equals(""))
		{
			strRtnCapitalCtrl = "ItIsNotControl";
		}
		if (strRtnBalanceCtrl == null || strRtnBalanceCtrl.equals(""))
		{
			strRtnBalanceCtrl = "ItIsNotControl";
		}
		if (strRtnRateCtrl == null || strRtnRateCtrl.equals(""))
		{
			strRtnRateCtrl = "ItIsNotControl";
		}
		if (strRtnIntervalCtrl == null || strRtnIntervalCtrl.equals(""))
		{
			strRtnIntervalCtrl = "ItIsNotControl";
		}
		if (strRtnStartDateCtrl == null || strRtnStartDateCtrl.equals(""))
		{
			strRtnStartDateCtrl = "ItIsNotControl";
		}
		if (strAmount == null || strAmount.equals(""))
		{
			strAmount = "ItIsNotControl";
		}
		String[] strMainNames =
			{ strCtrlName + "Ctrl", strRtnEndDateCtrl, strRtnOpenDateCtrl, strRtnCapitalCtrl, strRtnBalanceCtrl, strRtnRateCtrl, strRtnIntervalCtrl, strRtnStartDateCtrl, strAmount };
		String[] strMainFields = { "DepositNo", "EndDate", "OpenDate", "Capital", "Balance", "Rate", "Interval", "StartDate", "Balance" };
		String[] strReturnNames = { strCtrlName, strCtrlName + "AccountID" };
		String[] strReturnFields = { "SubAccountID", "AccountID" };
		String[] strReturnValues = { String.valueOf(lSubAccountID), String.valueOf(lAccountID)};
		String[] strDisplayNames = { URLEncoder.encode("���ݺ�")};
		String[] strDisplayFields = { "DepositNo" };
		//֧ȡ������ת��ʱ����ʾ��ͬ
		if (lTypeID == 21 || lTypeID == 22 || lTypeID == 23 || lTypeID == 2 || lTypeID == 3)
		{
			if (lDepositTypeID == 1)
			{
				//������ʾ���ݺš���������
				strDisplayNames = new String[] { URLEncoder.encode("���ݺ�"), URLEncoder.encode("���"), URLEncoder.encode("������")};
				strDisplayFields = new String[] { "DepositNo", "Balance", "EndDate" };
			}
			else
			{
				//������ʾ���ݺš�����������
				strDisplayNames = new String[] { URLEncoder.encode("���ݺ�"), URLEncoder.encode("���"), URLEncoder.encode("������")};
				strDisplayFields = new String[] { "DepositNo", "Balance", "OpenDate" };
			}
		}
		int nIndex = 0;
		String strSQL = "";
		boolean isCreateNewBook = Config.getBoolean(ConfigConstant.SETT_FIXEDDRAW_CREATENEWBOOK, true);
		if (strAccountIDCtrl != null && !strAccountIDCtrl.trim().equals(""))
		{
			if(isCreateNewBook && lDepositTypeID == 1){
				
				strSQL =
					"getFixedDepositNoSQL_CREATE_OLD("
					+ lOfficeID
					+ ","
					+ lCurrencyID
					+ ","
					+ lDepositTypeID
					+ ","
					+ strAccountIDCtrl
					+ ".value,"
					+ lUserID
					+ ","
					+ strCtrlName
					+ "Ctrl.value,"
					+ lTypeID
					+ ",'"
					+ Env.getSystemDateString(lOfficeID, lCurrencyID)
					+ "')";
				
			}else{
				strSQL =
					"getFixedDepositNoSQL("
					+ lOfficeID
					+ ","
					+ lCurrencyID
					+ ","
					+ lDepositTypeID
					+ ","
					+ strAccountIDCtrl
					+ ".value,"
					+ lUserID
					+ ","
					+ strCtrlName
					+ "Ctrl.value,"
					+ lTypeID
					+ ",'"
					+ Env.getSystemDateString(lOfficeID, lCurrencyID)
					+ "')";
			}
		}
		else
		{
			strSQL =
				"getFixedDepositNoSQL("
					+ lOfficeID
					+ ","
					+ lCurrencyID
					+ ","
					+ lDepositTypeID
					+ ",-1,"
					+ lUserID
					+ ","
					+ strCtrlName
					+ "Ctrl.value,"
					+ lTypeID
					+ ",'"
					+ Env.getSystemDateString(lOfficeID, lCurrencyID)
					+ "')";
		}
		Log.print("----------sql=" + strSQL);
		try
		{
			OBMagnifier.showZoomCtrl(
					out,
					strMagnifierName,
					strFormName,
					strPrefix,
					strMainNames,
					strMainFields,
					strReturnNames,
					strReturnFields,
					strReturnValues,
					strDisplayNames,
					strDisplayFields,
					nIndex,
					strMainProperty,
					strSQL,
					strNextControls,
					strTitle,
					strFirstTD,
					strSecondTD,
					false,
					false);
				
		}
		catch (Exception e)
		{
			Log.print("���ڴ��ݺŷŴ�[" + strCtrlName + "]�쳣��" + e.toString());
		}
	}
	/**
	 * ��������(֪ͨ)���ݺŷŴ�
	 * @param out
	 * @param lOfficeID ���´�ID
	 * @param lCurrencyID ����ID
	 * @param strFormName ��������
	 * @param strCtrlName �Ŵ����ؼ�����
	 * @param strTitle �Ŵ�����
	 * @param lUserID ��ǰ�û�ID(��ʶֵ)
	 * @param lAccountID ���˻�ID(��ʶֵ) 
	 * @param lSubAccountID ���˻�ID(��ʶֵ)
	 * @param strDepositNo ����(֪ͨ)�浥��(��ʶֵ)
	 * @param lDepositTypeID �浥���ͣ�1�����ڣ�2��֪ͨ��
	 * @param lTypeID �Ŵ����ͣ�ȡֵ���£�
	 * 	1�����ڣ�֪ͨ������--����ƥ��ʱʹ��
	 * 	21�����ڣ�֪ͨ��֧ȡ--ҵ����ʱʹ��
	 *  22�����ڣ�֪ͨ��֧ȡ--ҵ�񸴺�ʱʹ��
	 *  3����������ת��--ҵ����ʱʹ�ã�����ʾ�ѵ��ڵĴ浥��
	 * 
	 * @param strAccountIDCtrl����ѯʱ������
	 * @param strFirstTD ��һ��TD������
	 * @param strSecondTD �ڶ���TD������
	 * @param sNextControlsClient ��һ������������ý���Ŀؼ�
	 * @param strRtnEndDateCtrl ����ֵ�������գ���Ӧ�Ŀؼ���
	 * @param strRtnOpenDateCtrl ����ֵ�������գ���Ӧ�Ŀؼ���
	 * @param strRtnCapitalCtrl ����ֵ�����𣩶�Ӧ�Ŀؼ���
	 * @param strRtnBalanceCtrl ����ֵ������Ӧ�Ŀؼ���
	 */
	public static void createFixedDepositNoCtrl(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lUserID,
		long lAccountID,
		long lSubAccountID,
		String strDepositNo,
		long lDepositTypeID,
		long lTypeID,
		String strAccountIDCtrl,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,
		String strRtnEndDateCtrl,
		String strRtnOpenDateCtrl,
		String strRtnCapitalCtrl,
		String strRtnBalanceCtrl)
	{
		createFixedDepositNoCtrl(
			out,
			lOfficeID,
			lCurrencyID,
			strFormName,
			strCtrlName,
			strTitle,
			lUserID,
			lAccountID,
			lSubAccountID,
			strDepositNo,
			lDepositTypeID,
			lTypeID,
			strAccountIDCtrl,
			strFirstTD,
			strSecondTD,
			strNextControls,
			strRtnEndDateCtrl,
			strRtnOpenDateCtrl,
			strRtnCapitalCtrl,
			strRtnBalanceCtrl,
			"",
			"",
			"");
	}
	
	/**
	 * ����������λ�ͻ��Ŵ�
	 * @param out
	 * @param lOfficeID ���´�ID
	 * @param lCurrencyID ����ID
	 * @param strFormName ��������
	 * @param strCtrlName �Ŵ����ؼ�����
	 * @param strTitle �Ŵ�����
	 * @param lClientID �ͻ�ID(��ʶֵ)
	 * @param strClientNo �ͻ����(��ʶֵ)
	 * @param strFirstTD ��һ��TD������
	 * @param strSecondTD �ڶ���TD������
	 * @param sNextControlsClient ��һ������������ý���Ŀؼ�
	 * @param strRtnClientNameCtrl ����ֵ���ͻ����ƣ���Ӧ�Ŀؼ���
	 */
	public static void createChildClientCtrl(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lClientID,
		String strClientNo,
		long clientId,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,
		String strRtnClientNameCtrl)
	{
		String strMagnifierName = URLEncoder.encode("�ͻ�");
		String strMainProperty = " maxlength='30' value='" + strClientNo + "'";
		String strPrefix = "";
		if (strRtnClientNameCtrl == null || strRtnClientNameCtrl.equals(""))
		{
			strRtnClientNameCtrl = "ItIsNotControl";
		}
		String[] strMainNames = { strCtrlName + "Ctrl", strRtnClientNameCtrl };
		String[] strMainFields = { "clientNo", "clientName" };
		//���⴦��
		long lFromClient = 0;
		if (lClientID > 0)
		{
			lFromClient = 1;
		}
		String[] strReturnNames = { strCtrlName, strCtrlName + "FromClient" };
		String[] strReturnFields = { "ClientID", "FromClient" };
		String[] strReturnValues = { String.valueOf(lClientID), String.valueOf(lFromClient)};
		String[] strDisplayNames = { URLEncoder.encode("�ͻ����"), URLEncoder.encode("�ͻ�����")};
		String[] strDisplayFields = { "clientNo", "clientName" };
		int nIndex = 0;
		String strSQL = "getChildClientSQL(" + lOfficeID + "," +clientId+","+ strCtrlName + "Ctrl.value)";
		try
		{
			OBMagnifier.showZoomCtrl(
				out,
				strMagnifierName,
				strFormName,
				strPrefix,
				strMainNames,
				strMainFields,
				strReturnNames,
				strReturnFields,
				strReturnValues,
				strDisplayNames,
				strDisplayFields,
				nIndex,
				strMainProperty,
				strSQL,
				strNextControls,
				strTitle,
				strFirstTD,
				strSecondTD,false,false);
		}
		catch (Exception e)
		{
			Log.print("�ͻ��Ŵ�[" + strCtrlName + "]�쳣��" + e.toString());
		}
	}
	
	/**
	 * �����ʽ�ƻ��Ŵ�
	 * @author ��Ӣ��
	 * @param out
	 * @param lOfficeID ���´�ID
	 * @param lCurrencyID ����ID
	 * @param strFormName ��������
	 * @param strCtrlName �Ŵ����ؼ�����
	 * @param strTitle �Ŵ�����
	 * @param lClientID �ͻ�ID(��ʶֵ)
	 * @param strFirstTD ��һ��TD������
	 * @param strSecondTD �ڶ���TD������
	 * @param strNextControls ��һ������������ý���Ŀؼ�
	 * @param strRtnClientNameCtrl ����ֵ���ƻ���ţ���Ӧ�Ŀؼ���
	 */
	public static void createFundPlanCtrl(
			JspWriter out,
			long lOfficeID,
			long lCurrencyID,
			String strFormName,
			String strCtrlName,
			String strTitle,
			long lClientID,
			String strFirstTD,
			String strSecondTD,
			String[] strNextControls)
		{
			String strMagnifierName = URLEncoder.encode("�ʽ�ƻ��Ŵ�");
			String strMainProperty = "";
			String strPrefix = "";
			String[] strMainNames = { strCtrlName + "Ctrl"};
			String[] strMainFields = { "cpcode" };
			String[] strReturnNames = { strCtrlName};
			String[] strReturnFields = { "id" };
			String[] strReturnValues = {""};
			String[] strDisplayNames = { URLEncoder.encode("���"), URLEncoder.encode("�ʽ�ƻ����")};
			String[] strDisplayFields = { "id", "cpcode" };
			int nIndex = 0;
			String strSQL = "getFundPlanSQL("+strMainNames[0]+".value,"+lCurrencyID+","+lClientID+","+lOfficeID+")";
			try
			{
				OBMagnifier.showZoomCtrl(
					out,
					strMagnifierName,
					strFormName,
					strPrefix,
					strMainNames,
					strMainFields,
					strReturnNames,
					strReturnFields,
					strReturnValues,
					strDisplayNames,
					strDisplayFields,
					nIndex,
					strMainProperty,
					strSQL,
					strNextControls,
					strTitle,
					strFirstTD,
					strSecondTD,false,false);
			}
			catch (Exception e)
			{
				Log.print("�ʽ�ƻ��Ŵ�[" + strCtrlName + "]�쳣��" + e.toString());
			}
		}
	
	/**
	 * �¼��˻��Ŵ󾵹����ֶΣ�
	 * @author ���ָ�
	 * @param out
	 * @param strFormName
	 * @param strCtrlName
	 * @param lAccountID
	 * @param strAccountNO
	 * @param strRtnAccountNameCtrlName
	 * @param strNextControls
	 * @param sConditions
	 * @param officeID
	 * @param clientID
	 */
	 public static void createSubAccountCtrlReturnCode(JspWriter out, String strFormName, String strCtrlName, long lAccountID, String strAccountNO, String strRtnAccountNameCtrlName, String strNextControls[], 
	            String sConditions[], long officeID, long clientID)
	    {
	        String strMagnifierName = URLEncoder.encode("�˻�");
	        String strTitle = "�����˺�";
	        String strMainProperty = " size='20' maxlength='30' value='" + strAccountNO + "'";
	        String strPrefix = "";
	        if(strRtnAccountNameCtrlName == null || strRtnAccountNameCtrlName.equals(""))
	            strRtnAccountNameCtrlName = "ItIsNotControl";
	        String strMainNames[] = {
	            strCtrlName + "Ctrl", strRtnAccountNameCtrlName
	        };
	        String strMainFields[] = {
	            "AccountNO", "AccountName"
	        };
	        String strReturnNames[] = {
	            strCtrlName
	        };
	        String strReturnFields[] = {
	            "AccountID"
	        };
	        String strReturnValues[] = {
	            String.valueOf(lAccountID)
	        };
	        String strDisplayNames[] = {
	        		URLEncoder.encode("�˺�"), URLEncoder.encode("�˻�����"), URLEncoder.encode("��������")
	        };
	        String strDisplayFields[] = {
	            "AccountNO", "AccountName", "BankName"
	        };
	        int nIndex = 0;
	        String strSQL = null;
	        StringBuffer bufferSQL = new StringBuffer();
	        bufferSQL.append("getSubAccountSQLForEbank(" + strCtrlName + "Ctrl.value," + clientID + ",");
//	        if(Env.isHQ(officeID))
//	            bufferSQL.append("-1");
//	        else
	            bufferSQL.append(officeID);
	        if(sConditions == null || sConditions.length <= 0)
	        {
	            strSQL = bufferSQL.toString() + ")";
	        } else
	        {
	            bufferSQL.append(",");
	            int length = sConditions.length - 1;
	            for(int i = 0; i < sConditions.length; i++)
	            {
	                if(sConditions[i] == null)
	                    bufferSQL.append("-1");
	                else
	                    bufferSQL.append(sConditions[i] + ".value");
	                if(i < length)
	                    bufferSQL.append(",");
	            }

	            bufferSQL.append(")");
	            strSQL = bufferSQL.toString();
	        }
	        System.out.println("***strSQL=" + strSQL);
	        try
	        {
	        	OBMagnifier.showZoomCtrl(
	    				out,
	    				strMagnifierName,
	    				strFormName,
	    				strPrefix,
	    				strMainNames,
	    				strMainFields,
	    				strReturnNames,
	    				strReturnFields,
	    				strReturnValues,
	    				strDisplayNames,
	    				strDisplayFields,
	    				nIndex,
	    				strMainProperty,
	    				strSQL,
	    				strNextControls,
	    				strTitle,
	    				"","",false,false);
	        }
	        catch(Exception e)
	        {
	            Log.print("�˻��Ŵ�[" + strCtrlName + "]�쳣��" + e.toString());
	        }
	    }

	
	/**
	 * ��ʾ����˺ţ��������Ŵ�
	 * @author yanliu
	 * @param out
	 * @param strMagnifierName �Ŵ󾵵�����
	 * @param strFormName ��ҳ�������
	 * @param strPrefix �ؼ�����ǰ׺
	 * @param strMainNames �Ŵ󾵻�����λֵ�б�
	 * @param strMainFields �Ŵ󾵻�����λ��Ӧ�ı���ֶ�
	 * @param strReturnNames �Ŵ󾵷���ֵ�б�(����ֵ)
	 * @param strReturnFields �Ŵ󾵷���ֵ(����ֵ)��Ӧ�ı���ֶ��б�
	 * @param strReturnValues �Ŵ󾵷���ֵ(����ֵ)��Ӧ�ĳ�ʼֵ
	 * @param strDisplayNames �Ŵ�С������ʾ����λ����
	 * @param strDisplayFields �Ŵ�С������ʾ��λ��Ӧ�ı���ֶ�
	 * @param nIndex ȷ��ѡ����,��0��ʼ,���С��0,��Ĭ��������λ����ѡ��,������ڻ����strDisplayNames.length,��û��ѡ����
	 * @param strMainProperty �Ŵ���λ����
	 * @param strSQL �Ŵ󾵲�ѯSQL���
	 * @param strNextControls ������һ������
	 * @param strTitle ��λ����
	 * @param strFirstTD ��һ��TD������
	 * @param strSecondTD �ڶ���TD������ 
	 * @param blnIsOptional �Ƿ��ǿ�ѡ�Ŀǰ����ժҪ���ֽ�����Ŵ����ã�
	 * @param blnIsRateCtrl �Ƿ������ʿؼ�
	 * @throws Exception
	 */
	public static void showPayerAcctZoomCtrl(
		JspWriter out,
		String strMagnifierName,
		String strFormName,
		String strPrefix,
		String[] strMainNames,
		String[] strMainFields,
		String[] strReturnNames,
		String[] strReturnFields,
		String[] strReturnValues,
		String[] strDisplayNames,
		String[] strDisplayFields,
		int nIndex,
		String strMainProperty,
		String strSQL,
		String[] strNextControls,
		String strTitle,
		String strFirstTD,
		String strSecondTD,
		boolean blnIsOptional,
		boolean blnIsRateCtrl)
		throws Exception
	{
		String strButtonName = "button";
		try
		{
			//���Ŵ󾵲���
			checkValue(strMainNames, strMainFields, true);
			checkValue(strReturnNames, strReturnFields, strReturnValues, false);
			checkValue(strDisplayNames, strDisplayFields, true);
			if (strMagnifierName == null || strFormName == null || strFormName.equals("") || strSQL == null || strSQL.equals(""))
			{
				throw new IException(ZOOMERRORMSG);
			}
			if (strNextControls == null)
			{
				throw new IException(ZOOMERRORMSG);
			}
			if (strFirstTD == null)
			{
				strFirstTD = "";
			}
			if (strSecondTD == null)
			{
				strSecondTD = "";
			}
			//������
			//����ǰ׺
			if (strPrefix != null && !strPrefix.trim().equals(""))
			{
				for (int i = 0; i < strMainNames.length; i++)
				{
					strMainNames[i] = strPrefix + strMainNames[i];
				}
				for (int i = 0; i < strReturnNames.length; i++)
				{
					strReturnNames[i] = strPrefix + strReturnNames[i];
				}
			}
			//�������ڵ�����
			String sFeatures = null;
			if (strDisplayNames.length < 3)
			{
				sFeatures = "toolbar=no,menubar=no,resizable=no,location=no,width=520,height=450,scrollbars=yes";
			}
			else
			{
				sFeatures = "toolbar=no,menubar=no,resizable=no,location=no,width=624,height=540,scrollbars=yes";
			}
			//���ɴ��ݸ��������ڵĲ����ַ���
			String strParam = "";
			strParam = "strFormName=" + strFormName;
			strParam += "&strMagnifierName=" + strMagnifierName;
			strParam += "&nIndex=" + nIndex;
			if (!isSQL(strSQL))
			{
				strParam += "&strSQL='+" + strSQL + "+'";
			}
			else
			{
				strParam += "&strSQL=" + getSQL(strSQL);
			}
			for (int i = 0; i < strNextControls.length; i++)
			{
				strParam += "&strNextControls=" + strNextControls[i];
			}
			for (int i = 0; i < strMainNames.length; i++)
			{
				strParam += "&strMainNames=" + strMainNames[i];
				strParam += "&strMainFields=" + strMainFields[i];
			}
			if (strReturnNames != null)
			{
				boolean bValue = false;
				if (strReturnValues != null && strReturnValues.length == strReturnNames.length)
				{
					bValue = true;
				}
				for (int i = 0; i < strReturnNames.length; i++)
				{
					//�����������
					strParam += "&strReturnNames=" + strReturnNames[i];
					strParam += "&strReturnFields=" + strReturnFields[i];
					if (bValue)
					{
						out.println("<input type=\"hidden\" name=\"" + strReturnNames[i] + "\" value=\"" + strReturnValues[i] + "\">");
					}
					else
					{
						out.println("<input type=\"hidden\" name=\"" + strReturnNames[i] + "\">");
					}
				}
			}
			for (int i = 0; i < strDisplayNames.length; i++)
			{
				//�����������
				strParam += "&strDisplayNames=" + strDisplayNames[i];
				strParam += "&strDisplayFields=" + strDisplayFields[i];
			}
			//Log.print("strParam = " + strParam);
			//���ɲ�ѯ��ť���¼��ַ���
			String sOnKeydown =
				"if("
					+ strFormName
					+ "."
					+ strMainNames[0]
					+ ".disabled == false) {gnIsSelectCtrl=1;window.open('/NASApp/iTreasury-ebank/magnifier/ShowBankAccountCodeZoom.jsp?"
					+ strParam
					+ "', 'SelectAnything', '"
					+ sFeatures
					+ "', false);}";
			//
			String sOnKeyUp = "";
			if (strReturnNames != null)
			{
				for (int i = 0; i < strReturnNames.length; i++)
				{
					sOnKeyUp += strReturnNames[i] + ".value = -1; ";
				}
			}
			//int iPos = strMainProperty.toLowerCase().indexOf("disabled");
			int iPos = -1;
			//��ʾ�ؼ�
			if (iPos == -1)
			{
				out.println(
					"<td "
						+ strFirstTD
						+ ">"
						+ strTitle
						+ "��&nbsp;"
						+ "<a href=#><img name=\""
						+ strButtonName
						+ "\" src='/webob/graphics/zoom.gif' border=0 onclick=\""
						+ sOnKeydown
						+ "\"></a></td>");
				//image
			}
			else
			{
				out.println("<td " + strFirstTD + ">" + strTitle + "��&nbsp;" + "<a href=#><img name=\"" + strButtonName + "\" src='/webob/graphics/zoom.gif' border=0 width='568'></a></td>");
				//image
			}
			//blnIsOptional,�Ƿ��ѡ�����ժҪ���ֽ�����Ŵ���Ч��
			if (blnIsOptional == true)
			{
				if (strNextControls != null && strNextControls.length > 0)
				{
					strMainProperty = " onfocus=\"nextfield='" + strNextControls[0] + "'\" " + strMainProperty;
				}
				if (blnIsRateCtrl == true)
				{
					out.println("<td" + strSecondTD + "><input type=\"text\" name=\"" + strMainNames[0] + "\" class=\"box\" " + strMainProperty + ">%</td>");
				}
				else
				{
					out.println("<td" + strSecondTD + "><input type=\"text\" name=\"" + strMainNames[0] + "\" class=\"box\" " + strMainProperty + "></td>");
				}
			}
			else
			{
				if (blnIsRateCtrl == true)
				{
					out.println(
						"<td"
							+ strSecondTD
							+ "><input type=\"text\" name=\""
							+ strMainNames[0]
							+ "\" class=\"box\" "
							+ strMainProperty
							+ " onKeyDown=\"if(event.keyCode==13) "
							+ sOnKeydown
							+ "\" onKeyUp=\""
							+ sOnKeyUp
							+ "\">%</td>");
				}
				else
				{
					out.println(
						"<td"
							+ strSecondTD
							+ "><input type=\"text\" name=\""
							+ strMainNames[0]
							+ "\" class=\"box\" "
							+ strMainProperty
							+ " onKeyDown=\"if(event.keyCode==13) "
							+ sOnKeydown
							+ "\" onKeyUp=\""
							+ sOnKeyUp
							+ "\"></td>");
				}
			}
		}
		catch (Exception exp)
		{
			throw exp;
		}
	}
	/*
	 * �н�,Ϊ���� �»���Ա��λ ����Ŵ����
	 * */
	public static void showPayerAcctZoomCtrlZj(
			JspWriter out,
			String strMagnifierName,
			String strFormName,
			String strPrefix,
			String[] strMainNames,
			String[] strMainFields,
			String[] strReturnNames,
			String[] strReturnFields,
			String[] strReturnValues,
			String[] strDisplayNames,
			String[] strDisplayFields,
			int nIndex,
			String strMainProperty,
			String strSQL,
			String[] strNextControls,
			String strTitle,
			String strFirstTD,
			String strSecondTD,
			boolean blnIsOptional,
			boolean blnIsRateCtrl)
			throws Exception
		{
			String strButtonName = "button";
			try
			{
				//���Ŵ󾵲���
				checkValue(strMainNames, strMainFields, true);
				checkValue(strReturnNames, strReturnFields, strReturnValues, false);
				checkValue(strDisplayNames, strDisplayFields, true);
				if (strMagnifierName == null || strFormName == null || strFormName.equals("") || strSQL == null || strSQL.equals(""))
				{
					throw new IException(ZOOMERRORMSG);
				}
				if (strNextControls == null)
				{
					throw new IException(ZOOMERRORMSG);
				}
				if (strFirstTD == null)
				{
					strFirstTD = "";
				}
				if (strSecondTD == null)
				{
					strSecondTD = "";
				}
				//������
				//����ǰ׺
				if (strPrefix != null && !strPrefix.trim().equals(""))
				{
					for (int i = 0; i < strMainNames.length; i++)
					{
						strMainNames[i] = strPrefix + strMainNames[i];
					}
					for (int i = 0; i < strReturnNames.length; i++)
					{
						strReturnNames[i] = strPrefix + strReturnNames[i];
					}
				}
				//�������ڵ�����
				String sFeatures = null;
				if (strDisplayNames.length < 3)
				{
					sFeatures = "toolbar=no,menubar=no,resizable=no,location=no,width=520,height=450,scrollbars=yes";
				}
				else
				{
					sFeatures = "toolbar=no,menubar=no,resizable=no,location=no,width=624,height=540,scrollbars=yes";
				}
				//���ɴ��ݸ��������ڵĲ����ַ���
				String strParam = "";
				strParam = "strFormName=" + strFormName;
				strParam += "&strMagnifierName=" + strMagnifierName;
				strParam += "&nIndex=" + nIndex;
				if (!isSQL(strSQL))
				{
					strParam += "&strSQL='+" + strSQL + "+'";
				}
				else
				{
					strParam += "&strSQL=" + getSQL(strSQL);
				}
				for (int i = 0; i < strNextControls.length; i++)
				{
					strParam += "&strNextControls=" + strNextControls[i];
				}
				for (int i = 0; i < strMainNames.length; i++)
				{
					strParam += "&strMainNames=" + strMainNames[i];
					strParam += "&strMainFields=" + strMainFields[i];
				}
				if (strReturnNames != null)
				{
					boolean bValue = false;
					if (strReturnValues != null && strReturnValues.length == strReturnNames.length)
					{
						bValue = true;
					}
					for (int i = 0; i < strReturnNames.length; i++)
					{
						//�����������
						strParam += "&strReturnNames=" + strReturnNames[i];
						strParam += "&strReturnFields=" + strReturnFields[i];
						if (bValue)
						{
							out.println("<input type=\"hidden\" name=\"" + strReturnNames[i] + "\" value=\"" + strReturnValues[i] + "\">");
						}
						else
						{
							out.println("<input type=\"hidden\" name=\"" + strReturnNames[i] + "\">");
						}
					}
				}
				for (int i = 0; i < strDisplayNames.length; i++)
				{
					//�����������
					strParam += "&strDisplayNames=" + strDisplayNames[i];
					strParam += "&strDisplayFields=" + strDisplayFields[i];
				}
				//Log.print("strParam = " + strParam);
				//���ɲ�ѯ��ť���¼��ַ���
				String sOnKeydown =
					"if("
						+ strFormName
						+ "."
						+ strMainNames[0]
						+ ".disabled == false) {gnIsSelectCtrl=1;window.open('/NASApp/iTreasury-ebank/magnifier/ShowBankAccountXH.jsp?"
						+ strParam
						+ "', 'SelectAnything', '"
						+ sFeatures
						+ "', false);}";
				//
				String sOnKeyUp = "";
				if (strReturnNames != null)
				{
					for (int i = 0; i < strReturnNames.length; i++)
					{
						sOnKeyUp += strReturnNames[i] + ".value = -1; ";
					}
				}
				//int iPos = strMainProperty.toLowerCase().indexOf("disabled");
				int iPos = -1;
				//��ʾ�ؼ�
				if (iPos == -1)
				{
					out.println(
						"<td "
							+ strFirstTD
							+ ">"
							+ strTitle
							+ ":&nbsp;"
							+ "<a href=#><img name=\""
							+ strButtonName
							+ "\" src='/webob/graphics/zoom.gif' border=0 onclick=\""
							+ sOnKeydown
							+ "\"></a></td>");
					//image
				}
				else
				{
					out.println("<td " + strFirstTD + ">" + strTitle + ":&nbsp;" + "<a href=#><img name=\"" + strButtonName + "\" src='/webob/graphics/zoom.gif' border=0 ></a></td>");
					//image
				}
				//blnIsOptional,�Ƿ��ѡ�����ժҪ���ֽ�����Ŵ���Ч��
				if (blnIsOptional == true)
				{
					if (strNextControls != null && strNextControls.length > 0)
					{
						strMainProperty = " onfocus=\"nextfield='" + strNextControls[0] + "'\" " + strMainProperty;
					}
					if (blnIsRateCtrl == true)
					{
						out.println("<td" + strSecondTD + "><input type=\"text\" name=\"" + strMainNames[0] + "\" class=\"box\" " + strMainProperty + ">%</td>");
					}
					else
					{
						out.println("<td" + strSecondTD + "><input type=\"text\" name=\"" + strMainNames[0] + "\" class=\"box\" " + strMainProperty + "></td>");
					}
				}
				else
				{
					if (blnIsRateCtrl == true)
					{
						out.println(
							"<td"
								+ strSecondTD
								+ "><input type=\"text\" name=\""
								+ strMainNames[0]
								+ "\" class=\"box\" "
								+ strMainProperty
								+ " onKeyDown=\"if(event.keyCode==13) "
								+ sOnKeydown
								+ "\" onKeyUp=\""
								+ sOnKeyUp
								+ "\">%</td>");
					}
					else
					{
						out.println(
							"<td"
								+ strSecondTD
								+ "><input type=\"text\" name=\""
								+ strMainNames[0]
								+ "\" class=\"box\" "
								+ strMainProperty
								+ " onKeyDown=\"if(event.keyCode==13) "
								+ sOnKeydown
								+ "\" onKeyUp=\""
								+ sOnKeyUp
								+ "\"></td>");
					}
				}
			}
			catch (Exception exp)
			{
				throw exp;
			}
		}
	/**
	 * ��ʾ��ͨ�Ŵ�
	 * @param out
	 * @param strMagnifierName �Ŵ󾵵�����
	 * @param strFormName ��ҳ�������
	 * @param strPrefix �ؼ�����ǰ׺
	 * @param strMainNames �Ŵ󾵻�����λֵ�б�
	 * @param strMainFields �Ŵ󾵻�����λ��Ӧ�ı���ֶ�
	 * @param strReturnNames �Ŵ󾵷���ֵ�б�(����ֵ)
	 * @param strReturnFields �Ŵ󾵷���ֵ(����ֵ)��Ӧ�ı���ֶ��б�
	 * @param strReturnValues �Ŵ󾵷���ֵ(����ֵ)��Ӧ�ĳ�ʼֵ
	 * @param strDisplayNames �Ŵ�С������ʾ����λ����
	 * @param strDisplayFields �Ŵ�С������ʾ��λ��Ӧ�ı���ֶ�
	 * @param nIndex ȷ��ѡ����,��0��ʼ,���С��0,��Ĭ��������λ����ѡ��,������ڻ����strDisplayNames.length,��û��ѡ����
	 * @param strMainProperty �Ŵ���λ����
	 * @param strSQL �Ŵ󾵲�ѯSQL���
	 * @param strNextControls ������һ������
	 * @param strTitle ��λ����
	 * @param strFirstTD ��һ��TD������
	 * @param strSecondTD �ڶ���TD������ 
	 * @param blnIsOptional �Ƿ��ǿ�ѡ�Ŀǰ����ժҪ���ֽ�����Ŵ����ã�
	 * @param blnIsRateCtrl �Ƿ������ʿؼ�
	 * @throws Exception
	 */
	public static void showZoomCtrl(
		JspWriter out,
		String strMagnifierName,
		String strFormName,
		String strPrefix,
		String[] strMainNames,
		String[] strMainFields,
		String[] strReturnNames,
		String[] strReturnFields,
		String[] strReturnValues,
		String[] strDisplayNames,
		String[] strDisplayFields,
		int nIndex,
		String strMainProperty,
		String strSQL,
		String[] strNextControls,
		String strTitle,
		String strFirstTD,
		String strSecondTD,
	
		boolean blnIsOptional,
		boolean blnIsRateCtrl)
		throws Exception
	{
		String strButtonName = "button";
		try
		{
			//���Ŵ󾵲���
			checkValue(strMainNames, strMainFields, true);
			checkValue(strReturnNames, strReturnFields, strReturnValues, false);
			checkValue(strDisplayNames, strDisplayFields, true);
			if (strMagnifierName == null || strFormName == null || strFormName.equals("") || strSQL == null || strSQL.equals(""))
			{
				throw new IException(ZOOMERRORMSG);
			}
			if (strNextControls == null)
			{
				throw new IException(ZOOMERRORMSG);
			}
			if (strFirstTD == null)
			{
				strFirstTD = "";
			}
			if (strSecondTD == null)
			{
				strSecondTD = "";
			}
			//������
			//����ǰ׺
			if (strPrefix != null && !strPrefix.trim().equals(""))
			{
				for (int i = 0; i < strMainNames.length; i++)
				{
					strMainNames[i] = strPrefix + strMainNames[i];
				}
				for (int i = 0; i < strReturnNames.length; i++)
				{
					strReturnNames[i] = strPrefix + strReturnNames[i];
				}
			}
			//�������ڵ�����
			String sFeatures = null;
			if (strDisplayNames.length < 3)
			{
				sFeatures = "toolbar=no,menubar=no,resizable=no,location=no,width=520,height=450,scrollbars=yes";
			}
			else
			{
				sFeatures = "toolbar=no,menubar=no,resizable=no,location=no,width=624,height=540,scrollbars=yes";
			}
			//���ɴ��ݸ��������ڵĲ����ַ���
			String strParam = "";
			strParam = "strFormName=" + strFormName;
			strParam += "&strMagnifierName=" + strMagnifierName;
			strParam += "&nIndex=" + nIndex;
			if (!isSQL(strSQL))
			{
				strParam += "&strSQL='+" + strSQL + "+'";
			}
			else
			{
				strParam += "&strSQL=" + getSQL(strSQL);
			}
			for (int i = 0; i < strNextControls.length; i++)
			{
				strParam += "&strNextControls=" + strNextControls[i];
			}
			for (int i = 0; i < strMainNames.length; i++)
			{
				strParam += "&strMainNames=" + strMainNames[i];
				strParam += "&strMainFields=" + strMainFields[i];
			}
			if (strReturnNames != null)
			{
				boolean bValue = false;
				if (strReturnValues != null && strReturnValues.length == strReturnNames.length)
				{
					bValue = true;
				}
				for (int i = 0; i < strReturnNames.length; i++)
				{
					//�����������
					strParam += "&strReturnNames=" + strReturnNames[i];
					strParam += "&strReturnFields=" + strReturnFields[i];
					if (bValue)
					{
						out.println("<input type=\"hidden\" name=\"" + strReturnNames[i] + "\" value=\"" + strReturnValues[i] + "\">");
					}
					else
					{
						out.println("<input type=\"hidden\" name=\"" + strReturnNames[i] + "\">");
					}
				}
			}
			for (int i = 0; i < strDisplayNames.length; i++)
			{
				//�����������
				strParam += "&strDisplayNames=" + strDisplayNames[i];
				strParam += "&strDisplayFields=" + strDisplayFields[i];
			}
			//Log.print("strParam = " + strParam);
			//���ɲ�ѯ��ť���¼��ַ���
			String sOnKeydown =
				"if("
					+ strFormName
					+ "."
					+ strMainNames[0]
					+ ".disabled == false) {"
					+ strFormName
					+ "."
					+ strMainNames[0]
					+ ".focus();"
					+ " gnIsSelectCtrl=1;window.open('/NASApp/iTreasury-ebank/magnifier/ShowMagnifierZoom.jsp?"
					+ strParam
					+ "', 'SelectAnything', '"
					+ sFeatures
					+ "', false);}";
			//
			String sOnKeyUp = "";
			
			if (strReturnNames != null)
			{
				
				for (int i = 0; i < strReturnNames.length; i++)
				{
					sOnKeyUp += strReturnNames[i] + ".value = -1; ";
				}
			}
			//int iPos = strMainProperty.toLowerCase().indexOf("disabled");
			int iPos = -1;
			//��ʾ�ؼ�
			if (iPos == -1)
			{
				out.println(
					"<td width=130"
						+ strFirstTD
						+ ">"
						+ strTitle
						+ "��&nbsp;"
						+ "<a href=#><img name=\""
						+ strButtonName
						+ "\" src='/webob/graphics/zoom.gif' border=0 onclick=\""
						+ sOnKeydown
						+ "\"></a></td>");
				//image
			}
			else
			{
				out.println("<td" + strFirstTD + ">" + strTitle + "��&nbsp;" + "<a href=#><img name=\"" + strButtonName + "\" src='/webob/graphics/zoom.gif' border=0 ></a></td>");
				//image
			}
			//blnIsOptional,�Ƿ��ѡ�����ժҪ���ֽ�����Ŵ���Ч��
			if (blnIsOptional == true)
			{
				if (strNextControls != null && strNextControls.length > 0)
				{
					strMainProperty = " onfocus=\"nextfield='" + strNextControls[0] + "'\" " + strMainProperty;
				}
				if (blnIsRateCtrl == true)
				{
					out.println("<td" + strSecondTD + "><input type=\"text\" name=\"" + strMainNames[0] + "\" class=\"box\" " + strMainProperty + ">%</td>");
				}
				else
				{
					out.println("<td" + strSecondTD + "><input type=\"text\" name=\"" + strMainNames[0] + "\" class=\"box\" " + strMainProperty + "></td>");
				}
			}
			else
			{
				if (blnIsRateCtrl == true)
				{
					out.println(
						"<td"
							+ strSecondTD
							+ "><input type=\"text\" name=\""
							+ strMainNames[0]
							+ "\" class=\"box\" "
							+ strMainProperty
							+ " onKeyDown=\"if(event.keyCode==13) "
							+ sOnKeydown
							+ "\" onKeyUp=\""
							+ sOnKeyUp
							+ "\">%</td>");
				}
				else
				{
					out.println(
						"<td"
							+ strSecondTD
							+ "><input type=\"text\" name=\""
							+ strMainNames[0]
							+ "\" class=\"box\" "
							+ strMainProperty
							+ " onKeyDown=\"if(event.keyCode==13) "
							+ sOnKeydown
							+ "\" onKeyUp=\""
							+ sOnKeyUp
							+ "\"></td>");
				}
			}
		}
		catch (Exception exp)
		{
			throw exp;
		}
	}
	/**
	* ��ʾ��ͨ�Ŵ�
	* @param JspWriter out
	* @param String strMagnifierName �Ŵ󾵵�����
	* @param String strFormName ��ҳ�������
	* @param strPrefix strPrefix �ؼ�����ǰ׺
	* @param String[] strMainNames �Ŵ󾵻�����λֵ�б�
	* @param String[] strMainFields �Ŵ󾵻�����λ��Ӧ�ı���ֶ�
	* @param String[] strReturnNames �Ŵ󾵷���ֵ�б�(����ֵ)
	* @param String[] strReturnFields �Ŵ󾵷���ֵ(����ֵ)��Ӧ�ı���ֶ��б�
	* @param String   strReturnInitValues �Ŵ󾵻�����λ��Ӧ�ĳ�ʼֵ
	* @param String[] strReturnValues �Ŵ󾵷���ֵ(����ֵ)��Ӧ�ĳ�ʼֵ
	* @param String[] strDisplayNames �Ŵ�С������ʾ����λ����
	* @param String[] strDisplayFields �Ŵ�С������ʾ��λ��Ӧ�ı���ֶ�
	* @param nIndex ȷ��ѡ����,��0��ʼ,���С��0,��Ĭ��������λ����ѡ��,������ڻ����strDisplayNames.length,��û��ѡ����
	* @param strMainProperty �Ŵ���λ����
	* @param strSQL �Ŵ󾵲�ѯSQL���
	* @param strMatchValue �Ŵ�Ҫģ��ƥ����ֶ�
	* @param strNextControls ������һ������
	* @param strTitle ��λ����
	* @param strFirstTD ��һ��TD������
	* @param strSecondTD �ڶ���TD������ 
	* @throws Exception
	*/
	public static void showZoomCtrl(
		JspWriter out,
		String strMagnifierName,
		String strFormName,
		String strPrefix,
		String[] strMainNames,
		String[] strMainFields,
		String[] strReturnNames,
		String[] strReturnFields,
		String strReturnInitValues,
		String[] strReturnValues,
		String[] strDisplayNames,
		String[] strDisplayFields,
		int nIndex,
		String strMainProperty,
		String strSQL,
		String strMatchValue,
		String strNextControls,
		String strTitle,
		String strFirstTD,
		String strSecondTD)
		throws Exception
	{
		showZoomCtrl(
			out,
			strMagnifierName,
			strFormName,
			strPrefix,
			strMainNames,
			strMainFields,
			strReturnNames,
			strReturnFields,
			strReturnInitValues,
			strReturnValues,
			strDisplayNames,
			strDisplayFields,
			nIndex,
			strMainProperty,
			strSQL,
			strMatchValue,
			strNextControls,
			strTitle,
			strFirstTD,
			strSecondTD,
			false,
			false);
	}
	
	/**
		* ��ʾ��ͨ�Ŵ�
		* @param JspWriter out
		* @param String strMagnifierName �Ŵ󾵵�����
		* @param String strFormName ��ҳ�������
		* @param strPrefix strPrefix �ؼ�����ǰ׺
		* @param String[] strMainNames �Ŵ󾵻�����λֵ�б�
		* @param String[] strMainFields �Ŵ󾵻�����λ��Ӧ�ı���ֶ�
		* @param String[] strReturnNames �Ŵ󾵷���ֵ�б�(����ֵ)
		* @param String[] strReturnFields �Ŵ󾵷���ֵ(����ֵ)��Ӧ�ı���ֶ��б�
		* @param String   strReturnInitValues �Ŵ󾵻�����λ��Ӧ�ĳ�ʼֵ
		* @param String[] strReturnValues �Ŵ󾵷���ֵ(����ֵ)��Ӧ�ĳ�ʼֵ
		* @param String[] strDisplayNames �Ŵ�С������ʾ����λ����
		* @param String[] strDisplayFields �Ŵ�С������ʾ��λ��Ӧ�ı���ֶ�
		* @param nIndex ȷ��ѡ����,��0��ʼ,���С��0,��Ĭ��������λ����ѡ��,������ڻ����strDisplayNames.length,��û��ѡ����
		* @param strMainProperty �Ŵ���λ����
		* @param strSQL �Ŵ󾵲�ѯSQL���
		* @param strMatchValue �Ŵ�Ҫģ��ƥ����ֶ�
		* @param strNextControls ������һ������
		* @param strTitle ��λ����
		* @param strFirstTD ��һ��TD������
		* @param strSecondTD �ڶ���TD������ 
		* @param blnIsOptional �Ƿ��ǿ�ѡ�Ŀǰ����ժҪ���ֽ�����Ŵ����ã�
		* @param blnIsRateCtrl �Ƿ������ʿؼ�
		* @throws Exception
		*/
	public static void showZoomCtrl(
		JspWriter out,
		String strMagnifierName,
		String strFormName,
		String strPrefix,
		String[] strMainNames,
		String[] strMainFields,
		String[] strReturnNames,
		String[] strReturnFields,
		String strReturnInitValues,
		String[] strReturnValues,
		String[] strDisplayNames,
		String[] strDisplayFields,
		int nIndex,
		String strMainProperty,
		String strSQL,
		String strMatchValue,
		String strNextControls,
		String strTitle,
		String strFirstTD,
		String strSecondTD,
		boolean blnIsOptional,
		boolean blnIsRateCtrl)
		throws Exception
	{
		String strButtonName = "button";
		try
		{
			//���Ŵ󾵲���
			checkValue(strMainNames, strMainFields, true);
			checkValue(strReturnNames, strReturnFields, strReturnValues, false);
			checkValue(strDisplayNames, strDisplayFields, true);
			if (strMagnifierName == null || strFormName == null || strFormName.equals("") || strSQL == null || strSQL.equals(""))
			{
				throw new IException(ZOOMERRORMSG);
			}
			if (strNextControls == null)
			{
				throw new IException(ZOOMERRORMSG);
			}
			if (strMatchValue == null || strMatchValue.equals(""))
			{
				strMatchValue = strMainFields[0];
			}
			if (strFirstTD == null)
			{
				strFirstTD = "";
			}
			if (strSecondTD == null)
			{
				strSecondTD = "";
			}
			if (strReturnInitValues == null)
			{
				strReturnInitValues = "";
			}
			//������
			//����ǰ׺
			if (strPrefix != null && !strPrefix.trim().equals(""))
			{
				for (int i = 0; i < strMainNames.length; i++)
				{
					strMainNames[i] = strPrefix + strMainNames[i];
				}
				for (int i = 0; i < strReturnNames.length; i++)
				{
					strReturnNames[i] = strPrefix + strReturnNames[i];
				}
			}
			//�������ڵ�����
			String sFeatures = null;
			if (strDisplayNames.length < 3)
			{
				sFeatures = "toolbar=no,menubar=no,resizable=no,location=no,width=520,height=450,scrollbars=yes";
			}
			else
			{
				sFeatures = "toolbar=no,menubar=no,resizable=no,location=no,width=624,height=540,scrollbars=yes";
			}
			//���ɴ��ݸ��������ڵĲ����ַ���
			String strParam = "";
			strParam = "strFormName=" + strFormName;
			strParam += "&strMagnifierName=" + strMagnifierName;
			strParam += "&nIndex=" + nIndex;
			if (!isSQL(strSQL))
			{
				strParam += "&strSQL= select * from ( '+" + strSQL + "+' ) where 1=1 '+get" + strMainNames[0] + "(" + strMainNames[0] + ".value)+'";
			}
			else
			{
				strParam += "&strSQL= select * from ( " + strSQL + " ) where 1=1 '+get" + strMainNames[0] + "(" + strMainNames[0] + ".value)+'";
			}
			if (strNextControls != null && !strNextControls.equals(""))
			{
				strParam += "&strNextControls=" + strNextControls;
			}
			for (int i = 0; i < strMainNames.length; i++)
			{
				strParam += "&strMainNames=" + strMainNames[i];
				strParam += "&strMainFields=" + strMainFields[i];
			}
			if (strReturnNames != null)
			{
				boolean bValue = false;
				if (strReturnValues != null && strReturnValues.length == strReturnNames.length)
				{
					bValue = true;
				}
				for (int i = 0; i < strReturnNames.length; i++)
				{
					//�����������
					strParam += "&strReturnNames=" + strReturnNames[i];
					strParam += "&strReturnFields=" + strReturnFields[i];
					if (bValue)
					{
						out.println("<input type=\"hidden\" name=\"" + strReturnNames[i] + "\" value=\"" + strReturnValues[i] + "\">");
					}
					else
					{
						out.println("<input type=\"hidden\" name=\"" + strReturnNames[i] + "\">");
					}
				}
			}
			for (int i = 0; i < strDisplayNames.length; i++)
			{
				//�����������
				strParam += "&strDisplayNames=" + strDisplayNames[i];
				strParam += "&strDisplayFields=" + strDisplayFields[i];
			}
			//Log.print("strParam = " + strParam);
			//���ɲ�ѯ��ť���¼��ַ���
			String sOnKeydown =
				"if("
					+ strFormName
					+ "."
					+ strMainNames[0]
					+ ".disabled == false) {gnIsSelectCtrl=1;window.open('"
					+ Env.URL_PREFIX
					+ "/iTreasury-ebank/magnifier/ShowMagnifierZoom.jsp?"
					+ strParam
					+ "', 'SelectAnything', '"
					+ sFeatures
					+ "', false);}";
			//
			String sOnKeyUp = "";
			if (strReturnNames != null)
			{
				for (int i = 0; i < strReturnNames.length; i++)
				{
					sOnKeyUp += strReturnNames[i] + ".value = -1; ";
				}
			}
			int iPos = -1;
			//��ʾ�ؼ�
			if (iPos == -1)
			{
				out.println(
					"<td "
						+ strFirstTD
						+ " >"
						+ strTitle
						+ "��&nbsp;"
						+ "<a href=#><img name=\""
						+ strButtonName
						+ "\" src='/webob/graphics/icon.gif' border=0 onclick=\""
						+ sOnKeydown
						+ "\"></a></td>");
			}
			else
			{
				out.println("<td " + strFirstTD + " >" + strTitle + ":&nbsp;" + "<a href=#><img name=\"" + strButtonName + "\" src='/webob/graphics/icon.gif' border=0 ></a></td>");
			}
			//blnIsOptional,�Ƿ��ѡ�����ժҪ���ֽ�����Ŵ���Ч��
			if (blnIsOptional == true)
			{
				if (blnIsRateCtrl == true)
				{
					out.println("<td " + strSecondTD + " ><input type=\"text\" name=\"" + strMainNames[0] + "\" value=\"" + strReturnInitValues + "\" class=\"tar\" " + strMainProperty + ">%</td>");
				}
				else
				{
					out.println("<td " + strSecondTD + " ><input type=\"text\" name=\"" + strMainNames[0] + "\" value=\"" + strReturnInitValues + "\" class=\"box\" " + strMainProperty + "></td>");
				}
			}
			else
			{
				if (blnIsRateCtrl == true)
				{
					out.println(
						"<td "
							+ strSecondTD
							+ " ><input style='background-color:#ffffff;' type=\"text\" name=\""
							+ strMainNames[0]
							+ "\" value=\""
							+ strReturnInitValues
							+ "\" class=\"tar\" "
							+ strMainProperty
							+ " onKeyDown=\"if(event.keyCode==13) "
							+ sOnKeydown
							+ "\" onKeyUp=\""
							+ sOnKeyUp
							+ "\">%</td>");
				}
				else
				{
					out.println(
						"<td "
							+ strSecondTD
							+ " ><input type=\"text\" name=\""
							+ strMainNames[0]
							+ "\" value=\""
							+ strReturnInitValues
							+ "\" class=\"box\" "
							+ strMainProperty
							+ " onKeyDown=\"if(event.keyCode==13) "
							+ sOnKeydown
							+ "\" onKeyUp=\""
							+ sOnKeyUp
							+ "\"></td>");
				}
			}
			out.println("<script language=\"JavaScript\">");
			out.println("function get" + strMainNames[0] + "(str)");
			out.println("{");
			out.println("   var sql = \"\"; ");
			out.println("   if (str != null && str != \"\") ");
			out.println("   {");
			if (strMatchValue.equals("0"))
			{
				out.println(" sql = \"\" ");
			}
			else
			{
				out.println(" sql +=  \" and " + strMatchValue + " like '" + URLEncoder.encode("%") + "\"+str+\"" + URLEncoder.encode("%") + "'\";    ");
			}
			out.println("   }");
			out.println("    ");
			out.println("   return sql;    ");
			out.println("}");
			out.println("</SCRIPT> ");
		}
		catch (Exception exp)
		{
			throw exp;
		}
	}
	
	/**
	* Added by leiyang 2008/02/29 
	* ��ʾ��ͨ�Ŵ�
	* @param JspWriter out
	* @param String strMagnifierName �Ŵ󾵵�����
	* @param String strFormName ��ҳ�������
	* @param strPrefix strPrefix �ؼ�����ǰ׺
	* @param String[] strMainNames �Ŵ󾵻�����λֵ�б�
	* @param String[] strMainFields �Ŵ󾵻�����λ��Ӧ�ı���ֶ�
	* @param String[] strReturnNames �Ŵ󾵷���ֵ�б�(����ֵ)
	* @param String[] strReturnFields �Ŵ󾵷���ֵ(����ֵ)��Ӧ�ı���ֶ��б�
	* @param String   strReturnInitValues �Ŵ󾵻�����λ��Ӧ�ĳ�ʼֵ
	* @param String[] strReturnValues �Ŵ󾵷���ֵ(����ֵ)��Ӧ�ĳ�ʼֵ
	* @param String[] strDisplayNames �Ŵ�С������ʾ����λ����
	* @param String[] strDisplayFields �Ŵ�С������ʾ��λ��Ӧ�ı���ֶ�
	* @param nIndex ȷ��ѡ����,��0��ʼ,���С��0,��Ĭ��������λ����ѡ��,������ڻ����strDisplayNames.length,��û��ѡ����
	* @param strMainProperty �Ŵ���λ����
	* @param strSQL �Ŵ󾵲�ѯSQL���
	* @param strMatchValue �Ŵ�Ҫģ��ƥ����ֶ�
	* @param strNextControls ������һ������
	* @param strTitle ��λ����
	* @param strFirstTD ��һ��TD������
	* @param strSecondTD �ڶ���TD������ 
	* @param blnIsOptional �Ƿ��ǿ�ѡ�Ŀǰ����ժҪ���ֽ�����Ŵ����ã�
	* @param blnIsRateCtrl �Ƿ������ʿؼ�
	* @throws Exception
	*/
	public static void showNewZoomCtrl(
		JspWriter out,
		String strMagnifierName,
		String strFormName,
		String strPrefix,
		String[] strMainNames,
		String[] strMainFields,
		String[] strReturnNames,
		String[] strReturnFields,
		String strReturnInitValues,
		String[] strReturnValues,
		String[] strDisplayNames,
		String[] strDisplayFields,
		int nIndex,
		String strMainProperty,
		String strSQL,
		String strMatchValue,
		String strNextControls,
		String strTitle,
		String strFirstTD,
		String strSecondTD,
		boolean blnIsOptional,
		boolean blnIsRateCtrl)
		throws Exception
	{
		String strButtonName = "button";
		try
		{
			//���Ŵ󾵲���
			checkValue(strMainNames, strMainFields, true);
			checkValue(strReturnNames, strReturnFields, strReturnValues, false);
			checkValue(strDisplayNames, strDisplayFields, true);
			if (strMagnifierName == null || strFormName == null || strFormName.equals("") || strSQL == null || strSQL.equals(""))
			{
				throw new IException(ZOOMERRORMSG);
			}
			if (strNextControls == null)
			{
				throw new IException(ZOOMERRORMSG);
			}
			if (strMatchValue == null || strMatchValue.equals(""))
			{
				strMatchValue = strMainFields[0];
			}
			if (strFirstTD == null)
			{
				strFirstTD = "";
			}
			if (strSecondTD == null)
			{
				strSecondTD = "";
			}
			if (strReturnInitValues == null)
			{
				strReturnInitValues = "";
			}
			//������
			//����ǰ׺
			if (strPrefix != null && !strPrefix.trim().equals(""))
			{
				for (int i = 0; i < strMainNames.length; i++)
				{
					strMainNames[i] = strPrefix + strMainNames[i];
				}
				for (int i = 0; i < strReturnNames.length; i++)
				{
					strReturnNames[i] = strPrefix + strReturnNames[i];
				}
			}
			//�������ڵ�����
			String sFeatures = null;
			if (strDisplayNames.length < 3)
			{
				sFeatures = "toolbar=no,menubar=no,resizable=no,location=no,width=520,height=450,scrollbars=yes";
			}
			else
			{
				sFeatures = "toolbar=no,menubar=no,resizable=no,location=no,width=624,height=540,scrollbars=yes";
			}
			//���ɴ��ݸ��������ڵĲ����ַ���
			String strParam = "";
			strParam = "strFormName=" + strFormName;
			strParam += "&strMagnifierName=" + strMagnifierName;
			strParam += "&nIndex=" + nIndex;
			if (!isSQL(strSQL))
			{
				strParam += "&strSQL= select * from ( '+" + strSQL + "+' ) where 1=1 '+get" + strMainNames[0] + "(" + strMainNames[0] + ".value)+'";
			}
			else
			{
				strParam += "&strSQL= select * from ( " + strSQL + " ) where 1=1 '+get" + strMainNames[0] + "(" + strMainNames[0] + ".value)+'";
			}
			if (strNextControls != null && !strNextControls.equals(""))
			{
				strParam += "&strNextControls=" + strNextControls;
			}
			for (int i = 0; i < strMainNames.length; i++)
			{
				strParam += "&strMainNames=" + strMainNames[i];
				strParam += "&strMainFields=" + strMainFields[i];
			}
			if (strReturnNames != null)
			{
				boolean bValue = false;
				if (strReturnValues != null && strReturnValues.length == strReturnNames.length)
				{
					bValue = true;
				}
				for (int i = 0; i < strReturnNames.length; i++)
				{
					//�����������
					strParam += "&strReturnNames=" + strReturnNames[i];
					strParam += "&strReturnFields=" + strReturnFields[i];
					if (bValue)
					{
						out.println("<input type=\"hidden\" name=\"" + strReturnNames[i] + "\" value=\"" + strReturnValues[i] + "\">");
					}
					else
					{
						out.println("<input type=\"hidden\" name=\"" + strReturnNames[i] + "\">");
					}
				}
			}
			for (int i = 0; i < strDisplayNames.length; i++)
			{
				//�����������
				strParam += "&strDisplayNames=" + strDisplayNames[i];
				strParam += "&strDisplayFields=" + strDisplayFields[i];
			}
			//Log.print("strParam = " + strParam);
			//���ɲ�ѯ��ť���¼��ַ���
			String sOnKeydown =
				"if("
					+ strFormName
					+ "."
					+ strMainNames[0]
					+ ".disabled == false) {gnIsSelectCtrl=1;window.open('"
					+ Env.URL_PREFIX
					+ "/iTreasury-ebank/magnifier/ShowMagnifierZoom.jsp?"
					+ strParam
					+ "', 'SelectAnything', '"
					+ sFeatures
					+ "', false);}";
			//
			String sOnKeyUp = "";
			if (strReturnNames != null)
			{
				for (int i = 0; i < strReturnNames.length; i++)
				{
					sOnKeyUp += strReturnNames[i] + ".value = -1; ";
				}
			}

			
			//blnIsOptional,�Ƿ��ѡ�����ժҪ���ֽ�����Ŵ���Ч��
			if (blnIsOptional == true)
			{
				if (blnIsRateCtrl == true)
				{
					out.println("<td " + strSecondTD + " ><input type=\"text\" name=\"" + strMainNames[0] + "\" value=\"" + strReturnInitValues + "\" class=\"tar\" " + strMainProperty + ">%</td>");
				}
				else
				{
					out.println("<td " + strSecondTD + " ><input type=\"text\" name=\"" + strMainNames[0] + "\" value=\"" + strReturnInitValues + "\" class=\"box\" " + strMainProperty + "></td>");
				}
			}
			else
			{
				if (blnIsRateCtrl == true)
				{
					out.println(
						"<td "
							+ strSecondTD
							+ " ><input type=\"text\" name=\""
							+ strMainNames[0]
							+ "\" value=\""
							+ strReturnInitValues
							+ "\" class=\"tar\" "
							+ strMainProperty
							+ " onKeyDown=\"if(event.keyCode==13) "
							+ sOnKeydown
							+ "\" onKeyUp=\""
							+ sOnKeyUp
							+ "\">%</td>");
				}
				else
				{
					out.println(
						"<td "
							+ strSecondTD
							+ " ><input type=\"text\" name=\""
							+ strMainNames[0]
							+ "\" value=\""
							+ strReturnInitValues
							+ "\" class=\"box\" "
							+ strMainProperty
							+ " onKeyDown=\"if(event.keyCode==13) "
							+ sOnKeydown
							+ "\" onKeyUp=\""
							+ sOnKeyUp
							+ "\"></td>");
				}
			}
			
			int iPos = -1;
			//��ʾ�ؼ�
			if (iPos == -1)
			{
				out.println(
					"<td "
						+ strFirstTD
						+ " >"
						+ strTitle
						//+ "��&nbsp;"
						+ "<a href=#><img name=\""
						+ strButtonName
						+ "\" src='/webob/graphics/icon.gif' border=0 onclick=\""
						+ sOnKeydown
						+ "\"></a></td>");
			}
			else
			{
				out.println("<td " + strFirstTD + " >" + strTitle + "<a href=#><img name=\"" + strButtonName + "\" src='/webob/graphics/icon.gif' border=0 ></a></td>");
			}
			
			out.println("<script language=\"JavaScript\">");
			out.println("function get" + strMainNames[0] + "(str)");
			out.println("{");
			out.println("   var sql = \"\"; ");
			out.println("   if (str != null && str != \"\") ");
			out.println("   {");
			if (strMatchValue.equals("0"))
			{
				out.println(" sql = \"\" ");
			}
			else
			{
				out.println(" sql +=  \" and " + strMatchValue + " like '" + URLEncoder.encode("%") + "\"+str+\"" + URLEncoder.encode("%") + "'\";    ");
			}
			out.println("   }");
			out.println("    ");
			out.println("   return sql;    ");
			out.println("}");
			out.println("</SCRIPT> ");
		}
		catch (Exception exp)
		{
			throw exp;
		}
	}
	
	
	/**
	 * ��������еĿؼ����� �� ��Ӧ�����ݿ��ֶ� ����Ŀ �Ƿ�ƥ��
	 * @param strNames
	 * @param strValues
	 * @param bIsAllowNull
	 * @throws SecException
	 */
	private static void checkValue(String[] strNames, String[] strValues, boolean bIsAllowNull) throws IException
	{
		if (!bIsAllowNull)
		{
			if (strNames != null && strValues != null)
			{
				if (strNames.length != strValues.length)
				{
					throw new IException(ZOOMERRORMSG);
				}
			}
			return;
		}
		if (strNames == null || strValues == null)
		{
			throw new IException(ZOOMERRORMSG);
		}
		if (strNames.length == 0 || strValues.length == 0)
		{
			throw new IException(ZOOMERRORMSG);
		}
		if (strNames.length != strValues.length)
		{
			throw new IException(ZOOMERRORMSG);
		}
	}
	/**
	 * ��������еĿؼ����� �� ��Ӧ�����ݿ��ֶ� ����Ŀ �Ƿ�ƥ��
	 * @param strNames �ؼ�����
	 * @param strFields ��Ӧ���ݿ��ֶ�
	 * @param strValues ��Ӧ��ʼֵ
	 * @param bIsAllowNull �Ƿ�����Ϊ��
	 * @throws SecException
	 */
	private static void checkValue(String[] strNames, String[] strFields, String[] strValues, boolean bIsAllowNull) throws IException
	{
		if (!bIsAllowNull)
		{
			if (strNames != null && strFields != null && strValues != null)
			{
				if (strNames.length != strFields.length || strNames.length != strValues.length || strFields.length != strValues.length)
				{
					throw new IException(ZOOMERRORMSG);
				}
			}
		}
		else
		{
			if (strNames == null || strFields == null || strValues == null)
			{
				throw new IException(ZOOMERRORMSG);
			}
			if (strNames.length == 0 || strFields.length == 0 || strValues.length == 0)
			{
				throw new IException(ZOOMERRORMSG);
			}
			if (strNames.length != strFields.length || strNames.length != strValues.length || strFields.length != strValues.length)
			{
				throw new IException(ZOOMERRORMSG);
			}
		}
	}
	/**
	 * ��SQL�����д���,����������Ĳ�ѯSQL���,�����е�"'"�ַ�ǰ��"\".
	 * @param strSQL
	 * @return
	 */
	private static String getSQL(String strSQL)
	{
		StringBuffer sb = new StringBuffer();
		int nIndex = strSQL.indexOf("'");
		while (nIndex != -1)
		{
			String sTemp = strSQL.substring(0, nIndex);
			sb.append(sTemp).append("\\'");
			strSQL = strSQL.substring(nIndex + 1, strSQL.length());
			nIndex = strSQL.indexOf("'");
		}
		return sb.toString();
	}
	/**
	 * ����Ƿ�Ϊ��ѯSQL���
	 * @param strSQL
	 * @return ture-��,false-��
	 */
	private static boolean isSQL(String strSQL)
	{
		String strTemp = strSQL.toLowerCase();
		int nIndex = strTemp.indexOf("select ");
		if (nIndex == -1)
		{
			return false;
		}
		nIndex = strTemp.indexOf(" from ");
		if (nIndex == -1)
		{
			return false;
		}
		return true;
	}
	/**
	 * �ӳ�����ȡֵ
	 * @param nType, �漰�ӳ�����ȡֵ�ķŴ󾵵ı��
	 * Ŀǰ�ķŴ󾵱�ţ�
	 * 1 ����ͬ���ͷŴ�--��ʾ��
	 * 2 ����������--ģ�����ƷŴ�
	 * 3 ����������--ҵ�����ͷŴ�
	 * 4 ����������--�����Ŵ�
	 * 5 ����������--״̬
	 * 6 ������Ʊ�ݷŴ󾵣����������ӳ����еã�
	 * 7 �����ڻ�֪ͨ�浥�ţ��Ӷ��ڿ�������ȡ�ã�
	 * 8 ��
	 * @param lID, ��Ӧ�ĳ���ֵ
	 * @return
	 * @throws Exception
	 */
	private static String getValueFromConstant(int nType, long lID) throws Exception
	{
		String strReturn = "";
		try
		{
			switch (nType)
			{
				case 1 : //��ͬ���ͷŴ�--��ʾ��
					Log.print("��ͬ���ͷŴ�");
					//strReturn = Notes.getCodeName(Notes.CODETYPE_CONTRACT_TYPE,lID);
					break;
				case 2 : //��������--ģ�����ƷŴ�
					Log.print("ģ�����ƷŴ�");
					strReturn = Constant.ModuleType.getName(lID);
					break;
				case 3 : //��������--ҵ�����ͷŴ�
					Log.print("ҵ�����ͷŴ󾵷Ŵ�");
					strReturn = Constant.ApprovalLoanType.getName(lID);
					break;
				case 4 : //��������--�����Ŵ�
					Log.print("�����Ŵ󾵷Ŵ�");
					strReturn = Constant.ApprovalAction.getName(lID);
					break;
				case 5 : //��������--״̬
					Log.print("״̬�Ŵ�");
					strReturn = Constant.ApprovalStatus.getName(lID);
					break;
				case 6 : //����Ʊ�ݷŴ󾵣����������ӳ����еã�
					Log.print("����Ʊ�ݷŴ�");
					strReturn = SETTConstant.BankBillType.getName(lID);
					break;
				case 7 : //���ڻ�֪ͨ�浥�ţ��Ӷ��ڿ�������ȡ�ã�
					Log.print("���ڻ�֪ͨ�浥��");
					//UtilOperation utiloperation = new UtilOperation();
					//strReturn = utiloperation.getOpenDepositNo(lID);
					break;
				default :
					strReturn = "��ֵû�дӳ�����ȡ�ã����޸ģ�";
					break;
			}
		}
		catch (Exception ex)
		{
			throw new Exception("�������ݿ����");
		}
		return strReturn;
	}
	/**
	 *
	 * @param strMainFields
	 * @param strReturnFields
	 * @param strDisplayFields
	 * @param strSQL
	 * @return
	 * @throws Exception
	 */
	public static Vector getCommonSelectList(String[] strMainFields, String[] strReturnFields, String[] strDisplayFields, String strSQL) throws Exception
	{
		CommonSelectInfo info = null;
		PreparedStatement ps = null, ps1 = null;
		ResultSet rs = null, rs1 = null;
		Connection conn = null;
		Vector vResult = new Vector();
		try
		{
			conn = Database.getConnection();
			ps = conn.prepareStatement(strSQL);
			rs = ps.executeQuery();
			/*for( int i =0 ;i< rs.getMetaData().getColumnCount();i++){
			System.out.println( "((((((((((  " + rs.getMetaData().getColumnName(i));
			}*/
			while (rs.next())
			{
				info = new CommonSelectInfo();
				//��ȡ�Ŵ󾵲�ѯ�����ֶ�
				Object[] oMainCols = new Object[strMainFields.length];
				for (int i = 0; i < strMainFields.length; i++)
				{
					//�ж��Ƿ���Ҫ�ӳ�����Constant��ȡ�����ݡ�

					String strTempMain = rs.getString(strMainFields[i]);
					if (strTempMain != null && strTempMain.indexOf("FromConstant") >= 0)
					{
						String strSubString1 = strTempMain.substring(strTempMain.indexOf("_") + 1);
						String strTempField = strSubString1.substring(strSubString1.indexOf("_") + 1);
						int nType = (int) Long.parseLong(strSubString1.substring(0, strSubString1.indexOf("_")));
						oMainCols[i] = getValueFromConstant(nType, rs.getLong(strTempField));
					}
					else
					{
						oMainCols[i] = rs.getObject(strMainFields[i]);
						if (oMainCols[i] == null)
						{
							oMainCols[i] = "";
						}
					}
					oMainCols[0] = NameRef.getNoLineAccountNo(oMainCols[0].toString());
				}
				//��Ҫ������ҳ����ֶ�
				Object[] oReturnCols = null;
				if (strReturnFields != null)
				{
					oReturnCols = new Object[strReturnFields.length];
					for (int i = 0; i < strReturnFields.length; i++)
					{
						oReturnCols[i] = rs.getObject(strReturnFields[i]);
						//<PK>
					}
				}
				//��Ҫ�ڷŴ�����ʾ���ֶ�
				Object[] oDisplayCols = new Object[strDisplayFields.length];
				for (int i = 0; i < strDisplayFields.length; i++)
				{
					//�ж��Ƿ���Ҫ�ӳ�����Constant��ȡ�����ݡ�
					String strTempDisplay = rs.getString(strDisplayFields[i]);
					if (strTempDisplay != null && strTempDisplay.indexOf("FromConstant") >= 0)
					{
						String strSubString1 = strTempDisplay.substring(strTempDisplay.indexOf("_") + 1);
						String strTempField = strSubString1.substring(strSubString1.indexOf("_") + 1);
						int nType = (int) Long.parseLong(strSubString1.substring(0, strSubString1.indexOf("_")));
						oDisplayCols[i] = getValueFromConstant(nType, rs.getLong(strTempField));
					}
					else
					{
						oDisplayCols[i] = rs.getObject(strDisplayFields[i]);
						//<PK>
						if (oDisplayCols[i] == null)
						{
							oDisplayCols[i] = "";
						}
					}
				}
				info.setMainCols(oMainCols);
				if (strReturnFields != null)
				{
					info.setReturnCols(oReturnCols);
				}
				info.setDisplayCols(oDisplayCols);
				vResult.add(info);
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw new Exception("�������ݿ����");
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (conn != null)
				{
					conn.close();
					conn = null;
				}
			}
			catch (Exception _ex)
			{
				System.out.println("�ر����ݿ�����ʱ�������ݿ����");
			}
		}
		return vResult.size() > 0 ? vResult : null;
	}
	/**
	 * ��������и���˺ŷŴ󾵵����ⷽ�����Ŵ󾵻�����λֵ�޷���ͬһsql����в��
	 * @author yanliu
	 * @param strMainFields
	 * @param strReturnFields
	 * @param strDisplayFields
	 * @param strSQL
	 * @return
	 * @throws Exception
	 */
	public static Vector getOBAccountCommonSelectList(String[] strMainFields, String[] strReturnFields, String[] strDisplayFields, String strSQL) throws Exception
	{
		CommonSelectInfo info = null;
		PreparedStatement ps = null, ps1 = null;
		ResultSet rs = null, rs1 = null;
		Connection conn = null;
		Vector vResult = new Vector();
		OBFinanceInstrDao obFinanceInstrDao = new OBFinanceInstrDao();
		AccountBalanceInfo accountinfo = new AccountBalanceInfo();
		try
		{
			conn = Database.getConnection();
			ps = conn.prepareStatement(strSQL);
			rs = ps.executeQuery();
			while (rs.next())
			{
				info = new CommonSelectInfo();
				//��ȡ�Ŵ󾵲�ѯ�����ֶ�
				Object[] oMainCols = new Object[strMainFields.length];
				long lTempMain = rs.getLong(strMainFields[1]);
				long lCurrencyID = rs.getLong("CurrencyID");
				long lInstructionID = rs.getLong("InstructionID");
				accountinfo = obFinanceInstrDao.getCurrBalanceByAccountID(lTempMain, lCurrencyID, lInstructionID);
				oMainCols[0] = NameRef.getNoLineAccountNo(rs.getObject(strMainFields[0]).toString());
				oMainCols[1] = rs.getObject(strMainFields[1]);
				oMainCols[2] = DataFormat.formatDisabledAmount(accountinfo.getCurrentBalance(),2);
				oMainCols[3] = DataFormat.formatDisabledAmount(accountinfo.getUsableBalance(),2);
				if(oMainCols.length>4)
				{
					for(int j=oMainCols.length-1;j>=4;j--)
					{
						oMainCols[j] = rs.getObject(strMainFields[j]);
					}
				}
				/* added by mzh_fu 2008/03/13 �����˻���ϵ�Ŀ��� begin*/
				AccountSystemDelegation accountSystemDelegation = new AccountSystemDelegation();
				AccountSystemInfo accountSystemInfo = new AccountSystemInfo();
				accountSystemInfo.setNAccountId(lTempMain);
               //modify by xwhe
				double dCPF2Amount = obFinanceInstrDao.getUsableBalanceByAccountID(lTempMain, lCurrencyID, lInstructionID);
				if(accountSystemDelegation.isInAccountSystem(accountSystemInfo))
				{
					String accoutCanUseBalance = DataFormat.formatDisabledAmount(accountSystemDelegation.getAccoutCanUseBalance(accountSystemInfo)-dCPF2Amount);
					oMainCols[3] ="".equals(accoutCanUseBalance)?"0.00":accoutCanUseBalance;	
				}
				//	oMainCols[3] = DataFormat.formatDisabledAmount(accountSystemDelegation.getAccoutCanUseBalance(accountSystemInfo));
				/* added by mzh_fu 2008/03/13 �����˻���ϵ�Ŀ��� end*/
				
				Log.print("-------1:" + oMainCols[0]);
				Log.print("-------2:" + oMainCols[1]);
				Log.print("-------3:" + oMainCols[2]);
				Log.print("-------4:" + oMainCols[3]);
				//��Ҫ������ҳ����ֶ�
				Object[] oReturnCols = null;
				if (strReturnFields != null)
				{
					oReturnCols = new Object[strReturnFields.length];
					for (int i = 0; i < strReturnFields.length; i++)
					{
						oReturnCols[i] = rs.getObject(strReturnFields[i]);
						
						//<PK>
					}
				}
				//��Ҫ�ڷŴ�����ʾ���ֶ�
				Object[] oDisplayCols = new Object[strDisplayFields.length];
				for (int i = 0; i < strDisplayFields.length; i++)
				{
					//�ж��Ƿ���Ҫ�ӳ�����Constant��ȡ�����ݡ�
					String strTempDisplay = rs.getString(strDisplayFields[i]);
					if (strTempDisplay != null && strTempDisplay.indexOf("FromConstant") >= 0)
					{
						String strSubString1 = strTempDisplay.substring(strTempDisplay.indexOf("_") + 1);
						String strTempField = strSubString1.substring(strSubString1.indexOf("_") + 1);
						int nType = (int) Long.parseLong(strSubString1.substring(0, strSubString1.indexOf("_")));
						oDisplayCols[i] = getValueFromConstant(nType, rs.getLong(strTempField));
					}
					else
					{
						oDisplayCols[i] = rs.getObject(strDisplayFields[i]);
						//<PK>
						if (oDisplayCols[i] == null)
						{
							oDisplayCols[i] = "";
						}
					}
				}
				info.setMainCols(oMainCols);
				if (strReturnFields != null)
				{
					info.setReturnCols(oReturnCols);
				}
				info.setDisplayCols(oDisplayCols);
				vResult.add(info);
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw new Exception("�������ݿ����");
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (conn != null)
				{
					conn.close();
					conn = null;
				}
			}
			catch (Exception _ex)
			{
				System.out.println("�ر����ݿ�����ʱ�������ݿ����");
			}
		}
		return vResult.size() > 0 ? vResult : null;
	}
	/**
	 * ��������и���˺ŷŴ󾵵����ⷽ�����Ŵ󾵻�����λֵ�޷���ͬһsql����в��
	 * @author yanliu
	 * @param strMainFields
	 * @param strReturnFields
	 * @param strDisplayFields
	 * @param strSQL
	 * @return
	 * @throws Exception
	 */
	public static Vector getOBAccountCommonSelectListXH(String[] strMainFields, String[] strReturnFields, String[] strDisplayFields, String strSQL) throws Exception
	{
		CommonSelectInfo info = null;
		PreparedStatement ps = null, ps1 = null;
		ResultSet rs = null, rs1 = null;
		Connection conn = null;
		Vector vResult = new Vector();
		OBFinanceInstrDao obFinanceInstrDao = new OBFinanceInstrDao();
		AccountBalanceInfo accountinfo = new AccountBalanceInfo();
		try
		{
			conn = Database.getConnection();
			ps = conn.prepareStatement(strSQL);
			rs = ps.executeQuery();
			while (rs.next())
			{
				info = new CommonSelectInfo();
				//��ȡ�Ŵ󾵲�ѯ�����ֶ�
				Object[] oMainCols = new Object[strMainFields.length];
				long lTempMain = rs.getLong(strMainFields[1]);
				long lCurrencyID = rs.getLong("CurrencyID");
				long lInstructionID = rs.getLong("InstructionID");
				accountinfo = obFinanceInstrDao.getCurrBalanceByAccountID(lTempMain, lCurrencyID, lInstructionID);
				oMainCols[0] = rs.getObject(strMainFields[0]);
				oMainCols[1] = rs.getObject(strMainFields[1]);
				oMainCols[2] = DataFormat.formatDisabledAmount(accountinfo.getCurrentBalance());
				oMainCols[3] = DataFormat.formatDisabledAmount(accountinfo.getUsableBalance());
				oMainCols[4] = rs.getObject(strMainFields[4]);
				oMainCols[5] = rs.getObject(strMainFields[5]);
				oMainCols[6] = rs.getObject(strMainFields[6]);
				oMainCols[7] = rs.getObject(strMainFields[7]);
				oMainCols[8] = rs.getObject(strMainFields[8]);
				if(oMainCols[4]==" " || oMainCols[4]==null || oMainCols[4]=="null")
				{ 
					oMainCols[4]="";
				}
				if(oMainCols[5]==" " || oMainCols[5]==null || oMainCols[5]=="null")
				{ 
					oMainCols[5]="";
				}
				if(oMainCols[6]==" " || oMainCols[6]==null || oMainCols[6]=="null")
				{ 
					oMainCols[6]="";
				}
				if(oMainCols[7]==" " || oMainCols[7]==null || oMainCols[7]=="null")
				{ 
					oMainCols[7]="";
				}
				if(oMainCols[8]==" " || oMainCols[8]==null || oMainCols[8]=="null")
				{ 
					oMainCols[8]="";
				}
				Log.print("-------1:" + oMainCols[0]);
				Log.print("-------2:" + oMainCols[1]);
				Log.print("-------3:" + oMainCols[2]);
				Log.print("-------4:" + oMainCols[3]);
				//��Ҫ������ҳ����ֶ�
				Object[] oReturnCols = null;
				if (strReturnFields != null)
				{
					oReturnCols = new Object[strReturnFields.length];
					for (int i = 0; i < strReturnFields.length; i++)
					{
						oReturnCols[i] = rs.getObject(strReturnFields[i]);
						//<PK>
					}
				}
				//��Ҫ�ڷŴ�����ʾ���ֶ�
				Object[] oDisplayCols = new Object[strDisplayFields.length];
				for (int i = 0; i < strDisplayFields.length; i++)
				{
					//�ж��Ƿ���Ҫ�ӳ�����Constant��ȡ�����ݡ�
					String strTempDisplay = rs.getString(strDisplayFields[i]);
					if (strTempDisplay != null && strTempDisplay.indexOf("FromConstant") >= 0)
					{
						String strSubString1 = strTempDisplay.substring(strTempDisplay.indexOf("_") + 1);
						String strTempField = strSubString1.substring(strSubString1.indexOf("_") + 1);
						int nType = (int) Long.parseLong(strSubString1.substring(0, strSubString1.indexOf("_")));
						oDisplayCols[i] = getValueFromConstant(nType, rs.getLong(strTempField));
					}
					else
					{
						oDisplayCols[i] = rs.getObject(strDisplayFields[i]);
						//<PK>
						if (oDisplayCols[i] == null)
						{
							oDisplayCols[i] = "";
						}
					}
				}
				info.setMainCols(oMainCols);
				if (strReturnFields != null)
				{
					info.setReturnCols(oReturnCols);
				}
				info.setDisplayCols(oDisplayCols);
				vResult.add(info);
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw new Exception("�������ݿ����");
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (conn != null)
				{
					conn.close();
					conn = null;
				}
			}
			catch (Exception _ex)
			{
				System.out.println("�ر����ݿ�����ʱ�������ݿ����");
			}
		}
		return vResult.size() > 0 ? vResult : null;
	}
	
	/**
	 * �����˻��Ŵ�
	 * @param out
	 * @param lOfficeID ���´�ID
	 * @param lCurrencyID ����ID
	 * @param strFormName ��������
	 * @param strCtrlName �Ŵ����ؼ�����
	 * @param strTitle �Ŵ�����
	 * @param lClientID �ͻ�ID(��ʶֵ)
	 * @param lAccountID �˻�ID(��ʶֵ)
	 * @param strAccountNo �˺�(��ʶֵ)
	 * @param lAccountGroupTypeID �˻���ID
	 * @param lAccountTypeID �˻�����ID
	 * ����������£���ֱ�����˻����ͳ�����
	 * 	����ģ���Ϣ����֧�����봫��-100��ֻ��ʾ�������к�ί�����֣�����
	 * @param lReceiveOrPay �ո����ͣ�-1000����ģ���ʵ����״̬���˻���
	 * @param strClientCtrl �ͻ�ID��Ӧ�Ŀؼ����ƣ���ѯʱ������
	 * @param strFirstTD ��һ��TD������
	 * @param strSecondTD �ڶ���TD������
	 * @param strNextControls ��һ������������ý���Ŀؼ�
	 * @param strRtnClientIDCtrl ����ֵ���ͻ�ID����Ӧ�Ŀؼ���
	 * @param strRtnClientNoCtrl ����ֵ���ͻ���ţ���Ӧ�Ŀؼ���
	 * @param strRtnClientNameCtrl ����ֵ���ͻ����ƣ���Ӧ�Ŀؼ���
	 */
	public static void createAccountCtrl(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lClientID,
		long lAccountID,
		String strAccountNo,
		long lAccountGroupTypeID,
		long lAccountTypeID,
		long lReceiveOrPay,
		String strClientCtrl,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,
		String strRtnClientIDCtrl,
		String strRtnClientNoCtrl,
		String strRtnClientNameCtrl)
	{
		String strMagnifierName = URLEncoder.encode("�˻�");
		String strPrefix = "";
		String strMainProperty = "";
		int nCaseNumber = 4;
		if (lAccountGroupTypeID == SETTConstant.AccountGroupType.CURRENT
		        ||lAccountGroupTypeID == SETTConstant.AccountGroupType.OTHERDEPOSIT)
		{
			//�����˻��飬��ʾ4���ı���
			nCaseNumber = 4;
		}
		else if (lAccountGroupTypeID == SETTConstant.AccountGroupType.FIXED
		        ||lAccountGroupTypeID == SETTConstant.AccountGroupType.NOTIFY)
		{
			//�����˻��飬��ʾ3���ı���
			nCaseNumber = 3;
		}
		else if (lAccountGroupTypeID == SETTConstant.AccountGroupType.TRUST
		        ||lAccountGroupTypeID == SETTConstant.AccountGroupType.CONSIGN
		        ||lAccountGroupTypeID == SETTConstant.AccountGroupType.DISCOUNT
		        ||lAccountGroupTypeID == SETTConstant.AccountGroupType.OTHERLOAN)
		{
			//�����˻��飬��ʾ3���ı���
			nCaseNumber = 3;
		}
		if (strRtnClientIDCtrl == null || strRtnClientIDCtrl.equals(""))
		{
			strRtnClientIDCtrl = "ItIsNotControl";
		}
		if (strRtnClientNoCtrl == null || strRtnClientNoCtrl.equals(""))
		{
			strRtnClientNoCtrl = "ItIsNotControl";
		}
		if (strRtnClientNameCtrl == null || strRtnClientNameCtrl.equals(""))
		{
			strRtnClientNameCtrl = "ItIsNotControl";
		}
		String[] strMainNames = { strCtrlName + "Ctrl", strRtnClientIDCtrl, strRtnClientNoCtrl, strRtnClientNameCtrl };
		String[] strMainFields = { "AccountNo", "ClientID", "ClientNo", "ClientName" };
		String[] strReturnNames = { strCtrlName, strCtrlName + "ClientID" };
		String[] strReturnFields = { "AccountID", "ClientID" };
		String[] strReturnValues = { String.valueOf(lAccountID), String.valueOf(lClientID)};
		Log.print("SETTConstant.ReceiveOrPay.PAY ==========" + SETTConstant.ReceiveOrPay.PAY);
		String[] strDisplayNames = { URLEncoder.encode("�˺�"), URLEncoder.encode("�˻�����")};
		String[] strDisplayFields = { "AccountNo", "AccountName" };
		int nIndexAccount = 0;
		String strSQL = "";
		if (strClientCtrl != null && !strClientCtrl.trim().equals(""))
		{
			strSQL =
			"getAccountSQL("
				+ lOfficeID
				+ ","
				+ lCurrencyID
				+ ","
				+ lAccountGroupTypeID
				+ ","
				+ lAccountTypeID
				+ ","
				+ lReceiveOrPay
				+ ","
				+ strFormName
				+ "."
				+ strClientCtrl
				+ ".value"
				+ ","
				+ strFormName
				+ "."
				+ strCtrlName
				+ "Ctrl.value)";
		}
		else
		{
			strSQL =
			"getAccountSQL("
				+ lOfficeID
				+ ","
				+ lCurrencyID
				+ ","
				+ lAccountGroupTypeID
				+ ","
				+ lAccountTypeID
				+ ","
				+ lReceiveOrPay
				+ ",-1,"
				+ strFormName
				+ "."
				+ strCtrlName
				+ "Ctrl.value)";
		}
		//��ʼĬ��ֵ
		if (strAccountNo == null || strAccountNo.equals(""))
		{
			/*
			if (lOfficeID > 0)
			{
				strAccountNo = NameRef.getOfficeNoByID(lOfficeID);
			}
			*/
			//�޸� by kenny(��־ǿ)(2007-03-26) �����˺ű����������
			//�˺ŵĶμ����
			String tag = Config.getProperty(ConfigConstant.GLOBAL_ACCOUNTNO_TAG,"-");
			//�˺ŵĵ�һ�ε�����
			int firstFieldType = Config.getInteger(ConfigConstant.GLOBAL_ACCOUNTNO_FIRSTFIELDTYPE,1);
			//���������lCurrencyID��lOfficeID�������0
			if (firstFieldType == 1) {
				if (lCurrencyID > 0) {
					strAccountNo = NameRef.getCurrencyNoByID(lCurrencyID);
				}
				if (lOfficeID > 0) {
					strAccountNo = strAccountNo + tag + NameRef.getOfficeNoByID(lOfficeID);
				}
				if (lAccountTypeID > 0) {
					strAccountNo = strAccountNo + tag + DataFormat.formatInt(lAccountTypeID, 2);
				}
			} else if (firstFieldType == 2) {
				if (lOfficeID > 0) {
					strAccountNo = NameRef.getOfficeNoByID(lOfficeID);
				}
				if (lAccountTypeID > 0) {
					strAccountNo = strAccountNo + tag + DataFormat.formatInt(lAccountTypeID, 2);
				}
			}
		}
		try
		{
			OBMagnifier.showSpecialZoomCtrl(
					out,
					strMagnifierName,
					strFormName,
					strPrefix,
					nCaseNumber,
					strMainNames,
					strMainFields,
					strReturnNames,
					strReturnFields,
					strReturnValues,
					strDisplayNames,
					strDisplayFields,
					nIndexAccount,
					strMainProperty,
					strSQL,
					strNextControls,
					strAccountNo,
					strTitle,
					strFirstTD,
					strSecondTD,
					strClientCtrl);
		}
		catch (Exception e)
		{
			Log.print("�˻��Ŵ�[" + strCtrlName + "]�쳣��" + e.toString());
		}
	}
	
	/**
	 * @author ���ָ�
	 * @param out
	 * @param strFormName
	 * @param strCtrlName
	 * @param lAccountID
	 * @param strAccountNO
	 * @param strRtnAccountNameCtrlName
	 * @param strNextControls
	 * @param sConditions
	 * @param officeID
	 */
	public static void createAccountCtrlReturnCode(JspWriter out,
			String strFormName, String strCtrlName, long lAccountID,
			String strAccountNO, String strRtnAccountNameCtrlName,
			String[] strNextControls, String[] sConditions, long officeID) {
		String strMagnifierName = URLEncoder.encode("�˻�");
		String strMainProperty = " size='20' maxlength='30' value='"+ strAccountNO + "'";
		String strPrefix = "";
		String strTitle="�˺�";
		if (strRtnAccountNameCtrlName == null || strRtnAccountNameCtrlName.equals("")) 
		{
			strRtnAccountNameCtrlName = "ItIsNotControl";
		}
		String[] strMainNames = { strCtrlName + "Ctrl",strRtnAccountNameCtrlName };
		String[] strMainFields = { "AccountNO", "AccountName" };
		String[] strReturnNames = { strCtrlName };
		String[] strReturnFields = { "AccountID" };
		String[] strReturnValues = { String.valueOf(lAccountID) };

		String[] strDisplayNames = { URLEncoder.encode("�˺�"),URLEncoder.encode("�˻�����"),URLEncoder.encode("��������") };
		String[] strDisplayFields = { "AccountNO", "AccountName","BankName" };
		int nIndex = 0;
		String strSQL = null;
		//����ҳ�洫��Ĳ�ѯ���������ɶ�Ӧ��sql,��ǰҪ������������������涨���˳������¼���ڶ�Ӧ�Ĳ�ѯjspҳ��ָ����
		//¼��������˳������Ϊ��sAccountNO,officeID,clientId,countryId,bankId,currencyType,ownerType,inputOrOutput,
		//isDirectLink,accountPropertyOne,accountPropertyTwo,accountPropertyThree,isCheck,accountStatus,dirBankRefcode
		//���Ժ�����Ӳ�ѯ����������ֻ����ҳ��¼���ѯ�������ڶ�Ӧ��JS������׷����ѯ��������
		//ע����ڲ���sAccountNO,officeID�ǳ������ɵģ�����jspҳ��
		StringBuffer bufferSQL=new StringBuffer();
		bufferSQL.append("getAccountSQL(" + strCtrlName + "Ctrl.value,");
		//׷�Ӱ��´���ѯ��Ϣ
//		if(Env.isHQ(officeID))//�ܲ������Բ�ѯ�����´���Ϣ
//		{
//			bufferSQL.append("-1");
//		}
//		else
//		{
			bufferSQL.append(officeID);
//		}
		if(sConditions==null || sConditions.length<=0)
		{
			strSQL=bufferSQL.toString()+")";
		}
		else
		{
			bufferSQL.append(",");
			int length=sConditions.length-1;
			for(int i=0;i<sConditions.length;i++)
			{
				if(sConditions[i]==null)
				{
					bufferSQL.append("-1");
				}
				else
				{
					bufferSQL.append(sConditions[i]+".value");
				}
				if(i<length)
				{					
					bufferSQL.append(",");
				}					
			}
			bufferSQL.append(")");					
			strSQL=bufferSQL.toString();
		}
		System.out.println("***strSQL="+strSQL);
		try {
			OBMagnifier.showZoomCtrl(out, strMagnifierName, strFormName,
					strPrefix, strMainNames, strMainFields, strReturnNames,
					strReturnFields, strReturnValues, strDisplayNames,
					strDisplayFields, nIndex, strMainProperty, strSQL,
					strNextControls,strTitle,null,null,false,false);
		} catch (Exception e) {
			Log.print("�˻��Ŵ�[" + strCtrlName + "]�쳣��" + e.toString());
		}
	}
	//�����鿴Ȩ�޵ķŴ󾵲�ѯ
	public static void createAccountCtrlReturnCodeByAuthority(JspWriter out,
			String strFormName, String strCtrlName, long lAccountID,
			String strAccountNO, String strRtnAccountNameCtrlName,
			String[] strNextControls, String[] sConditions, long officeID) {
		String strMagnifierName = URLEncoder.encode("�˻�");
		String strMainProperty = " size='20' maxlength='30' value='"+ strAccountNO + "'";
		String strPrefix = "";
		String strTitle="�˺�";
		if (strRtnAccountNameCtrlName == null || strRtnAccountNameCtrlName.equals("")) 
		{
			strRtnAccountNameCtrlName = "ItIsNotControl";
		}
		String[] strMainNames = { strCtrlName + "Ctrl",strRtnAccountNameCtrlName };
		String[] strMainFields = { "AccountNO", "AccountName" };
		String[] strReturnNames = { strCtrlName };
		String[] strReturnFields = { "AccountID" };
		String[] strReturnValues = { String.valueOf(lAccountID) };

		String[] strDisplayNames = { URLEncoder.encode("�˺�"),URLEncoder.encode("�˻�����"),URLEncoder.encode("��������") };
		String[] strDisplayFields = { "AccountNO", "AccountName","BankName" };
		int nIndex = 0;
		String strSQL = null;
		//����ҳ�洫��Ĳ�ѯ���������ɶ�Ӧ��sql,��ǰҪ������������������涨���˳������¼���ڶ�Ӧ�Ĳ�ѯjspҳ��ָ����
		//¼��������˳������Ϊ��sAccountNO,officeID,clientId,countryId,bankId,currencyType,ownerType,inputOrOutput,
		//isDirectLink,accountPropertyOne,accountPropertyTwo,accountPropertyThree,isCheck,accountStatus,dirBankRefcode
		//���Ժ�����Ӳ�ѯ����������ֻ����ҳ��¼���ѯ�������ڶ�Ӧ��JS������׷����ѯ��������
		//ע����ڲ���sAccountNO,officeID�ǳ������ɵģ�����jspҳ��
		StringBuffer bufferSQL=new StringBuffer();
		bufferSQL.append("getAccountSQLByAuthority(" + strCtrlName + "Ctrl.value,");
		//׷�Ӱ��´���ѯ��Ϣ
//		if(Env.isHQ(officeID))//�ܲ������Բ�ѯ�����´���Ϣ
//		{
//			bufferSQL.append("-1");
//		}
//		else
//		{
			bufferSQL.append(officeID);
//		}
		if(sConditions==null || sConditions.length<=0)
		{
			strSQL=bufferSQL.toString()+")";
		}
		else
		{
			bufferSQL.append(",");
			int length=sConditions.length-1;
			for(int i=0;i<sConditions.length;i++)
			{
				if(sConditions[i]==null)
				{
					bufferSQL.append("-1");
				}
				else
				{
					bufferSQL.append(sConditions[i]+".value");
				}
				if(i<length)
				{					
					bufferSQL.append(",");
				}					
			}
			bufferSQL.append(")");					
			strSQL=bufferSQL.toString();
		}
		System.out.println("***strSQL="+strSQL);
		try {
			OBMagnifier.showZoomCtrl(out, strMagnifierName, strFormName,
					strPrefix, strMainNames, strMainFields, strReturnNames,
					strReturnFields, strReturnValues, strDisplayNames,
					strDisplayFields, nIndex, strMainProperty, strSQL,
					strNextControls,strTitle,null,null,false,false);
		} catch (Exception e) {
			Log.print("�˻��Ŵ�[" + strCtrlName + "]�쳣��" + e.toString());
		}
	}
	
	
	
	/**
	 * ��ʾ����ķŴ󾵣�������ı������ģ����˻��Ŵ󾵣�
	 * @param out
	 * @param strMagnifierName �Ŵ󾵵�����
	 * @param strFormName ��ҳ�������
	 * @param strPrefix �ؼ�����ǰ׺
	 * @param nCaseNumber ��ʾ�ı������Ŀ��Ŀǰֻ֧��3��4��
	 * @param strMainNames �Ŵ󾵻�����λֵ�б�
	 * @param strMainFields �Ŵ󾵻�����λ��Ӧ�ı���ֶ�
	 * @param strReturnNames �Ŵ󾵷���ֵ�б�(����ֵ)
	 * @param strReturnFields �Ŵ󾵷���ֵ(����ֵ)��Ӧ�ı���ֶ��б�
	 * @param strReturnValues �Ŵ󾵷���ֵ(����ֵ)��Ӧ�ĳ�ʼֵ
	 * @param strDisplayNames �Ŵ�С������ʾ����λ����
	 * @param strDisplayFields �Ŵ�С������ʾ��λ��Ӧ�ı���ֶ�
	 * @param nIndex ȷ��ѡ����,��0��ʼ,���С��0,��Ĭ��������λ����ѡ��,������ڻ����strDisplayNames.length,��û��ѡ����
	 * @param strMainProperty �Ŵ���λ����
	 * @param strSQL �Ŵ󾵲�ѯSQL���
	 * @param strNextControls ������һ������
	 * @param strAccountNo �˺�
	 * @param strTitle �Ŵ󾵵�����
	 * @param strFirstTD ��һ��TD������
	 * @param strSecondTD �ڶ���TD������ 
	 * @param String strClientIDCtrl �����ؼ����ͻ�ID��Ӧ�Ŀؼ����ƣ�
	 * @throws Exception
	 */
	public static void showSpecialZoomCtrl(
		JspWriter out,
		String strMagnifierName,
		String strFormName,
		String strPrefix,
		int nCaseNumber,
		String[] strMainNames,
		String[] strMainFields,
		String[] strReturnNames,
		String[] strReturnFields,
		String[] strReturnValues,
		String[] strDisplayNames,
		String[] strDisplayFields,
		int nIndex,
		String strMainProperty,
		String strSQL,
		String[] strNextControls,
		String strAccountNo,
		String strTitle,
		String strFirstTD,
		String strSecondTD,
		String strClientIDCtrl)
		throws Exception
	{
		String strButtonName = "button";
		try
		{
			//���Ŵ󾵲���
			checkValue(strMainNames, strMainFields, true);
			checkValue(strReturnNames, strReturnFields, strReturnValues, false);
			checkValue(strDisplayNames, strDisplayFields, true);
			if (strMagnifierName == null || strFormName == null || strFormName.equals("") || strSQL == null || strSQL.equals(""))
			{
				throw new IException(ZOOMERRORMSG);
			}
			if (strNextControls == null)
			{
				throw new IException(ZOOMERRORMSG);
			}
			if (strAccountNo == null)
			{
				strAccountNo = "";
			}
			if (strFirstTD == null)
			{
				strFirstTD = "";
			}
			if (strSecondTD == null)
			{
				strSecondTD = "";
			}

			//����ǰ׺
			if (strPrefix != null && !strPrefix.trim().equals(""))
			{
				for (int i = 0; i < strMainNames.length; i++)
				{
					strMainNames[i] = strPrefix + strMainNames[i];
				}
				for (int i = 0; i < strReturnNames.length; i++)
				{
					strReturnNames[i] = strPrefix + strReturnNames[i];
				}
			}
			//�������ڵ�����
			String sFeatures = null;
			if (strDisplayNames.length < 3)
			{
				sFeatures = "toolbar=no,menubar=no,resizable=no,location=no,width=520,height=450,scrollbars=yes";
			}
			else
			{
				sFeatures = "toolbar=no,menubar=no,resizable=no,location=no,width=624,height=540,scrollbars=yes";
			}
			//���ɴ��ݸ��������ڵĲ����ַ���
			String strParam = "";
			strParam = "strFormName=" + strFormName;
			strParam += "&strMagnifierName=" + strMagnifierName;
			strParam += "&nIndex=" + nIndex;
			if (!isSQL(strSQL))
			{
				strParam += "&strSQL='+" + strSQL + "+'";
			}
			else
			{
				strParam += "&strSQL=" + getSQL(strSQL);
			}
			for (int i = 0; i < strNextControls.length; i++)
			{
				strParam += "&strNextControls=" + strNextControls[i];
			}
			for (int i = 0; i < strMainNames.length; i++)
			{
				strParam += "&strMainNames=" + strMainNames[i];
				strParam += "&strMainFields=" + strMainFields[i];
			}
			if (strReturnNames != null)
			{
				boolean bValue = false;
				if (strReturnValues != null && strReturnValues.length == strReturnNames.length)
				{
					bValue = true;
				}
				for (int i = 0; i < strReturnNames.length; i++)
				{
					//�����������
					strParam += "&strReturnNames=" + strReturnNames[i];
					strParam += "&strReturnFields=" + strReturnFields[i];
					if (bValue)
					{
						out.println("<input type=\"hidden\" name=\"" + strReturnNames[i] + "\" value=\"" + strReturnValues[i] + "\">");
					}
					else
					{
						out.println("<input type=\"hidden\" name=\"" + strReturnNames[i] + "\">");
					}
				}
			}
			for (int i = 0; i < strDisplayNames.length; i++)
			{
				//�����������
				strParam += "&strDisplayNames=" + strDisplayNames[i];
				strParam += "&strDisplayFields=" + strDisplayFields[i];
			}
			//Log.print("strParam = " + strParam);
			//������

			/*�޸� by kenny (��־ǿ) ����˺Ŷ���������*/
			/*
			String strTempCtl1 = strMainNames[0] + "Ctrl1";
			String strTempCtl2 = strMainNames[0] + "Ctrl2";
			String strTempCtl3 = strMainNames[0] + "Ctrl3";
			String strTempCtl4 = strMainNames[0] + "Ctrl4";
			*/
			//ȡ���˻�ֵ����������
			int accountField = Config.getInteger(ConfigConstant.GLOBAL_ACCOUNTNO_FIELD,4);
			if (nCaseNumber == 3) {
				accountField = accountField-1;
			} else if (nCaseNumber == 4) {
			}
			String tag = Config.getProperty(ConfigConstant.GLOBAL_ACCOUNTNO_TAG,"");
			String[] accountNo = strAccountNo.split(tag);
			String[] strTempCtl = new String[accountField];
			for (int i=0; i<accountField; i++) {
				strTempCtl[i]=strMainNames[0] + "Ctrl"+(i+1);
			}
			//���ɲ�ѯ��ť���¼��ַ���
			String sOnKeydown =
				"if("
					+ strFormName
					+ "."
					+ strTempCtl[0]
					+ ".disabled == false) {gnIsSelectCtrl=1;window.open('"
					+ Env.URL_PREFIX
					+ "/iTreasury-settlement/magnifier/ShowMagnifierZoom.jsp?"
					+ strParam
					+ "', 'SelectAnything', '"
					+ sFeatures
					+ "', false);}";
			//
			String sOnKeyUp = "";
			if (strReturnNames != null)
			{
				for (int i = 0; i < strReturnNames.length; i++)
				{
					sOnKeyUp += strReturnNames[i] + ".value = -1; ";
				}
				if (strClientIDCtrl != null && !strClientIDCtrl.equals(""))
				{
					sOnKeyUp += "if(" + strClientIDCtrl + "FromClient.value == 0) {" + strClientIDCtrl + ".value = -1;}";
				}
			}
			//int iPos = strMainProperty.toLowerCase().indexOf("disabled");
			int iPos = -1;
			//��ʾ�ؼ�
			if (iPos == -1)
			{
				out.println(
					"<td "
						+ strFirstTD
						+ ">"
						+ strTitle
						+ "��&nbsp;"
						+ "<a href=#><img name=\""
						+ strButtonName
						+ "\" src='/websett/image/icon.gif' border=0 onclick=\""
						+ sOnKeydown
						+ "\"></a></td>");
				//image
			}
			else
			{
				out.println("<td " + strFirstTD + ">" + strTitle + "��&nbsp;" + "<a href=#><img name=\"" + strButtonName + "\" src='/websett/image/icon.gif' border=0 ></a></td>");
				//image
			}
			/*�޸� by kenny (��־ǿ) (2007-03-21) ����˺Ŷ���������*/
			//��װ�˺�ģ����ƥ���ֵ
			String strTempFunction = "";
			String strTempFunctionForFixed = "";
			for (int i=0; i<accountField; i++) {
				if (i>0) {
					strTempFunctionForFixed = strTempFunctionForFixed + ",";
				}
				strTempFunctionForFixed = strTempFunctionForFixed + strFormName + "." + strTempCtl[i] + ".value";
			}
			strTempFunction = 
				strPrefix
				+ strMainNames[0]
				+ "setWholeAcccountNo("
				+ strTempFunctionForFixed
				+ ");"
				+ sOnKeyUp;
			//������ʾ
			int length = Config.getInteger(ConfigConstant.GLOBAL_MAXACCOUNTNO_LENGTH,1);
			int number = Config.getInteger(ConfigConstant.GLOBAL_MAXACCOUNTNO_NUMBER,1);
			out.println("<td " + strSecondTD + ">");
			out.println("<input type=\"hidden\" name=\"" + strMainNames[0] + "\" value=\"" + strAccountNo + "\">");
			for (int i=0; i<accountField; i++) {
				if (i>0) {
					out.println(tag);
				}
				String strSize= "";
				String accountFieldValue = "";
				if (accountNo.length>i) {
					accountFieldValue = accountNo[i];
				}
				if (number == i+1) {
					strSize = "size=\""+length+"\" maxlength=\""+length+"\"";
				} else {
					strSize = "size=\"2\" maxlength=\"2\"";
				}
				
				out.println("<input type=\"text\" "+strSize+" name=\""
				+ strTempCtl[i]
				+ "\" value=\""
				+ accountFieldValue
				+ "\" class=\"box\"  onKeyDown=\"gnIsSelectCtrl=1;"
				+ strPrefix
				+ strMainNames[0]
				+ "fieldFocus"+(i+1)+"("
				+ strFormName
				+ "."
				+ strTempCtl[i]
				+ ".value)\" onKeyUp=\""
				+ strTempFunction
				+ "\">");
			}
			//script��������
			out.println("</td>");
			out.println("<script language=\"JavaScript\">");
			for (int i=0; i<accountField; i++) {
				int iLength = 0;
				if (number == i+1) {
					iLength = length;
				} else {
					iLength = 2;
				}
				out.println("function " + strPrefix + strMainNames[0] + "fieldFocus"+(i+1)+"(sValue)");
				out.println("{");
				out.println("	var k = window.event.keyCode;");
				out.println("	if (k != 46 && k != 8 && k != 37 && k != 39)");
				out.println("	{");
				out.println("		var i;");
				out.println("		if (k == 13 || sValue.length == "+iLength+")");
				out.println("		{");
				out.println("			" + strFormName + "." + strTempCtl[i] + ".value = sValue;");
				out.println("			if (sValue.length > 0)");
				out.println("			{");
				out.println("				for (i=0; i<"+iLength+"-sValue.length; i++)");
				out.println("				{");
				out.println("					" + strFormName + "." + strTempCtl[i] + ".value = " + "\"0\" + " + strFormName + "." + strTempCtl[i] + ".value;");
				out.println("				}");
				out.println("			}");
				if (i == accountField-1) {
					out.println("			" + sOnKeydown);
				} else {
					out.println("			" + strFormName + "." + strTempCtl[i+1] + ".focus();");
					out.println("			" + strFormName + "." + strTempCtl[i+1] + ".select();");
				}
				out.println("		}");
				out.println("	}");
				if (i > 0){
					out.println("	else if (k == 8 && sValue.length == 0)");
					out.println("	{");
					out.println("		" + strFormName + "." + strTempCtl[i-1] + ".value = \"\";");
					out.println("		" + strFormName + "." + strTempCtl[i-1] + ".focus();");
					out.println("	}");
				}
				out.println("}");
			}
			//��д�˻����ε�ֵ
			String param = "";
			String[] where = new String[accountField];
			String[] value = new String[accountField];
			for (int i=0; i<accountField; i++) {
				if (i>0) {
					param = param+",";
				}
				//��װ�����Ĳ���
				param = param+"sValue"+(i+1);
				for (int j=0; j<accountField-i; j++) {
					if (j == 0) {
						where[i] = "sValue"+(j+1)+" != \"\"";
						value[i] = "sValue"+(j+1);
					} else {
						//��װ�ж�����
						where[i] = where[i]+" && sValue"+(j+1)+" != \"\"";
						//��װ��������µ�ֵ
						value[i] = value[i]+" + \"-\" + sValue"+(j+1);
					}
				}
			}
			out.println("function " + strPrefix + strMainNames[0] + "setWholeAcccountNo("+param+")");
			out.println("{");
			String strIf = "";
			for (int i=0; i<accountField; i++) {
				if (i == 0) {
					strIf = "	if (";
				} else {
					strIf = "	} else if (";
				}
				out.println(strIf+where[i]+") {");
				out.println("		" + strFormName + "." + strMainNames[0] + ".value = "+value[i]+";");
			}
			out.println("	} else {");
			out.println("		" + strFormName + "." + strMainNames[0] + ".value = \"\";");
			out.println("	}");
			out.println("}");
			out.println("</script>");

			/*
			//���˺Ų��
			String strAccountNo1 = "";
			String strAccountNo2 = "";
			String strAccountNo3 = "";
			String strAccountNo4 = "";
			if (strAccountNo != null && strAccountNo.length() > 0)
			{
				String strTemp = strAccountNo;
				if (strTemp.indexOf("-") >= 0)
				{
					strAccountNo1 = strTemp.substring(0, strTemp.indexOf("-"));
				}
				else
				{
					strAccountNo1 = strTemp;
					strTemp = "";
				}
				Log.print(strAccountNo1);
				strTemp = strTemp.substring(strTemp.indexOf("-") + 1);
				if (strTemp.indexOf("-") >= 0)
				{
					strAccountNo2 = strTemp.substring(0, strTemp.indexOf("-"));
				}
				else
				{
					strAccountNo2 = strTemp;
					strTemp = "";
				}
				Log.print(strAccountNo2);
				strTemp = strTemp.substring(strTemp.indexOf("-") + 1);
				if (strTemp.indexOf("-") >= 0)
				{
					strAccountNo3 = strTemp.substring(0, strTemp.indexOf("-"));
				}
				else
				{
					strAccountNo3 = strTemp;
					strTemp = "";
				}
				Log.print(strAccountNo3);
				strTemp = strTemp.substring(strTemp.indexOf("-") + 1);
				strAccountNo4 = strTemp;
				Log.print(strAccountNo4);
			}
			String strTempFunction = "";
			String strTempFunctionForFixed = "";
			if (nCaseNumber == 3)
			{
				strTempFunctionForFixed =
					strPrefix
						+ strMainNames[0]
						+ "setWholeAcccountNo("
						+ strFormName
						+ "."
						+ strTempCtl1
						+ ".value,"
						+ strFormName
						+ "."
						+ strTempCtl2
						+ ".value,"
						+ strFormName
						+ "."
						+ strTempCtl3
						+ ".value,'');";
				strTempFunction = strTempFunctionForFixed + sOnKeyUp;
			}
			else
				if (nCaseNumber >= 4)
				{
					strTempFunction =
						strPrefix
							+ strMainNames[0]
							+ "setWholeAcccountNo("
							+ strFormName
							+ "."
							+ strTempCtl1
							+ ".value,"
							+ strFormName
							+ "."
							+ strTempCtl2
							+ ".value,"
							+ strFormName
							+ "."
							+ strTempCtl3
							+ ".value,"
							+ strFormName
							+ "."
							+ strTempCtl4
							+ ".value);"
							+ sOnKeyUp;
				}
			out.println("<td " + strSecondTD + ">");
			out.println("<input type=\"hidden\" name=\"" + strMainNames[0] + "\" value=\"" + strAccountNo + "\">");
			out.println(
				"<input type=\"text\" size=\"2\" maxlength=\"2\" name=\""
					+ strTempCtl1
					+ "\" value=\""
					+ strAccountNo1
					+ "\" class=\"box\"  onKeyDown=\"gnIsSelectCtrl=1;"
					+ strPrefix
					+ strMainNames[0]
					+ "fieldFirstFocus("
					+ strFormName
					+ "."
					+ strTempCtl1
					+ ".value)\" onKeyUp=\""
					+ strTempFunction
					+ "\">");
			out.println("-");
			out.println(
				"<input type=\"text\" size=\"2\" maxlength=\"2\" name=\""
					+ strTempCtl2
					+ "\" value=\""
					+ strAccountNo2
					+ "\" class=\"box\"  onKeyDown=\"gnIsSelectCtrl=1;"
					+ strPrefix
					+ strMainNames[0]
					+ "fieldSecondFocus("
					+ strFormName
					+ "."
					+ strTempCtl2
					+ ".value)\" onKeyUp=\""
					+ strTempFunction
					+ "\">");
			if (nCaseNumber == 3)
			{
				out.println("-");
				out.println(
					"<input type=\"text\" size=\"4\" maxlength=\"4\" name=\""
						+ strTempCtl3
						+ "\" value=\""
						+ strAccountNo3
						+ "\" class=\"box\"  onKeyDown=\"gnIsSelectCtrl=1;"
						+ strPrefix
						+ strMainNames[0]
						+ "fieldThirdFocus("
						+ strFormName
						+ "."
						+ strTempCtl3
						+ ".value)\" onKeyUp=\""
						+ strTempFunction
						+ "\">");
			}
			else
				if (nCaseNumber >= 4)
				{
					out.println("-");
					out.println(
						"<input type=\"text\" size=\"4\" maxlength=\"4\" name=\""
							+ strTempCtl3
							+ "\" value=\""
							+ strAccountNo3
							+ "\" class=\"box\"  onKeyDown=\"gnIsSelectCtrl=1;"
							+ strPrefix
							+ strMainNames[0]
							+ "fieldThirdFocus("
							+ strFormName
							+ "."
							+ strTempCtl3
							+ ".value)\" onKeyUp=\""
							+ strTempFunction
							+ "\">");
					out.println("-");
					out.println(
						"<input type=\"text\" size=\"1\" maxlength=\"1\" name=\""
							+ strTempCtl4
							+ "\" value=\""
							+ strAccountNo4
							+ "\" class=\"box\"  onKeyDown=\"gnIsSelectCtrl=1;"
							+ strPrefix
							+ strMainNames[0]
							+ "fieldFouthFocus("
							+ strFormName
							+ "."
							+ strTempCtl4
							+ ".value)\" onKeyUp=\""
							+ strTempFunction
							+ "\">");
				}
			out.println("</td>");
			//��ӡ�ű�����
			out.println("<script language=\"JavaScript\">");
			out.println("function " + strPrefix + strMainNames[0] + "fieldFirstFocus(sValue)");
			out.println("{");
			out.println("	var k = window.event.keyCode;");
			out.println("	if (k != 46 && k != 8 && k != 37 && k != 39)");
			out.println("	{");
			out.println("		if (k == 13 || sValue.length == 2)");
			out.println("		{");
			out.println("			if (sValue.length ==1)");
			out.println("			{");
			out.println("				" + strFormName + "." + strTempCtl1 + ".value = '0'+sValue;");
			out.println("			}");
			out.println("			" + strFormName + "." + strTempCtl2 + ".focus();");
			out.println("			" + strFormName + "." + strTempCtl2 + ".select();");
			out.println("		}");
			out.println("	}");
			out.println("}");
			out.println("function " + strPrefix + strMainNames[0] + "fieldSecondFocus(sValue)");
			out.println("{");
			out.println("	var k = window.event.keyCode;");
			out.println("	if (k != 46 && k != 8 && k != 37 && k != 39)");
			out.println("	{");
			out.println("		if (k == 13 || sValue.length == 2)");
			out.println("		{");
			out.println("			if (sValue.length ==1)");
			out.println("			{");
			out.println("				" + strFormName + "." + strTempCtl2 + ".value = '0'+sValue;");
			out.println("			}");
			out.println("			" + strFormName + "." + strTempCtl3 + ".focus();");
			out.println("			" + strFormName + "." + strTempCtl3 + ".select();");
			out.println("		}");
			out.println("	}");
			out.println("	else if (k == 8 && sValue.length == 0)");
			out.println("	{");
			out.println("		" + strFormName + "." + strTempCtl1 + ".value = \"\";");
			out.println("		" + strFormName + "." + strTempCtl1 + ".focus();");
			out.println("	}");
			out.println("}");
			if (nCaseNumber == 3)
			{
				out.println("function " + strPrefix + strMainNames[0] + "fieldThirdFocus(sValue)");
				out.println("{");
				out.println("	var k = window.event.keyCode;");
				out.println("	if (k != 46 && k != 8 && k != 37 && k != 39)");
				out.println("	{");
				out.println("		if (k == 13 )");
				out.println("		{");
				out.println("			if (sValue.length ==1)");
				out.println("			{");
				out.println("				" + strFormName + "." + strTempCtl3 + ".value = '000'+sValue;");
				out.println("			}");
				out.println("			else if (sValue.length ==2)");
				out.println("			{");
				out.println("				" + strFormName + "." + strTempCtl3 + ".value = '00'+sValue;");
				out.println("			}");
				out.println("			else if (sValue.length ==3)");
				out.println("			{");
				out.println("				" + strFormName + "." + strTempCtl3 + ".value = '0'+sValue;");
				out.println("			}");
				out.println("			" + strTempFunctionForFixed);
				out.println("			" + sOnKeydown);
				out.println("		}");
				out.println("	}");
				out.println("	else if (k == 8 && sValue.length == 0)");
				out.println("	{");
				out.println("		" + strFormName + "." + strTempCtl2 + ".value = \"\";");
				out.println("		" + strFormName + "." + strTempCtl2 + ".focus();");
				out.println("	}");
				out.println("}");
			}
			else
				if (nCaseNumber >= 4)
				{
					out.println("function " + strPrefix + strMainNames[0] + "fieldThirdFocus(sValue)");
					out.println("{");
					out.println("	var k = window.event.keyCode;");
					out.println("	if (k != 46 && k != 8 && k != 37 && k != 39)");
					out.println("	{");
					out.println("		if (k == 13 || sValue.length == 4)");
					out.println("		{");
					out.println("			if (sValue.length ==1)");
					out.println("			{");
					out.println("				" + strFormName + "." + strTempCtl3 + ".value = '000'+sValue;");
					out.println("			}");
					out.println("			else if (sValue.length ==2)");
					out.println("			{");
					out.println("				" + strFormName + "." + strTempCtl3 + ".value = '00'+sValue;");
					out.println("			}");
					out.println("			else if (sValue.length ==3)");
					out.println("			{");
					out.println("				" + strFormName + "." + strTempCtl3 + ".value = '0'+sValue;");
					out.println("			}");
					out.println("			" + strFormName + "." + strTempCtl4 + ".focus();");
					out.println("			" + strFormName + "." + strTempCtl4 + ".select();");
					out.println("		}");
					out.println("	}");
					out.println("	else if (k == 8 && sValue.length == 0)");
					out.println("	{");
					out.println("		" + strFormName + "." + strTempCtl2 + ".value = \"\";");
					out.println("		" + strFormName + "." + strTempCtl2 + ".focus();");
					out.println("	}");
					out.println("}");
					out.println("function " + strPrefix + strMainNames[0] + "fieldFouthFocus(sValue)");
					out.println("{");
					out.println("	var k = window.event.keyCode;");
					out.println("	if (k != 46 && k != 8 && k != 37 && k != 39)");
					out.println("	{");
					out.println("		if (k == 13 )");
					out.println("		{");
					out.println("			" + sOnKeydown);
					out.println("		}");
					out.println("	}");
					out.println("	else if (k == 8 && sValue.length == 0)");
					out.println("	{");
					out.println("		" + strFormName + "." + strTempCtl3 + ".value = \"\";");
					out.println("		" + strFormName + "." + strTempCtl3 + ".focus();");
					out.println("	}");
					out.println("}");
				}
			out.println("function " + strPrefix + strMainNames[0] + "setWholeAcccountNo(sValue1,sValue2,sValue3,sValue4)");
			out.println("{");
			out.println("	if (sValue1 != \"\" && sValue2 != \"\" && sValue3 != \"\" && sValue4  != \"\")");
			out.println("	{");
			out.println("		" + strFormName + "." + strMainNames[0] + ".value = sValue1 + \"-\" + sValue2+ \"-\" + sValue3 + \"-\" + sValue4;");
			out.println("	}");
			out.println("	else if (sValue1 != \"\" && sValue2 != \"\" && sValue3 != \"\")");
			out.println("	{");
			out.println("		" + strFormName + "." + strMainNames[0] + ".value = sValue1 + \"-\" + sValue2 + \"-\" + sValue3;");
			out.println("	}");
			out.println("	else if (sValue1 != \"\" && sValue2 != \"\")");
			out.println("	{");
			out.println("		" + strFormName + "." + strMainNames[0] + ".value = sValue1 + \"-\" + sValue2;");
			out.println("	}");
			out.println("	else if (sValue1 != \"\")");
			out.println("	{");
			out.println("		" + strFormName + "." + strMainNames[0] + ".value = sValue1;");
			out.println("	}");
			out.println("	else if (sValue1 == \"\")");
			out.println("	{");
			out.println("		" + strFormName + "." + strMainNames[0] + ".value = \"\";");
			out.println("	}");
			out.println("}");
			out.println("</script>");
			*/
		}
		catch (Exception exp)
		{
			throw exp;
		}
	}
	
	/**
	 * ��ʾ����ķŴ󾵣�������ı������ģ����˻��Ŵ󾵣�
	 * @param out
	 * @param strMagnifierName �Ŵ󾵵�����
	 * @param strFormName ��ҳ�������
	 * @param strPrefix �ؼ�����ǰ׺
	 * @param nCaseNumber ��ʾ�ı������Ŀ��Ŀǰֻ֧��3��4��
	 * @param strMainNames �Ŵ󾵻�����λֵ�б�
	 * @param strMainFields �Ŵ󾵻�����λ��Ӧ�ı���ֶ�
	 * @param strReturnNames �Ŵ󾵷���ֵ�б�(����ֵ)
	 * @param strReturnFields �Ŵ󾵷���ֵ(����ֵ)��Ӧ�ı���ֶ��б�
	 * @param strReturnValues �Ŵ󾵷���ֵ(����ֵ)��Ӧ�ĳ�ʼֵ
	 * @param strDisplayNames �Ŵ�С������ʾ����λ����
	 * @param strDisplayFields �Ŵ�С������ʾ��λ��Ӧ�ı���ֶ�
	 * @param nIndex ȷ��ѡ����,��0��ʼ,���С��0,��Ĭ��������λ����ѡ��,������ڻ����strDisplayNames.length,��û��ѡ����
	 * @param strMainProperty �Ŵ���λ����
	 * @param strSQL �Ŵ󾵲�ѯSQL���
	 * @param strNextControls ������һ������
	 * @param strAccountNo �˺�
	 * @param strTitle ��λ����
	 * @param strFirstTD ��һ��TD������
	 * @param strSecondTD �ڶ���TD������ 
	 * @throws Exception
	 */
	public static void showSpecialZoomCtrl(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strMagnifierName,
		String strFormName,
		String strPrefix,
		int nCaseNumber,
		String[] strMainNames,
		String[] strMainFields,
		String[] strReturnNames,
		String[] strReturnFields,
		String[] strReturnValues,
		String[] strDisplayNames,
		String[] strDisplayFields,
		int nIndex,
		String strMainProperty,
		String strSQL,
		String strNextControls,
		String strAccountNo,
		String strTitle,
		String strFirstTD,
		String strSecondTD)
		throws Exception
	{
		String strButtonName = "button";
		try
		{
			//���Ŵ󾵲���
			checkValue(strMainNames, strMainFields, true);
			checkValue(strReturnNames, strReturnFields, strReturnValues, false);
			checkValue(strDisplayNames, strDisplayFields, true);
			if (strMagnifierName == null || strFormName == null || strFormName.equals("") || strSQL == null || strSQL.equals(""))
			{
				throw new IException(ZOOMERRORMSG);
			}
			if (strNextControls == null)
			{
				throw new IException(ZOOMERRORMSG);
			}
			//��ʼĬ��ֵ
			if (strAccountNo == null || strAccountNo.equals(""))
			{
				/*
				if (lOfficeID > 0)
				{
					strAccountNo = NameRef.getOfficeNoByID(lOfficeID);
				}
				*/
				//�˺ŵĵ�һ�ε�����
				int firstFieldType = Config.getInteger(ConfigConstant.GLOBAL_ACCOUNTNO_FIRSTFIELDTYPE,1);
				if (firstFieldType == 1) {
					if (lCurrencyID > 0)
					{
						strAccountNo = NameRef.getCurrencyNoByID(lCurrencyID);
					}
				} else if (firstFieldType == 2) {
				} else if (firstFieldType == 3) {
					if (lOfficeID > 0)
					{
						strAccountNo = NameRef.getOfficeNoByID(lOfficeID);
					}
				}
			}
			if (strFirstTD == null)
			{
				strFirstTD = "";
			}
			if (strSecondTD == null)
			{
				strSecondTD = "";
			}
			//������
			//����ǰ׺
			if (strPrefix != null && !strPrefix.trim().equals(""))
			{
				for (int i = 0; i < strMainNames.length; i++)
				{
					strMainNames[i] = strPrefix + strMainNames[i];
				}
				for (int i = 0; i < strReturnNames.length; i++)
				{
					strReturnNames[i] = strPrefix + strReturnNames[i];
				}
			}
			//�������ڵ�����
			String sFeatures = null;
			if (strDisplayNames.length < 3)
			{
				sFeatures = "toolbar=no,menubar=no,resizable=no,location=no," + "width=520,height=450,scrollbars=yes";
			}
			else
			{
				sFeatures = "toolbar=no,menubar=no,resizable=no,location=no," + "width=624,height=540,scrollbars=yes";
			}
			//���ɴ��ݸ��������ڵĲ����ַ���
			String strParam = "";
			strParam = "strFormName=" + strFormName;
			strParam += "&strMagnifierName=" + strMagnifierName;
			strParam += "&nIndex=" + nIndex;
			//================================�޸Ŀ�ʼ================================================
			/*�޸� by kenny (��־ǿ) ����˺Ŷ���������*/
			strSQL =
				"getAccountSQL("
					+ lOfficeID
					+ ","
					+ lCurrencyID
					+ ","
					+ -1
					+ ","
					+ 100
					+ ","
					+ -1
					+ ","
					+ strFormName
					+ "."
					+ strMainNames[0]
					+ ".value)";
			if (!isSQL(strSQL))
			{
				strParam += "&strSQL='+" + strSQL + "+'";
			}
			else
			{
				strParam += "&strSQL=" + getSQL(strSQL);
			}
			//================================�޸Ľ���================================================
			if (strNextControls.length() > 0)
			{
				strParam += "&strNextControls=" + strNextControls;
			}
			for (int i = 0; i < strMainNames.length; i++)
			{
				strParam += "&strMainNames=" + strMainNames[i];
				strParam += "&strMainFields=" + strMainFields[i];
			}
			if (strReturnNames != null)
			{
				boolean bValue = false;
				if (strReturnValues != null && strReturnValues.length == strReturnNames.length)
				{
					bValue = true;
				}
				for (int i = 0; i < strReturnNames.length; i++)
				{
					//�����������
					strParam += "&strReturnNames=" + strReturnNames[i];
					strParam += "&strReturnFields=" + strReturnFields[i];
					if (bValue)
					{
						out.println("<input type=\"hidden\" name=\"" + strReturnNames[i] + "\" value=\"" + strReturnValues[i] + "\">");
					}
					else
					{
						out.println("<input type=\"hidden\" name=\"" + strReturnNames[i] + "\">");
					}
				}
			}
			for (int i = 0; i < strDisplayNames.length; i++)
			{
				//�����������
				strParam += "&strDisplayNames=" + strDisplayNames[i];
				strParam += "&strDisplayFields=" + strDisplayFields[i];
			}
			//Log.print("strParam = " + strParam);

			/*�޸� by kenny (��־ǿ) ����˺Ŷ���������*/
			//ȡ���˻�ֵ����������
			int accountField = Config.getInteger(ConfigConstant.GLOBAL_ACCOUNTNO_FIELD,4);
			if (nCaseNumber == 3) {
				accountField = accountField-1;
			} else if (nCaseNumber == 4) {
			}
			String tag = Config.getProperty(ConfigConstant.GLOBAL_ACCOUNTNO_TAG,"");
			String[] accountNo = strAccountNo.split(tag);
			String[] strTempCtl = new String[accountField];
			for (int i=0; i<accountField; i++) {
				strTempCtl[i]=strMainNames[0] + "Ctrl"+(i+1);
			}
			//���ɲ�ѯ��ť���¼��ַ���
			//���ɲ�ѯ��ť���¼��ַ���
			String sOnKeydown =
				"if("
					+ strFormName
					+ "."
					+ strTempCtl[0]
					+ ".disabled == false) {gnIsSelectCtrl=1;window.open('"
					+ Env.URL_PREFIX
					+ "/iTreasury-ebank/magnifier/ShowMagnifierZoom.jsp?"
					+ strParam
					+ "', 'SelectAnything', '"
					+ sFeatures
					+ "', false);}";
			String sOnKeyUp = "";
			if (strReturnNames != null)
			{
				for (int i = 0; i < strReturnNames.length; i++)
				{
					sOnKeyUp += strReturnNames[i] + ".value = -1; ";
				}
			}
			//int iPos = strMainProperty.toLowerCase().indexOf("disabled");
			int iPos = -1;
			//��ʾ�ؼ�
			if (iPos == -1)
			{
				out.println(
					"<td  "
						+ strFirstTD
						+ "  >"
						+ strTitle
						+ "��&nbsp;"
						+ "<a href=#><img name=\""
						+ strButtonName
						+ "\" src='/webob/graphics/icon.gif' border=0 onclick=\""
						+ sOnKeydown
						+ "\"></a></td>");
				//image
			}
			else
			{
				out.println("<td  " + strFirstTD + " >" + strTitle + ":&nbsp;" + "<a href=#><img name=\"" + strButtonName + "\" src='/webloan/graphics/icon.gif' border=0 ></a></td>");
				//image
			}

			/*�޸� by kenny (��־ǿ) ����˺Ŷ���������*/
			//��װ�˺�ģ����ƥ���ֵ
			String strTempFunction = "";
			String strTempFunctionForFixed = "";
			for (int i=0; i<accountField; i++) {
				if (i>0) {
					strTempFunctionForFixed = strTempFunctionForFixed + ",";
				}
				strTempFunctionForFixed = strTempFunctionForFixed + strFormName + "." + strTempCtl[i] + ".value";
			}
			strTempFunction = 
				strPrefix
				+ strMainNames[0]
				+ "setWholeAcccountNo("
				+ strTempFunctionForFixed
				+ ");"
				+ sOnKeyUp;
			//������ʾ
			int length = Config.getInteger(ConfigConstant.GLOBAL_MAXACCOUNTNO_LENGTH,1);
			int number = Config.getInteger(ConfigConstant.GLOBAL_MAXACCOUNTNO_NUMBER,1);
			out.println("<td " + strSecondTD + ">");
			out.println("<input type=\"hidden\" name=\"" + strMainNames[0] + "\" value=\"" + strAccountNo + "\">");
			for (int i=0; i<accountField; i++) {
				if (i>0) {
					out.println(tag);
				}
				String strSize= "";
				String accountFieldValue = "";
				if (accountNo.length>i) {
					accountFieldValue = accountNo[i];
				}
				if (number == i+1) {
					strSize = "size=\""+length+"\" maxlength=\""+length+"\"";
				} else {
					strSize = "size=\"2\" maxlength=\"2\"";
				}
				
				out.println("<input type=\"text\" "+strSize+" name=\""
				+ strTempCtl[i]
				+ "\" value=\""
				+ accountFieldValue
				+ "\" class=\"box\"  onKeyDown=\"gnIsSelectCtrl=1;"
				+ strPrefix
				+ strMainNames[0]
				+ "fieldFocus"+(i+1)+"("
				+ strFormName
				+ "."
				+ strTempCtl[i]
				+ ".value)\" onKeyUp=\""
				+ strTempFunction
				+ "\">");
			}
			//script��������
			out.println("</td>");
			out.println("<script language=\"JavaScript\">");
			for (int i=0; i<accountField; i++) {
				int iLength = 0;
				if (number == i+1) {
					iLength = length;
				} else {
					iLength = 2;
				}
				out.println("function " + strPrefix + strMainNames[0] + "fieldFocus"+(i+1)+"(sValue)");
				out.println("{");
				out.println("	var k = window.event.keyCode;");
				out.println("	if (k != 46 && k != 8 && k != 37 && k != 39)");
				out.println("	{");
				out.println("		var i;");
				out.println("		if (k == 13 || sValue.length == "+iLength+")");
				out.println("		{");
				out.println("			" + strFormName + "." + strTempCtl[i] + ".value = sValue;");
				out.println("			if (sValue.length > 0)");
				out.println("			{");
				out.println("				for (i=0; i<"+iLength+"-sValue.length; i++)");
				out.println("				{");
				out.println("					" + strFormName + "." + strTempCtl[i] + ".value = " + "\"0\" + " + strFormName + "." + strTempCtl[i] + ".value;");
				out.println("				}");
				out.println("			}");
				if (i == accountField-1) {
					out.println("			" + sOnKeydown);
				} else {
					out.println("			" + strFormName + "." + strTempCtl[i+1] + ".focus();");
					out.println("			" + strFormName + "." + strTempCtl[i+1] + ".select();");
				}
				out.println("		}");
				out.println("	}");
				if (i > 0){
					out.println("	else if (k == 8 && sValue.length == 0)");
					out.println("	{");
					out.println("		" + strFormName + "." + strTempCtl[i-1] + ".value = \"\";");
					out.println("		" + strFormName + "." + strTempCtl[i-1] + ".focus();");
					out.println("	}");
				}
				out.println("}");
			}
			//��д�˻����ε�ֵ
			String param = "";
			String[] where = new String[accountField];
			String[] value = new String[accountField];
			for (int i=0; i<accountField; i++) {
				if (i>0) {
					param = param+",";
				}
				//��װ�����Ĳ���
				param = param+"sValue"+(i+1);
				for (int j=0; j<accountField-i; j++) {
					if (j == 0) {
						where[i] = "sValue"+(j+1)+" != \"\"";
						value[i] = "sValue"+(j+1);
					} else {
						//��װ�ж�����
						where[i] = where[i]+" && sValue"+(j+1)+" != \"\"";
						//��װ��������µ�ֵ
						value[i] = value[i]+" + \"-\" + sValue"+(j+1);
					}
				}
			}
			out.println("function " + strPrefix + strMainNames[0] + "setWholeAcccountNo("+param+")");
			out.println("{");
			String strIf = "";
			for (int i=0; i<accountField; i++) {
				if (i == 0) {
					strIf = "	if (";
				} else {
					strIf = "	} else if (";
				}
				out.println(strIf+where[i]+") {");
				out.println("		" + strFormName + "." + strMainNames[0] + ".value = "+value[i]+";");
			}
			out.println("	} else {");
			out.println("		" + strFormName + "." + strMainNames[0] + ".value = \"\";");
			out.println("	}");
			out.println("}");
			out.println("</script>");

			/*
			//���˺Ų��
			String strAccountNo1 = "";
			String strAccountNo2 = "";
			String strAccountNo3 = "";
			String strAccountNo4 = "";
			if (strAccountNo != null && strAccountNo.length() > 0)
			{
				String strTemp = strAccountNo;
				strAccountNo1 = strTemp.substring(0, strTemp.indexOf("-"));
				Log.print(strAccountNo1);
				strTemp = strTemp.substring(strTemp.indexOf("-") + 1);
				strAccountNo2 = strTemp.substring(0, strTemp.indexOf("-"));
				Log.print(strAccountNo2);
				strTemp = strTemp.substring(strTemp.indexOf("-") + 1);
				if (strTemp.indexOf("-") > 0)
				{
					strAccountNo3 = strTemp.substring(0, strTemp.indexOf("-"));
					Log.print(strAccountNo3);
				}
				else
				{
					strTemp = strTemp.substring(strTemp.indexOf("-") + 1);
					strAccountNo3 = strTemp;
					Log.print(strAccountNo3);
				}
				if (strTemp.indexOf("-") > 0)
				{
					strTemp = strTemp.substring(strTemp.indexOf("-") + 1);
					strAccountNo4 = strTemp;
					Log.print(strAccountNo4);
				}
			}
			String strTempCtl1 = strMainNames[0] + "Ctrl1";
			String strTempCtl2 = strMainNames[0] + "Ctrl2";
			String strTempCtl3 = strMainNames[0] + "Ctrl3";
			String strTempCtl4 = strMainNames[0] + "Ctrl4";
			String strTempFunction = "";
			if (nCaseNumber == 3)
			{
				strTempFunction =
					strPrefix
						+ "setWholeAcccountNo("
						+ strFormName
						+ "."
						+ strTempCtl1
						+ ".value,"
						+ strFormName
						+ "."
						+ strTempCtl2
						+ ".value,"
						+ strFormName
						+ "."
						+ strTempCtl3
						+ ".value,'');"
						+ sOnKeyUp;
			}
			else if (nCaseNumber >= 4)
			{
				strTempFunction =
					strPrefix
						+ "setWholeAcccountNo("
						+ strFormName
						+ "."
						+ strTempCtl1
						+ ".value,"
						+ strFormName
						+ "."
						+ strTempCtl2
						+ ".value,"
						+ strFormName
						+ "."
						+ strTempCtl3
						+ ".value,"
						+ strFormName
						+ "."
						+ strTempCtl4
						+ ".value);"
						+ sOnKeyUp;
			}
			out.println("<td  " + strSecondTD + " nowrap>");
			out.println("<input type=\"hidden\" name=\"" + strMainNames[0] + "\" value=\"" + strAccountNo + "\">");
			out.println(
				"<input type=\"text\" size=\"2\" name=\""
					+ strTempCtl1
					+ "\" value=\""
					+ strAccountNo1
					+ "\" class=\"box\"  onKeyDown=\""
					+ strPrefix
					+ "fieldFirstFocus("
					+ strFormName
					+ "."
					+ strTempCtl1
					+ ".value)\" onKeyUp=\""
					+ strTempFunction
					+ "\">");
			out.println("-");
			out.println(
				"<input type=\"text\" size=\"2\" name=\""
					+ strTempCtl2
					+ "\" value=\""
					+ strAccountNo2
					+ "\" class=\"box\"  onKeyDown=\""
					+ strPrefix
					+ "fieldSecondFocus("
					+ strFormName
					+ "."
					+ strTempCtl2
					+ ".value)\" onKeyUp=\""
					+ strTempFunction
					+ "\">");
			if (nCaseNumber == 3)
			{
				out.println("-");
				out.println(
					"<input type=\"text\" size=\"4\" name=\""
						+ strTempCtl3
						+ "\" value=\""
						+ strAccountNo3
						+ "\" class=\"box\"  onKeyDown=\""
						+ strPrefix
						+ "fieldThirdFocus("
						+ strFormName
						+ "."
						+ strTempCtl3
						+ ".value)\" onKeyUp=\""
						+ strTempFunction
						+ "\">");
			}
			else if (nCaseNumber >= 4)
			{
				out.println("-");
				out.println(
					"<input type=\"text\" size=\"4\" name=\""
						+ strTempCtl3
						+ "\" value=\""
						+ strAccountNo3
						+ "\" class=\"box\"  onKeyDown=\""
						+ strPrefix
						+ "fieldThirdFocus("
						+ strFormName
						+ "."
						+ strTempCtl3
						+ ".value)\" onKeyUp=\""
						+ strTempFunction
						+ "\">");
				out.println("-");
				out.println(
					"<input type=\"text\" maxlength=\"1\" size=\"1\" name=\""
						+ strTempCtl4
						+ "\" value=\""
						+ strAccountNo4
						+ "\" class=\"box\"  onKeyDown=\"if(event.keyCode==13) "
						+ sOnKeydown
						+ " else "
						+ strPrefix
						+ "fieldFouthFocus("
						+ strFormName
						+ "."
						+ strTempCtl4
						+ ".value);\" onKeyUp=\""
						+ strTempFunction
						+ "\">");
			}
			out.println("</td>");
			//��ӡ�ű�����
			//------------------------�س��¼�------------------------
			out.println("<script language=\"JavaScript\">");
			out.println("function " + strPrefix + "fieldFirstFocus(sValue)");
			out.println("{");
			out.println("	var k = window.event.keyCode;");
			out.println("	if (k != 46 && k != 8 && k != 37 && k != 39)");
			out.println("	{");
			out.println("		if (k == 13 || sValue.length == 2)");
			out.println("		{");
			out.println("           gnIsSelectCtrl=1; ");
			out.println("		   " + strFormName + "." + strTempCtl2 + ".focus();");
			out.println("			" + strFormName + "." + strTempCtl2 + ".select();");
			out.println("		}");
			out.println("	}");
			out.println("}");
			out.println("function " + strPrefix + "fieldSecondFocus(sValue)");
			out.println("{");
			out.println("	var k = window.event.keyCode;");
			out.println("	if (k != 46 && k != 8 && k != 37 && k != 39)");
			out.println("	{");
			out.println("		if (k == 13 || sValue.length == 2)");
			out.println("		{");
			out.println("           gnIsSelectCtrl=1; ");
			out.println("		   " + strFormName + "." + strTempCtl3 + ".focus();");
			out.println("			" + strFormName + "." + strTempCtl3 + ".select();");
			out.println("		}");
			out.println("	}");
			out.println("	else if (k == 8 && sValue.length == 0)");
			out.println("	{");
			out.println("		" + strFormName + "." + strTempCtl1 + ".value = \"\";");
			out.println("		" + strFormName + "." + strTempCtl1 + ".focus();");
			out.println("	}");
			out.println("}");
			if (nCaseNumber == 3)
			{
				out.println("function " + strPrefix + "fieldThirdFocus(sValue)");
				out.println("{");
				out.println("	var k = window.event.keyCode;");
				out.println("	if (k != 46 && k != 8 && k != 37 && k != 39)");
				out.println("	{");
				out.println("		if (k == 13 || sValue.length == 4)");
				out.println("		{");
				out.println("			" + sOnKeydown);
				out.println("		}");
				out.println("	}");
				out.println("	else if (k == 8 && sValue.length == 0)");
				out.println("	{");
				out.println("		" + strFormName + "." + strTempCtl2 + ".value = \"\";");
				out.println("		" + strFormName + "." + strTempCtl2 + ".focus();");
				out.println("	}");
				out.println("}");
			}
			else if (nCaseNumber >= 4)
			{
				out.println("function " + strPrefix + "fieldThirdFocus(sValue)");
				out.println("{");
				out.println("	var k = window.event.keyCode;");
				out.println("	if (k != 46 && k != 8 && k != 37 && k != 39)");
				out.println("	{");
				out.println("		if (k == 13 || sValue.length == 4)");
				out.println("		{");
				out.println("           gnIsSelectCtrl=1; ");
				out.println("		   " + strFormName + "." + strTempCtl4 + ".focus();");
				out.println("		   " + strFormName + "." + strTempCtl4 + ".select();");
				out.println("		}");
				out.println("	}");
				out.println("	else if (k == 8 && sValue.length == 0)");
				out.println("	{");
				out.println("		" + strFormName + "." + strTempCtl2 + ".value = \"\";");
				out.println("		" + strFormName + "." + strTempCtl2 + ".focus();");
				out.println("	}");
				out.println("}");
				out.println("function " + strPrefix + "fieldFouthFocus(sValue)");
				out.println("{");
				out.println("	var k = window.event.keyCode;");
				out.println("	if (k != 46 && k != 8 && k != 37 && k != 39)");
				out.println("	{");
				out.println("		if (k == 13 )");
				out.println("		{");
				out.println("           gnIsSelectCtrl=1; ");
				out.println("			" + sOnKeydown);
				out.println("		}");
				out.println("	}");
				out.println("	else if (k == 8 && sValue.length == 0)");
				out.println("	{");
				out.println("		" + strFormName + "." + strTempCtl3 + ".value = \"\";");
				out.println("		" + strFormName + "." + strTempCtl3 + ".focus();");
				out.println("	}");
				out.println("}");
			}
			out.println("function " + strPrefix + "setWholeAcccountNo(sValue1,sValue2,sValue3,sValue4)");
			out.println("{");
			out.println("	if (sValue1 != \"\" && sValue2 != \"\" && sValue3 != \"\" && sValue4  != \"\")");
			out.println("	{");
			out.println("		" + strFormName + "." + strMainNames[0] + ".value = sValue1 + \"-\" + sValue2+ \"-\" + sValue3 + \"-\" + sValue4;");
			out.println("	}");
			out.println("	else if (sValue1 != \"\" && sValue2 != \"\" && sValue3 != \"\")");
			out.println("	{");
			out.println("		" + strFormName + "." + strMainNames[0] + ".value = sValue1 + \"-\" + sValue2 + \"-\" + sValue3;");
			out.println("	}");
			out.println("	else if (sValue1 != \"\" && sValue2 != \"\")");
			out.println("	{");
			out.println("		" + strFormName + "." + strMainNames[0] + ".value = sValue1 + \"-\" + sValue2;");
			out.println("	}");
			out.println("	else if (sValue1 != \"\")");
			out.println("	{");
			out.println("		" + strFormName + "." + strMainNames[0] + ".value = sValue1;");
			out.println("	}");
			out.println("	else if (sValue1 == \"\")");
			out.println("	{");
			out.println("		" + strFormName + "." + strMainNames[0] + ".value = \"\";");
			out.println("	}");
			out.println("}");
			//--------------------ģ��ƥ�䲿��------------------
			out.println("function get" + strMainNames[0] + "(Account)");
			out.println("{");
			out.println("   var sql = \"\"; ");
			out.println("   if (Account != null && Account != \"\") ");
			out.println("   {");
			out.println("       sql +=  \" and " + strMainFields[0] + " like '" + URLEncoder.encode("%") + "\"+Account+\"" + URLEncoder.encode("%") + "'\";    ");
			out.println("   }");
			out.println("    ");
			out.println("   return sql;    ");
			out.println("}");
			out.println("</script>");
			*/
		}
		catch (Exception exp)
		{
			throw exp;
		}
	}

	/**
			* ��ʾ����Ŵ�
			* @param JspWriter out
			* @param String strMagnifierName �Ŵ󾵵�����
			* @param String strFormName ��ҳ�������
			* @param strPrefix strPrefix �ؼ�����ǰ׺
			* @param String[] strMainNames �Ŵ󾵻�����λֵ�б�
			* @param String[] strMainFields �Ŵ󾵻�����λ��Ӧ�ı���ֶ�
			* @param String[] strReturnNames �Ŵ󾵷���ֵ�б�(����ֵ)
			* @param String[] strReturnFields �Ŵ󾵷���ֵ(����ֵ)��Ӧ�ı���ֶ��б�
			* @param String   strReturnInitValues �Ŵ󾵻�����λ��Ӧ�ĳ�ʼֵ
			* @param String[] strReturnValues �Ŵ󾵷���ֵ(����ֵ)��Ӧ�ĳ�ʼֵ
			* @param String[] strDisplayNames �Ŵ�С������ʾ����λ����
			* @param String[] strDisplayFields �Ŵ�С������ʾ��λ��Ӧ�ı���ֶ�
			* @param nIndex ȷ��ѡ����,��0��ʼ,���С��0,��Ĭ��������λ����ѡ��,������ڻ����strDisplayNames.length,��û��ѡ����
			* @param strMainProperty �Ŵ���λ����
			* @param strSQL �Ŵ󾵲�ѯSQL���
			* @param strMatchValue �Ŵ�Ҫģ��ƥ����ֶ�
			* @param strNextControls ������һ������
			* @param strTitle ��λ����
			* @param strFirstTD ��һ��TD������
			* @param strSecondTD �ڶ���TD������ 
			* @param blnIsOptional �Ƿ��ǿ�ѡ�Ŀǰ����ժҪ���ֽ�����Ŵ����ã�
			* @param blnIsRateCtrl �Ƿ������ʿؼ�
			* @throws Exception
			*/
	public static void showDifferZoomCtrl(
		JspWriter out,
		String strMagnifierName,
		String strFormName,
		String strPrefix,
		String[] strMainNames,
		String[] strMainFields,
		String[] strReturnNames,
		String[] strReturnFields,
		String[] strReturnValues,
		String[] strDisplayNames,
		String[] strDisplayFields,
		int nIndex,
		String strMainProperty,
		String strSQL,
		String[] strNextControls,
		String strTitle,
		String strFirstTD,
		String strSecondTD,
		boolean blnIsOptional,
		boolean blnIsRateCtrl,
		String sPageName)
		throws Exception
	{
		String strButtonName = "button";
		try
		{
			//���Ŵ󾵲���
			checkValue(strMainNames, strMainFields, true);
			checkValue(strReturnNames, strReturnFields, strReturnValues, false);
			checkValue(strDisplayNames, strDisplayFields, true);
			if (strMagnifierName == null || strFormName == null || strFormName.equals("") || strSQL == null || strSQL.equals(""))
			{
				throw new IException(ZOOMERRORMSG);
			}
			if (strNextControls == null)
			{
				throw new IException(ZOOMERRORMSG);
			}
			if (strFirstTD == null)
			{
				strFirstTD = "";
			}
			if (strSecondTD == null)
			{
				strSecondTD = "";
			}
			//������
			//����ǰ׺
			if (strPrefix != null && !strPrefix.trim().equals(""))
			{
				for (int i = 0; i < strMainNames.length; i++)
				{
					strMainNames[i] = strPrefix + strMainNames[i];
				}
				for (int i = 0; i < strReturnNames.length; i++)
				{
					strReturnNames[i] = strPrefix + strReturnNames[i];
				}
			}
			//�������ڵ�����
			String sFeatures = null;
			if (strDisplayNames.length < 3)
			{
				sFeatures = "toolbar=no,menubar=no,resizable=no,location=no,width=520,height=450,scrollbars=yes";
			}
			else
			{
				sFeatures = "toolbar=no,menubar=no,resizable=no,location=no,width=624,height=540,scrollbars=yes";
			}
			//���ɴ��ݸ��������ڵĲ����ַ���
			String strParam = "";
			strParam = "strFormName=" + strFormName;
			strParam += "&strMagnifierName=" + strMagnifierName;
			strParam += "&nIndex=" + nIndex;
			if (!isSQL(strSQL))
			{
				strParam += "&strSQL='+" + strSQL + "+'";
			}
			else
			{
				strParam += "&strSQL=" + getSQL(strSQL);
			}
			for (int i = 0; i < strNextControls.length; i++)
			{
				strParam += "&strNextControls=" + strNextControls[i];
			}
			for (int i = 0; i < strMainNames.length; i++)
			{
				strParam += "&strMainNames=" + strMainNames[i];
				strParam += "&strMainFields=" + strMainFields[i];
			}
			if (strReturnNames != null)
			{
				boolean bValue = false;
				if (strReturnValues != null && strReturnValues.length == strReturnNames.length)
				{
					bValue = true;
				}
				for (int i = 0; i < strReturnNames.length; i++)
				{
					//�����������
					strParam += "&strReturnNames=" + strReturnNames[i];
					strParam += "&strReturnFields=" + strReturnFields[i];
					if (bValue)
					{
						out.println("<input type=\"hidden\" name=\"" + strReturnNames[i] + "\" value=\"" + strReturnValues[i] + "\">");
					}
					else
					{
						out.println("<input type=\"hidden\" name=\"" + strReturnNames[i] + "\">");
					}
				}
			}
			for (int i = 0; i < strDisplayNames.length; i++)
			{
				//�����������
				strParam += "&strDisplayNames=" + strDisplayNames[i];
				strParam += "&strDisplayFields=" + strDisplayFields[i];
			}
			//Log.print("strParam = " + strParam);
			//���ɲ�ѯ��ť���¼��ַ���
			String sOnKeydown =
				"if("
					+ strFormName
					+ "."
					+ strMainNames[0]
					+ ".disabled == false) {"
					+ strFormName
					+ "."
					+ strMainNames[0]
					+ ".focus();"
					+ " gnIsSelectCtrl=1;window.open('/NASApp/iTreasury-ebank/magnifier/"
					+ sPageName
					+ "?"
					+ strParam
					+ "', 'SelectAnything', '"
					+ sFeatures
					+ "', false);}";
			//
			String sOnKeyUp = "";
			if (strReturnNames != null)
			{
				for (int i = 0; i < strReturnNames.length; i++)
				{
					sOnKeyUp += strReturnNames[i] + ".value = -1; ";
				}
			}
			//int iPos = strMainProperty.toLowerCase().indexOf("disabled");
			int iPos = -1;
			//��ʾ�ؼ�
			if (iPos == -1)
			{
				out.println(
					"<td "
						+ strFirstTD
						+ ">"
						+ strTitle
						+ ":&nbsp;"
						+ "<a href=#><img name=\""
						+ strButtonName
						+ "\" src='/webob/graphics/zoom.gif' border=0 onclick=\""
						+ sOnKeydown
						+ "\"></a></td>");
				//image
			}
			else
			{
				out.println("<td " + strFirstTD + ">" + strTitle + ":&nbsp;" + "<a href=#><img name=\"" + strButtonName + "\" src='/webob/graphics/zoom.gif' border=0 ></a></td>");
				//image
			}
			//blnIsOptional,�Ƿ��ѡ�����ժҪ���ֽ�����Ŵ���Ч��
			if (blnIsOptional == true)
			{
				if (strNextControls != null && strNextControls.length > 0)
				{
					strMainProperty = " onfocus=\"nextfield='" + strNextControls[0] + "'\" " + strMainProperty;
				}
				if (blnIsRateCtrl == true)
				{
					out.println("<td" + strSecondTD + "><input type=\"text\" name=\"" + strMainNames[0] + "\" class=\"box\" " + strMainProperty + ">%</td>");
				}
				else
				{
					out.println("<td" + strSecondTD + "><input type=\"text\" name=\"" + strMainNames[0] + "\" class=\"box\" " + strMainProperty + "></td>");
				}
			}
			else
			{
				if (blnIsRateCtrl == true)
				{
					out.println(
						"<td"
							+ strSecondTD
							+ "><input type=\"text\" name=\""
							+ strMainNames[0]
							+ "\" class=\"box\" "
							+ strMainProperty
							+ " onKeyDown=\"if(event.keyCode==13) "
							+ sOnKeydown
							+ "\" onKeyUp=\""
							+ sOnKeyUp
							+ "\">%</td>");
				}
				else
				{
					out.println(
						"<td"
							+ strSecondTD
							+ "><input type=\"text\" name=\""
							+ strMainNames[0]
							+ "\" class=\"box\" "
							+ strMainProperty
							+ " onKeyDown=\"if(event.keyCode==13) "
							+ sOnKeydown
							+ "\" onKeyUp=\""
							+ sOnKeyUp
							+ "\"></td>");
				}
			}
		}
		catch (Exception exp)
		{
			throw exp;
		}
	}
	
	/**
	 * �����û��Ŵ�
	 * @param out
	 * @param lOfficeID ���´�ID
	 * @param lCurrencyID ����ID
	 * @param strFormName ��������
	 * @param strCtrlName �Ŵ����ؼ�����
	 * @param strTitle �Ŵ�����
	 * @param lUserID �û�ID(��ʶֵ)
	 * @param strUserName �û�����(��ʶֵ)
	 * @param strFirstTD ��һ��TD������
	 * @param strSecondTD �ڶ���TD������
	 * @param sNextControlsClient ��һ������������ý���Ŀؼ�
	 */
	public static void createUserCtrl(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		long lClientID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lUserID,
		String strUserName,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls)
	{
		String strMagnifierName = URLEncoder.encode("�û�");
		String strMainProperty = " maxlength='30' value='" + strUserName + "'";
		String strPrefix = "";
		String[] strMainNames = { strCtrlName + "Ctrl" };
		String[] strMainFields = { "UserName" };
		String[] strReturnNames = { strCtrlName };
		String[] strReturnFields = { "UserID" };
		String[] strReturnValues = { String.valueOf(lUserID)};
		String[] strDisplayNames = { URLEncoder.encode("�û�����"),URLEncoder.encode("��λ")};
		String[] strDisplayFields = { "UserName","ClinetName" };
		int nIndex = 0;
		String strSQL = "getUserSQL(" + lOfficeID + "," + lCurrencyID + "," + lClientID + "," + strCtrlName + "Ctrl.value)";
		try
		{
			showZoomCtrl(
				out,
				strMagnifierName,
				strFormName,
				strPrefix,
				strMainNames,
				strMainFields,
				strReturnNames,
				strReturnFields,
				strReturnValues,
				strDisplayNames,
				strDisplayFields,
				nIndex,
				strMainProperty,
				strSQL,
				strNextControls,
				strTitle,
				strFirstTD,
				strSecondTD, false, false);
		}
		catch (Exception e)
		{
			Log.print(strMagnifierName + "�Ŵ�[" + strCtrlName + "]�쳣��" + e.toString());
		}
	}
	/**
	 * ��ʾ�������Ŵ�
	 * @param lOfficeID ���´�ID
	 * @param lCurrencyID ����
	 * @param out
	 * @param lApprovalID ������ID
	 * @param strFormName ��ҳ�������
	 * @param strControlName ��ҳ��ؼ����� 
	 * @param strPrefix �ؼ�����ǰ׺
	 * @param strMainProperty �Ŵ���λ����
	 * @param strReturnName �Ŵ���������ֵ
	 * @param strSQL �Ŵ󾵲�ѯSQL���
	 * @param strNextControls ������һ������
	 * @throws Exception
	 */
	public static void CreateApprovalSettingCtrl(long lOfficeID, long lCurrencyID,long lClientID, JspWriter out, long lApprovalID, String strFormName, String strControlName, String strPrefix, String strMainProperty, String strReturnName, String strNextControls) throws Exception
	{
		try
		{
			String strMagnifierName = "������";
			String[] strMainNames = { strControlName };
			String[] strMainFields = { "sName" };
			if(strReturnName.equals(""))
			{
				strReturnName = "hidApprovalID";
			}
			String[] strReturnNames = { strReturnName };
			String[] strReturnFields = { "ID" };
			String[] strReturnValues = { "-1" };
			String[] strDisplayNames = { "���������", "����������" };
			String[] strDisplayFields = { "ID", "sName" };
			int nIndexOffice = 0;
			String strSQL = strPrefix + "getApprovalSettingSQL(" + lOfficeID + ","  + lApprovalID+ "," + lClientID + ")";
						
			showZoomCtrl(
				out,
				strMagnifierName,
				strFormName,
				strPrefix,
				strMainNames,
				strMainFields,
				strReturnNames,
				strReturnFields,
				"",
				strReturnValues,
				strDisplayNames,
				strDisplayFields,
				nIndexOffice,
				strMainProperty,
				strSQL,
				"",
				strNextControls,
				strMagnifierName,
				"",
				"");
		}
		catch (Exception exp)
		{
			throw exp;
		}
	}
	
	/**
	 * ��ʾ�������������÷Ŵ�
	 * added by ypxu
	 * 2007-04-28
	 */
	public static void CreateApprovalRelationSettingCtrl(JspWriter out, long lOfficeID, long lClientID, String strFormName, String strControlName, String strMainProperty, String strReturnName1, String strReturnName2, String strNextControls) throws Exception
	{
		try
		{
			String strMagnifierName = "���̹���";
			String strPrefix = "";
			String[] strMainNames = { strControlName };
			String[] strMainFields = { "Name" };
			String[] strReturnNames = { strReturnName1, strReturnName2 };
			String[] strReturnFields = { "ID", "Name" };
			String[] strReturnValues = { "", "" };
			String[] strDisplayNames = { "����������" };
			String[] strDisplayFields = { "Name" };
			int nIndexOffice = 0;
			String strSQL = strPrefix + "getApprovalRelationSettingSQL(" + lOfficeID + "," + lClientID + ")";
						
			showZoomCtrl(
				out,
				strMagnifierName,
				strFormName,
				strPrefix,
				strMainNames,
				strMainFields,
				strReturnNames,
				strReturnFields,
				"",
				strReturnValues,
				strDisplayNames,
				strDisplayFields,
				nIndexOffice,
				strMainProperty,
				strSQL,
				"",
				strNextControls,
				strMagnifierName,
				"",
				"");
		}
		catch (Exception exp)
		{
			throw exp;
		}
	}
	
	/**
     * ��ʾ��ͨ�Ŵ�
     * @param JspWriter out
     * @param String strMagnifierName �Ŵ󾵵�����
     * @param String strFormName ��ҳ�������
     * @param strPrefix strPrefix �ؼ�����ǰ׺
     * @param String[] strMainNames �Ŵ󾵻�����λֵ�б�
     * @param String[] strMainFields �Ŵ󾵻�����λ��Ӧ�ı���ֶ�
     * @param String[] strReturnNames �Ŵ󾵷���ֵ�б�(����ֵ)
     * @param String[] strReturnFields �Ŵ󾵷���ֵ(����ֵ)��Ӧ�ı���ֶ��б�
     * @param String   strReturnInitValues �Ŵ󾵻�����λ��Ӧ�ĳ�ʼֵ
     * @param String[] strReturnValues �Ŵ󾵷���ֵ(����ֵ)��Ӧ�ĳ�ʼֵ
     * @param String[] strDisplayNames �Ŵ�С������ʾ����λ����
     * @param String[] strDisplayFields �Ŵ�С������ʾ��λ��Ӧ�ı���ֶ�
     * @param nIndex ȷ��ѡ����,��0��ʼ,���С��0,��Ĭ��������λ����ѡ��,������ڻ����strDisplayNames.length,��û��ѡ����
     * @param strMainProperty �Ŵ���λ����
     * @param strSQL �Ŵ󾵲�ѯSQL���
     * @param strMatchValue �Ŵ�Ҫģ��ƥ����ֶ�
     * @param strNextControls ������һ������
     * @param strTitle ��λ����
     * @param strFirstTD ��һ��TD������
     * @param strSecondTD �ڶ���TD������ 
     * @param blnIsOptional �Ƿ��ǿ�ѡ�Ŀǰ����ժҪ���ֽ�����Ŵ����ã�
     * @param blnIsRateCtrl �Ƿ������ʿؼ�
     * @throws Exception
     */
 public static void showRemarkCtrl(
     JspWriter out,
     String strMagnifierName,
     String strFormName,
     String strPrefix,
     String[] strMainNames,
     String[] strMainFields,
     String[] strReturnNames,
     String[] strReturnFields,
     String strReturnInitValues,
     String[] strReturnValues,
     String[] strDisplayNames,
     String[] strDisplayFields,
     int nIndex,
     String strMainProperty,
     String strSQL,
     String strMatchValue,
     String strNextControls,
     String strTitle,
     String strFirstTD,
     String strSecondTD)
     throws Exception
 {
     String strButtonName = "button";
     try
     {
         //���Ŵ󾵲���
         checkValue(strMainNames, strMainFields, true);
         checkValue(strReturnNames, strReturnFields, strReturnValues, false);
         checkValue(strDisplayNames, strDisplayFields, true);
         if (strMagnifierName == null || strFormName == null || strFormName.equals("") || strSQL == null || strSQL.equals(""))
         {
             throw new IException(ZOOMERRORMSG);
         }
         if (strNextControls == null)
         {
             throw new IException(ZOOMERRORMSG);
         }
         if (strMatchValue == null || strMatchValue.equals(""))
         {
             strMatchValue = strMainFields[0];
         }

         if (strFirstTD == null)
         {
             strFirstTD = "";
         }
         if (strSecondTD == null)
         {
             strSecondTD = "";
         }

         if (strReturnInitValues == null)
         {
             strReturnInitValues = "";
         }

         //������
         //����ǰ׺
         if (strPrefix != null && !strPrefix.trim().equals(""))
         {
             for (int i = 0; i < strMainNames.length; i++)
             {
                 strMainNames[i] = strPrefix + strMainNames[i];
             }
             for (int i = 0; i < strReturnNames.length; i++)
             {
                 strReturnNames[i] = strPrefix + strReturnNames[i];
             }
         }
         //�������ڵ�����
         String sFeatures = null;
         if (strDisplayNames.length < 3)
         {
             sFeatures = "toolbar=no,menubar=no,resizable=no,location=no,width=520,height=450,scrollbars=yes";
         }
         else
         {
             sFeatures = "toolbar=no,menubar=no,resizable=no,location=no,width=624,height=540,scrollbars=yes";
         }
         //���ɴ��ݸ��������ڵĲ����ַ���
         String strParam = "";
         strParam = "strFormName=" + strFormName;
         strParam += "&strMagnifierName=" + URLEncoder.encode(strMagnifierName);
         strParam += "&nIndex=" + nIndex;

         if (!isSQL(strSQL))
         {
             strParam += "&strSQL= select * from ( '+" + strSQL + "+' ) where 1=1 '+get" + strMainNames[0] + "(" + strMainNames[0] + ".value)+'";
         }
         else
         {
             strParam += "&strSQL= select * from ( " + strSQL + " ) where 1=1 '+get" + strMainNames[0] + "(" + strMainNames[0] + ".value)+'";
         }

         if (strNextControls != null && !strNextControls.equals(""))
         {
             strParam += "&strNextControls=" + strNextControls;
         }

         for (int i = 0; i < strMainNames.length; i++)
         {
             strParam += "&strMainNames=" + strMainNames[i];
             strParam += "&strMainFields=" + strMainFields[i];
         }

         if (strReturnNames != null)
         {
             boolean bValue = false;
             if (strReturnValues != null && strReturnValues.length == strReturnNames.length)
             {
                 bValue = true;
             }
             for (int i = 0; i < strReturnNames.length; i++)
             {
                 //�����������
                 strParam += "&strReturnNames=" + strReturnNames[i];
                 strParam += "&strReturnFields=" + strReturnFields[i];
                 if (bValue)
                 {
                     out.println("<input type=\"hidden\" name=\"" + strReturnNames[i] + "\" value=\"" + strReturnValues[i] + "\">");
                 }
                 else
                 {
                     out.println("<input type=\"hidden\" name=\"" + strReturnNames[i] + "\">");
                 }
             }
         }

         for (int i = 0; i < strDisplayNames.length; i++)
         {
             //�����������
             strParam += "&strDisplayNames=" + URLEncoder.encode(strDisplayNames[i]);
             strParam += "&strDisplayFields=" + strDisplayFields[i];
         }
         //Log.print("strParam = " + strParam);
         //���ɲ�ѯ��ť���¼��ַ���
         String sOnKeydown =
             "if("
                 + strFormName
                 + "."
                 + strMainNames[0]
                 + ".disabled == false) {gnIsSelectCtrl=1;window.open('"
                 + Env.URL_PREFIX
                 + "/iTreasury-ebank/magnifier/ShowMagnifierZoom.jsp?"
                 + strParam
                 + "', 'SelectAnything', '"
                 + sFeatures
                 + "', false);}";
         //
         String sOnKeyUp = "";
         if (strReturnNames != null)
         {
             for (int i = 0; i < strReturnNames.length; i++)
             {
                 sOnKeyUp += strReturnNames[i] + ".value = -1; ";
             }
         }

         int iPos = -1;
         //��ʾ�ؼ�
         if (iPos == -1)
         {
             out.println(
                 "<td "
                     + strFirstTD
                     + " >"
                     + strTitle
                     + "��&nbsp;"
                     + "<a href=#><img name=\""
                     + strButtonName
                     + "\" src='/webob/graphics/zoom.gif' border=0 onclick=\""
                     + sOnKeydown
                     + "\"></a></td>");
         }
         else
         {
             out.println("<td " + strFirstTD + " >" + strTitle + ":&nbsp;" + "<img name=\"" + strButtonName + "\" style=\"cursor: hand;\" src='/webloan/image/icon.gif' border=0 ></td>");
         }

             out.println(
                 "<td "
                     + strSecondTD
                     + " ><textArea cols='70' name=\""
                     + strMainNames[0]
                     + "\" onpropertychange=\"if(this.value.length>250) this.value=this.value.substr(0,250)\" "
                     + strMainProperty
                     + " onKeyDown=\"if(event.keyCode==13) "
                     + sOnKeydown
                     + "\" onKeyUp=\""
                     + sOnKeyUp
                     + "\">"
                     + strReturnInitValues
                     +"</textArea></td>");

         out.println("<script language=\"JavaScript\">");
         out.println("function get" + strMainNames[0] + "(str)");
         out.println("{");
         out.println("   var sql = \"\"; ");
         out.println("   if (str != null && str != \"\") ");
         out.println("   {");
         if (strMatchValue.equals("0"))
         {
             out.println(" sql = \"\" ");    
         }
         else
         {
             out.println(" sql +=  \" and " + strMatchValue + " like '" + URLEncoder.encode("%") + "\"+str+\"" + URLEncoder.encode("%") + "'\";    ");
         }
         out.println("   }");
         out.println("    ");
         out.println("   return sql;    ");
         out.println("}");
         out.println("</SCRIPT> ");
     }
     catch (Exception exp)
     {
         throw exp;
     }
 }
 
 
 /**
	 * ������������˺ŷŴ�,�տ�˺ţ��ڲ�ת�ˣ�
	 * @author yanliu
	 * @param out
	 * @param lUserID �û�ID
	 * @param lClientID �ͻ�ID
	 * @param lInstructionID ��ǰ����ID
	 * @param lCurrencyID ����ID
	 * @param sBankAccountCode �˺ţ���ʼֵ��
	 * @param strFormName ��ҳ������
	 * @param strCtrlName �Ŵ����ؼ�����
	 * @param strTitle �Ŵ�����)
	 * @param strClientNo �ͻ����(��ʶֵ)
	 * @param strFirstTD ��һ��TD������
	 * @param strSecondTD �ڶ���TD������
	 * @param sNextControlsClient ��һ������������ý���Ŀؼ�
	 * @param strRtnClientNameCtrl ����ֵ���ͻ����ƣ���Ӧ�Ŀؼ���
	 */
 public static void createPayerBankAccountNoCtrl(
			JspWriter out,
			long lCurrencyID,
			long lOfficeID,
			long lClientID,
			String strRelativeForm1,
			String strRelativeForm2,
			String strRelativeForm3,
			String strRelativeForm4,//�������
			String strFormName,
			String sBankAccountCode,
			String strCtrlName,
			String strTitle,
			String strFirstTD,
			String strSecondTD)
		{
			String strMagnifierName = URLEncoder.encode("��������˺�");
			String strMainProperty = " maxlength='40' value='" + sBankAccountCode + "'";
			String strPrefix = "";
			String[] strMainNames = { strCtrlName + "Ctrl", strRelativeForm1, strRelativeForm2, strRelativeForm3,strRelativeForm4,"bankname"};
			String[] strMainFields = { "accountno", "id", "currentbalance", "","name","bankname"};
			String[] strMainName = { strCtrlName + "Ctrl", strRelativeForm1 }; //�տ
			String[] strMainField = { "accountno", "id" }; //�տ
			String[] strReturnNames = { "sName" };
			String[] strReturnFields = { "name" };
			String[] strReturnValues = { "" };
			String[] strReturnName = { "sPayeeBankAccountNOInternal" }; //�տ
			String[] strReturnField = { "sname" }; //�տ
			String[] strReturnValue = { "" }; //�տ
			String[] strDisplayNames = { URLEncoder.encode("�����˺�"), URLEncoder.encode("�����˻�����"), URLEncoder.encode("��������")};
			String[] strDisplayFields = { "accountno", "name", "bankname" };
			int nIndex = 0;
			String strSQL = "";
				strSQL = "getBankAccountforbankpay(" + strCtrlName + "Ctrl.value,"  +lOfficeID+","+ lClientID + "," + lCurrencyID +")";
			 
			String[] strNextControls = { "sPayeeAcctNoZoomCtrl" };
			try
			{
				if (!strRelativeForm2.equals("") && !strRelativeForm3.equals(""))
				{
					showBankPayerAcctZoomCtrl(
						out,
						strMagnifierName,
						strFormName,
						strPrefix,
						strMainNames,
						strMainFields,
						strReturnNames,
						strReturnFields,
						strReturnValues,
						strDisplayNames,
						strDisplayFields,
						nIndex,
						strMainProperty,
						strSQL,
						strNextControls,
						strTitle,
						strFirstTD,
						strSecondTD,
						false,
						false);
				}

			}
			catch (Exception e)
			{
			}
		}
	public static void createPayerBankAccount(
		JspWriter out,
		long lUserID,
		long lCurrencyID,
		long lOfficeID,
		long lClientID,
		String strRelativeForm1,
		String strRelativeForm2,
		String strRelativeForm3,
		String strRelativeForm4,//�������
		String strFormName,
		String sBankAccountCode,
		String strCtrlName,
		String strTitle,
		String strFirstTD,
		String strSecondTD)
	{
		String strMagnifierName = URLEncoder.encode("��������˺�");
		String strMainProperty = " maxlength='40' value='" + sBankAccountCode + "'";
		String strPrefix = "";
		String[] strMainNames = { strCtrlName + "Ctrl", strRelativeForm1, strRelativeForm2, strRelativeForm3,strRelativeForm4,"bankname"};
		String[] strMainFields = { "accountno", "id", "currentbalance", "","name","bankname"};
		//String[] strMainName = { strCtrlName + "Ctrl", strRelativeForm1 }; //�տ
		//String[] strMainField = { "accountno", "id" }; //�տ
		String[] strReturnNames = { "sName" };
		String[] strReturnFields = { "name" };
		String[] strReturnValues = { "" };
		//String[] strReturnName = { "sPayeeBankAccountNOInternal" }; //�տ
		//String[] strReturnField = { "sname" }; //�տ
		//String[] strReturnValue = { "" }; //�տ
		String[] strDisplayNames = { URLEncoder.encode("�����˺�"), URLEncoder.encode("�����˻�����"), URLEncoder.encode("��������")};
		String[] strDisplayFields = { "accountno", "name", "bankname" };
		int nIndex = 0;
		String strSQL = "";
			strSQL = "getBankAccount(" + strCtrlName + "Ctrl.value,"  +lOfficeID+","+ lClientID + "," + lCurrencyID +","+lUserID +")";
		 
		String[] strNextControls = { "sPayeeAcctNoZoomCtrl" };
		try
		{
			if (!strRelativeForm2.equals("") && !strRelativeForm3.equals(""))
			{
				showBankPayerAcctZoomCtrl(
					out,
					strMagnifierName,
					strFormName,
					strPrefix,
					strMainNames,
					strMainFields,
					strReturnNames,
					strReturnFields,
					strReturnValues,
					strDisplayNames,
					strDisplayFields,
					nIndex,
					strMainProperty,
					strSQL,
					strNextControls,
					strTitle,
					strFirstTD,
					strSecondTD,
					false,
					false);
			}

		}
		catch (Exception e)
		{
		}
	}
	
	
	
	/**
	 * ��ʾ����˺ţ��������Ŵ�
	 * @author yanliu
	 * @param out
	 * @param strMagnifierName �Ŵ󾵵�����
	 * @param strFormName ��ҳ�������
	 * @param strPrefix �ؼ�����ǰ׺
	 * @param strMainNames �Ŵ󾵻�����λֵ�б�
	 * @param strMainFields �Ŵ󾵻�����λ��Ӧ�ı���ֶ�
	 * @param strReturnNames �Ŵ󾵷���ֵ�б�(����ֵ)
	 * @param strReturnFields �Ŵ󾵷���ֵ(����ֵ)��Ӧ�ı���ֶ��б�
	 * @param strReturnValues �Ŵ󾵷���ֵ(����ֵ)��Ӧ�ĳ�ʼֵ
	 * @param strDisplayNames �Ŵ�С������ʾ����λ����
	 * @param strDisplayFields �Ŵ�С������ʾ��λ��Ӧ�ı���ֶ�
	 * @param nIndex ȷ��ѡ����,��0��ʼ,���С��0,��Ĭ��������λ����ѡ��,������ڻ����strDisplayNames.length,��û��ѡ����
	 * @param strMainProperty �Ŵ���λ����
	 * @param strSQL �Ŵ󾵲�ѯSQL���
	 * @param strNextControls ������һ������
	 * @param strTitle ��λ����
	 * @param strFirstTD ��һ��TD������
	 * @param strSecondTD �ڶ���TD������ 
	 * @param blnIsOptional �Ƿ��ǿ�ѡ�Ŀǰ����ժҪ���ֽ�����Ŵ����ã�
	 * @param blnIsRateCtrl �Ƿ������ʿؼ�
	 * @throws Exception
	 */
	public static void showBankPayerAcctZoomCtrl(
		JspWriter out,
		String strMagnifierName,
		String strFormName,
		String strPrefix,
		String[] strMainNames,
		String[] strMainFields,
		String[] strReturnNames,
		String[] strReturnFields,
		String[] strReturnValues,
		String[] strDisplayNames,
		String[] strDisplayFields,
		int nIndex,
		String strMainProperty,
		String strSQL,
		String[] strNextControls,
		String strTitle,
		String strFirstTD,
		String strSecondTD,
		boolean blnIsOptional,
		boolean blnIsRateCtrl)
		throws Exception
	{
		String strButtonName = "button";
		try
		{
			//���Ŵ󾵲���
			checkValue(strMainNames, strMainFields, true);
			checkValue(strReturnNames, strReturnFields, strReturnValues, false);
			checkValue(strDisplayNames, strDisplayFields, true);
			if (strMagnifierName == null || strFormName == null || strFormName.equals("") || strSQL == null || strSQL.equals(""))
			{
				throw new IException(ZOOMERRORMSG);
			}
			if (strNextControls == null)
			{
				throw new IException(ZOOMERRORMSG);
			}
			if (strFirstTD == null)
			{
				strFirstTD = "";
			}
			if (strSecondTD == null)
			{
				strSecondTD = "";
			}
			//������
			//����ǰ׺
			if (strPrefix != null && !strPrefix.trim().equals(""))
			{
				for (int i = 0; i < strMainNames.length; i++)
				{
					strMainNames[i] = strPrefix + strMainNames[i];
				}
				for (int i = 0; i < strReturnNames.length; i++)
				{
					strReturnNames[i] = strPrefix + strReturnNames[i];
				}
			}
			//�������ڵ�����
			String sFeatures = null;
			if (strDisplayNames.length < 3)
			{
				sFeatures = "toolbar=no,menubar=no,resizable=no,location=no,width=520,height=450,scrollbars=yes";
			}
			else
			{
				sFeatures = "toolbar=no,menubar=no,resizable=no,location=no,width=624,height=540,scrollbars=yes";
			}
			//���ɴ��ݸ��������ڵĲ����ַ���
			String strParam = "";
			strParam = "strFormName=" + strFormName;
			strParam += "&strMagnifierName=" + strMagnifierName;
			strParam += "&nIndex=" + nIndex;
			if (!isSQL(strSQL))
			{
				strParam += "&strSQL='+" + strSQL + "+'";
			}
			else
			{
				strParam += "&strSQL=" + getSQL(strSQL);
			}
			for (int i = 0; i < strNextControls.length; i++)
			{
				strParam += "&strNextControls=" + strNextControls[i];
			}
			for (int i = 0; i < strMainNames.length; i++)
			{
				strParam += "&strMainNames=" + strMainNames[i];
				strParam += "&strMainFields=" + strMainFields[i];
			}
			if (strReturnNames != null)
			{
				boolean bValue = false;
				if (strReturnValues != null && strReturnValues.length == strReturnNames.length)
				{
					bValue = true;
				}
				for (int i = 0; i < strReturnNames.length; i++)
				{
					//�����������
					strParam += "&strReturnNames=" + strReturnNames[i];
					strParam += "&strReturnFields=" + strReturnFields[i];
					if (bValue)
					{
						out.println("<input type=\"hidden\" name=\"" + strReturnNames[i] + "\" value=\"" + strReturnValues[i] + "\">");
					}
					else
					{
						out.println("<input type=\"hidden\" name=\"" + strReturnNames[i] + "\">");
					}
				}
			}
			for (int i = 0; i < strDisplayNames.length; i++)
			{
				//�����������
				strParam += "&strDisplayNames=" + strDisplayNames[i];
				strParam += "&strDisplayFields=" + strDisplayFields[i];
				
			}
			//Log.print("strParam = " + strParam);
			//���ɲ�ѯ��ť���¼��ַ���
			String sOnKeydown =
				"if("
					+ strFormName
					+ "."
					+ strMainNames[0]
					+ ".disabled == false) {gnIsSelectCtrl=1;window.open('/NASApp/iTreasury-ebank/magnifier/ShowBankPayZoom.jsp?"
					+ strParam
					+ "', 'SelectAnything', '"
					+ sFeatures
					+ "', false);}";
			//
			String sOnKeyUp = "";
			if (strReturnNames != null)
			{
				for (int i = 0; i < strReturnNames.length; i++)
				{
					sOnKeyUp += strReturnNames[i] + ".value = -1; ";
				}
			}
			//int iPos = strMainProperty.toLowerCase().indexOf("disabled");
			int iPos = -1;
			//��ʾ�ؼ�
			if (iPos == -1)
			{
				out.println(
					"<td "
						+ strFirstTD
						+ ">"
						+ strTitle
						+ "��&nbsp;"
						+ "<a href=#><img name=\""
						+ strButtonName
						+ "\" src='/webob/graphics/zoom.gif' border=0 onclick=\""
						+ sOnKeydown
						+ "\"></a></td>");
				//image
			}
			else
			{
				out.println("<td " + strFirstTD + ">" + strTitle + ":&nbsp;" + "<a href=#><img name=\"" + strButtonName + "\" src='/webob/graphics/zoom.gif' border=0 ></a></td>");
				//image
			}
			//blnIsOptional,�Ƿ��ѡ�����ժҪ���ֽ�����Ŵ���Ч��
			if (blnIsOptional == true)
			{
				if (strNextControls != null && strNextControls.length > 0)
				{
					strMainProperty = " onfocus=\"nextfield='" + strNextControls[0] + "'\" " + strMainProperty;
				}
				if (blnIsRateCtrl == true)
				{
					out.println("<td" + strSecondTD + "><input type=\"text\" name=\"" + strMainNames[0] + "\" class=\"box\" " + strMainProperty + ">%</td>");
				}
				else
				{
					out.println("<td" + strSecondTD + "><input type=\"text\" name=\"" + strMainNames[0] + "\" class=\"box\" " + strMainProperty + "></td>");
				}
			}
			else
			{
				if (blnIsRateCtrl == true)
				{
					out.println(
						"<td"
							+ strSecondTD
							+ "><input type=\"text\" name=\""
							+ strMainNames[0]
							+ "\" class=\"box\" "
							+ strMainProperty
							+ " onKeyDown=\"if(event.keyCode==13) "
							+ sOnKeydown
							+ "\" onKeyUp=\""
							+ sOnKeyUp
							+ "\">%</td>");
				}
				else
				{
					out.println(
						"<td"
							+ strSecondTD
							+ "><input type=\"text\" name=\""
							+ strMainNames[0]
							+ "\" class=\"box\" "
							+ strMainProperty
							+ " onKeyDown=\"if(event.keyCode==13) "
							+ sOnKeydown
							+ "\" onKeyUp=\""
							+ sOnKeyUp
							+ "\"></td>");
				}
			}
		}
		catch (Exception exp)
		{
			throw exp;
		}
	}
	
	/**
	 * ��������и���˺ŷŴ󾵵����ⷽ�� For CSSC
	 * @author 
	 * @param strMainFields
	 * @param strReturnFields
	 * @param strDisplayFields
	 * @param strSQL
	 * @return
	 * @throws Exception
	 */
	public static Vector getOBBankPaySelectList(String[] strMainFields, String[] strReturnFields, String[] strDisplayFields, String strSQL) throws Exception
	{
		CommonSelectInfo info = null;
		PreparedStatement ps = null, ps1 = null;
		ResultSet rs = null, rs1 = null;
		Connection conn = null;
		Vector vResult = new Vector();
		
		System.out.println("strSQL------------------------>" +   strSQL);
		try
		{
			conn = Database.getConnection();
			ps = conn.prepareStatement(strSQL);
			rs = ps.executeQuery();
			while (rs.next())
			{
				info = new CommonSelectInfo();
				//��ȡ�Ŵ󾵲�ѯ�����ֶ�
				Object[] oMainCols = new Object[strMainFields.length];
				long lTempMain = rs.getLong(strMainFields[1]);
				oMainCols[0] = rs.getObject(strMainFields[0]);
				oMainCols[1] = rs.getObject(strMainFields[1]);
				oMainCols[2] = rs.getObject(strMainFields[2]);
				//oMainCols[3] = OBOperation.GetUseableBalance(Long.parseLong(oMainCols[1].toString()),1,DataFormat.getStringDateTime().substring(0,10),-1);//"999999";//�ӱ𴦻�)��
				//String useableAmount=oMainCols[3].toString();
				//oMainCols[3]=DataFormat.formatDisabledAmount(Double.parseDouble(useableAmount),2);
				oMainCols[4] = rs.getObject(strMainFields[4]);
				oMainCols[5] = rs.getObject(strMainFields[5]);
				Log.print("-------1:" + oMainCols[0]);
				Log.print("-------2:" + oMainCols[1]);
				Log.print("-------3:" + oMainCols[2]);
				Log.print("-------4:" + oMainCols[3]);
				//��Ҫ������ҳ����ֶ�
				Object[] oReturnCols = null;
				if (strReturnFields != null)
				{
					oReturnCols = new Object[strReturnFields.length];
					for (int i = 0; i < strReturnFields.length; i++)
					{
						oReturnCols[i] = rs.getObject(strReturnFields[i]);
						//<PK>
					}
				}
				//��Ҫ�ڷŴ�����ʾ���ֶ�
				Object[] oDisplayCols = new Object[strDisplayFields.length];
				for (int i = 0; i < strDisplayFields.length; i++)
				{
					//�ж��Ƿ���Ҫ�ӳ�����Constant��ȡ�����ݡ�
					String strTempDisplay = rs.getString(strDisplayFields[i]);
					if (strTempDisplay != null && strTempDisplay.indexOf("FromConstant") >= 0)
					{
						String strSubString1 = strTempDisplay.substring(strTempDisplay.indexOf("_") + 1);
						String strTempField = strSubString1.substring(strSubString1.indexOf("_") + 1);
						int nType = (int) Long.parseLong(strSubString1.substring(0, strSubString1.indexOf("_")));
						oDisplayCols[i] = getValueFromConstant(nType, rs.getLong(strTempField));
					}
					else
					{
						oDisplayCols[i] = rs.getObject(strDisplayFields[i]);
						//<PK>
						if (oDisplayCols[i] == null)
						{
							oDisplayCols[i] = "";
						}
					}
				}
				info.setMainCols(oMainCols);
				if (strReturnFields != null)
				{
					info.setReturnCols(oReturnCols);
				}
				info.setDisplayCols(oDisplayCols);
				vResult.add(info);
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw new Exception("�������ݿ����");
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (conn != null)
				{
					conn.close();
					conn = null;
				}
			}
			catch (Exception _ex)
			{
				System.out.println("�ر����ݿ�����ʱ�������ݿ����");
			}
		}
		return vResult.size() > 0 ? vResult : null;
	}
	
	
	/**
	 * add by liangge
	 * @param out
	 * @param lOfficeID
	 * @param lCurrencyID
	 * @param strFormName
	 * @param strCtrlName
	 * @param strTitle
	 * @param lUserID
	 * @param lAccountID
	 * @param lSubAccountID
	 * @param strDepositNo
	 * @param lDepositTypeID
	 * @param lTypeID
	 * @param lClientID
	 * @param strAccountIDCtrl
	 * @param strFirstTD
	 * @param strSecondTD
	 * @param strNextControls
	 * @param strRtnEndDateCtrl
	 * @param strRtnOpenDateCtrl
	 * @param strRtnCapitalCtrl
	 * @param strRtnBalanceCtrl
	 * @param strRtnRateCtrl
	 * @param strRtnIntervalCtrl
	 * @param strRtnStartDateCtrl
	 * @param strReturnNames
	 * @param strReturnFields
	 * @param strReturnValues
	 */
	public static void createFixedDepositNoCtrlZJ(
			JspWriter out,
			long lOfficeID,
			long lCurrencyID,
			String strFormName,
			String strCtrlName,
			String strTitle,
			long lUserID,
			long lAccountID,
			long lSubAccountID,
			String strDepositNo,
			long lDepositTypeID,
			long lTypeID,
			long lClientID,
			String strAccountIDCtrl,
			String strFirstTD,
			String strSecondTD,
			String[] strNextControls,
			String strRtnEndDateCtrl,
			String strRtnOpenDateCtrl,
			String strRtnCapitalCtrl,
			String strRtnBalanceCtrl,
			String strRtnRateCtrl,
			String strRtnIntervalCtrl,
			String strRtnStartDateCtrl,
			String[] strReturnNames,
			String[] strReturnFields,
			String[] strReturnValues
			)
	{
		String strMagnifierName = "���ݺ�";
		if (lDepositTypeID == 1)
		{
			strMagnifierName = "���ڴ��ݺ�";
		}
		else if (lDepositTypeID == 2)
		{
			strMagnifierName = "֪ͨ���ݺ�";
		}
		else if(lDepositTypeID == 6)
		{
			strMagnifierName = "��֤����ݺ�";
		}
		
		String strMainProperty = " maxlength='30' value='" + strDepositNo + "'";
		String strPrefix = "";
		if (strRtnEndDateCtrl == null || strRtnEndDateCtrl.equals(""))
		{
			strRtnEndDateCtrl = "ItIsNotControl";
		}
		if (strRtnOpenDateCtrl == null || strRtnOpenDateCtrl.equals(""))
		{
			strRtnOpenDateCtrl = "ItIsNotControl";
		}
		if (strRtnCapitalCtrl == null || strRtnCapitalCtrl.equals(""))
		{
			strRtnCapitalCtrl = "ItIsNotControl";
		}
		if (strRtnBalanceCtrl == null || strRtnBalanceCtrl.equals(""))
		{
			strRtnBalanceCtrl = "ItIsNotControl";
		}
		if (strRtnRateCtrl == null || strRtnRateCtrl.equals(""))
		{
			strRtnRateCtrl = "ItIsNotControl";
		}
		if (strRtnIntervalCtrl == null || strRtnIntervalCtrl.equals(""))
		{
			strRtnIntervalCtrl = "ItIsNotControl";
		}
		if (strRtnStartDateCtrl == null || strRtnStartDateCtrl.equals(""))
		{
			strRtnStartDateCtrl = "ItIsNotControl";
		}
		long lFromClient = 0;
		if (lClientID > 0)
		{
			lFromClient = 1;
		} 
		/*String[] strMainNames =
		{ strCtrlName + "Ctrl", strRtnEndDateCtrl, strRtnOpenDateCtrl, strRtnCapitalCtrl, strRtnBalanceCtrl, strRtnRateCtrl, strRtnIntervalCtrl, strRtnStartDateCtrl };
	    String[] strMainFields = { "DepositNo", "EndDate", "OpenDate", "Capital", "Balance", "Rate", "Interval", "StartDate" };*/
		//String[] strMainNames =
		//{ strCtrlName + "Ctrl", strRtnEndDateCtrl, strRtnOpenDateCtrl, strRtnCapitalCtrl, strRtnBalanceCtrl, strRtnRateCtrl, strRtnIntervalCtrl, strRtnStartDateCtrl };
	    //String[] strMainFields = { "DepositNo", "EndDate", "OpenDate", "Capital", "Balance", "Rate", "Interval", "StartDate" };
		String[] strMainNames =
			{ strCtrlName + "Ctrl","lClientIDCtrl","strClientName","lClientID","lAccountID","lAccountIDClientID","dPayerStartDate","dPayerEndDate","nFixedDepositTime","dPayerCurrBalance","dPayerCurrInterest","dPayerCurrStartDate","dAmount"};
		String[] strMainFields = { "DepositNo","ClientNo","ClientName","ClientID","AccountID","ClientID","OpenDate","EndDate","Interval","Balance","Rate","EndDate","Balance"};
		//String[] strReturnNames = { strCtrlName, strCtrlName + "AccountID","lClientID","lClientIDFromClient","lAccountID","lAccountIDClientID","lAccountIDGroupID"};
		//String[] strReturnFields = { "SubAccountID", "AccountID","ClientID","FromClient","AccountID","ClientID","accountgroupid" };
//		String[] strReturnNames = { strCtrlName, strCtrlName + "AccountID" };
//		String[] strReturnFields = { "SubAccountID", "AccountID" };
//		String[] strReturnValues = { String.valueOf(lSubAccountID), String.valueOf(lAccountID)};
		//String[] strReturnValues = { String.valueOf(lSubAccountID), String.valueOf(lAccountID),String.valueOf(lClientID), String.valueOf(lFromClient),String.valueOf(lAccountID), String.valueOf(lClientID), "-1"};
		String[] strDisplayNames = { "���ݺ�"};
		String[] strDisplayFields = { "DepositNo" };
		//֧ȡ������ת��ʱ����ʾ��ͬ
		if (lTypeID == 21 || lTypeID == 22 || lTypeID == 3 || lTypeID == 0)
		{
			if (lDepositTypeID == 1)
			{
				//������ʾ���ݺš���������
				strDisplayNames = new String[] { "���ݺ�", "���", "������"};
				strDisplayFields = new String[] { "DepositNo", "Balance", "EndDate" };
			}else if(lDepositTypeID == 6){
//				��֤����ʾ���ݺš���������������
				strDisplayNames = new String[] { "���ݺ�", "�������", "������"};
				strDisplayFields = new String[] { "DepositNo", "Balance", "OpenDate" };
			}
			else
			{
				//������ʾ���ݺš�����������
				strDisplayNames = new String[] { "���ݺ�", "���"};
				strDisplayFields = new String[] { "DepositNo", "Balance"};
			}
		}
		int nIndex = 0;
		String strSQL = "";
		if (strAccountIDCtrl != null && !strAccountIDCtrl.trim().equals(""))
		{
			strSQL =
				"getFixedDepositNoSQLOB("
					+ lOfficeID
					+ ","
					+ lCurrencyID
					+ ","
					+ lDepositTypeID
					+ ","
					+ strAccountIDCtrl
					+ ".value,"
					+ lUserID
					+ ","
					+ strCtrlName
					+ "Ctrl.value,"
					+ lTypeID
					+ ",'"
					+ Env.getSystemDateString(lOfficeID, lCurrencyID)
					+ "')";
		}
		else
		{
			strSQL =
				"getFixedDepositNoSQLOB("
					+ lOfficeID
					+ ","
					+ lCurrencyID
					+ ","
					+ lDepositTypeID
					+ ",-1,"
					+ lUserID
					+ ","
					+ strCtrlName
					+ "Ctrl.value,"
					+ lTypeID
					+ ",'"
					+ Env.getSystemDateString(lOfficeID, lCurrencyID)
					+ "')";
		}
		try
		{
			boolean blnIsOptional = false;
			boolean blnIsRateCtrl = false;
			OBMagnifier.showFormZoomCtrl(
				out,
				strMagnifierName,
				strFormName,
				strPrefix,		
				strMainNames,
				strMainFields,
				strReturnNames,
				strReturnFields,
				strReturnValues,
				strDisplayNames,
				strDisplayFields,
				nIndex,
				strMainProperty,
				strSQL,
				strNextControls,
				strTitle,
				strFirstTD,
				strSecondTD,
				blnIsOptional,
				blnIsRateCtrl);
		}
	
		catch (Exception e)
		{
			Log.print("���ڴ��ݺŷŴ�[" + strCtrlName + "]�쳣��" + e.toString());
		}
	
	}
	
	/**
	 * ��ʾ��ͨ�Ŵ󾵣�ͨ�����ύ
	 * 
	 * @param out
	 * @param strMagnifierName �Ŵ󾵵�����
	 * @param strFormName ��ҳ�������
	 * @param strPrefix �ؼ�����ǰ׺
	 * @param strMainNames �Ŵ󾵻�����λֵ�б�
	 * @param strMainFields �Ŵ󾵻�����λ��Ӧ�ı���ֶ�
	 * @param strReturnNames �Ŵ󾵷���ֵ�б�(����ֵ)
	 * @param strReturnFields �Ŵ󾵷���ֵ(����ֵ)��Ӧ�ı���ֶ��б�
	 * @param strReturnValues �Ŵ󾵷���ֵ(����ֵ)��Ӧ�ĳ�ʼֵ
	 * @param strDisplayNames �Ŵ�С������ʾ����λ����
	 * @param strDisplayFields �Ŵ�С������ʾ��λ��Ӧ�ı���ֶ�
	 * @param nIndex ȷ��ѡ����,��0��ʼ,���С��0,��Ĭ��������λ����ѡ��,������ڻ����strDisplayNames.length,��û��ѡ����
	 * @param strMainProperty �Ŵ���λ����
	 * @param strSQL �Ŵ󾵲�ѯSQL���
	 * @param strNextControls ������һ������
	 * @param strTitle ��λ����
	 * @param strFirstTD ��һ��TD������
	 * @param strSecondTD �ڶ���TD������ 
	 * @param blnIsOptional �Ƿ��ǿ�ѡ�Ŀǰ����ժҪ���ֽ�����Ŵ����ã�
	 * @param blnIsRateCtrl �Ƿ������ʿؼ�
	 * @throws Exception
	 */
	public static void showFormZoomCtrl(
		JspWriter out,
		String strMagnifierName,
		String strFormName,
		String strPrefix,
		String[] strMainNames,
		String[] strMainFields,
		String[] strReturnNames,
		String[] strReturnFields,
		String[] strReturnValues,
		String[] strDisplayNames,
		String[] strDisplayFields,
		int nIndex,
		String strMainProperty,
		String strSQL,
		String[] strNextControls,
		String strTitle,
		String strFirstTD,
		String strSecondTD,
		boolean blnIsOptional,
		boolean blnIsRateCtrl) throws Exception
	{
		String strButtonName = "button";
		try
		{
			//���Ŵ󾵲���
			checkValue(strMainNames, strMainFields, true);
			checkValue(strReturnNames, strReturnFields, strReturnValues, false);
			checkValue(strDisplayNames, strDisplayFields, true);
			if (strMagnifierName == null || strFormName == null || strFormName.equals("") || strSQL == null || strSQL.equals(""))
			{
				throw new IException(ZOOMERRORMSG);
			}
			if (strNextControls == null)
			{
				throw new IException(ZOOMERRORMSG);
			}
			if (strFirstTD == null)
			{
				strFirstTD = "";
			}
			if (strSecondTD == null)
			{
				strSecondTD = "";
			}
			//������
			//����ǰ׺
			if (strPrefix != null && !strPrefix.trim().equals(""))
			{
				for (int i = 0; i < strMainNames.length; i++)
				{
					strMainNames[i] = strPrefix + strMainNames[i];
				}
				for (int i = 0; i < strReturnNames.length; i++)
				{
					strReturnNames[i] = strPrefix + strReturnNames[i];
				}
			}

			//���ɴ��ݸ��������ڵĲ����ַ���
			StringBuffer strHiddenName = new StringBuffer();
			StringBuffer strHiddenValue = new StringBuffer();

			strHiddenName.append("strFormName|:|:");
			strHiddenValue.append(strFormName + "|:|:");
			strHiddenName.append("strMagnifierName|:|:");
			strHiddenValue.append(strMagnifierName + "|:|:");
			strHiddenName.append("nIndex|:|:");
			strHiddenValue.append(nIndex + "|:|:");

			for (int i = 0; i < strNextControls.length; i++)
			{
				strHiddenName.append("strNextControls|:|:");
				strHiddenValue.append(strNextControls[i] + "|:|:");
			}
			for (int i = 0; i < strMainNames.length; i++)
			{
				strHiddenName.append("strMainNames|:|:");
				strHiddenValue.append(strMainNames[i] + "|:|:");
				strHiddenName.append("strMainFields|:|:");
				strHiddenValue.append(strMainFields[i] + "|:|:");
			}
			if (strReturnNames != null)
			{
				boolean bValue = false;
				if (strReturnValues != null && strReturnValues.length == strReturnNames.length)
				{
					bValue = true;
				}
				for (int i = 0; i < strReturnNames.length; i++)
				{
					//�����������
					strHiddenName.append("strReturnNames|:|:");
					strHiddenValue.append(strReturnNames[i] + "|:|:");
					strHiddenName.append("strReturnFields|:|:");
					strHiddenValue.append(strReturnFields[i] + "|:|:");
					if (bValue)
					{
						out.println("<input type=\"hidden\" name=\"" + strReturnNames[i] + "\" value=\"" + strReturnValues[i] + "\">");
					}
					else
					{
						out.println("<input type=\"hidden\" name=\"" + strReturnNames[i] + "\">");
					}
				}
			}
			for (int i = 0; i < strDisplayNames.length; i++)
			{
				//�����������
				strHiddenName.append("strDisplayNames|:|:");
				strHiddenValue.append(strDisplayNames[i] + "|:|:");
				strHiddenName.append("strDisplayFields|:|:");
				strHiddenValue.append(strDisplayFields[i] + "|:|:");
			}

			//���ɲ�ѯ��ť���¼��ַ���
			StringBuffer sOnKeydown = new StringBuffer();
			sOnKeydown.append("var strHiddenName = '" + strHiddenName.toString() + "strSQL'; ");
			sOnKeydown.append("var strHiddenValue = '" + strHiddenValue.toString() + "'; ");
			if (!isSQL(strSQL)){
				sOnKeydown.append("var strSQL = " + strSQL + "; ");
			}
			else{
				sOnKeydown.append("var strSQL = " + getSQL(strSQL) + "; ");
			}
			sOnKeydown.append("strHiddenValue = strHiddenValue + encodeURI(strSQL); ");
			
			sOnKeydown.append("if(" + strFormName + "." + strMainNames[0] + ".disabled == false) { ");
			sOnKeydown.append("  " + strFormName + "." + strMainNames[0] + ".focus(); ");
			sOnKeydown.append("  gnIsSelectCtrl = 1; ");
			sOnKeydown.append("  openMagnifier('/NASApp/iTreasury-ebank/magnifier/ShowFormMagnifierZoom.jsp', strHiddenName, strHiddenValue); ");
			sOnKeydown.append("} ");
			//
			String sOnKeyUp = "";
			
			if (strReturnNames != null)
			{
				
				for (int i = 0; i < strReturnNames.length; i++)
				{
					sOnKeyUp += strReturnNames[i] + ".value = -1; ";
				}
			}
			//int iPos = strMainProperty.toLowerCase().indexOf("disabled");
			int iPos = -1;
			//��ʾ�ؼ�
			if (iPos == -1)
			{
				out.println("<td width=130"+ strFirstTD+ ">"+ strTitle +"��&nbsp;" + "<a href=#><img name=\""+ strButtonName +"\" src='/webob/graphics/zoom.gif' border=0 onclick=\"" + sOnKeydown +"\"></a></td>");
				//image
			}
			else
			{
				out.println("<td" + strFirstTD + ">" + strTitle + "��&nbsp;" + "<a href=#><img name=\"" + strButtonName + "\" src='/webob/graphics/zoom.gif' border=0 ></a></td>");
				//image
			}
			//blnIsOptional,�Ƿ��ѡ�����ժҪ���ֽ�����Ŵ���Ч��
			if (blnIsOptional == true)
			{
				if (strNextControls != null && strNextControls.length > 0)
				{
					strMainProperty = " onfocus=\"nextfield='" + strNextControls[0] + "'\" " + strMainProperty;
				}
				if (blnIsRateCtrl == true)
				{
					out.println("<td" + strSecondTD + "><input type=\"text\" name=\"" + strMainNames[0] + "\" class=\"box\" " + strMainProperty + ">%</td>");
				}
				else
				{
					out.println("<td" + strSecondTD + "><input type=\"text\" name=\"" + strMainNames[0] + "\" class=\"box\" " + strMainProperty + "></td>");
				}
			}
			else
			{
				if (blnIsRateCtrl == true)
				{
					out.println("<td"+ strSecondTD+ "><input type=\"text\" name=\""+ strMainNames[0]+ "\" class=\"box\" "+ strMainProperty+ " onKeyDown=\"if(event.keyCode==13) {"+ sOnKeydown.toString()+ "} \" onKeyUp=\""+ sOnKeyUp+ "\">%</td>");
				}
				else
				{
					out.println("<td"+ strSecondTD+ "><input type=\"text\" name=\""+ strMainNames[0]+ "\" class=\"box\" "+ strMainProperty+ " onKeyDown=\"if(event.keyCode==13) {"+ sOnKeydown.toString()+ "} \" onKeyUp=\""+ sOnKeyUp+ "\"></td>");
				}
			}
		}
		catch (Exception exp)
		{
			throw exp;
		}
	}
	/**
	 * ���������;ժҪ�Ŵ�
	 * @param out
	 * @param lOfficeID ���´�ID
	 * @param lClientID �û�ID
	 * @param lCurrencyID ����ID
	 * @param strFormName ��������
	 * @param strCtrlName �Ŵ����ؼ�����
	 * @param strTitle �Ŵ�����
	 * @param lAbstractID ժҪID(��ʶֵ)
	 * @param strAbstractDesc ժҪ����(��ʶֵ)
	 * @param strFirstTD ��һ��TD������
	 * @param strSecondTD �ڶ���TD������
	 * @param maxLength �ı������������ַ���
	 * @param strNextControls ��һ������������ý���Ŀؼ�
	 */
	public static void createAbstractSettingCtrl(
		JspWriter out,
		long lOfficeID,
		long lClientID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lAbstractID,
		String strAbstractDesc,
		String strFirstTD,
		String strSecondTD,
		long maxLength,
		String[] strNextControls)
	{
		String strTITLE = "�����;";
		String strMainName = strCtrlName + "Ctrl";
		String strMagnifierName = URLEncoder.encode(strTITLE);
		String strMainProperty = " size='40' maxlength = '50' onpropertychange='checkStr("+maxLength+",\""+strMainName+"\")' onblur ='checkStrMessage("+maxLength+",\""+strMainName+"\")' ";

		if (strNextControls != null && strNextControls.length > 0)
		{
			strMainProperty = strMainProperty + " onfocus=\"nextfield='" + strNextControls[0] + "';\"";
		}
		String strPrefix = "";
		String[] strMainNames = { strCtrlName + "Ctrl", String.valueOf(maxLength) };
		String[] strMainFields = { "AbstractDesc" };
		String[] strReturnNames = { strCtrlName,"sCode" };
		String[] strReturnFields = { "AbstractID","AbstractCode" };
		String[] strReturnValues = { String.valueOf(lAbstractID),String.valueOf(lAbstractID)};
		String[] strDisplayNames = { URLEncoder.encode("ժҪ����"), URLEncoder.encode("ժҪ����")};
		String[] strDisplayFields = { "AbstractCode", "AbstractDesc" };
		int nIndex = 1;
		String strSQL = "getAbstractSettingSQL(" + lOfficeID + "," + lClientID + "," + strCtrlName + "Ctrl.value)";
		boolean blnIsOptional = true;
		try
		{
			showZoomCtrl(
				out,
				strMagnifierName,
				strFormName,
				strPrefix,
				strMainNames,
				strMainFields,
				strReturnNames,
				strReturnFields,
				strReturnValues,
				strDisplayNames,
				strDisplayFields,
				nIndex,
				strMainProperty,
				strAbstractDesc,
				strSQL,
				strNextControls,
				strTitle,
				strFirstTD,
				strSecondTD,
				blnIsOptional);
		}
		catch (Exception e)
		{
			Log.print("ժҪ�Ŵ�[" + strCtrlName + "]�쳣��" + e.toString());
		}
	}	


/**
 * ��ʾ��ͨ�ı���Ŵ�
 * @param out
 * @param strMagnifierName �Ŵ󾵵�����
 * @param strFormName ��ҳ�������
 * @param strPrefix �ؼ�����ǰ׺
 * @param strMainNames �Ŵ󾵻�����λֵ�б�
 * @param strMainFields �Ŵ󾵻�����λ��Ӧ�ı���ֶ�
 * @param strReturnNames �Ŵ󾵷���ֵ�б�(����ֵ)
 * @param strReturnFields �Ŵ󾵷���ֵ(����ֵ)��Ӧ�ı���ֶ��б�
 * @param strReturnValues �Ŵ󾵷���ֵ(����ֵ)��Ӧ�ĳ�ʼֵ
 * @param strDisplayNames �Ŵ�С������ʾ����λ����
 * @param strDisplayFields �Ŵ�С������ʾ��λ��Ӧ�ı���ֶ�
 * @param nIndex ȷ��ѡ����,��0��ʼ,���С��0,��Ĭ��������λ����ѡ��,������ڻ����strDisplayNames.length,��û��ѡ����
 * @param strMainProperty �Ŵ���λ����
 * @param strSQL �Ŵ󾵲�ѯSQL���
 * @param strNextControls ������һ������
 * @param strTitle ��λ����
 * @param strFirstTD ��һ��TD������
 * @param strSecondTD �ڶ���TD������ 
 * @param blnIsOptional �Ƿ��ǿ�ѡ�Ŀǰ����ժҪ���ֽ�����Ŵ����ã�
 * @throws Exception
 */
public static void showZoomCtrl(
	JspWriter out,
	String strMagnifierName,
	String strFormName,
	String strPrefix,
	String[] strMainNames,
	String[] strMainFields,
	String[] strReturnNames,
	String[] strReturnFields,
	String[] strReturnValues,
	String[] strDisplayNames,
	String[] strDisplayFields,
	int nIndex,
	String strMainProperty,
	String strAbstractDesc,
	String strSQL,
	String[] strNextControls,
	String strTitle,
	String strFirstTD,
	String strSecondTD,
	boolean blnIsOptional)
	throws Exception
{
	showZoomCtrl(
		out,
		strMagnifierName,
		strFormName,
		strPrefix,
		strMainNames,
		strMainFields,
		strReturnNames,
		strReturnFields,
		strReturnValues,
		strDisplayNames,
		strDisplayFields,
		nIndex,
		strMainProperty,
		strAbstractDesc,
		strSQL,
		strNextControls,
		strTitle,
		strFirstTD,
		strSecondTD,
		blnIsOptional,
		"branch");
}

/**
 * ��ʾ��ͨ�Ŵ�
 * @param out
 * @param strMagnifierName �Ŵ󾵵�����
 * @param strFormName ��ҳ�������
 * @param strPrefix �ؼ�����ǰ׺
 * @param strMainNames �Ŵ󾵻�����λֵ�б�
 * @param strMainFields �Ŵ󾵻�����λ��Ӧ�ı���ֶ�
 * @param strReturnNames �Ŵ󾵷���ֵ�б�(����ֵ)
 * @param strReturnFields �Ŵ󾵷���ֵ(����ֵ)��Ӧ�ı���ֶ��б�
 * @param strReturnValues �Ŵ󾵷���ֵ(����ֵ)��Ӧ�ĳ�ʼֵ
 * @param strDisplayNames �Ŵ�С������ʾ����λ����
 * @param strDisplayFields �Ŵ�С������ʾ��λ��Ӧ�ı���ֶ�
 * @param nIndex ȷ��ѡ����,��0��ʼ,���С��0,��Ĭ��������λ����ѡ��,������ڻ����strDisplayNames.length,��û��ѡ����
 * @param strMainProperty �Ŵ���λ����
 * @param strSQL �Ŵ󾵲�ѯSQL���
 * @param strNextControls ������һ������
 * @param strTitle ��λ����
 * @param strFirstTD ��һ��TD������
 * @param strSecondTD �ڶ���TD������ 
 * @param blnIsOptional �Ƿ��ǿ�ѡ�Ŀǰ����ժҪ���ֽ�����Ŵ����ã�
 * @param strCtrlType �ؼ����ͣ����⴦��
 *  rate ���ʿؼ�(���Ը�ʽ������)
 *  branch �����пؼ�(�ؼ�Ϊtextarea)
 * @throws Exception
 */
public static void showZoomCtrl(
	JspWriter out,
	String strMagnifierName,
	String strFormName,
	String strPrefix,
	String[] strMainNames,
	String[] strMainFields,
	String[] strReturnNames,
	String[] strReturnFields,
	String[] strReturnValues,
	String[] strDisplayNames,
	String[] strDisplayFields,
	int nIndex,
	String strMainProperty,
	String strAbstractDesc,
	String strSQL,
	String[] strNextControls,
	String strTitle,
	String strFirstTD,
	String strSecondTD,
	boolean blnIsOptional,
	String strCtrlType)
	throws Exception
{
	String strButtonName = "button";
	try
	{
		String maxLength = "";
		if(strCtrlType.equals("branch")){
			maxLength = strMainNames[1];
			String[] _strMainNames = {strMainNames[0]};
			strMainNames  = _strMainNames;
		}
		
		//���Ŵ󾵲���
		
		checkValue(strMainNames, strMainFields, true);
		checkValue(strReturnNames, strReturnFields, strReturnValues, false);
		checkValue(strDisplayNames, strDisplayFields, true);
		if (strMagnifierName == null || strFormName == null || strFormName.equals("") || strSQL == null || strSQL.equals(""))
		{
			throw new IException(ZOOMERRORMSG);
		}
		if (strNextControls == null)
		{
			throw new IException(ZOOMERRORMSG);
		}
		if (strFirstTD == null)
		{
			strFirstTD = "";
		}
		if (strSecondTD == null)
		{
			strSecondTD = "";
		}
		//������
		//����ǰ׺
		if (strPrefix != null && !strPrefix.trim().equals(""))
		{
			for (int i = 0; i < strMainNames.length; i++)
			{
				strMainNames[i] = strPrefix + strMainNames[i];
			}
			for (int i = 0; i < strReturnNames.length; i++)
			{
				strReturnNames[i] = strPrefix + strReturnNames[i];
			}
		}
		//�������ڵ�����
		String sFeatures = null;
		if (strDisplayNames.length < 3)
		{
			sFeatures = "toolbar=no,menubar=no,resizable=no,location=no,width=520,height=450,scrollbars=yes";
		}
		else
		{
			sFeatures = "toolbar=no,menubar=no,resizable=no,location=no,width=624,height=540,scrollbars=yes";
		}
		//���ɴ��ݸ��������ڵĲ����ַ���
		String strParam = "";
		strParam = "strFormName=" + strFormName;
		strParam += "&strMagnifierName=" + strMagnifierName;
		strParam += "&nIndex=" + nIndex;
		if (!isSQL(strSQL))
		{
			strParam += "&strSQL='+" + strSQL + "+'";
		}
		else
		{
			strParam += "&strSQL=" + getSQL(strSQL);
		}
		for (int i = 0; i < strNextControls.length; i++)
		{
			strParam += "&strNextControls=" + strNextControls[i];
		}
	
		for (int i = 0; i < strMainNames.length; i++)
		{
			strParam += "&strMainNames=" + strMainNames[i];
			strParam += "&strMainFields=" + strMainFields[i];
		}
		if (strReturnNames != null)
		{
			boolean bValue = false;
			if (strReturnValues != null && strReturnValues.length == strReturnNames.length)
			{
				bValue = true;
			}
			for (int i = 0; i < strReturnNames.length; i++)
			{
				//�����������
				strParam += "&strReturnNames=" + strReturnNames[i];
				strParam += "&strReturnFields=" + strReturnFields[i];
				if (bValue)
				{
					out.println("<input type=\"hidden\" name=\"" + strReturnNames[i] + "\" value=\"" + strReturnValues[i] + "\">");
				}
				else
				{
					out.println("<input type=\"hidden\" name=\"" + strReturnNames[i] + "\">");
				}
			}
		}
		
		for (int i = 0; i < strDisplayNames.length; i++)
		{
			//�����������
			strParam += "&strDisplayNames=" + strDisplayNames[i];
			strParam += "&strDisplayFields=" + strDisplayFields[i];
		}
		
		//���ɲ�ѯ��ť���¼��ַ���
		String sOnKeydown =
			"if(checkMagnifierInput("+ strFormName +"."+ strMainNames[0] +",'')){"
				+ "if("
				+ strFormName
				+ "."
				+ strMainNames[0]
				+ ".disabled == false) {gnIsSelectCtrl=1;window.open('"
				+ Env.URL_PREFIX
				+ "/iTreasury-ebank/magnifier/ShowMagnifierZoom.jsp?"
				+ strParam
				+ "', 'SelectAnything', '"
				+ sFeatures
				+ "', false);}}";
		//
		String sOnKeyUp = "";
		if (strReturnNames != null)
		{
			for (int i = 0; i < strReturnNames.length; i++)
			{
				sOnKeyUp += strReturnNames[i] + ".value = -1; ";
			}
		}
		//int iPos = strMainProperty.toLowerCase().indexOf("disabled");
		int iPos = -1;
		//��ʾ�ؼ�
		if (iPos == -1)
		{
			out.println(
				"<td width=150"
					+ strFirstTD
					+ ">"
					+ strTitle
					+ "��&nbsp;"
					+ "<a href=#><img name=\""
					+ strButtonName
					+ "\" src='/webob/graphics/zoom.gif' border=0 onclick=\""
					+ sOnKeydown
					+ "\"></a></td>");
			//image
		}
		else
		{
			out.println("<td " + strFirstTD + ">" + strTitle + "&nbsp;" + "<a href=#><img name=\"" + strButtonName + "\" src='/websett/image/icon.gif' border=0 ></a></td>");
			//image
		}
		//blnIsOptional,�Ƿ��ѡ�����ժҪ���ֽ�����Ŵ���Ч��
		if (blnIsOptional == true)
		{
			/*
			if (strNextControls != null && strNextControls.length > 0)
			{
				strMainProperty = " onfocus=\"nextfield='" + strNextControls[0] + "'\" "+strMainProperty;
			}
			*/
			if (strCtrlType.equals("rate"))
			{
				out.println("<td" + strSecondTD + "><input type=\"text\" name=\"" + strMainNames[0] + "\" class=\"tar\" " + strMainProperty + "> %</td>");
			}
			else
				if (strCtrlType.equals("branch"))
				{
					long initLen = 0;
		    		long charLen = 0;
					int initValueLen = strAbstractDesc.length();
					for(int i = 0; i < initValueLen; i++){
					     if(strAbstractDesc.charAt(i)>255){
					    	 charLen+=2;
					     }else{
					         charLen+=1;
					     }  
					}
					initLen = Long.parseLong(maxLength) - charLen;
					//sOnKeyUp +="halfTurnFull(this,'"+strMainNames[0]+"');";//�Զ�����Ǳ��ת����ȫ�Ǳ��
					out.println(
						"<td"
							+ strSecondTD
							+ "><textarea name=\""
							+ strMainNames[0]
							+ "\"  " 
							+" id=\""
							+ strMainNames[0]
							+ "\" "
							+"class=\"textarea\" bgcolor=\"#FF00\"  rows=2 cols=65 onKeyDown=\"if(event.keyCode==13) "
							+ sOnKeydown
							+ "\" onKeyUp=\""
							+ sOnKeyUp
							+ "\" "
							+ strMainProperty
							+ ">" 
							+ strAbstractDesc
							+ "</textarea></br>"
							+ "<span id='"+strMainNames[0]+"textAreaShow'>���<b>"+maxLength+"</b>���ַ���һ������2���ַ��������������� <b>"+initLen+"</b> ���ַ�</span></td>");
	    		
				}
				else
				{
					out.println(
						"<td" + strSecondTD 
							+ "><input type=\"text\" name=\"" 
							+ strMainNames[0] 
							+ "\" class=\"box\" " 
							+ strMainProperty 
							//+ "></td>");
							//modified by qhzhou 2008-02-27 ����ժҪ�Ŵ󾵻س�����������
							+ " onKeyDown=\"if(event.keyCode==13) {"
							+ sOnKeydown
							+ "}\" onKeyUp=\""
							+ sOnKeyUp
							+ "\"></td>");
							
				}
		}
		else
		{
			if (strCtrlType.equals("rate"))
			{
				out.println(
					"<td"
						+ strSecondTD
						+ "><input type=\"text\" name=\""
						+ strMainNames[0]
						+ "\" class=\"tar\" "
						+ strMainProperty
						+ " onKeyDown=\"if(event.keyCode==13) {"
						+ sOnKeydown
						+ "}\" onKeyUp=\""
						+ sOnKeyUp
						+ "\"> %</td>");
			}
			else
				if (strCtrlType.equals("branch"))
				{
					out.println(
						"<td"
							+ strSecondTD
							+ "><textarea id=\"" +strMainNames[0]+"\" name=\""
							+ strMainNames[0] 
							+ "\"  class=\"box\" bgcolor=\"#FF00\"  rows=2 cols=30 onKeyDown=\"if(event.keyCode==13) "
							+ sOnKeydown
							+ "\" onKeyUp=\""
							+ sOnKeyUp
							+ "\" "
							+ strMainProperty
							+ "></textarea></td>");
				}
				else
				{
					out.println(
						"<td"
							+ strSecondTD
							+ "><input type=\"text\" name=\""
							+ strMainNames[0]
							+ "\" class=\"box\" "
							+ strMainProperty
							+ " onKeyDown=\"if(event.keyCode==13) "
							+ sOnKeydown
							+ "\" onKeyUp=\""
							+ sOnKeyUp
							+ "\"></td>");
				}
		}
	}
	catch (Exception exp)
	{
		throw exp;
	}
}

/**
 * ��������(֪ͨ)���ݺŷŴ�--�޸�
 * @param out
 * @param lOfficeID ���´�ID
 * @param lCurrencyID ����ID
 * @param strFormName ��������
 * @param strCtrlName �Ŵ����ؼ�����
 * @param strTitle �Ŵ�����
 * @param lUserID ��ǰ�û�ID(��ʶֵ)
 * @param lAccountID ���˻�ID(��ʶֵ) 
 * @param lSubAccountID ���˻�ID(��ʶֵ)
 * @param strDepositNo ����(֪ͨ)�浥��(��ʶֵ)
 * @param lDepositTypeID �浥���ͣ�1�����ڣ�2��֪ͨ��
 * @param lTypeID �Ŵ����ͣ�ȡֵ���£�
 * 	1�����ڣ�֪ͨ������--����ƥ��ʱʹ��
 * 	21�����ڣ�֪ͨ��֧ȡ--ҵ����ʱʹ��
 *  22�����ڣ�֪ͨ��֧ȡ--ҵ�񸴺�ʱʹ��
 *  3����������ת��--ҵ����ʱʹ�ã�����ʾ�ѵ��ڵĴ浥��
 * 
 * @param strAccountIDCtrl����ѯʱ������
 * @param strFirstTD ��һ��TD������
 * @param strSecondTD �ڶ���TD������
 * @param sNextControlsClient ��һ������������ý���Ŀؼ�
 * @param strRtnEndDateCtrl ����ֵ�������գ���Ӧ�Ŀؼ���
 * @param strRtnOpenDateCtrl ����ֵ�������գ���Ӧ�Ŀؼ���
 * @param strRtnCapitalCtrl ����ֵ�����𣩶�Ӧ�Ŀؼ���
 * @param strRtnBalanceCtrl ����ֵ������Ӧ�Ŀؼ���
 * @param strRtnRateCtrl ����ֵ�����ʣ���Ӧ�Ŀؼ���
 * @param strRtnIntervalCtrl ����ֵ�����ޣ���Ӧ�Ŀؼ���
 * @param strRtnStartDateCtrl ����ֵ����ʼ���ڣ���Ӧ�Ŀؼ���
 */
public static void createFixedDepositNoCtrlByModify(
	JspWriter out,
	long lOfficeID,
	long lCurrencyID,
	String strFormName,
	String strCtrlName,
	String strTitle,
	long lUserID,
	long lAccountID,
	long lSubAccountID,
	String strDepositNo,
	long lDepositTypeID,
	long lTypeID,
	String strAccountIDCtrl,
	String strFirstTD,
	String strSecondTD,
	String[] strNextControls,
	String strRtnEndDateCtrl,
	String strRtnOpenDateCtrl,
	String strRtnCapitalCtrl,
	String strRtnBalanceCtrl,
	String strRtnRateCtrl,
	String strRtnIntervalCtrl,
	String strRtnStartDateCtrl,
	String strAmount,
	FinanceInfo financeInfo)
{
	String strMagnifierName = URLEncoder.encode("���ݺ�");
	if (lDepositTypeID == 1)
	{
		strMagnifierName = URLEncoder.encode("���ڴ��ݺ�");
	}
	else if (lDepositTypeID == 2)
	{
		strMagnifierName = URLEncoder.encode("֪ͨ���ݺ�");
	}
	String strMainProperty = " maxlength='30' value='" + strDepositNo + "'";
	if (strAmount != null && strAmount != "")
	{
		strMainProperty += " onblur=\"adjustAmount('" + strFormName + "','" + strAmount + "',1,'sChineseAmount','" + lCurrencyID + "');\" ";
		//strMainProperty += " onfocus=\"adjustAmount('" + strFormName +"','" + strRtnCapitalCtrl + "',2,'sChineseAmount','" + lCurrencyID+"');\" ";
	}
	String strPrefix = "";
	if (strRtnEndDateCtrl == null || strRtnEndDateCtrl.equals(""))
	{
		strRtnEndDateCtrl = "ItIsNotControl";
	}
	if (strRtnOpenDateCtrl == null || strRtnOpenDateCtrl.equals(""))
	{
		strRtnOpenDateCtrl = "ItIsNotControl";
	}
	if (strRtnCapitalCtrl == null || strRtnCapitalCtrl.equals(""))
	{
		strRtnCapitalCtrl = "ItIsNotControl";
	}
	if (strRtnBalanceCtrl == null || strRtnBalanceCtrl.equals(""))
	{
		strRtnBalanceCtrl = "ItIsNotControl";
	}
	if (strRtnRateCtrl == null || strRtnRateCtrl.equals(""))
	{
		strRtnRateCtrl = "ItIsNotControl";
	}
	if (strRtnIntervalCtrl == null || strRtnIntervalCtrl.equals(""))
	{
		strRtnIntervalCtrl = "ItIsNotControl";
	}
	if (strRtnStartDateCtrl == null || strRtnStartDateCtrl.equals(""))
	{
		strRtnStartDateCtrl = "ItIsNotControl";
	}
	if (strAmount == null || strAmount.equals(""))
	{
		strAmount = "ItIsNotControl";
	}
	String[] strMainNames =
		{ strCtrlName + "Ctrl", strRtnEndDateCtrl, strRtnOpenDateCtrl, strRtnCapitalCtrl, strRtnBalanceCtrl, strRtnRateCtrl, strRtnIntervalCtrl, strRtnStartDateCtrl, strAmount };
	String[] strMainFields = { "DepositNo", "EndDate", "OpenDate", "Capital", "Balance", "Rate", "Interval", "StartDate", "Balance" };
	String[] strReturnNames = { strCtrlName, strCtrlName + "AccountID" };
	String[] strReturnFields = { "SubAccountID", "AccountID" };
	String[] strReturnValues = { String.valueOf(lSubAccountID), String.valueOf(lAccountID)};
	String[] strDisplayNames = { URLEncoder.encode("���ݺ�")};
	String[] strDisplayFields = { "DepositNo" };
	//֧ȡ������ת��ʱ����ʾ��ͬ
	if (lTypeID == 21 || lTypeID == 22 || lTypeID == 23 || lTypeID == 2 || lTypeID == 3)
	{
		if (lDepositTypeID == 1)
		{
			//������ʾ���ݺš���������
			strDisplayNames = new String[] { URLEncoder.encode("���ݺ�"), URLEncoder.encode("���"), URLEncoder.encode("������")};
			strDisplayFields = new String[] { "DepositNo", "Balance", "EndDate" };
		}
		else
		{
			//������ʾ���ݺš�����������
			strDisplayNames = new String[] { URLEncoder.encode("���ݺ�"), URLEncoder.encode("���"), URLEncoder.encode("������")};
			strDisplayFields = new String[] { "DepositNo", "Balance", "OpenDate" };
		}
	}
	int nIndex = 0;
	String strSQL = "";
	boolean isCreateNewBook = Config.getBoolean(ConfigConstant.SETT_FIXEDDRAW_CREATENEWBOOK, true);
	if (strAccountIDCtrl != null && !strAccountIDCtrl.trim().equals(""))
	{
		if(lDepositTypeID == 1){
			
			if(isCreateNewBook){
					strSQL =
						"getFixedDepositNoSQL_CREATE_MODIFY("
						+ financeInfo.getSubAccountID()
						+ ","
						+ lOfficeID
						+ ","
						+ lCurrencyID
						+ ","
						+ lDepositTypeID
						+ ","
						+ strAccountIDCtrl
						+ ".value,"
						+ lUserID
						+ ","
						+ strCtrlName
						+ "Ctrl.value,"
						+ lTypeID
						+ ",'"
						+ Env.getSystemDateString(lOfficeID, lCurrencyID)
						+ "')";
				}else{
					strSQL =
						"getFixedDepositNoSQL_MODIFY("
						+ financeInfo.getID()
						+ ","
						+ lOfficeID
						+ ","
						+ lCurrencyID
						+ ","
						+ lDepositTypeID
						+ ","
						+ strAccountIDCtrl
						+ ".value,"
						+ lUserID
						+ ","
						+ strCtrlName
						+ "Ctrl.value,"
						+ lTypeID
						+ ",'"
						+ Env.getSystemDateString(lOfficeID, lCurrencyID)
						+ "')";
				}
		}
		else
		{
			strSQL =
				"getFixedDepositNoSQL("
					+ lOfficeID
					+ ","
					+ lCurrencyID
					+ ","
					+ lDepositTypeID
					+ ",-1,"
					+ lUserID
					+ ","
					+ strCtrlName
					+ "Ctrl.value,"
					+ lTypeID
					+ ",'"
					+ Env.getSystemDateString(lOfficeID, lCurrencyID)
					+ "')";
		}	
	}
	else
	{
		strSQL =
			"getFixedDepositNoSQL("
				+ lOfficeID
				+ ","
				+ lCurrencyID
				+ ","
				+ lDepositTypeID
				+ ",-1,"
				+ lUserID
				+ ","
				+ strCtrlName
				+ "Ctrl.value,"
				+ lTypeID
				+ ",'"
				+ Env.getSystemDateString(lOfficeID, lCurrencyID)
				+ "')";
	}
	Log.print("----------sql=" + strSQL);
	try
	{
		OBMagnifier.showZoomCtrl(
				out,
				strMagnifierName,
				strFormName,
				strPrefix,
				strMainNames,
				strMainFields,
				strReturnNames,
				strReturnFields,
				strReturnValues,
				strDisplayNames,
				strDisplayFields,
				nIndex,
				strMainProperty,
				strSQL,
				strNextControls,
				strTitle,
				strFirstTD,
				strSecondTD,
				false,
				false);
			
	}
	catch (Exception e)
	{
		Log.print("���ڴ��ݺŷŴ�[" + strCtrlName + "]�쳣��" + e.toString());
	}
}


/**
 * add by xlchang 2010-11-30 ��ԭshowZoomCtrl�������޸�,����td�Ĺ̶�����
 * ��ʾ��ͨ�Ŵ�
 * @param out
 * @param strMagnifierName �Ŵ󾵵�����
 * @param strFormName ��ҳ�������
 * @param strPrefix �ؼ�����ǰ׺
 * @param strMainNames �Ŵ󾵻�����λֵ�б�
 * @param strMainFields �Ŵ󾵻�����λ��Ӧ�ı���ֶ�
 * @param strReturnNames �Ŵ󾵷���ֵ�б�(����ֵ)
 * @param strReturnFields �Ŵ󾵷���ֵ(����ֵ)��Ӧ�ı���ֶ��б�
 * @param strReturnValues �Ŵ󾵷���ֵ(����ֵ)��Ӧ�ĳ�ʼֵ
 * @param strDisplayNames �Ŵ�С������ʾ����λ����
 * @param strDisplayFields �Ŵ�С������ʾ��λ��Ӧ�ı���ֶ�
 * @param nIndex ȷ��ѡ����,��0��ʼ,���С��0,��Ĭ��������λ����ѡ��,������ڻ����strDisplayNames.length,��û��ѡ����
 * @param strMainProperty �Ŵ���λ����
 * @param strSQL �Ŵ󾵲�ѯSQL���
 * @param strNextControls ������һ������
 * @param strTitle ��λ����
 * @param strFirstTD ��һ��TD������
 * @param strSecondTD �ڶ���TD������ 
 * @param blnIsOptional �Ƿ��ǿ�ѡ�Ŀǰ����ժҪ���ֽ�����Ŵ����ã�
 * @param blnIsRateCtrl �Ƿ������ʿؼ�
 * @throws Exception
 */
public static void showZoomCtrl1(
	JspWriter out,
	String strMagnifierName,
	String strFormName,
	String strPrefix,
	String[] strMainNames,
	String[] strMainFields,
	String[] strReturnNames,
	String[] strReturnFields,
	String[] strReturnValues,
	String[] strDisplayNames,
	String[] strDisplayFields,
	int nIndex,
	String strMainProperty,
	String strSQL,
	String[] strNextControls,
	String strTitle,
	String strFirstTD,
	String strSecondTD,

	boolean blnIsOptional,
	boolean blnIsRateCtrl)
	throws Exception
{
	String strButtonName = "button";
	try
	{
		//���Ŵ󾵲���
		checkValue(strMainNames, strMainFields, true);
		checkValue(strReturnNames, strReturnFields, strReturnValues, false);
		checkValue(strDisplayNames, strDisplayFields, true);
		if (strMagnifierName == null || strFormName == null || strFormName.equals("") || strSQL == null || strSQL.equals(""))
		{
			throw new IException(ZOOMERRORMSG);
		}
		if (strNextControls == null)
		{
			throw new IException(ZOOMERRORMSG);
		}
		if (strFirstTD == null)
		{
			strFirstTD = "";
		}
		if (strSecondTD == null)
		{
			strSecondTD = "";
		}
		//������
		//����ǰ׺
		if (strPrefix != null && !strPrefix.trim().equals(""))
		{
			for (int i = 0; i < strMainNames.length; i++)
			{
				strMainNames[i] = strPrefix + strMainNames[i];
			}
			for (int i = 0; i < strReturnNames.length; i++)
			{
				strReturnNames[i] = strPrefix + strReturnNames[i];
			}
		}
		//�������ڵ�����
		String sFeatures = null;
		if (strDisplayNames.length < 3)
		{
			sFeatures = "toolbar=no,menubar=no,resizable=no,location=no,width=520,height=450,scrollbars=yes";
		}
		else
		{
			sFeatures = "toolbar=no,menubar=no,resizable=no,location=no,width=624,height=540,scrollbars=yes";
		}
		//���ɴ��ݸ��������ڵĲ����ַ���
		String strParam = "";
		strParam = "strFormName=" + strFormName;
		strParam += "&strMagnifierName=" + strMagnifierName;
		strParam += "&nIndex=" + nIndex;
		if (!isSQL(strSQL))
		{
			strParam += "&strSQL='+" + strSQL + "+'";
		}
		else
		{
			strParam += "&strSQL=" + getSQL(strSQL);
		}
		for (int i = 0; i < strNextControls.length; i++)
		{
			strParam += "&strNextControls=" + strNextControls[i];
		}
		for (int i = 0; i < strMainNames.length; i++)
		{
			strParam += "&strMainNames=" + strMainNames[i];
			strParam += "&strMainFields=" + strMainFields[i];
		}
		if (strReturnNames != null)
		{
			boolean bValue = false;
			if (strReturnValues != null && strReturnValues.length == strReturnNames.length)
			{
				bValue = true;
			}
			for (int i = 0; i < strReturnNames.length; i++)
			{
				//�����������
				strParam += "&strReturnNames=" + strReturnNames[i];
				strParam += "&strReturnFields=" + strReturnFields[i];
				if (bValue)
				{
					out.println("<input type=\"hidden\" name=\"" + strReturnNames[i] + "\" value=\"" + strReturnValues[i] + "\">");
				}
				else
				{
					out.println("<input type=\"hidden\" name=\"" + strReturnNames[i] + "\">");
				}
			}
		}
		for (int i = 0; i < strDisplayNames.length; i++)
		{
			//�����������
			strParam += "&strDisplayNames=" + strDisplayNames[i];
			strParam += "&strDisplayFields=" + strDisplayFields[i];
		}
		//Log.print("strParam = " + strParam);
		//���ɲ�ѯ��ť���¼��ַ���
		String sOnKeydown =
			"if("
				+ strFormName
				+ "."
				+ strMainNames[0]
				+ ".disabled == false) {"
				+ strFormName
				+ "."
				+ strMainNames[0]
				+ ".focus();"
				+ " gnIsSelectCtrl=1;window.open('/NASApp/iTreasury-ebank/magnifier/ShowMagnifierZoom.jsp?"
				+ strParam
				+ "', 'SelectAnything', '"
				+ sFeatures
				+ "', false);}";
		//
		String sOnKeyUp = "";
		
		if (strReturnNames != null)
		{
			
			for (int i = 0; i < strReturnNames.length; i++)
			{
				sOnKeyUp += strReturnNames[i] + ".value = -1; ";
			}
		}
		//int iPos = strMainProperty.toLowerCase().indexOf("disabled");
		int iPos = -1;
		//��ʾ�ؼ�
		if (iPos == -1)
		{
			out.println(
				"<td "
					+ strFirstTD
					+ ">"
					+ strTitle
					+ "��&nbsp;"
					+ "<a href=#><img name=\""
					+ strButtonName
					+ "\" src='/webob/graphics/zoom.gif' border=0 onclick=\""
					+ sOnKeydown
					+ "\"></a></td>");
			//image
		}
		else
		{
			out.println("<td" + strFirstTD + ">" + strTitle + "��&nbsp;" + "<a href=#><img name=\"" + strButtonName + "\" src='/webob/graphics/zoom.gif' border=0 ></a></td>");
			//image
		}
		//blnIsOptional,�Ƿ��ѡ�����ժҪ���ֽ�����Ŵ���Ч��
		if (blnIsOptional == true)
		{
			if (strNextControls != null && strNextControls.length > 0)
			{
				strMainProperty = " onfocus=\"nextfield='" + strNextControls[0] + "'\" " + strMainProperty;
			}
			if (blnIsRateCtrl == true)
			{
				out.println("<td" + strSecondTD + "><input type=\"text\" name=\"" + strMainNames[0] + "\" class=\"box\" " + strMainProperty + ">%</td>");
			}
			else
			{
				out.println("<td" + strSecondTD + "><input type=\"text\" name=\"" + strMainNames[0] + "\" class=\"box\" " + strMainProperty + "></td>");
			}
		}
		else
		{
			if (blnIsRateCtrl == true)
			{
				out.println(
					"<td"
						+ strSecondTD
						+ "><input type=\"text\" name=\""
						+ strMainNames[0]
						+ "\" class=\"box\" "
						+ strMainProperty
						+ " onKeyDown=\"if(event.keyCode==13) "
						+ sOnKeydown
						+ "\" onKeyUp=\""
						+ sOnKeyUp
						+ "\">%</td>");
			}
			else
			{
				out.println(
					"<td"
						+ strSecondTD
						+ "><input type=\"text\" name=\""
						+ strMainNames[0]
						+ "\" class=\"box\" "
						+ strMainProperty
						+ " onKeyDown=\"if(event.keyCode==13) "
						+ sOnKeydown
						+ "\" onKeyUp=\""
						+ sOnKeyUp
						+ "\"></td>");
			}
		}
	}
	catch (Exception exp)
	{
		throw exp;
	}
}

/**
 * �����տ�����˺ŷŴ󾵣��㣩
 * @author yanliu
 * @param out
 * @param lClientID �ͻ�ID
 * @param lCurrencyID ����ID
 * @param strRelativeForm ������
 * @param sBankAccountCode �˺ţ���ʼֵ��
 * @param strFormName ��ҳ������
 * @param strCtrlName �Ŵ����ؼ�����
 * @param strTitle �Ŵ�����)
 * @param strClientNo �ͻ����(��ʶֵ)
 * @param strFirstTD ��һ��TD������
 * @param strSecondTD �ڶ���TD������
 * @param sNextControlsClient ��һ������������ý���Ŀؼ�
 * @param strRtnClientNameCtrl ����ֵ���ͻ����ƣ���Ӧ�Ŀؼ���
 * @param isBlurQuery �Ƿ�֧���տ���Ƶ�ģ����ѯ
 */

	public static void createPayeeAccountNOCtrl2(
		JspWriter out,
		long lCurrencyID,
		long lClientID,
		String strRelativeForm1,
		String strRelativeForm2,
		String strRelativeForm3,
		String strRelativeForm4,
		String strRelativeForm5,
		String strRelativeForm6,
		String strRelativeForm7,
		String strRelativeForm8,
		String strFormName,
		String sBankAccountCode,
		String strCtrlName,
		String strTitle,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,
		boolean isBlurQuery)
	{
		String strMagnifierName = URLEncoder.encode("�տ�˺�");
		String strMainProperty = " maxlength='50' value='" + sBankAccountCode + "'";
		String strPrefix = "";
		String[] strMainNames = { strCtrlName + "Ctrl", strRelativeForm1, strRelativeForm2, strRelativeForm3, strRelativeForm4, strRelativeForm5,strRelativeForm6,strRelativeForm7,strRelativeForm8};
		String[] strMainFields = { "spayeeacctno", "ID", "sPayeeName", "SPAYEEPROV", "SPAYEECITY", "sPayeeBankName","spayeebankcnapsno","spayeebankorgno","spayeebankexchangeno" };
		
		String[]  strReturnNames = { strCtrlName+"hiddenValue" }; //�տ
		
		String[] strReturnFields = {"spayeeacctno"
		};
		String[] strReturnValues = {""
		};
		String[] strDisplayNames = { URLEncoder.encode("�տ�˺�"), URLEncoder.encode("�տ����"), URLEncoder.encode("����أ�ʡ��"), URLEncoder.encode("����أ��У�"), URLEncoder.encode("����������"), URLEncoder.encode("���к�"), URLEncoder.encode("CNAPS��"), URLEncoder.encode("������")};
		String[] strDisplayFields = { "spayeeacctno", "sPayeeName","SPAYEEPROV", "spayeecity", "sPayeeBankName", "spayeebankexchangeno", "spayeebankcnapsno", "spayeebankorgno" };
		int nIndex = 0;
		String strSQL = "getPayeeAccountNOSQL(" + isBlurQuery + "," + lClientID + "," + lCurrencyID + "," + strCtrlName + "Ctrl.value," + strRelativeForm2 + ".value" + ")";
		
		try
		{
			showZoomCtrl(
				out,
				strMagnifierName,
				strFormName,
				strPrefix,
				strMainNames,
				strMainFields,
				strReturnNames,
				strReturnFields,
				strReturnValues,
				strDisplayNames,
				strDisplayFields,
				nIndex,
				strMainProperty,
				strSQL,
				strNextControls,
				strTitle,
				strFirstTD,
				strSecondTD,
				
				false,
				false);
			
		}
		catch (Exception e)
		{
		}
	}


}
