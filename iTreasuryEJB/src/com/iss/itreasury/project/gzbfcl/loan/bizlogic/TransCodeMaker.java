package com.iss.itreasury.project.gzbfcl.loan.bizlogic;

import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.project.gzbfcl.loan.dataentity.CreditAuthenticationInfo;
import com.iss.itreasury.settlement.base.SettlementException;
import com.iss.itreasury.util.DataFormat;

public class TransCodeMaker extends ITreasuryDAO
{
	private static TransCodeMaker maker = null;
	public static TransCodeMaker getIntance()
	{
		if(maker ==null)
		{
			return new TransCodeMaker();
		}
		return maker;
	}
	
	public synchronized String getTransCode(String systemYear,CreditAuthenticationInfo info) throws Exception
	{
		String lTmpCode = "";
		String resultCode = "";
		long lTmpYear = -1;
		long lTmpNumber = -1;
		String lTmpSymbol = "ZX";
		String strTmpSQL = "";		

		try
		{	
			initDAO();
			//得到最大FoundsDispatchCode
			strTmpSQL = " select max(transno) transno from LOAN_CREDITAUTHENTICATION where 1=1 ";
			strTmpSQL+=	" and currencyid =" + info.getCurrencyId();
			strTmpSQL+= " and officeid = " + info.getOfficeId();
			transPS = prepareStatement(strTmpSQL);
			transRS = transPS.executeQuery();
			if (transRS != null && transRS.next()){
				lTmpCode = transRS.getString("transno");
				if(lTmpCode != null )
				{
					lTmpYear = DataFormat.parseLong(lTmpCode.substring(0, 4));
					lTmpSymbol = lTmpCode.substring(4, 6);
					lTmpNumber = DataFormat.parseLong(lTmpCode.substring(6, 9));
					
					if(lTmpYear != Long.valueOf(systemYear).longValue()){
	
						resultCode = systemYear + lTmpSymbol + "001";
					}
					else{
						
						lTmpNumber += 1;
						resultCode = lTmpYear + lTmpSymbol + DataFormat.formatInt(lTmpNumber, 3);
					}
				}else {
					resultCode = systemYear + lTmpSymbol + "001";
				}
			}	
			transRS.close();
			transRS = null;
			transPS.close();
			transPS = null;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new SettlementException();
		}
		finally
		{
			try
			{
				finalizeDAO();
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new SettlementException();
			}
		}
		
		return resultCode;
	}

}
