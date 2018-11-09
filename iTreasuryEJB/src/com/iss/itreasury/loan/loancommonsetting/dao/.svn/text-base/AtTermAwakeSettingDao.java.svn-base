/*
 * Created on 2003-8-28
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.iss.itreasury.loan.loancommonsetting.dao;

import java.sql.*;
import java.util.*;
import javax.servlet.jsp.JspWriter;
import com.iss.itreasury.util.*;
import com.iss.itreasury.loan.loancommonsetting.dataentity.*;
import com.iss.itreasury.loan.util.*;

/**
 * @author yfan
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class AtTermAwakeSettingDao extends Object implements java.io.Serializable
{
    private Connection m_Conn = null;

    public AtTermAwakeSettingDao()
    {
    }

    public AtTermAwakeSettingDao(Connection con)
    {
        m_Conn = con;
    }

    /**
     * 保存到期业务提醒设置信息
     * 操作数据库表
     * @param       ASInfo  审批设置信息
     * @return      long    成功，返回值=1；失败，返回值=-1
     */
    public long saveAtTermAwakeSetting(long lTypeID[],long lAheadDays[],long lAwakeDays[],long lOfficeID,long lCurrencyID) throws Exception
    {
        boolean bSucceed = false;
        long lResult = -1;
        PreparedStatement ps = null;
		ResultSet rs = null;
        String strSQL = "";
        long lMaxID = 0;
        try
        {
          for (int i=0; i<lTypeID.length; i++)
          {
              strSQL = " select * from loan_AttermAwakeSetting where nOfficeID = ? and nAwakeTypeID = ? and  ncurrencyid="+lCurrencyID+"";
              ps = m_Conn.prepareStatement(strSQL);

              ps.setLong(1, lOfficeID);
              ps.setLong(2, lTypeID[i]);
              rs = ps.executeQuery();
              if (rs.next())
              {
                  strSQL = " update loan_AttermAwakeSetting set nAwakeDays = ?, nAheadDays = ? where nOfficeID = ? and nAwakeTypeID = ? and ncurrencyid="+lCurrencyID+"";
                  ps = m_Conn.prepareStatement(strSQL);
                  //System.out.println(strSQL);
                  ps.setLong(1, lAwakeDays[i]);
                  ps.setLong(2, lAheadDays[i]);
                  ps.setLong(3, lOfficeID);
                  ps.setLong(4, lTypeID[i]);
                  lResult = ps.executeUpdate();
                  //关闭资源
                  ps.close();
                  ps = null;
              }
              else
              {
                  strSQL = "insert into loan_AttermAwakeSetting (nOfficeID,nAheadDays,nAwakeDays,nAwakeTypeID,ncurrencyid) values (?,?,?,?,?)";
                  ps = m_Conn.prepareStatement(strSQL);
                  //System.out.println(strSQL);
                  ps.setLong(1, lOfficeID);
                  ps.setLong(2, lAheadDays[i]);
                  ps.setLong(3, lAwakeDays[i]);
                  ps.setLong(4, lTypeID[i]);
                  ps.setLong(5, lCurrencyID);
                  lResult = ps.executeUpdate();
                  //关闭资源
                  ps.close();
                  ps = null;
              }
              if (rs != null)
              {
                  rs.close();
              }
              if (ps != null)
              {
                  ps.close();
              }
          }
        }
        catch (Exception e)
        {
            Log.print("AtTermAwakeSettingDao.saveAtTermAwakeSetting() failed.  Exception is " + e.toString());
			throw e;
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
          }
          catch (Exception ex)
          {
              throw ex;
          }

        }

        return (lResult);
    }
    /**
     * 保存到期业务提醒设置信息
     * 操作数据库表
     * @param       ASInfo  审批设置信息
     * @return      long    成功，返回值=1；失败，返回值=-1
     */
    public long saveAtTermAwakeSetting(long lTypeID[],long lAheadDays[],long lAwakeDays[],long lOfficeID) throws Exception
    {
        boolean bSucceed = false;
        long lResult = -1;
        PreparedStatement ps = null;
		ResultSet rs = null;
        String strSQL = "";
        long lMaxID = 0;
        try
        {
          for (int i=0; i<lTypeID.length; i++)
          {
              strSQL = " select * from loan_AttermAwakeSetting where nOfficeID = ? and nAwakeTypeID = ?";
              ps = m_Conn.prepareStatement(strSQL);

              ps.setLong(1, lOfficeID);
              ps.setLong(2, lTypeID[i]);
              rs = ps.executeQuery();
              if (rs.next())
              {
                  strSQL = " update loan_AttermAwakeSetting set nAwakeDays = ?, nAheadDays = ? where nOfficeID = ? and nAwakeTypeID = ?";
                  ps = m_Conn.prepareStatement(strSQL);
                  //System.out.println(strSQL);
                  ps.setLong(1, lAwakeDays[i]);
                  ps.setLong(2, lAheadDays[i]);
                  ps.setLong(3, lOfficeID);
                  ps.setLong(4, lTypeID[i]);
                  lResult = ps.executeUpdate();
                  //关闭资源
                  ps.close();
                  ps = null;
              }
              else
              {
                  strSQL = "insert into loan_AttermAwakeSetting (nOfficeID,nAheadDays,nAwakeDays,nAwakeTypeID) values (?,?,?,?)";
                  ps = m_Conn.prepareStatement(strSQL);
                  //System.out.println(strSQL);
                  ps.setLong(1, lOfficeID);
                  ps.setLong(2, lAheadDays[i]);
                  ps.setLong(3, lAwakeDays[i]);
                  ps.setLong(4, lTypeID[i]);
                  lResult = ps.executeUpdate();
                  //关闭资源
                  ps.close();
                  ps = null;
              }
              if (rs != null)
              {
                  rs.close();
              }
              if (ps != null)
              {
                  ps.close();
              }
          }
        }
        catch (Exception e)
        {
            Log.print("AtTermAwakeSettingDao.saveAtTermAwakeSetting() failed.  Exception is " + e.toString());
			throw e;
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
          }
          catch (Exception ex)
          {
              throw ex;
          }

        }

        return (lResult);
    }

    /**
     * 查询到期业务提醒设置信息
     * 查询条件为办事处标示和到期业务提醒类型
     * 操作数据库表ApprovalSetting,ApprovalItem,ApprovalTracing
     * @param       lApprovalID            审批设置标示（查询条件）
     * @return      ApprovalSettingInfo    返回审批设置信息
     */
    public AtTermAwakeInfo findAtTermAwakeSetting(long lOfficeID,long lAwakeTypeID,long lCurrencyID) throws Exception
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean bSucceed = false;
        long lReturn = -1;
        AtTermAwakeInfo ATAInfo = new AtTermAwakeInfo();
        String strSQL = "";

        try
        {
			strSQL = " select * from loan_AttermAwakeSetting where nOfficeID = ? and nAwakeTypeID = ? and ncurrencyid="+lCurrencyID+" ";
			ps = m_Conn.prepareStatement(strSQL);
			ps.setLong(1,lOfficeID);
			ps.setLong(2,lAwakeTypeID);
			rs = ps.executeQuery();
			if (rs!=null && rs.next ())
			{
				ATAInfo.setAwakeTypeID(rs.getLong("nAwakeTypeID"));
				ATAInfo.setAheadDays(rs.getLong("nAheadDays"));
				ATAInfo.setAwakeDays(rs.getLong("nAwakeDays"));
			}
			rs.close ();
			rs = null;
			ps.close ();
			ps = null;
        }
        catch (Exception e)
        {
            Log.print("AtTermAwakeSettingDao.findAtTermAwakeSetting() failed.  Exception is " + e.toString());
            throw e;
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
			}
			catch (Exception ex)
			{
				throw ex;
			}

        }

        return (ATAInfo);
    }
    /**
     * 查询到期业务提醒设置信息
     * 查询条件为办事处标示和到期业务提醒类型
     * 操作数据库表ApprovalSetting,ApprovalItem,ApprovalTracing
     * @param       lApprovalID            审批设置标示（查询条件）
     * @return      ApprovalSettingInfo    返回审批设置信息
     */
    public AtTermAwakeInfo findAtTermAwakeSetting(long lOfficeID,long lAwakeTypeID) throws Exception
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean bSucceed = false;
        long lReturn = -1;
        AtTermAwakeInfo ATAInfo = new AtTermAwakeInfo();
        String strSQL = "";

        try
        {
			strSQL = " select * from loan_AttermAwakeSetting where nOfficeID = ? and nAwakeTypeID = ? ";
			ps = m_Conn.prepareStatement(strSQL);
			ps.setLong(1,lOfficeID);
			ps.setLong(2,lAwakeTypeID);
			rs = ps.executeQuery();
			if (rs!=null && rs.next ())
			{
				ATAInfo.setAwakeTypeID(rs.getLong("nAwakeTypeID"));
				ATAInfo.setAheadDays(rs.getLong("nAheadDays"));
				ATAInfo.setAwakeDays(rs.getLong("nAwakeDays"));
			}
			rs.close ();
			rs = null;
			ps.close ();
			ps = null;
        }
        catch (Exception e)
        {
            Log.print("AtTermAwakeSettingDao.findAtTermAwakeSetting() failed.  Exception is " + e.toString());
            throw e;
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
			}
			catch (Exception ex)
			{
				throw ex;
			}

        }

        return (ATAInfo);
    }

    public static void main(String[] args)
    {
        try
        {
            //
        }
        catch (Exception e)
        {
            System.out.println(e.toString());
        }
    }

}
