

package com.iss.itreasury.loan.setting.dao;

import java.sql.*;
import java.util.Collection;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.loan.exception.LoanDAOException;
import com.iss.itreasury.loan.exception.LoanException;
import com.iss.itreasury.loan.setting.dataentity.YTLoanAttendBankInfo;
import com.iss.itreasury.dao.LoanDAO;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.*;
import com.iss.itreasury.loan.util.LOANConstant;

public class YTLoanAttendBankDao extends LoanDAO
{
    protected Log4j log4j = new Log4j(Constant.ModuleType.LOAN, this);
    /**
     * @param tableName
     */
    public YTLoanAttendBankDao()
    {
        super("Loan_YT_AttendBank");
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
            
            String strSQL = "select * from Loan_YT_AttendBank where 1=1 and statusId > 0 "+strLinkSearch;
            YTLoanAttendBankInfo info = new YTLoanAttendBankInfo();
            
             
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
    /*
     * 检查参与行是否被合同正在使用
     */
    
    public void CheckAttendBankUsed(long AttendBankID)
    throws LoanException,IException
    {
        PreparedStatement ps = null;
        Connection con = null;
        ResultSet rs = null;
        long lResult = -1;
        StringBuffer sbSQL = new StringBuffer();
        long lAttendBankUsed = -1;
        long lAttendStatusId = -1;
        long lConStatusId = -1;
        try
        {
            
            if(AttendBankID > 0)
            {
                con = Database.getConnection();
                
                sbSQL.setLength(0);
                sbSQL.append(" select a.statusId as AttendBankStatusId ");
                sbSQL.append(" ,c.nStatusId as ContractStatusId  ");
                sbSQL.append("   ");
                sbSQL.append(" from ");
                sbSQL.append(" loan_yt_attendbank a ");
                sbSQL.append(" ,loan_YT_LoanContractBankAssign b  ");
                sbSQL.append(" ,loan_ContractForm c  ");
                sbSQL.append("   ");
                sbSQL.append(" where 1=1 ");
                sbSQL.append(" and b.nAttendBankId = a.id ");
                sbSQL.append(" and b.nContractId = c.id ");
                sbSQL.append("   ");
                sbSQL.append(" and a.id = ? ");
                            
                log4j.info(sbSQL.toString());
                ps = con.prepareStatement(sbSQL.toString());
                int nIndex =1;
                ps.setLong(nIndex++, AttendBankID);
                rs=ps.executeQuery();
                if(rs != null && rs.next())
                {
                    lAttendStatusId = rs.getLong("AttendBankStatusId");
                    lConStatusId = rs.getLong("ContractStatusId");
                    if(lConStatusId != LOANConstant.ContractStatus.CANCEL)
                    {
                        lAttendBankUsed = Constant.YesOrNo.YES;
                        //updateAttendBankStatusID(AttendBankID,2);
                    }
                }
                
                if((lAttendBankUsed == Constant.YesOrNo.YES)
                    &&(lAttendStatusId == Constant.RecordStatus.VALID))
                {
                    updateAttendBankStatusID(AttendBankID,2);
                }
                else if((lAttendBankUsed != Constant.YesOrNo.YES)&&(lAttendStatusId == 2))
                {
                    updateAttendBankStatusID(AttendBankID,Constant.RecordStatus.VALID);
                }
            }
            else
            {
                Log.print("没有传标识");
                lResult = -1;
            }
        }
        catch (ITreasuryDAOException e) 
        {
            throw new LoanDAOException(e);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        //*
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
                log4j.error(e.toString());
                throw new IException("Gen_E001");
            }
        }//*/
    
    }
    
    /*
     * 更新参与行的状态
     */
    
    public long updateAttendBankStatusID(long AttendBankID,long StatusID)
    throws LoanException,IException
    {
        PreparedStatement ps = null;
        Connection con = null;
        long lResult = -1;
        StringBuffer sbSQL = new StringBuffer();
        try
        {
            
            if(AttendBankID > 0)
            {
                con = Database.getConnection();
                
                sbSQL.setLength(0);
                sbSQL.append(" UPDATE  loan_yt_attendbank");
                sbSQL.append("  set statusid = ? ");
                sbSQL.append(" where id = ? ");
                            
                log4j.info(sbSQL.toString());
                ps = con.prepareStatement(sbSQL.toString());
                int nIndex =1;
                ps.setLong(nIndex++, StatusID);
                ps.setLong(nIndex++, AttendBankID);
                lResult = ps.executeUpdate();
            }
            else
            {
                Log.print("没有传标识");
                lResult = -1;
            }
        }
        catch (ITreasuryDAOException e) 
        {
            throw new LoanDAOException(e);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        //*
        finally
        {
            try
            {
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
                log4j.error(e.toString());
                throw new IException("Gen_E001");
            }
        }//*/
        return lResult;
    }

}