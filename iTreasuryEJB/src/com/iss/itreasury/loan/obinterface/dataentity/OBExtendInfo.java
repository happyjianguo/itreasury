package com.iss.itreasury.loan.obinterface.dataentity;
import java.sql.*;
import java.util.Vector;
/**
 * @author gqzhang
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class OBExtendInfo implements java.io.Serializable
{
	public OBExtendInfo()
	{
	}
	private long lID = -1; //展期指令ID（主键）   
	private long lContractID = -1; //贷款合同ID           
	private long lPlanVersionID = -1; //版本ID               
	private long lLastPlanVersionID = -1; //最大旧版本ID         
	private long lSerialNO = -1; //展期序号             
	private String strInstructionNO = ""; //展期申请指令号       
	private String strExtendReason = ""; //展期原因             
	private String strReturnPostEnd = ""; //归还延期还款本息资金 
	private String strOtherContent = ""; //其他事项             
	private long lBankInterestID = -1; //银行利率ID           
	private long lStatusID = -1; //状态                 
	private long lInputUserID = -1; //录入人ID             
	private long lInterestTypeID = -1; //利率类型             
	private double dInterestAdjust = 0.00; //浮动利率             
	private Timestamp dtInputDate = null; //录入日期             
	private long lExtendTypeID = -1; //展期类型             
	private long lIsCanAccept = -1; //是否接受             
	private long lInID = -1; //内部系统使用字段     
	private long lHandleUserID = -1; //操作人               
	private double dInterestRate = 0.00; //基准利率 
	//**********
	private ContractPlanInfo contractPlanInfo = null;
	//**********
	private Vector vctExtendDetail = null;
	//**********
	private Vector vctContractPlanDetail = null;
	//**********以下为table loan_contractForm 部分信息，信息来源ContractInfo
	private String strContractCode = ""; //sContractCode:合同编号
	private String strBorrowClientName = ""; //sName:贷款单位名称
	private Timestamp tsLoanStart = null; //dtLoanStart借款起始日期
	private Timestamp tsLoanEnd = null; //dtLoanStart借款结束日期
	private long lIntervalNum = -1; //nIntervalNum:贷款期限
	private double dExamineAmount = 0.00; //ExamineAmount 批准金额
	private String strClientName = ""; //sName:委托单位名称
	private double dLoanInterestRate = 0.00; //	fInterestRate:贷款利率，执行利率
	//**********以上为table loan_contractForm 部分信息，信息来源ContractInfo
	/**
	 * Returns the contractPlanInfo.
	 * @return ContractPlanInfo
	 */
	public ContractPlanInfo getContractPlanInfo()
	{
		return contractPlanInfo;
	}
	/**
	 * Returns the interestAdjust.
	 * @return double
	 */
	public double getInterestAdjust()
	{
		return dInterestAdjust;
	}
	/**
	 * Returns the interestRate.
	 * @return double
	 */
	public double getInterestRate()
	{
		return dInterestRate;
	}
	/**
	 * Returns the inputDate.
	 * @return Timestamp
	 */
	public Timestamp getInputDate()
	{
		return dtInputDate;
	}
	/**
	 * Returns the bankInterestID.
	 * @return long
	 */
	public long getBankInterestID()
	{
		return lBankInterestID;
	}
	/**
	 * Returns the contractID.
	 * @return long
	 */
	public long getContractID()
	{
		return lContractID;
	}
	/**
	 * Returns the extendTypeID.
	 * @return long
	 */
	public long getExtendTypeID()
	{
		return lExtendTypeID;
	}
	/**
	 * Returns the handleUserID.
	 * @return long
	 */
	public long getHandleUserID()
	{
		return lHandleUserID;
	}
	/**
	 * Returns the iD.
	 * @return long
	 */
	public long getID()
	{
		return lID;
	}
	/**
	 * Returns the inID.
	 * @return long
	 */
	public long getInID()
	{
		return lInID;
	}
	/**
	 * Returns the inputUserID.
	 * @return long
	 */
	public long getInputUserID()
	{
		return lInputUserID;
	}
	/**
	 * Returns the interestTypeID.
	 * @return long
	 */
	public long getInterestTypeID()
	{
		return lInterestTypeID;
	}
	/**
	 * Returns the isCanAccept.
	 * @return long
	 */
	public long getIsCanAccept()
	{
		return lIsCanAccept;
	}
	/**
	 * Returns the lastPlanVersionID.
	 * @return long
	 */
	public long getLastPlanVersionID()
	{
		return lLastPlanVersionID;
	}
	/**
	 * Returns the planVersionID.
	 * @return long
	 */
	public long getPlanVersionID()
	{
		return lPlanVersionID;
	}
	/**
	 * Returns the serialNO.
	 * @return long
	 */
	public long getSerialNO()
	{
		return lSerialNO;
	}
	/**
	 * Returns the statusID.
	 * @return long
	 */
	public long getStatusID()
	{
		return lStatusID;
	}
	/**
	 * Returns the extendReason.
	 * @return String
	 */
	public String getExtendReason()
	{
		return strExtendReason;
	}
	/**
	 * Returns the instructionNO.
	 * @return String
	 */
	public String getInstructionNO()
	{
		return strInstructionNO;
	}
	/**
	 * Returns the otherContent.
	 * @return String
	 */
	public String getOtherContent()
	{
		return strOtherContent;
	}
	/**
	 * Returns the returnPostEnd.
	 * @return String
	 */
	public String getReturnPostEnd()
	{
		return strReturnPostEnd;
	}
	/**
	 * Returns the contractPlanDetail.
	 * @return Vector
	 */
	public Vector getContractPlanDetail()
	{
		return vctContractPlanDetail;
	}
	/**
	 * Returns the extendDetail.
	 * @return Vector
	 */
	public Vector getExtendDetail()
	{
		return vctExtendDetail;
	}
	/**
	 * Sets the contractPlanInfo.
	 * @param contractPlanInfo The contractPlanInfo to set
	 */
	public void setContractPlanInfo(ContractPlanInfo contractPlanInfo)
	{
		this.contractPlanInfo = contractPlanInfo;
	}
	/**
	 * Sets the interestAdjust.
	 * @param interestAdjust The interestAdjust to set
	 */
	public void setInterestAdjust(double interestAdjust)
	{
		dInterestAdjust = interestAdjust;
	}
	/**
	 * Sets the interestRate.
	 * @param interestRate The interestRate to set
	 */
	public void setInterestRate(double interestRate)
	{
		dInterestRate = interestRate;
	}
	/**
	 * Sets the inputDate.
	 * @param inputDate The inputDate to set
	 */
	public void setInputDate(Timestamp inputDate)
	{
		dtInputDate = inputDate;
	}
	/**
	 * Sets the bankInterestID.
	 * @param bankInterestID The bankInterestID to set
	 */
	public void setBankInterestID(long bankInterestID)
	{
		lBankInterestID = bankInterestID;
	}
	/**
	 * Sets the contractID.
	 * @param contractID The contractID to set
	 */
	public void setContractID(long contractID)
	{
		lContractID = contractID;
	}
	/**
	 * Sets the extendTypeID.
	 * @param extendTypeID The extendTypeID to set
	 */
	public void setExtendTypeID(long extendTypeID)
	{
		lExtendTypeID = extendTypeID;
	}
	/**
	 * Sets the handleUserID.
	 * @param handleUserID The handleUserID to set
	 */
	public void setHandleUserID(long handleUserID)
	{
		lHandleUserID = handleUserID;
	}
	/**
	 * Sets the iD.
	 * @param iD The iD to set
	 */
	public void setID(long iD)
	{
		lID = iD;
	}
	/**
	 * Sets the inID.
	 * @param inID The inID to set
	 */
	public void setInID(long inID)
	{
		lInID = inID;
	}
	/**
	 * Sets the inputUserID.
	 * @param inputUserID The inputUserID to set
	 */
	public void setInputUserID(long inputUserID)
	{
		lInputUserID = inputUserID;
	}
	/**
	 * Sets the interestTypeID.
	 * @param interestTypeID The interestTypeID to set
	 */
	public void setInterestTypeID(long interestTypeID)
	{
		lInterestTypeID = interestTypeID;
	}
	/**
	 * Sets the isCanAccept.
	 * @param isCanAccept The isCanAccept to set
	 */
	public void setIsCanAccept(long isCanAccept)
	{
		lIsCanAccept = isCanAccept;
	}
	/**
	 * Sets the lastPlanVersionID.
	 * @param lastPlanVersionID The lastPlanVersionID to set
	 */
	public void setLastPlanVersionID(long lastPlanVersionID)
	{
		lLastPlanVersionID = lastPlanVersionID;
	}
	/**
	 * Sets the planVersionID.
	 * @param planVersionID The planVersionID to set
	 */
	public void setPlanVersionID(long planVersionID)
	{
		lPlanVersionID = planVersionID;
	}
	/**
	 * Sets the serialNO.
	 * @param serialNO The serialNO to set
	 */
	public void setSerialNO(long serialNO)
	{
		lSerialNO = serialNO;
	}
	/**
	 * Sets the statusID.
	 * @param statusID The statusID to set
	 */
	public void setStatusID(long statusID)
	{
		lStatusID = statusID;
	}
	/**
	 * Sets the extendReason.
	 * @param extendReason The extendReason to set
	 */
	public void setExtendReason(String extendReason)
	{
		strExtendReason = extendReason;
	}
	/**
	 * Sets the instructionNO.
	 * @param instructionNO The instructionNO to set
	 */
	public void setInstructionNO(String instructionNO)
	{
		strInstructionNO = instructionNO;
	}
	/**
	 * Sets the otherContent.
	 * @param otherContent The otherContent to set
	 */
	public void setOtherContent(String otherContent)
	{
		strOtherContent = otherContent;
	}
	/**
	 * Sets the returnPostEnd.
	 * @param returnPostEnd The returnPostEnd to set
	 */
	public void setReturnPostEnd(String returnPostEnd)
	{
		strReturnPostEnd = returnPostEnd;
	}
	/**
	 * Sets the contractPlanDetail.
	 * @param contractPlanDetail The contractPlanDetail to set
	 */
	public void setContractPlanDetail(Vector contractPlanDetail)
	{
		vctContractPlanDetail = contractPlanDetail;
	}
	/**
	 * Sets the extendDetail.
	 * @param extendDetail The extendDetail to set
	 */
	public void setExtendDetail(Vector extendDetail)
	{
		vctExtendDetail = extendDetail;
	}
	/**
	 * Returns the examineAmount.
	 * @return double
	 */
	public double getExamineAmount()
	{
		return dExamineAmount;
	}
	/**
	 * Returns the loanInterestRate.
	 * @return double
	 */
	public double getLoanInterestRate()
	{
		return dLoanInterestRate;
	}
	/**
	 * Returns the intervalNum.
	 * @return long
	 */
	public long getIntervalNum()
	{
		return lIntervalNum;
	}
	/**
	 * Returns the borrowClientName.
	 * @return String
	 */
	public String getBorrowClientName()
	{
		return strBorrowClientName;
	}
	/**
	 * Returns the clientName.
	 * @return String
	 */
	public String getClientName()
	{
		return strClientName;
	}
	/**
	 * Returns the contractCode.
	 * @return String
	 */
	public String getContractCode()
	{
		return strContractCode;
	}
	/**
	 * Returns the loanStart.
	 * @return Timestamp
	 */
	public Timestamp getLoanStart()
	{
		return tsLoanStart;
	}
	/**
	 * Sets the examineAmount.
	 * @param examineAmount The examineAmount to set
	 */
	public void setExamineAmount(double examineAmount)
	{
		dExamineAmount = examineAmount;
	}
	/**
	 * Sets the loanInterestRate.
	 * @param loanInterestRate The loanInterestRate to set
	 */
	public void setLoanInterestRate(double loanInterestRate)
	{
		dLoanInterestRate = loanInterestRate;
	}
	/**
	 * Sets the intervalNum.
	 * @param intervalNum The intervalNum to set
	 */
	public void setIntervalNum(long intervalNum)
	{
		lIntervalNum = intervalNum;
	}
	/**
	 * Sets the borrowClientName.
	 * @param borrowClientName The borrowClientName to set
	 */
	public void setBorrowClientName(String borrowClientName)
	{
		strBorrowClientName = borrowClientName;
	}
	/**
	 * Sets the clientName.
	 * @param clientName The clientName to set
	 */
	public void setClientName(String clientName)
	{
		strClientName = clientName;
	}
	/**
	 * Sets the contractCode.
	 * @param contractCode The contractCode to set
	 */
	public void setContractCode(String contractCode)
	{
		strContractCode = contractCode;
	}
	/**
	 * Sets the loanStart.
	 * @param loanStart The loanStart to set
	 */
	public void setLoanStart(Timestamp loanStart)
	{
		tsLoanStart = loanStart;
	}
	/**
	 * Returns the loanEnd.
	 * @return Timestamp
	 */
	public Timestamp getLoanEnd()
	{
		return tsLoanEnd;
	}
	/**
	 * Sets the loanEnd.
	 * @param loanEnd The loanEnd to set
	 */
	public void setLoanEnd(Timestamp loanEnd)
	{
		tsLoanEnd = loanEnd;
	}
}