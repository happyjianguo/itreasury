/*
 * Created on 2004-5-25
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.securities.mywork.dataentity;

import com.iss.itreasury.securities.util.NameRef;
import com.iss.itreasury.securities.util.SECConstant;
import com.iss.itreasury.securities.mywork.dao.SEC_MyWorkDAO;
import com.iss.itreasury.system.approval.bizlogic.ApprovalBiz;
import com.iss.itreasury.util.*;
/**
 * @author lgwang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class TransactionURL {
	
	
	/**
	 * 根据交易类型和工作类型和操作类型获得链接地址
	 * @param transactionTypeId
	 * @param actionTypeId
	 * @param workTypeId
	 * @return
	 */
	public String getURL(long userId,long currencyId,long officeId,long transactionTypeId,long actionTypeId,int workTypeId,long statusId){
		String url = "";
		
		switch (workTypeId){
			case SEC_MyWorkDAO.SEC_APPLYFORM:{
				url = getApplyFormURL(userId,currencyId,officeId,transactionTypeId,actionTypeId,statusId);
				break;
			}
			case SEC_MyWorkDAO.SEC_DELIVERORDER:{
				url = getDeliveryOrderURL(userId,currencyId,officeId,transactionTypeId,actionTypeId,statusId);
				break;
			}
			case SEC_MyWorkDAO.SEC_NOTICE:{
				url = getNoticeFormURL(userId,currencyId,officeId,transactionTypeId,actionTypeId,statusId);
				break;
			}
			case SEC_MyWorkDAO.SEC_CONTRACT:{
				url = getApplyContractURL(userId,currencyId,officeId,transactionTypeId,actionTypeId,statusId);
				System.out.println("----getApplyContractURL");
				break;
			}
			case SEC_MyWorkDAO.SEC_PLAN:{
				url = getPlanURL(userId,currencyId,officeId,transactionTypeId,actionTypeId,statusId);
				break;
			}
		}
		
		return url;
	}
	
	/**
	 * 申请书
	 * @param transactionTypeId
	 * @param actionTypeId
	 * @return
	 */
	public String getApplyFormURL(long userId,long currencyId,long officeId,long transactionTypeId,long actionTypeId,long statusId){
		String url = "";
		if(actionTypeId == SECConstant.Actions.LINKSEARCH)
		{
			//url += "/NASApp/itreasury-securities/iTreasury-securities/securities/apply/control/a013c104.jsp?";
			url += "../apply/control/a013c104.jsp?";
			
		}
		else if(actionTypeId == SECConstant.Actions.CHECKSEARCH)
		{
			//url += "/NASApp/itreasury-securities/iTreasury-securities/securities/apply/control/a013c104.jsp?";
			url += "../apply/control/a013c104.jsp?";
			
		}
		
		url += "&fromMyWork=1";																	//确定从"我的工作"链接过来的标志位
		url += "&currencyId="+currencyId;														//币种
		url += "&firstsearch=yes";
		if (actionTypeId == SECConstant.Actions.LINKSEARCH){
			url += "&queryPurpose=1";			
		}
		else if (actionTypeId == SECConstant.Actions.CHECKSEARCH){
			url += "&queryPurpose=2";
		}
		//zpli mofify 2005-09-14
		url += "&approvalId="+NameRef.getApprovalIDByTransactionTypeID(transactionTypeId,1,officeId,currencyId);	//审批标识
		//url += "&approvalId="+NameRef.getApprovalIDByTransactionTypeID(transactionTypeId,1);	//审批标识
		
		url += "&strAction=" + SECConstant.Actions.LINKSEARCH;
		url += "&officeId="+officeId;															//办事处
		url += "&statusId="+statusId;															//交割单状态
		url += "&transactionTypeId="+transactionTypeId;											//交易类型
		url += "&pageLineCount=10";
		url += "&pageCount=1";
		url += "&pageNo=1";
		url += "&strFailPageURL=../../mywork/mywork.jsp";			//失败页面
		url += "&strSuccessPageURL=../view/";
				
		int businessTypeId = Integer.parseInt(String.valueOf(transactionTypeId).substring(0,2));
		
		switch ((int)businessTypeId){
			case (int)SECConstant.BusinessType.CAPITAL_IN_CREDIT_EXTENSION.ID:{					//资金拆入授信
				if(actionTypeId == SECConstant.Actions.LINKSEARCH)
				{
					url += "a011v107.jsp";
				}
				else if(actionTypeId == SECConstant.Actions.CHECKSEARCH )
				{
					url += "a011v108.jsp";
				}
				break;
			}
			case (int)SECConstant.BusinessType.CAPITAL_OUT_CREDIT_EXTENSION.ID:{				//资金拆出授信
				if(actionTypeId == SECConstant.Actions.LINKSEARCH)
				{
					url += "a011v107.jsp";
				}
				else if(actionTypeId == SECConstant.Actions.CHECKSEARCH )
				{
					url += "a011v108.jsp";
				}
				break;
			}
			case (int)SECConstant.BusinessType.CAPITAL_LANDING.ID:{								//资金拆借
				if(actionTypeId == SECConstant.Actions.LINKSEARCH)
				{
					url += "a013v105.jsp";
				}
				else if(actionTypeId == SECConstant.Actions.CHECKSEARCH )
				{
					url += "a013v106.jsp";
				}
				break;
			}
			case (int)SECConstant.BusinessType.STOCK_BID_ONLINE.ID:{							//股票一级网上申购	
				if(actionTypeId == SECConstant.Actions.LINKSEARCH)
				{
					url += "a016v105.jsp";
				}
				else if(actionTypeId == SECConstant.Actions.CHECKSEARCH )
				{
					url += "a016v106.jsp";
				}
				break;
			}
			case (int)SECConstant.BusinessType.STOCK_BID.ID:{									//股票一级网下申购
				if(actionTypeId == SECConstant.Actions.LINKSEARCH)
				{
					url += "a017v105.jsp";
				}
				else if(actionTypeId == SECConstant.Actions.CHECKSEARCH )
				{
					url += "a017v106.jsp";
				}
				break;
			}
			case (int)SECConstant.BusinessType.STOCK_TRANSACTION.ID:{							//股票二级
				if(actionTypeId == SECConstant.Actions.LINKSEARCH)
				{
					url += "a018v105.jsp";
				}
				else if(actionTypeId == SECConstant.Actions.CHECKSEARCH )
				{
					url += "a018v106.jsp";
				}
				break;
			}
			case (int)SECConstant.BusinessType.CENTRAL_BANK_NOTE_BID.ID:{						//央行票据一级 
				if(actionTypeId == SECConstant.Actions.LINKSEARCH)
				{
					url += "a021v105.jsp";
				}
				else if(actionTypeId == SECConstant.Actions.CHECKSEARCH )
				{
					url += "a021v106.jsp";
				}
				break;
			}
			case (int)SECConstant.BusinessType.CENTRAL_BANK_NOTE_TRANSACTION.ID:{				//央行票据二级 
				if(actionTypeId == SECConstant.Actions.LINKSEARCH)
				{
					url += "a022v105.jsp";
				}
				else if(actionTypeId == SECConstant.Actions.CHECKSEARCH )
				{
					url += "a022v106.jsp";
				}
				break;
			}
			case (int)SECConstant.BusinessType.BANK_BOND_REPURCHASE.ID:{						//银行间债券回购
				if(actionTypeId == SECConstant.Actions.LINKSEARCH)
				{
					url += "a026v105.jsp";
				}
				else if(actionTypeId == SECConstant.Actions.CHECKSEARCH )
				{
					url += "a026v106.jsp";
				}
				break;
			}
			case (int)SECConstant.BusinessType.EXCHANGECENTER_BOND_REPURCHASE.ID:{				//交易所债券回购 
				if(actionTypeId == SECConstant.Actions.LINKSEARCH)
				{
					url += "a027v105.jsp";
				}
				else if(actionTypeId == SECConstant.Actions.CHECKSEARCH )
				{
					url += "a027v106.jsp";
				}
				break;
			}
			case (int)SECConstant.BusinessType.BANK_NATIONAL_BOND_BID.ID:{						//银行间国债一级 
				if(actionTypeId == SECConstant.Actions.LINKSEARCH)
				{
					url += "a031v105.jsp";
				}
				else if(actionTypeId == SECConstant.Actions.CHECKSEARCH )
				{
					url += "a031v106.jsp";
				}
				break;
			}
			case (int)SECConstant.BusinessType.BANK_NATIONAL_BOND_TRANSACTION.ID:{				//银行间国债二级 
				if(actionTypeId == SECConstant.Actions.LINKSEARCH)
				{
					url += "a032v105.jsp";
				}
				else if(actionTypeId == SECConstant.Actions.CHECKSEARCH )
				{
					url += "a032v106.jsp";
				}
				break;
			}
			case (int)SECConstant.BusinessType.EXCHANGECENTER_NATIONAL_BOND_BID.ID:{			//交易所国债一级 
				if(actionTypeId == SECConstant.Actions.LINKSEARCH)
				{
					url += "a033v105.jsp";
				}
				else if(actionTypeId == SECConstant.Actions.CHECKSEARCH )
				{
					url += "a033v106.jsp";
				}
				break;
			}
			case (int)SECConstant.BusinessType.EXCHANGECENTER_NATIONAL_BOND_TRANSACTION.ID:{	//交易所国债二级
				if(actionTypeId == SECConstant.Actions.LINKSEARCH)
				{
					url += "a034v105.jsp";
				}
				else if(actionTypeId == SECConstant.Actions.CHECKSEARCH )
				{
					url += "a034v106.jsp";
				}
				break;
			}
			case (int)SECConstant.BusinessType.FINANCIAL_BOND_BID.ID:{							//金融债一级
				if(actionTypeId == SECConstant.Actions.LINKSEARCH)
				{
					url += "a036v105.jsp";
				}
				else if(actionTypeId == SECConstant.Actions.CHECKSEARCH )
				{
					url += "a036v106.jsp";
				}
				break;
			}
			case (int)SECConstant.BusinessType.FINANCIAL_BOND_TRANSACTION.ID:{					//金融债二级
				if(actionTypeId == SECConstant.Actions.LINKSEARCH)
				{
					url += "a037v105.jsp";
				}
				else if(actionTypeId == SECConstant.Actions.CHECKSEARCH )
				{
					url += "a037v106.jsp";
				}
				break;
			}
			case (int)SECConstant.BusinessType.POLICY_RELATED_FINANCIAL_BOND_BID.ID:{			//政策性金融债一级 
				if(actionTypeId == SECConstant.Actions.LINKSEARCH)
				{
					url += "a041v105.jsp";
				}
				else if(actionTypeId == SECConstant.Actions.CHECKSEARCH )
				{
					url += "a041v106.jsp";
				}
				break;
			}
			case (int)SECConstant.BusinessType.POLICY_RELATED_FINANCIAL_BOND_TRANSACTION.ID:{	//政策性金融债二级
				if(actionTypeId == SECConstant.Actions.LINKSEARCH)
				{
					url += "a042v105.jsp";
				}
				else if(actionTypeId == SECConstant.Actions.CHECKSEARCH )
				{
					url += "a042v106.jsp";
				}
				break;
			}
			case (int)SECConstant.BusinessType.ENTERPRISE_BOND_BID.ID:{							//企业债一级
				if(actionTypeId == SECConstant.Actions.LINKSEARCH)
				{
					url += "a046v105.jsp";
				}
				else if(actionTypeId == SECConstant.Actions.CHECKSEARCH )
				{
					url += "a046v106.jsp";
				}
				break;
			}
			case (int)SECConstant.BusinessType.ENTERPRISE_BOND_TRANSACTION.ID:{					//企业债二级
				if(actionTypeId == SECConstant.Actions.LINKSEARCH)
				{
					url += "a047v105.jsp";
				}
				else if(actionTypeId == SECConstant.Actions.CHECKSEARCH )
				{
					url += "a047v106.jsp";
				}
				break;
			}
			case (int)SECConstant.BusinessType.TRANSFORMABLE_BOND_BID_ONLINE.ID:{				//可转债一级网上申购 
				if(actionTypeId == SECConstant.Actions.LINKSEARCH)
				{
					url += "a051v105.jsp";
				}
				else if(actionTypeId == SECConstant.Actions.CHECKSEARCH )
				{
					url += "a051v106.jsp";
				}
				break;
			}
			case (int)SECConstant.BusinessType.TRANSFORMABLE_BOND_BID.ID:{						//可转债一级网下申购
				if(actionTypeId == SECConstant.Actions.LINKSEARCH)
				{
					url += "a052v105.jsp";
				}
				else if(actionTypeId == SECConstant.Actions.CHECKSEARCH )
				{
					url += "a052v106.jsp";
				}
				break;
			}
			case (int)SECConstant.BusinessType.TRANSFORMABLE_BOND_TRANSACTION.ID:{				//可转债二级
				if(actionTypeId == SECConstant.Actions.LINKSEARCH)
				{
					url += "a053v105.jsp";
				}
				else if(actionTypeId == SECConstant.Actions.CHECKSEARCH )
				{
					url += "a053v106.jsp";
				}
				break;
			}
			case (int)SECConstant.BusinessType.DEBT_TO_EQUITY.ID:{								//债转股 
				if(actionTypeId == SECConstant.Actions.LINKSEARCH)
				{
					url += "a054v105.jsp";
				}
				else if(actionTypeId == SECConstant.Actions.CHECKSEARCH )
				{
					url += "a054v106.jsp";
				}
				break;
			}
			case (int)SECConstant.BusinessType.ENCLOSED_FUND_BID_ONLINE.ID:{					//封闭式基金一级网上申购
				if(actionTypeId == SECConstant.Actions.LINKSEARCH)
				{
					url += "a056v105.jsp";
				}
				else if(actionTypeId == SECConstant.Actions.CHECKSEARCH )
				{
					url += "a056v106.jsp";
				}
				break;
			}
			case (int)SECConstant.BusinessType.ENCLOSED_FUND_BID.ID:{							//封闭式基金一级网下申购
				if(actionTypeId == SECConstant.Actions.LINKSEARCH)
				{
					url += "a057v105.jsp";
				}
				else if(actionTypeId == SECConstant.Actions.CHECKSEARCH )
				{
					url += "a057v106.jsp";
				}
				break;
			}
			case (int)SECConstant.BusinessType.ENCLOSED_FUND_TRANSACTION.ID:{					//封闭式基金二级
				if(actionTypeId == SECConstant.Actions.LINKSEARCH)
				{
					url += "a058v105.jsp";
				}
				else if(actionTypeId == SECConstant.Actions.CHECKSEARCH )
				{
					url += "a058v106.jsp";
				}
				break;
			}
			case (int)SECConstant.BusinessType.MUTUAL_FUND_SUBSCRIBE.ID:{						//开放式基金一级认购
				if(actionTypeId == SECConstant.Actions.LINKSEARCH)
				{
					url += "a061v105.jsp";
				}
				else if(actionTypeId == SECConstant.Actions.CHECKSEARCH )
				{
					url += "a061v106.jsp";
				}
				break;
			}
			case (int)SECConstant.BusinessType.MUTUAL_FUND_BID.ID:{								//开放式基金二级申购
				if(actionTypeId == SECConstant.Actions.LINKSEARCH)
				{
					url += "a062v105.jsp";
				}
				else if(actionTypeId == SECConstant.Actions.CHECKSEARCH )
				{
					url += "a062v106.jsp";
				}
				break;
			}
			case (int)SECConstant.BusinessType.MUTUAL_FUND_REDEEM.ID:{							//开放式基金二级赎回
				if(actionTypeId == SECConstant.Actions.LINKSEARCH)
				{
					url += "a063v105.jsp";
				}
				else if(actionTypeId == SECConstant.Actions.CHECKSEARCH )
				{
					url += "a063v106.jsp";
				}
				break;
			}
			/*
			case (int)SECConstant.BusinessType.MUTUAL_FUND_MELON_CUTTING.ID:{					//开放式基金分红
				if(actionTypeId == SECConstant.Actions.LINKSEARCH)
				{
					url += "";
				}
				else if(actionTypeId == SECConstant.Actions.CHECKSEARCH )
				{
					url += "";
				}
				break;
			}
			case (int)SECConstant.BusinessType.ENTRUSTED_FINANCING.ID:{							//受托理财 
				if(actionTypeId == SECConstant.Actions.LINKSEARCH)
				{
					url += "";
				}
				else if(actionTypeId == SECConstant.Actions.CHECKSEARCH )
				{
					url += "";
				}
				break;
			}
			case (int)SECConstant.BusinessType.STOCK_INVESTMENT.ID:{							//股权投资 
				if(actionTypeId == SECConstant.Actions.LINKSEARCH)
				{
					url += "";
				}
				else if(actionTypeId == SECConstant.Actions.CHECKSEARCH )
				{
					url += "";
				}
				break;
			}
			case (int)SECConstant.BusinessType.INSURANCE.ID:{									//保险代理
				if(actionTypeId == SECConstant.Actions.LINKSEARCH)
				{
					url += "";
				}
				else if(actionTypeId == SECConstant.Actions.CHECKSEARCH )
				{
					url += "";
				}
				break;
			}
			case (int)SECConstant.BusinessType.CAPITAL_TRANSFER.ID:{							//资金划拨
				if(actionTypeId == SECConstant.Actions.LINKSEARCH)
				{
					url += "";
				}
				else if(actionTypeId == SECConstant.Actions.CHECKSEARCH )
				{
					url += "";
				}
				break;
			}
			case (int)SECConstant.BusinessType.INTEREST_SETTLEMENT.ID:{							//资金利息结算
				if(actionTypeId == SECConstant.Actions.LINKSEARCH)
				{
					url += "";
				}
				else if(actionTypeId == SECConstant.Actions.CHECKSEARCH )
				{
					url += "";
				}
				break;
			}
			case (int)SECConstant.BusinessType.OTHER_CAPITAL_SETTLEMENT.ID:{					//其它资金往来  
				if(actionTypeId == SECConstant.Actions.LINKSEARCH)
				{
					url += "";
				}
				else if(actionTypeId == SECConstant.Actions.CHECKSEARCH )
				{
					url += "";
				}
				break;
			}
			case (int)SECConstant.BusinessType.SECURITIES_TRANSFER.ID:{							//证券划转
				if(actionTypeId == SECConstant.Actions.LINKSEARCH)
				{
					url += "";
				}
				else if(actionTypeId == SECConstant.Actions.CHECKSEARCH )
				{
					url += "";
				}
				break;
			}
			case (int)SECConstant.BusinessType.CARRY_COST.ID:{									//结转成本
				if(actionTypeId == SECConstant.Actions.LINKSEARCH)
				{
					url += "";
				}
				else if(actionTypeId == SECConstant.Actions.CHECKSEARCH )
				{
					url += "";
				}
				break;
			}
			*/
			//--------------------
			case (int)SECConstant.BusinessType.CAPITAL_REPURCHASE.ID:{	                        //资产回购
				if(actionTypeId == SECConstant.Actions.LINKSEARCH)
				{
					//url = "/NASApp/itreasury-securities/iTreasury-securities/securities/apply/control/a071c105.jsp?";
					url  = "/NASApp/iTreasury-securities/securities/apply/control/a071c105.jsp?";
					
					url += "&fromMyWork=1";																	//确定从"我的工作"链接过来的标志位
					url += "&currencyId="+currencyId;														//币种
					url += "&firstsearch=yes";
					url += "&queryPurpose=1";	
					url += "&strAction=" + SECConstant.Actions.LINKSEARCH;
					url += "&officeId="+officeId;															//办事处
					url += "&statusId="+statusId;															//交割单状态
					url += "&transactionTypeId="+transactionTypeId;											//交易类型
					url += "&pageLineCount=10";
					url += "&pageCount=1";
					url += "&pageNo=1";
					url += "&strFailPageURL=/NASApp/iTreasury-securities/securities/mywork/mywork.jsp";			//失败页面
					url += "&strSuccessPageURL=/NASApp/iTreasury-securities/securities/apply/view/";				//成功页面
					url += "a071v106.jsp";
					
				}
				else if(actionTypeId == SECConstant.Actions.CHECKSEARCH )
				{
					//url = "/NASApp/itreasury-securities/iTreasury-securities/securities/apply/control/a071c105.jsp?";
					url  = "/NASApp/iTreasury-securities/securities/apply/control/a071c105.jsp?";
					
					url += "&fromMyWork=1";																	//确定从"我的工作"链接过来的标志位
					url += "&currencyId="+currencyId;														//币种
					url += "&firstsearch=yes";
					url += "&queryPurpose=2";
					url += "&strAction=" + SECConstant.Actions.LINKSEARCH;
					url += "&officeId="+officeId;															//办事处
					url += "&statusId="+statusId;															//交割单状态
					url += "&transactionTypeId="+transactionTypeId;											//交易类型
					url += "&pageLineCount=10";
					url += "&pageCount=1";
					url += "&pageNo=1";
					url += "&strFailPageURL=/NASApp/iTreasury-securities/securities/mywork/mywork.jsp";			//失败页面
					url += "&strSuccessPageURL=/NASApp/iTreasury-securities/securities/apply/view/";
					url += "a071v107.jsp";
				}
				break;
			}
			case (int)SECConstant.BusinessType.BOND_UNDERWRITING.ID:{							//债券承销
				if(actionTypeId == SECConstant.Actions.LINKSEARCH)
				{
					//url = "/NASApp/itreasury-securities/iTreasury-securities/securities/apply/control/a071c105.jsp?";
					url  = "/NASApp/iTreasury-securities/securities/apply/control/a071c105.jsp?";
					
					url += "&fromMyWork=1";																	//确定从"我的工作"链接过来的标志位
					url += "&currencyId="+currencyId;														//币种
					url += "&firstsearch=yes";
					url += "&queryPurpose=1";	
					url += "&strAction=" + SECConstant.Actions.LINKSEARCH;
					url += "&officeId="+officeId;															//办事处
					url += "&statusId="+statusId;															//交割单状态
					url += "&transactionTypeId="+transactionTypeId;											//交易类型
					url += "&pageLineCount=10";
					url += "&pageCount=1";
					url += "&pageNo=1";
					url += "&strFailPageURL=/NASApp/iTreasury-securities/securities/mywork/mywork.jsp";			//失败页面
					url += "&strSuccessPageURL=/NASApp/iTreasury-securities/securities/apply/view/";				//成功页面
					url += "a081v106.jsp";
					
				}
				else if(actionTypeId == SECConstant.Actions.CHECKSEARCH )
				{
					//url = "/NASApp/itreasury-securities/iTreasury-securities/securities/apply/control/a071c105.jsp?";
					url  = "/NASApp/iTreasury-securities/securities/apply/control/a071c105.jsp?";
					
					url += "&fromMyWork=1";																	//确定从"我的工作"链接过来的标志位
					url += "&currencyId="+currencyId;														//币种
					url += "&firstsearch=yes";
					url += "&queryPurpose=2";
					url += "&strAction=" + SECConstant.Actions.LINKSEARCH;
					url += "&officeId="+officeId;															//办事处
					url += "&statusId="+statusId;															//交割单状态
					url += "&transactionTypeId="+transactionTypeId;											//交易类型
					url += "&pageLineCount=10";
					url += "&pageCount=1";
					url += "&pageNo=1";
					url += "&strFailPageURL=/NASApp/iTreasury-securities/securities/mywork/mywork.jsp";			//失败页面
					url += "&strSuccessPageURL=/NASApp/iTreasury-securities/securities/apply/view/";
					url += "a081v107.jsp";
				}
				break;
			}
			case (int)SECConstant.BusinessType.ENTRUST_FINANCING.ID:{							//委托理财
				if(actionTypeId == SECConstant.Actions.LINKSEARCH)
				{
					//url = "/NASApp/itreasury-securities/iTreasury-securities/securities/apply/control/a071c105.jsp?";
					url  = "/NASApp/iTreasury-securities/securities/apply/control/a071c105.jsp?";
					
					url += "&fromMyWork=1";																	//确定从"我的工作"链接过来的标志位
					url += "&currencyId="+currencyId;														//币种
					url += "&firstsearch=yes";
					url += "&queryPurpose=1";	
					url += "&strAction=" + SECConstant.Actions.LINKSEARCH;
					url += "&officeId="+officeId;															//办事处
					url += "&statusId="+statusId;															//交割单状态
					url += "&transactionTypeId="+transactionTypeId;											//交易类型
					url += "&pageLineCount=10";
					url += "&pageCount=1";
					url += "&pageNo=1";
					url += "&strFailPageURL=/NASApp/iTreasury-securities/securities/mywork/mywork.jsp";			//失败页面
					url += "&strSuccessPageURL=/NASApp/iTreasury-securities/securities/apply/view/";				//成功页面
					url += "a073v106.jsp";
					
				}
				else if(actionTypeId == SECConstant.Actions.CHECKSEARCH )
				{
					//url = "/NASApp/itreasury-securities/iTreasury-securities/securities/apply/control/a071c105.jsp?";
					url  = "/NASApp/iTreasury-securities/securities/apply/control/a071c105.jsp?";
					
					url += "&fromMyWork=1";																	//确定从"我的工作"链接过来的标志位
					url += "&currencyId="+currencyId;														//币种
					url += "&firstsearch=yes";
					url += "&queryPurpose=2";
					url += "&strAction=" + SECConstant.Actions.LINKSEARCH;
					url += "&officeId="+officeId;															//办事处
					url += "&statusId="+statusId;															//交割单状态
					url += "&transactionTypeId="+transactionTypeId;											//交易类型
					url += "&pageLineCount=10";
					url += "&pageCount=1";
					url += "&pageNo=1";
					url += "&strFailPageURL=/NASApp/iTreasury-securities/securities/mywork/mywork.jsp";			//失败页面
					url += "&strSuccessPageURL=/NASApp/iTreasury-securities/securities/apply/view/";
					url += "a073v107.jsp";
				}
				break;
			}
			case (int)SECConstant.BusinessType.FOREIGN_CURRENCY_INVESTMENT.ID:{					//结构性投资 
				if(actionTypeId == SECConstant.Actions.LINKSEARCH)
				{
					//url = "/NASApp/itreasury-securities/iTreasury-securities/securities/apply/control/a071c105.jsp?";
					url  = "/NASApp/iTreasury-securities/securities/apply/control/a071c105.jsp?";
					
					url += "&fromMyWork=1";																	//确定从"我的工作"链接过来的标志位
					//url += "&currencyId="+currencyId;														//币种
					url += "&firstsearch=yes";
					url += "&queryPurpose=1";	
					url += "&strAction=" + SECConstant.Actions.LINKSEARCH;
					url += "&officeId="+officeId;															//办事处
					url += "&statusId="+statusId;															//交割单状态
					url += "&transactionTypeId="+transactionTypeId;											//交易类型
					url += "&pageLineCount=10";
					url += "&pageCount=1";
					url += "&pageNo=1";
					url += "&strFailPageURL=/NASApp/iTreasury-securities/securities/mywork/mywork.jsp";			//失败页面
					url += "&strSuccessPageURL=/NASApp/iTreasury-securities/securities/apply/view/";				//成功页面
					url += "a077v106.jsp";
					
				}
				else if(actionTypeId == SECConstant.Actions.CHECKSEARCH )
				{
					//url = "/NASApp/itreasury-securities/iTreasury-securities/securities/apply/control/a071c105.jsp?";
					url  = "/NASApp/iTreasury-securities/securities/apply/control/a071c105.jsp?";
					
					url += "&fromMyWork=1";																	//确定从"我的工作"链接过来的标志位
					url += "&currencyId="+currencyId;														//币种
					url += "&firstsearch=yes";
					url += "&queryPurpose=2";
					url += "&strAction=" + SECConstant.Actions.LINKSEARCH;
					url += "&officeId="+officeId;															//办事处
					url += "&statusId="+statusId;															//交割单状态
					url += "&transactionTypeId="+transactionTypeId;											//交易类型
					url += "&pageLineCount=10";
					url += "&pageCount=1";
					url += "&pageNo=1";
					url += "&strFailPageURL=/NASApp/iTreasury-securities/securities/mywork/mywork.jsp";			//失败页面
					url += "&strSuccessPageURL=/NASApp/iTreasury-securities/securities/apply/view/";
					url += "a077v107.jsp";
				}
				break;
			}
			case (int)SECConstant.BusinessType.FUND_TRANSFER.ID:{                               //基金划转
				if(actionTypeId == SECConstant.Actions.LINKSEARCH)
				{
					//url = "/NASApp/itreasury-securities/iTreasury-securities/securities/apply/control/a013c101.jsp?";
					url  = "/NASApp/iTreasury-securities/securities/apply/control/a013c104.jsp?";
					
					url += "&fromMyWork=1";																	//确定从"我的工作"链接过来的标志位
					url += "&currencyId="+currencyId;														//币种
					url += "&firstsearch=yes";
					url += "&queryPurpose=1";	
					url += "&strAction=" + SECConstant.Actions.LINKSEARCH;
					url += "&officeId="+officeId;															//办事处
					url += "&statusId="+statusId;															//交割单状态
					url += "&transactionTypeId="+transactionTypeId;											//交易类型
					url += "&pageLineCount=10";
					url += "&pageCount=1";
					url += "&pageNo=1";
					url += "&strFailPageURL=/NASApp/iTreasury-securities/securities/mywork/mywork.jsp";			//失败页面
					url += "&strSuccessPageURL=/NASApp/iTreasury-securities/securities/apply/view/";				//成功页面
					url += "a092v105.jsp";
				}
				else if(actionTypeId == SECConstant.Actions.CHECKSEARCH)
				{
					//url = "/NASApp/itreasury-securities/iTreasury-securities/securities/apply/control/a013c104.jsp?";
					url  = "/NASApp/iTreasury-securities/securities/apply/control/a013c104.jsp?";
					
					url += "&fromMyWork=1";																	//确定从"我的工作"链接过来的标志位
					url += "&currencyId="+currencyId;														//币种
					url += "&firstsearch=yes";
					url += "&queryPurpose=2";
					url += "&strAction=" + SECConstant.Actions.LINKSEARCH;
					url += "&officeId="+officeId;															//办事处
					url += "&statusId="+statusId;															//交割单状态
					url += "&transactionTypeId="+transactionTypeId;											//交易类型
					url += "&pageLineCount=10";
					url += "&pageCount=1";
					url += "&pageNo=1";
					url += "&strFailPageURL=/NASApp/iTreasury-securities/securities/mywork/mywork.jsp";			//失败页面
					url += "&strSuccessPageURL=/NASApp/iTreasury-securities/securities/apply/view/";
					url += "a092v106.jsp";
				}	
				break;                      
			}
		}
		
		return url;
	}
	/**
	 * 交割单
	 * @param transactionTypeId
	 * @param actionTypeId
	 * @return
	 */
	public String getDeliveryOrderURL(long userId,long currencyId,long officeId,long transactionTypeId,long actionTypeId,long statusId){
		//String url = "/NASApp/itreasury-securities/iTreasury-securities/securities/deliveryorder/control/d013c003.jsp?";
		String url = "../deliveryorder/control/d013c003.jsp?";
		
		
		if (actionTypeId == SECConstant.Actions.LINKSEARCH){									//录入人
			url += "secInputUserId="+userId;
		}
		else if (actionTypeId == SECConstant.Actions.CHECKSEARCH){								//复核人
			url += "secCheckUserId="+userId;
		}
		
		url += "&fromMyWork=1";																	//确定从"我的工作"链接过来的标志位
		url += "&currencyId="+currencyId;														//币种
		url += "&officeId="+officeId;															//办事处
		url += "&secDeliveryOrderStatusId="+statusId;											//交割单状态
		url += "&secTransactionTypeId="+transactionTypeId;										//交易类型
		url += "&strFailPageURL=../../mywork/mywork.jsp";			//失败页面
		url += "&strSuccessPageURL=../view/";		//成功页面
		
		
		int businessTypeId = Integer.parseInt(String.valueOf(transactionTypeId).substring(0,2));

		switch ((int)businessTypeId){
			case (int)SECConstant.BusinessType.CAPITAL_LANDING.ID:{								//资金拆借
				if (actionTypeId == SECConstant.Actions.LINKSEARCH){
					
					url += "d013v006.jsp";
				}
				else if (actionTypeId == SECConstant.Actions.CHECKSEARCH){
					url += "d013v008.jsp";
				}
				break;
			}
			case (int)SECConstant.BusinessType.STOCK_BID_ONLINE.ID:{							//股票一级网上申购	
				if (actionTypeId == SECConstant.Actions.LINKSEARCH){
					url += "d016v006.jsp";
				}
				else if (actionTypeId == SECConstant.Actions.CHECKSEARCH){
					url += "d016v008.jsp";
				}
				break;
			}
			case (int)SECConstant.BusinessType.STOCK_BID.ID:{									//股票一级网下申购
				if (actionTypeId == SECConstant.Actions.LINKSEARCH){
					url += "d017v006.jsp";
				}
				else if (actionTypeId == SECConstant.Actions.CHECKSEARCH){
					url += "d017v008.jsp";
				}
				break;
			}
			case (int)SECConstant.BusinessType.STOCK_TRANSACTION.ID:{							//股票二级
				if (actionTypeId == SECConstant.Actions.LINKSEARCH){
					url += "d018v006.jsp";
				}
				else if (actionTypeId == SECConstant.Actions.CHECKSEARCH){
					url += "d018v008.jsp";
				}
				break;
			}
			case (int)SECConstant.BusinessType.CENTRAL_BANK_NOTE_BID.ID:{						//央行票据一级 
				if (actionTypeId == SECConstant.Actions.LINKSEARCH){
					url += "d021v004.jsp";
				}
				else if (actionTypeId == SECConstant.Actions.CHECKSEARCH){
					url += "d021v006.jsp";
				}
				break;
			}
			case (int)SECConstant.BusinessType.CENTRAL_BANK_NOTE_TRANSACTION.ID:{				//央行票据二级 
				if (actionTypeId == SECConstant.Actions.LINKSEARCH){
					url += "d022v005.jsp";
				}
				else if (actionTypeId == SECConstant.Actions.CHECKSEARCH){
					url += "d022v007.jsp";
				}
				break;
			}
			case (int)SECConstant.BusinessType.BANK_BOND_REPURCHASE.ID:{						//银行间债券回购
				if (actionTypeId == SECConstant.Actions.LINKSEARCH){
					url += "d026v006.jsp";
				}
				else if (actionTypeId == SECConstant.Actions.CHECKSEARCH){
					url += "d026v008.jsp";
				}
				break;
			}
			case (int)SECConstant.BusinessType.EXCHANGECENTER_BOND_REPURCHASE.ID:{				//交易所债券回购 
				if (actionTypeId == SECConstant.Actions.LINKSEARCH){
					url += "d027v006.jsp";
				}
				else if (actionTypeId == SECConstant.Actions.CHECKSEARCH){
					url += "d027v008.jsp";
				}
				break;
			}
			case (int)SECConstant.BusinessType.BANK_NATIONAL_BOND_BID.ID:{						//银行间国债一级 
				if (actionTypeId == SECConstant.Actions.LINKSEARCH){
					url += "d031v004.jsp";
				}
				else if (actionTypeId == SECConstant.Actions.CHECKSEARCH){
					url += "d031v006.jsp";
				}
				break;
			}
			case (int)SECConstant.BusinessType.BANK_NATIONAL_BOND_TRANSACTION.ID:{				//银行间国债二级 
				if (actionTypeId == SECConstant.Actions.LINKSEARCH){
					url += "d032v005.jsp";
				}
				else if (actionTypeId == SECConstant.Actions.CHECKSEARCH){
					url += "d032v007.jsp";
				}
				break;
			}
			case (int)SECConstant.BusinessType.EXCHANGECENTER_NATIONAL_BOND_BID.ID:{			//交易所国债一级
				if (actionTypeId == SECConstant.Actions.LINKSEARCH){
					url += "d033v006.jsp";
				}
				else if (actionTypeId == SECConstant.Actions.CHECKSEARCH){
					url += "d033v008.jsp";
				}
				break;
			}
			case (int)SECConstant.BusinessType.EXCHANGECENTER_NATIONAL_BOND_TRANSACTION.ID:{	//交易所国债二级
				if (actionTypeId == SECConstant.Actions.LINKSEARCH){
					url += "d034v005.jsp";
				}
				else if (actionTypeId == SECConstant.Actions.CHECKSEARCH){
					url += "d034v007.jsp";
				}
				break;
			}
			case (int)SECConstant.BusinessType.FINANCIAL_BOND_BID.ID:{							//金融债一级
				if (actionTypeId == SECConstant.Actions.LINKSEARCH){
					url += "d036v004.jsp";
				}
				else if (actionTypeId == SECConstant.Actions.CHECKSEARCH){
					url += "d036v006.jsp";
				}
				break;
			}
			case (int)SECConstant.BusinessType.FINANCIAL_BOND_TRANSACTION.ID:{					//金融债二级
				if (actionTypeId == SECConstant.Actions.LINKSEARCH){
					url += "d037v005.jsp";
				}
				else if (actionTypeId == SECConstant.Actions.CHECKSEARCH){
					url += "d037v007.jsp";
				}
				break;
			}
			case (int)SECConstant.BusinessType.POLICY_RELATED_FINANCIAL_BOND_BID.ID:{			//政策性金融债一级 
				if (actionTypeId == SECConstant.Actions.LINKSEARCH){
					url += "d041v004.jsp";
				}
				else if (actionTypeId == SECConstant.Actions.CHECKSEARCH){
					url += "d041v006.jsp";
				}
				break;
			}
			case (int)SECConstant.BusinessType.POLICY_RELATED_FINANCIAL_BOND_TRANSACTION.ID:{	//政策性金融债二级
				if (actionTypeId == SECConstant.Actions.LINKSEARCH){
					url += "d042v005.jsp";
				}
				else if (actionTypeId == SECConstant.Actions.CHECKSEARCH){
					url += "d042v007.jsp";
				}
				break;
			}
			case (int)SECConstant.BusinessType.ENTERPRISE_BOND_BID.ID:{							//企业债一级
				if (actionTypeId == SECConstant.Actions.LINKSEARCH){
					url += "d046v006.jsp";
				}
				else if (actionTypeId == SECConstant.Actions.CHECKSEARCH){
					url += "d046v008.jsp";
				}
				break;
			}
			case (int)SECConstant.BusinessType.ENTERPRISE_BOND_TRANSACTION.ID:{					//企业债二级
				if (actionTypeId == SECConstant.Actions.LINKSEARCH){
					url += "d047v006.jsp";
				}
				else if (actionTypeId == SECConstant.Actions.CHECKSEARCH){
					url += "d047v008.jsp";
				}
				break;
			}
			case (int)SECConstant.BusinessType.TRANSFORMABLE_BOND_BID_ONLINE.ID:{				//可转债一级网上申购
				if (actionTypeId == SECConstant.Actions.LINKSEARCH){
					url += "d051v006.jsp";
				}
				else if (actionTypeId == SECConstant.Actions.CHECKSEARCH){
					url += "d051v008.jsp";
				}
				break;
			}
			case (int)SECConstant.BusinessType.TRANSFORMABLE_BOND_BID.ID:{						//可转债一级网下申购
				if (actionTypeId == SECConstant.Actions.LINKSEARCH){
					url += "d052v006.jsp";
				}
				else if (actionTypeId == SECConstant.Actions.CHECKSEARCH){
					url += "d052v008.jsp";
				}
				break;
			}
			case (int)SECConstant.BusinessType.TRANSFORMABLE_BOND_TRANSACTION.ID:{				//可转债二级
				if (actionTypeId == SECConstant.Actions.LINKSEARCH){
					url += "d053v006.jsp";
				}
				else if (actionTypeId == SECConstant.Actions.CHECKSEARCH){
					url += "d053v008.jsp";
				}
				break;
			}
			case (int)SECConstant.BusinessType.DEBT_TO_EQUITY.ID:{								//债转股 
				if (actionTypeId == SECConstant.Actions.LINKSEARCH){
					url += "d054v006.jsp";
				}
				else if (actionTypeId == SECConstant.Actions.CHECKSEARCH){
					url += "d054v008.jsp";
				}
				break;
			}
			case (int)SECConstant.BusinessType.ENCLOSED_FUND_BID_ONLINE.ID:{					//封闭式基金一级网上申购
				if (actionTypeId == SECConstant.Actions.LINKSEARCH){
					url += "d056v006.jsp";
				}
				else if (actionTypeId == SECConstant.Actions.CHECKSEARCH){
					url += "d056v008.jsp";
				}
				break;
			}
			case (int)SECConstant.BusinessType.ENCLOSED_FUND_BID.ID:{							//封闭式基金一级网下申购
				if (actionTypeId == SECConstant.Actions.LINKSEARCH){
					url += "d057v006.jsp";
				}
				else if (actionTypeId == SECConstant.Actions.CHECKSEARCH){
					url += "d057v008.jsp";	
				}
				break;
			}
			case (int)SECConstant.BusinessType.ENCLOSED_FUND_TRANSACTION.ID:{					//封闭式基金二级
				if (actionTypeId == SECConstant.Actions.LINKSEARCH){
					url += "d058v006.jsp";
				}
				else if (actionTypeId == SECConstant.Actions.CHECKSEARCH){
					url += "d058v008.jsp";
				}
				break;
			}
			case (int)SECConstant.BusinessType.MUTUAL_FUND_SUBSCRIBE.ID:{						//开放式基金一级认购
				if (actionTypeId == SECConstant.Actions.LINKSEARCH){
					url += "d061v006.jsp";
				}
				else if (actionTypeId == SECConstant.Actions.CHECKSEARCH){
					url += "d061v008.jsp";
				}
				break;
			}
			case (int)SECConstant.BusinessType.MUTUAL_FUND_BID.ID:{								//开放式基金二级申购
				if (actionTypeId == SECConstant.Actions.LINKSEARCH){
					url += "d062v006.jsp";
				}
				else if (actionTypeId == SECConstant.Actions.CHECKSEARCH){
					url += "d062v008.jsp";
				}
				break;
			}
			case (int)SECConstant.BusinessType.MUTUAL_FUND_REDEEM.ID:{							//开放式基金二级赎回
				if (actionTypeId == SECConstant.Actions.LINKSEARCH){
					url += "d063v006.jsp";
				}
				else if (actionTypeId == SECConstant.Actions.CHECKSEARCH){
					url += "d063v008.jsp";
				}
				break;
			}
			case (int)SECConstant.BusinessType.MUTUAL_FUND_MELON_CUTTING.ID:{					//开放式基金分红
				if (actionTypeId == SECConstant.Actions.LINKSEARCH){
					url += "d064v003.jsp";
				}
				else if (actionTypeId == SECConstant.Actions.CHECKSEARCH){
					url += "d064v005.jsp";
				}
				break;
			}
			/*case (int)SECConstant.BusinessType.ENTRUSTED_FINANCING.ID:{							//受托理财 
				if (actionTypeId == SECConstant.Actions.LINKSEARCH){
					url += "d075v006.jsp";
				}
				else if (actionTypeId == SECConstant.Actions.CHECKSEARCH){
					url += "d075v006.jsp";
				}
				break;
			}
			case (int)SECConstant.BusinessType.STOCK_INVESTMENT.ID:{							//股权投资 
				if (actionTypeId == SECConstant.Actions.LINKSEARCH){
					url += "d079v006.jsp";
				}
				else if (actionTypeId == SECConstant.Actions.CHECKSEARCH){
					url += "d079v006.jsp";
				}
				break;
			}
			case (int)SECConstant.BusinessType.INSURANCE.ID:{									//保险代理
				if (actionTypeId == SECConstant.Actions.LINKSEARCH){
					url += "d083v006.jsp";
				}
				else if (actionTypeId == SECConstant.Actions.CHECKSEARCH){
					url += "d083v006.jsp";
				}
				break;
			}*/
			case (int)SECConstant.BusinessType.CAPITAL_TRANSFER.ID:{							//资金划拨
				if (actionTypeId == SECConstant.Actions.LINKSEARCH){
					url += "d085v006.jsp";
				}
				else if (actionTypeId == SECConstant.Actions.CHECKSEARCH){
					url += "d085v008.jsp";
				}
				break;
			}
			case (int)SECConstant.BusinessType.INTEREST_SETTLEMENT.ID:{							//资金利息结算
				if (actionTypeId == SECConstant.Actions.LINKSEARCH){
					url += "d087v006.jsp";
				}
				else if (actionTypeId == SECConstant.Actions.CHECKSEARCH){
					url += "d087v008.jsp";
				}
				break;
			}
			/*case (int)SECConstant.BusinessType.OTHER_CAPITAL_SETTLEMENT.ID:{					//其它资金往来  
				if (actionTypeId == SECConstant.Actions.LINKSEARCH){
					url += "d089v006.jsp";
				}
				else if (actionTypeId == SECConstant.Actions.CHECKSEARCH){
					url += "d089v006.jsp";
				}
				break;
			}*/
			case (int)SECConstant.BusinessType.SECURITIES_TRANSFER.ID:{							//证券划转
				if (actionTypeId == SECConstant.Actions.LINKSEARCH){
					url += "d091v003.jsp";
				}
				else if (actionTypeId == SECConstant.Actions.CHECKSEARCH){
					url += "d091v005.jsp";
				}
				break;
			}
			/*case (int)SECConstant.BusinessType.CARRY_COST.ID:{									//结转成本
				if (actionTypeId == SECConstant.Actions.LINKSEARCH){
					url += "d093v006.jsp";
				}
				else if (actionTypeId == SECConstant.Actions.CHECKSEARCH){
					url += "d093v006.jsp";
				}
				break;
			}
			//--------------------
			case (int)SECConstant.BusinessType.CAPITAL_REPURCHASE.ID:{							//资产回购
				if (actionTypeId == SECConstant.Actions.LINKSEARCH){
					url += "d071v006.jsp";
				}
				else if (actionTypeId == SECConstant.Actions.CHECKSEARCH){
					url += "d071v006.jsp";
				}
				break;
			}
			case (int)SECConstant.BusinessType.BOND_UNDERWRITING.ID:{							//债券承销
				if (actionTypeId == SECConstant.Actions.LINKSEARCH){
					url += "d081v006.jsp";
				}
				else if (actionTypeId == SECConstant.Actions.CHECKSEARCH){
					url += "d081v006.jsp";
				}
				break;
			}
			case (int)SECConstant.BusinessType.ENTRUST_FINANCING.ID:{							//委托理财
				if (actionTypeId == SECConstant.Actions.LINKSEARCH){
					url += "d073v006.jsp";
				}
				else if (actionTypeId == SECConstant.Actions.CHECKSEARCH){
					url += "d073v006.jsp";
				}
				break;
			}
			case (int)SECConstant.BusinessType.FOREIGN_CURRENCY_INVESTMENT.ID:{					//结构性投资 
				if (actionTypeId == SECConstant.Actions.LINKSEARCH){
					url += "d077v006.jsp";
				}
				else if (actionTypeId == SECConstant.Actions.CHECKSEARCH){
					url += "d077v006.jsp";
				}
				break;
			}*/
		}
		
		
		
		return url;
	}
	
	/**
	 * 通知书
	 * @param transactionTypeId
	 * @param actionTypeId
	 * @return
	 */
	public String getNoticeFormURL(long userId,long currencyId,long officeId,long transactionTypeId,long actionTypeId,long statusId){
		String url = "";
		
		if (actionTypeId == SECConstant.Actions.LINKSEARCH){									//录入人
			//url += "/NASApp/itreasury-securities/iTreasury-securities/securities/notice/control/n013c012.jsp?";
			url += "../notice/control/n013c012.jsp?";
			
		}
		else if (actionTypeId == SECConstant.Actions.CHECKSEARCH){								//复核人
			//url += "/NASApp/itreasury-securities/iTreasury-securities/securities/notice/control/n013c015.jsp?";
			url += "../notice/control/n013c015.jsp?";
			
		}
		
		url += "&fromMyWork=1";																	//确定从"我的工作"链接过来的标志位
		url += "&currencyId="+currencyId;														//币种
		url += "&officeId="+officeId;															//办事处
		url += "&statusId="+statusId;															//交割单状态
		
		//zpli modify 2005-09-14
		url += "&approvalId="+NameRef.getApprovalIDByTransactionTypeID(transactionTypeId,2,officeId,currencyId);	//审批标识
		//url += "&approvalId="+NameRef.getApprovalIDByTransactionTypeID(transactionTypeId,2);	//审批标识
		
		url += "&transactionTypeId="+transactionTypeId;											//交易类型
		url += "&strFailPageURL=../../mywork/mywork.jsp";			//失败页面
		url += "&strSuccessPageURL=../view/";				//成功页面
		
		
		int businessTypeId = Integer.parseInt(String.valueOf(transactionTypeId).substring(0,2));
		
		
		switch ((int)businessTypeId){
			case (int)SECConstant.BusinessType.CAPITAL_LANDING.ID:{								//资金拆借
				if (actionTypeId == SECConstant.Actions.LINKSEARCH){
					url += "n013v011.jsp";
				}
				else if (actionTypeId == SECConstant.Actions.CHECKSEARCH){
					url += "n013v014.jsp";
				}
				break;
			}
			case (int)SECConstant.BusinessType.STOCK_BID_ONLINE.ID:{							//股票一级网上申购	
				if (actionTypeId == SECConstant.Actions.LINKSEARCH){
					url += "n016v011.jsp";
				}
				else if (actionTypeId == SECConstant.Actions.CHECKSEARCH){
					url += "n016v014.jsp";
				}
				break;
			}
			case (int)SECConstant.BusinessType.STOCK_BID.ID:{									//股票一级网下申购
				if (actionTypeId == SECConstant.Actions.LINKSEARCH){
					url += "n017v011.jsp";
				}
				else if (actionTypeId == SECConstant.Actions.CHECKSEARCH){
					url += "n017v014.jsp";
				}
				break;
			}
			case (int)SECConstant.BusinessType.STOCK_TRANSACTION.ID:{							//股票二级
				if (actionTypeId == SECConstant.Actions.LINKSEARCH){
					url += "n018v011.jsp";
				}
				else if (actionTypeId == SECConstant.Actions.CHECKSEARCH){
					url += "n018v014.jsp";
				}
				break;
			}
			case (int)SECConstant.BusinessType.CENTRAL_BANK_NOTE_BID.ID:{						//央行票据一级 
				if (actionTypeId == SECConstant.Actions.LINKSEARCH){
					url += "n021v011.jsp";
				}
				else if (actionTypeId == SECConstant.Actions.CHECKSEARCH){
					url += "n021v014.jsp";
				}
				break;
			}
			case (int)SECConstant.BusinessType.CENTRAL_BANK_NOTE_TRANSACTION.ID:{				//央行票据二级 
				if (actionTypeId == SECConstant.Actions.LINKSEARCH){
					url += "n022v011.jsp";
				}
				else if (actionTypeId == SECConstant.Actions.CHECKSEARCH){
					url += "n022v014.jsp";
				}
				break;
			}
			case (int)SECConstant.BusinessType.BANK_BOND_REPURCHASE.ID:{						//银行间债券回购
				if (actionTypeId == SECConstant.Actions.LINKSEARCH){
					url += "n026v011.jsp";
				}
				else if (actionTypeId == SECConstant.Actions.CHECKSEARCH){
					url += "n026v014.jsp";
				}
				break;
			}
			case (int)SECConstant.BusinessType.EXCHANGECENTER_BOND_REPURCHASE.ID:{				//交易所债券回购 
				if (actionTypeId == SECConstant.Actions.LINKSEARCH){
					url += "n027v011.jsp";
				}
				else if (actionTypeId == SECConstant.Actions.CHECKSEARCH){
					url += "n027v014.jsp";
				}
				break;
			}
			case (int)SECConstant.BusinessType.BANK_NATIONAL_BOND_BID.ID:{						//银行间国债一级 
				if (actionTypeId == SECConstant.Actions.LINKSEARCH){
					url += "n031v011.jsp";
				}
				else if (actionTypeId == SECConstant.Actions.CHECKSEARCH){
					url += "n031v014.jsp";
				}
				break;
			}
			case (int)SECConstant.BusinessType.BANK_NATIONAL_BOND_TRANSACTION.ID:{				//银行间国债二级 
				if (actionTypeId == SECConstant.Actions.LINKSEARCH){
					url += "n032v011.jsp";
				}
				else if (actionTypeId == SECConstant.Actions.CHECKSEARCH){
					url += "n032v014.jsp";
				}
				break;
			}
			case (int)SECConstant.BusinessType.EXCHANGECENTER_NATIONAL_BOND_BID.ID:{			//交易所国债一级 
				if (actionTypeId == SECConstant.Actions.LINKSEARCH){
					url += "n033v011.jsp";
				}
				else if (actionTypeId == SECConstant.Actions.CHECKSEARCH){
					url += "n033v014.jsp";
				}
				break;
			}
			case (int)SECConstant.BusinessType.EXCHANGECENTER_NATIONAL_BOND_TRANSACTION.ID:{	//交易所国债二级
				if (actionTypeId == SECConstant.Actions.LINKSEARCH){
					url += "n034v011.jsp";
				}
				else if (actionTypeId == SECConstant.Actions.CHECKSEARCH){
					url += "n034v014.jsp";
				}
				break;
			}
			case (int)SECConstant.BusinessType.FINANCIAL_BOND_BID.ID:{							//金融债一级
				if (actionTypeId == SECConstant.Actions.LINKSEARCH){
					url += "n036v011.jsp";
				}
				else if (actionTypeId == SECConstant.Actions.CHECKSEARCH){
					url += "n036v014.jsp";
				}
				break;
			}
			case (int)SECConstant.BusinessType.FINANCIAL_BOND_TRANSACTION.ID:{					//金融债二级
				if (actionTypeId == SECConstant.Actions.LINKSEARCH){
					url += "n037v011.jsp";
				}
				else if (actionTypeId == SECConstant.Actions.CHECKSEARCH){
					url += "n037v014.jsp";
				}
				break;
			}
			case (int)SECConstant.BusinessType.POLICY_RELATED_FINANCIAL_BOND_BID.ID:{			//政策性金融债一级 
				if (actionTypeId == SECConstant.Actions.LINKSEARCH){
					url += "n041v011.jsp";
				}
				else if (actionTypeId == SECConstant.Actions.CHECKSEARCH){
					url += "n041v014.jsp";
				}
				break;
			}
			case (int)SECConstant.BusinessType.POLICY_RELATED_FINANCIAL_BOND_TRANSACTION.ID:{	//政策性金融债二级
				if (actionTypeId == SECConstant.Actions.LINKSEARCH){
					url += "n042v011.jsp";
				}
				else if (actionTypeId == SECConstant.Actions.CHECKSEARCH){
					url += "n042v014.jsp";
				}
				break;
			}
			case (int)SECConstant.BusinessType.ENTERPRISE_BOND_BID.ID:{							//企业债一级
				if (actionTypeId == SECConstant.Actions.LINKSEARCH){
					url += "n046v011.jsp";
				}
				else if (actionTypeId == SECConstant.Actions.CHECKSEARCH){
					url += "n046v014.jsp";
				}
				break;
			}
			case (int)SECConstant.BusinessType.ENTERPRISE_BOND_TRANSACTION.ID:{					//企业债二级
				if (actionTypeId == SECConstant.Actions.LINKSEARCH){
					url += "n047v011.jsp";
				}
				else if (actionTypeId == SECConstant.Actions.CHECKSEARCH){
					url += "n047v014.jsp";
				}
				break;
			}
			case (int)SECConstant.BusinessType.TRANSFORMABLE_BOND_BID_ONLINE.ID:{				//可转债一级网上申购
				if (actionTypeId == SECConstant.Actions.LINKSEARCH){
					url += "n051v011.jsp";
				}
				else if (actionTypeId == SECConstant.Actions.CHECKSEARCH){
					url += "n051v014.jsp";
				}
				break;
			}
			case (int)SECConstant.BusinessType.TRANSFORMABLE_BOND_BID.ID:{						//可转债一级网下申购
				if (actionTypeId == SECConstant.Actions.LINKSEARCH){
					url += "n052v011.jsp";
				}
				else if (actionTypeId == SECConstant.Actions.CHECKSEARCH){
					url += "n052v014.jsp";
				}
				break;
			}
			case (int)SECConstant.BusinessType.TRANSFORMABLE_BOND_TRANSACTION.ID:{				//可转债二级
				if (actionTypeId == SECConstant.Actions.LINKSEARCH){
					url += "n053v011.jsp";
				}
				else if (actionTypeId == SECConstant.Actions.CHECKSEARCH){
					url += "n053v014.jsp";
				}
				break;
			}
			case (int)SECConstant.BusinessType.DEBT_TO_EQUITY.ID:{								//债转股 
				if (actionTypeId == SECConstant.Actions.LINKSEARCH){
					url += "n054v011.jsp";
				}
				else if (actionTypeId == SECConstant.Actions.CHECKSEARCH){
					url += "n054v014.jsp";
				}
				break;
			}
			/*case (int)SECConstant.BusinessType.ENCLOSED_FUND_BID_ONLINE.ID:{					//封闭式基金一级网上申购
				if (actionTypeId == SECConstant.Actions.LINKSEARCH){
					
				}
				else if (actionTypeId == SECConstant.Actions.CHECKSEARCH){
					
				}
				break;
			}*/
			case (int)SECConstant.BusinessType.ENCLOSED_FUND_BID.ID:{							//封闭式基金一级网下申购
				if (actionTypeId == SECConstant.Actions.LINKSEARCH){
					url += "n057v011.jsp";
				}
				else if (actionTypeId == SECConstant.Actions.CHECKSEARCH){
					url += "n057v014.jsp";
				}
				break;
			}
			/*case (int)SECConstant.BusinessType.ENCLOSED_FUND_TRANSACTION.ID:{					//封闭式基金二级
				if (actionTypeId == SECConstant.Actions.LINKSEARCH){
					
				}
				else if (actionTypeId == SECConstant.Actions.CHECKSEARCH){
					
				}
				break;
			}*/
			case (int)SECConstant.BusinessType.MUTUAL_FUND_SUBSCRIBE.ID:{						//开放式基金一级认购
				if (actionTypeId == SECConstant.Actions.LINKSEARCH){
					url += "n061v011.jsp";
				}
				else if (actionTypeId == SECConstant.Actions.CHECKSEARCH){
					url += "n061v014.jsp";
				}
				break;
			}
			case (int)SECConstant.BusinessType.MUTUAL_FUND_BID.ID:{								//开放式基金二级申购
				if (actionTypeId == SECConstant.Actions.LINKSEARCH){
					url += "n062v011.jsp";
				}
				else if (actionTypeId == SECConstant.Actions.CHECKSEARCH){
					url += "n062v014.jsp";
				}
				break;
			}
			case (int)SECConstant.BusinessType.MUTUAL_FUND_REDEEM.ID:{							//开放式基金二级赎回
				if (actionTypeId == SECConstant.Actions.LINKSEARCH){
					url += "n063v011.jsp";
				}
				else if (actionTypeId == SECConstant.Actions.CHECKSEARCH){
					url += "n063v014.jsp";
				}
				break;
			}
			case (int)SECConstant.BusinessType.MUTUAL_FUND_MELON_CUTTING.ID:{					//开放式基金分红
				if (actionTypeId == SECConstant.Actions.LINKSEARCH){
					url += "n064v011.jsp";
				}
				else if (actionTypeId == SECConstant.Actions.CHECKSEARCH){
					url += "n064v014.jsp";
				}
				break;
			}
			/*case (int)SECConstant.BusinessType.ENTRUSTED_FINANCING.ID:{							//受托理财 
				if (actionTypeId == SECConstant.Actions.LINKSEARCH){								//录入人
					url = "/NASApp/itreasury-securities/iTreasury-securities/securities/notice/control/n071c012.jsp?";
					url += "&fromMyWork=1";																	//确定从"我的工作"链接过来的标志位
					url += "&currencyId="+currencyId;														//币种
					url += "&officeId="+officeId;															//办事处
					url += "&statusId="+statusId;															//交割单状态
					url += "&transactionTypeId="+transactionTypeId;											//交易类型
					url += "&strFailPageURL=/NASApp/iTreasury-securities/securities/mywork/mywork.jsp";			//失败页面
					url += "&strSuccessPageURL=/NASApp/iTreasury-securities/securities/notice/view/";				//成功页面
					url += "n073v011.jsp";
					
				}
				else if (actionTypeId == SECConstant.Actions.CHECKSEARCH){
					url = "/NASApp/itreasury-securities/iTreasury-securities/securities/notice/control/n071c015.jsp?";
					url += "&fromMyWork=1";																	//确定从"我的工作"链接过来的标志位
					url += "&currencyId="+currencyId;														//币种
					url += "&officeId="+officeId;															//办事处
					url += "&statusId="+statusId;															//交割单状态
					url += "&transactionTypeId="+transactionTypeId;											//交易类型
					url += "&strFailPageURL=/NASApp/iTreasury-securities/securities/mywork/mywork.jsp";			//失败页面
					url += "&strSuccessPageURL=/NASApp/iTreasury-securities/securities/notice/view/";				//成功页面
					url += "n073v014.jsp";
				}
				break;
			}*/
			/*case (int)SECConstant.BusinessType.STOCK_INVESTMENT.ID:{							//股权投资 
				if (actionTypeId == SECConstant.Actions.LINKSEARCH){
					
				}
				else if (actionTypeId == SECConstant.Actions.CHECKSEARCH){
					
				}
				break;
			}
			case (int)SECConstant.BusinessType.INSURANCE.ID:{									//保险代理
				if (actionTypeId == SECConstant.Actions.LINKSEARCH){
					
				}
				else if (actionTypeId == SECConstant.Actions.CHECKSEARCH){
					
				}
				break;
			}*/
			case (int)SECConstant.BusinessType.CAPITAL_TRANSFER.ID:{							//资金划拨
				if (actionTypeId == SECConstant.Actions.LINKSEARCH){
					url += "n085v011.jsp";
				}
				else if (actionTypeId == SECConstant.Actions.CHECKSEARCH){
					url += "n085v014.jsp";
				}
				break;
			}
			case (int)SECConstant.BusinessType.INTEREST_SETTLEMENT.ID:{							//资金利息结算
				if (actionTypeId == SECConstant.Actions.LINKSEARCH){
					url += "n087v011.jsp";
				}
				else if (actionTypeId == SECConstant.Actions.CHECKSEARCH){
					url += "n087v014.jsp";
				}
				break;
			}
			/*case (int)SECConstant.BusinessType.OTHER_CAPITAL_SETTLEMENT.ID:{					//其它资金往来  
				if (actionTypeId == SECConstant.Actions.LINKSEARCH){
					
				}
				else if (actionTypeId == SECConstant.Actions.CHECKSEARCH){
					
				}
				break;
			}
			case (int)SECConstant.BusinessType.SECURITIES_TRANSFER.ID:{							//证券划转
				if (actionTypeId == SECConstant.Actions.LINKSEARCH){
					
				}
				else if (actionTypeId == SECConstant.Actions.CHECKSEARCH){
					
				}
				break;
			}
			case (int)SECConstant.BusinessType.CARRY_COST.ID:{									//结转成本
				if (actionTypeId == SECConstant.Actions.LINKSEARCH){
					
				}
				else if (actionTypeId == SECConstant.Actions.CHECKSEARCH){
					
				}
				break;
			}*/
			//--------------------
			case (int)SECConstant.BusinessType.CAPITAL_REPURCHASE.ID:{							//资产回购
				if (actionTypeId == SECConstant.Actions.LINKSEARCH){								//录入人
					//url = "/NASApp/itreasury-securities/iTreasury-securities/securities/notice/control/n071c012.jsp?";
					url = "/NASApp/iTreasury-securities/securities/notice/control/n071c012.jsp?";
					
					url += "&fromMyWork=1";																	//确定从"我的工作"链接过来的标志位
					url += "&currencyId="+currencyId;														//币种
					url += "&officeId="+officeId;															//办事处
					url += "&statusId="+statusId;															//交割单状态
					url += "&transactionTypeId="+transactionTypeId;											//交易类型
					url += "&strFailPageURL=/NASApp/iTreasury-securities/securities/mywork/mywork.jsp";			//失败页面
					url += "&strSuccessPageURL=/NASApp/iTreasury-securities/securities/notice/view/";				//成功页面
					url += "n071v011.jsp";
					
				}
				else if (actionTypeId == SECConstant.Actions.CHECKSEARCH){
					//url = "/NASApp/itreasury-securities/iTreasury-securities/securities/notice/control/n071c015.jsp?";
					url = "/NASApp/iTreasury-securities/securities/notice/control/n071c015.jsp?";
					
					url += "&fromMyWork=1";																	//确定从"我的工作"链接过来的标志位
					url += "&currencyId="+currencyId;														//币种
					url += "&officeId="+officeId;															//办事处
					url += "&statusId="+statusId;															//交割单状态
					url += "&transactionTypeId="+transactionTypeId;											//交易类型
					url += "&strFailPageURL=/NASApp/iTreasury-securities/securities/mywork/mywork.jsp";			//失败页面
					url += "&strSuccessPageURL=/NASApp/iTreasury-securities/securities/notice/view/";				//成功页面
					url += "n071v014.jsp";
				}
				break;
			}
			case (int)SECConstant.BusinessType.BOND_UNDERWRITING.ID:{							//债券承销
				if (actionTypeId == SECConstant.Actions.LINKSEARCH){								//录入人
					//url = "/NASApp/itreasury-securities/iTreasury-securities/securities/notice/control/n071c012.jsp?";
					url = "/NASApp/iTreasury-securities/securities/notice/control/n071c012.jsp?";
					
					url += "&fromMyWork=1";																	//确定从"我的工作"链接过来的标志位
					url += "&currencyId="+currencyId;														//币种
					url += "&officeId="+officeId;															//办事处
					url += "&statusId="+statusId;															//交割单状态
					url += "&transactionTypeId="+transactionTypeId;											//交易类型
					url += "&strFailPageURL=/NASApp/iTreasury-securities/securities/mywork/mywork.jsp";			//失败页面
					url += "&strSuccessPageURL=/NASApp/iTreasury-securities/securities/notice/view/";				//成功页面
					url += "n081v011.jsp";
					
				}
				else if (actionTypeId == SECConstant.Actions.CHECKSEARCH){
					//url = "/NASApp/itreasury-securities/iTreasury-securities/securities/notice/control/n071c015.jsp?";
					url = "/NASApp/iTreasury-securities/securities/notice/control/n071c015.jsp?";
					
					url += "&fromMyWork=1";																	//确定从"我的工作"链接过来的标志位
					url += "&currencyId="+currencyId;														//币种
					url += "&officeId="+officeId;															//办事处
					url += "&statusId="+statusId;															//交割单状态
					url += "&transactionTypeId="+transactionTypeId;											//交易类型
					url += "&strFailPageURL=/NASApp/iTreasury-securities/securities/mywork/mywork.jsp";			//失败页面
					url += "&strSuccessPageURL=/NASApp/iTreasury-securities/securities/notice/view/";				//成功页面
					url += "n081v014.jsp";
				}
				break;
			}
			case (int)SECConstant.BusinessType.ENTRUST_FINANCING.ID:{							//委托理财
				if (actionTypeId == SECConstant.Actions.LINKSEARCH){								//录入人
					//url = "/NASApp/itreasury-securities/iTreasury-securities/securities/notice/control/n071c012.jsp?";
					url = "/NASApp/iTreasury-securities/securities/notice/control/n071c012.jsp?";
					
					url += "&fromMyWork=1";																	//确定从"我的工作"链接过来的标志位
					url += "&currencyId="+currencyId;														//币种
					url += "&officeId="+officeId;															//办事处
					url += "&statusId="+statusId;															//交割单状态
					url += "&transactionTypeId="+transactionTypeId;											//交易类型
					url += "&strFailPageURL=/NASApp/iTreasury-securities/securities/mywork/mywork.jsp";			//失败页面
					url += "&strSuccessPageURL=/NASApp/iTreasury-securities/securities/notice/view/";				//成功页面
					url += "n073v011.jsp";
					
				}
				else if (actionTypeId == SECConstant.Actions.CHECKSEARCH){
					//url = "/NASApp/itreasury-securities/iTreasury-securities/securities/notice/control/n071c015.jsp?";
					url = "/NASApp/iTreasury-securities/securities/notice/control/n071c015.jsp?";
					
					url += "&fromMyWork=1";																	//确定从"我的工作"链接过来的标志位
					url += "&currencyId="+currencyId;														//币种
					url += "&officeId="+officeId;															//办事处
					url += "&statusId="+statusId;															//交割单状态
					url += "&transactionTypeId="+transactionTypeId;											//交易类型
					url += "&strFailPageURL=/NASApp/iTreasury-securities/securities/mywork/mywork.jsp";			//失败页面
					url += "&strSuccessPageURL=/NASApp/iTreasury-securities/securities/notice/view/";				//成功页面
					url += "n073v014.jsp";
				}
				break;
			}
			case (int)SECConstant.BusinessType.FOREIGN_CURRENCY_INVESTMENT.ID:{					//结构性投资 
				if (actionTypeId == SECConstant.Actions.LINKSEARCH){								//录入人
					//url = "/NASApp/itreasury-securities/iTreasury-securities/securities/notice/control/n071c012.jsp?";
					url = "/NASApp/iTreasury-securities/securities/notice/control/n071c012.jsp?";
					
					url += "&fromMyWork=1";																	//确定从"我的工作"链接过来的标志位
					//url += "&currencyId="+currencyId;														//币种
					url += "&officeId="+officeId;															//办事处
					url += "&statusId="+statusId;															//交割单状态
					url += "&transactionTypeId="+transactionTypeId;											//交易类型
					url += "&strFailPageURL=/NASApp/iTreasury-securities/securities/mywork/mywork.jsp";			//失败页面
					url += "&strSuccessPageURL=/NASApp/iTreasury-securities/securities/notice/view/";				//成功页面
					url += "n077v011.jsp";
					
				}
				else if (actionTypeId == SECConstant.Actions.CHECKSEARCH){
					//url = "/NASApp/itreasury-securities/iTreasury-securities/securities/notice/control/n071c015.jsp?";
					url = "/NASApp/iTreasury-securities/securities/notice/control/n071c015.jsp?";
					
					url += "&fromMyWork=1";																	//确定从"我的工作"链接过来的标志位
					url += "&currencyId="+currencyId;														//币种
					url += "&officeId="+officeId;															//办事处
					url += "&statusId="+statusId;															//交割单状态
					url += "&transactionTypeId="+transactionTypeId;											//交易类型
					url += "&strFailPageURL=/NASApp/iTreasury-securities/securities/mywork/mywork.jsp";			//失败页面
					url += "&strSuccessPageURL=/NASApp/iTreasury-securities/securities/notice/view/";				//成功页面
					url += "n077v014.jsp";
				}
				break;
			}
		}
		
		return url;
	}
	
	/**
	 * 合同
	 * @param transactionTypeId
	 * @param actionTypeId
	 * @return
	 */
	public String getApplyContractURL(long userId,long currencyId,long officeId,long transactionTypeId,long actionTypeId,long statusId){
		String url = "";
		if(actionTypeId == SECConstant.Actions.LINKSEARCH)
		{
			//url += "/NASApp/itreasury-securities/iTreasury-securities/securities/apply/control/a013c104.jsp?";
			url += "../contract/control/c071c104.jsp?";
			
		}
		else if(actionTypeId == SECConstant.Actions.CHECKSEARCH)
		{
			//url += "/NASApp/itreasury-securities/iTreasury-securities/securities/apply/control/a013c104.jsp?";
			url += "../contract/control/c071c104.jsp?";
			 
		}
		else if (actionTypeId == SECConstant.Actions.COMMIT){
			url += "../contract/control/c071c104.jsp?";
		}
		
		url += "&fromMyWork=1";																	//确定从"我的工作"链接过来的标志位
		url += "&currencyId="+currencyId;														//币种
		url += "&firstsearch=yes";
		
		//zpli modify 2005-09-14
		url += "&approvalId="+NameRef.getApprovalIDByTransactionTypeID(transactionTypeId,3,officeId,currencyId);	//审批标识
		//url += "&approvalId="+NameRef.getApprovalIDByTransactionTypeID(transactionTypeId,3);	//审批标识
		
		if (actionTypeId == SECConstant.Actions.LINKSEARCH){
			url += "&queryPurpose=1";			
		}
		else if (actionTypeId == SECConstant.Actions.CHECKSEARCH){
			url += "&queryPurpose=2";
		}
		url += "&strAction=" + SECConstant.Actions.LINKSEARCH;
		url += "&officeId="+officeId;															//办事处
		url += "&statusId="+statusId;															//交割单状态
		url += "&transactionTypeId="+transactionTypeId;											//交易类型
		url += "&pageLineCount=10";
		url += "&pageCount=1";
		url += "&pageNo=1";
		url += "&strFailPageURL=../../mywork/mywork.jsp";			//失败页面
		url += "&strSuccessPageURL=../view/";
		System.out.println("--------------"+url);
		
		int businessTypeId = Integer.parseInt(String.valueOf(transactionTypeId).substring(0,2));
		
		switch ((int)businessTypeId){
			case (int)SECConstant.BusinessType.CAPITAL_IN_CREDIT_EXTENSION.ID:{					//资金拆入授信
				break;
			}
			case (int)SECConstant.BusinessType.CAPITAL_OUT_CREDIT_EXTENSION.ID:{				//资金拆出授信
				break;
			}
			case (int)SECConstant.BusinessType.CAPITAL_LANDING.ID:{								//资金拆借
				break;
			}
			case (int)SECConstant.BusinessType.STOCK_BID_ONLINE.ID:{							//股票一级网上申购	
				break;
			}
			case (int)SECConstant.BusinessType.STOCK_BID.ID:{									//股票一级网下申购
				break;
			}
			case (int)SECConstant.BusinessType.STOCK_TRANSACTION.ID:{							//股票二级
				break;
			}
			case (int)SECConstant.BusinessType.CENTRAL_BANK_NOTE_BID.ID:{						//央行票据一级 
				break;
			}
			case (int)SECConstant.BusinessType.CENTRAL_BANK_NOTE_TRANSACTION.ID:{				//央行票据二级 
				break;
			}
			case (int)SECConstant.BusinessType.BANK_BOND_REPURCHASE.ID:{						//银行间债券回购
				break;
			}
			case (int)SECConstant.BusinessType.EXCHANGECENTER_BOND_REPURCHASE.ID:{				//交易所债券回购 
				break;
			}
			case (int)SECConstant.BusinessType.BANK_NATIONAL_BOND_BID.ID:{						//银行间国债一级 
				break;
			}
			case (int)SECConstant.BusinessType.BANK_NATIONAL_BOND_TRANSACTION.ID:{				//银行间国债二级 
				break;
			}
			case (int)SECConstant.BusinessType.EXCHANGECENTER_NATIONAL_BOND_BID.ID:{			//交易所国债一级 
				break;
			}
			case (int)SECConstant.BusinessType.EXCHANGECENTER_NATIONAL_BOND_TRANSACTION.ID:{	//交易所国债二级
				break;
			}
			case (int)SECConstant.BusinessType.FINANCIAL_BOND_BID.ID:{							//金融债一级
				break;
			}
			case (int)SECConstant.BusinessType.FINANCIAL_BOND_TRANSACTION.ID:{					//金融债二级
				break;
			}
			case (int)SECConstant.BusinessType.POLICY_RELATED_FINANCIAL_BOND_BID.ID:{			//政策性金融债一级 
				break;
			}
			case (int)SECConstant.BusinessType.POLICY_RELATED_FINANCIAL_BOND_TRANSACTION.ID:{	//政策性金融债二级
				break;
			}
			case (int)SECConstant.BusinessType.ENTERPRISE_BOND_BID.ID:{							//企业债一级
				break;
			}
			case (int)SECConstant.BusinessType.ENTERPRISE_BOND_TRANSACTION.ID:{					//企业债二级
				break;
			}
			case (int)SECConstant.BusinessType.TRANSFORMABLE_BOND_BID_ONLINE.ID:{				//可转债一级网上申购 
				break;
			}
			case (int)SECConstant.BusinessType.TRANSFORMABLE_BOND_BID.ID:{						//可转债一级网下申购
				break;
			}
			case (int)SECConstant.BusinessType.TRANSFORMABLE_BOND_TRANSACTION.ID:{				//可转债二级
				break;
			}
			case (int)SECConstant.BusinessType.DEBT_TO_EQUITY.ID:{								//债转股 
				break;
			}
			case (int)SECConstant.BusinessType.ENCLOSED_FUND_BID_ONLINE.ID:{					//封闭式基金一级网上申购
				break;
			}
			case (int)SECConstant.BusinessType.ENCLOSED_FUND_BID.ID:{							//封闭式基金一级网下申购
				break;
			}
			case (int)SECConstant.BusinessType.ENCLOSED_FUND_TRANSACTION.ID:{					//封闭式基金二级
				break;
			}
			case (int)SECConstant.BusinessType.MUTUAL_FUND_SUBSCRIBE.ID:{						//开放式基金一级认购
				break;
			}
			case (int)SECConstant.BusinessType.MUTUAL_FUND_BID.ID:{								//开放式基金二级申购
				break;
			}
			case (int)SECConstant.BusinessType.MUTUAL_FUND_REDEEM.ID:{							//开放式基金二级赎回
				break;
			}
			case (int)SECConstant.BusinessType.MUTUAL_FUND_MELON_CUTTING.ID:{					//开放式基金分红
				break;
			}
			case (int)SECConstant.BusinessType.ENTRUSTED_FINANCING.ID:{							//受托理财 
				break;
			}
			case (int)SECConstant.BusinessType.STOCK_INVESTMENT.ID:{							//股权投资 
				break;
			}
			case (int)SECConstant.BusinessType.INSURANCE.ID:{									//保险代理
				break;
			}
			case (int)SECConstant.BusinessType.CAPITAL_TRANSFER.ID:{							//资金划拨
				break;
			}
			case (int)SECConstant.BusinessType.INTEREST_SETTLEMENT.ID:{							//资金利息结算
				break;
			}
			case (int)SECConstant.BusinessType.OTHER_CAPITAL_SETTLEMENT.ID:{					//其它资金往来  
				break;
			}
			case (int)SECConstant.BusinessType.SECURITIES_TRANSFER.ID:{							//证券划转
				break;
			}
			case (int)SECConstant.BusinessType.CARRY_COST.ID:{									//结转成本
				break;
			}
			//--------------------
			case (int)SECConstant.BusinessType.CAPITAL_REPURCHASE.ID:{
				if(actionTypeId == SECConstant.Actions.LINKSEARCH)
				{
					url += "c071v101.jsp";
				}
				else if(actionTypeId == SECConstant.Actions.CHECKSEARCH )
				{
					url += "c071v103.jsp";
				}
				else if (actionTypeId == SECConstant.Actions.COMMIT ){
					url +="c071v106.jsp";
				}
				break;//资产回购
			}
			case (int)SECConstant.BusinessType.BOND_UNDERWRITING.ID:{							//债券承销
				if(actionTypeId == SECConstant.Actions.LINKSEARCH)
				{
					url += "c081v101.jsp";
				}
				else if(actionTypeId == SECConstant.Actions.CHECKSEARCH )
				{
					url += "c081v103.jsp";
				}
				else if (actionTypeId == SECConstant.Actions.COMMIT ){
					url +="c071v106.jsp";
				}
				break;
			}
			case (int)SECConstant.BusinessType.ENTRUST_FINANCING.ID:{							//委托理财
				if(actionTypeId == SECConstant.Actions.LINKSEARCH)
				{
					url += "c073v101.jsp";
				}
				else if(actionTypeId == SECConstant.Actions.CHECKSEARCH )
				{
					url += "c073v103.jsp";
				}
				else if (actionTypeId == SECConstant.Actions.COMMIT ){
					url +="c071v106.jsp";
				}
				break;
			}
			case (int)SECConstant.BusinessType.FOREIGN_CURRENCY_INVESTMENT.ID:{					//结构性投资
				
				url = "";
				
				if(actionTypeId == SECConstant.Actions.LINKSEARCH)
				{
					//url += "/NASApp/itreasury-securities/iTreasury-securities/securities/apply/control/a013c104.jsp?";
					url += "/NASApp/iTreasury-securities/securities/contract/control/c071c104.jsp?";
					
				}
				else if(actionTypeId == SECConstant.Actions.CHECKSEARCH)
				{
					//url += "/NASApp/itreasury-securities/iTreasury-securities/securities/apply/control/a013c104.jsp?";
					url += "/NASApp/iTreasury-securities/securities/contract/control/c071c104.jsp?";
					
				}
				
				url += "&fromMyWork=1";																	//确定从"我的工作"链接过来的标志位
				//url += "&currencyId="+currencyId;														//币种
				url += "&firstsearch=yes";
				if (actionTypeId == SECConstant.Actions.LINKSEARCH){
					url += "&queryPurpose=1";			
				}
				else if (actionTypeId == SECConstant.Actions.CHECKSEARCH){
					url += "&queryPurpose=2";
				}
				url += "&strAction=" + SECConstant.Actions.LINKSEARCH;
				url += "&officeId="+officeId;															//办事处
				url += "&statusId="+statusId;															//交割单状态
				url += "&transactionTypeId="+transactionTypeId;											//交易类型
				url += "&pageLineCount=10";
				url += "&pageCount=1";
				url += "&pageNo=1";
				url += "&strFailPageURL=/NASApp/iTreasury-securities/securities/mywork/mywork.jsp";			//失败页面
				url += "&strSuccessPageURL=/NASApp/iTreasury-securities/securities/contract/view/";
				if(actionTypeId == SECConstant.Actions.LINKSEARCH)
				{
					url += "c077v101.jsp";
				}
				else if(actionTypeId == SECConstant.Actions.CHECKSEARCH )
				{
					url += "c077v103.jsp";
				}
				else if (actionTypeId == SECConstant.Actions.COMMIT ){
					url +="c071v106.jsp";
				}
				break;
			}
		}
		
		return url;
	}
	
	/**
	 * 计划
	 * @param transactionTypeId
	 * @param actionTypeId
	 * @return
	 */
	public String getPlanURL(long userId,long currencyId,long officeId,long transactionTypeId,long actionTypeId,long statusId){
		String url = "";
		long approvalId = -1;
		ApprovalBiz biz = new ApprovalBiz();
		try
		{
		// zpli modify 2004-09-14
			approvalId = biz.getApprovalID(Constant.ModuleType.SECURITIES,
					Constant.ApprovalLoanType.OTHER,
					Constant.ApprovalAction.SECURITIES_CONTRACT_PLAN, officeId,
					currencyId);
		//approvalId = biz.getApprovalID(Constant.ModuleType.SECURITIES,Constant.ApprovalLoanType.OTHER,Constant.ApprovalAction.SECURITIES_CONTRACT_PLAN);

		//approvalId = biz.getApprovalID(1,1,1);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		if(actionTypeId == SECConstant.Actions.LINKSEARCH)
		{
			//url += "/NASApp/itreasury-securities/iTreasury-securities/securities/apply/control/a013c104.jsp?";
			url += "../contractplan/control/c001c006.jsp?";
			
		}
		else if(actionTypeId == SECConstant.Actions.CHECKSEARCH)
		{
			//url += "/NASApp/itreasury-securities/iTreasury-securities/securities/apply/control/a013c104.jsp?";
			url += "../contractplan/control/c001c006.jsp?";
			 
		}
		
		url += "&fromMyWork=1";																	//确定从"我的工作"链接过来的标志位
		url += "&currencyId="+currencyId;														//币种
		url += "&firstsearch=yes";
		url += "&approvalId="+approvalId;
		if (actionTypeId == SECConstant.Actions.LINKSEARCH){
			url += "&queryPurpose=1";			
		}
		else if (actionTypeId == SECConstant.Actions.CHECKSEARCH){
			url += "&queryPurpose=2";
		}
		url += "&strAction=" + SECConstant.Actions.LINKSEARCH;
		url += "&officeId="+officeId;															//办事处
		url += "&statusId="+statusId;															//交割单状态
		url += "&transactionTypeId="+transactionTypeId;											//交易类型
		url += "&pageLineCount=10";
		url += "&pageCount=1";
		url += "&pageNo=1";
		url += "&strFailPageURL=../../mywork/mywork.jsp";			//失败页面
		url += "&strSuccessPageURL=../view/";

		
		int businessTypeId = Integer.parseInt(String.valueOf(transactionTypeId).substring(0,2));
		
		switch ((int)businessTypeId){
			case (int)SECConstant.BusinessType.CAPITAL_IN_CREDIT_EXTENSION.ID:{					//资金拆入授信
				break;
			}
			case (int)SECConstant.BusinessType.CAPITAL_OUT_CREDIT_EXTENSION.ID:{				//资金拆出授信
				break;
			}
			case (int)SECConstant.BusinessType.CAPITAL_LANDING.ID:{								//资金拆借
				break;
			}
			case (int)SECConstant.BusinessType.STOCK_BID_ONLINE.ID:{							//股票一级网上申购	
				break;
			}
			case (int)SECConstant.BusinessType.STOCK_BID.ID:{									//股票一级网下申购
				break;
			}
			case (int)SECConstant.BusinessType.STOCK_TRANSACTION.ID:{							//股票二级
				break;
			}
			case (int)SECConstant.BusinessType.CENTRAL_BANK_NOTE_BID.ID:{						//央行票据一级 
				break;
			}
			case (int)SECConstant.BusinessType.CENTRAL_BANK_NOTE_TRANSACTION.ID:{				//央行票据二级 
				break;
			}
			case (int)SECConstant.BusinessType.BANK_BOND_REPURCHASE.ID:{						//银行间债券回购
				break;
			}
			case (int)SECConstant.BusinessType.EXCHANGECENTER_BOND_REPURCHASE.ID:{				//交易所债券回购 
				break;
			}
			case (int)SECConstant.BusinessType.BANK_NATIONAL_BOND_BID.ID:{						//银行间国债一级 
				break;
			}
			case (int)SECConstant.BusinessType.BANK_NATIONAL_BOND_TRANSACTION.ID:{				//银行间国债二级 
				break;
			}
			case (int)SECConstant.BusinessType.EXCHANGECENTER_NATIONAL_BOND_BID.ID:{			//交易所国债一级 
				break;
			}
			case (int)SECConstant.BusinessType.EXCHANGECENTER_NATIONAL_BOND_TRANSACTION.ID:{	//交易所国债二级
				break;
			}
			case (int)SECConstant.BusinessType.FINANCIAL_BOND_BID.ID:{							//金融债一级
				break;
			}
			case (int)SECConstant.BusinessType.FINANCIAL_BOND_TRANSACTION.ID:{					//金融债二级
				break;
			}
			case (int)SECConstant.BusinessType.POLICY_RELATED_FINANCIAL_BOND_BID.ID:{			//政策性金融债一级 
				break;
			}
			case (int)SECConstant.BusinessType.POLICY_RELATED_FINANCIAL_BOND_TRANSACTION.ID:{	//政策性金融债二级
				break;
			}
			case (int)SECConstant.BusinessType.ENTERPRISE_BOND_BID.ID:{							//企业债一级
				break;
			}
			case (int)SECConstant.BusinessType.ENTERPRISE_BOND_TRANSACTION.ID:{					//企业债二级
				break;
			}
			case (int)SECConstant.BusinessType.TRANSFORMABLE_BOND_BID_ONLINE.ID:{				//可转债一级网上申购 
				break;
			}
			case (int)SECConstant.BusinessType.TRANSFORMABLE_BOND_BID.ID:{						//可转债一级网下申购
				break;
			}
			case (int)SECConstant.BusinessType.TRANSFORMABLE_BOND_TRANSACTION.ID:{				//可转债二级
				break;
			}
			case (int)SECConstant.BusinessType.DEBT_TO_EQUITY.ID:{								//债转股 
				break;
			}
			case (int)SECConstant.BusinessType.ENCLOSED_FUND_BID_ONLINE.ID:{					//封闭式基金一级网上申购
				break;
			}
			case (int)SECConstant.BusinessType.ENCLOSED_FUND_BID.ID:{							//封闭式基金一级网下申购
				break;
			}
			case (int)SECConstant.BusinessType.ENCLOSED_FUND_TRANSACTION.ID:{					//封闭式基金二级
				break;
			}
			case (int)SECConstant.BusinessType.MUTUAL_FUND_SUBSCRIBE.ID:{						//开放式基金一级认购
				break;
			}
			case (int)SECConstant.BusinessType.MUTUAL_FUND_BID.ID:{								//开放式基金二级申购
				break;
			}
			case (int)SECConstant.BusinessType.MUTUAL_FUND_REDEEM.ID:{							//开放式基金二级赎回
				break;
			}
			case (int)SECConstant.BusinessType.MUTUAL_FUND_MELON_CUTTING.ID:{					//开放式基金分红
				break;
			}
			case (int)SECConstant.BusinessType.ENTRUSTED_FINANCING.ID:{							//受托理财 
				break;
			}
			case (int)SECConstant.BusinessType.STOCK_INVESTMENT.ID:{							//股权投资 
				break;
			}
			case (int)SECConstant.BusinessType.INSURANCE.ID:{									//保险代理
				break;
			}
			case (int)SECConstant.BusinessType.CAPITAL_TRANSFER.ID:{							//资金划拨
				break;
			}
			case (int)SECConstant.BusinessType.INTEREST_SETTLEMENT.ID:{							//资金利息结算
				break;
			}
			case (int)SECConstant.BusinessType.OTHER_CAPITAL_SETTLEMENT.ID:{					//其它资金往来  
				break;
			}
			case (int)SECConstant.BusinessType.SECURITIES_TRANSFER.ID:{							//证券划转
				break;
			}
			case (int)SECConstant.BusinessType.CARRY_COST.ID:{									//结转成本
				break;
			}
			//--------------------
			case (int)SECConstant.BusinessType.CAPITAL_REPURCHASE.CAPITAL_REPURCHASE:{							//资产回购
				break;
			}
			case (int)SECConstant.BusinessType.BOND_UNDERWRITING.BOND_UNDERWRITING:{							//债券承销
				break;
			}
			case (int)SECConstant.BusinessType.ENTRUST_FINANCING.ID:{							//委托理财
				if(actionTypeId == SECConstant.Actions.LINKSEARCH)
				{
					url += "c001v005.jsp";
				}
				else if(actionTypeId == SECConstant.Actions.CHECKSEARCH )
				{
					url += "c001v008.jsp";
				}
				
				break;
			}
			case (int)SECConstant.BusinessType.FOREIGN_CURRENCY_INVESTMENT.ID:{					//结构性投资 
				
				url = "";
				if(actionTypeId == SECConstant.Actions.LINKSEARCH)
				{
					//url += "/NASApp/itreasury-securities/iTreasury-securities/securities/apply/control/a013c104.jsp?";
					url += "/NASApp/iTreasury-securities/securities/contractplan/control/c001c006.jsp?";
					
				}
				else if(actionTypeId == SECConstant.Actions.CHECKSEARCH)
				{
					//url += "/NASApp/itreasury-securities/iTreasury-securities/securities/apply/control/a013c104.jsp?";
					url += "/NASApp/iTreasury-securities/securities/contractplan/control/c001c006.jsp?";
					
				}
				
				url += "&fromMyWork=1";																	//确定从"我的工作"链接过来的标志位
				//url += "&currencyId="+currencyId;														//币种
				url += "&firstsearch=yes";
				if (actionTypeId == SECConstant.Actions.LINKSEARCH){
					url += "&queryPurpose=1";			
				}
				else if (actionTypeId == SECConstant.Actions.CHECKSEARCH){
					url += "&queryPurpose=2";
				}
				url += "&strAction=" + SECConstant.Actions.LINKSEARCH;
				url += "&officeId="+officeId;															//办事处
				url += "&statusId="+statusId;															//交割单状态
				url += "&transactionTypeId="+transactionTypeId;											//交易类型
				url += "&pageLineCount=10";
				url += "&pageCount=1";
				url += "&pageNo=1";
				url += "&strFailPageURL=/NASApp/iTreasury-securities/securities/mywork/mywork.jsp";			//失败页面
				url += "&strSuccessPageURL=/NASApp/iTreasury-securities/securities/contractplan/view/";
				if(actionTypeId == SECConstant.Actions.LINKSEARCH)
				{
					url += "c001v005.jsp";
				}
				else if(actionTypeId == SECConstant.Actions.CHECKSEARCH )
				{
					url += "c001v008.jsp";
				}
				break;
			}
		}
		
		return url;
	}
}
