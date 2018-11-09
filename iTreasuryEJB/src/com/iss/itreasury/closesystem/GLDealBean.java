/*
 * Created on 2004-2-11
 * 
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.iss.itreasury.closesystem;

import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.Collection;

import com.iss.itreasury.closesystem.settlement.SettGLWithinDao;
import com.iss.itreasury.glinterface.genersoft.GLGenerSoftBean;
import com.iss.itreasury.glinterface.u850.GLU850Bean;
import com.iss.itreasury.system.setting.dataentity.GlSettingInfo;
import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.ConfigConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.IException;

/**
 * @author yychen
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class GLDealBean
{
    /**
     *  
     */
    public GLDealBean()
    {
        super();
        // Auto-generated constructor stub
    }

    public static void main(String[] args)
    {
        try
        {
            /*����Ŀ*/
        	//addSubject(1,1,1);
            /*����Ŀ���*/
            //addSubjectBalance(1, 1, 1, DataFormat.getDateTime("2004-11-17"), DataFormat.getDateTime("2004-11-17"));
            	/*����Ŀ���ڵ���Ŀ����ڲ�����ã�*/
            	//addSubjectAmount(1, 1, 1, DataFormat.getDateTime("2005-01-31"), DataFormat.getDateTime("2005-01-31"));
            /*����ƾ֤*/
        	//postGLVoucher(1,1,1,DataFormat.getDateTime("2006-03-14"),DataFormat.getDateTime("2006-03-14"));
            /*�����ⲿ�ͻ�*/
        	//addExternalAccount(1,1,1);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /*
     * @author yychen ? postGLVoucherBean.postGLVoucher()���������ƾ֤ ��������
     * ����ֵ��boolean bIsSuccess:�Ƿ�ɹ��� ������������ҵ��ϵͳ���ɵĻ��ƾ֤���������˺���ϵͳ��
     * ��������(�ڴ��Ե�����U850Ϊ��)�� l �ȵ��ò�ѯfindGLVoucherBaseBean.findGLVoucherByCondition
     * ()���õ���Ҫ������ƾ֤���ݼ��ϣ����ݼ����е���������ΪVoucherInfo,��GLVoucherInfo�ɾ���Ļ�Ʒ�¼GlEntryInfo���ɣ�
     * l ��ƾ֤���ݼ�����Ϊ����������U850GLBean.postGlVoucher()������ƾ֤�� l
     * ����U850GLBean.getPostGlVoucherBackInfo ()���õ�����ϵͳ���ص���Ϣ�� l
     * ���ݷ��ص���Ϣ������postGLVoucherBean.updatePostStatus()���޸�ÿ�ʻ�Ʒ�¼�ĵ���״̬�� l
     * ���ݷ��ص���Ϣ,�жϻ�Ʒ�¼���Ƿ�ȫ���ɹ�������������ڻ�Ʒ�¼����ʧ�ܣ��򷵻�ʧ�ܣ����򷵻سɹ���
     */
    //ִ�е����ڲ�������ݵ�һϵ�в���
    public static boolean postGLVoucher(long lOfficeID, long lCurrencyID, long lModelID, Timestamp tsStart, Timestamp tsEnd) throws Exception
    {
        boolean bIsPassed = true;
        try
        {
        	//���ҵ����Ľӿ�����
        	GlSettingInfo glSettingInfo=new GlSettingInfo();
			glSettingInfo = GLU850Bean.getGlSettingInfo(lOfficeID,lCurrencyID);
			
			if(glSettingInfo.getId()==-1)  //���û���ҵ�
			{
				System.out.println("====�õ���IDֵ��=====:"+glSettingInfo.getId());
				System.out.println("����������Ʒ�¼����!");
				throw new IException("û���ҵ������Ľӿ�����,����������Ʒ�¼����!");
			}
			else
			{
				if(glSettingInfo.getGlName().equals("U850")){
					if(glSettingInfo.getGlOperationType() == Constant.GLOperationType.NoOperator || glSettingInfo.getGlOperationType() == Constant.GLOperationType.ImportOnly){
						throw new IException("���˽ӿ����ô���");
					}
				}
				
        		System.out.println("*********findGLVoucherByCondition start(������Ҫ������ƾ֤,��ʼ!)******=");
                Collection collVoucher = BeanFactory.getGLWithinBean(lModelID).findGLVoucherByCondition(lOfficeID, lCurrencyID, lModelID, tsStart, tsEnd);
                System.out.println("*********findGLVoucherByCondition start(������Ҫ������ƾ֤,����!)******=");

                if (bIsPassed && collVoucher != null)
                {
                    System.out.println("*********postGLVoucher start(��ʼ�����ݵ������ⲿ�Ľӿ�)******�õ�������������:="+collVoucher.size());
                    collVoucher=BeanFactory.getGLExtendBean(lOfficeID,lCurrencyID).postGLVoucher(collVoucher,lOfficeID,lCurrencyID,tsStart);
                    System.out.println("*********postGLVoucher end(����ƾ֤���ݽ���)******=");
                   
                    System.out.println("*********updatePostStatus start(�����ڲ�������,sett_glentry��Ĵ���״̬��ʼ)******=");
                    bIsPassed = BeanFactory.getGLWithinBean(lModelID).updatePostStatus(collVoucher);
                    System.out.println("*********updatePostStatus end(�����ڲ�������,sett_glentry��Ĵ���״̬����)******=");                   
                }else{
                	System.out.println("***********û����ʵ�ʵĵ�������!");
                	System.out.println("**********"+bIsPassed);
                	System.out.println("**********"+collVoucher);
                }
			}    
        }
        catch(IException ie){
            bIsPassed = false;
            ie.printStackTrace();
            throw new IException(ie.getMessage());
        }
        catch(Exception e){
            bIsPassed = false;
            e.printStackTrace();
            throw e;
        }
        return bIsPassed;
    }
    /**
     * �޸Ļ�Ʒ�¼״̬,�˳�ר��
     * @author xrli     
     * To change the template for this generated type comment go to
     * Window - Preferences - Java - Code Generation - Code and Comments
     */
    public static boolean updateStatus(long lOfficeID, long lCurrencyID, long lModelID) throws Exception
	{
    	boolean bIsPassed = true;
       
        try
        {
        	GlSettingInfo glSettingInfo=new GlSettingInfo();
        	glSettingInfo=GLU850Bean.getGlSettingInfo(lOfficeID,lCurrencyID);
        	if(!glSettingInfo.getGlName().equals("ISOFTSTONE"))
        	{
       			System.out.println("-----------------�����ڲ�����"+glSettingInfo.getGlName());
        		
            //if (!Env.RUN_ENVIRONMENT.equals("isoftstone")) //˵�������ڲ�
            //{
       			//�˳�
            	if(glSettingInfo.getGlName().equals("GENERSOFT"))
            	{
            		GLGenerSoftBean glBean = new GLGenerSoftBean(); 
            		//����˳����Ƿ��Ѿ��ɹ�����
            		bIsPassed = glBean.checkPostVoucher(lOfficeID,lCurrencyID);
            	}

            	
            	if(bIsPassed)
            	{
            		System.out.println("ͨ���˶������м��ĳ���!!");
            		bIsPassed = BeanFactory.getGLWithinBean(lModelID).updatePostStatus(lOfficeID,lCurrencyID);
            	}
            	else
            		System.out.println("*********û��ͨ���˶������м��ĳ���!!");
            }
        }
        catch (Exception e)
        {
            bIsPassed = false;
            throw new RemoteException(e.getMessage());
        }
        return bIsPassed;
    	
    }

    /**
     * �����Ŀ��ͨ�ýӿ�
     */
    public static boolean addSubject(long lOfficeID, long lCurrencyID, long lModelID) throws Exception
    {
        boolean bIsPassed = true;
        Collection collSubject = null;
        try
        {
        	GlSettingInfo glSettingInfo=new GlSettingInfo();
        	glSettingInfo=GLU850Bean.getGlSettingInfo(lOfficeID,lCurrencyID);
        	if(!glSettingInfo.getGlName().equals("ISOFTSTONE"))
        	{
        		System.out.println("*********����Ŀ!!");
                collSubject = BeanFactory.getGLExtendBean(lOfficeID,lCurrencyID).getGLSubject(lOfficeID, lCurrencyID);
                
                if(collSubject!=null && collSubject.size()!=0)
                {
                	bIsPassed = BeanFactory.getGLWithinBean(Constant.ModuleType.SETTLEMENT).addSubject(lOfficeID, lCurrencyID, collSubject);
                	
                	//���»���˿�Ŀʱ��鲢���¿�Ŀ���¼��ؼ�
                	String strConvert = Config.getProperty(ConfigConstant.SETTLEMENT_GLSUBJECTDEFINITION_CONVERT,"0");
                	SettGLWithinDao withinDao = new SettGLWithinDao();
                	withinDao.convertGlSubject(lOfficeID, lCurrencyID, strConvert);
                }
                else
                {
                	return false;
                }
        		System.out.println("*********����Ŀ�ɹ�!!");
            }
        }
        catch (Exception e)
        {
            bIsPassed = false;
            throw new RemoteException(e.getMessage());
        }
        return bIsPassed;
    }
    
    /**
     * ������������д�Ĵ洢���̻�����п�Ŀ�ĸ��ڵ�ID
     */
    public static boolean addParentsubjectidToSubject(long lOfficeID, long lCurrencyID) throws Exception
    {
        boolean bIsPassed = true;
        try
        {
        	//���»���˿�Ŀʱ��鲢���¿�Ŀ���¼��ؼ�
        	String strConvert = Config.getProperty(ConfigConstant.SETTLEMENT_GLSUBJECTDEFINITION_CONVERT,"0");
        	SettGLWithinDao withinDao = new SettGLWithinDao();
        	withinDao.convertGlSubject(lOfficeID, lCurrencyID, strConvert);
        	System.out.println("*********������п�Ŀ�ĸ��ڵ�ID�ɹ�!!");
        }
        catch (Exception e)
        {
            bIsPassed = false;
            throw new RemoteException(e.getMessage());
        }
        return bIsPassed;
    }
    
    /**
     * �����Ŀ��ͨ�ýӿ�
     */
    public static boolean addSubjectBalance(long lOfficeID, long lCurrencyID, long lModelID, Timestamp tsStartDate, Timestamp tsEndDate) throws Exception
    {
        boolean bIsPassed = true;
        Collection collBalance = null;
        try
        {
        	GlSettingInfo glSettingInfo=new GlSettingInfo();
        	glSettingInfo=GLU850Bean.getGlSettingInfo(lOfficeID,lCurrencyID);
        	if(!glSettingInfo.getGlName().equals("ISOFTSTONE"))
        	{
        	    //˵�������ڲ�
                for (Timestamp tsDealingDate = tsStartDate; bIsPassed && DataFormat.getDateString(tsDealingDate).compareTo(DataFormat.getDateString(tsEndDate)) <= 0; tsDealingDate = DataFormat.getNextDate(tsDealingDate))
                {
                    collBalance = BeanFactory.getGLExtendBean(lOfficeID,lCurrencyID).getGLSubjectBalance(lOfficeID, lCurrencyID, tsDealingDate);
                    
                    System.out.println("********* ����Ŀ��� ******");
                    bIsPassed = BeanFactory.getGLWithinBean(Constant.ModuleType.SETTLEMENT).addSubjectBalance(lOfficeID, lCurrencyID, tsDealingDate, collBalance);
                    System.out.println("********* ����Ŀ���ɹ� ******");
                }
            }
            /**
             * modify by shuangniu 2011-01-19
             */
            //addSubjectAmount(lOfficeID, lCurrencyID, lModelID, tsStartDate, tsEndDate);
        }
        catch (Exception e)
        {
            bIsPassed = false;
            e.printStackTrace();
            throw e;
        }
        return bIsPassed;
    }

    public static boolean addSubjectAmount(long lOfficeID, long lCurrencyID, long lModelID, Timestamp tsStartDate, Timestamp tsEndDate) throws Exception
    {
        boolean bIsPassed = true;
        Collection collBalance = null;
        try
        {
        	GlSettingInfo glSettingInfo=new GlSettingInfo();
        	glSettingInfo=GLU850Bean.getGlSettingInfo(lOfficeID,lCurrencyID);
        	if(!glSettingInfo.getGlName().equals("ISOFTSTONE"))
        	{
            //if (!Env.RUN_ENVIRONMENT.equals("ISOFTSTONE")) //˵�������ڲ�
            //{
                for (Timestamp tsDealingDate = tsStartDate; bIsPassed && DataFormat.getDateString(tsDealingDate).compareTo(DataFormat.getDateString(tsEndDate)) <= 0; tsDealingDate = DataFormat.getNextDate(tsDealingDate))
                {
                    collBalance = BeanFactory.getGLExtendBean(lOfficeID,lCurrencyID).getGLSubjectAmount(lOfficeID, lCurrencyID, lModelID, tsDealingDate);
                    System.out.println("********* ����Ŀ������ ******" + collBalance);
                    bIsPassed = BeanFactory.getGLWithinBean(Constant.ModuleType.SETTLEMENT).addSubjectAmount(lOfficeID, lCurrencyID, tsDealingDate, collBalance);
                    System.out.println("********* ����Ŀ�������ɹ� ******" + collBalance);
                }
            }
        }
        catch (Exception e)
        {
            bIsPassed = false;
            throw new RemoteException(e.getMessage());
        }
        return bIsPassed;
    }

    /**
     * ˵���������ⲿ�ͻ�
     * @author liuqing
     * @param lOfficeID
     * @param lCurrencyID
     * @param lModelID
     * @param tsStartDate
     * @param tsEndDate
     * @return
     * @throws Exception
     */
    public static boolean addExternalAccount(long lOfficeID, long lCurrencyID, long lModelID) throws Exception
    {
        boolean bIsPassed = true;
        Collection collExternalAccount = null;
        try
        {
        	GlSettingInfo glSettingInfo=new GlSettingInfo();
        	glSettingInfo=GLU850Bean.getGlSettingInfo(lOfficeID,lCurrencyID);
        	if(!glSettingInfo.getGlName().equals("ISOFTSTONE"))
        	{
            //if (!Env.RUN_ENVIRONMENT.equals("ISOFTSTONE")) //˵�������ڲ�
            
        		if(glSettingInfo.getGlName().equals("KINGDEE")) //����ӿ� 
        		{
        			
        			collExternalAccount = BeanFactory.getGLExtendBean(lOfficeID,lCurrencyID).getGLExternalAccount(lOfficeID, lCurrencyID);
                    System.out.println("*********addExternalAccount******" + collExternalAccount);
                    bIsPassed = BeanFactory.getGLWithinBean(Constant.ModuleType.SETTLEMENT).addExternalAccount(lOfficeID, lCurrencyID, collExternalAccount);
                    
        		}
        		
            }
        }
        catch (Exception e)
        {
            bIsPassed = false;
            throw new RemoteException(e.getMessage());
        }
        return bIsPassed;
    }
    
}