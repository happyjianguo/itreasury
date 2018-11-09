/*
 * Created on 2003-8-7
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.interest.bizlogic;

import java.rmi.RemoteException;
import java.util.Collection;

import javax.ejb.SessionBean;

import com.iss.itreasury.settlement.interest.dao.Sett_CompoundInterestSettingDAO;
import com.iss.itreasury.settlement.interest.dataentity.CompoundInterestSettingInfo;
import com.iss.itreasury.settlement.transfixeddeposit.dataentity.QueryByStatusConditionInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.IRollbackException;
import com.iss.itreasury.util.Log4j;
/**
 * @author xrli
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class CompoundInterestSettingEJB implements SessionBean
{
	private javax.ejb.SessionContext mySessionCtx = null;
	final static long serialVersionUID = 3206093459760846163L;
	private Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);
	/**
	 * ejbActivate method comment
	 * @exception RemoteException �쳣˵����
	 */
	public void ejbActivate() throws RemoteException
	{
	}
	/**
	 * ejbCreate method comment
	 * @exception javax.ejb.CreateException �쳣˵����
	 * @exception RemoteException �쳣˵����
	 */
	public void ejbCreate() throws javax.ejb.CreateException, RemoteException
	{
	}
	/**
	 * ejbPassivate method comment
	 * @exception RemoteException �쳣˵����
	 */
	public void ejbPassivate() throws RemoteException
	{
	}
	/**
	 * ejbRemove method comment
	 * @exception RemoteException �쳣˵����
	 */
	public void ejbRemove() throws RemoteException
	{
	}
	/**
	 * getSessionContext method comment
	 * @return javax.ejb.SessionContext
	 */
	public javax.ejb.SessionContext getSessionContext()
	{
		return mySessionCtx;
	}
	/**
	 * setSessionContext method comment
	 * @param ctx javax.ejb.SessionContext
	 * @exception RemoteException �쳣˵����
	 */
	public void setSessionContext(javax.ejb.SessionContext ctx) throws RemoteException
	{
		mySessionCtx = ctx;
	}

	/**
	 * ���ڣ�֪ͨ���������ױ���ǰ����Ƿ��ظ��ķ�����
	 * 
	 * 1��������
	 *    FixdOpenInfo ����ʵ����
	 * 
	 * 2������ֵ��
	 *    String , �ظ�ʱ����ʾ��Ϣ��������ظ�������null��
	 * 
	 * 3���߼�˵����
	 *    ��1���жϲ���FixdOpenInfo,�еĽ���ʵ����Ľ��ױ���Ƿ�Ϊ�ա�
	 *        ����ǿգ�˵�����������棺
	 *            �÷�����Sett_TransOpenFixedDeposit.checkIsDuplicate()�ж��Ƿ��ظ���
	 * @roseuid 3F73AE9300E8
	 */	

	/**
	 * ���ڣ�֪ͨ���������׵ı��淽����
	 * 
	 * 1��������
	 * FixdOpenInfo, ����ʵ����
	 * 
	 * 2������ֵ��
	 *    long ,���ڣ�֪ͨ�����׵ı�ʶ�����ʧ�ܣ�����-1
	 * 
	 * 3���߼�˵����
	 *    ��1���жϲ���FixdOpenInfo,�еı�����ʵ����Ľ��ױ���Ƿ�Ϊ�ա�
	 *        ����ǿգ�˵�����������棺
	 *            
	 * ���÷�����XXX.getTransactionNo()�õ�һ�����׺ţ�������д��FixdOpenInfo ��
	 *        ����ǿգ�˵�����޸ı���:
	 *            ���÷���this.openCheckIsTouched,�ж�Ҫ�ݴ�ļ�¼�Ƿ��޸Ĺ���
	 *            ���÷�����this.openFindByID(),�õ�����ԭ���Ľ���ʵ����FixdOpenInfo��
	 *            
	 * ���÷�����AccountDetail.deleteFixedDeposit()���ع�ԭ���Ĳ�����ע�������ԭ��
	 * ��ʵ��TransFixedOpenInfo��
	 *   ��2�����÷�����Sett_TransOpenFixedDepositDAO.add() ������Ϣ��
	 *   ��3�����÷�����AccountDetail.saveOpenFixedDeposit()�����в�����
	 *   ��4�����÷�����Sett_TransOpenFixedDepositDAO.updateStatus() 
	 * ���޸Ľ��׵�״̬Ϊ���档
	 * @roseuid 3F73AE99038F
	 *
		*  @throws RemoteException,IRollbackException
		*/
	public long save(CompoundInterestSettingInfo info) throws IRollbackException,RemoteException
	{
		long lReturn = -1;
		long sessionID = -1;
		//���ݷ��ʶ���
		Sett_CompoundInterestSettingDAO dao = new Sett_CompoundInterestSettingDAO();		
		log.debug("---------��ʼSave---------------");
		try
		{		
			//У��
			if(info.getSettingName()==null || info.getSettingName().equals("")){
				throw new Exception("��Ϣ�����������Ʋ���Ϊ��");
			}else{
				if(!dao.validateBySettingName(info.getOfficeID(), info.getCurrencyID(), info.getSettingName())){
					throw new Exception("��Ϣ�������������Ѵ���");
				}
			}
			
			if (info.getID() < 0)
			{				
				lReturn = dao.add(info);			
				//�޸Ľ��׵�״̬Ϊ���档
				lReturn = dao.updateStatus(info.getID(), SETTConstant.BooleanValue.ISTRUE);
			}
			else
			{
				//��������� �������ٱ���
				lReturn = dao.update(info);
				
			}
			
		}
		catch (RemoteException e)
		{
			throw e;
		}
		catch (IRollbackException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		
		log.debug("---------����Save---------------");
		return lReturn;
	}

	/**
	 * �����������õ�ɾ��������
	 * 
	 * @roseuid 3F73AE9E010B
	 */
	public long delete(CompoundInterestSettingInfo info) throws IRollbackException,RemoteException
	{
		long lReturn = -1;

		//����ʱʹ��		
		long sessionID = -1;

		Sett_CompoundInterestSettingDAO dao = new Sett_CompoundInterestSettingDAO();
		
				

		log.debug("---------��ʼDelete---------------");
		try
		{			
			lReturn = dao.updateStatus(info.getID(), SETTConstant.BooleanValue.ISFALSE);					
		}
		catch (RemoteException e)
		{
			throw e;
		}
		catch (IRollbackException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}		
		log.debug("---------����Delete---------------");
		return lReturn;
	}

	/**
	 * 
	 * @roseuid 3F73AEB8007A
	 */
	public CompoundInterestSettingInfo findByID(long lID) throws IRollbackException,RemoteException
	{
		CompoundInterestSettingInfo info = new CompoundInterestSettingInfo();

		Sett_CompoundInterestSettingDAO dao = new Sett_CompoundInterestSettingDAO();
		try
		{
			info = dao.findByID(lID);
		}
		catch (Exception e)
		{
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		return info;
	}
	/**
	 * ����״̬��ѯ�ķ�����
	 * 
	 * 1��������
	 *    QueryByStatusConditionInfo, ��״̬��ѯ�Ĳ�ѯ����ʵ���ࡣ
	 * 
	 * 2������ֵ��
	 *    Collection ,����TransFixedOpenInfo��ѯ���ʵ����ļ�¼��
	 * 
	 * 3���߼�˵����
	 *    ����Sett_TransOpenFixedDepositDAO.findByStatus()������
	 * @roseuid 3F73AEBB0273
	 */
	public Collection findByStatus(QueryByStatusConditionInfo info) throws IRollbackException,RemoteException
	{
		Collection coll = null;
		Sett_CompoundInterestSettingDAO dao = new Sett_CompoundInterestSettingDAO();
		try
		{
			coll = dao.findByStatus(info);
		}
		catch (Exception e)
		{
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		return coll;
	}

	/**
	 * ���ڣ�֪ͨ��֧ȡ/ת���ڽ��ױ���ǰ����Ƿ��ظ��ķ�����
	 * 
	 * 1��������
	 *    TransFixedDrawInfo ����ʵ����
	 * 
	 * 2������ֵ��
	 *    String , �ظ�ʱ����ʾ��Ϣ��������ظ�������null��
	 * 
	 * 3���߼�˵����
	 *    ��1���жϲ���TransFixedDrawInfo,�еĽ���ʵ����Ľ��ױ���Ƿ�Ϊ�ա�
	 *        ����ǿգ�˵�����������棺
	 *            �÷�����Sett_TransFixedWithDraw.checkIsDuplicate()�ж��Ƿ��ظ���
	 * @roseuid 3F73AF06006B
	 */	

	/**
	 * ��������ת�潻�ױ���ǰ����Ƿ��ظ��ķ�����
	 * 
	 * 1��������
	 *    FixedContinueInfo ����ʵ����
	 * 
	 * 2������ֵ��
	 *    String , �ظ�ʱ����ʾ��Ϣ��������ظ�������null��
	 * 
	 * 3���߼�˵����
	 *    ��1���жϲ���FixedContinueInfo,�еĽ���ʵ����Ľ��ױ���Ƿ�Ϊ�ա�
	 *        ����ǿգ�˵�����������棺
	 *            �÷�����Sett_TransFixedContinueDAO.checkIsDuplicate()�ж��Ƿ��ظ���
	 * @roseuid 3F73AF080349
	 */	
}
