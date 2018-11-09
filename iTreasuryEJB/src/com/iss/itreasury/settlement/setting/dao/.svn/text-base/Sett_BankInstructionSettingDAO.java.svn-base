package com.iss.itreasury.settlement.setting.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.settlement.base.SettlementDAO;
import com.iss.itreasury.settlement.setting.dataentity.BankInstructionSettingInfo;
import com.iss.itreasury.settlement.setting.dataentity.QueryBranchInfo;

public class Sett_BankInstructionSettingDAO extends SettlementDAO {
	public static String TABEL_NAME = "sett_bankInstructionSet";

	public Sett_BankInstructionSettingDAO() {
		super(TABEL_NAME);
	}

	public Sett_BankInstructionSettingDAO(String tableName, Connection conn) {
		super(tableName, conn);
	}

	/**
	 * 根据账户ID得到账户模式
	 * 
	 * @param accountId
	 * @return
	 * @throws ITreasuryDAOException
	 */
	public long getAccountModuleByAccountId(long accountId)
			throws ITreasuryDAOException {
		long lReturn = -1;
		try {
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" select b.accountmodule accountmodule ");
			sbSQL.append(" from SETT_ACCOUNT a,SETT_ACCOUNTTYPE b ");
			sbSQL.append(" where ");
			sbSQL.append("     a.id = ? and b.id = a.nAccountTypeId ");

			initDAO();
			prepareStatement(sbSQL.toString());
			transPS.setLong(1, accountId);

			executeQuery();

			if (transRS.next()) {
				lReturn = transRS.getLong(1);
			}

		} catch (Exception e) {
			throw new ITreasuryDAOException("查询账户模式出现异常", e);
		} finally {
			finalizeDAO();
		}
		return lReturn;
	}

	/**
	 * 根据bankID得到账户模式
	 * 
	 * @param accountId
	 * @return
	 * @throws ITreasuryDAOException
	 */
	public long getAccountModuleByBankId(long bankId)
			throws ITreasuryDAOException {
		long lReturn = -1;
		try {
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" select accountmodule ");
			sbSQL.append(" from sett_branch ");
			sbSQL.append(" where ");
			sbSQL.append("    id = ? ");

			initDAO();
			prepareStatement(sbSQL.toString());
			transPS.setLong(1, bankId);

			executeQuery();

			if (transRS.next()) {
				lReturn = transRS.getLong(1);
			}

		} catch (Exception e) {
			throw new ITreasuryDAOException("查询开户行账户模式出现异常", e);
		} finally {
			finalizeDAO();
		}
		return lReturn;
	}

	/**
	 * 根据账户ID得到该账户是否可以发送指令
	 * 
	 * @param aInfo
	 * @param accountId
	 * @return
	 * @throws ITreasuryDAOException
	 */
	public long findIsSendByAccountId(BankInstructionSettingInfo aInfo,
			long accountId) throws ITreasuryDAOException {
		long lReturn = com.iss.itreasury.util.Constant.FALSE;
		try {

			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" select a.isSend ISSEND ");
			sbSQL.append(" from " + TABEL_NAME
					+ " a,SETT_ACCOUNT b,SETT_ACCOUNTTYPE c ");
			sbSQL.append(" where a.statusId = "
					+ com.iss.itreasury.util.Constant.TRUE);
			sbSQL.append("     and a.nOfficeId = ? and a.nCurrencyId = ? ");
			sbSQL.append("     and a.transType = ? ");
			sbSQL.append("     and b.id = ? and c.id = b.nAccountTypeId ");
			sbSQL.append("     and a.accountModule = c.accountModule ");

			//added by mzh_fu 2007/09/27 加入转账方式过滤条件
			if (aInfo.getTransModule() > 0) {
				sbSQL.append(" and a.transmodule = ? ");
			}

			initDAO();
			prepareStatement(sbSQL.toString());
			transPS.setLong(1, aInfo.getNOfficeId());
			transPS.setLong(2, aInfo.getNCurrencyId());
			transPS.setLong(3, aInfo.getTransType());
			transPS.setLong(4, accountId);

			//added by mzh_fu 2007/09/27 加入转账方式过滤条件
			if (aInfo.getTransModule() > 0) {
				transPS.setLong(5, aInfo.getTransModule());
			}

			executeQuery();

			if (transRS.next()) {
				lReturn = transRS.getLong(1);
			}

		} catch (Exception e) {
			throw new ITreasuryDAOException("查询指令设置出现异常", e);
		} finally {
			finalizeDAO();
		}
		return lReturn;
	}

	/**
	 * 根据开户行ID得到该账户是否可以发送指令
	 * 
	 * @param aInfo
	 * @param bankId
	 * @return
	 * @throws ITreasuryDAOException
	 */
	public long findIsSendByBankId(BankInstructionSettingInfo aInfo, long bankId)
			throws ITreasuryDAOException {
		long lReturn = com.iss.itreasury.util.Constant.FALSE;
		try {

			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" select a.isSend ISSEND ");
			sbSQL.append(" from " + TABEL_NAME + " a,sett_branch b");
			sbSQL.append(" where a.statusId = "
					+ com.iss.itreasury.util.Constant.TRUE);
			sbSQL.append("     and a.nOfficeId = ? and a.nCurrencyId = ? ");
			sbSQL.append("     and a.transType = ? ");
			sbSQL.append("     and b.id = ? ");
			sbSQL.append("     and a.accountModule = b.accountModule ");

			initDAO();
			prepareStatement(sbSQL.toString());
			transPS.setLong(1, aInfo.getNOfficeId());
			transPS.setLong(2, aInfo.getNCurrencyId());
			transPS.setLong(3, aInfo.getTransType());
			transPS.setLong(4, bankId);

			executeQuery();

			if (transRS.next()) {
				lReturn = transRS.getLong(1);
			}

		} catch (Exception e) {
			throw new ITreasuryDAOException("查询指令设置出现异常", e);
		} finally {
			finalizeDAO();
		}
		return lReturn;
	}

	public void deleteByIds(String[] ck) throws ITreasuryDAOException {
		long[] ids = new long[ck.length];
		for (int i = 0; i < ck.length; i++) {
			ids[i] = Long.valueOf(ck[i]).longValue();
		}
		// To be modify the delete status defined in Constant
		delete(ids);

	}

	/**
	 * 数据库更新操作操作
	 * 
	 * @param id
	 * @param statusID
	 *            需要更新到的状态
	 * @return
	 * @throws ITreasuryDAOException
	 */
	public void delete(long[] ids) throws ITreasuryDAOException {
		try {
			initDAO();
			StringBuffer buffer = new StringBuffer();
			buffer.append("delete \n");
			buffer.append(strTableName);
			buffer.append("\n  WHERE ID in (");
			for (int i = 0; i < ids.length; i++) {
				buffer.append("" + ids[i] + ",");
			}

			String strSQL = buffer.toString();
			strSQL = strSQL.substring(0, strSQL.length() - 1);
			strSQL += ")";
			log.debug(strSQL);
			prepareStatement(strSQL);
			executeQuery();
			finalizeDAO();
		} catch (ITreasuryDAOException e) {
			throw new ITreasuryDAOException("状态更新异常", e);
		}

	}

	public Collection getAllBankSetValue(BankInstructionSettingInfo bInfo)
			throws ITreasuryDAOException {
		Collection coll = new ArrayList();
		// BankInstructionSettingInfo bSetInfo = null;
		try {
			initDAO();
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" select a.*,b.sname inputuserName ");
			sbSQL.append(" from sett_bankinstructionset a,userinfo b ");
			sbSQL.append("where 1=1 ");
			sbSQL.append(" and  a.inputuserid=b.id");
			if (bInfo.getNOfficeId() > 0) {
				sbSQL.append(" and a.NOFFICEID=" + bInfo.getNOfficeId());
			}
			if (bInfo.getNCurrencyId() > 0) {
				sbSQL.append(" and a.NCURRENCYID=" + bInfo.getNCurrencyId());
			}
			if (bInfo.getAccountModule() > 0) {
				sbSQL
						.append(" and a.ACCOUNTMODULE="
								+ bInfo.getAccountModule());
			}
			if (bInfo.getTransType() > 0) {
				sbSQL.append(" and a.TRANSTYPE=" + bInfo.getTransType());
			}
			if (bInfo.getTransModule() > 0) {
				sbSQL.append(" and a.TRANSMODULE=" + bInfo.getTransModule());
			}
			sbSQL.append(" order by INPUTDATE desc,TRANSTYPE desc");
			System.out.print("123:"+sbSQL);
			log.info(sbSQL.toString());
			prepareStatement(sbSQL.toString());
			executeQuery();

			try {
				while (transRS.next()) {
					BankInstructionSettingInfo bSetInfo = new BankInstructionSettingInfo();
					bSetInfo.setId(transRS.getLong("Id"));
					bSetInfo.setNCurrencyId(transRS.getLong("NCurrencyId"));
					bSetInfo.setNOfficeId(transRS.getLong("NOfficeId"));
					bSetInfo.setTransType(transRS.getLong("TransType"));
					bSetInfo.setAccountModule(transRS.getLong("AccountModule"));
					bSetInfo.setIsSend(transRS.getLong("IsSend"));
					bSetInfo.setInputUserID(transRS.getLong("InputUserID"));
					bSetInfo.setInputDate(transRS.getTimestamp("InputDate"));
					bSetInfo.setStatusId(transRS.getLong("StatusId"));
					bSetInfo.setInputuserName(transRS
							.getString("InputuserName"));
					bSetInfo.setTransModule(transRS.getLong("TransModule"));
					coll.add(bSetInfo);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (ITreasuryDAOException e) {
			throw new ITreasuryDAOException("查找银行指令设置异常", e);
		} finally {
			finalizeDAO();
		}
		return coll;
	}

	/**
	 * 根据账户ID得到指令设置信息
	 * 
	 * @param accountId
	 * @return BankInstructionSettingInfo
	 * @throws ITreasuryDAOException
	 */
	public BankInstructionSettingInfo getSettingInfoByAccountId(
			BankInstructionSettingInfo bankInstructionSettingQueryInfo)
			throws ITreasuryDAOException {
		BankInstructionSettingInfo _bankInstructionSettingInfo = new BankInstructionSettingInfo();
		try {
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" select ");
			sbSQL.append("     a.* , c.payModule payModule ");
			sbSQL.append(" from ").append(TABEL_NAME).append(" a,");
			sbSQL.append("     SETT_ACCOUNT b,");
			sbSQL.append("     SETT_ACCOUNTTYPE c ");
			sbSQL.append(" where ");
			sbSQL.append("     a.nOfficeId = ?   ");
			sbSQL.append("     and a.nCurrencyId = ? ");
			sbSQL.append("     and a.transType = ?   ");
			sbSQL.append("     and b.id = ? ");
			sbSQL.append("     and c.id = b.nAccountTypeId ");
			sbSQL.append("     and a.accountModule = c.accountModule ");

			if (bankInstructionSettingQueryInfo.getTransModule() > 0)
				sbSQL.append(" and a.transModule = ?  ");

			initDAO();
			prepareStatement(sbSQL.toString());

			int i = 0;

			transPS
					.setLong(++i, bankInstructionSettingQueryInfo
							.getNOfficeId());
			transPS.setLong(++i, bankInstructionSettingQueryInfo
					.getNCurrencyId());
			transPS
					.setLong(++i, bankInstructionSettingQueryInfo
							.getTransType());
			transPS
					.setLong(++i, bankInstructionSettingQueryInfo
							.getAccountId());

			if (bankInstructionSettingQueryInfo.getTransModule() > 0)
				transPS.setLong(++i, bankInstructionSettingQueryInfo
						.getTransModule());

			executeQuery();

			_bankInstructionSettingInfo = (BankInstructionSettingInfo) getDataEntityFromResultSet(BankInstructionSettingInfo.class);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ITreasuryDAOException("查询指令设置出现异常", e);
		} finally {
			finalizeDAO();
		}
		return _bankInstructionSettingInfo;
	}

	/**
	 * 根据账户ID得到指令设置信息
	 * 
	 * @param accountId
	 * @return BankInstructionSettingInfo
	 * @throws ITreasuryDAOException
	 */
	public BankInstructionSettingInfo getSettingInfoByTransType(
			BankInstructionSettingInfo bankInstructionSettingQueryInfo)
			throws ITreasuryDAOException {
		BankInstructionSettingInfo _bankInstructionSettingInfo = new BankInstructionSettingInfo();
		try {
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" select ");
			sbSQL.append("     *  ");
			sbSQL.append(" from ").append(TABEL_NAME);
			sbSQL.append(" where ");
			sbSQL.append("     nOfficeId = ?   ");
			sbSQL.append("     and nCurrencyId = ? ");
			sbSQL.append("     and transType = ?   ");

			if (bankInstructionSettingQueryInfo.getTransModule() > 0)
				sbSQL.append(" and transModule = ?  ");

			initDAO();
			prepareStatement(sbSQL.toString());

			int i = 0;

			transPS
					.setLong(++i, bankInstructionSettingQueryInfo
							.getNOfficeId());
			transPS.setLong(++i, bankInstructionSettingQueryInfo
					.getNCurrencyId());
			transPS
					.setLong(++i, bankInstructionSettingQueryInfo
							.getTransType());

			if (bankInstructionSettingQueryInfo.getTransModule() > 0)
				transPS.setLong(++i, bankInstructionSettingQueryInfo
						.getTransModule());

			executeQuery();

			_bankInstructionSettingInfo = (BankInstructionSettingInfo) getDataEntityFromResultSet(BankInstructionSettingInfo.class);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ITreasuryDAOException("查询指令设置出现异常", e);
		} finally {
			finalizeDAO();
		}
		return _bankInstructionSettingInfo;
	}


	public String queryBankInstructionSetting(QueryBranchInfo qbInfo) {
		StringBuffer sbSQL = new StringBuffer();
		sbSQL.append(" select a.*,b.sname inputuserName,(case ISSEND WHEN 1 THEN '是' WHEN 0 THEN '否' ELSE '' END) as STRISSEND ");
		sbSQL.append(" from sett_bankinstructionset a,userinfo b ");
		sbSQL.append("where 1=1 ");
		sbSQL.append(" and  a.inputuserid=b.id");
		if (qbInfo.getOfficeID()> 0) {
			sbSQL.append(" and a.NOFFICEID=" + qbInfo.getOfficeID());
		}
		if (qbInfo.getCurrencyID()> 0) {
			sbSQL.append(" and a.NCURRENCYID=" + qbInfo.getCurrencyID());
		}
		if (qbInfo.getAccountModule() > 0) {
			sbSQL.append(" and a.ACCOUNTMODULE=" + qbInfo.getAccountModule());
		}
		if (qbInfo.getBankType() > 0) {
			sbSQL.append(" and a.TRANSTYPE=" + qbInfo.getBankType());
		}
		if (qbInfo.getAccountModule() > 0) {
			sbSQL.append(" and a.TRANSMODULE=" + qbInfo.getAccountModule());
		}
		sbSQL.append(" order by INPUTDATE desc,TRANSTYPE desc");
		
		return sbSQL.toString();
		
	}

}
