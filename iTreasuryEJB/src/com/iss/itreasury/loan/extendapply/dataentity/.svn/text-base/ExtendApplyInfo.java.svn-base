/*
 * ExtendApplyInfo.java
 *
 * Created on 2002年2月19日, 下午6:15
 */

package com.iss.itreasury.loan.extendapply.dataentity ;

import java.beans.* ;
import java.sql.* ;
import java.util.* ;

/**
 *
 * @author  yzhang
 * @version
 */
public class ExtendApplyInfo
      implements java.io.Serializable
{

    /**
     * ExtendApplyInfo 构造子注解。
     */
    public ExtendApplyInfo()
    {
        super() ;
    }

    public long m_lID ; //展期申请标示

    public long m_nCurrencyID ; //币种
    
    public long m_typeId;//贷款类型
    public long m_lContractID ; //贷款合同标示
    public String m_strContractCode ; //贷款合同标示
    public double m_dLoanAmount ; // 合同金额
    public Timestamp m_tsLoanDate ; // 贷款日期
    public long m_lLoanIntervalNum ; // 贷款期限
    public long m_lisExtend ; // 是否展期

    public String m_strClientName ;
    public long m_lSerialNo ; //展期序号
    public double m_dLoanBalance ; //贷款余额
    public double m_dExtendAmount ; //展期金额
    public Timestamp m_tsExtendBeginDate ; //展期起始日期
    public Timestamp m_tsExtendEndDate ; //展期结束日期
    public long m_lExtendIntervalNum ; //展期月数
    public String m_strExtendReason ; //展期原因
    public String m_strReturnPostPend ; //归还延期还款本息资金
    public String m_strOtherContent ; //其他事项
    public double m_dExamineAmount ; //审核金额
    public long m_lIntervalNum ; //期限
    public double m_dInterestRate ; //利率
    public double m_dBasicInterestRate ; //基准利率
    public long m_lStatusID ; //状态
    public long m_lCheckNum ;

    public long m_lInputUserID ; //录入人标示
    public String m_sInputUserName ; //录入人名称
    public Timestamp m_tsInputDate ;
    public long m_lCheckUserID ; //复核人标示
    public String m_sCheckUserName ; //复核人名称

    public String m_sExCode ; // 展期合同编号
    public long m_lExContractID ; // 展期合同标识
    public long m_lNextUserID ; //下一审批人标识
    public long m_lNextCheckLevel = -1;	//下一个审核级别
    public Timestamp m_dtCheckDate ; // 审批日期
    public long m_lInterestTypeID ; // 利率类型
    public double m_dLiborAdjust ; // 利率调整
    public long m_lLiborRateID ;
    public long lBankRateTypeID ; // 银行利率类型
    public double m_dDefaultBankRate ; //缺省值

    public long loanID ; // LOANINFO.ID
    public Timestamp dtStartDate_loaninfo ; // 贷款起始日期
    public Timestamp dtEndDate_loaninfo ; // 贷款终止日期
    public double dExamineAmount_loaninfo ; // 贷款金额
    public double dfinterestRate_ioaninfo ; // 执行利率
    public long lExamineIntervalNum_loaninfo ;
    public double dLoanApplyAmount_loaninfo ;
    public long lConsignContractID_loaninfo ;
    public long lConsignClientID ;
    //Fan Yi
    public float fChargeRate ; //手续费率
    public double dContractBalance ;

    public long lLoanTypeID ; // 贷款申请类型
    public long lLoanSubTypeID ;//贷款子类型
    public String sClientName ; // 借款单位
    public String sEconomyType ;
    public String sConsignClientName ; // 委托单位
    public String sConsignCode ; // 委托合同编号
    public String sLastUserName ; // 最后审核人
    public String sApplyCode ; // 贷款申请编号
    public String sExtendApplyCode ; // 展期申请编号

    public long m_lextendtype ; // 展期类型
    public long m_lPlanVersionID ; // 计划版本标识
    public long m_lPageCount ; //记录数

    public Collection cExtendList ; // 展期明细表
    public Collection cExtendContractList ; // 展期合同表

    public String m_strOpinion1 = "" ; //审批意见
    public String m_strOpinion2 = "" ;
    public String m_strOpinion3 = "" ;
    
    public double dStaidAdjustRate=0;       //固定浮动利率
    public double dAdjustRate=0;            //浮动比例
    
    public long isLowLevel = -1;

}
