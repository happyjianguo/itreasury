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
				throw new RemoteException("EJBHomeFactory连接错误",e);
			}
			stockFacade = (Stock) home.create();
		}

		catch (CreateException ce)
		{
			throw new RemoteException("发生CreateException",ce);
		}								
	}
	
	/**
	 *  库存冻结：不改变库存，只是增加了库存冻结数量
	 * */
	public void freezeStock(SecuritiesStockParam stockParam) throws RemoteException,DuplicatedStockException,OutOfStockException,SecuritiesException{
		stockFacade.freezeStock(stockParam);
	}
	
	/**
	 * 不改变库存，只是减少了库存冻结数量
	 * @param stockParam
	 * @return
	 * */	
	public void cancelFreezeStock(SecuritiesStockParam stockParam) throws RemoteException,DuplicatedStockException,SecuritiesException{
		stockFacade.cancelFreezeStock(stockParam);
	}	
	
	
	/**
	 * 入库
	 * */
	public void enterStock(SecuritiesStockParam stockParam) throws RemoteException,SecuritiesException{
		stockFacade.enterStock(stockParam);
	}
	/**
	 * 销库(入库删除)
	 * */	
	public void cancelEnterStock(SecuritiesStockParam stockParam) throws RemoteException,SecuritiesException{
		stockFacade.deleteEnterStock(stockParam);
	}	
	

	/**
	 * 入库交割
	 * */	
	public void deliverStock(SecuritiesStockParam stockParam) throws RemoteException,SecuritiesException{
		stockFacade.deliverStock(stockParam);
	}
	/**
	 * 入库取消交割
	 * */		
	public void cancelDeliverStock(SecuritiesStockParam stockParam) throws RemoteException,SecuritiesException{
		stockFacade.cancelDeliverStock(stockParam);
	}
	/**
	 * 出库
	 * */	
	public SecuritiesStockReturn exitStock(SecuritiesStockParam stockParam) throws RemoteException,SecuritiesException{
		return stockFacade.exitStock(stockParam);
	}
	
	/**
	 * 退库(出库删除)
	 * */		
	public void cancelExitStock(SecuritiesStockParam stockParam) throws RemoteException,SecuritiesException{
		stockFacade.cancelExitStock(stockParam);
	}
	/**
	 * 出库交割
	 * */		
	public SecuritiesStockReturn deliverExitStock(SecuritiesStockParam stockParam) throws RemoteException,SecuritiesException{
		return stockFacade.deliverExitStock(stockParam);
	}
	/**
	 * 出库取消交割
	 * */			
	public void cancelDeliverExitStock(SecuritiesStockParam stockParam) throws RemoteException,SecuritiesException{
		stockFacade.cancelDeliverExitStock(stockParam);
	}

	/**
	 * 	单户库存日结
	 * 针对一个业务单位下一个资金帐户下的一个证券代码，从起日到止日，进行库存日结
	 * */
	public void caculateSingleAccountDailyStock(SingleAccountDailyStockParam param)throws RemoteException,SecuritiesException{
		stockFacade.caculateSingleAccountDailyStock(param);
	}	
}
