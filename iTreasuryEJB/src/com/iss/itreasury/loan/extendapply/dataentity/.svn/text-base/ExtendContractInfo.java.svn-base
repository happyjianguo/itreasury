/*
 * ExtendContractInfo.java
 *
 * Created on 2002年4月16日, 下午5:48
 */

package com.iss.itreasury.loan.extendapply.dataentity;

import java.sql.*;

/**
 *
 * @author  yzhang
 * @version
 */
public class ExtendContractInfo implements java.io.Serializable {

    /** Creates new ExtendContractInfo */
    public ExtendContractInfo() {
        super();
    }

    // The following variables are added by wli@isoftstone.com
    public long       m_lExtendID;             //展期合同ID
    public long       m_lContractID;           //合同ID
    public long       m_lExtendApplyID;        //展期申请的ID
    public long       m_lLoanID;               //nLoanID:贷款申请ID
    public long       m_lLoanTypeID;           //贷款类型
    public String     m_strExtendCode;         //展期合同编号
    public String     m_strContractCode;       //合同编号
    public String     m_strApplyCode;          //申请编号
    public long       m_lExtendType;           //展期类型


    public long       m_lClientID;             //借款单位ID
    public String     m_strClientName;         //借款单位名称
    public double     m_dExtendAmount;         //展期金额
    public Timestamp  m_tsExtendStart;         //起始日期
    public Timestamp  m_tsExtendEnd;           //结束日期
    public long       m_lExtendIntervalNum;    //展期期限
    public double     m_dExtendInterestRate;   //展期利率
    public long       m_lStatusID;             //展期合同状态
    public long       m_lInputUserID;          //录入人ID
    public String     m_sInputUserName;        //录入人姓名
    public long       m_lCheckUserID;          //复核人ID
    public String     m_sCheckUserName;        //复核人姓名
    public long       m_nCurrencyID;           //币种
    public long       m_lPageCount;            //记录数
    public long       m_lCheckNum;             //检查次数
    public String     m_sDocName;              //合同文本文件名
    public String     m_sTempletName;          //模板文件名strTempletName
    public String m_sJSPFILENAME;              //jsp文件名
    
    public long		  m_lNextCheckLevel = -1;	//下一个审核级别
    
    public double     m_dStaidAdjustRate=0;    //固定浮动利率
    public double     m_dAdjustRate=0;         //浮动比例
    
    public Timestamp dtExtendBeginDate = null;		//展期起始日期
    public Timestamp dtExtendEndDate = null;		//展期结束日期

    //******************************      wli@isoftstone.com
}
