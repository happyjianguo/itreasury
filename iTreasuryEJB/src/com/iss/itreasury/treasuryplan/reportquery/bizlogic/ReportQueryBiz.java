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

	// ��������ܲ�ѯ
	public Hashtable queryDepositLoan(long lCurrencyID, Timestamp tsDate)
			throws Exception {
		Hashtable htRtn = new Hashtable();
		QDepositLoanSearch qdlSearch = new QDepositLoanSearch();
		QueryDepositLoanWhereInfo qdlwInfo = new QueryDepositLoanWhereInfo();
		try {
			// �Ȳ�����а��´�
			ReportQueryDAO dao = new ReportQueryDAO();
			Vector officeList = dao.queryOfficeList();
			Iterator it = officeList.iterator();
			qdlwInfo.setCurrencyID(lCurrencyID);
			qdlwInfo.setDate(tsDate);
			// ѭ����ѯ�������´�
			while (it.hasNext()) {
				Long officeID = (Long) it.next();
				long lOfficeID = officeID.longValue();
				qdlwInfo.setOfficeID(lOfficeID);
				// �Ȳ�ѯSett_DepositLoanSearchSetting�����Ƿ��ж�Ӧ�ð��´�������
				Sett_DepositLoanSearchSettingDAO dlssaDao = new Sett_DepositLoanSearchSettingDAO();
				Collection dlssaColl = null;
				dlssaColl = dlssaDao.query(lCurrencyID, lOfficeID);
				// �����û�����ã����Ȳ��룬������ݰ��´���ѯ�������ᷢ���쳣
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
				// �������з��������ݰ��´���ѯ�������
				Vector vTemp = (Vector) qdlSearch.queryDepositLoan(qdlwInfo);
				// �ϼ�ֵ
				double sum = 0;
				QueryDepositLoanResultInfo totalInfo = new QueryDepositLoanResultInfo();
				QueryDepositLoanResultInfo tempInfo = null;
				for (int i = 0; i < vTemp.size() && vTemp.size() > 0; i++) {
					tempInfo = (QueryDepositLoanResultInfo) vTemp.get(i);
					sum = totalInfo.getSumBalance();
					sum += tempInfo.getSumBalance();
					totalInfo.setSumBalance(sum);
					totalInfo.setAccountTypeName("�ϼ�");
				}
				vTemp.add(totalInfo);
				htRtn.put(officeID, vTemp);
			}
		} catch (Exception e) {
			throw e;
		}
		return htRtn.size() > 0 ? htRtn : null;
	}

	// ƽ��������
	public Hashtable queryAverageAccountBalance(QueryAccountWhereInfo qInfo)
			throws Exception {
		Hashtable htRtn = new Hashtable();
		try {
			// �����˻���ѭ����ѯ
			ReportQueryDAO dao = new ReportQueryDAO();
			for (int i = 0; i < AccountGroupTypes.length; i++) {
				long lAccountGroupType = AccountGroupTypes[i];
				qInfo.setAccountID(lAccountGroupType); // ����ԭ��ʵ�壬�����˻�������
				Collection coll = null;
				Iterator it = null;
				// ִ�в�ѯ
				coll = dao.queryAverageAccountBalance(qInfo);
				if (coll != null && coll.size() > 0) {
					Vector vBalance = new Vector();
					// ĳһ������͵Ĺ�˾�ϼ�
					QueryAverageAccountResultInfo totalBalanceInfo = new QueryAverageAccountResultInfo();
					double dBalanceSum = 0;
					it = coll.iterator();
					while (it.hasNext()) {
						QueryAverageAccountResultInfo info = (QueryAverageAccountResultInfo) it.next();
						info.setAccountName(getOfficeNameByID(info.getAccountID())); // �����´����Ʒ���ʵ����
						vBalance.add(info);
						dBalanceSum += info.getBalance();
					}
					totalBalanceInfo.setAccountName("��˾�ϼ�");
					totalBalanceInfo.setBalance(dBalanceSum);
					vBalance.add(totalBalanceInfo);
					htRtn.put(AccountBalanceTypesForReport[i], vBalance);
					// ����ǻ����˻�����ȡЭ����ֵ
					if (lAccountGroupType == SETTConstant.AccountGroupType.CURRENT) {
						Vector vNegotiateBalance = new Vector();
						QueryAverageAccountResultInfo totalNegotiateInfo = new QueryAverageAccountResultInfo();
						double dNegotiateBalance = 0;
						Iterator iter = null;
						iter = coll.iterator();
						while (iter.hasNext()) {
							QueryAverageAccountResultInfo info = (QueryAverageAccountResultInfo) iter.next();
							QueryAverageAccountResultInfo nbInfo = new QueryAverageAccountResultInfo();
							nbInfo.setAccountName(getOfficeNameByID(info.getAccountID())); // �����´����Ʒ���ʵ����
							nbInfo.setBalance(info.getNegotiateBalance());
							vNegotiateBalance.add(nbInfo);
							dNegotiateBalance += info.getNegotiateBalance();
						}
						totalNegotiateInfo.setAccountName("��˾�ϼ�");
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

	// ���ڴ���ѯ
	public Hashtable queryFixedDepositInfo(QueryFixedDepositInfo qInfo)
			throws Exception {
		Hashtable htRtn = new Hashtable();
		QFixedDeposit qFixedDeposit = new QFixedDeposit();
		PageLoader pageLoader = null;
		try {
			// �Ȳ�����а��´�
			ReportQueryDAO dao = new ReportQueryDAO();
			Vector officeList = dao.queryOfficeList();
			Iterator it = officeList.iterator();
			// ѭ����ѯ�������´�
			while (it.hasNext()) {
				Long officeID = (Long) it.next();
				long lOfficeID = officeID.longValue();
				qInfo.setOfficeID(lOfficeID);
				// �������з��������ݰ��´���ѯ���ڴ����Ϣ
				pageLoader = qFixedDeposit.queryFixedDepositInfo(qInfo);
				// �������з��������ݰ��´���ѯ������Ϣ
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
						totalInfo.setAccountNo("�ϼ�");
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

	// �����ѯ
	public Hashtable queryLoanAccountBalance(QueryAccountWhereInfo qInfo)
			throws Exception {
		Hashtable htRtn = new Hashtable();
		QAccountBalance qobj = new QAccountBalance();
		PageLoader pageLoader = null;
		try {
			// �Ȳ�����а��´�
			ReportQueryDAO dao = new ReportQueryDAO();
			Vector officeList = dao.queryOfficeList();
			Iterator it = officeList.iterator();
			// ѭ����ѯ�������´�
			while (it.hasNext()) {
				Long officeID = (Long) it.next();
				long lOfficeID = officeID.longValue();
				qInfo.setOfficeID(lOfficeID);
				// �������з��������ݰ��´���ѯ����
				pageLoader = qobj.queryLoanAccountBalance(qInfo);
				// �������з��������ݰ��´���ѯ����
				QueryAccountSumInfo sumObj = qobj.queryLoanAccountBalanceSum(qInfo);

				Vector vTemp = new Vector();
				Object[] objs = pageLoader.listAll();
				if (objs != null && objs.length > 0) {
					for (int i = 0; i < objs.length; i++) {
						QueryAccountBalanceResultInfo obj = (QueryAccountBalanceResultInfo) objs[i];
						// ����������Ҫ���ݴ������ͽ��д���
						double dAmount = obj.getLoanPayAmount(); // ������
						double dBanlance = obj.getBalance(); // ��ǰ���
						double dInterestRate = obj.getInterestRate(); // ����
						double dInterest = obj.getInterest(); // Ӧ����Ϣ
						// ��Ҫ��ҵ����
						BankLoanQuery bankLoanQuery = new BankLoanQuery();
						UtilOperation utilOperation = new UtilOperation();
						// ת����
						if (obj.getLoanTypeID() == LOANConstant.LoanType.ZTX) {
							dAmount = obj.getOpenAmount();
						}
						// ���Ŵ���
						if (obj.getLoanTypeID() == LOANConstant.LoanType.YT) {
							// �д�����
							double dRate = 0.0;
							// ���ݷſID��ѯ
							dRate = bankLoanQuery.findRateByFormID(obj.getLoanPayID());
							dBanlance = bankLoanQuery.findBalanceByFormID(obj.getLoanPayID());
							if (dRate > 0) {
								dInterest = dInterest / dRate * 100;
							}
						}
						// ��������ִ��ֱ��ȡ�������ʣ�����ȡ���ʵ�����Ĵ������ʣ����������֣�
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
						totalInfo.setAccountNo("�ϼ�");
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

	// �ͻ����ܲ�ѯ
	public Hashtable queryClientGather(QueryClientGatherWhereInfo qInfo)
			throws Exception {
		Hashtable htRtn = new Hashtable();
		QClientGather qobj = new QClientGather();
		PageLoader pageLoader = null;
		try {
			// �Ȳ�����а��´�
			ReportQueryDAO dao = new ReportQueryDAO();
			Vector officeList = dao.queryOfficeList();
			Iterator it = officeList.iterator();
			// ѭ����ѯ�������´�
			while (it.hasNext()) {
				Long officeID = (Long) it.next();
				long lOfficeID = officeID.longValue();
				qInfo.setOfficeID(lOfficeID);
				// �������з��������ݰ��´���ѯ�ͻ�������Ϣ
				pageLoader = qobj.queryClientGather(qInfo);
				Vector vTemp = new Vector();
				Object[] objs = pageLoader.listAll();
				if (objs != null && objs.length > 0) {
					// �������´��ϼ�
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
					totalInfo.setClientCode("�ϼ�");
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

	// ҵ����ͳ��
	public Hashtable queryCounterTrans(QCounterTransInfo qInfo)
			throws Exception {
		Hashtable htRtn = new Hashtable();
		try {
			// ���ݽ�������ѭ����ѯ
			ReportQueryDAO dao = new ReportQueryDAO();
			for (int i = 0; i < TransactionTypesForReport.length; i++) {
				long lTransactionType = TransactionTypesForReport[i];
				qInfo.setAccountType(lTransactionType);
				Collection coll = null;
				Iterator it = null;
				// ��ѯ�ý������͵����а��´�������
				coll = dao.queryCounterTransByTransType(qInfo);
				if (coll != null && coll.size() > 0) {
					Vector vTemp = new Vector();
					// �ý������͵Ĺ�˾�ϼ�
					DailyGatherInfo totalInfo = new DailyGatherInfo();
					double dSumPay = 0;
					double dSumReceive = 0;
					long lSumNum = 0;
					it = coll.iterator();
					while (it.hasNext()) {
						DailyGatherInfo info = (DailyGatherInfo) it.next();
						info.setName(getOfficeNameByID(info.getID())); // �����´����Ʒ���ʵ����
						vTemp.add(info);
						dSumPay += info.getPutAmount();
						dSumReceive += info.getGetAmount();
						lSumNum += info.getNumber();
					}
					totalInfo.setName("��˾�ϼ�");
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

	// ���ݰ��´�ID��ȡ����
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

	// ȡҵ��ͳ�Ʊ���Ҫ�Ľ�������
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

	public static final String[] AccountBalanceTypesForReport = { "���ڴ��", "���ڴ��", "֪ͨ���", "Э�����" };
}