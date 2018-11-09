/*
 * Created on 2004-11-19
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.settlement.setting.bizlogic;

import java.sql.Connection;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import com.iss.itreasury.settlement.setting.dao.Sett_TurnInBankTransSettingDAO;
import com.iss.itreasury.settlement.setting.dataentity.TurnInBankTransSettingInfo;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IException;

/**
 * @author ytcui
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class TurnInBankTransSetting
{
	private static SimpleDateFormat tool = null;
	
	private Connection conn = null;
	/**
	 * Constructor for FilialeBiz.
	 */
	public TurnInBankTransSetting()
	{
		try
		{
			conn = Database.getConnection();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void save(TurnInBankTransSettingInfo info) throws IException
	{
		
		try
		{
			Sett_TurnInBankTransSettingDAO dao = new Sett_TurnInBankTransSettingDAO(conn);
			if( dao.getCount() > 0)
			{
				dao.update(info);
			}
			else
			{
				dao.add(info);
			}
		}
		catch (IException ie)
		{
			ie.printStackTrace();
			throw ie;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			e.printStackTrace();throw new IException("Gen_E001");
		}		
	}
	public static Timestamp convertStrToTimestamp(String date,String pattern)throws Exception{
		
		/**tool = new SimpleDateFormat(pattern);
		Timestamp ts = null;
		try{
			
			System.out.println(date);
			ts = new Timestamp(tool.parse(date).getTime());
			System.out.println(ts);
		}catch(Exception e){
			e.printStackTrace();
			e.printStackTrace();throw new Exception(e.getMessage());
			
		}
		return ts;
		**/
		String strTime = "1970-01-01 " + date + ":00";
		
		Timestamp ts = null;
		try 
		{
			ts = java.sql.Timestamp.valueOf(strTime);	
		}
		catch(Exception e){
			e.printStackTrace();
			e.printStackTrace();throw new Exception(e.getMessage());
			}
		return ts;
	}
	
	public static void main (String arg[]) throws IException
	{
		TurnInBankTransSettingInfo info = new TurnInBankTransSettingInfo ();
		
		//long lTemp = System.currentTimeMillis();
		//Timestamp result = new Timestamp(lTemp);
		
		TurnInBankTransSetting temp = new TurnInBankTransSetting();
		Timestamp result = null;
		try
		{
			result = TurnInBankTransSetting.convertStrToTimestamp("12:00:00","hh:mm:ss");
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		info.setDTTURNINENDTIME(result);
		temp.save(info);
	}
}
