/*
 * Created on 2005-8-10
 */
package com.iss.itreasury.settlement.bankinstruction.entity;

import java.io.Serializable;
import com.iss.itreasury.settlement.bankinterface.dataentity.BankAccountTransInfo;


/**
 * @author weilu
 *
 */
public class ReceiveInstructionParam implements Serializable{

	BankAccountTransInfo bankAccountTransInfo = new BankAccountTransInfo();

	/**
	 * @return Returns the bankAccountTransInfo.
	 */
	public BankAccountTransInfo getBankAccountTransInfo() {
		return bankAccountTransInfo;
	}

	/**
	 * @param bankAccountTransInfo The bankAccountTransInfo to set.
	 */
	public void setBankAccountTransInfo(BankAccountTransInfo bankAccountTransInfo) {
		this.bankAccountTransInfo = bankAccountTransInfo;
	}
    
}
