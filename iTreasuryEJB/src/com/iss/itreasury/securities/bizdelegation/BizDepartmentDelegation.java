package com.iss.itreasury.securities.bizdelegation;

import java.util.Collection;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.securities.setting.dao.SEC_AccountDAO;
import com.iss.itreasury.securities.exception.SecuritiesDAOException;
import com.iss.itreasury.securities.exception.SecuritiesException;
import com.iss.itreasury.securities.setting.dao.SEC_CounterPartDAO;
import com.iss.itreasury.securities.setting.dataentity.AccountInfo;
import com.iss.itreasury.securities.setting.dataentity.CounterPartInfo;

/**
 * @author yuxu
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class BizDepartmentDelegation {
	private SEC_CounterPartDAO counterPartFacade = null;
	private SEC_AccountDAO accountFacade = null;
	
	public BizDepartmentDelegation() {
		counterPartFacade = new SEC_CounterPartDAO();
		accountFacade = new SEC_AccountDAO();
	}

	/**
	 *增加操作
	*/
	public void save(CounterPartInfo counterPartInfo) throws SecuritiesException
	{

		try {
			String name = counterPartInfo.getName();
			String shortName = counterPartInfo.getShortName();
			
			Collection cAdd = counterPartFacade.findByLinkSearch(" and isbankofdeposit = 1 and name = '"+name+"' or shortname = '"+shortName+"'");
			Collection cUpdate = counterPartFacade.findByLinkSearch(" and isbankofdeposit = 1 and ((name = '"+name+"' or shortname = '"+shortName+"') and id <>"+counterPartInfo.getId()+")");

			//增加记录
			if(counterPartInfo.getId()<0  && cAdd.isEmpty()){
				counterPartFacade.setUseMaxID();
			
				long id = counterPartFacade.add(counterPartInfo);

			//更新记录
			}else if(counterPartInfo.getId() > 0   && cUpdate.isEmpty()){
				counterPartFacade.update(counterPartInfo);
			}else if((counterPartInfo.getId() < 0 && !cAdd.isEmpty()) || (counterPartInfo.getId() > 0 && !cUpdate.isEmpty())){
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
			AccountInfo accountInfo = new AccountInfo();
			accountInfo.setCounterpartId(id);
			
			Collection c = accountFacade.findByCondition(accountInfo);
			
			if(id>0 && c.isEmpty()){
				counterPartFacade.delete(id);
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
	public CounterPartInfo findById(long id) throws SecuritiesException
	{
		CounterPartInfo info = new CounterPartInfo();
		
		try {
			info = (CounterPartInfo)counterPartFacade.findByID(id, info.getClass());
			
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
		return counterPartFacade.findByLinkSearch(strLinkSearch);
	} 
	public String getMaxCode() throws SecuritiesException, ITreasuryDAOException{
		return counterPartFacade.getMaxCode();
	
	}
	public static void main(String[] args) {
		CounterPartInfo info = new CounterPartInfo();
		//info.setId(3);
		     
		info.setCode("aa");
		info.setName("ddddd");
		     
		BizDepartmentDelegation delegation = new BizDepartmentDelegation();
		try {
		   delegation.delete(9);
	   } catch (SecuritiesException e) {
	   }
	}

}
