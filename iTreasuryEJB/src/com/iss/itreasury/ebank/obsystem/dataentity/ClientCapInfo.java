/*
 * Created on 2003-9-15
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.ebank.obsystem.dataentity;

/**
 * @author hyzeng
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
import java.sql.*;

public class ClientCapInfo implements java.io.Serializable  
{

	/** Creates new ClientCapInfo */
	public ClientCapInfo () 
	{
	}
	private long lID = -1;//收款方资料ID
	private String sPayeeName="";//收款方名称
	private long lClientID = -1;//付款方客户ID 
	private long lCurrencyID = -1;//币种
	private long lIsCPFAcct = -1;//是否是中油财务内部账户，参见Notes定义
	private String sPayeeAccoutNO="";//收款方账号
	private String sPayeeBankNO = "" ;//收款方银行编号
	private String sPayeeBankName = "";//汇入行名称
	private String sPayeeProv = "";//收款方省
	private String sCity = "";//收款方城市
	private long lInputUserID = -1;//录入认
	private Timestamp tsInputDate = null;//录入时间
	private long lofficeid = -1; //办事处id
	//关联信息
	private String sInputUserName = "";//录入认姓名
	private String bankName = "";//汇入行全称
	
	//财企接口新增
	private String sPayeeBankExchangeNO = "";  //汇入行联行号
	private String sPayeeBankCNAPSNO = "";  //汇入行CNAPS号
	private String sPayeeBankOrgNO = "";  //汇入行机构号
	
	
	public String getSPayeeBankExchangeNO() {
		return sPayeeBankExchangeNO;
	}
	public void setSPayeeBankExchangeNO(String sPayeeBankExchangeNO) {
		this.sPayeeBankExchangeNO = sPayeeBankExchangeNO;
	}
	public String getSPayeeBankCNAPSNO() {
		return sPayeeBankCNAPSNO;
	}
	public void setSPayeeBankCNAPSNO(String sPayeeBankCNAPSNO) {
		this.sPayeeBankCNAPSNO = sPayeeBankCNAPSNO;
	}
	public String getSPayeeBankOrgNO() {
		return sPayeeBankOrgNO;
	}
	public void setSPayeeBankOrgNO(String sPayeeBankOrgNO) {
		this.sPayeeBankOrgNO = sPayeeBankOrgNO;
	}
	
	
	
	/**
	 * @return
	 */
	public long getClientID() {
		return lClientID;
	}

	/**
	 * @return
	 */
	public long getCurrencyID() {
		return lCurrencyID;
	}

	/**
	 * @return
	 */
	public long getID() {
		return lID;
	}

	/**
	 * @return
	 */
	public long getInputUserID() {
		return lInputUserID;
	}

	/**
	 * @return
	 */
	public long getIsCPFAcct() {
		return lIsCPFAcct;
	}

	/**
	 * @return
	 */
	public String getCity() {
		return sCity;
	}

	/**
	 * @return
	 */
	public String getInputUserName() {
		return sInputUserName;
	}

	/**
	 * @return
	 */
	public String getPayeeAccoutNO() {
		return sPayeeAccoutNO;
	}

	/**
	 * @return
	 */
	public String getPayeeBankName() {
		return sPayeeBankName;
	}

	/**
	 * @return
	 */
	public String getPayeeBankNO() {
		return sPayeeBankNO;
	}

	/**
	 * @return
	 */
	public String getPayeeName() {
		return sPayeeName;
	}

	/**
	 * @return
	 */
	public String getPayeeProv() {
		return sPayeeProv;
	}

	/**
	 * @return
	 */
	public Timestamp getInputDate() {
		return tsInputDate;
	}

	/**
	 * @param l
	 */
	public void setClientID(long l) {
		lClientID = l;
	}

	/**
	 * @param l
	 */
	public void setCurrencyID(long l) {
		lCurrencyID = l;
	}

	/**
	 * @param l
	 */
	public void setID(long l) {
		lID = l;
	}

	/**
	 * @param l
	 */
	public void setInputUserID(long l) {
		lInputUserID = l;
	}

	/**
	 * @param l
	 */
	public void setIsCPFAcct(long l) {
		lIsCPFAcct = l;
	}

	/**
	 * @param string
	 */
	public void setCity(String string) {
		sCity = string;
	}

	/**
	 * @param string
	 */
	public void setInputUserName(String string) {
		sInputUserName = string;
	}

	/**
	 * @param string
	 */
	public void setPayeeAccoutNO(String string) {
		sPayeeAccoutNO = string;
	}

	/**
	 * @param string
	 */
	public void setPayeeBankName(String string) {
		sPayeeBankName = string;
	}

	/**
	 * @param string
	 */
	public void setPayeeBankNO(String string) {
		sPayeeBankNO = string;
	}

	/**
	 * @param string
	 */
	public void setPayeeName(String string) {
		sPayeeName = string;
	}

	/**
	 * @param string
	 */
	public void setPayeeProv(String string) {
		sPayeeProv = string;
	}

	/**
	 * @param timestamp
	 */
	public void setInputDate(Timestamp timestamp) {
		tsInputDate = timestamp;
	}

	public long getLofficeid() {
		return lofficeid;
	}

	public void setLofficeid(long lofficeid) {
		this.lofficeid = lofficeid;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

}
