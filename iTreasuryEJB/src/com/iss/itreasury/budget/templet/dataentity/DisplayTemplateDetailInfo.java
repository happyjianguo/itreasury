/*
 * Created on 2004-6-29
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.budget.templet.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author wlming
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class DisplayTemplateDetailInfo implements Serializable
{
	
	private double mAmount = 0.0;//���	
	private String sDisplayValue = "0.0"; //��ʾֵ
	private long lIschange = -1;//�Ƿ��޸Ĺ�
	private long lIsEdit = -1 ; //�Ƿ���Ա༭   1ֻ��  0 �ɱ༭
	private long lDisplayType = -1 ; //��ʾ����     1 ��ʾsDisplayValue 0 ��ʾ���
	

	/**
	 * @return Returns the ischange.
	 */
	public long getIschange() {
		return lIschange;
	}
	/**
	 * @param ischange The ischange to set.
	 */
	public void setIschange(long ischange) {
		lIschange = ischange;
	}
	/**
	 * @return Returns the planAmount.
	 */
	public double getAmount() {
		return mAmount;
	}
	/**
	 * @param planAmount The planAmount to set.
	 */
	public void setAmount(double amount) {
		mAmount = amount;
	}
	/**
	 * @return Returns the displayValue.
	 */
	public String getDisplayValue() {
		return sDisplayValue;
	}
	/**
	 * @param displayValue The displayValue to set.
	 */
	public void setDisplayValue(String displayValue) {
		this.sDisplayValue = displayValue;
	}
	/**
	 * @return Returns the isEdit.
	 */
	public long getIsEdit() {
		return lIsEdit;
	}
	/**
	 * @param isEdit The isEdit to set.
	 */
	public void setIsEdit(long isEdit) {
		this.lIsEdit = isEdit;
	}
	/**
	 * @return Returns the display.
	 */
	public long getDisplayType() {
		return lDisplayType;
	}
	/**
	 * @param display The display to set.
	 */
	public void setDisplayType(long display) {
		lDisplayType = display;
	}
}