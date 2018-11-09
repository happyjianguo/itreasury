package com.iss.itreasury.securities.bizdelegation;

import java.util.Collection;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.securities.apply.dao.SEC_ApplyDAO;
import com.iss.itreasury.securities.apply.dataentity.ApplyInfo;
import com.iss.itreasury.securities.exception.SecuritiesDAOException;
import com.iss.itreasury.securities.exception.SecuritiesException;
import com.iss.itreasury.securities.setting.dao.SEC_AccountDAO;
import com.iss.itreasury.securities.setting.dataentity.AccountInfo;

/**
 * @author yuxu
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class AccountDelegation {
	private SEC_AccountDAO accountFacade = null;
	private SEC_ApplyDAO applyFacade = null;
	
	public AccountDelegation() {
		accountFacade = new SEC_AccountDAO();
		applyFacade = new SEC_ApplyDAO();
	}

	/**
	 *增加操作
	*/
	public void save(AccountInfo accountInfo) throws SecuritiesException
	{

		try {
			String accountCode = accountInfo.getAccountCode();
			Collection cAdd 	= accountFacade.findByLinkSearch(" and accountCode = '"+accountCode+"'");
			Collection cUpdate	= accountFacade.findByLinkSearch(" and (accountCode = '"+accountCode+"' and id <>"+accountInfo.getId()+")");
			
			//增加记录
			if(accountInfo.getId()<0 && cAdd.isEmpty()){
				accountFacade.setUseMaxID();
			
				long id = accountFacade.add(accountInfo);
				
			//更新记录
			}else if(accountInfo.getId() > 0  && cUpdate.isEmpty()){
				accountFacade.update(accountInfo);
			}else if((accountInfo.getId() < 0 && !cAdd.isEmpty()) || (accountInfo.getId() > 0 && !cUpdate.isEmpty())){
				throw new SecuritiesException();
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
	
			ApplyInfo applyInfo = new ApplyInfo();
			applyInfo.setAccountId(id);
			
			Collection c = applyFacade.findByCondition(applyInfo);
			
			if(id>0 && c.isEmpty()){
				accountFacade.delete(id);
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
	public AccountInfo findById(long id) throws SecuritiesException
	{
		AccountInfo info = new AccountInfo();
		
		try {
			info = (AccountInfo)accountFacade.findByID(id, info.getClass());
			
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
		return accountFacade.findByLinkSearch(strLinkSearch);
	} 

	public static void main(String[] args) {
		     
		AccountDelegation delegation = new AccountDelegation();
		
		AccountInfo info = new AccountInfo();
		
		info.setId(-1);
		info.setAccountCode("55555");
		
		try {
			
			delegation.save(info);
	   } catch (SecuritiesException e) {
	   }
	}
}
