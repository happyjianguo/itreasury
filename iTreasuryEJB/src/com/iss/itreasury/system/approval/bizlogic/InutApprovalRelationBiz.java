/*
 * Created on 2007-4-12
 *
 * Title:				iTreasury
 * @author             	���� 
 * Company:             iSoftStone
 * @version				iTreasury3.2����
 * Description:         ��Ʒ��3.2�ڽ���,��������������,��������������ñ����з����ĵĲ�ƷiNut,
 * 						Ϊ��iNut��������ҵ�����,�����������ù���        
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
     * ������� ,�������ֵС��0����ӳɹ���������ֵ����0ʱ�������в��������������ʧ��
     */
    public long batchSave(Collection infos) throws Exception
    {
        long lReturn = 0;
        Connection con = null;
        
        try
        {
        	/* ��������������,���dao��ά��connection,��ÿadd��updateһ��,����Ҫ��ȡһ��connection
        	 * �������Ĵ�����Դ,���Դ˴���connection��bizά��,ʹ�������ݿ������һ��connection�����
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
        				//throw new IException("��ҵ��δ������ɣ��������¹����µ�������");
        				//������δ���ʱ���������¹���������
        				lReturn ++;
        				continue;
        			}
        			
        			
        			
		        	if (info.getId() > 0)
		        	{		        			
		        		/* �������ñ� */
		        		dao.update(info);
		        	}
		        	else
		        	{
		        		/* �������ñ� */
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
     * ����ɾ������
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
        	/* ��������������,���dao��ά��connection,��ÿadd��updateһ��,����Ҫ��ȡһ��connection
        	 * �������Ĵ�����Դ,���Դ˴���connection��bizά��,ʹ�������ݿ������һ��connection�����
        	 * */
        	con = Database.getConnection();
            InutApprovalRelationDao dao = new InutApprovalRelationDao(con);
            InutApprovalRelationInfo info = null;
            //dao.setUseMaxID();
        	/* ɾ�����ñ� */
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
    				//throw new IException("��ҵ��δ������ɣ�����ȡ������������");
    				//������δ���ʱ������ȡ������������
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
     * ɾ������
     */
    public long delete(long lID) throws Exception
    {
        long lReturn = 1;
        InutApprovalRelationDao dao = new InutApprovalRelationDao();

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
     * �������������������������Ĳ�������
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
     * ���������������������������˻���������
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
	 * ͨ��ģ�����ͣ�ҵ�����ͣ��������͵���Ϣ���Ҷ�Ӧ��������ID
	 * �������ݿ��
	 * @param       info      	��Ϣ
	 * @return      long        �ɹ�������������IDֵ��ʧ�ܣ�����ֵ=-1
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
