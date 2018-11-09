package com.iss.itreasury.settlement.query.resultinfo;
import java.sql.Timestamp;
/**
 * 此处插入类型说明。
 * 创建日期：(2002-1-15 17:20:10)
 * @author：
 */
public class AccountTransactionTypeInfo implements java.io.Serializable
{
    public long m_lTransactionTypeID=-1;//交易类型标识
    public String m_strTransactionType="";//交易类型
    public double m_dDebit=0;//付款合计
    public double m_dLoan=0;//收款合计
    public long m_lNumber=0;//交易记录张数
    public long m_lCreditNumber=0;//贷方交易记录
    public long m_lDebitNumber=0;//借方交易记录
    public long m_lPageCount=0;//页数
    
    public String m_strAccount="";//科目号
}
