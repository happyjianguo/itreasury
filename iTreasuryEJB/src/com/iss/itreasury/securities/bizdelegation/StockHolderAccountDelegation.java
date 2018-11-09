package com.iss.itreasury.securities.bizdelegation;

import java.util.ArrayList;
import java.util.Collection;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.securities.exception.SecuritiesDAOException;
import com.iss.itreasury.securities.exception.SecuritiesException;
import com.iss.itreasury.securities.setting.dao.SEC_AccountDAO;
import com.iss.itreasury.securities.setting.dao.SEC_StockHolderAccountDAO;
import com.iss.itreasury.securities.setting.dataentity.AccountInfo;
import com.iss.itreasury.securities.setting.dataentity.StockHolderAccountInfo;

/**
 * @author yuxu
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class StockHolderAccountDelegation {
	private SEC_StockHolderAccountDAO stockHolderAccountFacade = null;
	private SEC_AccountDAO accountFacade = null;
	
	public StockHolderAccountDelegation() {
		stockHolderAccountFacade = new SEC_StockHolderAccountDAO();
		accountFacade = new SEC_AccountDAO();
	}

	/**
	 * 检查股东账户代码是否重复 
	 */
	public boolean checkDuplicate(StockHolderAccountInfo info){
		boolean lReturn = false;
		try{
			ArrayList list = new ArrayList();
			list.add("code");
			lReturn = stockHolderAccountFacade.checkDuplicate(info, list);
		}catch (SecuritiesException e) {
			e.printStackTrace();
		}
		return lReturn;
	}
	
	/**
	 *增加操作
	*/
	public void save(StockHolderAccountInfo stockHolderAccountInfo) throws SecuritiesException
	{

		try {
			//增加记录
			if(stockHolderAccountInfo.getId()<0){
				stockHolderAccountFacade.setUseMaxID();
			
				long id = stockHolderAccountFacade.add(stockHolderAccountInfo);

			//更新记录
			}else {
				stockHolderAccountFacade.update(stockHolderAccountInfo);
			}
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}
	}
	/**
	 *删除操作
	*/
	public void delete(long id) throws SecuritiesException
	{

		try {
			AccountInfo accountInfo = new AccountInfo();
			accountInfo.setStockHolderAccountId1(id);
			
			Collection c = accountFacade.findByCondition(accountInfo);
			
			if(id>0 && c.isEmpty()){
				stockHolderAccountFacade.delete(id);
			}else {
				throw new SecuritiesException();
			}
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}
	}
	
	/**
	 *单笔查询操作
	*/
	public StockHolderAccountInfo findById(long id) throws SecuritiesException
	{
		StockHolderAccountInfo info = new StockHolderAccountInfo();
		
		try {
			info = (StockHolderAccountInfo)stockHolderAccountFacade.findByID(id, info.getClass());
			
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}
		return info;
		
	}
	/**
	 * 条件查找
	 * @return Collection
	 */
	public Collection findByLinkSearch(String strLinkSearch) throws SecuritiesException{
		return stockHolderAccountFacade.findByLinkSearch(strLinkSearch);
	} 
	public static void main(String[] args) {
		StockHolderAccountInfo info = new StockHolderAccountInfo();
		//info.setId(3);
		     
   		info.setCode("aa");
		info.setName("ddddd");
		     
		StockHolderAccountDelegation delegation = new StockHolderAccountDelegation();
		try {
		   delegation.save(info);
	   } catch (SecuritiesException e) {
	   }
	}
}
