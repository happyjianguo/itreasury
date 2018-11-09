package com.iss.itreasury.credit.setting.dao;

import java.sql.Connection;
import java.util.Collection;

import com.iss.itreasury.credit.setting.dataentity.AmountFormViewInfo;
import com.iss.itreasury.credit.setting.dataentity.AmountSetupInfo;
import com.iss.itreasury.credit.setting.dataentity.AmountSetupViewInfo;
import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Log4j;
import com.iss.system.dao.PageLoader;

public class AmountSetupDao extends ITreasuryDAO {
	
	protected Log4j log4j = new Log4j(Constant.ModuleType.LOAN, this);
	
	private StringBuffer m_sbSelect  = null;
	
	private StringBuffer m_sbFrom    = null;
	
	private StringBuffer m_sbWhere   = null;
	
	private StringBuffer m_sbOrderBy = null;
	
	public AmountSetupDao() {
		super("CREDIT_AMOUNTSETUP");
	}

	public AmountSetupDao(Connection conn) {
		super("CREDIT_AMOUNTSETUP", conn);
	}
	
	/**
	 * 查询某个客户时间段内是否做过授信额度设置
	 * @param info
	 * @return
	 * @throws ITreasuryDAOException
	 */
	public Collection checkByCondition(AmountSetupInfo asInfo)
		throws ITreasuryDAOException
	{
		Collection coll = null;
		StringBuffer strSQL = null;
		try {
			  	initDAO();
		        
	        	strSQL = new StringBuffer();
	        	strSQL.append(" select * from " + this.strTableName + " t");
	        	//strSQL.append(" where t.state in ("+ LOANConstant.CreditFlowStatus.SAVE + "," + LOANConstant.CreditFlowStatus.CHECK + "," + LOANConstant.CreditFlowStatus.APPROVALING +")");
	        	strSQL.append(" where t.state > 0 ");
	        	strSQL.append(" and t.clientId = ?");
	        	strSQL.append(" and t.officeId = ?");
	        	strSQL.append(" and t.currencyId = ?");
	        	if(asInfo.getOperationType() == LOANConstant.OperationType.CHANGE){
	        		//strSQL.append(" and t.ACTIVESTATE is null");
	        		strSQL.append(" and (t.ACTIVESTATE is null or (t.ACTIVESTATE is not null and t.amountformid != "+ asInfo.getAmountFormId() +"))");
	        	}
	        	else
	        	{
	        		strSQL.append(" and t.OperationType = " + asInfo.getOperationType());
	        	}
		        if(asInfo.getStartDate() != null && asInfo.getEndDate() != null){
		        	strSQL.append(" and ((STARTDATE >= to_date('"+ DataFormat.getDateString(asInfo.getStartDate()) +"', 'yyyy-mm-dd')");
		        	strSQL.append("      and STARTDATE  <= to_date('"+ DataFormat.getDateString(asInfo.getEndDate()) +"', 'yyyy-mm-dd')) or");
		        	strSQL.append("      (ENDDATE >= to_date('"+ DataFormat.getDateString(asInfo.getStartDate()) +"', 'yyyy-mm-dd')");
		        	strSQL.append("      and ENDDATE <= to_date('"+ DataFormat.getDateString(asInfo.getEndDate()) +"', 'yyyy-mm-dd')) or");
		        	strSQL.append("      (STARTDATE < to_date('"+ DataFormat.getDateString(asInfo.getStartDate()) +"', 'yyyy-mm-dd')");
		        	strSQL.append("      and ENDDATE > to_date('"+ DataFormat.getDateString(asInfo.getEndDate()) +"', 'yyyy-mm-dd')))");
		        }
	        	if(asInfo.getId() > 0){
	        		strSQL.append(" and t.id != " + asInfo.getId());
	        	}
	
		        prepareStatement(strSQL.toString());
				log4j.info("AmountSetupDao.checkByCondition()\n");
				log4j.info("strSQL=\n" + strSQL.toString());
				
	        	int nIndex = 1;
	        	transPS.setLong(nIndex++, asInfo.getClientId());
	        	transPS.setLong(nIndex++, asInfo.getOfficeId());
	        	transPS.setLong(nIndex++, asInfo.getCurrencyId());
		        
	        	executeQuery();
	        	coll = getDataEntitiesFromResultSet(AmountSetupInfo.class);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new ITreasuryDAOException("数据库查询异常，" + e.getMessage(),e);
		}
		finally {
			finalizeDAO();
		}
		if(coll == null || coll.size() == 0){
			return null;
		}
		else {
			return coll;
		}
	}
	
	/**
	 * 基本数据库操作（修改）
	 * @param asInfo
	 * @throws ITreasuryDAOException
	 */
	public void updateAmountSetup(AmountSetupInfo asInfo)
		throws ITreasuryDAOException
	{
		StringBuffer strSQL = null;
		try {
			/*----------------- init DAO --------------------*/
			try {
			  initDAO();
			}
			catch (ITreasuryDAOException e) {
			   throw new ITreasuryDAOException("创建连接时异常",e);
			}
			/*----------------- end DAO --------------------*/
		        
	        try {
	        	strSQL = new StringBuffer();
		        if(asInfo.getActiveUserId() < 0) {
		        	strSQL.append(" update " + this.strTableName + " t");
		        	strSQL.append(" set t.CREDITAMOUNT = ?,");
		        	strSQL.append(" t.operationsign = ?,");
		        	strSQL.append(" t.CONTROLTYPE = ?,");
		        	strSQL.append(" t.STARTDATE = ?,");
		        	strSQL.append(" t.ENDDATE = ?,");
		        	strSQL.append(" t.STATE = ?");
	        	}
		        else {
		        	strSQL.append(" update " + this.strTableName + " t");
		        	strSQL.append(" set t.AMOUNTFORMID = ?,");
		        	strSQL.append(" t.ACTIVEUSERID = ?,");
		        	strSQL.append(" t.ACTIVEDATE = ?,");
		        	strSQL.append(" t.ACTIVESTATE = ?");
		        }
	        	strSQL.append(" where t.ID = ?");
	        	
	        	prepareStatement(strSQL.toString());
				log4j.info("AmountSetupDao.updateAmountSetup()\n");
				log4j.info("strSQL=\n" + strSQL.toString());
	        	
	        	int nIndex = 1;
	        	if(asInfo.getActiveUserId() < 0) {
		        	transPS.setDouble(nIndex++, asInfo.getCreditAmount());
		        	transPS.setLong(nIndex++, asInfo.getOperationSign());
		        	transPS.setLong(nIndex++, asInfo.getControlType());
		        	transPS.setTimestamp(nIndex++, asInfo.getStartDate());
		        	transPS.setTimestamp(nIndex++, asInfo.getEndDate());
		        	transPS.setLong(nIndex++, asInfo.getState());
	        	}
	        	else {
	        		transPS.setLong(nIndex++, asInfo.getAmountFormId());
	        		transPS.setLong(nIndex++, asInfo.getActiveUserId());
	        		transPS.setTimestamp(nIndex++, asInfo.getActiveDate());
	        		transPS.setLong(nIndex++, asInfo.getActiveState());
	        	}
	        	transPS.setLong(nIndex++, asInfo.getId());
	        	executeUpdate();
	        }
		    catch (Exception e) {
		        throw new ITreasuryDAOException("数据库操作异常", e);
		    }
	
		    /*----------------finalize Dao-----------------*/
		    try {
		        finalizeDAO();
		    }
		    catch (ITreasuryDAOException e) {
		        throw new ITreasuryDAOException("关闭连接时异常",e);
		    }
		    /*----------------end of finalize---------------*/
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new ITreasuryDAOException("数据库操作异常",e);
		}
		finally {
			finalizeDAO();
		}
	}
	
	/**
	 * 基本数据库操作（删除）
	 * @param id
	 * @throws ITreasuryDAOException
	 */
	public void delete(long id)
		throws ITreasuryDAOException
	{
		StringBuffer strSQL = null;
		try {
			/*----------------- init DAO --------------------*/
			try {
			  initDAO();
			}
			catch (ITreasuryDAOException e) {
			   throw new ITreasuryDAOException("创建连接时异常",e);
			}
			/*----------------- end DAO --------------------*/
		        
	        try {
	        	strSQL = new StringBuffer();
	        	strSQL.append(" update " + this.strTableName + " t");
	        	strSQL.append(" set t.STATE = ?");
	        	strSQL.append(" where t.ID = ?");
	        	
	        	prepareStatement(strSQL.toString());
				log4j.info("AmountSetupDao.delete()\n");
				log4j.info("strSQL=\n" + strSQL.toString());
	        	
	        	int nIndex = 1;
	        	transPS.setLong(nIndex++, LOANConstant.CreditFlowStatus.DELETE);
	        	transPS.setLong(nIndex++, id);
	        	executeUpdate();
	        }
		    catch (Exception e) {
		        throw new ITreasuryDAOException("数据库操作异常", e);
		    }
	
		    /*----------------finalize Dao-----------------*/
		    try {
		        finalizeDAO();
		    }
		    catch (ITreasuryDAOException e) {
		        throw new ITreasuryDAOException("关闭连接时异常",e);
		    }
		    /*----------------end of finalize---------------*/
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new ITreasuryDAOException("数据库操作异常",e);
		}
		finally {
			finalizeDAO();
		}
	}
	
	/**
	 * 查询集团授信成员的授信金额使用情况
	 * @param queryInfo
	 * @return
	 * @throws ITreasuryDAOException
	 */
	public double getGroupLeaguerCreditAmount(AmountFormViewInfo queryInfo)
		throws ITreasuryDAOException
	{
		double dCreditAmount = 0.0;
		StringBuffer strSQL = null;
		long lCreditType = 0;
		
		try {
			if(queryInfo.getSubAmountFormInfo() != null){
				lCreditType = queryInfo.getSubAmountFormInfo().getCreditType();
			}
			
			/*----------------- init DAO --------------------*/
			try {
			  initDAO();
			}
			catch (ITreasuryDAOException e) {
			   throw new ITreasuryDAOException("创建连接时异常",e);
			}
			/*----------------- end DAO --------------------*/
		        
	        try {
	        	strSQL = new StringBuffer();
	        	
	        	if(lCreditType == 0)
	        	{
		        	strSQL.append(" select nvl(sum(t1.creditamount), 0) creditamount from " + this.strTableName + " t1");
		        	strSQL.append(" where t1.state = ?");
		        	strSQL.append(" and t1.officeid = ?");
		        	strSQL.append(" and t1.currencyId = ?");
		        	strSQL.append(" and t1.groupCreditId = ?");
	        	}
	        	else 
	        	{
		        	strSQL.append(" select nvl(sum(t2.creditamount), 0) creditamount from " + this.strTableName + " t1, credit_subamountsetup t2");
		        	strSQL.append(" where t1.state = ?");
		        	strSQL.append(" and t1.officeid = ?");
		        	strSQL.append(" and t1.currencyId = ?");
		        	strSQL.append(" and t1.groupCreditId = ?");
		        	strSQL.append(" and t1.id = t2.amountsetupid(+)");
		        	strSQL.append(" and t2.credittype = ?");
	        	}

	        	prepareStatement(strSQL.toString());
				log4j.info("AmountSetupDao.getGroupLeaguerCreditAmount()\n");
				log4j.info("strSQL=\n" + strSQL.toString());
				
	        	int nIndex = 1;
	        	transPS.setLong(nIndex++, LOANConstant.CreditFlowStatus.APPROVALING);
	        	transPS.setLong(nIndex++, queryInfo.getOfficeId());
	        	transPS.setLong(nIndex++, queryInfo.getCurrencyId());
	        	transPS.setLong(nIndex++, queryInfo.getId());
	        	if(lCreditType > 0)
	        	{
	        		transPS.setLong(nIndex++, lCreditType);
	        	}
	        	
	        	executeQuery();
	        	while(transRS.next()){
	        		dCreditAmount = transRS.getDouble("creditamount");
	        	}
	        }
		    catch (Exception e) {
		        throw new ITreasuryDAOException("数据库查询异常", e);
		    }
	
		    /*----------------finalize Dao-----------------*/
		    try {
		        finalizeDAO();
		    }
		    catch (ITreasuryDAOException e) {
		        throw new ITreasuryDAOException("关闭连接时异常",e);
		    }
		    /*----------------end of finalize---------------*/
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new ITreasuryDAOException("数据库查询异常",e);
		}
		finally {
			finalizeDAO();
		}
		return dCreditAmount;
	}

	/**
	 * 查询出一个授信额度设置
	 * @param creditId
	 * @return
	 * @throws ITreasuryDAOException
	 */
	public AmountSetupViewInfo getCreditAmountSetupView(AmountSetupInfo asInfo)
		throws ITreasuryDAOException
	{
		AmountSetupViewInfo asvInfo = null;
		StringBuffer strSQL = null;
		try {
			/*----------------- init DAO --------------------*/
			try {
			  initDAO();
			}
			catch (ITreasuryDAOException e) {
			   throw new ITreasuryDAOException("创建连接时异常",e);
			}
			/*----------------- end DAO --------------------*/
		        
	        try {
	        	strSQL = new StringBuffer();
	        	strSQL.append(" select t1.*, t2.creditcode groupCreditCode, t2.startdate groupStartDate, t2.enddate groupEndDate");
	        	strSQL.append(" from " + this.strTableName + " t1,");
	        	//strSQL.append(" (select t.* from CREDIT_AMOUNTFORM t where t.state = ? and t.officeId = ? and t.currencyId = ?) t2");
	        	strSQL.append(" (select t.* from CREDIT_AMOUNTFORM t where  t.officeId = ? and t.currencyId = ?) t2");
	        	strSQL.append(" where t1.groupcreditid = t2.id(+)");
	        	strSQL.append(" and t1.officeId = ?");
	        	strSQL.append(" and t1.currencyId = ?");
	        	//strSQL.append(" and t1.state > 0");
	        	strSQL.append(" and t1.id = ?");

		        prepareStatement(strSQL.toString());
				log4j.info("AmountSetupDao.getCreditAmountSetupView()\n");
				log4j.info("strSQL=\n" + strSQL.toString());
		        
	        	int nIndex = 1;
	        	transPS.setLong(nIndex++, asInfo.getOfficeId());
	        	transPS.setLong(nIndex++, asInfo.getCurrencyId());
	        	//transPS.setLong(nIndex++, LOANConstant.CreditFlowStatus.SAVE);
	        	transPS.setLong(nIndex++, asInfo.getOfficeId());
	        	transPS.setLong(nIndex++, asInfo.getCurrencyId());
	        	transPS.setLong(nIndex++, asInfo.getId());
				
	        	executeQuery();
	        	asvInfo = (AmountSetupViewInfo)getDataEntityFromResultSet(AmountSetupViewInfo.class);
	        }
		    catch (Exception e) {
		        throw new ITreasuryDAOException("数据库查询异常", e);
		    }
	
		    /*----------------finalize Dao-----------------*/
		    try {
		        finalizeDAO();
		    }
		    catch (ITreasuryDAOException e) {
		        throw new ITreasuryDAOException("关闭连接时异常",e);
		    }
		    /*----------------end of finalize---------------*/
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new ITreasuryDAOException("数据库查询异常",e);
		}
		finally {
			finalizeDAO();
		}
		return asvInfo;
	}
	
	/**
	 * 根据 AmountSetupInfo 条件查询
	 * @param info
	 * @return
	 * @throws ITreasuryDAOException
	 */
	public Collection findByCollection(AmountSetupInfo asInfo)
		throws ITreasuryDAOException
	{
		Collection coll = null;
		StringBuffer strSQL = null;
		try {
			/*----------------- init DAO --------------------*/
			try {
			  initDAO();
			}
			catch (ITreasuryDAOException e) {
			   throw new ITreasuryDAOException("创建连接时异常",e);
			}
			/*----------------- end DAO --------------------*/
		        
	        try {
	        	strSQL = new StringBuffer();
	        	strSQL.append(" select t1.*, t2.name clientName, t3.sname inputUserName from " + this.strTableName + " t1, client_clientinfo t2, userinfo t3");
	        	strSQL.append(" where t1.clientid = t2.id(+)");
	        	strSQL.append(" and t1.inputuserid = t3.id(+)");
	        	if(asInfo.getClientId() > 0){
	        		strSQL.append(" and t1.clientId = ?");
	        	}
		        if(asInfo.getCreditModel() > 0){
		        	strSQL.append(" and t1.CreditModel = ?");
				}
	        	if(asInfo.getCreditAmount() > 0.0){
	        		strSQL.append(" and t1.creditAmount <= ?");
	        	}
	        	if(asInfo.getStartDate() != null && asInfo.getEndDate() == null){
	        		strSQL.append(" and t1.STARTDATE >= ?");
	        	}
	        	if(asInfo.getEndDate() != null && asInfo.getStartDate() == null){
	        		strSQL.append(" and t1.ENDDATE <= ?");
	        	}
	        	if(asInfo.getStartDate() != null && asInfo.getEndDate() != null){
	        		strSQL.append(" and t1.STARTDATE >= ?");
	        		strSQL.append(" and t1.ENDDATE <= ?");
	        	}
		        if(asInfo.getState() > 0){
		        	if(asInfo.getState() == LOANConstant.CreditFlowStatus.CHECK){
		        		strSQL.append(" and t1.STATE = ?");
		        		strSQL.append(" and t1.ACTIVESTATE is null");
		        	}
		        	else {
		        		strSQL.append(" and t1.STATE = ?");
		        	}
				}
		        else {
		        	strSQL.append(" and t1.STATE > 0");
		        }
	        	strSQL.append(" and t1.officeId = ?");
	        	strSQL.append(" and t1.currencyId = ?");

		        prepareStatement(strSQL.toString());
				log4j.info("AmountSetupDao.checkByCondition()\n");
				log4j.info("strSQL=\n" + strSQL.toString());
				
	        	int nIndex = 1;
	        	if(asInfo.getClientId() > 0){
	        		transPS.setLong(nIndex++, asInfo.getClientId());
	        	}
		        if(asInfo.getCreditModel() > 0){
		        	transPS.setLong(nIndex++, asInfo.getCreditModel());
				}
	        	if(asInfo.getCreditAmount() > 0.0){
	        		transPS.setDouble(nIndex++, asInfo.getCreditAmount());
	        	}
	        	if(asInfo.getStartDate() != null && asInfo.getEndDate() == null){
	        		transPS.setTimestamp(nIndex++, asInfo.getStartDate());
	        	}
	        	if(asInfo.getEndDate() != null && asInfo.getStartDate() == null){
	        		transPS.setTimestamp(nIndex++, asInfo.getEndDate());
	        	}
	        	if(asInfo.getStartDate() != null && asInfo.getEndDate() != null){
	        		transPS.setTimestamp(nIndex++, asInfo.getStartDate());
	        		transPS.setTimestamp(nIndex++, asInfo.getEndDate());
	        	}
		        if(asInfo.getState() > 0){
		        	transPS.setLong(nIndex++, asInfo.getState());
				}
	        	transPS.setLong(nIndex++, asInfo.getOfficeId());
	        	transPS.setLong(nIndex++, asInfo.getCurrencyId());
		        
	        	executeQuery();
	        	coll = getDataEntitiesFromResultSet(AmountSetupViewInfo.class);
	        }
		    catch (Exception e) {
		        throw new ITreasuryDAOException("数据库查询异常", e);
		    }
	
		    /*----------------finalize Dao-----------------*/
		    try {
		        finalizeDAO();
		    }
		    catch (ITreasuryDAOException e) {
		        throw new ITreasuryDAOException("关闭连接时异常",e);
		    }
		    /*----------------end of finalize---------------*/
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new ITreasuryDAOException("数据库查询异常",e);
		}
		finally {
			finalizeDAO();
		}
		if(coll == null || coll.size() == 0){
			return null;
		}
		else {
			return coll;
		}
	}
	
	public PageLoader getCreditRecordQuery(AmountSetupViewInfo amountSetupViewInfo)throws ITreasuryDAOException
	{
		getCreditRecordQuerySQL(amountSetupViewInfo);
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
        pageLoader.initPageLoader(new AppContext(),
				                  m_sbFrom.toString(),
				                  m_sbSelect.toString(),
				                  m_sbWhere.toString(),
				                  (int) Constant.PageControl.CODE_PAGELINECOUNT,
				                  "com.iss.itreasury.credit.setting.dataentity.AmountSetupViewInfo",
				                  null);
        pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
        return pageLoader;
	}
	private void getCreditRecordQuerySQL(AmountSetupViewInfo amountSetupViewInfo)
	{
		m_sbSelect = new StringBuffer();
		m_sbSelect.append("S.ID AS ID,S.CLIENTID AS CLIENTID,(SELECT C.SNAME FROM CLIENT C WHERE C.ID = S.CLIENTID) AS CLIENTNAME,");
		m_sbSelect.append("S.CREDITMODEL AS CREDITMODEL,S.OPERATIONTYPE AS OPERATIONTYPE,S.CREDITAMOUNT AS CREDITAMOUNT,S.STARTDATE AS STARTDATE,");
		m_sbSelect.append("S.ENDDATE AS ENDDATE,S.INPUTUSERID AS INPUTUSERID,(SELECT U.SNAME FROM USERINFO U WHERE U.ID = S.INPUTUSERID) AS INPUTUSERNAME,");
		m_sbSelect.append("S.INPUTDATE AS INPUTDATE,S.OFFICEID AS OFFICEID,S.CURRENCYID AS CURRENCYID, S.CREDITCODE AS CREDITCODE, \n");
		m_sbSelect.append("CASE WHEN (S.STATE = "+LOANConstant.CreditFlowStatus.CHECK+" AND S.ACTIVESTATE = "+LOANConstant.CreditFlowStatus.ACTIVE+") THEN S.ACTIVESTATE ELSE S.STATE END AS STATE \n");
		
		m_sbFrom = new StringBuffer();
		m_sbFrom.append(" CREDIT_AMOUNTSETUP S \n");
		
		m_sbWhere = new StringBuffer();
		m_sbWhere.append(" S.OFFICEID = "+amountSetupViewInfo.getOfficeId()+" AND S.CURRENCYID = "+amountSetupViewInfo.getCurrencyId());
		if(amountSetupViewInfo.getClientId() > 0)
		{
			m_sbWhere.append(" AND S.CLIENTID  = " + amountSetupViewInfo.getClientId());
		}
		if(amountSetupViewInfo.getStartDate() != null && amountSetupViewInfo.getEndDate() == null)
		{
			m_sbWhere.append(" AND STARTDATE  <= TO_DATE('"+DataFormat.getDateString(amountSetupViewInfo.getStartDate())+"','yyyy-mm-dd')");
			m_sbWhere.append(" AND ENDDATE  >= TO_DATE('"+DataFormat.getDateString(amountSetupViewInfo.getStartDate())+"','yyyy-mm-dd')");
		}
		if(amountSetupViewInfo.getStartDate() == null && amountSetupViewInfo.getEndDate() != null)
		{
			m_sbWhere.append(" AND STARTDATE  <= TO_DATE('"+DataFormat.getDateString(amountSetupViewInfo.getEndDate())+"','yyyy-mm-dd')");
			m_sbWhere.append(" AND ENDDATE  >= TO_DATE('"+DataFormat.getDateString(amountSetupViewInfo.getEndDate())+"','yyyy-mm-dd')");
		}
		if(amountSetupViewInfo.getStartDate() != null && amountSetupViewInfo.getEndDate() != null)
		{
			m_sbWhere.append(" and ((STARTDATE >= to_date('"+ DataFormat.getDateString(amountSetupViewInfo.getStartDate()) +"', 'yyyy-mm-dd')");
			m_sbWhere.append("      and STARTDATE  <= to_date('"+ DataFormat.getDateString(amountSetupViewInfo.getEndDate()) +"', 'yyyy-mm-dd')) or");
			m_sbWhere.append("      (ENDDATE >= to_date('"+ DataFormat.getDateString(amountSetupViewInfo.getStartDate()) +"', 'yyyy-mm-dd')");
			m_sbWhere.append("      and ENDDATE <= to_date('"+ DataFormat.getDateString(amountSetupViewInfo.getEndDate()) +"', 'yyyy-mm-dd')) or");
			m_sbWhere.append("      (STARTDATE < to_date('"+ DataFormat.getDateString(amountSetupViewInfo.getStartDate()) +"', 'yyyy-mm-dd')");
			m_sbWhere.append("      and ENDDATE > to_date('"+ DataFormat.getDateString(amountSetupViewInfo.getEndDate()) +"', 'yyyy-mm-dd')))");	
		}
		if(amountSetupViewInfo.getCreditAmount() > 0)
		{
			m_sbWhere.append(" AND S.CREDITAMOUNT <= " + amountSetupViewInfo.getCreditAmount());
		}
		if(amountSetupViewInfo.getOperationType() > 0)
		{
			m_sbWhere.append(" AND S.OPERATIONTYPE = " + amountSetupViewInfo.getOperationType());
		}
		else
		{
			m_sbWhere.append(" AND S.OPERATIONTYPE IN("+LOANConstant.OperationType.NEW+","+LOANConstant.OperationType.CHANGE+")");
		}
		if(amountSetupViewInfo.getState() > 0)
		{
			if(amountSetupViewInfo.getState() == LOANConstant.CreditFlowStatus.ACTIVE)
			{
			     m_sbWhere.append(" AND S.STATE = "+LOANConstant.CreditFlowStatus.CHECK+" AND S.ACTIVESTATE = "+LOANConstant.CreditFlowStatus.ACTIVE);
			}
			else if(amountSetupViewInfo.getState() == LOANConstant.CreditFlowStatus.CHECK)
			{
				 m_sbWhere.append(" AND S.STATE = "+LOANConstant.CreditFlowStatus.CHECK+" AND S.ACTIVESTATE IS NULL ");
			}
			else
			{
				 m_sbWhere.append(" AND S.STATE = " + amountSetupViewInfo.getState());
			}
		}
		else
		{
			m_sbWhere.append(" AND S.STATE > 0");
		}
		
		m_sbOrderBy = new StringBuffer();
		m_sbOrderBy.append(getCreditRecordQueryOrderParam(amountSetupViewInfo.getLDesc(),amountSetupViewInfo.getLOrderParam()));
	}
	
	/**
	 * 获取授信历史记录查询列表的order by，用于pageloader 
	 * 
	 * @param lDesc
	 * @param lOrderParam
	 * @return
	 */
	public  String getCreditRecordQueryOrderParam(long lDesc,long lOrderParam)
	{
		StringBuffer sbOrderBy = new StringBuffer();
        String strDesc = lDesc == 2 ? " DESC " : " ASC ";
		
		if(lOrderParam > 0) 
		{
			switch ((int) lOrderParam)
			{
			    case 1:sbOrderBy.append(" \n ORDER BY CREDITCODE " + strDesc);             break;
				case 2:sbOrderBy.append(" \n ORDER BY CLIENTID " + strDesc);             break;
				case 3:sbOrderBy.append(" \n ORDER BY CREDITMODEL " + strDesc);   break;
				case 4:sbOrderBy.append(" \n ORDER BY OPERATIONTYPE " + strDesc);         break;
				case 5:sbOrderBy.append(" \n ORDER BY CREDITAMOUNT " + strDesc);break;
				case 6:sbOrderBy.append(" \n ORDER BY STARTDATE " + strDesc);        break;
				case 7:sbOrderBy.append(" \n ORDER BY ENDDATE " + strDesc);          break;
				case 8:sbOrderBy.append(" \n ORDER BY INPUTUSERID " + strDesc);     break;
				case 9:sbOrderBy.append(" \n ORDER BY INPUTDATE " + strDesc);      break;
				case 10:sbOrderBy.append(" \n ORDER BY STATE " + strDesc);      break;
				
				default:sbOrderBy.append(" \n ORDER BY SID DESC" );break;
		    }	
		}
		else
		{
			sbOrderBy.append(" \n ORDER BY CREDITCODE ASC" );
		}
		return sbOrderBy.toString();
	}
}
