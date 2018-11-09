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
import com.iss.itreasury.loan.util.LOANConstant;

import java.util.*;

public class FormAssureDao
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
    public long insert(AssureInfo aInfo) throws Exception
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conn = null;
        String strSQL = null;
        long  lResult=-1;
        
        long aID=-1;
        long loanID=aInfo.getLoanID();
        long assureTypeID=aInfo.getAssureTypeID();
        long fillQuestionary=aInfo.getFillQuestionary();
        long clientID=aInfo.getClientID();
        double amount=aInfo.getAmount();
        String impawName=aInfo.getImpawName();
        long nimpawType=aInfo.getNimpawType();
        String impawQuality=aInfo.getImpawQuality();
        String impawStatus=aInfo.getImpawStatus();
        double pledgeAmount=aInfo.getPledgeAmount();
        double pledgeRate=aInfo.getPledgeRate();
        String assureCode=aInfo.getAssureCode();
        long isTopAssure=aInfo.getIsTopAssure();
        long clientType = aInfo.getClientType();
        //added by xiong fei 2010/05/24 增加了回购条件
        String repurchaseCondition = aInfo.getRepurchaseCondition();
        
        try
        {
            conn=Database.getConnection();
            /*首先获得ID*/
            strSQL="select nvl(max(ID)+1,1) nID from loan_LoanFormAssure";
            ps=conn.prepareStatement(strSQL);
            rs=ps.executeQuery();
            if ( rs.next() )
            {
                aID=rs.getLong("nID");
            }
            cleanup(rs);
            cleanup(ps);
            if ( aID<0 )
            {
                log.info("Get Next ID Error when Insert a loan Assure info");
                return -1;
            }
            /*插入操作*/
            strSQL="insert into loan_LoanFormAssure("
                +"ID, nLoanID, nAssureTypeID, nFillQuestionary, nClientID, "
                +"mAmount, sImpawName, nimpawType, sImpawStatus, mPledGeAmount, "
               //added by xiong fei 2010/05/24  增加了一个回购条件repurchaseCondition
                +"mPledGeRate, nStatusID, sAssureCode, nIsTopAssure,repurchaseCondition,ClientType) "
                +"values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            ps=conn.prepareStatement(strSQL);
            int n=1;
            ps.setLong(n++,aID);
            ps.setLong(n++,loanID);
            ps.setLong(n++,assureTypeID);
            ps.setLong(n++,fillQuestionary);
            ps.setLong(n++,clientID);
            ps.setDouble(n++,amount);
            ps.setString(n++,impawName);
            ps.setLong(n++,nimpawType);
            ps.setString(n++,impawStatus);
            ps.setDouble(n++,pledgeAmount);
            ps.setDouble(n++,pledgeRate);
            ps.setLong(n++,Constant.RecordStatus.VALID);
            ps.setString(n++,assureCode);
            ps.setLong(n++,isTopAssure);
            //added by xiong fei 2010/05/24
            ps.setString(n++, repurchaseCondition);
            ps.setLong(n++, clientType);
            lResult=ps.executeUpdate();
            cleanup(ps);
            cleanup(conn);
            if ( lResult<0 )
            {
                log.info("error when insert into a loan assure:"+lResult);
                return -1;    
            }
            else
            {
                return aID;
            }
        }
        catch (Exception e)
        {
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
            e.printStackTrace();
            throw e;
        }finally{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);            
        }
    }
    public long update(AssureInfo aInfo) throws Exception
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conn = null;
        String strSQL = null;
        long  lResult=-1;
        
        long aID=aInfo.getID();
        long loanID=aInfo.getLoanID();
        long assureTypeID=aInfo.getAssureTypeID();
        long fillQuestionary=aInfo.getFillQuestionary();
        long clientID=aInfo.getClientID();
        double amount=aInfo.getAmount();
        String impawName=aInfo.getImpawName();
        String impawQuality=aInfo.getImpawQuality();
        long nimpawType=aInfo.getNimpawType();
        String impawStatus=aInfo.getImpawStatus();
        double pledgeAmount=aInfo.getPledgeAmount();
        double pledgeRate=aInfo.getPledgeRate();
        long statusID=aInfo.getStatusID();
        String assureCode=aInfo.getAssureCode();
        long isTopAssure=aInfo.getIsTopAssure();
        //added by xiong fei 2010/05/24 增加回购条件
        String repurchaseCondition = aInfo.getRepurchaseCondition();
        
        try
        {
            conn=Database.getConnection(); 
            strSQL="update loan_LoanFormAssure set "
                +"nLoanID=?, nAssureTypeID=?, nFillQuestionary=?, "
                +"nClientID=?, mAmount=?, sImpawName=?, nimpawType=?, "
                +"sImpawStatus=?, mPledgeAmount=?, mPledgeRate=?, "
                //added by xiong fei 2010/05/24
                +"nStatusID=?, sAssureCode=?, nIsTopAssure=?,repurchaseCondition=? where ID=?";
                
            ps=conn.prepareStatement(strSQL);
            int n=1;
            ps.setLong(n++,loanID);
            ps.setLong(n++,assureTypeID);
            ps.setLong(n++,fillQuestionary);
            ps.setLong(n++,clientID);
            ps.setDouble(n++,amount);
            ps.setString(n++,impawName);
            ps.setLong(n++,nimpawType);
            ps.setString(n++,impawStatus);
            ps.setDouble(n++,pledgeAmount);
            ps.setDouble(n++,pledgeRate);
            ps.setLong(n++,statusID);
            ps.setString(n++,assureCode);
            ps.setLong(n++,isTopAssure);
            //added by xiong fei 2010/05/24
            ps.setString(n++, repurchaseCondition);
            ps.setLong(n++,aID);
            
           
            lResult=ps.executeUpdate();
            
            cleanup(ps);
            cleanup(conn);
            if ( lResult<0 )
            {
                log.info("update loan Assure info error:"+lResult);
                return -1;
            }
            else
            {
                return aID;
            }
                
        }
        catch (Exception e)
        {
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
            e.printStackTrace();
            throw e;
        }finally{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);       
        }
    }
    public long delete(long aID) throws Exception
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conn = null;
        String strSQL = null;
        long  lResult=-1;
        
        try 
        {
            if ( aID<= 0)
            {
                log.info("The plan assure to delete is :"+aID);
                return -1;
            }
            conn = Database.getConnection ();
            strSQL="delete from loan_LoanFormAssure where ID=?";
            ps=conn.prepareStatement(strSQL);
            ps.setLong(1,aID);
            lResult=ps.executeUpdate();
            cleanup(ps);
            cleanup(conn);
            
            if ( lResult<0 )
            {
                log.info("delete the plan assure error:"+lResult);
                return -1;
            }
            else
            {
                return 1;
            }
        }catch(Exception e){
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
            e.printStackTrace ();
            throw e;
        }finally{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);            
        }
    }
    public AssureInfo findByID(long aID) throws Exception
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conn = null;
        String strSQL = null;
        long  lResult=-1;
        
        AssureInfo aInfo=null;
        
        try 
        {
            conn = Database.getConnection ();
            strSQL="select * from loan_LoanFormAssure where ID=?";
            ps=conn.prepareStatement(strSQL);
            ps.setLong(1,aID);
            rs=ps.executeQuery();
            
            if ( rs.next() )
            {
                aInfo=new AssureInfo();
                aInfo.setID(rs.getLong("ID"));
                aInfo.setLoanID(rs.getLong("nLoanID"));
                aInfo.setAssureTypeID(rs.getLong("nAssureTypeID"));
                aInfo.setFillQuestionary(rs.getLong("nFillQuestionary"));
                aInfo.setClientID(rs.getLong("nClientID"));
                aInfo.setAmount(rs.getDouble("mAmount"));
                aInfo.setImpawName(rs.getString("sImpawName"));
                aInfo.setImpawQuality(rs.getString("sImpawQuality"));
                aInfo.setImpawStatus(rs.getString("sImpawStatus"));
                aInfo.setPledgeAmount(rs.getDouble("mPledGeAmount"));
                aInfo.setPledgeRate(rs.getDouble("mPledGeRate"));
                aInfo.setStatusID(rs.getLong("nStatusID"));
                aInfo.setAssureCode(rs.getString("sAssureCode"));
                aInfo.setIsTopAssure(rs.getLong("nIsTopAssure"));
                aInfo.setNimpawType(rs.getLong("NIMPAWTYPE"));
                //added by xiong fei 2010/05/30 获取回购条件
                aInfo.setRepurchaseCondition(rs.getString("REPURCHASECONDITION"));
                //新增客户类型
                aInfo.setClientType(rs.getLong("ClientType"));
            }
            
            cleanup(rs);
            cleanup(ps);
            cleanup(conn);
            return aInfo;      
                  
        }catch(Exception e){
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
            e.printStackTrace ();
            throw e;
        }finally{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);            
        }
    }    
    
    //added by xiong fei 2010/05/19 通过loanID来获得保证金
    public AssureInfo findByLoanID(long aID) throws Exception
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conn = null;
        String strSQL = null;
        long  lResult=-1;
        
        AssureInfo aInfo=null;
        
        try 
        {
            conn = Database.getConnection ();
            strSQL="select sum(mAmount) mAmount from loan_LoanFormAssure where NLOANID=? and nassuretypeid = 5";
            ps=conn.prepareStatement(strSQL);
            ps.setLong(1,aID);
            rs=ps.executeQuery();
            
            if ( rs.next() )
            {
                aInfo=new AssureInfo();
                aInfo.setAmount(rs.getDouble("mAmount"));
            }
            cleanup(rs);
            cleanup(ps);
            cleanup(conn);
            return aInfo;      
                  
        }catch(Exception e){
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
            e.printStackTrace ();
            throw e;
        }finally{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);            
        }
    }
    
    public Collection findByLoanID(long lLoanID,long lOrderParam,long lDesc) throws Exception
    {
    PreparedStatement ps = null;
    ResultSet rs = null;
    Connection conn = null;
    String strSQL = null;
    long  lResult=-1;
        
    Vector v=null;
    long    planID=-1;
        
    try
    {
        conn=Database.getConnection();
            
        /*组织查询条件*/
        //strSQL="select * from loan_LoanFormAssure where nLoanID="+lLoanID;
        strSQL = " select a.id as id,a.nloanid as nLoanID,a.nassuretypeid as nAssureTypeID,a.Nfillquestionary as nFillQuestionary,a.nclientid as nClientID,a.mamount as mAmount,a.simpawname as sImpawName,a.simpawquality as sImpawQuality,a.simpawstatus as sImpawStatus,a.mpledgeamount as mPledGeAmount,";
        strSQL +=" a.mpledgerate as mPledGeRate,a.nstatusid as nStatusID,a.sassurecode as sAssureCode,a.nistopassure as nIsTopAssure,a.clienttype as clienttype, ";
        strSQL +="b.sCode,b.sName,b.sContacter,b.sPhone,b.SPROVINCE,b.SCITY,b.SADDRESS,b.SBANK1,b.SEXTENDACCOUNT1 from "
            +"loan_loanformassure a,client b ";
        strSQL +=" where b.id=a.nclientid and a.nLoanID="+lLoanID+" and a.clienttype="+LOANConstant.LoanClientType.INTERIOR;
        strSQL +=" union ";
        strSQL += " select a.id as id,a.nloanid as nLoanID,a.nassuretypeid as nAssureTypeID,a.Nfillquestionary as nFillQuestionary,a.nclientid as nClientID,a.mamount as mAmount,a.simpawname as sImpawName,a.simpawquality as sImpawQuality,a.simpawstatus as sImpawStatus,a.mpledgeamount as mPledGeAmount,";
        strSQL +=" a.mpledgerate as mPledGeRate,a.nstatusid as nStatusID,a.sassurecode as sAssureCode,a.nistopassure as nIsTopAssure,a.clienttype as clienttype, ";
        strSQL +=" c.code as sCode,c.name as sName,c.linkman as sContacter, c.tel as sPhone, ' ' as SPROVINCE,' ' as SCITY,' ' as SADDRESS,' ' as SBANK1,' ' as SEXTENDACCOUNT1 ";
        strSQL +=" from loan_loanformassure a, client_extclientinfo c ";
        strSQL +="  where a.nclientid = c.id and a.nloanid ="+lLoanID+" and a.clienttype ="+LOANConstant.LoanClientType.EXTERIOR;
        switch ((int) lOrderParam)
        {
            case 1 : //按客户编码
                strSQL += " order by sCode ";
                break;
            case 2 : //按客户名称
                strSQL += " order by sName ";
                break;
            case 3 : //按保证方式
                strSQL += " order by nAssureTypeID ";
                break;  
            case 4 : //联系人
                strSQL += " order by sContacter ";
            case 5 : //电话
                strSQL += " order by sPhone ";    
                break;
            case 6 : //担保金额
                strSQL += " order by mAmount ";
                break;                
            default :
                strSQL += " order by ID ";
        }

        //判断是升序还是降序，升序是系统默认的，降序是desc
        if (lDesc == Constant.PageControl.CODE_ASCORDESC_DESC)
        {
            strSQL += " desc";
        }
        ps = conn.prepareStatement(strSQL);
        rs = ps.executeQuery();
        
        v=new Vector();
        while (rs.next())
        {
            AssureInfo aInfo=new AssureInfo();
            aInfo.setID(rs.getLong("ID"));
            aInfo.setLoanID(rs.getLong("nLoanID"));
            aInfo.setAssureTypeID(rs.getLong("nAssureTypeID"));
            aInfo.setFillQuestionary(rs.getLong("nFillQuestionary"));
            aInfo.setClientID(rs.getLong("nClientID"));
            aInfo.setAmount(rs.getDouble("mAmount"));
            aInfo.setImpawName(rs.getString("sImpawName"));
            aInfo.setImpawQuality(rs.getString("sImpawQuality"));
            aInfo.setImpawStatus(rs.getString("sImpawStatus"));
            aInfo.setPledgeAmount(rs.getDouble("mPledGeAmount"));
            aInfo.setPledgeRate(rs.getDouble("mPledGeRate"));
            aInfo.setStatusID(rs.getLong("nStatusID"));
            aInfo.setAssureCode(rs.getString("sAssureCode"));
            aInfo.setClientCode(rs.getString("sCode"));
            aInfo.setClientName(rs.getString("sName"));
            aInfo.setClientContacter(rs.getString("sContacter"));
            aInfo.setClientPhone(rs.getString("sPhone"));
            aInfo.setClientProvince( rs.getString("sProvince"));
            aInfo.setClientCity( rs.getString("sCity"));
            aInfo.setClientAddress(rs.getString("sAddress"));
            aInfo.setClientBank1(rs.getString("sBank1"));
            aInfo.setClientBankAccount1( rs.getString("SEXTENDACCOUNT1"));
            aInfo.setIsTopAssure(rs.getLong("nIsTopAssure"));
            aInfo.setClientType(rs.getLong("clienttype"));
            v.add(aInfo);
        }
        cleanup(rs);
        cleanup(ps);
        cleanup(conn);
            
        return v;
            
        }
        catch (Exception e)
        {
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
            e.printStackTrace();
            throw e;
        }finally{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
        }
    }
    public static void main(String[] args)
    {
        FormAssureDao fdao=new FormAssureDao();
        long ret=-1;
 
        
       try
        {
                 AssureInfo aInfo=null;
                    
                    aInfo=new AssureInfo();
                    aInfo.setID(1);
                    aInfo.setLoanID(1);
                    aInfo.setAssureTypeID(1);
                    aInfo.setFillQuestionary(1);
                    aInfo.setClientID(1);
                    aInfo.setAmount(100);
                    aInfo.setImpawName("impawName");
                    aInfo.setImpawQuality("execentlent");
                    aInfo.setImpawStatus("pretty good,too!");
                    aInfo.setAssureCode("");
                    aInfo.setPledgeAmount(12312);
                    aInfo.setPledgeRate(1);
                    
                    ret=fdao.insert(aInfo);
//                    ret=fdao.update(aInfo);
/*            
                    Vector v=(Vector)fdao.findByLoanID(1,3,1);
                    System.out.println("haha"+v.size());
                    for ( int i=0;i<v.size();i++)
                    {
                        System.out.println((AssureInfo)v.get(i));
                    }
             
                    //aInfo=fdao.findByID(1);
                    
                    //System.out.println(""+aInfo.getAmount());  
             //       ret=fdao.delete(1);
              //      System.out.println(""+ret);
                
               */  
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }          
        
    }
    
    public String findClientNameByLoanID(long lLoanID) throws Exception
    {
    PreparedStatement ps = null;
    ResultSet rs = null;
    Connection conn = null;
    String strSQL = null;

        
    String clientName="";

    try
    {
        conn=Database.getConnection();
            
        /*组织查询条件*/
       
        strSQL="select a.*,b.sCode,b.sName,b.sContacter,b.sPhone,b.SPROVINCE,b.SCITY,b.SADDRESS,b.SBANK1,b.SEXTENDACCOUNT1 from "
            +"loan_loanformassure a,client b where b.id=a.nclientid and a.nLoanID="+lLoanID;
       
       
        System.out.println(strSQL);
        ps = conn.prepareStatement(strSQL);
        rs = ps.executeQuery();
 
        if (rs.next())
        {
        	clientName=rs.getString("sName");
        }
        cleanup(rs);
        cleanup(ps);
        cleanup(conn);
            
        return clientName;
            
        }
        catch (Exception e)
        {
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
            e.printStackTrace();
            throw e;
        }finally{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
        }
    }
    
    
}
