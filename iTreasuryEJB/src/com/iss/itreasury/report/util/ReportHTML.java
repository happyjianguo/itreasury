package com.iss.itreasury.report.util;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.jsp.JspWriter;

import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;

public class ReportHTML {

	/*
     * ��ʾ�����б� @param out ��� @param strFieldName �ؼ������� @param strFieldName
     * ��һ���ؼ������� @param lValue ��ʼֵ @param lListType �����б�����
     */
    public static void ShowLoanSubTypeList(JspWriter out, String strFieldName, String strNextFieldName, long lValue, long lListType, boolean isDisable,long lOfficeID,long lCurrencyID)
            throws RemoteException
    {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String strSQL = "";
        strSQL = " select a.id , a.name from loan_loantypesetting a, loan_loantyperelation b where statusID = " + Constant.RecordStatus.VALID + " " +
        		"and a.loantypeid = b.loantypeid and a.id = b.subloantypeid " +
        		"and a.loantypeid in ("+LOANConstant.LoanType.WT+", "+LOANConstant.LoanType.ZY+")" +
        		"order by id";

        try
        {
        	System.out.println(strSQL);
            con = Database.getConnection();
            ps = con.prepareStatement(strSQL);
            rs = ps.executeQuery();
            if (isDisable)
                out.println("<select class=\"box\" name=\"" + strFieldName + "\" onfocus=\"nextfield='" + strNextFieldName + "'\" disabled>");
            else
                out.println("<select class=\"box\" name=\"" + strFieldName + "\" onfocus=\"nextfield='" + strNextFieldName + "'\">");
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
        	e.printStackTrace();
            throw new RemoteException(e.getMessage());
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
                throw new RemoteException(ex.getMessage());
            }
        }
    }	
	
}
