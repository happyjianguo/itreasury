package com.iss.itreasury.project.gzbfcl.util;


import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.settlement.base.SettlementException;
import com.iss.itreasury.util.DataFormat;

public class CreateCode extends ITreasuryDAO{

	public String createFoundsDispatatchCode(long systemYear,long CurrentID,long OfficeID) throws SettlementException{

		String lTmpCode="";
		String resultCode="";
		long lTmpYear=-1;
		long lTmpNumber=-1;
		String lTmpSymbol="DDL";
		String strTmpSQL = "";
		try
		{	
			initDAO();
			//得到最大FoundsDispatchCode
			strTmpSQL = "select max(FoundsDispatchCode) FoundsDispatchCode from Sett_FoundsDispatch  where currentID= ? and OfficeId=? ";
			transPS = prepareStatement(strTmpSQL);
			transPS.setLong(1, CurrentID);
			transPS.setLong(2, OfficeID);
			transRS = transPS.executeQuery();
			if (transRS != null && transRS.next()){
					lTmpCode = transRS.getString("FoundsDispatchCode");
					if(lTmpCode != null )
					{
					lTmpYear = DataFormat.parseLong(lTmpCode.substring(0, 4));
					lTmpSymbol=lTmpCode.substring(4, 7);
					lTmpNumber=DataFormat.parseLong(lTmpCode.substring(7));

					if(lTmpYear!=systemYear){
	
						resultCode=""+systemYear+""+lTmpSymbol+"0001";
					}
					else{
						
						lTmpNumber=lTmpNumber+1;
						resultCode=""+lTmpYear+""+lTmpSymbol+""+DataFormat.formatInt(lTmpNumber, 4)+"";
					}
				}
				else{
					
					resultCode = systemYear + lTmpSymbol + "0001";
					
				}
			}

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
