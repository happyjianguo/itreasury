/*
 * Created on 2004-9-10
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.settlement.setting.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.settlement.base.SettlementBaseDataEntity;

/**
 * @author jinchen
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class SettInterestRateSettingInfo extends SettlementBaseDataEntity
{
	//������ϢID
	private long ID;
	//������Ϣ����(FK_reference_loan_InterestRateTypeInfo_ID) 
	private long nBankInterestTypeId;
	//������Ϣ��������
	private String sInterestRateName;
    //������ֵ
	private double mRate; 
	//��Ч���� 
    private Timestamp dtValiDate;             
    //�ύ¼����ID
	private long nInputUserId;
	//�ύ¼��������
	private String sInputUserName;            
    //�ύ¼������
    private Timestamp dtInput;
    //�ύ�޸���ID
    private long nModifyUserId; 
    //�ύ�޸���
    private String sModifyUserName; 
    //�ύ�޸�ʱ��              
    private Timestamp dtModify;              
    //.............
	private long PageCount;
    //���ʱ��?
	
	private String sInterestRateNo;
	public static void main(String[] args)
	{
	    SettInterestRateSettingInfo t = new SettInterestRateSettingInfo();
	    
	}
	
	/**
	 * @return Returns the dtInput.
	 */
	public Timestamp getDtInput()
	{
		return dtInput;
	}
	/**
	 * @return Returns the dtModify.
	 */
	public Timestamp getDtModify()
	{
		return dtModify;
	}
	/**
	 * @return Returns the dtValiDate.
	 */
	public Timestamp getDtValiDate()
	{
		return dtValiDate;
	}
	/**
	 * @return Returns the iD.
	 */
	public long getId()
	{
		return ID;
	}
	/**
	 * @return Returns the mRate.
	 */
	public double getMRate()
	{
		return mRate;
	}
	/**
	 * @return Returns the nBankInterestTypeId.
	 */
	public long getNBankInterestTypeId()
	{
		return nBankInterestTypeId;
	}
	/**
	 * @return Returns the nInputUserId.
	 */
	public long getNInputUserId()
	{
		return nInputUserId;
	}
	/**
	 * @return Returns the nModifyUserId.
	 */
	public long getNModifyUserId()
	{
		return nModifyUserId;
	}
	/**
	 * @return Returns the pageCount.
	 */
	public long getPageCount()
	{
		return PageCount;
	}
	/**
	 * @return Returns the sInputUserName.
	 */
	public String getSInputUserName()
	{
		return sInputUserName;
	}
	/**
	 * @return Returns the sInterestRateName.
	 */
	public String getSInterestRateName()
	{
		return sInterestRateName;
	}
	/**
	 * @param dtInput The dtInput to set.
	 */
	public void setDtInput(Timestamp dtInput)
	{
		this.dtInput = dtInput;
		this.putUsedField("dtInput",dtInput);
	}
	/**
	 * @param dtModify The dtModify to set.
	 */
	public void setDtModify(Timestamp dtModify)
	{
		this.dtModify = dtModify;
		this.putUsedField("dtModify",dtModify);
	}
	/**
	 * @param dtValiDate The dtValiDate to set.
	 */
	public void setDtValiDate(Timestamp dtValiDate)
	{
		this.dtValiDate = dtValiDate;
		this.putUsedField("dtValiDate",dtValiDate);
	}
	/**
	 * @param id The iD to set.
	 */
	public void setId(long id)
	{
		ID = id;
		this.putUsedField("ID",id);
	}
	/**
	 * @param rate The mRate to set.
	 */
	public void setMRate(double rate)
	{
		mRate = rate;
		this.putUsedField("mRate",rate);
	}
	/**
	 * @param bankInterestTypeId The nBankInterestTypeId to set.
	 */
	public void setNBankInterestTypeId(long bankInterestTypeId)
	{
		nBankInterestTypeId = bankInterestTypeId;
		this.putUsedField("nBankInterestTypeId",bankInterestTypeId);
	}
	/**
	 * @param inputUserId The nInputUserId to set.
	 */
	public void setNInputUserId(long inputUserId)
	{
		nInputUserId = inputUserId;
		this.putUsedField("nInputUserId",inputUserId);
	}
	/**
	 * @param modifyUserId The nModifyUserId to set.
	 */
	public void setNModifyUserId(long modifyUserId)
	{
		nModifyUserId = modifyUserId;
		this.putUsedField("nModifyUserId",modifyUserId);
	}
	/**
	 * @param pageCount The pageCount to set.
	 */
	public void setPageCount(long pageCount)
	{
		PageCount = pageCount;
	}
	/**
	 * @param inputUserName The sInputUserName to set.
	 */
	public void setSInputUserName(String inputUserName)
	{
		sInputUserName = inputUserName;
	}
	/**
	 * @param interestRateName The sInterestRateName to set.
	 */
	public void setSInterestRateName(String interestRateName)
	{
		sInterestRateName = interestRateName;
	}
	/**
	 * @return Returns the sInterestRateNo.
	 */
	public String getSInterestRateNo()
	{
		return sInterestRateNo;
	}
	/**
	 * @param interestRateNo The sInterestRateNo to set.
	 */
	public void setSInterestRateNo(String interestRateNo)
	{
		sInterestRateNo = interestRateNo;
	}
	/**
	 * @return Returns the sModifyUserName.
	 */
	public String getSModifyUserName() {
		return sModifyUserName;
	}
	/**
	 * @param modifyUserName The sModifyUserName to set.
	 */
	public void setSModifyUserName(String modifyUserName) {
		sModifyUserName = modifyUserName;
	}
}
