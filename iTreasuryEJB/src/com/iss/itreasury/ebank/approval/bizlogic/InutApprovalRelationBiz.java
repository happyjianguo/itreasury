/*
 * Created on 2007-4-16
 *
 * Title:				iTreasury
 * @author             	ypxu
 * Company:             iSoftStone
 * @version				iTreasury3.2新增
 * Description:         产品化3.2在结算,网银新增审批流,且审批流引擎采用北京研发中心的产品iNut,
 * 						为将iNut审批流与业务关联,新增关联设置功能        
 */
package com.iss.itreasury.ebank.approval.bizlogic;

import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import com.iss.itreasury.ebank.approval.dao.InutApprovalRecordDao;
import com.iss.itreasury.ebank.approval.dao.InutApprovalRelationDao;
import com.iss.itreasury.ebank.approval.dataentity.InutApprovalRecordInfo;
import com.iss.itreasury.ebank.approval.dataentity.InutApprovalRelationInfo;
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
            InutApprovalRelationDao dao = new InutApprovalRelationDao(con);
            InutApprovalRecordDao inutApprovalRecordDao = new InutApprovalRecordDao();
            InutApprovalRecordInfo inutApprovalRecordInfo = null;
            Collection v = new Vector();
            
            dao.setUseMaxID();
            
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
        			inutApprovalRecordInfo.setClientID(info.getClientID());
        			
        			v = inutApprovalRecordDao.queryNotApprovedByCondition(inutApprovalRecordInfo);
        			if(v != null && v.size() > 0)
        			{
        				throw new IException("有本单位或审批下属单位业务未审批完成，不能重新关联新的审批流");
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
     * 删除操作
     */
    //Modify by leiyang date 2007/08/06
    public long delete(long lID) throws Exception
    {
        long lReturn = 1;
        InutApprovalRecordDao inutApprovalRecordDao = new InutApprovalRecordDao();
        InutApprovalRelationDao inutApprovalRelationDao = new InutApprovalRelationDao();
        InutApprovalRelationInfo inutApprovalRelationInfo = null;
        InutApprovalRecordInfo inutApprovalRecordInfo = null;
        Collection v = new Vector();
        String error="";
        try
        {
        	inutApprovalRelationInfo = inutApprovalRelationDao.getApprovalRelationInfoById(lID);
        	if(inutApprovalRelationInfo != null){
	        	inutApprovalRecordInfo = new InutApprovalRecordInfo();
				inutApprovalRecordInfo.setOfficeID(inutApprovalRelationInfo.getOfficeID());
				inutApprovalRecordInfo.setCurrencyID(inutApprovalRelationInfo.getCurrencyID());
				inutApprovalRecordInfo.setModuleID(inutApprovalRelationInfo.getModuleID());
				inutApprovalRecordInfo.setActionID(inutApprovalRelationInfo.getActionID());
				inutApprovalRecordInfo.setTransTypeID(inutApprovalRelationInfo.getTransTypeID());
				inutApprovalRecordInfo.setClientID(inutApprovalRelationInfo.getClientID());
				v = inutApprovalRecordDao.queryNotApprovedByCondition(inutApprovalRecordInfo);
				
				if(v != null && v.size() > 0)
				{
					error="有本单位或审批下属单位业务未审批完成，不能取消关联审批流";
					throw new IException("有本单位或审批下属单位业务未审批完成，不能取消关联审批流");
				}
				
	            /* 删除设置表 */
				inutApprovalRelationDao.deletePhysically(lID);
        	}
        	else {
        		error="系统异常，不能取消关联审批流";
        		throw new IException("系统异常，不能取消关联审批流");
        	}
        }
        catch (Exception e)
        {
        	lReturn = -1;
        	throw new IException(error);
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
    
    /**
     * 根据条件查找已设置审批流的操作类型(删除用)
     */
    public Collection queryByConditions(InutApprovalRelationInfo qInfo) throws Exception
    {
        Collection c = null;
        InutApprovalRelationDao dao = new InutApprovalRelationDao();

        try
        {
            c = dao.queryByConditions(qInfo);
        } catch (Exception e)
        {
            throw new Exception("Gen_E001", e);
        }

        return c;
    }
    
	/*
	 * add by ypxu
	 * 2007-04-21
	 * 通过approvalID得到INFO
	 */
	public String findByApprovalID(long lApprovalID) throws IException
	{
	        
		InutApprovalRelationDao dao = new InutApprovalRelationDao();	 
		String approvalName = "";
	        try
	        {
	        	approvalName = dao.findNameByApprovalID(lApprovalID);
	        } catch (Exception e)
	        {
	        	e.printStackTrace();
				throw new IException("Gen_E001",e);
	        }

	        return approvalName;
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
    
    /*
	 * add by ypxu
	 * 2007-04-21
	 * 通过approvalID得到INFO
	 */
	public String findNameByApprovalID(long lApprovalID) throws IException
	{
		InutApprovalRelationDao dao = new InutApprovalRelationDao(); 
		String strApprovalName = "";
		
		try
		{
			strApprovalName = dao.findNameByApprovalID(lApprovalID);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001",e);
		}
		return strApprovalName;
	}
}
