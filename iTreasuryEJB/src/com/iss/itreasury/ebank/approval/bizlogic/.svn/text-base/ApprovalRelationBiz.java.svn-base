/*
 * Created on 2005-5-12
 *
 * Title:				iTreasury
 * @author             	yfan 
 * Company:             iSoftStone
 * Copyright:           Copyright (c) 2003
 * @version
 * Description:         
 */
package com.iss.itreasury.ebank.approval.bizlogic;

import java.util.Collection;
import java.util.Iterator;

import com.iss.itreasury.loan.base.LoanException;
import com.iss.itreasury.ebank.approval.dao.ApprovalRelationDao;
import com.iss.itreasury.ebank.approval.dataentity.ApprovalRelationInfo;


/**
 * @author yfan
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ApprovalRelationBiz implements java.io.Serializable
{
	/**
     * 保存操作
     */
    public long save(Collection infos) throws LoanException
    {
        long lReturn = -1;
        ApprovalRelationDao dao = new ApprovalRelationDao();
        
        try
        {
        	if(infos != null)
        	{
        		Iterator it = infos.iterator();
        		while(it!=null && it.hasNext())
				{					
					ApprovalRelationInfo info = (ApprovalRelationInfo)it.next();																
		        	if (info.getId() > 0)
		        	{		        			
		        		/* 更新设置表 */
		        		dao.update(info);
		        	}
		        	else
		        	{
		        		/* 新增设置表 */
		        		dao.save(info);		        			
		        	}
				}					
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
    public long delete(ApprovalRelationInfo info) throws LoanException
    {
        long lReturn = -1;
        ApprovalRelationDao dao = new ApprovalRelationDao();

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
     * 查找贷款子类型下已设置审批流的操作类型
     */
    public Collection findByMultiOption(ApprovalRelationInfo qInfo) throws LoanException
    {
        Collection c = null;
        ApprovalRelationDao dao = new ApprovalRelationDao();

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
	 * 通过模块类型，贷款类型，操作类型等信息查找对应的审批流ID
	 * 操作数据库表
	 * @param       info      	信息
	 * @return      long        成功，返回审批流ID值；失败，返回值=-1
	 */
    public long findApprovalID(ApprovalRelationInfo qInfo) throws LoanException
    {
    	long lApprovalID = -1;
        ApprovalRelationDao dao = new ApprovalRelationDao();
        
        try
        {
        	lApprovalID = dao.findApprovalID(qInfo);
        } catch (Exception e)
        {
            throw new LoanException("Gen_E001", e);
        }

        return lApprovalID;
    }
}
