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
	private static String ZOOMERRORMSG = "�Ŵ󾵲������ô���!";

	private static Log4j log4j = null;

	public OBHtmlCom()
	{
		log4j = new Log4j(Constant.ModuleType.EBANK, this);
	}

	/**
	 * ͨ�������б���ʾ�ؼ�(��nextfield)
	 * @param out ���
	 * @param strFieldName �������
	 * @param strID ID������
	 * @param strName �����ֶε�����
	 * @param strTable ����
	 * @param strCondition ����
	 * @param lData ����
	 * @param strDesc  ��������
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
	 * ��ʾǩ�������������ؼ�
	 * @param strFieldName �������
	 * @param lCodeID ���룬���>0��ʾѡ������ؼ�
	 *JspWriter out,
	 */
	public static void showSignUserControl(JspWriter out, String strFieldName, long lCodeID, String strFieldDesc, long lClientID, long lOfficeID, long lCurrencyID, long lUserID) throws Exception
	{

		PreparedStatement ps = null; //
		ResultSet rs = null; //
		Connection conn = null; //
		String strSQL = null; //��ѯ��
		try
		{
			out.println("<select  class=\"select\"  name=\"" + strFieldName + "\"  " + strFieldDesc + ">");
			out.println("<option  value=\"-1\">������</option>");
			conn = Database.getConnection(); //���ݿ�����
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
	 * ��ʾǩ�������������ؼ�
	 * @param strFieldName �������
	 * @param lCodeID ���룬���>0��ʾѡ������ؼ�
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
	 * ��ʾ�����˺����������ؼ�
	 * @param strFieldName �������
	 * @param lCodeID ���룬���>0��ʾѡ������ؼ�
	 *JspWriter out,
	 *lClientID ��¼�ͻ�ID
	 *lCurrencyID ��¼����
	 *lUserID ��¼�û�ID,���Ϊ-1������ʾȫ��
	 */
	public static void showPayerBankCodeControl1(JspWriter out, String strFieldName, long lAccountID, String strFieldDesc, long lClientID, long lCurrencyID, long lUserID,long lOfficeID) throws Exception
	{

		PreparedStatement ps = null; //
		ResultSet rs = null; //
		Connection conn = null; //
		String strSQL = null; //��ѯ��
		try
		{
			out.println("<select  class=\"select\"  name=\"" + strFieldName + "\"  " + strFieldDesc + ">");
			out.println("<option value=\"\"></option>");
			conn = Database.getConnection(); //���ݿ�����

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
	 * ��ʾ�����˺����������ؼ�
	 * @param strFieldName �������
	 * @param lCodeID ���룬���>0��ʾѡ������ؼ�
	 *JspWriter out,
	 *lClientID ��¼�ͻ�ID
	 *lCurrencyID ��¼����
	 *lUserID ��¼�û�ID,���Ϊ-1������ʾȫ��
	 *lSAID����ԱID
	 */
	public static void showPayerBankCodeControl1(JspWriter out, String strFieldName, long lAccountID, String strFieldDesc, long lClientID, long lCurrencyID, long lUserID,long lOfficeID,long lSAID) throws Exception
	{

		PreparedStatement ps = null; //
		ResultSet rs = null; //
		Connection conn = null; //
		String strSQL = null; //��ѯ��
		try
		{
			out.println("<select  class=\"select\"  name=\"" + strFieldName + "\"  " + strFieldDesc + ">");
			out.println("<option value=\"\"></option>");
			conn = Database.getConnection(); //���ݿ�����

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
	 * ��ʽ�����б���ʾ�ؼ�(��nextfield)
	 * @param out ���
	 * @param strFieldName �������
	 * @param lData ����
	 * @param strDesc  ��������
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
	 * �벻Ҫ��ʹ�ø÷�����ʾ��ʽ�����б���ʹ��com.iss.itreasury.ebank.util.OBConstant.SettRemitType.showList����
	 * 
	 * ��ʽ�����б���ʾ�ؼ�(��nextfield)
	 * @param out ���
	 * @param strFieldName �������
	 * @param lData ����
	 * @param strDesc  ��������
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
		 * ��ʽ�����б���ʾ�ؼ�(��nextfield)
		 * @param out ���
		 * @param strFieldName �������
		 * @param lData ����
		 * @param strDesc  ��������
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
	 * ��ʽ�����б���ʾ�ؼ�(��nextfield)
	 * @param out ���
	 * @param strFieldName �������
	 * @param lData ����
	 * @param strDesc  ��������
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
	 * ��ʽ�����б���ʾ�ؼ�(��nextfield)
	 * @param out ���
	 * @param strFieldName �������
	 * @param lData ����
	 * @param strDesc  ��������
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
	 * ��ʽ�����б���ʾ�ؼ�(��nextfield)
	 * @param out ���
	 * @param strFieldName �������
	 * @param lData ����
	 * @param strDesc  ��������
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
			out.println("<option value=\"11\">����</option>");
			out.println("</select>");
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
	}
	
	/**
		 * ҵ�񸴺�ҵ�����������б���ʾ�ؼ�(��nextfield)
		 * @param out ���
		 * @param strFieldName �������
		 * @param lData ����
		 * @param strDesc  ��������
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
	 * ҵ�񸴺�ҵ�����������б���ʾ�ؼ�(��nextfield)
	 * @param out ���
	 * @param strFieldName �������
	 * @param lData ����
	 * @param strDesc  ��������
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
	 * ҵ�񸴺�ҵ�����������б���ʾ�ؼ�(�°»���ҵ��ǩ��)
	 * @param out ���
	 * @param strFieldName �������
	 * @param lData ����
	 * @param strDesc  ��������
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
	 * ҵ�񸴺�ҵ�����������б���ʾ�ؼ�(�°¶���ҵ��ǩ��)
	 * @param out ���
	 * @param strFieldName �������
	 * @param lData ����
	 * @param strDesc  ��������
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
     * У���������ˣ�ȡ��������ʾ�б��������������������Զ�����ʱ������ʾ������
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
			// �Ƿ��Ƿ����ù����¼���λ
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
	 * ҵ�񸴺�ҵ�����������б���ʾ�ؼ�(��nextfield)
	 * @param out ���
	 * @param strFieldName �������
	 * @param lData ����
	 * @param strDesc  ��������
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
	 * ȡ������-���� ҵ�����������б���ʾ�ؼ����°���Ŀ������
	 * @param out ���
	 * @param strFieldName �������
	 * @param lData ����
	 * @param strDesc  ��������
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
	 * ȡ������-���� ҵ�����������б���ʾ�ؼ����°���Ŀ������
	 * @param out ���
	 * @param strFieldName �������
	 * @param lData ����
	 * @param strDesc  ��������
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
	 * ҵ�񸴺�ҵ�����������б���ʾ�ؼ�(��nextfield)
	 * @param out ���
	 * @param strFieldName �������
	 * @param lData ����
	 * @param strDesc  ��������
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
	 * ҵ�񸴺�ҵ�����������б���ʾ�ؼ�(��nextfield)
	 * @param out ���
	 * @param strFieldName �������
	 * @param lData ����
	 * @param strDesc  ��������
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
	 * ҵ�񸴺�ҵ��״̬�����б���ʾ�ؼ�(��nextfield)
	 * @param out ���
	 * @param strFieldName �������
	 * @param lData ����
	 * @param strDesc  ��������
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
	 * ǩ��ҵ��״̬�����б���ʾ�ؼ�(��nextfield)
	 * @param out ���
	 * @param strFieldName �������
	 * @param lData ����
	 * @param strDesc  ��������
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
	* ���������ѯҵ��״̬�����б���ʾ�ؼ�(��nextfield)
	* @author yanliu
	* @param out ���
	* @param strFieldName �������
	* @param lData ����
	* @param strDesc  ��������
	*
	*/
	public static void showQueryStatusListControl(JspWriter out, String strFieldName, long lData, String strDesc) throws Exception
	{
		try
		{
			out.println("<select " + strDesc + " class=\"select\" name=\"" + strFieldName + "\">");
			out.println("<option  value=\"-1\">ȫ��</option>");

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
			out.println("<option  value=\"-1\">ȫ��</option>");

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
			out.println("<option  value=\"-1\">ȫ��</option>");

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
	* ���������ѯҵ��״̬�����б���ʾ�ؼ�(��nextfield) ��������״̬ 2007-10-29
	* @author zcwang 
	* @param out ���
	* @param strFieldName �������
	* @param lData ����
	* @param strDesc  ��������
	*
	*/
	public static void showQueryStatusListControlBase(JspWriter out, String strFieldName, long lData, String strDesc) throws Exception
	{
		try
		{
			out.println("<select " + strDesc + " class=\"select\" name=\"" + strFieldName + "\">");
			out.println("<option  value=\"-1\">ȫ��</option>");

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
		Function: ������ؼ�(ѡ���˺�)  --  �˻���ʷ��ϸ
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
					if(rs.getLong("Naccounttypeid")!=12){//���Ʋ��ܳ���"12��ס��������"
					
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
		String strMagnifierName = URLEncoder.encode("�տ�˺�");
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

		String[] strDisplayNames = { URLEncoder.encode("�տ�˺�"), URLEncoder.encode("�տ����")};
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
		String strMagnifierName = URLEncoder.encode("�տ�˺�");
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

		String[] strDisplayNames = { URLEncoder.encode("�տ�˺�"), URLEncoder.encode("�տ����")};
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
			lContractTypeIDs = new long[] { LOANConstant.LoanType.ZY, LOANConstant.LoanType.ZGXE, LOANConstant.LoanType.YT};

		}
		//ί�� : WT  WTTJTH
		else if (lTransactionType == SETTConstant.TransactionType.CONSIGNLOANGRANT || lTransactionType == SETTConstant.TransactionType.CONSIGNLOANRECEIVE)
		{
			lContractTypeIDs = new long[] { LOANConstant.LoanType.WT};
		}
		//���� : TX
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
		//ҵ����
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
			Log.print("��ͬ�Ŵ�[" + strCtrlName + "]�쳣��" + e.toString());
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
	 * @param lPayFormTypeID �ſ�֪ͨ������(��ѯ����:1,���У�2��ί��)
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
	 * @param strRtnStartDateCtrl ����ֵ����ʼ���ڣ���Ӧ�Ŀؼ���
	 * @param strRtnEndDateCtrl ����ֵ���������ڣ���Ӧ�Ŀؼ���
	 * @param strRtnSubAccountIDCtrl ����ֵ�����˻�ID����Ӧ�Ŀؼ���
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

		String[] strMainNames = { strCtrlName + "Ctrl", strRtnStartDateCtrl, strRtnEndDateCtrl, strRtnSubAccountIDCtrl };
		String[] strMainFields = { "PayFormCode", "StartDate", "EndDate", "SubAccountID" };

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
			lContractTypeIDs = new long[] { LOANConstant.LoanType.ZY, LOANConstant.LoanType.ZGXE, LOANConstant.LoanType.YT};

		}
		//ί�� : WT  WTTJTH
		else if (lPayFormTypeID == 2)
		{
			lContractTypeIDs = new long[] { LOANConstant.LoanType.WT};
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
			Log.print("�Ŵ�[" + strCtrlName + "]�쳣��" + e.toString());
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
		String strMagnifierName = URLEncoder.encode("����˺�");
		String strMainProperty = " value='" + sBankAccountCode + "'";
		String strPrefix = "";

		String[] strMainNames = { strCtrlName + "Ctrl", strRelativeForm1, strRelativeForm2 };
		String[] strMainFields = { "saccountno", "", "" };

		String[] strMainName = { strCtrlName + "Ctrl" }; //�տ
		String[] strMainField = { "saccountno" }; //�տ

		String[] strReturnNames = { "sBankAccountNO" };
		String[] strReturnFields = { "sbankaccountno" };
		String[] strReturnValues = { "" };

		String[] strReturnName = { "sPayeeBankAccountNOInternal" }; //�տ
		String[] strReturnField = { "sbankaccountno" }; //�տ
		String[] strReturnValue = { "" }; //�տ

		String[] strDisplayNames = { URLEncoder.encode("�ڲ��˺�"), URLEncoder.encode("�����˺�")};
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
		String strRtnStartDateCtrl)
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

		String[] strDisplayNames = { URLEncoder.encode("���ݺ�")};
		String[] strDisplayFields = { "DepositNo" };
		//֧ȡ������ת��ʱ����ʾ��ͬ
		if (lTypeID == 2 || lTypeID == 3)
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
				out.println("<td " + strFirstTD + ">" + strTitle + ":&nbsp;" + "<a href=#><img name=\"" + strButtonName + "\" src='/webob/zoom.gif' border=0 onclick=\"" + sOnKeydown + "\"></a></td>");
				//image
			}
			else
			{
				out.println("<td " + strFirstTD + ">" + strTitle + ":&nbsp;" + "<a href=#><img name=\"" + strButtonName + "\" src='/webob/zoom.gif' border=0 ></a></td>");
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
			//��ʾ�ؼ�
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
	* �����û����õ��˻�
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
	 * ��ʾ�����б�
	 * @param out                ���
	 * @param strFieldName       �ؼ�������
	 * @param strFieldName       ��һ���ؼ�������
	 * @param lValue             ��ʼֵ
	 * @param lListType          �����б�����
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
		 * ��ʾ�����б�
		 * @param out                ���
		 * @param strFieldName       �ؼ�������
		 * @param strFieldName       ��һ���ؼ�������
		 * @param lValue             ��ʼֵ
		 * @param lListType          �����б�����
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
	 * ��ʾ��ҵ���������б���ʾ�ؼ�
	 * @param out ���
	 * @param strControlName �ؼ�����
	 * @param lSelectValue ��ѡ�����Ӧֵ
	 * @param isNeedAll �Ƿ���Ҫ��ȫ������
	 * @param strProperty �����б�����
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
	 * ͨ�������б���ʾ�ؼ�
	 * @param out ���
	 * @param strControlName �ؼ�����
	 * @param strSQL ���ݿ��ѯ���
	 * @param strDisplayField ��ʾ�ֶ�
	 * @param strID ��ʾ�ֶζ�Ӧ��ʶ
	 * @param lSelectValue ��ѡ�����Ӧֵ
	 * @param isNeedAll �Ƿ���Ҫ��ȫ������
	 * @param strProperty �����б�����
	 * @param isNeedBlank �Ƿ���Ҫ�հ���
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
			//У�������sql������Ƿ����strDisplayField��strID
			if (strSQL.indexOf(strID) == -1 || strSQL.indexOf(strDisplayField) == -1)
			{
				System.out.println("����Ĳ�������ȷ��");
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
					out.println("<option value=\"0\" selected>ȫ��</option>");
				}
				else
				{
					out.println("<option value=\"0\" >ȫ��</option>");
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
		 * ��ʾ���ڴ�����������б���ʾ�ؼ�
		 * @param out ���
		 * @param strControlName �ؼ�����
		 * @param lSelectValue ��ѡ�����Ӧֵ�����ڴ�����ޣ�
		 * @param isNeedAll �Ƿ���Ҫ��ȫ������
		 * @param strProperty �����б�����
		 * @throws Exception
		 */
	public static void showFixedDepositMonthListCtrl(JspWriter out, String strControlName, long lSelectValue, boolean isNeedAll, String strProperty) throws Exception
	{
		StringBuffer sbSQL = new StringBuffer();
		sbSQL.append(" select distinct nFixedDepositMonthID as DepositMonthID,(nFixedDepositMonthID || '����') as DepositMonthDesc");
		sbSQL.append(" from Sett_SubAccountType_Fixed");
		sbSQL.append(" where nStatusID=1 and nAccountTypeID=2 and nFixedDepositMonthID < 10000");
		sbSQL.append(" union");
		sbSQL.append(" select distinct nFixedDepositMonthID as DepositMonthID,((nFixedDepositMonthID-10000) || '��') as DepositMonthDesc");
		sbSQL.append(" from Sett_SubAccountType_Fixed");
		sbSQL.append(" where nStatusID=1 and nAccountTypeID=2 and nFixedDepositMonthID>10000");
		String strDisplayField = "DepositMonthDesc";
		String strID = "DepositMonthID";
		showCommonListControl(out, strControlName, sbSQL.toString(), strDisplayField, strID, lSelectValue, isNeedAll, strProperty, true);
	}
	/**
		 * ��ʾ֪ͨ���Ʒ�������б���ʾ�ؼ�
		 * @param out ���
		 * @param strControlName �ؼ�����
		 * @param lSelectValue ��ѡ�����Ӧֵ��֪ͨ���Ʒ�֣�
		 * @param isNeedAll �Ƿ���Ҫ��ȫ������
		 * @param strProperty �����б�����
		 * @throws Exception
		 */
	public static void showNotifyDepositDayListCtrl(JspWriter out, String strControlName, long lSelectValue, boolean isNeedAll, String strProperty) throws Exception
	{
		StringBuffer sbSQL = new StringBuffer();
		sbSQL.append(" select distinct nFixedDepositMonthID as DepositMonthID,(nFixedDepositMonthID || '����') as DepositMonthDesc");
		sbSQL.append(" from Sett_SubAccountType_Fixed");
		sbSQL.append(" where nStatusID=1 and nAccountTypeID=3 and nFixedDepositMonthID < 10000");
		sbSQL.append(" union");
		sbSQL.append(" select distinct nFixedDepositMonthID as DepositMonthID,((nFixedDepositMonthID-10000) || '��') as DepositMonthDesc");
		sbSQL.append(" from Sett_SubAccountType_Fixed");
		sbSQL.append(" where nStatusID=1 and nAccountTypeID=3 and nFixedDepositMonthID>10000");
		String strDisplayField = "DepositMonthDesc";
		String strID = "DepositMonthID";
		showCommonListControl(out, strControlName, sbSQL.toString(), strDisplayField, strID, lSelectValue, isNeedAll, strProperty, true);
	}
	
	/**
	 * ���������б���ʾ�ؼ�(��nextfield)
	 * @param out ���
	 * @param strFieldName �������
	 * @param strDesc  ��������
	 * @param currency ��ѡ�еı���
	 *
	 */
	public static void showCurrencyList(JspWriter out, String strFieldName, String strDesc,long currencyid) throws Exception
	{
		try
		{
			long[] currencyID = Constant.CurrencyType.getAllCode();
			int tmpInt = currencyID.length;
			
			out.println("<select  class=\"select\" name="+strFieldName+" "+strDesc+" >");
			
			//ѭ�����
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
					out.print(">ȫ��</option>");
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
	 *  ������ؼ�(ѡ��������λ�˺�)
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
	 *  ������ؼ�(ѡ��������λ)
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
	 * �˻�����������
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
		out.println("<option value=\"-1\">ȫ��</option>");
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
	* �ʽ�ƻ��걨ҵ��״̬�����б���ʾ�ؼ�(��nextfield)
	* @author ylguo
	* @param out ���
	* @param strFieldName �������
	* @param lData ����
	* @param strDesc  ��������
	*
	*/
	public static void showQueryStatusListControlForfundPlan(JspWriter out, String strFieldName, long lData, String strDesc,String nextfield) throws Exception
	{
		try
		{
			out.println("<select " + strDesc + " class=\"select\" name=\"" + strFieldName + "\" onFocus=\"nextfield ='"+nextfield+"';\">");
			out.println("<option  value=\"-1\">ȫ��</option>");

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
	 * ҵ�񸴺�ҵ��״̬�����б���ʾ�ؼ�(��nextfield)
	 * @param out ���
	 * @param strFieldName �������
	 * @param lData ѡ��������
	 * @param strDesc  ��������
	 * @param nType  ��������
	 */
	public static void showConsignReceiveStatusListControl(JspWriter out, String strFieldName, long lData, String strDesc, int nType) throws Exception
	{
		try
		{
			out.println("<select " + strDesc + " class=\"select\" name=\"" + strFieldName + "\">");
			out.println("<option value=\"-1\">ȫ��</option>");
			String strSelected = "";
			long[] lTemp = null;
			switch (nType) {
			case 1:   //ί���տ��
				lTemp = new long[]{ OBConstant.SettInstrStatus.SAVE, OBConstant.SettInstrStatus.SUBMIT,
						OBConstant.SettInstrStatus.CONFIRM, OBConstant.SettInstrStatus.REFUSE };
				break;
			case 2:   //ί���տ�ȷ��
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
				out.println("<option value='-2'>ȫ��</option>");
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
			throw new IException("��ȡ������Ϣ����!",e);
		}
	}
	
	   
}