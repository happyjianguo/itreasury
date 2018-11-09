/*
 * CLASS   : Magnifier
 * FUNCTION: �Ŵ���
 * VERSION : 1.0.0
 * DATE    : 2003/08/08
 * AUTHOR  : Forest Ming
 * HISTORY :
 *
 */
package com.iss.itreasury.loan.util;
import java.io.Serializable;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.servlet.jsp.JspWriter;

import com.iss.itreasury.dataentity.CommonSelectInfo;
import com.iss.itreasury.settlement.util.NameRef;
import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.ConfigConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log; 
public class LOANMagnifier  implements Serializable
{ 
	private static String ZOOMERRORMSG = "�Ŵ󾵲������ô���!"; 
	
	/**
	 * �����ⲿ�������Ϳͻ����˻��Ŵ�
	 * @param out
	 * @param lOfficeID ���´�ID
	 * @param lCurrencyID ����ID
	 * @param strFormName ��������
	 * @param strCtrlName �Ŵ����ؼ�����
	 * @param strTitle �Ŵ�����
	 * @param lExtAccountID �ⲿ�˻�ID(��ʶֵ)
	 * @param strExtAccountNo �ⲿ�˻����(��ʶֵ)
	 * @param strFirstTD ��һ��TD������
	 * @param strSecondTD �ڶ���TD������
	 * @param strNextControls ��һ������������ý���Ŀؼ�
	 * @param strRtnClientNameCtrl ����ֵ���˻��ͻ����ƣ���Ӧ�Ŀؼ���
	 * @param strRtnProvinceCtrl ����ֵ�������(ʡ)����Ӧ�Ŀؼ���
	 * @param strRtnCityCtrl ����ֵ�������(��)����Ӧ�Ŀؼ���
	 * @param strRtnBankNameCtrl ����ֵ�����������ƣ���Ӧ�Ŀؼ���
	 */
	public static void createExtAccountCtrl(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		String lExtAccountID,
		String strExtAccountNo,
		String strFirstTD,
		String strSecondTD,
		String strNextControls,
		String strRtnClientName,
		String strRtnProvince,
		String strRtnCity,
		String strRtnBankName)
	{
		
		String strMagnifierName = "�տ�˻���";
		String strMainProperty = " size='30' maxlength='30'";
		String strPrefix = "";
		
		String[] strMainNames =  {"txtAccountCtrl","sreceiveclientname","sremitinprovince","sremitincity","sremitbank"};
		String[] strMainFields = { "ExtAcctNo", "ExtAcctName", "ExtProvince", "ExtCity", "ExtBankName" };
		String[] strReturnNames = { strCtrlName };
		String[] strReturnFields = { "ExtAcctNo" };
		String[] strReturnValues = { String.valueOf(lExtAccountID)};
		String[] strDisplayNames = {"�˻����","�˻�����","ʡ","��","����������"};
		String[] strDisplayFields = { "ExtAcctNo", "ExtAcctName", "ExtProvince", "ExtCity", "ExtBankName" };
		int nIndex = 0;
		String strSQL = "getExtAcctCurrencySQL(" + lOfficeID + "," +lCurrencyID + "," + strCtrlName + "Ctrl.value)";
		try
		{
			LOANMagnifier.showZoomCtrl(
				out,
				strMagnifierName,
				strFormName,
				strPrefix,
				strMainNames,
				strMainFields,
				strReturnNames,
				strReturnFields,
				strExtAccountNo,
				strReturnValues,
				strDisplayNames,
				strDisplayFields,
				nIndex,
				strMainProperty,
				strSQL,
				"ExtAcctNo",
				strNextControls,
				strTitle,
				strFirstTD,
				strSecondTD);
		}
		catch (Exception e)
		{
			Log.print("�ⲿ�������Ϳͻ����˻��Ŵ�[" + strCtrlName + "]�쳣��" + e.toString());
		}
	}
	
	
	/**
	 * �����ⲿ�������Ϳͻ����˻��Ŵ�
	 * @param out
	 * @param lOfficeID ���´�ID
	 * @param lCurrencyID ����ID
	 * @param strFormName ��������
	 * @param strCtrlName �Ŵ����ؼ�����
	 * @param strTitle �Ŵ�����
	 * @param lExtAccountID �ⲿ�˻�ID(��ʶֵ)
	 * @param strExtAccountNo �ⲿ�˻����(��ʶֵ)
	 * @param strFirstTD ��һ��TD������
	 * @param strSecondTD �ڶ���TD������
	 * @param strNextControls ��һ������������ý���Ŀؼ�
	 * @param strRtnClientNameCtrl ����ֵ���˻��ͻ����ƣ���Ӧ�Ŀؼ���
	 * @param strRtnProvinceCtrl ����ֵ�������(ʡ)����Ӧ�Ŀؼ���
	 * @param strRtnCityCtrl ����ֵ�������(��)����Ӧ�Ŀؼ���
	 * @param strRtnBankNameCtrl ����ֵ�����������ƣ���Ӧ�Ŀؼ���
	 */
	public static void createExtAccountCtrl(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lExtAccountID,
		String strExtAccountNo,
		String strFirstTD,
		String strSecondTD,
		String strNextControls,
		String strRtnClientName,
		String strRtnProvince,
		String strRtnCity,
		String strRtnBankName)
	{
		
		String strMagnifierName = "�տ�˻���";
		String strMainProperty = " size='30' maxlength='30'";
		String strPrefix = "";
		
		String[] strMainNames =  {"txtAccountCtrl","sreceiveclientname","sremitinprovince","sremitincity","sremitbank"};
		String[] strMainFields = { "ExtAcctNo", "ExtAcctName", "ExtProvince", "ExtCity", "ExtBankName" };
		String[] strReturnNames = { strCtrlName };
		String[] strReturnFields = { "ExtAcctNo" };
		String[] strReturnValues = { String.valueOf(lExtAccountID)};
		String[] strDisplayNames = {"�˻����","�˻�����","ʡ","��","����������"};
		String[] strDisplayFields = { "ExtAcctNo", "ExtAcctName", "ExtProvince", "ExtCity", "ExtBankName" };
		int nIndex = 0;
		String strSQL = "getExtAcctCurrencySQL(" + lOfficeID + "," +lCurrencyID + "," + strCtrlName + "Ctrl.value)";
		try
		{
			LOANMagnifier.showZoomCtrl(
				out,
				strMagnifierName,
				strFormName,
				strPrefix,
				strMainNames,
				strMainFields,
				strReturnNames,
				strReturnFields,
				strExtAccountNo,
				strReturnValues,
				strDisplayNames,
				strDisplayFields,
				nIndex,
				strMainProperty,
				strSQL,
				"ExtAcctNo",
				strNextControls,
				strTitle,
				strFirstTD,
				strSecondTD);
		}
		catch (Exception e)
		{
			Log.print("�ⲿ�������Ϳͻ����˻��Ŵ�[" + strCtrlName + "]�쳣��" + e.toString());
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
		String[] strMatchValues,
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
            
//            if(Env.getProjectName().equals("cpf"))//���⴦������
//            {
//                strTmp = "cpfLoan";
//            }
//            else
//            {
//                strTmp = "iTreasury-loan";
//            }

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
StringBuffer  aaa=new StringBuffer();
aaa.append("<script language=\"JavaScript\">");
aaa.append("function get" + strMainNames[0] + "(str)");
aaa.append("{");
aaa.append("   var sql = \"\"; ");
aaa.append("   if (str != null && str != \"\") ");
			aaa.append("   {");
			if (strMatchValue == null)
			{
				aaa.append(" sql += \"\"; ");	
			}
			else
			{
				if(strMatchValue.length == 1)
				{
					aaa.append(" sql +=  \" and " + strMatchValue[0] + " like '" + URLEncoder.encode("%") + "\"+str+\"" + URLEncoder.encode("%") + "'\"; ");
				}
				else
				{
					aaa.append(" sql +=  \" and  ( \";");
					for(int i=0;i<strMatchValue.length;i++)
					{
						if(i==0)
						{
							if(strMatchValue[i] != null || !strMatchValue[i].equals("") )
							{
								aaa.append(" sql +=  \"  " + strMatchValue[i] + " like '" + URLEncoder.encode("%") + "\"+str+\"" + URLEncoder.encode("%") + "'\"; ");
							}
							else
							{
								aaa.append(" sql +=  \" 1=1 \"; ");
							}
						}
						else
						{
							if(strMatchValue[i] != null || !strMatchValue[i].equals("") )
							{
								aaa.append(" sql +=  \" or  " + strMatchValue[i] + " like '" + URLEncoder.encode("%") + "\"+str+\"" + URLEncoder.encode("%") + "'\"; ");
							}
							else
							{
								aaa.append(" sql +=  \" 1=1 \"; ");
							}
						}
					}
					aaa.append(" sql +=  \" ) \";");
				}
//				aaa.append(" sql +=  \" and " + strMatchValue + " like '" + URLEncoder.encode("%") + "\"+str+\"" + URLEncoder.encode("%") + "'\";    ");
			}
			aaa.append("   }");
			aaa.append("    ");
			aaa.append("   return sql;    ");
			aaa.append("}");
			aaa.append("</SCRIPT> ");
			out.println(aaa.toString());
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
	 * @param strAccountNo �˻����
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
				//�޸� by kenny(��־ǿ)(2007-03-26) �����˻��ű����������
				//�˻��ŵĶμ����
				String tag = Config.getProperty(ConfigConstant.GLOBAL_ACCOUNTNO_TAG,"-");
				//�˻��ŵĵ�һ�ε�����
				int firstFieldType = Config.getInteger(ConfigConstant.GLOBAL_ACCOUNTNO_FIRSTFIELDTYPE,1);
				//���������lCurrencyID��lOfficeID�������0
				if (firstFieldType == 1) {
					if (lCurrencyID > 0) {
						strAccountNo = NameRef.getCurrencyNoByID(lCurrencyID);
					}
					if (lOfficeID > 0) {
						strAccountNo = strAccountNo + tag + NameRef.getOfficeNoByID(lOfficeID);
					}
				} else if (firstFieldType == 2) {
					if (lOfficeID > 0) {
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
			strParam += "&strMagnifierName=" + URLEncoder.encode(strMagnifierName);
			strParam += "&nIndex=" + nIndex;
			//================================�޸Ŀ�ʼ================================================
			/*�޸� by kenny (��־ǿ) ����˻��Ŷ���������*/
			/*strSQL =
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
					+ ".value)";*/
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
				strParam += "&strDisplayNames=" + URLEncoder.encode(strDisplayNames[i]);
				strParam += "&strDisplayFields=" + strDisplayFields[i];
			}
			//Log.print("strParam = " + strParam);

			/*�޸� by kenny (��־ǿ) ����˻��Ŷ���������*/
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
					+ "/iTreasury-loan/magnifier/ShowMagnifierZoom.jsp?"
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
						+ "<img name=\""
						+ strButtonName
						+ "\" style=\"cursor: hand;\" src='/webloan/image/icon.gif' border=0 onclick=\""
						+ sOnKeydown
						+ "\"></td>");
				//image
			}
			else
			{
				out.println("<td  " + strFirstTD + " >" + strTitle + ":&nbsp;" + "<img name=\"" + strButtonName + "\" style=\"cursor: hand;\" src='/webloan/image/icon.gif' border=0 ></td>");
				//image
			}

			/*�޸� by kenny (��־ǿ) ����˻��Ŷ���������*/
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
	 * ��ʾ���´��Ŵ�
	 * @param out
	 * @param strFormName ��ҳ�������
	 * @param strPrefix �ؼ�����ǰ׺
	 * @param strMainProperty �Ŵ���λ����
	 * @param strNextControls ������һ������
	 * @throws Exception
	 */
	public static void CreateOfficeCtrl(JspWriter out, String strFormName, String strPrefix, String strMainProperty, String strNextControls) throws Exception
	{
		try
		{
			//���SQL��ҳ��
			out.println("<script language=\"javascript\">");
			out.println("/*====================" + URLEncoder.encode("���´��Ŵ�") + "=================*/");
			out.println("function " + strPrefix + "getOfficeSQLForEncapsulation(sOfficeCode)");
			out.println("{");
			out.println("	var sql = \"select distinct a.id as officeid, " + "  a.sCode as OfficeCode, a.sname as officeName " + " from office a where a.nStatusID > 0 \";");
			out.println(" ");
			out.println("	if (sOfficeCode != null && sOfficeCode != \"\") ");
			out.println("	{");
			out.println("		sql += \" and a.sCode like '" + URLEncoder.encode("%") + "\" + sOfficeCode + \"" + URLEncoder.encode("%") + "'\";");
			out.println("	}");
			out.println("	return sql;");
			out.println("}");
			out.println("</SCRIPT> ");
			//String strMagnifierName = URLEncoder.encode("���´�");
			String strMagnifierName = "���´�";
			String[] strMainNames = { "txtOfficeCode", "txtOfficeName" };
			String[] strMainFields = { "officeCode", "officeName" };
			String[] strReturnNames = { "hidOfficeID" };
			String[] strReturnFields = { "officeID" };
			String[] strReturnValues = { "-1" };
			String[] strDisplayNames = { URLEncoder.encode("���´����"), URLEncoder.encode("���´�����")};
			String[] strDisplayFields = { "officeCode", "officeName" };
			int nIndexOffice = 0;
			String strSQL = strPrefix + "getOfficeSQLForEncapsulation(" + strFormName + "." + strPrefix + strMainNames[0] + ".value)";

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
	 * ��ʾ�ͻ��Ŵ�
	 * @param lOfficeID ���´�ID
	 * @param lCurrencyID ����
	 * @param out
	 * @param strFormName ��ҳ�������
	 * @param strPrefix �ؼ�����ǰ׺
	 * @param strMainProperty �Ŵ���λ����
	 * @param strSQL �Ŵ󾵲�ѯSQL���
	 * @param strNextControls ������һ������
	 * @throws Exception
	 */
	public static void CreateClientCtrl(long lOfficeID, long lCurrencyID, JspWriter out, String strFormName, String strPrefix, String strMainProperty, String strNextControls) throws Exception
	{
		try
		{
			//���SQL��ҳ��
			out.println("<script language=\"javascript\">");
			out.println("/*====================" + URLEncoder.encode("�ͻ��Ŵ�") + "=================*/");
			out.println("function " + strPrefix + "getClientSQLForEncapsulation(nOfficeID,sClientCode)");
			out.println("{");
			out.println(
				"	var sql = \"select distinct  a.sCode as ClientCode, " + " a.ID as ClientID,a.sname as ClientName," + " b.ID as OfficeID, b.sCode as OfficeCode, " + " b.sName as OfficeName \";");
			out.println("	sql += \" from client a, office b \";");
			out.println("	sql += \" where a.nofficeid = b.id and a.nStatusID > 0\";");
			out.println(" ");
			out.println("   if (nOfficeID > 0)");
			out.println("	{");
			out.println("		sql += \" and a.nofficeid = \" + nOfficeID; ");
			out.println("	}");
			out.println("	if (sClientCode != null && sClientCode != \"\") ");
			out.println("	{");
			out.println("		sql += \" and a.sCode like '" + URLEncoder.encode("%") + "\" + sClientCode + \"" + URLEncoder.encode("%") + "'\";");
			out.println("	}");
			out.println("	return sql;");
			out.println("}");
			out.println("</SCRIPT> ");
			//String strMagnifierName = URLEncoder.encode("�ͻ�");
			String strMagnifierName = "�ͻ�";
			String[] strMainNames = { "txtClientCode", "txtClientName", "txtOfficeCode", "txtOfficeName", "hidOfficeID" };
			String[] strMainFields = { "clientCode", "clientName", "OfficeCode", "OfficeName", "OfficeID" };
			String[] strReturnNames = { "hidClientID" };
			String[] strReturnFields = { "ClientID" };
			String[] strReturnValues = { "-1" };
			String[] strDisplayNames = { URLEncoder.encode("�ͻ����"), URLEncoder.encode("�ͻ�����")};
			String[] strDisplayFields = { "clientCode", "clientName" };
			int nIndexOffice = 0;
			String strSQL = strPrefix + "getClientSQLForEncapsulation(" + lOfficeID + "," + strFormName + "." + strPrefix + strMainNames[0] + ".value)";
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
                    + "/iTreasury-loan/magnifier/ShowMagnifierZoom.jsp?"
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
	 * 6 ��
	 * 7 ��
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
			System.out.println("strSQL"+strSQL);
			rs = ps.executeQuery();
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
	 * ��ʾҵ�����ͷŴ�
	 * @param lOfficeID ���´�ID
	 * @param lCurrencyID ����
	 * @param out
	 * @param strLoanTypeID ��������
	 * @param strFormName ��ҳ�������
	 * @param strPrefix �ؼ�����ǰ׺
	 * @param strMainProperty �Ŵ���λ����
	 * @param strSQL �Ŵ󾵲�ѯSQL���
	 * @param strNextControls ������һ������
	 * @throws Exception
	 */
	public static void CreateSubLoanTypeCtrl(long lOfficeID, long lCurrencyID, JspWriter out, String strLoanTypeID, String strFormName, String strPrefix, String strMainProperty, String strNextControls) throws Exception
	{
		
		try
		{
			//���SQL��ҳ��
			out.println("<script language=\"javascript\">");
			out.println("/*====================" + URLEncoder.encode("ҵ�����ͷŴ�") + "=================*/");
			out.println("function " + strPrefix + "getSubLoanTypeSQL(nOfficeID,lCurrencyID,lLoanTypeID)");
			out.println("{");
			out.println(
				"	var sql = \"select a.ID, a.LoanTypeID, a.Code, a.Name \";");
			out.println("	sql += \" from Loan_LoanTypeSetting a, Loan_LoanTypeRelation b \";");
			out.println("	sql += \" where a.ID = b.subLoanTypeID and b.currencyid=\"+lCurrencyID+\" and a.statusID = 1 \";");
			out.println(" ");
			out.println("   if (nOfficeID > 0)");
			out.println("	{");
			out.println("		sql += \" and b.officeID = \" + nOfficeID; ");
			out.println("	}");
			out.println("	if (lLoanTypeID > 0) ");
			out.println("	{");
			out.println("		sql += \" and a.loanTypeID = \" + lLoanTypeID; ");
			out.println("   sql+=\"order by code\";");
			out.println("	}");
			out.println("	return sql;");
			out.println("}");
			out.println("</SCRIPT> ");			
			String strMagnifierName = "ҵ������";
			String[] strMainNames = { "txtSubLoanTypeName", "txtSubLoanTypeCode", };
			String[] strMainFields = { "Name", "Code" };
			String[] strReturnNames = { "hidSubLoanTypeID","hidLoanTypeID" };
			String[] strReturnFields = { "ID","LoanTypeID" };
			String[] strReturnValues = { "-1","-1" };
			String[] strDisplayNames = { "ҵ����������", "ҵ�����ͱ���" };
			String[] strDisplayFields = { "Name", "Code" };
			int nIndexOffice = 0;
			String strSQL = strPrefix + "getSubLoanTypeSQL(" + lOfficeID + ","+ lCurrencyID +","  + strFormName + "." + strPrefix + strLoanTypeID + ".value)";
			
			System.out.println("ҵ������ҵ������ҵ������ҵ������strSQL = " + strSQL);
			
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
	 * ��ʾҵ�����ͷŴ�(ר��Ϊ/iTreasuryWEB/webapp/iTreasury-system/approval/view/v001.jsp ʹ��)
	 * @param lOfficeID ���´�ID
	 * @param lCurrencyID ����
	 * @param out
	 * @param strLoanTypeID ��������
	 * @param strFormName ��ҳ�������
	 * @param strPrefix �ؼ�����ǰ׺
	 * @param strMainProperty �Ŵ���λ����
	 * @param strSQL �Ŵ󾵲�ѯSQL���
	 * @param strNextControls ������һ������
	 * @throws Exception
	 */
	public static void CreateSubLoanTypeCtrl4approval(long lOfficeID, long lCurrencyID, JspWriter out, String strLoanTypeID, String strFormName, String strPrefix, String strMainProperty, String strNextControls) throws Exception
	{
		
		try
		{
			//���SQL��ҳ��
			out.println("<script language=\"javascript\">");
			out.println("/*====================" + URLEncoder.encode("ҵ�����ͷŴ�") + "=================*/");
			out.println("function " + strPrefix + "getSubLoanTypeSQL(nOfficeID,lCurrencyID,lLoanTypeID)");
			out.println("{");
			out.println(
				"	var sql = \"select a.ID, a.LoanTypeID, a.Code, a.Name \";");
			out.println("	sql += \" from Loan_LoanTypeSetting a, Loan_LoanTypeRelation b \";");
			out.println("	sql += \" where a.ID = b.subLoanTypeID and b.currencyid=\"+lCurrencyID+\" and a.statusID = 1 \";");
			out.println(" ");
			out.println("   if (nOfficeID > 0)");
			out.println("	{");
			out.println("		sql += \" and b.officeID = \" + nOfficeID; ");
			out.println("	}");
			out.println("	if (lLoanTypeID > 0) ");
			out.println("	{");
			out.println("		sql += \" and a.loanTypeID = \" + lLoanTypeID; ");
			out.println("   sql+=\"order by code\";");
			out.println("	}");
			out.println("	return sql;");
			out.println("}");
			out.println("</SCRIPT> ");			
			String strMagnifierName = "ҵ������";
			String[] strMainNames = { "txtSubLoanTypeName", "txtSubLoanTypeCode", };
			String[] strMainFields = { "Name", "Code" };
			String[] strReturnNames = { "hidSubLoanTypeID","hidLoanTypeID" };
			String[] strReturnFields = { "ID","LoanTypeID" };
			String[] strReturnValues = { "-1","-1" };
			String[] strDisplayNames = { "ҵ����������", "ҵ�����ͱ���" };
			String[] strDisplayFields = { "Name", "Code" };
			int nIndexOffice = 0;
			String strSQL = strPrefix + "getSubLoanTypeSQL(" + lOfficeID + ","+ strFormName +".selCurrency.value,"  + strFormName + "." + strPrefix + strLoanTypeID + ".value)";
			System.out.println("ҵ������ҵ������ҵ������ҵ������strSQL = " + strSQL);
			
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
	public static void CreateApprovalSettingCtrl(long lOfficeID, long lCurrencyID, JspWriter out, long lApprovalID, String strFormName, String strControlName, String strPrefix, String strMainProperty, String strReturnName, String strNextControls) throws Exception
	{
		try
		{
			//���SQL��ҳ��
			out.println("<script language=\"javascript\">");
			out.println("/*====================" + URLEncoder.encode("��ʾ�������Ŵ�") + "=================*/");
			out.println("function " + strPrefix + "getApprovalSettingSQL(nOfficeID,lApprovalID)");
			out.println("{");
			out.println(
				"	var sql = \"select ID, sName \";");
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
			String[] strDisplayNames = { "���������", "����������" };
			String[] strDisplayFields = { "ID", "sName" };
			int nIndexOffice = 0;
			String strSQL = strPrefix + "getApprovalSettingSQL(" + lOfficeID + ","  + lApprovalID + ")";
						
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
	 * �����ͬ�Ŵ�
	 * @param out
	 * @param lOfficeID
	 * @param lCurrencyID
	 * @param strFormName
	 * @param strCtrlName
	 * @param strTitle
	 * @param strNoticeFormNo
	 * @param lNoticeFormID
	 * @param strFirstTD
	 * @param strSecondTD
	 * @param strNextControls
	 * @param noticeType
	 * @param statusid
	 * @param sysTime
	 */
	public static void createLoanContractForm(
			JspWriter out,
			long lOfficeID,
			long lCurrencyID,
			String strFormName,
			String strCtrlName,
			String strTitle,
			String strContractFormNo,
			long lContractFormID,
			String strFirstTD,
			String strSecondTD,
			String strNextControls,
			long contractType,
			long statusid)
		{
		   String strMagnifierName = "�����ͬ���";
		    String strMainProperty = " maxlength='30' value='" + strContractFormNo + "'";			
			String strPrefix = "";
			String[] strMainNames = { "StrContractcode","strContracttype","strContractamount","strClient" };
			String[] strMainFields = { "contractcode" ,"contracttype","contractamount","clientname"};
			String[] strReturnNames = { "contractID" };
			String[] strReturnFields = { "ID" };
			String[] strReturnValues = { String.valueOf(lContractFormID)};
			String[] strDisplayNames = { "�����ͬ���","�����ͬ����","�����ͬ���"};
			String[] strDisplayFields = { "contractcode", "contracttype", "contractamount"};
			String[] strMatchValue = {"contractcode"};
			int nIndex = 0;
			String strSQL = "getLoanContractSQL_test(" + lOfficeID + "," + lCurrencyID + ","+contractType+","+statusid+")";
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
					"",
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
					strSecondTD);
			}
			catch (Exception e)
			{
				Log.print("�Ŵ�[" + strCtrlName + "]�쳣��" + e.toString());
			}
		}
	public static void showZoomCtrlStyleTwo(
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
			String[] strMatchValues,
			String strNextControls,
			String strTitle,
			String strFirstTD,
			String strSecondTD,
			String strStar)
			throws Exception
		{		
			showZoomCtrlStyleTwo(
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
				strStar,
				false,
				false);
		}
	
	/**
	 * 
	 * ���ߣ�
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
public static void showZoomCtrlStyleTwo(
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
	String strStar,
	boolean blnIsOptional,
	boolean blnIsRateCtrl)
	throws Exception
{
	
	System.out.println("***dwj***  " + strStar);
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
		
		String a = "<a href=#><img name=\""
			+ strButtonName
			+ "\" src='/webloan/image/icon.gif' border=0 onclick=\""
			+ sOnKeydown
			+ "\"></a>";
		
		int iPos = -1;
//		��ʾ�ؼ�
		if (iPos == -1)
		{
			out.println(
				"<td "
					+ strFirstTD
					+ " >"
					+ strTitle
					+ "��&nbsp;</td>");
		}
		else
		{
			out.println("<td " + strFirstTD + " >" + strTitle + ":&nbsp;</td>");
		}

		//blnIsOptional,�Ƿ��ѡ�����ժҪ���ֽ�����Ŵ���Ч��
		if (blnIsOptional == true)
		{
			if (blnIsRateCtrl == true)
			{
				out.println("<td " + strSecondTD + " >"+strStar+"<input type=\"text\" name=\"" + strMainNames[0] + "\" value=\"" + strReturnInitValues + "\" class=\"tar\" " + strMainProperty + ">%&nbsp;"+a+"</td>");
			}
			else
			{
				out.println("<td " + strSecondTD + " >"+strStar+"<input type=\"text\" name=\"" + strMainNames[0] + "\" value=\"" + strReturnInitValues + "\" class=\"box\" " + strMainProperty + ">&nbsp;"+a+"</td>");
			}
		}
		else
		{
			if (blnIsRateCtrl == true)
			{
				out.println(
					"<td "
						+ strSecondTD
						+ " >"+strStar+"<input type=\"text\" name=\""
						+ strMainNames[0]
						+ "\" value=\""
						+ strReturnInitValues
						+ "\" class=\"tar\" "
						+ strMainProperty
						+ " onKeyDown=\"if(event.keyCode==13) "
						+ sOnKeydown
						+ "\" onKeyUp=\""
						+ sOnKeyUp
						+ "\">%&nbsp;"+a+"</td>");
			}
			else
			{
				out.println(
					"<td "
						+ strSecondTD
						+ " >"+strStar+"<input type=\"text\" name=\""
						+ strMainNames[0]
						+ "\" value=\""
						+ strReturnInitValues
						+ "\" class=\"box\" "
						+ strMainProperty
						+ " onKeyDown=\"if(event.keyCode==13) "
						+ sOnKeydown
						+ "\" onKeyUp=\""
						+ sOnKeyUp
						+ "\">&nbsp;"+a+"</td>");
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
}