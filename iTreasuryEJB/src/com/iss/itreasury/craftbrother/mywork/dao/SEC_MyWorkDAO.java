/*
 * Created on 2004-5-20
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.craftbrother.mywork.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.securities.exception.SecuritiesDAOException;
import com.iss.itreasury.craftbrother.mywork.dataentity.ColumnNode;
import com.iss.itreasury.craftbrother.mywork.dataentity.MyWorkColumn;
import com.iss.itreasury.craftbrother.mywork.dataentity.TransactionType;
import com.iss.itreasury.craftbrother.mywork.dataentity.TransactionURL;
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
			
			String strSql = "select code nodeId,name nodeName,statusid nodeStatusId from sec_businessType order by id";
			
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
		
		String strURL = "/NASApp/iTreasury-craftbrother/mywork/mywork.jsp?type=";  //我的工作页面跳转，错误返回"我的工作"页面
		
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
					
					if (actionTypeId == SECConstant.Actions.LINKSEARCH)  //资产转让
					{
						//update
						strSql=" select count(ss1.transactionTypeId) dataCount,ss1.transactionTypeId nodeId,avg(ss1.statusId) nodeStatusId,tra.name nodeName from ( ";
						
						strSql +=" ( SELECT c.*,-1 moneysegment,-1 approvalid from SEC_ApplyForm c";
						strSql +=" ,(select a.NAPPROVALCONTENTID from loan_approvaltracing a,(select NAPPROVALCONTENTID,max(ID) as ID from loan_approvaltracing group by NAPPROVALCONTENTID) b";
						strSql +=" where (a.NNEXTUSERID="+userId+" ";
						strSql +=" and a.nactionid="+Constant.ApprovalAction.CAPITAL_REPURCHASE_APPLY+" and a.nloantypeid = "+Constant.ApprovalLoanType.CAPITAL_REPURCHASE+" ";
						strSql +="  and a.NMODULEID="+Constant.ModuleType.CRAFTBROTHER+" and nstatusid="+Constant.RecordStatus.VALID+""; 
						strSql +=" and a.ID(+)= b.ID and a.NAPPROVALCONTENTID(+) = b.NAPPROVALCONTENTID )) d";
						strSql +=" where c.id =d.NAPPROVALCONTENTID and c.statusid="+SECConstant.ApplyFormStatus.APPROVALING+"";
						strSql += " and c.transactiontypeid in ( "+SECConstant.BusinessType.CAPITAL_REPURCHASE.REPURCHASE_NOTIFY+", "+SECConstant.BusinessType.CAPITAL_REPURCHASE.AVERAGE_NOTIFY+", "+SECConstant.BusinessType.CAPITAL_REPURCHASE.BREAK_NOTIFY+" ) ";
						
					    strSql +=") union ( ";
					    
						strSql += " select d.* from (";
						strSql += " select aaa.* from (";
						strSql += " select aa.*,rr.moneysegment,rr.approvalid from SEC_ApplyForm aa,loan_approvalrelation rr";
						strSql += " where rr.moduleid="+Constant.ModuleType.CRAFTBROTHER+" and aa.amount>rr.moneysegment and rr.currencyid =" +currencyId+ " and aa.statusid="+SECConstant.ApplyFormStatus.SUBMITED;
						strSql += " and rr.actionid = " + Constant.ApprovalAction.CAPITAL_REPURCHASE_APPLY ;
						strSql += " and rr.subloantypeid = " + Constant.ApprovalLoanType.CAPITAL_REPURCHASE ;
						strSql += " and aa.transactiontypeid in ( "+SECConstant.BusinessType.CAPITAL_REPURCHASE.REPURCHASE_NOTIFY+", "+SECConstant.BusinessType.CAPITAL_REPURCHASE.AVERAGE_NOTIFY+", "+SECConstant.BusinessType.CAPITAL_REPURCHASE.BREAK_NOTIFY+" ) ";
						strSql += " ) aaa,(";
						strSql += " select aa.id,max(rr.moneysegment) maxamount from SEC_ApplyForm aa,loan_approvalrelation rr";
						strSql += " where rr.moduleid="+Constant.ModuleType.CRAFTBROTHER+" and aa.amount>rr.moneysegment and rr.currencyid =" +currencyId+ " and aa.statusid="+SECConstant.ApplyFormStatus.SUBMITED;
						strSql += " and rr.actionid = " + Constant.ApprovalAction.CAPITAL_REPURCHASE_APPLY ;
						strSql += " and rr.subloantypeid = " + Constant.ApprovalLoanType.CAPITAL_REPURCHASE ;
						strSql += " and aa.transactiontypeid in ( "+SECConstant.BusinessType.CAPITAL_REPURCHASE.REPURCHASE_NOTIFY+", "+SECConstant.BusinessType.CAPITAL_REPURCHASE.AVERAGE_NOTIFY+", "+SECConstant.BusinessType.CAPITAL_REPURCHASE.BREAK_NOTIFY+" ) ";
						strSql += " group by aa.id ) bbb";
						strSql += " where aaa.id = bbb.id and aaa.moneysegment = bbb.maxamount) d,";	
						strSql += " loan_approvalsetting e,loan_approvalitem f";
						strSql += " where d.approvalid = e.id and f.napprovalid=e.id and f.nlevel=1 and f.nuserid="+userId;
						strSql +=" ) ";
						
						//update
						strSql += " ) ss1,sec_transactionType tra where ss1.transactionTypeId = tra.id ";
						strSql +=" group by ss1.transactionTypeId,tra.name order by ss1.transactionTypeId ";
						
						System.out.println("资产转让申请SQL^^^^^^^^^^^===="+strSql);
						
						strURL = "../craftbrother/apply/control/a071c105.jsp?strSuccessPageURL=../view/a071v107.jsp&strFailPageURL=../mywork/mywork.jsp&currencyId="+currencyId+"&officeId="+officeId+"&userId="+userId+"&strAction="+SECConstant.Actions.LINKSEARCH+"&transactionTypeId=";  //资产转让页面链接
					}
					//资金拆借
					else if (actionTypeId == SECConstant.Actions.NEXTSTEP)
					{
						//update
						strSql=" select count(ss1.transactionTypeId) dataCount,ss1.transactionTypeId nodeId,avg(ss1.statusId) nodeStatusId,tra.name nodeName from ( ";
						
						strSql +=" ( SELECT c.*,-1 moneysegment,-1 approvalid from SEC_ApplyForm c";
						strSql +=" ,(select a.NAPPROVALCONTENTID from loan_approvaltracing a,(select NAPPROVALCONTENTID,max(ID) as ID from loan_approvaltracing group by NAPPROVALCONTENTID) b";
						strSql +=" where (a.NNEXTUSERID="+userId+" ";
						strSql +=" and a.nactionid in ("+Constant.ApprovalAction.CAPITAL_IN_APPLY+","+Constant.ApprovalAction.CAPITAL_OUT_APPLY+") and a.nloantypeid = "+Constant.ApprovalLoanType.CAPITAL_LANDING+" ";
						strSql +="  and a.NMODULEID="+Constant.ModuleType.CRAFTBROTHER+" and nstatusid="+Constant.RecordStatus.VALID+""; 
						strSql +=" and a.ID(+)= b.ID and a.NAPPROVALCONTENTID(+) = b.NAPPROVALCONTENTID )) d";
						strSql +=" where c.id =d.NAPPROVALCONTENTID and c.statusid="+SECConstant.ApplyFormStatus.APPROVALING+"";
						strSql += " and c.transactiontypeid in ( "+SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_IN+", "+SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_OUT+" ) ";

					    strSql +=") union ( ";
					    
						strSql += " select d.* from (";
						strSql += " select aaa.* from (";
						strSql += " select aa.*,rr.moneysegment,rr.approvalid from SEC_ApplyForm aa,loan_approvalrelation rr";
						strSql += " where rr.moduleid="+Constant.ModuleType.CRAFTBROTHER+" and aa.amount>rr.moneysegment and rr.currencyid =" +currencyId+ " and aa.statusid="+SECConstant.ApplyFormStatus.SUBMITED;
						strSql += " and rr.actionid in ("+Constant.ApprovalAction.CAPITAL_IN_APPLY+","+Constant.ApprovalAction.CAPITAL_OUT_APPLY+") ";
						strSql += " and rr.subloantypeid = " + Constant.ApprovalLoanType.CAPITAL_LANDING ;
						strSql += " and aa.transactiontypeid in ( "+SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_IN+", "+SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_OUT+" ) ";
						strSql += " ) aaa,(";
						strSql += " select aa.id,max(rr.moneysegment) maxamount from SEC_ApplyForm aa,loan_approvalrelation rr";
						strSql += " where rr.moduleid="+Constant.ModuleType.CRAFTBROTHER+" and aa.amount>rr.moneysegment and rr.currencyid =" +currencyId+ " and aa.statusid="+SECConstant.ApplyFormStatus.SUBMITED;
						strSql += " and rr.actionid in ("+Constant.ApprovalAction.CAPITAL_IN_APPLY+","+Constant.ApprovalAction.CAPITAL_OUT_APPLY+") ";
						strSql += " and rr.subloantypeid = " + Constant.ApprovalLoanType.CAPITAL_LANDING ;
						strSql += " and aa.transactiontypeid in ( "+SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_IN+", "+SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_OUT+" ) ";
						strSql += " group by aa.id ) bbb";
						strSql += " where aaa.id = bbb.id and aaa.moneysegment = bbb.maxamount) d,";	
						strSql += " loan_approvalsetting e,loan_approvalitem f";
						strSql += " where d.approvalid = e.id and f.napprovalid=e.id and f.nlevel=1 and f.nuserid="+userId;
						strSql +=" ) ";
						
						//update
						strSql += " ) ss1,sec_transactionType tra where ss1.transactionTypeId = tra.id ";
						strSql +=" group by ss1.transactionTypeId,tra.name order by ss1.transactionTypeId ";
						
						System.out.println("资金拆借申请SQL^^^^^^^^^^^===="+strSql);
						
						strURL = "../craftbrother/apply/control/a013c104.jsp?strSuccessPageURL=../view/a013v106.jsp&strFailPageURL=../mywork/mywork.jsp&currencyId="+currencyId+"&officeId="+officeId+"&userId="+userId+"&strAction="+SECConstant.Actions.LINKSEARCH+"&transactionTypeId=";  //资金拆借页面链接
					}
					//转贴现
					else if (actionTypeId == SECConstant.Actions.CHECKSEARCH)
					{
						long lSubLoanTypeID=23;
						//update
						strSql=" select count(ss1.ntypeid) dataCount,ss1.ntypeid nodeId,avg(ss1.nstatusid) nodeStatusId,tra.name nodeName from ( ";
						
			            strSql +="(SELECT aa.*,-1 moneysegment,-1 approvalid from Cra_LoanForm aa";
			            strSql +=" ,(select a.NAPPROVALCONTENTID from loan_approvaltracing a,(select NAPPROVALCONTENTID,max(ID) as ID from loan_approvaltracing group by NAPPROVALCONTENTID) b";
			            strSql +=" where (a.NNEXTUSERID="+userId+" ";
			            strSql +=" and a.nactionid="+Constant.ApprovalAction.LOAN_APPLY+" and a.nloantypeid = "+lSubLoanTypeID+" ";
			            strSql +="  and a.NMODULEID="+Constant.ModuleType.CRAFTBROTHER+" and nstatusid="+Constant.RecordStatus.VALID+"";
			            strSql +=" and a.ID(+)= b.ID and a.NAPPROVALCONTENTID(+) = b.NAPPROVALCONTENTID )) d";
			            strSql +=" where aa.id =d.NAPPROVALCONTENTID and aa.nstatusid = " + LOANConstant.LoanStatus.APPROVALING;
			            strSql +=" and aa.ntypeid="+LOANConstant.LoanType.ZTX+"";
			            
			            strSql +=" ) union ( ";
			             
			            strSql += " (select d.* from (";
			            strSql += " select aaa.* from (";
			            strSql += " select aa.*,rr.moneysegment,rr.approvalid from Cra_LoanForm aa,loan_approvalrelation rr";
			            strSql += " where rr.moduleid="+Constant.ModuleType.CRAFTBROTHER+" and aa.mLoanAmount>rr.moneysegment and rr.currencyid =" +currencyId;
			            strSql += " and rr.actionid = " + Constant.ApprovalAction.LOAN_APPLY ;
			            strSql += " and rr.subloantypeid = " + lSubLoanTypeID ;    
			            strSql += " and aa.ntypeid="+LOANConstant.LoanType.ZTX+" and aa.nstatusid = "+LOANConstant.LoanStatus.SUBMIT;
			            strSql += " ) aaa,(";
			            strSql += " select aa.id,max(rr.moneysegment) maxamount from Cra_LoanForm aa,loan_approvalrelation rr";
			            strSql += " where rr.moduleid="+Constant.ModuleType.CRAFTBROTHER+" and aa.mLoanAmount>rr.moneysegment and rr.currencyid =" +currencyId;
			            strSql += " and rr.actionid = " + Constant.ApprovalAction.LOAN_APPLY ;
			            strSql += " and rr.subloantypeid = " + lSubLoanTypeID ;            
			            strSql += " and aa.ntypeid="+LOANConstant.LoanType.ZTX+" and aa.nstatusid = "+LOANConstant.LoanStatus.SUBMIT;		            
			            strSql += " group by aa.id ) bbb";
			            strSql += " where aaa.id = bbb.id and aaa.moneysegment = bbb.maxamount) d,";	
			            strSql += " loan_approvalsetting e,loan_approvalitem f";
			            strSql += " where d.approvalid = e.id and f.nlevel=1 and f.napprovalid=e.id and f.nuserid="+userId+") )";
			            
						//update
						strSql += " ) ss1,sec_transactionType tra where ss1.ntypeid = tra.id ";
						strSql += " group by ss1.ntypeid,tra.name order by ss1.ntypeid ";
			            
			            System.out.println("转贴现申请=="+strSql);
			            
			            strURL = "../transdiscount/control/td009-c.jsp?strSuccessPageURL=../view/td009-v.jsp&strFailPageURL=../mywork/mywork.jsp&currencyId="+currencyId+"&officeId="+officeId+"&userId="+userId+"&queryPurpose=2&secTransactionTypeId=";  //转贴现页面链接
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
						
						//update
						"and trans.transactionTypeId in ( "+SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_IN+","+SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_IN_REPAY+","+SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_OUT+","+SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_OUT_REPAY+" ) \n" +
						
						"group by trans.transactionTypeId,tra.name order by trans.transactionTypeId ";
						
						strURL = "../craftbrother/deliveryorder/control/d013c003.jsp?strSuccessPageURL=../view/d013v008.jsp&strFailPageURL=../mywork/mywork.jsp&secDeliveryOrderStatusId="+SECConstant.TransactionStatus.SAVE+"&currencyId="+currencyId+"&officeId="+officeId+"&secCheckUserId="+userId+"&secTransactionTypeId=";  //资金拆借页面链接
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
						"group by trans.transactionTypeId,tra.name order by trans.transactionTypeId ";
					}
					//资金拆借
					else if (actionTypeId == SECConstant.Actions.CHECKSEARCH)
					{
						//update
						strSql=" select count(ss1.transactionTypeId) dataCount,ss1.transactionTypeId nodeId,avg(ss1.statusId) nodeStatusId,tra.name nodeName from ( ";
						
						strSql+=" ( SELECT c.*, -1 moneysegment,-1 approvalid ";
						strSql+=" from SEC_NoticeForm c ";
						strSql+=" ,(select a.NAPPROVALCONTENTID from loan_approvaltracing a,(select NAPPROVALCONTENTID,max(ID) as ID from loan_approvaltracing group by NAPPROVALCONTENTID) b";
						strSql+=" where (a.NNEXTUSERID="+userId+" ";
						strSql+=" and a.nactionid="+Constant.ApprovalAction.CAPITAL_LANDING_NOTICE+" and a.nloantypeid = "+Constant.ApprovalLoanType.CAPITAL_LANDING+" ";
						strSql+="  and a.NMODULEID="+Constant.ModuleType.CRAFTBROTHER+" and nstatusid="+Constant.RecordStatus.VALID+""; 
						strSql+=" and a.ID(+)= b.ID and a.NAPPROVALCONTENTID(+) = b.NAPPROVALCONTENTID )) d";
						strSql += " ,sec_DeliveryOrder bb ";
						strSql+=" where c.id =d.NAPPROVALCONTENTID and c.statusid="+SECConstant.NoticeFormStatus.APPROVALING+"";
						strSql += " and c.DeliveryOrderId = bb.id ";
						strSql += " and c.transactiontypeid in ( "+SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_IN+","+SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_IN_REPAY+","+SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_OUT+","+SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_OUT_REPAY+" ) ";
					    
					    strSql+=") union ( ";
					    
						strSql += " select d.* from ( ";
						strSql += " select aaa.* ";
						strSql += " from ( ";
						strSql += " select aa.*, rr.moneysegment, rr.approvalid ";
						strSql += " from sec_noticeform aa,loan_approvalrelation rr ";
						strSql += " ,sec_DeliveryOrder bb ";
						strSql += " where rr.moduleid="+Constant.ModuleType.CRAFTBROTHER+" and bb.amount>rr.moneysegment and rr.officeid = "+officeId+" and rr.currencyid =" +currencyId+ " and aa.statusid="+SECConstant.NoticeFormStatus.SUBMITED;
						strSql += " and aa.DeliveryOrderId = bb.id ";
						strSql += " and rr.actionid = " + Constant.ApprovalAction.CAPITAL_LANDING_NOTICE ;
						strSql += " and rr.subloantypeid = " + Constant.ApprovalLoanType.CAPITAL_LANDING ;
						strSql += " and aa.transactiontypeid in ( "+SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_IN+","+SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_IN_REPAY+","+SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_OUT+","+SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_OUT_REPAY+" ) ";
						strSql += " ) aaa,(";
						strSql += " select aa.id,max(rr.moneysegment) maxamount from sec_noticeform aa,loan_approvalrelation rr";
						strSql += " ,sec_DeliveryOrder bb ";
						strSql += " where rr.moduleid="+Constant.ModuleType.CRAFTBROTHER+" and bb.amount>rr.moneysegment and rr.officeid = "+officeId+" and rr.currencyid =" +currencyId+ " and aa.statusid="+SECConstant.NoticeFormStatus.SUBMITED;
						strSql += " and aa.DeliveryOrderId = bb.id ";
						strSql += " and rr.actionid = " + Constant.ApprovalAction.CAPITAL_LANDING_NOTICE ;
						strSql += " and rr.subloantypeid = " + Constant.ApprovalLoanType.CAPITAL_LANDING ;
						strSql += " and aa.transactiontypeid in ( "+SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_IN+","+SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_IN_REPAY+","+SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_OUT+","+SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_OUT_REPAY+" ) ";
						strSql += " group by aa.id ) bbb";
						strSql += " where aaa.id = bbb.id and aaa.moneysegment = bbb.maxamount) d,";	
						strSql += " loan_approvalsetting e,loan_approvalitem f";
						strSql += " where d.approvalid = e.id and f.napprovalid=e.id and f.nlevel=1 and f.nuserid="+userId;
						strSql +=" ) ";
						
						//update
						strSql += " ) ss1,sec_transactionType tra where ss1.transactionTypeId = tra.id ";
						strSql +=" group by ss1.transactionTypeId,tra.name order by ss1.transactionTypeId ";
						
						System.out.println("strSql=="+strSql);
						
						strURL = "../craftbrother/notice/control/n013c015.jsp?strSuccessPageURL=../view/n013v014.jsp&strFailPageURL=../mywork/mywork.jsp&currencyId="+currencyId+"&officeId="+officeId+"&userId="+userId+"&strAction="+SECConstant.Actions.LINKSEARCH+"&transactionTypeId=";  //资金拆借页面链接
					}
					//资产转让
					else if (actionTypeId == SECConstant.Actions.NEXTSTEP)
					{
						//update
						strSql=" select count(ss1.transactionTypeId) dataCount,ss1.transactionTypeId nodeId,avg(ss1.statusId) nodeStatusId,tra.name nodeName from ( ";
						
						strSql +=" ( SELECT c.*,-1 moneysegment,-1 approvalid ";
						strSql +=" from SEC_NoticeForm c ";
						strSql += " ,sec_applyContract bb ";
						strSql +=" ,(select a.NAPPROVALCONTENTID from loan_approvaltracing a,(select NAPPROVALCONTENTID,max(ID) as ID from loan_approvaltracing group by NAPPROVALCONTENTID) b";
						strSql +=" where (a.NNEXTUSERID="+userId+" ";
						strSql +=" and a.nactionid="+Constant.ApprovalAction.CAPITAL_REPURCHASE_NOTICE+" and a.nloantypeid = "+Constant.ApprovalLoanType.CAPITAL_REPURCHASE+" ";
						strSql +="  and a.NMODULEID="+Constant.ModuleType.CRAFTBROTHER+" and nstatusid="+Constant.RecordStatus.VALID+""; 
						strSql +=" and a.ID(+)= b.ID and a.NAPPROVALCONTENTID(+) = b.NAPPROVALCONTENTID )) d";
						strSql +=" where c.id =d.NAPPROVALCONTENTID and c.statusid="+SECConstant.NoticeFormStatus.APPROVALING+"";
						strSql += " and c.contractid = bb.id ";
						strSql += " and c.transactionTypeID in ( "+SECConstant.BusinessType.CAPITAL_REPURCHASE.REPURCHASE_NOTIFY+", "+SECConstant.BusinessType.CAPITAL_REPURCHASE.PAYBACK_NOTIFY+" ,"+SECConstant.BusinessType.CAPITAL_REPURCHASE.INTEREST_PAYMENT+", "+SECConstant.BusinessType.CAPITAL_REPURCHASE.AVERAGE_NOTIFY+", "+SECConstant.BusinessType.CAPITAL_REPURCHASE.ACCEPT_NOTIFY+", "+SECConstant.BusinessType.CAPITAL_REPURCHASE.BREAK_NOTIFY+" ) ";
						
						strSql +=" ) union ( ";
					    
						strSql += " select d.* from (";
						strSql += " select aaa.* from (";
						strSql += " select aa.*,rr.moneysegment,rr.approvalid ";
						strSql += " from sec_noticeform aa,loan_approvalrelation rr ";
						strSql += " ,sec_applyContract bb ";
						strSql += " where rr.moduleid="+Constant.ModuleType.CRAFTBROTHER+" and aa.noticeAmount>rr.moneysegment and rr.officeid = "+officeId+" and rr.currencyid =" +currencyId+" and aa.statusid="+SECConstant.NoticeFormStatus.SUBMITED;
						strSql += " and aa.contractid = bb.id ";
						strSql += " and rr.actionid = " + Constant.ApprovalAction.CAPITAL_REPURCHASE_NOTICE ;
						strSql += " and rr.subloantypeid = " + Constant.ApprovalLoanType.CAPITAL_REPURCHASE ;
						strSql += " and aa.transactionTypeID in ( "+SECConstant.BusinessType.CAPITAL_REPURCHASE.REPURCHASE_NOTIFY+", "+SECConstant.BusinessType.CAPITAL_REPURCHASE.PAYBACK_NOTIFY+" ,"+SECConstant.BusinessType.CAPITAL_REPURCHASE.INTEREST_PAYMENT+", "+SECConstant.BusinessType.CAPITAL_REPURCHASE.AVERAGE_NOTIFY+", "+SECConstant.BusinessType.CAPITAL_REPURCHASE.ACCEPT_NOTIFY+", "+SECConstant.BusinessType.CAPITAL_REPURCHASE.BREAK_NOTIFY+" ) ";
						strSql += " ) aaa,(";
						strSql += " select aa.id,max(rr.moneysegment) maxamount ";
						strSql += " from SEC_NoticeForm aa,loan_approvalrelation rr ";
						strSql += " ,sec_applyContract bb ";
						strSql += " where rr.moduleid="+Constant.ModuleType.CRAFTBROTHER+" and aa.noticeAmount>rr.moneysegment and rr.officeid = "+officeId+" and rr.currencyid =" +currencyId+ " and aa.statusid="+SECConstant.NoticeFormStatus.SUBMITED;
						strSql += " and rr.actionid = " + Constant.ApprovalAction.CAPITAL_REPURCHASE_NOTICE ;
						strSql += " and rr.subloantypeid = " + Constant.ApprovalLoanType.CAPITAL_REPURCHASE ;
						strSql += " and aa.contractid = bb.id ";
						strSql += " and aa.transactionTypeID in ( "+SECConstant.BusinessType.CAPITAL_REPURCHASE.REPURCHASE_NOTIFY+", "+SECConstant.BusinessType.CAPITAL_REPURCHASE.PAYBACK_NOTIFY+" ,"+SECConstant.BusinessType.CAPITAL_REPURCHASE.INTEREST_PAYMENT+", "+SECConstant.BusinessType.CAPITAL_REPURCHASE.AVERAGE_NOTIFY+", "+SECConstant.BusinessType.CAPITAL_REPURCHASE.ACCEPT_NOTIFY+", "+SECConstant.BusinessType.CAPITAL_REPURCHASE.BREAK_NOTIFY+" ) ";
						strSql += " group by aa.id ) bbb";
						strSql += " where aaa.id = bbb.id and aaa.moneysegment = bbb.maxamount) d,";	
						strSql += " loan_approvalsetting e,loan_approvalitem f";
						strSql += " where d.approvalid = e.id and f.napprovalid=e.id and f.nlevel=1 and f.nuserid="+userId;
						strSql +=" ) ";
						
						//update
						strSql += " ) ss1,sec_transactionType tra where ss1.transactionTypeId = tra.id ";
						strSql +=" group by ss1.transactionTypeId,tra.name order by ss1.transactionTypeId ";
						
						System.out.println("业务通知单资产转让SQL^^^^^^^^^^^===="+strSql);
						
						strURL = "../craftbrother/notice/control/n071c015.jsp?strSuccessPageURL=../view/n071v014.jsp&strFailPageURL=../mywork/mywork.jsp&currencyId="+currencyId+"&officeId="+officeId+"&userId="+userId+"&strAction="+SECConstant.Actions.LINKSEARCH+"&transactionTypeId=";  //资产转让页面链接
					}
					break;
				
				}
				case SEC_CONTRACT:{					//合同
					if (actionTypeId == SECConstant.Actions.LINKSEARCH)  //资产转让
					{
						//update
						strSql=" select count(ss1.transactionTypeId) dataCount,ss1.transactionTypeId nodeId,avg(ss1.statusId) nodeStatusId,tra.name nodeName from ( ";
						
						strSql +=" ( SELECT c.*,-1 moneysegment,-1 approvalid from SEC_ApplyContract c"; 
						strSql +=",(select a.NAPPROVALCONTENTID from loan_approvaltracing a,(select NAPPROVALCONTENTID,max(ID) as ID from loan_approvaltracing group by NAPPROVALCONTENTID) b";
						strSql +=" where (a.NNEXTUSERID="+userId+" ";
						strSql +=" and a.nactionid="+Constant.ApprovalAction.CAPITAL_REPURCHASE_CONTRACT+" and a.nloantypeid = "+Constant.ApprovalLoanType.CAPITAL_REPURCHASE+" ";
						strSql +="  and a.NMODULEID="+Constant.ModuleType.CRAFTBROTHER+" and nstatusid="+Constant.RecordStatus.VALID+""; 
						strSql +=" and a.ID(+)= b.ID and a.NAPPROVALCONTENTID(+) = b.NAPPROVALCONTENTID )) d";
						strSql +=" where c.id =d.NAPPROVALCONTENTID and c.statusid="+SECConstant.ContractStatus.APPROVALING+"";
						strSql += " and c.transactiontypeid in ( "+SECConstant.BusinessType.CAPITAL_REPURCHASE.REPURCHASE_NOTIFY+", "+SECConstant.BusinessType.CAPITAL_REPURCHASE.AVERAGE_NOTIFY+", "+SECConstant.BusinessType.CAPITAL_REPURCHASE.BREAK_NOTIFY+" ) ";
						
						strSql +=" ) union ( ";
						    
						strSql += " select d.* from (";
						strSql += " select aaa.* from (";
						strSql += " select aa.*,rr.moneysegment,rr.approvalid from SEC_ApplyContract aa,loan_approvalrelation rr";
						strSql += " where rr.moduleid="+Constant.ModuleType.CRAFTBROTHER+" and aa.amount>rr.moneysegment and rr.officeid = "+officeId+" and rr.currencyid =" +currencyId+ " and aa.statusid="+SECConstant.ContractStatus.SUBMIT;
						strSql += " and rr.actionid = " + Constant.ApprovalAction.CAPITAL_REPURCHASE_CONTRACT ;
						strSql += " and rr.subloantypeid = " + Constant.ApprovalLoanType.CAPITAL_REPURCHASE ;
						strSql += " and aa.transactiontypeid in ( "+SECConstant.BusinessType.CAPITAL_REPURCHASE.REPURCHASE_NOTIFY+", "+SECConstant.BusinessType.CAPITAL_REPURCHASE.AVERAGE_NOTIFY+", "+SECConstant.BusinessType.CAPITAL_REPURCHASE.BREAK_NOTIFY+" ) ";
						strSql += " ) aaa,(";
						strSql += " select aa.id,max(rr.moneysegment) maxamount from SEC_ApplyContract aa,loan_approvalrelation rr";
						strSql += " where rr.moduleid="+Constant.ModuleType.CRAFTBROTHER+" and aa.amount>rr.moneysegment and rr.officeid = "+officeId+" and rr.currencyid =" +currencyId+ " and aa.statusid="+SECConstant.ContractStatus.SUBMIT;
						strSql += " and rr.actionid = " + Constant.ApprovalAction.CAPITAL_REPURCHASE_CONTRACT ;
						strSql += " and rr.subloantypeid = " + Constant.ApprovalLoanType.CAPITAL_REPURCHASE ;
						strSql += " and aa.transactiontypeid in ( "+SECConstant.BusinessType.CAPITAL_REPURCHASE.REPURCHASE_NOTIFY+", "+SECConstant.BusinessType.CAPITAL_REPURCHASE.AVERAGE_NOTIFY+", "+SECConstant.BusinessType.CAPITAL_REPURCHASE.BREAK_NOTIFY+" ) ";
						strSql += " group by aa.id ) bbb";
						strSql += " where aaa.id = bbb.id and aaa.moneysegment = bbb.maxamount) d,";	
						strSql += " loan_approvalsetting e,loan_approvalitem f";
						strSql += " where d.approvalid = e.id and f.napprovalid=e.id and f.nlevel=1 and f.nuserid="+userId;
						strSql +=" ) ";
						
						//update
						strSql += " ) ss1,sec_transactionType tra where ss1.transactionTypeId = tra.id ";
						strSql +=" group by ss1.transactionTypeId,tra.name order by ss1.transactionTypeId ";
						
						System.out.println("合同资产转让SQL^^^^^^^^^^^===="+strSql);
						
						strURL = "../craftbrother/contract/control/c071c104.jsp?strSuccessPageURL=../view/c071v103.jsp&strFailPageURL=../mywork/mywork.jsp&currencyId="+currencyId+"&officeId="+officeId+"&userId="+userId+"&strAction="+SECConstant.Actions.LINKSEARCH+"&transactionTypeId=";  //资产转让页面链接
					}
					//转贴现
					else if (actionTypeId == SECConstant.Actions.CHECKSEARCH)
					{
						long lSubLoanTypeID=23;
						//update
						strSql=" select count(ss1.ntypeid) dataCount,ss1.ntypeid nodeId,avg(ss1.nstatusid) nodeStatusId,tra.name nodeName from ( ";
						
				        strSql +=" ( SELECT aa.*,-1 moneysegment,-1 approvalid from Loan_ContractForm aa ";
				        strSql +=" ,(select a.NAPPROVALCONTENTID from loan_approvaltracing a,(select NAPPROVALCONTENTID,max(ID) as ID from loan_approvaltracing group by NAPPROVALCONTENTID) b";
				        strSql +=" where (a.NNEXTUSERID="+userId+" ";
				        strSql +=" and a.nactionid="+Constant.ApprovalAction.LOAN_CONTRACT+" and a.nloantypeid = "+lSubLoanTypeID+" ";
				        strSql +="  and a.NMODULEID="+Constant.ModuleType.CRAFTBROTHER+" and nstatusid="+Constant.RecordStatus.VALID+"";
				        strSql +=" and a.ID(+)= b.ID and a.NAPPROVALCONTENTID(+) = b.NAPPROVALCONTENTID )) d";
				        strSql +=" where aa.id =d.NAPPROVALCONTENTID and aa.nstatusid="+LOANConstant.ContractStatus.APPROVALING;
				        strSql +=" and aa.ntypeid="+LOANConstant.LoanType.ZTX+" and aa.nCurrencyID = "+currencyId+" and aa.nOfficeID = "+officeId+" ";
				        
				        strSql+=" ) union ( ";
				         
				        strSql += " (select d.* from (";
				        strSql += " select aaa.* from (";
				        strSql += " select aa.*,rr.moneysegment,rr.approvalid from Loan_ContractForm aa,loan_approvalrelation rr";
				        strSql += " where rr.moduleid="+Constant.ModuleType.CRAFTBROTHER+" and aa.mLoanAmount>rr.moneysegment and rr.currencyid =" +currencyId;
				        strSql += " and rr.actionid = " +Constant.ApprovalAction.LOAN_CONTRACT ;
				        strSql += " and rr.subloantypeid = "+lSubLoanTypeID ;    
				        strSql +=" and aa.ntypeid="+LOANConstant.LoanType.ZTX+" and aa.nstatusid ="+LOANConstant.ContractStatus.SUBMIT;
				        strSql += " and aa.nCurrencyID = "+currencyId+" and aa.nOfficeID = "+officeId+" ";
				        strSql += " ) aaa,(";
				        strSql += " select aa.id,max(rr.moneysegment) maxamount from Loan_ContractForm aa,loan_approvalrelation rr";
				        strSql += " where rr.moduleid="+Constant.ModuleType.CRAFTBROTHER+" and aa.mLoanAmount>rr.moneysegment and rr.currencyid =" +currencyId;
				        strSql += " and rr.actionid = " +Constant.ApprovalAction.LOAN_CONTRACT ;
				        strSql += " and rr.subloantypeid = "+lSubLoanTypeID ;            
				        strSql +=" and aa.ntypeid="+LOANConstant.LoanType.ZTX+" and aa.nstatusid ="+LOANConstant.ContractStatus.SUBMIT+" and aa.nCurrencyID = "+currencyId+" and aa.nOfficeID = "+officeId+" ";
				        strSql += " group by aa.id ) bbb";
				        strSql += " where aaa.id = bbb.id and aaa.moneysegment = bbb.maxamount) d,";	
				        strSql += " loan_approvalsetting e,loan_approvalitem f";
				        strSql += " where d.approvalid = e.id and f.napprovalid=e.id and f.nlevel=1 and f.nuserid="+userId+") )";
				        
						//update
						strSql += " ) ss1,sec_transactionType tra where ss1.ntypeid = tra.id ";
						strSql +=" group by ss1.ntypeid,tra.name order by ss1.ntypeid ";
				        
				        System.out.println("合同转贴现sql=="+strSql);
				        
				        strURL = "../transdiscountcontract/control/dc001-c.jsp?strSuccessPageURL=../view/dc002-v.jsp&strFailPageURL=../mywork/mywork.jsp&currencyId="+currencyId+"&officeId="+officeId+"&userId="+userId+"&queryPurpose=2&secTransactionTypeId=";  //转贴现页面链接
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
				
				while (rs.next())
				{
					node = new ColumnNode(null);
					
					node.setNodeId(rs.getLong("nodeId"));
					node.setNodeName(rs.getString("nodeName"));
					node.setNodeStatusId(rs.getLong("nodeStatusId"));
					node.setDataCount(rs.getLong("dataCount"));
					
					node.setNodeURL( strURL + rs.getLong("nodeId") );  //置入链接
					
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
