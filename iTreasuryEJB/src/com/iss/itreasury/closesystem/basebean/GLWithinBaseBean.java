/*
 * Created on 2004-2-11
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.iss.itreasury.closesystem.basebean;
import java.sql.Timestamp;
import java.util.Collection;
/**
 * @author yychen
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class GLWithinBaseBean
{
	/**
	 * 
	 */
	public GLWithinBaseBean()
	{
		super();
		// TODO Auto-generated constructor stub
	}
	public static void main(String[] args)
	{
	}
	/*
					 * @author yychen
	?	FindGLVoucherBaseBean.findGLVoucherByCondition ():��ѯ���ƾ֤
	������long lOfficeID:���´���
	long lCurrencyID:���֣�
	Timestamp tsStartDate:ִ���գ�
	Timestamp tsEndDate:ִ���գ�
	����ֵ��Collection collVoucher:ƾ֤���ϣ�
	��������������������ҵ��ϵͳ�в�ѯ����Ļ��ƾ֤��
	����������
	l	�������ӣ���ʼ����
	l	����������ѯ������Ľ��ױ�ţ�
	l	�ֱ���ݽ��ױ�Ų�ѯ���ý��׵Ļ�Ʒ�¼��
	l	�ر����ӣ��ύ����
	*/
	public Collection findGLVoucherByCondition(long lOfficeID, long lCurrencyID, long lModelID, Timestamp tsStart, Timestamp tsEnd) throws Exception
	{
		return null;
	}
	/*
						 * @author yychen
						������Collection collVoucher:������EAI������Ϣ���õ�����Ϣ���ϣ�
		����ֵ��boolean bIsSuccess:�Ƿ�ɹ���
		�����������޸�ҵ��ϵͳ�л�Ʒ�¼��״̬��
		����������
		l	�������ӣ���ʼ�޸Ļ�Ʒ�¼״̬����
		l	���ݲ������޸�ÿ�ʻ�Ʒ�¼��״̬�����ݿ��Ʒ�¼��Sett_GLEntry��nPostGLStatusID�ֶΣ���
		l	�ύ�޸Ļ�Ʒ�¼״̬���񣬷����Ƿ�ɹ���
		*/
	public boolean updatePostStatus(Collection collVoucher) throws Exception
	{
		return true;
	}
	public boolean updatePostStatus(long lOfficeID, long lCurrencyID) throws Exception
	{
		return true;
	}
	public boolean checkPostVoucher(long lOfficeID, long lCurrencyID, long lModelID, Timestamp tsStart, Timestamp tsEnd) throws Exception
	{
		return true;
	}
	public boolean addSubject(long lOfficeID, long lCurrencyID, Collection collVoucher) throws Exception
	{
		return true;
	}
	public boolean addSubjectBalance(long lOfficeID, long lCurrencyID, Timestamp tsDate, Collection collVoucher) throws Exception
	{
		return true;
	}
	public boolean addSubjectAmount(long lOfficeID, long lCurrencyID, Timestamp tsDate, Collection collVoucher) throws Exception
	{
		return true;
	}
    public boolean addExternalAccount(long lOfficeID, long lCurrencyID, Collection collExternalAccount) throws Exception
    {
    	return true;
    }
    //add by xuteng
    public Collection getVouches(String strExecuteDate,long lOfficeID, long lCurrencyID)
    {
    	return null;
    }
}
