package com.iss.itreasury.settlement.bizdelegation;

import com.iss.itreasury.settlement.accountsystem.bizlogic.AccountSystem;
import com.iss.itreasury.settlement.dataentity.AccountSystemInfo;
import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.ConfigConstant;
import com.iss.itreasury.util.IException;

/**
 * �˻���ϵͳ�������������ӿ�(���ǵ������˻���ϵͳ���û��д�ĸĶ�,Ϊʹ����ĵ��ò���Ӱ��,���Դ������� 2008/02/27 mzh_fu)
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
	 * У���˻��Ƿ���Ը������˻�������ϵ�ڣ�����Ӱ�죩
	 * 
	 * @param accountSystemInfo
	 *            NAccountId ������ֵ�� NPayAmount ������ֵ������Ĭ��Ϊ0
	 * @return boolean �˻��Ƿ�ͨ����ϵУ��
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
	 *            ���´���nOfficeId���ͱ��֣�nCurrencyId��������ֵ
	 * @return String[] ͸֧�˻����˻�������
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
	 * �õ��˻���ϵ���˻��Ŀ������(����У���˻��Ƿ�����ϵ��)
	 * 
	 * @param accountSystemInfo
	 *            NAccountId ������ֵ
	 * @return
	 */
	public double getAccoutCanUseBalance(AccountSystemInfo accountSystemInfo)
			throws IException {
		double dReturn = 0.00d;
		AccountSystem accountSystem = getAccountSystemInstance();
		if (!isInAccountSystem(accountSystemInfo))
			throw new IException("�˻������˻���ϵ��");

		dReturn = accountSystem.getAccountCanUseBalance(accountSystemInfo
				.getNAccountId());

		return dReturn;
	}

	/**
	 * �˻��Ƿ����˻���ϵ��
	 * 
	 * @param accountSystemInfo
	 *            NAccountId ������ֵ
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
	 * ����ػ��Ƿ�ǿ��У���˻�ϵͳ
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
			throw new IException("accountSystemInfo����ʵ��ΪNULL");

		if (type == VERIFYPARAMTYPE_A) {
			if (accountSystemInfo.getNAccountId() < 0)
				throw new IException("�����˻�IDΪ��");
		}

		if (type == VERIFYPARAMTYPE_B) {
			if (accountSystemInfo.getNAccountId() < 0)
				throw new IException("�����˻�IDΪ��");
			if (accountSystemInfo.getNOfficeId() < 0)
				throw new IException("���´����Ϊ��");
			if (accountSystemInfo.getNCurrencyId() < 0)
				throw new IException("���ֱ��Ϊ��");
		}

		if (type == VERIFYPARAMTYPE_C) {
			if (accountSystemInfo.getNOfficeId() < 0)
				throw new IException("���´����Ϊ��");
			if (accountSystemInfo.getNCurrencyId() < 0)
				throw new IException("���ֱ��Ϊ��");
		}

	}

}
