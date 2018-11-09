/*
 * Created on 2003-8-7
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.settpaynotice.bizlogic;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.*;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.loan.discount.dataentity.DiscountBillInfo;
import com.iss.itreasury.settlement.settpaynotice.dao.SettDiscountBillDAO;
import com.iss.itreasury.settlement.settpaynotice.dao.SettDiscountCredenceDAO;
import com.iss.itreasury.settlement.settpaynotice.dao.SettPayNoticeDAO;
import com.iss.itreasury.settlement.settpaynotice.dataentity.*;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.Log;
import com.iss.system.dao.PageLoader;
/**
 * @author yiwang
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public interface SettPayNotice extends javax.ejb.EJBObject
{
	/**
	 * ��ѯ�ſ�֪ͨ��
	 * @param qInfo
	 * @return
	 * @throws java.rmi.RemoteException
	 */
	public Collection findPayNoticeByMultioption(SettPayNoticeQueryInfo qInfo) throws java.rmi.RemoteException;
	/**
	 * ��ѯ����֪ͨ��
	 * @param qInfo
	 * @return
	 * @throws java.rmi.RemoteException
	 */
	public Collection findDiscountCredenceByMultioption(SettPayNoticeQueryInfo qInfo) throws java.rmi.RemoteException;
	
	/**
	 * ����ſ�֪ͨ����Ϣ
	 * @param info
	 * @return
	 * @throws java.rmi.RemoteException
	 */
	public long savePayNotice(SettPayNoticeInfo info) throws java.rmi.RemoteException;
	
	/**
	 * �������ַſ�֪ͨ����Ϣ
	 * @param info
	 * @return
	 * @throws java.rmi.RemoteException
	 */
	public long saveDiscountCredence(SettDiscountCredenceInfo info) throws java.rmi.RemoteException;
	
	/**
	 * ���Ϸſ�֪ͨ��
	 * @param lID
	 * @param userID
	 * @return
	 * @throws java.rmi.RemoteException
	 */
	public long checkPayNotice(long lID,long userID) throws java.rmi.RemoteException;

	/**
	 * �������ַſ�֪ͨ��
	 * @param lID
	 * @param userID
	 * @return
	 * @throws java.rmi.RemoteException
	 */
	public long checkDiscountCredence(long lID,long userID) throws java.rmi.RemoteException;
	/**
	 * ����ID���ҷſ�֪ͨ��
	 * @param lID
	 * @return
	 * @throws java.rmi.RemoteException
	 */
	public SettPayNoticeInfo findPayNoticeByID(long lID) throws java.rmi.RemoteException;
	
	/**
	 * ����ID�������ַſ�֪ͨ��
	 * @param lID
	 * @return
	 * @throws java.rmi.RemoteException
	 */
	public SettDiscountCredenceInfo findDiscountCredenceByID(long lID) throws java.rmi.RemoteException;
	
	/**
	 * ����ID��������Ʊ��
	 * @param lID
	 * @return
	 * @throws java.rmi.RemoteException
	 */
	public SettDiscountBillInfo findDiscountBillByCredenceID(long lID) throws java.rmi.RemoteException;
	
	/**
	 * ��������Ʊ��
	 * @param info
	 * @return
	 * @throws java.rmi.RemoteException
	 */
	public long saveDiscountBill(SettDiscountBillInfo info) throws java.rmi.RemoteException;
	/**
	 * ��������Ʊ����Ϣ������LOAN_DISCOUNTCONTRACTBILL��
	 * @param DiscountBillInfo info,
	 * @param String tsCreate,
	 * @param String tsEnd,
	 * @param long lCurrencyID,
	 * @param long lOfficeID
	 * @return long
	 * @throws RemoteException,IException
	 */
	public long saveDiscountList(DiscountBillInfo info,
			String tsCreate,
			String tsEnd,
			long lCurrencyID,
			long lOfficeID)throws java.rmi.RemoteException;
	/**
	 * ƾ֤��ѯ��ҳ
	 * @param SettPayNoticeQueryInfo info
	 * @return PageLoader
	 * @throws java.rmi.RemoteException
	 */
	public PageLoader getMultiOptinList(SettPayNoticeQueryInfo info)throws java.rmi.RemoteException;
	/**
	 * �ſ�֪ͨ����ѯ��ҳ
	 * @param SettPayNoticeQueryInfo info
	 * @return PageLoader
	 * @throws java.rmi.RemoteException
	 */
	public PageLoader getPayNoticeByMultioption(SettPayNoticeQueryInfo info)throws java.rmi.RemoteException;
	public long saveDiscountList1(long contractId, long num, String strUser, String strBank, long isLocal, String tsCreate, String tsEnd, String strCode, double amount, long addDay, long acceptPOTypeID, String strFormerOwner, long currencyID, long officeID)throws java.rmi.RemoteException;


}
