package com.iss.itreasury.ebank.obsystem.dataentity;

/**
 * <p>Title: CNOOCS Project Phase II </p>
 * <p>Description: 票据信息</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: iSoftstone</p>
 * @Author: 曾海燕
 * @version 1.0
 * @Date: 2003-08-13
 */

import java.sql.Timestamp;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.settlement.util.SETTConstant;

public class VoucherInfo implements java.io.Serializable
{
    private String  sVoucherNo = "";//票据号
    private long lStatus = -1; // 票据状态
    private Timestamp tsDate = null;//生成日期
	/**
	 * @return
	 */
	
	public String getStatus() {
		return SETTConstant.BankBillStatus.getName(lStatus);
	}

	/**
	 * @return
	 */
	public String getVoucherNo() {
		return DataFormat.formatInt(Long.parseLong(sVoucherNo),6);
	}

	/**
	 * @return
	 */
	public String getDate() {
		return DataFormat.getDateString(tsDate) ;
	}

	/**
	 * @param l
	 */
	public void setStatus(long l) {
		lStatus = l;
	}

	/**
	 * @param l
	 */
	public void setVoucherNo(String string) {
		sVoucherNo = string;
	}

	/**
	 * @param timestamp
	 */
	public void setDate(Timestamp timestamp) {
		tsDate = timestamp;
	}

}