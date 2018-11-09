/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author              沈志荣
 * @version
 * Date of Creation     2004-04-5
 */
package com.iss.itreasury.securities.setting.dao; 

import java.util.*;
import java.util.Collection;   
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.securities.util.SECConstant;
import com.iss.itreasury.securities.util.SecuritiesDAO;
import com.iss.itreasury.securities.setting.dataentity.SecuritiesInfo;

public class SEC_SecuritiesDAO extends SecuritiesDAO {
	public SEC_SecuritiesDAO() {
		super("SEC_Securities");
	} 
	
	public SEC_SecuritiesDAO(Connection conn) {
		super("SEC_Securities"); 
	}

	
	
	/**
	 * 从表中取最大的备注编号
	 * @param  nothing
	 * @return String
	 * @exception throws ITreasuryDAOException
	 */
	
	public String getMaxCode() throws ITreasuryDAOException {
		String sReturn = "";
		PreparedStatement localPS = null;
		ResultSet localRS = null;

		this.initDAO();
		StringBuffer bufSQL = new StringBuffer();
		bufSQL.append("SELECT LPAD((NVL(MAX(SECURITIESCODE3),0)+1),5,'0') maxCode FROM  SEC_SECURITIES \n");
		log.info("SQL="+bufSQL.toString());
		try {//内部维护RS、PS、CONN，否则将会产生冲突
			localPS = transConn.prepareStatement(bufSQL.toString());
			localRS = localPS.executeQuery();
			while (localRS.next()) {
				sReturn = localRS.getString("maxCode");
			}
			if(localRS != null) {
				localRS.close();
				localRS = null;
			}
			if(localPS != null) {
				localPS.close();
				localPS = null;
			}
		} catch (Exception e) {
			new ITreasuryDAOException("数据库获取最大的备注编号产生异常",e);
		} finally {
		}
		this.finalizeDAO();
		return sReturn==null || sReturn==""?"":sReturn;
	}

	/**
	 * 从根据证券简称、证券类型、交易场所、用户号取得证券资料信息数据
	 * @param  inputUserID:long 用户号 //reserved
	 * @param typeId:long 证券类型
	 * @param securitiesName:String 证券名称
	 * @param exchangeCenterID:long 交易市场
	 * @return Collection
	 * @exception throws ITreasuryDAOException
	 */
	public Collection findBySecuritiesName(long inputUserID,
			long typeId,
			String shortName,
			long exchangeCenterID
			) throws ITreasuryDAOException {

		ArrayList list = new ArrayList();
		PreparedStatement localPS = null;
		ResultSet localRS = null;

		this.initDAO();
		StringBuffer bufSQL = new StringBuffer();
		
		bufSQL.append("SELECT \n");
		bufSQL.append("	NVL(ID,-1) ID, \n");
		bufSQL.append("	NVL(SECURITIESCODE1,'') CODE1, \n");
		bufSQL.append("	NVL(SECURITIESCODE2,'') CODE2, \n");
		bufSQL.append("	NVL(SECURITIESCODE3,'') CODE3, \n");			
		bufSQL.append("	NVL(SHORTNAME,'') SHORTNAME, \n");
		bufSQL.append("	NVL(TYPEID,-1) TYPEID, \n");
		bufSQL.append("	NVL(EXCHANGECENTERID,-1) EXCHANGECENTERID, \n");
		bufSQL.append(" NVL(PROMOTOR,'') PROMOTOR,\n ");
		bufSQL.append(" NVL(INTERESTRATE,0) INTERESTRATE,\n ");
		bufSQL.append(" NVL(TERM,0) TERM,\n ");
		bufSQL.append(" NVL(ISSUESTARTDATE,'') ISSUESTARTDATE,\n ");
		bufSQL.append(" NVL(LISTINGDATE,'') LISTINGDATE, \n");
		bufSQL.append(" NVL(VALUEDATE,'') VALUEDATE,\n ");
		bufSQL.append(" NVL(REDEEMPRICE,0.0) REDEEMPRICE,\n ");
		bufSQL.append(" NVL(BUYBACKPRICE,0.0) BUYBACKPRICE,\n ");
		bufSQL.append(" NVL(PLEDGERATE,0.0) PLEDGERATE,\n ");
		bufSQL.append(" NVL(INITIALTRANSFERSPRICE,0.0) INITIALTRANSFERSPRICE,\n ");	
		bufSQL.append(" NVL(AMENDMENTCLAUSE,'') AMENDMENTCLAUSE, \n ");
		bufSQL.append(" NVL(INTERESTCYCLE,0) INTERESTCYCLE, \n ");
		bufSQL.append(" NVL(TRANSFERSTERM,'') TRANSFERSTERM, \n ");
		bufSQL.append(" NVL(FUNDMANAGER,-1) FUNDMANAGER, \n ");
		bufSQL.append(" DECODE(QUANTITYISSUED,-1,'',NVL(QUANTITYISSUED,0)) QUANTITYISSUED, \n ");
		bufSQL.append(" NVL(QUANTITYCIRCULATION,0) QUANTITYCIRCULATION, \n ");
		bufSQL.append(" NVL(CIRCULATIONSITUATION,'') CIRCULATIONSITUATION, \n ");
		bufSQL.append(" NVL(ISSUEWAY,'') ISSUEWAY, \n ");
		bufSQL.append(" NVL(REMARK,'') REMARK \n ");
		bufSQL.append(" FROM SEC_SECURITIES  \n ");
		bufSQL.append(" WHERE ID > 0 \n");
		//bufSQL.append(inputUserID+" \n");
		bufSQL.append("	AND StatusID = "+SECConstant.SecuritiesStatus.CHECKED+" \n");
		bufSQL.append("	AND TYPEID = "+typeId+" \n");
		if(shortName==null||shortName.trim().equals("")){}
		else
		{
		  bufSQL.append(" AND SHORTNAME = '"+shortName+"' \n");	
		}
		if(exchangeCenterID==-1){}
		else
		{
		  bufSQL.append(" AND EXCHANGECENTERID = "+exchangeCenterID+" \n");	
		}
		bufSQL.append("ORDER BY ID \n");
		
		
		log.info("SQL="+bufSQL.toString());
		try {
			localPS = transConn.prepareStatement(bufSQL.toString());
			localRS = localPS.executeQuery();
			while (localRS.next()) {
				SecuritiesInfo info = new SecuritiesInfo();
				info.setId(localRS.getLong("ID"));						                    //自增,唯一不空
				info.setSecuritiesCode1(localRS.getString("CODE1"));                        //代码（首发）
				info.setSecuritiesCode2(localRS.getString("CODE2"));			        	//代码（二级）
				info.setSecuritiesCode3(localRS.getString("CODE3"));			        	//代码（新增）
				
				info.setRemark(localRS.getString("REMARK"));                            	//备注描述
				info.setShortName(localRS.getString("SHORTNAME"));	                        //证券名称简称
				
				info.setTypeID(localRS.getLong("TYPEID"));	                                //业务类型
                info.setExchangeCenterID(localRS.getLong("EXCHANGECENTERID"));	        	//交易场所
				
				info.setPromotor(localRS.getString("PROMOTOR"));                            //发起人
				info.setInterestRate(localRS.getDouble("INTERESTRATE"));			        //票面利率
		
				info.setTerm(localRS.getLong("TERM"));	                                    //期限
				info.setIssueStartDate(localRS.getTimestamp("ISSUESTARTDATE"));	            //发行日
				
				info.setListingDate(localRS.getTimestamp("LISTINGDATE"));	                //上市日
				info.setInterestcycle(localRS.getString("INTERESTCYCLE"));	                //付息频率
				info.setValueDate(localRS.getTimestamp("VALUEDATE"));                       //起息日
				info.setRedeemPrice(localRS.getDouble("REDEEMPRICE"));                      //赎回价格 
				info.setPledgeRate(localRS.getDouble("PLEDGERATE"));                       //折成标准券比率
				info.setBuyBackPrice(localRS.getDouble("BUYBACKPRICE"));                    //回购价格
				info.setInitialTransfersPrice(localRS.getDouble("INITIALTRANSFERSPRICE"));  //初始转股价格
				info.setAmendmentClause(localRS.getString("AMENDMENTCLAUSE"));              //修整条款
				info.setTransfersTerm(localRS.getString("TRANSFERSTERM"));                  //转股期限
				info.setFundManager(localRS.getLong("FUNDMANAGER"));                      //基金管理公司
				info.setQuantityIssued(localRS.getLong("QUANTITYISSUED"));                  //发行量
				info.setQuantityCirculation(localRS.getLong("QUANTITYCIRCULATION"));        //流通总量
				info.setCirculationSituation(localRS.getString("CIRCULATIONSITUATION"));    //流通情况
				info.setIssueWay(localRS.getString("ISSUEWAY"));                            //发行方式
				list.add(info);
			}
		} catch (Exception e) {
			new ITreasuryDAOException("数据库获取证券资料数据产生异常",e);
		}
		this.finalizeDAO();

		return list;
	}
	
	
	/**
	 * 从根据证券类型取得证券资料
	 * @param  inputUserID:long 用户号
	 * @param typeId:long 证券类型
	 * @return Collection
	 * @exception throws ITreasuryDAOException
	 */
	public Collection find(long inputUserID,long typeId) throws ITreasuryDAOException {

		ArrayList list = new ArrayList();
		PreparedStatement localPS = null;
		ResultSet localRS = null;

		this.initDAO(); 
		StringBuffer bufSQL = new StringBuffer();
		
			bufSQL.append("SELECT \n");
			bufSQL.append("	NVL(ID,-1) ID, \n");
			bufSQL.append("	NVL(SECURITIESCODE1,'') CODE1, \n");
			bufSQL.append("	NVL(SECURITIESCODE2,'') CODE2, \n");
			bufSQL.append("	NVL(SECURITIESCODE3,'') CODE3, \n");			
			bufSQL.append("	NVL(SHORTNAME,'') SHORTNAME, \n");
			bufSQL.append("	NVL(TYPEID,-1) TYPEID, \n");
			bufSQL.append("	NVL(EXCHANGECENTERID,-1) EXCHANGECENTERID, \n");
			bufSQL.append(" NVL(PROMOTOR,'') PROMOTOR,\n ");
			bufSQL.append(" NVL(INTERESTRATE,0) INTERESTRATE,\n ");
			bufSQL.append(" NVL(TERM,0) TERM,\n ");
			bufSQL.append(" NVL(DRAWNRATE,0.0) DRAWNRATE,\n ");
			bufSQL.append(" NVL(ISSUESTARTDATE,'') ISSUESTARTDATE,\n ");
			bufSQL.append(" NVL(LISTINGDATE,'') LISTINGDATE, \n");
			bufSQL.append(" NVL(VALUEDATE,'') VALUEDATE,\n ");
			bufSQL.append(" NVL(REDEEMPRICE,0.0) REDEEMPRICE,\n ");
			bufSQL.append(" NVL(BUYBACKPRICE,0.0) BUYBACKPRICE,\n ");
			bufSQL.append(" NVL(PLEDGERATE,0.0) PLEDGERATE,\n ");
			bufSQL.append(" NVL(INITIALTRANSFERSPRICE,0.0) INITIALTRANSFERSPRICE,\n ");	
			bufSQL.append(" NVL(AMENDMENTCLAUSE,'') AMENDMENTCLAUSE, \n ");			
			bufSQL.append(" NVL(INTERESTCYCLE,0) INTERESTCYCLE, \n ");
			bufSQL.append(" NVL(TRANSFERSTERM,'') TRANSFERSTERM, \n ");
			bufSQL.append(" NVL(FUNDMANAGER,-1) FUNDMANAGER, \n ");
			bufSQL.append(" DECODE(QUANTITYISSUED,-1,'',NVL(QUANTITYISSUED,0)) QUANTITYISSUED, \n ");
			bufSQL.append(" NVL(QUANTITYCIRCULATION,0) QUANTITYCIRCULATION, \n ");
			bufSQL.append(" NVL(CIRCULATIONSITUATION,'') CIRCULATIONSITUATION, \n ");
			bufSQL.append(" NVL(ISSUEWAY,'') ISSUEWAY, \n ");
			bufSQL.append(" NVL(REMARK,'') REMARK \n ");
			bufSQL.append(" FROM SEC_SECURITIES  \n");
			bufSQL.append(" WHERE ID > 0 \n");
			//bufSQL.append(inputUserID+" \n");
			bufSQL.append("	AND StatusID = "+SECConstant.SecuritiesStatus.CHECKED+" \n");
			bufSQL.append("	AND TYPEID = "+typeId+" \n");
			bufSQL.append("ORDER BY ID \n");
		
		
		log.info("SQL="+bufSQL.toString());
		try {
			localPS = transConn.prepareStatement(bufSQL.toString());
			localRS = localPS.executeQuery();
			while (localRS.next()) {
				SecuritiesInfo info = new SecuritiesInfo();
				//ID	NOT NULL	NUMBER
				info.setId(localRS.getLong("ID"));						
				//CODE		VARCHAR2(5)
				info.setSecuritiesCode1(localRS.getString("CODE1"));
				info.setSecuritiesCode2(localRS.getString("CODE2"));				
				info.setSecuritiesCode3(localRS.getString("CODE3"));				
				//DESCRIPTION		VARCHAR2(250)
				info.setRemark(localRS.getString("REMARK"));	                                   //备注描述
				info.setShortName(localRS.getString("SHORTNAME"));	                               //名称
				//BUSINESSTYPE		NUMBER
				info.setTypeID(localRS.getLong("TYPEID"));	                                       //业务类型
                info.setExchangeCenterID(localRS.getLong("EXCHANGECENTERID"));		

				//STATUSID		NUMBER
				info.setPromotor(localRS.getString("PROMOTOR"));
				info.setInterestRate(localRS.getDouble("INTERESTRATE"));			 
				//INPUTUSERID		NUMBER
				info.setTerm(localRS.getDouble("TERM"));
				info.setDrawnRate(localRS.getDouble("DRAWNRATE"));
				//INPUTDATE		DATE
				info.setIssueStartDate(localRS.getTimestamp("ISSUESTARTDATE"));	
				//UPDATEUSERID		NUMBER
				info.setListingDate(localRS.getTimestamp("LISTINGDATE"));	 
				//UPDATEDATE		DATE
				info.setInterestcycle(localRS.getString("INTERESTCYCLE"));	 
				info.setValueDate(localRS.getTimestamp("VALUEDATE"));  
				info.setRedeemPrice(localRS.getDouble("REDEEMPRICE"));
				info.setPledgeRate(localRS.getDouble("PLEDGERATE"));                       //折成标准券比率
				info.setBuyBackPrice(localRS.getDouble("BUYBACKPRICE"));
				info.setInitialTransfersPrice(localRS.getDouble("INITIALTRANSFERSPRICE"));
				info.setAmendmentClause(localRS.getString("AMENDMENTCLAUSE"));
				info.setTransfersTerm(localRS.getString("TRANSFERSTERM"));
				info.setFundManager(localRS.getLong("FUNDMANAGER"));
				info.setQuantityIssued(localRS.getLong("QUANTITYISSUED"));
				info.setQuantityCirculation(localRS.getLong("QUANTITYCIRCULATION"));
				info.setCirculationSituation(localRS.getString("CIRCULATIONSITUATION"));    //流通情况

				info.setIssueWay(localRS.getString("ISSUEWAY"));
				list.add(info);
			}
		} catch (Exception e) {
			new ITreasuryDAOException("数据库获取证券资料数据产生异常",e);
		}
		this.finalizeDAO();

		return list;
	}
	
	/**
	 * 根据证券类别、票面利率、名称、交易场所、起息日、起息结束日取得证券资料
	 * @param  inputUserID:long 用户号
	 * @param typeId:long 证券类型
	 * @param shortName:String 证券简称
	 * @param exchangeCenterID: long 交易场所
	 * @param interestRate:double 票面利率
	 * @param valueDate  :Timestamp 起息开始日
	 * @param maturityDate: Timestamp 起息结束日
	 * @return Collection
	 * @exception throws ITreasuryDAOException
	 */
	public Collection findByInterestRateValueDateMaturityDate(long inputUserID,
			long typeId,
			String shortName,
			long exchangeCenterID,
			double interestRate,
			Timestamp valueDate,
			Timestamp maturityDate) throws ITreasuryDAOException {

		ArrayList list = new ArrayList();
		PreparedStatement localPS = null;
		ResultSet localRS = null;

		this.initDAO();
		StringBuffer bufSQL = new StringBuffer();
		
			bufSQL.append("SELECT \n");
			bufSQL.append("	NVL(ID,-1) ID, \n");
			bufSQL.append("	NVL(SECURITIESCODE1,'') CODE1, \n");
			bufSQL.append("	NVL(SECURITIESCODE2,'') CODE2, \n");
			bufSQL.append("	NVL(SECURITIESCODE3,'') CODE3, \n");			
			bufSQL.append("	NVL(SHORTNAME,'') SHORTNAME, \n");
			bufSQL.append("	NVL(TYPEID,-1) TYPEID, \n");
			bufSQL.append("	NVL(EXCHANGECENTERID,-1) EXCHANGECENTERID, \n");
			bufSQL.append(" NVL(PROMOTOR,'') PROMOTOR,\n ");
			bufSQL.append(" NVL(INTERESTRATE,0) INTERESTRATE,\n ");
			bufSQL.append(" NVL(TERM,0) TERM,\n ");
			bufSQL.append(" NVL(ISSUESTARTDATE,'') ISSUESTARTDATE,\n ");
			bufSQL.append(" NVL(LISTINGDATE,'') LISTINGDATE, \n");
			bufSQL.append(" NVL(INTERESTCYCLE,0) INTERESTCYCLE, \n ");
			bufSQL.append(" NVL(VALUEDATE,'') VALUEDATE,\n ");
			bufSQL.append(" NVL(REDEEMPRICE,0.0) REDEEMPRICE,\n ");
			bufSQL.append(" NVL(BUYBACKPRICE,0.0) BUYBACKPRICE,\n ");
			bufSQL.append(" NVL(PLEDGERATE,0.0) PLEDGERATE,\n ");
			bufSQL.append(" NVL(INITIALTRANSFERSPRICE,0.0) INITIALTRANSFERSPRICE,\n ");	
			bufSQL.append(" NVL(AMENDMENTCLAUSE,'') AMENDMENTCLAUSE, \n ");	
			bufSQL.append(" NVL(TRANSFERSTERM,'') TRANSFERSTERM, \n ");
			bufSQL.append(" NVL(FUNDMANAGER,-1) FUNDMANAGER, \n ");
			bufSQL.append(" DECODE(QUANTITYISSUED,-1,'',NVL(QUANTITYISSUED,0)) QUANTITYISSUED, \n ");
			bufSQL.append(" NVL(QUANTITYCIRCULATION,0) QUANTITYCIRCULATION, \n ");
			bufSQL.append(" NVL(CIRCULATIONSITUATION,'') CIRCULATIONSITUATION, \n ");
			bufSQL.append(" NVL(ISSUEWAY,'') ISSUEWAY, \n ");
			bufSQL.append(" NVL(DRAWNRATE,'') DRAWNRATE, \n ");
			
			bufSQL.append(" NVL(REMARK,'') REMARK \n ");
			bufSQL.append(" FROM SEC_SECURITIES  \n");
			bufSQL.append(" WHERE ID > 0 \n");
			//bufSQL.append(inputUserID+" \n");
			bufSQL.append("	AND StatusID = "+SECConstant.SecuritiesStatus.CHECKED+" \n");
			bufSQL.append("	AND TYPEID = "+typeId+" \n");
			if(interestRate==0.0){}
			else
			{
				bufSQL.append(" AND INTERESTRATE = "+interestRate+" \n");	
			}
			if(shortName==null||shortName.trim().equals("")){}
			else
			{
			  bufSQL.append(" AND SHORTNAME = '"+shortName+"' \n");	
			}
			if(exchangeCenterID==-1){}
			else
			{
			  bufSQL.append(" AND EXCHANGECENTERID = "+exchangeCenterID+" \n");	
			}
			if(valueDate==null){}
			else
			{
			  bufSQL.append(" AND TO_CHAR(VALUEDATE,'yyyy-mm-dd') ='"+valueDate.toString().substring(0,10)+"' \n");
			}
			
			if(maturityDate==null){}
			else
			{
			   bufSQL.append(" AND TO_CHAR(MATURITYDATE,'yyyy-mm-dd') ='"+maturityDate.toString().substring(0,10)+"' \n");	
			}
											
			bufSQL.append("ORDER BY ID \n");
		
		
		log.info("SQL="+bufSQL.toString());
		try {
			localPS = transConn.prepareStatement(bufSQL.toString());
			localRS = localPS.executeQuery();
			while (localRS.next()) {
				SecuritiesInfo info = new SecuritiesInfo();
				
				info.setId(localRS.getLong("ID"));						 
				
				info.setSecuritiesCode1(localRS.getString("CODE1"));
				info.setSecuritiesCode2(localRS.getString("CODE2"));				
				info.setSecuritiesCode3(localRS.getString("CODE3"));				 
				
				info.setRemark(localRS.getString("REMARK"));	 
				info.setShortName(localRS.getString("SHORTNAME"));	 
		
				info.setTypeID(localRS.getLong("TYPEID"));	 
                info.setExchangeCenterID(localRS.getLong("EXCHANGECENTERID"));		

				
				info.setPromotor(localRS.getString("PROMOTOR"));
				info.setInterestRate(localRS.getDouble("INTERESTRATE"));			
				
				info.setTerm(localRS.getLong("TERM"));	
				
				info.setIssueStartDate(localRS.getTimestamp("ISSUESTARTDATE")); 
				
				info.setListingDate(localRS.getTimestamp("LISTINGDATE"));	 
				
				info.setInterestcycle(localRS.getString("INTERESTCYCLE"));	 
				info.setValueDate(localRS.getTimestamp("VALUEDATE"));  
				info.setRedeemPrice(localRS.getDouble("REDEEMPRICE"));
				info.setBuyBackPrice(localRS.getDouble("BUYBACKPRICE"));
				info.setPledgeRate(localRS.getDouble("PLEDGERATE"));                       //折成标准券比率
				info.setInitialTransfersPrice(localRS.getDouble("INITIALTRANSFERSPRICE"));
				info.setAmendmentClause(localRS.getString("AMENDMENTCLAUSE"));
				info.setTransfersTerm(localRS.getString("TRANSFERSTERM"));
				info.setFundManager(localRS.getLong("FUNDMANAGER"));
				info.setQuantityIssued(localRS.getLong("QUANTITYISSUED"));
				info.setQuantityCirculation(localRS.getLong("QUANTITYCIRCULATION"));
				info.setCirculationSituation(localRS.getString("CIRCULATIONSITUATION"));    //流通情况
				info.setIssueWay(localRS.getString("ISSUEWAY"));
				list.add(info);
			}
		} catch (Exception e) {
			new ITreasuryDAOException("数据库获取证券资料数据产生异常",e);
		}
		this.finalizeDAO();

		return list;
	}
	/**
	 * 根据证券类别、票面利率、名称、交易场所、起息日、起息结束日取得证券资料
	 * @param  inputUserID:long 用户号
	 * @param typeId:long 证券类型
	 * @param shortName:String 证券简称
	 * @param exchangeCenterID: long 交易场所
	 * @param interestRate:double 票面利率
	 * @param valueDate  :Timestamp 起息开始日
	 * @param maturityDate: Timestamp 起息结束日
	 * @return Collection
	 * @exception throws ITreasuryDAOException
	 */
	
	public Collection findByFundManager(long inputUserID,
			long typeId,
			String shortName,
			long exchangeCenterID,
			String fundManager			
			) throws ITreasuryDAOException {

		ArrayList list = new ArrayList();
		PreparedStatement localPS = null;
		ResultSet localRS = null;

		this.initDAO();
		StringBuffer bufSQL = new StringBuffer();
		
		bufSQL.append("SELECT \n");
		bufSQL.append("	NVL(ID,-1) ID, \n");
		bufSQL.append("	NVL(SECURITIESCODE1,'') CODE1, \n");
		bufSQL.append("	NVL(SECURITIESCODE2,'') CODE2, \n");
		bufSQL.append("	NVL(SECURITIESCODE3,'') CODE3, \n");			
		bufSQL.append("	NVL(SHORTNAME,'') SHORTNAME, \n");
		bufSQL.append("	NVL(TYPEID,-1) TYPEID, \n");
		bufSQL.append("	NVL(EXCHANGECENTERID,-1) EXCHANGECENTERID, \n");
		bufSQL.append(" NVL(PROMOTOR,'') PROMOTOR,\n ");
		bufSQL.append(" NVL(INTERESTRATE,0) INTERESTRATE,\n ");
		bufSQL.append(" NVL(TERM,0) TERM,\n ");
		bufSQL.append(" NVL(ISSUESTARTDATE,'') ISSUESTARTDATE,\n ");
		bufSQL.append(" NVL(LISTINGDATE,'') LISTINGDATE, \n");
		bufSQL.append(" NVL(INTERESTCYCLE,0) INTERESTCYCLE, \n ");
		bufSQL.append(" NVL(VALUEDATE,'') VALUEDATE,\n ");
		bufSQL.append(" NVL(REDEEMPRICE,0.0) REDEEMPRICE,\n ");
		bufSQL.append(" NVL(BUYBACKPRICE,0.0) BUYBACKPRICE,\n ");
		bufSQL.append(" NVL(PLEDGERATE,0.0) PLEDGERATE,\n ");
		bufSQL.append(" NVL(INITIALTRANSFERSPRICE,0.0) INITIALTRANSFERSPRICE,\n ");	
		bufSQL.append(" NVL(AMENDMENTCLAUSE,'') AMENDMENTCLAUSE, \n ");
		bufSQL.append(" NVL(TRANSFERSTERM,'') TRANSFERSTERM, \n ");
		bufSQL.append(" NVL(REMARK,'') REMARK, \n ");
		bufSQL.append(" NVL(FUNDMANAGER,-1) FUNDMANAGER, \n ");
		bufSQL.append(" DECODE(QUANTITYISSUED,-1,'',NVL(QUANTITYISSUED,0)) QUANTITYISSUED, \n ");
		bufSQL.append(" NVL(QUANTITYCIRCULATION,0) QUANTITYCIRCULATION, \n ");
		bufSQL.append(" NVL(CIRCULATIONSITUATION,'') CIRCULATIONSITUATION, \n ");
		bufSQL.append(" NVL(ISSUEWAY,'') ISSUEWAY \n ");
		bufSQL.append(" FROM SEC_SECURITIES  \n");
		bufSQL.append(" WHERE ID > 0 \n");
		//bufSQL.append(inputUserID+" \n");
		bufSQL.append("	AND StatusID = "+SECConstant.SecuritiesStatus.CHECKED+" \n");
		bufSQL.append("	AND TYPEID = "+typeId+" \n");
		if(fundManager==null||fundManager.trim().equals("-1")){}
		else
		{
		  bufSQL.append(" AND FUNDMANAGER ="+fundManager+" \n");
		}
		if(shortName==null||shortName.trim().equals("")){}
		else
		{
		  bufSQL.append(" AND SHORTNAME = '"+shortName+"' \n");	
		}
		if(exchangeCenterID==-1){}
		else
		{
		  bufSQL.append(" AND EXCHANGECENTERID = "+exchangeCenterID+" \n");	
		}
		
		bufSQL.append("ORDER BY ID \n");
		
		
		log.info("SQL="+bufSQL.toString());
		try {
			localPS = transConn.prepareStatement(bufSQL.toString());
			localRS = localPS.executeQuery();
			while (localRS.next()) {
				SecuritiesInfo info = new SecuritiesInfo();
				//ID	NOT NULL	NUMBER
				info.setId(localRS.getLong("ID"));						 
				//CODE		VARCHAR2(5)
				info.setSecuritiesCode1(localRS.getString("CODE1"));
				info.setSecuritiesCode2(localRS.getString("CODE2"));				
				info.setSecuritiesCode3(localRS.getString("CODE3"));				 
				//DESCRIPTION		VARCHAR2(250)
				info.setRemark(localRS.getString("REMARK"));	 
				info.setShortName(localRS.getString("SHORTNAME"));	 
				//BUSINESSTYPE		NUMBER
				info.setTypeID(localRS.getLong("TYPEID")); 
                info.setExchangeCenterID(localRS.getLong("EXCHANGECENTERID"));		

				//STATUSID		NUMBER
				info.setPromotor(localRS.getString("PROMOTOR"));
				info.setInterestRate(localRS.getDouble("INTERESTRATE"));			 
				//INPUTUSERID		NUMBER
				info.setTerm(localRS.getLong("TERM"));	
				//INPUTDATE		DATE
				info.setIssueStartDate(localRS.getTimestamp("ISSUESTARTDATE"));	 
				//UPDATEUSERID		NUMBER
				info.setListingDate(localRS.getTimestamp("LISTINGDATE"));	 
				//UPDATEDATE		DATE
				info.setInterestcycle(localRS.getString("INTERESTCYCLE"));	 
				info.setValueDate(localRS.getTimestamp("VALUEDATE"));  
				info.setRedeemPrice(localRS.getDouble("REDEEMPRICE"));
				info.setBuyBackPrice(localRS.getDouble("BUYBACKPRICE"));
				info.setPledgeRate(localRS.getDouble("PLEDGERATE"));                       //折成标准券比率
				info.setInitialTransfersPrice(localRS.getDouble("INITIALTRANSFERSPRICE"));
				info.setAmendmentClause(localRS.getString("AMENDMENTCLAUSE"));
				info.setTransfersTerm(localRS.getString("TRANSFERSTERM"));
				info.setFundManager(localRS.getLong("FUNDMANAGER"));
				info.setQuantityIssued(localRS.getLong("QUANTITYISSUED"));
				info.setQuantityCirculation(localRS.getLong("QUANTITYCIRCULATION"));
				info.setCirculationSituation(localRS.getString("CIRCULATIONSITUATION"));    //流通情况

				info.setIssueWay(localRS.getString("ISSUEWAY"));
				list.add(info);
			}
		} catch (Exception e) {
			new ITreasuryDAOException("数据库获取证券资料数据产生异常",e);
		}
		this.finalizeDAO();

		return list;
	}
	
    /**
     * 在申请单中验证该证券是否被使用了
     * @param securitiesId 证券ID
     * @return int 0:未被使用，非0:已经使用
     * */
	public int isSecuritiesUsed(long securitiesId)throws ITreasuryDAOException {

		PreparedStatement localPS = null;
		ResultSet localRS = null;

		this.initDAO();
		StringBuffer bufSQL = new StringBuffer();	
		 bufSQL.append(" SELECT * FROM SEC_APPLYFORM \n");
		 bufSQL.append(" WHERE SECURITIESID = "+securitiesId+" \n");
        
		 log.info(bufSQL.toString());
		 int count=0;
		try{
			
			localPS = transConn.prepareStatement(bufSQL.toString());
			localRS = localPS.executeQuery();
			while(localRS.next()){
			  count++;
			}
		
		}
		catch(Exception ex){
			new ITreasuryDAOException("数据库获取证券资料数据产生异常",ex);
		}
		
		this.finalizeDAO();

		return count;
	}
	
	/**
	 * 验证股票代码是否已经存在
	 * 
	 * **/
	
	
	public int isCodeExist(String code)throws ITreasuryDAOException 
	{
		PreparedStatement localPS = null;
		ResultSet localRS = null;
		this.initDAO();
		StringBuffer bufSQL = new StringBuffer();	
		 bufSQL.append(" SELECT * FROM SEC_SECURITIES \n");
		 bufSQL.append(" WHERE STATUSID="+SECConstant.SecuritiesStatus.CHECKED);
		 bufSQL.append(" AND ( SECURITIESCODE1 = '"+code+"' \n");
		 bufSQL.append("OR SECURITIESCODE2 ='"+code+"') \n");
	
        
		 log.info(bufSQL.toString());
		 int count=0;
		try{
			
			localPS = transConn.prepareStatement(bufSQL.toString());
			localRS = localPS.executeQuery();
			while(localRS.next()){
			  count++;
			}
		
		}
		catch(Exception ex){
			new ITreasuryDAOException("数据库获取证券资料数据产生异常",ex);
		}
		
		this.finalizeDAO();

		return count;
		
	} 
	/**
	 * 验证股票代码是否已经存在,
	 * 
	 * **/
	
	
	public int isCodeExist(String code,long id)throws ITreasuryDAOException 
	{
		PreparedStatement localPS = null;
		ResultSet localRS = null;
		this.initDAO();
		StringBuffer bufSQL = new StringBuffer();	
		 bufSQL.append(" SELECT * FROM SEC_SECURITIES \n");
		 bufSQL.append(" WHERE STATUSID="+SECConstant.SecuritiesStatus.CHECKED);
		 bufSQL.append(" AND ID <> "+id+" \n");
		 bufSQL.append(" AND ( SECURITIESCODE1 = '"+code+"' \n");
		 bufSQL.append("OR SECURITIESCODE2 ='"+code+"') \n");
	
        
		 log.info(bufSQL.toString());
		 int count=0;
		try{
			
			localPS = transConn.prepareStatement(bufSQL.toString());
			localRS = localPS.executeQuery();
			while(localRS.next()){
			  count++;
			}
		
		}
		catch(Exception ex){
			new ITreasuryDAOException("数据库获取证券资料数据产生异常",ex);
		}
		
		this.finalizeDAO();

		return count;
		
	} 
	
}
	