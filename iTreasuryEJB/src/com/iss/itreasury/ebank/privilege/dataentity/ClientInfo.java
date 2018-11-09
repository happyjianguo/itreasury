package com.iss.itreasury.ebank.privilege.dataentity;

/**
 * �˴���������˵����
 * �������ڣ�(2002-1-15 12:40:49)
 * @author��Administrator
 */

import java.sql.Timestamp;

public class ClientInfo implements java.io.Serializable
{
    /**
     * ClientInfo ������ע�⡣
     */
    public ClientInfo()
    {
        super();
    }

    //ID��ʶ
    public long m_lID;

    //���´���ʶ
    public long m_lOfficeID;

    //���´�����
    public String m_strOfficeName;

    //�ͻ����
    public String m_strCode;

    //�ͻ�����
    public String m_strName;

    //���˴����Ӫҵִ��
    public String m_strChiefRepCode;

    //��ҵ���� �����С������С�������
    public long m_lCorpNatureID;

    //��ҵ���� ���ܲ���������������ۡ�������
    public long m_lCorpIndustryID;

    //����˾��ʶ
    public long m_lParentCorpID;

    //����˾����
    public String m_strParentCorpName;

    //�ʼ�
    public String m_strEmail;

    //��ַ
    public String m_strAddress;

    //�ʱ�
    public String m_strZipCode;

    //�绰
    public String m_strPhone;

    //����
    public String m_strFax;

    //ӡ����ʼ����
    public Timestamp m_dtSignStart;

    //�ͻ���ǰӡ����ʶ
    public long m_lCurrentSignID;

    //¼����ID
    public long m_lInputUserID;

    //¼��������
    public String m_strInputUserName;

    //¼��ʱ��
    public Timestamp m_dtInput;

    //�����û�ID
    public long m_lUpdateUserID;

    //�����û�
    public String m_strUpdateUserName;

    //����ʱ��
    public Timestamp m_dtUpdate;

    //�Ƿ���Ч
    public long m_lStatusID;

    //�Ƿ��ǹɶ�
    public long m_lParentID;

    //���´��˻�
    public String m_strOfficeAccount;

    //��������1
    public String m_strBank1;

    //��������2
    public String m_strBank2;

    //��������3
    public String m_strBank3;

    //ʡ
    public String m_strProvince;

    //��
    public String m_strCity;

    //��ַ1
    public String m_strAddress1;

    //��ַ2
    public String m_strAddress2;

    //��ַ3
    public String m_strAddress3;

    //�����˻�1
    public String m_strBankAccount1;

    //�����˻�2
    public String m_strBankAccount2;

    //�����˻�3
    public String m_strBankAccount3;

    //��������
    public String m_strEconomyType;

    //���õȼ�
    public long m_lCreditLevelID;

    //��ϵ��
    public String m_strContacter;

    //���ܲ���
    public String m_strParentDepartment;
    public long m_lParentDepartmentID;

    //�ͻ�����
    public long m_lClientTypeID;

    //��������
    public long m_lRiskLevelID;

    //���˴���
    public String m_strLegalPerson;

    //�������˻�
    public String m_strBankAccount;

    //������
    public String m_strBank;

    //ע���ʱ�
    public String m_strCaptial;


    //��ҳ��ʾ��ҳ����¼
    public long m_lPageCount;

    //�ͻ���Ӧ��������
    public long m_lMaxLoanID = -1;

    //��������ID
    public long m_lDKDCBID;

    //������������
    public String m_strDKDCBName = "";

    //�ͻ���Ӧ��󵣱���
    public long m_lMaxAssureID = -1;

    //���������ID
    public long m_lDBDCBID;

    //�������������
    public String m_strDBDCBName = "";


}
