/*
 * Created on 2003-8-7
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.transsecurities.bizlogic;
import java.rmi.RemoteException;
import java.util.Collection;

import com.iss.itreasury.settlement.transsecurities.dataentity.QueryInfo;
import com.iss.itreasury.settlement.transsecurities.dataentity.TransSecuritiesInfo;
import com.iss.itreasury.util.IRollbackException;
/**
 * @author xirenli
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public interface TransSecurities extends javax.ejb.EJBObject
{
	//���׵ı���
	public long save(TransSecuritiesInfo info) throws IRollbackException,RemoteException;
	//���׵��ݴ�
	public long tempSave(TransSecuritiesInfo info) throws IRollbackException,RemoteException;
	//���׵�ƥ��
	public Collection match(TransSecuritiesInfo info) throws IRollbackException,RemoteException;
	//���׵����Ӳ���
	public Collection findByStatus(QueryInfo info) throws IRollbackException,RemoteException;
	//���׵ĸ���ID����
	public TransSecuritiesInfo findByID(long lID) throws IRollbackException,RemoteException;
	//У�鱨�����Ƿ��ظ�
	public long  checkFormNo(TransSecuritiesInfo info) throws IRollbackException,RemoteException;
	//���׵ĸ��ݽ��׺Ų���
	public TransSecuritiesInfo findByTransNo(String strTransNo) throws IRollbackException,RemoteException;
	//���׵ĸ���
	public long check(TransSecuritiesInfo info) throws IRollbackException,RemoteException;
	//���׵�ȡ������
	public long cancelCheck(TransSecuritiesInfo info) throws IRollbackException,RemoteException;
	//���׵�ɾ��
	public long delete(TransSecuritiesInfo info) throws IRollbackException,RemoteException;	
	
}
