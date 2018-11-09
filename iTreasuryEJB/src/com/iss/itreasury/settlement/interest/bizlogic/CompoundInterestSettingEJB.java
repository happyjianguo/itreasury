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
	 * @exception RemoteException 异常说明。
	 */
	public void ejbActivate() throws RemoteException
	{
	}
	/**
	 * ejbCreate method comment
	 * @exception javax.ejb.CreateException 异常说明。
	 * @exception RemoteException 异常说明。
	 */
	public void ejbCreate() throws javax.ejb.CreateException, RemoteException
	{
	}
	/**
	 * ejbPassivate method comment
	 * @exception RemoteException 异常说明。
	 */
	public void ejbPassivate() throws RemoteException
	{
	}
	/**
	 * ejbRemove method comment
	 * @exception RemoteException 异常说明。
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
	 * @exception RemoteException 异常说明。
	 */
	public void setSessionContext(javax.ejb.SessionContext ctx) throws RemoteException
	{
		mySessionCtx = ctx;
	}

	/**
	 * 定期（通知）开立交易保存前检测是否重复的方法：
	 * 
	 * 1、参数：
	 *    FixdOpenInfo 交易实体类
	 * 
	 * 2、返回值：
	 *    String , 重复时的提示信息；如果不重复，返回null。
	 * 
	 * 3、逻辑说明：
	 *    （1）判断参数FixdOpenInfo,中的交易实体类的交易编号是否为空。
	 *        如果是空，说明是新增保存：
	 *            用方法：Sett_TransOpenFixedDeposit.checkIsDuplicate()判断是否重复。
	 * @roseuid 3F73AE9300E8
	 */	

	/**
	 * 定期（通知）开立交易的保存方法：
	 * 
	 * 1、参数：
	 * FixdOpenInfo, 交易实体类
	 * 
	 * 2、返回值：
	 *    long ,定期（通知）交易的标识，如果失败，返回-1
	 * 
	 * 3、逻辑说明：
	 *    （1）判断参数FixdOpenInfo,中的本金交易实体类的交易编号是否为空。
	 *        如果是空，说明是新增保存：
	 *            
	 * 调用方法：XXX.getTransactionNo()得到一个交易号，并将其写入FixdOpenInfo 。
	 *        如果非空，说明是修改保存:
	 *            调用方法this.openCheckIsTouched,判断要暂存的记录是否被修改过。
	 *            调用方法：this.openFindByID(),得到包含原来的交易实体类FixdOpenInfo。
	 *            
	 * 调用方法：AccountDetail.deleteFixedDeposit()。回滚原来的财务处理。注意参数是原来
	 * 的实体TransFixedOpenInfo。
	 *   （2）调用方法：Sett_TransOpenFixedDepositDAO.add() 保存信息。
	 *   （3）调用方法：AccountDetail.saveOpenFixedDeposit()。进行财务处理。
	 *   （4）调用方法：Sett_TransOpenFixedDepositDAO.updateStatus() 
	 * 。修改交易的状态为保存。
	 * @roseuid 3F73AE99038F
	 *
		*  @throws RemoteException,IRollbackException
		*/
	public long save(CompoundInterestSettingInfo info) throws IRollbackException,RemoteException
	{
		long lReturn = -1;
		long sessionID = -1;
		//数据访问对象
		Sett_CompoundInterestSettingDAO dao = new Sett_CompoundInterestSettingDAO();		
		log.debug("---------开始Save---------------");
		try
		{		
			//校验
			if(info.getSettingName()==null || info.getSettingName().equals("")){
				throw new Exception("结息日期设置名称不能为空");
			}else{
				if(!dao.validateBySettingName(info.getOfficeID(), info.getCurrencyID(), info.getSettingName())){
					throw new Exception("结息日期设置名称已存在");
				}
			}
			
			if (info.getID() < 0)
			{				
				lReturn = dao.add(info);			
				//修改交易的状态为保存。
				lReturn = dao.updateStatus(info.getID(), SETTConstant.BooleanValue.ISTRUE);
			}
			else
			{
				//被保存过， 即保存再保存
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
		
		log.debug("---------结束Save---------------");
		return lReturn;
	}

	/**
	 * 复利计算设置的删除方法：
	 * 
	 * @roseuid 3F73AE9E010B
	 */
	public long delete(CompoundInterestSettingInfo info) throws IRollbackException,RemoteException
	{
		long lReturn = -1;

		//加锁时使用		
		long sessionID = -1;

		Sett_CompoundInterestSettingDAO dao = new Sett_CompoundInterestSettingDAO();
		
				

		log.debug("---------开始Delete---------------");
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
		log.debug("---------结束Delete---------------");
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
	 * 根据状态查询的方法：
	 * 
	 * 1、参数：
	 *    QueryByStatusConditionInfo, 按状态查询的查询条件实体类。
	 * 
	 * 2、返回值：
	 *    Collection ,包含TransFixedOpenInfo查询结果实体类的记录集
	 * 
	 * 3、逻辑说明：
	 *    调用Sett_TransOpenFixedDepositDAO.findByStatus()方法。
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
	 * 定期（通知）支取/转活期交易保存前检测是否重复的方法：
	 * 
	 * 1、参数：
	 *    TransFixedDrawInfo 交易实体类
	 * 
	 * 2、返回值：
	 *    String , 重复时的提示信息；如果不重复，返回null。
	 * 
	 * 3、逻辑说明：
	 *    （1）判断参数TransFixedDrawInfo,中的交易实体类的交易编号是否为空。
	 *        如果是空，说明是新增保存：
	 *            用方法：Sett_TransFixedWithDraw.checkIsDuplicate()判断是否重复。
	 * @roseuid 3F73AF06006B
	 */	

	/**
	 * 定期续期转存交易保存前检测是否重复的方法：
	 * 
	 * 1、参数：
	 *    FixedContinueInfo 交易实体类
	 * 
	 * 2、返回值：
	 *    String , 重复时的提示信息；如果不重复，返回null。
	 * 
	 * 3、逻辑说明：
	 *    （1）判断参数FixedContinueInfo,中的交易实体类的交易编号是否为空。
	 *        如果是空，说明是新增保存：
	 *            用方法：Sett_TransFixedContinueDAO.checkIsDuplicate()判断是否重复。
	 * @roseuid 3F73AF080349
	 */	
}
