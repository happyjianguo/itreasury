package com.iss.itreasury.settlement.transcurrentdeposit.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;
import com.iss.itreasury.settlement.util.SETTConstant;

/**
 * @author rongyang
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class TransOnePayMultiReceiveInfo implements Serializable
{
	private long id = -1; //is PK                      
	private long officeID = -1; //is '办事处编号ID'                  
	private long currencyID = -1; //is '币种'               
	private String transNo = ""; //is '交易编号'                  
	private String emptyTransNo = ""; //is '临时交易号'             
	private long serialNo = -1; //is '序列号'                 
	private long transactionTypeID = -1; //is '交易类型'        
	private long typeID = -1; //is '类型'                   
	private long isInternalVirement = -1; //is '是否内部转账'       
	private long isBank = -1; //is '是否银行'                 
	private long isGL = -1; //is '是否总账'                    
	private String consignVoucherNo = ""; //is '委托付款凭证号'         
	private String consignPassword = ""; //is '委托付款凭证密码'          
	private long payClientID = -1; //is '付款客户ID'              
	private long receiveClientID = -1; //is '收款客户ID'          
	private long accountID = -1; //is '收/付款账户ID'                
	private long payGL = -1; //is '付款总账类'                   
	private long receiveGL = -1; //is '收款总账类'               
	private long bankID = -1; //is '收/付款银行账户ID'                   
	private String declarationNo = ""; //is '报单号'            
	private String extAccountNo = ""; //is '非财务公司账户号'             
	private String extClientName = ""; //is '非财务公司账户名称'            
	private String remitInBank = ""; //is '非财务公司银行名称'              
	private String remitInProvince = ""; //is '省'          
	private String remitInCity = ""; //is '市'              
	private long CashFlowID = -1; //is '现金流向'               
	private double amount = 0.0; //is '发生额'                   
	private Timestamp interestStartDate = null; //is '起息日'           
	private Timestamp executeDate = null; //is '执行日'          
	private Timestamp modifyDate = null; //is '修改时间：时分秒'                  
	private Timestamp inputDate = null; //is '录入日期'                  
	private long inputUserID = -1; //is '录入人'             
	private long checkUserID = -1; //is '复核人'              
	private long signUserID = -1; //is '签认人'        
	private long confirmUserID = -1; //is '确认人'         
	private long confirmOfficeID = -1; //is '通存通兑对方办事处'
	private long abstractID = -1; //is '标准摘要ID'
	private String strAbstract = ""; //is '摘要'      
	private String checkAbstract = ""; //is '取消复核摘要'          
	private String confirmAbstract = ""; //is '确认摘要'          
	private long[] statusIDs = null; //is '交易状态'
	
	private long nSubTypeId = -1; //子类型id
	private boolean createInstruction = false;   //是否需要生成银行指令 true:生成,false:不生成
	private String contractno = "";              //特约委托收款合同号
	public String getContractno() {
		return contractno;
	}

	public void setContractno(String contractno) {
		this.contractno = contractno;
	}

	public TransOnePayMultiReceiveInfo()
	{
		//empty
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		StringBuffer sbResult = new StringBuffer(128);

		sbResult.append(this.getClass().getName() + " instance (hashCode=" + this.hashCode() + ")\r\n");
		sbResult.append("=========================================\r\n");

		//获得当前对象指定名称的Field对象
		java.lang.reflect.Field[] field = null;
		try
		{
			field = this.getClass().getDeclaredFields();

			if (field != null)
			{
				for (int i = 0; i < field.length; i++)
				{
					field[i].setAccessible(true);

					sbResult.append(field[i].getName() + " = ");
					sbResult.append(field[i].get(this) + ";\r\n");
				}
			}
		}
		catch (Exception exp)
		{
			exp.printStackTrace();
		}

		return sbResult.toString();
	}

	/**
	 * Returns the accountID.
	 * @return long
	 */
	public long getAccountID()
	{
		return accountID;
	}

	/**
	 * Returns the amount.
	 * @return double
	 */
	public double getAmount()
	{
		return amount;
	}

	/**
	 * Returns the bankID.
	 * @return long
	 */
	public long getBankID()
	{
		return bankID;
	}

	/**
	 * Returns the cashFlowID.
	 * @return long
	 */
	public long getCashFlowID()
	{
		return CashFlowID;
	}

	/**
	 * Returns the checkAbstract.
	 * @return String
	 */
	public String getCheckAbstract()
	{
		return checkAbstract;
	}

	/**
	 * Returns the checkUserID.
	 * @return long
	 */
	public long getCheckUserID()
	{
		return checkUserID;
	}

	/**
	 * Returns the confirmAbstract.
	 * @return String
	 */
	public String getConfirmAbstract()
	{
		return confirmAbstract;
	}

	/**
	 * Returns the confirmOfficeID.
	 * @return long
	 */
	public long getConfirmOfficeID()
	{
		return confirmOfficeID;
	}

	/**
	 * Returns the confirmUserID.
	 * @return long
	 */
	public long getConfirmUserID()
	{
		return confirmUserID;
	}

	/**
	 * Returns the consignPassword.
	 * @return String
	 */
	public String getConsignPassword()
	{
		return consignPassword;
	}

	/**
	 * Returns the cONSIGNVOUCHERNo.
	 * @return String
	 */
	public String getConsignVoucherNo()
	{
		return consignVoucherNo;
	}

	/**
	 * Returns the currencyID.
	 * @return long
	 */
	public long getCurrencyID()
	{
		return currencyID;
	}

	/**
	 * Returns the declarationNo.
	 * @return String
	 */
	public String getDeclarationNo()
	{
		return declarationNo;
	}

	/**
	 * Returns the emptyTransNo.
	 * @return String
	 */
	public String getEmptyTransNo()
	{
		return emptyTransNo;
	}

	/**
	 * Returns the executeDate.
	 * @return Timestamp
	 */
	public Timestamp getExecuteDate()
	{
		return executeDate;
	}

	/**
	 * Returns the extAccountNo.
	 * @return String
	 */
	public String getExtAccountNo()
	{
		return extAccountNo;
	}

	/**
	 * Returns the extClientName.
	 * @return String
	 */
	public String getExtClientName()
	{
		return extClientName;
	}

	/**
	 * Returns the id.
	 * @return long
	 */
	public long getId()
	{
		return id;
	}

	/**
	 * Returns the inputDate.
	 * @return Timestamp
	 */
	public Timestamp getInputDate()
	{
		return inputDate;
	}

	/**
	 * Returns the inputUserID.
	 * @return long
	 */
	public long getInputUserID()
	{
		return inputUserID;
	}

	/**
	 * Returns the interestStartDate.
	 * @return Timestamp
	 */
	public Timestamp getInterestStartDate()
	{
		return interestStartDate;
	}

	/**
	 * Returns the isBank.
	 * @return long
	 */
	public long getIsBank()
	{
		return isBank;
	}

	/**
	 * Returns the isGL.
	 * @return long
	 */
	public long getIsGL()
	{
		return isGL;
	}

	/**
	 * Returns the isInternalVirement.
	 * @return long
	 */
	public long getIsInternalVirement()
	{
		return isInternalVirement;
	}

	/**
	 * Returns the modifyDate.
	 * @return Timestamp
	 */
	public Timestamp getModifyDate()
	{
		return modifyDate;
	}

	/**
	 * Returns the officeID.
	 * @return long
	 */
	public long getOfficeID()
	{
		return officeID;
	}

	/**
	 * Returns the payClientID.
	 * @return long
	 */
	public long getPayClientID()
	{
		return payClientID;
	}

	/**
	 * Returns the payGL.
	 * @return long
	 */
	public long getPayGL()
	{
		return payGL;
	}

	/**
	 * Returns the receiveClientID.
	 * @return long
	 */
	public long getReceiveClientID()
	{
		return receiveClientID;
	}

	/**
	 * Returns the receiveGL.
	 * @return long
	 */
	public long getReceiveGL()
	{
		return receiveGL;
	}

	/**
	 * Returns the remitinBank.
	 * @return String
	 */
	public String getRemitInBank()
	{
		return remitInBank;
	}

	/**
	 * Returns the remitinCity.
	 * @return String
	 */
	public String getRemitInCity()
	{
		return remitInCity;
	}

	/**
	 * Returns the remitinProvince.
	 * @return String
	 */
	public String getRemitInProvince()
	{
		return remitInProvince;
	}

	/**
	 * Returns the serialNo.
	 * @return long
	 */
	public long getSerialNo()
	{
		return serialNo;
	}

	/**
	 * Returns the signUserID.
	 * @return long
	 */
	public long getSignUserID()
	{
		return signUserID;
	}

	/**
	 * Returns the statusID.
	 * @return long
	 */
	public long getStatusID()
	{
		long lResult = -1;
		if (this.statusIDs != null && this.statusIDs.length > 0)
		{
			lResult = this.statusIDs[0];
		}

		return lResult;
	}

	/**
	 * 用于当前Entity作为查询条件时
	 * @return long[]
	 */
	public long[] getStatusIDs()
	{
		return this.statusIDs;
	}

	/**
	 * Returns the strAbstract.
	 * @return String
	 */
	public String getAbstract()
	{
		return strAbstract;
	}

	/**
	 * Returns the transactionTypeID.
	 * @return long
	 */
	public long getTransactionTypeID()
	{
		return transactionTypeID;
	}

	/**
	 * Returns the transNo.
	 * @return String
	 */
	public String getTransNo()
	{
		return transNo;
	}

	/**
	 * Returns the typeID.
	 * @return long
	 */
	public long getTypeID()
	{
		return typeID;
	}

	/**
	 * Sets the accountID.
	 * @param accountID The accountID to set
	 */
	public void setAccountID(long accountID)
	{
		this.accountID = accountID;
	}

	/**
	 * Sets the amount.
	 * @param amount The amount to set
	 */
	public void setAmount(double amount)
	{
		this.amount = amount;
	}

	/**
	 * Sets the bankID.
	 * @param bankID The bankID to set
	 */
	public void setBankID(long bankID)
	{
		this.bankID = bankID;
	}

	/**
	 * Sets the cashFlowID.
	 * @param cashFlowID The cashFlowID to set
	 */
	public void setCashFlowID(long cashFlowID)
	{
		CashFlowID = cashFlowID;
	}

	/**
	 * Sets the checkAbstract.
	 * @param checkAbstract The checkAbstract to set
	 */
	public void setCheckAbstract(String checkAbstract)
	{
		this.checkAbstract = checkAbstract;
	}

	/**
	 * Sets the checkUserID.
	 * @param checkUserID The checkUserID to set
	 */
	public void setCheckUserID(long checkUserID)
	{
		this.checkUserID = checkUserID;
	}

	/**
	 * Sets the confirmAbstract.
	 * @param confirmAbstract The confirmAbstract to set
	 */
	public void setConfirmAbstract(String confirmAbstract)
	{
		this.confirmAbstract = confirmAbstract;
	}

	/**
	 * Sets the confirmOfficeID.
	 * @param confirmOfficeID The confirmOfficeID to set
	 */
	public void setConfirmOfficeID(long confirmOfficeID)
	{
		this.confirmOfficeID = confirmOfficeID;
	}

	/**
	 * Sets the confirmUserID.
	 * @param confirmUserID The confirmUserID to set
	 */
	public void setConfirmUserID(long confirmUserID)
	{
		this.confirmUserID = confirmUserID;
	}

	/**
	 * Sets the consignPassword.
	 * @param consignPassword The consignPassword to set
	 */
	public void setConsignPassword(String consignPassword)
	{
		this.consignPassword = consignPassword;
	}

	/**
	 * Sets the cONSIGNVOUCHERNo.
	 * @param cONSIGNVOUCHERNo The cONSIGNVOUCHERNo to set
	 */
	public void setConsignVoucherNo(String consignVoucherNo)
	{
		this.consignVoucherNo = consignVoucherNo;
	}

	/**
	 * Sets the currencyID.
	 * @param currencyID The currencyID to set
	 */
	public void setCurrencyID(long currencyID)
	{
		this.currencyID = currencyID;
	}

	/**
	 * Sets the declarationNo.
	 * @param declarationNo The declarationNo to set
	 */
	public void setDeclarationNo(String declarationNo)
	{
		this.declarationNo = declarationNo;
	}

	/**
	 * Sets the emptyTransNo.
	 * @param emptyTransNo The emptyTransNo to set
	 */
	public void setEmptyTransNo(String emptyTransNo)
	{
		this.emptyTransNo = emptyTransNo;
	}

	/**
	 * Sets the executeDate.
	 * @param executeDate The executeDate to set
	 */
	public void setExecuteDate(Timestamp executeDate)
	{
		this.executeDate = executeDate;
	}

	/**
	 * Sets the extAccountNo.
	 * @param extAccountNo The extAccountNo to set
	 */
	public void setExtAccountNo(String extAccountNo)
	{
		this.extAccountNo = extAccountNo;
	}

	/**
	 * Sets the extClientName.
	 * @param extClientName The extClientName to set
	 */
	public void setExtClientName(String extClientName)
	{
		this.extClientName = extClientName;
	}

	/**
	 * Sets the id.
	 * @param id The id to set
	 */
	public void setId(long id)
	{
		this.id = id;
	}

	/**
	 * Sets the inputDate.
	 * @param inputDate The inputDate to set
	 */
	public void setInputDate(Timestamp inputDate)
	{
		this.inputDate = inputDate;
	}

	/**
	 * Sets the inputUserID.
	 * @param inputUserID The inputUserID to set
	 */
	public void setInputUserID(long inputUserID)
	{
		this.inputUserID = inputUserID;
	}

	/**
	 * Sets the interestStartDate.
	 * @param interestStartDate The interestStartDate to set
	 */
	public void setInterestStartDate(Timestamp interestStartDate)
	{
		this.interestStartDate = interestStartDate;
	}

	/**
	 * Sets the isBank.
	 * @param isBank The isBank to set
	 */
	public void setIsBank(long isBank)
	{
		this.isBank = isBank;
	}

	/**
	 * Sets the isGL.
	 * @param isGL The isGL to set
	 */
	public void setIsGL(long isGL)
	{
		this.isGL = isGL;
	}

	/**
	 * Sets the isInternalVirement.
	 * @param isInternalVirement The isInternalVirement to set
	 */
	public void setIsInternalVirement(long isInternalVirement)
	{
		this.isInternalVirement = isInternalVirement;
	}

	/**
	 * Sets the modifyDate.
	 * @param modifyDate The modifyDate to set
	 */
	public void setModifyDate(Timestamp modifyDate)
	{
		this.modifyDate = modifyDate;
	}

	/**
	 * Sets the officeID.
	 * @param officeID The officeID to set
	 */
	public void setOfficeID(long officeID)
	{
		this.officeID = officeID;
	}

	/**
	 * Sets the payClientID.
	 * @param payClientID The payClientID to set
	 */
	public void setPayClientID(long payClientID)
	{
		this.payClientID = payClientID;
	}

	/**
	 * Sets the payGL.
	 * @param payGL The payGL to set
	 */
	public void setPayGL(long payGL)
	{
		this.payGL = payGL;
	}

	/**
	 * Sets the receiveClientID.
	 * @param receiveClientID The receiveClientID to set
	 */
	public void setReceiveClientID(long receiveClientID)
	{
		this.receiveClientID = receiveClientID;
	}

	/**
	 * Sets the receiveGL.
	 * @param receiveGL The receiveGL to set
	 */
	public void setReceiveGL(long receiveGL)
	{
		this.receiveGL = receiveGL;
	}

	/**
	 * Sets the remitinBank.
	 * @param remitinBank The remitinBank to set
	 */
	public void setRemitInBank(String remitinBank)
	{
		this.remitInBank = remitinBank;
	}

	/**
	 * Sets the remitinCity.
	 * @param remitinCity The remitinCity to set
	 */
	public void setRemitInCity(String remitinCity)
	{
		this.remitInCity = remitinCity;
	}

	/**
	 * Sets the remitinProvince.
	 * @param remitinProvince The remitinProvince to set
	 */
	public void setRemitInProvince(String remitinProvince)
	{
		this.remitInProvince = remitinProvince;
	}

	/**
	 * Sets the serialNo.
	 * @param serialNo The serialNo to set
	 */
	public void setSerialNo(long serialNo)
	{
		this.serialNo = serialNo;
	}

	/**
	 * Sets the signUserID.
	 * @param signUserID The signUserID to set
	 */
	public void setSignUserID(long signUserID)
	{
		this.signUserID = signUserID;
	}

	/**
	 * Sets the statusID.
	 * @param statusID The statusID to set
	 */
	public void setStatusID(long statusID)
	{
		this.statusIDs = new long[] { statusID };
	}

	/**
	 * 用于当前Entity作为查询条件时
	 * @param statusIDs
	 */
	public void setStatusIDs(long[] statusIDs)
	{
		this.statusIDs = statusIDs;
	}

	/**
	 * Sets the strAbstract.
	 * @param strAbstract The strAbstract to set
	 */
	public void setAbstract(String strAbstract)
	{
		this.strAbstract = strAbstract;
	}

	/**
	 * Sets the transactionTypeID.
	 * @param transactionTypeID The transactionTypeID to set
	 */
	public void setTransactionTypeID(long transactionTypeID)
	{
		this.transactionTypeID = transactionTypeID;
	}

	/**
	 * Sets the transNo.
	 * @param transNo The transNo to set
	 */
	public void setTransNo(String transNo)
	{
		this.transNo = transNo;
	}

	/**
	 * Sets the typeID.
	 * @param typeID The typeID to set
	 */
	public void setTypeID(long typeID)
	{
		this.typeID = typeID;
	}

	/**
	 * Returns the abstractID.
	 * @return long
	 */
	public long getAbstractID()
	{
		return abstractID;
	}

	/**
	 * Sets the abstractID.
	 * @param abstractID The abstractID to set
	 */
	public void setAbstractID(long abstractID)
	{
		this.abstractID = abstractID;
	}

	/**
	 * perSave check whether there is repeated tran record for different
	 * transaction because different transaction has different check condition.
	 */
	public TransOnePayMultiReceiveInfo getQeureyInfo()
	{
		TransOnePayMultiReceiveInfo queryInfo = new TransOnePayMultiReceiveInfo();
		int transType = (int) this.getTransactionTypeID();
		//按照不同业务类型校验重复交易
		queryInfo.setTransactionTypeID(transType);
		//收付类型
		queryInfo.setTypeID(this.getTypeID());

		//重复交易校验执行日
		queryInfo.setExecuteDate(this.getExecuteDate());

		//重复交易校验的交易状态为除过删除状态的所有有效交易
		queryInfo.setStatusIDs(
			new long[] {
				SETTConstant.TransactionStatus.CHECK,
				SETTConstant.TransactionStatus.CIRCLE,
				SETTConstant.TransactionStatus.CONFIRM,
				SETTConstant.TransactionStatus.NOTSIGN,
				SETTConstant.TransactionStatus.SAVE,
				SETTConstant.TransactionStatus.SIGN,
				SETTConstant.TransactionStatus.TEMPSAVE });

		switch (transType)
		{
			case (int) SETTConstant.TransactionType.ONETOMULTI :
				{
					queryInfo.setPayClientID(this.getPayClientID());
					queryInfo.setReceiveClientID(this.getReceiveClientID());
					queryInfo.setAccountID(this.getAccountID());
					queryInfo.setBankID(this.getBankID());
					queryInfo.setPayGL(this.getPayGL());
					queryInfo.setReceiveGL(this.getReceiveGL());
					queryInfo.setAmount(this.getAmount());
					break;
				}

		}
		//@TBD: we will add other trans type at here
		return queryInfo;

	}

	public static void main(String[] args)
	{
		try
		{
			TransOnePayMultiReceiveInfo aaa = new TransOnePayMultiReceiveInfo();

			System.out.println(aaa);
		}
		catch (Exception exp)
		{
			exp.printStackTrace();

		}

	}

	public long getNSubTypeId() {
		return nSubTypeId;
	}

	public void setNSubTypeId(long subTypeId) {
		nSubTypeId = subTypeId;
	}

	public boolean isCreateInstruction() {
		return createInstruction;
	}

	public void setCreateInstruction(boolean createInstruction) {
		this.createInstruction = createInstruction;
	}
	
}
