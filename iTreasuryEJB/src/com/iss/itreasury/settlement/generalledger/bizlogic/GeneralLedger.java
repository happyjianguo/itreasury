/*
 * Created on 2003-8-7
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.generalledger.bizlogic;
import java.rmi.RemoteException;
import java.util.*;

//import com.iss.itreasury.settlement.dataentity.TransInfo;
import com.iss.itreasury.settlement.generalledger.dataentity.*;
import com.iss.itreasury.settlement.logger.dataentity.QueryOpenCloseLog;
import com.iss.itreasury.util.IRollbackException;
import com.iss.system.dao.PageLoader;

/**
 * @author yiwang
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public interface GeneralLedger extends javax.ejb.EJBObject
{
    static int TRANSACTION_TYPE = 1; //��������
    static int CAPITAL_TYPE = 2; //�ʽ����� 
    static int ENTRY_TYPE = 3; //��¼����
    static int DIRECTION = 4; //���
    static int SUBJECT_TYPE = 5; //��Ŀ����
    static int SUBJECT_CODE = 6; //��Ŀ��
    static int AMOUNT_DIRECTION = 7; //����
    static int AMOUNT_TYPE = 8; //����
    static int OPERATE_TYPE = 9; //�������� 1:����;2:�޸�;3:ɾ��
    static int INPUTDATE_TYPE = 10; // ¼��ʱ��
    static int CHECKDATE_TYPE = 11; // ����ʱ��
    /**
     * �������˶����¼
     * @param info GLSubjectDefinitionInfo
     * @return ���˶����¼id
     * @throws java.rmi.RemoteException
     * @throws IRollbackException
     */
    public long addGLSubjectDefinition(GLSubjectDefinitionInfo info)
        throws RemoteException, IRollbackException;

    /**
     * �޸Ŀ�Ŀ�ܱ��¼
     * @param info ��Ŀ�ܱ��¼
     * @return ��Ŀ�ܱ��¼id
     * @throws IRollbackException
     * @throws RemoteException
     */
    public long updateGLSubjectDefinition(GLSubjectDefinitionInfo info)
        throws IRollbackException, RemoteException;

    /**
     * ͨ����ʶ��ѯ
     * @param id long
     * @return ��Ŀ�ܱ��¼
     * @throws IRollbackException
     * @throws RemoteException
     */
    public GLSubjectDefinitionInfo findGLSubjectDefinitionByID(long id)
        throws IRollbackException, RemoteException;

    /**
     * ͨ�������ѯ
     * @param subjectCode String
     * @return ��Ŀ�ܱ��¼
     * @throws IRollbackException
     * @throws RemoteException
     */
    public GLSubjectDefinitionInfo findGLSubjectDefinitionByCode(String subjectCode)
        throws IRollbackException, RemoteException;

    /**
     * ��ѯ���п�Ŀ�ܱ��¼
     * @param includeDisable true-������Ч��false-��������Ч
     * @param officeID ����ID �����-1���Դ�����
     * @param CurrencyID ���� �����-1���Դ�����
     * @param subjectType ��Ŀ���� �����-1���Դ�����
     * @return ���п�Ŀ�ܱ��¼����
     * @throws IRollbackException
     * @throws RemoteException
     */
    public Collection findAllGLSubjectDefinition(
        boolean includeDisable,
        long officeID,
        long CurrencyID,
        long subjectType)
        throws IRollbackException, RemoteException;

    /**
     * �޸Ŀ�Ŀ�ܱ��¼״̬
     * @param id ��Ŀ�ܱ��¼id
     * @param status ��Ŀ�ܱ��¼״̬
     * @return ��Ŀ�ܱ��¼id
     * @throws IRollbackException
     * @throws RemoteException
     */
    public long updateGLSubjectDefinitionStatus(long id, long status)
        throws IRollbackException, RemoteException;

    /**
     * ������¼���ö����¼
     * @param info GLEntryDefinitionInfo
     * @return ��¼���ö����¼id
     * @throws IRollbackException
     * @throws RemoteException
     */
    public long addGLEntryDefinition(GLEntryDefinitionInfo info)
        throws IRollbackException, RemoteException;

	/**
	 * ������¼���ö����¼
	 * @param tempInfo GLEntryDefinitionTempInfo
	 * @return��¼���ö����¼id
	 * @throws IRollbackException
	 * @throws RemoteException
	 */
    public long addGLEntryDefinitionTemp(GLEntryDefinitionTempInfo tempInfo)
        throws IRollbackException, RemoteException;
    
    /**
    	 * ���·�¼���ö����¼
    	 * @param info GLEntryDefinitionInfo
    	 * @return ��¼���ö����¼id
    	 * @throws IRollbackException
    	 * @throws RemoteException
    	 */
    public long updateGLEntryDefinition(GLEntryDefinitionInfo info)
        throws IRollbackException, RemoteException;

	/**
	 * ���·�¼���ö����¼
	 * @param tempInfo GLEntryDefinitionTempInfo
	 * @return ��¼���ö����¼id
	 * @throws IRollbackException
	 * @throws RemoteException
	 */
public long updateGLEntryDefinitionTemp(GLEntryDefinitionTempInfo tempInfo)
    throws IRollbackException, RemoteException;
    /**
     * ͨ����ʶ��ѯ
     * @param id long
     * @return GLEntryDefinitionInfo
     * @throws IRollbackException
     * @throws RemoteException
     */
    public GLEntryDefinitionInfo findGLEntryDefinitionByID(long id)
        throws IRollbackException, RemoteException;

    /**
     * ͨ����ʶ��ѯ
     * @param id long
     * @return GLEntryDefinitionInfo
     * @throws IRollbackException
     * @throws RemoteException
     */
    public GLEntryDefinitionTempInfo findGLEntryDefinitionTempInfoByID(long id)
        throws IRollbackException, RemoteException;
    /**
     * ��ѯ���з�¼���ö�����¼
     * @param officeID ����ID
     * @param currencyID ����
     * @param orderType ����ʽ
     * @return ���з�¼���ö�����¼����
     * @throws IRollbackException
     * @throws RemoteException
     */
    public Collection findAllGLEntryDefinition(
        long officeID,
        long currencyID,
        long orderType)
        throws IRollbackException, RemoteException;
    
	/**
	 * ��ѯ����δ���˺�����Ч��¼���ö�����¼
	 * @param officeID ����ID
	 * @param currencyID ����
	 * @param orderType long
	 * @return ����δ���˺�����Ч��¼���ö�����¼
	 * @throws IRollbackException
	 * @throws RemoteException
	 */
    public Collection findAllUncheckAndUsedGLEntryDefinition(
        long officeID,
        long currencyID,
        long orderType)
        throws IRollbackException, RemoteException;

	/**
	 * ��ѯ�������»�Ʒ�¼���ö�����¼���� ���޸� ����ɾ���� ֻ�� �¼�¼ ���� �ϵ� ����Ч�ļ�¼��
	 * @param officeID ����ID
	 * @param currencyID ����
	 * @param orderType long
	 * @return �������»�Ʒ�¼���ö�����¼���� ���޸� ����ɾ���� ֻ�� �¼�¼ ���� �ϵ� ����Ч�ļ�¼��
	 * @throws IRollbackException
	 * @throws RemoteException
	 */
    public Collection findAllUnUseAndUsedGLEntryDefinition(
        long officeID,
        long currencyID,
        long orderType)
        throws IRollbackException, RemoteException;
    /**
     * ����ɾ����Ʒ�¼����
     * @param id ��Ʒ�¼����id
     * @return ��Ʒ�¼����id
     * @throws IRollbackException
     * @throws RemoteException
     */
    public long deleteGLEntryDefinition(long id)
        throws IRollbackException, RemoteException;
    
    /**
     * ����ɾ����Ʒ�¼����
     * @param id ��Ʒ�¼����id
     * @return ��Ʒ�¼����id
     * @throws IRollbackException
     * @throws RemoteException
     */
    public long deleteGLEntryDefinitionTemp(GLEntryDefinitionTempInfo tempInfo)
        throws IRollbackException, RemoteException;

    /**
     * ���ײ�����Ʒ�¼
     * @return �ɹ�����true, ʧ�ܷ���false
     * @throws IRollbackException
     * @throws RemoteException
     */
    public boolean generateGLEntry(GenerateGLEntryParam param)
        throws IRollbackException, RemoteException;

    /**
     * ɾ����Ʒ�¼
     * @param stransNo ���׺�
     * @throws IRollbackException
     * @throws RemoteException
     */
    public void deleteGLEntry(String stransNo)
        throws IRollbackException, RemoteException;
        
	/**
	 * ���ݿ�Ŀ�Ų�ѯ��Ŀ������Ϣ
	 * @return GLSubjectDefinitionInfo
	 * @throws IRollbackException
	 * @throws RemoteException
	 */	
	public GLSubjectDefinitionInfo findBySubjectCode(String subjectCode)  throws IRollbackException, RemoteException;
	
	/**
	 * ���ݿ�Ŀ�Ų�ѯ��Ŀ������Ϣ
	 * @return GLSubjectDefinitionInfo
	 * @throws IRollbackException
	 * @throws RemoteException
	 */	
	public GLSubjectDefinitionInfo findBySubjectOldCode(String subjectCode)  throws IRollbackException, RemoteException;
	
	
	/**
	 * �жϿ�Ŀ���Ƿ����
	 * @return��boolean
	 * @throws IRollbackException
	 * @throws RemoteException
	 */		
	public boolean isExistSubeject(String subjectCode) throws IRollbackException, RemoteException;
	
	/**
	 * ���ӻ�Ʒ�¼(for������ҵ��)
	 * @param GLEntryInfo[]
	 * @throws IRollbackException
	 * @throws RemoteException
	 */
	public void addGLEntries(GLEntryInfo[] infos) throws IRollbackException, RemoteException;
	
	/**
	 * ���ӻ�Ʒ�¼(for��ת����ҵ��)
	 * @param GLEntryInfo[]
	 * @throws IRollbackException
	 * @throws RemoteException
	 */
	public void addDiscountGLEntries(GLEntryInfo[] infos) throws IRollbackException, RemoteException;
	
	/**
	 * �����׼����ƽ��
	 * @return �ɹ�����true, ʧ�ܷ���false
	 * @throws IRollbackException
	 */
	public boolean checkTransDCBalance(String transNo) throws IRollbackException, RemoteException;	
	
	/**
	 * �ϲ���Ʒ�¼
	 * @param GLEntryInfo[]
	 * @throws IRollbackException
	 * @throws RemoteException
	 */
	public String mergeVoucher(GLEntryInfo[] infos) throws IRollbackException, RemoteException;
	
	/**
	 * ȡ����Ʒ�¼
	 * @param sbatchno���κ�
	 * @throws IRollbackException
	 * @throws RemoteException
	 * @return ���׺������ַ���
	 */
	public String mergeCancel(String[] sbatchno) throws IRollbackException, RemoteException;
	
	/**
	 * ��ѯ����δ���� �� �Ѹ��� ��¼���ö�����¼ 
	 * @param officeID  CurrencyID
	 * @throws IRollbackException
	 * @throws RemoteException
	 * @return ��¼ Collection
	 */
	public Collection findAllGLEntryDefinitionTemp(String strState,long officeID, long currencyID,long orderType) throws  IRollbackException, RemoteException;

	/**
	 * ���ս������� ���� ��Ʒ�¼����
	 * @return �ɹ����� >= 0 , ʧ�ܷ��� -1
	 * @throws IRollbackException
	 */
	public long checkGLEntryDefinitionTemp(String strTransactionType,long checkUserID,long officeID, long currencyID) throws IRollbackException, RemoteException;	
	
	/**
	 * ���ս������� ���� ��Ʒ�¼����
	 * @return �ɹ����� >= 0 , ʧ�ܷ��� -1
	 * @throws IRollbackException
	 */
	public GLEntryDefinitionInfo switchTempInfoToGLEntryDefinitionInfo(GLEntryDefinitionTempInfo tempInfo) throws IRollbackException, RemoteException;	
	
	/**
	 *  ��ѯ����δ���� �� �Ѹ��� ��¼���ö�����¼ ��ҳ��ѯ
	 * @return δ���� �Ѹ��� �� ��� ��¼�� ��ҳ��ѯ
	 * @throws IRollbackException
	 */
	
	public PageLoader findAllGLEntryDefinitionPagerLoaderTemp(long nStatusID,long officeID, long currencyID,long orderType,String sort) throws RemoteException;
}
