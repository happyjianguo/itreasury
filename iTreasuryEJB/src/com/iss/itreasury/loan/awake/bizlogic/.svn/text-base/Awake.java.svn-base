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
package com.iss.itreasury.loan.awake.bizlogic;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.iss.itreasury.loan.awake.dataentity.AwakeInfo;
import com.iss.itreasury.loan.loaninterestsetting.dataentity.AdjustRateInfo;
import com.iss.itreasury.loan.loaninterestsetting.dataentity.InterestRateInfo;
import com.iss.itreasury.loan.loanpaynotice.dao.LoanPayNoticeDao;
import com.iss.itreasury.loan.mywork.dao.LoanMyWorkDao;
import com.iss.itreasury.loan.mywork.dao.LoanTransActionDao;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.ConfigConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.FSWorkflowManager;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.util.Log4j;
import com.iss.itreasury.settlement.util.NameRef;
import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;

public class Awake
{
    private Log4j log4j = new Log4j(Constant.ModuleType.LOAN, this);
    private long[] lAwakeDays = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
    private long[] lAheadAwakeDays = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
    
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
     * function 取业务提醒设置
     */
    public void getAwakeSetting(long lCurrencyID, long lOfficeID, Connection con)
        throws RemoteException, Exception
    {
        //Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        StringBuffer sb = new StringBuffer();
        String strSQL = "";
        /************业务提醒设置参数*************/
        long lAwakeDay = -1; //提醒天数
        long lAheadAwakeDay = -1; //提前提醒天数
        long lAwakeType = -1; //提醒类型（方式）
        long[] lAwakeDays1 = new long[40];//{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        long[] lAheadAwakeDays1 = new long[40];//{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        try
        {
            //con = Database.get_jdbc_connection();
            //----------从业务提醒设置中取出设置---------//          
            sb.setLength(0);
            sb.append(" select * from LOAN_ATTERMAWAKESETTING ");
            sb.append(" where 1 = 1 ");
            sb.append(" AND NOFFICEID = " + lOfficeID);
            sb.append (" and NCURRENCYID = "+lCurrencyID);
            sb.append(" order by NAWAKETYPEID  ");
            //Log.print("业务提醒设置:"+sb.toString());
            ps = con.prepareStatement(sb.toString());
            rs = ps.executeQuery();
            while (rs.next() && rs != null)
            {
                lAwakeDay = rs.getLong("NAWAKEDAYS"); //提醒天数
                lAheadAwakeDay = rs.getLong("NAHEADDAYS"); //提前提醒天数
                lAwakeType = rs.getLong("NAWAKETYPEID"); //提醒类型
                lAwakeDays1[(int) lAwakeType] = lAwakeDay; //提醒天数
                lAheadAwakeDays1[(int) lAwakeType] = lAheadAwakeDay; //提前提醒天数
                //Log.print("类型:"+lAwakeType
                // +",提前:"+lAheadAwakeDay
                // +"天,提醒:"+lAwakeDay+"天");
            }
            this.setAheadAwakeDays(lAheadAwakeDays1);
            this.setAwakeDays(lAwakeDays1);
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
            //throw new IException("Gen_E001");
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
                //throw new IException("Gen_E001");
            }
        }
    }
    /*
     * @author haoning
     * @time 2003-12-12
     * @param lDay
     * @param tsEndDate
     * @param sContractCode
     * function 业务提醒 到期合同提醒 转化为字符串
     */
    private String getAwakeContractCode(
        long lAheadAwakeDay,
        long lAwakeDay,
        Timestamp tsEndDate,
        String sContractCode)
        throws RemoteException, Exception
    {
        //Log.print("ok");
        String sAwakeContractCode = "";
        Timestamp tsBeg = Env.getSystemDateTime();
        Timestamp tsEnd = Env.getSystemDateTime();
        try
        {
            
            String sTimetempBeg = DataFormat.addDate(DataFormat.getDateString(DataFormat.getDateTime(tsBeg.toString())),(int) lAheadAwakeDay,"d");
            tsBeg = DataFormat.getDateTime(sTimetempBeg);
            String sTimetempEnd = DataFormat.addDate(DataFormat.getDateString(DataFormat.getDateTime(tsEnd.toString())),(int) (lAheadAwakeDay - lAwakeDay),"d");
            tsEnd = DataFormat.getDateTime(sTimetempEnd);
            //Log.print("----0----");
            /*
            if ((tsBeg.compareTo(tsEndDate) >= 0) && (tsEnd.compareTo(tsEndDate) <= 0))
            {
                sAwakeContractCode = sContractCode;
                Log.print("----1----");
            }
            else
            {
                sAwakeContractCode = "";
                Log.print("----2----");
            }//*/
            if ((DataFormat.getDateString(tsBeg).compareTo(DataFormat.getDateString(tsEndDate)) >= 0) 
            && (DataFormat.getDateString(tsEnd).compareTo(DataFormat.getDateString(tsEndDate)) <= 0))
            {
                sAwakeContractCode = sContractCode;
                //Log.print("----1----");
            }
            else
            {
                sAwakeContractCode = "";
                //Log.print("----2----");
            }//*/
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
        
        return sAwakeContractCode;
    }
    /*
     * @author haoning
     * @time 2003-12-12
     * @param lCurrencyID
     * @param lOfficeID
     * function 到期合同
     * 
     */
    public String getAwakeContract(long lCurrencyID, long lOfficeID, Connection con)
        throws RemoteException, Exception
    {
        //Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        StringBuffer sb = new StringBuffer();
        String sResult = "";
        long lAwakeDay = -1;
        long lAheadAwakeDay = -1;
        /*************合同参数**************/
        long lContractID = -1; //合同ID
        String sContractCode = ""; //合同编号
        long lLoanTypeID = -1; //贷款类型
        long lisCircle = Constant.YesOrNo.NO; //循环贷款
        long lisBuy = Constant.YesOrNo.NO; //卖方贷款
        Timestamp tsEndDate = null; // 终止日期
        long lBorrowClientID = -1;//借款单位
        try
        {
            //con = Database.getConnection();
            //con = Database.get_jdbc_connection ();      
            //con = Database.get_type4_connection();    
            //-----------------合同执行计划到期------------// 
            long[] lLoanType = LOANConstant.LoanType.getAllCode(lOfficeID,lCurrencyID);
            String strLoanType = "";
            for (int i = 0; i < lLoanType.length; i++)
            {
                if (i == 0)
                {
                    strLoanType = "" + lLoanType[i];
                }
                //else if (lLoanType[i] == LOANConstant.LoanType.TX)
                //{
                //    strLoanType += "";
                //}
                //else if (lLoanType[i] == LOANConstant.LoanType.ZTX)
                //{
                //    strLoanType += "";
                //}
                else
                {
                    strLoanType += "," + lLoanType[i];
                }
            }
            
            if(strLoanType == "")
            {
				strLoanType = "0";
            }
            
            sb.setLength(0);
            sb.append("SELECT DISTINCT ID, NISSALEBUY, NISCIRCLE ");
            sb.append(" ,SCONTRACTCODE , DTENDDATE, NTYPEID,NBORROWCLIENTID  ");
            sb.append(" FROM LOAN_CONTRACTFORM  ");
            sb.append(" where NCURRENCYID = " + lCurrencyID);
            sb.append(" and NOFFICEID = " + lOfficeID);
            sb.append(
                " and ( nStatusID =" + LOANConstant.ContractStatus.ACTIVE);
            sb.append(
                "    or nStatusID =" + LOANConstant.ContractStatus.EXTEND);
            sb.append("     ) ");
            sb.append(" and NTYPEID IN (" + strLoanType + ")");
            //Log.print("合同执行计划 : " + sb.toString());
            ps = con.prepareStatement(sb.toString());
            //ps.setLong (1,lCurrencyID);
            //ps.setLong (2,lOfficeID);
            //ps.setLong (3,LOANConstant.ContractStatus.ACTIVE);
            //ps.setLong (4,LOANConstant.ContractStatus.EXTEND);
            rs = ps.executeQuery();
            while (rs.next() && rs != null)
            {
                lLoanTypeID = rs.getLong("NTYPEID");
                tsEndDate = rs.getTimestamp("DTENDDATE");
                sContractCode = rs.getString("SCONTRACTCODE");
                lisCircle = rs.getLong("NISCIRCLE");
                lisBuy = rs.getLong("NISSALEBUY");
                lContractID = rs.getLong("ID");
                lBorrowClientID = rs.getLong("NBORROWCLIENTID");

                lAwakeDay = lAwakeDays[(int) lLoanTypeID];
                lAheadAwakeDay = lAheadAwakeDays[(int) lLoanTypeID];
    
                // 合同到期收集
                if (lAheadAwakeDay > 0 && tsEndDate != null && sContractCode != null)
                {
                    if (lLoanTypeID == LOANConstant.LoanType.WT
                     || lLoanTypeID == LOANConstant.LoanType.YT
                     || lLoanTypeID == LOANConstant.LoanType.ZGXE
                     || lLoanTypeID == LOANConstant.LoanType.MFXD
                    ) //专门对合同计划
                    {
                        sResult += getAwakeContractPlan(
                                lContractID,
                                lAheadAwakeDay,
                                lAwakeDay,
                                sContractCode,
                                con);
                    }
                    //自营贷款到期改为根据放款单结束日期判断
                    else if(lLoanTypeID == LOANConstant.LoanType.ZY)
                    {
                    	String remindType = Config.getProperty(ConfigConstant.LOAN_PAYOFF_TYPE, LOANConstant.RemindType.NOTICE);
                    	if(remindType.equals(LOANConstant.RemindType.NOTICE))
                    	{
	                    	sResult += getAwakeZYContract(
	                                lContractID,
	                                lAheadAwakeDay,
	                                lAwakeDay,
	                                sContractCode,
	                                lCurrencyID,
	                                lOfficeID,
	                                con);
                    	}else if(remindType.equals(LOANConstant.RemindType.PLAN))
                    	{
                            sResult += getAwakeContractPlan(
                                    lContractID,
                                    lAheadAwakeDay,
                                    lAwakeDay,
                                    sContractCode,
                                    con);                    		
                    	}
                    }
                    else //对非合同计划的提醒的处理-业务提醒
                    {
                        if ((getAwakeContractCode(lAheadAwakeDay,lAwakeDay,tsEndDate,sContractCode)).length() > 0)
                        {
                            sResult += this.getSimpleClientNameByID(lBorrowClientID) + sContractCode + " 即将到期； ";
                        }
                    } //else
                } //if
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
            /****************  合同到期提醒结束  **************/
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
            throw new Exception(e.getMessage());
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
                //throw new IException("Gen_E001");
            }
        }
        return sResult;
    }
    /*
     * @author haoning
     * @time 2003-12-12
     * @param lContractID
     * function 到期合同到期计划
     * 
     */
    public String getAwakeContractPlan(
        long lContractID,
        long lAwakeDay,
        long lAheadAwakeDay,
        String sContractCode,
        Connection con)
        throws RemoteException, Exception
    {
        String sResult = "";
        //Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        StringBuffer sb = new StringBuffer();
        Timestamp tsEndDate = null; // 终止日期
        try
        {
            //con = Database.getConnection();
            //con = Database.get_jdbc_connection ();  
            //con = Database.get_type4_connection();   
            // loan_loancontractplan.nstatusid =1标表示是有效记录
            //如下SQL语句是用于求得某个合同的所有计划版本ID！
            sb.setLength(0);
            sb.append(" select a.dtPlanDate,c.nBorrowClientID");
            sb.append(" from loan_loancontractplandetail a ");
            sb.append(" ,loan_loancontractplan b ");
            sb.append(" ,loan_contractform c ");
            sb.append(" where b.nStatusID = " + Constant.RecordStatus.VALID);
            sb.append(" and b.nPlanVersion in ");
            sb.append(" ( select MAX(nPlanVersion) ");
            sb.append("   from loan_loancontractplan ");
            sb.append("   where  nContractID = " + lContractID);
            sb.append(" ) ");
            sb.append(" and b.nContractID = " + lContractID);
            sb.append(" and b.ID = a.nContractPlanID ");
            sb.append(" and a.nPayTypeID = " + LOANConstant.PlanType.REPAY);
            sb.append(" and c.ID = " + lContractID);
            //Log.print(" 合同计划: "+sb.toString ());
            
            long lCountNumTmp = 0; 
            String sEndDateTmp = ""; //记录到期日期
            long lBorrowClientID = -1;
            ps = con.prepareStatement(sb.toString());
            rs = ps.executeQuery();
            while (rs.next() && rs != null)
            {
                tsEndDate = rs.getTimestamp(1);
                lBorrowClientID = rs.getLong(2);
                //将每笔计划的到期日和现在进行比较，看是否有到期的
                if (tsEndDate != null)
                {
                    if ((getAwakeContractCode(lAheadAwakeDay,lAwakeDay,tsEndDate,sContractCode)).length() > 0)
                    {
                        //Log.print("1");
                        lCountNumTmp++;
                        if (lCountNumTmp == 1)
                        {
                            sEndDateTmp = DataFormat.getChineseDateString(tsEndDate);
                        }
                        else
                        {
                            //Log.print("2");
                            sEndDateTmp += " 以及 " + DataFormat.getChineseDateString(tsEndDate);
                        }
                    } //
                }
            } //while 
            if (lCountNumTmp > 0)
            {
                sResult += getSimpleClientNameByID(lBorrowClientID) + "合同 " + sContractCode + " 下将有 " + lCountNumTmp + " 笔执行计划到期；";
                    //+ " 到期日分别是：" + sEndDateTmp + " ; "
                    //+ " ";
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
        catch (RemoteException e)
        {
            e.printStackTrace();
            throw new Exception(e.getMessage());
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
     * @author jdcheng
     * @time 2008-12-9
     * @param lContractID
     * function 自营贷款到期放款单
     * 
     */
    public String getAwakeZYContract(
        long lContractID,
        long lAwakeDay,
        long lAheadAwakeDay,
        String sContractCode,
        long lCurrencyID,
        long lOfficeID,
        Connection con)
        throws RemoteException, Exception
    {
        String sResult = "";
        //Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        StringBuffer sb = new StringBuffer();
        Timestamp tsEndDate = null; // 终止日期
        try
        {
            sb.setLength(0);
            sb.append(" select a.dtend,b.sContractCode,b.nBorrowClientID");
            sb.append(" from loan_payform a");
            sb.append("     ,loan_Contractform b ");
            sb.append("     , sett_subaccount  s ");
            sb.append(" where a.nContractID = b.ID ");
            sb.append(" and b.ID = " + lContractID);
            sb.append(" and a.nSourceTypeID= ");
            sb.append(LOANConstant.LoanPayNoticeModifySourceType.XD);
            sb.append(" and a.nStatusID =  ");
            sb.append(LOANConstant.LoanPayNoticeStatus.USED);
            sb.append(" and b.nCurrencyID = " + lCurrencyID);
            sb.append(" and b.nOfficeID = " + lOfficeID);
            sb.append(" and s.al_nloannoteid =a.id ");
            sb.append(" and s.mbalance >=0.01 " );
            sb.append(" and s.nstatusid <> "+ SETTConstant.TransactionStatus.DELETED );
            sb.append(" and a.nStatusID > " + Constant.RecordStatus.INVALID);
//            sb.append(" and to_char(a.dtEnd,'yyyy-mm-dd') =  ");
//            sb.append("      to_char(sysdate,'yyyy-mm-dd')");
            long lCountNumTmp = 0; 
            String sEndDateTmp = ""; //记录到期日期
            long lBorrowClientID = -1;
            ps = con.prepareStatement(sb.toString());
            rs = ps.executeQuery();
            while (rs.next() && rs != null)
            {
                tsEndDate = rs.getTimestamp(1);
                lBorrowClientID = rs.getLong(3);
                //将每笔放款单的到期日和现在进行比较，看是否有到期的
                if (tsEndDate != null)
                {
                    if ((getAwakeContractCode(lAheadAwakeDay,lAwakeDay,tsEndDate,sContractCode)).length() > 0)
                    {
                        //Log.print("1");
                        lCountNumTmp++;
                        if (lCountNumTmp == 1)
                        {
                            sEndDateTmp = DataFormat.getChineseDateString(tsEndDate);
                        }
                        else
                        {
                            //Log.print("2");
                            sEndDateTmp += " 以及 " + DataFormat.getChineseDateString(tsEndDate);
                        }
                    } //
                }
            } //while 
            if (lCountNumTmp > 0)
            {
                sResult += this.getSimpleClientNameByID(lBorrowClientID) + "合同 " + sContractCode + " 下将有 " + lCountNumTmp + " 笔放款单到期；";
                    //+ " 到期日分别是：" + sEndDateTmp + " ; "
                    //+ " ";
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
        catch (RemoteException e)
        {
            e.printStackTrace();
            throw new Exception(e.getMessage());
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
     * function 利率调整
     * 
     */
    public String getAwakeRateAdjust(long lCurrencyID, long lOfficeID, Connection con)
        throws RemoteException, Exception
    {
        //Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        StringBuffer sb = new StringBuffer();
        /***********利率调整设置参数************/
        String sResult = "";
        String sLoanPay = "";
        long lCount = -1;
        long lContractID = -1; //合同ID
        String sContractCode = ""; //合同编号
        Timestamp tsEndDate = null; // 终止日期
        Timestamp tsOutDate = null; // 放款日期
        Timestamp tsTempDate = null; // 日期
        long lAwakeDay = -1;
        long lAheadAwakeDay = -1;
        lAheadAwakeDay = lAheadAwakeDays[(int) LOANConstant.AtTermAwakeType.DKLVTZ];
        lAwakeDay = lAwakeDays[(int) LOANConstant.AtTermAwakeType.DKLVTZ];
        try
        {
            //con = Database.getConnection();
            long[] lLoanType = LOANConstant.LoanType.getAllCode(lOfficeID,lCurrencyID);
            String strLoanType = "";
            for (int i = 0; i < lLoanType.length; i++)
            {
                if (i == 0)
                {
                    strLoanType = "" + lLoanType[i];
                }
                else if (lLoanType[i] == LOANConstant.LoanType.TX)
                {
                    strLoanType += "";
                }
				else if (lLoanType[i] == LOANConstant.LoanType.ZTX)
				{
					strLoanType += "";
				}
                else
                {
                    strLoanType += "," + lLoanType[i];
                }
            }
            
			if(strLoanType == "")
			{
				strLoanType = "0";
			}
			
            sb.setLength(0);
            sb.append(" select a.dtOutDate ,a.sCode ");
            sb.append("  ,a.nContractID ,b.sContractCode,b.dtEndDate ");
            sb.append(" from loan_payform a,loan_contractform b  ");
            sb.append("   ");
            sb.append(" where a.nContractID = b.ID ");
            sb.append("   and b.ncurrencyid = " + lCurrencyID);
            sb.append("   and b.nofficeid = " + lOfficeID);
            sb.append(
                "   and ( b.nStatusID =" + LOANConstant.ContractStatus.ACTIVE);
            sb.append(
                "      or b.nStatusID =" + LOANConstant.ContractStatus.EXTEND);
            sb.append("     ) ");
            sb.append("   and b.nTypeID in (" + strLoanType + ")");
            sb.append(" order by a.nContractID  ");
            //Log.print("利率调整: " + sb.toString());
            ps = con.prepareStatement(sb.toString());
            rs = ps.executeQuery();
            while (rs != null && rs.next())
            {
                if (lContractID != rs.getLong("nContractID"))
                {
                    if (lCount > 0)
                    {
                        sResult += " 合同 " + sContractCode  + " 下有 "  + lCount  + " 笔放款可以作调整利率，" + " 放款编号分别为 " + sLoanPay + " ; " + " ";
                    }
                    lContractID = rs.getLong("nContractID");
                    sContractCode = rs.getString("sContractCode");
                    tsEndDate = rs.getTimestamp("dtEndDate");
                    sLoanPay = "";
                    lCount = 0;
                }
                tsOutDate = rs.getTimestamp("dtOutDate");
                tsTempDate = tsOutDate;
               if(tsTempDate!=null && tsEndDate!=null)
               {
                 while (DataFormat.getDateString(tsTempDate).compareTo(DataFormat.getDateString(tsEndDate)) <= 0)
                 {
                    tsTempDate = DataFormat.getNextYear(tsTempDate, 1);
                    if (lAheadAwakeDay > 0
                        && tsTempDate != null
                        && DataFormat.getDateString(tsTempDate).compareTo(DataFormat.getDateString(tsEndDate)) <= 0
                        && sContractCode != null)
                    {
                        if ((getAwakeContractCode(lAheadAwakeDay,lAwakeDay,tsTempDate,sContractCode)).length() > 0)
                        {
                            lCount++;
                            sLoanPay += rs.getString("sCode") + " ";
                        }
                    }
                 }
               }
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
        catch (RemoteException e)
        {
            e.printStackTrace();
            throw new Exception(e.getMessage());
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
     * function 贴现
     * 
     */
    public String getAwakeDiscount(long lCurrencyID, long lOfficeID, Connection con)
        throws RemoteException, Exception
    {
        String sResult = "";
        //Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        StringBuffer sb = new StringBuffer();
        PreparedStatement psTmp = null;
        ResultSet rsTmp = null;
        StringBuffer sbTmp = new StringBuffer();
        /*************合同参数**************/
        long lContractID = -1; //合同ID
        String sContractCode = ""; //合同编号
        Timestamp tsEndDate = null; // 终止日期
        long lTempDay = 0;
        //update by dwj 20111024
        //long lNLIBORRATEDAYS = 27;
        getAwakeSetting(lCurrencyID, lOfficeID, con);
        long lNLIBORRATEDAYS = lAheadAwakeDays[3] > 0 ? lAheadAwakeDays[3] : 10;
        long lAwakeDay = lAwakeDays[3];
        //end update by dwj 20111024
        long lBorrowClientID = -1;//借款单位
        //Common.log("贴现到期处理开始");
        try
        {
            if (lCurrencyID==1)
            {
                String strSQL2 = "";
                //con = Database.getConnection();
                //con = Database.get_type4_connection();  
                lTempDay = lNLIBORRATEDAYS;
                sb.setLength(0);
                sb.append(" SELECT * FROM  loan_contractForm ");
                sb.append(" WHERE nOfficeID = " + lOfficeID);
                sb.append("   and nCurrencyID = " + lCurrencyID);
                sb.append("   and nTypeID = " + LOANConstant.LoanType.TX);
               // sb.append("   and nStatusID > " + Constant.RecordStatus.INVALID);
                sb.append("  and nStatusID = " + LOANConstant.ContractStatus.ACTIVE);
                //Log.print("贴现到期 : "+sb.toString ());
                ps = con.prepareStatement(sb.toString());
                //************************************//
                rs = ps.executeQuery();
                while (rs != null && rs.next())
                {
                    sContractCode = rs.getString("sContractCode");
                    lContractID = rs.getLong("ID");
                    tsEndDate = rs.getTimestamp("dtEndDate");
                    lBorrowClientID = rs.getLong("nBorrowClientID");
                    // 贴现到期收集
                    if (lTempDay > 0
                        && tsEndDate != null
                        && sContractCode != null)
                    {
                        sbTmp.setLength(0);
                        sbTmp.append(" SELECT a.dtEnd ");
                        sbTmp.append(" FROM loan_discountcontractbill a ");
                        sbTmp.append(" WHERE a.nContractID = " + lContractID);
                        sbTmp.append(
                            " AND a.nStatusID > "
                                + Constant.RecordStatus.INVALID);
                        //Log.print(" discountbill: "+sbTmp.toString ());
                        psTmp = con.prepareStatement(sbTmp.toString());
                        //************************************
                        rsTmp = psTmp.executeQuery();
                        long lCountNumTmp2 = 0;
                        String sEndDateTmp2 = ""; //记录到期日期
                        while (rsTmp != null && rsTmp.next())
                        {
                            tsEndDate = rsTmp.getTimestamp("dtEnd");
                            //将每笔贴现的到期日和现在进行比较，看是否有到期的
                            if (tsEndDate != null)
                            {
                                //if ((getAwakeContractCode(lTempDay,lTempDay,tsEndDate,sContractCode)).length() > 0)
                            	if ((getAwakeContractCode(lAwakeDay,lTempDay,tsEndDate,sContractCode)).length() > 0)
                                {
                                    lCountNumTmp2++;
                                    if (lCountNumTmp2 == 1)
                                    {
                                        sEndDateTmp2 =
                                            DataFormat.getChineseDateString(
                                                tsEndDate);
                                    }
                                    else
                                    {
                                        sEndDateTmp2 += " 以及 "
                                            + DataFormat.getChineseDateString(
                                                tsEndDate);
                                    }
                                } //if
                            }
                            //sResult += sEndDateTmp2;                        
                        } //while 查出所有的贴现明细
                        if (lCountNumTmp2 > 0)
                        {
                            sResult += this.getSimpleClientNameByID(lBorrowClientID) + " 贴现合同 "
                                + sContractCode
                                + " 下将有 "
                                + lCountNumTmp2
                                + " 笔贴现票据到期；"
                                //+ " 到期日分别是："
                                //+ sEndDateTmp2
                                //+ " ;"
                                + " ";
                        }
                        if (rsTmp != null)
                        {
                            rsTmp.close();
                            rsTmp = null;
                        }
                        if (psTmp != null)
                        {
                            psTmp.close();
                            psTmp = null;
                        }
                    } //if 
                } //while                
                if (rsTmp != null)
                {
                    rsTmp.close();
                    rsTmp = null;
                }
                if (psTmp != null)
                {
                    psTmp.close();
                    psTmp = null;
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
                //   con.close();
                //    con = null;
                //}
            }
            //贴现到期处理结束
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
            throw new Exception(e.getMessage());
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
                if (rsTmp != null)
                {
                    rsTmp.close();
                    rsTmp = null;
                }
                if (psTmp != null)
                {
                    psTmp.close();
                    psTmp = null;
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
     * function 结息
     * 
     */
    public String getAwakeSettleInterest(long lCurrencyID, long lOfficeID, Connection con)
        throws RemoteException, Exception
    {
        String sResult = "";
        //Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        StringBuffer sb = new StringBuffer();
        PreparedStatement psTmp = null;
        ResultSet rsTmp = null;
        StringBuffer sbTmp = new StringBuffer();
        long lContractID = -1;
        String sContractCode = "";
        /***********结息 设置参数************/
        long lAheadAwakeDay = 7;
        //lAheadAwakeDays[(int)LOANConstant.AtTermAwakeType.DKLVTZ];
        long lAwakeDay = 7;
        //lAwakeDays[(int)LOANConstant.AtTermAwakeType.DKLVTZ];
        long lInterestTypeID = -1;
        //long lLiborRateID = -1;
        //long lInterestRateDay = -1;
        //long lLiborRateDay = -1;
        Timestamp tsEndDate = null; // 终止日期
        Timestamp InterestRateEndDate[] = new Timestamp[4];
        Timestamp tsToday = Env.getSystemDateTime();
        InterestRateEndDate[0] =
            Timestamp.valueOf(
                tsToday.toString().substring(0, 4) + "-03-21 00:00:00");
        InterestRateEndDate[1] =
            Timestamp.valueOf(
                tsToday.toString().substring(0, 4) + "-06-21 00:00:00");
        InterestRateEndDate[2] =
            Timestamp.valueOf(
                tsToday.toString().substring(0, 4) + "-09-21 00:00:00");
        InterestRateEndDate[3] =
            Timestamp.valueOf(
                tsToday.toString().substring(0, 4) + "-12-21 00:00:00");
        try
        {
            //con = Database.getConnection();
            //con = Database.get_jdbc_connection();
            //con = Database.get_type4_connection();  
            //***************************计息提醒开始****************************//
            long[] lLoanType = LOANConstant.LoanType.getAllCode(lOfficeID,lCurrencyID);
            String strLoanType = "";
            for (int i = 0; i < lLoanType.length; i++)
            {
                if (i == 0)
                {
                    strLoanType = "" + lLoanType[i];
                }
                else if (lLoanType[i] == LOANConstant.LoanType.TX)
                {
                    strLoanType += "";
                }
				else if (lLoanType[i] == LOANConstant.LoanType.ZTX)
				{
					strLoanType += "";
				}
                else
                {
                    strLoanType += "," + lLoanType[i];
                }
            }
            
			if(strLoanType == "")
			{
				strLoanType = "0";
			}
			
            sb.setLength(0);
            sb.append(" select id, sContractCode, nInterestTypeID");
            //,nLiborRateID");
            sb.append(" from loan_contractform ");
            sb.append("  where nCurrencyID = " + lCurrencyID);
            sb.append("   and nOfficeID = " + lOfficeID);
            sb.append(
                "    and ( nStatusID = " + LOANConstant.ContractStatus.ACTIVE);
            sb.append(
                "   or nStatusID = " + LOANConstant.ContractStatus.EXTEND);
            sb.append("   ) ");
            sb.append("    and NTYPEID IN (" + strLoanType + ")");
            //Log.print("计息: " + sb.toString());
            ps = con.prepareStatement(sb.toString());
            //ps.setLong (1, lCurrencyID);
            //ps.setLong (2, lOfficeID);
            //ps.setLong (3,LOANConstant.ContractStatus.ACTIVE);
            //ps.setLong (4,LOANConstant.ContractStatus.EXTEND);
            rs = ps.executeQuery();
            if (rs.next() && rs != null)
            {
                lContractID = rs.getLong("ID");
                sContractCode = rs.getString("sContractCode");
                lInterestTypeID = rs.getLong("nInterestTypeID");
                //lLiborRateID = rs.getLong ("nLiborRateID");
                // 计息到期收集
                // 银行利率
                if (lInterestTypeID == LOANConstant.InterestRateType.BANK
                    && sContractCode != null)
                {
                    if ((getAwakeContractCode(lAheadAwakeDay,lAwakeDay,InterestRateEndDate[0],sContractCode)).length() > 0)
                    {
                        sResult += " 合同 " + sContractCode + " 将要结息；";
                    }
                    else if (
                        (getAwakeContractCode(lAheadAwakeDay,lAwakeDay,InterestRateEndDate[1],sContractCode)).length() > 0)
                    {
                        sResult += " 合同 " + sContractCode + " 将要结息；";
                    }
                    else if (
                        (getAwakeContractCode(lAheadAwakeDay,lAwakeDay,InterestRateEndDate[2],sContractCode)).length() > 0)
                    {
                        sResult += " 合同 " + sContractCode + " 将要结息；";
                    }
                    else if (
                        (getAwakeContractCode(lAheadAwakeDay,lAwakeDay,InterestRateEndDate[3],sContractCode)).length() > 0)
                    {
                        sResult += " 合同 " + sContractCode + " 将要结息；";
                    }
                }
                //Libor利率
                if (lInterestTypeID == LOANConstant.InterestRateType.LIBOR
                    && sContractCode != null)
                {
                    String sEndDateTmp = ""; //记录到期日期
                    sbTmp.setLength(0);
                    sbTmp.append(" select dtInterestEnd from LiborInform ");
                    sbTmp.append(" where nContractID = " + lContractID);
                    psTmp = con.prepareStatement(sbTmp.toString());
                    rsTmp = psTmp.executeQuery();
                    while (rsTmp != null && rsTmp.next())
                    {
                        tsEndDate = rsTmp.getTimestamp(1);
                        //将每笔计划的到期日和现在进行比较，看是否有到期的
                        if (tsEndDate != null)
                        {
                            /*
                                                        if( (getAwakeContractCode (lLiborRateDay,
                                                            lLiborRateDay,
                                                            tsEndDate,sContractCode)).length () > 0 )
                                                        {
                                                            sResult += " 合同 " + sContractCode + " 将要结息；";
                                                        }//*/
                            if ((getAwakeContractCode(lAheadAwakeDay,lAwakeDay,tsEndDate,sContractCode)).length() > 0)
                            {
                                sResult += " 合同 " + sContractCode + " 将要结息；";
                            }
                        }
                    }
                    if (rsTmp != null)
                    {
                        rsTmp.close();
                        rsTmp = null;
                    }
                    if (psTmp != null)
                    {
                        psTmp.close();
                        psTmp = null;
                    }
                }
            }             
            if (rsTmp != null)
            {
                rsTmp.close();
                rsTmp = null;
            }
            if (psTmp != null)
            {
                psTmp.close();
                psTmp = null;
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
            //计息提醒结束
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
            throw new Exception(e.getMessage());
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
                if (rsTmp != null)
                {
                    rsTmp.close();
                    rsTmp = null;
                }
                if (psTmp != null)
                {
                    psTmp.close();
                    psTmp = null;
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
     * function 已审核 贴现
     * 
     */
    public String getCheckDiscount(long lCurrencyID, long lOfficeID, Connection con)
        throws RemoteException, Exception
    {
        String sResult = "";
        //Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        StringBuffer sb = new StringBuffer();
        long lContractID = -1;
        long lBorrowClientID = -1;
        String sContractCode = "";
        try
        {
            //已审核状态的贴现开始
            //con = Database.get_jdbc_connection ();  
            //con = Database.getConnection();
            if (lCurrencyID == Constant.CurrencyType.RMB)
            {
                sb.setLength(0);
                sb.append(" select distinct a.sContractCode,a.nBorrowClientID ");
                sb.append(" from loan_contractForm a ");
                sb.append("     ,loan_discountcontractbill b ");
                sb.append(" where 1=1 ");
                sb.append(" and a.nOfficeID = " + lOfficeID);
                sb.append(" and a.nCurrencyID = " + lCurrencyID);
                sb.append(" and b.nContractID = a.ID ");
                sb.append(
                    " and a.nStatusID = " + LOANConstant.ContractStatus.CHECK);
                sb.append(
                    " and b.nStatusID > " + Constant.RecordStatus.INVALID);
                sb.append(" and to_char(b.dtEnd,'yyyy-mm-dd') =  ");
                sb.append("     to_char(sysdate,'yyyy-mm-dd') ");
                sb.append(" and not exists ( ");
                sb.append("  select * from loan_discountcredence a ");
                sb.append("               ,loan_contractForm b ");
                sb.append("  where a.nContractID = b.ID ");
                sb.append("    and a.nStatusID >= ");
                sb.append(LOANConstant.DiscountCredenceStatus.SAVE);
                sb.append("                 ) ");
                //Log.print("贴现凭证:"+sb.toString());
                ps = con.prepareStatement(sb.toString());
                rs = ps.executeQuery();
                while (rs != null && rs.next())
                {
                    sContractCode = rs.getString("sContractCode");
                    lBorrowClientID = rs.getLong("nBorrowClientID");
                    sResult += this.getSimpleClientNameByID(lBorrowClientID) + DataFormat.formatString(sContractCode)
                        + " 贴现合同已审核，请提交贴现凭证；  ";
                }
                //已审核状态的贴现结束    
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
        
        return sResult;
    }
    /*
     * @author haoning
     * @time 2003-12-12
     * @param lCurrencyID
     * @param lOfficeID
     * function 已审核 贴现凭证
     * 
     */
    public String getCheckDiscountCredence(long lCurrencyID, long lOfficeID, Connection con)
        throws RemoteException, Exception
    {
        String sResult = "";
        //Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        StringBuffer sb = new StringBuffer();
        long lContractID = -1;
        String sContractCode = "";
        try
        {
            //已审核状态的贴现凭证开始
            //con = Database.get_jdbc_connection (); 
            //con = Database.getConnection();
            if (lCurrencyID == Constant.CurrencyType.RMB)
            {
                sb.setLength(0);
                sb.append(" select distinct b.sContractCode ");
                sb.append(" from loan_discountcredence a ");
                sb.append("    , loan_contractform b ");
                sb.append(" where a.nContractID = b.ID ");
                sb.append(" and a.nStatusID =  ");
                sb.append(LOANConstant.DiscountCredenceStatus.CHECK);
                sb.append(" and b.nCurrencyID = " + lCurrencyID);
                sb.append(" and b.nOfficeID = " + lOfficeID);
                sb.append(
                    " and b.nStatusID > " + Constant.RecordStatus.INVALID);
                sb.append(" and to_char(a.dtAtterm,'yyyy-mm-dd') =  ");
                sb.append("     to_char(sysdate,'yyyy-mm-dd')");
                //Log.print("凭证已审核:"+sb.toString());
                ps = con.prepareStatement(sb.toString());
                rs = ps.executeQuery();
                while (rs != null && rs.next())
                {
                    sContractCode = rs.getString("sContractCode");
                    sResult += DataFormat.formatString(sContractCode)
                        + " 贴现合同下贴现凭证已审核；  ";
                }
                //已审核状态的贴现凭证结束           
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
        
        return sResult;
    }
    /*
     * @author haoning
     * @time 2003-12-12
     * @param lCurrencyID
     * @param lOfficeID
     * function 已审核 放款通知单
     * 
     */
    public String getCheckLoanPay(long lCurrencyID, long lOfficeID, Connection con)
        throws RemoteException, Exception
    {
        String sResult = "";
        //Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        StringBuffer sb = new StringBuffer();
        long lContractID = -1;
        long lBorrowClientID = -1;
        String sContractCode = "";
        try
        {
            //已审核”状态的放款通知单开始
            //con = Database.get_jdbc_connection (); 
            //con = Database.getConnection();
            sb.setLength(0);
            sb.append(" select distinct a.sCode,b.sContractCode,b.nBorrowClientID ");
            sb.append(" from loan_payform a");
            sb.append("     ,loan_Contractform b ");
            sb.append(" where a.nContractID = b.ID ");
            sb.append(" and a.nSourceTypeID= ");
            sb.append(LOANConstant.LoanPayNoticeModifySourceType.XD);
            sb.append(" and a.nStatusID =  ");
            sb.append(LOANConstant.LoanPayNoticeStatus.CHECK);
            sb.append(" and b.nCurrencyID = " + lCurrencyID);
            sb.append(" and b.nOfficeID = " + lOfficeID);
            sb.append(" and a.nStatusID > " + Constant.RecordStatus.INVALID);
            sb.append(" and to_char(a.dtEnd,'yyyy-mm-dd') =  ");
            sb.append("      to_char(sysdate,'yyyy-mm-dd')");
            //Log.print("放款通知单:"+sb.toString());
            ps = con.prepareStatement(sb.toString());
            rs = ps.executeQuery();
            while (rs != null && rs.next())
            {
                sContractCode = rs.getString("sContractCode");
                lBorrowClientID = rs.getLong("nBorrowClientID");
                sResult += this.getSimpleClientNameByID(lBorrowClientID) + DataFormat.formatString(sContractCode)
                    + " 合同下放款通知单 "
                    + DataFormat.formatString(rs.getString("sCode"))
                    + " 已审核；  ";
            }
            //已审核”状态的放款通知单结束
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
                Log.print(e.toString());
                throw new Exception(e.getMessage());
            }
        }
        
        return sResult;
    }
    /*
     * @author liwang
     * @time 2006-04-06
     * @param lContractID
     * function 逾期合同逾期计划
     * 
     */
    public String getAwakeContractPlan1(
        long lContractID,
        long lAwakeDay,
        long lAheadAwakeDay,
        String sContractCode,
        Connection con)
        throws RemoteException, Exception
    {
        String sResult = "";
        //Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        StringBuffer sb = new StringBuffer();
        Timestamp tsEndDate = null; // 终止日期
        try
        {
            //con = Database.getConnection();
            //con = Database.get_jdbc_connection ();  
            //con = Database.get_type4_connection();   
            // loan_loancontractplan.nstatusid =1标表示是有效记录
            //如下SQL语句是用于求得某个合同的所有计划版本ID！
            sb.setLength(0);
            sb.append(" select a.dtPlanDate ");
            sb.append(" from loan_loancontractplandetail a ");
            sb.append(" ,loan_loancontractplan b ");
            sb.append(" ,loan_contractform c ");
            sb.append(" where b.nStatusID = " + Constant.RecordStatus.VALID);
            sb.append(" and b.nPlanVersion in ");
            sb.append(" ( select MAX(nPlanVersion) ");
            sb.append("   from loan_loancontractplan ");
            sb.append("   where  nContractID = " + lContractID);
            sb.append(" ) ");
            sb.append(" and b.nContractID = " + lContractID);
            sb.append(" and b.ID = a.nContractPlanID ");
            sb.append(" and a.nPayTypeID = " + LOANConstant.PlanType.REPAY);
            sb.append(" and c.ID = " + lContractID);
            
            //Log.print(" 逾期合同逾期计划: "+sb.toString ());
            
            long lCountNumTmp = 0; 
            String sEndDateTmp = ""; //记录逾期日期
            ps = con.prepareStatement(sb.toString());
            rs = ps.executeQuery();
            while (rs.next() && rs != null)
            {
                tsEndDate = rs.getTimestamp(1);
                //将每笔计划的逾期日和现在进行比较，看是否有到期的
                
                if (tsEndDate != null)
                {
                	
                    if ((getAwakeContractCode(lAheadAwakeDay,lAwakeDay,tsEndDate,sContractCode)).length() > 0)
                    {
                    	
                        lCountNumTmp++;
                        if (lCountNumTmp == 1)
                        {
                            sEndDateTmp = DataFormat.getChineseDateString(tsEndDate);
                        }
                        else
                        {
                            //Log.print("2");
                            sEndDateTmp += " 以及 " + DataFormat.getChineseDateString(tsEndDate);
                        }
                    } //
                }
            } //while 
            if (lCountNumTmp > 0)
            {
                sResult += "合同 " + sContractCode + " 下将有 " + lCountNumTmp + " 笔执行计划逾期；";
                    //+ " 逾期日分别是：" + sEndDateTmp + " ; "
                    //+ " ";
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
           
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
            throw new Exception(e.getMessage());
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
     * @author liwang
     * @time 2006-04-03
     * @param 
     * function 逾期提醒
     */ 
    public String getAwakeContractExceed(long lCurrencyID, long lOfficeID,Connection conn)
    throws RemoteException, Exception
{
    	
        PreparedStatement ps = null;
        ResultSet rs = null;
        StringBuffer sb = new StringBuffer();
        String sResult = "";
        long count=0;
        long lAwakeDay = -1;
        long lAheadAwakeDay = -1;
        /*************合同参数**************/
        long lContractID = -1; //合同ID
        String sContractCode = ""; //合同编号
        long lLoanTypeID = -1; //贷款类型
        long lisCircle = Constant.YesOrNo.NO; //循环贷款
        long lisBuy = Constant.YesOrNo.NO; //卖方贷款
        long lBorrowClientID = -1;
        Timestamp tsEndDate = null; // 终止日期
        try
        {
            //con = Database.getConnection();
            //con = Database.get_jdbc_connection ();      
            //con = Database.get_type4_connection(); 
        	
            //-----------------合同执行计划逾期------------// 
            long[] lLoanType = LOANConstant.LoanType.getAllCode(lOfficeID,lCurrencyID);
            String strLoanType = "";
            
            for (int i = 0; i < lLoanType.length; i++)
            {
                if (i == 0)
                {
                    strLoanType = "" + lLoanType[i];
                }
               
                else
                {
                    strLoanType += "," + lLoanType[i];
                }
            }
            
            if(strLoanType == "")
            {
				strLoanType = "0";
            }
            
            sb.setLength(0);
            sb.append("SELECT DISTINCT ID, NISSALEBUY, NISCIRCLE ");
            sb.append(" ,SCONTRACTCODE , DTENDDATE, NTYPEID,NBORROWCLIENTID  ");
            sb.append(" FROM LOAN_CONTRACTFORM  ");
            sb.append(" where NCURRENCYID = " + lCurrencyID);
            sb.append(" and NOFFICEID = " + lOfficeID);
            sb.append(
                " and ( nStatusID =" + LOANConstant.ContractStatus.OVERDUE);
            
            sb.append("     ) ");
            sb.append(" and NTYPEID IN (" + strLoanType + ")");
            
            //System.out.println(sb.toString());
            
            ps = conn.prepareStatement(sb.toString());
            //ps.setLong (1,lCurrencyID);
            //ps.setLong (2,lOfficeID);
            //ps.setLong (3,LOANConstant.ContractStatus.ACTIVE);
            //ps.setLong (4,LOANConstant.ContractStatus.EXTEND);
            rs = ps.executeQuery();
            while (rs.next() && rs != null)
            {
                lLoanTypeID = rs.getLong("NTYPEID");
                tsEndDate = rs.getTimestamp("DTENDDATE");
                sContractCode = rs.getString("SCONTRACTCODE");
                lisCircle = rs.getLong("NISCIRCLE");
                lisBuy = rs.getLong("NISSALEBUY");
                lContractID = rs.getLong("ID");
                lBorrowClientID = rs.getLong("NBORROWCLIENTID");

                lAheadAwakeDay = lAheadAwakeDays[(int) LOANConstant.AtTermAwakeType.EXCEED];
                lAwakeDay = lAwakeDays[(int) LOANConstant.AtTermAwakeType.EXCEED];
                
                
                // 合同逾期收集
                if (lAwakeDay > 0 && tsEndDate != null && sContractCode != null)
                {
                	if (lLoanTypeID == LOANConstant.LoanType.ZY
                            || lLoanTypeID == LOANConstant.LoanType.WT
                            || lLoanTypeID == LOANConstant.LoanType.YT
                            || lLoanTypeID == LOANConstant.LoanType.ZGXE
                            || lLoanTypeID == LOANConstant.LoanType.MFXD
           	                || lLoanTypeID == LOANConstant.LoanType.TX
                            || lLoanTypeID == LOANConstant.LoanType.ZTX
                            || lLoanTypeID == LOANConstant.LoanType.DB
                    ) //专门对合同计划
                    {
                		
                		/*sResult += getAwakeContractPlan1(
                                lContractID,
                                lAheadAwakeDay,
                                lAwakeDay,
                                sContractCode,
                                conn);*/
                		
                		sResult+=this.getSimpleClientNameByID(lBorrowClientID) + "合同 " + sContractCode + " 已逾期！ ";
                    }
                    else //对非合同计划的提醒的处理-业务提醒
                    {
                    	if ((getAwakeContractCode(lAheadAwakeDay,lAwakeDay,tsEndDate,sContractCode)).length() > 0)
                        {
                            sResult += this.getSimpleClientNameByID(lBorrowClientID) + sContractCode + " 即将逾期； ";
                         }
                    } //else
                } //if
            }
           
           //System.out.println("sResult:"+sResult);
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
           // if (conn != null)
            //{
            //	conn.close();
            //	conn = null;
           // }
            /****************  合同逾期提醒结束  **************/
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
            
            throw new Exception(e.getMessage());
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
//               if (conn != null)
//                {
//            	   conn.close();
//            	   conn = null;
//                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception(e.getMessage());
                //throw new IException("Gen_E001");
            }
        }
        return sResult;
}
    
    /*
     * @author leiyang
     * @time 2008-06-03
     * @param lCurrencyID
     * @param lOfficeID
     * function 委托贷款手续费收取提醒
     * 
     */
    public String getAwakeWTCommission(long lCurrencyID, long lOfficeID, Connection con)
        throws RemoteException, Exception
    {
        String sResult = "";
        PreparedStatement ps = null;
        ResultSet rs = null;
        StringBuffer strBuffer = null;
        //系统开机日期
        Timestamp systemDate = Env.getSystemDate(lOfficeID, lCurrencyID);
        //提醒天数
        long lAwakeDay = lAwakeDays[(int) LOANConstant.AtTermAwakeType.WTCOMMISSION];
        //提前提醒天数
        long lAheadAwakeDay = lAheadAwakeDays[(int) LOANConstant.AtTermAwakeType.WTCOMMISSION];
        
        try
        {
        	strBuffer = new StringBuffer();
        	strBuffer.append("select * from");
        	strBuffer.append(" (select contractid, contractcode, payformid, payformcode, nextdate, nextdate-"+ lAheadAwakeDay +" AheadAwakeDate, nextdate-"+ lAheadAwakeDay +"+"+ lAwakeDay +" AwakeDate from");
        	strBuffer.append(" (select t1.id contractid, t1.scontractcode contractcode, t2.id payformid, t2.scode payformcode,");
        	strBuffer.append(" ADD_MONTHS(to_date(to_char(t2.DTSTART,'yyyy-mm') || '-20','yyyy-mm-dd'), round(to_number(to_date('"+ DataFormat.getDateString(systemDate) +"','yyyy-mm-dd') - t2.DTSTART)/365, 0)*12) nextdate");
        	strBuffer.append(" from LOAN_CONTRACTFORM t1, Loan_Payform t2, sett_subaccount t3");
        	strBuffer.append(" where t1.ncurrencyid = "+ lCurrencyID +" and t1.nofficeid = "+ lOfficeID);
        	strBuffer.append(" and t1.nstatusid = " + LOANConstant.ContractStatus.ACTIVE);
        	strBuffer.append(" and t1.ntypeid = " + LOANConstant.LoanType.WT);
        	strBuffer.append(" and t1.nchargeratetypeid = " + LOANConstant.ChargeRatePayType.YEAR);
        	strBuffer.append(" and t1.id = t2.ncontractid");
        	strBuffer.append(" and t2.nstatusid = " + LOANConstant.LoanPayNoticeStatus.USED);
        	strBuffer.append(" and (t2.DTEND - t2.DTSTART) >= 365");
        	strBuffer.append(" and t2.id = t3.al_nloannoteid");
        	strBuffer.append(" and t3.nstatusid = " + SETTConstant.SubAccountStatus.NORMAL + ")");
        	strBuffer.append(" where payformid not in(select payformid from");
        	strBuffer.append(" (select t1.id contractid, t1.scontractcode contractcode, t2.id payformid, t2.scode payformcode,");
        	strBuffer.append(" ADD_MONTHS(to_date(to_char(t2.DTSTART,'yyyy-mm') || '-20','yyyy-mm-dd'), round(to_number(to_date('"+ DataFormat.getDateString(systemDate) +"','yyyy-mm-dd') - t2.DTSTART)/365, 0)*12) nextdate");
        	strBuffer.append(" from LOAN_CONTRACTFORM t1, Loan_Payform t2, sett_subaccount t3");
        	strBuffer.append(" where t1.ncurrencyid = "+ lCurrencyID +" and t1.nofficeid = "+ lOfficeID);
        	strBuffer.append(" and t1.nstatusid = " + LOANConstant.ContractStatus.ACTIVE);
        	strBuffer.append(" and t1.ntypeid = " + LOANConstant.LoanType.WT);
        	strBuffer.append(" and t1.nchargeratetypeid = " + LOANConstant.ChargeRatePayType.YEAR);
        	strBuffer.append(" and t1.id = t2.ncontractid");
        	strBuffer.append(" and t2.nstatusid = " + LOANConstant.LoanPayNoticeStatus.USED);
        	strBuffer.append(" and (t2.DTEND - t2.DTSTART) >= 365");
        	strBuffer.append(" and t2.id = t3.al_nloannoteid");
        	strBuffer.append(" and t3.nstatusid = " + SETTConstant.SubAccountStatus.NORMAL + ") a1 , SETT_TRANSREPAYMENTLOAN a2");
        	//strBuffer.append(" where a1.contractid = a2.nloanaccountid");
        	strBuffer.append(" where a1.payformid = a2.nloannoteid");
        	//strBuffer.append(" and a2.DTEXECUTE < a1.nextdate");
        	//strBuffer.append(" and a2.DTEXECUTE between a1.nextdate-"+ lAheadAwakeDay +" and a1.nextdate+"+ lAwakeDay);
        	strBuffer.append(" and a2.DTEXECUTE between a1.nextdate-30 and a1.nextdate+30");
        	strBuffer.append(" and a2.nstatusid = " + SETTConstant.TransactionStatus.CHECK);
        	strBuffer.append(" and a2.MREALCOMMISSION > 0)");
        	strBuffer.append(" ) t where t.AheadAwakeDate <= to_date('"+ DataFormat.getDateString(systemDate) +"','yyyy-mm-dd')");
        	strBuffer.append(" and t.AwakeDate >= to_date('"+ DataFormat.getDateString(systemDate) +"','yyyy-mm-dd')");
        	
        	ps = con.prepareStatement(strBuffer.toString());
        	
        	rs = ps.executeQuery();
        	while(rs != null && rs.next()){
        		String strContractCode = rs.getString("contractcode");
        		String strPayFormCode = rs.getString("payformcode");
        		Timestamp nextDate = rs.getTimestamp("nextdate");
        		//提前开始提醒日期
        		Timestamp aheadAwakeDate = rs.getTimestamp("AheadAwakeDate");
        		//提醒到日期
        		Timestamp awakeDate = rs.getTimestamp("AwakeDate");
        		
        		if(aheadAwakeDate != null && awakeDate != null){
	    			sResult = sResult + "委托贷款合同："+ strContractCode +" 的 "+ strPayFormCode +" 放款单将于"+ DataFormat.getChineseDateString(nextDate) +"收取手续费&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
        		}
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
    public void getAllAwakeContract()
        throws RemoteException, Exception
    {
        PreparedStatement ps = null;
        Connection conn = null;
        StringBuffer sbSQL = null;
        ResultSet rs = null;
        
        String sResult = " ";
        try
        {
           // Log.print("***进入方法 -- getAllAwakeContract ***");
            
            conn = Database.getConnection();

            sbSQL = new StringBuffer();
            //sbSQL.append(" select distinct nOfficeID,nCurrencyID from SETT_OFFICETIME c \n");
            sbSQL.append(" select nOfficeID,nCurrencyID from SETT_OFFICETIME t where t.nofficeid in (select distinct nOfficeID from LOAN_ATTERMAWAKESETTING)\n");
            ps = conn.prepareStatement(sbSQL.toString());
            rs = ps.executeQuery();
            
            while (rs.next())
            {
                long lCurrencyID = -1;
                long lOfficeID = -1;
                //Connection con = null;
                
                AwakeInfo awakeinfo = new AwakeInfo();
                
                lOfficeID=rs.getLong("nOfficeID");
                lCurrencyID=rs.getLong("nCurrencyID");
                awakeinfo.setOfficeID(lOfficeID);
                awakeinfo.setCurrencyID(lCurrencyID);
                
                //con = Database.getConnection();
                
                awakeinfo.setCon(conn);
                //业务提醒设置
                getAwakeSetting(lCurrencyID, lOfficeID, conn);
            
                //到期合同 或合同计划
                sResult = getAwakeContract(lCurrencyID, lOfficeID, conn);
                
                //已审核的放款单
                sResult += getCheckLoanPay(lCurrencyID, lOfficeID, conn);
            
                //结息 由于合同数太多，次功能被砍掉
              //  sResult += getAwakeSettleInterest(lCurrencyID, lOfficeID, conn);
            
                //利率调整
                //sResult += getAwakeRateAdjust(lCurrencyID, lOfficeID, conn);
                sResult += getLoanAdjustRate(lCurrencyID, lOfficeID, conn);

                //到期贴现
                sResult += getAwakeDiscount(lCurrencyID, lOfficeID, conn);
                
                //已审核的贴现
                sResult += getCheckDiscount(lCurrencyID, lOfficeID, conn);
                
                //已审核的贴现凭证
                sResult += getCheckDiscountCredence(lCurrencyID, lOfficeID, conn);
                
                //逾期提醒
                sResult += getAwakeContractExceed(lCurrencyID, lOfficeID,conn);
                
                //委托贷款手续费收取提醒
                sResult += getAwakeWTCommission(lCurrencyID, lOfficeID,conn);
                

                //Log.print("lOfficeID:"+lOfficeID);
                //Log.print("lCurrencyID:"+lCurrencyID);
                //Log.print("Msg:"+sResult);
                //获得业务提醒信息
                //String strMsg = this.getRemindMSG(lOfficeID,lCurrencyID);
                String strMsg = sResult;
                //保存业务提醒信息
                String strKey = String.valueOf(lOfficeID)+String.valueOf(lCurrencyID);
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
     * @time 2003-12-11
     * @param args
     * function
     */
    public static void main(String[] args)
    {
        String sss = "";
        Awake a = new Awake();
        Connection conn = null;
        try
        {
            //a.getAllAwakeContract();
        	conn = Database.getConnection();
        	a.getAwakeContractExceed(1, 1, conn);
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
    /**
     * @param 
     * function 得到/设置
     * return long[]
     */
    public long[] getAwakeDays()
    {
        return lAwakeDays;
    }
    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setAwakeDays(long[] ls)
    {
        this.lAwakeDays = ls;
    }
    /**
     * function 得到/设置
     * return long[]
     */
    public long[] getAheadAwakeDays()
    {
        return lAheadAwakeDays;
    }
    /**
     * @param ls
     * function 得到/设置
     * return void
     */
    public void setAheadAwakeDays(long[] ls)
    {
        this.lAheadAwakeDays = ls;
    }

    /*
     * @author Boxu
     * @time 2008-12-17
     * @param lCurrencyID
     * @param lOfficeID
     * function 到期利率调整提醒
     */
    public String getLoanAdjustRate(long lCurrencyID, long lOfficeID, Connection con) throws RemoteException, Exception
    {
        //Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        StringBuffer sb = new StringBuffer();
        /***********利率调整设置参数************/
        String sResult = "";
        String sLoanPay = "";
        long lCount = 0;
        long lContractID = -1; 			//合同ID
        String sContractCode = ""; 		//合同编号
        Timestamp payStartDate = null; 	//放款单开始日期
        Timestamp payEndDate = null; 	//放款单结束日期
        Timestamp tsOutDate = null; 	//放款日期
        Timestamp tsTempDate = null; 	//日期
        long lAheadAwakeDay = -1;
        long lAwakeDay = -1;
        long bankinterestid = -1;  		//放款单利率ID
        int adjustrateterm = 0;			//调整期限
        Timestamp rateadjustdate = null;//上次调整日期
        InterestRateInfo rateInfo = null;
        boolean bl = false;
        
        lAheadAwakeDay = lAheadAwakeDays[(int) LOANConstant.AtTermAwakeType.DKLVTZ];
        lAwakeDay = lAwakeDays[(int) LOANConstant.AtTermAwakeType.DKLVTZ];
        try
        {
            //con = Database.getConnection();
            sb.setLength(0);
            sb.append(" select b.sContractCode, a.scode ,a.dtOutDate, a.nContractID, a.dtstart, a.dtend, ");
            sb.append(" a.rateadjustdate, a.nbankinterestid, c.adjustrateterm, b.dtEndDate ");
            sb.append(" from loan_payform a, loan_contractform b, loan_loanform c, sett_subaccount ss ");
            sb.append(" where a.nContractID = b.ID and b.nloanid = c.id ");
            sb.append(" and b.ncurrencyid = " + lCurrencyID);
            sb.append(" and b.nofficeid = " + lOfficeID);
            sb.append(" and ( b.nStatusID =" + LOANConstant.ContractStatus.ACTIVE);
            sb.append("       or b.nStatusID =" + LOANConstant.ContractStatus.EXTEND);
            sb.append("     ) ");
            sb.append(" and b.nTypeID = " + LOANConstant.LoanType.ZY + " ");
            sb.append(" and a.id = ss.al_nloannoteid and ss.nstatusid != "+ SETTConstant.SubAccountStatus.FINISH +" ");
            sb.append(" order by b.sContractCode, a.scode ");
            ps = con.prepareStatement(sb.toString());
            rs = ps.executeQuery();
            while (rs != null && rs.next())
            {
            	bankinterestid = rs.getLong("nbankinterestid");
            	adjustrateterm = rs.getInt("adjustrateterm");		//调整周期
            	payStartDate = rs.getTimestamp("dtstart");
            	payEndDate = rs.getTimestamp("dtend");
            	rateadjustdate = rs.getTimestamp("rateadjustdate");
            	sContractCode = rs.getString("sContractCode");
            	
            	if(adjustrateterm > 0)
            	{
            		if(rateadjustdate != null)
            		{
            			tsTempDate = rateadjustdate;
            		}
            		else
            		{
            			tsTempDate = payStartDate;
            		}
            		
            		//下一次提醒日期
            		tsTempDate = DataFormat.getNextMonth(tsTempDate, adjustrateterm);
            		
            		//如果下一次提醒日期大于放款单结束日期跳出
            		if(tsTempDate.compareTo(payEndDate) > 0)
            		{
            			continue;
            		}
            		else
            		{
                    	if ((getAwakeContractCode(lAheadAwakeDay,lAwakeDay,tsTempDate,sContractCode)).length() > 0)
                    	{
                    		lCount++;
                    	}
            		}
            	}
            	else
            	{
            		if(bankinterestid > 0)
            		{
            			//查询是否有新的利率设置
            			rateInfo = findLoanNewRate(bankinterestid ,lCurrencyID, lOfficeID);
            			if(rateInfo != null)
            			{
            				//生效日
            				tsTempDate = rateInfo.getValiDate();
            				
            				if ((getAwakeContractCode(lAheadAwakeDay,lAwakeDay,tsTempDate,sContractCode)).length() > 0)
                        	{
            					lCount++;
                        	}
            			}
            		}
            	}
            }
            
            if(lCount > 0)
            {
            	sResult = " 共有<A href='../query/rA001-c.jsp' target=_blank>" + lCount  + "</A>笔放款单需要调整执行利率； ";
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
        //catch (RemoteException e)
        //{
        //    e.printStackTrace();
        //    throw new Exception(e.getMessage());
        //}
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
    
    //查询是否有新的利率
    public Collection findLoanAdjustRate(long lOfficeID, long lCurrencyID) throws Exception
    {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        StringBuffer sb = new StringBuffer();
        double newRate = 0.0;
        InterestRateInfo rateInfo = null;
        Timestamp dtvalidate = null;
        Timestamp adjustdate = null;
        long bankinterestid = -1;  			//放款单利率ID
        int adjustrateterm = 0;				//调整期限
        Timestamp rateadjustdate = null;	//上次调整日期
        Timestamp payStartDate = null; 		//放款单开始日期
        Timestamp payEndDate = null; 		//放款单结束日期
        Timestamp tsTempDate = null; 		//日期
        String sContractCode = ""; 			//合同编号
        long lAheadAwakeDay = -1;
        long lAwakeDay = -1;
        Collection coll = new ArrayList();
        AdjustRateInfo info = null;
        double payformrate = 0.0;
        LoanPayNoticeDao dao = new LoanPayNoticeDao();
        
        try {
            con = Database.getConnection();
            
            getAwakeSetting(lCurrencyID, lOfficeID, con);
            lAheadAwakeDay = lAheadAwakeDays[(int) LOANConstant.AtTermAwakeType.DKLVTZ];
            lAwakeDay = lAwakeDays[(int) LOANConstant.AtTermAwakeType.DKLVTZ];
            
            sb.setLength(0);
            sb.append(" select a.id payID, a.minterestrate, a.nintervalnoticenum, b.sContractCode, a.scode ,a.dtOutDate, a.nContractID, a.dtstart, a.dtend, ");
            sb.append(" a.rateadjustdate, a.nbankinterestid, c.adjustrateterm, b.dtEndDate ");
            sb.append(" from loan_payform a, loan_contractform b, loan_loanform c, sett_subaccount ss ");
            sb.append(" where a.nContractID = b.ID and b.nloanid = c.id ");
            sb.append(" and b.ncurrencyid = " + lCurrencyID);
            sb.append(" and b.nofficeid = " + lOfficeID);
            sb.append(" and ( b.nStatusID =" + LOANConstant.ContractStatus.ACTIVE);
            sb.append("       or b.nStatusID =" + LOANConstant.ContractStatus.EXTEND);
            sb.append("     ) ");
            sb.append(" and b.nTypeID = " + LOANConstant.LoanType.ZY + " ");
            sb.append(" and a.id = ss.al_nloannoteid and ss.nstatusid != "+ SETTConstant.SubAccountStatus.FINISH +" ");
            sb.append(" order by b.sContractCode, a.scode ");
            ps = con.prepareStatement(sb.toString());
            rs = ps.executeQuery();
            
            while (rs != null && rs.next())
            {
            	info = new AdjustRateInfo();
            	
            	bankinterestid = rs.getLong("nbankinterestid");
            	adjustrateterm = rs.getInt("adjustrateterm");		//调整周期
            	payStartDate = rs.getTimestamp("dtstart");
            	payEndDate = rs.getTimestamp("dtend");
            	rateadjustdate = rs.getTimestamp("rateadjustdate");
            	sContractCode = rs.getString("sContractCode");
            	
            	if(adjustrateterm > 0)
            	{
            		if(rateadjustdate != null)
            		{
            			tsTempDate = rateadjustdate;
            		}
            		else
            		{
            			tsTempDate = payStartDate;
            		}
            		
            		tsTempDate = DataFormat.getNextMonth(tsTempDate, adjustrateterm);
            		
            		//如果提醒日期大于放款单结束日期跳出
            		if(tsTempDate.compareTo(payEndDate) > 0)
            		{
            			continue;
            		}
            		else
            		{
            			if ((getAwakeContractCode(lAheadAwakeDay,lAwakeDay,tsTempDate,sContractCode)).length() > 0)
                    	{
	            			//查询是否有新的利率设置
	            			rateInfo = findLoanNewRate(bankinterestid, lOfficeID, lCurrencyID);
	            			if(rateInfo != null)
	            			{
		        				info.setDNewRate(rateInfo.getInterestRate());
		        				info.setDtValiDate(rateInfo.getValiDate());
	            			}
	        				
	            			info.setDtRateAdjustDate(tsTempDate);
	        				info.setSContractCode(rs.getString("sContractCode"));
	        				info.setSPayformCode(rs.getString("scode"));
	        				info.setLPayformTerm(rs.getLong("nintervalnoticenum"));
	        				info.setDRate(rs.getDouble("minterestrate"));
	        				
	        				payformrate = dao.getLatelyRate(rs.getLong("payID"), null);  //得到放款单执行利率
	        				info.setDInterestRate(payformrate);
	        				
	        				coll.add(info);
                    	}
            		}
            	}
            	else
            	{
            		if(bankinterestid > 0)
            		{
            			//查询是否有新的利率设置
            			rateInfo = findLoanNewRate(bankinterestid, lOfficeID, lCurrencyID);
            			
            			if(rateInfo != null)
            			{
            				//生效日
            				tsTempDate = rateInfo.getValiDate();
            				
            				if ((getAwakeContractCode(lAheadAwakeDay,lAwakeDay,tsTempDate,sContractCode)).length() > 0)
                        	{
	            				info.setDNewRate(rateInfo.getInterestRate());
	            				info.setDtValiDate(rateInfo.getValiDate());
	            				info.setDtRateAdjustDate(rateInfo.getValiDate());
	            				info.setSContractCode(rs.getString("sContractCode"));
	            				info.setSPayformCode(rs.getString("scode"));
	            				info.setLPayformTerm(rs.getLong("nintervalnoticenum"));
	            				info.setDRate(rs.getDouble("minterestrate"));
	            				
	            				payformrate = dao.getLatelyRate(rs.getLong("payID"), null);  //得到放款单执行利率
	            				info.setDInterestRate(payformrate);
	            				
	            				coll.add(info);
                        	}
            			}
            		}
            	}
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
            if (con != null)
            {
                con.close();
                con = null;
            }
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
                if (con != null)
                {
                    con.close();
                    con = null;
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception(e.getMessage());
            }
        }
        
        return coll;
    }
	
    //查询是否有新的利率
    public InterestRateInfo findLoanNewRate(long bankinterestid, long lOfficeID, long lCurrencyID) throws Exception
    {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        StringBuffer sb = new StringBuffer();
        double newRate = 0.0;
        Timestamp dtvalidate = null;
        long lID = -1;
        InterestRateInfo info = null;
        
        try {
            con = Database.getConnection();
            sb.setLength(0);
            sb.append(" select a.id, b.mrate, b.dtvalidate ");
            sb.append(" from loan_interestratetypeinfo a, loan_interestrate b, ");
            sb.append(" (select c.adjustratetypeid from loan_interestratetypeinfo c where c.id = "+ bankinterestid +") d ");
            sb.append(" where a.id = b.nbankinteresttypeid and d.adjustratetypeid = a.adjustratetypeid ");
            sb.append(" and a.nofficeid = "+ lOfficeID +" and a.ncurrencyid = "+ lCurrencyID +" ");
            sb.append(" and b.nofficeid = "+ lOfficeID +" and b.ncurrencyid = "+ lCurrencyID +" ");
            sb.append(" order by dtvalidate desc ");
            ps = con.prepareStatement(sb.toString());
            rs = ps.executeQuery();
            if (rs != null && rs.next())
            {
            	dtvalidate = rs.getTimestamp("dtvalidate");
            	newRate = rs.getDouble("mrate");
            	lID = rs.getLong("id");
            	
            	if(lID != bankinterestid)
            	{
	                info = new InterestRateInfo();
	            	info.setInterestRate(newRate);
	                info.setValiDate(dtvalidate);
            	}
            }
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
                if (con != null)
                {
                    con.close();
                    con = null;
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception(e.getMessage());
            }
        }
        
        return info;
    }
    
    /**
     * 根据配置文件，是否显示客户简称
     * @param lClientID
     * @return
     */
    private static String getSimpleClientNameByID(long lClientID)
    {
//    	System.out.println("#################### " + Config.getBoolean(ConfigConstant.LOAN_AWAKE_ISSHOWSIMPLENAME, false));
    	String clientName = "";
    	if(Config.getBoolean(ConfigConstant.LOAN_AWAKE_ISSHOWSIMPLENAME, false))
    	{
    		clientName = NameRef.getSimpleClientNameByID(lClientID);
    		//简称为空，显示全称
    		if(clientName.equals(""))
    			clientName = NameRef.getClientNameByID(lClientID);
    	}
    	return clientName;
    }
    
    public String getLoanApproval(long lCurrencyId,long lOfficeId,long lUserId) throws Exception
    {
     
        PreparedStatement ps = null;
        ResultSet rs = null;
        String result = "";
        StringBuffer sql = new StringBuffer();   
        LoanTransActionDao dao = new LoanTransActionDao();
        Connection conn = null;
        long num = 0;
        try
        {
        	conn = Database.getConnection();
        	InutParameterInfo pInfo = new InutParameterInfo();
        	pInfo.setUserID(lUserId);
        	HashMap hm = FSWorkflowManager.getCurrentList(pInfo);
        	String ApprovalId = dao.mergeString(hm.keySet().toArray());
        	if(!ApprovalId.equals(""))
        	{
	        	sql.append(" select count(*) num from sys_approvalrecord s ");
	        	sql.append(" where s.moduleid ="+Constant.ModuleType.LOAN);
	        	sql.append(" and s.officeid ="+lOfficeId);
	        	sql.append(" and s.currencyid ="+lCurrencyId);
	        	sql.append(" and s.approvalentryid in ("+ApprovalId+")");
	        	System.out.println("sql="+sql.toString());
	        	ps = conn.prepareStatement(sql.toString());
	        	rs = ps.executeQuery();
	        	while(rs.next())
	        	{
	        		num = rs.getLong("num");
	        	}
	        	if(num>0)
	        	{
	        		result = "您共有&nbsp;"+num+"笔业务需要审批!&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
	        	}
        	}
        }catch(Exception e)
        {
        	e.printStackTrace();
        	throw new Exception("获取审批提醒出错!");
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
                throw new Exception(e.getMessage());
            }        	
        }
        return result;
        
    }
    
}