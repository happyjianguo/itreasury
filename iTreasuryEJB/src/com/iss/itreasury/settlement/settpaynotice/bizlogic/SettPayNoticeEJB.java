/*
 * Created on 2003-8-7
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.settpaynotice.bizlogic;
import java.sql.Timestamp;
import java.util.Collection;

import javax.ejb.SessionBean;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.loan.discount.dataentity.DiscountBillInfo;
import com.iss.itreasury.settlement.settcontract.dao.SettContractDAO;
import com.iss.itreasury.settlement.settcontract.dataentity.SettContractInfo;
import com.iss.itreasury.settlement.settpaynotice.dao.SettDiscountBillDAO;
import com.iss.itreasury.settlement.settpaynotice.dao.SettDiscountCredenceDAO;
import com.iss.itreasury.settlement.settpaynotice.dao.SettPayNoticeDAO;
import com.iss.itreasury.settlement.settpaynotice.dataentity.SettDiscountBillInfo;
import com.iss.itreasury.settlement.settpaynotice.dataentity.SettDiscountCredenceInfo;
import com.iss.itreasury.settlement.settpaynotice.dataentity.SettPayNoticeInfo;
import com.iss.itreasury.settlement.settpaynotice.dataentity.SettPayNoticeQueryInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.Log;
import com.iss.system.dao.PageLoader;
/**
 * @author yiwang
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class SettPayNoticeEJB implements SessionBean
{
	private javax.ejb.SessionContext mySessionCtx = null;
	final static long serialVersionUID = 32123935678846163L;
	/**
	 * ejbActivate method comment
	 * @exception java.rmi.RemoteException 异常说明。
	 */
	public void ejbActivate() throws java.rmi.RemoteException
	{
	}
	/**
	 * ejbCreate method comment
	 * @exception javax.ejb.CreateException 异常说明。
	 * @exception java.rmi.RemoteException 异常说明。
	 */
	public void ejbCreate() throws javax.ejb.CreateException, java.rmi.RemoteException
	{
	}
	/**
	 * ejbPassivate method comment
	 * @exception java.rmi.RemoteException 异常说明。
	 */
	public void ejbPassivate() throws java.rmi.RemoteException
	{
	}
	/**
	 * ejbRemove method comment
	 * @exception java.rmi.RemoteException 异常说明。
	 */
	public void ejbRemove() throws java.rmi.RemoteException
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
	 * @exception java.rmi.RemoteException 异常说明。
	 */
	public void setSessionContext(javax.ejb.SessionContext ctx) throws java.rmi.RemoteException
	{
		mySessionCtx = ctx;
	}
	
	/**
	 * 查询放款通知单
	 * @param qInfo
	 * @return
	 * @throws java.rmi.RemoteException
	 */
	public Collection findPayNoticeByMultioption(SettPayNoticeQueryInfo qInfo) throws java.rmi.RemoteException
	{
		Collection c=null;
		
		try
		{
			SettPayNoticeDAO dao =new SettPayNoticeDAO();
			c=dao.findByMultiOption( qInfo );
		} catch (ITreasuryDAOException e)
		{
			e.printStackTrace();
			e.printStackTrace();throw new java.rmi.RemoteException();
		}
		return c;
	}
	/**
	 * 放款通知单查询分页
	 * @param SettPayNoticeQueryInfo info
	 * @return PageLoader
	 * @throws java.rmi.RemoteException
	 */
	public PageLoader getPayNoticeByMultioption(SettPayNoticeQueryInfo qInfo) throws java.rmi.RemoteException
	{
		PageLoader c=null;
		try
		{
			SettPayNoticeDAO dao =new SettPayNoticeDAO();
			c=dao.getPayNoticeByMultioption( qInfo );
		} catch (ITreasuryDAOException e)
		{
			e.printStackTrace();
			e.printStackTrace();
			throw new java.rmi.RemoteException();
		}
		return c;
	}
	
	/**
	 * 查询贴现通知单
	 * @param qInfo
	 * @return
	 * @throws java.rmi.RemoteException
	 */
	public Collection findDiscountCredenceByMultioption(SettPayNoticeQueryInfo qInfo) throws java.rmi.RemoteException
	{
		Collection c=null;
		
		try
		{
			SettDiscountCredenceDAO dao =new SettDiscountCredenceDAO();
			c=dao.findByMultiOption( qInfo );
		} catch (ITreasuryDAOException e)
		{
			e.printStackTrace();
			e.printStackTrace();throw new java.rmi.RemoteException();
		}
		return c;
	}
	
	/**
	 * 凭证查询分页
	 * @param SettPayNoticeQueryInfo info
	 * @return PageLoader
	 * @throws java.rmi.RemoteException
	 */
	public PageLoader getMultiOptinList(SettPayNoticeQueryInfo qInfo) throws java.rmi.RemoteException
	{
		PageLoader c=null;
		
		try
		{
			SettDiscountCredenceDAO dao =new SettDiscountCredenceDAO();
			c=dao.getMultiOptinList( qInfo );
		} catch (ITreasuryDAOException e)
		{
			e.printStackTrace();
			e.printStackTrace();
			throw new java.rmi.RemoteException();
		}
		return c;
	}
	
	/**
	 * 保存放款通知单信息
	 * @param info
	 * @return
	 * @throws java.rmi.RemoteException
	 */
	public long savePayNotice(SettPayNoticeInfo info) throws java.rmi.RemoteException
	{
		long lID = -1;
		try
		{
			SettPayNoticeDAO dao =new SettPayNoticeDAO();

			if (info.getId() <= 0)
			{
				
				
				info.setSourceTypeID( 2 );
				info.setCheckPerson( "" );
				info.setCode( dao.getPayNoticeCode(info.getContractID()));
				info.setStatusID( SETTConstant.SettPayNoticeStatus.CHECK );//ld修改状态为复核
				info.setInputDate( Env.getSystemDate() );
 
				lID = dao.add(info);
				Log.print("ID : " + lID);
			} 
			else
			{
				if (info.getStatusID()!=0 )
					info.setStatusID( SETTConstant.SettPayNoticeStatus.CHECK );//ld修改状态为复核
				dao.update(info);
				lID = info.getId();
			}
		} catch (Exception e)
		{
			e.printStackTrace();
			e.printStackTrace();throw new java.rmi.RemoteException();
		}
		return lID;

	}
	
	/**
	 * 保存贴现放款通知单信息
	 * @param info
	 * @return
	 * @throws java.rmi.RemoteException
	 */
	public long saveDiscountCredence(SettDiscountCredenceInfo info) throws java.rmi.RemoteException
	{
		long lID = -1;
		try
		{
			SettDiscountCredenceDAO dao =new SettDiscountCredenceDAO();

			if (info.getId() <= 0)
			{	
				//info.setNextCheckUserID(-1);
				info.setCode( dao.createCredenceCode(info.getContractID()));
				info.setStatusID( SETTConstant.SettPayNoticeStatus.CHECK );
				info.setInputDate( Env.getSystemDate() );
				dao.setUseMaxID();				
				lID = dao.add(info);
				
				SettContractDAO contractDao = new SettContractDAO("Loan_ContractForm");
				SettContractInfo contractInfo = new SettContractInfo();
				
				//更新合同的核定金额
				info = dao.findByID(lID);
				double purchaserInterest = info.getPurchaserInterest();	//利息
				contractInfo = contractDao.findByID(info.getContractID());
				contractInfo.setCheckAmount(contractInfo.getCheckAmount() - purchaserInterest);
				contractDao.update(contractInfo);
				
				Log.print("ID : " + lID);
			} 
			else
			{
				if (info.getStatusID()!=0 )
					info.setStatusID( SETTConstant.SettPayNoticeStatus.CHECK );				
				dao.update(info);
				lID = info.getId();
			}
		} catch (Exception e)
		{
			e.printStackTrace();
			e.printStackTrace();throw new java.rmi.RemoteException();
		}
		return lID;

	}
	
	/**
	 * 复合放款通知单
	 * @param lID
	 * @param userID
	 * @return
	 * @throws java.rmi.RemoteException
	 */
	public long checkPayNotice(long lID,long userID) throws java.rmi.RemoteException
	{
		try
		{
			SettPayNoticeDAO dao =new SettPayNoticeDAO();
			SettPayNoticeInfo info=new SettPayNoticeInfo();
			info.setId(lID);
			info.setNextCheckUserID( userID );
			info.setStatusID( SETTConstant.SettPayNoticeStatus.CHECK );
			
			dao.update(info);
			
		} catch (ITreasuryDAOException e)
		{
			e.printStackTrace();
			e.printStackTrace();throw new java.rmi.RemoteException();
		}
		return lID;
	}

	/**
	 * 复合贴现放款通知单
	 * @param lID
	 * @param userID
	 * @return
	 * @throws java.rmi.RemoteException
	 */
	public long checkDiscountCredence(long lID,long userID) throws java.rmi.RemoteException
	{
		try
		{
			SettDiscountCredenceDAO dao =new SettDiscountCredenceDAO();
			SettContractDAO contractDao = new SettContractDAO("Loan_ContractForm");
			SettDiscountCredenceInfo info=new SettDiscountCredenceInfo();
			SettContractInfo contractInfo = new SettContractInfo();
			info.setId(lID);
			info.setNextCheckUserID( userID );
			info.setStatusID( SETTConstant.SettPayNoticeStatus.CHECK );
			
			dao.update(info);
			
			//更新合同的核定金额
			info = dao.findByID(lID);
			double purchaserInterest = info.getPurchaserInterest();	//利息
			contractInfo = contractDao.findByID(info.getContractID());
			contractInfo.setCheckAmount(contractInfo.getCheckAmount() - purchaserInterest);
			contractDao.update(contractInfo);
			
		} catch (ITreasuryDAOException e)
		{
			e.printStackTrace();
			e.printStackTrace();throw new java.rmi.RemoteException();
		}
		return lID;

	}
	
	/**
	 * 根据ID查找放款通知单
	 * @param lID
	 * @return
	 * @throws java.rmi.RemoteException
	 */
	public SettPayNoticeInfo findPayNoticeByID(long lID) throws java.rmi.RemoteException
	{
		SettPayNoticeInfo info=new SettPayNoticeInfo();
		try
		{
			SettPayNoticeDAO dao =new SettPayNoticeDAO();			

			info=(SettPayNoticeInfo)dao.findByID( lID,SettPayNoticeInfo.class );
			
		} catch (ITreasuryDAOException e)
		{
			e.printStackTrace();
			e.printStackTrace();throw new java.rmi.RemoteException();
		}
		return info;

	}
	
	/**
	 * 根据ID查找贴现放款通知单
	 * @param lID
	 * @return
	 * @throws java.rmi.RemoteException
	 */
	public SettDiscountCredenceInfo findDiscountCredenceByID(long lID) throws java.rmi.RemoteException
	{
		SettDiscountCredenceInfo info=new SettDiscountCredenceInfo();
		SettDiscountCredenceDAO dao =new SettDiscountCredenceDAO();

		info=(SettDiscountCredenceInfo)dao.findByID( lID);
		return info;
	}
	
	/**
	 * 根据凭证ID查找贴现票据
	 * @param lID
	 * @return
	 * @throws java.rmi.RemoteException
	 */
	public SettDiscountBillInfo findDiscountBillByCredenceID(long lID) throws java.rmi.RemoteException
	{
		SettDiscountBillDAO dao =new SettDiscountBillDAO();

		return dao.findDiscountBillByCredenceID(lID);
	}
	
	/**
	 * 保存贴现票据
	 * @param info
	 * @return
	 * @throws java.rmi.RemoteException
	 */
	public long saveDiscountBill(SettDiscountBillInfo info) throws java.rmi.RemoteException
	{
	    long lReturn = -1;
	    try
		{
			SettDiscountBillDAO dao =new SettDiscountBillDAO();
			if (info.getId() > 0)
				dao.update(info);
			else
			{
				info.setStatusID(Constant.RecordStatus.VALID);
				lReturn = dao.add( info );
			}
			
		} catch (ITreasuryDAOException e)
		{
			e.printStackTrace();
			e.printStackTrace();throw new java.rmi.RemoteException();
		}
		return lReturn;
	}
	
	/**
	 * 保存贴现票据
	 * @param info
	 * @return
	 * @throws java.rmi.RemoteException
	 */
	public long saveDiscountList(DiscountBillInfo info,
			String tsCreate,
			String tsEnd,
			long lCurrencyID,
			long lOfficeID) throws java.rmi.RemoteException
	{
	    long lReturn = -1;
	    try
		{
			SettDiscountBillDAO dao =new SettDiscountBillDAO();
			lReturn=dao.addBillList(info,tsCreate, tsEnd,lCurrencyID,lOfficeID);
		} catch (Exception e)
		{
			e.printStackTrace();
			e.printStackTrace();
			throw new java.rmi.RemoteException();
		}
		return lReturn;
	}
	
	/**
	 * 保存贴现票据
	 * @param info
	 * @return
	 * @throws java.rmi.RemoteException
	 */
	public long saveDiscountList1(long contractId, long num, String strUser, String strBank, long isLocal, String tsCreate, String tsEnd, String strCode, double amount, long addDay, long acceptPOTypeID, String strFormerOwner, long currencyID, long officeID) throws java.rmi.RemoteException
	{
	    long lReturn = -1;
	    try
		{
			SettDiscountBillDAO dao =new SettDiscountBillDAO();
			lReturn=dao.addBillList1(contractId,num, strUser, strBank, isLocal,tsCreate, tsEnd, strCode, amount, addDay, acceptPOTypeID, strFormerOwner,currencyID,officeID);
		} catch (Exception e)
		{
			e.printStackTrace();
			e.printStackTrace();
			throw new java.rmi.RemoteException();
		}
		return lReturn;
	}
	
}
