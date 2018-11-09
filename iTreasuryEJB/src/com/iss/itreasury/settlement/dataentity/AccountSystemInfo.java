package com.iss.itreasury.settlement.dataentity;

/**
 * �˻���ϵͳ����������ʵ��ӿڣ����������ֶ����˻���ϵ���ô�������ֶ�������(���ǵ������˻���ϵͳ���û��д�ĸĶ�,Ϊʹ����ĵ��ò���Ӱ��,���Դ������ࣩ
 * 
 * @date 2008/02/27
 * @author mzh_fu
 * 
 */
public class AccountSystemInfo {
	private long nOfficeId = -1;

	private long nCurrencyId = -1;

	private long nAccountId = -1;

	private double dPayAmount = 0.00d;

	public long getNAccountId() {
		return nAccountId;
	}

	public void setNAccountId(long accountId) {
		nAccountId = accountId;
	}

	public long getNCurrencyId() {
		return nCurrencyId;
	}

	public void setNCurrencyId(long currencyId) {
		nCurrencyId = currencyId;
	}

	public long getNOfficeId() {
		return nOfficeId;
	}

	public void setNOfficeId(long officeId) {
		nOfficeId = officeId;
	}

	public double getDPayAmount() {
		return dPayAmount;
	}

	public void setDPayAmount(double payAmount) {
		dPayAmount = payAmount;
	}

}
