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
 * 自动指令接受处理消息驱动Bean
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
    	Log.print("--------------接受到消息--------------");
        ObjectMessage msg = null;
       
    	try
		{
			if (inMessage != null && inMessage.getJMSRedelivered() == true)
			{
				Log.print("--------------注意：此消息为重收消息--------------");
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
        	Log.print("--------------进入try--------------");
	        if (inMessage instanceof ObjectMessage)
	        {
	        	Log.print("--------------inMessage instanceof ObjectMessage--------------");
	        	msg = (ObjectMessage) inMessage;	        		        	        	
	        	//接收网银系统的业务信息
	        	if (msg.getObject() instanceof FinanceInfo)
	        	{
	        		System.out.println("--------------接受到网银系统的网银指令通知--------------");
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
			Log.print("---------------接受消息出现异常-----------------");
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

            //此处为自动接收、保存、复核
            if (transInfo.getAutoOperationType() == TransCurrentDepositInfo.Auto_OperationType_SaveAndCheck)
            {
                // 更改网银指令状态为已接收
                receiveFinanceInstruct(transInfo);
                // 自动保存、复核
                currentDepositDelegation.saveAndCheckAutomatically(assembler);
            }
            // 自动取消复核、删除、更改网银状态
            else if (transInfo.getAutoOperationType() == TransCurrentDepositInfo.Auto_OperationType_CancelSaveAndCheck)
            {
                // 自动取消复核、删除
                currentDepositDelegation.cancelAndCheckAutomatically(assembler);
                // 更新网银状态为已拒绝
                refuseFinanceInstruct(transInfo);
            }
        }
        catch (IRollbackException e)
        {
            //TBD:调用同页面处理显示异常相同的方法获取异常的ERR Message，并写入指令日志中并更新指令状态为已拒绝
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
    		//判断是否已经关机
			if (EndDayProcess.getSystemStatusID(financeInfo.getOfficeID(), financeInfo.getCurrencyID()) != Constant.SystemStatus.OPEN)
			{
				Log.print("系统关机办事处编号:" + financeInfo.getOfficeID() + "币种：" + financeInfo.getCurrencyID());
				throw new IRollbackException(null,"结算系统的已经关机");
			}
			
    		//判断网银指令的执行日和结算系统开机日是否一致
			Timestamp executeDate = null;
			Timestamp systemDate = null;
			executeDate = financeInfo.getExecuteDate();
			Log.print("网银指令的执行日:"+executeDate);
			//获取当前结算系统的开机时间
			try
			{
				systemDate = Env.getSystemDate(financeInfo.getOfficeID(), financeInfo.getCurrencyID());
				if(systemDate == null)
				{
					throw new Exception();
				}
				Log.print("当前结算系统的开机时间:"+systemDate);
			}
			catch(Exception e)
			{
				Log.print("无法获得当前结算系统的开机日期");
				throw new IRollbackException(null,"无法获得当前结算系统的开机日期");
			}
			//比较两时间是否相等
			if(IDate.compareDate(executeDate, systemDate) != 0)
			{
				throw new IRollbackException(null,"网银指令的执行日和结算系统开机日不一致");
			}
        	
        	//发送银行指令前，维护网银指令状态为“处理中”
	    	FinanceInfo financeInfoTemp = new FinanceInfo();
			OBFinanceInstrDao financeInstrDao = new OBFinanceInstrDao();
			
			try
			{
				financeInfoTemp = financeInstrDao.findByID(financeInfo.getID());
				if(financeInfoTemp == null)
				{
					throw new Exception("根据网银指令id："+financeInfo.getID()+"找到的对应的网银指令信息为空");
				}
			}
			catch(Exception e)
			{
				Log.print("根据网银指令id："+financeInfo.getID()+"找不到对应的网银指令信息");
				e.printStackTrace();
				return;
			}
			
			if(financeInfoTemp.getStatus() != OBConstant.SettInstrStatus.CHECK)
			{
				Log.print("根据网银指令id："+financeInfo.getID()+"找到的网银指令信息的状态非“已复核”，不作结算入账处理");
				return;
			}
			
	    	try 
			{
	    		financeInstrDao.updateStatus(financeInfo.getID(), OBConstant.SettInstrStatus.DEAL);
	    		Log.print("维护网银指令状态为“处理中”成功");
			} 
	    	catch (Exception e1) 
			{
	    		Log.print("维护网银指令状态为“处理中”时出现异常，不作结算入账处理");
	    		return;
			}
	    	
	    	//工行icbc项目要求，入账时间不能晚于15:00，判断当前接收时间是否符合要求
	    	if(!this.checkTime())
	    	{
	    		//如果时间不符合要求，修改指令状态为“已拒绝”，直接返回，不作入账处理
	    		throw new IRollbackException(null,"结算系统的入账时间无效");
	    	}
	    	
	        TransCurrentDepositDelegation currentDepositDelegation = new TransCurrentDepositDelegation();
	        //自动保存、复核
	        currentDepositDelegation.saveAndCheckAutomatically(financeInfo, Constant.MachineUser.InputUser);
        }
        catch (IRollbackException e)
        {
            e.printStackTrace();
        	//TBD:调用同页面处理显示异常相同的方法获取异常的ERR Message，并写入指令日志中并更新指令状态为已拒绝
        	OBFinanceInstrDao financeInstrDao = new OBFinanceInstrDao();
        	try 
			{
        		financeInstrDao.refuseInstr(
        				financeInfo.getID(), 
						"业务处理失败："+e.getMessage(),
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
			Log.print("获取网银指令的结算入账时间出错。");
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
            acceptOBInstrBiz.refuseInstr(Long.parseLong(transInfo.getInstructionNo()),"银行指令执行失败",transInfo.getInputUserID(),transInfo.getExecuteDate());
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