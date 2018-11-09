/*
 * Created on 2005-11-21
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.glinterface.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

import com.iss.sysframe.base.dataentity.BaseDataEntity;

/**
 * 金蝶凭证分录表信息
 * 
 * @author shuangniu
 * 
 *         To change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates
 *         
 * 		新奥财务 金蝶凭证中间表  CT_CUS_FMBIL 字段信息如下：
 * 		|---------------------------------------------------|
 *      | 字段名				   	| 字段描述					|
 * 		|-----------------------|---------------------------|
 * 		| FNumber				| 交易编号					|
 * 		| FBookDate				| 执行日期					|
 * 		| FBizDate				| 起息日期					|
 * 		| FDInputDate 			| 导入日期					|
 * 		| FCompanyNumber		| 单位编码					|
 * 		| Fcurrreny				| 币种       				|
 * 		| FCreator				| 制单人    					|
 * 		| FAuditor				| 审核人   					|
 * 		| FAbstract				| 摘要				     	|
 * 		| FAttachments			| 附件数   					|
 * 		| FEntryDC				| 借贷方向					|
 * 		| FAmount				| 金额      					|
 * 		| FAccountCode			| 科目编码					|
 * 		| FState				| 状态      					|
 * 		| FCustomerCode			| 客户编号     				|
 * 		| FCustomerName			| 客户名称      				|
 * 		|-----------------------|---------------------------|
 */
public class GLKingDeePzflInfo extends BaseDataEntity implements Serializable
{
	private String FVoucherID = "";         //凭证内码
	private String FEntryID = "";          	//分录编号
	private String FExplanation = "";       //摘要
	private String FAccountID = "";         //科目编号
	private long FCurrencyID = 1;     		//币种 
	private double FExchangeRate = 1;       //汇率
	private String FDC = "";	         	//记账方向
	private double FAmount = 0;             //金额  
	private String FAccountID2 = "";   	   	//对方科目
	
	
	/**
	 * add by shuangniu 2011-01-19 新奥财务增加
	 */
	private String FNumber = ""; // 交易编号
	private String FCurrenyCode = ""; //币种 RMB
	private Timestamp FBookDate = null; // 执行日期
	private Timestamp FBizDate = null; // 起息日期
	private Timestamp FDInputDate = null; // 导入日期
	private String FCompanyNumber = ""; // 单位编码
	private String FCreator = ""; // 制单人
	private String FAuditor = ""; // 审核人
	private int FAttachments = 1; // 附件数
	private String FState = ""; // 状态
	private String FCustomerCode="";      //客户编码 client_clientinfo.code("-"后字符串) = gl_assistant.assitantvalue辅助核算值  客户编码
	private String FCustomerName="";      //客户名称
	
	private String Execute=null; //查询日期
	
	private long OfficeID = -1; 
	
	private long id = -1;					//ID
	private String StartClientCode = "";	//客户编号由 
	private String EndClientCode = ""; 		//客户编号至
	private long clientID = -1;				//客户ID
	private String assitantValue = "";		//客商辅助核算 
	private String kingDeeClientName = "";	//金蝶客户名称
	
	/**
	 * @return Returns the FVoucherID.
	 */
	public String getFVoucherID() {
		return FVoucherID;
	}
	/**
	 * @param fvoucherid The FVoucherID to set.
	 */
	public void setFVoucherID(String fvoucherid) {
		FVoucherID = fvoucherid;
	}
	/**
	 * @return Returns the FEntryID.
	 */
	public String getFEntryID() {
		return FEntryID;
	}
	/**
	 * @param fentryid The FEntryID to set.
	 */
	public void setFEntryID(String fentryid) {
		FEntryID = fentryid;
	}
	/**
	 * @return Returns the FExplanation.
	 */
	public String getFExplanation() {
		return FExplanation;
	}
	/**
	 * @param fexplanation The FExplanation to set.
	 */
	public void setFExplanation(String fexplanation) {
		FExplanation = fexplanation;
	}
	/**
	 * @return Returns the FAccountID.
	 */
	public String getFAccountID() {
		return FAccountID;
	}
	/**
	 * @param faccountid The FAccountID to set.
	 */
	public void setFAccountID(String faccountid) {
		FAccountID = faccountid;
	}
	/**
	 * @return Returns the FCurrencyID.
	 */
	public long getFCurrencyID() {
		return FCurrencyID;
	}
	/**
	 * @param fcurrencyid The FCurrencyID to set.
	 */
	public void setFCurrencyID(long fcurrencyid) {
		FCurrencyID = fcurrencyid;
	}
	/**
	 * @return Returns the FExchangeRate.
	 */
	public double getFExchangeRate() {
		return FExchangeRate;
	}
	/**
	 * @param fexchangerate The FExchangeRate to set.
	 */
	public void setFExchangeRate(double fexchangerate) {
		FExchangeRate = fexchangerate;
	}
	/**
	 * @return Returns the FDC.
	 */
	public String getFDC() {
		return FDC;
	}
	/**
	 * @param fdc The FDC to set.
	 */
	public void setFDC(String fdc) {
		FDC = fdc;
	}
	/**
	 * @return Returns the FAmount.
	 */
	public double getFAmount() {
		return FAmount;
	}
	/**
	 * @param famount The FAmount to set.
	 */
	public void setFAmount(double famount) {
		FAmount = famount;
	}
	/**
	 * @return Returns the FAccountID2.
	 */
	public String getFAccountID2() {
		return FAccountID2;
	}
	/**
	 * @param faccountid2 The FAccountID2 to set.
	 */
	public void setFAccountID2(String faccountid2) {
		FAccountID2 = faccountid2;
	}
	
	public String getFNumber() {
		return FNumber;
	}

	public void setFNumber(String fNumber) {
		FNumber = fNumber;
	}

	public Timestamp getFBookDate() {
		return FBookDate;
	}

	public void setFBookDate(Timestamp fBookDate) {
		FBookDate = fBookDate;
	}

	public Timestamp getFBizDate() {
		return FBizDate;
	}

	public void setFBizDate(Timestamp fBizDate) {
		FBizDate = fBizDate;
	}

	public Timestamp getFDInputDate() {
		return FDInputDate;
	}

	public void setFDInputDate(Timestamp fDInputDate) {
		FDInputDate = fDInputDate;
	}

	public String getFCompanyNumber() {
		return FCompanyNumber;
	}

	public void setFCompanyNumber(String fCompanyNumber) {
		FCompanyNumber = fCompanyNumber;
	}

	public String getFCreator() {
		return FCreator;
	}

	public void setFCreator(String fCreator) {
		FCreator = fCreator;
	}

	public String getFAuditor() {
		return FAuditor;
	}

	public void setFAuditor(String fAuditor) {
		FAuditor = fAuditor;
	}

	public int getFAttachments() {
		return FAttachments;
	}

	public void setFAttachments(int fAttachments) {
		FAttachments = fAttachments;
	}

	public String getFState() {
		return FState;
	}

	public void setFState(String fState) {
		FState = fState;
	}

	public String getFCurrenyCode() {
		return FCurrenyCode;
	}

	public void setFCurrenyCode(String fCurrrenyCode) {
		FCurrenyCode = fCurrrenyCode;
	}

	public String getFCustomerCode() {
		return FCustomerCode;
	}

	public void setFCustomerCode(String fCustomerCode) {
		FCustomerCode = fCustomerCode;
	}

	public String getFCustomerName() {
		return FCustomerName;
	}

	public void setFCustomerName(String fCustomerName) {
		FCustomerName = fCustomerName;
	}
	public String getExecute() {
		return Execute;
	}
	public void setExecute(String execute) {
		Execute = execute;
	}
	public long getOfficeID() {
		return OfficeID;
	}
	public void setOfficeID(long officeID) {
		OfficeID = officeID;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getStartClientCode() {
		return StartClientCode;
	}
	public void setStartClientCode(String startClientCode) {
		StartClientCode = startClientCode;
	}
	public String getEndClientCode() {
		return EndClientCode;
	}
	public void setEndClientCode(String endClientCode) {
		EndClientCode = endClientCode;
	}
	public long getClientID() {
		return clientID;
	}
	public void setClientID(long clientID) {
		this.clientID = clientID;
	}
	public String getAssitantValue() {
		return assitantValue;
	}
	public void setAssitantValue(String assitantValue) {
		this.assitantValue = assitantValue;
	}
	public String getKingDeeClientName() {
		return kingDeeClientName;
	}
	public void setKingDeeClientName(String kingDeeClientName) {
		this.kingDeeClientName = kingDeeClientName;
	}
	
}