package com.iss.itreasury.settlement.query.Dao;

import com.iss.itreasury.settlement.query.paraminfo.QueryTransInterestConditionInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.DataFormat;

/**
 * 账户查询数据层
 * @author xiang
 *
 */
public class QTransInterestDao {
	
	public String getTransInterestSQL(QueryTransInterestConditionInfo info){
		StringBuffer m_sbSelect,m_sbFrom,m_sbWhere;
		m_sbSelect = new StringBuffer();
		// select 
		m_sbSelect.append(" TransID,TransNo,TransactionTypeID,ExecuteDate, \n");
		m_sbSelect.append(" InterestSettlementDate,AutoExecuteDate,SubAccountID,PayInterestAccountID, \n");
		m_sbSelect.append(" ReceiveInterestAccountID,Interest,InputUserID,CheckUserID,ClientID, \n");
		m_sbSelect.append(" ClientNo,ClientName,AccountTypeID,AccountID, \n");
		m_sbSelect.append(" AccountNo,DepositNo,DepositTerm,ContractID, \n");
		m_sbSelect.append(" ContractNo,PayFormID,PayFormNo,LoanTypeID, \n");
		m_sbSelect.append(" LoanTerm,LoanYear,InterestRatePlanID,OfficeID, \n");
		m_sbSelect.append(" CurrencyID,IsTrans,IsRecord,ConsignClientID, \n");
		m_sbSelect.append(" CLIENTTYPEID1,CLIENTTYPEID2,CLIENTTYPEID3, CLIENTTYPEID4, CLIENTTYPEID5, CLIENTTYPEID6,  \n");
		m_sbSelect.append(" InterestStart,InterestEnd,InterestDays,InterestTypeID, \n");
		m_sbSelect.append(" InterestRate,Capital,InterestTaxRate,Abstract,PayInterestAccountNo,ReceiveInterestAccountNo, sett_TransInterestSettlement.mnegotiateinterest,sett_TransInterestSettlement.mnegotiaterate \n");

		// from 
		m_sbFrom = new StringBuffer();
		m_sbFrom.append(" sett_vTransInterestDetail,client,sett_TransInterestSettlement \n");
		// where 
		m_sbWhere = new StringBuffer();
		m_sbWhere.append(" 1=1  and sett_TransInterestSettlement.id(+) =sett_vTransInterestDetail.TransID ");
		m_sbWhere.append(" and sett_vTransInterestDetail.clientID = client.id(+)");
		if (info.getOfficeID() > 0)
			m_sbWhere.append(" and OfficeID=" + info.getOfficeID() + "");
		if (info.getCurrencyID() > 0)
			m_sbWhere.append(" and CurrencyID=" + info.getCurrencyID() + "");
		if (info.getInterestSettlementType() == 1) //结算交易
			m_sbWhere.append(" and IsTrans=1");
		else if (info.getInterestSettlementType() == 2) //结息记录
			m_sbWhere.append(" and IsRecord=1");
		else if (info.getInterestSettlementType() == 3) //记提利息
			m_sbWhere.append(" and InterestTypeID=" + SETTConstant.InterestFeeType.PREDRAWINTEREST);
		else if (info.getInterestSettlementType() == 4) //冲销记提利息
			m_sbWhere.append(" and InterestTypeID=8");

		String strInterestTypeArray = "";
		if (info.getInterestType() != null && info.getInterestType().length > 0)
		{
			for (int i = 0; i < info.getInterestType().length; i++)
			{
				if (i == 0)
				{
					strInterestTypeArray += info.getInterestType()[i];
				}
				else
				{
					strInterestTypeArray += "," + info.getInterestType()[i];
				}
			}
			m_sbWhere.append(" and InterestTypeID in (" + strInterestTypeArray + ")");
		}
		if (info.getTransNo() != null && info.getTransNo().trim().length() > 0)
			m_sbWhere.append(" and TransNo='" + info.getTransNo() + "'");
		if (info.getAccountNoStart() != null && info.getAccountNoStart().trim().length() > 0)
		{
			m_sbWhere.append(" and AccountNo >='" + info.getAccountNoStart() + "'");
		}
		else
		{
			if (info.getClientNoStart() != null && info.getClientNoStart().trim().length() > 0)
				m_sbWhere.append(" and ClientNo >='" + info.getClientNoStart() + "'");			
		}
		if (info.getAccountNoEnd() != null && info.getAccountNoEnd().trim().length() > 0)
		{
			m_sbWhere.append(" and AccountNo <='" + info.getAccountNoEnd() + "'");
		}
		else
		{
			if (info.getClientNoEnd() != null && info.getClientNoEnd().trim().length() > 0)
				m_sbWhere.append(" and ClientNo <='" + info.getClientNoEnd() + "'");
		}
		//add by 2012-05-18 添加指定账户编号
		if(info.getAppointAccountNo() != null && info.getAppointAccountNo().length() > 0){
			m_sbWhere.append(" and AccountNo in ('"+info.getAppointAccountNo()+"')");
		}
		if (info.getFixedDepositNoStart() != null && info.getFixedDepositNoStart().trim().length() > 0)
			m_sbWhere.append(" and DepositNo >='" + info.getFixedDepositNoStart() + "'");
		if (info.getFixedDepositNoEnd() != null && info.getFixedDepositNoEnd().trim().length() > 0)
			m_sbWhere.append(" and DepositNo <='" + info.getFixedDepositNoEnd() + "'");
		if (info.getNotifyDepositNoStart() != null && info.getNotifyDepositNoStart().trim().length() > 0)
			m_sbWhere.append(" and DepositNo >='" + info.getNotifyDepositNoStart() + "'");
		if (info.getNotifyDepositNoEnd() != null && info.getNotifyDepositNoEnd().trim().length() > 0)
			m_sbWhere.append(" and DepositNo <='" + info.getNotifyDepositNoEnd() + "'");
		if (info.getContractNoStart() != null && info.getContractNoStart().trim().length() > 0)
			m_sbWhere.append(" and ContractNo >='" + info.getContractNoStart() + "'");
		if (info.getContractNoEnd() != null && info.getContractNoEnd().trim().length() > 0)
			m_sbWhere.append(" and ContractNo <='" + info.getContractNoEnd() + "'");
		if (info.getPayFormNoStart() != null && info.getPayFormNoStart().trim().length() > 0)
			m_sbWhere.append(" and PayFormNo >='" + info.getPayFormNoStart() + "'");
		if (info.getPayFormNoEnd() != null && info.getPayFormNoEnd().trim().length() > 0)
			m_sbWhere.append(" and PayFormNo <='" + info.getPayFormNoEnd() + "'");
		if (info.getConsignClientID() > 0)
			m_sbWhere.append(" and ConsignClientID =" + info.getConsignClientID() + "");
		if (info.getEnterpriseTypeID1() > 0)
			m_sbWhere.append("    and   nCLIENTTYPEID1 = " + info.getEnterpriseTypeID1());
		if (info.getEnterpriseTypeID2() > 0)
			m_sbWhere.append("    and   nCLIENTTYPEID2 = " + info.getEnterpriseTypeID2());
		if (info.getEnterpriseTypeID3() > 0)
			m_sbWhere.append("    and   nCLIENTTYPEID3 = " + info.getEnterpriseTypeID3());
		if (info.getEnterpriseTypeID4() > 0)
			m_sbWhere.append("    and   nCLIENTTYPEID4 = " + info.getEnterpriseTypeID4());
		if (info.getEnterpriseTypeID5() > 0)
			m_sbWhere.append("    and   nCLIENTTYPEID5 = " + info.getEnterpriseTypeID5());
		if (info.getEnterpriseTypeID6() > 0)
			m_sbWhere.append("    and   nCLIENTTYPEID6 = " + info.getEnterpriseTypeID6());
		if (info.getAutoExecuteDateStart() != null)
			m_sbWhere.append(" and AutoExecuteDate>=to_date('" + DataFormat.getDateString(info.getAutoExecuteDateStart()) + "','yyyy-mm-dd HH:MI:SS')");
		if (info.getAutoExecuteDateEnd() != null)
			m_sbWhere.append(" and AutoExecuteDate<=to_date('" + DataFormat.getDateString(info.getAutoExecuteDateEnd()) + "','yyyy-mm-dd HH:MI:SS')");
		if (info.getExecuteDateStart() != null)
			m_sbWhere.append(" and ExecuteDate>=to_date('" + DataFormat.getDateString(info.getExecuteDateStart()) + "','yyyy-mm-dd')");
		if (info.getExecuteDateEnd() != null)
			m_sbWhere.append(" and ExecuteDate<=to_date('" + DataFormat.getDateString(info.getExecuteDateEnd()) + "','yyyy-mm-dd')");
		if (info.getCalInterestDateStart() != null)
			m_sbWhere.append(" and InterestSettlementDate>=to_date('" + DataFormat.getDateString(info.getCalInterestDateStart()) + "','yyyy-mm-dd')");
		if (info.getCalInterestDateEnd() != null)
			m_sbWhere.append(" and InterestSettlementDate<=to_date('" + DataFormat.getDateString(info.getCalInterestDateEnd()) + "','yyyy-mm-dd')");
		if (info.getDepositTerm() > 0)
			m_sbWhere.append(" and DepositTerm=" + info.getDepositTerm() + "");
		if (info.getAccountTypeID() > 0)
			m_sbWhere.append(" and AccountTypeID=" + info.getAccountTypeID() + "");
		if (info.getLoanTypeID() > 0)
			m_sbWhere.append(" and LoanTypeID=" + info.getLoanTypeID() + "");
		if (info.getLoanTerm() > 0)
			m_sbWhere.append(" and LoanTerm=" + info.getLoanTerm() + "");
		if (info.getLoanYear() > 0)
			m_sbWhere.append(" and LoanYear=" + info.getLoanYear() + "");
		
		//上海电气新增扩展属性条件
		if (info.getExtendAttribute1()!=-1 && info.getExtendAttribute1()!=0)
		{
			m_sbWhere.append(" and client.NEXTENDATTRIBUTE1 = " + info.getExtendAttribute1() + " \n");
		}
		if (info.getExtendAttribute2()!=-1 && info.getExtendAttribute2()!=0)
		{
			m_sbWhere.append(" and client.NEXTENDATTRIBUTE2 = " + info.getExtendAttribute2() + " \n");
		}
		if (info.getExtendAttribute3()!=-1 && info.getExtendAttribute3()!=0)
		{
			m_sbWhere.append(" and client.NEXTENDATTRIBUTE3 = " + info.getExtendAttribute3() + " \n");
		}
		if (info.getExtendAttribute4()!=-1 && info.getExtendAttribute4()!=0)
		{
			m_sbWhere.append(" and client.NEXTENDATTRIBUTE4 = " + info.getExtendAttribute4() + " \n");
		}
		
		return "SELECT "+m_sbSelect.toString()+" FROM "+m_sbFrom.toString()+" WHERE " + m_sbWhere.toString();
	}

}
