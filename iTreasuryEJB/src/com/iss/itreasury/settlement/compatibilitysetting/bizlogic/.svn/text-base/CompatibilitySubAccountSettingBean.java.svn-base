/*
 * Created on 2004-8-12
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.settlement.compatibilitysetting.bizlogic;

import java.util.Collection;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.settlement.base.SettlementDAOException;
import com.iss.itreasury.settlement.base.SettlementException;
import com.iss.itreasury.settlement.compatibilitysetting.dao.Sett_CompatibilitySubAccountSettingDao;
import com.iss.itreasury.settlement.compatibilitysetting.dataentity.CompatibilitySubAccountSettingInfo;
import com.iss.itreasury.settlement.compatibilitysetting.dataentity.CompatibilitySubAccountSettingWhereInfo;
import com.iss.itreasury.settlement.transdiscount.dao.Sett_TransGrantDiscountDAO;
import com.iss.itreasury.settlement.transloan.dao.Sett_TransGrantLoanDAO;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.ITreasuryException;

/**
 * @author ruixie
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class CompatibilitySubAccountSettingBean {
	
	private Sett_CompatibilitySubAccountSettingDao subAccountDAO = new Sett_CompatibilitySubAccountSettingDao();
	
	/**
	 * 根据子账户ID得到子账户的所有信息
	 * @param lId
	 * @return
	 * @throws SettlementException
	 */
	public CompatibilitySubAccountSettingInfo findById(long lId) throws SettlementException
	{
		CompatibilitySubAccountSettingInfo info = new CompatibilitySubAccountSettingInfo();
		try
		{
			info = (CompatibilitySubAccountSettingInfo)subAccountDAO.findByID(lId,info.getClass());
		} catch (ITreasuryDAOException e)
		{
			throw new SettlementDAOException(e);
		}
		return info;
	}
	

	/**
	 * 保存子账户信息
	 * @param info
	 * @return
	 * @throws SettlementException
	 */
	public long save(CompatibilitySubAccountSettingInfo info) throws Exception
	{
		long lReturn = -1;
		try
		{
			System.out.println("into save subAccount");
			subAccountDAO.setUseMaxID();
			info.setStatusId(SETTConstant.SubAccountStatus.NEWSAVE);
			lReturn = subAccountDAO.add(info);
			System.out.println("====改变放款通知单状态为已使用====");
			Sett_TransGrantLoanDAO dao = new Sett_TransGrantLoanDAO();
			dao.updateLoanPayFormStatus(info.getAL_nLoanNoteId(), LOANConstant.LoanPayNoticeStatus.USED);
			System.out.println("====改变贴现凭证====");
			Sett_TransGrantDiscountDAO grantDao = new Sett_TransGrantDiscountDAO();
			grantDao.updateLoanCredenceDiscountStatus(info.getAL_nLoanNoteId(),1);//1,保存 2,删除
			System.out.println("====改变完成====");
			
			System.out.println("out save subAccount");
		}
		catch (ITreasuryDAOException e)
		{
			e.printStackTrace();
			throw new SettlementDAOException(e);
		} 
		return lReturn;
	}
	/**
	 * 根据ID 修改子账户信息
	 * @param info
	 * @return
	 * @throws SettlementException
	 */
	public long update(CompatibilitySubAccountSettingInfo subAccountInfo) throws Exception
	{
		long lReturn = -1;
		try
		{
			System.out.println("into update subAccount");
			if(subAccountInfo.getStatusId() == 0)//删除操作
			{
				if(subAccountDAO.findIsCanDelete(subAccountInfo.getId()) > 0)//子账户是否可以被删除
				{
					lReturn = -1;
				}
				else
				{
					subAccountDAO.update(subAccountInfo);
					
					System.out.println("====改变放款通知单状态为未使用====");
					Sett_TransGrantLoanDAO dao = new Sett_TransGrantLoanDAO();
					dao.updateLoanPayFormStatus(subAccountInfo.getAL_nLoanNoteId(), LOANConstant.LoanPayNoticeStatus.CHECK);
					System.out.println("====改变贴现凭证====");
					Sett_TransGrantDiscountDAO grantDao = new Sett_TransGrantDiscountDAO();
					grantDao.updateLoanCredenceDiscountStatus(subAccountInfo.getAL_nLoanNoteId(),2);//1,保存 2,删除
					System.out.println("====改变完成====");
					
					lReturn = 1;//修改成功
				}
			}
			else
			{
				subAccountDAO.update(subAccountInfo);
				lReturn = 1;//修改成功
			}
			System.out.println("out update subAccount");
		}
		catch (ITreasuryDAOException e)
		{
			e.printStackTrace();
			throw new SettlementDAOException(e);
		} 
		return lReturn;
	}
	
	/**
	 * 根据条件查找账户
	 * @param conditionInfo
	 * @return
	 * @throws Exception
	 */
	public Collection findByConditions(CompatibilitySubAccountSettingWhereInfo conditionInfo) throws Exception
	{
		return subAccountDAO.findByConditions(conditionInfo);
	}
	/*
	 * 录入的链接查找
	 */
	public Collection findBySaveLinkQuery(CompatibilitySubAccountSettingWhereInfo conditionInfo) throws Exception
	{
		return subAccountDAO.findBySaveLinkQuery(conditionInfo);
	}
	/*
	 * 录入复核的链接查找
	 */
	public Collection findByCheckLinkQuery(CompatibilitySubAccountSettingWhereInfo conditionInfo) throws Exception
	{
		return subAccountDAO.findByCheckLinkQuery(conditionInfo);
	}
	
	/*
	 * 根据LoanNoteID取得合同ID
	 */
	public long findContractIDByLoanNoteID(long lLoanNoteID) throws Exception
	{
		return subAccountDAO.findContractIDByLoanNoteID(lLoanNoteID);
	}
	
	/*
	 * 查找已有账户已经修改，并需要复核的账户
	 */
	public Collection findByOldCheckQuery(CompatibilitySubAccountSettingWhereInfo conditionInfo) throws Exception 
	{
		return subAccountDAO.findByOldCheckQuery(conditionInfo);
	}

}
