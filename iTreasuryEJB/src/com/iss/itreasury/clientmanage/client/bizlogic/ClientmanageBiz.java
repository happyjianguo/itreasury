package com.iss.itreasury.clientmanage.client.bizlogic;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import com.iss.itreasury.clientmanage.client.dao.ClientmanageDao_New;
import com.iss.itreasury.clientmanage.client.dataentity.ExtClientInfo;
import com.iss.itreasury.clientmanage.client.dataentity.QueryCorporationInfo;
import com.iss.itreasury.clientmanage.util.CMConstant;
import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.ConfigConstant;
import com.iss.itreasury.util.DownLoadFileNameEncryptionAndDecrypt;
import com.iss.itreasury.util.IException;
import com.iss.sysframe.pager.dataentity.PagerDepictBaseInfo;
import com.iss.sysframe.pager.dataentity.PagerInfo;
import com.iss.sysframe.pager.dataentity.PagerTypeConstant;
import com.iss.sysframe.pager.dataentity.ResultPagerRowInfo;
import com.iss.sysframe.pager.tools.PagerTools;

/**
 * 客户管理模块-业务层
 * @author xiangzhou 2012-11-29
 *
 */
public class ClientmanageBiz {

   ClientmanageDao_New clientDao = new ClientmanageDao_New();

	/**
	 * 客户基本信息查询业务层
	 * @param queryCorporationInfo
	 * @return
	 * @throws Exception
	 */
	public PagerInfo queryClientAccount(QueryCorporationInfo queryCorporationInfo) throws Exception{
		
		PagerInfo pagerInfo = null;
		String sql = null;
		try
		{
			pagerInfo = new PagerInfo();
			//得到查询SQL
			sql = clientDao.queryClientAccountSQL(queryCorporationInfo);
			pagerInfo.setSqlString(sql);
			
			ArrayList depictList = new ArrayList();
			PagerDepictBaseInfo baseInfo = null;
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("Code");
			baseInfo.setExtension(true);
			baseInfo.setExtensionName(new String[]{"Code", "AccountNo"});
			baseInfo.setExtensionType(new long[]{PagerTypeConstant.STRING, PagerTypeConstant.LONG});
			baseInfo.setExtensionString(PagerTypeConstant.LOGOTYPE + "," + PagerTypeConstant.LOGOTYPE);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("Name");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("parentCorpName");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("budgetparent");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
			
			pagerInfo.setDepictList(depictList);
		
		}	catch(Exception e)
			{
				e.printStackTrace();
				throw new Exception("====>查询异常", e);
			}
			return pagerInfo;
		}
	
	/**
	 * 客户明細資料查询业务层
	 * @param queryCorporationInfo
	 * @return
	 * @throws Exception
	 */
	
   public PagerInfo queryDetialAccount(QueryCorporationInfo queryCorporationInfo) throws Exception{
		
		PagerInfo pagerInfo = null;
		String sql = null;
		try
		{
			pagerInfo = new PagerInfo();
			//得到查询SQL
			sql = clientDao.queryDetialAccountSQL(queryCorporationInfo);
			pagerInfo.setSqlString(sql);
			
			ArrayList depictList = new ArrayList();
			PagerDepictBaseInfo baseInfo = null;
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("Code");
			baseInfo.setExtension(true);
			baseInfo.setExtensionName(new String[]{"Code", "AccountNo"});
			baseInfo.setExtensionType(new long[]{PagerTypeConstant.STRING, PagerTypeConstant.LONG});
			baseInfo.setExtensionString(PagerTypeConstant.LOGOTYPE + "," + PagerTypeConstant.LOGOTYPE);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("Name");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("LegalPerson");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("LegalPersonCodecert");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
			
			pagerInfo.setDepictList(depictList);
		
		}	catch(Exception e)
			{
				e.printStackTrace();
				throw new Exception("====>查询异常", e);
			}
			return pagerInfo;
		}
   
   /**
    * 上市信息查询业务层
    * add by liaiyi 2012-11-28
    *
    */
   public PagerInfo queryMarketAccount(QueryCorporationInfo queryCorporationInfo) throws Exception{
		PagerInfo pagerInfo = null;
		String sql = null;
		try
		{
			pagerInfo = new PagerInfo();
			//得到查询SQL
		sql = clientDao.queryMarketAccountSQL(queryCorporationInfo);
		pagerInfo.setSqlString(sql);
		
		ArrayList depictList = new ArrayList();
		PagerDepictBaseInfo baseInfo = null;
		
		baseInfo = new PagerDepictBaseInfo();
		baseInfo.setDisplayName("ClientCode");
		baseInfo.setExtension(true);
		baseInfo.setExtensionName(new String[]{"ClientCode", "AccountNo","ClientCode"});
		baseInfo.setExtensionType(new long[]{PagerTypeConstant.STRING, PagerTypeConstant.LONG, PagerTypeConstant.STRING});
		baseInfo.setExtensionString(PagerTypeConstant.LOGOTYPE + "," + PagerTypeConstant.LOGOTYPE+ "," + PagerTypeConstant.LOGOTYPE);
		depictList.add(baseInfo);
		
		baseInfo = new PagerDepictBaseInfo();
		baseInfo.setDisplayName("ClientName");
		baseInfo.setDisplayType(PagerTypeConstant.STRING);
		depictList.add(baseInfo);
		
		baseInfo = new PagerDepictBaseInfo();
		baseInfo.setDisplayName("lMarketSpace1");
		baseInfo.setDisplayType(PagerTypeConstant.STRING);
		depictList.add(baseInfo);
		
		baseInfo = new PagerDepictBaseInfo();
		baseInfo.setDisplayName("lStockNo1");
		baseInfo.setDisplayType(PagerTypeConstant.STRING);
		depictList.add(baseInfo);
		
		baseInfo = new PagerDepictBaseInfo();
		baseInfo.setDisplayName("lMarketSpace2");
		baseInfo.setDisplayType(PagerTypeConstant.STRING);
		depictList.add(baseInfo);
		
		baseInfo = new PagerDepictBaseInfo();
		baseInfo.setDisplayName("lStockNo2");
		baseInfo.setDisplayType(PagerTypeConstant.STRING);
		depictList.add(baseInfo);
		
		baseInfo = new PagerDepictBaseInfo();
		baseInfo.setDisplayName("lMarketSpace3");
		baseInfo.setDisplayType(PagerTypeConstant.STRING);
		depictList.add(baseInfo);
		
		baseInfo = new PagerDepictBaseInfo();
		baseInfo.setDisplayName("lStockNo3");
		baseInfo.setDisplayType(PagerTypeConstant.STRING);
		depictList.add(baseInfo);
		
		baseInfo = new PagerDepictBaseInfo();
		baseInfo.setDisplayName("lMarketSpace4");
		baseInfo.setDisplayType(PagerTypeConstant.STRING);
		depictList.add(baseInfo);
		
		baseInfo = new PagerDepictBaseInfo();
		baseInfo.setDisplayName("lStockNo4");
		baseInfo.setDisplayType(PagerTypeConstant.STRING);
		depictList.add(baseInfo);
		
		baseInfo = new PagerDepictBaseInfo();
		baseInfo.setDisplayName("lMarketSpace5");
		baseInfo.setDisplayType(PagerTypeConstant.STRING);
		depictList.add(baseInfo);
		
		baseInfo = new PagerDepictBaseInfo();
		baseInfo.setDisplayName("lStockNo5");
		baseInfo.setDisplayType(PagerTypeConstant.STRING);
		depictList.add(baseInfo);
		
		pagerInfo.setDepictList(depictList);
	
	}	catch(Exception e)
		{
		e.printStackTrace();
		throw new Exception("====>查询异常", e);
			
		}
		return pagerInfo;
	}
   /**
    * 股东信息查询业务层
    * add by liaiyi 2012-11-28
    *
    */
   public PagerInfo queryStockPartnerAccount(QueryCorporationInfo queryCorporationInfo) throws Exception{
		
		PagerInfo pagerInfo = null;
		String sql = null;
		try
		{
		pagerInfo = new PagerInfo();
		sql = clientDao.queryStockPartnerAccountSQL(queryCorporationInfo);
		pagerInfo.setSqlString(sql);
		
		ArrayList depictList = new ArrayList();
		PagerDepictBaseInfo baseInfo = null;
		
		baseInfo = new PagerDepictBaseInfo();
		baseInfo.setDisplayName("ClientCode");
		baseInfo.setExtension(true);
		baseInfo.setExtensionName(new String[]{"ClientCode", "AccountNo","ClientCode"});
		baseInfo.setExtensionType(new long[]{PagerTypeConstant.STRING, PagerTypeConstant.LONG,PagerTypeConstant.STRING});
		baseInfo.setExtensionString(PagerTypeConstant.LOGOTYPE + "," + PagerTypeConstant.LOGOTYPE+ "," + PagerTypeConstant.LOGOTYPE);
		depictList.add(baseInfo);
		
		baseInfo = new PagerDepictBaseInfo();
		baseInfo.setDisplayName("ClientName");
		baseInfo.setDisplayType(PagerTypeConstant.STRING);
		depictList.add(baseInfo);
		
		baseInfo = new PagerDepictBaseInfo();
		baseInfo.setDisplayName("StockholderNameCHN");
		baseInfo.setDisplayType(PagerTypeConstant.STRING);
		depictList.add(baseInfo);
		
		baseInfo = new PagerDepictBaseInfo();
		baseInfo.setDisplayName("StockCharacter");
		baseInfo.setDisplayType(PagerTypeConstant.STRING);
		depictList.add(baseInfo);
		
		baseInfo = new PagerDepictBaseInfo();
		baseInfo.setDisplayName("StockWay");
		baseInfo.setDisplayType(PagerTypeConstant.STRING);
		depictList.add(baseInfo);
		
		baseInfo = new PagerDepictBaseInfo();
		baseInfo.setDisplayName("ContributiveCurrency");
		baseInfo.setDisplayType(PagerTypeConstant.STRING);
		depictList.add(baseInfo);
		
		baseInfo = new PagerDepictBaseInfo();
		baseInfo.setDisplayName("ContributiveAmount");
		baseInfo.setDisplayType(PagerTypeConstant.STRING);
		depictList.add(baseInfo);
		
		baseInfo = new PagerDepictBaseInfo();
		baseInfo.setDisplayName("StockRate");
		baseInfo.setDisplayType(PagerTypeConstant.AMOUNT_2);
		depictList.add(baseInfo);
		
		baseInfo = new PagerDepictBaseInfo();
		baseInfo.setDisplayName("IsStockhoder");
		baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
		baseInfo.setExtensionMothod(CMConstant.StockHolderType.class, "getStockHolderType", new Class[]{long.class});
		depictList.add(baseInfo);
		
		pagerInfo.setDepictList(depictList);
		}	catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
		
	}

   /**
    * 股东信息查询业务
    * add by liaiyi 2012-11-28
    *
    */
   public PagerInfo queryStockPartner(long clientID,long officeID) throws Exception{
		
		PagerInfo pagerInfo = null;
		String sql = null;
		try
		{
		pagerInfo = new PagerInfo();
		sql = clientDao.queryStockPartnerSQL(clientID,officeID);
		pagerInfo.setSqlString(sql);
		
		ArrayList depictList = new ArrayList();
		PagerDepictBaseInfo baseInfo = null;
		
		
		baseInfo = new PagerDepictBaseInfo();
		baseInfo.setDisplayName("StockholderNameCHN");
		baseInfo.setExtension(true);
		baseInfo.setExtensionName(new String[]{"StockholderNameCHN", "partnerid","lID","stockHoldertype"});
		baseInfo.setExtensionType(new long[]{PagerTypeConstant.STRING,PagerTypeConstant.STRING, PagerTypeConstant.LONG,PagerTypeConstant.STRING});
		baseInfo.setExtensionString(PagerTypeConstant.LOGOTYPE + "," + PagerTypeConstant.LOGOTYPE+ "," + PagerTypeConstant.LOGOTYPE+ "," + PagerTypeConstant.LOGOTYPE);
		depictList.add(baseInfo);
		
		baseInfo = new PagerDepictBaseInfo();
		baseInfo.setDisplayName("stockholderNameEN");
		baseInfo.setDisplayType(PagerTypeConstant.STRING);
		depictList.add(baseInfo);
		
		baseInfo = new PagerDepictBaseInfo();
		baseInfo.setDisplayName("StockCharacter");
		baseInfo.setDisplayType(PagerTypeConstant.STRING);
		depictList.add(baseInfo);
		
		baseInfo = new PagerDepictBaseInfo();
		baseInfo.setDisplayName("StockWay");
		baseInfo.setDisplayType(PagerTypeConstant.STRING);
		depictList.add(baseInfo);
		
		
//		baseInfo = new PagerDepictBaseInfo();
//		baseInfo.setDisplayName("nTransactionTypeID");
//		baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
//		baseInfo.setExtensionMothod(SETTConstant.TransactionType.class, "getName", new Class[]{long.class});
//		depictList.add(baseInfo);
//		
//		SETTConstant.TransactionType. getName(1);
		
		
		baseInfo = new PagerDepictBaseInfo();
		baseInfo.setDisplayName("ContributiveCurrency");
		baseInfo.setDisplayType(PagerTypeConstant.STRING);
		depictList.add(baseInfo);
		
		baseInfo = new PagerDepictBaseInfo();
		baseInfo.setDisplayName("ContributiveAmount");
		baseInfo.setDisplayType(PagerTypeConstant.STRING);
		depictList.add(baseInfo);
		
		baseInfo = new PagerDepictBaseInfo();
		baseInfo.setDisplayName("StockRate");
		baseInfo.setDisplayType(PagerTypeConstant.AMOUNT_2);
		depictList.add(baseInfo);
		
		baseInfo = new PagerDepictBaseInfo();
		baseInfo.setDisplayName("IsStockhoder");
		baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
		baseInfo.setExtensionMothod(CMConstant.StockHolderType.class, "getStockHolderType", new Class[]{long.class});
		depictList.add(baseInfo);
		
		pagerInfo.setDepictList(depictList);
		}	catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
		
	}
   
   /**
    * 管理层信息查询业务
    * add by liaiyi 2012-12-05
    */
 public PagerInfo querymanagerInfo(QueryCorporationInfo queryCorporationInfo) throws Exception{
	   
	   PagerInfo pagerInfo = null;
	   String sql = null;
	   try
	   {
		   pagerInfo = new PagerInfo();
		   sql = clientDao.querymanagerInfoSQL(queryCorporationInfo);
		   pagerInfo.setSqlString(sql);
		   
		   ArrayList depictList = new ArrayList();
		   PagerDepictBaseInfo baseInfo = null;
		   
		   
		   baseInfo = new PagerDepictBaseInfo();
		   baseInfo.setDisplayName("Code");
		   baseInfo.setExtension(true);
		   baseInfo.setExtensionName(new String[]{"Code", "client_info_id","Code","Name"});
		   baseInfo.setExtensionType(new long[]{PagerTypeConstant.STRING,PagerTypeConstant.STRING, PagerTypeConstant.STRING,PagerTypeConstant.STRING});
		   baseInfo.setExtensionString(PagerTypeConstant.LOGOTYPE + "," + PagerTypeConstant.LOGOTYPE+ "," + PagerTypeConstant.LOGOTYPE+ "," + PagerTypeConstant.LOGOTYPE);
		   depictList.add(baseInfo);
		   
		   baseInfo = new PagerDepictBaseInfo();
		   baseInfo.setDisplayName("Name");
		   baseInfo.setDisplayType(PagerTypeConstant.STRING);
		   depictList.add(baseInfo);
		   
		   baseInfo = new PagerDepictBaseInfo();
		   baseInfo.setDisplayName("Position");
		   baseInfo.setDisplayType(PagerTypeConstant.STRING);
		   depictList.add(baseInfo);
		   
		   baseInfo = new PagerDepictBaseInfo();
		   baseInfo.setDisplayName("Managername");
		   baseInfo.setDisplayType(PagerTypeConstant.STRING);
		   depictList.add(baseInfo);
		   
		   baseInfo = new PagerDepictBaseInfo();
		   baseInfo.setDisplayName("Mail");
		   baseInfo.setDisplayType(PagerTypeConstant.STRING);
		   depictList.add(baseInfo);
		   
		  
		   
		   pagerInfo.setDepictList(depictList);
	   }	catch(Exception e)
	   {
		   e.printStackTrace();
		   throw new Exception("====>查询异常", e);
	   }
	   return pagerInfo;
 }
 
 /**
  * 管理层信息查询业务
  * add by liaiyi 2012-12-05
  */
public PagerInfo querymanager(long clientID,long OfficeID) throws Exception{
	   
	PagerInfo pagerInfo = null;
	String sql = null;
	try
	{
		pagerInfo = new PagerInfo();
		sql = clientDao.querymanagerSQL(clientID,OfficeID);
		pagerInfo.setSqlString(sql);
		
		pagerInfo.setExtensionMothod(ClientmanageBiz.class, "resultSetHandle2");
		
	}
	catch(Exception e)
	{
		e.printStackTrace();
		throw new Exception("====>查询异常", e);
	}
	return pagerInfo;
}
	public ArrayList resultSetHandle2(ResultSet rs) throws IException {
	
	ArrayList resultList = new ArrayList(); //最终返回结果
	ArrayList cellList = null;//列
	ResultPagerRowInfo rowInfo = null;//行
	
	long pid = -1;
	String Position = null;
	String Managername = null;
	String Mail = null;
	String Tel = null;
	
	
	try {
		if(rs!=null)
		{
		while(rs.next()){
			pid = rs.getLong("pid");
			Position = rs.getString("Position");
			Managername = rs.getString("Managername");
			Mail = rs.getString("Mail");
			Tel = rs.getString("Tel");
			
			if(pid > 0){
				//存储列数据
				cellList = new ArrayList();
				PagerTools.returnCellList(cellList,Position);
				PagerTools.returnCellList(cellList,Managername+","+pid);
				PagerTools.returnCellList(cellList,Mail);
				PagerTools.returnCellList(cellList,Tel);
				
				//存储行数据
				rowInfo = new ResultPagerRowInfo();
				rowInfo.setCell(cellList);
				rowInfo.setId(String.valueOf(rs.getLong("rownum1")));
				
				//返回数据
				resultList.add(rowInfo);
			}	
		}
		}
	} catch (SQLException e) {
		e.printStackTrace();
		throw new IException(e.getMessage());
	}
	
	return resultList;
}

/**
 * 企业大事件查询业务
 * add by liaiyi 2012-12-05
 */
 public PagerInfo queryEnterpriseMemoInfo(QueryCorporationInfo queryCorporationInfo) throws Exception{
	   
	   PagerInfo pagerInfo = null;
	   String sql = null;
	   try
	   {
		   pagerInfo = new PagerInfo();
		   sql = clientDao.queryEnterpriseMemoSQL(queryCorporationInfo);
		   pagerInfo.setSqlString(sql);
		   
		   ArrayList depictList = new ArrayList();
		   PagerDepictBaseInfo baseInfo = null;
		   
		   
		   baseInfo = new PagerDepictBaseInfo();
		   baseInfo.setDisplayName("Code");
		   baseInfo.setExtension(true);
		   baseInfo.setExtensionName(new String[]{"Code", "client_info_id","Code","Name"});
		   baseInfo.setExtensionType(new long[]{PagerTypeConstant.STRING,PagerTypeConstant.STRING, PagerTypeConstant.STRING,PagerTypeConstant.STRING});
		   baseInfo.setExtensionString(PagerTypeConstant.LOGOTYPE + "," + PagerTypeConstant.LOGOTYPE+ "," + PagerTypeConstant.LOGOTYPE+ "," + PagerTypeConstant.LOGOTYPE);
		   depictList.add(baseInfo);
		   
		   baseInfo = new PagerDepictBaseInfo();
		   baseInfo.setDisplayName("Name");
		   baseInfo.setDisplayType(PagerTypeConstant.STRING);
		   depictList.add(baseInfo);
		   
		   baseInfo = new PagerDepictBaseInfo();
		   baseInfo.setDisplayName("EventDate");
		   baseInfo.setDisplayType(PagerTypeConstant.DATE);
		   depictList.add(baseInfo);
		   
		   baseInfo = new PagerDepictBaseInfo();
		   baseInfo.setDisplayName("MemoDescribe");
		   baseInfo.setDisplayType(PagerTypeConstant.STRING);
		   depictList.add(baseInfo);
		   
		   pagerInfo.setDepictList(depictList);
	   }	catch(Exception e)
	   {
		   e.printStackTrace();
		   throw new Exception("====>查询异常", e);
	   }
	   return pagerInfo;
}

/**
 * 企业大事件查询业务
 * add by liaiyi 2012-12-05
 */
public PagerInfo queryEnterpriseInfo(long clientID,long OfficeID) throws Exception{
	   
	PagerInfo pagerInfo = null;
	String sql = null;
	try
	{
		pagerInfo = new PagerInfo();
		sql = clientDao.queryEnterpriseSQL(clientID,OfficeID);
		pagerInfo.setSqlString(sql);
		
		pagerInfo.setExtensionMothod(ClientmanageBiz.class, "resultSetHandle1");
		
	}
	catch(Exception e)
	{
		e.printStackTrace();
		throw new Exception("====>查询异常", e);
	}
	return pagerInfo;
}
	public ArrayList resultSetHandle1(ResultSet rs) throws IException {
	
	ArrayList resultList = new ArrayList(); //最终返回结果
	ArrayList cellList = null;//列
	ResultPagerRowInfo rowInfo = null;//行
	
	long pid = -1;
	Date EventDate = null;
	String MemoDescribe = null;
	String Describedetail = null;
	
	
	try {
		if(rs!=null)
		{
		while(rs.next()){
			pid = rs.getLong("pid");
			EventDate = rs.getDate("EventDate");
			MemoDescribe = rs.getString("MemoDescribe");
			Describedetail = rs.getString("Describedetail");
			
			if(pid > 0){
				//存储列数据
				cellList = new ArrayList();
				PagerTools.returnCellList(cellList,EventDate);
				PagerTools.returnCellList(cellList,MemoDescribe+","+pid);
				PagerTools.returnCellList(cellList,Describedetail);
				
				//存储行数据
				rowInfo = new ResultPagerRowInfo();
				rowInfo.setCell(cellList);
				rowInfo.setId(String.valueOf(rs.getLong("rownum1")));
				
				//返回数据
				resultList.add(rowInfo);
			}	
		}
		}
	} catch (SQLException e) {
		e.printStackTrace();
		throw new IException(e.getMessage());
	}
	
	return resultList;
}

   /**
    * 外部维护查询业务
    * add by liaiyi 2012-12-05
    *
    */
   public PagerInfo queryExtClient(ExtClientInfo extClientInfo) throws Exception{
	   
	   PagerInfo pagerInfo = null;
	   String sql = null;
	   try
	   {
		   pagerInfo = new PagerInfo();
		   sql = clientDao.queryExtClientSQL(extClientInfo);
		   pagerInfo.setSqlString(sql);
		   
		   ArrayList depictList = new ArrayList();
		   PagerDepictBaseInfo baseInfo = null;
		   
		   baseInfo = new PagerDepictBaseInfo();
		   baseInfo.setDisplayName("id");
		   baseInfo.setDisplayType(PagerTypeConstant.STRING);
		   depictList.add(baseInfo);

		   
		   baseInfo = new PagerDepictBaseInfo();
		   baseInfo.setDisplayName("code");
		   baseInfo.setExtension(true);
		   baseInfo.setExtensionName(new String[]{"code", "id"});
		   baseInfo.setExtensionType(new long[]{PagerTypeConstant.STRING, PagerTypeConstant.LONG});
		   baseInfo.setExtensionString(PagerTypeConstant.LOGOTYPE + "," + PagerTypeConstant.LOGOTYPE);
		   depictList.add(baseInfo);
		   
		   baseInfo = new PagerDepictBaseInfo();
		   baseInfo.setDisplayName("name");
		   baseInfo.setDisplayType(PagerTypeConstant.STRING);
		   depictList.add(baseInfo);
		   
		   baseInfo = new PagerDepictBaseInfo();
		   baseInfo.setDisplayName("engname");
		   baseInfo.setDisplayType(PagerTypeConstant.STRING);
		   depictList.add(baseInfo);
		   
		   baseInfo = new PagerDepictBaseInfo();
		   baseInfo.setDisplayName("linkman");
		   baseInfo.setDisplayType(PagerTypeConstant.STRING);
		   depictList.add(baseInfo);
		   
		   baseInfo = new PagerDepictBaseInfo();
		   baseInfo.setDisplayName("tel");
		   baseInfo.setDisplayType(PagerTypeConstant.STRING);
		   depictList.add(baseInfo);
		   
		   baseInfo = new PagerDepictBaseInfo();
		   baseInfo.setDisplayName("address");
		   baseInfo.setDisplayType(PagerTypeConstant.STRING);
		   depictList.add(baseInfo);
		   
		   baseInfo = new PagerDepictBaseInfo();
		   baseInfo.setDisplayName("remarks");
		   baseInfo.setDisplayType(PagerTypeConstant.STRING);
		   depictList.add(baseInfo);
		   
		
		   
		   pagerInfo.setDepictList(depictList);
	   }	catch(Exception e)
	   {
		   e.printStackTrace();
		   throw new Exception("====>查询异常", e);
	   }
	   return pagerInfo;
	   
   }
   /**
	 * 经营信息biz
	 * @author zk 2012-12-03
	 *
	 */
	public PagerInfo queryManagementInfo (QueryCorporationInfo qInfo) throws Exception {
		
		PagerInfo pagerInfo = null;
		String sql = null;	
		try
		{
			pagerInfo = new PagerInfo();
			sql = clientDao.queryManagementInfo(qInfo);
			pagerInfo.setSqlString(sql);
			
			ArrayList depictList = new ArrayList();
			PagerDepictBaseInfo baseInfo = null;
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("clientCode");
			baseInfo.setExtension(true);
			baseInfo.setExtensionName(new String[]{"clientCode","clientid","clientCode"});
			baseInfo.setExtensionType(new long[]{PagerTypeConstant.STRING,PagerTypeConstant.STRING,PagerTypeConstant.STRING});
			baseInfo.setExtensionString(PagerTypeConstant.LOGOTYPE +","+PagerTypeConstant.LOGOTYPE +","+PagerTypeConstant.LOGOTYPE);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("clientName");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("dealscope");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("productscope");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("capitalscope");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("netcapital");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
			
			pagerInfo.setDepictList(depictList);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
	}
	/**
	 * 业务信息biz
	 * @author zk 2012-12-04
	 *
	 */
	public PagerInfo queryBusinessInfo (QueryCorporationInfo qInfo) throws Exception {
		
		PagerInfo pagerInfo = null;
		String sql = null;	
		try
		{
			pagerInfo = new PagerInfo();
			sql = clientDao.queryBusinessInfo(qInfo);
			pagerInfo.setSqlString(sql);
			
			ArrayList depictList = new ArrayList();
			PagerDepictBaseInfo baseInfo = null;
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("clientCode");
			baseInfo.setExtension(true);
			baseInfo.setExtensionName(new String[]{"clientCode","clientid","clientCode"});
			baseInfo.setExtensionType(new long[]{PagerTypeConstant.STRING,PagerTypeConstant.STRING,PagerTypeConstant.STRING});
			baseInfo.setExtensionString(PagerTypeConstant.LOGOTYPE +","+PagerTypeConstant.LOGOTYPE +","+PagerTypeConstant.LOGOTYPE);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("clientName");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("account");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("bank1");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("extendAccount1");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("loanCardNo");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("riskLevel");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("creditLevelID");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("insideCreditLevel");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
			
			pagerInfo.setDepictList(depictList);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
	}
	/**
	 * 联系信息biz
	 * @author zk 2012-12-04
	 *
	 */
	public PagerInfo queryContactInfo(QueryCorporationInfo qInfo) throws Exception {
		
		PagerInfo pagerInfo = null;
		String sql = null;
		try
		{
			pagerInfo = new PagerInfo();
			sql = clientDao.queryContactInfo(qInfo);
			pagerInfo.setSqlString(sql);
			
			ArrayList depictList = new ArrayList();
			PagerDepictBaseInfo baseInfo = null;
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("clientCode");
			baseInfo.setExtension(true);
			baseInfo.setExtensionName(new String[]{"clientCode","clientid","clientCode"});
			baseInfo.setExtensionType(new long[]{PagerTypeConstant.STRING,PagerTypeConstant.STRING,PagerTypeConstant.STRING});
			baseInfo.setExtensionString(PagerTypeConstant.LOGOTYPE +","+PagerTypeConstant.LOGOTYPE +","+PagerTypeConstant.LOGOTYPE);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("clientName");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("country");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("province");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("city");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
			
			pagerInfo.setDepictList(depictList);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
	}
	/**
	 * 对外投资信息biz
	 * @author zk 2012-12-05
	 *
	 */
	public PagerInfo queryForeignInvestmentInfo(QueryCorporationInfo qInfo) throws Exception {
		
		PagerInfo pagerInfo = null;
		String sql = null;
		try
		{
			pagerInfo = new PagerInfo();
			sql = clientDao.queryForeignInvestmentInfo(qInfo);
			pagerInfo.setSqlString(sql);
			
			ArrayList depictList = new ArrayList();
			PagerDepictBaseInfo baseInfo = null;
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("clientCode");
			baseInfo.setExtension(true);
			baseInfo.setExtensionName(new String[]{"clientCode","clientid","clientCode","clientName"});
			baseInfo.setExtensionType(new long[]{PagerTypeConstant.STRING,PagerTypeConstant.STRING,PagerTypeConstant.STRING,PagerTypeConstant.STRING});
			baseInfo.setExtensionString(PagerTypeConstant.LOGOTYPE +","+PagerTypeConstant.LOGOTYPE +","+PagerTypeConstant.LOGOTYPE +","+PagerTypeConstant.LOGOTYPE);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("clientName");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("partnerName");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("stockCharacter");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("stockWay");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("contributiveCurrency");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("contributiveAmount");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
			
			pagerInfo.setDepictList(depictList);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
	}
	/**
	 * 获得编号为clientID的企业的所有企业投资信息biz
	 * @author zk 2012-12-05
	 *
	 */
	public PagerInfo queryForeignInvestmentInfoByClientID(long clientID) throws Exception {
		
		PagerInfo pagerInfo = null;
		String sql = null;
		try
		{
			pagerInfo = new PagerInfo();
			sql = clientDao.queryForeignInvestmentInfoByClientID(clientID);
			pagerInfo.setSqlString(sql);
			
			ArrayList depictList = new ArrayList();
			PagerDepictBaseInfo baseInfo = null;
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("partnerName");
			baseInfo.setExtension(true);
			baseInfo.setExtensionName(new String[]{"partnerName","id","partnerid","partnertype"});
			baseInfo.setExtensionType(new long[]{PagerTypeConstant.STRING,PagerTypeConstant.LONG,PagerTypeConstant.LONG,PagerTypeConstant.LONG});
			baseInfo.setExtensionString(PagerTypeConstant.LOGOTYPE +","+PagerTypeConstant.LOGOTYPE +","+PagerTypeConstant.LOGOTYPE +","+PagerTypeConstant.LOGOTYPE);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("stockCharacter");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("stockWay");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("contributiveCurrency");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("contributiveAmount");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("stockRate");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
			
			pagerInfo.setDepictList(depictList);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
	}
	/**
	 * 财务报表biz
	 * @author zk 2012-12-06
	 *
	 */
	public PagerInfo queryFinanceReportInfo(QueryCorporationInfo qInfo) throws Exception {
		
		PagerInfo pagerInfo = null;
		String sql = null;
		try
		{
			pagerInfo = new PagerInfo();
			sql = clientDao.queryFinanceReportInfo(qInfo);
			pagerInfo.setSqlString(sql);
			
			ArrayList depictList = new ArrayList();
			PagerDepictBaseInfo baseInfo = null;
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("clientCode");
			baseInfo.setExtension(true);
			baseInfo.setExtensionName(new String[]{"clientCode","clientid","clientCode","clientName"});
			baseInfo.setExtensionType(new long[]{PagerTypeConstant.STRING,PagerTypeConstant.STRING,PagerTypeConstant.STRING,PagerTypeConstant.STRING});
			baseInfo.setExtensionString(PagerTypeConstant.LOGOTYPE +","+PagerTypeConstant.LOGOTYPE +","+PagerTypeConstant.LOGOTYPE +","+PagerTypeConstant.LOGOTYPE);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("clientName");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("reportName");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("reportTypeID");
			baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
			baseInfo.setExtensionMothod(CMConstant.ClientReportType.class, "getClientReportType", new Class[]{long.class});
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("reportDate");
			baseInfo.setDisplayType(PagerTypeConstant.DATE);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("reportID");
			baseInfo.setExtension(true);
			baseInfo.setExtensionName(new String[]{"reportID","reportID","clientID"});
			baseInfo.setExtensionType(new long[]{PagerTypeConstant.LONG, PagerTypeConstant.LONG, PagerTypeConstant.LONG});
			baseInfo.setExtensionString(PagerTypeConstant.LOGOTYPE + "," + PagerTypeConstant.LOGOTYPE + "," + PagerTypeConstant.LOGOTYPE);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("reportID");
			baseInfo.setExtension(true);
			baseInfo.setExtensionName(new String[]{"reportID", "sDocPath"});
			baseInfo.setExtensionType(new long[]{PagerTypeConstant.LONG, PagerTypeConstant.STRING});
			baseInfo.setExtensionString(PagerTypeConstant.LOGOTYPE + "," + PagerTypeConstant.LOGOTYPE);
			depictList.add(baseInfo);
			
			pagerInfo.setDepictList(depictList);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
	}
	/**
	 * 通过客户id查询财务报表相关信息biz
	 * @author zk 2012-12-06
	 *
	 */
	public PagerInfo queryFinanceReportByID(long clientID,long officeID) throws Exception {
		
		PagerInfo pagerInfo = null;
		String sql = null;
		try
		{
			pagerInfo = new PagerInfo();
			sql = clientDao.queryFinanceReportByID(clientID,officeID);
			pagerInfo.setSqlString(sql);
			
			ArrayList depictList = new ArrayList();
			PagerDepictBaseInfo baseInfo = null;
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("reportName");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("reportType");
			baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
			baseInfo.setExtensionMothod(CMConstant.ClientReportType.class, "getClientReportType", new Class[]{long.class});
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("reportDate");
			baseInfo.setDisplayType(PagerTypeConstant.DATE);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("reportID");
			baseInfo.setExtension(true);
			baseInfo.setExtensionName(new String[]{"reportID","reportID","clientID"});
			baseInfo.setExtensionType(new long[]{PagerTypeConstant.LONG, PagerTypeConstant.LONG, PagerTypeConstant.LONG});
			baseInfo.setExtensionString(PagerTypeConstant.LOGOTYPE + "," + PagerTypeConstant.LOGOTYPE + "," + PagerTypeConstant.LOGOTYPE);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("reportID");
			baseInfo.setExtension(true);
			baseInfo.setExtensionName(new String[]{"reportID", "sDocPath"});
			baseInfo.setExtensionType(new long[]{PagerTypeConstant.LONG, PagerTypeConstant.STRING});
			baseInfo.setExtensionString(PagerTypeConstant.LOGOTYPE + "," + PagerTypeConstant.LOGOTYPE);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("reportID");
			baseInfo.setExtension(true);
			baseInfo.setExtensionName(new String[]{"reportID", "reportID"});
			baseInfo.setExtensionType(new long[]{PagerTypeConstant.LONG, PagerTypeConstant.LONG});
			baseInfo.setExtensionString(PagerTypeConstant.LOGOTYPE + "," + PagerTypeConstant.LOGOTYPE);
			depictList.add(baseInfo);
			
			pagerInfo.setDepictList(depictList);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
	}
	/**
	 * 附件信息biz
	 * @author zk 2012-12-07
	 *
	 */
	public PagerInfo queryAnnexInfo(QueryCorporationInfo qInfo) throws Exception {
		
		PagerInfo pagerInfo = null;
		String sql = null;
		try
		{
			pagerInfo = new PagerInfo();
			sql = clientDao.queryAnnexInfo(qInfo);
			pagerInfo.setSqlString(sql);
			
			pagerInfo.setExtensionMothod(ClientmanageBiz.class, "resultSetHandle");
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
	}
	public ArrayList resultSetHandle(ResultSet rs) throws IException {
		
		ArrayList resultList = new ArrayList(); //最终返回结果
		ArrayList cellList = null;//列
		ResultPagerRowInfo rowInfo = null;//行
		
		long clientID = -1;
		String clientCode = null;
		String clientName = null;
		long nFileID = -1;
		String strFileID = "";
		Date signStart = null;
		String sClientFileName = null;
		boolean isFileIDEncrypt=Config.getBoolean(ConfigConstant.GLOBAL_DOWNLOAD_FILEID_CAN_ENCRYPT,false);
		DownLoadFileNameEncryptionAndDecrypt en = new DownLoadFileNameEncryptionAndDecrypt();
		
		try {
			while(rs.next()){
				clientID = rs.getLong("ID");
				clientCode = rs.getString("clientCode") == null ? "" : rs.getString("clientCode");
				clientName = rs.getString("clientName") == null ? "" : rs.getString("clientName");
				nFileID = rs.getLong("NFILEID");
				signStart = rs.getDate("signstart");
				sClientFileName = rs.getString("sclientfilename") == null ? "" : rs.getString("sclientfilename");
				
				if(nFileID > 0){
					if(isFileIDEncrypt){
						strFileID = en.getEncString(String.valueOf(nFileID));
					}else{
						strFileID = String.valueOf(nFileID);
					}
				}else{
					strFileID = " ";
				}
				
				//存储列数据
				cellList = new ArrayList();
				PagerTools.returnCellList(cellList,clientCode+","+clientID+","+clientCode+","+clientName+","+signStart);
				PagerTools.returnCellList(cellList,clientName);
				PagerTools.returnCellList(cellList,sClientFileName+","+strFileID);
				
				//存储行数据
				rowInfo = new ResultPagerRowInfo();
				rowInfo.setCell(cellList);
				rowInfo.setId(String.valueOf(rs.getLong("rownum1")));
				
				//返回数据
				resultList.add(rowInfo);
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new IException(e.getMessage());
		}
		
		return resultList;
	}
	/**
	 * 通过客户id查询附件信息biz
	 * @author zk 2012-12-10
	 *
	 */
	public PagerInfo queryAnnexInfoByClientID(long clientID,long officeID) throws Exception {
		
		PagerInfo pagerInfo = null;
		String sql = null;
		try
		{
			pagerInfo = new PagerInfo();
			sql = clientDao.queryAnnexInfoByClientID(clientID,officeID);
			pagerInfo.setSqlString(sql);
			
			pagerInfo.setExtensionMothod(ClientmanageBiz.class, "getResultByClientIDSetHandle");
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
	}
	public ArrayList getResultByClientIDSetHandle(ResultSet rs) throws IException {
		
		ArrayList resultList = new ArrayList(); //最终返回结果
		ArrayList cellList = null;//列
		ResultPagerRowInfo rowInfo = null;//行
		
		long nDocID = -1;
		long nFileID = -1;
		String strFileID = "";
		String showName = null;
		String realName = null;
		boolean isFileIDEncrypt=Config.getBoolean(ConfigConstant.GLOBAL_DOWNLOAD_FILEID_CAN_ENCRYPT,false);
		DownLoadFileNameEncryptionAndDecrypt en = new DownLoadFileNameEncryptionAndDecrypt();
		
		try {
			while(rs.next()){
				nDocID = rs.getLong("docID");
				nFileID = rs.getLong("id");
				showName = rs.getString("sClientFileName") == null ? "" : rs.getString("sClientFileName"); 
				realName = rs.getString("sClientPath") == null ? "" : rs.getString("sClientPath");
				
				if(isFileIDEncrypt){
					strFileID=en.getEncString(String.valueOf(nFileID));
				}else{
					strFileID=String.valueOf(nFileID);
				}
				
				//存储列数据
				cellList = new ArrayList();
				PagerTools.returnCellList(cellList,showName+","+strFileID);
				PagerTools.returnCellList(cellList,realName);
				PagerTools.returnCellList(cellList,"删除"+","+"3"+","+nDocID);
				
				//存储行数据
				rowInfo = new ResultPagerRowInfo();
				rowInfo.setCell(cellList);
				rowInfo.setId(String.valueOf(rs.getLong("rownum1")));
				
				//返回数据
				resultList.add(rowInfo);
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new IException(e.getMessage());
		}
		
		return resultList;
	}
	
}