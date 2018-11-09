/*
 * Created on 2007-4-12
 *
 * Title:				iTreasury
 * @author             	刘琰 
 * Company:             iSoftStone
 * @version				iTreasury3.2新增
 * Description:         产品化3.2在结算,网银新增审批流,且审批流引擎采用北京研发中心的产品iNut,
 * 						审批流每审批一笔业务，在该表内新增一条记录，记录业务id与审批流实例id对应关系     
 */
package com.iss.itreasury.system.approval.bizlogic;

import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;

import com.iss.itreasury.system.approval.dao.InutApprovalRecordDao;
import com.iss.itreasury.system.approval.dataentity.InutApprovalRecordInfo;
import com.iss.itreasury.util.Database;


/**
 * @author yfan
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class InutApprovalRecordBiz implements java.io.Serializable
{
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
