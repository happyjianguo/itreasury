/*
 * ContractStatusInfo.java
 *
 * Created on 2003��2��12��
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

    public long m_lID ; //��ǰ����֪ͨ��ID��ʶ

    public long m_lInputUserID ; //¼���˱�ʾ
    public String m_sInputUserName ; //¼��������
    public Timestamp m_dtInputDate ; //¼��ʱ��

    public long m_lNextCheckUserID ; 	//��һ������˱�ʾ
    public long m_lNextCheckLevel = -1;	//��һ����˼���

    public long lContractOldStatusID ; //���ǰ��״̬
    public long lContractNewStatusID ; //������״̬
    public long m_lStatusID ; //���״̬
    public String m_sChangeReason ; //���ԭ��

    public long m_lContractID ; //��ͬ��ʶ
    public ContractInfo cInfo ; // ��ͬ��ϸ

    public long m_lPageCount ; //��¼��
    
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
