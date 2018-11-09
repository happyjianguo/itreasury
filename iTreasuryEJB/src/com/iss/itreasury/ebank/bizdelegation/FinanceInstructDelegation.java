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
	 * ������ָ����Ϣӳ�䵽����ת����Ϣ
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
			transCurrentDepositInfo.setInputUserID(Constant.MachineUser.InputUser); // �Զ����棬�����û�-100��
			transCurrentDepositInfo.setCheckUserID(Constant.MachineUser.CheckUser); // �Զ����ˣ������û�-101��
            
			transCurrentDepositInfo.setInstructionNo(String.valueOf(financeInfo.getID()));
			if (financeInfo.getRemitType() == OBConstant.SettRemitType.BANKPAY)
			{
				//�����տ��˻�ID,��Ϊ-1,����Ӱ�츴��
				transCurrentDepositInfo.setReceiveAccountID(-1);
			}
			//���и���
			transCurrentDepositInfo.setExtAccountNo(financeInfo.getPayeeAcctNo());
			transCurrentDepositInfo.setExtClientName(financeInfo.getPayeeName());
			transCurrentDepositInfo.setRemitInCity(financeInfo.getPayeeCity());
			transCurrentDepositInfo.setRemitInProvince(financeInfo.getPayeeProv());
			transCurrentDepositInfo.setRemitInBank(financeInfo.getPayeeBankName());

			//����������λ
			transCurrentDepositInfo.setSubClientID(financeInfo.getChildClientID());

			transCurrentDepositInfo.setStatusID(SETTConstant.TransactionStatus.SAVE);
			//�����������ó�-���и���
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
