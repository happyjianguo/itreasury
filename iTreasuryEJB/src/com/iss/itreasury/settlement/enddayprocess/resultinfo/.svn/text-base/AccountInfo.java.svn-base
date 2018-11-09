package com.iss.itreasury.settlement.enddayprocess.resultinfo;

/**
 * 账户数据。
 * 创建日期：(2002-1-15 17;        //50:16)
 * @author：Administrator
 */

import java.sql.Timestamp;

public class AccountInfo implements java.io.Serializable
{
    /** 
     * AccountInfo 构造子注解。
     */ 
    public AccountInfo()
    {
        super();
        
    }
    
    //账户标识
    public long m_lID = -1;
    
    //账户号全称
    public String m_strAccountNo = "";
    
    //活期第几笔
    public long m_lInterestNo = -1;
    
    //账户名称
    public String m_strAccountName = "";
    
    //账户号的第三部分
    public long m_lAccountNo = -1;
    
    //办事处标识
    public long m_lOfficeID = -1;
    
    //币种标识
    public long m_lCurrencyID = -1;
    
    //账户类型标识
    public long m_lAccountTypeID = -1;
    
    //账户类型名称
    public String m_strAccountTypeName = "";
    
    //客户标识
    public long m_lClientID = -1;
    
    //客户编号
    public String m_strClientCode = "";
    
    //是否是过渡活期账户
    public long m_lIsTransitionalAccount = -1;
    
    //账户名称
    public String m_strName = "";
    
    //账户状态标识（正常、冻结、封闭、清户）
    public long m_lAccountStatusID = -1;
    
    //复核状态（新增未复核，修改未复核，已复核）
    public long m_lCheckStatusID = -1;
    
    //开户日期
    public Timestamp m_dtOpen = null;
    
    //币种
    public long m_lCurrencyTypeID = -1;
    
    //银行账号编号
    public String[] m_strBankAccountCode = null;
    
    //开户行标识
    public long[] m_lBankID = null; 
    
    //
    public String[] m_strBankName = null;
    
    //备注
    public String m_strNote = "";
     
    //科目标识
    public long m_lSubjectID = -1;
    
    public String m_strSubject="";// 科目
    
    //是否计息 
    public long m_lIsWithInterest = -1;
    
    //当前利率计划标识
    public long m_lInterestRatePlanID = -1;
    
    public String m_strInterestRatePlanName = "";
    
    //利率计划生效日期
    public Timestamp m_dtEffective = null;
    
    //下次利率计划标识，到了dtEffect后,nInterestRatePlanID为nNextInterestRatePlanID
    public long m_lNextInterestRatePlanID = -1;
    
    //下次利率计划生效日
    public Timestamp m_dtNextEffective = null;
    
    //是否能透支   -->  nIsOverdraft
    public long m_lOverdraft = -1;
    
    //一级限制
    public long m_lFirstLimitID = -1;
    
    //一级限制透支限额
    public double m_dFirstLimit = 0.0;
    
    //二级限制
    public long m_lSecondLimitID = -1;
    
    //二级限制透支限额
    public double m_dSecondLimit = 0.0;
    
    //三级限制
    public long m_lThirdLimitID = -1;
    
    //三级限制透支限额
    public double m_dThirdLimit = 0.0;
    
    //账户余额
    public double m_dAmount = 0.0;
    
    //担保费最低余额限额
    public double m_dRestriceAssure = 0.0;
    
    //下一个凭证号码，创建和重置的时候为1
    public long m_lNextTrustEvidenceNo = -1;
    
    //贷款上限
    public double m_dLoanLimited = 0.0; 
    
    //活期利息 
    public double m_dInterest = 0.0;
    
    //对于活期和定期是上次计算利息的日期，对于贷款是上次还款的日子
    public Timestamp m_dtCal = null;
    
    //是否资金限制
    public long m_lIsRestrictID = -1;
    
    //限制金额
    public double m_dRestrict = 0.0;
    
    //限制起始日期
    public Timestamp m_dtRestrictStart = null;
    
    //限制结束日期
    public Timestamp m_dtRestrictEnd = null;
    
    //录入人ID
    public long m_lInputUserID = -1;
    
    //录入人姓名
    public String m_strInputUserName = "";
    
    //录入日期
    public Timestamp m_dtInput = null;
    
    //复核人ID
    public long m_lCheckUserID = -1;
    
    //复核人姓名
    public String m_strCheckUserName = "";
    
    //复核日期
    public Timestamp m_dtCheck = null;
    
    //分页显示总页数记录
    public long m_lPageCount = -1;
    
    public long m_lInterestAccountID=-1;///收息账户号ID
    public String m_strInterestAccountNo ="";///收息账户号编号
}
