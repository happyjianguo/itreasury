package com.iss.itreasury.loan.bizdelegation;

import java.util.Collection;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.loan.exception.LoanDAOException;
import com.iss.itreasury.loan.exception.LoanException;
import com.iss.itreasury.loan.setting.dao.YTLoanAttendBankDao;
import com.iss.itreasury.loan.setting.dataentity.YTLoanAttendBankInfo;
import com.iss.itreasury.loan.setting.dataentity.YTLoanAttendBankQueryInfo;
import com.iss.itreasury.util.*;

public class  YTLoanAttendBankDelegation
{
    private YTLoanAttendBankDao facade = null;
    
    public YTLoanAttendBankDelegation()
    {
        facade = new YTLoanAttendBankDao();
    }
    /**
     *增加操作
    */
    public long save(YTLoanAttendBankInfo info) throws LoanException
    {
        long id = -1;
        try 
        {
            //增加记录
            if(info.getId()<0)
            {
                long lTrue = 1;//校验一
                if(lTrue > 0)
                {
                    lTrue = 1;//校验二
                }
                if(lTrue > 0)
                {
                    facade.setUseMaxID();
            
                    id = facade.add(info);
                }
                else
                {
                    id = lTrue;
                }
                
                //更新记录
            }
            else 
            {
                long lTrue = 1;//校验
                if(lTrue > 0)
                {
                    facade.update(info);
                    id = info.getId();
                }
                else
                {
                    id = lTrue;
                }
            }
        } 
        catch (ITreasuryDAOException e) 
        {
            throw new LoanDAOException(e);
        }
        return id;
    }

    /**
     *删除操作
    */
    public void delete(long id) throws LoanException,IException
    {

        try 
        {    
            YTLoanAttendBankInfo Info = new YTLoanAttendBankInfo();
            Info.setId(id);
            if(Info.getId() > 0)
            {
                Info = (YTLoanAttendBankInfo)facade.findByID(id,Info.getClass());
            }
            if(Info.getStatusId() == 2)//该银行参与行设置已经被使用。不允许删除!
            {
                Log.print("the record is already used!");
                throw new IException("Loan_E030");
            }
            else
            {
                facade.delete(id);
            }

        } 
        catch (ITreasuryDAOException e) 
        {
            throw new LoanDAOException(e);
        }
    }

    /**
     *单笔查询操作
    */
    public YTLoanAttendBankInfo findById(long id) throws LoanException,IException
    {
        YTLoanAttendBankInfo info = new YTLoanAttendBankInfo();
        
        try 
        {
            facade.CheckAttendBankUsed(id);
            info = (YTLoanAttendBankInfo)facade.findByID(id, info.getClass());            
        } 
        catch (ITreasuryDAOException e) 
        {
            throw new LoanDAOException(e);
        }
        catch (IException ie) 
        {
            throw ie;
        }
        return info;
        
    }
    
    /**
     * 条件查找
     * @return Collection
     */
    public Collection findByLinkSearch(String strLinkSearch) 
    throws LoanException
    {
        return facade.findByLinkSearch(strLinkSearch);
    } 
    
    /**
     * 条件查找
     * @return Collection
     */
    public Collection findByMultiOption(YTLoanAttendBankQueryInfo qInfo) 
    throws LoanException
    {
        StringBuffer sb = new StringBuffer();
        
        if(qInfo.getId() > 0)
        {
            sb.append(" and id = "+qInfo.getId());
        }
        if(qInfo.getOfficeID() > 0)
        {
            sb.append(" and officeid = "+qInfo.getOfficeID());
        }
        if(qInfo.getCurrencyID() > 0)
        {
            sb.append(" and currencyid = "+qInfo.getCurrencyID());
        }
        if((qInfo.getName() != null)&&(qInfo.getName().length() > 0))
        {
            sb.append(" and name like '%"+qInfo.getName()+"%' ");
        }
        if((qInfo.getBank() != null)&&(qInfo.getBank().length() > 0))
        {
            sb.append(" and bank like '%"+qInfo.getBank()+"%' ");
        }
        if((qInfo.getOtherGLSubjectCode() != null)&&(qInfo.getOtherGLSubjectCode().length() > 0))
        {
            sb.append(" and OtherGLSubjectCode like '%"+qInfo.getOtherGLSubjectCode()+"%' ");
        }
        if((qInfo.getOrderParamString() != null)&&(qInfo.getOrderParamString().length() > 0))
        {
            sb.append(" order by "+qInfo.getOrderParamString());
            if(qInfo.getDesc() == Constant.PageControl.CODE_ASCORDESC_DESC)
            {
                sb.append(" desc ");
            }
        }
        else
        {
            sb.append(" order by name ");
        }
        return facade.findByLinkSearch(sb.toString());
    } 

    public static void main(String[] args) 
    {
        YTLoanAttendBankDelegation d = new YTLoanAttendBankDelegation();
        try
        {
            long lR = -1;
            String sCode = "";
            YTLoanAttendBankInfo info = new YTLoanAttendBankInfo();
            sCode = "0011";
            info = (YTLoanAttendBankInfo)d.findById(1);
            Log.print(info.getName());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }




}