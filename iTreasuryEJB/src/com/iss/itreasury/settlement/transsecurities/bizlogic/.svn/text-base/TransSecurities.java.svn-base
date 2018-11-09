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
	//交易的保存
	public long save(TransSecuritiesInfo info) throws IRollbackException,RemoteException;
	//交易的暂存
	public long tempSave(TransSecuritiesInfo info) throws IRollbackException,RemoteException;
	//交易的匹配
	public Collection match(TransSecuritiesInfo info) throws IRollbackException,RemoteException;
	//交易的链接查找
	public Collection findByStatus(QueryInfo info) throws IRollbackException,RemoteException;
	//交易的根据ID查找
	public TransSecuritiesInfo findByID(long lID) throws IRollbackException,RemoteException;
	//校验报单号是否重复
	public long  checkFormNo(TransSecuritiesInfo info) throws IRollbackException,RemoteException;
	//交易的根据交易号查找
	public TransSecuritiesInfo findByTransNo(String strTransNo) throws IRollbackException,RemoteException;
	//交易的复核
	public long check(TransSecuritiesInfo info) throws IRollbackException,RemoteException;
	//交易的取消复核
	public long cancelCheck(TransSecuritiesInfo info) throws IRollbackException,RemoteException;
	//交易的删除
	public long delete(TransSecuritiesInfo info) throws IRollbackException,RemoteException;	
	
}
