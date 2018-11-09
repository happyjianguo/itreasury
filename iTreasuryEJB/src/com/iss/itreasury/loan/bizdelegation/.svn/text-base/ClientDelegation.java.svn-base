/*
 * Created on 2004-8-3
 *
 * Title:				iTreasury
 * @author             	yfan 
 * Company:             iSoftStone
 * Copyright:           Copyright (c) 2003
 * @version
 * Description:         
 */

package com.iss.itreasury.loan.bizdelegation;

import java.rmi.RemoteException;

import javax.ejb.CreateException;

import com.iss.itreasury.loan.base.LoanException;
import com.iss.itreasury.loan.loancommonsetting.bizlogic.LoanCommonSetting;
import com.iss.itreasury.loan.loancommonsetting.bizlogic.LoanCommonSettingHome;
import com.iss.itreasury.loan.loancommonsetting.dataentity.ClientInfo;
import com.iss.itreasury.loan.transdiscountapply.bizlogic.TransDiscountApply;
import com.iss.itreasury.util.EJBHomeFactory;
import com.iss.itreasury.util.IException;

public class ClientDelegation {
	
	private TransDiscountApply transDiscountApplyFacade = null;
	
	private LoanCommonSetting loanCommonSettingFacade = null;
	
	public ClientDelegation() throws RemoteException{
		try
		{
			/** 
			 *客户资料
			*/
			LoanCommonSettingHome loanCommonSettingHome;
			try {
			    loanCommonSettingHome =
					(LoanCommonSettingHome) EJBHomeFactory.getFactory().lookUpHome(
					        LoanCommonSettingHome.class);
			} catch (IException e) {
				throw new RemoteException("EJBHomeFactory连接错误",e);
			}
			loanCommonSettingFacade = (LoanCommonSetting) loanCommonSettingHome.create();
		}

		catch (CreateException ce)
		{
			throw new RemoteException("发生CreateException",ce);
		}

	}
    
    /**
     *客户资料新增操作
     */
    public long addClientInfo(ClientInfo info) throws java.rmi.RemoteException, LoanException
    {
        long lResult = -1;
        try 
        {
            //华能、国机项目
            lResult = loanCommonSettingFacade.saveClientInfo(info);
            //中油项目
            //return loanCommonSettingFacade.saveCustomerInfo(0, info.m_strName, "*", info.m_strChiefRepCode, info.c_lOfficeID, "*","*","*","*","*","*","*","*","*","*","*","*","*","*","*","*","*","*","*","*",-1,-1,-1,-1,-1,"*");
        } 
        catch (RemoteException re) 
        {
            throw re;
        } 
        catch (IException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return lResult;
    }
    
    /**
     *客户资料维护操作
     */
    public long saveClientInfo(ClientInfo info) throws java.rmi.RemoteException, LoanException
    {
        long lResult = -1;
        try 
        {
            //华能、国机项目
            lResult = loanCommonSettingFacade.saveClientInfo(info);
            //中油项目
            //return loanCommonSettingFacade.saveCustomerInfo(info.m_lID,"*","*", "*",-1,info.m_strOfficeAccount,info.m_strBank,info.m_strBankAccount,"*","*","*","*","*","*",info.m_strProvince,info.m_strCity,info.m_strAddress1,info.m_strAddress2,info.m_strZipCode,info.m_strLegalPerson,info.m_strPhone,info.m_strFax,info.m_strEmail,info.m_strContacter,info.m_strEconomyType,info.m_lParentDepartmentID,info.m_lParentID,info.m_lClientTypeID,-1,-1,"*");
        } 
        catch (RemoteException re) 
        {
            throw re;
        } 
        catch (IException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return lResult;
    }
    
    /**
     *客户资料查询操作
     */
    public ClientInfo findClientInfo(long lID) throws java.rmi.RemoteException, LoanException
    {
        ClientInfo info = new ClientInfo();
        try 
        {
            //华能、国机项目
            info = loanCommonSettingFacade.findClientByID(lID);
            //中油项目
            //return loanCommonSettingFacade.findCustomerByID(lID);
        } 
        catch (RemoteException re) 
        {
            throw re;
        } 
        catch (IException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return info;
    }

}
