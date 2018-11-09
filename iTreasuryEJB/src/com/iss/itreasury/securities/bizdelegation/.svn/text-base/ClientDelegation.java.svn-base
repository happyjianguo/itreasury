package com.iss.itreasury.securities.bizdelegation;

import java.util.Collection;
import java.util.ArrayList;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.securities.apply.dao.SEC_ApplyDAO;
import com.iss.itreasury.securities.apply.dataentity.ApplyInfo;
import com.iss.itreasury.securities.exception.SecuritiesDAOException;
import com.iss.itreasury.securities.exception.SecuritiesException;
import com.iss.itreasury.securities.setting.dao.*;
import com.iss.itreasury.securities.setting.dataentity.*;
import com.iss.itreasury.securities.util.*;

/**
 * @author yuxu
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class ClientDelegation {
	
	private SEC_ClientDAO clientFacade = null;
	private SEC_ApplyDAO applyFacade = null;
	private SEC_AccountDAO accountFacade = null;
	private SEC_StockHolderAccountDAO stockHolderAccountFacade = null;
	
	
	public ClientDelegation() {
		clientFacade				= new SEC_ClientDAO();
		applyFacade					= new SEC_ApplyDAO();
		accountFacade				= new SEC_AccountDAO();
		stockHolderAccountFacade	= new SEC_StockHolderAccountDAO();
		
	}


	/**
	 *增加操作
	*/
	public void save(ClientInfo clientInfo) throws SecuritiesException
	{

		try {
			//增加记录
			if(clientInfo.getId()<0){
				clientFacade.setUseMaxID();
				
				ArrayList arr=new ArrayList();
				arr.add("code");
				arr.add("statusId");
				//效验编号和状态
				ClientInfo validateclientInfo = new ClientInfo();
				validateclientInfo.setCode(clientInfo.getCode());
				validateclientInfo.setStatusId(SECConstant.SettingStatus.CHECKED);
				
				if(clientFacade.checkDuplicate(validateclientInfo,arr)){
					throw new SecuritiesException("Sec_E006",clientInfo.getCode(),new Exception());
				}
				
				//效验名称是否重复
				ArrayList arrname=new ArrayList();
				arrname.add("name");
				arrname.add("statusId");
				if(clientFacade.checkDuplicate(clientInfo,arrname)){
					throw new SecuritiesException("Sec_E006",clientInfo.getName(),new Exception());
				}
				
				long id = clientFacade.add(clientInfo);
				
				ClientInfo info = this.findById(id);
				
				if(info.getCode().equals("")){
					info.setCode(Long.toString(id));
					clientFacade.update(info);
				}		
			//更新记录
			}else {
				
				ArrayList arr=new ArrayList();
				arr.add("code");
				arr.add("statusId");
				//效验编号和状态
				ClientInfo validateclientInfo = new ClientInfo();
				validateclientInfo.setCode(clientInfo.getCode());
				validateclientInfo.setStatusId(SECConstant.SettingStatus.CHECKED);
				
				if(clientFacade.checkDuplicate(validateclientInfo,arr)){
					throw new SecuritiesException("Sec_E006",clientInfo.getCode(),new Exception());
				}
				
				//效验名称是否重复
				ArrayList arrname=new ArrayList();
				arrname.add("name");
				arrname.add("statusId");
				if(clientFacade.checkDuplicate(clientInfo,arrname)){
					throw new SecuritiesException("Sec_E006",clientInfo.getName(),new Exception());
				}
				
				clientFacade.update(clientInfo);
			}
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
			throw new SecuritiesDAOException(e);
		}
	}
	/**
	 *删除操作
	*/
	public void delete(long id) throws SecuritiesException
	{

		try {
			ApplyInfo applyInfo = new ApplyInfo();
			AccountInfo accountInfo = new AccountInfo();
			StockHolderAccountInfo stockHolderAccountInfo = new StockHolderAccountInfo();
			
			applyInfo.setClientId(id);
			
			accountInfo.setClientId(id);
			accountInfo.setStatusId(SECConstant.AccountStatus.CHECKED);
			
			stockHolderAccountInfo.setClientId(id);
			stockHolderAccountInfo.setStatusId(SECConstant.AccountStatus.CHECKED);
			
			Collection cApply				= applyFacade.findByCondition(applyInfo);
			Collection cAccount				= accountFacade.findByCondition(accountInfo);
			Collection cStockHolderAccount	= stockHolderAccountFacade.findByCondition(stockHolderAccountInfo);
			
			
			
			if(id>0 && cApply.isEmpty() && cAccount.isEmpty() && cStockHolderAccount.isEmpty()){
				clientFacade.delete(id);
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
	public ClientInfo findById(long clientId) throws SecuritiesException
	{
		ClientInfo info = new ClientInfo();
		
		try {
			info = (ClientInfo)clientFacade.findByID(clientId, info.getClass());
			
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}
		return info;
		
	}
	public Collection findByAll() throws SecuritiesException{
		return clientFacade.findByAll();
	}
	/**
	 * 条件查找
	 * @return Collection
	 */
	public Collection findByLinkSearch(String strLinkSearch) throws SecuritiesException{
		return clientFacade.findByLinkSearch(strLinkSearch);
	} 
	public String getMaxCode() throws SecuritiesException, ITreasuryDAOException{
		return clientFacade.getMaxCode();
	
	}
	public static void main(String[] args) {
		     ClientInfo info = new ClientInfo();
		     info.setId(0);
		     info.setStatusId(-1);
		     
		info.setCode("aa");
		     //info.setName("ddddd");
		     
		     ClientDelegation delegation = new ClientDelegation();
		     try {
				delegation.save(info);
			} catch (SecuritiesException e) {
				e.printStackTrace();
			}

           
	}
}
