/*
 * Created on 2006-4-28
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.bizdelegation;
import java.rmi.RemoteException;
import java.util.*;

import javax.ejb.CreateException;
import com.iss.itreasury.settlement.base.SettlementException;
import com.iss.itreasury.settlement.transfinance.bizlogic.*;
import com.iss.itreasury.settlement.transfinance.dataentity.*;
import com.iss.itreasury.settlement.transloan.dataentity.TransGrantLoanInfo;
import com.iss.itreasury.util.*;
/**
 * @author feiye
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class TransFinanceDelegation
{
	//private com.iss.itreasury.settlement.transfixeddeposit.bizlogic.TransFixedDepositEJB ejb;
	private TransFinance transFinanceFacade;
	
	//���캯����ʼ����EJB
	public TransFinanceDelegation() throws RemoteException,IRollbackException
	{
		try
		{
			TransFinanceHome home = (TransFinanceHome)EJBHomeFactory.getFactory().lookUpHome(TransFinanceHome.class);
			transFinanceFacade = home.create();
		}
		catch(Exception e){
			throw new RemoteException("CreateException in TransFixedDepositDelegation", e);			
		}
	}	 
	/**
	 * ��������Added by zwsun, 2007-6-20
	 * ����˵������������
	 *  Method  doApproval. 
	 * @param TransFixedOpenInfo info
	 * @return long transid
	 */
	public long doApproval ( TransReturnFinanceInfo info)
		throws RemoteException, IRollbackException
	{
		return this.transFinanceFacade.doApproval(info);
	}
	/**
	 * ��������Added by zwsun, 2007-6-20
	 * ����˵���������ܾ�
	 *  Method  doApproval. 
	 * @param TransFixedOpenInfo info
	 * @return long transid
	 */
	public long cancelApproval ( TransReturnFinanceInfo info)
		throws RemoteException, IRollbackException
	{
		return this.transFinanceFacade.cancelApproval(info);
	}	 
	/**
	 * ���������տ�����ķ�����
	 * @param lID long , ���׵�ID
	 * @return FixedDepositAssemble,����ʵ����
	 * @throws RemoteException
	 */
	public TransReceiveFinanceInfo receiveNext(TransReceiveFinanceInfo info) throws RemoteException,IRollbackException
	{
		return transFinanceFacade.receiveNext(info);	
	}
	
	/**
	 * ���ݱ�ʶ��ѯ���������տ����ϸ�ķ�����
	 * @param lID long , ���׵�ID
	 * @return FixedDepositAssemble,����ʵ����
	 * @throws RemoteException
	 */
	public TransReceiveFinanceInfo receiveFindByID(long lID) throws RemoteException,IRollbackException
	{
		return transFinanceFacade.receiveFindByID(lID);	
	}
	
	/**
	 * ���ݽ��׺Ų�ѯ���������տ����ϸ�ķ�����
	 * @param strTransNo String , ���׺�
	 * @return FixedDepositAssemble,����ʵ����
	 * @throws RemoteException
	 */
	public TransReceiveFinanceInfo receiveFindByTransNo(String strTransNo) throws RemoteException,IRollbackException
	{
		return transFinanceFacade.receiveFindByTransNo(strTransNo);	
	}
	
	/**
	 * ���������տ����״̬��ѯ�ķ�����
	 * @param QueryByStatusConditionInfo, ��״̬��ѯ�Ĳ�ѯ����ʵ���ࡣ
	 * @return Collection ,����FixedDepositResultInfo��ѯ���ʵ����ļ�¼��
	 * @throws RemoteException
	 */
	public Collection receiveFindByStatus(QueryByStatusConditionInfo info) throws RemoteException,IRollbackException
	{
		return transFinanceFacade.receiveFindByStatus(info);	
	}
	
	/**
	 * ���������տ�׵��ݴ淽����
	 * @param info, TransReceiveFinanceInfo, ����ʵ���ࣨinfo��
	 * @return long ,�����׵ı�ʶ�����ʧ�ܣ�����-1
	 * @throws java.rmi.RemoteException
	 */
	public long receiveTempSave(TransReceiveFinanceInfo info) throws RemoteException,IRollbackException
	{
		return transFinanceFacade.receiveTempSave(info);	
	}
	
	/**
	 * ���������տ�׵ı���
	 * @param Assemble, TransReceiveFinanceInfo, ����ʵ���ࣨinfo��
	 * @return long ,���������տ�׵ı�ʶ�����ʧ�ܣ�����-1
	 * @throws java.rmi.RemoteException
	 */	
	public long receiveSave(TransReceiveFinanceInfo info) throws RemoteException,IRollbackException
	{
		return transFinanceFacade.receiveSave(info);	
	}
	
	/**
	 * ���������տ�׵�ɾ��
	 * @param Assemble, TransReceiveFinanceInfo, ����ʵ���ࣨinfo��
	 * @return long ,���������տ�׵ı�ʶ�����ʧ�ܣ�����-1
	 * @throws java.rmi.RemoteException
	 */	
	public long receiveDelete(TransReceiveFinanceInfo info) throws RemoteException,IRollbackException
	{
		return transFinanceFacade.receiveDelete(info);
	}

	/**
	 * ���������տ��ƥ��ķ�����
	 * @param info , TransReceiveFinanceInfo,���������տ�ʵ����
	 * @return Collection ,����FixedDepositResultInfo��ѯ���ʵ����ļ�¼��
	 * @throws RemoteException
	 */
	public Collection receiveMatch(TransReceiveFinanceInfo info) throws RemoteException,IRollbackException
	{
		return transFinanceFacade.receiveMatch(info);	
	}
	
	/**
	 * ���������տ�׵ĸ��˷�����
	 * @param info, TransReceiveFinanceInfo, ����ʵ���ࣨinfo��
	 * @return long ,�����˵����������տ�׵ı�ʶ�����ʧ�ܣ�����-1
	 * @throws RemoteException
	 */
	public long receiveCheck(TransReceiveFinanceInfo info) throws RemoteException,IRollbackException
	{
		long res =  transFinanceFacade.receiveCheck(info);
		return res;
	}
	
	/**
	 * ���������տ�׵�ȡ�����˷�����
	 * @param info, TransReceiveFinanceInfo, ����ʵ���ࣨinfo��
	 * @return long ,��ȡ�����˵ı����׵ı�ʶ�����ʧ�ܣ�����-1
	 * @throws RemoteException
	 */
	public long receiveCancelCheck(TransReceiveFinanceInfo info) throws RemoteException,IRollbackException
	{
		return transFinanceFacade.receiveCancelCheck(info);	
	}
	
	
	/**
	 * ���������տ�����ķ�����
	 * @param lID long , ���׵�ID
	 * @Return FixedDepositAssemble,����ʵ����
	 * @throws RemoteException
	 */
	public TransReturnFinanceInfo ReturnNext(TransReturnFinanceInfo info) throws RemoteException,IRollbackException
	{
		return transFinanceFacade.returnNext(info);	
	}
	
	/**
	 * ���ݱ�ʶ��ѯ���������տ����ϸ�ķ�����
	 * @param lID long , ���׵�ID
	 * @Return FixedDepositAssemble,����ʵ����
	 * @throws RemoteException
	 */
	public TransReturnFinanceInfo ReturnFindByID(long lID) throws RemoteException,IRollbackException
	{
		return transFinanceFacade.returnFindByID(lID);	
	}
	
	/**
	 * ���ݽ��׺Ų�ѯ���������տ����ϸ�ķ�����
	 * @param strTransNo String , ���׺�
	 * @Return FixedDepositAssemble,����ʵ����
	 * @throws RemoteException
	 */
	public TransReturnFinanceInfo ReturnFindByTransNo(String strTransNo) throws RemoteException,IRollbackException
	{
		return transFinanceFacade.returnFindByTransNo(strTransNo);	
	}
	
	/**
	 * ���������տ����״̬��ѯ�ķ�����
	 * @param QueryByStatusConditionInfo, ��״̬��ѯ�Ĳ�ѯ����ʵ���ࡣ
	 * @Return Collection ,����FixedDepositResultInfo��ѯ���ʵ����ļ�¼��
	 * @throws RemoteException
	 */
	public Collection ReturnFindByStatus(QueryByStatusConditionInfo info) throws RemoteException,IRollbackException
	{
		return transFinanceFacade.returnFindByStatus(info);	
	}
	
	/**
	 * ���������տ�׵��ݴ淽����
	 * @param info, TransReturnFinanceInfo, ����ʵ���ࣨinfo��
	 * @Return long ,�����׵ı�ʶ�����ʧ�ܣ�����-1
	 * @throws java.rmi.RemoteException
	 */
	public long ReturnTempSave(TransReturnFinanceInfo info) throws RemoteException,IRollbackException
	{
		return transFinanceFacade.returnTempSave(info);	
	}
	
	/**
	 * ���������տ�׵ı���
	 * @param Assemble, TransReturnFinanceInfo, ����ʵ���ࣨinfo��
	 * @Return long ,���������տ�׵ı�ʶ�����ʧ�ܣ�����-1
	 * @throws java.rmi.RemoteException
	 */	
	public long ReturnSave(TransReturnFinanceInfo info) throws RemoteException,IRollbackException
	{
		return transFinanceFacade.returnSave(info);	
	}
	
	/**
	 * ���������տ�׵�ɾ��
	 * @param Assemble, TransReturnFinanceInfo, ����ʵ���ࣨinfo��
	 * @Return long ,���������տ�׵ı�ʶ�����ʧ�ܣ�����-1
	 * @throws java.rmi.RemoteException
	 */	
	public long ReturnDelete(TransReturnFinanceInfo info) throws RemoteException,IRollbackException
	{
		return transFinanceFacade.returnDelete(info);
	}

	/**
	 * ���������տ��ƥ��ķ�����
	 * @param info , TransReturnFinanceInfo,���������տ�ʵ����
	 * @Return Collection ,����FixedDepositResultInfo��ѯ���ʵ����ļ�¼��
	 * @throws RemoteException
	 */
	public Collection ReturnMatch(TransReturnFinanceInfo info) throws RemoteException,IRollbackException
	{
		return transFinanceFacade.returnMatch(info);	
	}
	
	/**
	 * ���������տ�׵ĸ��˷�����
	 * @param info, TransReturnFinanceInfo, ����ʵ���ࣨinfo��
	 * @Return long ,�����˵����������տ�׵ı�ʶ�����ʧ�ܣ�����-1
	 * @throws RemoteException
	 */
	public long ReturnCheck(TransReturnFinanceInfo info) throws RemoteException,IRollbackException
	{
		long res =  transFinanceFacade.returnCheck(info);
		return res;
	}
	
	/**
	 * ���������տ�׵�ȡ�����˷�����
	 * @param info, TransReturnFinanceInfo, ����ʵ���ࣨinfo��
	 * @Return long ,��ȡ�����˵ı����׵ı�ʶ�����ʧ�ܣ�����-1
	 * @throws RemoteException
	 */
	public long ReturnCancelCheck(TransReturnFinanceInfo info) throws RemoteException,IRollbackException
	{
		return transFinanceFacade.returnCancelCheck(info);	
	}
	
	/**
	 * @author yunchang
	 * @date 2010-07-02
	 * @function ��������-�տ�-���ձ�֤����
	 * @param long 
	 * @return double
	 * @throws IRollbackException RemoteException
	 */
	public double getMbailamount(long constractID) throws IRollbackException, RemoteException
	{
		return transFinanceFacade.getMbailamount(constractID);
	}
	
}
