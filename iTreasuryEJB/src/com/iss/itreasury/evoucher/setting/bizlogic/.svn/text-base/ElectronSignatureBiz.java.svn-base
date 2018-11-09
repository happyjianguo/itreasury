package com.iss.itreasury.evoucher.setting.bizlogic;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.evoucher.setting.dao.ElectronSignatureDao;

//add by dwj 20110815 µç×ÓÇ©ÕÂÀà
public class ElectronSignatureBiz {
	
	public String findIsSignature(long clientID,long transTypeID,long nOfficeID,long nCurrencyID,String billName,int isNetBank) throws ITreasuryDAOException
	{
		String isSignature = "0";
		ElectronSignatureDao dao = new ElectronSignatureDao();
		isSignature = dao.findIsSignature(clientID, transTypeID, nOfficeID, nCurrencyID, billName, isNetBank);
		return isSignature;
	}

}
