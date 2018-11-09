/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2004-3-4
 */
package com.iss.itreasury.securities.register.bizlogic;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.securities.deliveryorder.dataentity.DeliveryOrderInfo;
import com.iss.itreasury.securities.exception.SECInvalidRecordException;
import com.iss.itreasury.securities.exception.SecuritiesDAOException;
import com.iss.itreasury.securities.exception.SecuritiesException;
import com.iss.itreasury.securities.register.dao.RegisterDAOFactory;
import com.iss.itreasury.securities.register.dao.SEC_RepurchaseRegisterDAO;
import com.iss.itreasury.securities.register.dataentity.RepurchaseRegisterInfo;
import com.iss.itreasury.securities.register.exception.CapitalHasReturnException;
import com.iss.itreasury.securities.register.exception.NoSuchRegisterOperationException;
import com.iss.itreasury.securities.register.exception.TransAmontNotMatchException;
import com.iss.itreasury.securities.setting.dataentity.TransactionTypeInfo;
import com.iss.itreasury.securities.util.SECConstant;
import com.iss.itreasury.securities.util.SecuritiesDAO;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Log4j;
/**
 * @author yehuang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class RepurchaseRegisterBean extends RegisterBean {
	private Log4j log = new Log4j(Constant.ModuleType.SECURITIES, this);	
	/**
	 * �����ǼǱ����ڲ����Ĵ���
	 * @param regiesterInfo �ǼǱ���Ϣ
	 * */	

	
	/**
	 * �ǼǱ��Ǽǲ�����ͳһ����
	 * @param regiesterInfo �ǼǱ���Ϣ
	 * */
	public void cancelRegister(DeliveryOrderInfo doInfo) throws SecuritiesException{
		log.debug("-------��ʼ�ǼǱ�����::ȡ���ǼǴ���---------");		
		SecuritiesDAO dao = RegisterDAOFactory.getRegisterDAO(doInfo.getTransactionTypeInfo());
		try {
			long regID = doInfo.convertToRegisterInfo(REGISTER_OPERATION_TYPE_CANCELREGISTER).getId();
			RepurchaseRegisterInfo foundInfo = null;
			foundInfo = (RepurchaseRegisterInfo) dao.findByID(regID, RepurchaseRegisterInfo.class);
			log.debug("-------��Ҫ�����µĵǼǱ���ϢΪ:"+foundInfo);			
			if(foundInfo == null || foundInfo.getId() < 0 || foundInfo.getStatusID() != SECConstant.BusinessAttributeStatus.SAVED){
				throw new SECInvalidRecordException("");
			}			
			if(foundInfo.getLastDeliveryOrderID() > 0)//�ñ�ҵ���ѷ���	
				throw new SecuritiesException("Sec_E120",null);						
			
			dao.delete(regID);
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}
		log.debug("-------�����ǼǱ�����::ȡ���ǼǴ���---------");		
	}		
	
	
	/**
	 * �����ǼǱ��Ǽ�ȡ������
	 * @param regiesterInfo �ǼǱ���Ϣ
	 * */	
	public void cancelMaturate(DeliveryOrderInfo doInfo) throws SecuritiesException{
		log.debug("-------��ʼ�����ǼǱ�����::ȡ�����ڴ���---------");		
		RepurchaseRegisterInfo info = (RepurchaseRegisterInfo)doInfo.convertToRegisterInfo(RegisterBean.REGISTER_OPERATION_TYPE_CANCELMATURATE);
		log.debug("-------�����ǼǱ��������Ϊ:"+info);		
		SecuritiesDAO dao = RegisterDAOFactory.getRegisterDAO(doInfo.getTransactionTypeInfo());
		RepurchaseRegisterInfo foundInfo = null;
		try {
			foundInfo = (RepurchaseRegisterInfo) dao.findByID(info.getId(), RepurchaseRegisterInfo.class);
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}
		if(foundInfo.getId() < 0)
			throw new SECInvalidRecordException("���ڷ���");
			
		log.debug("ȡ�����ڵǼǱ���ϢΪ: " +foundInfo);		
//		//�ñʷ���ҵ���Ѿ���ȡ���������ٱ�ȡ��
//		if(foundInfo.getBalance() == 0.0){
//			throw new RegisterHasCancelException();
//		}
//		
//		//������
//		if(foundInfo.getBalance() != info.getAmount()){
//			throw new TransAmontNotMatchException(info.getAmount(),foundInfo.getBalance());
//		}
		//���µǼǱ���Ϣ
		RepurchaseRegisterInfo updatedInfo = new RepurchaseRegisterInfo();
		updatedInfo.setId(info.getId());
		updatedInfo.setBalance(foundInfo.getAmount());
		//ĩ�ν�����=null
		updatedInfo.setLastDeliveryOrderID(-1);
		log.debug("ȡ�����ڵǼǱ�������ϢΪ: " +updatedInfo);		
		try {
			dao.update(updatedInfo);
		} catch (ITreasuryDAOException e1) {
			throw new SecuritiesDAOException(e1);
		}
		log.debug("-------���������ǼǱ�����::ȡ�����ڴ���---------");		
	}


	public void confirmApplication(DeliveryOrderInfo doInfo) throws SecuritiesException {
		 throw new NoSuchRegisterOperationException();
	}


	public void cancelConfirmApplication(DeliveryOrderInfo doInfo) throws SecuritiesException {
		 throw new NoSuchRegisterOperationException();
	}


	public void sellOut(DeliveryOrderInfo doInfo) throws SecuritiesException {
		 throw new NoSuchRegisterOperationException();
	}

	public void cancelSellOut(DeliveryOrderInfo doInfo) throws SecuritiesException {
		 throw new NoSuchRegisterOperationException();
	}


	public void maturate(DeliveryOrderInfo doInfo) throws SecuritiesException{
		log.debug("-------��ʼ�����ǼǱ�����::���ڴ���---------");		
		RepurchaseRegisterInfo info = (RepurchaseRegisterInfo)doInfo.convertToRegisterInfo(RegisterBean.REGISTER_OPERATION_TYPE_MATURATE);
		log.debug("-------�����ǼǱ��������Ϊ:"+info);
		SecuritiesDAO dao = RegisterDAOFactory.getRegisterDAO(doInfo.getTransactionTypeInfo());
		
		RepurchaseRegisterInfo foundInfo = null;
		try {
			foundInfo = (RepurchaseRegisterInfo) dao.findByID(info.getId(), RepurchaseRegisterInfo.class);
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}
		log.debug("���ڵǼǱ���ϢΪ: " +foundInfo);
		if(foundInfo.getBalance() == 0.0){//�ñ�ҵ���ѷ���
			throw new CapitalHasReturnException();	
		}
		//������
		if(foundInfo.getBalance() != info.getAmount()){
			throw new TransAmontNotMatchException(info.getAmount(),foundInfo.getBalance());
		}
		//���µǼǱ���Ϣ
		RepurchaseRegisterInfo updatedInfo = new RepurchaseRegisterInfo();
		updatedInfo.setId(info.getId());
		updatedInfo.setBalance(0.0);
		//��������ʱʹ��FirstDeliveryOrderIDz��Ϊĩ�ν�����ID���д���
		updatedInfo.setLastDeliveryOrderID(info.getFirstDeliveryOrderID());
		log.debug("���ڵǼǱ�������ϢΪ: " +updatedInfo);		
		try {
			dao.update(updatedInfo);
		} catch (ITreasuryDAOException e1) {
			throw new SecuritiesDAOException(e1);
		}
		log.debug("-------���������ǼǱ�����::���ڴ���---------");		
	}	
	
	
	/**
	 * �жϹ������׵Ľ���Ƿ���Ա�ȡ������
	 * */
	public void isAllowCancelCheck(DeliveryOrderInfo doInfo) throws SecuritiesException{
		RepurchaseRegisterInfo foundInfo = null;
		try {
			SecuritiesDAO dao = RegisterDAOFactory.getRegisterDAO(doInfo.getTransactionTypeInfo());			
			foundInfo = (RepurchaseRegisterInfo) dao.findByID(doInfo.getRegisterId(), RepurchaseRegisterInfo.class);
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}
		if(foundInfo.getId() < 0)
			throw new SECInvalidRecordException("���ڷ���");		
	
		if(foundInfo.getBalance() == 0.0 && foundInfo.getLastDeliveryOrderID() <= 0)//�ñ�ҵ���ѷ���
			throw new CapitalHasReturnException();	
	}		
}
