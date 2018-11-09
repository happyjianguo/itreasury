package com.iss.itreasury.settlement.interest.bizlogic;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import com.iss.itreasury.settlement.account.dao.Sett_AccountTypeDAO;
import com.iss.itreasury.settlement.account.dao.Sett_SubAccountDAO;
import com.iss.itreasury.settlement.account.dao.sett_TransAccountDetailDAO;
import com.iss.itreasury.settlement.account.dataentity.AccountTypeInfo;
import com.iss.itreasury.settlement.interest.dao.InterestQueryDao;
import com.iss.itreasury.settlement.interest.dataentity.InterestEstimateQueryInfo;
import com.iss.itreasury.settlement.interest.dataentity.InterestEstimateQueryResultInfo;
import com.iss.itreasury.settlement.interest.dataentity.InterestQueryInfo;
import com.iss.itreasury.settlement.interest.dataentity.InterestQueryResultInfo;
import com.iss.itreasury.settlement.interest.dataentity.InterestSettmentInfo;
import com.iss.itreasury.settlement.interest.dataentity.LoanNoticeInfo;
import com.iss.itreasury.settlement.query.queryobj.QAccountAmountInfoDao;
import com.iss.itreasury.settlement.query.queryobj.QInterest;
import com.iss.itreasury.settlement.query.queryobj.QInterestEstimate;
import com.iss.itreasury.settlement.query.queryobj.QLoanNotice;
import com.iss.itreasury.settlement.query.resultinfo.QueryAccountResultInfo;
import com.iss.itreasury.settlement.query.resultinfo.QueryInterestSumInfo;
import com.iss.itreasury.settlement.util.NameRef;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.settlement.util.UtilOperation;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log4j;
import com.iss.itreasury.util.SessionMng;
import com.iss.sysframe.pager.dataentity.PagerDepictBaseInfo;
import com.iss.sysframe.pager.dataentity.PagerInfo;
import com.iss.sysframe.pager.dataentity.PagerTypeConstant;
import com.iss.sysframe.pager.dataentity.ResultPagerRowInfo;
import com.iss.sysframe.pager.tools.PagerTools;

public class InterestQueryBiz {
	InterestQueryDao interestQueryDao = new InterestQueryDao();
	private Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);

	/**
	 * 利息费用结算处理查询方法
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public PagerInfo queryInterest(InterestQueryInfo qInfo) throws Exception {

		PagerInfo pagerInfo = null;
		Map paramMap = new HashMap();
		paramMap.put("qInfo", qInfo);
		String sql = null;
		try {
			pagerInfo = new PagerInfo();
			// 得到查询SQL
			sql = interestQueryDao.queryInterestSQL(qInfo);
			pagerInfo.setSqlString(sql);

			ArrayList depictList = new ArrayList();
			PagerDepictBaseInfo baseInfo = null;

			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("ID");
			baseInfo.setDisplayType(PagerTypeConstant.LONG);
			depictList.add(baseInfo);

			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("AccountNo");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);

			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("contractNo");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);

			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("payFormNo");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);

			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("InterestStartDate");
			baseInfo.setDisplayType(PagerTypeConstant.DATE);
			depictList.add(baseInfo);

			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("InterestEndDate");
			baseInfo.setDisplayType(PagerTypeConstant.DATE);
			depictList.add(baseInfo);

			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("InterestDays");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);

			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("BaseBalance");
			baseInfo.setDisplayType(PagerTypeConstant.AMOUNT_2);
			depictList.add(baseInfo);

			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("Rate");
			baseInfo.setDisplayType(PagerTypeConstant.AMOUNT_6);
			depictList.add(baseInfo);

			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("InterestType_ex1");
			baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
			baseInfo.setExtensionMothod(SETTConstant.InterestFeeType.class,
					"getName_ex1", new Class[] { String.class });
			depictList.add(baseInfo);

			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("Interest_ex2");
			baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
			baseInfo.setExtensionMothod(SETTConstant.InterestFeeType.class,
					"getName_ex2", new Class[] { String.class });
			depictList.add(baseInfo);

			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("InterestTax");
			baseInfo.setDisplayType(PagerTypeConstant.AMOUNT_2);
			depictList.add(baseInfo);

			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("InputUserID");
			baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
			baseInfo.setExtensionMothod(NameRef.class, "getUserNameByID",
					new Class[] { long.class });
			depictList.add(baseInfo);

			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("ExecuteDate");
			baseInfo.setDisplayType(PagerTypeConstant.DATE);
			depictList.add(baseInfo);

			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("TransNo");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);

			pagerInfo.setDepictList(depictList);

			pagerInfo.setTotalExtensionMothod(InterestQueryBiz.class,
					"getInterestSumAmount", paramMap);

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return pagerInfo;

	}

	public ArrayList getInterestSumAmount(ResultSet rs, Map map)
			throws Exception {

		try {
			String strBalanceSum = ""; // 余额合计
			String strInterestSum = ""; // 利息合计
			String strAssureSum = ""; // 担保费合计
			String strCompoundInterestSum = ""; // 复利合计
			String strForfeitInterestSum = ""; // 罚息合计
			String strInterestTaxSum = ""; // 利息税费合计
			String strCommisionSum = ""; // 手续费合计
			ArrayList list = new ArrayList();
			InterestQueryInfo qInfo = (InterestQueryInfo) map.get("qInfo");
			QInterest qobj = new QInterest();
			QueryInterestSumInfo qisi = new QueryInterestSumInfo();
			qisi = qobj.queryAllTypeInterestSum(qInfo);
			if (rs != null) {

				QAccountAmountInfoDao qaid = new QAccountAmountInfoDao();
				// 查询的总计结果集

				if (qisi != null) {
					strBalanceSum = DataFormat.formatDisabledAmount(DataFormat
							.formatDouble(qisi.getBalanceSum(), 2), 2);
					strInterestSum = DataFormat.formatDisabledAmount(DataFormat
							.formatDouble(qisi.getInterestSum(), 2), 2);
					strAssureSum = DataFormat.formatDisabledAmount(DataFormat
							.formatDouble(qisi.getAssureSum(), 2), 2);
					strInterestTaxSum = DataFormat.formatDisabledAmount(
							DataFormat
									.formatDouble(qisi.getInterestTaxSum(), 2),
							2);
					strCommisionSum = DataFormat.formatDisabledAmount(
							DataFormat.formatDouble(qisi.getCommisionSum(), 2),
							2);
					strCompoundInterestSum = DataFormat.formatDisabledAmount(
							DataFormat.formatDouble(qisi
									.getCompoundInterestSum(), 2), 2);
					;
					strForfeitInterestSum = DataFormat.formatDisabledAmount(
							DataFormat.formatDouble(qisi
									.getForfeitInterestSum(), 2), 2);
					;
				}

				list.add("余额合计{" + strBalanceSum + "}");
				list.add("利息合计{" + strInterestSum + "}");
				if (qInfo.getIsPrewDraw() != 1) {
					list.add("手续费合计{" + strCommisionSum + "}");
					list.add("担保费合计{" + strAssureSum + "}");
					list.add("复利合计{" + strCompoundInterestSum + "}");
					list.add("罚息合计{" + strForfeitInterestSum + "}");
					list.add("利息税费合计{" + strInterestTaxSum + "}");
				}
			}
			return list;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}

	}

	/**
	 * 打印到期贷款通知书、逾期贷款催收通知书
	 *  
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public PagerInfo loanMatureNotice(InterestEstimateQueryInfo qInfo,
			String action, String ids,String cp,String rp) throws Exception {
		PagerInfo pagerInfo = null;
		String sql = null;
		try {
			pagerInfo = new PagerInfo();
			// 得到查询SQL

			Map paramMap = new HashMap();
			paramMap.put("qInfo", qInfo);
			paramMap.put("cp", cp);
			paramMap.put("rp", rp);

			if (action.equals("ysClientNotice")) {
				sql = interestQueryDao.queryLoanNoticeClientYs(qInfo, ids
						);
			} else if (action.equals("ysAccountNotice")) {
				sql = interestQueryDao.queryLoanNoticeAccountYs(qInfo, ids
						);
			} else {
				sql = interestQueryDao.queryloanMatureNoticeSQL(qInfo);
			}
				pagerInfo.setSqlString(sql);
				System.out.println(sql);
				pagerInfo.setExtensionMothod(InterestQueryBiz.class,
						"loanMatureNoticeResultSetHandle", paramMap);

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;

	}

	public ArrayList loanMatureNoticeResultSetHandle(ResultSet rs, Map map) throws SQLException, Exception {
		InterestEstimateQueryInfo qinfo = (InterestEstimateQueryInfo) map
				.get("qInfo");
		int cp = Integer.parseInt(map.get("cp").toString());
		int rp = Integer.parseInt(map.get("rp").toString());
		ArrayList resultList = new ArrayList(); // 最终返回结果
		ArrayList cellList = null;// 列
		ResultPagerRowInfo rowInfo = null;// 行
		QLoanNotice qNotice = new QLoanNotice();
		Vector noticeVec = new Vector();
		Vector resultVec = new Vector();
		ArrayList rownum = new ArrayList();
		while (rs.next()) {
			LoanNoticeInfo resultInfo = new LoanNoticeInfo();

			resultInfo.setAccountNo(rs.getString("ACCOUNTNO"));
			resultInfo.setOfficeID(rs.getLong("OFFICEID"));
			resultInfo.setCurrencyID(rs.getLong("CURRENCYID"));
			resultInfo.setAccountTypeID(rs.getLong("ACCOUNTTYPEID"));
			resultInfo.setClearInterestDate(rs.getTimestamp("CLEARINTERESTDATE"));
			resultInfo.setSubAccountID(rs.getLong("SUBACCOUNTID"));
			resultInfo.setLoanNoteID(rs.getLong("LOANNOTEID"));
			resultInfo.setContractID(rs.getLong("CONTRACTID"));
			resultInfo.setContractNo(rs.getString("CONTRACTNO"));
			resultInfo.setBorrowClientID(rs.getLong("BORROWCLIENTID"));
			resultInfo.setLoanAmount(rs.getDouble("LoanAmount"));
			resultInfo.setLoanTypeID(rs.getLong("LoanTypeID"));
			resultInfo.setLoanStartDate(rs.getTimestamp("LOANSTARTDATE"));
			resultInfo.setLoanEndDate(rs.getTimestamp("LOANENDDATE"));
			resultInfo.setPayFormAmount(rs.getDouble("PAYFORMAMOUNT"));
			resultInfo.setLoanTerm(rs.getLong("LOANTERM"));
			resultInfo.setPayFormNo(rs.getString("PAYFORMNO"));
			rownum.add(rs.getLong("rownum1"));
			
			noticeVec.addElement(resultInfo);
		}
		resultVec=qNotice.queryNoticeInterest(null,noticeVec,qinfo);
		if(resultVec != null && resultVec.size()>0){
			for(int i=0; i<resultVec.size(); i++){
				LoanNoticeInfo obj = (LoanNoticeInfo )resultVec.elementAt(i);
				
				String FormNo = obj.getFormNo();
				int j = Integer.valueOf(FormNo).intValue();
				int k= (cp-1)*rp+j;
				String strReturn = DataFormat.formatInt(k, 4);
				obj.setFormNo(strReturn);
				
				String strContraceNo = DataFormat.formatString(obj.getContractNo());	
				String	strPayFormNo = DataFormat.formatString(obj.getPayFormNo());
				String strFormYear = DataFormat.formatString(obj.getFormYear());
				String strFormNo = DataFormat.formatString(obj.getFormNo());
				String strFormNum="";
				if(obj.getFormNum()>0)
				{
					strFormNum = DataFormat.formatNumber(obj.getFormNum());			
				}
				cellList = new ArrayList();
				PagerTools.returnCellList(cellList, i);
				PagerTools.returnCellList(cellList, strContraceNo);
				PagerTools.returnCellList(cellList, strPayFormNo);
				PagerTools.returnCellList(cellList, strFormYear);
				PagerTools.returnCellList(cellList, strFormNo);
				PagerTools.returnCellList(cellList, strFormNum);

				rowInfo = new ResultPagerRowInfo();
				rowInfo.setCell(cellList);
				rowInfo.setId(String.valueOf(rownum.get(i)));
				// 返回数据
				resultList.add(rowInfo);
			}
		}
		return resultList;

	}

	public String getFormNo(String FormNo) {
		String strFormNo = "";
		if (FormNo != null && !FormNo.equals("")) {
			int k = Integer.valueOf(FormNo).intValue();
			strFormNo = DataFormat.formatInt(k, 4);
		} else {
			strFormNo = "0001";
		}

		return strFormNo;
	}

	public PagerInfo calculateInterest(InterestQueryInfo qInfo)
			throws Exception {
		PagerInfo pagerInfo = null;
		String sql = null;
		try {
			pagerInfo = new PagerInfo();
			Map paramMap = new HashMap();
			paramMap.put("qInfo", qInfo);

			sql = interestQueryDao.calculateInterestSQL(qInfo);
			// 得到查询SQL
			pagerInfo.setSqlString(sql);

			pagerInfo.setExtensionMothod(InterestQueryBiz.class,
					"calculateInterestResultSetHandle", paramMap);

			pagerInfo.setTotalExtensionMothod(InterestQueryBiz.class,
					"getCalculateInterestSumAmount", paramMap);

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
	}

	public ArrayList calculateInterestResultSetHandle(ResultSet rs, Map map)
			throws Exception {

		ArrayList resultList = new ArrayList(); // 最终返回结果
		ArrayList cellList = null;// 列
		ResultPagerRowInfo rowInfo = null;// 行

		InterestSettlement interestSettlement = new InterestSettlement();
		InterestQueryInfo qInfo = (InterestQueryInfo) map.get("qInfo");
		Connection conInternal = Database.getConnection();

		InterestOperation io = new InterestOperation(conInternal);
		InterestBatch ib = new InterestBatch(conInternal);
		// 判断是否续倒填
		sett_TransAccountDetailDAO transDetailDAO = new sett_TransAccountDetailDAO(
				conInternal);
		// 判断是否利率倒填
		Sett_SubAccountDAO subaccDAO = new Sett_SubAccountDAO(conInternal);

		QueryAccountResultInfo info = null;

	
		
	
		int i = 0;

		try {

			if (rs != null) {

				while (rs.next()) {
					String strDays = "";
					String strInterest = "";
					String strCompoundInterest = "";
					String strForfeitInterest = "";
					// 计提利息
					String strDrawingInterest = "";
					// 手续费
					String strHandlingCharge = "";
					// 担保费
					String strAssuranceCharge = "";
					// 利息税费
					String strInterestTaxCharge = "";
					String strCreator = "";
					String strCreateDate = "";
					long officeID = -1;
					long currencyID = -1;
					long accountTypeID = -1;
					String fixedDepositNo = "";
					double loanPreDrawInterest = 0.0;
					String contractNo = "";
					String PayFormNo = "";
					long payFormID = -1;
					long SubAccountID = -1;
					long AccountID = -1;
					double InterestRate = 0.0; // 合同获得利率
					Timestamp EndDate = null;
					long ContractID = -1;
					long DiscreID = -1;
					accountTypeID = rs.getLong("accountTypeID");
					fixedDepositNo = rs.getString("fixedDepositNo");
					loanPreDrawInterest = rs.getDouble("loanPreDrawInterest");
					contractNo = rs.getString("contractNo");
					PayFormNo = rs.getString("payFormNo");
					payFormID = rs.getLong("payFormID");
					SubAccountID = rs.getLong("subAccountID");
					AccountID = rs.getLong("accountID");
					InterestRate = rs.getDouble("discountRate"); // 合同获得利率
					EndDate = rs.getTimestamp("enddate");
					ContractID = rs.getLong("contractID");
					DiscreID = rs.getLong("discreID");

					InterestQueryResultInfo resultInfo = new InterestQueryResultInfo();
					/**
					 * 进入 info.setInterest(Interest); --利息 true
					 */
					if (qInfo.isBInterest()) {

						log.info("-------开始处理利息查询数据--------");

						Sett_AccountTypeDAO sett_AccountTypeDAO = new Sett_AccountTypeDAO();
						AccountTypeInfo accountTypeInfo = null;

						log.debug("---------判断账户类型------------");
						try {
							accountTypeInfo = sett_AccountTypeDAO
									.findByID(accountTypeID);
						} catch (SQLException e) {
							e.printStackTrace();
						}

						if (accountTypeInfo != null) {
							/**
							 * 如果是贴现帐户传入参数增加 贴现利率 info.getInterestRate() 结束时间
							 * resultInfo.getEndDate()
							 */
							if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.DISCOUNT) {
								resultInfo = interestSettlement
										.getDiscountInterestInfo(
												fixedDepositNo,
												loanPreDrawInterest,
												contractNo,
												PayFormNo,
												SubAccountID,
												AccountID,
												accountTypeID,
												InterestRate // 合同获得利率
												,
												EndDate,
												ContractID,
												DiscreID,
												qInfo.getOfficeID(),
												qInfo.getCurrencyID(),
												qInfo.getClearInterest(),
												Env.getSystemDate(conInternal,
														qInfo.getOfficeID(),
														qInfo.getCurrencyID()),
												SETTConstant.InterestFeeType.INTEREST,
												io, ib, transDetailDAO);
							}
							// 是否活期账户组类型
							// 是否保证金账户组类型
							else if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CURRENT
									|| accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.BAK
									|| accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.RESERVE
									|| accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.LENDING
									|| accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.MARGIN) {
								resultInfo = interestSettlement
										.getInterestInfo(
												fixedDepositNo,
												loanPreDrawInterest,
												contractNo,
												PayFormNo,
												SubAccountID,
												AccountID,
												accountTypeID,
												qInfo.getOfficeID(),
												qInfo.getCurrencyID(),
												qInfo.getClearInterest(),
												Env.getSystemDate(conInternal,
														qInfo.getOfficeID(),
														qInfo.getCurrencyID()),
												SETTConstant.InterestFeeType.INTEREST,
												io, ib, transDetailDAO);
							} else {
								resultInfo = interestSettlement
										.getInterestInfo(
												fixedDepositNo,
												loanPreDrawInterest,
												contractNo,
												PayFormNo,
												SubAccountID,
												AccountID,
												accountTypeID,
												qInfo.getOfficeID(),
												qInfo.getCurrencyID(),
												qInfo.getClearInterest(),
												Env.getSystemDate(conInternal,
														qInfo.getOfficeID(),
														qInfo.getCurrencyID()),
												SETTConstant.InterestFeeType.INTEREST,
												io, ib, transDetailDAO,
												subaccDAO);

								resultInfo.setPayFormID(payFormID);
							}

							// 处理利息值
							resultInfo.setInterest(UtilOperation.Arith.round(
									resultInfo.getInterest(), 2));
							log.info("-------处理利息查询数据结束--------");
						}
					}

					if (qInfo.isBCompoundInterest()) {
						log.info("-------开始处理复利数据--------");
						resultInfo = interestSettlement.getInterestInfo(
								fixedDepositNo, loanPreDrawInterest,
								contractNo, PayFormNo, SubAccountID, AccountID,
								accountTypeID, qInfo.getOfficeID(), qInfo
										.getCurrencyID(), qInfo
										.getClearInterest(), Env.getSystemDate(
										conInternal, qInfo.getOfficeID(), qInfo
												.getCurrencyID()),
								SETTConstant.InterestFeeType.COMPOUNDINTEREST,
								io, ib, transDetailDAO);
						resultInfo.setPayFormID(payFormID);
						log.info("-------处理复利数据结束--------");
					}

					if (qInfo.isBForfeitInterest()) {
						log.info("-------开始处理罚息数据--------");
						resultInfo = interestSettlement.getInterestInfo(
								fixedDepositNo, loanPreDrawInterest,
								contractNo, PayFormNo, SubAccountID, AccountID,
								accountTypeID, qInfo.getOfficeID(), qInfo
										.getCurrencyID(), qInfo
										.getClearInterest(), Env.getSystemDate(
										conInternal, qInfo.getOfficeID(), qInfo
												.getCurrencyID()),
								SETTConstant.InterestFeeType.FORFEITINTEREST,
								io, ib, transDetailDAO);
						resultInfo.setPayFormID(payFormID);
						log.info("-------处理罚息数据结束--------");
					}

					if (qInfo.getFeeType() == SETTConstant.InterestFeeType.COMMISION) {
						log.info("-------开始处理手续费数据--------");
						resultInfo = interestSettlement.getInterestInfo(
								fixedDepositNo, loanPreDrawInterest,
								contractNo, PayFormNo, SubAccountID, AccountID,
								accountTypeID, qInfo.getOfficeID(), qInfo
										.getCurrencyID(), qInfo
										.getClearInterest(), Env.getSystemDate(
										conInternal, qInfo.getOfficeID(), qInfo
												.getCurrencyID()),
								SETTConstant.InterestFeeType.COMMISION, io, ib,
								transDetailDAO);

						log.info("-------处理手续费数据结束--------");
					}

					if (qInfo.getFeeType() == SETTConstant.InterestFeeType.ASSURE) {
						log.info("-------开始处理担保费数据--------");
						resultInfo = interestSettlement.getInterestInfo(
								fixedDepositNo, loanPreDrawInterest,
								contractNo, PayFormNo, SubAccountID, AccountID,
								accountTypeID, qInfo.getOfficeID(), qInfo
										.getCurrencyID(), qInfo
										.getClearInterest(), Env.getSystemDate(
										conInternal, qInfo.getOfficeID(), qInfo
												.getCurrencyID()),
								SETTConstant.InterestFeeType.ASSURE, io, ib,
								transDetailDAO);
						log.info("-------处理担保费数据结束--------");
					}

					/**
					 * 进入 info.setFeeType(lFeeType); --计提利息 6
					 */
					// 计提利息
					if (qInfo.getFeeType() == SETTConstant.InterestFeeType.PREDRAWINTEREST) {

						log.info("-------开始处理计提利息数据--------");
						payFormID = -1;
						payFormID = resultInfo.getPayFormID();
						resultInfo = interestSettlement.getInterestInfo(
								fixedDepositNo, loanPreDrawInterest,
								contractNo, PayFormNo, SubAccountID, AccountID,
								accountTypeID, qInfo.getOfficeID(), qInfo
										.getCurrencyID(), qInfo
										.getClearInterest(), Env.getSystemDate(
										conInternal, qInfo.getOfficeID(), qInfo
												.getCurrencyID()),
								SETTConstant.InterestFeeType.PREDRAWINTEREST // 6
								, io, ib, transDetailDAO);
						resultInfo.setPayFormID(payFormID);
						log.info("-------处理计提利息数据结束--------");
					}

					// 存储列数据
					cellList = new ArrayList();
					strCreateDate = Env.getSystemDateString(
							qInfo.getOfficeID(), qInfo.getCurrencyID());
					// 加入返回结果
					SessionMng.sessionCache.put(i + "", resultInfo);
					PagerTools.returnCellList(cellList, i);
					PagerTools.returnCellList(cellList, resultInfo
							.getAccountNo());
					PagerTools.returnCellList(cellList, NameRef
							.getAccountNameByID(resultInfo.getAccountID()));
					PagerTools.returnCellList(cellList, resultInfo
							.getContractNo());
					PagerTools.returnCellList(cellList, resultInfo
							.getPayFormNo());
					PagerTools.returnCellList(cellList, fixedDepositNo);
					PagerTools.returnCellList(cellList, DataFormat
							.formatDate(resultInfo.getStartDate()));
					PagerTools.returnCellList(cellList, DataFormat
							.formatDate(resultInfo.getEndDate()));
					if (resultInfo.getDays() > 0) {
						strDays = String.valueOf(resultInfo.getDays());
					}
					PagerTools.returnCellList(cellList, strDays);
					PagerTools.returnCellList(cellList, DataFormat
							.formatDisabledAmount(resultInfo.getBalance(), 2));
					PagerTools.returnCellList(cellList, DataFormat
							.formatRate(resultInfo.getInterestRate()));
					// 利息
					if (resultInfo.getInterestType() == SETTConstant.InterestFeeType.INTEREST) {
						strInterest = DataFormat.formatDisabledAmount(
								UtilOperation.Arith.round(resultInfo
										.getInterest(), 2)
										+ UtilOperation.Arith.round(resultInfo
												.getNegotiateInterest(), 2), 2);
					}
					PagerTools.returnCellList(cellList, strInterest);
					PagerTools.returnCellList(cellList, DataFormat
							.formatDisabledAmount(DataFormat.formatDouble(
									resultInfo.getInterest(), 2), 2));
					PagerTools.returnCellList(cellList, DataFormat
							.formatDisabledAmount(DataFormat.formatDouble(
									resultInfo.getNegotiateInterest(), 2), 2));
					// 复利
					if (resultInfo.getInterestType() == SETTConstant.InterestFeeType.COMPOUNDINTEREST) {
						strCompoundInterest = DataFormat.formatDisabledAmount(
								DataFormat.formatDouble(resultInfo
										.getCompoundInterest(), 2), 2);
					}
					PagerTools.returnCellList(cellList, strCompoundInterest);
					// 罚息
					if (resultInfo.getInterestType() == SETTConstant.InterestFeeType.FORFEITINTEREST) {
						strForfeitInterest = DataFormat.formatDisabledAmount(
								DataFormat.formatDouble(resultInfo
										.getForfeitInterest(), 2), 2);
					}
					PagerTools.returnCellList(cellList, strForfeitInterest);
					// 计提利息
					strDrawingInterest = DataFormat.formatDisabledAmount(
							DataFormat.formatDouble(resultInfo
									.getDrawingInterest(), 2), 2);
					PagerTools.returnCellList(cellList, strDrawingInterest);
					// 手续费
					strHandlingCharge = DataFormat.formatDisabledAmount(
							DataFormat.formatDouble(resultInfo
									.getHandlingCharge(), 2), 2);
					PagerTools.returnCellList(cellList, strHandlingCharge);
					// 担保费
					strAssuranceCharge = DataFormat.formatDisabledAmount(
							DataFormat.formatDouble(resultInfo
									.getAssuranceCharge(), 2), 2);
					PagerTools.returnCellList(cellList, strAssuranceCharge);
					// 利息税费
					strInterestTaxCharge = DataFormat.formatDisabledAmount(
							DataFormat.formatDouble(resultInfo
									.getInterestTaxCharge(), 2), 2);
					PagerTools.returnCellList(cellList, strInterestTaxCharge);
					strCreator = DataFormat.formatString(NameRef
							.getUserNameByID(qInfo.getUserID()));
					PagerTools.returnCellList(cellList, strCreator);
					PagerTools.returnCellList(cellList, strCreateDate);

					// 存储行数据
					rowInfo = new ResultPagerRowInfo();
					rowInfo.setCell(cellList);
					rowInfo.setId(String.valueOf(rs.getLong("rownum1")));

					// 返回数据
					resultList.add(rowInfo);

					i++;
				}

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException(e.getMessage());
		} finally {
			conInternal.close();
		}

		return resultList;

	}

	public ArrayList getCalculateInterestSumAmount(ResultSet rs, Map map)
			throws Exception {

		ArrayList list = new ArrayList();

		InterestSettlement interestSettlement = new InterestSettlement();
		InterestQueryInfo qInfo = (InterestQueryInfo) map.get("qInfo");
		Connection conInternal = Database.getConnection();

		InterestOperation io = new InterestOperation(conInternal);
		InterestBatch ib = new InterestBatch(conInternal);
		// 判断是否续倒填
		sett_TransAccountDetailDAO transDetailDAO = new sett_TransAccountDetailDAO(
				conInternal);
		// 判断是否利率倒填
		Sett_SubAccountDAO subaccDAO = new Sett_SubAccountDAO(conInternal);

		QueryAccountResultInfo info = null;

		long officeID = -1;
		long currencyID = -1;
		long accountTypeID = -1;
		String fixedDepositNo = "";
		double loanPreDrawInterest = 0.0;
		String contractNo = "";
		String PayFormNo = "";
		long SubAccountID = -1;
		long AccountID = -1;
		double InterestRate = 0.0; // 合同获得利率
		Timestamp EndDate = null;
		long ContractID = -1;
		long DiscreID = -1;
		String strDays = "";
		String strInterest = "";
		String strCompoundInterest = "";
		String strForfeitInterest = "";
		// 计提利息
		String strDrawingInterest = "";
		// 手续费
		String strHandlingCharge = "";
		// 担保费
		String strAssuranceCharge = "";
		// 利息税费
		String strInterestTaxCharge = "";
		String strCreator = "";
		String strCreateDate = "";

		double BalanceSum = 0; // 余额合计
		double InterestSum = 0; // 利息合计
		double AssureSum = 0; // 担保费合计
		double CompoundInterestSum = 0; // 复利合计
		double ForfeitInterestSum = 0; // 罚息合计
		double InterestTaxSum = 0; // 利息税费合计
		double CommisionSum = 0; // 手续费合计

		String strBalanceSum = ""; // 余额合计
		String strInterestSum = ""; // 利息合计
		String strAssureSum = ""; // 担保费合计
		String strCompoundInterestSum = ""; // 复利合计
		String strForfeitInterestSum = ""; // 罚息合计
		String strInterestTaxSum = ""; // 利息税费合计
		String strCommisionSum = ""; // 手续费合计

		try {

			if (rs != null) {

				while (rs.next()) {
					InterestQueryResultInfo resultInfo = new InterestQueryResultInfo();
					accountTypeID = rs.getLong("accountTypeID");
					fixedDepositNo = rs.getString("fixedDepositNo");
					loanPreDrawInterest = rs.getDouble("loanPreDrawInterest");
					contractNo = rs.getString("contractNo");
					PayFormNo = rs.getString("payFormNo");
					SubAccountID = rs.getLong("subAccountID");
					AccountID = rs.getLong("accountID");
					InterestRate = rs.getDouble("discountRate"); // 合同获得利率
					EndDate = rs.getTimestamp("enddate");
					ContractID = rs.getLong("contractID");
					DiscreID = rs.getLong("discreID");

					/**
					 * 进入 info.setInterest(Interest); --利息 true
					 */
					if (qInfo.isBInterest()) {

						log.info("-------开始处理利息查询数据--------");

						Sett_AccountTypeDAO sett_AccountTypeDAO = new Sett_AccountTypeDAO();
						AccountTypeInfo accountTypeInfo = null;

						log.debug("---------判断账户类型------------");
						try {
							accountTypeInfo = sett_AccountTypeDAO
									.findByID(accountTypeID);
						} catch (SQLException e) {
							e.printStackTrace();
						}

						if (accountTypeInfo != null) {
							/**
							 * 如果是贴现帐户传入参数增加 贴现利率 info.getInterestRate() 结束时间
							 * resultInfo.getEndDate()
							 */
							if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.DISCOUNT) {
								resultInfo = interestSettlement
										.getDiscountInterestInfo(
												fixedDepositNo,
												loanPreDrawInterest,
												contractNo,
												PayFormNo,
												SubAccountID,
												AccountID,
												accountTypeID,
												InterestRate // 合同获得利率
												,
												EndDate,
												ContractID,
												DiscreID,
												qInfo.getOfficeID(),
												qInfo.getCurrencyID(),
												qInfo.getClearInterest(),
												Env.getSystemDate(conInternal,
														qInfo.getOfficeID(),
														qInfo.getCurrencyID()),
												SETTConstant.InterestFeeType.INTEREST,
												io, ib, transDetailDAO);
							}
							// 是否活期账户组类型
							// 是否保证金账户组类型
							else if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CURRENT
									|| accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.BAK
									|| accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.RESERVE
									|| accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.LENDING
									|| accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.MARGIN) {
								resultInfo = interestSettlement
										.getInterestInfo(
												fixedDepositNo,
												loanPreDrawInterest,
												contractNo,
												PayFormNo,
												SubAccountID,
												AccountID,
												accountTypeID,
												qInfo.getOfficeID(),
												qInfo.getCurrencyID(),
												qInfo.getClearInterest(),
												Env.getSystemDate(conInternal,
														qInfo.getOfficeID(),
														qInfo.getCurrencyID()),
												SETTConstant.InterestFeeType.INTEREST,
												io, ib, transDetailDAO);
							} else {
								long payFormID = -1;
								payFormID = resultInfo.getPayFormID();
								resultInfo = interestSettlement
										.getInterestInfo(
												fixedDepositNo,
												loanPreDrawInterest,
												contractNo,
												PayFormNo,
												SubAccountID,
												AccountID,
												accountTypeID,
												qInfo.getOfficeID(),
												qInfo.getCurrencyID(),
												qInfo.getClearInterest(),
												Env.getSystemDate(conInternal,
														qInfo.getOfficeID(),
														qInfo.getCurrencyID()),
												SETTConstant.InterestFeeType.INTEREST,
												io, ib, transDetailDAO,
												subaccDAO);

								resultInfo.setPayFormID(payFormID);
							}

							// 处理利息值
							resultInfo.setInterest(UtilOperation.Arith.round(
									resultInfo.getInterest(), 2));
							log.info("-------处理利息查询数据结束--------");
						}
					}

					if (qInfo.isBCompoundInterest()) {
						log.info("-------开始处理复利数据--------");
						long payFormID = -1;
						payFormID = resultInfo.getPayFormID();
						resultInfo = interestSettlement.getInterestInfo(
								fixedDepositNo, loanPreDrawInterest,
								contractNo, PayFormNo, SubAccountID, AccountID,
								accountTypeID, qInfo.getOfficeID(), qInfo
										.getCurrencyID(), qInfo
										.getClearInterest(), Env.getSystemDate(
										conInternal, qInfo.getOfficeID(), qInfo
												.getCurrencyID()),
								SETTConstant.InterestFeeType.COMPOUNDINTEREST,
								io, ib, transDetailDAO);
						resultInfo.setPayFormID(payFormID);
						log.info("-------处理复利数据结束--------");
					}

					if (qInfo.isBForfeitInterest()) {
						log.info("-------开始处理罚息数据--------");
						long payFormID = -1;
						payFormID = resultInfo.getPayFormID();
						resultInfo = interestSettlement.getInterestInfo(
								fixedDepositNo, loanPreDrawInterest,
								contractNo, PayFormNo, SubAccountID, AccountID,
								accountTypeID, qInfo.getOfficeID(), qInfo
										.getCurrencyID(), qInfo
										.getClearInterest(), Env.getSystemDate(
										conInternal, qInfo.getOfficeID(), qInfo
												.getCurrencyID()),
								SETTConstant.InterestFeeType.FORFEITINTEREST,
								io, ib, transDetailDAO);
						resultInfo.setPayFormID(payFormID);
						log.info("-------处理罚息数据结束--------");
					}

					if (qInfo.getFeeType() == SETTConstant.InterestFeeType.COMMISION) {
						log.info("-------开始处理手续费数据--------");
						resultInfo = interestSettlement.getInterestInfo(
								fixedDepositNo, loanPreDrawInterest,
								contractNo, PayFormNo, SubAccountID, AccountID,
								accountTypeID, qInfo.getOfficeID(), qInfo
										.getCurrencyID(), qInfo
										.getClearInterest(), Env.getSystemDate(
										conInternal, qInfo.getOfficeID(), qInfo
												.getCurrencyID()),
								SETTConstant.InterestFeeType.COMMISION, io, ib,
								transDetailDAO);

						log.info("-------处理手续费数据结束--------");
					}

					if (qInfo.getFeeType() == SETTConstant.InterestFeeType.ASSURE) {
						log.info("-------开始处理担保费数据--------");
						resultInfo = interestSettlement.getInterestInfo(
								fixedDepositNo, loanPreDrawInterest,
								contractNo, PayFormNo, SubAccountID, AccountID,
								accountTypeID, qInfo.getOfficeID(), qInfo
										.getCurrencyID(), qInfo
										.getClearInterest(), Env.getSystemDate(
										conInternal, qInfo.getOfficeID(), qInfo
												.getCurrencyID()),
								SETTConstant.InterestFeeType.ASSURE, io, ib,
								transDetailDAO);
						log.info("-------处理担保费数据结束--------");
					}

					/**
					 * 进入 info.setFeeType(lFeeType); --计提利息 6
					 */

					// 计提利息
					if (qInfo.getFeeType() == SETTConstant.InterestFeeType.PREDRAWINTEREST) {

						log.info("-------开始处理计提利息数据--------");
						long payFormID = -1;
						payFormID = resultInfo.getPayFormID();
						resultInfo = interestSettlement.getInterestInfo(
								fixedDepositNo, loanPreDrawInterest,
								contractNo, PayFormNo, SubAccountID, AccountID,
								accountTypeID, qInfo.getOfficeID(), qInfo
										.getCurrencyID(), qInfo
										.getClearInterest(), Env.getSystemDate(
										conInternal, qInfo.getOfficeID(), qInfo
												.getCurrencyID()),
								SETTConstant.InterestFeeType.PREDRAWINTEREST // 6
								, io, ib, transDetailDAO);
						resultInfo.setPayFormID(payFormID);
						log.info("-------处理计提利息数据结束--------");
					}

					// 合计
					BalanceSum = BalanceSum
							+ UtilOperation.Arith.round(
									resultInfo.getBalance(), 2);
					InterestSum = InterestSum
							+ UtilOperation.Arith.round(resultInfo
									.getInterest(), 2);
					InterestSum = InterestSum
							+ UtilOperation.Arith.round(resultInfo
									.getNegotiateInterest(), 2);
					AssureSum = AssureSum
							+ UtilOperation.Arith.round(resultInfo
									.getAssuranceCharge(), 2);
					CompoundInterestSum = CompoundInterestSum
							+ UtilOperation.Arith.round(resultInfo
									.getCompoundInterest(), 2);
					ForfeitInterestSum = ForfeitInterestSum
							+ UtilOperation.Arith.round(resultInfo
									.getForfeitInterest(), 2);
					InterestTaxSum = InterestTaxSum
							+ UtilOperation.Arith.round(resultInfo
									.getInterestTaxCharge(), 2);
					CommisionSum = CommisionSum
							+ UtilOperation.Arith.round(resultInfo
									.getHandlingCharge(), 2);

				}

				strBalanceSum = DataFormat.formatDisabledAmount(BalanceSum, 2);
				strInterestSum = DataFormat
						.formatDisabledAmount(InterestSum, 2);
				strAssureSum = DataFormat.formatDisabledAmount(AssureSum, 2);
				strCompoundInterestSum = DataFormat.formatDisabledAmount(
						CompoundInterestSum, 2);
				strForfeitInterestSum = DataFormat.formatDisabledAmount(
						ForfeitInterestSum, 2);
				strInterestTaxSum = DataFormat.formatDisabledAmount(
						InterestTaxSum, 2);
				strCommisionSum = DataFormat.formatDisabledAmount(CommisionSum,
						2);

				list.add("余额合计{" + strBalanceSum + "}");
				list.add("利息合计{" + strInterestSum + "}");
				list.add("手续费合计{" + strCommisionSum + "}");
				list.add("担保费合计{" + strAssureSum + "}");
				list.add("复利合计{" + strCompoundInterestSum + "}");
				list.add("罚息合计{" + strForfeitInterestSum + "}");
				list.add("利息税费合计{" + strInterestTaxSum + "}");
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException(e.getMessage());
		} finally {
			conInternal.close();
		}

		return list;

	}

	public PagerInfo balanceInterest(InterestQueryInfo qInfo, Map map)
			throws Exception {
		PagerInfo pagerInfo = null;
		try {
			pagerInfo = new PagerInfo();
			map.put("qInfo", qInfo);
			String sql = "select * from userinfo where id = 1 ";
			// sql = interestQueryDao.balanceInterestSQL(qInfo);
			// 得到查询SQL
			pagerInfo.setSqlString(sql);

			pagerInfo.setExtensionMothod(InterestQueryBiz.class,
					"balanceInterestResultSetHandle", map);

			pagerInfo.setTotalExtensionMothod(InterestQueryBiz.class,
					"getBalanceInterestSumAmount", map);

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
	}

	public ArrayList balanceInterestResultSetHandle(ResultSet rs, Map map)
			throws Exception {

		ArrayList resultList = new ArrayList(); // 最终返回结果
		ArrayList cellList = null;// 列
		ResultPagerRowInfo rowInfo = null;// 行

		Vector result = (Vector) map.get("result");
		InterestSettmentInfo settmentInfo = (InterestSettmentInfo) map
				.get("settmentInfo");

		try {
			for (Object obj : result) {
				InterestQueryResultInfo info = (InterestQueryResultInfo) obj;

				String strAccountNo = ""; // 账户号
				String strAccountName = ""; // 账户名称
				String strContractNo = ""; // 合同号
				String strPayFormNo = ""; // 放款通知单号
				String strFixedDepositNo = ""; // 定期单据号
				String strStartDate = ""; // 开始日期
				String strEndDate = ""; // 结束日期
				String strDays = ""; // 天数
				String strBalance = ""; // 余额
				String strInterestRate = ""; // 利率
				String strInterest = ""; // 利息
				String strCompoundInterest = ""; // 复利
				String strForfeitInterest = ""; // 罚息
				String strDrawingInterest = ""; // 计提利息
				String strHandlingCharge = ""; // 手续费
				String strAssuranceCharge = ""; // 担保费
				String strInterestTaxCharge = ""; // 利息税费
				String strCreator = ""; // 生成人
				String strCreateDate = "";
				; // 生成日期
				String strTransNo = ""; // 交易号
				String strCommInterest = ""; // 正常利息
				String strNegotiateInterest = ""; // 协定利息

				double dBalance = 0.0; // 余额
				double dInterest = 0.0; // 利息
				double dCompoundInterest = 0.0; // 复利
				double dForfeitInterest = 0.0; // 罚息
				double dDrawingInterest = 0.0; // 计提利息
				double dHandlingCharge = 0.0; // 手续费
				double dAssuranceCharge = 0.0; // 担保费
				double dInterestTaxCharge = 0.0; // 利息税费
				strTransNo = DataFormat.formatEmptyString(info.getExchangeNo());
				strAccountName = DataFormat.formatEmptyString(NameRef
						.getAccountNameByID(info.getAccountID()));
				strAccountNo = DataFormat
						.formatEmptyString(info.getAccountNo());
				strContractNo = DataFormat.formatString(info.getContractNo());
				strPayFormNo = DataFormat.formatString(info.getPayFormNo());
				strFixedDepositNo = DataFormat.formatString(info
						.getFixedDepositNo());
				strStartDate = DataFormat.formatString(DataFormat
						.formatDate(info.getStartDate()));
				strEndDate = DataFormat.formatString(DataFormat.formatDate(info
						.getEndDate()));
				if (info.getDays() > 0) {
					strDays = DataFormat.formatString(String.valueOf(info
							.getDays()));
				}
				strInterestRate = DataFormat.formatRate(info.getInterestRate());

				/** *** Boxu Add 2008年4月22日 利息费用结算处理时，余额、利息、计提利息都为0的记录不要显示出来 **** */
				// 利息
				if (info.getInterestType() == SETTConstant.InterestFeeType.INTEREST) {
					dBalance = DataFormat.formatDouble(info.getBalance());
					dInterest = DataFormat.formatDouble(info.getRealInterest());
					dDrawingInterest = DataFormat.formatDouble(info
							.getDrawingInterest());
				}
				// 计提利息
				if (info.getInterestType() == SETTConstant.InterestFeeType.PREDRAWINTEREST) {
					dDrawingInterest = DataFormat.formatDouble(info
							.getDrawingInterest());
					if (dDrawingInterest == 0.0) {
						continue;
					}
				}
				// 复利
				if (info.getInterestType() == SETTConstant.InterestFeeType.COMPOUNDINTEREST) {
					dCompoundInterest = DataFormat.formatDouble(info
							.getRealInterest());
					if (dCompoundInterest == 0.0) {
						continue;
					}
				}
				// 罚息
				if (info.getInterestType() == SETTConstant.InterestFeeType.FORFEITINTEREST) {
					dForfeitInterest = DataFormat.formatDouble(info
							.getForfeitInterest());
					if (dForfeitInterest == 0.0) {
						continue;
					}
				}
				// 手续费
				if (info.getInterestType() == SETTConstant.InterestFeeType.COMMISION) {
					dHandlingCharge = DataFormat.formatDouble(info
							.getHandlingCharge());
					if (dHandlingCharge == 0.0) {
						continue;
					}
				}
				// 担保费
				if (info.getInterestType() == SETTConstant.InterestFeeType.ASSURE) {
					dAssuranceCharge = DataFormat.formatDouble(info
							.getAssuranceCharge());
					if (dAssuranceCharge == 0.0) {
						continue;
					}
				}

				// 余额
				strBalance = DataFormat.formatDisabledAmount(info.getBalance(),
						2);
				// 利息
				if (info.getInterestType() == SETTConstant.InterestFeeType.INTEREST) {
					if (info.getRealInterest() > 0) {
						strInterest = DataFormat.formatDisabledAmount(
								UtilOperation.Arith.round(info
										.getRealInterest(), 2), 2);
					} else {
						strInterest = DataFormat
								.formatDisabledAmount(UtilOperation.Arith
										.round(info.getInterest(), 2)
										+ UtilOperation.Arith.round(info
												.getNegotiateInterest(), 2));
					}
				}
				// 复利
				if (info.getInterestType() == SETTConstant.InterestFeeType.COMPOUNDINTEREST) {
					if (info.getRealInterest() > 0) {
						strCompoundInterest = DataFormat.formatDisabledAmount(
								DataFormat.formatDouble(info.getRealInterest(),
										2), 2);
					} else {
						strCompoundInterest = DataFormat.formatDisabledAmount(
								DataFormat.formatDouble(info
										.getCompoundInterest(), 2), 2);
					}
				}
				// 罚息
				if (info.getInterestType() == SETTConstant.InterestFeeType.FORFEITINTEREST) {
					strForfeitInterest = DataFormat.formatDisabledAmount(
							DataFormat.formatDouble(info.getForfeitInterest(),
									2), 2);
				}
				// 计提利息
				strDrawingInterest = DataFormat.formatDisabledAmount(DataFormat
						.formatDouble(info.getDrawingInterest(), 2), 2);
				// 手续费
				strHandlingCharge = DataFormat.formatDisabledAmount(DataFormat
						.formatDouble(info.getHandlingCharge(), 2), 2);
				// 担保费
				strAssuranceCharge = DataFormat.formatDisabledAmount(DataFormat
						.formatDouble(info.getAssuranceCharge(), 2), 2);
				// 利息税费
				strInterestTaxCharge = DataFormat
						.formatDisabledAmount(DataFormat.formatDouble(info
								.getInterestTaxCharge(), 2), 2);

				// 正常利息
				strCommInterest = DataFormat.formatDisabledAmount(DataFormat
						.formatDouble(info.getInterest(), 2), 2);
				// 协定利息
				strNegotiateInterest = DataFormat
						.formatDisabledAmount(DataFormat.formatDouble(info
								.getNegotiateInterest(), 2), 2);

				strCreator = DataFormat.formatString(NameRef
						.getUserNameByID(settmentInfo.getInputUserID()));
				strCreateDate = Env.getSystemDateString(settmentInfo
						.getOfficeID(), settmentInfo.getCurrencyID());

				// 存储列数据
				cellList = new ArrayList();
				PagerTools.returnCellList(cellList,
						info.isSuccess() == true ? strAccountNo
								: "<font color=\"red\">" + strAccountNo
										+ "</font>");
				PagerTools.returnCellList(cellList,
						info.isSuccess() == true ? strAccountName
								: "<font color=\"red\">" + strAccountName
										+ "</font>");
				PagerTools.returnCellList(cellList,
						info.isSuccess() == true ? strContractNo
								: "<font color=\"red\">" + strContractNo
										+ "</font>");
				PagerTools.returnCellList(cellList,
						info.isSuccess() == true ? strPayFormNo
								: "<font color=\"red\">" + strPayFormNo
										+ "</font>");
				PagerTools.returnCellList(cellList,
						info.isSuccess() == true ? strFixedDepositNo
								: "<font color=\"red\">" + strFixedDepositNo
										+ "</font>");

				PagerTools.returnCellList(cellList,
						info.isSuccess() == true ? strStartDate
								: "<font color=\"red\">" + strStartDate
										+ "</font>");
				PagerTools.returnCellList(cellList,
						info.isSuccess() == true ? strEndDate
								: "<font color=\"red\">" + strEndDate
										+ "</font>");
				PagerTools.returnCellList(cellList,
						info.isSuccess() == true ? strDays
								: "<font color=\"red\">" + strDays + "</font>");
				PagerTools.returnCellList(cellList,
						info.isSuccess() == true ? strBalance
								: "<font color=\"red\">" + strBalance
										+ "</font>");
				PagerTools.returnCellList(cellList,
						info.isSuccess() == true ? strInterestRate
								: "<font color=\"red\">" + strInterestRate
										+ "</font>");

				PagerTools.returnCellList(cellList,
						info.isSuccess() == true ? strInterest
								: "<font color=\"red\">" + strInterest
										+ "</font>");
				PagerTools.returnCellList(cellList,
						info.isSuccess() == true ? strCommInterest
								: "<font color=\"red\">" + strCommInterest
										+ "</font>");
				PagerTools.returnCellList(cellList,
						info.isSuccess() == true ? strNegotiateInterest
								: "<font color=\"red\">" + strNegotiateInterest
										+ "</font>");
				PagerTools.returnCellList(cellList,
						info.isSuccess() == true ? strCompoundInterest
								: "<font color=\"red\">" + strCompoundInterest
										+ "</font>");
				PagerTools.returnCellList(cellList,
						info.isSuccess() == true ? strForfeitInterest
								: "<font color=\"red\">" + strForfeitInterest
										+ "</font>");

				PagerTools.returnCellList(cellList,
						info.isSuccess() == true ? strDrawingInterest
								: "<font color=\"red\">" + strDrawingInterest
										+ "</font>");
				PagerTools.returnCellList(cellList,
						info.isSuccess() == true ? strHandlingCharge
								: "<font color=\"red\">" + strHandlingCharge
										+ "</font>");
				PagerTools.returnCellList(cellList,
						info.isSuccess() == true ? strAssuranceCharge
								: "<font color=\"red\">" + strAssuranceCharge
										+ "</font>");
				PagerTools.returnCellList(cellList,
						info.isSuccess() == true ? strInterestTaxCharge
								: "<font color=\"red\">" + strInterestTaxCharge
										+ "</font>");
				PagerTools.returnCellList(cellList,
						info.isSuccess() == true ? strCreator
								: "<font color=\"red\">" + strCreator
										+ "</font>");

				PagerTools.returnCellList(cellList,
						info.isSuccess() == true ? strCreateDate
								: "<font color=\"red\">" + strCreateDate
										+ "</font>");
				PagerTools.returnCellList(cellList,
						info.isSuccess() == true ? "成功"
								: "<font color=\"red\">失败</font>");
				PagerTools.returnCellList(cellList,
						info.isSuccess() == true ? "" : "<font color=\"red\">"
								+ info.getStrPromptMessage() + "</font>");

				// 存储行数据
				rowInfo = new ResultPagerRowInfo();
				rowInfo.setCell(cellList);
				// rowInfo.setId(String.valueOf(rs.getLong("rownum1")));

				// 返回数据
				resultList.add(rowInfo);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException(e.getMessage());
		}

		return resultList;

	}

	public ArrayList getBalanceInterestSumAmount(ResultSet rs, Map map)
			throws Exception {

		ArrayList list = new ArrayList();
		Vector result = (Vector) map.get("result");

		try {
			String strBalanceSum = ""; // 余额合计
			String strInterestSum = ""; // 利息合计
			String strAssureSum = ""; // 担保费合计
			String strCompoundInterestSum = ""; // 复利合计
			String strForfeitInterestSum = ""; // 罚息合计
			String strInterestTaxSum = ""; // 利息税费合计
			String strCommisionSum = ""; // 手续费合计

			double BalanceSum = 0; // 余额合计
			double InterestSum = 0; // 利息合计
			double AssureSum = 0; // 担保费合计
			double CompoundInterestSum = 0; // 复利合计
			double ForfeitInterestSum = 0; // 罚息合计
			double InterestTaxSum = 0; // 利息税费合计
			double CommisionSum = 0; // 手续费合计

			for (Object obj : result) {
				InterestQueryResultInfo info = (InterestQueryResultInfo) obj;

				// 合计
				BalanceSum = BalanceSum
						+ UtilOperation.Arith.round(info.getBalance(), 2);
				InterestSum = InterestSum
						+ UtilOperation.Arith.round(info.getInterest(), 2);
				InterestSum = InterestSum
						+ UtilOperation.Arith.round(
								info.getNegotiateInterest(), 2);
				AssureSum = AssureSum
						+ UtilOperation.Arith.round(info.getAssuranceCharge(),
								2);
				CompoundInterestSum = CompoundInterestSum
						+ UtilOperation.Arith.round(info.getCompoundInterest(),
								2);
				ForfeitInterestSum = ForfeitInterestSum
						+ UtilOperation.Arith.round(info.getForfeitInterest(),
								2);
				InterestTaxSum = InterestTaxSum
						+ UtilOperation.Arith.round(
								info.getInterestTaxCharge(), 2);
				CommisionSum = CommisionSum
						+ UtilOperation.Arith
								.round(info.getHandlingCharge(), 2);

			}

			strBalanceSum = DataFormat.formatDisabledAmount(BalanceSum, 2);
			strInterestSum = DataFormat.formatDisabledAmount(InterestSum, 2);
			strAssureSum = DataFormat.formatDisabledAmount(AssureSum, 2);
			strCompoundInterestSum = DataFormat.formatDisabledAmount(
					CompoundInterestSum, 2);
			strForfeitInterestSum = DataFormat.formatDisabledAmount(
					ForfeitInterestSum, 2);
			strInterestTaxSum = DataFormat.formatDisabledAmount(InterestTaxSum,
					2);
			strCommisionSum = DataFormat.formatDisabledAmount(CommisionSum, 2);

			list.add("余额合计{" + strBalanceSum + "}");
			list.add("利息合计{" + strInterestSum + "}");
			list.add("手续费合计{" + strCommisionSum + "}");
			list.add("担保费合计{" + strAssureSum + "}");
			list.add("复利合计{" + strCompoundInterestSum + "}");
			list.add("罚息合计{" + strForfeitInterestSum + "}");
			list.add("利息税费合计{" + strInterestTaxSum + "}");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException(e.getMessage());
		}

		return list;

	}

	public PagerInfo calInterest(InterestQueryInfo qInfo, Map map)
			throws Exception {
		PagerInfo pagerInfo = null;
		try {
			pagerInfo = new PagerInfo();
			map.put("qInfo", qInfo);
			String sql = "select * from userinfo where id = 1 ";
			// sql = interestQueryDao.balanceInterestSQL(qInfo);
			// 得到查询SQL
			pagerInfo.setSqlString(sql);

			pagerInfo.setExtensionMothod(InterestQueryBiz.class,
					"calInterestResultSetHandle", map);

			pagerInfo.setTotalExtensionMothod(InterestQueryBiz.class,
					"getBalanceInterestSumAmount", map);

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return pagerInfo;
	}

	public ArrayList calInterestResultSetHandle(ResultSet rs, Map map)
			throws Exception {

		ArrayList resultList = new ArrayList(); // 最终返回结果
		ArrayList cellList = null;// 列
		ResultPagerRowInfo rowInfo = null;// 行
		InterestQueryInfo qInfo = (InterestQueryInfo) map.get("qInfo");
		Vector result = (Vector) map.get("result");
		InterestSettmentInfo settmentInfo = (InterestSettmentInfo) map
				.get("settmentInfo");
		int i = 0;

		try {
			for (Object obj : result) {

				InterestQueryResultInfo info = (InterestQueryResultInfo) obj;

				String strAccountNo = ""; // 账户号
				String strAccountName = ""; // 账户名称
				String strContractNo = ""; // 合同号
				String strPayFormNo = ""; // 放款通知单号
				String strFixedDepositNo = ""; // 定期单据号
				String strStartDate = ""; // 开始日期
				String strEndDate = ""; // 结束日期
				String strDays = ""; // 天数
				String strBalance = ""; // 余额
				String strInterestRate = ""; // 利率
				String strInterest = ""; // 利息
				String strCompoundInterest = ""; // 复利
				String strForfeitInterest = ""; // 罚息
				String strDrawingInterest = ""; // 计提利息
				String strHandlingCharge = ""; // 手续费
				String strAssuranceCharge = ""; // 担保费
				String strInterestTaxCharge = ""; // 利息税费
				String strCreator = ""; // 生成人
				String strCreateDate = "";
				; // 生成日期
				String strTransNo = ""; // 交易号
				String strCommInterest = ""; // 正常利息
				String strNegotiateInterest = ""; // 协定利息

				double dBalance = 0.0; // 余额
				double dInterest = 0.0; // 利息
				double dCompoundInterest = 0.0; // 复利
				double dForfeitInterest = 0.0; // 罚息
				double dDrawingInterest = 0.0; // 计提利息
				double dHandlingCharge = 0.0; // 手续费
				double dAssuranceCharge = 0.0; // 担保费
				double dInterestTaxCharge = 0.0; // 利息税费
				strTransNo = DataFormat.formatEmptyString(info.getExchangeNo());
				strAccountName = DataFormat.formatEmptyString(NameRef
						.getAccountNameByID(info.getAccountID()));
				strAccountNo = DataFormat
						.formatEmptyString(info.getAccountNo());
				strContractNo = DataFormat.formatString(info.getContractNo());
				strPayFormNo = DataFormat.formatString(info.getPayFormNo());
				strFixedDepositNo = DataFormat.formatString(info
						.getFixedDepositNo());
				strStartDate = DataFormat.formatString(DataFormat
						.formatDate(info.getStartDate()));
				strEndDate = DataFormat.formatString(DataFormat.formatDate(info
						.getEndDate()));
				if (info.getDays() > 0) {
					strDays = DataFormat.formatString(String.valueOf(info
							.getDays()));
				}
				strInterestRate = DataFormat.formatRate(info.getInterestRate());

				/** *** Boxu Add 2008年4月22日 利息费用结算处理时，余额、利息、计提利息都为0的记录不要显示出来 **** */
				// 利息
				if (info.getInterestType() == SETTConstant.InterestFeeType.INTEREST) {
					dBalance = DataFormat.formatDouble(info.getBalance());
					dInterest = DataFormat.formatDouble(info.getRealInterest());
					dDrawingInterest = DataFormat.formatDouble(info
							.getDrawingInterest());
				}
				// 计提利息
				if (info.getInterestType() == SETTConstant.InterestFeeType.PREDRAWINTEREST) {
					dDrawingInterest = DataFormat.formatDouble(info
							.getDrawingInterest());
					if (dDrawingInterest == 0.0) {
						continue;
					}
				}
				// 复利
				if (info.getInterestType() == SETTConstant.InterestFeeType.COMPOUNDINTEREST) {
					dCompoundInterest = DataFormat.formatDouble(info
							.getRealInterest());
					if (dCompoundInterest == 0.0) {
						continue;
					}
				}
				// 罚息
				if (info.getInterestType() == SETTConstant.InterestFeeType.FORFEITINTEREST) {
					dForfeitInterest = DataFormat.formatDouble(info
							.getForfeitInterest());
					if (dForfeitInterest == 0.0) {
						continue;
					}
				}
				// 手续费
				if (info.getInterestType() == SETTConstant.InterestFeeType.COMMISION) {
					dHandlingCharge = DataFormat.formatDouble(info
							.getHandlingCharge());
					if (dHandlingCharge == 0.0) {
						continue;
					}
				}
				// 担保费
				if (info.getInterestType() == SETTConstant.InterestFeeType.ASSURE) {
					dAssuranceCharge = DataFormat.formatDouble(info
							.getAssuranceCharge());
					if (dAssuranceCharge == 0.0) {
						continue;
					}
				}

				// 余额
				strBalance = DataFormat.formatDisabledAmount(info.getBalance(),
						2);
				// 利息
				if (info.getInterestType() == SETTConstant.InterestFeeType.INTEREST) {
					if (info.getRealInterest() > 0) {
						strInterest = DataFormat.formatDisabledAmount(
								UtilOperation.Arith.round(info
										.getRealInterest(), 2), 2);
					} else {
						strInterest = DataFormat
								.formatDisabledAmount(UtilOperation.Arith
										.round(info.getInterest(), 2)
										+ UtilOperation.Arith.round(info
												.getNegotiateInterest(), 2));
					}
				}
				// 复利
				if (info.getInterestType() == SETTConstant.InterestFeeType.COMPOUNDINTEREST) {
					if (info.getRealInterest() > 0) {
						strCompoundInterest = DataFormat.formatDisabledAmount(
								DataFormat.formatDouble(info.getRealInterest(),
										2), 2);
					} else {
						strCompoundInterest = DataFormat.formatDisabledAmount(
								DataFormat.formatDouble(info
										.getCompoundInterest(), 2), 2);
					}
				}
				// 罚息
				if (info.getInterestType() == SETTConstant.InterestFeeType.FORFEITINTEREST) {
					strForfeitInterest = DataFormat.formatDisabledAmount(
							DataFormat.formatDouble(info.getForfeitInterest(),
									2), 2);
				}
				// 计提利息
				strDrawingInterest = DataFormat.formatDisabledAmount(DataFormat
						.formatDouble(info.getDrawingInterest(), 2), 2);
				// 手续费
				strHandlingCharge = DataFormat.formatDisabledAmount(DataFormat
						.formatDouble(info.getHandlingCharge(), 2), 2);
				// 担保费
				strAssuranceCharge = DataFormat.formatDisabledAmount(DataFormat
						.formatDouble(info.getAssuranceCharge(), 2), 2);
				// 利息税费
				strInterestTaxCharge = DataFormat
						.formatDisabledAmount(DataFormat.formatDouble(info
								.getInterestTaxCharge(), 2), 2);

				// 正常利息
				strCommInterest = DataFormat.formatDisabledAmount(DataFormat
						.formatDouble(info.getInterest(), 2), 2);
				// 协定利息
				strNegotiateInterest = DataFormat
						.formatDisabledAmount(DataFormat.formatDouble(info
								.getNegotiateInterest(), 2), 2);

				strCreator = DataFormat.formatString(NameRef
						.getUserNameByID(settmentInfo.getInputUserID()));
				strCreateDate = Env.getSystemDateString(settmentInfo
						.getOfficeID(), settmentInfo.getCurrencyID());

				// 存储列数据
				cellList = new ArrayList();
				PagerTools.returnCellList(cellList, i + "_"
						+ info.getAccountTypeID() + "_" + info.getAccountID()
						+ "_" + info.getAccountNo() + "_"
						+ info.getSubAccountID() + "_" + info.getPayFormID()
						+ "_" + info.getInterestType() + "_" + strInterest
						+ "_" + strCompoundInterest + "_" + strForfeitInterest
						+ "_" + info.getNegotiateInterest() + "_"
						+ strInterestTaxCharge + "_" + strAssuranceCharge + "_"
						+ strHandlingCharge + "_" + strCreateDate + "_"
						+ DataFormat.formatDate(info.getStartDate()) + "_"
						+ DataFormat.formatDate(info.getEndDate()) + "_"
						+ info.getRecieveInterestAccountID() + "_"
						+ info.getPayInterestAccountID() + "_"
						+ info.getRealInterest());

				PagerTools.returnCellList(cellList, strAccountNo);
				PagerTools.returnCellList(cellList, DataFormat
						.formatEmptyString(NameRef.getAccountNameByID(info
								.getAccountID())));
				PagerTools.returnCellList(cellList, DataFormat
						.formatString(info.getContractNo()));
				PagerTools.returnCellList(cellList, DataFormat
						.formatString(info.getPayFormNo()));
				PagerTools.returnCellList(cellList, DataFormat
						.formatString(info.getFixedDepositNo()));

				PagerTools.returnCellList(cellList, DataFormat
						.formatString(DataFormat
								.formatDate(info.getStartDate())));
				PagerTools
						.returnCellList(cellList, DataFormat
								.formatString(DataFormat.formatDate(info
										.getEndDate())));
				if (info.getDays() > 0) {
					strDays = DataFormat.formatString(String.valueOf(info
							.getDays()));
				}
				PagerTools.returnCellList(cellList, strDays);
				PagerTools.returnCellList(cellList, DataFormat
						.formatDisabledAmount(info.getBalance(), 2));
				PagerTools.returnCellList(cellList, DataFormat.formatRate(info
						.getInterestRate()));

				// 利息
				if (info.getInterestType() == SETTConstant.InterestFeeType.INTEREST) {
					strInterest = DataFormat.formatDisabledAmount(
							UtilOperation.Arith.round(info.getInterest(), 2)
									+ UtilOperation.Arith.round(info
											.getNegotiateInterest(), 2), 2);
				}
				PagerTools.returnCellList(cellList, strInterest);
				PagerTools.returnCellList(cellList, DataFormat
						.formatDisabledAmount(DataFormat.formatDouble(info
								.getInterest(), 2), 2));
				PagerTools.returnCellList(cellList, DataFormat
						.formatDisabledAmount(DataFormat.formatDouble(info
								.getNegotiateInterest(), 2), 2));
				PagerTools.returnCellList(cellList, DataFormat
						.formatDisabledAmount(DataFormat.formatDouble(info
								.getCompoundInterest(), 2), 2));
				// 罚息
				if (info.getInterestType() == SETTConstant.InterestFeeType.FORFEITINTEREST) {
					strForfeitInterest = DataFormat.formatDisabledAmount(
							DataFormat.formatDouble(info.getForfeitInterest(),
									2), 2);
				}
				PagerTools.returnCellList(cellList, strForfeitInterest);

				PagerTools.returnCellList(cellList, DataFormat
						.formatDisabledAmount(DataFormat.formatDouble(info
								.getDrawingInterest(), 2), 2));
				PagerTools.returnCellList(cellList, DataFormat
						.formatDisabledAmount(DataFormat.formatDouble(info
								.getHandlingCharge(), 2), 2));
				PagerTools.returnCellList(cellList, DataFormat
						.formatDisabledAmount(DataFormat.formatDouble(info
								.getAssuranceCharge(), 2), 2));
				PagerTools.returnCellList(cellList, DataFormat
						.formatDisabledAmount(DataFormat.formatDouble(info
								.getInterestTaxCharge(), 2), 2));
				PagerTools.returnCellList(cellList, DataFormat
						.formatString(NameRef
								.getUserNameByID(qInfo.getUserID())));

				PagerTools.returnCellList(cellList, Env.getSystemDateString(
						qInfo.getOfficeID(), qInfo.getCurrencyID()));

				// 存储行数据
				rowInfo = new ResultPagerRowInfo();
				rowInfo.setCell(cellList);
				// rowInfo.setId(String.valueOf(rs.getLong("rownum1")));

				// 返回数据
				resultList.add(rowInfo);

				i++;
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}

		return resultList;

	}

	public PagerInfo queryInterestEstimateClientInfo(
			InterestEstimateQueryInfo info) throws Exception {
		PagerInfo pagerInfo = null;
		String sql = null;
		try {
			pagerInfo = new PagerInfo();
			Map paramMap = new HashMap();
			paramMap.put("qInfo", info);

			sql = interestQueryDao.getInterestEstimateClientInfoSQL(info);
			// 得到查询SQL
			pagerInfo.setSqlString(sql);

			pagerInfo.setExtensionMothod(InterestQueryBiz.class,
					"queryInterestEstimateClientInfoResultSetHandle", paramMap);

			pagerInfo.setTotalExtensionMothod(InterestQueryBiz.class,
					"queryInterestEstimateClientInfoSumAmount", paramMap);

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
	}

	public ArrayList queryInterestEstimateClientInfoResultSetHandle(
			ResultSet rs, Map map) throws Exception {
		InterestEstimateQueryInfo qinfo = (InterestEstimateQueryInfo) map
				.get("qInfo");
		ArrayList resultList = new ArrayList(); // 最终返回结果
		ArrayList cellList = null;// 列
		ResultPagerRowInfo rowInfo = null;// 行
		int i = 0;
		while (rs.next()) {

			cellList = new ArrayList();
			InterestEstimateQueryResultInfo detailClientInfo = new InterestEstimateQueryResultInfo();
			QInterestEstimate qobj = new QInterestEstimate();
			detailClientInfo = qobj.queryDetailForClient(qinfo, rs
					.getLong("ClientID"));
			detailClientInfo.setConsignClientCode(rs
					.getString("ClientNo"));
			detailClientInfo.setConsignClientName(rs
					.getString("ClientName"));
			String strClientName = DataFormat.formatString(detailClientInfo
					.getClientName());
			String strClientNo = DataFormat.formatString(detailClientInfo
					.getClientNo());
			String strSelfLoanInterest = DataFormat.formatDisabledAmount(
					detailClientInfo.getSelfLoanInterest(), 2);
			String strCompoundInterest = DataFormat.formatDisabledAmount(
					detailClientInfo.getCompoundInterest(), 2);
			String strConsignLoanInterest = DataFormat.formatDisabledAmount(
					detailClientInfo.getConsignLoanInterest(), 2);
			String strForfeitInterest = DataFormat.formatDisabledAmount(
					detailClientInfo.getForfeitInterest(), 2);
			String strInterestSum = DataFormat.formatDisabledAmount(
					detailClientInfo.getTotalInterest(), 2);
			String strCommission = DataFormat.formatDisabledAmount(
					detailClientInfo.getCommission(), 2);
			String strAssuranceCharge = DataFormat.formatDisabledAmount(
					detailClientInfo.getAssuranceCharge(), 2);
			String strEndDate = DataFormat.formatString(DataFormat
					.formatDate(detailClientInfo.getClearInterestDate()));
			String total = DataFormat.formatDisabledAmount(detailClientInfo
					.getTotal(), 2);
			String strDepositBalance = DataFormat.formatDisabledAmount(
					detailClientInfo.getBalance(), 2);
			String strBackInterest = DataFormat.formatDisabledAmount(
					detailClientInfo.getBackInterest(), 2);

			PagerTools.returnCellList(cellList, i);
			PagerTools.returnCellList(cellList, strClientNo);
			PagerTools.returnCellList(cellList, strClientName);
			PagerTools.returnCellList(cellList, strSelfLoanInterest);
			PagerTools.returnCellList(cellList, strCompoundInterest);
			PagerTools.returnCellList(cellList, strConsignLoanInterest);
			PagerTools.returnCellList(cellList, strForfeitInterest);
			PagerTools.returnCellList(cellList, strInterestSum);
			PagerTools.returnCellList(cellList, strCommission);
			PagerTools.returnCellList(cellList, strAssuranceCharge);
			PagerTools.returnCellList(cellList, strEndDate);
			PagerTools.returnCellList(cellList, total);
			PagerTools.returnCellList(cellList, strBackInterest);

			rowInfo = new ResultPagerRowInfo();
			rowInfo.setCell(cellList);
			rowInfo.setId(String.valueOf(rs.getLong("rownum1")));
			// 返回数据
			resultList.add(rowInfo);
			i++;
		}
		return resultList;

	}

	public ArrayList queryInterestEstimateClientInfoSumAmount(ResultSet rs,
			Map map) throws Exception {
		InterestEstimateQueryInfo qinfo = (InterestEstimateQueryInfo) map
				.get("qInfo");
		QInterestEstimate qobj = new QInterestEstimate();
		InterestEstimateQueryResultInfo interestSumInfo = qobj
				.queryInterestSum(qinfo);
		ArrayList list = new ArrayList();
		String strPayInterestSum = DataFormat
				.formatDisabledAmount(UtilOperation.Arith.round(interestSumInfo
						.getInterest(), 2)
						+ UtilOperation.Arith.round(interestSumInfo
								.getForfeitInterest(), 2)
						+ UtilOperation.Arith.round(interestSumInfo
								.getCompoundInterest(), 2));
		String strPayCommissionSum = DataFormat
				.formatDisabledAmount(interestSumInfo.getCommission());
		String strPayAssuranceSum = DataFormat
				.formatDisabledAmount(interestSumInfo.getAssuranceCharge());
		String strPayInterestTaxSum = DataFormat
				.formatDisabledAmount(interestSumInfo.getInterestTaxCharge());
		list.add("应付利息合计{" + strPayInterestSum + "}");
		list.add("应付担保费合计{" + strPayAssuranceSum + "}");
		list.add("应付手续费合计{" + strPayCommissionSum + "}");
		list.add("应付利息税费合计{" + strPayInterestTaxSum + "}");
		return list;
	}

	public PagerInfo queryInterestEstimateAccountInfo(
			InterestEstimateQueryInfo info) throws Exception {
		PagerInfo pagerInfo = null;
		String sql = null;
		try {
			pagerInfo = new PagerInfo();
			Map paramMap = new HashMap();
			paramMap.put("qInfo", info);

			sql = interestQueryDao.getInterestEstimateAccountInfoSQL(info);
			// 得到查询SQL
			pagerInfo.setSqlString(sql);

			pagerInfo
					.setExtensionMothod(InterestQueryBiz.class,
							"queryInterestEstimateAccountInfoResultSetHandle",
							paramMap);

			pagerInfo.setTotalExtensionMothod(InterestQueryBiz.class,
					"queryInterestEstimateAccountInfoSumAmount", paramMap);

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
	}

	public ArrayList queryInterestEstimateAccountInfoResultSetHandle(
			ResultSet rs, Map map) throws Exception {
		InterestEstimateQueryInfo qinfo = (InterestEstimateQueryInfo) map
				.get("qInfo");
		ArrayList resultList = new ArrayList(); // 最终返回结果
		ArrayList cellList = null;// 列
		ResultPagerRowInfo rowInfo = null;// 行
		int i = 0;
		while (rs.next()) {

			cellList = new ArrayList();
			QInterestEstimate qobj = new QInterestEstimate();
			InterestEstimateQueryResultInfo detailAccountInfo = new InterestEstimateQueryResultInfo();
			detailAccountInfo = qobj.queryDetailForAccount(qinfo, rs
					.getLong("accountID"));

			String strAccountNo = DataFormat.formatString(detailAccountInfo
					.getAccountNo());
			String strClientName = DataFormat.formatString(detailAccountInfo
					.getClientName());
			String strClientNo = DataFormat.formatString(detailAccountInfo
					.getClientNo());
			String strSelfLoanInterest = DataFormat.formatDisabledAmount(
					detailAccountInfo.getSelfLoanInterest(), 2);
			String strCompoundInterest = DataFormat.formatDisabledAmount(
					detailAccountInfo.getCompoundInterest(), 2);
			String strConsignLoanInterest = DataFormat.formatDisabledAmount(
					detailAccountInfo.getConsignLoanInterest(), 2);
			String strForfeitInterest = DataFormat.formatDisabledAmount(
					detailAccountInfo.getForfeitInterest(), 2);
			String strInterestSum = DataFormat.formatDisabledAmount(
					detailAccountInfo.getTotalInterest(), 2);
			String strCommission = DataFormat.formatDisabledAmount(
					detailAccountInfo.getCommission(), 2);
			String strAssuranceCharge = DataFormat.formatDisabledAmount(
					detailAccountInfo.getAssuranceCharge(), 2);
			String strEndDate = DataFormat.formatString(DataFormat
					.formatDate(detailAccountInfo.getClearInterestDate()));
			String total = DataFormat.formatDisabledAmount(detailAccountInfo
					.getTotal(), 2);
			String strDepositBalance = DataFormat.formatDisabledAmount(
					detailAccountInfo.getBalance(), 2);
			String strBackInterest = DataFormat.formatDisabledAmount(
					detailAccountInfo.getBackInterest(), 2);

			PagerTools.returnCellList(cellList, i);
			PagerTools.returnCellList(cellList, strClientNo);
			PagerTools.returnCellList(cellList, strClientName);
			PagerTools.returnCellList(cellList, strSelfLoanInterest);
			PagerTools.returnCellList(cellList, strCompoundInterest);
			PagerTools.returnCellList(cellList, strConsignLoanInterest);
			PagerTools.returnCellList(cellList, strForfeitInterest);
			PagerTools.returnCellList(cellList, strInterestSum);
			PagerTools.returnCellList(cellList, strCommission);
			PagerTools.returnCellList(cellList, strAssuranceCharge);
			PagerTools.returnCellList(cellList, strEndDate);
			PagerTools.returnCellList(cellList, total);
			PagerTools.returnCellList(cellList, strBackInterest);
			
			Collection outInterest = null;
			outInterest = qobj.findCurrentBalanceByClientID(detailAccountInfo
					.getClientID(), qinfo.getOfficeID(), qinfo.getCurrencyID());
			if ((outInterest != null) && (outInterest.size() > 0)) {
				int m = 0;
				Iterator it = outInterest.iterator();
				m++;
				String amount = "";
				String accountNo = "";
				while (it.hasNext()) {
					InterestEstimateQueryResultInfo Info = (InterestEstimateQueryResultInfo) it
							.next();
					amount+=(Constant.CurrencyType.getSymbol(qinfo.getCurrencyID())+ "" + DataFormat.formatDisabledAmount(Info.getBalance(),2) + "<br>");
					accountNo+=(Info.getAccountNo()+"<br>");
				}
				PagerTools.returnCellList(cellList,amount);
				PagerTools.returnCellList(cellList,accountNo);
			} else {
				PagerTools.returnCellList(cellList, "");
				PagerTools.returnCellList(cellList, "");
			}
			
			rowInfo = new ResultPagerRowInfo();
			rowInfo.setCell(cellList);
			rowInfo.setId(String.valueOf(rs.getLong("rownum1")));
			// 返回数据
			resultList.add(rowInfo);
			i++;
		}
		return resultList;

	}

	public ArrayList queryInterestEstimateAccountInfoSumAmount(ResultSet rs,
			Map map) throws Exception {
		InterestEstimateQueryInfo qinfo = (InterestEstimateQueryInfo) map
				.get("qInfo");
		QInterestEstimate qobj = new QInterestEstimate();
		InterestEstimateQueryResultInfo interestSumInfo = qobj
				.queryInterestSum(qinfo);
		ArrayList list = new ArrayList();
		String strPayInterestSum = DataFormat
				.formatDisabledAmount(UtilOperation.Arith.round(interestSumInfo
						.getInterest(), 2)
						+ UtilOperation.Arith.round(interestSumInfo
								.getForfeitInterest(), 2)
						+ UtilOperation.Arith.round(interestSumInfo
								.getCompoundInterest(), 2));
		String strPayCommissionSum = DataFormat
				.formatDisabledAmount(interestSumInfo.getCommission());
		String strPayAssuranceSum = DataFormat
				.formatDisabledAmount(interestSumInfo.getAssuranceCharge());
		String strPayInterestTaxSum = DataFormat
				.formatDisabledAmount(interestSumInfo.getInterestTaxCharge());
		list.add("应付利息合计{" + strPayInterestSum + "}");
		list.add("应付担保费合计{" + strPayAssuranceSum + "}");
		list.add("应付手续费合计{" + strPayCommissionSum + "}");
		list.add("应付利息税费合计{" + strPayInterestTaxSum + "}");
		return list;
	}

}
