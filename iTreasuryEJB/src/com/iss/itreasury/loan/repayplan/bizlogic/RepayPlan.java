package com.iss.itreasury.loan.repayplan.bizlogic;

import java.rmi.RemoteException;
import java.security.Identity;
import javax.ejb.*;
import java.util.*;
import java.sql.*;
//
import com.iss.itreasury.loan.contract.dataentity.ContractInfo;
import com.iss.itreasury.loan.loaninterestsetting.dataentity.AdjustPayConditionInfo;
import com.iss.itreasury.loan.repayplan.dataentity.*;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IRollbackException;

public interface RepayPlan extends EJBObject
{

	/**
	 * ��������ƻ�
	 * <p>    
	 * <b>&nbsp;</b>
	 * <ol><b>�������޸Ļ���ƻ�</b>
	 * <ul>
	 * <li>������ܴ��ں�������
	 *
	 * <li>�������ݿ��ContractPayPlan��ContractPayPlanVersion
	 * <li>��������lID == 0��
	 * <li>����nAddANewVersion�� 1--����һ���汾��¼  0--���������汾��¼
	 * <li>���nAddANewVersion=1��ContractPayPlanVersion������һ����¼���汾��Ϊ��
	 * <li>                      ����ContractPayPlan������İ汾��¼��nContractPayPlanVersionID
	 * <li>                      ֵΪContractPayPlanVersion��������¼��ID
	 * <li>                      ContractPayPlan�в��������ļ�¼
	 * <li>���nAddANewVersion=0��ContractPayPlan�в��������ļ�¼��nContractPayPlanVersionID
	 * <li>                      ֵΪ�ô����¼��ContractPayPlanVersion�е����ID
	 * <li>�޸�:  (lID > 0)
	 * <li>�������������ƺ��޸�
	 * <li>����ͬ����
	 * </ul>
	 * </ol>
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	 *
	 * @param     long        lID                    ����ƻ���ʾ
	 * @param     long        lLoanID                �����ʾ
	 * @param     long        lContractID            ��ͬ��ʾ
	 * @param     Timestamp   tsPlanDate             ԭʼ�ƻ�����
	 * @param     int         nLoanOrRepay           ��/��
	 * @param     double      dAmount                ���
	 * @param     String      strType                ���ͣ�����
	 * @param     long        lInputUserID           �û���ʾ
	 * @param     Timestamp   tsInputDate            ����ʱ��/�޸�ʱ��
	 * @param     long        lExtendApplyID         չ�������ʾ
	 * @param     long        lOverdueApplyID        ���������ʾ
	 * @param     long        lIsOverdue             �Ƿ�����
	 * @param     long        lUserTypeID            �޸���Դ�����롢չ�ڡ����ڡ��˵���
	 * @param     int         nAddANewVersion        �Ƿ�����һ���汾��
	 *
	 * @param     long       lOfficeID               ���´���ʾ��ѡ��ʹ�ã��������ں˶��Ƿ���loanInfo�еİ��´���ͬ
	 *
	 * @return    long     �������޸ĳɹ�������ֵ ContractPayPlanVersion�м�¼��ID��ʧ�ܣ�����ֵ == 0����Ҫ�����޸���Ϣ
	 *
	 * @exception Exception
	**/
	public long savePlan(PlanDetailInfo o) throws RemoteException, IRollbackException;

	/**
	 * �޸Ļ���ƻ������˺�
	 * <p>    
	 * <b>&nbsp;</b>
	 * <ol><b>�޸Ļ���ƻ�</b>
	 * <ul>
	 * <li>�������ݿ��ContractPayPlanVersion
	 * </ul>
	 * </ol>
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	 *
	 * @param     long        lID                    ����ƻ���ʾ
	 * @param     long        lCheckUserID           �����˱�ʾ
	 * @param     Timestamp   tsCheckDate            ����ʱ��
	 *
	 * @param     long       lOfficeID               ���´���ʾ��ѡ��ʹ�ã��������ں˶��Ƿ���loanInfo�еİ��´���ͬ
	 *
	 * @return    long     �޸ĳɹ�������ֵ == 1��ʧ�ܣ�����ֵ == 0����Ҫ�����޸���Ϣ
	 *
	 * @exception Exception
	**/
	public long savePlan(long lID, long lCheckUserID, Timestamp tsCheckDate, long lOfficeID) throws RemoteException;

	/**
	 * �Զ���������ƻ�
	 * <p>    
	 * <b>&nbsp;</b>
	 * <ol><b>��������ƻ�</b>
	 * <ul>
	 * <li>�������ݿ��ContractPayPlan��ContractPayPlanVersion
	 * <li>��ContractPayPlanVersion�в���һ���汾��Ϊ�յļ�¼
	 * <li>��ϸ�ƻ�����ContractPayPlan
	 * </ul>
	 * </ol>
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	 *
	 * @param     long        lLoanID                 �����ʾ
	 * @param     int         nLoanType               �ſʽ����
	 * @param     Timestamp   tsLoanStartDate         ��ʼ��������
	 * @param     int         nRepayType              ���ʽ����
	 * @param     Timestamp   tsRepayStartDate        ��ʼ��������
	 * @param     Timestamp   tsInputDate             ����ʱ��/�޸�ʱ��
	 * @param     String      strType                   ���ͣ�����
	 *
	 * @param     long        lUserID            �û���ʾ��ѡ��ʹ�ã��������ں˶��Ƿ���loanInfo�е�inputuser��ͬһ��
	 * @param     long        lOfficeID          ���´���ʾ��ѡ��ʹ�ã��������ں˶��Ƿ���loanInfo�еİ��´���ͬ
	 *
	 * @return    long     �������޸ĳɹ�������ֵ ContractPayPlanVersion�м�¼��ID��ʧ�ܣ�����ֵ == 0����Ҫ�����޸���Ϣ
	 *
	 * @exception Exception
	**/
	public long autosavePlan(PlanAssignInfo o) throws RemoteException, IRollbackException;

	/**
	 * ȷ���°滹��ƻ�
	 * <p>    
	 * <b>&nbsp;</b>
	 * <ol><b>ȷ���°滹��ƻ�</b>
	 * <ul>
	 * <li>�������ɡ�ִ�д˷���
	 * <li>�������ݿ��ContractPayPlanVersion
	 * <li>����ContractPayPlanVersion�м�¼�İ汾��
	 * </ul>
	 * </ol>
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	 *
	 * @param     long        lID                     �汾��¼��ʾ
	 *
	 * @param     long        lUserID            �û���ʾ��ѡ��ʹ�ã��������ں˶��Ƿ���loanInfo�е�inputuser��ͬһ��
	 * @param     long        lOfficeID          ���´���ʾ��ѡ��ʹ�ã��������ں˶��Ƿ���loanInfo�еİ��´���ͬ
	 *
	 * @return    long        �ɹ�������ֵ == 1��ʧ�ܣ�����ֵ == 0��
	 *
	 * @exception Exception
	**/
	public long createPlanVersion(long lID, long lLoanID, long lUserID, long lOfficeID, long lCurrencyID) throws RemoteException, IRollbackException;

	/**
	 * ���ݴ����ͬ�Ų��Ҽƻ���Ϣ
	 * <p>    
	 * <b>&nbsp;</b>
	 * <ol><b>���Ҵ�����Ϣ</b>
	 * <ul>
	 * <li>�������ݿ��ContractPayPlanVersion��ContractPayPlan
	 * <li>�õ��汾����ߵ���Ϣ
	 * </ul>
	 * </ol>
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	 *
	 * @param     long     lContractID                 �����ͬ��ʾ
	 *
	 * @param     long     lPageLineCount         ÿҳҳ��������
	 * @param     long     lPageNo                �ڼ�ҳ����
	 * @param     long     lOrderParam            �������������ݴ˲��������������������
	 * @param     long     lDesc                  �������
	 *
	 * @return    Collection     
	 *
	 * @exception Exception
	**/
	public Collection findPlanByContract(long lContractID, long lPageLineCount, long lPageNo, long lOrderParam, long lDesc) throws RemoteException,IException;

	/**
	 * ����ContractPayPlanVersion�е�ID���Ҽƻ���Ϣ
	 * <p>    
	 * <b>&nbsp;</b>
	 * <ol><b>���Ҵ�����Ϣ</b>
	 * <ul>
	 * <li>�����İ汾��
	 * </ul>
	 * </ol>
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	 *
	 * @param     long     ContractPayPlanVersionID    ContractPayPlanVersion�е�ID
	 *
	 * @param     long     lPageLineCount         ÿҳҳ��������
	 * @param     long     lPageNo                �ڼ�ҳ����
	 * @param     long     lOrderParam            �������������ݴ˲��������������������
	 * @param     long     lDesc                  �������
	 *
	 * @param     long     lUserID               �û���ʾ��ѡ��ʹ�ã��������ں˶��Ƿ���loanInfo�е�inputuser��ͬһ��
	 * @param     long     lOfficeID             ���´���ʾ��ѡ��ʹ�ã��������ں˶��Ƿ���loanInfo�еİ��´���ͬ
	 *
	 * @return    Collection     
	 *
	 * @exception Exception
	**/
	public Collection findPlanByVer(
		long ContractPayPlanVersionID,
		long lPageLineCount,
		long lPageNo,
		long lOrderParam,
		long lDesc,
		long lUserID,
		long lOfficeID)
		throws RemoteException;

	/**
	 * ���ݼƻ���ʾ���һ���ƻ�
	 * <p>    
	 * <b>&nbsp;</b>
	 * <ol><b>���һ���ƻ�</b>
	 * <ul>
	 * <li>�������ݿ��ContractPayPlan
	 * </ul>
	 * </ol>
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	 *
	 * @param     long       lID                ����ƻ���ʾ       
	 *
	 * @param     long       lUserID            �û���ʾ��ѡ��ʹ�ã��������ں˶��Ƿ���loanInfo�е�inputuser��ͬһ��
	 * @param     long       lOfficeID          ���´���ʾ��ѡ��ʹ�ã��������ں˶��Ƿ���loanInfo�еİ��´���ͬ
	 *
	 * @return    PayPlanInfo     
	 *
	 * @exception Exception
	**/
	public RepayPlanInfo findPlanByID(long lID, long lUserID, long lOfficeID) throws RemoteException;

	/**
	 * ɾ������ƻ�
	 * <p>    
	 * <b>&nbsp;</b>
	 * <ol><b>ɾ��lID[]ָ���Ļ���ƻ�</b>
	 * <ul>
	 * <li>�������ݿ��ContractPayPlan
	 * <li>ע�⣺�Ƿ�Ҫ�����µİ汾��Ϣ�����Ҫ��Ҫ���ȸ������һ�εĻ���ƻ���
	 * <li>�����µĻ����¼
	 * </ul>
	 * </ol>
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	 *
	 * @param     long     lID[]              �ƻ���ʾ����   
	 * @param     long     lLoanID            �����ʾ
	 * @param     int      nAddANewVersion    �Ƿ�����һ���汾��
	 *
	 * @param     long     lUserID            �û���ʾ��ѡ��ʹ�ã��������ں˶��Ƿ���loanInfo�е�inputuser��ͬһ��
	 * @param     long     lOfficeID          ���´���ʾ��ѡ��ʹ�ã��������ں˶��Ƿ���loanInfo�еİ��´���ͬ
	 *
	 * @return    long     ɾ���ɹ�������ֵ ���� ɾ���ļ�¼����ʧ�ܣ�����ֵ == -1��
	 *
	 * @exception Exception
	**/
	// ����һ�����������������Ƿ���Ҫ���������־��isFirst=trueʱ����Ҫ���棬=falseʱ��Ҫ����
//	public long deletePlan(long lID[], long lLoanID, long nAddANewVersion, long lUserID, long lOfficeID) throws RemoteException, IRollbackException;
	public long deletePlan(long lID[], long lLoanID, long nAddANewVersion, long lUserID, long lOfficeID, boolean isFirst) throws RemoteException, IRollbackException;

	/**
		 * ִ�мƻ����ĺ�ͬ���ң���ͬ��״̬Ϊ��ִ���С���
		 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	   * @param nIsHaveNew  �Ƿ�����Ҫ���˵ļƻ��汾
		 * @param lCurrencyID ���ֱ�ʶ
		 * @param lOfficeID ���´���ʶ
		 * @param lContractIDFrom ��ͬ�����ʼ
		 * @param lContractIDTo ��ͬ��Ž���
		 * @param lClientID ��λ��ʶ
	 	 * @param lPeriod  ����
		 * @param dAmountFrom �����ʼ
		 * @param dAmountTo ������
	   * @param tsUpdateFrom  ��ʼ�޸�����
	   * @param tsUpdateTo    �����޸�����
	   * @param lStatusID  ״̬
		 * @return Collection 
		 * @exception Exception
		 */
	public Collection findContractByMultiOption(QueryContractInfo o) throws RemoteException;

	/**
		 * ����ContractPayPlanVersion�е�nContractID���Ҽƻ��汾��Ϣ
		 * <p>    
		 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	   *
	   * @param     long     lContractID    ContractPayPlanVersion�е�lContractID
	   *
	   * @param     long     lPageLineCount         ÿҳҳ��������
	   * @param     long     lPageNo                �ڼ�ҳ����
	   * @param     long     lOrderParam            �������������ݴ˲��������������������
	   * @param     long     lDesc                  �������
	   *
	   * @param     long     lUserID               �û���ʾ��ѡ��ʹ�ã��������ں˶��Ƿ���loanInfo�е�inputuser��ͬһ��
	   * @param     long     lOfficeID             ���´���ʾ��ѡ��ʹ�ã��������ں˶��Ƿ���loanInfo�еİ��´���ͬ
	   *
		 * @return    Collection     
	   *
		 * @exception Exception
	 **/
	public Collection findPlanVerByContract(long lContractID, long lPageLineCount, long lPageNo, long lOrderParam, long lDesc, long lUserID, long lOfficeID)
		throws RemoteException;

	// ͨ����ͬID�õ�����PlanID
	public long findMaxVersionID(long lContractID) throws RemoteException;
	
	//add by zwxiao ͨ����ͬID�õ�����PlanVersion
	public long findMaxVersionCode(long lContractID) throws RemoteException;

	/**
	* ȡ�����ύ�ĺ�ִͬ�мƻ��޸�
	* <p>
	* @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	*
	* @param     long        lPlanModifyID    ��ִͬ�мƻ��޸ļ�¼��ʾ
	* @param     long        lUserID               �޸���
	* @return    long        �ɹ�������ֵ == 1��ʧ�ܣ�����ֵ == -1
	* @exception long 
	* */
	public long cancelRepayPlan(long lPlanModifyID, long lUserID) throws RemoteException, IRollbackException;

	/**
		 * ��˺˸��ĵĺ�ִͬ�мƻ�
		 * <p>    
		 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	     *
		 * @param     long        nReviewContentID      ������������ContractPayPlanVersion�е�ID
		 * @param     String      sOpinion              �������
		 * @param     long        nUserID               �����˱�ʾ
		 * @param     long        nNextUserID           ��һ�������˱�ʾ
		 * @param     long        lAction               �������ܾ����޸ģ��������
		 * @return    long        �ɹ�������ֵ == 1��ʧ�ܣ�����ֵ == -1
		 * @exception long
	  **/
	public long checkRepayPlan(long lApprovalContentID, String sOpinion, long lUserID, long lNextUserID, long lAction,long lCurrencyID,long lOfficeID) throws RemoteException, IRollbackException;

	/**
	 * �жϺ�ִͬ�мƻ��Ƿ��ܹ���ָ������Դ�޸�
	 * <p>    
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	 *
	 * @param     long     lID                   ��ͬ��ʾ
	 * @param     long     lSourceID             ��Դ��ʾ
	 * @param     long     lUserID               �û���ʾ
	 *
	 * @return    Collection     
	 *
	 * @exception long
	 **/
	public long findCanBeModify(long lContractID, long lSourceID, long lUserID) throws RemoteException;
	
	public long commitAndApprovalInit(PlanModifyInfo info) throws RemoteException, IRollbackException;
	
	public long doApproval(PlanModifyInfo cInfo) throws RemoteException,IRollbackException ;
	
	public long cancelApproval(PlanModifyInfo Info)throws RemoteException, IRollbackException;
	
	/**
     * @author yunchang
     * @date 2010-06-03
     * @function:
     * upRepayPlanRate  �����������ʵ���
     * ���� LOAN_RATEADJUSTPAYCONDITION ���ݱ�
     * ���±�������
     * ����ֵΪ-1��ʶʧ�ܣ�n>0��ʶ���µ�����
     * @return long  �ɹ�����ID��ʶ��ʧ�ܷ���0
     * @throws RemoteException
     */
    public Collection adjustRzzlRate (AdjustPayConditionInfo adjustPayConditionInfo,Collection repayColl,ContractInfo contractInfo,long planID) throws java.rmi.RemoteException, IRollbackException;  
    
    /**
     * @author zwxiao
     * @date 2010-06-20
     * @function:
     * �����������޵����ʺͻ���ƻ�
     * ����ֵΪ-1��ʶʧ�ܣ�n>0��ʶ���µ�����
     * @return long  �ɹ�����ID��ʶ��ʧ�ܷ���0
     * @throws RemoteException
     */
    public long saveAdjustPlanAndRate (PlanModifyInfo pinfo,Collection repayColl,AdjustPayConditionInfo conditionInfo,ContractInfo contractInfo,long planID) throws java.rmi.RemoteException, IRollbackException; 
    
    /**
     * @author zwxiao
     * @date 2010-06-20
     * @function:
     * ɾ���������޵����ʺͻ���ƻ�
     * ����ֵΪ-1��ʶʧ�ܣ�n>0��ʶ���µ�����
     * @return long  �ɹ�����ID��ʶ��ʧ�ܷ���0
     * @throws RemoteException
     */
    public long deleteAdjustPlanAndRate (AdjustPayConditionInfo conditionInfo,ContractInfo contractInfo,long planID) throws java.rmi.RemoteException, IRollbackException;

    /**
     * @author zwxiao
     * @date 2010-06-20
     * @function:
     * ͨ����ͬID�õ�����PlanID
     * ����ֵΪ-1��ʶʧ�ܣ�n>0��ʶ���µ�����
     * @return long  �ɹ�����ID��ʶ��ʧ�ܷ���0
     * @throws RemoteException
     */
	public long findNewMaxVersionID(long lContractID,long planID) throws RemoteException, IRollbackException;
	
	//add by zwxiao 2010-07-08 ȡ�����ʵ�������ԭʼ�İ汾ID
	public long findOldMaxVersionID(long lContractID) throws java.rmi.RemoteException, IRollbackException;
	
	//add by zwxiao 2010-07-08 ȡ�����ʵ�������ԭʼ�İ汾code
	public long findOldMaxVersionCode(long lContractID) throws java.rmi.RemoteException, IRollbackException;
}
