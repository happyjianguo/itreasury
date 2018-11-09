/*
 * Created on 2007-4-12
 *
 * Title:				iTreasury
 * @author             	刘琰 
 * Company:             iSoftStone
 * @version				iTreasury3.2新增
 * Description:         产品化3.2在结算,网银新增审批流,且审批流引擎采用北京研发中心的产品iNut,
 * 						此为该功能添加的统一的标准审批意见设置功能        
 */
package com.iss.itreasury.system.approval.bizlogic;

import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;

import com.iss.itreasury.system.approval.dao.ApprovalOpinionDao;
import com.iss.itreasury.system.approval.dataentity.ApprovalOpinionInfo;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IException;


/**
 * @author yfan
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ApprovalOpinionBiz implements java.io.Serializable
{
	/**
     * 保存操作
     */
    public long save(ApprovalOpinionInfo info) throws Exception
    {
        long lReturn = -1;
        Connection con = null;
        
        try
        {
        	con = Database.getConnection();
        	ApprovalOpinionDao dao = new ApprovalOpinionDao(con);       	
            dao.setUseMaxID();
															
		    if (info.getId() > 0)
		    {		        			
		        /* 更新设置表 */
		        dao.update(info);
		    }
		    else
		    {
		    	info.setCode(this.getMaxCode(info.getModuleID()));
		    	/* 新增设置表 */
		        dao.add(info);		        			
		    }
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
     * 删除操作
     */
    public long delete(long lID) throws Exception
    {
        long lReturn = 1;
        ApprovalOpinionDao dao = new ApprovalOpinionDao();

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
    public Collection queryByCondition(ApprovalOpinionInfo qInfo) throws Exception
    {
        Collection c = null;
        ApprovalOpinionDao dao = new ApprovalOpinionDao();

        try
        {
            c = dao.queryByCondition(qInfo);
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
    public long findApprovalID(ApprovalOpinionInfo qInfo) throws Exception
    {
    	long lApprovalID = -1;
    	ApprovalOpinionDao dao = new ApprovalOpinionDao();    
        
        try
        {
        	lApprovalID = dao.findApprovalID(qInfo);
        } catch (Exception e)
        {
            throw new Exception("Gen_E001", e);
        }

        return lApprovalID;
    }
    
    public ApprovalOpinionInfo findByID(long lID) throws Exception
    {
    	ApprovalOpinionDao dao = new ApprovalOpinionDao();
    	ApprovalOpinionInfo returnInfo = new ApprovalOpinionInfo();
        
        try
        {
        	returnInfo = (ApprovalOpinionInfo)dao.findByID(lID, ApprovalOpinionInfo.class);
        } catch (Exception e)
        {
            throw new Exception("Gen_E001", e);
        }

        return returnInfo;
    }
    
	/**
	 * 此方法根据模块的ID返回最大的审批编号
	 * 添加时间:2007-04-24
	 * 添加人:梁浩
	 * @param moudleID
	 * @return int
	 * @throws Exception 
	 * @throws IException 
	 */
    public String getMaxCode(long moduleID) throws Exception
    {
    	String maxCode = null;
        ApprovalOpinionDao dao = new ApprovalOpinionDao();
       
        try
        {
        	maxCode = dao.getStringMaxcode(moduleID);
        } catch (Exception e)
        {
            throw new Exception("查询最大审批编号失败！！！", e);
        }

        return maxCode;
    }
    
}
























