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

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.loan.base.LoanException;
import com.iss.itreasury.loan.setting.dao.LoanTypeRelationDao;
import com.iss.itreasury.loan.setting.dataentity.LoanTypeRelationInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Log4j;

/**
 * @author yfan
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class LoanTypeRelationBiz extends Object implements java.io.Serializable
{
    private static Log4j log4j = null;

    /**
     * No argument constructor required by container.
     */
    public LoanTypeRelationBiz()
    {
        log4j = new Log4j(Constant.ModuleType.LOAN, this);
    }

    /**
     * 保存操作
     */
    public long save(LoanTypeRelationInfo info) throws LoanException
    {
        long lReturn = -1;
        LoanTypeRelationDao dao = new LoanTypeRelationDao();

        try
        {
            //重复
            if (dao.checkInsert(info) <= 0)
            {
                lReturn = -2;
            }
            else
            {
                /* 更新设置表 */
                lReturn = dao.save(info);
            }
        } catch (Exception e)
        {
            throw new LoanException("Gen_E001", e);
        }
        return lReturn;
    }

    /**
     * 删除操作
     */
    public long delete(LoanTypeRelationInfo info) throws LoanException
    {
        long lReturn = -1;
        LoanTypeRelationDao dao = new LoanTypeRelationDao();

        try
        {
            /* 删除设置表 */
            lReturn = dao.delete(info);
        } catch (Exception e)
        {
            throw new LoanException("Gen_E001", e);
        }
        return lReturn;
    }

    /**
     * 多笔查询操作
     */
    public Collection findByMultiOption(LoanTypeRelationInfo qInfo)
            throws LoanException
    {
        Collection c = null;
        LoanTypeRelationDao dao = new LoanTypeRelationDao();

        try
        {
            c = dao.findByMultiOption(qInfo);
        } catch (Exception e)
        {
            throw new LoanException("Gen_E001", e);
        }

        return c;
    }
    
	/*
	 * 取贷款类型关系设置中选择的大类
	 */
	public long[] getAllSetLoanTypeID(long officeID,long currencyID)
	{
		LoanTypeRelationDao dao = new LoanTypeRelationDao();

		return dao.getAllSetLoanTypeID(officeID,currencyID);
	}
	/*
	 * 取贷款类型关系设置中选择的zi类
	 */
	public long[] getAllSetSubLoanTypeID(long officeID,long currencyID,long[] loanTypeID)
	{
		LoanTypeRelationDao dao = new LoanTypeRelationDao();

		return dao.getAllSetSubLoanTypeID(officeID,currencyID,loanTypeID);
	}
	public long getLoanTypeBySubLoan(long subLoanid)throws Exception{
	    long loanTypeid=-1;
	    try{
	        LoanTypeRelationDao dao=new LoanTypeRelationDao();
	        loanTypeid=dao.getLoanTypeBySubLoan(subLoanid);
	        
	    }catch(Exception e){
	        e.printStackTrace();
	    }
	    
	    return loanTypeid;
	}
	
    //效验操作
    public String validateSubTypeId(LoanTypeRelationInfo info) throws LoanException
    {
    	String lReturn = "";
        LoanTypeRelationDao dao = new LoanTypeRelationDao();

        try
        {
            lReturn = dao.validateSubTypeId(info);
        } catch (Exception e)
        {
            throw new LoanException("Gen_E001", e);
        }
        return lReturn;
    }
}
