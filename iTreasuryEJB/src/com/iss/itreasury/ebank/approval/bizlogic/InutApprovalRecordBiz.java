package com.iss.itreasury.ebank.approval.bizlogic;

import java.util.Collection;
import java.util.Iterator;

import com.iss.itreasury.ebank.approval.dao.InutApprovalRecordDao;
import com.iss.itreasury.ebank.approval.dataentity.InutApprovalRecordInfo;

public class InutApprovalRecordBiz implements java.io.Serializable{


	/**
     * �������
     */
    public long save(InutApprovalRecordInfo info) throws Exception
    {
        long lReturn = -1;
        
        try
        {
        	InutApprovalRecordDao dao = new InutApprovalRecordDao();
            dao.setUseMaxID();
            
		    /* ������¼ */
		    dao.add(info);		        			
        } 
        catch (Exception e)
        {
            throw new Exception("Gen_E001", e);
        }
      
        return lReturn;
    }
    
    /**
     * ɾ������
     */
    public long delete(long lID) throws Exception
    {
        long lReturn = 1;
        InutApprovalRecordDao dao = new InutApprovalRecordDao();

        try
        {
            /* ɾ�����ñ� */
            dao.deletePhysically(lID);
        } catch (Exception e)
        {
        	lReturn = -1;
        	throw new Exception("Gen_E001", e);
        }
        
        return lReturn;
    }
    
    /**
     * ��������ʵ��id����ҵ����ϸ��Ϣ����������ʵ��id��Ψһ�ģ�
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
     * ����ҵ��id��ҵ�����Ͳ���ҵ����ϸ��Ϣ
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
	 * ͨ��ģ�����ͣ�ҵ�����ͣ��������͵���Ϣ���Ҷ�Ӧ������ʵ��
	 * �������ݿ��
	 * @param       info      	��Ϣ
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
     * ͨ������ʵ��id�޸ļ�¼����
     */
    public long updateByApprovalEntryID(InutApprovalRecordInfo qInfo) throws Exception
    {
        long lReturn = 1;
        InutApprovalRecordDao dao = new InutApprovalRecordDao();

        try
        {
            /* uodate���ñ� */
            dao.updateByApprovalEntryID(qInfo);
        } catch (Exception e)
        {
        	lReturn = -1;
        	throw new Exception("Gen_E001", e);
        }
        
        return lReturn;
    }

}
