/*
 * Created on 2004-7-30
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.settlement.compatibilitysetting.dataentity;
import java.sql.Timestamp;

import com.iss.itreasury.settlement.base.SettlementBaseDataEntity;

/**
 * @author yychen
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CompatibilityTypeSettingInfo extends SettlementBaseDataEntity
{
	private long Id = -1; //	Number	ID	主键，兼容业务类型ID
	private String Name = ""; //Abstract	名称	兼容业务名称
	private long OfficeID = -1; //	Number	办事处	设置该兼容业务类型的办事处
	private long CurrencyID = -1; //	Number	币种	设置该兼容业务类型的币种
	private long AmountSource1 = -1; //	Number	是否需要资金来源信息1	取值范围：有效、无效
	private long Voucher1 = -1; //	Number	是否需要凭证号信息1	取值范围：有效、无效
	private long BankInfo1 = -1; //	Number	是否需要银行信息1	取值范围：有效、无效
	private long AmountSource2 = -1; //	Number	是否需要资金来源信息2	取值范围：有效、无效
	private long Voucher2 = -1; //	Number	是否需要凭证号信息2	取值范围：有效、无效
	private long BankInfo2 = -1; //	Number	是否需要银行信息2	取值范围：有效、无效
	private long AmountSource3 = -1; //	Number	是否需要资金来源信息3	取值范围：有效、无效
	private long Voucher3 = -1; //	Number	是否需要凭证号信息3	取值范围：有效、无效
	private long BankInfo3 = -1; //	Number	是否需要银行信息3	取值范围：有效、无效
	private long AmountSource4 = -1; //	Number	是否需要资金来源信息4	取值范围：有效、无效
	private long Voucher4 = -1; //	Number	是否需要凭证号信息4	取值范围：有效、无效
	private long BankInfo4 = -1; //	Number	是否需要银行信息4	取值范围：有效、无效
	private long InputUserID = -1; //	Number	录入人	录入人
	private Timestamp InputDate = null; //	录入时间	录入时间
	private long StatusID = -1; //	Number	记录状态	取值范围：有效、无效
	/**
	 * Returns the amountSource1.
	 * @return long
	 */
	public long getAmountSource1()
	{
		return AmountSource1;
	}
	/**
	 * Returns the amountSource2.
	 * @return long
	 */
	public long getAmountSource2()
	{
		return AmountSource2;
	}
	/**
	 * Returns the amountSource3.
	 * @return long
	 */
	public long getAmountSource3()
	{
		return AmountSource3;
	}
	/**
	 * Returns the amountSource4.
	 * @return long
	 */
	public long getAmountSource4()
	{
		return AmountSource4;
	}
	/**
	 * Returns the bankInfo1.
	 * @return long
	 */
	public long getBankInfo1()
	{
		return BankInfo1;
	}
	/**
	 * Returns the bankInfo2.
	 * @return long
	 */
	public long getBankInfo2()
	{
		return BankInfo2;
	}
	/**
	 * Returns the bankInfo3.
	 * @return long
	 */
	public long getBankInfo3()
	{
		return BankInfo3;
	}
	/**
	 * Returns the bankInfo4.
	 * @return long
	 */
	public long getBankInfo4()
	{
		return BankInfo4;
	}
	/**
	 * Returns the currencyID.
	 * @return long
	 */
	public long getCurrencyID()
	{
		return CurrencyID;
	}
	/**
	 * Returns the iD.
	 * @return long
	 */
	public long getId()
	{
		return Id;
	}
	/**
	 * Returns the inputDate.
	 * @return Timestamp
	 */
	public Timestamp getInputDate()
	{
		return InputDate;
	}
	/**
	 * Returns the inputUserID.
	 * @return long
	 */
	public long getInputUserID()
	{
		return InputUserID;
	}
	/**
	 * Returns the name.
	 * @return String
	 */
	public String getName()
	{
		return Name;
	}
	/**
	 * Returns the officeID.
	 * @return long
	 */
	public long getOfficeID()
	{
		return OfficeID;
	}
	/**
	 * Returns the statusID.
	 * @return long
	 */
	public long getStatusID()
	{
		return StatusID;
	}
	/**
	 * Returns the voucher1.
	 * @return long
	 */
	public long getVoucher1()
	{
		return Voucher1;
	}
	/**
	 * Returns the voucher2.
	 * @return long
	 */
	public long getVoucher2()
	{
		return Voucher2;
	}
	/**
	 * Returns the voucher3.
	 * @return long
	 */
	public long getVoucher3()
	{
		return Voucher3;
	}
	/**
	 * Returns the voucher4.
	 * @return long
	 */
	public long getVoucher4()
	{
		return Voucher4;
	}
	/**
	 * Sets the amountSource1.
	 * @param amountSource1 The amountSource1 to set
	 */
	public void setAmountSource1(long amountSource1)
	{
		AmountSource1 = amountSource1;
		putUsedField("AmountSource1", AmountSource1);
	}
	/**
	 * Sets the amountSource2.
	 * @param amountSource2 The amountSource2 to set
	 */
	public void setAmountSource2(long amountSource2)
	{
		AmountSource2 = amountSource2;
		putUsedField("AmountSource2", AmountSource2);
	}
	/**
	 * Sets the amountSource3.
	 * @param amountSource3 The amountSource3 to set
	 */
	public void setAmountSource3(long amountSource3)
	{
		AmountSource3 = amountSource3;
		putUsedField("AmountSource3", AmountSource3);
	}
	/**
	 * Sets the amountSource4.
	 * @param amountSource4 The amountSource4 to set
	 */
	public void setAmountSource4(long amountSource4)
	{
		AmountSource4 = amountSource4;
		putUsedField("AmountSource4", AmountSource4);
	}
	/**
	 * Sets the bankInfo1.
	 * @param bankInfo1 The bankInfo1 to set
	 */
	public void setBankInfo1(long bankInfo1)
	{
		BankInfo1 = bankInfo1;
		putUsedField("BankInfo1", BankInfo1);
	}
	/**
	 * Sets the bankInfo2.
	 * @param bankInfo2 The bankInfo2 to set
	 */
	public void setBankInfo2(long bankInfo2)
	{
		BankInfo2 = bankInfo2;
		putUsedField("BankInfo2", BankInfo2);
	}
	/**
	 * Sets the bankInfo3.
	 * @param bankInfo3 The bankInfo3 to set
	 */
	public void setBankInfo3(long bankInfo3)
	{
		BankInfo3 = bankInfo3;
		putUsedField("BankInfo3", BankInfo3);
	}
	/**
	 * Sets the bankInfo4.
	 * @param bankInfo4 The bankInfo4 to set
	 */
	public void setBankInfo4(long bankInfo4)
	{
		BankInfo4 = bankInfo4;
		putUsedField("BankInfo4", BankInfo4);
	}
	/**
	 * Sets the currencyID.
	 * @param currencyID The currencyID to set
	 */
	public void setCurrencyID(long currencyID)
	{
		CurrencyID = currencyID;
		putUsedField("CurrencyID", CurrencyID);
	}
	/**
	 * Sets the iD.
	 * @param iD The iD to set
	 */
	public void setId(long iD)
	{
		Id = iD;
		putUsedField("Id", Id);
	}
	/**
	 * Sets the inputDate.
	 * @param inputDate The inputDate to set
	 */
	public void setInputDate(Timestamp inputDate)
	{
		InputDate = inputDate;
		putUsedField("InputDate", InputDate);
	}
	/**
	 * Sets the inputUserID.
	 * @param inputUserID The inputUserID to set
	 */
	public void setInputUserID(long inputUserID)
	{
		InputUserID = inputUserID;
		putUsedField("InputUserID", InputUserID);
	}
	/**
	 * Sets the name.
	 * @param name The name to set
	 */
	public void setName(String name)
	{
		Name = name;
		putUsedField("Name", Name);
	}
	/**
	 * Sets the officeID.
	 * @param officeID The officeID to set
	 */
	public void setOfficeID(long officeID)
	{
		OfficeID = officeID;
		putUsedField("OfficeID", OfficeID);
	}
	/**
	 * Sets the statusID.
	 * @param statusID The statusID to set
	 */
	public void setStatusID(long statusID)
	{
		StatusID = statusID;
		putUsedField("StatusID", StatusID);
	}
	/**
	 * Sets the voucher1.
	 * @param voucher1 The voucher1 to set
	 */
	public void setVoucher1(long voucher1)
	{
		Voucher1 = voucher1;
		putUsedField("Voucher1", Voucher1);
	}
	/**
	 * Sets the voucher2.
	 * @param voucher2 The voucher2 to set
	 */
	public void setVoucher2(long voucher2)
	{
		Voucher2 = voucher2;
		putUsedField("Voucher2", Voucher2);
	}
	/**
	 * Sets the voucher3.
	 * @param voucher3 The voucher3 to set
	 */
	public void setVoucher3(long voucher3)
	{
		Voucher3 = voucher3;
		putUsedField("Voucher3", Voucher3);
	}
	/**
	 * Sets the voucher4.
	 * @param voucher4 The voucher4 to set
	 */
	public void setVoucher4(long voucher4)
	{
		Voucher4 = voucher4;
		putUsedField("Voucher4", Voucher4);
	}
}
