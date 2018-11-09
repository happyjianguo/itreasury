/*
 * RiskLevelInfo.java
 *
 * Created on 2003年2月12日
 */

package com.iss.itreasury.loan.risklevel.dataentity ;

import java.rmi.RemoteException ;
import java.beans.* ;
import java.sql.* ;
import com.iss.itreasury.loan.contract.dataentity.* ;

/**
 *
 * @author  yfan
 * @version
 */
public class RiskLevelInfo
      implements java.io.Serializable
{

    /** Creates new RiskLevelInfo */
    public RiskLevelInfo()
    {
        super() ;
    }

    public long m_lID ; //提前还款通知单ID标识

    public long m_lInputUserID ; //录入人标示
    public String m_sInputUserName ; //录入人名称
    public Timestamp m_dtInputDate ; //录入时间
    public long officeid = -1;
    public long currencyid = 1;
    public long m_lNextCheckUserID ; 	//下一个审核人标示
    public long m_lNextCheckLevel = -1;	//下一个审核级别

    public long lContractOldRiskLevel ; //变更前的状态
    public long lContractNewRiskLevel ; //变更后的状态
    public long m_lStatusID ; //变更状态
    public String m_sChangeReason ; //变更原因
    public Timestamp m_effectDate ; //变更生效日期 修改时间:2010 09 26

    public long m_lContractID ; //合同标识
    public ContractInfo cInfo ; // 合同明细

    public long m_lPageCount ; //记录数

}
