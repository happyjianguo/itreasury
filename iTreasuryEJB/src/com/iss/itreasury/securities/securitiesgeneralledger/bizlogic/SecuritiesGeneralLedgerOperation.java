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
	 * ����ά�����������Ե����ݿ�����
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
				throw new RemoteException("EJBHomeFactory���Ӵ���",e);
			}
			securitiesGeneralLedgerFacade = (SecuritiesGeneralLedger) home.create();
		}
	
		catch (CreateException ce)
		{
			throw new RemoteException("����CreateException",ce);
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
						throw new RemoteException("EJBHomeFactory���Ӵ���",e);
					}
					securitiesGeneralLedgerFacade = (SecuritiesGeneralLedger) home.create();
				}
			
				catch (CreateException ce)
				{
					throw new RemoteException("����CreateException",ce);
				}		
		}else
			transConn = conn;
	}
	
	
	/**
	 * ���ײ�����Ʒ�¼
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
	 * ����ɾ����Ʒ�¼
	 * @param ���ID
	 * */
	public void deleteGLEntry(String deliverOrderCode) throws SecuritiesException,RemoteException{
		if(transConn != null){
			SecuritiesGeneralLedgerBean securitiesGeneralLedgerBean = new SecuritiesGeneralLedgerBean(transConn);
			securitiesGeneralLedgerBean.deleteGLEntry(deliverOrderCode);			
		}else		
			securitiesGeneralLedgerFacade.deleteGLEntry(deliverOrderCode);
	}
	
	/**
	 * ���ݺ�ͬ���Լ���Ϣ��ɾ��ɾ����Ʒ�¼
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
	 * ����ת�ɱ������������л�Ʒ�¼�У��Ի�����ID�����ֺš���Ŀ�������ͬ�ķ�¼��
	 * Ӧ�û������ʹ֮�ϲ���һ�ʷ�¼
	 * */
	public void combineGLEntryForCarryCost(long officeID,long currencyID,String subjectCode) throws SecuritiesException,RemoteException{
		securitiesGeneralLedgerFacade.combineGLEntryForCarryCost(officeID,currencyID,subjectCode);
	}	
}
