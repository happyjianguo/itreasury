/*
 * Copyright (c) 2003-2005 iss.com. All Rights Reserved.
 *
 * ���幦��˵��������ȡ�õ������ѵĺ�ͬ��
 *
 * ʹ��ע�����
 * 1
 * 2
 *
 * ���ߣ�haoning
 * time: 2003-12-10
 */
package com.iss.itreasury.treasuryplan.awake.bizlogic;
import java.sql.*;
import java.rmi.RemoteException;
import com.iss.itreasury.util.*;
import com.iss.itreasury.treasuryplan.util.*;
import com.iss.itreasury.treasuryplan.awake.dataentity.*;

public class Awake
{
    private Log4j log4j = new Log4j(Constant.ModuleType.PLAN, this);
    
    //public static long Count = 50;
    
    //public static String AwakeMSG = ""; 
    

    public Awake()
    {
    }
    
    /*
     * @author haoning
     * @time 2003-12-12
     * @param lCurrencyID
     * @param lOfficeID
     * @param lDepartmentID
     * function �����ʽ�ƻ�����¼��
     * 
     */
    public String getForecastData (long lCurrencyID, long lOfficeID, Connection con)
        throws RemoteException, Exception
    {
        String sResult = "";
        //Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        StringBuffer sb = new StringBuffer();
        long lContractID = -1;
        long lDepartmentID[] = new long[10];
        String sDepartmentID = "";
        String sDepartmentName = "";
        Timestamp tsTransactionDate = null;
        
        try
        {
            //�����ʽ�ƻ�����¼�뿪ʼ
            //con = Database.get_jdbc_connection ();  
            //con = Database.getConnection();
            sb.setLength(0);
            sb.append(" select nvl(a.AuthorizedDepartment,'') AuthorizedDepartment, a.TransactionDate ");
            sb.append(" from Trea_TPForecastData a ");
            sb.append(" where 1=1 ");
            sb.append(" and a.OfficeID = " + lOfficeID);
            sb.append(" and a.CurrencyID = " + lCurrencyID);
            sb.append(" and to_char(sysdate,'YYYY-MM-DD') >= to_char(a.InputTime,'YYYY-MM-DD') ");
            sb.append(" and to_char(sysdate,'YYYY-MM-DD') <= to_char(a.InputTime + 7,'YYYY-MM-DD') ");
            sb.append(" group by a.AuthorizedDepartment,a.TransactionDate ");
            
            //Log.print(sb.toString());
            ps = con.prepareStatement(sb.toString());
            rs = ps.executeQuery();
            while (rs != null && rs.next())
            {
                sDepartmentID = rs.getString("AuthorizedDepartment");
                //Log.print(sDepartmentID);
                if (sDepartmentID != null && sDepartmentID.length() > 0)
                {
	                sDepartmentID = StringReplace(sDepartmentID,"><",",");
	                //Log.print(sDepartmentID);
	                sDepartmentID = sDepartmentID.replace('<',' ').trim();
	                //Log.print(sDepartmentID);
	                sDepartmentID = sDepartmentID.replace('>',' ').trim();
	                //Log.print(sDepartmentID);
	                lDepartmentID = changeStringGroup(sDepartmentID,",");
	                sDepartmentName = "";
	                for(int i=0;i<lDepartmentID.length;i++)
	                {
	                	if (lDepartmentID[i] > 0)
	                	{
	                		if (sDepartmentName.length() > 0)
	                		{
	                			sDepartmentName += "��";
	                		}
	                		sDepartmentName += findNameByID(lDepartmentID[i], con);
	                		//Log.print(lDepartmentID[i]);
	                		//Log.print(sDepartmentName);
	                	}
	                }
	                tsTransactionDate = rs.getTimestamp("TransactionDate");
	                sResult += " ���� "
	                	+ DataFormat.formatString(sDepartmentName)
	                    + " �� " + DataFormat.formatDate(tsTransactionDate) 
	                    + " ������ʽ�ƻ������и��£�";
                }
            }
            if (sResult == null && sResult.length() <= 0)
            {
                sResult = " ���ʽ�ƻ����ݸ��£�";
            }
            //�����ʽ�ƻ�����¼�����
            
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
            //if (con != null)
            //{
            //    con.close();
            //    con = null;
            //}
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
        catch (Exception e)
        {
            e.printStackTrace();
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
                //if (con != null)
                //{
                //    con.close();
                //    con = null;
                //}
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception(e.getMessage());
            }
        }
        
        return sResult;
    }
    
    /*
     * @author haoning
     * @time 2003-12-12
     * @param lCurrencyID
     * @param lOfficeID
     * @param lDepartmentID
     * function ������ˡ����ʽ�ƻ��汾
     * 
     */
    public String getCheckPlan(long lCurrencyID, long lOfficeID, long lDepartmentID, Connection con)
        throws RemoteException, Exception
    {
        String sResult = "";
        //Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        StringBuffer sb = new StringBuffer();
        String sDepartmentName = "";
        String sCode = "";
        Timestamp tsStartDate = null;
        Timestamp tsEndDate = null;
        
        try
        {
            //������ˡ����ʽ�ƻ��汾��ʼ
            //con = Database.get_jdbc_connection ();  
            //con = Database.getConnection();            
            sb.setLength(0);
            sb.append(" select b.DepartmentName, a.Code, a.StartDate, a.EndDate ");
            sb.append(" from Trea_TreasuryPlan a, Department b ");
            sb.append(" where 1=1 ");
            sb.append(" and a.DepartmentID = b.ID ");
            sb.append(" and a.OfficeID = " + lOfficeID);
            sb.append(" and a.CurrencyID = " + lCurrencyID);
            sb.append(" and a.StatusID = " + TPConstant.PlanVersionStatus.CHECK);
            sb.append(" and b.StatusID = " + Constant.RecordStatus.VALID);
            sb.append(" and to_char(sysdate,'YYYY-MM-DD') >= to_char(a.CheckDate,'YYYY-MM-DD') ");
            sb.append(" and to_char(sysdate,'YYYY-MM-DD') <= to_char(a.CheckDate + 7,'YYYY-MM-DD') ");
            
            //Log.print(sb.toString());
            ps = con.prepareStatement(sb.toString());
            rs = ps.executeQuery();
            while (rs != null && rs.next())
            {
                sDepartmentName = rs.getString("DepartmentName");
                sCode = rs.getString("Code");
                tsStartDate = rs.getTimestamp("StartDate");
                tsEndDate = rs.getTimestamp("EndDate");
                sResult += " ���� "
                	+ DataFormat.formatString(sDepartmentName)
                    + " �� " + DataFormat.formatDate(tsStartDate) 
                    + " - " + DataFormat.formatDate(tsEndDate)
                    + " ������ʽ�ƻ��汾�� "
                    + DataFormat.formatString(sCode)
                    + " ������ˣ�";
            }
            
            if (sResult == null && sResult.length() <= 0)
            {
                sResult = " ���ʽ�ƻ��汾����ˣ�";
            }
            //������ˡ����ʽ�ƻ��汾����
            
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
            //if (con != null)
            //{
            //    con.close();
            //    con = null;
            //}
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
        catch (Exception e)
        {
            e.printStackTrace();
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
                //if (con != null)
                //{
                //    con.close();
                //    con = null;
                //}
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception(e.getMessage());
            }
        }
        
        return sResult;
    }
    
    /*
     * @author haoning
     * @time 2003-12-11
     * @param 
     * function 
     */
    public void getAllAwakeMsg()
        throws RemoteException, Exception
    {
        PreparedStatement ps = null;
        Connection conn = null;
        StringBuffer sbSQL = null;
        ResultSet rs = null;
        
        long lCurrencyID = -1;
        long lOfficeID = -1;
        long lDepartmentID = -1;

		long lTimeMillisBegin = 0;
		long lTimeMillisEnd = 0;
        
        String sResult = " ";
        try
        {
            Log.print("***���뷽�� -- getAllAwakeMsg ***");
            
            conn = Database.getConnection();

            sbSQL = new StringBuffer();
            sbSQL.append(" select distinct nOfficeID,nCurrencyID from SETT_OFFICETIME c where c.nStatusID = 1 \n");
            ps = conn.prepareStatement(sbSQL.toString());
            rs = ps.executeQuery();
            
            while (rs.next())
            {                               
                AwakeInfo awakeinfo = new AwakeInfo();
                
                lOfficeID = rs.getLong("nOfficeID");
                lCurrencyID = rs.getLong("nCurrencyID");
                awakeinfo.setOfficeID(lOfficeID);
                awakeinfo.setCurrencyID(lCurrencyID);
                
                awakeinfo.setCon(conn);
                
                lTimeMillisBegin = Env.getSystemDateTime().getTime();
                //�����ʽ�ƻ�����¼������
                sResult = getForecastData(lCurrencyID, lOfficeID, conn);
                lTimeMillisEnd = Env.getSystemDateTime().getTime();
                Log.print("���´���" + lOfficeID + "�����֣�" + lCurrencyID + "�������ʽ�ƻ�����¼������ִ��ʱ�䣺" + (lTimeMillisEnd - lTimeMillisBegin) + "ms");
                
                lTimeMillisBegin = Env.getSystemDateTime().getTime();
                //������ˡ����ʽ�ƻ��汾
                sResult += getCheckPlan(lCurrencyID, lOfficeID, lDepartmentID, conn);
                lTimeMillisEnd = Env.getSystemDateTime().getTime();
                Log.print("���´���" + lOfficeID + "�����֣�" + lCurrencyID + "��������ˡ����ʽ�ƻ��汾ִ��ʱ�䣺" + (lTimeMillisEnd - lTimeMillisBegin) + "ms");
                
                //Log.print("lOfficeID:"+lOfficeID);
                //Log.print("lCurrencyID:"+lCurrencyID);
                //Log.print("Msg:"+sResult);
                //���ҵ��������Ϣ
                //String strMsg = this.getRemindMSG(lOfficeID,lCurrencyID);
                String strMsg = sResult;
                //����ҵ��������Ϣ
                String strKey = String.valueOf(lOfficeID) + String.valueOf(lCurrencyID);
                awakeinfo.AwakeMSG.put(strKey,strMsg);
            }
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
            
            Log.print("***�˳����� -- getAllAwakeMsg ***");
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
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
            throw new Exception(e.getMessage());
        }
        catch (Exception e)
        {
            e.printStackTrace();
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
				if (conn != null)
                {
                    conn.close();
                    conn = null;
                }
            }
            catch (Exception e)
            {
                Log.print(e.toString());
                throw new Exception(e.getMessage());
            }
        }
        //return sResult;
    }
    
    /*
     * @author haoning
     * @time 2003-12-12
     * @param lCurrencyID
     * @param lOfficeID
     * @param lDepartmentID
     * function 
     * 
     */
    public String findNameByID(long lID, Connection con)
        throws RemoteException, Exception
    {
        String sName = "";
        //Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        StringBuffer sb = new StringBuffer();
        
        try
        {
            //con = Database.get_jdbc_connection ();  
            //con = Database.getConnection();            
            sb.setLength(0);
            sb.append(" select b.DepartmentName ");
            sb.append(" from Department b ");
            sb.append(" where 1=1 ");
            sb.append(" and b.StatusID = " + Constant.RecordStatus.VALID);
            sb.append(" and ID = " + lID);
            
            //Log.print(sb.toString());
            ps = con.prepareStatement(sb.toString());
            rs = ps.executeQuery();
            while (rs != null && rs.next())
            {
                sName = rs.getString("DepartmentName");
            }
            
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
            //if (con != null)
            //{
            //    con.close();
            //    con = null;
            //}
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
        catch (Exception e)
        {
            e.printStackTrace();
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
                //if (con != null)
                //{
                //    con.close();
                //    con = null;
                //}
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception(e.getMessage());
            }
        }
        
        return sName;
    }
    
	// �滻�ַ�������
	// String strSource - Դ�ַ���
	// String strFrom�� - Ҫ�滻���Ӵ�
	// String strTo���� - �滻Ϊ���ַ���
	private static String StringReplace(String strSource, String strFrom, String strTo)
	{
		// ���Ҫ�滻���Ӵ�Ϊ�գ���ֱ�ӷ���Դ��
		if(strFrom == null || strFrom.equals(""))
			return strSource;
		String strDest = "";
		// Ҫ�滻���Ӵ�����
		int intFromLen = strFrom.length();
		int intPos;
		// ѭ���滻�ַ���
		while((intPos = strSource.indexOf(strFrom)) != -1)
		{
			// ��ȡƥ���ַ���������Ӵ�
			strDest = strDest + strSource.substring(0,intPos);
			// �����滻����Ӵ�
			strDest = strDest + strTo;
			// �޸�Դ��Ϊƥ���Ӵ�����Ӵ�
			strSource = strSource.substring(intPos + intFromLen);
		}
		// ����û��ƥ����Ӵ�
		strDest = strDest + strSource;
		// ����
		return strDest;
	}
    
    /**
     * ��һ���÷ָ����ֿ��Ĵ��ֽ�Ϊһ��long������
     * @param strParam ��Ҫ��ֵĲ���
	 * @param strSeparatorChar �ָ���
     * @return ����һ��long������
     */
    private static long[] changeStringGroup(String strParam,String strSeparatorChar)
    {
        long[] lReturn = new long[10];
        int i = 0;
        int nIndex = 0;//�ָ���������λ��
        nIndex = strParam.indexOf(strSeparatorChar);
        String strData = "";//��������ִ�
        while(nIndex>0)
        {
            strData = strParam.substring(0,nIndex);
            lReturn[i] = Long.parseLong(strData.trim());
            strParam = strParam.substring(nIndex+1,strParam.length());
            nIndex = strParam.indexOf(strSeparatorChar);
            i++;
        }
        if (strParam != "")
        {
            lReturn[i] = Long.parseLong(strParam.trim());
        }
        return lReturn;
    }
    
    /*
     * @author haoning
     * @time 2003-12-11
     * @param args
     * function
     */
    public static void main(String[] args)
    {
        String sss = "";
        Awake a = new Awake();
        try
        {
            a.getAllAwakeMsg();
            //Log.print(a.AwakeMSG);
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
