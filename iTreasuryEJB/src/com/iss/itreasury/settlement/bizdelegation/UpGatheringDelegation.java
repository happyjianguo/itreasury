package com.iss.itreasury.settlement.bizdelegation;

import java.util.Collection;

import com.iss.itreasury.settlement.base.SettlementException;
import com.iss.itreasury.settlement.upgathering.bizlogic.UpGatheringBiz;
import com.iss.itreasury.settlement.upgathering.dataentity.UpGatheringDetailInfo;
import com.iss.itreasury.settlement.upgathering.dataentity.UpGatheringPolicyInfo;
import com.iss.itreasury.settlement.upgathering.dataentity.UpGatheringQueryInfo;

/**
 * @author ygzhao
 * 2005-8-16
 * �ʽ�����ҵ���װ��,�������ʽ������˻����ú��ʽ����ս��׵Ļ�������
 */
public class UpGatheringDelegation
{
	UpGatheringBiz biz = new UpGatheringBiz();
	
	/**
	 * ��ѯ���е����ղ���
	 * @param info
	 * @param lOrderBy
	 * @param lAscOrDesc
	 * @return
	 * @throws SettlementException
	 */	 
	public Collection findAllPolicies(UpGatheringPolicyInfo info,long lOrderBy,long lAscOrDesc) throws SettlementException
	{
		return biz.findAllPolicies(info,lOrderBy,lAscOrDesc);
	}
	
	/**
	 * ����ĳһ�����ղ���
	 * @param lID
	 * @return
	 * @throws SettlementException
	 */
	public UpGatheringPolicyInfo findPolicyByID(long lID) throws SettlementException
	{
		return biz.findPolicyByID(lID);
	}
	
	/**
	 * �õ���һ�����ղ��Եı��
	 * @return
	 * @throws SettlementException
	 */
	public String getNextPolicyCode() throws SettlementException
	{
		return biz.getNextPolicyCode();
	}
	/**
	 * �������޸����ղ���
	 * @param info
	 */
	public long savePolicy(UpGatheringPolicyInfo info) throws SettlementException
	{
		return biz.savePolicy(info);
	}
	
	/**
	 * ɾ�����ղ���
	 * @param info
	 */
	public void deletePolicy(UpGatheringPolicyInfo info) throws SettlementException
	{
		biz.deletePolicy(info);
	}
	
	/**
	 * ����ĳ�����µ����ղ�����ϸ
	 * @param lPolicyID ���ղ��Ա�ʶ
	 * @return
	 */
	public Collection findAllPayerAccountsByPolicy(long lPolicyID) throws SettlementException
	{
		return biz.findAllPayerAccountsByPolicy(lPolicyID);
	}
	
	/**
	 * ����ĳһ�����ղ���
	 * @param lID
	 * @return
	 * @throws SettlementException
	 */
	public UpGatheringDetailInfo findPayerAccountByID(long lID) throws SettlementException
	{
		return biz.findPayerAccountByID(lID);
	}
	
	/**
	 * �������޸�ĳ�����µĸ���˻�����
	 * @param info
	 */
	public long savePayerAccount(UpGatheringDetailInfo info) throws SettlementException
	{
		return biz.savePayerAccount(info);
	}
	
	/**
	 * ɾ��ĳ�����µ�һ������˻�
	 * @param lID
	 */
	public void deletePayerAccount(UpGatheringDetailInfo info) throws SettlementException
	{
		biz.deletePayerAccount(info);
	}
	
	/**
	 * ��ѯ���е����ղ���(�ʽ�������)
	 * @param lAscOrDesc
	 * @return
	 * @throws SettlementException
	 */
	public Collection findAllPoliciesForExcute(long lAscOrDesc) throws SettlementException
	{
		return biz.findAllPoliciesForExcute(lAscOrDesc);
	}
	/**
	 * ����ĳ�����µ����ղ�����ϸ(�ʽ�������)
	 * @param lPolicyID
	 * @return
	 * @throws SettlementException
	 */
	public Collection findPayerAccountsByIDForExcute(long lPolicyID) throws SettlementException
	{
		return biz.findPayerAccountsByIDForExcute(lPolicyID);
	}
	/**
	 * ���ĳһ��������ѡ���˻�����,�����ʽ�����
	 * @param lPolicyID
	 * @param c
	 * @return
	 * @throws SettlementException
	 */
	public long autoExecuteUpGatheringOfAccounts(long lPolicyID,Collection c) throws SettlementException
	{
		return biz.autoExecuteUpGatheringOfAccounts(lPolicyID,c);
	}
	/**
	 * ���ĳһ��������ѡ���˻�����,�����ʽ����� ���� +¼���ˡ������ˡ�ǩ֤��ID
	 * @param lPolicyID
	 * @param c
	 * @return
	 * @throws SettlementException
	 * @version 2.0 
	 */
	public long autoExecuteUpGatheringOfAccounts(long lPolicyID,Collection c,long lUserID) throws SettlementException
	{
		return biz.autoExecuteUpGatheringOfAccounts(lPolicyID,c,lUserID);
	}
	/**
	 * �����ѡ�Ĳ���,�����ʽ�����
	 * @param c
	 * @return
	 * @throws SettlementException
	 */
	public long autoExecuteUpGatheringOfPolicies(Collection c) throws SettlementException
	{
		return biz.autoExecuteUpGatheringOfPolicies(c);
	}
	/**
	 * �����ѡ�Ĳ���,�����ʽ�����,���� +¼���ˡ������ˡ�ǩ֤��ID
	 * @param c
	 * @return
	 * @throws SettlementException
	 * @version 2.0
	 */
	public long autoExecuteUpGatheringOfPolicies(Collection c,long userID) throws SettlementException
	{
		return biz.autoExecuteUpGatheringOfPolicies(c,userID);
	}
	
	/**
	 * ��ѯ���������ѽ����ʽ����յĽ���
	 * @param info
	 * @return
	 * @throws SettlementException
	 */
	public Collection findForCancelUpGathering(UpGatheringQueryInfo info) throws SettlementException
	{
		return biz.findForCancelUpGathering(info);
	}
	
	/**
	 * �����ѡ�����ս���,����ȡ������
	 * @param c һ�����ս��׺ŵļ���
	 * @return
	 * @throws SettlementException
	 */
	public long cancelUpGatchering(Collection c) throws SettlementException
	{
		return biz.cancelUpGatchering(c);
	}
}
