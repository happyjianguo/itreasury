package com.iss.itreasury.settlement.query.resultinfo;
import java.sql.Timestamp;
/**
 * �˴���������˵����
 * �������ڣ�(2002-1-15 17:20:10)
 * @author��
 */
public  class AccountTransactionInfo implements java.io.Serializable//��Ŀ������Ϣ
{
	public Timestamp m_dtInterestStart=null;//��Ϣ���� //added by mzh_fu 2007-3-12 �������Ϣ�մ�����ʾ����ִ�����ڵ�����
	
    public Timestamp m_tsExecute=null;//ִ������
    public long m_lTransactionNoID=-1;//���ױ�ʶ
    public String m_strTransNo="";//���׺�
    public long m_lTransactionTypeID=-1;//�������ͱ�ʶ
    public String m_strTransactionType="";//��������
    public String m_strAccountRecord="";//��Ŀ��
    public double m_dAmount=0;//���
    public long m_lDirectionID=-1;//�����ʶ
    public String m_strDirection="";//�������
    public String m_strOtherAccountRecord="";//�Է���Ŀ��
    public long m_lStatusID=-1;//״̬
    public String m_strStatus="";//״̬����
    public String m_strAbstract="";//ժҪ
    public String m_strInputUser="";//¼����
    public String m_strCheckUser="";//������ 
    public long m_lPageCount=0;//ҳ��
    //
    public double m_dTotalAmount = 0.0;
    public long m_lTotalRecordes = 0;
    //�������д����ϸ��ѯ
    public String m_strName = "";
    public String m_strName2 = "";
    public long   m_lRecord = -1;//�к�
    public double m_dDRAmount=0;//�跽���
    public double m_dCRAmount=0;//�������
    public String m_strCurrencyCode="";//��������
    public String m_strSource = "";//��Դ
    public String m_strDescription = "";
    public String m_strAccountDescription = "";//�˻�����
    
}
