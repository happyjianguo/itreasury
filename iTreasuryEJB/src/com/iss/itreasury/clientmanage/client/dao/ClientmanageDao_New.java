package com.iss.itreasury.clientmanage.client.dao;

import com.iss.itreasury.clientmanage.client.dataentity.ExtClientInfo;
import com.iss.itreasury.clientmanage.client.dataentity.QueryCorporationInfo;
import com.iss.itreasury.clientmanage.util.CMConstant;
import com.iss.itreasury.util.Constant;

/**
 * 客户管理模块-数据层
 * @author xiangzhou 2012-11-29
 *
 */
public class ClientmanageDao_New {

	/**
	 * 客戶基本信息分頁查詢
	 * @param queryCorporationInfo
	 * @return
	 */
   public String queryClientAccountSQL(QueryCorporationInfo queryCorporationInfo){
		
		StringBuffer sbSQL = new StringBuffer();
		//select
		sbSQL.append("select c.id AccountNo,code,name,(select name from client_clientinfo where id = parentcorpid1) parentCorpName,budgetparent \n");
		//form
		sbSQL.append("from client_clientinfo c,client_corporationinfo o \n");
		//where
		sbSQL.append("where c.id = o.clientid(+) \n");
		sbSQL.append(" and c.statusid ="+Constant.RecordStatus.VALID +"\n");
		sbSQL.append(" and c.clientbasetype = '"+CMConstant.ClientBaseType.CORPORATION+"'" +"\n");
		sbSQL.append(" and c.officeid = "+queryCorporationInfo.getOfficeID());
		if(queryCorporationInfo.getLClientNOStart()!=null && !queryCorporationInfo.getLClientNOStart().equals("")){
			sbSQL.append(" and c.code >= '"+queryCorporationInfo.getLClientNOStart()+"'");
		}
		if(queryCorporationInfo.getLClientNOEnd()!=null && !queryCorporationInfo.getLClientNOEnd().equals("")){
			sbSQL.append(" and c.code <= '"+queryCorporationInfo.getLClientNOEnd()+"'");
		}
		
		sbSQL.append("order by code asc");
		
		return sbSQL.toString();
		
	}
   /**
    * 客户明细资料查询数据层
    * add by liaiyi 2012-11-28
    *
    */
   public String queryDetialAccountSQL(QueryCorporationInfo queryCorporationInfo){
		
		  StringBuffer sbSQL= new StringBuffer();
			
		  sbSQL.append("select c.id AccountNo,code Code,name Name,legalperson ,legalpersoncodecert \n");
			
		  sbSQL.append("from client_clientinfo c,client_corporationinfo o \n");
			
		  sbSQL.append("where c.id = o.clientid(+) \n");
		  sbSQL.append(" and c.statusid ="+Constant.RecordStatus.VALID);
		  sbSQL.append(" and c.clientbasetype = "+Constant.RecordStatus.VALID +"\n"  );
		  sbSQL.append(" and c.officeid = "+queryCorporationInfo.getOfficeID()+"\n" );
			if(queryCorporationInfo.getLClientNOStart()!=null && !queryCorporationInfo.getLClientNOStart().equals("")){
				sbSQL.append(" and c.code >= '"+queryCorporationInfo.getLClientNOStart()+"'");
			}
			if(queryCorporationInfo.getLClientNOEnd()!=null && !queryCorporationInfo.getLClientNOEnd().equals("")){
				sbSQL.append(" and c.code <= '"+queryCorporationInfo.getLClientNOEnd()+"'");
			}
			
			    sbSQL.append("order by Code asc");
			
			return sbSQL.toString();
		
		}
   /**
    * 上市信息查询数据层
    * add by liaiyi 2012-11-28
    *
    */
   public String queryMarketAccountSQL(QueryCorporationInfo queryCorporationInfo){ 
		
		StringBuffer sbSQL= new StringBuffer();
		
		sbSQL.append(" SELECT c.id AccountNo,c.code ClientCode, ");
		sbSQL.append(" c.name ClientName, ");
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
		
		sbSQL.append(" and c.statusid = 1 ");
		sbSQL.append(" and c.clientbasetype = '1' ");
		
		if(queryCorporationInfo.getLClientNOStart()!=null && !queryCorporationInfo.getLClientNOStart().equals(""))
		{
			sbSQL.append(" and c.code>='"+queryCorporationInfo.getLClientNOStart()+"'");
		}
		if(queryCorporationInfo.getLClientNOEnd()!=null && !queryCorporationInfo.getLClientNOEnd().equals(""))
		{
			sbSQL.append(" and c.code<='"+queryCorporationInfo.getLClientNOEnd()+"'" );
		}
//		//order by
		sbSQL.append(" order by c.code ");
		
		return sbSQL.toString();
	}
   /**
    * 股东信息查询数据层
    * add by liaiyi 2012-11-28
    *
    */
   public String queryStockPartnerAccountSQL(QueryCorporationInfo queryCorporationInfo){
		
		StringBuffer sbSQL = new StringBuffer();
		
		sbSQL.append("select c.id AccountNo,p.partnerid partnerid,c.code ClientCode,c.name ClientName,p.stockholderNameCHN StockholderNameCHN,p.stockcharacter stockCharacter,p.stockway stockWay,p.contributiveCurrency ContributiveCurrency,p.contributiveamount contributiveAmount,p.stockrate stockRate,p.isstockholder IsStockhoder,c.code code,c.name name \n");
	
		
		//from 
		sbSQL.append("from (SELECT * FROM (SELECT aa.*, ROWNUM r  FROM (select *  from client_clientinfo \n");
		
		sbSQL.append("where statusid = 1 \n");
		sbSQL.append("and clientbasetype = '1' \n");
		sbSQL.append("and officeid = 1 \n");
		sbSQL.append("order by code) aa)\n");
		sbSQL.append("WHERE r BETWEEN 1 AND 10)\n");
		
		sbSQL.append(" c, (select pc.name StockholderNameCHN,p.stockcharacter StockCharacter,p.stockway StockWay,p.contributivecurrency ContributiveCurrency,p.contributiveamount ContributiveAmount,p.stockrate StockRate,p.isstockhoder Isstockholder,p.partnerid partnerid,p.clientid clientid \n");
		sbSQL.append(" from Client_PartnerOfClient p, Client_ClientInfo pc where p.partnerid = pc.id(+) and p.statusid(+) = 1 and p.stockholdertype = 1 \n");
		sbSQL.append("  union ");
		sbSQL.append(" select pc.name stockholderNameCHN,p.stockcharacter StockCharacter,p.stockway StockWay,p.contributivecurrency ContributiveCurrency,p.contributiveamount ContributiveAmount,p.stockrate StockRate,p.isstockhoder IsStockhoder,p.partnerid partnerid,p.clientid clientid \n");
		sbSQL.append("  from Client_PartnerOfClient p, client_extclientinfo pc where p.partnerid = pc.id(+) and p.statusid(+) = 1 and p.stockholdertype = 2) p \n");
		
		
		//where
		
		sbSQL.append("where 1=1 \n");
		sbSQL.append(" and c.id = p.clientid(+) \n");
		
		if(queryCorporationInfo.getLClientNOStart()!=null && !queryCorporationInfo.getLClientNOStart().equals("")){
		sbSQL.append(" and c.code >= '"+queryCorporationInfo.getLClientNOStart()+"'");
		}
	   if(queryCorporationInfo.getLClientNOEnd()!=null && !queryCorporationInfo.getLClientNOEnd().equals("")){
			sbSQL.append(" and c.code <= '"+queryCorporationInfo.getLClientNOEnd()+"'");
		}
		//order by
		
		sbSQL.append(" order by c.code ");


		return sbSQL.toString();
		
	}
   
   /**
    * 股东信息查询数据
    * add by liaiyi 2012-11-28
    *
    */
   public String queryStockPartnerSQL(long clientID,long officeID){
		
		StringBuffer sbSQL = new StringBuffer();
		
		sbSQL.append(" select * from ( SELECT p.id lID,c.id clientid, p.partnerid partnerid,c.name clientName,");
		sbSQL.append(" pc.name stockholderNameCHN, pc.engname stockholderNameEN, ");
		sbSQL.append(" p.stockcharacter stockCharacter,p.stockway stockWay,");
		sbSQL.append(" p.contributivecurrency ContributiveCurrency,p.contributiveamount contributiveAmount, ");
		sbSQL.append(" p.stockrate stockRate,p.isstockhoder isStockHoder,c.code code,p.stockHoldertype stockHoldertype ");
		sbSQL.append(" FROM Client_ClientInfo c, Client_PartnerOfClient p, Client_ClientInfo pc ");
		sbSQL.append(" WHERE 1 = 1 ");
		sbSQL.append(" and c.id = p.clientid(+) ");
		sbSQL.append(" and p.partnerid = pc.id(+) ");
		sbSQL.append(" and c.statusid = 1 ");
		sbSQL.append(" and c.clientbasetype = '1' ");
		sbSQL.append(" and p.statusid=1 ");
		sbSQL.append(" and p.stockholdertype=1 ");
		sbSQL.append(" and c.officeid ="+officeID);
		sbSQL.append(" and c.id ="+clientID);
		sbSQL.append(" union ");
		sbSQL.append(" SELECT p.id lID,c.id clientid, p.partnerid partnerid,c.name clientName, ");
		sbSQL.append(" pc.name stockholderNameCHN, pc.engname stockholderNameEN, ");
		sbSQL.append(" p.stockcharacter stockCharacter,p.stockway stockWay,");
		sbSQL.append(" p.contributivecurrency ContributiveCurrency,p.contributiveamount contributiveAmount, ");
		sbSQL.append(" p.stockrate stockRate,p.isstockhoder isStockHoder,c.code code,p.stockHoldertype stockHoldertype");
		sbSQL.append(" FROM Client_ClientInfo c, Client_PartnerOfClient p, client_extclientinfo pc ");
		sbSQL.append(" WHERE 1 = 1");
		sbSQL.append(" and c.id = p.clientid(+) ");
		sbSQL.append(" and p.partnerid = pc.id(+) ");
		sbSQL.append(" and c.statusid = 1 ");
		sbSQL.append(" and c.clientbasetype = '1' ");
		sbSQL.append(" and p.statusid = 1 ");
		sbSQL.append(" and p.stockholdertype=2 ");
		sbSQL.append(" and c.officeid ="+officeID);
		sbSQL.append(" and c.id ="+clientID+" )");
		sbSQL.append(" where 1=1  ");
		sbSQL.append(" order by code  ");
		return sbSQL.toString();
		
	}
   /**
    * 管理层信息查询数据
    * 
    */
   public String querymanagerInfoSQL(QueryCorporationInfo clientInfo){
	   
	   StringBuffer sbSQL = new StringBuffer();
	   sbSQL.append("select a.mail,a.managername, a.position,f.code, f.name, a.id,f.id client_info_id \n");
	  
	   sbSQL.append("FROM (select * from client_clientinfo \n");
	   sbSQL.append("where statusid = 1 \n");
	   sbSQL.append("and clientbasetype = '1' \n");
	   sbSQL.append("and officeid = 1 \n");
	   sbSQL.append("order by code) \n");
	   sbSQL.append(" f, \n");
	   sbSQL.append("(select * from client_management where client_management.statusid = 1) a \n");
	   sbSQL.append("WHERE f.id = a.clientid(+) \n");
	   sbSQL.append(" and f.officeid = 1 \n");
	   if(clientInfo.getLClientNOStart()!=null && !clientInfo.getLClientNOStart().equals("")){
			sbSQL.append(" and f.code >= '"+clientInfo.getLClientNOStart()+"'");
			}
		   if(clientInfo.getLClientNOEnd()!=null && !clientInfo.getLClientNOEnd().equals("")){
				sbSQL.append(" and f.code <= '"+clientInfo.getLClientNOEnd()+"'");
			}
	   
	   sbSQL.append("order by f.code asc \n");
	   
	   return sbSQL.toString();
   }
   
   /**
    * 管理层信息查询
    * 
    */
   public String querymanagerSQL(long clientID,long OfficeID){
	   
	   StringBuffer sbSQL = new StringBuffer();
	   
	   sbSQL.append("select a.mail, a.tel Tel, a.managername managername, a.position, b.code, b.id clientid, b.name, a.abstract beizhu,a.id pid, b.id client_info_id \n");
	   sbSQL.append("from (select * from client_clientinfo where client_clientinfo.statusid = 1) b, \n");
	   sbSQL.append("(select * from client_management where client_management.statusid = 1) a \n");
	   sbSQL.append("where a.clientid = b.id \n");
	   sbSQL.append(" and b.id = '"+clientID+"'  \n");
	   sbSQL.append("and b.statusid = "+Constant.RecordStatus.VALID+" \n");
	   sbSQL.append("and b.clientbasetype = '1' \n");
	   sbSQL.append(" and b.officeid = "+OfficeID+" \n");
	  
	   
	   return sbSQL.toString();
   }
   /**
    * 企业大事件查询
    * 
    */
   public String queryEnterpriseMemoSQL(QueryCorporationInfo clientInfo){
	   
	   StringBuffer sbSQL = new StringBuffer();
	   
	   sbSQL.append("select  a.eventrecordno,a.eventdate EventDate,a.memodescribe,a.abstract,b.code,b.name,a.clientID,b.id client_info_id \n");
	   sbSQL.append("from  (select * from  client_clientinfo  \n");
	   sbSQL.append(" where statusid = 1  \n");
	   sbSQL.append("and clientbasetype = '1' and officeid =1  order by code  )  \n");
	   sbSQL.append("b , \n");
	   sbSQL.append(" ( select * from Client_EnterpriseMemo where Client_EnterpriseMemo.statusid = 1 ) a  \n");
	   sbSQL.append(" WHERE b.id = a.clientid(+)   \n");
	   sbSQL.append(" and b.officeid=1   \n");
	  
	   if(clientInfo.getLClientNOStart()!=null && !clientInfo.getLClientNOStart().equals("")){
			sbSQL.append(" and b.code >= '"+clientInfo.getLClientNOStart()+"'");
		}
		   if(clientInfo.getLClientNOEnd()!=null && !clientInfo.getLClientNOEnd().equals("")){
				sbSQL.append(" and b.code <= '"+clientInfo.getLClientNOEnd()+"'");
		}
	   
	   sbSQL.append("order by b.code asc \n");
	   return sbSQL.toString();
   }
   
   /**
    * 企业大事件查询
    * 
    */
   public String queryEnterpriseSQL(long clientID,long OfficeID){
	   
	   StringBuffer sbSQL = new StringBuffer();
	   

	    sbSQL.append(" SELECT a.eventrecordno,a.eventdate EventDate,a.memodescribe MemoDescribe,a.abstract,b.code,b.name,a.clientid,a.id pid,a.describedetail Describedetail,b.id client_info_id \n");
		sbSQL.append(" FROM ( select * from client_clientinfo where client_clientinfo.statusid = 1 ) b , ");
		sbSQL.append("( select * from Client_EnterpriseMemo where Client_EnterpriseMemo.statusid = 1 ) a ");
		sbSQL.append(" WHERE a.clientid = b.id and b.id='"+clientID+"'and b.clientbasetype = '1' and b.officeid ="+OfficeID+" ");
		sbSQL.append("order by a.eventdate asc"); 
	   
	   return sbSQL.toString();
   }
   
   /**
    * 外部单位维护查询数据
    * add by liaiyi 2012-12-04
    */
   public String queryExtClientSQL(ExtClientInfo extClientInfo){
		
		StringBuffer sbSQL = new StringBuffer();
		sbSQL.append(" SELECT * FROM client_extclientinfo \n");
		sbSQL.append(" WHERE  statusid='"+Constant.RecordStatus.VALID+"' \n");
		if(extClientInfo.getStatusid()!=-1)
		{
			sbSQL.append(" and statusid =" + extClientInfo.getStatusid());
		}
		if(extClientInfo.getOfficeid()!=-1)
		{
			sbSQL.append(" and officeid =" + extClientInfo.getOfficeid());
		}	
		if(extClientInfo.getCurrencyid()!=-1)
		{
			sbSQL.append(" and currencyid =" + extClientInfo.getCurrencyid());
		}	
		sbSQL.append(" order by id");
		return sbSQL.toString();
   }
   
   /**
	 * 经营信息分页查询SQL
	 * @param clientInfo
	 */
	public String queryManagementInfo(QueryCorporationInfo clientInfo){
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("select c.code clientCode,c.name clientName,o.clientid,o.dealscope,o.productscope,o.capitalscope,o.netcapital");
		sql.append(" from client_clientinfo c,client_corporationinfo o");
		sql.append(" where c.id = o.clientid(+)");
		sql.append(" and c.statusid ="+Constant.RecordStatus.VALID);
		sql.append(" and c.clientbasetype = '1'");
		sql.append(" and c.officeid = "+clientInfo.getOfficeID());
		if(clientInfo.getLClientNOStart()!=null && !clientInfo.getLClientNOStart().equals("")){
			sql.append(" and c.code >= '"+clientInfo.getLClientNOStart()+"'");
		}
		if(clientInfo.getLClientNOEnd()!=null && !clientInfo.getLClientNOEnd().equals("")){
			sql.append(" and c.code <= '"+clientInfo.getLClientNOEnd()+"'");
		}
		
		return sql.toString();
	}
	/**
	 * 业务信息分页查询SQL
	 * @param clientInfo
	 */
	public String queryBusinessInfo(QueryCorporationInfo clientInfo){
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("select c.code clientCode,c.name clientName,o.clientid,o.account,o.bank1,o.extendAccount1,o.loanCardNo,o.riskLevel,o.creditLevelID,o.insideCreditLevel");
		sql.append(" from client_clientinfo c,client_corporationinfo o");
		sql.append(" where c.id = o.clientid(+)");
		sql.append(" and c.statusid ="+Constant.RecordStatus.VALID);
		sql.append(" and c.clientbasetype = '1'");
		sql.append(" and c.officeid = "+clientInfo.getOfficeID());
		if(clientInfo.getLClientNOStart()!=null && !clientInfo.getLClientNOStart().equals("")){
			sql.append(" and c.code >= '"+clientInfo.getLClientNOStart()+"'");
		}
		if(clientInfo.getLClientNOEnd()!=null && !clientInfo.getLClientNOEnd().equals("")){
			sql.append(" and c.code <= '"+clientInfo.getLClientNOEnd()+"'");
		}
		
		return sql.toString();
	}
	 /**
	 * 联系信息分页查询SQL
	 * @param clientInfo
	 */
	public String queryContactInfo(QueryCorporationInfo clientInfo){
		
		StringBuffer sql = new StringBuffer();
		sql.append("select c.code clientCode,c.name clientName,c.country,o.clientid,o.province,o.city");
		sql.append(" from client_clientinfo c,client_corporationinfo o");
		sql.append(" where c.id = o.clientid(+)");
		sql.append(" and c.statusid ="+Constant.RecordStatus.VALID);
		sql.append(" and c.clientbasetype = '1'");
		sql.append(" and c.officeid = "+clientInfo.getOfficeID());
		if(clientInfo.getLClientNOStart()!=null && !clientInfo.getLClientNOStart().equals("")){
			sql.append(" and c.code >= '"+clientInfo.getLClientNOStart()+"'");
		}
		if(clientInfo.getLClientNOEnd()!=null && !clientInfo.getLClientNOEnd().equals("")){
			sql.append(" and c.code <= '"+clientInfo.getLClientNOEnd()+"'");
		}
		
		return sql.toString();
	}
	/**
	 * 对外投资信息分页查询SQL
	 * @param clientInfo
	 */
	public String queryForeignInvestmentInfo(QueryCorporationInfo clientInfo){
		
		StringBuffer sql = new StringBuffer();
		sql.append("select a.id,a.code clientCode,a.name clientName,b.clientid,b.partnerid,b.partnername,b.stockCharacter,b.contributiveAmount,b.contributivecurrency,b.stockway ");
		sql.append(" from (select * from client_clientinfo where statusid = 1 and clientbasetype = '1' and officeid ="+clientInfo.getOfficeID());
		if(clientInfo.getLClientNOStart()!=null && !clientInfo.getLClientNOStart().equals("")){
			sql.append(" and code >='"+clientInfo.getLClientNOStart()+"'");	
		}
		if(clientInfo.getLClientNOEnd()!=null && !clientInfo.getLClientNOEnd().equals("")){
			sql.append(" and code <= '"+clientInfo.getLClientNOEnd()+"'");
			
		}
		sql.append(" ) a, ");
		sql.append(" (select c.name partnername, f.* from client_clientinfo c, client_investofsubsidiary f where c.id(+) = f.partnerid and f.statusid = "+Constant.RecordStatus.VALID +" and f.partnertype=1 ");
		sql.append(" union ");
		sql.append(" select c.name partnername, f.* from client_extclientinfo c, client_investofsubsidiary f where c.id(+) = f.partnerid and f.statusid = "+Constant.RecordStatus.VALID +" and f.partnertype = 2) b ");
		sql.append(" where a.id = b.clientid(+)");
		
		return sql.toString();
	}
	/**
	 * 获得编号为clientID的企业的所有企业投资信息分页查询SQL
	 * @param clientID
	 */
	public String queryForeignInvestmentInfoByClientID(long clientID){
		
		StringBuffer sql = new StringBuffer();
		sql.append("select c.name partnername,f.* ");
		sql.append(" from client_clientinfo c, client_InvestOfSubsidiary f ");
		sql.append(" where c.id(+) = f.partnerid and f.ClientID = "+clientID+" and (f.statusid is null or f.statusid <> 0) and f.partnertype=1 ");
		sql.append(" union ");
		sql.append(" select c.name partnername, f.* ");
		sql.append(" from client_extclientinfo c, client_InvestOfSubsidiary f ");
		sql.append(" where c.id(+) = f.partnerid ");
		sql.append(" and f.ClientID = "+clientID+" and (f.statusid is null or f.statusid <> 0) and f.partnertype=2 ");
		
		return sql.toString();
	}
	/**
	 * 财务报表分页查询SQL
	 * @param clientInfo
	 */
	public String queryFinanceReportInfo(QueryCorporationInfo clientInfo){
		
		StringBuffer sql = new StringBuffer();
		sql.append("select c.id clientID,r.id reportID,c.code clientCode,c.name clientName,r.reportname reportName,r.reporttype reportTypeID,r.reportdate reportDate,r.sdocpath sDocPath ");
		sql.append(" from (select * from client_clientinfo where statusid = 1 and clientbasetype = '1' and officeid ="+clientInfo.getOfficeID());
		if(clientInfo.getLClientNOStart()!=null && !clientInfo.getLClientNOStart().equals("")){
			sql.append(" and code >='"+clientInfo.getLClientNOStart()+"'");	
		}
		if(clientInfo.getLClientNOEnd()!=null && !clientInfo.getLClientNOEnd().equals("")){
			sql.append(" and code <= '"+clientInfo.getLClientNOEnd()+"'");
			
		}
		sql.append(" ) c, ");
		sql.append(" (select * from client_uploadreport r where r.nstatusid=1) r ");
		sql.append(" where c.id=r.clientid(+)");
		
		return sql.toString();
	}
	/**
	 * 通过客户id查询财务报表相关信息分页查询SQL
	 * @param clientID,officeID
	 */
	public String queryFinanceReportByID(long clientID,long officeID){
		
		StringBuffer sql = new StringBuffer();
		sql.append("select c.id clientID,r.id reportID,r.reportname reportName,r.reporttype reportType,r.reportdate reportDate,r.sdocpath sDocPath ");
		sql.append(" from client_clientinfo c,client_uploadreport r ");
		sql.append(" where c.id = r.clientid(+) and c.clientbasetype = '1' and r.nstatusid = 1 and c.statusid = 1 ");
		sql.append(" and c.officeid ="+officeID+" and c.id ="+clientID);
		
		return sql.toString();
	}
	/**
	 * 附件信息分页查询SQL
	 * @param clientInfo
	 */
	public String queryAnnexInfo(QueryCorporationInfo clientInfo){
		
		StringBuffer sql = new StringBuffer();
		sql.append("select a.id,a.code clientCode,a.name clientName,b.nfileid,( select fileInfo.Sclientfilename from fileInfo where fileInfo.Id=b.nfileid and fileInfo.nstatus = 1 ) sclientfilename, ");
		sql.append(" (select distinct client_corporationinfo.signstart from client_corporationinfo where client_corporationinfo.clientid=a.id) signstart ");
		sql.append(" from ( select * from client_clientinfo where statusid = 1 and clientbasetype = '1' and officeid ="+clientInfo.getOfficeID());
		if(clientInfo.getLClientNOStart()!=null && !clientInfo.getLClientNOStart().equals("")){
			sql.append(" and code >= '"+clientInfo.getLClientNOStart()+"'");
		}
		if(clientInfo.getLClientNOEnd()!=null && !clientInfo.getLClientNOEnd().equals("")){
			sql.append(" and code <= '"+clientInfo.getLClientNOEnd()+"'");
		}
		sql.append(" ) a, ");
		sql.append(" ( select * from loan_docInfo where loan_docInfo.Nstatusid = 1 ) b ");
		sql.append(" where a.id=b.clientid(+) and a.officeid = "+clientInfo.getOfficeID()+" and a.clientbasetype = '1'");
		
		return sql.toString();
	}
	/**
	 * 通过客户id查询附件信息分页查询SQL
	 * @param clientID,officeID
	 */
	public String queryAnnexInfoByClientID(long clientID,long officeID){
		
		StringBuffer sql = new StringBuffer();
		sql.append("select a.id as docID,a.clientid as aClientID,a.contractid as aContractID,a.payformid as aPayformID,a.archivestypeid as aArchivestypeID,b.* ");
		sql.append(" from loan_docInfo a,fileInfo b ");
		sql.append(" where a.nFileID = b.id and a.officeid = " + officeID);
		sql.append(" and a.nStatusID = " + Constant.RecordStatus.VALID + " and a.clientid = " + clientID);
		
		return sql.toString();
	}
   
}