/*
 * Created on 2003-8-7
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.interest.bizlogic;
import java.rmi.RemoteException;
import java.util.Collection;

import com.iss.itreasury.settlement.interest.dataentity.CompoundInterestSettingInfo;
import com.iss.itreasury.settlement.transfixeddeposit.dataentity.QueryByStatusConditionInfo;
import com.iss.itreasury.util.IRollbackException;
/**
 * @author yiwang
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public interface CompoundInterestSetting extends javax.ejb.EJBObject
{
	//复利计算设置的保存
	public long save(CompoundInterestSettingInfo info) throws IRollbackException,RemoteException;
	//复利计算设置的查找
	public Collection findByStatus(QueryByStatusConditionInfo info) throws IRollbackException,RemoteException;
	//复利计算设置的根据ID查找
	public CompoundInterestSettingInfo findByID(long lID) throws IRollbackException,RemoteException;
	//复利计算设置的删除
	public long delete(CompoundInterestSettingInfo info) throws IRollbackException,RemoteException;
}
