/*
 * Created on 2004-4-1
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.securities.util;

import com.iss.itreasury.securities.exception.SecuritiesException;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Log;
/**
 * @author lgwang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class MagnifierHelper
{
	
	/**交易对手类型,暂时只用到五类**/
	public static final long COUNTERPART_TYPE_BANK 						= 0;	//开户行
	public static final long COUNTERPART_TYPE_BANKOFDEPOSIT 			= 1;	//开户营业部
	public static final long COUNTERPART_TYPE_FUNDMANAGEMENTCO 			= 2;	//开户基金管理公司
	public static final long COUNTERPART_TYPE_ISINTERBANKCOUNTERPART 	= 3;	//银行间交易对手
	public static final long COUNTERPART_TYPE_ISSECURITIESUNDERWRITER 	= 4;	//债券分销商
	
	/**保留类型**/
	public static final long COUNTERPART_TYPE_ISSECURITYCO 				= 5;	//委托理财券商
	public static final long COUNTERPART_TYPE_ISINVESTEDCORPORATION 	= 6;	//被投资企业
	public static final long COUNTERPART_TYPE_ISOWNERSHIPTRANSFER 		= 7;	//股权受让方
	public static final long COUNTERPART_TYPE_ISFLOATERS 				= 8;	//债券发行人
	public static final long COUNTERPART_TYPE_ISCONSIGNER 				= 9;	//受托理财委托方
	public static final long COUNTERPART_TYPE_ISPOLICYHOLDER 			= 10;	//投保人  */
	
	
	private static String[] counterpartTypeFields = new String[11];		//所有字段
	
	static{
		counterpartTypeFields[0] 	= "IsBank";							//开户行
		counterpartTypeFields[1] 	= "IsBankOfDeposit";				//开户营业部
		counterpartTypeFields[2] 	= "IsFundManagementCo";				//开户基金管理公司
		counterpartTypeFields[3] 	= "IsInterBankCounterpart";			//银行间交易对手
		counterpartTypeFields[4] 	= "IsSecuritiesUnderwriter";		//债券分销商
		counterpartTypeFields[5] 	= "IsSecurityCo";					//委托理财券商
		counterpartTypeFields[6] 	= "IsInvestedCorporation";			//被投资企业
		counterpartTypeFields[7] 	= "IsOwnershipTransfer";			//股权受让方
		counterpartTypeFields[8]	= "IsFloaters";						//债券发行人
		counterpartTypeFields[9] 	= "IsConsigner";					//受托理财委托方
		counterpartTypeFields[10] 	= "IsPolicyHolder";					//投保人
	}

	
	/**
	 * 用交易类型获得相应的交易对手类型的字段名
	 * 比如,某交易类型的所有的交易对手的类型为"开户行"和"银行间交易对手"
	 * 则返回字符串数组{"IsBank","IsInterBankCounterpart"}
	 * 如果交易类型错误或交易类型不具备交易对手类型,则返回 ""
	 * @param transactionTypeId
	 * @return
	 */
	public static String[] getCounterpartTypeFields(long transactionTypeId)throws SecuritiesException{
		String[] strFields = null;
		long[] counterpartTypeIds = getCounterpartTypeIdsByTransactionTypeId(transactionTypeId);
		
		int counter = 0;
		for (int n=0;n<counterpartTypeIds.length;n++){					//拼sql
			if (counterpartTypeIds[n] == SECConstant.TRUE){
				counter++;
			}
		}
		strFields = new String[counter];
		counter = 0;
		for (int n=0;n<counterpartTypeIds.length;n++){
			if (counterpartTypeIds[n] == SECConstant.TRUE){
				strFields[counter] = getCounterpartTypeField(n);
				counter++;
			}
		}
		return strFields;
	}

	/**
	 * 获得交易对手相应的字段名
	 * @param counterpartTypeId
	 * @return
	 */
	public static String getCounterpartTypeField(long counterpartTypeId){
		return counterpartTypeFields[(int)counterpartTypeId];
	}
	
	/**
	 * 通过交易类型获得该交易类型的交易对手类型代码
	 * @param transactionTypeId		交易类型代码
	 * @return
	 */
	public static long[] getCounterpartTypeIdsByTransactionTypeId(long transactionTypeId)throws SecuritiesException{
		long[] returnVal = new long[12];
		
		if (!(transactionTypeId>=0 && transactionTypeId<=11) && String.valueOf(transactionTypeId).length()<2){
			throw new SecuritiesException("交易对手代码放大镜传入交易类型代码错误",null);
		}
		
		for(int n=0;n<returnVal.length;n++){
			returnVal[n] = SECConstant.FALSE;
		}
		
		//获得交易类型前两位代码
		int intTransTypeId = -1;
		if (!(transactionTypeId>=0 && transactionTypeId<=11)){				//如果传入的是交易号,则按照传入的值返回交易对手类型
			intTransTypeId = Integer.parseInt(String.valueOf(transactionTypeId).substring(0,2));
		}
		else{
			intTransTypeId = (int)transactionTypeId;
		}
		
		switch (intTransTypeId){
			case 0:{																//0.全部
				for (int n=0;n<11;n++){
					returnVal[n] = SECConstant.TRUE;
				}
				break;
			}
			case (int)(COUNTERPART_TYPE_BANK+1):{
				returnVal[(int)COUNTERPART_TYPE_BANK] = SECConstant.TRUE;
				break;
			}
			case (int)(COUNTERPART_TYPE_BANKOFDEPOSIT+1):{
				returnVal[(int)COUNTERPART_TYPE_BANKOFDEPOSIT] = SECConstant.TRUE;
				break;
			}
			case (int)(COUNTERPART_TYPE_FUNDMANAGEMENTCO+1):{
				returnVal[(int)COUNTERPART_TYPE_FUNDMANAGEMENTCO] = SECConstant.TRUE;
				break;
			}
			case (int)(COUNTERPART_TYPE_ISINTERBANKCOUNTERPART+1):{
				returnVal[(int)COUNTERPART_TYPE_ISINTERBANKCOUNTERPART] = SECConstant.TRUE;
				break;
			}
			case (int)(COUNTERPART_TYPE_ISSECURITIESUNDERWRITER+1):{
				returnVal[(int)COUNTERPART_TYPE_ISSECURITIESUNDERWRITER] = SECConstant.TRUE;
				break;
			}
			case (int)(COUNTERPART_TYPE_ISSECURITYCO+1):{
				returnVal[(int)COUNTERPART_TYPE_ISSECURITYCO] = SECConstant.TRUE;
				break;
			}
			case (int)(COUNTERPART_TYPE_ISINVESTEDCORPORATION+1):{
				returnVal[(int)COUNTERPART_TYPE_ISINVESTEDCORPORATION] = SECConstant.TRUE;
				break;
			}
			case (int)(COUNTERPART_TYPE_ISOWNERSHIPTRANSFER+1):{
				returnVal[(int)COUNTERPART_TYPE_ISOWNERSHIPTRANSFER] = SECConstant.TRUE;
				break;
			}
			case (int)(COUNTERPART_TYPE_ISFLOATERS+1):{
				returnVal[(int)COUNTERPART_TYPE_ISFLOATERS] = SECConstant.TRUE;
				break;
			}
			case (int)(COUNTERPART_TYPE_ISCONSIGNER+1):{
				returnVal[(int)COUNTERPART_TYPE_ISCONSIGNER] = SECConstant.TRUE;
				break;
			}
			case (int)(COUNTERPART_TYPE_ISPOLICYHOLDER+1):{
				returnVal[(int)COUNTERPART_TYPE_ISPOLICYHOLDER] = SECConstant.TRUE;
				break;
			}
			
			case 12:{
				for (int n=0;n<11;n++){
					returnVal[n] = SECConstant.TRUE;
				}
				returnVal[(int)COUNTERPART_TYPE_BANKOFDEPOSIT] = SECConstant.FALSE;
				break;
			}
			
			//---------------------------------------按业务类型分
			case (int)SECConstant.BusinessType.CAPITAL_LANDING.ID:{					//1.资金拆借
				returnVal[(int)COUNTERPART_TYPE_ISINTERBANKCOUNTERPART] = SECConstant.TRUE;
				break;
			}
			case (int)SECConstant.BusinessType.STOCK_BID_ONLINE.ID:{				//2.股票一级网上申购
				returnVal[(int)COUNTERPART_TYPE_BANKOFDEPOSIT] = SECConstant.TRUE;
				break;
			}
			case (int)SECConstant.BusinessType.STOCK_BID.ID:{						//3.股票一级网下申购
				returnVal[(int)COUNTERPART_TYPE_BANKOFDEPOSIT] = SECConstant.TRUE;
				break;
			}
			case (int)SECConstant.BusinessType.STOCK_TRANSACTION.ID:{				//4.股票二级
				returnVal[(int)COUNTERPART_TYPE_BANKOFDEPOSIT] = SECConstant.TRUE;
				break;
			}
			case (int)SECConstant.BusinessType.CENTRAL_BANK_NOTE_BID.ID:{			//5.央行票据一级
				returnVal[(int)COUNTERPART_TYPE_ISSECURITIESUNDERWRITER] = SECConstant.TRUE;
				break;
			}
			case (int)SECConstant.BusinessType.CENTRAL_BANK_NOTE_TRANSACTION.ID:{	//6.央行票据二级
				returnVal[(int)COUNTERPART_TYPE_ISINTERBANKCOUNTERPART] = SECConstant.TRUE;
				break;
			}
			case (int)SECConstant.BusinessType.BANK_BOND_REPURCHASE.ID:{			//7.银行间债券回购
				returnVal[(int)COUNTERPART_TYPE_ISINTERBANKCOUNTERPART] = SECConstant.TRUE;
				break;
			}
			case (int)SECConstant.BusinessType.EXCHANGECENTER_BOND_REPURCHASE.ID:{	//8.交易所债券回购
				returnVal[(int)COUNTERPART_TYPE_BANKOFDEPOSIT] = SECConstant.TRUE;
				break;
			}
			case (int)SECConstant.BusinessType.BANK_NATIONAL_BOND_BID.ID:{			//9.银行间国债一级
				returnVal[(int)COUNTERPART_TYPE_ISSECURITIESUNDERWRITER] = SECConstant.TRUE;
				break;
			}
			case (int)SECConstant.BusinessType.BANK_NATIONAL_BOND_TRANSACTION.ID:{	//10.银行间国债二级
				returnVal[(int)COUNTERPART_TYPE_ISINTERBANKCOUNTERPART] = SECConstant.TRUE;
				break;
			}	
			case (int)SECConstant.BusinessType.EXCHANGECENTER_NATIONAL_BOND_BID.ID:{//11.交易所国债一级
				returnVal[(int)COUNTERPART_TYPE_BANKOFDEPOSIT] = SECConstant.TRUE;
				break;
			}
			case (int)SECConstant.BusinessType.EXCHANGECENTER_NATIONAL_BOND_TRANSACTION.ID:{//12.交易所国债二级
				returnVal[(int)COUNTERPART_TYPE_BANKOFDEPOSIT] = SECConstant.TRUE;
				break;
			}
			case (int)SECConstant.BusinessType.FINANCIAL_BOND_BID.ID:{				//13.金融债一级
				returnVal[(int)COUNTERPART_TYPE_ISSECURITIESUNDERWRITER] = SECConstant.TRUE;
				break;
			}
			case (int)SECConstant.BusinessType.FINANCIAL_BOND_TRANSACTION.ID:{		//14.金融债二级
				returnVal[(int)COUNTERPART_TYPE_ISINTERBANKCOUNTERPART] = SECConstant.TRUE;
				break;
			}
			case (int)SECConstant.BusinessType.POLICY_RELATED_FINANCIAL_BOND_BID.ID:{//15.政策性金融债一级
				returnVal[(int)COUNTERPART_TYPE_ISSECURITIESUNDERWRITER] = SECConstant.TRUE;
				break;
			}
			case (int)SECConstant.BusinessType.POLICY_RELATED_FINANCIAL_BOND_TRANSACTION.ID:{//16.政策性金融债二级
				returnVal[(int)COUNTERPART_TYPE_ISINTERBANKCOUNTERPART] = SECConstant.TRUE;
				break;
			}
			case (int)SECConstant.BusinessType.ENTERPRISE_BOND_BID.ID:{				//17.企业债一级
				returnVal[(int)COUNTERPART_TYPE_BANKOFDEPOSIT] = SECConstant.TRUE;
				break;
			}
			case (int)SECConstant.BusinessType.ENTERPRISE_BOND_TRANSACTION.ID:{		//18.企业债二级
				returnVal[(int)COUNTERPART_TYPE_BANKOFDEPOSIT] = SECConstant.TRUE;
				break;
			}
			case (int)SECConstant.BusinessType.TRANSFORMABLE_BOND_BID_ONLINE.ID:{	//19.可转债一级网上申购
				returnVal[(int)COUNTERPART_TYPE_BANKOFDEPOSIT] = SECConstant.TRUE;
				break;
			}
			case (int)SECConstant.BusinessType.TRANSFORMABLE_BOND_BID.ID:{			//20.可转债一级网下申购
				returnVal[(int)COUNTERPART_TYPE_BANKOFDEPOSIT] = SECConstant.TRUE;
				break;
			}
			case (int)SECConstant.BusinessType.TRANSFORMABLE_BOND_TRANSACTION.ID:{	//21.可转债二级
				returnVal[(int)COUNTERPART_TYPE_BANKOFDEPOSIT] = SECConstant.TRUE;
				break;
			}
			case (int)SECConstant.BusinessType.DEBT_TO_EQUITY.ID:{					//22.债转股
				returnVal[(int)COUNTERPART_TYPE_BANKOFDEPOSIT] = SECConstant.TRUE;
				break;
			}
			case (int)SECConstant.BusinessType.ENCLOSED_FUND_BID_ONLINE.ID:{		//23.封闭式基金一级网上申购
				returnVal[(int)COUNTERPART_TYPE_BANKOFDEPOSIT] = SECConstant.TRUE;
				break;
			}
			case (int)SECConstant.BusinessType.ENCLOSED_FUND_BID.ID:{				//24.封闭式基金一级网下申购
				returnVal[(int)COUNTERPART_TYPE_BANKOFDEPOSIT] = SECConstant.TRUE;
				break;
			}
			case (int)SECConstant.BusinessType.ENCLOSED_FUND_TRANSACTION.ID:{		//25.封闭式基金二级
				returnVal[(int)COUNTERPART_TYPE_BANKOFDEPOSIT] = SECConstant.TRUE;
				break;
			}
			case (int)SECConstant.BusinessType.MUTUAL_FUND_SUBSCRIBE.ID:{			//26.开放式基金一级认购
				returnVal[(int)COUNTERPART_TYPE_FUNDMANAGEMENTCO] = SECConstant.TRUE;
				break;
			}
			case (int)SECConstant.BusinessType.MUTUAL_FUND_BID.ID:{					//27.开放式基金二级申购
				returnVal[(int)COUNTERPART_TYPE_FUNDMANAGEMENTCO] = SECConstant.TRUE;
				break;
			}
			case (int)SECConstant.BusinessType.MUTUAL_FUND_REDEEM.ID:{				//28.开放式基金二级赎回
				returnVal[(int)COUNTERPART_TYPE_FUNDMANAGEMENTCO] = SECConstant.TRUE;
				break;
			}
			case (int)SECConstant.BusinessType.MUTUAL_FUND_MELON_CUTTING.ID:{		//29.开放式基金分红
				returnVal[(int)COUNTERPART_TYPE_FUNDMANAGEMENTCO] = SECConstant.TRUE;
				break;
			}
			//---------------------------新添加的
			case (int)SECConstant.BusinessType.CAPITAL_TRANSFER.ID:{				//30.资金划拨
				returnVal[(int)COUNTERPART_TYPE_BANKOFDEPOSIT] = SECConstant.TRUE;
				break;
			}
			case (int)SECConstant.BusinessType.INTEREST_SETTLEMENT.ID:{				//31.资金利息结算
				returnVal[(int)COUNTERPART_TYPE_BANKOFDEPOSIT] = SECConstant.TRUE;
				break;
			}
			case (int)SECConstant.BusinessType.SECURITIES_TRANSFER.ID:{				//32.证券划转
				returnVal[(int)COUNTERPART_TYPE_BANKOFDEPOSIT] = SECConstant.TRUE;
				break;
			}
			case (int)SECConstant.BusinessType.CAPITAL_REPURCHASE.ID:{				//33.资产回购
				returnVal[(int)COUNTERPART_TYPE_ISINTERBANKCOUNTERPART] = SECConstant.TRUE;
				break;
			}
			case (int)SECConstant.BusinessType.FOREIGN_CURRENCY_INVESTMENT.ID:{		//34.结构性投资
				returnVal[(int)COUNTERPART_TYPE_ISINTERBANKCOUNTERPART] = SECConstant.TRUE;
				break;
			}
			case (int)SECConstant.BusinessType.ENTRUST_FINANCING.ID:{				//35.委托理财
				returnVal[(int)COUNTERPART_TYPE_ISSECURITYCO] = SECConstant.TRUE;
				break;
			}
			case (int)SECConstant.BusinessType.BOND_UNDERWRITING.ID:{				//36.债券承销
				returnVal[(int)COUNTERPART_TYPE_ISFLOATERS] = SECConstant.TRUE;
				break;
			}
			case (int)SECConstant.BusinessType.FUND_TRANSFER.ID:{					//37.基金划转
				returnVal[(int)COUNTERPART_TYPE_FUNDMANAGEMENTCO] = SECConstant.TRUE;
				break;
			}
			
		}
		
		return returnVal;
	}
	
	//---------------------------------------------------------------------------
	
	/**
	 * 证券代码对应字段,首发和二级和增发
	 */
	private static String[] securitiesCodes = new String[4];
	static{
		securitiesCodes[0] = "SECURITIESCODE1";							//首发代码
		securitiesCodes[1] = "SECURITIESCODE2";							//二级代码
		securitiesCodes[2] = "SECURITIESCODE3";							//增发代码
		securitiesCodes[3] = "SECURITIESCODE4";							//其他代码
	}
	
	
	public MagnifierHelper(){
		super();
	}
	
	/**
	 * 根据业务类型ID获得证券代码需要的字段
	 * @param transactionTypeId
	 * @return
	 */
	public static String getSecuritiesCodeField(long transactionTypeId) throws SecuritiesException{
		String returnVal = "";
		if (!(transactionTypeId>=0 && transactionTypeId<5) && String.valueOf(transactionTypeId).length()<2){
			throw new SecuritiesException("证券代码放大镜传入交易类型代码错误",null);
		}
		
		int intTransactionTypeId = -1;
		if (transactionTypeId > 11){
			intTransactionTypeId = Integer.parseInt(String.valueOf(transactionTypeId).substring(0,2));
		}
		else{
			intTransactionTypeId = (int)transactionTypeId;
		}
		
		switch (intTransactionTypeId){
			/*case (int)SECConstant.BusinessType.CAPITAL_LANDING.ID:{					//1.资金拆借
				returnVal = securitiesCodes[0];
				break;
			}*/
			case 0:{
				returnVal = securitiesCodes[0];
				break;
			}
			case 1:{
				returnVal = securitiesCodes[0];
				break;
			}
			case 2:{
				returnVal = securitiesCodes[1];
				break;
			}
			case 3:{
				returnVal = securitiesCodes[2];
				break;
			}
			case 4:{
				returnVal = securitiesCodes[3];
				break;
			}
				
			case (int)SECConstant.BusinessType.STOCK_BID_ONLINE.ID:{				//2.股票一级网上申购
				returnVal = securitiesCodes[0];
				break;
			}
			case (int)SECConstant.BusinessType.STOCK_BID.ID:{						//3.股票一级网下申购
				returnVal = securitiesCodes[0];
				break;
			}
			case (int)SECConstant.BusinessType.STOCK_TRANSACTION.ID:{				//4.股票二级
				returnVal = securitiesCodes[1];
				break;
			}
			case (int)SECConstant.BusinessType.CENTRAL_BANK_NOTE_BID.ID:{			//5.央行票据一级
				returnVal = securitiesCodes[0];
				break;
			}
			case (int)SECConstant.BusinessType.CENTRAL_BANK_NOTE_TRANSACTION.ID:{	//6.央行票据二级
				returnVal = securitiesCodes[1];
				break;
			}
			case (int)SECConstant.BusinessType.BANK_BOND_REPURCHASE.ID:{			//7.银行间债券回购
				returnVal = securitiesCodes[1];
				break;
			}
			case (int)SECConstant.BusinessType.EXCHANGECENTER_BOND_REPURCHASE.ID:{	//8.交易所债券回购
				returnVal = securitiesCodes[1];
				break;
			}
			case (int)SECConstant.BusinessType.BANK_NATIONAL_BOND_BID.ID:{			//9.银行间国债一级
				returnVal = securitiesCodes[0];
				break;
			}
			case (int)SECConstant.BusinessType.BANK_NATIONAL_BOND_TRANSACTION.ID:{	//10.银行间国债二级
				returnVal = securitiesCodes[1];
				break;
			}	
			case (int)SECConstant.BusinessType.EXCHANGECENTER_NATIONAL_BOND_BID.ID:{//11.交易所国债一级
				returnVal = securitiesCodes[0];
				break;
			}
			case (int)SECConstant.BusinessType.EXCHANGECENTER_NATIONAL_BOND_TRANSACTION.ID:{//12.交易所国债二级
				returnVal = securitiesCodes[1];
				break;
			}
			case (int)SECConstant.BusinessType.FINANCIAL_BOND_BID.ID:{				//13.金融债一级
				returnVal = securitiesCodes[0];
				break;
			}
			case (int)SECConstant.BusinessType.FINANCIAL_BOND_TRANSACTION.ID:{		//14.金融债二级
				returnVal = securitiesCodes[1];
				break;
			}
			case (int)SECConstant.BusinessType.POLICY_RELATED_FINANCIAL_BOND_BID.ID:{//15.政策性金融债一级
				returnVal = securitiesCodes[0];
				break;
			}
			case (int)SECConstant.BusinessType.POLICY_RELATED_FINANCIAL_BOND_TRANSACTION.ID:{//16.政策性金融债二级
				returnVal = securitiesCodes[1];
				break;
			}
			case (int)SECConstant.BusinessType.ENTERPRISE_BOND_BID.ID:{				//17.企业债一级
				returnVal = securitiesCodes[0];
				break;
			}
			case (int)SECConstant.BusinessType.ENTERPRISE_BOND_TRANSACTION.ID:{		//18.企业债二级
				returnVal = securitiesCodes[1];
				break;
			}
			case (int)SECConstant.BusinessType.TRANSFORMABLE_BOND_BID_ONLINE.ID:{	//19.可转债一级网上申购
				returnVal = securitiesCodes[0];
				break;
			}
			case (int)SECConstant.BusinessType.TRANSFORMABLE_BOND_BID.ID:{			//20.可转债一级网下申购
				returnVal = securitiesCodes[0];
				break;
			}
			case (int)SECConstant.BusinessType.TRANSFORMABLE_BOND_TRANSACTION.ID:{	//21.可转债二级
				returnVal = securitiesCodes[1];
				break;
			}
			case (int)SECConstant.BusinessType.DEBT_TO_EQUITY.ID:{					//22.债转股
				returnVal = securitiesCodes[1];
				break;
			}
			case (int)SECConstant.BusinessType.ENCLOSED_FUND_BID_ONLINE.ID:{		//23.封闭式基金一级网上申购
				returnVal = securitiesCodes[0];
				break;
			}
			case (int)SECConstant.BusinessType.ENCLOSED_FUND_BID.ID:{				//24.封闭式基金一级网下申购
				returnVal = securitiesCodes[0];
				break;
			}
			case (int)SECConstant.BusinessType.ENCLOSED_FUND_TRANSACTION.ID:{		//25.封闭式基金二级
				returnVal = securitiesCodes[1];
				break;
			}
			case (int)SECConstant.BusinessType.MUTUAL_FUND_SUBSCRIBE.ID:{			//26.开放式基金一级认购
				returnVal = securitiesCodes[0];
				break;
			}
			case (int)SECConstant.BusinessType.MUTUAL_FUND_BID.ID:{					//27.开放式基金二级申购
				returnVal = securitiesCodes[1];
				break;
			}
			case (int)SECConstant.BusinessType.MUTUAL_FUND_REDEEM.ID:{				//28.开放式基金二级赎回
				returnVal = securitiesCodes[1];
				break;
			}
			case (int)SECConstant.BusinessType.MUTUAL_FUND_MELON_CUTTING.ID:{		//29.开放式基金分红
				returnVal = securitiesCodes[1];
				break;
			}
			case (int)SECConstant.BusinessType.SECURITIES_TRANSFER.ID:{				//30.证券划转XXXX
				returnVal = securitiesCodes[1];
				break;
			}
			case (int)SECConstant.BusinessType.ENTRUST_FINANCING.ID:{				//31.委托理财
				returnVal = securitiesCodes[1]; 
				break;
			}
			case (int)SECConstant.BusinessType.FUND_TRANSFER.ID:{					//32.基金划转
				returnVal = securitiesCodes[1]; 
				break;
			}
			case (int)SECConstant.BusinessType.BOND_UNDERWRITING.ID:{				//33.债券承销
				returnVal = securitiesCodes[0]; 
				break;
			}
		}
		return returnVal;
	}
	
	//-----------------------------------------------------------------------------
	
	
	/**
	 * 返回所有Id的逗号分割的拼串
	 */
	public static String getSecuritiesTypeIds(long transactionTypeId , long currencyId,long typeId)throws SecuritiesException{
		String ids = "";
		
		long[] securitiesIds = getSecuritiesTypeId(transactionTypeId , currencyId,typeId);
		
		if (securitiesIds!=null && securitiesIds.length>0){
			for (int n=0;n<securitiesIds.length;n++){
				Log.print("类型"+n+":"+securitiesIds[n]);
				if (n==0){
					ids = String.valueOf(securitiesIds[n]);
				}
				else{
					ids += "," + securitiesIds[n];
				}
			}
		}
		
		return ids;
	}
	
	/**
	 * 通过交易类型和币种确定当前的证券类型
	 */
	public static long[] getSecuritiesTypeId(long transactionTypeId , long currencyId,long typeId)throws SecuritiesException{
		long[] securitiesTypeId = null;
		if (!(transactionTypeId>=0 && transactionTypeId<5) && String.valueOf(transactionTypeId).length()<2){
			throw new SecuritiesException("证券代码放大镜传入交易类型代码错误",null);
		}
		int intTransactionTypeId = -1;
		
		if (transactionTypeId > 10){
			intTransactionTypeId = Integer.parseInt(String.valueOf(transactionTypeId).substring(0,2));
		}
		else{
			intTransactionTypeId = (int)transactionTypeId;
		}
		
		switch (intTransactionTypeId){
			/*case (int)SECConstant.BusinessType.CAPITAL_LANDING.ID:{					//1.资金拆借
				returnVal = securitiesCodes[0];
				break;
			}*/
			case 0:{																//所有证券类型
				securitiesTypeId = SECConstant.SecuritiesType.getAllCode();
				break;
			}
			case 1:{
				securitiesTypeId = SECConstant.SecuritiesType.getAllCode();
				break;
			}
			case 2:{
				securitiesTypeId = SECConstant.SecuritiesType.getAllCode();
				break;
			}
			case 3:{
				securitiesTypeId = SECConstant.SecuritiesType.getAllCode();
				break;
			}
			case 4:{
				securitiesTypeId = SECConstant.SecuritiesType.getAllCode();
				break;
			}
			case (int)SECConstant.BusinessType.STOCK_BID_ONLINE.ID:{				//2.股票一级网上申购
				if (currencyId == Constant.CurrencyType.RMB){
					securitiesTypeId = new long[1];
					securitiesTypeId[0] = SECConstant.SecuritiesType.STOCK_A;
				}
				else if (currencyId == Constant.CurrencyType.USD){
					securitiesTypeId = new long[1];
					securitiesTypeId[0] = SECConstant.SecuritiesType.STOCK_B;
				}
				break;
			}
			case (int)SECConstant.BusinessType.STOCK_BID.ID:{						//3.股票一级网下申购
				if (currencyId == Constant.CurrencyType.RMB){
					securitiesTypeId = new long[1];
					securitiesTypeId[0] = SECConstant.SecuritiesType.STOCK_A;
				}
				else if (currencyId == Constant.CurrencyType.USD){
					securitiesTypeId = new long[1];
					securitiesTypeId[0] = SECConstant.SecuritiesType.STOCK_B;
				}
				break;
			}
			case (int)SECConstant.BusinessType.STOCK_TRANSACTION.ID:{				//4.股票二级
				if (currencyId == Constant.CurrencyType.RMB){
					securitiesTypeId = new long[1];
					securitiesTypeId[0] = SECConstant.SecuritiesType.STOCK_A;
				}
				else if (currencyId == Constant.CurrencyType.USD){
					securitiesTypeId = new long[1];
					securitiesTypeId[0] = SECConstant.SecuritiesType.STOCK_B;
				}
				break;
			}
			case (int)SECConstant.BusinessType.CENTRAL_BANK_NOTE_BID.ID:{			//5.央行票据一级
				securitiesTypeId = new long[1];
				securitiesTypeId[0] = SECConstant.SecuritiesType.CENTRAL_BANK_NOTE;
				break;
			}
			case (int)SECConstant.BusinessType.CENTRAL_BANK_NOTE_TRANSACTION.ID:{	//6.央行票据二级
				securitiesTypeId = new long[1];
				securitiesTypeId[0] = SECConstant.SecuritiesType.CENTRAL_BANK_NOTE;
				break;
			}
			case (int)SECConstant.BusinessType.BANK_BOND_REPURCHASE.ID:{			//7.银行间债券回购**
				securitiesTypeId = new long[1];
				securitiesTypeId[0] = SECConstant.SecuritiesType.BANK_NATIONAL_BOND;
				break;
			}
			case (int)SECConstant.BusinessType.EXCHANGECENTER_BOND_REPURCHASE.ID:{	//8.交易所债券回购
				securitiesTypeId = new long[2];
				securitiesTypeId[0] = SECConstant.SecuritiesType.EXCHANGECENTER_BOND_REPURCHASE;
				securitiesTypeId[1] = SECConstant.SecuritiesType.EXCHANGECENTER_ENTERPRISE_BOND;
				break;
			}
			case (int)SECConstant.BusinessType.BANK_NATIONAL_BOND_BID.ID:{			//9.银行间国债一级
				securitiesTypeId = new long[1];
				securitiesTypeId[0] = SECConstant.SecuritiesType.BANK_NATIONAL_BOND;
				break;
			}
			case (int)SECConstant.BusinessType.BANK_NATIONAL_BOND_TRANSACTION.ID:{	//10.银行间国债二级
				securitiesTypeId = new long[1];
				securitiesTypeId[0] = SECConstant.SecuritiesType.BANK_NATIONAL_BOND;
				break;
			}	
			case (int)SECConstant.BusinessType.EXCHANGECENTER_NATIONAL_BOND_BID.ID:{//11.交易所国债一级
				securitiesTypeId = new long[1];
				securitiesTypeId[0] = SECConstant.SecuritiesType.EXCHANGECENTER_NATIONAL_BOND;
				break;
			}
			case (int)SECConstant.BusinessType.EXCHANGECENTER_NATIONAL_BOND_TRANSACTION.ID:{//12.交易所国债二级
				securitiesTypeId = new long[1];
				securitiesTypeId[0] = SECConstant.SecuritiesType.EXCHANGECENTER_NATIONAL_BOND;
				break;
			}
			case (int)SECConstant.BusinessType.FINANCIAL_BOND_BID.ID:{				//13.金融债一级
				securitiesTypeId = new long[1];
				securitiesTypeId[0] = SECConstant.SecuritiesType.FINANCIAL_BOND;
				break;
			}
			case (int)SECConstant.BusinessType.FINANCIAL_BOND_TRANSACTION.ID:{		//14.金融债二级
				securitiesTypeId = new long[1];
				securitiesTypeId[0] = SECConstant.SecuritiesType.FINANCIAL_BOND;
				break;
			}
			case (int)SECConstant.BusinessType.POLICY_RELATED_FINANCIAL_BOND_BID.ID:{//15.政策性金融债一级
				securitiesTypeId = new long[1];
				securitiesTypeId[0] = SECConstant.SecuritiesType.POLICY_RELATED_FINANCIAL_BOND;
				break;
			}
			case (int)SECConstant.BusinessType.POLICY_RELATED_FINANCIAL_BOND_TRANSACTION.ID:{//16.政策性金融债二级
				securitiesTypeId = new long[1];
				securitiesTypeId[0] = SECConstant.SecuritiesType.POLICY_RELATED_FINANCIAL_BOND;
				break;
			}
			case (int)SECConstant.BusinessType.ENTERPRISE_BOND_BID.ID:{				//17.企业债一级
				securitiesTypeId = new long[1];
				securitiesTypeId[0] = SECConstant.SecuritiesType.ENTERPRISE_BOND;
				break;
			}
			case (int)SECConstant.BusinessType.ENTERPRISE_BOND_TRANSACTION.ID:{		//18.企业债二级
				securitiesTypeId = new long[1];
				securitiesTypeId[0] = SECConstant.SecuritiesType.ENTERPRISE_BOND;
				break;
			}
			case (int)SECConstant.BusinessType.TRANSFORMABLE_BOND_BID_ONLINE.ID:{	//19.可转债一级网上申购
				securitiesTypeId = new long[1];
				securitiesTypeId[0] = SECConstant.SecuritiesType.TRANSFORMABLE_BOND;
				break;
			}
			case (int)SECConstant.BusinessType.TRANSFORMABLE_BOND_BID.ID:{			//20.可转债一级网下申购
				securitiesTypeId = new long[1];
				securitiesTypeId[0] = SECConstant.SecuritiesType.TRANSFORMABLE_BOND;
				break;
			}
			case (int)SECConstant.BusinessType.TRANSFORMABLE_BOND_TRANSACTION.ID:{	//21.可转债二级
				securitiesTypeId = new long[1];
				securitiesTypeId[0] = SECConstant.SecuritiesType.TRANSFORMABLE_BOND;
				break;
			}
			case (int)SECConstant.BusinessType.DEBT_TO_EQUITY.ID:{					//22.债转股********
				if (typeId == 0){					//类型一可转债现券
					securitiesTypeId = new long[1];
					securitiesTypeId[0] = SECConstant.SecuritiesType.TRANSFORMABLE_BOND;
				}
				else if (typeId == 1){				//类型二A股
					securitiesTypeId = new long[1];
					securitiesTypeId[0] = SECConstant.SecuritiesType.STOCK_A;
				}
				break;
			}
			case (int)SECConstant.BusinessType.ENCLOSED_FUND_BID_ONLINE.ID:{		//23.封闭式基金一级网上申购
				securitiesTypeId = new long[1];
				securitiesTypeId[0] = SECConstant.SecuritiesType.ENCLOSED_FUND;
				break;
			}
			case (int)SECConstant.BusinessType.ENCLOSED_FUND_BID.ID:{				//24.封闭式基金一级网下申购
				securitiesTypeId = new long[1];
				securitiesTypeId[0] = SECConstant.SecuritiesType.ENCLOSED_FUND;
				break;
			}
			case (int)SECConstant.BusinessType.ENCLOSED_FUND_TRANSACTION.ID:{		//25.封闭式基金二级
				securitiesTypeId = new long[1];
				securitiesTypeId[0] = SECConstant.SecuritiesType.ENCLOSED_FUND;
				break;
			}
			case (int)SECConstant.BusinessType.MUTUAL_FUND_SUBSCRIBE.ID:{			//26.开放式基金一级认购
				securitiesTypeId = new long[1];
				securitiesTypeId[0] = SECConstant.SecuritiesType.MUTUAL_FUND;
				break;
			}
			case (int)SECConstant.BusinessType.MUTUAL_FUND_BID.ID:{					//27.开放式基金二级申购
				securitiesTypeId = new long[1];
				securitiesTypeId[0] = SECConstant.SecuritiesType.MUTUAL_FUND;
				break;
			}
			case (int)SECConstant.BusinessType.MUTUAL_FUND_REDEEM.ID:{				//28.开放式基金二级赎回
				securitiesTypeId = new long[1];
				securitiesTypeId[0] = SECConstant.SecuritiesType.MUTUAL_FUND;
				break;
			}
			case (int)SECConstant.BusinessType.MUTUAL_FUND_MELON_CUTTING.ID:{		//29.开放式基金分红
				securitiesTypeId = new long[1];
				securitiesTypeId[0] = SECConstant.SecuritiesType.MUTUAL_FUND;
				break;
			}
			case (int)SECConstant.BusinessType.SECURITIES_TRANSFER.ID:{				//30.证券划转XXXX
				//securitiesTypeId = SECConstant.SecuritiesType.getAllCode();
                /**
                 * 从业务的角度看，证券划转的对象是交易所现券（不包括开放式基金和债券回购），不能是全部的类型的证券。
                 * 因此不能调用SECConstant.SecuritiesType.getAllCode()方法。
                 * 新加一个方法取得相应的证券类型：SECConstant.SecuritiesType.getExchangeCode()。
                 */
                
                securitiesTypeId = SECConstant.SecuritiesType.getExchangeCode();
                 
				Log.print("当前业务是证券划转,证券类型大小为:"+securitiesTypeId.length);
				break;
			}
			case (int)SECConstant.BusinessType.ENTRUST_FINANCING.ID:{				//31.委托理财
				securitiesTypeId = SECConstant.SecuritiesType.getAllCode(); 
				break;
			}
			case (int)SECConstant.BusinessType.FUND_TRANSFER.ID:{					//32.基金划转
				securitiesTypeId = new long[1];
				securitiesTypeId[0] = SECConstant.SecuritiesType.MUTUAL_FUND;
				break;
			}
			case (int)SECConstant.BusinessType.BOND_UNDERWRITING.ID:{				//33.债券承销
				securitiesTypeId = new long[1];
				securitiesTypeId[0] = SECConstant.SecuritiesType.ENTERPRISE_BOND;
				break;
		}
			
		}
		return securitiesTypeId;
	}
	
	public static void main(String[] args)throws Exception{
		/*String[] str = MagnifierHelper.getCounterpartTypeFields(3101);
		if (str!=null){
			for (int n=0;n<str.length;n++){
				Log.print(str[n]);
			}
		}*/
		Log.print(MagnifierHelper.getCounterpartTypeField(4));
	}
}
