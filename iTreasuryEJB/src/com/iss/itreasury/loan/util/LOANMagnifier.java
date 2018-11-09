/*
 * CLASS   : Magnifier
 * FUNCTION: 放大镜类
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
	private static String ZOOMERRORMSG = "放大镜参数设置错误!"; 
	
	/**
	 * 创建外部（非中油客户）账户放大镜
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lExtAccountID 外部账户ID(初识值)
	 * @param strExtAccountNo 外部账户编号(初识值)
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param strNextControls 下一个（或多个）获得焦点的控件
	 * @param strRtnClientNameCtrl 返回值（账户客户名称）对应的控件名
	 * @param strRtnProvinceCtrl 返回值（汇入地(省)）对应的控件名
	 * @param strRtnCityCtrl 返回值（汇入地(市)）对应的控件名
	 * @param strRtnBankNameCtrl 返回值（汇入行名称）对应的控件名
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
		
		String strMagnifierName = "收款方账户号";
		String strMainProperty = " size='30' maxlength='30'";
		String strPrefix = "";
		
		String[] strMainNames =  {"txtAccountCtrl","sreceiveclientname","sremitinprovince","sremitincity","sremitbank"};
		String[] strMainFields = { "ExtAcctNo", "ExtAcctName", "ExtProvince", "ExtCity", "ExtBankName" };
		String[] strReturnNames = { strCtrlName };
		String[] strReturnFields = { "ExtAcctNo" };
		String[] strReturnValues = { String.valueOf(lExtAccountID)};
		String[] strDisplayNames = {"账户编号","账户名称","省","市","汇入行名称"};
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
			Log.print("外部（非中油客户）账户放大镜[" + strCtrlName + "]异常：" + e.toString());
		}
	}
	
	
	/**
	 * 创建外部（非中油客户）账户放大镜
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lExtAccountID 外部账户ID(初识值)
	 * @param strExtAccountNo 外部账户编号(初识值)
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param strNextControls 下一个（或多个）获得焦点的控件
	 * @param strRtnClientNameCtrl 返回值（账户客户名称）对应的控件名
	 * @param strRtnProvinceCtrl 返回值（汇入地(省)）对应的控件名
	 * @param strRtnCityCtrl 返回值（汇入地(市)）对应的控件名
	 * @param strRtnBankNameCtrl 返回值（汇入行名称）对应的控件名
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
		
		String strMagnifierName = "收款方账户号";
		String strMainProperty = " size='30' maxlength='30'";
		String strPrefix = "";
		
		String[] strMainNames =  {"txtAccountCtrl","sreceiveclientname","sremitinprovince","sremitincity","sremitbank"};
		String[] strMainFields = { "ExtAcctNo", "ExtAcctName", "ExtProvince", "ExtCity", "ExtBankName" };
		String[] strReturnNames = { strCtrlName };
		String[] strReturnFields = { "ExtAcctNo" };
		String[] strReturnValues = { String.valueOf(lExtAccountID)};
		String[] strDisplayNames = {"账户编号","账户名称","省","市","汇入行名称"};
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
			Log.print("外部（非中油客户）账户放大镜[" + strCtrlName + "]异常：" + e.toString());
		}
	}
	
	
	/**
	* 显示普通放大镜
	* @param JspWriter out
	* @param String strMagnifierName 放大镜的名称
	* @param String strFormName 主页面表单名称
	* @param strPrefix strPrefix 控件名称前缀
	* @param String[] strMainNames 放大镜回显栏位值列表
	* @param String[] strMainFields 放大镜回显栏位对应的表格字段
	* @param String[] strReturnNames 放大镜返回值列表(隐含值)
	* @param String[] strReturnFields 放大镜返回值(隐含值)对应的表格字段列表
	* @param String   strReturnInitValues 放大镜回显栏位对应的初始值
	* @param String[] strReturnValues 放大镜返回值(隐含值)对应的初始值
	* @param String[] strDisplayNames 放大镜小窗口显示的栏位名称
	* @param String[] strDisplayFields 放大镜小窗口显示栏位对应的表格字段
	* @param nIndex 确定选择项,从0开始,如果小于0,则默认所有栏位都可选择,如果大于或等于strDisplayNames.length,则没有选择项
	* @param strMainProperty 放大镜栏位属性
	* @param strSQL 放大镜查询SQL语句
	* @param strMatchValue 放大镜要模糊匹配的字段
	* @param strNextControls 设置下一个焦点
	* @param strTitle 栏位标题
	* @param strFirstTD 第一个TD的属性
	* @param strSecondTD 第二个TD的属性 
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
		//2004-11-18 模糊匹配支持多字段匹配
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
	* 显示普通放大镜
	* @param JspWriter out
	* @param String strMagnifierName 放大镜的名称
	* @param String strFormName 主页面表单名称
	* @param strPrefix strPrefix 控件名称前缀
	* @param String[] strMainNames 放大镜回显栏位值列表
	* @param String[] strMainFields 放大镜回显栏位对应的表格字段
	* @param String[] strReturnNames 放大镜返回值列表(隐含值)
	* @param String[] strReturnFields 放大镜返回值(隐含值)对应的表格字段列表
	* @param String   strReturnInitValues 放大镜回显栏位对应的初始值
	* @param String[] strReturnValues 放大镜返回值(隐含值)对应的初始值
	* @param String[] strDisplayNames 放大镜小窗口显示的栏位名称
	* @param String[] strDisplayFields 放大镜小窗口显示栏位对应的表格字段
	* @param nIndex 确定选择项,从0开始,如果小于0,则默认所有栏位都可选择,如果大于或等于strDisplayNames.length,则没有选择项
	* @param strMainProperty 放大镜栏位属性
	* @param strSQL 放大镜查询SQL语句
	* @param strMatchValue 放大镜要模糊匹配的字段
	* @param strNextControls 设置下一个焦点
	* @param strTitle 栏位标题
	* @param strFirstTD 第一个TD的属性
	* @param strSecondTD 第二个TD的属性 
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
		* 显示普通放大镜
		* @param JspWriter out
		* @param String strMagnifierName 放大镜的名称
		* @param String strFormName 主页面表单名称
		* @param strPrefix strPrefix 控件名称前缀
		* @param String[] strMainNames 放大镜回显栏位值列表
		* @param String[] strMainFields 放大镜回显栏位对应的表格字段
		* @param String[] strReturnNames 放大镜返回值列表(隐含值)
		* @param String[] strReturnFields 放大镜返回值(隐含值)对应的表格字段列表
		* @param String   strReturnInitValues 放大镜回显栏位对应的初始值
		* @param String[] strReturnValues 放大镜返回值(隐含值)对应的初始值
		* @param String[] strDisplayNames 放大镜小窗口显示的栏位名称
		* @param String[] strDisplayFields 放大镜小窗口显示栏位对应的表格字段
		* @param nIndex 确定选择项,从0开始,如果小于0,则默认所有栏位都可选择,如果大于或等于strDisplayNames.length,则没有选择项
		* @param strMainProperty 放大镜栏位属性
		* @param strSQL 放大镜查询SQL语句
		* @param strMatchValue 放大镜要模糊匹配的字段
		* @param strNextControls 设置下一个焦点
		* @param strTitle 栏位标题
		* @param strFirstTD 第一个TD的属性
		* @param strSecondTD 第二个TD的属性 
		* @param blnIsOptional 是否是可选项（目前仅对摘要、现金流向放大镜有用）
		* @param blnIsRateCtrl 是否是利率控件
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
			//检查放大镜参数
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

			//检查完毕
			//设置前缀
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
			//弹出窗口的属性
			String sFeatures = null;
			if (strDisplayNames.length < 3)
			{
				sFeatures = "toolbar=no,menubar=no,resizable=no,location=no,width=520,height=450,scrollbars=yes";
			}
			else
			{
				sFeatures = "toolbar=no,menubar=no,resizable=no,location=no,width=624,height=540,scrollbars=yes";
			}
			//生成传递给弹出窗口的参数字符串
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
					//生成数组参数
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
				//生成数组参数
				strParam += "&strDisplayNames=" + URLEncoder.encode(strDisplayNames[i]);
				strParam += "&strDisplayFields=" + strDisplayFields[i];
			}
			//Log.print("strParam = " + strParam);
			//生成查询按钮的事件字符串
            String strTmp ="";
            
			/*  TOCONFIG—TODELETE  */
            /*
             * 产品化不再区分项目 
             * ninh 
             * 2005-03-24
             */
            
//            if(Env.getProjectName().equals("cpf"))//特殊处理中油
//            {
//                strTmp = "cpfLoan";
//            }
//            else
//            {
//                strTmp = "iTreasury-loan";
//            }

			strTmp = "iTreasury-loan";
			
			/*  TOCONFIG—END  */
            
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
			//显示控件
			if (iPos == -1)
			{
				out.println(
					"<td "
						+ strFirstTD
						+ " >"
						+ strTitle
						+ "：&nbsp;"
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

			//blnIsOptional,是否可选项（仅对摘要、现金流向放大镜有效）
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
	 * 显示特殊的放大镜（带多个文本输入框的，如账户放大镜）
	 * @param out
	 * @param strMagnifierName 放大镜的名称
	 * @param strFormName 主页面表单名称
	 * @param strPrefix 控件名称前缀
	 * @param nCaseNumber 显示文本框的数目（目前只支持3，4）
	 * @param strMainNames 放大镜回显栏位值列表
	 * @param strMainFields 放大镜回显栏位对应的表格字段
	 * @param strReturnNames 放大镜返回值列表(隐含值)
	 * @param strReturnFields 放大镜返回值(隐含值)对应的表格字段列表
	 * @param strReturnValues 放大镜返回值(隐含值)对应的初始值
	 * @param strDisplayNames 放大镜小窗口显示的栏位名称
	 * @param strDisplayFields 放大镜小窗口显示栏位对应的表格字段
	 * @param nIndex 确定选择项,从0开始,如果小于0,则默认所有栏位都可选择,如果大于或等于strDisplayNames.length,则没有选择项
	 * @param strMainProperty 放大镜栏位属性
	 * @param strSQL 放大镜查询SQL语句
	 * @param strNextControls 设置下一个焦点
	 * @param strAccountNo 账户编号
	 * @param strTitle 栏位标题
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性 
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
			//检查放大镜参数
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
			//初始默认值
			if (strAccountNo == null || strAccountNo.equals(""))
			{
				/*
				if (lOfficeID > 0)
				{
					strAccountNo = NameRef.getOfficeNoByID(lOfficeID);
				}
				*/
				//修改 by kenny(胡志强)(2007-03-26) 处理账户号编码规则问题
				//账户号的段间符号
				String tag = Config.getProperty(ConfigConstant.GLOBAL_ACCOUNTNO_TAG,"-");
				//账户号的第一段的类型
				int firstFieldType = Config.getInteger(ConfigConstant.GLOBAL_ACCOUNTNO_FIRSTFIELDTYPE,1);
				//正常情况下lCurrencyID、lOfficeID均会大于0
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

			//检查完毕
			//设置前缀
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
			//弹出窗口的属性
			String sFeatures = null;
			if (strDisplayNames.length < 3)
			{
				sFeatures = "toolbar=no,menubar=no,resizable=no,location=no," + "width=520,height=450,scrollbars=yes";
			}
			else
			{
				sFeatures = "toolbar=no,menubar=no,resizable=no,location=no," + "width=624,height=540,scrollbars=yes";
			}
			//生成传递给弹出窗口的参数字符串
			String strParam = "";
			strParam = "strFormName=" + strFormName;
			strParam += "&strMagnifierName=" + URLEncoder.encode(strMagnifierName);
			strParam += "&nIndex=" + nIndex;
			//================================修改开始================================================
			/*修改 by kenny (胡志强) 解决账户号段数的问题*/
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
			//================================修改结束================================================
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
					//生成数组参数
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
				//生成数组参数
				strParam += "&strDisplayNames=" + URLEncoder.encode(strDisplayNames[i]);
				strParam += "&strDisplayFields=" + strDisplayFields[i];
			}
			//Log.print("strParam = " + strParam);

			/*修改 by kenny (胡志强) 解决账户号段数的问题*/
			//取得账户值及定义属性
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
			//生成查询按钮的事件字符串
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
			//显示控件
			if (iPos == -1)
			{
				out.println(
					"<td  "
						+ strFirstTD
						+ "  >"
						+ strTitle
						+ "：&nbsp;"
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

			/*修改 by kenny (胡志强) 解决账户号段数的问题*/
			//组装账户号模糊批匹配的值
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
			//界面显示
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
			//script函数处理
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
			//回写账户各段的值
			String param = "";
			String[] where = new String[accountField];
			String[] value = new String[accountField];
			for (int i=0; i<accountField; i++) {
				if (i>0) {
					param = param+",";
				}
				//组装函数的参数
				param = param+"sValue"+(i+1);
				for (int j=0; j<accountField-i; j++) {
					if (j == 0) {
						where[i] = "sValue"+(j+1)+" != \"\"";
						value[i] = "sValue"+(j+1);
					} else {
						//组装判断条件
						where[i] = where[i]+" && sValue"+(j+1)+" != \"\"";
						//组装相关条件下的值
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
			//将账户编号拆分
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
			//打印脚本程序
			//------------------------回车事件------------------------
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
			//--------------------模糊匹配部分------------------
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
	 * 显示办事处放大镜
	 * @param out
	 * @param strFormName 主页面表单名称
	 * @param strPrefix 控件名称前缀
	 * @param strMainProperty 放大镜栏位属性
	 * @param strNextControls 设置下一个焦点
	 * @throws Exception
	 */
	public static void CreateOfficeCtrl(JspWriter out, String strFormName, String strPrefix, String strMainProperty, String strNextControls) throws Exception
	{
		try
		{
			//输出SQL到页面
			out.println("<script language=\"javascript\">");
			out.println("/*====================" + URLEncoder.encode("办事处放大镜") + "=================*/");
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
			//String strMagnifierName = URLEncoder.encode("办事处");
			String strMagnifierName = "办事处";
			String[] strMainNames = { "txtOfficeCode", "txtOfficeName" };
			String[] strMainFields = { "officeCode", "officeName" };
			String[] strReturnNames = { "hidOfficeID" };
			String[] strReturnFields = { "officeID" };
			String[] strReturnValues = { "-1" };
			String[] strDisplayNames = { URLEncoder.encode("办事处编号"), URLEncoder.encode("办事处名称")};
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
	 * 显示客户放大镜
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种
	 * @param out
	 * @param strFormName 主页面表单名称
	 * @param strPrefix 控件名称前缀
	 * @param strMainProperty 放大镜栏位属性
	 * @param strSQL 放大镜查询SQL语句
	 * @param strNextControls 设置下一个焦点
	 * @throws Exception
	 */
	public static void CreateClientCtrl(long lOfficeID, long lCurrencyID, JspWriter out, String strFormName, String strPrefix, String strMainProperty, String strNextControls) throws Exception
	{
		try
		{
			//输出SQL到页面
			out.println("<script language=\"javascript\">");
			out.println("/*====================" + URLEncoder.encode("客户放大镜") + "=================*/");
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
			//String strMagnifierName = URLEncoder.encode("客户");
			String strMagnifierName = "客户";
			String[] strMainNames = { "txtClientCode", "txtClientName", "txtOfficeCode", "txtOfficeName", "hidOfficeID" };
			String[] strMainFields = { "clientCode", "clientName", "OfficeCode", "OfficeName", "OfficeID" };
			String[] strReturnNames = { "hidClientID" };
			String[] strReturnFields = { "ClientID" };
			String[] strReturnValues = { "-1" };
			String[] strDisplayNames = { URLEncoder.encode("客户编号"), URLEncoder.encode("客户名称")};
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
        * 显示普通放大镜
        * @param JspWriter out
        * @param String strMagnifierName 放大镜的名称
        * @param String strFormName 主页面表单名称
        * @param strPrefix strPrefix 控件名称前缀
        * @param String[] strMainNames 放大镜回显栏位值列表
        * @param String[] strMainFields 放大镜回显栏位对应的表格字段
        * @param String[] strReturnNames 放大镜返回值列表(隐含值)
        * @param String[] strReturnFields 放大镜返回值(隐含值)对应的表格字段列表
        * @param String   strReturnInitValues 放大镜回显栏位对应的初始值
        * @param String[] strReturnValues 放大镜返回值(隐含值)对应的初始值
        * @param String[] strDisplayNames 放大镜小窗口显示的栏位名称
        * @param String[] strDisplayFields 放大镜小窗口显示栏位对应的表格字段
        * @param nIndex 确定选择项,从0开始,如果小于0,则默认所有栏位都可选择,如果大于或等于strDisplayNames.length,则没有选择项
        * @param strMainProperty 放大镜栏位属性
        * @param strSQL 放大镜查询SQL语句
        * @param strMatchValue 放大镜要模糊匹配的字段
        * @param strNextControls 设置下一个焦点
        * @param strTitle 栏位标题
        * @param strFirstTD 第一个TD的属性
        * @param strSecondTD 第二个TD的属性 
        * @param blnIsOptional 是否是可选项（目前仅对摘要、现金流向放大镜有用）
        * @param blnIsRateCtrl 是否是利率控件
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
            //检查放大镜参数
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

            //检查完毕
            //设置前缀
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
            //弹出窗口的属性
            String sFeatures = null;
            if (strDisplayNames.length < 3)
            {
                sFeatures = "toolbar=no,menubar=no,resizable=no,location=no,width=520,height=450,scrollbars=yes";
            }
            else
            {
                sFeatures = "toolbar=no,menubar=no,resizable=no,location=no,width=624,height=540,scrollbars=yes";
            }
            //生成传递给弹出窗口的参数字符串
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
                    //生成数组参数
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
                //生成数组参数
                strParam += "&strDisplayNames=" + URLEncoder.encode(strDisplayNames[i]);
                strParam += "&strDisplayFields=" + strDisplayFields[i];
            }
            //Log.print("strParam = " + strParam);
            //生成查询按钮的事件字符串
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
            //显示控件
            if (iPos == -1)
            {
                out.println(
                    "<td "
                        + strFirstTD
                        + " >"
                        + strTitle
                        + "：&nbsp;"
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
	 * 检测数组中的控件名称 和 对应的数据库字段 的数目 是否匹配
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
	 * 检测数组中的控件名称 和 对应的数据库字段 的数目 是否匹配
	 * @param strNames 控件名称
	 * @param strFields 对应数据库字段
	 * @param strValues 对应初始值
	 * @param bIsAllowNull 是否允许为空
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
	 * 对SQL语句进行处理,如果是完整的查询SQL语句,对所有的"'"字符前加"\".
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
	 * 检查是否为查询SQL语句
	 * @param strSQL
	 * @return ture-是,false-否
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
	 * 从常量中取值
	 * @param nType, 涉及从常量中取值的放大镜的编号
	 * 目前的放大镜编号：
	 * 1 ：合同类型放大镜--演示用
	 * 2 ：审批设置--模块名称放大镜
	 * 3 ：审批设置--业务类型放大镜
	 * 4 ：审批设置--操作放大镜
	 * 5 ：审批设置--状态
	 * 6 ：
	 * 7 ：
	 * 8 ：
	 * @param lID, 对应的常量值
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
				case 1 : //合同类型放大镜--演示用
					Log.print("合同类型放大镜");
					//strReturn = Notes.getCodeName(Notes.CODETYPE_CONTRACT_TYPE,lID);
					break;
				case 2 : //审批设置--模块名称放大镜
					Log.print("模块名称放大镜");
					strReturn = Constant.ModuleType.getName(lID);
					break;
				case 3 : //审批设置--业务类型放大镜
					Log.print("业务类型放大镜放大镜");
					strReturn = Constant.ApprovalLoanType.getName(lID);
					break;
				case 4 : //审批设置--操作放大镜
					Log.print("操作放大镜放大镜");
					strReturn = Constant.ApprovalAction.getName(lID);
					break;
				case 5 : //审批设置--状态
					Log.print("状态放大镜");
					strReturn = Constant.ApprovalStatus.getName(lID);
					break;
				default :
					strReturn = "此值没有从常量中取得，请修改！";
					break;
			}
		}
		catch (Exception ex)
		{
			throw new Exception("发生数据库错误！");
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
				//获取放大镜查询的主字段
				Object[] oMainCols = new Object[strMainFields.length];
				for (int i = 0; i < strMainFields.length; i++)
				{
					//判断是否需要从常量类Constant中取得数据。
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
				//需要返回主页面的字段
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
				//需要在放大镜中显示的字段
				Object[] oDisplayCols = new Object[strDisplayFields.length];
				for (int i = 0; i < strDisplayFields.length; i++)
				{
					//判断是否需要从常量类Constant中取得数据。
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
			throw new Exception("发生数据库错误！");
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
				System.out.println("关闭数据库连接时发生数据库错误！");
			}
		}
		return vResult.size() > 0 ? vResult : null;
	}
	/**
	 * 显示业务类型放大镜
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种
	 * @param out
	 * @param strLoanTypeID 贷款种类
	 * @param strFormName 主页面表单名称
	 * @param strPrefix 控件名称前缀
	 * @param strMainProperty 放大镜栏位属性
	 * @param strSQL 放大镜查询SQL语句
	 * @param strNextControls 设置下一个焦点
	 * @throws Exception
	 */
	public static void CreateSubLoanTypeCtrl(long lOfficeID, long lCurrencyID, JspWriter out, String strLoanTypeID, String strFormName, String strPrefix, String strMainProperty, String strNextControls) throws Exception
	{
		
		try
		{
			//输出SQL到页面
			out.println("<script language=\"javascript\">");
			out.println("/*====================" + URLEncoder.encode("业务类型放大镜") + "=================*/");
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
			String strMagnifierName = "业务类型";
			String[] strMainNames = { "txtSubLoanTypeName", "txtSubLoanTypeCode", };
			String[] strMainFields = { "Name", "Code" };
			String[] strReturnNames = { "hidSubLoanTypeID","hidLoanTypeID" };
			String[] strReturnFields = { "ID","LoanTypeID" };
			String[] strReturnValues = { "-1","-1" };
			String[] strDisplayNames = { "业务类型名称", "业务类型编码" };
			String[] strDisplayFields = { "Name", "Code" };
			int nIndexOffice = 0;
			String strSQL = strPrefix + "getSubLoanTypeSQL(" + lOfficeID + ","+ lCurrencyID +","  + strFormName + "." + strPrefix + strLoanTypeID + ".value)";
			
			System.out.println("业务类型业务类型业务类型业务类型strSQL = " + strSQL);
			
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
	 * 显示业务类型放大镜(专门为/iTreasuryWEB/webapp/iTreasury-system/approval/view/v001.jsp 使用)
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种
	 * @param out
	 * @param strLoanTypeID 贷款种类
	 * @param strFormName 主页面表单名称
	 * @param strPrefix 控件名称前缀
	 * @param strMainProperty 放大镜栏位属性
	 * @param strSQL 放大镜查询SQL语句
	 * @param strNextControls 设置下一个焦点
	 * @throws Exception
	 */
	public static void CreateSubLoanTypeCtrl4approval(long lOfficeID, long lCurrencyID, JspWriter out, String strLoanTypeID, String strFormName, String strPrefix, String strMainProperty, String strNextControls) throws Exception
	{
		
		try
		{
			//输出SQL到页面
			out.println("<script language=\"javascript\">");
			out.println("/*====================" + URLEncoder.encode("业务类型放大镜") + "=================*/");
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
			String strMagnifierName = "业务类型";
			String[] strMainNames = { "txtSubLoanTypeName", "txtSubLoanTypeCode", };
			String[] strMainFields = { "Name", "Code" };
			String[] strReturnNames = { "hidSubLoanTypeID","hidLoanTypeID" };
			String[] strReturnFields = { "ID","LoanTypeID" };
			String[] strReturnValues = { "-1","-1" };
			String[] strDisplayNames = { "业务类型名称", "业务类型编码" };
			String[] strDisplayFields = { "Name", "Code" };
			int nIndexOffice = 0;
			String strSQL = strPrefix + "getSubLoanTypeSQL(" + lOfficeID + ","+ strFormName +".selCurrency.value,"  + strFormName + "." + strPrefix + strLoanTypeID + ".value)";
			System.out.println("业务类型业务类型业务类型业务类型strSQL = " + strSQL);
			
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
	 * 显示审批流放大镜
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种
	 * @param out
	 * @param lApprovalID 审批流ID
	 * @param strFormName 主页面表单名称
	 * @param strControlName 主页面控件名称 
	 * @param strPrefix 控件名称前缀
	 * @param strMainProperty 放大镜栏位属性
	 * @param strReturnName 放大镜隐含返回值
	 * @param strSQL 放大镜查询SQL语句
	 * @param strNextControls 设置下一个焦点
	 * @throws Exception
	 */
	public static void CreateApprovalSettingCtrl(long lOfficeID, long lCurrencyID, JspWriter out, long lApprovalID, String strFormName, String strControlName, String strPrefix, String strMainProperty, String strReturnName, String strNextControls) throws Exception
	{
		try
		{
			//输出SQL到页面
			out.println("<script language=\"javascript\">");
			out.println("/*====================" + URLEncoder.encode("显示审批流放大镜") + "=================*/");
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
			String strMagnifierName = "审批流";
			String[] strMainNames = { strControlName };
			String[] strMainFields = { "sName" };
			if(strReturnName.equals(""))
			{
				strReturnName = "hidApprovalID";
			}
			String[] strReturnNames = { strReturnName };
			String[] strReturnFields = { "ID" };
			String[] strReturnValues = { "-1" };
			String[] strDisplayNames = { "审批流编号", "审批流名称" };
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
	 * 贷款合同放大镜
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
		   String strMagnifierName = "贷款合同编号";
		    String strMainProperty = " maxlength='30' value='" + strContractFormNo + "'";			
			String strPrefix = "";
			String[] strMainNames = { "StrContractcode","strContracttype","strContractamount","strClient" };
			String[] strMainFields = { "contractcode" ,"contracttype","contractamount","clientname"};
			String[] strReturnNames = { "contractID" };
			String[] strReturnFields = { "ID" };
			String[] strReturnValues = { String.valueOf(lContractFormID)};
			String[] strDisplayNames = { "贷款合同编号","贷款合同类型","贷款合同金额"};
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
				Log.print("放大镜[" + strCtrlName + "]异常：" + e.toString());
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
	 * 作者：
	* 显示普通放大镜
	* @param JspWriter out
	* @param String strMagnifierName 放大镜的名称
	* @param String strFormName 主页面表单名称
	* @param strPrefix strPrefix 控件名称前缀
	* @param String[] strMainNames 放大镜回显栏位值列表
	* @param String[] strMainFields 放大镜回显栏位对应的表格字段
	* @param String[] strReturnNames 放大镜返回值列表(隐含值)
	* @param String[] strReturnFields 放大镜返回值(隐含值)对应的表格字段列表
	* @param String   strReturnInitValues 放大镜回显栏位对应的初始值
	* @param String[] strReturnValues 放大镜返回值(隐含值)对应的初始值
	* @param String[] strDisplayNames 放大镜小窗口显示的栏位名称
	* @param String[] strDisplayFields 放大镜小窗口显示栏位对应的表格字段
	* @param nIndex 确定选择项,从0开始,如果小于0,则默认所有栏位都可选择,如果大于或等于strDisplayNames.length,则没有选择项
	* @param strMainProperty 放大镜栏位属性
	* @param strSQL 放大镜查询SQL语句
	* @param strMatchValue 放大镜要模糊匹配的字段
	* @param strNextControls 设置下一个焦点
	* @param strTitle 栏位标题
	* @param strFirstTD 第一个TD的属性
	* @param strSecondTD 第二个TD的属性 
	* @param blnIsOptional 是否是可选项（目前仅对摘要、现金流向放大镜有用）
	* @param blnIsRateCtrl 是否是利率控件
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
		//检查放大镜参数
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

		//检查完毕
		//设置前缀
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
		//弹出窗口的属性
		String sFeatures = null;
		if (strDisplayNames.length < 3)
		{
			sFeatures = "toolbar=no,menubar=no,resizable=no,location=no,width=520,height=450,scrollbars=yes";
		}
		else
		{
			sFeatures = "toolbar=no,menubar=no,resizable=no,location=no,width=624,height=540,scrollbars=yes";
		}
		//生成传递给弹出窗口的参数字符串
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
				//生成数组参数
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
			//生成数组参数
			strParam += "&strDisplayNames=" + URLEncoder.encode(strDisplayNames[i]);
			strParam += "&strDisplayFields=" + strDisplayFields[i];
		}
		//Log.print("strParam = " + strParam);
		//生成查询按钮的事件字符串
        String strTmp ="";
        
		/*  TOCONFIG—TODELETE  */
        /*
         * 产品化不再区分项目 
         * ninh 
         * 2005-03-24
         */
        
//        if(Env.getProjectName().equals("cpf"))//特殊处理中油
//        {
//            strTmp = "cpfLoan";
//        }
//        else
//        {
//            strTmp = "iTreasury-loan";
//        }
        
		strTmp = "iTreasury-loan";
		
		/*  TOCONFIG—END  */
        
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
//		显示控件
		if (iPos == -1)
		{
			out.println(
				"<td "
					+ strFirstTD
					+ " >"
					+ strTitle
					+ "：&nbsp;</td>");
		}
		else
		{
			out.println("<td " + strFirstTD + " >" + strTitle + ":&nbsp;</td>");
		}

		//blnIsOptional,是否可选项（仅对摘要、现金流向放大镜有效）
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