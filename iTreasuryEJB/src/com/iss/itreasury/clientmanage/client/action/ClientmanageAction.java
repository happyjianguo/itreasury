package com.iss.itreasury.clientmanage.client.action;

import java.util.Map;

import com.iss.itreasury.clientmanage.client.bizlogic.ClientmanageBiz;
import com.iss.itreasury.clientmanage.client.dataentity.ExtClientInfo;
import com.iss.itreasury.clientmanage.client.dataentity.QueryCorporationInfo;
import com.iss.sysframe.pager.dataentity.PagerInfo;

/**
 * �ͻ�����ģ��-�����
 * @author xiangzhou 2012-11-29
 *
 */
public class ClientmanageAction {
	
	ClientmanageBiz clientBiz = new ClientmanageBiz();
	
	/**
	 * �ͻ�������Ϣ��ѯ��
	 * add by liaiyi 2012-11-28
	 */
	public PagerInfo queryClientAccount(Map map) throws Exception
	{
		PagerInfo pagerInfo = null;
		try
		{
			QueryCorporationInfo queryCorporationInfo = new QueryCorporationInfo();
			queryCorporationInfo.convertMapToDataEntity(map);
			pagerInfo = clientBiz.queryClientAccount(queryCorporationInfo);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
	}
	/**
	 * �ͻ���ϸ���ϲ�ѯ��
	 * add by liaiyi 2012-11-28
	 */
	public PagerInfo queryDetialAccount(Map map) throws Exception
	{
		PagerInfo pagerInfo = null;
		try
		{
			QueryCorporationInfo queryCorporationInfo = new QueryCorporationInfo();
			queryCorporationInfo.convertMapToDataEntity(map);
			pagerInfo = clientBiz.queryDetialAccount(queryCorporationInfo);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
	}
	/**
	 * ���в�ѯ��
	 * add by liaiyi 2012-11-28
	 */
    public PagerInfo queryMarketAccount(Map map) throws Exception{
		
		PagerInfo pagerInfo = null;
		
		try
		{
			QueryCorporationInfo queryCorporationInfo = new QueryCorporationInfo();
			queryCorporationInfo.convertMapToDataEntity(map);
			pagerInfo = clientBiz.queryMarketAccount(queryCorporationInfo);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
	}
    /**
     * �ɶ���Ϣ��ѯ��
     * add by liaiyi 2012-11-28
     */
    public PagerInfo queryStockPartnerAccount(Map map) throws Exception
	{
		PagerInfo pagerInfo = null;
		try
		{
			QueryCorporationInfo queryCorporationInfo = new QueryCorporationInfo();
			queryCorporationInfo.convertMapToDataEntity(map);
			pagerInfo = clientBiz.queryStockPartnerAccount(queryCorporationInfo);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
	}
    /**
     * �ɶ���Ϣ��ѯ
     * add by liaiyi 2012-11-28
     */
    public PagerInfo queryStockPartner(Map map) throws Exception
	{
		PagerInfo pagerInfo = null;
		long clientID = -1;
		long OfficeID = -1;
		try
		{
			if(map.get("clientid") != null){
				clientID = Long.parseLong((String)map.get("clientid"));
			}
			if(map.get("officeid") != null){
				OfficeID = Long.parseLong((String)map.get("officeid"));
			}
			pagerInfo = clientBiz.queryStockPartner(clientID,OfficeID);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
	}

    /**
     * �������Ϣ��ѯ��
     * add by liaiyi 2012-12-04
     */
    public PagerInfo querymanagerInfo(Map map) throws Exception
	{
		PagerInfo pagerInfo = null;
		try
		{
			QueryCorporationInfo queryCorporationInfo = new QueryCorporationInfo();
			queryCorporationInfo.convertMapToDataEntity(map);
			pagerInfo = clientBiz.querymanagerInfo(queryCorporationInfo);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
	}
    
    /**
     * �������Ϣ��ѯ��
     * add by liaiyi 2012-12-04
     */
    public PagerInfo querymanager(Map map) throws Exception
	{
		PagerInfo pagerInfo = null;
		long clientID = -1;
		long OfficeID = -1;
		try
		{
			if(map.get("clientid") != null){
				clientID = Long.parseLong((String)map.get("clientid"));
			}
			if(map.get("officeid") != null){
				OfficeID = Long.parseLong((String)map.get("officeid"));
			}
			pagerInfo = clientBiz.querymanager(clientID,OfficeID);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
	}
    
    /**
     * ��ҵ���¼���ѯ��
     * add by liaiyi 2012-12-04
     */
     public PagerInfo queryEnterpriseMemoInfo(Map map) throws Exception
 	{
 		PagerInfo pagerInfo = null;
 		try
 		{
 			QueryCorporationInfo clientInfo = new QueryCorporationInfo();
 			clientInfo.convertMapToDataEntity(map);
 			pagerInfo = clientBiz.queryEnterpriseMemoInfo(clientInfo);
 		}
 		catch(Exception e)
 		{
 			e.printStackTrace();
 			throw new Exception(e.getMessage(), e);
 		}
 		return pagerInfo;
 	}
     
     /**
      * ��ҵ���¼���ѯ
      * add by liaiyi 2012-12-04
      */
      public PagerInfo queryEnterpriseInfo(Map map) throws Exception
  	{
  		PagerInfo pagerInfo = null;
  		long clientID = -1;
		long OfficeID = -1;
		try
		{
			if(map.get("clientid") != null){
				clientID = Long.parseLong((String)map.get("clientid"));
			}
			if(map.get("officeid") != null){
				OfficeID = Long.parseLong((String)map.get("officeid"));
			}
			pagerInfo = clientBiz.queryEnterpriseInfo(clientID,OfficeID);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
  		return pagerInfo;
  	}
   /**
    * �ⲿ��λά����ѯ��
    * add by liaiyi 2012-12-04
    */
    public PagerInfo queryExtClient(Map map) throws Exception
	{
    	long officeid = -1;
    	long currencyid = -1;
    	long statusid = -1;
		PagerInfo pagerInfo = null;
		try
		{
			ExtClientInfo extClientInfo = new ExtClientInfo();
			//extClientInfo.convertMapToDataEntity(map);
			if(map.get("officeid") != null){
				officeid = Long.parseLong((String)map.get("officeid"));
				extClientInfo.setOfficeid(officeid);
			}
			if(map.get("currencyid") != null){
				currencyid = Long.parseLong((String)map.get("currencyid"));
				extClientInfo.setCurrencyid(currencyid);
			}
			if(map.get("statusid") != null){
				statusid = Long.parseLong((String)map.get("statusid"));
				extClientInfo.setStatusid(statusid);
			}
			pagerInfo = clientBiz.queryExtClient(extClientInfo);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
	}
    /**
	 * ��Ӫ��Ϣaction
	 * @author zk 2012-12-03
	 *
	 */
	public PagerInfo queryManagementInfo(Map map) throws Exception {
		
		PagerInfo pagerInfo = null;
		try
		{
			QueryCorporationInfo qInfo = new QueryCorporationInfo();
			qInfo.convertMapToDataEntity(map);//��Mapת��ΪINFO
			pagerInfo = clientBiz.queryManagementInfo(qInfo);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
	}
	/**
	 * ҵ����Ϣaction
	 * @author zk 2012-12-04
	 *
	 */
	public PagerInfo queryBusinessInfo(Map map) throws Exception {
		
		PagerInfo pagerInfo = null;
		try
		{
			QueryCorporationInfo qInfo = new QueryCorporationInfo();
			qInfo.convertMapToDataEntity(map);//��Mapת��ΪINFO
			pagerInfo = clientBiz.queryBusinessInfo(qInfo);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
	}
	/**
	 * ��ϵ��Ϣaction
	 * @author zk 2012-12-04
	 *
	 */
	public PagerInfo queryContactInfo(Map map) throws Exception {
		
		PagerInfo pagerInfo = null;
		try
		{
			QueryCorporationInfo qInfo = new QueryCorporationInfo();
			qInfo.convertMapToDataEntity(map);//��Mapת��ΪINFO
			pagerInfo = clientBiz.queryContactInfo(qInfo);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(),e);
		}
		return pagerInfo;
	}
	/**
	 * ����Ͷ����Ϣaction
	 * @author zk 2012-12-05
	 *
	 */
	public PagerInfo queryForeignInvestmentInfo(Map map) throws Exception {
		
		PagerInfo pagerInfo = null;
		try
		{
			QueryCorporationInfo qInfo = new QueryCorporationInfo();
			qInfo.convertMapToDataEntity(map);//��Mapת��ΪINFO
			pagerInfo = clientBiz.queryForeignInvestmentInfo(qInfo);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(),e);
		}
		return pagerInfo;
	}
	/**
	 * ��ñ��ΪclientID����ҵ��������ҵͶ����Ϣaction
	 * @author zk 2012-12-05
	 *
	 */
	public PagerInfo queryForeignInvestmentInfoByClientID(Map map) throws Exception {
		
		PagerInfo pagerInfo = null;
		long clientID = -1;
		try
		{
			if(map !=null && map.get("clientid") != null){
				clientID = Long.parseLong((String)map.get("clientid"));
			}
			pagerInfo = clientBiz.queryForeignInvestmentInfoByClientID(clientID);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(),e);
		}
		return pagerInfo;
	}
	/**
	 * ���񱨱�action
	 * @author zk 2012-12-06
	 *
	 */
	public PagerInfo queryFinanceReportInfo(Map map) throws Exception {
		
		PagerInfo pagerInfo = null;
		try
		{
			QueryCorporationInfo qInfo = new QueryCorporationInfo();
			qInfo.convertMapToDataEntity(map);//��Mapת��ΪINFO
			pagerInfo = clientBiz.queryFinanceReportInfo(qInfo);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(),e);
		}
		return pagerInfo;
	}
	/**
	 * ͨ���ͻ�id��ѯ���񱨱������Ϣaction
	 * @author zk 2012-12-06
	 *
	 */
	public PagerInfo queryFinanceReportByID(Map map) throws Exception {
		
		PagerInfo pagerInfo = null;
		long clientID = -1;
		long officeID = -1;
		try
		{
			if(map !=null && map.get("clientid") != null){
				clientID = Long.parseLong((String)map.get("clientid"));
			}
			if(map !=null && map.get("officeid") != null){
				officeID = Long.parseLong((String)map.get("officeid"));
			}
			pagerInfo = clientBiz.queryFinanceReportByID(clientID,officeID);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(),e);
		}
		return pagerInfo;
	}
	/**
	 * ������Ϣaction
	 * @author zk 2012-12-07
	 *
	 */
	public PagerInfo queryAnnexInfo(Map map) throws Exception {
		
		PagerInfo pagerInfo = null;
		try
		{
			QueryCorporationInfo qInfo = new QueryCorporationInfo();
			qInfo.convertMapToDataEntity(map);//��Mapת��ΪINFO
			pagerInfo = clientBiz.queryAnnexInfo(qInfo);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(),e);
		}
		return pagerInfo;
	}
	/**
	 * ͨ���ͻ�id��ѯ������Ϣaction
	 * @author zk 2012-12-10
	 *
	 */
	public PagerInfo queryAnnexInfoByClientID(Map map) throws Exception {
		
		PagerInfo pagerInfo = null;
		long clientID = -1;
		long officeID = -1;
		try
		{
			if(map !=null && map.get("lclientid") != null){
				clientID = Long.parseLong((String)map.get("lclientid"));
			}
			if(map !=null && map.get("officeid") != null){
				officeID = Long.parseLong((String)map.get("officeid"));
			}
			pagerInfo = clientBiz.queryAnnexInfoByClientID(clientID,officeID);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(),e);
		}
		return pagerInfo;
	}
    
}