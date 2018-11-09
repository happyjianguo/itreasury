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
package com.iss.itreasury.codingrule.bizlogic;

import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import com.iss.itreasury.codingrule.dao.Sys_CodingRuleRelationDao;
import com.iss.itreasury.codingrule.dao.Sys_SerialNumberDao;
import com.iss.itreasury.codingrule.dataentity.CodingRuleRelationInfo;
import com.iss.itreasury.codingrule.dataentity.SerialnumberInfo;
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
public class CodingRuleRelationBiz implements java.io.Serializable
{
	/**
     * 保存操作
     */
    public long batchSave(Collection infos) throws Exception
    {
        long lReturn = -1;
        Connection con = null;
        
        try
        {
        	/* 由于是批量操作,如果dao自维护connection,则每add或update一次,均需要获取一次connection
        	 * 将会消耗大量资源,所以此处的connection由biz维护,使所有数据库更新在一个connection中完成
        	 * */
        	con = Database.getConnection();
        	Sys_CodingRuleRelationDao dao = new Sys_CodingRuleRelationDao(con);
        	Sys_SerialNumberDao sDao = new Sys_SerialNumberDao(con);
            //InutApprovalRecordDao inutApprovalRecordDao = new InutApprovalRecordDao();
           // InutApprovalRecordInfo inutApprovalRecordInfo = null;
            dao.setUseMaxID();
            sDao.setUseMaxID();
        //    Collection v = new Vector();
        	if(infos != null)
        	{
        		Iterator it = infos.iterator();
        		while(it!=null && it.hasNext())
				{					
        			CodingRuleRelationInfo info = (CodingRuleRelationInfo)it.next();
        			
//        			inutApprovalRecordInfo = new InutApprovalRecordInfo();
//        			inutApprovalRecordInfo.setOfficeID(info.getOfficeID());
//        			inutApprovalRecordInfo.setCurrencyID(info.getCurrencyID());
//        			inutApprovalRecordInfo.setModuleID(info.getModuleID());
//        			inutApprovalRecordInfo.setActionID(info.getActionID());
//        			inutApprovalRecordInfo.setTransTypeID(info.getTransTypeID());
//        			//inutApprovalRecordInfo.setLastAppUserID(-1);
//        			
//        			v = inutApprovalRecordDao.queryNotApprovedByCondition(inutApprovalRecordInfo);
//        			if(v != null && v.size() > 0)
//        			{
//        				throw new IException("有业务未审批完成，不能重新关联新的审批流");
//        			}
        			
        			
        			
		        	if (info.getId() > 0)
		        	{		        			
		        		/* 更新设置表 */
		        		dao.update(info);
		        		lReturn = info.getId();
		        	}
		        	else
		        	{
		        		/* 新增设置表 */
		        		lReturn=dao.add(info);		
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
    	long lReturn = 1;
        Connection con = null;        

        try
        {
        	/* 由于是批量操作,如果dao自维护connection,则每add或update一次,均需要获取一次connection
        	 * 将会消耗大量资源,所以此处的connection由biz维护,使所有数据库更新在一个connection中完成
        	 * */
        	con = Database.getConnection();
        	Sys_CodingRuleRelationDao dao = new Sys_CodingRuleRelationDao(con);
            //dao.setUseMaxID();
        	/* 删除设置表 */
            for(int i=0;i<lID.length;i++)
            {
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
     * 根据条件查找已设置编码规则的操作类型
     */
    public Collection queryByCondition(CodingRuleRelationInfo qInfo) throws Exception
    {
        Collection c = null;
        Sys_CodingRuleRelationDao dao = new Sys_CodingRuleRelationDao();

        try
        {
            c = dao.queryByCondition(qInfo);
        } catch (Exception e)
        {
            throw new Exception("Gen_E001", e);
        }

        return c;
    }
   
}
