package com.iss.itreasury.itreasuryinfo.branch.dataentity;

import java.sql.Timestamp;

public class BranchRateResultInfo {
	private Timestamp dtDate = null;		/** ���� */
	private double mRate = 0.0;		        /** ����ֵ�������ʣ�*/
	public Timestamp getDate() {
		return dtDate;
	}
	public void setDate(Timestamp dtDate) {
		this.dtDate = dtDate;
	}
	public double getRate() {
		return mRate;
	}
	public void setRate(double mRate) {
		this.mRate = mRate;
	}
	
	
}
