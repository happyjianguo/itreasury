package com.iss.itreasury.evoucher.endorse.bizlogic;

import com.iss.itreasury.evoucher.base.VoucherBaseBean;
import com.iss.itreasury.evoucher.base.VoucherException;
import com.iss.itreasury.evoucher.endorse.dao.EndorseDao;

public class EndorseBiz extends VoucherBaseBean implements java.io.Serializable 
{

	public long VoucherEndorse(String[] transID) throws VoucherException
	{
		long lResult = -1;
		long temp = -1;
		
		EndorseDao dao = new EndorseDao(); 
		
		try
		{
			for(int i=0; i<transID.length; i++) {
				temp = dao.updateStatus(Long.valueOf(transID[i]).longValue());
				if( temp < 0 )
				{
					break;
				}
				else 
				{
					lResult = temp;
				}
			}
		}
		catch (Exception e)
        {
            throw new VoucherException("Gen_E001", e);
        }
		return lResult;
	}
}
