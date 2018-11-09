/*
 * Created on 2003-8-7
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.creditrating.creditrating.bizlogic;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.ejb.SessionBean;

import com.iss.itreasury.creditrating.creditrating.dao.CreditRatingDao;
import com.iss.itreasury.creditrating.creditrating.dataentity.CreditRatingFinalInfo;
import com.iss.itreasury.creditrating.creditrating.dataentity.CreditRatingInfo;
import com.iss.itreasury.creditrating.creditrating.dataentity.SubCreditRatingInfo;
import com.iss.itreasury.creditrating.util.CreRtConstant;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.settlement.transfixeddeposit.dataentity.TransFixedOpenInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;
import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.ConfigConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.FSWorkflowManager;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IRollbackException;
import com.iss.itreasury.util.ITreasuryBaseDataEntity;
import com.iss.itreasury.util.Log4j;


/**
 * @author zcwang
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class CreditRatingEJB implements SessionBean {
	private javax.ejb.SessionContext mySessionCtx = null;

	final static long serialVersionUID = 3206093459760846163L;

	private Log4j log = new Log4j(Constant.ModuleType.CREDITRATING, this);

	/**
	 * ejbActivate method comment
	 * 
	 * @exception RemoteException
	 *                �쳣˵����
	 */
	public void ejbActivate() throws RemoteException {
	}

	/**
	 * ejbCreate method comment
	 * 
	 * @exception javax.ejb.CreateException
	 *                �쳣˵����
	 * @exception RemoteException
	 *                �쳣˵����
	 */
	public void ejbCreate() throws javax.ejb.CreateException, RemoteException {
	}

	/**
	 * ejbPassivate method comment
	 * 
	 * @exception RemoteException
	 *                �쳣˵����
	 */
	public void ejbPassivate() throws RemoteException {
	}

	/**
	 * ejbRemove method comment
	 * 
	 * @exception RemoteException
	 *                �쳣˵����
	 */
	public void ejbRemove() throws RemoteException {
	}

	/**
	 * getSessionContext method comment
	 * 
	 * @return javax.ejb.SessionContext
	 */
	public javax.ejb.SessionContext getSessionContext() {
		return mySessionCtx;
	}

	/**
	 * setSessionContext method comment
	 * 
	 * @param ctx
	 *            javax.ejb.SessionContext
	 * @exception RemoteException
	 *                �쳣˵����
	 */
	public void setSessionContext(javax.ejb.SessionContext ctx)
			throws RemoteException {
		mySessionCtx = ctx;
	}

	/**
	 * ��һ����һ���õ����
	 * @param info
	 * @return
	 * @throws IRollbackException
	 * @throws RemoteException
	 */
	public String nextOneStep(CreditRatingInfo info) throws IRollbackException, RemoteException 
	{
		String strReturn = "";
		CreditRatingDao dao = new CreditRatingDao();
		try {
			strReturn = dao.nextOneStep(info);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		return strReturn;
	}

	/**
	 * ͨ��ID,��������������Ϣ������
	 * @param info
	 * @return
	 * @throws IRollbackException
	 * @throws RemoteException
	 */
	public ITreasuryBaseDataEntity getCreditRatingByCondition(long ID, Class className) throws IRollbackException, RemoteException 
	{
		ITreasuryBaseDataEntity resultInfo = null;
		CreditRatingDao dao = new CreditRatingDao("crert_creditrating");
		try {
			resultInfo = dao.getCreditRatingByCondition(ID,className);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		return resultInfo;
	}
	/**
	 * �ڶ�����һ��(����)
	 * @param info
	 * @return
	 * @throws IRollbackException
	 * @throws RemoteException
	 */
	public long nextTwoStepSave(CreditRatingInfo info) throws IRollbackException, RemoteException 
	{
		long lReturn = -1;
		CreditRatingDao dao = new CreditRatingDao("crert_creditrating");
		try {
			if(dao.validateRatingCode(info))
			{
				throw new IException("������ͬ��ҵ����,���ܱ���");
			}
			if(dao.validateSameDate(info) && Config.getBoolean(ConfigConstant.LOAN_CREDITRATING_CHECKSAMERATINGDATE,false))
			{
				throw new IException("�˿ͻ�����ʱ�佻�����������,���ܱ���");
			}
			if(info.getID()<0)
			{	
				//�õ�����
				info.setRatingResult(dao.findSubtandardratingNameByRatingID(info.getRatingprojectID(), info.getRatingNumeric()));
				
				lReturn = dao.nextTwoStepSave(info);
				//�������������ӱ���Ϣ
				if(info.getSubInfoColl()!=null &&info.getSubInfoColl().size()>0)
				{
					Iterator it = info.getSubInfoColl().iterator();
					while(it.hasNext())
					{
						SubCreditRatingInfo subInfo = (SubCreditRatingInfo)it.next();
						subInfo.setCreditratingID(lReturn);
						CreditRatingDao subDao = new CreditRatingDao("crert_subcreditrating");
						subDao.nextTwoStepSave(subInfo);
					}
				}
			}
			else
			{

				//�õ�����
				info.setRatingResult(dao.findSubtandardratingNameByRatingID(info.getRatingprojectID(), info.getRatingNumeric()));
				
				 lReturn = dao.save(info);
				 //���޸�ҳ�����
				 if(info.getSubInfoColl()!=null&& info.getSubInfoColl().size()>0)
				 {
					 Iterator it = info.getSubInfoColl().iterator();
					 while(it.hasNext())
					{
						SubCreditRatingInfo subInfo = (SubCreditRatingInfo)it.next();
						CreditRatingDao subDao = new CreditRatingDao("crert_subcreditrating");
						subDao.save(subInfo);
					}
				 }
				 
			}
			//
			info.setID(lReturn);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		return lReturn;
	}
	/**
	 * ͨ����������ID�����������ĵ��������������AAA��
	 * @param ratingProjectID
	 * @param ratingnumeric
	 * @return
	 * @throws IRollbackException
	 * @throws RemoteException
	 */
	public String findSubtandardratingNameByRatingID(long ratingProjectID,double ratingnumeric)throws IRollbackException, RemoteException 
	{
		String strReturn = "";
		CreditRatingDao dao = new CreditRatingDao();
		try {
		strReturn = dao.findSubtandardratingNameByRatingID(ratingProjectID, ratingnumeric);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		return strReturn;
	}
	/**
	 * ͨ������ID,�õ������ӱ�MAP
	 * @param ratingID
	 * @return
	 * @throws IRollbackException
	 * @throws RemoteException
	 */
	public Map findSubCreditRatingValueByRatingID(long ratingID)throws IRollbackException, RemoteException 
	{
		Map map = null;
		CreditRatingDao dao = new CreditRatingDao();
		try {
			map = dao.findSubCreditRatingValueByRatingID(ratingID);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		return map;
	}
	
	/**
	 * ͨ����������ID,�õ�������׼���ƣ�����AAA;;AA;;A��
	 * @param ratingProjectID
	 * @return
	 * @throws ITreasuryDAOException
	 */
	public Collection findSubtandardratingNamesByProjectID(long ratingProjectID)throws IRollbackException, RemoteException 
	{
		Collection coll = null;
		CreditRatingDao dao = new CreditRatingDao();
		try {
			coll = dao.findSubtandardratingNamesByProjectID(ratingProjectID);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		return coll;
	}
	/**
	 * ����
	 * @param info
	 * @return
	 * @throws IRollbackException
	 * @throws RemoteException
	 */
	public long save(CreditRatingInfo info) throws IRollbackException, RemoteException 
	{
		long lReturn = -1;
		CreditRatingDao dao = new CreditRatingDao("crert_creditrating");
		try {
			if(info.getState()!=CreRtConstant.CreRtStatus.DELETE)
			{
				if(dao.validateRatingCode(info))
				{
					throw new IException("������ͬ��ҵ����,���ܱ���");
				}
				if(dao.validateSameDate(info) && Config.getBoolean(ConfigConstant.LOAN_CREDITRATING_CHECKSAMERATINGDATE,false))
				{
					throw new IException("�˿ͻ�����ʱ�佻�����������,���ܱ���");
				}
			}
			if(info.getID()>0)
			{
				 lReturn = dao.save(info);
				 //���޸�ҳ�����
				 if(info.getSubInfoColl()!=null&& info.getSubInfoColl().size()>0)
				 {
					 Iterator it = info.getSubInfoColl().iterator();
					 while(it.hasNext())
					{
						SubCreditRatingInfo subInfo = (SubCreditRatingInfo)it.next();
						CreditRatingDao subDao = new CreditRatingDao("crert_subcreditrating");
						subDao.save(subInfo);
					}
				 }
				 
			}
			else
			{
			  info.setID(-1);
			  lReturn = dao.nextTwoStepSave(info);
			}
			/**
			 * ���Info�е�InutParameterInfo��Ϊ��,����Ҫ�ύ���� add by ���� 2007-04-17
			 */
			if (info.getInutParameterInfo() != null) {
				log.debug("------�ύ����--------");	
				InutParameterInfo tempInfo = info.getInutParameterInfo();
				tempInfo.setUrl(tempInfo.getUrl() + lReturn);
				tempInfo.setTransID(String.valueOf(lReturn));// ���ﱣ����ǽ��ױ��
				tempInfo.setDataEntity(info);

				// �ύ����
				FSWorkflowManager.initApproval(info.getInutParameterInfo());
				log.debug("------�ύ�����ɹ�--------");

			}
			info.setID(lReturn);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		return lReturn;
	}
	/**
	 * ����
	 * @param info
	 * @return
	 * @throws IRollbackException
	 * @throws RemoteException
	 */
	public long doApproval(CreditRatingInfo info) throws IRollbackException, RemoteException 
	{
		long lReturn = -1;
		CreditRatingDao dao = new CreditRatingDao("crert_creditrating");
		InutParameterInfo returnInfo = new InutParameterInfo();
		InutParameterInfo inutParameterInfo = info.getInutParameterInfo();
		try {
			
			CreditRatingInfo depositInfo = new CreditRatingInfo();
			depositInfo = (CreditRatingInfo)this.getCreditRatingByCondition(info.getID(),CreditRatingInfo.class);
			inutParameterInfo.setDataEntity(depositInfo);
			// �ύ����
			returnInfo = FSWorkflowManager.doApproval(inutParameterInfo);
			//��������һ��,��Ϊ����ͨ��,����״̬Ϊ������
			if (returnInfo.isLastLevel()) {
					depositInfo.setState(CreRtConstant.CreRtStatus.APPROVALED);
					dao.save(depositInfo);
					//����ͨ�������ݴ��������������
					CreditRatingFinalInfo resultInfo = new CreditRatingFinalInfo();
					resultInfo.setID(-1);
					resultInfo.setCreditratingID(depositInfo.getID());
					resultInfo.setRatingCode(depositInfo.getRatingCode());
					resultInfo.setRatingprojectID(depositInfo.getRatingprojectID());
					resultInfo.setRatingprojectName(depositInfo.getRatingprojectName());
					resultInfo.setRatingType(depositInfo.getRatingType());
					resultInfo.setRatingNumeric(depositInfo.getRatingNumeric());
					resultInfo.setRatingResult(depositInfo.getRatingResult());
					resultInfo.setRemark(depositInfo.getRemark());
					resultInfo.setClientID(depositInfo.getClientID());
					resultInfo.setOfficeID(depositInfo.getOfficeID());
					resultInfo.setCurrencyID(depositInfo.getCurrencyID());
					resultInfo.setStateDate(depositInfo.getStateDate());
					resultInfo.setEndDate(depositInfo.getEndDate());
					resultInfo.setState(depositInfo.getState());
					resultInfo.setInputuserID(depositInfo.getInputuserID());
					resultInfo.setInputdate(Env.getSystemDate());
					resultInfo.setRatingDate(Env.getSystemDate());
					CreditRatingDao daoResult= new CreditRatingDao("crert_creditratingdetail");
					
					daoResult.add(resultInfo);
					//
				}
			
			// ��������һ��,��Ϊ�����ܾ�,����״̬Ϊ�ѱ���
			else if (returnInfo.isRefuse()) {
				depositInfo.setState(CreRtConstant.CreRtStatus.SAVE);
				dao.save(depositInfo);
			}
			lReturn = depositInfo.getID();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		return lReturn;
	}
	/**
	 * ȡ������
	 * @param info
	 * @return
	 * @throws IRollbackException
	 * @throws RemoteException
	 */
	public long cancelApproval(CreditRatingInfo info) throws IRollbackException, RemoteException 
	{
		long lReturn = -1;
		CreditRatingDao dao = new CreditRatingDao("crert_creditrating");
		InutParameterInfo inutParameterInfo = info.getInutParameterInfo();
		try {
			if(dao.validateCancelCreditRating(info.getID()))
			{
				throw new IException("�����������Ѿ����������ع������ϲ���������ȡ������");
			}
		   	CreditRatingInfo depositInfo = new CreditRatingInfo();
			  depositInfo = (CreditRatingInfo)this.getCreditRatingByCondition(info.getID(),CreditRatingInfo.class);
			  depositInfo.setState(CreRtConstant.CreRtStatus.SAVE);
			  dao.save(depositInfo);
			  CreditRatingDao dao2 = new CreditRatingDao();
			  dao2.cancelFinalRatingInfo(depositInfo.getID(), CreRtConstant.CreRtStatus.DELETE);
			//��������¼���ڵĸý��׵�������¼״̬��Ϊ��Ч
			if (inutParameterInfo.getApprovalEntryID() > 0) {
				FSWorkflowManager.cancelApprovalRecord(inutParameterInfo);
			}
			lReturn = depositInfo.getID();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		return lReturn;
	}
}
