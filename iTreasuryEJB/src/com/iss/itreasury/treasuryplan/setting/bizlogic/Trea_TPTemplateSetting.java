package com.iss.itreasury.treasuryplan.setting.bizlogic;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.treasuryplan.etl.transform.dao.Trea_TPTemplateItemDAO;
import com.iss.itreasury.treasuryplan.setting.dao.Trea_TPTemplateDAO;
import com.iss.itreasury.treasuryplan.setting.dataentity.Trea_TPTemplateInfo;
import com.iss.itreasury.treasuryplan.setting.dataentity.Trea_TPTemplateItemInfo;
import com.iss.itreasury.treasuryplan.util.TPConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.ITreasuryException;
import com.iss.itreasury.util.Log4j;


/**
 *
 * @name: Trea_TPTemplateSetting
 * @description: �ʽ�ƻ�ģ��������
 * @author: jason
 * @create: 2005-11-15
 *
 */
public class Trea_TPTemplateSetting
{
    protected Log4j log4j = new Log4j(Constant.ModuleType.PLAN, this);
    
    /**
     *@Description:�ʽ�ƻ�ģ�����
     *     step 1 :find data from Table Trea_TPTemplate;
     *     step 2 :return a Collection.
     *@param Trea_TPTemplateInfo
     *@return Collection
     */
    public Collection queryTemplateByCondition( Trea_TPTemplateInfo paramInfo ) throws Exception
    {
        try 
        {
            Vector dataList   = new Vector();
            Vector tempList   = new Vector();
            Vector returnList = new Vector();
            
            Trea_TPTemplateDAO dao = new Trea_TPTemplateDAO(); 
            
            //��ѯ�ʽ�ƻ�ģ������
            dataList = dao.queryTemplateData( paramInfo );
             
            return dataList;
        }
        catch ( Exception e ) 
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw e;
        }
    }
    
    /**
     *@Description: �õ�ģ����Ŀ����󼶱�
     *@param Trea_TPTemplateInfo
     *@return maxLevel
     *@throws Exception
     */
    public long getMaxLevel( Trea_TPTemplateInfo paramInfo ) throws Exception
    {
        long maxLevel = -1;
        
        try
        {
            Trea_TPTemplateDAO dao = new Trea_TPTemplateDAO(); 
            maxLevel = dao.getMaxLevel(paramInfo);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw e;
        }
        
        
        return maxLevel;
    }
    
    /**
     *@Description:����Id,����һ������Ŀ
     *@param lineId
     *@return Trea_TPTemplateInfo
     *@throws Exception
     */
    public Trea_TPTemplateInfo findTemplateById( long lineId ) throws Exception
    {
        Trea_TPTemplateInfo trea_TPTemplateInfo = null;
        
        try
        {
            Trea_TPTemplateDAO dao = new Trea_TPTemplateDAO(); 
            trea_TPTemplateInfo = (Trea_TPTemplateInfo) dao.findByID( lineId , (new Trea_TPTemplateInfo()).getClass() );
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw e;
        }
        return trea_TPTemplateInfo;
    }
    
    
    /**
     *@Description:����Ŀȡ���߼����� - �������޸�
     *@param Trea_TPTemplateInfo
     *@return templateId
     *@throws IException
     */
    public long saveTemplateItem(Trea_TPTemplateItemInfo itmeInfo) throws Exception
    {
        long itemId = -1;
        
        try
        {
            log4j.info("����Ŀȡ���߼�����..............");
            //����DAO
            com.iss.itreasury.treasuryplan.setting.dao.Trea_TPTemplateItemDAO itemDao = new com.iss.itreasury.treasuryplan.setting.dao.Trea_TPTemplateItemDAO();
            
            
            if( itmeInfo != null )
            {
                if( itmeInfo.getId() > 0 )
                {
                    //�޸ı���
                    log4j.info("����Ŀȡ���߼�����  �޸ı���..............");
                    itemDao.updateItem(itmeInfo);
                }
                else
                {
                    //��������
                    log4j.info("����Ŀȡ���߼����� ��������..............");
                    itemDao.add(itmeInfo);
                }
            }
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception("��������Ŀȡ���߼��쳣��");
        }
            
        return itemId;
     }
    
    
    /**
     *@Description: ɾ���ʽ�ƻ�ģ������Ŀ��һ��ȡ���߼�
     *@param itemId
     *@return itemId
     *@throws Exception
     */
    public  void deleteTemplateItem( long itemId ) throws ITreasuryException,Exception
    {

        try 
        {
            //ʵ�������� - ����Ŀ
            com.iss.itreasury.treasuryplan.setting.dao.Trea_TPTemplateItemDAO  itemDao   = new com.iss.itreasury.treasuryplan.setting.dao.Trea_TPTemplateItemDAO();
            Trea_TPTemplateItemInfo itemInfo  = new Trea_TPTemplateItemInfo();
            
            //ɾ������Ŀ
            log4j.info("ɾ������Ŀ��һ��ȡ���߼�..............");
            if( itemId > 0 )
            {
                itemDao.deleteOneItem(itemId);
            }
        }
        catch ( Exception e ) 
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
            
    }
    
    
    /**
     *@Description:����ʽ�ƻ�ģ�������Ŀ - �Ƿ�������
     *@param Trea_TPTemplateInfo
     *@return boolean
     *               true : ���Խ��б��������
     *               false: ���������Ϣ�����ݿ����Ϣ��ͻ����������б��������
     *@throws Exception
     */
    private boolean isAllowSave(Trea_TPTemplateInfo info) throws Exception
    {
        boolean flag    = true;
        Collection coll = null;
        
        try
        {
           //�ж�������Ŀ����Ƿ��ظ�
            Trea_TPTemplateDAO  dao  = new Trea_TPTemplateDAO();
            Trea_TPTemplateInfo para = new Trea_TPTemplateInfo();
            
            para.setLineNo(info.getLineNo());
            para.setStatusId(Constant.RecordStatus.VALID);
            para.setOfficeId(info.getOfficeId());
            coll = dao.findByCondition(para);
            
            if( coll != null && coll.size() > 0)
            {
                log4j.info("�ظ�������Ŀ =================================== :  "+para.getLineNo());
                flag = false;
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new Exception(" ����ʽ�ƻ�ģ�������Ŀ - �Ƿ������� �쳣��");
        }
        
        return flag;
    }
    
    /**
     *@Description:����ʽ�ƻ�ģ�������Ŀ - �Ƿ�����ɾ��
     *             i.��������¼���Ŀ�����ϼ���Ŀ����ɾ��
     *            ii.���һ����ĿȨ���Ѿ������ĳ����ʹ�ã����ϼ���Ŀ����ɾ��
     *@param Trea_TPTemplateInfo
     *@return boolean
     *@throws Exception
     */
    private boolean isAllowDelete(Trea_TPTemplateInfo info) throws Exception
    {
        boolean flag = true;
        
        try
        {
            Trea_TPTemplateDAO dao = new Trea_TPTemplateDAO();
            
            //�ж��Ƿ����¼���Ŀ
            if( dao.isHaveSubItem(info) )
            {
                log4j.info("���¼�����Ŀ..............");
                flag = false;
                return false;
            }
            
            //�ж���Ŀ�Ƿ�����ĳ����ʹ��
            if( info.getAuthorizedDepartment() != null || info.getAuthorizedUser() != null  )
            {
                log4j.info("��Ŀ�Ѿ������ĳ����ʹ��..............");
                flag = false;
                return false;
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new Exception(" ����ʽ�ƻ�ģ�������Ŀ - �Ƿ�����ɾ�� �쳣��");
        }
        
        return flag;
    }
    
    /**
     *@Description: ɾ���ʽ�ƻ�ģ���һ������Ŀ
     *              ɾ����Ŀ��ͬʱ������Ŀ��ȡ���߼�Ҳͬʱɾ��
     *@param lineId
     *@return lineId
     *@throws Exception
     */
    public  void deleteTemplateLine( long lineId ) throws ITreasuryException,Exception
    {
        //ʵ�������� - ����Ŀ
        Trea_TPTemplateDAO  dao           = new Trea_TPTemplateDAO();
        Trea_TPTemplateInfo info          = new Trea_TPTemplateInfo();
        
        //ʵ�������� - ����Ŀ
        Trea_TPTemplateItemDAO  itemDao   = new Trea_TPTemplateItemDAO();
        Trea_TPTemplateItemInfo itemInfo  = new Trea_TPTemplateItemInfo();
        
        //�õ���Ҫ��ɾ��������Ŀ��Ϣ
        if( lineId > 0 )
        {
            log4j.info("�õ���Ҫ��ɾ��������Ŀ��Ϣ..............");
            info = (Trea_TPTemplateInfo)dao.findByID( lineId , (new Trea_TPTemplateInfo()).getClass() );
        }
        
        //�ж��Ƿ�����ɾ���ʽ�ƻ�ģ�������Ŀ
        if( info != null && isAllowDelete(info) )
        {
            
            try 
            {
                //ɾ������Ŀ
                log4j.info("ɾ������Ŀ..............");
                dao.updateStatus(info.getId(),Constant.RecordStatus.INVALID);
                
                
                //ɾ������Ŀ��ȡ���߼�
                log4j.info("����������Ŀ��Ӧ������Ŀ.............. lineId = "+lineId);
                Collection coll = itemDao.findAllByLineID(lineId);
                
                if( coll != null && coll.size() > 0 )
                {
                    
                    Iterator iterator = coll.iterator();
                    
                    com.iss.itreasury.treasuryplan.setting.dao.Trea_TPTemplateItemDAO  deleteDao   = null;
                    com.iss.itreasury.treasuryplan.etl.transform.dataentity.TPTemplateItemInfo   forDelInfo = null;
                    
                    while( iterator.hasNext() )
                    {
                        forDelInfo = (com.iss.itreasury.treasuryplan.etl.transform.dataentity.TPTemplateItemInfo )iterator.next();
                        deleteDao = new com.iss.itreasury.treasuryplan.setting.dao.Trea_TPTemplateItemDAO();
                       
                        log4j.info("ɾ������Ŀ��ȡ���߼�(����ɾ��)..............");
                        
                        deleteDao.deleteOneItem(forDelInfo.getId());
                    }
                }
            }
            catch ( Exception e ) 
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
        }
        else
        {
            throw new ITreasuryException("������Ŀ�����¼�����Ŀ�����Ѿ������ĳ����ʹ�ã�������ɾ����","",null);
        }
    }
    
    
    /**
     *@Description:�ʽ�ƻ�ģ�������Ŀ���� - �������޸�����Ŀ
     *@param Trea_TPTemplateInfo
     *@return templateId
     *@throws IException
     */
    public long saveTemplateLine(Trea_TPTemplateInfo info) throws Exception
    {
        long templateId = -1;
            
        Trea_TPTemplateDAO dao = new Trea_TPTemplateDAO();
            
        if( info.getId() > 0 )
        {
            //�޸ı���
        	//ȡ���߼��Ľ���־������Ŀ����Ŀ�����йأ����������Ŀ�����ͱ��޸ĵ�ʱ����Ҫ�ж��Ƿ���Ҫ����ȡ���߼����޸�
        	//�漰�������������Ҫ�ֹ���������
        	Connection transConn = null;
            try 
            {
        		try
				{
            		transConn =Database.getConnection();
            		transConn.setAutoCommit(false);
                }
        		catch (Exception e)
				{
                   throw new ITreasuryDAOException("���ݿ��ʹ���쳣����", e);
                }
                Trea_TPTemplateDAO tpDao = new Trea_TPTemplateDAO(transConn);
                com.iss.itreasury.treasuryplan.setting.dao.Trea_TPTemplateItemDAO tpItemDao = new com.iss.itreasury.treasuryplan.setting.dao.Trea_TPTemplateItemDAO(transConn);
				tpDao.update(info);
				if (info.getMaintenanceFlag() == TPConstant.LineType.Balance_Begin)  
				{
					tpItemDao.updateItemByMaintenanceFlag(info.getId(), TPConstant.AmountFlagForSetting.Yesterday_Balance);
				}
				if (info.getMaintenanceFlag() == TPConstant.LineType.Balance_End)
				{
					tpItemDao.updateItemByMaintenanceFlag(info.getId(), TPConstant.AmountFlagForSetting.Today_Balance);
				}
				try
				{
	                if (transConn != null)
	                {
	                    transConn.commit();
						transConn.close();
	                    transConn = null;
	                }
	            }
				catch (SQLException e)
				{
	                e.printStackTrace();
	                throw new ITreasuryDAOException("���ݿ�ر��쳣����", e);
	            }
	        }
            catch (Exception e)
			{
	            try
				{
	                if (transConn != null)
	                {
	                    transConn.rollback();
						transConn.close();
	                    transConn = null;
	                }
	            }
	            catch (Exception es)
				{
	                es.printStackTrace();
	            }
	        }
            finally
			{
	            try
				{
	                if (transConn != null)
	                {
	                    transConn.close();
	                    transConn = null;
	                }
	            }
	            catch (Exception e)
				{
	                e.printStackTrace();
	            }
	        }
        }
        else
        {
            //��������
            if( isAllowSave(info) )//�������Ŀ�Ƿ��ظ�
            {
                try 
                {
                    dao.add(info);
                }
                catch ( ITreasuryDAOException e ) 
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            else
            {
                throw new ITreasuryException("�ظ�����,����Ŀ: "+info.getLineNo()+" �Ѿ�����!","",null);
            }
        }
            
        return templateId;
     }

    
}