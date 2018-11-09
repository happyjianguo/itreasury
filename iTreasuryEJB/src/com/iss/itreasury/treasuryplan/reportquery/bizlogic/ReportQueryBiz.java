/*
 * Created on 2006-9-8
 */
package com.iss.itreasury.treasuryplan.reportquery.bizlogic;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.settlement.query.paraminfo.QCounterTransInfo;
import com.iss.itreasury.settlement.query.paraminfo.QueryAccountWhereInfo;
import com.iss.itreasury.settlement.query.paraminfo.QueryClientGatherWhereInfo;
import com.iss.itreasury.settlement.query.paraminfo.QueryDepositLoanWhereInfo;
import com.iss.itreasury.settlement.query.paraminfo.QueryFixedDepositInfo;
import com.iss.itreasury.settlement.query.queryobj.QAccountBalance;
import com.iss.itreasury.settlement.query.queryobj.QClientGather;
import com.iss.itreasury.settlement.query.queryobj.QDepositLoanSearch;
import com.iss.itreasury.settlement.query.queryobj.QFixedDeposit;
import com.iss.itreasury.settlement.query.resultinfo.DailyGatherInfo;
import com.iss.itreasury.settlement.query.resultinfo.QueryAccountBalanceResultInfo;
import com.iss.itreasury.settlement.query.resultinfo.QueryAccountSumInfo;
import com.iss.itreasury.settlement.query.resultinfo.QueryAverageAccountResultInfo;
import com.iss.itreasury.settlement.query.resultinfo.QueryClientGatherResultInfo;
import com.iss.itreasury.settlement.query.resultinfo.QueryDepositLoanResultInfo;
import com.iss.itreasury.settlement.query.resultinfo.QueryFixedDepositSumInfo;
import com.iss.itreasury.settlement.query.resultinfo.QueryTransFixedResultInfo;
import com.iss.itreasury.settlement.setting.dao.Sett_DepositLoanSearchSettingDAO;
import com.iss.itreasury.settlement.setting.dataentity.DepositLoanSearchSettingInfo;
import com.iss.itreasury.settlement.transloan.bizlogic.BankLoanQuery;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.settlement.util.UtilOperation;
import com.iss.itreasury.treasuryplan.reportquery.dao.ReportQueryDAO;
import com.iss.system.dao.PageLoader;

public class ReportQueryBiz {

	// 存款贷款汇总查询
	public Hashtable queryDepositLoan(long lCurrencyID, Timestamp tsDate)
			throws Exception {
		Hashtable htRtn = new Hashtable();
		QDepositLoanSearch qdlSearch = new QDepositLoanSearch();
		QueryDepositLoanWhereInfo qdlwInfo = new QueryDepositLoanWhereInfo();
		try {
			// 先查出所有办事处
			ReportQueryDAO dao = new ReportQueryDAO();
			Vector officeList = dao.queryOfficeList();
			Iterator it = officeList.iterator();
			qdlwInfo.setCurrencyID(lCurrencyID);
			qdlwInfo.setDate(tsDate);
			// 循环查询各个办事处
			while (it.hasNext()) {
				Long officeID = (Long) it.next();
				long lOfficeID = officeID.longValue();
				qdlwInfo.setOfficeID(lOfficeID);
				// 先查询Sett_DepositLoanSearchSetting表中是否有对应该办事处的设置
				Sett_DepositLoanSearchSettingDAO dlssaDao = new Sett_DepositLoanSearchSettingDAO();
				Collection dlssaColl = null;
				dlssaColl = dlssaDao.query(lCurrencyID, lOfficeID);
				// 如果还没有设置，就先插入，否则根据办事处查询汇总余额会发生异常
				if (dlssaColl == null || dlssaColl.size() == 0) {
					long[] LoanType = SETTConstant.DepositLoanType.getAllCode(lCurrencyID, lOfficeID);
					for (int i = 0; i < LoanType.length; i++) {
						DepositLoanSearchSettingInfo info = new DepositLoanSearchSettingInfo();
						info.setCurrencyID(lCurrencyID);
						info.setOfficeID(lOfficeID);
						info.setName(SETTConstant.DepositLoanType.getName(LoanType[i]));
						dlssaDao.add(info);
					}
				}
				// 调用已有方法，根据办事处查询汇总余额
				Vector vTemp = (Vector) qdlSearch.queryDepositLoan(qdlwInfo);
				// 合计值
				double sum = 0;
				QueryDepositLoanResultInfo totalInfo = new QueryDepositLoanResultInfo();
				QueryDepositLoanResultInfo tempInfo = null;
				for (int i = 0; i < vTemp.size() && vTemp.size() > 0; i++) {
					tempInfo = (QueryDepositLoanResultInfo) vTemp.get(i);
					sum = totalInfo.getSumBalance();
					sum += tempInfo.getSumBalance();
					totalInfo.setSumBalance(sum);
					totalInfo.setAccountTypeName("合计");
				}
				vTemp.add(totalInfo);
				htRtn.put(officeID, vTemp);
			}
		} catch (Exception e) {
			throw e;
		}
		return htRtn.size() > 0 ? htRtn : null;
	}

	// 平均余额分析
	public Hashtable queryAverageAccountBalance(QueryAccountWhereInfo qInfo)
			throws Exception {
		Hashtable htRtn = new Hashtable();
		try {
			// 根据账户组循环查询
			ReportQueryDAO dao = new ReportQueryDAO();
			for (int i = 0; i < AccountGroupTypes.length; i++) {
				long lAccountGroupType = AccountGroupTypes[i];
				qInfo.setAccountID(lAccountGroupType); // 借用原有实体，设置账户组类型
				Collection coll = null;
				Iterator it = null;
				// 执行查询
				coll = dao.queryAverageAccountBalance(qInfo);
				if (coll != null && coll.size() > 0) {
					Vector vBalance = new Vector();
					// 某一存款类型的公司合计
					QueryAverageAccountResultInfo totalBalanceInfo = new QueryAverageAccountResultInfo();
					double dBalanceSum = 0;
					it = coll.iterator();
					while (it.hasNext()) {
						QueryAverageAccountResultInfo info = (QueryAverageAccountResultInfo) it.next();
						info.setAccountName(getOfficeNameByID(info.getAccountID())); // 将办事处名称放入实体中
						vBalance.add(info);
						dBalanceSum += info.getBalance();
					}
					totalBalanceInfo.setAccountName("公司合计");
					totalBalanceInfo.setBalance(dBalanceSum);
					vBalance.add(totalBalanceInfo);
					htRtn.put(AccountBalanceTypesForReport[i], vBalance);
					// 如果是活期账户，就取协定的值
					if (lAccountGroupType == SETTConstant.AccountGroupType.CURRENT) {
						Vector vNegotiateBalance = new Vector();
						QueryAverageAccountResultInfo totalNegotiateInfo = new QueryAverageAccountResultInfo();
						double dNegotiateBalance = 0;
						Iterator iter = null;
						iter = coll.iterator();
						while (iter.hasNext()) {
							QueryAverageAccountResultInfo info = (QueryAverageAccountResultInfo) iter.next();
							QueryAverageAccountResultInfo nbInfo = new QueryAverageAccountResultInfo();
							nbInfo.setAccountName(getOfficeNameByID(info.getAccountID())); // 将办事处名称放入实体中
							nbInfo.setBalance(info.getNegotiateBalance());
							vNegotiateBalance.add(nbInfo);
							dNegotiateBalance += info.getNegotiateBalance();
						}
						totalNegotiateInfo.setAccountName("公司合计");
						totalNegotiateInfo.setBalance(dNegotiateBalance);
						vNegotiateBalance.add(totalNegotiateInfo);
						htRtn.put(AccountBalanceTypesForReport[3], vNegotiateBalance);
					}
				}
			}

		} catch (Exception e) {
			throw e;
		}
		return htRtn.size() > 0 ? htRtn : null;
	}

	// 定期存款查询
	public Hashtable queryFixedDepositInfo(QueryFixedDepositInfo qInfo)
			throws Exception {
		Hashtable htRtn = new Hashtable();
		QFixedDeposit qFixedDeposit = new QFixedDeposit();
		PageLoader pageLoader = null;
		try {
			// 先查出所有办事处
			ReportQueryDAO dao = new ReportQueryDAO();
			Vector officeList = dao.queryOfficeList();
			Iterator it = officeList.iterator();
			// 循环查询各个办事处
			while (it.hasNext()) {
				Long officeID = (Long) it.next();
				long lOfficeID = officeID.longValue();
				qInfo.setOfficeID(lOfficeID);
				// 调用已有方法，根据办事处查询定期存款信息
				pageLoader = qFixedDeposit.queryFixedDepositInfo(qInfo);
				// 调用已有方法，根据办事处查询汇总信息
				QueryFixedDepositSumInfo sumObj = qFixedDeposit.queryFixedDepositSum(qInfo);

				Vector vTemp = new Vector();
				Object[] objs = pageLoader.listAll();
				if (objs != null && objs.length > 0) {

					for (int i = 0; i < objs.length; i++) {
						QueryTransFixedResultInfo obj = (QueryTransFixedResultInfo) objs[i];
						vTemp.add(obj);
					}
					if (sumObj != null) {
						QueryTransFixedResultInfo totalInfo = new QueryTransFixedResultInfo();
						totalInfo.setAccountNo("合计");
						totalInfo.setAmount(sumObj.getDepositAmountSum());
						totalInfo.setBalance(sumObj.getDepositBalanceSum());
						totalInfo.setPreDrawInterest(sumObj.getDepositInterestSum());
						vTemp.add(totalInfo);
					}
				}
				htRtn.put(officeID, vTemp);
			}
		} catch (Exception e) {
			throw e;
		}
		return htRtn.size() > 0 ? htRtn : null;
	}

	// 贷款查询
	public Hashtable queryLoanAccountBalance(QueryAccountWhereInfo qInfo)
			throws Exception {
		Hashtable htRtn = new Hashtable();
		QAccountBalance qobj = new QAccountBalance();
		PageLoader pageLoader = null;
		try {
			// 先查出所有办事处
			ReportQueryDAO dao = new ReportQueryDAO();
			Vector officeList = dao.queryOfficeList();
			Iterator it = officeList.iterator();
			// 循环查询各个办事处
			while (it.hasNext()) {
				Long officeID = (Long) it.next();
				long lOfficeID = officeID.longValue();
				qInfo.setOfficeID(lOfficeID);
				// 调用已有方法，根据办事处查询贷款
				pageLoader = qobj.queryLoanAccountBalance(qInfo);
				// 调用已有方法，根据办事处查询汇总
				QueryAccountSumInfo sumObj = qobj.queryLoanAccountBalanceSum(qInfo);

				Vector vTemp = new Vector();
				Object[] objs = pageLoader.listAll();
				if (objs != null && objs.length > 0) {
					for (int i = 0; i < objs.length; i++) {
						QueryAccountBalanceResultInfo obj = (QueryAccountBalanceResultInfo) objs[i];
						// 部分数据需要根据贷款类型进行处理
						double dAmount = obj.getLoanPayAmount(); // 贷款金额
						double dBanlance = obj.getBalance(); // 当前余额
						double dInterestRate = obj.getInterestRate(); // 利率
						double dInterest = obj.getInterest(); // 应付利息
						// 需要的业务类
						BankLoanQuery bankLoanQuery = new BankLoanQuery();
						UtilOperation utilOperation = new UtilOperation();
						// 转贴现
						if (obj.getLoanTypeID() == LOANConstant.LoanType.ZTX) {
							dAmount = obj.getOpenAmount();
						}
						// 银团贷款
						if (obj.getLoanTypeID() == LOANConstant.LoanType.YT) {
							// 承贷比例
							double dRate = 0.0;
							// 根据放款单ID查询
							dRate = bankLoanQuery.findRateByFormID(obj.getLoanPayID());
							dBanlance = bankLoanQuery.findBalanceByFormID(obj.getLoanPayID());
							if (dRate > 0) {
								dInterest = dInterest / dRate * 100;
							}
						}
						// 如果是贴现贷款，直接取贴现利率，否则取利率调整后的贷款利率（不包括贴现）
						if (SETTConstant.AccountType.isDiscountAccountType(obj.getAccountTypeID())) {
							dInterestRate = obj.getContractInterestRate();
						} else {
							dInterestRate = utilOperation.getLatelyRate(obj.getLoanPayID(), obj.getBalanceDate());
						}
						obj.setLoanPayAmount(dAmount);
						obj.setBalance(dBanlance);
						obj.setInterestRate(dInterestRate);
						obj.setInterest(dInterest);
						vTemp.add(obj);
					}
					if (sumObj != null) {
						QueryAccountBalanceResultInfo totalInfo = new QueryAccountBalanceResultInfo();
						totalInfo.setAccountNo("合计");
						totalInfo.setBalance(sumObj.getBalanceSum());
						totalInfo.setInterest(sumObj.getInterestSum());
						vTemp.add(totalInfo);
					}
				}
				htRtn.put(officeID, vTemp);
			}
		} catch (Exception e) {
			throw e;
		}
		return htRtn.size() > 0 ? htRtn : null;
	}

	// 客户汇总查询
	public Hashtable queryClientGather(QueryClientGatherWhereInfo qInfo)
			throws Exception {
		Hashtable htRtn = new Hashtable();
		QClientGather qobj = new QClientGather();
		PageLoader pageLoader = null;
		try {
			// 先查出所有办事处
			ReportQueryDAO dao = new ReportQueryDAO();
			Vector officeList = dao.queryOfficeList();
			Iterator it = officeList.iterator();
			// 循环查询各个办事处
			while (it.hasNext()) {
				Long officeID = (Long) it.next();
				long lOfficeID = officeID.longValue();
				qInfo.setOfficeID(lOfficeID);
				// 调用已有方法，根据办事处查询客户汇总信息
				pageLoader = qobj.queryClientGather(qInfo);
				Vector vTemp = new Vector();
				Object[] objs = pageLoader.listAll();
				if (objs != null && objs.length > 0) {
					// 单个办事处合计
					QueryClientGatherResultInfo totalInfo = new QueryClientGatherResultInfo();
					long lAccountSum = 0;
					long lFixedSum = 0;
					long lLoanSum = 0;
					for (int i = 0; i < objs.length; i++) {
						QueryClientGatherResultInfo obj = (QueryClientGatherResultInfo) objs[i];
						lAccountSum += obj.getAccountCount();
						lFixedSum += obj.getFixedCount();
						lLoanSum += obj.getLoanCount();
						vTemp.add(obj);
					}
					totalInfo.setClientCode("合计");
					totalInfo.setAccountCount(lAccountSum);
					totalInfo.setFixedCount(lFixedSum);
					totalInfo.setLoanCount(lLoanSum);
					vTemp.add(totalInfo);
				}
				htRtn.put(officeID, vTemp);
			}
		} catch (Exception e) {
			throw e;
		}
		return htRtn.size() > 0 ? htRtn : null;
	}

	// 业务量统计
	public Hashtable queryCounterTrans(QCounterTransInfo qInfo)
			throws Exception {
		Hashtable htRtn = new Hashtable();
		try {
			// 根据交易类型循环查询
			ReportQueryDAO dao = new ReportQueryDAO();
			for (int i = 0; i < TransactionTypesForReport.length; i++) {
				long lTransactionType = TransactionTypesForReport[i];
				qInfo.setAccountType(lTransactionType);
				Collection coll = null;
				Iterator it = null;
				// 查询该交易类型的所有办事处的数据
				coll = dao.queryCounterTransByTransType(qInfo);
				if (coll != null && coll.size() > 0) {
					Vector vTemp = new Vector();
					// 该交易类型的公司合计
					DailyGatherInfo totalInfo = new DailyGatherInfo();
					double dSumPay = 0;
					double dSumReceive = 0;
					long lSumNum = 0;
					it = coll.iterator();
					while (it.hasNext()) {
						DailyGatherInfo info = (DailyGatherInfo) it.next();
						info.setName(getOfficeNameByID(info.getID())); // 将办事处名称放入实体中
						vTemp.add(info);
						dSumPay += info.getPutAmount();
						dSumReceive += info.getGetAmount();
						lSumNum += info.getNumber();
					}
					totalInfo.setName("公司合计");
					totalInfo.setPutAmount(dSumPay);
					totalInfo.setGetAmount(dSumReceive);
					totalInfo.setNumber(lSumNum);

					vTemp.add(totalInfo);
					htRtn.put(String.valueOf(lTransactionType), vTemp);
				}
			}
		} catch (Exception e) {
			throw e;
		}
		return htRtn.size() > 0 ? htRtn : null;
	}

	// 根据办事处ID号取名称
	public static String getOfficeNameByID(long lOfficeID) throws Exception {
		String strOfficeName = "";
		try {
			ReportQueryDAO dao = new ReportQueryDAO();
			strOfficeName = dao.getOfficeNameByID(lOfficeID);
		} catch (Exception e) {
			throw e;
		}
		return strOfficeName;
	}

	// 取业务统计表需要的交易类型
	public static final long[] TransactionTypesForReport = {
			SETTConstant.TransactionType.BANKRECEIVE,
			SETTConstant.TransactionType.BANKPAY,
			SETTConstant.TransactionType.INTERNALVIREMENT,
			SETTConstant.TransactionType.OPENNOTIFYACCOUNT,
			SETTConstant.TransactionType.NOTIFYDEPOSITDRAW,
			SETTConstant.TransactionType.OPENFIXEDDEPOSIT,
			SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER,
			SETTConstant.TransactionType.FIXEDCONTINUETRANSFER,
			SETTConstant.TransactionType.TRUSTLOANGRANT,
			SETTConstant.TransactionType.TRUSTLOANRECEIVE,
			SETTConstant.TransactionType.CONSIGNLOANGRANT,
			SETTConstant.TransactionType.CONSIGNLOANRECEIVE,
			SETTConstant.TransactionType.DISCOUNTGRANT,
			SETTConstant.TransactionType.DISCOUNTRECEIVE };

	//
	public static final long[] AccountGroupTypes = {
			SETTConstant.AccountGroupType.CURRENT,
			SETTConstant.AccountGroupType.FIXED,
			SETTConstant.AccountGroupType.NOTIFY };

	public static final String[] AccountBalanceTypesForReport = { "活期存款", "定期存款", "通知存款", "协定存款" };
}