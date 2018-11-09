/*
 * Created on 2003-8-7
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.securities.stock.bizlogic;
import java.rmi.RemoteException;

import com.iss.itreasury.securities.exception.SecuritiesException;
import com.iss.itreasury.securities.stock.dataentity.SecuritiesStockReturn;
import com.iss.itreasury.securities.stock.dataentity.SecuritiesStockParam;
import com.iss.itreasury.securities.stock.dataentity.SingleAccountDailyStockParam;
import com.iss.itreasury.securities.stock.exception.DuplicatedStockException;
import com.iss.itreasury.securities.stock.exception.OutOfStockException;
/**
 * @author yiwang
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public interface Stock extends javax.ejb.EJBObject
{
	/**
	 * 入库
	 * */
	public void enterStock(SecuritiesStockParam stockParam) throws RemoteException,SecuritiesException;
	/**
	 * 销库(入库删除)
	 * */	
	public void deleteEnterStock(SecuritiesStockParam stockParam) throws RemoteException,SecuritiesException;
	
	/**
	 * 入库交割
	 * */	
	public void deliverStock(SecuritiesStockParam stockParam) throws RemoteException,SecuritiesException;
	/**
	 * 入库取消交割
	 * */		
	public void cancelDeliverStock(SecuritiesStockParam stockParam) throws RemoteException,SecuritiesException;
	/**
	 * 出库
	 * */	
	public SecuritiesStockReturn exitStock(SecuritiesStockParam stockParam) throws RemoteException,SecuritiesException;
	
	/**
	 * 退库(出库删除)
	 * */		
	public void cancelExitStock(SecuritiesStockParam stockParam) throws RemoteException,SecuritiesException;
	/**
	 * 出库交割
	 * */		
	public SecuritiesStockReturn deliverExitStock(SecuritiesStockParam stockParam) throws RemoteException,SecuritiesException;
	/**
	 * 出库取消交割
	 * */			
	public void cancelDeliverExitStock(SecuritiesStockParam stockParam) throws RemoteException,SecuritiesException;
	
	/**
	 * 库存冻结：不改变库存，只是增加了库存冻结数量
	 * @param stockParam
	 * @return
	 * */
	public void freezeStock(SecuritiesStockParam stockParam) throws RemoteException,DuplicatedStockException,OutOfStockException,SecuritiesException;
	
	/**
	 * 不改变库存，只是减少了库存冻结数量
	 * @param stockParam
	 * @return
	 * */	
	public void cancelFreezeStock(SecuritiesStockParam stockParam) throws RemoteException,DuplicatedStockException,SecuritiesException;

	/**
	 * 	单户库存日结
	 * 针对一个业务单位下一个资金帐户下的一个证券代码，从起日到止日，进行库存日结
	 * */
	public void caculateSingleAccountDailyStock(SingleAccountDailyStockParam param)throws RemoteException,SecuritiesException;
}
