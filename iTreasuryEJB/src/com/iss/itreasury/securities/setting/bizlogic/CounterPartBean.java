/*
 * Created on 2004-3-15
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.securities.setting.bizlogic;

import java.util.Collection;

import com.iss.itreasury.util.*;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.securities.exception.SecuritiesDAOException;
import com.iss.itreasury.securities.exception.SecuritiesException;
import com.iss.itreasury.securities.setting.dao.SEC_CounterPartBankAccountDAO;
import com.iss.itreasury.securities.setting.dao.SEC_CounterPartDAO;
import com.iss.itreasury.securities.setting.dataentity.CounterPartBankAccountInfo;
import com.iss.itreasury.securities.setting.dataentity.CounterPartInfo;
import com.iss.itreasury.securities.util.NameRef;
import com.iss.itreasury.securities.util.SECConstant;
import com.iss.itreasury.util.Log4j;

/**
 * @author hjliu
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class CounterPartBean
{
	protected Log4j log4j = new Log4j(Constant.ModuleType.SECURITIES, this);

	/**
	 * 新增或者修改交易对手资料
	 * @param counterPartInfo 
	 * @throws SecuritiesException
	 */
	public void saveCounterPart(CounterPartInfo counterPartInfo) throws SecuritiesDAOException
	{
		SEC_CounterPartDAO counterPartDAO = new SEC_CounterPartDAO();
		try
		{

			long counterPartID = -1; //交易对手的ID			
			if (counterPartInfo.getId() < 0)
			{
				log4j.debug(" debug info :: 开始增加交易对手！！！");
				//--------------生成新的交易对手编号----start---
				String counterPartCode = counterPartDAO.getCounterPartCode();
				log4j.debug("-- debug info :: 返回交易对手的编号：" + counterPartCode);
				counterPartInfo.setCode(counterPartCode);
				//-------------生成新的交易对手编号-------end---
				counterPartInfo.setStatusId(SECConstant.CounterpartStatus.CHECKED);
				counterPartDAO.setUseMaxID(); //赋ID
				//新增一条交易对手！！！！
				counterPartID = counterPartDAO.add(counterPartInfo);
				//清空缓存
				NameRef.refreshCounterpartInformation();

				log4j.debug("-- debug info :: 增加交易对手的新的ID:" + counterPartID);

				log4j.debug(" debug info :: 完成增加交易对手！！！");

			}
			else
			{
				log4j.debug(" debug info :: 开始修改交易对手！！！");
				log4j.debug("111111111111111");
				//如果不是投保人类型，将相应的字段清空！
				if (counterPartInfo.getIsPolicyHolder() != 1)
				{
					log4j.debug("2222222222222222222222");
					counterPartInfo.setCommisionRate(0.0);
					counterPartInfo.setIndustryType(-1);
				}
				//如果不是被投资企业，将相应的字段清空！
				if (counterPartInfo.getIsInvestedCorporation() != 1)
				{
					log4j.debug("333333333333333");
					counterPartInfo.setStockHoldQuantity(0);
					counterPartInfo.setStockHoldRate(0.0);
					counterPartInfo.setIndustrySort(-1);
				}
				if (counterPartInfo.getIsFundManagementCo() != 1 && counterPartInfo.getIsInterBankCounterpart() != 1)
				{
					log4j.debug("44444444444444444");
					counterPartInfo.setFinanceLicenseCode("");
					counterPartInfo.setSecuritiesTrusteeCode("");

					counterPartInfo.setBusinessLicenseCode("");
					counterPartInfo.setLegalPerson("");
					counterPartInfo.setContacter("");
					counterPartInfo.setAddress("");
					counterPartInfo.setPhone("");
					counterPartInfo.setFax("");
					counterPartInfo.setZipCode("");
					counterPartInfo.setEmail("");
				}
				else
				{
					if (counterPartInfo.getIsInterBankCounterpart() != 1)
					{
						log4j.debug("555555555555555");
						counterPartInfo.setFinanceLicenseCode("");
						counterPartInfo.setSecuritiesTrusteeCode("");
					}
				}
				log4j.debug("6666666666666666");
				counterPartDAO.update(counterPartInfo); //更新交易对手资料
				NameRef.refreshCounterpartInformation();
				log4j.debug("lhj debug info :: 完成修改交易对手！！！");
			}

		}
		catch (ITreasuryDAOException e)
		{
			throw new SecuritiesDAOException(e);
		}

	}

	public void saveCounterPartBankAccount(CounterPartBankAccountInfo counterPartBankAccountInfo) throws SecuritiesDAOException
	{
		SEC_CounterPartBankAccountDAO counterPartBankAccountDAO = new SEC_CounterPartBankAccountDAO();
		long counterPartBankAccountID = -1; //交易对手开户行的ID

		try
		{
			if (counterPartBankAccountInfo.getId() < 0)
			{
				log4j.debug("lhj debug info :: 开始新增交易对手开户行");
				counterPartBankAccountInfo.setStatusId(SECConstant.CounterpartStatus.CHECKED);
				counterPartBankAccountDAO.setUseMaxID();
				counterPartBankAccountID = counterPartBankAccountDAO.add(counterPartBankAccountInfo);
				
				log4j.debug("--lhj debug info :: 新增交易对手开户行的ID:" + counterPartBankAccountID);
				log4j.debug("lhj debug info :: 完成增加交易对手开户行！！！");

			}
			else
			{
				log4j.debug("lhj debug info :: 开始修改交易对手开户行！！！");
				counterPartBankAccountDAO.update(counterPartBankAccountInfo);
				log4j.debug("lhj debug info :: 完成增加交易对手开户行！！！");

			}

		}
		catch (ITreasuryDAOException e)
		{
			throw new SecuritiesDAOException(e);
		}
	}

	/**
	 * 通过ID返回交易对手资料
	 * @param counterPartID
	 * @return
	 * @throws SecuritiesDAOException
	 */
	public CounterPartInfo findCounterPartById(long counterPartId) throws SecuritiesDAOException
	{
		log4j.debug("lhj debug info CounterPartBean :: findCounterPartByID start!");
		log4j.debug("--lhj dubug info 交易对手ID == " + counterPartId);
		CounterPartInfo counterPartInfo = new CounterPartInfo();
		SEC_CounterPartDAO counterPartDAO = new SEC_CounterPartDAO();
		try
		{
			log4j.debug("----lhj dubug info  1111111111");
			counterPartInfo = (CounterPartInfo) counterPartDAO.findByID(counterPartId, counterPartInfo.getClass());
		}
		catch (ITreasuryDAOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new SecuritiesDAOException(e);
		}

		log4j.debug("lhj debug info CounterPartBean :: findCounterPartByID end!");
		return counterPartInfo;

	}
	/**
	 * 通过ID返回交易对手开户行资料
	 * @param counterPartBankAccountID
	 * @return
	 * @throws SecuritiesDAOException
	 */
	public CounterPartBankAccountInfo findCounterPartBankAccountById(long counterPartBankAccountId) throws SecuritiesDAOException
	{
		log4j.debug("lhj debug info CounterPartBean :: findCounterPartBankAccountById start!");
		CounterPartBankAccountInfo counterPartBankAccountInfo = new CounterPartBankAccountInfo();
		SEC_CounterPartBankAccountDAO counterPartBankAccountDAO = new SEC_CounterPartBankAccountDAO();

		try
		{
			counterPartBankAccountInfo = (CounterPartBankAccountInfo) counterPartBankAccountDAO.findByID(counterPartBankAccountId, counterPartBankAccountInfo.getClass());
			log4j.debug("lhj debug info CounterPartBean :: findCounterPartBankAccountById end!");
		}
		catch (ITreasuryDAOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new SecuritiesDAOException(e);
		}

		return counterPartBankAccountInfo;

	}
	/**
	 * 删除交易对手（逻辑删除！）
	 * 如果在交割单等出现了该交易对手，则不可删除，此处throw new Exception ，页面给出提示！！！
	 * @param counterPartId
	 * @throws SecuritiesDAOException
	 */
	public void deleteCounterPart(long counterPartId) throws SecuritiesException
	{
		SEC_CounterPartDAO counterPartDAO = new SEC_CounterPartDAO();
		SEC_CounterPartBankAccountDAO counterPartBankAccountDAO = new SEC_CounterPartBankAccountDAO();

		try
		{
			log4j.debug("lhj debug info 开始删除 [" + counterPartId + " ].......... ");

			/*
			 * 1:交易对手前，先判断在交割单中，或者通知单中，或者申请书中是否已经用到了
			 * 该交易对手，如果出现上述三种中的任意一个，则不能删除该交易对手。页面给出提示！
			 * 
			 * 2:足删除条件下的删除是逻辑删除，即只将status改变！！
			 * 
			 * 3:删除交易对手前，先将交易对手开户行中交易对手是该交易对手的全部逻辑删除！
			 *
			 */
			log4j.debug("lhj debug info 判断是否已经用到过该交易对手！start");
			long lIsUsed = -1;
			lIsUsed = counterPartDAO.findIsUseCounterPart(counterPartId);
			log4j.debug("lhj debug info 返回值 lIsUsed " + lIsUsed);
			if (lIsUsed < 0)
			{
				log4j.debug("lhj debug info 该交易对手可以被删除！");
				log4j.debug("lhj debug info 判断是否已经用到过该交易对手！end");

				log4j.debug("lhj debug info 逻辑删除相应的交易对手开户行！start");
				//statusID 删除
				long statusID = SECConstant.CounterpartStatus.DELETED;
				counterPartBankAccountDAO.updateCounterPartBankStatus(counterPartId, statusID);
				NameRef.refreshCounterpartInformation();
				log4j.debug("lhj debug info 逻辑删除相应的交易对手开户行！end");

				log4j.debug("lhj debug info 逻辑删除交易对手！start");
				//删除交易对手资料
				counterPartDAO.delete(counterPartId);
				log4j.debug("lhj debug info 逻辑删除交易对手！end");

			}
			else
			{
				log4j.debug("lhj debug info 已经用到过该交易对手！");
				log4j.debug("lhj debug info 判断是否已经用到过该交易对手！end");
			    //throw new SecuritiesException("该交易对手已经使用，不能删除!", null);
				throw new SecuritiesException("Sec_E171", null);
			}

		}
		catch (SecuritiesException e1)
		{
			throw e1;
		}
		catch (ITreasuryDAOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new SecuritiesException("Gen_E001", e);
		}

	}

	/**
	 * 删除交易对手开户行
	 * 注意！逻辑删除！
	 * @param counterPartBankAccountID
	 * @throws SecuritiesDAOException
	 */
	public void deleteCounterPartBankAccount(long counterPartBankAccountID) throws SecuritiesDAOException
	{
		SEC_CounterPartBankAccountDAO counterPartBankAccountDAO = new SEC_CounterPartBankAccountDAO();
		try
		{
			counterPartBankAccountDAO.delete(counterPartBankAccountID);
		}
		catch (ITreasuryDAOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new SecuritiesDAOException(e);
		}
	}
		
	/**
	 * 链接查找
	 * @param counterPartId
	 * @return
	 * @throws SecuritiesException
	 */
	public Collection findByLinkSearch(long counterPartId) throws SecuritiesException
	{
		SEC_CounterPartDAO counterPartDAO = new SEC_CounterPartDAO();
		String strLinkSearch = "";
		if(counterPartId>0){
			//如果不能同时出现开户营业部 ：and  (isBankOfDeposit <>1 or isBankOfDeposit is null)
			strLinkSearch = " and  (isBankOfDeposit <>1 or isBankOfDeposit is null) and statusID = 3    and id = "+counterPartId;
		}else{
			strLinkSearch = " and  (isBankOfDeposit <>1 or isBankOfDeposit is null) and statusID = 3   ";
		}
		
	    Collection counterPartCollection = counterPartDAO.findByLinkSearch(strLinkSearch);
	    return  counterPartCollection;
	}
	/**
	 * 
	 * @return
	 * @throws SecuritiesException
	 */
	
	public Collection findByConditions(String conditions) throws SecuritiesException{
		SEC_CounterPartDAO counterPartDAO = new SEC_CounterPartDAO();		
		conditions+=" and statusid=3 ";
		Collection counterPartCollection = counterPartDAO.findByLinkSearch(conditions);
	    return  counterPartCollection;
	}
}
