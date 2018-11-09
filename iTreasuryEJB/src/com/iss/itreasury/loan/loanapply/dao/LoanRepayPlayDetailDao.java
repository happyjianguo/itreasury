/*
 * Created on 2003-10-7
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.iss.itreasury.loan.loanapply.dao;

/**
 * @author 赵国栋
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.iss.itreasury.util.*;
import com.iss.itreasury.loan.loanapply.dataentity.*;
public class LoanRepayPlayDetailDao
{
    private Log4j log = new Log4j(Constant.ModuleType.LOAN, this);
    
    private void cleanup(ResultSet rs) throws SQLException
    {
        try
        {
            if (rs != null)
            {
                rs.close();
                rs = null;
            }
        }catch (SQLException sqle){ }
     }
     private void cleanup(CallableStatement cs) throws SQLException
     {
        try
        {
            if (cs != null)
            {
                cs.close();
                cs = null;
            }
         }catch (SQLException sqle){ }
     }
     private void cleanup(PreparedStatement ps) throws SQLException
     {
        try
        {
            if (ps != null)
            {
                ps.close();
                ps = null;
            }
        }catch (SQLException sqle){}
     }
     private void cleanup(Connection con) throws SQLException
     {
        try
        {
            if (con != null)
            {
                con.close();
                con = null;
            }
        }catch (SQLException sqle){ }
     }
    public long insert(LoanPlanDetailInfo pdInfo)
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conn = null;
        String strSQL = null;
        long  lResult=-1;
        
        long ID=-1;
        long planID=pdInfo.getPlanID();
        long payTypeID=pdInfo.getPayTypeID();
        double amount=pdInfo.getAmount();
        String type=pdInfo.getType();
        long lastExtendID=pdInfo.getLastExtendID();
        long lastOverdueID=pdInfo.getLastOverdueID();
        long lastVersionPlanID=pdInfo.getLastVersionPlanID();
        
        
        try
        {
            conn=Database.getConnection();
            /*首先要获得新的ID*/
            strSQL="select nvl(max(ID)+1,1) nID from loan_LoanFormPlanDetail";
            ps=conn.prepareStatement(strSQL);
            rs=ps.executeQuery();
            if ( rs.next() )
            {
                ID=rs.getLong("nID");
            }
            cleanup(rs);
            cleanup(ps);
            if ( ID<0 )
            {
                log.info("Get Next ID Error when Insert a loanPlan Detail");
                return -1;
            }

            /* 开始执行插入*/            
            strSQL="insert into loan_LoanFormPlanDetail ("
                +"ID, nPlanID, dtPlanDate, nPayTypeID, mAmount, sType, "
                +"dtModifyDate, nLastExtendID, nLastOverdueID,nLastVersionPlanID"
                +") values (?, ?, SYSDATE, ?, ?, ?, , ?, ?, ?)";
            ps=conn.prepareStatement(strSQL);
            int n=1;
            ps.setLong(n++,ID);
            ps.setLong(n++,planID);
            ps.setLong(n++,payTypeID);
            ps.setDouble(n++,amount);
            ps.setString(n++,type);
            ps.setLong(n++,lastExtendID);
            ps.setLong(n++,lastOverdueID);
            ps.setLong(n++,lastVersionPlanID);
            
            lResult=ps.executeUpdate();
            cleanup(rs);
            cleanup(ps);
            cleanup(conn);
            
            if ( lResult<0 )
            {
                log.info("insert a loan plan detail fail");
                return -1;
            }
            else
            {
                return ID;
            }
        }
        catch (Exception e)
        {
            
            e.printStackTrace();
            return -1;
        }

    }
    public long delete(long pdID)
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conn = null;
        String strSQL = null;
        long  lResult=-1;
        
        try
        {
            conn=Database.getConnection();
            strSQL="delete loan_LoanFormPlanDetail where ID=?";
            ps=conn.prepareStatement(strSQL);
            ps.setLong(1,pdID);
            lResult=ps.executeUpdate();
            cleanup(ps);
            cleanup(conn);
            if ( lResult<0 )
            {
                log.info("delete loan detail info fail:"+lResult);
                return -1;
            }
            else
            {
                return 1;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return -1;
        }

    }
    public long update(LoanPlanDetailInfo pdInfo)
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conn = null;
        String strSQL = null;
        long  lResult=-1;
        
        long pdID=pdInfo.getID();
        long planID=pdInfo.getPlanID();
        long payTypeID=pdInfo.getPayTypeID();
        double amount=pdInfo.getAmount();
        String type=pdInfo.getType();
        long lastExtendID=pdInfo.getLastExtendID();
        long lastOverdueID=pdInfo.getLastOverdueID();
        long lastVersionPlanID=pdInfo.getLastVersionPlanID();
        
        try
        {
            conn=Database.getConnection();
            strSQL="update loan_LoanFormPlanDetail "
                +"set dtModifyDate=SYSDATE, nPlanID=?, "
                +"nPayTypeID=?, mAmount=?, sType=?, nLastExtendID=?, "
                +"nLastOverdueID=?, nLastVersionPlanID=? "
                +"where ID=?";
            ps=conn.prepareStatement(strSQL);
            int n=1;
            ps.setLong(n++,planID);
            ps.setLong(n++,payTypeID);
            ps.setDouble(n++,amount);
            ps.setString(n++,type);
            ps.setLong(n++,lastExtendID);
            ps.setLong(n++,lastOverdueID);
            ps.setLong(n++,lastVersionPlanID);
            ps.setLong(n++,pdID);
            
            lResult=ps.executeUpdate();
            
            cleanup(ps);
            cleanup(conn);
            
            if ( lResult<0 )
            {
                log.info("update loan plan detail fail:"+lResult);
                return -1;          
            }
            else
            {
                return pdID;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return -1;
        }
    }
    public LoanPlanDetailInfo findByID(long pdID)
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conn = null;
        String strSQL = null;
        long  lResult=-1;
        
        LoanPlanDetailInfo pdInfo=null;
        try
        {
            conn=Database.getConnection();
            strSQL="select * from loan_LoanFormPlanDetail where ID=?";
            ps=conn.prepareStatement(strSQL);
            ps.setLong(1,pdID);
            rs=ps.executeQuery();
            if (rs.next())
            {
                pdInfo=new LoanPlanDetailInfo();
                pdInfo.setID(rs.getLong("ID"));
                pdInfo.setPlanID(rs.getLong("nPlanID"));
                pdInfo.setPlanDate(rs.getTimestamp("dtPlanDate"));
                pdInfo.setPayTypeID(rs.getLong("nPayTypeID"));
                pdInfo.setAmount(rs.getDouble("mAmount"));
                pdInfo.setType(rs.getString("sType"));
                pdInfo.setModifyDate(rs.getTimestamp("dtModifyDate"));
                pdInfo.setLastExtendID(rs.getLong("nLastExtendID"));
                pdInfo.setLastOverdueID(rs.getLong("nLastOverdueID"));
                pdInfo.setLastVersionPlanID(rs.getLong("nLastVersionPlanID"));
            }
            cleanup(rs);
            cleanup(ps);
            cleanup(conn);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
        return pdInfo;
    }
    public static void main(String[] args)
    {
    }
}
