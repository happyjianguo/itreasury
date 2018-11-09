package com.iss.itreasury.settlement.accountsystem.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.settlement.accountsystem.dataentity.AccountRelationSettingInfo;
import com.iss.itreasury.settlement.accountsystem.dataentity.AccountSystemSettingInfo;
import com.iss.itreasury.settlement.accountsystem.util.AccountSystemConstant;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.ITreasuryBaseDataEntity;

public class AccountSystemDAO extends ITreasuryDAO {

	public final static String TABLENAME_SET = "sett_accountsystemsetting";

	public final static String TABLENAME_RELATION = "sett_accountrelationsetting";

	public AccountSystemDAO() {
		super(AccountSystemDAO.TABLENAME_SET);
	}
	
	/**
	 * 根据父帐户ID查找体系信息，并且为不删除状态
	 * nAccountId 是一个可选参数
	 * 
	 * @param nAccountSystemId
	 * @param nAccountId
	 * @return
	 * @throws ITreasuryDAOException
	 * @throws SQLException 
	 */	
	public Collection findByInfoAccountSystem(AccountSystemSettingInfo info) throws ITreasuryDAOException, SQLException{
		initDAO();
		StringBuffer buffer = new StringBuffer();
		buffer.append("select t.* from "+ strTableName +" t \n");
		buffer.append("where t.nofficeid = "+ info.getNOfficeId() +" \n");
		buffer.append("and t.ncurrencyid = "+ info.getNCurrencyId() +" \n");
		buffer.append("and t.nstatusid != "+ AccountSystemConstant.SettingStatus.DELETED +" \n");
		if(info.getId() > 0){
			buffer.append("and t.id = " + info.getId() + " \n");
		}
		if(info.getNUpAccountId() > 0){
			buffer.append("and t.nupaccountid = " + info.getNUpAccountId() + " \n");
		}
		if(info.getNClientId() > 0){
			buffer.append("and t.nclientid = "+ info.getNClientId() +" \n");
		}

		prepareStatement( buffer.toString());		
		executeQuery();

		Collection coll = new ArrayList();
		
		while(transRS.next()){
			AccountSystemSettingInfo assInfo = new AccountSystemSettingInfo();
			assInfo.setId(transRS.getLong("ID"));
			assInfo.setSystemCode(transRS.getString("SYSTEMCODE"));
			assInfo.setSystemName(transRS.getString("SYSTEMNAME"));
			assInfo.setNUpAccountId(transRS.getLong("NUPACCOUNTID"));
			assInfo.setNStatusId(transRS.getLong("NSTATUSID"));
			assInfo.setNOfficeId(transRS.getLong("NOFFICEID"));
			assInfo.setNCurrencyId(transRS.getLong("NCURRENCYID"));
			assInfo.setNClientId(transRS.getLong("NCLIENTID"));
			assInfo.setDtInputTime(transRS.getTimestamp("DTINPUTTIME"));
			assInfo.setNInputUserId(transRS.getLong("NINPUTUSERID"));
			assInfo.setDtModifyTime(transRS.getTimestamp("DTMODIFYTIME"));
			assInfo.setNModifyUserId(transRS.getLong("NMODIFYUSERID"));
			coll.add(assInfo);
		}
		
		finalizeDAO();
		
		if(coll.size() == 0){
			return null;
		}
		else {
			return coll;
		}
	}
	
	/**
	 * 联合查询，并显示结果
	 * nAccountId 是一个可选参数
	 * 
	 * @param nAccountSystemId
	 * @param nAccountId
	 * @return
	 * @throws ITreasuryDAOException
	 * @throws SQLException 
	 */	
	public AccountSystemSettingInfo findByID(long id) throws ITreasuryDAOException, SQLException{
		initDAO();
		StringBuffer buffer = new StringBuffer();
		buffer.append("select a.*,b.saccountno accountcode, b.sname accountname \n");
		buffer.append("from "+ strTableName +" a, sett_account b \n");
		buffer.append("where a.nstatusid != "+ AccountSystemConstant.SettingStatus.DELETED +" \n");
		buffer.append("and a.id = "+ id +" \n");
		buffer.append("and a.nupaccountid = b.id \n");

		prepareStatement( buffer.toString());		
		executeQuery();

		AccountSystemSettingInfo assInfo = new AccountSystemSettingInfo();
		
		while(transRS.next()){
			assInfo.setId(transRS.getLong("ID"));
			assInfo.setSystemCode(transRS.getString("SYSTEMCODE"));
			assInfo.setSystemName(transRS.getString("SYSTEMNAME"));
			assInfo.setNUpAccountId(transRS.getLong("NUPACCOUNTID"));
			assInfo.setNStatusId(transRS.getLong("NSTATUSID"));
			assInfo.setNOfficeId(transRS.getLong("NOFFICEID"));
			assInfo.setNCurrencyId(transRS.getLong("NCURRENCYID"));
			assInfo.setNClientId(transRS.getLong("NCLIENTID"));
			assInfo.setDtInputTime(transRS.getTimestamp("DTINPUTTIME"));
			assInfo.setNInputUserId(transRS.getLong("NINPUTUSERID"));
			assInfo.setDtModifyTime(transRS.getTimestamp("DTMODIFYTIME"));
			assInfo.setNModifyUserId(transRS.getLong("NMODIFYUSERID"));
			assInfo.setAccountCode(transRS.getString("accountcode"));
			assInfo.setAccountName(transRS.getString("accountname"));
		}
		
		finalizeDAO();
		
		return assInfo;
	}
	
	/**
	 * 根据下级账户取得上级账户
	 * 
	 * @param accountId
	 * @return
	 * @throws ITreasuryDAOException
	 */
	public long findUpAccountIdByChildAccountId(long childAccountId)
			throws ITreasuryDAOException {
		long lReturn = -1;
		try {
			int i = 0;
			StringBuffer sbSQL = new StringBuffer("");
			sbSQL.append(" select ");
			sbSQL.append("      b.nupaccountid superAccountId");
			sbSQL.append(" from ");
			sbSQL.append(		TABLENAME_RELATION).append(" a,");
			sbSQL.append(		TABLENAME_SET).append(" b ");
			sbSQL.append(" where ");
			sbSQL.append("     a.naccountid = ? ");
			sbSQL.append("     and b.id = a.naccountsystemid ");
			sbSQL.append("     and a.nstatusid != ? ");
			sbSQL.append("     and b.nstatusid != ? ");

			initDAO();
			prepareStatement(sbSQL.toString());

			transPS.setLong(++i, childAccountId);
			transPS.setLong(++i, AccountSystemConstant.RelationStatus.DELETED);
			transPS.setLong(++i, AccountSystemConstant.SettingStatus.DELETED);

			executeQuery();

			if (transRS.next())
				lReturn = transRS.getLong("superAccountId");

		} catch (Exception e) {
			throw new ITreasuryDAOException(e.getMessage(), e);
		} finally {
			finalizeDAO();
		}
		return lReturn;
	}

	/**
	 * 通过上级账户ＩＤ取得体系ＩＤ
	 * 
	 * @param upAccountId
	 * @return
	 * @throws ITreasuryDAOException
	 */
	public long findSystemIdByUpAccoutId(long upAccountId)
			throws ITreasuryDAOException {
		long lReturn = -1;
		try {
			int i = 0;
			StringBuffer sbSQL = new StringBuffer("");
			sbSQL.append(" select ");
			sbSQL.append("      id systemId");
			sbSQL.append(" from ");
			sbSQL.append(		TABLENAME_SET);
			sbSQL.append(" where ");
			sbSQL.append("     nupaccountid = ? ");
			sbSQL.append("     and nstatusid != ? ");
			sbSQL.append("     and nstatusid != ? ");

			initDAO();
			prepareStatement(sbSQL.toString());

			transPS.setLong(++i, upAccountId);
			transPS.setLong(++i, AccountSystemConstant.SettingStatus.DELETED);
			transPS.setLong(++i, AccountSystemConstant.SettingStatus.SAVE);

			executeQuery();

			if (transRS.next())
				lReturn = transRS.getLong("systemId");

		} catch (Exception e) {
			throw new ITreasuryDAOException(e.getMessage(), e);
		} finally {
			finalizeDAO();
		}
		return lReturn;
	}

	/**
	 * 通过上级账户ＩＤ取得体系ＩＤ
	 * 
	 * @param upAccountId
	 * @return
	 * @throws ITreasuryDAOException
	 */
	public long findSystemIdByAccoutId(long accountId)
			throws ITreasuryDAOException {
		long lReturn = -1;
		try {
			int i = 0;
			StringBuffer sbSQL = new StringBuffer("");
/*			sbSQL.append(" select ");
			sbSQL.append("      a.id systemId");
			sbSQL.append(" from ");
			sbSQL.append(		TABLENAME_SET + " a,");
			sbSQL.append(		TABLENAME_RELATION  + " b");
			sbSQL.append(" where ");
			sbSQL.append("     (b.naccountid = ? or a.nupaccountid = ?) ");
			sbSQL.append("     and b.naccountsystemid = a.id  ");
			sbSQL.append("     and a.nstatusid != ? ");
			sbSQL.append("     and a.nstatusid != ? ");
			sbSQL.append("     and b.nstatusid != ? ");
			sbSQL.append("     and b.nstatusid != ? ");
			sbSQL.append("     and rownum < 2 ");*/
			
			sbSQL.append(" 			select sas.id systemId 								");
			sbSQL.append(" 			from " + TABLENAME_SET + " sas 						");
			sbSQL.append(" 			where sas.nupaccountid = ? 							");
			sbSQL.append(" 		 	  	and sas.nstatusid != ? and sas.nstatusid != ?   ");
			sbSQL.append(" 		union 													");
			sbSQL.append(" 			select sar.naccountsystemid  systemId 				");
			sbSQL.append(" 		    from " + TABLENAME_RELATION + " sar 				");
			sbSQL.append(" 		    where sar.naccountid = ? 							");
			sbSQL.append(" 		          and sar.nstatusid != ? and sar.nstatusid != ? ");

			initDAO();
			prepareStatement(sbSQL.toString());

			transPS.setLong(++i, accountId);
			transPS.setLong(++i, AccountSystemConstant.SettingStatus.DELETED);
			transPS.setLong(++i, AccountSystemConstant.SettingStatus.SAVE);
			transPS.setLong(++i, accountId);
			transPS.setLong(++i, AccountSystemConstant.RelationStatus.DELETED);
			transPS.setLong(++i, AccountSystemConstant.RelationStatus.SAVE);

			executeQuery();

			if (transRS.next())
				lReturn = transRS.getLong("systemId");

		} catch (Exception e) {
			throw new ITreasuryDAOException(e.getMessage(), e);
		} finally {
			finalizeDAO();
		}
		return lReturn;
	}
	
	/**
	 * 根据下级账户ＩＤ取得体系ＩＤ
	 * 
	 * @param subAccountId
	 * @return
	 * @throws ITreasuryDAOException
	 */
	public long findSystemIdByChildAccoutId(long childAccountId)
			throws ITreasuryDAOException {
		long lReturn = -1;
		try {
			int i = 0;
			StringBuffer sbSQL = new StringBuffer("");
			sbSQL.append(" select ");
			sbSQL.append("      naccountsystemid systemId");
			sbSQL.append(" from ");
			sbSQL.append(		TABLENAME_RELATION);
			sbSQL.append(" where ");
			sbSQL.append("     naccountid = ? ");
			sbSQL.append("     and nstatusid != ? ");

			initDAO();
			prepareStatement(sbSQL.toString());

			transPS.setLong(++i, childAccountId);
			transPS.setLong(++i, AccountSystemConstant.RelationStatus.DELETED);

			executeQuery();

			if (transRS.next())
				lReturn = transRS.getLong("superAccountId");

		} catch (Exception e) {
			throw new ITreasuryDAOException(e.getMessage(), e);
		} finally {
			finalizeDAO();
		}
		return lReturn;
	}

	/**
	 * 查询其它下级账户合计透支金额
	 * 
	 * @param systemId
	 * @param subAccountId
	 * @return
	 * @throws ITreasuryDAOException
	 */
	public double sumOtherChildAccountOverDraftBalance(long systemId,
			long childAccountId) throws ITreasuryDAOException {
		double dReturn = 0.00d;
		try {
			int i = 0;
			StringBuffer sbSQL = new StringBuffer("");
			sbSQL.append(" select ");
			sbSQL.append(" 		nvl(sum(nvl(ss.mbalance,0)- nvl(ss.muncheckpaymentamount,0)),0) as mbalance ");
			sbSQL.append(" from ");
			sbSQL.append(		TABLENAME_RELATION).append(" sas ");
			sbSQL.append("		,sett_account sa,sett_subaccount ss");
			sbSQL.append("	where ");
			sbSQL.append("    	sas.naccountsystemid = ?  ");
			sbSQL.append("      and sas.naccountid = sa.id ");
			sbSQL.append("      and ss.naccountid = sa.id ");
			sbSQL.append("      and sas.naccountid != ? ");
			sbSQL.append("      and ss.mbalance-ss.muncheckpaymentamount < 0");
			sbSQL.append("      and (sas.nstatusid = ?  or   sas.nstatusid = ? )");

			initDAO();
			prepareStatement(sbSQL.toString());

			transPS.setLong(++i, systemId);
			transPS.setLong(++i, childAccountId);
			transPS.setLong(++i, AccountSystemConstant.RelationStatus.CHECKED);
			transPS.setLong(++i, AccountSystemConstant.RelationStatus.DELETING);

			executeQuery();

			if (transRS.next())
				dReturn = transRS.getDouble("mbalance");

		} catch (Exception e) {
			throw new ITreasuryDAOException(e.getMessage(), e);
		} finally {
			finalizeDAO();
		}
		return dReturn;
	}
	
	
	/**
	 * 查询其它下级账户合计透支金额
	 * 
	 * @param systemId
	 * @param subAccountId
	 * @return
	 * @throws ITreasuryDAOException
	 */
	public double sumOtherChildAccountOverDraftBalance(long childAccountId) throws ITreasuryDAOException {
		double dReturn = 0.00d;
		try {
			int i = 0;
			StringBuffer sbSQL = new StringBuffer("");
			sbSQL.append(" select ");
			sbSQL.append(" 		nvl(sum(nvl(ss.mbalance,0)- nvl(ss.muncheckpaymentamount,0)),0) as mbalance ");
			sbSQL.append(" from ");
			sbSQL.append(		TABLENAME_RELATION).append(" sas ");
			sbSQL.append("		,sett_account sa,sett_subaccount ss");
			sbSQL.append("	where ");
			sbSQL.append("    	sas.naccountsystemid = (select naccountsystemid  from " +TABLENAME_RELATION +" where naccountid = ? and nstatusid != ?)  ");
			sbSQL.append("      and sas.naccountid = sa.id ");
			sbSQL.append("      and ss.naccountid = sa.id ");
			sbSQL.append("      and sas.naccountid != ? ");
			sbSQL.append("      and ss.mbalance-ss.muncheckpaymentamount < 0");
			sbSQL.append("      and (sas.nstatusid = ?  or   sas.nstatusid = ? )");

			initDAO();
			prepareStatement(sbSQL.toString());

			transPS.setLong(++i, childAccountId);
			transPS.setLong(++i, AccountSystemConstant.RelationStatus.DELETED);
			transPS.setLong(++i, childAccountId);
			transPS.setLong(++i, AccountSystemConstant.RelationStatus.CHECKED);
			transPS.setLong(++i, AccountSystemConstant.RelationStatus.DELETING);

			executeQuery();

			if (transRS.next())
				dReturn = transRS.getDouble("mbalance");

		} catch (Exception e) {
			throw new ITreasuryDAOException(e.getMessage(), e);
		} finally {
			finalizeDAO();
		}
		return dReturn;
	}
	

	
	/**
	 * 查询所有下级账户合计透支金额
	 * 
	 * @param systemId
	 * @param subAccountId
	 * @return
	 * @throws ITreasuryDAOException
	 */
	public double sumAllChildAccountOverDraftBalance(long systemId) throws ITreasuryDAOException {
		double dReturn = 0.00d;
		try {
			int i = 0;
			StringBuffer sbSQL = new StringBuffer("");
			sbSQL.append(" select ");
			sbSQL.append(" 		nvl(sum(nvl(ss.mbalance,0)- nvl(ss.muncheckpaymentamount,0)),0) as mbalance ");
			sbSQL.append(" from ");
			sbSQL.append(		TABLENAME_RELATION).append(" sas ");
			sbSQL.append("		,sett_account sa,sett_subaccount ss");
			sbSQL.append("	where ");
			sbSQL.append("    	sas.naccountsystemid = ?  ");
			sbSQL.append("      and sas.naccountid = sa.id ");
			sbSQL.append("      and ss.naccountid = sa.id ");
			sbSQL.append("      and ss.mbalance-ss.muncheckpaymentamount < 0");
			sbSQL.append("      and (sas.nstatusid = ?  or   sas.nstatusid = ? )");

			initDAO();
			prepareStatement(sbSQL.toString());

			transPS.setLong(++i, systemId);
			transPS.setLong(++i, AccountSystemConstant.RelationStatus.CHECKED);
			transPS.setLong(++i, AccountSystemConstant.RelationStatus.DELETING);

			executeQuery();

			if (transRS.next())
				dReturn = transRS.getDouble("mbalance");

		} catch (Exception e) {
			throw new ITreasuryDAOException(e.getMessage(), e);
		} finally {
			finalizeDAO();
		}
		return dReturn;
	}
	/**
	 * 查询账户自已本身的可用余额
	 * @param accountId
	 * @return
	 * @throws ITreasuryDAOException
	 */
	public double findAccountSelfCanUseBalance(long accountId) throws ITreasuryDAOException{
		double dReturn  = 0.00d;
		try {
			int i = 0;
			StringBuffer sbSQL = new StringBuffer("");
			sbSQL.append(" select ");
			sbSQL.append(" 		nvl(ss.mbalance,0)- nvl(ss.muncheckpaymentamount,0) as mbalance ");
			sbSQL.append(" from ");
			sbSQL.append("		sett_account sa,sett_subaccount ss");
			sbSQL.append("	where ");
			sbSQL.append("      ss.naccountid = sa.id ");
			sbSQL.append("      and sa.id = ? ");

			initDAO();
			prepareStatement(sbSQL.toString());

			transPS.setLong(++i, accountId);

			executeQuery();

			if (transRS.next())
				dReturn = transRS.getDouble("mbalance");

		} catch (Exception e) {
			throw new ITreasuryDAOException(e.getMessage(), e);
		} finally {
			finalizeDAO();
		}
		return dReturn;		
	}
	
	/**
	 * 得到办事处所有透支账户
	 * @param officeId
	 * @param currencyId
	 * @return
	 * @throws ITreasuryDAOException
	 */
	public String[] findSystemOverDraftAcountList(long officeId,long currencyId) throws ITreasuryDAOException{
		String[] strReturn = null;
		try {
			int i = 0;
			StringBuffer sbSQL = new StringBuffer("");
			sbSQL.append(" select  														");
			sbSQL.append(" 		sa.saccountno accountno									");
			sbSQL.append(" from ( 														");
			sbSQL.append(" 			select sas.nupaccountid accountid 					");
			sbSQL.append(" 			from " + TABLENAME_SET + " sas 						");
			sbSQL.append(" 			where sas.nofficeid = ? 							");
			sbSQL.append(" 		      	and sas.ncurrencyid = ? 						");
			sbSQL.append(" 		 	  	and sas.nstatusid != ? and sas.nstatusid != ?   ");
			sbSQL.append(" 		union 													");
			sbSQL.append(" 			select sar.naccountid accountid 					");
			sbSQL.append(" 		    from " + TABLENAME_RELATION + " sar 				");
			sbSQL.append(" 		    where sar.nofficeid = ? 							");
			sbSQL.append(" 		          and sar.ncurrencyid = ? 						");
			sbSQL.append(" 		          and sar.nstatusid != ? and sar.nstatusid != ? ");
			sbSQL.append(" 		 ) aa ,sett_subaccount ss,sett_account sa 				");
			sbSQL.append(" where    													");
			sbSQL.append(" 		aa.accountid = sa.id 									");
			sbSQL.append(" 		and ss.naccountid = sa.id 								");
			sbSQL.append(" 		and ss.mbalance < 0 									");
					   

			initDAO();
			prepareStatement(sbSQL.toString());

			transPS.setLong(++i, officeId);
			transPS.setLong(++i, currencyId);
			transPS.setLong(++i, AccountSystemConstant.SettingStatus.DELETED);
			transPS.setLong(++i, AccountSystemConstant.SettingStatus.SAVE);
			transPS.setLong(++i, officeId);
			transPS.setLong(++i, currencyId);
			transPS.setLong(++i, AccountSystemConstant.RelationStatus.DELETED);
			transPS.setLong(++i, AccountSystemConstant.RelationStatus.SAVE);

			executeQuery();
			List lstRs = new ArrayList();
			while (transRS.next()) {
				lstRs.add(transRS.getString("accountno"));
			}
			
			if (lstRs.size() > 0) {
				strReturn = new String[lstRs.size()];
				for (int k = 0; k < lstRs.size(); k++) {
					strReturn[k] = lstRs.get(k).toString();
				}
			}

		} catch (Exception e) {
			throw new ITreasuryDAOException(e.getMessage(), e);
		} finally {
			finalizeDAO();
		}
		return strReturn;		
	}
}
