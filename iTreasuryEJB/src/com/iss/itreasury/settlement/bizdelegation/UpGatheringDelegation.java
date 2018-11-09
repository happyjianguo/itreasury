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
 * 资金上收业务封装类,包含了资金上收账户设置和资金上收交易的基本方法
 */
public class UpGatheringDelegation
{
	UpGatheringBiz biz = new UpGatheringBiz();
	
	/**
	 * 查询所有的上收策略
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
	 * 查找某一条上收策略
	 * @param lID
	 * @return
	 * @throws SettlementException
	 */
	public UpGatheringPolicyInfo findPolicyByID(long lID) throws SettlementException
	{
		return biz.findPolicyByID(lID);
	}
	
	/**
	 * 得到下一个上收策略的编号
	 * @return
	 * @throws SettlementException
	 */
	public String getNextPolicyCode() throws SettlementException
	{
		return biz.getNextPolicyCode();
	}
	/**
	 * 新增或修改上收策略
	 * @param info
	 */
	public long savePolicy(UpGatheringPolicyInfo info) throws SettlementException
	{
		return biz.savePolicy(info);
	}
	
	/**
	 * 删除上收策略
	 * @param info
	 */
	public void deletePolicy(UpGatheringPolicyInfo info) throws SettlementException
	{
		biz.deletePolicy(info);
	}
	
	/**
	 * 查找某策略下的上收策略明细
	 * @param lPolicyID 上收策略标识
	 * @return
	 */
	public Collection findAllPayerAccountsByPolicy(long lPolicyID) throws SettlementException
	{
		return biz.findAllPayerAccountsByPolicy(lPolicyID);
	}
	
	/**
	 * 查找某一条上收策略
	 * @param lID
	 * @return
	 * @throws SettlementException
	 */
	public UpGatheringDetailInfo findPayerAccountByID(long lID) throws SettlementException
	{
		return biz.findPayerAccountByID(lID);
	}
	
	/**
	 * 新增或修改某策略下的付款方账户设置
	 * @param info
	 */
	public long savePayerAccount(UpGatheringDetailInfo info) throws SettlementException
	{
		return biz.savePayerAccount(info);
	}
	
	/**
	 * 删除某策略下的一个付款方账户
	 * @param lID
	 */
	public void deletePayerAccount(UpGatheringDetailInfo info) throws SettlementException
	{
		biz.deletePayerAccount(info);
	}
	
	/**
	 * 查询所有的上收策略(资金上收用)
	 * @param lAscOrDesc
	 * @return
	 * @throws SettlementException
	 */
	public Collection findAllPoliciesForExcute(long lAscOrDesc) throws SettlementException
	{
		return biz.findAllPoliciesForExcute(lAscOrDesc);
	}
	/**
	 * 查找某策略下的上收策略明细(资金上收用)
	 * @param lPolicyID
	 * @return
	 * @throws SettlementException
	 */
	public Collection findPayerAccountsByIDForExcute(long lPolicyID) throws SettlementException
	{
		return biz.findPayerAccountsByIDForExcute(lPolicyID);
	}
	/**
	 * 针对某一策略下所选的账户设置,进行资金上收
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
	 * 针对某一策略下所选的账户设置,进行资金上收 重载 +录入人、复核人、签证人ID
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
	 * 针对所选的策略,进行资金上收
	 * @param c
	 * @return
	 * @throws SettlementException
	 */
	public long autoExecuteUpGatheringOfPolicies(Collection c) throws SettlementException
	{
		return biz.autoExecuteUpGatheringOfPolicies(c);
	}
	/**
	 * 针对所选的策略,进行资金上收,重载 +录入人、复核人、签证人ID
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
	 * 查询当天所有已进行资金上收的交易
	 * @param info
	 * @return
	 * @throws SettlementException
	 */
	public Collection findForCancelUpGathering(UpGatheringQueryInfo info) throws SettlementException
	{
		return biz.findForCancelUpGathering(info);
	}
	
	/**
	 * 针对所选的上收交易,进行取消操作
	 * @param c 一付多收交易号的集合
	 * @return
	 * @throws SettlementException
	 */
	public long cancelUpGatchering(Collection c) throws SettlementException
	{
		return biz.cancelUpGatchering(c);
	}
}
