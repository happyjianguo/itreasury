package com.iss.itreasury.clientmanage.client.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;

import com.iss.itreasury.clientmanage.client.dataentity.ClientAreaInfo;
import com.iss.itreasury.clientmanage.client.dataentity.DepositAndLoanMessageDetailInfo;
import com.iss.itreasury.clientmanage.client.dataentity.EconomicComponentInfo;
import com.iss.itreasury.clientmanage.client.dataentity.EconomicDepartmentInfo;
import com.iss.itreasury.clientmanage.client.dataentity.EconomicIndustryInfo;
import com.iss.itreasury.clientmanage.client.dataentity.EnterpriseSizeInfo;
import com.iss.itreasury.clientmanage.client.dataentity.ExtClientInfo;
import com.iss.itreasury.clientmanage.client.dataentity.ManagerInfo;
import com.iss.itreasury.clientmanage.client.dataentity.ClientUploadReportInfo;
import com.iss.itreasury.clientmanage.client.dataentity.CorporationExtendsInfo;
import com.iss.itreasury.clientmanage.client.dataentity.StockPartnerInfo;
import com.iss.itreasury.clientmanage.partner.dataentity.PartnerInfo;
import com.iss.itreasury.clientmanage.client.dataentity.ClientBusinessInfo;
import com.iss.itreasury.clientmanage.client.dataentity.ClientContactInfo;
import com.iss.itreasury.clientmanage.client.dataentity.ClientInvestInfo;

import com.iss.itreasury.clientmanage.client.dataentity.QueryCorporationInfo;
import com.iss.itreasury.clientmanage.client.dataentity.ClientDealInfo;
import com.iss.itreasury.clientmanage.dataentity.ClientInfo;
import java.util.Vector;
import com.iss.itreasury.clientmanage.client.dataentity.CorporationInfo;
import com.iss.itreasury.clientmanage.client.dataentity.QueryClientInfo;
import com.iss.itreasury.clientmanage.util.CMConstant;
import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.settlement.account.dataentity.ExternalAccountInfo;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.ConfigConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.ITreasuryBaseDataEntity;
import com.iss.itreasury.util.ITreasuryException;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.util.Log4j;
import com.iss.itreasury.util.SessionMng;
import com.iss.system.dao.PageLoader;
import com.iss.system.dao.oracle.PageLoaderCombine;




public class ClientmanageDAO extends ITreasuryDAO{
	
	protected Log4j log = new Log4j(Constant.ModuleType.BUDGET, this);
	public ClientmanageDAO() {
		super("Client_clientInfo");
	}
	public ClientmanageDAO(String table,Connection con) {
		super(table, con);
	}
	public ClientmanageDAO(Connection con) {
		super("Client_clientInfo", con);
	}
	public ClientmanageDAO(String table)
	{
		super(table);
	}
	StringBuffer m_sbSelect = null;
	StringBuffer m_sbFrom = null;
	StringBuffer m_sbWhere = null;
	StringBuffer m_sbOrderBy = null;
	StringBuffer m_sbMainTable = null;
	StringBuffer m_sbMainCondition = null;
	StringBuffer m_sbMainOrderBy = null;
	
	
	

	/**
	 * 经营信息分页查询
	 * @param clientInfo
	 */
    public PageLoader queryClientdealInfo(QueryCorporationInfo clientInfo) {
		
		queryClientdealSQL(clientInfo);
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		pageLoader.initPageLoader(
			new AppContext(),
			m_sbFrom.toString(),
			m_sbSelect.toString(),
			m_sbWhere.toString(),
			(int) Constant.PageControl.CODE_PAGELINECOUNT,
			"com.iss.itreasury.clientmanage.client.dataentity.ClientDealInfo",
			null);
		pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
		return pageLoader;
		
	}
    /**
     * 存贷款标准化信息查询
     * @param clientInfo
     * @return
     */
    public PageLoader queryClientDepositAndLoanMessage(QueryCorporationInfo clientInfo){
    	queryClientDepositAndLoanMessageSQL(clientInfo);
    	PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		pageLoader.initPageLoader(
				new AppContext(),
				m_sbFrom.toString(),
				m_sbSelect.toString(),
				m_sbWhere.toString(),
				(int) Constant.PageControl.CODE_PAGELINECOUNT,
				"com.iss.itreasury.clientmanage.client.dataentity.DepositAndLoanMessageQueryInfo",
				null);    
		pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
		return pageLoader;
    }
    
    /**
	 * 经营信息分页查询SQL
	 * @param clientInfo
	 */
	public void queryClientdealSQL(QueryCorporationInfo clientInfo){
		
		m_sbSelect = new StringBuffer();
		m_sbSelect.append("c.code,c.name,o.clientid,o.dealscope,o.productscope,o.capitalscope,o.netcapital");
		m_sbFrom = new StringBuffer();
		m_sbFrom.append(" client_clientinfo c,client_corporationinfo o");
		m_sbWhere= new StringBuffer();
		m_sbWhere.append(" c.id = o.clientid(+)");
		m_sbWhere.append(" and c.statusid ="+Constant.RecordStatus.VALID);
		m_sbWhere.append(" and c.clientbasetype = '1'");
		m_sbWhere.append(" and c.officeid = "+clientInfo.getOfficeID());
		if(clientInfo.getLClientNOStart()!=null && !clientInfo.getLClientNOStart().equals("")){
			m_sbWhere.append(" and c.code >= '"+clientInfo.getLClientNOStart()+"'");
		}
		if(clientInfo.getLClientNOEnd()!=null && !clientInfo.getLClientNOEnd().equals("")){
			m_sbWhere.append(" and c.code <= '"+clientInfo.getLClientNOEnd()+"'");
		}
		m_sbOrderBy = new StringBuffer();
		m_sbOrderBy.append("order by code asc");
		
	}
	/**
	 * 存贷款标准化信息查询
	 * @param clientInfo
	 */
	public void queryClientDepositAndLoanMessageSQL(QueryCorporationInfo clientInfo){
		m_sbSelect = new StringBuffer();
		m_sbSelect.append(" m.id,c.id clientId, c.code clientCode, c.name clientName, m.departmentname, m.componentname ");
		m_sbFrom = new StringBuffer();
		m_sbFrom.append(" client_clientinfo c left join (select m.clientid,m.id, ");
		m_sbFrom.append(" d.name departmentName,c.typename componentName ");
		m_sbFrom.append(" from client_depositAndLoanMessage m,client_economicdepartment d,client_economicComponent c ");
		m_sbFrom.append(" where m.departmentcode = d.code ");
		m_sbFrom.append(" and m.economiccomponentcode = c.code) m ");
		m_sbFrom.append(" on c.id = m.clientid ");
		m_sbWhere= new StringBuffer();
		//m_sbWhere.append(" 1=1 ");
		m_sbWhere.append(" c.clientbasetype = '1' ");
		m_sbWhere.append(" and c.statusid = 1 ");
		if(clientInfo.getOfficeID()>0)
		{
			m_sbWhere.append(" and c.officeid = "+clientInfo.getOfficeID());
		}
		if(clientInfo.getLClientNOStart()!=null && !clientInfo.getLClientNOStart().equals("")){
			m_sbWhere.append(" and c.code >= '"+clientInfo.getLClientNOStart()+"'");
		}
		if(clientInfo.getLClientNOEnd()!=null && !clientInfo.getLClientNOEnd().equals("")){
			m_sbWhere.append(" and c.code <= '"+clientInfo.getLClientNOEnd()+"'");
		}		
		m_sbOrderBy = new StringBuffer();
		m_sbOrderBy.append("order by c.code asc");
		
	}
	
	/**
	 * 业务信息分页查询
	 * @param clientInfo
	 */
	public PageLoader queryClientbusinessInfo(QueryCorporationInfo clientInfo) {
		
		queryClientbusinessSQL(clientInfo);
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		pageLoader.initPageLoader(
			new AppContext(),
			m_sbFrom.toString(),
			m_sbSelect.toString(),
			m_sbWhere.toString(),
			(int) Constant.PageControl.CODE_PAGELINECOUNT,
			"com.iss.itreasury.clientmanage.client.dataentity.ClientBusinessInfo",
			null);
		pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
		return pageLoader;
		
	}
	
	/**
	 * 业务信息分页查询SQL
	 * @param clientInfo
	 */
	public void queryClientbusinessSQL(QueryCorporationInfo clientInfo){
		
		m_sbSelect = new StringBuffer();
		m_sbSelect.append("c.code,c.name,o.clientid,o.account,o.bank1,o.extendAccount1,o.loanCardNo,o.riskLevel,o.creditLevelID,o.insideCreditLevel");
		m_sbFrom = new StringBuffer();
		m_sbFrom.append(" client_clientinfo c,client_corporationinfo o");
		m_sbWhere= new StringBuffer();
		m_sbWhere.append(" c.id = o.clientid(+)");
		m_sbWhere.append(" and c.statusid ="+Constant.RecordStatus.VALID);
		m_sbWhere.append(" and c.clientbasetype = '1'");
		m_sbWhere.append(" and c.officeid = "+clientInfo.getOfficeID());
		if(clientInfo.getLClientNOStart()!=null && !clientInfo.getLClientNOStart().equals("")){
			m_sbWhere.append(" and c.code >= '"+clientInfo.getLClientNOStart()+"'");
		}
		if(clientInfo.getLClientNOEnd()!=null && !clientInfo.getLClientNOEnd().equals("")){
			m_sbWhere.append(" and c.code <= '"+clientInfo.getLClientNOEnd()+"'");
		}
		m_sbOrderBy = new StringBuffer();
		m_sbOrderBy.append("order by code asc");
		
	}
	
	/**
	 * 联系信息分页查询
	 * @param clientInfo
	 */
    public PageLoader queryClientcontactInfo(QueryCorporationInfo clientInfo) {
		
		queryClientcontactSQL(clientInfo);
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		pageLoader.initPageLoader(
			new AppContext(),
			m_sbFrom.toString(),
			m_sbSelect.toString(),
			m_sbWhere.toString(),
			(int) Constant.PageControl.CODE_PAGELINECOUNT,
			"com.iss.itreasury.clientmanage.client.dataentity.ClientContactInfo",
			null);
		pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
		return pageLoader;
		
	}
    /**
	 * 联系信息分页查询SQL
	 * @param clientInfo
	 */
	public void queryClientcontactSQL(QueryCorporationInfo clientInfo){
		
		m_sbSelect = new StringBuffer();
		m_sbSelect.append("c.code,c.name,c.country,o.clientid,o.province,o.city");
		m_sbFrom = new StringBuffer();
		m_sbFrom.append(" client_clientinfo c,client_corporationinfo o");
		m_sbWhere= new StringBuffer();
		m_sbWhere.append(" c.id = o.clientid(+)");
		m_sbWhere.append(" and c.statusid ="+Constant.RecordStatus.VALID);
		m_sbWhere.append(" and c.clientbasetype = '1'");
		m_sbWhere.append(" and c.officeid = "+clientInfo.getOfficeID());
		if(clientInfo.getLClientNOStart()!=null && !clientInfo.getLClientNOStart().equals("")){
			m_sbWhere.append(" and c.code >= '"+clientInfo.getLClientNOStart()+"'");
		}
		if(clientInfo.getLClientNOEnd()!=null && !clientInfo.getLClientNOEnd().equals("")){
			m_sbWhere.append(" and c.code <= '"+clientInfo.getLClientNOEnd()+"'");
		}
		m_sbOrderBy = new StringBuffer();
		m_sbOrderBy.append("order by code asc");
		
	}
	
    /**
	 * 对外投资信息分页查询
	 * @param clientInfo
	 */
	public PageLoader queryClientinvestInfo(QueryCorporationInfo clientInfo) {
		
		
		queryClientinvestSQL(clientInfo);
		PageLoader pageLoader = new PageLoaderCombine();
		pageLoader.initPageLoaderCombine(
			new AppContext(),
			m_sbFrom.toString(),
			m_sbSelect.toString(),
			m_sbWhere.toString(),
			(int) Constant.PageControl.CODE_PAGELINECOUNT,
			"com.iss.itreasury.clientmanage.client.dataentity.ClientInvestInfo",
			null,
			m_sbMainCondition.toString(),
			m_sbMainTable.toString(),
			m_sbMainOrderBy.toString());
		pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
		return pageLoader;
		
	}
    /**
	 * 对外投资信息分页查询SQL
	 * @param clientInfo
	 */
	public void queryClientinvestSQL(QueryCorporationInfo clientInfo){
		
		m_sbMainTable = new StringBuffer();
		m_sbMainTable.append(" client_clientinfo ");
		
		m_sbMainCondition = new StringBuffer();
		m_sbMainCondition.append(" where statusid = 1 and clientbasetype = '1' and officeid ="+clientInfo.getOfficeID());
		if(clientInfo.getLClientNOStart()!=null && !clientInfo.getLClientNOStart().equals(""))
		{
			m_sbMainCondition.append(" and code >='"+clientInfo.getLClientNOStart()+"'");
			
		}
		if(clientInfo.getLClientNOEnd()!=null && !clientInfo.getLClientNOEnd().equals(""))
		{
			m_sbMainCondition.append(" and code <= '"+clientInfo.getLClientNOEnd()+"'");
			
		}
		m_sbMainOrderBy = new StringBuffer();
		m_sbMainOrderBy.append(" order by code ");
		
		
		m_sbSelect = new StringBuffer();
		m_sbSelect.append("a.id,a.code,a.name,");
		m_sbSelect.append("b.clientid,b.partnerid,b.partnername,b.stockCharacter,b.contributiveAmount,b.contributivecurrency,b.stockway");
		m_sbFrom = new StringBuffer();
		m_sbFrom.append("  a,");
		m_sbFrom.append(" (select c.name partnername ,f.* from client_clientinfo c, client_investofsubsidiary f where c.id(+) = f.partnerid and f.statusid = "+Constant.RecordStatus.VALID +" and f.partnertype=1 ");
		m_sbFrom.append("  union");
		m_sbFrom.append("  select c.name partnername ,f.* from client_extclientinfo c,client_investofsubsidiary f  where c.id(+) = f.partnerid  and f.statusid = "+Constant.RecordStatus.VALID +" and f.partnertype = 2 ) b ");
		m_sbWhere= new StringBuffer();
		m_sbWhere.append(" a.id = b.clientid(+)");
		
		m_sbOrderBy = new StringBuffer();
		m_sbOrderBy.append("order by a.code asc");
		
	}
    
	/**
	 * 业务信息修改
	 * @param clientBusinessInfo
	 */
	public long updateClientBusinessInfo(ClientBusinessInfo clientBusinessInfo) throws Exception{
		
		long id = -1;
		ClientmanageDAO clientmanageDAO = new ClientmanageDAO();
		clientmanageDAO.strTableName = "client_corporationinfo";
		clientmanageDAO.update(clientBusinessInfo);
		id = 1;
		return id;
	}

	/**
	 * 联系信息修改
	 * @param clientInfo
	 */
	public long updateClientContactInfo(ClientInfo clientInfo) throws Exception{
		
		long id = -1;
		ClientmanageDAO clientmanageDAO = new ClientmanageDAO();
		clientmanageDAO.update(clientInfo);
		id = 1;
		return id;
	}
	
	/**
	 * 联系信息修改
	 * @param clientContactInfo
	 */
	public long updateClientContactInfo1(ClientContactInfo clientContactInfo) throws Exception{
		
		long id = -1;
		ClientmanageDAO clientmanageDAO = new ClientmanageDAO();
		clientmanageDAO.strTableName = "client_corporationinfo";
		clientmanageDAO.update(clientContactInfo);
		id = 1;
		return id;
	}

	/**
	 * 对外投资信息修改
	 * @param dataEntity
	 */
	public long updateClientInvestInfo(ClientInvestInfo dataEntity) throws Exception{
		long id = -1;
		Connection con = null;
    	con = Database.getConnection();
    	ClientmanageDAO clientmanageDAO = new ClientmanageDAO(con);
    	clientmanageDAO.strTableName = "client_investofsubsidiary";
    	clientmanageDAO.update(dataEntity);
    	id = 1;
    	if(con != null)
    	{
    		con.close();
    		con=null;
    	}
    	return id;
    }
	
	/**
	 * 数据库逻辑删除操作
	 * @param id　　　
	 * @param 
	 * @return
	 * @throws ITreasuryDAOException
	 */	
	public void deleteClientInvestInfo(long id)  throws ITreasuryDAOException{
		//To be modify the delete status defined in Constant
		updateStatus(id, 0);
		
	}

	/**
	 * 数据库更新操作操作
	 * @param id　　　
	 * @param statusID 需要更新到的状态
	 * @return
	 * @throws ITreasuryDAOException
	 */		
	public void updateStatus(long id, long statusID) throws ITreasuryDAOException{
		try {
			initDAO();
			StringBuffer buffer = new StringBuffer();
			buffer.append("UPDATE \n");
			buffer.append("client_investofsubsidiary \n");
			buffer.append(" SET STATUSID = " + statusID);
			//TBD: maybe need add update execute date
			buffer.append("\n  WHERE ID = " + id);
			String strSQL = buffer.toString();
			log.debug(strSQL);
			prepareStatement(strSQL);
			executeQuery();
			
		} catch (ITreasuryDAOException e) {
			throw new ITreasuryDAOException("状态更新异常",e);
		}
		finally
		{
		    finalizeDAO();
		}
		
	}
	
	/**
	 * 新增对外投资信息
	 * @param dataEntity
	 */
     public long addClientInvestInfo(ClientInvestInfo dataEntity) throws Exception {
     	
     	long myid = -1;
     	Connection con = null;
     	con = Database.getConnection();
     	ClientmanageDAO clientmanageDAO = new ClientmanageDAO(con);
     	clientmanageDAO.setUseMaxID();
     	clientmanageDAO.strTableName = "client_investofsubsidiary";
     	myid = clientmanageDAO.add(dataEntity);
     	if(con != null)
     	{
     		con.close();
     		con=null;
     	}
     	return myid;
     }
	
     /**
 	 * 根据id查找联系信息
 	 */
	public ClientContactInfo findContactInformationByID(long id,long officeid) throws Exception
	{
		ClientContactInfo clientContactInfo =null;
	    Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;
		try
		{
			con = Database.getConnection();
			sbSQL = new StringBuffer();
			sbSQL.append(" SELECT c.code ClientNo, ");
			sbSQL.append(" c.name ClientName, ");
			sbSQL.append(" c.customerManagerUserID CustomerManagerUserID, ");
			sbSQL.append(" c.queryPassword QueryPassword, ");
			sbSQL.append(" c.inputDate InputDate, ");
			sbSQL.append(" c.serviceLevel ServiceLevel, ");
			sbSQL.append(" o.id ID, ");
			sbSQL.append(" o.clientid ClientId, ");
			sbSQL.append(" c.country Country, ");
			sbSQL.append(" o.province Province, ");
			sbSQL.append(" o.city City, ");
			sbSQL.append(" o.address Address, ");
			sbSQL.append(" o.phone Phone, ");
			sbSQL.append(" o.fax Fax, ");
			sbSQL.append(" o.website Website, ");
			sbSQL.append(" o.email Email,");
			sbSQL.append(" o.settlementContacter SettlementContacter,");
			sbSQL.append(" o.loanContacter LoanContacter ");
			sbSQL.append(" FROM client_clientinfo c, client_corporationinfo o ");
			sbSQL.append(" WHERE 1 = 1 ");
			sbSQL.append(" and c.id = o.clientid(+) ");
			sbSQL.append(" and c.id="+id);
			sbSQL.append(" and c.statusid =" +Constant.RecordStatus.VALID);
			sbSQL.append(" and c.clientbasetype = '1' ");
			sbSQL.append(" and c.officeid ="+officeid);
			ps = con.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();
			if(rs.next())
			{
				clientContactInfo = new ClientContactInfo();
				clientContactInfo.setCode(rs.getString("ClientNo"));
				clientContactInfo.setName(rs.getString("ClientName"));
				clientContactInfo.setClientid(rs.getLong("ClientId"));
				clientContactInfo.setAddress(rs.getString("Address"));
				clientContactInfo.setCity(rs.getString("City"));
				clientContactInfo.setCountry(rs.getString("Country"));
				clientContactInfo.setCustomerManagerUserID(rs.getLong("CustomerManagerUserID"));
				clientContactInfo.setEmail(rs.getString("Email"));
				clientContactInfo.setFax(rs.getString("Fax"));
				clientContactInfo.setId(rs.getLong("ID"));
				clientContactInfo.setInputDate(rs.getTimestamp("InputDate"));
				clientContactInfo.setLoanContacter(rs.getString("LoanContacter"));
				clientContactInfo.setPhone(rs.getString("Phone"));
				clientContactInfo.setProvince(rs.getString("Province"));
				clientContactInfo.setQueryPassword(rs.getString("QueryPassword"));
				clientContactInfo.setSettlementContacter(rs.getString("SettlementContacter"));
				clientContactInfo.setServiceLevel(rs.getLong("ServiceLevel"));
				clientContactInfo.setWebsite(rs.getString("Website"));
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;	
		}
		catch(Exception e)
		{
			log.info("ClientmanageDAO.findContactInformationByID():SQL==" + sbSQL.toString());
			log.error(e.toString());
			throw new Exception("查询出现异常");
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
				log.error(e.toString());
				throw new Exception("Gen_E001");
			}
		}
		return clientContactInfo;
	}
	
    /**
 	 * 根据id查找业务信息
 	 */  
	public ClientBusinessInfo findBusinessInformationByID(long id,long officeid) throws Exception
	{
		ClientBusinessInfo clientBusinessInfo =null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;
		try
		{
			con = Database.getConnection();
			sbSQL = new StringBuffer();
			sbSQL.append(" SELECT c.code ClientNo, ");
			sbSQL.append(" c.name ClientName, ");
			sbSQL.append(" o.id ID, ");
			sbSQL.append(" o.clientid ClientId, ");
			sbSQL.append(" o.account Account, ");
			sbSQL.append(" o.bank1 Bank1, ");
			sbSQL.append(" o.bank2 Bank2, ");
			sbSQL.append(" o.bank3 Bank3, ");
			sbSQL.append(" o.extendAccount1 ExtendAccount1, ");
			sbSQL.append(" o.extendAccount2 ExtendAccount2, ");
			sbSQL.append(" o.extendAccount3 ExtendAccount3, ");
			sbSQL.append(" o.loanCardNo LoanCardNo, ");
			sbSQL.append(" o.loanCardPwd LoanCardPwd, ");
			sbSQL.append(" o.riskLevel RiskLevel, ");
			sbSQL.append(" o.talentLevel TalentLevel, ");
			sbSQL.append(" o.creditLevelID CreditLevelID, ");
			sbSQL.append(" o.judgeLevelClient JudgeLevelClient, ");
			sbSQL.append(" o.creditLevelDate CreditLevelDate, ");
			sbSQL.append(" o.insideCreditLevel InsideCreditLevel, ");
			sbSQL.append(" o.assessMark AssessMark ");
			sbSQL.append(" FROM client_clientinfo c, client_corporationinfo o ");
			sbSQL.append(" WHERE 1 = 1 ");
			sbSQL.append(" and c.id = o.clientid(+) ");
			sbSQL.append(" and c.id="+id);
			sbSQL.append(" and c.statusid =" +Constant.RecordStatus.VALID);
			sbSQL.append(" and c.clientbasetype = '1' ");
			sbSQL.append(" and c.officeid ="+officeid);
			ps = con.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();
			if(rs.next())
			{
				clientBusinessInfo = new ClientBusinessInfo();
				clientBusinessInfo.setCode(rs.getString("ClientNo"));
				clientBusinessInfo.setName(rs.getString("ClientName"));
				clientBusinessInfo.setId(rs.getLong("ID"));
				clientBusinessInfo.setClientid(rs.getLong("ClientId"));
				clientBusinessInfo.setAccount(rs.getString("Account"));
				clientBusinessInfo.setBank1(rs.getString("Bank1"));
				clientBusinessInfo.setBank2(rs.getString("Bank2"));
				clientBusinessInfo.setBank3(rs.getString("Bank3"));
				clientBusinessInfo.setExtendAccount1(rs.getString("ExtendAccount1"));
				clientBusinessInfo.setExtendAccount2(rs.getString("ExtendAccount2"));
				clientBusinessInfo.setExtendAccount3(rs.getString("ExtendAccount3"));
				clientBusinessInfo.setLoanCardNo(rs.getString("LoanCardNo"));
				clientBusinessInfo.setLoanCardPwd(rs.getString("LoanCardPwd"));
				clientBusinessInfo.setRiskLevel(rs.getString("RiskLevel"));
				clientBusinessInfo.setTalentLevel(rs.getString("TalentLevel"));
				clientBusinessInfo.setCreditLevelID(rs.getString("CreditLevelID"));
				clientBusinessInfo.setJudgeLevelClient(rs.getString("JudgeLevelClient"));
				clientBusinessInfo.setCreditLevelDate(rs.getTimestamp("CreditLevelDate"));
				clientBusinessInfo.setInsideCreditLevel(rs.getString("InsideCreditLevel"));
				clientBusinessInfo.setAssessMark(rs.getDouble("AssessMark"));
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;	
		}
		catch(Exception e)
		{
			log.info("ClientmanageDAO.findBusinessInformationByID():SQL==" + sbSQL.toString());
			log.error(e.toString());
			throw new Exception("查询出现异常");
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
				log.error(e.toString());
				throw new Exception("Gen_E001");
			}
		}
		return clientBusinessInfo;
	}
	
    /**
 	 * 根据id查找经营信息
 	 */
	public ClientDealInfo findDealInformationByID(long id,long officeid) throws Exception
	{
		ClientDealInfo clientDealInfo =null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;
		try
		{
			con = Database.getConnection();
			sbSQL = new StringBuffer();
			sbSQL.append(" SELECT c.code ClientNo, ");
			sbSQL.append(" c.name ClientName, ");
			sbSQL.append(" o.id ID, ");
			sbSQL.append(" o.clientid ClientId, ");
			sbSQL.append(" o.dealscope DealScope, ");
			sbSQL.append(" o.productscope ProductScope, ");
			sbSQL.append(" o.capitalscope CapitalScope, ");
			sbSQL.append(" o.netcapital NetCapital, ");
			sbSQL.append(" o.products Products, ");
			sbSQL.append(" o.operations Operations, ");
			sbSQL.append(" o.builddate BuildDate,");
			sbSQL.append(" o.employeenumber EmployeeNumber ");
			sbSQL.append(" FROM client_clientinfo c, client_corporationinfo o ");
			sbSQL.append(" WHERE 1 = 1 ");
			sbSQL.append(" and c.id = o.clientid(+) ");
			sbSQL.append(" and c.id="+id);
			sbSQL.append(" and c.statusid =" +Constant.RecordStatus.VALID);
			sbSQL.append(" and c.clientbasetype = '1' ");
			sbSQL.append(" and c.officeid ="+officeid);
			ps = con.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();
			if(rs.next())
			{
				clientDealInfo = new ClientDealInfo();
				clientDealInfo.setCode(rs.getString("ClientNo"));
				clientDealInfo.setName(rs.getString("ClientName"));
				clientDealInfo.setId(rs.getLong("ID"));
				clientDealInfo.setClientid(rs.getLong("ClientId"));
				clientDealInfo.setDealScope(rs.getString("DealScope"));
				clientDealInfo.setProductScope(rs.getString("ProductScope"));
				clientDealInfo.setCapitalScope(rs.getString("CapitalScope"));
				clientDealInfo.setNetCapital(rs.getString("NetCapital"));
				clientDealInfo.setProducts(rs.getString("Products"));
				clientDealInfo.setOperations(rs.getString("Operations"));
				clientDealInfo.setBuildDate(rs.getTimestamp("BuildDate"));
				clientDealInfo.setEmployeeNumber(rs.getLong("EmployeeNumber"));
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
		}
		catch(Exception e)
		{
			log.info("ClientmanageDAO.findDealInformationByID():SQL==" + sbSQL.toString());
			log.error(e.toString());
			throw new Exception("查询出现异常");
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
				log.error(e.toString());
				throw new Exception("Gen_E001");
			}
		}
		return clientDealInfo;
	}
	
	/**
	 * 经营信息修改
	 * @param info
	 */
	public long updateClientDealInfo(ClientDealInfo info) throws Exception
	{
		//定义变量
		String strSQL = null;
		long lResult = -1;
		Connection con = null;
		PreparedStatement ps = null;

		try
		{
			if(info.getClientid() > 0)
			{
				con = Database.getConnection();

				StringBuffer sbCondition = new StringBuffer();

				if (info.getDealScope() != null)
				{
					sbCondition.append("dealscope = ? ,");
				}
				if (info.getProductScope() != null)
				{
					sbCondition.append("productscope = ? ,");
				}

				if (info.getCapitalScope() != null)
				{
					sbCondition.append("capitalscope = ? ,");
				}

				if (info.getNetCapital() != null)
				{
					sbCondition.append("netcapital = ? ,");
				}

				if (info.getProducts() != null)
				{
					sbCondition.append("products = ? ,");
				}
                    
				if (info.getOperations() != null)
				{
					sbCondition.append("operations = ? ,");
				}
					
			    //if (info.getBuildDate() != null)
				//{
					sbCondition.append("builddate = ? ,");
				//}
					
				sbCondition.append("employeenumber = ? ");

				strSQL = " UPDATE client_corporationinfo SET " + sbCondition.toString() + "WHERE id=? ";

				log.info("strSQL = " + strSQL);
				ps = con.prepareStatement(strSQL);
				int lIndex = 1;

				if (info.getDealScope() != null)
				{
					ps.setString(lIndex++, info.getDealScope());
				}

				if (info.getProductScope() != null)
				{
					ps.setString(lIndex++, info.getProductScope());
				}

				if (info.getCapitalScope() != null)
				{
					ps.setString(lIndex++, info.getCapitalScope());
				}

				if (info.getNetCapital() != null)
				{
					ps.setString(lIndex++, info.getNetCapital());
				}

				if (info.getProducts() != null)
				{
					ps.setString(lIndex++, info.getProducts());
				}
					
				if (info.getOperations() != null)
				{
					ps.setString(lIndex++, info.getOperations());
				}
					
				//if (info.getBuildDate() != null)
				//{
					ps.setTimestamp(lIndex++, info.getBuildDate());
				//}
				ps.setLong(lIndex++, info.getEmployeeNumber() );
					
				ps.setLong(lIndex++, info.getId());
				lResult = ps.executeUpdate();

				//关闭资源
				ps.close();
				ps = null;
				con.close();
				con = null;
			}
			else
			{
				lResult = 0;
			}

		}
		catch (Exception e)
		{
			log.error(e.toString());
			throw new IException("Gen_E001");
		}
		finally
		{
			try
			{
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception ex)
			{
				log.error(ex.toString());
				throw new IException("Gen_E001");
			}
		}
		return lResult;
	}
	
	/**
     *获得属于某一客户的所有对外投资信息
     * */
     public Collection findByClientID(long clientID) throws ITreasuryException{
     	
     	Collection data = null;
     	
     	try
		{
			
			StringBuffer sb = new StringBuffer();
			sb.append(" select c.name partnername,f.*");
			sb.append(" from client_clientinfo c, client_InvestOfSubsidiary f ");
			sb.append(" where c.id(+) = f.partnerid");
			sb.append(" and f.ClientID = ?  and  (f.statusid is null or f.statusid <> 0) and f.partnertype=1 ");
			sb.append(" union ");
			sb.append(" select c.name partnername, f.* ");
			sb.append(" from client_extclientinfo c, client_InvestOfSubsidiary f ");
			sb.append(" where c.id(+) = f.partnerid ");
			sb.append(" and f.ClientID = ?  and  (f.statusid is null or f.statusid <> 0) and f.partnertype=2 ");
			
					
			transPS = transConn.prepareStatement(sb.toString());
			transPS.setLong(1, clientID);
			transPS.setLong(2, clientID);
			transRS = transPS.executeQuery();
			
			data=getDataEntitiesFromResultSet(new ClientInvestInfo().getClass());
		}
		catch (Exception e)
		{
			log.error(e.toString());
			throw new ITreasuryException();
		}
		finalizeDAO();
		return data;
     }
     
     /**
      *获得属于某一客户的某一对外投资信息
      * */
     public ClientInvestInfo findClientInvestInfoById(long id) throws Exception{
     	
     	ClientInvestInfo oneinvestinfo = new ClientInvestInfo();
     	Connection con = null;
     	con = Database.getConnection();
     	ClientmanageDAO clientmanageDAO = new ClientmanageDAO(con);
     	clientmanageDAO.strTableName = "client_InvestOfSubsidiary";
     	oneinvestinfo =(ClientInvestInfo) clientmanageDAO.findByID(id,oneinvestinfo.getClass());
     	if(con != null)
     	{
     		con.close();
     		con=null;
     	}
     	return oneinvestinfo;
     	
     }

	/**
	 * 客户基本信息查询--分页
	 * @param clientInfo
	 * @return
	 */
	public PageLoader queryClientmanageInfo(QueryCorporationInfo clientInfo) {
		
		queryClientmanageSQL(clientInfo);
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		pageLoader.initPageLoader(
			new AppContext(),
			m_sbFrom.toString(),
			m_sbSelect.toString(),
			m_sbWhere.toString(),
			(int) Constant.PageControl.CODE_PAGELINECOUNT,
			"com.iss.itreasury.clientmanage.client.dataentity.ClientBaseInfo",
			null);
		pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
		return pageLoader;
		
	}
	
	/**
	 * 客户基本信息查询--分页SQL
	 * @param clientInfo
	 * @return
	 */
	public void queryClientmanageSQL(QueryCorporationInfo clientInfo){
		
		m_sbSelect = new StringBuffer();
		m_sbSelect.append("c.id,code,name,(select name from client_clientinfo where id = parentcorpid1) parentCorpName,budgetparent");
		m_sbFrom = new StringBuffer();
		m_sbFrom.append(" client_clientinfo c,client_corporationinfo o");
		m_sbWhere= new StringBuffer();
		m_sbWhere.append(" c.id = o.clientid(+)");
		m_sbWhere.append(" and c.statusid ="+Constant.RecordStatus.VALID);
		m_sbWhere.append(" and c.clientbasetype = '"+CMConstant.ClientBaseType.CORPORATION+"'");
		m_sbWhere.append(" and c.officeid = "+clientInfo.getOfficeID());
		if(clientInfo.getLClientNOStart()!=null && !clientInfo.getLClientNOStart().equals("")){
			m_sbWhere.append(" and c.code >= '"+clientInfo.getLClientNOStart()+"'");
		}
		if(clientInfo.getLClientNOEnd()!=null && !clientInfo.getLClientNOEnd().equals("")){
			m_sbWhere.append(" and c.code <= '"+clientInfo.getLClientNOEnd()+"'");
		}
		m_sbOrderBy = new StringBuffer();
		m_sbOrderBy.append("order by code asc");
		
	}
	
	/**
	 * 企业管理层信息分页查询
	 * @param clientInfo
	 * @return
	 */
	public PageLoader querymanagerInfo(QueryCorporationInfo clientInfo) {
		
		querymanagerInfoSQL(clientInfo);
		PageLoader pageLoader = new PageLoaderCombine();
		pageLoader.initPageLoaderCombine(
			new AppContext(),
			m_sbFrom.toString(),
			m_sbSelect.toString(),
			m_sbWhere.toString(),
			(int) Constant.PageControl.CODE_PAGELINECOUNT,
			"com.iss.itreasury.clientmanage.client.dataentity.ManagerInfo",
			null,
			m_sbMainCondition.toString(),
			m_sbMainTable.toString(),
			m_sbMainOrderBy.toString());
		pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
		return pageLoader;
		
	}
	/**
	 * 企业管理层信息分页查询
	 * @param clientInfo
	 */
	public void querymanagerInfoSQL(QueryCorporationInfo clientInfo){
		m_sbMainTable = new StringBuffer();
		m_sbMainTable.append(" client_clientinfo ");
		m_sbMainCondition = new StringBuffer();
		m_sbMainCondition.append(" where statusid = 1 and clientbasetype = '1' and officeid ="+clientInfo.getOfficeID());
		if(clientInfo.getLClientNOStart()!=null && !clientInfo.getLClientNOStart().equals("")){
			m_sbMainCondition.append(" and code >= '"+clientInfo.getLClientNOStart()+"'");
		}
		if(clientInfo.getLClientNOEnd()!=null && !clientInfo.getLClientNOEnd().equals("")){
			m_sbMainCondition.append(" and code <= '"+clientInfo.getLClientNOEnd()+"'");
		}
		m_sbMainOrderBy = new StringBuffer();
		m_sbMainOrderBy.append(" order by code ");
		m_sbSelect = new StringBuffer();
		m_sbSelect.append(" a.mail, \n"); 
		m_sbSelect.append("a.managername , \n");
		m_sbSelect.append(" a.position,f.code,f.name,a.id,f.id client_info_id \n");
		m_sbFrom = new StringBuffer();
		m_sbFrom.append(" f , ");
		m_sbFrom.append("( select * from client_management where client_management.statusid = 1 ) a ");
		m_sbWhere= new StringBuffer();
		m_sbWhere.append(" f.id = a.clientid(+)");
		m_sbWhere.append(" and f.officeid = "+clientInfo.getOfficeID());
	
		m_sbOrderBy = new StringBuffer();
		m_sbOrderBy.append("order by f.code asc");
	}	
	public Collection query_ManagerInfo(ClientInfo clientinfo) throws Exception
	{
		ManagerInfo info = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Collection vReturn = new ArrayList();
		StringBuffer sbSQL = new StringBuffer();
		try
		{
			con = Database.getConnection();
			sbSQL.append("select a.mail,a.tel,a.managername managername,a.position,b.code,b.id clientid,b.name,a.abstract beizhu,a.id,b.id client_info_id \n");
			sbSQL.append("from ( select * from client_clientinfo where client_clientinfo.statusid = 1 ) b , ");
			sbSQL.append(" ( select * from client_management where client_management.statusid = 1 ) a  ");
			sbSQL.append(" 	where a.clientid(+) = b.id and b.code = '"+clientinfo.getCode()+"' ");
			sbSQL.append(" and b.statusid="+Constant.RecordStatus.VALID+" and b.clientbasetype = '1' and b.officeid ="+clientinfo.getOfficeID()+"");	
			System.out.print("SQL="+sbSQL);
			ps = con.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();
			while(rs.next())
			{     
				info = new ManagerInfo();
				info.setMail((rs.getString("mail")));	
				info.setManagername((rs.getString("managername")));
				info.setPosition((rs.getString("position")));
				info.setTel((rs.getString("tel")));
				info.setClientid(rs.getLong("clientid"));
				info.setCode(rs.getString("code"));
				info.setName(rs.getString("name"));
				info.setBeizhu(rs.getString("beizhu"));
				info.setId(rs.getLong("id"));
				info.setClient_info_id(rs.getLong("client_info_id"));
				System.out.print(info.getClient_info_id());
				vReturn.add(info);	
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
		}
		catch (Exception e)
		{
			throw new IException("查询出现异常");
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
				throw new IException("Gen_E001");
			}
		}
		return vReturn;
		
		
	}
	/**
	 * 附件信息分页查询
	 * @param annexinfo
	 * @return
	 */
    public PageLoader queryannexInfo(QueryCorporationInfo annexinfo) {
		
    	queryannexInfoSQL(annexinfo);
    	PageLoader pageLoader = new PageLoaderCombine();
		pageLoader.initPageLoaderCombine(
			new AppContext(),
			m_sbFrom.toString(),
			m_sbSelect.toString(),
			m_sbWhere.toString(),
			(int) Constant.PageControl.CODE_PAGELINECOUNT,
			"com.iss.itreasury.clientmanage.client.dataentity.AnnexInfo",
			null,
			m_sbMainCondition.toString(),
			m_sbMainTable.toString(),
			m_sbMainOrderBy.toString());
		pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
		return pageLoader;
		
	}
    /**
	 * 附件信息分页查询SQL
	 * @param annexinfo
	 * @return
	 */
	public void queryannexInfoSQL(QueryCorporationInfo annexinfo){
		m_sbMainTable = new StringBuffer();
		m_sbMainTable.append(" client_clientinfo ");
		
		m_sbMainCondition = new StringBuffer();
		m_sbMainCondition.append(" where statusid = 1 and clientbasetype = '1' and officeid ="+annexinfo.getOfficeID());
		if(annexinfo.getLClientNOStart()!=null && !annexinfo.getLClientNOStart().equals("")){
			m_sbMainCondition.append(" and code >= '"+annexinfo.getLClientNOStart()+"'");
		}
		if(annexinfo.getLClientNOEnd()!=null && !annexinfo.getLClientNOEnd().equals("")){
			m_sbMainCondition.append(" and code <= '"+annexinfo.getLClientNOEnd()+"'");
		}
		m_sbMainOrderBy = new StringBuffer();
		m_sbMainOrderBy.append(" order by code ");
		
		m_sbSelect = new StringBuffer();
		m_sbSelect.append(" a.code,a.name,a.id,b.nfileid,( select fileInfo.Sclientfilename from fileInfo where fileInfo.Id=b.nfileid and fileInfo.nstatus = 1 ) sclientfilename, \n");
		m_sbSelect.append(" (select distinct client_corporationinfo.signstart from client_corporationinfo  where client_corporationinfo.clientid=a.id)  signstart \n");       
		m_sbFrom = new StringBuffer();
		m_sbFrom.append(" a , ");
		m_sbFrom.append(" ( select * from loan_docInfo where loan_docInfo.Nstatusid = 1 ) b  ");
		m_sbWhere= new StringBuffer();
		m_sbWhere.append(" a.id=b.clientid(+) and a.officeid = "+annexinfo.getOfficeID());
		m_sbWhere.append(" and a.clientbasetype = '1'");
		
		m_sbOrderBy = new StringBuffer();
		m_sbOrderBy.append("order by a.code asc");
	}	

	//上市信息分页查询
	public PageLoader queryCorporationMarket(QueryCorporationInfo queryCorporationInfo) throws Exception
	{
		getSQLCorporationMarket(queryCorporationInfo);
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		pageLoader.initPageLoader(new AppContext(),   
				m_sbFrom.toString(),
				m_sbSelect.toString(),
				m_sbWhere.toString(),
				(int) Constant.PageControl.CODE_PAGELINECOUNT,
				"com.iss.itreasury.clientmanage.client.dataentity.CorporationExtendsInfo",
				null);
		pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
		return pageLoader;
		
		
	}
	
	//股东信息分页查询
	public PageLoader queryShareholderInformation(QueryCorporationInfo queryCorporationInfo)throws Exception
	{
		
		getSQLShareholderInformation(queryCorporationInfo);
		PageLoader pageLoader = new PageLoaderCombine();
		pageLoader.initPageLoaderCombine(new AppContext(),   
				m_sbFrom.toString(),
				m_sbSelect.toString(),
				m_sbWhere.toString(),
				(int) Constant.PageControl.CODE_PAGELINECOUNT,
				"com.iss.itreasury.clientmanage.client.dataentity.StockPartnerInfo",
				null,
				m_sbMainCondition.toString(),
				m_sbMainTable.toString(),
				m_sbMainOrderBy.toString());
		pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
		return pageLoader;
		
	}
	
	//财务报表分页查询
	public PageLoader queryClientReport(QueryCorporationInfo queryCorporationInfo)throws Exception
	{
		
		getSQLClientReport(queryCorporationInfo);
		PageLoader pageLoader = new PageLoaderCombine();
		pageLoader.initPageLoaderCombine(new AppContext(),   
				m_sbFrom.toString(),
				m_sbSelect.toString(),
				m_sbWhere.toString(),
				(int) Constant.PageControl.CODE_PAGELINECOUNT,
				"com.iss.itreasury.clientmanage.client.dataentity.QueryClientUploadReportInfo",
				null,
				m_sbMainCondition.toString(),
				m_sbMainTable.toString(),
				m_sbMainOrderBy.toString());
		pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
		return pageLoader;
		
	}
	
	
	//上市信息--根据id查找其他相关信息
	public CorporationExtendsInfo findMarketInformationByID(long id,long officeid) throws Exception
	{
		CorporationExtendsInfo corporationExtendsInfo =null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;
		try
		{
			con = Database.getConnection();
			sbSQL = new StringBuffer();
			sbSQL.append(" SELECT c.code lClientNo, ");
			sbSQL.append(" c.name lClientName, ");
			sbSQL.append(" o.ismarkcompany isMarkCompany, ");
			sbSQL.append(" o.markplace1 lMarketSpace1, ");
			sbSQL.append(" o.stockno1 lStockNo1, ");
			sbSQL.append(" o.markplace2 lMarketSpace2, ");
			sbSQL.append(" o.stockno2 lStockNo2, ");
			sbSQL.append(" o.markplace3 lMarketSpace3,");
			sbSQL.append(" o.stockno3 lStockNo3, ");
			sbSQL.append(" o.markplace4 lMarketSpace4, ");
			sbSQL.append(" o.stockno4 lStockNo4, ");
			sbSQL.append(" o.markplace5 lMarketSpace5, ");
			sbSQL.append(" o.stockno5 lStockNo5 ");
			sbSQL.append(" FROM client_clientinfo c, client_corporationinfo o ");
			sbSQL.append(" WHERE 1 = 1 ");
			sbSQL.append(" and c.id = o.clientid(+) ");
			sbSQL.append(" and c.id="+id);
			sbSQL.append(" and c.statusid = 1 ");
			sbSQL.append(" and c.clientbasetype = '1' ");
			sbSQL.append(" and c.officeid ="+officeid);
			sbSQL.append(" order by c.code ");
			ps = con.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();
			if(rs.next())
			{
				corporationExtendsInfo = new CorporationExtendsInfo();
				corporationExtendsInfo.setLClientNo(rs.getString("lClientNo"));
				corporationExtendsInfo.setLClientName(rs.getString("lClientName"));
				corporationExtendsInfo.setIsMarkCompany(rs.getLong("isMarkCompany"));
				corporationExtendsInfo.setLMarketSpace1(rs.getString("lMarketSpace1"));
				corporationExtendsInfo.setLStockNo1(rs.getString("lStockNo1"));
				corporationExtendsInfo.setLMarketSpace2(rs.getString("lMarketSpace2"));
				corporationExtendsInfo.setLStockNo2(rs.getString("lStockNo2"));
				corporationExtendsInfo.setLMarketSpace3(rs.getString("lMarketSpace3"));
				corporationExtendsInfo.setLStockNo3(rs.getString("lStockNo3"));
				corporationExtendsInfo.setLMarketSpace4(rs.getString("lMarketSpace4"));
				corporationExtendsInfo.setLStockNo4(rs.getString("lStockNo4"));
				corporationExtendsInfo.setLMarketSpace5(rs.getString("lMarketSpace5"));
				corporationExtendsInfo.setLStockNo5(rs.getString("lStockNo5"));
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
			
		}
		catch(Exception e)
		{
			log.info("FinanceInstrEJB.query():SQL==" + sbSQL.toString());
			log.error(e.toString());
			throw new Exception("查询出现异常");
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
				log.error(e.toString());
				throw new Exception("Gen_E001");
			}
		}
		return corporationExtendsInfo;
	}
	
	//股东信息--根据id查询相关信息
	public ArrayList findStockPartnerInformationByID(long id,long officeid)throws Exception
	{
		ArrayList list = new ArrayList();
		StockPartnerInfo stockPartnerInfo = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;
		try
		{
			con = Database.getConnection();
			sbSQL = new StringBuffer();
			sbSQL.append(" select * from ( SELECT p.id lID,c.id clientid, p.partnerid partnerid,c.name clientName,");
			sbSQL.append(" pc.name stockholderNameCHN, pc.engname stockholderNameEN, ");
			sbSQL.append(" p.stockcharacter stockCharacter,p.stockway stockWay,");
			sbSQL.append(" p.contributivecurrency contributiveCurrency,p.contributiveamount contributiveAmount, ");
			sbSQL.append(" p.stockrate stockRate,p.isstockhoder isStockHoder,c.code code,p.stockHoldertype stockHoldertype ");
			sbSQL.append(" FROM Client_ClientInfo c, Client_PartnerOfClient p, Client_ClientInfo pc ");
			sbSQL.append(" WHERE 1 = 1 ");
			sbSQL.append(" and c.id = p.clientid(+) ");
			sbSQL.append(" and p.partnerid = pc.id(+) ");
			sbSQL.append(" and c.statusid = 1 ");
			sbSQL.append(" and c.clientbasetype = '1' ");
			sbSQL.append(" and p.statusid=1 ");
			sbSQL.append(" and p.stockholdertype=1 ");
			sbSQL.append(" and c.officeid ="+officeid);
			sbSQL.append(" and c.id ="+id);
			sbSQL.append(" union ");
			sbSQL.append(" SELECT p.id lID,c.id clientid, p.partnerid partnerid,c.name clientName, ");
			sbSQL.append(" pc.name stockholderNameCHN, pc.engname stockholderNameEN, ");
			sbSQL.append(" p.stockcharacter stockCharacter,p.stockway stockWay,");
			sbSQL.append(" p.contributivecurrency contributiveCurrency,p.contributiveamount contributiveAmount, ");
			sbSQL.append(" p.stockrate stockRate,p.isstockhoder isStockHoder,c.code code,p.stockHoldertype stockHoldertype");
			sbSQL.append(" FROM Client_ClientInfo c, Client_PartnerOfClient p, client_extclientinfo pc ");
			sbSQL.append(" WHERE 1 = 1");
			sbSQL.append(" and c.id = p.clientid(+) ");
			sbSQL.append(" and p.partnerid = pc.id(+) ");
			sbSQL.append(" and c.statusid = 1 ");
			sbSQL.append(" and c.clientbasetype = '1' ");
			sbSQL.append(" and p.statusid = 1 ");
			sbSQL.append(" and p.stockholdertype=2 ");
			sbSQL.append(" and c.officeid ="+officeid);
			sbSQL.append(" and c.id ="+id+" )");
			sbSQL.append(" where 1=1  ");
			sbSQL.append(" order by code  ");
			System.out.println("sql="+sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			rs=ps.executeQuery();
			while(rs.next())
			{
				stockPartnerInfo = new StockPartnerInfo();
				stockPartnerInfo.setLID(rs.getLong("lID"));
				stockPartnerInfo.setClientid(rs.getLong("clientid"));
				stockPartnerInfo.setPartnerid(rs.getLong("partnerid"));
				stockPartnerInfo.setClientName(rs.getString("clientName"));
				stockPartnerInfo.setStockholderNameCHN(rs.getString("stockholderNameCHN"));
				stockPartnerInfo.setStockholderNameEN(rs.getString("stockholderNameEN"));
				stockPartnerInfo.setStockCharacter(rs.getString("stockCharacter"));
				stockPartnerInfo.setStockWay(rs.getString("stockWay"));
				stockPartnerInfo.setContributiveAmount(rs.getString("contributiveAmount"));
				stockPartnerInfo.setContributiveCurrency(rs.getString("contributiveCurrency"));
				stockPartnerInfo.setStockRate(rs.getDouble("stockRate"));
				stockPartnerInfo.setIsStockholder(rs.getLong("isStockHoder"));
				stockPartnerInfo.setStockHoldertype(rs.getLong("stockHoldertype"));
				list.add(stockPartnerInfo);
				
			}
			
		}
		catch(Exception e)
		{
			log.error(e.toString());
			throw new Exception("查询出现异常");
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
				log.error(e.toString());
				throw new Exception("Gen_E001");
			}
		}
		return list;
		
	}
	
	//上市信息分页查询sql
	public void getSQLCorporationMarket(QueryCorporationInfo queryCorporationInfo) throws Exception
	{
		try{
			//select 
			m_sbSelect = new StringBuffer();
			m_sbSelect.append(" c.id lClientID,c.code lClientNo, c.name lClientName, o.markplace1 lMarketSpace1,o.stockno1 lStockNo1,o.markplace2 lMarketSpace2,o.stockno2 lStockNo2,o.markplace3 lMarketSpace3,o.stockno3 lStockNo3,o.markplace4 lMarketSpace4,o.stockno4 lStockNo4,o.markplace5 lMarketSpace5,o.stockno5 lStockNo5 ");
			System.out.println("select "+m_sbSelect.toString());
			
			//from
			m_sbFrom = new StringBuffer();
			m_sbFrom.append(" client_clientinfo c, client_corporationinfo o ");
			System.out.println("From "+m_sbFrom.toString());
			
			//where
			m_sbWhere = new StringBuffer();
			m_sbWhere.append(" 1=1 ");
			m_sbWhere.append(" and c.id = o.clientid(+) ");
			m_sbWhere.append(" and c.statusid = 1 ");
			m_sbWhere.append(" and c.officeid ="+queryCorporationInfo.getOfficeID());
			m_sbWhere.append(" and c.clientbasetype = '1' ");
			if(queryCorporationInfo.getLClientNOStart()!=null && !queryCorporationInfo.getLClientNOStart().equals(""))
			{
				m_sbWhere.append(" and c.code>='"+queryCorporationInfo.getLClientNOStart()+"'");
			}
			if(queryCorporationInfo.getLClientNOEnd()!=null && !queryCorporationInfo.getLClientNOEnd().equals(""))
			{
				m_sbWhere.append(" and c.code<='"+queryCorporationInfo.getLClientNOEnd()+"'" );
			}
			System.out.println("Where "+m_sbWhere.toString());
			
			//order by
			m_sbOrderBy = new StringBuffer();
			m_sbOrderBy.append(" order by c.code ");
			System.out.println(m_sbOrderBy);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println(e.toString());
		}
	}
	
	public void getSQLShareholderInformation(QueryCorporationInfo queryCorporationInfo) throws Exception
	{
		try
		{
			m_sbMainTable = new StringBuffer();
			m_sbMainTable.append(" client_clientinfo ");
			
			m_sbMainCondition = new StringBuffer();
			m_sbMainCondition.append(" where statusid = 1 and clientbasetype = '1' and officeid ="+queryCorporationInfo.getOfficeID());
			
			if(queryCorporationInfo.getLClientNOStart()!=null && !queryCorporationInfo.getLClientNOStart().equals(""))
			{
				m_sbMainCondition.append(" and code>='"+queryCorporationInfo.getLClientNOStart()+"'");
			}
			if(queryCorporationInfo.getLClientNOEnd()!=null && !queryCorporationInfo.getLClientNOEnd().equals(""))
			{
				m_sbMainCondition.append(" and code<='"+queryCorporationInfo.getLClientNOEnd()+"'" );
			}
			m_sbMainOrderBy = new StringBuffer();
			m_sbMainOrderBy.append(" order by code ");
			
			//select 
			m_sbSelect = new StringBuffer();
			m_sbSelect.append(" c.id clientid,p.partnerid partnerid,c.code clientNo,c.name clientName,p.stockholderNameCHN stockholderNameCHN,p.stockcharacter stockCharacter,p.stockway stockWay,p.contributivecurrency contributiveCurrency,p.contributiveamount contributiveAmount,p.stockrate stockRate,p.isstockholder isStockholder,c.code code,c.name name ");
			System.out.println("select "+m_sbSelect.toString());
			
			//from
			m_sbFrom = new StringBuffer();
			m_sbFrom.append(" c, (select pc.name stockholderNameCHN,p.stockcharacter stockCharacter,p.stockway stockWay,p.contributivecurrency contributiveCurrency,p.contributiveamount contributiveAmount,p.stockrate stockRate,p.isstockhoder isstockholder,p.partnerid partnerid,p.clientid clientid ");
			m_sbFrom.append(" from Client_PartnerOfClient p, Client_ClientInfo pc where p.partnerid = pc.id(+) and p.statusid(+) = 1 and p.stockholdertype = 1");
			m_sbFrom.append("  union ");
			m_sbFrom.append(" select pc.name stockholderNameCHN,p.stockcharacter stockCharacter,p.stockway stockWay,p.contributivecurrency contributiveCurrency,p.contributiveamount contributiveAmount,p.stockrate stockRate,p.isstockhoder isstockholder,p.partnerid partnerid,p.clientid clientid ");
			m_sbFrom.append("  from Client_PartnerOfClient p, client_extclientinfo pc where p.partnerid = pc.id(+) and p.statusid(+) = 1 and p.stockholdertype = 2) p ");
			System.out.println("From "+m_sbFrom.toString());
			
			//where
			m_sbWhere = new StringBuffer();
			m_sbWhere.append(" 1=1 ");
			m_sbWhere.append(" and c.id = p.clientid(+) ");
			System.out.println("Where "+m_sbWhere.toString());
			
			//order by
			m_sbOrderBy = new StringBuffer();
			m_sbOrderBy.append(" order by c.code ");
			System.out.println(m_sbOrderBy);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println(e.toString());
		}
	}
	
	//判断股东信息查询中同一个客户是否有相同的股东
	public boolean isStockClientNoRepeat(PartnerInfo partnerInfo) throws Exception
	{
		boolean exists = false;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;
		try
		{
			con = Database.getConnection();
			sbSQL = new StringBuffer();
			sbSQL.append(" select c.partnerid  ");
			sbSQL.append(" from Client_PartnerOfClient c ");
			sbSQL.append(" where c.statusid=1 ");
			sbSQL.append(" and c.clientid="+partnerInfo.getClientid());
			sbSQL.append(" and c.partnerid="+partnerInfo.getPartnerid());
			sbSQL.append(" and c.stockholdertype="+partnerInfo.getStockHoldertype());
			
			sbSQL.append(" and id<>"+partnerInfo.getId());
			System.out.println("sql="+sbSQL.toString());
			ps=con.prepareStatement(sbSQL.toString());
			rs=ps.executeQuery();
			if(rs.next())
			{
				exists = true;
			}
		}
		catch(Exception e)
		{
			log.error(e.toString());
			throw new Exception("查询出现异常");
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
				log.error(e.toString());
				throw new Exception("Gen_E001");
			}
		}
		
		return exists;
	}
	
	//财务报表分页查询sql
	public void getSQLClientReport(QueryCorporationInfo queryCorporationInfo) throws Exception
	{
		try
		{
			m_sbMainTable = new StringBuffer();
			m_sbMainTable.append(" client_clientinfo ");
			
			m_sbMainCondition = new StringBuffer();
			m_sbMainCondition.append("  where statusid = 1 and clientbasetype = '1' and officeid ="+queryCorporationInfo.getOfficeID());
			if(queryCorporationInfo.getLClientNOStart()!=null && !queryCorporationInfo.getLClientNOStart().equals(""))
			{
				m_sbMainCondition.append(" and code>='"+queryCorporationInfo.getLClientNOStart()+"'");
			}
			if(queryCorporationInfo.getLClientNOEnd()!=null && !queryCorporationInfo.getLClientNOEnd().equals(""))
			{
				m_sbMainCondition.append(" and code<='"+queryCorporationInfo.getLClientNOEnd()+"'" );
			}
			
			m_sbMainOrderBy = new StringBuffer();
			m_sbMainOrderBy.append(" order by code ");
			//select 
			m_sbSelect = new StringBuffer();
			m_sbSelect.append(" c.id clientID,r.id reportID,c.code clientNo,c.name clientName,r.reportname reportName,r.reporttype reportTypeID,r.reportdate reportDate, r.sdocpath sDocPath ");
			System.out.println("select "+m_sbSelect.toString());
			
			//from
			m_sbFrom = new StringBuffer();
			m_sbFrom.append(" c,(select * from CLIENT_UPLOADREPORT r where r.nstatusid=1) r");
			System.out.println("From "+m_sbFrom.toString());
			
			//where
			m_sbWhere = new StringBuffer();
			m_sbWhere.append(" 1=1 ");
			m_sbWhere.append(" and c.id=r.clientid(+) ");
	
			System.out.println("Where "+m_sbWhere.toString());
			
			//order by
			
			m_sbOrderBy = new StringBuffer();
			m_sbOrderBy.append(" order by c.code ");
			System.out.println(m_sbOrderBy);
			
			
		
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println(e.toString());
		}
		
		
	}
	//通过客户id查询财务报表相关信息
	public ArrayList findClientReportByID(long id,long officeid) throws Exception
	{
		ArrayList list = new ArrayList();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;
		ClientUploadReportInfo clientUploadReportInfo = null;
		try
		{
			con = Database.getConnection();
			sbSQL = new StringBuffer();
			sbSQL.append(" SELECT r.id  id,r.reportname reportName,r.reporttype reportType, ");
			sbSQL.append(" r.reportdate reportDate,r.sdocpath sDocPath ");
			sbSQL.append(" FROM Client_ClientInfo c, CLIENT_UPLOADREPORT r ");
			sbSQL.append(" WHERE 1 = 1 ");
			sbSQL.append(" and c.id = r.clientid(+) ");
			sbSQL.append(" and c.clientbasetype = '1' ");
			sbSQL.append(" and r.nstatusid = 1 ");
			sbSQL.append(" and c.statusid = 1 ");
			sbSQL.append(" and c.officeid ="+officeid);
			sbSQL.append(" and c.id ="+id);
			sbSQL.append(" order by id ");
			System.out.println("sql="+sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			rs=ps.executeQuery();
			while(rs.next())
			{
				clientUploadReportInfo = new ClientUploadReportInfo();
				clientUploadReportInfo.setId(rs.getLong("id"));
				clientUploadReportInfo.setReportName(rs.getString("reportName"));
				clientUploadReportInfo.setReportType(rs.getLong("reportType"));
				clientUploadReportInfo.setReportDate(rs.getTimestamp("reportDate"));
				clientUploadReportInfo.setSDocPath(rs.getString("sDocPath"));
				list.add(clientUploadReportInfo);
				
			}
		}
		catch(Exception e)
		{
			log.error(e.toString());
			throw new Exception("查询出现异常");
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
				log.error(e.toString());
				throw new Exception("Gen_E001");
			}
		}
		return list.size()>0?list:null;
		
		
	}
	
	//财务报表新增
	public void addClientReport(ClientUploadReportInfo clientUploadReportInfo)throws Exception
	{
		strTableName = "CLIENT_UPLOADREPORT";
		setUseMaxID();
		add(clientUploadReportInfo);
	}
	
	//根据id查询财务报表相关信息
	public ClientUploadReportInfo findClientReportInformationByID(long id) throws Exception
	{
		ClientUploadReportInfo clientUploadReportInfo = new ClientUploadReportInfo();
		strTableName = "CLIENT_UPLOADREPORT";
		clientUploadReportInfo=(ClientUploadReportInfo)findByID(id,ClientUploadReportInfo.class);
		return clientUploadReportInfo;
	}
	
	//财务报表修改
	public void updateClientReport(ClientUploadReportInfo clientUploadReportInfo)throws Exception
	{
		strTableName = "CLIENT_UPLOADREPORT";
		update(clientUploadReportInfo);
		
	}

	/**
	 * 新增客户时得到客户编码
	 * @param dataEntity
	 * @return
	 * @throws ITreasuryException
	 */
	public String genClientNo(ClientInfo dataEntity) throws ITreasuryException {
		long officeid = dataEntity.getOfficeID();
		String code = "";
		String officeCode = "";
		String clientCode = "";

		try {
			initDAO();
			StringBuffer sb = new StringBuffer();
			sb.append(" select  max(substr(code,length(code)-3)) code from client_clientinfo where isinstitutionalclient is null");

			transPS = transConn.prepareStatement(sb.toString());
			transRS = transPS.executeQuery();
			if (transRS.next()) {
				clientCode = transRS.getString("code");
			}
			transRS.close();
			transRS = null;
			transPS.close();
			transPS = null;
			
			// 客户号最长段的长度
			int accountNumber = Config.getInteger(ConfigConstant.GLOBAL_MAXACCOUNTNO_LENGTH, 4);
			// 客户号的段间符号
			String tag = Config.getProperty(ConfigConstant.GLOBAL_ACCOUNTNO_TAG, "-");
			if (clientCode == null || "".equalsIgnoreCase(clientCode)) {
				// 默认编号从1开始
				clientCode = DataFormat.formatInt(1, accountNumber);
			} else {
				String[] clientCodes = clientCode.split(tag);
				clientCode = DataFormat.formatInt(Integer.parseInt(clientCodes[clientCodes.length - 1]) + 1,accountNumber);
			}
			sb.setLength(0);
			sb.append(" select scode from office where id=? ");
			transPS = transConn.prepareStatement(sb.toString());
			transPS.setLong(1, officeid);
			transRS = transPS.executeQuery();

			if (transRS.next()) {
				officeCode = transRS.getString("scode");
			}
			code = officeCode + tag + clientCode;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ITreasuryException();
		}
		finalizeDAO();

		return code;
	}
	
	/**
	 * 法人客户编号是否重复
	 * 
	 * @return true(重复） or false(不重复）
	 * @throws ITreasuryException
	 */
	public boolean isNameRepeat(ClientInfo clientInfo) throws ITreasuryException {
		
		boolean exists = false;
		try {
			initDAO();
			StringBuffer sb = new StringBuffer();
			sb.append(" select Code");
			sb.append(" from Client_ClientInfo ");
			sb.append(" where ClientBaseType = '"+CMConstant.ClientBaseType.CORPORATION+"' and statusid = "+Constant.RecordStatus.VALID+" and  Name = '"+ clientInfo.getName());
			sb.append("' ");
			sb.append(" and id<> " + clientInfo.getId() + " ");
			transPS = transConn.prepareStatement(sb.toString());
			transRS = transPS.executeQuery();
			if (transRS.next()) {
				exists = true;
			}
		} catch (Exception e) {
			log.error(e.toString());
			throw new ITreasuryException();
		}
		finalizeDAO();

		return exists;
	}
	
	/**
	 * 判断数据库中是否已经存在编号为ClientNo的客户
	 * 
	 * @return true(重复） or false(不重复）
	 * @param clientNo    客户编号
	 * @throws ITreasuryException
	 */
	public boolean isClientNoRepeat(ClientInfo clientInfo) throws ITreasuryException {
		
		boolean exists = false;
		try {
			initDAO();
			StringBuffer sb = new StringBuffer();
			sb.append(" select Code");
			sb.append(" from Client_clientInfo ");
			sb.append(" where ClientBaseType = '"+CMConstant.ClientBaseType.CORPORATION+"' and statusid = "+Constant.RecordStatus.VALID+" and substr(code,4) = '"+ clientInfo.getTmpCode());
			sb.append("' ");
			sb.append(" and id<> " + clientInfo.getId() + " ");
			transPS = transConn.prepareStatement(sb.toString());
			transRS = transPS.executeQuery();
			if (transRS.next()) {
				exists = true;
			}

		} catch (Exception e) {
			log.error(e.toString());
			throw new ITreasuryException();
		}
		finalizeDAO();

		return exists;
	}
	
	/**
	 * 新增客户信息
	 * @param clientInfo
	 * @return
	 * @throws Exception
	 */
	public long addClientInfo(ClientInfo clientInfo) throws Exception {

		long myid = -1;
		try {
			ClientmanageDAO clientmanageDAO = new ClientmanageDAO();
			clientmanageDAO.setUseMaxID();
			myid = clientmanageDAO.add(clientInfo);
		} catch (Exception e) {
			log.error(e.toString());
			throw new ITreasuryException();
		}

		return myid;
	}
	
	/**
	 * 更新客户信息
	 * @param clientInfo
	 * @return
	 * @throws Exception
	 */
	public long updateClientInfo(ClientInfo clientInfo) throws Exception {
		
		long myid = -1;
		try {
			ClientmanageDAO clientmanageDAO = new ClientmanageDAO();
			clientmanageDAO.update(clientInfo);
			myid = 1;
		} catch (Exception e) {
			log.error(e.toString());
			throw new ITreasuryException();
		}
		
		return myid;
	}
	
	/**
	 * 新增客户的详细信息
	 * @param corporationInfo
	 * @return
	 * @throws Exception
	 */
	public long addCorporation(CorporationInfo corporationInfo) throws Exception {

		long myid = -1;
		try {
			ClientmanageDAO clientmanageDAO = new ClientmanageDAO();
			clientmanageDAO.setUseMaxID();
			clientmanageDAO.strTableName = "client_corporationinfo";
			myid = clientmanageDAO.add(corporationInfo);
		} catch (Exception e) {
			log.error(e.toString());
			throw new ITreasuryException();
		}

		return myid;
	}
	
	/**
	 * 更新客户的详细信息
	 * @param corporationInfo
	 * @return
	 * @throws Exception
	 */
	public long updateCorporation(CorporationInfo corporationInfo) throws Exception {
		
		long myid = -1;
		try {
			ClientmanageDAO clientmanageDAO = new ClientmanageDAO();
			clientmanageDAO.strTableName = "client_corporationinfo";
			clientmanageDAO.update(corporationInfo);
			myid = 1;
		} catch (Exception e) {
			log.error(e.toString());
			throw new ITreasuryException();
		}
		
		return myid;
	}
	
	/**
	 * 查找客户的详细信息
	 * @param corporationInfo
	 * @return
	 * @throws ITreasuryDAOException
	 */
	public Collection findCorporationInfoByID(CorporationInfo corporationInfo) throws ITreasuryDAOException{
		
		Collection coll = null;
		ClientmanageDAO clientmanageDAO = new ClientmanageDAO();
		clientmanageDAO.strTableName = "client_corporationinfo";
		coll = clientmanageDAO.findByCondition(corporationInfo);
		return coll;
	}

	/**
	 * 客户明细信息查询--分页
	 * @param clientInfo
	 * @return
	 */
	public PageLoader queryClientDetailInfo(QueryCorporationInfo clientInfo) {
		
		queryClientDetailSQL(clientInfo);
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		pageLoader.initPageLoader(
			new AppContext(),
			m_sbFrom.toString(),
			m_sbSelect.toString(),
			m_sbWhere.toString(),
			(int) Constant.PageControl.CODE_PAGELINECOUNT,
			"com.iss.itreasury.clientmanage.client.dataentity.ClientBaseInfo",
			null);
		pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
		return pageLoader;
	}
	
	/**
	 * 客户明细信息查询--分页SQL
	 * @param clientInfo
	 * @return
	 */
	public void queryClientDetailSQL(QueryCorporationInfo clientInfo){
		
		m_sbSelect = new StringBuffer();
		m_sbSelect.append("c.id,code,name,legalperson,legalpersoncodecert");
		m_sbFrom = new StringBuffer();
		m_sbFrom.append(" client_clientinfo c,client_corporationinfo o");
		m_sbWhere= new StringBuffer();
		m_sbWhere.append(" c.id = o.clientid(+)");
		m_sbWhere.append(" and c.statusid ="+Constant.RecordStatus.VALID);
		m_sbWhere.append(" and c.clientbasetype = '"+CMConstant.ClientBaseType.CORPORATION+"'");
		m_sbWhere.append(" and c.officeid = "+clientInfo.getOfficeID());
		if(clientInfo.getLClientNOStart()!=null && !clientInfo.getLClientNOStart().equals("")){
			m_sbWhere.append(" and c.code >= '"+clientInfo.getLClientNOStart()+"'");
		}
		if(clientInfo.getLClientNOEnd()!=null && !clientInfo.getLClientNOEnd().equals("")){
			m_sbWhere.append(" and c.code <= '"+clientInfo.getLClientNOEnd()+"'");
		}
		m_sbOrderBy = new StringBuffer();
		m_sbOrderBy.append("order by code asc");
		
	}
	
	/**
	 * 客户信息汇总查询
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public Vector getQueryClientInfo(QueryClientInfo info) throws Exception
	{
		Vector v = new Vector();
		String strSQL = "";
		strSQL = " select a.*,b.* "
		    + " from  client_clientinfo a, client_corporationinfo b "
			+ " where 1 = 1 "
			+ " and a.id = b.clientid(+) "
			+ " and a.officeid = "
			+ info.getOfficeID()
			+ " and a.StatusID = "
			+ Constant.RecordStatus.VALID
			+ " and a.clientbasetype = " + CMConstant.ClientBaseType.CORPORATION;
		
		if(info.getId()>0)
		{
			if(info.getIsInclude().equals("1"))
			{
				strSQL+=" and a.levelcode like '"+info.getLevelcode()+"%'";
			}else
			{
				strSQL+=" and a.id = "+info.getId();
			}
		}
		
		if(!info.getLoanCardNo().equals(""))
		{
			strSQL+=" and b.loancardno = '"+info.getLoanCardNo()+"'";
		}
		if(!info.getLegalpersoncodecert().equals(""))
		{
			strSQL+=" and b.Legalpersoncodecert = '"+info.getLegalpersoncodecert()+"'";
		}
		if(!info.getLicencecode().equals(""))
		{
			strSQL+=" and b.Licencecode = '"+info.getLicencecode()+"'";
		}
		if(info.getClienttypeid1().equals("-1"))
		{
			strSQL+=" and (b.clienttypeid1 = -1 or b.clienttypeid1 is null) ";
		}
		else if(info.getClienttypeid1().equals("0")||info.getClienttypeid1().equals(""))
		{
			
		}
		else 
		{
			strSQL+=" and b.clienttypeid1 = "+info.getClienttypeid1();
		}
		if(info.getClienttypeid2().equals("-1"))
		{
			strSQL+=" and (b.clienttypeid2 = -1 or b.clienttypeid2 is null) ";
		}
		else if(info.getClienttypeid2().equals("0")||info.getClienttypeid2().equals(""))
		{
			
		}
		else 
		{
			strSQL+=" and b.clienttypeid2 = "+info.getClienttypeid2();
		}	
		if(info.getClienttypeid3().equals("-1"))
		{
			strSQL+=" and (b.clienttypeid3 = -1 or b.clienttypeid3 is null) ";
		}
		else if(info.getClienttypeid3().equals("0")||info.getClienttypeid3().equals(""))
		{
			
		}
		else 
		{
			strSQL+=" and b.clienttypeid3 = "+info.getClienttypeid3();
		}
		if(info.getClienttypeid4().equals("-1"))
		{
			strSQL+=" and (b.clienttypeid4 = -1 or b.clienttypeid4 is null) ";
		}
		else if(info.getClienttypeid4().equals("0")||info.getClienttypeid4().equals(""))
		{
			
		}
		else 
		{
			strSQL+=" and b.clienttypeid4 = "+info.getClienttypeid4();
		}
		if(info.getClienttypeid5().equals("-1"))
		{
			strSQL+=" and (b.clienttypeid5 = -1 or b.clienttypeid5 is null) ";
		}
		else if(info.getClienttypeid5().equals("0")||info.getClienttypeid5().equals(""))
		{
			
		}
		else 
		{
			strSQL+=" and b.clienttypeid5 = "+info.getClienttypeid5();
		}
		if(info.getClienttypeid6().equals("-1"))
		{
			strSQL+=" and (b.clienttypeid6 = -1 or b.clienttypeid6 is null) ";
		}
		else if(info.getClienttypeid6().equals("0")||info.getClienttypeid6().equals(""))
		{
			
		}
		else 
		{
			strSQL+=" and b.clienttypeid6 = "+info.getClienttypeid6();
		}
		if(info.getExtendattribute1().equals("-1"))
		{
			strSQL+=" and (b.Extendattribute1 is null or b.Extendattribute1 = -1) ";
		}
		else if(info.getExtendattribute1().equals("0")||info.getExtendattribute1().equals(""))
		{
			
		}
		else 
		{
			strSQL+=" and b.Extendattribute1 = "+info.getExtendattribute1();
		}
		if(info.getExtendattribute2().equals("-1"))
		{
			strSQL+=" and (b.Extendattribute2 is null or b.Extendattribute2 = -1) ";
		}
		else if(info.getExtendattribute2().equals("0")||info.getExtendattribute2().equals(""))
		{
			
		}
		else 
		{
			strSQL+=" and b.Extendattribute2 = "+info.getExtendattribute2();
		}
		if(info.getExtendattribute3().equals("-1"))
		{
			strSQL+=" and (b.Extendattribute3 is null or b.Extendattribute3 = -1) ";
		}
		else if(info.getExtendattribute3().equals("0")||info.getExtendattribute3().equals(""))
		{
			
		}
		else 
		{
			strSQL+=" and b.Extendattribute3 = "+info.getExtendattribute3();
		}
		if(info.getExtendattribute4().equals("-1"))
		{
			strSQL+=" and (b.Extendattribute4 is null or b.Extendattribute4 = -1) ";
		}
		else if(info.getExtendattribute4().equals("0")||info.getExtendattribute4().equals(""))
		{
			
		}
		else 
		{
			strSQL+=" and b.Extendattribute4 = "+info.getExtendattribute4();
		}
		if(info.getExtendattribute5().equals("-1"))
		{
			strSQL+=" and (b.Extendattribute5 is null or b.Extendattribute5 = -1) ";
		}
		else if(info.getExtendattribute5().equals("0")||info.getExtendattribute5().equals(""))
		{
			
		}
		else 
		{
			strSQL+=" and b.Extendattribute5 = "+info.getExtendattribute5();
		}
		if(info.getExtendattribute6().equals("-1"))
		{
			strSQL+=" and (b.Extendattribute6 is null or b.Extendattribute6 = -1) ";
		}
		else if(info.getExtendattribute6().equals("0")||info.getExtendattribute6().equals(""))
		{
			
		}
		else 
		{
			strSQL+=" and b.Extendattribute6 = "+info.getExtendattribute6();
		}
		if(info.getExtendattribute7().equals("-1"))
		{
			strSQL+=" and (b.Extendattribute7 is null or b.Extendattribute7 = -1) ";
		}
		else if(info.getExtendattribute7().equals("0")||info.getExtendattribute7().equals(""))
		{
			
		}
		else 
		{
			strSQL+=" and b.Extendattribute7 = "+info.getExtendattribute7();
		}
		if(info.getExtendattribute8().equals("-1"))
		{
			strSQL+=" and (b.Extendattribute8 is null or b.Extendattribute8 = -1) ";
		}
		else if(info.getExtendattribute8().equals("0")||info.getExtendattribute8().equals(""))
		{
			
		}
		else 
		{
			strSQL+=" and b.Extendattribute8 = "+info.getExtendattribute8();
		}
		
		strSQL += " order by a.levelcode";
		
		try {
			initDAO();
			System.out.println("strSQL----->"+strSQL);
			transPS = transConn.prepareStatement(strSQL);
			transRS = transPS.executeQuery();
			
			while (transRS.next()) {
				QueryClientInfo qinfo = new QueryClientInfo();
				qinfo.setId(transRS.getLong("id"));
				qinfo.setCode(transRS.getString("code"));
				qinfo.setName(transRS.getString("name"));
				qinfo.setLevelcode(transRS.getString("levelcode"));
				qinfo.setLevelID(transRS.getLong("levelid"));
				v.add(qinfo);
			}
			finalizeDAO();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
		finally{
			finalizeDAO();
		}
		
		return v;
	}
	
	/**
	 * 法人客户是否开立账户
	 * 
	 * @return true(重复） or false(不重复）
	 * @throws ITreasuryException
	 */
	public boolean isCreateAccount(ClientInfo clientInfo) throws ITreasuryException {
		
		boolean exists = false;
		try {
			initDAO();
			StringBuffer sb = new StringBuffer();
			sb.append(" select a.nclientid");
			sb.append(" from sett_account a ");
			sb.append(" where a.nstatusid = "+Constant.RecordStatus.VALID );
			sb.append(" and a.nclientid = " + clientInfo.getId() +" group by a.nclientid ");
			transPS = transConn.prepareStatement(sb.toString());
			transRS = transPS.executeQuery();
			if (transRS.next()) {
				exists = true;
			}
		} catch (Exception e) {
			log.error(e.toString());
			throw new ITreasuryException();
		}
		finalizeDAO();

		return exists;
	}
	
	/**
	 * 根据客户ID获得客户levelcode
	 * @param clientInfo
	 * @return
	 */
	public String findClientLevelCodeByID(ClientInfo clientInfo) throws Exception {
		
		String strLevelCode = "";
		try {
			initDAO();
			StringBuffer sb = new StringBuffer();
			sb.append(" select levelcode from client_clientinfo a");
			sb.append(" where a.statusid = "+Constant.RecordStatus.VALID );
			sb.append(" and a.id = " + clientInfo.getId());
			transPS = transConn.prepareStatement(sb.toString());
			transRS = transPS.executeQuery();
			if (transRS.next()) {
				strLevelCode = transRS.getString("levelcode");
			}
		} catch (Exception e) {
			log.error(e.toString());
			throw new ITreasuryException();
		}
		finalizeDAO();

		return strLevelCode;
	}
	

	/**
	 * 上级客户修改时更新下级客户的级别编码
	 * @param clientInfo
	 * @return
	 * @throws ITreasuryDAOException 
	 * @throws ITreasuryDAOException
	 */
	public long updateClientLevelCode(String str1, String str2) throws ITreasuryDAOException {
		
		long lReturn = -1;
		long strLen = str1.length();
		long _strLen = str1.length()+1;
		try {
			initDAO();
			StringBuffer buffer = new StringBuffer();
			buffer.append("UPDATE client_clientinfo");
			buffer.append(" SET levelcode = REPLACE(substr(levelcode,0,"+strLen+"), '"+str1+"', '"+str2+"')||substr(levelcode,"+_strLen+",length(levelcode))");
			buffer.append(" WHERE levelcode like '"+str1+"%'");
			String strSQL = buffer.toString();
			log.debug(strSQL);
			prepareStatement(strSQL);
			executeQuery();
			lReturn = 1;
			if(lReturn == 1){
				buffer = new StringBuffer();
				buffer.append("UPDATE client_clientinfo");
				buffer.append(" SET levelid = length(levelcode)/5");
				buffer.append(" WHERE levelcode like '"+str2+"%'");
				strSQL = buffer.toString();
				log.debug(strSQL);
				prepareStatement(strSQL);
				executeQuery();
				lReturn = 1;
			}
		} catch (ITreasuryDAOException e) {
			throw new ITreasuryDAOException("更新下级客户的级别编码异常",e);
		}
		finally
		{
		    finalizeDAO();
		}
		return lReturn;
	}
	
	/**
	 * 法人客户是否有下级客户
	 * 
	 * @return true(重复） or false(不重复）
	 * @throws ITreasuryException 
	 * @throws ITreasuryException
	 */
	public boolean isExistLower(ClientInfo clientInfo) throws ITreasuryException {

		boolean exists = false;
		try {
			initDAO();
			StringBuffer sb = new StringBuffer();
			sb.append(" select id ");
			sb.append(" from client_clientinfo ");
			sb.append(" where StatusID = " + Constant.RecordStatus.VALID );
			sb.append(" and levelcode like '" + clientInfo.getLevelCode() +"%'");
			sb.append(" and id <> " + clientInfo.getId() );
			transPS = transConn.prepareStatement(sb.toString());
			transRS = transPS.executeQuery();
			if (transRS.next()) {
				exists = true;
			}
		} catch (Exception e) {
			log.error(e.toString());
			throw new ITreasuryException();
		}
		finalizeDAO();
		return exists;
	}
	
	 /**
     * 企业大事件信息分页查询
     * @param clientInfo
     * @return
     */
    public PageLoader queryEnterpriseMemoInfo(QueryCorporationInfo clientInfo) {
		
    	
   	 	queryEnterpriseMemoInfoSQL(clientInfo);
   	 	PageLoader pageLoader = new PageLoaderCombine();
		pageLoader.initPageLoaderCombine(
			new AppContext(),
			m_sbFrom.toString(),
			m_sbSelect.toString(),
			m_sbWhere.toString(),
			(int) Constant.PageControl.CODE_PAGELINECOUNT,
			"com.iss.itreasury.clientmanage.enterprisememo.dataentity.EnterpriseMemoInfo",
			null,
			m_sbMainCondition.toString(),
			m_sbMainTable.toString(),
			m_sbMainOrderBy.toString());
		pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
		return pageLoader;
		
	}
    
    /**
     * 企业大事件信息分页查询SQL
     * @param clientInfo
     * @return
     */
	public void queryEnterpriseMemoInfoSQL(QueryCorporationInfo clientInfo){
		
		m_sbMainTable = new StringBuffer();
		m_sbMainTable.append(" client_clientinfo ");
		
		m_sbMainCondition = new StringBuffer();
		m_sbMainCondition.append(" where statusid = 1 and clientbasetype = '1' and officeid ="+clientInfo.getOfficeID());
		if(clientInfo.getLClientNOStart()!=null && !clientInfo.getLClientNOStart().equals("")){
			m_sbMainCondition.append(" and code >= '"+clientInfo.getLClientNOStart()+"'");
		}
		if(clientInfo.getLClientNOEnd()!=null && !clientInfo.getLClientNOEnd().equals("")){
			m_sbMainCondition.append(" and code <= '"+clientInfo.getLClientNOEnd()+"'");
		}
		
		m_sbMainOrderBy = new StringBuffer();
		m_sbMainOrderBy.append(" order by code ");
		
		m_sbSelect = new StringBuffer();
		m_sbSelect.append(" a.eventrecordno,a.eventdate,a.memodescribe,a.abstract,b.code,b.name,a.clientID,b.id client_info_id \n"); 
		m_sbFrom = new StringBuffer();
		m_sbFrom.append(" b , ");
		m_sbFrom.append("( select * from Client_EnterpriseMemo where Client_EnterpriseMemo.statusid = 1 ) a ");
		m_sbWhere= new StringBuffer();
		m_sbWhere.append(" a.clientid(+)=b.id ");
		
	
		m_sbOrderBy = new StringBuffer();
		m_sbOrderBy.append("order by b.code asc");
	}
	
	/**
	 * 是否设置过财务公司
	 * @return
	 * @throws ITreasuryException 
	 */
	public boolean isExistPartner(long id,long office) throws ITreasuryException {
		
		boolean exists = false;
		try {
			initDAO();
			StringBuffer sb = new StringBuffer();
			sb.append(" select a.id ");
			sb.append(" from client_clientinfo a, client_corporationinfo b ");
			sb.append(" where a.StatusID = " + Constant.RecordStatus.VALID );
			sb.append(" and a.id = b.clientid(+) ");
			sb.append(" and a.officeid = "+ office);
			sb.append(" and b.ispartner = 1 ");
			if(id!= -1){
				sb.append(" and a.id <> " + id );
			}
			transPS = transConn.prepareStatement(sb.toString());
			transRS = transPS.executeQuery();
			if (transRS.next()) {
				exists = true;
			}
		} catch (Exception e) {
			log.error(e.toString());
			throw new ITreasuryException();
		}
		finalizeDAO();
		return exists;
	}	
	
	/**
	 *  查找外部单位信息
	 * Create Date: 2011-05-17
	 * @return Collection
	 * @exception Exception
	 */
	public Collection queryExtClient(ExtClientInfo info) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Vector vReturn = new Vector();
		
		try
		{
			con = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" SELECT * FROM client_extclientinfo");
			sbSQL.append(" WHERE  1=1");
			if(info.getStatusid()!=-1)
			{
				sbSQL.append(" and statusid =" + info.getStatusid());
			}
			if(info.getOfficeid()!=-1)
			{
				sbSQL.append(" and officeid =" + info.getOfficeid());
			}	
			if(info.getCurrencyid()!=-1)
			{
				sbSQL.append(" and currencyid =" + info.getCurrencyid());
			}	
			sbSQL.append(" order by id");
			Log.print(sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();
			
			while (rs.next())
			{
				ExtClientInfo resultInfo = new ExtClientInfo();
				resultInfo.setId(rs.getLong("ID"));
				resultInfo.setCode(rs.getString("CODE"));
				resultInfo.setName(rs.getString("NAME"));
				resultInfo.setEngName(rs.getString("ENGNAME"));
				resultInfo.setLinkman(rs.getString("LINKMAN"));
				resultInfo.setTel(rs.getString("TEL"));
				resultInfo.setAddress(rs.getString("ADDRESS"));
				resultInfo.setStatusid(rs.getLong("STATUSID"));
				resultInfo.setRemarks(rs.getString("REMARKS"));
				
				vReturn.add(resultInfo);
				resultInfo = null;
			}

			if (rs != null)
			{
				rs.close();
				rs = null;
			}
			if (ps != null)
			{
				ps.close();
				ps = null;
			}
			if (con != null)
			{
				con.close();
				con = null;
			}
		}
		catch (Exception e)
		{
			throw new IException("Gen_E001");
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
				throw new IException("Gen_E001");
			}
		}
		return vReturn.size() > 0 ? vReturn : null;
	}
	
	/**
	 * 删除一条外部单位信息
	 * Create Date: 2011-05-17
	 * @param lId 外部单位ID
	 * @return long 大于0表示成功，小于,等于0表示失败
	 * @exception Exception
	 */
	public long deleteExtClient(long lId) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		int nIndex = 1;
		long lReturn = -1;
		try
		{
			con = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append("update client_extclientinfo set statusid=0 where ID = ?");
			Log.print(sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			nIndex = 1;
			ps.setLong(nIndex++, lId);
			lReturn = ps.executeUpdate();

			if (ps != null)
			{
				ps.close();
				ps = null;
			}
			if (con != null)
			{
				con.close();
				con = null;
			}
		}
		catch (Exception e)
		{
			throw new IException("Gen_E001");
		}
		finally
		{
			try
			{
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
				throw new IException("Gen_E001");
			}
		}
		return lReturn;
	}
	
	/**
	 * 新增外部单位时得到单位编码
	 * @param dataEntity
	 * @return
	 * @throws ITreasuryException
	 */
	public String genClientNo(ExtClientInfo dataEntity) throws ITreasuryException {
		long officeid = dataEntity.getOfficeid();
		System.out.print(officeid);
		String code = "";
		String clientCode = "";
		try {
			initDAO();
			StringBuffer sb = new StringBuffer();
			sb.append(" select max(code) code from client_extclientinfo where officeid=?");

			transPS = transConn.prepareStatement(sb.toString());
			System.out.println(sb.toString());
			transPS.setLong(1, officeid);
			transRS = transPS.executeQuery();

			if (transRS.next()) {
				clientCode = transRS.getString("code");
			}
			transRS.close();
			transRS = null;
			transPS.close();
			transPS = null;
			System.out.print(clientCode);
			if (clientCode == null || "".equalsIgnoreCase(clientCode)) {
				// 默认编号从1开始
				clientCode = DataFormat.formatInt(1, 4);
				System.out.print(clientCode);
			} else {
				String[] clientCodes = clientCode.split("-");
				System.out.print(clientCode);
				clientCode = DataFormat.formatInt(Integer.parseInt(clientCodes[clientCodes.length - 1]) + 1,4);
				System.out.print(clientCode);	
			}
			code = clientCode;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ITreasuryException();
		}
		finalizeDAO();

		return code;
	}
	
	/**
	 * 外部单位名称是否重复
	 * 
	 * @return true(重复） or false(不重复）
	 * @throws ITreasuryException
	 */
	public boolean isExtClientNameRepeat(ExtClientInfo clientInfo) throws ITreasuryException {
		
		boolean exists = false;
		try {
			initDAO();
			StringBuffer sb = new StringBuffer();
			sb.append(" select Code");
			sb.append(" from client_extclientinfo ");
			sb.append(" where statusid = "+Constant.RecordStatus.VALID+" and  Name = '"+ clientInfo.getName());
			sb.append("' ");
			sb.append(" and id<> " + clientInfo.getId() + " ");
			transPS = transConn.prepareStatement(sb.toString());
			transRS = transPS.executeQuery();
			if (transRS.next()) {
				exists = true;
			}
		} catch (Exception e) {
			log.error(e.toString());
			throw new ITreasuryException();
		}
		finalizeDAO();

		return exists;
	}
	
	/**
	 * 新增外部单位信息
	 * @param clientInfo
	 * @return
	 * @throws Exception
	 */
	public long addExtClientInfo(ExtClientInfo clientInfo) throws Exception {
		long myid = -1;
		try {
			ClientmanageDAO clientmanageDAO = new ClientmanageDAO();
			clientmanageDAO.setUseMaxID();
			clientmanageDAO.strTableName = "client_extclientinfo";
			myid = clientmanageDAO.add(clientInfo);
		} catch (Exception e) {
			log.error(e.toString());
			throw new ITreasuryException();
		}

		return myid;
	}
	
	/**
	 * 根据ID获得外部单位信息
	 * @param clientInfo
	 * @return
	 * @throws ITreasuryDAOException
	 */
	public Collection findExtClientByCondition(ExtClientInfo clientInfo) throws ITreasuryDAOException {
       
		this.strTableName="client_extclientinfo";
        
		return findByCondition(clientInfo, null);
    }
	
	/**
	 * 更新外部单位信息
	 * @param clientInfo
	 * @return
	 * @throws Exception
	 */
	public long updateExtClientInfo(ExtClientInfo clientInfo) throws Exception {
		
		long myid = -1;
		try {
			ClientmanageDAO clientmanageDAO = new ClientmanageDAO();
			clientmanageDAO.strTableName ="client_extclientinfo";
			clientmanageDAO.update(clientInfo);
			myid = 1;
		} catch (Exception e) {
			log.error(e.toString());
			throw new ITreasuryException();
		}
		
		return myid;
	}
	/**
	 * 根据ID获得外部单位信息
	 * @param clientInfo
	 * @return
	 * @throws ITreasuryDAOException
	 */
	public ITreasuryBaseDataEntity findExtClientInfoByID(long id) throws ITreasuryDAOException {
       
		this.strTableName="client_extclientinfo";
        
		return this.findByID(id,ExtClientInfo.class);
    }
	
	/**
	 * 判断对外投资客户是否重复
	 * @param clientInfo
	 * @return
	 * @throws ITreasuryDAOException
	 */
	public boolean isClientInvestNoRepeat(ClientInvestInfo dataEntity) throws Exception
	{
		boolean exists = false;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;
		try
		{
			con = Database.getConnection();
			sbSQL = new StringBuffer();
			sbSQL.append(" select c.partnerid  ");
			sbSQL.append(" from client_investofsubsidiary c ");
			sbSQL.append(" where c.statusid=1 ");
			sbSQL.append(" and c.clientid="+dataEntity.getClientid());
			sbSQL.append(" and c.partnerid="+dataEntity.getPartnerid());
			sbSQL.append(" and c.partnertype="+dataEntity.getPartnertype());
			
			sbSQL.append(" and id<>"+dataEntity.getId());
			System.out.println("sql="+sbSQL.toString());
			ps=con.prepareStatement(sbSQL.toString());
			rs=ps.executeQuery();
			if(rs.next())
			{
				exists = true;
			}
		}
		catch(Exception e)
		{
			log.error(e.toString());
			throw new Exception("查询出现异常");
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
				log.error(e.toString());
				throw new Exception("Gen_E001");
			}
		}
		
		return exists;
	}
	
	/**
	 * 判断外部单位信息是否正被使用
	 * @param clientInfo
	 * @return
	 * @throws ITreasuryDAOException
	 */
	public long isExsitForExtClient(long lID) throws Exception
	{
		long exists = -1;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;
		try
		{
			con = Database.getConnection();
			sbSQL = new StringBuffer();
			sbSQL.append(" select c.partnerid from CLIENT_PARTNEROFCLIENT c where c.statusid=1 and c.partnerid="+lID+" and c.stockholdertype=2  ");
			sbSQL.append(" union all ");
			sbSQL.append(" select b.partnerid from CLIENT_INVESTOFSUBSIDIARY b where b.statusid=1 and b.partnerid="+lID+" and b.partnertype=2 ");
			sbSQL.append(" union all ");
			sbSQL.append(" select l.nclientid from loan_loanformassure l where l.nstatusid=1  and l.nclientid="+lID+" and l.clienttype=2 ");
			ps=con.prepareStatement(sbSQL.toString());
			rs=ps.executeQuery();
			if(rs.next())
			{
				exists = 1;
			}
		}
		catch(Exception e)
		{
			log.error(e.toString());
			throw new Exception("查询出现异常");
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
				log.error(e.toString());
				throw new Exception("Gen_E001");
			}
		}
		return exists;
	}
	/**
	 * 获取国民经济部门信息
	 * @return
	 * @throws Exception
	 */
	public ArrayList getEconomicDepartmentList() throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;
		ArrayList list = new ArrayList();
		EconomicDepartmentInfo info = null;
		try
		{
			con = Database.getConnection();
			sbSQL = new StringBuffer();
			sbSQL.append(" select code,name from client_economicdepartment ");
			ps=con.prepareStatement(sbSQL.toString());
			rs=ps.executeQuery();		
			while(rs.next())
			{
				info = new EconomicDepartmentInfo();
				info.setDepartmentName(rs.getString("name"));
				info.setDepartmentCode(rs.getString("code"));
				list.add(info);
				
			}
		}catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("获取国民经济部门信息出错!",e);
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
				log.error(e.toString());
				throw new Exception("Gen_E001");
			}			
		}
		return list.size()>0?list:null;
	}
	
	/**
	 * 获取经济成分信息
	 * @return
	 * @throws Exception
	 */
	public ArrayList getEconomicComponentList() throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;
		ArrayList list = new ArrayList();
		EconomicComponentInfo info = null;
		try
		{
			con = Database.getConnection();
			sbSQL = new StringBuffer();
			sbSQL.append(" select code,typename from client_economiccomponent ");
			ps=con.prepareStatement(sbSQL.toString());
			rs=ps.executeQuery();		
			while(rs.next())
			{
				info = new EconomicComponentInfo();
				info.setComponentCode(rs.getString("code"));
				info.setComponentTypeName(rs.getString("typename"));
				list.add(info);
				
			}
		}catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("获取经济成分信息出错!",e);
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
				log.error(e.toString());
				throw new Exception("Gen_E001");
			}			
		}
		return list.size()>0?list:null;
	}	
	
	/**
	 * 获取企业规模信息
	 * @return
	 * @throws Exception
	 */
	public ArrayList getEnterpriseSizeList() throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;
		ArrayList list = new ArrayList();
		EnterpriseSizeInfo info = null;
		try
		{
			con = Database.getConnection();
			sbSQL = new StringBuffer();
			sbSQL.append(" select code,sizename from client_enterpriseSize ");
			ps=con.prepareStatement(sbSQL.toString());
			rs=ps.executeQuery();		
			while(rs.next())
			{
				info = new EnterpriseSizeInfo();
				info.setCode(rs.getString("code"));
				info.setSizeName(rs.getString("sizename"));
				list.add(info);
				
			}
		}catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("获取企业规模信息出错!",e);
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
				log.error(e.toString());
				throw new Exception("Gen_E001");
			}			
		}
		return list.size()>0?list:null;
	}		
/**
 * 获取地区信息
 * @return
 * @throws Exception
 */	
	public ArrayList getAreaList(long Type,String code) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;
		ArrayList list = new ArrayList();		
		ClientAreaInfo info = null;
		try
		{
		
			con = Database.getConnection();
			sbSQL = new StringBuffer();	
			sbSQL.append(" select c.code,c.name from client_areaInformation c ");
			sbSQL.append(" where c.areatype="+Type);
			if(code!=null&&!code.equals(""))
			{
				sbSQL.append(" and c.code like '"+code+"%'");
			}
			System.out.println("获取地区信息sql="+sbSQL.toString());
			ps=con.prepareStatement(sbSQL.toString());
			rs=ps.executeQuery();
			while(rs.next())
			{
				info = new ClientAreaInfo();
				info.setAreaName(rs.getString("name"));
				info.setAreaCode(rs.getString("code"));
				list.add(info);
			}
			
		}catch (Exception e)
		{
			e.printStackTrace();
			throw new Exception("获取地区信息出错!",e);
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
				log.error(e.toString());
				throw new Exception("Gen_E001");
			}			
		}
		return list.size()>0?list:null;
	}
	
	public ArrayList getIndustryList(long sort,String code) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;
		ArrayList list = new ArrayList();	
		EconomicIndustryInfo info = null;
		try
		{
			con = Database.getConnection();
			sbSQL = new StringBuffer();			
			sbSQL.append(" select c.code,c.type,c.sort from client_economicIndustry c ");
			sbSQL.append(" where c.sort ="+sort);
			if(code!=null&&!code.equals(""))
			{
				sbSQL.append(" and c.code like '"+code+"%'");
			}
			System.out.println("获取行业信息sql="+sbSQL.toString());
			ps=con.prepareStatement(sbSQL.toString());
			rs=ps.executeQuery();
			while(rs.next())
			{
				info = new EconomicIndustryInfo();
				info.setIndustryCode(rs.getString("code"));
				info.setIndustryType(rs.getString("type"));
				list.add(info);
			}
		}catch (Exception e)
		{
			e.printStackTrace();
			throw new Exception("获取行业信息出错!",e);
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
				log.error(e.toString());
				throw new Exception("Gen_E001");
			}			
		}
		return list.size()>0?list:null;
	}
	
	public DepositAndLoanMessageDetailInfo findDepositAndLoanMessageDetailById(long id,long clientId) throws Exception
	{
		DepositAndLoanMessageDetailInfo info = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;
		try
		{
			con = Database.getConnection();
			sbSQL = new StringBuffer();		
			sbSQL.append(" select m.id,m.clientid,m.departmentcode,m.economiccomponentcode,m.sizecode,");
			sbSQL.append(" getArea(substr(m.areacode, 0, 2),"+CMConstant.DepositAndLoanMessage.Area.PROVINCE+") province, ");
			sbSQL.append(" getArea(substr(m.areacode, 0, 4),"+CMConstant.DepositAndLoanMessage.Area.CITY+") city, ");
			sbSQL.append(" getArea(substr(m.areacode, 0, 6),"+CMConstant.DepositAndLoanMessage.Area.COUNTY+") county, ");
			sbSQL.append(" getIndustry(substr(m.industrycode,0,1),"+CMConstant.DepositAndLoanMessage.Industry.CATEGORY+") category, ");
			sbSQL.append(" getIndustry(substr(m.industrycode,0,3),"+CMConstant.DepositAndLoanMessage.Industry.BIGCATEGORY+") bigcategory ");
			sbSQL.append(" from client_depositAndLoanMessage m ");
			sbSQL.append(" where m.nstatusid = 1 ");
			if(id>0)
			{
				sbSQL.append(" and m.id ="+id);
			}
			if(clientId>0)
			{
				sbSQL.append(" and m.clientid ="+clientId);
			}
			System.out.println("明细信息sql="+sbSQL.toString());
			ps=con.prepareStatement(sbSQL.toString());
			rs=ps.executeQuery();
			while(rs.next())
			{
				info = new DepositAndLoanMessageDetailInfo();
				info.setId(rs.getLong("id"));
				info.setClientId(rs.getLong("clientid"));
				info.setDepartmentCode(rs.getString("departmentcode"));
				info.setEconomicComponentCode(rs.getString("economiccomponentcode"));
				info.setEnterpriseSizeCode(rs.getString("sizecode"));
				info.setProvinceCode(rs.getString("province"));
				info.setCityCode(rs.getString("city"));
				info.setCountyCode(rs.getString("county"));
				info.setCategoryCode(rs.getString("category"));
				info.setBigCategoryCode(rs.getString("bigcategory"));
			}
		}catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("获取明细信息出错!",e);			
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
				log.error(e.toString());
				throw new Exception("Gen_E001");
			}			
		}		
		return info;
	}
}

