package com.iss.itreasury.settlement.query.bizlogic;

import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.iss.itreasury.settlement.bizdelegation.TransCurrentDepositDelegation;
import com.iss.itreasury.settlement.bizdelegation.TransSpecialOperationDelegation;
import com.iss.itreasury.settlement.query.Dao.QTransactionDao;
import com.iss.itreasury.settlement.query.paraminfo.QueryTransactionConditionInfo;
import com.iss.itreasury.settlement.query.queryobj.QTransaction;
import com.iss.itreasury.settlement.query.resultinfo.QueryTransactionInfo;
import com.iss.itreasury.settlement.transcurrentdeposit.dataentity.TransOnePayMultiReceiveInfo;
import com.iss.itreasury.settlement.transspecial.dataentity.TransSpecialOperationInfo;
import com.iss.itreasury.settlement.util.NameRef;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.IException;
import com.iss.sysframe.pager.dataentity.PagerInfo;
import com.iss.sysframe.pager.dataentity.ResultPagerRowInfo;
import com.iss.sysframe.pager.tools.PagerTools;

/**
 * 账户查询业务层
 * 
 * @author xiang
 * 
 */
public class QTransactionBiz {

	QTransactionDao dao = new QTransactionDao();

	public PagerInfo queryTransaction(QueryTransactionConditionInfo qInfo,
			String strPageLoaderKey, String lUnit) throws Exception {
		// TODO Auto-generated method stub
		{
			PagerInfo pagerInfo = null;
			String sql = null;
			try {
				pagerInfo = new PagerInfo();
				// 得到查询SQL
				sql = dao.getTransactionSQL(qInfo);
				System.out.println("**sql:"+sql);
				pagerInfo.setSqlString(sql);

				try {
					Map params = new HashMap();
					params.put("param", qInfo);
					params.put("strPageLoaderKey", strPageLoaderKey);
					params.put("lUnit", lUnit);
					pagerInfo.setExtensionMothod(QTransactionBiz.class,
							"resultSetHandle", params);
					pagerInfo.setTotalExtensionMothod(QTransactionBiz.class,
							"totalResultSetHandle", params);
				} catch (Exception e) {
					e.printStackTrace();
					throw new Exception("====>查询异常", e);
				}

			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception("====>查询异常", e);
			}
			return pagerInfo;
		}
	}

	public ArrayList totalResultSetHandle(ResultSet rs, Map params)
			throws Exception {
		ArrayList list = new ArrayList();
		try{
		QueryTransactionConditionInfo conditionInfo = (QueryTransactionConditionInfo) params
				.get("param");
		QTransaction qobj = new QTransaction();
		long lUnit = Long.valueOf(params.get("lUnit") + "").longValue();
		double dReceiveSum = qobj.getReceiveAmountSumForQuery(conditionInfo);
		long lCount = qobj.getCountForQuery(conditionInfo);

		
		list.add("交易金额合计{" + DataFormat.formatDisabledAmount(dReceiveSum / lUnit)
				+ "}");
		list.add("记录合计{" + lCount + "}");
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return list;
	}

	public ArrayList resultSetHandle(ResultSet rs, Map params)
			throws IException, RemoteException {
		String strPageLoaderKey = (String) params.get("strPageLoaderKey");
		ArrayList resultList = new ArrayList(); // 最终返回结果
		ArrayList cellList = null;// 列
		ResultPagerRowInfo rowInfo = null;// 行
		double dSumDeposit = 0.0;// 存款合计
		double dSumLoan = 0.0;// 贷款合计
		long lUnit = Long.valueOf((String) params.get("lUnit")).longValue();
		String strAssistAction = (String) params.get("strAssistAction");
		try {
			if (rs != null) {
				while (rs.next()) {

					// 获取数据
					String ID = rs.getString("ID") == null ? "" : rs
							.getString("ID");
					String SerialNo = rs.getString("SERIALNO") == null ? ""
							: rs.getString("SERIALNO");
					String OfficeID = rs.getString("OFFICEID") == null ? "-1"
							: rs.getString("OFFICEID");
					String CurrencyID = rs.getString("CURRENCYID") == null ? "-1"
							: rs.getString("CURRENCYID");
					String TransNo = rs.getString("TRANSNO") == null ? "" : rs
							.getString("TRANSNO");
					String TransactionTypeID = rs
							.getString("TRANSACTIONTYPEID") == null ? "" : rs
							.getString("TRANSACTIONTYPEID");
					String InterestStart = rs.getString("INTERESTSTART") == null ? ""
							: rs.getString("INTERESTSTART");
					Timestamp Execute = rs.getTimestamp("EXECUTE");
					String StatusID = rs.getString("STATUSID") == null ? ""
							: rs.getString("STATUSID");
					String InputuserID = rs.getString("INPUTUSERID") == null ? "-1"
							: rs.getString("INPUTUSERID");
					String CheckuserID = rs.getString("CHECKUSERID") == null ? "-1"
							: rs.getString("CHECKUSERID");
					String Abstract = rs.getString("ABSTRACT") == null ? ""
							: rs.getString("ABSTRACT");
					String PayclientID = rs.getString("PAYCLIENTID") == null ? ""
							: rs.getString("PAYCLIENTID");
					String PayaccountID = rs.getString("PAYACCOUNTID") == null ? ""
							: rs.getString("PAYACCOUNTID");
					String PayAmount = rs.getString("PAYAMOUNT") == null ? ""
							: rs.getString("PAYAMOUNT");
					String ReceiveAmount = rs.getString("RECEIVEAMOUNT") == null ? ""
							: rs.getString("RECEIVEAMOUNT");
					String ReceiveClientID = rs.getString("RECEIVECLIENTID") == null ? ""
							: rs.getString("RECEIVECLIENTID");
					String ReceiveAccountID = rs.getString("RECEIVEACCOUNTID") == null ? ""
							: rs.getString("RECEIVEACCOUNTID");
					String BankID = rs.getString("BANKID") == null ? "" : rs
							.getString("BANKID");
					String ContractID = rs.getString("CONTRACTID") == null ? "-1"
							: rs.getString("CONTRACTID");
					String LoanFormID = rs.getString("LOANFORMID") == null ? "-1"
							: rs.getString("LOANFORMID");
					String DepositNo = rs.getString("DEPOSITNO") == null ? ""
							: rs.getString("DEPOSITNO");
					String PayAccountNo = rs.getString("PAYACCOUNTNO") == null ? ""
							: rs.getString("PAYACCOUNTNO");
					String PayAccountName = rs.getString("PAYACCOUNTNAME") == null ? ""
							: rs.getString("PAYACCOUNTNAME");
					String ReceiveAccountNo = rs.getString("RECEIVEACCOUNTNO") == null ? ""
							: rs.getString("RECEIVEACCOUNTNO");
					String ReceiveAccountName = rs
							.getString("RECEIVEACCOUNTNAME") == null ? "" : rs
							.getString("RECEIVEACCOUNTNAME");
					String PayClientCode = rs.getString("PAYCLIENTCODE") == null ? ""
							: rs.getString("PAYCLIENTCODE");
					String PayClientName = rs.getString("PAYCLIENTNAME") == null ? ""
							: rs.getString("PAYCLIENTNAME");
					String ReceiveClientCode = rs
							.getString("RECEIVECLIENTCODE") == null ? "" : rs
							.getString("RECEIVECLIENTCODE");
					String ReceiveClientName = rs
							.getString("RECEIVECLIENTNAME") == null ? "" : rs
							.getString("RECEIVECLIENTNAME");
					String DeclarationNo = rs.getString("DECLARATIONNO") == null ? ""
							: rs.getString("DECLARATIONNO");
					String BankChequeNo = rs.getString("BANKCHEQUENO") == null ? ""
							: rs.getString("BANKCHEQUENO");
					String Operationtypeid = rs.getString("OPERATIONTYPEID") == null ? ""
							: rs.getString("OPERATIONTYPEID");
					String Source = rs.getString("SOURCE") == null ? "-1" : rs
							.getString("SOURCE");
					String PayBakAccountNo = rs.getString("PAYBAKACCOUNTNO") == null ? ""
							: rs.getString("PAYBAKACCOUNTNO");
					String ReceiveBakAccountNo = rs
							.getString("RECEIVEBAKACCOUNTNO") == null ? "" : rs
							.getString("RECEIVEBAKACCOUNTNO");

					QueryTransactionConditionInfo qInfo = (QueryTransactionConditionInfo) params
							.get("param");
					// 处理数据
					String url = "";
					String param = "";
					if (rs.getDouble("TransactionTypeID") == SETTConstant.TransactionType.ONETOMULTI) {

						if (rs.getString("TransNo").equals("")) {
							url = "/settlement/tran/current/control/c011.jsp";
							param = "strSuccessPageURL=../view/v093_1.jsp&nextAction=view&strFailPageURL=../view/v093_1.jsp&strAction=toModify&lId="
									+ rs.getString("ID");

						} else {
							url = "/settlement/query/control/querycontrol.jsp";
							param = "lID="
									+ rs.getString("ID")
									+ "&TransactionTypeID="
									+ rs.getDouble("TransactionTypeID")
									+ "&TransNo="
									+ rs.getString("TransNo")
									+ "&SerialNo="
									+ rs.getString("SerialNo")
									+ "&strFailPageURL=/settlement/query/view/v012.jsp";

						}
						// add by zcwang 2007-3-8 得到多借多贷类型
						TransOnePayMultiReceiveInfo searchInfo = new TransOnePayMultiReceiveInfo();
						TransOnePayMultiReceiveInfo resultInfo1 = null;
						TransCurrentDepositDelegation depositDelegation = new TransCurrentDepositDelegation();
						searchInfo.setId(rs.getLong("ID"));
						searchInfo.setTransNo(rs.getString("TransNo"));
						searchInfo.setTransactionTypeID(rs
								.getLong("TransactionTypeID"));
						searchInfo.setSerialNo(rs.getLong("SerialNo"));
						Collection resultColl = null;
						resultColl = depositDelegation.findByConditions(
								searchInfo, 11, false);
						if (resultColl != null && !resultColl.isEmpty()
								&& resultColl.size() == 1) {
							Iterator itTemp = resultColl.iterator();
							resultInfo1 = (TransOnePayMultiReceiveInfo) itTemp
									.next();
						}
						//	 

						// add by xlchang 2010-11-08 武钢需求 多借多贷显示子类型
						TransactionTypeID = Operationtypeid;
					} else if (Long.valueOf(TransactionTypeID).longValue() == SETTConstant.TransactionType.UPGATHERING) {
						url = "/settlement/query/view/v726-d.jsp";
						param = "_pageLoaderKey=" + strPageLoaderKey
								+ "&TransNo=" + TransNo + "&SerialNo="
								+ SerialNo;
					} else if (Long.valueOf(TransactionTypeID).longValue() == SETTConstant.TransactionType.MULTILOANRECEIVE) {
						if (TransNo.equals("")) {
							url = "/settlement/tran/loan/control/c051.jsp";
							param = "strAction1=isQuery&strAction="
									+ SETTConstant.Actions.TODETAIL + "&lID="
									+ ID;
						} else {
							url = "/settlement/query/control/querycontrol.jsp";
							param = "lID="
									+ ID
									+ "&TransactionTypeID="
									+ TransactionTypeID
									+ "&TransNo="
									+ TransNo
									+ "&SerialNo="
									+ SerialNo
									+ "&strFailPageURL=/settlement/query/view/v012.jsp";
						}
					} else if (Long.valueOf(TransactionTypeID).longValue() == SETTConstant.TransactionType.FINANCIALMARGIN) {
						url = "/settlement/query/control/querycontrol.jsp";
						param = "lID="
								+ ID
								+ "&TransactionTypeID="
								+ TransactionTypeID
								+ "&TransNo="
								+ TransNo
								+ "&SerialNo="
								+ SerialNo
								+ "&strFailPageURL=/settlement/query/view/v012.jsp";
					} else {
						url = "/settlement/query/control/querycontrol.jsp";
						param = "lID="
								+ ID
								+ "&TransactionTypeID="
								+ TransactionTypeID
								+ "&TransNo="
								+ TransNo
								+ "&SerialNo="
								+ SerialNo
								+ "&strFailPageURL=/settlement/query/view/v012.jsp";
					}

					// 处理特殊交易无法显示付款方帐号、收款方帐号问题 gqfang 2008-10-15
					if (Long.valueOf(TransactionTypeID).longValue() == SETTConstant.TransactionType.SPECIALOPERATION) {

						TransSpecialOperationDelegation specialDelegation = new TransSpecialOperationDelegation();

						TransSpecialOperationInfo specialInfo = new TransSpecialOperationInfo();

						long payBankID = -1;// 付款方银行ID
						long receiveBankID = -1;// 收款方银行ID

						if (Long.valueOf(ID).longValue() > 0
								&& (PayAccountNo.equals("") || ReceiveAccountNo
										.equals(""))) {
							specialInfo = specialDelegation.findDetailByID(Long
									.valueOf(ID).longValue());

							if (specialInfo != null) {
								payBankID = specialInfo.getNpaybankid();
								receiveBankID = specialInfo.getNreceivebankid();
								if (payBankID > 0 && PayAccountNo.equals("")) {
									PayAccountNo = NameRef
											.getBankAccountCodeByBankID(payBankID);
								}
								if (ReceiveAccountNo.equals("")) {
									if (receiveBankID > 0) {
										ReceiveAccountNo = NameRef
												.getBankAccountCodeByBankID(receiveBankID);
									} else {
										ReceiveAccountNo = specialInfo
												.getSextaccountno();
									}
								}
							}
						}
					}
					// 处理信贷资产转让收付款 add by zcwang 2009-08-03
					if (Long.valueOf(TransactionTypeID) == SETTConstant.TransactionType.TRANSFERPAY
							|| Long.valueOf(TransactionTypeID).longValue() == SETTConstant.TransactionType.TRANSFERREPAY) {
						String payAccountNo = "";// 付款方银行帐号
						String receiveAccountNo = "";// 收款方银行帐号
						if (Long.valueOf(ID).longValue() > 0) {
							if (Long.valueOf(TransactionTypeID).longValue() == SETTConstant.TransactionType.TRANSFERPAY) {
								payAccountNo = NameRef
										.getBankAccountCodeByBankID(Long
												.valueOf(BankID).longValue());
							}

							else if (Long.valueOf(TransactionTypeID)
									.longValue() == SETTConstant.TransactionType.TRANSFERREPAY) {
								receiveAccountNo = NameRef
										.getBankAccountCodeByBankID(Long
												.valueOf(BankID).longValue());
							}
						}
					}

					// 存储列数据
					cellList = new ArrayList();
					PagerTools.returnCellList(cellList, DataFormat
							.getDateString(Execute));

					if (SETTConstant.TransactionType.isInterestTransaction(Long
							.valueOf(TransactionTypeID).longValue())
							|| Long.valueOf(TransactionTypeID).longValue() == SETTConstant.TransactionType.GRANT_DEBIT_INTEREST
							|| Long.valueOf(TransactionTypeID).longValue() == SETTConstant.TransactionType.RECEIVE_DEBIT_INTEREST
							|| Long.valueOf(TransactionTypeID).longValue() == SETTConstant.TransactionType.TRANS_DISCOUNT_PREDRAW_INTEREST) {

						PagerTools.returnCellList(cellList, TransNo);

					} else {
						PagerTools.returnCellList(cellList, TransNo + "," + url
								+ "," + param);

					}

					PagerTools.returnCellList(cellList,
							SETTConstant.TransactionType.getName(Long.valueOf(
									TransactionTypeID).longValue()));
					PagerTools
							.returnCellList(
									cellList,
									Long.valueOf(TransactionTypeID).longValue() == SETTConstant.TransactionType.RETURNFINANCE ? "&nbsp;"
											: (ReceiveAccountNo == null ? " "
													: ReceiveAccountNo));

					PagerTools.returnCellList(cellList,
							PayAccountNo == null ? "" : PayAccountNo);
					PagerTools.returnCellList(cellList, DepositNo == null ? ""
							: DepositNo);

					// 处理信贷资产转让收付款 add by zcwang 2009-08-03
					if (Long.valueOf(TransactionTypeID).longValue() == SETTConstant.TransactionType.TRANSFERPAY
							|| Long.valueOf(TransactionTypeID).longValue() == SETTConstant.TransactionType.TRANSFERREPAY
							|| Long.valueOf(TransactionTypeID).longValue() == SETTConstant.TransactionType.AGENTTRANSFERREPAY) {
						PagerTools.returnCellList(cellList, NameRef
								.getCraContractNoByID(Long.valueOf(ContractID)
										.longValue()));
						PagerTools.returnCellList(cellList, NameRef
								.getCraFormNoByID(Long.valueOf(LoanFormID)
										.longValue()));

					} else if (Long.valueOf(TransactionTypeID).longValue() == SETTConstant.TransactionType.SAME_BUSINESS_GRANT
							|| Long.valueOf(TransactionTypeID).longValue() == SETTConstant.TransactionType.SAME_BUSINESS_RECEIVE
							|| Long.valueOf(TransactionTypeID).longValue() == SETTConstant.TransactionType.SAME_BUSINESS_INTERESTPROCESS
							|| Long.valueOf(TransactionTypeID).longValue() == SETTConstant.TransactionType.SAME_BUSINESS_INTERESTRECEIVE) {
						PagerTools.returnCellList(cellList, NameRef
								.getSecContractNoByID(Long.valueOf(ContractID)
										.longValue()));
						PagerTools.returnCellList(cellList, NameRef
								.getSecFormNoByID(Long.valueOf(LoanFormID)
										.longValue()));
					} else if (Long.valueOf(TransactionTypeID).longValue() == SETTConstant.TransactionType.TRANS_DISCOUNT_GRANT
							|| Long.valueOf(TransactionTypeID).longValue() == SETTConstant.TransactionType.TRANS_DISCOUNT_RECEIVE
							|| Long.valueOf(TransactionTypeID).longValue() == SETTConstant.TransactionType.TRANS_DISCOUNT_REPURCHASE
							|| Long.valueOf(TransactionTypeID).longValue() == SETTConstant.TransactionType.TRANS_DISCOUNT_PREDRAW_INTEREST) {
						PagerTools.returnCellList(cellList, NameRef
								.getContractNoByID(Long.valueOf(ContractID)
										.longValue()));
						PagerTools.returnCellList(cellList, NameRef
								.getContractNoByID(Long.valueOf(LoanFormID)
										.longValue()));

					} else if (Long.valueOf(TransactionTypeID).longValue() == SETTConstant.TransactionType.FUND_ATTORN
							|| Long.valueOf(TransactionTypeID).longValue() == SETTConstant.TransactionType.FUND_ATTORN_REPAY) {
						PagerTools.returnCellList(cellList, "");
						PagerTools.returnCellList(cellList, NameRef
								.getSecFormNoByID(Long.valueOf(LoanFormID)
										.longValue()));

					} else {
						PagerTools.returnCellList(cellList, NameRef
								.getContractNoByID(Long.valueOf(ContractID)
										.longValue()));
						PagerTools.returnCellList(cellList, NameRef
								.getPayFormNoByID(Long.valueOf(LoanFormID)
										.longValue()));

					}
					//PagerTools.returnCellList(cellList, DeclarationNo);

					PagerTools.returnCellList(cellList, BankChequeNo);

					PagerTools.returnCellList(cellList, DataFormat
							.formatDisabledAmount(Double.valueOf(ReceiveAmount)
									.doubleValue()
									/ lUnit));

					PagerTools.returnCellList(cellList,
							NameRef.getSourceNameByID(Long.valueOf(Source)
									.longValue()));

					PagerTools.returnCellList(cellList,
							SETTConstant.TransactionStatus.getName(Long
									.valueOf(StatusID).longValue()));

					PagerTools.returnCellList(cellList, Abstract == null ? ""
							: Abstract);

					if (Long.valueOf(InputuserID).longValue() > 0) {
						PagerTools.returnCellList(cellList, NameRef
								.getUserNameByID(Long.valueOf(InputuserID)
										.longValue()));
					} else {
						PagerTools
								.returnCellList(
										cellList,
										NameRef
												.getUserNameByID(Constant.MachineUser.InputUser));
					}

					if (Long.valueOf(CheckuserID).longValue() > 0) {
						if (Long.valueOf(StatusID).longValue() >= SETTConstant.TransactionStatus.CHECK)
							PagerTools.returnCellList(cellList, NameRef
									.getUserNameByID(Long.valueOf(CheckuserID)
											.longValue()));
						else
							PagerTools.returnCellList(cellList, "");
					}
					// add by zcwang 2007-10-10 结息没有复核人,设置复核人为"机核"
					else if (Long.valueOf(CheckuserID).longValue() < 0
							&& Long.valueOf(StatusID).longValue() >= SETTConstant.TransactionStatus.CHECK) {
						if (Long.valueOf(StatusID).longValue() != SETTConstant.TransactionStatus.APPROVALING
								&& Long.valueOf(StatusID).longValue() != SETTConstant.TransactionStatus.APPROVALED
								&& Long.valueOf(StatusID).longValue() != SETTConstant.TransactionStatus.REFUSE) {
							PagerTools
									.returnCellList(
											cellList,
											NameRef
													.getUserNameByID(Constant.MachineUser.CheckUser));
						}
					} else {
						PagerTools.returnCellList(cellList, "");
					}

					// 存储行数据
					rowInfo = new ResultPagerRowInfo();
					rowInfo.setCell(cellList);
					rowInfo.setId(String.valueOf(rs.getLong("rownum1")));

					// 返回数据
					resultList.add(rowInfo);

				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new IException(e.getMessage());
		}

		return resultList;
	}

	public PagerInfo queryTransactionInfo(
			QueryTransactionConditionInfo conditionInfo) throws Exception {
		PagerInfo pagerInfo = null;
		String sql = null;
		try {
			pagerInfo = new PagerInfo();
			// 得到查询SQL
			sql = dao.getTransactionSQLForSubAccount(conditionInfo);
			pagerInfo.setSqlString(sql);

			Map params = new HashMap();
			params.put("param", conditionInfo);
			pagerInfo.setExtensionMothod(QTransactionBiz.class,
					"qTSubAccountResultSetHandle", params);
	//		pagerInfo.setTotalExtensionMothod(QTransactionBiz.class,
	//				"qTSubAccountTotalResultSetHandle", params);

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
	}

	public ArrayList qTSubAccountResultSetHandle(ResultSet rs, Map params)
			throws Exception {
		QueryTransactionConditionInfo conditionInfo = (QueryTransactionConditionInfo) params.get("param");
		ArrayList resultList = new ArrayList(); // 最终返回结果
		ArrayList cellList = null;// 列
		ResultPagerRowInfo rowInfo = null;// 行
		QueryTransactionInfo resultInfo;
		if (rs != null) {
			while (rs.next()) {
				cellList = new ArrayList();
				resultInfo = new QueryTransactionInfo();
				resultInfo.setTransactionTypeID(rs.getLong("TransactionTypeID"));
				resultInfo.setID(rs.getLong("ID"));
				resultInfo.setTransNo(rs.getString("TransNo"));
				resultInfo.setExecute(rs.getTimestamp("Execute"));
				resultInfo.setDepositNo(rs.getString("DepositNo"));
				resultInfo.setPayAmount(rs.getDouble("PayAmount"));
				resultInfo.setBankID(rs.getLong("BankID"));
				resultInfo.setReceiveAccountNo(rs.getString("ReceiveAccountNo"));
				resultInfo.setPayAccountNo(rs.getString("PayAccountNo"));
				resultInfo.setStatusID(rs.getLong("StatusID"));
				resultInfo.setAbstract(rs.getString("Abstract"));
				resultInfo.setInputUserID(rs.getLong("InputuserID"));
				resultInfo.setCheckUserID(rs.getLong("CheckuserID"));
				
				
				String strDetailPageURL = "/settlement/query/control/querycontrol.jsp";
				String param = "TransactionTypeID="+resultInfo.getTransactionTypeID()+"&lID="+resultInfo.getID()+"&TransNo="+resultInfo.getTransNo()+"&strFailPageURL=../query/view/v022.jsp";

				PagerTools.returnCellList(cellList, DataFormat.getDateString(resultInfo.getExecute()));
				if (SETTConstant.TransactionType.isInterestTransaction(resultInfo.getTransactionTypeID()))
				{
					PagerTools.returnCellList(cellList, resultInfo.getTransNo()+",,");
				}else{
					PagerTools.returnCellList(cellList, resultInfo.getTransNo()+","+strDetailPageURL+","+param);
				}
				PagerTools.returnCellList(cellList, SETTConstant.TransactionType.getName(resultInfo.getTransactionTypeID()));
				PagerTools.returnCellList(cellList, resultInfo.getDepositNo());
				PagerTools.returnCellList(cellList, DataFormat.formatDisabledAmount(resultInfo.getPayAmount()));
				
				if(resultInfo.getTransactionTypeID()==SETTConstant.TransactionType.REPORTLOSS || resultInfo.getTransactionTypeID()==SETTConstant.TransactionType.REPORTFIND || resultInfo.getTransactionTypeID()==SETTConstant.TransactionType.FREEZE || resultInfo.getTransactionTypeID()==SETTConstant.TransactionType.DEFREEZE)
				{
					PagerTools.returnCellList(cellList, "");
				}else{
					if(resultInfo.getBankID() > 0)
					{
						PagerTools.returnCellList(cellList, "银行收款");
					}
					else
					{
						PagerTools.returnCellList(cellList, "内部转账");
					}
				}
				
				if(resultInfo.getTransactionTypeID()==SETTConstant.TransactionType.REPORTLOSS || resultInfo.getTransactionTypeID()==SETTConstant.TransactionType.REPORTFIND || resultInfo.getTransactionTypeID()==SETTConstant.TransactionType.FREEZE || resultInfo.getTransactionTypeID()==SETTConstant.TransactionType.DEFREEZE)
				{
					
					PagerTools.returnCellList(cellList, "");
				
				}else{
					
					if(resultInfo.getReceiveAccountNo() != null && !resultInfo.getReceiveAccountNo().equals(conditionInfo.getPayAccountNoStart()))
					{
						PagerTools.returnCellList(cellList,resultInfo.getReceiveAccountNo());
					}
					else if(resultInfo.getPayAccountNo() != null )
					{
						PagerTools.returnCellList(cellList,resultInfo.getPayAccountNo());
					}
					else
					{
						PagerTools.returnCellList(cellList,"");
				
					}
				}
				
				if(resultInfo.getBankID() > 0)
				{
					
					PagerTools.returnCellList(cellList, NameRef.getBankNameByID(resultInfo.getBankID()));

				}
				else
				{
					PagerTools.returnCellList(cellList,"");
	
				}
				
				PagerTools.returnCellList(cellList,SETTConstant.TransactionStatus.getName(resultInfo.getStatusID()));
				PagerTools.returnCellList(cellList,resultInfo.getAbstract());
				PagerTools.returnCellList(cellList,NameRef.getUserNameByID(resultInfo.getInputUserID()));
				PagerTools.returnCellList(cellList,NameRef.getUserNameByID(resultInfo.getCheckUserID()));

				rowInfo = new ResultPagerRowInfo();
				rowInfo.setCell(cellList);
				rowInfo.setId(String.valueOf(rs.getLong("rownum1")));

				// 返回数据
				resultList.add(rowInfo);

			}
		}
		return resultList;
	}

	public ArrayList qTSubAccountTotalResultSetHandle(ResultSet rs, Map params)
			throws Exception {
		return null;
	}

	public PagerInfo queryAccountTransactionInfo(
			QueryTransactionConditionInfo conditionInfo) throws Exception {
		PagerInfo pagerInfo = null;
		String sql = null;
		try {
			pagerInfo = new PagerInfo();
			// 得到查询SQL
			sql = dao.getTransactionSQLForSubAccount(conditionInfo);
			pagerInfo.setSqlString(sql);

			Map params = new HashMap();
			params.put("param", conditionInfo);
			pagerInfo.setExtensionMothod(QTransactionBiz.class,
					"queryAccountTransactionInfoResultSetHandle", params);

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
	}
	public ArrayList queryAccountTransactionInfoResultSetHandle(ResultSet rs, Map params) throws SQLException{
		QueryTransactionConditionInfo conditionInfo = (QueryTransactionConditionInfo) params.get("param");
		ArrayList resultList = new ArrayList(); // 最终返回结果
		ArrayList cellList = null;// 列
		ResultPagerRowInfo rowInfo = null;// 行
		QueryTransactionInfo resultInfo;
		if (rs != null) {
			while (rs.next()) {
				cellList = new ArrayList();
				resultInfo = new QueryTransactionInfo();
				resultInfo.setTransactionTypeID(rs.getLong("TransactionTypeID"));
				resultInfo.setID(rs.getLong("ID"));
				resultInfo.setTransNo(rs.getString("TransNo"));
				resultInfo.setExecute(rs.getTimestamp("Execute"));
				resultInfo.setDepositNo(rs.getString("DepositNo"));
				resultInfo.setPayAmount(rs.getDouble("PayAmount"));
				resultInfo.setBankID(rs.getLong("BankID"));
				resultInfo.setReceiveAccountNo(rs.getString("ReceiveAccountNo"));
				resultInfo.setPayAccountNo(rs.getString("PayAccountNo"));
				resultInfo.setStatusID(rs.getLong("StatusID"));
				resultInfo.setAbstract(rs.getString("Abstract"));
				resultInfo.setInputUserID(rs.getLong("InputuserID"));
				resultInfo.setCheckUserID(rs.getLong("CheckuserID"));
				
				String strDetailPageURL = "/settlement/query/control/querycontrol.jsp";
				String param = "TransactionTypeID="+resultInfo.getTransactionTypeID()+"&TransNo="+resultInfo.getTransNo()+"&strFailPageURL=../query/view/v022.jsp";

				String strLoanAccountNo = "";//贷款账户号
				if (resultInfo.getReceiveAccountNo() != null && resultInfo.getReceiveAccountNo().length() <= 10)
				{
					strLoanAccountNo = resultInfo.getReceiveAccountNo();
				}
				else if (resultInfo.getPayAccountNo() != null && resultInfo.getPayAccountNo().length() <= 10)
				{
					strLoanAccountNo = resultInfo.getPayAccountNo();
				}
				else
				{
					strLoanAccountNo = "&nbsp;";
				}
				String strCurrenctAccountNo = "";//活期账户号
				if (resultInfo.getReceiveAccountNo() != null && resultInfo.getReceiveAccountNo().length() > 10)
				{
					strCurrenctAccountNo = resultInfo.getReceiveAccountNo();
				}
				else if (resultInfo.getPayAccountNo() != null && resultInfo.getPayAccountNo().length() > 10)
				{
					strCurrenctAccountNo = resultInfo.getPayAccountNo();
				}
				else
				{
					strCurrenctAccountNo = "&nbsp;";
				}
				
				//列表
				if (SETTConstant.TransactionType.isInterestTransaction(resultInfo.getTransactionTypeID()))
				{
					PagerTools.returnCellList(cellList, (resultInfo.getTransNo() == null || resultInfo.getTransNo().equals("")) ? "" : resultInfo.getTransNo()+",,");
				}else{
					PagerTools.returnCellList(cellList,((resultInfo.getTransNo() == null || resultInfo.getTransNo().equals("")) ? "" : resultInfo.getTransNo())+","+strDetailPageURL+","+param);
				}
				
				
				PagerTools.returnCellList(cellList,SETTConstant.TransactionType.getName(resultInfo.getTransactionTypeID()));
				PagerTools.returnCellList(cellList,strLoanAccountNo);
				PagerTools.returnCellList(cellList,strCurrenctAccountNo);
				PagerTools.returnCellList(cellList,(resultInfo.getContractID()<=0 ? "&nbsp;" : NameRef.getContractNoByID(resultInfo.getContractID())));
				PagerTools.returnCellList(cellList,(resultInfo.getLoanFormID()<=0 ? "&nbsp;" : NameRef.getPayFormNoByID(resultInfo.getLoanFormID())));
				PagerTools.returnCellList(cellList,DataFormat.formatDisabledAmount(resultInfo.getPayAmount()));
				PagerTools.returnCellList(cellList,(resultInfo.getInterestStart() == null ? "&nbsp;" : DataFormat.getDateString(resultInfo.getInterestStart())));
				PagerTools.returnCellList(cellList,resultInfo.getAbstract() == null ? "" : resultInfo.getAbstract());
				PagerTools.returnCellList(cellList,SETTConstant.TransactionStatus.getName(resultInfo.getStatusID()));
				PagerTools.returnCellList(cellList,(resultInfo.getInputUserID()<=0 ? "" : NameRef.getUserNameByID(resultInfo.getInputUserID())));
				PagerTools.returnCellList(cellList,(resultInfo.getCheckUserID()<=0 ? "" : NameRef.getUserNameByID(resultInfo.getCheckUserID())));

				rowInfo = new ResultPagerRowInfo();
				rowInfo.setCell(cellList);
				rowInfo.setId(String.valueOf(rs.getLong("rownum1")));
				resultList.add(rowInfo);
			}
		}
		return resultList;
	}

	public PagerInfo queryTransactionInfoForPrint(
			QueryTransactionConditionInfo conditionInfo, long lUserID) throws Exception {
		PagerInfo pagerInfo = null;
		String sql = null;
		try {
			pagerInfo = new PagerInfo();
			// 得到查询SQL
			sql = dao.getTransactionSQLForPrint(conditionInfo,lUserID);
			pagerInfo.setSqlString(sql);
			Map params = new HashMap();
			params.put("conditionInfo", conditionInfo);
			pagerInfo.setExtensionMothod(QTransactionBiz.class,
					"queryTransactionInfoForPrintResultSetHandle",params);

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
	}
	
	public ArrayList queryTransactionInfoForPrintResultSetHandle(ResultSet rs,Map paramMap) throws SQLException{
		ArrayList resultList = new ArrayList(); // 最终返回结果
		ArrayList cellList = null;// 列
		ResultPagerRowInfo rowInfo = null;// 行
		QueryTransactionConditionInfo conditionInfo = (QueryTransactionConditionInfo)paramMap.get("conditionInfo");
		QueryTransactionInfo resultInfo;
		if (rs != null) {
			while (rs.next()) {
				cellList = new ArrayList();
				resultInfo = new QueryTransactionInfo();
				resultInfo.setOperationtypeid(rs.getLong("operationtypeid"));
				resultInfo.setTransactionTypeID(rs.getLong("TransactionTypeID"));
				resultInfo.setID(rs.getLong("ID"));
				resultInfo.setTransNo(rs.getString("TransNo"));
				resultInfo.setSerialNo(rs.getLong("SerialNo"));
				resultInfo.setExecute(rs.getTimestamp("Execute"));
				resultInfo.setPayClientID(rs.getLong("PayclientID"));
				resultInfo.setStatusID(rs.getLong("StatusID"));
				resultInfo.setAbstract(rs.getString("Abstract"));
				resultInfo.setReceiveAccountNo(rs.getString("ReceiveAccountNo"));
				resultInfo.setReceiveAccountName(rs.getString("ReceiveAccountName"));
				resultInfo.setPayAmount(rs.getDouble("PayAmount"));
				resultInfo.setPayAccountNo(rs.getString("PayAccountNo"));
				resultInfo.setPayAccountName(rs.getString("PayAccountName"));
				resultInfo.setReceiveAmount(rs.getDouble("ReceiveAmount"));
				
				//特殊交易业务显示特殊交易类型
				long transactionTypeID = -1;
				if(resultInfo.getOperationtypeid() > 0)
				{
					transactionTypeID = resultInfo.getOperationtypeid();
				}
				else
				{
					transactionTypeID = resultInfo.getTransactionTypeID();
				}
				
//				String strDetailPageURL = strContext+"/print/view/v003_P.jsp?lID="+resultInfo.getID()+
//										"&TransactionTypeID="+resultInfo.getTransactionTypeID()+
//										"&operationTypeID="+resultInfo.getOperationtypeid()+
//										"&TransNo="+resultInfo.getTransNo()+
//										"&SerialNo="+resultInfo.getSerialNo()+
//										"&strFailPageURL="+ strContext +"/print/view/v002_P.jsp";

				
				PagerTools.returnCellList(cellList,resultInfo.getID()+"####"+resultInfo.getTransNo());
				PagerTools.returnCellList(cellList,DataFormat.getDateString(resultInfo.getExecute()));
				PagerTools.returnCellList(cellList,((resultInfo.getTransNo() == null || resultInfo.getTransNo().equals("")) ? "&nbsp;" : resultInfo.getTransNo())+","
						+resultInfo.getID()+","
						+resultInfo.getTransactionTypeID()+","
						+resultInfo.getOperationtypeid()+","
						+resultInfo.getTransNo()+","
						+resultInfo.getSerialNo()+",");
				PagerTools.returnCellList(cellList,SETTConstant.TransactionType.getName(transactionTypeID));
				PagerTools.returnCellList(cellList,resultInfo.getAbstract() == null ? "&nbsp;" : resultInfo.getAbstract());
				if ( resultInfo.getPayClientID() == conditionInfo.getClientId() ) {
					PagerTools.returnCellList(cellList,resultInfo.getReceiveAccountNo() == null ? "&nbsp;" : resultInfo.getReceiveAccountNo());
					PagerTools.returnCellList(cellList,resultInfo.getReceiveAccountName());
					PagerTools.returnCellList(cellList,DataFormat.formatDisabledAmount(resultInfo.getPayAmount()));
					PagerTools.returnCellList(cellList,"&nbsp;");
				}else{
					PagerTools.returnCellList(cellList,resultInfo.getPayAccountNo() == null ? "&nbsp;" : resultInfo.getPayAccountNo());
					PagerTools.returnCellList(cellList,resultInfo.getPayAccountName());
					PagerTools.returnCellList(cellList,"&nbsp;");
					PagerTools.returnCellList(cellList,DataFormat.formatDisabledAmount(resultInfo.getReceiveAmount()));
				}

				rowInfo = new ResultPagerRowInfo();
				rowInfo.setCell(cellList);
				resultList.add(rowInfo);
			}
		}
		return resultList;
	}
}
