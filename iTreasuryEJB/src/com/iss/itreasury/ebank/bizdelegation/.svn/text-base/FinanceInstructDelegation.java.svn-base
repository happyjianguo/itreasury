/*
 * Created on 2004-11-2
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.ebank.bizdelegation;

import com.iss.itreasury.ebank.obfinanceinstr.bizlogic.OBFinanceInstr;
import com.iss.itreasury.ebank.obfinanceinstr.bizlogic.OBFinanceInstrHome;
import com.iss.itreasury.ebank.obfinanceinstr.dao.OBFinanceInstrDao;
import com.iss.itreasury.ebank.obfinanceinstr.dataentity.FinanceInfo;
import com.iss.itreasury.ebank.util.OBConstant;
import com.iss.itreasury.settlement.transcurrentdeposit.dataentity.TransCurrentDepositInfo;
import com.iss.itreasury.settlement.util.NameRef;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.EJBObject;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.MessageQueueSender;

/**
 * @author yiwang
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class FinanceInstructDelegation
{

    /**
     * 
     */
    public FinanceInstructDelegation()
    {
        super();
        // TODO Auto-generated constructor stub
    }
    public void check(long lInstructionID, long lCheckUserID, long lCurrencyID) throws Exception
    {
        OBFinanceInstrHome financeInstrHome = (OBFinanceInstrHome)EJBObject.getEJBHome("OBFinanceInstrHome");
        OBFinanceInstr financeInstr = financeInstrHome.create();
        financeInstr.check(lInstructionID,lCheckUserID,lCurrencyID);
		    
        if(Config.getBoolean( Config.INTEGRATION_ES_FINANCEINFO,false))
        {
        	FinanceInfo financeInfo = new FinanceInfo();
    		OBFinanceInstrDao dao = new OBFinanceInstrDao();
        	try
			{
	    		financeInfo = dao.findByID(lInstructionID);
	    		//TransCurrentDepositInfo transInfo = mapFinanceInfoToTransCurrentDepositInfo(lInstructionID);
	    		MessageQueueSender.sendObject(financeInfo);
			}
            catch(Exception e)
			{
            	dao.updateStatus(lInstructionID, OBConstant.SettInstrStatus.REFUSE);
			}
        }
    }
    
	/**
	 * 将网银指令信息映射到活期转账信息
	 * 
	 * @throws Exception
	 *  
	 */
	public TransCurrentDepositInfo mapFinanceInfoToTransCurrentDepositInfo(long financeInstrID) throws Exception
	{
		System.out.println("---------------mapFinanceInfoToTransCurrentDepositInfo111-----");
		TransCurrentDepositInfo transCurrentDepositInfo = new TransCurrentDepositInfo();
		FinanceInfo financeInfo = new FinanceInfo();
		try
		{
			OBFinanceInstrDao dao = new OBFinanceInstrDao();
			financeInfo = dao.findByID(financeInstrID);
			System.out.println("---------------mapFinanceInfoToTransCurrentDepositInfo2-----");            
			//
			transCurrentDepositInfo.setOfficeID(financeInfo.getOfficeID());
			transCurrentDepositInfo.setAbstractStr(financeInfo.getNote());
			transCurrentDepositInfo.setPayAccountID(financeInfo.getPayerAcctID());
			transCurrentDepositInfo.setPayClientID(NameRef.getClientIDByAccountID(financeInfo.getPayerAcctID()));
			transCurrentDepositInfo.setReceiveAccountID(financeInfo.getPayeeAcctID());
			transCurrentDepositInfo.setReceiveClientID(NameRef.getClientIDByAccountID(financeInfo.getPayeeAcctID()));
			transCurrentDepositInfo.setAmount(financeInfo.getAmount());
			transCurrentDepositInfo.setCurrencyID(financeInfo.getCurrencyID());
			//transCurrentDepositInfo.setExecuteDate(info.getExecuteDate());
			transCurrentDepositInfo.setExecuteDate(Env.getSystemDate(financeInfo.getOfficeID(), financeInfo.getCurrencyID()));
			transCurrentDepositInfo.setInterestStartDate(financeInfo.getExecuteDate());
			transCurrentDepositInfo.setInputDate(Env.getSystemDate(financeInfo.getOfficeID(), financeInfo.getCurrencyID()));
			transCurrentDepositInfo.setInputUserID(Constant.MachineUser.InputUser); // 自动保存，机制用户-100。
			transCurrentDepositInfo.setCheckUserID(Constant.MachineUser.CheckUser); // 自动复核，机核用户-101。
            
			transCurrentDepositInfo.setInstructionNo(String.valueOf(financeInfo.getID()));
			if (financeInfo.getRemitType() == OBConstant.SettRemitType.BANKPAY)
			{
				//不用收款账户ID,置为-1,否则影响复核
				transCurrentDepositInfo.setReceiveAccountID(-1);
			}
			//银行付款
			transCurrentDepositInfo.setExtAccountNo(financeInfo.getPayeeAcctNo());
			transCurrentDepositInfo.setExtClientName(financeInfo.getPayeeName());
			transCurrentDepositInfo.setRemitInCity(financeInfo.getPayeeCity());
			transCurrentDepositInfo.setRemitInProvince(financeInfo.getPayeeProv());
			transCurrentDepositInfo.setRemitInBank(financeInfo.getPayeeBankName());

			//其它下属单位
			transCurrentDepositInfo.setSubClientID(financeInfo.getChildClientID());

			transCurrentDepositInfo.setStatusID(SETTConstant.TransactionStatus.SAVE);
			//交易类型设置成-银行付款
			transCurrentDepositInfo.setTransactionTypeID(SETTConstant.TransactionType.BANKPAY);
			transCurrentDepositInfo.setInstructionNo(String.valueOf(financeInfo.getID()));
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}

		return transCurrentDepositInfo;
	}

}
