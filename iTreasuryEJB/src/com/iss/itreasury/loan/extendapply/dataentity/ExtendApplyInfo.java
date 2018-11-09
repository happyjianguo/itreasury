/*
 * ExtendApplyInfo.java
 *
 * Created on 2002��2��19��, ����6:15
 */

package com.iss.itreasury.loan.extendapply.dataentity ;

import java.beans.* ;
import java.sql.* ;
import java.util.* ;

/**
 *
 * @author  yzhang
 * @version
 */
public class ExtendApplyInfo
      implements java.io.Serializable
{

    /**
     * ExtendApplyInfo ������ע�⡣
     */
    public ExtendApplyInfo()
    {
        super() ;
    }

    public long m_lID ; //չ�������ʾ

    public long m_nCurrencyID ; //����
    
    public long m_typeId;//��������
    public long m_lContractID ; //�����ͬ��ʾ
    public String m_strContractCode ; //�����ͬ��ʾ
    public double m_dLoanAmount ; // ��ͬ���
    public Timestamp m_tsLoanDate ; // ��������
    public long m_lLoanIntervalNum ; // ��������
    public long m_lisExtend ; // �Ƿ�չ��

    public String m_strClientName ;
    public long m_lSerialNo ; //չ�����
    public double m_dLoanBalance ; //�������
    public double m_dExtendAmount ; //չ�ڽ��
    public Timestamp m_tsExtendBeginDate ; //չ����ʼ����
    public Timestamp m_tsExtendEndDate ; //չ�ڽ�������
    public long m_lExtendIntervalNum ; //չ������
    public String m_strExtendReason ; //չ��ԭ��
    public String m_strReturnPostPend ; //�黹���ڻ��Ϣ�ʽ�
    public String m_strOtherContent ; //��������
    public double m_dExamineAmount ; //��˽��
    public long m_lIntervalNum ; //����
    public double m_dInterestRate ; //����
    public double m_dBasicInterestRate ; //��׼����
    public long m_lStatusID ; //״̬
    public long m_lCheckNum ;

    public long m_lInputUserID ; //¼���˱�ʾ
    public String m_sInputUserName ; //¼��������
    public Timestamp m_tsInputDate ;
    public long m_lCheckUserID ; //�����˱�ʾ
    public String m_sCheckUserName ; //����������

    public String m_sExCode ; // չ�ں�ͬ���
    public long m_lExContractID ; // չ�ں�ͬ��ʶ
    public long m_lNextUserID ; //��һ�����˱�ʶ
    public long m_lNextCheckLevel = -1;	//��һ����˼���
    public Timestamp m_dtCheckDate ; // ��������
    public long m_lInterestTypeID ; // ��������
    public double m_dLiborAdjust ; // ���ʵ���
    public long m_lLiborRateID ;
    public long lBankRateTypeID ; // ������������
    public double m_dDefaultBankRate ; //ȱʡֵ

    public long loanID ; // LOANINFO.ID
    public Timestamp dtStartDate_loaninfo ; // ������ʼ����
    public Timestamp dtEndDate_loaninfo ; // ������ֹ����
    public double dExamineAmount_loaninfo ; // ������
    public double dfinterestRate_ioaninfo ; // ִ������
    public long lExamineIntervalNum_loaninfo ;
    public double dLoanApplyAmount_loaninfo ;
    public long lConsignContractID_loaninfo ;
    public long lConsignClientID ;
    //Fan Yi
    public float fChargeRate ; //��������
    public double dContractBalance ;

    public long lLoanTypeID ; // ������������
    public long lLoanSubTypeID ;//����������
    public String sClientName ; // ��λ
    public String sEconomyType ;
    public String sConsignClientName ; // ί�е�λ
    public String sConsignCode ; // ί�к�ͬ���
    public String sLastUserName ; // ��������
    public String sApplyCode ; // ����������
    public String sExtendApplyCode ; // չ��������

    public long m_lextendtype ; // չ������
    public long m_lPlanVersionID ; // �ƻ��汾��ʶ
    public long m_lPageCount ; //��¼��

    public Collection cExtendList ; // չ����ϸ��
    public Collection cExtendContractList ; // չ�ں�ͬ��

    public String m_strOpinion1 = "" ; //�������
    public String m_strOpinion2 = "" ;
    public String m_strOpinion3 = "" ;
    
    public double dStaidAdjustRate=0;       //�̶���������
    public double dAdjustRate=0;            //��������
    
    public long isLowLevel = -1;

}
