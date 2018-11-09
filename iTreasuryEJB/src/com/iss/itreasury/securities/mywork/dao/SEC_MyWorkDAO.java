/*
 * Created on 2004-5-20
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.securities.mywork.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.iss.itreasury.securities.exception.SecuritiesDAOException;
import com.iss.itreasury.securities.mywork.dataentity.ColumnNode;
import com.iss.itreasury.securities.mywork.dataentity.MyWorkColumn;
import com.iss.itreasury.securities.mywork.dataentity.TransactionType;
import com.iss.itreasury.securities.mywork.dataentity.TransactionURL;
import com.iss.itreasury.securities.util.NameRef;
import com.iss.itreasury.securities.util.SECConstant;
import com.iss.itreasury.securities.util.SecuritiesDAO;
import com.iss.itreasury.system.approval.bizlogic.ApprovalBiz;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Log;
/**
 * @author lgwang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class SEC_MyWorkDAO extends SecuritiesDAO{

	
	/**
	 * 我的工作分类
	 */
	public final static int SEC_APPLYFORM 		= 1;				//申请书
	public final static int SEC_DELIVERORDER 	= 2;				//交割单
	public final static int SEC_CONTRACT 		= 3;				//合同
	public final static int SEC_PLAN 			= 4;				//计划
	public final static int SEC_NOTICE			= 5;				//通知书
	
	
	public SEC_MyWorkDAO(){
		super("SEC_MyWorkDAO");
	}
	
	/**
	 * 得到交易类型的框架信息
	 * @param workTypeId
	 * @param nodeStatusId
	 * @param actionTypeId
	 * @return
	 * @throws SecuritiesDAOException
	 */
	public MyWorkColumn getBusinessType(long workTypeId,long nodeStatusId,long actionTypeId)throws SecuritiesDAOException{
		MyWorkColumn columns = new MyWorkColumn(workTypeId,nodeStatusId,actionTypeId);
		try{
			initDAO();
			
			String strSql = "select code nodeId,name nodeName,statusid nodeStatusId from sec_businessType where id <> 6 order by id";
			
			prepareStatement(strSql);
			ResultSet rs = this.executeQuery();
			
			while (rs.next()){
				
				columns.add(rs.getLong("nodeId"),						//添加一个新节点
										rs.getString("nodeName"),
										nodeStatusId,
										0,
										"");							//添加新节点
				
				columns.upToParent();						//回到父节点
			}
			
			finalizeDAO();
		}catch(SQLException sqlE){
			throw new SecuritiesDAOException("从数据库中查找我的工作信息时发生数据库错误",sqlE);
		}
		catch(Exception e){
			throw new SecuritiesDAOException("从数据库中查找我的工作信息时发生其他错误",e);
		}
		
		return columns;
	}
	

	/**
	 * 得到某一种"我的工作"的信息
	 * @param userId			用户ID
	 * @param currencyId		币种ID
	 * @param workTypeId		我的工作类型ID
	 * @param statusId			状态ID
	 * @return
	 * @throws SecuritiesDAOException
	 */
	public MyWorkColumn getSingleWork(long userId,long currencyId,long officeId,int workTypeId,long statusId,long actionTypeId)throws SecuritiesDAOException{
		MyWorkColumn column = new MyWorkColumn(workTypeId,statusId,actionTypeId);

		TransactionURL transURL = new TransactionURL();
		
		ArrayList alTmp = new ArrayList();
		
		boolean isSpecialType = false;					//特殊类型标志,因为申请和通知有权限转移问题,所以添加此标志
		
		try{
			initDAO();
			ApprovalBiz appBiz = new ApprovalBiz();
			
			long[] transIds = null;
			
			String strSql = "";
			
			boolean needCurrency = true;		//是否需要币种查询条件
			
			/**
			 * 按照工作类型来确定SQL
			 */
			switch (workTypeId){
				case SEC_APPLYFORM:{				//申请书
					
					if (actionTypeId == SECConstant.Actions.LINKSEARCH){
						strSql = "select count(trans.transactionTypeId) dataCount,trans.transactionTypeId nodeId,avg(trans.statusId) nodeStatusId,tra.name nodeName \n" +
						"from sec_applyform trans,sec_transactionType tra" + " \n" +
						"where trans.transactionTypeId = tra.id" + " \n" +
						"and trans.statusId=" + statusId + " \n" +
						"and (trans.currencyId=" + currencyId + " \n" +
						"or trans.transactionTypeId like '" + SECConstant.BusinessType.FOREIGN_CURRENCY_INVESTMENT.ID +"%') \n" + 
						"and trans.inputUserId =" + userId + " \n" +
						"and trans.nextCheckLevel = 1" + " \n" +
						
						//申请有9种大类
						" and trans.transactionTypeId in " +
						" ( " +

						" "+SECConstant.BusinessType.CENTRAL_BANK_NOTE_BID.NOTE_BID+", " +
						" "+SECConstant.BusinessType.CENTRAL_BANK_NOTE_TRANSACTION.NOTE_BUYIN+", " +
						" "+SECConstant.BusinessType.CENTRAL_BANK_NOTE_TRANSACTION.NOTE_SELL+", " +
						" "+SECConstant.BusinessType.BANK_BOND_REPURCHASE.FUND_REPURCHASE+", " +
						" "+SECConstant.BusinessType.BANK_BOND_REPURCHASE.BOND_REPURCHASE+", " +
						" "+SECConstant.BusinessType.EXCHANGECENTER_BOND_REPURCHASE.FUND_REPURCHASE+", " +
						" "+SECConstant.BusinessType.EXCHANGECENTER_BOND_REPURCHASE.BOND_REPURCHASE+", " +
						" "+SECConstant.BusinessType.BANK_NATIONAL_BOND_BID.BOND_BID+", " +
						" "+SECConstant.BusinessType.BANK_NATIONAL_BOND_TRANSACTION.BOND_BUYIN+", " +
						" "+SECConstant.BusinessType.BANK_NATIONAL_BOND_TRANSACTION.BOND_SELL+", " +
						" "+SECConstant.BusinessType.EXCHANGECENTER_NATIONAL_BOND_BID.BOND_BID+", " +
						" "+SECConstant.BusinessType.BANK_NATIONAL_BOND_TRANSACTION.BOND_BUYIN+", " +
						" "+SECConstant.BusinessType.BANK_NATIONAL_BOND_TRANSACTION.BOND_SELL+", " +
						" "+SECConstant.BusinessType.ENCLOSED_FUND_BID_ONLINE.FUND_BID_ONLINE+", " +
						" "+SECConstant.BusinessType.ENCLOSED_FUND_BID.FUND_BID+", " +
						" "+SECConstant.BusinessType.ENCLOSED_FUND_TRANSACTION.FUND_BUYIN+", " +
						" "+SECConstant.BusinessType.ENCLOSED_FUND_TRANSACTION.FUND_SELL+", " +
						" "+SECConstant.BusinessType.MUTUAL_FUND_SUBSCRIBE.FUND_SUBSCRIBE+", " +
						" "+SECConstant.BusinessType.MUTUAL_FUND_BID.FUND_BID+", " +
						" "+SECConstant.BusinessType.MUTUAL_FUND_REDEEM.FUND_REDEEM+", " +
						" "+SECConstant.BusinessType.FUND_TRANSFER.FUND_TRANSFER+", " +
						
						" "+SECConstant.BusinessType.STOCK_BID_ONLINE.INITIAL_OFFERING_BID_ONLINE+", " +
						" "+SECConstant.BusinessType.STOCK_BID_ONLINE.PROMOTION_BID_ONLINE+", " +
						" "+SECConstant.BusinessType.STOCK_BID.INITIAL_OFFERING_BID+", " +
						" "+SECConstant.BusinessType.STOCK_BID.PROMOTION_BID+", " +
						" "+SECConstant.BusinessType.STOCK_TRANSACTION.STOCK_BUYIN+", " +
						" "+SECConstant.BusinessType.STOCK_TRANSACTION.STOCK_SELL+", " +
						" "+SECConstant.BusinessType.STOCK_TRANSACTION.STOCK_RATION_ONLINE+", " +
						" "+SECConstant.BusinessType.STOCK_TRANSACTION.STOCK_RATION+", " +
						" "+SECConstant.BusinessType.FINANCIAL_BOND_BID.BOND_BID+", " +
						" "+SECConstant.BusinessType.FINANCIAL_BOND_TRANSACTION.BOND_BUYIN+", " +
						" "+SECConstant.BusinessType.FINANCIAL_BOND_TRANSACTION.BOND_SELL+", " +
						" "+SECConstant.BusinessType.ENTERPRISE_BOND_BID.BOND_BID+", " +
						" "+SECConstant.BusinessType.ENTERPRISE_BOND_TRANSACTION.BOND_BUYIN+", " +
						" "+SECConstant.BusinessType.ENTERPRISE_BOND_TRANSACTION.BOND_SELL+", " +
						" "+SECConstant.BusinessType.TRANSFORMABLE_BOND_BID_ONLINE.BOND_BID_ONLINE+", " +
						" "+SECConstant.BusinessType.TRANSFORMABLE_BOND_BID.BOND_BID+", " +
						" "+SECConstant.BusinessType.TRANSFORMABLE_BOND_TRANSACTION.BOND_BUYIN+", " +
						" "+SECConstant.BusinessType.TRANSFORMABLE_BOND_TRANSACTION.BOND_SELL+", " +
						" "+SECConstant.BusinessType.DEBT_TO_EQUITY.DEBT_TO_EQUITY+" " +
						
						" ) " +
						
						"group by trans.transactionTypeId,tra.name order by trans.transactionTypeId";
					}
					/**
					 * 特殊类型
					 */
					else if (actionTypeId == SECConstant.Actions.CHECKSEARCH){
						isSpecialType = true;
						//transIds = TransactionType.getTransactionTypeCode(SEC_APPLYFORM);
						
						//申请有9种大类
						transIds = new long[40];
						transIds[0] = SECConstant.BusinessType.CENTRAL_BANK_NOTE_BID.NOTE_BID;  //央行票据申购
						transIds[1] = SECConstant.BusinessType.CENTRAL_BANK_NOTE_TRANSACTION.NOTE_BUYIN;  //央行票据买入
						transIds[2] = SECConstant.BusinessType.CENTRAL_BANK_NOTE_TRANSACTION.NOTE_SELL;  //央行票据卖出
						transIds[3] = SECConstant.BusinessType.BANK_BOND_REPURCHASE.FUND_REPURCHASE;  //正回购
						transIds[4] = SECConstant.BusinessType.BANK_BOND_REPURCHASE.BOND_REPURCHASE;  //逆回购
						transIds[5] = SECConstant.BusinessType.EXCHANGECENTER_BOND_REPURCHASE.FUND_REPURCHASE;  //融资回购
						transIds[6] = SECConstant.BusinessType.EXCHANGECENTER_BOND_REPURCHASE.BOND_REPURCHASE;  //融券回购
						transIds[7] = SECConstant.BusinessType.BANK_NATIONAL_BOND_BID.BOND_BID;  //银行间国债申购
						transIds[8] = SECConstant.BusinessType.BANK_NATIONAL_BOND_TRANSACTION.BOND_BUYIN;  //银行间国债买入
						transIds[9] = SECConstant.BusinessType.BANK_NATIONAL_BOND_TRANSACTION.BOND_SELL;  //银行间国债卖出
						transIds[10] = SECConstant.BusinessType.EXCHANGECENTER_NATIONAL_BOND_BID.BOND_BID;  //交易所国债申购
						transIds[11] = SECConstant.BusinessType.BANK_NATIONAL_BOND_TRANSACTION.BOND_BUYIN;  //交易所国债买入
						transIds[12] = SECConstant.BusinessType.BANK_NATIONAL_BOND_TRANSACTION.BOND_SELL;  //交易所国债卖出
						transIds[13] = SECConstant.BusinessType.ENCLOSED_FUND_BID_ONLINE.FUND_BID_ONLINE;  //封闭式基金网上申购
						transIds[14] = SECConstant.BusinessType.ENCLOSED_FUND_BID.FUND_BID;  //封闭式基金网下申购
						transIds[15] = SECConstant.BusinessType.ENCLOSED_FUND_TRANSACTION.FUND_BUYIN;  //封闭式基金买入
						transIds[16] = SECConstant.BusinessType.ENCLOSED_FUND_TRANSACTION.FUND_SELL;  //封闭式基金卖出
						transIds[17] = SECConstant.BusinessType.MUTUAL_FUND_SUBSCRIBE.FUND_SUBSCRIBE;  //开放式基金一级认购
						transIds[18] = SECConstant.BusinessType.MUTUAL_FUND_BID.FUND_BID;  //开放式基金二级申购
						transIds[19] = SECConstant.BusinessType.MUTUAL_FUND_REDEEM.FUND_REDEEM;  //开放式基金二级赎回
						transIds[20] = SECConstant.BusinessType.FUND_TRANSFER.FUND_TRANSFER;  //基金转换

						transIds[21] = SECConstant.BusinessType.STOCK_BID_ONLINE.INITIAL_OFFERING_BID_ONLINE;  //股票首发网上申购
						transIds[22] = SECConstant.BusinessType.STOCK_BID_ONLINE.PROMOTION_BID_ONLINE;  //股票增发网上申购
						transIds[23] = SECConstant.BusinessType.STOCK_BID.INITIAL_OFFERING_BID;  //股票首发网下申购
						transIds[24] = SECConstant.BusinessType.STOCK_BID.PROMOTION_BID;  //股票增发网下申购
						transIds[25] = SECConstant.BusinessType.STOCK_TRANSACTION.STOCK_BUYIN;  //股票买入
						transIds[26] = SECConstant.BusinessType.STOCK_TRANSACTION.STOCK_SELL;  //股票卖出
						transIds[27] = SECConstant.BusinessType.STOCK_TRANSACTION.STOCK_RATION_ONLINE;  //网上配股
						transIds[28] = SECConstant.BusinessType.STOCK_TRANSACTION.STOCK_RATION;  //网下配股
						transIds[29] = SECConstant.BusinessType.FINANCIAL_BOND_BID.BOND_BID;  //金融债申购
						transIds[30] = SECConstant.BusinessType.FINANCIAL_BOND_TRANSACTION.BOND_BUYIN;  //金融债买入
						transIds[31] = SECConstant.BusinessType.FINANCIAL_BOND_TRANSACTION.BOND_SELL;  //金融债卖出
						transIds[32] = SECConstant.BusinessType.ENTERPRISE_BOND_BID.BOND_BID; //企业债申购
						transIds[33] = SECConstant.BusinessType.ENTERPRISE_BOND_TRANSACTION.BOND_BUYIN;  //企业债买入
						transIds[34] = SECConstant.BusinessType.ENTERPRISE_BOND_TRANSACTION.BOND_SELL;  //企业债卖出
						transIds[35] = SECConstant.BusinessType.TRANSFORMABLE_BOND_BID_ONLINE.BOND_BID_ONLINE;  //可转债网上申购
						transIds[36] = SECConstant.BusinessType.TRANSFORMABLE_BOND_BID.BOND_BID;  //可转债网下申购
						transIds[37] = SECConstant.BusinessType.TRANSFORMABLE_BOND_TRANSACTION.BOND_BUYIN;  //可转债买入
						transIds[38] = SECConstant.BusinessType.TRANSFORMABLE_BOND_TRANSACTION.BOND_SELL;  //可转债卖出
						transIds[39] = SECConstant.BusinessType.DEBT_TO_EQUITY.DEBT_TO_EQUITY;  //债转股
						
						long lApprovalID[] = null;  //审批ID
						String strUser = "";
						for (int n=0;n<transIds.length;n++)  //循环查找每一种交易
						{
							//转换类型查询审批流设置
							long ActionID = 1;
							long lSubLoanTypeID = -1;
							long lActionID = -1;
							if ( ActionID == 1 )
							{
								long[] result=NameRef.getLoantypidAndActionidByTransactionid(ActionID,transIds[n]);  //申请
								lSubLoanTypeID=result[0];
								lActionID=result[1];
							}
							
							strSql=" select count(ss1.transactionTypeId) dataCount,ss1.transactionTypeId nodeId,avg(ss1.statusId) nodeStatusId,tra.name nodeName from ( ";
							
							strSql += " ( SELECT c.*,-1 moneysegment,-1 approvalid from SEC_ApplyForm c";
							strSql += " ,(select a.NAPPROVALCONTENTID from loan_approvaltracing a,(select NAPPROVALCONTENTID,max(ID) as ID from loan_approvaltracing group by NAPPROVALCONTENTID) b";
							strSql += " where (a.NNEXTUSERID="+userId+" ";
							strSql += " and a.nactionid="+lActionID+" and a.nloantypeid = "+lSubLoanTypeID+" ";
							strSql += "  and a.NMODULEID="+Constant.ModuleType.SECURITIES+" and nstatusid="+Constant.RecordStatus.VALID+""; 
							strSql += " and a.ID(+)= b.ID and a.NAPPROVALCONTENTID(+) = b.NAPPROVALCONTENTID )) d";
							strSql += " where c.id =d.NAPPROVALCONTENTID and c.statusid="+SECConstant.ApplyFormStatus.APPROVALING+"";
							strSql += " and c.transactiontypeid = " + transIds[n];
						    
						    strSql += " ) union ( ";
						    
							strSql += " select d.* from (";
							strSql += " select aaa.* from (";
							strSql += " select aa.*,rr.moneysegment,rr.approvalid from SEC_ApplyForm aa,loan_approvalrelation rr";
							strSql += " where rr.moduleid="+Constant.ModuleType.SECURITIES+" and aa.amount>rr.moneysegment and rr.officeid = "+officeId+" and rr.currencyid =" +currencyId+ " and aa.statusid="+SECConstant.ApplyFormStatus.SUBMITED;
							strSql += " and rr.actionid = " + lActionID ;
							strSql += " and rr.subloantypeid = " + lSubLoanTypeID ;
							strSql += " and aa.transactiontypeid = " + transIds[n];
							strSql += " ) aaa,(";
							strSql += " select aa.id,max(rr.moneysegment) maxamount from SEC_ApplyForm aa,loan_approvalrelation rr";
							strSql += " where rr.moduleid="+Constant.ModuleType.SECURITIES+" and aa.amount>rr.moneysegment and rr.officeid = "+officeId+" and rr.currencyid =" +currencyId+ " and aa.statusid="+SECConstant.ApplyFormStatus.SUBMITED;
							strSql += " and rr.actionid = " + lActionID ;
							strSql += " and rr.subloantypeid = " + lSubLoanTypeID ;
							strSql += " and aa.transactiontypeid = " + transIds[n];
							strSql += " group by aa.id ) bbb";
							strSql += " where aaa.id = bbb.id and aaa.moneysegment = bbb.maxamount) d,";	
							strSql += " loan_approvalsetting e,loan_approvalitem f";
							strSql += " where d.approvalid = e.id and f.napprovalid=e.id and f.nlevel=1 and f.nuserid="+userId;
							strSql += " ) ";
							
							strSql += " ) ss1,sec_transactionType tra where ss1.transactionTypeId = tra.id ";
							strSql +=" group by ss1.transactionTypeId,tra.name order by ss1.transactionTypeId ";
							
							System.out.println("申请审核查询SQL^^^^^^^^^^^===="+strSql);
							
								prepareStatement(strSql);
								ResultSet rs = this.executeQuery();
								ColumnNode node = null;

								if (rs.next()){
									node = new ColumnNode(null);
	
									node.setNodeId(rs.getLong("nodeId"));
									node.setNodeName(rs.getString("nodeName"));
									node.setNodeStatusId(rs.getLong("nodeStatusId"));
									node.setDataCount(rs.getLong("dataCount"));
									Log.print("交易："+node.getNodeId() + " // " + node.getDataCount());
									node.setNodeURL(transURL.getURL(userId,currencyId,officeId,node.getNodeId(),actionTypeId,workTypeId,statusId));			//置入链接
	
									alTmp.add(node);
								}
						}
					}
					break;
				}
				case SEC_DELIVERORDER:{				//交割单
					if (actionTypeId == SECConstant.Actions.LINKSEARCH){
						strSql = "select count(trans.transactionTypeId) dataCount,trans.transactionTypeId nodeId,avg(trans.statusId) nodeStatusId,tra.name nodeName \n" +
						"from sec_deliveryOrder trans,sec_transactionType tra \n" +
						"where trans.transactionTypeId = tra.id \n" +
						"and trans.statusId= " + statusId + " \n" +
						"and trans.currencyId= " + currencyId + " \n" +
						"and trans.inputUserId = " + userId + " \n" +
						"and trans.ISRELATEDBYNOTICEFORM <> " + SECConstant.TRUE + " \n" +
						
						//交割单不查询"资金拆借"
						" and trans.transactionTypeId not in ( "+SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_IN+", "+SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_IN_REPAY+", "+SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_OUT+", "+SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_OUT_REPAY+" ) " +
						
						"group by trans.transactionTypeId,tra.name order by trans.transactionTypeId ";
					}
					else if (actionTypeId == SECConstant.Actions.CHECKSEARCH){
						strSql = "select count(trans.transactionTypeId) dataCount,trans.transactionTypeId nodeId,avg(trans.statusId) nodeStatusId,tra.name nodeName \n" +
						"from sec_deliveryOrder trans,sec_transactionType tra \n" +
						"where trans.transactionTypeId = tra.id \n" +
						"and trans.statusId= " + statusId + " \n" +
						"and trans.currencyId= " + currencyId + " \n" +
						"and trans.inputUserId <> " + userId + " \n" +
						"and trans.ISRELATEDBYNOTICEFORM <> " + SECConstant.TRUE + " \n" +
						"group by trans.transactionTypeId,tra.name order by trans.transactionTypeId ";
					}
					break;
				}
				case SEC_NOTICE:{					//通知书
					if (actionTypeId == SECConstant.Actions.LINKSEARCH){
						//因为通知单没有币种字段，所以要关联相关的交割单！
						strSql = "select count(trans.transactionTypeId) dataCount,trans.transactionTypeId nodeId,avg(trans.statusId) nodeStatusId,tra.name nodeName \n" +
						"from sec_noticeForm trans,sec_transactionType tra,sec_deliveryOrder delivery \n" +
						"where trans.transactionTypeId = tra.id \n" +
						"and trans.deliveryOrderId = delivery.id \n" +
						"and trans.statusId= " + statusId + " \n" +
						"and (delivery.currencyId= " + currencyId + " \n" +
						"or trans.transactionTypeId like '" + SECConstant.BusinessType.FOREIGN_CURRENCY_INVESTMENT.ID +"%') \n" + 
						"and trans.inputUserId = " + userId + " \n" +
						"and trans.NextCheckLevel = 1  \n " + 
						
						//业务通知单只有5种大类
						" and trans.transactionTypeId in " +
						" ( " +

						" "+SECConstant.BusinessType.CENTRAL_BANK_NOTE_BID.NOTE_BID+", " +
						" "+SECConstant.BusinessType.CENTRAL_BANK_NOTE_TRANSACTION.NOTE_BUYIN+", " +
						" "+SECConstant.BusinessType.CENTRAL_BANK_NOTE_TRANSACTION.NOTE_SELL+", " +
						" "+SECConstant.BusinessType.CENTRAL_BANK_NOTE_TRANSACTION.NOTE_ACCRUAL_IN+", " +
						" "+SECConstant.BusinessType.CENTRAL_BANK_NOTE_TRANSACTION.NOTE_MATURITY_REPAY+", " +
						" "+SECConstant.BusinessType.BANK_BOND_REPURCHASE.FUND_REPURCHASE+", " +
						" "+SECConstant.BusinessType.BANK_BOND_REPURCHASE.FUND_REPAY+", " +
						" "+SECConstant.BusinessType.BANK_BOND_REPURCHASE.BOND_REPURCHASE+", " +
						" "+SECConstant.BusinessType.BANK_BOND_REPURCHASE.BOND_REPAY+", " +
						" "+SECConstant.BusinessType.BANK_NATIONAL_BOND_BID.BOND_BID+", " +
						" "+SECConstant.BusinessType.BANK_NATIONAL_BOND_TRANSACTION.BOND_BUYIN+", " +
						" "+SECConstant.BusinessType.BANK_NATIONAL_BOND_TRANSACTION.BOND_SELL+", " +
						" "+SECConstant.BusinessType.BANK_NATIONAL_BOND_TRANSACTION.BOND_ACCRUAL_IN+", " +
						" "+SECConstant.BusinessType.BANK_NATIONAL_BOND_TRANSACTION.BOND_MATURITY_REPAY+", " +
						" "+SECConstant.BusinessType.EXCHANGECENTER_NATIONAL_BOND_BID.BOND_BID+", " +
						" "+SECConstant.BusinessType.ENCLOSED_FUND_BID.FUND_BID+", " +
						" "+SECConstant.BusinessType.ENCLOSED_FUND_BID.FUND_BID_MARGIN_SUPPLY+", " +
						" "+SECConstant.BusinessType.ENCLOSED_FUND_BID.FUND_BID_MARGIN_REPAY+", " +
						" "+SECConstant.BusinessType.MUTUAL_FUND_SUBSCRIBE.FUND_SUBSCRIBE+", " +
						" "+SECConstant.BusinessType.MUTUAL_FUND_BID.FUND_BID+", " +
						" "+SECConstant.BusinessType.MUTUAL_FUND_REDEEM.FUND_REDEEM+", " +
						
						//资金划拨
						" "+SECConstant.BusinessType.CAPITAL_TRANSFER.CAPITAL_IN+", " +
						" "+SECConstant.BusinessType.CAPITAL_TRANSFER.CAPITAL_OUT+", " +
						
						" "+SECConstant.BusinessType.STOCK_BID.INITIAL_OFFERING_BID+", " +
						" "+SECConstant.BusinessType.STOCK_BID.INITIAL_OFFERING_MARGIN_SUPPLY+", " +
						" "+SECConstant.BusinessType.STOCK_BID.INITIAL_OFFERING_MARGIN_REPAY+", " +
						" "+SECConstant.BusinessType.STOCK_BID.PROMOTION_BID+", " +
						" "+SECConstant.BusinessType.STOCK_BID.PROMOTION_BID_MARGIN_SUPPLY+", " +
						" "+SECConstant.BusinessType.STOCK_BID.PROMOTION_BID_MARGIN_REPAY+", " +
						" "+SECConstant.BusinessType.STOCK_TRANSACTION.STOCK_RATION+", " +
						" "+SECConstant.BusinessType.FINANCIAL_BOND_BID.BOND_BID+", " +
						" "+SECConstant.BusinessType.FINANCIAL_BOND_TRANSACTION.BOND_BUYIN+", " +
						" "+SECConstant.BusinessType.FINANCIAL_BOND_TRANSACTION.BOND_SELL+", " +
						" "+SECConstant.BusinessType.FINANCIAL_BOND_TRANSACTION.BOND_ACCRUAL_IN+", " +
						" "+SECConstant.BusinessType.FINANCIAL_BOND_TRANSACTION.BOND_MATURITY_REPAY+", " +
						" "+SECConstant.BusinessType.ENTERPRISE_BOND_BID.BOND_BID+", " +
						" "+SECConstant.BusinessType.ENTERPRISE_BOND_BID.BOND_BID_MARGIN_SUPPLY+", " +
						" "+SECConstant.BusinessType.ENTERPRISE_BOND_BID.BOND_BID_MARGIN_REPAY+", " +
						" "+SECConstant.BusinessType.TRANSFORMABLE_BOND_BID.BOND_BID+", " +
						" "+SECConstant.BusinessType.TRANSFORMABLE_BOND_BID.BOND_BID_MARGIN_SUPPAY+", " +
						" "+SECConstant.BusinessType.TRANSFORMABLE_BOND_BID.BOND_BID_MARGIN_REPAY+", " +
						
						" "+SECConstant.BusinessType.MUTUAL_FUND_MELON_CUTTING.FUND_CASH_MELON_CUTTING+" " +
						
						" ) " +
						
						"group by trans.transactionTypeId,tra.name order by trans.transactionTypeId ";
					}
					/**
					 * 特殊类型
					 */
					else if (actionTypeId == SECConstant.Actions.CHECKSEARCH){
						isSpecialType = true;
						transIds = TransactionType.getTransactionTypeCode(SEC_NOTICE);
						
						transIds = new long[42];
						transIds[0] = SECConstant.BusinessType.CENTRAL_BANK_NOTE_BID.NOTE_BID;  //央行票据申购
						transIds[1] = SECConstant.BusinessType.CENTRAL_BANK_NOTE_TRANSACTION.NOTE_BUYIN;  //央行票据买入
						transIds[2] = SECConstant.BusinessType.CENTRAL_BANK_NOTE_TRANSACTION.NOTE_SELL;  //央行票据卖出
						transIds[3] = SECConstant.BusinessType.CENTRAL_BANK_NOTE_TRANSACTION.NOTE_ACCRUAL_IN;  //兑息
						transIds[4] = SECConstant.BusinessType.CENTRAL_BANK_NOTE_TRANSACTION.NOTE_MATURITY_REPAY;  //到期还本
						transIds[5] = SECConstant.BusinessType.BANK_BOND_REPURCHASE.FUND_REPURCHASE;  //正回购
						transIds[6] = SECConstant.BusinessType.BANK_BOND_REPURCHASE.FUND_REPAY;  //正回购到期
						transIds[7] = SECConstant.BusinessType.BANK_BOND_REPURCHASE.BOND_REPURCHASE;  //逆回购
						transIds[8] = SECConstant.BusinessType.BANK_BOND_REPURCHASE.BOND_REPAY;  //逆回购到期
						transIds[9] = SECConstant.BusinessType.BANK_NATIONAL_BOND_BID.BOND_BID;  //银行间国债申购
						transIds[10] = SECConstant.BusinessType.BANK_NATIONAL_BOND_TRANSACTION.BOND_BUYIN;  //银行间国债买入
						transIds[11] = SECConstant.BusinessType.BANK_NATIONAL_BOND_TRANSACTION.BOND_SELL;  //银行间国债卖出
						transIds[12] = SECConstant.BusinessType.BANK_NATIONAL_BOND_TRANSACTION.BOND_ACCRUAL_IN;  //兑息
						transIds[13] = SECConstant.BusinessType.BANK_NATIONAL_BOND_TRANSACTION.BOND_MATURITY_REPAY;  //到期还本
						transIds[14] = SECConstant.BusinessType.EXCHANGECENTER_NATIONAL_BOND_BID.BOND_BID;  //交易所国债申购
						transIds[15] = SECConstant.BusinessType.ENCLOSED_FUND_BID.FUND_BID;  //封闭式基金网下申购
						transIds[16] = SECConstant.BusinessType.ENCLOSED_FUND_BID.FUND_BID_MARGIN_SUPPLY;  //封闭式基金申购差额补款
						transIds[17] = SECConstant.BusinessType.ENCLOSED_FUND_BID.FUND_BID_MARGIN_REPAY;  //封闭式基金申购差额返款
						transIds[18] = SECConstant.BusinessType.MUTUAL_FUND_SUBSCRIBE.FUND_SUBSCRIBE;  //开放式基金一级认购
						transIds[19] = SECConstant.BusinessType.MUTUAL_FUND_BID.FUND_BID;  //开放式基金二级申购
						transIds[20] = SECConstant.BusinessType.MUTUAL_FUND_REDEEM.FUND_REDEEM;  //开放式基金二级赎回
						transIds[21] = SECConstant.BusinessType.MUTUAL_FUND_MELON_CUTTING.FUND_CASH_MELON_CUTTING;  //开放式基金现金分红
						transIds[22] = SECConstant.BusinessType.CAPITAL_TRANSFER.CAPITAL_IN;  //资金存入
						transIds[23] = SECConstant.BusinessType.CAPITAL_TRANSFER.CAPITAL_OUT;  //资金取出
						
						transIds[24] = SECConstant.BusinessType.STOCK_BID.INITIAL_OFFERING_BID;  //股票首发网下申购
						transIds[25] = SECConstant.BusinessType.STOCK_BID.INITIAL_OFFERING_MARGIN_SUPPLY;  //股票首发差额补款
						transIds[26] = SECConstant.BusinessType.STOCK_BID.INITIAL_OFFERING_MARGIN_REPAY;  //股票首发差额返款
						transIds[27] = SECConstant.BusinessType.STOCK_BID.PROMOTION_BID;  //股票增发网下申购
						transIds[28] = SECConstant.BusinessType.STOCK_BID.PROMOTION_BID_MARGIN_SUPPLY;  //股票增发差额补款
						transIds[29] = SECConstant.BusinessType.STOCK_BID.PROMOTION_BID_MARGIN_REPAY;  //股票增发差额返款
						transIds[30] = SECConstant.BusinessType.STOCK_TRANSACTION.STOCK_RATION;  //网下配股
						transIds[31] = SECConstant.BusinessType.FINANCIAL_BOND_BID.BOND_BID;  //金融债申购
						transIds[32] = SECConstant.BusinessType.FINANCIAL_BOND_TRANSACTION.BOND_BUYIN;  //金融债买入
						transIds[33] = SECConstant.BusinessType.FINANCIAL_BOND_TRANSACTION.BOND_SELL;  //金融债卖出
						transIds[34] = SECConstant.BusinessType.FINANCIAL_BOND_TRANSACTION.BOND_ACCRUAL_IN;  //兑息
						transIds[35] = SECConstant.BusinessType.FINANCIAL_BOND_TRANSACTION.BOND_MATURITY_REPAY;  //到期还本 
						transIds[36] = SECConstant.BusinessType.ENTERPRISE_BOND_BID.BOND_BID;  //企业债申购
						transIds[37] = SECConstant.BusinessType.ENTERPRISE_BOND_BID.BOND_BID_MARGIN_SUPPLY;  //企业债申购差额补款
						transIds[38] = SECConstant.BusinessType.ENTERPRISE_BOND_BID.BOND_BID_MARGIN_REPAY;  //企业债申购差额返款  
						transIds[39] = SECConstant.BusinessType.TRANSFORMABLE_BOND_BID.BOND_BID;  //可转债网下申购
						transIds[40] = SECConstant.BusinessType.TRANSFORMABLE_BOND_BID.BOND_BID_MARGIN_SUPPAY;  //可转债申购差额补款
						transIds[41] = SECConstant.BusinessType.TRANSFORMABLE_BOND_BID.BOND_BID_MARGIN_REPAY;  //可转债申购差额返款
						
						long lApprovalID[] = null;  //审批ID
						String strUser = "";
						for (int n=0;n<transIds.length;n++){			//循环查找每一种交易
							//转换类型查询审批流设置
							long ActionID = 2;
							long lSubLoanTypeID = -1;
							long lActionID = -1;
							if ( ActionID == 2 )
							{
								long[] result=NameRef.getLoantypidAndActionidByTransactionid(ActionID,transIds[n]);  //业务
								lSubLoanTypeID=result[0];
								lActionID=result[1];
							}
							strSql  = " select count(ss1.transactionTypeId) dataCount,ss1.transactionTypeId nodeId,avg(ss1.statusId) nodeStatusId,tra.name nodeName from ( ";
							
							strSql += " ( SELECT c.*,-1 moneysegment,-1 approvalid ";
							strSql += " from SEC_NoticeForm c ";
							strSql += " ,(select a.NAPPROVALCONTENTID from loan_approvaltracing a,(select NAPPROVALCONTENTID,max(ID) as ID from loan_approvaltracing group by NAPPROVALCONTENTID) b";
							strSql += " where (a.NNEXTUSERID="+userId+" ";
							strSql += " and a.nactionid="+lActionID+" and a.nloantypeid = "+lSubLoanTypeID+" ";
							strSql += "  and a.NMODULEID="+Constant.ModuleType.SECURITIES+" and nstatusid="+Constant.RecordStatus.VALID+""; 
							strSql += " and a.ID(+)= b.ID and a.NAPPROVALCONTENTID(+) = b.NAPPROVALCONTENTID )) d";
							strSql += " ,sec_DeliveryOrder bb ";
							strSql += " where c.id =d.NAPPROVALCONTENTID and c.statusid="+SECConstant.NoticeFormStatus.APPROVALING+"";
							strSql += " and c.DeliveryOrderId = bb.id ";
							strSql += " and c.transactiontypeid = " + transIds[n];

						    strSql += " ) union ( ";
						    
							strSql += " select d.* from ( ";
							strSql += " select aaa.* ";
							strSql += " from ( ";
							strSql += " select aa.*,rr.moneysegment,rr.approvalid ";
							strSql += " from sec_noticeform aa,loan_approvalrelation rr ";
							strSql += " ,sec_DeliveryOrder bb ";
							strSql += " where rr.moduleid="+Constant.ModuleType.SECURITIES+" and rr.officeid = "+officeId+" and rr.currencyid =" +currencyId+ " and aa.statusid="+SECConstant.NoticeFormStatus.SUBMITED;
							
							//如果是资金划拨业务查询金额字段
							if ( transIds[n] == SECConstant.BusinessType.CAPITAL_TRANSFER.CAPITAL_IN || transIds[n] == SECConstant.BusinessType.CAPITAL_TRANSFER.CAPITAL_OUT )
							{
								strSql += " and bb.netincome>rr.moneysegment ";
							}
							else 
							{
								strSql += " and bb.amount>rr.moneysegment ";
							}
							
							strSql += " and aa.DeliveryOrderId = bb.id ";
							strSql += " and rr.actionid = " + lActionID ;
							strSql += " and rr.subloantypeid = " + lSubLoanTypeID ;
							strSql += " and aa.transactiontypeid = " + transIds[n];
							strSql += " ) aaa,(";
							strSql += " select aa.id,max(rr.moneysegment) maxamount from sec_noticeform aa,loan_approvalrelation rr";
							strSql += " ,sec_DeliveryOrder bb ";
							strSql += " where rr.moduleid="+Constant.ModuleType.SECURITIES+" and rr.officeid = "+officeId+" and rr.currencyid =" +currencyId+ " and aa.statusid="+SECConstant.NoticeFormStatus.SUBMITED;
							
							//如果是资金划拨业务查询金额字段
							if ( transIds[n] == SECConstant.BusinessType.CAPITAL_TRANSFER.CAPITAL_IN || transIds[n] == SECConstant.BusinessType.CAPITAL_TRANSFER.CAPITAL_OUT )
							{
								strSql += " and bb.netincome>rr.moneysegment ";
							}
							else 
							{
								strSql += " and bb.amount>rr.moneysegment ";
							}
							
							strSql += " and aa.DeliveryOrderId = bb.id ";
							strSql += " and rr.actionid = " + lActionID ;
							strSql += " and rr.subloantypeid = " + lSubLoanTypeID ;
							strSql += " and aa.transactiontypeid = " + transIds[n];
							strSql += " group by aa.id ) bbb";
							strSql += " where aaa.id = bbb.id and aaa.moneysegment = bbb.maxamount) d,";	
							strSql += " loan_approvalsetting e,loan_approvalitem f";
							strSql += " where d.approvalid = e.id and f.napprovalid=e.id and f.nlevel=1 and f.nuserid="+userId;
							strSql += " ) ";
							
							strSql += " ) ss1,sec_transactionType tra where ss1.transactionTypeId = tra.id ";
							strSql +=" group by ss1.transactionTypeId,tra.name order by ss1.transactionTypeId ";
							
							System.out.println("查询语句SQL^^^^^^^^^^^===="+strSql);
							
								prepareStatement(strSql);
								ResultSet rs = this.executeQuery();
								ColumnNode node = null;
								
								if (rs.next()){
									node = new ColumnNode(null);
									
									node.setNodeId(rs.getLong("nodeId"));
									node.setNodeName(rs.getString("nodeName"));
									node.setNodeStatusId(rs.getLong("nodeStatusId"));
									node.setDataCount(rs.getLong("dataCount"));
									Log.print("交易："+node.getNodeId() + " // " + node.getDataCount());
									node.setNodeURL(transURL.getURL(userId,currencyId,officeId,node.getNodeId(),actionTypeId,workTypeId,statusId));			//置入链接
									
									alTmp.add(node);
								}
						}
					}
					break;
				
				}
				case SEC_CONTRACT:{					//合同
					if (actionTypeId == SECConstant.Actions.LINKSEARCH){
						strSql = "select count(trans.transactionTypeId) dataCount,trans.transactionTypeId nodeId,avg(trans.statusId) nodeStatusId,tra.name nodeName \n" +
						"from sec_applyContract trans,sec_transactionType tra" + " \n" +
						"where trans.transactionTypeId = tra.id" + " \n" +
						"and trans.statusId=" + statusId + " \n" +
						"and (trans.currencyId=" + currencyId + " \n" +
						"or trans.transactionTypeId like '" + SECConstant.BusinessType.FOREIGN_CURRENCY_INVESTMENT.ID +"%') \n" + 
						"and trans.inputUserId =" + userId + " \n" +
						"group by trans.transactionTypeId,tra.name order by trans.transactionTypeId";
					}
					/**
					 * 特殊类型
					 */
					else if (actionTypeId == SECConstant.Actions.CHECKSEARCH){
						isSpecialType = true;
						transIds = TransactionType.getTransactionTypeCode(SEC_CONTRACT);
						
						long lApprovalID[] = null;							//审批ID
						String strUser = "";
						for (int n=0;n<transIds.length;n++){			//循环查找每一种交易
							
							lApprovalID = NameRef.getApprovalIDByTransactionTypeID(transIds[n] , 3,true);
							/**
							 * 取得下一级审核人
							 */
							
//							zpli modify 2005-09-14
							//TODO: 证券 lLoanTypeID 暂时没有取到
							strUser = appBiz.findTheVeryUser(
									Constant.ModuleType.SECURITIES, lApprovalID[0],
									actionTypeId, officeId, currencyId, userId,
									this.transConn);							
							//strUser = appBiz.findTheVeryUser(Constant.ModuleType.SECURITIES,lApprovalID[0],lApprovalID[1],userId,this.transConn);
							
							/**
							 * 是否需要 币种 ,如果是结构性投资不需要
							 */
							needCurrency = true;
							if (String.valueOf(transIds[n]).startsWith(""+SECConstant.BusinessType.FOREIGN_CURRENCY_INVESTMENT.ID)){
								needCurrency = false;
							}
							
							
							if (!"".equals(strUser)){
								strSql = "select count(app.transactionTypeId) dataCount," + " \n " +
								"app.transactionTypeId nodeId," + "\n" +
								"avg(app.statusId) nodeStatusId," + "\n" +
								"trans.name nodeName" + "\n" +
								
								"from sec_applyContract app,sec_transactionType trans" + "\n" +
								"where app.transactionTypeId = " + transIds[n] + "\n" +
								"and trans.id = " + transIds[n] + "\n" +
								"and app.statusid=" + statusId + "\n" ;
								if (needCurrency){
									strSql += "and app.currencyId=" + currencyId + "\n" ;
								}
								
								strSql += "and app.nextCheckUserId in " + strUser + "\n" +
								"group by app.transactionTypeId,trans.name" + "\n";
								
								prepareStatement(strSql);
								ResultSet rs = this.executeQuery();
								ColumnNode node = null;
								
								if (rs.next()){
									node = new ColumnNode(null);
									
									node.setNodeId(rs.getLong("nodeId"));
									node.setNodeName(rs.getString("nodeName"));
									node.setNodeStatusId(rs.getLong("nodeStatusId"));
									node.setDataCount(rs.getLong("dataCount"));
									Log.print("&^&^&^&^&@@@@@@合同交易："+node.getNodeId() + " // " + node.getDataCount());
									node.setNodeURL(transURL.getURL(userId,currencyId,officeId,node.getNodeId(),actionTypeId,workTypeId,statusId));			//置入链接

									alTmp.add(node);
								}
							}
						}
						
					}
					else if(actionTypeId == SECConstant.Actions.COMMIT){
						strSql = "select count(trans.transactionTypeId) dataCount,trans.transactionTypeId nodeId,avg(trans.statusId) nodeStatusId,tra.name nodeName \n" +
						"from sec_applyContract trans,sec_transactionType tra" + " \n" +
						"where trans.transactionTypeId = tra.id" + " \n" +
						"and trans.statusId=" + statusId + " \n" +
						"and (trans.currencyId=" + currencyId + " \n" +
						"or trans.transactionTypeId like '" + SECConstant.BusinessType.FOREIGN_CURRENCY_INVESTMENT.ID +"%') \n" + 
						"and trans.inputUserId =" + userId + " \n" +
						"group by trans.transactionTypeId,tra.name order by trans.transactionTypeId";
					}
					break;
				}
				case SEC_PLAN:{						//计划
					if(actionTypeId == SECConstant.Actions.LINKSEARCH){			//已提交
						strSql = "select count(bb.transactionTypeId) dataCount,bb.transactionTypeId nodeId,  \n" +
								"avg(bb.statusId) nodeStatusId,tra.name nodeName from \n" +
								"(select * from SEC_PLANMODIFY where statusid=1)cc,SEC_APPLYCONTRACT bb,SEC_transactionType tra \n" +
								"where cc.ContractID(+) = bb.ID \n" +
								"and bb.transactionTypeId = tra.id" + " \n" +
								"and cc.statusId=" + statusId + "\n" +
								"and (bb.currencyId=" + currencyId + " \n" +
								"or bb.transactionTypeId like '" + SECConstant.BusinessType.FOREIGN_CURRENCY_INVESTMENT.ID +"%') \n" +
								"and cc.inputUserId =" + userId + " \n" +
								"and cc.nextCheckLevel = 1" + " \n" +
								"group by bb.transactionTypeId,tra.name order by bb.transactionTypeId";
					}
					else if(actionTypeId == SECConstant.Actions.CHECKSEARCH){	//待审核
						isSpecialType = true;
						transIds = TransactionType.getTransactionTypeCode(SEC_PLAN);
						
						String strUser = "";
						for (int n=0;n<transIds.length;n++){			//循环查找每一种交易

							/**
							 * 取得下一级审核人
							 */
							
							//zpli modify 2005-09-14
							//TODO: 证券 lLoanTypeID 暂时没有取到							
							strUser = appBiz.findTheVeryUser(Constant.ModuleType.SECURITIES,
							Constant.ApprovalLoanType.OTHER,Constant.ApprovalAction.SECURITIES_CONTRACT_PLAN,officeId, currencyId,userId,this.transConn);
							
							
							/**
							 * 是否需要 币种 ,如果是结构性投资不需要
							 */
							needCurrency = true;
							if (String.valueOf(transIds[n]).startsWith(""+SECConstant.BusinessType.FOREIGN_CURRENCY_INVESTMENT.ID)){
								needCurrency = false;
							}
							
							
							if (!"".equals(strUser)){
								strSql = "select count(bb.transactionTypeId) dataCount,bb.transactionTypeId nodeId,  \n" +
								"avg(bb.statusId) nodeStatusId,tra.name nodeName from \n" +
								"(select * from SEC_PLANMODIFY where statusid=" + SECConstant.PlanModifyStatus.SUBMIT + ")cc,SEC_APPLYCONTRACT bb,SEC_transactionType tra \n" +
								"where cc.ContractID(+) = bb.ID \n" +
								"and bb.transactionTypeId = tra.id" + " \n" +
								"and cc.statusId=" + SECConstant.PlanModifyStatus.SUBMIT + " \n" +
								"and tra.id = " + transIds[n] + "\n" ;
								if (needCurrency){
									strSql += "and bb.currencyId=" + currencyId + "\n" ;
								}
								
								strSql += "and cc.nextCheckUserId in " + strUser + "\n" +
								"group by bb.transactionTypeId,tra.name" + "\n";
								
								prepareStatement(strSql);
								ResultSet rs = this.executeQuery();
								ColumnNode node = null;
								
								if (rs.next()){
									node = new ColumnNode(null);
									
									node.setNodeId(rs.getLong("nodeId"));
									node.setNodeName(rs.getString("nodeName"));
									node.setNodeStatusId(rs.getLong("nodeStatusId"));
									node.setDataCount(rs.getLong("dataCount"));
									node.setNodeURL(transURL.getURL(userId,currencyId,officeId,node.getNodeId(),actionTypeId,workTypeId,statusId));			//置入链接
									
									alTmp.add(node);
								}
							}
						}
					}
				}
			}
			
			
			if (!isSpecialType){
				Log.print(strSql);
				
				prepareStatement(strSql);
				ResultSet rs = this.executeQuery();
				ColumnNode node = null;
				
				
				
				while (rs.next()){
					node = new ColumnNode(null);
					
					node.setNodeId(rs.getLong("nodeId"));
					node.setNodeName(rs.getString("nodeName"));
					node.setNodeStatusId(rs.getLong("nodeStatusId"));
					node.setDataCount(rs.getLong("dataCount"));
					
					node.setNodeURL(transURL.getURL(userId,currencyId,officeId,node.getNodeId(),actionTypeId,workTypeId,statusId));			//置入链接
					
					alTmp.add(node);
				}
			}
			/**
			 * 转成数组
			 */
			ColumnNode[] nodes = new ColumnNode[alTmp.size()];

			for (int n=0;n<nodes.length;n++){
				nodes[n] = (ColumnNode)alTmp.get(n);
			}
			column = getBusinessType(workTypeId,statusId,actionTypeId);					//得到交易类型结构
			MyWorkColumn copy = getBusinessType(workTypeId,statusId,actionTypeId);		//得到交易类型交割拷贝
			column.upToRoot();						//回到根准备添加节点
			/**
			 * 针对对应的交易类型进行节点添加
			 */
			column.addNodesToColumn(column,copy,nodes);

			column.upToRoot();
			
			
			
			finalizeDAO();
		}catch(SQLException sqlE){
			throw new SecuritiesDAOException("从数据库中查找我的工作信息时发生数据库错误",sqlE);
		 }
		 catch(Exception e){
		 	throw new SecuritiesDAOException("从数据库中查找我的工作信息时发生其他错误",e);
		 }
		
		return column;
	}
}
