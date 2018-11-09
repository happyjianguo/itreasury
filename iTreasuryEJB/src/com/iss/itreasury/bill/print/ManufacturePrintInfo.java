/*
 * Created on 2005-3-18
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.bill.print;

import java.rmi.RemoteException;

import com.iss.itreasury.bill.bizdelegation.BlankDelegation;
import com.iss.itreasury.bill.bizdelegation.DraftDelegation;
import com.iss.itreasury.bill.blankvoucher.dataentity.*;
import com.iss.itreasury.bill.draft.dataentity.DiscountContractBillInfo;
import com.iss.itreasury.bill.draft.dataentity.TransDraftInInfo;
import com.iss.itreasury.bill.draft.dataentity.TransDraftOutInfo;
import com.iss.itreasury.bill.draft.dataentity.assemble.DraftStorageAssembleInfo;
import com.iss.itreasury.bill.util.*;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.Log4j;

/**
 * @author zntan
 *
 * call delegations to find print details by ID and return a PrintInfo
 */
public class ManufacturePrintInfo
{
	protected Log4j Log = new Log4j(Constant.ModuleType.BILL, this);
	/**
	 * 
	 */
	public ManufacturePrintInfo()
	{
		super();
	}
	
	/**
	 * get PrintInfo for Blank register and Blank use
	 * @param lTransId
	 * @return
	 */
	public PrintInfo getBlankPrintInfo (long lTransId) throws BillException
	{
		PrintInfo info = new PrintInfo();
		try
		{
			BlankDelegation blankDel = new BlankDelegation();
			BlankTransactionInfo btInfo = blankDel.findTransByTransID(lTransId);
			info.setExecuteDate(Env.getBillSystemDate(btInfo.getOfficeID(),btInfo.getCurrencyID()));
			info.setTransNo(btInfo.getTransCode());
			info.setAbstract(btInfo.getSummary());
			info.setInputUserID(btInfo.getInputUserID());
			info.setCheckUserID(btInfo.getCheckUserID());
			if (btInfo.getTransTypeID()== BILLConstant.BlankTransctionType.REGISTER)
			{
				BlankRegisterInfo brInfo = (BlankRegisterInfo) btInfo.getDetailInfo();
				info.setAmount(brInfo.getAmount());
				info.setPayClient(Env.getClientName());
			}
			else if (btInfo.getTransTypeID()==BILLConstant.BlankTransctionType.USE)
			{
				BlankUseInfo buInfo = (BlankUseInfo) btInfo.getDetailInfo();
				info.setAmount(buInfo.getAmount());
				info.setPayClient(BillNameRef.getClientNameByID(buInfo.getUseClientID()));
				info.setPayAccount(BillNameRef.getAccountNoByID(buInfo.getUseAccountID()));
				if (buInfo.getPayAccountID() > 0)
					info.setPayAccount(BillNameRef.getAccountNoByID(buInfo.getPayAccountID()));
				info.setPayBank(BillNameRef.getBranchNameByID(buInfo.getPayBankID()));
				info.setReceiveClient(Env.getClientName());
			}
		} catch (RemoteException e)
		{
			e.printStackTrace();
			throw new BillException("Gen_001",e);
		} catch (BillException e)
		{
			throw e;
		}
		return info;
	}
	/**
	 * get WithDraw PrintInfo for Blank Use
	 * @param lTransId
	 * @return
	 */
	public PrintInfo getWithDrawPrintInfo (long lTransId) throws BillException
	{
		PrintInfo info = new PrintInfo();
		try
		{
			BlankDelegation blankDel = new BlankDelegation();
			BlankTransactionInfo btInfo = blankDel.findTransByTransID(lTransId);
			info.setExecuteDate(Env.getBillSystemDate(btInfo.getOfficeID(),btInfo.getCurrencyID()));
			info.setTransNo(btInfo.getTransCode());
			info.setAbstract(btInfo.getSummary());
			info.setInputUserID(btInfo.getInputUserID());
			info.setCheckUserID(btInfo.getCheckUserID());
			info.setCurrencyID(btInfo.getCurrencyID());
			if (btInfo.getTransTypeID()==BILLConstant.BlankTransctionType.USE)
			{
				BlankUseInfo buInfo = (BlankUseInfo) btInfo.getDetailInfo();
				info.setAmount(buInfo.getAmount());
				info.setPayClient(BillNameRef.getClientNameByID(buInfo.getUseClientID()));
				info.setPayAccountID(buInfo.getUseAccountID());
				if (buInfo.getPayAccountID() > 0)
					info.setPayAccountID(buInfo.getPayAccountID());
				info.setPayBank(BillNameRef.getBranchNameByID(buInfo.getPayBankID()));
				info.setReceiveClient(Env.getClientName());
			}
		} catch (RemoteException e)
		{
			e.printStackTrace();
			throw new BillException("Gen_001",e);
		} catch (BillException e)
		{
			e.printStackTrace();
			throw e;
		}
		return info;
	}
	
	/**
	 * Method getDiscountGrantPrintInfo.
	 * 套打 打印（贴现凭证、委托收款凭证……） 
	 * @param lID
	 * @return PrintInfo
	 * @throws Exception
	 */
	public PrintInfo getDiscountVoucherPrintInfo(long lID) throws BillException
	{
		PrintInfo info = new PrintInfo();
		try
		{
			DraftDelegation del = new DraftDelegation();
			DraftStorageAssembleInfo dsaInfo = new DraftStorageAssembleInfo();
			dsaInfo = del.findDraftInByID(lID);
			DiscountContractBillInfo billInfo = new DiscountContractBillInfo();
			billInfo = dsaInfo.getDiscountContractBillInfo();
			TransDraftInInfo inInfo = new TransDraftInInfo();
			inInfo = dsaInfo.getTransDraftInInfo();
			//TransDraftOutInfo outInfo = dsaInfo.getTransDraftOutInfo();
			
			info.setExecuteDate(Env.getBillSystemDate(inInfo.getOfficeID(),inInfo.getCurrencyID()));
			info.setTypeID(billInfo.getNDraftTypeID());
			info.setCode(billInfo.getSCode());
			if (billInfo.getDtCreate()!= null)
				info.setStartDate(billInfo.getDtCreate());
			if (billInfo.getDtEnd() != null)
				info.setEndDate(billInfo.getDtEnd());
			//info.setPayClient(billInfo.get) ;//出票人名称？账号？开户银行？
			info.setAmount(billInfo.getMAmount());
			//info.setRate(billInfo.get);//利率？利息？
			//info.setInterest();
			info.setInputUserID(billInfo.getNInputUserID());
			//info.setCheckUserID(billInfo.);//复核人
			
			
			info.setReceiveClient(inInfo.getCurrentHolder());//持票人

		}
		catch(BillException e)
		{
			throw e;
		}
		return info;
	}
}
