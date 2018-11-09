package com.iss.itreasury.ebank.obrepayplan.bizlogic;

import java.rmi.*;
import java.util.*;
import javax.ejb.*;
import javax.ejb.EJBObject;
import com.iss.itreasury.ebank.obrepayplan.dataentity.*;
import com.iss.itreasury.ebank.obrepayplan.dao.*;
import com.iss.itreasury.ebank.util.*;
import com.iss.itreasury.loan.contract.dataentity.*;
import com.iss.itreasury.util.*;
import com.iss.itreasury.ebank.obdataentity.*;

public interface OBRepayPlan extends EJBObject {

	/**
	 * ִ�мƻ����ĺ�ͬ���ң���ͬ��״̬Ϊ��ִ���С���
	 * @Copyright (c) Jan. 2004, by iSoftStone Inc. All Rights Reserved
	 * @param OBQueryContractInfo  ��ѯ����
	 * @return Collection
	 * @exception Exception
	 */
	public Collection findContractByMultiOption(OBQueryContractInfo o) throws RemoteException,IException;

	/**
	 * �жϺ�ִͬ�мƻ��Ƿ��ܹ���ָ������Դ�޸�
	 * @Copyright (c) Jan. 2004, by iSoftStone Inc. All Rights Reserved
	 * @param     long     lID                   ��ͬ��ʾ
	 * @param     OBSecurityInfo     sInfo       ��ȫ��Ϣ
	 * @return    long
	 **/
	public long findCanBeModify(long lContractID,OBSecurityInfo sInfo) throws RemoteException,IException;

	/**
	 * ����ContractPayPlanVersion�е�nContractID���Ҽƻ��汾��Ϣ
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
     * @param     long     lContractID
     * @param     OBPageInfo     ��ҳ���������Ϣ
     * @param     OBSecurityInfo �йذ�ȫ����Ϣ
	 * @return    Collection
     * @exception Exception
	 **/
	public Collection findPlanVerByContract(long lContractID, OBPageInfo pageInfo,OBSecurityInfo securityInfo) throws RemoteException,IException;

	/**
	 * ����һ���汾��Ϊ�յ��°汾�������ư汾��ϸ
	 * @Copyright (c) Jan. 2004, by iSoftStone Inc. All Rights Reserved
	 * @param     OBPlanDetailInfo     dInfo
	 * @param     OBSecurityInfo     sInfo
	 * @return    Collection
	 * @exception Exception
	 **/
	public Collection addTempVersion(OBPlanDetailInfo dInfo) throws RemoteException,IException;

	/**
	 * ����ִ�мƻ��汾��ID���Ҽƻ���ϸ��Ϣ
	 * @Copyright (c) Jan. 2004, by iSoftStone Inc. All Rights Reserved
	 * @param     long     versionID
	 * @param     OBPageInfo     pInfo ��ҳ��Ϣ
	 * @param     OBSecurityInfo sInfo ��ȫ�����Ϣ
	 * @return    Collection
	 * @exception Exception
	**/
	public Collection findPlanByVer(long versionID,OBPageInfo pInfo,OBSecurityInfo sInfo) throws RemoteException,IException;

	/**
	 * ���ݺ�ͬ��ID���Һ�ͬ����Ϣ
	 * @Copyright (c) Jan. 2004, by iSoftStone Inc. All Rights Reserved
	 * @param     long     contractID
	 * @param     OBSecurityInfo sInfo ��ȫ�����Ϣ
	 * @return    Collection
	 * @exception Exception
	**/
	public ContractInfo findContractByID(long contractID,OBSecurityInfo sInfo) throws RemoteException,IException;

	/**
	 * �Զ���������ƻ�
	 * @Copyright (c) Jan. 2004, by iSoftStone Inc. All Rights Reserved
	 * @param     OBPlanAssignInfo     o
	 * @return    long     �������޸ĳɹ���
	 * @exception Exception
	**/
	public long autoSavePlan(OBPlanAssignInfo o) throws RemoteException,IException;

	/**
	 * ���ݼƻ���ʾ���һ���ƻ�
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	 * @param     long       lID                ����ƻ���ʾ
	 * @return    PayPlanInfo
	 * @exception Exception
	**/
	public OBRepayPlanInfo findPlanDetailByID(long lID,OBSecurityInfo sInfo) throws RemoteException,IException;

	/**
	* ��������ƻ������޸�
	* @Copyright (c) Jan. 2004, by iSoftStone Inc. All Rights Reserved
	* @param     Collection       detailList
	* @param     OBSecurityInfo         sInfo
	* @return    long     �������޸ĳɹ�
	* @exception Exception
	**/
	public long savePlan(Collection detailList) throws RemoteException,IException;

	/**
	* ɾ��ԭʼ�ƻ���ϸ
	* @Copyright (c) Jan. 2004, by iSoftStone Inc. All Rights Reserved
	* @param     long[]       lID
	* @param     OBSecurityInfo         sInfo
	* @return    long     �������޸ĳɹ�
	* @exception Exception
	**/
	public long deletePlan(long[] lID,OBSecurityInfo sInfo) throws RemoteException,IException;

	/**
	 * ȷ���°滹��ƻ�
	 * @Copyright (c) Jan. 2004, by iSoftStone Inc. All Rights Reserved
	 * @param     long        lID                     �汾��¼��ʾ
	 * @param     OBSecurityInfo sInfo
	 * @return    long        �ɹ�������ֵ == 1��ʧ�ܣ�����ֵ == 0��
	 * @exception Exception
	**/
	public long commit(long lID, OBSecurityInfo sInfo) throws RemoteException,IException;

	/**
	 * ȷ���°滹��ƻ�
	 * @Copyright (c) Jan. 2004, by iSoftStone Inc. All Rights Reserved
	 * @param     long        lID                     �汾��¼��ʾ
	 * @param     OBSecurityInfo sInfo
	 * @return    long        �ɹ�������ֵ == 1��ʧ�ܣ�����ֵ == 0��
	 * @exception Exception
	**/
	public long cancel(long lID, OBSecurityInfo sInfo) throws RemoteException,IException;

	/**
	 * ����ContractPayPlanVersion�е�nContractID���Ҽƻ��汾��Ϣ
	 * @Copyright (c) Jan. 2004, by iSoftStone Inc. All Rights Reserved
	 * @param     long     lContractID
	 * @param     OBPageInfo     ��ҳ���������Ϣ
	 * @param     OBSecurityInfo �йذ�ȫ����Ϣ
	 * @return    Collection
	 * @exception Exception
	 **/
	public Collection findPlanByContract(long lContractID, OBPageInfo pageInfo,OBSecurityInfo securityInfo) throws RemoteException ,IException;


	/**
	 * ����ContractPayPlanVersion�е�nContractID���Ҽƻ��汾��Ϣ
	 * @Copyright (c) Jan. 2004, by iSoftStone Inc. All Rights Reserved
	 * @param     long     lContractID
	 * @param     OBPageInfo     ��ҳ���������Ϣ
	 * @param     OBSecurityInfo �йذ�ȫ����Ϣ
	 * @return    Collection
	 * @exception Exception
	 **/
	public Collection findTempPlanVerByContract(long lContractID, OBPageInfo pageInfo,OBSecurityInfo securityInfo) throws RemoteException ,IException;

	/**
	 * ����ִ�мƻ��汾��ID���Ҽƻ���ϸ��Ϣ
	 * @Copyright (c) Jan. 2004, by iSoftStone Inc. All Rights Reserved
	 * @param     long     versionID
	 * @param     OBPageInfo     pInfo ��ҳ��Ϣ
	 * @param     OBSecurityInfo sInfo ��ȫ�����Ϣ
	 * @return    Collection
	 * @exception Exception
	**/
	public Collection findTempPlanByVer(long versionID,OBPageInfo pInfo,OBSecurityInfo sInfo) throws RemoteException,IException;

	/**
	 * ����ContractPayPlanVersion�е�nContractID���Ҽƻ��汾��Ϣ
	 * @Copyright (c) Jan. 2004, by iSoftStone Inc. All Rights Reserved
	 * @param     long     lContractID
	 * @param     OBPageInfo     ��ҳ���������Ϣ
	 * @param     OBSecurityInfo �йذ�ȫ����Ϣ
	 * @return    Collection
	 * @exception Exception
	 **/
	public Collection findTempPlanByContract(long lContractID, OBPageInfo pageInfo,OBSecurityInfo securityInfo) throws RemoteException ,IException;

}