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
	//�����������õı���
	public long save(CompoundInterestSettingInfo info) throws IRollbackException,RemoteException;
	//�����������õĲ���
	public Collection findByStatus(QueryByStatusConditionInfo info) throws IRollbackException,RemoteException;
	//�����������õĸ���ID����
	public CompoundInterestSettingInfo findByID(long lID) throws IRollbackException,RemoteException;
	//�����������õ�ɾ��
	public long delete(CompoundInterestSettingInfo info) throws IRollbackException,RemoteException;
}
