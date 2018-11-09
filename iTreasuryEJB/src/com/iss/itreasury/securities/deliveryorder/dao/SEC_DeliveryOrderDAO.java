package com.iss.itreasury.securities.deliveryorder.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.securities.deliveryorder.dataentity.DeliveryOrderConditionInfo;
import com.iss.itreasury.securities.deliveryorder.dataentity.DeliveryOrderInfo;
import com.iss.itreasury.securities.deliveryorder.exception.DeliveryOrderDuplicatedException;
import com.iss.itreasury.securities.deliveryorder.exception.DeliveryOrderModifiedException;
import com.iss.itreasury.securities.exception.SecuritiesDAOException;
import com.iss.itreasury.securities.exception.SecuritiesException;
import com.iss.itreasury.securities.util.SECConstant;
import com.iss.itreasury.securities.util.SecuritiesDAO;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.util.Log4j;

/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2004-2-27
 */
public class SEC_DeliveryOrderDAO extends SecuritiesDAO {
	protected Log4j log4j = new Log4j(Constant.ModuleType.SECURITIES, this);

	public SEC_DeliveryOrderDAO(){
		super("SEC_DeliveryOrder");
	}
	
	public SEC_DeliveryOrderDAO(Connection conn){
		super("SEC_DeliveryOrder",conn);
	}	
	/**
	 *	查询 指定申请单对应的 已保存交割单 的拆借金额的和 
	 *
	 */
	public long findSumDeliveryOrderByApplyFormID(long applyFormID)throws SecuritiesException{
		
			try {
				long l=0;
				initDAO();
				String strSQL="select sum(netPriceAmount) sum from Sec_Deliveryorder where 1=1 and applyformid="+applyFormID+" and statusid !=0";
				log4j.debug(strSQL);
				prepareStatement(strSQL);
				ResultSet rs=executeQuery();
				if(rs.next()){
					l=rs.getLong("sum");
				}
				finalizeDAO();
				return l;
			} catch (ITreasuryDAOException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		return 0;
	}
	/*
	 * 查找某个申请书生成的交割单集
	 * 因为一个申请书可以生成多个交割单。
	 * 
	 */
	public Collection findByApplyFormID(long applyFormID) throws SecuritiesException 
	{
		try {
			initDAO();
			
			String strSQL = "select * from SEC_DeliveryOrder where ApplyFormID = "+applyFormID;
			DeliveryOrderInfo deliveryOrderInfo = new DeliveryOrderInfo();
			 
			log4j.debug(strSQL);
			
			prepareStatement(strSQL);
			executeQuery();
	
			Collection c = getDataEntitiesFromResultSet(deliveryOrderInfo.getClass());
	
			finalizeDAO();
		    return c;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	/*
	 * 查找某个申请书生成的交割单集
	 * 因为一个申请书可以生成多个交割单。
	 * 
	 */
	public Collection findByAnyConditions(String whereSQL) throws SecuritiesException 
	{
		try {
			initDAO();
			
			String strSQL = "select * from SEC_DeliveryOrder where "+whereSQL;
			DeliveryOrderInfo deliveryOrderInfo = new DeliveryOrderInfo();
			 
			log4j.debug(strSQL);
			
			prepareStatement(strSQL);
			executeQuery();
	
			Collection c = getDataEntitiesFromResultSet(deliveryOrderInfo.getClass());
	
			finalizeDAO();
			return c;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	/**
	 * 链接查找方法
	 * @param deliveryOrderConditionInfo
	 * @return
	 * @throws SecuritiesException
	 */
	public Collection findByCondition(DeliveryOrderConditionInfo deliveryOrderConditionInfo) throws SecuritiesException 
	{
		try
		{
			initDAO();
		}
		catch (ITreasuryDAOException e)
		{
			// TODO Auto-generated catch block
			throw new SecuritiesDAOException(e);
		}
		Collection col = null;
		
		String strSQL = "select a.* , trunc(b.deliverydate-b.TransactionDate) defterm from SEC_DeliveryOrder a , SEC_DeliveryOrder b  where 1=1 and a.id=b.id \n";
		
		strSQL += "and a.currencyId = " +deliveryOrderConditionInfo.getCurrencyId() + " \n";//币种
		
		strSQL += "and a.officeId = " +deliveryOrderConditionInfo.getOfficeId() + " \n";//办事处
		
		// 回购期限
		if(validateTransactionType(deliveryOrderConditionInfo.getSecTransactionTypeId())){
			if(deliveryOrderConditionInfo.getStartTerm()>=0){
				strSQL += "and trunc(a.deliverydate-a.TransactionDate) >= "+deliveryOrderConditionInfo.getStartTerm()+" \n";
			}
			if(deliveryOrderConditionInfo.getEndTerm()>=0){
				strSQL += "and trunc(a.deliverydate-a.TransactionDate) <= "+deliveryOrderConditionInfo.getEndTerm()+" \n";
			}
		}
		
		if (deliveryOrderConditionInfo.getSecTransactionTypeId()>0){				//交易类型
			strSQL+="and a.transactionTypeId="+deliveryOrderConditionInfo.getSecTransactionTypeId() + "\n";
		}
		if (deliveryOrderConditionInfo.getSecTransactionDateFrom()!=null				//交易日期由
			&& deliveryOrderConditionInfo.getSecTransactionDateFrom().trim().length()>0){
			strSQL+="and a.TransactionDate>=to_date('"+
				deliveryOrderConditionInfo.getSecTransactionDateFrom() +
				"','yyyy-mm-dd') \n";
		}
		if (deliveryOrderConditionInfo.getSecTransactionDateTo()!=null 				//交易日期到
			&&deliveryOrderConditionInfo.getSecTransactionDateTo().trim().length()>0){
			strSQL+="and a.TransactionDate<=to_date('"+
				deliveryOrderConditionInfo.getSecTransactionDateTo() + 
				"','yyyy-mm-dd') \n";
		}
		if (deliveryOrderConditionInfo.getSecSystemTransactionCode()!=null			//系统交易编号
			&& deliveryOrderConditionInfo.getSecSystemTransactionCode().trim().length()>0){
			strSQL+="and a.SystemTransactionCode='"+deliveryOrderConditionInfo.getSecSystemTransactionCode()+"' \n";
		}
		if (deliveryOrderConditionInfo.getSecClientId()>0){							//业务单位
			strSQL+="and a.clientId="+deliveryOrderConditionInfo.getSecClientId()+" \n";
		}
		if (deliveryOrderConditionInfo.getSecCounterpartId()>0){					//交易对手
			strSQL+="and a.counterpartId="+deliveryOrderConditionInfo.getSecCounterpartId()+" \n";
		}
		if (deliveryOrderConditionInfo.getSecAmountFrom()>0){							//拆借金额(由)
			strSQL+="and a.amount>="+deliveryOrderConditionInfo.getSecAmountFrom()+" \n";
		}
		if (deliveryOrderConditionInfo.getSecAmountTo()>0){							//拆借金额(到)
			strSQL+="and a.amount<="+deliveryOrderConditionInfo.getSecAmountTo()+" \n";
		}
		
		if (deliveryOrderConditionInfo.getSecPledgeSecuritiesAmountFrom()>0){							//拆借金额(由)
			strSQL+="and a.pledgeSecuritiesAmount>="+deliveryOrderConditionInfo.getSecPledgeSecuritiesAmountFrom()+" \n";
		}
		if (deliveryOrderConditionInfo.getSecPledgeSecuritiesAmountTo()>0){								//拆借金额(到)
			strSQL+="and a.pledgeSecuritiesAmount<="+deliveryOrderConditionInfo.getSecPledgeSecuritiesAmountTo()+" \n";
		}
//----------------------股票二级增加
		if (deliveryOrderConditionInfo.getSecPriceFrom()>0){							//成交价格(由)
			strSQL+="and a.price>="+deliveryOrderConditionInfo.getSecPriceFrom()+" \n";
		}
		if (deliveryOrderConditionInfo.getSecPriceTo()>0){							//成交价格(到)
			strSQL+="and a.price<="+deliveryOrderConditionInfo.getSecPriceTo()+" \n";
		}
		if (deliveryOrderConditionInfo.getSecQuantityFrom()>0){							//成交数量(由)
			strSQL+="and a.quantity>="+deliveryOrderConditionInfo.getSecQuantityFrom()+" \n";
		}
		if (deliveryOrderConditionInfo.getSecQuantityTo()>0){							//成交数量(到)
			strSQL+="and a.quantity<="+deliveryOrderConditionInfo.getSecQuantityTo()+" \n";
		}
		if (deliveryOrderConditionInfo.getSecAccountId()>0){							//资金帐户ID
			strSQL+="and a.accountID="+deliveryOrderConditionInfo.getSecAccountId()+" \n";
		}
		if (deliveryOrderConditionInfo.getSecSecuritiesId()>0){							//证券ID
			strSQL+="and a.SecuritiesID="+deliveryOrderConditionInfo.getSecSecuritiesId()+" \n";
		}
//----------------------第三次添加
		if (deliveryOrderConditionInfo.getSecNetPriceFrom()>0){							//净价(由)
			strSQL+="and a.netprice>="+deliveryOrderConditionInfo.getSecNetPriceFrom() + " \n";
		}
		if (deliveryOrderConditionInfo.getSecNetPriceTo()>0){							//净价(到)
			strSQL+="and a.netprice>="+deliveryOrderConditionInfo.getSecNetPriceTo() + " \n";
		}

//----------------------为证券划转添加
		if (deliveryOrderConditionInfo.getSecCompanyBankId()>0){							//证券ID
			strSQL+="and a.CompanyBankId="+deliveryOrderConditionInfo.getSecCompanyBankId()+" \n";
		}
		if (deliveryOrderConditionInfo.getSecCounterpartBankId()>0){							//证券ID
			strSQL+="and a.CounterpartBankId="+deliveryOrderConditionInfo.getSecCounterpartBankId()+" \n";
		}
		if (deliveryOrderConditionInfo.getSecCounterpartAccountId()>0){							//证券ID
			strSQL+="and a.CounterpartAccountId="+deliveryOrderConditionInfo.getSecCounterpartAccountId()+" \n";
		}
		if (deliveryOrderConditionInfo.getSecOppositeSecuritiesId()>0){							//证券ID
			strSQL+="and a.OppositeSecuritiesId="+deliveryOrderConditionInfo.getSecOppositeSecuritiesId()+" \n";
		}
		//国机用
		if (deliveryOrderConditionInfo.getIsRelatedByNoticeForm() == 0){							//国机用
					strSQL+="and a.isRelatedByNoticeForm="+deliveryOrderConditionInfo.getIsRelatedByNoticeForm()+" \n";
		}
//----------------------		
		if (deliveryOrderConditionInfo.getSecDeliveryOrderStatusId()>0){				//交割单状态
			strSQL+="and a.statusId="+deliveryOrderConditionInfo.getSecDeliveryOrderStatusId() + " \n";
		}
//		else if(deliveryOrderConditionInfo.getSecDeliveryOrderStatusId()==0 || deliveryOrderConditionInfo.getSecDeliveryOrderStatusId()==-1){
//			strSQL+="and a.statusId in (" + SECConstant.DeliveryOrderStatus.TEMPSAVED + 
//					"," + SECConstant.DeliveryOrderStatus.SAVED + ") \n";
//		}
//		else{
//			strSQL+="and a.statusId=-1 \n";
//		}
		if (deliveryOrderConditionInfo.getSecInputUserId()>0){						//录入人
			strSQL+="and a.inputUserId="+deliveryOrderConditionInfo.getSecInputUserId() + " \n";
		}
		if (deliveryOrderConditionInfo.getSecCheckUserId()>0){						//复核人
			if (deliveryOrderConditionInfo.getSecDeliveryOrderStatusId() == SECConstant.TransactionStatus.SAVE){
				strSQL+="and a.inputUserId<>"+deliveryOrderConditionInfo.getSecCheckUserId() + " \n";
			}
			else if (deliveryOrderConditionInfo.getSecDeliveryOrderStatusId() == SECConstant.TransactionStatus.CHECK){
				strSQL+="and a.checkUserId="+deliveryOrderConditionInfo.getSecCheckUserId() + " \n";
			}
		}

		
		
		
		if (deliveryOrderConditionInfo.getOrderField()!=null 						//排序字段
			&& deliveryOrderConditionInfo.getOrderField().trim().length()>0){
			strSQL+="order by a."+deliveryOrderConditionInfo.getOrderField();
			
			if (deliveryOrderConditionInfo.getDesc() == SECConstant.TRUE){			//正反序
				strSQL+=" desc \n";
			}
		}
		else{
			strSQL+="order by a.id desc";
		}
		
		log4j.debug(strSQL);
		try {

			prepareStatement (strSQL);
			ResultSet rs = executeQuery ();
			Log.print("组成preparedStatement完成");
			col = getDataEntitiesFromResultSet(DeliveryOrderInfo.class);
			finalizeDAO();
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
			throw new SecuritiesDAOException(e);
		}
		return col;
	}
	
	/**
	 * 得到同业往来所有未返款的交割单
	 * @param conditionInfo
	 * @param deliveryOrderIds
	 * @return
	 * @throws SecuritiesDAOException
	 */
	public Collection getDeliveryOrderByIds(DeliveryOrderConditionInfo conditionInfo,Collection colDeliveryOrderIds,long deliveryOrderStatusId)throws SecuritiesDAOException{
		try
		{
			initDAO();

			long[] deliveryOrderIds = new long[colDeliveryOrderIds.size()];
			Object[] objTmp = colDeliveryOrderIds.toArray();
			for (int n=0;n<deliveryOrderIds.length;n++){
				deliveryOrderIds[n] = ((Long)objTmp[n]).longValue();
			}
			
			Collection colResult = null;
			String strSQL = "";
			
			String strIds = "";		//ID拼串
			if (deliveryOrderIds!=null && deliveryOrderIds.length>0){
				for (int n=0;n<deliveryOrderIds.length;n++){
					if (n==0){
						strIds += deliveryOrderIds[n];
					} 
					else{
						strIds += "," +  deliveryOrderIds[n];
					}
				}
				
				strSQL += "select * from SEC_DeliveryOrder where 1=1 \n";			   
			   //modify by xwhe 增加条件当天的拆入交割单不能被选出为当天返款 2008-02-20   
				strSQL += " and transactiondate<trunc(sysdate)";
				strSQL += "and id in (" + strIds + ") \n";
				if (deliveryOrderStatusId == 1){		//显示已复核和已使用的
					strSQL += "and statusId in (" + SECConstant.DeliveryOrderStatus.CHECKED + ",";
					strSQL += SECConstant.DeliveryOrderStatus.USED + ",";
					strSQL += SECConstant.DeliveryOrderStatus.POSTED + ") \n";
				}
				if (conditionInfo.getOrderField()!=null 						//排序字段
				&& conditionInfo.getOrderField().trim().length()>0){
					strSQL+="order by "+conditionInfo.getOrderField();
				
					if (conditionInfo.getDesc() == SECConstant.TRUE){				//正反序
						strSQL+=" desc \n";
					}
				}
				else{
					strSQL+="order by id desc";
				}
				Log.print(strSQL);
				try{
					prepareStatement (strSQL);
					ResultSet rs = executeQuery ();
					colResult = getDataEntitiesFromResultSet(DeliveryOrderInfo.class);
					finalizeDAO();
				}catch (ITreasuryDAOException e) {
				e.printStackTrace();
				throw new SecuritiesDAOException(e);
			}
			}
			
			finalizeDAO();
			
			return colResult;
		}
		catch (ITreasuryDAOException e)
		{
			// TODO Auto-generated catch block
			throw new SecuritiesDAOException(e);
		}
	}
	/**
	 * 得到所有未返款的交割单
	 * @param conditionInfo
	 * @param deliveryOrderIds
	 * @return
	 * @throws SecuritiesDAOException
	 */
	public Collection getDeliveryOrderInfosByIds(DeliveryOrderConditionInfo conditionInfo,Collection colDeliveryOrderIds,long deliveryOrderStatusId)throws SecuritiesDAOException{
		try
		{
			initDAO();

			long[] deliveryOrderIds = new long[colDeliveryOrderIds.size()];
			Object[] objTmp = colDeliveryOrderIds.toArray();
			for (int n=0;n<deliveryOrderIds.length;n++){
				deliveryOrderIds[n] = ((Long)objTmp[n]).longValue();
			}
			
			Collection colResult = null;
			StringBuffer strSQL = null;
			
			String strIds = "";		//ID拼串
			if (deliveryOrderIds!=null && deliveryOrderIds.length>0){
				for (int n=0;n<deliveryOrderIds.length;n++){
					if (n==0){
						strIds += deliveryOrderIds[n];
					} 
					else{
						strIds += "," +  deliveryOrderIds[n];
					}
				}
				
				long transTypeID = -1;
				if(conditionInfo.getSecTransactionTypeId() == SECConstant.BusinessType.STOCK_BID_ONLINE.INITIAL_OFFERING_BID_WIN){
					transTypeID = SECConstant.BusinessType.STOCK_BID_ONLINE.INITIAL_OFFERING_BID_ONLINE;
				}else if(conditionInfo.getSecTransactionTypeId() == SECConstant.BusinessType.STOCK_BID_ONLINE.PROMOTION_BID_WIN){
					transTypeID = SECConstant.BusinessType.STOCK_BID_ONLINE.PROMOTION_BID_ONLINE;
				}else if(conditionInfo.getSecTransactionTypeId() == SECConstant.BusinessType.STOCK_BID.INITIAL_OFFERING_QUOTA_CONFIRM){
					transTypeID = SECConstant.BusinessType.STOCK_BID.INITIAL_OFFERING_BID;
				}else if(conditionInfo.getSecTransactionTypeId() == SECConstant.BusinessType.STOCK_BID.PROMOTION_BID_QUOTA_CONFIRM){
					transTypeID = SECConstant.BusinessType.STOCK_BID.PROMOTION_BID;
				}else if(conditionInfo.getSecTransactionTypeId() == SECConstant.BusinessType.MUTUAL_FUND_SUBSCRIBE.FUND_SUBSCRIBE_CONFIRM){
					transTypeID = SECConstant.BusinessType.MUTUAL_FUND_SUBSCRIBE.FUND_SUBSCRIBE;
				}else if(conditionInfo.getSecTransactionTypeId() == SECConstant.BusinessType.MUTUAL_FUND_BID.FUND_BID_CONFIRM){
					transTypeID = SECConstant.BusinessType.MUTUAL_FUND_BID.FUND_BID;
				}else if(conditionInfo.getSecTransactionTypeId() == SECConstant.BusinessType.ENCLOSED_FUND_BID.FUND_BID_QUOTA_CONFIRM){
					transTypeID = SECConstant.BusinessType.ENCLOSED_FUND_BID.FUND_BID;
				}else if(conditionInfo.getSecTransactionTypeId() == SECConstant.BusinessType.TRANSFORMABLE_BOND_BID_ONLINE.BOND_BID_WIN){
					transTypeID = SECConstant.BusinessType.TRANSFORMABLE_BOND_BID_ONLINE.BOND_BID_ONLINE;
				}else if(conditionInfo.getSecTransactionTypeId() == SECConstant.BusinessType.ENTERPRISE_BOND_BID.BOND_BID_QUOTA_CONFIRM){
					transTypeID = SECConstant.BusinessType.ENTERPRISE_BOND_BID.BOND_BID;
				}else if(conditionInfo.getSecTransactionTypeId() == SECConstant.BusinessType.ENCLOSED_FUND_BID_ONLINE.FUND_BID_WIN){
					transTypeID = SECConstant.BusinessType.ENCLOSED_FUND_BID_ONLINE.FUND_BID_ONLINE;
				}else if(conditionInfo.getSecTransactionTypeId() == SECConstant.BusinessType.TRANSFORMABLE_BOND_BID.BOND_BID_QUOTA_CONFIRM){
					transTypeID = SECConstant.BusinessType.TRANSFORMABLE_BOND_BID.BOND_BID;
				}else if(conditionInfo.getSecTransactionTypeId() == SECConstant.BusinessType.STOCK_BID_ONLINE.INITIAL_OFFERING_BID_REPAY){
					transTypeID = SECConstant.BusinessType.STOCK_BID_ONLINE.INITIAL_OFFERING_BID_ONLINE;
				}else if(conditionInfo.getSecTransactionTypeId() == SECConstant.BusinessType.TRANSFORMABLE_BOND_BID_ONLINE.BOND_BID_REPAY){
					transTypeID = SECConstant.BusinessType.TRANSFORMABLE_BOND_BID_ONLINE.BOND_BID_ONLINE;
				}else if(conditionInfo.getSecTransactionTypeId() == SECConstant.BusinessType.STOCK_BID.INITIAL_OFFERING_MARGIN_REPAY
						|| conditionInfo.getSecTransactionTypeId() == SECConstant.BusinessType.STOCK_BID.INITIAL_OFFERING_MARGIN_SUPPLY){
					transTypeID = SECConstant.BusinessType.STOCK_BID.INITIAL_OFFERING_QUOTA_CONFIRM;
				}else if(conditionInfo.getSecTransactionTypeId() == SECConstant.BusinessType.STOCK_BID.PROMOTION_BID_MARGIN_SUPPLY
						||conditionInfo.getSecTransactionTypeId() == SECConstant.BusinessType.STOCK_BID.PROMOTION_BID_MARGIN_REPAY){
					transTypeID = SECConstant.BusinessType.STOCK_BID.PROMOTION_BID_QUOTA_CONFIRM;		
				}else if(conditionInfo.getSecTransactionTypeId() == SECConstant.BusinessType.ENCLOSED_FUND_BID.FUND_BID_MARGIN_SUPPLY
						||conditionInfo.getSecTransactionTypeId() == SECConstant.BusinessType.ENCLOSED_FUND_BID.FUND_BID_MARGIN_REPAY){
					transTypeID = SECConstant.BusinessType.ENCLOSED_FUND_BID.FUND_BID_QUOTA_CONFIRM;		
				}else if(conditionInfo.getSecTransactionTypeId() == SECConstant.BusinessType.ENTERPRISE_BOND_BID.BOND_BID_MARGIN_REPAY
						||conditionInfo.getSecTransactionTypeId() == SECConstant.BusinessType.ENTERPRISE_BOND_BID.BOND_BID_MARGIN_SUPPLY){
					transTypeID = SECConstant.BusinessType.ENTERPRISE_BOND_BID.BOND_BID_QUOTA_CONFIRM;		
				}else if(conditionInfo.getSecTransactionTypeId() == SECConstant.BusinessType.STOCK_BID_ONLINE.PROMOTION_BID_REPAY){
					transTypeID = SECConstant.BusinessType.STOCK_BID_ONLINE.PROMOTION_BID_ONLINE;
				}else if(conditionInfo.getSecTransactionTypeId() == SECConstant.BusinessType.ENCLOSED_FUND_BID_ONLINE.FUND_BID_REPAY){
					transTypeID = SECConstant.BusinessType.ENCLOSED_FUND_BID_ONLINE.FUND_BID_ONLINE;
				}else if(conditionInfo.getSecTransactionTypeId() == SECConstant.BusinessType.TRANSFORMABLE_BOND_BID.BOND_BID_MARGIN_SUPPAY
						||conditionInfo.getSecTransactionTypeId() == SECConstant.BusinessType.TRANSFORMABLE_BOND_BID.BOND_BID_MARGIN_REPAY){
					transTypeID = SECConstant.BusinessType.TRANSFORMABLE_BOND_BID.BOND_BID_QUOTA_CONFIRM;		
				}

						
				//对于 股票一级网下申购、封闭式基金一级网下申购、企业债一级、可转债一级网下申购的比较操作	
				//补款业务	
				if(conditionInfo.getSecTransactionTypeId() == SECConstant.BusinessType.STOCK_BID.INITIAL_OFFERING_MARGIN_SUPPLY
						||conditionInfo.getSecTransactionTypeId() == SECConstant.BusinessType.STOCK_BID.PROMOTION_BID_MARGIN_SUPPLY
						||conditionInfo.getSecTransactionTypeId() == SECConstant.BusinessType.ENCLOSED_FUND_BID.FUND_BID_MARGIN_SUPPLY
						||conditionInfo.getSecTransactionTypeId() == SECConstant.BusinessType.ENTERPRISE_BOND_BID.BOND_BID_MARGIN_SUPPLY
						||conditionInfo.getSecTransactionTypeId() == SECConstant.BusinessType.TRANSFORMABLE_BOND_BID.BOND_BID_MARGIN_SUPPAY){
					strSQL = new StringBuffer();
					strSQL.append("select t.* from SEC_DeliveryOrder t, SEC_ApplyForm t2 ");
					strSQL.append("where t.transactiontypeid = "+ transTypeID +" ");
					strSQL.append("and t.id in (" + strIds + ") ");
					if(deliveryOrderStatusId == 1){		//显示已复核和已使用的
						strSQL.append("and t.statusId in (" + SECConstant.DeliveryOrderStatus.CHECKED + ",");
						strSQL.append(SECConstant.DeliveryOrderStatus.USED + ",");
						strSQL.append(SECConstant.DeliveryOrderStatus.POSTED + ") ");
					}
					strSQL.append("and t.applyformid = t2.id ");
					strSQL.append("and t.netincome > t2.amount ");
					//排序字段
					if(conditionInfo.getOrderField()!= null && conditionInfo.getOrderField().trim().length()>0){
						strSQL.append("order by t."+conditionInfo.getOrderField() + " ");
						if(conditionInfo.getDesc() == SECConstant.TRUE){				//正反序
							strSQL.append("desc");
						}
					} else{
						strSQL.append("order by t.id desc");
					}
				}
				//返款业务
				else if(conditionInfo.getSecTransactionTypeId() == SECConstant.BusinessType.STOCK_BID.INITIAL_OFFERING_MARGIN_REPAY
						||conditionInfo.getSecTransactionTypeId() == SECConstant.BusinessType.STOCK_BID.PROMOTION_BID_MARGIN_REPAY
						||conditionInfo.getSecTransactionTypeId() == SECConstant.BusinessType.ENCLOSED_FUND_BID.FUND_BID_MARGIN_REPAY
						||conditionInfo.getSecTransactionTypeId() == SECConstant.BusinessType.ENTERPRISE_BOND_BID.BOND_BID_MARGIN_REPAY
						||conditionInfo.getSecTransactionTypeId() == SECConstant.BusinessType.TRANSFORMABLE_BOND_BID.BOND_BID_MARGIN_REPAY){
					strSQL = new StringBuffer();
					strSQL.append("select t.* from SEC_DeliveryOrder t, SEC_ApplyForm t2 ");
					strSQL.append("where t.transactiontypeid = "+ transTypeID +" ");
					strSQL.append("and t.id in (" + strIds + ") ");
					if(deliveryOrderStatusId == 1){		//显示已复核和已使用的
						strSQL.append("and t.statusId in (" + SECConstant.DeliveryOrderStatus.CHECKED + ",");
						strSQL.append(SECConstant.DeliveryOrderStatus.USED + ",");
						strSQL.append(SECConstant.DeliveryOrderStatus.POSTED + ") ");
					}
					strSQL.append("and t.applyformid = t2.id ");
					strSQL.append("and t.netincome < t2.amount ");
					//排序字段
					if(conditionInfo.getOrderField()!= null && conditionInfo.getOrderField().trim().length()>0){
						strSQL.append("order by t."+conditionInfo.getOrderField() + " ");
						if(conditionInfo.getDesc() == SECConstant.TRUE){				//正反序
							strSQL.append("desc");
						}
					} else{
						strSQL.append("order by t.id desc");
					}
				}
				else{
					strSQL = new StringBuffer();
					strSQL.append("select t.* from SEC_DeliveryOrder t ");
					strSQL.append("where t.transactiontypeid = "+ transTypeID +" ");
					strSQL.append("and t.id in (" + strIds + ") ");
					if(deliveryOrderStatusId == 1){		//显示已复核和已使用的
						strSQL.append("and t.statusId in (" + SECConstant.DeliveryOrderStatus.CHECKED + ",");
						strSQL.append(SECConstant.DeliveryOrderStatus.USED + ",");
						strSQL.append(SECConstant.DeliveryOrderStatus.POSTED + ") ");
					}
					//排序字段
					if(conditionInfo.getOrderField()!= null && conditionInfo.getOrderField().trim().length()>0){
						strSQL.append("order by t."+conditionInfo.getOrderField() + " ");
						if(conditionInfo.getDesc() == SECConstant.TRUE){				//正反序
							strSQL.append("desc");
						}
					} else{
						strSQL.append("order by t.id desc");
					}
				}
				Log.print(strSQL.toString());
				try{
					prepareStatement(strSQL.toString());
					ResultSet rs = executeQuery();
					colResult = getDataEntitiesFromResultSet(DeliveryOrderInfo.class);
					finalizeDAO();
				}catch (ITreasuryDAOException e) {
				e.printStackTrace();
				throw new SecuritiesDAOException(e);
			}
			}
			
			finalizeDAO();
			
			return colResult;
		}
		catch (ITreasuryDAOException e)
		{
			// TODO Auto-generated catch block
			throw new SecuritiesDAOException(e);
		}
	}
	
	/*
	 *  (non-Javadoc)
	 * 
	 */
	/*
	 *  (non-Javadoc)
	 * 
	 */
	public String getDeliveryOrderCode(long lTransactionTypeID) throws SecuritiesException {

		String strSQL = "";
		String strCode = "000";
		long lCode = 0;
		Timestamp tsToday = Env.getSystemDateTime();
		String strYear = DataFormat.getDateString(tsToday).substring(2,4);
		
		try {
			initDAO();
			strSQL = " select max(nvl(Code,0)) Code from SEC_DeliveryOrder where Code like 'JG" + strYear + lTransactionTypeID + "%'";
			log4j.debug(strSQL);
			prepareStatement(strSQL);
			ResultSet rs = executeQuery();

			if (rs != null && rs.next())
			{   
				strCode = rs.getString(1);
				
				log4j.debug(strCode);
				
				if(strCode != null && strCode.length() == 11)
				{
					lCode = Long.parseLong(strCode.substring(8)) + 1;
				} else {
					lCode = 1;
				}
				strCode = "JG" + strYear + lTransactionTypeID + DataFormat.formatInt(lCode, 3, true);
			}

			
			finalizeDAO();
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}catch (SQLException ex) {
			throw new SecuritiesDAOException(new ITreasuryDAOException("数据库执行操作异常发生",ex));
	    }
		log4j.debug(":::::::::: ::::strcode::::::" + strCode);

		return strCode;
	}
//	public boolean isDuplicatedDeliveryOrder(DeliveryOrderInfo deliveryOrderInfo) {
//		try {
//			initDAO();
//			//----------------------------------------------------------
//			//  校验规则：
//			//  1。银行间业务，有系统成交编号的，其编号是唯一的，
//			//     所以只要校验系统成交编号即可。
//			//  2。其它业务，因为没有系统成交编号，所以必须比较所有
//			//     栏位才能知道交割单是否重复。
//			//-----------------------------------------------------------
//			
//			String transactionCode = deliveryOrderInfo.getSystemTransactionCode();
//			String firstSQL = "select * from SEC_DeliveryOrder where SystemTransactionCode = '"+transactionCode+"'";
//
//			prepareStatement(firstSQL);
//			ResultSet rs = executeQuery();
//			
//			//找到系统成交编号相同的交割单，可能是有重复的银行间交割单，
//			//也可能是其它交易的交割单，仍需继续查询。
//			if(rs.next() && this.checkDuplicatedDeliveryOrder(deliveryOrderInfo)){
//				finalizeDAO();
//				return true;
//			}
//	
//			finalizeDAO();
//			return false;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return false;
//	}
	public void checkDuplicatedDeliveryOrder(DeliveryOrderInfo info) throws SecuritiesException{
		String scode = "";
		
		try {
			initDAO();
			
			HashMap usedFields = info.gainAllUsedFieldsAndValue();
			
			Iterator iKey = usedFields.keySet().iterator();
			
			String strSQL="select * from SEC_DeliveryOrder where statusid > 0  ";
			
			while(iKey.hasNext()){
				String key = (String)iKey.next();
				Object o = usedFields.get(key);
				
				if(o instanceof String){
					if(!key.equalsIgnoreCase("remark")){
						strSQL += " and " + key +" = '" + (String)usedFields.get(key) + "'";
					}
					
					
				}else if (o instanceof Timestamp){
					strSQL += " and to_char(" + key +",'yyyy-mm-dd') = '"+(DataFormat.getDateString((Timestamp) usedFields.get(key))) + "' ";
					
				}else if (o instanceof Long){
					strSQL += " and " + key +" = " + ((Long) usedFields.get(key)).longValue() ;
					
				}else if (o instanceof Double){
					strSQL += " and " + key +" = " + ((Double) usedFields.get(key)).doubleValue();
				}
			}
				
			System.out.println("######: "+strSQL);

			prepareStatement(strSQL);
			ResultSet rs = executeQuery();
			
			if(rs.next()){
				scode = rs.getString("code");
				
				finalizeDAO();
				throw new DeliveryOrderDuplicatedException( scode );
			}
	
			finalizeDAO();

		} catch (Exception e) {
			throw new DeliveryOrderDuplicatedException( scode );
		}
	}
	/**
	 *检查时间戳
	 *
	 *当交割单的时间戳与数据库里的不相同时.检查交割单状态，给前台打出时间戳不同的原因。
	*/
	public DeliveryOrderInfo checkTimeStamp(DeliveryOrderInfo deliveryOrderInfo) throws SecuritiesException{
		//deliveryOrderInfo只有id,statusID,TimeStamp三个参数。
		//初始化数据库联接
		SEC_DeliveryOrderDAO deliveryOrderDAO = new SEC_DeliveryOrderDAO();
		
		//提取数据库里的交割单
		DeliveryOrderInfo deliveryOrderInfoFromDB = new DeliveryOrderInfo();
		try {
			deliveryOrderInfoFromDB = (DeliveryOrderInfo)deliveryOrderDAO.findByID(deliveryOrderInfo.getId(), deliveryOrderInfoFromDB.getClass());
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}
		
		//比较两个交割单里的时间戳是否相同
		// 交割单的时间戳与数据库里的不相同时.说明该条交割单已经被别人修改.
		if(!deliveryOrderInfo.getTimeStamp().equals(deliveryOrderInfoFromDB.getTimeStamp())){
			//检查交割单前后变化的状态，查出时间戳改变的原因。
			String strReason = "";
			
			switch ((int) deliveryOrderInfoFromDB.getStatusId())
			{
				case (int) SECConstant.DeliveryOrderStatus.TEMPSAVED:
					strReason = "暂存";
					break;
				case (int) SECConstant.DeliveryOrderStatus.SAVED:
					strReason = "保存";
					break;
				case (int) SECConstant.DeliveryOrderStatus.DELETED:
					strReason = "删除";
					break;
				case (int) SECConstant.DeliveryOrderStatus.USED:
					strReason = "使用";
					break;
				case (int) SECConstant.DeliveryOrderStatus.POSTED:
					strReason = "过帐";
					break;
				case (int) SECConstant.DeliveryOrderStatus.CHECKED:
					strReason = "复核";
					break;
			}
			if(deliveryOrderInfo.getStatusId() == SECConstant.DeliveryOrderStatus.CHECKED &&
				deliveryOrderInfoFromDB.getStatusId() == SECConstant.DeliveryOrderStatus.SAVED){
				strReason = "取消复核";
			}
			throw new DeliveryOrderModifiedException(strReason);
		}
		return deliveryOrderInfoFromDB;
	}
	//test
	public static void main(String[] args) {
		
		SEC_DeliveryOrderDAO test = new SEC_DeliveryOrderDAO();
//		try{
//		    //DeliveryOrderInfo info = new DeliveryOrderInfo();
//		    //info.setApplyFormId(1);
//		    //info.setSystemTransactionCode("asdfawefawef");
////			DeliveryOrderInfo info = new DeliveryOrderInfo();
////			info.setSystemTransactionCode("00001");
////			info.setAmount(23.23);
////			info.setAccountId(1000);
////			info.setTransactionDate(DataFormat.getDateTime("2003-02-03"));
//			
//			//System.out.println(info.toString());
//			
//			//test.add(info);
//			test.checkDuplicatedDeliveryOrder(info);
//			
//
//		}catch(Exception e){
//			e.printStackTrace();
//		}  
	}
	
	
	/**
	 * 获取“已做过日结，但后来又被修改的交割单”的交割日期
	 * @param clientID -1:表示无效，不进行品配
	 * */
	public Timestamp getDeliveryDateThatHadSettledAndModified(long accontID,long clientID,Timestamp lastEndProcessDate) throws SecuritiesDAOException{
		Timestamp resDate = null;
		try {
			initDAO();
		
			String strSQL = "select min(DeliveryDate) from SEC_DeliveryOrder where (accountid = "+accontID + "or OppositeAccountID="+accontID +")" +
					" and  DeliveryDate <= ? and InputDate<= ? and (UpdateDate>? or DeleteDate > ?)";
			if(clientID > 0)
				strSQL += "and clientid = "+clientID;
			PreparedStatement localPS = prepareStatement(strSQL);
			int i=1;
			try {
				localPS.setTimestamp(i++, lastEndProcessDate);
				localPS.setTimestamp(i++, lastEndProcessDate);
				localPS.setTimestamp(i++, lastEndProcessDate);
				localPS.setTimestamp(i++, lastEndProcessDate);
	
				ResultSet localRS = executeQuery();
				if(localRS.next())
					resDate = localRS.getTimestamp(1);
			} catch (SQLException e1) {
				throw new ITreasuryDAOException("",e1);
			}			
			finalizeDAO();
		} catch (ITreasuryDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return resDate;
	}
	
	/**
	 * 获取上次日结日期之后录入的，但交割日是上次日结日期之前的交割日期
	 * @param clientID -1:表示无效，不进行品配
	 * */
	public Timestamp getDeliveryDateThatInputAfterSettlementButDeliverBeforeSettlement(long accontID,long clientID,long securitiesID, Timestamp lastEndProcessDate) throws SecuritiesDAOException{
		Timestamp resDate = null;
		try {
			initDAO();
			String time = lastEndProcessDate.toString();
			time = time.substring(0, 10);
			String strSQL = "select min(DeliveryDate) as date1 from SEC_DeliveryOrder where (accountid = "+accontID + " or OppositeAccountID="+accontID +")" +
					" and  DeliveryDate <= to_date('" + time + "','yyyy-mm-dd') and InputDate >= to_date('" + time + "','yyyy-mm-dd')";
			if(clientID > 0)
				strSQL += "and clientid = "+clientID;
			if(securitiesID > 0)
				strSQL += " and securitiesID = "+securitiesID;			
			PreparedStatement localPS = prepareStatement(strSQL);
			//int i=1;
			try {
				//localPS.setTimestamp(i++, lastEndProcessDate);
				//localPS.setTimestamp(i++, lastEndProcessDate);
	
				ResultSet localRS = executeQuery();
				if(localRS.next())
					resDate = localRS.getTimestamp("date1");
			} catch (SQLException e1) {
				throw new ITreasuryDAOException("",e1);
			}			
			finalizeDAO();
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}	
		return resDate;		
	}
	
	/**
	 * 根据条件查询需要结转成本的交割单
	 * */
	public Collection getDeliveryOrderForCarryCost(long clientID,long accountID,long securitiesID,Timestamp sDate,Timestamp eDate)throws SecuritiesDAOException{
		try {
			initDAO();
			StringBuffer strBuffer = new StringBuffer(); 
			strBuffer.append("select d.* from sec_deliveryorder d ,sec_transactiontype t where d.statusid>=3 and d.transactiontypeid=t.id and t.StockDirection in (2,3) and (d.IsCarryCost =0 or d.IsCarryCost is null) and t.ISCARRYCOST = 1");
			if(sDate != null)
				strBuffer.append(" and DeliveryDate >= ? ");
			if(eDate != null)
				strBuffer.append(" and DeliveryDate <= ? ");
			
			if(clientID != -1){
				strBuffer.append(" and clientID = "+clientID);
			}
			if(accountID != -1){
				strBuffer.append(" and accountID = "+accountID);
			}
			
			if(securitiesID != -1){
				strBuffer.append(" and securitiesID = "+securitiesID);
			}			
			
			prepareStatement(strBuffer.toString());
			executeQuery();
			Collection c = getDataEntitiesFromResultSet(DeliveryOrderInfo.class);			
			
			finalizeDAO();
			return c;
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}
	}
	
	/**
	 * 状态>=复核，且交割状态=未交割，且交割日<=当前日
	 * */
	public Collection getDeliveryOrderForDeliverAutomatically(long officeID,long currencyID) throws SecuritiesDAOException{
		try {
			initDAO();
			StringBuffer strBuffer = new StringBuffer(); 
			//strBuffer.append("select * from sec_deliveryorder d where statusid="+SECConstant.DeliveryOrderStatus.CHECKED+" and  isDelivery < 1 and deliveryDate <= ?");
			strBuffer.append("select * from sec_deliveryorder d where isDelivery < 1 and deliveryDate <= ?");
			
			PreparedStatement localPS = prepareStatement(strBuffer.toString());
			Timestamp today = Env.getSecuritiesSystemDate(officeID, currencyID);
			try {
				localPS.setTimestamp(1,today);
			} catch (SQLException e1) {
				throw new ITreasuryDAOException("",e1);
			}
			executeQuery();
			Collection c = getDataEntitiesFromResultSet(DeliveryOrderInfo.class);			
			
			finalizeDAO();
			return c;
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}
	}
	
	/**
	 * 已被交割单使用的申请书金额
	 * */
	public double getApplyAmountThatUsedByDeliveryOrder(long securitiesID, long securitiesTypeID) throws SecuritiesDAOException{
		double amount = 0.0;
		try {		
			String strSQL = "select sum(d.amount) from sec_applyform a ,sec_deliveryorder d where d.TransactionTypeID in (select id from SEC_TransactionType where StockDirection=1) and a.securitiesid "; 
			initDAO();
			if(securitiesID > 0){
				strSQL += " = "+ securitiesID; 
			}else{
				strSQL += " in (select id from SEC_Securities where TypeID= "+ securitiesTypeID + ")"; 
			}
			
			strSQL += " and d.ApplyFormID=a.id and d.statusid<>0 and a.statusid=3 ";
			prepareStatement(strSQL);
			ResultSet localRS = executeQuery();
			try {
				if(localRS.next())
					amount = localRS.getDouble(1);
			} catch (SQLException e1) {
				throw new ITreasuryDAOException("",e1);	
			}
			finalizeDAO();
		} catch (ITreasuryDAOException e) {			
			throw new SecuritiesDAOException(e);
		}	
		return amount;		
	}
	
	public double[] sumAmountAndQuantityByApplyFormID(long applyFormID) throws SecuritiesDAOException{
		double res[] = {0.0,0.0};
		try {		
			String strSQL = "select sum(a.amount),sum(a.quantity) from sec_deliveryorder a, sec_transactiontype b where a.statusid > 0 and a.transactiontypeid = b.id and b.isneedapplyform > 0 and a.applyformid = "+applyFormID; 
			initDAO();
			prepareStatement(strSQL);
			ResultSet localRS = executeQuery();
			try {
				if(localRS.next())
					res[0] = localRS.getDouble(1);
					res[1] = localRS.getDouble(2);
			} catch (SQLException e1) {
				throw new ITreasuryDAOException("",e1);	
			}
			finalizeDAO();
		} catch (ITreasuryDAOException e) {			
			throw new SecuritiesDAOException(e);
		}	
		return res;				
	}
	
	/**
	 * 验证交易类型
	 * @param TransactionTypeID
	 * @return
	 */
	private boolean validateTransactionType(long TransactionTypeID){
		// 银行间一级
		if(SECConstant.BusinessType.BANK_NATIONAL_BOND_BID.BOND_BID==TransactionTypeID)
			return true;
		
		// 银行间二级
		if(SECConstant.BusinessType.BANK_NATIONAL_BOND_TRANSACTION.BOND_BUYIN==TransactionTypeID ||
				SECConstant.BusinessType.BANK_NATIONAL_BOND_TRANSACTION.BOND_SELL==TransactionTypeID ||
				SECConstant.BusinessType.BANK_NATIONAL_BOND_TRANSACTION.BOND_ACCRUAL_IN==TransactionTypeID || 
				SECConstant.BusinessType.BANK_NATIONAL_BOND_TRANSACTION.BOND_MATURITY_REPAY==TransactionTypeID)
			return true;
		
		// 交易所一级
		if(SECConstant.BusinessType.EXCHANGECENTER_NATIONAL_BOND_BID.BOND_BID==TransactionTypeID)
			return true;
		
		// 交易所二级
		if(SECConstant.BusinessType.EXCHANGECENTER_NATIONAL_BOND_TRANSACTION.BOND_BUYIN==TransactionTypeID ||
				SECConstant.BusinessType.EXCHANGECENTER_NATIONAL_BOND_TRANSACTION.BOND_SELL==TransactionTypeID ||
				SECConstant.BusinessType.EXCHANGECENTER_NATIONAL_BOND_TRANSACTION.BOND_ACCRUAL_IN==TransactionTypeID ||
				SECConstant.BusinessType.EXCHANGECENTER_NATIONAL_BOND_TRANSACTION.BOND_MATURITY_REPAY==TransactionTypeID)
			return true;
		return false;
	}
	
	public Map getDeliveryOrderSumInfo(DeliveryOrderConditionInfo deliveryOrderConditionInfo) throws SecuritiesException 
	{
		Map m = new HashMap();
		try
		{
			initDAO();
		}
		catch (ITreasuryDAOException e)
		{
			// TODO Auto-generated catch block
			throw new SecuritiesDAOException(e);
		}
		Collection col = null;
		String strSQL = "select sum(t.amount) amount , sum(t.interest) interest,sum(t.netincome) netincome,sum(t.maturityamount) maturityamount,sum(t.suspenseinterest) suspenseinterest,sum(t.tax) tax from (" +getSQLByCondition(deliveryOrderConditionInfo)+") t";
		try {

			prepareStatement (strSQL);
			ResultSet rs = executeQuery ();
			Log.print("组成preparedStatement完成");
			if(rs!=null && rs.next()){
				m.put("amount", new Double(rs.getDouble("amount")));// 拆借金额 
				m.put("interest", new Double(rs.getDouble("interest")));//实计利息
				m.put("netincome", new Double(rs.getDouble("netincome")));//实际拆借
				m.put("maturityamount", new Double(rs.getDouble("maturityamount")));//到期还款金额
				m.put("suspenseinterest", new Double(rs.getDouble("suspenseinterest")));//应计利息
				m.put("tax", new Double(rs.getDouble("tax")));//手续费统计
			}
			finalizeDAO();
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
			throw new SecuritiesDAOException(e);
		}catch (Exception e){
			e.printStackTrace();
		}
		return m;
	}	
	
	public String getSQLByCondition(
			DeliveryOrderConditionInfo deliveryOrderConditionInfo) {
		String strSQL = "select a.* , trunc(b.deliverydate-b.TransactionDate) defterm from SEC_DeliveryOrder a , SEC_DeliveryOrder b  where 1=1 and a.id=b.id \n";

		strSQL += "and a.currencyId = "
				+ deliveryOrderConditionInfo.getCurrencyId() + " \n";// 币种

		strSQL += "and a.officeId = "
				+ deliveryOrderConditionInfo.getOfficeId() + " \n";// 办事处

		// 回购期限
		if (validateTransactionType(deliveryOrderConditionInfo
				.getSecTransactionTypeId())) {
			if (deliveryOrderConditionInfo.getStartTerm() >= 0) {
				strSQL += "and trunc(a.deliverydate-a.TransactionDate) >= "
						+ deliveryOrderConditionInfo.getStartTerm() + " \n";
			}
			if (deliveryOrderConditionInfo.getEndTerm() >= 0) {
				strSQL += "and trunc(a.deliverydate-a.TransactionDate) <= "
						+ deliveryOrderConditionInfo.getEndTerm() + " \n";
			}
		}

		if (deliveryOrderConditionInfo.getSecTransactionTypeId() > 0) { // 交易类型
			strSQL += "and a.transactionTypeId="
					+ deliveryOrderConditionInfo.getSecTransactionTypeId()
					+ "\n";
		}
		if (deliveryOrderConditionInfo.getSecTransactionDateFrom() != null // 交易日期由
				&& deliveryOrderConditionInfo.getSecTransactionDateFrom()
						.trim().length() > 0) {
			strSQL += "and a.TransactionDate>=to_date('"
					+ deliveryOrderConditionInfo.getSecTransactionDateFrom()
					+ "','yyyy-mm-dd') \n";
		}
		if (deliveryOrderConditionInfo.getSecTransactionDateTo() != null // 交易日期到
				&& deliveryOrderConditionInfo.getSecTransactionDateTo().trim()
						.length() > 0) {
			strSQL += "and a.TransactionDate<=to_date('"
					+ deliveryOrderConditionInfo.getSecTransactionDateTo()
					+ "','yyyy-mm-dd') \n";
		}
		if (deliveryOrderConditionInfo.getSecSystemTransactionCode() != null // 系统交易编号
				&& deliveryOrderConditionInfo.getSecSystemTransactionCode()
						.trim().length() > 0) {
			strSQL += "and a.SystemTransactionCode='"
					+ deliveryOrderConditionInfo.getSecSystemTransactionCode()
					+ "' \n";
		}
		if (deliveryOrderConditionInfo.getSecClientId() > 0) { // 业务单位
			strSQL += "and a.clientId="
					+ deliveryOrderConditionInfo.getSecClientId() + " \n";
		}
		if (deliveryOrderConditionInfo.getSecCounterpartId() > 0) { // 交易对手
			strSQL += "and a.counterpartId="
					+ deliveryOrderConditionInfo.getSecCounterpartId() + " \n";
		}
		if (deliveryOrderConditionInfo.getSecAmountFrom() > 0) { // 拆借金额(由)
			strSQL += "and a.amount>="
					+ deliveryOrderConditionInfo.getSecAmountFrom() + " \n";
		}
		if (deliveryOrderConditionInfo.getSecAmountTo() > 0) { // 拆借金额(到)
			strSQL += "and a.amount<="
					+ deliveryOrderConditionInfo.getSecAmountTo() + " \n";
		}

		if (deliveryOrderConditionInfo.getSecPledgeSecuritiesAmountFrom() > 0) { // 拆借金额(由)
			strSQL += "and a.pledgeSecuritiesAmount>="
					+ deliveryOrderConditionInfo
							.getSecPledgeSecuritiesAmountFrom() + " \n";
		}
		if (deliveryOrderConditionInfo.getSecPledgeSecuritiesAmountTo() > 0) { // 拆借金额(到)
			strSQL += "and a.pledgeSecuritiesAmount<="
					+ deliveryOrderConditionInfo
							.getSecPledgeSecuritiesAmountTo() + " \n";
		}
		// ----------------------股票二级增加
		if (deliveryOrderConditionInfo.getSecPriceFrom() > 0) { // 成交价格(由)
			strSQL += "and a.price>="
					+ deliveryOrderConditionInfo.getSecPriceFrom() + " \n";
		}
		if (deliveryOrderConditionInfo.getSecPriceTo() > 0) { // 成交价格(到)
			strSQL += "and a.price<="
					+ deliveryOrderConditionInfo.getSecPriceTo() + " \n";
		}
		if (deliveryOrderConditionInfo.getSecQuantityFrom() > 0) { // 成交数量(由)
			strSQL += "and a.quantity>="
					+ deliveryOrderConditionInfo.getSecQuantityFrom() + " \n";
		}
		if (deliveryOrderConditionInfo.getSecQuantityTo() > 0) { // 成交数量(到)
			strSQL += "and a.quantity<="
					+ deliveryOrderConditionInfo.getSecQuantityTo() + " \n";
		}
		if (deliveryOrderConditionInfo.getSecAccountId() > 0) { // 资金帐户ID
			strSQL += "and a.accountID="
					+ deliveryOrderConditionInfo.getSecAccountId() + " \n";
		}
		if (deliveryOrderConditionInfo.getSecSecuritiesId() > 0) { // 证券ID
			strSQL += "and a.SecuritiesID="
					+ deliveryOrderConditionInfo.getSecSecuritiesId() + " \n";
		}
		// ----------------------第三次添加
		if (deliveryOrderConditionInfo.getSecNetPriceFrom() > 0) { // 净价(由)
			strSQL += "and a.netprice>="
					+ deliveryOrderConditionInfo.getSecNetPriceFrom() + " \n";
		}
		if (deliveryOrderConditionInfo.getSecNetPriceTo() > 0) { // 净价(到)
			strSQL += "and a.netprice>="
					+ deliveryOrderConditionInfo.getSecNetPriceTo() + " \n";
		}

		// ----------------------为证券划转添加
		if (deliveryOrderConditionInfo.getSecCompanyBankId() > 0) { // 证券ID
			strSQL += "and a.CompanyBankId="
					+ deliveryOrderConditionInfo.getSecCompanyBankId() + " \n";
		}
		if (deliveryOrderConditionInfo.getSecCounterpartBankId() > 0) { // 证券ID
			strSQL += "and a.CounterpartBankId="
					+ deliveryOrderConditionInfo.getSecCounterpartBankId()
					+ " \n";
		}
		if (deliveryOrderConditionInfo.getSecCounterpartAccountId() > 0) { // 证券ID
			strSQL += "and a.CounterpartAccountId="
					+ deliveryOrderConditionInfo.getSecCounterpartAccountId()
					+ " \n";
		}
		if (deliveryOrderConditionInfo.getSecOppositeSecuritiesId() > 0) { // 证券ID
			strSQL += "and a.OppositeSecuritiesId="
					+ deliveryOrderConditionInfo.getSecOppositeSecuritiesId()
					+ " \n";
		}
		// 国机用
		if (deliveryOrderConditionInfo.getIsRelatedByNoticeForm() == 0) { // 国机用
			strSQL += "and a.isRelatedByNoticeForm="
					+ deliveryOrderConditionInfo.getIsRelatedByNoticeForm()
					+ " \n";
		}
		// ----------------------
		if (deliveryOrderConditionInfo.getSecDeliveryOrderStatusId() >= 0) { // 交割单状态
			strSQL += "and a.statusId="
					+ deliveryOrderConditionInfo.getSecDeliveryOrderStatusId()
					+ " \n";
		} else {
			strSQL += "and a.statusId <> "
					+ SECConstant.DeliveryOrderStatus.DELETED + " \n";// 查询统计全部时过滤已删除状态
		}
		// else if(deliveryOrderConditionInfo.getSecDeliveryOrderStatusId()==0
		// || deliveryOrderConditionInfo.getSecDeliveryOrderStatusId()==-1){
		// strSQL+="and a.statusId in (" +
		// SECConstant.DeliveryOrderStatus.TEMPSAVED +
		// "," + SECConstant.DeliveryOrderStatus.SAVED + ") \n";
		// }
		// else{
		// strSQL+="and a.statusId=-1 \n";
		// }
		if (deliveryOrderConditionInfo.getSecInputUserId() > 0) { // 录入人
			strSQL += "and a.inputUserId="
					+ deliveryOrderConditionInfo.getSecInputUserId() + " \n";
		}
		if (deliveryOrderConditionInfo.getSecCheckUserId() > 0) { // 复核人
			if (deliveryOrderConditionInfo.getSecDeliveryOrderStatusId() == SECConstant.TransactionStatus.SAVE) {
				strSQL += "and a.inputUserId<>"
						+ deliveryOrderConditionInfo.getSecCheckUserId()
						+ " \n";
			} else if (deliveryOrderConditionInfo.getSecDeliveryOrderStatusId() == SECConstant.TransactionStatus.CHECK) {
				strSQL += "and a.checkUserId="
						+ deliveryOrderConditionInfo.getSecCheckUserId()
						+ " \n";
			}
		}

		if (deliveryOrderConditionInfo.getOrderField() != null //排序字段
				&& deliveryOrderConditionInfo.getOrderField().trim().length() > 0) {
			strSQL += "order by a."
					+ deliveryOrderConditionInfo.getOrderField();

			if (deliveryOrderConditionInfo.getDesc() == SECConstant.TRUE) { //正反序
				strSQL += " desc \n";
			}
		} else {
			strSQL += "order by a.id desc";
		}

		log4j.debug(strSQL);
		return strSQL;
	}	
	
}
