/*
 * Created on 2004-10-13
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.settlement.settadjustinterestrate.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.settlement.base.SettlementBaseDataEntity;

/**
 * @author jinchen
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SettAdjustInterestConditionInfo extends SettlementBaseDataEntity
{
    
    private long lID;                    //����������ʾ
    private long lInterestID;            //�������ʱ�ʾ
    private String strInterestNo;        //�������ʱ��
    private String strInterestName;      //������������
    private double dInterestRate;        //��������
    private Timestamp tsUseDate;         //��Ч����

    private long lContractID;            //��ͬ��ʾ
    private String strContractNo;         //��ͬ���
    private long lLoanType;              //��������
    private long lConsignClientID;     //ί�е�λ��ʾ
    private String strConsignClientName; //ί�е�λ����
    private long lPeriod;                  //��������
    private String strAdjustReason;        //���ʵ���ԭ��
    private String strRefuseReason;        //ȡ������ԭ��
    private long lInputUserID;			  //¼����ID
    private String strInputUserName;       //¼��������
    private Timestamp tsInputDate;         //¼������
    private long lCheckUserID;           //���ʵ���������ID
    private String strCheckUserName;      //���ʵ�������������
    private Timestamp tsCheck;           //���ʵ�������ʱ��
    private long lOfficeID;              //���´���ʶ

    //Fan Yi Add in 8.27
    private String strClientName;			//��λ
    private double mExamineAmount;			//������
    private double mBalance;					//�������
    private double fInterestRate;				//��������(��ͬ�ģ���ǰ��)
    private Timestamp tsStartDate;			//���ʼ����
    private Timestamp tsEndDate;				//�����������
    private long lIntervalNum;				//��������

    private long lBankLoanInterestRateID;		//�������ʱ�ʾ
    private String strBankLoanInterestRateNo;	//�������ʱ��
    private String strBankLoanInterestRateName;	//������������
    private double mRate;							//����ֵ(���ڵ�)
    private double dAdjustRate;					//�������ʣ����ڵģ�
    private double dStaidAdjustRate;				//�̶������㣨���ڵģ�
    private Timestamp tsValidate;				//��ͬ���ʵ�����Ч��
	private Timestamp tsRateValidate;            //������Ч��
    private String strReason;					//���ʵ���ԭ��
	private long lStatusID;						//���ʵ���״̬

	//Fan Yi Add in 1.3
	private long lLoanPayNoticeID;			//�ſ�֪ͨ����ʾ
	
	private long lNextCheckLevel = -1;		//��һ����˼���

	private long lCount;

    /**
     * @return Returns the adjustRate.
     */
    public double getAdjustRate()
    {
        return dAdjustRate;
    }
    /**
     * @param adjustRate The adjustRate to set.
     */
    public void setAdjustRate(double adjustRate)
    {
        dAdjustRate = adjustRate;
    }
    /**
     * @return Returns the interestRate.
     */
    public double getInterestRate()
    {
        return dInterestRate;
    }
    /**
     * @param interestRate The interestRate to set.
     */
    public void setInterestRate(double interestRate)
    {
        dInterestRate = interestRate;
    }
    /**
     * @return Returns the staidAdjustRate.
     */
    public double getStaidAdjustRate()
    {
        return dStaidAdjustRate;
    }
    /**
     * @param staidAdjustRate The staidAdjustRate to set.
     */
    public void setStaidAdjustRate(double staidAdjustRate)
    {
        dStaidAdjustRate = staidAdjustRate;
    }
    /**
     * @return Returns the fInterestRate.
     */
    public double getFInterestRate()
    {
        return fInterestRate;
    }
    /**
     * @param interestRate The fInterestRate to set.
     */
    public void setFInterestRate(double interestRate)
    {
        fInterestRate = interestRate;
    }
    /**
     * @return Returns the fRate.
     */
    public double getRate()
    {
        return mRate;
    }
    /**
     * @param rate The fRate to set.
     */
    public void setRate(double rate)
    {
        mRate = rate;
    }
    /**
     * @return Returns the bankLoanInterestRateID.
     */
    public long getBankLoanInterestRateID()
    {
        return lBankLoanInterestRateID;
    }
    /**
     * @param bankLoanInterestRateID The bankLoanInterestRateID to set.
     */
    public void setBankLoanInterestRateID(long bankLoanInterestRateID)
    {
        lBankLoanInterestRateID = bankLoanInterestRateID;
    }
    /**
     * @return Returns the checkUserID.
     */
    public long getCheckUserID()
    {
        return lCheckUserID;
    }
    /**
     * @param checkUserID The checkUserID to set.
     */
    public void setCheckUserID(long checkUserID)
    {
        lCheckUserID = checkUserID;
    }
    /**
     * @return Returns the consignClientID.
     */
    public long getConsignClientID()
    {
        return lConsignClientID;
    }
    /**
     * @param consignClientID The consignClientID to set.
     */
    public void setConsignClientID(long consignClientID)
    {
        lConsignClientID = consignClientID;
    }
    /**
     * @return Returns the contractID.
     */
    public long getContractID()
    {
        return lContractID;
    }
    /**
     * @param contractID The contractID to set.
     */
    public void setContractID(long contractID)
    {
        lContractID = contractID;
    }
    /**
     * @return Returns the count.
     */
    public long getCount()
    {
        return lCount;
    }
    /**
     * @param count The count to set.
     */
    public void setCount(long count)
    {
        lCount = count;
    }
    /**
     * @return Returns the iD.
     */
    public long getID()
    {
        return lID;
    }
    /**
     * @param id The iD to set.
     */
    public void setID(long id)
    {
        lID = id;
    }
    /**
     * @return Returns the inputUserID.
     */
    public long getInputUserID()
    {
        return lInputUserID;
    }
    /**
     * @param inputUserID The inputUserID to set.
     */
    public void setInputUserID(long inputUserID)
    {
        lInputUserID = inputUserID;
    }
    /**
     * @return Returns the interestID.
     */
    public long getInterestID()
    {
        return lInterestID;
    }
    /**
     * @param interestID The interestID to set.
     */
    public void setInterestID(long interestID)
    {
        lInterestID = interestID;
    }
    /**
     * @return Returns the intervalNum.
     */
    public long getIntervalNum()
    {
        return lIntervalNum;
    }
    /**
     * @param intervalNum The intervalNum to set.
     */
    public void setIntervalNum(long intervalNum)
    {
        lIntervalNum = intervalNum;
    }
    /**
     * @return Returns the loanPayNoticeID.
     */
    public long getLoanPayNoticeID()
    {
        return lLoanPayNoticeID;
    }
    /**
     * @param loanPayNoticeID The loanPayNoticeID to set.
     */
    public void setLoanPayNoticeID(long loanPayNoticeID)
    {
        lLoanPayNoticeID = loanPayNoticeID;
    }
    /**
     * @return Returns the loanType.
     */
    public long getLoanType()
    {
        return lLoanType;
    }
    /**
     * @param loanType The loanType to set.
     */
    public void setLoanType(long loanType)
    {
        lLoanType = loanType;
    }
    /**
     * @return Returns the nextCheckLevel.
     */
    public long getNextCheckLevel()
    {
        return lNextCheckLevel;
    }
    /**
     * @param nextCheckLevel The nextCheckLevel to set.
     */
    public void setNextCheckLevel(long nextCheckLevel)
    {
        lNextCheckLevel = nextCheckLevel;
    }
    /**
     * @return Returns the officeID.
     */
    public long getOfficeID()
    {
        return lOfficeID;
    }
    /**
     * @param officeID The officeID to set.
     */
    public void setOfficeID(long officeID)
    {
        lOfficeID = officeID;
    }
    /**
     * @return Returns the period.
     */
    public long getPeriod()
    {
        return lPeriod;
    }
    /**
     * @param period The period to set.
     */
    public void setPeriod(long period)
    {
        lPeriod = period;
    }
    /**
     * @return Returns the statusID.
     */
    public long getStatusID()
    {
        return lStatusID;
    }
    /**
     * @param statusID The statusID to set.
     */
    public void setStatusID(long statusID)
    {
        lStatusID = statusID;
    }
    /**
     * @return Returns the balance.
     */
    public double getBalance()
    {
        return mBalance;
    }
    /**
     * @param balance The balance to set.
     */
    public void setBalance(double balance)
    {
        mBalance = balance;
    }
    /**
     * @return Returns the examineAmount.
     */
    public double getExamineAmount()
    {
        return mExamineAmount;
    }
    /**
     * @param examineAmount The examineAmount to set.
     */
    public void setExamineAmount(double examineAmount)
    {
        mExamineAmount = examineAmount;
    }
    /**
     * @return Returns the adjustReason.
     */
    public String getAdjustReason()
    {
        return strAdjustReason;
    }
    /**
     * @param adjustReason The adjustReason to set.
     */
    public void setAdjustReason(String adjustReason)
    {
        strAdjustReason = adjustReason;
    }
    /**
     * @return Returns the bankLoanInterestRateName.
     */
    public String getBankLoanInterestRateName()
    {
        return strBankLoanInterestRateName;
    }
    /**
     * @param bankLoanInterestRateName The bankLoanInterestRateName to set.
     */
    public void setBankLoanInterestRateName(String bankLoanInterestRateName)
    {
        strBankLoanInterestRateName = bankLoanInterestRateName;
    }
    /**
     * @return Returns the bankLoanInterestRateNo.
     */
    public String getBankLoanInterestRateNo()
    {
        return strBankLoanInterestRateNo;
    }
    /**
     * @param bankLoanInterestRateNo The bankLoanInterestRateNo to set.
     */
    public void setBankLoanInterestRateNo(String bankLoanInterestRateNo)
    {
        strBankLoanInterestRateNo = bankLoanInterestRateNo;
    }
    /**
     * @return Returns the checkUserName.
     */
    public String getCheckUserName()
    {
        return strCheckUserName;
    }
    /**
     * @param checkUserName The checkUserName to set.
     */
    public void setCheckUserName(String checkUserName)
    {
        strCheckUserName = checkUserName;
    }
    /**
     * @return Returns the clientName.
     */
    public String getClientName()
    {
        return strClientName;
    }
    /**
     * @param clientName The clientName to set.
     */
    public void setClientName(String clientName)
    {
        strClientName = clientName;
    }
    /**
     * @return Returns the consignClientName.
     */
    public String getConsignClientName()
    {
        return strConsignClientName;
    }
    /**
     * @param consignClientName The consignClientName to set.
     */
    public void setConsignClientName(String consignClientName)
    {
        strConsignClientName = consignClientName;
    }
    /**
     * @return Returns the contractNo.
     */
    public String getContractNo()
    {
        return strContractNo;
    }
    /**
     * @param contractNo The contractNo to set.
     */
    public void setContractNo(String contractNo)
    {
        strContractNo = contractNo;
    }
    /**
     * @return Returns the inputUserName.
     */
    public String getInputUserName()
    {
        return strInputUserName;
    }
    /**
     * @param inputUserName The inputUserName to set.
     */
    public void setInputUserName(String inputUserName)
    {
        strInputUserName = inputUserName;
    }
    /**
     * @return Returns the interestName.
     */
    public String getInterestName()
    {
        return strInterestName;
    }
    /**
     * @param interestName The interestName to set.
     */
    public void setInterestName(String interestName)
    {
        strInterestName = interestName;
    }
    /**
     * @return Returns the interestNo.
     */
    public String getInterestNo()
    {
        return strInterestNo;
    }
    /**
     * @param interestNo The interestNo to set.
     */
    public void setInterestNo(String interestNo)
    {
        strInterestNo = interestNo;
    }
    /**
     * @return Returns the reason.
     */
    public String getReason()
    {
        return strReason;
    }
    /**
     * @param reason The reason to set.
     */
    public void setReason(String reason)
    {
        strReason = reason;
    }
    /**
     * @return Returns the refuseReason.
     */
    public String getRefuseReason()
    {
        return strRefuseReason;
    }
    /**
     * @param refuseReason The refuseReason to set.
     */
    public void setRefuseReason(String refuseReason)
    {
        strRefuseReason = refuseReason;
    }
    /**
     * @return Returns the check.
     */
    public Timestamp getCheck()
    {
        return tsCheck;
    }
    /**
     * @param check The check to set.
     */
    public void setCheck(Timestamp check)
    {
        tsCheck = check;
    }
    /**
     * @return Returns the endDate.
     */
    public Timestamp getEndDate()
    {
        return tsEndDate;
    }
    /**
     * @param endDate The endDate to set.
     */
    public void setEndDate(Timestamp endDate)
    {
        tsEndDate = endDate;
    }
    /**
     * @return Returns the inputDate.
     */
    public Timestamp getInputDate()
    {
        return tsInputDate;
    }
    /**
     * @param inputDate The inputDate to set.
     */
    public void setInputDate(Timestamp inputDate)
    {
        tsInputDate = inputDate;
    }
    /**
     * @return Returns the rateValidate.
     */
    public Timestamp getRateValidate()
    {
        return tsRateValidate;
    }
    /**
     * @param rateValidate The rateValidate to set.
     */
    public void setRateValidate(Timestamp rateValidate)
    {
        tsRateValidate = rateValidate;
    }
    /**
     * @return Returns the startDate.
     */
    public Timestamp getStartDate()
    {
        return tsStartDate;
    }
    /**
     * @param startDate The startDate to set.
     */
    public void setStartDate(Timestamp startDate)
    {
        tsStartDate = startDate;
    }
    /**
     * @return Returns the useDate.
     */
    public Timestamp getUseDate()
    {
        return tsUseDate;
    }
    /**
     * @param useDate The useDate to set.
     */
    public void setUseDate(Timestamp useDate)
    {
        tsUseDate = useDate;
    }
    /**
     * @return Returns the validate.
     */
    public Timestamp getValidate()
    {
        return tsValidate;
    }
    /**
     * @param validate The validate to set.
     */
    public void setValidate(Timestamp validate)
    {
        tsValidate = validate;
    }


    public static void main(String[] args)
    {
    }
}
