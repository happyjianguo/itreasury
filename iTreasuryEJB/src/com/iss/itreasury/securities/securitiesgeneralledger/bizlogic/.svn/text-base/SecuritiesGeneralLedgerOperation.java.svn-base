/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2004-3-10
 */
package com.iss.itreasury.securities.securitiesgeneralledger.bizlogic;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.Timestamp;

import javax.ejb.CreateException;

import com.iss.itreasury.securities.exception.SecuritiesException;
import com.iss.itreasury.securities.securitiesgeneralledger.dataentity.GenerateGLEntryParam;
import com.iss.itreasury.util.EJBHomeFactory;

/**
 * @author yehuang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class SecuritiesGeneralLedgerOperation {
	
	private SecuritiesGeneralLedger securitiesGeneralLedgerFacade = null;
	/**
	 * 用于维护事务完整性的数据库连接
	 * */
	private Connection transConn = null;
	
	public SecuritiesGeneralLedgerOperation() throws RemoteException{
	try
	{
			SecuritiesGeneralLedgerHome home;
			try {
				home =
					(SecuritiesGeneralLedgerHome) EJBHomeFactory.getFactory().lookUpHome(
							SecuritiesGeneralLedgerHome.class);
			} catch (Exception e) {
				throw new RemoteException("EJBHomeFactory连接错误",e);
			}
			securitiesGeneralLedgerFacade = (SecuritiesGeneralLedger) home.create();
		}
	
		catch (CreateException ce)
		{
			throw new RemoteException("发生CreateException",ce);
		}
	}
	
	public SecuritiesGeneralLedgerOperation(Connection conn)throws RemoteException{
		if(conn == null){
			try
			{
					SecuritiesGeneralLedgerHome home;
					try {
						home =
							(SecuritiesGeneralLedgerHome) EJBHomeFactory.getFactory().lookUpHome(
									SecuritiesGeneralLedgerHome.class);
					} catch (Exception e) {
						throw new RemoteException("EJBHomeFactory连接错误",e);
					}
					securitiesGeneralLedgerFacade = (SecuritiesGeneralLedger) home.create();
				}
			
				catch (CreateException ce)
				{
					throw new RemoteException("发生CreateException",ce);
				}		
		}else
			transConn = conn;
	}
	
	
	/**
	 * 交易产生会计分录
	 * @param GenerateGLEntryParam
	 * */
	public void generateGLEntry(GenerateGLEntryParam param) throws SecuritiesException,RemoteException{
		if(transConn != null){
			SecuritiesGeneralLedgerBean securitiesGeneralLedgerBean = new SecuritiesGeneralLedgerBean(transConn);
			securitiesGeneralLedgerBean.generateGLEntry(param);			
		}else			
			securitiesGeneralLedgerFacade.generateGLEntry(param);
	}
	/**
	 * 交易删除会计分录
	 * @param 交割单ID
	 * */
	public void deleteGLEntry(String deliverOrderCode) throws SecuritiesException,RemoteException{
		if(transConn != null){
			SecuritiesGeneralLedgerBean securitiesGeneralLedgerBean = new SecuritiesGeneralLedgerBean(transConn);
			securitiesGeneralLedgerBean.deleteGLEntry(deliverOrderCode);			
		}else		
			securitiesGeneralLedgerFacade.deleteGLEntry(deliverOrderCode);
	}
	
	/**
	 * 根据合同号以及起息日删除删除会计分录
	 * @param 
	 * */
	/*public void deleteGLEntry(String contractCode,Timestamp interestStartDate) throws SecuritiesException,RemoteException{
		if(transConn != null){
			SecuritiesGeneralLedgerBean securitiesGeneralLedgerBean = new SecuritiesGeneralLedgerBean(transConn);
			securitiesGeneralLedgerBean.deleteGLEntry(contractCode,interestStartDate);			
		}else		
			securitiesGeneralLedgerFacade.deleteGLEntry(contractCode,interestStartDate);
	}	
	*/
	/**
	 * “结转成本”产生的所有会计分录中，对机构号ID、币种号、科目代码均相同的分录，
	 * 应该汇总其金额、使之合并成一笔分录
	 * */
	public void combineGLEntryForCarryCost(long officeID,long currencyID,String subjectCode) throws SecuritiesException,RemoteException{
		securitiesGeneralLedgerFacade.combineGLEntryForCarryCost(officeID,currencyID,subjectCode);
	}	
}
