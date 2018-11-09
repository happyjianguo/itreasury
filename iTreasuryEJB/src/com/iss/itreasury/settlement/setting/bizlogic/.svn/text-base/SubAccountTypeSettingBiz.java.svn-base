/*
 * Created on 2004-9-3
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.settlement.setting.bizlogic;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collection;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.settlement.account.dao.Sett_AccountDAO;
import com.iss.itreasury.settlement.account.dao.Sett_CurrencySubjectDAO;
import com.iss.itreasury.settlement.account.dataentity.AccountInfo;
import com.iss.itreasury.settlement.account.dataentity.CurrencySubjectInfo;
import com.iss.itreasury.settlement.account.dataentity.QueryAccountConditionInfo;
import com.iss.itreasury.settlement.base.SettlementDAOException;
import com.iss.itreasury.settlement.base.SettlementException;
import com.iss.itreasury.settlement.interest.dataentity.InterestCalculationModeQueryEntity;
import com.iss.itreasury.settlement.setting.dao.Sett_AccountTypeSettingDAO;
import com.iss.itreasury.settlement.setting.dao.Sett_SubAccountType_CurrentSettingDAO;
import com.iss.itreasury.settlement.setting.dao.Sett_SubAccountType_FixSettingDAO;
import com.iss.itreasury.settlement.setting.dao.Sett_SubAccountType_LoanSettingDAO;
import com.iss.itreasury.settlement.setting.dao.Sett_SubAccountType_TransferSettingDAO;
import com.iss.itreasury.settlement.setting.dataentity.AccountTypeSettingInfo;
import com.iss.itreasury.settlement.setting.dataentity.SubAccountTypeCurrentSettingInfo;
import com.iss.itreasury.settlement.setting.dataentity.SubAccountTypeFixedDepositInfo;
import com.iss.itreasury.settlement.setting.dataentity.SubAccountTypeLoanSettingInfo;
import com.iss.itreasury.settlement.setting.dataentity.TransferSettingInfo;
import com.iss.itreasury.settlement.util.UtilOperation;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IRollbackException;
import com.iss.itreasury.util.Log4j;

/**
 * @author ruixie
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class SubAccountTypeSettingBiz{
    private Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);
	private javax.ejb.SessionContext mySessionCtx = null;
	private Sett_SubAccountType_LoanSettingDAO subLoanDAO = new Sett_SubAccountType_LoanSettingDAO();
	private Sett_SubAccountType_CurrentSettingDAO subCurrentDAO = new Sett_SubAccountType_CurrentSettingDAO();
	private Sett_AccountTypeSettingDAO accountTypeDAO = new Sett_AccountTypeSettingDAO();
	private Sett_SubAccountType_FixSettingDAO subFixDAO = new Sett_SubAccountType_FixSettingDAO();
	private Sett_SubAccountType_TransferSettingDAO subTransferDAO = new Sett_SubAccountType_TransferSettingDAO();
	
	/*
	 * 从AccountType表中取得 某一账户组下面的所有账户类型
	 * lAccountGroupID = -1,则取出所有账户类型
	 */
	public Collection findAllAccountTypeByGroupID(long lAccountGroupID) throws SettlementException
	{
		AccountTypeSettingInfo info = new AccountTypeSettingInfo();
		try
		{
			return accountTypeDAO.findAllAccountTypeByGroupID(lAccountGroupID);
			
		} catch (SettlementException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * add by 2007.6.18 qhzhou
	 * 从AccountType表中取得 某一账户组下面当前办事处，币种的所有账户类型
	 * lAccountGroupID = -1,则取出所有账户类型
	 * 
	 */
	public Collection findAllAccountTypeByGroupID(long lAccountGroupID,long lOfficeID,long lCurrencyID) throws SettlementException
	{
		//AccountTypeSettingInfo info = new AccountTypeSettingInfo();
		try
		{
			return accountTypeDAO.findAllAccountTypeByGroupID(lAccountGroupID,lOfficeID,lCurrencyID);
			
		} catch (SettlementException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	
	/*
	 * 取得贷款账户类型编码设置
	 */
	public SubAccountTypeLoanSettingInfo findSubTypeLoanById(long lId) throws SettlementException
	{
		SubAccountTypeLoanSettingInfo info = new SubAccountTypeLoanSettingInfo();
		try
		{
			info = (SubAccountTypeLoanSettingInfo)subLoanDAO.findByID(lId,info.getClass());
		} catch (ITreasuryDAOException e)
		{
			throw new SettlementDAOException(e);
		}
		return info;
	}
	
	
	/*
	* 取得活期账户类型编码设置
	 */
		public SubAccountTypeCurrentSettingInfo findSubTypeCurrentById(long lId) throws SettlementException
		{
			SubAccountTypeCurrentSettingInfo info = new SubAccountTypeCurrentSettingInfo();
			try
			{
				info = (SubAccountTypeCurrentSettingInfo)subCurrentDAO.findByID(lId,info.getClass());
			} catch (ITreasuryDAOException e)
			{
				throw new SettlementDAOException(e);
			}
			return info;
		}
	/*
	 * 取得活期账户类型编码设置
	 */
	public TransferSettingInfo findTransferById(long lId) throws SettlementException
	{
		TransferSettingInfo info = new TransferSettingInfo();
		try
		{
			info = (TransferSettingInfo)subTransferDAO.findByID(lId, info.getClass());
		}catch(ITreasuryDAOException e)
		{
			throw new SettlementDAOException(e);
		}
		return info;
	}
	/*
	 * 取得账户设置
	 */
	public AccountTypeSettingInfo findAccountTypeById(long lId) throws SettlementException
	{
		AccountTypeSettingInfo info = new AccountTypeSettingInfo();
		try
		{
			info = (AccountTypeSettingInfo)accountTypeDAO.findByID(lId,info.getClass());
		} catch (ITreasuryDAOException e)
		{
			throw new SettlementDAOException(e);
		}
		return info;
	}
	
	public long updateAccountTypeSetting(AccountTypeSettingInfo info,long lCurrencyID,String strSubject) throws SettlementException
	{
		long lReturn = -1;
		try
		{
			System.out.println("修改sett_AccountType表start");
			System.out.println(info);
			accountTypeDAO.update(info);
			System.out.println("修改sett_AccountType表end");
			System.out.println("修改sett_currencysubject表start");
			accountTypeDAO.updateSubjectCodeByAccountTypeID(lCurrencyID,info.getId(),strSubject);
			System.out.println("修改sett_currencysubject表end");
			lReturn = 1;
//          刷新hashtable
            Env env = Env.getInstance();
            env.setAccountTypeHash();
		} catch (ITreasuryDAOException e)
		{
			throw new SettlementDAOException(e);
		}
		return lReturn;
	}
	
	/*
	 * 根据AccountTypeID查询，所有设置的贷款编码明细列表
	 */
	public Collection findLoanAccountByAccountTypeID (long lAccountTypeID,long lOfficeID,long lCurrencyID) throws SettlementException
	{
		try
		{
			return subLoanDAO.findByAccountTypeID(lAccountTypeID,lOfficeID,lCurrencyID);
			
		} catch (SettlementException e)
		{
			e.printStackTrace();
			throw new SettlementException();
		}
	}
	
		/*
		 * 根据AccountTypeID查询，所有设置的活期编码明细列表
		 */
		public Collection findCurrentAccountByAccountTypeID (long lAccountTypeID,long lOfficeID,long lCurrencyID) throws SettlementException
		{
			try
			{
				return subCurrentDAO.findByAccountTypeID(lAccountTypeID,lOfficeID,lCurrencyID);
				
			
			} catch (SettlementException e)
			{
				e.printStackTrace();
				throw new SettlementException();
			}
		}
		/*
		 * 根据AccountTypeID查询，所有设置的资产转让编码明细列表
		 */
		public Collection findTransferAccountByAccountTypeID(long lOfficeID,long lCurrencyID) throws SettlementException,Exception
		{
			try
			{
				return subTransferDAO.findByAccountTypeID(lOfficeID, lCurrencyID);
			}catch(SettlementException e)
			{
				e.printStackTrace();
				throw new SettlementException();
			}
		}
	
	/*
	 * 修改贷款的明细设置
	 */
	public long updateSubLoanInfo (SubAccountTypeLoanSettingInfo subLoanInfo) throws SettlementException
	{
		long lReturn = -1;
		try
		{
			if(subLoanDAO.isLoanSettingExist(subLoanInfo))
			{
				throw new IRollbackException(mySessionCtx,"已经存在相同条件的科目设置信息！");
			}
			subLoanDAO.update(subLoanInfo);
			lReturn = 1;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new SettlementException(e.getMessage(),e);
		}
		return lReturn;
	}
	/*
	 * 删除贷款的明细设置
	 */
	public long deleteSubLoanInfo (long lID) throws SettlementException
	{
		long lReturn = -1;
		try
		{
			SubAccountTypeLoanSettingInfo subLoanInfo = new SubAccountTypeLoanSettingInfo();
			subLoanInfo.setId(lID);
			subLoanInfo.setStatusId(Constant.RecordStatus.INVALID);
			subLoanDAO.update(subLoanInfo);
			lReturn = 1;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new SettlementException();
		}
		return lReturn;
	}
	/*
	 * 新增贷款明细设置
	 */
	public long addSubLoanInfo (SubAccountTypeLoanSettingInfo subLoanInfo) throws SettlementException
	{
		long lReturn = -1;
		try
		{
			subLoanInfo.setSerialNo(subLoanDAO.getMaxSerialNo(subLoanInfo));
			subLoanInfo.setStatusId(Constant.RecordStatus.VALID);
			if(subLoanDAO.isLoanSettingExist(subLoanInfo))
			{
					throw new IRollbackException(mySessionCtx,"已经存在相同条件的科目设置信息！");
			}
			System.out.println("====================开始新增=======================");
			System.out.println(subLoanInfo);
			subLoanDAO.setUseMaxID();
			subLoanDAO.add(subLoanInfo);
			System.out.println("====================新增结束=======================");
			lReturn = 1;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new SettlementException(e.getMessage(),e);
		}
		return lReturn;
	}
	
	
	/*
		 * 修改活期的明细设置
		 */
		public long updateSubCurrentInfo (SubAccountTypeCurrentSettingInfo subCurrentInfo) throws SettlementException
		{
			long lReturn = -1;
			try
			{
				if(subCurrentDAO.isCurrentSettingExist(subCurrentInfo))
				{
					throw new IRollbackException(mySessionCtx,"已经存在相同条件的科目设置信息！");
				}
				subCurrentDAO.update(subCurrentInfo);
				lReturn = 1;
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new SettlementException(e.getMessage(),e);
			}
			return lReturn;
		}
		/*
		 * 修改资产转让设置
		 */
		public long updateSubTransferInfo (TransferSettingInfo subTransferInfo) throws SettlementException
		{
			long lReturn = -1;
			try
			{
				subTransferDAO.update(subTransferInfo);
				lReturn = 1;
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new SettlementException(e.getMessage(),e);
			}
			return lReturn;
		}
		
		/*
		 * 删除活期的明细设置
		 */
		public long deleteSubCurrentInfo (long lID) throws SettlementException
		{
			long lReturn = -1;
			try
			{
				SubAccountTypeCurrentSettingInfo subCurrentInfo = new SubAccountTypeCurrentSettingInfo();
				subCurrentInfo.setId(lID);
				subCurrentInfo.setStatusID(Constant.RecordStatus.INVALID);
				subCurrentDAO.update(subCurrentInfo);
				lReturn = 1;
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new SettlementException();
			}
			return lReturn;
		}
		/*
		 * 删除资产转让设置
		 */
		public long deleteSubTransferInfo (long lID) throws SettlementException
		{
			long lReturn = -1;
			try
			{
				TransferSettingInfo subTransferInfo = new TransferSettingInfo();
				subTransferInfo.setId(lID);
				subTransferInfo.setStatusId(Constant.RecordStatus.INVALID);
				subTransferDAO.update(subTransferInfo);
				lReturn = 1;
			}
			catch(Exception e)
			{
				e.printStackTrace();
				throw new SettlementException();
			}
			return lReturn;
		}
		/*
		 * 新增贷活期细设置
		 */
		public long addSubCurrentInfo (SubAccountTypeCurrentSettingInfo subCurrentInfo) throws SettlementException
		{
			long lReturn = -1;
			try
			{
				subCurrentInfo.setStatusID(Constant.RecordStatus.VALID);
				if(subCurrentDAO.isCurrentSettingExist(subCurrentInfo))
				{
					throw new IRollbackException(mySessionCtx,"已经存在相同条件的科目设置信息！");
				}
				System.out.println("====================开始新增=======================");
				System.out.println(subCurrentInfo);
				subCurrentDAO.add(subCurrentInfo);
				System.out.println("====================新增结束=======================");
				lReturn = 1;
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new SettlementException("操作失败",e);
			}
			return lReturn;
		}
		/*
		 * 新增资产转让设置
		 */
		public long addSubTransferInfo (TransferSettingInfo subTransferInfo) throws SettlementException
		{
			long lReturn = -1;
			try
			{
				subTransferInfo.setStatusId(Constant.RecordStatus.VALID);
//				if(subTransferDAO.isTransferSettingExist(subTransferInfo))
//				{
//					throw new IRollbackException(mySessionCtx,"已经存在相同条件的科目设置信息！");
//				}
				System.out.println("====================开始新增=======================");
				System.out.println(subTransferInfo);
				subTransferDAO.add(subTransferInfo);
				System.out.println("====================新增结束=======================");
				lReturn = 1;
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new SettlementException("操作失败",e);
			}
			return lReturn;
		}
	
	
	
	/**
	 * 查询某账户组所有账户类型编码设置
	 * <p>
	 * <b>&nbsp;</b>
	 * <ol><b>查询某账户组所有账户类型编码设置</b>
	 * <ul>
	 * <li>操作数据库表AccountType
	 * <li>返回Collection，包含类AccountTypeInfo
	 * </ul>
	 * </ol>
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	 * @param lAccountGroupID
	 * @param lPageLineCount  每页行数条件
	 * @param lPageNo         第几页条件
	 * @param lOrderParam     排序条件，根据此参数决定结果集排序条件
	 * @param lDesc           升序或降序
	 * @return Collection
	 * @exception Exception
	 */
	public Collection findAllAccountTypeByGroupID(long lAccountGroupID, long lOfficeID, long lCurrencyID, long lPageLineCount, long lPageNo, long lOrderParam, long lDesc) {
		return accountTypeDAO.findAllAccountTypeByGroupID( lAccountGroupID,  lOfficeID,  lCurrencyID,  lPageLineCount,  lPageNo,  lOrderParam,  lDesc);
	}
	
	
	/**
	 * 保存活期账户类型编码设置
	 * <p>
	 * <b>&nbsp;</b>
	 * <ol><b>保存活期账户类型编码设置</b>
	 * <ul>
	 * <li>操作数据库表AccountType
	 * <li>如果lID<0，则在AccountType表中新增一条记录
	 * <li>否则更新标识是lID的记录信息
	 * <li>将状态置为正常
	 * </ul>
	 * </ol>
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	 * @param lID
	 * @param lAccountGroupID
	 * @param strAccountTypeCode
	 * @param strAccountType
	 * @param strSubjectCode
	 * @return void
	 * @exception Exception
	 */
	public long saveCurrentAccountType(long lID, long lAccountGroupID, String strAccountTypeCode, String strAccountType, long lIsExistSubClass,long lIsClient,long lIsAccount,String strSubjectCode, long lOfficeID, long lCurrencyID,long AccountModule,long payModule)
	{
		return accountTypeDAO.saveCurrentAccountType( lID,  lAccountGroupID,  strAccountTypeCode,  strAccountType, lIsExistSubClass, lIsClient,lIsAccount,strSubjectCode,  lOfficeID,  lCurrencyID ,AccountModule,payModule);
	}
	/**
	 * 保存活期账户类型编码设置2
	 * <p>
	 * <b>&nbsp;</b>
	 * <ol><b>保存活期账户类型编码设置</b>
	 * <ul>
	 * <li>操作数据库表AccountType
	 * <li>如果lID<0，则在AccountType表中新增一条记录
	 * <li>否则更新标识是lID的记录信息
	 * <li>将状态置为正常
	 * </ul>
	 * </ol>
	 * @Copyright (c) Jan. 2008, by iSoftStone Inc. All Rights Reserved
	 * @param lID
	 * @param lAccountGroupID
	 * @param strAccountTypeCode
	 * @param strAccountType
	 * @param strSubjectCode
	 * @return void
	 * @exception Exception
	 */
	public long saveMarginAccountType(long lID, long lAccountGroupID, String strAccountTypeCode, String strAccountType, long lIsExistSubClass,long lIsClient,long lIsAccount,long lIsDeposit ,String strSubjectCode, long lOfficeID, long lCurrencyID,long AccountModule,long payModule)
	{
		return accountTypeDAO.saveMarginAccountType( lID,  lAccountGroupID,  strAccountTypeCode,  strAccountType, lIsExistSubClass, lIsClient,lIsAccount,lIsDeposit,strSubjectCode,  lOfficeID,  lCurrencyID ,AccountModule,payModule);
	}
	/*
	 * 
	 * add by jiangqi 
	 * */
	public long modifySaveReserveAccountType(AccountTypeSettingInfo info)
	{		
		return accountTypeDAO.modifySaveReserveAccountType(info);		
	}
	/**
	 * 保存定期账户类型编码设置
	 * <p>
	 * <b>&nbsp;</b>
	 * <ol><b>保存定期账户类型编码设置</b>
	 * <ul>
	 * <li>操作数据库表AccountType
	 * <li>如果lID<0，则在AccountType表中新增一条记录
	 * <li>否则更新标识是lID的记录信息,及表SubAccountType_FixedDeposit中的对应信息
	 * <li>将状态置为正常
	 * </ul>
	 * </ol>
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	 * @param lID
	 * @param lAccountGroupID
	 * @param strAccountTypeCode
	 * @param strAccountType
	 * @param lIsExistSubClass
	 * @param strDefaultDocCode
	 * @param lIsAutoClearAccount
	 * @param strSubjectCode
	 * @return void
	 * @exception Exception
	 */
	public long saveFixedAccountType(long lID, long lAccountGroupID, String strAccountTypeCode, String strAccountType, long lIsExistSubClass, String strDefaultDocCode, long lIsAutoClearAccount, String strSubjectCode,long lIsClient, long lOfficeID, long lCurrencyID) 
	{
		return accountTypeDAO.saveFixedAccountType( lID,  lAccountGroupID,  strAccountTypeCode,  strAccountType,  lIsExistSubClass,  strDefaultDocCode,  lIsAutoClearAccount,  strSubjectCode,  lIsClient,lOfficeID,  lCurrencyID);
	}
	
	/**
	 * Modify by leiyang  date 2007/06/19
	 * 
	 * 
	 * 保存定期账户类型编码设置
	 * <p>
	 * <b>&nbsp;</b>
	 * <ol><b>保存定期账户类型编码设置</b>
	 * <ul>
	 * <li>操作数据库表AccountType
	 * <li>否则更新标识是lID的记录信息,及表SubAccountType_FixedDeposit中的对应信息
	 * <li>将状态置为正常
	 * </ul>
	 * </ol>
	 * @Copyright (c) Jan. 2007, by iSoftStone Inc. All Rights Reserved
	 * @param info
	 * @return void
	 * @exception Exception
	 */
	public void saveFixedAccountType(AccountTypeSettingInfo info){
		accountTypeDAO.saveFixedAccountType(info);
	}
	
	
	/**
	 * 根据标识查询账户类型编码设置
	 * <p>
	 * <b>&nbsp;</b>
	 * <ol><b>根据标识查询账户类型编码设置</b>
	 * <ul>
	 * <li>操作数据库表AccountType,AccountGroup
	 * <li>返回类AccountTypeInfo
	 * </ul>
	 * </ol>
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	 * @param lID
	 * @return AccountTypeInfo
	 * @exception Exception
	 */
	public AccountTypeSettingInfo findAccountTypeByID(long lID, long lOfficeID, long lCurrencyID)
	{
		return accountTypeDAO.findAccountTypeByID( lID,  lOfficeID,  lCurrencyID);
	}
	
	
	/**
	 * 保存定期账户类型编码设置的下级分类信息
	 * <p>
	 * <b>&nbsp;</b>
	 * <ol><b>保存定期账户类型编码设置的下级分类信息</b>
	 * <ul>
	 * <li>操作数据库表SubAccountType_FixedDeposit
	 * <li>如果lID<0，则在SubAccountType_FixedDeposit表中新增一条记录
	 * <li>将状态置为正常
	 * </ul>
	 * </ol>
	 * @Copyright (c) Jan. 2008, by iSoftStone Inc. All Rights Reserved
	 * @param lID
	 * @param lAccountTypeID
	 * @param lFixedDepositMonthID
	 * @param strSubjectCode
	 * @return void
	 * @exception Exception
	 */
	public long saveSubAccountTypeFixedDeposit(SubAccountTypeFixedDepositInfo info){
		return subFixDAO.saveSubAccountTypeFixedDeposit(info);
	}
	
	/**
	 * 查询某条定期存款账户类型编码设置的所有下级分类信息
	 * <p>
	 * <b>&nbsp;</b>
	 * <ol><b>查询某条定期存款账户类型编码设置的所有下级分类信息</b>
	 * <ul>
	 * <li>操作数据库表SubAccountType_FixedDeposit
	 * <li>返回Collection，包含类SubAccountTypeFixedDepositInfo
	 * </ul>
	 * </ol>
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	 * @param lAccountTypeID
	 * @param lPageLineCount  每页行数条件
	 * @param lPageNo         第几页条件
	 * @param lOrderParam     排序条件，根据此参数决定结果集排序条件
	 * @param lDesc           升序或降序
	 * @return Collection
	 * @exception Exception
	 */
	public Collection findAllSubAccountTypeFixedDepositByAccountType(long lAccountTypeID,long lOfficeID, long lCurrencyID, long lPageLineCount, long lPageNo, long lOrderParam, long lDesc) {
		return subFixDAO.findAllSubAccountTypeFixedDepositByAccountType( lAccountTypeID,  lOfficeID,  lCurrencyID, lPageLineCount, lPageNo, lOrderParam, lDesc);
	}
	/**
	 * 根据标识查询定期存款账户类型编码设置的一条下级分类信息
	 * <p>
	 * <b>&nbsp;</b>
	 * <ol><b>根据标识查询定期存款账户类型编码设置的一条下级分类信息</b>
	 * <ul>
	 * <li>操作数据库表SubAccountType_FixedDeposit
	 * <li>返回类SubAccountTypeFixedDepositInfo
	 * </ul>
	 * </ol>
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	 * @param lID
	 * @return SubAccountTypeFixedDepositInfo
	 * @exception Exception
	 */
	public SubAccountTypeFixedDepositInfo findSubAccountTypeFixedDepositByID(long lID, long lOfficeID, long lCurrencyID) {
		return accountTypeDAO.findSubAccountTypeFixedDepositByID( lID,  lOfficeID,  lCurrencyID);
	}
	
	/**
	 * 删除定期账户类型编码设置的下级分类信息
	 * <p>
	 * <b>&nbsp;</b>
	 * <ol><b>删除定期账户类型编码设置的下级分类信息</b>
	 * <ul>
	 * <li>操作数据库表SubAccountType_FixedDeposit
	 * <li>将状态置为删除
	 * </ul>
	 * </ol>
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	 * @param lID
	 * @return void
	 * @exception Exception
	 */
	public long deleteSubAccountTypeFixedDeposit(long lID){
		return accountTypeDAO.deleteSubAccountTypeFixedDeposit(lID);
	}
	/**
	 * 新增定期账户类型编码设置的下级分类信息
	 * <p>
	 * <b>&nbsp;</b>
	 * <ol><b>新增定期账户类型编码设置的下级分类信息</b>
	 * <ul>
	 * <li>操作数据库表SubAccountType_FixedDeposit
	 * <li>
	 * </ul>
	 * </ol>
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	 * @param lID
	 * @return void
	 * @exception Exception
	 */
	public long saveSubAccountTypeFixedDeposit(long lID){
		return accountTypeDAO.deleteSubAccountTypeFixedDeposit(lID);
	}
	/**
	 * 新增账户类型
	 * @param info
	 * @return 账户类型ID
	 * @throws IException
	 */
	public long addAccountType(AccountTypeSettingInfo info) throws IException
	{
	    log.debug(UtilOperation.dataentityToString(info));
	    long AccountType = -1;
	    AccountTypeSettingInfo tmpInfo = new AccountTypeSettingInfo();
	    tmpInfo.setStatusId(Constant.RecordStatus.VALID);
	    tmpInfo.setAccountType(info.getAccountType());
	    tmpInfo.setId(Long.parseLong(info.getAccountTypeCode()));///////////////
	    tmpInfo.setAccountTypeCode(info.getAccountTypeCode());
	    tmpInfo.setOfficeID(info.getOfficeID());//2007.6.16 by qhzhou	
	    tmpInfo.setCurrencyID(info.getCurrencyID());//2007.6.16 by qhzhou

	    //原来的匹配有问题，现在改为同修改一样的方法	    
	    Connection conn = null;
	    ResultSet rs = null;
	    PreparedStatement ps = null;
	    try {	
	    	conn=Database.getConnection();
//	    	保证编码不重复
			String strSQL = "select * from Sett_AccountType where sAccountTypeCode=? and id<>? and nstatusid<>? and officeId=? and currencyId=?";
			ps = conn.prepareStatement(strSQL);
			ps.setString(1, info.getAccountTypeCode());
			ps.setLong(2, info.getId());
			ps.setLong(3, 0);//Notes.CODE_RECORD_STATUS_INVALID
			ps.setLong(4, info.getOfficeID());
			ps.setLong(5, info.getCurrencyID());				
			rs = ps.executeQuery();
			if (rs != null && rs.next())
			{
				throw new IException("账户类型编码" + tmpInfo.getAccountTypeCode() + "已经存在");
			}
	    	//保证账户类型不重复
	    	strSQL = "select * from Sett_AccountType where sAccountType=? and id<>? and nstatusid<>? and officeId=? and currencyId=?";
	    	
	    	ps = conn.prepareStatement(strSQL);
			ps.setString(1, info.getAccountType());
			ps.setLong(2, info.getId());
			ps.setLong(3, Constant.RecordStatus.INVALID);//Notes.CODE_RECORD_STATUS_INVALID
			ps.setLong(4, info.getOfficeID());
			ps.setLong(5, info.getCurrencyID());
			rs = ps.executeQuery();
			if (rs != null && rs.next())
			{
				throw new IException("账户类型 \"" + tmpInfo.getAccountType() + "\" 已经存在");
			}
			
		}catch (IException ie){ 
			throw ie;
		}catch (Exception e) {
			throw new IException("操作失败");
		}finally{
			try{
				rs.close();
				rs = null;
				ps.close();
				ps = null;  
				ps.close();
				ps = null;
				conn.close();
				conn = null;
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
	    
	    try {
	        //检查账户类型编码是否存在
//            Collection c = accountTypeDAO.findByCondition(tmpInfo);			
            
//            if (c != null && c.size()>0)
//            {
//                throw new IException("账户类型编码" + tmpInfo.getAccountTypeCode() + "已经存在");
//            }
//            //检查账户类型名称是否存在
//            tmpInfo = new AccountTypeSettingInfo();
//            tmpInfo.setStatusId(Constant.RecordStatus.VALID);
//            tmpInfo.setAccountType(info.getAccountType());
//            tmpInfo.setOfficeID(info.getOfficeID());//2007.6.16 by qhzhou	
//    	    tmpInfo.setCurrencyID(info.getCurrencyID());//2007.6.16 by qhzhou
//            c = accountTypeDAO.findByCondition(tmpInfo);
//            if (c != null && c.size()>0)
//            {
//                throw new IException("账户类型 " + tmpInfo.getAccountType() + " 已经存在");
//            }
            
            //开始新增账户类型
            info.setId(Long.parseLong(info.getAccountTypeCode()));///////////////////////////
            AccountType = accountTypeDAO.add(info);
            log.info("新增账户类型结束,返回ID="+AccountType);
            log.info("开始新增账户类型科目表记录");
            //初始化CurrencySubjectInfo
            CurrencySubjectInfo subjectInfo = new CurrencySubjectInfo();
            subjectInfo.setCurrencyID(info.getCurrencyID());
            subjectInfo.setBackOfficeID(info.getOfficeID());
            subjectInfo.setTableName("Sett_accounttype");
            subjectInfo.setRecordID(AccountType);
            subjectInfo.setSubject(info.getSubjectCode());
            subjectInfo.setInterestSubject(info.getInterestSubjectCode());
            subjectInfo.setBookedInterestSubject(info.getBookedInterestSubjectCode());
            subjectInfo.setNegotiateInterestSubject(info.getNegotiateInterestSubjectCode());
            
            Sett_CurrencySubjectDAO subjectDao = new Sett_CurrencySubjectDAO();
            subjectDao.add(subjectInfo);
//          刷新hashtable
            Env env = Env.getInstance();
            env.setAccountTypeHash();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new IException();
        }
	    return AccountType;
	}
	
	/**
	 * 删除账户类型
	 * @param lID
	 * @throws IException
	 */
	public void deleteAccountType(long lID) throws IException
	{
	    QueryAccountConditionInfo accountInfo = new QueryAccountConditionInfo();
	    accountInfo.setAccountTypeID(lID);
	    //删除账户类型时不判断状态 edit by zyyao 2007-8-13
	    //accountInfo.setStatusID(Constant.RecordStatus.VALID);
	    Sett_AccountDAO accountDao = new Sett_AccountDAO();
	    try {
            Collection c = accountDao.findByConditions(accountInfo);
            if (c!=null && c.size()>0)
            {
                throw new IException("此账户类型下已经存在账户,不能删除");
            }
            accountTypeDAO.deletePhysically(lID);
            log.info("开始删除账户类型科目表");
            Sett_CurrencySubjectDAO subjectDao = new Sett_CurrencySubjectDAO();
            CurrencySubjectInfo subjectInfo = new CurrencySubjectInfo();
            subjectInfo.setTableName("Sett_accounttype");
            subjectInfo.setRecordID(lID);
            subjectDao.deletePhysically(subjectInfo);
            log.info("删除成功返回");
//          刷新hashtable
            Env env = Env.getInstance();
            env.setAccountTypeHash();
        }
	    catch (IException e) {
            throw e;
        }catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new IException();
        }
	}
	
	/**
	 * 删除总类型时，删除其所有下级分类
	 * @param accountTypeID
	 * @throws IException
	 */
	public void deleteSubTypeinfo(long accountTypeID) throws IException
	{
	    try
		{
			SubAccountTypeCurrentSettingInfo subCurrentInfo = new SubAccountTypeCurrentSettingInfo();
			subCurrentInfo.setAccountTypeID(accountTypeID);
			subCurrentInfo.setStatusID(Constant.RecordStatus.INVALID);
			subCurrentDAO.updateSubType(subCurrentInfo);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new SettlementException();
		}
	}
	

	/**
	 * 用于返回查询计息方式
	 * modify by leiyang  date 2007/06/18
	 * @param icmqe
	 * @return
	 */
	public long getInterestCalculationMode(InterestCalculationModeQueryEntity icmqe) throws IException
	{
		long lResult = accountTypeDAO.findInterestCalculation(icmqe);
		if(lResult == -1) {
			lResult = 1;
		}
		return lResult;
	}
	
	
	public static void main(String[] args)
	{
	    SubAccountTypeSettingBiz biz  = new SubAccountTypeSettingBiz();
	    AccountTypeSettingInfo info  = new AccountTypeSettingInfo();
	    info.setAccountGroupId(1);
	    info.setAccountType("baozhengjin");
	    info.setAccountTypeCode("20");
	    try {
            biz.addAccountType(info);
        } catch (IException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}
}
