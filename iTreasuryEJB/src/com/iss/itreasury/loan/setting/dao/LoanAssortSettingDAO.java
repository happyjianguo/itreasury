package com.iss.itreasury.loan.setting.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.loan.base.LoanDAO;
import com.iss.itreasury.loan.setting.dataentity.LoanAssortSettingInfo;
import com.iss.itreasury.settlement.base.SettlementDAO;
import com.iss.itreasury.settlement.setting.dataentity.BankInstructionSettingInfo;
import com.iss.itreasury.util.Constant;

public class LoanAssortSettingDAO extends LoanDAO {
	public static String TABEL_NAME = "loan_assortsetting";

	public LoanAssortSettingDAO() {
		super(TABEL_NAME);
	}

	public LoanAssortSettingDAO(String tableName, Connection conn) {
		super(tableName, conn);
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
	
	/**
	 * 通过类型ID得到类型名称
	 * 
	 * @param assortId
	 * 
	 *            需要更新到的状态
	 * @return Collection
	 * @throws ITreasuryDAOException
	 */
	public Collection getAssortNameAndIDByAssortId(long assortId) throws ITreasuryDAOException {
		Collection coll = new ArrayList();
		try{
			initDAO();
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" select a.name assortName,a.id id");
			sbSQL.append(" from loan_assortsetting a ");
			sbSQL.append("where 1=1 ");
			sbSQL.append(" and  a.ASSORTTYPEID = " + assortId);
			sbSQL.append(" and  a.STATUSID = " + Constant.TRUE);
			sbSQL.append(" order by a.id asc");
			prepareStatement(sbSQL.toString());
			executeQuery();
			try {
				while (transRS.next()) {
					LoanAssortSettingInfo bLoanInfo = new LoanAssortSettingInfo();
					bLoanInfo.setName(transRS.getString("assortName"));
					bLoanInfo.setId(transRS.getLong("id"));
					coll.add(bLoanInfo);
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
	
	public Collection getAssortNameAndIDByAssort(long officeId, long currencyId, long typeId) throws ITreasuryDAOException {
		Collection coll = new ArrayList();
		try{
			initDAO();
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" select a.name assortName,a.id id");
			sbSQL.append(" from loan_assortsetting a ");
			sbSQL.append(" where 1=1 ");
			sbSQL.append(" and a.ASSORTTYPEID = " + typeId);
			sbSQL.append(" and a.officeid = " + officeId);
			sbSQL.append(" and a.currencyid = " + currencyId);
			sbSQL.append(" and a.STATUSID = " + Constant.TRUE);
			sbSQL.append(" order by a.id asc");
			prepareStatement(sbSQL.toString());
			executeQuery();
			try {
				while (transRS.next()) {
					LoanAssortSettingInfo bLoanInfo = new LoanAssortSettingInfo();
					bLoanInfo.setName(transRS.getString("assortName"));
					bLoanInfo.setId(transRS.getLong("id"));
					coll.add(bLoanInfo);
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
	 * 通过类型ID得到类型名称
	 * 
	 * @param assortId
	 * 
	 *            需要更新到的状态
	 * @return Collection
	 * @throws ITreasuryDAOException
	 */
	public String getAssortNameByID(long Id) throws ITreasuryDAOException {
		String result = "";
		try{
			initDAO();
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" select a.name assortName");
			sbSQL.append(" from loan_assortsetting a ");
			sbSQL.append("where 1=1 ");
			sbSQL.append(" and  a.id = " + Id);
			sbSQL.append(" and  a.STATUSID = " + Constant.TRUE);
			sbSQL.append(" order by a.id desc");
			prepareStatement(sbSQL.toString());
			executeQuery();
			try {
				while (transRS.next()) {
					result = transRS.getString("assortName");
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
		return result;
	}
	
	public Collection getAllLoanAssortSetValue(LoanAssortSettingInfo bInfo)
			throws ITreasuryDAOException {
		Collection coll = new ArrayList();
		// BankInstructionSettingInfo bSetInfo = null;
		try {
			initDAO();
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" select a.*,b.sname inputuserName,a.Name assortName");
			sbSQL.append(" from loan_assortsetting a,userinfo b ");
			sbSQL.append("where 1=1 ");
			sbSQL.append(" and  a.inputuserid=b.id");
			if (bInfo.getOfficeId() > 0) {
				sbSQL.append(" and a.OFFICEID=" + bInfo.getOfficeId());
			}
			if (bInfo.getCurrencyId() > 0) {
				sbSQL.append(" and a.CURRENCYID=" + bInfo.getCurrencyId());
			}
			if (bInfo.getName()!="") {
				sbSQL.append(" and a.NAME='" + bInfo.getName()+"'");
			}
			if (bInfo.getAssortTypeId() > 0) {
				sbSQL.append(" and a.ASSORTTYPEID=" + bInfo.getAssortTypeId());
			}
			sbSQL.append(" order by INPUTDATE desc");
			log.info(sbSQL.toString());
			prepareStatement(sbSQL.toString());
			executeQuery();
 
			try {
				while (transRS.next()) {
					LoanAssortSettingInfo bLoanInfo = new LoanAssortSettingInfo();
					bLoanInfo.setId(transRS.getLong("Id"));
					bLoanInfo.setCurrencyId(transRS.getLong("CurrencyId"));
					bLoanInfo.setOfficeId(transRS.getLong("OfficeId"));
					bLoanInfo.setAssortTypeId(transRS.getLong("ASSORTTYPEID"));
					bLoanInfo.setName(transRS.getString("assortName"));
					bLoanInfo.setInputUserID(transRS.getLong("InputUserID"));
					bLoanInfo.setInputDate(transRS.getTimestamp("InputDate"));
					bLoanInfo.setStatusId(transRS.getLong("StatusId"));
					bLoanInfo.setInputuserName(transRS.getString("InputuserName"));
					coll.add(bLoanInfo);
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
	
}
