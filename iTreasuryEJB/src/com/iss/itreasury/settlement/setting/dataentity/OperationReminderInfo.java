/*
 * Created on 2004-10-12
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.settlement.setting.dataentity;

/**
 * @author weilu
 *
*/
/**
 * ҵ������������Ϣ��
 * �������ڣ�(2002-1-15 18:11:44)
 * @author��Administrator
 */
public class OperationReminderInfo implements java.io.Serializable {
    /**
     * OpenNotifyAccountInfo ������ע�⡣
     */
    public OperationReminderInfo() {
        super();
    }
    
    public long m_lOfficeID;
    public long m_lCurrencyID;
    public long m_lFixedDeposit;
    public long m_lLoan;
    public long m_lUnification;
    public long m_lPreInput;
    public long m_lInterestCompute;
    public long m_l973;
    public long m_lNetOperation;
    public long m_lSecOperation;
    public long m_lOverDraft;
    public long m_lIsRemind;
    public long m_lPayForm;
    public long m_OffBalanceForm; //�Ƿ���Ҫ�ſ����ת��������
	public long m_lAheadRepayForm;
	public long m_lFreeForm;
	public long m_lNegotiation;	//�Ƿ���ҪЭ���������
	public long m_lEnsureDeposit;	//�Ƿ���Ҫ��֤��������
	public long m_lOBSoundRemind;
	public long m_lAccountDeadLine;	//�Ƿ���Ҫ�޶�����
	
	
	public long m_lIsNeedBillRemindDay;
	public long m_lIsNeedBillConsignReceiveDay;
	public long m_lIsNeedFreezeDay;
	public long m_lIsNeedLossDay;
	public long m_lExhibitionDay;        //չ�ں�ͬ��������
	public long m_lIsNeedPrimnessDay;
	
	public double m_dIsNeedBankLowBalance;
	public double m_dIsNeedLowRate;
	
}

