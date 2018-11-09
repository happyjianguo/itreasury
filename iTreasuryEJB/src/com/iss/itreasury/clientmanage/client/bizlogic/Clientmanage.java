package com.iss.itreasury.clientmanage.client.bizlogic;


import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;

import com.iss.itreasury.clientmanage.client.dao.ClientDAO;
import com.iss.itreasury.clientmanage.client.dao.ClientmanageDAO;
import com.iss.itreasury.clientmanage.client.dataentity.ClientAreaInfo;
import com.iss.itreasury.clientmanage.client.dataentity.ClientBusinessInfo;
import com.iss.itreasury.clientmanage.client.dataentity.ClientContactInfo;
import com.iss.itreasury.clientmanage.client.dataentity.ClientDealInfo;
import com.iss.itreasury.clientmanage.client.dataentity.ClientInvestInfo;
import com.iss.itreasury.clientmanage.client.dataentity.DepositAndLoanMessageDetailInfo;
import com.iss.itreasury.clientmanage.client.dataentity.DepositAndLoanMessageInfo;
import com.iss.itreasury.clientmanage.client.dataentity.EconomicIndustryInfo;
import com.iss.itreasury.clientmanage.client.dataentity.ExtClientInfo;
import com.iss.itreasury.clientmanage.client.dataentity.QueryCorporationInfo;
import com.iss.itreasury.clientmanage.dataentity.ClientInfo;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.settlement.account.dao.Sett_ExternalAccountDAO;
import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.ConfigConstant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IRollbackException;
import com.iss.itreasury.util.ITreasuryException;
import com.iss.system.dao.PageLoader;
import java.util.ArrayList;
import com.iss.itreasury.clientmanage.client.dataentity.ClientUploadReportInfo;
import com.iss.itreasury.clientmanage.client.dataentity.CorporationExtendsInfo;
import com.iss.itreasury.clientmanage.partner.dao.PartnerDAO;
import com.iss.itreasury.clientmanage.partner.dataentity.PartnerInfo;
import com.iss.itreasury.clientmanage.util.CMConstant;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.clientmanage.client.dataentity.CorporationInfo;
import com.iss.itreasury.clientmanage.client.dataentity.QueryClientInfo;


public class Clientmanage {
	
	/**
	 * ��Ӫ��Ϣ��ҳ��ѯ
	 * @param clientInfo
	 * @return
	 */
	public PageLoader queryDealInfo(QueryCorporationInfo clientInfo){
		PageLoader pageLoader = null;
		ClientmanageDAO clientmanageDao = new ClientmanageDAO();
		pageLoader = clientmanageDao.queryClientdealInfo(clientInfo);
		return pageLoader;
	}
	
	public PageLoader queryClientDepositAndLoanMessage(QueryCorporationInfo clientInfo)
	{
		PageLoader pageLoader = null;
		ClientmanageDAO clientmanageDao = new ClientmanageDAO();
		pageLoader = clientmanageDao.queryClientDepositAndLoanMessage(clientInfo);
		return pageLoader;
	}
    
	/**
	 * ҵ����Ϣ��ҳ��ѯ
	 * @param clientInfo
	 * @return
	 */
	public PageLoader queryBusinessInfo(QueryCorporationInfo clientInfo){
		PageLoader pageLoader = null;
		ClientmanageDAO clientmanageDao = new ClientmanageDAO();
		pageLoader = clientmanageDao.queryClientbusinessInfo(clientInfo);
		return pageLoader;
	}
	
	/**
	 * ��ϵ��Ϣ��ҳ��ѯ
	 * @param clientInfo
	 * @return
	 */
	public PageLoader queryContactInfo(QueryCorporationInfo clientInfo){
		PageLoader pageLoader = null;
		ClientmanageDAO clientmanageDao = new ClientmanageDAO();
		pageLoader = clientmanageDao.queryClientcontactInfo(clientInfo);
		return pageLoader;
	}
	
	/**
	 * ����Ͷ����Ϣ��ҳ��ѯ
	 * @param clientInfo
	 * @return
	 */
	public PageLoader queryInvestInfo(QueryCorporationInfo clientInfo){
		PageLoader pageLoader = null;
		ClientmanageDAO clientmanageDao = new ClientmanageDAO();
		pageLoader = clientmanageDao.queryClientinvestInfo(clientInfo);
		return pageLoader;
		
	}
	
	/**
	 * ��Ӫ��Ϣ�޸�
	 */
	public long updateClientDealInfo(ClientDealInfo clientDealInfo) throws Exception{
		long id = -1;
		ClientmanageDAO clientmanageDAO = new ClientmanageDAO();
		id =  clientmanageDAO.updateClientDealInfo(clientDealInfo);
		return id;
	}
	
    /**
 	 * ����id���Ҿ�Ӫ��Ϣ
 	 */
	public ClientDealInfo findDealInformationByID(long id,long officeid) throws Exception{
		ClientDealInfo clientDealInfo = new ClientDealInfo();
		ClientmanageDAO clientmanageDAO = new ClientmanageDAO();
		clientDealInfo = clientmanageDAO.findDealInformationByID(id, officeid);
		return clientDealInfo;
	}
	
    /**
 	 * ����id����ҵ����Ϣ
 	 */
	public ClientBusinessInfo findBusinessInformationByID(long id,long officeid) throws Exception{
		ClientBusinessInfo clientBusinessInfo = new ClientBusinessInfo();
		ClientmanageDAO clientmanageDAO = new ClientmanageDAO();
		clientBusinessInfo = clientmanageDAO.findBusinessInformationByID(id, officeid);
		return clientBusinessInfo;
	}
	
    /**
 	 * ����id������ϵ��Ϣ
 	 */
	public ClientContactInfo findContactInformationByID(long id,long officeid) throws Exception{
		ClientContactInfo clientContactInfo = new ClientContactInfo();
		ClientmanageDAO clientmanageDAO = new ClientmanageDAO();
		clientContactInfo = clientmanageDAO.findContactInformationByID(id, officeid);
		return clientContactInfo;
	}
	
	/**
	 * ҵ����Ϣ�޸�
	 * @param clientBusinessInfo
	 */
	public long updateClientBusinessInfo(ClientBusinessInfo clientBusinessInfo) throws Exception{
		long id = -1;
		Connection con = null;
		con = Database.getConnection();
		ClientmanageDAO clientmanageDAO = new ClientmanageDAO(con);
		id = clientmanageDAO.updateClientBusinessInfo(clientBusinessInfo);
		if (con != null) {
			con.close();
			con = null;
		}
		return id;
	}

	/**
	 * ��ϵ��Ϣ�޸�
	 * @param clientInfo
	 */
	public long updateClientContactInfo(ClientInfo clientInfo) throws Exception{
		long id = -1;
		Connection con = null;
		con = Database.getConnection();
		ClientmanageDAO clientmanageDAO = new ClientmanageDAO(con);
		id = clientmanageDAO.updateClientContactInfo(clientInfo);
		if (con != null) {
			con.close();
			con = null;
		}
		return id;
	}

	/**
	 * ��ϵ��Ϣ�޸�
	 * @param clientContactInfo
	 */
	public long updateClientContactInfo1(ClientContactInfo clientContactInfo) throws Exception{
		long id = -1;
		Connection con = null;
		con = Database.getConnection();
		ClientmanageDAO clientmanageDAO = new ClientmanageDAO(con);
		id = clientmanageDAO.updateClientContactInfo1(clientContactInfo);
		if (con != null) {
			con.close();
			con = null;
		}
		return id;
	}
	
	/**
	 * ����Ͷ����Ϣ�޸�
	 * @param dataEntity
	 */
	public long updateClientInvestInfo(ClientInvestInfo dataEntity) throws Exception{
    	long id=-1;
    	boolean exists = false;
		Connection con = null;
    	con = Database.getConnection();
    	ClientmanageDAO clientmanageDAO = new ClientmanageDAO(con);	
    	exists=clientmanageDAO.isClientInvestNoRepeat(dataEntity);
		if(exists&&dataEntity.getPartnerid()!=-1)
		{
			throw new IException("��Ͷ�ʿͻ�����Ѵ���!!");
		}
		else
		{
			id = clientmanageDAO.updateClientInvestInfo(dataEntity);
		}
    	
    	if(con != null)
    	{
    		con.close();
    		con=null;
    	}
    	return id;
    }
	
	/**
     *ɾ��һ���ܿ�λ�����Ϣ
     * @param id ��ҵ�ܿص�λId
     * @throws ITreasuryDAOException
     * @throws Exception
     * */
    public long deleteClientInvestInfo(long id) throws Exception{
    	long lID = -1;
    	Connection con = null;
    	con = Database.getConnection();
    	ClientmanageDAO clientmanageDAO = new ClientmanageDAO(con);
    	clientmanageDAO.deleteClientInvestInfo(id);
    	lID = 1;
    	if(con != null)
    	{
    		con.close();
    		con=null;
    	}
    	return lID;
    }
	
	 /**
     *��ñ��Ϊid��һ����ҵͶ����Ϣ
     * @throws Exception
     * */
    public ClientInvestInfo findClientInvestInfoById(long id) throws Exception{
    	
    	ClientInvestInfo oneinvestinfo = new ClientInvestInfo();
    	Connection con = null;
    	con = Database.getConnection();
    	ClientmanageDAO clientmanageDAO = new ClientmanageDAO(con);
    	oneinvestinfo =(ClientInvestInfo) clientmanageDAO.findClientInvestInfoById(id);
    	if(con != null)
    	{
    		con.close();
    		con=null;
    	}
    	return oneinvestinfo;
    	
    }
	
	/**
     *��ñ��ΪclientID����ҵ��������ҵͶ����Ϣ
     * @param  clientID���˿ͻ�ID
     * @return  ����IvestDataEntity��Collection
	 * @throws Exception
     * */
    public Collection findByClientID(long clientID) throws Exception{
    	Collection data = null;
    	Connection con = null;
    	con = Database.getConnection();
    	ClientmanageDAO clientmanageDAO = new ClientmanageDAO(con);
    	data=clientmanageDAO.findByClientID(clientID);
    	if(con != null)
    	{
    		con.close();
    		con=null;
    	}
    	return data;
    }
    
    /**
     *����һ����ҵͶ���ܿص�λ��Ϣ
     * @param  �ܿص�λ���dataEntity
     * @return  ������¼ID
	 * @throws ITreasuryDAOException
	 * @throws Exception
     * */
    public long addClientInvestInfo(ClientInvestInfo dataEntity) throws Exception {
    	
    	long myid = -1;
    	boolean exists = false;
    	Connection con = null;
    	con = Database.getConnection();
    	ClientmanageDAO clientmanageDAO = new ClientmanageDAO(con);
    	exists=clientmanageDAO.isClientInvestNoRepeat(dataEntity);
		if(exists&&dataEntity.getPartnerid()!=-1)
		{
			throw new IException("��Ͷ�ʿͻ�����Ѵ���!!");
		}
		else
		{
			myid = clientmanageDAO.addClientInvestInfo(dataEntity);
		}
    	if(con != null)
    	{
    		con.close();
    		con=null;
    	}
    	return myid;
    }
    
    /*
	 * ����id���в�ѯ
	 */

	public ClientInfo findbyid(long id) throws Exception {

		Connection con = null;
		con = Database.getConnection();
		ClientDAO clientdao = new ClientDAO(con);
		ClientInfo clientinfo = new ClientInfo();

		clientinfo = (ClientInfo) clientdao.findByID(id, clientinfo.getClass());
		if (con != null) {
			con.close();
			con = null;
		}

		return clientinfo;
	}
	
	/**
	 * ��ҵ�������Ϣ��ҳ��ѯ
	 * @param 
	 * @return
	 */
	public PageLoader querymanagerInfo(QueryCorporationInfo clientInfo){
		PageLoader pageLoader = null;
		ClientmanageDAO clientmanageDao = new ClientmanageDAO();
		pageLoader = clientmanageDao.querymanagerInfo(clientInfo);
		return pageLoader;
		
	}
	/**
	 * ��ҵ�������Ϣ��ѯ
	 * @param clientinfo
	 * @return
	 * @throws Exception
	 */
	public Collection query_ManagerInfo(ClientInfo clientinfo) throws Exception
	{
		ClientmanageDAO clientmanageDao = new ClientmanageDAO();
		Collection vReturn = new ArrayList();
		vReturn=clientmanageDao.query_ManagerInfo(clientinfo);
		return vReturn;
		
	}
    /**
     * ������Ϣ��ҳ��ѯ
     * @param annexinfo
     * @return
     */ 
	public PageLoader queryannexInfo(QueryCorporationInfo annexinfo){
			PageLoader pageLoader = null;
			ClientmanageDAO clientmanageDao = new ClientmanageDAO();
			pageLoader = clientmanageDao.queryannexInfo(annexinfo);
			return pageLoader;
			
		}
	
	//������Ϣ��ҳ��ѯ
	public PageLoader queryCorporationMarket(QueryCorporationInfo queryCorporationInfo) throws Exception
	{
		
		PageLoader pageLoader = null;
		ClientmanageDAO clientmanageDAO = new ClientmanageDAO();
		pageLoader=clientmanageDAO.queryCorporationMarket(queryCorporationInfo);
		return pageLoader;
		
	}
	
	//�ɶ���Ϣ��ҳ��ѯ
	public PageLoader queryShareholderInformation(QueryCorporationInfo queryCorporationInfo)throws Exception
	{
		PageLoader pageLoader = null;
		ClientmanageDAO clientmanageDAO = new ClientmanageDAO();
		pageLoader=clientmanageDAO.queryShareholderInformation(queryCorporationInfo);
		return pageLoader;
	}
	
	//���񱨱��ҳ��ѯ
	public PageLoader queryClientReport(QueryCorporationInfo queryCorporationInfo)throws Exception
	{
		PageLoader pageLoader = null;
		ClientmanageDAO clientmanageDAO = new ClientmanageDAO();
		pageLoader=clientmanageDAO.queryClientReport(queryCorporationInfo);
		return pageLoader;
	}
	
	//������Ϣ--����id�������������Ϣ
	public CorporationExtendsInfo findMarketInformationByID(long id,long officeid) throws Exception
	{
		CorporationExtendsInfo corporationExtendsInfo = new CorporationExtendsInfo();
		ClientmanageDAO clientmanageDAO = new ClientmanageDAO();
		corporationExtendsInfo = clientmanageDAO.findMarketInformationByID(id, officeid);
		return corporationExtendsInfo;
	}
	
	//�ɶ���Ϣ--����id��ѯ�����Ϣ
	public ArrayList findStockPartnerInformationByID(long id,long officeid) throws Exception
	{
		ArrayList list =new ArrayList();
		ClientmanageDAO clientmanageDAO = new ClientmanageDAO();
		list=clientmanageDAO.findStockPartnerInformationByID(id, officeid);
		return list;
	}
	
	//�ɶ���Ϣ��ѯ--�޸�
	public void updateStockClient(PartnerInfo partnerInfo) throws Exception
	{
		Connection con = null;
		boolean exists = false;
		con = Database.getConnection();
		ClientmanageDAO clientmanageDAO = new ClientmanageDAO(con);
		PartnerDAO partnerDAO = new PartnerDAO(con);
		exists=clientmanageDAO.isStockClientNoRepeat(partnerInfo);
		if(exists&&partnerInfo.getPartnerid()!=-1)
		{
			throw new IException("�ɶ��ͻ�����Ѵ���!!");
			
		}
		else
		{
			partnerDAO.update(partnerInfo);
		}

		if (con != null) {
			con.close();
			con = null;
		}
		
		
	}
	
	//�ɶ���Ϣ��ѯ--����
	public void addStockClient(PartnerInfo partnerInfo) throws Exception
	{
		Connection con = null;
		boolean exists = false;
		con = Database.getConnection();
		ClientmanageDAO clientmanageDAO = new ClientmanageDAO(con);
		PartnerDAO partnerDAO = new PartnerDAO(con);
		partnerDAO.setUseMaxID();
		exists=clientmanageDAO.isStockClientNoRepeat(partnerInfo);
		if(exists&&partnerInfo.getPartnerid()!=-1)
		{
			throw new IException("�ɶ��ͻ�����Ѵ���!!");
		}
		else
		{
			partnerDAO.add(partnerInfo);
		}
		if (con != null) {
			con.close();
			con = null;
		}
	}
	
	//ͨ���ͻ�id��ѯ���񱨱������Ϣ
	public ArrayList findClientReportByID(long id,long officeid) throws Exception
	{
		ArrayList list = new ArrayList();
		ClientmanageDAO clientmanageDAO = new ClientmanageDAO();
		list = clientmanageDAO.findClientReportByID(id, officeid);
		return list;
		
	}
	
	//���񱨱�����
	public void addClientReport(ClientUploadReportInfo clientUploadReportInfo)throws Exception
	{
		Connection con = null;
		con = Database.getConnection();
		ClientmanageDAO clientmanageDAO = new ClientmanageDAO(con);
		clientmanageDAO.addClientReport(clientUploadReportInfo);
	}
	
	//����id��ѯ���񱨱������Ϣ
	public ClientUploadReportInfo findClientReportInformationByID(long id) throws Exception
	{
		ClientUploadReportInfo clientUploadReportInfo = new ClientUploadReportInfo();
		ClientmanageDAO clientmanageDAO = new ClientmanageDAO();
		clientUploadReportInfo = clientmanageDAO.findClientReportInformationByID(id);
		return clientUploadReportInfo;
	}
	
	//���񱨱��޸�
	public void updateClientReport(ClientUploadReportInfo clientUploadReportInfo)throws Exception
	{
		Connection con = null;
		con = Database.getConnection();
		ClientmanageDAO clientmanageDAO = new ClientmanageDAO(con);
		clientmanageDAO.updateClientReport(clientUploadReportInfo);
	}

	/**
	 * �ͻ�������Ϣ��ҳ��ѯ
	 * @param clientInfo
	 * @return
	 */
	public PageLoader queryInfo(QueryCorporationInfo clientInfo){
		PageLoader pageLoader = null;
		ClientmanageDAO dao = new ClientmanageDAO();
		pageLoader = dao.queryClientmanageInfo(clientInfo);
		return pageLoader;
	}
	
	/**
	 * �ͻ���ϸ��Ϣ��ҳ��ѯ
	 * @param clientInfo
	 * @return
	 */
	public PageLoader queryClientDetailInfo(QueryCorporationInfo clientInfo){
		PageLoader pageLoader = null;
		ClientmanageDAO dao = new ClientmanageDAO();
		pageLoader = dao.queryClientDetailInfo(clientInfo);
		return pageLoader;
	}
	
	/**
	 * ��ȡ�ͻ�����
	 * @param clientInfo
	 * @return
	 */
	public String getClientCode(ClientInfo clientInfo) throws Exception{
		
		String code = "";
		ClientmanageDAO dao = new ClientmanageDAO();
		try {
			code = dao.genClientNo(clientInfo);
		} catch (Exception e) {
			throw new IException("��ȡ�ͻ��������");
		}
		return code;
	}
	
	/**
	 * �����ͻ�
	 * @param clientInfo
	 * @return
	 * @throws Exception
	 */
	public long addClientInfo(ClientInfo clientInfo) throws Exception{
		long id = -1;
		ClientmanageDAO dao = new ClientmanageDAO();
		if(!dao.isClientNoRepeat(clientInfo))
		{
			if(!dao.isNameRepeat(clientInfo))
			{
				id = dao.addClientInfo(clientInfo);
			}
			else
			{
				throw new IException("�ͻ����������Ѵ���");
			}
			
		}else
		{
			throw new IException("�ͻ���ˮ���Ѵ���");
		}
		
		return id;
	}
	
	/**
	 * ���¿ͻ�
	 * @param clientInfo
	 * @return
	 * @throws Exception
	 */
	public long updateClientInfo(ClientInfo clientInfo) throws Exception{
		long id = -1;
		ClientmanageDAO dao = new ClientmanageDAO();
		if(!dao.isNameRepeat(clientInfo))
		{
			id = dao.updateClientInfo(clientInfo);
		}
		else
		{
			throw new IException("�˷��˿ͻ����������Ѵ���!!");
		}
		return id;
	}
	
	/**
	 * ɾ���ͻ�
	 * @param clientInfo
	 * @return
	 * @throws Exception
	 */
	public long delClientInfo(ClientInfo clientInfo) throws Exception{
		long id = -1;
		ClientmanageDAO dao = new ClientmanageDAO();
		if(!dao.isCreateAccount(clientInfo))
		{
			if(!dao.isExistLower(clientInfo))
			{
				id = dao.updateClientInfo(clientInfo);
			}
			else
			{
				throw new IException("�˿ͻ������¼��ͻ�������ɾ��!!");
			}
		}
		else
		{
			throw new IException("�˿ͻ��ѿ����˻�������ɾ��!!");
		}
		return id;
	}
	
	/**
	 * �����ͻ���ϸ��Ϣ
	 * @param corporationInfo
	 * @return
	 * @throws Exception
	 */
	public long addCorporation(CorporationInfo corporationInfo) throws Exception{
		long id = -1;
		ClientmanageDAO dao = new ClientmanageDAO();
		id = dao.addCorporation(corporationInfo);
		return id;
	}
	
	/**
	 * ���¿ͻ���ϸ��Ϣ
	 * @param corporationInfo
	 * @return
	 * @throws Exception
	 */
	public long updateCorporation(CorporationInfo corporationInfo) throws Exception{
		long id = -1;
		ClientmanageDAO dao = new ClientmanageDAO();
		id = dao.updateCorporation(corporationInfo);
		return id;
	}
	
	/**
	 * ���ݿͻ�ID��ÿͻ���Ϣ
	 * @param clientInfo
	 * @return
	 * @throws ITreasuryDAOException
	 */
	public Collection findClientInfoByID(ClientInfo clientInfo) throws ITreasuryDAOException{
		
		Collection coll = null;
		ClientmanageDAO dao = new ClientmanageDAO();
		coll = dao.findByCondition(clientInfo);
		return coll;
	}
	
	/**
	 * ���ݿͻ�ID��ÿͻ���ϸ��Ϣ
	 * @param clientInfo
	 * @return
	 * @throws ITreasuryDAOException
	 */
	public Collection findCorporationInfoByID(CorporationInfo corporationInfo) throws ITreasuryDAOException{
		
		Collection coll = null;
		ClientmanageDAO dao = new ClientmanageDAO();
		coll = dao.findCorporationInfoByID(corporationInfo);
		return coll;
	}
	
	/**
	 * �ͻ���Ϣ���ܲ�ѯ
	 * @param queryClientInfo
	 * @return
	 * @throws Exception
	 */
	public Collection findQueryClientInfo(QueryClientInfo queryClientInfo) throws Exception{
		
		Collection coll = null;
		ClientmanageDAO dao = new ClientmanageDAO();
		coll = dao.getQueryClientInfo(queryClientInfo);
		return coll;
	}
	
	/**
	 * ���ݿͻ�ID��ÿͻ�levelcode
	 * @param clientInfo
	 * @return
	 * @throws ITreasuryDAOException
	 */
	public String findClientLevelCodeByID(ClientInfo clientInfo) throws Exception{
		
		String strLevelCode = "";
		ClientmanageDAO dao = new ClientmanageDAO();
		strLevelCode = dao.findClientLevelCodeByID(clientInfo);
		return strLevelCode;
	}
	
	/**
	 * �ϼ��ͻ��޸�ʱ�����¼��ͻ��ļ������
	 * @param clientInfo
	 * @return
	 * @throws ITreasuryDAOException
	 */
	public long updateClientLevelCode(String str1,String str2) throws Exception{
		
		long lReturn = -1;
		ClientmanageDAO dao = new ClientmanageDAO();
		lReturn = dao.updateClientLevelCode(str1,str2);
		return lReturn;
	}
	

	 /**
     * ��ҵ���¼���Ϣ��ҳ��ѯ
     * @param clientInfo
     * @return
     */
	 public PageLoader queryEnterpriseMemoInfo(QueryCorporationInfo clientInfo){
			PageLoader pageLoader = null;
			ClientmanageDAO clientmanageDao = new ClientmanageDAO();
			pageLoader = clientmanageDao.queryEnterpriseMemoInfo(clientInfo);
			return pageLoader;
			
	}
	 
	public String isExistPartner(long id,long office) throws ITreasuryException{
		
		boolean exists = false;
		ClientmanageDAO clientmanageDao = new ClientmanageDAO();
		exists = clientmanageDao.isExistPartner(id,office);
		return String.valueOf(exists);
	}
	
	/**
	 * ɾ���ⲿ��λ��Ϣ
	 * 
	 * @param lID
	 *         �ⲿ��λID
	 * @return @throws
	 *         RemoteException
	 * @throws IRollbackException
	 */
	public long deleteExtClient(long lID) throws IException
	{
		long lReturn = -1;
		long exist = -1;
		ClientmanageDAO dao = new ClientmanageDAO();
		try
		{
			 exist = dao.isExsitForExtClient(lID);
			 if(exist>0)
			 {	 
			throw new IException("���ⲿ��λ�Ѿ���ʹ�ã�����ɾ��!");	 
			 }else{
		    lReturn = dao.deleteExtClient(lID);
			 }
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IException(true, e.getMessage(), e);
		}
		return lReturn;
	}
	
	/**
	 * ��ȡ�ⲿ��λ����
	 * @param clientInfo
	 * @return
	 */
	public String getClientCode(ExtClientInfo info) throws Exception{
		
		String code = "";
		ClientmanageDAO dao = new ClientmanageDAO();
		try {
			code = dao.genClientNo(info);
		} catch (Exception e) {
			throw new IException("��ȡ�ͻ��������");
		}
		return code;
	}
	
	/**
	 * �����ⲿ��λ
	 * @param clientInfo
	 * @return
	 * @throws Exception
	 */
	public long addExtClientInfo(ExtClientInfo clientInfo) throws Exception{
		long id = -1;
		ClientmanageDAO dao = new ClientmanageDAO();
			if(!dao.isExtClientNameRepeat(clientInfo))
			{
				id = dao.addExtClientInfo(clientInfo);
			}
			else
			{
				throw new IException("�ͻ����������Ѵ���!");
			}
		return id;
	}
	
	/**
	 * ����ID����ⲿ��λ��Ϣ
	 * @param clientInfo
	 * @return
	 * @throws ITreasuryDAOException
	 */
	public Collection findExtClientInfoByID(ExtClientInfo clientInfo) throws ITreasuryDAOException{
		
		Collection coll = null;
		ClientmanageDAO dao = new ClientmanageDAO();
		
		coll = dao.findExtClientByCondition(clientInfo);
		return coll;
	}
	
	/**
	 * �����ⲿ��λ��Ϣ
	 * @param clientInfo
	 * @return
	 * @throws Exception
	 */
	public long updateExtClientInfo(ExtClientInfo clientInfo) throws Exception{
		long id = -1;
		ClientmanageDAO dao = new ClientmanageDAO();
		if(!dao.isExtClientNameRepeat(clientInfo))
		{
			id = dao.updateExtClientInfo(clientInfo);
		}
		else
		{
			throw new IException("�ͻ����������Ѵ���!");
		}
		return id;
	}
	
	public ArrayList getEconomicDepartmentList() throws Exception
	{
		ArrayList list = new ArrayList();
		ClientmanageDAO dao = new ClientmanageDAO();
		list = dao.getEconomicDepartmentList();
		return list;
		
	}
	
	public ArrayList getEconomicComponentList() throws Exception
	{
		ArrayList list = new ArrayList();
		ClientmanageDAO dao = new ClientmanageDAO();
		list = dao.getEconomicComponentList();
		return list;
		
	}	
	
	public ArrayList getEnterpriseSizeList() throws Exception
	{
		ArrayList list = new ArrayList();
		ClientmanageDAO dao = new ClientmanageDAO();
		list = dao.getEnterpriseSizeList();
		return list;
		
	}	
	
	public ArrayList getProvinceList() throws Exception
	{
		ArrayList list = new ArrayList();
		ClientmanageDAO dao = new ClientmanageDAO();
		list = dao.getAreaList(CMConstant.DepositAndLoanMessage.Area.PROVINCE,"");
		return list;
		
	}		
	
	public String getCityString(String code) throws Exception
	{
		String city = "";
		ArrayList list = new ArrayList();
		ClientmanageDAO dao = new ClientmanageDAO();
		list = dao.getAreaList(CMConstant.DepositAndLoanMessage.Area.CITY,code.substring(0, 2));
		city = getAreaString(list);
		return city;
	}
	
	public ArrayList getCityList(String code) throws Exception
	{
		ArrayList list = new ArrayList();
		ClientmanageDAO dao = new ClientmanageDAO();		
		list = dao.getAreaList(CMConstant.DepositAndLoanMessage.Area.CITY,code.substring(0, 2));
		return list;
	}
	
	public String getAreaString(ArrayList list) throws Exception
	{
		String result = "";
		ClientAreaInfo info = null;
		if(list!=null)
		{
			Iterator it = list.iterator();
			while(it.hasNext())
			{
				info = (ClientAreaInfo)it.next(); 
				result = result + "{'code':'"+info.getAreaCode()+"','name':'"+info.getAreaName()+"'},";
			}	
			result = "["+result.substring(0, result.length()-1)+"]";
		}

		return result;
	}
	
	public String getCountyString(String code) throws Exception
	{
		String county = "";
		ArrayList list = new ArrayList();
		ClientmanageDAO dao = new ClientmanageDAO();		
		list = dao.getAreaList(CMConstant.DepositAndLoanMessage.Area.COUNTY,code.substring(0, 4));
		county = getAreaString(list);
		return county;
	}
	
	public ArrayList getCountyList(String code) throws Exception
	{
		ArrayList list = new ArrayList();
		ClientmanageDAO dao = new ClientmanageDAO();		
		list = dao.getAreaList(CMConstant.DepositAndLoanMessage.Area.COUNTY,code.substring(0, 4));
		return list;
	}	
	
	public ArrayList getCategoryList() throws Exception
	{
		ArrayList list = new ArrayList();
		ClientmanageDAO dao = new ClientmanageDAO();	
		list = dao.getIndustryList(CMConstant.DepositAndLoanMessage.Industry.CATEGORY, "");
		return list;
	}
	
	public String getBigCategoryString(String categoryCode) throws Exception
	{
		String bigCategory = "";
		ArrayList list = new ArrayList();
		ClientmanageDAO dao = new ClientmanageDAO();
		list = dao.getIndustryList(CMConstant.DepositAndLoanMessage.Industry.BIGCATEGORY, categoryCode);
		bigCategory = getIndustryString(list);
		return bigCategory;
	}
	
	public ArrayList getBigCategoryList(String categoryCode) throws Exception
	{
		ArrayList list = new ArrayList();
		ClientmanageDAO dao = new ClientmanageDAO();
		list = dao.getIndustryList(CMConstant.DepositAndLoanMessage.Industry.BIGCATEGORY, categoryCode);
		return list;
	}
	
	public String getIndustryString(ArrayList list)throws Exception
	{
		String result = "";
		EconomicIndustryInfo info = null;
		if(list!=null)
		{
			Iterator it = list.iterator();
			while(it.hasNext())
			{
				info = (EconomicIndustryInfo)it.next();
				result = result + "{'code':'"+info.getIndustryCode()+"','name':'"+info.getIndustryType()+"'},";
			}
			result = "["+result.substring(0, result.length()-1)+"]";
			
		}
		return result;
	}
	
	public long saveDepositAndLoanMessage(DepositAndLoanMessageInfo info) throws Exception
	{
		long lId = -1;
		ClientmanageDAO dao = new ClientmanageDAO("client_depositAndLoanMessage");
		dao.setUseMaxID();
		lId = dao.add(info);
		return lId;
	}
	
	public void updateDepositAndLoanMessage(DepositAndLoanMessageInfo info) throws Exception
	{
		ClientmanageDAO dao = new ClientmanageDAO("client_depositAndLoanMessage");
		dao.update(info);
	}
	
	public DepositAndLoanMessageDetailInfo findDepositAndLoanMessageDetailById(long id,long clientId) throws Exception
	{
		ClientmanageDAO dao = new ClientmanageDAO();
		DepositAndLoanMessageDetailInfo info = dao.findDepositAndLoanMessageDetailById(id,clientId);
		return info;
	}
}
