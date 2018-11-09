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
 * 业务提醒设置信息。
 * 创建日期：(2002-1-15 18:11:44)
 * @author：Administrator
 */
public class OperationReminderInfo implements java.io.Serializable {
    /**
     * OpenNotifyAccountInfo 构造子注解。
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
    public long m_OffBalanceForm; //是否需要放款单逾期转表外提醒
	public long m_lAheadRepayForm;
	public long m_lFreeForm;
	public long m_lNegotiation;	//是否需要协定存款提醒
	public long m_lEnsureDeposit;	//是否需要保证金存款提醒
	public long m_lOBSoundRemind;
	public long m_lAccountDeadLine;	//是否需要限额提醒
	
	
	public long m_lIsNeedBillRemindDay;
	public long m_lIsNeedBillConsignReceiveDay;
	public long m_lIsNeedFreezeDay;
	public long m_lIsNeedLossDay;
	public long m_lExhibitionDay;        //展期合同提醒天数
	public long m_lIsNeedPrimnessDay;
	
	public double m_dIsNeedBankLowBalance;
	public double m_dIsNeedLowRate;
	
}

