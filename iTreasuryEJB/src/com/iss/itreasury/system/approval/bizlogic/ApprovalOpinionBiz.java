/*
 * Created on 2007-4-12
 *
 * Title:				iTreasury
 * @author             	���� 
 * Company:             iSoftStone
 * @version				iTreasury3.2����
 * Description:         ��Ʒ��3.2�ڽ���,��������������,��������������ñ����з����ĵĲ�ƷiNut,
 * 						��Ϊ�ù�����ӵ�ͳһ�ı�׼����������ù���        
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
     * �������
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
		        /* �������ñ� */
		        dao.update(info);
		    }
		    else
		    {
		    	info.setCode(this.getMaxCode(info.getModuleID()));
		    	/* �������ñ� */
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
     * ɾ������
     */
    public long delete(long lID) throws Exception
    {
        long lReturn = 1;
        ApprovalOpinionDao dao = new ApprovalOpinionDao();

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
	 * ͨ��ģ�����ͣ�ҵ�����ͣ��������͵���Ϣ���Ҷ�Ӧ��������ID
	 * �������ݿ��
	 * @param       info      	��Ϣ
	 * @return      long        �ɹ�������������IDֵ��ʧ�ܣ�����ֵ=-1
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
	 * �˷�������ģ���ID���������������
	 * ���ʱ��:2007-04-24
	 * �����:����
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
            throw new Exception("��ѯ����������ʧ�ܣ�����", e);
        }

        return maxCode;
    }
    
}
























