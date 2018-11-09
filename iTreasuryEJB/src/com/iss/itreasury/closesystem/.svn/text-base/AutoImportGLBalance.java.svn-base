/*
 * Created on 2004-7-20
 * 
 * TODO To change the template for this generated file go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.closesystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;

/**
 * @author yychen
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class AutoImportGLBalance
{

    public static void main(String[] args)
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conn = null;
        long lOfficeID = 1;
        long lCurrencyID = 1;
        Timestamp tsImportDate = null;
        StringBuffer sb = new StringBuffer();
        try
        {
            conn = Database.getConnection();
        	System.out.println("Begin..."); 
            sb.append("select nOfficeID,nCurrencyID,to_date(to_char(sysdate,'yyyymmdd'),'yyyymmdd') dtSystemDate from sett_officetime where nstatusid = " + Constant.RecordStatus.VALID);
            ps = conn.prepareStatement(sb.toString());
            rs = ps.executeQuery();
            while ( rs.next() )
            {
                lOfficeID = rs.getLong("nOfficeID");
                lCurrencyID = rs.getLong("nCurrencyID");
                tsImportDate = rs.getTimestamp("dtSystemDate");
                GLDealBean.addSubjectBalance(lOfficeID, lCurrencyID, Constant.ModuleType.SETTLEMENT, tsImportDate, tsImportDate);
            }
            rs.close();
            rs = null;
            ps.close();
            ps = null;
            conn.close();
            conn = null;
            System.out.println("End.");
        }
        catch (Exception e)
        {
            e.printStackTrace();
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
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
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
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}