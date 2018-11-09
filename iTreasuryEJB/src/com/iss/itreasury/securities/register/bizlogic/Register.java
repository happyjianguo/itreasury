/*
 * Created on 2003-8-7
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.securities.register.bizlogic;
import java.rmi.RemoteException;

import com.iss.itreasury.securities.deliveryorder.dataentity.DeliveryOrderInfo;
import com.iss.itreasury.securities.exception.SecuritiesException;
import com.iss.itreasury.securities.register.dataentity.IRegisterParam;
/**
 * @author yiwang
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public interface Register extends javax.ejb.EJBObject
{
	/**
	 * �ǼǱ��Ǽ�
	 * @param regiesterInfo �ǼǱ���Ϣ
	 * */
	public long register(DeliveryOrderInfo doInfo) throws RemoteException,SecuritiesException;
	
	/**
	 * �ǼǱ�ȡ���Ǽ�
	 * @param regiesterInfo �ǼǱ���Ϣ
	 * */
	public void cancelRegister(DeliveryOrderInfo doInfo) throws RemoteException,SecuritiesException;
	
	/**
	 * �ǼǱ��Ǽǵ���
	 * @param regiesterInfo �ǼǱ���Ϣ
	 * */	
	public void maturate(DeliveryOrderInfo doInfo) throws RemoteException,SecuritiesException;
	
	/**
	 * �ǼǱ��Ǽ�ȡ������
	 * @param regiesterInfo �ǼǱ���Ϣ
	 * */	
	public void cancelMaturate(DeliveryOrderInfo doInfo) throws RemoteException,SecuritiesException;
	
	/**
	 * �ǼǱ��깺ȷ�ϣ���������깺���ҵ��
	 * @param regiesterInfo �ǼǱ���Ϣ
	 * */
	public void confirmApplication(DeliveryOrderInfo doInfo)throws RemoteException,SecuritiesException;
	/**
	 * �ǼǱ��깺ȡ��ȷ�ϣ���������깺���ҵ��
	 * @param regiesterInfo �ǼǱ���Ϣ
	 * */
	public void cancelConfirmApplication(DeliveryOrderInfo doInfo)throws RemoteException,SecuritiesException;
	
	/**
	 * �깺����
	 * һ������ʱ���޸�֤ȯ�깺�Ǽǲ�
	 * @param regiesterInfo �ǼǱ���Ϣ
	 * */
	public void sellOut(DeliveryOrderInfo doInfo)throws RemoteException,SecuritiesException;
	
	/**
	 * �깺ȡ������
	 * �޸�֤ȯ�깺�Ǽǲ�
	 * @param regiesterInfo �ǼǱ���Ϣ
	 * */	
	public void cancelSellOut(DeliveryOrderInfo doInfo)throws RemoteException,SecuritiesException;
	

}
