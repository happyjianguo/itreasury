package com.iss.itreasury.settlement.query.resultinfo;
import java.sql.Timestamp;
/**
 * 此处插入类型说明。
 * 创建日期：(2002-1-15 17:20:10)
 * @author：
 */
public  class AccountTransactionInfo implements java.io.Serializable//科目交易信息
{
	public Timestamp m_dtInterestStart=null;//起息日期 //added by mzh_fu 2007-3-12 解决把起息日错误显示成了执行日期的问题
	
    public Timestamp m_tsExecute=null;//执行日期
    public long m_lTransactionNoID=-1;//交易标识
    public String m_strTransNo="";//交易号
    public long m_lTransactionTypeID=-1;//交易类型标识
    public String m_strTransactionType="";//交易类型
    public String m_strAccountRecord="";//科目号
    public double m_dAmount=0;//金额
    public long m_lDirectionID=-1;//借贷标识
    public String m_strDirection="";//借贷名称
    public String m_strOtherAccountRecord="";//对方科目号
    public long m_lStatusID=-1;//状态
    public String m_strStatus="";//状态名称
    public String m_strAbstract="";//摘要
    public String m_strInputUser="";//录入人
    public String m_strCheckUser="";//复核人 
    public long m_lPageCount=0;//页号
    //
    public double m_dTotalAmount = 0.0;
    public long m_lTotalRecordes = 0;
    //用于银行存款明细查询
    public String m_strName = "";
    public String m_strName2 = "";
    public long   m_lRecord = -1;//行号
    public double m_dDRAmount=0;//借方金额
    public double m_dCRAmount=0;//贷方金额
    public String m_strCurrencyCode="";//币种名称
    public String m_strSource = "";//来源
    public String m_strDescription = "";
    public String m_strAccountDescription = "";//账户名称
    
}
