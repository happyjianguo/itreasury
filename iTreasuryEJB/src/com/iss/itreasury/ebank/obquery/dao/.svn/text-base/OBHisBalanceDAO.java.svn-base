package com.iss.itreasury.ebank.obquery.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Vector;

import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.IDate;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.ebank.obquery.dataentity.OBQueryAccInfo;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.ebank.util.NameRef;
import com.iss.itreasury.ebank.util.OBConstant;
import com.iss.itreasury.ebank.util.OBOperation;
import com.iss.itreasury.util.ITreasuryException;
import com.iss.itreasury.util.Constant;
import com.iss.system.dao.PageLoader;

/**
 * 历史余额查询：包括汇总历史余额查询和下属单位历史余额查询 写具体的查询语句，返回包含OBHisBalanceResultInfo对象的集合
 * 
 * @author liuyang
 *  
 */
public class OBHisBalanceDAO extends ITreasuryDAO {

	public OBHisBalanceDAO() {
		super("");
	}

	/** 定义SQL语句变量* */
	private StringBuffer sbWhere = null;

	private StringBuffer sbFrom = null;

	private StringBuffer sbSelect = null;

	private StringBuffer sbOrderBy = null;

	private StringBuffer sbunion = null;

	String strBankAccountid = "";

	String strCurrencyAcctID = "";

	String strFixedAcctID = "";

	String strLoanAcctID = "";

	String strtmp = "";

	/**
	 * 汇总账户余额查询-历史余额查询 银行接口历史余额 bs_accthisbalance 信托贷款 transtrustgrantloan 委托贷款
	 * transconsigngrantloan 定期 transfixeddeposit
	 * 备注：得到的记录如果不符合显示的要求，可以在另外一个类里做记录处理，得到最终想要的结果集
	 * 
	 * @param info
	 * @return
	 */
	public PageLoader QueryHistoryBalance(OBQueryAccInfo info)
			throws ITreasuryException {
		getSQL(info);

		/** 获取PageLoader* */
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory
				.getBaseObject("com.iss.system.dao.PageLoader");

		pageLoader
				.initPageLoader(
						new AppContext(),
						sbFrom.toString(),
						sbSelect.toString(),
						sbWhere.toString(),
						(int) Constant.PageControl.CODE_PAGELINECOUNT,
						"com.iss.itreasury.ebank.obquery.dataentity.OBHisBalanceResultInfo",
						null);

		pageLoader.setOrderBy(" " + sbOrderBy.toString() + " ");

		return pageLoader;
	}

	/**
	 * 下属单位账户信息查询-汇总账户历史余额查询 银行接口历史余额 bs_accthisbalance 结算活期accountBalance 信托贷款
	 * transtrustgrantloan 委托贷款 transconsigngrantloan 定期 transfixeddeposit
	 * 备注：得到的记录如果不符合显示的要求，可以在另外一个类里做记录处理，得到最终想要的结果集
	 * 
	 * @param info
	 * @return
	 */
	public PageLoader QueryClientHistoryBal(OBQueryAccInfo info)
			throws ITreasuryException {
		getSQL(info);

		/** 获取PageLoader* */
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory
				.getBaseObject("com.iss.system.dao.PageLoader");

		pageLoader
				.initPageLoader(
						new AppContext(),
						sbFrom.toString(),
						sbSelect.toString(),
						sbWhere.toString(),
						(int) Constant.PageControl.CODE_PAGELINECOUNT,
						"com.iss.itreasury.ebank.obquery.dataentity.OBHisBalanceResultInfo",
						null);

		pageLoader.setOrderBy(" " + sbOrderBy.toString() + " ");

		return pageLoader;
	}

	/**
	 * 透支查询 备注：得到的记录如果不符合显示的要求，可以在另外一个类里做记录处理，得到最终想要的结果集
	 * 
	 * @param info
	 * @return
	 */
	public Collection QueryOverdraft(OBQueryAccInfo info) {
		return null;
	}

	private void getacctbytypeid(OBQueryAccInfo info) {
		System.out.println("jianlaile");
		if (info.getAccountId() != null
				&& info.getAccountId().trim().length() > 0) {

			String[] strAcctID = DataFormat.splitString(info.getAccountId(),
					",");
			String[] strAccttypeid = DataFormat.splitString(info
					.getAccttypeid(), ",");

			for (int i = 0; i < strAccttypeid.length; i++) {
				if (Long.parseLong(strAccttypeid[i]) == OBConstant.AccountType.OUT) {
					strBankAccountid += strAcctID[i] + ",";
				}
				if (Long.parseLong(strAccttypeid[i]) == OBConstant.AccountType.IN) {
					strtmp += strAcctID[i] + ",";
				}
			}
			if (strBankAccountid.trim().length() > 0) {
				strBankAccountid = strBankAccountid.trim().substring(0,
						strBankAccountid.length() - 1);
			} else {
				strBankAccountid = "-1";
			}
			if (strtmp.trim().length() > 0) {
				strtmp = strtmp.trim().substring(0, strtmp.length() - 1);

				String[] strAccountID = DataFormat.splitString(strtmp, ",");

				for (int i = 0; i < strAccountID.length; i++) {
					//得到账户组
					long lGroupID = NameRef.getAcctGroupIDByAcctID(Long
							.parseLong(strAccountID[i]));
					switch ((int) lGroupID) {
					case ((int) SETTConstant.AccountGroupType.CURRENT):
						strCurrencyAcctID = strCurrencyAcctID + strAccountID[i]
								+ ",";
						break;
					case ((int) SETTConstant.AccountGroupType.FIXED):
						strFixedAcctID = strFixedAcctID + strAccountID[i] + ",";
						break;
					case ((int) SETTConstant.AccountGroupType.OTHERLOAN):
						strLoanAcctID = strLoanAcctID + strAccountID[i] + ",";
						break;
					default:
						break;
					}
				}
				//去掉最后逗号and变为数组
				if (strCurrencyAcctID != null && strCurrencyAcctID.length() > 0) {
					strCurrencyAcctID = strCurrencyAcctID.substring(0,
							strCurrencyAcctID.length() - 1);
				}
				if (strFixedAcctID != null && strFixedAcctID.length() > 0) {
					strFixedAcctID = strFixedAcctID.substring(0, strFixedAcctID
							.length() - 1);
				}
				if (strLoanAcctID != null && strLoanAcctID.length() > 0) {
					strLoanAcctID = strLoanAcctID.substring(0, strLoanAcctID
							.length() - 1);
				}
			}
		}

		System.out.println("---------------strBankAccountid||||||||||"
				+ strBankAccountid);
		System.out.println("---------------strCurrencyAcctID||||||||||"
				+ strCurrencyAcctID);
		System.out.println("---------------strFixedAcctID||||||||||"
				+ strFixedAcctID);
		System.out.println("---------------strLoanAcctID||||||||||"
				+ strLoanAcctID);
	}

	private void getSQL(OBQueryAccInfo info) throws ITreasuryException {

		Vector vRetrun = new Vector();
		Vector vTemp = new Vector();

		sbSelect = new StringBuffer();
		sbOrderBy = new StringBuffer();
		sbunion = new StringBuffer();
		sbFrom = new StringBuffer();
		sbWhere = new StringBuffer();

		/** 拼写查询条件串------------END* */
		sbSelect.append("  *  \n");
		/** sbFrom---拼写开始* */
		sbFrom.append("  ob_hisbalanceview v \n");

		/** sbFrom---拼写结束* */

		/** sbWhere---拼写开始* */
		if(info.getAccountId() != null && info.getAccountId().length() > 0){
			sbWhere.append(" v.AccountID in (" + info.getAccountId() +")\n");
		}
		else{
		sbWhere
				.append(" v.AccountID in(select t.id id from sett_account t where t.ncheckstatusid = 4 and t.nclientid ="
						+info.getClientid()+" union select b.n_id id from bs_bankaccountinfo b where b.n_ischeck = 1 and b.n_rdstatus =1 and b.n_clientid="
						+info.getClientid()+") ");
		}

		if (info.getClientid() > 0) {
			sbWhere.append(" and v.clientID=" + info.getClientid() + "\n");
		}
		if (info.getAreaCenter() != null && info.getAreaCenter().length() > 0) {
			sbWhere.append(" and v.areaID in(" + info.getAreaCenter() + ")\n");
		}
		if (info.getBankId() != null && info.getBankId().length() > 0) {
			sbWhere.append(" and v.Bankid in(" + info.getBankId() + ")\n");
		}
		if (info.getCurrencyId() != null && info.getCurrencyId().length() > 0) {
			sbWhere.append(" and v.currencyID in(" + info.getCurrencyId()
					+ ")\n");
		}
		if (info.getCountryId() != null && info.getCountryId().length() > 0) {
			sbWhere.append(" and v.countryid in(" + info.getCountryId()
					+ ")\n");
		}
		
		if (info.getStartDate() != null && info.getStartDate().length() > 0)// 查询日期起
		{
			sbWhere.append(" and to_char(v.executedate,'yyyy-mm-dd' ) >= " + "'"
					+ info.getStartDate() + "'" + " \n");
		}

		if (info.getEndDate() != null && info.getEndDate().length() > 0)// 查询日期止
		{
			sbWhere.append(" and to_char(v.executedate,'yyyy-mm-dd' ) <= " + "'"
					+ info.getEndDate() + "'" + " \n");
		}
		/** sbWhere---拼写结束* */

		/** sbOrderBy---拼写开始* */

		sbOrderBy
				.append(" \n  Order by executeDate,clientID,areaID,bankid,accountNo,currencyID");

		/** sbOrderBy---拼写结束* */

	}

}