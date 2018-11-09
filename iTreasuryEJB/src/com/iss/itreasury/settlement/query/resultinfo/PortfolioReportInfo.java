/*
 * PortfolioReportInfo.java
 *
 * Created on 2002��8��15��, ����3:19
 */
package com.iss.itreasury.settlement.query.resultinfo;
import java.sql.Timestamp;
/**
 *
 * @author  xrzhang
 */
public class PortfolioReportInfo
{
	//m_lTotalType=1��ʾ������¼Ϊ�պϼƣ�m_lTotalType=2��ʾ������¼Ϊ�ܺϼƣ�m_lTotalType=3��ʾ������¼�ºϼ�
	public long m_lTotalType = 0;
	//���ڵ��ַ��������ܺϼƵ�ʱ��Ϊ'�ܺϼ�'����Լ�ϼƵ�ʱ��Ϊ'�ºϼ�'
	public String m_strDate = "";
	//����
	public Timestamp m_tsDate = null;
	//���д�������ָ���ڡ������飻�������п�Ŀ���͵���  ��212�� �Ľ��׽���������ۼ�
	//���Ϊҵ��������2�������������ֱ���
	public long m_lTransTrustDepositNumber = 0;
	//���д���������������п�Ŀ���͵���  ��112�� �Ľ��׽���������ۼ�
	//���Ϊҵ��������2�����������������ί���տ����
	public long m_lTransTrustLoanNumber = 0;
	//ί�д���������������п�Ŀ���͵���  ��116�� �Ľ��׽���������ۼ�
	//���Ϊҵ��������2����������ɷݹ�˾ί���տ����
	public long m_lTransConsignLoanNumber = 0;
	//���д��������������п�Ŀ���͵���  ��102�� �Ľ��׽���������ۼ�
	//���Ϊҵ��������2����������ί�������ʽ����
	public long m_lTransBankDepositNumber = 0;
	//���Ϊҵ��������2�����������ϴ��ʽ���ر���
	public long m_lTransUpXXXNumber = 0;
	//���Ͻ������
	//���Ϊҵ��������2�����������Ÿ�Ϣ�ʽ����
	public long m_lTransInternetSettlementNumber = 0;
	//���˱���
	//���Ϊҵ��������1������������˱���
	public long m_lAdjustAccountNumber = 0;
	//��������
	//���Ϊҵ��������2�����������̸�����
	public long m_lTransOtherNumber = 0;
	//�պϼƱ��� �����ܺϼƱ��� �����ºϼƱ���
	//���Ϊҵ��������2�����������պϼƱ��� �����ܺϼƱ��� �����ºϼƱ���
	public long m_lDayTotalNumber = 0;
	//�պϼƽ����� �����ܺϼƽ�� �����ºϼƽ��
	//���Ϊҵ��������2�����������պϼƽ����� �����ܺϼƽ�� �����ºϼƽ��
	public double m_dDayTotalAmount = 0;
	//�����ۼƽ��������
	//���Ϊҵ��������2�������������ۼƽ��������
	public double m_dYearTotalAmount = 0;
	//�����ۼ�ҵ��������
	//���Ϊҵ��������2�������������ۼ�ҵ��������
	public long m_lYearTotalNumber = 0;
	//�������н����� �������Ͻ����ܺϼƽ�� �������Ͻ����ºϼƽ��
	public double m_dInternetSettlementAmount = 0;
	//���Ϊҵ��������2�������������ֽ�����
	public double m_dDiscountAmount = 0;
	//���Ϊҵ��������2�����������������ί���տ������
	public double m_dGLJYConsignAmount = 0;
	//���Ϊҵ��������2����������ɷݹ�˾ί���տ������
	public double m_dGFGSConsignAmount = 0;
	//���Ϊҵ��������2����������ί�������ʽ������
	public double m_dUpReceiveAmount = 0;
	//���Ϊҵ��������2�����������Ÿ�Ϣ�ʽ������
	public double m_dUpXXXAmount = 0;
	//���Ϊҵ��������2�����������ϴ��ʽ���ؽ�����
	public double m_dUpSaveAmount = 0;
	//���Ϊҵ��������2�����������̸������--�������ƴ��� -����
	public double m_dShortDebtReturnAmount = 0;
	//���Ϊҵ��������1������������˽��
	public double m_dChangedAmount = 0;
}
