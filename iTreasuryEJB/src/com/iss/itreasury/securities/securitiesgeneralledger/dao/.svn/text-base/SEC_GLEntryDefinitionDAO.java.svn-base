/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2004-3-9
 */
package com.iss.itreasury.securities.securitiesgeneralledger.dao;
import java.sql.Connection;
import java.util.Collection;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.securities.securitiesgeneralledger.dataentity.GLEntryDefinitionInfo;
import com.iss.itreasury.securities.util.SecuritiesDAO;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Log4j;
import com.iss.itreasury.securities.exception.SecuritiesDAOException;
/**
 * @author yehuang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class SEC_GLEntryDefinitionDAO extends SecuritiesDAO
{
	private Log4j log = new Log4j(Constant.ModuleType.SECURITIES, this);
	
	public final static int TRANSACTION_TYPE = 1; //交易类型(业务类型)
	public final static int SUBTRANSACTION_TYPE = 10;//交易子类型
	public final static int CAPITAL_TYPE = 2; //资金流向 
	public final static int ENTRY_TYPE = 3; //分录类型
	public final static int DIRECTION = 4; //借贷方向
	public final static int SUBJECT_TYPE = 5; //科目类型
	public final static int SUBJECT_CODE = 6; //科目号
	public final static int AMOUNT_DIRECTION = 7; //金额方向
	public final static int AMOUNT_TYPE = 8; //金额类型	
	public final static int REMARK=9;//摘要
	public SEC_GLEntryDefinitionDAO()
	{
		super("SEC_GLEntryDefinition");
	}
	public SEC_GLEntryDefinitionDAO(Connection conn)
	{
		super("SEC_GLEntryDefinition", conn);
	}
	/**
	 * 查询所有分录设置定义表记录
	 * @param GLEntryDefinitionInfo info
	 * @return 所有分录设置定义表记录集合
	 * @throws SQLException
	 */
	public Collection findAllByTransactionTypeIDAndEntryType(long lTransactionTypeID, long lEntryType, long lSubTransactionType, long lCurrencyID, long lOfficeID) throws ITreasuryDAOException
	{
		initDAO();
		StringBuffer buffer = new StringBuffer();
		buffer.append("select * from " + strTableName);
		buffer.append(" where ");
		buffer.append(" TRANSACTIONTYPEID =").append(lTransactionTypeID);
		buffer.append(" AND (ENTRYTYPE = ").append(lEntryType);
		buffer.append(" OR ENTRYTYPE = ").append(-1);
		buffer.append(" )");
		buffer.append(" AND (SubTransactionType = ").append(lSubTransactionType);
		buffer.append(" OR SubTransactionType = ").append(-1);
		buffer.append(" )");
		buffer.append(" AND OFFICEID = ").append(lOfficeID);
		buffer.append(" AND CURRENCYID = ").append(lCurrencyID);
		buffer.append(" AND CURRENCYID = ").append(lCurrencyID);
		buffer.append(" AND STATUSID > 0 ");
		
		log.debug(buffer.toString());
		prepareStatement(buffer.toString());
		executeQuery();
		Collection res = getDataEntitiesFromResultSet(GLEntryDefinitionInfo.class);
		finalizeDAO();
		return res;
	}
	public Collection findAllAndOrderBy(GLEntryDefinitionInfo paraInfo)throws SecuritiesDAOException
	{

		Collection collection = null;
		GLEntryDefinitionInfo info = null;
		StringBuffer buffer = new StringBuffer();
		buffer.append("select * \n");
		buffer.append("from SEC_GLENTRYDEFINITION ");
		boolean itemNotEmpty = false;
		buffer.append(" where STATUSID=").append(paraInfo.getStatusID());
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
		switch ((int)( paraInfo.getOrderType()))
		{
			case TRANSACTION_TYPE :
				buffer.append(" order by TRANSACTIONTYPEID");
				break;
			case (CAPITAL_TYPE) :
				buffer.append(" order by CAPITALTYPE");
				break;
			case (ENTRY_TYPE) :
				buffer.append(" order by ENTRYTYPE");
				break;
			case (DIRECTION) :
				buffer.append(" order by DIRECTION");
				break;
			case (SUBJECT_TYPE) :
				buffer.append(" order by SUBJECTTYPE");
				break;
			case (SUBJECT_CODE) :
				buffer.append(" order by SUBJECTCODE");
				break;
			case (AMOUNT_DIRECTION) :
				buffer.append(" order by AMOUNTDIRECTION");
				break;
			case (AMOUNT_TYPE) :
				buffer.append(" order by AMOUNTTYPE");
			    break;
			case (REMARK) :
			    buffer.append(" order by REMARK");	
				break;
			case (SUBTRANSACTION_TYPE) :
			    buffer.append(" order by SUBTRANSACTIONTYPE ");
			    break;
			default:
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
			collection =  getDataEntitiesFromResultSet(GLEntryDefinitionInfo.class);
			finalizeDAO();
		}
		catch (ITreasuryDAOException e)
		{
			throw new SecuritiesDAOException("",e);
		}
		return collection;
	}
	public Collection findByConditions(GLEntryDefinitionInfo paraInfo)throws SecuritiesDAOException
	{

		Collection collection = null;
		GLEntryDefinitionInfo info = null;
		StringBuffer buffer = new StringBuffer();
		buffer.append("select t.* from SEC_GLENTRYDEFINITION t,sec_businessattribute m,sec_businesstype n,sec_transactiontype l where t.transactiontypeid=l.id and l.businesstypeid=n.id and n.businessattributeid=m.id and" );
		
				
		buffer.append(" t.STATUSID=").append(paraInfo.getStatusID()).append( " and");
		
		if (paraInfo.getOfficeID() != -1)
		{			
			buffer.append(" t.OfficeID=").append(paraInfo.getOfficeID()).append( " and");
		}
		if (paraInfo.getCurrencyID() != -1)
		{			
			buffer.append(" t.CurrencyID=").append(paraInfo.getCurrencyID()).append(" and");
		}
		
		if (paraInfo.getBusinessAttributeId()!=-1){
			
			buffer.append(" m.id=").append(paraInfo.getBusinessAttributeId()).append(" and");
		}
		if (paraInfo.getBusinessTypeId()!=-1){
			buffer.append(" n.id=").append(paraInfo.getBusinessTypeId()).append(" and");
		}
		if (paraInfo.getTransactionTypeId()!=-1){			
			buffer.append(" l.id=").append(paraInfo.getTransactionTypeId()).append(" and");
		}
		buffer=new StringBuffer(buffer.substring(0,buffer.length()-3));
		
		switch ((int)( paraInfo.getOrderType()))
		{
			case TRANSACTION_TYPE :
				buffer.append(" order by t.TRANSACTIONTYPEID");
				break;
			case (CAPITAL_TYPE) :
				buffer.append(" order by t.CAPITALTYPE");
				break;
			case (ENTRY_TYPE) :
				buffer.append(" order by t.ENTRYTYPE");
				break;
			case (DIRECTION) :
				buffer.append(" order by t.DIRECTION");
				break;
			case (SUBJECT_TYPE) :
				buffer.append(" order by t.SUBJECTTYPE");
				break;
			case (SUBJECT_CODE) :
				buffer.append(" order by t.SUBJECTCODE");
				break;
			case (AMOUNT_DIRECTION) :
				buffer.append(" order by t.AMOUNTDIRECTION");
				break;
			case (AMOUNT_TYPE) :
				buffer.append(" order by t.AMOUNTTYPE");
			    break;
			case (REMARK) :
			    buffer.append(" order by t.REMARK");	
				break;
			case (SUBTRANSACTION_TYPE) :
			    buffer.append(" order by t.SUBTRANSACTIONTYPE ");
			    break;
			default:
			    buffer.append(" order by t.ID");
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
			collection =  getDataEntitiesFromResultSet(GLEntryDefinitionInfo.class);
			finalizeDAO();
		}
		catch (ITreasuryDAOException e)
		{
			throw new SecuritiesDAOException("",e);
		}
		return collection;
	}
}
