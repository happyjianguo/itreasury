package com.iss.itreasury.loan.loancommonsetting.bizlogic;
import java.rmi.RemoteException;
import java.security.Identity;
import java.util.Properties;
import javax.ejb.*;
import com.iss.itreasury.util.*;

import java.rmi.RemoteException;
//
import java.util.*;
import java.sql.*;
//
import com.iss.itreasury.loan.loancommonsetting.dataentity.*;
import javax.ejb.EJBObject;
/**
 * Created 2003-8-15 14:50:59
 * Code generated by the Forte for Java EJB Module
 * @author yfan
 */
public interface LoanCommonSetting extends EJBObject
{
    /**
     * findContractByMultiOption ���Һ�ͬ��Ϣ
     * ���������������������Һ�ͬ��Ϣ
     * ����LoanInfo��ContractInfo���ݱ�
     * ��ѯ��¼
     * haoning 2003-09-01
     * @param lLoanType ��������
     * @param lContractIDBeg ����ҵĺ�ͬ�������
     * @param lContractIDEnd ����ҵĺ�ͬ�������
     * @param lLoanClientID ���λ
     * @param dLoanSumBeg �����������
     * @param dLoanSumEnd �����������
     * @param lContractStatus ��ͬ״̬
     * @param lContractManagerID ��ͬ������
     * @param lCurrencyID ����
     * @param lOfficeID ���´�ID
     * @param long           lPageLineCount        ÿҳҳ��������
     * @param long           lPageNo               �ڼ�ҳ����
     * @param long           lOrderParam           �������������ݴ˲��������������������
     * @param long           lDesc                 �������
     * @return Collection  ��ͬ��ϸ��Ϣ
     * @throws RemoteException
     */
    public Collection findContractByMultiOption(
        long lLoanType,
        long lContractIDBeg,
        long lContractIDEnd,
        long lLoanClientID,
        double dLoanSumBeg,
        double dLoanSumEnd,
        long lContractStatus,
        long lContractManagerID,
        long lCurrencyID,
        long lOfficeID,
        long lPageLineCount,
        long lPageNo,
        long lOrderParam,
        long lDesc)
        throws IException,RemoteException;
    
    
    /**
     * changeContractManager  ��ͬ�����˱��
     * �����ͬ�Ĺ�����
     * ����ContractInfo���ݱ�
     * ���±��е�nInputUserID�ֶ�
     * ֻ�Ե�ǰҳ�еĺ�ͬ���б����ȫѡ����ǰҳ�ֵ����ݱ�ȫ��ѡ��
     * haoning TODO
     * @param lContractID ��ͬ��ʾ
     * @param lContractManagerID ��ͬ�����˱�ʶ
     * @return long �ɹ�����1��ʧ�ܷ���0
     * @throws RemoteException
     */
    public long changeContractManager(
        long lContractID[],
        long lContractManagerID,String reason)
        throws IException,RemoteException;
    
    
    
    /**
     * findLoanByMultiOption ������������Ϣ
     * ������������������������������Ϣ
     * ����LoanInfo��ContractInfo���ݱ�
     * ��ѯ��¼
     * haoning 2003-09-01
     * @param lLoanType ��������
     * @param lLoanIDBeg ����ҵ�������������
     * @param lLoanIDEnd ����ҵ�������������
     * @param lLoanClientID ���λ
     * @param dLoanSumBeg �����������
     * @param dLoanSumEnd �����������
     * @param lLoanStatus ������״̬
     * @param lLoanManagerID �����������
     * @param lCurrencyID ����
     * @param lOfficeID ���´�ID
     * @param long           lPageLineCount        ÿҳҳ��������
     * @param long           lPageNo               �ڼ�ҳ����
     * @param long           lOrderParam           �������������ݴ˲��������������������
     * @param long           lDesc                 �������
     * @return Collection  ��������ϸ��Ϣ
     * @throws RemoteException
     */
    public Collection findLoanByMultiOption(
        long lLoanType,
        long lLoanIDBeg,
        long lLoanIDEnd,
        long lLoanClientID,
        double dLoanSumBeg,
        double dLoanSumEnd,
        long lLoanStatus,
        long lLoanManagerID,
        long lCurrencyID,
        long lOfficeID,
        long lPageLineCount,
        long lPageNo,
        long lOrderParam,
        long lDesc)
        throws IException,RemoteException;
    /**
     * changeLoanManager  ����������˱��
     * ���������Ĺ�����
     * ����ContractInfo���ݱ�
     * ���±��е�nInputUserID�ֶ�
     * ֻ�Ե�ǰҳ�е���������б����ȫѡ����ǰҳ�ֵ����ݱ�ȫ��ѡ��
     * haoning TODO
     * @param lLoanID �������ʾ
     * @param lLoanManagerID ����������˱�ʶ
     * @return long �ɹ�����1��ʧ�ܷ���0
     * @throws RemoteException
     */
    public long changeLoanManager(long lLoanID[], long lLoanManagerID)
        throws IException,RemoteException;
    /**
     * findClient �������пͻ�
     * ���ݿͻ���Ų������пͻ������ؿͻ���ϸ����
     * ����Client���ݱ�
     * ��ѯ��¼
     * haoning
     * @param strClientID String  �ͻ����
     * @return ClientInfo  ��ϸ�Ŀͻ���Ϣ
     * @throws RemoteException`
     */
    public ClientInfo findClientByID(long lClientID)
        throws IException,RemoteException;
    /**
     * �������޸ģ��ͻ���ϸ����
     * saveClientInfo  �������޸ģ��ͻ�����ϸ����
     * ����Client���ݱ�
     * ������Ӧ�ֶ�
     * lID=0,����  lID>0,�޸�
     * Լ����long�Ͳ���=-1��string�Ͳ���=����,Ϊδʹ����������������޸�
     *
     * @param clientinfo �ͻ���Ϣ
     * ��Ӧ�ֶΣ���������clientinfo���У�
     * @param lID ��ʶ
     * @param strClientName   ��˾����
     * @param strClientNo,    �ͻ����
     * @param strLicence,     Ӫҵִ��
     * @param lOfficeID,      ���´�
     * @param strOfficeAccount,     ���´��˺�
     * @param strBank,        ��������
     * @param strAccount      ���������˺�
     * @param strBank1,       ��������1
     * @param strAccount1,    �˺�1
     * @param strBank2,       ��������2
     * @param strAccount2,    �˺�2
     * @param strBank3,       ��������3
     * @param strAccount3,    �˺�3
     * @param strProvince,    ʡ
     * @param strCity,        ��
     * @param strAddress1,    ��ַ1
     * @param strAddress2     ��ַ2
     * @param strZipCode,     �ʱ�
     * @param strDeputy,      ���˴���
     * @param strTel,         �绰
     * @param strFax,         ����
     * @param strMailAddr,    ����
     * @param strContact,     ��ϵ��
     * @param strEconomic,    ��������
     * @param lGovernmentID,  ���ܲ��ű�ʾ
     * @param isShareHolder,  �Ƿ�ɷ�
     * @param lClientTypeID,  �ͻ�����
     * @param lCreditLevel,   ���õȼ�
     * @param lVentureLevel   ��������
     * @param strCapital        ע���ʱ�
     *
     * @return long �ɹ�����ID��Ϣ��ʧ�ܷ���0
     * @throws RemoteException
     */
    public long saveClientInfo(ClientInfo clientinfo)
        throws IException,RemoteException;
    /**
     * saveAtTermAwake  �Ŵ����ֵ���ҵ������
     * ���е���ҵ���������ã���ǰXX�����ѿͻ�,����XX�졣
     * ����loan_attermawakesetting ���ݱ�
     * ������Ӧ�ֶ�
     * ����´���أ�������޹�
     *
     * @param lTypeID ��������
     * @param lAheadDays ��ǰ����
     * @param lAwakeDays ��������
     * @param lOfficeID ���´�ID
     * @return long �ɹ�����1��ʧ�ܷ���0
     * @throws RemoteException
     */
    public long saveAtTermAwake(
        long lTypeID[],
        long lAheadDays[],
        long lAwakeDays[],
        long lOfficeID)
        throws java.rmi.RemoteException, IRollbackException;
    /**
     * findAtTermAwake  ����ҵ�����Ѳ���
     * �������ͣ��Ŵ�/��㣩���е���ҵ�����Ѳ���
     * ����AtTermAwake���ݱ�
     *
     * @param lTypeID ��������
     * @param lOfficeID ���´�ID
     * @return long �ɹ�����1��ʧ�ܷ���0
     * @throws RemoteException
     */
    public AtTermAwakeInfo findAtTermAwake(long lTypeID, long lOfficeID)
        throws IException,RemoteException;
    /**
     * addClientType  ������ͨ����ͻ�����
     * �����ͻ�����
     * ����XXXXX
     * ������¼
     *
     * @param lInputUserID ¼����
     * @param tsInputDate ¼������
     * @param strClientTypeCode �ͻ����ͱ��
     * @param strClientTypeName �ͻ���������
     * @param lID ��ʶ ���� 0/�޸� ��=0
     * @return long �ɹ�����ID��ʶ��ʧ�ܷ���0
     * @throws RemoteException
     */
    public long addClientType(
        long lInputUserID,
        Timestamp tsInputDate,
        String sClientTypeCode,
        String sClientTypeName,
        long lID,
		long lOfficeID)
        throws IException,RemoteException;
    /**
     * findClientTypeByID  ��ѯ��ͨ����ͻ�����
     * �ͻ������ѯ
     * ����XXXXX
     * ��ѯ��Ӧ��¼
     *
     * @param lID �ͻ�������
     * @return CostomerTypeInfo  �ͻ�������Ϣ
     * @throws RemoteException
     */
    public ClientTypeInfo findClientTypeByID(long lID)
        throws IException,RemoteException;
    /**
      * findClientTypeByID  ��ѯ�����ͨ����ͻ������ʾ
      * ����XXXXX
      * ��ѯ��Ӧ��¼
      *
      * @return long  �ͻ�������Ϣ
      * @throws RemoteException
      */
    public long findMaxClientTypeID(long lOfficeID) throws IException,RemoteException;
    /**
     * findClientTypeByMultiOption  ��ѯ��ͨ����ͻ�����
     * �ͻ������ѯ
     * ����XXXXX
     * ��ѯ��Ӧ��¼
     *
     * @param strClientTypeCode �ͻ�������
     * @param strClientTypeName �ͻ���������
     * @return CostomerTypeInfo  �ͻ�������Ϣ
     * @param long           lPageLineCount        ÿҳҳ��������
     * @param long           lPageNo               �ڼ�ҳ����
     * @param long           lOrderParam           �������������ݴ˲��������������������
     * @param long           lDesc                 �������
     * @throws RemoteException
     */
    public Collection findClientTypeByMultiOption(
        String sClientTypeCode,
        String sClientTypeName,
        long lPageLineCount,
        long lPageNo,
        long lOrderParam,
        long lDesc,
		long lOfficeID)
        throws IException,RemoteException;
    /**
     * deleteClientType  ɾ���ͻ�����
     * ɾ���ͻ�����
     * ����SETT_ClientType
     * ɾ����¼�����߼�ɾ����
     *
     * @param lID  �ͻ����ͱ�ʾ
     * @param lUserID ɾ����
     * @param tsDate ɾ������
     * @return long  �ɹ�����1��ʧ�ܷ���0
     * @throws RemoteException
     */
    public long deleteClientType(long lID, long lUserID, Timestamp tsDate)
        throws IException,RemoteException;
    /**
     * ���浽��ҵ������������Ϣ
     * �������ݿ��Loan_AtTermAwakeSetting
     * @param       ASInfo  ����ҵ������������Ϣ
     * @return      long    �ɹ�������ֵ=1��ʧ�ܣ�����ֵ=-1
     */
    public long saveAtTermAwakeSetting(
        long lTypeID[],
        long lAheadDays[],
        long lAwakeDays[],
        long lOfficeID,long lCurrencyID)
        throws RemoteException, IRollbackException;
    /**
     * ��ѯ����ҵ������������Ϣ
     * ��ѯ����Ϊ���´���ʾ�͵���ҵ����������
     * �������ݿ��Loan_AtTermAwakeSetting
     * @param       lOfficeID            ���´���ʾ
     * @param       lAwakeTypeID         ����ҵ����������
     * @return      AtTermAwakeInfo      ���ص���ҵ������������Ϣ
     */
    public AtTermAwakeInfo findAtTermAwakeSetting(
        long lOfficeID,
        long lAwakeTypeID,long lCurrencyID)
        throws IException,RemoteException;
    /**
     * addCurrency  �������������
     * �����������
     * ����XXXXX
     * ����XXXX
     *
     * @param strCurrencyCode ���ֱ��
     * @param strCurrencyName ��������
     * @param nInputUserID ¼����
     * @param tsInputDate ¼������
     * @param lInputUserID ¼����
     * @return long  �ɹ�����1��ʧ�ܷ���0
     * @throws RemoteException
     */
    public long addCurrency(
        String strCurrencyCode,
        String strCurrencyName,
        long lInputUserID,
        Timestamp tsInputDate)
        throws IException,RemoteException;
    /**
     * findCurrencyByID  ��ѯ�������������
     * ��ѯ�������������
     * ����XXXXX
     * ����XXXX
     *
     * @param lID ���ֱ�ʾ
     * @throws RemoteException
     */
    public CurrencyInfo findCurrencyByID(long lID) throws IException,RemoteException;
    /**
     * findCurrencyByMultiOption  ��ѯ�������������
     * ��ѯ�������������
     * ����XXXXX
     * ����XXXX
     *
     * @param strCurrencyCode ���ֱ��
     * @param lCurrencyID ���ֱ�ʾ
     * @return Collection  ���Ʒ����Ϣ����
     * @param long           lPageLineCount        ÿҳҳ��������
     * @param long           lPageNo               �ڼ�ҳ����
     * @param long           lOrderParam           �������������ݴ˲��������������������
     * @param long           lDesc                 �������
     * @throws RemoteException
     */
    public Collection findCurrencyByMultiOption(
        String strCurrencyCode,
        long lCurrencyID,
        long lPageLineCount,
        long lPageNo,
        long lOrderParam,
        long lDesc)
        throws IException,RemoteException;
}