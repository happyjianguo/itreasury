/*
 * Created on 2005-3-2
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.configtool.util;

import javax.servlet.jsp.JspWriter;
import java.lang.reflect.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import com.iss.itreasury.configtool.constantmanage.bizlogic.ConstantManager;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Log;

/**
 * @author hyzeng
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class CONFIGConstant {

	public static void main(String[] args) {
		System.out.println("**begin**");
		//showCommonList("com.iss.itreasury.configtool.util.CONFIGConstant$ModuleType","",
		// 1,"");
		System.out.println("**end**");
	}

	public static void showCommonList(String constantName, JspWriter out,
			String strControlName, long lSelectValue, String strProperty) {
		showCommonList(constantName, out, strControlName, lSelectValue, false,
				strProperty, true);
	}

	/**
	 * ��ʾͨ�������б�
	 * 
	 * @param out
	 * @param strControlName
	 * @param lArrayID
	 * @param strArrayName
	 * @param lSelectValue
	 * @param isNeedAll
	 * @param strProperty
	 * @param isNeedBlank
	 */
	public static void showCommonList(String constantName, JspWriter out,
			String strControlName, long lSelectValue, boolean isNeedAll,
			String strProperty, boolean isNeedBlank) {
		try {
			long[] lArrayID = null;
			String[] strArrayName = null;
			int i = 0;

			//�õ�һ��������
			Class constantClass = Class.forName(constantName);

			Method methods[] = constantClass.getDeclaredMethods();

			Object param[] = new Object[1];

			for (i = 0; i < methods.length; i++) {
				if (methods[i].getName().equalsIgnoreCase("getAllCode")) {
					lArrayID = (long[]) methods[i].invoke(constantClass, new Object[]{});
					break;
				}
			}

			for (i = 0; i < methods.length; i++) {
				if (methods[i].getName().equalsIgnoreCase("getName")) {
					strArrayName = new String[lArrayID.length];

					for (int n = 0; n < lArrayID.length; n++) {
						param[0] = new Long(lArrayID[n]);
						strArrayName[n] = (String) methods[i].invoke(
								constantClass, param);
					}
					break;
				}
			}

			out.println("<select name=\"" + strControlName + "\" "
					+ strProperty + ">");
			if (isNeedBlank == true) {
				if (lSelectValue == -1) {
					out.println("<option value='-1' selected>&nbsp;</option>");
				} else {
					out.println("<option value='-1'>&nbsp;</option>");
				}
			}
			for (i = 0; i < lArrayID.length; i++) {
				if (lArrayID[i] == lSelectValue) {
					out.println("<option value='" + lArrayID[i]
							+ "' selected >" + strArrayName[i] + "</option>");
				} else {
					out.println("<option value='" + lArrayID[i] + "'>"
							+ strArrayName[i] + "</option>");
				}
			}
			if (isNeedAll == true) {
				if (lSelectValue == 0) {
					out.println("<option value='0' selected>ȫ��</option>");
				} else {
					out.println("<option value='0'>ȫ��</option>");
				}
			}
			out.println("</select>");
		} catch (Exception ex) {
			Log.print("��ʾ�����б�����쳣��" + ex.toString());
		}
	}

	/**
	 * ͨ�������б���ʾ�ؼ�
	 * 
	 * @param out
	 *            ���
	 * @param strFieldName
	 *            �������
	 * @param strID
	 *            ID������
	 * @param strName
	 *            �����ֶε�����
	 * @param strTable
	 *            ����
	 * @param strCondition
	 *            ����
	 * @param lData
	 *            ���� Created by Hally Zhang,2002-01-31
	 *  
	 */
	public static void showCommonListControl(JspWriter out,
			String strFieldName, String strID, String strName, String strTable,
			String strCondition, long lData, String property) throws Exception {
		long lResult = -1;
		ResultSet rs = null;
		PreparedStatement ps = null;
		Connection con = null;
		long lTemplateID = 0;
		long lLobID = -1;
		Vector v = new Vector();
		try {
			out.println("<select class=box name=\"" + strFieldName + "\" "
					+ property + ">");
			con = Database.getConnection();
			String strTmp = new String();
			strTmp = " select " + strID + " id, " + strName + " name from "
					+ strTable + " " + strCondition;
			ps = con.prepareStatement(strTmp);
			rs = ps.executeQuery();
			String strSelected = "";
			while (rs.next()) {
				long lSupplierID = rs.getLong("ID");
				if (lSupplierID == lData) {
					strSelected = "selected";
				}
				out.println("<option value=\"" + rs.getLong("id") + "\""
						+ strSelected + ">" + rs.getString("name")
						+ "</option>");
				strSelected = "";
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
			out.println("</select>");
		} catch (Exception e) {
			System.out.println(" can not select OFFICE, because " + e);
			throw e;
		} finally {
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (con != null) {
				con.close();
				con = null;
			}
		}
	}

	public static final class ManageStatus {
		public static final long NOTSET = 0;//δ����

		public static final long MANAGE = 1;//����

		public static final long NOTMANAGE = 2;//������

		/**
		 * �õ����е�ģ��
		 * 
		 * @return long[]
		 */
		public static final long[] getAllCode() {
			return ConstantManager.getInstance()
					.getAllFields("com.iss.itreasury.configtool.util.CONFIGConstant$ManageStatus");
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant.getAllCode(
					"com.iss.itreasury.configtool.util.CONFIGConstant$ManageStatus",
					officeID, currencyID);
		}

		public static final String getName(long lCode) {
			String strReturn = ""; //��ʼ������ֵ
			switch ((int) lCode) {
			case (int) NOTSET:
				strReturn = "δ����";
				break;
			case (int) MANAGE:
				strReturn = "����";
				break;
			case (int) NOTMANAGE:
				strReturn = "������";
				break;
			}
			return strReturn;
		}

	}

	/**
	 * ģ������
	 * 
	 * @author yzhang
	 * 
	 * To change the template for this generated type comment go to
	 * Window>Preferences>Java>Code Generation>Code and Comments
	 */
	public static class ModuleType {
		//ģ������
		public static final long GLOBAL = 0; //ȫ��

		public static final long SETTLEMENT = 1; //����

		public static final long LOAN = 2; //����

		public static final long SYSTEM = 3; //ϵͳ����

		public static final long FOREIGN = 4; //���

		public static final long SECURITIES = 5; //֤ȯ

		public static final long EBANK = 6; //��������

		public static final long PLAN = 7; //�ʽ�ƻ�

		public static final long CLIENTCENTER = 8; // �ͻ�����

		// public static final long GENERALLEDGER = 9; // ����

		public static final long BILL = 10;//Ʊ�ݹ���
		
		public static final long CRAFTBROTHER = 15;//ͬҵ����
		
		public static final long CREDITRATING = 23;//��������  add by zcwang 2009-03-03
		
		public static final long EVOUCHER = 16;//���ӵ��ݹ�	add by xiangzhou 2011-5-24

		//
		/**
		 * �õ����е�ģ��
		 * 
		 * @return long[]
		 */
		public static final long[] getAllCode() {
			return ConstantManager.getInstance().getAllFields("com.iss.itreasury.configtool.util.CONFIGConstant$ModuleType");
		}

		/**
		 * 
		 * @param lCode
		 * @return @throws
		 *         Exception
		 */
		public static final String getModuleName(long lCode) throws Exception {
			String strReturn = ""; //��ʼ������ֵ
			switch ((int) lCode) {
			case (int) GLOBAL:
				strReturn = "util";
				break;
			case (int) SETTLEMENT:
				strReturn = "settlement";
				break;
			case (int) LOAN:
				strReturn = "loan";
				break;
			case (int) EBANK:
				strReturn = "EBANK";
				break;
			case (int) SECURITIES:
				strReturn = "SECURITIES";
				break;
			case (int) SYSTEM:
				strReturn = "SYSTEM";
				break;
			case (int) FOREIGN:
				strReturn = "FOREIGN";
				break;
			case (int) PLAN:
				strReturn = "treasuryplan";
				break;
			case (int) CLIENTCENTER:
				strReturn = "CLIENTCENTER";
				break;
			case (int) BILL:
				strReturn = "BILL";
				break;
			case (int) CRAFTBROTHER:
				strReturn = "CRAFTBROTHER";
				break;
			case (int) CREDITRATING:
				strReturn = "CREDITRATING";
				break;
			case (int) EVOUCHER:
				strReturn = "EVOUCHER";
			break;
			}
			return strReturn;
		}

		public static final long getModuleID(String lCode) throws Exception {
			long lReturn = -1; //��ʼ������ֵ
			if (lCode.equalsIgnoreCase("util")) {
				lReturn = GLOBAL;
			} else if (lCode.equalsIgnoreCase("settlement")) {
				lReturn = SETTLEMENT;
			} else if (lCode.equalsIgnoreCase("loan")) {
				lReturn = LOAN;
			} else if (lCode.equalsIgnoreCase("EBANK")) {
				lReturn = EBANK;
			} else if (lCode.equalsIgnoreCase("SECURITIES")) {
				lReturn = SECURITIES;
			} else if (lCode.equalsIgnoreCase("SYSTEM")) {
				lReturn = SYSTEM;
			} else if (lCode.equalsIgnoreCase("FOREIGN")) {
				lReturn = FOREIGN;
			} else if (lCode.equalsIgnoreCase("treasuryplan")) {
				lReturn = PLAN;
			} else if (lCode.equalsIgnoreCase("CLIENTCENTER")) {
				lReturn = CLIENTCENTER;
			} else if (lCode.equalsIgnoreCase("BILL")) {
				lReturn = BILL;
			}else if (lCode.equalsIgnoreCase("CRAFTBROTHER")) {
				lReturn = CRAFTBROTHER;
			}else if (lCode.equalsIgnoreCase("CREDITRATING")) {
				lReturn = CREDITRATING;
			}else if (lCode.equalsIgnoreCase("EVOUCHER")) {
				lReturn = EVOUCHER;
			}

			return lReturn;
		}

		public static final String getNameByModuleName(String moduleName)
				throws Exception {
			String strReturn = ""; //��ʼ������ֵ

			if (moduleName.equalsIgnoreCase("util")) {
				strReturn = "ȫ��";
			}
			if (moduleName.equalsIgnoreCase("settlement")) {
				strReturn = "����";
			}
			if (moduleName.equalsIgnoreCase("loan")) {
				strReturn = "����";
			}
			if (moduleName.equalsIgnoreCase("EBANK")) {
				strReturn = "��������";
			}
			if (moduleName.equalsIgnoreCase("SECURITIES")) {
				strReturn = "֤ȯ";
			}
			if (moduleName.equalsIgnoreCase("SYSTEM")) {
				strReturn = "ϵͳ����";
			}
			if (moduleName.equalsIgnoreCase("FOREIGN")) {
				strReturn = "���";
			}
			if (moduleName.equalsIgnoreCase("treasuryplan")) {
				strReturn = "�ʽ�ƻ�";
			}
			if (moduleName.equalsIgnoreCase("CLIENTCENTER")) {
				strReturn = "�ͻ�����";
			}
			if (moduleName.equalsIgnoreCase("BILL")) {
				strReturn = "Ʊ�ݹ���";
			}
			if (moduleName.equalsIgnoreCase("CRAFTBROTHER")) {
				strReturn = "ͬҵ����";
			}
			if (moduleName.equalsIgnoreCase("CREDITRATING")) {
				strReturn = "��������";
			}
			if (moduleName.equalsIgnoreCase("EVOUCHER")) {
				strReturn = "���ӵ��ݹ�";
			}
			return strReturn;
		}

		public static final String getName(long lCode) throws Exception {
			String strReturn = ""; //��ʼ������ֵ
			switch ((int) lCode) {
			case (int) GLOBAL:
				strReturn = "ȫ��";
				break;
			case (int) SETTLEMENT:
				strReturn = "����";
				break;
			case (int) LOAN:
				strReturn = "����";
				break;
			case (int) EBANK:
				strReturn = "��������";
				break;
			case (int) SECURITIES:
				strReturn = "֤ȯ";
				break;
			case (int) SYSTEM:
				strReturn = "ϵͳ����";
				break;
			case (int) FOREIGN:
				strReturn = "���";
				break;
			case (int) PLAN:
				strReturn = "�ʽ�ƻ�";
				break;
			case (int) CLIENTCENTER:
				strReturn = "�ͻ���Ϣ����";
				break;
			case (int) BILL:
				strReturn = "Ʊ�ݹ���";
				break;
			case (int) CRAFTBROTHER:
				strReturn = "ͬҵ����";
				break;
			case (int) CREDITRATING:
				strReturn = "��������";
				break;
			case (int) EVOUCHER:
				strReturn = "���ӵ��ݹ�";
			break;
			}
			return strReturn;
		}

	}
}