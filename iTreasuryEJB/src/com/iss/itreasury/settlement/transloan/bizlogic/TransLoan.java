/*
 * Created on 2003-8-7
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.transloan.bizlogic;
import java.rmi.RemoteException;
import java.util.Collection;

import com.iss.itreasury.settlement.transfixeddeposit.dataentity.TransFixedOpenInfo;
import com.iss.itreasury.settlement.transloan.dataentity.GrantConsignLoanConditionInfo;
import com.iss.itreasury.settlement.transloan.dataentity.GrantTrustLoanConditionInfo;
import com.iss.itreasury.settlement.transloan.dataentity.LoanPayFormDetailInfo;
import com.iss.itreasury.settlement.transloan.dataentity.RepaymentConsignLoanConditionInfo;
import com.iss.itreasury.settlement.transloan.dataentity.RepaymentInterestConditionInfo;
import com.iss.itreasury.settlement.transloan.dataentity.RepaymentTrustLoanConditionInfo;
import com.iss.itreasury.settlement.transloan.dataentity.SubLoanAccountDetailInfo;
import com.iss.itreasury.settlement.transloan.dataentity.TransGrantLoanInfo;
import com.iss.itreasury.settlement.transloan.dataentity.TransRepaymentLoanInfo;
import com.iss.itreasury.util.IRollbackException;
/**
 * @author yiwang
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public interface TransLoan extends javax.ejb.EJBObject
{
	/******************************************************************
	 * ******************************����ſ�ʼ***************************
	 * ****************************************************************
	 * */
	/**
	 * ����ǰ�Ĳ���
	 * @param info
	 * @return 
	 * @throws RemoteException
	 */
	public long preSave(TransGrantLoanInfo info) throws RemoteException, IRollbackException;
	/**
	 * ���д�����ݴ����
	 * @param info TransGrantLoanInfo
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long tempSave(TransGrantLoanInfo info) throws RemoteException, IRollbackException;
	/**
	 * ����
	 * @param info
	 * @param isTrustLoan
	 * @return TransGrantLoanInfo id
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long save(TransGrantLoanInfo info) throws RemoteException, IRollbackException;
	
	/**
	 * ������������ͨ���� Added by zwsun ,2007-06-20
	 */
	public long doApproval(TransGrantLoanInfo info)throws RemoteException, IRollbackException;
	/**
	 * �����ܾ�
	 */
	public long cancelApproval(TransGrantLoanInfo info)throws RemoteException, IRollbackException;
	/**
	* ����id�������д������Ϣ
	* @param id ���д������Ϣid
	* @return TrustLoanInfoAssembler
	* @throws RemoteException
	* @throws IRollbackException
	*/
	public TransGrantLoanInfo FindGrantDetailByID(long id) throws RemoteException, IRollbackException;
	
	/*
	 * ���ݷſ�֪ͨ��ID�������д������Ϣ
	 * 
	 * @param id ���д������Ϣ�ķſ�֪ͨ��ID
	 * @return TransGrantLoanInfo
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public TransGrantLoanInfo FindGrantDetailByLoanNoteID(long lLoanNoteID) throws RemoteException, IRollbackException;
	
	/**
		 * ���Ӳ�ѯ���д������Ϣ����
		 * @param info TrustLoanInfo
		 * @param orderType
		 * @param isDesc
		 * @return ���д������Ϣ����
		 * @throws RemoteException
		 * @throws IRollbackException
		 */
	public Collection findByCondition(TransGrantLoanInfo info) throws RemoteException, IRollbackException;
	/**
	 * ƥ���ѯ���д������Ϣ
	 * @param info
	 * @return ���д������Ϣ
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public TransGrantLoanInfo match(GrantTrustLoanConditionInfo info) throws RemoteException, IRollbackException;
	/**
		 * ƥ���ѯ���д������Ϣ
		 * @param info
		 * @return ���д������Ϣ
		 * @throws RemoteException
		 * @throws IRollbackException
		 */
	public TransGrantLoanInfo match(GrantConsignLoanConditionInfo info) throws RemoteException, IRollbackException;
	/**
	 * �õ�����������Ϣ
	 * @param info LoanConditionInfo
	 * @return LoanConditionInfo
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public LoanPayFormDetailInfo findLoanPayFormDetailByCondition(LoanPayFormDetailInfo info) throws RemoteException, IRollbackException;
	/**
	 * ɾ�������¼ 
	 * @param info
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long delete(TransGrantLoanInfo info) throws RemoteException, IRollbackException;
	/**
	 * ���˻���ȡ������
	 * @param info
	 * @param checkOrCancel
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long check(TransGrantLoanInfo info, boolean checkOrCancel) throws RemoteException, IRollbackException;
	/******************************************************************
		 * ******************************����Ž���***************************
		 * ****************************************************************
		 * */
	/******************************************************************
		 * ******************************�����ջؿ�ʼ***************************
		 * ****************************************************************
		 * */
	/**
		 * ����ǰ�Ĳ���
		 * @param info
		 * @return 
		 * @throws RemoteException
		 */
	public long preSave(TransRepaymentLoanInfo info) throws RemoteException, IRollbackException;
	/**
	 * ���д����ջ��ݴ����
	 * @param info TransRepaymentLoanInfo
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long tempSave(TransRepaymentLoanInfo info) throws RemoteException, IRollbackException;
	/**
	 * ����
	 * @param info 
	 * @return TransRepaymentLoanInfo id
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long save(TransRepaymentLoanInfo info) throws RemoteException, IRollbackException;
	/**
	* ����id�������д����ջ���Ϣ
	* @param id ���д����ջ���Ϣid
	* @return TransRepaymentLoanInfo
	* @throws RemoteException
	* @throws IRollbackException
	*/
	public TransRepaymentLoanInfo FindRepaymentDetailByID(long id) throws RemoteException, IRollbackException;
	/**
		 * ���Ӳ�ѯ���д����ջ���Ϣ����
		 * @param info TrustLoanInfo
		 * @param orderType
		 * @param isDesc
		 * @return ���д������Ϣ����
		 * @throws RemoteException
		 * @throws IRollbackException
		 */
	public Collection findByCondition(TransRepaymentLoanInfo info) throws RemoteException, IRollbackException;
	/**
	 * ƥ���ѯ���д����ջ���Ϣ
	 * @param info
	 * @return ���д����ջ���Ϣ
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public TransRepaymentLoanInfo match(RepaymentTrustLoanConditionInfo info) throws RemoteException, IRollbackException;
	/**
		 * ƥ���ѯ���д����ջ���Ϣ
		 * @param info
		 * @return ���д����ջ���Ϣ
		 * @throws RemoteException
		 * @throws IRollbackException
		 */
	public TransRepaymentLoanInfo match(RepaymentConsignLoanConditionInfo info) throws RemoteException, IRollbackException;
	/**
	 * ƥ���ѯ���д����ջ���Ϣ
	 * @param info
	 * @return ���д����ջ���Ϣ
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public TransRepaymentLoanInfo match(RepaymentInterestConditionInfo info) throws RemoteException, IRollbackException;
	/**
	 * �õ�����������Ϣ
	 * @param info SubLoanAccountDetailInfo
	 * @return SubLoanAccountDetailInfo
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public SubLoanAccountDetailInfo findSubLoanAccountDetailByCondition(SubLoanAccountDetailInfo info) throws RemoteException, IRollbackException;
	/**
	 * ɾ�������¼ 
	 * @param info
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long delete(TransRepaymentLoanInfo info) throws RemoteException, IRollbackException;
	/**
	 * ���˻���ȡ������
	 * @param info
	 * @param checkOrCancel
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long check(TransRepaymentLoanInfo info, boolean checkOrCancel) throws RemoteException, IRollbackException;
	/**
	 * ��ʴ����ջع���
	 * @param infos
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public boolean squareUp(TransRepaymentLoanInfo[] infos) throws RemoteException, IRollbackException;
	/**
	 * ��ʴ����ջ�ȡ������
	 * @param infos
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public boolean cancelSquareUp(TransRepaymentLoanInfo[] infos) throws RemoteException, IRollbackException;
	
	/**
	 * ��ʴ����ջع��˲�ѯ
	 * @param condiInfo
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public Collection findSquareUpRecordsByCondition(TransRepaymentLoanInfo condiInfo) throws RemoteException, IRollbackException;
	/******************************************************************
			 * ******************************�����ջؽ���***************************
			 * ****************************************************************
			 * */
	public long grantGetIDByTransNo(String strTransNo) throws RemoteException, IRollbackException;
	
	public long repaymentGetIDByTransNo(String strTransNo) throws RemoteException, IRollbackException;
	
	public long repaymentGetIDByTransNoAndSerialNo(String strTransNo,long lSerialNo) throws RemoteException, IRollbackException;
	
	/**
	 * ���ݽ��׺Ų�ѯ����ʴ����ջػ�������
	 * @param strTransNo
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public Collection getRepaymentCollectionByTransNo(String strTransNo)throws RemoteException,IRollbackException;

    /*
	 * ���ݷſ�֪ͨ��ID�������д����ջ���Ϣ
	 * 
	 * @param id ���д������Ϣ�ķſ�֪ͨ��ID
	 * @return TransGrantLoanInfo
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public Collection grantFindInterestByLoanNoteID(long lLoanNoteID,long nOfficeID,long nCurrencyID,long lLoanAccountID,long lContractID,long lSubAccountID) throws RemoteException, IRollbackException;
	
	  /*
	 * ���ݷſ�֪ͨ��ID�ͺ�ͬID�������д����ջ���Ϣ
	 * 
	 * @param id ���д������Ϣ�ķſ�֪ͨ��ID
	 * @return TransGrantLoanInfo
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public TransRepaymentLoanInfo grantFindInterestByCondition(SubLoanAccountDetailInfo info) throws RemoteException, IRollbackException;
}
