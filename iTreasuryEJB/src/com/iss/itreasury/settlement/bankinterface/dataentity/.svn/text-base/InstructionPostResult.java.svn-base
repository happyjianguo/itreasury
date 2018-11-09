package com.iss.itreasury.settlement.bankinterface.dataentity;

import com.iss.itreasury.settlement.util.SETTConstant;

/**
 * @author rongyang
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class InstructionPostResult
{
	private String strTransactionNo = null;

	private long lStatusID = -1;

	private String strMessage = null;
	/**
	 * Constructor for InstructionPostResult.
	 */
	public InstructionPostResult()
	{
		super();
	}

	/**
	 * Returns the lStatusID.
	 * @return long
	 */
	public long getStatusID()
	{
		return lStatusID;
	}

	/**
	 * Returns the message.
	 * @return String
	 */
	public String getMessage()
	{
		return strMessage;
	}

	/**
	 * Returns the transactionNo.
	 * @return String
	 */
	public String getTransactionNo()
	{
		return strTransactionNo;
	}

	/**
	 * Sets the lStatusID.
	 * @param lStatusID The lStatusID to set
	 */
	public void setStatusID(long lStatusID)
	{
		this.lStatusID = lStatusID;
	}

	/**
	 * Sets the message.
	 * @param message The message to set
	 */
	public void setMessage(String message)
	{
		strMessage = message;
	}

	/**
	 * Sets the transactionNo.
	 * @param transactionNo The transactionNo to set
	 */
	public void setTransactionNo(String transactionNo)
	{
		strTransactionNo = transactionNo;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return "交易号为"
			+ this.getTransactionNo()
			+ "的指令执行状态是\""
			+ SETTConstant.BankInstructionStatus.getName(this.getStatusID())
			+ "\"，银行提示："
			+ this.getMessage();
	}

}
