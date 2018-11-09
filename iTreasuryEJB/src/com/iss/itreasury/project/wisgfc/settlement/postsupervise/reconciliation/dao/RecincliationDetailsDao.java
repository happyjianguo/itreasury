package com.iss.itreasury.project.wisgfc.settlement.postsupervise.reconciliation.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.project.wisgfc.settlement.postsupervise.reconciliation.dataentity.ReconcliationDtailsInfo;
import com.iss.itreasury.settlement.query.paraminfo.QueryAccountAmountWhereInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.system.dao.PageLoader;

public class RecincliationDetailsDao extends ITreasuryDAO {
	public final static int OrderBy_AccountNo = 1;
	public final static int OrderBy_Name = 2;
	public final static int OrderBy_AccountTypeID = 3;
	public final static int OrderBy_StartBalance = 4;
	public final static int OrderBy_PayAmount = 5;
	public final static int OrderBy_RecAmount = 6;
	public final static int OrderBy_EndBalance = 7;
	public final static int OrderBy_TransCount =8;

	private StringBuffer m_sbSelect = null;
	private StringBuffer m_sbFrom = null;
	private StringBuffer m_sbWhere = null;
	private StringBuffer m_sbOrderBy = null;

	public RecincliationDetailsDao() {
		super("SETT_RECONCILIATION_DETAILS");
	}
	
	public RecincliationDetailsDao(Connection conn){
		super("SETT_RECONCILIATION_DETAILS",conn);
	}

	/**
	 * 增加调节表回收率详情  2010-11-04
	 * @param reconcliationDtailsInfos
	 * @throws ITreasuryDAOException
	 */
	public void addRecincliationDetails(
			ReconcliationDtailsInfo[] reconcliationDtailsInfos)
			throws ITreasuryDAOException {
			setUseMaxID();
		for (int i =0; i<reconcliationDtailsInfos.length;i++) {
			ReconcliationDtailsInfo reconcliationDtailsInfo = reconcliationDtailsInfos[i];
			reconcliationDtailsInfo.setId(-1);

			StringBuffer buffer = new StringBuffer();
			buffer.append("INSERT INTO " + strTableName + " (\n");
			String[] buffers = getAllFieldNameBuffer(reconcliationDtailsInfo,
					DAO_OPERATION_ADD);
			buffer.append(buffers[0]);
			buffer.append("\n) " + "VALUES (\n");
			buffer.append(buffers[1] + ") \n");

			String strSQL = buffer.toString();
			log.debug(strSQL);
			prepareStatement(strSQL);

			setPrepareStatementByDataEntity(reconcliationDtailsInfo, DAO_OPERATION_ADD,
					buffers[0].toString());

			executeUpdate();
		}
	}

	/**
	 * 修改调节表回收率详情 2010-10-04
	 * @param reconcliationDtailsInfos
	 * @throws ITreasuryDAOException
	 * @throws SQLException 
	 */
	public void updateRecincliationDetails(
			ReconcliationDtailsInfo[] reconcliationDtailsInfos)
			throws ITreasuryDAOException, SQLException {
		for (int i =0; i<reconcliationDtailsInfos.length;i++) {
			ReconcliationDtailsInfo reconcliationDtailsInfo = reconcliationDtailsInfos[i];
			StringBuffer buffer = new StringBuffer();
			buffer.append("UPDATE " + strTableName + " SET \n");
			try{
			String[] buffers = getAllFieldNameBuffer(reconcliationDtailsInfo,
					DAO_OPERATION_UPDATE);
			buffer.append(buffers[0]);
			buffer.append(" WHERE ID = " + reconcliationDtailsInfo.getId());

			String strSQL = buffer.toString();
			log.debug(strSQL);
			prepareStatement(strSQL);
			setPrepareStatementByDataEntity(reconcliationDtailsInfo, DAO_OPERATION_UPDATE,
					buffers[0].toString());

			executeUpdate();
			}catch(Exception e){
				throw new RuntimeException(e);
			}finally{
				if(transPS!=null){
					transPS.close();
				}
			}
		}
	}

	/**
	 * 分页查询调节表回收率详情
	 * @param reconciliationID
	 * @return
	 * @throws Exception
	 */
	public PageLoader queryReconciliationDetailInfo(long reconciliationID)
			throws Exception {
		// 获取SQL字句
		getRecincliationDetailInfoSQL(reconciliationID);
		// 获取PageLoader对象
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory
				.getBaseObject("com.iss.system.dao.PageLoader");

		// 初始化PageLoader对象、实现查询和分页
		pageLoader
				.initPageLoader(
						new AppContext(),
						m_sbFrom.toString(),
						m_sbSelect.toString(),
						m_sbWhere.toString(),
						(int) Constant.PageControl.CODE_PAGELINECOUNT,
						"com.iss.itreasury.project.wisgfc.settlement.postsupervise.reconciliation.dataentity.ReconcliationDtailsInfo",
						null);
		pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
		return pageLoader;
	}

	private void getRecincliationDetailInfoSQL(long reconciliationID) {
		m_sbSelect = new StringBuffer();
		m_sbSelect.append("\n ID id,ISCHECKED isChecked, \n");
		m_sbSelect.append("ACCOUNTNO accountNO, \n");
		m_sbSelect.append("ACCOUNTNAME accountName, \n");
		m_sbSelect.append("ACCOUNTTYPE accountType, \n");
		m_sbSelect.append("TRANSCOUNT transCount, \n");
		m_sbSelect.append("NVL(STARTBALANCE,0.0) startBalance, \n");
		m_sbSelect.append("NVL(PAYAMOUNT,0.0) payAmount, \n");
		m_sbSelect.append("NVL(RECAMOUNT,0.0) recAmount, \n");
		m_sbSelect.append("NVL(ENDBALANCE,0.0) endBalance, \n");
		m_sbSelect.append("RECONCILIATIONID reconciliationID, \n");
		m_sbSelect.append("ACCOUNTID accountID \n");
		m_sbFrom = new StringBuffer();

		m_sbFrom.append("SETT_RECONCILIATION_DETAILS");
		m_sbWhere = new StringBuffer();
		m_sbWhere.append("RECONCILIATIONID = ");
		m_sbWhere.append(reconciliationID);
		m_sbOrderBy = new StringBuffer();
		m_sbOrderBy.append("order by ACCOUNTNAME");
	}

	/**
	 * 物理删除调价表回收率详情
	 * @param reconciliationID
	 * @throws ITreasuryDAOException
	 * @throws SQLException 
	 */
	public void deleteRecincliationDetails(long reconciliationID)
			throws ITreasuryDAOException, SQLException {
		try {
			String strSQL = "delete from " + strTableName
					+ " where RECONCILIATIONID = " + reconciliationID;
			log.debug(strSQL);
			prepareStatement(strSQL);
			executeUpdate();

		} catch (ITreasuryDAOException ide) {
			throw ide;
		} finally {// added by mzh_fu 2008/03/26 将关闭连接放到 finally 中
			if(transPS!=null){
				transPS.close();
			}
		}
	}

	public String getDepositAccountType(long lCurrencyID, long lOfficeID) {
		System.out.println("进入 getDepositAccountType");

		String str = "";
		long[] array = SETTConstant.AccountType.getDepositAccountTypeCode(
				lCurrencyID, lOfficeID);
		for (int i = 0; i < array.length; i++)
			str = str + array[i] + ",";

		System.out.println("出去 getDepositAccountType:" + str);
		return (str.length() != 0 ? str.substring(0, str.length() - 1) : str);

	}

	public String getLoanAccountType(long lCurrencyID, long lOfficeID) {
		System.out.println("进入1 getLoanAccountType");
		String str = "";
		long[] array = SETTConstant.AccountType.getLoanAccountTypeCode(
				lCurrencyID, lOfficeID);
		for (int i = 0; i < array.length; i++)
			str = str + array[i] + ",";
		System.out.println("出去1 getLoanAccountType:" + str);
		return (str.length() != 0 ? str.substring(0, str.length() - 1) : str);
	}

	/**
	 * 临时SQL
	 * @param  qaawi,nTransTypeID
	 * @return bufForm
	 */
	private String setFrom(QueryAccountAmountWhereInfo qaawi, long lTransTypeID) {
		StringBuffer bufFrom = new StringBuffer();

		String depositAccountType = this.getDepositAccountType(qaawi
				.getCurrencyID(), qaawi.getOfficeID());
		String loanAccountType = this.getLoanAccountType(qaawi.getCurrencyID(),
				qaawi.getOfficeID());
		if ((depositAccountType != null && depositAccountType.trim().length() > 0)
				|| (loanAccountType != null && loanAccountType.trim().length() > 0)) {
			bufFrom.append("AND (");
			if (depositAccountType != null
					&& depositAccountType.trim().length() > 0) {
				bufFrom
						.append(" (a.nTransDirection = "
								+ (lTransTypeID == SETTConstant.DebitOrCredit.DEBIT ? SETTConstant.DebitOrCredit.DEBIT
										: SETTConstant.DebitOrCredit.CREDIT));
				bufFrom.append(" AND b.nAccountTypeID IN ("
						+ depositAccountType + "))");
			}
			if ((depositAccountType != null && depositAccountType.trim()
					.length() > 0)
					&& (loanAccountType != null && loanAccountType.trim()
							.length() > 0)) {
				bufFrom.append(" OR");
			}
			if (loanAccountType != null && loanAccountType.trim().length() > 0) {
				bufFrom
						.append(" (a.nTransDirection = "
								+ (lTransTypeID == SETTConstant.DebitOrCredit.DEBIT ? SETTConstant.DebitOrCredit.CREDIT
										: SETTConstant.DebitOrCredit.DEBIT));
				bufFrom.append("AND b.nAccountTypeID IN (" + loanAccountType
						+ "))");
			}
			bufFrom.append(" )");
		}

		return bufFrom.toString();
	}

	/**
	 * 调节表回收率查询账户交易信息 包含时间段交易次数
	 * @param qaawi
	 * @return
	 * @throws Exception
	 */
	public PageLoader queryReconciliationInfo(QueryAccountAmountWhereInfo qaawi)
			throws Exception {
		// 获取SQL字句
		getAccountReconciliationInfoSQL(qaawi, 2);
		// 获取PageLoader对象
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory
				.getBaseObject("com.iss.system.dao.PageLoader");

		// 初始化PageLoader对象、实现查询和分页
		pageLoader
				.initPageLoader(
						new AppContext(),
						m_sbFrom.toString(),
						m_sbSelect.toString(),
						m_sbWhere.toString(),
						(int) Constant.PageControl.CODE_PAGELINECOUNT,
						"com.iss.itreasury.project.wisgfc.settlement.postsupervise.reconciliation.dataentity.ReconcliationDtailsInfo",
						null);
		pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
		return pageLoader;
	}

	/**
	 * 调节表回收率计算 查询产生交易账号信息
	 * @param     qaawi:查询条件
	 * @return nothing
	 */
	private void getAccountReconciliationInfoSQL(
			QueryAccountAmountWhereInfo qaawi, int nSelectTypeID) {
		StringBuffer bufTmp = new StringBuffer();
		StringBuffer bufTemp = new StringBuffer();
		StringBuffer bufFromTemp = new StringBuffer();
		// create select
		m_sbSelect = new StringBuffer();
		m_sbSelect.append("\n bbb.ID accountID,bbb.sAccountNo accountNO, \n");
		if (qaawi.getIsCheckedType() == 3) {
			m_sbSelect.append("bbb.nAccountTypeID accountType, \n");
		} else if (qaawi.getIsCheckedType() == 6) {
			//m_sbSelect.append("(select b.id AccountTypeID from sett_accounttype a,(select id,',' || saccounttypeid || ',' saccounttypeid from sett_DEPOSITLOANSEARCHSETTING where nofficeid=" + qaawi.getOfficeID() + " and ncurrencyid="+ qaawi.getCurrencyID() +" and SNAME='"+SETTConstant.DepositLoanType.getName(qaawi.getAccountTypeID2())+"' ) b where b.saccounttypeid like   '%,' || a.id || ',%' and a.id=bbb.nAccountTypeID and a.nstatusId=1 and a.officeId="+qaawi.getOfficeID()+" and a.currencyId="+qaawi.getCurrencyID()+") AccountTypeID, \n");
			m_sbSelect.append("bbb.nAccountTypeID accountType, \n");
		}
		m_sbSelect.append("ccc.sName accountName, \n");
		m_sbSelect.append("NVL(aaa.mStartBalance,0.0) startBalance, \n");
		m_sbSelect.append("NVL(aaa.mPayAmount,0.0) payAmount, \n");
		m_sbSelect.append("NVL(aaa.mRecAmount,0.0) recAmount, \n");
		m_sbSelect.append("NVL(aaa.mStartBalance,0.0)+ \n");
		m_sbSelect.append("NVL(aaa.mRecAmount,0.0)- \n");
		m_sbSelect.append("NVL(aaa.mPayAmount, 0.0) endBalance, \n");
		m_sbSelect.append("ee.count transCount");
		// create from 
		m_sbFrom = new StringBuffer();
		m_sbFrom.append("(SELECT dd.nAccountID, \n");
		bufTmp.append("NVL(dd.mStartBalance,0.0) mStartBalance, \n");
		bufTmp.append("NVL(cc.mPayAmount,0.0) mPayAmount,"
				+ "NVL(cc.mRecAmount,0.0) mRecAmount FROM ( \n");
		bufTmp.append("    SELECT bb.nTransAccountID,"
				+ "aa.mPayAmount,bb.mRecAmount FROM \n");
		bufFromTemp.append("        (SELECT nTransAccountID,"
				+ "nTransDirection,SUM(mAmount) mPayAmount \n");
		bufTemp.append("        FROM sett_TransAccountDetail a, "
				+ "sett_account b \n");
		bufTemp.append("        WHERE a.nTransAccountID = b.ID \n");
		bufTemp.append("        	AND a.nStatusID = "
				+ SETTConstant.TransactionStatus.CHECK + " \n");

		if (!(qaawi.getStartQueryDate() == null || qaawi.getStartQueryDate()
				.toString().trim().length() == 0)) {
			bufTemp.append(" AND a.dtExecute >= to_date('"
					+ DataFormat.getDateString(qaawi.getStartQueryDate())
					+ "','yyyy-mm-dd')   \n");
		}
		if (!(qaawi.getEndQueryDate() == null || qaawi.getEndQueryDate()
				.toString().trim().length() == 0)) {
			bufTemp.append(" AND a.dtExecute <= to_date('"
					+ DataFormat.getDateString(qaawi.getEndQueryDate())
					+ "','yyyy-mm-dd')   \n");
		}
		bufFromTemp.append(bufTemp.toString());
		bufFromTemp.append(this
				.setFrom(qaawi, SETTConstant.DebitOrCredit.DEBIT));
		bufFromTemp.append("            GROUP BY nTransAccountID,"
				+ "nTransDirection) aa, \n");
		bufFromTemp.append("        (SELECT nTransAccountID,"
				+ "nTransDirection,SUM(mAmount) mRecAmount \n");
		bufFromTemp.append(bufTemp.toString());
		bufFromTemp.append(this.setFrom(qaawi,
				SETTConstant.DebitOrCredit.CREDIT));
		bufFromTemp.append("            GROUP BY  nTransAccountID,"
				+ "nTransDirection) bb \n");
		bufTmp.append(bufFromTemp.toString());
		bufTmp.append("        WHERE aa.nTransAccountID(+) = "
				+ "bb.nTransAccountID \n");
		bufTmp.append("        UNION \n");
		bufTmp.append("    SELECT aa.nTransAccountID,"
				+ "aa.mPayAmount,bb.mRecAmount FROM \n");
		bufTmp.append(bufFromTemp.toString());
		bufTmp.append("        WHERE aa.nTransAccountID = "
				+ "bb.nTransAccountID(+)) cc, \n");
		bufTmp.append("        (SELECT SUM(mBalance) mStartBalance,"
				+ "nAccountID FROM sett_DailyAccountBalance \n");
		if (!(qaawi.getStartQueryDate() == null || qaawi.getStartQueryDate()
				.toString().trim().length() == 0))
			bufTmp.append("        WHERE dtDate = TO_DATE('"
					+ DataFormat.getDateString(DataFormat.getPreviousDate(qaawi
							.getStartQueryDate())) + "','yyyy-mm-dd') \n");
		bufTmp.append("        GROUP BY nAccountID) dd \n");
		m_sbFrom.append(bufTmp.toString());
		m_sbFrom.append("    WHERE  cc.nTransAccountID(+) = dd.nAccountID \n");
		m_sbFrom.append("UNION \n");
		m_sbFrom.append("SELECT cc.nTransAccountID, \n");
		m_sbFrom.append(bufTmp.toString());
		m_sbFrom
				.append("    WHERE  cc.nTransAccountID = dd.nAccountID(+)) aaa, \n");
		m_sbFrom.append("    sett_Account bbb, \n");
		m_sbFrom.append("    Client ccc, \n");
		m_sbFrom
				.append(" (SELECT count(*) count,c.SACCOUNTNO SACCOUNTNO FROM sett_TransAccountDetail a, sett_VTransaction b, sett_account c \n");
		m_sbFrom
				.append(" WHERE 1 = 1 AND a.sTransNo = b.TransNo(+) AND a.nOfficeID = "
						+ qaawi.getOfficeID()
						+ " AND a.nCurrencyID = "
						+ qaawi.getCurrencyID());
		m_sbFrom.append(" And a.nStatusID = 3");
		if (qaawi.getStartQueryDate() != null
				&& qaawi.getStartQueryDate().toString().trim().length() > 0) {
			System.out.println(qaawi.getStartQueryDate());
			System.out.println(DataFormat.getDateString(qaawi
					.getStartQueryDate()));
			m_sbFrom.append("	AND a.dtExecute >= TO_DATE('"
					+ DataFormat.getDateString(qaawi.getStartQueryDate())
					+ "', 'YYYY-MM-DD') \n");
		}
		if (qaawi.getEndQueryDate() != null
				&& qaawi.getEndQueryDate().toString().trim().length() > 0) {
			m_sbFrom.append("	AND a.dtExecute <= TO_DATE('"
					+ DataFormat.getDateString(qaawi.getEndQueryDate())
					+ "', 'YYYY-MM-DD') \n");
		}
		m_sbFrom.append(" AND a.nTransAccountID = c.ID ");
		m_sbFrom.append("group by c.SACCOUNTNO) ee ");

		// create where 
		m_sbWhere = new StringBuffer();
		m_sbWhere.append("  aaa.nAccountID = bbb.ID \n");
		m_sbWhere.append("  AND bbb.nClientID = ccc.ID \n");
		m_sbWhere
				.append("  AND bbb.nOfficeID = " + qaawi.getOfficeID() + " \n");
		m_sbWhere.append("  AND bbb.nCurrencyID = " + qaawi.getCurrencyID()
				+ " \n");
		m_sbWhere.append("  AND bbb.nCheckStatusID = "
				+ SETTConstant.AccountCheckStatus.CHECK + " \n");
		m_sbWhere.append(" AND bbb.sAccountNo = ee.SACCOUNTNO ");
		System.out.println("--------得到客户类型ID为:" + qaawi.getClientTypeID());
		if (qaawi.getClientTypeID() != -1) { //客户类型ID
			m_sbWhere.append("  AND ccc.NSETTCLIENTTYPEID = "
					+ qaawi.getClientTypeID() + " \n");
		}

		if (qaawi.getEnterpriseTypeID1() > 0) { //客户属性1
			m_sbWhere.append("  AND ccc.nClienttypeID1 = "
					+ qaawi.getEnterpriseTypeID1() + " \n");
		}
		if (qaawi.getEnterpriseTypeID2() > 0) { //客户属性2
			m_sbWhere.append("  AND ccc.nClienttypeID2 = "
					+ qaawi.getEnterpriseTypeID2() + " \n");
		}
		if (qaawi.getEnterpriseTypeID3() > 0) { //客户属性3
			m_sbWhere.append("  AND ccc.nClienttypeID3 = "
					+ qaawi.getEnterpriseTypeID3() + " \n");
		}
		if (qaawi.getEnterpriseTypeID4() > 0) { //客户属性4
			m_sbWhere.append("  AND ccc.nClienttypeID4 = "
					+ qaawi.getEnterpriseTypeID4() + " \n");
		}
		if (qaawi.getEnterpriseTypeID5() > 0) { //客户属性5
			m_sbWhere.append("  AND ccc.nClienttypeID5 = "
					+ qaawi.getEnterpriseTypeID5() + " \n");
		}
		if (qaawi.getEnterpriseTypeID6() > 0) { //客户属性6
			m_sbWhere.append("  AND ccc.nClienttypeID6 = "
					+ qaawi.getEnterpriseTypeID6() + " \n");
		}
		if (qaawi.getClientManager() > 0) { //客户经理
			m_sbWhere.append("  AND ccc.nCustomerManagerUserId = "
					+ qaawi.getClientManager() + " \n");
		}
		if (qaawi.getStartAccountNo() != null
				&& qaawi.getStartAccountNo().length() > 0) {
			m_sbWhere.append("  AND bbb.sAccountNo >= '"
					+ qaawi.getStartAccountNo() + "'");
		}
		if (qaawi.getEndAccountNo() != null
				&& qaawi.getEndAccountNo().length() > 0) {
			m_sbWhere.append("  AND bbb.sAccountNo <= '"
					+ qaawi.getEndAccountNo() + "'");
		}
		if (qaawi.getStartClientCode() != null
				&& qaawi.getStartClientCode().length() > 0) {
			m_sbWhere.append("  AND ccc.sCode >= '"
					+ qaawi.getStartClientCode() + "'");
		}
		if (qaawi.getEndClientCode() != null
				&& qaawi.getEndClientCode().length() > 0) {
			m_sbWhere.append("  AND ccc.sCode <= '" + qaawi.getEndClientCode()
					+ "'");
		}
		if (qaawi.getIsCheckedType() == 3 && qaawi.getAccountTypeID1() > 0) {
			m_sbWhere.append("  AND bbb.nAccountTypeID = "
					+ qaawi.getAccountTypeID1());
		} else if (qaawi.getIsCheckedType() == 6
				&& qaawi.getAccountTypeID2() > 0) {
			m_sbWhere.append("  AND bbb.nAccountTypeID IN ( \n");
			m_sbWhere.append("SELECT ID FROM " + "sett_accounttype WHERE \n");
			m_sbWhere.append(" NACCOUNTGROUPID = " + qaawi.getAccountTypeID2()
					+ ") \n");
		}
		if (nSelectTypeID == 2)
			m_sbWhere.append("  AND (mPayAmount <> 0.0 OR "
					+ "mRecAmount <> 0.0) \n");

		// create orderby
		m_sbOrderBy = new StringBuffer();
		String sDesc = qaawi.getDesc() == 1 ? " desc " : "";
		switch ((int) qaawi.getOrderField()) {
		case OrderBy_AccountNo:
			m_sbOrderBy.append(" \n order by accountNO" + sDesc);
			break;
		case OrderBy_Name:
			m_sbOrderBy.append(" \n order by accountName" + sDesc);
			break;
		case OrderBy_AccountTypeID:
			m_sbOrderBy.append(" \n order by accountType" + sDesc);
			break;
		case OrderBy_StartBalance:
			m_sbOrderBy.append(" \n order by startBalance" + sDesc);
			break;
		case OrderBy_PayAmount:
			m_sbOrderBy.append(" \n order by payAmount" + sDesc);
			break;
		case OrderBy_RecAmount:
			m_sbOrderBy.append(" \n order by recAmount" + sDesc);
			break;
		case OrderBy_EndBalance:
			m_sbOrderBy.append(" \n order by endBalance" + sDesc);
			break;
		case OrderBy_TransCount:
			m_sbOrderBy.append(" \n order by transCount" + sDesc);
			break;
		default:
			m_sbOrderBy.append(" \n order by accountName" + sDesc);
			break;
		}
	}

	/**
	 * 排序信息
	 * @return
	 */
	public StringBuffer getOrderBy() {
		return m_sbOrderBy;
	}

	/**
	 * 
	 * @param qaawi
	 */
	public void setOrderBy(long field, long desc) {
		// create orderby
		m_sbOrderBy = new StringBuffer();
		String sDesc = desc == 1 ? " desc " : "";
		switch ((int) field) {
		case OrderBy_AccountNo:
			m_sbOrderBy.append(" \n order by accountNO" + sDesc);
			break;
		case OrderBy_Name:
			m_sbOrderBy.append(" \n order by accountName" + sDesc);
			break;
		case OrderBy_AccountTypeID:
			m_sbOrderBy.append(" \n order by accountType" + sDesc);
			break;
		case OrderBy_StartBalance:
			m_sbOrderBy.append(" \n order by startBalance" + sDesc);
			break;
		case OrderBy_PayAmount:
			m_sbOrderBy.append(" \n order by payAmount" + sDesc);
			break;
		case OrderBy_RecAmount:
			m_sbOrderBy.append(" \n order by RecAmount" + sDesc);
			break;
		case OrderBy_EndBalance:
			m_sbOrderBy.append(" \n order by EndBalance" + sDesc);
			break;
		case OrderBy_TransCount:
			m_sbOrderBy.append(" \n order by transCount" + sDesc);
			break;
		default:
			m_sbOrderBy.append(" \n order by AccountNo" + sDesc);
			break;
		}
	}
}
