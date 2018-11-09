

package com.iss.itreasury.loan.setting.dao;

import java.sql.*;
import java.util.Collection;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.loan.exception.LoanDAOException;
import com.iss.itreasury.loan.exception.LoanException;
import com.iss.itreasury.loan.setting.dataentity.ApprovalOpinionInfo;
import com.iss.itreasury.dao.LoanDAO;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.*;

public class ApprovalOpinionDao extends LoanDAO
{
    protected Log4j log4j = new Log4j(Constant.ModuleType.LOAN, this);
    /**
     * @param tableName
     */
    public ApprovalOpinionDao()
    {
        super("Loan_Remark");
    }
    /*
     *  (non-Javadoc)
     *  新增编号
     */
    public String getApprovalOpinionCode(long lOfficeID,long lCurrencyID) throws LoanDAOException 
    {

        String strSQL = "";
        String strCode = "00000";
        long lCode = 0;
        //Timestamp tsToday = Env.getSystemDateTime();
        //String strYear = DataFormat.getDateString(tsToday).substring(2,4);
        
        try 
        {
            initDAO();
        } 
        catch (ITreasuryDAOException e) 
        {
            throw new LoanDAOException(e);
        }
       
        strSQL = "select nvl(max(code)+1,1) code from LOAN_REMARK ";
        strSQL += " where nOfficeID ="+lOfficeID;
        strSQL += " and   nCurrencyID="+lCurrencyID;
        log4j.debug(strSQL);
  
        try 
        {
            prepareStatement(strSQL);
            ResultSet rs = executeQuery();
        
            if (rs != null && rs.next())
            {
                lCode= rs.getLong(1);
                log4j.debug(strCode);
    
                strCode = DataFormat.reverseFormatAmount(DataFormat.formatInt(lCode, 5, true));
            }
            if(rs != null)
            {
                rs.close();
                rs=null;
            }
        } 
        catch (ITreasuryDAOException e) 
        {
            throw new LoanDAOException("生成审批意见编号产生错误",e);
        } 
        catch (SQLException e) 
        {
            throw new LoanDAOException("生成审批意见编号产生错误",e);
        }
            
        try 
        {
            finalizeDAO();
        } 
        catch (ITreasuryDAOException e) 
        {
            throw new LoanDAOException(e);
        }
        
        log4j.debug(":::::::::: ::::strcode::::::" + strCode);

        return strCode;
    }
    


    /**
     * 条件查找
     * @return Collection
     */
    public Collection findByLinkSearch(String strLinkSearch) 
    throws LoanException
    {
        try 
        {
            initDAO();
            
            String strSQL = "select * from Loan_Remark where 1=1 and statusId > 0 "+strLinkSearch;
            ApprovalOpinionInfo info = new ApprovalOpinionInfo();
            
             
            prepareStatement(strSQL);
            executeQuery();
    
            Collection c = getDataEntitiesFromResultSet(info.getClass());
    
            finalizeDAO();
            return c;
        } 
        catch (ITreasuryDAOException e) 
        {
            throw new LoanDAOException(e);
        }
    }
   
    
    public long getMaxID()  throws ITreasuryDAOException
    {
        long id = -1;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;       
        StringBuffer sb = new StringBuffer();
        sb.append("select nvl(max(ID)+1,1) ID from LOAN_REMARK ");
        try 
        {   //内部维护RS和PS，否则将会产生冲突,但Connection使用同一个  
            con = Database.getConnection();
                 
            ps = con.prepareStatement(sb.toString());
            rs = ps.executeQuery();
            if (rs.next())
            {
                id = rs.getLong("ID");
            }
            if(rs != null)
                rs.close();
            if(ps != null)
                ps.close();
            if(con != null)
                con.close();
        } 
        catch (SQLException e) 
        {
            new ITreasuryDAOException("数据库获取ID产异常",e);
        }    
        catch (Exception e1)
        {
            e1.printStackTrace();
        }           
        return id;      
    }
    
    public long CheckNewCode(ApprovalOpinionInfo info) throws LoanDAOException 
    {
        StringBuffer sb = new StringBuffer();
        String strCode = "";
        long lReturn = 1;
        ResultSet rs = null;
        
        try 
        {
            initDAO();
        } 
        catch (ITreasuryDAOException e) 
        {
            throw new LoanDAOException(e);
        }

        try 
        {
            //在编号不足5位的前补0
            int nLength=info.getCode().length();
            for(int i=nLength;i<5;i++)
            {
                strCode += "0";
            }
            strCode += info.getCode();
            info.setCode(strCode);
            //检查是否存在相同编号
            sb.append(" select id from Loan_Remark where Code ='" + info.getCode() + "'");
            sb.append("  and statusId > "+Constant.RecordStatus.INVALID);
            sb.append(" and nOfficeID =" + info.getNOfficeID() );
            sb.append(" and nCurrencyID ="+ info.getNCurrencyID());
            if(info.getId() > 0)//不是新增
            {
                sb.append("  and id <> "+info.getId());
            }
            log4j.debug(sb.toString());
            
            prepareStatement(sb.toString());
            rs = executeQuery();
            
            if (rs != null && rs.next())
            {
                lReturn = -2;
                Log.print("###############存在相同审核意见编号###########");
            }
            if(rs != null)
            {
                rs.close();
                rs=null;
            }
        } 
        catch (ITreasuryDAOException e) 
        {
            throw new LoanDAOException("检查审核意见产生错误",e);
        } 
        catch (SQLException e) 
        {
            throw new LoanDAOException("检查审核意见产生错误",e);
        }
            
        try 
        {
            finalizeDAO();
        } 
        catch (ITreasuryDAOException e) 
        {
            throw new LoanDAOException(e);
        }
            
        return lReturn;
    }
    public long CheckNewDescrption(ApprovalOpinionInfo info) throws LoanDAOException 
    {
        StringBuffer sb = new StringBuffer();
        String strCode = "";
        long lReturn = 1;
        ResultSet rs = null;
        //Timestamp tsToday = Env.getSystemDateTime();
        //String strYear = DataFormat.getDateString(tsToday).substring(2,4);
        
        try 
        {
            initDAO();
        } 
        catch (ITreasuryDAOException e) 
        {
            throw new LoanDAOException(e);
        }

        try 
        {
            //检查是否存在相同审核意见
            sb.setLength(0);
            sb.append(" select id from Loan_Remark where Description = '" + info.getDescription() + "'");
            sb.append("  and statusId > "+Constant.RecordStatus.INVALID);
            sb.append(" and nOfficeID =" + info.getNOfficeID() );
            sb.append(" and nCurrencyID ="+ info.getNCurrencyID());
            
            if(info.getId() > 0)//不是新增
            {
                sb.append("  and id <> "+info.getId());
            }
            log4j.debug(sb.toString());
            
            prepareStatement(sb.toString());
            rs = executeQuery();
        
            if (rs != null && rs.next())
            {
                Log.print("##############存在相同审核意见描述##########");
                lReturn = -3;
            }
            if(rs != null)
            {
                rs.close();
                rs=null;
            }
        } 
        catch (ITreasuryDAOException e) 
        {
            throw new LoanDAOException("检查审核意见产生错误",e);
        } 
        catch (SQLException e) 
        {
            throw new LoanDAOException("检查审核意见产生错误",e);
        }
            
        try 
        {
            finalizeDAO();
        } 
        catch (ITreasuryDAOException e) 
        {
            throw new LoanDAOException(e);
        }
            
        return lReturn;
    }





    public static void main(String[] args) 
    {
    }

}
