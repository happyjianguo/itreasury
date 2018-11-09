package com.iss.itreasury.loan.bizdelegation;

import java.util.Collection;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.loan.exception.LoanDAOException;
import com.iss.itreasury.loan.exception.LoanException;
import com.iss.itreasury.loan.setting.dao.ApprovalOpinionDao;
import com.iss.itreasury.loan.setting.dataentity.ApprovalOpinionInfo;
import com.iss.itreasury.loan.setting.dataentity.ApprovalOpinionQueryInfo;
import com.iss.itreasury.util.*;

public class ApprovalOpinionDelegation
{
    private ApprovalOpinionDao facade = null;
    
    public ApprovalOpinionDelegation()
    {
        facade = new ApprovalOpinionDao();
    }
    /**
     *���Ӳ���
    */
    public long save(ApprovalOpinionInfo info) throws LoanException
    {
        long id = -1;
        try 
        {
            //���Ӽ�¼
            if(info.getId()<0)
            {
                long lTrue = facade.CheckNewCode(info);
                if(lTrue > 0)
                {
                    lTrue = facade.CheckNewDescrption(info);
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
                
                //���¼�¼
            }
            else 
            {
                long lTrue = facade.CheckNewDescrption(info);
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
     *ɾ������
    */
    public void delete(long id) throws LoanException
    {

        try 
        {    
            //ApprovalOpinionInfo Info = new ApprovalOpinionInfo();
            //Info.setId(id);
            
            //Collection c = facade.findByCondition(Info);
            
            if(id>0)// && c.isEmpty())
            {
                facade.delete(id);
            }
            else 
            {
                throw new LoanException();
            }

        } catch (ITreasuryDAOException e) 
        {
            throw new LoanDAOException(e);
        }
    }

    /**
     *���ʲ�ѯ����
    */
    public ApprovalOpinionInfo findById(long id) throws LoanException
    {
        ApprovalOpinionInfo info = new ApprovalOpinionInfo();
        
        try 
        {
            info = (ApprovalOpinionInfo)facade.findByID(id, info.getClass());            
        } 
        catch (ITreasuryDAOException e) 
        {
            throw new LoanDAOException(e);
        }
        return info;
        
    }
    
    /**
     * ��������
     * @return Collection
     */
    public Collection findByLinkSearch(String strLinkSearch) 
    throws LoanException
    {
        return facade.findByLinkSearch(strLinkSearch);
    } 
    
    /**
     * ��������
     * @return Collection
     */
    public Collection findByMultiOption(ApprovalOpinionQueryInfo qInfo,long lOfficeID,long lCurrencyID) 
    throws LoanException
    {
        StringBuffer sb = new StringBuffer();
        
        if(qInfo.getId() > 0)
        {
            sb.append(" and id = "+qInfo.getId());
        }

        if(lOfficeID > 0)
        {
            sb.append(" and nOfficeID = "+lOfficeID);
        }

        if(lCurrencyID > 0)
        {
            sb.append(" and nCurrencyID = "+lCurrencyID);
        }
   
        if((qInfo.getCode() != null)&&(qInfo.getCode().length() > 0))
        {
            sb.append(" and code like '%"+qInfo.getCode()+"%' ");
        }
        if((qInfo.getDescription() != null)&&(qInfo.getDescription().length() > 0))
        {
            sb.append(" and description like '%"+qInfo.getDescription()+"%' ");
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
            sb.append(" order by loan_remark.code ");
        }
        return facade.findByLinkSearch(sb.toString());
    } 

    public String getNextCode(long lOfficeID,long lCurrencyID) throws LoanException,ITreasuryDAOException
    {
        String strNewCode = "";
        try
        {            
        	strNewCode=facade.getApprovalOpinionCode(lOfficeID,lCurrencyID);
        }
        catch (LoanDAOException e)
        {
            e.printStackTrace();
            throw e;
        }
        return strNewCode;
    }
    public static void main(String[] args) 
    {
        ApprovalOpinionDelegation d = new ApprovalOpinionDelegation();
        try
        {
            long lR = -1;
            String sCode = d.getNextCode(1,1);
            Log.print(sCode);
            ApprovalOpinionInfo info = new ApprovalOpinionInfo();
            sCode = "0011";
            /*
            info.setId(1);
            info.setCode(sCode);
            info.setDescription("123456");
            lR = d.save(info);
            if(lR==-2)
            {
                Log.print("������ͬ���");
            }
            if(lR==-3)
            {
                Log.print("������ͬ������");
            }
            if(lR==2)
            {
                Log.print("�����������ɹ�");
            }
            if(lR==3)
            {
                Log.print("�޸��������ɹ�");
            }//*/
            info = (ApprovalOpinionInfo)d.findById(1);
            Log.print(info.getCode());
        }
        catch (LoanException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (ITreasuryDAOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }



} 
