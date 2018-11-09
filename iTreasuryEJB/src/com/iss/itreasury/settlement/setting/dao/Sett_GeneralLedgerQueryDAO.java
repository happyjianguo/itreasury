package com.iss.itreasury.settlement.setting.dao;

import com.iss.itreasury.util.Log;

/**
 * 总账类设置
 * @author 宋雯霄
 *
 */

public class Sett_GeneralLedgerQueryDAO {
	
	public String queryGeneralLedgerSql(long lOfficeID, long lCurrencyID, String strStartCode, String strEndCode, String strName, String strSubject) 
	{
		StringBuffer strBuff = new StringBuffer();
		try
		{
			long lGeneralLedgerStartID = -1;
			long lGeneralLedgerEndID = -1;
			try
			{
				if (strStartCode != null && !strStartCode.equals("") && !strStartCode.equals("null"))
				{
					lGeneralLedgerStartID = Long.parseLong(strStartCode);
				}
			}
			catch (Exception ef)
			{
				lGeneralLedgerStartID = -1;
			}
			try
			{
				if (strEndCode != null && !strEndCode.equals("") && !strEndCode.equals("null"))
				{
					lGeneralLedgerEndID = Long.parseLong(strEndCode);
				}
			}
			catch (Exception ef)
			{
				lGeneralLedgerEndID = -1;
			}
			//返回需求的结果集
			strBuff = new StringBuffer();
			strBuff.append(" SELECT  ID ,sGeneralLedgerCode ,sName, sSubjectCode ,nStatusID,nofficeid,nCurrencyID ");
			strBuff.append(" FROM (");
			strBuff.append(" SELECT gl.ID ,gl.sGeneralLedgerCode, gl.sName ,gl.sSubjectCode sSubjectCode ,gl.nStatusID, gl.nofficeid,gl.nCurrencyID \n");
			strBuff.append(" FROM  Sett_GeneralLedger gl,Sett_currencysubject cs \n");
			strBuff.append(" where cs.ncurrencyid(+)= "+lCurrencyID+" and cs.stablename(+)='generalledger' and cs.nrecordid(+)=gl.id \n");
			strBuff.append("       and gl.nStatusID=1 AND gl.NOFFICEID= "+lOfficeID+" and gl.nCurrencyID= "+lCurrencyID+" \n");
			if (lGeneralLedgerStartID > 0)
			{
				strBuff.append(" and to_number(gl.sGeneralLedgerCode) >= " + lGeneralLedgerStartID);
			}
			if (lGeneralLedgerEndID > 0)
			{
				strBuff.append(" and to_number(gl.sGeneralLedgerCode) <= " + lGeneralLedgerEndID);
			}
			if (strName != null && !strName.equals("") && !strName.equals("null"))
			{
				strBuff.append(" and gl.sName  like  '%" + strName.trim() + "%' ");
			}
			if (strSubject != null && !strSubject.equals("") && !strSubject.equals("null"))
			{
				strBuff.append(" and gl.sSubjectCode  like  '%" + strSubject.trim() + "%' ");
			}
			
			strBuff.append(" ) ");
			//
			Log.print("*********" + strBuff.toString());
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return strBuff.toString();
		
	}

}
