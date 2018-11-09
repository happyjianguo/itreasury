/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2004-3-9
 */
package com.iss.itreasury.securities.securitiesaccount.dao;
import java.util.Collection;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.securities.exception.SecuritiesDAOException;
import com.iss.itreasury.securities.securitiesaccount.dataentity.AccountTypeInfo;
import com.iss.itreasury.securities.util.SECConstant;
import com.iss.itreasury.securities.util.SecuritiesDAO;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Log4j;
/**
 * @author yehuang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class SEC_AccountTypeDAO extends SecuritiesDAO
{
	private Log4j log = new Log4j(Constant.ModuleType.SECURITIES, this);
	/**排序条件**/
	public final static int clientID = 1;                  //业务单位
	public final static int counterpartID = 2;             //交易对手
	public final static int accountID = 3;                 //资金帐号
	public final static int accountType = 4;               //帐务类型
	public final static int securitiesID = 5;              //证券名称
	public final static int principalSubject = 6;          //本金/成本/面值科目号
	public final static int interestSubject = 7;           //利息/收益/支出科目号
	public final static int marginSubject = 8;             //证券销售差价收入/损失科目号
	public final static int suspenseInterestSubject = 9;   //应计利息/损益调整科目号
	public final static int suspenseSubject = 10;          //挂帐科目号
	public final static int commissionSubject = 11;        //手续费科目号
	public final static int receivableSubject = 12;        //其它应收款科目
	public final static int payableSubject = 13;           //其它应付款科目
	public SEC_AccountTypeDAO()
	{
		super("SEC_AccountType");
	}
	/**
	 * 根据查询条件获取帐务类型信息
	 * 
	 */
	public Collection getSecuritiesSubjectCode(
		long officeID,
		long currencyID,
		long accountType,
		long termTypeID,
		long term,
		long counterpartID,
		long clientID,
		long exchangeCenterCode,
		String seatCode,
		long accountID,
		long securitiesID,
		long subjectType)
		throws ITreasuryDAOException
	{
		initDAO();
		log.debug("--------根据查询条件获取帐务类型信息-----------");
		
		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append("SELECT * FROM " + strTableName);
		strBuffer.append(" WHERE OfficeID = " + officeID);
		strBuffer.append(" AND   CurrencyID = " + currencyID);
		strBuffer.append(" and  (AccountType = -1");
		strBuffer.append(" OR   (AccountType = " + accountType + "))");
		strBuffer.append(" AND  (TermTypeID  = -1");
		strBuffer.append(" OR   (TermTypeID = " + termTypeID);
		strBuffer.append(" AND  Term = " + term + "))");
		strBuffer.append(" AND  (CounterpartID = -1");
		strBuffer.append(" OR  CounterpartID = " + counterpartID + ")");
		strBuffer.append(" AND  (ClientID  = -1");
		strBuffer.append(" OR  ClientID  = " + clientID + ")");
		//		strBuffer.append(" AND  (exchangeCenterCode  = 0");		
		//		strBuffer.append(" OR  exchangeCenterCode  = " + exchangeCenterCode + ")");
		strBuffer.append(" AND  (SeatCode IS NULL");
		strBuffer.append(" OR   (SeatCode = '" + seatCode + "'))");
		strBuffer.append(" AND  (AccountID   = -1");
		strBuffer.append(" OR  AccountID   = " + accountID + ")");
		strBuffer.append(" AND  (SecuritiesID   = -1");
		strBuffer.append(" OR  securitiesID   = " + securitiesID + ")");
		strBuffer.append(" AND  StatusID   = " + SECConstant.BusinessAttributeStatus.CHECKED);
		String sql = strBuffer.toString();
		//log.debug(sql);
		prepareStatement(sql);
		executeQuery();
		Collection c = getDataEntitiesFromResultSet(AccountTypeInfo.class);
		finalizeDAO();
		return c;
	}
	/**
	 * 帐务类型设置查找（排序）操作
	 * @param officeID
	 * @param currencyID
	 * @param orderType
	 * @return
	 * @throws SecuritiesException
	 */
	public Collection findAllAccountType(AccountTypeInfo paraInfo) throws SecuritiesDAOException
	{
		Collection collection = null;
		AccountTypeInfo info = null;
		StringBuffer buffer = new StringBuffer();
		buffer.append("select * \n");
		buffer.append("from SEC_ACCOUNTTYPE ");
		boolean itemNotEmpty = false;
		buffer.append(" where STATUSID=").append(paraInfo.getStatusID());
		if( paraInfo.getBusinessAttributeId() == SECConstant.BusinessAttribute.INTER_BANK)
		{
			buffer.append(" and ACCOUNTTYPE IN("+SECConstant.EntryAccountType.AccountType_0+","+
			                                     SECConstant.EntryAccountType.AccountType_1+","+
			                                     SECConstant.EntryAccountType.AccountType_2+","+
			                                     SECConstant.EntryAccountType.AccountType_3+","+
			                                     SECConstant.EntryAccountType.AccountType_4+","+
												 SECConstant.EntryAccountType.AccountType_5+","+
			                                     SECConstant.EntryAccountType.AccountType_10+")");
		}
		else if( paraInfo.getBusinessAttributeId() == SECConstant.BusinessAttribute.INTER_EXCHANGECENTER )
		{
			buffer.append( " and ACCOUNTTYPE IN("+SECConstant.EntryAccountType.AccountType_0+","+
			                                      SECConstant.EntryAccountType.AccountType_6+","+
			                                      SECConstant.EntryAccountType.AccountType_7+","+
			                                      SECConstant.EntryAccountType.AccountType_8+")");
		}
		else if( paraInfo.getBusinessAttributeId() == SECConstant.BusinessAttribute.OPENFUND )
		{
			buffer.append(" and ACCOUNTTYPE IN("+SECConstant.EntryAccountType.AccountType_0+","+
			                                     SECConstant.EntryAccountType.AccountType_9+")");
		}
		else if (paraInfo.getBusinessAttributeId() == SECConstant.BusinessAttribute.CONTRACTS)
		{
			buffer.append(" and AccountType in("+SECConstant.EntryAccountType.AccountType_0+","+
												SECConstant.EntryAccountType.AccountType_11+","+
												SECConstant.EntryAccountType.AccountType_12+","+
												SECConstant.EntryAccountType.AccountType_13+","+
												SECConstant.EntryAccountType.AccountType_14+","+
												SECConstant.EntryAccountType.AccountType_15+","+
												SECConstant.EntryAccountType.AccountType_16+","+
												SECConstant.EntryAccountType.AccountType_17+")");
		}
		//因为没有其他业务，没有对应的AccountType值，现在临时用18
		else if (paraInfo.getBusinessAttributeId() == SECConstant.BusinessAttribute.OTHERS)
		{
			buffer.append(" and AccountType in("+SECConstant.EntryAccountType.AccountType_0+")");
		}
		if (paraInfo.getOfficeID() != -1)
		{
			itemNotEmpty = true;
			buffer.append(" and  OfficeID=").append(paraInfo.getOfficeID());
		}
		if (paraInfo.getCurrencyID() != -1)
		{
			if (itemNotEmpty)
			{
				buffer.append(" and ");
			}
			else
			{
				itemNotEmpty = true;
				buffer.append(" where ");
			}
			buffer.append(" CurrencyID=").append(paraInfo.getCurrencyID());
		}
		switch ((int) (paraInfo.getOrderType()))
		{
			case clientID :
				buffer.append(" order by CLIENTID");
				break;
			case (counterpartID) :
				buffer.append(" order by COUNTERPARTID");
				break;
			case (accountID) :
				buffer.append(" order by ACCOUNTID");
				break;
			case (accountType) :
				buffer.append(" order by ACCOUNTTYPE");
				break;
			case (securitiesID) :
				buffer.append(" order by SECURITIESID");
				break;
			case (principalSubject) :
				buffer.append(" order by PRINCIPALSUBJECT");
				break;
			case (interestSubject) :
				buffer.append(" order by INTERESTSUBJECT");
				break;
			case (marginSubject) :
				buffer.append(" order by MARGINSUBJECT");
				break;
			case (suspenseInterestSubject) :
				buffer.append(" order by SUSPENSEINTERESTSUBJECT");
				break;
			case (suspenseSubject) :
				buffer.append(" order by SUSPENSESUBJECT");
				break;
			case (commissionSubject) :
				buffer.append(" order by commissionSubject");
				break;
			case (receivableSubject) :
				buffer.append(" order by receivableSubject");
				break;
			case (payableSubject) :
				buffer.append(" order by payableSubject");
				break;
			default :
				buffer.append(" order by ID");
				break;
		}
		if( paraInfo.getDescOrAsc() == Constant.PageControl.CODE_ASCORDESC_ASC)
		{
			buffer.append(" ASC ");
		}
		else
		{
			buffer.append(" DESC ");
		}
		try
		{
			initDAO();
			prepareStatement(buffer.toString());
			executeQuery();
			collection = getDataEntitiesFromResultSet(AccountTypeInfo.class);
			finalizeDAO();
		}
		catch (ITreasuryDAOException e)
		{
			throw new SecuritiesDAOException("", e);
		}
		return collection;
	}
}
