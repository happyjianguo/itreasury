package com.iss.itreasury.fcinterface.bizlogic;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Iterator;

import com.iss.itreasury.ebank.obfinanceinstr.dao.OBFinanceInstrDao;
import com.iss.itreasury.ebank.obfinanceinstr.dataentity.FinanceInfo;
import com.iss.itreasury.ebank.util.OBConstant;
import com.iss.itreasury.fcinterface.dao.FcinterfaceDao;
import com.iss.itreasury.fcinterface.dataentity.ExtSystemLogInfo;
import com.iss.itreasury.fcinterface.util.EPConstant;
import com.iss.itreasury.settlement.bizdelegation.TransCurrentDepositDelegation;
import com.iss.itreasury.settlement.obinstruction.bizlogic.TransInfo;
import com.iss.itreasury.settlement.setting.bizlogic.CommissionSettingBiz;
import com.iss.itreasury.settlement.setting.bizlogic.SettInterestRateBiz;
import com.iss.itreasury.settlement.setting.dataentity.CommissionSettingInfo;
import com.iss.itreasury.settlement.setting.dataentity.InterestRateInfo;
import com.iss.itreasury.settlement.transcurrentdeposit.dataentity.TransCurrentDepositAssembler;
import com.iss.itreasury.settlement.transcurrentdeposit.dataentity.TransCurrentDepositInfo;
import com.iss.itreasury.settlement.transfixeddeposit.dataentity.NotifyDepositInformInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.ConfigConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log4j;

/**
 * �����ӿ�ָ����մ���
 * @author xiangzhou
 * 
 */
public class FcinterfaceHandle {
	
	private static Log4j log4j = new Log4j(Constant.ModuleType.SETTLEMENT);
	
	/**
	 * ����ָ���
	 * @param financeInfo
	 * @return
	 */
	public long handle(FinanceInfo financeInfo) throws Exception
	{		
		long lStatusID = -1;
		OBFinanceInstrDao dao = new OBFinanceInstrDao();
		Connection conn=null;
		try {
			conn = Database.getConnection();
			//conn.setAutoCommit(false);			
			lStatusID = saveAndCheck(financeInfo,conn);			
			if(lStatusID > 0){			
				if(isAutoAcceptInstr(financeInfo.getSource())){
					financeInfo=dao.findByID(lStatusID,financeInfo.getConfirmUserID(),financeInfo.getCurrencyID());
					lStatusID = acceptInstr(financeInfo);
				}	
			}			
		}
		catch (Exception e) {			
			e.printStackTrace();
			lStatusID=-1;
			throw e;
		}finally{
			try {				
				if(conn!=null)
				{
					conn.close();
					conn=null;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}			
		}
		return lStatusID;
	}
	
	/**
	 * ��������ָ���Ϊ����״̬
	 * @param financeInfo
	 * @return
	 */
	public long saveAndCheck(FinanceInfo financeInfo,Connection con){
		
		FcinterfaceDao dao = new FcinterfaceDao();
		ExtSystemLogInfo log = new ExtSystemLogInfo();
		
		long lReturn = -1;
		try {
			if(dao.checkApplyCode(financeInfo)) throw new IException("�ⲿ����ָ����ظ�");
			lReturn = dao.saveAndCheck(financeInfo,con);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.setRemark(e.getMessage());
			log4j.info("����ӿ�ָ�����ʧ�ܣ�����ԭ��Ϊ��"+e.getMessage());
			
		}finally{
			try {
				log.setSource(financeInfo.getSource());
				log.setApplycode(financeInfo.getApplyCode());
				if(lReturn > 0){
					log.setNstatus(EPConstant.EPInstructionStatus.SENDSUCCESS);
				}else{
					log.setNstatus(EPConstant.EPInstructionStatus.FAIL);
				}
				log(log);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return lReturn;
	}
	
	/**
	 * ����ҵ���¼
	 * @param financeInfo
	 * @return
	 */
	public long acceptInstr(FinanceInfo financeInfo)throws Exception{
		
		long lReturn = -1;
		long lUserID = -1;
		long lCurrencyID = -1;
		long JIZHI = -1;
		long JIHE = -1;
		double dAmount = 0.00;
		double ratingResult = 0.00;
		double commissionAmount = 0.00;
		long bankID = -1;
		long remitType = -1;
		long remitSpeed = -1;
		
		lCurrencyID = Constant.CurrencyType.RMB;	//Ĭ��Ϊ�����
		
		FinanceInfo info = null;
		OBFinanceInstrDao dao = new OBFinanceInstrDao();
		SettInterestRateBiz biz = new SettInterestRateBiz();
		TransInfo transInfo = new TransInfo();
		
		ExtSystemLogInfo log = new ExtSystemLogInfo();
		
		try {
			Timestamp tsOldModify = null;
			tsOldModify = financeInfo.getDtModify();
			
			info=dao.findByID(financeInfo.getID(),lUserID,lCurrencyID);
			
			//JIZHI = getMachineUser(info.getPayerAcctID(),1);
			JIZHI = new FcinterfaceDao().checkReceiveUser(financeInfo);
			if(JIZHI < 0 ) throw new Exception("����ָ�������δ����");
			JIHE = getMachineUser(info.getPayerAcctID(),2);
			//if(JIHE < 0 ) throw new Exception("�����û���ȡʧ��");
			
			//���ݴ�����ͺ���ʼʱ���ѯ���ݱ��е�����
			InterestRateInfo interestRateInfo = new InterestRateInfo();
			interestRateInfo.setnOfficeid(info.getOfficeID());
			interestRateInfo.setnCurrencyid(info.getCurrencyID());
			interestRateInfo.setDtEffective(info.getExecuteDate());
			ratingResult = biz.findLastInterestRate(interestRateInfo);
			info.setDepositRate(ratingResult);
			
			remitType = info.getRemitArea();
			remitSpeed = info.getRemitSpeed();
			
			//����������
			if ( Config.getBoolean(ConfigConstant.SETT_TRANSCOMMISSION,false) )
			{
				dAmount = info.getAmount();
				CommissionSettingBiz biz1 = new CommissionSettingBiz();
				CommissionSettingInfo tempinfo1 = new CommissionSettingInfo();
				tempinfo1.setOfficeID(1);
				tempinfo1.setCurrencyID(1);
				tempinfo1.setStatusId(Constant.RecordStatus.VALID);
				Collection c1 = biz1.findAll(tempinfo1);
				Iterator it1 = null;
				if( c1 != null)
				{
					it1 = c1.iterator();
				}
				
				if( dAmount > 0)
				{
					if( it1 != null && it1.hasNext() )
					{
						while( it1.hasNext() )
						{
							CommissionSettingInfo commInfo = (CommissionSettingInfo)it1.next();
							
							System.out.println(commInfo.getCommissionType());
							System.out.println(commInfo.getIsUrgent());
							
							if(commInfo.getCommissionType() == 1 && commInfo.getIsUrgent() == 1)
							{
								if(remitType == 1 && remitSpeed == 1)
								{
									if(dAmount > commInfo.getAmountFrom() && dAmount <= commInfo.getAmountTo())
									{
										commissionAmount = dAmount*commInfo.getCommissionRate()/10000.00+commInfo.getCommissionAmount();
									}
								}
							}
							if(commInfo.getCommissionType() == 1 && commInfo.getIsUrgent() == 2)
							{
								if( remitType == 1 && remitSpeed == 2)
								{
									if( dAmount > commInfo.getAmountFrom() && dAmount <= commInfo.getAmountTo())
									{
										commissionAmount = dAmount*commInfo.getCommissionRate()/10000.00+commInfo.getCommissionAmount();
									}
								}
							}
							if(commInfo.getCommissionType() == 2 && commInfo.getIsUrgent() == 1)
							{
								if(remitType == 2 && remitSpeed == 1)
								{
									if( dAmount > commInfo.getAmountFrom() && dAmount <= commInfo.getAmountTo())
									{
										commissionAmount = dAmount*commInfo.getCommissionRate()/10000.00+commInfo.getCommissionAmount();
									}
								}
							}
							if(commInfo.getCommissionType() == 2 && commInfo.getIsUrgent()== 2)
							{
								if(remitType == 2 && remitSpeed == 2)
								{
									if( dAmount >commInfo.getAmountFrom() && dAmount <= commInfo.getAmountTo())
									{
										commissionAmount = dAmount*commInfo.getCommissionRate()/10000.00+commInfo.getCommissionAmount();
									}
								}
							}
						}
					}
		
				}
			
			}
			
			//����ҵ���¼����������ָ��
			long lTempSettInstrStatus = info.getStatus();
		    FinanceInfo financeEbankInfo = new FinanceInfo();
		    financeEbankInfo = transInfo.findEbankInstr(info.getID(),lUserID,lCurrencyID);
		    info.setPayerAcctID( financeEbankInfo.getPayerAcctID() );
		    info.setPayeeAcctID( financeEbankInfo.getPayeeAcctID() );
		    info.setInterestPayeeAcctID(financeEbankInfo.getInterestPayeeAcctID());
		    info.setSDepositInterestToAccountID(financeEbankInfo.getSDepositInterestToAccountID());
		    boolean flag1=transInfo.obModifyCheckIsTouched(info.getID(),tsOldModify);
		    
		    //����ҵ������ָ�����޸�ʱ��
		    transInfo.updateObModifyDate(info.getID());
		    
	 		if(flag1!=true){
	 			
	 			//�ڲ�ת��
	 			if(info.getDefaultTransType()==SETTConstant.TransactionType.INTERNALVIREMENT)
				{
					TransCurrentDepositInfo currInfo  = new TransCurrentDepositInfo();
					currInfo = transInfo.transCurrent(info,JIZHI);	//����
					currInfo.setCheckUserID(JIHE);
					currInfo.setTransactionTypeID(SETTConstant.TransactionType.INTERNALVIREMENT);
					currInfo.setCommissionAmount( commissionAmount );
					
					TransCurrentDepositAssembler assembler  =  new TransCurrentDepositAssembler(currInfo);
					/*modify by kevin(������) 2012-05-31 ����ӿ�ָ��ٶԽ������˶���ֱ�ӶԽӵ���˾��*/
					//TransCurrentDepositForEbankDelegation depositDelegation  =  new TransCurrentDepositForEbankDelegation();
					TransCurrentDepositDelegation depositDelegation=  new TransCurrentDepositDelegation();
					
					// �Զ����桢����
	                System.out.println("---------��ʼ�Զ����渴��------------");
			        try{
			           depositDelegation.saveAndCheckAutomaticallyforEbank( assembler );
			           lReturn = 1;
			        }
					catch( Exception exp )
					{
						//�Զ����渴��ʧ�ܻع�
						exp.printStackTrace();
						OBFinanceInstrDao oBFinanceInstrDao= new OBFinanceInstrDao();
						oBFinanceInstrDao.acceptInstr(info.getID(),lTempSettInstrStatus,JIZHI);
						throw new Exception(exp.getMessage());
					}
					
					System.out.println("---------�Զ����渴��---����ָ�����------------");
					
				}
				//���и���
				else if(info.getDefaultTransType()==SETTConstant.TransactionType.BANKPAY || info.getDefaultTransType()==SETTConstant.TransactionType.BANKPAY_DOWNTRANSFER)
				{
					TransCurrentDepositInfo currInfo  = new TransCurrentDepositInfo();
					currInfo  =  transInfo.transCurrent(info,JIZHI);
					currInfo.setCheckUserID(JIHE);
					
					if(info.getDefaultTransType()==SETTConstant.TransactionType.BANKPAY_DOWNTRANSFER)
					{
						currInfo.setTransactionTypeID(SETTConstant.TransactionType.BANKPAY_DOWNTRANSFER);
					}
					else
					{
						currInfo.setTransactionTypeID(SETTConstant.TransactionType.BANKPAY);
					}
					
					//���ƥ�俪����
					bankID = getBranchMatching(info.getPayeeBankName());
					if(bankID < 0) throw new Exception("���ƥ�俪����ʧ��");
					
					currInfo.setCommissionAmount(commissionAmount);
					currInfo.setCommissionType(remitType);
					currInfo.setIsUrgent(remitSpeed);
					currInfo.setBankID(bankID);
					
					TransCurrentDepositAssembler assembler  =  new TransCurrentDepositAssembler(currInfo);
					/*modify by kevin(������) 2012-05-31 ����ӿ�ָ��ٶԽ������˶���ֱ�ӶԽӵ���˾��*/
					//TransCurrentDepositForEbankDelegation depositDelegation  =  new TransCurrentDepositForEbankDelegation();
					TransCurrentDepositDelegation depositDelegation=  new TransCurrentDepositDelegation();
					
					
					// �Զ����桢����
	                System.out.println("---------��ʼ�Զ����渴��------------");
	                try{
		                  depositDelegation.saveAndCheckAutomaticallyforEbank( assembler );
				          lReturn = 1;
	                }
					catch( Exception exp )
					{
						//�Զ����渴��ʧ�ܻع�
						exp.printStackTrace();
						OBFinanceInstrDao oBFinanceInstrDao= new OBFinanceInstrDao();
						oBFinanceInstrDao.acceptInstr(info.getID(),lTempSettInstrStatus,JIZHI);
						throw new Exception(exp.getMessage());
					}
	                  
					System.out.println("---------�Զ����渴��---����ָ�����------------");
					
				}
	 			
	 		}else{
	 			log.setRemark("���ݱ��������޸�");
	 		}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.setRemark(e.getMessage());
			log4j.info("����ҵ���¼ʧ�ܣ�����ԭ��Ϊ��"+e.getMessage());			
			throw e;
		}finally{
			
			try {
				//�����־
				log.setSource(info.getSource());
				log.setApplycode(info.getApplyCode());
				if(lReturn > 0){
		            log.setNstatus(EPConstant.EPInstructionStatus.SETTSUCCESS);
				}else{
					log.setNstatus(EPConstant.EPInstructionStatus.FAIL);
				}
				log(log);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return lReturn;
	}
	
	/**
	 * ��ȡ�Ƿ��Զ�����ָ��
	 * @param extSystemID
	 * @return
	 */
	public boolean isAutoAcceptInstr(long extSystemID) throws Exception{
		boolean bool = true;
		FcinterfaceDao dao = new FcinterfaceDao();
		try {
			bool = dao.isAutoAcceptInstr(extSystemID);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bool;
	}
	
	/**
	 * �����ⲿϵͳcode��ѯ�ⲿϵͳ��Ӧ��ID
	 * @param extSystemCode
	 * @return
	 * @throws Exception
	 */
	public long getExtSystemIDByCode(String extSystemCode) throws Exception{
		long extSystemID = -1;
		FcinterfaceDao dao = new FcinterfaceDao();
		try {
			extSystemID = dao.getExtSystemIDByCode(extSystemCode);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return extSystemID;
	}
	
	/**
	 * ���ƥ�俪����
	 * @param bankName
	 * @return
	 * @throws Exception 
	 */
	public long getBranchMatching(String bankName) throws Exception{
		FcinterfaceDao dao = new FcinterfaceDao();
		return dao.branchMatchForBankName(bankName);
	}
	
	/**
	 * �����־
	 * @param log
	 * @throws Exception
	 */
	public void log(ExtSystemLogInfo log) throws Exception{
		FcinterfaceDao dao = new FcinterfaceDao();
		dao.addLog(log);
	}
	
	/**
	 * ��ȡ���ơ������û����ְ��´���
	 * @param username
	 * @param account
	 * @return
	 * @throws Exception 
	 */
	public long getMachineUser(long account,long usertype) throws Exception{
		FcinterfaceDao dao = new FcinterfaceDao();
		return dao.getMachineUser(account,usertype);
	}	
	
	public NotifyDepositInformInfo transfer(FinanceInfo info) throws Exception{
		NotifyDepositInformInfo depositInfo = new NotifyDepositInformInfo();
		depositInfo.setSApplyCode(info.getApplyCode());
		depositInfo.setAmount(info.getAmount());
		depositInfo.setOfficeID(info.getOfficeID());
		depositInfo.setDepositNo(info.getDepositNo());
		depositInfo.setNotifyDate(DataFormat.getDateString(info.getTsNoticeDate()));
		depositInfo.setStatusID(SETTConstant.NotifyInformStatus.SAVE);
		depositInfo.setUserID(info.getConfirmUserID());
		depositInfo.setLSource(info.getSource());
		return depositInfo;
	}	
}
