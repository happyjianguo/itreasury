package com.iss.itreasury.settlement.bizdelegation;

import com.iss.itreasury.settlement.accountsystem.bizlogic.AccountSystem;
import com.iss.itreasury.settlement.dataentity.AccountSystemInfo;
import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.ConfigConstant;
import com.iss.itreasury.util.IException;

/**
 * 账户体系统管理结算对外代理接口(考虑到将来账户体系统设置会有大的改动,为使结算的调用不受影响,所以创建该类 2008/02/27 mzh_fu)
 * 
 * @date 2008/02/27
 * @author mzh_fu
 * 
 */
public class AccountSystemDelegation {
	private static int VERIFYPARAMTYPE_A = 1;

	private static int VERIFYPARAMTYPE_B = 2;

	private static int VERIFYPARAMTYPE_C = 3;

	private AccountSystem getAccountSystemInstance() {
		return new AccountSystem();
	}

	/**
	 * 校验账户是否可以付款（如果账户不在体系内，不受影响）
	 * 
	 * @param accountSystemInfo
	 *            NAccountId 必须设值； NPayAmount 必须设值，否则默认为0
	 * @return boolean 账户是否通过体系校验
	 * @throws IException
	 */
	public boolean isPassAccountSystemVerify(AccountSystemInfo accountSystemInfo)
			throws IException {

		verifyParamInfo(accountSystemInfo, VERIFYPARAMTYPE_A);

		AccountSystem accountSystem = getAccountSystemInstance();

		if (!isInAccountSystem(accountSystemInfo))
			return true;

		double dAccountCanUseBalance = accountSystem
				.getAccountCanUseBalance(accountSystemInfo.getNAccountId());

		if (dAccountCanUseBalance - accountSystemInfo.getDPayAmount() < 0)
			return false;

		return true;
	}

	/**
	 * 
	 * @param accountSystemInfo
	 *            办事处（nOfficeId）和币种（nCurrencyId）必须设值
	 * @return String[] 透支账户的账户号数组
	 * @throws IException
	 */
	public String[] findSystemOverDraftAcountList(
			AccountSystemInfo accountSystemInfo) throws IException {

		AccountSystem accountSystem = getAccountSystemInstance();
		verifyParamInfo(accountSystemInfo, VERIFYPARAMTYPE_C);

		return accountSystem.findSystemOverDraftAcountList(accountSystemInfo
				.getNOfficeId(), accountSystemInfo.getNCurrencyId());

	}

	/**
	 * 得到账户体系内账户的可用余额(需先校验账户是否在体系内)
	 * 
	 * @param accountSystemInfo
	 *            NAccountId 必须设值
	 * @return
	 */
	public double getAccoutCanUseBalance(AccountSystemInfo accountSystemInfo)
			throws IException {
		double dReturn = 0.00d;
		AccountSystem accountSystem = getAccountSystemInstance();
		if (!isInAccountSystem(accountSystemInfo))
			throw new IException("账户不在账户体系内");

		dReturn = accountSystem.getAccountCanUseBalance(accountSystemInfo
				.getNAccountId());

		return dReturn;
	}

	/**
	 * 账户是否在账户体系中
	 * 
	 * @param accountSystemInfo
	 *            NAccountId 必须设值
	 * @return
	 * @throws IException
	 */
	public boolean isInAccountSystem(AccountSystemInfo accountSystemInfo)
			throws IException {
		boolean blnReturn = true;

		verifyParamInfo(accountSystemInfo, VERIFYPARAMTYPE_A);

		AccountSystem accountSystem = getAccountSystemInstance();
		long lAccountSystemId = accountSystem
				.getAccountSystemId(accountSystemInfo.getNAccountId());
		if (lAccountSystemId < 0)
			blnReturn = false;

		return blnReturn;
	}

	/**
	 * 结算关机是否强制校验账户系统
	 * 
	 * @return
	 */
	public static boolean isForceVerifyAccountSystemOnClose() {
		return Config.getBoolean(
				ConfigConstant.SETT_CLOSESYSTEM_ISFORCEVERIFYACCOUNTSYSTEM,
				false);
	}

	private void verifyParamInfo(AccountSystemInfo accountSystemInfo, int type)
			throws IException {
		if (accountSystemInfo == null)
			throw new IException("accountSystemInfo参数实体为NULL");

		if (type == VERIFYPARAMTYPE_A) {
			if (accountSystemInfo.getNAccountId() < 0)
				throw new IException("交易账户ID为空");
		}

		if (type == VERIFYPARAMTYPE_B) {
			if (accountSystemInfo.getNAccountId() < 0)
				throw new IException("交易账户ID为空");
			if (accountSystemInfo.getNOfficeId() < 0)
				throw new IException("办事处编号为空");
			if (accountSystemInfo.getNCurrencyId() < 0)
				throw new IException("币种编号为空");
		}

		if (type == VERIFYPARAMTYPE_C) {
			if (accountSystemInfo.getNOfficeId() < 0)
				throw new IException("办事处编号为空");
			if (accountSystemInfo.getNCurrencyId() < 0)
				throw new IException("币种编号为空");
		}

	}

}
