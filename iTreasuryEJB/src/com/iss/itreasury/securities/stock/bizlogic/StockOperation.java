/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2004-3-8
 */
package com.iss.itreasury.securities.stock.bizlogic;

import java.rmi.RemoteException;

import javax.ejb.CreateException;

import com.iss.itreasury.securities.exception.SecuritiesException;
import com.iss.itreasury.securities.stock.dataentity.SecuritiesStockReturn;
import com.iss.itreasury.securities.stock.dataentity.SecuritiesStockParam;
import com.iss.itreasury.securities.stock.dataentity.SingleAccountDailyStockParam;
import com.iss.itreasury.securities.stock.exception.DuplicatedStockException;
import com.iss.itreasury.securities.stock.exception.OutOfStockException;
import com.iss.itreasury.util.EJBHomeFactory;

/**
 * @author yehuang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class StockOperation {
	
	public final static int STOCK_OPERATION_ENTER = 0;
	public final static int STOCK_OPERATION_CANCELENTER = 1;
	public final static int STOCK_OPERATION_EXIT = 2;
	public final static int STOCK_OPERATION_CANCELEXIT = 3;
	public final static int STOCK_OPERATION_FREEZE = 4;
	public final static int STOCK_OPERATION_CANCELFREEZE = 5;
	
	private Stock stockFacade = null;	
	
	public StockOperation() throws RemoteException{
		try
		{
			StockHome home;
			try {
				home =
					(StockHome) EJBHomeFactory.getFactory().lookUpHome(
							StockHome.class);
			} catch (Exception e) {
				throw new RemoteException("EJBHomeFactory���Ӵ���",e);
			}
			stockFacade = (Stock) home.create();
		}

		catch (CreateException ce)
		{
			throw new RemoteException("����CreateException",ce);
		}								
	}
	
	/**
	 *  ��涳�᣺���ı��棬ֻ�������˿�涳������
	 * */
	public void freezeStock(SecuritiesStockParam stockParam) throws RemoteException,DuplicatedStockException,OutOfStockException,SecuritiesException{
		stockFacade.freezeStock(stockParam);
	}
	
	/**
	 * ���ı��棬ֻ�Ǽ����˿�涳������
	 * @param stockParam
	 * @return
	 * */	
	public void cancelFreezeStock(SecuritiesStockParam stockParam) throws RemoteException,DuplicatedStockException,SecuritiesException{
		stockFacade.cancelFreezeStock(stockParam);
	}	
	
	
	/**
	 * ���
	 * */
	public void enterStock(SecuritiesStockParam stockParam) throws RemoteException,SecuritiesException{
		stockFacade.enterStock(stockParam);
	}
	/**
	 * ����(���ɾ��)
	 * */	
	public void cancelEnterStock(SecuritiesStockParam stockParam) throws RemoteException,SecuritiesException{
		stockFacade.deleteEnterStock(stockParam);
	}	
	

	/**
	 * ��⽻��
	 * */	
	public void deliverStock(SecuritiesStockParam stockParam) throws RemoteException,SecuritiesException{
		stockFacade.deliverStock(stockParam);
	}
	/**
	 * ���ȡ������
	 * */		
	public void cancelDeliverStock(SecuritiesStockParam stockParam) throws RemoteException,SecuritiesException{
		stockFacade.cancelDeliverStock(stockParam);
	}
	/**
	 * ����
	 * */	
	public SecuritiesStockReturn exitStock(SecuritiesStockParam stockParam) throws RemoteException,SecuritiesException{
		return stockFacade.exitStock(stockParam);
	}
	
	/**
	 * �˿�(����ɾ��)
	 * */		
	public void cancelExitStock(SecuritiesStockParam stockParam) throws RemoteException,SecuritiesException{
		stockFacade.cancelExitStock(stockParam);
	}
	/**
	 * ���⽻��
	 * */		
	public SecuritiesStockReturn deliverExitStock(SecuritiesStockParam stockParam) throws RemoteException,SecuritiesException{
		return stockFacade.deliverExitStock(stockParam);
	}
	/**
	 * ����ȡ������
	 * */			
	public void cancelDeliverExitStock(SecuritiesStockParam stockParam) throws RemoteException,SecuritiesException{
		stockFacade.cancelDeliverExitStock(stockParam);
	}

	/**
	 * 	��������ս�
	 * ���һ��ҵ��λ��һ���ʽ��ʻ��µ�һ��֤ȯ���룬�����յ�ֹ�գ����п���ս�
	 * */
	public void caculateSingleAccountDailyStock(SingleAccountDailyStockParam param)throws RemoteException,SecuritiesException{
		stockFacade.caculateSingleAccountDailyStock(param);
	}	
}
