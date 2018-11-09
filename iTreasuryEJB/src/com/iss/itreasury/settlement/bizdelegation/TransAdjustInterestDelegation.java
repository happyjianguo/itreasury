/*
 * Created on 2005-9-6
 * 
 * To change the template for this generated type comment go to Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.bizdelegation;

import java.rmi.RemoteException;
import java.util.Collection;

import javax.ejb.CreateException;

import com.iss.itreasury.settlement.base.SettlementException;
import com.iss.itreasury.settlement.transadjustinterest.bizlogic.*;
import com.iss.itreasury.settlement.transadjustinterest.dataentity.*;
import com.iss.itreasury.util.EJBHomeFactory;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IRollbackException;

/**
 * @author feiye
 * 
 * �ۼƷ��ü���Ϣ�������ܴ�����
 * 
 * To change the template for this generated type comment go to Window>Preferences>Java>Code Generation>Code and
 * Comments
 */
public class TransAdjustInterestDelegation
{
	
	private TransAdjustInterest adjustFacade;
	public TransAdjustInterestDelegation() throws RemoteException
	{
		try
		{
			TransAdjustInterestHome home = (TransAdjustInterestHome) EJBHomeFactory.getFactory().lookUpHome(TransAdjustInterestHome.class);
			adjustFacade = (TransAdjustInterest) home.create();
		}
		catch (RemoteException re)
		{
			re.printStackTrace();
		}
		catch (CreateException ce)
		{
			ce.printStackTrace();
		}
		catch (IException ie)
		{
			ie.printStackTrace();
		}
	}	
	/**
     * ����ģ��--����(�ۼƷ��ü���Ϣ����)���ҵ�ǰ��Ϣ 
     * �����ʼ����ֹ�ն��ǽ��죬�򲻲���ʷ��Ϣ
     */
    public Collection findForm(AccumulateFeeInfoQuery queryCondition) {
    	TransAdjustInterestBiz adjustBiz=new TransAdjustInterestBiz();
    	return adjustBiz.findForm(queryCondition);
    }
    /**
     * ����ģ��--����(�ۼƷ��ü���Ϣ����)��ʷ��¼ 
     */
    public Collection findFormHistory(AccumulateFeeInfoQuery queryCondition) {
    	TransAdjustInterestBiz adjustBiz=new TransAdjustInterestBiz();
    	return adjustBiz.findFormHistory(queryCondition);
    }


    /**
     * ����ģ��--����ID���Ҽ�¼(�ۼƷ��ü���Ϣ����)
     */
    public AdjustInterestInfo findAdjustByID(long lID) throws SettlementException{
    	//null:ʧ��  ����:AdjustInterestInfo
    	TransAdjustInterestBiz adjustBiz=new TransAdjustInterestBiz();
    	return adjustBiz.findAdjustByID(lID);
    }
    
    /**
     * ����ģ��--������������(�ۼƷ��ü���Ϣ����)
     */
    public Collection findAdjustByCondition(AdjustInterestInfoQuery info) throws SettlementException{
    	//null:ʧ��  ����:�ѻصļ�¼��
    	TransAdjustInterestBiz adjustBiz=new TransAdjustInterestBiz();
    	return adjustBiz.findAdjustByCondition(info);
    }
   
    /**
     * ����ģ��--���(�ۼƷ��ü���Ϣ����)
     */
    public long addAdjust(AdjustInterestInfo info) throws SettlementException, IRollbackException, RemoteException{
    	//-1:ʧ��  ����:��ӵļ�¼ID
    	//Modified by zwsun, 2007/7/9, Ӧ��ejb
    	//TransAdjustInterestBiz adjustBiz=new TransAdjustInterestBiz();

    	return adjustFacade.addAdjust(info);   	
    }
    
    /**
     * ����ģ��--ɾ��(�ۼƷ��ü���Ϣ����)
     */
    public long delAdjust(long lID) throws SettlementException, IRollbackException, RemoteException{
    	//-1:ʧ�� ������ɾ����ID��
    	//Modified by zwsun, 2007/7/9, Ӧ��ejb
    	//TransAdjustInterestBiz adjustBiz=new TransAdjustInterestBiz();

    	return adjustFacade.delAdjust(lID);
    }
    
    /**
     * ����ģ��--ֵ����(�ۼƷ��ü���Ϣ����)
     */
    public long valueAdjust(AdjustInterestInfo adjustInfo) throws SettlementException, IRollbackException, RemoteException{
    	//0:���� -1:ʧ��
    	//Modified by zwsun, 2007/7/9, Ӧ��ejb
    	//TransAdjustInterestBiz adjustBiz=new TransAdjustInterestBiz();

    	return adjustFacade.valueAdjust(adjustInfo);
    }
    
    /**
     * ����ģ��--����(�ۼƷ��ü���Ϣ����)
     * ��Ҫ����������������ڸĳ������check(), zwsun
     */
    public long checkAdjust(AdjustInterestInfo adjustInfo) throws SettlementException{
    	//0:���� -1:ʧ��
    	TransAdjustInterestBiz adjustBiz=new TransAdjustInterestBiz();
    	return adjustBiz.checkAdjust(adjustInfo);
    }
	
    /**
     * ������ Added by zwsun, 2007/7/6
     * @param adjustInfo
     * @return
     * @throws SettlementException
     */
    public long doApproval(AdjustInterestInfo adjustInfo) throws SettlementException, IRollbackException, RemoteException{
    	return adjustFacade.doApproval(adjustInfo);    	
    }
    
    /**
     * ȡ����������Added by zwsun, 2007/7/6
     * @param adjustInfo
     * @return
     * @throws SettlementException
     */
    public long cancelApproval(AdjustInterestInfo adjustInfo) throws SettlementException, IRollbackException, RemoteException{
    	return adjustFacade.cancelApproval(adjustInfo);    	
    }
    
    /**
     * ���ˣ���Added by zwsun, 2007/7/6
     * @param adjustInfo
     * @param isCancel��true��ʾȡ������
     * @return
     * @throws SettlementException
     */
    public long check(AdjustInterestInfo adjustInfo, boolean isCancel) throws SettlementException, IRollbackException, RemoteException{
    	return adjustFacade.check(adjustInfo, isCancel);     	
    }
}