/*
 * Created on 2008-02-27
 * 
 */
package com.iss.itreasury.settlement.accountsystem.bizlogic;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Collection;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.settlement.accountsystem.dao.AccountRelationDAO;
import com.iss.itreasury.settlement.accountsystem.dao.AccountSystemDAO;
import com.iss.itreasury.settlement.accountsystem.dataentity.AccountRelationSettingInfo;
import com.iss.itreasury.settlement.accountsystem.dataentity.AccountSystemSettingInfo;
import com.iss.itreasury.settlement.bizdelegation.TransCurrentDepositDelegation;
import com.iss.itreasury.settlement.transcurrentdeposit.dao.Sett_TransCurrentDepositDAO;
import com.iss.itreasury.settlement.transcurrentdeposit.dataentity.TransCurrentDepositAssembler;
import com.iss.itreasury.settlement.transcurrentdeposit.dataentity.TransCurrentDepositInfo;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IException;

public class AccountSystem {
	
	/**
	 * 得到帐户体系编号
	 * 
	 * @return
	 * @throws IException
	 */
    public String getAccountSystemCode() throws IException {
        String code = "";
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddhhmmss");
            code = formatter.format(Env.getSystemDateTime());
        }
        catch (Exception e) {
            throw new IException("Gen_E001");
        }
        return code;
    }
    
    /**
     * 添加帐户体系信息
     * 
     * @param assInfo
     * @return
     * @throws IException
     */
    public long addAccountSystem(AccountSystemSettingInfo assInfo) throws IException {
        try {
        	AccountSystemDAO asDAO = new AccountSystemDAO();
        	long nId = asDAO.add(assInfo);
        	return nId;
        }
        catch (Exception e) {
            throw new IException("Gen_E001");
        }
    }
    
    /**
     * 添加帐户体系关系信息
     * 
     * @param arsInfo
     * @return
     * @throws IException
     */
    public long addAccountRelation(AccountRelationSettingInfo arsInfo) throws IException {
        try {
        	AccountRelationDAO arDAO = new AccountRelationDAO();
        	long nId = arDAO.getMaxID();
        	arsInfo.setId(nId);
        	arDAO.add(arsInfo);
        	return nId;
        }
        catch (Exception e) {
            throw new IException("Gen_E001");
        }
    }
    
    /**
     * 更新帐户体系信息
     * 
     * @param assInfo
     * @throws IException
     */
    public void updateAccountSystem(AccountSystemSettingInfo assInfo) throws IException {
        try {
        	AccountSystemDAO asDAO = new AccountSystemDAO();
        	asDAO.update(assInfo);
        }
        catch (Exception e) {
            throw new IException("Gen_E001");
        }
    }
    
    /**
     * 更新帐户体系信息
     * 
     * @param assInfo
     * @throws IException
     */
    public void updateAccountRelation(AccountRelationSettingInfo arsInfo) throws IException {
        try {
        	AccountRelationDAO arDAO = new AccountRelationDAO();
        	arDAO.update(arsInfo);
        }
        catch (Exception e) {
            throw new IException("Gen_E001");
        }
    }
    
    /**
     * 获取帐户体系信息集合
     * 
     * @param assInfo
     * @return
     * @throws IException
     */
    public Collection findAccountSystem(AccountSystemSettingInfo assInfo) throws IException {
        try {
        	AccountSystemDAO asDAO = new AccountSystemDAO();
        	Collection coll = asDAO.findByInfoAccountSystem(assInfo);
        	return coll;
        }
        catch (Exception e) {
            throw new IException("Gen_E001");
        }
    }
    
    /**
     * 根据AccountSystemID获取帐户体系子信息集合
     * 
     * @param nAccountSystemId
     * @return
     * @throws IException
     */
    public Collection findCollByAccountSystemId(long nAccountSystemId) throws IException {
        try {
        	AccountRelationDAO arDAO = new AccountRelationDAO();
        	Collection coll = arDAO.findCollByAccountSystemId(nAccountSystemId, -1);
        	return coll;
        }
        catch (Exception e) {
            throw new IException("Gen_E001");
        }
    }
    
    /**
     * 根据ID获取帐户体系信息
     * 
     */
    public AccountSystemSettingInfo findAccountSystem(long id) throws IException {
        try {
        	AccountSystemDAO asDAO = new AccountSystemDAO();
        	AccountSystemSettingInfo assInfo = asDAO.findByID(id);
        	return assInfo;
        }
        catch (Exception e) {
            throw new IException("Gen_E001");
        }
    }
    
    /**
     * 撤消资金上划、下拨
     * 
     * @param nAccountSystemId
     * @param nId 
     * @param nTransactionTypeId
     * @throws IException
     */
    public void unCapitalChange(long nTransId) throws IException {
        try {
        	Sett_TransCurrentDepositDAO dao = new Sett_TransCurrentDepositDAO();
        	TransCurrentDepositDelegation depositDelegation = null;
        	TransCurrentDepositAssembler depositAssembler =null;
        	
        	//取消复核
        	/*depositAssembler = new TransCurrentDepositAssembler(dao.findByID(nTransId));
        	depositDelegation = new TransCurrentDepositDelegation();
        	depositDelegation.cancelCheck(depositAssembler);
        	
        	//删除
        	depositAssembler = new TransCurrentDepositAssembler(dao.findByID(nTransId));
        	depositDelegation = new TransCurrentDepositDelegation();
        	depositDelegation.delete(depositAssembler);*/
        	
        	depositDelegation = new TransCurrentDepositDelegation();
        	depositAssembler = new TransCurrentDepositAssembler(dao.findByID(nTransId));
        	depositDelegation.cancelAndCheckAutomatically(depositAssembler);
        }
        catch (Exception e) {
            throw new IException("Gen_E001");
        }
    }
    
    /**
     * 资金上划、下拨
     * 
     * @param assInfo
     * @param arsInfo
     * @param balance
     * @throws IException
     */
    public void capitalChange(TransCurrentDepositInfo depositInfo) throws IException{
        try {
        	TransCurrentDepositDelegation depositDelegation = null;
        	TransCurrentDepositAssembler depositAssembler = null;
        	
        	//保存
        	/*depositAssembler = new TransCurrentDepositAssembler(depositInfo);
        	depositDelegation = new TransCurrentDepositDelegation();
        	long lTemp = depositDelegation.save(depositAssembler);
        	
        	//复核
        	Sett_TransCurrentDepositDAO dao = new Sett_TransCurrentDepositDAO();
        	depositAssembler = new TransCurrentDepositAssembler(dao.findByID(lTemp));
        	depositDelegation = new TransCurrentDepositDelegation();
        	depositDelegation.check(depositAssembler);*/
        	
        	depositDelegation = new TransCurrentDepositDelegation();
        	depositAssembler = new TransCurrentDepositAssembler(depositInfo);
        	depositDelegation.saveAndCheckNew(depositAssembler);
        	
        }
        catch (Exception e) {
            throw new IException("Gen_E001");
        }
    }
    
    /**
     * 根据子ID获取子信息
     * 
     */
    public AccountRelationSettingInfo findAccountRelation(long id) throws IException {
        try {
        	AccountRelationDAO arDAO = new AccountRelationDAO();
        	AccountRelationSettingInfo arsInfo = (AccountRelationSettingInfo)arDAO.findByID(id, AccountRelationSettingInfo.class);
        	return arsInfo;
        }
        catch (Exception e) {
            throw new IException("Gen_E001");
        }
    }
    
	/**
	 * 根据体系ID 查找有效的交易记录，（企业资金上拨、下划）
	 * added by leiyang 2008/03/31
	 */
	public Collection findTranscurrentDepositColl(long nAccountSystemId) throws IException{
        try {
        	AccountRelationDAO arDAO = new AccountRelationDAO();
        	Collection coll = arDAO.findTranscurrentDepositColl(nAccountSystemId);
        	return coll;
        }
        catch (Exception e) {
            throw new IException("Gen_E001");
        }
	}
    
    /**
     * 企业资金上划、下拨 时使用此方法
     * 根据AccountSystemId检查帐户体系子信息
     * 
     */
    public Collection findAccountRelationColl(long nId, long nAccountSystemId, long nOfficeId, long nCurrencyId) throws IException {
        try {
        	AccountRelationDAO arDAO = new AccountRelationDAO();
        	Collection coll = arDAO.findAccountRelationColl(nId, nAccountSystemId, nOfficeId, nCurrencyId);
        	return coll;
        }
        catch (Exception e) {
            throw new IException("Gen_E001");
        }
    }
    
    /**
     * 企业撒销上划、下拨 时使用此方法
     * 
     * @param nAccountSystemId
     * @param nId
     * @param nTransactionTypeId
     * @return
     * @throws IException
     */
    public Collection findAccountRelationColl(long nOfficeId, long nCurrencyId, long nTransactionTypeId, Timestamp dtExecute) throws IException {
        try {
        	AccountRelationDAO arDAO = new AccountRelationDAO();
        	Collection coll = arDAO.findAccountRelationColl(nOfficeId, nCurrencyId, nTransactionTypeId, dtExecute);
        	return coll;
        }
        catch (Exception e) {
            throw new IException("Gen_E001");
        }
    }
    
    /**
     * 增加子信息时 检查AccountRelation中帐户的信息
     * 根据AccountSystemId检查帐户体系子信息
     * 
     */
    public boolean checkAccountRelationByAccountId(long accountId) throws IException {
        try {
        	AccountRelationDAO arDAO = new AccountRelationDAO();
        	Collection coll = arDAO.findCollByAccountSystemId(-1, accountId);
        	if(coll == null){
        		return true;
        	}
        	return false;
        }
        catch (Exception e) {
            throw new IException("Gen_E001");
        }
    }

	/**
	 * 账户在体系内的可用余额(前提是账户在体系内)
	 * 
	 * @param childAccountId
	 * @return
	 */
	public double getAccountCanUseBalance(long accountId) throws IException {

		double dReturn = 0.00d;
		AccountSystemDAO accountSystemDAO = new AccountSystemDAO();

		try {
			/** 查询账户是否是上级账户 */
			long lSystemId = accountSystemDAO
					.findSystemIdByUpAccoutId(accountId);

			if (lSystemId > 0) {
				/** 如果是上级账户 */
				// A、得到体系内它下级账户的所有透支金额（负数）
				double dAllChildAccountOverDraftBalance = accountSystemDAO
						.sumAllChildAccountOverDraftBalance(lSystemId);
				// B、得到它自身的余额；
				double dSelfBalance = accountSystemDAO
						.findAccountSelfCanUseBalance(accountId);
				// C、自身余额-透支总和
				dReturn = dSelfBalance + dAllChildAccountOverDraftBalance;

			} else {
				/** 如果下级账户 */

				// A、得到同级其它账户的透支总和(负数)
				double dOtherChildAccountOverDraftBalance = accountSystemDAO
						.sumOtherChildAccountOverDraftBalance(accountId);

				// B、上级账户的可用余额
				long lUpAccountId = accountSystemDAO
						.findUpAccountIdByChildAccountId(accountId);
				double dUpAccountSelfCanUseBalance = accountSystemDAO
						.findAccountSelfCanUseBalance(lUpAccountId);

				// C、自身的可用余额
				double dSelfAccountCanUseBalance = accountSystemDAO
						.findAccountSelfCanUseBalance(accountId);

				// D、自身余额+上级账户余额-同级透支金额
				dReturn = dUpAccountSelfCanUseBalance
						+ dOtherChildAccountOverDraftBalance
						+ dSelfAccountCanUseBalance;
			}

		} catch (ITreasuryDAOException e) {
			throw new IException("查询账户在体系内的可用余额失败：" + e.getMessage(), e);
		}
		return dReturn;
	}

	public long getAccountSystemId(long accountId) throws IException {
		long lReturn = -1;
		AccountSystemDAO accountSystemDAO = new AccountSystemDAO();
		try {

			/*
			 * lReturn = accountSystemDAO.findSystemIdByUpAccoutId(accountId);
			 * if (lReturn < 0) { lReturn = accountSystemDAO
			 * .findSystemIdByChildAccoutId(accountId); }
			 */

			lReturn = accountSystemDAO.findSystemIdByAccoutId(accountId);
		} catch (ITreasuryDAOException e) {
			throw new IException("查询账户体系失败：" + e.getMessage(), e);
		}

		return lReturn;
	}

	public String[] findSystemOverDraftAcountList(long officeId, long currencyId)
			throws IException {
		return new AccountSystemDAO().findSystemOverDraftAcountList(officeId,
				currencyId);
	}
}
