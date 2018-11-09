/*
 * CLASS   : Magnifier
 * FUNCTION: �Ŵ���
 * VERSION : 1.0.0
 * DATE    : 2003/08/08
 * AUTHOR  : Forest Ming
 * HISTORY :
 *
 */
package com.iss.itreasury.creditrating.util;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import javax.servlet.jsp.JspWriter;
import com.iss.itreasury.dataentity.CommonSelectInfo;
import com.iss.itreasury.ebank.util.OBMagnifier;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.securities.util.SECConstant;
import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.ConfigConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log;
public class CRERTMagnifier implements java.io.Serializable
{
	private static String ZOOMERRORMSG = "�Ŵ󾵲������ô���!";
	

	/**
	 * �����ͻ��Ŵ�
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
	public static void createClientCtrl(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lClientID,
		String strClientNo,
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
		String strSQL = "getClientSQL(" + lOfficeID + "," + strCtrlName + "Ctrl.value)";
		try
		{
			CRERTMagnifier.showZoomCtrl(
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
				strSecondTD);
		}
		catch (Exception e)
		{
			Log.print("�ͻ��Ŵ�[" + strCtrlName + "]�쳣��" + e.toString());
		}
	}
	
	/**
	 * ������׼�ȼ��Ŵ�
	 * @param out
	 * @param lOfficeID ���´�ID
	 * @param lCurrencyID ����ID
	 * @param strFormName ��������
	 * @param strCtrlName �Ŵ����ؼ�����
	 * @param strTitle �Ŵ�����
	 * @param defaultId ��ʼID
	 * @param strDefaultName ��ʼ����
	 * @param strFirstTD ��һ��TD������
	 * @param strSecondTD �ڶ���TD������
	 * @param sNextControlsClient ��һ������������ý���Ŀؼ�
	 * @param strRtnClientNameCtrl ����ֵ���ͻ����ƣ���Ӧ�Ŀؼ���
	 */
	public static void createCreRtStandardRatingCtrl(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long defaultId,
		String strDefaultName,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,
		String strRtnNameCtrl)
	{
		String strMagnifierName = URLEncoder.encode("��׼�ȼ�");
		String strMainProperty = " maxlength='30' value='" + strDefaultName + "'";
		String strPrefix = "";
		if (strRtnNameCtrl == null || strRtnNameCtrl.equals(""))
		{
			strRtnNameCtrl = "ItIsNotControl";
		}
		String[] strMainNames = { strCtrlName + "Ctrl" };
		String[] strMainFields = { "standardRatingName" };

		String[] strReturnNames = { strCtrlName};
		String[] strReturnFields = { "standardRatingID"};
		String[] strReturnValues = { String.valueOf(defaultId)};
		String[] strDisplayNames = { URLEncoder.encode("��׼�ȼ�����"), URLEncoder.encode("��׼�ȼ�����")};
		String[] strDisplayFields = { "standardRatingName", "standardRatingDescription"};
		int nIndex = 0;
		String strSQL = "getStandardRatingSQL(" + lOfficeID + ","+ lCurrencyID +"," + strCtrlName + "Ctrl.value)";
		try
		{
			CRERTMagnifier.showZoomCtrl(
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
				strSecondTD);
		}
		catch (Exception e)
		{
			Log.print("�ͻ��Ŵ�[" + strCtrlName + "]�쳣��" + e.toString());
		}
	}
	
	/**
	 * ����ָ����ϵ�Ŵ�
	 * @param out
	 * @param lOfficeID ���´�ID
	 * @param lCurrencyID ����ID
	 * @param strFormName ��������
	 * @param strCtrlName �Ŵ����ؼ�����
	 * @param strTitle �Ŵ�����
	 * @param defaultId ��ʼID
	 * @param strDefaultName ��ʼ����
	 * @param strFirstTD ��һ��TD������
	 * @param strSecondTD �ڶ���TD������
	 * @param sNextControlsClient ��һ������������ý���Ŀؼ�
	 * @param strRtnClientNameCtrl ����ֵ���ͻ����ƣ���Ӧ�Ŀؼ���
	 */
	public static void createCreRtTargetSetupCtrl(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long defaultId,
		String strDefaultName,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,
		String strRtnNameCtrl)
	{
		String strMagnifierName = URLEncoder.encode("ָ����ϵ");
		String strMainProperty = " maxlength='30' value='" + strDefaultName + "'";
		String strPrefix = "";
		if (strRtnNameCtrl == null || strRtnNameCtrl.equals(""))
		{
			strRtnNameCtrl = "ItIsNotControl";
		}
		String[] strMainNames = { strCtrlName + "Ctrl" };
		String[] strMainFields = { "targetSetupName" };

		String[] strReturnNames = { strCtrlName};
		String[] strReturnFields = { "targetSetupID"};
		String[] strReturnValues = { String.valueOf(defaultId)};
		String[] strDisplayNames = { URLEncoder.encode("ָ����ϵ����"), URLEncoder.encode("ָ����ϵ����")};
		String[] strDisplayFields = { "targetSetupName", "targetSetupDescription"};
		int nIndex = 0;
		String strSQL = "getTargetSetupSQL(" + lOfficeID + ","+ lCurrencyID +"," + strCtrlName + "Ctrl.value)";
		try
		{
			CRERTMagnifier.showZoomCtrl(
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
				strSecondTD);
		}
		catch (Exception e)
		{
			Log.print("�ͻ��Ŵ�[" + strCtrlName + "]�쳣��" + e.toString());
		}
	}
	

	/**
	 * �������������Ŵ�
	 * @param out
	 * @param lOfficeID ���´�ID
	 * @param lCurrencyID ����ID
	 * @param strFormName ��������
	 * @param strCtrlName �Ŵ����ؼ�����
	 * @param strTitle �Ŵ�����
	 * @param planID ��������ID
	 * @param strPlanName ������������
	 * @param strFirstTD ��һ��TD������
	 * @param strSecondTD �ڶ���TD������
	 * @param sNextControlsClient ��һ������������ý���Ŀؼ�
	 * @param strRtnClientNameCtrl ����ֵ���ͻ����ƣ���Ӧ�Ŀؼ���
	 */
	public static void createCreRtPlanCtrl(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long planID,
		String strPlanName,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,
		String strRtnNameCtrl)
	{
		String strMagnifierName = URLEncoder.encode("��������");
		String strMainProperty = " maxlength='30' value='" + strPlanName + "'";
		String strPrefix = "";
		if (strRtnNameCtrl == null || strRtnNameCtrl.equals(""))
		{
			strRtnNameCtrl = "ItIsNotControl";
		}
		String[] strMainNames = { strCtrlName + "Ctrl" };
		String[] strMainFields = { "planName" };

		String[] strReturnNames = { strCtrlName};
		String[] strReturnFields = { "planID"};
		String[] strReturnValues = { String.valueOf(planID)};
		String[] strDisplayNames = { URLEncoder.encode("������������"), URLEncoder.encode("��׼�ȼ�����"),URLEncoder.encode("����ָ����ϵ����")};
		String[] strDisplayFields = { "planName", "standratingName" ,"targetsetupName"};
		int nIndex = 0;
		String strSQL = "getPlanSQL(" + lOfficeID + ","+ planID+"," + strCtrlName + "Ctrl.value)";
		try
		{
			CRERTMagnifier.showZoomCtrl(
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
				strSecondTD);
		}
		catch (Exception e)
		{
			Log.print("�ͻ��Ŵ�[" + strCtrlName + "]�쳣��" + e.toString());
		}
	}
	/**
	 * �����ͻ��Ŵ�
	 * @param out
	 * @param lOfficeID ���´�ID
	 * @param lCurrencyID ����ID
	 * @param strFormName ��������
	 * @param strCtrlName �Ŵ����ؼ�����
	 * @param strTitle �Ŵ�����
	 * @param lClientID �ͻ�ID(��ʶֵ)
	 * @param strClientName �ͻ�����(��ʶֵ)
	 * @param strFirstTD ��һ��TD������
	 * @param strSecondTD �ڶ���TD������
	 * @param sNextControlsClient ��һ������������ý���Ŀؼ�
	 * @param strRtnClientNameCtrl ����ֵ���ͻ����ƣ���Ӧ�Ŀؼ���
	 */
	public static void createClientCtrl1(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lClientID,
		String strClientName,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls)
	{
		String strMagnifierName = URLEncoder.encode("�ͻ�");
		String strMainProperty = " maxlength='30' value='" + strClientName + "'";
		String strPrefix = "";
		String[] strMainNames = { strCtrlName + "Ctrl" };
		String[] strMainFields = { "clientName" };
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
		String strSQL = "getClientSQL1(" + lOfficeID + "," + strCtrlName + "Ctrl.value)";
		try
		{
			CRERTMagnifier.showZoomCtrl(
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
				strSecondTD);
		}
		catch (Exception e)
		{
			Log.print("�ͻ��Ŵ�[" + strCtrlName + "]�쳣��" + e.toString());
		}
	}
	/**
	 * �����ͻ��Ŵ�(��������ҵ����)
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
	 * @param strRtnEnterpriseTypeCtrl ����ֵ���ͻ�������ҵ���ͣ���Ӧ�Ŀؼ���
	 */
	public static void createClientCtrl2(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lClientID,
		String strClientNo,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,
		String strRtnClientNameCtrl,
		String strRtnEnterpriseTypeCtrl)
	{
		String strMagnifierName = URLEncoder.encode("�ͻ�");
		String strMainProperty = " maxlength='30' value='" + strClientNo + "'";
		String strPrefix = "";
		if (strRtnClientNameCtrl == null || strRtnClientNameCtrl.equals(""))
		{
			strRtnClientNameCtrl = "ItIsNotControl";
		}
		String[] strMainNames = { strCtrlName + "Ctrl", strRtnClientNameCtrl, strRtnEnterpriseTypeCtrl };
		String[] strMainFields = { "clientNo", "clientName", "EnterpriseTypeName" };
		//���⴦��
		long lFromClient = 0;
		if (lClientID > 0)
		{
			lFromClient = 1;
		}
		String[] strReturnNames = { strCtrlName, strCtrlName + "FromClient" };
		String[] strReturnFields = { "ClientID", "FromClient" };
		String[] strReturnValues = { String.valueOf(lClientID), String.valueOf(lFromClient)};
		String[] strDisplayNames = { URLEncoder.encode("�ͻ����"), URLEncoder.encode("�ͻ�����"), URLEncoder.encode("��ҵ����")};
		String[] strDisplayFields = { "clientNo", "clientName", "EnterpriseTypeName" };
		int nIndex = 0;
		String strSQL = "getClientSQL2(" + lOfficeID + "," + strCtrlName + "Ctrl.value)";
		try
		{
			CRERTMagnifier.showZoomCtrl(
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
				strSecondTD);
		}
		catch (Exception e)
		{
			Log.print("�ͻ��Ŵ�[" + strCtrlName + "]�쳣��" + e.toString());
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
		//2004-11-18 ģ��ƥ��֧�ֶ��ֶ�ƥ��
		String[] strMatchValues = new String[1];
		strMatchValues[0]=strMatchValue;
		
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
			strMatchValues,
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
	String[] strMatchValue,
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
		if (strMatchValue == null )//|| strMatchValue.equals(""))
		{
			strMatchValue = new String[1];
			strMatchValue[0] = strMainFields[0];
		}
		else
		{
			if(strMatchValue[0] == null || strMatchValue[0].equals(""))
			{
				strMatchValue[0] = strMainFields[0];
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
        String strTmp ="";
        
		/*  TOCONFIG��TODELETE  */
        /*
         * ��Ʒ������������Ŀ 
         * ninh 
         * 2005-03-24
         */
        
//        if(Env.getProjectName().equals("cpf"))//���⴦������
//        {
//            strTmp = "cpfLoan";
//        }
//        else
//        {
//            strTmp = "iTreasury-loan";
//        }

		strTmp = "iTreasury-loan";
		
		/*  TOCONFIG��END  */
        
		String sOnKeydown =
			"if("
				+ strFormName
				+ "."
				+ strMainNames[0]
				+ ".disabled == false) {gnIsSelectCtrl=1;window.open('"
				+ Env.URL_PREFIX
				+ "/"
                + strTmp
                +"/magnifier/ShowMagnifierZoom.jsp?"
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
					+ "<img name=\""
					+ strButtonName
					+ "\" style=\"cursor: hand;\" src='/webloan/image/icon.gif' border=0 onclick=\""
					+ sOnKeydown
					+ "\"></td>");
		}
		else
		{
			out.println("<td " + strFirstTD + " >" + strTitle + ":&nbsp;" + "<img name=\"" + strButtonName + "\" style=\"cursor: hand;\" src='/webloan/image/icon.gif' border=0 ></td>");
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

		out.println("<script language=\"JavaScript\">");
		out.println("function get" + strMainNames[0] + "(str)");
		out.println("{");
		out.println("   var sql = \"\"; ");
		out.println("   if (str != null && str != \"\") ");
		out.println("   {");
		if (strMatchValue == null)
		{
			out.println(" sql += \"\"; ");	
		}
		else
		{
			if(strMatchValue.length == 1)
			{
				out.println(" sql +=  \" and " + strMatchValue[0] + " like '" + URLEncoder.encode("%") + "\"+str+\"" + URLEncoder.encode("%") + "'\"; ");
			}
			else
			{
				out.println(" sql +=  \" and  ( \";");
				for(int i=0;i<strMatchValue.length;i++)
				{
					if(i==0)
					{
						if(strMatchValue[i] != null || !strMatchValue[i].equals("") )
						{
							out.println(" sql +=  \"  " + strMatchValue[i] + " like '" + URLEncoder.encode("%") + "\"+str+\"" + URLEncoder.encode("%") + "'\"; ");
						}
						else
						{
							out.println(" sql +=  \" 1=1 \"; ");
						}
					}
					else
					{
						if(strMatchValue[i] != null || !strMatchValue[i].equals("") )
						{
							out.println(" sql +=  \" or  " + strMatchValue[i] + " like '" + URLEncoder.encode("%") + "\"+str+\"" + URLEncoder.encode("%") + "'\"; ");
						}
						else
						{
							out.println(" sql +=  \" 1=1 \"; ");
						}
					}
				}
				out.println(" sql +=  \" ) \";");
			}
//			out.println(" sql +=  \" and " + strMatchValue + " like '" + URLEncoder.encode("%") + "\"+str+\"" + URLEncoder.encode("%") + "'\";    ");
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
		String[] strNextControls)
		throws Exception
	{
		String strTitle = strMagnifierName;
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
			strTitle);
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
		String strSecondTD)
		throws Exception
	{
		boolean blnIsOptional = false;
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
			blnIsOptional);
	}
	
	/**
	 * ��ʾ��ͨ�Ŵ�  add by xfma 2008-10-25
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
					+ " gnIsSelectCtrl=1;window.open('"
					+ Env.URL_PREFIX
					+"/iTreasury-settlement/magnifier/ShowMagnifierZoom.jsp?"
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
					"<td width=140"
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
			strSQL,
			strNextControls,
			strTitle,
			strFirstTD,
			strSecondTD,
			blnIsOptional,
			"");
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
					+ "/iTreasury-creditrating/magnifier/ShowMagnifierZoom.jsp?"
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
					"<td "
						+ strFirstTD
						+ ">"
						+ strTitle
						+ "��&nbsp;"
						+ "<a href=#><img name=\""
						+ strButtonName
						+ "\" src='/itreasury/graphics/icon.gif' border=0 onclick=\""
						+ sOnKeydown
						+ "\"></a></td>");
				//image
			}
			else
			{
				out.println("<td " + strFirstTD + ">" + strTitle + "&nbsp;" + "<a href=#><img name=\"" + strButtonName + "\" src='/itreasury/graphics/icon.gif' border=0 ></a></td>");
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
						out.println(
							"<td"
								+ strSecondTD
								+ "><textarea name=\""
								+ strMainNames[0]
								+ "\"  class=\"box\" bgcolor=\"#FF00\"  rows=2 cols=30>"
								+ strMainProperty
								+ "</textarea></td>");
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
								+ "><textarea name=\""
								+ strMainNames[0]
								+ "\"  class=\"box\" bgcolor=\"#FF00\"  rows=2 cols=30 onKeyDown=\"if(event.keyCode==13) "
								+ sOnKeydown
								+ "\" onKeyUp=\""
								+ sOnKeyUp
								+ "\">"
								+ strMainProperty
								+ "</textarea></td>");
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
	 * Added by leiyang 2008/03/03 
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
	public static void showNewZoomCtrl(
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
		String strMatchValue,
		String[] strNextControls,
		String strTitle,
		String strFirstTD,
		String strSecondTD,
		boolean blnIsOptional,
		String strCtrlType) throws Exception
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
			/*if (!isSQL(strSQL))
			{
				strParam += "&strSQL='+" + strSQL + "+'";
			}
			else
			{
				strParam += "&strSQL=" + getSQL(strSQL);
			}*/
			if (!isSQL(strSQL))
			{
				strParam += "&strSQL= select * from ( '+" + strSQL + "+' ) where 1=1 '+get" + strMainNames[0] + "(" + strMainNames[0] + ".value)+'";
			}
			else
			{
				strParam += "&strSQL= select * from ( " + strSQL + " ) where 1=1 '+get" + strMainNames[0] + "(" + strMainNames[0] + ".value)+'";
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
					+ "/iTreasury-creditrating/magnifier/ShowMagnifierZoom.jsp?"
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
						out.println(
							"<td"
								+ strSecondTD
								+ "><textarea name=\""
								+ strMainNames[0]
								+ "\"  class=\"box\" bgcolor=\"#FF00\"  rows=2 cols=30>"
								+ strMainProperty
								+ "</textarea></td>");
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
								+ "><textarea name=\""
								+ strMainNames[0]
								+ "\"  class=\"box\" bgcolor=\"#FF00\"  rows=2 cols=30 onKeyDown=\"if(event.keyCode==13) "
								+ sOnKeydown
								+ "\" onKeyUp=\""
								+ sOnKeyUp
								+ "\">"
								+ strMainProperty
								+ "</textarea></td>");
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
			
			//int iPos = strMainProperty.toLowerCase().indexOf("disabled");
			int iPos = -1;
			//��ʾ�ؼ�
			if (iPos == -1)
			{
				out.println(
					"<td width='150'"
						+ strFirstTD
						+ ">"
						+ strTitle
						+ "<a href=#><img name=\""
						+ strButtonName
						+ "\" src='/websett/image/icon.gif' border=0 onclick=\""
						+ sOnKeydown
						+ "\"></a></td>");
				//image
			}
			else
			{
				out.println("<td " + strFirstTD + ">" + strTitle + "<a href=#><img name=\"" + strButtonName + "\" src='/websett/image/icon.gif' border=0 ></a></td>");
				//image
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
				out.println(" sql +=  \" and " + strMatchValue + " like '" + URLEncoder.encode("%") + "\"+str+\"" + URLEncoder.encode("%") + "'\";   ");
			}
			out.println("   }");
			out.println("    ");
			out.println("   return sql;    ");
			out.println("}");
			out.println("</script> ");
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
		String strTitle)
		throws Exception
	{
		String strFirstTD = "";
		String strSecondTD = "";
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
			strSecondTD);
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
	 * @param strAccountNo �˻����
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
		String strAccountNo)
		throws Exception
	{
		String strTitle = strMagnifierName;
		showSpecialZoomCtrl(
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
			nIndex,
			strMainProperty,
			strSQL,
			strNextControls,
			strAccountNo,
			strTitle);
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
	 * @param strAccountNo �˻����
	 * @param strTitle �Ŵ󾵵�����
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
		String strTitle)
		throws Exception
	{
		String strFirstTD = "";
		String strSecondTD = "";
		showSpecialZoomCtrl(
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
			nIndex,
			strMainProperty,
			strSQL,
			strNextControls,
			strAccountNo,
			strTitle,
			strFirstTD,
			strSecondTD);
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
	 * @param strAccountNo �˻����
	 * @param strTitle �Ŵ󾵵�����
	 * @param strFirstTD ��һ��TD������
	 * @param strSecondTD �ڶ���TD������ 
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
		String strSecondTD)
		throws Exception
	{
		showSpecialZoomCtrl(
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
			nIndex,
			strMainProperty,
			strSQL,
			strNextControls,
			strAccountNo,
			strTitle,
			strFirstTD,
			strSecondTD,
			"");
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
	 * @param strAccountNo �˻����
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

			/*�޸� by kenny (��־ǿ) ����˻��Ŷ���������*/
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
					+ "/iTreasury-creditrating/magnifier/ShowMagnifierZoom.jsp?"
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
					//Modify by leiyang date 2008/06/12
					//sOnKeyUp += "if(" + strClientIDCtrl + "FromClient.value == 0) {" + strClientIDCtrl + ".value = -1;}";
					sOnKeyUp += "if(" + strClientIDCtrl + ".value == 0) {" + strClientIDCtrl + ".value = -1;}";
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
						+ "\" src='/itreasury/graphics/icon.gif' border=0 onclick=\""
						+ sOnKeydown
						+ "\"></a></td>");
				//image
			}
			else
			{
				out.println("<td " + strFirstTD + ">" + strTitle + "��&nbsp;" + "<a href=#><img name=\"" + strButtonName + "\" src='/itreasury/graphics/icon.gif' border=0 ></a></td>");
				//image
			}
			/*�޸� by kenny (��־ǿ) (2007-03-21) ����˻��Ŷ���������*/
			//��װ�˻���ģ����ƥ���ֵ
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
			//���˻���Ų��
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
					"<input type=\"text\" size=\"8\" maxlength=\"8\" name=\""
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
						"<input type=\"text\" size=\"8\" maxlength=\"8\" name=\""
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
	 * added by leiyang 2008/03/03
	 * 
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
	 * @param strAccountNo �˻����
	 * @param strTitle �Ŵ󾵵�����
	 * @param strFirstTD ��һ��TD������
	 * @param strSecondTD �ڶ���TD������ 
	 * @param String strClientIDCtrl �����ؼ����ͻ�ID��Ӧ�Ŀؼ����ƣ�
	 * @throws Exception
	 */
	public static void showNewSpecialZoomCtrl(
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

			/*�޸� by kenny (��־ǿ) ����˻��Ŷ���������*/
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

			/*�޸� by kenny (��־ǿ) (2007-03-21) ����˻��Ŷ���������*/
			//��װ�˻���ģ����ƥ���ֵ
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
			out.println("</td>");
			
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
						+ "<a href=#><img name=\""
						+ strButtonName
						+ "\" src='/websett/image/icon.gif' border=0 onclick=\""
						+ sOnKeydown
						+ "\"></a></td>");
				//image
			}
			else
			{
				out.println("<td " + strFirstTD + ">" + strTitle + "<a href=#><img name=\"" + strButtonName + "\" src='/websett/image/icon.gif' border=0 ></a></td>");
				//image
			}
			
			//script��������
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
		if(nIndex<0){
		    return strSQL;
		}
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
				
				case 1 : //��������--ģ�����ƷŴ�
					Log.print("ģ�����ƷŴ�");
					strReturn = Constant.ModuleType.getName(lID);
					break;
				case 2 : //��������--ҵ�����ͷŴ�
					Log.print("ҵ�����ͷŴ󾵷Ŵ�");
					strReturn = Constant.ApprovalLoanType.getName(lID);
					break;
				case 3 : //��������--�����Ŵ�
					Log.print("�����Ŵ󾵷Ŵ�");
					strReturn = Constant.ApprovalAction.getName(lID);
					break;
				case 4 : //��������--״̬
					Log.print("״̬�Ŵ�");
					strReturn = Constant.ApprovalStatus.getName(lID);
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
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		Vector vResult = new Vector();
		try
		{
			
			if(strMainFields!=null && strMainFields.length>0){
				System.out.println("   kkf:"+strMainFields.length);
				
			}else{
				System.out.println("   kkf: �������Ĳ�����NULL�͵�");
			}
			
			conn = Database.getConnection();
			ps = conn.prepareStatement(strSQL);
			rs = ps.executeQuery();
			System.out.println("SQL="+strSQL);
			while (rs.next())
			{
				info = new CommonSelectInfo();
				//��ȡ�Ŵ󾵲�ѯ�����ֶ�
				Object[] oMainCols = new Object[strMainFields.length];
				
				
				for (int i = 0; i < strMainFields.length; i++)
				{
					//�ж��Ƿ���Ҫ�ӳ�����Constant��ȡ�����ݡ�
					//if(strMainFields[i].equals("ClientCode")==true)
					//{
					//	strMainFields[i]="ClientName";
					//}
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
			System.out.println("qlantest====="+e.getMessage());
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
	public static void CreateApprovalSettingCtrl(long lOfficeID, long lCurrencyID, JspWriter out, long lApprovalID, String strFormName, String strControlName, String strPrefix, String strMainProperty, String strReturnName, String[] strNextControls) throws Exception
	{
		try
		{
			//���SQL��ҳ��
			out.println("<script language=\"javascript\">");
			out.println("/*====================" + URLEncoder.encode("��ʾ�������Ŵ�") + "=================*/");
			out.println("function " + strPrefix + "getApprovalSettingSQL(nOfficeID,lApprovalID,sname)");
			out.println("{");
			out.println("	var sql = \"select ID, sName \";");
			out.println("	sql += \" from loan_approvalSetting \";");
			out.println("	sql += \" where nStatusID = 2 \";");
			out.println(" ");
			out.println("   if (nOfficeID > 0)");
			out.println("	{");
			out.println("		sql += \" and nOfficeID = \" + nOfficeID; ");
			out.println("	}");
			out.println("   if (lApprovalID > 0)");
			out.println("	{");
			out.println("		sql += \" and ID = \" + lApprovalID; ");
			out.println("	}");
			out.println("   if (sname != null && sname != \"\")");
			out.println("   {");
			out.println("       sql += \" and sname like '%\" + sname + \"%'\"");
			out.println("   }");
			out.println("	return sql;");
			out.println("}");
			out.println("</SCRIPT> ");
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
			String[] strDisplayNames = { URLEncoder.encode("���������"), URLEncoder.encode("����������") };
			String[] strDisplayFields = { "ID", "sName" };
			int nIndexOffice = 0;
			String name = DataFormat.toChinese(strFormName+"."+strControlName+".value");
			String strSQL = strPrefix + "getApprovalSettingSQL(" + lOfficeID + ","  + lApprovalID + "," + name + ")";
						
			CRERTMagnifier.showZoomCtrl(
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
				nIndexOffice,
				strMainProperty,
				strSQL,
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
	
}