/*
 * Created on 2004-11-29
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
import com.iss.itreasury.loan.setting.dao.LiborInterestRateDao;
import com.iss.itreasury.loan.setting.dataentity.LiborInterestRateInfo;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Log4j;

/**
 * @author yfan
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class LiborInterestRateBiz extends Object implements
        java.io.Serializable
{
    private static Log4j log4j = null;

    /**
     * No argument constructor required by container.
     */
    public LiborInterestRateBiz()
    {
        log4j = new Log4j(Constant.ModuleType.LOAN, this);
    }

    /**
     * Libor利率设置的保存操作
     */
    public long save(LiborInterestRateInfo info) throws LoanException
    {
        long lReturn = -1;
        LiborInterestRateDao dao = null;

        dao = new LiborInterestRateDao();

        try
        {
            /* 更新Libor利率设置表 */
            if (info.getId() <= 0)
            {
                //Libor利率编号重复
                if (dao.checkLiborCode(info.getCode(),info.getCurrencyID()) <= 0)
                {
                    lReturn = -2;
                }
                //Libor利率名称重复
                else if (dao.checkLiborName(info.getLiborName(),info.getCurrencyID()) <= 0)
                {
                    lReturn = -3;
                } else
                {
                    /* 更新Libor利率设置表 */
                    dao.setUseMaxID();
                    lReturn = dao.add(info);
                } 
            } else if (info.getId() > 0)
            {
//              Libor利率名称重复
                if (dao.checkLiborName(info) <= 0)
                {
                    lReturn = -3;
                }
                /* 更新Libor利率设置表 */
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
     * Libor利率设置的取消操作
     */
    public void cancel(long lID) throws LoanException
    {
        LiborInterestRateDao dao = null;
        dao = new LiborInterestRateDao();
        LiborInterestRateInfo info = new LiborInterestRateInfo();

        try
        {
            info.setId(lID);
            info.setStatusID(LOANConstant.RecordStatus.INVALID);
            dao.update(info);
        } catch (Exception e)
        {
            throw new LoanException("Gen_E001", e);
        }
    }

    /**
     * Libor利率设置的单笔查询操作
     */
    public LiborInterestRateInfo findByID(long lID) throws LoanException
    {
        LiborInterestRateInfo returnInfo = new LiborInterestRateInfo();
        LiborInterestRateDao dao = null;

        dao = new LiborInterestRateDao();

        try
        {
            returnInfo = (LiborInterestRateInfo) dao.findByID(lID, returnInfo
                    .getClass());
        } catch (Exception e)
        {
            throw new LoanException("Gen_E001", e);
        }

        return returnInfo;
    }

    /**
     * Libor利率设置的多笔查询操作
     */
    public Collection findByMultiOption(LiborInterestRateInfo qInfo)
            throws LoanException
    {
        Collection c = null;
        LiborInterestRateDao dao = null;

        dao = new LiborInterestRateDao();

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
     * Libor利率设置自动生成编号操作
     * @param lCurrencyID
     * @return
     * @throws LoanException
     */
    public String findMaxInterestRateCode(long lCurrencyID,long lOfficeID)
            throws  LoanException
    {
        
        String s = null;
        LiborInterestRateDao dao = null;
        dao = new LiborInterestRateDao();
        try
        {
            s = dao.findMaxInterestRateCode(lCurrencyID, lOfficeID);
            
        } catch (Exception e)
        {
            throw new LoanException("Gen_E001", e);
        }
       
        
        return s;
    }

}