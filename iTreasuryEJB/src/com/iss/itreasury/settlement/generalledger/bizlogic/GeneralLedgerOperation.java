/*
 * Created on 2003-9-16
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.iss.itreasury.settlement.generalledger.bizlogic;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Collection;

//import com.iss.itreasury.settlement.bankinstruction.dao.BankInstructionDAO;
import com.iss.itreasury.settlement.generalledger.dataentity.GLEntryDefinitionInfo;
import com.iss.itreasury.settlement.generalledger.dataentity.GLEntryInfo;
import com.iss.itreasury.settlement.generalledger.dataentity.GLSubjectDefinitionInfo;
import com.iss.itreasury.settlement.generalledger.dataentity.GenerateGLEntryParam;
import com.iss.itreasury.util.EJBHomeFactory;
import com.iss.itreasury.util.IRollbackException;
import com.iss.itreasury.util.IException;

/**
 * ��������ϵͳ�Ľӿ�ʵ��
 * @author yqwu
 * 
 */
public class GeneralLedgerOperation
{
	GeneralLedger generalLedger;

	public GeneralLedgerOperation() throws RemoteException, IRollbackException
	{
		GeneralLedgerHome home;
		try
		{
			home = (GeneralLedgerHome) EJBHomeFactory.getFactory().lookUpHome(GeneralLedgerHome.class);
			generalLedger = (GeneralLedger) home.create();
		}
		catch (Exception e)
		{
			throw new RemoteException();
		}
	}


	public GeneralLedgerOperation(Connection conn) 
	{

	}


	/**
	 * ͨ����ʶ��ѯ
	 * @param id long
	 * @return ��Ŀ�ܱ��¼
	 * @throws IRollbackException
	 */
	public GLSubjectDefinitionInfo findGLSubjectDefinitionByID(long id) throws RemoteException, IRollbackException
	{
		GLSubjectDefinitionInfo info = null;
		info = this.generalLedger.findGLSubjectDefinitionByID(id);
		return info;
	}

	/**
	 * ͨ�������ѯ
	 * @param subjectCode String
	 * @return ��Ŀ�ܱ��¼
	 * @throws IRollbackException
	 */
	public GLSubjectDefinitionInfo findGLSubjectDefinitionByCode(String subjectCode) throws RemoteException, IRollbackException
	{
		GLSubjectDefinitionInfo info = null;
		info = this.generalLedger.findGLSubjectDefinitionByCode(subjectCode);
		return info;
	}

	/**
	 * ��ѯ���п�Ŀ�ܱ��¼
	 * @param includeDisable true-������Ч��false-��������Ч
	 * @param officeID ����ID �����-1���Դ�����
	 * @param CurrencyID ���� �����-1���Դ�����
	 * @param subjectType ��Ŀ���� �����-1���Դ�����
	 * @return ���п�Ŀ�ܱ��¼����
	 * @throws IRollbackException
	 */
	public Collection findAllGLSubjectDefinition(boolean includeDisable, long officeID, long CurrencyID, long subjectType) throws RemoteException, IRollbackException
	{
		Collection collection = null;
		collection = this.generalLedger.findAllGLSubjectDefinition(includeDisable, officeID, CurrencyID, subjectType);
		return collection;
	}

	/**
	 * ͨ����ʶ��ѯ
	 * @param id long
	 * @return GLEntryDefinitionInfo
	 * @throws IRollbackException
	 */
	public GLEntryDefinitionInfo findGLEntryDefinitionByID(long id) throws RemoteException, IRollbackException
	{
		GLEntryDefinitionInfo info = null;
		info = this.generalLedger.findGLEntryDefinitionByID(id);
		return info;
	}

	/**
	 *  ��ѯ���з�¼���ö�����¼
	 * @param officeID ����ID
	 * @param currencyID ����
	 * @param orderType
	 * @return ���з�¼���ö�����¼����
	 * @throws IRollbackException
	 */
	public Collection findAllGLEntryDefinition(long officeID, long currencyID, long orderType) throws RemoteException, IRollbackException
	{
		Collection collection = null;
		collection = this.generalLedger.findAllGLEntryDefinition(officeID, currencyID, orderType);
		return collection;
	}

	/**
	 * ɾ����Ʒ�¼
	 * @param stransNo ���׺�
	 * @throws IRollbackException
	 * @throws RemoteException
	 */
	public void deleteGLEntry(String stransNo) throws IRollbackException, RemoteException
	{
		generalLedger.deleteGLEntry(stransNo);
	}
	
	
	/**
	 * ɾ����Ʒ�¼
	 * @param stransNo ���׺�
	 * @throws IRollbackException
	 * @throws RemoteException
	 */
	public void deleteGLEntry(String stransNo, Connection conn) throws IException
	{
		GeneralLedgerBean glBean = new GeneralLedgerBean(conn);		
		glBean.deleteGLEntry(stransNo);
	}	

	/**
	 * ���ײ�����Ʒ�¼
	 * @return �ɹ�����true, ʧ�ܷ���false
	 * @throws IRollbackException
	 * @throws RemoteException
	 */
	public boolean generateGLEntry(GenerateGLEntryParam param) throws IRollbackException, RemoteException
	{
		return generalLedger.generateGLEntry(param);
	}
	
	/**
	 * ���ײ�����Ʒ�¼
	 * @return �ɹ�����true, ʧ�ܷ���false
	 * @throws IRollbackException
	 * @throws RemoteException
	 */
	public boolean generateGLEntry(GenerateGLEntryParam param, Connection conn) throws IException
	{
		GeneralLedgerBean glBean = new GeneralLedgerBean(conn);		
		return glBean.generateGLEntry(param);
	}	
	

	
	/**
	 * ���ݿ�Ŀ�Ų�ѯ��Ŀ������Ϣ
	 * @return GLSubjectDefinitionInfo
	 * @throws IRollbackException
	 * @throws RemoteException
	 */	
	public GLSubjectDefinitionInfo findBySubjectCode(String subjectCode)  throws IRollbackException, RemoteException{
		return generalLedger.findBySubjectCode(subjectCode);
	}
	
	/**
	 * ���ݿ�Ŀ�Ų�ѯ��Ŀ������Ϣ
	 * @return GLSubjectDefinitionInfo
	 * @throws IRollbackException
	 * @throws RemoteException
	 */	
	public GLSubjectDefinitionInfo findBySubjectOldCode(String subjectCode)  throws IRollbackException, RemoteException{
		return generalLedger.findBySubjectOldCode(subjectCode);
	}
	
	/**
	 * �жϿ�Ŀ���Ƿ����
	 * @return��boolean
	 * @throws IRollbackException
	 * @throws RemoteException
	 */		
	public boolean isExistSubeject(String subjectCode) throws IRollbackException, RemoteException{
		return generalLedger.isExistSubeject(subjectCode);		
	}
	
	/**
	 * �жϿ�Ŀ���Ƿ����
	 * @return��boolean
	 * @throws IRollbackException
	 * @throws RemoteException
	 */		
	public boolean isExistSubeject(String subjectCode, Connection conn) throws IException{
		GeneralLedgerBean glBean = new GeneralLedgerBean(conn);				
		return glBean.isExistSubeject(subjectCode);		
	}	
	
	/**
	 * ���ӻ�Ʒ�¼(for������ҵ��)
	 * @param GLEntryInfo[]
	 * @throws IRollbackException
	 * @throws RemoteException
	 */
	public void addGLEntries(GLEntryInfo[] infos) throws IRollbackException, RemoteException{
		generalLedger.addGLEntries(infos);
	}
	/**
	 * ���ӻ�Ʒ�¼(for��ת����ҵ��)
	 * @param GLEntryInfo[]
	 * @throws IRollbackException
	 * @throws RemoteException
	 */
	public void addDiscountGLEntries(GLEntryInfo[] infos) throws IRollbackException, RemoteException{
		generalLedger.addDiscountGLEntries(infos);
	}
	
	/**
	 * �����׼����ƽ��
	 * @return �ɹ�����true, ʧ�ܷ���false
	 * @throws IRollbackException
	 */
	public boolean checkTransDCBalance(String transNo) throws IRollbackException, RemoteException{
		return generalLedger.checkTransDCBalance(transNo);
	}
	
//	/**
//	 * 	������¼������
//	 *  �� һ����¼�뵽�����У�һ���ǹػ���ϵͳ�����ñ����������������з�¼���������С�
//	*/
//	public void postGLEntry(long officeID, long currencyID, String subjectCode, long transDirection,double amount,Timestamp execDate, Connection conn) throws IException{
//		GeneralLedgerBean glBean = new GeneralLedgerBean(conn);
//		glBean.postGLEntry(officeID, currencyID, subjectCode, transDirection, amount, execDate);		
//	}
	
	/**
	 * ����ÿ�ճ�ʼ����
	*/
	public void createSODGLBalance(long officeID, long currencyID, Timestamp today, Connection conn)  throws IException{
		GeneralLedgerBean glBean = new GeneralLedgerBean(conn);		
		glBean.createSODGLBalance(officeID, currencyID,today);
	}	
	
	/**	���շ�¼������*/
	public void postEODGLEntries(long officeID, long currencyID, Connection conn)  throws IException{
		GeneralLedgerBean glBean = new GeneralLedgerBean(conn);
		glBean.postEODGLEntries(officeID, currencyID);		
	}
	
	/**
	 * �ϲ���Ʒ�¼
	 * @param GLEntryInfo[]
	 * @throws IRollbackException
	 * @throws RemoteException
	 */
	public String mergeVoucher(GLEntryInfo[] infos) throws IRollbackException, RemoteException{
		return generalLedger.mergeVoucher(infos);
	}

	/**
	 * ȡ����Ʒ�¼
	 * @param sbatchno���κ�
	 * @throws IRollbackException
	 * @throws RemoteException
	 * @return ���׺������ַ���
	 */
	public String mergeCancel(String[] sbatchno) throws IRollbackException, RemoteException{
		return generalLedger.mergeCancel(sbatchno);
	}
}
