/*
 * Created on 2004-4-26
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.securities.bizdelegation;

import java.rmi.RemoteException;

import com.iss.itreasury.securities.exception.SecuritiesException;
import com.iss.itreasury.securities.print.bizlogic.PrintBondRepurchaseBean;
import com.iss.itreasury.securities.print.bizlogic.PrintCapitalTransferBean;
import com.iss.itreasury.securities.print.bizlogic.PrintNotPurchaseDebtBean;
import com.iss.itreasury.securities.print.bizlogic.PrintSecuritiesApplyBean;
import com.iss.itreasury.securities.print.bizlogic.PrintSecuritiesChangeBankBean;
import com.iss.itreasury.securities.print.bizlogic.PrintSecuritiesChangeBourseBean;
import com.iss.itreasury.securities.print.bizlogic.PrintSecuritiesChangeFundBean;
import com.iss.itreasury.securities.print.bizlogic.PrintSecuritiesChangeStockBean;
import com.iss.itreasury.securities.print.bizlogic.PrintSecuritiesExchangeStatusBean;
import com.iss.itreasury.securities.print.bizlogic.PrintSecuritiesProfitAndLossBankBean;
import com.iss.itreasury.securities.print.bizlogic.PrintSecuritiesProfitAndLossBourseBean;
import com.iss.itreasury.securities.print.bizlogic.PrintSecuritiesProfitAndLossFundBean;
import com.iss.itreasury.securities.print.bizlogic.PrintSecuritiesProfitAndLossStockBean;
import com.iss.itreasury.securities.print.bizlogic.PrintSecuritiesTransferBean;
import com.iss.itreasury.securities.print.bizlogic.PrintStockApplyingBean;
import com.iss.itreasury.securities.print.bizlogic.PrintSecuritiesBalanceBean;
import com.iss.itreasury.securities.print.bizlogic.PrintSecuritiesListAccountBean;
import com.iss.itreasury.securities.print.bizlogic.PrintSecuritiesInvestBean;
import com.iss.itreasury.securities.print.bizlogic.PrintStockRationBean;
import com.iss.itreasury.securities.print.bizlogic.PrintOpenFundApplyingBean;
import com.iss.itreasury.securities.print.dataentity.PrintBondRepurchaseInfo;
import com.iss.itreasury.securities.print.dataentity.PrintBondRepurchaseParam;
import com.iss.itreasury.securities.print.dataentity.PrintCapitalTransferInfo;
import com.iss.itreasury.securities.print.dataentity.PrintCapitalTransferParam;
import com.iss.itreasury.securities.print.dataentity.PrintNotPurchaseDebtInfo;
import com.iss.itreasury.securities.print.dataentity.PrintNotPurchaseDebtParam;
import com.iss.itreasury.securities.print.dataentity.PrintSecuritiesApplyInfo;
import com.iss.itreasury.securities.print.dataentity.PrintSecuritiesApplyParam;
import com.iss.itreasury.securities.print.dataentity.PrintSecuritiesBalanceInfo;
import com.iss.itreasury.securities.print.dataentity.PrintSecuritiesBalanceParam;
import com.iss.itreasury.securities.print.dataentity.PrintSecuritiesListAccountInfo;
import com.iss.itreasury.securities.print.dataentity.PrintSecuritiesListAccountParam;
import com.iss.itreasury.securities.print.dataentity.PrintSecuritiesInvestInfo;
import com.iss.itreasury.securities.print.dataentity.PrintSecuritiesInvestParam;
import com.iss.itreasury.securities.print.dataentity.PrintSecuritiesExchangeStatusInfo;
import com.iss.itreasury.securities.print.dataentity.PrintSecuritiesExchangeStatusParam;
import com.iss.itreasury.securities.print.dataentity.PrintSecuritiesStockChangeInfo;
import com.iss.itreasury.securities.print.dataentity.PrintSecuritiesStockChangeParam;
import com.iss.itreasury.securities.print.dataentity.PrintSecuritiesTransferInfo;
import com.iss.itreasury.securities.print.dataentity.PrintSecuritiesTransferParam;
import com.iss.itreasury.securities.print.dataentity.PrintStockApplyingInfo;
import com.iss.itreasury.securities.print.dataentity.PrintStockApplyingParam;
import com.iss.itreasury.securities.print.dataentity.PrintStockRationInfo;
import com.iss.itreasury.securities.print.dataentity.PrintStockRationParam;
import com.iss.itreasury.securities.print.dataentity.SecuritiesProfitAndLossBankInfo;
import com.iss.itreasury.securities.print.dataentity.SecuritiesProfitAndLossBourseInfo;
import com.iss.itreasury.securities.print.dataentity.SecuritiesProfitAndLossFundInfo;
import com.iss.itreasury.securities.print.dataentity.SecuritiesProfitAndLossParam;
import com.iss.itreasury.securities.print.dataentity.SecuritiesProfitAndLossStockInfo;
import com.iss.itreasury.securities.print.dataentity.PrintOpenFundApplyingParam;
import com.iss.itreasury.securities.print.dataentity.PrintOpenFundApplyingInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Log4j;
import com.iss.system.action.ActionException;
import com.iss.system.dao.oracle.PageLoader;

/**
 * @author chluo
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class PrintDelegation {
	protected Log4j log4j = new Log4j(Constant.ModuleType.SECURITIES, this);

	public PrintDelegation() throws RemoteException {
	}

	/** 资金划拨报表，返回一个printCapitalTransferInfo数组
	 * @author洛传和
	 * @param queryParam
	 * @return
	 * @throws SecuritiesException
	 */ 
	public PrintCapitalTransferInfo[]  PrintCapitalTransfer(PrintCapitalTransferParam printParam)
	throws SecuritiesException, ActionException  { 
		PageLoader printCapitalTransferPageLoader = null;
		PrintCapitalTransferBean printCapitalTransfeBean = new PrintCapitalTransferBean();

		log4j.debug("PrintDelegation debug info ::::PrintCapital start");
		printCapitalTransferPageLoader =
			(PageLoader) printCapitalTransfeBean.PrintCapitalTransfer (printParam);
		log4j.debug("PrintDelegation debug info ::::PrintCapital end");
		PrintCapitalTransferInfo[] printCapitalTransferInfo =(PrintCapitalTransferInfo[]) printCapitalTransferPageLoader.listAll() ;
		return printCapitalTransferInfo ;
	}

	/** 债券申购一览表，返回一个PageLoader
	 * @author王怡
	 * @param queryParam
	 * @return
	 * @throws SecuritiesException
	 */ 
	public PrintSecuritiesApplyInfo[]  PrintSecuritiesApply(PrintSecuritiesApplyParam printParam)
	throws SecuritiesException, ActionException { 
		PageLoader printPageLoader = null;
		PrintSecuritiesApplyBean printSecuritiesApplyBean = new PrintSecuritiesApplyBean();

		log4j.debug("queryDelegation debug info ::::queryDeliveryorder start");
		printPageLoader =
			(PageLoader) printSecuritiesApplyBean.printSecuritiesApply(printParam);
		log4j.debug("queryDelegation debug info ::::queryDeliveryorder end");
		PrintSecuritiesApplyInfo[] printSecuritiesApplyInfos =(PrintSecuritiesApplyInfo[]) printPageLoader.listAll() ;
		return printSecuritiesApplyInfos ;
	}

	/** 债券申购一览表，返回一个PageLoader
	 * @author王怡
	 * @param queryParam
	 * @return
	 * @throws SecuritiesException
	 */ 
	public PrintSecuritiesTransferInfo[]  PrintSecuritiesTransfer(PrintSecuritiesTransferParam printParam)
	throws SecuritiesException, ActionException { 
		PageLoader printPageLoader = null;
		PrintSecuritiesTransferBean printSecuritiesTransferBean = new PrintSecuritiesTransferBean();

		log4j.debug("queryDelegation debug info ::::queryDeliveryorder start");
		printPageLoader =
			(PageLoader) printSecuritiesTransferBean.printSecuritiesTransfer(printParam);
		log4j.debug("queryDelegation debug info ::::queryDeliveryorder end");
		PrintSecuritiesTransferInfo[] printSecuritiesTransferInfos =(PrintSecuritiesTransferInfo[]) printPageLoader.listAll() ;
		return printSecuritiesTransferInfos ;
	}

	/** 股票申购一览表，返回一个PageLoader
	 * @author王欢
	 * @param printParam
	 * @return
	 * @throws SecuritiesException
	 */ 
	public PrintStockApplyingInfo[]  PrintStockApplying(PrintStockApplyingParam printParam)
	throws SecuritiesException, ActionException {
		PageLoader printStockApplyingPageLoader = null;
		PrintStockApplyingBean printStockApplyingBean = new PrintStockApplyingBean();

		log4j.debug("printDelegation debug info ::::printStockApplying start");
		printStockApplyingPageLoader=(PageLoader) printStockApplyingBean.PrintStockApplying (printParam);
				
		log4j.debug("printDelegation debug info ::::printStockApplying end");
		PrintStockApplyingInfo[] printStockApplyingInfo =(PrintStockApplyingInfo[]) printStockApplyingPageLoader.listAll() ;

		return printStockApplyingInfo ;
	}

	/** 配股情况一览表，返回一个PageLoader
	 * @author谢建生
	 * @param queryParam
	 * @return
	 * @throws SecuritiesException
	 */ 
	public PrintStockRationInfo[]  PrintStockRation(PrintStockRationParam printParam)
	throws SecuritiesException, ActionException { 
		PageLoader printStockRationPageLoader = null;
		PrintStockRationBean printStockRationBean = new PrintStockRationBean();

		log4j.debug("queryDelegation debug info ::::queryDeliveryorder start");
		printStockRationPageLoader =
		   (PageLoader) printStockRationBean.PrintStockRation (printParam);
		log4j.debug("queryDelegation debug info ::::queryDeliveryorder end");
		PrintStockRationInfo[] printStockRationInfo =(PrintStockRationInfo[]) printStockRationPageLoader.listAll() ;
		return printStockRationInfo ;
	}

	/** 国债回购交易统计表，返回一个PageLoader
		* @author  ygzhao
		* @param printParam
		* @return
		* @throws SecuritiesException
		*/ 
		public PrintBondRepurchaseInfo[]  PrintBondRepurchase(PrintBondRepurchaseParam printParam)
		throws SecuritiesException, ActionException 
		{ 
			PageLoader printBondRepurchasePageLoader = null;
			PrintBondRepurchaseBean printBondRepurchaseBean = new PrintBondRepurchaseBean();

			log4j.debug("printDelegation debug info :::: PrintBondRepurchase start");
			printBondRepurchasePageLoader =
				(PageLoader) printBondRepurchaseBean.PrintBondRepurchase(printParam);
			System.out.println("after printBondRepurchaseBean.PrintBondRepurchase(printParam)");
			log4j.debug("printDelegation debug info :::: PrintBondRepurchase end,and printBondRepurchasePageLoader.getRowCount() = "+(printBondRepurchasePageLoader.getPageLoaderInfo()==null?0:printBondRepurchasePageLoader.getPageLoaderInfo().getRowCount()));
			PrintBondRepurchaseInfo[] printBondRepurchaseInfo =(PrintBondRepurchaseInfo[]) printBondRepurchasePageLoader.listAll() ;
			if(printBondRepurchaseInfo!=null)
					System.out.println("------printBondRepurchaseInfo.length = "+printBondRepurchaseInfo.length );
			else 
			 System.out.println("PrintBondRepurchase :printBondRepurchaseInfo = null");
		
			return printBondRepurchaseInfo ;
		}   
		
		
	/** 证券库存变化表
	 * @author 王怡
	 * @param printParam
	 * @return
	 * @throws SecuritiesException
	 */ 
	public PrintSecuritiesStockChangeInfo[]  securitiesChangeStock(PrintSecuritiesStockChangeParam printParam)
	throws SecuritiesException, ActionException 
	{   
		
		PageLoader pageLoader = null;
		PrintSecuritiesChangeStockBean stock=new PrintSecuritiesChangeStockBean();

		log4j.debug("PrintDelegation debug info ::::PrintSecuritiesChangeStockBean start");
		pageLoader =
			(PageLoader) stock.printSecuritiesChangeStock(printParam);
		PrintSecuritiesStockChangeInfo[] stockinfos =(PrintSecuritiesStockChangeInfo[]) pageLoader.listAll() ;
		return stockinfos ;
	}
	
	public PrintSecuritiesStockChangeInfo[]  securitiesChangeBourse(PrintSecuritiesStockChangeParam printParam)
	throws SecuritiesException, ActionException 
	{   
		
		PageLoader pageLoader = null;
		PrintSecuritiesChangeBourseBean stock=new PrintSecuritiesChangeBourseBean();

		log4j.debug("PrintDelegation debug info ::::PrintSecuritiesChangeBourseBean start");
		pageLoader =
			(PageLoader) stock.printSecuritiesChangeBourse(printParam);
		PrintSecuritiesStockChangeInfo[] stockinfos =(PrintSecuritiesStockChangeInfo[]) pageLoader.listAll() ;
		return stockinfos ;
	}
	
	public PrintSecuritiesStockChangeInfo[]  securitiesChangeBank(PrintSecuritiesStockChangeParam printParam)
	throws SecuritiesException, ActionException 
	{   
		
		PageLoader pageLoader = null;
		PrintSecuritiesChangeBankBean stock=new PrintSecuritiesChangeBankBean();

		log4j.debug("PrintDelegation debug info ::::PrintSecuritiesChangeBankBean start");
		pageLoader =
			(PageLoader) stock.printSecuritiesChangeBank(printParam);
		PrintSecuritiesStockChangeInfo[] stockinfos =(PrintSecuritiesStockChangeInfo[]) pageLoader.listAll() ;
		return stockinfos ;
	}
	
	public PrintSecuritiesStockChangeInfo[]  securitiesChangeFund(PrintSecuritiesStockChangeParam printParam)
	throws SecuritiesException, ActionException 
	{   
		
		PageLoader pageLoader = null;
		PrintSecuritiesChangeFundBean stock=new PrintSecuritiesChangeFundBean();

		log4j.debug("PrintDelegation debug info ::::PrintSecuritiesChangeFundBean start");
		pageLoader =
			(PageLoader) stock.printSecuritiesChangeFund(printParam);
		PrintSecuritiesStockChangeInfo[] stockinfos =(PrintSecuritiesStockChangeInfo[]) pageLoader.listAll() ;
		return stockinfos ;
	}
	
	
	/** 国债回购—未购回国债情况表，返回一个PageLoader
	 * @author王怡
	 * @param queryParam
	 * @return
	 * @throws SecuritiesException
	 */ 
	public PrintNotPurchaseDebtInfo[]  printNotPurchaseDebt(PrintNotPurchaseDebtParam printParam)
	throws SecuritiesException, ActionException 
	{ 
		PageLoader printPageLoader = null;
		PrintNotPurchaseDebtBean printNotPurchaseDebtBean = new PrintNotPurchaseDebtBean();

		log4j.debug("queryDelegation debug info ::::PrintNotPurchaseDebtBean start");
		printPageLoader =
			(PageLoader) printNotPurchaseDebtBean.printNotPurchaseDebt(printParam);
		log4j.debug("queryDelegation debug info ::::PrintNotPurchaseDebtBean end");
		PrintNotPurchaseDebtInfo[] printNotPurchaseDebtInfos =(PrintNotPurchaseDebtInfo[]) printPageLoader.listAll() ;
		return printNotPurchaseDebtInfos ;
	}
	
	/** 业务交易情况表，返回一个PageLoader
	 * @author王怡
	 * @param queryParam
	 * @return
	 * @throws SecuritiesException
	 */ 
	public PrintSecuritiesExchangeStatusInfo[]  printSecuritiesExchangeStatus(PrintSecuritiesExchangeStatusParam printParam)
	throws SecuritiesException, ActionException 
	{ 
		PageLoader printPageLoader = null;
		PrintSecuritiesExchangeStatusBean printSecuritiesExchangeStatusBean = new PrintSecuritiesExchangeStatusBean();

		log4j.debug("queryDelegation debug info ::::PrintSecuritiesExchangeStatusBean start");
		printPageLoader =
			(PageLoader) printSecuritiesExchangeStatusBean.printSecuritiesExchangeStatus(printParam);
		log4j.debug("queryDelegation debug info ::::PrintSecuritiesExchangeStatusBean end");
		PrintSecuritiesExchangeStatusInfo[] printSecuritiesExchangeStatusInfos =(PrintSecuritiesExchangeStatusInfo[]) printPageLoader.listAll() ;
		return printSecuritiesExchangeStatusInfos ;
	}
		
		
	/** 证券盈亏报表，返回一个数组
	 * @author洛传和
	 * @param printParam
	 * @return
	 * @throws SecuritiesException
	 */ 
	public SecuritiesProfitAndLossBankInfo[]  SecuritiesProfitAndLossBank(SecuritiesProfitAndLossParam printParam)
	throws SecuritiesException, ActionException 
	{   
		
		PageLoader pageLoader = null;
		PrintSecuritiesProfitAndLossBankBean bank = new PrintSecuritiesProfitAndLossBankBean();
		

		log4j.debug("PrintDelegation debug info ::::PrintSecuritiesProfitAndLossBankBean start");
		pageLoader =
			(PageLoader) bank.PrintSecuritiesProfitAndLossForBank (printParam);
		SecuritiesProfitAndLossBankInfo[] bankinfos =(SecuritiesProfitAndLossBankInfo[]) pageLoader.listAll() ;
		return bankinfos ;
	}
	
	/** 证券盈亏报表，返回一个数组
	 * @author洛传和
	 * @param printParam
	 * @return
	 * @throws SecuritiesException
	 */ 
	public SecuritiesProfitAndLossBankInfo[]  SecuritiesProfitAndLossBankForCnmef(SecuritiesProfitAndLossParam printParam)
	throws SecuritiesException, ActionException 
	{   
		
		PageLoader pageLoader = null;
		PrintSecuritiesProfitAndLossBankBean bank = new PrintSecuritiesProfitAndLossBankBean();
		

		log4j.debug("PrintDelegation debug info ::::PrintSecuritiesProfitAndLossBankBean start");
		pageLoader =
			(PageLoader) bank.PrintSecuritiesProfitAndLossForBankForCnmef (printParam);
		SecuritiesProfitAndLossBankInfo[] bankinfos =(SecuritiesProfitAndLossBankInfo[]) pageLoader.listAll() ;
		return bankinfos ;
	}
	
	public SecuritiesProfitAndLossBourseInfo[]  SecuritiesProfitAndLossBourseForCnmef(SecuritiesProfitAndLossParam printParam)
	throws SecuritiesException, ActionException 
	{   
		PageLoader pageLoader = null;
		PrintSecuritiesProfitAndLossBourseBean bourse = new PrintSecuritiesProfitAndLossBourseBean();
		

		log4j.debug("PrintDelegation debug info ::::PrintSecuritiesProfitAndLossBankBean start");
		pageLoader =
			(PageLoader) bourse.PrintSecuritiesProfitAndLossForBourseForCnmef(printParam);
		SecuritiesProfitAndLossBourseInfo[] bourseinfos =(SecuritiesProfitAndLossBourseInfo[]) pageLoader.listAll() ;
		return bourseinfos ;
	}
	
	public SecuritiesProfitAndLossBourseInfo[]  SecuritiesProfitAndLossBourse(SecuritiesProfitAndLossParam printParam)
	throws SecuritiesException, ActionException 
	{   
		PageLoader pageLoader = null;
		PrintSecuritiesProfitAndLossBourseBean bourse = new PrintSecuritiesProfitAndLossBourseBean();
		

		log4j.debug("PrintDelegation debug info ::::PrintSecuritiesProfitAndLossBankBean start");
		pageLoader =
			(PageLoader) bourse.PrintSecuritiesProfitAndLossForBourse(printParam);
		SecuritiesProfitAndLossBourseInfo[] bourseinfos =(SecuritiesProfitAndLossBourseInfo[]) pageLoader.listAll() ;
		return bourseinfos ;
	}
	
	public SecuritiesProfitAndLossFundInfo[]  SecuritiesProfitAndLossFund(SecuritiesProfitAndLossParam printParam)
	throws SecuritiesException, ActionException 
	{   
		
		PageLoader pageLoader = null;
		PrintSecuritiesProfitAndLossFundBean found =new PrintSecuritiesProfitAndLossFundBean();
		

		log4j.debug("PrintDelegation debug info ::::PrintSecuritiesProfitAndLossBankBean start");
		pageLoader =
			(PageLoader) found.PrintSecuritiesProfitAndLossFund( printParam);
		SecuritiesProfitAndLossFundInfo[] bankinfos =(SecuritiesProfitAndLossFundInfo[]) pageLoader.listAll() ;
		return bankinfos ;
	}
	
	public SecuritiesProfitAndLossFundInfo[]  SecuritiesProfitAndLossFundForCnmef(SecuritiesProfitAndLossParam printParam)
	throws SecuritiesException, ActionException 
	{   
		
		PageLoader pageLoader = null;
		PrintSecuritiesProfitAndLossFundBean found =new PrintSecuritiesProfitAndLossFundBean();
		

		log4j.debug("PrintDelegation debug info ::::PrintSecuritiesProfitAndLossBankBean start");
		pageLoader =
			(PageLoader) found.PrintSecuritiesProfitAndLossFundForCnmef( printParam);
		SecuritiesProfitAndLossFundInfo[] bankinfos =(SecuritiesProfitAndLossFundInfo[]) pageLoader.listAll() ;
		return bankinfos ;
	}

	public SecuritiesProfitAndLossStockInfo[]  SecuritiesProfitAndLossStock(SecuritiesProfitAndLossParam printParam)
	throws SecuritiesException, ActionException 
	{   
		
		PageLoader pageLoader = null;
		PrintSecuritiesProfitAndLossStockBean stock=new PrintSecuritiesProfitAndLossStockBean();

		log4j.debug("PrintDelegation debug info ::::PrintSecuritiesProfitAndLossBankBean start");
		pageLoader =
			(PageLoader) stock.PrintSecuritiesProfitAndLossStock(printParam);
		SecuritiesProfitAndLossStockInfo[] stockinfos =(SecuritiesProfitAndLossStockInfo[]) pageLoader.listAll() ;
		return stockinfos ;
	}

	public SecuritiesProfitAndLossStockInfo[]  SecuritiesProfitAndLossStockForCnmef(SecuritiesProfitAndLossParam printParam)
	throws SecuritiesException, ActionException 
	{   
		
		PageLoader pageLoader = null;
		PrintSecuritiesProfitAndLossStockBean stock=new PrintSecuritiesProfitAndLossStockBean();

		log4j.debug("PrintDelegation debug info ::::PrintSecuritiesProfitAndLossBankBean start");
		pageLoader =
			(PageLoader) stock.PrintSecuritiesProfitAndLossStockForCnmef(printParam);
		SecuritiesProfitAndLossStockInfo[] stockinfos =(SecuritiesProfitAndLossStockInfo[]) pageLoader.listAll() ;
		return stockinfos ;
	}

	/**
	 * 余额表数据
	 * @param  PrintSecuritiesBalanceParam
	 * @return PageLoader
	 * @exception SecuritiesException
	 */
	public PrintSecuritiesBalanceInfo[] printSecuritiesBalance(PrintSecuritiesBalanceParam parameter) throws SecuritiesException, ActionException {
		PrintSecuritiesBalanceBean printSecuritiesBalanceBean = new PrintSecuritiesBalanceBean();
		log4j.debug("PrintDelegation debug info::PrintSecuritiesBalanceBean Start！");
		PageLoader pageLoader = (PageLoader)printSecuritiesBalanceBean.printSecuritiesBalance(parameter);
		log4j.debug("PrintDelegation debug info::PrintSecuritiesBalanceBean End！");
		
		PrintSecuritiesBalanceInfo[] printSecuritiesBalanceInfo =(PrintSecuritiesBalanceInfo[]) pageLoader.listAll() ;

		return printSecuritiesBalanceInfo ; 
	}

	/**
	 * 明细账表数据
	 * @param  PrintSecuritiesListAccountParam
	 * @return PageLoader
	 * @exception SecuritiesException
	 */
	public PrintSecuritiesListAccountInfo[] printSecuritiesListAccount(PrintSecuritiesListAccountParam parameter) throws SecuritiesException, ActionException {
		PrintSecuritiesListAccountBean printSecuritiesListAccountBean = new PrintSecuritiesListAccountBean();
		log4j.debug("PrintDelegation debug info::PrintSecuritiesListAccountBean Start！");
		PageLoader pageLoader = (PageLoader)printSecuritiesListAccountBean.printSecuritiesListAccount(parameter);
		log4j.debug("PrintDelegation debug info::PrintSecuritiesListAccountBean End！");
		
		PrintSecuritiesListAccountInfo[] printSecuritiesListAccountInfo =(PrintSecuritiesListAccountInfo[]) pageLoader.listAll() ;
		return printSecuritiesListAccountInfo;
	}

	/**
	 * 投资业务一览表
	 * @param  PrintSecuritiesInvestParam
	 * @return PageLoader
	 * @exception SecuritiesException
	 */
	public PageLoader printSecuritiesInvest(PrintSecuritiesInvestParam parameter) throws SecuritiesException {
		PrintSecuritiesInvestBean printSecuritiesInvestBean = new PrintSecuritiesInvestBean();
		log4j.debug("PrintDelegation debug info::PrintSecuritiesInvestBean Start！");
		PageLoader pageLoader = (PageLoader)printSecuritiesInvestBean.printSecuritiesInvest(parameter);
		log4j.debug("PrintDelegation debug info::PrintSecuritiesInvestBean End！");
		return pageLoader;
	}
	/**
	 * 开放式基金一览表
	 * @param parameter
	 * @return
	 * @throws SecuritiesException
	 */
	public PrintOpenFundApplyingInfo[] printOpenFund( PrintOpenFundApplyingParam parameter ) throws SecuritiesException, ActionException 
	{
		PrintOpenFundApplyingBean printOpenFundApplyingBean = new PrintOpenFundApplyingBean();
		log4j.debug("PrintDelegation debug info::printOpenFundApplyingBean Start！");
		PageLoader pageLoader = (PageLoader)printOpenFundApplyingBean.printOpenFund(parameter);
		log4j.debug("PrintDelegation debug info::printOpenFundApplyingBean End！");
		PrintOpenFundApplyingInfo[] infos = (PrintOpenFundApplyingInfo[])pageLoader.listAll();
		return infos;
	}
}