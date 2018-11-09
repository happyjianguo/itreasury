/*
 * Created on 2007-4-16
 *
 * Title:				iTreasury
 * @author             	ypxu
 * Company:             iSoftStone
 * @version				iTreasury3.2����
 * Description:         ��Ʒ��3.2�ڽ���,��������������,��������������ñ����з����ĵĲ�ƷiNut,
 * 						Ϊ��iNut��������ҵ�����,�����������ù���        
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
     * �������
     */
    public long batchSave(Collection infos) throws Exception
    {
        long lReturn = -1;
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
        				throw new IException("�б���λ������������λҵ��δ������ɣ��������¹����µ�������");
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
     * ɾ������
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
					error="�б���λ������������λҵ��δ������ɣ�����ȡ������������";
					throw new IException("�б���λ������������λҵ��δ������ɣ�����ȡ������������");
				}
				
	            /* ɾ�����ñ� */
				inutApprovalRelationDao.deletePhysically(lID);
        	}
        	else {
        		error="ϵͳ�쳣������ȡ������������";
        		throw new IException("ϵͳ�쳣������ȡ������������");
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
    
    /**
     * �������������������������Ĳ�������(ɾ����)
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
	 * ͨ��approvalID�õ�INFO
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
    
    /*
	 * add by ypxu
	 * 2007-04-21
	 * ͨ��approvalID�õ�INFO
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
