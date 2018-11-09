package com.iss.itreasury.glinterface.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import com.iss.itreasury.util.IException;

/**
 * 导出会计凭证日志
 * @author xiangzhou 20110907
 *
 */
public class GLVoucherLogInfo implements Serializable{
	
	public long ID = -1;
	public long OFFICEID = -1;
	public long CURRENCYID = -1;
	public String log = null;
	public Timestamp inputDate = null;
	public long NStatusID = -1;
	public long inputUserID = -1;
	
	public String OFFICE = null;
	public String CURRENCY = null;
	public String inputUserName = null;
	public String StartDate = null;
	public String EndDate = null;
	
	private static Map glLog = new HashMap();
	
	/**
	 * 组装日志基本信息
	 * @param lOfficeId
	 * @param lCurrencyId
	 * @param lUserId
	 * @throws IException
	 */
	public static void bulidGLLog(long lOfficeId, long lCurrencyId, long lUserId) throws IException{

        try {
        	String strKey = String.valueOf(lOfficeId) + "--" +  String.valueOf(lCurrencyId);
        	GLVoucherLogInfo log = new GLVoucherLogInfo(lOfficeId,lCurrencyId,lUserId);
    		glLog.put(strKey, log);
        }
        catch(Exception e) {
            throw new IException("Gen_E001");
        }
	}
	/**
	 * 获取日志基本信息
	 * @param lOfficeId
	 * @param lCurrencyId
	 * @return
	 * @throws IException
	 */
	public static GLVoucherLogInfo getGLLog(long lOfficeId, long lCurrencyId) throws IException{
		try {
			String strKey = String.valueOf(lOfficeId) + "--" +  String.valueOf(lCurrencyId);
			if(glLog.get(strKey) == null){
				throw new IException("Gen_E001");
			}
			GLVoucherLogInfo log = (GLVoucherLogInfo)glLog.get(strKey);
			return log;
        }
        catch(Exception e) {
            throw new IException("Gen_E001");
        }
	}
	
	public GLVoucherLogInfo(long lOfficeId, long lCurrencyId, long lUserId){
		this.OFFICEID = lOfficeId;
		this.CURRENCYID = lCurrencyId;
		this.inputUserID = lUserId;
	}
	
	public GLVoucherLogInfo(){
		
	}
	
	public long getID() {
		return ID;
	}
	public void setID(long id) {
		ID = id;
	}
	public long getOFFICEID() {
		return OFFICEID;
	}
	public void setOFFICEID(long officeid) {
		OFFICEID = officeid;
	}
	public long getCURRENCYID() {
		return CURRENCYID;
	}
	public void setCURRENCYID(long currencyid) {
		CURRENCYID = currencyid;
	}
	public String getLog() {
		return log;
	}
	public void setLog(String log) {
		this.log = log;
	}
	public Timestamp getInputDate() {
		return inputDate;
	}
	public void setInputDate(Timestamp inputDate) {
		this.inputDate = inputDate;
	}
	public long getNStatusID() {
		return NStatusID;
	}
	public void setNStatusID(long statusID) {
		NStatusID = statusID;
	}
	public long getInputUserID() {
		return inputUserID;
	}
	public void setInputUserID(long inputUserID) {
		this.inputUserID = inputUserID;
	}
	public String getOFFICE() {
		return OFFICE;
	}
	public void setOFFICE(String office) {
		OFFICE = office;
	}
	public String getCURRENCY() {
		return CURRENCY;
	}
	public void setCURRENCY(String currency) {
		CURRENCY = currency;
	}
	public String getInputUserName() {
		return inputUserName;
	}
	public void setInputUserName(String inputUserName) {
		this.inputUserName = inputUserName;
	}
	public String getStartDate() {
		return StartDate;
	}
	public void setStartDate(String startDate) {
		StartDate = startDate;
	}
	public String getEndDate() {
		return EndDate;
	}
	public void setEndDate(String endDate) {
		EndDate = endDate;
	}

}
