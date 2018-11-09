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
	 *                异常说明。
	 */
	public void ejbActivate() throws RemoteException {
	}

	/**
	 * ejbCreate method comment
	 * 
	 * @exception javax.ejb.CreateException
	 *                异常说明。
	 * @exception RemoteException
	 *                异常说明。
	 */
	public void ejbCreate() throws javax.ejb.CreateException, RemoteException {
	}

	/**
	 * ejbPassivate method comment
	 * 
	 * @exception RemoteException
	 *                异常说明。
	 */
	public void ejbPassivate() throws RemoteException {
	}

	/**
	 * ejbRemove method comment
	 * 
	 * @exception RemoteException
	 *                异常说明。
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
	 *                异常说明。
	 */
	public void setSessionContext(javax.ejb.SessionContext ctx)
			throws RemoteException {
		mySessionCtx = ctx;
	}

	/**
	 * 第一个下一步得到编号
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
	 * 通过ID,返回信用评级信息（主）
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
	 * 第二个下一步(保存)
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
				throw new IException("存在相同的业务编号,不能保存");
			}
			if(dao.validateSameDate(info) && Config.getBoolean(ConfigConstant.LOAN_CREDITRATING_CHECKSAMERATINGDATE,false))
			{
				throw new IException("此客户存在时间交叉的信用评级,不能保存");
			}
			if(info.getID()<0)
			{	
				//得到级别
				info.setRatingResult(dao.findSubtandardratingNameByRatingID(info.getRatingprojectID(), info.getRatingNumeric()));
				
				lReturn = dao.nextTwoStepSave(info);
				//保存信用评级子表信息
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

				//得到级别
				info.setRatingResult(dao.findSubtandardratingNameByRatingID(info.getRatingprojectID(), info.getRatingNumeric()));
				
				 lReturn = dao.save(info);
				 //从修改页面进入
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
	 * 通过评级方案ID，评级分数的到评级结果（例：AAA）
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
	 * 通过评级ID,得到评级子表MAP
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
	 * 通过评级方案ID,得到评级标准名称（例：AAA;;AA;;A）
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
	 * 保存
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
					throw new IException("存在相同的业务编号,不能保存");
				}
				if(dao.validateSameDate(info) && Config.getBoolean(ConfigConstant.LOAN_CREDITRATING_CHECKSAMERATINGDATE,false))
				{
					throw new IException("此客户存在时间交叉的信用评级,不能保存");
				}
			}
			if(info.getID()>0)
			{
				 lReturn = dao.save(info);
				 //从修改页面进入
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
			 * 如果Info中的InutParameterInfo不为空,则需要提交审批 add by 刘琰 2007-04-17
			 */
			if (info.getInutParameterInfo() != null) {
				log.debug("------提交审批--------");	
				InutParameterInfo tempInfo = info.getInutParameterInfo();
				tempInfo.setUrl(tempInfo.getUrl() + lReturn);
				tempInfo.setTransID(String.valueOf(lReturn));// 这里保存的是交易编号
				tempInfo.setDataEntity(info);

				// 提交审批
				FSWorkflowManager.initApproval(info.getInutParameterInfo());
				log.debug("------提交审批成功--------");

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
	 * 审批
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
			// 提交审批
			returnInfo = FSWorkflowManager.doApproval(inutParameterInfo);
			//如果是最后一级,且为审批通过,更新状态为已审批
			if (returnInfo.isLastLevel()) {
					depositInfo.setState(CreRtConstant.CreRtStatus.APPROVALED);
					dao.save(depositInfo);
					//审批通过将数据存入评级结果表中
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
			
			// 如果是最后一级,且为审批拒绝,更新状态为已保存
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
	 * 取消审批
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
				throw new IException("此信用评级已经做过信用重估或作废操作，不能取消审批");
			}
		   	CreditRatingInfo depositInfo = new CreditRatingInfo();
			  depositInfo = (CreditRatingInfo)this.getCreditRatingByCondition(info.getID(),CreditRatingInfo.class);
			  depositInfo.setState(CreRtConstant.CreRtStatus.SAVE);
			  dao.save(depositInfo);
			  CreditRatingDao dao2 = new CreditRatingDao();
			  dao2.cancelFinalRatingInfo(depositInfo.getID(), CreRtConstant.CreRtStatus.DELETE);
			//将审批记录表内的该交易的审批记录状态置为无效
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
