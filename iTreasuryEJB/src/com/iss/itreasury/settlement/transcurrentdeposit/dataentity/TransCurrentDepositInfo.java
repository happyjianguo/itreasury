package com.iss.itreasury.settlement.transcurrentdeposit.dataentity;
//import java.io.Serializable;
import java.io.Serializable;
import java.sql.Timestamp;
import com.iss.itreasury.settlement.account.dataentity.ExternalAccountInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Env;
import com.iss.sysframe.base.dataentity.BaseDataEntity;
/**
 * Title: iTreasury Description: Copyright: Copyright (c) 2003 Company:
 * iSoftStone
 * 
 * @author yehuang
 * @version Date of Creation 2003-9-8
 */
public class TransCurrentDepositInfo extends BaseDataEntity implements Serializable {
	//下属单位客户ID
	private long SubClientID = -1;
	//	付款客户ID
	private long payClientID = -1;
	//收款账户ID
	private long receiveAccountID = -1;
	//	收款客户ID
	private long receiveClientID = -1;
	// 非财务公司客户的银行账户ID,取值于sett_ExternalAccount表的ID
	//private long externalAccountID = -1;
	//收/付款银行账户ID
	private long bankID = -1;
	//修改时间：时分秒
	private Timestamp modifyTime = null;
	//  现金流向ID
	private long CashFlowID = -1;
	//签认人
	private long signUserID = -1;
	//确认人
	private long confirmUserID = -1;
	//通存通兑对方办事处
	private long confirmOfficeID = -1;
	//取消复核摘要
	private String checkAbstractStr = "";
	//确认摘要
	private String confirmAbstractStr = "";
	//交易状态 0已删除1暂存2保存（未复核）3已复核4未签认5已签认6已确认7已勾账
	private long[] statusIDs = null;
	//银行单边账类型 1现金付款2银行转账
	private long singleBankAccountTypeID = -1;
	//委托收款类型 1关联交易委托收款2股份公司委托收款
	private long consignReceiveTypeID = -1;
	//标识非结算系统产生的流水号 结算模块：default网上银行：ebank证券模块：sec
	private String instructionNo = "";
	//委托付款凭证号
	private String ConsignVoucherNo = "";
	//委托付款凭证密码
	private String ConsignPassword = "";
	//	银行支票号
	private String bankChequeNo = "";
	// 报单号
	private String declarationNo = "";
	//提入提出行
	private String extBankNo = "";
	//摘要id
	private long abstractID = -1;
	//非财务公司账户号
	private String extAccountNo = "";
	//非财务公司客户名称
	private String extClientName = "";
	//非财务公司汇入地(省)：
	private String remitInProvince = "";
	//非财务公司汇入地(市)：
	private String remitInCity = "";
	//非财务公司汇入银行名称
	private String remitInBank = "";
	//是否加急
	private long isUrgent=-1;
	//	PK
	protected long id = -1;
	//	办事处编号ID
	protected long officeID = -1;
	//	交易的币种
	protected long currencyID = -1;
	//	交易编号
	protected String transNo = "";
	//	交易类型
	protected long transactionTypeID = -1;
	//	执行日
	protected Timestamp executeDate = null;
	//	起息日
	protected Timestamp interestStartDate = null;
	//	摘要
	protected String abstractStr = "";
	//	录入人
	protected long inputUserID = -1;
	//复核人
	protected long checkUserID = -1;
	//票据发放银行
	protected long billBankID = -1;
	//票据类型
	protected long billTypeID = -1;
	//票据号
	protected String billNo = "";
	//付款账户ID
	protected long payAccountID = -1;
	//发生额
	protected double amount = 0.0;
	//录入日期
	private Timestamp inputDate = null;
	//确定BankID是付款还是收款，在数据库中不存在该字段，该字段在getter方法中通过交易类型获取
	private boolean isPaymentBank = true;
	
	//联行号
	private String spayeebankexchangeno="";
	//CNAPS号
	private String spayeebankcnapsno ="";
	//机构号
	private String spayeebankorgno ="";
	
	
	//清算类型
	protected long ReckoningTypeID = -1;
	//清算表单上的凭证种类描述
	protected String ReckoningBillTypeDesc = "";
	
	// 
	public final static long Auto_OperationType_SaveAndCheck = 1;
//	 
	public final static long Auto_OperationType_CancelSaveAndCheck = 2;
	
	//是否生成银行指令
	private boolean AutoCreateBankInstruction = true;
 	private long autoOperationType = -1;
 	
 	//预算新增
 	//预算项目ID
 	private long BudgetItemID = -1;
    
    
    //手续费金额
    private double commissionAmount = 0.00;
    
    //手续费类型
    private long commissionType = -1;
    
    //手续费交易号
    private String commissionTransNo = "";
    
    //add by xiangzhou  2011-3-23	外部系统申请指令
    
    //外部系统数据来源
    private long lSource = -1;

    //外部系统申请指令号
    private String sApplyCode = "";
    
    //是否不同办事处交易-通存通兑
    private long IsDifOffice = -1;
    
    //付方办事处ID
    private long PayOfficeID = -1;
    
    //收方办事处ID
    private long ReceiveOfficeID = -1;
    
    //总部办事处ID
    private long ParentOfficeID = -1;
	
	/**
	 * @return Returns the autoOperationType.
	 */
	public long getAutoOperationType() {
		return autoOperationType;
	}
	/**
	 * @param autoOperationType The autoOperationType to set.
	 */
	public void setAutoOperationType(long autoOperationType) {
		this.autoOperationType = autoOperationType;
	}
	
	//add by xwhe 2008-11-25  银行付款取消复核自动删除
	private boolean AutoDelBankInstruction = false;
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuffer sbResult = new StringBuffer(128);
/*		sbResult.append(this.getClass().getName() + " instance (hashCode="
				+ this.hashCode() + ")\r\n");
		sbResult.append("=========================================\r\n");
		//获得当前对象指定名称的Field对象
		java.lang.reflect.Field[] field = null;
		try {
			field = this.getClass().getDeclaredFields();
			if (field != null) {
				for (int i = 0; i < field.length; i++) {
					field[i].setAccessible(true);
					sbResult.append(field[i].getName() + " = ");
					sbResult.append(field[i].get(this) + ";\r\n");
				}
			}
		} catch (Exception exp) {
			exp.printStackTrace();
		}*/
		return sbResult.toString();
	}
	/**
	 * Returns the abstractStr.
	 * 
	 * @return String
	 */
	public String getAbstractStr() {
		return abstractStr;
	}
	/**
	 * Returns the billBankID.
	 * 
	 * @return long
	 */
	public long getBillBankID() {
		return billBankID;
	}
	/**
	 * Returns the billNo.
	 * 
	 * @return String
	 */
	public String getBillNo() {
		return billNo;
	}
	/**
	 * Returns the billTypeID.
	 * 
	 * @return long
	 */
	public long getBillTypeID() {
		return billTypeID;
	}
	/**
	 * Returns the checkUserID.
	 * 
	 * @return long
	 */
	public long getCheckUserID() {
		return checkUserID;
	}
	/**
	 * Returns the currencyID.
	 * 
	 * @return long
	 */
	public long getCurrencyID() {
		return currencyID;
	}
	/**
	 * Returns the executeDate.
	 * 
	 * @return Timestamp
	 */
	public Timestamp getExecuteDate() {
		return executeDate;
	}
	/**
	 * Returns the id.
	 * 
	 * @return long
	 */
	public long getId() {
		return id;
	}
	/**
	 * Returns the inputUserID.
	 * 
	 * @return long
	 */
	public long getInputUserID() {
		return inputUserID;
	}
	/**
	 * Returns the interestStartDate.
	 * 
	 * @return Timestamp
	 */
	public Timestamp getInterestStartDate() {
		return interestStartDate;
	}
	/**
	 * Returns the officeID.
	 * 
	 * @return long
	 */
	public long getOfficeID() {
		return officeID;
	}
	/**
	 * Returns the transactionTypeID.
	 * 
	 * @return long
	 */
	public long getTransactionTypeID() {
		return transactionTypeID;
	}
	/**
	 * Returns the transNo.
	 * 
	 * @return String
	 */
	public String getTransNo() {
		return transNo;
	}
	/**
	 * Sets the abstractStr.
	 * 
	 * @param abstractStr
	 *            The abstractStr to set
	 */
	public void setAbstractStr(String abstractStr) {
		this.abstractStr = abstractStr;
	}
	/**
	 * Sets the billBankID.
	 * 
	 * @param billBankID
	 *            The billBankID to set
	 */
	public void setBillBankID(long billBankID) {
		this.billBankID = billBankID;
	}
	/**
	 * Sets the billNo.
	 * 
	 * @param billNo
	 *            The billNo to set
	 */
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	/**
	 * Sets the billTypeID.
	 * 
	 * @param billTypeID
	 *            The billTypeID to set
	 */
	public void setBillTypeID(long billTypeID) {
		this.billTypeID = billTypeID;
	}
	/**
	 * Sets the checkUserID.
	 * 
	 * @param checkUserID
	 *            The checkUserID to set
	 */
	public void setCheckUserID(long checkUserID) {
		this.checkUserID = checkUserID;
	}
	/**
	 * Sets the currencyID.
	 * 
	 * @param currencyID
	 *            The currencyID to set
	 */
	public void setCurrencyID(long currencyID) {
		this.currencyID = currencyID;
	}
	/**
	 * Sets the executeDate.
	 * 
	 * @param executeDate
	 *            The executeDate to set
	 */
	public void setExecuteDate(Timestamp executeDate) {
		this.executeDate = executeDate;
	}
	/**
	 * Sets the id.
	 * 
	 * @param id
	 *            The id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * Sets the inputUserID.
	 * 
	 * @param inputUserID
	 *            The inputUserID to set
	 */
	public void setInputUserID(long inputUserID) {
		this.inputUserID = inputUserID;
	}
	/**
	 * Sets the interestStartDate.
	 * 
	 * @param interestStartDate
	 *            The interestStartDate to set
	 */
	public void setInterestStartDate(Timestamp interestStartDate) {
		this.interestStartDate = interestStartDate;
	}
	/**
	 * Sets the officeID.
	 * 
	 * @param officeID
	 *            The officeID to set
	 */
	public void setOfficeID(long officeID) {
		this.officeID = officeID;
	}
	/**
	 * Sets the transactionTypeID.
	 * 
	 * @param transactionTypeID
	 *            The transactionTypeID to set
	 */
	public void setTransactionTypeID(long transactionTypeID) {
		this.transactionTypeID = transactionTypeID;
	}
	/**
	 * Sets the transNo.
	 * 
	 * @param transNo
	 *            The transNo to set
	 */
	public void setTransNo(String transNo) {
		this.transNo = transNo;
	}
	/**
	 * Returns the payAccountID.
	 * 
	 * @return long
	 */
	public long getPayAccountID() {
		return payAccountID;
	}
	/**
	 * Sets the payAccountID.
	 * 
	 * @param payAccountID
	 *            The payAccountID to set
	 */
	public void setPayAccountID(long payAccountID) {
		this.payAccountID = payAccountID;
	}
	/**
	 * Returns the amount.
	 * 
	 * @return double
	 */
	public double getAmount() {
		return amount;
	}
	/**
	 * Sets the amount.
	 * 
	 * @param amount
	 *            The amount to set
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}
	/**
	 * Returns the bankChequeNo.
	 * 
	 * @return String
	 */
	public String getBankChequeNo() {
		return bankChequeNo;
	}
	/**
	 * Returns the bankID.
	 * 
	 * @return long
	 */
	public long getBankID() {
		return bankID;
	}
	/**
	 * Returns the cashFlowID.
	 * 
	 * @return long
	 */
	public long getCashFlowID() {
		return CashFlowID;
	}
	/**
	 * Returns the checkAbstractStr.
	 * 
	 * @return String
	 */
	public String getCheckAbstractStr() {
		return checkAbstractStr;
	}
	/**
	 * Returns the confirmAbstractStr.
	 * 
	 * @return String
	 */
	public String getConfirmAbstractStr() {
		return confirmAbstractStr;
	}
	/**
	 * Returns the confirmOfficeID.
	 * 
	 * @return long
	 */
	public long getConfirmOfficeID() {
		return confirmOfficeID;
	}
	/**
	 * Returns the confirmUserID.
	 * 
	 * @return long
	 */
	public long getConfirmUserID() {
		return confirmUserID;
	}
	/**
	 * Returns the consignPassword.
	 * 
	 * @return String
	 */
	public String getConsignPassword() {
		return ConsignPassword;
	}
	/**
	 * Returns the consignReceiveTypeID.
	 * 
	 * @return long
	 */
	public long getConsignReceiveTypeID() {
		return consignReceiveTypeID;
	}
	/**
	 * Returns the consignVoucherNo.
	 * 
	 * @return String
	 */
	public String getConsignVoucherNo() {
		return ConsignVoucherNo;
	}
	/**
     * @return Returns the commissionAmount.
     */
    public double getCommissionAmount()
    {
        return commissionAmount;
    }
    /**
     * @param commissionAmount The commissionAmount to set.
     */
    public void setCommissionAmount(double commissionAmount)
    {
        this.commissionAmount = commissionAmount;
    }
    /**
     * @return Returns the commissionTransNo.
     */
    public String getCommissionTransNo()
    {
        return commissionTransNo;
    }
    /**
     * @param commissionTransNo The commissionTransNo to set.
     */
    public void setCommissionTransNo(String commissionTransNo)
    {
        this.commissionTransNo = commissionTransNo;
    }
    /**
     * @return Returns the commissionType.
     */
    public long getCommissionType()
    {
        return commissionType;
    }
    /**
     * @param commissionType The commissionType to set.
     */
    public void setCommissionType(long commissionType)
    {
        this.commissionType = commissionType;
    }
    /**
	 * Returns the declarationNo.
	 * 
	 * @return String
	 */
	public String getDeclarationNo() {
		return declarationNo;
	}
	/**
	 * Returns the extBankNo.
	 * 
	 * @return String
	 */
	public String getExtBankNo() {
		return extBankNo;
	}
	/**
	 * Returns the instructionNo.
	 * 
	 * @return String
	 */
	public String getInstructionNo() {
		return instructionNo;
	}
	/**
	 * Returns the modifyTime.
	 * 
	 * @return Timestamp
	 */
	public Timestamp getModifyTime() {
		return modifyTime;
	}
	/**
	 * Returns the receiveAccountID.
	 * 
	 * @return long
	 */
	public long getReceiveAccountID() {
		return receiveAccountID;
	}
	/**
	 * Returns the signUserID.
	 * 
	 * @return long
	 */
	public long getSignUserID() {
		return signUserID;
	}
	/**
	 * Returns the singleBankAccountTypeID.
	 * 
	 * @return long
	 */
	public long getSingleBankAccountTypeID() {
		return singleBankAccountTypeID;
	}
	/**
	 * Returns the statusID.
	 * 
	 * @return long
	 */
	public long getStatusID() {
		long lResult = -1;
		if (this.statusIDs != null && this.statusIDs.length > 0) {
			lResult = this.statusIDs[0];
		}
		return lResult;
	}
	/**
	 * 用于当前Entity作为查询条件时
	 * 
	 * @return long[]
	 */
	public long[] getStatusIDs() {
		return this.statusIDs;
	}
	/**
	 * Method getReceiveClientID.
	 * 
	 * @return long
	 */
	public long getReceiveClientID() {
		return this.receiveClientID;
	}
	/**
	 * Method getPayClientID.
	 * 
	 * @return long
	 */
	public long getPayClientID() {
		return this.payClientID;
	}
	/**
	 * Returns the abstractID.
	 * 
	 * @return long
	 */
	public void setAbstractID(long abstractID) {
		this.abstractID = abstractID;
	}
	/**
	 * Sets the bankChequeNo.
	 * 
	 * @param bankChequeNo
	 *            The bankChequeNo to set
	 */
	public void setBankChequeNo(String bankChequeNo) {
		this.bankChequeNo = bankChequeNo;
	}
	/**
	 * Sets the bankID.
	 * 
	 * @param bankID
	 *            The bankID to set
	 */
	public void setBankID(long bankID) {
		this.bankID = bankID;
	}
	/**
	 * Sets the cashFlowID.
	 * 
	 * @param cashFlowID
	 *            The cashFlowID to set
	 */
	public void setCashFlowID(long cashFlowID) {
		CashFlowID = cashFlowID;
	}
	/**
	 * Sets the checkAbstractStr.
	 * 
	 * @param checkAbstractStr
	 *            The checkAbstractStr to set
	 */
	public void setCheckAbstractStr(String checkAbstractStr) {
		this.checkAbstractStr = checkAbstractStr;
	}
	/**
	 * Sets the confirmAbstractStr.
	 * 
	 * @param confirmAbstractStr
	 *            The confirmAbstractStr to set
	 */
	public void setConfirmAbstractStr(String confirmAbstractStr) {
		this.confirmAbstractStr = confirmAbstractStr;
	}
	/**
	 * Sets the confirmOfficeID.
	 * 
	 * @param confirmOfficeID
	 *            The confirmOfficeID to set
	 */
	public void setConfirmOfficeID(long confirmOfficeID) {
		this.confirmOfficeID = confirmOfficeID;
	}
	/**
	 * Sets the confirmUserID.
	 * 
	 * @param confirmUserID
	 *            The confirmUserID to set
	 */
	public void setConfirmUserID(long confirmUserID) {
		this.confirmUserID = confirmUserID;
	}
	/**
	 * Sets the consignPassword.
	 * 
	 * @param consignPassword
	 *            The consignPassword to set
	 */
	public void setConsignPassword(String consignPassword) {
		ConsignPassword = consignPassword;
	}
	/**
	 * Sets the consignReceiveTypeID.
	 * 
	 * @param consignReceiveTypeID
	 *            The consignReceiveTypeID to set
	 */
	public void setConsignReceiveTypeID(long consignReceiveTypeID) {
		this.consignReceiveTypeID = consignReceiveTypeID;
	}
	/**
	 * Sets the consignVoucherNo.
	 * 
	 * @param consignVoucherNo
	 *            The consignVoucherNo to set
	 */
	public void setConsignVoucherNo(String consignVoucherNo) {
		ConsignVoucherNo = consignVoucherNo;
	}
	/**
	 * Sets the declarationNo.
	 * 
	 * @param declarationNo
	 *            The declarationNo to set
	 */
	public void setDeclarationNo(String declarationNo) {
		this.declarationNo = declarationNo;
	}
	/**
	 * Sets the extBankNo.
	 * 
	 * @param extBankNo
	 *            The extBankNo to set
	 */
	public void setExtBankNo(String extBankNo) {
		this.extBankNo = extBankNo;
	}
	/**
	 * Sets the instructionNo.
	 * 
	 * @param instructionNo
	 *            The instructionNo to set
	 */
	public void setInstructionNo(String instructionNo) {
		this.instructionNo = instructionNo;
	}
	/**
	 * Sets the modifyTime.
	 * 
	 * @param modifyTime
	 *            The modifyTime to set
	 */
	public void setModifyTime(Timestamp modifyTime) {
		this.modifyTime = modifyTime;
	}
	/**
	 * Sets the receiveAccountID.
	 * 
	 * @param receiveAccountID
	 *            The receiveAccountID to set
	 */
	public void setReceiveAccountID(long receiveAccountID) {
		this.receiveAccountID = receiveAccountID;
	}
	/**
	 * Sets the signUserID.
	 * 
	 * @param signUserID
	 *            The signUserID to set
	 */
	public void setSignUserID(long signUserID) {
		this.signUserID = signUserID;
	}
	/**
	 * Sets the singleBankAccountTypeID.
	 * 
	 * @param singleBankAccountTypeID
	 *            The singleBankAccountTypeID to set
	 */
	public void setSingleBankAccountTypeID(long singleBankAccountTypeID) {
		this.singleBankAccountTypeID = singleBankAccountTypeID;
	}
	/**
	 * Sets the statusID.
	 * 
	 * @param statusID
	 *            The statusID to set
	 */
	public void setStatusID(long statusID) {
		this.statusIDs = new long[]{statusID};
	}
	/**
	 * 用于当前Entity作为查询条件时
	 * 
	 * @param statusIDs
	 */
	public void setStatusIDs(long[] statusIDs) {
		this.statusIDs = statusIDs;
	}
	/**
	 * Method setReceiveClientID.
	 * 
	 * @return void
	 */
	public void setReceiveClientID(long receiveClientID) {
		this.receiveClientID = receiveClientID;
	}
	/**
	 * Method setPayClientID.
	 * 
	 * @return void
	 */
	public void setPayClientID(long payClientID) {
		this.payClientID = payClientID;
	}
	////////////////////////End of Getters and
	// Setters////////////////////////////////////
	/**
	 * Method isNeedMatch Descption: check input input field wheather need to
	 * Match as a condition
	 * 
	 * @return boolean
	 */
	public boolean isNeedMatch(long stransactionType, String fieldName) {
		//statusID is awlays a matched filed in match operation
		if (fieldName.compareToIgnoreCase("nStatusID") == 0
				|| fieldName.compareToIgnoreCase("nTransactionTypeID") == 0
				|| fieldName.compareToIgnoreCase("nOfficeID") == 0
				|| fieldName.compareToIgnoreCase("nCurrencyID") == 0
				|| fieldName.compareToIgnoreCase("nInputUserID") == 0)
			return true;
		switch ((int) stransactionType) {
			case (int) SETTConstant.TransactionType.DRAFTPAY :
				{
						if (fieldName.compareToIgnoreCase("nPayAccountID") == 0
								|| fieldName
										.compareToIgnoreCase("nPayClientID") == 0
								|| fieldName.compareToIgnoreCase("nBankID") == 0
								|| fieldName.compareToIgnoreCase("mAmount") == 0
								|| fieldName
										.compareToIgnoreCase("dtInterestStart") == 0
								|| fieldName.compareToIgnoreCase("nBillBankID") == 0
								|| fieldName.compareToIgnoreCase("sBillNo") == 0
								|| fieldName.compareToIgnoreCase("nBillTypeID") == 0) {
							return true;
						}
				}
			case (int) SETTConstant.TransactionType.BANKPAY :
			{
				if (fieldName.compareToIgnoreCase("nPayAccountID") == 0
						|| fieldName
								.compareToIgnoreCase("nPayClientID") == 0
						|| fieldName.compareToIgnoreCase("strExtAccountNo") == 0
						|| fieldName.compareToIgnoreCase("mAmount") == 0
						|| fieldName.compareToIgnoreCase("nCashFlowID") == 0
						|| fieldName
								.compareToIgnoreCase("dtInterestStart") == 0

						|| fieldName.compareToIgnoreCase("strExtClientName") == 0
						|| fieldName.compareToIgnoreCase("sDeclarationNo")== 0
						|| fieldName.compareToIgnoreCase("nBankID")== 0) {
					return true;
				}
			}		
				break;
			case (int) SETTConstant.TransactionType.BANKPAY_NOTONLINE :
			{
				if (fieldName.compareToIgnoreCase("nPayAccountID") == 0
						|| fieldName
								.compareToIgnoreCase("nPayClientID") == 0
						|| fieldName.compareToIgnoreCase("strExtAccountNo") == 0
						|| fieldName.compareToIgnoreCase("mAmount") == 0
						//|| fieldName.compareToIgnoreCase("nCashFlowID") == 0
						|| fieldName
								.compareToIgnoreCase("dtInterestStart") == 0

						|| fieldName.compareToIgnoreCase("strExtClientName") == 0
						|| fieldName.compareToIgnoreCase("sDeclarationNo")== 0) {
					return true;
				}
			}		
				break;
			case (int) SETTConstant.TransactionType.BANKPAY_DOWNTRANSFER :
			{
				if (fieldName.compareToIgnoreCase("nPayAccountID") == 0
						|| fieldName
								.compareToIgnoreCase("nPayClientID") == 0
						|| fieldName.compareToIgnoreCase("strExtAccountNo") == 0
						|| fieldName.compareToIgnoreCase("mAmount") == 0
						|| fieldName.compareToIgnoreCase("nCashFlowID") == 0
						|| fieldName
								.compareToIgnoreCase("dtInterestStart") == 0
						|| fieldName.compareToIgnoreCase("sDeclarationNo")== 0) {
					return true;
				}
			}		
				break;
            case (int) SETTConstant.TransactionType.BANKPAY_FINCOMPANYPAY :
                {
                        if (fieldName.compareToIgnoreCase("nPayAccountID") == 0
                                || fieldName
                                        .compareToIgnoreCase("nPayClientID") == 0
                                || fieldName.compareToIgnoreCase("nBankID") == 0
                                || fieldName.compareToIgnoreCase("mAmount") == 0
                                || fieldName.compareToIgnoreCase("nCashFlowID") == 0
                                || fieldName
                                        .compareToIgnoreCase("dtInterestStart") == 0
                                || fieldName.compareToIgnoreCase("sDeclarationNo")== 0) {
                            return true;
                        }
                }
                break;
            case (int) SETTConstant.TransactionType.BANKPAY_PAYSUBACCOUNT :
                {
                        if (fieldName.compareToIgnoreCase("nPayAccountID") == 0
                                || fieldName
                                        .compareToIgnoreCase("nPayClientID") == 0
                                || fieldName.compareToIgnoreCase("nBankID") == 0
                                || fieldName.compareToIgnoreCase("mAmount") == 0
                                || fieldName.compareToIgnoreCase("nCashFlowID") == 0
                                || fieldName
                                        .compareToIgnoreCase("dtInterestStart") == 0
                                || fieldName.compareToIgnoreCase("sDeclarationNo")== 0) {
                            return true;
                        }
                }
                break;
			case (int) SETTConstant.TransactionType.INTERNALVIREMENT :
				{
						if (fieldName.compareToIgnoreCase("nPayAccountID") == 0
								|| fieldName
										.compareToIgnoreCase("nPayClientID") == 0
								|| fieldName
										.compareToIgnoreCase("nReceiveAccountID") == 0
								|| fieldName
										.compareToIgnoreCase("nReceiveClientID") == 0
								|| fieldName.compareToIgnoreCase("mAmount") == 0
								|| fieldName
										.compareToIgnoreCase("dtInterestStart") == 0
								|| fieldName.compareToIgnoreCase("sDeclarationNo")== 0) {
							return true;
						}
				}
				break;
			case (int) SETTConstant.TransactionType.BANKRECEIVE :
				{
						if (fieldName.compareToIgnoreCase("nReceiveAccountID") == 0
								|| fieldName
										.compareToIgnoreCase("nReceiveClientID") == 0
								|| fieldName.compareToIgnoreCase("nBankID") == 0
								|| fieldName
										.compareToIgnoreCase("sDeclarationNo") == 0
								|| fieldName.compareToIgnoreCase("mAmount") == 0
								|| fieldName
										.compareToIgnoreCase("dtInterestStart") == 0
                                || fieldName.compareToIgnoreCase("sDeclarationNo")== 0) {
							return true;
						}
				}
				break;
            case (int) SETTConstant.TransactionType.BANKRECEIVE_GATHERING :
                {
                        if (fieldName.compareToIgnoreCase("nReceiveAccountID") == 0
                                || fieldName
                                        .compareToIgnoreCase("nReceiveClientID") == 0
                                || fieldName.compareToIgnoreCase("nBankID") == 0
                                || fieldName
                                        .compareToIgnoreCase("sDeclarationNo") == 0
                                || fieldName.compareToIgnoreCase("mAmount") == 0
                                || fieldName
                                        .compareToIgnoreCase("dtInterestStart") == 0
                                || fieldName.compareToIgnoreCase("sDeclarationNo")== 0) {
                            return true;
                        }
                }
                break;
            case (int) SETTConstant.TransactionType.BANKRECEIVE_SUBCLIENT :
                {
                        if (fieldName.compareToIgnoreCase("nReceiveAccountID") == 0
                                || fieldName
                                        .compareToIgnoreCase("nReceiveClientID") == 0
                                || fieldName.compareToIgnoreCase("nBankID") == 0
                                || fieldName
                                        .compareToIgnoreCase("sDeclarationNo") == 0
                                || fieldName.compareToIgnoreCase("mAmount") == 0
                                || fieldName
                                        .compareToIgnoreCase("dtInterestStart") == 0
                                || fieldName.compareToIgnoreCase("sDeclarationNo")== 0) {
                            return true;
                        }
                }
                break;
            case (int) SETTConstant.TransactionType.BANKRECEIVE_TOSUBCLIENT :
                {
                        if (fieldName.compareToIgnoreCase("nReceiveAccountID") == 0
                                || fieldName
                                        .compareToIgnoreCase("nReceiveClientID") == 0
                                || fieldName.compareToIgnoreCase("nBankID") == 0
                                || fieldName
                                        .compareToIgnoreCase("sDeclarationNo") == 0
                                || fieldName.compareToIgnoreCase("mAmount") == 0
                                || fieldName
                                        .compareToIgnoreCase("dtInterestStart") == 0
                                || fieldName.compareToIgnoreCase("sDeclarationNo")== 0) {
                            return true;
                        }
                }
                break;
			case (int) SETTConstant.TransactionType.SUBCLIENT_BANKPAY :
				{
						if (fieldName.compareToIgnoreCase("nPayAccountID") == 0
								|| fieldName
										.compareToIgnoreCase("nPayClientID") == 0
								|| fieldName.compareToIgnoreCase("nBankID") == 0
								|| fieldName.compareToIgnoreCase("mAmount") == 0
								|| fieldName.compareToIgnoreCase("nCashFlowID") == 0
								|| fieldName
										.compareToIgnoreCase("dtInterestStart") == 0
								|| fieldName
										.compareToIgnoreCase("nSubClientID") == 0) {
							return true;
						}
				}
				break;
			case (int) SETTConstant.TransactionType.SUBCLIENT_BANKRECEIVE :
				{
						if (fieldName.compareToIgnoreCase("nReceiveAccountID") == 0
								|| fieldName
										.compareToIgnoreCase("nReceiveClientID") == 0
								|| fieldName.compareToIgnoreCase("nBankID") == 0
								|| fieldName
										.compareToIgnoreCase("sDeclarationNo") == 0
								|| fieldName.compareToIgnoreCase("mAmount") == 0
								|| fieldName
										.compareToIgnoreCase("dtInterestStart") == 0
								|| fieldName
										.compareToIgnoreCase("nSubClientID") == 0) {
							return true;
						}
				}
				break;
			case (int) SETTConstant.TransactionType.CASHRECEIVE :
				{
						if (fieldName.compareToIgnoreCase("nReceiveAccountID") == 0
								|| fieldName
										.compareToIgnoreCase("nReceiveClientID") == 0
								|| fieldName.compareToIgnoreCase("nBankID") == 0
								|| fieldName
										.compareToIgnoreCase("sDeclarationNo") == 0
								|| fieldName.compareToIgnoreCase("mAmount") == 0
								|| fieldName
										.compareToIgnoreCase("dtInterestStart") == 0) {
							return true;
						}
				}
				break;
			case (int) SETTConstant.TransactionType.CASHPAY :
				{
						if (fieldName.compareToIgnoreCase("nPayAccountID") == 0
								|| fieldName
										.compareToIgnoreCase("nPayClientID") == 0
								|| fieldName.compareToIgnoreCase("nBankID") == 0
								|| fieldName.compareToIgnoreCase("mAmount") == 0
								|| fieldName
										.compareToIgnoreCase("dtInterestStart") == 0
								|| fieldName.compareToIgnoreCase("nBillBankID") == 0
								|| fieldName.compareToIgnoreCase("sBillNo") == 0
								|| fieldName.compareToIgnoreCase("nBillTypeID") == 0) {
							return true;
						}
				}
				break;
			case (int) SETTConstant.TransactionType.CHECKPAY :
				{
						if (fieldName.compareToIgnoreCase("nPayAccountID") == 0
								|| fieldName
										.compareToIgnoreCase("nPayClientID") == 0
								|| fieldName.compareToIgnoreCase("nBankID") == 0
								|| fieldName.compareToIgnoreCase("mAmount") == 0
								|| fieldName
										.compareToIgnoreCase("dtInterestStart") == 0
								|| fieldName.compareToIgnoreCase("nBillBankID") == 0
								|| fieldName.compareToIgnoreCase("sBillNo") == 0
								|| fieldName.compareToIgnoreCase("nBillTypeID") == 0) {
							return true;
						}
				}
				break;
			case (int) SETTConstant.TransactionType.OTHERPAY :
				{
						if (fieldName.compareToIgnoreCase("nPayAccountID") == 0
								|| fieldName
										.compareToIgnoreCase("nPayClientID") == 0
								|| fieldName.compareToIgnoreCase("nBankID") == 0
								|| fieldName.compareToIgnoreCase("mAmount") == 0
								|| fieldName
										.compareToIgnoreCase("dtInterestStart") == 0) {
							return true;
						}
				}
				break;
			case (int) SETTConstant.TransactionType.CONSIGNSAVE :
				{
						if (fieldName.compareToIgnoreCase("nPayAccountID") == 0
								|| fieldName
										.compareToIgnoreCase("nPayClientID") == 0
								|| fieldName
										.compareToIgnoreCase("nReceiveAccountID") == 0
								|| fieldName
										.compareToIgnoreCase("nReceiveClientID") == 0
								|| fieldName.compareToIgnoreCase("nBankID") == 0
								|| fieldName.compareToIgnoreCase("mAmount") == 0
								|| fieldName
										.compareToIgnoreCase("dtInterestStart") == 0) {
							return true;
						}
				}
				break;
			case (int) SETTConstant.TransactionType.CAUTIONMONEYSAVE :
				{
						if (fieldName.compareToIgnoreCase("nPayAccountID") == 0
								|| fieldName
										.compareToIgnoreCase("nPayClientID") == 0
								|| fieldName
										.compareToIgnoreCase("nReceiveAccountID") == 0
								|| fieldName
										.compareToIgnoreCase("nReceiveClientID") == 0
								|| fieldName.compareToIgnoreCase("nBankID") == 0
								|| fieldName.compareToIgnoreCase("mAmount") == 0
								|| fieldName
										.compareToIgnoreCase("dtInterestStart") == 0) {
							return true;
						}
				}
				break;
//				此处为海尔添加
			case (int)SETTConstant.TransactionType. CHECK_RECEIVE:   //支票收款
			{
					if (fieldName.compareToIgnoreCase("nReceiveAccountID") == 0
							|| fieldName
									.compareToIgnoreCase("nReceiveClientID") == 0
							|| fieldName.compareToIgnoreCase("nBankID") == 0
							|| fieldName
									.compareToIgnoreCase("sDeclarationNo") == 0
							|| fieldName.compareToIgnoreCase("mAmount") == 0
							|| fieldName
									.compareToIgnoreCase("dtInterestStart") == 0) {
						return true;
					}
				break;
			}
			case (int)SETTConstant.TransactionType. REMIT_RECEIVE:   //汇款收款
			{
					if (fieldName.compareToIgnoreCase("nReceiveAccountID") == 0
							|| fieldName
									.compareToIgnoreCase("nReceiveClientID") == 0
							|| fieldName.compareToIgnoreCase("nBankID") == 0
							|| fieldName
									.compareToIgnoreCase("sDeclarationNo") == 0
							|| fieldName.compareToIgnoreCase("mAmount") == 0
							|| fieldName
									.compareToIgnoreCase("dtInterestStart") == 0) {
						return true;
					}
	
				break;
			}
			case (int)SETTConstant.TransactionType. OTHER_RECEIVE:   //其它收款
			{
				
					if (fieldName.compareToIgnoreCase("nReceiveAccountID") == 0
							|| fieldName
									.compareToIgnoreCase("nReceiveClientID") == 0
							|| fieldName.compareToIgnoreCase("nBankID") == 0
							|| fieldName
									.compareToIgnoreCase("sDeclarationNo") == 0
							|| fieldName.compareToIgnoreCase("mAmount") == 0
							|| fieldName
									.compareToIgnoreCase("dtInterestStart") == 0) {
						return true;
					}

				break;
			}
			case (int)SETTConstant.TransactionType. CHECK_PAY:    //支票付款
			{
					if (fieldName.compareToIgnoreCase("nPayAccountID") == 0
							|| fieldName
									.compareToIgnoreCase("nPayClientID") == 0
							|| fieldName.compareToIgnoreCase("nBankID") == 0
							|| fieldName.compareToIgnoreCase("mAmount") == 0
							|| fieldName
									.compareToIgnoreCase("dtInterestStart") == 0
							|| fieldName.compareToIgnoreCase("nBillBankID") == 0
							|| fieldName.compareToIgnoreCase("sBillNo") == 0
							|| fieldName.compareToIgnoreCase("nBillTypeID") == 0) {
						return true;
					}
				break;
			}
			case (int)SETTConstant.TransactionType. REMIT_PAY:   //汇款付款
			{
					if (fieldName.compareToIgnoreCase("nPayAccountID") == 0
							|| fieldName
									.compareToIgnoreCase("nPayClientID") == 0
							|| fieldName.compareToIgnoreCase("nBankID") == 0
							|| fieldName.compareToIgnoreCase("mAmount") == 0
							|| fieldName
									.compareToIgnoreCase("dtInterestStart") == 0
							|| fieldName.compareToIgnoreCase("nBillBankID") == 0
							|| fieldName.compareToIgnoreCase("sBillNo") == 0
							|| fieldName.compareToIgnoreCase("nBillTypeID") == 0
							|| fieldName.compareToIgnoreCase("sDeclarationNo")==0) {
						return true;
					}
				break;
			}
			case (int)SETTConstant.TransactionType. TAX_PAY:   //税单付款
			{
					if (fieldName.compareToIgnoreCase("nPayAccountID") == 0
							|| fieldName
									.compareToIgnoreCase("nPayClientID") == 0
							|| fieldName.compareToIgnoreCase("nBankID") == 0
							|| fieldName.compareToIgnoreCase("mAmount") == 0
							|| fieldName
									.compareToIgnoreCase("dtInterestStart") == 0
							|| fieldName.compareToIgnoreCase("nBillBankID") == 0
							|| fieldName.compareToIgnoreCase("sBillNo") == 0
							|| fieldName.compareToIgnoreCase("nBillTypeID") == 0
							|| fieldName.compareToIgnoreCase("sDeclarationNo")==0) {
						return true;
					}
	
				break;
			}
			case (int)SETTConstant.TransactionType. OTHER_PAY:  //其它付款
			{
					if (fieldName.compareToIgnoreCase("nPayAccountID") == 0
							|| fieldName
									.compareToIgnoreCase("nPayClientID") == 0
							|| fieldName.compareToIgnoreCase("nBankID") == 0
							|| fieldName.compareToIgnoreCase("mAmount") == 0
							|| fieldName
									.compareToIgnoreCase("dtInterestStart") == 0
							|| fieldName.compareToIgnoreCase("nBillBankID") == 0
							|| fieldName.compareToIgnoreCase("sBillNo") == 0
							|| fieldName.compareToIgnoreCase("nBillTypeID") == 0
							|| fieldName.compareToIgnoreCase("sDeclarationNo")==0) {
						return true;
					}
				
				break;
			}
			
			//Please add other Transaction Types as follow
			default :
				return false;
		}
		return false;
	}
	/**
	 * Returns the abstractID.
	 * 
	 * @return long
	 */
	public long getAbstractID() {
		return abstractID;
	}
	/**
	 * Returns the extAccountNo.
	 * 
	 * @return String
	 */
	public String getExtAccountNo() {
		return extAccountNo;
	}
	/**
	 * Returns the extClientName.
	 * 
	 * @return String
	 */
	public String getExtClientName() {
		return extClientName;
	}
	/**
	 * Returns the remitInBank.
	 * 
	 * @return String
	 */
	public String getRemitInBank() {
		return remitInBank;
	}
	/**
	 * Returns the remitInCity.
	 * 
	 * @return String
	 */
	public String getRemitInCity() {
		return remitInCity;
	}
	/**
	 * Returns the remitInProvince.
	 * 
	 * @return String
	 */
	public String getRemitInProvince() {
		return remitInProvince;
	}
	/**
	 * Sets the extAccountNo.
	 * 
	 * @param extAccountNo
	 *            The extAccountNo to set
	 */
	public void setExtAccountNo(String extAccountNo) {
		this.extAccountNo = extAccountNo;
	}
	/**
	 * Sets the extClientName.
	 * 
	 * @param extClientName
	 *            The extClientName to set
	 */
	public void setExtClientName(String extClientName) {
		this.extClientName = extClientName;
	}
	/**
	 * Sets the remitInBank.
	 * 
	 * @param remitInBank
	 *            The remitInBank to set
	 */
	public void setRemitInBank(String remitInBank) {
		this.remitInBank = remitInBank!=null?remitInBank.trim():remitInBank;
	}
	/**
	 * Sets the remitInCity.
	 * 
	 * @param remitInCity
	 *            The remitInCity to set
	 */
	public void setRemitInCity(String remitInCity) {
		this.remitInCity = remitInCity;
	}
	/**
	 * Sets the remitInProvince.
	 * 
	 * @param remitInProvince
	 *            The remitInProvince to set
	 */
	public void setRemitInProvince(String remitInProvince) {
		this.remitInProvince = remitInProvince;
	}
	/**
	 * perSave check whether there is repeated tran record for different
	 * transaction because different transaction has different check condition.
	 */
	public TransCurrentDepositInfo getQeureyInfo() {
		TransCurrentDepositInfo queryInfo = new TransCurrentDepositInfo();
		int transType = (int) getTransactionTypeID();

		//按照不同业务类型校验重复交易
		queryInfo.setTransactionTypeID(transType);
		//重复交易校验执行日
		queryInfo.setExecuteDate(executeDate);
		//重复交易校验的交易状态为除过删除状态的所有有效交易
		queryInfo.setStatusIDs(new long[]{SETTConstant.TransactionStatus.CHECK,
				SETTConstant.TransactionStatus.CIRCLE,
				SETTConstant.TransactionStatus.CONFIRM,
				SETTConstant.TransactionStatus.NOTSIGN,
				SETTConstant.TransactionStatus.SAVE,
				SETTConstant.TransactionStatus.SIGN,
				SETTConstant.TransactionStatus.TEMPSAVE});
		switch (transType) {
			case (int) SETTConstant.TransactionType.BANKPAY:
				
				{
					queryInfo.setPayAccountID(payAccountID);
					queryInfo.setBankID(bankID);
					queryInfo.setAmount(amount);
					queryInfo.setDeclarationNo(declarationNo);
					break;
				}
			case (int) SETTConstant.TransactionType.BANKPAY_DOWNTRANSFER:
				{
					queryInfo.setPayAccountID(payAccountID);
					queryInfo.setBankID(bankID);
					queryInfo.setAmount(amount);
					queryInfo.setDeclarationNo(declarationNo);
					break;
				}
                case (int) SETTConstant.TransactionType.BANKPAY_FINCOMPANYPAY :
                {
                    queryInfo.setPayAccountID(payAccountID);
                    queryInfo.setBankID(bankID);
                    queryInfo.setAmount(amount);
					queryInfo.setDeclarationNo(declarationNo);
                    break;
                }
                case (int) SETTConstant.TransactionType.BANKPAY_PAYSUBACCOUNT :
                {
                    queryInfo.setPayAccountID(payAccountID);
                    queryInfo.setBankID(bankID);
                    queryInfo.setAmount(amount);
					queryInfo.setDeclarationNo(declarationNo);
                    break;
                }
			case (int) SETTConstant.TransactionType.SUBCLIENT_BANKPAY :
				{
					queryInfo.setPayAccountID(payAccountID);
					queryInfo.setBankID(bankID);
					queryInfo.setAmount(amount);
					queryInfo.setDeclarationNo(declarationNo);
					break;
				}
			case (int) SETTConstant.TransactionType.DRAFTPAY :
				{
					queryInfo.setPayAccountID(payAccountID);
					queryInfo.setBankID(bankID);
					queryInfo.setAmount(amount);
					queryInfo.setDeclarationNo(declarationNo);
					break;
				}
			case (int) SETTConstant.TransactionType.INTERNALVIREMENT :
				{
					queryInfo.setPayAccountID(payAccountID);
					queryInfo.setReceiveAccountID(receiveAccountID);
					queryInfo.setAmount(amount);
					queryInfo.setDeclarationNo(declarationNo);
					break;
				}
			case (int) SETTConstant.TransactionType.BANKRECEIVE :
				{
					queryInfo.setReceiveAccountID(receiveAccountID);
					queryInfo.setAmount(amount);
					queryInfo.setDeclarationNo(declarationNo);
					break;
				}
                case (int) SETTConstant.TransactionType.BANKRECEIVE_GATHERING :
                {
                    queryInfo.setReceiveAccountID(receiveAccountID);
                    queryInfo.setAmount(amount);
					queryInfo.setDeclarationNo(declarationNo);
                    break;
                }
                case (int) SETTConstant.TransactionType.BANKRECEIVE_SUBCLIENT :
                {
                    queryInfo.setReceiveAccountID(receiveAccountID);
                    queryInfo.setAmount(amount);
					queryInfo.setDeclarationNo(declarationNo);
                    break;
                }
                case (int) SETTConstant.TransactionType.BANKRECEIVE_TOSUBCLIENT :
                {
                    queryInfo.setReceiveAccountID(receiveAccountID);
                    queryInfo.setAmount(amount);
					queryInfo.setDeclarationNo(declarationNo);
                    break;
                }
			case (int) SETTConstant.TransactionType.SUBCLIENT_BANKRECEIVE :
				{
					queryInfo.setReceiveAccountID(receiveAccountID);
					queryInfo.setAmount(amount);
					queryInfo.setDeclarationNo(declarationNo);
					break;
				}
			case (int) SETTConstant.TransactionType.CASHRECEIVE :
				{
					queryInfo.setReceiveAccountID(receiveAccountID);
					queryInfo.setAmount(amount);
					queryInfo.setDeclarationNo(declarationNo);
					break;
				}
			case (int) SETTConstant.TransactionType.CASHPAY :
				{
					queryInfo.setPayAccountID(payAccountID);
					queryInfo.setBankID(bankID);
					queryInfo.setAmount(amount);
					queryInfo.setDeclarationNo(declarationNo);
					break;
				}
			case (int) SETTConstant.TransactionType.CHECKPAY :
				{
					queryInfo.setPayAccountID(payAccountID);
					queryInfo.setBankID(bankID);
					queryInfo.setAmount(amount);
					queryInfo.setDeclarationNo(declarationNo);
					break;
				}
			case (int) SETTConstant.TransactionType.OTHERPAY :
				{
					queryInfo.setPayAccountID(payAccountID);
					queryInfo.setBankID(bankID);
					queryInfo.setAmount(amount);
					queryInfo.setDeclarationNo(declarationNo);
					break;
				}
			case (int) SETTConstant.TransactionType.CONSIGNSAVE :
				{
					queryInfo.setPayAccountID(payAccountID);
					queryInfo.setReceiveAccountID(receiveAccountID);
					queryInfo.setBankID(bankID);
					queryInfo.setAmount(amount);
					queryInfo.setDeclarationNo(declarationNo);
					break;
				}
			case (int) SETTConstant.TransactionType.CAUTIONMONEYSAVE :
				{
					queryInfo.setPayAccountID(payAccountID);
					queryInfo.setReceiveAccountID(receiveAccountID);
					queryInfo.setBankID(bankID);
					queryInfo.setAmount(amount);
					queryInfo.setDeclarationNo(declarationNo);
					break;
				}
			//liuguang增加
			case (int) SETTConstant.TransactionType.BANKPAY_NOTONLINE : // 银行付款落地处理
			{
				queryInfo.setPayClientID(payClientID);
				queryInfo.setPayAccountID(payAccountID);
				queryInfo.setReceiveAccountID(receiveAccountID);
				queryInfo.setBankID(bankID);
				queryInfo.setAmount(amount);
				queryInfo.setBankChequeNo(bankChequeNo);
				break;
			}
			//此处为海尔添加
			case (int)SETTConstant.TransactionType.CHECK_RECEIVE:   //支票收款
			{
				queryInfo.setReceiveAccountID(receiveAccountID);
				queryInfo.setAmount(amount);
				break;
			}
			case (int)SETTConstant.TransactionType.REMIT_RECEIVE:   //汇款收款
			{
				queryInfo.setReceiveAccountID(receiveAccountID);
				queryInfo.setAmount(amount);
				break;
			}
			case (int)SETTConstant.TransactionType.OTHER_RECEIVE:   //其它收款
			{
				queryInfo.setReceiveAccountID(receiveAccountID);
				queryInfo.setAmount(amount);
				break;
			}
			case (int)SETTConstant.TransactionType.CHECK_PAY:   //支票收款
			{
				
				queryInfo.setPayAccountID(payAccountID);
				queryInfo.setBankID(bankID);
				queryInfo.setAmount(amount);
				break;
			}
			case (int)SETTConstant.TransactionType.REMIT_PAY:   //汇款付款
			{
				queryInfo.setPayAccountID(payAccountID);
				queryInfo.setBankID(bankID);
				queryInfo.setAmount(amount);
				break;
			}
			case (int)SETTConstant.TransactionType.TAX_PAY:   //税单付款
			{
				queryInfo.setPayAccountID(payAccountID);
				queryInfo.setBankID(bankID);
				queryInfo.setAmount(amount);
				break;
			}
			case (int)SETTConstant.TransactionType.OTHER_PAY:  //其它付款
			{
				queryInfo.setPayAccountID(payAccountID);
				queryInfo.setBankID(bankID);
				queryInfo.setAmount(amount);
				break;
			}
		}
		
		//@TBD: we will add other trans type at here
		return queryInfo;
	}
	public ExternalAccountInfo getExternalAccountInfo() {
		ExternalAccountInfo extAccountInfo = new ExternalAccountInfo();
		//No id will be set because id will be gotten from external
		extAccountInfo.setOfficeID(officeID);
		extAccountInfo.setExtAcctNo(extAccountNo);
		extAccountInfo.setExtAcctName(extClientName);
		extAccountInfo.setBankName(remitInBank);
		extAccountInfo.setProvince(remitInProvince);
		extAccountInfo.setCity(remitInCity);
		extAccountInfo.setNcurrencyID(currencyID);
		extAccountInfo.setSpayeebankcnapsno(spayeebankcnapsno);
		extAccountInfo.setSpayeebankexchangeno(spayeebankexchangeno);
		extAccountInfo.setSpayeebankorgno(spayeebankorgno);
		return extAccountInfo;
	}
	/**
	 * Returns the inputDate.
	 * 
	 * @return Timestamp
	 */
	public Timestamp getInputDate() {
		return inputDate;
	}
	/**
	 * Sets the inputDate.
	 * 
	 * @param inputDate
	 *            The inputDate to set
	 */
	public void setInputDate(Timestamp inputDate) {
		this.inputDate = inputDate;
	}
	/**
	 * Returns the isPaymentBank.
	 * 当前只在活期交易中判断是否生成银行指令中使用
	 * @return boolean 必须在设定交易类型后使用
	 */
	public boolean isPaymentBank() {
		if (transactionTypeID == SETTConstant.TransactionType.BANKPAY
				|| transactionTypeID == SETTConstant.TransactionType.SUBCLIENT_BANKPAY)
			return true;
		else
			return false;
	}
	/**
	 * @return Returns the subClientID.
	 */
	public long getSubClientID() {
		return SubClientID;
	}
	/**
	 * @param subClientID
	 *            The subClientID to set.
	 */
	public void setSubClientID(long subClientID) {
		SubClientID = subClientID;
	}
	/**
	 * @param isPaymentBank
	 *            The isPaymentBank to set.
	 */
	public void setPaymentBank(boolean isPaymentBank) {
		this.isPaymentBank = isPaymentBank;
	}
	/**
	 * @return Returns the reckoningTypeID.
	 */
	public long getReckoningTypeID()
	{
		return ReckoningTypeID;
	}
	/**
	 * @param reckoningTypeID The reckoningTypeID to set.
	 */
	public void setReckoningTypeID(long reckoningTypeID)
	{
		ReckoningTypeID = reckoningTypeID;
	}
	/**
	 * @return Returns the reckoningBillTypeDesc.
	 */
	public String getReckoningBillTypeDesc()
	{
		return ReckoningBillTypeDesc;
	}
	/**
	 * @param reckoningBillTypeDesc The reckoningBillTypeDesc to set.
	 */
	public void setReckoningBillTypeDesc(String reckoningBillTypeDesc)
	{
		ReckoningBillTypeDesc = reckoningBillTypeDesc;
	}
	/**
	 * @return Returns the autoCreateBankInstruction.
	 */
	public boolean isAutoCreateBankInstruction()
	{
		return AutoCreateBankInstruction;
	}
	/**
	 * @param autoCreateBankInstruction The autoCreateBankInstruction to set.
	 */
	public void setAutoCreateBankInstruction(boolean autoCreateBankInstruction)
	{
		AutoCreateBankInstruction = autoCreateBankInstruction;
	}
	/**
	 * @return Returns the budgetItemID.
	 */
	public long getBudgetItemID() {
		return BudgetItemID;
	}
	/**
	 * @param budgetItemID The budgetItemID to set.
	 */
	public void setBudgetItemID(long budgetItemID) {
		BudgetItemID = budgetItemID;
	}
	public long getIsUrgent() {
		return isUrgent;
	}
	public void setIsUrgent(long isUrgent) {
		this.isUrgent = isUrgent;
	}
	public boolean isAutoDelBankInstruction() {
		return AutoDelBankInstruction;
	}
	public void setAutoDelBankInstruction(boolean autoDelBankInstruction) {
		AutoDelBankInstruction = autoDelBankInstruction;
	}
	public String getSpayeebankexchangeno() {
		return spayeebankexchangeno;
	}
	public void setSpayeebankexchangeno(String spayeebankexchangeno) {
		this.spayeebankexchangeno = spayeebankexchangeno;
	}
	public String getSpayeebankcnapsno() {
		return spayeebankcnapsno;
	}
	public void setSpayeebankcnapsno(String spayeebankcnapsno) {
		this.spayeebankcnapsno = spayeebankcnapsno;
	}
	public String getSpayeebankorgno() {
		return spayeebankorgno;
	}
	public void setSpayeebankorgno(String spayeebankorgno) {
		this.spayeebankorgno = spayeebankorgno;
	}
	public String getSApplyCode() {
		return sApplyCode;
	}
	public void setSApplyCode(String applyCode) {
		sApplyCode = applyCode;
	}
	public long getLSource() {
		return lSource;
	}
	public void setLSource(long source) {
		lSource = source;
	}
	public long getIsDifOffice() {
		return IsDifOffice;
	}
	public void setIsDifOffice(long isDifOffice) {
		IsDifOffice = isDifOffice;
	}
	public long getPayOfficeID() {
		return PayOfficeID;
	}
	public void setPayOfficeID(long payOfficeID) {
		PayOfficeID = payOfficeID;
	}
	public long getReceiveOfficeID() {
		return ReceiveOfficeID;
	}
	public void setReceiveOfficeID(long receiveOfficeID) {
		ReceiveOfficeID = receiveOfficeID;
	}
	public long getParentOfficeID() {
		return ParentOfficeID;
	}
	public void setParentOfficeID(long parentOfficeID) {
		ParentOfficeID = parentOfficeID;
	}
	 
	
}
