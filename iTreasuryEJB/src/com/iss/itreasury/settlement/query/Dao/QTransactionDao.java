package com.iss.itreasury.settlement.query.Dao;

import com.iss.itreasury.settlement.query.paraminfo.QueryTransactionConditionInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Env;

/**
 * �˻���ѯ���ݲ�
 * @author xiang
 *
 */
public class QTransactionDao {
	
	public String getTransactionSQL(QueryTransactionConditionInfo info){
		StringBuffer m_sbSelect = new StringBuffer();
		StringBuffer m_sbWhere = new StringBuffer();
		StringBuffer m_sbFrom = new StringBuffer();
		m_sbSelect.append(" ID,SerialNo,OfficeID,CurrencyID,TransNo, \n");
		m_sbSelect.append(" TransactionTypeID,InterestStart,Execute,StatusID,InputuserID, \n");
		m_sbSelect.append(" CheckuserID,Abstract,PayclientID,PayaccountID,PayAmount, \n");
		m_sbSelect.append(" ReceiveAmount,ReceiveClientID,ReceiveAccountID,BankID,ContractID, \n");
		m_sbSelect.append(" LoanFormID,DepositNo,PayAccountNo,PayAccountName,ReceiveAccountNo, \n");
		m_sbSelect.append(" ReceiveAccountName,PayClientCode,PayClientName,ReceiveClientCode,ReceiveClientName, \n");
		m_sbSelect.append(" DeclarationNo,BankChequeNo,");
		//modify by xlchang 2010-11-05 ������� �����ʹ�������� 
		m_sbSelect.append(" Operationtypeid,Source, \n");
		//modify by bingliu 2011-08-01 �����������˻����շ��������˻� 
		m_sbSelect.append(" PayBakAccountNo,ReceiveBakAccountNo \n");
		// from 
		m_sbFrom.append(" SETT_VTRANSACTION \n");
		// where 
		
		//m_sbWhere.append(" StatusID>0 ");
		
		if (info.getQueryType() == 200)		
		{
			//��ҳ���ϵ�ѡ�� ��ѯ��ɾ�����ڿ�����֪ͨ�������� ��ť����ʱ�ߴ˷�֧
			m_sbWhere.append(" StatusID =0 ");
			m_sbWhere.append(
					" and TransactionTypeID in ("
						+ SETTConstant.TransactionType.OPENFIXEDDEPOSIT				//���ڿ���
						+ ","
						+ SETTConstant.TransactionType.FIXEDCONTINUETRANSFER		//����ת��
						+ ","
						+ SETTConstant.TransactionType.OPENNOTIFYACCOUNT			//֪ͨ����
						+ ")");
			System.out.println("ִ�е��Ƕ��ڣ�֪ͨ�������׵ļ�¼!");
		}
		else
		{
			m_sbWhere.append(" StatusID>0 ");
		}
		
		if (info.getOfficeID() > 0)
		{
			if (info.getQueryType() == 100)
			{
				//���˻�����ѯ���룬Ĭ�ϲ�ѯͨ��ͨ�ҽ���
				info.setDifoffice(Constant.TRUE);
			}
			if(info.getDifoffice()==Constant.TRUE)
			{//�����ʾͨ��ͨ�ҽ���
				//�����Ҫ��ʾͨ��ͨ�ҽ��ף�����Ҫ���Ӳ��ڱ����´������ģ������뱾���´���صĽ���
				//���������
				//1.��ǰ��ѯ�������ܲ����ܲ���Ҫ�������ͨ��ͨ�ҽ���
				//2.��ǰ��ѯ�����Ƿֲ���ֻ�ܲ���뱾������ص�ͨ��ͨ�ҽ���
				String sbOr = "";
				if(info.getOfficeID()==Env.getHQOFFICEID())
				{//�����ǰ���´����ܲ�
					sbOr = "or (OfficeID!=" + info.getOfficeID() + " and Isdifoffice = 1)";
				}
				else
				{//����Ƿ�֧����
					sbOr = "or (OfficeID!=" + info.getOfficeID() + " and Isdifoffice = 1 and (Payofficeid ="+info.getOfficeID()+" or Receiveofficeid ="+info.getOfficeID()+ "))";
				}
				m_sbWhere.append(" and (OfficeID=" + info.getOfficeID() + sbOr+")");
			}
			else
			{
				m_sbWhere.append(" and OfficeID=" + info.getOfficeID() + "");
			}
		}
			
		if (info.getCurrencyID() > 0)
			m_sbWhere.append(" and CurrencyID=" + info.getCurrencyID() + "");
		//ѡ�����˻��������ͺ󣬽������ͱ���Ч
		if (info.getAccountTransTypeID() > 0)
		{
		    String sqlTmp = "";
			switch ((int) info.getAccountTransTypeID())
			{
				//���ڴ����Բ�ѯ������ʹ�û��ڴ���˻��Ľ���
				case (int) SETTConstant.AccountTransactionType.CURRENT_DEPOSIT :
				    sqlTmp = "select a.ID from sett_Account a,sett_accountType b where a.nAccountTypeID=b.id"
							+ " and b.nAccountGroupID = "+ SETTConstant.AccountGroupType.CURRENT;
					m_sbWhere.append(
						" and (PayAccountID in (" + sqlTmp + ") or ReceiveAccountID in ("+ sqlTmp +"))");
					break;
					//���ڴ����Բ�ѯ���Ͷ��ڴ��ҵ����صĽ��ס�
				case (int) SETTConstant.AccountTransactionType.FIXED_DEPOSIT :
				    sqlTmp = "select a.ID from sett_Account a,sett_accountType b where a.nAccountTypeID=b.id"
						+ " and b.nAccountGroupID = "+ SETTConstant.AccountGroupType.FIXED;
				    m_sbWhere.append(
							" and (PayAccountID in (" + sqlTmp + ") or ReceiveAccountID in ("+ sqlTmp +"))");
					break;
					//֪ͨ�����Բ�ѯ����֪ͨ���ҵ����صĽ��ס�
				case (int) SETTConstant.AccountTransactionType.NOTIFY_DEPOSIT :
				    sqlTmp = "select a.ID from sett_Account a,sett_accountType b where a.nAccountTypeID=b.id"
						+ " and b.nAccountGroupID = "+ SETTConstant.AccountGroupType.NOTIFY;
				    m_sbWhere.append(
							" and (PayAccountID in (" + sqlTmp + ") or ReceiveAccountID in ("+ sqlTmp +"))");
					break;
					//���д�����Բ�ѯ�������д���ҵ����صĽ��ס�
				case (int) SETTConstant.AccountTransactionType.TRUST_LOAN :
				    sqlTmp = "select a.ID from sett_Account a,sett_accountType b where a.nAccountTypeID=b.id"
						+ " and b.nAccountGroupID = "+ SETTConstant.AccountGroupType.TRUST;
				    m_sbWhere.append(
							" and (PayAccountID in (" + sqlTmp + ") or ReceiveAccountID in ("+ sqlTmp +"))");
					break;
					//ί�д�����Բ�ѯ����ί�д���ҵ����صĽ��ס�
				case (int) SETTConstant.AccountTransactionType.CONSIGN_LOAN :
				    sqlTmp = "select a.ID from sett_Account a,sett_accountType b where a.nAccountTypeID=b.id"
						+ " and b.nAccountGroupID = "+ SETTConstant.AccountGroupType.CONSIGN;
				    m_sbWhere.append(
							" and (PayAccountID in (" + sqlTmp + ") or ReceiveAccountID in ("+ sqlTmp +"))");
					break;
					//���֣����Բ�ѯ��������ҵ����صĽ��ס�
				case (int) SETTConstant.AccountTransactionType.DISCOUNT_LOAN :
				    sqlTmp = "select a.ID from sett_Account a,sett_accountType b where a.nAccountTypeID=b.id"
						+ " and b.nAccountGroupID = "+ SETTConstant.AccountGroupType.DISCOUNT;
				    m_sbWhere.append(
							" and (PayAccountID in (" + sqlTmp + ") or ReceiveAccountID in ("+ sqlTmp +"))");
					break;
					// ���ڴ�����Բ�ѯ���Ͷ��ڴ���ҵ����صĽ��ס�
					//ѭ��������Բ�ѯ����ѭ������ҵ����صĽ��ס�
					//ί��ҵ�񣺿��Բ�ѯ��ί��ҵ��˵������еĽ��ס�
					//���⸶����Բ�ѯ���и��֧Ʊ����ֽ𸶿Ʊ�㸶��ҵ��
				case (int) SETTConstant.AccountTransactionType.OUT_PAYMENT :
					m_sbWhere.append(
						" and TransactionTypeID in ("
							+ SETTConstant.TransactionType.BANKPAY
							+ ","
							+ SETTConstant.TransactionType.DRAFTPAY
							+ ","
							+ SETTConstant.TransactionType.CASHPAY
							+ ","
							+ SETTConstant.TransactionType.CHECKPAY
							+ ")");
					break;
			}
		}
		else
		{
			m_sbWhere.append(" and TransactionTypeID<>" + SETTConstant.TransactionType.CHANGECERTIFICATE + "");
			if (info.getTransactionTypeIDs().length() > 0) //add by rxie for query TransSpecialOperation subTrans
				m_sbWhere.append(" and ( TransactionTypeID in (" + info.getTransactionTypeIDs() + ") or OperationTypeID in (" + info.getTransactionTypeIDs() + ") )");
		}
		if (info.getSource() > 0)
			m_sbWhere.append(" and source=" + info.getSource() + "");
		if (info.getBankID() > 0)
			m_sbWhere.append(" and BankID=" + info.getBankID() + "");
		if (info.getInputuserID() != -1)//modify by xwhe 2009-03-30 ����ID Ϊ-100
			m_sbWhere.append(" and InputuserID=" + info.getInputuserID() + "");
		if (info.getCheckuserID() != -1)//modify by xwhe 2009-03-30 ����ID Ϊ-100
			m_sbWhere.append(" and CheckuserID=" + info.getCheckuserID() + "");
		if (info.getStatusID() > 0)
			m_sbWhere.append(" and StatusID=" + info.getStatusID() + "");
		if (info.getPayClientIDStart() > 0 && (info.getPayClientNoStart() == null || info.getPayClientNoStart().trim().length()<=0))
			m_sbWhere.append(" and PayclientID>=" + info.getPayClientIDStart() + "");
		if (info.getPayClientIDEnd() > 0 && (info.getPayClientNoEnd() == null || info.getPayClientNoEnd().trim().length()<=0))
			m_sbWhere.append(" and PayclientID<=" + info.getPayClientIDEnd() + "");
		if (info.getPayAccountIDStart() > 0 && (info.getPayAccountNoStart() == null || info.getPayAccountNoStart().trim().length()<=0))
			m_sbWhere.append(" and PayaccountID>=" + info.getPayAccountIDStart() + "");
		if (info.getPayAccountIDEnd() > 0 && (info.getPayAccountNoEnd() == null || info.getPayAccountNoEnd().trim().length()<=0))
			m_sbWhere.append(" and PayaccountID<=" + info.getPayAccountIDEnd() + "");
		if (info.getReceiveClientIDStart() > 0 && (info.getReceiveClientNoStart() == null || info.getReceiveClientNoStart().trim().length()<=0))
			m_sbWhere.append(" and ReceiveClientID>=" + info.getReceiveClientIDStart() + "");
		if (info.getReceiveClientIDEnd() > 0 && (info.getReceiveClientNoEnd() == null || info.getReceiveClientNoEnd().trim().length()<=0))
			m_sbWhere.append(" and ReceiveClientID<=" + info.getReceiveClientIDEnd() + "");
		if (info.getReceiveAccountIDStart() > 0 && (info.getReceiveAccountNoStart() == null || info.getReceiveAccountNoStart().trim().length()<=0))
			m_sbWhere.append(" and ReceiveAccountID>=" + info.getReceiveAccountIDStart() + "");
		if (info.getReceiveAccountIDEnd() > 0 && (info.getReceiveAccountNoEnd() == null || info.getReceiveAccountNoEnd().trim().length()<=0))
			m_sbWhere.append(" and ReceiveAccountID<=" + info.getReceiveAccountIDEnd() + "");

		if (info.getContractID() > 0)
			m_sbWhere.append(" and ContractID=" + info.getContractID() + "");
		if (info.getPayFormID() > 0)
			m_sbWhere.append(" and LoanFormID=" + info.getPayFormID() + "");
//		if (!"blank".equals(info.getPayMoneyStartBlank()))
//			m_sbWhere.append(" and PayAmount>=" + info.getPayAmountStart() + "");
//		if (!"blank".equals(info.getPayMoneyEndBlank()))
//			m_sbWhere.append(" and PayAmount<=" + info.getPayAmountEnd() + "");
//		if (!"blank".equals(info.getReceiveMoneyStartBlank()))
//			m_sbWhere.append(" and ReceiveAmount>=" + info.getReceiveAmountStart() + "");
//		if (!"blank".equals(info.getReceiveMoneyEndBlank()))
//			m_sbWhere.append(" and ReceiveAmount<=" + info.getReceiveAmountEnd() + "");
		if (info.getPayAmountStart()>0)
			m_sbWhere.append(" and PayAmount>=" + info.getPayAmountStart() + "");
		if (info.getPayAmountEnd()>0)
			m_sbWhere.append(" and PayAmount<=" + info.getPayAmountEnd() + "");
		if (info.getReceiveAmountStart()>0)
			m_sbWhere.append(" and ReceiveAmount>=" + info.getReceiveAmountStart() + "");
		if (info.getReceiveAmountEnd()>0)
			m_sbWhere.append(" and ReceiveAmount<=" + info.getReceiveAmountEnd() + "");
		if (info.getQueryType() == 100)
		{
			//���˻�����ѯ���룬�߼��ǣ��ո��˻��ǡ��򡱵Ĺ�ϵ
			if ((info.getPayAccountNoStart() != null && info.getPayAccountNoStart().trim().length() > 0)
				&& (info.getReceiveAccountNoStart() != null && info.getReceiveAccountNoStart().trim().length() > 0))
			{
				m_sbWhere.append(" and (PayAccountNo='" + info.getPayAccountNoStart() + "' or ReceiveAccountNo='" + info.getReceiveAccountNoStart() + "'");
				m_sbWhere.append(" or  paybakaccountno='" + info.getPayAccountNoStart() + "' or receivebakaccountno='" + info.getReceiveAccountNoStart() + "')");
			}
		}
		else
		{
			if (info.getPayClientNoStart() != null && info.getPayClientNoStart().trim().length() > 0
					&& (info.getPayAccountNoStart() == null || info.getPayAccountNoStart().trim().length()<=0))
				m_sbWhere.append(" and PayClientCode>='" + info.getPayClientNoStart() + "'");
			if (info.getPayClientNoEnd() != null && info.getPayClientNoEnd().trim().length() > 0
				 	&& (info.getPayAccountNoEnd() == null || info.getPayAccountNoEnd().trim().length()<=0))
				m_sbWhere.append(" and PayClientCode<='" + info.getPayClientNoEnd() + "'");
			if (info.getPayAccountNoStart() != null && info.getPayAccountNoStart().trim().length() > 0)
				m_sbWhere.append(" and PayAccountNo>='" + info.getPayAccountNoStart() + "'");
			if (info.getPayAccountNoEnd() != null && info.getPayAccountNoEnd().trim().length() > 0)
				m_sbWhere.append(" and PayAccountNo<='" + info.getPayAccountNoEnd() + "'");
			//add by 2012-05-17 ��Ӹ��ָ�����
			if(info.getPayAppointAccountNo() != null && info.getPayAppointAccountNo().length() > 0){
				m_sbWhere.append(" and PayAccountNo in ('"+info.getPayAppointAccountNo()+"')");
			}
			if (info.getReceiveClientNoStart() != null && info.getReceiveClientNoStart().trim().length() > 0
					&& (info.getReceiveAccountNoStart() == null || info.getReceiveAccountNoStart().trim().length()<=0))
				m_sbWhere.append(" and ReceiveClientCode>='" + info.getReceiveClientNoStart() + "'");
			if (info.getReceiveClientNoEnd() != null && info.getReceiveClientNoEnd().trim().length() > 0
					&& (info.getReceiveAccountNoEnd() == null || info.getReceiveAccountNoEnd().trim().length()<=0))
				m_sbWhere.append(" and ReceiveClientCode<='" + info.getReceiveClientNoEnd() + "'");
			if (info.getReceiveAccountNoStart() != null && info.getReceiveAccountNoStart().trim().length() > 0)
				m_sbWhere.append(" and ReceiveAccountNo>='" + info.getReceiveAccountNoStart() + "'");
			if (info.getReceiveAccountNoEnd() != null && info.getReceiveAccountNoEnd().trim().length() > 0)
				m_sbWhere.append(" and ReceiveAccountNo<='" + info.getReceiveAccountNoEnd() + "'");
			//add by 2012-05-17 ����տָ�����
			if(info.getReceiveAppointAccountNo() != null && info.getReceiveAppointAccountNo().length() > 0){
				m_sbWhere.append(" and ReceiveAccountNo in ('"+info.getReceiveAppointAccountNo()+"')");
			}
		}
		if (info.getApplyCode() != null && info.getApplyCode().trim().length() > 0)
			m_sbWhere.append(" and applycode like '%" + info.getApplyCode() + "%'");
		if (info.getTransNoStart() != null && info.getTransNoStart().trim().length() > 0)
			m_sbWhere.append(" and TransNo>='" + info.getTransNoStart() + "'");
		if (info.getTransNoEnd() != null && info.getTransNoEnd().trim().length() > 0)
			m_sbWhere.append(" and TransNo<='" + info.getTransNoEnd() + "'");
		if (info.getDepositNo() != null && info.getDepositNo().trim().length() > 0)
			m_sbWhere.append(" and DepositNo='" + info.getDepositNo() + "'");
		if (info.getInterestStartStart() != null)
			m_sbWhere.append(" and InterestStart>=to_date('" + DataFormat.getDateString(info.getInterestStartStart()) + "','yyyy-mm-dd')");
		if (info.getInterestStartEnd() != null)
			m_sbWhere.append(" and InterestStart<=to_date('" + DataFormat.getDateString(info.getInterestStartEnd()) + "','yyyy-mm-dd')");
		if (info.getExecuteStart() != null)
			m_sbWhere.append(" and Execute>=to_date('" + DataFormat.getDateString(info.getExecuteStart()) + "','yyyy-mm-dd')");
		if (info.getExecuteEnd() != null)
			m_sbWhere.append(" and Execute<=to_date('" + DataFormat.getDateString(info.getExecuteEnd()) + "','yyyy-mm-dd')");
		if(info.getBankChequeNO() != null && !"".equals(info.getBankChequeNO().trim()))
		{
			m_sbWhere.append(" and bankchequeno ='" + info.getBankChequeNO().trim()+"'");
		}
		if(info.getDeclarationNO() != null && !"".equals(info.getDeclarationNO().trim()))
		{
			m_sbWhere.append(" and declarationno ='" + info.getDeclarationNO().trim()+"'");
		}
		//		add by jzw 2010-08-24 ���ӶԶ�����ǰ����֧ȡ�¿����浥�Ĺ���
		int index=info.getTransactionTypeIDs().indexOf(""+SETTConstant.TransactionType.OPENFIXEDDEPOSIT);
		if(index >= 0 || info.getTransactionTypeIDs()==""){
		
			m_sbWhere.append(" and StatusID <> "+SETTConstant.TransactionStatus.WAITAPPROVAL);
		
		}
		
		
		StringBuffer strSQL = new StringBuffer();
		if (info.getBankID() > 0)
		{
			strSQL = getTransactionSQLForQueryTSYW(info);
		}
		//end add by dwj 20120428
		//add by zyyao 2007-6-7 ����ժҪ��Ϊ��ѯ����
		if(info.getAbstract() != null && !"".equals(info.getAbstract().trim()))
		{
			m_sbWhere.append(" and abstract like '%" + info.getAbstract().trim()+"%'");
			strSQL.append(" and abstract like '%" + info.getAbstract().trim()+"%'");
		}
		if(info.getSigner()>0)
		{
			m_sbWhere.append(" and nvl(PayclientID,0) not in (select nclientid from sett_signature where NISSIGNATURE=1 ) ");
			m_sbWhere.append(" and nvl(ReceiveClientID,0) not in (select nclientid from sett_signature where NISSIGNATURE=1 ) ");
			strSQL.append(" and nvl(PayclientID,0) not in (select nclientid from sett_signature where NISSIGNATURE=1 ) ");
			strSQL.append(" and nvl(ReceiveClientID,0) not in (select nclientid from sett_signature where NISSIGNATURE=1 ) ");
		}
		//
		
		//		add by qhzhou 2007-07-25 ���ӶԶ�����ǰ����֧ȡ�¿����浥�Ĺ���
		int index1=info.getTransactionTypeIDs().indexOf(""+SETTConstant.TransactionType.OPENFIXEDDEPOSIT);
		if(index1 >= 0 || info.getTransactionTypeIDs()==""){
		
			m_sbWhere.append(" and StatusID <> "+SETTConstant.TransactionStatus.WAITAPPROVAL);
			strSQL.append(" and StatusID <> "+SETTConstant.TransactionStatus.WAITAPPROVAL);
		
		}
		//
		//add by dwj 20120428
		if (info.getBankID() > 0)
		{
			m_sbWhere.append(" Union ");
			m_sbWhere.append(strSQL);
		}		
		return " SELECT "+m_sbSelect.toString()+ " FROM " + m_sbFrom + " WHERE " + m_sbWhere;
	}
	
	public StringBuffer getTransactionSQLForQueryTSYW(QueryTransactionConditionInfo info)
	{
		
		StringBuffer returnSQL = new StringBuffer();
		StringBuffer m_sbSelect = new StringBuffer();
		// select 
		m_sbSelect.append(" t1.ID,SerialNo,OfficeID,CurrencyID,TransNo, \n");
		m_sbSelect.append(" TransactionTypeID,InterestStart,Execute,StatusID,InputuserID, \n");
		m_sbSelect.append(" CheckuserID,Abstract,PayclientID,PayaccountID,PayAmount, \n");
		m_sbSelect.append(" ReceiveAmount,ReceiveClientID,ReceiveAccountID,BankID,ContractID, \n");
		m_sbSelect.append(" LoanFormID,DepositNo,PayAccountNo,PayAccountName,ReceiveAccountNo, \n");
		m_sbSelect.append(" ReceiveAccountName,PayClientCode,PayClientName,ReceiveClientCode,ReceiveClientName, \n");
		m_sbSelect.append(" DeclarationNo,BankChequeNo,");
		//modify by xlchang 2010-11-05 ������� �����ʹ�������� 
		m_sbSelect.append(" Operationtypeid,Source,PayBakAccountNo,ReceiveBakAccountNo \n");
		// from 
		StringBuffer m_sbFrom = new StringBuffer();
		m_sbFrom.append(" SETT_VTRANSACTION t1,Sett_TransSpecialOperation t2 \n");
		// where 
		StringBuffer m_sbWhere = new StringBuffer();
		//m_sbWhere.append(" StatusID>0 ");
		
		if (info.getQueryType() == 200)		
		{
			//��ҳ���ϵ�ѡ�� ��ѯ��ɾ�����ڿ�����֪ͨ�������� ��ť����ʱ�ߴ˷�֧
			m_sbWhere.append(" t1.StatusID =0 ");
			m_sbWhere.append(
					" and t1.TransactionTypeID in ("
						+ SETTConstant.TransactionType.OPENFIXEDDEPOSIT				//���ڿ���
						+ ","
						+ SETTConstant.TransactionType.FIXEDCONTINUETRANSFER		//����ת��
						+ ","
						+ SETTConstant.TransactionType.OPENNOTIFYACCOUNT			//֪ͨ����
						+ ")");
			System.out.println("ִ�е��Ƕ��ڣ�֪ͨ�������׵ļ�¼!");
		}
		else
		{
			m_sbWhere.append(" t1.StatusID>0 ");
		}
		
		m_sbWhere.append(" and t1.transno = t2.stransno ");
		
		if (info.getOfficeID() > 0)
			m_sbWhere.append(" and t1.OfficeID=" + info.getOfficeID() + "");
		if (info.getCurrencyID() > 0)
			m_sbWhere.append(" and t1.CurrencyID=" + info.getCurrencyID() + "");
		//ѡ�����˻��������ͺ󣬽������ͱ���Ч
		if (info.getAccountTransTypeID() > 0)
		{
		    String sqlTmp = "";
			switch ((int) info.getAccountTransTypeID())
			{
				//���ڴ����Բ�ѯ������ʹ�û��ڴ���˻��Ľ���
				case (int) SETTConstant.AccountTransactionType.CURRENT_DEPOSIT :
				    sqlTmp = "select a.ID from sett_Account a,sett_accountType b where a.nAccountTypeID=b.id"
							+ " and b.nAccountGroupID = "+ SETTConstant.AccountGroupType.CURRENT;
					m_sbWhere.append(
						" and (t1.PayAccountID in (" + sqlTmp + ") or t1.ReceiveAccountID in ("+ sqlTmp +"))");
					break;
					//���ڴ����Բ�ѯ���Ͷ��ڴ��ҵ����صĽ��ס�
				case (int) SETTConstant.AccountTransactionType.FIXED_DEPOSIT :
				    sqlTmp = "select a.ID from sett_Account a,sett_accountType b where a.nAccountTypeID=b.id"
						+ " and b.nAccountGroupID = "+ SETTConstant.AccountGroupType.FIXED;
				    m_sbWhere.append(
							" and (t1.PayAccountID in (" + sqlTmp + ") or t1.ReceiveAccountID in ("+ sqlTmp +"))");
					break;
					//֪ͨ�����Բ�ѯ����֪ͨ���ҵ����صĽ��ס�
				case (int) SETTConstant.AccountTransactionType.NOTIFY_DEPOSIT :
				    sqlTmp = "select a.ID from sett_Account a,sett_accountType b where a.nAccountTypeID=b.id"
						+ " and b.nAccountGroupID = "+ SETTConstant.AccountGroupType.NOTIFY;
				    m_sbWhere.append(
							" and (t1.PayAccountID in (" + sqlTmp + ") or t1.ReceiveAccountID in ("+ sqlTmp +"))");
					break;
					//���д�����Բ�ѯ�������д���ҵ����صĽ��ס�
				case (int) SETTConstant.AccountTransactionType.TRUST_LOAN :
				    sqlTmp = "select a.ID from sett_Account a,sett_accountType b where a.nAccountTypeID=b.id"
						+ " and b.nAccountGroupID = "+ SETTConstant.AccountGroupType.TRUST;
				    m_sbWhere.append(
							" and (t1.PayAccountID in (" + sqlTmp + ") or t1.ReceiveAccountID in ("+ sqlTmp +"))");
					break;
					//ί�д�����Բ�ѯ����ί�д���ҵ����صĽ��ס�
				case (int) SETTConstant.AccountTransactionType.CONSIGN_LOAN :
				    sqlTmp = "select a.ID from sett_Account a,sett_accountType b where a.nAccountTypeID=b.id"
						+ " and b.nAccountGroupID = "+ SETTConstant.AccountGroupType.CONSIGN;
				    m_sbWhere.append(
							" and (t1.PayAccountID in (" + sqlTmp + ") or t1.ReceiveAccountID in ("+ sqlTmp +"))");
					break;
					//���֣����Բ�ѯ��������ҵ����صĽ��ס�
				case (int) SETTConstant.AccountTransactionType.DISCOUNT_LOAN :
				    sqlTmp = "select a.ID from sett_Account a,sett_accountType b where a.nAccountTypeID=b.id"
						+ " and b.nAccountGroupID = "+ SETTConstant.AccountGroupType.DISCOUNT;
				    m_sbWhere.append(
							" and (t1.PayAccountID in (" + sqlTmp + ") or t1.ReceiveAccountID in ("+ sqlTmp +"))");
					break;
					// ���ڴ�����Բ�ѯ���Ͷ��ڴ���ҵ����صĽ��ס�
					//ѭ��������Բ�ѯ����ѭ������ҵ����صĽ��ס�
					//ί��ҵ�񣺿��Բ�ѯ��ί��ҵ��˵������еĽ��ס�
					//���⸶����Բ�ѯ���и��֧Ʊ����ֽ𸶿Ʊ�㸶��ҵ��
				case (int) SETTConstant.AccountTransactionType.OUT_PAYMENT :
					m_sbWhere.append(
						" and t1.TransactionTypeID in ("
							+ SETTConstant.TransactionType.BANKPAY
							+ ","
							+ SETTConstant.TransactionType.DRAFTPAY
							+ ","
							+ SETTConstant.TransactionType.CASHPAY
							+ ","
							+ SETTConstant.TransactionType.CHECKPAY
							+ ")");
					break;
			}
		}
		else
		{
			m_sbWhere.append(" and t1.TransactionTypeID<>" + SETTConstant.TransactionType.CHANGECERTIFICATE + "");
			if (info.getTransactionTypeIDs().length() > 0) //add by rxie for query TransSpecialOperation subTrans
				m_sbWhere.append(" and ( t1.TransactionTypeID in (" + info.getTransactionTypeIDs() + ") or t1.OperationTypeID in (" + info.getTransactionTypeIDs() + ") )");
		}
		if (info.getSource() > 0)
			m_sbWhere.append(" and t1.source=" + info.getSource() + "");
		if (info.getBankID() > 0)
			m_sbWhere.append(" and (t2.npaybankid="+ info.getBankID() +" or t2.NRECEIVEBANKID="+ info.getBankID() +" or t2.sextaccountno = (select sbankaccountcode from sett_branch where id = "+info.getBankID()+"))");
		if (info.getInputuserID() != -1)//modify by xwhe 2009-03-30 ����ID Ϊ-100
			m_sbWhere.append(" and t1.InputuserID=" + info.getInputuserID() + "");
		if (info.getCheckuserID() != -1)//modify by xwhe 2009-03-30 ����ID Ϊ-100
			m_sbWhere.append(" and t1.CheckuserID=" + info.getCheckuserID() + "");
		if (info.getStatusID() > 0)
			m_sbWhere.append(" and t1.StatusID=" + info.getStatusID() + "");
		if (info.getPayClientIDStart() > 0 && (info.getPayClientNoStart() == null || info.getPayClientNoStart().trim().length()<=0))
			m_sbWhere.append(" and t1.PayclientID>=" + info.getPayClientIDStart() + "");
		if (info.getPayClientIDEnd() > 0 && (info.getPayClientNoEnd() == null || info.getPayClientNoEnd().trim().length()<=0))
			m_sbWhere.append(" and t1.PayclientID<=" + info.getPayClientIDEnd() + "");
		if (info.getPayAccountIDStart() > 0 && (info.getPayAccountNoStart() == null || info.getPayAccountNoStart().trim().length()<=0))
			m_sbWhere.append(" and t1.PayaccountID>=" + info.getPayAccountIDStart() + "");
		if (info.getPayAccountIDEnd() > 0 && (info.getPayAccountNoEnd() == null || info.getPayAccountNoEnd().trim().length()<=0))
			m_sbWhere.append(" and t1.PayaccountID<=" + info.getPayAccountIDEnd() + "");
		if (info.getReceiveClientIDStart() > 0 && (info.getReceiveClientNoStart() == null || info.getReceiveClientNoStart().trim().length()<=0))
			m_sbWhere.append(" and t1.ReceiveClientID>=" + info.getReceiveClientIDStart() + "");
		if (info.getReceiveClientIDEnd() > 0 && (info.getReceiveClientNoEnd() == null || info.getReceiveClientNoEnd().trim().length()<=0))
			m_sbWhere.append(" and t1.ReceiveClientID<=" + info.getReceiveClientIDEnd() + "");
		if (info.getReceiveAccountIDStart() > 0 && (info.getReceiveAccountNoStart() == null || info.getReceiveAccountNoStart().trim().length()<=0))
			m_sbWhere.append(" and t1.ReceiveAccountID>=" + info.getReceiveAccountIDStart() + "");
		if (info.getReceiveAccountIDEnd() > 0 && (info.getReceiveAccountNoEnd() == null || info.getReceiveAccountNoEnd().trim().length()<=0))
			m_sbWhere.append(" and t1.ReceiveAccountID<=" + info.getReceiveAccountIDEnd() + "");

		if (info.getContractID() > 0)
			m_sbWhere.append(" and t1.ContractID=" + info.getContractID() + "");
		if (info.getPayFormID() > 0)
			m_sbWhere.append(" and t1.LoanFormID=" + info.getPayFormID() + "");
		if (!"blank".equals(info.getPayMoneyStartBlank()))
			m_sbWhere.append(" and t1.PayAmount>=" + info.getPayAmountStart() + "");
		if (!"blank".equals(info.getPayMoneyEndBlank()))
			m_sbWhere.append(" and t1.PayAmount<=" + info.getPayAmountEnd() + "");
		if (!"blank".equals(info.getReceiveMoneyStartBlank()))
			m_sbWhere.append(" and t1.ReceiveAmount>=" + info.getReceiveAmountStart() + "");
		if (!"blank".equals(info.getReceiveMoneyEndBlank()))
			m_sbWhere.append(" and t1.ReceiveAmount<=" + info.getReceiveAmountEnd() + "");
		if (info.getQueryType() == 100)
		{
			//���˻�����ѯ���룬�߼��ǣ��ո��˻��ǡ��򡱵Ĺ�ϵ
			if ((info.getPayAccountNoStart() != null && info.getPayAccountNoStart().trim().length() > 0)
				&& (info.getReceiveAccountNoStart() != null && info.getReceiveAccountNoStart().trim().length() > 0))
			{
				m_sbWhere.append(" and (t1.PayAccountNo='" + info.getPayAccountNoStart() + "' or t1.ReceiveAccountNo='" + info.getReceiveAccountNoStart() + "')");
			}
		}
		else
		{
			if (info.getPayClientNoStart() != null && info.getPayClientNoStart().trim().length() > 0
					&& (info.getPayAccountNoStart() == null || info.getPayAccountNoStart().trim().length()<=0))
				m_sbWhere.append(" and t1.PayClientCode>='" + info.getPayClientNoStart() + "'");
			if (info.getPayClientNoEnd() != null && info.getPayClientNoEnd().trim().length() > 0
					&& (info.getPayAccountNoEnd() == null || info.getPayAccountNoEnd().trim().length()<=0))
				m_sbWhere.append(" and t1.PayClientCode<='" + info.getPayClientNoEnd() + "'");
			if (info.getPayAccountNoStart() != null && info.getPayAccountNoStart().trim().length() > 0)
				m_sbWhere.append(" and t1.PayAccountNo>='" + info.getPayAccountNoStart() + "'");
			if (info.getPayAccountNoEnd() != null && info.getPayAccountNoEnd().trim().length() > 0)
				m_sbWhere.append(" and t1.PayAccountNo<='" + info.getPayAccountNoEnd() + "'");
			//add by 2012-05-17 ��Ӹ��ָ�����
			if(info.getPayAppointAccountNo() != null && info.getPayAppointAccountNo().length() > 0){
				m_sbWhere.append(" and PayAccountNo in ('"+info.getPayAppointAccountNo()+"')");
			}
			if (info.getReceiveClientNoStart() != null && info.getReceiveClientNoStart().trim().length() > 0
					&& (info.getReceiveAccountNoStart() == null || info.getReceiveAccountNoStart().trim().length()<=0))
				m_sbWhere.append(" and t1.ReceiveClientCode>='" + info.getReceiveClientNoStart() + "'");
			if (info.getReceiveClientNoEnd() != null && info.getReceiveClientNoEnd().trim().length() > 0
					&& (info.getReceiveAccountNoEnd() == null || info.getReceiveAccountNoEnd().trim().length()<=0))
				m_sbWhere.append(" and t1.ReceiveClientCode<='" + info.getReceiveClientNoEnd() + "'");
			if (info.getReceiveAccountNoStart() != null && info.getReceiveAccountNoStart().trim().length() > 0)
				m_sbWhere.append(" and t1.ReceiveAccountNo>='" + info.getReceiveAccountNoStart() + "'");
			if (info.getReceiveAccountNoEnd() != null && info.getReceiveAccountNoEnd().trim().length() > 0)
				m_sbWhere.append(" and t1.ReceiveAccountNo<='" + info.getReceiveAccountNoEnd() + "'");
			//add by 2012-05-17 ����տָ�����
			if(info.getReceiveAppointAccountNo() != null && info.getReceiveAppointAccountNo().length() > 0){
				m_sbWhere.append(" and ReceiveAccountNo in ('"+info.getReceiveAppointAccountNo()+"')");
			}
		}
		if (info.getApplyCode() != null && info.getApplyCode().trim().length() > 0)
			m_sbWhere.append(" and t1.applycode like '%" + info.getApplyCode() + "%'");
		if (info.getTransNoStart() != null && info.getTransNoStart().trim().length() > 0)
			m_sbWhere.append(" and t1.TransNo>='" + info.getTransNoStart() + "'");
		if (info.getTransNoEnd() != null && info.getTransNoEnd().trim().length() > 0)
			m_sbWhere.append(" and t1.TransNo<='" + info.getTransNoEnd() + "'");
		if (info.getDepositNo() != null && info.getDepositNo().trim().length() > 0)
			m_sbWhere.append(" and t1.DepositNo='" + info.getDepositNo() + "'");
		if (info.getInterestStartStart() != null)
			m_sbWhere.append(" and t1.InterestStart>=to_date('" + DataFormat.getDateString(info.getInterestStartStart()) + "','yyyy-mm-dd')");
		if (info.getInterestStartEnd() != null)
			m_sbWhere.append(" and t1.InterestStart<=to_date('" + DataFormat.getDateString(info.getInterestStartEnd()) + "','yyyy-mm-dd')");
		if (info.getExecuteStart() != null)
			m_sbWhere.append(" and t1.Execute>=to_date('" + DataFormat.getDateString(info.getExecuteStart()) + "','yyyy-mm-dd')");
		if (info.getExecuteEnd() != null)
			m_sbWhere.append(" and t1.Execute<=to_date('" + DataFormat.getDateString(info.getExecuteEnd()) + "','yyyy-mm-dd')");
		if(info.getBankChequeNO() != null && !"".equals(info.getBankChequeNO().trim()))
		{
			m_sbWhere.append(" and t1.bankchequeno ='" + info.getBankChequeNO().trim()+"'");
		}
		if(info.getDeclarationNO() != null && !"".equals(info.getDeclarationNO().trim()))
		{
			m_sbWhere.append(" and t1.declarationno ='" + info.getDeclarationNO().trim()+"'");
		}
		//		add by jzw 2010-08-24 ���ӶԶ�����ǰ����֧ȡ�¿����浥�Ĺ���
		int index=info.getTransactionTypeIDs().indexOf(""+SETTConstant.TransactionType.OPENFIXEDDEPOSIT);
		if(index >= 0 || info.getTransactionTypeIDs()==""){
		
			m_sbWhere.append(" and t1.StatusID <> "+SETTConstant.TransactionStatus.WAITAPPROVAL);
		
		}
		return returnSQL.append(" select "+m_sbSelect.toString()+" from " + m_sbFrom.toString() + " where " + m_sbWhere.toString());
	
	}

	public String getTransactionSQLForSubAccount(
			QueryTransactionConditionInfo info) {
		StringBuffer m_sbSelect = new StringBuffer();
		// select 
		m_sbSelect.append(" ID,SerialNo,OfficeID,CurrencyID,TransNo, \n");
		m_sbSelect.append(" TransactionTypeID,InterestStart,Execute,StatusID,InputuserID, \n");
		m_sbSelect.append(" CheckuserID,Abstract,PayclientID,PayaccountID,PayAmount, \n");
		m_sbSelect.append(" ReceiveAmount,ReceiveClientID,ReceiveAccountID,BankID,ContractID, \n");
		m_sbSelect.append(" LoanFormID,DepositNo,NewDepositNo,PayAccountNo,PayAccountName,ReceiveAccountNo, \n");
		m_sbSelect.append(" ReceiveAccountName,PayClientCode,PayClientName,ReceiveClientCode,ReceiveClientName \n");

		// from 
		StringBuffer m_sbFrom = new StringBuffer();
		m_sbFrom.append(" SETT_VTRANSACTION \n");
		// where 
		StringBuffer m_sbWhere = new StringBuffer();
		m_sbWhere.append(" ((TransactionTypeID not in (11,23) and StatusID=3) ");//��ʴ����ջ�/һ����������
		m_sbWhere.append(" or (TransactionTypeID in (11,23) and StatusID=7)) ");
		
		if (info.getOfficeID() > 0)
			m_sbWhere.append(" and OfficeID=" + info.getOfficeID() + "");
		if (info.getCurrencyID() > 0)
			m_sbWhere.append(" and CurrencyID=" + info.getCurrencyID() + "");
			
		/*
		if (info.getStatusID() > 0)
			m_sbWhere.append(" and StatusID=" + info.getStatusID() + "");
		*/
		if (info.getContractID() > 0)
			m_sbWhere.append(" and ContractID=" + info.getContractID() + "");
		if (info.getPayFormID() > 0)
			m_sbWhere.append(" and LoanFormID=" + info.getPayFormID() + "");

		if ((info.getPayAccountNoStart() != null && info.getPayAccountNoStart().trim().length() > 0) &&
		(info.getPayAccountNoEnd() != null && info.getPayAccountNoEnd().trim().length() > 0) &&
		(info.getReceiveAccountNoStart() != null && info.getReceiveAccountNoStart().trim().length() > 0) &&
		(info.getReceiveAccountNoEnd() != null && info.getReceiveAccountNoEnd().trim().length() > 0))
		{
			m_sbWhere.append(" and ((PayAccountNo>='" + info.getPayAccountNoStart() + "'");
			m_sbWhere.append(" and PayAccountNo<='" + info.getPayAccountNoEnd() + "')");
			m_sbWhere.append(" or (ReceiveAccountNo>='" + info.getReceiveAccountNoStart() + "'");
			m_sbWhere.append(" and ReceiveAccountNo<='" + info.getReceiveAccountNoEnd() + "'))");
		}
		if (info.getDepositNo() != null && info.getDepositNo().trim().length() > 0)
			m_sbWhere.append(" and (DepositNo='" + info.getDepositNo() + "' or NewDepositNo='" + info.getDepositNo() + "')");

		if (info.getExecuteStart() != null )
			m_sbWhere.append(" and Execute<=to_date('" + DataFormat.getDateString(info.getExecuteStart()) + "','yyyy-mm-dd'");
		if (info.getExecuteEnd() != null )
			m_sbWhere.append(" and Execute>=to_date('" + DataFormat.getDateString(info.getExecuteEnd()) + "','yyyy-mm-dd'");

		
		return "SELECT " + m_sbSelect.toString()+" FROM " + m_sbFrom.toString() + "WHERE" + m_sbWhere.toString();
	}

	/**
	 * ������ѯSQL
	 * @param info
	 */
	public String getTransactionSQLForPrint(QueryTransactionConditionInfo info,long lUserID)
	{
		StringBuffer sbSQL = new StringBuffer();
		//select 
		sbSQL.append(" select \n");
		sbSQL.append(" ID,SerialNo,OfficeID,CurrencyID,TransNo, \n");
		sbSQL.append(" TransactionTypeID,operationtypeid,InterestStart,Execute,StatusID,InputuserID, \n");
		sbSQL.append(" CheckuserID,Abstract,PayclientID,PayaccountID,PayAmount, \n");
		sbSQL.append(" ReceiveAmount,ReceiveClientID,ReceiveAccountID,BankID,ContractID, \n");
		sbSQL.append(" LoanFormID,DepositNo,PayAccountNo,PayAccountName,ReceiveAccountNo, \n");
		sbSQL.append(" ReceiveAccountName,PayClientCode,PayClientName,ReceiveClientCode,ReceiveClientName,CommitionAccountCode \n");
		//from 
		sbSQL.append(" from \n");
		
		//���������� Boxu 2007-1-5 ������ͼ sett_vtransaction ����  "SINSTRUCTIONNO ��ʶ�ǽ���ϵͳ��������ˮ��"
		//�޸���ǰ���߼�,�ɲ�ѯ����ֻҪ��"��"��"��"��Ϊ����λ��ҵ��
		sbSQL.append(" sett_vtransaction \n");
		
		//where 
		sbSQL.append(" where \n");
		
		if (info.getQueryType() == 200)	
		{
			//��ҳ���ϵ�ѡ�� ��ѯ��ɾ�����ڿ�����֪ͨ�������� ��ť����ʱ�ߴ˷�֧
			sbSQL.append(" StatusID =0 ");
			sbSQL.append(" and TransactionTypeID in ( "
						+ SETTConstant.TransactionType.OPENFIXEDDEPOSIT				//���ڿ���
						+ ","
						+ SETTConstant.TransactionType.FIXEDCONTINUETRANSFER		//����ת��
						+ ","
						+ SETTConstant.TransactionType.OPENNOTIFYACCOUNT			//֪ͨ����
						+ " ) ");
			System.out.println("ִ�е��Ƕ��ڣ�֪ͨ�������׵ļ�¼!");
		}
		else
		{
			sbSQL.append(" StatusID > 0 ");
			sbSQL.append(" and (PayAccountNo in ");
			sbSQL.append(" (select a.saccountno ");
			sbSQL.append(" from OB_AccountOwnedByUserQuery a, Sett_Account ai ");
			sbSQL.append(" where ai.nStatusID=1 ");
			sbSQL.append(" and a.saccountno=ai.saccountno ");
			sbSQL.append(" and a.nUserID="+lUserID);
			sbSQL.append(" and a.nclientid="+info.getClientId());
			sbSQL.append(" and ai.ncurrencyid="+info.getCurrencyID()+")");
			sbSQL.append(" or ReceiveAccountNo in ");
			sbSQL.append(" (select a.saccountno ");
			sbSQL.append(" from OB_AccountOwnedByUserQuery a, Sett_Account ai ");
			sbSQL.append(" where ai.nStatusID=1 ");
			sbSQL.append(" and a.saccountno=ai.saccountno ");
			sbSQL.append(" and a.nUserID="+lUserID);
			sbSQL.append(" and a.nclientid="+info.getClientId());
			sbSQL.append(" and ai.ncurrencyid="+info.getCurrencyID()+")");
			sbSQL.append(" or CommitionAccountCode in ");
			sbSQL.append(" (select a.saccountno ");
			sbSQL.append(" from OB_AccountOwnedByUserQuery a, Sett_Account ai ");
			sbSQL.append(" where ai.nStatusID=1 ");
			sbSQL.append(" and a.saccountno=ai.saccountno ");
			sbSQL.append(" and a.nclientid="+info.getClientId());
			sbSQL.append(" and a.nUserID="+lUserID);
			sbSQL.append(" and ai.ncurrencyid="+info.getCurrencyID()+")");
			sbSQL.append(" )");
			

			//Boxu add 2007-8-9 �޸� ����ѯ3��״̬ 1.��ɾ�� 2.���ݴ� 3.�Ѿܾ�
	    	sbSQL.append(" and StatusID not in("+SETTConstant.TransactionStatus.DELETED+","
	    											+SETTConstant.TransactionStatus.TEMPSAVE+","
	    											+SETTConstant.TransactionStatus.REFUSE+") ");
		}
		
    	//���Ϊ�յĲ��ܲ����
    	sbSQL.append(" and transno is not null ");
		
		//���������� Boxu 2006-12-12 ���ų�Ա��λ��ʶ
		sbSQL.append(" and ( PayClientID="+info.getPayClientIDStart()+" or ReceiveClientID = "+info.getReceiveClientIDStart()+" ) ");
		
		if (info.getOfficeID() > 0)
			sbSQL.append(" and OfficeID=" + info.getOfficeID() + "");
		if (info.getCurrencyID() > 0)
			sbSQL.append(" and CurrencyID=" + info.getCurrencyID() + "");
		
		//ѡ�����ʻ��������ͺ󣬽������ͱ���Ч
		if (info.getAccountTransTypeID() > 0)
		{
		    String sqlTmp = "";
			switch ((int) info.getAccountTransTypeID())
			{
				//���ڴ����Բ�ѯ������ʹ�û��ڴ���ʻ��Ľ���
				case (int) SETTConstant.AccountTransactionType.CURRENT_DEPOSIT :
				    sqlTmp = "select a.ID from sett_Account a,sett_accountType b where a.nAccountTypeID=b.id"
							+ " and b.nAccountGroupID = "+ SETTConstant.AccountGroupType.CURRENT;
					sbSQL.append(
						" and (PayAccountID in (" + sqlTmp + ") or ReceiveAccountID in ("+ sqlTmp +"))");
					break;
					//���ڴ����Բ�ѯ���Ͷ��ڴ��ҵ����صĽ��ס�
				case (int) SETTConstant.AccountTransactionType.FIXED_DEPOSIT :
				    sqlTmp = "select a.ID from sett_Account a,sett_accountType b where a.nAccountTypeID=b.id"
						+ " and b.nAccountGroupID = "+ SETTConstant.AccountGroupType.FIXED;
				    sbSQL.append(
							" and (PayAccountID in (" + sqlTmp + ") or ReceiveAccountID in ("+ sqlTmp +"))");
					break;
					//֪ͨ�����Բ�ѯ����֪ͨ���ҵ����صĽ��ס�
				case (int) SETTConstant.AccountTransactionType.NOTIFY_DEPOSIT :
				    sqlTmp = "select a.ID from sett_Account a,sett_accountType b where a.nAccountTypeID=b.id"
						+ " and b.nAccountGroupID = "+ SETTConstant.AccountGroupType.NOTIFY;
				    sbSQL.append(
							" and (PayAccountID in (" + sqlTmp + ") or ReceiveAccountID in ("+ sqlTmp +"))");
					break;
					//���д�����Բ�ѯ�������д���ҵ����صĽ��ס�
				case (int) SETTConstant.AccountTransactionType.TRUST_LOAN :
				    sqlTmp = "select a.ID from sett_Account a,sett_accountType b where a.nAccountTypeID=b.id"
						+ " and b.nAccountGroupID = "+ SETTConstant.AccountGroupType.TRUST;
				    sbSQL.append(
							" and (PayAccountID in (" + sqlTmp + ") or ReceiveAccountID in ("+ sqlTmp +"))");
					break;
					//ί�д�����Բ�ѯ����ί�д���ҵ����صĽ��ס�
				case (int) SETTConstant.AccountTransactionType.CONSIGN_LOAN :
				    sqlTmp = "select a.ID from sett_Account a,sett_accountType b where a.nAccountTypeID=b.id"
						+ " and b.nAccountGroupID = "+ SETTConstant.AccountGroupType.CONSIGN;
				    sbSQL.append(
							" and (PayAccountID in (" + sqlTmp + ") or ReceiveAccountID in ("+ sqlTmp +"))");
					break;
					//���֣����Բ�ѯ��������ҵ����صĽ��ס�
				case (int) SETTConstant.AccountTransactionType.DISCOUNT_LOAN :
				    sqlTmp = "select a.ID from sett_Account a,sett_accountType b where a.nAccountTypeID=b.id"
						+ " and b.nAccountGroupID = "+ SETTConstant.AccountGroupType.DISCOUNT;
				    sbSQL.append(
							" and (PayAccountID in (" + sqlTmp + ") or ReceiveAccountID in ("+ sqlTmp +"))");
					break;
					//���ڴ�����Բ�ѯ���Ͷ��ڴ���ҵ����صĽ��ס�
					//ѭ��������Բ�ѯ����ѭ������ҵ����صĽ��ס�
					//ί��ҵ�񣺿��Բ�ѯ��ί��ҵ��˵������еĽ��ס�
					//���⸶����Բ�ѯ���и��֧Ʊ����ֽ𸶿Ʊ�㸶��ҵ��
				case (int) SETTConstant.AccountTransactionType.OUT_PAYMENT :
					sbSQL.append(
						" and TransactionTypeID in ("
							+ SETTConstant.TransactionType.BANKPAY
							+ ","
							+ SETTConstant.TransactionType.DRAFTPAY
							+ ","
							+ SETTConstant.TransactionType.CASHPAY
							+ ","
							+ SETTConstant.TransactionType.CHECKPAY
							+ ")");
					break;
			}
		}
		else
		{
			if (info.getTransactionTypeIDs().length() > 0) //add by rxie for query TransSpecialOperation subTrans
				sbSQL.append(" and ( TransactionTypeID in (" + info.getTransactionTypeIDs() + ") or OperationTypeID in (" + info.getTransactionTypeIDs() + ") )");
		}


		if (info.getBankID() > 0)
			sbSQL.append(" and BankID=" + info.getBankID() + "");			
		if (info.getPayAccountIDStart() > 0 && (info.getPayAccountNoStart() == null || info.getPayAccountNoStart().trim().length()<=0))
			sbSQL.append(" and PayaccountID>=" + info.getPayAccountIDStart() + "");
		if (info.getPayAccountIDEnd() > 0 && (info.getPayAccountNoEnd() == null || info.getPayAccountNoEnd().trim().length()<=0))
			sbSQL.append(" and PayaccountID<=" + info.getPayAccountIDEnd() + "");
		if (info.getReceiveAccountIDStart() > 0 && (info.getReceiveAccountNoStart() == null || info.getReceiveAccountNoStart().trim().length()<=0))
			sbSQL.append(" and ReceiveAccountID>=" + info.getReceiveAccountIDStart() + "");
		if (info.getReceiveAccountIDEnd() > 0 && (info.getReceiveAccountNoEnd() == null || info.getReceiveAccountNoEnd().trim().length()<=0))
			sbSQL.append(" and ReceiveAccountID<=" + info.getReceiveAccountIDEnd() + "");

		if (info.getContractID() > 0)
			sbSQL.append(" and ContractID=" + info.getContractID() + "");
		if (info.getPayFormID() > 0)
			sbSQL.append(" and LoanFormID=" + info.getPayFormID() + "");
		if (info.getPayAmountStart() > 0.0)
			sbSQL.append(" and PayAmount>=" + info.getPayAmountStart() + "");
		if (info.getPayAmountEnd() > 0.0)
			sbSQL.append(" and PayAmount<=" + info.getPayAmountEnd() + "");
		if (info.getReceiveAmountStart() > 0.0)
			sbSQL.append(" and ReceiveAmount>=" + info.getReceiveAmountStart() + "");
		if (info.getReceiveAmountEnd() > 0.0)
			sbSQL.append(" and ReceiveAmount<=" + info.getReceiveAmountEnd() + "");
		if (info.getQueryType() == 100)
		{
			//���˻�����ѯ���룬�߼��ǣ��ո��ʻ��ǡ��򡱵Ĺ�ϵ
			if ((info.getPayAccountNoStart() != null && info.getPayAccountNoStart().trim().length() > 0)
				&& (info.getReceiveAccountNoStart() != null && info.getReceiveAccountNoStart().trim().length() > 0))
			{
				sbSQL.append(" and (PayAccountNo='" + info.getPayAccountNoStart() + "' or ReceiveAccountNo='" + info.getReceiveAccountNoStart() + "')");
			}
		}
		else
		{
			if (info.getPayClientNoStart() != null && info.getPayClientNoStart().trim().length() > 0
					&& (info.getPayAccountNoStart() == null || info.getPayAccountNoStart().trim().length()<=0))
				sbSQL.append(" and PayClientCode>='" + info.getPayClientNoStart() + "'");
			if (info.getPayClientNoEnd() != null && info.getPayClientNoEnd().trim().length() > 0
					&& (info.getPayAccountNoEnd() == null || info.getPayAccountNoEnd().trim().length()<=0))
				sbSQL.append(" and PayClientCode<='" + info.getPayClientNoEnd() + "'");
			if (info.getPayAccountNoStart() != null && info.getPayAccountNoStart().trim().length() > 0)
				sbSQL.append(" and PayAccountNo>='" + info.getPayAccountNoStart() + "'");
			if (info.getPayAccountNoEnd() != null && info.getPayAccountNoEnd().trim().length() > 0)
				sbSQL.append(" and PayAccountNo<='" + info.getPayAccountNoEnd() + "'");
			if (info.getReceiveClientNoStart() != null && info.getReceiveClientNoStart().trim().length() > 0
					&& (info.getReceiveAccountNoStart() == null || info.getReceiveAccountNoStart().trim().length()<=0))
				sbSQL.append(" and ReceiveClientCode>='" + info.getReceiveClientNoStart() + "'");
			if (info.getReceiveClientNoEnd() != null && info.getReceiveClientNoEnd().trim().length() > 0
					&& (info.getReceiveAccountNoEnd() == null || info.getReceiveAccountNoEnd().trim().length()<=0))
				sbSQL.append(" and ReceiveClientCode<='" + info.getReceiveClientNoEnd() + "'");
			if (info.getReceiveAccountNoStart() != null && info.getReceiveAccountNoStart().trim().length() > 0)
				sbSQL.append(" and ReceiveAccountNo>='" + info.getReceiveAccountNoStart() + "'");
			if (info.getReceiveAccountNoEnd() != null && info.getReceiveAccountNoEnd().trim().length() > 0)
				sbSQL.append(" and ReceiveAccountNo<='" + info.getReceiveAccountNoEnd() + "'");
		}
		if (info.getTransNoStart() != null && info.getTransNoStart().trim().length() > 0)
			sbSQL.append(" and TransNo>='" + info.getTransNoStart() + "'");
		if (info.getTransNoEnd() != null && info.getTransNoEnd().trim().length() > 0)
			sbSQL.append(" and TransNo<='" + info.getTransNoEnd() + "'");
		if (info.getDepositNo() != null && info.getDepositNo().trim().length() > 0)
			sbSQL.append(" and DepositNo='" + info.getDepositNo() + "'");
		if (info.getInterestStartStart() != null)
			sbSQL.append(" and InterestStart>=to_date('" + DataFormat.getDateString(info.getInterestStartStart()) + "','yyyy-mm-dd')");
		if (info.getInterestStartEnd() != null)
			sbSQL.append(" and InterestStart<=to_date('" + DataFormat.getDateString(info.getInterestStartEnd()) + "','yyyy-mm-dd')");
		
		if (info.getExecuteStart() != null)
			sbSQL.append(" and Execute>=to_date('" + DataFormat.getDateString(info.getExecuteStart()) + "','yyyy-mm-dd')");
		if (info.getExecuteEnd() != null)
		{
			sbSQL.append(" and Execute<=to_date('" + DataFormat.getDateString(info.getExecuteEnd()) + "','yyyy-mm-dd')");
		}
		else
		{
			sbSQL.append(" and Execute<to_date('" + DataFormat.getDateString(info.getSystemDate()) + "','yyyy-mm-dd')");
		}
		if (info.getInputuserID() > 0)
			sbSQL.append(" and InputuserID=" + info.getInputuserID() + "");
		
		System.out.println("$$$$$$$$$$QTransaction$$$$$$$$$"+sbSQL.toString());
		
		return sbSQL.toString();
	}
}
