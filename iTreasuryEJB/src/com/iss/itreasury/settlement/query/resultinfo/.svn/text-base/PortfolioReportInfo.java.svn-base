/*
 * PortfolioReportInfo.java
 *
 * Created on 2002年8月15日, 下午3:19
 */
package com.iss.itreasury.settlement.query.resultinfo;
import java.sql.Timestamp;
/**
 *
 * @author  xrzhang
 */
public class PortfolioReportInfo
{
	//m_lTotalType=1表示本条记录为日合计，m_lTotalType=2表示本条记录为周合计，m_lTotalType=3表示本条记录月合计
	public long m_lTotalType = 0;
	//日期的字符串，在周合计的时候为'周合计'，在约合计的时候为'月合计'
	public String m_strDate = "";
	//日期
	public Timestamp m_tsDate = null;
	//信托存款笔数，指活期、定期组；等于所有科目类型等于  ‘212’ 的交易借贷笔数的累加
	//如果为业务量报表2，则此域代表贴现笔数
	public long m_lTransTrustDepositNumber = 0;
	//信托贷款笔数，等于所有科目类型等于  ‘112’ 的交易借贷笔数的累加
	//如果为业务量报表2，则此域代表关联交易委托收款笔数
	public long m_lTransTrustLoanNumber = 0;
	//委托贷款笔数，等于所有科目类型等于  ‘116’ 的交易借贷笔数的累加
	//如果为业务量报表2，则此域代表股份公司委托收款笔数
	public long m_lTransConsignLoanNumber = 0;
	//银行存款笔数，等于所有科目类型等于  ‘102’ 的交易借贷笔数的累加
	//如果为业务量报表2，则此域代表委托上收资金笔数
	public long m_lTransBankDepositNumber = 0;
	//如果为业务量报表2，则此域代表上存资金调回笔数
	public long m_lTransUpXXXNumber = 0;
	//网上结算笔数
	//如果为业务量报表2，则此域代表发放付息资金笔数
	public long m_lTransInternetSettlementNumber = 0;
	//调账笔数
	//如果为业务量报表1，则此域代表调账笔数
	public long m_lAdjustAccountNumber = 0;
	//其他笔数
	//如果为业务量报表2，则此域代表还短付笔数
	public long m_lTransOtherNumber = 0;
	//日合计笔数 ；；周合计笔数 ；；月合计笔数
	//如果为业务量报表2，则此域代表日合计笔数 ；；周合计笔数 ；；月合计笔数
	public long m_lDayTotalNumber = 0;
	//日合计结算金额 ；；周合计金额 ；；月合计金额
	//如果为业务量报表2，则此域代表日合计结算金额 ；；周合计金额 ；；月合计金额
	public double m_dDayTotalAmount = 0;
	//本年累计结算量金额
	//如果为业务量报表2，则此域代表本年累计结算量金额
	public double m_dYearTotalAmount = 0;
	//本年累计业务量笔数
	//如果为业务量报表2，则此域代表本年累计业务量笔数
	public long m_lYearTotalNumber = 0;
	//网上银行结算金额 ；；网上结算周合计金额 ；；网上结算月合计金额
	public double m_dInternetSettlementAmount = 0;
	//如果为业务量报表2，则此域代表贴现结算金额
	public double m_dDiscountAmount = 0;
	//如果为业务量报表2，则此域代表关联交易委托收款结算金额
	public double m_dGLJYConsignAmount = 0;
	//如果为业务量报表2，则此域代表股份公司委托收款结算金额
	public double m_dGFGSConsignAmount = 0;
	//如果为业务量报表2，则此域代表委托上收资金结算金额
	public double m_dUpReceiveAmount = 0;
	//如果为业务量报表2，则此域代表发放付息资金结算金额
	public double m_dUpXXXAmount = 0;
	//如果为业务量报表2，则此域代表上存资金调回结算金额
	public double m_dUpSaveAmount = 0;
	//如果为业务量报表2，则此域代表还短付结算金额，--变量名称错误 -陈熙
	public double m_dShortDebtReturnAmount = 0;
	//如果为业务量报表1，则此域代表调账金额
	public double m_dChangedAmount = 0;
}
