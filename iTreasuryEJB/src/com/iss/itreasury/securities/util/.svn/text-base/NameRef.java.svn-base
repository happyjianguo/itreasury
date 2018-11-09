/*
 * Created on 2003-9-5
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */


package com.iss.itreasury.securities.util;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.securities.deliveryorder.dataentity.DeliveryOrderInfo;
import com.iss.itreasury.securities.exception.SecuritiesDAOException;
import com.iss.itreasury.securities.setting.dataentity.TransactionTypeInfo;
import com.iss.itreasury.settlement.util.SETTHTML;
import com.iss.itreasury.system.approval.bizlogic.ApprovalBiz;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Log;

/**
 * Methods:
 * <ul>
 * <li>getClientNameByID(long lID)  						客户(业务单位),通过ID获得名称
 * <li>getCounterpartNameByID(long lID)  					交易对手,通过ID获得名称
 * <li>getCounterpartCodeByID(long lID)						交易对手,通过ID获得CODE
 * <li>getTransTypeNameByID(long lID)						交易类型,通过ID获得名称
 * <li>getTransAttributeByTransTypeID(long lID)				通过交易类型ID获得交易属性ID
 * <li>getApplyFormCodeByID(long lID)						申请书,通过ID取得CODE
 * <li>getAccountIDFromDeliverOrder(DeliveryOrderInfo doInfo)从交割单中根据交易类型确定该交割单使用的资金账户ID 
 * <li>getTransTypeIDByApplyFormID(long lID)				从申请书ID获得交易类型 ID
 * <li>getDeliveryOrderCodeByID(long lID)					通过交割单ID获得交割单CODE
 * <li>getUserNameCodeByID(long lID)						通过用户ID查找用户名称
 * 
 * <li>getCounterpartBankNameByBankID(long lID)				通过开户行ID查找交易对手开户行名称
 * <li>getCounterpartAccountCodeByBankID(long lID)			通过开户行ID查交易对手帐户代码
 * <li>getCounterpartAccountNameByBankID(long lID)			通过开户行ID查交易对手帐户名称
 * <li>getClientBankNameByBankID(long lID)					通过开户行ID查找业务单位开户行名称
 * <li>getClientAccountCodeByBankID(long lID)				通过开户行ID查业务单位帐户代码
 * <li>getClientAccountNameByBankID(long lID)				通过开户行ID查业务单位帐户名称
 * 
 * <li>getAccountNobyAccountID(long lID)					通过资金账户ID获取其账户CODE
 * <li>getCounterpartNameByID(long lID)						通过交易对手ID查名称
 * <li>getTransactionTypeNamebyTransactionTypeID(long lID)  通过交易类型ID获取交易类型名称
 * <li>getClientCodeById(long lID)							通过客户ID获得客户编号
 * <li>getRemarkDescById(long lID)							通过备注ID获得备注描述
 * <li>getRemarkCodeByID(long lID)							通过备注ID获得备注Code
 * <li>getStockHolderAccountCodeByID(long lID)				通过股东帐户ID获得代码
 * <li>getStockHolderAccountNameByID(long lID)				通过股东帐户ID获得名称
 * <li>getTransactionStartDateByCounterpartID(long lID)		通过交易对手ID获得交易开始日期
 * <li>getTransactionEndDateByCounterpartID(long lID)		通过交易对手ID获得交易结束日期
 * <li>getAmountByCounterpartID(long lID)					通过交易对手ID获得金额
 * <li>gettermByCounterpartID(long lID)						通过交易对手ID获得期限
 * <li>getBusinessTypeByID(long lID)						通过业务ID获得业务类型名称
 * <li>getExchangeCenterCodeByID(long lID)					通过证交所ID获得证交所代码
 * <li>getExchangeCenterNameByID(long lID)					通过证交所ID获得证交所名称
 * <li>getSecuritiesNameByID(long lID)						通过证券ID获得证券名称
 * <li>getSecuritiesCodeByID(long lID)						通过证券ID获得证券CODE
 * <li>getSecuritiesCode2ByID(long lID)						通过证券ID获得证券CODE
 * <li>getPledgeTermBySecuritiesId(long lID)				通过证券ID获得该证券品种的回购期限
 * <li>getAccountNameById(long lID)							通过资金帐户ID获得资金帐户名称
 * <li>getStockHolderAccountCodeByAccountId(long lID)		通过资金帐户ID获得股东帐户Code
 * <li>getStockHolderAccountNameByAccountId(long lID)		通过资金帐户ID获得股东帐户Name
 * <li>getClientNameByAccountId(long lID)					通过资金帐户ID获得业务单位Name
 * <li>getAccountIDFromDeliveryOrder()						从交割单中根据交易类型确定该交割单使用的资金账户ID
 * <li>getSecuritiesTypeNameByID(long lID)					通过证券类型ID获得证券类型名称
 * <li>getCapitalDirectionByTransactionTypeID(long lID)     通过交易类型ID获得资金收付方向。直接返回 "+"或"-"或""
 * <li>getStockDirectionByTransactionTypeID(long lID)       通过交易类型ID获得库存收付方向。直接返回 "+" 或 "-"或""
 * <li>getClientIdsByCounterpartId(long lID)				通过交易对手ID获得所有关联的业务单位的ID
 * 
 * <li>getClientIDByAccountID(long lID)						通过资金帐户ID获得其业务单位ID
 * <li>getCounterpartIDByAccountID(long lID)				通过资金帐户ID获得其交易对手ID
 * <li>getCodeByAccountID(lID)								通过资金帐户ID获得交易帐户Code
 * 
 * <li>getSecuritiesCodeByID(lID,codeField)					通过指定的证券ID和代码字段名获得代码
 * <li>getSubTypeNameById(lID)								通过证券ID获得子类型
 * <li>getSubjectCodeByID(lID)								通过科目ID获得科目代码
 * <li>getSubjectNameByID(lID)								通过科目ID获得科目名称
 * <li>getSubjectIDByCode(code)								通过科目代码获得科目ID
 * <li>getBusinessAttributeNameById(lID)					通过业务性质ID获得名称
 * <li>getBusinessTypeNameByTransactionTypeId(lId)			通过交易类型ID获得业务类型名称
 * <li>getApprovalIDByTransactionTypeID(lTransactionTypeID,lActionID)		通过交易类型ID获得对应的审批设置ID
 * <li>getCounterpartTrusteeCodeByCounterpartId(lID)		通过交易对手ID获得交易对手债券托管账号
 * <li>getContractCodeByID(lID)								通过合同ID获得合同代码
 * <li>getCounterpartIDByContractID(lID)					通过合同ID获得交易对手ID
 * <li>getSecuritiesTypeIDBySecuritiesID(lID)				通过证券ID获得证券类型ID
 * <li>getSettAccountNoById(lID)							通过结算的帐户ID获得帐户代码
 * <li>getNoticeWithSecuritiesId(lID)						通过证券ID获得NoticeWithSecuritiesId
 * </ul>
 *  
 */


/**
 * @author yiwang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class NameRef
{
	
	
	/*************************************************
	 * 					NameRef刷新函数区
	 *************************************************/
	
	/**
	 *  刷新证券类别授信额度设置信息缓存
	 */
	public static void refreshSecuritiesTypeCreditLineInformation(){
		
	}

	/**
	 *  刷新证券种类授信额度设置信息缓存
	 */
	public static void refreshSecuritiesCreditLineInformation(){
		
	}

	/**
	 *  刷新股东帐户设置信息缓存
	 */
	public static void refreshStockHolderInformation(){
		cacheStockHolderAccountCodeToAccountId.clear();
		cacheStockHolderAccountNameToAccountId.clear();
		cacheStockHolderAccountCodeToId.clear();
		cacheStockHolderAccountNameToId.clear();
	}
	
	/**
	 * 刷新资金帐户(包括基金帐户)设置信息缓存
	 */
	public static void refreshAccountInformation(){
		cacheClientNameToAccountId.clear();
		cacheAccountNameToId.clear();
		cacheAccountCodeToId.clear();
		cacheAccountNoToAccountId.clear();
		cacheStockHolderAccountCodeToAccountId.clear();
		cacheStockHolderAccountNameToAccountId.clear();
	}
	
	/**
	 * 刷新交易对手开户行设置信息缓存
	 */
	public static void refreshCounterpartBankInformation(){
		cacheCounterpartBankNameToBankId.clear();
		cacheCounterpartAccountCodeToBankId.clear();
		cacheCounterpartAccountNameToBankId.clear();
	}
	/**
	 * 刷新交易对手(包括开户营业部)设置信息缓存
	 */
	public static void refreshCounterpartInformation(){
		cacheCounterpartNameToId.clear();
		cacheCounterpartCodeToId.clear();
		cacheCounterpartTrusteeCodeToCounterpartId.clear();
	}
	
	/**
	 * 刷新业务单位设置信息缓存
	 */
	public static void refreshClientInformation(){
		cacheClientNameToId.clear();
		cacheClientCodeToId.clear();
		cacheClientNameToAccountId.clear();
	}
	
	/**
	 * 刷新证券资料设置信息缓存
	 */
	public static void refreshSecuritiesInformation(){
		cacheSecuritiesNameToId.clear();
		cacheSecuritiesCodeToId.clear();
		cacheSecuritiesCode2ToId.clear();
		cachePledgeTermToSecuritiesId.clear();
		cacheSecuritiesTypeIDToSecuritiesId.clear();
	}
	
	/**
	 * 刷新证券交易市场设置信息缓存
	 *
	 */
	public static void refreshExchangeCenterInformation(){
		cacheExchangeCenterCodeToId.clear();
		cacheExchangeCenterNameToId.clear();
	}
	
	/*************************************************
	 * 					NameRef刷新函数区
	 *************************************************/
	
	
	/**
	 * 用ID或code获得名称方法的公用方法1
	 * @param strNameField			//名称字段名
	 * @param strTableName			//表名
	 * @param strIdField			//id或者code字段名
	 * @param strValue				//id或者code的值
	 * @return
	 */
	public static String getNameByID(String strNameField,String strTableName,String strIdField,String strValue,HashMap map){
		String strReturn = null;
		try
		{
			String strSQL = "select " + strNameField + " from " + strTableName + " where " + strIdField + "='" + strValue+"'";
			Log.print("NameRef SQL:"+strSQL);
			if (map != null){								//首先从HashMap中获取数据
				strReturn = (String)map.get(strValue);
			}
			if (strReturn == null){						//如果HashMap中没有,则从数据库中获得
				Collection c = SETTHTML.getCommonSelectList(strSQL, strNameField);
				if (c != null)
				{
					Object o = c.iterator().next();
					if (o != null){
						Log.print("NameRef return value:"+o);
						strReturn = String.valueOf(o);
					}
				}
				if (strReturn == null) strReturn = "";
				if (map != null)map.put(strValue,strReturn);				//置入HashMap
			}
			else{
				Log.print("from HashMap:" + strReturn + "  Cache size:" + map.size());
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		if (strReturn == null) strReturn = "";
		return strReturn;
	}
	/**
	 * 用ID或code获得名称方法的公用方法2
	 * @param strSQL					sql
	 * @param strField					要得到的字段
	 * @return
	 */
	public static String getNameByID(String strSQL,String strField){
		String strReturn = "";
		try
		{
			Collection c = SETTHTML.getCommonSelectList(strSQL, strField);
			if (c != null)
			{
				Object object = (Object) c.iterator().next();
				strReturn = object.toString();
			}
		}
		catch (Exception e)
		{
			System.out.println(e.toString());
		}
		return strReturn;
	}
	
	/**
	 * 用一个ID获得所有的Name,中间用","分割
	 * @param strNameField
	 * @param strTableName
	 * @param strIdField
	 * @param strValue
	 * @return
	 */
	public static String getNamesByID(String strNameField,String strTableName,String strIdField,String strValue){
		String strReturn = "";
		try
		{
			String strSQL = "select " + strNameField + " from " + strTableName + " where " + strIdField + "='" + strValue+"'";
			Log.print("NameRef SQL:"+strSQL);
			Collection c = SETTHTML.getCommonSelectList(strSQL, strNameField);
			if (c != null)
			{
				Iterator ite = c.iterator();
				
				while (ite.hasNext()){
					if (strReturn.equals("")){
						strReturn +=ite.next().toString();
					}
					else{
						strReturn += ","+ite.next().toString();
					}
				}
			}
		}
		catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
		return strReturn;
	}
	
	/**
	 * 用一个ID获得所有的Name,中间用","分割 add by zcwang 2007-9-11
	 * @param strNameField
	 * @param strTableName
	 * @param hm
	 * @return
	 */
	public static String getNamesByID(String strNameField,String strTableName,HashMap hm){
		String strReturn = "";
		try
		{
			
			String strSQL = "select " + strNameField + " from " + strTableName + " where " + getSQLPlus(hm);
			Log.print("NameRef SQL:"+strSQL);
			Collection c = SETTHTML.getCommonSelectList(strSQL, strNameField);
			if (c != null)
			{
				Iterator ite = c.iterator();
				
				while (ite.hasNext()){
					if (strReturn.equals("")){
						strReturn +=ite.next().toString();
					}
					else{
						strReturn += ","+ite.next().toString();
					}
				}
			}
		}
		catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
		return strReturn;
	}
	/*
	 * sql 查询条件 add by zcwang 2007-9-11
	 * @param hm
	 */
	public static String getSQLPlus(HashMap hm)
	{
		String sqlplus = "";
		String key = "";
		try
		{
			Set set = hm.keySet();
			for (Iterator it = set.iterator();it.hasNext();)
			{
				key = it.next().toString();
				if(hm.get(key) != null)
				{
					sqlplus += key + " = "+hm.get(key)+" and ";
				}
			}
			if(!sqlplus.equals(""))
			{
				sqlplus = sqlplus.substring(0, sqlplus.length()-4);
			}
			
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}
		
		return sqlplus;
		
	}
	/**
	 * 用一个ID获得所有的Name,中间用","分割
	 * @param strSql							SQL语句
	 * @param strField							要获得的字段名
	 * @return
	 */
	public static String getNamesByID(String strSql,String strField){
		String strReturn = "";
		try
		{
			
			Log.print("NameRef SQL:"+strSql);
			Collection c = SETTHTML.getCommonSelectList(strSql, strField);
			if (c != null)
			{
				Iterator ite = c.iterator();
				
				while (ite.hasNext()){
					if (strReturn.equals("")){
						strReturn +=ite.next().toString();
					}
					else{
						strReturn += ","+ite.next().toString();
					}
				}
			}
		}
		catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
		return strReturn;
	}
	
	/**
	 * 功能：通过客户ID获得客户名称
	 * @param lID
	 * @return
	 * @
	 */
	private static HashMap cacheClientNameToId = new HashMap();
	public static String getClientNameByID(long lID)
	{
		return getNameByID("Name","SEC_Client","ID",String.valueOf(lID),cacheClientNameToId);
	}
	/**
	 * 通过交易对手ID获得交易对手名称
	 * @param lID
	 * @return
	 */
	private static HashMap cacheCounterpartNameToId = new HashMap();
	public static String getCounterpartNameByID(long lID)
	{
		return getNameByID("Name","SEC_Counterpart","ID",String.valueOf(lID),cacheCounterpartNameToId);
		//return getNameByID("COUNTERPARTNAME","CRA_COUNTERPART","ID",String.valueOf(lID),cacheCounterpartNameToId);
		
	}
	/**
	 * 通过交易对手ID获得交易对手CODE
	 * @param lID
	 * @return
	 */
	private static HashMap cacheCounterpartCodeToId = new HashMap();
	public static String getCounterpartCodeByID(long lID){
		return getNameByID("Code","SEC_Counterpart","ID",String.valueOf(lID),cacheCounterpartCodeToId);
	}
	/**
	 * 通过交易类型ID获得交易类型名称
	 * @param lID
	 * @return
	 */
	private static HashMap cacheTransTypeNameToId = new HashMap();
	public static String getTransTypeNameByID(long lID){
		return getNameByID("Name","SEC_TransactionType","ID",String.valueOf(lID),cacheTransTypeNameToId);
	}
	/**
	 * 通过业务类型取得业务性质ID
	 * @param lID
	 * @return
	 */
	public static long getTransAttributeByTransTypeID(long lID){ 			//SEC_BusinessType     SEC_BusinessAttribute
		String strReturn = "";

			String strSQL = "select att.Name name from SEC_BusinessType btype,SEC_TransactionType ttype,SEC_BusinessAttribute att \n"+
				 "where btype.BusinessAttributeID = att.id and btype.id = ttype.BUSINESSTYPEID and ttype.id="+lID;
			Log.print(strSQL);
			String bizAttr = getNameByID(strSQL,"name");
			return SECConstant.BusinessAttribute.getIDByName(bizAttr);
	}
	/**
	 * 通过ID获得申请书编号
	 * @param lID
	 * @return
	 */
	public static String getApplyFormCodeByID(long lID){
		return getNameByID("Code","SEC_ApplyForm","ID",String.valueOf(lID),null);
	}
		

	
	/**
	 * 通过申请书ID获得交易类型ID
	 * @param lID
	 * @return
	 */
	public static String getTransTypeIDByApplyFormID(long lID){
		return getNameByID("TRANSACTIONTYPEID","SEC_ApplyForm","ID",String.valueOf(lID),null);
	}
	
	/**
	 * 通过交割单id查交割单Code
	 * @param lID
	 * @return
	 */
	public static String getDeliveryOrderCodeByID(long lID){
		return getNameByID("Code","SEC_DeliveryOrder","ID",String.valueOf(lID),null);
	}
	
	/**
	 * 通过用户ID获得用户名称 
	 * @param lID
	 * @return
	 */
	public static String getUserNameCodeByID(long lID){
		return getNameByID("sName","UserInfo","ID",String.valueOf(lID),null);
	}
	//---------------------------
	// 查询交易对手帐户资料
	//---------------------------
	/**
	 * 通过开户行ID查交易对手开户行名称
	 * @param lID
	 * @return
	 */
	private static HashMap cacheCounterpartBankNameToBankId = new HashMap();
	public static String getCounterpartBankNameByBankID(long lID){
		return getNameByID("BankName","SEC_CounterpartBankAccount","id",String.valueOf(lID),cacheCounterpartBankNameToBankId);
	}
	
	/**
	 * 通过开户行ID查交易对手帐户代码
	 * @param lID
	 * @return
	 */
	private static HashMap cacheCounterpartAccountCodeToBankId = new HashMap();
	public static String getCounterpartAccountCodeByBankID(long lID){
		return getNameByID("BankAccountCode","SEC_CounterpartBankAccount","id",String.valueOf(lID),cacheCounterpartAccountCodeToBankId);
	
	}
	/**
	 * 通过开户行ID查交易对手帐户名称
	 * @param lID
	 * @return
	 */
	private static HashMap cacheCounterpartAccountNameToBankId = new HashMap();
	public static String getCounterpartAccountNameByBankID(long lID){
		return getNameByID("BankAccountName","SEC_CounterpartBankAccount","id",String.valueOf(lID),cacheCounterpartAccountNameToBankId);
	}
	//-----------------------------
	//  查询业务单位帐户资料
	//-----------------------------
	/**
	 * 通过开户行ID查业务单位帐户名称
	 * @param lID
	 * @return
	 */
	public static String getClientBankNameByBankID(long lID){
		return getNameByID("sName","sett_branch","Id",String.valueOf(lID),null);
	}
	
	/**
	 * 通过开户行ID业务单位帐户Code
	 * @param lID
	 * @return
	 */
	public static String getClientAccountCodeByBankID(long lID){
		return getNameByID("sBankAccountCode","sett_branch","Id",String.valueOf(lID),null);
	}
	/**
	 * 通过开户行ID业务单位帐户名称
	 * @param lID
	 * @return
	 */
	public static String getClientAccountNameByBankID(long lID){
		return getNameByID("sEnterpriseName","sett_branch","Id",String.valueOf(lID),null);
	}

	//----------------------------
	/**			cacheTransTypeNameToId
	 * 通过交易类型ID获取交易类型名称
	 * @param lID
	 * @return
	 * */
	public static String getTransactionTypeNamebyTransactionTypeID(long lID){
		return getNameByID("Name","SEC_TransactionType","id",String.valueOf(lID),cacheTransTypeNameToId);		
	}
	
	private static HashMap cacheAccountNoToAccountId = new HashMap();
	public static String getAccountNobyAccountID(long lID){
		return getNameByID("code","SEC_Account","id",String.valueOf(lID),cacheAccountNoToAccountId);
	}
	/**
	 * 功能：通过客户ID获得客户名称
	 * @param lID
	 * @return
	 * @
	 */
	private static HashMap cacheClientCodeToId = new HashMap();
	public static String getClientCodeByID(long lID)
	{
		return getNameByID("CODE","SEC_Client","ID",String.valueOf(lID),cacheClientCodeToId);
	}
	
	/**
	 * 通过备注的ID获得备注描述
	 * @param lID
	 * @return
	 */
	public static String getRemarkDescByID(long lID){
		return getNameByID("DESCRIPTION","SEC_Remark","ID",String.valueOf(lID),null);
	}
	/**
	 * 通过备注的ID获得备注CODE
	 * @param lID
	 * @return
	 */
	public static String getRemarkCodeByID(long lID){
		return getNameByID("CODE","SEC_Remark","ID",String.valueOf(lID),null);
	}
	/**
	 * 通过股东帐户ID获得CODE
	 * @param lID
	 * @return
	 */
	private static HashMap cacheStockHolderAccountCodeToId = new HashMap();
	public static String getStockHolderAccountCodeByID(long lID){
		return getNameByID("CODE","SEC_StockHolderAccount","ID",String.valueOf(lID),cacheStockHolderAccountCodeToId);
	}

	/**
	 * 通过股东帐户ID获得CODE
	 * @param lID
	 * @return
	 */
	private static HashMap cacheStockHolderAccountNameToId = new HashMap();
	public static String getStockHolderAccountNameByID(long lID){
		return getNameByID("NAME","SEC_StockHolderAccount","ID",String.valueOf(lID),cacheStockHolderAccountNameToId);
	}
	
	/**
	 * 通过交易对手ID获得交易开始日期
	 */
	public static String getTransactionStartDateByCounterpartID(long lID){
		return getNameByID("transactionStartDate","SEC_VCounterpartMagnifier_1","ID",String.valueOf(lID),null);
	}
	/**
	 * 通过交易对手ID获得交易结束日期
	 * @param lID
	 * @return
	 */
	public static String getTransactionEndDateByCounterpartID(long lID){
		return getNameByID("transactionEndDate","SEC_VCounterpartMagnifier_1","ID",String.valueOf(lID),null);
	}
	/**
	 * 通过交易对手ID获得金额
	 * @param lID
	 * @return
	 */
	public static String getPledgeSecuritiesAmountByCounterpartID(long lID){
		return getNameByID("pledgeSecuritiesAmount","SEC_VCounterpartMagnifier_1","ID",String.valueOf(lID),null);
	}
	/**
	 * 通过交易对手ID获得期限
	 * @param lID
	 * @return
	 */
	public static String getTermByCounterpartID(long lID){
		return getNameByID("term","SEC_VCounterpartMagnifier_1","ID",String.valueOf(lID),null);
	}
	
	/**
	 * 通过业务ID获得业务类型名称
	 * @param lID
	 * @return
	 */
	private static HashMap cacheBusinessTypeToId = new HashMap();
	public static String getBusinessTypeByID(long lID){
		return getNameByID("Name","SEC_BusinessType","ID",String.valueOf(lID),cacheBusinessTypeToId);
	}
	
	/**
	 * 通过证交所ID获得证交所code
	 * @param lID
	 * @return
	 */
	private static HashMap cacheExchangeCenterCodeToId = new HashMap();
	public static String getExchangeCenterCodeByID(long lID){
		return getNameByID("Code","SEC_ExchangeCenter","ID",String.valueOf(lID),cacheExchangeCenterCodeToId);
	}
	
	/**
	 * 通过证交所ID获得证交所名称
	 * @param lID
	 * @return
	 */
	private static HashMap cacheExchangeCenterNameToId = new HashMap();
	public static String getExchangeCenterNameByID(long lID){
		return getNameByID("Name","SEC_ExchangeCenter","ID",String.valueOf(lID),cacheExchangeCenterNameToId);
	}
	
	/**
	 * 通过证券ID获得证券名称
	 * @param lID
	 * @return
	 */
	private static HashMap cacheSecuritiesNameToId = new HashMap();
	public static String getSecuritiesNameByID(long lID){
		return getNameByID("shortName","SEC_Securities","ID",String.valueOf(lID),cacheSecuritiesNameToId);
	}
	
	/**
	 * 得到证券一级代码
	 * @param lID
	 * @return
	 */
	private static HashMap cacheSecuritiesCodeToId = new HashMap();
	public static String getSecuritiesCodeByID(long lID){
		return getNameByID("SECURITIESCODE1","SEC_Securities","ID",String.valueOf(lID),cacheSecuritiesCodeToId);
	}
	
	/**
	 * 得到证券二级代码
	 * @param lID
	 * @return
	 */
	private static HashMap cacheSecuritiesCode2ToId = new HashMap();
	public static String getSecuritiesCode2ByID(long lID){
		return getNameByID("SECURITIESCODE2","SEC_Securities","ID",String.valueOf(lID),cacheSecuritiesCode2ToId);
	}
	
	/**
	 * 通过指定的securitiesCode字段查找证券代码
	 * @param lID
	 * @param codeField
	 * @return
	 */
	public static String getSecuritiesCodeByID(long lID,String codeField){
		return getNameByID(codeField,"SEC_Securities","ID",String.valueOf(lID),null);
	}
	
	/**
	 * 通过证券ID获得证券名称
	 * @param lID
	 * @return
	 */
	private static HashMap cachePledgeTermToSecuritiesId = new HashMap();
	public static String getPledgeTermBySecuritiesId(long lID){
		return getNameByID("Term","SEC_Securities","ID",String.valueOf(lID),cachePledgeTermToSecuritiesId);
	}
	/**
	 * 通过资金帐户ID获得资金帐户名称
	 * @param lID
	 * @return
	 */
	private static HashMap cacheAccountNameToId = new HashMap();
	public static String getAccountNameById(long lID){
		return getNameByID("AccountName","SEC_Account","ID",String.valueOf(lID),cacheAccountNameToId);
	}
	
	/**
	 * 通过资金帐户ID获得资金帐户Code
	 * @param lID
	 * @return
	 */
	private static HashMap cacheAccountCodeToId = new HashMap();
	public static String getAccountCodeById(long lID){
		return getNameByID("AccountCode","SEC_Account","ID",String.valueOf(lID),cacheAccountCodeToId);
	}
	
	/**
	 * 通过资金帐户ID获得股东帐户代码
	 * @param lID
	 * @return
	 */
	private static HashMap cacheStockHolderAccountCodeToAccountId = new HashMap();
	public static String getStockHolderAccountCodeByAccountId(long lID){
		return getNameByID("holderAccountCode","SEC_VAccountMagnifier","ID",String.valueOf(lID),cacheStockHolderAccountCodeToAccountId);
	}
	
	/**
	 * 通过资金帐户ID获得股东帐户名称
	 * @param lID
	 * @return
	 */
	private static HashMap cacheStockHolderAccountNameToAccountId = new HashMap();
	public static String getStockHolderAccountNameByAccountId(long lID){
		return getNameByID("holderAccountName","SEC_VAccountMagnifier","ID",String.valueOf(lID),cacheStockHolderAccountNameToAccountId);
	}
	/**
	 * 通过资金帐户ID获得业务单位名称
	 * @param lID
	 * @return
	 */
	private static HashMap cacheClientNameToAccountId = new HashMap();
	public static String getClientNameByAccountId(long lID){
		return getNameByID("clientName","SEC_VAccountMagnifier","ID",String.valueOf(lID),cacheClientNameToAccountId);
	}
	
	/**
	 * 从交割单中根据交易类型确定该交割单使用的资金账户ID（银行间业务 VS 交易所或开放式基金业务）
	 * @param DeliveryOrderInfo
	 * @param isNeedNull 是否允许是银行间业务时返回ID为无效值(买卖登记薄使用)
	 * @return
	 */
	public static long getAccountIDFromDeliveryOrder(DeliveryOrderInfo doInfo,boolean isNeedNull) throws SecuritiesDAOException{
		TransactionTypeInfo transTypeInfo = null;
		try {
			transTypeInfo = SECConstant.BusinessType.getTransactionTypeInfoByID(doInfo.getTransactionTypeId());
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}
		long accountID = -1;
		if(transTypeInfo.getIsNeedNotifyForm() <= 0)
			accountID = doInfo.getAccountId();
		else if(!isNeedNull)
			accountID = doInfo.getCompanyAccountId();
		return accountID;
		
	}	
	/**
	 * 通过证券类型ID获得证券类型名称
	 * @param lID
	 * @return
	 */
	private static HashMap cacheSecuritiesTypeNameToID = new HashMap();
	public static String getSecuritiesTypeNameByID(long lID){
		return getNameByID("Name","SEC_SecuritiesType","ID",String.valueOf(lID),cacheSecuritiesTypeNameToID);
	}
	/**
	 * 通过交易类型ID获得收付方向。
	 * 返回:收="+" 付="-" 不收不付=""
	 * @param lID
	 * @return
	 */
	private static HashMap cacheCapitalDirectionByTransactionTypeID = new HashMap();
	public static String getCapitalDirectionByTransactionTypeID(long lID){
		String direction = "";
		
		String tmpStr = getNameByID("CapitalDirection","SEC_TransactionType","ID",String.valueOf(lID),cacheCapitalDirectionByTransactionTypeID);
		if(tmpStr.equals("1") || tmpStr.equals("4") || tmpStr.equals("6")){
			direction = "+";
		}else if(tmpStr.equals("2") || tmpStr.equals("5") || tmpStr.equals("7")){
			direction = "-";
		}else if(tmpStr.equals("0")){
			direction = "";
		}
		
		return direction;
	}

	/**
	 * 通过交易类型ID获得收付方向。
	 * 返回:入库="+" 出库="-" 不入不出=""
	 * @param lID
	 * @return
	 */
	private static HashMap cacheStockDirectionToTransactionTypeId = new HashMap();
	public static String getStockDirectionByTransactionTypeID(long lID){
		String direction = "";
		
		String tmpStr = getNameByID("StockDirection","SEC_TransactionType","ID",String.valueOf(lID),cacheStockDirectionToTransactionTypeId);
		if(tmpStr.equals("1")){
			direction = "+";
		}else if(tmpStr.equals("2")){
			direction = "-";
		}else if(tmpStr.equals("0")){
			direction = "";
		}
		
		return direction;
	}
	/**
	 * 通过交易对手ID获得所有通过帐户表关联的业务单位ID,中间用","分割
	 * @param lID
	 * @return
	 */
	public static String getClientIdsByCounterpartId(long lID){
		return getNamesByID("clientId","SEC_VCounterClientMagnifier","counterpartId",String.valueOf(lID));
	}
	
	/**
	 * 通过资金帐户ID获得业务单位ID
	 * @param lID
	 * @return
	 */
	public static String getClientIDByAccountID(long lID){
		return getNamesByID("clientId","SEC_Account","id",String.valueOf(lID));
	}
	/**
	 * 通过资金帐户ID获得交易对手ID
	 * @param lID
	 * @return
	 */
	public static String getCounterpartIDByAccountID(long lID){
		return getNamesByID("counterpartId","SEC_Account","id",String.valueOf(lID));
	}
	
	/**
	 * 通过资金帐户ID获得交易帐户Code
	 * @param lID
	 * @return
	 */
	public static String getCodeByAccountID(long lID){
		return getNamesByID("Code","SEC_Account","id",String.valueOf(lID));
	}
	
	/**
	 * 通过证券ID获得证券子类型 
	 * @param lID
	 * @return
	 */
	public static String getSubTypeNameById(long lID){
		String subTypeName = "";

		subTypeName = SECConstant.SecuritiesSubType.getName(lID);

		return subTypeName;
	}
	
	

	/**
	 * 通过科目ID获得科目代码
	 */
	public static String getSubjectCodeByID(long lID){
		return getNamesByID("","SETT_VGLSUBJECTDEFINITION","id",String.valueOf(lID));
	}
	
	/**
	 * 通过科目ID获得科目代码
	 */
	public static String getSubjectNameByID(long lID){
		return getNamesByID("sSubjectName","SETT_VGLSUBJECTDEFINITION","id",String.valueOf(lID));
	}
	
	/**
	 * 通过科目代码获得科目ID
	 */
	public static String getSubjectIDByCode(String code){
		return getNamesByID("id","SETT_VGLSUBJECTDEFINITION","sSubjectCode",code);
	}
	
	/**
	 * 通过科目代码获得科目ID add by zcwang 2007-09-11 区分办事处和币种
	 */
	public static String getSubjectIDByCode(String code,long officeID,long currencyID){
		HashMap hm = new HashMap();
			hm.put("sSubjectCode", "'"+code+"'");
			hm.put("NOFFICEID", String.valueOf(officeID));
			hm.put("NCURRENCYID", String.valueOf(currencyID));
		
		return getNamesByID("id","SETT_VGLSUBJECTDEFINITION",hm);
	}
	
	/**
	 * 通过业务性质ID获得名称
	 * @return
	 */
	public static String getBusinessAttributeNameById(long lID){
		return getNamesByID("Name","sec_businessAttribute","ID",String.valueOf(lID));
	}
	
	/**
	 * 通过交易类型ID获得业务类型名称
	 */
	public static String getBusinessTypeNameByTransactionTypeId(long lID){
		String strSql = "select bus.name Name from sec_businessType bus,sec_transactionType tra " +
				"where tra.businessTypeId = bus.id and tra.id=" + lID;
		return getNameByID(strSql,"NAME");
	}

	/**
	 * 通过交易类型ID获得对应的审批设置ID
	 * lID 交易类型ID
	 * lActionID 申请书：1；业务通知单：2；合同：3
	 */
	public static long getApprovalIDByTransactionTypeID1(long lTransactionTypeID,long lActionID,long lOfficeID,long lCurrencyID){
		//zpli modify 2005-09-14
		//public static long getApprovalIDByTransactionTypeID(long lTransactionTypeID,long lActionID){
		
		long lApprovalID = -1;
	    long lModuleTypeID = Constant.ModuleType.CRAFTBROTHER;
	    long lApprovalLoanTypeID = -1;
		long lApprovalActionID = -1;
		ApprovalBiz appbiz = new ApprovalBiz();
		
		//zpli modify 2005-09-20 将转换代码提到一个函数中
		long[] param=getLoantypidAndActionidByTransactionid(lActionID,lTransactionTypeID);
		lApprovalLoanTypeID=param[0];
		lApprovalActionID=param[1];
		
		if (lModuleTypeID > 0 && lApprovalLoanTypeID > 0 && lApprovalActionID > 0)
		{
		    try {
		        Log.print("lModuleTypeID       = " + lModuleTypeID);
		        Log.print("lApprovalLoanTypeID = " + lApprovalLoanTypeID);
		        Log.print("lApprovalActionID   = " + lApprovalActionID);
		        //获得ApprovalID
		        
		        //zpli modify 2005-09-14
	            lApprovalID = appbiz.getApprovalID(lModuleTypeID,lApprovalLoanTypeID,lApprovalActionID,lOfficeID,lCurrencyID);
		        //lApprovalID = appbiz.getApprovalID(lModuleTypeID,lApprovalLoanTypeID,lApprovalActionID);
	            
	        } catch (Exception e) {
	            Log.print("getApprovalID fail");
	            e.printStackTrace();
	        }
		}
		return lApprovalID;
	}	
	
	/**
	 * 通过交易类型ID获得对应的审批设置ID
	 * lID 交易类型ID
	 * lActionID 申请书：1；业务通知单：2；合同：3
	 */
	public static long getApprovalIDByTransactionTypeID(long lTransactionTypeID,long lActionID,long lOfficeID,long lCurrencyID){
		//zpli modify 2005-09-14
		//public static long getApprovalIDByTransactionTypeID(long lTransactionTypeID,long lActionID){
		
		long lApprovalID = -1;
	    long lModuleTypeID = Constant.ModuleType.SECURITIES;
	    long lApprovalLoanTypeID = -1;
		long lApprovalActionID = -1;
		ApprovalBiz appbiz = new ApprovalBiz();
		
		//zpli modify 2005-09-20 将转换代码提到一个函数中
		long[] param=getLoantypidAndActionidByTransactionid(lActionID,lTransactionTypeID);
		lApprovalLoanTypeID=param[0];
		lApprovalActionID=param[1];
		
		if (lModuleTypeID > 0 && lApprovalLoanTypeID > 0 && lApprovalActionID > 0)
		{
		    try {
		        Log.print("lModuleTypeID       = " + lModuleTypeID);
		        Log.print("lApprovalLoanTypeID = " + lApprovalLoanTypeID);
		        Log.print("lApprovalActionID   = " + lApprovalActionID);
		        //获得ApprovalID
		        
		        //zpli modify 2005-09-14
	            lApprovalID = appbiz.getApprovalID(lModuleTypeID,lApprovalLoanTypeID,lApprovalActionID,lOfficeID,lCurrencyID);
		        //lApprovalID = appbiz.getApprovalID(lModuleTypeID,lApprovalLoanTypeID,lApprovalActionID);
	            
	        } catch (Exception e) {
	            Log.print("getApprovalID fail");
	            e.printStackTrace();
	        }
		}
		return lApprovalID;
	}
	
	/**
	 * 通过交易类型ID获得对应的审批设置ID
	 * lID 交易类型ID
	 * lActionID 申请书：1；业务通知单：2；合同：3
	 */
	public static long[] getApprovalIDByTransactionTypeID(long lTransactionTypeID,long lActionID,boolean blnTmp){
		long lApprovalID = -1;
		long lModuleTypeID = Constant.ModuleType.SECURITIES;
		//long approvalId[0] = -1;
		//long approvalId[1] = -1;
		long[] approvalId = new long[2]; 
		//ApprovalBiz appbiz = new ApprovalBiz();
	
		//申请书
		if (lActionID == 1)
		{
			switch ((int) lTransactionTypeID)
			{
				case (int) SECConstant.BusinessType.CAPITAL_IN_CREDIT_EXTENSION.CAPITAL_IN_CREDIT_EXTENSION :
					approvalId[0] = Constant.ApprovalLoanType.CAPITAL_IN_CREDIT_EXTENSION;
					approvalId[1] = Constant.ApprovalAction.CAPITAL_IN_CREDIT_EXTENSION_APPLY;
					break;
				case (int) SECConstant.BusinessType.CAPITAL_OUT_CREDIT_EXTENSION.CAPITAL_OUT_CREDIT_EXTENSION :
					approvalId[0] = Constant.ApprovalLoanType.CAPITAL_IN_CREDIT_EXTENSION;
					approvalId[1] = Constant.ApprovalAction.CAPITAL_IN_CREDIT_EXTENSION_APPLY;
					break;
				case (int) SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_IN :
					approvalId[0] = Constant.ApprovalLoanType.CAPITAL_LANDING;
					approvalId[1] = Constant.ApprovalAction.CAPITAL_IN_APPLY;
					break;
				case (int) SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_IN_REPAY :
					approvalId[0] = Constant.ApprovalLoanType.CAPITAL_LANDING;
					approvalId[1] = Constant.ApprovalAction.CAPITAL_IN_APPLY;
					break;
				case (int) SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_OUT :
					approvalId[0] = Constant.ApprovalLoanType.CAPITAL_LANDING;
					approvalId[1] = Constant.ApprovalAction.CAPITAL_OUT_APPLY;
					break;
				case (int) SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_OUT_REPAY :
					approvalId[0] = Constant.ApprovalLoanType.CAPITAL_LANDING;
					approvalId[1] = Constant.ApprovalAction.CAPITAL_OUT_APPLY;
					break;
				case (int) SECConstant.BusinessType.STOCK_BID_ONLINE.INITIAL_OFFERING_BID_ONLINE :
					approvalId[0] = Constant.ApprovalLoanType.STOCK;
					approvalId[1] = Constant.ApprovalAction.STOCK_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.STOCK_BID_ONLINE.INITIAL_OFFERING_BID_REPAY :
					approvalId[0] = Constant.ApprovalLoanType.STOCK;
					approvalId[1] = Constant.ApprovalAction.STOCK_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.STOCK_BID_ONLINE.INITIAL_OFFERING_BID_WIN :
					approvalId[0] = Constant.ApprovalLoanType.STOCK;
					approvalId[1] = Constant.ApprovalAction.STOCK_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.STOCK_BID_ONLINE.PROMOTION_BID_ONLINE :
					approvalId[0] = Constant.ApprovalLoanType.STOCK;
					approvalId[1] = Constant.ApprovalAction.STOCK_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.STOCK_BID_ONLINE.PROMOTION_BID_REPAY :
					approvalId[0] = Constant.ApprovalLoanType.STOCK;
					approvalId[1] = Constant.ApprovalAction.STOCK_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.STOCK_BID_ONLINE.PROMOTION_BID_WIN :
					approvalId[0] = Constant.ApprovalLoanType.STOCK;
					approvalId[1] = Constant.ApprovalAction.STOCK_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.STOCK_BID.INITIAL_OFFERING_BID :
					approvalId[0] = Constant.ApprovalLoanType.STOCK;
					approvalId[1] = Constant.ApprovalAction.STOCK_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.STOCK_BID.INITIAL_OFFERING_MARGIN_REPAY :
					approvalId[0] = Constant.ApprovalLoanType.STOCK;
					approvalId[1] = Constant.ApprovalAction.STOCK_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.STOCK_BID.INITIAL_OFFERING_MARGIN_SUPPLY :
					approvalId[0] = Constant.ApprovalLoanType.STOCK;
					approvalId[1] = Constant.ApprovalAction.STOCK_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.STOCK_BID.INITIAL_OFFERING_QUOTA_CONFIRM :
					approvalId[0] = Constant.ApprovalLoanType.STOCK;
					approvalId[1] = Constant.ApprovalAction.STOCK_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.STOCK_BID.PROMOTION_BID :
					approvalId[0] = Constant.ApprovalLoanType.STOCK;
					approvalId[1] = Constant.ApprovalAction.STOCK_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.STOCK_BID.PROMOTION_BID_MARGIN_REPAY :
					approvalId[0] = Constant.ApprovalLoanType.STOCK;
					approvalId[1] = Constant.ApprovalAction.STOCK_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.STOCK_BID.PROMOTION_BID_MARGIN_SUPPLY :
					approvalId[0] = Constant.ApprovalLoanType.STOCK;
					approvalId[1] = Constant.ApprovalAction.STOCK_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.STOCK_BID.PROMOTION_BID_QUOTA_CONFIRM :
					approvalId[0] = Constant.ApprovalLoanType.STOCK;
					approvalId[1] = Constant.ApprovalAction.STOCK_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.STOCK_TRANSACTION.STOCK_BUYIN :
					approvalId[0] = Constant.ApprovalLoanType.STOCK;
					approvalId[1] = Constant.ApprovalAction.STOCK_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.STOCK_TRANSACTION.STOCK_MELON_CUTTING :
					approvalId[0] = Constant.ApprovalLoanType.STOCK;
					approvalId[1] = Constant.ApprovalAction.STOCK_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.STOCK_TRANSACTION.STOCK_RATION :
					approvalId[0] = Constant.ApprovalLoanType.STOCK;
					approvalId[1] = Constant.ApprovalAction.STOCK_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.STOCK_TRANSACTION.STOCK_RATION_ONLINE :
					approvalId[0] = Constant.ApprovalLoanType.STOCK;
					approvalId[1] = Constant.ApprovalAction.STOCK_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.STOCK_TRANSACTION.STOCK_SELL :
					approvalId[0] = Constant.ApprovalLoanType.STOCK;
					approvalId[1] = Constant.ApprovalAction.STOCK_SELL_APPLY;
					break;
				case (int) SECConstant.BusinessType.STOCK_TRANSACTION.STOCK_TAKEBACK :
					approvalId[0] = Constant.ApprovalLoanType.STOCK;
					approvalId[1] = Constant.ApprovalAction.STOCK_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.CENTRAL_BANK_NOTE_BID.NOTE_BID :
					approvalId[0] = Constant.ApprovalLoanType.STOCK;
					approvalId[1] = Constant.ApprovalAction.NOTE_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.CENTRAL_BANK_NOTE_TRANSACTION.NOTE_ACCRUAL_IN :
					approvalId[0] = Constant.ApprovalLoanType.CENTRAL_BANK_NOTE;
					approvalId[1] = Constant.ApprovalAction.NOTE_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.CENTRAL_BANK_NOTE_TRANSACTION.NOTE_BUYIN :
					approvalId[0] = Constant.ApprovalLoanType.CENTRAL_BANK_NOTE;
					approvalId[1] = Constant.ApprovalAction.NOTE_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.CENTRAL_BANK_NOTE_TRANSACTION.NOTE_MATURITY_REPAY :
					approvalId[0] = Constant.ApprovalLoanType.CENTRAL_BANK_NOTE;
					approvalId[1] = Constant.ApprovalAction.NOTE_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.CENTRAL_BANK_NOTE_TRANSACTION.NOTE_SELL :
					approvalId[0] = Constant.ApprovalLoanType.CENTRAL_BANK_NOTE;
					approvalId[1] = Constant.ApprovalAction.NOTE_SELL_APPLY;
					break;
				case (int) SECConstant.BusinessType.BANK_BOND_REPURCHASE.BOND_REPAY :
					approvalId[0] = Constant.ApprovalLoanType.BANK_BOND_REPURCHASE;
					approvalId[1] = Constant.ApprovalAction.BANK_BOND_REPURCHASE_APPLY;
					break;
				case (int) SECConstant.BusinessType.BANK_BOND_REPURCHASE.BOND_REPURCHASE :
					approvalId[0] = Constant.ApprovalLoanType.BANK_BOND_REPURCHASE;
					approvalId[1] = Constant.ApprovalAction.BANK_BOND_REPURCHASE_APPLY;
					break;
				case (int) SECConstant.BusinessType.BANK_BOND_REPURCHASE.FUND_REPAY :
					approvalId[0] = Constant.ApprovalLoanType.BANK_BOND_REPURCHASE;
					approvalId[1] = Constant.ApprovalAction.BANK_FUND_REPURCHASE_APPLY;
					break;
				case (int) SECConstant.BusinessType.BANK_BOND_REPURCHASE.FUND_REPURCHASE :
					approvalId[0] = Constant.ApprovalLoanType.BANK_BOND_REPURCHASE;
					approvalId[1] = Constant.ApprovalAction.BANK_FUND_REPURCHASE_APPLY;
					break;
				case (int) SECConstant.BusinessType.EXCHANGECENTER_BOND_REPURCHASE.BOND_REPAY :
					approvalId[0] = Constant.ApprovalLoanType.EXCHANGECENTER_BOND_REPURCHASE;
					approvalId[1] = Constant.ApprovalAction.EXCHANGECENTER_BOND_REPURCHASE_APPLY;
					break;
				case (int) SECConstant.BusinessType.EXCHANGECENTER_BOND_REPURCHASE.BOND_REPURCHASE :
					approvalId[0] = Constant.ApprovalLoanType.EXCHANGECENTER_BOND_REPURCHASE;
					approvalId[1] = Constant.ApprovalAction.EXCHANGECENTER_BOND_REPURCHASE_APPLY;
					break;
				case (int) SECConstant.BusinessType.EXCHANGECENTER_BOND_REPURCHASE.FUND_REPAY :
					approvalId[0] = Constant.ApprovalLoanType.EXCHANGECENTER_BOND_REPURCHASE;
					approvalId[1] = Constant.ApprovalAction.EXCHANGECENTER_FUND_REPURCHASE_APPLY;
					break;
				case (int) SECConstant.BusinessType.EXCHANGECENTER_BOND_REPURCHASE.FUND_REPURCHASE :
					approvalId[0] = Constant.ApprovalLoanType.EXCHANGECENTER_BOND_REPURCHASE;
					approvalId[1] = Constant.ApprovalAction.EXCHANGECENTER_FUND_REPURCHASE_APPLY;
					break;
				case (int) SECConstant.BusinessType.BANK_NATIONAL_BOND_BID.BOND_BID :
					approvalId[0] = Constant.ApprovalLoanType.BANK_NATIONAL_BOND;
					approvalId[1] = Constant.ApprovalAction.BANK_NATIONAL_BOND_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.BANK_NATIONAL_BOND_TRANSACTION.BOND_ACCRUAL_IN :
					approvalId[0] = Constant.ApprovalLoanType.BANK_NATIONAL_BOND;
					approvalId[1] = Constant.ApprovalAction.BANK_NATIONAL_BOND_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.BANK_NATIONAL_BOND_TRANSACTION.BOND_BUYIN :
					approvalId[0] = Constant.ApprovalLoanType.BANK_NATIONAL_BOND;
					approvalId[1] = Constant.ApprovalAction.BANK_NATIONAL_BOND_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.BANK_NATIONAL_BOND_TRANSACTION.BOND_MATURITY_REPAY :
					approvalId[0] = Constant.ApprovalLoanType.BANK_NATIONAL_BOND;
					approvalId[1] = Constant.ApprovalAction.BANK_NATIONAL_BOND_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.BANK_NATIONAL_BOND_TRANSACTION.BOND_SELL :
					approvalId[0] = Constant.ApprovalLoanType.BANK_NATIONAL_BOND;
					approvalId[1] = Constant.ApprovalAction.BANK_NATIONAL_BOND_SELL_APPLY;
					break;
				case (int) SECConstant.BusinessType.EXCHANGECENTER_NATIONAL_BOND_BID.BOND_BID :
					approvalId[0] = Constant.ApprovalLoanType.EXCHANGECENTER_NATIONAL_BOND;
					approvalId[1] = Constant.ApprovalAction.EXCHANGECENTER_NATIONAL_BOND_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.EXCHANGECENTER_NATIONAL_BOND_TRANSACTION.BOND_ACCRUAL_IN :
					approvalId[0] = Constant.ApprovalLoanType.EXCHANGECENTER_NATIONAL_BOND;
					approvalId[1] = Constant.ApprovalAction.EXCHANGECENTER_NATIONAL_BOND_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.EXCHANGECENTER_NATIONAL_BOND_TRANSACTION.BOND_BUYIN :
					approvalId[0] = Constant.ApprovalLoanType.EXCHANGECENTER_NATIONAL_BOND;
					approvalId[1] = Constant.ApprovalAction.EXCHANGECENTER_NATIONAL_BOND_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.EXCHANGECENTER_NATIONAL_BOND_TRANSACTION.BOND_MATURITY_REPAY :
					approvalId[0] = Constant.ApprovalLoanType.EXCHANGECENTER_NATIONAL_BOND;
					approvalId[1] = Constant.ApprovalAction.EXCHANGECENTER_NATIONAL_BOND_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.EXCHANGECENTER_NATIONAL_BOND_TRANSACTION.BOND_SELL :
					approvalId[0] = Constant.ApprovalLoanType.EXCHANGECENTER_NATIONAL_BOND;
					approvalId[1] = Constant.ApprovalAction.EXCHANGECENTER_NATIONAL_BOND_SELL_APPLY;
					break;
				case (int) SECConstant.BusinessType.FINANCIAL_BOND_BID.BOND_BID :
					approvalId[0] = Constant.ApprovalLoanType.FINANCIAL_BOND;
					approvalId[1] = Constant.ApprovalAction.FINANCIAL_BOND_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.FINANCIAL_BOND_TRANSACTION.BOND_ACCRUAL_IN :
					approvalId[0] = Constant.ApprovalLoanType.FINANCIAL_BOND;
					approvalId[1] = Constant.ApprovalAction.FINANCIAL_BOND_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.FINANCIAL_BOND_TRANSACTION.BOND_BUYIN :
					approvalId[0] = Constant.ApprovalLoanType.FINANCIAL_BOND;
					approvalId[1] = Constant.ApprovalAction.FINANCIAL_BOND_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.FINANCIAL_BOND_TRANSACTION.BOND_MATURITY_REPAY :
					approvalId[0] = Constant.ApprovalLoanType.FINANCIAL_BOND;
					approvalId[1] = Constant.ApprovalAction.FINANCIAL_BOND_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.FINANCIAL_BOND_TRANSACTION.BOND_SELL :
					approvalId[0] = Constant.ApprovalLoanType.FINANCIAL_BOND;
					approvalId[1] = Constant.ApprovalAction.FINANCIAL_BOND_SELL_APPLY;
					break;
				case (int) SECConstant.BusinessType.POLICY_RELATED_FINANCIAL_BOND_BID.BOND_BID :
					approvalId[0] = Constant.ApprovalLoanType.POLICY_RELATED_FINANCIAL_BOND;
					approvalId[1] = Constant.ApprovalAction.POLICY_RELATED_FINANCIAL_BOND_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.POLICY_RELATED_FINANCIAL_BOND_TRANSACTION.BOND_ACCRUAL_IN :
					approvalId[0] = Constant.ApprovalLoanType.POLICY_RELATED_FINANCIAL_BOND;
					approvalId[1] = Constant.ApprovalAction.POLICY_RELATED_FINANCIAL_BOND_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.POLICY_RELATED_FINANCIAL_BOND_TRANSACTION.BOND_BUYIN :
					approvalId[0] = Constant.ApprovalLoanType.POLICY_RELATED_FINANCIAL_BOND;
					approvalId[1] = Constant.ApprovalAction.POLICY_RELATED_FINANCIAL_BOND_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.POLICY_RELATED_FINANCIAL_BOND_TRANSACTION.BOND_MATURITY_REPAY :
					approvalId[0] = Constant.ApprovalLoanType.POLICY_RELATED_FINANCIAL_BOND;
					approvalId[1] = Constant.ApprovalAction.POLICY_RELATED_FINANCIAL_BOND_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.POLICY_RELATED_FINANCIAL_BOND_TRANSACTION.BOND_SELL :
					approvalId[0] = Constant.ApprovalLoanType.POLICY_RELATED_FINANCIAL_BOND;
					approvalId[1] = Constant.ApprovalAction.POLICY_RELATED_FINANCIAL_BOND_SELL_APPLY;
					break;
				case (int) SECConstant.BusinessType.ENTERPRISE_BOND_BID.BOND_BID :
					approvalId[0] = Constant.ApprovalLoanType.ENTERPRISE_BOND;
					approvalId[1] = Constant.ApprovalAction.ENTERPRISE_BOND_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.ENTERPRISE_BOND_BID.BOND_BID_MARGIN_REPAY :
					approvalId[0] = Constant.ApprovalLoanType.ENTERPRISE_BOND;
					approvalId[1] = Constant.ApprovalAction.ENTERPRISE_BOND_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.ENTERPRISE_BOND_BID.BOND_BID_MARGIN_SUPPLY :
					approvalId[0] = Constant.ApprovalLoanType.ENTERPRISE_BOND;
					approvalId[1] = Constant.ApprovalAction.ENTERPRISE_BOND_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.ENTERPRISE_BOND_BID.BOND_BID_QUOTA_CONFIRM :
					approvalId[0] = Constant.ApprovalLoanType.ENTERPRISE_BOND;
					approvalId[1] = Constant.ApprovalAction.ENTERPRISE_BOND_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.ENTERPRISE_BOND_TRANSACTION.BOND_ACCRUAL_IN :
					approvalId[0] = Constant.ApprovalLoanType.ENTERPRISE_BOND;
					approvalId[1] = Constant.ApprovalAction.ENTERPRISE_BOND_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.ENTERPRISE_BOND_TRANSACTION.BOND_BUYIN :
					approvalId[0] = Constant.ApprovalLoanType.ENTERPRISE_BOND;
					approvalId[1] = Constant.ApprovalAction.ENTERPRISE_BOND_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.ENTERPRISE_BOND_TRANSACTION.BOND_MATURITY_REPAY :
					approvalId[0] = Constant.ApprovalLoanType.ENTERPRISE_BOND;
					approvalId[1] = Constant.ApprovalAction.ENTERPRISE_BOND_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.ENTERPRISE_BOND_TRANSACTION.BOND_SELL :
					approvalId[0] = Constant.ApprovalLoanType.ENTERPRISE_BOND;
					approvalId[1] = Constant.ApprovalAction.ENTERPRISE_BOND_SELL_APPLY;
					break;
				case (int) SECConstant.BusinessType.TRANSFORMABLE_BOND_BID_ONLINE.BOND_BID_ONLINE :
					approvalId[0] = Constant.ApprovalLoanType.TRANSFORMABLE_BOND;
					approvalId[1] = Constant.ApprovalAction.TRANSFORMABLE_BOND_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.TRANSFORMABLE_BOND_BID_ONLINE.BOND_BID_REPAY :
					approvalId[0] = Constant.ApprovalLoanType.TRANSFORMABLE_BOND;
					approvalId[1] = Constant.ApprovalAction.TRANSFORMABLE_BOND_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.TRANSFORMABLE_BOND_BID_ONLINE.BOND_BID_WIN :
					approvalId[0] = Constant.ApprovalLoanType.TRANSFORMABLE_BOND;
					approvalId[1] = Constant.ApprovalAction.TRANSFORMABLE_BOND_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.TRANSFORMABLE_BOND_BID.BOND_BID :
					approvalId[0] = Constant.ApprovalLoanType.TRANSFORMABLE_BOND;
					approvalId[1] = Constant.ApprovalAction.TRANSFORMABLE_BOND_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.TRANSFORMABLE_BOND_BID.BOND_BID_MARGIN_REPAY :
					approvalId[0] = Constant.ApprovalLoanType.TRANSFORMABLE_BOND;
					approvalId[1] = Constant.ApprovalAction.TRANSFORMABLE_BOND_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.TRANSFORMABLE_BOND_BID.BOND_BID_MARGIN_SUPPAY :
					approvalId[0] = Constant.ApprovalLoanType.TRANSFORMABLE_BOND;
					approvalId[1] = Constant.ApprovalAction.TRANSFORMABLE_BOND_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.TRANSFORMABLE_BOND_BID.BOND_BID_QUOTA_CONFIRM :
					approvalId[0] = Constant.ApprovalLoanType.TRANSFORMABLE_BOND;
					approvalId[1] = Constant.ApprovalAction.TRANSFORMABLE_BOND_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.TRANSFORMABLE_BOND_TRANSACTION.BOND_ACCRUAL_IN :
					approvalId[0] = Constant.ApprovalLoanType.TRANSFORMABLE_BOND;
					approvalId[1] = Constant.ApprovalAction.TRANSFORMABLE_BOND_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.TRANSFORMABLE_BOND_TRANSACTION.BOND_BUYIN :
					approvalId[0] = Constant.ApprovalLoanType.TRANSFORMABLE_BOND;
					approvalId[1] = Constant.ApprovalAction.TRANSFORMABLE_BOND_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.TRANSFORMABLE_BOND_TRANSACTION.BOND_MATURITY_REPAY :
					approvalId[0] = Constant.ApprovalLoanType.TRANSFORMABLE_BOND;
					approvalId[1] = Constant.ApprovalAction.TRANSFORMABLE_BOND_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.TRANSFORMABLE_BOND_TRANSACTION.BOND_SELL :
					approvalId[0] = Constant.ApprovalLoanType.TRANSFORMABLE_BOND;
					approvalId[1] = Constant.ApprovalAction.TRANSFORMABLE_BOND_SELL_APPLY;
					break;
				case (int) SECConstant.BusinessType.DEBT_TO_EQUITY.DEBT_TO_EQUITY :
					approvalId[0] = Constant.ApprovalLoanType.TRANSFORMABLE_BOND;
					approvalId[1] = Constant.ApprovalAction.DEBT_TO_EQUITY;
					break;
				case (int) SECConstant.BusinessType.ENCLOSED_FUND_BID_ONLINE.FUND_BID_ONLINE :
					approvalId[0] = Constant.ApprovalLoanType.FUND;
					approvalId[1] = Constant.ApprovalAction.FUND_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.ENCLOSED_FUND_BID_ONLINE.FUND_BID_REPAY :
					approvalId[0] = Constant.ApprovalLoanType.FUND;
					approvalId[1] = Constant.ApprovalAction.FUND_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.ENCLOSED_FUND_BID_ONLINE.FUND_BID_WIN :
					approvalId[0] = Constant.ApprovalLoanType.FUND;
					approvalId[1] = Constant.ApprovalAction.FUND_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.ENCLOSED_FUND_BID.FUND_BID :
					approvalId[0] = Constant.ApprovalLoanType.FUND;
					approvalId[1] = Constant.ApprovalAction.FUND_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.ENCLOSED_FUND_BID.FUND_BID_MARGIN_REPAY :
					approvalId[0] = Constant.ApprovalLoanType.FUND;
					approvalId[1] = Constant.ApprovalAction.FUND_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.ENCLOSED_FUND_BID.FUND_BID_MARGIN_SUPPLY :
					approvalId[0] = Constant.ApprovalLoanType.FUND;
					approvalId[1] = Constant.ApprovalAction.FUND_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.ENCLOSED_FUND_BID.FUND_BID_QUOTA_CONFIRM :
					approvalId[0] = Constant.ApprovalLoanType.FUND;
					approvalId[1] = Constant.ApprovalAction.FUND_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.ENCLOSED_FUND_TRANSACTION.FUND_BUYIN :
					approvalId[0] = Constant.ApprovalLoanType.FUND;
					approvalId[1] = Constant.ApprovalAction.FUND_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.ENCLOSED_FUND_TRANSACTION.FUND_MATURITY_SETTLE :
					approvalId[0] = Constant.ApprovalLoanType.FUND;
					approvalId[1] = Constant.ApprovalAction.FUND_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.ENCLOSED_FUND_TRANSACTION.FUND_MELON_CUTTING :
					approvalId[0] = Constant.ApprovalLoanType.FUND;
					approvalId[1] = Constant.ApprovalAction.FUND_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.ENCLOSED_FUND_TRANSACTION.FUND_SELL :
					approvalId[0] = Constant.ApprovalLoanType.FUND;
					approvalId[1] = Constant.ApprovalAction.FUND_SELL_APPLY;
					break;
				case (int) SECConstant.BusinessType.MUTUAL_FUND_SUBSCRIBE.FUND_SUBSCRIBE :
					approvalId[0] = Constant.ApprovalLoanType.FUND;
					approvalId[1] = Constant.ApprovalAction.FUND_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.MUTUAL_FUND_SUBSCRIBE.FUND_SUBSCRIBE_CONFIRM :
					approvalId[0] = Constant.ApprovalLoanType.FUND;
					approvalId[1] = Constant.ApprovalAction.FUND_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.MUTUAL_FUND_BID.FUND_BID :
					approvalId[0] = Constant.ApprovalLoanType.FUND;
					approvalId[1] = Constant.ApprovalAction.FUND_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.MUTUAL_FUND_BID.FUND_BID_CONFIRM :
					approvalId[0] = Constant.ApprovalLoanType.FUND;
					approvalId[1] = Constant.ApprovalAction.FUND_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.MUTUAL_FUND_REDEEM.FUND_REDEEM :
					approvalId[0] = Constant.ApprovalLoanType.FUND;
					approvalId[1] = Constant.ApprovalAction.FUND_SELL_APPLY;
					break;
				case (int) SECConstant.BusinessType.MUTUAL_FUND_MELON_CUTTING.FUND_CASH_MELON_CUTTING :
					approvalId[0] = Constant.ApprovalLoanType.FUND;
					approvalId[1] = Constant.ApprovalAction.FUND_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.MUTUAL_FUND_MELON_CUTTING.FUND_SHARE_MELON_CUTTING :
					approvalId[0] = Constant.ApprovalLoanType.FUND;
					approvalId[1] = Constant.ApprovalAction.FUND_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.CAPITAL_REPURCHASE.CAPITAL_REPURCHASE :
					approvalId[0] = Constant.ApprovalLoanType.CAPITAL_REPURCHASE;
					approvalId[1] = Constant.ApprovalAction.CAPITAL_REPURCHASE_APPLY;
					break;
				case (int) SECConstant.BusinessType.CAPITAL_REPURCHASE.INTEREST_PAYMENT :
					approvalId[0] = Constant.ApprovalLoanType.CAPITAL_REPURCHASE;
					approvalId[1] = Constant.ApprovalAction.CAPITAL_REPURCHASE_APPLY;
					break;
				case (int) SECConstant.BusinessType.CAPITAL_REPURCHASE.PAYBACK_NOTIFY :
					approvalId[0] = Constant.ApprovalLoanType.CAPITAL_REPURCHASE;
					approvalId[1] = Constant.ApprovalAction.CAPITAL_REPURCHASE_APPLY;
					break;
				case (int) SECConstant.BusinessType.CAPITAL_REPURCHASE.REPURCHASE_NOTIFY :
					approvalId[0] = Constant.ApprovalLoanType.CAPITAL_REPURCHASE;
					approvalId[1] = Constant.ApprovalAction.CAPITAL_REPURCHASE_APPLY;
					break;
				case (int) SECConstant.BusinessType.ENTRUST_FINANCING.CORPORACITY_DRAWBACK_NOTIFY :
					approvalId[0] = Constant.ApprovalLoanType.ENTRUST_FINANCING;
					approvalId[1] = Constant.ApprovalAction.ENTRUST_FINANCING_APPLY;
					break;
				case (int) SECConstant.BusinessType.ENTRUST_FINANCING.CORPORACITY_PAYMENT_NOTIFY :
					approvalId[0] = Constant.ApprovalLoanType.ENTRUST_FINANCING;
					approvalId[1] = Constant.ApprovalAction.ENTRUST_FINANCING_APPLY;
					break;
				case (int) SECConstant.BusinessType.ENTRUST_FINANCING.ENTRUST_FINANCING :
					approvalId[0] = Constant.ApprovalLoanType.ENTRUST_FINANCING;
					approvalId[1] = Constant.ApprovalAction.ENTRUST_FINANCING_APPLY;
					break;
				case (int) SECConstant.BusinessType.ENTRUST_FINANCING.INCOME_DRAWBACK_NOTIFY :
					approvalId[0] = Constant.ApprovalLoanType.ENTRUST_FINANCING;
					approvalId[1] = Constant.ApprovalAction.ENTRUST_FINANCING_APPLY;
					break;
				case (int) SECConstant.BusinessType.ENTRUSTED_FINANCING.CORPORACITY_DRAWBACK_NOTIFY :
					approvalId[0] = Constant.ApprovalLoanType.ENTRUSTED_FINANCING;
					approvalId[1] = Constant.ApprovalAction.ENTRUSTED_FINANCING_APPLY;
					break;
				case (int) SECConstant.BusinessType.ENTRUSTED_FINANCING.CORPORACITY_PAYMENT_NOTIFY :
					approvalId[0] = Constant.ApprovalLoanType.ENTRUSTED_FINANCING;
					approvalId[1] = Constant.ApprovalAction.ENTRUSTED_FINANCING_APPLY;
					break;
				case (int) SECConstant.BusinessType.ENTRUSTED_FINANCING.ENTRUSTED_FINANCING :
					approvalId[0] = Constant.ApprovalLoanType.ENTRUSTED_FINANCING;
					approvalId[1] = Constant.ApprovalAction.ENTRUSTED_FINANCING_APPLY;
					break;
				case (int) SECConstant.BusinessType.ENTRUSTED_FINANCING.INCOME_PAYMENT_NOTIFY :
					approvalId[0] = Constant.ApprovalLoanType.ENTRUSTED_FINANCING;
					approvalId[1] = Constant.ApprovalAction.ENTRUSTED_FINANCING_APPLY;
					break;
				case (int) SECConstant.BusinessType.FOREIGN_CURRENCY_INVESTMENT.FOREIGN_CURRENCY_INVESTMENT :
					approvalId[0] = Constant.ApprovalLoanType.FOREIGN_CURRENCY_INVESTMENT;
					approvalId[1] = Constant.ApprovalAction.FOREIGN_CURRENCY_INVESTMENT_APPLY;
					break;
				case (int) SECConstant.BusinessType.FOREIGN_CURRENCY_INVESTMENT.INCOME_DRAWBACK_NOTIFY :
					approvalId[0] = Constant.ApprovalLoanType.FOREIGN_CURRENCY_INVESTMENT;
					approvalId[1] = Constant.ApprovalAction.FOREIGN_CURRENCY_INVESTMENT_APPLY;
					break;
				case (int) SECConstant.BusinessType.FOREIGN_CURRENCY_INVESTMENT.INVESTMENT_DRAWBACK_NOTIFY :
					approvalId[0] = Constant.ApprovalLoanType.FOREIGN_CURRENCY_INVESTMENT;
					approvalId[1] = Constant.ApprovalAction.FOREIGN_CURRENCY_INVESTMENT_APPLY;
					break;
				case (int) SECConstant.BusinessType.FOREIGN_CURRENCY_INVESTMENT.INVESTMENT_PAYMENT_NOTIFY :
					approvalId[0] = Constant.ApprovalLoanType.FOREIGN_CURRENCY_INVESTMENT;
					approvalId[1] = Constant.ApprovalAction.FOREIGN_CURRENCY_INVESTMENT_APPLY;
					break;
				case (int) SECConstant.BusinessType.BOND_UNDERWRITING.BOND_UNDERWRITING :
					approvalId[0] = Constant.ApprovalLoanType.BOND_UNDERWRITING;
					approvalId[1] = Constant.ApprovalAction.BOND_UNDERWRITING_APPLY;
					break;
				case (int) SECConstant.BusinessType.BOND_UNDERWRITING.INCOME_DRAWBACK_NOTIFY :
					approvalId[0] = Constant.ApprovalLoanType.BOND_UNDERWRITING;
					approvalId[1] = Constant.ApprovalAction.BOND_UNDERWRITING_APPLY;
					break;
				case (int) SECConstant.BusinessType.BOND_UNDERWRITING.UNDERWRITING_DRAWBACK_NOTIFY :
					approvalId[0] = Constant.ApprovalLoanType.BOND_UNDERWRITING;
					approvalId[1] = Constant.ApprovalAction.BOND_UNDERWRITING_APPLY;
					break;
				case (int) SECConstant.BusinessType.BOND_UNDERWRITING.UNDERWRITING_PAYMENT_NOTIFY :
					approvalId[0] = Constant.ApprovalLoanType.BOND_UNDERWRITING;
					approvalId[1] = Constant.ApprovalAction.BOND_UNDERWRITING_APPLY;
					break;
			}
		}
		//业务通知单
		else if (lActionID == 2)
		{
			switch ((int) lTransactionTypeID)
			{
				case (int) SECConstant.BusinessType.CAPITAL_IN_CREDIT_EXTENSION.CAPITAL_IN_CREDIT_EXTENSION :
					approvalId[0] = Constant.ApprovalLoanType.CAPITAL_IN_CREDIT_EXTENSION;
					approvalId[1] = Constant.ApprovalAction.CAPITAL_IN_CREDIT_EXTENSION_APPLY;
					break;
				case (int) SECConstant.BusinessType.CAPITAL_OUT_CREDIT_EXTENSION.CAPITAL_OUT_CREDIT_EXTENSION :
					approvalId[0] = Constant.ApprovalLoanType.CAPITAL_IN_CREDIT_EXTENSION;
					approvalId[1] = Constant.ApprovalAction.CAPITAL_IN_CREDIT_EXTENSION_APPLY;
					break;
				case (int) SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_IN :
					approvalId[0] = Constant.ApprovalLoanType.CAPITAL_LANDING;
					approvalId[1] = Constant.ApprovalAction.CAPITAL_LANDING_NOTICE;
					break;
				case (int) SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_IN_REPAY :
					approvalId[0] = Constant.ApprovalLoanType.CAPITAL_LANDING;
					approvalId[1] = Constant.ApprovalAction.CAPITAL_LANDING_NOTICE;
					break;
				case (int) SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_OUT :
					approvalId[0] = Constant.ApprovalLoanType.CAPITAL_LANDING;
					approvalId[1] = Constant.ApprovalAction.CAPITAL_LANDING_NOTICE;
					break;
				case (int) SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_OUT_REPAY :
					approvalId[0] = Constant.ApprovalLoanType.CAPITAL_LANDING;
					approvalId[1] = Constant.ApprovalAction.CAPITAL_LANDING_NOTICE;
					break;
				case (int) SECConstant.BusinessType.STOCK_BID_ONLINE.INITIAL_OFFERING_BID_ONLINE :
					approvalId[0] = Constant.ApprovalLoanType.STOCK;
					approvalId[1] = Constant.ApprovalAction.STOCK_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.STOCK_BID_ONLINE.INITIAL_OFFERING_BID_REPAY :
					approvalId[0] = Constant.ApprovalLoanType.STOCK;
					approvalId[1] = Constant.ApprovalAction.STOCK_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.STOCK_BID_ONLINE.INITIAL_OFFERING_BID_WIN :
					approvalId[0] = Constant.ApprovalLoanType.STOCK;
					approvalId[1] = Constant.ApprovalAction.STOCK_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.STOCK_BID_ONLINE.PROMOTION_BID_ONLINE :
					approvalId[0] = Constant.ApprovalLoanType.STOCK;
					approvalId[1] = Constant.ApprovalAction.STOCK_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.STOCK_BID_ONLINE.PROMOTION_BID_REPAY :
					approvalId[0] = Constant.ApprovalLoanType.STOCK;
					approvalId[1] = Constant.ApprovalAction.STOCK_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.STOCK_BID_ONLINE.PROMOTION_BID_WIN :
					approvalId[0] = Constant.ApprovalLoanType.STOCK;
					approvalId[1] = Constant.ApprovalAction.STOCK_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.STOCK_BID.INITIAL_OFFERING_BID :
					approvalId[0] = Constant.ApprovalLoanType.STOCK;
					approvalId[1] = Constant.ApprovalAction.STOCK_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.STOCK_BID.INITIAL_OFFERING_MARGIN_REPAY :
					approvalId[0] = Constant.ApprovalLoanType.STOCK;
					approvalId[1] = Constant.ApprovalAction.STOCK_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.STOCK_BID.INITIAL_OFFERING_MARGIN_SUPPLY :
					approvalId[0] = Constant.ApprovalLoanType.STOCK;
					approvalId[1] = Constant.ApprovalAction.STOCK_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.STOCK_BID.INITIAL_OFFERING_QUOTA_CONFIRM :
					approvalId[0] = Constant.ApprovalLoanType.STOCK;
					approvalId[1] = Constant.ApprovalAction.STOCK_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.STOCK_BID.PROMOTION_BID :
					approvalId[0] = Constant.ApprovalLoanType.STOCK;
					approvalId[1] = Constant.ApprovalAction.STOCK_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.STOCK_BID.PROMOTION_BID_MARGIN_REPAY :
					approvalId[0] = Constant.ApprovalLoanType.STOCK;
					approvalId[1] = Constant.ApprovalAction.STOCK_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.STOCK_BID.PROMOTION_BID_MARGIN_SUPPLY :
					approvalId[0] = Constant.ApprovalLoanType.STOCK;
					approvalId[1] = Constant.ApprovalAction.STOCK_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.STOCK_BID.PROMOTION_BID_QUOTA_CONFIRM :
					approvalId[0] = Constant.ApprovalLoanType.STOCK;
					approvalId[1] = Constant.ApprovalAction.STOCK_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.STOCK_TRANSACTION.STOCK_BUYIN :
					approvalId[0] = Constant.ApprovalLoanType.STOCK;
					approvalId[1] = Constant.ApprovalAction.STOCK_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.STOCK_TRANSACTION.STOCK_MELON_CUTTING :
					approvalId[0] = Constant.ApprovalLoanType.STOCK;
					approvalId[1] = Constant.ApprovalAction.STOCK_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.STOCK_TRANSACTION.STOCK_RATION :
					approvalId[0] = Constant.ApprovalLoanType.STOCK;
					approvalId[1] = Constant.ApprovalAction.STOCK_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.STOCK_TRANSACTION.STOCK_RATION_ONLINE :
					approvalId[0] = Constant.ApprovalLoanType.STOCK;
					approvalId[1] = Constant.ApprovalAction.STOCK_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.STOCK_TRANSACTION.STOCK_SELL :
					approvalId[0] = Constant.ApprovalLoanType.STOCK;
					approvalId[1] = Constant.ApprovalAction.STOCK_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.STOCK_TRANSACTION.STOCK_TAKEBACK :
					approvalId[0] = Constant.ApprovalLoanType.STOCK;
					approvalId[1] = Constant.ApprovalAction.STOCK_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.CENTRAL_BANK_NOTE_BID.NOTE_BID :
					approvalId[0] = Constant.ApprovalLoanType.CENTRAL_BANK_NOTE;
					approvalId[1] = Constant.ApprovalAction.CENTRAL_BANK_NOTE_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.CENTRAL_BANK_NOTE_TRANSACTION.NOTE_ACCRUAL_IN :
					approvalId[0] = Constant.ApprovalLoanType.CENTRAL_BANK_NOTE;
					approvalId[1] = Constant.ApprovalAction.CENTRAL_BANK_NOTE_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.CENTRAL_BANK_NOTE_TRANSACTION.NOTE_BUYIN :
					approvalId[0] = Constant.ApprovalLoanType.CENTRAL_BANK_NOTE;
					approvalId[1] = Constant.ApprovalAction.CENTRAL_BANK_NOTE_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.CENTRAL_BANK_NOTE_TRANSACTION.NOTE_MATURITY_REPAY :
					approvalId[0] = Constant.ApprovalLoanType.CENTRAL_BANK_NOTE;
					approvalId[1] = Constant.ApprovalAction.CENTRAL_BANK_NOTE_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.CENTRAL_BANK_NOTE_TRANSACTION.NOTE_SELL :
					approvalId[0] = Constant.ApprovalLoanType.CENTRAL_BANK_NOTE;
					approvalId[1] = Constant.ApprovalAction.CENTRAL_BANK_NOTE_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.BANK_BOND_REPURCHASE.BOND_REPAY :
					approvalId[0] = Constant.ApprovalLoanType.BANK_BOND_REPURCHASE;
					approvalId[1] = Constant.ApprovalAction.BANK_BOND_REPURCHASE_NOTICE;
					break;
				case (int) SECConstant.BusinessType.BANK_BOND_REPURCHASE.BOND_REPURCHASE :
					approvalId[0] = Constant.ApprovalLoanType.BANK_BOND_REPURCHASE;
					approvalId[1] = Constant.ApprovalAction.BANK_BOND_REPURCHASE_NOTICE;
					break;
				case (int) SECConstant.BusinessType.BANK_BOND_REPURCHASE.FUND_REPAY :
					approvalId[0] = Constant.ApprovalLoanType.BANK_BOND_REPURCHASE;
					approvalId[1] = Constant.ApprovalAction.BANK_BOND_REPURCHASE_NOTICE;
					break;
				case (int) SECConstant.BusinessType.BANK_BOND_REPURCHASE.FUND_REPURCHASE :
					approvalId[0] = Constant.ApprovalLoanType.BANK_BOND_REPURCHASE;
					approvalId[1] = Constant.ApprovalAction.BANK_BOND_REPURCHASE_NOTICE;
					break;
				case (int) SECConstant.BusinessType.EXCHANGECENTER_BOND_REPURCHASE.BOND_REPAY :
					approvalId[0] = Constant.ApprovalLoanType.EXCHANGECENTER_BOND_REPURCHASE;
					approvalId[1] = Constant.ApprovalAction.EXCHANGECENTER_BOND_REPURCHASE_NOTICE;
					break;
				case (int) SECConstant.BusinessType.EXCHANGECENTER_BOND_REPURCHASE.BOND_REPURCHASE :
					approvalId[0] = Constant.ApprovalLoanType.EXCHANGECENTER_BOND_REPURCHASE;
					approvalId[1] = Constant.ApprovalAction.EXCHANGECENTER_BOND_REPURCHASE_NOTICE;
					break;
				case (int) SECConstant.BusinessType.EXCHANGECENTER_BOND_REPURCHASE.FUND_REPAY :
					approvalId[0] = Constant.ApprovalLoanType.EXCHANGECENTER_BOND_REPURCHASE;
					approvalId[1] = Constant.ApprovalAction.EXCHANGECENTER_BOND_REPURCHASE_NOTICE;
					break;
				case (int) SECConstant.BusinessType.EXCHANGECENTER_BOND_REPURCHASE.FUND_REPURCHASE :
					approvalId[0] = Constant.ApprovalLoanType.EXCHANGECENTER_BOND_REPURCHASE;
					approvalId[1] = Constant.ApprovalAction.EXCHANGECENTER_BOND_REPURCHASE_NOTICE;
					break;
				case (int) SECConstant.BusinessType.BANK_NATIONAL_BOND_BID.BOND_BID :
					approvalId[0] = Constant.ApprovalLoanType.BANK_NATIONAL_BOND;
					approvalId[1] = Constant.ApprovalAction.BANK_NATIONAL_BOND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.BANK_NATIONAL_BOND_TRANSACTION.BOND_ACCRUAL_IN :
					approvalId[0] = Constant.ApprovalLoanType.BANK_NATIONAL_BOND;
					approvalId[1] = Constant.ApprovalAction.BANK_NATIONAL_BOND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.BANK_NATIONAL_BOND_TRANSACTION.BOND_BUYIN :
					approvalId[0] = Constant.ApprovalLoanType.BANK_NATIONAL_BOND;
					approvalId[1] = Constant.ApprovalAction.BANK_NATIONAL_BOND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.BANK_NATIONAL_BOND_TRANSACTION.BOND_MATURITY_REPAY :
					approvalId[0] = Constant.ApprovalLoanType.BANK_NATIONAL_BOND;
					approvalId[1] = Constant.ApprovalAction.BANK_NATIONAL_BOND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.BANK_NATIONAL_BOND_TRANSACTION.BOND_SELL :
					approvalId[0] = Constant.ApprovalLoanType.BANK_NATIONAL_BOND;
					approvalId[1] = Constant.ApprovalAction.BANK_NATIONAL_BOND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.EXCHANGECENTER_NATIONAL_BOND_BID.BOND_BID :
					approvalId[0] = Constant.ApprovalLoanType.EXCHANGECENTER_NATIONAL_BOND;
					approvalId[1] = Constant.ApprovalAction.EXCHANGECENTER_NATIONAL_BOND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.EXCHANGECENTER_NATIONAL_BOND_TRANSACTION.BOND_ACCRUAL_IN :
					approvalId[0] = Constant.ApprovalLoanType.EXCHANGECENTER_NATIONAL_BOND;
					approvalId[1] = Constant.ApprovalAction.EXCHANGECENTER_NATIONAL_BOND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.EXCHANGECENTER_NATIONAL_BOND_TRANSACTION.BOND_BUYIN :
					approvalId[0] = Constant.ApprovalLoanType.EXCHANGECENTER_NATIONAL_BOND;
					approvalId[1] = Constant.ApprovalAction.EXCHANGECENTER_NATIONAL_BOND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.EXCHANGECENTER_NATIONAL_BOND_TRANSACTION.BOND_MATURITY_REPAY :
					approvalId[0] = Constant.ApprovalLoanType.EXCHANGECENTER_NATIONAL_BOND;
					approvalId[1] = Constant.ApprovalAction.EXCHANGECENTER_NATIONAL_BOND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.EXCHANGECENTER_NATIONAL_BOND_TRANSACTION.BOND_SELL :
					approvalId[0] = Constant.ApprovalLoanType.EXCHANGECENTER_NATIONAL_BOND;
					approvalId[1] = Constant.ApprovalAction.EXCHANGECENTER_NATIONAL_BOND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.FINANCIAL_BOND_BID.BOND_BID :
					approvalId[0] = Constant.ApprovalLoanType.FINANCIAL_BOND;
					approvalId[1] = Constant.ApprovalAction.FINANCIAL_BOND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.FINANCIAL_BOND_TRANSACTION.BOND_ACCRUAL_IN :
					approvalId[0] = Constant.ApprovalLoanType.FINANCIAL_BOND;
					approvalId[1] = Constant.ApprovalAction.FINANCIAL_BOND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.FINANCIAL_BOND_TRANSACTION.BOND_BUYIN :
					approvalId[0] = Constant.ApprovalLoanType.FINANCIAL_BOND;
					approvalId[1] = Constant.ApprovalAction.FINANCIAL_BOND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.FINANCIAL_BOND_TRANSACTION.BOND_MATURITY_REPAY :
					approvalId[0] = Constant.ApprovalLoanType.FINANCIAL_BOND;
					approvalId[1] = Constant.ApprovalAction.FINANCIAL_BOND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.FINANCIAL_BOND_TRANSACTION.BOND_SELL :
					approvalId[0] = Constant.ApprovalLoanType.FINANCIAL_BOND;
					approvalId[1] = Constant.ApprovalAction.FINANCIAL_BOND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.POLICY_RELATED_FINANCIAL_BOND_BID.BOND_BID :
					approvalId[0] = Constant.ApprovalLoanType.POLICY_RELATED_FINANCIAL_BOND;
					approvalId[1] = Constant.ApprovalAction.POLICY_RELATED_FINANCIAL_BOND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.POLICY_RELATED_FINANCIAL_BOND_TRANSACTION.BOND_ACCRUAL_IN :
					approvalId[0] = Constant.ApprovalLoanType.POLICY_RELATED_FINANCIAL_BOND;
					approvalId[1] = Constant.ApprovalAction.POLICY_RELATED_FINANCIAL_BOND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.POLICY_RELATED_FINANCIAL_BOND_TRANSACTION.BOND_BUYIN :
					approvalId[0] = Constant.ApprovalLoanType.POLICY_RELATED_FINANCIAL_BOND;
					approvalId[1] = Constant.ApprovalAction.POLICY_RELATED_FINANCIAL_BOND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.POLICY_RELATED_FINANCIAL_BOND_TRANSACTION.BOND_MATURITY_REPAY :
					approvalId[0] = Constant.ApprovalLoanType.POLICY_RELATED_FINANCIAL_BOND;
					approvalId[1] = Constant.ApprovalAction.POLICY_RELATED_FINANCIAL_BOND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.POLICY_RELATED_FINANCIAL_BOND_TRANSACTION.BOND_SELL :
					approvalId[0] = Constant.ApprovalLoanType.POLICY_RELATED_FINANCIAL_BOND;
					approvalId[1] = Constant.ApprovalAction.POLICY_RELATED_FINANCIAL_BOND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.ENTERPRISE_BOND_BID.BOND_BID :
					approvalId[0] = Constant.ApprovalLoanType.ENTERPRISE_BOND;
					approvalId[1] = Constant.ApprovalAction.ENTERPRISE_BOND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.ENTERPRISE_BOND_BID.BOND_BID_MARGIN_REPAY :
					approvalId[0] = Constant.ApprovalLoanType.ENTERPRISE_BOND;
					approvalId[1] = Constant.ApprovalAction.ENTERPRISE_BOND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.ENTERPRISE_BOND_BID.BOND_BID_MARGIN_SUPPLY :
					approvalId[0] = Constant.ApprovalLoanType.ENTERPRISE_BOND;
					approvalId[1] = Constant.ApprovalAction.ENTERPRISE_BOND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.ENTERPRISE_BOND_BID.BOND_BID_QUOTA_CONFIRM :
					approvalId[0] = Constant.ApprovalLoanType.ENTERPRISE_BOND;
					approvalId[1] = Constant.ApprovalAction.ENTERPRISE_BOND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.ENTERPRISE_BOND_TRANSACTION.BOND_ACCRUAL_IN :
					approvalId[0] = Constant.ApprovalLoanType.ENTERPRISE_BOND;
					approvalId[1] = Constant.ApprovalAction.ENTERPRISE_BOND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.ENTERPRISE_BOND_TRANSACTION.BOND_BUYIN :
					approvalId[0] = Constant.ApprovalLoanType.ENTERPRISE_BOND;
					approvalId[1] = Constant.ApprovalAction.ENTERPRISE_BOND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.ENTERPRISE_BOND_TRANSACTION.BOND_MATURITY_REPAY :
					approvalId[0] = Constant.ApprovalLoanType.ENTERPRISE_BOND;
					approvalId[1] = Constant.ApprovalAction.ENTERPRISE_BOND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.ENTERPRISE_BOND_TRANSACTION.BOND_SELL :
					approvalId[0] = Constant.ApprovalLoanType.ENTERPRISE_BOND;
					approvalId[1] = Constant.ApprovalAction.ENTERPRISE_BOND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.TRANSFORMABLE_BOND_BID_ONLINE.BOND_BID_ONLINE :
					approvalId[0] = Constant.ApprovalLoanType.TRANSFORMABLE_BOND;
					approvalId[1] = Constant.ApprovalAction.TRANSFORMABLE_BOND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.TRANSFORMABLE_BOND_BID_ONLINE.BOND_BID_REPAY :
					approvalId[0] = Constant.ApprovalLoanType.TRANSFORMABLE_BOND;
					approvalId[1] = Constant.ApprovalAction.TRANSFORMABLE_BOND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.TRANSFORMABLE_BOND_BID_ONLINE.BOND_BID_WIN :
					approvalId[0] = Constant.ApprovalLoanType.TRANSFORMABLE_BOND;
					approvalId[1] = Constant.ApprovalAction.TRANSFORMABLE_BOND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.TRANSFORMABLE_BOND_BID.BOND_BID :
					approvalId[0] = Constant.ApprovalLoanType.TRANSFORMABLE_BOND;
					approvalId[1] = Constant.ApprovalAction.TRANSFORMABLE_BOND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.TRANSFORMABLE_BOND_BID.BOND_BID_MARGIN_REPAY :
					approvalId[0] = Constant.ApprovalLoanType.TRANSFORMABLE_BOND;
					approvalId[1] = Constant.ApprovalAction.TRANSFORMABLE_BOND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.TRANSFORMABLE_BOND_BID.BOND_BID_MARGIN_SUPPAY :
					approvalId[0] = Constant.ApprovalLoanType.TRANSFORMABLE_BOND;
					approvalId[1] = Constant.ApprovalAction.TRANSFORMABLE_BOND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.TRANSFORMABLE_BOND_BID.BOND_BID_QUOTA_CONFIRM :
					approvalId[0] = Constant.ApprovalLoanType.TRANSFORMABLE_BOND;
					approvalId[1] = Constant.ApprovalAction.TRANSFORMABLE_BOND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.TRANSFORMABLE_BOND_TRANSACTION.BOND_ACCRUAL_IN :
					approvalId[0] = Constant.ApprovalLoanType.TRANSFORMABLE_BOND;
					approvalId[1] = Constant.ApprovalAction.TRANSFORMABLE_BOND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.TRANSFORMABLE_BOND_TRANSACTION.BOND_BUYIN :
					approvalId[0] = Constant.ApprovalLoanType.TRANSFORMABLE_BOND;
					approvalId[1] = Constant.ApprovalAction.TRANSFORMABLE_BOND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.TRANSFORMABLE_BOND_TRANSACTION.BOND_MATURITY_REPAY :
					approvalId[0] = Constant.ApprovalLoanType.TRANSFORMABLE_BOND;
					approvalId[1] = Constant.ApprovalAction.TRANSFORMABLE_BOND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.TRANSFORMABLE_BOND_TRANSACTION.BOND_SELL :
					approvalId[0] = Constant.ApprovalLoanType.TRANSFORMABLE_BOND;
					approvalId[1] = Constant.ApprovalAction.TRANSFORMABLE_BOND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.DEBT_TO_EQUITY.DEBT_TO_EQUITY :
					approvalId[0] = Constant.ApprovalLoanType.TRANSFORMABLE_BOND;
					approvalId[1] = Constant.ApprovalAction.TRANSFORMABLE_BOND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.ENCLOSED_FUND_BID_ONLINE.FUND_BID_ONLINE :
					approvalId[0] = Constant.ApprovalLoanType.FUND;
					approvalId[1] = Constant.ApprovalAction.FUND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.ENCLOSED_FUND_BID_ONLINE.FUND_BID_REPAY :
					approvalId[0] = Constant.ApprovalLoanType.FUND;
					approvalId[1] = Constant.ApprovalAction.FUND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.ENCLOSED_FUND_BID_ONLINE.FUND_BID_WIN :
					approvalId[0] = Constant.ApprovalLoanType.FUND;
					approvalId[1] = Constant.ApprovalAction.FUND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.ENCLOSED_FUND_BID.FUND_BID :
					approvalId[0] = Constant.ApprovalLoanType.FUND;
					approvalId[1] = Constant.ApprovalAction.FUND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.ENCLOSED_FUND_BID.FUND_BID_MARGIN_REPAY :
					approvalId[0] = Constant.ApprovalLoanType.FUND;
					approvalId[1] = Constant.ApprovalAction.FUND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.ENCLOSED_FUND_BID.FUND_BID_MARGIN_SUPPLY :
					approvalId[0] = Constant.ApprovalLoanType.FUND;
					approvalId[1] = Constant.ApprovalAction.FUND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.ENCLOSED_FUND_BID.FUND_BID_QUOTA_CONFIRM :
					approvalId[0] = Constant.ApprovalLoanType.FUND;
					approvalId[1] = Constant.ApprovalAction.FUND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.ENCLOSED_FUND_TRANSACTION.FUND_BUYIN :
					approvalId[0] = Constant.ApprovalLoanType.FUND;
					approvalId[1] = Constant.ApprovalAction.FUND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.ENCLOSED_FUND_TRANSACTION.FUND_MATURITY_SETTLE :
					approvalId[0] = Constant.ApprovalLoanType.FUND;
					approvalId[1] = Constant.ApprovalAction.FUND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.ENCLOSED_FUND_TRANSACTION.FUND_MELON_CUTTING :
					approvalId[0] = Constant.ApprovalLoanType.FUND;
					approvalId[1] = Constant.ApprovalAction.FUND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.ENCLOSED_FUND_TRANSACTION.FUND_SELL :
					approvalId[0] = Constant.ApprovalLoanType.FUND;
					approvalId[1] = Constant.ApprovalAction.FUND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.MUTUAL_FUND_SUBSCRIBE.FUND_SUBSCRIBE :
					approvalId[0] = Constant.ApprovalLoanType.FUND;
					approvalId[1] = Constant.ApprovalAction.FUND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.MUTUAL_FUND_SUBSCRIBE.FUND_SUBSCRIBE_CONFIRM :
					approvalId[0] = Constant.ApprovalLoanType.FUND;
					approvalId[1] = Constant.ApprovalAction.FUND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.MUTUAL_FUND_BID.FUND_BID :
					approvalId[0] = Constant.ApprovalLoanType.FUND;
					approvalId[1] = Constant.ApprovalAction.FUND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.MUTUAL_FUND_BID.FUND_BID_CONFIRM :
					approvalId[0] = Constant.ApprovalLoanType.FUND;
					approvalId[1] = Constant.ApprovalAction.FUND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.MUTUAL_FUND_REDEEM.FUND_REDEEM :
					approvalId[0] = Constant.ApprovalLoanType.FUND;
					approvalId[1] = Constant.ApprovalAction.FUND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.MUTUAL_FUND_MELON_CUTTING.FUND_CASH_MELON_CUTTING :
					approvalId[0] = Constant.ApprovalLoanType.FUND;
					approvalId[1] = Constant.ApprovalAction.FUND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.MUTUAL_FUND_MELON_CUTTING.FUND_SHARE_MELON_CUTTING :
					approvalId[0] = Constant.ApprovalLoanType.FUND;
					approvalId[1] = Constant.ApprovalAction.FUND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.CAPITAL_REPURCHASE.CAPITAL_REPURCHASE :
					approvalId[0] = Constant.ApprovalLoanType.CAPITAL_REPURCHASE;
					approvalId[1] = Constant.ApprovalAction.CAPITAL_REPURCHASE_APPLY;
					break;
				case (int) SECConstant.BusinessType.CAPITAL_REPURCHASE.INTEREST_PAYMENT :
					approvalId[0] = Constant.ApprovalLoanType.CAPITAL_REPURCHASE;
					approvalId[1] = Constant.ApprovalAction.CAPITAL_REPURCHASE_NOTICE;
					break;
				case (int) SECConstant.BusinessType.CAPITAL_REPURCHASE.PAYBACK_NOTIFY :
					approvalId[0] = Constant.ApprovalLoanType.CAPITAL_REPURCHASE;
					approvalId[1] = Constant.ApprovalAction.CAPITAL_REPURCHASE_NOTICE;
					break;
				case (int) SECConstant.BusinessType.CAPITAL_REPURCHASE.REPURCHASE_NOTIFY :
					approvalId[0] = Constant.ApprovalLoanType.CAPITAL_REPURCHASE;
					approvalId[1] = Constant.ApprovalAction.CAPITAL_REPURCHASE_NOTICE;
					break;
				case (int) SECConstant.BusinessType.ENTRUST_FINANCING.CORPORACITY_DRAWBACK_NOTIFY :
					approvalId[0] = Constant.ApprovalLoanType.ENTRUST_FINANCING;
					approvalId[1] = Constant.ApprovalAction.ENTRUST_FINANCING_NOTICE;
					break;
				case (int) SECConstant.BusinessType.ENTRUST_FINANCING.CORPORACITY_PAYMENT_NOTIFY :
					approvalId[0] = Constant.ApprovalLoanType.ENTRUST_FINANCING;
					approvalId[1] = Constant.ApprovalAction.ENTRUST_FINANCING_NOTICE;
					break;
				case (int) SECConstant.BusinessType.ENTRUST_FINANCING.ENTRUST_FINANCING :
					approvalId[0] = Constant.ApprovalLoanType.ENTRUST_FINANCING;
					approvalId[1] = Constant.ApprovalAction.ENTRUST_FINANCING_NOTICE;
					break;
				case (int) SECConstant.BusinessType.ENTRUST_FINANCING.SECURITIES_PAYMENT_NOTIFY :
					approvalId[0] = Constant.ApprovalLoanType.ENTRUST_FINANCING;
					approvalId[1] = Constant.ApprovalAction.ENTRUST_FINANCING_NOTICE;
					break;
				case (int) SECConstant.BusinessType.ENTRUST_FINANCING.SECURITIES_DRAWBACK_NOTIFY :
					approvalId[0] = Constant.ApprovalLoanType.ENTRUST_FINANCING;
					approvalId[1] = Constant.ApprovalAction.ENTRUST_FINANCING_NOTICE;
					break;
				case (int) SECConstant.BusinessType.ENTRUST_FINANCING.INCOME_DRAWBACK_NOTIFY :
					approvalId[0] = Constant.ApprovalLoanType.ENTRUST_FINANCING;
					approvalId[1] = Constant.ApprovalAction.ENTRUST_FINANCING_NOTICE;
					break;
				case (int) SECConstant.BusinessType.ENTRUSTED_FINANCING.CORPORACITY_DRAWBACK_NOTIFY :
					approvalId[0] = Constant.ApprovalLoanType.ENTRUSTED_FINANCING;
					approvalId[1] = Constant.ApprovalAction.ENTRUSTED_FINANCING_NOTICE;
					break;
				case (int) SECConstant.BusinessType.ENTRUSTED_FINANCING.CORPORACITY_PAYMENT_NOTIFY :
					approvalId[0] = Constant.ApprovalLoanType.ENTRUSTED_FINANCING;
					approvalId[1] = Constant.ApprovalAction.ENTRUSTED_FINANCING_NOTICE;
					break;
				case (int) SECConstant.BusinessType.ENTRUSTED_FINANCING.ENTRUSTED_FINANCING :
					approvalId[0] = Constant.ApprovalLoanType.ENTRUSTED_FINANCING;
					approvalId[1] = Constant.ApprovalAction.ENTRUSTED_FINANCING_NOTICE;
					break;
				case (int) SECConstant.BusinessType.ENTRUSTED_FINANCING.INCOME_PAYMENT_NOTIFY :
					approvalId[0] = Constant.ApprovalLoanType.ENTRUSTED_FINANCING;
					approvalId[1] = Constant.ApprovalAction.ENTRUSTED_FINANCING_NOTICE;
					break;
				case (int) SECConstant.BusinessType.FOREIGN_CURRENCY_INVESTMENT.FOREIGN_CURRENCY_INVESTMENT :
					approvalId[0] = Constant.ApprovalLoanType.FOREIGN_CURRENCY_INVESTMENT;
					approvalId[1] = Constant.ApprovalAction.FOREIGN_CURRENCY_INVESTMENT_NOTICE;
					break;
				case (int) SECConstant.BusinessType.FOREIGN_CURRENCY_INVESTMENT.INCOME_DRAWBACK_NOTIFY :
					approvalId[0] = Constant.ApprovalLoanType.FOREIGN_CURRENCY_INVESTMENT;
					approvalId[1] = Constant.ApprovalAction.FOREIGN_CURRENCY_INVESTMENT_NOTICE;
					break;
				case (int) SECConstant.BusinessType.FOREIGN_CURRENCY_INVESTMENT.INVESTMENT_DRAWBACK_NOTIFY :
					approvalId[0] = Constant.ApprovalLoanType.FOREIGN_CURRENCY_INVESTMENT;
					approvalId[1] = Constant.ApprovalAction.FOREIGN_CURRENCY_INVESTMENT_NOTICE;
					break;
				case (int) SECConstant.BusinessType.FOREIGN_CURRENCY_INVESTMENT.INVESTMENT_PAYMENT_NOTIFY :
					approvalId[0] = Constant.ApprovalLoanType.FOREIGN_CURRENCY_INVESTMENT;
					approvalId[1] = Constant.ApprovalAction.FOREIGN_CURRENCY_INVESTMENT_NOTICE;
					break;
				case (int) SECConstant.BusinessType.BOND_UNDERWRITING.BOND_UNDERWRITING :
					approvalId[0] = Constant.ApprovalLoanType.BOND_UNDERWRITING;
					approvalId[1] = Constant.ApprovalAction.BOND_UNDERWRITING_NOTICE;
					break;
				case (int) SECConstant.BusinessType.BOND_UNDERWRITING.INCOME_DRAWBACK_NOTIFY :
					approvalId[0] = Constant.ApprovalLoanType.BOND_UNDERWRITING;
					approvalId[1] = Constant.ApprovalAction.BOND_UNDERWRITING_NOTICE;
					break;
				case (int) SECConstant.BusinessType.BOND_UNDERWRITING.UNDERWRITING_DRAWBACK_NOTIFY :
					approvalId[0] = Constant.ApprovalLoanType.BOND_UNDERWRITING;
					approvalId[1] = Constant.ApprovalAction.BOND_UNDERWRITING_NOTICE;
					break;
				case (int) SECConstant.BusinessType.BOND_UNDERWRITING.UNDERWRITING_PAYMENT_NOTIFY :
					approvalId[0] = Constant.ApprovalLoanType.BOND_UNDERWRITING;
					approvalId[1] = Constant.ApprovalAction.BOND_UNDERWRITING_NOTICE;
					break;
			}
		}
		//合同
		else if (lActionID == 3)
		{
			switch ((int) lTransactionTypeID)
			{
				case (int) SECConstant.BusinessType.CAPITAL_REPURCHASE.CAPITAL_REPURCHASE :
					approvalId[0] = Constant.ApprovalLoanType.CAPITAL_REPURCHASE;
					approvalId[1] = Constant.ApprovalAction.CAPITAL_REPURCHASE_CONTRACT;
					break;
				case (int) SECConstant.BusinessType.CAPITAL_REPURCHASE.INTEREST_PAYMENT :
					approvalId[0] = Constant.ApprovalLoanType.CAPITAL_REPURCHASE;
					approvalId[1] = Constant.ApprovalAction.CAPITAL_REPURCHASE_CONTRACT;
					break;
				case (int) SECConstant.BusinessType.CAPITAL_REPURCHASE.PAYBACK_NOTIFY :
					approvalId[0] = Constant.ApprovalLoanType.CAPITAL_REPURCHASE;
					approvalId[1] = Constant.ApprovalAction.CAPITAL_REPURCHASE_CONTRACT;
					break;
				case (int) SECConstant.BusinessType.CAPITAL_REPURCHASE.REPURCHASE_NOTIFY :
					approvalId[0] = Constant.ApprovalLoanType.CAPITAL_REPURCHASE;
					approvalId[1] = Constant.ApprovalAction.CAPITAL_REPURCHASE_CONTRACT;
					break;
				case (int) SECConstant.BusinessType.ENTRUST_FINANCING.CORPORACITY_DRAWBACK_NOTIFY :
					approvalId[0] = Constant.ApprovalLoanType.ENTRUST_FINANCING;
					approvalId[1] = Constant.ApprovalAction.ENTRUST_FINANCING_CONTRACT;
					break;
				case (int) SECConstant.BusinessType.ENTRUST_FINANCING.CORPORACITY_PAYMENT_NOTIFY :
					approvalId[0] = Constant.ApprovalLoanType.ENTRUST_FINANCING;
					approvalId[1] = Constant.ApprovalAction.ENTRUST_FINANCING_CONTRACT;
					break;
				case (int) SECConstant.BusinessType.ENTRUST_FINANCING.ENTRUST_FINANCING :
					approvalId[0] = Constant.ApprovalLoanType.ENTRUST_FINANCING;
					approvalId[1] = Constant.ApprovalAction.ENTRUST_FINANCING_CONTRACT;
					break;
				case (int) SECConstant.BusinessType.ENTRUST_FINANCING.INCOME_DRAWBACK_NOTIFY :
					approvalId[0] = Constant.ApprovalLoanType.ENTRUST_FINANCING;
					approvalId[1] = Constant.ApprovalAction.ENTRUST_FINANCING_CONTRACT;
					break;
				case (int) SECConstant.BusinessType.ENTRUSTED_FINANCING.CORPORACITY_DRAWBACK_NOTIFY :
					approvalId[0] = Constant.ApprovalLoanType.ENTRUSTED_FINANCING;
					approvalId[1] = Constant.ApprovalAction.ENTRUSTED_FINANCING_CONTRACT;
					break;
				case (int) SECConstant.BusinessType.ENTRUSTED_FINANCING.CORPORACITY_PAYMENT_NOTIFY :
					approvalId[0] = Constant.ApprovalLoanType.ENTRUSTED_FINANCING;
					approvalId[1] = Constant.ApprovalAction.ENTRUSTED_FINANCING_CONTRACT;
					break;
				case (int) SECConstant.BusinessType.ENTRUSTED_FINANCING.ENTRUSTED_FINANCING :
					approvalId[0] = Constant.ApprovalLoanType.ENTRUSTED_FINANCING;
					approvalId[1] = Constant.ApprovalAction.ENTRUSTED_FINANCING_CONTRACT;
					break;
				case (int) SECConstant.BusinessType.ENTRUSTED_FINANCING.INCOME_PAYMENT_NOTIFY :
					approvalId[0] = Constant.ApprovalLoanType.ENTRUSTED_FINANCING;
					approvalId[1] = Constant.ApprovalAction.ENTRUSTED_FINANCING_CONTRACT;
					break;
				case (int) SECConstant.BusinessType.FOREIGN_CURRENCY_INVESTMENT.FOREIGN_CURRENCY_INVESTMENT :
					approvalId[0] = Constant.ApprovalLoanType.FOREIGN_CURRENCY_INVESTMENT;
					approvalId[1] = Constant.ApprovalAction.FOREIGN_CURRENCY_INVESTMENT_CONTRACT;
					break;
				case (int) SECConstant.BusinessType.FOREIGN_CURRENCY_INVESTMENT.INCOME_DRAWBACK_NOTIFY :
					approvalId[0] = Constant.ApprovalLoanType.FOREIGN_CURRENCY_INVESTMENT;
					approvalId[1] = Constant.ApprovalAction.FOREIGN_CURRENCY_INVESTMENT_CONTRACT;
					break;
				case (int) SECConstant.BusinessType.FOREIGN_CURRENCY_INVESTMENT.INVESTMENT_DRAWBACK_NOTIFY :
					approvalId[0] = Constant.ApprovalLoanType.FOREIGN_CURRENCY_INVESTMENT;
					approvalId[1] = Constant.ApprovalAction.FOREIGN_CURRENCY_INVESTMENT_CONTRACT;
					break;
				case (int) SECConstant.BusinessType.FOREIGN_CURRENCY_INVESTMENT.INVESTMENT_PAYMENT_NOTIFY :
					approvalId[0] = Constant.ApprovalLoanType.FOREIGN_CURRENCY_INVESTMENT;
					approvalId[1] = Constant.ApprovalAction.FOREIGN_CURRENCY_INVESTMENT_CONTRACT;
					break;
				case (int) SECConstant.BusinessType.BOND_UNDERWRITING.BOND_UNDERWRITING :
					approvalId[0] = Constant.ApprovalLoanType.BOND_UNDERWRITING;
					approvalId[1] = Constant.ApprovalAction.BOND_UNDERWRITING_CONTRACT;
					break;
				case (int) SECConstant.BusinessType.BOND_UNDERWRITING.INCOME_DRAWBACK_NOTIFY :
					approvalId[0] = Constant.ApprovalLoanType.BOND_UNDERWRITING;
					approvalId[1] = Constant.ApprovalAction.BOND_UNDERWRITING_CONTRACT;
					break;
				case (int) SECConstant.BusinessType.BOND_UNDERWRITING.UNDERWRITING_DRAWBACK_NOTIFY :
					approvalId[0] = Constant.ApprovalLoanType.BOND_UNDERWRITING;
					approvalId[1] = Constant.ApprovalAction.BOND_UNDERWRITING_CONTRACT;
					break;
				case (int) SECConstant.BusinessType.BOND_UNDERWRITING.UNDERWRITING_PAYMENT_NOTIFY :
					approvalId[0] = Constant.ApprovalLoanType.BOND_UNDERWRITING;
					approvalId[1] = Constant.ApprovalAction.BOND_UNDERWRITING_CONTRACT;
					break;
			}
		}
		/*if (lModuleTypeID > 0 && approvalId[0] > 0 && approvalId[1] > 0)
		{
			try {
				Log.print("lModuleTypeID       = " + lModuleTypeID);
				Log.print("approvalId[0] = " + approvalId[0]);
				Log.print("approvalId[1]   = " + approvalId[1]);
				//获得ApprovalID
				//lApprovalID = appbiz.getApprovalID(lModuleTypeID,approvalId[0],approvalId[1]);
			} catch (Exception e) {
				Log.print("getApprovalID fail");
				e.printStackTrace();
			}
		}*/
		return approvalId;
	}
	
	
	
	/*通过交易类型获得收支方向
	 * 
	 */
	public static String getReceiveOrPayDirectionByTransactionTypeId(long lTransactionTypeID)
	{
		String direction = "";
		switch ((int) lTransactionTypeID)
		{
			//资产回购
			case (int)SECConstant.BusinessType.CAPITAL_REPURCHASE.REPURCHASE_NOTIFY:
				direction = "收款";
				break;
			case (int)SECConstant.BusinessType.CAPITAL_REPURCHASE.PAYBACK_NOTIFY:
				direction = "付款";
				break;
			case (int)SECConstant.BusinessType.CAPITAL_REPURCHASE.INTEREST_PAYMENT:
				direction = "付款";
				break;
            case (int)SECConstant.BusinessType.CAPITAL_REPURCHASE.AVERAGE_NOTIFY:
                direction = "付款";
                break;
            case (int)SECConstant.BusinessType.CAPITAL_REPURCHASE.ACCEPT_NOTIFY:
                direction = "收款";
                break;
            case (int)SECConstant.BusinessType.CAPITAL_REPURCHASE.BREAK_NOTIFY:
                direction = "收款";
                break;
			//委托理财
			case (int)SECConstant.BusinessType.ENTRUST_FINANCING.CORPORACITY_PAYMENT_NOTIFY:
				direction = "付款";
				break;
			case (int)SECConstant.BusinessType.ENTRUST_FINANCING.CORPORACITY_DRAWBACK_NOTIFY:
				direction = "收款";
				break;
			case (int)SECConstant.BusinessType.ENTRUST_FINANCING.INCOME_DRAWBACK_NOTIFY:
				direction = "收款";
				break;
			//受托理财
			case (int)SECConstant.BusinessType.ENTRUSTED_FINANCING.CORPORACITY_DRAWBACK_NOTIFY:
				direction = "收款";
				break;
			case (int)SECConstant.BusinessType.ENTRUSTED_FINANCING.CORPORACITY_PAYMENT_NOTIFY:
				direction = "付款";
				break;
			case (int)SECConstant.BusinessType.ENTRUSTED_FINANCING.INCOME_PAYMENT_NOTIFY:
				direction = "付款";
				break;
			//结构性投资
			case (int)SECConstant.BusinessType.FOREIGN_CURRENCY_INVESTMENT.INVESTMENT_PAYMENT_NOTIFY:
				direction = "付款";
				break;
			case (int)SECConstant.BusinessType.FOREIGN_CURRENCY_INVESTMENT.INVESTMENT_DRAWBACK_NOTIFY:
				direction = "收款";
				break;
			case (int)SECConstant.BusinessType.FOREIGN_CURRENCY_INVESTMENT.INCOME_DRAWBACK_NOTIFY:
				direction = "收款";
				break;
			//债券承销
			case (int)SECConstant.BusinessType.BOND_UNDERWRITING.UNDERWRITING_DRAWBACK_NOTIFY:
				direction = "收款";
				break;
			case (int)SECConstant.BusinessType.BOND_UNDERWRITING.UNDERWRITING_PAYMENT_NOTIFY:
				direction = "付款";
				break;
			case (int)SECConstant.BusinessType.BOND_UNDERWRITING.INCOME_DRAWBACK_NOTIFY:
				direction = "收款";
				break;
		}
		return direction;
	}
	
	/**
	 * 通过交易对手ID获得交易对手债券托管账号
	 */
	private static HashMap cacheCounterpartTrusteeCodeToCounterpartId = new HashMap();
	public static String getCounterpartTrusteeCodeByCounterpartId(long lID){
		return getNameByID("SecuritiesTrusteeCode","sec_counterpart","id",String.valueOf(lID),cacheCounterpartTrusteeCodeToCounterpartId);
	}
	
	/**
	 * 通过合同ID获得合同代码
	 * @param lID
	 * @return
	 */
	public static String getContractCodeByID(long lID){
		return getNameByID("Code","sec_applyContract","id",String.valueOf(lID),null);
	}
	
	/**
	 * 通过合同ID获得交易对手ID
	 * @param lID
	 * @return
	 */
	public static String getCounterpartIDByContractID(long lID){
		return getNameByID("counterpartId","sec_applyContract","id",String.valueOf(lID),null);
	}
	
	/**
	 * 通过证券ID获得证券类型ID
	 * @param lID
	 * @return
	 */
	private static HashMap cacheSecuritiesTypeIDToSecuritiesId = new HashMap();
	private static HashMap cacheSecuritiesTypeIDBySecuritiesID = new HashMap();
	public static String getSecuritiesTypeIDBySecuritiesID(long lID){
		return getNameByID("TypeId","sec_securities","id",String.valueOf(lID),cacheSecuritiesTypeIDToSecuritiesId);
	}
	/**
	 * 通过结算的帐户ID获得帐户代码
	 * @param lID
	 * @return
	 */
	public static String getSettAccountNoById(long lID){
		return getNameByID("sAccountNo","sett_account","id",String.valueOf(lID),null);
	}
	
	/**
	 * 通过证券ID获得NoticeWithSecuritiesId
	 */
	public static String getNoticeWithSecuritiesId(long lID){
		return getNameByID("noticeWithSecId","SEC_NoticeSecMagnifier","id",String.valueOf(lID),null);
	}
	
	public static long[] getLoantypidAndActionidByTransactionid(long lActionID,long lTransactionTypeID){
		long lApprovalLoanTypeID=-1;
		long lApprovalActionID=-1;
		
		//申请书
		if (lActionID == 1)
		{
			switch ((int) lTransactionTypeID)
			{
				case (int) SECConstant.BusinessType.CAPITAL_IN_CREDIT_EXTENSION.CAPITAL_IN_CREDIT_EXTENSION :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.CAPITAL_IN_CREDIT_EXTENSION;
				    lApprovalActionID = Constant.ApprovalAction.CAPITAL_IN_CREDIT_EXTENSION_APPLY;
					break;
				case (int) SECConstant.BusinessType.CAPITAL_OUT_CREDIT_EXTENSION.CAPITAL_OUT_CREDIT_EXTENSION :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.CAPITAL_IN_CREDIT_EXTENSION;
			    	lApprovalActionID = Constant.ApprovalAction.CAPITAL_IN_CREDIT_EXTENSION_APPLY;
					break;
				case (int) SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_IN :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.CAPITAL_LANDING;
		    		lApprovalActionID = Constant.ApprovalAction.CAPITAL_IN_APPLY;
					break;
				case (int) SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_IN_REPAY :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.CAPITAL_LANDING;
				    lApprovalActionID = Constant.ApprovalAction.CAPITAL_IN_APPLY;
					break;
				case (int) SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_OUT :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.CAPITAL_LANDING;
				    lApprovalActionID = Constant.ApprovalAction.CAPITAL_OUT_APPLY;
					break;
				case (int) SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_OUT_REPAY :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.CAPITAL_LANDING;
				    lApprovalActionID = Constant.ApprovalAction.CAPITAL_OUT_APPLY;
					break;
				case (int) SECConstant.BusinessType.STOCK_BID_ONLINE.INITIAL_OFFERING_BID_ONLINE :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.STOCK;
				    lApprovalActionID = Constant.ApprovalAction.STOCK_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.STOCK_BID_ONLINE.INITIAL_OFFERING_BID_REPAY :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.STOCK;
				    lApprovalActionID = Constant.ApprovalAction.STOCK_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.STOCK_BID_ONLINE.INITIAL_OFFERING_BID_WIN :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.STOCK;
				    lApprovalActionID = Constant.ApprovalAction.STOCK_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.STOCK_BID_ONLINE.PROMOTION_BID_ONLINE :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.STOCK;
				    lApprovalActionID = Constant.ApprovalAction.STOCK_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.STOCK_BID_ONLINE.PROMOTION_BID_REPAY :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.STOCK;
				    lApprovalActionID = Constant.ApprovalAction.STOCK_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.STOCK_BID_ONLINE.PROMOTION_BID_WIN :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.STOCK;
				    lApprovalActionID = Constant.ApprovalAction.STOCK_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.STOCK_BID.INITIAL_OFFERING_BID :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.STOCK;
				    lApprovalActionID = Constant.ApprovalAction.STOCK_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.STOCK_BID.INITIAL_OFFERING_MARGIN_REPAY :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.STOCK;
				    lApprovalActionID = Constant.ApprovalAction.STOCK_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.STOCK_BID.INITIAL_OFFERING_MARGIN_SUPPLY :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.STOCK;
				    lApprovalActionID = Constant.ApprovalAction.STOCK_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.STOCK_BID.INITIAL_OFFERING_QUOTA_CONFIRM :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.STOCK;
				    lApprovalActionID = Constant.ApprovalAction.STOCK_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.STOCK_BID.PROMOTION_BID :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.STOCK;
				    lApprovalActionID = Constant.ApprovalAction.STOCK_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.STOCK_BID.PROMOTION_BID_MARGIN_REPAY :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.STOCK;
				    lApprovalActionID = Constant.ApprovalAction.STOCK_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.STOCK_BID.PROMOTION_BID_MARGIN_SUPPLY :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.STOCK;
				    lApprovalActionID = Constant.ApprovalAction.STOCK_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.STOCK_BID.PROMOTION_BID_QUOTA_CONFIRM :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.STOCK;
				    lApprovalActionID = Constant.ApprovalAction.STOCK_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.STOCK_TRANSACTION.STOCK_BUYIN :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.STOCK;
				    lApprovalActionID = Constant.ApprovalAction.STOCK_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.STOCK_TRANSACTION.STOCK_MELON_CUTTING :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.STOCK;
				    lApprovalActionID = Constant.ApprovalAction.STOCK_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.STOCK_TRANSACTION.STOCK_RATION :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.STOCK;
				    lApprovalActionID = Constant.ApprovalAction.STOCK_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.STOCK_TRANSACTION.STOCK_RATION_ONLINE :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.STOCK;
				    lApprovalActionID = Constant.ApprovalAction.STOCK_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.STOCK_TRANSACTION.STOCK_SELL :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.STOCK;
				    lApprovalActionID = Constant.ApprovalAction.STOCK_SELL_APPLY;
					break;
				case (int) SECConstant.BusinessType.STOCK_TRANSACTION.STOCK_TAKEBACK :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.STOCK;
				    lApprovalActionID = Constant.ApprovalAction.STOCK_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.CENTRAL_BANK_NOTE_BID.NOTE_BID :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.CENTRAL_BANK_NOTE;
				    lApprovalActionID = Constant.ApprovalAction.NOTE_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.CENTRAL_BANK_NOTE_TRANSACTION.NOTE_ACCRUAL_IN :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.CENTRAL_BANK_NOTE;
				    lApprovalActionID = Constant.ApprovalAction.NOTE_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.CENTRAL_BANK_NOTE_TRANSACTION.NOTE_BUYIN :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.CENTRAL_BANK_NOTE;
				    lApprovalActionID = Constant.ApprovalAction.NOTE_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.CENTRAL_BANK_NOTE_TRANSACTION.NOTE_MATURITY_REPAY :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.CENTRAL_BANK_NOTE;
				    lApprovalActionID = Constant.ApprovalAction.NOTE_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.CENTRAL_BANK_NOTE_TRANSACTION.NOTE_SELL :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.CENTRAL_BANK_NOTE;
				    lApprovalActionID = Constant.ApprovalAction.NOTE_SELL_APPLY;
					break;
				case (int) SECConstant.BusinessType.BANK_BOND_REPURCHASE.BOND_REPAY :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.BANK_BOND_REPURCHASE;
				    lApprovalActionID = Constant.ApprovalAction.BANK_BOND_REPURCHASE_APPLY;
					break;
				case (int) SECConstant.BusinessType.BANK_BOND_REPURCHASE.BOND_REPURCHASE :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.BANK_BOND_REPURCHASE;
				    lApprovalActionID = Constant.ApprovalAction.BANK_BOND_REPURCHASE_APPLY;
					break;
				case (int) SECConstant.BusinessType.BANK_BOND_REPURCHASE.FUND_REPAY :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.BANK_BOND_REPURCHASE;
				    lApprovalActionID = Constant.ApprovalAction.BANK_FUND_REPURCHASE_APPLY;
					break;
				case (int) SECConstant.BusinessType.BANK_BOND_REPURCHASE.FUND_REPURCHASE :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.BANK_BOND_REPURCHASE;
				    lApprovalActionID = Constant.ApprovalAction.BANK_FUND_REPURCHASE_APPLY;
					break;
				case (int) SECConstant.BusinessType.EXCHANGECENTER_BOND_REPURCHASE.BOND_REPAY :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.EXCHANGECENTER_BOND_REPURCHASE;
				    lApprovalActionID = Constant.ApprovalAction.EXCHANGECENTER_BOND_REPURCHASE_APPLY;
					break;
				case (int) SECConstant.BusinessType.EXCHANGECENTER_BOND_REPURCHASE.BOND_REPURCHASE :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.EXCHANGECENTER_BOND_REPURCHASE;
				    lApprovalActionID = Constant.ApprovalAction.EXCHANGECENTER_BOND_REPURCHASE_APPLY;
					break;
				case (int) SECConstant.BusinessType.EXCHANGECENTER_BOND_REPURCHASE.FUND_REPAY :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.EXCHANGECENTER_BOND_REPURCHASE;
				    lApprovalActionID = Constant.ApprovalAction.EXCHANGECENTER_FUND_REPURCHASE_APPLY;
					break;
				case (int) SECConstant.BusinessType.EXCHANGECENTER_BOND_REPURCHASE.FUND_REPURCHASE :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.EXCHANGECENTER_BOND_REPURCHASE;
				    lApprovalActionID = Constant.ApprovalAction.EXCHANGECENTER_FUND_REPURCHASE_APPLY;
					break;
				case (int) SECConstant.BusinessType.BANK_NATIONAL_BOND_BID.BOND_BID :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.BANK_NATIONAL_BOND;
				    lApprovalActionID = Constant.ApprovalAction.BANK_NATIONAL_BOND_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.BANK_NATIONAL_BOND_TRANSACTION.BOND_ACCRUAL_IN :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.BANK_NATIONAL_BOND;
				    lApprovalActionID = Constant.ApprovalAction.BANK_NATIONAL_BOND_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.BANK_NATIONAL_BOND_TRANSACTION.BOND_BUYIN :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.BANK_NATIONAL_BOND;
				    lApprovalActionID = Constant.ApprovalAction.BANK_NATIONAL_BOND_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.BANK_NATIONAL_BOND_TRANSACTION.BOND_MATURITY_REPAY :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.BANK_NATIONAL_BOND;
				    lApprovalActionID = Constant.ApprovalAction.BANK_NATIONAL_BOND_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.BANK_NATIONAL_BOND_TRANSACTION.BOND_SELL :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.BANK_NATIONAL_BOND;
				    lApprovalActionID = Constant.ApprovalAction.BANK_NATIONAL_BOND_SELL_APPLY;
					break;
				case (int) SECConstant.BusinessType.EXCHANGECENTER_NATIONAL_BOND_BID.BOND_BID :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.EXCHANGECENTER_NATIONAL_BOND;
				    lApprovalActionID = Constant.ApprovalAction.EXCHANGECENTER_NATIONAL_BOND_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.EXCHANGECENTER_NATIONAL_BOND_TRANSACTION.BOND_ACCRUAL_IN :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.EXCHANGECENTER_NATIONAL_BOND;
				    lApprovalActionID = Constant.ApprovalAction.EXCHANGECENTER_NATIONAL_BOND_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.EXCHANGECENTER_NATIONAL_BOND_TRANSACTION.BOND_BUYIN :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.EXCHANGECENTER_NATIONAL_BOND;
				    lApprovalActionID = Constant.ApprovalAction.EXCHANGECENTER_NATIONAL_BOND_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.EXCHANGECENTER_NATIONAL_BOND_TRANSACTION.BOND_MATURITY_REPAY :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.EXCHANGECENTER_NATIONAL_BOND;
				    lApprovalActionID = Constant.ApprovalAction.EXCHANGECENTER_NATIONAL_BOND_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.EXCHANGECENTER_NATIONAL_BOND_TRANSACTION.BOND_SELL :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.EXCHANGECENTER_NATIONAL_BOND;
				    lApprovalActionID = Constant.ApprovalAction.EXCHANGECENTER_NATIONAL_BOND_SELL_APPLY;
					break;
				case (int) SECConstant.BusinessType.FINANCIAL_BOND_BID.BOND_BID :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.FINANCIAL_BOND;
				    lApprovalActionID = Constant.ApprovalAction.FINANCIAL_BOND_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.FINANCIAL_BOND_TRANSACTION.BOND_ACCRUAL_IN :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.FINANCIAL_BOND;
				    lApprovalActionID = Constant.ApprovalAction.FINANCIAL_BOND_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.FINANCIAL_BOND_TRANSACTION.BOND_BUYIN :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.FINANCIAL_BOND;
				    lApprovalActionID = Constant.ApprovalAction.FINANCIAL_BOND_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.FINANCIAL_BOND_TRANSACTION.BOND_MATURITY_REPAY :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.FINANCIAL_BOND;
				    lApprovalActionID = Constant.ApprovalAction.FINANCIAL_BOND_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.FINANCIAL_BOND_TRANSACTION.BOND_SELL :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.FINANCIAL_BOND;
				    lApprovalActionID = Constant.ApprovalAction.FINANCIAL_BOND_SELL_APPLY;
					break;
				case (int) SECConstant.BusinessType.POLICY_RELATED_FINANCIAL_BOND_BID.BOND_BID :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.POLICY_RELATED_FINANCIAL_BOND;
				    lApprovalActionID = Constant.ApprovalAction.POLICY_RELATED_FINANCIAL_BOND_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.POLICY_RELATED_FINANCIAL_BOND_TRANSACTION.BOND_ACCRUAL_IN :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.POLICY_RELATED_FINANCIAL_BOND;
				    lApprovalActionID = Constant.ApprovalAction.POLICY_RELATED_FINANCIAL_BOND_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.POLICY_RELATED_FINANCIAL_BOND_TRANSACTION.BOND_BUYIN :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.POLICY_RELATED_FINANCIAL_BOND;
				    lApprovalActionID = Constant.ApprovalAction.POLICY_RELATED_FINANCIAL_BOND_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.POLICY_RELATED_FINANCIAL_BOND_TRANSACTION.BOND_MATURITY_REPAY :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.POLICY_RELATED_FINANCIAL_BOND;
				    lApprovalActionID = Constant.ApprovalAction.POLICY_RELATED_FINANCIAL_BOND_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.POLICY_RELATED_FINANCIAL_BOND_TRANSACTION.BOND_SELL :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.POLICY_RELATED_FINANCIAL_BOND;
				    lApprovalActionID = Constant.ApprovalAction.POLICY_RELATED_FINANCIAL_BOND_SELL_APPLY;
					break;
				case (int) SECConstant.BusinessType.ENTERPRISE_BOND_BID.BOND_BID :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.ENTERPRISE_BOND;
				    lApprovalActionID = Constant.ApprovalAction.ENTERPRISE_BOND_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.ENTERPRISE_BOND_BID.BOND_BID_MARGIN_REPAY :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.ENTERPRISE_BOND;
				    lApprovalActionID = Constant.ApprovalAction.ENTERPRISE_BOND_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.ENTERPRISE_BOND_BID.BOND_BID_MARGIN_SUPPLY :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.ENTERPRISE_BOND;
				    lApprovalActionID = Constant.ApprovalAction.ENTERPRISE_BOND_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.ENTERPRISE_BOND_BID.BOND_BID_QUOTA_CONFIRM :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.ENTERPRISE_BOND;
				    lApprovalActionID = Constant.ApprovalAction.ENTERPRISE_BOND_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.ENTERPRISE_BOND_TRANSACTION.BOND_ACCRUAL_IN :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.ENTERPRISE_BOND;
				    lApprovalActionID = Constant.ApprovalAction.ENTERPRISE_BOND_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.ENTERPRISE_BOND_TRANSACTION.BOND_BUYIN :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.ENTERPRISE_BOND;
				    lApprovalActionID = Constant.ApprovalAction.ENTERPRISE_BOND_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.ENTERPRISE_BOND_TRANSACTION.BOND_MATURITY_REPAY :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.ENTERPRISE_BOND;
				    lApprovalActionID = Constant.ApprovalAction.ENTERPRISE_BOND_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.ENTERPRISE_BOND_TRANSACTION.BOND_SELL :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.ENTERPRISE_BOND;
				    lApprovalActionID = Constant.ApprovalAction.ENTERPRISE_BOND_SELL_APPLY;
					break;
				case (int) SECConstant.BusinessType.TRANSFORMABLE_BOND_BID_ONLINE.BOND_BID_ONLINE :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.TRANSFORMABLE_BOND;
				    lApprovalActionID = Constant.ApprovalAction.TRANSFORMABLE_BOND_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.TRANSFORMABLE_BOND_BID_ONLINE.BOND_BID_REPAY :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.TRANSFORMABLE_BOND;
				    lApprovalActionID = Constant.ApprovalAction.TRANSFORMABLE_BOND_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.TRANSFORMABLE_BOND_BID_ONLINE.BOND_BID_WIN :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.TRANSFORMABLE_BOND;
				    lApprovalActionID = Constant.ApprovalAction.TRANSFORMABLE_BOND_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.TRANSFORMABLE_BOND_BID.BOND_BID :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.TRANSFORMABLE_BOND;
				    lApprovalActionID = Constant.ApprovalAction.TRANSFORMABLE_BOND_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.TRANSFORMABLE_BOND_BID.BOND_BID_MARGIN_REPAY :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.TRANSFORMABLE_BOND;
				    lApprovalActionID = Constant.ApprovalAction.TRANSFORMABLE_BOND_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.TRANSFORMABLE_BOND_BID.BOND_BID_MARGIN_SUPPAY :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.TRANSFORMABLE_BOND;
				    lApprovalActionID = Constant.ApprovalAction.TRANSFORMABLE_BOND_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.TRANSFORMABLE_BOND_BID.BOND_BID_QUOTA_CONFIRM :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.TRANSFORMABLE_BOND;
				    lApprovalActionID = Constant.ApprovalAction.TRANSFORMABLE_BOND_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.TRANSFORMABLE_BOND_TRANSACTION.BOND_ACCRUAL_IN :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.TRANSFORMABLE_BOND;
				    lApprovalActionID = Constant.ApprovalAction.TRANSFORMABLE_BOND_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.TRANSFORMABLE_BOND_TRANSACTION.BOND_BUYIN :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.TRANSFORMABLE_BOND;
				    lApprovalActionID = Constant.ApprovalAction.TRANSFORMABLE_BOND_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.TRANSFORMABLE_BOND_TRANSACTION.BOND_MATURITY_REPAY :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.TRANSFORMABLE_BOND;
				    lApprovalActionID = Constant.ApprovalAction.TRANSFORMABLE_BOND_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.TRANSFORMABLE_BOND_TRANSACTION.BOND_SELL :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.TRANSFORMABLE_BOND;
				    lApprovalActionID = Constant.ApprovalAction.TRANSFORMABLE_BOND_SELL_APPLY;
					break;
				case (int) SECConstant.BusinessType.DEBT_TO_EQUITY.DEBT_TO_EQUITY :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.TRANSFORMABLE_BOND;
				    lApprovalActionID = Constant.ApprovalAction.DEBT_TO_EQUITY;
					break;
				case (int) SECConstant.BusinessType.ENCLOSED_FUND_BID_ONLINE.FUND_BID_ONLINE :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.FUND;
				    lApprovalActionID = Constant.ApprovalAction.FUND_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.ENCLOSED_FUND_BID_ONLINE.FUND_BID_REPAY :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.FUND;
				    lApprovalActionID = Constant.ApprovalAction.FUND_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.ENCLOSED_FUND_BID_ONLINE.FUND_BID_WIN :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.FUND;
				    lApprovalActionID = Constant.ApprovalAction.FUND_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.ENCLOSED_FUND_BID.FUND_BID :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.FUND;
				    lApprovalActionID = Constant.ApprovalAction.FUND_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.ENCLOSED_FUND_BID.FUND_BID_MARGIN_REPAY :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.FUND;
				    lApprovalActionID = Constant.ApprovalAction.FUND_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.ENCLOSED_FUND_BID.FUND_BID_MARGIN_SUPPLY :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.FUND;
				    lApprovalActionID = Constant.ApprovalAction.FUND_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.ENCLOSED_FUND_BID.FUND_BID_QUOTA_CONFIRM :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.FUND;
				    lApprovalActionID = Constant.ApprovalAction.FUND_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.ENCLOSED_FUND_TRANSACTION.FUND_BUYIN :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.FUND;
				    lApprovalActionID = Constant.ApprovalAction.FUND_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.ENCLOSED_FUND_TRANSACTION.FUND_MATURITY_SETTLE :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.FUND;
				    lApprovalActionID = Constant.ApprovalAction.FUND_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.ENCLOSED_FUND_TRANSACTION.FUND_MELON_CUTTING :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.FUND;
				    lApprovalActionID = Constant.ApprovalAction.FUND_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.ENCLOSED_FUND_TRANSACTION.FUND_SELL :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.FUND;
				    lApprovalActionID = Constant.ApprovalAction.FUND_SELL_APPLY;
					break;
				case (int) SECConstant.BusinessType.MUTUAL_FUND_SUBSCRIBE.FUND_SUBSCRIBE :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.FUND;
				    lApprovalActionID = Constant.ApprovalAction.FUND_BUYIN_APPLY;
					break;
				/*************************基金转换**************************************/
				case (int) SECConstant.BusinessType.FUND_TRANSFER.FUND_TRANSFER :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.FUND;
				    lApprovalActionID = Constant.ApprovalAction.FUND_BUYIN_APPLY;
					break;
				/*************************基金转换**************************************/
				case (int) SECConstant.BusinessType.MUTUAL_FUND_SUBSCRIBE.FUND_SUBSCRIBE_CONFIRM :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.FUND;
				    lApprovalActionID = Constant.ApprovalAction.FUND_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.MUTUAL_FUND_BID.FUND_BID :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.FUND;
				    lApprovalActionID = Constant.ApprovalAction.FUND_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.MUTUAL_FUND_BID.FUND_BID_CONFIRM :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.FUND;
				    lApprovalActionID = Constant.ApprovalAction.FUND_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.MUTUAL_FUND_REDEEM.FUND_REDEEM :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.FUND;
				    lApprovalActionID = Constant.ApprovalAction.FUND_SELL_APPLY;
					break;
				case (int) SECConstant.BusinessType.MUTUAL_FUND_MELON_CUTTING.FUND_CASH_MELON_CUTTING :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.FUND;
				    lApprovalActionID = Constant.ApprovalAction.FUND_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.MUTUAL_FUND_MELON_CUTTING.FUND_SHARE_MELON_CUTTING :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.FUND;
				    lApprovalActionID = Constant.ApprovalAction.FUND_BUYIN_APPLY;
					break;
				case (int) SECConstant.BusinessType.CAPITAL_REPURCHASE.CAPITAL_REPURCHASE :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.CAPITAL_REPURCHASE;
				    lApprovalActionID = Constant.ApprovalAction.CAPITAL_REPURCHASE_APPLY;
					break;
				case (int) SECConstant.BusinessType.CAPITAL_REPURCHASE.INTEREST_PAYMENT :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.CAPITAL_REPURCHASE;
				    lApprovalActionID = Constant.ApprovalAction.CAPITAL_REPURCHASE_APPLY;
					break;
				case (int) SECConstant.BusinessType.CAPITAL_REPURCHASE.PAYBACK_NOTIFY :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.CAPITAL_REPURCHASE;
				    lApprovalActionID = Constant.ApprovalAction.CAPITAL_REPURCHASE_APPLY;
					break;
				case (int) SECConstant.BusinessType.CAPITAL_REPURCHASE.REPURCHASE_NOTIFY :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.CAPITAL_REPURCHASE;
				    lApprovalActionID = Constant.ApprovalAction.CAPITAL_REPURCHASE_APPLY;
					break;
				case (int) SECConstant.BusinessType.CAPITAL_REPURCHASE.AVERAGE_NOTIFY :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.CAPITAL_REPURCHASE;
				    lApprovalActionID = Constant.ApprovalAction.CAPITAL_REPURCHASE_APPLY;
					break;
				case (int) SECConstant.BusinessType.CAPITAL_REPURCHASE.ACCEPT_NOTIFY :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.CAPITAL_REPURCHASE;
				    lApprovalActionID = Constant.ApprovalAction.CAPITAL_REPURCHASE_APPLY;
					break;
				case (int) SECConstant.BusinessType.CAPITAL_REPURCHASE.BREAK_NOTIFY :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.CAPITAL_REPURCHASE;
				    lApprovalActionID = Constant.ApprovalAction.CAPITAL_REPURCHASE_APPLY;
					break;
				case (int) SECConstant.BusinessType.ENTRUST_FINANCING.CORPORACITY_DRAWBACK_NOTIFY :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.ENTRUST_FINANCING;
				    lApprovalActionID = Constant.ApprovalAction.ENTRUST_FINANCING_APPLY;
					break;
				case (int) SECConstant.BusinessType.ENTRUST_FINANCING.CORPORACITY_PAYMENT_NOTIFY :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.ENTRUST_FINANCING;
				    lApprovalActionID = Constant.ApprovalAction.ENTRUST_FINANCING_APPLY;
					break;
				case (int) SECConstant.BusinessType.ENTRUST_FINANCING.ENTRUST_FINANCING :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.ENTRUST_FINANCING;
				    lApprovalActionID = Constant.ApprovalAction.ENTRUST_FINANCING_APPLY;
					break;
				case (int) SECConstant.BusinessType.ENTRUST_FINANCING.INCOME_DRAWBACK_NOTIFY :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.ENTRUST_FINANCING;
				    lApprovalActionID = Constant.ApprovalAction.ENTRUST_FINANCING_APPLY;
					break;
				case (int) SECConstant.BusinessType.ENTRUSTED_FINANCING.CORPORACITY_DRAWBACK_NOTIFY :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.ENTRUSTED_FINANCING;
				    lApprovalActionID = Constant.ApprovalAction.ENTRUSTED_FINANCING_APPLY;
					break;
				case (int) SECConstant.BusinessType.ENTRUSTED_FINANCING.CORPORACITY_PAYMENT_NOTIFY :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.ENTRUSTED_FINANCING;
				    lApprovalActionID = Constant.ApprovalAction.ENTRUSTED_FINANCING_APPLY;
					break;
				case (int) SECConstant.BusinessType.ENTRUSTED_FINANCING.ENTRUSTED_FINANCING :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.ENTRUSTED_FINANCING;
				    lApprovalActionID = Constant.ApprovalAction.ENTRUSTED_FINANCING_APPLY;
					break;
				case (int) SECConstant.BusinessType.ENTRUSTED_FINANCING.INCOME_PAYMENT_NOTIFY :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.ENTRUSTED_FINANCING;
				    lApprovalActionID = Constant.ApprovalAction.ENTRUSTED_FINANCING_APPLY;
					break;
				case (int) SECConstant.BusinessType.FOREIGN_CURRENCY_INVESTMENT.FOREIGN_CURRENCY_INVESTMENT :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.FOREIGN_CURRENCY_INVESTMENT;
				    lApprovalActionID = Constant.ApprovalAction.FOREIGN_CURRENCY_INVESTMENT_APPLY;
					break;
				case (int) SECConstant.BusinessType.FOREIGN_CURRENCY_INVESTMENT.INCOME_DRAWBACK_NOTIFY :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.FOREIGN_CURRENCY_INVESTMENT;
				    lApprovalActionID = Constant.ApprovalAction.FOREIGN_CURRENCY_INVESTMENT_APPLY;
					break;
				case (int) SECConstant.BusinessType.FOREIGN_CURRENCY_INVESTMENT.INVESTMENT_DRAWBACK_NOTIFY :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.FOREIGN_CURRENCY_INVESTMENT;
				    lApprovalActionID = Constant.ApprovalAction.FOREIGN_CURRENCY_INVESTMENT_APPLY;
					break;
				case (int) SECConstant.BusinessType.FOREIGN_CURRENCY_INVESTMENT.INVESTMENT_PAYMENT_NOTIFY :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.FOREIGN_CURRENCY_INVESTMENT;
				    lApprovalActionID = Constant.ApprovalAction.FOREIGN_CURRENCY_INVESTMENT_APPLY;
					break;
				case (int) SECConstant.BusinessType.BOND_UNDERWRITING.BOND_UNDERWRITING :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.BOND_UNDERWRITING;
				    lApprovalActionID = Constant.ApprovalAction.BOND_UNDERWRITING_APPLY;
					break;
				case (int) SECConstant.BusinessType.BOND_UNDERWRITING.INCOME_DRAWBACK_NOTIFY :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.BOND_UNDERWRITING;
				    lApprovalActionID = Constant.ApprovalAction.BOND_UNDERWRITING_APPLY;
					break;
				case (int) SECConstant.BusinessType.BOND_UNDERWRITING.UNDERWRITING_DRAWBACK_NOTIFY :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.BOND_UNDERWRITING;
				    lApprovalActionID = Constant.ApprovalAction.BOND_UNDERWRITING_APPLY;
					break;
				case (int) SECConstant.BusinessType.BOND_UNDERWRITING.UNDERWRITING_PAYMENT_NOTIFY :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.BOND_UNDERWRITING;
				    lApprovalActionID = Constant.ApprovalAction.BOND_UNDERWRITING_APPLY;
					break;
			}
		}
		//业务通知单
		else if (lActionID == 2)
		{
		    switch ((int) lTransactionTypeID)
			{
				case (int) SECConstant.BusinessType.CAPITAL_IN_CREDIT_EXTENSION.CAPITAL_IN_CREDIT_EXTENSION :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.CAPITAL_IN_CREDIT_EXTENSION;
				    lApprovalActionID = Constant.ApprovalAction.CAPITAL_IN_CREDIT_EXTENSION_APPLY;
					break;
				case (int) SECConstant.BusinessType.CAPITAL_OUT_CREDIT_EXTENSION.CAPITAL_OUT_CREDIT_EXTENSION :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.CAPITAL_IN_CREDIT_EXTENSION;
			    	lApprovalActionID = Constant.ApprovalAction.CAPITAL_IN_CREDIT_EXTENSION_APPLY;
					break;
				case (int) SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_IN :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.CAPITAL_LANDING;
		    		lApprovalActionID = Constant.ApprovalAction.CAPITAL_LANDING_NOTICE;
					break;
				case (int) SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_IN_REPAY :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.CAPITAL_LANDING;
				    lApprovalActionID = Constant.ApprovalAction.CAPITAL_LANDING_NOTICE;
					break;
				case (int) SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_OUT :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.CAPITAL_LANDING;
				    lApprovalActionID = Constant.ApprovalAction.CAPITAL_LANDING_NOTICE;
					break;
				case (int) SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_OUT_REPAY :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.CAPITAL_LANDING;
				    lApprovalActionID = Constant.ApprovalAction.CAPITAL_LANDING_NOTICE;
					break;
				case (int) SECConstant.BusinessType.STOCK_BID_ONLINE.INITIAL_OFFERING_BID_ONLINE :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.STOCK;
				    lApprovalActionID = Constant.ApprovalAction.STOCK_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.STOCK_BID_ONLINE.INITIAL_OFFERING_BID_REPAY :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.STOCK;
				    lApprovalActionID = Constant.ApprovalAction.STOCK_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.STOCK_BID_ONLINE.INITIAL_OFFERING_BID_WIN :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.STOCK;
				    lApprovalActionID = Constant.ApprovalAction.STOCK_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.STOCK_BID_ONLINE.PROMOTION_BID_ONLINE :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.STOCK;
				    lApprovalActionID = Constant.ApprovalAction.STOCK_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.STOCK_BID_ONLINE.PROMOTION_BID_REPAY :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.STOCK;
				    lApprovalActionID = Constant.ApprovalAction.STOCK_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.STOCK_BID_ONLINE.PROMOTION_BID_WIN :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.STOCK;
				    lApprovalActionID = Constant.ApprovalAction.STOCK_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.STOCK_BID.INITIAL_OFFERING_BID :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.STOCK;
				    lApprovalActionID = Constant.ApprovalAction.STOCK_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.STOCK_BID.INITIAL_OFFERING_MARGIN_REPAY :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.STOCK;
				    lApprovalActionID = Constant.ApprovalAction.STOCK_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.STOCK_BID.INITIAL_OFFERING_MARGIN_SUPPLY :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.STOCK;
				    lApprovalActionID = Constant.ApprovalAction.STOCK_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.STOCK_BID.INITIAL_OFFERING_QUOTA_CONFIRM :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.STOCK;
				    lApprovalActionID = Constant.ApprovalAction.STOCK_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.STOCK_BID.PROMOTION_BID :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.STOCK;
				    lApprovalActionID = Constant.ApprovalAction.STOCK_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.STOCK_BID.PROMOTION_BID_MARGIN_REPAY :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.STOCK;
				    lApprovalActionID = Constant.ApprovalAction.STOCK_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.STOCK_BID.PROMOTION_BID_MARGIN_SUPPLY :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.STOCK;
				    lApprovalActionID = Constant.ApprovalAction.STOCK_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.STOCK_BID.PROMOTION_BID_QUOTA_CONFIRM :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.STOCK;
				    lApprovalActionID = Constant.ApprovalAction.STOCK_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.STOCK_TRANSACTION.STOCK_BUYIN :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.STOCK;
				    lApprovalActionID = Constant.ApprovalAction.STOCK_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.STOCK_TRANSACTION.STOCK_MELON_CUTTING :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.STOCK;
				    lApprovalActionID = Constant.ApprovalAction.STOCK_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.STOCK_TRANSACTION.STOCK_RATION :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.STOCK;
				    lApprovalActionID = Constant.ApprovalAction.STOCK_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.STOCK_TRANSACTION.STOCK_RATION_ONLINE :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.STOCK;
				    lApprovalActionID = Constant.ApprovalAction.STOCK_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.STOCK_TRANSACTION.STOCK_SELL :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.STOCK;
				    lApprovalActionID = Constant.ApprovalAction.STOCK_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.STOCK_TRANSACTION.STOCK_TAKEBACK :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.STOCK;
				    lApprovalActionID = Constant.ApprovalAction.STOCK_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.CENTRAL_BANK_NOTE_BID.NOTE_BID :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.CENTRAL_BANK_NOTE;
				    lApprovalActionID = Constant.ApprovalAction.CENTRAL_BANK_NOTE_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.CENTRAL_BANK_NOTE_TRANSACTION.NOTE_ACCRUAL_IN :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.CENTRAL_BANK_NOTE;
				    lApprovalActionID = Constant.ApprovalAction.CENTRAL_BANK_NOTE_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.CENTRAL_BANK_NOTE_TRANSACTION.NOTE_BUYIN :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.CENTRAL_BANK_NOTE;
				    lApprovalActionID = Constant.ApprovalAction.CENTRAL_BANK_NOTE_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.CENTRAL_BANK_NOTE_TRANSACTION.NOTE_MATURITY_REPAY :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.CENTRAL_BANK_NOTE;
				    lApprovalActionID = Constant.ApprovalAction.CENTRAL_BANK_NOTE_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.CENTRAL_BANK_NOTE_TRANSACTION.NOTE_SELL :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.CENTRAL_BANK_NOTE;
				    lApprovalActionID = Constant.ApprovalAction.CENTRAL_BANK_NOTE_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.BANK_BOND_REPURCHASE.BOND_REPAY :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.BANK_BOND_REPURCHASE;
				    lApprovalActionID = Constant.ApprovalAction.BANK_BOND_REPURCHASE_NOTICE;
					break;
				case (int) SECConstant.BusinessType.BANK_BOND_REPURCHASE.BOND_REPURCHASE :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.BANK_BOND_REPURCHASE;
				    lApprovalActionID = Constant.ApprovalAction.BANK_BOND_REPURCHASE_NOTICE;
					break;
				case (int) SECConstant.BusinessType.BANK_BOND_REPURCHASE.FUND_REPAY :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.BANK_BOND_REPURCHASE;
				    lApprovalActionID = Constant.ApprovalAction.BANK_BOND_REPURCHASE_NOTICE;
					break;
				case (int) SECConstant.BusinessType.BANK_BOND_REPURCHASE.FUND_REPURCHASE :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.BANK_BOND_REPURCHASE;
				    lApprovalActionID = Constant.ApprovalAction.BANK_BOND_REPURCHASE_NOTICE;
					break;
				case (int) SECConstant.BusinessType.EXCHANGECENTER_BOND_REPURCHASE.BOND_REPAY :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.EXCHANGECENTER_BOND_REPURCHASE;
				    lApprovalActionID = Constant.ApprovalAction.EXCHANGECENTER_BOND_REPURCHASE_NOTICE;
					break;
				case (int) SECConstant.BusinessType.EXCHANGECENTER_BOND_REPURCHASE.BOND_REPURCHASE :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.EXCHANGECENTER_BOND_REPURCHASE;
				    lApprovalActionID = Constant.ApprovalAction.EXCHANGECENTER_BOND_REPURCHASE_NOTICE;
					break;
				case (int) SECConstant.BusinessType.EXCHANGECENTER_BOND_REPURCHASE.FUND_REPAY :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.EXCHANGECENTER_BOND_REPURCHASE;
				    lApprovalActionID = Constant.ApprovalAction.EXCHANGECENTER_BOND_REPURCHASE_NOTICE;
					break;
				case (int) SECConstant.BusinessType.EXCHANGECENTER_BOND_REPURCHASE.FUND_REPURCHASE :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.EXCHANGECENTER_BOND_REPURCHASE;
				    lApprovalActionID = Constant.ApprovalAction.EXCHANGECENTER_BOND_REPURCHASE_NOTICE;
					break;
				case (int) SECConstant.BusinessType.BANK_NATIONAL_BOND_BID.BOND_BID :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.BANK_NATIONAL_BOND;
				    lApprovalActionID = Constant.ApprovalAction.BANK_NATIONAL_BOND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.BANK_NATIONAL_BOND_TRANSACTION.BOND_ACCRUAL_IN :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.BANK_NATIONAL_BOND;
				    lApprovalActionID = Constant.ApprovalAction.BANK_NATIONAL_BOND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.BANK_NATIONAL_BOND_TRANSACTION.BOND_BUYIN :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.BANK_NATIONAL_BOND;
				    lApprovalActionID = Constant.ApprovalAction.BANK_NATIONAL_BOND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.BANK_NATIONAL_BOND_TRANSACTION.BOND_MATURITY_REPAY :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.BANK_NATIONAL_BOND;
				    lApprovalActionID = Constant.ApprovalAction.BANK_NATIONAL_BOND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.BANK_NATIONAL_BOND_TRANSACTION.BOND_SELL :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.BANK_NATIONAL_BOND;
				    lApprovalActionID = Constant.ApprovalAction.BANK_NATIONAL_BOND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.EXCHANGECENTER_NATIONAL_BOND_BID.BOND_BID :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.EXCHANGECENTER_NATIONAL_BOND;
				    lApprovalActionID = Constant.ApprovalAction.EXCHANGECENTER_NATIONAL_BOND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.EXCHANGECENTER_NATIONAL_BOND_TRANSACTION.BOND_ACCRUAL_IN :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.EXCHANGECENTER_NATIONAL_BOND;
				    lApprovalActionID = Constant.ApprovalAction.EXCHANGECENTER_NATIONAL_BOND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.EXCHANGECENTER_NATIONAL_BOND_TRANSACTION.BOND_BUYIN :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.EXCHANGECENTER_NATIONAL_BOND;
				    lApprovalActionID = Constant.ApprovalAction.EXCHANGECENTER_NATIONAL_BOND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.EXCHANGECENTER_NATIONAL_BOND_TRANSACTION.BOND_MATURITY_REPAY :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.EXCHANGECENTER_NATIONAL_BOND;
				    lApprovalActionID = Constant.ApprovalAction.EXCHANGECENTER_NATIONAL_BOND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.EXCHANGECENTER_NATIONAL_BOND_TRANSACTION.BOND_SELL :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.EXCHANGECENTER_NATIONAL_BOND;
				    lApprovalActionID = Constant.ApprovalAction.EXCHANGECENTER_NATIONAL_BOND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.FINANCIAL_BOND_BID.BOND_BID :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.FINANCIAL_BOND;
				    lApprovalActionID = Constant.ApprovalAction.FINANCIAL_BOND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.FINANCIAL_BOND_TRANSACTION.BOND_ACCRUAL_IN :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.FINANCIAL_BOND;
				    lApprovalActionID = Constant.ApprovalAction.FINANCIAL_BOND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.FINANCIAL_BOND_TRANSACTION.BOND_BUYIN :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.FINANCIAL_BOND;
				    lApprovalActionID = Constant.ApprovalAction.FINANCIAL_BOND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.FINANCIAL_BOND_TRANSACTION.BOND_MATURITY_REPAY :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.FINANCIAL_BOND;
				    lApprovalActionID = Constant.ApprovalAction.FINANCIAL_BOND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.FINANCIAL_BOND_TRANSACTION.BOND_SELL :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.FINANCIAL_BOND;
				    lApprovalActionID = Constant.ApprovalAction.FINANCIAL_BOND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.POLICY_RELATED_FINANCIAL_BOND_BID.BOND_BID :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.POLICY_RELATED_FINANCIAL_BOND;
				    lApprovalActionID = Constant.ApprovalAction.POLICY_RELATED_FINANCIAL_BOND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.POLICY_RELATED_FINANCIAL_BOND_TRANSACTION.BOND_ACCRUAL_IN :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.POLICY_RELATED_FINANCIAL_BOND;
				    lApprovalActionID = Constant.ApprovalAction.POLICY_RELATED_FINANCIAL_BOND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.POLICY_RELATED_FINANCIAL_BOND_TRANSACTION.BOND_BUYIN :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.POLICY_RELATED_FINANCIAL_BOND;
				    lApprovalActionID = Constant.ApprovalAction.POLICY_RELATED_FINANCIAL_BOND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.POLICY_RELATED_FINANCIAL_BOND_TRANSACTION.BOND_MATURITY_REPAY :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.POLICY_RELATED_FINANCIAL_BOND;
				    lApprovalActionID = Constant.ApprovalAction.POLICY_RELATED_FINANCIAL_BOND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.POLICY_RELATED_FINANCIAL_BOND_TRANSACTION.BOND_SELL :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.POLICY_RELATED_FINANCIAL_BOND;
				    lApprovalActionID = Constant.ApprovalAction.POLICY_RELATED_FINANCIAL_BOND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.ENTERPRISE_BOND_BID.BOND_BID :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.ENTERPRISE_BOND;
				    lApprovalActionID = Constant.ApprovalAction.ENTERPRISE_BOND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.ENTERPRISE_BOND_BID.BOND_BID_MARGIN_REPAY :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.ENTERPRISE_BOND;
				    lApprovalActionID = Constant.ApprovalAction.ENTERPRISE_BOND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.ENTERPRISE_BOND_BID.BOND_BID_MARGIN_SUPPLY :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.ENTERPRISE_BOND;
				    lApprovalActionID = Constant.ApprovalAction.ENTERPRISE_BOND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.ENTERPRISE_BOND_BID.BOND_BID_QUOTA_CONFIRM :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.ENTERPRISE_BOND;
				    lApprovalActionID = Constant.ApprovalAction.ENTERPRISE_BOND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.ENTERPRISE_BOND_TRANSACTION.BOND_ACCRUAL_IN :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.ENTERPRISE_BOND;
				    lApprovalActionID = Constant.ApprovalAction.ENTERPRISE_BOND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.ENTERPRISE_BOND_TRANSACTION.BOND_BUYIN :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.ENTERPRISE_BOND;
				    lApprovalActionID = Constant.ApprovalAction.ENTERPRISE_BOND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.ENTERPRISE_BOND_TRANSACTION.BOND_MATURITY_REPAY :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.ENTERPRISE_BOND;
				    lApprovalActionID = Constant.ApprovalAction.ENTERPRISE_BOND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.ENTERPRISE_BOND_TRANSACTION.BOND_SELL :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.ENTERPRISE_BOND;
				    lApprovalActionID = Constant.ApprovalAction.ENTERPRISE_BOND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.TRANSFORMABLE_BOND_BID_ONLINE.BOND_BID_ONLINE :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.TRANSFORMABLE_BOND;
				    lApprovalActionID = Constant.ApprovalAction.TRANSFORMABLE_BOND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.TRANSFORMABLE_BOND_BID_ONLINE.BOND_BID_REPAY :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.TRANSFORMABLE_BOND;
				    lApprovalActionID = Constant.ApprovalAction.TRANSFORMABLE_BOND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.TRANSFORMABLE_BOND_BID_ONLINE.BOND_BID_WIN :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.TRANSFORMABLE_BOND;
				    lApprovalActionID = Constant.ApprovalAction.TRANSFORMABLE_BOND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.TRANSFORMABLE_BOND_BID.BOND_BID :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.TRANSFORMABLE_BOND;
				    lApprovalActionID = Constant.ApprovalAction.TRANSFORMABLE_BOND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.TRANSFORMABLE_BOND_BID.BOND_BID_MARGIN_REPAY :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.TRANSFORMABLE_BOND;
				    lApprovalActionID = Constant.ApprovalAction.TRANSFORMABLE_BOND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.TRANSFORMABLE_BOND_BID.BOND_BID_MARGIN_SUPPAY :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.TRANSFORMABLE_BOND;
				    lApprovalActionID = Constant.ApprovalAction.TRANSFORMABLE_BOND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.TRANSFORMABLE_BOND_BID.BOND_BID_QUOTA_CONFIRM :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.TRANSFORMABLE_BOND;
				    lApprovalActionID = Constant.ApprovalAction.TRANSFORMABLE_BOND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.TRANSFORMABLE_BOND_TRANSACTION.BOND_ACCRUAL_IN :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.TRANSFORMABLE_BOND;
				    lApprovalActionID = Constant.ApprovalAction.TRANSFORMABLE_BOND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.TRANSFORMABLE_BOND_TRANSACTION.BOND_BUYIN :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.TRANSFORMABLE_BOND;
				    lApprovalActionID = Constant.ApprovalAction.TRANSFORMABLE_BOND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.TRANSFORMABLE_BOND_TRANSACTION.BOND_MATURITY_REPAY :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.TRANSFORMABLE_BOND;
				    lApprovalActionID = Constant.ApprovalAction.TRANSFORMABLE_BOND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.TRANSFORMABLE_BOND_TRANSACTION.BOND_SELL :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.TRANSFORMABLE_BOND;
				    lApprovalActionID = Constant.ApprovalAction.TRANSFORMABLE_BOND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.DEBT_TO_EQUITY.DEBT_TO_EQUITY :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.TRANSFORMABLE_BOND;
				    lApprovalActionID = Constant.ApprovalAction.TRANSFORMABLE_BOND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.ENCLOSED_FUND_BID_ONLINE.FUND_BID_ONLINE :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.FUND;
				    lApprovalActionID = Constant.ApprovalAction.FUND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.ENCLOSED_FUND_BID_ONLINE.FUND_BID_REPAY :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.FUND;
				    lApprovalActionID = Constant.ApprovalAction.FUND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.ENCLOSED_FUND_BID_ONLINE.FUND_BID_WIN :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.FUND;
				    lApprovalActionID = Constant.ApprovalAction.FUND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.ENCLOSED_FUND_BID.FUND_BID :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.FUND;
				    lApprovalActionID = Constant.ApprovalAction.FUND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.ENCLOSED_FUND_BID.FUND_BID_MARGIN_REPAY :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.FUND;
				    lApprovalActionID = Constant.ApprovalAction.FUND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.ENCLOSED_FUND_BID.FUND_BID_MARGIN_SUPPLY :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.FUND;
				    lApprovalActionID = Constant.ApprovalAction.FUND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.ENCLOSED_FUND_BID.FUND_BID_QUOTA_CONFIRM :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.FUND;
				    lApprovalActionID = Constant.ApprovalAction.FUND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.ENCLOSED_FUND_TRANSACTION.FUND_BUYIN :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.FUND;
				    lApprovalActionID = Constant.ApprovalAction.FUND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.ENCLOSED_FUND_TRANSACTION.FUND_MATURITY_SETTLE :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.FUND;
				    lApprovalActionID = Constant.ApprovalAction.FUND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.ENCLOSED_FUND_TRANSACTION.FUND_MELON_CUTTING :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.FUND;
				    lApprovalActionID = Constant.ApprovalAction.FUND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.ENCLOSED_FUND_TRANSACTION.FUND_SELL :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.FUND;
				    lApprovalActionID = Constant.ApprovalAction.FUND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.MUTUAL_FUND_SUBSCRIBE.FUND_SUBSCRIBE :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.FUND;
				    lApprovalActionID = Constant.ApprovalAction.FUND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.MUTUAL_FUND_SUBSCRIBE.FUND_SUBSCRIBE_CONFIRM :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.FUND;
				    lApprovalActionID = Constant.ApprovalAction.FUND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.MUTUAL_FUND_BID.FUND_BID :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.FUND;
				    lApprovalActionID = Constant.ApprovalAction.FUND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.MUTUAL_FUND_BID.FUND_BID_CONFIRM :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.FUND;
				    lApprovalActionID = Constant.ApprovalAction.FUND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.MUTUAL_FUND_REDEEM.FUND_REDEEM :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.FUND;
				    lApprovalActionID = Constant.ApprovalAction.FUND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.MUTUAL_FUND_MELON_CUTTING.FUND_CASH_MELON_CUTTING :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.FUND;
				    lApprovalActionID = Constant.ApprovalAction.FUND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.MUTUAL_FUND_MELON_CUTTING.FUND_SHARE_MELON_CUTTING :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.FUND;
				    lApprovalActionID = Constant.ApprovalAction.FUND_TRANSACTION_NOTICE;
					break;
				case (int) SECConstant.BusinessType.CAPITAL_REPURCHASE.CAPITAL_REPURCHASE :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.CAPITAL_REPURCHASE;
				    lApprovalActionID = Constant.ApprovalAction.CAPITAL_REPURCHASE_APPLY;
					break;
				case (int) SECConstant.BusinessType.CAPITAL_REPURCHASE.INTEREST_PAYMENT :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.CAPITAL_REPURCHASE;
				    lApprovalActionID = Constant.ApprovalAction.CAPITAL_REPURCHASE_NOTICE;
					break;
				case (int) SECConstant.BusinessType.CAPITAL_REPURCHASE.PAYBACK_NOTIFY :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.CAPITAL_REPURCHASE;
				    lApprovalActionID = Constant.ApprovalAction.CAPITAL_REPURCHASE_NOTICE;
					break;
				case (int) SECConstant.BusinessType.CAPITAL_REPURCHASE.REPURCHASE_NOTIFY :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.CAPITAL_REPURCHASE;
				    lApprovalActionID = Constant.ApprovalAction.CAPITAL_REPURCHASE_NOTICE;
					break;
				case (int) SECConstant.BusinessType.CAPITAL_REPURCHASE.AVERAGE_NOTIFY :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.CAPITAL_REPURCHASE;
				    lApprovalActionID = Constant.ApprovalAction.CAPITAL_REPURCHASE_NOTICE;
					break;
				case (int) SECConstant.BusinessType.CAPITAL_REPURCHASE.ACCEPT_NOTIFY :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.CAPITAL_REPURCHASE;
				    lApprovalActionID = Constant.ApprovalAction.CAPITAL_REPURCHASE_NOTICE;
					break;
				case (int) SECConstant.BusinessType.CAPITAL_REPURCHASE.BREAK_NOTIFY :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.CAPITAL_REPURCHASE;
				    lApprovalActionID = Constant.ApprovalAction.CAPITAL_REPURCHASE_NOTICE;
					break;
				case (int) SECConstant.BusinessType.ENTRUST_FINANCING.CORPORACITY_DRAWBACK_NOTIFY :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.ENTRUST_FINANCING;
				    lApprovalActionID = Constant.ApprovalAction.ENTRUST_FINANCING_NOTICE;
					break;
				case (int) SECConstant.BusinessType.ENTRUST_FINANCING.CORPORACITY_PAYMENT_NOTIFY :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.ENTRUST_FINANCING;
				    lApprovalActionID = Constant.ApprovalAction.ENTRUST_FINANCING_NOTICE;
					break;
				case (int) SECConstant.BusinessType.ENTRUST_FINANCING.ENTRUST_FINANCING :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.ENTRUST_FINANCING;
				    lApprovalActionID = Constant.ApprovalAction.ENTRUST_FINANCING_NOTICE;
					break;
				case (int) SECConstant.BusinessType.ENTRUST_FINANCING.SECURITIES_PAYMENT_NOTIFY :
					lApprovalLoanTypeID = Constant.ApprovalLoanType.ENTRUST_FINANCING;
					lApprovalActionID = Constant.ApprovalAction.ENTRUST_FINANCING_NOTICE;
					break;
				case (int) SECConstant.BusinessType.ENTRUST_FINANCING.SECURITIES_DRAWBACK_NOTIFY :
					lApprovalLoanTypeID = Constant.ApprovalLoanType.ENTRUST_FINANCING;
					lApprovalActionID = Constant.ApprovalAction.ENTRUST_FINANCING_NOTICE;
					break;
				case (int) SECConstant.BusinessType.ENTRUST_FINANCING.INCOME_DRAWBACK_NOTIFY :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.ENTRUST_FINANCING;
				    lApprovalActionID = Constant.ApprovalAction.ENTRUST_FINANCING_NOTICE;
					break;
				case (int) SECConstant.BusinessType.ENTRUSTED_FINANCING.CORPORACITY_DRAWBACK_NOTIFY :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.ENTRUSTED_FINANCING;
				    lApprovalActionID = Constant.ApprovalAction.ENTRUSTED_FINANCING_NOTICE;
					break;
				case (int) SECConstant.BusinessType.ENTRUSTED_FINANCING.CORPORACITY_PAYMENT_NOTIFY :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.ENTRUSTED_FINANCING;
				    lApprovalActionID = Constant.ApprovalAction.ENTRUSTED_FINANCING_NOTICE;
					break;
				case (int) SECConstant.BusinessType.ENTRUSTED_FINANCING.ENTRUSTED_FINANCING :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.ENTRUSTED_FINANCING;
				    lApprovalActionID = Constant.ApprovalAction.ENTRUSTED_FINANCING_NOTICE;
					break;
				case (int) SECConstant.BusinessType.ENTRUSTED_FINANCING.INCOME_PAYMENT_NOTIFY :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.ENTRUSTED_FINANCING;
				    lApprovalActionID = Constant.ApprovalAction.ENTRUSTED_FINANCING_NOTICE;
					break;
				case (int) SECConstant.BusinessType.FOREIGN_CURRENCY_INVESTMENT.FOREIGN_CURRENCY_INVESTMENT :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.FOREIGN_CURRENCY_INVESTMENT;
				    lApprovalActionID = Constant.ApprovalAction.FOREIGN_CURRENCY_INVESTMENT_NOTICE;
					break;
				case (int) SECConstant.BusinessType.FOREIGN_CURRENCY_INVESTMENT.INCOME_DRAWBACK_NOTIFY :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.FOREIGN_CURRENCY_INVESTMENT;
				    lApprovalActionID = Constant.ApprovalAction.FOREIGN_CURRENCY_INVESTMENT_NOTICE;
					break;
				case (int) SECConstant.BusinessType.FOREIGN_CURRENCY_INVESTMENT.INVESTMENT_DRAWBACK_NOTIFY :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.FOREIGN_CURRENCY_INVESTMENT;
				    lApprovalActionID = Constant.ApprovalAction.FOREIGN_CURRENCY_INVESTMENT_NOTICE;
					break;
				case (int) SECConstant.BusinessType.FOREIGN_CURRENCY_INVESTMENT.INVESTMENT_PAYMENT_NOTIFY :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.FOREIGN_CURRENCY_INVESTMENT;
				    lApprovalActionID = Constant.ApprovalAction.FOREIGN_CURRENCY_INVESTMENT_NOTICE;
					break;
				case (int) SECConstant.BusinessType.BOND_UNDERWRITING.BOND_DRAWBACK_NOTIFY :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.BOND_UNDERWRITING;
				    lApprovalActionID = Constant.ApprovalAction.BOND_UNDERWRITING_NOTICE;
					break;
				case (int) SECConstant.BusinessType.BOND_UNDERWRITING.UNDERWRITING_PAYMENT_NOTIFY :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.BOND_UNDERWRITING;
				    lApprovalActionID = Constant.ApprovalAction.BOND_UNDERWRITING_NOTICE;
					break;
				case (int) SECConstant.BusinessType.BOND_UNDERWRITING.INCOME_DRAWBACK_NOTIFY :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.BOND_UNDERWRITING;
				    lApprovalActionID = Constant.ApprovalAction.BOND_UNDERWRITING_NOTICE;
					break;
				case (int) SECConstant.BusinessType.BOND_UNDERWRITING.UNDERWRITING_DRAWBACK_NOTIFY :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.BOND_UNDERWRITING;
				    lApprovalActionID = Constant.ApprovalAction.BOND_UNDERWRITING_NOTICE;
					break;
					
				//资金划拨
				case (int) SECConstant.BusinessType.CAPITAL_TRANSFER.CAPITAL_IN :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.CAPITAL_TRANSFER;
				    lApprovalActionID = Constant.ApprovalAction.CAPITAL_TRANSFER_NOTICE;
					break;
				case (int) SECConstant.BusinessType.CAPITAL_TRANSFER.CAPITAL_OUT :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.CAPITAL_TRANSFER;
				    lApprovalActionID = Constant.ApprovalAction.CAPITAL_TRANSFER_NOTICE;
					break;
			}
		}
		//合同
		else if (lActionID == 3)
		{
		    switch ((int) lTransactionTypeID)
			{
				case (int) SECConstant.BusinessType.CAPITAL_REPURCHASE.CAPITAL_REPURCHASE :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.CAPITAL_REPURCHASE;
				    lApprovalActionID = Constant.ApprovalAction.CAPITAL_REPURCHASE_CONTRACT;
					break;
				case (int) SECConstant.BusinessType.CAPITAL_REPURCHASE.INTEREST_PAYMENT :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.CAPITAL_REPURCHASE;
				    lApprovalActionID = Constant.ApprovalAction.CAPITAL_REPURCHASE_CONTRACT;
					break;
				case (int) SECConstant.BusinessType.CAPITAL_REPURCHASE.PAYBACK_NOTIFY :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.CAPITAL_REPURCHASE;
				    lApprovalActionID = Constant.ApprovalAction.CAPITAL_REPURCHASE_CONTRACT;
					break;
				case (int) SECConstant.BusinessType.CAPITAL_REPURCHASE.REPURCHASE_NOTIFY :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.CAPITAL_REPURCHASE;
				    lApprovalActionID = Constant.ApprovalAction.CAPITAL_REPURCHASE_CONTRACT;
					break;
				case (int) SECConstant.BusinessType.CAPITAL_REPURCHASE.AVERAGE_NOTIFY :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.CAPITAL_REPURCHASE;
				    lApprovalActionID = Constant.ApprovalAction.CAPITAL_REPURCHASE_CONTRACT;
					break;
				case (int) SECConstant.BusinessType.CAPITAL_REPURCHASE.ACCEPT_NOTIFY :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.CAPITAL_REPURCHASE;
				    lApprovalActionID = Constant.ApprovalAction.CAPITAL_REPURCHASE_CONTRACT;
					break;
				case (int) SECConstant.BusinessType.CAPITAL_REPURCHASE.BREAK_NOTIFY :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.CAPITAL_REPURCHASE;
				    lApprovalActionID = Constant.ApprovalAction.CAPITAL_REPURCHASE_CONTRACT;
					break;
				case (int) SECConstant.BusinessType.ENTRUST_FINANCING.CORPORACITY_DRAWBACK_NOTIFY :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.ENTRUST_FINANCING;
				    lApprovalActionID = Constant.ApprovalAction.ENTRUST_FINANCING_CONTRACT;
					break;
				case (int) SECConstant.BusinessType.ENTRUST_FINANCING.CORPORACITY_PAYMENT_NOTIFY :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.ENTRUST_FINANCING;
				    lApprovalActionID = Constant.ApprovalAction.ENTRUST_FINANCING_CONTRACT;
					break;
				case (int) SECConstant.BusinessType.ENTRUST_FINANCING.ENTRUST_FINANCING :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.ENTRUST_FINANCING;
				    lApprovalActionID = Constant.ApprovalAction.ENTRUST_FINANCING_CONTRACT;
					break;
				case (int) SECConstant.BusinessType.ENTRUST_FINANCING.INCOME_DRAWBACK_NOTIFY :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.ENTRUST_FINANCING;
				    lApprovalActionID = Constant.ApprovalAction.ENTRUST_FINANCING_CONTRACT;
					break;
				case (int) SECConstant.BusinessType.ENTRUSTED_FINANCING.CORPORACITY_DRAWBACK_NOTIFY :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.ENTRUSTED_FINANCING;
				    lApprovalActionID = Constant.ApprovalAction.ENTRUSTED_FINANCING_CONTRACT;
					break;
				case (int) SECConstant.BusinessType.ENTRUSTED_FINANCING.CORPORACITY_PAYMENT_NOTIFY :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.ENTRUSTED_FINANCING;
				    lApprovalActionID = Constant.ApprovalAction.ENTRUSTED_FINANCING_CONTRACT;
					break;
				case (int) SECConstant.BusinessType.ENTRUSTED_FINANCING.ENTRUSTED_FINANCING :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.ENTRUSTED_FINANCING;
				    lApprovalActionID = Constant.ApprovalAction.ENTRUSTED_FINANCING_CONTRACT;
					break;
				case (int) SECConstant.BusinessType.ENTRUSTED_FINANCING.INCOME_PAYMENT_NOTIFY :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.ENTRUSTED_FINANCING;
				    lApprovalActionID = Constant.ApprovalAction.ENTRUSTED_FINANCING_CONTRACT;
					break;
				case (int) SECConstant.BusinessType.FOREIGN_CURRENCY_INVESTMENT.FOREIGN_CURRENCY_INVESTMENT :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.FOREIGN_CURRENCY_INVESTMENT;
				    lApprovalActionID = Constant.ApprovalAction.FOREIGN_CURRENCY_INVESTMENT_CONTRACT;
					break;
				case (int) SECConstant.BusinessType.FOREIGN_CURRENCY_INVESTMENT.INCOME_DRAWBACK_NOTIFY :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.FOREIGN_CURRENCY_INVESTMENT;
				    lApprovalActionID = Constant.ApprovalAction.FOREIGN_CURRENCY_INVESTMENT_CONTRACT;
					break;
				case (int) SECConstant.BusinessType.FOREIGN_CURRENCY_INVESTMENT.INVESTMENT_DRAWBACK_NOTIFY :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.FOREIGN_CURRENCY_INVESTMENT;
				    lApprovalActionID = Constant.ApprovalAction.FOREIGN_CURRENCY_INVESTMENT_CONTRACT;
					break;
				case (int) SECConstant.BusinessType.FOREIGN_CURRENCY_INVESTMENT.INVESTMENT_PAYMENT_NOTIFY :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.FOREIGN_CURRENCY_INVESTMENT;
				    lApprovalActionID = Constant.ApprovalAction.FOREIGN_CURRENCY_INVESTMENT_CONTRACT;
					break;
				case (int) SECConstant.BusinessType.BOND_UNDERWRITING.BOND_UNDERWRITING :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.BOND_UNDERWRITING;
				    lApprovalActionID = Constant.ApprovalAction.BOND_UNDERWRITING_CONTRACT;
					break;
				case (int) SECConstant.BusinessType.BOND_UNDERWRITING.INCOME_DRAWBACK_NOTIFY :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.BOND_UNDERWRITING;
				    lApprovalActionID = Constant.ApprovalAction.BOND_UNDERWRITING_CONTRACT;
					break;
				case (int) SECConstant.BusinessType.BOND_UNDERWRITING.UNDERWRITING_DRAWBACK_NOTIFY :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.BOND_UNDERWRITING;
				    lApprovalActionID = Constant.ApprovalAction.BOND_UNDERWRITING_CONTRACT;
					break;
				case (int) SECConstant.BusinessType.BOND_UNDERWRITING.UNDERWRITING_PAYMENT_NOTIFY :
				    lApprovalLoanTypeID = Constant.ApprovalLoanType.BOND_UNDERWRITING;
				    lApprovalActionID = Constant.ApprovalAction.BOND_UNDERWRITING_CONTRACT;
					break;
			}
		}
	
		long[] rtn={lApprovalLoanTypeID,lApprovalActionID};
		return rtn;
	}
	/**
	 * 通过ID获得审批流名称（子类）
	 * @param lID
	 * @return
	 */
	public static String getApprovalSettingNameByID(long lID){
		return getNameByID("sName","loan_approvalSetting","id",String.valueOf(lID),null);
	}
}