package com.iss.itreasury.project.wisgfc.ebank.special.bizlogic;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Iterator;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.ebank.obfinanceinstr.dao.OBFinanceInstrDao;
import com.iss.itreasury.ebank.obfinanceinstr.dataentity.AccountBalanceInfo;
import com.iss.itreasury.ebank.obfinanceinstr.dataentity.FinanceInfo;
import com.iss.itreasury.ebank.obfinanceinstr.dataentity.OpenDateInfo;
import com.iss.itreasury.ebank.util.NameRef;
import com.iss.itreasury.ebank.util.OBConstant;
import com.iss.itreasury.project.wisgfc.ebank.special.dao.ConsignReceiveDao;
import com.iss.itreasury.project.wisgfc.ebank.special.dataentity.ConsignReceiveInfo;
import com.iss.itreasury.settlement.bizdelegation.AccountSystemDelegation;
import com.iss.itreasury.settlement.dataentity.AccountSystemInfo;
import com.iss.itreasury.settlement.setting.bizlogic.OBCommitTimeBiz;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log4j;
import com.iss.system.dao.PageLoader;

/**
 * @author xlchang 2010-11-29
 * ί���տ������
 *
 */
public class ConsignReceiveBiz {
	private static Log4j log4j = new Log4j(Constant.ModuleType.EBANK);
	private ConsignReceiveDao dao = new ConsignReceiveDao();
		
	/**
	 * ����ί���տ�
	 * @param info
	 * @throws IException
	 */
	public void add(ConsignReceiveInfo info) throws IException {
		try {
			dao.add(info);
		}catch (ITreasuryDAOException e) {
			throw new IException("����ʧ�ܣ�");
		} 		
	}
	
	/**
	 * ����ί���տ�
	 * @param info
	 * @throws IException
	 */
	public void update(ConsignReceiveInfo info) throws IException {
		try {				
			dao.update(info);					
		}catch (ITreasuryDAOException e) {
			throw new IException("����ʧ�ܣ�");
		} 		
	}
	
	/**
	 * ɾ��ί���տ�
	 * @param info
	 * @throws IException
	 */
	public void del(ConsignReceiveInfo info) throws IException {
		try {
			dao.update(info);			
		}catch (ITreasuryDAOException e) {
			throw new IException("ɾ��ʧ�ܣ�");
		} 		
	}	
		
	/**
	 * ��ѯί���տ���Ϣ��ί���տ��Ĭ��������
	 * @param info
	 * @return
	 * @throws IException
	 */
	public Collection startDefFindByCondition(ConsignReceiveInfo info) throws IException{
		Collection c = null;
		try {
		ConsignReceiveInfo qInfo = new ConsignReceiveInfo();
    	qInfo.setNOfficeID(info.getNOfficeID());
    	qInfo.setNCurrencyID(info.getNCurrencyID());
    	qInfo.setNPayeeClientID(info.getNPayerClientID());
    	if (info.getQ_NStatus() > 0) {	
    		qInfo.setNStatus(info.getQ_NStatus());
    	}      
     	String strStatus =  OBConstant.SettInstrStatus.SAVE + "," + OBConstant.SettInstrStatus.SUBMIT + ","
     			+ OBConstant.SettInstrStatus.CONFIRM + "," + OBConstant.SettInstrStatus.REFUSE;
    	c = dao.findByCondition(qInfo,strStatus);    	
		} catch (Exception e) {
			e.printStackTrace();			
			throw new IException("��ѯί���տ���Ϣʧ�ܣ�");
		}
    	return c;			
	}
	
	/**
	 * ��ѯί���տ���Ϣ��ί���տ�ȷ��Ĭ��������
	 * @param info
	 * @return
	 * @throws IException
	 */
	public Collection confirmDefFindByCondition(ConsignReceiveInfo info, long nClientID) throws IException{	
		try {
			ConsignReceiveInfo qInfo = new ConsignReceiveInfo();
			qInfo = new ConsignReceiveInfo();
			qInfo.setNOfficeID(info.getNOfficeID());
	    	qInfo.setNCurrencyID(info.getNCurrencyID());
	    	//qInfo.setNStatus(OBConstant.SettInstrStatus.SUBMIT);
	    	qInfo.setNPayerClientID(nClientID);    		
	    	String strStatus =  OBConstant.SettInstrStatus.SUBMIT + "," + OBConstant.SettInstrStatus.CONFIRM + ","
	    			+ OBConstant.SettInstrStatus.REFUSE;
	    	return dao.findByCondition(qInfo,strStatus);		
		} catch (Exception e) {
			e.printStackTrace();			
			throw new IException("��ѯί���տ���Ϣʧ�ܣ�");
		}
		
    		
	}
		
		
	/**
	 * ����id��ѯί���տ���Ϣ
	 * @param info
	 * @return
	 * @throws ITreasuryDAOException
	 */
	public ConsignReceiveInfo findByID(long id) throws IException {
		ConsignReceiveInfo result = null;
		try {
			result = (ConsignReceiveInfo)dao.findByID(id, ConsignReceiveInfo.class);
		} catch (ITreasuryDAOException e) {
			throw new IException("��ѯί���տ���Ϣʧ�ܣ�");
		} 
		return result;
	}
	
	/**
	 * ί���տ�ȷ�� ����һ����תҵ�񣬸���ί���տ������Ϣ
	 * @param info
	 * @throws IException 
	 */
	public FinanceInfo createFinanceInfo(ConsignReceiveInfo info) throws IException {
		long instrID = -1;
		FinanceInfo result = null;
		try {
			
			//��װ��תҵ����Ϣ
			FinanceInfo fInfo = new FinanceInfo();
			ConsignReceiveInfo dbInfo = findByID(info.getId());
			fInfo.setOfficeID(dbInfo.getNOfficeID());// ���װ��´�
			fInfo.setCurrencyID(dbInfo.getNCurrencyID());// ���ױ���		
			fInfo.setClientID(dbInfo.getNPayerClientID());// �����ύ��λ
			fInfo.setTransType(OBConstant.SettInstrType.CAPTRANSFER_INTERNALVIREMENT);  // ���Ͻ�������
			fInfo.setRemitType(OBConstant.SettRemitType.INTERNALVIREMENT);// ��ʽ
			fInfo.setAmount(dbInfo.getMAmount());  // ���׽��
			fInfo.setExecuteDate(info.getDTExecute()); // ִ����
			
			//���������ύʱ��Ч��
			OBCommitTimeBiz commitTime = new OBCommitTimeBiz();
			boolean isCommitTimes = commitTime.validateOBCommitTime(info.getDTExecute(),dbInfo.getNOfficeID(),dbInfo.getNCurrencyID());
			if(isCommitTimes == false){
				fInfo.setExecuteDate(DataFormat.getNextDate(fInfo.getExecuteDate(),1));
			}
			
			fInfo.setNote(NameRef.getAbstractNameByID(info.getNAbstractID())); // �����;/ժҪ
			fInfo.setStatus(OBConstant.SettInstrStatus.SAVE); // ָ��״̬
			fInfo.setConfirmDate(fInfo.getExecuteDate()); //ȷ������
			fInfo.setConfirmUserID(info.getNConfirmUserID()); //ȷ����ID	
			fInfo.setPayerAcctID(dbInfo.getNPayerAcctID()); // ����˻�ID				
			fInfo.setPayeeAcctID(dbInfo.getNPayeeAcctID()); //�տ�˻�ID	
			
			addCapitalTrans(fInfo);			
			
			//��װί���տ�ҵ����Ϣ
			ConsignReceiveInfo cInfo = new ConsignReceiveInfo();
			cInfo.setId(info.getId());
			cInfo.setNStatus(OBConstant.SettInstrStatus.CONFIRM);
			cInfo.setNConfirmUserID(info.getNConfirmUserID());
			cInfo.setDTConfirm(fInfo.getConfirmDate());
			cInfo.setDTExecute(info.getDTExecute());
			cInfo.setSMemo(info.getSMemo());
			try{
				instrID = dao.createFinaneInfo(fInfo, cInfo);	
			} catch (Exception e) {
				throw new IException("ί���տ�ȷ��ʧ�ܣ�",e);
			}
			
			OBFinanceInstrDao obFinanceInstrDao = new OBFinanceInstrDao();
			
			try{
				result = obFinanceInstrDao.findByInstructionID(instrID,info.getNCurrencyID());	
			} catch (Exception e) {
				throw new IException("ί���տ�ȷ�ϳɹ�����ѯ���ɵ���ת����ʧ�ܣ���ͨ������ָ���ѯ��",e);
			}
		} catch (IException ie) {
			throw ie;
		} catch (Exception e) {
			throw new IException("ί���տ�ȷ��ʧ�ܣ�",e);
		}
		return result;
	}

	
	/**
	 * ͨ��ָ��id�ж��Ƿ���ί���տ����ɵ�ҵ��
	 * @param instrID  ָ��id
	 * @return
	 */
	public boolean isConsignReceive (long instrID) throws IException {
		boolean flag = false;
		try {
	        ConsignReceiveInfo tempInfo = new ConsignReceiveInfo();
	        tempInfo.setNInstrID(instrID);        
	        Collection c = dao.findByCondition(tempInfo);
	        if (c != null && c.size() > 0) {
	        	flag = true;
	        }	     
		} catch (Exception e) {
			throw new IException("ί���տ��ѯʧ�ܣ�",e);
		}
        return flag;        
	}
	
	/**
	 * ɾ����ί���տ����ɵ���תʱ��������Ӧ��ί���տ�ҵ��
	 * @param instrID ָ��id
	 * @return
	 * @throws IException
	 */
	public boolean delInstr (long instrID, long nUserID) throws IException {
		boolean flag = false;
		try {
	        ConsignReceiveInfo tempInfo = new ConsignReceiveInfo();
	        ConsignReceiveInfo info = new ConsignReceiveInfo();
	        tempInfo.setNInstrID(instrID);        
	        Collection c = dao.findByCondition(tempInfo);	        
	        if (c != null && c.size() > 0) {
	        	Iterator t = c.iterator();
	        	info = (ConsignReceiveInfo)t.next();	 
	        	if (info != null && info.getId() > 0) {
	        		 tempInfo.setId(info.getId());
	        		 tempInfo.setNInstrID(-1);   
	        		 tempInfo.setNStatus(OBConstant.SettInstrStatus.REFUSE);
	        		 tempInfo.setNConfirmUserID(nUserID);
	        		 tempInfo.setDTConfirm(Env.getSystemDate());
	        		 update(tempInfo);
	        	}
	        }	     
		} catch (Exception e) {
			throw new IException("����ί���տ�ҵ��ʧ�ܣ�",e);
		}
        return flag;        
	}
		
	/**
	 * ��ҳ��ѯ
	 * @param info ��ѯ����
	 * @return
	 * @throws Exception
	 */
	public PageLoader queryConsignReceiveInfo(ConsignReceiveInfo info) throws Exception
	{
		PageLoader pageLoader = null;
		
		try {	
			pageLoader = dao.queryConsignReceiveInfo(info);
		} catch (Exception e) {
			throw new IException("��ѯί���տ�ҵ��ʧ�ܣ�",e);
		}
		
		return pageLoader;
	}
	
	  
    /**
     * �����ʽ𻮲���Ϣ
     * ����ԭOBFinanceInstrEJB.java��addCapitalTrans����
     * @param FinanceInfo
     * @return long ������ָ����ˮ��,��С��0��ʾ�������޸�ʧ��
     * @exception Exception
     */
    public long addCapitalTrans(FinanceInfo info) throws RemoteException, Exception
    {
        long lReturn = -1;
        AccountBalanceInfo accountBalanceInfo = null;
        OpenDateInfo openDateInfo = null;
       
        try
        {        	
            if (info != null)
            {
                OBFinanceInstrDao obFinanceInstrDao = new OBFinanceInstrDao();
                if (info.getTransType() != OBConstant.SettInstrType.FIXEDTOCURRENTTRANSFER && info.getTransType() != OBConstant.SettInstrType.NOTIFYDEPOSITDRAW)
                {
                    accountBalanceInfo = obFinanceInstrDao.getCurrBalanceByAccountID(info.getPayerAcctID(), info.getCurrencyID(), info.getID());
                    
                    if (info.getTransType()==OBConstant.SettInstrType.APPLYCAPITAL)
                    {	
                    	//У���������Ƿ���ڻ������
	                    log4j.info("�������=" + (accountBalanceInfo.getOverdraftAmount()+accountBalanceInfo.getUsableBalance()));
	                    log4j.info("�������=" + info.getAmount());
	                    if ((accountBalanceInfo.getOverdraftAmount()+accountBalanceInfo.getUsableBalance())
	                    		< info.getAmount())
	                    {
	                        throw new IException("OB_EC18");
	                    }
                    }
                    else
                    {
	                    //У���������Ƿ���ڻ������
	                    log4j.info("�������=" + accountBalanceInfo.getUsableBalance());
	                    log4j.info("�������=" + info.getAmount());
	                    	                  
	                    if ((accountBalanceInfo.getOverdraftAmount() + accountBalanceInfo.getUsableBalance())
	                    		< info.getAmount())
	                    {
	                        throw new IException("OB_EC18");
	                    }
                    }
                   
    	        	AccountSystemInfo accountSystemInfo = new AccountSystemInfo();
    	        	accountSystemInfo.setNAccountId(info.getPayerAcctID());
    	        	double dCPF2Amount = obFinanceInstrDao.getUsableBalanceByAccountID(info.getPayerAcctID(), info.getCurrencyID(), info.getID());
    	        	accountSystemInfo.setDPayAmount(info.getAmount()+dCPF2Amount);
    	        	if(!new AccountSystemDelegation().isPassAccountSystemVerify(accountSystemInfo)){
    	        		throw new IException("OB_EC18");
    	        	}
                }
                
                if (info.getTransType()!=OBConstant.SettInstrType.APPLYCAPITAL)
	            {
	                //���ڲ����ڽ��㿪����֮ǰ
	                openDateInfo = obFinanceInstrDao.getOpenDate(info.getOfficeID(), info.getCurrencyID());
	
	                if (openDateInfo == null)//����ȡ�����ػ�ʱ��
	                {	                    
	                	throw new IException("OB_EC30");
	                } 
	                if (openDateInfo.getSystemStatusID() == 1)//����
	                {
	                    if (info.getExecuteDate().getTime() - openDateInfo.getOpenDate().getTime() < 0)
	                    {
	                        log4j.info("ExecuteDate=" + info.getExecuteDate().toString());
	                        log4j.info("openDate=" + openDateInfo.getOpenDate().toString());
	                        throw new IException("OB_EC31");
	                    }
	                }
	                else
	                //�ػ�
	                {
	                    if (info.getExecuteDate().getTime() - openDateInfo.getOpenDate().getTime() <= 0)
	                    {
	                        throw new IException("OB_EC17");
	                    }
	                }	               
	            }
               
            }
		
			if(null != info.getSignatureValue() && info.getSignatureValue().length() > 0){
				//����ǩ��ֵ
				new OBFinanceInstrDao().updateSignatureInfo(info);
			}
			
        } 
        catch (Exception e)
        {        	
            log4j.error(e.toString());
            throw new IException(e.getMessage(), e);
        }

        return lReturn;

    }
	
    
	
}
