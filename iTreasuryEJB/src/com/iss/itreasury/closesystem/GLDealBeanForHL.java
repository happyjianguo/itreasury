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

import javax.servlet.http.HttpServletResponse;

import com.iss.itreasury.glinterface.u850.GLU850BeanForHL;
import com.iss.itreasury.system.setting.dataentity.GlSettingInfo;

/**
 * @author yychen
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class GLDealBeanForHL
{
    /**
     *  
     */
    public GLDealBeanForHL()
    {
        super();
        // Auto-generated constructor stub
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
    public static String postGLVoucher(HttpServletResponse response,long lOfficeID, long lCurrencyID, long lModelID, Timestamp tsStart, Timestamp tsEnd) throws Exception
    {
        boolean bIsPassed = true;
        String returnData = null;
        try
        {
        	//���ҵ����Ľӿ�����
        	GlSettingInfo glSettingInfo=new GlSettingInfo();
			glSettingInfo=GLU850BeanForHL.getGlSettingInfo(lOfficeID,lCurrencyID,1);
			
			if(glSettingInfo.getId()==-1)  //���û���ҵ�
			{
				System.out.println("====�õ���IDֵ��=====:"+glSettingInfo.getId());
				System.out.println("����������Ʒ�¼����!");
			}
			else{
				
			
        		System.out.println("*********findGLVoucherByCondition start(������Ҫ������ƾ֤,��ʼ!)******=");
                Collection collVoucher = BeanFactory.getGLWithinBean(lModelID).findGLVoucherByCondition(lOfficeID, lCurrencyID, lModelID, tsStart, tsEnd);
                System.out.println("*********findGLVoucherByCondition start(������Ҫ������ƾ֤,����!)******=");

                if (bIsPassed && collVoucher != null)
                {
                	GLU850BeanForHL glu850Beanforhl = new GLU850BeanForHL();
                	returnData = glu850Beanforhl.postGLVoucher(response,collVoucher, lOfficeID, lCurrencyID, tsStart, tsEnd);
                              
                }else{
                	System.out.println("***********û����ʵ�ʵĵ�������!");
                	System.out.println("**********"+collVoucher);
                }
                	

			}    
        }
        catch (Exception e)
        {
            bIsPassed = false;
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }
        return returnData;
    }  
}