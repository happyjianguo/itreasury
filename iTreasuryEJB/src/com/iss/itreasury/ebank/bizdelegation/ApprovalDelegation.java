/*
 * Created on 2005-5-26
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.ebank.bizdelegation;

import java.sql.Connection;
import java.util.Collection;
import javax.servlet.jsp.JspWriter;
import com.iss.itreasury.ebank.approval.bizlogic.ApprovalBiz;
import com.iss.itreasury.ebank.approval.dataentity.*;

/**
 * 01��findApprovalSetting 		��ѯ����������Ϣ
 * 02��saveApprovalTracing 		������������Ϣ
 * 03��findApprovalTracingInfo 	��ѯָ���������������Ϣ
 * 04��findApprovalTracing 		��ѯ��������Ϣ
 * 05��getLastCheckPerson 		������һ�����������
 * 06��deleteApprovalTracing 	ɾ����������Ϣ
 * 07��showApprovalUserList 		��ʾ��һ��������б�
 * 08��findApprovalUserLevel 	��ѯ�û���˼���
 * 09��getApprovalID 			������������ID
 * 10��findTheVeryUser 			��ѯ����������˵�ʵ���û�
 * 11��findTheVeryUser 			��ѯ����������˵�ʵ���û�
 */

/**
 * @author yfan
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class ApprovalDelegation
{
	ApprovalBiz bean ;
	/**
	 * 
	 */
	public ApprovalDelegation ( )
	{
		super ( ) ;
		// TODO Auto-generated constructor stub
		bean = new ApprovalBiz();
	}
	
	/**
	 * ��ѯ����������Ϣ
	 * ��ѯ����Ϊ�������ñ�ʾ
	 * �������ݿ��ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       lApprovalID    		   �������ñ�ʾ����ѯ������
	 * @return      ApprovalSettingInfo    ��������������Ϣ
	 */
	public ApprovalSettingInfo findApprovalSetting(long lApprovalID) throws Exception
	{		
		return bean.findApprovalSetting(lApprovalID);
	}
	
	/**
	 * ������������Ϣ
	 * �������ݿ��ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       ATInfo      ��������Ϣ
	 * @return      long        �ɹ�������ֵ=1��ʧ�ܣ�����ֵ=-1
	 */
	public long saveApprovalTracing(ApprovalTracingInfo ATInfo) throws Exception
	{
		return bean.saveApprovalTracing(ATInfo);
	}

	/**
	 * ��ѯָ���������������Ϣ
	 * �������ݿ��ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       lModuleID               ģ������ID
	 * @param       lLoanTypeID				������������ID
	 * @param       lActionID               ����ID
	 * @param       lOfficeID               ���´�ID
	 * @param       lCurrencyID             ����ID
	 * @param       lApprovalContentID      �������ID
	 * @param       lLevel               	��������
	 * @return      ApprovalTracingInfo     ������������Ϣ(ApprovalTracingInfo)
	 */
	public ApprovalTracingInfo findApprovalTracingInfo(long lModuleID, long lLoanTypeID, long lActionID, long lOfficeID, long lCurrencyID, long lApprovalContentID, long lLevel) throws Exception
	{
		return bean.findApprovalTracingInfo(lModuleID,lLoanTypeID,lActionID,lOfficeID,lCurrencyID,lApprovalContentID,lLevel);
	}

	/**
	 * ��ѯ��������Ϣ
	 * ������Ҫ����
	 * �������ݿ��ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       lModuleID               ģ������ID
	 * @param       lLoanTypeID				������������ID
	 * @param       lActionID               ����ID
	 * @param       lOfficeID               ���´�ID
	 * @param       lCurrencyID             ����ID
	 * @param       lApprovalContentID      �������ID
	 * @param       lDesc               	����ʽ
	 * @return      Collection              ������������Ϣ(ApprovalTracingInfo)
	 */
	public Collection findApprovalTracing(long lModuleID, long lLoanTypeID, long lActionID, long lOfficeID, long lCurrencyID, long lApprovalContentID, long lDesc) throws Exception
	{
		return bean.findApprovalTracing(lModuleID,lLoanTypeID,lActionID,lOfficeID,lCurrencyID,lApprovalContentID,lDesc);
	}

	/**
	 * ������һ�����������
	 * �������ݿ��ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       lModuleID               ģ������ID
	 * @param       lLoanTypeID				������������ID
	 * @param       lActionID               ����ID
	 * @param       lOfficeID               ���´�ID
	 * @param       lCurrencyID             ����ID
	 * @param       lApprovalContentID      ������ݱ�ʾ
	 * @return      String              	�������һ�����������
	 */
	public String getLastCheckPerson(long lModuleID, long lLoanTypeID, long lActionID, long lOfficeID, long lCurrencyID, long lApprovalContentID) throws Exception
	{
		return bean.getLastCheckPerson(lModuleID,lLoanTypeID,lActionID,lOfficeID,lCurrencyID,lApprovalContentID);
	}

	/**
	 * ɾ����������Ϣ
	 * ԭ����Ӧ�ö�ʹ���߼�ɾ��
	 * �������ݿ��ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       lModuleID               ģ������ID
	 * @param       lLoanTypeID				������������ID
	 * @param       lActionID               ����ID
	 * @param       lOfficeID               ���´�ID
	 * @param       lCurrencyID             ����ID
	 * @param       lApprovalContentID      ������ݱ�ʾ
	 * @param       lActionID               ��ʶ��1������ɾ����2���߼�ɾ��
	 * @return      long                    �ɹ�������ֵ=1��ʧ�ܣ�����ֵ=-1
	 */
	public long deleteApprovalTracing(long lModuleID, long lLoanTypeID, long lActionID, long lOfficeID, long lCurrencyID, long lApprovalContentID, long lTypeID) throws	Exception
	{
		return bean.deleteApprovalTracing(lModuleID,lLoanTypeID,lActionID,lOfficeID,lCurrencyID,lApprovalContentID,lTypeID);
	}

	/**
	 * ��ʾ��һ��������б�
	 * ֧��Խ������Լ���ȿ���
	 * �������ݿ��ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       out                     ���
	 * @param       strFieldName            �������
	 * @param       strNextFieldName        ��һ���������
	 * @param       lModuleID               ģ������ID
	 * @param       lLoanTypeID				������������ID
	 * @param       lActionID               ����ID
	 * @param       lOfficeID               ���´�ID
	 * @param       lCurrencyID             ����ID
	 * @param       lUserID                 ����û���ʾ
	 * @param       lLevel                  ��ǰ��˼���
	 * @param       bIsLowLevel             �Ƿ���������������
	 * @return      long                    ���һ����˷���1������������˷���0�����󷵻�-1
	 */
	public long showApprovalUserList(JspWriter out, String strFieldName,
									 String strNextFieldName, long lModuleID,
									 long lLoanTypeID, long lActionID,
									 long lOfficeID, long lCurrencyID,
									 long lUserID, long lLevel, 
									 boolean bIsLowLevel) throws Exception
	{
		return bean.showApprovalUserList(out,strFieldName,strNextFieldName,lModuleID,
				 lLoanTypeID,lActionID,lOfficeID,lCurrencyID,lUserID,lLevel,bIsLowLevel);
	}
	
	/**
	 * ��ѯ�û���˼���
	 * �������ݿ��ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       lApprovalID            �������ñ�ʾ
	 * @param       lUserID                �û���ʶ
	 * @return      long                   �����û���˼���
	 */
	public long findApprovalUserLevel(long lApprovalID, long lUserID) throws Exception
	{		
		return bean.findApprovalUserLevel(lApprovalID,lUserID);

	}

	/**
	 * ������������ID
	 * ֧��ͨ������������ӵ�����������Ϣ�����ʽ�ƻ�ģ��Ĳ�������
	 * �������ݿ��ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       lModuleID              ģ������ID
	 * @param       lLoanTypeID			   ������������ID
	 * @param       lActionID              ����ID
	 * @param       lOfficeID              ���´�ID
	 * @param       lCurrencyID            ����ID
	 * @return      lApprovalID            ��������������Ϣ
	 */
	public long getApprovalID(long lModuleID,long lLoanTypeID,long lActionID,long lOfficeID,long lCurrencyID) throws Exception
	{
		return bean.getApprovalID(lModuleID,lLoanTypeID,lActionID,lOfficeID,lCurrencyID);
	}

	/**
	 * ��ѯ����������˵�ʵ���û�
	 * �������ݿ��ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       lModuleID              ģ������ID
	 * @param       lLoanTypeID			   ������������ID
	 * @param       lActionID              ����ID
	 * @param       lOfficeID              ���´�ID
	 * @param       lCurrencyID            ����ID
	 * @param       lUserID                �û���ʶ
	 * @param       con                	   ���ݿ�����
	 * @return      String                 ���ؿ������Ա����(���ŷָֱ������SQL)
	 */
	public String findTheVeryUser(long lModuleID,long lLoanTypeID,long lActionID,long lOfficeID,long lCurrencyID,long lUserID,Connection con) throws Exception
	{
		return bean.findTheVeryUser(lModuleID,lLoanTypeID,lActionID,lOfficeID,lCurrencyID,lUserID,con);
	}
	
	/**
	 * ��ѯ����������˵�ʵ���û�
	 * �������ݿ��ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       lModuleID              ģ������ID
	 * @param       lLoanTypeID			   ������������ID
	 * @param       lActionID              ����ID
	 * @param       lOfficeID              ���´�ID
	 * @param       lCurrencyID            ����ID
	 * @param       lUserID                �û���ʶ
	 * @return      String                 ���ؿ������Ա����(���ŷָֱ������SQL)
	 */
	public String findTheVeryUser(long lModuleID,long lLoanTypeID,long lActionID,long lOfficeID,long lCurrencyID,long lUserID) throws Exception
	{
	    return bean.findTheVeryUser(lModuleID,lLoanTypeID,lActionID,lOfficeID,lCurrencyID,lUserID);
	}
}
