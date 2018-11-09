/*
 * Copyright (c) 2003-2005 iss.com. All Rights Reserved.
 *
 * 总体功能说明：用于取得到期提醒的合同号
 *
 * 使用注意事项：
 * 1
 * 2
 *
 * 作者：haoning
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
     * function 部门资金计划数据录入
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
            //部门资金计划数据录入开始
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
	                			sDepartmentName += "、";
	                		}
	                		sDepartmentName += findNameByID(lDepartmentID[i], con);
	                		//Log.print(lDepartmentID[i]);
	                		//Log.print(sDepartmentName);
	                	}
	                }
	                tsTransactionDate = rs.getTimestamp("TransactionDate");
	                sResult += " 部门 "
	                	+ DataFormat.formatString(sDepartmentName)
	                    + " 在 " + DataFormat.formatDate(tsTransactionDate) 
	                    + " 区间的资金计划数据有更新；";
                }
            }
            if (sResult == null && sResult.length() <= 0)
            {
                sResult = " 无资金计划数据更新；";
            }
            //部门资金计划数据录入结束
            
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
     * function “已审核”的资金计划版本
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
            //“已审核”的资金计划版本开始
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
                sResult += " 部门 "
                	+ DataFormat.formatString(sDepartmentName)
                    + " 在 " + DataFormat.formatDate(tsStartDate) 
                    + " - " + DataFormat.formatDate(tsEndDate)
                    + " 区间的资金计划版本“ "
                    + DataFormat.formatString(sCode)
                    + " ”已审核；";
            }
            
            if (sResult == null && sResult.length() <= 0)
            {
                sResult = " 无资金计划版本已审核；";
            }
            //“已审核”的资金计划版本结束
            
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
            Log.print("***进入方法 -- getAllAwakeMsg ***");
            
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
                //部门资金计划数据录入提醒
                sResult = getForecastData(lCurrencyID, lOfficeID, conn);
                lTimeMillisEnd = Env.getSystemDateTime().getTime();
                Log.print("办事处：" + lOfficeID + "；币种：" + lCurrencyID + "；部门资金计划数据录入提醒执行时间：" + (lTimeMillisEnd - lTimeMillisBegin) + "ms");
                
                lTimeMillisBegin = Env.getSystemDateTime().getTime();
                //“已审核”的资金计划版本
                sResult += getCheckPlan(lCurrencyID, lOfficeID, lDepartmentID, conn);
                lTimeMillisEnd = Env.getSystemDateTime().getTime();
                Log.print("办事处：" + lOfficeID + "；币种：" + lCurrencyID + "；“已审核”的资金计划版本执行时间：" + (lTimeMillisEnd - lTimeMillisBegin) + "ms");
                
                //Log.print("lOfficeID:"+lOfficeID);
                //Log.print("lCurrencyID:"+lCurrencyID);
                //Log.print("Msg:"+sResult);
                //获得业务提醒信息
                //String strMsg = this.getRemindMSG(lOfficeID,lCurrencyID);
                String strMsg = sResult;
                //保存业务提醒信息
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
            
            Log.print("***退出方法 -- getAllAwakeMsg ***");
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
    
	// 替换字符串函数
	// String strSource - 源字符串
	// String strFrom　 - 要替换的子串
	// String strTo　　 - 替换为的字符串
	private static String StringReplace(String strSource, String strFrom, String strTo)
	{
		// 如果要替换的子串为空，则直接返回源串
		if(strFrom == null || strFrom.equals(""))
			return strSource;
		String strDest = "";
		// 要替换的子串长度
		int intFromLen = strFrom.length();
		int intPos;
		// 循环替换字符串
		while((intPos = strSource.indexOf(strFrom)) != -1)
		{
			// 获取匹配字符串的左边子串
			strDest = strDest + strSource.substring(0,intPos);
			// 加上替换后的子串
			strDest = strDest + strTo;
			// 修改源串为匹配子串后的子串
			strSource = strSource.substring(intPos + intFromLen);
		}
		// 加上没有匹配的子串
		strDest = strDest + strSource;
		// 返回
		return strDest;
	}
    
    /**
     * 将一个用分隔符分开的串分解为一个long的数组
     * @param strParam 需要拆分的参数
	 * @param strSeparatorChar 分隔符
     * @return 返回一个long型数组
     */
    private static long[] changeStringGroup(String strParam,String strSeparatorChar)
    {
        long[] lReturn = new long[10];
        int i = 0;
        int nIndex = 0;//分隔符的索引位置
        nIndex = strParam.indexOf(strSeparatorChar);
        String strData = "";//拆出的数字串
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
