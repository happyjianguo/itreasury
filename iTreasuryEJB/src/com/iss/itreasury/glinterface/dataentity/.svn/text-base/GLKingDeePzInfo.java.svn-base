/*
 * Created on 2005-11-21
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.glinterface.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * 金蝶凭证汇总表信息
 * @author liuqing
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class GLKingDeePzInfo implements Serializable
{
	private String FVoucherID = "";   	//凭证内码
	private Timestamp FDate = null;   	//凭证日期
	private String FYear = "";			//会计年度
	private String FPeriod = "";		//会计期间
	private String FGroupID = "";		//凭证字类
	private String FNumber = "";        //凭证编号 
	private String FExplanation = "";  	//备注
	private String FReference = "";		//参考信息-保存导入批次号
	private String FAttachments = "";  	//附件张数
	private String FEntryCount = "";	//分录条数
	private String FDebitTotal = "";	//借方合计
	private String FCreditTotal = "";	//贷方合计
	private String FPreparerID = "";	//制单人	
	private String FSerialNum = "";    	//引入后凭证编号
	private Timestamp FTransDate = null;//业务日期 
	private String Flag = "";          	//引入标志
	
	private ArrayList list = new ArrayList();  //分录数组
      
	/**
	 * @return Returns the FVoucherID.
	 */
	public String getFVoucherID() {
		return FVoucherID;
	}
	/**
	 * @param fvoucherID The FVoucherID to set.
	 */
	public void setFVoucherID(String fvoucherID) {
		FVoucherID = fvoucherID;
	}
	/**
	 * @return Returns the FDate.
	 */
	public Timestamp getFDate() {
		return FDate;
	}
	/**
	 * @param fdate The FDate to set.
	 */
	public void setFDate(Timestamp fdate) {
		FDate = fdate;
	}
	/**
	 * @return Returns the FYear.
	 */
	public String getFYear() {
		return FYear;
	}
	/**
	 * @param fyear The FYear to set.
	 */
	public void setFYear(String fyear) {
		FYear = fyear;
	}
	/**
	 * @return Returns the FPeriod.
	 */
	public String getFPeriod() {
		return FPeriod;
	}
	/**
	 * @param fperiod The FPeriod to set.
	 */
	public void setFPeriod(String fperiod) {
		FPeriod = fperiod;
	}
	/**
	 * @return Returns the FGroupID.
	 */
	public String getFGroupID() {
		return FGroupID;
	}
	/**
	 * @param fgroupID The FGroupID to set.
	 */
	public void setFGroupID(String fgroupID) {
		FGroupID = fgroupID;
	}
	/**
	 * @return Returns the FNumber.
	 */
	public String getFNumber() {
		return FNumber;
	}
	/**
	 * @param fnumber The FNumber to set.
	 */
	public void setFNumber(String fnumber) {
		FNumber = fnumber;
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
	 * @return Returns the FAttachments.
	 */
	public String getFAttachments() {
		return FAttachments;
	}
	/**
	 * @param fattachments The FAttachments to set.
	 */
	public void setFAttachments(String fattachments) {
		FAttachments = fattachments;
	}
	/**
	 * @return Returns the FEntryCount.
	 */
	public String getFEntryCount() {
		return FEntryCount;
	}
	/**
	 * @param fentrycount The FEntryCount to set.
	 */
	public void setFEntryCount(String fentrycount) {
		FEntryCount = fentrycount;
	}
	/**
	 * @return Returns the FDebitTotal.
	 */
	public String getFDebitTotal() {
		return FDebitTotal;
	}
	/**
	 * @param fdebittotal The FDebitTotal to set.
	 */
	public void setFDebitTotal(String fdebittotal) {
		FDebitTotal = fdebittotal;
	}
	/**
	 * @return Returns the FCreditTotal.
	 */
	public String getFCreditTotal() {
		return FCreditTotal;
	}
	/**
	 * @param fcredittotal The FCreditTotal to set.
	 */
	public void setFCreditTotal(String fcredittotal) {
		FCreditTotal = fcredittotal;
	}
	/**
	 * @return Returns the FPreparerID.
	 */
	public String getFPreparerID() {
		return FPreparerID;
	}
	/**
	 * @param fpreparerid The FPreparerID to set.
	 */
	public void setFPreparerID(String fpreparerid) {
		FPreparerID = fpreparerid;
	}
	/**
	 * @return Returns the FSerialNum.
	 */
	public String getFSerialNum() {
		return FSerialNum;
	}
	/**
	 * @param fserialnum The FSerialNum to set.
	 */
	public void setFSerialNum(String fserialnum) {
		FSerialNum = fserialnum;
	}
	/**
	 * @return Returns the FTransDate.
	 */
	public Timestamp getFTransDate() {
		return FTransDate;
	}
	/**
	 * @param ftransdate The FTransDate to set.
	 */
	public void setFTransDate(Timestamp ftransdate) {
		FTransDate = ftransdate;
	}
	
	
	/**
	 * @return Returns the Flag.
	 */
	public String getFlag() {
		return Flag;
	}
	/**
	 * @param Flag The Flag to set.
	 */
	public void setFlag(String flag) {
		Flag = flag;
	}
	
	
	/**
	 * @return Returns the list.
	 */
	public ArrayList getList() {
		return list;
	}
	/**
	 * @param list The list to set.
	 */
	public void setList(ArrayList list) {
		this.list = list;
	}
	
	
	/**
	 * @return
	 */
	public GLKingDeePzflInfo getEntryInfo(long i)
	{
		if (this.list.size() > i && i >= 0)
		{
			return (GLKingDeePzflInfo) this.list.get((int) i);
		}
		else
		{
			return null;
		}
	}
	/**
	 * @param l
	 */
	public void addEntryInfo(GLKingDeePzflInfo entry)
	{
		this.list.add(entry);
	}
	
    /**
     * @return Returns the fReference.
     */
    public String getFReference() {
        return FReference;
    }
    /**
     * @param reference The fReference to set.
     */
    public void setFReference(String reference) {
        FReference = reference;
    }
}