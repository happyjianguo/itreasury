package com.iss.itreasury.ebank.util;

/**
 * @author yzhang
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */

import java.sql.*;
import java.util.*;
import java.net.URLEncoder;
import com.iss.itreasury.settlement.setting.dao.Sett_FilialeAccountSetDAO;
import com.iss.itreasury.settlement.setting.dataentity.FilialeAccountInfo;
import com.iss.itreasury.settlement.util.*;
import com.iss.itreasury.dataentity.OfficeInfo;
import com.iss.itreasury.ebank.approval.dataentity.InutParameterInfo;
import com.iss.itreasury.ebank.obaccountinfo.bizlogic.OBAccountQueryWhere;
import com.iss.itreasury.ebank.obaccountinfo.dataentity.OBAccountQueryAmountInfo;
import com.iss.itreasury.ebank.obaccountinfo.bizlogic.OBAccountQueryAmountBiz;
import com.iss.itreasury.ebank.obfinanceinstr.bizlogic.OBFinanceInstrBiz;
import com.iss.itreasury.loan.util.*;
import com.iss.itreasury.util.*;
import javax.servlet.jsp.JspWriter;

import com.iss.itreasury.settlement.util.NameRef;

import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.ConfigConstant;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.Log4j;
import com.iss.itreasury.util.Constant;

public class OBHtmlCom
{
	private static String ZOOMERRORMSG = "放大镜参数设置错误!";

	private static Log4j log4j = null;

	public OBHtmlCom()
	{
		log4j = new Log4j(Constant.ModuleType.EBANK, this);
	}

	/**
	 * 通用下拉列表显示控件(带nextfield)
	 * @param out 输出
	 * @param strFieldName 域的名称
	 * @param strID ID的名称
	 * @param strName 标中字段的名称
	 * @param strTable 表名
	 * @param strCondition 条件
	 * @param lData 数据
	 * @param strDesc  其他属性
	 * Created by Hally Zhang,2002-01-31
	 *
	 */
	public static void showCommonListControl(JspWriter out, String strFieldName, String strID, String strName, String strTable, String strCondition, long lData, String strDesc) throws Exception
	{
		long lResult = -1;
		ResultSet rs = null;
		PreparedStatement ps = null;
		Connection con = null;
		long lTemplateID = 0;
		long lLobID = -1;
		Vector v = new Vector();
		try
		{
			out.println("<select " + strDesc + " class=\"select\" name=\"" + strFieldName + "\">");
			out.println("<option value=\"-1\"></option>");
			con = Database.getConnection();
			String strTmp = new String();
			strTmp = " select " + strID + " id, " + strName + " name from " + strTable + " " + strCondition;
			//System.out.println(strTmp);
			ps = con.prepareStatement(strTmp);
			rs = ps.executeQuery();
			String strSelected = "";
			while (rs.next())
			{
				long lSupplierID = rs.getLong("ID");
				if (lSupplierID == lData)
				{
					strSelected = "selected";
				}
				out.println("<option value=\"" + rs.getLong("id") + "\"" + strSelected + ">" + rs.getString("name") + "</option>");
				strSelected = "";
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
			out.println("</select>");
		}
		catch (Exception e)
		{
			System.out.println(" can not select OFFICE, because " + e);
			throw e;
		}
		finally
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
			if (con != null)
			{
				con.close();
				con = null;
			}
		}
	}

	/**
	 * 显示签认人下拉框代码控件
	 * @param strFieldName 域的名称
	 * @param lCodeID 代码，如果>0表示选中这个控件
	 *JspWriter out,
	 */
	public static void showSignUserControl(JspWriter out, String strFieldName, long lCodeID, String strFieldDesc, long lClientID, long lOfficeID, long lCurrencyID, long lUserID) throws Exception
	{

		PreparedStatement ps = null; //
		ResultSet rs = null; //
		Connection conn = null; //
		String strSQL = null; //查询串
		try
		{
			out.println("<select  class=\"select\"  name=\"" + strFieldName + "\"  " + strFieldDesc + ">");
			out.println("<option  value=\"-1\">不适用</option>");
			conn = Database.getConnection(); //数据库连接
			strSQL = " SELECT id,sname,sloginno FROM OB_user ";
			strSQL += " WHERE nclientid = " + lClientID ;
			strSQL += " AND (ncurrencyid = " + lCurrencyID + " OR nCurrencyID=0) ";
			strSQL += " AND nstatus = " + Constant.RecordStatus.VALID;
			strSQL += " AND nOfficeID= " + lOfficeID;
			strSQL += " union ";
			strSQL += " SELECT distinct u.ID id,u.NAME sname,u.LOGINNAME sloginno ";
			strSQL += " FROM MG_USER U ";
			strSQL += " INNER JOIN MG_R_USER_DUTY_AGENCY T1 ON U.ID = T1.USERID ";
			strSQL += " INNER JOIN MG_R_DUTY_AGENCY T2 ON T2.ID = T1.AGENCY_DUTY_ID ";
			strSQL += " INNER JOIN MG_AGENCY T3 ON T3.ID = T2.AGENCYID ";
			strSQL += " WHERE U.FLAG = '1' ";
			strSQL += " AND U.STATUS = '1' ";
			strSQL += " AND T3.FLAG = '1' ";
			strSQL += " AND T3.STATUS = '1' ";
			strSQL += " AND u.AGENCY_TYPE = '1002' ";
			strSQL += " AND t3.ID = '"+lClientID+"' ";
			strSQL += " AND (u.SCURRENCYID = '"+lCurrencyID+"' OR nCurrencyID='0')";
			ps = conn.prepareStatement(strSQL);
			rs = ps.executeQuery();
			while (rs.next())
			{
				String strSelected = "";
				if (rs.getLong("id") == lCodeID)
				{
					strSelected = "  selected  ";
				}
				out.println("<option  value=\"" + String.valueOf(rs.getLong("id")) + "\"" + strSelected + ">" + rs.getString("sName") + "</option>");
			}
			out.println("</select>");
			rs.close();
			ps.close();
			rs = null;
			ps = null;
			conn.close();
			conn = null;
		}
		catch (Exception exp)
		{
			throw exp;
		}
		finally
		{
			if (rs != null)
			{
				try
				{
					rs.close();
				}
				catch (Exception exp)
				{
					;
				}
			}
			if (ps != null)
			{
				try
				{
					ps.close();
				}
				catch (Exception exp)
				{
					;
				}
			}
			if (conn != null)
			{
				try
				{
					conn.close();
				}
				catch (Exception exp)
				{
					;
				}
			}
		}
	}

	/**
	 * 显示签认人下拉框代码控件
	 * @param strFieldName 域的名称
	 * @param lCodeID 代码，如果>0表示选中这个控件
	 *JspWriter out,
	 */
	public static void showBillTypeControl(JspWriter out, String strFieldName, long lCodeID, String strFieldDesc) throws Exception
	{
		try
		{
			showCommonListControl(out, strFieldName, "ID", "sDesc", "sett_BankBillType", "", lCodeID, strFieldDesc);
		}
		catch (Exception e)
		{
			throw e;
		}
	}

	/**
	 * 显示银行账号下拉框代码控件
	 * @param strFieldName 域的名称
	 * @param lCodeID 代码，如果>0表示选中这个控件
	 *JspWriter out,
	 *lClientID 登录客户ID
	 *lCurrencyID 登录币种
	 *lUserID 登录用户ID,如果为-1，则显示全部
	 */
	public static void showPayerBankCodeControl1(JspWriter out, String strFieldName, long lAccountID, String strFieldDesc, long lClientID, long lCurrencyID, long lUserID,long lOfficeID) throws Exception
	{

		PreparedStatement ps = null; //
		ResultSet rs = null; //
		Connection conn = null; //
		String strSQL = null; //查询串
		try
		{
			out.println("<select  class=\"select\"  name=\"" + strFieldName + "\"  " + strFieldDesc + ">");
			out.println("<option value=\"\"></option>");
			conn = Database.getConnection(); //数据库连接

			if (lUserID == -1)
			{
				strSQL =
					"select distinct a.ID ,a.saccountno "
						+ " from SETT_Account a "
						+ " where a.nStatusID=1 and a.nclientid = "
						+ lClientID
						+ "  and a.ncurrencyid = "
						+ lCurrencyID
						+" and a.nOfficeID="+lOfficeID
						+ " "
						+ " order by a.saccountno";
			}
			else
			{
				strSQL =
					"select distinct a.ID ,a.saccountno "
						+ " from SETT_Account a,ob_accountownedbysa obau "
						+ " where obau.sAccountNo=a.sAccountNo(+) and a.nStatusID=1 and a.nclientid = "
						+ lClientID
						+ "  and a.ncurrencyid = "
						+ lCurrencyID
						+ " and obau.nsaid = "
						+ lUserID
						+" and a.nOfficeID="+lOfficeID
						+ " "
						+ " order by a.saccountno";
			}

			Log.print("showPayerBankCodeControl1 = " + strSQL);
			ps = conn.prepareStatement(strSQL);
			rs = ps.executeQuery();

			while (rs.next())
			{
				String strSelected = "";
				if (rs.getLong("id") == lAccountID)
				{
					strSelected = "  selected  ";
				}
				String show = rs.getString("saccountno");

				out.println("<option  value=\"" + String.valueOf(rs.getString("id")) + "\"" + strSelected + ">" + NameRef.getNoLineAccountNo(show) + "</option>");
			}
			out.println("</select>");
			rs.close();
			ps.close();
			rs = null;
			ps = null;
			conn.close();
			conn = null;
		}
		catch (Exception exp)
		{
			throw exp;
		}
		finally
		{
			if (rs != null)
			{
				try
				{
					rs.close();
				}
				catch (Exception exp)
				{
					;
				}
			}
			if (ps != null)
			{
				try
				{
					ps.close();
				}
				catch (Exception exp)
				{
					;
				}
			}
			if (conn != null)
			{
				try
				{
					conn.close();
				}
				catch (Exception exp)
				{
					;
				}
			}
		}
	}
	
	/**
	 * 显示银行账号下拉框代码控件
	 * @param strFieldName 域的名称
	 * @param lCodeID 代码，如果>0表示选中这个控件
	 *JspWriter out,
	 *lClientID 登录客户ID
	 *lCurrencyID 登录币种
	 *lUserID 登录用户ID,如果为-1，则显示全部
	 *lSAID管理员ID
	 */
	public static void showPayerBankCodeControl1(JspWriter out, String strFieldName, long lAccountID, String strFieldDesc, long lClientID, long lCurrencyID, long lUserID,long lOfficeID,long lSAID) throws Exception
	{

		PreparedStatement ps = null; //
		ResultSet rs = null; //
		Connection conn = null; //
		String strSQL = null; //查询串
		try
		{
			out.println("<select  class=\"select\"  name=\"" + strFieldName + "\"  " + strFieldDesc + ">");
			out.println("<option value=\"\"></option>");
			conn = Database.getConnection(); //数据库连接

			if(lOfficeID == -1)
			{
				if (lUserID == -1)
				{
					strSQL =
						"select distinct a.ID ,a.saccountno "
							+ " from SETT_Account a "
							+ " where a.nStatusID=1 and a.nclientid = "
							+ lClientID
							+ "  and a.ncurrencyid = "
							+ lCurrencyID
							+ " "
							+ " order by a.saccountno";
				}
				else if(lSAID==lUserID)
				{
					strSQL =
						"select distinct a.ID ,a.saccountno "
							+ " from SETT_Account a,ob_accountownedbysa obau "
							+ " where obau.sAccountNo=a.sAccountNo(+) and a.nStatusID=1 and a.nclientid = "
							+ lClientID
							+ "  and a.ncurrencyid = "
							+ lCurrencyID
							+ " and obau.nsaid = "
							+ lUserID
							+ " "
							+ " order by a.saccountno";
				}else{
					strSQL =
						"select distinct a.ID ,a.saccountno "
							+ " from SETT_Account a,ob_accountownedbyuser obau "
							+ " where obau.sAccountNo=a.sAccountNo(+) and a.nStatusID=1 and a.nclientid = "
							+ lClientID
							+ "  and a.ncurrencyid = "
							+ lCurrencyID
							+ " and obau.nuserid = "
							+ lUserID
							+ " "
							+ " order by a.saccountno";
				}
				
			}else
			{
				if (lUserID == -1)
				{
					strSQL =
						"select distinct a.ID ,a.saccountno "
							+ " from SETT_Account a "
							+ " where a.nStatusID=1 and a.nclientid = "
							+ lClientID
							+ "  and a.ncurrencyid = "
							+ lCurrencyID
							+" and a.nOfficeID="+lOfficeID
							+ " "
							+ " order by a.saccountno";
				}
				else if(lSAID==lUserID)
				{
					strSQL =
						"select distinct a.ID ,a.saccountno "
							+ " from SETT_Account a,ob_accountownedbysa obau "
							+ " where obau.sAccountNo=a.sAccountNo(+) and a.nStatusID=1 and a.nclientid = "
							+ lClientID
							+ "  and a.ncurrencyid = "
							+ lCurrencyID
							+ " and obau.nsaid = "
							+ lUserID
							+" and a.nOfficeID="+lOfficeID
							+ " "
							+ " order by a.saccountno";
				}else{
					strSQL =
						"select distinct a.ID ,a.saccountno "
							+ " from SETT_Account a,ob_accountownedbyuser obau "
							+ " where obau.sAccountNo=a.sAccountNo(+) and a.nStatusID=1 and a.nclientid = "
							+ lClientID
							+ "  and a.ncurrencyid = "
							+ lCurrencyID
							+ " and obau.nuserid = "
							+ lUserID
							+" and a.nOfficeID="+lOfficeID
							+ " "
							+ " order by a.saccountno";
				}
			}

			Log.print("showPayerBankCodeControl1 = " + strSQL);
			ps = conn.prepareStatement(strSQL);
			rs = ps.executeQuery();

			while (rs.next())
			{
				String strSelected = "";
				if (rs.getLong("id") == lAccountID)
				{
					strSelected = "  selected  ";
				}
				String show = rs.getString("saccountno");

				out.println("<option  value=\"" + String.valueOf(rs.getString("id")) + "\"" + strSelected + ">" + NameRef.getNoLineAccountNo(show) + "</option>");
			}
			out.println("</select>");
			rs.close();
			ps.close();
			rs = null;
			ps = null;
			conn.close();
			conn = null;
		}
		catch (Exception exp)
		{
			throw exp;
		}
		finally
		{
			if (rs != null)
			{
				try
				{
					rs.close();
				}
				catch (Exception exp)
				{
					;
				}
			}
			if (ps != null)
			{
				try
				{
					ps.close();
				}
				catch (Exception exp)
				{
					;
				}
			}
			if (conn != null)
			{
				try
				{
					conn.close();
				}
				catch (Exception exp)
				{
					;
				}
			}
		}
	}


	/**
	 * 汇款方式下拉列表显示控件(带nextfield)
	 * @param out 输出
	 * @param strFieldName 域的名称
	 * @param lData 数据
	 * @param strDesc  其他属性
	 *
	 */
	public static void showRemitTypeListControl(JspWriter out, String strFieldName, long lData, String strDesc,long lOfficeID,long lCurrencyID) throws Exception
	{
		try
		{
			out.println("<select " + strDesc + " class=\"select\" name=\"" + strFieldName + "\">");
			out.println("<option value=\"-1\"></option>");
			long[] lTemp = null;
			
			String strSelected = "";
			lTemp = OBConstant.SettRemitType.getAllCode(lOfficeID,lCurrencyID);
			
			for (int i = 0; i < lTemp.length; i++)
			{

				if (lTemp[i] == lData)
				{
					strSelected = "selected";
				}
				else
				{
					strSelected = "";
				}
				out.println("<option value=\"" + lTemp[i] + "\"" + strSelected + ">" + OBConstant.SettRemitType.getName(lTemp[i]) + "</option>");
				strSelected = "";
			}
			out.println("</select>");
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
	}
	/**
	 * @deprecated
	 * 请不要再使用该方法显示汇款方式下拉列表，可使用com.iss.itreasury.ebank.util.OBConstant.SettRemitType.showList代替
	 * 
	 * 汇款方式下拉列表显示控件(带nextfield)
	 * @param out 输出
	 * @param strFieldName 域的名称
	 * @param lData 数据
	 * @param strDesc  其他属性
	 *
	 */
	public static void showRemitTypeListControlZj(JspWriter out, String strFieldName, long lData, String strDesc,long lOfficeID,long lCurrencyID) throws Exception
	{
		try
		{
			out.println("<select " + strDesc + "  class=\"select\" name=\"" + strFieldName + "\">");
			out.println("<option value=\"-1\"></option>");
			long[] lTemp = {OBConstant.SettRemitType.SELF,OBConstant.SettRemitType.BANKPAY,OBConstant.SettRemitType.INTERNALVIREMENT  , OBConstant.SettRemitType.BANKPAY_DOWNTRANSFER };
			
			String strSelected = "";
			
			for (int i = 0; i < lTemp.length; i++)
			{

				if (lTemp[i] == lData&&(i==1||i==2))
				{
					strSelected = "selected";
				}
				else
				{
					strSelected = "";
				}
				if(i==1||i==2){
				out.println("<option value=\"" + lTemp[i] + "\"" + strSelected + ">" + OBConstant.SettRemitType.getName(lTemp[i]) + "</option>");
				strSelected = "";
				}
			}
			out.println("</select>");
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
	}

	/**
		 * 汇款方式下拉列表显示控件(带nextfield)
		 * @param out 输出
		 * @param strFieldName 域的名称
		 * @param lData 数据
		 * @param strDesc  其他属性
		 *
		 */
	public static void showRemitTypeListControl2(JspWriter out, String strFieldName, long lData, String strDesc ) throws Exception
	{
		try
		{
			out.println("<select " + strDesc + " class=\"select\" name=\"" + strFieldName + "\">");
			out.println("<option value=\"-1\"></option>");

			String strSelected = "";
			long[] lTemp = { OBConstant.SettRemitType.BANKPAY };
			for (int i = 0; i < lTemp.length; i++)
			{

				if (lTemp[i] == lData)
				{
					strSelected = "selected";
				}
				else
				{
					strSelected = "";
				}
				out.println("<option value=\"" + lTemp[i] + "\"" + strSelected + ">" + OBConstant.SettRemitType.getName(lTemp[i]) + "</option>");
				strSelected = "";
			}
			out.println("</select>");
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
	}
	/**
	 * 汇款方式下拉列表显示控件(带nextfield)
	 * @param out 输出
	 * @param strFieldName 域的名称
	 * @param lData 数据
	 * @param strDesc  其他属性
	 *
	 */
public static void showRemitTypeListControlZj2(JspWriter out, String strFieldName, long lData, String strDesc ) throws Exception
{
	try
	{
		out.println("<select " + strDesc + "  class=\"select\" name=\"" + strFieldName + "\">");
		out.println("<option value=\"-1\"></option>");

		String strSelected = "";
		long[] lTemp = { OBConstant.SettRemitType.BANKPAY };
		for (int i = 0; i < lTemp.length; i++)
		{

			if (lTemp[i] == lData&&(i==1||i==2))
			{
				strSelected = "selected";
			}
			else
			{
				strSelected = "";
			}
			if((i==1||i==2)){
			out.println("<option value=\"" + lTemp[i] + "\"" + strSelected + ">" + OBConstant.SettRemitType.getName(lTemp[i]) + "</option>");
			strSelected = "";
			}
		}
		out.println("</select>");
	}
	catch (Exception e)
	{
		log4j.error(e.toString());
		throw new IException("Gen_E001");
	}
}

	/**
	 * 汇款方式下拉列表显示控件(带nextfield)
	 * @param out 输出
	 * @param strFieldName 域的名称
	 * @param lData 数据
	 * @param strDesc  其他属性
	 *
	 */
	public static void showRemitTypeListControl1(JspWriter out, String strFieldName, long lData, String strDesc) throws Exception
	{
		try
		{
			out.println("<select class=\"select\" " + strDesc + " class=\"select\" name=\"" + strFieldName + "\" disabled=\"disabled\">");
			out.println("<option value=\"-1\"></option>");

			String strSelected = "";
			long[] lTemp = { OBConstant.SettRemitType.BANKPAY, OBConstant.SettRemitType.INTERNALVIREMENT };
			for (int i = 0; i < lTemp.length; i++)
			{
				if( lTemp[i]!=102){
				if (lTemp[i] == lData)
				{
					strSelected = "selected";
				}
				else
				{
					strSelected = "";
				}
				out.println("<option value=\"" + lTemp[i] + "\"" + strSelected + ">" + OBConstant.SettRemitType.getName(lTemp[i]) + "</option>");
				strSelected = "";
				}
			}
			out.println("</select>");
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
	}

	/**
	 * 汇款方式下拉列表显示控件(带nextfield)
	 * @param out 输出
	 * @param strFieldName 域的名称
	 * @param lData 数据
	 * @param strDesc  其他属性
	 *
	 */
	public static void showRemitTypeListControl3(JspWriter out, String strFieldName, long lData, String strDesc,long lOfficeID,long lCurrencyID) throws Exception
	{
		try
		{
			out.println("<select class=\"select\" " + strDesc + " class=\"select\" name=\"" + strFieldName + "\">");
			out.println("<option value=\"-1\"></option>");
			long[] lTemp = null;
			
			String strSelected = "";
			lTemp = OBConstant.SettRemitType.getAllCode(lOfficeID,lCurrencyID);
			
			for (int i = 0; i < lTemp.length; i++)
			{

				if (lTemp[i] == lData)
				{
					strSelected = "selected";
				}
				else
				{
					strSelected = "";
				}
				out.println("<option value=\"" + lTemp[i] + "\"" + strSelected + ">" + OBConstant.SettRemitType.getName(lTemp[i]) + "</option>");
				strSelected = "";
			}
			out.println("<option value=\"11\">测试</option>");
			out.println("</select>");
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
	}
	
	/**
		 * 业务复核业务类型下拉列表显示控件(带nextfield)
		 * @param out 输出
		 * @param strFieldName 域的名称
		 * @param lData 数据
		 * @param strDesc  其他属性
		 *
		 */
	public static void showQueryTypeListControl1(JspWriter out, String strFieldName, long lData, String strDesc, boolean isNeedAll) throws Exception
	{
		try
		{
			out.println("<select class=\"select\" " + strDesc + " class=\"select\" name=\"" + strFieldName + "\">");

			String strSelected = "";
			if (isNeedAll == false)
			{
				long[] lTemp=null;
					lTemp=new long[]	
					{
						OBConstant.QueryInstrType.ALL ,
						OBConstant.QueryInstrType.CAPTRANSFER,
						//OBConstant.QueryInstrType.CHILDCAPTRANSFER,
						OBConstant.QueryInstrType.OPENFIXDEPOSIT,
//						OBConstant.QueryInstrType.BILLOPENFIXDEPOSIT,
						OBConstant.QueryInstrType.FIXEDTOCURRENTTRANSFER,
						OBConstant.QueryInstrType.OPENNOTIFYACCOUNT,
						OBConstant.QueryInstrType.NOTIFYDEPOSITDRAW,
						//OBConstant.QueryInstrType.TRUSTLOANRECEIVE,
//						OBConstant.QueryInstrType.CONSIGNLOANRECEIVE,
						//OBConstant.QueryInstrType.INTERESTFEEPAYMENT,
						OBConstant.QueryInstrType.DRIVEFIXDEPOSIT,
					//	OBConstant.QueryInstrType.CHANGEFIXDEPOSIT,
						//OBConstant.QueryInstrType.YTLOANRECEIVE 
					};
				
				for (int i = 0; i < lTemp.length; i++)
				{
					if (lTemp[i] == lData)
					{
						strSelected = "selected";
					}
					else
					{
						strSelected = "";
					}
					out.println("<option value=\"" + lTemp[i] + "\"" + strSelected + ">" + OBConstant.QueryInstrType.getName(lTemp[i]) + "</option>");
					strSelected = "";
				}
				out.println("</select>");
			}
			else if (isNeedAll == true)
			{
				long[] lTemp=null;
					lTemp=new long[]	
					{
						OBConstant.QueryInstrType.ALL ,
						OBConstant.QueryInstrType.CAPTRANSFER,
						//OBConstant.QueryInstrType.CHILDCAPTRANSFER,
						OBConstant.QueryInstrType.OPENFIXDEPOSIT,
//						OBConstant.QueryInstrType.BILLOPENFIXDEPOSIT,
						OBConstant.QueryInstrType.FIXEDTOCURRENTTRANSFER,
						OBConstant.QueryInstrType.OPENNOTIFYACCOUNT,
						OBConstant.QueryInstrType.NOTIFYDEPOSITDRAW,
						//OBConstant.QueryInstrType.TRUSTLOANRECEIVE,
//						OBConstant.QueryInstrType.CONSIGNLOANRECEIVE,
						//OBConstant.QueryInstrType.INTERESTFEEPAYMENT,
						OBConstant.QueryInstrType.DRIVEFIXDEPOSIT,
					//	OBConstant.QueryInstrType.CHANGEFIXDEPOSIT,
						//OBConstant.QueryInstrType.YTLOANRECEIVE 
					};
				for (int i = 0; i < lTemp.length; i++)
				{
					if (lTemp[i] == lData)
					{
						strSelected = "selected";
					}
					else
					{
						strSelected = "";
					}
					out.println("<option value=\"" + lTemp[i] + "\"" + strSelected + ">" + OBConstant.QueryInstrType.getName(lTemp[i]) + "</option>");
					strSelected = "";
				}
				out.println("</select>");
			}
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
	}

	/**
	 * 业务复核业务类型下拉列表显示控件(带nextfield)
	 * @param out 输出
	 * @param strFieldName 域的名称
	 * @param lData 数据
	 * @param strDesc  其他属性
	 *
	 */
	public static void showQueryTypeListControl(JspWriter out, String strFieldName, long lData, String strDesc, boolean isNeedAll) throws Exception
	{
		try
		{
			out.println("<select class=\"select\" " + strDesc + "  name=\"" + strFieldName + "\">");

			String strSelected = "";
			if (isNeedAll == false)
			{
				long[] lTemp=null;
					lTemp=new long[]	
					{
						OBConstant.QueryInstrType.CAPTRANSFER,
						//OBConstant.QueryInstrType.CHILDCAPTRANSFER,
						OBConstant.QueryInstrType.OPENFIXDEPOSIT,
//						OBConstant.QueryInstrType.BILLOPENFIXDEPOSIT,
						OBConstant.QueryInstrType.FIXEDTOCURRENTTRANSFER,
						OBConstant.QueryInstrType.OPENNOTIFYACCOUNT,
						OBConstant.QueryInstrType.NOTIFYDEPOSITDRAW,
						//OBConstant.QueryInstrType.TRUSTLOANRECEIVE,
//						OBConstant.QueryInstrType.CONSIGNLOANRECEIVE,
						//OBConstant.QueryInstrType.INTERESTFEEPAYMENT,
						  OBConstant.QueryInstrType.DRIVEFIXDEPOSIT,
						//OBConstant.QueryInstrType.CHANGEFIXDEPOSIT,
						//OBConstant.QueryInstrType.YTLOANRECEIVE 
					};
				for (int i = 0; i < lTemp.length; i++)
				{
					if (lTemp[i] == lData)
					{
						strSelected = "selected";
					}
					else
					{
						strSelected = "";
					}
					out.println("<option value=\"" + lTemp[i] + "\"" + strSelected + ">" + OBConstant.QueryInstrType.getName(lTemp[i]) + "</option>");
					strSelected = "";
				}
				out.println("</select>");
			}
			else if (isNeedAll == true)
			{
				long[] lTemp=null;
					lTemp=new long[]	
					{
						OBConstant.QueryInstrType.ALL ,
						OBConstant.QueryInstrType.CAPTRANSFER,
						//OBConstant.QueryInstrType.CHILDCAPTRANSFER,
						OBConstant.QueryInstrType.OPENFIXDEPOSIT,
//						OBConstant.QueryInstrType.BILLOPENFIXDEPOSIT,
						OBConstant.QueryInstrType.FIXEDTOCURRENTTRANSFER,
						OBConstant.QueryInstrType.OPENNOTIFYACCOUNT,
						OBConstant.QueryInstrType.NOTIFYDEPOSITDRAW,
						//OBConstant.QueryInstrType.TRUSTLOANRECEIVE,
//						OBConstant.QueryInstrType.CONSIGNLOANRECEIVE,
						//OBConstant.QueryInstrType.INTERESTFEEPAYMENT,
						OBConstant.QueryInstrType.DRIVEFIXDEPOSIT,
						//OBConstant.QueryInstrType.CHANGEFIXDEPOSIT,
						//OBConstant.QueryInstrType.YTLOANRECEIVE    
					};
					
				for (int i = 0; i < lTemp.length; i++)
				{
					if (lTemp[i] == lData)
					{
						strSelected = "selected";
					}
					else
					{
						strSelected = "";
					}
					out.println("<option value=\"" + lTemp[i] + "\"" + strSelected + ">" + OBConstant.QueryInstrType.getName(lTemp[i]) + "</option>");
					strSelected = "";
				}
				out.println("</select>");
			}
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
	}
	
	/**
	 * 业务复核业务类型下拉列表显示控件(新奥活期业务签认)
	 * @param out 输出
	 * @param strFieldName 域的名称
	 * @param lData 数据
	 * @param strDesc  其他属性
	 *
	 */
public static void showQueryTypeListControl2(JspWriter out, String strFieldName, long lData, String strDesc, boolean isNeedAll) throws Exception
{
	try
	{
		out.println("<select " + strDesc + " class=\"select\" name=\"" + strFieldName + "\">");

		String strSelected = "";
		if (isNeedAll == false)
		{
			long[] lTemp=null;
				lTemp=new long[]	
				{
					OBConstant.QueryInstrType.ALL ,
					OBConstant.QueryInstrType.CAPTRANSFER
				};
			
			for (int i = 0; i < lTemp.length; i++)
			{
				if (lTemp[i] == lData)
				{
					strSelected = "selected";
				}
				else
				{
					strSelected = "";
				}
				out.println("<option value=\"" + lTemp[i] + "\"" + strSelected + ">" + OBConstant.QueryInstrType.getName(lTemp[i]) + "</option>");
				strSelected = "";
			}
			out.println("</select>");
		}
		else if (isNeedAll == true)
		{
			long[] lTemp=null;
				lTemp=new long[]	
				{
					OBConstant.QueryInstrType.ALL ,
					OBConstant.QueryInstrType.CAPTRANSFER
				};
			for (int i = 0; i < lTemp.length; i++)
			{
				if (lTemp[i] == lData)
				{
					strSelected = "selected";
				}
				else
				{
					strSelected = "";
				}
				out.println("<option value=\"" + lTemp[i] + "\"" + strSelected + ">" + OBConstant.QueryInstrType.getName(lTemp[i]) + "</option>");
				strSelected = "";
			}
			out.println("</select>");
		}
	}
	catch (Exception e)
	{
		log4j.error(e.toString());
		throw new IException("Gen_E001");
	}
}
	/**
	 * 业务复核业务类型下拉列表显示控件(新奥定期业务签认)
	 * @param out 输出
	 * @param strFieldName 域的名称
	 * @param lData 数据
	 * @param strDesc  其他属性
	 *
	 */
public static void showQueryTypeListControl3(JspWriter out, String strFieldName, long lData, String strDesc, boolean isNeedAll) throws Exception
{
	try
	{
		out.println("<select " + strDesc + " class=\"select\" name=\"" + strFieldName + "\">");

		String strSelected = "";
		if (isNeedAll == false)
		{
			long[] lTemp=null;
				lTemp=new long[]	
				{
					OBConstant.QueryInstrType.ALL ,
					OBConstant.QueryInstrType.OPENFIXDEPOSIT,
					OBConstant.QueryInstrType.FIXEDTOCURRENTTRANSFER,
					OBConstant.QueryInstrType.OPENNOTIFYACCOUNT,
					OBConstant.QueryInstrType.NOTIFYDEPOSITDRAW,
					OBConstant.QueryInstrType.DRIVEFIXDEPOSIT,
				};
			
			for (int i = 0; i < lTemp.length; i++)
			{
				if (lTemp[i] == lData)
				{
					strSelected = "selected";
				}
				else
				{
					strSelected = "";
				}
				out.println("<option value=\"" + lTemp[i] + "\"" + strSelected + ">" + OBConstant.QueryInstrType.getName(lTemp[i]) + "</option>");
				strSelected = "";
			}
			out.println("</select>");
		}
		else if (isNeedAll == true)
		{
			long[] lTemp=null;
				lTemp=new long[]	
				{
					OBConstant.QueryInstrType.ALL ,
					OBConstant.QueryInstrType.OPENFIXDEPOSIT,
					OBConstant.QueryInstrType.FIXEDTOCURRENTTRANSFER,
					OBConstant.QueryInstrType.OPENNOTIFYACCOUNT,
					OBConstant.QueryInstrType.NOTIFYDEPOSITDRAW,
					OBConstant.QueryInstrType.DRIVEFIXDEPOSIT,
				};
			for (int i = 0; i < lTemp.length; i++)
			{
				if (lTemp[i] == lData)
				{
					strSelected = "selected";
				}
				else
				{
					strSelected = "";
				}
				out.println("<option value=\"" + lTemp[i] + "\"" + strSelected + ">" + OBConstant.QueryInstrType.getName(lTemp[i]) + "</option>");
				strSelected = "";
			}
			out.println("</select>");
		}
	}
	catch (Exception e)
	{
		log4j.error(e.toString());
		throw new IException("Gen_E001");
	}
}
    
    /**
     * 校验网银复核，取消复核显示列表，当存在审批流并且是自动复核时，不显示此属性
     * @param moduleID
     * @param officeID
     * @param currencyID
     * @param clientID
     * @param queryTransTypes
     * @return
     */
    private static  long[] validateShowCheck(long moduleID,long officeID,long currencyID,long clientID,long[] queryTransTypes)
    {
    	long[] lReturn = new long[queryTransTypes.length];
    	boolean bisAutoCheck;
		try {
			bisAutoCheck = OBFSWorkflowManager.isAutoCheck();
			InutParameterInfo pInfo = new InutParameterInfo();
	    	pInfo.setModuleID(moduleID);
	    	pInfo.setOfficeID(officeID);
	    	pInfo.setCurrencyID(currencyID);
			
	    	pInfo.setClientID(clientID);
			// 是否是否设置关联下级单位
	    	pInfo.setIslowerunit(OBConstant.IsLowerun.ISNO); 
	    	for (int i = 0; i < queryTransTypes.length; i++)
			{
	    		if(queryTransTypes[i]>0)
	    		{
		    		if(OBConstant.QueryInstrType.CAPTRANSFER==queryTransTypes[i])
		        	{
		    			pInfo.setTransTypeID(OBFSWorkflowManager.getReflectOperation(queryTransTypes[i],OBConstant.SettRemitType.INTERNALVIREMENT));
		        		boolean binternalvirement = false;
		        		boolean bbankpay = false;
		    			if(OBFSWorkflowManager.isNeedApproval(pInfo) && bisAutoCheck)
		        		{
		    				binternalvirement = true;
		        		}
		        		pInfo.setTransTypeID(OBFSWorkflowManager.getReflectOperation(queryTransTypes[i],OBConstant.SettRemitType.BANKPAY));
		        		if(OBFSWorkflowManager.isNeedApproval(pInfo) && bisAutoCheck)
		        		{
		        			bbankpay = true;
		        		}
		        		if(binternalvirement && bbankpay)
		        		{
		        			lReturn[i] = -1;
		        		}
		        		else
		        		{
		        			lReturn[i] = OBConstant.QueryInstrType.CAPTRANSFER;
		        		}
		        	}
		    		else
		    		{
		    			pInfo.setTransTypeID(OBFSWorkflowManager.getReflectOperation(queryTransTypes[i],-1));
		    			if(OBFSWorkflowManager.isNeedApproval(pInfo) && bisAutoCheck)
		        		{
		    				lReturn[i] = -1;
		        		}
		        		else
		        		{
		        			lReturn[i] = queryTransTypes[i];
		        		}
		    		}
	    		}
	    		else if(queryTransTypes[i]==OBConstant.QueryInstrType.ALL)
	    		{
	    			lReturn[i]=OBConstant.QueryInstrType.ALL;
	    		}
	    		
	    			
			}
	    	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return lReturn;
    	
    }
	/**
	 * 业务复核业务类型下拉列表显示控件(带nextfield)
	 * @param out 输出
	 * @param strFieldName 域的名称
	 * @param lData 数据
	 * @param strDesc  其他属性
	 *
	 */
	public static void showQueryCheckTypeListControl(JspWriter out, String strFieldName, long lData, String strDesc,long officeID,long currencyID,long clientID, boolean isNeedAll) throws Exception
	{
		try
		{
			out.println("<select " + strDesc + " class=\"select\" name=\"" + strFieldName + "\">");

			String strSelected = "";
			if (isNeedAll == false)
			{
				long[] lTemp=null;
					lTemp=new long[]	
					{
						OBConstant.QueryInstrType.CAPTRANSFER,
						//OBConstant.QueryInstrType.CHILDCAPTRANSFER,
						OBConstant.QueryInstrType.OPENFIXDEPOSIT,
//						OBConstant.QueryInstrType.BILLOPENFIXDEPOSIT,
						OBConstant.QueryInstrType.FIXEDTOCURRENTTRANSFER,
						OBConstant.QueryInstrType.OPENNOTIFYACCOUNT,
						OBConstant.QueryInstrType.NOTIFYDEPOSITDRAW,
						//OBConstant.QueryInstrType.TRUSTLOANRECEIVE,
//						OBConstant.QueryInstrType.CONSIGNLOANRECEIVE,
						//OBConstant.QueryInstrType.INTERESTFEEPAYMENT,
						  OBConstant.QueryInstrType.DRIVEFIXDEPOSIT,
						//OBConstant.QueryInstrType.CHANGEFIXDEPOSIT,
						//OBConstant.QueryInstrType.YTLOANRECEIVE 
					};
					long[] checkTemp = validateShowCheck(Constant.ModuleType.SETTLEMENT,officeID,currencyID,clientID,lTemp);
				for (int i = 0; i < checkTemp.length; i++)
				{
					if(checkTemp[i]>=0)
					{
						if (checkTemp[i] == lData)
						{
							strSelected = "selected";
						}
						else
						{
							strSelected = "";
						}
						out.println("<option value=\"" + checkTemp[i] + "\"" + strSelected + ">" + OBConstant.QueryInstrType.getName(checkTemp[i]) + "</option>");
					}
					strSelected = "";
				}
				out.println("</select>");
			}
			else if (isNeedAll == true)
			{
				long[] lTemp=null;
					lTemp=new long[]	
					{
						OBConstant.QueryInstrType.ALL ,
						OBConstant.QueryInstrType.CAPTRANSFER,
						//OBConstant.QueryInstrType.CHILDCAPTRANSFER,
						OBConstant.QueryInstrType.OPENFIXDEPOSIT,
//						OBConstant.QueryInstrType.BILLOPENFIXDEPOSIT,
						OBConstant.QueryInstrType.FIXEDTOCURRENTTRANSFER,
						OBConstant.QueryInstrType.OPENNOTIFYACCOUNT,
						OBConstant.QueryInstrType.NOTIFYDEPOSITDRAW,
						//OBConstant.QueryInstrType.TRUSTLOANRECEIVE,
//						OBConstant.QueryInstrType.CONSIGNLOANRECEIVE,
						//OBConstant.QueryInstrType.INTERESTFEEPAYMENT,
						OBConstant.QueryInstrType.DRIVEFIXDEPOSIT,
						//OBConstant.QueryInstrType.CHANGEFIXDEPOSIT,
						//OBConstant.QueryInstrType.YTLOANRECEIVE    
					};
				long[] checkTemp = validateShowCheck(Constant.ModuleType.SETTLEMENT,officeID,currencyID,clientID,lTemp);	
				for (int i = 0; i < checkTemp.length; i++)
				{
					if(checkTemp[i]>=0)
					{
						if (checkTemp[i] == lData)
						{
							strSelected = "selected";
						}
						else
						{
							strSelected = "";
						}
						out.println("<option value=\"" + checkTemp[i] + "\"" + strSelected + ">" + OBConstant.QueryInstrType.getName(checkTemp[i]) + "</option>");
					}
					strSelected = "";
				}
				out.println("</select>");
			}
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
	}
	/**
	 * 取消复核-活期 业务类型下拉列表显示控件（新奥项目新增）
	 * @param out 输出
	 * @param strFieldName 域的名称
	 * @param lData 数据
	 * @param strDesc  其他属性
	 *
	 */
	public static void showQueryCheckTypeListControl1(JspWriter out, String strFieldName, long lData, String strDesc,long officeID,long currencyID,long clientID, boolean isNeedAll) throws Exception
	{
		try
		{
			out.println("<select " + strDesc + " class=\"select\" name=\"" + strFieldName + "\">");

			String strSelected = "";
			if (isNeedAll == false)
			{
				long[] lTemp=null;
					lTemp=new long[]	
					{
						OBConstant.QueryInstrType.CAPTRANSFER
					};
					long[] checkTemp = validateShowCheck(Constant.ModuleType.SETTLEMENT,officeID,currencyID,clientID,lTemp);
				for (int i = 0; i < checkTemp.length; i++)
				{
					if(checkTemp[i]>=0)
					{
						if (checkTemp[i] == lData)
						{
							strSelected = "selected";
						}
						else
						{
							strSelected = "";
						}
						out.println("<option value=\"" + checkTemp[i] + "\"" + strSelected + ">" + OBConstant.QueryInstrType.getName(checkTemp[i]) + "</option>");
					}
					strSelected = "";
				}
				out.println("</select>");
			}
			else if (isNeedAll == true)
			{
				long[] lTemp=null;
					lTemp=new long[]	
					{
						OBConstant.QueryInstrType.ALL ,
						OBConstant.QueryInstrType.CAPTRANSFER
					};
				long[] checkTemp = validateShowCheck(Constant.ModuleType.SETTLEMENT,officeID,currencyID,clientID,lTemp);	
				for (int i = 0; i < checkTemp.length; i++)
				{
					if(checkTemp[i]>=0)
					{
						if (checkTemp[i] == lData)
						{
							strSelected = "selected";
						}
						else
						{
							strSelected = "";
						}
						out.println("<option value=\"" + checkTemp[i] + "\"" + strSelected + ">" + OBConstant.QueryInstrType.getName(checkTemp[i]) + "</option>");
					}
					strSelected = "";
				}
				out.println("</select>");
			}
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
	}
	
	/**
	 * 取消复核-定期 业务类型下拉列表显示控件（新奥项目新增）
	 * @param out 输出
	 * @param strFieldName 域的名称
	 * @param lData 数据
	 * @param strDesc  其他属性
	 *
	 */
	public static void showQueryCheckTypeListControl2(JspWriter out, String strFieldName, long lData, String strDesc,long officeID,long currencyID,long clientID, boolean isNeedAll) throws Exception
	{
		try
		{
			out.println("<select " + strDesc + " class=\"select\" name=\"" + strFieldName + "\">");

			String strSelected = "";
			if (isNeedAll == false)
			{
				long[] lTemp=null;
					lTemp=new long[]	
					{
						OBConstant.QueryInstrType.OPENFIXDEPOSIT,
						OBConstant.QueryInstrType.FIXEDTOCURRENTTRANSFER,
						OBConstant.QueryInstrType.OPENNOTIFYACCOUNT,
						OBConstant.QueryInstrType.NOTIFYDEPOSITDRAW,
						  OBConstant.QueryInstrType.DRIVEFIXDEPOSIT
					};
					long[] checkTemp = validateShowCheck(Constant.ModuleType.SETTLEMENT,officeID,currencyID,clientID,lTemp);
				for (int i = 0; i < checkTemp.length; i++)
				{
					if(checkTemp[i]>=0)
					{
						if (checkTemp[i] == lData)
						{
							strSelected = "selected";
						}
						else
						{
							strSelected = "";
						}
						out.println("<option value=\"" + checkTemp[i] + "\"" + strSelected + ">" + OBConstant.QueryInstrType.getName(checkTemp[i]) + "</option>");
					}
					strSelected = "";
				}
				out.println("</select>");
			}
			else if (isNeedAll == true)
			{
				long[] lTemp=null;
					lTemp=new long[]	
					{
						OBConstant.QueryInstrType.ALL ,
						OBConstant.QueryInstrType.OPENFIXDEPOSIT,
						OBConstant.QueryInstrType.FIXEDTOCURRENTTRANSFER,
						OBConstant.QueryInstrType.OPENNOTIFYACCOUNT,
						OBConstant.QueryInstrType.NOTIFYDEPOSITDRAW,
						OBConstant.QueryInstrType.DRIVEFIXDEPOSIT
					};
				long[] checkTemp = validateShowCheck(Constant.ModuleType.SETTLEMENT,officeID,currencyID,clientID,lTemp);	
				for (int i = 0; i < checkTemp.length; i++)
				{
					if(checkTemp[i]>=0)
					{
						if (checkTemp[i] == lData)
						{
							strSelected = "selected";
						}
						else
						{
							strSelected = "";
						}
						out.println("<option value=\"" + checkTemp[i] + "\"" + strSelected + ">" + OBConstant.QueryInstrType.getName(checkTemp[i]) + "</option>");
					}
					strSelected = "";
				}
				out.println("</select>");
			}
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
	}
	/**
	 * 业务复核业务类型下拉列表显示控件(带nextfield)
	 * @param out 输出
	 * @param strFieldName 域的名称
	 * @param lData 数据
	 * @param strDesc  其他属性
	 *
	 */
	public static void showQueryTypeListControl(JspWriter out, String strFieldName, long lData, String strChange, String strDesc, boolean isNeedAll) throws Exception
	{
		try
		{
			out.println("<select " + strChange + strDesc + " class=\"select\" name=\"" + strFieldName + "\">");

			String strSelected = "";
			if (isNeedAll == false)
			{
				long[] lTemp =
					{
						OBConstant.QueryInstrType.CAPTRANSFER//,
						//OBConstant.QueryInstrType.OPENFIXDEPOSIT,
						//OBConstant.QueryInstrType.FIXEDTOCURRENTTRANSFER,
						//OBConstant.QueryInstrType.OPENNOTIFYACCOUNT,
						//OBConstant.QueryInstrType.NOTIFYDEPOSITDRAW,
						//OBConstant.QueryInstrType.TRUSTLOANRECEIVE,
						//OBConstant.QueryInstrType.CONSIGNLOANRECEIVE,
						//OBConstant.QueryInstrType.INTERESTFEEPAYMENT,
						//OBConstant.QueryInstrType.YTLOANRECEIVE 
					};
				for (int i = 0; i < lTemp.length; i++)
				{
					if (lTemp[i] == lData)
					{
						strSelected = "selected";
					}
					else
					{
						strSelected = "";
					}
					out.println("<option value=\"" + lTemp[i] + "\"" + strSelected + ">" + OBConstant.QueryInstrType.getName(lTemp[i]) + "</option>");
					strSelected = "";
				}
				out.println("</select>");
			}
			else if (isNeedAll == true)
			{
				long[] lTemp =
					{
						OBConstant.QueryInstrType.ALL,
						OBConstant.QueryInstrType.CAPTRANSFER//,
						//OBConstant.QueryInstrType.OPENFIXDEPOSIT,
						//OBConstant.QueryInstrType.FIXEDTOCURRENTTRANSFER,
						//OBConstant.QueryInstrType.OPENNOTIFYACCOUNT,
						//OBConstant.QueryInstrType.NOTIFYDEPOSITDRAW,
						//OBConstant.QueryInstrType.TRUSTLOANRECEIVE,
						//OBConstant.QueryInstrType.CONSIGNLOANRECEIVE,
						//OBConstant.QueryInstrType.INTERESTFEEPAYMENT,
						//OBConstant.QueryInstrType.YTLOANRECEIVE 
					};
				for (int i = 0; i < lTemp.length; i++)
				{
					if (lTemp[i] == lData)
					{
						strSelected = "selected";
					}
					else
					{
						strSelected = "";
					}
					out.println("<option value=\"" + lTemp[i] + "\"" + strSelected + ">" + OBConstant.QueryInstrType.getName(lTemp[i]) + "</option>");
					strSelected = "";
				}
				out.println("</select>");
			}
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
	}

	/**
	 * 业务复核业务类型下拉列表显示控件(带nextfield)
	 * @param out 输出
	 * @param strFieldName 域的名称
	 * @param lData 数据
	 * @param strDesc  其他属性
	 *
	 */
	public static void showTransTypeListControl(JspWriter out, String strFieldName, long lData, String strDesc) throws Exception
	{
		try
		{
			out.println("<select " + strDesc + " class=\"select\" name=\"" + strFieldName + "\">");

			String strSelected = "";
			long[] lTemp =
				{
					OBConstant.SettInstrType.CAPTRANSFER_SELF,
					OBConstant.SettInstrType.CAPTRANSFER_BANKPAY,
					OBConstant.SettInstrType.CAPTRANSFER_INTERNALVIREMENT,
					OBConstant.SettInstrType.OPENFIXDEPOSIT,
					OBConstant.SettInstrType.FIXEDTOCURRENTTRANSFER,
					OBConstant.SettInstrType.OPENNOTIFYACCOUNT,
					OBConstant.SettInstrType.NOTIFYDEPOSITDRAW,
					OBConstant.SettInstrType.TRUSTLOANRECEIVE,
					OBConstant.SettInstrType.CONSIGNLOANRECEIVE,
					OBConstant.SettInstrType.INTERESTFEEPAYMENT };
			for (int i = 0; i < lTemp.length; i++)
			{

				if (lTemp[i] == lData)
				{
					strSelected = "selected";
				}
				else
				{
					strSelected = "";
				}
				out.println("<option value=\"" + lTemp[i] + "\"" + strSelected + ">" + OBConstant.SettInstrType.getName(lTemp[i]) + "</option>");
				strSelected = "";
			}
			out.println("</select>");
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
	}

	/**
	 * 业务复核业务状态下拉列表显示控件(带nextfield)
	 * @param out 输出
	 * @param strFieldName 域的名称
	 * @param lData 数据
	 * @param strDesc  其他属性
	 *
	 */
	public static void showTransStatusListControl(JspWriter out, String strFieldName, long lData, String strDesc) throws Exception
	{
		try
		{
			out.println("<select " + strDesc + " class=\"select\" name=\"" + strFieldName + "\">");

			String strSelected = "";
			long[] lTemp = { OBConstant.SettInstrStatus.SAVE, OBConstant.SettInstrStatus.CHECK };
			for (int i = 0; i < lTemp.length; i++)
			{
				if (lTemp[i] == lData)
				{
					strSelected = "selected";
				}
				else
				{
					strSelected = "";
				}
				out.println("<option value=\"" + lTemp[i] + "\"" + strSelected + ">" + OBConstant.SettInstrStatus.getName(lTemp[i]) + "</option>");
				strSelected = "";
			}
			out.println("</select>");
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
	}

	/**
	 * 签认业务状态下拉列表显示控件(带nextfield)
	 * @param out 输出
	 * @param strFieldName 域的名称
	 * @param lData 数据
	 * @param strDesc  其他属性
	 *
	 */
	public static void showSignTransStatusListControl(JspWriter out, String strFieldName, long lData, String strDesc) throws Exception
	{
		try
		{
			out.println("<select " + strDesc + " class=\"select\" name=\"" + strFieldName + "\">");

			String strSelected = "";
			long[] lTemp = { OBConstant.SettInstrStatus.CHECK, OBConstant.SettInstrStatus.SIGN, };
			for (int i = 0; i < lTemp.length; i++)
			{
				if (lTemp[i] == lData)
				{
					strSelected = "selected";
				}
				else
				{
					strSelected = "";
				}
				out.println("<option value=\"" + lTemp[i] + "\"" + strSelected + ">" + OBConstant.SettInstrStatus.getName(lTemp[i]) + "</option>");
				strSelected = "";
			}
			out.println("</select>");
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
	}

	/**
	* 交易申请查询业务状态下拉列表显示控件(带nextfield)
	* @author yanliu
	* @param out 输出
	* @param strFieldName 域的名称
	* @param lData 数据
	* @param strDesc  其他属性
	*
	*/
	public static void showQueryStatusListControl(JspWriter out, String strFieldName, long lData, String strDesc) throws Exception
	{
		try
		{
			out.println("<select " + strDesc + " class=\"select\" name=\"" + strFieldName + "\">");
			out.println("<option  value=\"-1\">全部</option>");

			String strSelected = "";
			long[] lTemp =
				{
					OBConstant.SettInstrStatus.SAVE,
					OBConstant.SettInstrStatus.APPROVALING,
					OBConstant.SettInstrStatus.APPROVALED,
					OBConstant.SettInstrStatus.CHECK,
					OBConstant.SettInstrStatus.SIGN,
					OBConstant.SettInstrStatus.DEAL,
					OBConstant.SettInstrStatus.FINISH,
					OBConstant.SettInstrStatus.REFUSE };
			for (int i = 0; i < lTemp.length; i++)
			{
				if (lTemp[i] == lData)
				{
					strSelected = "selected";
				}
				else
				{
					strSelected = "";
				}
				out.println("<option value=\"" + lTemp[i] + "\"" + strSelected + ">" + OBConstant.SettInstrStatus.getName(lTemp[i]) + "</option>");
				strSelected = "";
			}
			out.println("</select>");
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
	}
	
	public static void showQueryEbankStatusListControl(JspWriter out, String strFieldName, long lData, String strDesc) throws Exception
	{
		try
		{
			out.println("<select " + strDesc + "class=\"select\" name=\"" + strFieldName + "\">");
			out.println("<option  value=\"-1\">全部</option>");

			String strSelected = "";
			long[] lTemp =
				{
					OBConstant.SettInstrStatus.SAVE,
					OBConstant.SettInstrStatus.APPROVALING,
					OBConstant.SettInstrStatus.APPROVALED,
					OBConstant.SettInstrStatus.CHECK,
					
					OBConstant.SettInstrStatus.FINISH,
					OBConstant.SettInstrStatus.REFUSE };
			for (int i = 0; i < lTemp.length; i++)
			{
				if (lTemp[i] == lData)
				{
					strSelected = "selected";
				}
				else
				{
					strSelected = "";
				}
				out.println("<option value=\"" + lTemp[i] + "\"" + strSelected + ">" + OBConstant.SettInstrStatus.getName(lTemp[i]) + "</option>");
				strSelected = "";
			}
			out.println("</select>");
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
	}
	
	
	public static void showCheckStatusListControl(JspWriter out, String strFieldName, long lData, String strDesc) throws Exception
	{
		try
		{
			out.println("<select " + strDesc + " class=\"select\" name=\"" + strFieldName + "\">");
			out.println("<option  value=\"-1\">全部</option>");

			String strSelected = "";
			long[] lTemp =
				{
					OBConstant.SettInstrStatus.SAVE,
					OBConstant.SettInstrStatus.APPROVALED,
					 };
			for (int i = 0; i < lTemp.length; i++)
			{
				if (lTemp[i] == lData)
				{
					strSelected = "selected";
				}
				else
				{
					strSelected = "";
				}
				out.println("<option value=\"" + lTemp[i] + "\"" + strSelected + ">" + OBConstant.SettInstrStatus.getName(lTemp[i]) + "</option>");
				strSelected = "";
			}
			out.println("</select>");
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
	}
	
	/**
	* 交易申请查询业务状态下拉列表显示控件(带nextfield) 无审批流状态 2007-10-29
	* @author zcwang 
	* @param out 输出
	* @param strFieldName 域的名称
	* @param lData 数据
	* @param strDesc  其他属性
	*
	*/
	public static void showQueryStatusListControlBase(JspWriter out, String strFieldName, long lData, String strDesc) throws Exception
	{
		try
		{
			out.println("<select " + strDesc + " class=\"select\" name=\"" + strFieldName + "\">");
			out.println("<option  value=\"-1\">全部</option>");

			String strSelected = "";
			long[] lTemp =
				{
					OBConstant.SettInstrStatus.SAVE,
					//OBConstant.SettInstrStatus.APPROVALING,
					//OBConstant.SettInstrStatus.APPROVALED,
					OBConstant.SettInstrStatus.CHECK,
					OBConstant.SettInstrStatus.SIGN,
					OBConstant.SettInstrStatus.DEAL,
					OBConstant.SettInstrStatus.FINISH,
					OBConstant.SettInstrStatus.REFUSE };
			for (int i = 0; i < lTemp.length; i++)
			{
				if (lTemp[i] == lData)
				{
					strSelected = "selected";
				}
				else
				{
					strSelected = "";
				}
				out.println("<option value=\"" + lTemp[i] + "\"" + strSelected + ">" + OBConstant.SettInstrStatus.getName(lTemp[i]) + "</option>");
				strSelected = "";
			}
			out.println("</select>");
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
	}

	/*
		Function: 下拉框控件(选择账号)  --  账户历史明细
	*/
	public static void showAccountListControl(JspWriter out, String strFieldName, long lClientID, long lCurrencyID, long lData, long lUserID, String strFieldDesc) throws Exception
	{
		long lResult = -1;
		ResultSet rs = null;
		PreparedStatement ps = null;
		Connection con = null;
		long lTemplateID = 0;
		long lLobID = -1;
		Vector v = new Vector();
		try
		{
			con = Database.getConnection();
			String strTmp = new String();
			String strAccountNo = findAccountByUser(lUserID, lCurrencyID);
			Sett_FilialeAccountSetDAO settDao = new Sett_FilialeAccountSetDAO ();

			out.println("<select class=\"select\" name=\"" + strFieldName + "\" " + strFieldDesc + ">");
			strTmp =
				"select a.ID as AccountID,a.sAccountNo AccountNo,c.ID as ClientID,c.sCode as ClientNo,c.sName as ClientName, "
					+ " atp.naccountgroupid as Naccountgroupid, a.naccounttypeid as Naccounttypeid, atp.sAccountType as sAccountType "
					+ " from sett_Account a, Client c,sett_AccountType atp "
					+ "where a.nclientid=c.id and a.nCheckStatusid =" 
					+ SETTConstant.AccountCheckStatus.CHECK + 
					" and a.naccounttypeid=atp.id and a.nCurrencyID= ? and c.id=? " +
					" and a.id in (select o.naccountid from OB_ACCOUNTOWNEDBYUSERQUERY o where o.nuserid = ?)";
/*
			if (strAccountNo.length() > 4)
			{
				strTmp += " and a.sAccountNo in ( " + strAccountNo + ")";
				Log.print("1----------2");
			}
*/
			strTmp += " order by atp.naccountgroupid, a.id";
			Log.print(strTmp);
			ps = con.prepareStatement(strTmp);
			ps.setLong(1, lCurrencyID);
			ps.setLong(2, lClientID);
			ps.setLong(3, lUserID);
			rs = ps.executeQuery();
			String strSelected = "";
			String sTemp = "";
			boolean bool = true;
			while (rs.next())
			{ 
				long lSupplierID = rs.getLong("AccountID");
				String sAccountNO = rs.getString("AccountNo");
				if (lSupplierID == lData)
				{
					strSelected = " selected";
				}
				
				//FilialeAccountInfo[] info = settDao.findRefFilialeAccountInfoBySettAccountId(lSupplierID);
				sTemp = com.iss.itreasury.settlement.util.NameRef.getBankAccountNOByCurrenctAccountID(sAccountNO.trim());
					//sTemp = "";
					if(rs.getLong("Naccounttypeid")!=12){//控制不能出现"12的住房公积金"
					
					out.println(
							"<option value=\""
							+ rs.getLong("AccountID")
							+ ";"
							+ rs.getLong("Naccountgroupid")
							+ "@"
							+ rs.getLong("Naccounttypeid")
							+ "\""
							+ strSelected
							+ ">"
							+ rs.getString("sAccountType")
							+ " -- "
							+ 
							NameRef.getNoLineAccountNo(rs.getString("AccountNo"))
							+sTemp
							+ "</option>");
					strSelected = "";
					bool = false;
				}

			}
			if(bool){
				out.println("<option value='-1'>&nbsp;</option>");
			}
			out.println("</select>");
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
		}
		catch (Exception e)
		{
			System.out.println(" can not select OFFICE, because showAccountListControl " + e);
			e.printStackTrace() ;
			throw e;
		}
		finally
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
			if (con != null)
			{
				con.close();
				con = null;
			}
		}
	}
	public static void showAccountListControl(JspWriter out, String strFieldName, long lClientID, long lCurrencyID,long lAccountGroupID, long lData, long lUserID, String strFieldDesc) throws Exception
	{
		long lResult = -1;
		ResultSet rs = null;
		PreparedStatement ps = null;
		Connection con = null;
		long lTemplateID = 0;
		long lLobID = -1;
		Vector v = new Vector();
		try
		{
			con = Database.getConnection();
			String strTmp = new String();
			String strAccountNo = findAccountByUser(lUserID, lCurrencyID);
			Sett_FilialeAccountSetDAO settDao = new Sett_FilialeAccountSetDAO ();

			out.println("<select class=\"select\" name=\"" + strFieldName + "\" " + strFieldDesc + ">");
			strTmp =
				"select a.ID as AccountID,a.sAccountNo AccountNo,c.ID as ClientID,c.sCode as ClientNo,c.sName as ClientName, "
					+ " atp.naccountgroupid as Naccountgroupid, a.naccounttypeid as Naccounttypeid, atp.sAccountType as sAccountType "
					+ " from sett_Account a, Client c,sett_AccountType atp "
					+ "where a.nclientid=c.id and a.naccounttypeid=atp.id and a.nCurrencyID= ?";
			if(lClientID != -1)
				strTmp += "and c.id=? ";
			if(lAccountGroupID != -1)
				strTmp += "and atp.naccountgroupid=? ";
			strTmp += " order by atp.naccountgroupid, a.id";
			Log.print(strTmp);
			ps = con.prepareStatement(strTmp);
			ps.setLong(1, lCurrencyID);
				if(lClientID != -1)
			ps.setLong(2, lClientID);
			if(lAccountGroupID != -1)
				ps.setLong(3, lAccountGroupID);
			rs = ps.executeQuery();
			String strSelected = "";
			String sTemp = "";
			while (rs.next())
			{ 
				long lSupplierID = rs.getLong("AccountID");
				String sAccountNO = rs.getString("AccountNo");
				if (lSupplierID == lData)
				{
					strSelected = " selected";
				}
				
				//FilialeAccountInfo[] info = settDao.findRefFilialeAccountInfoBySettAccountId(lSupplierID);
				sTemp = com.iss.itreasury.settlement.util.NameRef.getBankAccountNOByCurrenctAccountID(sAccountNO.trim());
					//sTemp = "";
				out.println(
					"<option value=\""
						+ rs.getLong("AccountID")
 						+ ";"
 						+ rs.getLong("Naccountgroupid")
 						+ "@"
 						+ rs.getLong("Naccounttypeid")
						+ "\""
						+ strSelected
						+ ">"
						+ rs.getString("sAccountType")
						+ " -- "
						+ rs.getString("AccountNo")
						+sTemp
						+ "</option>");
				strSelected = "";
			}
			out.println("</select>");
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
		}
		catch (Exception e)
		{
			System.out.println(" can not select OFFICE, because showAccountListControl " + e);
			e.printStackTrace() ;
			throw e;
		}
		finally
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
			if (con != null)
			{
				con.close();
				con = null;
			}
		}
	}

	/**
		 * 创建收款方银行账号放大镜（汇）
		 * @author yanliu
		 * @param out
		 * @param lClientID 客户ID
		 * @param lCurrencyID 币种ID
		 * @param strRelativeForm 关联项
		 * @param sBankAccountCode 账号（初始值）
		 * @param strFormName 主页表单名称
		 * @param strCtrlName 放大镜主控件名称
		 * @param strTitle 放大镜描述)
		 * @param strClientNo 客户编号(初识值)
		 * @param strFirstTD 第一个TD的属性
		 * @param strSecondTD 第二个TD的属性
		 * @param sNextControlsClient 下一个（或多个）获得焦点的控件
		 * @param strRtnClientNameCtrl 返回值（客户名称）对应的控件名
		 */
	public static void createPayeeAccountNOCtrl(
		JspWriter out,
		long lCurrencyID,
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
		String strSecondTD)
	{
		String strMagnifierName = URLEncoder.encode("收款方账号");
		String strMainProperty = " value='" + sBankAccountCode + "'";
		String strPrefix = "";

		String[] strMainNames = { strCtrlName + "Ctrl", strRelativeForm1, strRelativeForm2, strRelativeForm3, strRelativeForm4 };
		String[] strMainFields = { "spayeeacctno", "sPayeeName", "SPAYEEPROV", "SPAYEECITY", "sPayeeBankName" };

		String[] strReturnNames = {
		};
		String[] strReturnFields = {
		};
		String[] strReturnValues = {
		};

		String[] strDisplayNames = { URLEncoder.encode("收款方账号"), URLEncoder.encode("收款方名称")};
		String[] strDisplayFields = { "spayeeacctno", "sPayeeName" };
		int nIndex = 0;

		String strSQL = "getPayeeAccountNOSQL(" + lClientID + "," + lCurrencyID + "," + strCtrlName + "Ctrl.value," + strRelativeForm1 + ".value" + ")";
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
		 * 创建收款方银行账号放大镜（本转）
		 * @author yanliu
		 * @param out
		 * @param lClientID 客户ID
		 * @param lCurrencyID 币种ID
		 * @param strRelativeForm 关联项
		 * @param sBankAccountCode 账号（初始值）
		 * @param strFormName 主页表单名称
		 * @param strCtrlName 放大镜主控件名称
		 * @param strTitle 放大镜描述)
		 * @param strClientNo 客户编号(初识值)
		 * @param strFirstTD 第一个TD的属性
		 * @param strSecondTD 第二个TD的属性
		 * @param sNextControlsClient 下一个（或多个）获得焦点的控件
		 * @param strRtnClientNameCtrl 返回值（客户名称）对应的控件名
		 */
	public static void createPayeeBankNOCtrl(
		JspWriter out,
		long lCurrencyID,
		long lClientID,
		String strRelativeForm,
		String strFormName,
		String sBankAccountCode,
		String strCtrlName,
		String strTitle,
		String strFirstTD,
		String strSecondTD)
	{
		String strMagnifierName = URLEncoder.encode("收款方账号");
		String strMainProperty = " value='" + sBankAccountCode + "'";
		String strPrefix = "";

		String[] strMainNames = { strCtrlName + "Ctrl", strRelativeForm };
		String[] strMainFields = { "spayeeacctno", "sPayeeName" };

		String[] strReturnNames = {
		};
		String[] strReturnFields = {
		};
		String[] strReturnValues = {
		};

		String[] strDisplayNames = { URLEncoder.encode("收款方账号"), URLEncoder.encode("收款方名称")};
		String[] strDisplayFields = { "spayeeacctno", "sPayeeName" };
		int nIndex = 0;

		String strSQL = "getPayeeBankNOSQL(" + lClientID + "," + lCurrencyID + "," + strCtrlName + "Ctrl.value," + strRelativeForm + ".value" + ")";
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
	 * 创建合同放大镜
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lClientID 客户ID(初识值)
	 * @param lContractID 合同ID(初识值)
	 * @param strContractNo 合同号(初识值)
	 * @param lTransactionType 交易类型(查询条件:由SETTConstant.TransactionType定义，-1 查询所有)
	 * @param lMagnifierType 合同放大境类型(查询条件: 1 业务处理;2 业务复核;)
	 * @param strClientCtrl 客户ID对应的控件名称（查询时关联）
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param strNextControls 下一个（或多个）获得焦点的控件
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
		String strMagnifierName = URLEncoder.encode("合同");
		String strMainProperty = " maxlength='30' value='" + strContractNo + "'";
		String strPrefix = "";

		String[] strMainNames = { strCtrlName + "Ctrl" };
		String[] strMainFields = { "ContractCode", };

		String[] strReturnNames = { strCtrlName, strCtrlName + "ClientID" };
		String[] strReturnFields = { "ContractID", "ClientID" };
		String[] strReturnValues = { String.valueOf(lContractID), String.valueOf(lClientID)};

		String[] strDisplayNames = { URLEncoder.encode("合同号"), URLEncoder.encode("客户名称")};
		String[] strDisplayFields = { "ContractCode", "ClientName" };
		int nIndex = 0;

		long[] lContractTypeIDs = null;

		//信托 ：LOANConstant.LoanType.ZYDQ  ZYZCQ  ZGXEDQ  ZGXEZCQ  YT
		if (lTransactionType == SETTConstant.TransactionType.TRUSTLOANGRANT || lTransactionType == SETTConstant.TransactionType.TRUSTLOANRECEIVE)
		{
			lContractTypeIDs = new long[] { LOANConstant.LoanType.ZY, LOANConstant.LoanType.ZGXE, LOANConstant.LoanType.YT};

		}
		//委托 : WT  WTTJTH
		else if (lTransactionType == SETTConstant.TransactionType.CONSIGNLOANGRANT || lTransactionType == SETTConstant.TransactionType.CONSIGNLOANRECEIVE)
		{
			lContractTypeIDs = new long[] { LOANConstant.LoanType.WT};
		}
		//贴现 : TX
		else if (lTransactionType == SETTConstant.TransactionType.DISCOUNTGRANT || lTransactionType == SETTConstant.TransactionType.DISCOUNTRECEIVE)
		{
			lContractTypeIDs = new long[] { LOANConstant.LoanType.TX };
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
		//业务处理
		else if (lMagnifierType == 1)
		{
			lStatusIDs = new long[] { LOANConstant.ContractStatus.NOTACTIVE, LOANConstant.ContractStatus.ACTIVE, LOANConstant.ContractStatus.EXTEND };
		}
		else if (lMagnifierType == 2)
		{
			//TODO:
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
			OBHtmlCom.showZoomCtrl(
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
			Log.print("合同放大镜[" + strCtrlName + "]异常：" + e.toString());
		}
	}

	/**
	 * 创建放款通知单放大镜
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lContractID 合同ID(初识值)
	 * @param lPayFormID 放款通知单ID(初识值)
	 * @param strPayFormNo 放款通知单号(初识值)
	 * @param lPayFormTypeID 放款通知单类型(查询条件:1,信托；2，委托)
	 * @param lTypeID 放款通知单使用条件(内部状态：
	 * 		0，显示全部；
	 * 		1，信托/委托发放——业务处理使用；
	 * 		2，信托/委托发放——业务复核使用；
	 * 		3，信托/委托收回——业务处理使用；
	 * 		4，信托/委托收回——业务复核使用。）
	 * @param strContractCtrl 合同ID对应的控件名称（查询时关联）
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param strNextControls 下一个（或多个）获得焦点的控件
	 * @param strRtnStartDateCtrl 返回值（起始日期）对应的控件名
	 * @param strRtnEndDateCtrl 返回值（到期日期）对应的控件名
	 * @param strRtnSubAccountIDCtrl 返回值（子账户ID）对应的控件名
	 */
	public static void createPayFormCtrl(
		JspWriter out,
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
		String strRtnSubAccountIDCtrl)
	{
		String strMagnifierName = URLEncoder.encode("放款通知单");
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

		String[] strMainNames = { strCtrlName + "Ctrl", strRtnStartDateCtrl, strRtnEndDateCtrl, strRtnSubAccountIDCtrl };
		String[] strMainFields = { "PayFormCode", "StartDate", "EndDate", "SubAccountID" };

		String[] strReturnNames = { strCtrlName, strCtrlName + "ContractID" };
		String[] strReturnFields = { "PayFormID", "ContractID" };
		String[] strReturnValues = { String.valueOf(lPayFormID), String.valueOf(lContractID)};

		String[] strDisplayNames = { URLEncoder.encode("放款通知单号"), URLEncoder.encode("起始日期"), URLEncoder.encode("放款日期")};
		String[] strDisplayFields = { "PayFormCode", "StartDate", "PayDate" };
		int nIndex = 0;

		long[] lContractTypeIDs = null;

		//信托 ：LOANConstant.LoanType.ZYDQ  ZYZCQ  ZGXEDQ  ZGXEZCQ  YT
		if (lPayFormTypeID == 1)
		{
			lContractTypeIDs = new long[] { LOANConstant.LoanType.ZY, LOANConstant.LoanType.ZGXE, LOANConstant.LoanType.YT};

		}
		//委托 : WT  WTTJTH
		else if (lPayFormTypeID == 2)
		{
			lContractTypeIDs = new long[] { LOANConstant.LoanType.WT};
		}
		long[] lStatusIDArray = null;

		//获得状态数组
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

		StringBuffer sbSQL = new StringBuffer(64);
		sbSQL.append("getPayFormSQL(");
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
			OBHtmlCom.showZoomCtrl(
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
			Log.print("放大镜[" + strCtrlName + "]异常：" + e.toString());
		}
	}

	/**
	 * 创建付款方银行账号放大镜,收款方账号（内部转账）
	 * @author yanliu
	 * @param out
	 * @param lUserID 用户ID
	 * @param lClientID 客户ID
	 * @param lInstructionID 当前交易ID
	 * @param lCurrencyID 币种ID
	 * @param sBankAccountCode 账号（初始值）
	 * @param strFormName 主页表单名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述)
	 * @param strClientNo 客户编号(初识值)
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param sNextControlsClient 下一个（或多个）获得焦点的控件
	 * @param strRtnClientNameCtrl 返回值（客户名称）对应的控件名
	 */
	public static void createBankAccountCodeCtrl(
		JspWriter out,
		long lUserID,
		long lCurrencyID,
		long lInstructionID,
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
		String strMagnifierName = URLEncoder.encode("付款方账号");
		String strMainProperty = " value='" + sBankAccountCode + "'";
		String strPrefix = "";

		String[] strMainNames = { strCtrlName + "Ctrl", strRelativeForm1, strRelativeForm2 };
		String[] strMainFields = { "saccountno", "", "" };

		String[] strMainName = { strCtrlName + "Ctrl" }; //收款方
		String[] strMainField = { "saccountno" }; //收款方

		String[] strReturnNames = { "sBankAccountNO" };
		String[] strReturnFields = { "sbankaccountno" };
		String[] strReturnValues = { "" };

		String[] strReturnName = { "sPayeeBankAccountNOInternal" }; //收款方
		String[] strReturnField = { "sbankaccountno" }; //收款方
		String[] strReturnValue = { "" }; //收款方

		String[] strDisplayNames = { URLEncoder.encode("内部账号"), URLEncoder.encode("银行账号")};
		String[] strDisplayFields = { "saccountno", "sbankaccountno" };
		int nIndex = 0;

		String strSQL = "getBankAccountCodeSQL(" + strCtrlName + "Ctrl.value," + lUserID + "," + lClientID + "," + lCurrencyID + "," + lInstructionID + ")";
		String[] strNextControls = { "nRemitType" };

		try
		{
			if (!strRelativeForm1.equals("") && !strRelativeForm2.equals(""))
			{
				showBankAccountZoomCtrl(
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
	 * 创建定期(通知)存款单据号放大镜
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lUserID 当前用户ID(初识值)
	 * @param lAccountID 主账户ID(初识值) 
	 * @param lSubAccountID 子账户ID(初识值)
	 * @param strDepositNo 定期(通知)存单号(初识值)
	 * @param lDepositTypeID 存单类型：1，定期；2，通知。
	 * @param lTypeID 放大镜类型，取值如下：
	 * 	1、定期（通知）开立--复核匹配时使用
	 * 	21、定期（通知）支取--业务处理时使用
	 *  22、定期（通知）支取--业务复核时使用
	 *  3、定期续期转存--业务处理时使用（仅显示已到期的存单）
	 * 
	 * @param strAccountIDCtrl（查询时关联）
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param sNextControlsClient 下一个（或多个）获得焦点的控件
	 * @param strRtnEndDateCtrl 返回值（到期日）对应的控件名
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
	/**
	 * 创建定期(通知)存款单据号放大镜
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lUserID 当前用户ID(初识值)
	 * @param lAccountID 主账户ID(初识值) 
	 * @param lSubAccountID 子账户ID(初识值)
	 * @param strDepositNo 定期(通知)存单号(初识值)
	 * @param lDepositTypeID 存单类型：1，定期；2，通知。
	 * @param lTypeID 放大镜类型，取值如下：
	 * 	1、定期（通知）开立--复核匹配时使用
	 * 	21、定期（通知）支取--业务处理时使用
	 *  22、定期（通知）支取--业务复核时使用
	 *  3、定期续期转存--业务处理时使用（仅显示已到期的存单）
	 * 
	 * @param strAccountIDCtrl（查询时关联）
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param sNextControlsClient 下一个（或多个）获得焦点的控件
	 * @param strRtnEndDateCtrl 返回值（到期日）对应的控件名
	 * @param strRtnOpenDateCtrl 返回值（开户日）对应的控件名
	 * @param strRtnCapitalCtrl 返回值（本金）对应的控件名
	 * @param strRtnBalanceCtrl 返回值（余额）对应的控件名
	 * @param strRtnRateCtrl 返回值（利率）对应的控件名
	 * @param strRtnIntervalCtrl 返回值（期限）对应的控件名
	 * @param strRtnStartDateCtrl 返回值（开始日期）对应的控件名
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
		String strRtnStartDateCtrl)
	{
		String strMagnifierName = URLEncoder.encode("存款单据号");
		if (lDepositTypeID == 1)
		{
			strMagnifierName = URLEncoder.encode("定期存款单据号");
		}
		else if (lDepositTypeID == 2)
		{
			strMagnifierName = URLEncoder.encode("通知存款单据号");
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

		String[] strMainNames = { strCtrlName + "Ctrl", strRtnEndDateCtrl, strRtnOpenDateCtrl, strRtnCapitalCtrl, strRtnBalanceCtrl, strRtnRateCtrl, strRtnIntervalCtrl, strRtnStartDateCtrl };
		String[] strMainFields = { "DepositNo", "EndDate", "OpenDate", "Capital", "Balance", "Rate", "Interval", "StartDate" };

		String[] strReturnNames = { strCtrlName, strCtrlName + "AccountID" };
		String[] strReturnFields = { "SubAccountID", "AccountID" };
		String[] strReturnValues = { String.valueOf(lSubAccountID), String.valueOf(lAccountID)};

		String[] strDisplayNames = { URLEncoder.encode("存款单据号")};
		String[] strDisplayFields = { "DepositNo" };
		//支取或续期转存时，显示不同
		if (lTypeID == 2 || lTypeID == 3)
		{
			if (lDepositTypeID == 1)
			{
				//定期显示单据号、余额、到期日
				strDisplayNames = new String[] { URLEncoder.encode("存款单据号"), URLEncoder.encode("余额"), URLEncoder.encode("到期日")};
				strDisplayFields = new String[] { "DepositNo", "Balance", "EndDate" };
			}
			else
			{
				//定期显示单据号、余额、开户日期
				strDisplayNames = new String[] { URLEncoder.encode("存款单据号"), URLEncoder.encode("余额"), URLEncoder.encode("开户日")};
				strDisplayFields = new String[] { "DepositNo", "Balance", "OpenDate" };
			}
		}
		int nIndex = 0;

		String strSQL = "";
		if (strAccountIDCtrl != null && !strAccountIDCtrl.trim().equals(""))
		{
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
			OBHtmlCom.showZoomCtrl(
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
			Log.print("定期存款单据号放大镜[" + strCtrlName + "]异常：" + e.toString());
		}
	}

	/**
	 * 创建定期(通知)存款单据号放大镜
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lUserID 当前用户ID(初识值)
	 * @param lAccountID 主账户ID(初识值) 
	 * @param lSubAccountID 子账户ID(初识值)
	 * @param strDepositNo 定期(通知)存单号(初识值)
	 * @param lDepositTypeID 存单类型：1，定期；2，通知。
	 * @param lTypeID 放大镜类型，取值如下：
	 * 	1、定期（通知）开立--复核匹配时使用
	 * 	21、定期（通知）支取--业务处理时使用
	 *  22、定期（通知）支取--业务复核时使用
	 *  3、定期续期转存--业务处理时使用（仅显示已到期的存单）
	 * 
	 * @param strAccountIDCtrl（查询时关联）
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param sNextControlsClient 下一个（或多个）获得焦点的控件
	 * @param strRtnEndDateCtrl 返回值（到期日）对应的控件名
	 * @param strRtnOpenDateCtrl 返回值（开户日）对应的控件名
	 * @param strRtnCapitalCtrl 返回值（本金）对应的控件名
	 * @param strRtnBalanceCtrl 返回值（余额）对应的控件名
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
	 * 显示付款方账号（网银）放大镜
	 * @author yanliu
	 * @param out
	 * @param strMagnifierName 放大镜的名称
	 * @param strFormName 主页面表单名称
	 * @param strPrefix 控件名称前缀
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
	 * @param strTitle 栏位标题
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性 
	 * @param blnIsOptional 是否是可选项（目前仅对摘要、现金流向放大镜有用）
	 * @param blnIsRateCtrl 是否是利率控件
	 * @throws Exception
	 */
	public static void showBankAccountZoomCtrl(
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
				sFeatures = "toolbar=no,menubar=no,resizable=no,location=no,width=520,height=450,scrollbars=yes";
			}
			else
			{
				sFeatures = "toolbar=no,menubar=no,resizable=no,location=no,width=624,height=540,scrollbars=yes";
			}
			//生成传递给弹出窗口的参数字符串
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
				strParam += "&strDisplayNames=" + strDisplayNames[i];
				strParam += "&strDisplayFields=" + strDisplayFields[i];
			}
			//Log.print("strParam = " + strParam);
			//生成查询按钮的事件字符串
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
			//显示控件
			if (iPos == -1)
			{
				out.println("<td " + strFirstTD + ">" + strTitle + ":&nbsp;" + "<a href=#><img name=\"" + strButtonName + "\" src='/webob/zoom.gif' border=0 onclick=\"" + sOnKeydown + "\"></a></td>");
				//image
			}
			else
			{
				out.println("<td " + strFirstTD + ">" + strTitle + ":&nbsp;" + "<a href=#><img name=\"" + strButtonName + "\" src='/webob/zoom.gif' border=0 ></a></td>");
				//image
			}
			//blnIsOptional,是否可选项（仅对摘要、现金流向放大镜有效）
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
	 * 显示普通放大镜
	 * @param out
	 * @param strMagnifierName 放大镜的名称
	 * @param strFormName 主页面表单名称
	 * @param strPrefix 控件名称前缀
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
				sFeatures = "toolbar=no,menubar=no,resizable=no,location=no,width=520,height=450,scrollbars=yes";
			}
			else
			{
				sFeatures = "toolbar=no,menubar=no,resizable=no,location=no,width=624,height=540,scrollbars=yes";
			}
			//生成传递给弹出窗口的参数字符串
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
				strParam += "&strDisplayNames=" + strDisplayNames[i];
				strParam += "&strDisplayFields=" + strDisplayFields[i];
			}
			//Log.print("strParam = " + strParam);
			//生成查询按钮的事件字符串
			String sOnKeydown =
				"if("
					+ strFormName
					+ "."
					+ strMainNames[0]
					+ ".disabled == false) {gnIsSelectCtrl=1;window.open('/NASApp/iTreasury-ebank/magnifier/ShowMagnifierZoom.jsp?"
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
			//显示控件
			if (iPos == -1)
			{
				out.println("<td " + strFirstTD + ">" + strTitle + ":&nbsp;" + "<a href=#><img name=\"" + strButtonName + "\" src='/webob/zoom.gif' border=0 onclick=\"" + sOnKeydown + "\"></a></td>");
				//image
			}
			else
			{
				out.println("<td " + strFirstTD + ">" + strTitle + ":&nbsp;" + "<a href=#><img name=\"" + strButtonName + "\" src='/webob/zoom.gif' border=0 ></a></td>");
				//image
			}
			//blnIsOptional,是否可选项（仅对摘要、现金流向放大镜有效）
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
	 * 6 ：银行票据放大镜（类型描述从常量中得）
	 * 7 ：定期或通知存单号（从定期开立表中取得）
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
				case 6 : //银行票据放大镜（类型描述从常量中得）
					Log.print("银行票据放大镜");
					strReturn = SETTConstant.BankBillType.getName(lID);
					break;
				case 7 : //定期或通知存单号（从定期开立表中取得）
					Log.print("定期或通知存单号");
					//UtilOperation utiloperation = new UtilOperation();
					//strReturn = utiloperation.getOpenDepositNo(lID);
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
	* 查找用户可用的账户
	*/
	public static String findAccountByUser(long lUserID, long lCurrencyID) throws Exception
	{
		/*
		Connection con = null;
		SADao sa = null;
		String strReturn = "";
		try
		{
			con = Database.getConnection();
			sa = new SADao(con);
			Log.print("1----------");
			Collection cAccount = sa.findAccountByUser(lUserID, lCurrencyID);
			Log.print("2 ---------");
			if (cAccount != null)
			{
				Iterator itUser = cAccount.iterator();
				while (itUser.hasNext())
				{
					AccountInfo ai = (AccountInfo) itUser.next();
					strReturn = strReturn + "'" + ai.m_strAccountNo + "'" + ",";
				}
				Log.print("strReturn is :" + strReturn);
				if (strReturn.length() > 0)
				{
					strReturn = strReturn.substring(0, (strReturn.length() - 1));
				}
				Log.print("strReturn is :" + strReturn);
			}
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		finally
		{
			if (con != null)
			{
				con.close();
				con = null;
			}
		}
		return strReturn;
			*/
		return null;
	}

	/*
	 * 显示下拉列表
	 * @param out                输出
	 * @param strFieldName       控件的名称
	 * @param strFieldName       下一个控件的名称
	 * @param lValue             初始值
	 * @param lListType          下拉列表类型
	 */
	public static void ShowList(JspWriter out, String strFieldName, String strNextFieldName, long lValue, long lListType,long lOfficeID,long lCurrencyID) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String strSQL = "";
		switch ((int) lListType)
		{
			case (int) LOANConstant.ListType.LOANCLIENTTYPE :
				strSQL = " select id, sName from LOAN_CLIENTTYPE where nStatusID = " + Constant.RecordStatus.VALID + "order by sCode ";
				break;
			case (int) LOANConstant.ListType.SETTCLIENTTYPE :
				strSQL = " select id, sName from SETT_CLIENTTYPE where nStatusID = " + Constant.RecordStatus.VALID + "order by sCode ";
				break;
		}
		try
		{
			con = Database.getConnection();
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			out.println("<select class=\"box\" name=\"" + strFieldName + "\" onfocus=\"nextfield='" + strNextFieldName + "'\">");
			out.println("<option value=\"-1\"></option>");
			while (rs != null && rs.next())
			{
				if (lValue == rs.getLong(1))
				{
					out.println("<option value=\"" + rs.getLong(1) + "\" selected>" + rs.getString(2) + "</option>");
				}
				else
				{
					out.println("<option value=\"" + rs.getLong(1) + "\">" + rs.getString(2) + "</option>");
				}
			}
			out.println("</select>");
		}
		catch (Exception e)
		{
			throw new Exception(e.getMessage());
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
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception ex)
			{
				throw new Exception(ex.getMessage());
			}
		}
	}
	/*
		 * 显示下拉列表
		 * @param out                输出
		 * @param strFieldName       控件的名称
		 * @param strFieldName       下一个控件的名称
		 * @param lValue             初始值
		 * @param lListType          下拉列表类型
		 */
	public static void ShowList(JspWriter out, String strFieldName, String strNextFieldName, long lValue, long lListType, boolean isDisable,long lOfficeID,long lCurrencyID) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String strSQL = "";
		switch ((int) lListType)
		{
			case (int) LOANConstant.ListType.LOANCLIENTTYPE :
				strSQL = " select id, sName from LOAN_CLIENTTYPE where nStatusID = " + Constant.RecordStatus.VALID + "order by sCode ";
				break;
			case (int) LOANConstant.ListType.SETTCLIENTTYPE :
				strSQL = " select id, sName from SETT_CLIENTTYPE where nStatusID = " + Constant.RecordStatus.VALID + "order by sCode ";
				break;
		}
		try
		{
			con = Database.getConnection();
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (isDisable)
				out.println("<select  class=\"select\" name=\"" + strFieldName + "\" onfocus=\"nextfield='" + strNextFieldName + "'\" disabled>");
			else
				out.println("<select  class=\"select\" name=\"" + strFieldName + "\" onfocus=\"nextfield='" + strNextFieldName + "'\">");
			out.println("<option value=\"-1\"></option>");
			while (rs != null && rs.next())
			{
				if (lValue == rs.getLong(1))
				{
					out.println("<option value=\"" + rs.getLong(1) + "\" selected>" + rs.getString(2) + "</option>");
				}
				else
				{
					out.println("<option value=\"" + rs.getLong(1) + "\">" + rs.getString(2) + "</option>");
				}
			}
			out.println("</select>");
		}
		catch (Exception e)
		{
			throw new Exception(e.getMessage());
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
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception ex)
			{
				throw new Exception(ex.getMessage());
			}
		}
	}

	/**
	 * 显示企业类型下拉列表显示控件
	 * @param out 输出
	 * @param strControlName 控件名称
	 * @param lSelectValue 被选择项对应值
	 * @param isNeedAll 是否需要”全部“项
	 * @param strProperty 下拉列表属性
	 * @throws Exception
	 */
	public static void showEnterpriseTypeListCtrl(JspWriter out, String strControlName, long lSelectValue, boolean isNeedAll, String strProperty,long lOfficeID,long lCurrencyID) throws Exception
	{
		StringBuffer sbSQL = new StringBuffer();
		sbSQL.append(" select  id, sname ");
		sbSQL.append(" from SETT_ENTERPRICETYPE ");
		sbSQL.append(" where NSTATUSID = 1 ");
		sbSQL.append(" and nOfficeID = "+lOfficeID);
		sbSQL.append(" order by id");
		
		String strDisplayField = "sname";
		String strID = "id";
		showCommonListControl(out, strControlName, sbSQL.toString(), strDisplayField, strID, lSelectValue, isNeedAll, strProperty, true);
	}
	/**
	 * 通用下拉列表显示控件
	 * @param out 输出
	 * @param strControlName 控件名称
	 * @param strSQL 数据库查询语句
	 * @param strDisplayField 显示字段
	 * @param strID 显示字段对应标识
	 * @param lSelectValue 被选择项对应值
	 * @param isNeedAll 是否需要”全部“项
	 * @param strProperty 下拉列表属性
	 * @param isNeedBlank 是否需要空白行
	 * @throws Exception
	 */
	public static void showCommonListControl(
		JspWriter out,
		String strControlName,
		String strSQL,
		String strDisplayField,
		String strID,
		long lSelectValue,
		boolean isNeedAll,
		String strProperty,
		boolean isNeedBlank)
		throws Exception
	{
		long lResult = -1;
		ResultSet rs = null;
		PreparedStatement ps = null;
		Connection con = null;
		long lTemplateID = 0;
		long lLobID = -1;
		Vector v = new Vector();
		try
		{
			//校验输入的sql语句里是否包含strDisplayField和strID
			if (strSQL.indexOf(strID) == -1 || strSQL.indexOf(strDisplayField) == -1)
			{
				System.out.println("传入的参数不正确！");
				return;
			}

			out.println("<select class=\"select\" name=\"" + strControlName + "\"" + strProperty + ">");
			if (isNeedBlank == true)
			{
				out.println("<option value=\"-1\">&nbsp;</option>");
			}
			con = Database.getConnection();
			String strTmp = new String();
			strTmp = strSQL;
			//System.out.println(strTmp);
			ps = con.prepareStatement(strTmp);
			rs = ps.executeQuery();
			String strSelected = "";
			while (rs.next())
			{
				if (rs.getLong(strID) == lSelectValue)
				{
					strSelected = "selected";
				}
				out.println("<option value=\"" + rs.getLong(strID) + "\"" + strSelected + ">" + rs.getString(strDisplayField) + "</option>");
				strSelected = "";
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
			if (isNeedAll == true)
			{
				if (lSelectValue == 0)
				{
					out.println("<option value=\"0\" selected>全部</option>");
				}
				else
				{
					out.println("<option value=\"0\" >全部</option>");
				}
			}
			out.println("</select>");
		}
		catch (Exception e)
		{
			//System.out.println(" can not select OFFICE, because " + e);
			//throw e;
			System.out.println(e.toString());
		}
		finally
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
			if (con != null)
			{
				con.close();
				con = null;
			}
		}
	}

	/**
		 * 显示定期存款期限下拉列表显示控件
		 * @param out 输出
		 * @param strControlName 控件名称
		 * @param lSelectValue 被选择项对应值（定期存款期限）
		 * @param isNeedAll 是否需要”全部“项
		 * @param strProperty 下拉列表属性
		 * @throws Exception
		 */
	public static void showFixedDepositMonthListCtrl(JspWriter out, String strControlName, long lSelectValue, boolean isNeedAll, String strProperty) throws Exception
	{
		StringBuffer sbSQL = new StringBuffer();
		sbSQL.append(" select distinct nFixedDepositMonthID as DepositMonthID,(nFixedDepositMonthID || '个月') as DepositMonthDesc");
		sbSQL.append(" from Sett_SubAccountType_Fixed");
		sbSQL.append(" where nStatusID=1 and nAccountTypeID=2 and nFixedDepositMonthID < 10000");
		sbSQL.append(" union");
		sbSQL.append(" select distinct nFixedDepositMonthID as DepositMonthID,((nFixedDepositMonthID-10000) || '天') as DepositMonthDesc");
		sbSQL.append(" from Sett_SubAccountType_Fixed");
		sbSQL.append(" where nStatusID=1 and nAccountTypeID=2 and nFixedDepositMonthID>10000");
		String strDisplayField = "DepositMonthDesc";
		String strID = "DepositMonthID";
		showCommonListControl(out, strControlName, sbSQL.toString(), strDisplayField, strID, lSelectValue, isNeedAll, strProperty, true);
	}
	/**
		 * 显示通知存款品种下拉列表显示控件
		 * @param out 输出
		 * @param strControlName 控件名称
		 * @param lSelectValue 被选择项对应值（通知存款品种）
		 * @param isNeedAll 是否需要”全部“项
		 * @param strProperty 下拉列表属性
		 * @throws Exception
		 */
	public static void showNotifyDepositDayListCtrl(JspWriter out, String strControlName, long lSelectValue, boolean isNeedAll, String strProperty) throws Exception
	{
		StringBuffer sbSQL = new StringBuffer();
		sbSQL.append(" select distinct nFixedDepositMonthID as DepositMonthID,(nFixedDepositMonthID || '个月') as DepositMonthDesc");
		sbSQL.append(" from Sett_SubAccountType_Fixed");
		sbSQL.append(" where nStatusID=1 and nAccountTypeID=3 and nFixedDepositMonthID < 10000");
		sbSQL.append(" union");
		sbSQL.append(" select distinct nFixedDepositMonthID as DepositMonthID,((nFixedDepositMonthID-10000) || '天') as DepositMonthDesc");
		sbSQL.append(" from Sett_SubAccountType_Fixed");
		sbSQL.append(" where nStatusID=1 and nAccountTypeID=3 and nFixedDepositMonthID>10000");
		String strDisplayField = "DepositMonthDesc";
		String strID = "DepositMonthID";
		showCommonListControl(out, strControlName, sbSQL.toString(), strDisplayField, strID, lSelectValue, isNeedAll, strProperty, true);
	}
	
	/**
	 * 币种下拉列表显示控件(带nextfield)
	 * @param out 输出
	 * @param strFieldName 域的名称
	 * @param strDesc  其他属性
	 * @param currency 已选中的币种
	 *
	 */
	public static void showCurrencyList(JspWriter out, String strFieldName, String strDesc,long currencyid) throws Exception
	{
		try
		{
			long[] currencyID = Constant.CurrencyType.getAllCode();
			int tmpInt = currencyID.length;
			
			out.println("<select  class=\"select\" name="+strFieldName+" "+strDesc+" >");
			
			//循环输出
			for(int i=0;i<=tmpInt;i++)
			{
				if (i==0)
				{
					out.print("<option  value=\"0\"");
				}
				else
				{
					out.print("<option  value=\""+ currencyID[i-1] +"\"");
				}
				
				if (i>0 && currencyid==currencyID[i-1])
				{
					out.print("selected");
				}
				
				if (i==0)
				{
					out.print(">全部</option>");
				}
				else
				{
					out.print(">"+Constant.CurrencyType.getName(currencyID[i-1])+"</option>");
				}
				
			}
				
			out.println("</select>");  			  	
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	/**
	 *  下拉框控件(选择下属单位账号)
	 * @param  JspWriter out, String strFileName, OBAccountQueryWhere obaqw,
	 * @param  long lAccountGroupID,
	 * @param  Collection list, long lClientID, long lAccountID
	 * @return Collection
	 * @exception throws Exception
	 */
	public static void showHisAccountListControl(
		JspWriter out, 
		String strFileName, 
		OBAccountQueryWhere obaqw,
		long lAccountGroupTypeID, 
		long lParentClentID,
		long lClientID, 
		long lAccountID,
		String strJS) 
		throws Exception {
		OBAccountQueryAmountBiz accountQBiz= new OBAccountQueryAmountBiz(); 
		 accountQBiz.showAccountListControl(out,strFileName,obaqw,lAccountGroupTypeID,lParentClentID,lClientID,lAccountID,strJS);
	}
	/**
	 *  下拉框控件(选择下属单位)
	 * @param  JspWriter out, OBAccountQueryWhere obaqw, long lParentCorpID, 
	 * @param  long lAccountGroupTypeID, long lClientID
	 * @return Collection
	 * @exception throws Exception
	 */
	public static void showHisClientListControl(
		JspWriter out, 
		String strFileName, 
		OBAccountQueryWhere obaqw, 
		long lParentCorpID, 
		long lAccountGroupTypeID, 
		long lClientID,
		long lOfficeid,
		boolean child) 
		throws Exception {
		OBAccountQueryAmountBiz accountQBiz= new OBAccountQueryAmountBiz();
		 accountQBiz.showClientListControl(out,strFileName,obaqw,lParentCorpID,lAccountGroupTypeID,lClientID,lOfficeid,true);
	}
	/**
	 * 账户类型下拉框
	 * @param out
	 * @param strFileName
	 * @param jsMethod
	 * @param obaqw
	 * @param lAccountGroupId
	 * @throws Exception
	 */
	public static void showAccountGroupList (
			JspWriter out, 
			String strFileName, 
			String jsMethod,
			OBAccountQueryWhere obaqw, 
			long lAccountGroupId
			) throws Exception {
		OBAccountQueryAmountBiz accountQBiz= new OBAccountQueryAmountBiz();
		Collection listType = null;
		Iterator iterator = null; 
		out.println("<select class=\"select\" name=\"" + strFileName + "\" onChange=\""+jsMethod+"\" >");
		out.println("<option value=\"-1\">全部</option>");
		listType = accountQBiz.getCodeInfo(SETTConstant.AccountGroupType.getAllCode(obaqw.getOfficeID(),obaqw.getCurrencyID()));
        if(listType != null) {
            iterator = listType.iterator();
        }
         while (iterator != null && iterator.hasNext()) {
        	OBAccountQueryAmountInfo obaqai = (OBAccountQueryAmountInfo) iterator.next();
        	long lGroupId = obaqai.getAccountGroupID();
			String strSelected = "";
			if (lGroupId == lAccountGroupId) {
				strSelected = " selected";
			}
        	out.println(
    				"<option value=\""
    					+ lGroupId
    					+ "\""
    					+ strSelected
    					+ ">"
    					+ obaqai.getAccountGroupName()
    					+ "</option>");
        } 
		out.println("</select>");
	}
	
	/**
	* 资金计划申报业务状态下拉列表显示控件(带nextfield)
	* @author ylguo
	* @param out 输出
	* @param strFieldName 域的名称
	* @param lData 数据
	* @param strDesc  其他属性
	*
	*/
	public static void showQueryStatusListControlForfundPlan(JspWriter out, String strFieldName, long lData, String strDesc,String nextfield) throws Exception
	{
		try
		{
			out.println("<select " + strDesc + " class=\"select\" name=\"" + strFieldName + "\" onFocus=\"nextfield ='"+nextfield+"';\">");
			out.println("<option  value=\"-1\">全部</option>");

			String strSelected = "";
			long[] lTemp =
				{
					OBConstant.SettInstrStatus.SAVE,
					OBConstant.SettInstrStatus.CHECK,
					OBConstant.SettInstrStatus.APPROVALED
					 };
			for (int i = 0; i < lTemp.length; i++)
			{
				if (lTemp[i] == lData)
				{
					strSelected = "selected";
				}
				else
				{
					strSelected = "";
				}
				out.println("<option value=\"" + lTemp[i] + "\"" + strSelected + ">" + OBConstant.SettInstrStatus.getName(lTemp[i]) + "</option>");
				strSelected = "";
			}
			out.println("</select>");
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
	}
	
	/**
	 * 业务复核业务状态下拉列表显示控件(带nextfield)
	 * @param out 输出
	 * @param strFieldName 域的名称
	 * @param lData 选定的数据
	 * @param strDesc  其他属性
	 * @param nType  数据类型
	 */
	public static void showConsignReceiveStatusListControl(JspWriter out, String strFieldName, long lData, String strDesc, int nType) throws Exception
	{
		try
		{
			out.println("<select " + strDesc + " class=\"select\" name=\"" + strFieldName + "\">");
			out.println("<option value=\"-1\">全部</option>");
			String strSelected = "";
			long[] lTemp = null;
			switch (nType) {
			case 1:   //委托收款发起
				lTemp = new long[]{ OBConstant.SettInstrStatus.SAVE, OBConstant.SettInstrStatus.SUBMIT,
						OBConstant.SettInstrStatus.CONFIRM, OBConstant.SettInstrStatus.REFUSE };
				break;
			case 2:   //委托收款确认
				lTemp = new long[]{ OBConstant.SettInstrStatus.SUBMIT,OBConstant.SettInstrStatus.CONFIRM, 
						OBConstant.SettInstrStatus.REFUSE };
				break;
			}			
			
			for (int i = 0; i < lTemp.length; i++)
			{
				if (lTemp[i] == lData)
				{
					strSelected = "selected";
				}
				else
				{
					strSelected = "";
				}
				out.println("<option value=\"" + lTemp[i] + "\"" + strSelected + ">" + OBConstant.SettInstrStatus.getName(lTemp[i]) + "</option>");
				strSelected = "";
			}
			out.println("</select>");
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
	}
	
	public static void showOfficeListControl(JspWriter out,String strFieldName,long selectedValue,String strProperties,boolean isNeedAll,boolean isNeedBlank,long clientId)throws Exception
	{
		OBFinanceInstrBiz biz = new OBFinanceInstrBiz();
		ArrayList list = new ArrayList();
		OfficeInfo info = new OfficeInfo();
		try
		{
			list = biz.findOfficeInformation(clientId);
			out.println("<select name='"+strFieldName+"'  class=\"select\" "+strProperties+">");
			if(isNeedAll)
			{
				out.println("<option value='-2'>全部</option>");
			}
			if(isNeedBlank)
			{
				out.println("<option value='-1'></option>");
			}
			if(list!=null)
			{
				Iterator it = list.iterator();
				while(it.hasNext())
				{
					info = (OfficeInfo)it.next();
					if(selectedValue>-1&&selectedValue==info.getM_lID())
					{
						out.println("<option value='"+info.getM_lID()+"' selected>"+info.getM_strName()+"</option>");
					}
					else
					{
						out.println("<option value='"+info.getM_lID()+"'>"+info.getM_strName()+"</option>");
					}
				}
			}
			out.println("</select>");
		}catch(Exception e)
		{
			e.printStackTrace();
			log4j.error(e.toString());
			throw new IException("获取机构信息错误!",e);
		}
	}
	
	   
}