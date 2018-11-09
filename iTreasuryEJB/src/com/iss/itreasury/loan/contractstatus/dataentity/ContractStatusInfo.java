/*
 * ContractStatusInfo.java
 *
 * Created on 2003年2月12日
 */

package com.iss.itreasury.loan.contractstatus.dataentity ;

import java.rmi.RemoteException ;
import java.beans.* ;
import java.io.Serializable;
import java.sql.* ;
import com.iss.itreasury.loan.contract.dataentity.* ;
import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;
import com.iss.sysframe.base.dataentity.BaseDataEntity;

/**
 *
 * @author  yfan
 * @version
 */
public class ContractStatusInfo
extends BaseDataEntity implements Serializable
{

    /** Creates new ContractStatusInfo */
    public ContractStatusInfo()
    {
        super() ;
    }

    public long m_lID ; //提前还款通知单ID标识

    public long m_lInputUserID ; //录入人标示
    public String m_sInputUserName ; //录入人名称
    public Timestamp m_dtInputDate ; //录入时间

    public long m_lNextCheckUserID ; 	//下一个审核人标示
    public long m_lNextCheckLevel = -1;	//下一个审核级别

    public long lContractOldStatusID ; //变更前的状态
    public long lContractNewStatusID ; //变更后的状态
    public long m_lStatusID ; //变更状态
    public String m_sChangeReason ; //变更原因

    public long m_lContractID ; //合同标识
    public ContractInfo cInfo ; // 合同明细

    public long m_lPageCount ; //记录数
    
    private InutParameterInfo inutParameterInfo = null; //added by mzh_fu 2007/07/10

	public InutParameterInfo getInutParameterInfo() {
		return inutParameterInfo;
	}

	public void setInutParameterInfo(InutParameterInfo inutParameterInfo) {
		this.inutParameterInfo = inutParameterInfo;
	}

	@Override
	public long getId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setId(long id) {
		// TODO Auto-generated method stub
		
	}

}
