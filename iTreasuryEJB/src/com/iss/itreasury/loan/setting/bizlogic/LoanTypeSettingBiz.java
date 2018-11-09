/*
 * Created on 2005-5-8
 *
 * Title:				iTreasury
 * @author             	yfan 
 * Company:             iSoftStone
 * Copyright:           Copyright (c) 2003
 * @version
 * Description:         
 */
package com.iss.itreasury.loan.setting.bizlogic;

import java.util.Collection;

import com.iss.itreasury.loan.base.LoanException;
import com.iss.itreasury.loan.setting.dao.*;
import com.iss.itreasury.loan.setting.dataentity.*;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Log4j;

/**
 * @author yfan
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class LoanTypeSettingBiz extends Object implements java.io.Serializable
{
    private static Log4j log4j = null;

    /**
     * No argument constructor required by container.
     */
    public LoanTypeSettingBiz()
    {
        log4j = new Log4j(Constant.ModuleType.LOAN, this);
    }

    /**
     * �������
     */
    public long save(LoanTypeSettingInfo info) throws LoanException
    {
        long lReturn = -1;
        LoanTypeSettingDao dao = new LoanTypeSettingDao();

        try
        {
            /* �������ñ� */
        	
            if (info.getId() <= 0)
            {
                //����ظ�
                if (dao.checkCode(info) <= 0)
                {
                    lReturn = -2;
                }
                //�����ظ�
                else if (dao.checkName(info) <= 0)
                {
                    lReturn = -3;
                } 
                else
                {
                    if(info.getLoanTypeID() == LOANConstant.LoanType.CREDIT){
                    	//�ж������Ƿ����������ͣ�����ֻ����һ�������ͣ�
                		Collection coll = dao.findByLoanTypeID(LOANConstant.LoanType.CREDIT,info.getOfficeID(),info.getCurrencyID());
                		if(coll != null){
                			lReturn = -4;
                			return lReturn;
                		}
                    }
                    if(info.getLoanTypeID() == LOANConstant.LoanType.XDZR){
                    	//�ж��Ŵ��ʲ�ת���Ƿ����������ͣ��Ŵ��ʲ�ת��ֻ����һ�������ͣ�
                		Collection coll = dao.findByLoanTypeID(LOANConstant.LoanType.XDZR,info.getOfficeID(),info.getCurrencyID());
                		if(coll != null){
                			lReturn = -4;
                			return lReturn;
                		}
                    }
                    
                    /* �������ñ� */
                    dao.setUseMaxID();
                    lReturn = dao.add(info);
                } 
            } 
            else if (info.getId() > 0)
            {   
                if (dao.checkCode(info) <= 0)
              {
                lReturn = -2;
              }
                //�����ظ�
               else if (dao.checkName(info) <= 0)
                {
                    lReturn = -3;
                }
               
                /* �������ñ� */
                else
                {
	                dao.update(info);
	                lReturn = info.getId();
                }
            }
        } catch (Exception e)
        {
            throw new LoanException("Gen_E001", e);
        }
        return lReturn;
    }

    /**
     * ɾ������
     */
    public void delete(long lID) throws LoanException
    {
        long lReturn = -1;
        LoanTypeSettingDao dao = new LoanTypeSettingDao();
        LoanTypeSettingInfo info = new LoanTypeSettingInfo();
        
        try
        {
            info.setId(lID);
            info.setStatusID(LOANConstant.RecordStatus.INVALID);
            /* ɾ�����ñ� */
            dao.update(info);
        } catch (Exception e)
        {
            throw new LoanException("Gen_E001", e);
        }
    }
    
    /**
     * ���ʲ�ѯ����
     */
    public LoanTypeSettingInfo findByID(long lID) throws LoanException
    {
        LoanTypeSettingInfo info = new LoanTypeSettingInfo();
        LoanTypeSettingDao dao = new LoanTypeSettingDao();

        try
        {
            info = (LoanTypeSettingInfo)dao.findByID(lID, info.getClass());
        } catch (Exception e)
        {
            throw new LoanException("Gen_E001", e);
        }
        return info;
    }

    /**
     * ��ʲ�ѯ����
     */
    public Collection findByMultiOption(LoanTypeSettingInfo qInfo)
            throws LoanException
    {
        Collection c = null;
        LoanTypeSettingDao dao = new LoanTypeSettingDao();

        try
        {
            c = dao.findByMultiOption(qInfo);
        } catch (Exception e)
        {
            throw new LoanException("Gen_E001", e);
        }

        return c;
    }
    
    /**
     * ��ʲ�ѯ����
     */
    public Collection findByLoanTypeID(long lLoanTypeID)
            throws LoanException
    {
        Collection c = null;
        LoanTypeSettingDao dao = new LoanTypeSettingDao();

        try
        {
            c = dao.findByLoanTypeID(lLoanTypeID);
        } catch (Exception e)
        {
            throw new LoanException("Gen_E001", e);
        }

        return c;
    }
	public Collection findByLoanTypeID(long lLoanTypeID,long lOfficeID,long lCurrencyID)
	    throws LoanException
	{
	Collection c = null;
	LoanTypeSettingDao dao = new LoanTypeSettingDao();
	
	try
	{
	    c = dao.findByLoanTypeID(lLoanTypeID,lOfficeID,lCurrencyID);
	} catch (Exception e)
	{
	    throw new LoanException("Gen_E001", e);
	}
	
	return c;
	}
    
    public Collection findByLoanTypeID(long[] lLoanTypeID)
            throws LoanException
    {
        Collection c = null;
        LoanTypeSettingDao dao = new LoanTypeSettingDao();

        try
        {
            c = dao.findByLoanTypeID(lLoanTypeID);
        } catch (Exception e)
        {
            throw new LoanException("Gen_E001", e);
        }

        return c;
    }
    
    
    
    /*
     * ���Ҵ���������
     */
    public Collection findSubLoanCodeByLoanTypeID(long[] lLoanTypeID,long officeid,long currencyid)throws LoanException
	{
		Collection c = null;
		LoanTypeSettingDao dao = new LoanTypeSettingDao();
		
		try
		{
		    c = dao.findSubLoanCodeByLoanTypeID(lLoanTypeID,officeid,currencyid);
		} catch (Exception e)
		{
		    throw new LoanException("Gen_E001", e);
		}
		
		return c;
	}    
    
//	2007.6.12 qhzhou
    public Collection findByLoanTypeID(long[] lLoanTypeID,long lOfficeID,long lCurrencyID)
    	throws LoanException
	{
	Collection c = null;
	LoanTypeSettingDao dao = new LoanTypeSettingDao();
	
	try
	{
	    c = dao.findByLoanTypeID(lLoanTypeID,lOfficeID,lCurrencyID);
	} catch (Exception e)
	{
	    throw new LoanException("Gen_E001", e);
	}
	
	return c;
	}
    /**
	 * @param ls
	 * @return
	 */
	public Collection findByLoanTypeIDTrust(long[] lLoanTypeID) throws LoanException{
		Collection c = null;
        LoanTypeSettingDao dao = new LoanTypeSettingDao();

        try
        {
            c = dao.findByLoanTypeIDTrust(lLoanTypeID);
        } catch (Exception e)
        {
            throw new LoanException("Gen_E001", e);
        }

        return c;
	}
	//2007.6.11 qhzhou
	public Collection findByLoanTypeIDTrust(long[] lLoanTypeID,long lOfficeID,long lCurrencyID) throws LoanException{
		Collection c = null;
        LoanTypeSettingDao dao = new LoanTypeSettingDao();

        try
        {
            c = dao.findByLoanTypeIDTrust(lLoanTypeID,lOfficeID,lCurrencyID);
        } catch (Exception e)
        {
            throw new LoanException("Gen_E001", e);
        }

        return c;
	}

	/**
	 * @param wt
	 * @return
	 */
	public Collection findByLoanTypeID2(long lLoanTypeID) throws LoanException {
		Collection c = null;
        LoanTypeSettingDao dao = new LoanTypeSettingDao();

        try
        {
            c = dao.findByLoanTypeID1(lLoanTypeID);
        } catch (Exception e)
        {
            throw new LoanException("Gen_E001", e);
        }

        return c;
	}
	//2007.6.11 qhzhou
	public Collection findByLoanTypeID2(long lLoanTypeID,long lOfficeID,long lCurrencyID) throws LoanException {
		Collection c = null;
        LoanTypeSettingDao dao = new LoanTypeSettingDao();

        try
        {
            c = dao.findByLoanTypeID1(lLoanTypeID,lOfficeID,lCurrencyID);
        } catch (Exception e)
        {
            throw new LoanException("Gen_E001", e);
        }

        return c;
	}
	
    //Ч���ܲ���ɾ��
    public String validateSubloanTypeId(LoanTypeSettingInfo qInfo) throws LoanException
    {
    	String lReturn = "";
        LoanTypeSettingDao dao = new LoanTypeSettingDao();
        
        try
        {
        	lReturn = dao.validateSubloanTypeId(qInfo);
        } catch (Exception e)
        {
            throw new LoanException("Gen_E001", e);
        }
        return lReturn;
    }
}
