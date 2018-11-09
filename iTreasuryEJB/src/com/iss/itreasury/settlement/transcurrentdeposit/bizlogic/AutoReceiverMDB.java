/*
 * Created on 2004-10-18
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.settlement.transcurrentdeposit.bizlogic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;

//import com.iss.itreasury.bs.TransactionKey;
//import com.iss.itreasury.bs.VirementResultInfo;
import com.iss.itreasury.ebank.obfinanceinstr.dao.OBFinanceInstrDao;
import com.iss.itreasury.ebank.obfinanceinstr.dataentity.FinanceInfo;
import com.iss.itreasury.ebank.util.OBConstant;
import com.iss.itreasury.settlement.account.dao.Sett_AccountDAO;
import com.iss.itreasury.settlement.account.dataentity.AccountInfo;
import com.iss.itreasury.settlement.bankinstruction.ImportAccountFactory;
import com.iss.itreasury.settlement.bankinstruction.entity.ReceiveInstructionParam;
//import com.iss.itreasury.settlement.bankinterface.bizlogic.BankInstruction;
//import com.iss.itreasury.settlement.bankinterface.dao.Sett_BankInstructionDAO;
//import com.iss.itreasury.settlement.bankinterface.dao.Sett_HisTransInfoOfBankAccountDAO;
//import com.iss.itreasury.settlement.bankinterface.dao.Sett_TransInfoOfBankAccountDAO;
//import com.iss.itreasury.settlement.bankinterface.dataentity.BankAccountTransInfo;
import com.iss.itreasury.settlement.bankinterface.dataentity.BankInstructionInfo;
import com.iss.itreasury.settlement.bankinterface.dataentity.InstructionPostResult;
import com.iss.itreasury.settlement.bizdelegation.TransCurrentDepositDelegation;
import com.iss.itreasury.settlement.enddayprocess.process.EndDayProcess;
import com.iss.itreasury.settlement.obinstruction.bizlogic.TransInfo;
import com.iss.itreasury.settlement.setting.dao.Sett_FilialeAccountSetDAO;
import com.iss.itreasury.settlement.setting.dao.Sett_TurnInBankTransSettingDAO;
import com.iss.itreasury.settlement.setting.dataentity.FilialeAccountInfo;
import com.iss.itreasury.settlement.setting.dataentity.QueryFilialeAccountInfo;
import com.iss.itreasury.settlement.transcurrentdeposit.dao.Sett_TransCurrentDepositDAO;
import com.iss.itreasury.settlement.transcurrentdeposit.dataentity.TransCurrentDepositAssembler;
import com.iss.itreasury.settlement.transcurrentdeposit.dataentity.TransCurrentDepositInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.settlement.util.UtilOperation;
import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IDate;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IExceptionMessage;
import com.iss.itreasury.util.IRollbackException;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.util.MessageQueueSender;

/**
 * @author yehuang
 * 
 * �Զ�ָ����ܴ�����Ϣ����Bean
 *  
 */
public class AutoReceiverMDB implements javax.ejb.MessageDrivenBean, javax.jms.MessageListener
{
    private transient javax.ejb.MessageDrivenContext context;

    /**
     * @see javax.ejb.MessageDrivenBean#setMessageDrivenContext(javax.ejb.MessageDrivenContext)
     */
    public void setMessageDrivenContext(javax.ejb.MessageDrivenContext aContext)
    {
        context = aContext;
    }

    /**
     * See section 15.4.4 of the EJB 2.0 specification
     */
    public void ejbCreate()
    {
    }

    /**
     * @see javax.ejb.MessageDrivenBean#ejbRemove()
     */
    public void ejbRemove()
    {
    }

    /**
     * @see javax.jms.MessageListener#onMessage(javax.jms.Message)
     */
    public void onMessage(javax.jms.Message inMessage)
    {
    	Log.print("--------------���ܵ���Ϣ--------------");
        ObjectMessage msg = null;
       
    	try
		{
			if (inMessage != null && inMessage.getJMSRedelivered() == true)
			{
				Log.print("--------------ע�⣺����ϢΪ������Ϣ--------------");
				return;
			}
		} 
    	catch (JMSException e) {			
			e.printStackTrace();
			return;
		}
		
        try
		{
        	Log.print("--------------inMessage:"+inMessage);
        	Log.print("--------------����try--------------");
	        if (inMessage instanceof ObjectMessage)
	        {
	        	Log.print("--------------inMessage instanceof ObjectMessage--------------");
	        	msg = (ObjectMessage) inMessage;	        		        	        	
	        	//��������ϵͳ��ҵ����Ϣ
	        	if (msg.getObject() instanceof FinanceInfo)
	        	{
	        		System.out.println("--------------���ܵ�����ϵͳ������ָ��֪ͨ--------------");
	                FinanceInfo financeInfo = (FinanceInfo) msg.getObject();
	                System.out.println("========= FinanceInfo : " + UtilOperation.dataentityToString(financeInfo));

		            this.receiveFinanceInfo(financeInfo);
	        	}	        	
	        }
	        else
	        {
	            System.out.println("Message of wrong type: " + inMessage.getClass().getName());
	        }
		}
        catch (Throwable te)
        {
			Log.print("---------------������Ϣ�����쳣-----------------");
            te.printStackTrace();
        }
    }
    
    /**
	 * @param transInfo
	 */
	private void receiveTransCurrentDepositInfo(TransCurrentDepositInfo transInfo)
	{
        try
	    {
            TransCurrentDepositDelegation currentDepositDelegation = new TransCurrentDepositDelegation();
            TransCurrentDepositAssembler assembler = new TransCurrentDepositAssembler(transInfo); //ifp

            //�˴�Ϊ�Զ����ա����桢����
            if (transInfo.getAutoOperationType() == TransCurrentDepositInfo.Auto_OperationType_SaveAndCheck)
            {
                // ��������ָ��״̬Ϊ�ѽ���
                receiveFinanceInstruct(transInfo);
                // �Զ����桢����
                currentDepositDelegation.saveAndCheckAutomatically(assembler);
            }
            // �Զ�ȡ�����ˡ�ɾ������������״̬
            else if (transInfo.getAutoOperationType() == TransCurrentDepositInfo.Auto_OperationType_CancelSaveAndCheck)
            {
                // �Զ�ȡ�����ˡ�ɾ��
                currentDepositDelegation.cancelAndCheckAutomatically(assembler);
                // ��������״̬Ϊ�Ѿܾ�
                refuseFinanceInstruct(transInfo);
            }
        }
        catch (IRollbackException e)
        {
            //TBD:����ͬҳ�洦����ʾ�쳣��ͬ�ķ�����ȡ�쳣��ERR Message����д��ָ����־�в�����ָ��״̬Ϊ�Ѿܾ�
        	OBFinanceInstrDao financeInstrDao = new OBFinanceInstrDao();
        	FinanceInfo financeInfo = new FinanceInfo();
        	financeInfo.setID(Long.parseLong(transInfo.getInstructionNo()));
        	financeInfo.setReturnMsg(e.getMessage());
        	try {
        		financeInstrDao.update(financeInfo);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        }
        catch (Exception te)
        {
            te.printStackTrace();
        }
	}
	
	private void receiveFinanceInfo(FinanceInfo financeInfo)
    {
        try
	    {
    		//�ж��Ƿ��Ѿ��ػ�
			if (EndDayProcess.getSystemStatusID(financeInfo.getOfficeID(), financeInfo.getCurrencyID()) != Constant.SystemStatus.OPEN)
			{
				Log.print("ϵͳ�ػ����´����:" + financeInfo.getOfficeID() + "���֣�" + financeInfo.getCurrencyID());
				throw new IRollbackException(null,"����ϵͳ���Ѿ��ػ�");
			}
			
    		//�ж�����ָ���ִ���պͽ���ϵͳ�������Ƿ�һ��
			Timestamp executeDate = null;
			Timestamp systemDate = null;
			executeDate = financeInfo.getExecuteDate();
			Log.print("����ָ���ִ����:"+executeDate);
			//��ȡ��ǰ����ϵͳ�Ŀ���ʱ��
			try
			{
				systemDate = Env.getSystemDate(financeInfo.getOfficeID(), financeInfo.getCurrencyID());
				if(systemDate == null)
				{
					throw new Exception();
				}
				Log.print("��ǰ����ϵͳ�Ŀ���ʱ��:"+systemDate);
			}
			catch(Exception e)
			{
				Log.print("�޷���õ�ǰ����ϵͳ�Ŀ�������");
				throw new IRollbackException(null,"�޷���õ�ǰ����ϵͳ�Ŀ�������");
			}
			//�Ƚ���ʱ���Ƿ����
			if(IDate.compareDate(executeDate, systemDate) != 0)
			{
				throw new IRollbackException(null,"����ָ���ִ���պͽ���ϵͳ�����ղ�һ��");
			}
        	
        	//��������ָ��ǰ��ά������ָ��״̬Ϊ�������С�
	    	FinanceInfo financeInfoTemp = new FinanceInfo();
			OBFinanceInstrDao financeInstrDao = new OBFinanceInstrDao();
			
			try
			{
				financeInfoTemp = financeInstrDao.findByID(financeInfo.getID());
				if(financeInfoTemp == null)
				{
					throw new Exception("��������ָ��id��"+financeInfo.getID()+"�ҵ��Ķ�Ӧ������ָ����ϢΪ��");
				}
			}
			catch(Exception e)
			{
				Log.print("��������ָ��id��"+financeInfo.getID()+"�Ҳ�����Ӧ������ָ����Ϣ");
				e.printStackTrace();
				return;
			}
			
			if(financeInfoTemp.getStatus() != OBConstant.SettInstrStatus.CHECK)
			{
				Log.print("��������ָ��id��"+financeInfo.getID()+"�ҵ�������ָ����Ϣ��״̬�ǡ��Ѹ��ˡ��������������˴���");
				return;
			}
			
	    	try 
			{
	    		financeInstrDao.updateStatus(financeInfo.getID(), OBConstant.SettInstrStatus.DEAL);
	    		Log.print("ά������ָ��״̬Ϊ�������С��ɹ�");
			} 
	    	catch (Exception e1) 
			{
	    		Log.print("ά������ָ��״̬Ϊ�������С�ʱ�����쳣�������������˴���");
	    		return;
			}
	    	
	    	//����icbc��ĿҪ������ʱ�䲻������15:00���жϵ�ǰ����ʱ���Ƿ����Ҫ��
	    	if(!this.checkTime())
	    	{
	    		//���ʱ�䲻����Ҫ���޸�ָ��״̬Ϊ���Ѿܾ�����ֱ�ӷ��أ��������˴���
	    		throw new IRollbackException(null,"����ϵͳ������ʱ����Ч");
	    	}
	    	
	        TransCurrentDepositDelegation currentDepositDelegation = new TransCurrentDepositDelegation();
	        //�Զ����桢����
	        currentDepositDelegation.saveAndCheckAutomatically(financeInfo, Constant.MachineUser.InputUser);
        }
        catch (IRollbackException e)
        {
            e.printStackTrace();
        	//TBD:����ͬҳ�洦����ʾ�쳣��ͬ�ķ�����ȡ�쳣��ERR Message����д��ָ����־�в�����ָ��״̬Ϊ�Ѿܾ�
        	OBFinanceInstrDao financeInstrDao = new OBFinanceInstrDao();
        	try 
			{
        		financeInstrDao.refuseInstr(
        				financeInfo.getID(), 
						"ҵ����ʧ�ܣ�"+e.getMessage(),
						Constant.MachineUser.InputUser,
						Env.getSystemDate());
			} 
        	catch (Exception e1) 
			{
				e1.printStackTrace();
			}
        }
        catch (Exception te)
        {
            te.printStackTrace();
        }
    }
    

    /**
	 * @return
	 */
	private boolean checkTime()
	{
		boolean valid = false;
		try
		{
			Timestamp currentTime = Env.getSystemDateTime();
			Timestamp stopTime = null;
			Sett_TurnInBankTransSettingDAO dao = new Sett_TurnInBankTransSettingDAO();

			stopTime = dao.select();

			java.util.Calendar currentCalendar = Calendar.getInstance();
			currentCalendar.setTime(currentTime);
			java.util.Calendar stopCalendar = Calendar.getInstance();
			stopCalendar.setTime(stopTime);
			stopTime = DataFormat.getDateTime(
					currentCalendar.get(Calendar.YEAR),
					currentCalendar.get(Calendar.MONTH) + 1,
					currentCalendar.get(Calendar.DATE),
					stopCalendar.get(Calendar.HOUR_OF_DAY),
					stopCalendar.get(Calendar.MINUTE),
					stopCalendar.get(Calendar.SECOND));
			if (currentTime.getTime() < stopTime.getTime())
			{
				valid = true;
			}
		}
		catch(Exception e)
		{
			Log.print("��ȡ����ָ��Ľ�������ʱ�����");
			e.printStackTrace();
		}
		return valid;
	}

	private void receiveFinanceInstruct(TransCurrentDepositInfo transInfo) throws Exception
    {
        try
        {
            TransInfo acceptOBInstrBiz = new TransInfo();
            acceptOBInstrBiz.acceptInstr(Long.parseLong(transInfo.getInstructionNo()), OBConstant.SettInstrStatus.DEAL, transInfo.getInputUserID());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    private void refuseFinanceInstruct(TransCurrentDepositInfo transInfo) throws Exception
    {
        try
        {
            TransInfo acceptOBInstrBiz = new TransInfo();
            acceptOBInstrBiz.refuseInstr(Long.parseLong(transInfo.getInstructionNo()),"����ָ��ִ��ʧ��",transInfo.getInputUserID(),transInfo.getExecuteDate());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }        
    private void updatePreDrawInterest(long accountID, double newPreDrawInterest, Timestamp newClearInterestDate, Connection conn) throws Exception
    {
		
		PreparedStatement ps = null;
		
		StringBuffer buffer = new StringBuffer();
		buffer.append("UPDATE sett_subaccount \n");
		buffer.append(" SET AF_mPreDrawInterest = ? \n");
		if(newClearInterestDate != null)
		{
			buffer.append(", dtClearInterest = ? \n");
		}
		buffer.append(" WHERE naccountid = ? \n");
		Log.print(buffer.toString());
		
		try
		{
			ps = conn.prepareStatement(buffer.toString());
			int index = 1;
			ps.setDouble(index++, newPreDrawInterest);
			if(newClearInterestDate != null)
			{
				ps.setTimestamp(index++, newClearInterestDate);
			}
			ps.setLong(index++, accountID);
			ps.executeUpdate();
		}
		finally
		{
			if (ps != null)
			{
				ps.close();
				ps = null;
			}
		}
    }
     
	
}