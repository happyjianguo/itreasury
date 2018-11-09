package com.iss.itreasury.settlement.obinstruction.bizlogic;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.ebank.obfinanceinstr.bizlogic.OBFinanceInstrBiz;
import com.iss.itreasury.ebank.obfinanceinstr.dao.OBFinanceInstrDao;
import com.iss.itreasury.ebank.obfinanceinstr.dataentity.BranchbankInfo;
import com.iss.itreasury.ebank.obfinanceinstr.dataentity.PayerOrPayeeInfo;
import com.iss.itreasury.ebank.util.OBConstant;
import com.iss.itreasury.settlement.bizdelegation.TransCurrentDepositDelegation;
import com.iss.itreasury.settlement.bizdelegation.TransFixedDepositDelegation;
import com.iss.itreasury.settlement.bizdelegation.TransLoanDelegation;
import com.iss.itreasury.settlement.bizdelegation.TransSpecialOperationDelegation;
import com.iss.itreasury.settlement.obinstruction.dao.QOBFinanceInfoDao;
import com.iss.itreasury.settlement.obinstruction.dataentity.AmountCountInfo;
import com.iss.itreasury.settlement.obinstruction.dataentity.QueryOBFinanceInstrInfo;
import com.iss.itreasury.settlement.obinstruction.dataentity.QueryOBFinanceSumInfo;
import com.iss.itreasury.settlement.query.bizlogic.QQueryAccountBiz;
import com.iss.itreasury.settlement.query.paraminfo.QueryTransactionConditionInfo;
import com.iss.itreasury.settlement.query.queryobj.QTransaction;
import com.iss.itreasury.settlement.query.resultinfo.QueryAccountResultInfo;
import com.iss.itreasury.settlement.query.resultinfo.QueryTransactionNewInfo;
import com.iss.itreasury.settlement.setting.bizlogic.CommissionSettingBiz;
import com.iss.itreasury.settlement.setting.dataentity.CommissionSettingInfo;
import com.iss.itreasury.settlement.transcurrentdeposit.dataentity.TransCurrentDepositAssembler;
import com.iss.itreasury.settlement.transcurrentdeposit.dataentity.TransCurrentDepositInfo;
import com.iss.itreasury.settlement.transfixeddeposit.dataentity.TransFixedContinueInfo;
import com.iss.itreasury.settlement.transfixeddeposit.dataentity.TransFixedDrawInfo;
import com.iss.itreasury.settlement.transfixeddeposit.dataentity.TransFixedOpenInfo;
import com.iss.itreasury.settlement.transloan.dataentity.TransRepaymentLoanInfo;
import com.iss.itreasury.settlement.transspecial.dataentity.TransSpecialOperationInfo;
import com.iss.itreasury.settlement.util.NameRef;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.ConfigConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IException;
import com.iss.sysframe.pager.dataentity.PagerInfo;
import com.iss.sysframe.pager.dataentity.ResultPagerRowInfo;
import com.iss.sysframe.pager.tools.PagerTools;

public class QOBFinanceInfoBiz extends SettlementDAO{
	
	QOBFinanceInfoDao qOBFinanceInfoDao = new QOBFinanceInfoDao();
	
	public PagerInfo queryOBFinanceInfo(QueryOBFinanceInstrInfo qInfo) throws Exception{
		PagerInfo pagerInfo = null;
		String sql = null;
		try
		{
			pagerInfo = new PagerInfo();
			Map paramMap = new HashMap();
			paramMap.put("qInfo", qInfo);
			
			sql = qOBFinanceInfoDao.queryOBFinanceInfoSQL(qInfo);
			
			//�õ���ѯSQL
			pagerInfo.setSqlString(sql);
			
			pagerInfo.setExtensionMothod(QOBFinanceInfoBiz.class, "oBFinanceResultSetHandle" , paramMap);
			
			pagerInfo.setTotalExtensionMothod(QOBFinanceInfoBiz.class, "getOBFinanceSumAmount" , paramMap);
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("====>��ѯ�쳣", e);
		}
		return pagerInfo;
	}
	
	public ArrayList oBFinanceResultSetHandle(ResultSet rs , Map map) throws Exception{
		
		ArrayList resultList = new ArrayList(); //���շ��ؽ��
		ArrayList cellList = null;//��
		ResultPagerRowInfo rowInfo = null;//��
		QueryAccountResultInfo info = null;
		QueryOBFinanceInstrInfo qInfo = (QueryOBFinanceInstrInfo)map.get("qInfo");
		long officeID = -1;
		long currencyID = -1;
		long TransType = -1;
		long PayerAcctID = -1; 
	    long PayeeAcctID = -1;
		long RemitType = -1;
		double Amount = 0.0;
		Timestamp ExecuteDate = null;
		Timestamp SignSignDate = null;
		Timestamp CheckDate = null;
		String Abstract = "";
		long Source = -1;
		long DepositBillStatusID = -1;
		String TransNo = "";
		long lTransactionType = -1;
		long ID = -1;
		long Status = -1;
		long userId = -1;
		
		try{
			
			if(rs!=null)
			{
				while(rs.next())
				{
					ID = rs.getLong("ID");
					TransType = rs.getLong("TransType");
					PayerAcctID = rs.getLong("PayerAcctID");
					PayeeAcctID = rs.getLong("PayeeAcctID");
					RemitType = rs.getLong("RemitType");
					Amount = rs.getDouble("Amount");
					ExecuteDate = rs.getTimestamp("ExecuteDate");
					SignSignDate = rs.getTimestamp("signSignDate");
					CheckDate = rs.getTimestamp("CheckDate");
					Abstract = rs.getString("Note");
					Source = rs.getLong("Source");
					DepositBillStatusID = rs.getLong("DepositBillStatusID");
					TransNo = rs.getString("TransNo");
					lTransactionType = rs.getLong("DefaultTransType");
					Status = rs.getLong("Status");
					userId = rs.getLong("DealUserID");
					//�洢������
					cellList = new ArrayList();
					
					PagerTools.returnCellList(cellList,String.valueOf(ID)+","+ID+","+Status+","+userId+","+NameRef.getUserNameByID(userId));
					boolean isHTKG = Config.getBoolean(ConfigConstant.SETTLEMENT_ISHTKG, false); 
					String strInstructionType = "";
					if(isHTKG)
					{
						if(TransType==SETTConstant.SettInstrType.TRANSFER_BANKEXTERNAL||TransType==SETTConstant.SettInstrType.BOOKING_BANKEXTERNAL)
						{
							strInstructionType =  DataFormat.formatString(OBConstant.SettInstrType.getName(SETTConstant.SettInstrType.CAPTRANSFER_BANKPAY));
						}
						else if(TransType==SETTConstant.SettInstrType.TRANSFER_INTERNALVIREMENT||TransType==SETTConstant.SettInstrType.SCM_TRANSFER_INTERNALVIREMENT||TransType==SETTConstant.SettInstrType.BOOKING_INTERNALVIREMENT||TransType==SETTConstant.SettInstrType.SCM_BOOKING_INTERNALVIREMENT)
						{
							strInstructionType =  DataFormat.formatString(OBConstant.SettInstrType.getName(SETTConstant.SettInstrType.CAPTRANSFER_INTERNALVIREMENT));
						}else{
						    strInstructionType =  DataFormat.formatString(OBConstant.SettInstrType.getName(TransType));
						}
					}
					else
					{
						strInstructionType =  DataFormat.formatString(OBConstant.SettInstrType.getName(TransType));		
					}
					PagerTools.returnCellList(cellList,strInstructionType);
					PagerTools.returnCellList(cellList,DataFormat.formatString(NameRef.getAccountNoByID(PayerAcctID)));
					PagerTools.returnCellList(cellList,DataFormat.formatString(NameRef.getClientNameByAccountID(PayerAcctID)));
					PayerOrPayeeInfo payeeInfo = new PayerOrPayeeInfo();
					OBFinanceInstrDao obFinanceInstrDao = new OBFinanceInstrDao();
					payeeInfo = obFinanceInstrDao.getPayerOrPayeeInfoByID(PayeeAcctID,RemitType,OBConstant.PayerOrPayee.PAYEE);
					PagerTools.returnCellList(cellList,payeeInfo.getAccountNo());
					PagerTools.returnCellList(cellList,payeeInfo.getAccountName());
					PagerTools.returnCellList(cellList,DataFormat.formatString(OBConstant.SettRemitType.getName(RemitType)));
					PagerTools.returnCellList(cellList,DataFormat.formatDisabledAmount(Amount,2));
					PagerTools.returnCellList(cellList,DataFormat.formatString(DataFormat.formatDate(ExecuteDate)));
					PagerTools.returnCellList(cellList,DataFormat.formatString(Abstract));
					PagerTools.returnCellList(cellList,NameRef.getSourceNameByID(Source));
					String strStatus = "";
					if(DepositBillStatusID>0){
						strStatus = DataFormat.formatString(OBConstant.SettInstrStatus.getName(DepositBillStatusID));
					}else{
						strStatus = DataFormat.formatString(OBConstant.SettInstrStatus.getName(Status));
					}
					if(strStatus.equals("�Ѹ���")||strStatus.equals("��ǩ��"))
					{
						strStatus="���ύ";
					}
					PagerTools.returnCellList(cellList,strStatus);
					String strTransDetailsURL ="";
					String url1="";
					String url2="";
					String strTransNo = DataFormat.formatString(TransNo);
					if(strTransNo!=null && strTransNo.length()>0)
					{
						//��ͬ�������Ͳ�ͬ
						//���ڿ���
						if(lTransactionType==SETTConstant.TransactionType.OPENFIXEDDEPOSIT)
						{
							TransFixedDepositDelegation depositDelegation = new TransFixedDepositDelegation();	
							TransFixedOpenInfo fixedOpenInfo = null;
							fixedOpenInfo = depositDelegation.openFindByTransNo(strTransNo);
							if(fixedOpenInfo.getStatusID()==SETTConstant.TransactionStatus.APPROVALING || fixedOpenInfo.getStatusID()==SETTConstant.TransactionStatus.APPROVALED || fixedOpenInfo.getStatusID()==SETTConstant.TransactionStatus.CHECK || fixedOpenInfo.getStatusID()==SETTConstant.TransactionStatus.DELETED || fixedOpenInfo.getInputUserID()!=qInfo.getUserID())
							{
								strTransDetailsURL=  "/settlement/tran/fixed/control/c003.jsp?strAction=FixedQuery&strPrintAction=obPrint&strTransNo=" + strTransNo+"&OBInstr=obinstr&strSuccessPageURL=/settlement/tran/fixed/view/v004.jsp&strFailPageURL=/settlement/obinstruction/view/v001.jsp";
								url1="/settlement/tran/fixed/control/c003.jsp";
								url2="strAction=FixedQuery&strPrintAction=obPrint&strTransNo=" + strTransNo+"&OBInstr=obinstr&strSuccessPageURL=/settlement/tran/fixed/view/v004.jsp&strFailPageURL=/settlement/obinstruction/view/v001.jsp";
							}
							else
							{
								strTransDetailsURL=  "/settlement/tran/fixed/control/c001.jsp?lID="+fixedOpenInfo.getID()+"&strAction=toModify"+"&OBInstr=obinstr&strSuccessPageURL=/settlement/tran/fixed/view/v007.jsp&strFailPageURL=/settlement/obinstruction/view/v001.jsp";
								url1="/settlement/tran/fixed/control/c001.jsp";
								url2="lID="+fixedOpenInfo.getID()+"&strAction=toModify"+"&OBInstr=obinstr&strSuccessPageURL=/settlement/tran/fixed/view/v007.jsp&strFailPageURL=/settlement/obinstruction/view/v001.jsp";
							}
						}
						//֪ͨ����
						if(lTransactionType==SETTConstant.TransactionType.OPENNOTIFYACCOUNT)
						{
							TransFixedDepositDelegation depositDelegation = new TransFixedDepositDelegation();	
							TransFixedOpenInfo fixedOpenInfo = null;
							fixedOpenInfo = depositDelegation.openFindByTransNo(strTransNo);
							if(fixedOpenInfo.getStatusID()==SETTConstant.TransactionStatus.APPROVALING || fixedOpenInfo.getStatusID()==SETTConstant.TransactionStatus.APPROVALED || fixedOpenInfo.getStatusID()==SETTConstant.TransactionStatus.CHECK || fixedOpenInfo.getStatusID()==SETTConstant.TransactionStatus.DELETED || fixedOpenInfo.getInputUserID()!=qInfo.getUserID())
							{
								strTransDetailsURL=  "/settlement/tran/fixed/control/c003.jsp?strAction=FixedQuery&strPrintAction=obPrint&strTransNo=" + strTransNo +"&OBInstr=obinstr&strSuccessPageURL=/settlement/tran/fixed/view/v034.jsp&strFailPageURL=/settlement/obinstruction/view/v001.jsp";
								url1="/settlement/tran/fixed/control/c003.jsp";
								url2="strAction=FixedQuery&strPrintAction=obPrint&strTransNo=" + strTransNo +"&OBInstr=obinstr&strSuccessPageURL=/settlement/tran/fixed/view/v034.jsp&strFailPageURL=/settlement/obinstruction/view/v001.jsp";
							}
							else
							{
								strTransDetailsURL=  "/settlement/tran/fixed/control/c001.jsp?lID="+fixedOpenInfo.getID()+"&strAction=toModify"+"&OBInstr=obinstr&strSuccessPageURL=/settlement/tran/fixed/view/v037.jsp&strFailPageURL=/settlement/obinstruction/view/v001.jsp";
								url1="/settlement/tran/fixed/control/c001.jsp";
								url2="lID="+fixedOpenInfo.getID()+"&strAction=toModify"+"&OBInstr=obinstr&strSuccessPageURL=/settlement/tran/fixed/view/v037.jsp&strFailPageURL=/settlement/obinstruction/view/v001.jsp";
							}
						}
						//����֧ȡ
						if(lTransactionType==SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER)
						{
							TransFixedDepositDelegation depositDelegation = new TransFixedDepositDelegation();	
							TransFixedDrawInfo fixedDrawInfo = null;
							fixedDrawInfo = depositDelegation.drawFindByTransNo(strTransNo);
							if(fixedDrawInfo.getStatusID()==SETTConstant.TransactionStatus.APPROVALING || fixedDrawInfo.getStatusID()==SETTConstant.TransactionStatus.APPROVALED || fixedDrawInfo.getStatusID()==SETTConstant.TransactionStatus.CHECK || fixedDrawInfo.getStatusID()==SETTConstant.TransactionStatus.DELETED || fixedDrawInfo.getInputUserID()!=qInfo.getUserID())
							{
								strTransDetailsURL=  "/settlement/tran/fixed/control/c013.jsp?strAction=FixedQuery&strPrintAction=obPrint&strTransNo=" + strTransNo+"&OBInstr=obinstr&strSuccessPageURL=/settlement/tran/fixed/view/v015.jsp&strFailPageURL=/settlement/obinstruction/view/v001.jsp";
								url1="/settlement/tran/fixed/control/c013.jsp";
								url2="strAction=FixedQuery&strPrintAction=obPrint&strTransNo=" + strTransNo+"&OBInstr=obinstr&strSuccessPageURL=/settlement/tran/fixed/view/v015.jsp&strFailPageURL=/settlement/obinstruction/view/v001.jsp";
							}
							else
							{
								strTransDetailsURL=  "/settlement/tran/fixed/control/c011.jsp?lID="+fixedDrawInfo.getID()+"&strAction=toModify&OBInstr=obinstr&strSuccessPageURL=/settlement/tran/fixed/view/v018.jsp&strFailPageURL=/settlement/obinstruction/view/v001.jsp";
								url1="/settlement/tran/fixed/control/c011.jsp";
								url2="lID="+fixedDrawInfo.getID()+"&strAction=toModify&OBInstr=obinstr&strSuccessPageURL=/settlement/tran/fixed/view/v018.jsp&strFailPageURL=/settlement/obinstruction/view/v001.jsp";
							}
						}
						//֪֧ͨȡ
						if(lTransactionType==SETTConstant.TransactionType.NOTIFYDEPOSITDRAW)
						{
							TransFixedDepositDelegation depositDelegation = new TransFixedDepositDelegation();	
							TransFixedDrawInfo fixedDrawInfo = null;
							fixedDrawInfo = depositDelegation.drawFindByTransNo(strTransNo);
							if(fixedDrawInfo.getStatusID()==SETTConstant.TransactionStatus.APPROVALING || fixedDrawInfo.getStatusID()==SETTConstant.TransactionStatus.APPROVALED || fixedDrawInfo.getStatusID()==SETTConstant.TransactionStatus.CHECK || fixedDrawInfo.getStatusID()==SETTConstant.TransactionStatus.DELETED || fixedDrawInfo.getInputUserID()!=qInfo.getUserID())
							{
								strTransDetailsURL=  "/settlement/tran/fixed/control/c013.jsp?strAction=FixedQuery&strPrintAction=obPrint&strTransNo=" + strTransNo+"&OBInstr=obinstr&strSuccessPageURL=/settlement/tran/fixed/view/v045.jsp&strFailPageURL=/settlement/obinstruction/view/v001.jsp";
								url1="/settlement/tran/fixed/control/c013.jsp";
								url2="strAction=FixedQuery&strPrintAction=obPrint&strTransNo=" + strTransNo+"&OBInstr=obinstr&strSuccessPageURL=/settlement/tran/fixed/view/v045.jsp&strFailPageURL=/settlement/obinstruction/view/v001.jsp";
							}
							else
							{
								strTransDetailsURL=  "/settlement/tran/fixed/control/c011.jsp?lID="+fixedDrawInfo.getID()+"&strAction=toModify&OBInstr=obinstr&strSuccessPageURL=/settlement/tran/fixed/view/v048.jsp&strFailPageURL=/settlement/obinstruction/view/v001.jsp";
								url1="/settlement/tran/fixed/control/c011.jsp";
								url2="lID="+fixedDrawInfo.getID()+"&strAction=toModify&OBInstr=obinstr&strSuccessPageURL=/settlement/tran/fixed/view/v048.jsp&strFailPageURL=/settlement/obinstruction/view/v001.jsp";
							}
						}	
						//��������
						if(lTransactionType==SETTConstant.TransactionType.FIXEDCONTINUETRANSFER)
						{
							TransFixedDepositDelegation depositDelegation = new TransFixedDepositDelegation();	
							TransFixedContinueInfo fixedContinueInfo = null;
							fixedContinueInfo = depositDelegation.continueFindByTransNo(strTransNo);
							if(fixedContinueInfo.getStatusID()==SETTConstant.TransactionStatus.APPROVALING || fixedContinueInfo.getStatusID()==SETTConstant.TransactionStatus.APPROVALED || fixedContinueInfo.getStatusID()==SETTConstant.TransactionStatus.CHECK || fixedContinueInfo.getStatusID()==SETTConstant.TransactionStatus.DELETED || fixedContinueInfo.getInputUserID()!=qInfo.getUserID())
							{
								strTransDetailsURL=  "/settlement/tran/fixed/control/c023.jsp?strAction=FixedQuery&strPrintAction=obPrint&strTransNo=" + strTransNo +"&OBInstr=obinstr&strSuccessPageURL=/settlement/tran/fixed/view/v025.jsp&strFailPageURL=/settlement/obinstruction/view/v001.jsp";
								url1="/settlement/tran/fixed/control/c023.jsp";
								url2="strAction=FixedQuery&strPrintAction=obPrint&strTransNo=" + strTransNo +"&OBInstr=obinstr&strSuccessPageURL=/settlement/tran/fixed/view/v025.jsp&strFailPageURL=/settlement/obinstruction/view/v001.jsp";
							}
							else
							{
								strTransDetailsURL=  "/settlement/tran/fixed/control/c021.jsp?lID="+fixedContinueInfo.getID()+"&strAction=toModify&OBInstr=obinstr&strSuccessPageURL=/settlement/tran/fixed/view/v026.jsp&strFailPageURL=/settlement/obinstruction/view/v001.jsp";
								url1="/settlement/tran/fixed/control/c021.jsp";
								url2="lId="+fixedContinueInfo.getID()+"&strAction=toModify&OBInstr=obinstr&strSuccessPageURL=/settlement/tran/fixed/view/v026.jsp&strFailPageURL=/settlement/obinstruction/view/v001.jsp";
							}
						}
						//��Ӫ�ջ�
						if(lTransactionType==SETTConstant.TransactionType.TRUSTLOANRECEIVE)
						{
							 //��Ӫ�ջ�				
							TransLoanDelegation depositDelegation = new TransLoanDelegation();
							TransRepaymentLoanInfo transRepaymentLoanInfo = null;
							long trustLoanID = -1;
							trustLoanID = depositDelegation.repaymentGetIDByTransNo(strTransNo);
							transRepaymentLoanInfo = depositDelegation.repaymentFindDetailByID(trustLoanID);
							if(transRepaymentLoanInfo!=null)
							{
								if(transRepaymentLoanInfo.getStatusID()==SETTConstant.TransactionStatus.APPROVALING || transRepaymentLoanInfo.getStatusID()==SETTConstant.TransactionStatus.APPROVALED || transRepaymentLoanInfo.getStatusID()==SETTConstant.TransactionStatus.CHECK || transRepaymentLoanInfo.getStatusID()==SETTConstant.TransactionStatus.DELETED || transRepaymentLoanInfo.getInputUserID()!=qInfo.getUserID())
								{
									strTransDetailsURL =  "/settlement/tran/loan/control/c014.jsp?strTransNo="+strTransNo+"&OBInstr=obinstr&strAction=Query&strPrintAction=obPrint&strSuccessPageURL=/settlement/tran/loan/view/v016.jsp&strFailPageURL=/settlement/obinstruction/view/v001.jsp&lID="+trustLoanID;
									url1="/settlement/tran/loan/control/c014.jsp";
									url2="strTransNo="+strTransNo+"&OBInstr=obinstr&strAction=Query&strPrintAction=obPrint&strSuccessPageURL=/settlement/tran/loan/view/v016.jsp&strFailPageURL=/settlement/obinstruction/view/v001.jsp&lID="+trustLoanID;
								}
								else
								{
									strTransDetailsURL=  "/settlement/tran/loan/control/c011.jsp?lID="+trustLoanID+"&strAction=toModify&OBInstr=obinstr&strSuccessPageURL=/settlement/tran/loan/view/v019-1.jsp&strFailPageURL=/settlement/obinstruction/view/v001.jsp";
									url1="/settlement/tran/loan/control/c011.jsp";
									url2="lID="+trustLoanID+"&strAction=toModify&OBInstr=obinstr&strSuccessPageURL=/settlement/tran/loan/view/v019-1.jsp&strFailPageURL=/settlement/obinstruction/view/v001.jsp";
								}
							}
						}	
						//�����ջ�
						if(lTransactionType==SETTConstant.TransactionType.BANKGROUPLOANRECEIVE)
						{
							 //�����ջ�				
							TransLoanDelegation depositDelegation = new TransLoanDelegation();
							TransRepaymentLoanInfo transRepaymentLoanInfo = null;
							long trustLoanID = -1;
							trustLoanID = depositDelegation.repaymentGetIDByTransNo(strTransNo);
							transRepaymentLoanInfo = depositDelegation.repaymentFindDetailByID(trustLoanID);
							if(transRepaymentLoanInfo!=null)
							{
								if(transRepaymentLoanInfo.getStatusID()==SETTConstant.TransactionStatus.APPROVALING || transRepaymentLoanInfo.getStatusID()==SETTConstant.TransactionStatus.APPROVALED || transRepaymentLoanInfo.getStatusID()==SETTConstant.TransactionStatus.CHECK || transRepaymentLoanInfo.getStatusID()==SETTConstant.TransactionStatus.DELETED || transRepaymentLoanInfo.getInputUserID()!=qInfo.getUserID())
								{
									strTransDetailsURL =  "/settlement/tran/loan/control/c034.jsp?strTransNo="+strTransNo+"&OBInstr=obinstr&strAction=Query&strPrintAction=obPrint&strSuccessPageURL=/settlement/tran/loan/view/v076.jsp&strFailPageURL==/settlement/obinstruction/view/v001.jsp&lID="+trustLoanID;
									url1="/settlement/tran/loan/control/c034.jsp";
									url2="strTransNo="+strTransNo+"&OBInstr=obinstr&strAction=Query&strPrintAction=obPrint&strSuccessPageURL=/settlement/tran/loan/view/v076.jsp&strFailPageURL==/settlement/obinstruction/view/v001.jsp&lID="+trustLoanID;
								}
								else
								{
									strTransDetailsURL=  "/settlement/tran/loan/control/c031.jsp?lID="+trustLoanID+"&strAction=toModify&OBInstr=obinstr&strSuccessPageURL=/settlement/tran/loan/view/v079-1.jsp&strFailPageURL=/settlement/obinstruction/view/v001.jsp";
									url1="/settlement/tran/loan/control/c031.jsp";
									url2="lID="+trustLoanID+"&strAction=toModify&OBInstr=obinstr&strSuccessPageURL=/settlement/tran/loan/view/v079-1.jsp&strFailPageURL=/settlement/obinstruction/view/v001.jsp";
								}
							}
						}	
						//ί���ջ�
						if(lTransactionType==SETTConstant.TransactionType.CONSIGNLOANRECEIVE)
						{
							 //ί���ջ�				
							TransLoanDelegation depositDelegation = new TransLoanDelegation();
							TransRepaymentLoanInfo transRepaymentLoanInfo = null;
							long trustLoanID = -1;
							trustLoanID = depositDelegation.repaymentGetIDByTransNo(strTransNo);
							transRepaymentLoanInfo = depositDelegation.repaymentFindDetailByID(trustLoanID);
							if(transRepaymentLoanInfo!=null)
							{
								if(transRepaymentLoanInfo.getStatusID()==SETTConstant.TransactionStatus.APPROVALING || transRepaymentLoanInfo.getStatusID()==SETTConstant.TransactionStatus.APPROVALED || transRepaymentLoanInfo.getStatusID()==SETTConstant.TransactionStatus.CHECK || transRepaymentLoanInfo.getStatusID()==SETTConstant.TransactionStatus.DELETED || transRepaymentLoanInfo.getInputUserID()!=qInfo.getUserID())
								{
									strTransDetailsURL =  "/settlement/tran/loan/control/c014.jsp?strTransNo="+strTransNo+"&OBInstr=obinstr&strAction=Query&strPrintAction=obPrint&strSuccessPageURL=/settlement/tran/loan/view/v038.jsp&strFailPageURL=/settlement/obinstruction/view/v001.jsp&lID="+trustLoanID;
									url1="/settlement/tran/loan/control/c014.jsp";
									url2="strTransNo="+strTransNo+"&OBInstr=obinstr&strAction=Query&strPrintAction=obPrint&strSuccessPageURL=/settlement/tran/loan/view/v038.jsp&strFailPageURL=/settlement/obinstruction/view/v001.jsp&lID="+trustLoanID;
								}
								else
								{
									strTransDetailsURL=  "/settlement/tran/loan/control/c011.jsp?lID="+trustLoanID+"&strAction=toModify&OBInstr=obinstr&strSuccessPageURL=.settlement/tran/loan/view/v039-1.jsp&strFailPageURL=/settlement/obinstruction/view/v001.jsp";
									url1="/settlement/tran/loan/control/c011.jsp";
									url2="lID="+trustLoanID+"&strAction=toModify&OBInstr=obinstr&strSuccessPageURL=.settlement/tran/loan/view/v039-1.jsp&strFailPageURL=/settlement/obinstruction/view/v001.jsp";
								}
							}
						}
						//��Ϣ����֧��
						if(lTransactionType==SETTConstant.TransactionType.INTERESTFEEPAYMENT)
						{
							 //��Ϣ����֧��				
							TransLoanDelegation depositDelegation = new TransLoanDelegation();
							TransRepaymentLoanInfo transRepaymentLoanInfo = null;
							long trustLoanID = -1;
							trustLoanID = depositDelegation.repaymentGetIDByTransNo(strTransNo);
							transRepaymentLoanInfo = depositDelegation.repaymentFindDetailByID(trustLoanID);
							if(transRepaymentLoanInfo!=null)
							{
								if(transRepaymentLoanInfo.getStatusID()==SETTConstant.TransactionStatus.APPROVALING || transRepaymentLoanInfo.getStatusID()==SETTConstant.TransactionStatus.APPROVALED || transRepaymentLoanInfo.getStatusID()==SETTConstant.TransactionStatus.CHECK || transRepaymentLoanInfo.getStatusID()==SETTConstant.TransactionStatus.DELETED || transRepaymentLoanInfo.getInputUserID()!=qInfo.getUserID())
								{
									strTransDetailsURL =  "/settlement/tran/loan/control/c044.jsp?strTransNo="+strTransNo+"&OBInstr=obinstr&strAction=Query&strPrintAction=obPrint&strSuccessPageURL=/settlement/tran/loan/view/v047.jsp&strFailPageURL=/settlement/obinstruction/view/v001.jsp&lID="+trustLoanID;
									url1="/settlement/tran/loan/control/c044.jsp";
									url2="strTransNo="+strTransNo+"&OBInstr=obinstr&strAction=Query&strPrintAction=obPrint&strSuccessPageURL=/settlement/tran/loan/view/v047.jsp&strFailPageURL=/settlement/obinstruction/view/v001.jsp&lID="+trustLoanID;
								}
								else
								{					
									strTransDetailsURL=  "/settlement/tran/loan/control/c041.jsp?lID="+trustLoanID+"&strAction=" + String.valueOf(SETTConstant.Actions.TODETAIL) +"&OBInstr=obinstr&strSuccessPageURL=/settlement/tran/loan/view/v049-1.jsp&strFailPageURL=/settlement/obinstruction/view/v001.jsp";
									url1="/settlement/tran/loan/control/c041.jsp";
									url2="lID="+trustLoanID+"&strAction=" + String.valueOf(SETTConstant.Actions.TODETAIL) +"&OBInstr=obinstr&strSuccessPageURL=/settlement/tran/loan/view/v049-1.jsp&strFailPageURL=/settlement/obinstruction/view/v001.jsp";
								}
							}
						}
						//���⽻��
						if(lTransactionType==SETTConstant.TransactionType.SPECIALOPERATION)
						{
							TransSpecialOperationDelegation depositDelegation = new TransSpecialOperationDelegation();
							
							TransSpecialOperationInfo specialInfo = new TransSpecialOperationInfo();;					
							
							long lSpecialID = -1;
							lSpecialID = depositDelegation.getIDByTransNo(strTransNo);
				
							specialInfo = depositDelegation.findDetailByID(lSpecialID);
							
				          	if(specialInfo != null)
			            	{
				       	        if(specialInfo.getNstatusid()==SETTConstant.TransactionStatus.APPROVALING || specialInfo.getNstatusid()==SETTConstant.TransactionStatus.APPROVALED || specialInfo.getNstatusid()==SETTConstant.TransactionStatus.CHECK || specialInfo.getNstatusid()==SETTConstant.TransactionStatus.DELETED || specialInfo.getNinputuserid()!=qInfo.getUserID())
								{
									strTransDetailsURL=  "/settlement/tran/special/control/c004.jsp?strAction=Query&strPrintAction=obPrint&strTransNo=" + strTransNo +"&OBInstr=obinstr&strSuccessPageURL=/settlement/tran/special/view/v006.jsp&strFailPageURL=/settlement/obinstruction/view/v001.jsp&lId=" + lSpecialID + "&FromQuery=yes ";
									url1="/settlement/tran/special/control/c004.jsp";
									url2="strAction=Query&strPrintAction=obPrint&strTransNo=" + strTransNo +"&OBInstr=obinstr&strSuccessPageURL=/settlement/tran/special/view/v006.jsp&strFailPageURL=/settlement/obinstruction/view/v001.jsp&lId=" + lSpecialID + "&FromQuery=yes ";
								}
								else
								{
									strTransDetailsURL=  "/settlement/tran/special/control/c001.jsp?lId="+specialInfo.getId()+"&strAction=toModify&OBInstr=obinstr&strSuccessPageURL=/settlement/tran/special/view/v007.jsp&strFailPageURL=/settlement/obinstruction/view/v001.jsp";
									url1="/settlement/tran/special/control/c001.jsp";
									url2="lId="+specialInfo.getId()+"&strAction=toModify&OBInstr=obinstr&strSuccessPageURL=/settlement/tran/special/view/v007.jsp&strFailPageURL=/settlement/obinstruction/view/v001.jsp";
								}
			       		    }
						}
						//�ڲ�ת��
						if(lTransactionType==SETTConstant.TransactionType.INTERNALVIREMENT)
						{
							TransCurrentDepositDelegation depositDelegation = new TransCurrentDepositDelegation();	
							TransCurrentDepositInfo currInfo = new TransCurrentDepositInfo();;
							currInfo.setTransNo(strTransNo);
							TransCurrentDepositAssembler data = new TransCurrentDepositAssembler(currInfo);
							Collection col=null;
							col = depositDelegation.findByConditions(data,-1,false);
							
							if(col != null && !col.isEmpty())
					        {								
								TransCurrentDepositInfo currentInfo = null;
								Iterator iterator=col.iterator();				
								if(iterator.hasNext())
								{
									currentInfo = (TransCurrentDepositInfo)iterator.next();
								}
					          	if(currentInfo != null)
				            	{
					       	        if(currentInfo.getStatusID()==SETTConstant.TransactionStatus.APPROVALING || currentInfo.getStatusID()==SETTConstant.TransactionStatus.APPROVALED || currentInfo.getStatusID()==SETTConstant.TransactionStatus.CHECK || currentInfo.getStatusID()==SETTConstant.TransactionStatus.DELETED || currentInfo.getInputUserID()!=qInfo.getUserID())
									{
										strTransDetailsURL=  "/settlement/tran/current/control/c003.jsp?strAction=Query&strPrintAction=obPrint&strTransNo=" + strTransNo +"&OBInstr=obinstr&strSuccessPageURL=/settlement/tran/current/view/v015.jsp&strFailPageURL=/settlement/obinstruction/view/v001.jsp";
										url1="/settlement/tran/current/control/c003.jsp";
										url2="strAction=Query&strPrintAction=obPrint&strTransNo=" + strTransNo +"&OBInstr=obinstr&strSuccessPageURL=/settlement/tran/current/view/v015.jsp&strFailPageURL=/settlement/obinstruction/view/v001.jsp";
									}
									else
									{
										strTransDetailsURL=  "/settlement/tran/current/control/c001.jsp?lId="+currentInfo.getId()+"&strAction=toModify&OBInstr=obinstr&strSuccessPageURL=/settlement/tran/current/view/v017.jsp&strFailPageURL=/settlement/obinstruction/view/v001.jsp";
										url1="/settlement/tran/current/control/c001.jsp";
										url2="lId="+currentInfo.getId()+"&strAction=toModify&OBInstr=obinstr&strSuccessPageURL=/settlement/tran/current/view/v017.jsp&strFailPageURL=/settlement/obinstruction/view/v001.jsp";
									}
				       		    }
							}
						}
						//������λ���и���
						if(lTransactionType==SETTConstant.TransactionType.SUBCLIENT_BANKPAY||lTransactionType==SETTConstant.TransactionType.BANKPAY_DOWNTRANSFER)
						{
							TransCurrentDepositDelegation depositDelegation = new TransCurrentDepositDelegation();	
							TransCurrentDepositInfo currInfo = new TransCurrentDepositInfo();;
							currInfo.setTransNo(strTransNo);
							TransCurrentDepositAssembler data = new TransCurrentDepositAssembler(currInfo);
							Collection col=null;
							col = depositDelegation.findByConditions(data,-1,false);
							
							if(col != null && !col.isEmpty())
					        {								
								TransCurrentDepositInfo currentInfo = null;
								Iterator iterator=col.iterator();				
								if(iterator.hasNext())
								{
									currentInfo = (TransCurrentDepositInfo)iterator.next();
								}
					          	if(currentInfo != null)
				            	{
					       	        if(currentInfo.getStatusID()==SETTConstant.TransactionStatus.APPROVALING || currentInfo.getStatusID()==SETTConstant.TransactionStatus.APPROVALED || currentInfo.getStatusID()==SETTConstant.TransactionStatus.CHECK || currentInfo.getStatusID()==SETTConstant.TransactionStatus.DELETED || currentInfo.getInputUserID()!=qInfo.getUserID())
									{
										strTransDetailsURL=  "/settlement/tran/current/control/c003.jsp?strAction=Query&strPrintAction=obPrint&strTransNo=" + strTransNo +"&OBInstr=obinstr&strSuccessPageURL=/settlement/tran/current/view/v215.jsp&strFailPageURL=/settlement/obinstruction/view/v001.jsp";
										url1="/settlement/tran/current/control/c003.jsp";
										url2="strAction=Query&strPrintAction=obPrint&strTransNo=" + strTransNo +"&OBInstr=obinstr&strSuccessPageURL=/settlement/tran/current/view/v215.jsp&strFailPageURL=/settlement/obinstruction/view/v001.jsp";
									}
									else
									{
										strTransDetailsURL=  "/settlement/tran/current/control/c001.jsp?lId="+currentInfo.getId()+"&strAction=toModify&OBInstr=obinstr&strSuccessPageURL=/settlement/tran/current/view/v217.jsp&strFailPageURL=/settlement/obinstruction/view/v001.jsp";
										url1="/settlement/tran/current/control/c001.jsp";
										url2="lId="+currentInfo.getId()+"&strAction=toModify&OBInstr=obinstr&strSuccessPageURL=/settlement/tran/current/view/v217.jsp&strFailPageURL=/settlement/obinstruction/view/v001.jsp";
									}
				       		    }
							}
						}
						//���и���
						if(lTransactionType==SETTConstant.TransactionType.BANKPAY||lTransactionType==SETTConstant.TransactionType.BANKPAY_DOWNTRANSFER)
						{
							TransCurrentDepositDelegation depositDelegation = new TransCurrentDepositDelegation();	
							TransCurrentDepositInfo currInfo = new TransCurrentDepositInfo();;
							currInfo.setTransNo(strTransNo);
							TransCurrentDepositAssembler data = new TransCurrentDepositAssembler(currInfo);
							Collection col=null;
							col = depositDelegation.findByConditions(data,-1,false);
							
							if(col != null && !col.isEmpty())
					        {								
								TransCurrentDepositInfo currentInfo = null;
								Iterator iterator=col.iterator();				
								if(iterator.hasNext())
								{
									currentInfo = (TransCurrentDepositInfo)iterator.next();
								}
					          	if(currentInfo != null)
				            	{
					       	        if(currentInfo.getStatusID()==SETTConstant.TransactionStatus.APPROVALING || currentInfo.getStatusID()==SETTConstant.TransactionStatus.APPROVALED || currentInfo.getStatusID()==SETTConstant.TransactionStatus.CHECK || currentInfo.getStatusID()==SETTConstant.TransactionStatus.DELETED || currentInfo.getInputUserID()!=qInfo.getUserID())
									{
										strTransDetailsURL=  "/settlement/tran/current/control/c003.jsp?strAction=Query&strPrintAction=obPrint&strTransNo=" + strTransNo +"&OBInstr=obinstr&strSuccessPageURL=/settlement/tran/current/view/v115.jsp&strFailPageURL=/settlement/obinstruction/view/v001.jsp";
										url1="/settlement/tran/current/control/c003.jsp";
										url2="strAction=Query&strPrintAction=obPrint&strTransNo=" + strTransNo +"&OBInstr=obinstr&strSuccessPageURL=/settlement/tran/current/view/v115.jsp&strFailPageURL=/settlement/obinstruction/view/v001.jsp";
									}
									else
									{
										strTransDetailsURL=  "/settlement/tran/current/control/c001.jsp?lId="+currentInfo.getId()+"&strAction=toModify&OBInstr=obinstr&strSuccessPageURL=/settlement/tran/current/view/v117.jsp&strFailPageURL=/settlement/obinstruction/view/v001.jsp";
										url1="/settlement/tran/current/control/c001.jsp";
										url2="lId="+currentInfo.getId()+"&strAction=toModify&OBInstr=obinstr&strSuccessPageURL=/settlement/tran/current/view/v117.jsp&strFailPageURL=/settlement/obinstruction/view/v001.jsp";
									}
				       		    }
							}	
						}
						
						//���и����˾����
						if(lTransactionType==SETTConstant.TransactionType.BANKPAY_FINCOMPANYPAY)
						{
							TransCurrentDepositDelegation depositDelegation = new TransCurrentDepositDelegation();	
							TransCurrentDepositInfo currInfo = new TransCurrentDepositInfo();;
							currInfo.setTransNo(strTransNo);
							TransCurrentDepositAssembler data = new TransCurrentDepositAssembler(currInfo);
							Collection col=null;
							col = depositDelegation.findByConditions(data,-1,false);
							
							if(col != null && !col.isEmpty())
					        {								
								TransCurrentDepositInfo currentInfo = null;
								Iterator iterator=col.iterator();				
								if(iterator.hasNext())
								{
									currentInfo = (TransCurrentDepositInfo)iterator.next();
								}
					          	if(currentInfo != null)
				            	{
					       	        if(currentInfo.getStatusID()==SETTConstant.TransactionStatus.APPROVALING || currentInfo.getStatusID()==SETTConstant.TransactionStatus.APPROVALED || currentInfo.getStatusID()==SETTConstant.TransactionStatus.CHECK || currentInfo.getStatusID()==SETTConstant.TransactionStatus.DELETED || currentInfo.getInputUserID()!=qInfo.getUserID())
									{
										strTransDetailsURL=  "/settlement/tran/current/control/c003_3.jsp?strAction=Query&strPrintAction=obPrint&strTransNo=" + strTransNo +"&OBInstr=obinstr&strSuccessPageURL=/settlement/tran/current/view/v115_3.jsp&strFailPageURL=/settlement/obinstruction/view/v001.jsp";
										url1="/settlement/tran/current/control/c003_3.jsp";
										url2="strAction=Query&strPrintAction=obPrint&strTransNo=" + strTransNo +"&OBInstr=obinstr&strSuccessPageURL=/settlement/tran/current/view/v115_3.jsp&strFailPageURL=/settlement/obinstruction/view/v001.jsp";
									}
									else
									{
										strTransDetailsURL=  "/settlement/tran/current/control/c001_3.jsp?lId="+currentInfo.getId()+"&strAction=toModify&OBInstr=obinstr&strSuccessPageURL=/settlement/tran/current/view/v117_3.jsp&strFailPageURL=/settlement/obinstruction/view/v001.jsp";
										url1="/settlement/tran/current/control/c001_3.jsp";
										url2="lId="+currentInfo.getId()+"&strAction=toModify&OBInstr=obinstr&strSuccessPageURL=/settlement/tran/current/view/v117_3.jsp&strFailPageURL=/settlement/obinstruction/view/v001.jsp";
									}
				       		    }
							}	
						}
						
						//���и���-�����˻�
						if(lTransactionType==SETTConstant.TransactionType.BANKPAY_PAYSUBACCOUNT)
						{
							TransCurrentDepositDelegation depositDelegation = new TransCurrentDepositDelegation();	
							TransCurrentDepositInfo currInfo = new TransCurrentDepositInfo();;
							currInfo.setTransNo(strTransNo);
							TransCurrentDepositAssembler data = new TransCurrentDepositAssembler(currInfo);
							Collection col=null;
							col = depositDelegation.findByConditions(data,-1,false);
							
							if(col != null && !col.isEmpty())
					        {								
								TransCurrentDepositInfo currentInfo = null;
								Iterator iterator=col.iterator();				
								if(iterator.hasNext())
								{
									currentInfo = (TransCurrentDepositInfo)iterator.next();
								}
					          	if(currentInfo != null)
				            	{
					       	        if(currentInfo.getStatusID()==SETTConstant.TransactionStatus.APPROVALING || currentInfo.getStatusID()==SETTConstant.TransactionStatus.APPROVALED || currentInfo.getStatusID()==SETTConstant.TransactionStatus.CHECK || currentInfo.getStatusID()==SETTConstant.TransactionStatus.DELETED || currentInfo.getInputUserID()!=qInfo.getUserID())
									{
										strTransDetailsURL=  "/settlement/tran/current/control/c003_4.jsp?strAction=Query&strPrintAction=obPrint&strTransNo=" + strTransNo +"&OBInstr=obinstr&strSuccessPageURL=/settlement/tran/current/view/v115_4.jsp&strFailPageURL=/settlement/obinstruction/view/v001.jsp";
										url1="/settlement/tran/current/control/c003_4.jsp";
										url2="strAction=Query&strPrintAction=obPrint&strTransNo=" + strTransNo +"&OBInstr=obinstr&strSuccessPageURL=/settlement/tran/current/view/v115_4.jsp&strFailPageURL=/settlement/obinstruction/view/v001.jsp";
									}
									else
									{
										strTransDetailsURL=  "/settlement/tran/current/control/c001_4.jsp?lId="+currentInfo.getId()+"&strAction=toModify"+"&OBInstr=obinstr&strSuccessPageURL=/settlement/tran/current/view/v117_4.jsp&strFailPageURL=/settlement/obinstruction/view/v001.jsp";
										url1="/settlement/tran/current/control/c001_4.jsp";
										url2="lId="+currentInfo.getId()+"&strAction=toModify"+"&OBInstr=obinstr&strSuccessPageURL=/settlement/tran/current/view/v117_4.jsp&strFailPageURL=/settlement/obinstruction/view/v001.jsp";
									}
				       		    }
							}	
						}
					}	
					
					PagerTools.returnCellList(cellList,strTransNo+","+url1+","+url2);
					String strCheckTime = "";
					if(SignSignDate!=null){
						strCheckTime = DataFormat.formatString(DataFormat.formatDate(SignSignDate));
					}else{
						strCheckTime = DataFormat.formatString(DataFormat.formatDate(CheckDate));
					}
					PagerTools.returnCellList(cellList,strCheckTime);
					
					
					
					//�洢������
					rowInfo = new ResultPagerRowInfo();
					rowInfo.setCell(cellList);
					rowInfo.setId(String.valueOf(rs.getLong("rownum1")));
					
					//��������
					resultList.add(rowInfo);
					
				}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException(e.getMessage());
		}
		
		return resultList;
		
	}
	
	public ArrayList getOBFinanceSumAmount(ResultSet rs , Map map) throws Exception{
		
		try{
			
			ArrayList list = new ArrayList();
			QueryOBFinanceInstrInfo qInfo = (QueryOBFinanceInstrInfo)map.get("qInfo");
			
			String strAmountSum="" ; //����ܺ�
			String strBalanceSum="";//��ǰҳ����
			BigDecimal totalMoney = new BigDecimal(0);
			
			QOBFinanceInstr qobj = new QOBFinanceInstr();
			AmountCountInfo qasi = new AmountCountInfo();
			double amount = qobj.queryAmountCountInfo(qInfo);
			double  balanceSum = 0.00; //��ǰҳ����
			
			qasi.setAmountSum(amount);
			while(rs.next()){
				double dAmount = rs.getDouble("amount");
				String strAmount = DataFormat.formatDisabledAmount(dAmount,2);	
				totalMoney = totalMoney.add(new BigDecimal(strAmount.replaceAll(",","")));
				balanceSum +=dAmount;
			}
			strAmountSum = DataFormat.formatListAmount(amount);
			strBalanceSum =DataFormat.formatListAmount(balanceSum);
			long count = qobj.queryCount(qInfo);
			
			list.add("�ϼ�{"+totalMoney.doubleValue()+"}");
			list.add("���ϼ�{"+(strAmountSum.equals("")?"0.00":strAmountSum)+"}");
			list.add("���С��{"+(strBalanceSum.equals("")?"0.00":strBalanceSum)+"}");
			list.add("�����ϼ�{"+count+"}");
			
			return list;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException(e.getMessage());
		}
		
	}

	public PagerInfo queryBatchOBFinanceInfo(QueryOBFinanceInstrInfo qInfo) throws Exception{
		PagerInfo pagerInfo = null;
		String sql = null;
		try
		{
			pagerInfo = new PagerInfo();
			Map paramMap = new HashMap();
			paramMap.put("qInfo", qInfo);
			
			sql = qOBFinanceInfoDao.queryBatchOBFinanceInfoSQL(qInfo);
			
			//�õ���ѯSQL
			pagerInfo.setSqlString(sql);
			
			pagerInfo.setExtensionMothod(QOBFinanceInfoBiz.class, "batchOBFinanceResultSetHandle" , paramMap);
			
			pagerInfo.setTotalExtensionMothod(QOBFinanceInfoBiz.class, "getBatchOBFinanceSumAmount" , paramMap);
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("====>��ѯ�쳣", e);
		}
		return pagerInfo;
	
		
	}

	public ArrayList batchOBFinanceResultSetHandle(ResultSet rs , Map map) throws Exception{
		
		ArrayList resultList = new ArrayList(); //���շ��ؽ��
		ArrayList cellList = null;//��
		ResultPagerRowInfo rowInfo = null;//��
		QueryAccountResultInfo info = null;
		QueryOBFinanceInstrInfo qInfo = (QueryOBFinanceInstrInfo)map.get("qInfo");
		long officeID = -1;
		long currencyID = -1;
		long TransType = -1;
		long PayerAcctID = -1; 
	    long PayeeAcctID = -1;
		long RemitType = -1;
		double Amount = 0.0;
		Timestamp ExecuteDate = null;
		Timestamp CheckDate = null;
		String Abstract = "";
		long Source = -1;
		long DepositBillStatusID = -1;
		String TransNo = "";
		long lTransactionType = -1;
		long ID = -1;
		long Status = -1;
		long userId = -1;
		String PayeeBankName = "";
		long remitArea  	= -1; //�������
		long remitSpeed  	= -1; //����ٶ�
		double commissionAmount = 0.0;
		Timestamp dtModify = null;
		
		try{
			
			if(rs!=null)
			{
				//������������ -- ��ʼ
				List<String[]> branchList = this.getBranchShowList(qInfo);
				String branchStr = "0:��ѡ��,";
				for(int i=0 ; i<branchList.size() ; i++){
					String[] branchArr = branchList.get(i);
					String branchID = branchArr[0];
					String branchName = branchArr[1];
					branchStr += (branchID+":"+branchName+",");
				}
				branchStr = branchStr.substring(0,branchStr.length()-1);
				//������������ -- ����
				
				while(rs.next())
				{
					ID = rs.getLong("ID");
					TransType = rs.getLong("TransType");
					PayerAcctID = rs.getLong("PayerAcctID");
					PayeeAcctID = rs.getLong("PayeeAcctID");
					RemitType = rs.getLong("RemitType");
					Amount = rs.getDouble("Amount");
					ExecuteDate = rs.getTimestamp("ExecuteDate");
					CheckDate = rs.getTimestamp("CheckDate");
					Abstract = rs.getString("Note");
					Source = rs.getLong("Source");
					DepositBillStatusID = rs.getLong("DepositBillStatusID");
					TransNo = rs.getString("TransNo");
					lTransactionType = rs.getLong("DefaultTransType");
					Status = rs.getLong("Status");
					userId = rs.getLong("DealUserID");
					PayeeBankName = rs.getString("PayeeBankName");
					remitArea = rs.getLong("remitArea");
					remitSpeed = rs.getLong("remitSpeed");
					dtModify = rs.getTimestamp("dtModify");
					
					String strTransDetailsURL ="";
					String url1="";
					String url2="";
					
					long getid=0;
					String strStatus = "";
					long paraStatus = -1;
					if(DepositBillStatusID>0){
						strStatus = DataFormat.formatString(OBConstant.SettInstrStatus.getName(DepositBillStatusID));
						paraStatus = DepositBillStatusID;
					}else{
						strStatus = DataFormat.formatString(OBConstant.SettInstrStatus.getName(Status));
						paraStatus = Status;
					}
					if(strStatus.equals("�Ѹ���")||strStatus.equals("��ǩ��"))
					{
						strStatus="���ύ";
					}
					
					String strTransNo = DataFormat.formatString(TransNo);
					if(strTransNo!=null && strTransNo.length()>0)
					{
						//��ͬ�������Ͳ�ͬ
						//���ڿ���
						if(lTransactionType==SETTConstant.TransactionType.OPENFIXEDDEPOSIT)
						{
							TransFixedDepositDelegation depositDelegation = new TransFixedDepositDelegation();	
							TransFixedOpenInfo fixedOpenInfo = null;
							fixedOpenInfo = depositDelegation.openFindByTransNo(strTransNo);
							strTransDetailsURL= "/settlement/tran/fixed/control/c003.jsp?strAction=Querybatcheck&strTransNo=" + strTransNo + "&OBInstr=obinstr&strSuccessPageURL=/settlement/tran/fixed/view/v004.jsp&strFailPageURL=/settlement/obinstruction/view/v004.jsp";
							url1="/settlement/tran/fixed/control/c003.jsp";
							url2="strAction=Querybatcheck&strTransNo=" + strTransNo + "&OBInstr=obinstr&strSuccessPageURL=/settlement/tran/fixed/view/v004.jsp&strFailPageURL=/settlement/obinstruction/view/v004.jsp";
								
							
						}
						//֪ͨ����
						if(lTransactionType==SETTConstant.TransactionType.OPENNOTIFYACCOUNT)
						{
							TransFixedDepositDelegation depositDelegation = new TransFixedDepositDelegation();	
							TransFixedOpenInfo fixedOpenInfo = null;
							fixedOpenInfo = depositDelegation.openFindByTransNo(strTransNo);
							
								strTransDetailsURL= "/settlement/tran/fixed/control/c003.jsp?strAction=Querybatcheck&strTransNo=" + strTransNo + "&OBInstr=obinstr&strSuccessPageURL=/settlement/tran/fixed/view/v034.jsp&strFailPageURL=/settlement/obinstruction/view/v004.jsp";
								url1="/settlement/tran/fixed/control/c003.jsp";
								url2="strAction=Querybatcheck&strTransNo=" + strTransNo + "&OBInstr=obinstr&strSuccessPageURL=/settlement/tran/fixed/view/v034.jsp&strFailPageURL=/settlement/obinstruction/view/v004.jsp";
							
						}
						//����֧ȡ
						if(lTransactionType==SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER)
						{
							TransFixedDepositDelegation depositDelegation = new TransFixedDepositDelegation();	
							TransFixedDrawInfo fixedDrawInfo = null;
							fixedDrawInfo = depositDelegation.drawFindByTransNo(strTransNo);
							strTransDetailsURL= "/settlement/tran/fixed/control/c013.jsp?strAction=Querybatcheck&strTransNo=" + strTransNo + "&OBInstr=obinstr&strSuccessPageURL=/settlement/tran/fixed/view/v015.jsp&strFailPageURL=/settlement/obinstruction/view/v004.jsp";
							url1="/settlement/tran/fixed/control/c013.jsp";
							url2="strAction=Querybatcheck&strTransNo=" + strTransNo + "&OBInstr=obinstr&strSuccessPageURL=/settlement/tran/fixed/view/v015.jsp&strFailPageURL=/settlement/obinstruction/view/v004.jsp";
							
						}
						//֪֧ͨȡ
						if(lTransactionType==SETTConstant.TransactionType.NOTIFYDEPOSITDRAW)
						{
							TransFixedDepositDelegation depositDelegation = new TransFixedDepositDelegation();	
							TransFixedDrawInfo fixedDrawInfo = null;
							fixedDrawInfo = depositDelegation.drawFindByTransNo(strTransNo);
							strTransDetailsURL= "/settlement/tran/fixed/control/c013.jsp?strAction=Querybatcheck&strTransNo=" + strTransNo + "&OBInstr=obinstr&strSuccessPageURL=/settlement/tran/fixed/view/v045.jsp&strFailPageURL=/settlement/obinstruction/view/v004.jsp";
							url1="/settlement/tran/fixed/control/c013.jsp";
							url2="strAction=Querybatcheck&strTransNo=" + strTransNo + "&OBInstr=obinstr&strSuccessPageURL=/settlement/tran/fixed/view/v045.jsp&strFailPageURL=/settlement/obinstruction/view/v004.jsp";
							
						}	
						//��������
						if(lTransactionType==SETTConstant.TransactionType.FIXEDCONTINUETRANSFER)
						{
							TransFixedDepositDelegation depositDelegation = new TransFixedDepositDelegation();	
							TransFixedContinueInfo fixedContinueInfo = null;
							fixedContinueInfo = depositDelegation.continueFindByTransNo(strTransNo);
								strTransDetailsURL= "/settlement/tran/fixed/control/c023.jsp?strAction=Querybatcheck&strTransNo=" + strTransNo + "&OBInstr=obinstr&strSuccessPageURL=/settlement/tran/fixed/view/v025.jsp&strFailPageURL=/settlement/obinstruction/view/v004.jsp";
								url1="/settlement/tran/fixed/control/c023.jsp";
								url2="strAction=Querybatcheck&strTransNo=" + strTransNo + "&OBInstr=obinstr&strSuccessPageURL=/settlement/tran/fixed/view/v025.jsp&strFailPageURL=/settlement/obinstruction/view/v004.jsp";
							
						}
						//��Ӫ�ջ�
						if(lTransactionType==SETTConstant.TransactionType.TRUSTLOANRECEIVE)
						{
							 //��Ӫ�ջ�				
							TransLoanDelegation depositDelegation = new TransLoanDelegation();
							TransRepaymentLoanInfo transRepaymentLoanInfo = null;
							long trustLoanID = -1;
							trustLoanID = depositDelegation.repaymentGetIDByTransNo(strTransNo);
							transRepaymentLoanInfo = depositDelegation.repaymentFindDetailByID(trustLoanID);
							if(transRepaymentLoanInfo!=null)
							{
								if(transRepaymentLoanInfo.getStatusID()==SETTConstant.TransactionStatus.APPROVALING || transRepaymentLoanInfo.getStatusID()==SETTConstant.TransactionStatus.APPROVALED || transRepaymentLoanInfo.getStatusID()==SETTConstant.TransactionStatus.CHECK || transRepaymentLoanInfo.getStatusID()==SETTConstant.TransactionStatus.DELETED || transRepaymentLoanInfo.getInputUserID()!=qInfo.getUserID())
								{
									strTransDetailsURL = "/settlement/tran/loan/control/c014.jsp?strTransNo="+strTransNo+"&OBInstr=obinstr&strAction=Query&strSuccessPageURL=/settlement/tran/loan/view/v016.jsp&strFailPageURL=/settlement/obinstruction/view/v004.jsp&lID="+trustLoanID;
									url1="/settlement/tran/loan/control/c014.jsp";
									url2="strTransNo="+strTransNo+"&OBInstr=obinstr&strAction=Query&strSuccessPageURL=/settlement/tran/loan/view/v016.jsp&strFailPageURL=/settlement/obinstruction/view/v004.jsp&lID="+trustLoanID;
								}
								else
								{
									strTransDetailsURL= "/settlement/tran/loan/control/c011.jsp?lID="+trustLoanID+"&strAction=toModify&OBInstr=obinstr&strSuccessPageURL=/settlement/tran/loan/view/v019-1.jsp&strFailPageURL=/settlement/obinstruction/view/v004.jsp";
									url1="/settlement/tran/loan/control/c011.jsp";
									url2="lID="+trustLoanID+"&strAction=toModify&OBInstr=obinstr&strSuccessPageURL=/settlement/tran/loan/view/v019-1.jsp&strFailPageURL=/settlement/obinstruction/view/v004.jsp";
								}
							}
						}	
						//�����ջ�
						if(lTransactionType==SETTConstant.TransactionType.BANKGROUPLOANRECEIVE)
						{
							 //�����ջ�				
							TransLoanDelegation depositDelegation = new TransLoanDelegation();
							TransRepaymentLoanInfo transRepaymentLoanInfo = null;
							long trustLoanID = -1;
							trustLoanID = depositDelegation.repaymentGetIDByTransNo(strTransNo);
							transRepaymentLoanInfo = depositDelegation.repaymentFindDetailByID(trustLoanID);
							if(transRepaymentLoanInfo!=null)
							{
								if(transRepaymentLoanInfo.getStatusID()==SETTConstant.TransactionStatus.APPROVALING || transRepaymentLoanInfo.getStatusID()==SETTConstant.TransactionStatus.APPROVALED || transRepaymentLoanInfo.getStatusID()==SETTConstant.TransactionStatus.CHECK || transRepaymentLoanInfo.getStatusID()==SETTConstant.TransactionStatus.DELETED || transRepaymentLoanInfo.getInputUserID()!=qInfo.getUserID())
								{
									strTransDetailsURL = "/settlement/tran/loan/control/c034.jsp?strTransNo="+strTransNo+"&OBInstr=obinstr&strAction=Query&strSuccessPageURL=/settlement/tran/loan/view/v076.jsp&strFailPageURL=/settlement/obinstruction/view/v004.jsp&lID="+trustLoanID;
									url1="/settlement/tran/loan/control/c034.jsp";
									url2="strTransNo="+strTransNo+"&OBInstr=obinstr&strAction=Query&strSuccessPageURL=/settlement/tran/loan/view/v076.jsp&strFailPageURL=/settlement/obinstruction/view/v004.jsp&lID="+trustLoanID;
								}
								else
								{
									strTransDetailsURL= "/settlement/tran/loan/control/c031.jsp?lID="+trustLoanID+"&strAction=toModify&OBInstr=obinstr&strSuccessPageURL=/settlement/tran/loan/view/v079-1.jsp&strFailPageURL=/settlement/obinstruction/view/v004.jsp";
									url1="/settlement/tran/loan/control/c031.jsp";
									url2="lID="+trustLoanID+"&strAction=toModify&OBInstr=obinstr&strSuccessPageURL=/settlement/tran/loan/view/v079-1.jsp&strFailPageURL=/settlement/obinstruction/view/v004.jsp";
								}
							}
						}	
						//ί���ջ�
						if(lTransactionType==SETTConstant.TransactionType.CONSIGNLOANRECEIVE)
						{
							 //ί���ջ�				
							TransLoanDelegation depositDelegation = new TransLoanDelegation();
							TransRepaymentLoanInfo transRepaymentLoanInfo = null;
							long trustLoanID = -1;
							trustLoanID = depositDelegation.repaymentGetIDByTransNo(strTransNo);
							transRepaymentLoanInfo = depositDelegation.repaymentFindDetailByID(trustLoanID);
							if(transRepaymentLoanInfo!=null)
							{
								if(transRepaymentLoanInfo.getStatusID()==SETTConstant.TransactionStatus.APPROVALING || transRepaymentLoanInfo.getStatusID()==SETTConstant.TransactionStatus.APPROVALED || transRepaymentLoanInfo.getStatusID()==SETTConstant.TransactionStatus.CHECK || transRepaymentLoanInfo.getStatusID()==SETTConstant.TransactionStatus.DELETED || transRepaymentLoanInfo.getInputUserID()!=qInfo.getUserID())
								{
									strTransDetailsURL = "/settlement/tran/loan/control/c014.jsp?strTransNo="+strTransNo+"&OBInstr=obinstr&strAction=Query&strSuccessPageURL=/settlement/tran/loan/view/v038.jsp&strFailPageURL=/settlement/obinstruction/view/v004.jsp&lID="+trustLoanID;
									url1="/settlement/tran/loan/control/c014.jsp";
									url2="strTransNo="+strTransNo+"&OBInstr=obinstr&strAction=Query&strSuccessPageURL=/settlement/tran/loan/view/v038.jsp&strFailPageURL=/settlement/obinstruction/view/v004.jsp&lID="+trustLoanID;
								}
								else
								{
									strTransDetailsURL= "/settlement/tran/loan/control/c011.jsp?lID="+trustLoanID+"&strAction=toModify&OBInstr=obinstr&strSuccessPageURL=/settlement/tran/loan/view/v039-1.jsp&strFailPageURL=/settlement/obinstruction/view/v004.jsp";
									url1="/settlement/tran/loan/control/c011.jsp";
									url2="lID="+trustLoanID+"&strAction=toModify&OBInstr=obinstr&strSuccessPageURL=/settlement/tran/loan/view/v039-1.jsp&strFailPageURL=/settlement/obinstruction/view/v004.jsp";
								}
							}
						}
						//��Ϣ����֧��
						if(lTransactionType==SETTConstant.TransactionType.INTERESTFEEPAYMENT)
						{
							 //��Ϣ����֧��				
							TransLoanDelegation depositDelegation = new TransLoanDelegation();
							TransRepaymentLoanInfo transRepaymentLoanInfo = null;
							long trustLoanID = -1;
							trustLoanID = depositDelegation.repaymentGetIDByTransNo(strTransNo);
							transRepaymentLoanInfo = depositDelegation.repaymentFindDetailByID(trustLoanID);
							if(transRepaymentLoanInfo!=null)
							{
								if(transRepaymentLoanInfo.getStatusID()==SETTConstant.TransactionStatus.APPROVALING || transRepaymentLoanInfo.getStatusID()==SETTConstant.TransactionStatus.APPROVALED || transRepaymentLoanInfo.getStatusID()==SETTConstant.TransactionStatus.CHECK || transRepaymentLoanInfo.getStatusID()==SETTConstant.TransactionStatus.DELETED || transRepaymentLoanInfo.getInputUserID()!=qInfo.getUserID())
								{
									strTransDetailsURL = "/settlement/tran/loan/control/c044.jsp?strTransNo="+strTransNo+"&OBInstr=obinstr&strAction=Query&strSuccessPageURL=/settlement/tran/loan/view/v047.jsp&strFailPageURL=/settlement/obinstruction/view/v004.jsp&lID="+trustLoanID;
									url1="/settlement/tran/loan/control/c044.jsp";
									url2="strTransNo="+strTransNo+"&OBInstr=obinstr&strAction=Query&strSuccessPageURL=/settlement/tran/loan/view/v047.jsp&strFailPageURL=/settlement/obinstruction/view/v004.jsp&lID="+trustLoanID;
								}
								else
								{					
									strTransDetailsURL= "/settlement/tran/loan/control/c041.jsp?lID="+trustLoanID+"&strAction=" + String.valueOf(SETTConstant.Actions.TODETAIL) + "&OBInstr=obinstr&strSuccessPageURL=/settlement/tran/loan/view/v049-1.jsp&strFailPageURL=/settlement/obinstruction/view/v004.jsp";
									url1="/settlement/tran/loan/control/c041.jsp";
									url2="lID="+trustLoanID+"&strAction=" + String.valueOf(SETTConstant.Actions.TODETAIL) + "&OBInstr=obinstr&strSuccessPageURL=/settlement/tran/loan/view/v049-1.jsp&strFailPageURL=/settlement/obinstruction/view/v004.jsp";
								}
							}
						}
						//���⽻��
						if(lTransactionType==SETTConstant.TransactionType.SPECIALOPERATION)
						{
							TransSpecialOperationDelegation depositDelegation = new TransSpecialOperationDelegation();
							
							TransSpecialOperationInfo specialInfo = new TransSpecialOperationInfo();;					
							
							long lSpecialID = -1;
							lSpecialID = depositDelegation.getIDByTransNo(strTransNo);
				
							specialInfo = depositDelegation.findDetailByID(lSpecialID);
							
				          	if(specialInfo != null)
			            	{
				       	        strTransDetailsURL= "/settlement/tran/special/control/c004.jsp?strAction=Querybatcheck&strTransNo=" + strTransNo + "&OBInstr=obinstr&strSuccessPageURL=/settlement/tran/special/view/v006.jsp&strFailPageURL=/settlement/obinstruction/view/v004.jsp&lId=" + lSpecialID + "&FromQuery=yes ";
				       	        url1="/settlement/tran/special/control/c004.jsp";
								url2="strAction=Querybatcheck&strTransNo=" + strTransNo + "&OBInstr=obinstr&strSuccessPageURL=/settlement/tran/special/view/v006.jsp&strFailPageURL=/settlement/obinstruction/view/v004.jsp&lId=" + lSpecialID + "&FromQuery=yes ";
								
			       		    }
						}
						//�ڲ�ת��
						if(lTransactionType==SETTConstant.TransactionType.INTERNALVIREMENT)
						{
							TransCurrentDepositDelegation depositDelegation = new TransCurrentDepositDelegation();	
							TransCurrentDepositInfo currInfo = new TransCurrentDepositInfo();;
							currInfo.setTransNo(strTransNo);
							TransCurrentDepositAssembler data = new TransCurrentDepositAssembler(currInfo);
							Collection col=null;
							col = depositDelegation.findByConditions(data,-1,false);
							
							if(col != null && !col.isEmpty())
					        {								
								TransCurrentDepositInfo currentInfo = null;
								Iterator iterator=col.iterator();				
								if(iterator.hasNext())
								{
									currentInfo = (TransCurrentDepositInfo)iterator.next();
								}
					          	if(currentInfo != null)
				            	{strTransDetailsURL= "/settlement/tran/current/control/c003.jsp?strAction=Querybatcheck&strTransNo=" + strTransNo + "&OBInstr=obinstr&strSuccessPageURL=/settlement/tran/current/view/v015.jsp&strFailPageURL=/settlement/obinstruction/view/v004.jsp";
				            	url1="/settlement/tran/current/control/c003.jsp";
								url2="strAction=Querybatcheck&strTransNo=" + strTransNo + "&OBInstr=obinstr&strSuccessPageURL=/settlement/tran/current/view/v015.jsp&strFailPageURL=/settlement/obinstruction/view/v004.jsp";
									}
							}
						}
						//������λ���и���
						if(lTransactionType==SETTConstant.TransactionType.SUBCLIENT_BANKPAY||lTransactionType==SETTConstant.TransactionType.BANKPAY_DOWNTRANSFER)
						{
							TransCurrentDepositDelegation depositDelegation = new TransCurrentDepositDelegation();	
							TransCurrentDepositInfo currInfo = new TransCurrentDepositInfo();;
							currInfo.setTransNo(strTransNo);
							TransCurrentDepositAssembler data = new TransCurrentDepositAssembler(currInfo);
							Collection col=null;
							col = depositDelegation.findByConditions(data,-1,false);
							
							if(col != null && !col.isEmpty())
					        {								
								TransCurrentDepositInfo currentInfo = null;
								Iterator iterator=col.iterator();				
								if(iterator.hasNext())
								{
									currentInfo = (TransCurrentDepositInfo)iterator.next();
								}
					          	if(currentInfo != null)
				            	{
					            	strTransDetailsURL= "/settlement/tran/current/control/c003.jsp?strAction=Querybatcheck&strTransNo=" + strTransNo + "&OBInstr=obinstr&strSuccessPageURL=/settlement/tran/current/view/v215.jsp&strFailPageURL=/settlement/obinstruction/view/v004.jsp";
					            	url1="/settlement/tran/current/control/c003.jsp";
									url2="strAction=Querybatcheck&strTransNo=" + strTransNo + "&OBInstr=obinstr&strSuccessPageURL=/settlement/tran/current/view/v215.jsp&strFailPageURL=/settlement/obinstruction/view/v004.jsp";
				            	}
							}
						}				
						//���и���
						if(lTransactionType==SETTConstant.TransactionType.BANKPAY||lTransactionType==SETTConstant.TransactionType.BANKPAY_DOWNTRANSFER)
						{
							//�õ������ѽ��
							commissionAmount = NameRef.getCommissionAmountByTransNo(strTransNo);
							
							String strBranchName = null;	
							long lBankID = -1;
							
							OBFinanceInstrBiz financeInstrBiz = new OBFinanceInstrBiz();
							BranchbankInfo branchbankInfo = new BranchbankInfo();
					        BranchbankInfo queryInfo = new BranchbankInfo();
					        
							String bankName = rs.getString("bankname");
							
							if(!(bankName==null||"".equals(bankName)))
							{
								queryInfo.setBranchName(bankName);
								queryInfo.setOfficeID(qInfo.getOfficeID());
								branchbankInfo = financeInstrBiz.findBranchBankByBankName(queryInfo);
							}
							if(branchbankInfo!=null)
							{
							    lBankID = branchbankInfo.getBranchID();
							    strBranchName = branchbankInfo.getBranchName();
							}
							
							TransCurrentDepositDelegation depositDelegation = new TransCurrentDepositDelegation();	
							TransCurrentDepositInfo currInfo = new TransCurrentDepositInfo();;
							currInfo.setTransNo(strTransNo);
							lBankID = NameRef.getBankIDByTransNo(strTransNo);
							if(lBankID > 0)
							{
								currInfo.setBankID(lBankID);
							}           		
							strBranchName = DataFormat.formatString(NameRef.getBankNameByID(currInfo.getBankID() ))	;		
							TransCurrentDepositAssembler data = new TransCurrentDepositAssembler(currInfo);
							Collection col=null;
							col = depositDelegation.findByConditions(data,-1,false);
							
							if(col != null && !col.isEmpty())
					        {								
								TransCurrentDepositInfo currentInfo = null;
								Iterator iterator=col.iterator();				
								if(iterator.hasNext())
								{
									currentInfo = (TransCurrentDepositInfo)iterator.next();
								}
					          	if(currentInfo != null)
				            	{
						       	    strTransDetailsURL= "/settlement/tran/current/control/c003.jsp?strAction=Querybatcheck&strTransNo=" + strTransNo + "&OBInstr=obinstr&strSuccessPageURL=/settlement/tran/current/view/v115.jsp&strFailPageURL=/settlement/obinstruction/view/v007.jsp";
						       	    url1="/settlement/tran/current/control/c003.jsp";
									url2="strAction=Querybatcheck&strTransNo=" + strTransNo + "&OBInstr=obinstr&strSuccessPageURL=/settlement/tran/current/view/v115.jsp&strFailPageURL=/settlement/obinstruction/view/v007.jsp";
									
				       		    }
					          	getid=currentInfo.getId(); // ͨ��currentInfo��ý��׺Ŷ�Ӧ��ID
							}
						}
						
						//���и����˾����
						if(lTransactionType==SETTConstant.TransactionType.BANKPAY_FINCOMPANYPAY)
						{
							TransCurrentDepositDelegation depositDelegation = new TransCurrentDepositDelegation();	
							TransCurrentDepositInfo currInfo = new TransCurrentDepositInfo();;
							currInfo.setTransNo(strTransNo);
							TransCurrentDepositAssembler data = new TransCurrentDepositAssembler(currInfo);
							Collection col=null;
							col = depositDelegation.findByConditions(data,-1,false);
							
							if(col != null && !col.isEmpty())
					        {								
								TransCurrentDepositInfo currentInfo = null;
								Iterator iterator=col.iterator();				
								if(iterator.hasNext())
								{
									currentInfo = (TransCurrentDepositInfo)iterator.next();
								}
					          	if(currentInfo != null)
				            	{
					       	    	strTransDetailsURL= "/settlement/tran/current/control/c003_3.jsp?strAction=Querybatcheck&strTransNo=" + strTransNo + "&OBInstr=obinstr&strSuccessPageURL=/settlement/tran/current/view/v115_3.jsp&strFailPageURL=/settlement/obinstruction/view/v004.jsp";
					       	    	url1="/settlement/tran/current/control/c003_3.jsp";
									url2="strAction=Querybatcheck&strTransNo=" + strTransNo + "&OBInstr=obinstr&strSuccessPageURL=/settlement/tran/current/view/v115_3.jsp&strFailPageURL=/settlement/obinstruction/view/v004.jsp";
									
				       		    }
							}	
						}
						
						//���и���-�����˻�
						if(lTransactionType==SETTConstant.TransactionType.BANKPAY_PAYSUBACCOUNT)
						{
							TransCurrentDepositDelegation depositDelegation = new TransCurrentDepositDelegation();	
							TransCurrentDepositInfo currInfo = new TransCurrentDepositInfo();;
							currInfo.setTransNo(strTransNo);
							TransCurrentDepositAssembler data = new TransCurrentDepositAssembler(currInfo);
							Collection col=null;
							col = depositDelegation.findByConditions(data,-1,false);
							
							if(col != null && !col.isEmpty())
					        {								
								TransCurrentDepositInfo currentInfo = null;
								Iterator iterator=col.iterator();				
								if(iterator.hasNext())
								{
									currentInfo = (TransCurrentDepositInfo)iterator.next();
								}
					          	if(currentInfo != null)
				            	{
					       	    	strTransDetailsURL= "/settlement/tran/current/control/c003_4.jsp?strAction=Querybatcheck&strTransNo=" + strTransNo + "&OBInstr=obinstr&strSuccessPageURL=/settlement/tran/current/view/v115_4.jsp&strFailPageURL=/settlement/obinstruction/view/v004.jsp";
					       	    	url1="/settlement/tran/current/control/c003_4.jsp";
									url2="strAction=Querybatcheck&strTransNo=" + strTransNo + "&OBInstr=obinstr&strSuccessPageURL=/settlement/tran/current/view/v115_4.jsp&strFailPageURL=/settlement/obinstruction/view/v004.jsp";
									
				       		    }
							}	
						}
					}
					
					//�洢������
					cellList = new ArrayList();
					//ID## ##RemitType##
					PagerTools.returnCellList(cellList,String.valueOf(ID)+"##"+" "+"##"+RemitType+"##"+paraStatus+"##"+PayerAcctID+"##"+Amount+"##"+TransNo+"##"+dtModify+"##"+commissionAmount);
					PagerTools.returnCellList(cellList,String.valueOf(ID)+","+ID+","+Status+","+userId+","+NameRef.getUserNameByID(userId));
					boolean isHTKG = Config.getBoolean(ConfigConstant.SETTLEMENT_ISHTKG, false); 
					String strInstructionType = "";
					if(isHTKG)
					{
						if(TransType==SETTConstant.SettInstrType.TRANSFER_BANKEXTERNAL||TransType==SETTConstant.SettInstrType.BOOKING_BANKEXTERNAL)
						{
							strInstructionType =  DataFormat.formatString(OBConstant.SettInstrType.getName(SETTConstant.SettInstrType.CAPTRANSFER_BANKPAY));
						}
						else if(TransType==SETTConstant.SettInstrType.TRANSFER_INTERNALVIREMENT||TransType==SETTConstant.SettInstrType.SCM_TRANSFER_INTERNALVIREMENT||TransType==SETTConstant.SettInstrType.BOOKING_INTERNALVIREMENT||TransType==SETTConstant.SettInstrType.SCM_BOOKING_INTERNALVIREMENT)
						{
							strInstructionType =  DataFormat.formatString(OBConstant.SettInstrType.getName(SETTConstant.SettInstrType.CAPTRANSFER_INTERNALVIREMENT));
						}else{
						    strInstructionType =  DataFormat.formatString(OBConstant.SettInstrType.getName(TransType));
						}
					}
					else
					{
						strInstructionType =  DataFormat.formatString(OBConstant.SettInstrType.getName(TransType));		
					}
					PagerTools.returnCellList(cellList,strInstructionType);
					PagerTools.returnCellList(cellList,DataFormat.formatString(NameRef.getAccountNoByID(PayerAcctID)));
					PagerTools.returnCellList(cellList,DataFormat.formatString(NameRef.getClientNameByAccountID(PayerAcctID)));
					PayerOrPayeeInfo payeeInfo = new PayerOrPayeeInfo();
					OBFinanceInstrDao obFinanceInstrDao = new OBFinanceInstrDao();
					payeeInfo = obFinanceInstrDao.getPayerOrPayeeInfoByID(PayeeAcctID,RemitType,OBConstant.PayerOrPayee.PAYEE);
					PagerTools.returnCellList(cellList,payeeInfo.getAccountNo());
					PagerTools.returnCellList(cellList,payeeInfo.getAccountName());
					if(RemitType==OBConstant.SettRemitType.BANKPAY)
					{
						PayeeBankName = rs.getString("PayeeBankName");
					}
					if(RemitType==OBConstant.SettRemitType.INTERNALVIREMENT)
					{
						PagerTools.returnCellList(cellList,"&nbsp;");//������
						PagerTools.returnCellList(cellList,"-1");//������
					}
					else
					{
						PagerTools.returnCellList(cellList,PayeeBankName);//������
						PagerTools.returnCellList(cellList,branchStr);//������
					}
					PagerTools.returnCellList(cellList,DataFormat.formatDisabledAmount(Amount,2));//���
					
					if(Config.getBoolean(ConfigConstant.SETT_TRANSCOMMISSION,false)){
						PagerTools.returnCellList(cellList,remitArea);//�������
						PagerTools.returnCellList(cellList,remitSpeed);//����ٶ�
						
						CommissionSettingBiz biz = new CommissionSettingBiz();
						CommissionSettingInfo tempinfo = new CommissionSettingInfo();
						tempinfo.setOfficeID(qInfo.getOfficeID());
						tempinfo.setCurrencyID(qInfo.getCurrencyID());
						tempinfo.setStatusId(Constant.RecordStatus.VALID);
						Collection coll = biz.findAll(tempinfo);
						//�����Ѽ���
						double amount = Amount;
						long commissionType = remitArea;
						long isUrgent = remitSpeed;
						Iterator it = null;
						if(coll != null)
						{
							it = coll.iterator();
							if(amount > 0)
							{
								if( it != null && it.hasNext() )
								{
									while( it.hasNext() )
									{
										CommissionSettingInfo commInfo = (CommissionSettingInfo)it.next();
										if(commInfo.getCommissionType() == commissionType && commInfo.getIsUrgent() == isUrgent)
										{
											if(amount > commInfo.getAmountFrom() && amount <= commInfo.getAmountTo())
											{
												commissionAmount = amount*commInfo.getCommissionRate()/10000.00+commInfo.getCommissionAmount();
											}
										}
									}
								}
							}
						}
						PagerTools.returnCellList(cellList,DataFormat.formatDisabledAmount(commissionAmount));//������
					}
					PagerTools.returnCellList(cellList,DataFormat.formatString(DataFormat.formatDate(ExecuteDate)));//ִ������
					PagerTools.returnCellList(cellList,NameRef.getSourceNameByID(Source));//������Դ
					PagerTools.returnCellList(cellList,strStatus);//״̬
					PagerTools.returnCellList(cellList,strTransNo+","+url1+","+url2);//���׺�
					PagerTools.returnCellList(cellList,"");//�ܾ�����
					
					
					//�洢������
					rowInfo = new ResultPagerRowInfo();
					rowInfo.setCell(cellList);
					rowInfo.setId(String.valueOf(rs.getLong("rownum1")));
					
					//��������
					resultList.add(rowInfo);
					
				}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException(e.getMessage());
		}
		
		return resultList;
		
	}
	
	public ArrayList getBatchOBFinanceSumAmount(ResultSet rs , Map map) throws Exception{
		
		try{
			
			ArrayList list = new ArrayList();
			
			QueryOBFinanceInstrInfo qInfo = (QueryOBFinanceInstrInfo)map.get("qInfo");
			
			QOBFinanceInstr qobj = new QOBFinanceInstr();
			QueryOBFinanceSumInfo qsum = null;
			qsum = qobj.querySumOBFinanceInstrInfo(qInfo);
			double  pageSum = 0.00; //��ǰҳ����
			double Amount = 0.0;
			while(rs.next()){
				Amount = rs.getDouble("Amount");
				pageSum+=Amount;
			}
			
			list.add("����ܼ�{"+DataFormat.formatDisabledAmount(qsum.getAllFinanceSumInfo())+"}");
			list.add("��ҳ���ϼ�{"+DataFormat.formatDisabledAmount(pageSum)+"}");
			
			return list;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException(e.getMessage());
		}
		
	
		
	}
	

	public PagerInfo queryBatchOBFinanceInfoCheck(QueryOBFinanceInstrInfo qInfo) throws Exception{
		PagerInfo pagerInfo = null;
		String sql = null;
		try
		{
			pagerInfo = new PagerInfo();
			Map paramMap = new HashMap();
			paramMap.put("qInfo", qInfo);
			
			sql = qOBFinanceInfoDao.queryBatchOBFinanceInfoCheckSQL(qInfo);
			
			//�õ���ѯSQL
			pagerInfo.setSqlString(sql);
			
			pagerInfo.setExtensionMothod(QOBFinanceInfoBiz.class, "batchOBFinanceCheckResultSetHandle" , paramMap);
			
			pagerInfo.setTotalExtensionMothod(QOBFinanceInfoBiz.class, "getBatchOBFinanceCheckSumAmount" , paramMap);
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("====>��ѯ�쳣", e);
		}
		return pagerInfo;
	
		
	}

	
	public ArrayList batchOBFinanceCheckResultSetHandle(ResultSet rs , Map map) throws Exception{
		
		ArrayList resultList = new ArrayList(); //���շ��ؽ��
		ArrayList cellList = null;//��
		ResultPagerRowInfo rowInfo = null;//��
		QueryAccountResultInfo info = null;
		QueryOBFinanceInstrInfo qInfo = (QueryOBFinanceInstrInfo)map.get("qInfo");
		long officeID = -1;
		long currencyID = -1;
		long TransType = -1;
		long PayerAcctID = -1; 
	    long PayeeAcctID = -1;
		long RemitType = -1;
		double Amount = 0.0;
		Timestamp ExecuteDate = null;
		Timestamp CheckDate = null;
		String Abstract = "";
		long Source = -1;
		long DepositBillStatusID = -1;
		String TransNo = "";
		long lTransactionType = -1;
		long ID = -1;
		long Status = -1;
		long userId = -1;
		String strBranchName = "";
		
		ResultSet rs_1 = null;
		try{
			
			if(rs!=null)
			{
				while(rs.next())
				{
					ID = rs.getLong("ID");
					TransType = rs.getLong("TransType");
					PayerAcctID = rs.getLong("PayerAcctID");
					PayeeAcctID = rs.getLong("PayeeAcctID");
					RemitType = rs.getLong("RemitType");
					Amount = rs.getDouble("Amount");
					ExecuteDate = rs.getTimestamp("ExecuteDate");
					CheckDate = rs.getTimestamp("CheckDate");
					Abstract = rs.getString("Note");
					Source = rs.getLong("Source");
					DepositBillStatusID = rs.getLong("DepositBillStatusID");
					TransNo = rs.getString("TransNo");
					lTransactionType = rs.getLong("DefaultTransType");
					Status = rs.getLong("Status");
					userId = rs.getLong("DealUserID");
					long getid=0;
					TransCurrentDepositInfo currInfo = new TransCurrentDepositInfo();
					//�洢������
					cellList = new ArrayList();
					
					PagerTools.returnCellList(cellList,String.valueOf(ID)+"##"+TransNo+"##"+Status);
					PagerTools.returnCellList(cellList,String.valueOf(ID));
					boolean isHTKG = Config.getBoolean(ConfigConstant.SETTLEMENT_ISHTKG, false); 
					String strInstructionType = "";
					if(isHTKG)
					{
						if(TransType==SETTConstant.SettInstrType.TRANSFER_BANKEXTERNAL||TransType==SETTConstant.SettInstrType.BOOKING_BANKEXTERNAL)
						{
							strInstructionType =  DataFormat.formatString(OBConstant.SettInstrType.getName(SETTConstant.SettInstrType.CAPTRANSFER_BANKPAY));
						}
						else if(TransType==SETTConstant.SettInstrType.TRANSFER_INTERNALVIREMENT||TransType==SETTConstant.SettInstrType.SCM_TRANSFER_INTERNALVIREMENT||TransType==SETTConstant.SettInstrType.BOOKING_INTERNALVIREMENT||TransType==SETTConstant.SettInstrType.SCM_BOOKING_INTERNALVIREMENT)
						{
							strInstructionType =  DataFormat.formatString(OBConstant.SettInstrType.getName(SETTConstant.SettInstrType.CAPTRANSFER_INTERNALVIREMENT));
						}else{
						    strInstructionType =  DataFormat.formatString(OBConstant.SettInstrType.getName(TransType));
						}
					}
					else
					{
						strInstructionType =  DataFormat.formatString(OBConstant.SettInstrType.getName(TransType));		
					}
					PagerTools.returnCellList(cellList,strInstructionType);
					PagerTools.returnCellList(cellList,DataFormat.formatString(NameRef.getAccountNoByID(PayerAcctID)));
					PagerTools.returnCellList(cellList,DataFormat.formatString(NameRef.getClientNameByAccountID(PayerAcctID)));
					PayerOrPayeeInfo payeeInfo = new PayerOrPayeeInfo();
					OBFinanceInstrDao obFinanceInstrDao = new OBFinanceInstrDao();
					payeeInfo = obFinanceInstrDao.getPayerOrPayeeInfoByID(PayeeAcctID,RemitType,OBConstant.PayerOrPayee.PAYEE);
					PagerTools.returnCellList(cellList,payeeInfo.getAccountNo());
					PagerTools.returnCellList(cellList,payeeInfo.getAccountName());
					
					String strTransDetailsURL ="";
					String url1="";
					String url2="";
					String strTransNo = DataFormat.formatString(TransNo);
					if(strTransNo!=null && strTransNo.length()>0)
					{
						//��ͬ�������Ͳ�ͬ
						//���ڿ���
						if(lTransactionType==SETTConstant.TransactionType.OPENFIXEDDEPOSIT)
						{
							TransFixedDepositDelegation depositDelegation = new TransFixedDepositDelegation();	
							TransFixedOpenInfo fixedOpenInfo = null;
							fixedOpenInfo = depositDelegation.openFindByTransNo(strTransNo);
							
								strTransDetailsURL= "/settlement/tran/fixed/control/c003.jsp?strAction=Querybatcheck&strTransNo=" + strTransNo + "&OBInstr=obinstr&strSuccessPageURL=/settlement/tran/fixed/view/v004.jsp&strFailPageURL=/settlement/obinstruction/view/v007.jsp";
								url1="/settlement/tran/fixed/control/c003.jsp";
								url2="strAction=Querybatcheck&strTransNo=" + strTransNo + "&OBInstr=obinstr&strSuccessPageURL=/settlement/tran/fixed/view/v004.jsp&strFailPageURL=/settlement/obinstruction/view/v007.jsp";
							
						}
						//֪ͨ����
						if(lTransactionType==SETTConstant.TransactionType.OPENNOTIFYACCOUNT)
						{
							TransFixedDepositDelegation depositDelegation = new TransFixedDepositDelegation();	
							TransFixedOpenInfo fixedOpenInfo = null;
							fixedOpenInfo = depositDelegation.openFindByTransNo(strTransNo);
							
								strTransDetailsURL= "/settlement/tran/fixed/control/c003.jsp?strAction=Querybatcheck&strTransNo=" + strTransNo + "&OBInstr=obinstr&strSuccessPageURL=/settlement/tran/fixed/view/v034.jsp&strFailPageURL=/settlement/obinstruction/view/v007.jsp";
								url1="/settlement/tran/fixed/control/c003.jsp";
								url2="strAction=Querybatcheck&strTransNo=" + strTransNo + "&OBInstr=obinstr&strSuccessPageURL=/settlement/tran/fixed/view/v034.jsp&strFailPageURL=/settlement/obinstruction/view/v007.jsp";
							
						}
						//����֧ȡ
						if(lTransactionType==SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER)
						{
							TransFixedDepositDelegation depositDelegation = new TransFixedDepositDelegation();	
							TransFixedDrawInfo fixedDrawInfo = null;
							fixedDrawInfo = depositDelegation.drawFindByTransNo(strTransNo);
							
								strTransDetailsURL= "/settlement/tran/fixed/control/c013.jsp?strAction=Querybatcheck&strTransNo=" + strTransNo + "&OBInstr=obinstr&strSuccessPageURL=/settlement/tran/fixed/view/v015.jsp&strFailPageURL=/settlement/obinstruction/view/v007.jsp";
								url1="/settlement/tran/fixed/control/c013.jsp";
								url2="strAction=Querybatcheck&strTransNo=" + strTransNo + "&OBInstr=obinstr&strSuccessPageURL=/settlement/tran/fixed/view/v015.jsp&strFailPageURL=/settlement/obinstruction/view/v007.jsp";
							
						}
						//֪֧ͨȡ
						if(lTransactionType==SETTConstant.TransactionType.NOTIFYDEPOSITDRAW)
						{
							TransFixedDepositDelegation depositDelegation = new TransFixedDepositDelegation();	
							TransFixedDrawInfo fixedDrawInfo = null;
							fixedDrawInfo = depositDelegation.drawFindByTransNo(strTransNo);
							
								strTransDetailsURL= "/settlement/tran/fixed/control/c013.jsp?strAction=Querybatcheck&strTransNo=" + strTransNo + "&OBInstr=obinstr&strSuccessPageURL=/settlement/tran/fixed/view/v045.jsp&strFailPageURL=/settlement/obinstruction/view/v007.jsp";
								url1="/settlement/tran/fixed/control/c013.jsp";
								url2="strAction=Querybatcheck&strTransNo=" + strTransNo + "&OBInstr=obinstr&strSuccessPageURL=/settlement/tran/fixed/view/v045.jsp&strFailPageURL=/settlement/obinstruction/view/v007.jsp";
							
						}	
						//��������
						if(lTransactionType==SETTConstant.TransactionType.FIXEDCONTINUETRANSFER)
						{
							TransFixedDepositDelegation depositDelegation = new TransFixedDepositDelegation();	
							TransFixedContinueInfo fixedContinueInfo = null;
							fixedContinueInfo = depositDelegation.continueFindByTransNo(strTransNo);
							
								strTransDetailsURL= "/settlement/tran/fixed/control/c023.jsp?strAction=Querybatcheck&strTransNo=" + strTransNo + "&OBInstr=obinstr&strSuccessPageURL=/settlement/tran/fixed/view/v025.jsp&strFailPageURL=/settlement/obinstruction/view/v007.jsp";
								url1="/settlement/tran/fixed/control/c023.jsp";
								url2="strAction=Querybatcheck&strTransNo=" + strTransNo + "&OBInstr=obinstr&strSuccessPageURL=/settlement/tran/fixed/view/v025.jsp&strFailPageURL=/settlement/obinstruction/view/v007.jsp";
							
						}
						//��Ӫ�ջ� 
						if(lTransactionType==SETTConstant.TransactionType.TRUSTLOANRECEIVE)
						{
							 //��Ӫ�ջ�				
							TransLoanDelegation depositDelegation = new TransLoanDelegation();
							TransRepaymentLoanInfo transRepaymentLoanInfo = null;
							long trustLoanID = -1;
							trustLoanID = depositDelegation.repaymentGetIDByTransNo(strTransNo);
							transRepaymentLoanInfo = depositDelegation.repaymentFindDetailByID(trustLoanID);
							if(transRepaymentLoanInfo!=null)
							{
								if(transRepaymentLoanInfo.getStatusID()==SETTConstant.TransactionStatus.APPROVALING || transRepaymentLoanInfo.getStatusID()==SETTConstant.TransactionStatus.APPROVALED || transRepaymentLoanInfo.getStatusID()==SETTConstant.TransactionStatus.CHECK || transRepaymentLoanInfo.getStatusID()==SETTConstant.TransactionStatus.DELETED || transRepaymentLoanInfo.getInputUserID()!=qInfo.getUserID())
								{
									strTransDetailsURL = "/settlement/tran/loan/control/c014.jsp?strTransNo="+strTransNo+"&OBInstr=obinstr&strAction=Query&strSuccessPageURL=/settlement/tran/loan/view/v016.jsp&strFailPageURL==/settlement/obinstruction/view/v007.jsp&lID="+trustLoanID;
									url1="/settlement/tran/loan/control/c014.jsp";
									url2="strTransNo="+strTransNo+"&OBInstr=obinstr&strAction=Query&strSuccessPageURL=/settlement/tran/loan/view/v016.jsp&strFailPageURL==/settlement/obinstruction/view/v007.jsp&lID="+trustLoanID;
								}
								else
								{
									strTransDetailsURL= "/settlement/tran/loan/control/c011.jsp?lID="+trustLoanID+"&strAction=toModify&OBInstr=obinstr&strSuccessPageURL=/settlement/tran/loan/view/v019-1.jsp&strFailPageURL=/settlement/obinstruction/view/v007.jsp";
									url1="/settlement/tran/loan/control/c011.jsp";
									url2="lID="+trustLoanID+"&strAction=toModify&OBInstr=obinstr&strSuccessPageURL=/settlement/tran/loan/view/v019-1.jsp&strFailPageURL=/settlement/obinstruction/view/v007.jsp";
								}
							}
						}	
						//�����ջ�
						if(lTransactionType==SETTConstant.TransactionType.BANKGROUPLOANRECEIVE)
						{
							 //�����ջ�				
							TransLoanDelegation depositDelegation = new TransLoanDelegation();
							TransRepaymentLoanInfo transRepaymentLoanInfo = null;
							long trustLoanID = -1;
							trustLoanID = depositDelegation.repaymentGetIDByTransNo(strTransNo);
							transRepaymentLoanInfo = depositDelegation.repaymentFindDetailByID(trustLoanID);
							if(transRepaymentLoanInfo!=null)
							{
								if(transRepaymentLoanInfo.getStatusID()==SETTConstant.TransactionStatus.APPROVALING || transRepaymentLoanInfo.getStatusID()==SETTConstant.TransactionStatus.APPROVALED || transRepaymentLoanInfo.getStatusID()==SETTConstant.TransactionStatus.CHECK || transRepaymentLoanInfo.getStatusID()==SETTConstant.TransactionStatus.DELETED || transRepaymentLoanInfo.getInputUserID()!=qInfo.getUserID())
								{
									strTransDetailsURL = "/settlement/tran/loan/control/c034.jsp?strTransNo="+strTransNo+"&OBInstr=obinstr&strAction=Query&strSuccessPageURL=/settlement/tran/loan/view/v076.jsp&strFailPageURL==/settlement/obinstruction/view/v007.jsp&lID="+trustLoanID;
									url1="/settlement/tran/loan/control/c034.jsp";
									url2="strTransNo="+strTransNo+"&OBInstr=obinstr&strAction=Query&strSuccessPageURL=/settlement/tran/loan/view/v076.jsp&strFailPageURL==/settlement/obinstruction/view/v007.jsp&lID="+trustLoanID;
								}
								else
								{
									strTransDetailsURL= "/settlement/tran/loan/control/c031.jsp?lID="+trustLoanID+"&strAction=toModify&OBInstr=obinstr&strSuccessPageURL=/settlement/tran/loan/view/v079-1.jsp&strFailPageURL=/settlement/obinstruction/view/v007.jsp";
									url1="/settlement/tran/loan/control/c031.jsp";
									url2="lID="+trustLoanID+"&strAction=toModify&OBInstr=obinstr&strSuccessPageURL=/settlement/tran/loan/view/v079-1.jsp&strFailPageURL=/settlement/obinstruction/view/v007.jsp";
								}
							}
						}	
						//ί���ջ�
						if(lTransactionType==SETTConstant.TransactionType.CONSIGNLOANRECEIVE)
						{
							 //ί���ջ�				
							TransLoanDelegation depositDelegation = new TransLoanDelegation();
							TransRepaymentLoanInfo transRepaymentLoanInfo = null;
							long trustLoanID = -1;
							trustLoanID = depositDelegation.repaymentGetIDByTransNo(strTransNo);
							transRepaymentLoanInfo = depositDelegation.repaymentFindDetailByID(trustLoanID);
							if(transRepaymentLoanInfo!=null)
							{
								if(transRepaymentLoanInfo.getStatusID()==SETTConstant.TransactionStatus.APPROVALING || transRepaymentLoanInfo.getStatusID()==SETTConstant.TransactionStatus.APPROVALED || transRepaymentLoanInfo.getStatusID()==SETTConstant.TransactionStatus.CHECK || transRepaymentLoanInfo.getStatusID()==SETTConstant.TransactionStatus.DELETED || transRepaymentLoanInfo.getInputUserID()!=qInfo.getUserID())
								{
									strTransDetailsURL = "/settlement/tran/loan/control/c014.jsp?strTransNo="+strTransNo+"&OBInstr=obinstr&strAction=Query&strSuccessPageURL=/settlement/tran/loan/view/v038.jsp&strFailPageURL==/settlement/obinstruction/view/v007.jsp&lID="+trustLoanID;
									url1="/settlement/tran/loan/control/c014.jsp";
									url2="strTransNo="+strTransNo+"&OBInstr=obinstr&strAction=Query&strSuccessPageURL=/settlement/tran/loan/view/v038.jsp&strFailPageURL==/settlement/obinstruction/view/v007.jsp&lID="+trustLoanID;
								}
								else
								{
									strTransDetailsURL= "/settlement/tran/loan/control/c011.jsp?lID="+trustLoanID+"&strAction=toModify&OBInstr=obinstr&strSuccessPageURL=/settlement/tran/loan/view/v039-1.jsp&strFailPageURL=/settlement/obinstruction/view/v007.jsp";
									url1="/settlement/tran/loan/control/c011.jsp";
									url2="lID="+trustLoanID+"&strAction=toModify&OBInstr=obinstr&strSuccessPageURL=/settlement/tran/loan/view/v039-1.jsp&strFailPageURL=/settlement/obinstruction/view/v007.jsp";
								}
							}
						}
						//��Ϣ����֧��
						if(lTransactionType==SETTConstant.TransactionType.INTERESTFEEPAYMENT)
						{
							 //��Ϣ����֧��				
							TransLoanDelegation depositDelegation = new TransLoanDelegation();
							TransRepaymentLoanInfo transRepaymentLoanInfo = null;
							long trustLoanID = -1;
							trustLoanID = depositDelegation.repaymentGetIDByTransNo(strTransNo);
							transRepaymentLoanInfo = depositDelegation.repaymentFindDetailByID(trustLoanID);
							if(transRepaymentLoanInfo!=null)
							{
								if(transRepaymentLoanInfo.getStatusID()==SETTConstant.TransactionStatus.APPROVALING || transRepaymentLoanInfo.getStatusID()==SETTConstant.TransactionStatus.APPROVALED || transRepaymentLoanInfo.getStatusID()==SETTConstant.TransactionStatus.CHECK || transRepaymentLoanInfo.getStatusID()==SETTConstant.TransactionStatus.DELETED || transRepaymentLoanInfo.getInputUserID()!=qInfo.getUserID())
								{
									strTransDetailsURL = "/settlement/tran/loan/control/c044.jsp?strTransNo="+strTransNo+"&OBInstr=obinstr&strAction=Query&strSuccessPageURL=/settlement/tran/loan/view/v047.jsp&strFailPageURL==/settlement/obinstruction/view/v007.jsp&lID="+trustLoanID;
									url1="/settlement/tran/loan/control/c044.jsp";
									url2="strTransNo="+strTransNo+"&OBInstr=obinstr&strAction=Query&strSuccessPageURL=/settlement/tran/loan/view/v047.jsp&strFailPageURL==/settlement/obinstruction/view/v007.jsp&lID="+trustLoanID;
								}
								else
								{					
									strTransDetailsURL= "/settlement/tran/loan/control/c041.jsp?lID="+trustLoanID+"&strAction=" + String.valueOf(SETTConstant.Actions.TODETAIL) + "&OBInstr=obinstr&strSuccessPageURL=/settlement/tran/loan/view/v049-1.jsp&strFailPageURL=/settlement/obinstruction/view/v007.jsp";
									url1="/settlement/tran/loan/control/c041.jsp";
									url2="lID="+trustLoanID+"&strAction=" + String.valueOf(SETTConstant.Actions.TODETAIL) + "&OBInstr=obinstr&strSuccessPageURL=/settlement/tran/loan/view/v049-1.jsp&strFailPageURL=/settlement/obinstruction/view/v007.jsp";
								}
							}
						}
						//���⽻��
						if(lTransactionType==SETTConstant.TransactionType.SPECIALOPERATION)
						{
							TransSpecialOperationDelegation depositDelegation = new TransSpecialOperationDelegation();
							
							TransSpecialOperationInfo specialInfo = new TransSpecialOperationInfo();;					
							
							long lSpecialID = -1;
							lSpecialID = depositDelegation.getIDByTransNo(strTransNo);
				
							specialInfo = depositDelegation.findDetailByID(lSpecialID);
							
				          	if(specialInfo != null)
			            	{
				       	       
									strTransDetailsURL= "/settlement/tran/special/control/c004.jsp?strAction=Querybatcheck&strTransNo=" + strTransNo + "&OBInstr=obinstr&strSuccessPageURL=/settlement/tran/special/view/v006.jsp&strFailPageURL=/settlement/obinstruction/view/v007.jsp&lId=" + lSpecialID + "&FromQuery=yes ";
									url1="/settlement/tran/special/control/c004.jsp";
									url2="strAction=Querybatcheck&strTransNo=" + strTransNo + "&OBInstr=obinstr&strSuccessPageURL=/settlement/tran/special/view/v006.jsp&strFailPageURL=/settlement/obinstruction/view/v007.jsp&lId=" + lSpecialID + "&FromQuery=yes ";
								
			       		    }
						}
						//�ڲ�ת��
						if(lTransactionType==SETTConstant.TransactionType.INTERNALVIREMENT)
						{
							TransCurrentDepositDelegation depositDelegation = new TransCurrentDepositDelegation();	
							
							currInfo.setTransNo(strTransNo);
							TransCurrentDepositAssembler data = new TransCurrentDepositAssembler(currInfo);
							Collection col=null;
							col = depositDelegation.findByConditions(data,-1,false);
							
							if(col != null && !col.isEmpty())
					        {								
								TransCurrentDepositInfo currentInfo = null;
								Iterator iterator=col.iterator();				
								if(iterator.hasNext())
								{
									currentInfo = (TransCurrentDepositInfo)iterator.next();
								}
					          	if(currentInfo != null)
				            	{
					       	       
										strTransDetailsURL= "/settlement/tran/current/control/c003.jsp?strAction=Querybatcheck&strTransNo=" + strTransNo + "&OBInstr=obinstr&strSuccessPageURL=/settlement/tran/current/view/v015.jsp&strFailPageURL=/settlement/obinstruction/view/v007.jsp";
										url1="/settlement/tran/current/control/c003.jsp";
										url2="strAction=Querybatcheck&strTransNo=" + strTransNo + "&OBInstr=obinstr&strSuccessPageURL=/settlement/tran/current/view/v015.jsp&strFailPageURL=/settlement/obinstruction/view/v007.jsp";
									
				       		    }
							}
						}
						//������λ���и���
						if(lTransactionType==SETTConstant.TransactionType.SUBCLIENT_BANKPAY||lTransactionType==SETTConstant.TransactionType.BANKPAY_DOWNTRANSFER)
						{
							TransCurrentDepositDelegation depositDelegation = new TransCurrentDepositDelegation();	
							
							
							TransCurrentDepositAssembler data = new TransCurrentDepositAssembler(currInfo);
							Collection col=null;
							col = depositDelegation.findByConditions(data,-1,false);
							
							if(col != null && !col.isEmpty())
					        {								
								TransCurrentDepositInfo currentInfo = null;
								Iterator iterator=col.iterator();				
								if(iterator.hasNext())
								{
									currentInfo = (TransCurrentDepositInfo)iterator.next();
								}
					          	if(currentInfo != null)
				            	{
					       	        
										strTransDetailsURL= "/settlement/tran/current/control/c003.jsp?strAction=Querybatcheck&strTransNo=" + strTransNo + "&OBInstr=obinstr&strSuccessPageURL=/settlement/tran/current/view/v215.jsp&strFailPageURL=/settlement/obinstruction/view/v007.jsp";
										url1="/settlement/tran/current/control/c003.jsp";
										url2="strAction=Querybatcheck&strTransNo=" + strTransNo + "&OBInstr=obinstr&strSuccessPageURL=/settlement/tran/current/view/v215.jsp&strFailPageURL=/settlement/obinstruction/view/v007.jsp";
									
				       		    }
							}
						}
						//���и���
						if(lTransactionType==SETTConstant.TransactionType.BANKPAY||lTransactionType==SETTConstant.TransactionType.BANKPAY_DOWNTRANSFER)
						{           
							long lBankID = -1; 
							TransCurrentDepositDelegation depositDelegation = new TransCurrentDepositDelegation();	
						
							currInfo.setTransNo(strTransNo);
							Connection con = null;
							PreparedStatement ps = null;
			                con = Database.getConnection();
							StringBuffer sql = new StringBuffer();								
							sql.append("select nbankid from sett_transcurrentdeposit a where a.stransno='"+strTransNo+"'");
							 
							ps = con.prepareStatement(sql.toString());
							rs_1 = ps.executeQuery();
							
							if(rs_1 != null && rs_1.next())
							{
								lBankID = rs_1.getLong("nbankid");
							}
							if(lBankID > 0)
							{
								currInfo.setBankID(lBankID);
								
							}
							if(con != null)
							{
								con.close();
								con = null;
							}
							if(ps != null)
							{
								ps.close();
								ps = null;
							}
							if(rs_1 != null)
							{
								rs_1.close();
								rs_1 = null;
							}   		
										
							strBranchName = DataFormat.formatString(NameRef.getBankNameByID(currInfo.getBankID() ))	;							
							TransCurrentDepositAssembler data = new TransCurrentDepositAssembler(currInfo);
							Collection col=null;
							col = depositDelegation.findByConditions(data,-1,false);
							
							if(col != null && !col.isEmpty())
					        {								
								TransCurrentDepositInfo currentInfo = null;
								Iterator iterator=col.iterator();				
								if(iterator.hasNext())
								{
									currentInfo = (TransCurrentDepositInfo)iterator.next();
								}
					          	if(currentInfo != null)
				            	{
					       	        
										
										strTransDetailsURL= "/settlement/tran/current/control/c003.jsp?strAction=Querybatcheck&strTransNo=" + strTransNo + "&OBInstr=obinstr&strSuccessPageURL=/settlement/tran/current/view/v115.jsp&strFailPageURL=/settlement/obinstruction/view/v007.jsp";
										url1="/settlement/tran/current/control/c003.jsp";
										url2="strAction=Querybatcheck&strTransNo=" + strTransNo + "&OBInstr=obinstr&strSuccessPageURL=/settlement/tran/current/view/v115.jsp&strFailPageURL=/settlement/obinstruction/view/v007.jsp";
									
				       		    }
							}	
						}
						
						//���и����˾����
						if(lTransactionType==SETTConstant.TransactionType.BANKPAY_FINCOMPANYPAY)
						{
							TransCurrentDepositDelegation depositDelegation = new TransCurrentDepositDelegation();	
							
							currInfo.setTransNo(strTransNo);
							TransCurrentDepositAssembler data = new TransCurrentDepositAssembler(currInfo);
							Collection col=null;
							col = depositDelegation.findByConditions(data,-1,false);
							
							if(col != null && !col.isEmpty())
					        {								
								TransCurrentDepositInfo currentInfo = null;
								Iterator iterator=col.iterator();				
								if(iterator.hasNext())
								{
									currentInfo = (TransCurrentDepositInfo)iterator.next();
								}
					          	if(currentInfo != null)
				            	{
					       	       
										strTransDetailsURL= "/settlement/tran/current/control/c003_3.jsp?strAction=Querybatcheck&strTransNo=" + strTransNo + "&OBInstr=obinstr&strSuccessPageURL=/settlement/tran/current/view/v115_3.jsp&strFailPageURL=/settlement/obinstruction/view/v007.jsp";
										url1="/settlement/tran/current/control/c003_3.jsp";
										url2="strAction=Querybatcheck&strTransNo=" + strTransNo + "&OBInstr=obinstr&strSuccessPageURL=/settlement/tran/current/view/v115_3.jsp&strFailPageURL=/settlement/obinstruction/view/v007.jsp";
									
				       		    }
							}	
						}
						
						//���и���-�����˻�
						if(lTransactionType==SETTConstant.TransactionType.BANKPAY_PAYSUBACCOUNT)
						{
							TransCurrentDepositDelegation depositDelegation = new TransCurrentDepositDelegation();	
							
							currInfo.setTransNo(strTransNo);
							TransCurrentDepositAssembler data = new TransCurrentDepositAssembler(currInfo);
							Collection col=null;
							col = depositDelegation.findByConditions(data,-1,false);
							
							if(col != null && !col.isEmpty())
					        {								
								TransCurrentDepositInfo currentInfo = null;
								Iterator iterator=col.iterator();				
								if(iterator.hasNext())
								{
									currentInfo = (TransCurrentDepositInfo)iterator.next();
								}
					          	if(currentInfo != null)
				            	{
					       	       
										strTransDetailsURL= "/settlement/tran/current/control/c003_4.jsp?strAction=Querybatcheck&strTransNo=" + strTransNo + "&OBInstr=obinstr&strSuccessPageURL=/settlement/tran/current/view/v115_4.jsp&strFailPageURL=/settlement/obinstruction/view/v007.jsp";
										url1="/settlement/tran/current/control/c003_4.jsp";
										url2="strAction=Querybatcheck&strTransNo=" + strTransNo + "&OBInstr=obinstr&strSuccessPageURL=/settlement/tran/current/view/v115_4.jsp&strFailPageURL=/settlement/obinstruction/view/v007.jsp";
									
				       		    }
							}	
						}
					}	
					if(strBranchName==null)
					{
						strBranchName="";
					}
					else{
						strBranchName = strBranchName;
					}
					PagerTools.returnCellList(cellList,strBranchName);//������
					
					PagerTools.returnCellList(cellList,DataFormat.formatDisabledAmount(Amount,2));//���
					PagerTools.returnCellList(cellList,DataFormat.formatString(DataFormat.formatDate(ExecuteDate)));//ִ������
					String strStatus = "";
					if(DepositBillStatusID>0){
						strStatus = DataFormat.formatString(OBConstant.SettInstrStatus.getName(DepositBillStatusID));
					}else{
						strStatus = DataFormat.formatString(OBConstant.SettInstrStatus.getName(Status));
					}
					if(strStatus.equals("�Ѹ���")||strStatus.equals("��ǩ��"))
					{
						strStatus="���ύ";
					}
					PagerTools.returnCellList(cellList,strStatus);//״̬
					PagerTools.returnCellList(cellList,strTransNo+","+url1+","+url2);//���׺�
					
					
					//�洢������
					rowInfo = new ResultPagerRowInfo();
					rowInfo.setCell(cellList);
					rowInfo.setId(String.valueOf(rs.getLong("rownum1")));
					
					//��������
					resultList.add(rowInfo);
					
				}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException(e.getMessage());
		}
		
		return resultList;
		
	}
	
	public ArrayList getBatchOBFinanceCheckSumAmount(ResultSet rs , Map map) throws Exception{
		
		try{
			
			ArrayList list = new ArrayList();
			
			QueryOBFinanceInstrInfo qInfo = (QueryOBFinanceInstrInfo)map.get("qInfo");
			
			QOBFinanceInstr qobj = new QOBFinanceInstr();
			QueryOBFinanceSumInfo qsum = null;
			qsum = qobj.querySumOBFinanceInstrInfo(qInfo);
			double  pageSum = 0.00; //��ǰҳ����
			double Amount = 0.0;
			while(rs.next()){
				Amount = rs.getDouble("Amount");
				pageSum+=Amount;
			}
			
			list.add("����ܼ�{"+DataFormat.formatDisabledAmount(qsum.getAllFinanceSumInfo())+"}");
			list.add("��ҳ���ϼ�{"+DataFormat.formatDisabledAmount(pageSum)+"}");
			
			return list;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException(e.getMessage());
		}
	
		
	}
	
	public List<String[]> getBranchShowList(QueryOBFinanceInstrInfo qInfo) throws SQLException{
		
		List<String[]> resultList = new ArrayList<String[]>();
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;				
		
		StringBuffer bufferSQL = new StringBuffer();
		
		conn = getConnection();
		
		bufferSQL.append(" select distinct b.ID BranchID,b.sCode BranchNo,b.sName BranchName,b.sBankAccountCode BranchAccountNo \n");
		bufferSQL.append(" from sett_Branch b \n");
		bufferSQL.append(" where b.nStatusID=1 \n");		
		bufferSQL.append(" and b.nOfficeID = "+qInfo.getOfficeID()+" \n");
		bufferSQL.append(" and b.nCurrencyID = "+qInfo.getCurrencyID()+" \n");
		bufferSQL.append(" order by b.sCode");
		
		ps = conn.prepareStatement(bufferSQL.toString());
		rs = ps.executeQuery(); 
		
		while(rs.next()){
			String[] rsArr = new String[2];
			rsArr[0] = rs.getString("BranchID");
			rsArr[1] = rs.getString("BranchName");
			resultList.add(rsArr);
		}
		
	    cleanup(rs);
	    cleanup(ps);
	    cleanup(conn);
	    
		return resultList;
				
	}
	
}

