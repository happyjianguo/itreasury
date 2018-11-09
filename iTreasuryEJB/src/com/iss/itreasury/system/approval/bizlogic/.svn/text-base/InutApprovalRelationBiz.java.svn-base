/*
 * Created on 2007-4-12
 *
 * Title:				iTreasury
 * @author             	刘琰 
 * Company:             iSoftStone
 * @version				iTreasury3.2新增
 * Description:         产品化3.2在结算,网银新增审批流,且审批流引擎采用北京研发中心的产品iNut,
 * 						为将iNut审批流与业务关联,新增关联设置功能        
 */
package com.iss.itreasury.system.approval.bizlogic;

import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import com.iss.itreasury.system.approval.dao.InutApprovalRecordDao;
import com.iss.itreasury.system.approval.dao.InutApprovalRelationDao;
import com.iss.itreasury.system.approval.dataentity.InutApprovalRecordInfo;
import com.iss.itreasury.system.approval.dataentity.InutApprovalRelationInfo;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IException;


/**
 * @author yfan 
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class InutApprovalRelationBiz implements java.io.Serializable
{
	/**
     * 保存操作 ,如果返回值小于0则添加成功，当返回值大于0时，表明有部分审批流会关联失败
     */
    public long batchSave(Collection infos) throws Exception
    {
        long lReturn = 0;
        Connection con = null;
        
        try
        {
        	/* 由于是批量操作,如果dao自维护connection,则每add或update一次,均需要获取一次connection
        	 * 将会消耗大量资源,所以此处的connection由biz维护,使所有数据库更新在一个connection中完成
        	 * */
        	con = Database.getConnection();
            InutApprovalRelationDao dao = new InutApprovalRelationDao(con);
            InutApprovalRecordDao inutApprovalRecordDao = new InutApprovalRecordDao();
            InutApprovalRecordInfo inutApprovalRecordInfo = null;
            dao.setUseMaxID();
            Collection v = new Vector();
        	if(infos != null)
        	{
        		Iterator it = infos.iterator();
        		while(it!=null && it.hasNext())
				{					
        			InutApprovalRelationInfo info = (InutApprovalRelationInfo)it.next();
        			
        			inutApprovalRecordInfo = new InutApprovalRecordInfo();
        			inutApprovalRecordInfo.setOfficeID(info.getOfficeID());
        			inutApprovalRecordInfo.setCurrencyID(info.getCurrencyID());
        			inutApprovalRecordInfo.setModuleID(info.getModuleID());
        			inutApprovalRecordInfo.setActionID(info.getActionID());
        			inutApprovalRecordInfo.setTransTypeID(info.getTransTypeID());
        			//inutApprovalRecordInfo.setLastAppUserID(-1);
        			
        			v = inutApprovalRecordDao.queryNotApprovedByCondition(inutApprovalRecordInfo);
        			if(v != null && v.size() > 0)
        			{
        				//throw new IException("有业务未审批完成，不能重新关联新的审批流");
        				//当审批未完成时，不再重新关联审批流
        				lReturn ++;
        				continue;
        			}
        			
        			
        			
		        	if (info.getId() > 0)
		        	{		        			
		        		/* 更新设置表 */
		        		dao.update(info);
		        	}
		        	else
		        	{
		        		/* 新增设置表 */
		        		dao.add(info);		        			
		        	}
				}					
			}
        } 
        catch (IException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            throw new Exception("Gen_E001", e);
        }
        finally
        {
        	if(con != null)
        	{
        		con.close();
        		con = null;
        	}	
        }       
        return lReturn;
    }

    /**
     * 批量删除操作
     */
    public long batchDelete(long[] lID) throws Exception
    {
    	long lReturn = 0;
        Connection con = null;        
        InutApprovalRecordInfo inutApprovalRecordInfo = null;
        InutApprovalRecordDao inutApprovalRecordDao = new InutApprovalRecordDao();
        Collection v = new Vector();
        try
        {
        	/* 由于是批量操作,如果dao自维护connection,则每add或update一次,均需要获取一次connection
        	 * 将会消耗大量资源,所以此处的connection由biz维护,使所有数据库更新在一个connection中完成
        	 * */
        	con = Database.getConnection();
            InutApprovalRelationDao dao = new InutApprovalRelationDao(con);
            InutApprovalRelationInfo info = null;
            //dao.setUseMaxID();
        	/* 删除设置表 */
            for(int i=0;i<lID.length;i++)
            {
            	info = new InutApprovalRelationInfo();
            	info = (InutApprovalRelationInfo)dao.findByID(lID[i], info.getClass());
            	
            	inutApprovalRecordInfo = new InutApprovalRecordInfo();           	
            	inutApprovalRecordInfo.setOfficeID(info.getOfficeID());
    			inutApprovalRecordInfo.setCurrencyID(info.getCurrencyID());
    			inutApprovalRecordInfo.setModuleID(info.getModuleID());
    			inutApprovalRecordInfo.setActionID(info.getActionID());
    			inutApprovalRecordInfo.setTransTypeID(info.getTransTypeID());
    			
    			v = inutApprovalRecordDao.queryNotApprovedByCondition(inutApprovalRecordInfo);
    			
    			if(v != null && v.size() > 0)
    			{
    				//throw new IException("有业务未审批完成，不能取消关联审批流");
    				//当审批未完成时，不再取消关联审批流
    				lReturn ++;
    				continue;
    			}
    			
            	dao.deletePhysically(lID[i]);
            }
        } 
        catch (Exception e)
        {
        	lReturn = -1;
        	throw new Exception("Gen_E001", e);
        }
        finally
        {
        	if(con != null)
        	{
        		con.close();
        		con = null;
        	}	
        } 
        
        return lReturn;
    }    
    
    /**
     * 删除操作
     */
    public long delete(long lID) throws Exception
    {
        long lReturn = 1;
        InutApprovalRelationDao dao = new InutApprovalRelationDao();

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
     * 根据条件查找已设置审批流的操作类型
     */
    public Collection queryByCondition(InutApprovalRelationInfo qInfo) throws Exception
    {
        Collection c = null;
        InutApprovalRelationDao dao = new InutApprovalRelationDao();

        try
        {
            c = dao.queryByCondition(qInfo);
        } catch (Exception e)
        {
            throw new Exception("Gen_E001", e);
        }

        return c;
    }
    
    public Collection queryMyworkByCondition(InutApprovalRelationInfo qInfo) throws Exception
    {
        Collection c = null;
        InutApprovalRelationDao dao = new InutApprovalRelationDao();

        try
        {
            c = dao.queryMyworkByCondition(qInfo);
        } catch (Exception e)
        {
            throw new Exception("Gen_E001", e);
        }

        return c;
    }
    
    /**
     * 根据条件查找已设置审批流的账户操作类型
     */
    public Collection queryByAccountCondition(InutApprovalRelationInfo qInfo) throws Exception
    {
        Collection c = null;
        InutApprovalRelationDao dao = new InutApprovalRelationDao();

        try
        {
            c = dao.queryByAccountCondition(qInfo);
        } catch (Exception e)
        {
            throw new Exception("Gen_E001", e);
        }

        return c;
    }
    
    /**
	 * 通过模块类型，业务类型，操作类型等信息查找对应的审批流ID
	 * 操作数据库表
	 * @param       info      	信息
	 * @return      long        成功，返回审批流ID值；失败，返回值=-1
	 */
    public long findApprovalID(InutApprovalRelationInfo qInfo) throws Exception
    {
    	long lApprovalID = -1;
    	InutApprovalRelationDao dao = new InutApprovalRelationDao();    
        
        try
        {
        	lApprovalID = dao.findApprovalID(qInfo);
        } catch (Exception e)
        {
            throw new Exception("Gen_E001", e);
        }

        return lApprovalID;
    }
}
