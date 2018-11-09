package com.iss.itreasury.settlement.setting.ejb;

import java.rmi.RemoteException;

import javax.ejb.CreateException;

import com.iss.itreasury.util.EJBHomeFactory;
import com.iss.itreasury.util.IException;

public class CurrencySettingDelegation {
	private CurrencySetting currencySetting = null;
	//构造函数
	public CurrencySettingDelegation() throws RemoteException{
		try{
			CurrencySettingHome home = (CurrencySettingHome) EJBHomeFactory.getFactory().lookUpHome(CurrencySettingHome.class);
			currencySetting = home.create();
		}catch (RemoteException e){
			throw e;
		}catch (IException e){
			e.printStackTrace();
			throw new RemoteException();
		}catch (CreateException e){
			e.printStackTrace();
			throw new RemoteException();
		}
	}
	
//	保存币种设置
	public long saveAllCurrency(long officeId,String strTransactionType) throws IException{
		try{
			return currencySetting.saveAllCurrency(officeId, strTransactionType);
		
		}catch(Exception e){
			
			throw new IException("币种设置出现异常，操作已取消",e);			
			
		}
	}
}
