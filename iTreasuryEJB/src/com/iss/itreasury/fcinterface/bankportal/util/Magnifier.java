package com.iss.itreasury.fcinterface.bankportal.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import java.net.URLEncoder;

import javax.servlet.jsp.JspWriter;

import com.iss.itreasury.fcinterface.bankportal.privilegemgt.DataPrivilegeUtil;

/**
 * @author jsxie
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class Magnifier
{
	private static String ZOOMERRORMSG = "�Ŵ󾵲������ô���!";
	
	/**��־����*/
	private static Logger log = new Logger(Magnifier.class);
	
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
			String[] strNextControls)
			throws Exception
		{
			String strButtonName = "button";
			try{
				//���Ŵ󾵲���
				checkValue(strMainNames, strMainFields, true);
				checkValue(strReturnNames, strReturnFields, strReturnValues, false);
				checkValue(strDisplayNames, strDisplayFields, true);
				if (strMagnifierName == null || strFormName == null || strFormName.equals("") || strSQL == null || strSQL.equals(""))
					throw new Exception(ZOOMERRORMSG);
				if (strNextControls == null)
					throw new Exception(ZOOMERRORMSG);
				//������
				
				//����ǰ׺
				if (strPrefix != null && !strPrefix.trim().equals("")){
					for (int i = 0; i < strMainNames.length; i++){
						strMainNames[i] = strPrefix + strMainNames[i];
					}
					for (int i = 0; i < strReturnNames.length; i++){
						strReturnNames[i] = strPrefix + strReturnNames[i];
					}
				}
				//�������ڵ�����
				String sFeatures = null;
				if (strDisplayNames.length < 3){
					sFeatures = "toolbar=no,menubar=no,resizable=no,location=no,width=520,height=450,scrollbars=yes";
				}else{
					sFeatures = "toolbar=no,menubar=no,resizable=no,location=no,width=624,height=540,scrollbars=yes";
				}
				//���ɴ��ݸ��������ڵĲ����ַ���
				String strParam = "";			
				strParam = "strFormName=" + strFormName;
				strParam += "&isChinese=" + "����";//�ж��Ƿ���Ҫ����ת��
				strParam += "&strMagnifierName=" + strMagnifierName;
				strParam += "&nIndex=" + nIndex;
				if (!isSQL(strSQL)){
					strParam += "&strSQL='+" + strSQL + "+'";
				}else{
					strParam += "&strSQL=" + getSQL(strSQL);
				}
				for (int i = 0; i < strNextControls.length; i++){
					strParam += "&strNextControls=" + strNextControls[i];
				}
				for (int i = 0; i < strMainNames.length; i++){
					strParam += "&strMainNames=" + strMainNames[i];
					strParam += "&strMainFields=" + strMainFields[i];
				}
				if (strReturnNames != null){
					boolean bValue = false;
					if (strReturnValues != null && strReturnValues.length == strReturnNames.length){
						bValue = true;
					}
					for (int i = 0; i < strReturnNames.length; i++){
						//�����������
						strParam += "&strReturnNames=" + strReturnNames[i];
						strParam += "&strReturnFields=" + strReturnFields[i];
						if (bValue){
							out.println("<input type=\"hidden\" name=\"" + strReturnNames[i] + "\" value=\"" + strReturnValues[i] + "\">");
						}else{
							out.println("<input type=\"hidden\" name=\"" + strReturnNames[i] + "\">");
						}
					}
				}
				log.debug("strDisplayNames.length="+strDisplayNames.length);
				log.debug("strDisplayFields.length"+strDisplayFields.length);
				for (int i = 0; i < strDisplayNames.length; i++){
					//�����������
					strParam += "&strDisplayNames=" + strDisplayNames[i];
					strParam += "&strDisplayFields=" + strDisplayFields[i];
				}
				//Log.print("strParam = " + strParam);
				//���ɲ�ѯ��ť���¼��ַ���
				String sOnKeydown =
					"if(" + strFormName + "." + strMainNames[0] + ".disabled == false) {gnIsSelectCtrl=1;window.open('"
						+ Env.getEnvConfigItem(Env.G_BANKPORTAL_URL)
						+ "/magnifier/ShowMagnifierZoom.jsp?"
						+ strParam
						+ "', 'SelectAnything', '"
						+ sFeatures
						+ "', false);}";
				//
				String sOnKeyUp = "";
				if (strReturnNames != null){
					for (int i = 0; i < strReturnNames.length; i++){
						sOnKeyUp += strReturnNames[i] + ".value = -1; ";
					}
				}
				
				//ͼƬ�ؼ�
				out.println(
						"<img style=\"cursor:hand\" name=\""
						+ strButtonName
						+ "\" src='/itreasury/graphics/icon.gif' border=0 onclick=\""
						+ sOnKeydown
						+ "\">");
				//�ı���ؼ�
				out.println(
						"<input type=\"text\" name=\""
						+ strMainNames[0]
						+ "\" class=\"box\" "
						+ strMainProperty
						+ " onKeyDown=\"if(event.keyCode==13) "
						+ sOnKeydown
						+ "\" onKeyUp=\""
						+ sOnKeyUp
						+ "\">");
			}catch (Exception exp){throw exp;}
		}
//	public static void showZoomCtrl(
//		JspWriter out,
//		String strMagnifierName,
//		String strFormName,
//		String strPrefix,
//		String[] strMainNames,
//		String[] strMainFields,
//		String[] strReturnNames,
//		String[] strReturnFields,
//		String[] strReturnValues,
//		String[] strDisplayNames,
//		String[] strDisplayFields,
//		int nIndex,
//		String strMainProperty,
//		String strSQL,
//		String[] strNextControls)
//		throws Exception
//	{
//		String strButtonName = "button";
//		try{
//			//���Ŵ󾵲���
//			checkValue(strMainNames, strMainFields, true);
//			checkValue(strReturnNames, strReturnFields, strReturnValues, false);
//			checkValue(strDisplayNames, strDisplayFields, true);
//			if (strMagnifierName == null || strFormName == null || strFormName.equals("") || strSQL == null || strSQL.equals(""))
//				throw new Exception(ZOOMERRORMSG);
//			if (strNextControls == null)
//				throw new Exception(ZOOMERRORMSG);
//			//������
//			
//			//����ǰ׺
//			if (strPrefix != null && !strPrefix.trim().equals("")){
//				for (int i = 0; i < strMainNames.length; i++){
//					strMainNames[i] = strPrefix + strMainNames[i];
//				}
//				for (int i = 0; i < strReturnNames.length; i++){
//					strReturnNames[i] = strPrefix + strReturnNames[i];
//				}
//			}
//			//�������ڵ�����
//			String sFeatures = null;
//			if (strDisplayNames.length < 3){
//				sFeatures = "toolbar=no,menubar=no,resizable=no,location=no,width=520,height=450,scrollbars=yes";
//			}else{
//				sFeatures = "toolbar=no,menubar=no,resizable=no,location=no,width=624,height=540,scrollbars=yes";
//			}
//			//���ɴ��ݸ��������ڵĲ����ַ���
//			String strParam = "";			
//			strParam = "strFormName=" + strFormName;
//			strParam += "&isChinese=" + "����";//�ж��Ƿ���Ҫ����ת��
//			strParam += "&strMagnifierName=" + strMagnifierName;
//			strParam += "&nIndex=" + nIndex;
//			if (!isSQL(strSQL)){
//				strParam += "&strSQL='+" + strSQL + "+'";
//			}else{
//				strParam += "&strSQL=" + getSQL(strSQL);
//			}
//			for (int i = 0; i < strNextControls.length; i++){
//				strParam += "&strNextControls=" + strNextControls[i];
//			}
//			for (int i = 0; i < strMainNames.length; i++){
//				strParam += "&strMainNames=" + strMainNames[i];
//				strParam += "&strMainFields=" + strMainFields[i];
//			}
//			if (strReturnNames != null){
//				boolean bValue = false;
//				if (strReturnValues != null && strReturnValues.length == strReturnNames.length){
//					bValue = true;
//				}
//				for (int i = 0; i < strReturnNames.length; i++){
//					//�����������
//					strParam += "&strReturnNames=" + strReturnNames[i];
//					strParam += "&strReturnFields=" + strReturnFields[i];
//					if (bValue){
//						out.println("<input type=\"hidden\" name=\"" + strReturnNames[i] + "\" value=\"" + strReturnValues[i] + "\">");
//					}else{
//						out.println("<input type=\"hidden\" name=\"" + strReturnNames[i] + "\">");
//					}
//				}
//			}
//			log.debug("strDisplayNames.length="+strDisplayNames.length);
//			log.debug("strDisplayFields.length"+strDisplayFields.length);
//			for (int i = 0; i < strDisplayNames.length; i++){
//				//�����������
//				strParam += "&strDisplayNames=" + strDisplayNames[i];
//				strParam += "&strDisplayFields=" + strDisplayFields[i];
//			}
//			//Log.print("strParam = " + strParam);
//			//���ɲ�ѯ��ť���¼��ַ���
//			String sOnKeydown =
//				"if(" + strFormName + "." + strMainNames[0] + ".disabled == false) {gnIsSelectCtrl=1;window.open('"
//					+ Env.getEnvConfigItem(Env.G_BANKPORTAL_URL)
//					+ "/magnifier/ShowMagnifierZoom.jsp?"
//					+ strParam
//					+ "', 'SelectAnything', '"
//					+ sFeatures
//					+ "', false);}";
//			//
//			String sOnKeyUp = "";
//			if (strReturnNames != null){
//				for (int i = 0; i < strReturnNames.length; i++){
//					sOnKeyUp += strReturnNames[i] + ".value = -1; ";
//				}
//			}
//			
//			//ͼƬ�ؼ�
//			out.println(
//					"<a href=#><img name=\""
//					+ strButtonName
//					+ "\" src='/itreasury/graphics/icon.gif' border=0 onclick=\""
//					+ sOnKeydown
//					+ "\"></a>");
//			//�ı���ؼ�
//			out.println(
//					"<input type=\"text\" name=\""
//					+ strMainNames[0]
//					+ "\" class=\"box\" "
//					+ strMainProperty
//					+ " onKeyDown=\"if(event.keyCode==13) "
//					+ sOnKeydown
//					+ "\" onKeyUp=\""
//					+ sOnKeyUp
//					+ "\">");
//		}catch (Exception exp){throw exp;}
//	}
	
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
				throw new Exception(ZOOMERRORMSG);
			}
			if (strNextControls == null)
			{
				throw new Exception(ZOOMERRORMSG);
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
			//������
			String strTempCtl1 = strMainNames[0] + "Ctrl1";
			String strTempCtl2 = strMainNames[0] + "Ctrl2";
			String strTempCtl3 = strMainNames[0] + "Ctrl3";
			String strTempCtl4 = strMainNames[0] + "Ctrl4";
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
			strParam += "&isChinese=" + URLEncoder.encode("����");//�ж��Ƿ���Ҫ����ת��
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
					+ strTempCtl1
					+ ".disabled == false) {gnIsSelectCtrl=1;window.open('"
					+ Env.getEnvConfigItem(Env.G_BANKPORTAL_URL)
					+ "/magnifier/ShowMagnifierZoom.jsp?"
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
//				Log.print(strAccountNo1);
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
//				Log.print(strAccountNo2);
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
//				Log.print(strAccountNo3);
				strTemp = strTemp.substring(strTemp.indexOf("-") + 1);
				strAccountNo4 = strTemp;
//				Log.print(strAccountNo4);
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
	private static void checkValue(String[] strNames, String[] strValues, boolean bIsAllowNull) throws Exception
	{
		if (!bIsAllowNull)
		{
			if (strNames != null && strValues != null)
			{
				if (strNames.length != strValues.length)
				{
					throw new Exception(ZOOMERRORMSG);
				}
			}
			return;
		}
		if (strNames == null || strValues == null)
		{
			throw new Exception(ZOOMERRORMSG);
		}
		if (strNames.length == 0 || strValues.length == 0)
		{
			throw new Exception(ZOOMERRORMSG);
		}
		if (strNames.length != strValues.length)
		{
			throw new Exception(ZOOMERRORMSG);
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
	private static void checkValue(String[] strNames, String[] strFields, String[] strValues, boolean bIsAllowNull) throws Exception
	{
		if (!bIsAllowNull)
		{
			if (strNames != null && strFields != null && strValues != null)
			{
				if (strNames.length != strFields.length || strNames.length != strValues.length || strFields.length != strValues.length)
				{
					throw new Exception(ZOOMERRORMSG);
				}
			}
		}
		else
		{
			if (strNames == null || strFields == null || strValues == null)
			{
				throw new Exception(ZOOMERRORMSG);
			}
			if (strNames.length == 0 || strFields.length == 0 || strValues.length == 0)
			{
				throw new Exception(ZOOMERRORMSG);
			}
			if (strNames.length != strFields.length || strNames.length != strValues.length || strFields.length != strValues.length)
			{
				throw new Exception(ZOOMERRORMSG);
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
//		try
//		{
//			switch (nType)
//			{
//				case 1 : //��ͬ���ͷŴ�--��ʾ��
//					log.debug("��ͬ���ͷŴ�");
//					//strReturn = Notes.getCodeName(Notes.CODETYPE_CONTRACT_TYPE,lID);
//					break;
//				case 2 : //��������--ģ�����ƷŴ�
//					log.debug("ģ�����ƷŴ�");
//					strReturn = Constant.ModuleType.getName(lID);
//					break;
//				case 3 : //��������--ҵ�����ͷŴ�
//					log.debug("ҵ�����ͷŴ󾵷Ŵ�");
//					strReturn = Constant.ApprovalLoanType.getName(lID);
//					break;
//				case 4 : //��������--�����Ŵ�
//					log.debug("�����Ŵ󾵷Ŵ�");
//					strReturn = Constant.ApprovalAction.getName(lID);
//					break;
//				case 5 : //��������--״̬
//					log.debug("״̬�Ŵ�");
//					strReturn = Constant.ApprovalStatus.getName(lID);
//					break;
//				case 6 : //����Ʊ�ݷŴ󾵣����������ӳ����еã�
//					log.debug("����Ʊ�ݷŴ�");
//					strReturn = SETTConstant.BankBillType.getName(lID);
//					break;
//				case 7 : //���ڻ�֪ͨ�浥�ţ��Ӷ��ڿ�������ȡ�ã�
//					log.debug("���ڻ�֪ͨ�浥��");
//					UtilOperation utiloperation = new UtilOperation();
//					strReturn = utiloperation.getOpenDepositNo(lID);
//					break;
//				case 8 : //���ֻ�Ʊ�Ŵ󾵣���Ʊ����
//					log.debug("���ֻ�Ʊ�Ŵ�");
//					strReturn = LOANConstant.DraftType.getName(lID);
//					break;
//				case 9 : //���ֻ�Ʊ�Ŵ󾵣��Ƿ񱾵�
//					log.debug("���ֻ�Ʊ�Ŵ�");
//					strReturn = Constant.YesOrNo.getName(lID);
//					break;
//				case 10 : //���ֻ�Ʊ�Ŵ󾵣��Ƿ񱾵�
//					log.debug("������ͬ�Ŵ�");
//					strReturn = LOANConstant.AssureType2.getName(lID);
//					break;
//				case 11 : //���ֻ�Ʊ�Ŵ󾵣���ͬ��
//					log.debug("���ֻ�Ʊ�Ŵ�");
//					strReturn = NameRef.getContractNoByID(lID);
//					break;
//				case 12 : //���ֻ�Ʊ�Ŵ󾵣�����ƾ֤��
//					log.debug("���ֻ�Ʊ�Ŵ�");
//					strReturn = NameRef.getDiscountCredenceNoByID(lID);
//					break;
//				default :
//					strReturn = "��ֵû�дӳ�����ȡ�ã����޸ģ�";
//					break;
//			}
//		}
//		catch (Exception ex)
//		{
//			throw new Exception("�������ݿ����");
//		}
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
	public static Vector getCommonSelectList(String[] strMainFields, String[] strReturnFields, String[] strDisplayFields, String strSQL,SessionMng sessionMng) throws Exception
	{
		CommonSelectInfo info = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		Vector vResult = new Vector();
		try
		{
			
			conn = Database.getConnection();
			ps = conn.prepareStatement(DataPrivilegeUtil.processSQL(strSQL,sessionMng));
			rs = ps.executeQuery();
			log.debug("SQL="+strSQL);
			while (rs.next())
			{
				info = new CommonSelectInfo();
				//��ȡ�Ŵ󾵲�ѯ�����ֶ�
				Object[] oMainCols = new Object[strMainFields.length];
				
				
				for (int i = 0; i < strMainFields.length; i++)
				{
					//�ж��Ƿ���Ҫ�ӳ�����Constant��ȡ�����ݡ�
					log.debug("������"+strMainFields[i]);
					//if(strMainFields[i].equals("ClientCode")==true)
					//{
					//	strMainFields[i]="ClientName";
					//}
					String strTempMain = rs.getString(strMainFields[i]);
					log.debug(strTempMain+",");
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
					log.debug("��ʾ��ҳ���������"+strDisplayFields[i]);
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
			log.debug("qlantest====="+e.getMessage());
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
				log.error("�ر����ݿ�����ʱ�������ݿ����");
			}
		}
		return vResult.size() > 0 ? vResult : null;
	}
	
}
