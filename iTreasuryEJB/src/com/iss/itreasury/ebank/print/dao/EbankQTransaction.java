/*
 * Created on 2006-12-12
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.ebank.print.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;

import com.iss.itreasury.evoucher.util.VOUConstant;
import com.iss.itreasury.settlement.query.paraminfo.QueryTransactionConditionInfo;
import com.iss.itreasury.settlement.query.resultinfo.QUpGatherAccountDetailInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.system.dao.PageLoader;

/**
 * @author boxu
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class EbankQTransaction
{
	StringBuffer m_sbSelect = null;
	StringBuffer m_sbFrom = null;
	StringBuffer m_sbWhere = null;
	StringBuffer m_sbOrderBy = null;
	
	/**
	 * ������ѯSQL
	 * @param info
	 */
	public void getTransactionSQL(QueryTransactionConditionInfo info,long lUserID)
	{
		m_sbSelect = new StringBuffer();
		//select 
		m_sbSelect.append(" ID,SerialNo,OfficeID,CurrencyID,TransNo, \n");
		m_sbSelect.append(" TransactionTypeID,operationtypeid,InterestStart,Execute,StatusID,InputuserID, \n");
		m_sbSelect.append(" CheckuserID,Abstract,PayclientID,PayaccountID,PayAmount, \n");
		m_sbSelect.append(" ReceiveAmount,ReceiveClientID,ReceiveAccountID,BankID,ContractID, \n");
		m_sbSelect.append(" LoanFormID,DepositNo,PayAccountNo,PayAccountName,ReceiveAccountNo, \n");
		m_sbSelect.append(" ReceiveAccountName,PayClientCode,PayClientName,ReceiveClientCode,ReceiveClientName,CommitionAccountCode \n");
		//from 
		m_sbFrom = new StringBuffer();
		
		//���������� Boxu 2007-1-5 ������ͼ sett_vtransaction ����  "SINSTRUCTIONNO ��ʶ�ǽ���ϵͳ��������ˮ��"
		//�޸���ǰ���߼�,�ɲ�ѯ����ֻҪ��"��"��"��"��Ϊ����λ��ҵ��
		m_sbFrom.append(" sett_vtransaction \n");
		
		//where 
		m_sbWhere = new StringBuffer();
		//m_sbWhere.append(" StatusID>0 ");
		
		if (info.getQueryType() == 200)	
		{
			//��ҳ���ϵ�ѡ�� ��ѯ��ɾ�����ڿ�����֪ͨ�������� ��ť����ʱ�ߴ˷�֧
			m_sbWhere.append(" StatusID =0 ");
			m_sbWhere.append(" and TransactionTypeID in ( "
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
			m_sbWhere.append(" StatusID > 0 ");
			m_sbWhere.append(" and (PayAccountNo in ");
			m_sbWhere.append(" (select a.saccountno ");
			m_sbWhere.append(" from OB_AccountOwnedByUserQuery a, Sett_Account ai ");
			m_sbWhere.append(" where ai.nStatusID=1 ");
			m_sbWhere.append(" and a.saccountno=ai.saccountno ");
			m_sbWhere.append(" and a.nUserID="+lUserID);
			m_sbWhere.append(" and a.nclientid="+info.getClientId());
			m_sbWhere.append(" and ai.ncurrencyid="+info.getCurrencyID()+")");
			m_sbWhere.append(" or ReceiveAccountNo in ");
			m_sbWhere.append(" (select a.saccountno ");
			m_sbWhere.append(" from OB_AccountOwnedByUserQuery a, Sett_Account ai ");
			m_sbWhere.append(" where ai.nStatusID=1 ");
			m_sbWhere.append(" and a.saccountno=ai.saccountno ");
			m_sbWhere.append(" and a.nUserID="+lUserID);
			m_sbWhere.append(" and a.nclientid="+info.getClientId());
			m_sbWhere.append(" and ai.ncurrencyid="+info.getCurrencyID()+")");
			m_sbWhere.append(" or CommitionAccountCode in ");
			m_sbWhere.append(" (select a.saccountno ");
			m_sbWhere.append(" from OB_AccountOwnedByUserQuery a, Sett_Account ai ");
			m_sbWhere.append(" where ai.nStatusID=1 ");
			m_sbWhere.append(" and a.saccountno=ai.saccountno ");
			m_sbWhere.append(" and a.nclientid="+info.getClientId());
			m_sbWhere.append(" and a.nUserID="+lUserID);
			m_sbWhere.append(" and ai.ncurrencyid="+info.getCurrencyID()+")");
			m_sbWhere.append(" )");
			

			//Boxu add 2007-8-9 �޸� ����ѯ3��״̬ 1.��ɾ�� 2.���ݴ� 3.�Ѿܾ�
	    	m_sbWhere.append(" and StatusID not in("+SETTConstant.TransactionStatus.DELETED+","
	    											+SETTConstant.TransactionStatus.TEMPSAVE+","
	    											+SETTConstant.TransactionStatus.REFUSE+") ");
		}
		
		//���������� Boxu 2007-1-5 ֻ��ѯ������������
		//m_sbWhere.append(" and SINSTRUCTIONNO is not null ");
		
    	//���Ϊ�յĲ��ܲ����
    	m_sbWhere.append(" and transno is not null ");
		
		//���������� Boxu 2006-12-12 ���ų�Ա��λ��ʶ
		m_sbWhere.append(" and ( PayClientID="+info.getPayClientIDStart()+" or ReceiveClientID = "+info.getReceiveClientIDStart()+" ) ");
		
		if (info.getOfficeID() > 0)
			m_sbWhere.append(" and OfficeID=" + info.getOfficeID() + "");
		if (info.getCurrencyID() > 0)
			m_sbWhere.append(" and CurrencyID=" + info.getCurrencyID() + "");
		
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
					//���ڴ�����Բ�ѯ���Ͷ��ڴ���ҵ����صĽ��ס�
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
			if (info.getTransactionTypeIDs().length() > 0) //add by rxie for query TransSpecialOperation subTrans
				m_sbWhere.append(" and ( TransactionTypeID in (" + info.getTransactionTypeIDs() + ") or OperationTypeID in (" + info.getTransactionTypeIDs() + ") )");
		}

//		if (info.getEyeCheckQuery() == 1) 
//		{
//			// Ŀ�Ӹ��ϲ�ѯ: ������ == ��¼�� ���� ¼���� != ��¼��
//			// InputuserID ,CheckuserID,
//			m_sbWhere.append(" and ((CheckuserID ='" + info.getUserID()
//					+ "' and StatusID ='3') or (InputuserID !='"
//					+ info.getUserID() + "' and StatusID ='2'))");
//		}
//		else
//		{	
//			//����Ŀ�Ӹ��ϲ�ѯ
//			if (info.getInputuserID() > 0)
//				m_sbWhere.append(" and InputuserID=" + info.getInputuserID() + "");
//			if (info.getCheckuserID() > 0)
//				m_sbWhere.append(" and CheckuserID=" + info.getCheckuserID() + "");			
//		}
//		

		//if (info.getStatusID() > 0)
		//	m_sbWhere.append(" and StatusID=" + info.getStatusID() + "");
		if (info.getBankID() > 0)
			m_sbWhere.append(" and BankID=" + info.getBankID() + "");			
		//if (info.getPayClientIDStart() > 0 && (info.getPayClientNoStart() == null || info.getPayClientNoStart().trim().length()<=0))
		//	m_sbWhere.append(" and PayclientID>=" + info.getPayClientIDStart() + "");
		//if (info.getPayClientIDEnd() > 0 && (info.getPayClientNoEnd() == null || info.getPayClientNoEnd().trim().length()<=0))
		//	m_sbWhere.append(" and PayclientID<=" + info.getPayClientIDEnd() + "");
		if (info.getPayAccountIDStart() > 0 && (info.getPayAccountNoStart() == null || info.getPayAccountNoStart().trim().length()<=0))
			m_sbWhere.append(" and PayaccountID>=" + info.getPayAccountIDStart() + "");
		if (info.getPayAccountIDEnd() > 0 && (info.getPayAccountNoEnd() == null || info.getPayAccountNoEnd().trim().length()<=0))
			m_sbWhere.append(" and PayaccountID<=" + info.getPayAccountIDEnd() + "");
		//if (info.getReceiveClientIDStart() > 0 && (info.getReceiveClientNoStart() == null || info.getReceiveClientNoStart().trim().length()<=0))
		//	m_sbWhere.append(" and ReceiveClientID>=" + info.getReceiveClientIDStart() + "");
		//if (info.getReceiveClientIDEnd() > 0 && (info.getReceiveClientNoEnd() == null || info.getReceiveClientNoEnd().trim().length()<=0))
		//	m_sbWhere.append(" and ReceiveClientID<=" + info.getReceiveClientIDEnd() + "");
		if (info.getReceiveAccountIDStart() > 0 && (info.getReceiveAccountNoStart() == null || info.getReceiveAccountNoStart().trim().length()<=0))
			m_sbWhere.append(" and ReceiveAccountID>=" + info.getReceiveAccountIDStart() + "");
		if (info.getReceiveAccountIDEnd() > 0 && (info.getReceiveAccountNoEnd() == null || info.getReceiveAccountNoEnd().trim().length()<=0))
			m_sbWhere.append(" and ReceiveAccountID<=" + info.getReceiveAccountIDEnd() + "");

		if (info.getContractID() > 0)
			m_sbWhere.append(" and ContractID=" + info.getContractID() + "");
		if (info.getPayFormID() > 0)
			m_sbWhere.append(" and LoanFormID=" + info.getPayFormID() + "");
		if (info.getPayAmountStart() > 0.0)
			m_sbWhere.append(" and PayAmount>=" + info.getPayAmountStart() + "");
		if (info.getPayAmountEnd() > 0.0)
			m_sbWhere.append(" and PayAmount<=" + info.getPayAmountEnd() + "");
		if (info.getReceiveAmountStart() > 0.0)
			m_sbWhere.append(" and ReceiveAmount>=" + info.getReceiveAmountStart() + "");
		if (info.getReceiveAmountEnd() > 0.0)
			m_sbWhere.append(" and ReceiveAmount<=" + info.getReceiveAmountEnd() + "");
		if (info.getQueryType() == 100)
		{
			//���˻�����ѯ���룬�߼��ǣ��ո��ʻ��ǡ��򡱵Ĺ�ϵ
			if ((info.getPayAccountNoStart() != null && info.getPayAccountNoStart().trim().length() > 0)
				&& (info.getReceiveAccountNoStart() != null && info.getReceiveAccountNoStart().trim().length() > 0))
			{
				m_sbWhere.append(" and (PayAccountNo='" + info.getPayAccountNoStart() + "' or ReceiveAccountNo='" + info.getReceiveAccountNoStart() + "')");
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
		}
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
		{
			m_sbWhere.append(" and Execute<=to_date('" + DataFormat.getDateString(info.getExecuteEnd()) + "','yyyy-mm-dd')");
		}
		else
		{
			m_sbWhere.append(" and Execute<to_date('" + DataFormat.getDateString(info.getSystemDate()) + "','yyyy-mm-dd')");
		}
		if (info.getInputuserID() > 0)
			m_sbWhere.append(" and InputuserID=" + info.getInputuserID() + "");
		m_sbOrderBy = new StringBuffer();
		String strDesc = info.getDESC() == 1 ? " desc " : "";
		switch ((int) info.getOrderID())
		{
			default :
				m_sbOrderBy.append(" order by Execute, TransNo" + strDesc + " \n");
				break;
		}
		
		System.out.println("$$$$$$$$$$QTransaction$$$$$$$$$ select"+m_sbSelect.toString() + " from "+m_sbFrom.toString()+" where "+m_sbWhere.toString() +"$$$$");
	}
	
	/**
	 * 
	 * @param qaci
	 * @return
	 * @throws Exception
	 */
	public PageLoader queryTransactionInfo(QueryTransactionConditionInfo info,long lUserID) throws Exception
	{
		getTransactionSQL(info,lUserID);

		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		pageLoader.initPageLoader(
			new AppContext(),
			m_sbFrom.toString(),
			m_sbSelect.toString(),
			m_sbWhere.toString(),
			(int) Constant.PageControl.CODE_PAGELINECOUNT,
			"com.iss.itreasury.settlement.query.resultinfo.QueryTransactionInfo",
			null);
		pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
		return pageLoader;
	}
	
	/**
	 * ��ӡ����
	 * @param qaci
	 * @return
	 * @throws Exception
	 */
	public PageLoader queryTransactionInfoApply(QueryTransactionConditionInfo info,long lUserID) throws Exception
	{
		getTransactionApplySQL(info,lUserID);

		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		pageLoader.initPageLoader(
			new AppContext(),
			m_sbFrom.toString(),
			m_sbSelect.toString(),
			m_sbWhere.toString(),
			(int) Constant.PageControl.CODE_PAGELINECOUNT,
			"com.iss.itreasury.settlement.query.resultinfo.QueryTransactionInfo",
			null);
		pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
		return pageLoader;
	}
	
	/**
	 * ��ӡ����
	 * ������ѯSQL
	 * @param info
	 */
	public void getTransactionApplySQL(QueryTransactionConditionInfo info,long lUserID)
	{
		m_sbSelect = new StringBuffer();
		
		//select 
		m_sbSelect.append(" a.ID,a.SerialNo,a.OfficeID,a.CurrencyID,a.TransNo, ");
		m_sbSelect.append(" a.TransactionTypeID,a.InterestStart,a.Execute,a.StatusID,a.InputuserID, ");
		m_sbSelect.append(" a.CheckuserID,a.Abstract,a.PayclientID,a.PayaccountID,a.PayAmount, ");
		m_sbSelect.append(" a.ReceiveAmount,a.ReceiveClientID,a.ReceiveAccountID,a.BankID,a.ContractID, ");
		m_sbSelect.append(" a.LoanFormID,a.DepositNo,a.PayAccountNo,a.PayAccountName,a.ReceiveAccountNo, ");
		m_sbSelect.append(" a.ReceiveAccountName,a.PayClientCode,a.PayClientName,a.ReceiveClientCode,a.ReceiveClientName ");
		
		//from 
		m_sbFrom = new StringBuffer();
		
		//���������� Boxu 2007-1-5 ������ͼ sett_vtransaction ����  "SINSTRUCTIONNO ��ʶ�ǽ���ϵͳ��������ˮ��"
		//�޸���ǰ���߼�,�ɲ�ѯ����ֻҪ��"��"��"��"��Ϊ����λ��ҵ��
		m_sbFrom.append(" sett_vtransaction a, print_printrecord b, print_billrelation c ");
		
		//where  
		m_sbWhere = new StringBuffer();
		
		if (info.getQueryType() == 200)		
		{
			//��ҳ���ϵ�ѡ�� ��ѯ��ɾ�����ڿ�����֪ͨ�������� ��ť����ʱ�ߴ˷�֧
			m_sbWhere.append(" StatusID =0 ");
			m_sbWhere.append(" and TransactionTypeID in ( "
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
			m_sbWhere.append(" a.StatusID>0 ");
			m_sbWhere.append(" and (a.PayAccountNo in ");
			m_sbWhere.append(" (select a.saccountno ");
			m_sbWhere.append(" from OB_AccountOwnedByUserQuery a, Sett_Account ai ");
			m_sbWhere.append(" where ai.nStatusID=1 ");
			m_sbWhere.append(" and a.saccountno=ai.saccountno ");
			m_sbWhere.append(" and a.nUserID="+lUserID);
			m_sbWhere.append(" and a.nclientid="+info.getClientId());
			m_sbWhere.append(" and ai.ncurrencyid="+info.getCurrencyID()+")");
			m_sbWhere.append(" or a.ReceiveAccountNo in ");
			m_sbWhere.append(" (select a.saccountno ");
			m_sbWhere.append(" from OB_AccountOwnedByUserQuery a, Sett_Account ai ");
			m_sbWhere.append(" where ai.nStatusID=1 ");
			m_sbWhere.append(" and a.saccountno=ai.saccountno ");
			m_sbWhere.append(" and a.nUserID="+lUserID);
			m_sbWhere.append(" and a.nclientid="+info.getClientId());
			m_sbWhere.append(" and ai.ncurrencyid="+info.getCurrencyID()+"))");
			
			//Boxu add 2007-8-9 �޸� ����Ҫ��3��״̬  1.��ɾ�� 2.���ݴ� 3.�Ѿܾ�
			m_sbWhere.append(" and (a.statusid  not  in( "+SETTConstant.TransactionStatus.DELETED+","
	    												  +SETTConstant.TransactionStatus.TEMPSAVE+","
	    												  +SETTConstant.TransactionStatus.REFUSE+")) ");

			//m_sbWhere.append(" and a.StatusID in ( "+SETTConstant.TransactionStatus.SAVE+","+SETTConstant.TransactionStatus.CHECK+","+SETTConstant.TransactionStatus.APPROVAL+","+SETTConstant.TransactionStatus.APPROVALING+" ) ");
		}
		
		//���������� Boxu 2007-1-5 ֻ��ѯ������������
		//m_sbWhere.append(" and a.SINSTRUCTIONNO is not null ");
		
		//���������� Boxu 2006-12-12 ���ų�Ա��λ��ʶ
		m_sbWhere.append(" and ( a.PayClientID="+info.getPayClientIDStart()+" or a.ReceiveClientID = "+info.getReceiveClientIDStart()+" ) ");
		
		if (info.getOfficeID() > 0)
			m_sbWhere.append(" and a.OfficeID=" + info.getOfficeID() + "");
		if (info.getCurrencyID() > 0)
			m_sbWhere.append(" and a.CurrencyID=" + info.getCurrencyID() + "");
		
		//ѡ�����ʻ��������ͺ󣬽������ͱ���Ч
		if (info.getAccountTransTypeID() > 0)
		{
		    String sqlTmp = "";
			switch ((int) info.getAccountTransTypeID())
			{
				//���ڴ����Բ�ѯ������ʹ�û��ڴ���ʻ��Ľ���
				case (int) SETTConstant.AccountTransactionType.CURRENT_DEPOSIT :
				    sqlTmp = " select a.ID from sett_Account a,sett_accountType b where a.nAccountTypeID=b.id "
						   + " and b.nAccountGroupID = "+ SETTConstant.AccountGroupType.CURRENT;
					m_sbWhere.append(" and (PayAccountID in (" + sqlTmp + ") or ReceiveAccountID in ("+ sqlTmp +"))");
					break;
					
				//���ڴ����Բ�ѯ���Ͷ��ڴ��ҵ����صĽ��ס�
				case (int) SETTConstant.AccountTransactionType.FIXED_DEPOSIT :
				    sqlTmp = " select a.ID from sett_Account a,sett_accountType b where a.nAccountTypeID=b.id "
						   + " and b.nAccountGroupID = "+ SETTConstant.AccountGroupType.FIXED;
				    m_sbWhere.append(" and (PayAccountID in (" + sqlTmp + ") or ReceiveAccountID in ("+ sqlTmp +"))");
					break;
				
				//֪ͨ�����Բ�ѯ����֪ͨ���ҵ����صĽ��ס�
				case (int) SETTConstant.AccountTransactionType.NOTIFY_DEPOSIT :
				    sqlTmp = " select a.ID from sett_Account a,sett_accountType b where a.nAccountTypeID=b.id "
						   + " and b.nAccountGroupID = "+ SETTConstant.AccountGroupType.NOTIFY;
				    m_sbWhere.append(" and (PayAccountID in (" + sqlTmp + ") or ReceiveAccountID in ("+ sqlTmp +"))");
					break;

				//���д�����Բ�ѯ�������д���ҵ����صĽ��ס�
				case (int) SETTConstant.AccountTransactionType.TRUST_LOAN :
				    sqlTmp = " select a.ID from sett_Account a,sett_accountType b where a.nAccountTypeID=b.id "
						   + " and b.nAccountGroupID = "+ SETTConstant.AccountGroupType.TRUST;
				    m_sbWhere.append(" and (PayAccountID in (" + sqlTmp + ") or ReceiveAccountID in ("+ sqlTmp +"))");
					break;
				
				//ί�д�����Բ�ѯ����ί�д���ҵ����صĽ��ס�
				case (int) SETTConstant.AccountTransactionType.CONSIGN_LOAN :
				    sqlTmp = " select a.ID from sett_Account a,sett_accountType b where a.nAccountTypeID=b.id "
						   + " and b.nAccountGroupID = "+ SETTConstant.AccountGroupType.CONSIGN;
				    m_sbWhere.append(" and (PayAccountID in (" + sqlTmp + ") or ReceiveAccountID in ("+ sqlTmp +"))");
					break;
					
				//���֣����Բ�ѯ��������ҵ����صĽ��ס�
				case (int) SETTConstant.AccountTransactionType.DISCOUNT_LOAN :
				    sqlTmp = " select a.ID from sett_Account a,sett_accountType b where a.nAccountTypeID=b.id "
						   + " and b.nAccountGroupID = "+ SETTConstant.AccountGroupType.DISCOUNT;
				    m_sbWhere.append(" and (PayAccountID in (" + sqlTmp + ") or ReceiveAccountID in ("+ sqlTmp +"))");
					break;
					
				//���ڴ�����Բ�ѯ���Ͷ��ڴ���ҵ����صĽ��ס�
				//ѭ��������Բ�ѯ����ѭ������ҵ����صĽ��ס�
				//ί��ҵ�񣺿��Բ�ѯ��ί��ҵ��˵������еĽ��ס�
				//���⸶����Բ�ѯ���и��֧Ʊ����ֽ𸶿Ʊ�㸶��ҵ��
				case (int) SETTConstant.AccountTransactionType.OUT_PAYMENT :
					m_sbWhere.append(" and TransactionTypeID in ( "
							+ SETTConstant.TransactionType.BANKPAY
							+ ","
							+ SETTConstant.TransactionType.DRAFTPAY
							+ ","
							+ SETTConstant.TransactionType.CASHPAY
							+ ","
							+ SETTConstant.TransactionType.CHECKPAY
							+ " ) ");
					break;
			}
		}
		else
		{
			//add by rxie for query TransSpecialOperation subTrans
			if (info.getTransactionTypeIDs().length() > 0)  
			{
				m_sbWhere.append(" and ( TransactionTypeID in (" + info.getTransactionTypeIDs() + ") or OperationTypeID in (" + info.getTransactionTypeIDs() + ") )");
			}
		}

//		if (info.getEyeCheckQuery() == 1) 
//		{
//			// Ŀ�Ӹ��ϲ�ѯ: ������ == ��¼�� ���� ¼���� != ��¼��
//			// InputuserID ,CheckuserID,
//			m_sbWhere.append(" and ((CheckuserID ='" + info.getUserID()
//					+ "' and StatusID ='3') or (InputuserID !='"
//					+ info.getUserID() + "' and StatusID ='2'))");
//		}
//		else
//		{	
//			//����Ŀ�Ӹ��ϲ�ѯ
//			if (info.getInputuserID() > 0)
//				m_sbWhere.append(" and InputuserID=" + info.getInputuserID() + "");
//			if (info.getCheckuserID() > 0)
//				m_sbWhere.append(" and CheckuserID=" + info.getCheckuserID() + "");			
//		}

		if (info.getBankID() > 0)
			m_sbWhere.append(" and BankID=" + info.getBankID() + "");			
		if (info.getPayAccountIDStart() > 0 && (info.getPayAccountNoStart() == null || info.getPayAccountNoStart().trim().length()<=0))
			m_sbWhere.append(" and PayaccountID>=" + info.getPayAccountIDStart() + "");
		if (info.getPayAccountIDEnd() > 0 && (info.getPayAccountNoEnd() == null || info.getPayAccountNoEnd().trim().length()<=0))
			m_sbWhere.append(" and PayaccountID<=" + info.getPayAccountIDEnd() + "");
		if (info.getReceiveAccountIDStart() > 0 && (info.getReceiveAccountNoStart() == null || info.getReceiveAccountNoStart().trim().length()<=0))
			m_sbWhere.append(" and ReceiveAccountID>=" + info.getReceiveAccountIDStart() + "");
		if (info.getReceiveAccountIDEnd() > 0 && (info.getReceiveAccountNoEnd() == null || info.getReceiveAccountNoEnd().trim().length()<=0))
			m_sbWhere.append(" and ReceiveAccountID<=" + info.getReceiveAccountIDEnd() + "");
		if (info.getContractID() > 0)
			m_sbWhere.append(" and ContractID=" + info.getContractID() + "");
		if (info.getPayFormID() > 0)
			m_sbWhere.append(" and LoanFormID=" + info.getPayFormID() + "");
		if (info.getPayAmountStart() > 0.0)
			m_sbWhere.append(" and PayAmount>=" + info.getPayAmountStart() + "");
		if (info.getPayAmountEnd() > 0.0)
			m_sbWhere.append(" and PayAmount<=" + info.getPayAmountEnd() + "");
		if (info.getReceiveAmountStart() > 0.0)
			m_sbWhere.append(" and ReceiveAmount>=" + info.getReceiveAmountStart() + "");
		if (info.getReceiveAmountEnd() > 0.0)
			m_sbWhere.append(" and ReceiveAmount<=" + info.getReceiveAmountEnd() + "");
		
		if (info.getQueryType() == 100)
		{
			//���˻�����ѯ���룬�߼��ǣ��ո��ʻ��ǡ��򡱵Ĺ�ϵ
			if ((info.getPayAccountNoStart() != null && info.getPayAccountNoStart().trim().length() > 0)
				&& (info.getReceiveAccountNoStart() != null && info.getReceiveAccountNoStart().trim().length() > 0))
			{
				m_sbWhere.append(" and (PayAccountNo='" + info.getPayAccountNoStart() + "' or ReceiveAccountNo='" + info.getReceiveAccountNoStart() + "')");
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
		}
		
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
			m_sbWhere.append(" and a.Execute>=to_date('" + DataFormat.getDateString(info.getExecuteStart()) + "','yyyy-mm-dd')");
		if (info.getExecuteEnd() != null)
		{
			m_sbWhere.append(" and a.Execute<=to_date('" + DataFormat.getDateString(info.getExecuteEnd()) + "','yyyy-mm-dd')");
		}
		else
		{
			m_sbWhere.append(" and a.Execute<to_date('" + DataFormat.getDateString(info.getSystemDate()) + "','yyyy-mm-dd')");
		}
		if (info.getInputuserID() > 0)
			m_sbWhere.append(" and InputuserID=" + info.getInputuserID() + "");
		
		//�¼ӹ�����ӡ����
		m_sbWhere.append(" and a.transactiontypeid=c.ntransactiontypeid and b.ntransactiontypeid=c.ntransactiontypeid and b.stempname = c.stempname and b.nmoduleid=c.nmoduleid  and  b.nprintnum>=c.nmaxprint and a.id=b.nprintcontentid ");
		m_sbWhere.append(" and b.ndeptid = c.ndeptid " );
		
		//�¼ӿ��Ƿ��Ѿ��������� add by zyyao 2007-8-6
		//boxu update 2007-8-15 ���˵��������д���δ���������
		m_sbWhere.append(" and a.id not in ( select distinct d.nprintcontentid from ebank_printapply d where d.nstatusid in ("+VOUConstant.VoucherStatus.SAVE+" ,"+VOUConstant.VoucherStatus.APPROVED+") and d.nofficeid = "+info.getOfficeID()+" and d.ncurrency = "+info.getCurrencyID()+" and d.ndeptid = "+VOUConstant.PrintSection.EBANKCUSTOMER+" ) ");
		
		m_sbWhere.append(" and c.ndeptid = "+VOUConstant.PrintSection.EBANKCUSTOMER+" and b.ndeptid = "+VOUConstant.PrintSection.EBANKCUSTOMER+" ");
		//end
		m_sbWhere.append(" group by a.ID,a.SerialNo,a.OfficeID,a.CurrencyID,a.TransNo,a.TransactionTypeID,a.InterestStart,a.Execute,a.StatusID,a.InputuserID, a.CheckuserID,a.Abstract,a.PayclientID,a.PayaccountID,a.PayAmount, ");
		m_sbWhere.append(" a.ReceiveAmount,a.ReceiveClientID,a.ReceiveAccountID,a.BankID,a.ContractID, a.LoanFormID,a.DepositNo,a.PayAccountNo,a.PayAccountName,a.ReceiveAccountNo, a.ReceiveAccountName,a.PayClientCode,a.PayClientName,a.ReceiveClientCode,a.ReceiveClientName ");
		
		m_sbOrderBy = new StringBuffer();
		String strDesc = info.getDESC() == 1 ? " desc " : "";
		
		switch ((int) info.getOrderID())
		{
			default :
				m_sbOrderBy.append(" order by TransNo" + strDesc + " \n");
				break;
		}
		
		System.out.println("$$$$$$$$$$QTransaction$$$$$$$$$ select"+m_sbSelect.toString() +" from "+m_sbFrom.toString()+" where "+m_sbWhere.toString() +"$$$$");
	}
}
