/*
 * Created on 2006-4-20
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */

package com.iss.itreasury.settlement.transnoteacceptance.bizlogic;

import java.rmi.RemoteException;
import java.util.Collection;
import com.iss.itreasury.settlement.transnoteacceptance.dataentity.QueryByStatusConditionInfo;
import com.iss.itreasury.settlement.transnoteacceptance.dataentity.TransAcceptanceNoteAcceptanceInfo;
import com.iss.itreasury.settlement.transnoteacceptance.dataentity.TransAdvancedReceviceNoteAcceptanceInfo;
import com.iss.itreasury.util.IRollbackException;

/**
 * @author feiye
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public interface TransNoteAcceptance extends javax.ejb.EJBObject
{

	/** 商业票据承兑 - 到期承兑 start* */
	// 到期承兑交易的保存
	public long acceptanceSave(TransAcceptanceNoteAcceptanceInfo info) throws IRollbackException, RemoteException;

	// 到期承兑交易的暂存
	public long acceptanceTempSave(TransAcceptanceNoteAcceptanceInfo info) throws IRollbackException, RemoteException;

	// 到期承兑交易的匹配
	public Collection acceptanceMatch(TransAcceptanceNoteAcceptanceInfo info) throws IRollbackException, RemoteException;

	// 到期承兑交易的链接查找
	public Collection acceptanceFindByStatus(QueryByStatusConditionInfo info) throws IRollbackException, RemoteException;

	// 到期承兑交易的根据ID查找
	public TransAcceptanceNoteAcceptanceInfo acceptanceFindByID(long lID) throws IRollbackException, RemoteException;

	// 到期承兑交易的根据交易号查找
	public TransAcceptanceNoteAcceptanceInfo acceptanceFindByTransNo(String strTransNo) throws IRollbackException, RemoteException;

	// 到期承兑交易的复核
	public long acceptanceCheck(TransAcceptanceNoteAcceptanceInfo info) throws IRollbackException, RemoteException;

	// 到期承兑交易的取消复核
	public long acceptanceCancelCheck(TransAcceptanceNoteAcceptanceInfo info) throws IRollbackException, RemoteException;

	// 到期承兑交易的删除
	public long acceptanceDelete(TransAcceptanceNoteAcceptanceInfo info) throws IRollbackException, RemoteException;

	//到期承兑交易的继续
	public TransAcceptanceNoteAcceptanceInfo acceptanceNext(TransAcceptanceNoteAcceptanceInfo info) throws IRollbackException, RemoteException;

	/** 商业票据承兑 - 到期承兑 end* */
	
	
	/** 商业票据承兑 - 垫付收回 start* */
	// 垫付收回交易的保存
	public long advancedReceviceSave(TransAdvancedReceviceNoteAcceptanceInfo info) throws IRollbackException, RemoteException;

	// 垫付收回交易的暂存
	public long advancedReceviceTempSave(TransAdvancedReceviceNoteAcceptanceInfo info) throws IRollbackException, RemoteException;

	// 垫付收回交易的匹配
	public Collection advancedReceviceMatch(TransAdvancedReceviceNoteAcceptanceInfo info) throws IRollbackException, RemoteException;

	// 垫付收回交易的链接查找
	public Collection advancedReceviceFindByStatus(QueryByStatusConditionInfo info) throws IRollbackException, RemoteException;

	// 垫付收回交易的根据交易号查找
	public TransAdvancedReceviceNoteAcceptanceInfo advancedReceviceFindByTransNo(String strTransNo) throws IRollbackException, RemoteException;

	// 垫付收回交易的继续
	public TransAdvancedReceviceNoteAcceptanceInfo advancedReceviceNext(TransAdvancedReceviceNoteAcceptanceInfo info) throws IRollbackException, RemoteException;

	// 垫付收回交易的根据ID查找
	public TransAdvancedReceviceNoteAcceptanceInfo advancedReceviceFindByID(long lID) throws IRollbackException, RemoteException;

	// 垫付收回交易的复核
	public long advancedReceviceCheck(TransAdvancedReceviceNoteAcceptanceInfo info) throws IRollbackException, RemoteException;

	// 垫付收回交易的取消复核
	public long advancedReceviceCancelCheck(TransAdvancedReceviceNoteAcceptanceInfo info) throws IRollbackException, RemoteException;

	//  垫付收回交易的删除
	public long advancedReceviceDelete(TransAdvancedReceviceNoteAcceptanceInfo info) throws IRollbackException, RemoteException;
	/** 商业票据承兑 - 垫付收回 end* */
}
