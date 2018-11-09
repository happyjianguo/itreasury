////////////////////////////////////////////////////////////////////////////
//
// COPYRIGHT (C) 2003 ISS CORPORATION
//
// ALL RIGHTS RESERVED BY ISS CORPORATION, THIS PROGRAM
// MUST BE USED SOLELY FOR THE PURPOSE FOR WHICH IT WAS
// FURNISHED BY ISS CORPORATION, NO PART OF THIS PROGRAM
// MAY BE REPRODUCED OR DISCLOSED TO OTHERS, IN ANY FORM
// WITHOUT THE PRIOR WRITTEN PERMISSION OF ISS CORPORATION.
// USE OF COPYRIGHT NOTICE DOES NOT EVIDENCE PUBLICATION
// OF THE PROGRAM
//
//            ISS CONFIDENTIAL AND PROPRIETARY
//
////////////////////////////////////////////////////////////////////////////

/**
 * BankBillDailyReportInfo.java
 * 银行票据日报表实体类：
 * @author  Ryan
 * @version 1.0
*/

package com.iss.itreasury.settlement.bankbill.dataentity;

import java.sql.*;
import java.io.Serializable;

public class BankBillDailyReportInfo implements Serializable
{
	public DailyReportItem[] reportItems= null;			//日报条目
	private Timestamp tsQueryDate		= null;			//查询日期

	/**
	 * constructor
	 * @param iItemNum		the num of the items needed
	 */
	public BankBillDailyReportInfo(int iItemNum){
		reportItems=new DailyReportItem[iItemNum+1];	//日报项目，最后一条为合计
		for (int n=0;n<iItemNum;n++){
			reportItems[n]=new DailyReportItem(""+(n+1));	//添加序号
		}
		reportItems[iItemNum]=new DailyReportItem("合计");			//合计
	}
	/**
	 * cacalute every item's balance
	 *
	 */
	public void caculateBalance(){
		if (reportItems!=null){
			for (int n=0;n<reportItems.length;n++){
				reportItems[n].setLBalance(reportItems[n].getLOriginalNum()
										  +reportItems[n].getLTodayInCome()
										  -reportItems[n].getLTodayOutCome());
			}
		}
	}
	public void caculateSum(){
		if (reportItems!=null){
			for (int n=0;n<(reportItems.length-1);n++){
				reportItems[reportItems.length-1].setLOriginalNum(reportItems[reportItems.length-1].getLOriginalNum()+reportItems[n].getLOriginalNum());
				reportItems[reportItems.length-1].setLTodayInCome(reportItems[reportItems.length-1].getLTodayInCome()+reportItems[n].getLTodayInCome());
				reportItems[reportItems.length-1].setLTodayOutCome(reportItems[reportItems.length-1].getLTodayOutCome()+reportItems[n].getLTodayOutCome());
				reportItems[reportItems.length-1].setLBalance(reportItems[reportItems.length-1].getLBalance()+reportItems[n].getLBalance());
			}
		}
	}
	/**
	 * @return Returns the tsQueryDate.
	 */
	public Timestamp getTsQueryDate() {
		return tsQueryDate;
	}

	/**
	 * @param tsQueryDate The tsQueryDate to set.
	 */
	public void setTsQueryDate(Timestamp tsQueryDate) {
		this.tsQueryDate = tsQueryDate;
	}
	
	public class DailyReportItem implements Serializable{
		private String strSerialNum		= "";			//序号
		private long lBankBillType		= -1;			//类型
		private long lOriginalNum		= 0;			//初始张数
		private long lTodayInCome		= 0;			//今日入库
		private long lTodayOutCome		= 0;			//今日出库
		private long lBalance			= 0;			//今日结余

		/**
		 * constructor
		 */
		public DailyReportItem(String strSerialNum){
			this.strSerialNum=""+strSerialNum;
		}
		/**
		 * @return Returns the lBalance.
		 */
		public long getLBalance() {
			return lBalance;
		}

		/**
		 * @param balance The lBalance to set.
		 */
		public void setLBalance(long balance) {
			lBalance = balance;
		}

		/**
		 * @return Returns the lOriginalNum.
		 */
		public long getLOriginalNum() {
			return lOriginalNum;
		}

		/**
		 * @param originalNum The lOriginalNum to set.
		 */
		public void setLOriginalNum(long originalNum) {
			lOriginalNum = originalNum;
		}

		/**
		 * @return Returns the lTodayInCome.
		 */
		public long getLTodayInCome() {
			return lTodayInCome;
		}

		/**
		 * @param todayInCome The lTodayInCome to set.
		 */
		public void setLTodayInCome(long todayInCome) {
			lTodayInCome = todayInCome;
		}

		/**
		 * @return Returns the lTodayOutCome.
		 */
		public long getLTodayOutCome() {
			return lTodayOutCome;
		}

		/**
		 * @param todayOutCome The lTodayOutCome to set.
		 */
		public void setLTodayOutCome(long todayOutCome) {
			lTodayOutCome = todayOutCome;
		}

		/**
		 * @return Returns the strSerialNum.
		 */
		public String getStrSerialNum() {
			return strSerialNum;
		}

		/**
		 * @param strSerialNum The strSerialNum to set.
		 */
		public void setStrSerialNum(String strSerialNum) {
			this.strSerialNum = strSerialNum;
		}

		/**
		 * @return Returns the lBankBillType.
		 */
		public long getLBankBillType() {
			return lBankBillType;
		}

		/**
		 * @param bankBillType The lBankBillType to set.
		 */
		public void setLBankBillType(long bankBillType) {
			lBankBillType = bankBillType;
		}

	}
	


}
