package com.iss.itreasury.ebank.approval.bizlogic;

import java.util.Collection;
import java.util.Iterator;

import com.iss.itreasury.ebank.approval.dao.InutApprovalRecordDao;
import com.iss.itreasury.ebank.approval.dataentity.InutApprovalRecordInfo;

public class InutApprovalRecordBiz implements java.io.Serializable{


	/**
     * 保存操作
     */
    public long save(InutApprovalRecordInfo info) throws Exception
    {
        long lReturn = -1;
        
        try
        {
        	InutApprovalRecordDao dao = new InutApprovalRecordDao();
            dao.setUseMaxID();
            
		    /* 新增记录 */
		    dao.add(info);		        			
        } 
        catch (Exception e)
        {
            throw new Exception("Gen_E001", e);
        }
      
        return lReturn;
    }
    
    /**
     * 删除操作
     */
    public long delete(long lID) throws Exception
    {
        long lReturn = 1;
        InutApprovalRecordDao dao = new InutApprovalRecordDao();

        try
        {
            /* 删除设置表 */
            dao.deletePhysically(lID);
        } catch (Exception e)
        {
        	lReturn = -1;
        	throw new Exception("Gen_E001", e);
        }
        
        return lReturn;
    }
    
    /**
     * 根据审批实例id查找业务详细信息（表中审批实例id是唯一的）
     */
    public InutApprovalRecordInfo findByInstanceID(long lApprovalEntryID) throws Exception
    {
    	InutApprovalRecordInfo info = new InutApprovalRecordInfo();
        InutApprovalRecordDao dao = new InutApprovalRecordDao();
        Collection c = null;

        try
        {
            info.setApprovalEntryID(lApprovalEntryID);
        	c = dao.queryByCondition(info);
        	if(c!=null)
        	{
        		Iterator it = c.iterator();
        		if(it.hasNext())
        		{
        			info = (InutApprovalRecordInfo)it.next();
        		}	
        	}	
        } catch (Exception e)
        {
            throw new Exception("Gen_E001", e);
        }

        return info;
    }
    
    /**
     * 根据业务id和业务类型查找业务详细信息
     */
    public InutApprovalRecordInfo findByTransID(String lTransID,long lTransType ,long status) throws Exception
    {
    	InutApprovalRecordInfo info = new InutApprovalRecordInfo();
        InutApprovalRecordDao dao = new InutApprovalRecordDao();
        Collection c = null;

        try
        {
            info.setTransID(lTransID);
            info.setTransTypeID(lTransType);
            info.setStatusID(status);
        	c = dao.queryByCondition(info);
        	if(c!=null)
        	{
        		Iterator it = c.iterator();
        		if(it.hasNext())
        		{
        			info = (InutApprovalRecordInfo)it.next();
        		}	
        	}	
        } catch (Exception e)
        {
            throw new Exception("Gen_E001", e);
        }

        return info;
    }
    
    
    /**
	 * 通过模块类型，业务类型，操作类型等信息查找对应的审批实例
	 * 操作数据库表
	 * @param       info      	信息
	 * @return      Collection  
	 */
    public Collection queryByCondition(InutApprovalRecordInfo qInfo) throws Exception
    {
    	InutApprovalRecordDao dao = new InutApprovalRecordDao(); 
    	Collection c = null;
        
        try
        {
        	c = dao.queryByCondition(qInfo);
        } 
        catch (Exception e)
        {
            throw new Exception("Gen_E001", e);
        }

        return c;
    }
    
    /**
     * 通过审批实例id修改记录操作
     */
    public long updateByApprovalEntryID(InutApprovalRecordInfo qInfo) throws Exception
    {
        long lReturn = 1;
        InutApprovalRecordDao dao = new InutApprovalRecordDao();

        try
        {
            /* uodate设置表 */
            dao.updateByApprovalEntryID(qInfo);
        } catch (Exception e)
        {
        	lReturn = -1;
        	throw new Exception("Gen_E001", e);
        }
        
        return lReturn;
    }

}
